package jp.groupsession.v2.mem;

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
import jp.groupsession.v2.mem.mem030.Mem030Biz;
import jp.groupsession.v2.mem.mem030.Mem030Form;
import jp.groupsession.v2.mem.mem030.Mem030ParamModel;
import jp.groupsession.v2.mem.model.MemoAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] メモについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class MemIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] メモについての処理を行う
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
        //メモは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_MEMO, pconfig)) {
            return false;
        }

        Mem030Biz biz = new Mem030Biz(reqMdl);
        Mem030Form memForm = new Mem030Form();
        Mem030ParamModel paramMdl = new Mem030ParamModel();
        paramMdl.setParam(memForm);
        biz.setInitData(paramMdl, con);
        biz.setLabel(reqMdl, paramMdl);
        paramMdl.setFormData(memForm);
        req.setAttribute("mem030Form", memForm);
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
        
        Mem030Form memForm = new Mem030Form();
        req.getParameterMap();
        Mem030ParamModel paramMdl = new Mem030ParamModel();
        paramMdl.setParam(memForm);
        paramMdl.setMem030Kbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("mem030Kbn"), "0")));
        paramMdl.setMem030Year(Integer.parseInt(
                NullDefault.getString(req.getParameter("mem030Year"), "0")));
        paramMdl.setMem030Month(Integer.parseInt(
                NullDefault.getString(req.getParameter("mem030Month"), "0")));
        paramMdl.setFormData(memForm);

        //入力チェック
        return memForm.validateCheck(req);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] メモについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Mem030Biz biz = new Mem030Biz(reqMdl);
        Mem030Form memForm = new Mem030Form();
        req.getParameterMap();
        Mem030ParamModel paramMdl = new Mem030ParamModel();
        paramMdl.setParam(memForm);
        paramMdl.setMem030Kbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("mem030Kbn"), "0")));
        paramMdl.setMem030Year(Integer.parseInt(
                NullDefault.getString(req.getParameter("mem030Year"), "0")));
        paramMdl.setMem030Month(Integer.parseInt(
                NullDefault.getString(req.getParameter("mem030Month"), "0")));
        paramMdl.setFormData(memForm);

        MemoAdmConfModel conf = biz.setAutoDelete(paramMdl, reqMdl.getSmodel(), con);
        req.setAttribute("mem030Form", memForm);
        __setAtDelOpLog(con, req, reqMdl, conf);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param conf メモ管理者設定
     */
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, MemoAdmConfModel conf) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");
        String keikaYM = "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
        String delKbn = null;
        String year = null;
        String month = null;
        String value = "";
        value += "[" +  gsMsg.getMessage("memo.01") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        if (conf.getMacAtdelKbn() == 0) {
            delKbn = gsMsg.getMessage(req, "cmn.noset");
            value += delKbn;
        } else {
            delKbn = gsMsg.getMessage(req, "cmn.automatically.delete");
            year = gsMsg.getMessage(req, "cmn.year",
                    String.valueOf(conf.getMacAtdelY()));
            month = gsMsg.getMessage(req, "cmn.months",
                    String.valueOf(conf.getMacAtdelM()));
            value += delKbn + "\n" + keikaYM + year + month;
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
     * <br>[解  説] メモについての処理を行う
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

        return false;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] メモについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doManualDeleteCheck(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        return null;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] メモについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面のアカウント変更時に実行される
     * <br>[解  説] メモについての処理を行う
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
        //メモのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_MEMO)
                && canPlgList.contains(GSConst.PLUGIN_ID_MEMO)) {
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
        return false;
    }
}