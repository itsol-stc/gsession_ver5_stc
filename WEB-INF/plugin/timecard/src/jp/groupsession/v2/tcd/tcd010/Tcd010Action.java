package jp.groupsession.v2.tcd.tcd010;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardCalcBiz;
import jp.groupsession.v2.tcd.TimecardUtil;
import jp.groupsession.v2.tcd.csv.TcdCsvWriter;
import jp.groupsession.v2.tcd.excel.TimeCardXlsParametarModel;
import jp.groupsession.v2.tcd.model.TcdCsvSearchModel;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;
import jp.groupsession.v2.tcd.tcd020.Tcd020Biz;

/**
 * <br>[機  能] タイムカード 一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd010Action extends AbstractTimecardAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd010Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("xls")) {
            log__.debug("勤務表エクセルファイルダウンロード");
            return true;
        } else if (cmd.equals("csv")) {
            log__.debug("CSVファイルダウンロード");
            return true;
        } else if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        }

        return false;
    }

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

        log__.debug("tcd010 start");
        ActionForward forward = null;
        Tcd010Form myForm = (Tcd010Form) form;

        //指定したユーザのタイムカードを閲覧可能かを確認
        if (!__canViewTimecard(con, myForm, req)) {
            return getAuthErrorPage(map, req);
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("del")) {
            //削除
            forward = __doDelete(map, myForm, req, res, con);
        } else if (cmd.equals("del_ok")) {
            //削除実行
            forward = __doDeleteOk(map, myForm, req, res, con);
        } else if (cmd.equals("single_edit")) {
            //単体編集
            forward = __doSingleEdit(map, myForm, req, res, con);
        } else if (cmd.equals("multi_edit")) {
            //複数編集
            forward = __doMultiEdit(map, myForm, req, res, con);
        } else if (cmd.equals("dakoku_edit")) {
            //打刻編集
            forward = __doDakokuEdit(map, myForm, req, res, con);
        } else if (cmd.equals("move_now")) {
            //今月移動
            __doMoveNow(map, myForm, req, res, con);
            forward = __doInit(map, myForm, req, res, con);
        } else if (cmd.equals("move_next")) {
            //来月移動
            __doMoveNext(map, myForm, req, res, con);
            forward = __doInit(map, myForm, req, res, con);
        } else if (cmd.equals("move_last")) {
            //前月移動
            __doMoveLast(map, myForm, req, res, con);
            forward = __doInit(map, myForm, req, res, con);
        } else if (cmd.equals("mng")) {
            //勤怠集計
            forward = __doManager(map);
        } else if (cmd.equals("yuukyuList")) {
            //有休日数一覧
            forward = __doYukyu(map);
        } else if (cmd.equals("admtool")) {
            //管理者設定
            forward = __doAdminTool(map, myForm, req, res, con);
        } else if (cmd.equals("pritool")) {
            //個人設定
            forward = __doPrivateTool(map, myForm, req, res, con);
        } else if (cmd.equals("xls")) {
            //勤務表出力
            forward = __doExportXls(map, myForm, req, res, con);
        } else if (cmd.equals("csv")) {
            //CSV出力
            forward = __doExportCsv(map, myForm, req, res, con);
        } else if (cmd.equals("pdf")) {
            //PDF出力
            forward = __doExportPdf(map, myForm, req, res, con);
        } else if (cmd.equals("imp")) {
            //CSVインポート
            forward = __doImportCsv(map, myForm, req, res, con);
        } else {
            //初期表示処理
            forward = __doInit(map, myForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        log__.debug("初期表示");
        ActionForward forward = null;
        con.setAutoCommit(true);

        Tcd010ParamModel paramMdl = new Tcd010ParamModel();
        paramMdl.setParam(form);
        Tcd010Biz biz = new Tcd010Biz();

        //Formの初期値を設定
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);

        if (form.getTcd010FailFlg().equals(String.valueOf(GSConstTimecard.DATA_FAIL))) {
            __setFailDataMsg(form, req, con);
        }
        con.setAutoCommit(false);

        saveToken(req);

        forward = map.getInputForward();
        return forward;
    }

    /**
     * データエラーが有る場合、エラーメッセージを設定する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setFailDataMsg(Tcd010Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        UDate failDate = tcBiz.getFailDataYmd(Integer.parseInt(form.getUsrSid()), con);

        if (failDate != null) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String setting = gsMsg.getMessage("tcd.155");

            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "error.input.format.file", UDateUtil.getSlashYYMD(failDate), setting);
            errors.add("error.input.format.file", msg);
            addErrors(req, errors);
        }
    }
    /**
     * <br>[機  能] 管理者設定ボタンクリック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doAdminTool(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        ActionForward forward = null;

        //セッション情報を取得
        BaseUserModel usModel = getSessionUserModel(req);
        TimecardBiz tcdBiz = new TimecardBiz(getRequestModel(req));
        int usKbn = tcdBiz.getUserKbn(con, usModel);
        //権限チェック(管理者orグループ管理者)
        if (usKbn == GSConstTimecard.USER_KBN_NORMAL) {
            //権限無し
            return getNotAdminSeniPage(map, req);
        }

        forward = map.findForward("admtool");
        return forward;
    }

    /**
     * <br>[機  能] 個人設定ボタンクリック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doPrivateTool(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        ActionForward forward = null;
        forward = map.findForward("pritool");
        return forward;
    }

    /**
     * <br>[機  能] 削除ボタンクリック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        log__.debug("-- doDelete START--");
        ActionForward forward = null;
        con.setAutoCommit(true);
        //ユーザ情報
        BaseUserModel umodel = getSessionUserModel(req);
        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID
        TimecardBiz tBiz = new TimecardBiz(getRequestModel(req));
        //基本設定取得
        TcdAdmConfModel admConf = tBiz.getTcdAdmConfModel(sessionUsrSid, con);

        GsMessage gsMsg = new GsMessage();
        String deleteDay = gsMsg.getMessage(req, "tcd.156");

        //明細がチェックされているかを調べる
        if (form.isDaySelected()) {
            saveToken(req);
            //削除確認画面へ
            forward = __doDeleteKakuDsp(map, form, req, res, admConf, con);
        } else {
            //チェックボックスがチェックされていない
            ActionMessages ers = new ActionMessages();
            ers.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.select.required.text", deleteDay));
            saveErrors(req, ers);
            forward = __doInit(map, form, req, res, con);

        }

        return forward;
    }
    /**
     * <br>[機  能] 削除確認画面にてOKボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDeleteOk(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        log__.debug("-- doDelete START--");
        //セッション情報を取得
        int sessionUsrSid = getSessionUserSid(req);
        RequestModel reqMdl = getRequestModel(req);

        boolean commit = false;
        try {
            Tcd010ParamModel paramMdl = new Tcd010ParamModel();
            paramMdl.setParam(form);
            Tcd010Biz biz = new Tcd010Biz();
            biz.deleteTcdData(paramMdl, sessionUsrSid, con, reqMdl);
            paramMdl.setFormData(form);
            commit = true;
        } catch (SQLException e) {
            log__.error("タイムカード情報削除に失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        log__.debug("-- doDelete END--");

        //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        StringBuilder opLog = new StringBuilder();
        opLog.append("[");
        opLog.append(gsMsg.getMessage("tcd.log.targetm"));
        opLog.append("]");
        opLog.append(form.getYear());
        opLog.append(gsMsg.getMessage("cmn.year2"));
        opLog.append(form.getMonth());
        opLog.append(gsMsg.getMessage("cmn.month"));
        opLog.append("\n");
        opLog.append("[");
        opLog.append(gsMsg.getMessage("tcd.log.targetd"));
        opLog.append("]");
        boolean chk = false;
        for (String s : form.getSelectDay()) {
            if (chk) {
                opLog.append("、");
            }
            opLog.append(s);
            opLog.append(gsMsg.getMessage("cmn.day"));
            chk = true;
        }
        String delete = gsMsg.getMessage("cmn.delete");

        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        tcdBiz.outPutTimecardLog(map, reqMdl, con, delete,
                GSConstLog.LEVEL_TRACE, opLog.toString());


        ActionForward forward = __doDeleteCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 今月移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doMoveNow(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        //セッション情報を取得
        int sessionUsrSid = getSessionUserSid(req);

        TimecardBiz tcBiz = new TimecardBiz(getRequestModel(req));
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);

        //表示月を設定する
        UDate sysDate = TimecardBiz.getDspUDate(new UDate(), admConf);
        String strDspDate = sysDate.getDateString();
        form.setTcdDspFrom(strDspDate);
        form.setYear(strDspDate.substring(0, 4));
        form.setMonth(strDspDate.substring(4, 6));

        //日付選択をクリア
        form.setSelectDay(null);
    }

    /**
     * <br>[機  能] 前月移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doMoveLast(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        int year = Integer.parseInt(form.getYear());
        int month = Integer.parseInt(form.getMonth());

        UDate date = new UDate();
        date.setDate(year, month, 1);

        if (month > 1) {
            month--;
            date.setMonth(month);
            form.setMonth(date.getStrMonth());
        } else {
            //一月
            if (year > GSConstTimecard.MIN_YEAR) {
                //設定された年の最小値より値が大きい
                year--;
                date.setYear(year);
                form.setYear(date.getStrYear());
                form.setMonth(String.valueOf(12));
            }
        }

        String moveDate = form.getYear() + form.getMonth() + "01";
        form.setTcdDspFrom(moveDate);

        //日付選択をクリア
        form.setSelectDay(null);
    }

    /**
     * <br>[機  能] 来月移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doMoveNext(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        int year = Integer.parseInt(form.getYear());
        int month = Integer.parseInt(form.getMonth());

        UDate date = new UDate();
        date.setDate(year, month, 1);

        if (month < 12) {
            month++;
            date.setMonth(month);
            form.setMonth(date.getStrMonth());
        } else {
            //12月
            if (year < GSConstTimecard.MAX_YEAR) {
                //設定された年の最大値より値が小さい
                year++;
                date.setYear(year);
                form.setYear(date.getStrYear());
                form.setMonth("0" + String.valueOf(1));
            }
        }

        String moveDate = form.getYear() + form.getMonth() + "01";
        form.setTcdDspFrom(moveDate);

        //日付選択をクリア
        form.setSelectDay(null);
    }

    /**
     * <br>[機  能] 複数編集
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doMultiEdit(ActionMapping map,
                                    Tcd010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {
        log__.debug("複数編集");
        ActionForward forward = null;

        GsMessage gsMsg = new GsMessage();
        String editDay = gsMsg.getMessage(req, "tcd.157");

        if (!form.isDaySelected()) {
            //チェックボックスがチェックされていない
            ActionMessages ers = new ActionMessages();
            ers.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.select.required.text", editDay));
            saveErrors(req, ers);
            forward = __doInit(map, form, req, res, con);
        } else {
            forward = map.findForward("multi_edit");
        }
        return forward;
    }

    /**
     * <br>[機  能] 単体編集
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSingleEdit(ActionMapping map, Tcd010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("単体編集");
        ActionForward forward = null;
        forward = map.findForward("single_edit");
        return forward;
    }

    /**
     * <br>削除確認画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param admConf 基本設定
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doDeleteKakuDsp(
            ActionMapping map,
            Tcd010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            TcdAdmConfModel admConf,
            Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        RequestModel reqMdl = getRequestModel(req);
        Tcd020Biz biz = new Tcd020Biz();
        int year = Integer.parseInt(form.getYear());
        int month = Integer.parseInt(form.getMonth());
        String deleteDays = biz.getMultiDaysString(
                year,
                month,
                form.getSelectDay(),
                GSConstTimecard.DAYS_SEP,
                admConf.getTacSimebi(),
                10,
                reqMdl);

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("delete");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("reload");
        cmn999Form.setUrlCancel(urlForward.getPath());

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("tcd.50");

        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.list", msg, deleteDays));

        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>削除完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doDeleteCompDsp(ActionMapping map, Tcd010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //タイムカード削除完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("reload");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.50");

        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object", msg));

        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] エクセル勤務表出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力例外
     */
    private ActionForward __doExportXls(
            ActionMapping map,
            Tcd010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOException {
        log__.debug("start");
        log__.info("勤務表EXCEL形式出力 開始");
        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        //プラグイン固有のテンポラリパス取得
        RequestModel reqMdl = getRequestModel(req);
        Tcd010Biz tcdBiz = new Tcd010Biz();
        String outTempDir = tcdBiz.getTempDir(reqMdl);

        Tcd010ParamModel paramMdl = new Tcd010ParamModel();
        paramMdl.setParam(form);

        int targetUserSid = tcdBiz.getTargetUserSid(reqMdl, paramMdl, con);
        List<Tcd010Model> timeCardInfoList = tcdBiz.getXlsTimeCardList(reqMdl, paramMdl, con);
        paramMdl.setFormData(form);

        //勤務表出力
        TimeCardXlsParametarModel parmModel = new TimeCardXlsParametarModel();
        UDate date = new UDate();
        date.setDate(Integer.parseInt(form.getYear()), Integer.parseInt(form.getMonth()), 01);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("tcd.140");
        String msg2 = gsMsg.getMessage("cmn.export");

        String outBookName = msg + date.getStrYear() + date.getStrMonth() + ".xlsx";
        String outBookTmpName = date.getStrYear()
                + date.getStrMonth()
                + GSConstCommon.ENDSTR_SAVEFILE;
        parmModel.setOutBookName(outBookName);
        parmModel.setOutBookTmpName(outBookTmpName);
        parmModel.setTargetUserSid(targetUserSid);
        parmModel.setTargetYear(date.getYear());
        parmModel.setTargetMonth(date.getMonth());
        parmModel.setTimeCardInfoList(timeCardInfoList);
        parmModel.setAppRootPath(appRootPath);
        parmModel.setOutTempDir(outTempDir);
        parmModel.setCon(con);
        log__.debug("----------------------------------------");
        log__.debug("outBookName     :" + outBookName);
        log__.debug("sessionUsrSid   :" + targetUserSid);
        log__.debug("form.getYear()  :" + form.getYear());
        log__.debug("form.getMonth() :" + form.getMonth());
        log__.debug("appRootPath     :" + appRootPath);
        log__.debug("outTempDir      :" + outTempDir);
        log__.debug("----------------------------------------");
        TimecardBiz timecardBiz = new TimecardBiz(reqMdl);
        timecardBiz.createTimeCardXls(parmModel, "tcd010");
        try {
            String outFilePath = IOTools.setEndPathChar(outTempDir) + outBookTmpName;
            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            tcdBiz.deleteTempDir(reqMdl);

            //ログ出力
            TimecardBiz cBiz = new TimecardBiz(reqMdl);
            cBiz.outPutTimecardLog(map, reqMdl, con, msg2, GSConstLog.LEVEL_INFO,
                    outBookName);
        } catch (Exception e) {
            log__.error("エクセル勤務表出力の出力に失敗", e);
        }
        log__.info("勤務表EXCEL形式出力 終了");
        log__.debug("end");
        return null;
    }

    /**
     * <br>[解  説] エクスポートの処理
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doExportCsv(
        ActionMapping map,
        Tcd010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        Tcd010Biz tcdBiz = new Tcd010Biz();
        String tempDir = tcdBiz.getTempDir(reqMdl);

        int year = Integer.parseInt(form.getYear());
        int month = Integer.parseInt(form.getMonth());
        UDate date = new UDate();
        date.setYear(year);
        date.setMonth(month);
        String fileName = date.getStrYear() + date.getStrMonth() + TcdCsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        forward = __doExport(map, form, req, res, con, tempDir);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        tcdBiz.deleteTempDir(reqMdl);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.export");

        //ログ出力
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(map, reqMdl, con, msg, GSConstLog.LEVEL_INFO,
                fileName);

        return forward;
    }
    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(ActionMapping map, Tcd010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(CSV)");

        //セッション情報を取得
        int sessionUsrSid = getSessionUserSid(req);
        RequestModel reqMdl = getRequestModel(req);

        //検索条件をセット(SAVEより)
        int year = Integer.parseInt(form.getYear());
        int month = Integer.parseInt(form.getMonth());
        UDate date = new UDate();
        date.setYear(year);
        date.setMonth(month);
        String ym = date.getStrYear() + date.getStrMonth();

        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        int sime = admConf.getTacSimebi();

        //from～toを設定
        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);
        UDate frDate = new UDate();
        UDate toDate = calBiz.setTimeCardCal(year, month, sime, frDate);

        TcdCsvSearchModel searchMdl = new TcdCsvSearchModel();
        searchMdl.setTcdCsvFrDate(frDate);
        searchMdl.setTcdCsvToDate(toDate);
        searchMdl.setTcdCsvUserSid(Integer.parseInt(form.getUsrSid()));
        //CSVファイルを作成
        TcdCsvWriter write = new TcdCsvWriter(reqMdl);
        write.setFileYm(ym);
        write.setSearchModel(searchMdl);
        write.setSessionUserSid(sessionUsrSid);
        write.outputCsv(con, outDir);

        return null;
    }

    /**
     * <br>[機  能] PDFエクスポートの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doExportPdf(
        ActionMapping map,
        Tcd010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");
        ActionForward forward = null;

        //テンポラリディレクトリパスを取得
        Tcd010Biz tcdBiz = new Tcd010Biz();
        String tempDir = tcdBiz.getTempDir(getRequestModel(req));

        //ディレクトリの作成
        File tmpDir = new File(tempDir);
        tmpDir.mkdirs();
        forward = __createPdf(map, form, req, res, con, tempDir);

        return forward;
    }

    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __createPdf(ActionMapping map, Tcd010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(PDF)");

        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        //テンポラリディレクトリパスを取得
        Tcd010Biz tcdBiz = new Tcd010Biz();
        String outTempDir = tcdBiz.getTempDir(reqMdl);

        Tcd010ParamModel paramMdl = new Tcd010ParamModel();
        paramMdl.setParam(form);
        int targetUserSid = tcdBiz.getTargetUserSid(reqMdl, paramMdl, con);
        List<Tcd010Model> timeCardInfoList = tcdBiz.getXlsTimeCardList(reqMdl, paramMdl, con);
        paramMdl.setFormData(form);

      //勤務表出力
        TimeCardXlsParametarModel parmModel = new TimeCardXlsParametarModel();
        UDate date = new UDate();
        date.setDate(Integer.parseInt(form.getYear()), Integer.parseInt(form.getMonth()), 01);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("tcd.140");
        String msg2 = gsMsg.getMessage("cmn.export");
        String outBookName = msg + date.getStrYear() + date.getStrMonth() + ".pdf";
        String outBookTmpName = date.getStrYear()
                + date.getStrMonth()
                + GSConstCommon.ENDSTR_SAVEFILE;
        parmModel.setOutBookName(outBookName);
        parmModel.setOutBookTmpName(outBookTmpName);
        parmModel.setTargetUserSid(targetUserSid);
        parmModel.setTargetYear(date.getYear());
        parmModel.setTargetMonth(date.getMonth());
        parmModel.setTimeCardInfoList(timeCardInfoList);
        parmModel.setAppRootPath(appRootPath);
        parmModel.setOutTempDir(outTempDir);
        parmModel.setCon(con);
        log__.debug("----------------------------------------");
        log__.debug("outBookName     :" + outBookName);
        log__.debug("sessionUsrSid   :" + targetUserSid);
        log__.debug("form.getYear()  :" + form.getYear());
        log__.debug("form.getMonth() :" + form.getMonth());
        log__.debug("appRootPath     :" + appRootPath);
        log__.debug("outTempDir      :" + outTempDir);
        log__.debug("----------------------------------------");
        TimecardBiz timecardBiz = new TimecardBiz(reqMdl);
        timecardBiz.createTimeCardPdf(parmModel);
        try {
            String outFilePath = IOTools.setEndPathChar(outTempDir) + outBookTmpName;
            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            tcdBiz.deleteTempDir(reqMdl);

            //ログ出力
            TimecardBiz cBiz = new TimecardBiz(reqMdl);
            cBiz.outPutTimecardLog(map, reqMdl, con, msg2, GSConstLog.LEVEL_INFO,
                    outBookName, GSConstTimecard.TCD_LOG_FLG_PDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <br>[機  能] 始業・終業打刻ボタン押下時処理
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
    public ActionForward __doDakokuEdit(ActionMapping map,
                                      Tcd010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        ActionForward forward = null;
        BaseUserModel umodel = getSessionUserModel(req);
        int sessionUserSid = umodel.getUsrsid();
        RequestModel reqMdl = getRequestModel(req);

        con.setAutoCommit(false);
        boolean commitFlg = false;
        UDate dDate = null;
        int kbn = 1;
        UDate sysDate = new UDate();
        try {
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors = form.validateChkTcd010(sessionUserSid, con, reqMdl, sysDate);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                forward = __doInit(map, form, req, res, con);
                return forward;
            }
            //トランザクションsave
            saveToken(req);

            //タイムカード情報更新
            TimecardBiz biz = new TimecardBiz(reqMdl);
            int flg = -1;
            if (form.getDakokuStrSetFlg() == GSConstTimecard.DAKOKUBTN_PUSH) {
                flg = GSConstTimecard.DAKOKUKBN_START;
            }

            if (form.getDakokuEndSetFlg() == GSConstTimecard.DAKOKUBTN_PUSH) {
                flg = GSConstTimecard.DAKOKUKBN_END;
            }

            TcdTcdataModel tcMdl = biz.dakokuTcd(reqMdl, con, sysDate, flg, true);

            if (tcMdl != null) {
                dDate = tcMdl.getTcdDate();
                if (tcMdl.getTcdStrikeOuttime() != null) {
                    TimecardUtil.setTime(dDate, tcMdl.getTcdStrikeOuttime());
                    kbn = 2;
                } else {
                    TimecardUtil.setTime(dDate, tcMdl.getTcdStrikeIntime());
                }
            }
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
        con.setAutoCommit(true);

        GsMessage gsMsg = new GsMessage(reqMdl);

        //ログ出力
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        String opLog =  tcdBiz.opLogContent(kbn, null, dDate);
        tcdBiz.outPutTimecardLog(map, reqMdl, con, gsMsg.getMessage("cmn.entry"),
                GSConstLog.LEVEL_TRACE, opLog);
        //完了画面の設定
        return __setCompDsp(map, req, form);
    }

    /**
     * <br>[機  能]インポート画面遷移
     * <br>[解  説]
     * <br>[備  考]
     * @param map
     * @param form
     * @param req
     * @param res
     * @param con
     * @return
     * @throws SQLException
     */
    private ActionForward __doImportCsv(
            ActionMapping map,
            Tcd010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        ActionForward forward = null;
        forward = map.findForward("import");
        return forward;
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Tcd010Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("reload").getPath());

        GsMessage gsMsg = new GsMessage();
        String timecard = gsMsg.getMessage(req, "tcd.50");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("hensyu.kanryo.object", timecard));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 指定されたユーザのタイムカードを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param form フォーム
     * @param req リクエスト
     * @return true:閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行時例外
     */
    private boolean __canViewTimecard(Connection con, Tcd010Form form, HttpServletRequest req)
    throws SQLException {
        BaseUserModel umodel = getSessionUserModel(req);
        int tcdUserSid = umodel.getUsrsid();
        if (!StringUtil.isNullZeroStringSpace(form.getUsrSid())) {
            tcdUserSid = Integer.parseInt(form.getUsrSid());
        }

        TimecardBiz tcdBiz = new TimecardBiz(getRequestModel(req));
        return tcdBiz.canViewTimecard(con, tcdUserSid, umodel);
    }

    /**
     * <br>[機  能] タイムカードマネージャー画面へ
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doManager(ActionMapping map) {

        log__.debug("初期表示");
        return map.findForward("mng");
    }

    /**
     * <br>[機  能] 有休日数一覧
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     */
    private ActionForward __doYukyu(ActionMapping map) {
        ActionForward forward = null;

        forward = map.findForward("yukyu");
        return forward;
    }
}
