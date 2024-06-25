package jp.groupsession.v2.ntp.ntp240;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpTargetDao;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 目標設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp240Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp240Action.class);

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

        Ntp240Form ntpForm = (Ntp240Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("changeGrp")) {
            //グループ変更
            forward = __doInit(map, ntpForm, req, res, con);
        } else if (cmd.equals("nippou")) {
            //日報画面へ
            forward = map.findForward("nippou");
        } else if (cmd.equals("bunseki")) {
            //分析画面へ
            forward = map.findForward("bunseki");
        } else if (cmd.equals("masta")) {
            //マスタ画面へ
            forward = map.findForward("masta");
        } else if (cmd.equals("anken")) {
            //案件検索画面へ
            forward = map.findForward("anken");
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else if (cmd.equals("edit")) {
            //編集ボタン
            __doEdit(map, ntpForm, req, res, con);
        } else if (cmd.equals("cansel")) {
            //キャンセルボタン
            __doCansel(map, ntpForm, req, res, con);
        } else if (cmd.equals("yearData")) {
            //年度データ取得
            __getYearData(map, ntpForm, req, res, con);
        } else if (cmd.equals("yearDataNow")) {
            //年度データ取得
            __getYearDataNow(map, ntpForm, req, res, con);

        } else if (cmd.equals("nextmonth")) {
            //翌月
            forward = __changeMonth(map, ntpForm, req, res, con, 1);
        } else if (cmd.equals("prevmonth")) {
            //前月
            forward = __changeMonth(map, ntpForm, req, res, con, -1);
        } else if (cmd.equals("thismonth")) {
            //今月
            forward = __changeMonth(map, ntpForm, req, res, con, 0);
        } else {
            //デフォルト
            forward = __doInit(map, ntpForm, req, res, con);
        }
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
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        log__.debug("form = " + form);
        ActionForward forward = null;

        Ntp240Biz biz = new Ntp240Biz(con, getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);
        CommonBiz commonBiz = new CommonBiz();
        if (commonBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            form.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            form.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        Ntp240ParamModel paramMdl = new Ntp240ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>月変更
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param param 変更パラメータ
     * @return アクションフォーワード
     * @throws Exception 実行例外
     */
    private ActionForward __changeMonth(ActionMapping map, Ntp240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, int param)
            throws Exception {

        //画面表示年月
        int dspYear = Integer.valueOf(form.getNtp240Year());
        int dspMonth = Integer.valueOf(form.getNtp240Month());

        UDate dspDate = new UDate();

        if (param != 0) {
            dspDate.setYear(dspYear);
            dspDate.setMonth(dspMonth);
            dspDate.setDay(1);
            dspDate.addMonth(param);
        }

        form.setNtp240Year(String.valueOf(dspDate.getYear()));
        form.setNtp240Month(String.valueOf(dspDate.getMonth()));

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>確定ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __doEdit(ActionMapping map, Ntp240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        Ntp240Biz biz = new Ntp240Biz(con, getRequestModel(req));

        //画面入力データ取得
        String targetId = NullDefault.getString(req.getParameter("targetId"), "");
        String recordVal = NullDefault.getString(req.getParameter("recordVal"), "");
        String targetVal = NullDefault.getString(req.getParameter("targetVal"), "");

        if (!StringUtil.isNullZeroStringSpace(targetId)) {
            ArrayList<String> list = StringUtil.split("_", targetId);
            String targetYear = list.get(0);
            String targetMonth = list.get(1);
            String selectUsrSid = list.get(2);
            String targetSid = list.get(3);

            ActionErrors errors = null;
            errors = biz.validateCheck(targetVal, recordVal);

            if (errors.size() > 0) {
                List<String> errorList = new ArrayList<String>();
                errorList = __getJsonErrorMsg(req, errors);

                JSONArray json = new JSONArray();
                json = JSONArray.fromObject(errorList);
                if (!json.isEmpty()) {
                    try {
                        res.setHeader("Cache-Control", "no-cache");
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = res.getWriter();
                        out.print(json);
                        out.flush();
                    } catch (Exception e) {
                        log__.error("jsonデータ送信失敗(目標エラー)");
                    }
                }

            } else {

                //目標登録
                BaseUserModel umodel = getSessionUserModel(req);
                boolean commitFlg = false;
                con.setAutoCommit(false);
                try {
                    biz.setTarget(con, Integer.valueOf(targetYear),
                                       Integer.valueOf(targetMonth),
                                       Integer.valueOf(selectUsrSid),
                                       Integer.valueOf(targetSid),
                                       Long.valueOf(recordVal),
                                       Long.valueOf(targetVal),
                                       umodel);
                    commitFlg = true;


                    NtpTargetDao trgDao = new NtpTargetDao(con);
                    NtpTargetModel ntgMdl = null;
                    ntgMdl = trgDao.select(Integer.valueOf(targetSid));

                    if (ntgMdl != null) {
                        GsMessage gsMsg = new GsMessage();
                        String change = gsMsg.getMessage(req, "cmn.change");

                        //ログ出力処理
                        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));

                        ntpBiz.outPutLog(
                         map, req, res, change, GSConstLog.LEVEL_TRACE, ntgMdl.getNtgName());
                    }


                } catch (SQLException e) {
                    log__.error("目標の登録に失敗しました" + e);
                    throw e;
                } finally {
                    if (commitFlg) {
                        con.commit();
                    } else {
                        con.rollback();
                    }
                }

                //最新の目標取得
                JSONObject jsonData = biz.getPriTargetMdl(
                                        con,
                                        Integer.valueOf(targetYear),
                                        Integer.valueOf(targetMonth),
                                        Integer.valueOf(selectUsrSid),
                                        Integer.valueOf(targetSid));

                if (jsonData != null && !jsonData.isEmpty()) {

                    try {
                        res.setHeader("Cache-Control", "no-cache");
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = res.getWriter();
                        out.print(jsonData);
                        out.flush();
                    } catch (Exception e) {
                        log__.error("jsonデータ送信失敗(目標)");
                    }

                }
            }
        }
    }

    /**
     * <br>jsonエラーメッセージ作成
     * @param req リクエスト
     * @param errors エラーメッセージ
     * @throws Exception 実行例外
     * @return errorResult jsonエラーメッセージ
     */
    private List<String> __getJsonErrorMsg(
        HttpServletRequest req, ActionErrors errors) throws Exception {

        List<String> errorList = null;

        @SuppressWarnings("all")
        Iterator iterator = errors.get();
        errorList = new ArrayList<String>();

        while (iterator.hasNext()) {
            ActionMessage error = (ActionMessage) iterator.next();
            errorList.add(getResources(req).getMessage(error.getKey(), error.getValues()));
        }
        return errorList;
    }

    /**
     * <br>キャンセルボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __doCansel(ActionMapping map, Ntp240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        Ntp240Biz biz = new Ntp240Biz(con, getRequestModel(req));

        //画面入力データ取得
        String targetId = NullDefault.getString(req.getParameter("canselId"), "");

        if (!StringUtil.isNullZeroStringSpace(targetId)) {
            ArrayList<String> list = StringUtil.split("_", targetId);
            String targetYear = list.get(0);
            String targetMonth = list.get(1);
            String selectUsrSid = list.get(2);
            String targetSid = list.get(3);

            //最新の目標取得
            JSONObject jsonData = biz.getPriTargetMdl(
                                    con,
                                    Integer.valueOf(targetYear),
                                    Integer.valueOf(targetMonth),
                                    Integer.valueOf(selectUsrSid),
                                    Integer.valueOf(targetSid));

            if (jsonData != null && !jsonData.isEmpty()) {

                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(目標)");
                }

            }
        }

    }

    /**
     * <br>年度目標データ取得
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __getYearData(ActionMapping map, Ntp240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        //パラメータ取得
        String targetId = NullDefault.getString(req.getParameter("yearDataId"), "");

        if (!StringUtil.isNullZeroStringSpace(targetId)) {
            ArrayList<String> list = StringUtil.split("_", targetId);
            String targetYear = list.get(0);
            String targetMonth = list.get(1);
            String usrSid = list.get(2);
            String targetSid = list.get(3);

            __getYearDataJson(con, req, res, targetYear, targetMonth, usrSid, targetSid);

        }
    }

    /**
     * <br>年度目標データ取得(今年度)
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __getYearDataNow(ActionMapping map, Ntp240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        //パラメータ取得
        String targetId = NullDefault.getString(req.getParameter("yearDataId"), "");

        UDate now = new UDate();

        if (!StringUtil.isNullZeroStringSpace(targetId)) {
            ArrayList<String> list = StringUtil.split("_", targetId);
            String targetYear = now.getStrYear();
            String targetMonth = String.valueOf(now.getMonth());
            String usrSid = list.get(2);
            String targetSid = list.get(3);

            __getYearDataJson(con, req, res, targetYear, targetMonth, usrSid, targetSid);

        }
    }

    /**
     * <br>年度目標JSONデータ取得
     * @param req リクエスト
     * @param res レスポンス
     * @param targetYear 年
     * @param targetMonth 月
     * @param targetSid 目標SID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __getYearDataJson(Connection con,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   String targetYear,
                                   String targetMonth,
                                   String usrSid,
                                   String targetSid)
    throws Exception {

        Ntp240Biz biz = new Ntp240Biz(con, getRequestModel(req));

        //最新の目標取得
        JSONObject jsonData = null;
        jsonData = biz.getYearData(
                                con,
                                Integer.valueOf(targetYear),
                                Integer.valueOf(targetMonth),
                                Integer.valueOf(usrSid),
                                Integer.valueOf(targetSid));

        if (jsonData != null && !jsonData.isEmpty()) {

            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(年度目標)");
            }

        }
    }
}