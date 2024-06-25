package jp.groupsession.v2.api.smail.mail.delete;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.sml.sml030.Sml030ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメールを完全削除するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailDeleteAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailDeleteAction.class);

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

        //CommonBiz cmnBiz = new CommonBiz();
        ApiSmlMailDeleteForm form = (ApiSmlMailDeleteForm) aForm;
        GsMessage gsMsg = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);

        int sacSid  = form.getSacSid();
        int smlSid  = form.getSmlSid();
        int userSid = getSessionUserSid(req);

        // アカウントチェック
        ApiSmlMailBiz biz = new ApiSmlMailBiz();
        ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        // メール使用可否チェック
        biz.validateReadSmail(err, con, gsMsg, sacSid, smlSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        // 指定されたメールSIDが送信メールかチェック
        if (!form.isSendMailCheck(con)) {
            // メールSIDが不正
            ActionMessage msg = new ActionMessage("search.data.notfound",
                    gsMsg.getMessage("cmn.shortmail"));
            StrutsUtil.addMessage(err, msg, "smlNotFound");
            addErrors(req, err);
            return null;
        }

        boolean commitFlg = false;

        ActionMessage errMsg = null;

        try {
            Sml030Biz sml030biz = new Sml030Biz(reqMdl);
            Sml030ParamModel paramMdl = new Sml030ParamModel();
            paramMdl.setSml010ProcMode(GSConstSmail.TAB_DSP_MODE_GOMIBAKO); // ゴミ箱の際のみ
            paramMdl.setSmlViewAccount(sacSid);
            paramMdl.setSml010SelectedSid(smlSid);
            paramMdl.setSml010SelectedMailKbn(GSConstSmail.TAB_DSP_MODE_SOSIN);

            //完全に削除可能かチェック
            if (sml030biz.isDeleteAll(paramMdl, con)) {
                // 完全削除処理実行
                sml030biz.allDeleteMessage(paramMdl, reqMdl, con);
            } else {
                errMsg = new ActionMessage("error.alldelete.mail.delete");
            }

            if (errMsg != null) {
                // エラーメッセージあり
            } else if (paramMdl.getErrorsList() != null
                    && paramMdl.getErrorsList().size() > 0
                    && paramMdl.getErrorsList().get(0).length() > 0) {
                // 実行処理によるエラーメッセージあり
                log__.info("LIST UPDATE ERROR END: " + paramMdl.getErrorsList().get(0));
                errMsg = new ActionMessage("errors.free.msg",
                        paramMdl.getErrorsList().get(0));
            } else {
                // エラーなし
                commitFlg = true;
                log__.info("LIST UPDATE COMPLETE");

                //ログ出力処理
                SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
                smlBiz.outPutApiLog(req, con, userSid,  this.getClass().getCanonicalName(),
                        getInterMessage(req, "cmn.sent"), GSConstLog.LEVEL_TRACE, "");
            }
        } catch (SQLException e) {
            log__.error("メッセージの更新に失敗", e);
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        // エラーあり
        if (errMsg != null) {
            StrutsUtil.addMessage(err, errMsg, "mailDeleteAll");
            addErrors(req, err);
            return null;
        }

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
