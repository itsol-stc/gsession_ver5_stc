package jp.groupsession.v2.rsv.rsv310;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveAction;

/**
 * <br>[機  能] 施設予約 空き状況ポップアップ画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv310Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv310Action.class);

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

        log__.debug("START_Rsv310");
        ActionForward forward = null;
        Rsv310Form thisForm = (Rsv310Form) form;
        __setCanUsePluginFlg(thisForm, req, con);

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
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Rsv310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        RequestModel reqMdl = getRequestModel(req);
        Rsv310Biz biz = new Rsv310Biz(reqMdl);

        Rsv310ParamModel paramMdl = new Rsv310ParamModel();
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
    private void __doMoveDays(Rsv310Form form, int moveDay, boolean today) {
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getRsv310DspDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        form.setRsv310DspDate(udate.getDateString());
    }

    /**
     * スケジュールプラグインが利用可能かフォームへ設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Rsv310Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        con.setAutoCommit(true);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        //スケジュールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstReserve.PLUGIN_ID_SCHEDULE, pconfig)) {
            form.setScheduleUseOk(GSConstReserve.PLUGIN_USE);
        } else {
            form.setScheduleUseOk(GSConstReserve.PLUGIN_NOT_USE);
        }
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConst.PLUGINID_ZSK, pconfig)) {
            form.setZaisekiUseOk(GSConstReserve.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstReserve.PLUGIN_NOT_USE);
        }
        con.setAutoCommit(false);
    }
}
