package jp.groupsession.v2.tcd.tcd220;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;

/**
 * <br>[機  能] タイムカード 有休警告設定画面のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd220Action extends AbstractTimecardAdminAction {

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction (
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        boolean isAdmin =
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstTimecard.PLUGIN_ID_TIMECARD);
        if (!isAdmin) {
            return getNotAdminSeniPage(map, req);
        }

        ActionForward forward = null;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Tcd220Form thisForm = (Tcd220Form) form;

        if (cmd.equals("add")) {
            forward = null;
        } else if (cmd.equals("tcd220redraw")) {
            //再描画処理
            forward = __doRedraw(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd220ok")) {
            //OKボタン押下時処理
            forward = __doInsert(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd220commit")) {
            //登録確認画面OKボタン押下時処理
            forward = __doCommit(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd220back")) {
            //戻るボタン押下時処理
            forward = map.findForward("tcd220back");
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能]　初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(
            ActionMapping map,
            Tcd220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        Tcd220ParamModel paramMdl = new Tcd220ParamModel();
        paramMdl.setParam(form);

        Tcd220Biz biz = new Tcd220Biz();
        biz._setInitData(con, paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doRedraw(
            ActionMapping map,
            Tcd220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        Tcd220ParamModel paramMdl = new Tcd220ParamModel();
        paramMdl.setParam(form);
        Tcd220Biz biz = new Tcd220Biz();
        paramMdl.setTcd220MonthLabelList(biz._getMonthLabel());
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInsert(
            ActionMapping map,
            Tcd220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        RequestModel reqMdl = getRequestModel(req);

        //入力値チェック
        ActionErrors errors = form.validateCheck(req, con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doRedraw(map, form, req, res, con);
        }

        //トークンの保存
        saveToken(req);

        //登録確認画面へ遷移
        return __doInsertKakunin(map, form ,req ,res, con);

    }

    /**
     * <br>[機  能] 登録確認を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInsertKakunin(
            ActionMapping map,
            Tcd220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forward = map.findForward("tcd220commit");
        cmn999Form.setUrlOK(forward.getPath());
        //キャンセルクリック時遷移先
        forward = map.findForward("tcd220cancel");
        cmn999Form.setUrlCancel(forward.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //有休警告設定
        String textYukyuWarn = gsMsg.getMessage(req, "tcd.tcd030.17");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kakunin.once", textYukyuWarn));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("tcd220DispMonth1", form.getTcd220DispMonth1());
        cmn999Form.addHiddenParam("tcd220Days1", form.getTcd220Days1());
        cmn999Form.addHiddenParam("tcd220Message1", form.getTcd220Message1());
        cmn999Form.addHiddenParam("tcd220Row1", form.getTcd220Row1());
        cmn999Form.addHiddenParam("tcd220DispMonth2", form.getTcd220DispMonth2());
        cmn999Form.addHiddenParam("tcd220Days2", form.getTcd220Days2());
        cmn999Form.addHiddenParam("tcd220Message2", form.getTcd220Message2());
        cmn999Form.addHiddenParam("tcd220Row2", form.getTcd220Row2());
        cmn999Form.addHiddenParam("tcd220DispMonth3", form.getTcd220DispMonth3());
        cmn999Form.addHiddenParam("tcd220Days3", form.getTcd220Days3());
        cmn999Form.addHiddenParam("tcd220Message3", form.getTcd220Message3());
        cmn999Form.addHiddenParam("tcd220Row3", form.getTcd220Row3());
        cmn999Form.addHiddenParam("tcd220DispMonth4", form.getTcd220DispMonth4());
        cmn999Form.addHiddenParam("tcd220Days4", form.getTcd220Days4());
        cmn999Form.addHiddenParam("tcd220Message4", form.getTcd220Message4());
        cmn999Form.addHiddenParam("tcd220Row4", form.getTcd220Row4());
        cmn999Form.addHiddenParam("tcd220DispMonth5", form.getTcd220DispMonth5());
        cmn999Form.addHiddenParam("tcd220Days5", form.getTcd220Days5());
        cmn999Form.addHiddenParam("tcd220Message5", form.getTcd220Message5());
        cmn999Form.addHiddenParam("tcd220Row5", form.getTcd220Row5());
        cmn999Form.addHiddenParam("tcd220DispMonth6", form.getTcd220DispMonth6());
        cmn999Form.addHiddenParam("tcd220Days6", form.getTcd220Days6());
        cmn999Form.addHiddenParam("tcd220Message6", form.getTcd220Message6());
        cmn999Form.addHiddenParam("tcd220Row6", form.getTcd220Row6());
        cmn999Form.addHiddenParam("tcd220DispMonth7", form.getTcd220DispMonth7());
        cmn999Form.addHiddenParam("tcd220Days7", form.getTcd220Days7());
        cmn999Form.addHiddenParam("tcd220Message7", form.getTcd220Message7());
        cmn999Form.addHiddenParam("tcd220Row7", form.getTcd220Row7());
        cmn999Form.addHiddenParam("tcd220DispMonth8", form.getTcd220DispMonth8());
        cmn999Form.addHiddenParam("tcd220Days8", form.getTcd220Days8());
        cmn999Form.addHiddenParam("tcd220Message8", form.getTcd220Message8());
        cmn999Form.addHiddenParam("tcd220Row8", form.getTcd220Row8());
        cmn999Form.addHiddenParam("tcd220DispMonth9", form.getTcd220DispMonth9());
        cmn999Form.addHiddenParam("tcd220Days9", form.getTcd220Days9());
        cmn999Form.addHiddenParam("tcd220Message9", form.getTcd220Message9());
        cmn999Form.addHiddenParam("tcd220Row9", form.getTcd220Row9());
        cmn999Form.addHiddenParam("tcd220DispMonth10", form.getTcd220DispMonth10());
        cmn999Form.addHiddenParam("tcd220Days10", form.getTcd220Days10());
        cmn999Form.addHiddenParam("tcd220Message10", form.getTcd220Message10());
        cmn999Form.addHiddenParam("tcd220Row10", form.getTcd220Row10());
        cmn999Form.addHiddenParam("tcd220DispMonth11", form.getTcd220DispMonth11());
        cmn999Form.addHiddenParam("tcd220Days11", form.getTcd220Days11());
        cmn999Form.addHiddenParam("tcd220Message11", form.getTcd220Message11());
        cmn999Form.addHiddenParam("tcd220Row11", form.getTcd220Row11());
        cmn999Form.addHiddenParam("tcd220DispMonth12", form.getTcd220DispMonth12());
        cmn999Form.addHiddenParam("tcd220Days12", form.getTcd220Days12());
        cmn999Form.addHiddenParam("tcd220Message12", form.getTcd220Message12());
        cmn999Form.addHiddenParam("tcd220Row12", form.getTcd220Row12());
        cmn999Form.addHiddenParam("tcd220DispMonth13", form.getTcd220DispMonth13());
        cmn999Form.addHiddenParam("tcd220Days13", form.getTcd220Days13());
        cmn999Form.addHiddenParam("tcd220Message13", form.getTcd220Message13());
        cmn999Form.addHiddenParam("tcd220Row13", form.getTcd220Row13());
        cmn999Form.addHiddenParam("tcd220DispMonth14", form.getTcd220DispMonth14());
        cmn999Form.addHiddenParam("tcd220Days14", form.getTcd220Days14());
        cmn999Form.addHiddenParam("tcd220Message14", form.getTcd220Message14());
        cmn999Form.addHiddenParam("tcd220Row14", form.getTcd220Row14());
        cmn999Form.addHiddenParam("tcd220DispMonth15", form.getTcd220DispMonth15());
        cmn999Form.addHiddenParam("tcd220Days15", form.getTcd220Days15());
        cmn999Form.addHiddenParam("tcd220Message15", form.getTcd220Message15());
        cmn999Form.addHiddenParam("tcd220Row15", form.getTcd220Row15());

        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 登録確認画面OKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doCommit(
            ActionMapping map,
            Tcd220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        RequestModel reqMdl = getRequestModel(req);
        //入力値チェック
        ActionErrors errors = form.validateCheck(req, con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doRedraw(map, form, req, res, con);
        }

        Tcd220ParamModel paramMdl = new Tcd220ParamModel();
        paramMdl.setParam(form);
        Tcd220Biz biz = new Tcd220Biz();
        biz._doInsert(con, paramMdl, map, reqMdl);

        //登録完了画面へ遷移
        return __doInsertComp(map, form ,req ,res, con);
    }

    /**
     * <br>[機  能] タイムカード有休日数登録,編集後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doInsertComp(ActionMapping map,
            Tcd220Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("tcd220back");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //有休警告設定
        String textYukyuWarn = gsMsg.getMessage(req, "tcd.tcd030.17");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textYukyuWarn));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("tcd220DispMonth1", form.getTcd220DispMonth1());
        cmn999Form.addHiddenParam("tcd220Days1", form.getTcd220Days1());
        cmn999Form.addHiddenParam("tcd220Message1", form.getTcd220Message1());
        cmn999Form.addHiddenParam("tcd220Row1", form.getTcd220Row1());
        cmn999Form.addHiddenParam("tcd220DispMonth2", form.getTcd220DispMonth2());
        cmn999Form.addHiddenParam("tcd220Days2", form.getTcd220Days2());
        cmn999Form.addHiddenParam("tcd220Message2", form.getTcd220Message2());
        cmn999Form.addHiddenParam("tcd220Row2", form.getTcd220Row2());
        cmn999Form.addHiddenParam("tcd220DispMonth3", form.getTcd220DispMonth3());
        cmn999Form.addHiddenParam("tcd220Days3", form.getTcd220Days3());
        cmn999Form.addHiddenParam("tcd220Message3", form.getTcd220Message3());
        cmn999Form.addHiddenParam("tcd220Row3", form.getTcd220Row3());
        cmn999Form.addHiddenParam("tcd220DispMonth4", form.getTcd220DispMonth4());
        cmn999Form.addHiddenParam("tcd220Days4", form.getTcd220Days4());
        cmn999Form.addHiddenParam("tcd220Message4", form.getTcd220Message4());
        cmn999Form.addHiddenParam("tcd220Row4", form.getTcd220Row4());
        cmn999Form.addHiddenParam("tcd220DispMonth5", form.getTcd220DispMonth5());
        cmn999Form.addHiddenParam("tcd220Days5", form.getTcd220Days5());
        cmn999Form.addHiddenParam("tcd220Message5", form.getTcd220Message5());
        cmn999Form.addHiddenParam("tcd220Row5", form.getTcd220Row5());
        cmn999Form.addHiddenParam("tcd220DispMonth6", form.getTcd220DispMonth6());
        cmn999Form.addHiddenParam("tcd220Days6", form.getTcd220Days6());
        cmn999Form.addHiddenParam("tcd220Message6", form.getTcd220Message6());
        cmn999Form.addHiddenParam("tcd220Row6", form.getTcd220Row6());
        cmn999Form.addHiddenParam("tcd220DispMonth7", form.getTcd220DispMonth7());
        cmn999Form.addHiddenParam("tcd220Days7", form.getTcd220Days7());
        cmn999Form.addHiddenParam("tcd220Message7", form.getTcd220Message7());
        cmn999Form.addHiddenParam("tcd220Row7", form.getTcd220Row7());
        cmn999Form.addHiddenParam("tcd220DispMonth8", form.getTcd220DispMonth8());
        cmn999Form.addHiddenParam("tcd220Days8", form.getTcd220Days8());
        cmn999Form.addHiddenParam("tcd220Message8", form.getTcd220Message8());
        cmn999Form.addHiddenParam("tcd220Row8", form.getTcd220Row8());
        cmn999Form.addHiddenParam("tcd220DispMonth9", form.getTcd220DispMonth9());
        cmn999Form.addHiddenParam("tcd220Days9", form.getTcd220Days9());
        cmn999Form.addHiddenParam("tcd220Message9", form.getTcd220Message9());
        cmn999Form.addHiddenParam("tcd220Row9", form.getTcd220Row9());
        cmn999Form.addHiddenParam("tcd220DispMonth10", form.getTcd220DispMonth10());
        cmn999Form.addHiddenParam("tcd220Days10", form.getTcd220Days10());
        cmn999Form.addHiddenParam("tcd220Message10", form.getTcd220Message10());
        cmn999Form.addHiddenParam("tcd220Row10", form.getTcd220Row10());
        cmn999Form.addHiddenParam("tcd220DispMonth11", form.getTcd220DispMonth11());
        cmn999Form.addHiddenParam("tcd220Days11", form.getTcd220Days11());
        cmn999Form.addHiddenParam("tcd220Message11", form.getTcd220Message11());
        cmn999Form.addHiddenParam("tcd220Row11", form.getTcd220Row11());
        cmn999Form.addHiddenParam("tcd220DispMonth12", form.getTcd220DispMonth12());
        cmn999Form.addHiddenParam("tcd220Days12", form.getTcd220Days12());
        cmn999Form.addHiddenParam("tcd220Message12", form.getTcd220Message12());
        cmn999Form.addHiddenParam("tcd220Row12", form.getTcd220Row12());
        cmn999Form.addHiddenParam("tcd220DispMonth13", form.getTcd220DispMonth13());
        cmn999Form.addHiddenParam("tcd220Days13", form.getTcd220Days13());
        cmn999Form.addHiddenParam("tcd220Message13", form.getTcd220Message13());
        cmn999Form.addHiddenParam("tcd220Row13", form.getTcd220Row13());
        cmn999Form.addHiddenParam("tcd220DispMonth14", form.getTcd220DispMonth14());
        cmn999Form.addHiddenParam("tcd220Days14", form.getTcd220Days14());
        cmn999Form.addHiddenParam("tcd220Message14", form.getTcd220Message14());
        cmn999Form.addHiddenParam("tcd220Row14", form.getTcd220Row14());
        cmn999Form.addHiddenParam("tcd220DispMonth15", form.getTcd220DispMonth15());
        cmn999Form.addHiddenParam("tcd220Days15", form.getTcd220Days15());
        cmn999Form.addHiddenParam("tcd220Message15", form.getTcd220Message15());
        cmn999Form.addHiddenParam("tcd220Row15", form.getTcd220Row15());

        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
