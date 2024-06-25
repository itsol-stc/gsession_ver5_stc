package jp.groupsession.v2.ntp.ntp020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 日報 月間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp020Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp020Action.class);

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

        log__.debug("START_Ntp020");
        ActionForward forward = null;
        Ntp020Form uform = (Ntp020Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("day")) {
            //日間日報
            forward = map.findForward("020_day");
        } else if (cmd.equals("week")) {
            //月間日報
            forward = map.findForward("020_week");
        } else if (cmd.equals("masta")) {
            //マスタメンテ
            forward = map.findForward("masta");
        } else if (cmd.equals("bunseki")) {
            //分析
            forward = map.findForward("bunseki");
        } else if (cmd.equals("add")) {
            //日報追加
            forward = map.findForward("020_add");
        } else if (cmd.equals("edit")) {
            //日報修正・閲覧
            forward = map.findForward("020_edit");
        } else if (cmd.equals("list")) {
            //日報一覧
            forward = map.findForward("list");
        } else if (cmd.equals("search")) {
            //一覧画面へ
            forward = map.findForward("020_list");
        } else if (cmd.equals("today")) {
            //今日ボタン
            __doMoveMonth(uform, 0, true);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rm")) {
            //次月移動
            __doMoveMonth(uform, 1, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_lm")) {
            //前月移動
            __doMoveMonth(uform, -1, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else if (cmd.equals("reload")) {
            //再読込
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("import")) {
            //日報インポート
            forward = map.findForward("020_imp");
        } else if (cmd.equals("ikkatu")) {
            //日報一括確認
            forward = __doIkkatu(map, uform, req, res, con);
        } else if (cmd.equals("ikkatuOk")) {
            //日報一括確認でOKボタン押下
            forward = __doIkkatuOk(map, uform, req, res, con);
        } else if (cmd.equals("anken")) {
            //案件検索画面へ
            forward = map.findForward("anken");
        } else if (cmd.equals("target")) {
            //目標設定画面へ
            forward = map.findForward("target");
        } else {
            //日報月間表示
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_Ntp020");
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Ntp020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        Ntp020Biz biz = new Ntp020Biz(con, reqMdl);


        Ntp020ParamModel paramMdl = new Ntp020ParamModel();
        paramMdl.setParam(form);
        //グループメンバー存在チェック
        ActionErrors errors =
                biz.validateGroupMemberExistCheck(paramMdl, reqMdl.getSmodel().getUsrsid());

        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
        }

        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);
    }

    /**
     * <br>表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveMonth 移動月数
     * @param today 今日へ移動
     */
    private void __doMoveMonth(Ntp020Form form, int moveMonth, boolean today) {
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getNtp010DspDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        UDate rsDate = udate.cloneUDate();
        rsDate = UDateUtil.addMonthLastDay(rsDate, moveMonth);

        int iSYear = rsDate.getYear();
        int iSMonth = rsDate.getMonth();
        int iSDay = udate.getIntDay();
        rsDate.setDay(udate.getIntDay());
        //日付論理エラーの場合、月末日を設定
        if (rsDate.getYear() != iSYear
         || rsDate.getMonth() != iSMonth
         || rsDate.getIntDay() != iSDay) {
            rsDate = udate.cloneUDate();
            rsDate = UDateUtil.addMonthLastDay(rsDate, moveMonth);
        }
        form.setNtp010DspDate(rsDate.getDateString());
    }


    /**
     * <br>一括確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doIkkatu(ActionMapping map,
                            Ntp020Form form,
                            HttpServletRequest req,
                            HttpServletResponse res,
                            Connection con) throws Exception {

        //一括確認選択チェック
        ActionErrors errors = form.validateSelectCheck();

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }
        __doIkkatuCheckDsp(map, form, req, res, con);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 一括確認押下時 確認画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param uform フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    @SuppressWarnings("unchecked")
    private void __doIkkatuCheckDsp(ActionMapping map,
                                    Ntp020Form uform,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=ikkatuOk");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("update.ikkatu.nippou"));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("adminkbn", uform.getAdminKbn());
        cmn999Form.addHiddenParam("backScreen", uform.getBackScreen());
        cmn999Form.addHiddenParam("belongKbn", uform.getBelongKbn());
        cmn999Form.addHiddenParam("cmd", uform.getCmd());
        cmn999Form.addHiddenParam("dspMod", uform.getDspMod());
        cmn999Form.addHiddenParam("listMod", uform.getListMod());
        cmn999Form.addHiddenParam("ntp010BttomList", uform.getNtp010BottomList());
        cmn999Form.addHiddenParam("ntp010calendarList", uform.getNtp010CalendarList());
        cmn999Form.addHiddenParam("ntp010DspDate", uform.getNtp010DspDate());
        cmn999Form.addHiddenParam("ntp010DspSid", uform.getNtp010DspGpSid());
        cmn999Form.addHiddenParam("ntp010NipSid", uform.getNtp010NipSid());
        cmn999Form.addHiddenParam("ntp010Reload", uform.getNtp010Reload());
        cmn999Form.addHiddenParam("ntp010searchWord", uform.getNtp010searchWord());
        cmn999Form.addHiddenParam("ntp010SelectDate", uform.getNtp010SelectDate());
        cmn999Form.addHiddenParam("ntp010SelectUsrKbn", uform.getNtp010SelectUsrKbn());
        cmn999Form.addHiddenParam("ntp010SelectUsrSid", uform.getNtp010SelectUsrSid());
        cmn999Form.addHiddenParam("ntp010StrDspDate", uform.getNtp010StrDspDate());
        cmn999Form.addHiddenParam("ntp010TopList", uform.getNtp010TopList());
        cmn999Form.addHiddenParam("rsvIkkatuKey", uform.getRsvIkkatuKey());
        cmn999Form.addHiddenParam("rsvSelectedIkkatuKey", uform.getRsvSelectedIkkatuKey());
        cmn999Form.addHiddenParam("smailUseOk", uform.getSmailUseOk());
        cmn999Form.addHiddenParam("zaisekiUseOk", uform.getZaisekiUseOk());
        cmn999Form.addHiddenParam("Ntp020StrDspDate", uform.getNtp020StrDspDate());
        cmn999Form.addHiddenParam("Ntp020StrUserName", uform.getNtp020StrUserName());
        cmn999Form.addHiddenParam("Ntp020NippouList", uform.getNtp020NippouList());
        cmn999Form.addHiddenParam("Ntp020SelectUsrSid", uform.getNtp020SelectUsrSid());
        cmn999Form.addHiddenParam("Ntp020Reload", uform.getNtp020Reload());

        req.setAttribute("cmn999Form", cmn999Form);

        saveToken(req);
        return;
    }


    /**
     * <br>[機  能] 一括確認確認画面でOKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param uform フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doIkkatuOk(ActionMapping map,
                                              Ntp020Form uform,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            Ntp010Biz biz = new Ntp010Biz(con, getRequestModel(req));

            //一括確認処理実行
            Ntp020ParamModel paramMdl = new Ntp020ParamModel();
            paramMdl.setParam(uform);
            biz.doIkkatuUpdate(paramMdl, con);
            paramMdl.setFormData(uform);


            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        return __doIkkatuComp(map, uform, req, res, con);
    }


    /**
     * <br>[機  能] 一括確認処理完了後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param uform フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    @SuppressWarnings("unchecked")
    private ActionForward __doIkkatuComp(ActionMapping map,
                                          Ntp020Form uform,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=reload");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("hensyu.henkou.kanryo.object", "一括確認"));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("adminkbn", uform.getAdminKbn());
        cmn999Form.addHiddenParam("backScreen", uform.getBackScreen());
        cmn999Form.addHiddenParam("belongKbn", uform.getBelongKbn());
        cmn999Form.addHiddenParam("cmd", uform.getCmd());
        cmn999Form.addHiddenParam("dspMod", uform.getDspMod());
        cmn999Form.addHiddenParam("listMod", uform.getListMod());
        cmn999Form.addHiddenParam("ntp010BttomList", uform.getNtp010BottomList());
        cmn999Form.addHiddenParam("ntp010calendarList", uform.getNtp010CalendarList());
        cmn999Form.addHiddenParam("ntp010DspDate", uform.getNtp010DspDate());
        cmn999Form.addHiddenParam("ntp010DspSid", uform.getNtp010DspGpSid());
        cmn999Form.addHiddenParam("ntp010NipSid", uform.getNtp010NipSid());
        cmn999Form.addHiddenParam("ntp010Reload", uform.getNtp010Reload());
        cmn999Form.addHiddenParam("ntp010searchWord", uform.getNtp010searchWord());
        cmn999Form.addHiddenParam("ntp010SelectDate", uform.getNtp010SelectDate());
        cmn999Form.addHiddenParam("ntp010SelectUsrKbn", uform.getNtp010SelectUsrKbn());
        cmn999Form.addHiddenParam("ntp010SelectUsrSid", uform.getNtp010SelectUsrSid());
        cmn999Form.addHiddenParam("ntp010StrDspDate", uform.getNtp010StrDspDate());
        cmn999Form.addHiddenParam("ntp010TopList", uform.getNtp010TopList());
        cmn999Form.addHiddenParam("rsvIkkatuKey", uform.getRsvIkkatuKey());
        cmn999Form.addHiddenParam("rsvSelectedIkkatuKey", uform.getRsvSelectedIkkatuKey());
        cmn999Form.addHiddenParam("smailUseOk", uform.getSmailUseOk());
        cmn999Form.addHiddenParam("zaisekiUseOk", uform.getZaisekiUseOk());
        cmn999Form.addHiddenParam("Ntp020StrDspDate", uform.getNtp020StrDspDate());
        cmn999Form.addHiddenParam("Ntp020StrUserName", uform.getNtp020StrUserName());
        cmn999Form.addHiddenParam("Ntp020NippouList", uform.getNtp020NippouList());
        cmn999Form.addHiddenParam("Ntp020SelectUsrSid", uform.getNtp020SelectUsrSid());
        cmn999Form.addHiddenParam("Ntp020Reload", uform.getNtp020Reload());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
