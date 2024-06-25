package jp.groupsession.v2.cht.cht090kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.cht080.Cht080Form;
import jp.groupsession.v2.cht.cht090.Cht090Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] チャット 特例アクセス登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht090knAction extends AbstractChatAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht090knAction.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        Cht090knForm chtForm = (Cht090knForm) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("cht090knback")) {
            forward = map.findForward("cht090");
        } else if (cmd.equals("doRegister")) {
            forward = __doRegister(map, chtForm, req, res, con);
        } else {
            forward = __doInit(map, chtForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Cht090knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht090knParamModel paramMdl = new Cht090knParamModel();
        paramMdl.setParam(form);
        Cht090knBiz biz = new Cht090knBiz();
        biz.setInitData(con, paramMdl, getRequestModel(req), getTempPath(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doRegister(ActionMapping map, Cht090knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //特例アクセス存在チェック
        if (form.getCht080editMode() == 1) {
            if (!form.existChtSpAccess(con, form.getCht080editData())) {
                return __doNoneDataError(map, req, form);
            }
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        boolean commit = false;
        try {
            Cht090knParamModel paramMdl = new Cht090knParamModel();
            paramMdl.setParam(form);

            RequestModel reqMdl = getRequestModel(req);
            Cht090knBiz biz = new Cht090knBiz();
            biz.registerAccessData(con, paramMdl, getCountMtController(req),
                                            reqMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("特例アクセス情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        String opCode = "";
        if (form.getCht080editMode() == GSConst.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else {
            opCode = getInterMessage(req, "cmn.change");
        }

        //ログ出力
        ChtBiz chtBiz = new ChtBiz(con, getRequestModel(req));
        chtBiz.outPutLog(map, opCode, GSConstLog.LEVEL_INFO,
                "[特例アクセス名]" + form.getCht090name(),
                getRequestModel(req), "");


        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return アクションフォーム
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Cht090knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("cht080");

        ((Cht090Form) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getCht080editMode() == GSConstWebmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getCht080editMode() == GSConstWebmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(
                msgRes.getMessage(msgState, getInterMessage(req, "schedule.sch230.02")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除対象が存在しない場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(
        ActionMapping map,
        HttpServletRequest req,
        Cht080Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cht080");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("cht.cht080.02");
        String textOperation = gsMsg.getMessage("cmn.delete");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                                        enqName, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}

