package jp.groupsession.v2.prj.main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj010.Prj010Biz;

/**
 * <br>[機  能] プロジェクト(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjMainAction extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjMainAction.class);

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

        log__.debug("PrjMainAction start");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        PrjMainForm thisForm = (PrjMainForm) form;

        if (cmd.equals("getImageFile")) {
            log__.debug("画像ダウンロード");
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("PrjMainAction end");
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        PrjMainForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //初期表示情報を画面にセットする
        PrjMainBiz biz = new PrjMainBiz(con, getRequestModel(req));
        PrjMainParamModel paramMdl = new PrjMainParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, userSid);
        paramMdl.setFormData(form);



        form.setPrjTopUrl(getPluginConfig(req).getPlugin(
                GSConstProject.PLUGIN_ID_PROJECT).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            PrjMainForm form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;
        //画像バイナリSIDとプロジェクトSIDの照合チェック
        Prj010Biz prj010Biz = new Prj010Biz(con, getRequestModel(req));
        boolean icoHnt = prj010Biz.cheIcoHnt(form.getPrjMainPrjSid(), form.getPrjMainPrjBinSid());

        if (!icoHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getPrjMainPrjBinSid(),
                    GroupSession.getResourceManager().getDomain(req));
        }

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }

}
