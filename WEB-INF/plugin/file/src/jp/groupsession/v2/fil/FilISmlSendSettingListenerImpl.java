package jp.groupsession.v2.fil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.fil130.Fil130Biz;
import jp.groupsession.v2.fil.fil130.Fil130Form;
import jp.groupsession.v2.fil.fil130.Fil130ParamModel;
import jp.groupsession.v2.fil.fil130kn.Fil130knBiz;
import jp.groupsession.v2.fil.fil130kn.Fil130knForm;
import jp.groupsession.v2.fil.fil130kn.Fil130knParamModel;
import jp.groupsession.v2.fil.fil260.Fil260Biz;
import jp.groupsession.v2.fil.fil260.Fil260Form;
import jp.groupsession.v2.fil.fil260.Fil260ParamModel;
import jp.groupsession.v2.fil.fil260kn.Fil260knBiz;
import jp.groupsession.v2.fil.fil260kn.Fil260knForm;
import jp.groupsession.v2.fil.fil260kn.Fil260knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilISmlSendSettingListenerImpl implements ISmlSendSettingListener {

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
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_FILE, pconfig)) {
            return false;
        }

        Fil260Biz biz = new Fil260Biz(reqMdl, con);
        Fil260Form filForm = new Fil260Form();
        Fil260ParamModel paramMdl = new Fil260ParamModel();
        paramMdl.setParam(filForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(filForm);
        req.setAttribute("fil260Form", filForm);
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

        Fil260knForm filForm = new Fil260knForm();
        Fil260knParamModel paramMdl = new Fil260knParamModel();
        paramMdl.setFil260smlSendKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("fil260smlSendKbn"), "0")));
        paramMdl.setFil260smlSend(Integer.parseInt(
                NullDefault.getString(req.getParameter("fil260smlSend"), "0")));
        paramMdl.setFormData(filForm);

        //入力値チェック
        ActionErrors errors = filForm.validateCheck(reqMdl);
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

        Fil260knBiz biz = new Fil260knBiz(reqMdl, con);
        Fil260knForm filForm = new Fil260knForm();
        Fil260knParamModel paramMdl = new Fil260knParamModel();
        paramMdl.setParam(filForm);
        paramMdl.setFil260smlSendKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("fil260smlSendKbn"), "0")));
        paramMdl.setFil260smlSend(Integer.parseInt(
                NullDefault.getString(req.getParameter("fil260smlSend"), "0")));
        biz.updateFileSmailSetting(paramMdl);
        paramMdl.setFormData(filForm);
        req.setAttribute("fil260Form", filForm);
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
            RequestModel reqMdl, Fil260knParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.filekanri") + "] ";
        value += "\r\n";
        value += "[" + gsMsg.getMessage("shortmail.notification") + "] ";
        String[] setting = {
                gsMsg.getMessage("cmn.set.eachuser"),
                gsMsg.getMessage("cmn.set.the.admin")
        };
        value += setting[paramMdl.getFil260smlSendKbn()];
        if (paramMdl.getFil260smlSendKbn() == GSConstFile.FAC_SMAIL_SEND_KBN_ADMIN) {
            String[] notify = {
                    gsMsg.getMessage("cmn.notify"),
                    gsMsg.getMessage("cmn.dont.notify")
            };
            value += "\r\n";
            value += notify[paramMdl.getFil260smlSend()];
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
     * @throws Exception 実行例外
     * @return 表示・非表示判定
     */
    public boolean doPconfSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        if (filBiz.canSetSmailConf()) {
            CommonBiz cmnBiz = new CommonBiz();
            //ショートメールは利用可能か判定
            return cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_FILE, pconfig);
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

        Fil130Biz biz = new Fil130Biz(con);
        Fil130Form filForm = new Fil130Form();
        Fil130ParamModel paramMdl = new Fil130ParamModel();
        paramMdl.setParam(filForm);
        biz.setInitData(paramMdl, reqMdl.getSmodel());
        paramMdl.setFormData(filForm);
        req.setAttribute("fil130Form", filForm);
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

        Fil130knForm filForm = new Fil130knForm();
        Fil130knParamModel paramMdl = new Fil130knParamModel();

        paramMdl.setParam(filForm);
        paramMdl.setFil130SmailSend(
                NullDefault.getString(req.getParameter("fil130SmailSend"), "0"));
        paramMdl.setFormData(filForm);

        //入力チェック
        ActionErrors errors = filForm.validateCheck(reqMdl);
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

        Fil130knBiz biz = new Fil130knBiz(con);
        Fil130knForm filForm = new Fil130knForm();
        Fil130knParamModel paramMdl = new Fil130knParamModel();

        paramMdl.setParam(filForm);
        paramMdl.setParam(filForm);
        paramMdl.setFil130SmailSend(
                NullDefault.getString(req.getParameter("fil130SmailSend"), "0"));
        biz.registerData(paramMdl, reqMdl.getSmodel());
        paramMdl.setFormData(filForm);
        req.setAttribute("fil130knForm", filForm);
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
            RequestModel reqMdl, Fil130knParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.filekanri") + "] ";
        value += "\r\n";
        value += "[" + gsMsg.getMessage("shortmail.notification") + "] ";
        String[] notify = {
                gsMsg.getMessage("cmn.dont.notify"),
                gsMsg.getMessage("cmn.notify")
        };
        value += notify[Integer.parseInt(paramMdl.getFil130SmailSend())];

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
     * <p>日報個人設定ショートメール通知設定画面ユーザ・グループ追加時に実行される
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
        //ファイル管理のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_FILE)
                && canPlgList.contains(GSConst.PLUGIN_ID_FILE)) {
            return true;
        }
        return false;
    }
}