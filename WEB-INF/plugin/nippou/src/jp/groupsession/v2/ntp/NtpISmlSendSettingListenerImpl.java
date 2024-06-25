package jp.groupsession.v2.ntp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.ntp085.Ntp085Biz;
import jp.groupsession.v2.ntp.ntp085.Ntp085Form;
import jp.groupsession.v2.ntp.ntp085.Ntp085ParamModel;
import jp.groupsession.v2.ntp.ntp095.Ntp095Biz;
import jp.groupsession.v2.ntp.ntp095.Ntp095Form;
import jp.groupsession.v2.ntp.ntp095.Ntp095ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpISmlSendSettingListenerImpl implements ISmlSendSettingListener {

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
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_NIPPOU, pconfig)) {
            return false;
        }

        Ntp085Biz biz = new Ntp085Biz(con, reqMdl);
        Ntp085Form ntpForm = new Ntp085Form();
        Ntp085ParamModel paramMdl = new Ntp085ParamModel();
        paramMdl.setParam(ntpForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(ntpForm);
        req.setAttribute("ntp085Form", ntpForm);
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
        
        Ntp085Form ntpForm = new Ntp085Form();
        Ntp085ParamModel paramMdl = new Ntp085ParamModel();
        paramMdl.setNtp085NoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085NoticeKbn"), "0")));
        paramMdl.setNtp085SmlNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085SmlNoticeKbn"), "0")));
        paramMdl.setNtp085SmlNoticePlace(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085SmlNoticePlace"), "0")));
        paramMdl.setNtp085CmtNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085CmtNoticeKbn"), "0")));
        paramMdl.setNtp085CmtSmlNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085CmtSmlNoticeKbn"), "0")));
        paramMdl.setNtp085GoodNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085GoodNoticeKbn"), "0")));
        paramMdl.setNtp085GoodSmlNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085GoodSmlNoticeKbn"), "0")));
        paramMdl.setFormData(ntpForm);
        
        //入力値チェック
        ActionErrors errors = ntpForm.validateCheck(reqMdl);
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

        Ntp085Biz biz = new Ntp085Biz(con, reqMdl);
        Ntp085Form ntpForm = new Ntp085Form();
        Ntp085ParamModel paramMdl = new Ntp085ParamModel();
        paramMdl.setParam(ntpForm);
        paramMdl.setNtp085NoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085NoticeKbn"), "0")));
        paramMdl.setNtp085SmlNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085SmlNoticeKbn"), "0")));
        paramMdl.setNtp085SmlNoticePlace(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085SmlNoticePlace"), "0")));
        paramMdl.setNtp085CmtNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085CmtNoticeKbn"), "0")));
        paramMdl.setNtp085CmtSmlNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085CmtSmlNoticeKbn"), "0")));
        paramMdl.setNtp085GoodNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085GoodNoticeKbn"), "0")));
        paramMdl.setNtp085GoodSmlNoticeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("ntp085GoodSmlNoticeKbn"), "0")));
        biz.setAconfSetting(paramMdl, reqMdl.getSmodel());
        paramMdl.setFormData(ntpForm);
        req.setAttribute("ntp085Form", ntpForm);
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
            RequestModel reqMdl, Ntp085ParamModel paramMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String[] setting = {
                gsMsg.getMessage("cmn.set.the.admin"),
                gsMsg.getMessage("cmn.set.eachuser")
        };
        String[] notify = {
                gsMsg.getMessage("cmn.dont.notify"),
                gsMsg.getMessage("cmn.notify")
        };
        String[] group = {
                gsMsg.getMessage("ntp.160"),
                gsMsg.getMessage("ntp.161")
        };

        String value = "";
        value += "[" +  gsMsg.getMessage("ntp.1") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("ntp.88") + "] ";
        value += setting[paramMdl.getNtp085NoticeKbn()];
        if (paramMdl.getNtp085NoticeKbn()
                == GSConstNippou.SML_NOTICE_ADM) {
            value += "\r\n";
            value += notify[paramMdl.getNtp085SmlNoticeKbn()];
        }
        if (paramMdl.getNtp085NoticeKbn()
                == GSConstNippou.SML_NOTICE_ADM
            && paramMdl.getNtp085SmlNoticeKbn() 
                == GSConstNippou.SML_NOTICE_YES) {
            value += "\r\n";
            value += group[paramMdl.getNtp085SmlNoticePlace()];
        }
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("ntp.89") + "] ";
        value += setting[paramMdl.getNtp085CmtNoticeKbn()];
        if (paramMdl.getNtp085CmtNoticeKbn()
                == GSConstNippou.SML_NOTICE_ADM) {
            value += "\r\n";
            value += notify[paramMdl.getNtp085CmtSmlNoticeKbn()];
        }
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("ntp.9") + "] ";
        value += setting[paramMdl.getNtp085GoodNoticeKbn()];
        if (paramMdl.getNtp085GoodNoticeKbn()
                == GSConstNippou.SML_NOTICE_ADM) {
            value += "\r\n";
            value += notify[paramMdl.getNtp085GoodSmlNoticeKbn()];
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

        NtpCommonBiz biz = new NtpCommonBiz(con, reqMdl);
        NtpAdmConfModel admConf = biz.getAdmConfModel(con);
        if (admConf.getNacSmlKbn() == GSConstNippou.SML_NOTICE_USR 
                || admConf.getNacCsmlKbn() == GSConstNippou.SML_NOTICE_USR 
                || admConf.getNacGsmlKbn() == GSConstNippou.SML_NOTICE_USR) {
            CommonBiz cmnBiz = new CommonBiz();
            //ショートメールは利用可能か判定
            return cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_NIPPOU, pconfig);
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

        Ntp095Biz biz = new Ntp095Biz(con, reqMdl);
        Ntp095Form ntpForm = new Ntp095Form();
        Ntp095ParamModel paramMdl = new Ntp095ParamModel();
        paramMdl.setParam(ntpForm);
        biz.setInitData(paramMdl, reqMdl.getSmodel());
        paramMdl.setFormData(ntpForm);
        req.setAttribute("ntp095Form", ntpForm);
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
        
        Ntp095Form ntpForm = new Ntp095Form();
        Ntp095ParamModel paramMdl = new Ntp095ParamModel();
        paramMdl.setNtp095Smail(
                NullDefault.getString(req.getParameter("ntp095Smail"), "0"));
        paramMdl.setNtp095CmtSmail(
                NullDefault.getString(req.getParameter("ntp095CmtSmail"), "0"));
        paramMdl.setNtp095GoodSmail(
                NullDefault.getString(req.getParameter("ntp095GoodSmail"), "0"));
        paramMdl.setNtp095memberSid(req.getParameterValues("ntp095memberSid"));
        paramMdl.setFormData(ntpForm);
        
        //入力値チェック
        ActionErrors errors = ntpForm.validateCheck(reqMdl);
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

        Ntp095Biz biz = new Ntp095Biz(con, reqMdl);
        Ntp095Form ntpForm = new Ntp095Form();
        Ntp095ParamModel paramMdl = new Ntp095ParamModel();
        paramMdl.setParam(ntpForm);
        paramMdl.setNtp095Smail(
                NullDefault.getString(req.getParameter("ntp095Smail"), "0"));
        paramMdl.setNtp095CmtSmail(
                NullDefault.getString(req.getParameter("ntp095CmtSmail"), "0"));
        paramMdl.setNtp095GoodSmail(
                NullDefault.getString(req.getParameter("ntp095GoodSmail"), "0"));
        paramMdl.setNtp095memberSid(req.getParameterValues("ntp095memberSid"));
        paramMdl.setNtp095NtpDspKbn(NullDefault.getInt(req.getParameter("ntp095NtpDspKbn"), 0));
        paramMdl.setNtp095CmtDspKbn(NullDefault.getInt(req.getParameter("ntp095CmtDspKbn"), 0));
        paramMdl.setNtp095GoodDspKbn(NullDefault.getInt(req.getParameter("ntp095GoodDspKbn"), 0));
        biz.setPconfSetting(paramMdl, reqMdl.getSmodel());
        paramMdl.setFormData(ntpForm);
        req.setAttribute("ntp095Form", ntpForm);
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
            Ntp095ParamModel paramMdl) throws NumberFormatException, SQLException {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String[] notify = {
                gsMsg.getMessage("cmn.dont.notify"),
                gsMsg.getMessage("cmn.notify")
        };

        String value = "";
        value += "[" +  gsMsg.getMessage("ntp.1") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("ntp.88") + "] ";
        value += notify[Integer.parseInt(paramMdl.getNtp095Smail())];
        value += "\r\n";
        value += gsMsg.getMessage("ntp.162") + ": ";
        if (Integer.parseInt(paramMdl.getNtp095Smail()) == GSConstNippou.SML_NOTICE_YES
                && paramMdl.getNtp095memberSid() != null) {
            List<String> list = Arrays.asList();
            for (String sid : list) {
                if (sid.substring(0, 1).equals("G")) {
                    //グループの時はそのグループに所属するユーザSIDを取得
                    int grpSid = Integer.parseInt(sid.substring(1));
                    CmnGroupmDao grpDao = new CmnGroupmDao(con);
                    CmnGroupmModel grpMdl = grpDao.select(grpSid);
                    value += grpMdl.getGrpName() + " ";
                } else {
                    CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                    CmnUsrmInfModel usrMdl = usrDao.select(Integer.parseInt(sid));
                    value += usrMdl.getUsiSei() + usrMdl.getUsiMei() + " ";
                }
            }
        } else if (paramMdl.getNtp095memberSid() == null) {
            value += gsMsg.getMessage("cmn.no");
        }
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("ntp.89") + "] ";
        value += notify[Integer.parseInt(paramMdl.getNtp095CmtSmail())];
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("ntp.9") + "] ";
        value += notify[Integer.parseInt(paramMdl.getNtp095GoodSmail())];

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

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_NIPPOU)) {
            return null;
        }
        Ntp095Biz biz = new Ntp095Biz(con, reqMdl);
        Ntp095Form ntpForm = new Ntp095Form();
        ntpForm.setNtp095memberSid(req.getParameterValues("ntp095memberSid"));
        ntpForm.setNtp095Smail(req.getParameter("ntp095Smail"));
        ntpForm.setNtp095CmtSmail(req.getParameter("ntp095CmtSmail"));
        ntpForm.setNtp095GoodSmail(req.getParameter("ntp095GoodSmail"));
        ntpForm.setNtp095groupSid(req.getParameter("ntp095groupSid"));
        ntpForm.setNtp095InitFlg(String.valueOf(GSConstNippou.NOT_INIT_FLG));

        //グループ変更
        Ntp095ParamModel paramMdl = new Ntp095ParamModel();
        paramMdl.setParam(ntpForm);
        biz.setInitData(paramMdl, reqMdl.getSmodel());
        paramMdl.setFormData(ntpForm);
        
        req.setAttribute("ntp095Form", ntpForm);
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
        //日報のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_NIPPOU)
                && canPlgList.contains(GSConst.PLUGIN_ID_NIPPOU)) {
            return true;
        }
        return false;
    }
}