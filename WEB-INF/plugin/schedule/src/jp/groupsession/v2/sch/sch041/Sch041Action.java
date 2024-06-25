package jp.groupsession.v2.sch.sch041;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール繰り返し登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch041Action.class);

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

        log__.debug("START_SCH041");
        //更新処理が無いのでAutoCommitはtrue
        con.setAutoCommit(true);

        ActionForward forward = null;
        Sch041Form uform = (Sch041Form) form;

        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        int selectUserSid = NullDefault.getInt(uform.getSch010SelectUsrSid(), -1);
        if (selectUserSid >= 0) {
            int sessionUserSid = getSessionUserSid(req);
            String selectUsrKbn = NullDefault.getString(uform.getSch010SelectUsrKbn(), "");
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        uform.setSch040ScrollFlg("0");
        Sch041Biz biz = new Sch041Biz(getRequestModel(req));
        int schSid = NullDefault.getInt(uform.getSch010SchSid(), -1);
        log__.debug("ーーーーーーーーーーーーー");

        String cm = NullDefault.getString(uform.getCmd(), GSConstSchedule.CMD_ADD);
        if (cm.equals(GSConstSchedule.CMD_EDIT)
                && !biz.isExistData(schSid, con)) {
            log__.debug("データが存在しない");
            //データが存在しない
            return __doNoneDataError(map, uform, req, res, con);
        }
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("041_week")) {
            //週間スケジュール
            forward = map.findForward("041_week");
        } else if (cmd.equals("041_month")) {
            //月間スケジュール
            forward = map.findForward("041_month");
        } else if (cmd.equals("041_day")) {
            //日間スケジュール
            forward = map.findForward("041_day");
        } else if (cmd.equals("041_ok")) {
            //スケジュール拡張登録確認
            forward = __doOk(map, uform, req, res, con);
        } else if (cmd.equals("041_del")) {
            //削除確認画面
            forward = __doDelete(map, uform, req, res, con);
        } else if (cmd.equals("041_back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);
        } else if (cmd.equals("041_fileDownload")) {
            //ファイルダウンロード
            forward = __doDownLoad(map, uform, req, res, con);
        } else if (cmd.equals("041_fileDelete")) {
            //ファイル削除
            forward = __doAttachDelete(map, uform, req, res, con);
        } else if (cmd.equals("041_group")) {
            //グループコンボ変更
            __doInit(map, uform, req, res, con);
            uform.setSch040ScrollFlg("1");
            forward = map.getInputForward();
        } else if (cmd.equals("041_default")) {
            //「通常登録画面」
            log__.debug("「通常登録画面」");
            forward = __doDefault(map, uform, req, res, con);
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
        } else {
            //スケジュール登録
            log__.debug("初期表示");
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_SCH041");
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __doInit(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        RequestModel reqMdl = getRequestModel(req);

        if (NullDefault.getInt(
                form.getSch041InitFlg(),  GSConstSchedule.INIT_FLG) == GSConstSchedule.INIT_FLG) {
            //繰り返し登録画面 テンポラリディレクトリの初期化
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(
                    reqMdl, GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);
            temp.createTempDir(
                    reqMdl, GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);
        }

        Sch041Biz biz = new Sch041Biz(reqMdl);
        Sch041ParamModel paramMdl = new Sch041ParamModel();
        paramMdl.setParam(form);
        String appRootPath = getAppRootPath();
        String tempRoot = getTempPath(req);
        String domain = GroupSession.getResourceManager().getDomain(req);
        biz.getInitData(paramMdl, pconfig, appRootPath, tempRoot, domain, con);
        biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid(), reqMdl);
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        saveToken(req);
    }

    /**
     * 通常登録へ遷移します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doDefault(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;
        forward = map.findForward("041_default");

        return forward;
    }

    /**
     * <br>登録ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doOk(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        ActionForward forward = null;

        ScheduleSearchModel oldMdl = null;
        if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
            SchCommonBiz cBiz = new SchCommonBiz();
            SchAdmConfModel adminConf = cBiz.getAdmConfModel(con);
            Sch040Biz biz = new Sch040Biz(con, getRequestModel(req));
            oldMdl = biz.getSchData(
                Integer.parseInt(form.getSch010SchSid()),
                adminConf,
                con);
            if (oldMdl == null) {
                return __doNoneDataError(map, form, req, res, con);
            }
        }

        ActionErrors errors = form.validateCheck(getRequestModel(req), con, oldMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }

        log__.debug("入力エラーなし");

        forward = __doKakunin(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>スケジュール拡張登録確認画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;
        forward = map.findForward("041_ok");
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        ActionForward forward = null;

        //編集権限チェック
        ActionErrors errors = form.validateExPowerCheck(getRequestModel(req), con);

        if (errors.size() > 0) {
            log__.debug("権限エラー");
            addErrors(req, errors);
            form.setCmd("edit");
            __doInit(map, form, req, res, con);

            return map.getInputForward();
        }

        // トランザクショントークン設定
        this.saveToken(req);
        forward = map.findForward("041_ok");
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
    private ActionForward __doBack(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;

//        String cmn = form.getCmd();
        String dspMod = form.getDspMod();
        String listMod = form.getListMod();
        if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
            forward = map.findForward("041_list");
            return forward;
        }
        if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
            forward = map.findForward("041_week");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
            forward = map.findForward("041_month");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
            forward = map.findForward("041_day");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MAIN)) {
            forward = map.findForward("041_main");
        } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
            forward = map.findForward("041_kojin");
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
     * <br>登録・更新完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(ActionMapping map, Sch041Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
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
        String dspMod = form.getDspMod();
        if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
            urlForward = map.findForward("041_list");
        } else {
            if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("041_week");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
                urlForward = map.findForward("041_month");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("041_day");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MAIN)) {
                urlForward = map.findForward("041_main");
            }
        }
        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");
        //変更
        String textChange = gsMsg.getMessage(req, "cmn.change");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textSchedule, textChange));
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
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
        cmn999Form.addHiddenParam("sch100SvSortKey2", form.getSch100SvSortKey2());
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
            Sch041Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        __doInit(map, form, req, res, con);
        return map.getInputForward();
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
            Sch041Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        //重複したパラメータを除外する
        String[] acoSidArray = form.getSch041CompanySid();
        if (acoSidArray != null) {
            String[] abaSidArray = form.getSch041CompanyBaseSid();
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

            form.setSch041CompanySid(acoSidArray);
            form.setSch041CompanyBaseSid(abaSidArray);
        }

        String[] addressIdArray = form.getSch041AddressId();
        if (addressIdArray != null) {
            List<String> addressIdList = new ArrayList<String>();

            for (int index = 0; index < addressIdArray.length; index++) {
                if (addressIdList.indexOf(addressIdArray[index]) < 0) {
                    addressIdList.add(addressIdArray[index]);
                }
            }

            form.setSch041AddressId(
                    addressIdList.toArray(new String[addressIdList.size()]));
        }
        //初期表示処理
        __doInit(map, form, req, res, con);

        return map.getInputForward();
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
            Sch041Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {

        int delCompanyId = NullDefault.getInt(form.getSch041delCompanyId(), -1);
        int delCompanyBaseId = NullDefault.getInt(form.getSch041delCompanyBaseId(), -1);

        if (delCompanyId != -1 && delCompanyBaseId != -1) {

            //会社情報を設定
            RequestModel reqMdl = getRequestModel(req);
            Sch041Biz biz = new Sch041Biz(reqMdl);

            Sch041ParamModel paramMdl = new Sch041ParamModel();
            paramMdl.setParam(form);
            biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid(), reqMdl);
            paramMdl.setFormData(form);

            List<Sch040CompanyModel> companyList = form.getSch041CompanyList();
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

                form.setSch041CompanySid(companyIdList.toArray(new String[companyIdList.size()]));
                form.setSch041CompanyBaseSid(
                        companyBaseIdList.toArray(new String[companyBaseIdList.size()]));
                form.setSch041AddressId(addressIdList.toArray(new String[addressIdList.size()]));
                form.setSch041CompanyList(newCompanyList);
            }
        }

        //初期表示処理
        __doInit(map, form, req, res, con);

        return map.getInputForward();
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
            Sch041Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getSch041BinSid();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        SchCommonBiz schBiz = new SchCommonBiz();
        String tempDir = schBiz.getTempDir(reqMdl, GSConstSchedule.SCR_ID_SCH041);

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
        Sch041Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String[] binSid = new String[]{form.getSch041BinSid()};
        temp.deleteFile(binSid, getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);

        __doInit(map, form, req, res, con);

        return map.getInputForward();
    }
}