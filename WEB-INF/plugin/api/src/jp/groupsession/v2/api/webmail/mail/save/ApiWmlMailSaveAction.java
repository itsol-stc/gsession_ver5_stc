package jp.groupsession.v2.api.webmail.mail.save;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.api.webmail.mail.ApiWmlMailBiz;
import jp.groupsession.v2.api.webmail.mail.send.ApiWmlMailSendAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] WEBメールを編集するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlMailSaveAction extends ApiWmlMailSendAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlMailSaveAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        // WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        boolean commitFlg = false;

        ApiWmlMailSaveForm form = (ApiWmlMailSaveForm) aForm;
        GsMessage    gsMsg       = new GsMessage(req);
        RequestModel reqMdl      = getRequestModel(req);
        GSContext    gsContext   = getGsContext();
        String       appRootPath = getAppRootPath();

        int          userSid    = getSessionUserSid(req);
        SmtpSendModel sendData   = null;
        ActionErrors errors     = new ActionErrors();

        String dirId = null;
        try {
            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(form.getWacSid(), userSid)) {
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            // フォームパラメータチェック
            ApiWmlMailBiz biz = new ApiWmlMailBiz();
            biz.validateFormSmail(errors, con, gsMsg, form, userSid);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return null;
            }

            // パラメータ最大容量チェック
            biz.validateMailSize(errors, req, gsMsg);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return null;
            }
            GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

            GSTemporaryPathModel tempPathModel = GSTemporaryPathModel.getInstance(reqMdl,
                            GSConst.PLUGINID_API,
                            GSConstWebmail.PLUGIN_ID_WEBMAIL);
            tempUtil.createTempDir(tempPathModel);
                    
            String tempDir = tempPathModel.getTempPath();
                    

            // 添付ファイルのアップロード
            biz.uploadTmpFile(errors, con, gsMsg, reqMdl, form, appRootPath, tempDir);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                log__.info(errors.toString());
                return null;
            }


            WmlMailSendBiz sender = __createMailSendBiz(
                con,
                gsContext,
                reqMdl,
                form,
                appRootPath,
                tempDir);

            WmlSendResultModel result = null;
            try {
                result = sender.executeDraftMail(
                    con,
                    gsContext,
                    userSid,
                    appRootPath,
                    tempDir,
                    reqMdl.getDomain(),
                    reqMdl.getLocale()
                    );
                    con.commit();
            } catch (WmlMailSizeOverException e) {
                //メールのサイズが上限を超えていた場合
                ActionMessage msg = new ActionMessage("error.sizeover.sendmail", e.getMaxSizeText());
                StrutsUtil.addMessage(errors, msg, "meailSaveError");
                addErrors(req, errors);
                return null;
            } catch (WmlDiskSizeOverException e) {
                //ディスクの容量制限を超えていた場合
                String message = e.getErrorMsg();
                ActionMessage msg = new ActionMessage("errors.free.msg", message);
                StrutsUtil.addMessage(errors, msg, "meailSaveError");
                addErrors(req, errors);
                return null;
            }

            // メール保存
            sendData = result.getSendMailData();
            // 結果判定
            if (sendData != null) {
                commitFlg = true;  // エラーなし → データ更新OK

                //ログ出力
                String value = getInterMessage(req, "cmn.save.draft") + "："
                + " [Subject]" + NullDefault.getString(sendData.getSubject(), "")
                + " [To]" + NullDefault.getString(sendData.getTo(), "")
                + " [Cc]" + NullDefault.getString(sendData.getCc(), "")
                + " [Bcc]" + NullDefault.getString(sendData.getBcc(), "");

                WmlBiz wmlBiz = new WmlBiz();
                wmlBiz.outPutApiLog(reqMdl, con, userSid, getClass().getCanonicalName(),
                        getInterMessage(req, "cmn.entry"), GSConstLog.LEVEL_TRACE,
                        StringUtil.trimRengeString(value, 3000));
            }
        } catch (ClassNotFoundException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (SQLException e) {
            log__.error("メッセージの送信に失敗", e);
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            throw e;
        } catch (IOException e) {
            log__.error("IOException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }

            //テンポラリディレクトリ削除
            if (dirId != null && dirId.isEmpty()) {
                WmlBiz wmlBiz = new WmlBiz();
                wmlBiz.deleteTempDir(reqMdl, dirId);
            }
        }

        log__.debug("createXml end");

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (commitFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        return doc;
    }

    /**
     * 共通ビジネスロジックを生成
     * @return メール送信保管ビジネスロジック
     * @throws SQLException
     * @throws IOToolsException
     * @throws IOException
     * @throws TempFileException 
     */
    private WmlMailSendBiz __createMailSendBiz(Connection con,GSContext gsContext, RequestModel reqMdl,
        ApiWmlMailSaveForm form, String appRootPath, String tempDir) throws SQLException, IOToolsException, IOException, TempFileException {

        // 初期状態は即時送信設定
        int   sendPlanType = GSConstWebmail.TIMESENT_NORMAL;
        int   sendPlanImm  = GSConstWebmail.TIMESENT_AFTER;


        // 送信方法チェック
        if (form.getSendPlan() == 2) {
            // 予約送信
            sendPlanType = GSConstWebmail.TIMESENT_AFTER;
        } else if (form.getSendPlan() == 1) {
            // 時間差送信
            sendPlanImm = 0;
        }

        int timeSent = GSConstWebmail.TIMESENT_NORMAL;
        if (sendPlanType == GSConstWebmail.TIMESENT_AFTER) {
            timeSent = GSConstWebmail.TIMESENT_FUTURE;
        } else if (sendPlanImm != 1) {
            timeSent = GSConstWebmail.TIMESENT_AFTER;
        }

        int wacSid = form.getWacSid();

        UDate sendPlanDate = 
            ApiWmlMailBiz.convertSlashDateTimeFormat(form.getSendPlanDate(), false);

        int compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_NOTCOMPRESS;
        if (form.getFileCompress() == 1) {
            compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_COMPRESS;
        }

        WmlMailSendBiz mailBiz = WmlMailSendBiz.builder()
            .setTo(form.getSendAdrToStr())
            .setCc(form.getSendAdrCcStr())
            .setBcc(form.getSendAdrBccStr())
            .setSubject(form.getTitle())
            .setContent(form.getBody())
            .setWacSid(wacSid)

            .setSendMessageNum(form.getWmlSid())
            .setSendMailType(form.getSendType())
            .setHtmlMail(false)
            .setTimeSentType(timeSent)
            .setSendPlanDate(sendPlanDate)
            .setCompressFileType(compressFileFlg)
            .build(con, gsContext, appRootPath, tempDir, reqMdl.getSmodel().getUsrsid());


        return mailBiz;
    }
}
