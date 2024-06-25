package jp.groupsession.v2.hlp.hlp000top;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ヘルプ トップメニューのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp000topAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Hlp000topAction.class);

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
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <p>
     * アクション実行
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
        Hlp000topForm myForm = (Hlp000topForm) form;

        if (myForm == null) {
            myForm = new Hlp000topForm();
        }

        __doInit(map, myForm, req, res, con);

        forward = map.getInputForward();
        return forward;
    }


    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Hlp000topForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        log__.debug("初期表示");
        ActionForward forward = null;

        Hlp000topParamModel paramMdl = new Hlp000topParamModel();
        paramMdl.setParam(form);

        PluginConfig pconfig = getPluginConfig(req);
        String contextPath = IOTools.setEndPathChar(req.getContextPath());
        Hlp000topBiz myBiz = new Hlp000topBiz();
        myBiz.getInitData(paramMdl, pconfig, con, contextPath,
                        getRequestModel(req));

        forward = map.getInputForward();
        return forward;
    }

}
