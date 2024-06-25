package jp.groupsession.v2.sch.sch088;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール ショートメール通知設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch088Action extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch088Action.class);

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

        Sch088Form schForm = (Sch088Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("sch088commit")) {
            //登録
            forward = __doCommit(map, schForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, schForm, req, res, con);
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
    private ActionForward __doCommit(ActionMapping map, Sch088Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("登録");
        ActionForward forward = null;

        if (!__canUseSmlConf(form, req, con)) {
            return getSubmitErrorPage(map, req);
        }

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel umodel = getSessionUserModel(req);
        Sch088Biz biz = new Sch088Biz(getRequestModel(req));

        Sch088ParamModel paramMdl = new Sch088ParamModel();
        paramMdl.setParam(form);
        biz.setPconfSetting(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        String value = "";
        value += "[" +  gsMsg.getMessage("shortmail.notification") + "] ";
        String[] setting = {
                gsMsg.getMessage("cmn.set.eachuser"),
                gsMsg.getMessage("cmn.set.the.admin")
        };
        value += setting[Integer.parseInt(paramMdl.getSch088smlSendKbn())];
        if (Integer.parseInt(paramMdl.getSch088smlSendKbn())
                == GSConstSchedule.SMAIL_SEND_KBN_ADMIN) {
            String[] notify = {
                    gsMsg.getMessage("cmn.dont.notify"),
                    gsMsg.getMessage("cmn.notify")
            };
            value += "\r\n";
            value += gsMsg.getMessage("schedule.sch088.2") + ":";
            value += notify[Integer.parseInt(paramMdl.getSch088Smail())];
            value += "\r\n";
            value += gsMsg.getMessage("schedule.sch088.1") + ":";
            value += notify[Integer.parseInt(paramMdl.getSch088SmailGroup())];
            value += "\r\n";
            value += gsMsg.getMessage("schedule.sch088.3") + ":";
            value += notify[Integer.parseInt(paramMdl.getSch088SmailAttend())];

        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("schedule.108") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");
        schBiz.outPutLogNoDspName(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, value, null, dspName);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch088Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        if (!__canUseSmlConf(form, req, con)) {
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        con.setAutoCommit(true);
        Sch088Biz biz = new Sch088Biz(getRequestModel(req));

        Sch088ParamModel paramMdl = new Sch088ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl,  con);
        paramMdl.setFormData(form);
        //トランザクショントークン設定
        
        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] ショートメール設定が利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private boolean __canUseSmlConf(Sch088Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }
}
