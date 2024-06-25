package jp.groupsession.v2.api.smail.mail.list;

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

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBodyModel;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailDao;
import jp.groupsession.v2.api.smail.mail.search.ApiSmlMailSearchAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 受信ショートメールリストを取得するWEBAPIアクション
 * <br>[解  説] 最新の20件を取得する。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailListAction extends ApiSmlMailSearchAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailListAction.class);

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

        ApiSmlMailListForm form = (ApiSmlMailListForm) aform;
        GsMessage gsMsg = new GsMessage(req);

        List<SmailModel> smailList = null;
        //HashMap<Integer, List<CmnBinfModel>> binMdMap  = null;
        //HashMap<Integer, String>             bodyMap   = null;
        HashMap<Integer, ApiSmlMailBodyModel> mailBodyMap = null;

        int sacSid  = form.getSacSid();
        int smlKbn  = form.getSmlKbn();
        int offset  = form.getOffset() + 1;
        int count   = form.getCount();
        int userSid = getSessionUserSid(req);

        UDate fromDate = form.getFromTimeDate();
        UDate toDate   = form.getToTimeDate();

        // アカウントチェック
        ApiSmlMailBiz biz = new ApiSmlMailBiz();
        ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        try {
            String smlKbnStr = String.valueOf(smlKbn);

            // ショートメールリスト取得
            SmailDao smlDao = new SmailDao(con);

            boolean isMyAc = false;
            if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                // 受信(全件)
                smailList = smlDao.selectJmeisList(sacSid, offset, count,
                                                   GSConstSmail.MSG_SORT_KEY_DATE,
                                                   GSConstSmail.ORDER_KEY_DESC,
                                                   GSConstCommon.NUM_INIT,
                                                   fromDate, toDate);
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                // 送信
                smailList = smlDao.selectSmeisList(sacSid, offset, count,
                                                   GSConstSmail.MSG_SORT_KEY_DATE,
                                                   GSConstSmail.ORDER_KEY_DESC,
                                                   fromDate, toDate);

                //宛先を設定
                smailList = __setSendAtesakiList(smailList);

                isMyAc = true;
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                // 草稿
                smailList = smlDao.selectWmeisList(sacSid, offset, count,
                                                   GSConstSmail.MSG_SORT_KEY_DATE,
                                                   GSConstSmail.ORDER_KEY_DESC,
                                                   fromDate, toDate);

                //宛先を設定
                smailList = __setSendAtesakiList(smailList);

                isMyAc = true;
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                // ゴミ箱
                smailList = smlDao.selectGomibakoList(sacSid, offset, count,
                                                      GSConstSmail.MSG_SORT_KEY_DATE,
                                                      GSConstSmail.ORDER_KEY_DESC,
                                                      fromDate, toDate);
            } else if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
                // ラベル(検索なし)
                int slbSid = form.getSlbSid();
                SmlLabelModel labelData = null;
                if (slbSid >= 0) {
                    SmlLabelDao labelDao = new SmlLabelDao(con);
                    labelData = labelDao.select(slbSid);
                }

                if (labelData != null) {
                    // ラベルが存在する
                    smailList = smlDao.selectLabelList(sacSid, slbSid, offset, count,
                            GSConstSmail.MSG_SORT_KEY_DATE,
                            GSConstSmail.ORDER_KEY_DESC,
                            fromDate, toDate);
                } else {
                    // ラベルが見つからない
                    ActionMessage msg = new ActionMessage("search.data.notfound",
                                                       gsMsg.getMessage("cmn.label"));
                    StrutsUtil.addMessage(err, msg, "label");
                    addErrors(req, err);
                    return null;
                }
            } else {
                // 該当フォルダが見つからない
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("cmn.folder"));
                StrutsUtil.addMessage(err, msg, "folder");
                addErrors(req, err);
                return null;
            }

            if (smailList != null && smailList.size() > 0) {
                String sacName = null;
                int    ukoFlg  = GSConst.YUKOMUKO_YUKO;

                if (isMyAc) {
                    // 自アカウントデータを使用する為、ここでデータ取得
                    SmlAccountDao sacDao = new SmlAccountDao(con);
                    SmlAccountModel mdl = sacDao.select(sacSid);
                    if (mdl != null) {
                        sacName   = mdl.getSacName();

                        // ユーザSIDが存在する場合、ユーザ無効フラグを取得
                        if (mdl.getUsrSid() > 0) {
                            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
                            CmnUsrmInfModel cuiMdl = cuiDao.selectUserNameAndJtkbn(userSid);
                            if (cuiMdl != null) {
                                ukoFlg  = cuiMdl.getUsrUkoFlg();
                            }
                        }
                    }
                }

                List<Integer> sidList = new ArrayList<Integer>(); // 不足データを取得する為のアカウントSID一覧
                for (SmailModel mdl : smailList) {
                    if (mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                     || mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                     || mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                        sidList.add(mdl.getSmlSid());
                    }

                    if (isMyAc) {
                        // 送信・草稿の場合、[差出人 = 自アカウント]で固定
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

                // メールSID一覧から情報を個別に取得
                ApiSmlMailDao apiDao = new ApiSmlMailDao(con);
                mailBodyMap = apiDao.getMailBodyMap(sidList);
                //bodyMap  = apiDao.getMailBodyMap(sidList);
                //binMdMap = apiDao.getFileListMap(sidList);
                HashMap<Integer, ArrayList<AtesakiModel>> atesakiMap
                              = apiDao.getMailAtesakiMap(sidList, smlKbnStr);
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

        //XMLデータ作成
        if (smailList != null) {
            for (SmailModel smlModel : smailList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                this.setSmlElement(result, smlModel, mailBodyMap);
            }
        }

        //log__.debug("createXml end");
        return doc;
    }

    /**
     * <br>[機  能] 送信済み/草稿 ショートメール情報の「宛先」を設定する
     * <br>[解  説] 通常アカウント(ユーザ情報があるアカウント)の場合、
     * <br>　　　　 「宛先」アカウント名称に「ユーザ名」を設定
     * <br>[備  考]
     * @param smailList ショートメール情報一覧
     * @return ショートメール情報一覧(宛先設定後)
     */
    private List<SmailModel> __setSendAtesakiList(List<SmailModel> smailList) {
        AtesakiModel atesakiMdl = null;
        for (int smlIdx = 0; smlIdx < smailList.size(); smlIdx++) {
            ArrayList<AtesakiModel> atesakiList = smailList.get(smlIdx).getAtesakiList();
            if (atesakiList == null || atesakiList.isEmpty()) {
                continue;
            }

            for (int atesakiIdx = 0; atesakiIdx < atesakiList.size(); atesakiIdx++) {
                atesakiMdl = atesakiList.get(atesakiIdx);

                //通常アカウント(ユーザ情報があるアカウント)の場合、
                //アカウント名称に「ユーザ名」を設定
                if (!StringUtil.isNullZeroStringSpace(atesakiMdl.getUsiSei())
                && !StringUtil.isNullZeroStringSpace(atesakiMdl.getUsiMei())) {

                    atesakiList.get(atesakiIdx).setAccountName(
                            atesakiMdl.getUsiSei() + " " + atesakiMdl.getUsiMei());

                    if (atesakiMdl.getUsrJkbn() == GSConstUser.USER_JTKBN_DELETE) {
                        atesakiList.get(atesakiIdx).setAccountJkbn(
                                GSConstSmail.SAC_JKBN_DELETE);
                    } else {
                        atesakiList.get(atesakiIdx).setAccountJkbn(
                                GSConstSmail.SAC_JKBN_NORMAL);
                    }
                }
            }
            smailList.get(smlIdx).setAtesakiList(atesakiList);
        }

        return smailList;
    }
}
