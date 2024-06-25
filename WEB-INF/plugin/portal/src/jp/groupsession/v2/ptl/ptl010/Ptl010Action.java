package jp.groupsession.v2.ptl.ptl010;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.ptl.AbstractPortalAction;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] ポータルトップ画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl010Action extends AbstractPortalAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl010Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return false;
    }

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Ptl010Form ptlForm = (Ptl010Form) form;

        if (cmd.equals("ktools")) {
            //管理者設定ボタンクリック
            forward = map.findForward("ktools");

        } else if (cmd.equals("pset")) {
            //個人設定ボタンクリック
            forward = map.findForward("pset");

        } else if (cmd.equals("portalSetting")) {
            //ポータル設定
            forward  = map.findForward("portalSetting");

        } else if (cmd.equals("cirConf")) {
            //回覧板アクション
            forward = map.findForward("cir020");

        } else if (cmd.equals("smlCheck")) {
            //ショートメール
            forward = map.findForward("smlCheck");

        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward  = map.findForward("month");

        } else if (cmd.equals("infoSetting")) {
            //インフォメーション一覧
            forward  = map.findForward("infoSetting");

        } else if (cmd.equals("getClickUrl")) {
            //aタグクリック時
            __getClickUrl(map, ptlForm, req, res, con);

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
     * @throws Exception 実行例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ptl010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        ) throws Exception, GSException {

        con.setAutoCommit(true);

        BaseUserModel usModel = getSessionUserModel(req);
        if (usModel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        PluginConfig pconfig = getPluginConfig(req);
        PluginConfig mainPconfig = getPluginConfigForMain(pconfig, con, usModel.getUsrsid());

        Ptl010ParamModel paramMdl = new Ptl010ParamModel();
        paramMdl.setParam(form);
        Ptl010Biz biz = new Ptl010Biz();
        boolean mainFlg = biz.setInitData(paramMdl, con, usModel, mainPconfig,
                req, getServlet().getServletContext(), getRequestModel(req), getAppRootPath());
        paramMdl.setFormData(form);

        if (mainFlg) {
            return map.findForward("gsmain");
        }

        con.setAutoCommit(false);

        boolean commit = true;
        try {
            //自動リロード時間を設定する。
            biz.setReloadTime(paramMdl, con, usModel.getUsrsid());
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("自動リロード時間の設定に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] HTML入力ポートレットのリンククリック時のURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __getClickUrl(ActionMapping map,
            Ptl010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {


        //コマンドパラメータ取得
        String url = NullDefault.getString(req.getParameter("url"), "");

        JSONObject jsonData = null;

        if (!StringUtil.isNullZeroStringSpace(url)) {

            Ptl010Biz biz = new Ptl010Biz();
            jsonData = biz.getClickUrl(con, getRequestModel(req), url, getAppRootPath());
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(HTML入力ポートレットのリンククリック時URLデータ取得)");
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}

