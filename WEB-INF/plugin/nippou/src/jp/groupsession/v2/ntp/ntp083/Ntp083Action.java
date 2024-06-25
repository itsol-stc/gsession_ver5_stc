package jp.groupsession.v2.ntp.ntp083;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 手動データ削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp083Action extends AbstractNippouAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp083Action.class);

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

        Ntp083Form ntpForm = (Ntp083Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp083kakunin")) {
            //確認
            forward = __doKakunin(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp083delete")) {
            //削除
            forward = __doDelete(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp083back")) {
            //戻るボタンクリック
            forward = __doBack(map, ntpForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Ntp083Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("削除処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        boolean commit = false;
        //DB更新
        try {
            BaseUserModel umodel = getSessionUserModel(req);
            Ntp083Biz biz = new Ntp083Biz(con, getRequestModel(req));

            Ntp083ParamModel paramMdl = new Ntp083ParamModel();
            paramMdl.setParam(form);
            biz.deleteNippou(paramMdl, umodel);
            paramMdl.setFormData(form);

            commit = true;
        } catch (SQLException e) {
            log__.error("日報の削除に失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 削除 **/
        String delete = gsMsg.getMessage(req, "cmn.delete");
        //ログ出力処理
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        StringBuilder values = new StringBuilder();
        values.append("[year]" + form.getNtp083DelYear());
        values.append("[month]" + form.getNtp083DelMonth());
        ntpBiz.outPutLog(
                map, req, res,
                delete, GSConstLog.LEVEL_INFO, values.toString());

        //共通メッセージ画面(OKオンリー)を表示
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp083Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ktool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";
        String key1 = "日報情報";
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
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
    private ActionForward __doKakunin(ActionMapping map, Ntp083Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("確認処理");

        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp083Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "sakujo.kakunin.once";
        String mkey1 = form.getNtp083DelYear() + "年" + form.getNtp083DelMonth() + "ヶ月"
                       + "以上経過した日報データ";
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));
        cmn999Form.addHiddenParam("ntp083DelYear", form.getNtp083DelYear());
        cmn999Form.addHiddenParam("ntp083DelMonth", form.getNtp083DelMonth());

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Ntp083Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("ntp083back");
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
    private ActionForward __doInit(ActionMapping map, Ntp083Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        return map.getInputForward();
    }
}