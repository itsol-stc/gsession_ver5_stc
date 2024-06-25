package jp.groupsession.v2.ntp.ntp081;

import java.beans.IntrospectionException;
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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp081Action extends AbstractNippouAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp081Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp081Form thisForm = (Ntp081Form) form;

        if (cmd.equals("ntp081Back")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("ntp080");

        } else if (cmd.equals("ntp081Ok")) {
            log__.debug("OKボタンクリック");
            forward = __doKakunin(map, thisForm, req, res, con);

        } else if (cmd.equals("ntp081commit")) {
            //登録 戻る
            forward = __doCommit(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Ntp081Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        Ntp081Biz biz = new Ntp081Biz(getRequestModel(req), con);

        Ntp081ParamModel paramMdl = new Ntp081ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
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
     */
    private ActionForward __doKakunin(ActionMapping map, Ntp081Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("確認画面");

        ActionErrors errors = form.validateNtp081();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //トランザクショントークン設定
        saveToken(req);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        String mkey1 = "日報基本設定";
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));
        cmn999Form.addHiddenParam("ntp081KyoyuFlg", form.getNtp081KyoyuFlg());
        cmn999Form.addHiddenParam("ntp081HourDivision", form.getNtp081HourDivision());
        cmn999Form.addHiddenParam("ntp081ColCmt1", form.getNtp081ColCmt1());
        cmn999Form.addHiddenParam("ntp081ColCmt2", form.getNtp081ColCmt2());
        cmn999Form.addHiddenParam("ntp081ColCmt3", form.getNtp081ColCmt3());
        cmn999Form.addHiddenParam("ntp081ColCmt4", form.getNtp081ColCmt4());
        cmn999Form.addHiddenParam("ntp081ColCmt5", form.getNtp081ColCmt5());
        cmn999Form.addHiddenParam("ntp081MikomidoCmt1", form.getNtp081MikomidoCmt1());
        cmn999Form.addHiddenParam("ntp081MikomidoCmt2", form.getNtp081MikomidoCmt2());
        cmn999Form.addHiddenParam("ntp081MikomidoCmt3", form.getNtp081MikomidoCmt3());
        cmn999Form.addHiddenParam("ntp081MikomidoCmt4", form.getNtp081MikomidoCmt4());
        cmn999Form.addHiddenParam("ntp081MikomidoCmt5", form.getNtp081MikomidoCmt5());
        cmn999Form.addHiddenParam("ntp081KakuteiFlg", form.getNtp081KakuteiFlg());


        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        //共通メッセージ画面を表示
        return map.findForward("gf_msg");
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
     * @throws IntrospectionException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doCommit(ActionMapping map, Ntp081Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException {
        log__.debug("登録処理");
        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        Ntp081Biz biz = new Ntp081Biz(getRequestModel(req), con);
        BaseUserModel umodel = getSessionUserModel(req);

        Ntp081ParamModel paramMdl = new Ntp081ParamModel();
        paramMdl.setParam(form);
        biz.setAconfSetting(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ntp080");
        cmn999Form.setUrlOK(urlForward.getPath());
        form.setHiddenParam(cmn999Form);

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, "日報基本設定"));
        req.setAttribute("cmn999Form", cmn999Form);

        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");
        //ログ出力処理
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, "");

        //共通メッセージ画面(OK)を表示
        forward = map.findForward("gf_msg");
        return forward;
    }

}