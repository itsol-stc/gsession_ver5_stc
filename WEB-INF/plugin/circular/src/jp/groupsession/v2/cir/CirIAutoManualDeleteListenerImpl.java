package jp.groupsession.v2.cir;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.cir110.Cir110Biz;
import jp.groupsession.v2.cir.cir110.Cir110Form;
import jp.groupsession.v2.cir.cir110.Cir110ParamModel;
import jp.groupsession.v2.cir.cir110kn.Cir110knBiz;
import jp.groupsession.v2.cir.cir110kn.Cir110knForm;
import jp.groupsession.v2.cir.cir110kn.Cir110knParamModel;
import jp.groupsession.v2.cir.cir120.Cir120Biz;
import jp.groupsession.v2.cir.cir120.Cir120Form;
import jp.groupsession.v2.cir.cir120.Cir120ParamModel;
import jp.groupsession.v2.cir.cir120kn.Cir120knBiz;
import jp.groupsession.v2.cir.cir120kn.Cir120knForm;
import jp.groupsession.v2.cir.cir120kn.Cir120knParamModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] 回覧板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] 回覧板についての処理を行う
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
        //回覧板は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_CIR, pconfig)) {
            return false;
        }

        Cir110Biz biz = new Cir110Biz();
        Cir110Form cirForm = new Cir110Form();
        Cir110ParamModel paramMdl = new Cir110ParamModel();
        paramMdl.setParam(cirForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir110Form", cirForm);
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

        Cir110knForm cirForm = new Cir110knForm();
        req.getParameterMap();
        Cir110knParamModel paramMdl = new Cir110knParamModel();
        paramMdl.setParam(cirForm);
        paramMdl.setCir110JdelKbn(
                NullDefault.getString(req.getParameter("cir110JdelKbn"), "0"));
        paramMdl.setCir110JYear(
                NullDefault.getString(req.getParameter("cir110JYear"), "0"));
        paramMdl.setCir110JMonth(
                NullDefault.getString(req.getParameter("cir110JMonth"), "0"));
        paramMdl.setCir110SdelKbn(
                NullDefault.getString(req.getParameter("cir110SdelKbn"), "0"));
        paramMdl.setCir110SYear(
                NullDefault.getString(req.getParameter("cir110SYear"), "0"));
        paramMdl.setCir110SMonth(
                NullDefault.getString(req.getParameter("cir110SMonth"), "0"));
        paramMdl.setCir110DdelKbn(
                NullDefault.getString(req.getParameter("cir110DdelKbn"), "0"));
        paramMdl.setCir110DYear(
                NullDefault.getString(req.getParameter("cir110DYear"), "0"));
        paramMdl.setCir110DMonth(
                NullDefault.getString(req.getParameter("cir110DMonth"), "0"));
        paramMdl.setFormData(cirForm);
        
        return cirForm.validateCheck(reqMdl);

    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] 回覧板についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        Cir110knBiz biz = new Cir110knBiz();
        Cir110knForm cirForm = new Cir110knForm();
        req.getParameterMap();
        Cir110knParamModel paramMdl = new Cir110knParamModel();
        paramMdl.setParam(cirForm);
        paramMdl.setCir110JdelKbn(
                NullDefault.getString(req.getParameter("cir110JdelKbn"), "0"));
        paramMdl.setCir110JYear(
                NullDefault.getString(req.getParameter("cir110JYear"), "0"));
        paramMdl.setCir110JMonth(
                NullDefault.getString(req.getParameter("cir110JMonth"), "0"));
        paramMdl.setCir110SdelKbn(
                NullDefault.getString(req.getParameter("cir110SdelKbn"), "0"));
        paramMdl.setCir110SYear(
                NullDefault.getString(req.getParameter("cir110SYear"), "0"));
        paramMdl.setCir110SMonth(
                NullDefault.getString(req.getParameter("cir110SMonth"), "0"));
        paramMdl.setCir110DdelKbn(
                NullDefault.getString(req.getParameter("cir110DdelKbn"), "0"));
        paramMdl.setCir110DYear(
                NullDefault.getString(req.getParameter("cir110DYear"), "0"));
        paramMdl.setCir110DMonth(
                NullDefault.getString(req.getParameter("cir110DMonth"), "0"));
        CirAdelModel delMdl = biz.updateAuteDelSetting(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir110knForm", cirForm);
        __setAtDelOpLog(con, req, reqMdl, delMdl);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param delMdl 自動削除情報
     */
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, CirAdelModel delMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String year = null;
        String month = null;
        String value = "";
        value += "[" +  gsMsg.getMessage("cir.5") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete")
              + gsMsg.getMessage("cir.25") + "] ";
        if (delMdl.getCadJdelKbn() == GSConstCircular.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getCadJdelYear());
            month = Integer.toString(delMdl.getCadJdelMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete")
              + gsMsg.getMessage("cir.26") + "] ";
        if (delMdl.getCadSdelKbn() == GSConstCircular.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getCadSdelYear());
            month = Integer.toString(delMdl.getCadSdelMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete")
              + gsMsg.getMessage("cir.27") + "] ";
        if (delMdl.getCadDdelKbn() == GSConstCircular.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getCadDdelYear());
            month = Integer.toString(delMdl.getCadDdelMonth());
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
     * <br>[解  説] 回覧板についての処理を行う
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
        //回覧板は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_CIR, pconfig)) {
            return false;
        }

        Cir120Biz biz = new Cir120Biz();
        Cir120Form cirForm = new Cir120Form();
        Cir120ParamModel paramMdl = new Cir120ParamModel();
        paramMdl.setParam(cirForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        req.setAttribute("cir120Form", cirForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] 回覧板についての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGINID_CIR)) {
            return null;
        }

        Cir120knForm cirForm = new Cir120knForm();
        req.getParameterMap();
        Cir120knParamModel paramMdl = new Cir120knParamModel();
        paramMdl.setParam(cirForm);
        paramMdl.setCir120SelKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir120SelKbn"), "0")));
        paramMdl.setCir120AccountSid(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir120AccountSid"), "0")));
        paramMdl.setCir120AccountName(
                NullDefault.getString(req.getParameter("cir120AccountName"), "0"));
        paramMdl.setCir120JdelKbn(
                NullDefault.getString(req.getParameter("cir120JdelKbn"), "0"));
        paramMdl.setCir120JYear(
                NullDefault.getString(req.getParameter("cir120JYear"), "0"));
        paramMdl.setCir120JMonth(
                NullDefault.getString(req.getParameter("cir120JMonth"), "0"));
        paramMdl.setCir120SdelKbn(
                NullDefault.getString(req.getParameter("cir120SdelKbn"), "0"));
        paramMdl.setCir120SYear(
                NullDefault.getString(req.getParameter("cir120SYear"), "0"));
        paramMdl.setCir120SMonth(
                NullDefault.getString(req.getParameter("cir120SMonth"), "0"));
        paramMdl.setCir120DdelKbn(
                NullDefault.getString(req.getParameter("cir120DdelKbn"), "0"));
        paramMdl.setCir120DYear(
                NullDefault.getString(req.getParameter("cir120DYear"), "0"));
        paramMdl.setCir120DMonth(
                NullDefault.getString(req.getParameter("cir120DMonth"), "0"));
        paramMdl.setFormData(cirForm);

        return cirForm.validatecir120(con, req);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] 回覧板についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_CIR)) {
            return;
        }
        Cir120knBiz biz = new Cir120knBiz(con);
        Cir120knForm cirForm = new Cir120knForm();
        req.getParameterMap();
        Cir120knParamModel paramMdl = new Cir120knParamModel();
        paramMdl.setParam(cirForm);
        paramMdl.setCir120SelKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir120SelKbn"), "0")));
        paramMdl.setCir120AccountSid(Integer.parseInt(
                NullDefault.getString(req.getParameter("cir120AccountSid"), "0")));
        paramMdl.setCir120AccountName(
                NullDefault.getString(req.getParameter("cir120AccountName"), "0"));
        paramMdl.setCir120JdelKbn(
                NullDefault.getString(req.getParameter("cir120JdelKbn"), "0"));
        paramMdl.setCir120JYear(
                NullDefault.getString(req.getParameter("cir120JYear"), "0"));
        paramMdl.setCir120JMonth(
                NullDefault.getString(req.getParameter("cir120JMonth"), "0"));
        paramMdl.setCir120SdelKbn(
                NullDefault.getString(req.getParameter("cir120SdelKbn"), "0"));
        paramMdl.setCir120SYear(
                NullDefault.getString(req.getParameter("cir120SYear"), "0"));
        paramMdl.setCir120SMonth(
                NullDefault.getString(req.getParameter("cir120SMonth"), "0"));
        paramMdl.setCir120DdelKbn(
                NullDefault.getString(req.getParameter("cir120DdelKbn"), "0"));
        paramMdl.setCir120DYear(
                NullDefault.getString(req.getParameter("cir120DYear"), "0"));
        paramMdl.setCir120DMonth(
                NullDefault.getString(req.getParameter("cir120DMonth"), "0"));
        paramMdl.setFormData(cirForm);

        biz.updateSyudoDelSetting(paramMdl, reqMdl);
        req.setAttribute("cir120knForm", cirForm);
        __setMtDelOpLog(con, req, reqMdl, cirForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param cirForm  アクションフォーム
     * @throws SQLException 
     */
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Cir120knForm cirForm) throws SQLException {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = null;
        String month = null;

        String accountName;
        CirAccountDao cirDao = new CirAccountDao(con);
        CirAccountModel cirMdl = cirDao.select(cirForm.getCir120AccountSid());
        if (cirForm.getCir120SelKbn() != GSConstCircular.ACCOUNT_SEL) {
            accountName = gsMsg.getMessage("wml.wml120.01");
        } else {
            accountName = cirMdl.getCacName();
        }

        String value = "";
        value += "[" +  gsMsg.getMessage("cir.5") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.target")
                + gsMsg.getMessage("cmn.pdf.account") + "] ";
        value += accountName;
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete")
        + gsMsg.getMessage("cir.25") + "] ";
        if (Integer.parseInt(cirForm.getCir120JdelKbn())
                == GSConstCircular.AUTO_DEL_ACCOUNT) {
            year = cirForm.getCir120JYear();
            month = cirForm.getCir120JMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete")
        + gsMsg.getMessage("cir.26") + "] ";
        if (Integer.parseInt(cirForm.getCir120SdelKbn())
                == GSConstCircular.AUTO_DEL_ACCOUNT) {
            year = cirForm.getCir120SYear();
            month = cirForm.getCir120SMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete")
        + gsMsg.getMessage("cir.27") + "] ";
        if (Integer.parseInt(cirForm.getCir120DdelKbn())
                == GSConstCircular.AUTO_DEL_ACCOUNT) {
            year = cirForm.getCir120DYear();
            month = cirForm.getCir120DMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
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
     * <br>[解  説] 回覧板についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteChangeAccount(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_CIR)) {
            return;
        }
        String accountSid = NullDefault.getString(req.getParameter("accountSid"), "0");
        String accountName = NullDefault.getString(req.getParameter("accountName"), "");
        Cir120Biz biz = new Cir120Biz();
        Cir120Form cirForm = new Cir120Form();
        try {
            BeanUtils.copyProperties(cirForm, req.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        Cir120ParamModel paramMdl = new Cir120ParamModel();

        cirForm.setCir120AccountSid(Integer.parseInt(accountSid));
        paramMdl.setParam(cirForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(cirForm);
        cirForm.setCir120AccountName(accountName);
        req.setAttribute("cir120Form", cirForm);
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
        //回覧板のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_CIR)
                && canPlgList.contains(GSConst.PLUGINID_CIR)) {
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
        //回覧板のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_CIR)
                && canPlgList.contains(GSConst.PLUGINID_CIR)) {
            return true;
        }
        return false;
    }
}