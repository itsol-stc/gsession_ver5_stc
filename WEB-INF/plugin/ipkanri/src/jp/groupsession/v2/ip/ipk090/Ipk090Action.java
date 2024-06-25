package jp.groupsession.v2.ip.ipk090;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.ip.AbstractIpAdminAction;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk090Action extends AbstractIpAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk090Action.class);

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
        Ipk090Form ipkForm = (Ipk090Form) form;

        con.setAutoCommit(true);
        //権限チェック
        IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
        if (!ipkAdminInfo.isGsIpAdm(getRequestModel(req), con)) {
            log__.debug("管理者権限無しエラー");
            return map.findForward("gf_power");
        }
        con.setAutoCommit(false);

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("===CMD=== " + cmd);
        //戻るボタンクリック
        if (cmd.equals("ipk090Return")) {
            forward = map.findForward("ipk090Return");

        //追加ボタン、名前クリック
        } else if (cmd.equals("specMstEdit")) {
            forward = map.findForward("specMstEdit");

        //初期表示
        } else {
            forward = __doInit(map, ipkForm, req, con);
        }
        log__.debug("END");
        return forward;
    }
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ipk090Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        Ipk090ParamModel paramMdl = new Ipk090ParamModel();
        paramMdl.setParam(form);
        Ipk090Biz biz = new Ipk090Biz();

        con.setAutoCommit(true);
        //スペック一覧を設定する。
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        //ヘルプパラメータを設定する。
        if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_MEMORY))) {
            form.setIpk090helpMode(IpkConst.IPK_HELP_MEMORY);
        } else if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_HD))) {
            form.setIpk090helpMode(IpkConst.IPK_HELP_HD);
        } else {
            form.setIpk090helpMode(IpkConst.IPK_HELP_CPU);
        }
        return map.getInputForward();
    }
}