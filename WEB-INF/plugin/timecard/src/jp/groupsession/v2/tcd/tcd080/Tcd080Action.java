package jp.groupsession.v2.tcd.tcd080;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAction;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 個人設定 基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd080Action extends AbstractTimecardAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd080Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
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

        ActionForward forward = null;
        Tcd080Form schForm = (Tcd080Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("tcd080ok")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("tcd080commit")) {
            log__.debug("登録処理実行");
            //登録 戻る
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("tcd080back")) {
            //戻る
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, schForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 確認処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Tcd080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("確認");

        ActionErrors errors = form.validateCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doReLoad(map, form, req, res, con);
            return map.getInputForward();
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
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
        Tcd080Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("tcd080commit").getPath());
        cmn999Form.setUrlCancel(map.findForward("tcd080cancel").getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.tcd050.07");

        //メッセージセット
        String msgState = "edit.kakunin.once";
        String mkey1 = msg;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("tcd080DefFrH", form.getTcd080DefFrH());
        cmn999Form.addHiddenParam("tcd080DefFrM", form.getTcd080DefFrM());
        cmn999Form.addHiddenParam("tcd080DefToH", form.getTcd080DefToH());
        cmn999Form.addHiddenParam("tcd080DefToM", form.getTcd080DefToM());
        cmn999Form.addHiddenParam("tcd080mainDsp", form.getTcd080mainDsp());
        cmn999Form.addHiddenParam("tcd080kinmuOutput", form.getTcd080kinmuOutput());
        cmn999Form.addHiddenParam("tcd080zaisekiSts", form.getTcd080zaisekiSts());

        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("usrKbn", form.getUsrKbn());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Tcd080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("更新");

        //２重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        boolean commit = false;
        Tcd080ParamModel paramMdl = new Tcd080ParamModel();
        paramMdl.setParam(form);
        int workreportKbn = 0;
        try {
            Tcd080Biz biz = new Tcd080Biz();
            workreportKbn = biz.updatePriConf(paramMdl, getSessionUserSid(req), con, getRequestModel(req));
            paramMdl.setFormData(form);
            commit = true;
        } catch (SQLException e) {
            log__.error("タイムカード基本設定の更新に失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");

        //ログ出力
        TimecardBiz cBiz = new TimecardBiz(getRequestModel(req));
        String value = "";
        // 時間
        value += "[" + gsMsg.getMessage("cmn.time") + "]\r\n";
        value += gsMsg.getMessage("cmn.starttime") + ":";
        value += paramMdl.getTcd080DefFrH() + "時" + paramMdl.getTcd080DefFrM() + "分";
        value += "\r\n";
        value += gsMsg.getMessage("cmn.endtime") + ":";
        value += paramMdl.getTcd080DefToH() + "時" + paramMdl.getTcd080DefToM() + "分";
        // メイン画面設定
        value += "\r\n[" + gsMsg.getMessage("tcd.tcd080.11") + "] ";
        String[] dsp = {
                gsMsg.getMessage("cmn.dont.show"),
                gsMsg.getMessage("cmn.display.ok")
        };
        value += dsp[paramMdl.getTcd080mainDsp()];
        // 在席管理
        value += "\r\n[" + gsMsg.getMessage("cmn.zaiseki.management") + "] ";
        String[] zsk = {
                gsMsg.getMessage("tcd.tcd080.02"),
                gsMsg.getMessage("tcd.tcd080.01")
        };
        value += zsk[Integer.parseInt(paramMdl.getTcd080zaisekiSts())];
        // 勤務表形式
        if (workreportKbn == 0) {
            value += "\r\n[" + gsMsg.getMessage("tcd.tcd080.08") + "] ";
            String[] export = {
                    gsMsg.getMessage("tcd.tcd080.15"),
                    gsMsg.getMessage("tcd.tcd080.14")
            };
            value += export[paramMdl.getTcd080kinmuOutput()];
        }
        cBiz.outPutTimecardLog(map, reqMdl, con, edit, GSConstLog.LEVEL_INFO, value);

        //共通メッセージ画面(OK)を表示
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
        Tcd080Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("Tcd080back");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.tcd050.07");

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        String key1 = msg;
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("usrKbn", form.getUsrKbn());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Tcd080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        return map.findForward("Tcd080back");
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);

        Tcd080ParamModel paramMdl = new Tcd080ParamModel();
        paramMdl.setParam(form);
        Tcd080Biz biz = new Tcd080Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doReLoad(ActionMapping map, Tcd080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        Tcd080ParamModel paramMdl = new Tcd080ParamModel();
        paramMdl.setParam(form);
        Tcd080Biz biz = new Tcd080Biz();
        biz.setLabel(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}
