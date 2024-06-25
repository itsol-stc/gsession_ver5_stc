package jp.groupsession.v2.api.smail.send;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlPushSender;
import jp.groupsession.v2.sml.biz.SmlReceiveFilter;
import jp.groupsession.v2.sml.model.SmailSendModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメールを送信するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlSendAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlSendAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "smlsend";

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        ApiSmlSendBiz biz = new ApiSmlSendBiz(umodel.getUsrsid());
        ApiSmlSendForm thisForm = (ApiSmlSendForm) form;
        GsMessage gsMsg = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);
        //入力チェック
        ActionErrors errors
        = thisForm.validateCheckSmlSend(con, gsMsg, getSessionUserSid(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

        //テンポラリディレクトリパスを取得
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);
        //アプリケーションのルートパス
        String appRootPath = getAppRootPath();

        boolean commitFlg = false;
        SmailSendModel sendMdl = null;
        try {

            //添付ファイルをテンポラリディレクトリに保存する。
            biz.setTempFileAll(tempDir, thisForm);

            //ショートメール送信
            MlCountMtController cntCon = getCountMtController(req);
            sendMdl = biz.insertMailData(
                    thisForm, con, cntCon, appRootPath, tempDir, getPluginConfig(req),
                    getRequestModel(req));
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("メッセージの送信に失敗", e);
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        //受信フィルタ実行処理
        new SmlReceiveFilter(con, sendMdl)
            .doFilterJmail();
        //GSショートメールアプリ使用者にPush通知
        SmlPushSender pushSender = new SmlPushSender(reqMdl, con,
                sendMdl.getAccountSidList(),
                 thisForm.getTitle(),
                  sendMdl.getSmjSid(),
                  sendMdl.getSacMdl());
        pushSender.sendPush();

        //テンポラリディレクトリの削除を行う
        temp.deleteTempPath(getRequestModel(req),
                GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

        //ログ出力処理
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        smlBiz.outPutApiLog(req, con, umodel.getUsrsid(), this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE, "");

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (commitFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        log__.debug("createXml end");
        return doc;
    }

}
