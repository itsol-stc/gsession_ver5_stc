package jp.groupsession.v2.anp.anp080;


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
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;

/**
 * <br>[機  能] 管理者設定・基本設定画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp080Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp080Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp080Form uform = (Anp080Form) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp080back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);

        } else if (cmd.equals("anp080excute")) {
            //OK
            forward = __doUpdate(map, uform, req, res, con);

        } else if (cmd.equals("anp080group")) {
            //グループコンボボックス選択時、または選択画面からの戻り
            forward = __refresh(map, uform, req, res, con);

        } else if (cmd.equals("anp080knback")) {
            //確認画面からの戻り時
            forward = __refresh(map, uform, req, res, con);
        } else if (cmd.equals("oauthSuccess")) {
            //Oauth認証からの戻り時
            forward = __refresh(map, uform, req, res, con);
        } else {
            //初期化
            if (cmd.equals("backHaisin")) {
                uform.setAnp080BackScreen("1");
            }
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doInit(ActionMapping map, Anp080Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //設定データ取得
        Anp080Biz biz = new Anp080Biz();
        Anp080ParamModel paramModel = new Anp080ParamModel();
        paramModel.setParam(form);
        biz.setConfData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doUpdate(ActionMapping map, Anp080Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("更新");

        //入力チェック
        ActionErrors errors = form.validateAnp080(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        // トランザクショントークン設定
        this.saveToken(req);

        return map.findForward("excute");
    }

    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __refresh(ActionMapping map, Anp080Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期データ取得
        Anp080Biz biz = new Anp080Biz();
        Anp080ParamModel paramModel = new Anp080ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return アクションフォーワード
     */
    private ActionForward __doBack(ActionMapping map, Anp080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        String back = NullDefault.getString(form.getAnp080BackScreen(), "");
        log__.debug("戻る anp080BackScreen = " + back);

        if (back.equals("1")) {
            log__.debug("メイン画面へ遷移");
            return map.findForward("anpimain");
        }

        return map.findForward("back");
    }
}
