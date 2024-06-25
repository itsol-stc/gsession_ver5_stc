package jp.groupsession.v2.ip.ipk080;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.ip.AbstractIpAdminAction;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] IP管理 管理者メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk080Action extends AbstractIpAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk080Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("START");
        ActionForward forward = null;
        Ipk080Form ipkForm = (Ipk080Form) form;

        con.setAutoCommit(true);
        //権限チェック
        IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
        if (!ipkAdminInfo.isGsIpAdm(getRequestModel(req), con)) {
            log__.debug("管理者権限無しエラー");
            return map.findForward("gf_power");
        }

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("===CMD=== " + cmd);
        //戻るボタンクリック
        if (cmd.equals("ipk080Return")) {
            forward = __doBack(map, ipkForm);

        //管理者設定ボタンクリック
        } else if (cmd.equals("adminSetting")) {
            forward = map.findForward("adminSetting");

         //スペックマスタメンテナンスボタンクリック
        } else if (cmd.equals("specMstSetting")) {
            forward = map.findForward("specMstSetting");

        //初期表示
        } else {
            forward = map.getInputForward();
        }
        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return アクションフォーム
     */
    public ActionForward __doBack(ActionMapping map, Ipk080Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("ipk080Return");
    }
}