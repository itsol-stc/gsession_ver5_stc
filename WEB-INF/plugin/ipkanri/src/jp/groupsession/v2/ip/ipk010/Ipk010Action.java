package jp.groupsession.v2.ip.ipk010;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAction;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] IP管理 ネットワーク一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk010Action extends AbstractIpAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk010Action.class);

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
        ActionForward forward = null;
        Ipk010Form ipkForm = (Ipk010Form) form;
        log__.debug("START");

        //GS管理者と全ネットワーク管理者を設定する。
        IpkAdminInfo kanri = new IpkAdminInfo();
        ipkForm.setAllAdm(kanri.isGsIpAdm(getRequestModel(req), con));

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("==== cmd ==== " + cmd);
        //追加ボタンクリック
        if (cmd.equals("networkAdd")) {
            forward = map.findForward("networkAdd");
        //変更ボタンクリック
        } else if (cmd.equals("networkEdit")) {
            forward = map.findForward("networkEdit");
        //削除OKボタンクリック 実際に削除
        } else if (cmd.equals("networkDeleteKn")) {
            forward = __doDelete(map, ipkForm, req, res, con);
        //削除ボタンクリック 確認画面表示
        } else if (cmd.equals("networkDelete")) {
            forward = __doDeleteKn(map, ipkForm.getNetSid(), req);
        //管理者設定ボタンクリック
        } else if (cmd.equals("admin")) {
            forward = map.findForward("admin");
        //ネットワーク名クリック
        } else if (cmd.equals("ipList")) {
            forward = map.findForward("ipList");
        //検索ボタンクリック
        } else if (cmd.equals("search")) {
            forward = map.findForward("search");
        //初期表示
        } else {
            forward = __doInit(map, ipkForm, req, con);
        }
        log__.info("END");
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
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ipk010Form form,
        HttpServletRequest req,
        Connection con)
        throws Exception {

        con.setAutoCommit(true);
        //ネットワーク一覧情報を取得
        Ipk010ParamModel paramMdl = new Ipk010ParamModel();
        paramMdl.setParam(form);
        Ipk010Biz biz = new Ipk010Biz();
        biz.setInitData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ネットワーク情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(ActionMapping map,
        Ipk010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {
        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        String netSid = form.getNetSid();

        //ネットワーク一覧情報を削除する。
        Ipk010Biz biz = new Ipk010Biz();
        String netName = biz.getNetName(netSid, con);
        biz.deleteNetwork(NullDefault.getInt(netSid, 0), con, getSessionUserSid(req));

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        //ログ出力処理
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                textDel, GSConstLog.LEVEL_TRACE, "[name]" + netName);
        //削除完了画面を表示する。
        forward = __doDeleteCompDsp(map, netSid, req);
        return forward;
    }

    /**
     * <br>[機  能] 削除確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param netSid ネットワークSID
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteKn(ActionMapping map, String netSid,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        ActionForward forwardCancel = null;

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        //ネットワーク削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("networkDeleteKn");
        cmn999Form.setUrlOK(forwardOK.getPath());
        forwardCancel = map.findForward("networkDeleteBack");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                textNetwork));
        cmn999Form.addHiddenParam("netSid", netSid);
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

        // トランザクショントークン設定
        saveToken(req);

        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param netSid ネットワークSID
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteCompDsp(ActionMapping map, String netSid,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        //ネットワーク削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("networkDeleteBack");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                textNetwork));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("netSid", netSid);
        return forward;
    }
}