package jp.groupsession.v2.ntp.ntp030;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] 日報 日間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030Action extends AbstractNippouAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp030Action.class);

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

        log__.debug("START_Ntp030");
        ActionForward forward = null;
        Ntp030Form uform = (Ntp030Form) form;

        //権限チェック
        __setCanUsePluginFlg(uform, req, con);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        uform.setNtp030ScrollFlg("0");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("week")) {
            //週間スケジュール
            forward = map.findForward("030_week");
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward = map.findForward("030_month");
        } else if (cmd.equals("masta")) {
            //マスタメンテ
            forward = map.findForward("masta");
        } else if (cmd.equals("bunseki")) {
            //分析
            forward = map.findForward("bunseki");
        } else if (cmd.equals("target")) {
            //目標設定画面へ
            forward = map.findForward("target");
        } else if (cmd.equals("anken")) {
            //案件検索画面へ
            forward = map.findForward("anken");
        } else if (cmd.equals("030_back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);
        } else if (cmd.equals("add")) {
            //日報登録
            forward = map.findForward("030_add");
        } else if (cmd.equals("list")) {
            //一覧画面へ
            forward = map.findForward("030_list");
        } else if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, uform, req, res, con);
        } else if (cmd.equals("tempdel")) {
            //添付ファイル 削除ボタン押下
            __doDeleteTemp(map, uform, req, res, con);
        } else if (cmd.equals("dodelete")) {
            //削除更新実行
            __doDeleteOk(map, uform, req, res, con);
        } else if (cmd.equals("edit")) {
            //編集ボタンクリック
            forward = map.findForward("030_edit");
        } else if (cmd.equals("resetData")) {
            //データ再設定(確定ボタン、編集キャンセルボタン)
            __doResetData(map, uform, req, res, con);
        } else if (cmd.equals("selectedCompany")) {
            //会社選択
            log__.debug("会社を選択");
            forward = __doSelectCompany(map, uform, req, res, con);
        } else if (cmd.equals("deleteCompany")) {
            //会社削除
            log__.debug("会社削除");
            forward = __doDeleteCompany(map, uform, req, res, con);
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else if (cmd.equals("addComment")) {
            //コメントをする
            __doAddComment(map, req, res, uform, con);
        } else if (cmd.equals("delComment")) {
            //コメント削除
            __doDelComment(map, req, res, uform, con);
        } else if (cmd.equals("addGood")) {
            //いいね件数確認
            __doAddGood(req, res, uform, con);
        } else if (cmd.equals("commitGood")) {
            //いいね登録 最新データ取得
            __doCommitGood(map, req, res, uform, con);
        } else if (cmd.equals("deleteGood")) {
            //いいね削除 最新データ取得
            __doDeleteGood(map, req, res, uform, con);
        } else if (cmd.equals("getTimeLineData")) {
            //タイムラインに表示するデータを取得する
            __doGetTimeLineData(req, res, uform, con);
        } else if (cmd.equals("goodAddUser")) {
            //いいねいているユーザを取得する
            __doGetGoodAddUser(req, res, uform, con);
        } else if (cmd.equals("import")) {
            //日報インポート
            forward = map.findForward("030_imp");
        } else {
            //初期表示
            forward = __doInit(map, uform, req, res, con);
        }

        log__.debug("END_Ntp030");
        return forward;
    }

    /**
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        ActionForward forward = null;
        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        if (form.getNtp030DspMoveFlg() != 0) {
            //不要なパラメータのクリア
            form.clearParam();
        }

        Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req));
        Ntp030ParamModel paramMdl = new Ntp030ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, pconfig, con);
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        saveToken(req);

        forward = map.getInputForward();

        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>コメント削除
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doGetTimeLineData(HttpServletRequest req,
                                HttpServletResponse res,
                                Ntp030Form form,
                                Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String offset = NullDefault.getString(req.getParameter("ntpOffset"), "0");
        String gpSid = NullDefault.getString(req.getParameter("ntpDspGpSid"), "-1");
        String usrSid = NullDefault.getString(req.getParameter("ntpSelectUsrSid"), "-1");
        String sortKey = NullDefault.getString(req.getParameter("ntpSortLabel"), "0");

        form.setNtp010DspGpSid(gpSid);
        form.setNtp030SelectUsrSid(usrSid);
        form.setNtp030Offset(Integer.valueOf(offset));
        form.setNtp030Sort(Integer.valueOf(sortKey));

        if (usModel != null) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();

            //日報データ取得
            Ntp030Biz biz  = new Ntp030Biz(con, getRequestModel(req));
            JSONArray jsonData = new JSONArray();


            Ntp030ParamModel paramMdl = new Ntp030ParamModel();
            paramMdl.setParam(form);
            jsonData = biz.getTimeLineData(paramMdl, sessionUsrSid, con);
            paramMdl.setFormData(form);

            if (jsonData != null && !jsonData.isEmpty()) {
                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(タイムラインデータ)");
                }
            }
        }
    }

    /**
     * <br>削除処理実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception 実行例外
     */
    private ActionForward __doDeleteOk(ActionMapping map, Ntp030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req));
        String delNtpSid = NullDefault.getString(req.getParameter("delNtpSid"), "");

        if (!StringUtil.isNullZeroStringSpace(delNtpSid)) {

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {
                log__.debug("削除処理実行");

                //同時登録反映無しの場合
                biz.deleteNippou(Integer.valueOf(delNtpSid), con);

                //添付ファイル 削除ボタン押下
                __doDeleteTemp(map, form, req, res, con);

                commitFlg = true;
            } catch (SQLException e) {
                log__.error("日報削除に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        return null;
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
    private ActionForward __doBack(ActionMapping map, Ntp030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        //テンポラリディレクトリパスを取得
        ActionForward forward = null;

        String dspMod = form.getDspMod();
        if (dspMod.equals(GSConstNippou.DSP_MOD_WEEK)) {
            forward = map.findForward("040_week");
        } else if (dspMod.equals(GSConstNippou.DSP_MOD_MONTH)) {
            forward = map.findForward("040_month");
        } else if (dspMod.equals(GSConstNippou.DSP_MOD_DAY)) {
            forward = map.findForward("040_day");
        } else if (dspMod.equals(GSConstNippou.DSP_MOD_MAIN)) {
            forward = map.findForward("040_main");
        } else if (dspMod.equals(GSConstNippou.DSP_MOD_LIST)) {
            forward = map.findForward("040_list");
        }
        return forward;
    }

    /**
     * <br>[機  能] 会社を選択処理
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
    private ActionForward __doSelectCompany(ActionMapping map,
                                            Ntp030Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
    throws Exception {

        //重複したパラメータを除外する
        String[] acoSidArray = form.getNtp030CompanySid();
        if (acoSidArray != null) {
            String[] abaSidArray = form.getNtp030CompanyBaseSid();
            List<String> companyIdList = new ArrayList<String>();

            for (int index = 0; index < acoSidArray.length; index++) {
                String companyId = acoSidArray[index] + ":" + abaSidArray[index];
                if (companyIdList.indexOf(companyId) < 0) {
                    companyIdList.add(companyId);
                }
            }

            acoSidArray = new String[companyIdList.size()];
            abaSidArray = new String[companyIdList.size()];
            for (int index = 0; index < companyIdList.size(); index++) {
                String companyId = companyIdList.get(index);
                acoSidArray[index] = companyId.split(":")[0];
                abaSidArray[index] = companyId.split(":")[1];
            }

            form.setNtp030CompanySid(acoSidArray);
            form.setNtp030CompanyBaseSid(abaSidArray);
        }

        String[] addressIdArray = form.getNtp030AddressId();
        if (addressIdArray != null) {
            List<String> addressIdList = new ArrayList<String>();

            for (int index = 0; index < addressIdArray.length; index++) {
                if (addressIdList.indexOf(addressIdArray[index]) < 0) {
                    addressIdList.add(addressIdArray[index]);
                }
            }

            form.setNtp030AddressId(
                    addressIdList.toArray(new String[addressIdList.size()]));
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 会社を削除処理
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
    private ActionForward __doDeleteCompany(ActionMapping map,
                                            Ntp030Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
                                            throws Exception {
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDeleteTemp(ActionMapping map,
                                      Ntp030Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        String rowNum = NullDefault.getString(req.getParameter("rowNum"), "");

        if (StringUtil.isNullZeroString(rowNum)) {
            rowNum = "1";
        }
        //jsonデータ取得
        __getJsonTempData(req, form);

        return null;
    }

    /**
     * <br>JSON添付ファイルデータ取得
     * @param req リクエスト
     * @param form アクションフォーム
     * @throws Exception 実行例外
     */
    private void __getJsonTempData(HttpServletRequest req, Ntp030Form form) throws Exception {

        String nippouTempData = NullDefault.getString(req.getParameter("nippouTempData"), "");
        if (!StringUtil.isNullZeroStringSpace((String) nippouTempData)) {

            JSONArray obj = JSONArray.fromObject(nippouTempData);
            ArrayList<String> fileList = new ArrayList<String>();
            for (int i = 0; i < obj.size(); i++) {
                fileList.add((String) obj.getString(i));
            }
        }
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
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
        Ntp030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));

        int ntpSid = NullDefault.getInt(form.getNtp010NipSid(), -1);
        Long binSid = NullDefault.getLong(form.getNtp030BinSid(), -1);

        //日報の添付ファイルがダウンロード可能かチェックする
        if (ntpBiz.isCheckDLNtpTemp(con, ntpSid, getSessionUserSid(req), binSid)) {

            CmnBinfModel cbMdl = cmnBiz.getBinInfo(
                    con, binSid, GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                String download = gsMsg.getMessage("cmn.download");

                //ログ出力処理
                ntpBiz.outPutLog(map,
                        download, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(),
                        String.valueOf(binSid), GSConstNippou.NTP_LOG_FLG_DOWNLOAD);

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            }
        }

        return null;
    }

    /**
     * <br>編集キャンセルボタンクリック
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doResetData(
            ActionMapping map,
            Ntp030Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        JSONObject jsonData = null;
        Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req));

        //日報SID取得
        String resetNtpSid = NullDefault.getString(req.getParameter("resetNtpSid"), "");

        if (!StringUtil.isNullZeroStringSpace(resetNtpSid)) {
            //一時保管ファイル削除
            __doDeleteTemp(map, form, req, res, con);

            //データの再取得
            jsonData = biz.getNtpJsonData(con, resetNtpSid);
        }

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(確定or編集キャンセル)");
        }
    }

    /**
     * <br>コメント登録 + 最新データ取得
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doAddComment(ActionMapping map,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Ntp030Form form,
                                Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String ntpSid = NullDefault.getString(req.getParameter("commentNtpSid"), "");
        String commentStr = NullDefault.getString(req.getParameter("commentStr"), "");

        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)
                                   && !StringUtil.isNullZeroStringSpace(commentStr)) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();
            //コメントをデコード
            commentStr = URLDecoder.decode(commentStr, "utf-8");

            MlCountMtController cntCon = getCountMtController(req);
            Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req), cntCon);

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {

                //コメント新規登録
                Ntp030ParamModel paramMdl = new Ntp030ParamModel();
                paramMdl.setParam(form);
                biz.insertComment(paramMdl, Integer.valueOf(ntpSid), commentStr,
                        sessionUsrSid, getAppRootPath(), getPluginConfig(req),
                        getRequestModel(req));
                paramMdl.setFormData(form);

                commitFlg = true;

                NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
                String opCode = "コメント 登録";

                ntpBiz.outPutLog(
                 map, opCode, GSConstLog.LEVEL_TRACE, "");

            } catch (SQLException e) {
                log__.error("コメントの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }

            //最新のコメント取得
            JSONArray jsonData = biz.getComment(Integer.valueOf(ntpSid));
            if (jsonData != null && !jsonData.isEmpty()) {
                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(コメント)");
                }
            }
        }
    }

    /**
     * <br>コメント削除
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doDelComment(
                                ActionMapping map,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Ntp030Form form,
                                Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String ntpSid = NullDefault.getString(req.getParameter("commentNtpSid"), "");

        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)) {

            MlCountMtController cntCon = getCountMtController(req);
            Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req), cntCon);

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {
                //コメント新規登録
                biz.deleteComment(Integer.valueOf(ntpSid));
                commitFlg = true;

                NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
                String opCode = "コメント 削除";

                ntpBiz.outPutLog(
                 map, opCode, GSConstLog.LEVEL_TRACE, "");

            } catch (SQLException e) {
                log__.error("コメントの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
    }

    /**
     * 編集権限、アドレス帳、ショートメールプラグイン、が利用可能かフォームへ設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Ntp030Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        CommonBiz cmnBiz = new CommonBiz();

        //アドレス帳は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConst.PLUGINID_ADDRESS, pconfig)) {
            form.setAddressUseOk(GSConstNippou.PLUGIN_USE);
        } else {
            form.setAddressUseOk(GSConstNippou.PLUGIN_NOT_USE);
        }

        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstNippou.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstNippou.PLUGIN_NOT_USE);
        }

    }

    /**
     * <br>いいねされているか確認
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doAddGood(
                            HttpServletRequest req,
                            HttpServletResponse res,
                            Ntp030Form form,
                            Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String ntpSid = NullDefault.getString(req.getParameter("goodNtpSid"), "");

        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();
            NtpGoodDao gDao = new NtpGoodDao(con);
            int cnt = 0;
            Map<String, Integer> cntMap = new HashMap<String, Integer>();
            cnt = gDao.count(Integer.parseInt(ntpSid), sessionUsrSid);
            cntMap.put("cnt", cnt);
            JSONObject jsonData = JSONObject.fromObject(cntMap);
            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(いいね数)");
            }
        }
    }

    /**
     * <br>いいね登録
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doCommitGood(ActionMapping map,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Ntp030Form form,
                                Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String ntpSid = NullDefault.getString(req.getParameter("goodNtpSid"), "");

        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();

            MlCountMtController cntCon = getCountMtController(req);
            Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req), cntCon);

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {
                //いいね新規登録
                Ntp030ParamModel paramMdl = new Ntp030ParamModel();
                paramMdl.setParam(form);
                biz.insertGood(
                        paramMdl, Integer.valueOf(ntpSid), sessionUsrSid,
                        getAppRootPath(), getPluginConfig(req), getRequestModel(req));
                paramMdl.setFormData(form);



                commitFlg = true;

                //ログ出力
                NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
                String opCode = "いいね! 登録";

                ntpBiz.outPutLog(
                 map, opCode, GSConstLog.LEVEL_TRACE, "");

            } catch (SQLException e) {
                log__.error("いいねの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }

            //最新のいいねの件数を取得
            NtpGoodDao gDao = new NtpGoodDao(con);
            int cnt = 0;
            Map<String, Integer> cntMap = new HashMap<String, Integer>();
            cnt = gDao.count(Integer.parseInt(ntpSid));
            cntMap.put("cnt", cnt);
            JSONObject jsonData = JSONObject.fromObject(cntMap);
            if (jsonData != null && !jsonData.isEmpty()) {
                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(いいね)");
                }
            }
        }
    }

    /**
     * <br>いいね取り消し
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doDeleteGood(
                                ActionMapping map,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Ntp030Form form,
                                Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String ntpSid = NullDefault.getString(req.getParameter("goodNtpSid"), "");

        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();

            MlCountMtController cntCon = getCountMtController(req);
            Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req), cntCon);

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {
                //コメント新規登録
                biz.deleteGood(Integer.valueOf(ntpSid), sessionUsrSid);
                commitFlg = true;

                NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
                String opCode = "いいね! 取り消し";

                ntpBiz.outPutLog(
                 map, opCode, GSConstLog.LEVEL_TRACE, "");

            } catch (SQLException e) {
                log__.error("いいねの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }

            //最新のいいねの件数を取得
            NtpGoodDao gDao = new NtpGoodDao(con);
            int cnt = 0;
            Map<String, Integer> cntMap = new HashMap<String, Integer>();
            cnt = gDao.count(Integer.parseInt(ntpSid));
            cntMap.put("cnt", cnt);
            JSONObject jsonData = JSONObject.fromObject(cntMap);
            if (jsonData != null && !jsonData.isEmpty()) {
                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(いいね)");
                }
            }
        }
    }

    /**
     * <br>いいねしているユーザを取得
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con Connection
     * @throws Exception 実行例外
     */
    private void __doGetGoodAddUser(HttpServletRequest req,
                                HttpServletResponse res,
                                Ntp030Form form,
                                Connection con) throws Exception {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String ntpSid = NullDefault.getString(req.getParameter("goodAddNtpSid"), "");

        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();

            Ntp030Biz biz = new Ntp030Biz(con, getRequestModel(req));
            List<Ntp030GoodMdl> usrList = new ArrayList<Ntp030GoodMdl>();
            usrList = biz.getGoodAddUserList(Integer.parseInt(ntpSid), sessionUsrSid);
            if (!usrList.isEmpty()) {
                JSONArray jsonData = JSONArray.fromObject(usrList);
                try {
                    res.setHeader("Cache-Control", "no-cache");
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.print(jsonData);
                    out.flush();
                } catch (Exception e) {
                    log__.error("jsonデータ送信失敗(いいねしているユーザ)");
                }
            }
        }
    }
}