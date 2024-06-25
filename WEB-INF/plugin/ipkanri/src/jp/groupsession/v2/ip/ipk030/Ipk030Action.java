package jp.groupsession.v2.ip.ipk030;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAdminAction;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IP管理 全ネットワーク管理者設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk030Action extends AbstractIpAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk030Action.class);

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
        Ipk030Form ipkForm = (Ipk030Form) form;

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
        if (cmd.equals("ipk030Return")) {
            forward = map.findForward("ipk030Return");
        //登録ボタンクリック
        } else if (cmd.equals("networkAdminEdit")) {
            forward = __doInsertAdmin(map, ipkForm, req, res, con);
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
        Ipk030Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        con.setAutoCommit(true);

        Ipk030ParamModel paramMdl = new Ipk030ParamModel();
        paramMdl.setParam(form);
        Ipk030Biz biz = new Ipk030Biz();
        //管理者情報をセットする。
        biz.setInitAdminData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        //トランザクショントークン設定
        saveToken(req);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInsertAdmin(
        ActionMapping map,
        Ipk030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Ipk030Biz biz = new Ipk030Biz();
        biz.setNetworkAdmin(form.getAdminSidList(), con, getSessionUserSid(req));

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl, textEdit, GSConstLog.LEVEL_INFO, "");

        forward = __doInsertAdminCompDsp(map, req, form);
        return forward;
    }

    /**
     * <br>[機  能] 登録完了画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * <br>登録完了画面設定
     * @param map アクションマッピング
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward
     */
    private ActionForward __doInsertAdminCompDsp(
            ActionMapping map, HttpServletRequest req, Ipk030Form form) {

        GsMessage gsMsg = new GsMessage();
        String textZenNetworkKanri = gsMsg.getMessage(req, "cmn.preferences");

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ipk030Return");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                  textZenNetworkKanri));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }
}