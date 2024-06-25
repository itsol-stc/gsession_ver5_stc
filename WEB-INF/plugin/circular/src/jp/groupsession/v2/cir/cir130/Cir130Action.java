package jp.groupsession.v2.cir.cir130;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 回覧板 個人設定 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir130Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir130Action.class);

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
        Cir130Form thisForm = (Cir130Form) form;

        thisForm.setCirViewUser(getRequestModel(req).getSmodel().getUsrsid());

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("Cir130ACtion CMD==>" + cmd);

        //OKボタン押下
        if (cmd.equals("init_change_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("backKtool")) {
            log__.debug("戻るボタン押下");
            return map.findForward("backKtool");

        } else if (cmd.equals("memoKbnChange")) {
            log__.debug("メモ欄修正期限ラジオクリック");
            forward = __doReDsp(map, thisForm, req, res, con);

        } else if (cmd.equals("cir130reload")) {
            log__.debug("確認画面からのバック時の再表示");
            forward = __doReDsp(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);

        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir130Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        int userSid = getSessionUserSid(req);

        con.setAutoCommit(true);
        Cir130ParamModel paramMdl = new Cir130ParamModel();
        paramMdl.setParam(form);
        Cir130Biz biz = new Cir130Biz();
        biz.setInitDataDsp(paramMdl, con, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説] 確認画面からの戻ってきた場合の再表示
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doReDsp(
        ActionMapping map,
        Cir130Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        return map.getInputForward();
    }



    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward 画面遷移先
     */
    private ActionForward __doOk(ActionMapping map,
                                  Cir130Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con) throws Exception {


        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("init_change_kakunin");
    }
}
