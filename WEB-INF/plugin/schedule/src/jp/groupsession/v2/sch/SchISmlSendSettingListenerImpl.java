package jp.groupsession.v2.sch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.sch088.Sch088Biz;
import jp.groupsession.v2.sch.sch088.Sch088Form;
import jp.groupsession.v2.sch.sch088.Sch088ParamModel;
import jp.groupsession.v2.sch.sch095.Sch095Biz;
import jp.groupsession.v2.sch.sch095.Sch095Form;
import jp.groupsession.v2.sch.sch095.Sch095ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchISmlSendSettingListenerImpl implements ISmlSendSettingListener {

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
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SCH, pconfig)) {
            return false;
        }

        Sch088Biz biz = new Sch088Biz(reqMdl);
        Sch088Form schForm = new Sch088Form();
        Sch088ParamModel paramMdl = new Sch088ParamModel();
        paramMdl.setParam(schForm);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(schForm);
        req.setAttribute("sch088Form", schForm);
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
        
        Sch088Form schForm = new Sch088Form();
        Sch088ParamModel paramMdl = new Sch088ParamModel();
        paramMdl.setSch088smlSendKbn(
                NullDefault.getString(req.getParameter("sch088smlSendKbn"), "0"));
        paramMdl.setSch088Smail(NullDefault.getString(req.getParameter("sch088Smail"), "0"));
        paramMdl.setSch088SmailGroup(
                NullDefault.getString(req.getParameter("sch088SmailGroup"), "0"));
        paramMdl.setSch088SmailAttend(
                NullDefault.getString(req.getParameter("sch088SmailAttend"), "0"));
        paramMdl.setFormData(schForm);
        
        //入力値チェック
        ActionErrors errors = schForm.validateCheck(reqMdl);
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
        
        Sch088Biz biz = new Sch088Biz(reqMdl);
        Sch088Form schForm = new Sch088Form();
        Sch088ParamModel paramMdl = new Sch088ParamModel();
        paramMdl.setParam(schForm);
        paramMdl.setSch088smlSendKbn(
                NullDefault.getString(req.getParameter("sch088smlSendKbn"), "0"));
        paramMdl.setSch088Smail(NullDefault.getString(req.getParameter("sch088Smail"), "0"));
        paramMdl.setSch088SmailGroup(
                NullDefault.getString(req.getParameter("sch088SmailGroup"), "0"));
        paramMdl.setSch088SmailAttend(
                NullDefault.getString(req.getParameter("sch088SmailAttend"), "0"));
        paramMdl.setFormData(schForm);
        biz.setPconfSetting(paramMdl, reqMdl.getSmodel(), con);
        paramMdl.setFormData(schForm);
        req.setAttribute("sch088Form", schForm);
        __setAdmOpLog(con, req, reqMdl, paramMdl);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     */
    private void __setAdmOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Sch088ParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("schedule.108") + "] ";
        value += "\r\n";
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
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, value);
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

        CommonBiz cmnBiz = new CommonBiz();
        SchCommonBiz biz = new SchCommonBiz(con, reqMdl);
        SchAdmConfModel admMdl = biz.getAdmConfModel(con);
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)
              && admMdl.getSadSmailSendKbn() == GSConstSchedule.SMAIL_SEND_KBN_USER) {
            return true;
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

        Sch095Biz biz = new Sch095Biz(reqMdl);
        Sch095Form schForm = new Sch095Form();
        Sch095ParamModel paramMdl = new Sch095ParamModel();
        biz.setInitData(paramMdl, reqMdl.getSmodel(), con);
        paramMdl.setFormData(schForm);
        req.setAttribute("sch095Form", schForm);
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
        
        Sch095Form schForm = new Sch095Form();
        Sch095ParamModel paramMdl = new Sch095ParamModel();
        paramMdl.setSch095Smail(
                NullDefault.getString(req.getParameter("sch095Smail"), "0"));
        paramMdl.setSch095SmailGroup(
                NullDefault.getString(req.getParameter("sch095SmailGroup"), "0"));
        paramMdl.setSch095SmailAttend(
                NullDefault.getString(req.getParameter("sch095SmailAttend"), "0"));
        paramMdl.setFormData(schForm);
        
        //入力値チェック
        ActionErrors errors = schForm.validateCheck(reqMdl);
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

        Sch095Biz biz = new Sch095Biz(reqMdl);
        Sch095Form schForm = new Sch095Form();
        Sch095ParamModel paramMdl = new Sch095ParamModel();
        paramMdl.setParam(schForm);
        paramMdl.setSch095Smail(
                NullDefault.getString(req.getParameter("sch095Smail"), "0"));
        paramMdl.setSch095SmailGroup(
                NullDefault.getString(req.getParameter("sch095SmailGroup"), "0"));
        paramMdl.setSch095SmailAttend(
                NullDefault.getString(req.getParameter("sch095SmailAttend"), "0"));
        biz.setPconfSetting(paramMdl, reqMdl.getSmodel(), con);
        paramMdl.setFormData(schForm);
        req.setAttribute("sch095Form", schForm);
        __setPriOpLog(con, req, reqMdl, paramMdl);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     */
    private void __setPriOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Sch095ParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("schedule.108") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("shortmail.notification") + "] ";
        String[] notify = {
                gsMsg.getMessage("cmn.dont.notify"),
                gsMsg.getMessage("cmn.notify")
        };
        value += "\r\n";
        value += gsMsg.getMessage("schedule.sch095.6") + ":";
        value += notify[Integer.parseInt(paramMdl.getSch095Smail())];
        value += "\r\n";
        value += gsMsg.getMessage("schedule.sch095.5") + ":";
        value += notify[Integer.parseInt(paramMdl.getSch095SmailGroup())];
        value += "\r\n";
        value += gsMsg.getMessage("schedule.sch095.7") + ":";
        value += notify[Integer.parseInt(paramMdl.getSch095SmailAttend())];

        String dspName = gsMsg.getMessage("cmn.preferences2") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(), dspName,
                change, GSConstLog.LEVEL_INFO, value);
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
        //スケジュールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_SCH)
                && canPlgList.contains(GSConst.PLUGINID_SCH)) {
            return true;
        }
        return false;
    }
}