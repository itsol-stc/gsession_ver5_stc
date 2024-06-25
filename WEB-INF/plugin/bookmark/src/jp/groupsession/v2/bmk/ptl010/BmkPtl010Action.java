package jp.groupsession.v2.bmk.ptl010;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;

/**
 * <p>ポータル グループブックマーク表示用のアクション
 * @author JTS
 */
public class BmkPtl010Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkPtl010Action.class);

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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        BmkPtl010Form bmkForm = (BmkPtl010Form) form;

        if (cmd.equals("prevPage")) {
            log__.debug("前ページクリック");
            bmkForm.setBmkptl010page(bmkForm.getBmkptl010page() - 1);
            forward = __doInit(map, bmkForm, req, con);

        } else if (cmd.equals("nextPage")) {
            log__.debug("次ページクリック");
            bmkForm.setBmkptl010page(bmkForm.getBmkptl010page() + 1);
            forward = __doInit(map, bmkForm, req, con);

        } else {
            log__.debug("初期表示");
            //初期表示
            forward = __doInit(map, bmkForm, req, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        BmkPtl010Form form,
        HttpServletRequest req,
        Connection con
        )
        throws Exception {

        //ログインユーザ情報を取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        con.setAutoCommit(true);
        BmkPtl010Biz biz = new BmkPtl010Biz();

        BmkPtl010ParamModel paramMdl = new BmkPtl010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, buMdl);
        paramMdl.setFormData(form);

        form.setBmkTopUrl(getPluginConfig(req).getPlugin(
                GSConstBookmark.PLUGIN_ID_BOOKMARK).getTopMenuInfo().getUrl()
                + "?bmk010mode=1&bmk010groupSid=" + form.getBmkGrpSid());
        con.setAutoCommit(false);

        return map.getInputForward();
    }
}

