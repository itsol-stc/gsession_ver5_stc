package jp.groupsession.v2.ip.ipk100;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAdminAction;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス 登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk100Action extends AbstractIpAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk100Action.class);

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
        Ipk100Form ipkForm = (Ipk100Form) form;

        con.setAutoCommit(true);
        //権限チェック
        IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
        if (!ipkAdminInfo.isGsIpAdm(getRequestModel(req), con)) {
            log__.debug("管理者権限無しエラー");
            return map.findForward("gf_power");
        }
        con.setAutoCommit(false);

        //「スクロール位置」パラメータに不正な値が設定されている場合、"0"に置き換える
        String scroll = NullDefault.getString(ipkForm.getIpk100scroll(), "");
        if (!ValidateUtil.isNumber(scroll)) {
            ipkForm.setIpk100scroll("0");
        }

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("CMD = " + cmd);
        //戻るボタンクリック
        if (cmd.equals("ipk100Return")) {
            forward = map.findForward("ipk100Return");

        //OKボタンクリック
        } else if (cmd.equals("ipk100Touroku")) {
            forward = __doValidateCheck(map, ipkForm, req, con);

        //削除ボタンクリック
        } else if (cmd.equals("ipk100Delete")) {
            forward = __doDeleteKnDsp(map, ipkForm, req);

        //削除OKボタンクリック
        } else if (cmd.equals("ipk100DeleteOk")) {
            forward = __doDelete(map, ipkForm, req, res, con);

        //削除cancelボタンクリック
        } else if (cmd.equals("deleteCancel")) {
            forward = __doInitAg(map, ipkForm, con);

        //登録確認画面から戻ってきた場合
        } else if (cmd.equals("ipk100knReturn")) {
            forward = __doInitAg(map, ipkForm, con);

        //表示順ラジオクリック
        } else if (cmd.equals("radioChange")) {
            forward = __doInitAg(map, ipkForm, con);

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
        Ipk100Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        Ipk100ParamModel paramMdl = new Ipk100ParamModel();
        paramMdl.setParam(form);
        Ipk100Biz biz = new Ipk100Biz();

        //初期表示情報を設定する。
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        //ヘルプパラメータを設定する。
        __setHelpMode(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム

     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInitAg(ActionMapping map,
        Ipk100Form form,
        Connection con)
        throws SQLException {

        Ipk100ParamModel paramMdl = new Ipk100ParamModel();
        paramMdl.setParam(form);
        Ipk100Biz biz = new Ipk100Biz();

        //初期表示情報を設定する。
        biz.setInitDataAg(paramMdl, con);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }
    /**
     * <br>[機  能] 削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doDelete(ActionMapping map,
        Ipk100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

        ActionForward forward = null;
        Ipk100Biz biz = new Ipk100Biz();
        int ismSid = NullDefault.getInt(form.getIsmSid(), 0);
        int specKbn = NullDefault.getInt(form.getSpecKbn(), 0);
        //スペックマスタの項目の削除を行う。
        biz.deleteData(ismSid, con, getSessionUserSid(req), specKbn);

        //ログ出力処理
        IpkBiz ipkBiz = new IpkBiz(con);
        String title = "";
        if (specKbn == IpkConst.IPK_SPECKBN_CPU) {
            title = "[cpu]";
        } else if (specKbn == IpkConst.IPK_SPECKBN_MEMORY) {
            title = "[memory]";
        } else if (specKbn == IpkConst.IPK_SPECKBN_HD) {
            title = "[hd]";
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDelete = gsMsg.getMessage("cmn.delete");

        ipkBiz.outPutLog(map, reqMdl, textDelete,
                GSConstLog.LEVEL_INFO, title + form.getIpk100name());

        //削除確認画面を設定する。
        forward = __doDeleteCompDsp(map, form, req);
        return forward;
    }

    /**
     * <br>[機  能] 削除確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteKnDsp(ActionMapping map, Ipk100Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        ActionForward forwardCancel = null;

        // トランザクショントークン設定
        this.saveToken(req);

        //スペック情報削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("ipk100DeleteOk");
        cmn999Form.setUrlOK(forwardOK.getPath());
        forwardCancel = map.findForward("ipk100DeleteCancel");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                __getSpecKbn(form, req)));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("ismSid", form.getIsmSid());
        cmn999Form.addHiddenParam("editMode", form.getEditMode());
        cmn999Form.addHiddenParam("ipk100name", form.getIpk100name());
        cmn999Form.addHiddenParam("ipk100specLevel", form.getIpk100specLevel());
        cmn999Form.addHiddenParam("ipk100scroll", form.getIpk100scroll());
        cmn999Form.addHiddenParam("ipk100biko", form.getIpk100biko());
        cmn999Form.addHiddenParam("specKbn", form.getSpecKbn());
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteCompDsp(ActionMapping map, Ipk100Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        //スペック情報の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("ipk100DeleteComp");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                __getSpecKbn(form, req)));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("ismSid", form.getIsmSid());
        cmn999Form.addHiddenParam("specKbn", form.getSpecKbn());
        return forward;
    }

    /**
     * <br>[機  能] スペック区分をメッセージ表示用に変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private String __getSpecKbn(Ipk100Form form, HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textMemory = gsMsg.getMessage(req, "cmn.memory");

        String specKbn = IpkConst.TEXT_CPU;
        if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_MEMORY))) {
            specKbn = textMemory;
        } else if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_HD))) {
            specKbn = IpkConst.TEXT_HD;
        }
        return specKbn;
    }

    /**
     * <br>[機  能] 入力チェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doValidateCheck(ActionMapping map,
        Ipk100Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        ActionErrors errors = null;
        errors = form.validateCheck(getRequestModel(req));
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return __doInitAg(map, form, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        return map.findForward("ipk100Touroku");
    }

    /**
     * <br>[機  能] ヘルプパラメータを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     */
    private void __setHelpMode(Ipk100Form form) {

        if (form.getEditMode().equals(IpkConst.IPK_SPECM_TUIKA)) {
            //登録時
            if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_MEMORY))) {
                //メモリ登録
                form.setIpk100helpMode(IpkConst.IPK_HELP_MEMORY);
            } else if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_HD))) {
                //HD登録
                form.setIpk100helpMode(IpkConst.IPK_HELP_HD);
            } else {
                //CPU登録
                form.setIpk100helpMode(IpkConst.IPK_HELP_CPU);
            }
        } else if (form.getEditMode().equals(IpkConst.IPK_SPECM_HENKOU)) {
            //編集時
            if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_MEMORY))) {
                //メモリ編集
                form.setIpk100helpMode(IpkConst.IPK_HELP_MEMORY_HENSYUU);
            } else if (form.getSpecKbn().equals(String.valueOf(IpkConst.IPK_SPECKBN_HD))) {
                //HD編集
                form.setIpk100helpMode(IpkConst.IPK_HELP_HD_HENSYUU);
            } else {
                //CPU編集
                form.setIpk100helpMode(IpkConst.IPK_HELP_CPU_HENSYUU);
            }
        }
    }
}