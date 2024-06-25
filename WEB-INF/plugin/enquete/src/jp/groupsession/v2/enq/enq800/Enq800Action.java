package jp.groupsession.v2.enq.enq800;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] アンケート 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq800Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq800Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        log__.debug("Enq800Action_START");

        ActionForward forward = null;
        Enq800Form enq800Form = (Enq800Form) form;

        // アクセス権限チェック
        con.setAutoCommit(true);
        try {
            EnqCommonBiz enqBiz = new EnqCommonBiz();
            if (!enqBiz.checkPriPerm(getRequestModel(req), con, GSConstEnquete.DSP_ID_800)) {
                return __notUsePriPerm(map, enq800Form, req, res, con);
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq800dsp")) {
            // 表示設定
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq800dspMain")) {
            // メイン表示設定
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq800back")) {
            // 戻る
            forward = __doBack(map, enq800Form);

        } else {
            // 初期表示処理
            forward = __doInit(map, enq800Form, req, res, con);
        }

        log__.debug("Enq800Action_END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq800Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {

        log__.debug("初期表示処理");

        con.setAutoCommit(true);
        try {
            Enq800Biz biz = new Enq800Biz();
            Enq800ParamModel paramModel = new Enq800ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel, getRequestModel(req), con);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 後戻処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doBack(ActionMapping map, Enq800Form form) throws Exception {

        log__.debug("後戻処理");

        String fwdName = "";
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            fwdName = "mainPriSetting";
        } else {
            fwdName = "enq800backTo010";
        }

        return map.findForward(fwdName);
    }

    /**
     * <br>[機  能] 使用不可画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __notUsePriPerm(ActionMapping map,
                                          Enq800Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        log__.debug("使用不可画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            urlForward = map.findForward("mainPriSetting");
        } else {
            urlForward = map.findForward("enq800backTo010");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage();
        String textFnc = gsMsg.getMessage(req, "cmn.preferences2");
        cmn999Form.setMessage(msgRes.getMessage("error.cant.use.function", textFnc));

        // 画面パラメータをセット
        cmn999Form.addHiddenParam("enqEditMode", form.getEnqEditMode());
        cmn999Form.addHiddenParam("enq010folder", form.getEnq010folder());
        cmn999Form.addHiddenParam("enq010subFolder", form.getEnq010subFolder());
        cmn999Form.addHiddenParam("enq010initFlg", form.getEnq010initFlg());
        cmn999Form.addHiddenParam("editEnqSid", form.getEditEnqSid());
        cmn999Form.addHiddenParam("enq010type", form.getEnq010type());
        cmn999Form.addHiddenParam("enq010keyword", form.getEnq010keyword());
        cmn999Form.addHiddenParam("enq010keywordType", form.getEnq010keywordType());
        cmn999Form.addHiddenParam("enq010sendGroup", form.getEnq010sendGroup());
        cmn999Form.addHiddenParam("enq010sendUser", form.getEnq010sendUser());
        cmn999Form.addHiddenParam("enq010sendInput", form.getEnq010sendInput());
        cmn999Form.addHiddenParam("enq010sendInputText", form.getEnq010sendInputText());
        cmn999Form.addHiddenParam("enq010makeDateKbn", form.getEnq010makeDateKbn());
        cmn999Form.addHiddenParam("enq010makeDateFromYear", form.getEnq010makeDateFromYear());
        cmn999Form.addHiddenParam("enq010makeDateFromMonth", form.getEnq010makeDateFromMonth());
        cmn999Form.addHiddenParam("enq010makeDateFromDay", form.getEnq010makeDateFromDay());
        cmn999Form.addHiddenParam("enq010makeDateToYear", form.getEnq010makeDateToYear());
        cmn999Form.addHiddenParam("enq010makeDateToMonth", form.getEnq010makeDateToMonth());
        cmn999Form.addHiddenParam("enq010makeDateToDay", form.getEnq010makeDateToDay());
        cmn999Form.addHiddenParam("enq010pubDateKbn", form.getEnq010pubDateKbn());
        cmn999Form.addHiddenParam("enq010pubDateFromYear", form.getEnq010pubDateFromYear());
        cmn999Form.addHiddenParam("enq010pubDateFromMonth", form.getEnq010pubDateFromMonth());
        cmn999Form.addHiddenParam("enq010pubDateFromDay", form.getEnq010pubDateFromDay());
        cmn999Form.addHiddenParam("enq010pubDateToYear", form.getEnq010pubDateToYear());
        cmn999Form.addHiddenParam("enq010pubDateToMonth", form.getEnq010pubDateToMonth());
        cmn999Form.addHiddenParam("enq010pubDateToDay", form.getEnq010pubDateToDay());
        cmn999Form.addHiddenParam("enq010ansDateKbn", form.getEnq010ansDateKbn());
        cmn999Form.addHiddenParam("enq010ansDateFromYear", form.getEnq010ansDateFromYear());
        cmn999Form.addHiddenParam("enq010ansDateFromMonth", form.getEnq010ansDateFromMonth());
        cmn999Form.addHiddenParam("enq010ansDateFromDay", form.getEnq010ansDateFromDay());
        cmn999Form.addHiddenParam("enq010ansDateToYear", form.getEnq010ansDateToYear());
        cmn999Form.addHiddenParam("enq010ansDateToMonth", form.getEnq010ansDateToMonth());
        cmn999Form.addHiddenParam("enq010ansDateToDay", form.getEnq010ansDateToDay());
        cmn999Form.addHiddenParam("enq010anony", form.getEnq010anony());
        cmn999Form.addHiddenParam("enq010svType", form.getEnq010svType());
        cmn999Form.addHiddenParam("enq010svKeyword", form.getEnq010svKeyword());
        cmn999Form.addHiddenParam("enq010svKeywordType", form.getEnq010svKeywordType());
        cmn999Form.addHiddenParam("enq010svSendGroup", form.getEnq010svSendGroup());
        cmn999Form.addHiddenParam("enq010svSendUser", form.getEnq010svSendUser());
        cmn999Form.addHiddenParam("enq010svSendInput", form.getEnq010svSendInput());
        cmn999Form.addHiddenParam("enq010svSendInputText", form.getEnq010svSendInputText());
        cmn999Form.addHiddenParam("enq010svMakeDateKbn", form.getEnq010svMakeDateKbn());
        cmn999Form.addHiddenParam("enq010svMakeDateFromYear", form.getEnq010svMakeDateFromYear());
        cmn999Form.addHiddenParam("enq010svMakeDateFromMonth", form.getEnq010svMakeDateFromMonth());
        cmn999Form.addHiddenParam("enq010svMakeDateFromDay", form.getEnq010svMakeDateFromDay());
        cmn999Form.addHiddenParam("enq010svMakeDateToYear", form.getEnq010svMakeDateToYear());
        cmn999Form.addHiddenParam("enq010svMakeDateToMonth", form.getEnq010svMakeDateToMonth());
        cmn999Form.addHiddenParam("enq010svMakeDateToDay", form.getEnq010svMakeDateToDay());
        cmn999Form.addHiddenParam("enq010svPubDateKbn", form.getEnq010svPubDateKbn());
        cmn999Form.addHiddenParam("enq010svPubDateFromYear", form.getEnq010svPubDateFromYear());
        cmn999Form.addHiddenParam("enq010svPubDateFromMonth", form.getEnq010svPubDateFromMonth());
        cmn999Form.addHiddenParam("enq010svPubDateFromDay", form.getEnq010svPubDateFromDay());
        cmn999Form.addHiddenParam("enq010svPubDateToYear", form.getEnq010svPubDateToYear());
        cmn999Form.addHiddenParam("enq010svPubDateToMonth", form.getEnq010svPubDateToMonth());
        cmn999Form.addHiddenParam("enq010svPubDateToDay", form.getEnq010svPubDateToDay());
        cmn999Form.addHiddenParam("enq010svAnsDateKbn", form.getEnq010svAnsDateKbn());
        cmn999Form.addHiddenParam("enq010svAnsDateFromYear", form.getEnq010svAnsDateFromYear());
        cmn999Form.addHiddenParam("enq010svAnsDateFromMonth", form.getEnq010svAnsDateFromMonth());
        cmn999Form.addHiddenParam("enq010svAnsDateFromDay", form.getEnq010svAnsDateFromDay());
        cmn999Form.addHiddenParam("enq010svAnsDateToYear", form.getEnq010svAnsDateToYear());
        cmn999Form.addHiddenParam("enq010svAnsDateToMonth", form.getEnq010svAnsDateToMonth());
        cmn999Form.addHiddenParam("enq010svAnsDateToDay", form.getEnq010svAnsDateToDay());
        cmn999Form.addHiddenParam("enq010svAnony", form.getEnq010svAnony());
        cmn999Form.addHiddenParam("enq010pageTop", form.getEnq010pageTop());
        cmn999Form.addHiddenParam("enq010pageBottom", form.getEnq010pageBottom());
        cmn999Form.addHiddenParam("enq010priority", form.getEnq010priority());
        cmn999Form.addHiddenParam("enq010status", form.getEnq010status());
        cmn999Form.addHiddenParam("enq010svPriority", form.getEnq010svPriority());
        cmn999Form.addHiddenParam("enq010svStatus", form.getEnq010svStatus());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
