package jp.groupsession.v2.ntp.ntp091;

import java.lang.reflect.InvocationTargetException;
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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp091Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp091Action.class);

    /**
     * <br>アクション実行
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
        Ntp091Form ntpForm = (Ntp091Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp091kakunin")) {
            log__.debug("確認");
            forward = __doKakunin(map, ntpForm, req, res, con);

        } else if (cmd.equals("ntp091commit")) {
            log__.debug("登録 戻る");
            forward = __doCommit(map, ntpForm, req, res, con);

        } else if (cmd.equals("ntp091back")) {
            log__.debug("戻る");
            forward = __doBack(map, ntpForm, req, res, con);

        } else if (cmd.equals("changeGrp")) {
            log__.debug("グループコンボ変更");
            forward = __doReload(map, ntpForm, req, res, con);

        } else if (cmd.equals("removeUser")) {
            log__.debug("削除(左矢印)ボタン押下");
            forward = __doLeft(map, ntpForm, req, res, con);

        } else if (cmd.equals("addUser")) {
            log__.debug("追加(右矢印)ボタン押下");
            forward = __doRight(map, ntpForm, req, res, con);

        } else if (cmd.equals("ntp091reload")) {
            log__.debug("再表示");
            forward = __doReload(map, ntpForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Ntp091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws
            SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        log__.debug("確認");
        ActionErrors errors = form.validateCheckNtp091(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doReload(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp091Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        String mkey1 = "日報 初期値";
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("ntp091DefFrH", form.getNtp091DefFrH());
        cmn999Form.addHiddenParam("ntp091DefFrM", form.getNtp091DefFrM());
        cmn999Form.addHiddenParam("ntp091DefToH", form.getNtp091DefToH());
        cmn999Form.addHiddenParam("ntp091DefToM", form.getNtp091DefToM());
        cmn999Form.addHiddenParam("ntp091PubFlg", form.getNtp091PubFlg());
        cmn999Form.addHiddenParam("ntp091Edit", form.getNtp091Edit());
        cmn999Form.addHiddenParam("ntp091Fcolor", form.getNtp091Fcolor());
        cmn999Form.addHiddenParam("ntp091DefGyomu", form.getNtp091DefGyomu());
        cmn999Form.addHiddenParam("ntp091userSid", form.getNtp091userSid());
        cmn999Form.addHiddenParam("ntp091groupSid", form.getNtp091groupSid());

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Ntp091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("登録");
        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        Ntp091Biz biz = new Ntp091Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Ntp091ParamModel paramMdl = new Ntp091ParamModel();
        paramMdl.setParam(form);
        biz.setPconfSetting(paramMdl, umodel, con);
        biz.setPkakuninSetting(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, "");

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp091Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ntp091back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        String key1 = "日報初期値設定";
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Ntp091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("ntp091back");
        return forward;
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        log__.debug("初期表示");
        ActionForward forward = null;

        Ntp091Biz biz = new Ntp091Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Ntp091ParamModel paramMdl = new Ntp091ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }
    /**
     * <br>再表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doReload(ActionMapping map, Ntp091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        log__.debug("再表示");
        ActionForward forward = null;

        Ntp091Biz biz = new Ntp091Biz(getRequestModel(req));

        Ntp091ParamModel paramMdl = new Ntp091ParamModel();
        paramMdl.setParam(form);
        biz.setComboLabel(paramMdl, con);
        biz.setTitleColorMsg(paramMdl, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }
    /**
     * <br>[機  能] 削除(左矢印)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doLeft(
        ActionMapping map,
        Ntp091Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        CommonBiz cmnBiz = new CommonBiz();
        form.setNtp091userSid(
                cmnBiz.getDeleteMember(form.getNtp091SelectRightUser(), form.getNtp091userSid()));

        return __doReload(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 追加(右矢印)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doRight(
        ActionMapping map,
        Ntp091Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        CommonBiz cmnBiz = new CommonBiz();
        form.setNtp091userSid(
                cmnBiz.getAddMember(form.getNtp091SelectLeftUser(), form.getNtp091userSid()));

        return __doReload(map, form, req, res, con);
    }
}
