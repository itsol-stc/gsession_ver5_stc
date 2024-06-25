package jp.groupsession.v2.cir.cir240;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularSubAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cmn.biz.CommonBiz;

/**
 * <br>[機  能] ラベル登録編集画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir240Action extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir240Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        log__.debug("START");

        ActionForward forward = null;
        Cir240Form thisForm = (Cir240Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        // アクセス権限チェック
        CommonBiz cmnBiz = new CommonBiz();
        CirCommonBiz cirBiz = new CirCommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(
                con,
                getSessionUserModel(req),
                getPluginId());
        if (adminFlg) {
            CirAccountDao dao = new CirAccountDao(con);
            if (!dao.selectExistAccount(thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        } else {
            if (!cirBiz.canUseAccount(
                    con, getSessionUserSid(req), thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        }
        // 編集時ラベル存在チェック
        if (thisForm.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            if (!cirBiz.checkExistLabel(con,
                    thisForm.getCir230EditLabelId(),
                    thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        }

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);
        } else if (cmd.equals("backConfLabel")) {
            //戻るボタンクリック
            forward = map.findForward("backConfLabel");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
    public ActionForward __doInit(ActionMapping map, Cir240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Cir240ParamModel paramMdl = new Cir240ParamModel();
        paramMdl.setParam(form);
        Cir240Biz biz = new Cir240Biz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    public ActionForward __doOK(ActionMapping map, Cir240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        // トランザクショントークン設定
        this.saveToken(req);
        //入力チェック
        ActionErrors errors = form.validate240Check(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        return map.findForward("confirm");
    }
}
