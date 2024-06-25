package jp.groupsession.v2.api.webmail.mail.search;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.batch.WmlReceiveBatch;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;
import jp.groupsession.v2.wml.model.mail.WmlMailLabelModel;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSearchModel;
import jp.groupsession.v2.wml.wml010.Wml010ParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] メールリストを取得するWEBAPIアクション
 * <br>[解  説] 最新の20件を取得する。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlMailSearchAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlMailSearchAction.class);

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

        //log__.debug("createXml start");
        // WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        ApiWmlMailSearchForm form = (ApiWmlMailSearchForm) aform;
        RequestModel reqMdl = getRequestModel(req);
        GsMessage    gsMsg  = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();

        int  usrSid  = umodel.getUsrsid();
        int  wacSid  = form.getWacSid();
        long wdrSid  = form.getWdrSid();
        int  offset  = form.getOffset();
        int  count   = form.getCount();
        int  wdrType = -1;
        String warnDiskRatio = null; // 警告メッセージ

        WmlDao          wmlDao = new WmlDao(con);
        WmlDirectoryDao wdrDao = new WmlDirectoryDao(con);

        // 選択されているアカウントが使用可能かを判定する
        if (!wmlDao.canUseAccount(wacSid, usrSid)) {
            // アカウントがない場合
            ActionMessage msg = new ActionMessage("search.data.notfound",
                                                  gsMsg.getMessage("wml.102"));
            StrutsUtil.addMessage(errors, msg, "account");
            addErrors(req, errors);
            return null;
        }

        // ディレクトリ情報取得＋権限チェック
        if (wdrSid >= 0) {
            WmlDirectoryModel wdrMdl = wdrDao.select(wdrSid);
            if (wdrMdl != null && wdrMdl.getWdrView() == 0 && wacSid == wdrMdl.getWacSid()) {
                wdrType = wdrMdl.getWdrType();
            } else {
                // 該当フォルダが見つからない
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("cmn.folder"));
                StrutsUtil.addMessage(errors, msg, "folder");
                addErrors(req, errors);
                return null;
            }
        }

        // 新着更新チェック(受信メールの場合のみ) → 失敗した場合でもそのまま続行
        boolean commit = false;
        if (form.isNewFlg() && wdrType == GSConstWebmail.DIR_TYPE_RECEIVE) {
            try {
                Wml010ParamModel paramMdl = new Wml010ParamModel();
                paramMdl.setWmlViewAccount(String.valueOf(wacSid));
                paramMdl.setWml010viewDirectory(wdrSid);
                paramMdl.setWml010viewDirectoryType(wdrType);
                paramMdl.setWml010sortKey(0);
                paramMdl.setWml010order(0);

                GSContext        context     = getGsContext();
                MessageResources msgResource =
                                        (MessageResources) context.get(GSContext.MSG_RESOURCE);
                WmlReceiveBatch receiveBatch =
                         new WmlReceiveBatch(context, wacSid, msgResource, reqMdl.getDomain());
                Thread thread = new Thread(receiveBatch);
                receiveBatch.setStatus(WmlReceiveBatch.STATUS_RECEIVE);
                thread.start();

                // 受信メール進捗チェック(1秒間隔でチェック → 一定時間経過後はタイムアウト)
                for (int i = 0; i < ApiWmlMailSearchForm.SEARCH_RECEIVE_MAIL_TIME; i++) {
                    if (receiveBatch.getStatus() != WmlReceiveBatch.STATUS_RECEIVE) {
                        // 終了判定
                        break;
                    }
                    Thread.sleep(1000);
                }

                if (con != null && !con.isClosed()) {
                    con.commit();
                    commit = true;
                }
            } finally {
                if (!commit && !con.isClosed()) {
                    con.rollback();
                }
            }
        }


        WmlBiz biz = new WmlBiz();

        //アカウントディスク使用量
        long accountDiskSize = biz.getUseDiskSize(con, wacSid);

        //ディスク容量上限
        WmlAdmConfDao wacAdmDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = wacAdmDao.selectAdmData();
        int limitDiskSize = biz.getDiskLimitSize(con, wacSid, admConfMdl);

        //ディスク使用割合、ディスク容量警告
        if (limitDiskSize > 0) {
            //管理者設定 ディスク容量
            if (admConfMdl.getWadWarnDisk() == GSConstWebmail.WAD_WARN_DISK_YES) {
                int diskWarnTh = admConfMdl.getWadWarnDiskTh();
                BigDecimal useDiskSize = new BigDecimal(accountDiskSize);
                BigDecimal alertSize   = new BigDecimal(diskWarnTh * 1024 * 1024);
                alertSize = alertSize.divide(new BigDecimal(100), 2,
                                                        BigDecimal.ROUND_HALF_UP);
                alertSize = alertSize.multiply(new BigDecimal(limitDiskSize));
                if (useDiskSize.compareTo(alertSize) >= 0) {
                    // 警告表示
                    warnDiskRatio = Integer.toString(diskWarnTh);
                }
            }
        }

        //strBuild.append("\"useDiskRatio\" : \"" + useDiskRatio.toString() + "\",");
        //strBuild.append("\"warnDiskRatio\" : \"" + warnDiskRatio + "\",");

        List<WmlMailResultModel> mailList = null;

        try {
            // 検索条件
            WmlMailSearchModel searchMdl = new WmlMailSearchModel();
            searchMdl.setStart(offset + 1); // 読み込み開始位置(SQLで-1されるので調整)
            searchMdl.setMaxCount(count);   // 1ページの最大表示件数

            searchMdl.setAccountSid(wacSid);      // アカウントSID
            searchMdl.setDirectorySid(wdrSid);    // ディレクトリSID
            searchMdl.setDirectoryType(wdrType);  // ディレクトリ区分

            if (form.getKeyword() != null && form.getKeyword().length() > 0) {
                searchMdl.setKeyword(form.getKeyword());   // キーワード
            }
            if (form.getLabelSid() > 0) {
                searchMdl.setLabelSid(form.getLabelSid()); // ラベル
            }

            if (form.getSendFrom() != null && form.getSendFrom().length() > 0) {
                searchMdl.setFrom(form.getSendFrom());      // 差出人
            }

            if (form.getSendTo() != null && form.getSendTo().length() > 0) {
                // 区分けしていないので、全てチェック
                searchMdl.setDestinationTo(true);  // To フラグ
                searchMdl.setDestinationCc(true);  // CC フラグ
                searchMdl.setDestinationBcc(true); // BCC フラグ
                searchMdl.setDestination(form.getSendTo()); // 宛先
            } else {
                searchMdl.setDestinationTo(false);  // To フラグ
                searchMdl.setDestinationCc(false);  // CC フラグ
                searchMdl.setDestinationBcc(false); // BCC フラグ
            }

            // 並び順は日付降順で固定
            searchMdl.setSortKey(GSConstWebmail.SORTKEY_SDATE);
            searchMdl.setOrder(GSConstWebmail.ORDER_DESC);

            UDate fdate = form.getFromTimeDate();
            UDate tdate = form.getToTimeDate();
            if (fdate != null) {
                searchMdl.setResvDateFrom(fdate);   // 日付 受信日 From
            }
            if (tdate != null) {
                searchMdl.setResvDateTo(tdate);     // 日付 受信日 To
            }

            searchMdl.setTempFile(form.isTempFile()); // 添付ファイル チェック
            searchMdl.setReadKbn(form.getReadKbn());  // 未読 / 既読

            //メッセージ一覧取得
            WmlMaildataDao maildataDao = new WmlMaildataDao(con);
            mailList = maildataDao.getMailList(searchMdl,
                                          WmlBiz.getBodyLimitLength(getAppRootPath()));

        } catch (SQLException e) {
            log__.error("WEBメールリストの取得に失敗", e);
        }


        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        // 警告表示
        if (warnDiskRatio != null) {
            String msgStr = gsMsg.getMessage("wml.250", new String[] {warnDiskRatio});
            resultSet.addContent(_createElement("Message", msgStr));
        }

        Integer resultCnt = 0;

        //XMLデータ作成
        if (mailList != null) {
            for (WmlMailResultModel mdl : mailList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                result.addContent(_createElement("wmlSid",   mdl.getMailNum()));  // メールSID
                result.addContent(_createElement("wdrSid",   mdl.getDirSid()));   // ディレクトリSID
                result.addContent(_createElement("openKbn",  (mdl.isReaded() ? 1 : 0))); // 開封区分
                result.addContent(_createElement("title",    mdl.getSubject()));  // 件名
                result.addContent(_createElement("body",     mdl.getBody()));     // 本文

                if (mdl.isReply()) {
                    // 返信済みフラグ(フラグがない場合はデータ削減の為に省略)
                    result.addContent(_createElement("reply", 1));
                }
                if (mdl.isForward()) {
                    // 転送済みフラグ(フラグがない場合はデータ削減の為に省略)
                    result.addContent(_createElement("forward", 1));
                }

                // 差出人情報
                result.addContent(_createElement("sendAdr",  mdl.getFrom()));

                String strSdate = null;

                //日時(yyyy/MM/dd hh:mm:ss形式)
                if (mdl.getDate() != null) {
                    strSdate =
                        UDateUtil.getSlashYYMD(mdl.getDate())
                        + " "
                        + UDateUtil.getSeparateHMS(mdl.getDate());
                    result.addContent(_createElement("date", strSdate));
                }

                // 送信予定日時(送信待ちの場合のみ)
                if (mdl.isSendWaitMail() && mdl.getSendPlanDate() != null) {
                    strSdate = UDateUtil.getSlashYYMD(mdl.getSendPlanDate())
                             + " "
                             + UDateUtil.getSeparateHMS(mdl.getSendPlanDate());
                    result.addContent(_createElement("sendPlanDate", strSdate));
                }

                // 宛先一覧
                List<String> toList  = null;
                List<String> ccList  = null;
                List<String> bccList = null;
                if (mdl.getSendAddress() != null) {
                    toList  = mdl.getSendAddress().getToList();
                    ccList  = mdl.getSendAddress().getCcList();
                    bccList = mdl.getSendAddress().getBccList();
                }
                Element atesakiSet = new Element("atesakiSet");
                result.addContent(atesakiSet);
                Integer atesakiCnt = 0;
                if (toList != null) {
                    for (String adr : toList) {
                        Element atesakiEle = new Element("atesaki");
                        atesakiEle.addContent(_createElement("smaName", adr));
                        atesakiEle.addContent(_createElement("sendKbn", 0));
                        atesakiSet.addContent(atesakiEle);
                    }
                    atesakiCnt += toList.size();
                }
                if (ccList != null) {
                    for (String adr : ccList) {
                        Element atesakiEle = new Element("atesaki");
                        atesakiEle.addContent(_createElement("smaName", adr));
                        atesakiEle.addContent(_createElement("sendKbn", 1));
                        atesakiSet.addContent(atesakiEle);
                    }
                    atesakiCnt += ccList.size();
                }
                if (bccList != null) {
                    for (String adr : bccList) {
                        Element atesakiEle = new Element("atesaki");
                        atesakiEle.addContent(_createElement("smaName", adr));
                        atesakiEle.addContent(_createElement("sendKbn", 2));
                        atesakiSet.addContent(atesakiEle);
                    }
                    atesakiCnt += bccList.size();
                }
                atesakiSet.setAttribute("Count", Integer.toString(atesakiCnt));

                // ラベル一覧
                Element labelSet = new Element("labelSet");
                result.addContent(labelSet);
                Integer labelCnt = 0;
                if (mdl.getLabelList() != null) {
                    for (WmlMailLabelModel lbl : mdl.getLabelList()) {
                        Element labelEle = new Element("label");
                        labelEle.addContent(_createElement("wlbSid",  lbl.getId()));
                        labelEle.addContent(_createElement("wlbName", lbl.getName()));
                        labelSet.addContent(labelEle);
                    }
                    labelCnt = mdl.getLabelList().size();
                }
                labelSet.setAttribute("Count", Integer.toString(labelCnt));

                // 添付ファイル一覧
                Element tmpFileSet = new Element("tmpFileSet");
                result.addContent(tmpFileSet);
                Integer tmpFileCnt = 0;
                if (mdl.getTempFileList() != null && mdl.getTempFileList().size() > 0) {
                    // 添付ファイル圧縮設定フラグ
                    result.addContent(
                            _createElement("compressFile", mdl.getSendPlanCompressFile()));

                    for (MailTempFileModel file : mdl.getTempFileList()) {
                        Element binEle = new Element("tmpFile");
                        binEle.addContent(_createElement("wtfSid",   file.getBinSid()));
                        binEle.addContent(_createElement("fileName", file.getFileName()));
                        binEle.addContent(_createElement("filePath", file.getFilePath()));
                        binEle.addContent(_createElement("fileSize", file.getFileSize()));
                        tmpFileSet.addContent(binEle);
                    }
                    tmpFileCnt = mdl.getTempFileList().size();
                }
                tmpFileSet.setAttribute("Count", Integer.toString(tmpFileCnt));
            }
            resultCnt = mailList.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));

        //log__.debug("createXml end");
        return doc;
    }
}
