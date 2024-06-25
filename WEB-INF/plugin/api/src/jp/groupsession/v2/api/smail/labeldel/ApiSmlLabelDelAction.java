package jp.groupsession.v2.api.smail.labeldel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.sml310.Sml310Biz;
import jp.groupsession.v2.sml.sml310.Sml310ParamModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメールラベルを削除するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlLabelDelAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlLabelDelAction.class);

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

        ApiSmlLabelDelForm form = (ApiSmlLabelDelForm) aForm;

        int    sacSid   = form.getSacSid();
        int    labelSid = form.getSlbSid();
        int    userSid  = getSessionUserSid(req);
        String sacName  = "";

        boolean commitFlg = false;

        GsMessage gsMsg = new GsMessage(req);

        try {
            // アカウントチェック
            ApiSmlMailBiz biz = new ApiSmlMailBiz();
            ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            // ラベル使用可否チェック
            if (labelSid < 0) {
                ActionMessage msg = new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(err, msg, "label");
                addErrors(req, err);
                return null;
            }

            // 使用可能なラベル一覧の中からラベルSIDが一致するデータを検索
            SmlLabelDao  smllblDao = new SmlLabelDao(con);
            List<SmlLabelModel> labelList = smllblDao.getLabelList(sacSid);
            SmlLabelModel labelMdl = null;
            if (labelList != null) {
                for (SmlLabelModel mdl : labelList) {
                    if (mdl.getSlbSid() == labelSid) {
                        labelMdl = mdl;
                        break;
                    }
                }
            }

            // 一致するデータがない為、削除権限なし
            if (labelMdl == null) {
                ActionMessage msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(err, msg, "label");
                addErrors(req, err);
                return null;
            }

            //ラベルを削除する
            Sml310ParamModel paramMdl = new Sml310ParamModel();
            paramMdl.setSmlEditLabelId(labelSid);
            Sml310Biz sml310Biz = new Sml310Biz();
            sml310Biz.deleteLabel(con, paramMdl);

            commitFlg = true;

            //ログ出力処理
            SmlAccountDao sacDao = new SmlAccountDao(con);
            SmlAccountModel sacMdl = sacDao.select(sacSid);
            if (sacMdl != null) {
                sacName = sacMdl.getSacName();
            }

            SmlCommonBiz smlBiz = new SmlCommonBiz(con, getRequestModel(req));
            smlBiz.outPutApiLog(req, con, userSid, this.getClass().getCanonicalName(),
                    getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO, "アカウント:"
                                             + sacName + "\n");

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
