package jp.groupsession.v2.prj.ptl011;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.http.BrowserUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;

/**
 * <br>[機  能] プロジェクト管理 ポートレット TODO状態内訳のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl011Action extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPtl011Action.class);

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
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("start");
        ActionForward forward = null;

        PrjPtl011Form thisForm = (PrjPtl011Form) form;

        log__.debug("初期表示");
        forward = __doInit(map, thisForm, req, res, con);

        log__.debug("end");
        return forward;
    }

    /**
     * <br>[機  能] 初期処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws GSAuthenticateException 実行例外
     */
    private ActionForward __doInit(
        ActionMapping map,
        PrjPtl011Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSAuthenticateException {

        con.setAutoCommit(true);

        //ログインユーザ情報を取得
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        //初期表示情報を画面にセットする
        PrjPtl011Biz biz = new PrjPtl011Biz(con, getRequestModel(req));

        PrjPtl011ParamModel paramMdl = new PrjPtl011ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, userMdl);
        paramMdl.setFormData(form);

        form.setPrjTopUrl(GSConstProject.DSP_TODO_URL);

        //ブラウザの判定
        if (BrowserUtil.isIe(req)) {
            form.setPrj010IeFlg(1);
        }

        return map.getInputForward();
    }
}