package jp.groupsession.v2.ptl.ptl070;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.ptl010.Ptl010Biz;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル プレビュー(ポップアップ)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl070Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl070Action.class);

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
        Ptl070Form thisForm = (Ptl070Form) form;


        if (cmd.equals("ptlManager")) {
            //「ポータル管理」クリック
            forward = map.findForward("ptlManager");

        } else if (cmd.equals("getClickUrl")) {
            //aタグクリック時
            __getClickUrl(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = doInit(map, thisForm, req, con);
        }

        return forward;
    }


    /**
     * [機 能] 初期表示
     * [解 説]
     * [備 考]
     * @param map ActionMapping
     * @param form Ptl070Form
     * @param req HttpServletRequest
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward doInit(ActionMapping map, Ptl070Form form,
            HttpServletRequest req, Connection con) throws Exception {


        PluginConfig pconfig = getPluginConfig(req);
        con.setAutoCommit(true);
        //管理者設定をプラグイン設定へ反映し新たにPluginConfigを生成
        PluginConfig mainPconfig
            = getPluginConfigForMain(pconfig, con, getSessionUserSid(req));
        //ログインユーザ情報を取得
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        Ptl070ParamModel paramMdl = new Ptl070ParamModel();
        paramMdl.setParam(form);
        Ptl070Biz biz = new Ptl070Biz();
        biz.setInitData(paramMdl, con, mainPconfig, getServlet().getServletContext(),
                        getRequestModel(req), getAppRootPath());
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

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
            Ptl070Form form,
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

