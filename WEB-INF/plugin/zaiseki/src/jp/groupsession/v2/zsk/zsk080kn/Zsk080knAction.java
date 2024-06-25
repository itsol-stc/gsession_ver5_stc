package jp.groupsession.v2.zsk.zsk080kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
/**
 * <br>[機  能] 在席管理 個人設定 初期表示設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk080knAction extends AbstractZaisekiAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk080knAction.class);

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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Zsk080knForm zskForm = (Zsk080knForm) form;
        if (cmd.equals("zsk080")) {
            //戻る
            forward = map.findForward("zsk080");
        } else if (cmd.equals("zsk080commit")) {
            //完了
            forward = __doCommit(map, zskForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk080knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Zsk080knBiz biz = new Zsk080knBiz();

        Zsk080knParamModel paramMdl = new Zsk080knParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * 個人設定登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL例外
     * @return Forward
     * @throws IOToolsException SQLエラー
     */
    private ActionForward __doCommit(ActionMapping map, Zsk080knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOToolsException {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        boolean commitFlg = false;
        con.setAutoCommit(false);
        Zsk080knBiz biz = new Zsk080knBiz();
        try {

            Zsk080knParamModel paramMdl = new Zsk080knParamModel();
            paramMdl.setParam(form);
            biz.zaisekiPriConfUpdate(paramMdl, sessionUsrSid, con);
            paramMdl.setFormData(form);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("在席管理個人設定の更新・登録に失敗しました。" + e);
            e.printStackTrace();
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.edit");

        //ログ出力
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        int zifSid = NullDefault.getInt(form.getDfZifSid(), 0);
        String targetName = biz.getLogMessage(zifSid, con);
        cmnBiz.outPutLog(con,
                msg, GSConstLog.LEVEL_INFO, "[" + gsMsg.getMessage("zsk.29") + "]" + targetName,
                map.getType());
        //完了画面へ遷移
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
        Zsk080knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);

        //メッセージセット
        String msgState = null;
        urlForward = map.findForward("zsk080upcomp");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String message = gsMsg.getMessage(req, "zsk.66");
        msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, message));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
