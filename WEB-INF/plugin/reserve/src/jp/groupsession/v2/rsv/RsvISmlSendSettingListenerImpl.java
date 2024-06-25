package jp.groupsession.v2.rsv;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.rsv300.Rsv300Biz;
import jp.groupsession.v2.rsv.rsv300.Rsv300Form;
import jp.groupsession.v2.rsv.rsv300.Rsv300ParamModel;
import jp.groupsession.v2.rsv.rsv320.Rsv320Biz;
import jp.groupsession.v2.rsv.rsv320.Rsv320Form;
import jp.groupsession.v2.rsv.rsv320.Rsv320ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvISmlSendSettingListenerImpl implements ISmlSendSettingListener {

    /**
     * <p>管理者設定ショートメール通知設定画面表示時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 使用権限
     */
    public boolean doAdminSettingDisp(Connection con,  HttpServletRequest req, 
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //掲示板は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RESERVE, pconfig)) {
            return false;
        }

        Rsv320Biz biz = new Rsv320Biz(reqMdl, con);
        Rsv320Form rsvForm = new Rsv320Form();
        Rsv320ParamModel paramMdl = new Rsv320ParamModel();
        paramMdl.setParam(rsvForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv320Form", rsvForm);
        return true;
    }
    
    /**
     * <p>管理者設定ショートメール通知設定画面更新時に実行される
     * <p>入力値チェックを行う
     * @param req
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doAconfValidate(HttpServletRequest req, RequestModel reqMdl) {
        
        Rsv320Form rsvForm = new Rsv320Form();
        Rsv320ParamModel paramMdl = new Rsv320ParamModel();
        paramMdl.setRsv320SmailSendKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv320SmailSendKbn"), "0")));
        paramMdl.setRsv320SmailSend(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv320SmailSend"), "0")));
        paramMdl.setFormData(rsvForm);
        
        //入力値チェック
        ActionErrors errors = rsvForm.validateCheck(reqMdl);
        return errors;
    }

    /**
     * <p>管理者設定ショートメール通知設定画面更新時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAdminSettingEdit(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Rsv320Biz biz = new Rsv320Biz(reqMdl, con);
        Rsv320Form rsvForm = new Rsv320Form();
        Rsv320ParamModel paramMdl = new Rsv320ParamModel();
        paramMdl.setParam(rsvForm);
        paramMdl.setRsv320SmailSendKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv320SmailSendKbn"), "0")));
        paramMdl.setRsv320SmailSend(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv320SmailSend"), "0")));
        biz.updateSmailKbn(paramMdl);
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv320Form", rsvForm);
        __setAdmOpLog(con, req, reqMdl, rsvForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param rsvForm アクションフォーム
     */
    private void __setAdmOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rsv320Form rsvForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String msg = "";
        String change = gsMsg.getMessage(req, "cmn.change");

        //ショートメール通知設定
        msg += "";
        msg += "[" +  gsMsg.getMessage("cmn.reserve") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("shortmail.notification") + "]";
        if (rsvForm.getRsv320SmailSendKbn() == GSConstReserve.AUTH_ADMIN_USER) {
            msg += gsMsg.getMessage("cmn.set.the.admin") + "\r\n";
            if (rsvForm.getRsv320SmailSend() == GSConstReserve.RSU_ADM_SMAIL_SEND_NO) {
                msg += gsMsg.getMessage("cmn.dont.notify");
            } else if (rsvForm.getRsv320SmailSend() == GSConstReserve.RSU_ADM_SMAIL_SEND_OK) {
                msg += gsMsg.getMessage("cmn.notify");
            }
        } else if (rsvForm.getRsv320SmailSendKbn() == GSConstReserve.AUTH_ALL_USER) {
            msg += gsMsg.getMessage("cmn.set.eachuser");
        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <p>個人設定ショートメール通知設定使用判定時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 表示・非表示判定
     */
    public boolean doPconfSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
     
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        if (rsvBiz.canSetSmailConf(con, reqMdl.getSmodel().getUsrsid())) {
            CommonBiz cmnBiz = new CommonBiz();
            //ショートメールは利用可能か判定
            return cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RESERVE, pconfig);
        }
        return false;
    }

    /**
     * <p>個人設定ショートメール通知設定画面表示時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doPconfSettingDisp(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        Rsv300Biz biz = new Rsv300Biz(reqMdl, con);
        Rsv300Form rsvForm = new Rsv300Form();
        Rsv300ParamModel paramMdl = new Rsv300ParamModel();
        paramMdl.setParam(rsvForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv300Form", rsvForm);
    }
    
    /**
     * <p>個人設定ショートメール通知設定画面更新時に実行される
     * <p>入力値チェックを行う
     * @param req
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doPconfValidate(HttpServletRequest req, RequestModel reqMdl) {
        
        Rsv300Form rsvForm = new Rsv300Form();
        Rsv300ParamModel paramMdl = new Rsv300ParamModel();
        paramMdl.setRsv300smailKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv300smailKbn"), "0")));
        paramMdl.setFormData(rsvForm);
        
        //入力値チェック
        ActionErrors errors = rsvForm.validateCheck(reqMdl);
        return errors;
    }

    /**
     * <p>個人設定ショートメール通知設定画面更新時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doPconfSettingEdit(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        Rsv300Biz biz = new Rsv300Biz(reqMdl, con);
        Rsv300Form rsvForm = new Rsv300Form();
        Rsv300ParamModel paramMdl = new Rsv300ParamModel();
        paramMdl.setParam(rsvForm);
        paramMdl.setRsv300smailKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv300smailKbn"), "0")));
        biz.updateSmailKbn(paramMdl);
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv300Form", rsvForm);
        __setPriOpLog(con, req, reqMdl, rsvForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param rsvForm アクションフォーム
     */
    private void __setPriOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rsv300Form rsvForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        String change = gsMsg.getMessage(req, "cmn.change");

        //ショートメール通知設定
        msg += "";
        msg += "[" +  gsMsg.getMessage("cmn.reserve") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("shortmail.notification") + "]";
        if (rsvForm.getRsv300smailKbn() == GSConstReserve.RSU_SMAIL_SEND_NO) {
            msg += gsMsg.getMessage("cmn.dont.notify");
        } else if (rsvForm.getRsv300smailKbn() == GSConstReserve.RSU_SMAIL_SEND_OK) {
            msg += gsMsg.getMessage("cmn.notify");
        }
        String dspName = gsMsg.getMessage("cmn.preferences2") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, msg);
        }

    /**
     * <p>個人設定ショートメール通知設定画面アカウント変更時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doPconfSettingChangeAccount(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        
    }
    
    /**
     * <p>個人設定ショートメール通知設定画面ユーザ・グループ追加時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doPconfSettingUsrGrp(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        return null;
    }

    /**
     * <br>[機  能] セッションユーザがシステム管理者又はプラグイン管理者であるか
     * <br>[解  説] 指定されたプラグインが使用可能であるか
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @return true:使用可, false:使用不可
     * @throws SQLException 
     */
    public boolean doAdminSettingAvailable(Connection con,
            HttpServletRequest req, RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        List<String> canPlgList = cmnBiz.getCanUsePluginIdList(con, reqMdl.getSmodel().getUsrsid());
        //施設予約のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_RESERVE)
                && canPlgList.contains(GSConst.PLUGIN_ID_RESERVE)) {
            return true;
        }
        return false;
    }
}