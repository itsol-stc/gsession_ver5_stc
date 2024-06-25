package jp.groupsession.v2.fil.ptl050;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.man.GSConstPortal;

/**
 * <p>
 * ポータル ファイル一覧管理 キャビネット選択画面Action
 *
 * @author JTS
 */
public class FilPtl050Action extends AbstractFileAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl050Action.class);

    /**
     * プラグインIDを取得します
     *
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstPortal.PLUGIN_ID;
    }

    /**
     * <br>
     * [機 能] アクション実行 <br>
     * [解 説] コントローラの役割を担います <br>
     * [備 考]
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
        log__.debug("START");

        ActionForward forward = null;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        FilPtl050Form ptlForm = (FilPtl050Form) form;

        if (cmd.equals("selectCabinet")) {
            // キャビネット名クリック
            // プラグインポートレット フォルダ情報一覧へ遷移
            forward = map.findForward("filptl060");

        } else if (cmd.equals("backList")) {
            // 閉じるボタンクリック
            forward = map.findForward("ptlList");

        } else if (cmd.equals("filchangeCombo")) {
            // プラグインポートレットコンボ変更
            forward = __changeCombo(map, ptlForm, req, res, con);

        } else {
            // 初期表示
            forward = __doInit(map, ptlForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>
     * [機 能] 初期表示を行う <br>
     * [解 説] <br>
     * [備 考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doInit(ActionMapping map, FilPtl050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        FilPtl050Biz biz = new FilPtl050Biz(con, getRequestModel(req));

        FilPtl050ParamModel paramMdl = new FilPtl050ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }


    /**
     * <br>
     * [機 能] プラグインポートレットコンボ変更時の処理 <br>
     * [解 説] <br>
     * [備 考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __changeCombo(ActionMapping map, FilPtl050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        String screenId = form.getPtl080PluginPortlet();
        if (StringUtil.isNullZeroString(screenId)) {
            return map.getInputForward();
        }

        return map.findForward(screenId);

    }
}
