package jp.groupsession.v2.ntp.ntp100;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.NippouListSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 日報一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp100Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp100Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "ntp100";
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

        return false;
    }
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

        log__.debug("START_NTP100");
        ActionForward forward = null;
        Ntp100Form uform = (Ntp100Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("100_back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);
        } else if (cmd.equals("day")) {
            //日間スケジュール
            forward = map.findForward("100_day");
        } else if (cmd.equals("week")) {
            //週間スケジュール
            forward = map.findForward("100_week");
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward = map.findForward("100_month");
        } else if (cmd.equals("edit")) {
            //日報修正・閲覧
            forward = map.findForward("100_edit");
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        //ページコンボ変更
        } else if (cmd.equals("changeMode")) {
            log__.debug("ページコンボ変更");
            forward = __doChangeMode(map, uform, req, res, con);
        //左矢印押下
        } else if (cmd.equals("arrorw_left")) {
            log__.debug("左矢印ボタン押下");
            forward = __doPageMinus(map, uform, req, res, con);
        //右矢印押下
        } else if (cmd.equals("arrorw_right")) {
            log__.debug("右矢印ボタン押下");
            forward = __doPagePlus(map, uform, req, res, con);
        } else if (cmd.equals("ntp100search")) {
            log__.debug("検索ボタン押下");
            __doSearch(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("research")) {
            log__.debug("再表示");
            __doSearch(map, uform, req, res, con, false);
            forward = map.getInputForward();
        } else if (cmd.equals("import")) {
            //日報インポート
            forward = map.findForward("100_imp");
        } else if (cmd.equals("anken")) {
            //案件検索画面へ
            forward = map.findForward("anken");
        } else if (cmd.equals("masta")) {
            //マスタメンテ
            forward = map.findForward("masta");
        } else if (cmd.equals("bunseki")) {
            //分析
            forward = map.findForward("bunseki");
        } else if (cmd.equals("target")) {
            //目標設定画面へ
            forward = map.findForward("target");
        } else if (cmd.equals("ntp100export")) {
            //エクスポート
            log__.debug("エクスポートボタン押下");
            forward = __doDownLoad(map, uform, req, res, con);
        } else {
            //検索対象
            Ntp100Biz biz = new Ntp100Biz(getRequestModel(req));

            Ntp100ParamModel paramMdl = new Ntp100ParamModel();
            paramMdl.setParam(uform);
            biz.setDefultSearchTarget(paramMdl);
            //タイトルカラー
            biz.setDefultBgcolor(paramMdl);
            //見込み度
            biz.setDefultMikomido(paramMdl);
            paramMdl.setFormData(uform);

            //初期表示
            __doSearch(map, uform, req, res, con, true);
            forward = map.getInputForward();
        }
        log__.debug("END_NTP100");
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
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private void __doInit(ActionMapping map, Ntp100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Ntp100Biz biz = new Ntp100Biz(getRequestModel(req));

        Ntp100ParamModel paramMdl = new Ntp100ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);
    }

    /**
     * <br>検索処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save パラメータをセーブする場合true
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private void __doSearch(ActionMapping map, Ntp100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean save)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        RequestModel reqMdl = getRequestModel(req);

        con.setAutoCommit(true);
        Ntp100Biz biz = new Ntp100Biz(reqMdl);

        Ntp100ParamModel paramMdl = new Ntp100ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

        if (save) {
            //入力チェック
            ActionErrors errors = form.validateNtp100Check(map, reqMdl, con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                __doInit(map, form, req, res, con);
                return;
            }
            form.setNtp100PageNum(1);
            form.setNtp100SelectNtpAreaSid("-1");
            //検索条件SAVE
            form.saveSearchParm();
        }
        if (!form.validateNtp100Research(reqMdl, con)) {
            ActionErrors errors = new ActionErrors();

            //日報情報
            String textScheduleInfo = "日報";
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return;

        }
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        form.setNtp100searchUse(CommonBiz.getWebSearchUse(pconfig));

        paramMdl = new Ntp100ParamModel();
        paramMdl.setParam(form);
        int count = biz.getSearchResult(paramMdl, con);
        paramMdl.setFormData(form);

        if (count < 1) {
            ActionErrors errors = new ActionErrors();

            //日報情報
            String textScheduleInfo = "日報";
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return;
        }
    }

    /**
     * <br>リクエストを解析し画面遷移先を取得する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     */
    private ActionForward __doBack(ActionMapping map, Ntp100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;

        String dspMod = form.getDspMod();
        if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
            forward = map.findForward("100_week");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
            forward = map.findForward("100_month");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
            forward = map.findForward("100_day");
        }
        return forward;
    }


    /**
     * <br>[機  能] ページコンボ変更処理
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
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doChangeMode(ActionMapping map,
                                          Ntp100Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        form.setNtp100SelectNtpAreaSid("-1");
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 左矢印押下処理
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
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doPageMinus(
            ActionMapping map,
            Ntp100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //ページ数取得
        int page = form.getNtp100PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        form.setNtp100SelectNtpAreaSid("-1");

        //調整後ページ数セット
        form.setNtp100PageNum(page);
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印押下処理
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
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doPagePlus(ActionMapping map,
            Ntp100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //ページ数取得
        int page = form.getNtp100PageNum();
        page += 1;

        form.setNtp100SelectNtpAreaSid("-1");

        //調整後ページ数セット
        form.setNtp100PageNum(page);
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**日報情報ダウンロードの処理
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
    private ActionForward __doDownLoad(
        ActionMapping map,
        Ntp100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");
        ActionForward ret = null;
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstNippou.PLUGIN_ID_NIPPOU, TEMP_DIRECTORY_ID);
        String fileName = NtpCsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(false);
        if (!form.validateNtp100Research(getRequestModel(req), con)) {
            ActionErrors errors = new ActionErrors();

            //日報情報
            String textScheduleInfo = "日報";
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }



        ret = __doExport(map, form, req, res, con, tempDir);
        if (ret != null) {
            return ret;
        }
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstNippou.PLUGIN_ID_NIPPOU, TEMP_DIRECTORY_ID);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage(req, "cmn.export");

        //ログ出力処理
        NtpCommonBiz schBiz = new NtpCommonBiz(con, getRequestModel(req));
        schBiz.outPutLog(
                map, req, res,
                export, GSConstLog.LEVEL_INFO, fileName);

        return null;
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
    private ActionForward __doExport(ActionMapping map, Ntp100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(CSV)");
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        Ntp100Biz biz = new Ntp100Biz(getRequestModel(req));
        ActionErrors errors = form.validateNtp100Check(map, getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }


        //検索条件をセット(SAVEより)
        Ntp100ParamModel paramMdl = new Ntp100ParamModel();
        paramMdl.setParam(form);
        NippouListSearchModel searchMdl =
            biz.setNippouListSearchModel(paramMdl, con, sessionUsrSid);
        paramMdl.setFormData(form);

        //CSVファイルを作成
        NtpCsvWriter write = new NtpCsvWriter(req, form);
        write.setSearchModel(searchMdl);
        write.setSessionUserSid(sessionUsrSid);
        write.outputCsv(con, outDir);

        return null;
    }

}
