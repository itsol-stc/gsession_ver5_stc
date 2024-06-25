package jp.groupsession.v2.zsk.zsk011;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席管理 在席状況登録ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk011Action extends AbstractZaisekiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk011Action.class);

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

        log__.debug("START_Zsk011");
        ActionForward forward = null;

        Zsk011Form thisForm = (Zsk011Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("ok")) {
            log__.debug("追加ボタンクリック");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("cancel")) {
            log__.debug("キャンセルボタンクリック");
            forward = map.findForward("cancel");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Zsk011");
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
        Zsk011Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        // トランザクショントークン設定
        this.saveToken(req);

        int userSid = NullDefault.getInt(form.getUioUpdateUsrSid(), 0);
        ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));

        //更新対象ユーザ情報取得
        UserSearchModel uMdl = zbiz.getZskStatusData(userSid, con);
        String msg = "";
        String userName = "";
        if (uMdl != null) {
            msg = uMdl.getUioComment();
            userName = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
        }
        //更新対象ユーザ名
        form.setZsk011UpdateUserName(userName);
        form.setZsk011UpdateUsrUkoFlg(uMdl.getUsrUkoFlg());
        form.setZsk011Comment(NullDefault.getString(form.getZsk011Comment(), msg));
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 追加ボタンクリック時の処理を行う
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
    private ActionForward __doOk(
        ActionMapping map,
        Zsk011Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateComment(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //在席状況＆コメントを変更
        int userSid = NullDefault.getInt(form.getUioUpdateUsrSid(), 0);
        int status = NullDefault.getInt(form.getZsk011Status(), GSConst.UIOSTS_IN);
        String msg = NullDefault.getString(form.getZsk011Comment(), "");

        boolean commitFlg = false;
        con.setAutoCommit(false);
        ZsjCommonBiz biz = new ZsjCommonBiz(getRequestModel(req));
        try {
            biz.updateUserZskStatus(userSid, status, msg, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("在席ステータスの更新に失敗しました。" + e);
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        GsMessage gsMsg = new GsMessage(req);
        String msg2 = gsMsg.getMessage("cmn.change");

        //ログ出力
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        String opLog = cmnBiz.opLogContent(userSid, status, con);
        cmnBiz.outPutLog(con,
                msg2,
                GSConstLog.LEVEL_TRACE, opLog, map.getType());

        __doInit(map, form, req, res, con);
        form.setCloseFlg(true);
        return map.getInputForward();
    }

}
