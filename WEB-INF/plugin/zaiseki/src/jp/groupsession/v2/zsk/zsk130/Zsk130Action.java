package jp.groupsession.v2.zsk.zsk130;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;

/**
 * <br>[機  能] 在席管理 個人設定 座席表メンバー表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk130Action extends AbstractGsAction {

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {


        ActionForward forward = null;
        Zsk130Form zskForm = (Zsk130Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("zsk130kakunin")) {
            //OK
            forward = __doKakunin(map, zskForm, req, res, con);
        } else if (cmd.equals("backZsk130")) {
            //確認画面からの戻り
            forward = __doBackInit(map, zskForm, req, res, con);
        } else if (cmd.equals("zsk130back")) {
            //戻る
            forward = __doBack(map, zskForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, zskForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Zsk130Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {
        con.setAutoCommit(true);
        Zsk130Biz biz = new Zsk130Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Zsk130ParamModel paramMdl = new Zsk130ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con, true);
        paramMdl.setFormData(form);

        ActionForward forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] 確認画面からの戻り
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doBackInit(ActionMapping map,
                                       Zsk130Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {
        Zsk130Biz biz = new Zsk130Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Zsk130ParamModel paramMdl = new Zsk130ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con, false);
        paramMdl.setFormData(form);

        ActionForward forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタン押下
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     */
    private ActionForward __doBack(ActionMapping map,
                                    Zsk130Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = null;
        int mode = form.getZsk130Mode();
        if (mode == GSConstZaiseki.MODE_MAIN) {
            forward = map.findForward("gf_main");
        } else {
            forward = map.findForward("zsk070");
        }

        return forward;
    }

    /**
     * <br>[機  能] 確認ボタン押下
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     */
    private ActionForward __doKakunin(ActionMapping map,
                                       Zsk130Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) {
        saveToken(req);

        return map.findForward("zsk130kn");

    }
}