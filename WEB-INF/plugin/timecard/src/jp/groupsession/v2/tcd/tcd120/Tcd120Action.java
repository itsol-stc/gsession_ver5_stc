package jp.groupsession.v2.tcd.tcd120;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;


/**
 * <br>[機  能] タイムカード 管理者設定 時間帯設定一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd120Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd120Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        ActionForward forward = null;
        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        boolean isAdmin =
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstTimecard.PLUGIN_ID_TIMECARD);
        if (!isAdmin) {
            return getNotAdminSeniPage(map, req);
        }

        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd120Form thisForm = (Tcd120Form) form;

        if (cmd.equals("tcd120_back")) {
            //戻る
            forward = map.findForward("back");
        } else if (cmd.equals("add")) {
            thisForm.setTimezoneCmdMode(GSConst.CMDMODE_ADD);
            //追加ボタン
            forward = map.findForward("add");
        } else if (cmd.equals("edit")) {
            thisForm.setTimezoneCmdMode(GSConst.CMDMODE_EDIT);
            //編集
            forward = map.findForward("add");
        } else if (cmd.equals("deleteTimezone")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            //削除ボタン
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("upTcdzoneData")) {
            //上へボタンクリック
            forward = __setAdmConf(map, thisForm, req, res, con, GSConst.SORT_UP);
        } else if (cmd.equals("downTcdzoneData")) {
            //下へボタン
            forward = __setAdmConf(map, thisForm, req, res, con, GSConst.SORT_DOWN);
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
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        Tcd120Biz biz = new Tcd120Biz(getRequestModel(req));
        Tcd120ParamModel paramMdl = new Tcd120ParamModel();
        paramMdl.setParam(form);
        con.setAutoCommit(true);
        biz._setInitData(paramMdl, con);
        con.setAutoCommit(false);
        paramMdl.setFormData(form);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setAdmConf(
        ActionMapping map,
        Tcd120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //入力チェックを行う
        ActionErrors ereors = form.validateSortCheck(reqMdl);
        if (!ereors.isEmpty()) {
            addErrors(req, ereors);
            return __doInit(map, form, req, res, con);
        }
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;
        Tcd120Biz biz = new Tcd120Biz(reqMdl);
        boolean updateFlg = false;
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        try {
            Tcd120ParamModel paramMdl = new Tcd120ParamModel();
            paramMdl.setParam(form);
            updateFlg = biz._updateSort(con, paramMdl, changeKbn, userSid);
            paramMdl.setFormData(form);
            commitFlg = true;

        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        //ログ出力
        if (updateFlg) {
            //時間帯名称取得
            TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
            int sid = NullDefault.getInt(form.getTcd120SortRadio(), -1);
            TcdTimezoneInfoModel mdl = dao.select(sid);
            String thiName = "";
            if (mdl != null) {
                thiName = mdl.getTtiName();
            }
            GsMessage gsMsg = new GsMessage(reqMdl);
            String opCode = gsMsg.getMessage("fil.77");
            StringBuilder sb = new StringBuilder();
            //時間帯名称
            sb.append("[" + gsMsg.getMessage("tcd.tcd120.01") + "] ");
            sb.append(thiName);
            sb.append(System.getProperty("line.separator"));
            //実行内容
            sb.append("[" + gsMsg.getMessage("wml.282") + "] ");
            if (changeKbn == GSConst.SORT_UP) {
                sb.append(gsMsg.getMessage("tcd.tcd120.06"));
            } else {
                sb.append(gsMsg.getMessage("tcd.tcd120.07"));
            }
            TimecardBiz timBiz = new TimecardBiz();
            timBiz.outPutTimecardLog(
                    map,
                    reqMdl,
                    con,
                    opCode,
                    GSConstLog.LEVEL_TRACE,
                    sb.toString());
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Tcd120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //入力チェックを行う
        ActionErrors ereors = form.validateCheck(reqMdl, con);
        if (!ereors.isEmpty()) {
            addErrors(req, ereors);
            return __doInit(map, form, req, res, con);
        }
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Tcd120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        //入力チェックを行う
        ActionErrors errors = form.validateCheck(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //ログ出力用
        StringBuilder sb = new StringBuilder();
        TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
        int sid = NullDefault.getInt(form.getTcd120SortRadio(), -1);
        TcdTimezoneInfoModel mdl = dao.select(sid);

        //削除実行
        Tcd120ParamModel paramMdl = new Tcd120ParamModel();
        paramMdl.setParam(form);
        Tcd120Biz biz = new Tcd120Biz(reqMdl);
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        biz._deleteTimezone(con, paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ作成
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgState = gsMsg.getMessage("cmn.delete");

        //時間帯名称
        sb.append("[" + gsMsg.getMessage("tcd.tcd120.01") + "] ");
        sb.append(mdl.getTtiName());
        sb.append(System.getProperty("line.separator"));
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(
                map,
                reqMdl,
                con,
                msgState,
                GSConstLog.LEVEL_INFO,
                sb.toString());


        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Tcd120Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(req);

        //キャンセルボタンクリック時遷移先
        ActionForward forward = map.findForward("mine");
        cmn999Form.setUrlCancel(forward.getPath());

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteTimezone");
        //メッセージ
        String msg = "sakujo.kakunin.once";
        String mkey1 = gsMsg.getMessage("tcd.tcd070.01");
        cmn999Form.setMessage(msgRes.getMessage(msg, mkey1));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        form.setHiddenParamTcd120(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Tcd120Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "tcd.tcd070.01")));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
