package jp.groupsession.v2.api.webmail.mail.send;

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
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.api.webmail.mail.ApiWmlMailBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailServerConnectException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.exception.WmlTempDirNoneException;
import jp.groupsession.v2.wml.exception.WmlTempFileNameException;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] WEBメールを送信するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlMailSendAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlMailSendAction.class);

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
        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        boolean commitFlg = false;
        String  message   = null;

        ApiWmlMailSendForm form = (ApiWmlMailSendForm) aForm;
        GsMessage    gsMsg  = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);
        WmlBiz       wmlBiz = new WmlBiz();

        int     wacSid      = form.getWacSid();
        int     userSid     = getSessionUserSid(req);
        String  appRootDir  = getAppRootPath();
        SmtpSendModel sendData     = null;
        UDate         sendPlanDate = null;
        ActionErrors  errors       = new ActionErrors();

        try {
            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(wacSid, userSid)) {
                //message = gsMsg.getMessage("wml.191");
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

            // 予約送信時のみチェック
            if (form.getSendPlan() == GSConstWebmail.TIMESENT_FUTURE) {
                UDate planDate =
                ApiWmlMailBiz.convertSlashDateTimeFormat(form.getSendPlanDate(), false);
                if (planDate == null) {
                    // 日付情報が正しくない場合エラー
                    ActionMessage msg = new ActionMessage("error.input.format.text",
                    gsMsg.getMessage("wml.260"));
                    StrutsUtil.addMessage(errors, msg, "account");
                    addErrors(req, errors);
                    return null;
                }
            }

            String[] errMessages = form.validateSendMail(con, reqMdl, appRootDir);

            if (errMessages != null && errMessages.length > 0) {
                message = errMessages[0]; // 送信パラメータエラー
            } else {
                String tempDir = WmlMailSendBiz.getSendTempDir(reqMdl);

                // 送信時に容量のチェックが行われるため、事前にテンポラリディレクトリに添付ファイルのアップロードを行う
                biz.uploadTmpFile(errors, con, gsMsg, reqMdl, form, appRootDir, tempDir);
                if (!errors.isEmpty()) {
                    addErrors(req, errors);
                    log__.info(errors.toString());
                    return null;
                }
                
                int sendResult = WmlSendResultModel.RESULT_SUCCESS;

                //メール送信の実行
                WmlMailSendBiz sendBiz = form.createMailSendBiz(
                    con, getGsContext(), appRootDir, tempDir, reqMdl);
                try {
                    WmlSendResultModel result = sendBiz.executeMail(con, getGsContext(),
                        userSid, appRootDir, tempDir, reqMdl.getDomain(), reqMdl.getLocale());
                    //テンポラリディレクトリを削除する
                    WmlMailSendBiz.deleteSendTempDir(tempDir);

                    boolean sendNowFlg = result.getSendMailData().getSendPlanDate() == null;
                    if (sendNowFlg) {
                        //通常送信の場合
                        sendResult = result.getResult();
                        if (sendResult == WmlSendResultModel.RESULT_SUCCESS) {
                            //送信成功
                            sendData = result.getSendMailData();
                        } else {
                            //送信失敗
                            if (sendResult == WmlSendResultModel.RESULT_SIZEOVER) {
                                String[] mailSize = {Long.toString(
                                                        result.getMailMaxSize() / (1024 * 1024))
                                                        + "MB"};
                                message = gsMsg.getMessage("wml.245", mailSize);
                            } else {
                                message = gsMsg.getMessage("wml.js.40");
                            }
                        }
                    } else {
                        //時間差送信，予約送信の場合
                        if (result != null) {
                            sendData = result.getSendMailData();
                            // 送信時間コピー(ログ用)
                            sendPlanDate = sendData.getSendPlanDate();
                        }
                    }
                } catch (WmlTempDirNoneException e) {
                    //テンポラリディレクトリがなかった時
                    message = gsMsg.getMessage("wml.222", new String[] {
                                                    gsMsg.getMessage("cmn.main.temp.path")});
                } catch (WmlMailSizeOverException e) {
                    //メールのサイズが上限を超えていた場合
                    message = gsMsg.getMessageVal0("wml.245", e.getMaxSizeText());
                } catch (WmlMailServerConnectException e) {
                    //メールサーバへアクセスできなかった場合
                    message = gsMsg.getMessage("wml.js.40");
                } catch(WmlDiskSizeOverException e) {
                    //ディスクの容量制限を超えていた場合
                    message = e.getErrorMsg();
                } catch (WmlTempFileNameException e) {
                    //添付ファイル名が不正の場合
                    message = e.getMessage();
                    return null;
                }
            }

            // 結果判定
            if (sendData != null) {
                commitFlg = true; // エラーなし → データ更新OK

                //ログ出力
                String value = "";
                if (sendPlanDate != null) {
                    //UDate sendPlanDate = wml010Biz.getSendPlanDate(paramMdl);
                    value = getInterMessage(req, "wml.211") + "：";
                    value += " [" + getInterMessage(req, "wml.260") + "]";
                    value += UDateUtil.getSlashYYMD(sendPlanDate)
                            +  " " + UDateUtil.getSeparateHM(sendPlanDate);
                    value += " ";
                } else {
                    value = getInterMessage(req, "cmn.mail.send") + "：";
                }
                value += "[Subject]" + sendData.getSubject();
                value += " [From]" + NullDefault.getString(sendData.getFrom(), "");
                value += " [To]" + NullDefault.getString(sendData.getTo(), "");
                value += " [Cc]" + NullDefault.getString(sendData.getCc(), "");
                value += " [Bcc]" + NullDefault.getString(sendData.getBcc(), "");
                wmlBiz.outPutApiLog(reqMdl, con, userSid, getClass().getCanonicalName(),
                        getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE,
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
        }

        // エラーメッセージがある場合
        if (message != null && message.length() > 0) {
            //result.addContent(_createElement("Message", message));
            ActionMessage msg = new ActionMessage("errors.free.msg", message);
            StrutsUtil.addMessage(errors, msg, "meailSaveError");
            addErrors(req, errors);
            return null;
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
}
