package jp.groupsession.v2.rss.main;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.rss.AbstractRssAction;

/**
 * <br>[機  能] RSSリーダー(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssMainAction extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssMainAction.class);

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

        log__.debug("RssMainAction start");
        ActionForward forward = null;

        RssMainForm thisForm = (RssMainForm) form;

        //初期表示
        forward = __doInit(map, thisForm, req, res, con);

        log__.debug("RssMainAction end");
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
    private ActionForward __doInit(
        ActionMapping map,
        RssMainForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(false);
        //初期表示情報を画面にセットする
        RssMainParamModel paramMdl = new RssMainParamModel();
        paramMdl.setParam(form);
        MlCountMtController cntCon = getCountMtController(req);
        RssMainBiz biz = new RssMainBiz(con);
        biz.setInitData(paramMdl, userSid, getRequestModel(req), cntCon);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

}
