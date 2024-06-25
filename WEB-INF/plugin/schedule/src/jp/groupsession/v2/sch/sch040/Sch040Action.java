package jp.groupsession.v2.sch.sch040;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.ScheduleExSearchModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.pdf.SchTanPdfModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.sch.sch041kn.Sch041knBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040Action extends AbstractScheduleAction {
    /** エラーの種類 閲覧不可データ */
    private static final int ERRORTYPE_VIEW__ = 0;
    /** エラーの種類 データなし */
    private static final int ERRORTYPE_NONE__ = 1;
    /** エラーの種類 想定外エラー */
    private static final int ERRORTYPE_UNEXPECTED__ = 9;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch040Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "sch040";
    /** PDF用テンポラリディレクトリID */
    private static final String TEMP_DIRECTORY_PDF = "pdf";

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

        if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        }
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

        log__.debug("START_SCH040");
        ActionForward forward = null;
        Sch040Form uform = (Sch040Form) form;

        RequestModel reqMdl = getRequestModel(req);
        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        int scdSid = NullDefault.getInt(uform.getSch010SchSid(), -1);
        Sch010Biz schBiz = new Sch010Biz(reqMdl);
        if (scdSid != -1) {
            SchDataDao scdDao = new SchDataDao(con);
            SchDataModel scdMdl = scdDao.getEditCheckData(scdSid);
            if (!schBiz.checkViewOk(scdMdl, reqMdl.getSmodel().getUsrsid(), con)) {
                return getSubmitErrorPage(map, req);
            }

            //編集権限チェック
            if (!schBiz.isEditOk(scdSid, reqMdl, con, false)) {
                return map.findForward("040_dsp");
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        uform.setSch040ScrollFlg("0");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("040_week")) {
            //週間スケジュール
            forward = map.findForward("040_week");
        } else if (cmd.equals("040_month")) {
            //月間スケジュール
            forward = map.findForward("040_month");
        } else if (cmd.equals("040_day")) {
            //日間スケジュール
            forward = map.findForward("040_day");
        } else if (cmd.equals("040_ok")) {
            //スケジュール登録 重複登録チェック有
            forward = __doOk(map, uform, req, res, con, true);
        } else if (cmd.equals("040_ok_dup_nocheck")) {
            //スケジュール登録 重複登録チェック無
            forward = __doOk(map, uform, req, res, con, false);
        } else if (cmd.equals("040_del")) {
            //削除確認画面
            forward = __doDelete(map, uform, req, res, con);
        } else if (cmd.equals("040_del_ok")) {
            //削除更新実行
            forward = __doDeleteOk(map, uform, req, res, con);
        } else if (cmd.equals("040_back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);
        } else if (cmd.equals("040_group")) {
            //グループコンボ変更
            uform.setSch040ScrollFlg("1");
            forward = __doInit(map, uform, req, res, con);
        } else if (cmd.equals("040_fileDownload")) {
            //添付ファイルダウンロード
            forward = __doDownLoad(map, uform, req, res, con);
        } else if (cmd.equals("040_fileDelete")) {
            //添付ファイル削除
            forward = __doAttachDelete(map, uform, req, res, con);
        } else if (cmd.equals("040_resgroup")) {
            //施設グループコンボ変更
            uform.setSch040ScrollFlg("1");
            forward = __doInit(map, uform, req, res, con);
        } else if (cmd.equals("040_extend")) {
            //「拡張登録画面」
            log__.debug("「拡張登録画面」");
            forward = __doExtend(map, uform, req, res, con);
        } else if (cmd.equals("040_copy")) {
            //「複写して登録」
            log__.debug("「複写して登録」");
            forward = __doCopy(map, uform, req, res, con);
        } else if (cmd.equals("selectedCompany")) {
            //会社選択
            log__.debug("会社を選択");
            forward = __doSelectCompany(map, uform, req, res, con);
        } else if (cmd.equals("deleteCompany")) {
            //会社削除
            log__.debug("会社削除");
            forward = __doDeleteCompany(map, uform, req, res, con);
            //PDF出力
        } else if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            forward = __doDownLoadPdf(map, uform, req, res, con);
        } else if (cmd.equals("edit_comment")) {
            log__.debug("出欠確認コメント反映");
            forward = __doEditComment(map, uform, req, res, con);
        } else if (cmd.equals("getAttendComment")) {
            log__.debug("出欠確認コメント取得");
            __dogetAttendComment(map, uform, req, res, con);
        }  else {
            //初期表示
            forward = __doInit(map, uform, req, res, con);
        }

        log__.debug("END_SCH040");
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
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        if (!NullDefault.getString(form.getCmd(), "").equals(GSConstSchedule.CMD_ADD)
                && !NullDefault.getString(form.getCmd(), "").equals(GSConstSchedule.CMD_EDIT)) {
            //想定外エラー
            return __doDataError(map, form, req, res, con, ERRORTYPE_UNEXPECTED__);
        }

        //一括登録キーチェック
        if (form.getSchIkkatsuFlg() == GSConstSchedule.SCH_IKKATSUFLG_ENTRY) {
            if (!form.validateTorokukey()) {
                return getSubmitErrorPage(map, req);
            }
            SchDao schDao = new SchDao(con);
            int sessionUsrSid = getSessionUserSid(req);
            List<Integer> grpList = schDao.getNotRegistGrpList(sessionUsrSid);
            List<Integer> usrList = schDao.getNotRegistUserList(sessionUsrSid);

            for (String torokuKey : form.getSchIkkatuTorokuKey()) {
                String torokuTarget = torokuKey.split("-")[1];
                if (torokuTarget.startsWith("G")) {
                    int grpSid = Integer.parseInt(torokuTarget.substring(1));
                    if (grpList.contains(grpSid)) {
                        return getSubmitErrorPage(map, req);
                    }
                } else {
                    int usrSid = Integer.parseInt(torokuTarget);
                    if (usrList.contains(usrSid)) {
                        return getSubmitErrorPage(map, req);
                    }
                }
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        if (NullDefault.getInt(
                form.getSch040InitFlg(),  GSConstSchedule.INIT_FLG) == GSConstSchedule.INIT_FLG) {
            //繰り返し登録画面 テンポラリディレクトリの初期化
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(
                    reqMdl, GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH040);
            temp.createTempDir(
                    reqMdl, GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH040);
        }

        con.setAutoCommit(true);
        ActionForward forward = null;
        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);
        String appRootPath = getAppRootPath();
        String tempRoot = getTempPath(req);
        String domain = GroupSession.getResourceManager().getDomain(req);
        biz.getInitData(paramMdl, pconfig, con, appRootPath, tempRoot, domain);
        biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid(), reqMdl);
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        saveToken(req);

        if (NullDefault.getString(form.getCmd(), "").equals(GSConstSchedule.CMD_EDIT)) {
            if (!form.isSch040ViewFlg()) {
                //編集データ閲覧権限チェック
                forward = __doDataError(map, form, req, res, con, ERRORTYPE_VIEW__);
            } else if (!form.isSch040DataFlg()) {
                //編集データ有無チェック
                forward = __doDataError(map, form, req, res, con, ERRORTYPE_NONE__);
            } else {
                forward = map.getInputForward();
            }
        } else {
            forward = map.getInputForward();
        }
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] 拡張登録へ遷移します。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doExtend(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;
        forward = map.findForward("040_extend");

        return forward;
    }

    /** エラーメッセージ作成
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __errorMessage(ActionMapping map,
            Sch040Form form,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textChange = gsMsg.getMessage("cmn.change");
        String msg = gsMsg.getMessage("cmn.edit");
        MessageResources msgRes = getResources(req);
        //メッセージセット
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        if (form.getListMod().equals(GSConstSchedule.DSP_MOD_LIST)) {
            urlForward = map.findForward("040_list");
        } else {

            if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("040_week");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MONTH)) {

                urlForward = map.findForward("040_month");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("040_day");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MAIN)) {
                urlForward = map.findForward("040_main");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
                urlForward = map.findForward("040_kojin");
            } else {
                urlForward = map.findForward("040_week");
            }
        }
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("error.edit.power.user", msg, textChange));
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>登録ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param warnFlg 重複登録警告チェックフラグ
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doOk(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean warnFlg)
    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        if (form.getSchIkkatsuFlg() == GSConstSchedule.SCH_IKKATSUFLG_ENTRY) {
            return __doOkIkkatsu(map, form, req, res, con, warnFlg);
        }
        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        int selectUserSid = NullDefault.getInt(form.getSch010SelectUsrSid(), -1);
        if (selectUserSid >= 0) {
            int sessionUserSid = getSessionUserSid(req);
            String selectUsrKbn = NullDefault.getString(form.getSch010SelectUsrKbn(), "");
            SchDao schDao = new SchDao(con);
            if (selectUsrKbn.equals(String.valueOf(GSConstSchedule.USER_KBN_GROUP))) {
                //グループスケジュール登録権限チェック
                if (!schDao.canRegistGroupSchedule(selectUserSid, sessionUserSid)) {
                    return __errorMessage(map, form, req);
                }
            } else {
                //ユーザスケジュール登録権限チェック
                if (!schDao.canRegistUserSchedule(selectUserSid, sessionUserSid)) {
                    return __errorMessage(map, form, req);
                }
            }
        }

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        RequestModel reqMdl = getRequestModel(req);

        ActionForward forward = null;
        ActionErrors errors = form.validateCheck(reqMdl, con, sessionUsrSid);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        log__.debug("入力エラーなし");

        //登録対象者名
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        String userName = biz.getUsrName(
                Integer.parseInt(form.getSch010SelectUsrSid()),
                Integer.parseInt(form.getSch010SelectUsrKbn()), con);

        if (warnFlg) {
            //編集＆出欠回答者モード以外の場合
            if (!String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER).equals(
                    form.getSch040EditDspMode())) {
                //重複登録警告チェック
                forward = __doDupWarningCheck(map, form, req, res, con);
            }
        }
        String opeLogBefore = "";
        ScheduleSearchModel oldMdl = null;
        //更新登録の場合旧データの存在チェック
        if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
            SchCommonBiz commonBiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = commonBiz.getAdmConfModel(con);
            Sch040Biz sch040biz = new Sch040Biz(con, reqMdl);
            oldMdl = sch040biz.getSchData(
                    Integer.parseInt(form.getSch010SchSid()), adminConf, con);
            if (oldMdl == null) {
                return __doNoneDataError(map, form, req, res, con);
            }
            opeLogBefore = sch040biz.getOpLogBefore(form.getSch010SchSid(),
                    form.getSch010SelectUsrSid(), userName, false, oldMdl);
        }

        if (forward == null || !warnFlg) {
            //出欠回答者モードの場合
            if (String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER).equals(
                    form.getSch040EditDspMode())) {
                forward = __doCommitAns(map, form, req, res, con, oldMdl);

            //通常処理
            } else {
                //確認画面省略：更新処理2006/08/17
                forward = __doCommit(map, form, req, res, con, oldMdl);
            }

            //ログ出力に必要な項目名、値を設定する
            GsMessage gsMsg = new GsMessage(req);
            String entry = gsMsg.getMessage("cmn.entry");
            String change = gsMsg.getMessage("cmn.change");
            int startHour = NullDefault.getInt(form.getSch040FrHour(), 0);
            int startMin = NullDefault.getInt(form.getSch040FrMin(), 0);
            int endHour = NullDefault.getInt(form.getSch040ToHour(), 0);
            int endMin = NullDefault.getInt(form.getSch040ToMin(), 0);

            //開始日時
            String startDate
               = gsMsg.getMessage("cmn.view.date", new String[] {
                       form.getSch040FrYear(),
                       form.getSch040FrMonth(),
                       form.getSch040FrDay(),
                       String.valueOf(startHour),
                       String.valueOf(startMin)
                });
            //終了日時
            String endDate
               = gsMsg.getMessage("cmn.view.date", new String[] {
                    form.getSch040ToYear(),
                    form.getSch040ToMonth(),
                    form.getSch040ToDay(),
                    String.valueOf(endHour),
                    String.valueOf(endMin)
                 });

            //施設予約
            Sch040ParamModel paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            ArrayList<String> sisetuList = biz.getPdfSisInf(paramMdl);
            paramMdl.setFormData(form);
            //ログ出力処理
            SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
            String opCode = "";
            if (form.getCmd().equals(GSConstSchedule.CMD_ADD)) {
                opCode = entry;
            } else if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
                opCode = change;
            }
            int editFlg = -1;
            int henkouFlg = 0;
            if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
                editFlg = Integer.parseInt(form.getSch040BatchRef());
                henkouFlg = 2;
            }
            //対象者取得
            if (Integer.parseInt(form.getSch040BatchRef()) == GSConstSchedule.SAME_EDIT_ON) {
                UserBiz userBiz = new UserBiz();
                ArrayList <CmnUsrmInfModel> selectUsrList = null;
                selectUsrList =
                        (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con, form.getSv_users());
                if (selectUsrList != null) {
                    for (int idx = 0; idx < selectUsrList.size(); idx++) {

                        userName += ", " + selectUsrList.get(idx).getUsiSei()
                                + " " + selectUsrList.get(idx).getUsiMei();
                    }
                }
            }
            ArrayList<String> dateList = new ArrayList<String>();
            dateList.add(startDate);
            dateList.add(endDate);
            String outOpLog = biz.getOpLog(
                    dateList, form.getSch040Title(),
                    form.getSch040Value(), userName, sisetuList, editFlg, henkouFlg, false);
            schBiz.outPutLog(map, req, res, opCode, GSConstLog.LEVEL_TRACE,
                    opeLogBefore + outOpLog);
        }

        return forward;
    }

    /**
     * <br>一括登録時の処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param warnFlg 重複登録警告チェックフラグ
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doOkIkkatsu(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean warnFlg)
                    throws Exception {

        //回答モード，登録キーが不正ではないか確認
        if (String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER).equals(form.getSch040EditDspMode())
                || !form.validateTorokukey()
                || !form.getCmd().equals(GSConstSchedule.CMD_ADD)) {
            return getSubmitErrorPage(map, req);
        }

        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        for (String torokuKey : form.getSchIkkatuTorokuKey()) {
            String sidStr = torokuKey.substring(torokuKey.indexOf("-") + 1);
            int selectUsrKbn = GSConstSchedule.USER_KBN_USER;
            if (sidStr.startsWith("G")) {
                sidStr = sidStr.replace("G", "");
                selectUsrKbn = GSConstSchedule.USER_KBN_GROUP;
            }
            int selectUserSid = Integer.parseInt(sidStr);

            if (selectUserSid >= 0) {
                int sessionUserSid = getSessionUserSid(req);
                SchDao schDao = new SchDao(con);
                if (selectUsrKbn == GSConstSchedule.USER_KBN_GROUP) {
                    //グループスケジュール登録権限チェック
                    if (!schDao.canRegistGroupSchedule(selectUserSid, sessionUserSid)) {
                        return __errorMessage(map, form, req);
                    }
                } else {
                    //ユーザスケジュール登録権限チェック
                    if (!schDao.canRegistUserSchedule(selectUserSid, sessionUserSid)) {
                        return __errorMessage(map, form, req);
                    }
                }
            }
        }

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        RequestModel reqMdl = getRequestModel(req);

        ActionForward forward = null;
        ActionErrors errors = form.validateCheck(reqMdl, con, sessionUsrSid);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        log__.debug("入力エラーなし");

        if (warnFlg) {
            //重複登録警告チェック
            forward = __doDupWarningCheckIkkatsu(map, form, req, res, con);
        }

        if (forward != null) {
            return forward;
        }

        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        GsMessage gsMsg = new GsMessage(req);
        List<String> targetDspList = new ArrayList<String>();

        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);
        if (forward == null || !warnFlg) {
            //一括登録
            __doCommitIkkatsu(map, form, req, res, con);

            //ログ出力処理
            for (String torokuKey : form.getSchIkkatuTorokuKey()) {
                String dateStr = torokuKey.substring(0, torokuKey.indexOf("-"));
                String sidStr = torokuKey.substring(torokuKey.indexOf("-") + 1);
                int selectUsrKbn = GSConstSchedule.USER_KBN_USER;
                if (sidStr.startsWith("G")) {
                    sidStr = sidStr.replace("G", "");
                    selectUsrKbn = GSConstSchedule.USER_KBN_GROUP;
                }
                UDate date = UDate.getInstanceStr(dateStr);

                //登録対象者名
                String userName = biz.getUsrName(
                        Integer.parseInt(sidStr), selectUsrKbn, con);

                //ログ出力に必要な項目名、値を設定する
                int startHour = NullDefault.getInt(form.getSch040FrHour(), 0);
                int startMin = NullDefault.getInt(form.getSch040FrMin(), 0);
                int endHour = NullDefault.getInt(form.getSch040ToHour(), 0);
                int endMin = NullDefault.getInt(form.getSch040ToMin(), 0);

                //開始日時
                String startDate
                = gsMsg.getMessage("cmn.view.date", new String[] {
                        String.valueOf(date.getYear()),
                        String.valueOf(date.getMonth()),
                        String.valueOf(date.getIntDay()),
                        String.valueOf(startHour),
                        String.valueOf(startMin)
                });
                //終了日時
                String endDate
                = gsMsg.getMessage("cmn.view.date", new String[] {
                        String.valueOf(date.getYear()),
                        String.valueOf(date.getMonth()),
                        String.valueOf(date.getIntDay()),
                        String.valueOf(endHour),
                        String.valueOf(endMin)
                });

                //対象者取得
                if (Integer.parseInt(form.getSch040BatchRef()) == GSConstSchedule.SAME_EDIT_ON) {
                    UserBiz userBiz = new UserBiz();
                    ArrayList <CmnUsrmInfModel> selectUsrList = null;
                    selectUsrList =
                            (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(
                                    con, form.getSv_users());
                    if (selectUsrList != null) {
                        for (int idx = 0; idx < selectUsrList.size(); idx++) {

                            userName += ", " + selectUsrList.get(idx).getUsiSei()
                                    + " " + selectUsrList.get(idx).getUsiMei();
                        }
                    }
                }
                ArrayList<String> dateList = new ArrayList<String>();
                dateList.add(startDate);
                dateList.add(endDate);

                //対象の取得
                StringBuilder sbValue = new StringBuilder();
                sbValue.append(userName);
                sbValue.append("(");
                sbValue.append(dateList.get(0));
                sbValue.append(")");
                targetDspList.add(sbValue.toString());
            }

            //施設予約
            paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            ArrayList<String> sisetuList = biz.getPdfSisInf(paramMdl);
            paramMdl.setFormData(form);

            String opCode = gsMsg.getMessage("cmn.entry");

            String logStr = biz.getIkkatsuOpLog(
                    targetDspList, form.getSch040Title(), form.getSch040Value(), sisetuList);
            schBiz.outPutLog(map, req, res, opCode, GSConstLog.LEVEL_TRACE, logStr);
        }

        return __doCompDsp(map, form, req, res, con);
    }

    /**
     * <br>処理モードによって登録・修正処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param oldMdl 編集前データ
     * @return ActionForward 画面遷移
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            ScheduleSearchModel oldMdl)
    throws Exception {
        ActionForward forward = null;

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);

        MlCountMtController cntCon = getCountMtController(req);
        Sch040Biz biz = new Sch040Biz(con, reqMdl, cntCon);
        boolean commitFlg = false;
        con.setAutoCommit(false);

        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        try {
            //公開対象:指定グループ・ユーザのみ の時、選択対象から削除済みユーザを除外する。
            if (NullDefault.getInt(form.getSch040Public(), GSConstSchedule.DSP_PUBLIC)
                    == GSConstSchedule.DSP_USRGRP) {
                form.setSch040DisplayTarget(
                        biz.pubDelUserRemove(form.getSch040DisplayTarget(), con));
            }

            //会社情報の設定
            Sch040ParamModel paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            biz.setCompanyData(paramMdl, con, sessionUsrSid, reqMdl);
            paramMdl.setFormData(form);

            ArrayList<int[]> sidDataList = new ArrayList<int[]>();

            //新規登録
            if (form.getCmd().equals(GSConstSchedule.CMD_ADD)) {
                //採番マスタからスケジュールSIDを取得
                paramMdl = new Sch040ParamModel();
                paramMdl.setParam(form);
                sidDataList = biz.insertScheduleDate(
                        reqMdl, paramMdl, appRootPath, plconf, smailPluginUseFlg, false);
                paramMdl.setFormData(form);
            } else if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
                paramMdl = new Sch040ParamModel();
                paramMdl.setParam(form);
                sidDataList = biz.updateScheduleDate(reqMdl, paramMdl,
                        sessionUsrSid, appRootPath, plconf, smailPluginUseFlg, oldMdl);
                paramMdl.setFormData(form);
            }

            //テンポラリディレクトリパスを取得
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            String tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
            //申請通知メール
            for (int[] sidData : sidDataList) {
                int yoyakuSid = sidData[0];
                int sisetsuSid = sidData[1];
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                //選択した施設に承認設定がされている場合
                if (rsvCmnBiz.isApprSis(con, sisetsuSid, sessionUsrSid)) {
                    //ショートメールで通知
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                        RsvRegSmailModel regMdl = new RsvRegSmailModel();
                        regMdl.setCon(con);
                        regMdl.setReqMdl(reqMdl);
                        regMdl.setRsySid(yoyakuSid);
                        regMdl.setRsdSid(sisetsuSid);
                        regMdl.setCntCon(cntCon);
                        regMdl.setUserSid(sessionUsrSid);
                        regMdl.setAppRootPath(getAppRootPath());
                        regMdl.setTempDir(tempDir);
                        regMdl.setPluginConfig(getPluginConfig(req));

                        rsvCmnBiz.sendRegSmail(regMdl);
                    }
                }
            }

            con.commit();
            commitFlg = true;
        } catch (Exception e) {
            log__.error("スケジュール登録に失敗しました" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH040);

        forward = __doCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>一括登録処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommitIkkatsu(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        ActionForward forward = null;

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);

        MlCountMtController cntCon = getCountMtController(req);
        Sch040Biz biz = new Sch040Biz(con, reqMdl, cntCon);
        boolean commitFlg = false;
        con.setAutoCommit(false);

        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        try {
            //公開対象:指定グループ・ユーザのみ の時、選択対象から削除済みユーザを除外する。
            if (NullDefault.getInt(form.getSch040Public(), GSConstSchedule.DSP_PUBLIC)
                    == GSConstSchedule.DSP_USRGRP) {
                form.setSch040DisplayTarget(
                        biz.pubDelUserRemove(form.getSch040DisplayTarget(), con));
            }

            //会社情報の設定
            Sch040ParamModel paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            biz.setCompanyData(paramMdl, con, sessionUsrSid, reqMdl);
            paramMdl.setFormData(form);

            ArrayList<int[]> sidDataList = new ArrayList<int[]>();
            sidDataList = biz.insertScheduleDate(reqMdl, paramMdl,
                        appRootPath, plconf, smailPluginUseFlg, true);
            paramMdl.setFormData(form);
            //テンポラリディレクトリパスを取得
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            String tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
            //申請通知メール
            for (int[] sidData : sidDataList) {
                int yoyakuSid = sidData[0];
                int sisetsuSid = sidData[1];
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                //選択した施設に承認設定がされている場合
                if (rsvCmnBiz.isApprSis(con, sisetsuSid, sessionUsrSid)) {
                    //ショートメールで通知
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                        RsvRegSmailModel regMdl = new RsvRegSmailModel();
                        regMdl.setCon(con);
                        regMdl.setReqMdl(reqMdl);
                        regMdl.setRsySid(yoyakuSid);
                        regMdl.setRsdSid(sisetsuSid);
                        regMdl.setCntCon(cntCon);
                        regMdl.setUserSid(sessionUsrSid);
                        regMdl.setAppRootPath(getAppRootPath());
                        regMdl.setTempDir(tempDir);
                        regMdl.setPluginConfig(getPluginConfig(req));

                        rsvCmnBiz.sendRegSmail(regMdl);
                    }
                }
            }

            con.commit();
            commitFlg = true;
        } catch (Exception e) {
            log__.error("スケジュール登録に失敗しました" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        forward = __doCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>出欠確認 回答者の処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param oldMdl 編集前データ
     * @return ActionForward 画面遷移
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommitAns(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            ScheduleSearchModel oldMdl)
    throws Exception {
        ActionForward forward = null;

        //セッション情報を取得
        RequestModel reqMdl = getRequestModel(req);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();


        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);

        MlCountMtController cntCon = getCountMtController(req);
        Sch040Biz biz = new Sch040Biz(con, reqMdl, cntCon);


        boolean commitFlg = false;
        con.setAutoCommit(false);

        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        try {

            Sch040ParamModel paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            biz.updateScheduleDateAns(
                    reqMdl, paramMdl, sessionUsrSid, appRootPath,
                    plconf, smailPluginUseFlg, oldMdl);
            paramMdl.setFormData(form);

            con.commit();
            commitFlg = true;
        } catch (Exception e) {
            log__.error("スケジュール登録に失敗しました" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            }
        }
        forward = __doCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>削除ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, Exception {

        ActionForward forward = null;
        //編集権限チェック
        ActionErrors errors = form.validatePowerCheck(getRequestModel(req), con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            log__.debug("権限エラー");
            return __doInit(map, form, req, res, con);
        }
        RequestModel reqMdl = getRequestModel(req);

        SchCommonBiz cbiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = cbiz.getAdmConfModel(con);
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        ScheduleSearchModel oldMdl = biz.getSchData(
                Integer.parseInt(form.getSch010SchSid()), adminConf, con);
        if (oldMdl == null) {
            return __doNoneDataError(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //確認画面へ
        log__.debug("削除確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("040_del_ok");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("040_del_cancel");
        cmn999Form.setUrlCancel(urlForward.getPath());
        GsMessage gsMsg = new GsMessage(req);

        ScheduleSearchModel seachMdl =
                biz.getSchData(Integer.parseInt(form.getSch010SchSid()), adminConf, con);

        //削除確認画面の文章を設定する。
        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);
        String delTarget = biz.delTargetSet(paramMdl, seachMdl, gsMsg);
        paramMdl.setFormData(form);

        //スケジュール
        String textSchedule = gsMsg.getMessage("schedule.108");
        if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
            cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.list",
                    textSchedule,
                    StringUtilHtml.transToHTmlPlusAmparsant(delTarget)));
        }
        //週間・日間・月間
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        //選択
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch010SchSid", form.getSch010SchSid());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        //登録・修正画面
        cmn999Form.addHiddenParam("sch040Bgcolor", form.getSch040Bgcolor());
        cmn999Form.addHiddenParam("sch040Title", form.getSch040Title());
        cmn999Form.addHiddenParam("sch040Value", form.getSch040Value());
        cmn999Form.addHiddenParam("sch040Biko", form.getSch040Biko());
        cmn999Form.addHiddenParam("sch040Edit", form.getSch040Edit());
        cmn999Form.addHiddenParam("sch040Public", form.getSch040Public());
        cmn999Form.addHiddenParam("sch040FrYear", form.getSch040FrYear());
        cmn999Form.addHiddenParam("sch040FrMonth", form.getSch040FrMonth());
        cmn999Form.addHiddenParam("sch040FrDay", form.getSch040FrDay());
        cmn999Form.addHiddenParam("sch040FrHour", form.getSch040FrHour());
        cmn999Form.addHiddenParam("sch040FrMin", form.getSch040FrMin());
        cmn999Form.addHiddenParam("sch040ToYear", form.getSch040ToYear());
        cmn999Form.addHiddenParam("sch040ToMonth", form.getSch040ToMonth());
        cmn999Form.addHiddenParam("sch040ToDay", form.getSch040ToDay());
        cmn999Form.addHiddenParam("sch040ToHour", form.getSch040ToHour());
        cmn999Form.addHiddenParam("sch040ToMin", form.getSch040ToMin());
        cmn999Form.addHiddenParam("sch040FrDate", form.getSch040FrDate());
        cmn999Form.addHiddenParam("sch040ToDate", form.getSch040ToDate());
        cmn999Form.addHiddenParam("sch040FrTime", form.getSch040FrTime());
        cmn999Form.addHiddenParam("sch040ToTime", form.getSch040ToTime());
        cmn999Form.addHiddenParam("sch040TimeKbn", form.getSch040TimeKbn());
        cmn999Form.addHiddenParam("sch040GroupSid", form.getSch040GroupSid());
        cmn999Form.addHiddenParam("sch040BatchRef", form.getSch040BatchRef());
        cmn999Form.addHiddenParam("sch040ReserveGroupSid", form.getSch040ReserveGroupSid());
        cmn999Form.addHiddenParam("sch040ResBatchRef", form.getSch040ResBatchRef());

        cmn999Form.addHiddenParam("sch040ReminderTime", form.getSch040ReminderTime());
        cmn999Form.addHiddenParam("sch040TargetGroup", form.getSch040TargetGroup());

        cmn999Form.addHiddenParam("sch040AttendKbn", form.getSch040AttendKbn());
        cmn999Form.addHiddenParam("sch040EditDspMode", form.getSch040EditDspMode());
        cmn999Form.addHiddenParam("sch040InitFlg", form.getSch040InitFlg());
        cmn999Form.addHiddenParam("sch040DisplayTarget", form.getSch040DisplayTarget());

        //同時登録ユーザ
        String[] users = form.getSv_users();
        if (users != null) {
            for (String user : users) {
                cmn999Form.addHiddenParam("sv_users", user);
            }
        }
        //同時登録施設
        String[] reserves = form.getSvReserveUsers();
        if (reserves != null) {
            for (String reserve : reserves) {
                cmn999Form.addHiddenParam("svReserveUsers", reserve);
            }
        }
        cmn999Form.addHiddenParam("sch040contact", form.getSch040contact());
        cmn999Form.addHiddenParam("sch040CompanySid", form.getSch040CompanySid());
        cmn999Form.addHiddenParam("sch040CompanyBaseSid", form.getSch040CompanyBaseSid());
        cmn999Form.addHiddenParam("sch040AddressId", form.getSch040AddressId());

        //拡張登録画面
        cmn999Form.addHiddenParam("sch041ExtKbn", form.getSch041ExtKbn());
        cmn999Form.addHiddenParam("sch041Week", form.getSch041Week());
        cmn999Form.addHiddenParam("sch041Day", form.getSch041Day());
        cmn999Form.addHiddenParam("sch041MonthOfYearly", form.getSch041MonthOfYearly());
        cmn999Form.addHiddenParam("sch041DayOfYearly", form.getSch041DayOfYearly());
        cmn999Form.addHiddenParam("sch041ConfKbn", form.getSch041ConfKbn());
        cmn999Form.addHiddenParam("sch041WeekOrDay", form.getSch041WeekOrDay());
        cmn999Form.addHiddenParam("sch041TranKbn", form.getSch041TranKbn());
        cmn999Form.addHiddenParam("sch041FrYear", form.getSch041FrYear());
        cmn999Form.addHiddenParam("sch041FrMonth", form.getSch041FrMonth());
        cmn999Form.addHiddenParam("sch041FrDay", form.getSch041FrDay());
        cmn999Form.addHiddenParam("sch041ToYear", form.getSch041ToYear());
        cmn999Form.addHiddenParam("sch041ToMonth", form.getSch041ToMonth());
        cmn999Form.addHiddenParam("sch041ToDay", form.getSch041ToDay());
        cmn999Form.addHiddenParam("sch041FrHour", form.getSch041FrHour());
        cmn999Form.addHiddenParam("sch041FrMin", form.getSch041FrMin());
        cmn999Form.addHiddenParam("sch041ToHour", form.getSch041ToHour());
        cmn999Form.addHiddenParam("sch041ToMin", form.getSch041ToMin());
        cmn999Form.addHiddenParam("sch041FrDate", form.getSch041FrDate());
        cmn999Form.addHiddenParam("sch041ToDate", form.getSch041ToDate());
        cmn999Form.addHiddenParam("sch041FrTime", form.getSch041FrTime());
        cmn999Form.addHiddenParam("sch041ToTime", form.getSch041ToTime());
        cmn999Form.addHiddenParam("sch041Bgcolor", form.getSch041Bgcolor());
        cmn999Form.addHiddenParam("sch041Title", form.getSch041Title());
        cmn999Form.addHiddenParam("sch041Value", form.getSch041Value());
        cmn999Form.addHiddenParam("sch041Biko", form.getSch041Biko());
        cmn999Form.addHiddenParam("sch041Public", form.getSch041Public());
        cmn999Form.addHiddenParam("sch041Edit", form.getSch041Edit());
        cmn999Form.addHiddenParam("sch041BatchRef", form.getSch041BatchRef());
        cmn999Form.addHiddenParam("sch041GroupSid", form.getSch041GroupSid());
        cmn999Form.addHiddenParam("sch041InitFlg", form.getSch041InitFlg());
        cmn999Form.addHiddenParam("sch041DisplayTarget", form.getSch041DisplayTarget());
        cmn999Form.addHiddenParam("sch041TimeKbn", form.getSch041TimeKbn());
        //曜日
        String[] weeks = form.getSch041Dweek();
        if (weeks != null) {
//            for (String week : weeks) {
//                cmn999Form.addHiddenParam("sch041Dweek", weeks);
//            }
            cmn999Form.addHiddenParam("sch041Dweek", weeks);
        }
        //同時登録ユーザ
        String[] exusers = form.getSch041SvUsers();
        if (exusers != null) {
            for (String exuser : exusers) {
                cmn999Form.addHiddenParam("sch041SvUsers", exuser);
            }
        }
        //同時登録施設
        String[] exreserves = form.getSch041SvReserve();
        if (exreserves != null) {
            for (String exreserve : exreserves) {
                cmn999Form.addHiddenParam("sch041SvReserve", exreserve);
            }
        }
        cmn999Form.addHiddenParam("sch041contact", form.getSch041contact());
        cmn999Form.addHiddenParam("sch041CompanySid", form.getSch041CompanySid());
        cmn999Form.addHiddenParam("sch041CompanyBaseSid", form.getSch041CompanyBaseSid());
        cmn999Form.addHiddenParam("sch041AddressId", form.getSch041AddressId());

        //検索対象
        String[] searchTarget = form.getSch100SearchTarget();
        if (searchTarget != null) {
            for (String target : searchTarget) {
                cmn999Form.addHiddenParam("sch100SearchTarget", target);
            }
        }
        //検索対象
        String[] svSearchTarget = form.getSch100SvSearchTarget();
        if (svSearchTarget != null) {
            for (String target : svSearchTarget) {
                cmn999Form.addHiddenParam("sch100SvSearchTarget", target);
            }
        }
        //メイン画面用
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());
        cmn999Form.addHiddenParam("sch100SelectScdSid", form.getSch100SelectScdSid());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>削除処理実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteOk(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, Exception {

        ActionForward forward = null;
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        RequestModel reqMdl = getRequestModel(req);

        SchCommonBiz cbiz = new SchCommonBiz(con, reqMdl);
        SchAdmConfModel adminConf = cbiz.getAdmConfModel(con);

        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        int selectUserSid = NullDefault.getInt(form.getSch010SelectUsrSid(), -1);
        if (selectUserSid >= 0) {
            int sessionUserSid = getSessionUserSid(req);
            String selectUsrKbn = NullDefault.getString(form.getSch010SelectUsrKbn(), "");
            SchDao schDao = new SchDao(con);
            if (selectUsrKbn.equals(String.valueOf(GSConstSchedule.USER_KBN_GROUP))) {
                //グループスケジュール登録権限チェック
                if (!schDao.canRegistGroupSchedule(selectUserSid, sessionUserSid)) {
                    return getSubmitErrorPage(map, req);
                }
            } else {
                //ユーザスケジュール登録権限チェック
                if (!schDao.canRegistUserSchedule(selectUserSid, sessionUserSid)) {
                    return getSubmitErrorPage(map, req);
                }
            }
        }

        //編集権限チェック
        ActionErrors errors = form.validatePowerCheck(reqMdl, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            log__.debug("権限エラー");
            return __doInit(map, form, req, res, con);
        }
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        ScheduleSearchModel oldMdl = biz.getSchData(
                Integer.parseInt(form.getSch010SchSid()), adminConf, con);
        if (oldMdl == null) {
            return __doNoneDataError(map, form, req, res, con);
        }

        Sch041knBiz sch041knBiz = new Sch041knBiz(con, reqMdl);
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ScheduleSearchModel seachMdl =
                biz.getSchData(Integer.parseInt(form.getSch010SchSid()), adminConf, con);

        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);
        ArrayList<RsvSisDataModel> sisetuList = biz.getSisetuList(form.getSch010SchSid(), paramMdl);
        //施設名リスト
        ArrayList<String> sisNameList = new ArrayList<String>();
        //施設リストを元に施設名を取り出して、施設名リストに格納する
        if (sisetuList != null) {
            for (RsvSisDataModel rsvsisMdl : sisetuList) {
                sisNameList.add(rsvsisMdl.getRsdName());
            }
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            int scdSid = Integer.parseInt(form.getSch010SchSid());
            //管理者設定を取得
            ScheduleSearchDao ssDao = new ScheduleSearchDao(con);
            ArrayList<Integer> scds = ssDao.getScheduleUsrs(
                    Integer.parseInt(form.getSch010SchSid()),
                    sessionUsrSid,
                    adminConf.getSadCrange(),
                    GSConstSchedule.SSP_AUTHFILTER_EDIT
                    );
            ScheduleExSearchModel sceMdl = biz.getSchExData(scdSid, adminConf, con);
            log__.debug("削除処理実行");

            //ひも付いている施設予約情報が無くなった場合、予約拡張データを削除
            RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con);
            RsvSisRyrkModel ryrkMdl = ryrkDao.selectFromScdSid(scdSid);
            if (ryrkMdl != null) {
                int rsrRsid = ryrkMdl.getRsrRsid();
                RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
                if (rsrRsid > -1 && yrkDao.getYrkDataCnt(rsrRsid, scdSid) < 1) {
                    //件数取得し0件の場合
                    ryrkDao.delete(rsrRsid);
                    //施設予約拡張区分別情報削除
                    RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con);
                    kryrkDao.delete(rsrRsid);
                    //施設予約拡張公開情報削除
                    RsvExdataPubDao repDao = new RsvExdataPubDao(con);
                    repDao.delete(rsrRsid);
                }
            }

            //施設予約を削除
            if (form.getSch040ResBatchRef().equals("1")) {
                //同時登録施設予約があれば削除
                int cnt = biz.deleteReserve(scdSid, con, oldMdl);
                log__.debug("施設予約削除件数=>" + cnt);
            }

            if (form.getSch040BatchRef().equals("0") || scds.size() < 1) {
                //同時登録反映無しの場合
                cbiz.deleteSchedule(scdSid);
                cbiz.deletePushList(scdSid);
            } else if (form.getSch040BatchRef().equals("1")) {

                //同時登録ユーザへ反映更新
                if (scds.size() > 1) {
                    cbiz.deleteSchedule(scds);
                    cbiz.deletePushList(scds);
                } else {
                    cbiz.deleteSchedule(scdSid);
                    cbiz.deletePushList(scdSid);
                }
            }
            //ひも付いているスケジュール情報が無い拡張データを削除
            if (sceMdl != null) {
                int sceSid = sceMdl.getSceSid();
                if (ssDao.getExCount(sceSid) == 0) {
                    sch041knBiz.deleteScheduleEx(sceSid, con, sessionUsrSid);
                }
            }

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("スケジュール登録に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //開始日時
        String frDate = UDateUtil.getYymdJ(seachMdl.getScdFrDate(), reqMdl)
                + UDateUtil.getSeparateHMJ(seachMdl.getScdFrDate(), reqMdl);
        //終了日時
        String toDate = UDateUtil.getYymdJ(seachMdl.getScdToDate(), reqMdl)
                + UDateUtil.getSeparateHMJ(seachMdl.getScdToDate(), reqMdl);
        //グループが対象ユーザの場合、名を空欄指定
        String userMei = seachMdl.getScdUsrMei();
        if (seachMdl.getScdUsrMei() == null) {
            userMei = "";
        }

        //ログ出力処理
        String userName = seachMdl.getScdUsrSei() + userMei;
        if (form.getSch040BatchRef().equals(String.valueOf(GSConstSchedule.SAME_EDIT_ON))) {
            String[] users = form.getSv_users();
            if (users != null) {
                UserBiz userBiz = new UserBiz();
                ArrayList <CmnUsrmInfModel> selectUsrList = null;
                selectUsrList =
                        (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con, form.getSv_users());
                if (selectUsrList != null) {
                    for (int idx = 0; idx < selectUsrList.size(); idx++) {

                        userName += ", " + selectUsrList.get(idx).getUsiSei()
                                + " " + selectUsrList.get(idx).getUsiMei();
                    }
                }
            }
        }

        ArrayList<String> dateList = new ArrayList<String>();
        dateList.add(frDate);
        dateList.add(toDate);
        String outOpLog = biz.getOpLog(
                dateList, seachMdl.getScdTitle(), seachMdl.getScdValue(),
                userName, sisNameList, -1, 0, false);
        GsMessage gsMsg = new GsMessage(req);
        String delete = gsMsg.getMessage("cmn.delete");
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

        schBiz.outPutLog(
                map, req, res,
                delete, GSConstLog.LEVEL_TRACE, outOpLog);

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH040);

        forward = __doDeleteCompDsp(map, form, req, res, con);
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
    private ActionForward __doDeleteCompDsp(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        if (form.getListMod().equals(GSConstSchedule.DSP_MOD_LIST)) {
            urlForward = map.findForward("040_list");
        } else {
            if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("040_week");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MONTH)) {
                urlForward = map.findForward("040_month");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("040_day");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MAIN)) {
                urlForward = map.findForward("040_main");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
                urlForward = map.findForward("040_kojin");
            } else {
                urlForward = map.findForward("040_week");
            }
        }
        GsMessage gsMsg = new GsMessage();
        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                textSchedule));
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());

        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());
        cmn999Form.addHiddenParam("sch100SelectScdSid", form.getSch100SelectScdSid());

        //検索対象
        String[] searchTarget = form.getSch100SearchTarget();
        if (searchTarget != null) {
            for (String target : searchTarget) {
                cmn999Form.addHiddenParam("sch100SearchTarget", target);
            }
        }
        //検索対象
        String[] svSearchTarget = form.getSch100SvSearchTarget();
        if (svSearchTarget != null) {
            for (String target : svSearchTarget) {
                cmn999Form.addHiddenParam("sch100SvSearchTarget", target);
            }
        }
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
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
    private ActionForward __doBack(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;

//        String cmn = form.getCmd();
        String dspMod = form.getDspMod();
        String listMod = form.getListMod();
        if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
            forward = map.findForward("040_list");
            return forward;
        }
        if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
            forward = map.findForward("040_week");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
            forward = map.findForward("040_month");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
            forward = map.findForward("040_day");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MAIN)) {
            forward = map.findForward("040_main");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
            forward = map.findForward("040_kojin");
        }

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH040);

        return forward;
    }





    /**
     * 登録・更新完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        if (form.getListMod().equals(GSConstSchedule.DSP_MOD_LIST)) {
            urlForward = map.findForward("040_list");
        } else if (form.getSchIkkatsuViewMode() == 1) {
            if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("040_week_ikkatu_finish");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MONTH)) {
                urlForward = map.findForward("040_month_ikkatu_finish");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("040_day_ikkatu_finish");
            } else {
                urlForward = map.findForward("040_week_ikkatu_finish");
            }
        } else {

            if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("040_week");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MONTH)) {

                urlForward = map.findForward("040_month");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("040_day");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MAIN)) {
                urlForward = map.findForward("040_main");
            } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
                urlForward = map.findForward("040_kojin");
            } else {
                urlForward = map.findForward("040_week");
            }
        }
        GsMessage gsMsg = new GsMessage();
        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                textSchedule));

        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());
        cmn999Form.addHiddenParam("sch100SelectUsrSid", form.getSch100SelectUsrSid());
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());
        cmn999Form.addHiddenParam("schIkkatsuViewMode", form.getSchIkkatsuViewMode());
        //save
        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());
        cmn999Form.addHiddenParam("sch100SelectScdSid", form.getSch100SelectScdSid());

        //検索対象
        String[] searchTarget = form.getSch100SearchTarget();
        if (searchTarget != null) {
            for (String target : searchTarget) {
                cmn999Form.addHiddenParam("sch100SearchTarget", target);
            }
        }
        //検索対象
        String[] svSearchTarget = form.getSch100SvSearchTarget();
        if (svSearchTarget != null) {
            for (String target : svSearchTarget) {
                cmn999Form.addHiddenParam("sch100SvSearchTarget", target);
            }
        }
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>編集対象が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        return __doDataError(map, form, req, res, con, ERRORTYPE_NONE__);
    }

    /**
     * <br>スケジュールデータに関するエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param errorType エラー種別
     * @return ActionForward
     */
    private ActionForward __doDataError(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            int errorType) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        String listMod = NullDefault.getString(form.getListMod(), "");
        if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
            urlForward = map.findForward("040_list");
        } else {
            String dspMod = NullDefault.getString(form.getDspMod(), "");
            if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("040_week");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
                urlForward = map.findForward("040_month");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("040_day");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MAIN)) {
                urlForward = map.findForward("040_main");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
                urlForward = map.findForward("040_kojin");
            } else {
                urlForward = map.findForward("040_week");
            }
        }

        //エラーメッセージ
        if (errorType == ERRORTYPE_NONE__) {
            String textSchedule = gsMsg.getMessage(req, "schedule.108");
            String textChange = gsMsg.getMessage(req, "cmn.change");
            cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                    textSchedule, textChange));
        } else if (errorType == ERRORTYPE_VIEW__) {
            String textEdit = gsMsg.getMessage(req, "cmn.edit");
            cmn999Form.setMessage(msgRes.getMessage("error.edit.power.user",
                    textEdit, textEdit));
        } else if (errorType == ERRORTYPE_UNEXPECTED__) {
            urlForward = map.findForward("040_week");
            cmn999Form.setMessage(msgRes.getMessage("error.schedule.unexpected"));
            //エラーログ出力
            String cmd = NullDefault.getString(req.getParameter("CMD"), "");
            SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
            schBiz.outPutUnexpectedErrorLog(form.getDspMod(), cmd,
                    form.getSch010SelectUsrSid(), form.getSch010SelectUsrKbn(),
                    form.getSch010SelectDate(),
                    req);
        }

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100SelectUsrSid", form.getSch100SelectUsrSid());
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());

        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());
        cmn999Form.addHiddenParam("sch100SelectScdSid", form.getSch100SelectScdSid());

        //検索対象
        String[] searchTarget = form.getSch100SearchTarget();
        if (searchTarget != null) {
            for (String target : searchTarget) {
                cmn999Form.addHiddenParam("sch100SearchTarget", target);
            }
        }
        //検索対象
        String[] svSearchTarget = form.getSch100SvSearchTarget();
        if (svSearchTarget != null) {
            for (String target : svSearchTarget) {
                cmn999Form.addHiddenParam("sch100SvSearchTarget", target);
            }
        }
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>重複登録警告画面
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDupWarningCheck(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        RequestModel reqMdl = getRequestModel(req);

        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        int sessionUsrSid = getSessionUserSid(req);

        //重複登録 警告スケジュール一覧を取得する。
        int scdUsrKbn = GSConstSchedule.USER_KBN_USER;
        String selectUsrKbn = NullDefault.getString(form.getSch010SelectUsrKbn(), "");
        if (selectUsrKbn.equals(String.valueOf(GSConstSchedule.USER_KBN_GROUP))) {
            scdUsrKbn = GSConstSchedule.USER_KBN_GROUP;
        }
        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);
        List<SchDataModel> rptSchList
        = biz.getSchWarningList(
                paramMdl, sessionUsrSid, con, GSConstSchedule.SCH_REPEAT_KBN_WARNING,
                scdUsrKbn);
        paramMdl.setFormData(form);


        StringBuilder sbTextSchList = new StringBuilder();
        if (rptSchList != null && rptSchList.size() > 0) {
            int i = 0;
            String title = "";
            for (SchDataModel model : rptSchList) {
                if (i > 0) {
                    sbTextSchList.append("<br>");
                }

                title = schBiz.getDspTitle(model, sessionUsrSid);

                sbTextSchList.append("・");
                sbTextSchList.append(
                        StringUtilHtml.transToHTmlPlusAmparsant(model.getScdUserName()));
                sbTextSchList.append(" ");
                sbTextSchList.append(StringUtilHtml.transToHTmlPlusAmparsant(title));

                sbTextSchList.append("(");
                sbTextSchList.append(UDateUtil.getYymdJ(model.getScdFrDate(), req));
                sbTextSchList.append(UDateUtil.getSeparateHMJ(model.getScdFrDate(), req));
                sbTextSchList.append("～");
                sbTextSchList.append(UDateUtil.getYymdJ(model.getScdToDate(), req));
                sbTextSchList.append(UDateUtil.getSeparateHMJ(model.getScdToDate(), req));
                sbTextSchList.append(")");

                i++;
            }
        } else {
            //警告がなければNULLを返す
            return null;
        }

        return __doDupWarning(map, form, req, res, con, sbTextSchList.toString());

    }

    /**
     * <br>重複登録警告画面を出力します
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param msg 表示メッセージ
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDupWarning(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String msg) {

        ActionForward forward = null;
        // トランザクショントークン設定
        saveToken(req);
        GsMessage gsMsg = new GsMessage();

        //警告画面へ
        log__.debug("警告画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("040_warning_ok");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("040_warning_cancel");
        cmn999Form.setUrlCancel(urlForward.getPath());
        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");

        cmn999Form.setMessage(msgRes.getMessage("warning.input.dup.sch", textSchedule, msg));

        //週間・日間・月間
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        //選択
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch010SchSid", form.getSch010SchSid());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        //登録・修正画面
        cmn999Form.addHiddenParam("sch040Bgcolor", form.getSch040Bgcolor());
        cmn999Form.addHiddenParam("sch040Title", form.getSch040Title());
        cmn999Form.addHiddenParam("sch040Value", form.getSch040Value());
        cmn999Form.addHiddenParam("sch040Biko", form.getSch040Biko());
        cmn999Form.addHiddenParam("sch040Edit", form.getSch040Edit());
        cmn999Form.addHiddenParam("sch040Public", form.getSch040Public());
        cmn999Form.addHiddenParam("sch040FrYear", form.getSch040FrYear());
        cmn999Form.addHiddenParam("sch040FrMonth", form.getSch040FrMonth());
        cmn999Form.addHiddenParam("sch040FrDay", form.getSch040FrDay());
        cmn999Form.addHiddenParam("sch040FrHour", form.getSch040FrHour());
        cmn999Form.addHiddenParam("sch040FrMin", form.getSch040FrMin());
        cmn999Form.addHiddenParam("sch040ToYear", form.getSch040ToYear());
        cmn999Form.addHiddenParam("sch040ToMonth", form.getSch040ToMonth());
        cmn999Form.addHiddenParam("sch040ToDay", form.getSch040ToDay());
        cmn999Form.addHiddenParam("sch040ToHour", form.getSch040ToHour());
        cmn999Form.addHiddenParam("sch040ToMin", form.getSch040ToMin());
        cmn999Form.addHiddenParam("sch040FrDate", form.getSch040FrDate());
        cmn999Form.addHiddenParam("sch040ToDate", form.getSch040ToDate());
        cmn999Form.addHiddenParam("sch040FrTime", form.getSch040FrTime());
        cmn999Form.addHiddenParam("sch040ToTime", form.getSch040ToTime());
        cmn999Form.addHiddenParam("sch040TimeKbn", form.getSch040TimeKbn());
        cmn999Form.addHiddenParam("sch040GroupSid", form.getSch040GroupSid());
        cmn999Form.addHiddenParam("sch040BatchRef", form.getSch040BatchRef());
        cmn999Form.addHiddenParam("sch040ReserveGroupSid", form.getSch040ReserveGroupSid());
        cmn999Form.addHiddenParam("sch040ResBatchRef", form.getSch040ResBatchRef());
        cmn999Form.addHiddenParam("sch040TimeKbn", form.getSch040TimeKbn());

        cmn999Form.addHiddenParam("sch040AttendKbn", form.getSch040AttendKbn());
        cmn999Form.addHiddenParam("sch040AttendAnsKbn", form.getSch040AttendAnsKbn());
        cmn999Form.addHiddenParam("sch040AttendAnsComment", form.getSch040AttendAnsComment());
        cmn999Form.addHiddenParam("sch040EditDspMode", form.getSch040EditDspMode());
        cmn999Form.addHiddenParam("sch040EditMailSendKbn", form.getSch040EditMailSendKbn());
        cmn999Form.addHiddenParam("sch040InitFlg", form.getSch040InitFlg());
        cmn999Form.addHiddenParam("sch040DisplayTarget", form.getSch040DisplayTarget());
        cmn999Form.addHiddenParam("sch040DisplayTargetGroup", form.getSch040DisplayTargetGroup());

        cmn999Form.addHiddenParam("sch040ReminderTime", form.getSch040ReminderTime());
        cmn999Form.addHiddenParam("sch040TargetGroup", form.getSch040TargetGroup());

        cmn999Form.addHiddenParam("schIkkatuTorokuKey", form.getSchIkkatuTorokuKey());
        cmn999Form.addHiddenParam("schIkkatsuFlg", form.getSchIkkatsuFlg());

        //同時登録ユーザ
        String[] users = form.getSv_users();
        if (users != null) {
            for (String user : users) {
                cmn999Form.addHiddenParam("sv_users", user);
            }
        }
        //同時登録施設
        String[] reserves = form.getSvReserveUsers();
        if (reserves != null) {
            for (String reserve : reserves) {
                cmn999Form.addHiddenParam("svReserveUsers", reserve);
            }
        }
        cmn999Form.addHiddenParam("sch040contact", form.getSch040contact());
        cmn999Form.addHiddenParam("sch040CompanySid", form.getSch040CompanySid());
        cmn999Form.addHiddenParam("sch040CompanyBaseSid", form.getSch040CompanyBaseSid());
        cmn999Form.addHiddenParam("sch040AddressId", form.getSch040AddressId());

        //拡張登録画面
        cmn999Form.addHiddenParam("sch041ExtKbn", form.getSch041ExtKbn());
        cmn999Form.addHiddenParam("sch041Week", form.getSch041Week());
        cmn999Form.addHiddenParam("sch041Day", form.getSch041Day());
        cmn999Form.addHiddenParam("sch041MonthOfYearly", form.getSch041MonthOfYearly());
        cmn999Form.addHiddenParam("sch041DayOfYearly", form.getSch041DayOfYearly());
        cmn999Form.addHiddenParam("sch041ConfKbn", form.getSch041ConfKbn());
        cmn999Form.addHiddenParam("sch041WeekOrDay", form.getSch041WeekOrDay());
        cmn999Form.addHiddenParam("sch041TranKbn", form.getSch041TranKbn());
        cmn999Form.addHiddenParam("sch041FrYear", form.getSch041FrYear());
        cmn999Form.addHiddenParam("sch041FrMonth", form.getSch041FrMonth());
        cmn999Form.addHiddenParam("sch041FrDay", form.getSch041FrDay());
        cmn999Form.addHiddenParam("sch041ToYear", form.getSch041ToYear());
        cmn999Form.addHiddenParam("sch041ToMonth", form.getSch041ToMonth());
        cmn999Form.addHiddenParam("sch041ToDay", form.getSch041ToDay());
        cmn999Form.addHiddenParam("sch041FrHour", form.getSch041FrHour());
        cmn999Form.addHiddenParam("sch041FrMin", form.getSch041FrMin());
        cmn999Form.addHiddenParam("sch041ToHour", form.getSch041ToHour());
        cmn999Form.addHiddenParam("sch041ToMin", form.getSch041ToMin());
        cmn999Form.addHiddenParam("sch041FrDate", form.getSch041FrDate());
        cmn999Form.addHiddenParam("sch041ToDate", form.getSch041ToDate());
        cmn999Form.addHiddenParam("sch041FrTime", form.getSch041FrTime());
        cmn999Form.addHiddenParam("sch041ToTime", form.getSch041ToTime());
        cmn999Form.addHiddenParam("sch041Bgcolor", form.getSch041Bgcolor());
        cmn999Form.addHiddenParam("sch041Title", form.getSch041Title());
        cmn999Form.addHiddenParam("sch041Value", form.getSch041Value());
        cmn999Form.addHiddenParam("sch041Biko", form.getSch041Biko());
        cmn999Form.addHiddenParam("sch041Public", form.getSch041Public());
        cmn999Form.addHiddenParam("sch041Edit", form.getSch041Edit());
        cmn999Form.addHiddenParam("sch041BatchRef", form.getSch041BatchRef());
        cmn999Form.addHiddenParam("sch041GroupSid", form.getSch041GroupSid());
        cmn999Form.addHiddenParam("sch041InitFlg", form.getSch041InitFlg());
        cmn999Form.addHiddenParam("sch041DisplayTarget", form.getSch041DisplayTarget());
        cmn999Form.addHiddenParam("sch041TimeKbn", form.getSch041TimeKbn());

        cmn999Form.addHiddenParam("sch041ReminderTime", form.getSch041ReminderTime());
        cmn999Form.addHiddenParam("sch041TargetGroup", form.getSch041TargetGroup());
        //曜日
        String[] weeks = form.getSch041Dweek();
        if (weeks != null) {
            cmn999Form.addHiddenParam("sch041Dweek", weeks);
        }
        //同時登録ユーザ
        String[] exusers = form.getSch041SvUsers();
        if (exusers != null) {
            for (String exuser : exusers) {
                cmn999Form.addHiddenParam("sch041SvUsers", exuser);
            }
        }
        //同時登録施設
        String[] exreserves = form.getSch041SvReserve();
        if (exreserves != null) {
            for (String exreserve : exreserves) {
                cmn999Form.addHiddenParam("sch041SvReserve", exreserve);
            }
        }
        cmn999Form.addHiddenParam("sch041contact", form.getSch041contact());
        cmn999Form.addHiddenParam("sch041CompanySid", form.getSch041CompanySid());
        cmn999Form.addHiddenParam("sch041CompanyBaseSid", form.getSch041CompanyBaseSid());
        cmn999Form.addHiddenParam("sch041AddressId", form.getSch041AddressId());

        //検索対象
        String[] searchTarget = form.getSch100SearchTarget();
        if (searchTarget != null) {
            for (String target : searchTarget) {
                cmn999Form.addHiddenParam("sch100SearchTarget", target);
            }
        }
        //検索対象
        String[] svSearchTarget = form.getSch100SvSearchTarget();
        if (svSearchTarget != null) {
            for (String target : svSearchTarget) {
                cmn999Form.addHiddenParam("sch100SvSearchTarget", target);
            }
        }
        //メイン画面用
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100SelectUsrSid", form.getSch100SelectUsrSid());
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());
        cmn999Form.addHiddenParam("sch100SelectScdSid", form.getSch100SelectScdSid());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>一括登録時の重複登録警告画面
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDupWarningCheckIkkatsu(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        RequestModel reqMdl = getRequestModel(req);

        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        int sessionUsrSid = getSessionUserSid(req);
        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);

        StringBuilder sbTextSchList = new StringBuilder();
        int i = 0;
        for (String torokuKey : form.getSchIkkatuTorokuKey()) {
            String dateStr = torokuKey.substring(0, torokuKey.indexOf("-"));
            String sidStr = torokuKey.substring(torokuKey.indexOf("-") + 1);

            int selectUsrKbn = GSConstSchedule.USER_KBN_USER;
            if (sidStr.startsWith("G")) {
                sidStr = sidStr.replace("G", "");
                selectUsrKbn = GSConstSchedule.USER_KBN_GROUP;
            }
            UDate date = UDate.getInstanceStr(dateStr);
            paramMdl.setSch040FrDate(date.getDateString("/"));
            paramMdl.setSch040ToDate(date.getDateString("/"));
            paramMdl.setSch040FrYear(String.valueOf(date.getYear()));
            paramMdl.setSch040FrMonth(String.valueOf(date.getMonth()));
            paramMdl.setSch040FrDay(String.valueOf(date.getIntDay()));
            paramMdl.setSch040ToYear(String.valueOf(date.getYear()));
            paramMdl.setSch040ToMonth(String.valueOf(date.getMonth()));
            paramMdl.setSch040ToDay(String.valueOf(date.getIntDay()));
            paramMdl.setSch010SelectUsrSid(sidStr);
            List<SchDataModel> rptSchList
            = biz.getSchWarningList(
                    paramMdl, sessionUsrSid, con, GSConstSchedule.SCH_REPEAT_KBN_WARNING,
                    selectUsrKbn);

            if (rptSchList != null && rptSchList.size() > 0) {
                String title = "";
                for (SchDataModel model : rptSchList) {
                    if (i > 0) {
                        sbTextSchList.append("<br>");
                    }

                    title = schBiz.getDspTitle(model, sessionUsrSid);

                    sbTextSchList.append("・");
                    sbTextSchList.append(
                            StringUtilHtml.transToHTmlPlusAmparsant(model.getScdUserName()));
                    sbTextSchList.append(" ");
                    sbTextSchList.append(StringUtilHtml.transToHTmlPlusAmparsant(title));

                    sbTextSchList.append("(");
                    sbTextSchList.append(UDateUtil.getYymdJ(model.getScdFrDate(), req));
                    sbTextSchList.append(UDateUtil.getSeparateHMJ(model.getScdFrDate(), req));
                    sbTextSchList.append("～");
                    sbTextSchList.append(UDateUtil.getYymdJ(model.getScdToDate(), req));
                    sbTextSchList.append(UDateUtil.getSeparateHMJ(model.getScdToDate(), req));
                    sbTextSchList.append(")");
                    i++;
                }
            }
        }

        String msg = sbTextSchList.toString();
        if (!StringUtil.isNullZeroString(msg)) {
            return __doDupWarning(map, form, req, res, con, msg);
        } else {
            return null;
        }
    }

    /**
     * <br>[機  能] 「複写して登録」処理
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
    private ActionForward __doCopy(ActionMapping map,
            Sch040Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {
        form.setSch040CopyFlg(GSConstSchedule.COPY_FLG);
        return __doInit(map, form, req, res, con);

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
                                            Sch040Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
    throws Exception {

        //重複したパラメータを除外する
        String[] acoSidArray = form.getSch040CompanySid();
        if (acoSidArray != null) {
            String[] abaSidArray = form.getSch040CompanyBaseSid();
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

            form.setSch040CompanySid(acoSidArray);
            form.setSch040CompanyBaseSid(abaSidArray);
        }

        String[] addressIdArray = form.getSch040AddressId();
        if (addressIdArray != null) {
            List<String> addressIdList = new ArrayList<String>();

            for (int index = 0; index < addressIdArray.length; index++) {
                if (addressIdList.indexOf(addressIdArray[index]) < 0) {
                    addressIdList.add(addressIdArray[index]);
                }
            }

            form.setSch040AddressId(
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
                                            Sch040Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
                                            throws Exception {

        int delCompanyId = NullDefault.getInt(form.getSch040delCompanyId(), -1);
        int delCompanyBaseId = NullDefault.getInt(form.getSch040delCompanyBaseId(), -1);
        if (delCompanyId != -1 && delCompanyBaseId != -1) {

            //会社情報を設定
            RequestModel reqMdl = getRequestModel(req);
            Sch040Biz biz = new Sch040Biz(con, reqMdl);
            Sch040ParamModel paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid(), reqMdl);
            paramMdl.setFormData(form);


            List<Sch040CompanyModel> companyList = form.getSch040CompanyList();
            if (companyList != null && !companyList.isEmpty()) {
                List<String> companyIdList = new ArrayList<String>();
                List<String> companyBaseIdList = new ArrayList<String>();
                List<String> addressIdList = new ArrayList<String>();
                List<Sch040CompanyModel> newCompanyList = new ArrayList<Sch040CompanyModel>();

                for (Sch040CompanyModel companyData : companyList) {
                    if (companyData.getCompanySid() != delCompanyId
                    || companyData.getCompanyBaseSid() != delCompanyBaseId) {
                        companyIdList.add(String.valueOf(companyData.getCompanySid()));
                        companyBaseIdList.add(String.valueOf(companyData.getCompanyBaseSid()));
                        for (Sch040AddressModel addressMdl : companyData.getAddressDataList()) {
                            addressIdList.add(String.valueOf(addressMdl.getAdrSid()));
                        }
                        newCompanyList.add(companyData);
                    }
                }

                form.setSch040CompanySid(companyIdList.toArray(new String[companyIdList.size()]));
                form.setSch040CompanyBaseSid(
                        companyBaseIdList.toArray(new String[companyBaseIdList.size()]));
                form.setSch040AddressId(addressIdList.toArray(new String[addressIdList.size()]));
                form.setSch040CompanyList(newCompanyList);
            }
        }

        //初期表示処理
        __doInit(map, form, req, res, con);

        return map.getInputForward();
    }


    /**
     * <br>[機  能] PDFファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownLoadPdf(ActionMapping map,
            Sch040Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルダウンロード処理(スケジュール単票PDF)");

        RequestModel reqMdl = getRequestModel(req);

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID, TEMP_DIRECTORY_PDF);

        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        //PDF生成
        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);
        SchTanPdfModel pdfMdl =
                biz.createSchTanPdf(
                        paramMdl, appRootPath, outTempDir, pconfig, getSessionUserSid(req));
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID, TEMP_DIRECTORY_PDF);

        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        GsMessage gsMsg = new GsMessage();
        String downloadPdf = gsMsg.getMessage(req, "cmn.pdf");
        String logCode = "PDF出力：" + form.getSch010SchSid();
        schBiz.outPutLog(map, req, res, downloadPdf, GSConstLog.LEVEL_INFO, outBookName, logCode);

        return null;
    }


    /**
     * <br>出欠確認コメント反映確定ボタン押下時
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doEditComment(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        ActionForward forward = null;
        ActionErrors errors = form.validateCommentCheck(reqMdl, con);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        log__.debug("入力エラーなし");
        forward = __doCommitEditComment(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>出欠確認 回答者の処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommitEditComment(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        //セッション情報を取得
        RequestModel reqMdl = getRequestModel(req);
        int usrSid = reqMdl.getSmodel().getUsrsid();
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            Sch040ParamModel paramMdl = new Sch040ParamModel();
            paramMdl.setParam(form);
            biz.updateScheduleAttendComment(paramMdl, usrSid);
            paramMdl.setFormData(form);
            //ログ出力処理
            SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
            GsMessage gsMsg = new GsMessage();
            schBiz.outPutLog(
                    map,
                    req,
                    res,
                    gsMsg.getMessage(req, "schedule.sch040.3"),
                    GSConstLog.LEVEL_TRACE,
                    "[" + gsMsg.getMessage(req, "cmn.comment") + "]"
                        + form.getSch040AttendAnsComment());
            con.commit();
            commitFlg = true;
        } catch (Exception e) {
            log__.error("コメント登録に失敗しました" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            }
        }
        return __doInit(map, form, req, res, con);
    }
    /**
     * <br>出欠確認 コメントを取得する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __dogetAttendComment(ActionMapping map, Sch040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        //セッション情報を取得
        RequestModel reqMdl = getRequestModel(req);
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        Sch040ParamModel paramMdl = new Sch040ParamModel();
        paramMdl.setParam(form);

        // 権限チェック
        Sch010Biz sch010Biz = new Sch010Biz(reqMdl);
        if (!sch010Biz.isEditOk(Integer.parseInt(paramMdl.getSch010SchSid()), reqMdl, con, false)) {
            return;
        }

        String comment = biz.getAttendComment(paramMdl);
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("comment", comment);
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 添付ファイルダウンロード処理
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
            Sch040Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        //出欠確認回答モードのダウンロード処理
        if (String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER).equals(
                form.getSch040EditDspMode())) {

            if (!form._isExistFile(con)) {
                return getSubmitErrorPage(map, req);
            }
            long binSid = Long.parseLong(form.getSch040BinSid());
            String appRootPath = getAppRootPath();
            String domain = GroupSession.getResourceManager().getDomain(req);
            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbmMdl = cmnBiz.getBinInfo(con, binSid, domain);

            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(
                    req, res, cbmMdl, appRootPath, Encoding.UTF_8);

            GsMessage gsMsg = new GsMessage(req);
            SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));

            int scdSid = Integer.parseInt(form.getSch010SchSid());
            SchDataDao scdDao = new SchDataDao(con);
            SchDataModel scdMdl = scdDao.getSchData(scdSid);

            StringBuffer sb = new StringBuffer();
            sb.append("[" + gsMsg.getMessage("cmn.title") + "]");
            sb.append(scdMdl.getScdTitle());
            sb.append("\r\n");
            sb.append("[" + gsMsg.getMessage("cmn.file.name") + "]");
            sb.append(cbmMdl.getBinFileName());

            schBiz.outPutLog(
                    map, req, res, gsMsg.getMessage("cmn.download"),
                    GSConstLog.LEVEL_INFO, sb.toString(), String.valueOf(binSid));
            return null;
        }

        String fileId = form.getSch040BinSid();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        SchCommonBiz schBiz = new SchCommonBiz();
        String tempDir = schBiz.getTempDir(reqMdl, GSConstSchedule.SCR_ID_SCH040);

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] 添付削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doAttachDelete(
        ActionMapping map,
        Sch040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {


        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String[] binSid = new String[]{form.getSch040BinSid()};
        temp.deleteFile(binSid, getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH040);


       return __doInit(map, form, req, res, con);
    }

}