package jp.groupsession.v2.fil.fil200;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 管理者設定メニュー画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil200Action extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil200Action.class);

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("fil200Action START");

        ActionForward forward = null;
        Fil200Form thisForm = (Fil200Form) form;

        //メイン管理者設定より遷移の場合、遷移元をクリアする。
        if (thisForm.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            thisForm.setBackDsp("");
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil200back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil200baseConf")) {
            //基本設定リンククリック
            forward = map.findForward("fil210");

        } else if (cmd.equals("fil200cabinetConf")) {
            //キャビネット管理設定リンククリック
            forward = map.findForward("fil220");

        } else if (cmd.equals("fil200myCabinetBaseConf")) {
            //個人キャビネット使用許可設定リンククリック
            forward = map.findForward("fil290");

        } else if (cmd.equals("fil200myCabinetConf")) {
            //個人キャビネット管理設定リンククリック
            forward = map.findForward("fil280");

        } else if (cmd.equals("fil200deleteFile")) {
            //ファイル一括削除リンククリック
            forward = map.findForward("fil230");

        } else if (cmd.equals("fil200call")) {
            //更新通知一括更新リンククリック
            forward = map.findForward("fil250");

        } else if (cmd.equals("fil200smlConf")) {
            //ショートメール通知設定リンククリック
            forward = map.findForward("fil260");

        } else if (cmd.equals("fil200logCnt")) {
            //統計情報リンククリック
            forward = map.findForward("fil270");

        } else if (cmd.equals("fil200gaika")) {
            //外貨マスタクリック
            forward = map.findForward("fil310");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        if (__canUseSmlConf(form, req, con)) {
            form.setCanUseSml(GSConst.PLUGIN_USE);
        } else {
            form.setCanUseSml(GSConst.PLUGIN_NOT_USE);
        }

        form.setCanUsePrivate(__getUsePrivateConf(con));

        //GS管理者情報を設定
        BaseUserModel buMdl = getSessionUserModel(req);
        form.setFil200GsAdminFlg(buMdl.getAdminFlg());

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil200Form form) {

        ActionForward forward = null;

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            forward = map.findForward("mainAdmSetting");
        } else if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
            forward = map.findForward("fil040");
        } else {
            forward = map.findForward("cabinetMain");
        }
        return forward;
    }
    /**
     * <br>[機  能] ショートメール設定が利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private boolean __canUseSmlConf(Fil200Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }

    /**
     * <br>[機  能] 個人キャビネットが利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private int __getUsePrivateConf(Connection con)
    throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con);
        FileAconfModel aconfMdl = aconfDao.select();

        if (aconfMdl == null) {
            aconfMdl = new FileAconfModel();
            aconfMdl.init();
        }
        return aconfMdl.getFacPersonalKbn();
    }
}

