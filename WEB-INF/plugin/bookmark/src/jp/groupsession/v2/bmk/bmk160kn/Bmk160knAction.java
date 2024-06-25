package jp.groupsession.v2.bmk.bmk160kn;

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
import jp.groupsession.v2.bmk.AbstractBookmarkAdminAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 セキュリティ設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bmk160knAction extends AbstractBookmarkAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk160knAction.class);

    /**
    * <br>[機  能] アクションを実行する
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @return ActionForward
    * @throws Exception 実行時例外
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Bmk160knForm bmkform = (Bmk160knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("bmk160knkakutei")) {
            log__.debug("確定ボタン押下");
            forward = __doKakutei(map, bmkform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("bmk160knback")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("bmk160");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, bmkform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Bmk160knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        ActionForward forward = null;
        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Adr040knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Bmk160knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェックを行う
        ActionErrors errors = null;
        errors = form.validateCheck(req);

        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        ActionForward forward = null;

        //セッションユーザSIDを取得する。
        int sessionUserSid = getSessionUserModel(req).getUsrsid();
        //DB更新
        Bmk160knBiz biz = new Bmk160knBiz();

        Bmk160knParamModel paramMdl = new Bmk160knParamModel();
        paramMdl.setParam(form);
        int addEditFlg = biz.setBmkAconf(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);


        //ログ出力処理
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        String opCode = "";
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        //登録
        if (addEditFlg == GSConstBookmark.BMK_MODE_TOUROKU) {
            msg = gsMsg.getMessage(req, "cmn.entry");
            opCode = msg;
        //編集
        } else {
            msg = gsMsg.getMessage(req, "cmn.change");
            opCode = msg;
        }

        bmkBiz.outPutLog(opCode, GSConstLog.LEVEL_INFO, "", map.getType());

        forward = __setCompPageParam(map, req, form);
        return forward;
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
        Bmk160knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("bmk100");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.security.setting");

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
