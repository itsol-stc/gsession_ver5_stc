package jp.groupsession.v2.rsv;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv120.Rsv120Biz;
import jp.groupsession.v2.rsv.rsv120.Rsv120Form;
import jp.groupsession.v2.rsv.rsv120.Rsv120ParamModel;
import jp.groupsession.v2.rsv.rsv120kn.Rsv120knBiz;
import jp.groupsession.v2.rsv.rsv120kn.Rsv120knForm;
import jp.groupsession.v2.rsv.rsv120kn.Rsv120knParamModel;
import jp.groupsession.v2.rsv.rsv130.Rsv130Biz;
import jp.groupsession.v2.rsv.rsv130.Rsv130Form;
import jp.groupsession.v2.rsv.rsv130.Rsv130ParamModel;
import jp.groupsession.v2.rsv.rsv130kn.Rsv130knBiz;
import jp.groupsession.v2.rsv.rsv130kn.Rsv130knForm;
import jp.groupsession.v2.rsv.rsv130kn.Rsv130knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] 施設予約についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] 施設予約についての処理を行う
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
        //施設予約は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RESERVE, pconfig)) {
            return false;
        }

        Rsv120Biz biz = new Rsv120Biz(con, reqMdl);
        Rsv120Form rsvForm = new Rsv120Form();
        Rsv120ParamModel paramMdl = new Rsv120ParamModel();
        paramMdl.setParam(rsvForm);
        biz.initDsp(paramMdl);
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv120Form", rsvForm);
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
        
        Rsv120knForm rsvForm = new Rsv120knForm();
        req.getParameterMap();
        Rsv120knParamModel paramMdl = new Rsv120knParamModel();
        paramMdl.setParam(rsvForm);
        paramMdl.setRsv120batchKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv120batchKbn"), "0")));
        paramMdl.setRsv120year(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv120year"), "0")));
        paramMdl.setRsv120month(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv120month"), "0")));
        paramMdl.setFormData(rsvForm);
        return rsvForm.validateCheck(reqMdl);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] 施設予約についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Rsv120knBiz biz = new Rsv120knBiz(con, reqMdl);
        Rsv120knForm rsvForm = new Rsv120knForm();
        req.getParameterMap();
        Rsv120knParamModel paramMdl = new Rsv120knParamModel();
        paramMdl.setParam(rsvForm);
        paramMdl.setRsv120batchKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv120batchKbn"), "0")));
        paramMdl.setRsv120year(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv120year"), "0")));
        paramMdl.setRsv120month(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv120month"), "0")));
        biz.registKanriBatch(paramMdl, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv120knForm", rsvForm);
        __setAtDelOpLog(con, req, reqMdl, rsvForm);
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
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rsv120knForm rsvForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.reserve") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        if (rsvForm.getRsv120batchKbn() == 1) {
            String year = Integer.toString(rsvForm.getRsv120year());
            String month = Integer.toString(rsvForm.getRsv120month());
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
     * <br>[解  説] 施設予約についての処理を行う
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
        //施設予約は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RESERVE, pconfig)) {
            return false;
        }

        Rsv130Biz biz = new Rsv130Biz(con, reqMdl);
        Rsv130Form rsvForm = new Rsv130Form();
        Rsv130ParamModel paramMdl = new Rsv130ParamModel();
        paramMdl.setParam(rsvForm);
        biz.initDsp(paramMdl);
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv130Form", rsvForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] 施設予約についての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGIN_ID_RESERVE)) {
            return null;
        }

        Rsv130knForm rsvForm = new Rsv130knForm();
        req.getParameterMap();
        Rsv130knParamModel paramMdl = new Rsv130knParamModel();
        paramMdl.setParam(rsvForm);
        paramMdl.setRsv130year(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv130year"), "0")));
        paramMdl.setRsv130month(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv130month"), "0")));
        paramMdl.setFormData(rsvForm);

        return rsvForm.validateCheck(reqMdl);
    }

        /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] 施設予約についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_RESERVE)) {
            return;
        }
        Rsv130knBiz biz = new Rsv130knBiz(con, reqMdl);
        Rsv130knForm rsvForm = new Rsv130knForm();
        req.getParameterMap();
        Rsv130knParamModel paramMdl = new Rsv130knParamModel();
        paramMdl.setParam(rsvForm);
        paramMdl.setRsv130year(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv130year"), "0")));
        paramMdl.setRsv130month(Integer.parseInt(
                NullDefault.getString(req.getParameter("rsv130month"), "0")));
        biz.delete(paramMdl, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(rsvForm);
        req.setAttribute("rsv130knForm", rsvForm);
        __setMtDelOpLog(con, req, reqMdl, rsvForm);
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
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rsv130knForm rsvForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = Integer.toString(rsvForm.getRsv130year());
        String month = Integer.toString(rsvForm.getRsv130month());

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.reserve") + "] ";
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
     * <br>[解  説] 施設予約についての処理を行う
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
        //施設予約のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_RESERVE)
                && canPlgList.contains(GSConst.PLUGIN_ID_RESERVE)) {
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
        //施設予約のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_RESERVE)
                && canPlgList.contains(GSConst.PLUGIN_ID_RESERVE)) {
            return true;
        }
        return false;
    }
}