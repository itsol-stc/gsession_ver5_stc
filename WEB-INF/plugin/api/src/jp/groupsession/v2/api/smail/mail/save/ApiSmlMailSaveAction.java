package jp.groupsession.v2.api.smail.mail.save;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.sml020.Sml020Biz;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.sml.sml020.Sml020ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメールを草稿へ保存するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailSaveAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailSaveAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "smlmailsave";

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm  フォーム
     * @param req    リクエスト
     * @param res    レスポンス
     * @param con    DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        boolean commitFlg = false;

        ApiSmlMailSaveForm form = (ApiSmlMailSaveForm) aForm;
        RequestModel reqMdl = getRequestModel(req);

        int sacSid  = form.getSacSid();
        int userSid = getSessionUserSid(req);
        GsMessage gsMsg = new GsMessage(req);
        ApiSmlMailBiz biz = new ApiSmlMailBiz();
        String tempDir = "";

        try {
            // アカウントチェック＋メール閲覧可否チェック(返信、転送、草稿で参照するメールへのアクセス制限確認)
            ActionErrors err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            // 参照メールの使用可否チェック
            biz.validateFormSmail(err, con, gsMsg, form);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            //テンポラリディレクトリの削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

            //テンポラリディレクトリパスを取得
            tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

            //アプリケーションのルートパス
            String appRootPath = getAppRootPath();
            String domain      = reqMdl.getDomain();

            // 添付ファイルのアップロード
            biz.uploadTmpFile(err, con, gsMsg, req, form, domain, appRootPath, tempDir);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            // フォームへ情報セット(PCブラウザと共通処理を使用)
            Sml020Form sml020Form = new Sml020Form();
            sml020Form.setSml020ProcMode(form.getProcMode());
            sml020Form.setSml020MailType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL); // メールタイプは固定
            sml020Form.setSml010EditSid(form.getSmlSid());
            sml020Form.setSml010SelectedSid(form.getSmlSid());
            sml020Form.setSml020Title(NullDefault.getString(form.getTitle(), ""));
            sml020Form.setSmlViewAccount(sacSid);
            sml020Form.setSml020SendAccount(sacSid);
            sml020Form.setSml020Mark(form.getMark());
            sml020Form.setSml020Body(form.getBody());
            sml020Form.setSml020userSid(biz.convertSacSidList(form.getSendToList(), null));
            sml020Form.setSml020userSidCc(biz.convertSacSidList(form.getSendCcList(), null));
            sml020Form.setSml020userSidBcc(biz.convertSacSidList(form.getSendBccList(), null));

            //入力チェック
            err = sml020Form.validateCheck020(Sml020Form.VALIDATE_MODE_SAVE, con,
                    reqMdl, getAppRootPath());
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            // メール情報をDBに登録
            Sml020Biz sml020Biz = new Sml020Biz(reqMdl);
            MlCountMtController cntCon = getCountMtController(req);
            Sml020ParamModel paramMdl = new Sml020ParamModel();
            paramMdl.setParam(sml020Form);
            sml020Biz.insertSitagakiData(paramMdl, reqMdl, con, cntCon,
                                        appRootPath, TEMP_DIRECTORY_ID);

            // 結果判定
            if (paramMdl.getErrorsList() == null || paramMdl.getErrorsList().size() == 0) {
                // エラーなし → データ更新OK
                commitFlg = true;

                //ログ出力処理
                SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
                smlBiz.outPutApiLog(req, con, umodel.getUsrsid(),
                        this.getClass().getCanonicalName(),
                        getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE, "");
            }
        } catch (SQLException e) {
            log__.error("メッセージの送信に失敗", e);
        } finally {

            //テンポラリディレクトリの削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        log__.debug("createXml end");

        return biz.resultDocument(commitFlg);
    }
}
