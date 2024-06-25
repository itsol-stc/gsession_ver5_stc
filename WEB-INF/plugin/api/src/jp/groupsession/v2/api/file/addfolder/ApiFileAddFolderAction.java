package jp.groupsession.v2.api.file.addfolder;

import java.sql.Connection;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダの登録を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileAddFolderAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileAddFolderAction.class);

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

        RequestModel reqMdl = getRequestModel(req);

        log__.debug("createXml start");

        ApiFileAddFolderForm thisForm = (ApiFileAddFolderForm) form;
        ApiFileAddFolderBiz biz = new ApiFileAddFolderBiz();
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }

        //入力チェック
        ActionErrors errors = thisForm.validateFileUpload(con, gsMsg, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        boolean commitFlg = false;

        int fdrSid = -1;
        try {

            int userSid = umodel.getUsrsid();
            ApiFileAddFolderParamModel params = new ApiFileAddFolderParamModel();
            params.setParam(thisForm);

            //登録処理
            fdrSid = biz.registerData(con, cntCon, params, userSid);

            String textEntry = gsMsg.getMessage("cmn.entry");

            //ログ出力処理
            FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(gsMsg.getMessage(req, "cmn.target"));
            sb.append("] ");
            sb.append(fileBiz.getDirctoryPath(
                    fdrSid, true));

            fileBiz.outPutApiLog(umodel.getUsrsid(), getClass().getCanonicalName(),
                            textEntry, GSConstLog.LEVEL_TRACE,
                            sb.toString());

            con.commit();
            commitFlg = true;

        } catch (Exception e) {
            log__.error("Exception", e);
            throw e;

        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);

        Element dirSidElement = new Element("fdrSid");
        dirSidElement.addContent(String.valueOf(fdrSid));
        result.addContent(dirSidElement);

        log__.debug("createXml end");
        return doc;
    }

}
