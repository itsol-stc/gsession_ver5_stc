package jp.groupsession.v2.sch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch082.Sch082Biz;
import jp.groupsession.v2.sch.sch082.Sch082Form;
import jp.groupsession.v2.sch.sch082.Sch082ParamModel;
import jp.groupsession.v2.sch.sch083.Sch083Biz;
import jp.groupsession.v2.sch.sch083.Sch083Form;
import jp.groupsession.v2.sch.sch083.Sch083ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] スケジュールについての処理を行う
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
        //スケジュールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SCH, pconfig)) {
            return false;
        }

        Sch082Biz biz = new Sch082Biz(reqMdl);
        Sch082Form schForm = new Sch082Form();
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        __setLabel(schForm, req, reqMdl);
        paramMdl.setParam(schForm);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(schForm);
        req.setAttribute("sch082Form", schForm);
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
        
        Sch082Form schForm = new Sch082Form();
        req.getParameterMap();
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        paramMdl.setParam(schForm);
        paramMdl.setSch082AtdelFlg(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch082AtdelFlg"), "0")));
        paramMdl.setSch082AtdelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch082AtdelYear"), "0")));
        paramMdl.setSch082AtdelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch082AtdelMonth"), "0")));
        paramMdl.setFormData(schForm);

        //入力チェック
        return schForm.validateCheck(req);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] スケジュールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Sch082Biz biz = new Sch082Biz(reqMdl);
        Sch082Form schForm = new Sch082Form();
        req.getParameterMap();
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        paramMdl.setParam(schForm);
        paramMdl.setSch082AtdelFlg(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch082AtdelFlg"), "0")));
        paramMdl.setSch082AtdelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch082AtdelYear"), "0")));
        paramMdl.setSch082AtdelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch082AtdelMonth"), "0")));
        paramMdl.setFormData(schForm);

        biz.setAutoDeleteSetting(paramMdl, reqMdl.getSmodel(), con);
        req.setAttribute("sch082Form", schForm);
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
            RequestModel reqMdl, Sch082ParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("schedule.108") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        if (paramMdl.getSch082AtdelFlg() 
                == GSConstSchedule.AUTO_DELETE_ON) {
            String year = Integer.toString(paramMdl.getSch082AtdelYear());
            String month = Integer.toString(paramMdl.getSch082AtdelMonth());
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
     * <br>[機  能] ラベルの設定
     * <br>[解  説] スケジュールについての処理を行う
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void __setLabel(Sch082Form form, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //年ラベル作成
        ArrayList<LabelValueBean> sch082AtdelYearLabel__ = new ArrayList<LabelValueBean>();
        for (String label : Sch082Form.YEAR_VALUE) {
            sch082AtdelYearLabel__.add(
                    new LabelValueBean(gsMsg.getMessage(req, "cmn.year", label), label));
        }
        form.setSch082AtdelYearLabel(sch082AtdelYearLabel__);
        //月ラベル作成
        ArrayList<LabelValueBean> sch082AtdelMonthLabel__ = new ArrayList<LabelValueBean>();
        for (String label : Sch082Form.MONTH_VALUE) {
            sch082AtdelMonthLabel__.add(
                    new LabelValueBean(gsMsg.getMessage(req, "cmn.months", label), label));
        }
        form.setSch082AtdelMonthLabel(sch082AtdelMonthLabel__);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の表示時に実行される
     * <br>[解  説] スケジュールについての処理を行う
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
        //スケジュールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SCH, pconfig)) {
            return false;
        }
        Sch083Form schForm = new Sch083Form();
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //年ラベル作成
        ArrayList<LabelValueBean> sch083DelYearLabel__ = new ArrayList<LabelValueBean>();
        for (String label : Sch083Form.YEAR_VALUE) {
            sch083DelYearLabel__.add(
                    new LabelValueBean(gsMsg.getMessage(req, "cmn.year", label), label));
        }
        schForm.setSch083DelYearLabel(sch083DelYearLabel__);
        //月ラベル作成
        ArrayList<LabelValueBean> sch083DelMonthLabel__ = new ArrayList<LabelValueBean>();
        for (String label : Sch083Form.MONTH_VALUE) {
            sch083DelMonthLabel__.add(
                    new LabelValueBean(gsMsg.getMessage(req, "cmn.months", label), label));
        }
        schForm.setSch083DelMonthLabel(sch083DelMonthLabel__);
        paramMdl.setParam(schForm);
        req.setAttribute("sch083Form", schForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] スケジュールについての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGINID_SCH)) {
            return null;
        }

        Sch083Form schForm = new Sch083Form();
        req.getParameterMap();
        Sch083ParamModel paramMdl = new Sch083ParamModel();
        paramMdl.setParam(schForm);
        paramMdl.setSch083DelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch083DelYear"), "0")));
        paramMdl.setSch083DelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch083DelMonth"), "0")));
        paramMdl.setFormData(schForm);

        return schForm.validateCheck(req);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] スケジュールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_SCH)) {
            return;
        }
        Sch083Biz biz = new Sch083Biz(reqMdl);
        Sch083Form schForm = new Sch083Form();
        req.getParameterMap();
        Sch083ParamModel paramMdl = new Sch083ParamModel();
        paramMdl.setParam(schForm);
        paramMdl.setSch083DelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch083DelYear"), "0")));
        paramMdl.setSch083DelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("sch083DelMonth"), "0")));
        paramMdl.setFormData(schForm);

        biz.deleteSchedule(paramMdl, reqMdl.getSmodel(), con);
        req.setAttribute("sch083Form", schForm);
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
            RequestModel reqMdl, Sch083ParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = Integer.toString(paramMdl.getSch083DelYear());
        String month = Integer.toString(paramMdl.getSch083DelMonth());

        String value = "";
        value += "[" +  gsMsg.getMessage("schedule.108") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.manual.delete2") + "] ";
        value += gsMsg.getMessage(req, "cmn.year", year);
        value += gsMsg.getMessage(req, "cmn.months", month);
        value += gsMsg.getMessage("cmn.after.data");

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.manual.delete2");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, delete, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面のアカウント変更時に実行される
     * <br>[解  説] スケジュールについての処理を行う
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
        //スケジュールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_SCH)
                && canPlgList.contains(GSConst.PLUGINID_SCH)) {
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
        //スケジュールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_SCH)
                && canPlgList.contains(GSConst.PLUGINID_SCH)) {
            return true;
        }
        return false;
    }
}