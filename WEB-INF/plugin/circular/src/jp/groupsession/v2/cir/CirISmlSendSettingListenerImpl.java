package jp.groupsession.v2.cir;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir200.Cir200Biz;
import jp.groupsession.v2.cir.cir200.Cir200Form;
import jp.groupsession.v2.cir.cir200.Cir200ParamModel;
import jp.groupsession.v2.cir.cir200kn.Cir200knBiz;
import jp.groupsession.v2.cir.cir200kn.Cir200knForm;
import jp.groupsession.v2.cir.cir200kn.Cir200knParamModel;
import jp.groupsession.v2.cir.cir250.Cir250Biz;
import jp.groupsession.v2.cir.cir250.Cir250Form;
import jp.groupsession.v2.cir.cir250.Cir250ParamModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirAconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirISmlSendSettingListenerImpl implements ISmlSendSettingListener {

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
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_CIR, pconfig)) {
            return false;
        }
        
        Cir200Biz biz = new Cir200Biz();
        Cir200Form cirForm = new Cir200Form();
        Cir200ParamModel paramMdl = new Cir200ParamModel();
        paramMdl.setParam(cirForm);
        biz.setInitData(con, paramMdl, reqMdl);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir200Form", cirForm);
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
        
        Cir200knForm cirForm = new Cir200knForm();
        Cir200knParamModel paramMdl = new Cir200knParamModel();

        paramMdl.setCir200SmlSendKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlSendKbn"), "0")));
        paramMdl.setCir200SmlSend(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlSend"), "0")));
        paramMdl.setCir200SmlMemo(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlMemo"), "0")));
        paramMdl.setCir200SmlEdt(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlEdt"), "0")));
        paramMdl.setFormData(cirForm);
        
        //入力チェック
        ActionErrors errors = cirForm.validateCheck(reqMdl);
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

        Cir200knBiz biz = new Cir200knBiz();
        Cir200knForm cirForm = new Cir200knForm();
        Cir200knParamModel paramMdl = new Cir200knParamModel();

        paramMdl.setParam(cirForm);
        paramMdl.setCir200SmlSendKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlSendKbn"), "0")));
        paramMdl.setCir200SmlSend(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlSend"), "0")));
        paramMdl.setCir200SmlMemo(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlMemo"), "0")));
        paramMdl.setCir200SmlEdt(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir200SmlEdt"), "0")));
        biz.updateCirSmailSetting(paramMdl, con, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir200Form", cirForm);
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
            RequestModel reqMdl, Cir200knParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cir.5") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("shortmail.notification") + "] ";
        String[] setting = {
                gsMsg.getMessage("cmn.set.eachuser"),
                gsMsg.getMessage("cmn.set.the.admin")
        };
        String[] notify = {
                gsMsg.getMessage("cmn.notify"),
                gsMsg.getMessage("cmn.dont.notify")
        };
        value += setting[paramMdl.getCir200SmlSendKbn()];
        if (paramMdl.getCir200SmlSendKbn()
                == GSConstSchedule.SMAIL_SEND_KBN_ADMIN) {
            
            value += "\r\n";
            value += "[" +  gsMsg.getMessage("cir.cir160.2") + "] ";
            value += notify[paramMdl.getCir200SmlSend()];
            value += "\r\n";
            value += "[" +  gsMsg.getMessage("cir.cir160.4") + "] ";
            value += notify[paramMdl.getCir200SmlMemo()];
            value += "\r\n";
            value += "[" +  gsMsg.getMessage("cir.cir160.6") + "] ";
            value += notify[paramMdl.getCir200SmlEdt()];
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

        CirCommonBiz cirBiz = new CirCommonBiz(con);
        CirAconfModel confMdl = cirBiz.getCirAdminData(con, reqMdl.getSmodel().getUsrsid());
        if (confMdl.getCafSmailSendKbn() == 0) {
            CommonBiz cmnBiz = new CommonBiz();
            //ショートメールは利用可能か判定
            return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_CIR, pconfig);    
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
        
        Cir250Biz biz = new Cir250Biz();
        Cir250Form cirForm = new Cir250Form();
        Cir250ParamModel paramMdl = new Cir250ParamModel();
        paramMdl.setParam(cirForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir250Form", cirForm);

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
        
        Cir250Form cirForm = new Cir250Form();
        Cir250ParamModel paramMdl = new Cir250ParamModel();
        paramMdl.setCir250smlEdt(NullDefault.getInt(req.getParameter("cir250smlEdt"), 0));
        paramMdl.setCir250smlMemo(NullDefault.getInt(req.getParameter("cir250smlMemo"), 0));
        paramMdl.setCir250smlNtf(NullDefault.getInt(req.getParameter("cir250smlNtf"), 0));
        paramMdl.setCir250selectAccount(
                NullDefault.getString(req.getParameter("cir250selectAccount"), ""));
        paramMdl.setFormData(cirForm);
        
        //入力値チェック
        ActionErrors errors = cirForm.validateCheck(reqMdl);
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
        Cir250Biz biz = new Cir250Biz();
        Cir250Form cirForm = new Cir250Form();
        Cir250ParamModel paramMdl = new Cir250ParamModel();
        paramMdl.setParam(cirForm);
        paramMdl.setCir250smlEdt(NullDefault.getInt(req.getParameter("cir250smlEdt"), 0));
        paramMdl.setCir250smlMemo(NullDefault.getInt(req.getParameter("cir250smlMemo"), 0));
        paramMdl.setCir250smlNtf(NullDefault.getInt(req.getParameter("cir250smlNtf"), 0));
        paramMdl.setCir250selectAccount(
                NullDefault.getString(req.getParameter("cir250selectAccount"), ""));
        CirAccountDao accountDao = new CirAccountDao(con);
        
        Perl5Util util = new Perl5Util();
        if (!util.match("/^[0-9]+$/", paramMdl.getCir250selectAccount())) {
            return;
        } 
        if (!accountDao.canUseAccount(Integer.parseInt(paramMdl.getCir250selectAccount()),
                reqMdl.getSmodel().getUsrsid())) {
            return;
        }
        
        biz.updateSmlConf(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir250Form", cirForm);
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
     * @throws SQLException 
     * @throws NumberFormatException 
     */
    private void __setPriOpLog(
            Connection con,
            HttpServletRequest req,
            RequestModel reqMdl,
            Cir250ParamModel paramMdl) throws NumberFormatException, SQLException {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cir.5") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.pdf.account") + "] ";
        CirAccountDao dao = new CirAccountDao(con);
        CirAccountModel model = dao.select(Integer.parseInt(paramMdl.getCir250selectAccount()));
        value += model.getCacName();
        String[] notify = {
                gsMsg.getMessage("cmn.notify"),
                gsMsg.getMessage("cmn.dont.notify")
        };
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cir.cir160.2") + "] ";
        value += gsMsg.getMessage("schedule.sch095.6") + ":";
        value += notify[paramMdl.getCir250smlEdt()];
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cir.cir160.4") + "] ";
        value += gsMsg.getMessage("schedule.sch095.5") + ":";
        value += notify[paramMdl.getCir250smlMemo()];
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cir.cir160.6") + "] ";
        value += gsMsg.getMessage("schedule.sch095.7") + ":";
        value += notify[paramMdl.getCir250smlNtf()];

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
        
        String cmd = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!cmd.equals(GSConst.PLUGINID_CIR)) {
            return;
        }
        String cirSid = NullDefault.getString(req.getParameter("cir250selectAccount"), "");
        Cir250Biz biz = new Cir250Biz();
        Cir250Form cirForm = new Cir250Form();
        Cir250ParamModel paramMdl = new Cir250ParamModel();
        paramMdl.setParam(cirForm);
        paramMdl.setCir250selectAccount(cirSid);
        biz.setReloadData(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir250Form", cirForm);
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
        //回覧板のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_CIR)
                && canPlgList.contains(GSConst.PLUGINID_CIR)) {
            return true;
        }
        return false;
    }
}