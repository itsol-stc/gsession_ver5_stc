package jp.groupsession.v2.fil.ptl030;

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
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.man.GSConstPortal;

/**
 * <p>ポータル キャビネット一覧画面アクション
 * @author JTS
 */
public class FilPtl030Action extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl030Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstPortal.PLUGIN_ID;
    }

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        FilPtl030Form ptlForm = (FilPtl030Form) form;

        if (cmd.equals("selectCabinet")) {
            //キャビネット名クリック

            //フォルダ情報一覧へ遷移
            forward = map.findForward("fil040");

        } else {
            //初期表示
            forward = __doInit(map, ptlForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        FilPtl030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {
        FilPtl030Biz biz = new FilPtl030Biz(con, getRequestModel(req));

        FilPtl030ParamModel paramMdl = new FilPtl030ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

}

