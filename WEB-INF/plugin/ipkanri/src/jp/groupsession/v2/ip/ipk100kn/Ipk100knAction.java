package jp.groupsession.v2.ip.ipk100kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAdminAction;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス 登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk100knAction extends AbstractIpAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk100knAction.class);

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
        Ipk100knForm ipkForm = (Ipk100knForm) form;

        //権限チェック
        IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
        if (!ipkAdminInfo.isGsIpAdm(getRequestModel(req), con)) {
            log__.debug("管理者権限無しエラー");
            return map.findForward("gf_power");
        }

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug(" CMD = " + cmd);
        //戻るボタンクリック
        if (cmd.equals("ipk100knReturn")) {
            forward = map.findForward("ipk100knReturn");

        //確定ボタンクリック
        } else if (cmd.equals("ipk100knTouroku")) {
            forward = __doAddSpecData(map, ipkForm, req, res, con);

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
        Ipk100knForm form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        Ipk100knParamModel paramMdl = new Ipk100knParamModel();
        paramMdl.setParam(form);
        Ipk100knBiz biz = new Ipk100knBiz();

        con.setAutoCommit(true);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] スペック情報を登録する。
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
    private ActionForward __doAddSpecData(ActionMapping map,
        Ipk100knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        int sessionUsrSid = getSessionUserSid(req);

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //スペック情報を登録する。
        Ipk100knParamModel paramMdl = new Ipk100knParamModel();
        paramMdl.setParam(form);
        Ipk100knBiz biz = new Ipk100knBiz();
        biz.setSpecData(paramMdl, con, sessionUsrSid, cntCon);
        paramMdl.setFormData(form);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textTouroku = gsMsg.getMessage("cmn.entry");
        String textHenkou = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        IpkBiz ipkBiz = new IpkBiz(con);
        String opCode = "";
        if (form.getEditMode().equals(IpkConst.IPK_SPECM_TUIKA)) {
            opCode = textTouroku;
        } else if (form.getEditMode().equals(IpkConst.IPK_SPECM_HENKOU)) {
            opCode = textHenkou;
        }

        int specKbn = NullDefault.getInt(form.getSpecKbn(), -1);
        String title = "";
        if (specKbn == IpkConst.IPK_SPECKBN_CPU) {
            title = "[cpu]";
        } else if (specKbn == IpkConst.IPK_SPECKBN_MEMORY) {
            title = "[memory]";
        } else if (specKbn == IpkConst.IPK_SPECKBN_HD) {
            title = "[hd]";
        }
        ipkBiz.outPutLog(map, reqMdl, opCode,
                GSConstLog.LEVEL_INFO, title + form.getIpk100name());

        //登録完了画面の設定
        forward = __doInsertCompDsp(map, req, form);
        return forward;
    }

    /**
     * <br>[機  能] 登録完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param form Ipk100knForm
     * @return ActionForward
     */
    private ActionForward __doInsertCompDsp(ActionMapping map,
            HttpServletRequest req, Ipk100knForm form) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("kanryo");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();

        String specKbn = IpkConst.TEXT_CPU;
        if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_MEMORY))) {
            String textMemory = gsMsg.getMessage(req, "cmn.memory");
            specKbn = textMemory;
        } else if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_HD))) {
            specKbn = IpkConst.TEXT_HD;
        }
        String editMode = form.getEditMode();
        if (editMode.equals(IpkConst.IPK_SPECM_TUIKA)) {
            cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", specKbn));
        } else if (editMode.equals(IpkConst.IPK_SPECM_HENKOU)) {
            cmn999Form.setMessage(msgRes.getMessage("hensyu.kanryo.object", specKbn));
        }

        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("specKbn", form.getSpecKbn());
        cmn999Form.addHiddenParam("editMode", form.getEditMode());
        req.setAttribute("cmn999Form", cmn999Form);
        return forward;
    }
}