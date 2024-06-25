package jp.groupsession.v2.ntp.ntp010;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010Form;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 日報 週間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp010Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp010Action.class);

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

        log__.debug("START_NTP010");
        ActionForward forward = null;
        Ntp010Form uform = (Ntp010Form) form;
        __setCanUsePluginFlg(uform, con, req);
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("CMD=" + cmd);

        if (cmd.equals("day")) {
            //日報（日間）
            forward = map.findForward("day");
        } else if (cmd.equals("month")) {
            //日報（月間）
            forward = map.findForward("month");
        } else if (cmd.equals("list")) {
            //日報（一覧）
            forward = map.findForward("list");
        } else if (cmd.equals("masta")) {
            //マスタメンテ
            forward = map.findForward("masta");
        } else if (cmd.equals("bunseki")) {
            //分析
            forward = map.findForward("bunseki");
        } else if (cmd.equals("add")) {
            //日報登録
            forward = map.findForward("add");
        } else if (cmd.equals("edit")) {
            //日報修正・閲覧
            forward = map.findForward("edit");

        } else if (cmd.equals("today")) {
            //今日ボタン
            __doMoveDays(uform, 0, true);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rd")) {
            //次日移動
            __doMoveDays(uform, 1, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_ld")) {
            //前日移動
            __doMoveDays(uform, -1, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rw")) {
            //次週移動
            __doMoveDays(uform, 7, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_lw")) {
            //前週移動
            __doMoveDays(uform, -7, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("msg")) {
            //ショートメール
            __doCreateMsg(map, uform, req, res, con);
            forward = map.findForward("msg");
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else if (cmd.equals("ntp010Zaiseki")) {
            //在席
            __doZaiseki(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("ntp010Fuzai")) {
            //不在
            __doFuzai(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("ntp010Sonota")) {
            //その他
            __doSonota(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("search")) {
            //一覧画面へ
            forward = map.findForward("list");
        } else if (cmd.equals("reload")) {
            //再読込
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("import")) {
            //日報インポート
            forward = map.findForward("imp");
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
            //日報週間初期表示
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_SCH010");
        return forward;
    }

    /**
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(ActionMapping map, Ntp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        Ntp010Biz biz = new Ntp010Biz(con, getRequestModel(req));


        Ntp010ParamModel paramMdl = new Ntp010ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

    }

    /**
     * <br>在席にする処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doZaiseki(ActionMapping map, Ntp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);

        UDate now = new UDate();
        BaseUserModel umodel = getSessionUserModel(req);
        CmnUsrInoutModel param = new CmnUsrInoutModel();

        int uid = NullDefault.getInt(form.getNtp010SelectUsrSid(), -1);
        if (uid != -1) {
            param.setUioSid(uid);
            param.setUioStatus(GSConst.UIOSTS_IN);
            param.setUioBiko(null);
            param.setUioAuid(umodel.getUsrsid());
            param.setUioAdate(now);
            param.setUioEuid(umodel.getUsrsid());
            param.setUioEdate(now);
            try {
                ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));
                zbiz.updateZskStatus(con, param);
                commitFlg = true;
           } catch (SQLException e) {
               log__.error("在席状況の更新に失敗しました。");
               throw e;
           } finally {
               if (commitFlg) {
                   con.commit();
               } else {
                   con.rollback();
               }
           }
        }
        form.setNtp010SelectUsrSid("");
        __doInit(map, form, req, res, con);
    }

    /**
     * <br>不在にする処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doFuzai(ActionMapping map, Ntp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);

        UDate now = new UDate();
        BaseUserModel umodel = getSessionUserModel(req);
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        int uid = NullDefault.getInt(form.getNtp010SelectUsrSid(), -1);
        if (uid != -1) {
            param.setUioSid(uid);
            param.setUioStatus(GSConst.UIOSTS_LEAVE);
            param.setUioBiko(null);
            param.setUioAuid(umodel.getUsrsid());
            param.setUioAdate(now);
            param.setUioEuid(umodel.getUsrsid());
            param.setUioEdate(now);
            try {
                ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));
                zbiz.updateZskStatus(con, param);
                commitFlg = true;
           } catch (SQLException e) {
               log__.error("在席状況の更新に失敗しました。");
               throw e;
           } finally {
               if (commitFlg) {
                   con.commit();
               } else {
                   con.rollback();
               }
           }
        }

        form.setNtp010SelectUsrSid("");
        __doInit(map, form, req, res, con);
    }

    /**
     * <br>その他にする処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doSonota(ActionMapping map, Ntp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);

        UDate now = new UDate();
        BaseUserModel umodel = getSessionUserModel(req);
        CmnUsrInoutModel param = new CmnUsrInoutModel();

        int uid = NullDefault.getInt(form.getNtp010SelectUsrSid(), -1);
        if (uid != -1) {
            param.setUioSid(uid);
            param.setUioStatus(GSConst.UIOSTS_ETC);
            param.setUioBiko(null);
            param.setUioAuid(umodel.getUsrsid());
            param.setUioAdate(now);
            param.setUioEuid(umodel.getUsrsid());
            param.setUioEdate(now);
            try {
                ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));
                zbiz.updateZskStatus(con, param);
                commitFlg = true;
           } catch (SQLException e) {
               log__.error("在席状況の更新に失敗しました。");
               throw e;
           } finally {
               if (commitFlg) {
                   con.commit();
               } else {
                   con.rollback();
               }
           }
        }

        form.setNtp010SelectUsrSid("");
        __doInit(map, form, req, res, con);
    }


    /**
     * <br>表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveDay 移動日数
     * @param today 今日へ移動
     */
    private void __doMoveDays(Ntp010Form form, int moveDay, boolean today) {
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getNtp010DspDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        form.setNtp010DspDate(udate.getDateString());
    }

    /**
     * <br>メッセンジャー処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doCreateMsg(ActionMapping map, Ntp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        log__.debug("週間==>メッセンジャー");
        //パラメータ取得
//        String selectUserSid = form.getNtp010SelectUsrSid();
//        String dspDate = form.getNtp010DspDate();
//        String dspGroup = form.getNtp010DspGpSid();

        Sml010Form msgForm = new Sml010Form();
        msgForm.setSml010scriptFlg(GSConstSmail.SCRIPT_FIG_TRUE);
        msgForm.setSml010scriptKbn(GSConstSmail.SCRIPT_CREATE_MAIL);
        msgForm.setSml010scriptSelUsrSid(form.getNtp010SelectUsrSid());
        req.setAttribute("sml010Form", msgForm);

    }

    /**
     * 在席管理・ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Ntp010Form form,
            Connection con, HttpServletRequest req)
    throws SQLException {
        //プラグイン設定を取得する
        PluginConfig pconfig = getPluginConfigForMain(
                getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstNippou.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstNippou.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstNippou.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstNippou.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstNippou.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstNippou.PLUGIN_NOT_USE);
        }
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
                            Ntp010Form form,
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
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    @SuppressWarnings("unchecked")
    private void __doIkkatuCheckDsp(ActionMapping map,
                                    Ntp010Form form,
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
        cmn999Form.addHiddenParam("adminkbn", form.getAdminKbn());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("belongKbn", form.getBelongKbn());
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("ntp010BttomList", form.getNtp010BottomList());
        cmn999Form.addHiddenParam("ntp010calendarList", form.getNtp010CalendarList());
        cmn999Form.addHiddenParam("ntp010DspDate", form.getNtp010DspDate());
        cmn999Form.addHiddenParam("ntp010DspSid", form.getNtp010DspGpSid());
        cmn999Form.addHiddenParam("ntp010NipSid", form.getNtp010NipSid());
        cmn999Form.addHiddenParam("ntp010Reload", form.getNtp010Reload());
        cmn999Form.addHiddenParam("ntp010searchWord", form.getNtp010searchWord());
        cmn999Form.addHiddenParam("ntp010SelectDate", form.getNtp010SelectDate());
        cmn999Form.addHiddenParam("ntp010SelectUsrKbn", form.getNtp010SelectUsrKbn());
        cmn999Form.addHiddenParam("ntp010SelectUsrSid", form.getNtp010SelectUsrSid());
        cmn999Form.addHiddenParam("ntp010StrDspDate", form.getNtp010StrDspDate());
        cmn999Form.addHiddenParam("ntp010TopList", form.getNtp010TopList());
        cmn999Form.addHiddenParam("rsvIkkatuKey", form.getRsvIkkatuKey());
        cmn999Form.addHiddenParam("rsvSelectedIkkatuKey", form.getRsvSelectedIkkatuKey());
        cmn999Form.addHiddenParam("smailUseOk", form.getSmailUseOk());
        cmn999Form.addHiddenParam("zaisekiUseOk", form.getZaisekiUseOk());

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
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doIkkatuOk(ActionMapping map,
                                              Ntp010Form form,
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
            Ntp010ParamModel paramMdl = new Ntp010ParamModel();
            paramMdl.setParam(form);
            biz.doIkkatuUpdate(paramMdl, con);
            paramMdl.setFormData(form);


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

        return __doIkkatuComp(map, form, req, res, con);
    }


    /**
     * <br>[機  能] 一括確認処理完了後の画面遷移設定
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
    @SuppressWarnings("unchecked")
    private ActionForward __doIkkatuComp(ActionMapping map,
                                          Ntp010Form form,
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
        cmn999Form.addHiddenParam("adminkbn", form.getAdminKbn());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("belongKbn", form.getBelongKbn());
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("ntp010BttomList", form.getNtp010BottomList());
        cmn999Form.addHiddenParam("ntp010calendarList", form.getNtp010CalendarList());
        cmn999Form.addHiddenParam("ntp010DspDate", form.getNtp010DspDate());
        cmn999Form.addHiddenParam("ntp010DspSid", form.getNtp010DspGpSid());
        cmn999Form.addHiddenParam("ntp010NipSid", form.getNtp010NipSid());
        cmn999Form.addHiddenParam("ntp010Reload", form.getNtp010Reload());
        cmn999Form.addHiddenParam("ntp010searchWord", form.getNtp010searchWord());
        cmn999Form.addHiddenParam("ntp010SelectDate", form.getNtp010SelectDate());
        cmn999Form.addHiddenParam("ntp010SelectUsrKbn", form.getNtp010SelectUsrKbn());
        cmn999Form.addHiddenParam("ntp010SelectUsrSid", form.getNtp010SelectUsrSid());
        cmn999Form.addHiddenParam("ntp010StrDspDate", form.getNtp010StrDspDate());
        cmn999Form.addHiddenParam("ntp010TopList", form.getNtp010TopList());
        cmn999Form.addHiddenParam("rsvIkkatuKey", form.getRsvIkkatuKey());
        cmn999Form.addHiddenParam("rsvSelectedIkkatuKey", form.getRsvSelectedIkkatuKey());
        cmn999Form.addHiddenParam("smailUseOk", form.getSmailUseOk());
        cmn999Form.addHiddenParam("zaisekiUseOk", form.getZaisekiUseOk());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


}
