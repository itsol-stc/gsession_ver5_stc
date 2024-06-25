package jp.groupsession.v2.enq;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.enq950.Enq950Biz;
import jp.groupsession.v2.enq.enq950.Enq950Form;
import jp.groupsession.v2.enq.enq950.Enq950ParamModel;
import jp.groupsession.v2.enq.enq950kn.Enq950knBiz;
import jp.groupsession.v2.enq.enq950kn.Enq950knForm;
import jp.groupsession.v2.enq.enq950kn.Enq950knParamModel;
import jp.groupsession.v2.enq.enq960.Enq960Biz;
import jp.groupsession.v2.enq.enq960.Enq960Form;
import jp.groupsession.v2.enq.enq960.Enq960ParamModel;
import jp.groupsession.v2.enq.enq960kn.Enq960knBiz;
import jp.groupsession.v2.enq.enq960kn.Enq960knForm;
import jp.groupsession.v2.enq.enq960kn.Enq960knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] アンケートについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] アンケートについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 使用権限
     */
    public boolean doAutoDeleteDisp(Connection con, HttpServletRequest req, 
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //アンケートは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_ENQUETE, pconfig)) {
            return false;
        }

        Enq960Biz biz = new Enq960Biz();
        Enq960Form enqForm = new Enq960Form();
        Enq960ParamModel paramMdl = new Enq960ParamModel();
        paramMdl.setParam(enqForm);
        biz.setInitData(paramMdl, reqMdl, con);
        paramMdl.setFormData(enqForm);
        req.setAttribute("enq960Form", enqForm);
        return true;
    }
    
    /**
     * <p>管理者設定 自動削除設定画面の更新時前に入力チェックとして実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doAutoDeleteCheck(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        
        Enq960knForm enqForm = new Enq960knForm();
        req.getParameterMap();
        Enq960knParamModel paramMdl = new Enq960knParamModel();
        paramMdl.setParam(enqForm);
        paramMdl.setEnq960SendDelKbn(
                NullDefault.getString(req.getParameter("enq960SendDelKbn"), "0"));
        paramMdl.setEnq960SelectSendYear(
                NullDefault.getString(req.getParameter("enq960SelectSendYear"), "0"));
        paramMdl.setEnq960SelectSendMonth(
                NullDefault.getString(req.getParameter("enq960SelectSendMonth"), "0"));
        paramMdl.setEnq960DraftDelKbn(
                NullDefault.getString(req.getParameter("enq960DraftDelKbn"), "0"));
        paramMdl.setEnq960SelectDraftYear(
                NullDefault.getString(req.getParameter("enq960SelectDraftYear"), "0"));
        paramMdl.setEnq960SelectDraftMonth(
                NullDefault.getString(req.getParameter("enq960SelectDraftMonth"), "0"));
        paramMdl.setFormData(enqForm);
        return enqForm.validateCheck(reqMdl);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] アンケートについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Enq960knBiz biz = new Enq960knBiz(con);
        Enq960knForm enqForm = new Enq960knForm();
        req.getParameterMap();
        Enq960knParamModel paramMdl = new Enq960knParamModel();
        paramMdl.setParam(enqForm);
        paramMdl.setEnq960SendDelKbn(
                NullDefault.getString(req.getParameter("enq960SendDelKbn"), "0"));
        paramMdl.setEnq960SelectSendYear(
                NullDefault.getString(req.getParameter("enq960SelectSendYear"), "0"));
        paramMdl.setEnq960SelectSendMonth(
                NullDefault.getString(req.getParameter("enq960SelectSendMonth"), "0"));
        paramMdl.setEnq960DraftDelKbn(
                NullDefault.getString(req.getParameter("enq960DraftDelKbn"), "0"));
        paramMdl.setEnq960SelectDraftYear(
                NullDefault.getString(req.getParameter("enq960SelectDraftYear"), "0"));
        paramMdl.setEnq960SelectDraftMonth(
                NullDefault.getString(req.getParameter("enq960SelectDraftMonth"), "0"));
        biz.updateAuteDelSetting(reqMdl, paramMdl);
        paramMdl.setFormData(enqForm);
        req.setAttribute("enq960knForm", enqForm);
        __setAtDelOpLog(con, req, reqMdl, paramMdl);
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
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Enq960knParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String year = null;
        String month = null;
        String value = "";
        value += "[" +  gsMsg.getMessage("enq.plugin") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("enq.enq960.01") + "] ";
        if (paramMdl.getEnq960SendDelKbn().equals(
                String.valueOf(GSConstSchedule.AUTO_DELETE_ON))) {
            year = paramMdl.getEnq960SelectSendYear();
            month = paramMdl.getEnq960SelectSendMonth();
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("enq.enq960.02") + "] ";
        if (paramMdl.getEnq960DraftDelKbn().equals(
                String.valueOf(GSConstSchedule.AUTO_DELETE_ON))) {
            year = paramMdl.getEnq960SelectDraftYear();
            month = paramMdl.getEnq960SelectDraftMonth();
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.autodelete.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の表示時に実行される
     * <br>[解  説] アンケートについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 使用権限
     */
    public boolean doManualDeleteDisp(Connection con, HttpServletRequest req, 
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //アンケートは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_ENQUETE, pconfig)) {
            return false;
        }

        Enq950Biz biz = new Enq950Biz();
        Enq950Form enqForm = new Enq950Form();
        Enq950ParamModel paramMdl = new Enq950ParamModel();
        paramMdl.setParam(enqForm);
        biz.setInitData(paramMdl, reqMdl, con);
        paramMdl.setFormData(enqForm);
        req.setAttribute("enq950Form", enqForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] アンケートについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doManualDeleteCheck(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_ENQUETE)) {
            return null;
        }

        Enq950knForm enqForm = new Enq950knForm();
        req.getParameterMap();
        Enq950knParamModel paramMdl = new Enq950knParamModel();
        paramMdl.setParam(enqForm);
        paramMdl.setEnq950SendDelKbn(
                NullDefault.getString(req.getParameter("enq950SendDelKbn"), "0"));
        paramMdl.setEnq950SelectSendYear(
                NullDefault.getString(req.getParameter("enq950SelectSendYear"), "0"));
        paramMdl.setEnq950SelectSendMonth(
                NullDefault.getString(req.getParameter("enq950SelectSendMonth"), "0"));
        paramMdl.setEnq950DraftDelKbn(
                NullDefault.getString(req.getParameter("enq950DraftDelKbn"), "0"));
        paramMdl.setEnq950SelectDraftYear(
                NullDefault.getString(req.getParameter("enq950SelectDraftYear"), "0"));
        paramMdl.setEnq950SelectDraftMonth(
                NullDefault.getString(req.getParameter("enq950SelectDraftMonth"), "0"));
        paramMdl.setFormData(enqForm);

        return enqForm.validateCheck(reqMdl);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] アンケートについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_ENQUETE)) {
            return;
        }
        Enq950knBiz biz = new Enq950knBiz();
        Enq950knForm enqForm = new Enq950knForm();
        req.getParameterMap();
        Enq950knParamModel paramMdl = new Enq950knParamModel();
        paramMdl.setParam(enqForm);
        paramMdl.setEnq950SendDelKbn(
                NullDefault.getString(req.getParameter("enq950SendDelKbn"), "0"));
        paramMdl.setEnq950SelectSendYear(
                NullDefault.getString(req.getParameter("enq950SelectSendYear"), "0"));
        paramMdl.setEnq950SelectSendMonth(
                NullDefault.getString(req.getParameter("enq950SelectSendMonth"), "0"));
        paramMdl.setEnq950DraftDelKbn(
                NullDefault.getString(req.getParameter("enq950DraftDelKbn"), "0"));
        paramMdl.setEnq950SelectDraftYear(
                NullDefault.getString(req.getParameter("enq950SelectDraftYear"), "0"));
        paramMdl.setEnq950SelectDraftMonth(
                NullDefault.getString(req.getParameter("enq950SelectDraftMonth"), "0"));
        biz.doCommit(paramMdl, reqMdl, con);
        paramMdl.setFormData(enqForm);
        req.setAttribute("enq950knForm", enqForm);
        __setMtDelOpLog(con, req, reqMdl, paramMdl);
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
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Enq950knParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = null;
        String month = null;

        String value = "";
        value += "[" +  gsMsg.getMessage("enq.plugin") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("enq.enq950.01") + "] ";
        if (paramMdl.getEnq950SendDelKbn().equals(
                String.valueOf(GSConstSchedule.AUTO_DELETE_ON))) {
            year = paramMdl.getEnq950SelectSendYear();
            month = paramMdl.getEnq950SelectSendMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.dont.delete");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("enq.enq950.02") + "] ";
        if (paramMdl.getEnq950DraftDelKbn().equals(
                String.valueOf(GSConstSchedule.AUTO_DELETE_ON))) {
            year = paramMdl.getEnq950SelectDraftYear();
            month = paramMdl.getEnq950SelectDraftMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage("cmn.dont.delete");
        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.manual.delete2");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, delete, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面のアカウント変更時に実行される
     * <br>[解  説] アンケートについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteChangeAccount(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
    }

    /**
     * <br>[機  能] セッションユーザがシステム管理者、プラグイン管理者であるか
     * <br>[解  説] 指定されたプラグインが使用可能であるか
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @return true:使用可, false:使用不可
     * @throws SQLException
     */
    public boolean doAutoAdminSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        List<String> canPlgList = cmnBiz.getCanUsePluginIdList(con, reqMdl.getSmodel().getUsrsid());
        //アンケートのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_ENQUETE)
                && canPlgList.contains(GSConst.PLUGIN_ID_ENQUETE)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] セッションユーザがシステム管理者、プラグイン管理者であるか
     * <br>[解  説] 指定されたプラグインが使用可能であるか
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @return true:使用可, false:使用不可
     * @throws SQLException
     */
    public boolean doManualAdminSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        List<String> canPlgList = cmnBiz.getCanUsePluginIdList(con, reqMdl.getSmodel().getUsrsid());
        //アンケートのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_ENQUETE)
                && canPlgList.contains(GSConst.PLUGIN_ID_ENQUETE)) {
            return true;
        }
        return false;
    }
}