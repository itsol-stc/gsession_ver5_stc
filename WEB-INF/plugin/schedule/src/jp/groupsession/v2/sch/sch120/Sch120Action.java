package jp.groupsession.v2.sch.sch120;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] スケジュール 施設予約一覧POPUP(日間)画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch120Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch120Action.class);

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

        log__.debug("START_SCH120");
        ActionForward forward = null;
        Sch120Form thisForm = (Sch120Form) form;
        __setCanUsePluginFlg(thisForm, req, con);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("today")) {
            //今日ボタン
            __doMoveDays(thisForm, 0, true);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rd")) {
            //次日ボタン
            __doMoveDays(thisForm, 1, false);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_ld")) {
            //前日ボタン
            __doMoveDays(thisForm, -1, false);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("basetime")) {
            //基準時間ボタン
            __doMoveHour(thisForm, 0, sessionUsrSid, con, true, req);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rt")) {
            //３Ｈ＞ボタン
            __doMoveHour(thisForm, 3, sessionUsrSid, con, false, req);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_lt")) {
            //３Ｈ＜ボタン
            __doMoveHour(thisForm, -3, sessionUsrSid, con, false, req);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("reload")) {
            //再読込
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else {
            //スケジュール日間表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_SCH120");
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Sch120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        RequestModel reqMdl = getRequestModel(req);
        Sch120Biz biz = new Sch120Biz(reqMdl);

        Sch120ParamModel paramMdl = new Sch120ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, pconfig);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
    }

    /**
     * <br>表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveDay 移動日数
     * @param today 今日へ移動
     */
    private void __doMoveDays(Sch120Form form, int moveDay, boolean today) {
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getSch010DspDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        form.setSch010DspDate(udate.getDateString());
    }

    /**
     * <br>表示時間の移動を行います
     * @param form アクションフォーム
     * @param moveHour 移動時間数
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param baseFlg 基準時間へ移動
     * @param req HttpServletRequest
     * @throws SQLException SQL実行時例外
     */
    private void __doMoveHour(
            Sch120Form form,
            int moveHour,
            int sessionUsrSid,
            Connection con,
            boolean baseFlg,
            HttpServletRequest req) throws SQLException {
        int dspHour = 0;

        Sch010Biz biz = new Sch010Biz(getRequestModel(req));
        SchPriConfModel confMdl = biz.getPrivateConf(sessionUsrSid, con);
        UDate confDate = confMdl.getSccFrDate();
        if (baseFlg) {
            dspHour = confDate.getIntHour();
        } else {
            dspHour = NullDefault.getInt(
                    form.getSch120FromHour(), confDate.getIntHour());
        }
        dspHour = dspHour + moveHour;
        if (dspHour > 14) {
            dspHour = 14;
        }
        if (dspHour < 0) {
            dspHour = 0;
        }
        form.setSch120FromHour(String.valueOf(dspHour));
    }

    /**
     * 在席管理・ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Sch120Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        con.setAutoCommit(true);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
        con.setAutoCommit(false);
    }
}
