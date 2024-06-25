package jp.groupsession.v2.ntp;

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
import jp.groupsession.v2.ntp.ntp082.Ntp082Biz;
import jp.groupsession.v2.ntp.ntp082.Ntp082Form;
import jp.groupsession.v2.ntp.ntp082.Ntp082ParamModel;
import jp.groupsession.v2.ntp.ntp083.Ntp083Biz;
import jp.groupsession.v2.ntp.ntp083.Ntp083Form;
import jp.groupsession.v2.ntp.ntp083.Ntp083ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] 日報についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] 日報についての処理を行う
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
        //日報は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_NIPPOU, pconfig)) {
            return false;
        }

        Ntp082Biz biz = new Ntp082Biz(con, reqMdl);
        Ntp082Form ntpForm = new Ntp082Form();
        Ntp082ParamModel paramMdl = new Ntp082ParamModel();
        paramMdl.setParam(ntpForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(ntpForm);
        req.setAttribute("ntp082Form", ntpForm);
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

        Ntp082Form ntpForm = new Ntp082Form();
        req.getParameterMap();
        Ntp082ParamModel paramMdl = new Ntp082ParamModel();
        paramMdl.setParam(ntpForm);
        paramMdl.setNtp082AtdelFlg(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp082AtdelFlg"), "0")));
        paramMdl.setNtp082AtdelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp082AtdelYear"), "0")));
        paramMdl.setNtp082AtdelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp082AtdelMonth"), "0")));
        paramMdl.setFormData(ntpForm);

        //入力チェック
        return ntpForm.validateCheck(reqMdl);

    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] 日報についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Ntp082Biz biz = new Ntp082Biz(con, reqMdl);
        Ntp082Form ntpForm = new Ntp082Form();
        req.getParameterMap();
        Ntp082ParamModel paramMdl = new Ntp082ParamModel();
        paramMdl.setParam(ntpForm);
        paramMdl.setNtp082AtdelFlg(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp082AtdelFlg"), "0")));
        paramMdl.setNtp082AtdelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp082AtdelYear"), "0")));
        paramMdl.setNtp082AtdelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp082AtdelMonth"), "0")));
        paramMdl.setFormData(ntpForm);

        biz.setAutoDeleteSetting(paramMdl, reqMdl.getSmodel());
        req.setAttribute("ntp082Form", ntpForm);
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
            RequestModel reqMdl, Ntp082ParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("ntp.1") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        if (paramMdl.getNtp082AtdelFlg() 
                == GSConstSchedule.AUTO_DELETE_ON) {
            String year = Integer.toString(paramMdl.getNtp082AtdelYear());
            String month = Integer.toString(paramMdl.getNtp082AtdelMonth());
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
     * <br>[解  説] 日報についての処理を行う
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
        //日報は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_NIPPOU, pconfig)) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] 日報についての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGIN_ID_NIPPOU)) {
            return null;
        }

        Ntp083Form ntpForm = new Ntp083Form();
        req.getParameterMap();
        Ntp083ParamModel paramMdl = new Ntp083ParamModel();
        paramMdl.setParam(ntpForm);
        paramMdl.setNtp083DelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp083DelYear"), "0")));
        paramMdl.setNtp083DelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp083DelMonth"), "0")));
        paramMdl.setFormData(ntpForm);

        return ntpForm.validateCheck(req);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] 日報についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_NIPPOU)) {
            return;
        }
        Ntp083Biz biz = new Ntp083Biz(con, reqMdl);
        Ntp083Form ntpForm = new Ntp083Form();
        req.getParameterMap();
        Ntp083ParamModel paramMdl = new Ntp083ParamModel();
        paramMdl.setParam(ntpForm);
        paramMdl.setNtp083DelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp083DelYear"), "0")));
        paramMdl.setNtp083DelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp083DelMonth"), "0")));
        paramMdl.setFormData(ntpForm);

        biz.deleteNippou(paramMdl, reqMdl.getSmodel());
        req.setAttribute("ntp083Form", ntpForm);
        __setMtDelOpLog(con, req, reqMdl, ntpForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param ntpForm アクションフォーム
     */
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Ntp083Form ntpForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = Integer.toString(ntpForm.getNtp083DelYear());
        String month = Integer.toString(ntpForm.getNtp083DelMonth());

        String value = "";
        value += "[" +  gsMsg.getMessage("ntp.1") + "] ";
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
     * <br>[解  説] 日報についての処理を行う
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
        //日報のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_NIPPOU)
                && canPlgList.contains(GSConst.PLUGIN_ID_NIPPOU)) {
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
        //日報のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_NIPPOU)
                && canPlgList.contains(GSConst.PLUGIN_ID_NIPPOU)) {
            return true;
        }
        return false;
    }
}