package jp.groupsession.v2.ntp.ntp133;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 日報 商品カテゴリ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp133Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp133Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Ntp130");
        ActionForward forward = null;

        Ntp133Form thisForm = (Ntp133Form) form;

//        //権限チェック
//        forward = checkPow(map, req, con);
//        if (forward != null) {
//            return forward;
//        }

        //カテゴリ「未設定」を編集・削除しようとした場合
        if (thisForm.getNtp130EditSid() == 1) {
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("ok")) {
            log__.debug("OKボタンクリック");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタンクリック");
            forward = __doDeleteCat(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("input_back")) {
            log__.debug("確認画面から戻る");
            forward = map.getInputForward();

        } else if (cmd.equals("ntp133back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("listback");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Ntp130");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
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
    private ActionForward __doInit(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Ntp133Biz biz = new Ntp133Biz(getRequestModel(req));

        Ntp133ParamModel paramMdl = new Ntp133ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        
        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateNtp133(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //登録、または更新処理を行う
        Ntp133Biz biz = new Ntp133Biz(getRequestModel(req));

        Ntp133ParamModel paramMdl = new Ntp133ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, con, cntCon, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage(req);
        ntpBiz.outPutLog(
                map, req, res,
                gsMsg.getMessage(req, "cmn.entry"),
                GSConstLog.LEVEL_INFO, "[CategoryName]" + form.getNtp133CategoryName()
                + " [CategoryBiko]" + NullDefault.getString(form.getNtp133bikou(), ""));

        return __setTourokuKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
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
    private ActionForward __doDeleteCat(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //削除チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.deleteCheck(con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
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
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //カテゴリを削除する
        Ntp133Biz biz = new Ntp133Biz(getRequestModel(req));

        Ntp133ParamModel paramMdl = new Ntp133ParamModel();
        paramMdl.setParam(form);
        biz.deleteCat(con, paramMdl);
        paramMdl.setFormData(form);

        //ログ出力処理
        NtpCommonBiz usrBiz = new NtpCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage();
        String opCode = gsMsg.getMessage(req, "cmn.delete");

        usrBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getNtp133CategoryName());


        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("listback");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "cmn.category")));
        //パラメータセット
        cmn999Form.addHiddenParam("ntp130EditSid", form.getNtp130EditSid());
        cmn999Form.addHiddenParam("ntp130DspKbn", form.getNtp130DspKbn());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    
    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setTourokuKanryoDsp(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("listback");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String procMode = form.getNtp130ProcMode();
        if (procMode.equals("add")) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object",
                            gsMsg.getMessage(req, "cmn.category")));
        } else if (procMode.equals("edit")) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object",
                            gsMsg.getMessage(req, "cmn.category")));
        }
        form.setNtp130HiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("ntp130DspKbn", form.getNtp130DspKbn());

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除確認画面<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Ntp133Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        int editSid = form.getNtp130EditSid();
        //商品一覧を取得
        Ntp133Biz biz = new Ntp133Biz(getRequestModel(req));
        ArrayList<String> list = biz.getDeleteLabList(con, editSid);
        String delMsg = "";
        boolean labExis = false;

        if (list.size() > 0) {
            //対象のカテゴリに商品が存在する場合
            for (int i = 0; i < list.size(); i++) {
                delMsg += "・";
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(list.get(i), ""));

                //最後の要素以外は改行を挿入
                if (i < list.size() - 1) {
                    delMsg += "<br>";
                }
            }
            form.setCatKbn(GSConstNippou.CATEGORY_EXIST_YES);
            labExis = true;
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("ntp133back");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=input_back");

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("deleteok");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //カテゴリ内に商品が存在する場合
        if (labExis) {
            //メッセージ
            MessageResources msgRes = getResources(req);
            String msg = biz.getDeleteLabAndCatMsg(con, form.getNtp130EditSid(), msgRes, delMsg);
            cmn999Form.setMessage(msg);
        } else {
            //メッセージ
            MessageResources msgRes = getResources(req);
            String msg = biz.getDeletePosMsg(con, form.getNtp130EditSid(), msgRes);
            cmn999Form.setMessage(msg);
        }

        //画面パラメータをセット
        cmn999Form.addHiddenParam("ntp130EditSid", form.getNtp130EditSid());
        cmn999Form.addHiddenParam("ntp133bikou", form.getNtp133bikou());
        cmn999Form.addHiddenParam("ntp133CategoryName", form.getNtp133CategoryName());
        cmn999Form.addHiddenParam("catKbn", form.getCatKbn());
        cmn999Form.addHiddenParam("ntp130ProcMode", form.getNtp130ProcMode());
        cmn999Form.addHiddenParam("ntp130DspKbn", form.getNtp130DspKbn());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


//    /**
//     * <br>[機  能] 権限チェック
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param map ActionMapping
//     * @param req HttpServletRequest
//     * @param con DB Connection
//     * @return ActionForward
//     * @throws Exception 実行時例外
//     */
//    public ActionForward checkPow(ActionMapping map,
//            HttpServletRequest req, Connection con)
//    throws Exception {
//
//        CommonBiz cmnBiz = new CommonBiz();
//        BaseUserModel usModel = getSessionUserModel(req);
//        boolean gsAdmFlg = cmnBiz.isPluginAdmin(con, usModel, GSConstAddress.PLUGIN_ID_ADDRESS);
//
//        if (!gsAdmFlg) {
//            con.setAutoCommit(true);
//            NtpAdmConfDao dao = new NtpAdmConfDao(con);
//            NtpAdmConfModel model = dao.select();
//            con.setAutoCommit(false);
//            if (model != null && model.getAacAlbEdit() == GSConstAddress.POW_LIMIT) {
//                return getNotAdminSeniPage(map, req);
//            }
//        }
//        return null;
//    }


}
