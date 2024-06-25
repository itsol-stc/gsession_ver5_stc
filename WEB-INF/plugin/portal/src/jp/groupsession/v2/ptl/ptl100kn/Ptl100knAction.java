package jp.groupsession.v2.ptl.ptl100kn;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.ptl.ptl010.Ptl010Biz;
import jp.groupsession.v2.ptl.ptl100.Ptl100Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] ポータル ポートレット登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl100knAction extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl100knAction.class);

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

        Ptl100knForm thisForm = (Ptl100knForm) form;
        if (cmd.equals("ptl100knOk")) {
            log__.debug("*** 確定。");
            forward = __doKakutei(map, thisForm, req, res, con);

        } else if (cmd.equals("ptl100knBack")) {
            //戻るボタンクリック
            forward = map.findForward("backAddPortlet");

        } else if (cmd.equals("getClickUrl")) {
            //aタグクリック時
            __getClickUrl(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }
    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Ptl100knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //確認画面への直接アクセスを禁止する
        if (!isTokenValid(req, false)) {
            log__.error("ポートレット登録確認画面への直接アクセス");
            return getSubmitErrorPage(map, req);
        }
        con.setAutoCommit(true);
        Ptl100knParamModel paramMdl = new Ptl100knParamModel();
        paramMdl.setParam(form);
        Ptl100knBiz biz = new Ptl100knBiz(con);
        biz.initDsp(paramMdl, getRequestModel(req), getAppRootPath());
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
     }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng090knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Ptl100knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validatePtl100(reqMdl, getAppRootPath());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        //ログインユーザ情報を取得
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        try {
            Ptl100knParamModel paramMdl = new Ptl100knParamModel();
            paramMdl.setParam(form);
            Ptl100knBiz biz = new Ptl100knBiz(con);
            //ポートレット情報の登録
            biz.registPtl(
                    paramMdl,
                    getCountMtController(req),
                    getAppRootPath(),
                    reqMdl,
                    form.getPtlCmdMode());
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポートレット情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //テンポラリディレクトリ削除
        Ptl100Biz biz100 = new Ptl100Biz();
        biz100.deletePortletTempDir(getRequestModel(req));

        GsMessage gsMsg = new GsMessage(reqMdl);
        String entry = gsMsg.getMessage("cmn.entry");
        String edit = gsMsg.getMessage("cmn.edit");
        String msg = "[name]" + NullDefault.getString(form.getPtl100name(), "");

        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        String opCode = "";

        if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
            opCode = entry;
        } else if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
            opCode = edit;
        }

        ptlBiz.outPutLog(
                map, reqMdl, opCode,
                GSConstLog.LEVEL_INFO,
                msg);

        return __setCompPageParam(map, req, form);
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ptl100knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backPortletList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        GsMessage gsMsg = new GsMessage();
        String portlet = gsMsg.getMessage(req, "ptl.3");
        cmn999Form.setMessage(msgRes.getMessage(msgState, portlet));

        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptl090category", form.getPtl090category());
        cmn999Form.addHiddenParam("ptl090svCategory", form.getPtl090svCategory());
        cmn999Form.addHiddenParam("ptl090sortPortlet", form.getPtl090sortPortlet());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
            Ptl100knForm form,
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

