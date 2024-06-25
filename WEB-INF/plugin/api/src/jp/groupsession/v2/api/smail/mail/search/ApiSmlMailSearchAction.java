package jp.groupsession.v2.api.smail.mail.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBodyModel;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailSearchDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.sml090.Sml090SearchParameterModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 受信ショートメールリストを取得するWEBAPIアクション
 * <br>[解  説] 最新の20件を取得する。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailSearchAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailSearchAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aform フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aform, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        ApiSmlMailSearchForm form = (ApiSmlMailSearchForm) aform;

        ArrayList<SmailModel> smailList = null;
        HashMap<Integer, ApiSmlMailBodyModel> mailBodyMap = null;

        int sacSid  = form.getSacSid();
        int smlKbn  = form.getSmlKbn();
        int offset  = form.getOffset() + 1; // 後で-1される為、ここで加算
        int count   = form.getCount();
        int userSid = getSessionUserSid(req);
        GsMessage gsMsg = new GsMessage(req);
        ApiSmlMailDao apiDao = new ApiSmlMailDao(con);

        // アカウントチェック
        ApiSmlMailBiz biz = new ApiSmlMailBiz();
        ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        try {
            String smlKbnStr = String.valueOf(smlKbn);

            // 検索条件
            int    mark      = form.getMark();
            int    sendFrom  = form.getSendFrom();
            int[]  sendTo    = form.getSendTo();
            String keyword   = NullDefault.getString(form.getKeyword(),  "");
            int    targetKbn = form.getTargetKbn();
            boolean isMyAc   = false;
            int    smlSid    = form.getSmlSid();

            Sml090SearchParameterModel prmModel = new Sml090SearchParameterModel();
            prmModel.setMySid(sacSid);
            prmModel.setOffset(offset);
            prmModel.setLimit(count);
            prmModel.setMailMode(smlKbnStr);
            prmModel.setSearchOrderKey1(GSConstSmail.ORDER_KEY_DESC);
            prmModel.setSearchOrderKey2(GSConstSmail.ORDER_KEY_DESC);

            prmModel.setFromDate(form.getFromTimeDate());
            prmModel.setToDate(form.getToTimeDate());

            // マーク
            if (mark  >= 0) {
                prmModel.setMailMark(mark);
            }
            log__.info("SMAIL SEARCH CHECK1");

            // 差出人
            if (sendFrom >= 0) {
                prmModel.setSltGroup("0"); // 仮グループSID ※値は実際には使用されない
                prmModel.setSltUser(GSConstSmail.SML_ACCOUNT_STR + String.valueOf(sendFrom));
            }

            // キーワード
            if (keyword != null && keyword.length() > 0) {
                List<String> keywordList = new ArrayList<String>();
                keywordList.add(keyword);
                prmModel.setKeywordList(keywordList);

                String[] targets = null;
                if (targetKbn == 3) { // 検索条件 件名+本文
                    targets = new String[2];
                    targets[0] = String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE);
                    targets[1] = String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN);
                } else if (targetKbn == 2) {
                    targets = new String[1];
                    targets[0] = String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN);
                } else if (targetKbn == 1) {
                    targets = new String[1];
                    targets[0] = String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE);
                }
                prmModel.setSearchTarget(targets);
            }

            // 宛先ユーザ(複数)
            if (sendTo != null && sendTo.length > 0) {
                String[] atesakiList = new String[sendTo.length];
                for (int i = 0; i < sendTo.length; i++) {
                    atesakiList[i] = GSConstSmail.SML_ACCOUNT_STR
                                          + Integer.valueOf(sendTo[i]).toString();
                }
                prmModel.setAtesaki(atesakiList);
            }

            //ショートメールSID
            if (smlSid > -1) {
                prmModel.setSmlSid(smlSid);
            }


            // ショートメールリスト取得
            SmailSearchDao searchDao = new SmailSearchDao(con);
            if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                // 受信
                smailList = searchDao.getSearchDataJushin(prmModel);
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                // 送信
                smailList = searchDao.getSearchDataSoushin(prmModel);
                isMyAc    = true;
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                // 草稿
                smailList = searchDao.getSearchDataSoukou(prmModel);
                isMyAc    = true;
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                // ゴミ箱
                smailList = searchDao.getSearchDataGomiBako(prmModel);
            } else {
                // 該当フォルダが見つからない
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("cmn.folder"));
                StrutsUtil.addMessage(err, msg, "folder");
                addErrors(req, err);
                return null;
            }

            if (smailList != null && smailList.size() > 0) {
                ArrayList<Integer> sidList     = new ArrayList<Integer>();
                ArrayList<Integer> atesakiSids = new ArrayList<Integer>();
                String sacName   = null;
                int    ukoFlg    = GSConst.YUKOMUKO_YUKO;

                if (isMyAc) {
                    // 自アカウントデータを使用する為、ここでデータ取得
                    SmlAccountDao sacDao = new SmlAccountDao(con);
                    SmlAccountModel mdl = sacDao.select(sacSid);
                    if (mdl != null) {
                        sacName   = mdl.getSacName();

                        // ユーザSIDが存在する場合、ユーザ無効フラグを取得
                        if (mdl.getUsrSid() > 0) {
                            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
                            CmnUsrmInfModel cuiMdl = cuiDao.selectUserNameAndJtkbn(mdl.getUsrSid());
                            if (cuiMdl != null) {
                                ukoFlg  = cuiMdl.getUsrUkoFlg();
                            }
                        }
                    }
                }
                for (SmailModel mdl : smailList) {
                    if (mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                        sidList.add(mdl.getSmlSid());

                        // 受信メールの場合のみ宛先用メールSID一覧
                        atesakiSids.add(mdl.getSmlSid());
                    } else if (mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                        sidList.add(mdl.getSmlSid());
                    }

                    if (isMyAc) {
                        // 送信・草稿の場合、差出人情報は自アカウントで固定
                        mdl.setAccountSid(sacSid);
                        mdl.setAccountName(sacName);
                        mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_NORMAL); // 使用中の自アカウントの為、常に状態区分:有効
                        mdl.setUsrUkoFlg(ukoFlg);
                    } else if (!StringUtil.isNullZeroStringSpace(mdl.getUsiSei())
                            && !StringUtil.isNullZeroStringSpace(mdl.getUsiMei())) {
                        // 通常アカウント(ユーザ情報があるアカウント)の場合、ユーザ情報を使用
                        mdl.setAccountName(mdl.getUsiSei() + " " + mdl.getUsiMei());
                        if (mdl.getUsrJkbn() == GSConstUser.USER_JTKBN_DELETE) {
                            mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_DELETE);
                        } else {
                            mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_NORMAL);
                        }
                    }
                }

                // 送信 or 草稿ボックス の場合、全件において宛先データが必要(TO以外)
                if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                 || smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                    atesakiSids = sidList;
                }

                // メールSID一覧から情報を個別に取得
                mailBodyMap = apiDao.getMailBodyMap(sidList);
                //bodyMap  = apiDao.getMailBodyMap(sidList);
                //binMdMap = apiDao.getFileListMap(sidList);

                // ＤＢから宛先一覧情報取得(メールSIDがキー)
                HashMap<Integer, ArrayList<AtesakiModel>> atesakiMap
                                           = apiDao.getMailAtesakiMap(atesakiSids, smlKbnStr);
                if (atesakiMap != null) {
                    for (SmailModel mdl : smailList) {
                        ArrayList<AtesakiModel> list = atesakiMap.get(mdl.getSmlSid());
                        if (list != null) {
                            // 既存データがある場合、一覧へ追加(送信・草稿は宛先データが取得済みの為)
                            if (mdl.getAtesakiList() != null && mdl.getAtesakiList().size() > 0) {
                                list.addAll(mdl.getAtesakiList());
                            }
                            mdl.setAtesakiList(list);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            log__.error("ショートメールリストの取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        //XMLデータ作成
        if (smailList != null) {
            for (SmailModel smlModel : smailList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                this.setSmlElement(result, smlModel, mailBodyMap);
            }
            resultCnt = smailList.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));

        return doc;
    }

    /**
     * <br>[機  能] ショートメール情報をXMLのresult属性にセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param result      エレメント
     * @param smlModel    ショートメール情報
     * @param mailBodyMap メール本文一覧
     * @throws Exception 実行例外
     */
    protected void setSmlElement(Element result,
                                 SmailModel smlModel,
                                 HashMap<Integer, ApiSmlMailBodyModel> mailBodyMap)
            throws Exception {

        Integer key = Integer.valueOf(smlModel.getSmlSid());

        // ---------------------------------------------------------
        // 不足分データ
        String             bodyStr = null;
        List<CmnBinfModel> retBin  = null;
        int                smlType = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;
        if (mailBodyMap != null && mailBodyMap.get(key) != null) {
            ApiSmlMailBodyModel mailBodyMdl = mailBodyMap.get(key);
            smlType = mailBodyMdl.getMailType();
            bodyStr = mailBodyMdl.getMailBody();
            retBin  = mailBodyMdl.getBinList();
        }
        // ---------------------------------------------------------

        // メール情報
        result.addContent(_createElement("smlSid",   smlModel.getSmlSid()));   // メールSID
        result.addContent(_createElement("smlKbn",   smlModel.getMailKbn()));  // メール区分
        result.addContent(_createElement("smlType",  smlType));                // メールタイプ
        result.addContent(_createElement("openKbn",  smlModel.getSmjOpkbn())); // 開封区分
        result.addContent(_createElement("title",    smlModel.getSmsTitle())); // 件名
        result.addContent(_createElement("mark",     smlModel.getSmsMark()));  // マーク
        result.addContent(_createElement("mailJkbn", smlModel.getUsrJkbn()));  // メール状態区分

        // 差出人アカウント情報
        result.addContent(_createElement("sacSid",    smlModel.getAccountSid()));  // SID
        result.addContent(_createElement("sacName",   smlModel.getAccountName())); // 名前
        result.addContent(_createElement("sacJkbn",   smlModel.getAccountJkbn())); // 状態区分
        result.addContent(_createElement("sacUkoFlg", smlModel.getUsrUkoFlg()));   // ユーザ有効・無効

        //日時yyyy/MM/dd hh:mm:ss形式に変換
        String strSdate = null;
        if (smlModel.getSmsSdate() != null) {
            strSdate =
                UDateUtil.getSlashYYMD(smlModel.getSmsSdate())
                + "  "
                + UDateUtil.getSeparateHMS(smlModel.getSmsSdate());
        }
        //date 日時
        Element date = new Element("date");
        date.addContent(strSdate);
        result.addContent(date);

        // 宛先一覧
        Element atesakiSet = new Element("atesakiSet");
        result.addContent(atesakiSet);
        Integer atesakiCnt = 0;

        List<AtesakiModel>  retAts = smlModel.getAtesakiList();
        if (retAts != null) {
            for (AtesakiModel mdl : retAts) {
                Element atesakiEle = new Element("atesaki");
                atesakiEle.addContent(_createElement("smaSid",    mdl.getAccountSid()));
                atesakiEle.addContent(_createElement("smaName",   mdl.getAccountName()));
                atesakiEle.addContent(_createElement("smaJkbn",   mdl.getAccountJkbn()));
                atesakiEle.addContent(_createElement("smaUkoFlg", mdl.getUsrUkoFlg()));
                atesakiEle.addContent(_createElement("sendKbn",   mdl.getSmjSendkbn()));
                atesakiSet.addContent(atesakiEle);
            }
            atesakiCnt = retAts.size();
        }
        atesakiSet.setAttribute("Count", Integer.toString(atesakiCnt));

        // ラベル一覧
        Element labelSet = new Element("labelSet");
        result.addContent(labelSet);
        Integer labelCnt = 0;

        List<SmlLabelModel> retLbl = smlModel.getLabelList();
        if (retLbl != null) {
            for (SmlLabelModel mdl : retLbl) {
                Element labelEle = new Element("label");
                labelEle.addContent(_createElement("slbSid",  mdl.getSlbSid()));
                labelEle.addContent(_createElement("slbName", mdl.getSlbName()));
                labelSet.addContent(labelEle);
            }
            labelCnt = retLbl.size();
        }
        labelSet.setAttribute("Count", Integer.toString(labelCnt));

        // 本文
        if (bodyStr != null) {
            result.addContent(_createElement("body",  bodyStr));
        }

        // 添付ファイル一覧
        Element tmpFileSet = new Element("tmpFileSet");
        result.addContent(tmpFileSet);
        Integer tmpFileCnt = 0;
        if (retBin != null) {
            for (CmnBinfModel mdl : retBin) {
                Element binEle = new Element("tmpFile");
                binEle.addContent(_createElement("binSid",  mdl.getBinSid()));
                binEle.addContent(_createElement("fileName", mdl.getBinFileName()));
                binEle.addContent(_createElement("fileSize", mdl.getBinFileSize()));
                binEle.addContent(_createElement("filePath", mdl.getBinFilePath()));
                tmpFileSet.addContent(binEle);
            }
            tmpFileCnt = retBin.size();
        }
        tmpFileSet.setAttribute("Count", Integer.toString(tmpFileCnt));
    }
}
