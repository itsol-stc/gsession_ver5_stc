package jp.groupsession.v2.bmk.bmk090;

import java.sql.Connection;
import java.sql.SQLException;

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
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 権限設定(グループブックマーク)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk090Action extends AbstractBookmarkAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk090Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;

        Bmk090Form bmkForm = (Bmk090Form) form;

        //グループ管理者権限判定
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        int gSid = bmkForm.getBmk010groupSid();
        if (!bmkBiz.isGrpAdmin(con, gSid)) {
            //権限エラー
            forward = getNotAdminSeniPage(map, req);
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("bmk090kakunin")) {
            //OKボタンクリック
            forward = __doKakunin(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmk090back")) {
            //戻るボタンクリック
            forward = map.findForward("bmk010");
        } else if (cmd.equals("bmk090back")) {
            //確認画面からの戻り
            forward = __doInitBack(map, bmkForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, bmkForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Bmk090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        //セッションユーザSIDを取得する。
        int sessionUserSid = getSessionUserModel(req).getUsrsid();

        con.setAutoCommit(true);
        Bmk090Biz biz = new Bmk090Biz();

        Bmk090ParamModel paramMdl = new Bmk090ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        
        saveToken(req);

        return map.getInputForward();
    }
    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Bmk090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("確認");

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        
        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(con, req);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        ActionForward forward = null;

        //セッションユーザSIDを取得する。
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        //DB更新
        Bmk090Biz biz = new Bmk090Biz();

        Bmk090ParamModel paramMdl = new Bmk090ParamModel();
        paramMdl.setParam(form);
        biz.setBmkGconf(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        forward = __setCompPageParam(map, req, form);
        return forward;
    }

    /**
     * <br>確認画面からの戻り処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInitBack(ActionMapping map, Bmk090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        return map.getInputForward();
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
        Bmk090Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("bmk010");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.setting.permissions");

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
