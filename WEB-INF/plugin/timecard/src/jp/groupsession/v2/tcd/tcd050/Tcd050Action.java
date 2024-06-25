package jp.groupsession.v2.tcd.tcd050;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;


/**
 * <br>[機  能] タイムカード 管理者設定 基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd050Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd050Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        ActionForward forward = null;
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

        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd050Form tcForm = (Tcd050Form) form;

        if (cmd.equals("tcd050_back")) {
            //戻る
            forward = map.findForward("back");
        } else if (cmd.equals("tcd050_submit")) {
            //OKボタン
            forward = __doSubmit(map, tcForm, req, res, con);
        } else if (cmd.equals("moveRight")) {
            //年移動：次年
            forward = __doRight(map, tcForm, req, res, con);
        } else if (cmd.equals("moveLeft")) {
            //年移動：前年
            forward = __doLeft(map, tcForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, tcForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Tcd050ParamModel paramMdl = new Tcd050ParamModel();
        paramMdl.setParam(form);
        Tcd050Biz biz = new Tcd050Biz(reqMdl);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doSubmit(ActionMapping map, Tcd050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        //入力チェック
        ActionErrors ereors = form.validateCheck(getRequestModel(req), con);
        if (!ereors.isEmpty()) {
            addErrors(req, ereors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }

        saveToken(req);
        return map.findForward("submit");
    }

    /**
     * <br>[機  能] 「左矢印」処理
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
    private ActionForward __doLeft(ActionMapping map,
            Tcd050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        ActionForward forward = null;

        //表示開始日を-1する
        String strDspYear = NullDefault.getString(
                form.getTcd050DspHolidayYear(), new UDate().getStrYear());

        int dspYear = Integer.parseInt(strDspYear) - 1;
        if (dspYear >= 0) {
            form.setTcd050DspHolidayYear(String.valueOf(dspYear));
        }
        form.setTcd050SelectHoliDay(null);
        forward = __doInit(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 「右矢印」処理
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
    private ActionForward __doRight(ActionMapping map,
            Tcd050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        ActionForward forward = null;

        //表示開始日を+1する
        String strDspYear = NullDefault.getString(
                form.getTcd050DspHolidayYear(), new UDate().getStrYear());

        int dspYear = Integer.parseInt(strDspYear) + 1;
        if (dspYear <= 9999) {
            form.setTcd050DspHolidayYear(String.valueOf(dspYear));
        }
        form.setTcd050SelectHoliDay(null);
        forward = __doInit(map, form, req, res, con);
        return forward;
    }
}
