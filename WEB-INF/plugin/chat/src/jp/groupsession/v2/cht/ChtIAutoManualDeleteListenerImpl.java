package jp.groupsession.v2.cht;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.cht050.Cht050Biz;
import jp.groupsession.v2.cht.cht050.Cht050Form;
import jp.groupsession.v2.cht.cht050.Cht050ParamModel;
import jp.groupsession.v2.cht.cht050kn.Cht050knBiz;
import jp.groupsession.v2.cht.cht050kn.Cht050knForm;
import jp.groupsession.v2.cht.cht050kn.Cht050knParamModel;
import jp.groupsession.v2.cht.cht060.Cht060Biz;
import jp.groupsession.v2.cht.cht060.Cht060Form;
import jp.groupsession.v2.cht.cht060.Cht060ParamModel;
import jp.groupsession.v2.cht.cht060kn.Cht060knBiz;
import jp.groupsession.v2.cht.cht060kn.Cht060knForm;
import jp.groupsession.v2.cht.cht060kn.Cht060knParamModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] チャットについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] チャットについての処理を行う
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
        //チャットは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_CHAT, pconfig)) {
            return false;
        }

        Cht050Biz biz = new Cht050Biz(reqMdl, con);
        Cht050Form chtForm = new Cht050Form();
        Cht050ParamModel paramMdl = new Cht050ParamModel();
        paramMdl.setParam(chtForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(chtForm);
        req.setAttribute("cht050Form", chtForm);
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
        
        Cht050knForm chtForm = new Cht050knForm();
        req.getParameterMap();
        Cht050knParamModel paramMdl = new Cht050knParamModel();
        paramMdl.setParam(chtForm);
        paramMdl.setCht050DoAutoDel(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht050DoAutoDel"), "0")));
        paramMdl.setCht050ReferenceYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht050ReferenceYear"), "0")));
        paramMdl.setCht050ReferenceMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht050ReferenceMonth"), "0")));
        paramMdl.setFormData(chtForm);
        //入力チェック
        return chtForm.validateCht050(reqMdl, con, chtForm);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] チャットについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Cht050knBiz biz = new Cht050knBiz(con);
        Cht050knForm chtForm = new Cht050knForm();
        req.getParameterMap();
        Cht050knParamModel paramMdl = new Cht050knParamModel();
        paramMdl.setParam(chtForm);
        paramMdl.setCht050DoAutoDel(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht050DoAutoDel"), "0")));
        paramMdl.setCht050ReferenceYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht050ReferenceYear"), "0")));
        paramMdl.setCht050ReferenceMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht050ReferenceMonth"), "0")));
        paramMdl.setFormData(chtForm);

        biz.setDelteData(reqMdl, paramMdl, reqMdl.getSmodel().getUsrsid());
        req.setAttribute("cht050knForm", chtForm);
        __setAtDelOpLog(con, req, reqMdl, chtForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param chtForm アクションフォーム
     */
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Cht050knForm chtForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cht.01") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        if (chtForm.getCht050DoAutoDel() == GSConstChat.AUTO_DELETE_YES) {
            String year = Integer.toString(chtForm.getCht050ReferenceYear());
            String month = Integer.toString(chtForm.getCht050ReferenceMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.noset");
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
     * <br>[解  説] チャットについての処理を行う
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
        //チャットは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_CHAT, pconfig)) {
            return false;
        }

        Cht060Biz biz = new Cht060Biz(reqMdl);
        Cht060Form chtForm = new Cht060Form();
        Cht060ParamModel paramMdl = new Cht060ParamModel();
        paramMdl.setParam(chtForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(chtForm);
        req.setAttribute("cht060Form", chtForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] チャットについての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGIN_ID_CHAT)) {
            return null;
        }

        Cht060knForm chtForm = new Cht060knForm();
        req.getParameterMap();
        Cht060knParamModel paramMdl = new Cht060knParamModel();
        paramMdl.setParam(chtForm);
        paramMdl.setCht060ReferenceYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht060ReferenceYear"), "0")));
        paramMdl.setCht060ReferenceMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht060ReferenceMonth"), "0")));
        paramMdl.setFormData(chtForm);

        return chtForm.validateCht060(reqMdl, con, chtForm);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] チャットについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_CHAT)) {
            return;
        }
        Cht060knBiz biz = new Cht060knBiz(con);
        Cht060knForm chtForm = new Cht060knForm();
        req.getParameterMap();
        Cht060knParamModel paramMdl = new Cht060knParamModel();
        paramMdl.setParam(chtForm);
        paramMdl.setCht060ReferenceYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht060ReferenceYear"), "0")));
        paramMdl.setCht060ReferenceMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("cht060ReferenceMonth"), "0")));
        paramMdl.setFormData(chtForm);

        biz.setDelteData(reqMdl, paramMdl, reqMdl.getSmodel().getUsrsid());
        req.setAttribute("cht060knForm", chtForm);
        __setMtDelOpLog(con, req, reqMdl, chtForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param chtForm アクションフォーム
     */
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Cht060knForm chtForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = Integer.toString(chtForm.getCht060ReferenceYear());
        String month = Integer.toString(chtForm.getCht060ReferenceMonth());

        String value = "";
        value += "[" +  gsMsg.getMessage("cht.01") + "] ";
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
     * <br>[解  説] チャットについての処理を行う
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
        //チャットのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_CHAT)
                && canPlgList.contains(GSConst.PLUGIN_ID_CHAT)) {
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
        //チャットのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_CHAT)
                && canPlgList.contains(GSConst.PLUGIN_ID_CHAT)) {
            return true;
        }
        return false;
    }
}