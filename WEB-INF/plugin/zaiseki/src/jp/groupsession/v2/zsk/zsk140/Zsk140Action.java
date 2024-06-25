package jp.groupsession.v2.zsk.zsk140;

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
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席管理 管理者設定 座席表メンバーデフォルト表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk140Action extends AbstractZaisekiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk140Action.class);

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
        Zsk140Form zskForm = (Zsk140Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("zsk140kakunin")) {
            //OK
            forward = __doKakunin(map, zskForm, req, res, con);
        } else if (cmd.equals("changeOk")) {
            //確認画面からの遷移
            forward = __doCommit(map, zskForm, req, res, con);
        } else if (cmd.equals("zsk140back")) {
            //戻る
            forward = __doBack(map, zskForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, zskForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Zsk140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {
        con.setAutoCommit(true);
        Zsk140Biz biz = new Zsk140Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Zsk140ParamModel paramMdl = new Zsk140ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        ActionForward forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタン押下
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     */
    private ActionForward __doBack(ActionMapping map,
                                    Zsk140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = map.findForward("zsk020");
        return forward;
    }

    /**
     * <br>[機  能] 確認ボタン押下
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     */
    private ActionForward __doKakunin(ActionMapping map,
                                       Zsk140Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) {

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=changeOk");
        cmn999Form.setUrlCancel(map.findForward("mine").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        GsMessage gsMsg = new GsMessage();

        String textGroupMemDspSetting = gsMsg.getMessage(req, "cmn.default.setting");
        String mkey1 = textGroupMemDspSetting;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());

        cmn999Form.addHiddenParam("zsk140initKbn", form.getZsk140initKbn());
        cmn999Form.addHiddenParam("zsk140SortKbn", form.getZsk140SortKbn());
        cmn999Form.addHiddenParam("zsk140SortKey1", form.getZsk140SortKey1());
        cmn999Form.addHiddenParam("zsk140SortOrder1", form.getZsk140SortOrder1());
        cmn999Form.addHiddenParam("zsk140SortKey2", form.getZsk140SortKey2());
        cmn999Form.addHiddenParam("zsk140SortOrder2", form.getZsk140SortOrder2());

        req.setAttribute("cmn999Form", cmn999Form);
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
     */
    private ActionForward __doCommit(
        ActionMapping map,
        Zsk140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        BaseUserModel umodel = getSessionUserModel(req);
        Zsk140Biz biz = new Zsk140Biz(getRequestModel(req));

        boolean commit = false;
        try {
            //DB更新
            Zsk140ParamModel paramMdl = new Zsk140ParamModel();
            paramMdl.setParam(form);
            biz.updateAconfSort(paramMdl, umodel, con);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.change");
        String logMsg = __getLogMessage(form, req);
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        cmnBiz.outPutLog(con,
                         msg, GSConstLog.LEVEL_INFO, logMsg, map.getType()
                         );

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
        Zsk140Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("zsk020");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();

        String textGroupMemDspSetting = gsMsg.getMessage(req, "cmn.default.setting");
        String mkey1 = textGroupMemDspSetting;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Zsk140Form form,
        HttpServletRequest req) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //表示順
        msg += "[" + gsMsg.getMessage("cmn.sort") + "]";

        if (form.getZsk140SortKbn() == GSConstZaiseki.ORDER_ADMIN_USER) {
            msg += gsMsg.getMessage("cmn.set.the.admin");
        } else if (form.getZsk140SortKbn() == GSConstZaiseki.ORDER_ALL_USER) {
            msg += gsMsg.getMessage("cmn.set.eachuser");
        }
        msg += "\r\n" + gsMsg.getMessage("cmn.first.key") + ":";
        if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_NAME) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_NAME);
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_NUMBER) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_NUMBER);
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_POSITION) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_POSITION);
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_DATE) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_DATE);
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_STATUS) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_STATUS);
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_COMMENT) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_COMMENT);
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_KEY1) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_KEY1) + "1";
        } else if (form.getZsk140SortKey1() == GSConstZaiseki.SHOW_SORT_KEY2) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_KEY2) + "2";
        }
        if (form.getZsk140SortOrder1() == GSConstZaiseki.SHOW_SORT_ORDER_ASC) {
            msg += " " + gsMsg.getMessage("cmn.order.asc");
        } else if (form.getZsk140SortOrder1() == GSConstZaiseki.SHOW_SORT_ORDER_DESC) {
            msg += " " + gsMsg.getMessage("cmn.order.desc");
        }

        msg += "\r\n" + gsMsg.getMessage("cmn.second.key") + ":";
        if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_NAME) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_NAME);
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_NUMBER) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_NUMBER);
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_POSITION) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_POSITION);
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_DATE) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_DATE);
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_STATUS) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_STATUS);
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_COMMENT) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_COMMENT);
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_KEY1) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_KEY1) + "1";
        } else if (form.getZsk140SortKey2() == GSConstZaiseki.SHOW_SORT_KEY2) {
            msg += gsMsg.getMessage(GSConstZaiseki.SHOW_SORT_TEXT_KEY2) + "2";
        }
        if (form.getZsk140SortOrder2() == GSConstZaiseki.SHOW_SORT_ORDER_ASC) {
            msg += " " + gsMsg.getMessage("cmn.order.asc");
        } else if (form.getZsk140SortOrder2() == GSConstZaiseki.SHOW_SORT_ORDER_DESC) {
            msg += " " + gsMsg.getMessage("cmn.order.desc");
        }
        return msg;
    }
}