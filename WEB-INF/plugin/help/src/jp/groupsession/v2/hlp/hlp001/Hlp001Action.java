package jp.groupsession.v2.hlp.hlp001;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ヘルプ フレーム(ヘッダーとボディの)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp001Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Hlp001Action.class);

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
     * <p>
     * セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>
     * サブクラスでこのメソッドをオーバーライドして使用する
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
        Hlp001Form cmnForm = (Hlp001Form) form;
        forward = __doDisp(map, cmnForm, req, res, con);
        return forward;
    }

    /**
     * <p>表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doDisp(ActionMapping map, Hlp001Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("start");

        ActionForward forward = map.getInputForward();

        //入力チェック
        PluginConfig pconfig = getPluginConfig(req);
        ActionErrors errors = form.validateBodyUrl(pconfig);
        if (errors.size() > 0) {
            log__.debug("エラー " + errors.size());
            addErrors(req, errors);
            //デフォルトURLをセット
            form.setUrl(__getDefaultUrl(map));
        }

        //未入力の場合
        if (form.getUrl() == null || form.getUrl().length() <= 0) {
            //デフォルトURLをセット
            form.setUrl(__getDefaultUrl(map));
        }
        log__.debug("BODY URL is " + form.getUrl());
        return forward;
    }

    /**
     * BodyデフォルトのURLを返す
     * @param map アクションマッピング
     * @return デフォルトURL
     */
    private String __getDefaultUrl(ActionMapping map) {
        ActionForward defforward = map.findForward("main");
        String url = ".." + defforward.getPath();
        return url;
    }
}
