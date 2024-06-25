package jp.groupsession.v2.rsv.rsv140;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;

/**
 * <br>[機  能] 施設予約 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv140Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv140Action.class);

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Rsv140Form rsvform = (Rsv140Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("back_to_menu")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, rsvform, req, res, con);
        //初期値設定リンククリック
        } else if (cmd.equals("syokiti_settei")) {
            log__.debug("初期値設定リンククリック");
            forward = map.findForward("syokiti_settei");
        //表示設定リンククリック
        } else if (cmd.equals("hyozi_settei")) {
            log__.debug("表示設定リンククリック");
            forward = map.findForward("hyozi_settei");
        //施設予約メイン表示設定リンククリック
        } else if (cmd.equals("main_settei")) {
            log__.debug("施設予約メイン表示設定リンククリック");
            forward = map.findForward("main_settei");
            //施設予約ショートメール通知設定リンククリック
        } else if (cmd.equals("smail_settei")) {
            log__.debug("施設予約ショートメール通知設定リンククリック");
            forward = map.findForward("smail_settei");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
            Rsv140Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException {
        con.setAutoCommit(true);

        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        form.setRsv140canEditDtm(rsvBiz.canEditShowHoursDays(con));
        RsvAdmConfModel mdl = rsvBiz.getRsvAdminData(con, getSessionUserSid(req));
        form.setRsv140SmailSendKbn(mdl.getRacSmailSendKbn());
        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig)) {
            form.setRsv140SmailSendKbn(GSConstReserve.RAC_SMAIL_SEND_KBN_ADMIN);
        }
        // 初期値設定表示判定
        if(!rsvBiz.isAccessUnconfInitSetting(mdl)) {
            form.setRsv140canEditIvs(false);
        } else {
            form.setRsv140canEditIvs(true);
        }
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻るボタン処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map,
                                    Rsv140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = null;

        String backPgId =
            NullDefault.getStringZeroLength(
                    form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

        //予約一覧[週間]画面へ戻る
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            forward = map.findForward("mainPriSetting");
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            forward = map.findForward("back_to_syukan");
        //予約一覧[日間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            forward = map.findForward("back_to_nikkan");
        //予約一覧[月間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            forward = map.findForward("back_to_gekkan");
        //施設利用状況照会画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV100)) {
            forward = map.findForward("back_to_itiran");
        }

        return forward;
    }
}