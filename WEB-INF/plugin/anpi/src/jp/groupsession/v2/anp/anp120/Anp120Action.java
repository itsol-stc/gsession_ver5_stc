package jp.groupsession.v2.anp.anp120;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <br>[機  能] 管理者設定・緊急連絡先編集画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp120Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp120Action.class);

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
     * @return アクションフォアード
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
                                throws Exception {

        ActionForward forward = null;
        Anp120Form uform = (Anp120Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp120back")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp120excute")) {
            //登録（確認画面へ移動）
            forward = __doExcute(map, uform, req, res, con);

        } else if (cmd.equals("anp120knback")) {
            //確認画面からの戻り
            forward = map.getInputForward();

        } else {
            //初期化
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
    private ActionForward __doInit(ActionMapping map,
                                   Anp120Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {
        //表示データ取得
        Anp120Biz biz = new Anp120Biz();
        Anp120ParamModel paramModel = new Anp120ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録実行（確認画面へ）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doExcute(ActionMapping map,
                                     Anp120Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
                                     throws Exception {

        //入力チェック
        ActionErrors errors = form.validateAnp120(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        // トランザクショントークン設定
        this.saveToken(req);
        return map.findForward("excute");
    }
}
