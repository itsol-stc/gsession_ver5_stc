package jp.groupsession.v2.api.smail.labeladd;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.sml320kn.Sml320knBiz;
import jp.groupsession.v2.sml.sml320kn.Sml320knParamModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメールラベルを追加するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlLabelAddAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlLabelAddAction.class);

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

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        ApiSmlLabelAddForm form = (ApiSmlLabelAddForm) aForm;

        int    sacSid    = form.getSacSid();
        String labelName = form.getSlbName();
        int    userSid   = getSessionUserSid(req);
        String sacName   = "";

        boolean commitFlg = false;

        GsMessage    gsMsg = new GsMessage(req);

        try {

            // アカウントチェック
            ApiSmlMailBiz biz = new ApiSmlMailBiz();
            ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            // 入力されたラベル名チェック
            GSValidateSmail.validateTextBoxInput(err, labelName,
                "sml300LabelName",
                getInterMessage(req, GSConstSmail.TEXT_LABEL),
                GSConstSmail.MAXLEN_SEARCH_KEYWORD,
                true);

            if (!err.isEmpty()) {
                // ラベル名に問題
                addErrors(req, err);
                return null;
            }

            //登録処理
            if (labelName != null && labelName.length() > 0) {
                MlCountMtController cntCon = getCountMtController(req);
                Sml320knParamModel paramMdl = new Sml320knParamModel();
                paramMdl.setSmlAccountSid(sacSid);
                paramMdl.setSml320LabelName(labelName);
                paramMdl.setSmlLabelCmdMode(GSConstSmail.CMDMODE_ADD); // 登録で固定
                Sml320knBiz sml320Biz = new Sml320knBiz(con);
                sml320Biz.doAddEdit(paramMdl, userSid, cntCon);

                // エラーなし → データ更新OK
                commitFlg = true;

                // ログ出力
                SmlAccountDao sacDao = new SmlAccountDao(con);
                SmlAccountModel sacMdl = sacDao.select(sacSid);
                if (sacMdl != null) {
                    sacName = sacMdl.getSacName();
                }
                SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
                smlBiz.outPutApiLog(req, con, userSid, this.getClass().getCanonicalName(),
                        getInterMessage(req, "cmn.entry"), GSConstLog.LEVEL_INFO, "アカウント:"
                                + sacName + "\n[name]" + NullDefault.getString(labelName, ""));
            }
        } catch (SQLException e) {
            log__.error("ショートメールラベルの追加に失敗", e);
        }

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
