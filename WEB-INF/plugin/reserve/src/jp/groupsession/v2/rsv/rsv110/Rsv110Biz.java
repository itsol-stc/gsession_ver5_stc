package jp.groupsession.v2.rsv.rsv110;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.biz.SchRemindConfWriter;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvInitPubDao;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.ReserveSmlModel;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvInitPubModel;
import jp.groupsession.v2.rsv.model.RsvScdOperationModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisKryrkModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.RsvSchDataPubModel;
import jp.groupsession.v2.rsv.model.other.ScheduleRsvModel;
import jp.groupsession.v2.rsv.pdf.RsvTanPdfModel;
import jp.groupsession.v2.rsv.pdf.RsvTanPdfUtil;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.model.SchBinModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 施設予約登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv110Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv110Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param pconfig プラグイン情報
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void setInitData(Rsv110ParamModel paramMdl,
            PluginConfig pconfig, int userSid, String appRootPath)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //印刷区分使用フラグの設定
        paramMdl.setRsvPrintUseKbn(RsvCommonBiz.getPrintUseKbn(appRootPath));

        String procMode = paramMdl.getRsv110ProcMode();

        RsvCommonBiz biz = new RsvCommonBiz();
        // 管理者設定の取得
        RsvAdmConfDao admConfDao = new RsvAdmConfDao(con_);
        RsvAdmConfModel admConfModel = admConfDao.select();
        if (admConfModel == null) {

            admConfModel = biz.setInitAdminnConfModel();
        }
        // 個人設定の取得
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();
        RsvUserDao uConfDao = new RsvUserDao(con_);
        RsvUserModel uConfMdl = uConfDao.select(usrSid);
        if (uConfMdl == null) {
            uConfMdl = biz.getUnconfInitModel();
        }

        //新規モード
        if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)) {
            log__.debug("新規モード");
            __setSinkiData(paramMdl, admConfModel, uConfMdl, usrSid);
            //編集モード or 複写して登録モード
        } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)
                || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
            log__.debug("編集モード or 複写して登録モード");
            __setEditData(paramMdl, pconfig, userSid, admConfModel, uConfMdl);
            //ポップアップモード
        } else if (procMode.equals(GSConstReserve.PROC_MODE_POPUP)) {
            log__.debug("ポップアップ表示モード");
            __setPopUpData(reqMdl_, paramMdl, pconfig, userSid);
        }


        //午前
        paramMdl.setRsv110AmFrHour(admConfModel.getRacAmTimeFrH());
        paramMdl.setRsv110AmFrMin(admConfModel.getRacAmTimeFrM());
        paramMdl.setRsv110AmToHour(admConfModel.getRacAmTimeToH());
        paramMdl.setRsv110AmToMin(admConfModel.getRacAmTimeToM());

        //午後
        paramMdl.setRsv110PmFrHour(admConfModel.getRacPmTimeFrH());
        paramMdl.setRsv110PmFrMin(admConfModel.getRacPmTimeFrM());
        paramMdl.setRsv110PmToHour(admConfModel.getRacPmTimeToH());
        paramMdl.setRsv110PmToMin(admConfModel.getRacPmTimeToM());

        //終日
        paramMdl.setRsv110AllDayFrHour(admConfModel.getRacAllDayTimeFrH());
        paramMdl.setRsv110AllDayFrMin(admConfModel.getRacAllDayTimeFrM());
        paramMdl.setRsv110AllDayToHour(admConfModel.getRacAllDayTimeToH());
        paramMdl.setRsv110AllDayToMin(admConfModel.getRacAllDayTimeToM());

        //初期表示フラグOFF
        paramMdl.setRsv110InitFlg(false);

        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setRsv110PubUsrGrpList(
                cmnBiz.getUserLabelList(con_, paramMdl.getRsv110PubUsrGrpSid()));

        //時間表示間隔を設定
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        paramMdl.setRacHourDiv(rsvBiz.getHourDivision(con_));

        //スケジュール使用有無
        if (pconfig.getPlugin(GSConstReserve.PLUGIN_ID_SCHEDULE) != null) {
            paramMdl.setSchedulePluginKbn(GSConst.PLUGIN_USE);
            log__.debug("スケジュール使用");

            //スケジュール管理者設定 共有範囲を取得する
            SchDao schDao = new SchDao(con_);
            paramMdl.setRsv110SchCrangeKbn(schDao.getSadCrange());
        } else {
            paramMdl.setSchedulePluginKbn(GSConst.PLUGIN_NOT_USE);
            log__.debug("スケジュール使用不可");
        }
    }

    /**
     * <br>[機  能] 新規モード画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param admConf 管理者設定
     * @param uConf 個人設定
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void __setSinkiData(Rsv110ParamModel paramMdl,
            RsvAdmConfModel admConf, RsvUserModel uConf, int usrSid)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        boolean initFlg = paramMdl.isRsv110InitFlg();

        //新規登録者名をセット
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        paramMdl.setRsv110Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

        //年コンボ設定用 開始年
        String dspDate = paramMdl.getRsv110SinkiDefaultDate();
        UDate frUd = new UDate();
        frUd.setDate(dspDate);
        frUd.setHour(GSConstReserve.YRK_DEFAULT_START_HOUR);
        frUd.setMinute(GSConstReserve.YRK_DEFAULT_START_MINUTE);

        //年コンボ設定用 終了年
        UDate toUd = new UDate();
        toUd.setDate(dspDate);
        toUd.setHour(GSConstReserve.YRK_DEFAULT_END_HOUR);
        toUd.setMinute(GSConstReserve.YRK_DEFAULT_END_MINUTE);

        //登録日付の初期化
        paramMdl.setRsv110AddDate(null);

        if (initFlg) {
            //初期表示時は各項目の選択値を設定
            __setComboSelectValueInit(paramMdl, frUd, toUd, admConf, uConf, usrSid);
        }

        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String frDateNameJp = gsMsg.getMessage("reserve.157");
        String toDateNameJp = gsMsg.getMessage("reserve.158");

        //日付時間設定
        picker.setDateParam(
                paramMdl, 
                "rsv110SelectedDateFr",
                "rsv110SelectedYearFr",
                "rsv110SelectedMonthFr",
                "rsv110SelectedDayFr",
                frDateNameJp);
        picker.setTimeParam(
                paramMdl,
                "rsv110SelectedTimeFr",
                "rsv110SelectedHourFr",
                "rsv110SelectedMinuteFr",
                frDateNameJp);
        picker.setDateParam(
                paramMdl, 
                "rsv110SelectedDateTo",
                "rsv110SelectedYearTo",
                "rsv110SelectedMonthTo",
                "rsv110SelectedDayTo",
                toDateNameJp);
        picker.setTimeParam(
                paramMdl,
                "rsv110SelectedTimeTo",
                "rsv110SelectedHourTo",
                "rsv110SelectedMinuteTo",
                toDateNameJp);

        //使用者 グループコンボ、ユーザコンボを設定
        _setUserCombo(con_, paramMdl, reqMdl_);

        //施設グループ情報を取得
        Rsv070Model grpMdl = __getGroupData(paramMdl.getRsv110RsdSid());
        int rskSid = 0;
        if (grpMdl != null) {
            rskSid = grpMdl.getRskSid();

            //施設区分毎に入力可能な項目を設定
            __setSisetuHeader(paramMdl, rskSid);

            //施設グループ情報セット
            __setGroupData(paramMdl, grpMdl);

            if (initFlg) {
                if (RsvCommonBiz.isRskKbnRegCheck(rskSid)) {
                    __setInitSisYrkData(paramMdl, usrMdl, rskSid);
                }
            }
        }

        //同時登録ユーザリストセット
        __setUserList(paramMdl, null);

        //初回表示時のみ繰り返し登録情報をセット
        if (paramMdl.isRsv111InitFlg()) {
            __setKakutyoData(paramMdl, paramMdl.getRsv110SinkiDefaultDate(),
                    null, null, usrMdl, rskSid, admConf, uConf);
        }

        String timeNameJp = gsMsg.getMessage("reserve.159");
        //日付時間設定
        picker.setDateParam(
                paramMdl, 
                "rsv111RsrDateFr",
                "rsv111RsrDateYearFr",
                "rsv111RsrDateMonthFr",
                "rsv111RsrDateDayFr",
                frDateNameJp);
        picker.setDateParam(
                paramMdl, 
                "rsv111RsrDateTo",
                "rsv111RsrDateYearTo",
                "rsv111RsrDateMonthTo",
                "rsv111RsrDateDayTo",
                toDateNameJp);
        picker.setTimeParam(
                paramMdl,
                "rsv111RsrTimeFr",
                "rsv111RsrTimeHourFr",
                "rsv111RsrTimeMinuteFr",
                timeNameJp);
        picker.setTimeParam(
                paramMdl,
                "rsv111RsrTimeTo",
                "rsv111RsrTimeHourTo",
                "rsv111RsrTimeMinuteTo",
                timeNameJp);

    }

    /**
     * <br>[機  能] 編集モード画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param pconfig プラグイン情報
     * @param userSid セッションユーザSID
     * @param admConf 管理者設定
     * @param uConf 個人設定
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void __setEditData(Rsv110ParamModel paramMdl, PluginConfig pconfig,
            int userSid, RsvAdmConfModel admConf, RsvUserModel uConf)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        boolean initFlg = paramMdl.isRsv110InitFlg();
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);

        //予約情報取得
        Rsv110SisetuModel yrkEditMdl = getYoyakuEditData(paramMdl);
        Rsv110SisetuModel yrkAddMdl = getYoyakuAddData(paramMdl);
        if (yrkEditMdl != null) {
            paramMdl.setRsvDataFlg(true);

            //複写して登録
            if (paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
                //新規登録者名をセット
                paramMdl.setRsv110Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

                //編集
            } else {
                //新規登録者名および最終更新者名をセット
                __setDspUsr(paramMdl, yrkEditMdl, GSConstReserve.DSP_USR_EDIT);
                __setDspUsr(paramMdl, yrkAddMdl, GSConstReserve.DSP_USR_ADD);

                //施設SIDを画面に設定
                paramMdl.setRsvSelectedSisetuSid(yrkEditMdl.getRsdSid());
            }

            if (paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
                //登録日付および更新日付をセット
                __setDspDate(paramMdl, yrkEditMdl, GSConstReserve.DSP_USR_ADD);
                __setDspDate(paramMdl, yrkEditMdl, GSConstReserve.DSP_USR_EDIT);
            }

            //施設グループ情報を取得
            Rsv070Model grpMdl = __getGroupData(yrkEditMdl.getRsdSid());
            int rskSid = 0;
            if (grpMdl != null) {
                rskSid = grpMdl.getRskSid();

                //施設区分毎に入力可能な項目を設定
                __setSisetuHeader(paramMdl, rskSid);

                //施設グループ情報セット
                __setGroupData(paramMdl, grpMdl);
            }

            //予約データ修正可否の判定
            __setEditAuth(paramMdl, yrkEditMdl, grpMdl, paramMdl.getRsv110RsySid());

            if (initFlg) {

                //取得値をセット
                __setYoyakuData(paramMdl, yrkEditMdl, pconfig, userSid);

                __setKbnYoyakuData(paramMdl, yrkEditMdl, rskSid);


                //初期表示時はコンボの選択値を設定
                __setComboSelectValueEdit(
                        paramMdl,
                        yrkEditMdl.getRsyFrDate(),
                        yrkEditMdl.getRsyToDate());
            }

            GsMessage gsMsg = new GsMessage();
            DateTimePickerBiz picker = new DateTimePickerBiz();
            String frDateNameJp = gsMsg.getMessage("reserve.157");
            String toDateNameJp = gsMsg.getMessage("reserve.158");

            //日付時間設定
            picker.setDateParam(
                    paramMdl, 
                    "rsv110SelectedDateFr",
                    "rsv110SelectedYearFr",
                    "rsv110SelectedMonthFr",
                    "rsv110SelectedDayFr",
                    frDateNameJp);
            picker.setTimeParam(
                    paramMdl,
                    "rsv110SelectedTimeFr",
                    "rsv110SelectedHourFr",
                    "rsv110SelectedMinuteFr",
                    frDateNameJp);
            picker.setDateParam(
                    paramMdl, 
                    "rsv110SelectedDateTo",
                    "rsv110SelectedYearTo",
                    "rsv110SelectedMonthTo",
                    "rsv110SelectedDayTo",
                    toDateNameJp);
            picker.setTimeParam(
                    paramMdl,
                    "rsv110SelectedTimeTo",
                    "rsv110SelectedHourTo",
                    "rsv110SelectedMinuteTo",
                    toDateNameJp);


            //使用者 グループコンボ、ユーザコンボを設定
            _setUserCombo(con_, paramMdl, reqMdl_);

            //同時登録ユーザリストセット
            __setUserList(paramMdl, yrkEditMdl);

            //初回表示時のみ繰り返し登録情報をセット
            if (paramMdl.isRsv111InitFlg()) {
                RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con_);
                RsvSisRyrkModel ryrkMdl = ryrkDao.select(yrkEditMdl.getRsrRsid());
                __setKakutyoData(
                        paramMdl, yrkEditMdl.getRsyFrDate().getDateString(),
                        ryrkMdl, yrkEditMdl, usrMdl, rskSid, admConf, uConf);
            }

            String timeNameJp = gsMsg.getMessage("reserve.159");
            //日付時間設定
            picker.setDateParam(
                    paramMdl, 
                    "rsv111RsrDateFr",
                    "rsv111RsrDateYearFr",
                    "rsv111RsrDateMonthFr",
                    "rsv111RsrDateDayFr",
                    frDateNameJp);
            picker.setDateParam(
                    paramMdl, 
                    "rsv111RsrDateTo",
                    "rsv111RsrDateYearTo",
                    "rsv111RsrDateMonthTo",
                    "rsv111RsrDateDayTo",
                    toDateNameJp);
            picker.setTimeParam(
                    paramMdl,
                    "rsv111RsrTimeFr",
                    "rsv111RsrTimeHourFr",
                    "rsv111RsrTimeMinuteFr",
                    timeNameJp);
            picker.setTimeParam(
                    paramMdl,
                    "rsv111RsrTimeTo",
                    "rsv111RsrTimeHourTo",
                    "rsv111RsrTimeMinuteTo",
                    timeNameJp);

              //関連するスケジュールデータ存在チェック
            __existSchData(paramMdl);

        }
    }

    /**
     * <br>[機  能] 施設予約データに対応するスケジュールデータが存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @throws SQLException SQL実行時例外
     */
    private void __existSchData(Rsv110ParamModel paramMdl)
            throws SQLException {

        //施設予約情報取得
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        RsvSisYrkModel selParam = new RsvSisYrkModel();
        selParam.setRsySid(paramMdl.getRsv110RsySid());
        RsvSisYrkModel yrkRet = yrkDao.select(selParam);

        if (yrkRet == null) {
            //施設予約情報が無い
            paramMdl.setRsv110ExistSchDateFlg(false);
            //スケジュールデータへ反映させない
            paramMdl.setRsv110ScdReflection(GSConstReserve.SCD_REFLECTION_NO);
            return;
        }

        RsvScdOperationDao scdDao = new RsvScdOperationDao(con_);
        ArrayList<RsvScdOperationModel> scdRet = null;
        if (yrkRet.getScdRsSid() < 0) {
            //関連付いたスケジュールが無い
            paramMdl.setRsv110ExistSchDateFlg(false);
            //スケジュールデータへ反映させない
            paramMdl.setRsv110ScdReflection(GSConstReserve.SCD_REFLECTION_NO);
            return;
        }
        scdRet = scdDao.selectSchList(yrkRet.getScdRsSid());

        if (scdRet.isEmpty()) {
            //関連付いたスケジュールが無い
            paramMdl.setRsv110ExistSchDateFlg(false);
            //スケジュールデータへ反映させない
            paramMdl.setRsv110ScdReflection(GSConstReserve.SCD_REFLECTION_NO);
            return;
        }
        paramMdl.setRsv110ExistSchDateFlg(true);
    }

    /**
     * <br>[機  能] ポップアップモード画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl フォーム
     * @param pconfig プラグイン情報
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void __setPopUpData(
            RequestModel reqMdl,  Rsv110ParamModel paramMdl,
            PluginConfig pconfig, int userSid)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //予約情報取得
        Rsv110SisetuModel yrkAddMdl = getYoyakuAddData(paramMdl);
        Rsv110SisetuModel yrkEditMdl = getYoyakuEditData(paramMdl);
        if (yrkEditMdl != null) {

            //施設SIDを画面に設定
            paramMdl.setRsvSelectedSisetuSid(yrkEditMdl.getRsdSid());

            __setComboSelectValueEdit(
                    paramMdl,
                    yrkEditMdl.getRsyFrDate(),
                    yrkEditMdl.getRsyToDate());

            GsMessage gsMsg = new GsMessage();
            DateTimePickerBiz picker = new DateTimePickerBiz();
            String frDateNameJp = gsMsg.getMessage("reserve.157");
            String toDateNameJp = gsMsg.getMessage("reserve.158");

            //日付時間設定
            picker.setDateParam(
                    paramMdl, 
                    "rsv110SelectedDateFr",
                    "rsv110SelectedYearFr",
                    "rsv110SelectedMonthFr",
                    "rsv110SelectedDayFr",
                    frDateNameJp);
            picker.setTimeParam(
                    paramMdl,
                    "rsv110SelectedTimeFr",
                    "rsv110SelectedHourFr",
                    "rsv110SelectedMinuteFr",
                    frDateNameJp);
            picker.setDateParam(
                    paramMdl, 
                    "rsv110SelectedDateTo",
                    "rsv110SelectedYearTo",
                    "rsv110SelectedMonthTo",
                    "rsv110SelectedDayTo",
                    toDateNameJp);
            picker.setTimeParam(
                    paramMdl,
                    "rsv110SelectedTimeTo",
                    "rsv110SelectedHourTo",
                    "rsv110SelectedMinuteTo",
                    toDateNameJp);

            //新規登録者名および更新者をセット
            __setDspUsr(paramMdl, yrkAddMdl, GSConstReserve.DSP_USR_ADD);
            __setDspUsr(paramMdl, yrkEditMdl, GSConstReserve.DSP_USR_EDIT);

            //取得値をセット
            __setYoyakuData(paramMdl, yrkEditMdl, pconfig, userSid);

            //施設グループ情報を取得
            Rsv070Model grpMdl = __getGroupData(yrkEditMdl.getRsdSid());
            if (grpMdl != null) {
                int rskSid = grpMdl.getRskSid();

                __setKbnYoyakuData(paramMdl, yrkEditMdl, rskSid);

                //施設区分毎に表示可能な項目を設定
                __setSisetuHeader(paramMdl, rskSid);

                //施設グループ情報セット
                __setGroupData(paramMdl, grpMdl);
            }

            //同時登録ユーザリストセット
            __setUserList(paramMdl, yrkEditMdl);
        }
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    public Rsv110SisetuModel getYoyakuEditData(Rsv110ParamModel paramMdl) throws SQLException {
        return __getYoyakuEditData(paramMdl.getRsv110RsySid());
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 予約SID
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    private Rsv110SisetuModel __getYoyakuEditData(int rsySid) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuEditData(rsySid);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    public Rsv110SisetuModel getYoyakuAddData(Rsv110ParamModel paramMdl) throws SQLException {
        return __getYoyakuAddData(paramMdl.getRsv110RsySid());
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 予約SID
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    private Rsv110SisetuModel __getYoyakuAddData(int rsySid) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuAddData(rsySid);

        return ret;
    }

    /**
     * <br>[機  能] 施設グループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsdSid 施設SID
     * @return ret 取得結果
     * @throws SQLException SQL実行時例外
     */
    private Rsv070Model __getGroupData(int rsdSid) throws SQLException {

        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        Rsv070Model ret = dataDao.getPopUpSisetuData(rsdSid);

        return ret;
    }

    /**
     * <br>[機  能] 施設グループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsdSid 施設SID
     * @return ret 取得結果
     * @throws SQLException SQL実行時例外
     */
    public Rsv210Model getGroupData(int rsdSid) throws SQLException {

        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        Rsv210Model ret = dataDao.getGroupCheckModel(rsdSid);

        return ret;
    }

    /**
     * <br>[機  能] 施設予約情報削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @throws SQLException SQL実行時例外
     */
    public void doYoyakuDelete(Rsv110ParamModel paramMdl)
    throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con_, reqMdl_);

        //スケジュールと関連付いている & 「スケジュールへ反映」が選択されている
        if (paramMdl.getRsv110ScdRsSid() > 0
                && paramMdl.getRsv110ScdReflection() == GSConstReserve.SCD_REFLECTION_OK) {

            //関連付いているスケジュールを削除
            RsvSisYrkModel selParam = new RsvSisYrkModel();
            selParam.setRsySid(paramMdl.getRsv110RsySid());
            RsvSisYrkModel ret = yrkDao.select(selParam);
            if (ret != null) {

                //スケジュールと関連付いている施設予約が他に存在する場合、
                //スケジュールの削除を行なわない
                int scdRssid = ret.getScdRsSid();
                if (yrkDao.getSisYrkCountFromScdRs(scdRssid) <= 1) {
                    List<Integer> scdSidList = new ArrayList<Integer>();

                    //スケジュール拡張SID取得
                    RsvScdOperationDao scdDao = new RsvScdOperationDao(con_);
                    int sceSid = scdDao.selectSceSid(scdRssid);

                    //スケジュールデータ一覧を取得
                    RsvScdOperationDao opDao = new RsvScdOperationDao(con_);
                    ArrayList<RsvScdOperationModel> schDataList =
                        opDao.selectSchList(scdRssid);

                    //スケジュールアクセス不可グループ or ユーザを取得
                    int sessionUserSid = reqMdl_.getSmodel().getUsrsid();
                    SchDao schDao = new SchDao(con_);
                    List<Integer> notAccessUserList = new ArrayList<Integer>();
                    List<Integer> notAccessGroupList = new ArrayList<Integer>();
                    notAccessGroupList = schDao.getNotRegistGrpList(sessionUserSid);
                    notAccessUserList = schDao.getNotRegistUserList(sessionUserSid);

                    //スケジュールを削除
                    boolean groupFlg = false;
                    List<String> delUsers = new ArrayList<String>();
                    for (RsvScdOperationModel mdl : schDataList) {
                        List<Integer> notAccessList = null;
                        if (mdl.getScdUsrKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                            notAccessList = notAccessGroupList;
                            groupFlg = true;
                        } else {
                            notAccessList = notAccessUserList;
                        }
                        //アクセス不可グループ or ユーザ を削除対象から除外する
                        if (notAccessList.indexOf(mdl.getScdUsrSid()) < 0) {
                            delUsers.add(String.valueOf(mdl.getScdUsrSid()));
                            scdSidList.add(mdl.getScdSid());
                        }
                    }

                    //スケジュール削除
                    opDao.deleteScdTiWithUsers(
                            scdRssid, delUsers.toArray(new String[delUsers.size()]));
                    SchBinDao schBinDao = new SchBinDao(con_);
                    schBinDao.deleteTempFile(scdSidList);

                    for (int scdSid : scdSidList) {
                        //スケジュール公開対象情報を削除
                        opDao.deleteSdpData(scdSid);
                    }

                    if (groupFlg) {
                        for (int scdSid : scdSidList) {
                            schCmnBiz.deletePushList(scdSid);
                        }
                    } else {
                        schCmnBiz.deleteUserPushList(
                                scdSidList, delUsers.toArray(new String[delUsers.size()]));
                    }

                    //スケジュールが繰り返し登録されているデータ
                    if (sceSid != -1) {
                        //スケジュール拡張に紐付くスケジュールが無くなった
                        if (scdDao.selectExDataCnt(sceSid) == 0) {
                            //スケジュール拡張データ削除
                            scdDao.deleteExData(sceSid);
                            //スケジュール拡張公開対象
                            scdDao.deleteExPubData(sceSid);
                            //スケジュール拡張_添付ファイル情報を削除
                            SchExdataBinDao exBinDao = new SchExdataBinDao(con_);
                            exBinDao.deleteTempFile(Set.of(sceSid));
                        }
                    }
                }
            }
        }

        //公開対象情報を削除
        RsvDataPubDao rdpDao = new RsvDataPubDao(con_);
        rdpDao.delete(paramMdl.getRsv110RsySid());
        //施設予約データを削除
        RsvSisYrkModel delParam = new RsvSisYrkModel();
        delParam.setRsySid(paramMdl.getRsv110RsySid());
        yrkDao.delete(delParam);
        kyrkDao.delete(paramMdl.getRsv110RsySid());
    }

    /**
     * <br>[機  能] 拡張予約データをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dspDate 処理日付(yyyyMMdd)
     * @param bean 拡張予約モデル
     * @param yrkMdl 施設予約情報
     * @param usrMdl ユーザモデル
     * @param rskSid 施設区分
     * @param admConf 管理者設定
     * @param uConf 個人設定
     * @throws SQLException SQL実行時例外
     */
    private void __setKakutyoData(Rsv110ParamModel paramMdl,
            String dspDate,
            RsvSisRyrkModel bean,
            Rsv110SisetuModel yrkMdl,
            BaseUserModel usrMdl,
            int rskSid,
            RsvAdmConfModel admConf,
            RsvUserModel uConf)
                    throws SQLException {
        //コンボ設定用 表示日
        UDate selDate = new UDate();
        selDate.setDate(dspDate);
        //毎年 月
        paramMdl.setRsv111RsrMonthOfYearly(selDate.getMonth());
        //毎年 日
        paramMdl.setRsv111RsrDayOfYearly(selDate.getIntDay());

        if (bean == null) {

            //施設予約拡張SID
            paramMdl.setRsv111RsrRsid(-1);
            //拡張区分
            paramMdl.setRsv111RsrKbn(GSConstReserve.KAKUTYO_KBN_EVERY_DAY);
            //曜日(日曜)
            paramMdl.setRsv111RsrDweek1(GSConstReserve.WEEK_CHECK_OFF);
            //曜日(月曜)
            paramMdl.setRsv111RsrDweek2(GSConstReserve.WEEK_CHECK_OFF);
            //曜日(火曜)
            paramMdl.setRsv111RsrDweek3(GSConstReserve.WEEK_CHECK_OFF);
            //曜日(水曜)
            paramMdl.setRsv111RsrDweek4(GSConstReserve.WEEK_CHECK_OFF);
            //曜日(木曜)
            paramMdl.setRsv111RsrDweek5(GSConstReserve.WEEK_CHECK_OFF);
            //曜日(金曜)
            paramMdl.setRsv111RsrDweek6(GSConstReserve.WEEK_CHECK_OFF);
            //曜日(土曜)
            paramMdl.setRsv111RsrDweek7(GSConstReserve.WEEK_CHECK_OFF);
            //週
            paramMdl.setRsv111RsrWeek(GSConstReserve.COMBO_DEFAULT_VALUE);
            //日
            paramMdl.setRsv111RsrDay(GSConstReserve.COMBO_DEFAULT_VALUE);


            //振替区分
            paramMdl.setRsv111RsrTranKbn(GSConstReserve.FURIKAE_NO);

            //年コンボ設定用 開始年
            UDate frUd = new UDate();
            frUd.setDate(dspDate);
            frUd.setHour(GSConstReserve.YRK_DEFAULT_START_HOUR);
            frUd.setMinute(GSConstReserve.YRK_DEFAULT_START_MINUTE);

            //年コンボ設定用 終了年
            UDate toUd = new UDate();
            toUd.setDate(dspDate);
            toUd.setHour(GSConstReserve.YRK_DEFAULT_END_HOUR);
            toUd.setMinute(GSConstReserve.YRK_DEFAULT_END_MINUTE);

            //反映期間from 年
            paramMdl.setRsv111RsrDateYearFr(String.valueOf(frUd.getYear()));
            //反映期間from 月
            paramMdl.setRsv111RsrDateMonthFr(String.valueOf(frUd.getMonth()));
            //反映期間from 日
            paramMdl.setRsv111RsrDateDayFr(String.valueOf(frUd.getIntDay()));
            //反映期間To 年
            paramMdl.setRsv111RsrDateYearTo(String.valueOf(toUd.getYear()));
            //反映期間To 月
            paramMdl.setRsv111RsrDateMonthTo(String.valueOf(toUd.getMonth()));
            //反映期間To 日
            paramMdl.setRsv111RsrDateDayTo(String.valueOf(toUd.getIntDay()));

            //時間・編集権限のデフォルト値を設定
            __setDefHourMin(paramMdl, admConf, uConf);
            //利用目的
            paramMdl.setRsv111RsrMok("");
            //内容
            paramMdl.setRsv111RsrBiko("");

            if (paramMdl.getRsv111RsrPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                int userSid = GSConst.SYSTEM_USER_ADMIN;
                if (admConf.getRacIniPublicKbn() == GSConstReserve.AUTH_ALL_USER) {
                    userSid = usrMdl.getUsrsid();
                }
                RsvInitPubDao ripDao = new RsvInitPubDao(con_);
                ArrayList<String> usrGrpList = new ArrayList<String>();
                //指定ユーザグループのみ公開の場合
                List<RsvInitPubModel> ripList =  ripDao.select(userSid);
                for (RsvInitPubModel ripMdl : ripList) {
                    if (ripMdl.getRipType() == GSConst.TYPE_GROUP) {
                        usrGrpList.add("G" + String.valueOf(ripMdl.getRipPsid()));
                    } else {
                        usrGrpList.add(String.valueOf(ripMdl.getRipPsid()));
                    }
                }
                String[] sidList = usrGrpList.toArray(new String[0]);
                paramMdl.setRsv111PubUsrGrpSid(sidList);
            }

            if (RsvCommonBiz.isRskKbnRegCheck(rskSid)) {
                //施設予約区分別情報
                GroupDao dao = new GroupDao(con_);
                CmnGroupmModel grpMdl = dao.getDefaultGroup(usrMdl.getUsrsid());

                //担当部署
                paramMdl.setRsv111Busyo(grpMdl.getGrpName());
                //担当・使用者名
                paramMdl.setRsv111UseName(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());
                //連絡先
                CmnUsrmInfDao uInfDao = new CmnUsrmInfDao(con_);
                CmnUsrmInfModel uInfMdl = uInfDao.select(usrMdl.getUsrsid());
                paramMdl.setRsv111Contact(NullDefault.getString(uInfMdl.getUsiTelNai1(), ""));

                //施設区分 部屋
                if (rskSid == GSConstReserve.RSK_KBN_HEYA) {
                    paramMdl.setRsv111UseKbn(GSConstReserve.RSY_USE_KBN_NOSET);

                } else if (rskSid == GSConstReserve.RSK_KBN_CAR) {
                    paramMdl.setRsv111PrintKbn(GSConstReserve.RSY_PRINT_KBN_YES);
                }
            }


        } else {

            //施設予約拡張SID
            paramMdl.setRsv111RsrRsid(bean.getRsrRsid());
            //拡張区分
            paramMdl.setRsv111RsrKbn(bean.getRsrKbn());
            //曜日(日曜)
            paramMdl.setRsv111RsrDweek1(bean.getRsrDweek1());
            //曜日(月曜)
            paramMdl.setRsv111RsrDweek2(bean.getRsrDweek2());
            //曜日(火曜)
            paramMdl.setRsv111RsrDweek3(bean.getRsrDweek3());
            //曜日(水曜)
            paramMdl.setRsv111RsrDweek4(bean.getRsrDweek4());
            //曜日(木曜)
            paramMdl.setRsv111RsrDweek5(bean.getRsrDweek5());
            //曜日(金曜)
            paramMdl.setRsv111RsrDweek6(bean.getRsrDweek6());
            //曜日(土曜)
            paramMdl.setRsv111RsrDweek7(bean.getRsrDweek7());
            //週
            paramMdl.setRsv111RsrWeek(bean.getRsrWeek());
            //日
            paramMdl.setRsv111RsrDay(bean.getRsrDay());
            //毎年 月
            if (bean.getRsrMonthYearly() > 0) {
                paramMdl.setRsv111RsrMonthOfYearly(bean.getRsrMonthYearly());
            }
            //毎年 日
            if (bean.getRsrDayYearly() > 0) {
                paramMdl.setRsv111RsrDayOfYearly(bean.getRsrDayYearly());
            }

            //振替区分
            paramMdl.setRsv111RsrTranKbn(bean.getRsrTranKbn());

            UDate frUd = bean.getRsrDateFr();
            UDate toUd = bean.getRsrDateTo();

            //反映期間from 年
            paramMdl.setRsv111RsrDateYearFr(String.valueOf(frUd.getYear()));
            //反映期間from 月
            paramMdl.setRsv111RsrDateMonthFr(String.valueOf(frUd.getMonth()));
            //反映期間from 日
            paramMdl.setRsv111RsrDateDayFr(String.valueOf(frUd.getIntDay()));
            //反映期間To 年
            paramMdl.setRsv111RsrDateYearTo(String.valueOf(toUd.getYear()));
            //反映期間To 月
            paramMdl.setRsv111RsrDateMonthTo(String.valueOf(toUd.getMonth()));
            //反映期間To 日
            paramMdl.setRsv111RsrDateDayTo(String.valueOf(toUd.getIntDay()));

            UDate frTime = bean.getRsrTimeFr();
            UDate toTime = bean.getRsrTimeTo();

            //時間from
            paramMdl.setRsv111RsrTimeHourFr(String.valueOf(frTime.getIntHour()));
            //時間from 分
            paramMdl.setRsv111RsrTimeMinuteFr(String.valueOf(frTime.getIntMinute()));
            //時間To
            paramMdl.setRsv111RsrTimeHourTo(String.valueOf(toTime.getIntHour()));
            //時間To
            paramMdl.setRsv111RsrTimeMinuteTo(String.valueOf(toTime.getIntMinute()));

            //利用目的
            paramMdl.setRsv111RsrMok(NullDefault.getString(bean.getRsrMok(), ""));
            //内容
            paramMdl.setRsv111RsrBiko(NullDefault.getString(bean.getRsrBiko(), ""));
            //権限設定
            paramMdl.setRsv111RsrEdit(bean.getRsrEdit());
            //公開区分
            paramMdl.setRsv111RsrPublic(bean.getRsrPublic());

            if (paramMdl.getRsv111RsrPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                String[] sidList = __getPubTargetSidList(paramMdl.getRsv110RsySid());
                paramMdl.setRsv111PubUsrGrpSid(sidList);
            }

            if (RsvCommonBiz.isRskKbnRegCheck(rskSid)) {
                RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con_);
                RsvSisKryrkModel kryrkModel = kryrkDao.select(bean.getRsrRsid());
                if (kryrkModel != null) {
                    paramMdl.setRsv111Busyo(NullDefault.getString(kryrkModel.getRkrBusyo(), ""));
                    paramMdl.setRsv111UseName(NullDefault.getString(kryrkModel.getRkrName(), ""));
                    paramMdl.setRsv111UseNum(NullDefault.getString(kryrkModel.getRkrNum(), ""));
                    paramMdl.setRsv111Contact(
                            NullDefault.getString(kryrkModel.getRkrContact(), ""));

                    if (rskSid == GSConstReserve.RSK_KBN_HEYA) {
                        paramMdl.setRsv111UseKbn(kryrkModel.getRkrUseKbn());
                        paramMdl.setRsv111Guide(
                                NullDefault.getString(kryrkModel.getRkrGuide(), ""));
                        paramMdl.setRsv111ParkNum(
                                NullDefault.getString(kryrkModel.getRkrParkNum(), ""));
                    } else if (rskSid == GSConstReserve.RSK_KBN_CAR) {
                        paramMdl.setRsv111PrintKbn(kryrkModel.getRkrPrintKbn());
                        paramMdl.setRsv111Dest(NullDefault.getString(kryrkModel.getRkrDest(), ""));
                    }
                }
            }
        }

        //編集モード時、スケジュール情報を取得する
        String procMode = paramMdl.getRsv110ProcMode();
        if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            //SCD_RSSIDからスケジュールSIDを取得する
            if (yrkMdl.getScdRsSid() > 0) {

                int scdSid = getScdSid(yrkMdl.getScdRsSid());
                if (scdSid > 0) {
                    //スケジュール管理者設定(共有範囲など)を取得
                    RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
                    RsvSchAdmConfModel adminConf = rsvSchBiz.getAdmConfModel(con_);

                    ScheduleRsvModel schMdl =
                            getSchData(reqMdl_, scdSid, adminConf, con_);

                    if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                        paramMdl.setRsv111SchKbn(GSConstReserve.RSV_SCHKBN_GROUP);

                        //同時登録スケジュールにアクセス不可の場合、未選択に設定する
                        int scdUsrSid = schMdl.getScdUsrSid();
                        if (paramMdl.getRsv110SchNotAccessGroupList().indexOf(scdUsrSid) < 0) {
                            paramMdl.setRsv111SchGroupSid(String.valueOf(scdUsrSid));
                        } else {
                            paramMdl.setRsv111SchGroupSid("-1");
                        }
                    } else {
                        if (schMdl != null
                                && (schMdl.getUsrInfList() == null
                                || schMdl.getUsrInfList().isEmpty())
                                && schMdl.getScdGrpSid() == GSConstSchedule.DF_SCHGP_ID) {

                            ArrayList<CmnUsrmInfModel> myList =
                                    new ArrayList<CmnUsrmInfModel>();

                            CmnUsrmInfModel myMdl = new CmnUsrmInfModel();
                            myMdl.setUsrSid(schMdl.getScdUsrSid());
                            myMdl.setUsiSei(schMdl.getScdUsrSei());
                            myMdl.setUsiMei(schMdl.getScdUsrMei());

                            myList.add(myMdl);
                            schMdl.setUsrInfList(myList);
                        }

                        if (paramMdl.getRsv111SvUsers() == null
                                || paramMdl.getRsv111SvUsers().length == 0) {
                            __setExSaveUsersForDb(paramMdl, schMdl.getUsrInfList());
                        }
                    }
                }

            }
        }

        //初期表示フラグ
        paramMdl.setRsv111InitFlg(false);
    }

    /**
     * <br>[機  能] 各項目の選択値を設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param frDate 予約開始
     * @param toDate 予約終了
     * @param admConf 管理者設定
     * @param uConf 個人設定
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __setComboSelectValueInit(
            Rsv110ParamModel paramMdl,
            UDate frDate,
            UDate toDate,
            RsvAdmConfModel admConf,
            RsvUserModel uConf,
            int usrSid)
                    throws SQLException {
        RsvCommonBiz rsvBiz = new RsvCommonBiz();

        //編集権限
        paramMdl.setRsv110RsyEdit(rsvBiz.getInitEditAuth(admConf, uConf));
        //公開区分
        paramMdl.setRsv110Public(rsvBiz.getInitPublicAuth(admConf, uConf));

        if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
            int userSid = GSConst.SYSTEM_USER_ADMIN;
            if (admConf.getRacIniPublicKbn() == GSConstReserve.AUTH_ALL_USER) {
                userSid = usrSid;
            }
            RsvInitPubDao ripDao = new RsvInitPubDao(con_);
            ArrayList<String> usrGrpList = new ArrayList<String>();
            //指定ユーザグループのみ公開の場合
            List<RsvInitPubModel> ripList =  ripDao.select(userSid);
            for (RsvInitPubModel ripMdl : ripList) {
                if (ripMdl.getRipType() == GSConst.TYPE_GROUP) {
                    usrGrpList.add("G" + String.valueOf(ripMdl.getRipPsid()));
                } else {
                    usrGrpList.add(String.valueOf(ripMdl.getRipPsid()));
                }
            }
            String[] sidList = usrGrpList.toArray(new String[0]);
            paramMdl.setRsv110PubUsrGrpSid(sidList);
        }

        // 開始期間
        UDate iniFrDate = rsvBiz.getFrDateAuth(admConf, uConf);
        //年コンボFr選択値
        paramMdl.setRsv110SelectedYearFr(frDate.getYear());
        //月コンボFr選択値
        paramMdl.setRsv110SelectedMonthFr(frDate.getMonth());
        //日コンボFr選択値
        paramMdl.setRsv110SelectedDayFr(frDate.getIntDay());
        //時コンボFr選択値
        paramMdl.setRsv110SelectedHourFr(iniFrDate.getIntHour());
        //分コンボFr選択値
        paramMdl.setRsv110SelectedMinuteFr(iniFrDate.getIntMinute());

        // 終了期間
        UDate  iniToDate = rsvBiz.getToDateAuth(admConf, uConf);
        //年コンボTo選択値(初期表示時はその日内に完結するようにセット)
        paramMdl.setRsv110SelectedYearTo(toDate.getYear());
        //月コンボTo選択値
        paramMdl.setRsv110SelectedMonthTo(toDate.getMonth());
        //日コンボTo選択値
        paramMdl.setRsv110SelectedDayTo(toDate.getIntDay());
        //時コンボTo選択値
        paramMdl.setRsv110SelectedHourTo(iniToDate.getIntHour());
        //分コンボTo選択値
        paramMdl.setRsv110SelectedMinuteTo(iniToDate.getIntMinute());
    }

    /**
     * <br>[機  能] 各コンボの選択値を設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param frDate 予約開始
     * @param toDate 予約終了
     * @throws SQLException SQL実行時例外
     */
    private void __setComboSelectValueEdit(
            Rsv110ParamModel paramMdl, UDate frDate, UDate toDate)
                    throws SQLException {

        //年コンボFr選択値
        paramMdl.setRsv110SelectedYearFr(frDate.getYear());
        //月コンボFr選択値
        paramMdl.setRsv110SelectedMonthFr(frDate.getMonth());
        //日コンボFr選択値
        paramMdl.setRsv110SelectedDayFr(frDate.getIntDay());
        //時コンボFr選択値
        paramMdl.setRsv110SelectedHourFr(frDate.getIntHour());
        //分コンボFr選択値
        paramMdl.setRsv110SelectedMinuteFr(frDate.getIntMinute());

        //年コンボTo選択値(初期表示時はその日内に完結するようにセット)
        paramMdl.setRsv110SelectedYearTo(toDate.getYear());
        //月コンボTo選択値
        paramMdl.setRsv110SelectedMonthTo(toDate.getMonth());
        //日コンボTo選択値
        paramMdl.setRsv110SelectedDayTo(toDate.getIntDay());
        //時コンボTo選択値
        paramMdl.setRsv110SelectedHourTo(toDate.getIntHour());
        //分コンボTo選択値
        paramMdl.setRsv110SelectedMinuteTo(toDate.getIntMinute());
    }

    /**
     * <br>[機  能] DBから取得した施設グループ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dbMdl DB取得結果
     */
    private void __setGroupData(Rsv110ParamModel paramMdl,
            Rsv070Model dbMdl) {

        //所属グループ名
        paramMdl.setRsv110GrpName(NullDefault.getString(dbMdl.getRsgName(), ""));
        //施設区分 */
        paramMdl.setRsv110SisetuKbn(dbMdl.getRskSid());
        //施設区分名称 */
        paramMdl.setRsv110SisetuKbnName(NullDefault.getString(dbMdl.getRskName(), ""));
        //施設名称
        paramMdl.setRsv110SisetuName(NullDefault.getString(dbMdl.getRsdName(), ""));
        //資産管理番号
        paramMdl.setRsv110SisanKanri(NullDefault.getString(dbMdl.getRsdSnum(), ""));
        //可変項目1
        paramMdl.setRsv110Prop1Value(NullDefault.getString(dbMdl.getRsdProp1Value(), ""));
        //可変項目2
        paramMdl.setRsv110Prop2Value(
                NullDefault.getStringZeroLength(
                        dbMdl.getRsdProp2Value(),
                        String.valueOf(GSConstReserve.PROP_KBN_KA)));
        //可変項目3
        paramMdl.setRsv110Prop3Value(
                NullDefault.getStringZeroLength(
                        dbMdl.getRsdProp3Value(),
                        String.valueOf(GSConstReserve.PROP_KBN_KA)));
        //可変項目4
        paramMdl.setRsv110Prop4Value(NullDefault.getString(dbMdl.getRsdProp4Value(), ""));
        //可変項目5
        paramMdl.setRsv110Prop5Value(NullDefault.getString(dbMdl.getRsdProp5Value(), ""));
        //可変項目6
        paramMdl.setRsv110Prop6Value(NullDefault.getString(dbMdl.getRsdProp6Value(), ""));
        //可変項目7
        paramMdl.setRsv110Prop7Value(
                NullDefault.getStringZeroLength(
                        dbMdl.getRsdProp7Value(),
                        String.valueOf(GSConstReserve.PROP_KBN_KA)));
        //備考
        paramMdl.setRsv110Biko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(dbMdl.getRsdBiko(), "")));
    }

    /**
     * <br>[機  能] 予約データ変更権限を判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param bean 施設予約情報
     * @param grpBean グループ情報
     * @param rsySid 変更対象の施設予約SID
     * @throws SQLException SQL実行時例外
     */
    private void __setEditAuth(
            Rsv110ParamModel paramMdl, Rsv110SisetuModel bean, Rsv070Model grpBean,
            int rsySid)
                    throws SQLException {

        boolean auth = false;
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);

        /***********************************************
         *
         * 強権限を判定
         * (どの権限設定の場合でも強制的に変更可能)
         *
         ***********************************************/
        //システム管理者グループ所属
        CommonBiz cmnBiz = new CommonBiz();
        boolean admFlg = cmnBiz.isPluginAdmin(con_, usrMdl, GSConstReserve.PLUGIN_ID_RESERVE);
        if (admFlg) {
            auth = true;
        }

        /***********************************************
        *
        * 施設グループ管理責任者か判定
        * (どの権限設定の場合でも強制的に変更可能)
        *
        ***********************************************/
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        if (!auth) {
            if (rsvCmnBiz.isGroupAdmin(con_, bean.getRsdSid(), usrMdl.getUsrsid())) {
                auth = true;
            }
        }

        /***********************************************
         *
         * アクセス権限を判定
         *
         ***********************************************/
        if (!auth) {
            boolean accessOkFlg = false;
            accessOkFlg = _isEditRsvGrp(con_, grpBean.getRsgSid(), usrMdl.getUsrsid(), admFlg);

            //編集権限
            if (!accessOkFlg) {
                paramMdl.setRsv110EditAuth(false);
                return;
            }
        }

        /***********************************************
         *
         * 強権限が無い場合は細かくチェック
         *
         ***********************************************/
        if (!auth) {
            int rsyEdit = bean.getRsyEdit();
            auth = rsvCmnBiz.isAbleEditLowAuth(
                    con_, rsySid, bean.getRsyAuid(), bean.getRsyEuid(),
                    rsyEdit, bean.getScdRsSid(), usrMdl.getUsrsid(), bean.getRsyPublic());

        }

        paramMdl.setRsv110EditAuth(auth);
    }

    /**
     * <br>[機  能] DBから取得した予約情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dbMdl DBからの取得値
     * @param dspUsr 表示ユーザ 0:登録者 1:更新者
     */
    private void __setDspUsr(
            Rsv110ParamModel paramMdl, Rsv110SisetuModel dbMdl, int dspUsr) {

        if (dspUsr == GSConstReserve.DSP_USR_ADD) {
            //新規登録者名
            paramMdl.setRsv110AuId(dbMdl.getRsyAuid());
            paramMdl.setRsv110Torokusya(
                    NullDefault.getString(dbMdl.getUsiSei(), "")
                    + "  "
                    + NullDefault.getString(dbMdl.getUsiMei(), ""));
            paramMdl.setRsv110AddUsrJKbn(dbMdl.getUsrJkbn());
            paramMdl.setRsv110AddUsrUkoFlg(dbMdl.getUsrUkoFlg());

        } else if (dspUsr == GSConstReserve.DSP_USR_EDIT) {
            //最終更新者
            paramMdl.setRsv110EuId(dbMdl.getRsyEuid());
            paramMdl.setRsv110Koshinsya(
                    NullDefault.getString(dbMdl.getUsiSei(), "")
                    + "  "
                    + NullDefault.getString(dbMdl.getUsiMei(), ""));
            paramMdl.setRsv110EditUsrJKbn(dbMdl.getUsrJkbn());
            paramMdl.setRsv110EditUsrUkoFlg(dbMdl.getUsrUkoFlg());
        }


    }

    /**
     * <br>[機  能] DBから取得した予約情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dbMdl DBからの取得値
     * @param dspUsr 表示ユーザ 0:登録者 1:更新者
     */
    private void __setDspDate(
            Rsv110ParamModel paramMdl, Rsv110SisetuModel dbMdl, int dspUsr) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        if (dspUsr == GSConstReserve.DSP_USR_ADD) {
            //登録日時
            String textAddDate = gsMsg.getMessage("schedule.src.84");
            paramMdl.setRsv110AddDate(
                    textAddDate + " : "
                            + UDateUtil.getSlashYYMD(dbMdl.getRsyAdate())
                            + " "
                            + UDateUtil.getSeparateHM(dbMdl.getRsyAdate()));
        } else if (dspUsr == GSConstReserve.DSP_USR_EDIT) {
            //更新日時
            String textEditDate = gsMsg.getMessage("schedule.src.85");
            paramMdl.setRsv110EditDate(
                    textEditDate + " : "
                            + UDateUtil.getSlashYYMD(dbMdl.getRsyEdate())
                            + " "
                            + UDateUtil.getSeparateHM(dbMdl.getRsyEdate()));
        }
    }

    /**
     * <br>[機  能] DBから取得した予約情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dbMdl DBからの取得値
     * @param pconfig プラグイン情報
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __setYoyakuData(Rsv110ParamModel paramMdl,
            Rsv110SisetuModel dbMdl,
            PluginConfig pconfig,
            int userSid)
                    throws SQLException {

        //利用目的
        paramMdl.setRsv110Mokuteki(NullDefault.getString(dbMdl.getRsyMok(), ""));
        //利用目的(エスケープ処理  承認・却下時のメッセージ用)
        paramMdl.setRsv110MokutekiEsc(StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(dbMdl.getRsyMok(), "")));
        //編集権限
        paramMdl.setRsv110RsyEdit(dbMdl.getRsyEdit());
        //公開区分
       paramMdl.setRsv110Public(dbMdl.getRsyPublic());

       if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
           RsvDataPubDao ripDao = new RsvDataPubDao(con_);
           int rsySid = paramMdl.getRsv110RsySid();
           ArrayList<String> usrGrpList = new ArrayList<String>();
           List<RsvDataPubModel> ripList = ripDao.select(rsySid);
           for (RsvDataPubModel ripMdl : ripList) {
               if (ripMdl.getRdpType() == GSConst.TYPE_GROUP) {
                   usrGrpList.add("G" + String.valueOf(ripMdl.getRdpPsid()));
               } else {
                   usrGrpList.add(String.valueOf(ripMdl.getRdpPsid()));
               }
           }
           String[] sidList = usrGrpList.toArray(new String[0]);
           paramMdl.setRsv110PubUsrGrpSid(sidList);
       }

       //スケジュールプラグイン使用判定
        if (pconfig.getPlugin("schedule") != null) {
            log__.debug("スケジュールプラグイン使用");
            //スケジュールリレーションSID
            paramMdl.setRsv110ScdRsSid(dbMdl.getScdRsSid());
        } else {
            log__.debug("スケジュールプラグイン未使用");
            //スケジュールリレーションSID
            paramMdl.setRsv110ScdRsSid(0);
        }

        //ポップアップモードの場合は表示用の予約日付をセット
        if (paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_POPUP)
                || !paramMdl.isRsv110EditAuth()) {
            //予約開始
            paramMdl.setYoyakuFrString(__convertUdateToYmdhm(dbMdl.getRsyFrDate()));
            //予約終了
            paramMdl.setYoyakuToString(__convertUdateToYmdhm(dbMdl.getRsyToDate()));
            //内容
            paramMdl.setRsv110Naiyo(StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(dbMdl.getRsyBiko(), "")));
        } else {
            //内容
            paramMdl.setRsv110Naiyo(NullDefault.getString(dbMdl.getRsyBiko(), ""));
        }

        //承認(否認)ボタン表示フラグ
        paramMdl.setRsv110ApprBtnFlg(0);
        RsvCommonBiz rsvBiz = new RsvCommonBiz();

        //セッションユーザが施設グループ管理者の場合のみ表示判定
        if (rsvBiz.isSisGrpAdmin(con_, paramMdl.getRsv110RsySid(), userSid)) {
            if (dbMdl.getRsyApprStatus() == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
                //承認待ち状態の場合は承認ボタン表示
                paramMdl.setRsv110ApprBtnFlg(1);
            } else {
                //所属する施設グループの区分を判定する
                RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
                RsvSisYrkModel yrkMdl = yrkDao.select(paramMdl.getRsv110RsySid());
                RsvSisDataDao sisDataDao = new RsvSisDataDao(con_);
                RsvSisDataModel sisData = sisDataDao.getSisetuData(yrkMdl.getRsdSid());

                if (rsvBiz.isApprSisGroup(con_, sisData.getRsgSid())
                        || sisData.getRsdApprKbn() == GSConstReserve.RSD_APPR_KBN_APPR) {
                    //施設グループ管理者以外が登録、且つ否認済みでない場合否認ボタン表示
                    if (!rsvBiz.isSisGrpAdmin(con_, paramMdl.getRsv110RsySid(), yrkMdl.getRsyAuid())
                            && yrkMdl.getRsyApprKbn() != GSConstReserve.RSY_APPR_KBN_REJECTION) {
                        paramMdl.setRsv110ApprBtnFlg(2);
                    } else {
                        paramMdl.setRsv110ApprBtnFlg(0);
                    }
                }
            }
        }

    }

    /**
     * <br>[機  能] UDateをyyyy/mm/dd hh:MM に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 変換対象
     * @return convDateString 変換後
     */
    private String __convertUdateToYmdhm(UDate target) {

        String convDateString = "";

        if (target == null) {
            return convDateString;
        }

        StringBuilder convBuf = new StringBuilder();
        convBuf.append(UDateUtil.getSlashYYMD(target));
        convBuf.append("    ");
        convBuf.append(UDateUtil.getSeparateHM(target));

        convDateString = convBuf.toString();

        return convDateString;
    }

    /**
     * <br>[機  能] DBから取得した区分別予約情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dbMdl DBからの取得値
     * @param rskSid 施設区分
     * @throws SQLException SQL実行時例外
     */
    private void __setKbnYoyakuData(
            Rsv110ParamModel paramMdl,
            Rsv110SisetuModel dbMdl,
            int rskSid)
                    throws SQLException {

        paramMdl.setRsv110Busyo(NullDefault.getString(dbMdl.getRkyBusyo(), ""));
        paramMdl.setRsv110UseName(NullDefault.getString(dbMdl.getRkyName(), ""));
        paramMdl.setRsv110UseNum(NullDefault.getString(dbMdl.getRkyNum(), ""));
        paramMdl.setRsv110Contact(NullDefault.getString(dbMdl.getRkyContact(), ""));

        if (rskSid == GSConstReserve.RSK_KBN_HEYA) {
            paramMdl.setRsv110UseKbn(dbMdl.getRkyUseKbn());
            paramMdl.setRsv110Guide(NullDefault.getString(dbMdl.getRkyGuide(), ""));
            paramMdl.setRsv110ParkNum(NullDefault.getString(dbMdl.getRkyParkNum(), ""));
        } else if (rskSid == GSConstReserve.RSK_KBN_CAR) {
            paramMdl.setRsv110PrintKbn(dbMdl.getRkyPrintKbn());
            paramMdl.setRsv110Dest(NullDefault.getString(dbMdl.getRkyDest(), ""));
        }


    }

    /**
     * <br>[機  能] 施設区分に応じて施設のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param rskSid 施設区分SID
     */
    private void __setSisetuHeader(Rsv110ParamModel paramMdl, int rskSid) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
        //部屋
        case GSConstReserve.RSK_KBN_HEYA:
            paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.128"));
            paramMdl.setRsv110PropHeaderName2(gsMsg.getMessage("reserve.132"));
            paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
            paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
            break;
            //物品
        case GSConstReserve.RSK_KBN_BUPPIN:
            paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.130"));
            paramMdl.setRsv110PropHeaderName3(gsMsg.getMessage("reserve.133"));
            paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
            paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
            break;
            //車
        case GSConstReserve.RSK_KBN_CAR:
            paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.129"));
            paramMdl.setRsv110PropHeaderName2(gsMsg.getMessage("reserve.132"));
            paramMdl.setRsv110PropHeaderName4(gsMsg.getMessage("reserve.134"));
            paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
            paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
            break;
            //書籍
        case GSConstReserve.RSK_KBN_BOOK:
            paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.131"));
            paramMdl.setRsv110PropHeaderName3(gsMsg.getMessage("reserve.133"));
            paramMdl.setRsv110PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
            paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
            paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
            break;
            //その他
        case GSConstReserve.RSK_KBN_OTHER:
            paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
            paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
            break;
        default:
            break;
        }
    }

    /**
     * <br>[機  能] 期間の時・分・編集権限・公開区分の初期値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param admConf 管理者設定
     * @param uConf 個人設定
     * @throws SQLException SQL実行時例外
     */
    private void __setDefHourMin(Rsv110ParamModel paramMdl, RsvAdmConfModel admConf,
            RsvUserModel uConf) {

        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        // 開始期間
        UDate frDate = rsvBiz.getFrDateAuth(admConf, uConf);
        paramMdl.setRsv111RsrTimeHourFr(String.valueOf(frDate.getIntHour()));
        paramMdl.setRsv111RsrTimeMinuteFr(
                String.valueOf(frDate.getIntMinute()));
        // 終了期間
        UDate toDate = rsvBiz.getToDateAuth(admConf, uConf);
        paramMdl.setRsv111RsrTimeHourTo(String.valueOf(toDate.getIntHour()));
        paramMdl.setRsv111RsrTimeMinuteTo(
                String.valueOf(toDate.getIntMinute()));
        //編集権限の初期値を設定
        paramMdl.setRsv111RsrEdit(rsvBiz.getInitEditAuth(admConf, uConf));
        //公開区分の初期値を設定
        paramMdl.setRsv111RsrPublic(rsvBiz.getInitPublicAuth(admConf, uConf));

    }

    /**
     * <br>[機  能] スケジュールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsSid スケジュールリレーションSID
     * @return ret スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    public int getScdSid(int rsSid) throws SQLException {

        RsvScdOperationDao rsvSchDao = new RsvScdOperationDao(con_);
        int scdSid = rsvSchDao.getScdSidFromRsSid(rsSid);

        return scdSid;
    }

    /**
     * <br>[機  能] 施設予約区分別情報の初期値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param usrMdl ユーザモデル
     * @param rskSid 施設区分
     * @throws SQLException SQL実行時例外
     */
    private void __setInitSisYrkData(
            Rsv110ParamModel paramMdl,
            BaseUserModel usrMdl,
            int rskSid) throws SQLException {

        GroupDao dao = new GroupDao(con_);
        CmnGroupmModel grpMdl = dao.getDefaultGroup(usrMdl.getUsrsid());

        //担当部署
        paramMdl.setRsv110Busyo(grpMdl.getGrpName());
        //担当・使用者名
        paramMdl.setRsv110UseName(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());
        //連絡先
        CmnUsrmInfDao uInfDao = new CmnUsrmInfDao(con_);
        CmnUsrmInfModel uInfMdl = uInfDao.select(usrMdl.getUsrsid());
        paramMdl.setRsv110Contact(NullDefault.getString(uInfMdl.getUsiTelNai1(), ""));

        //施設区分 部屋
        if (rskSid == GSConstReserve.RSK_KBN_HEYA) {
            paramMdl.setRsv110UseKbn(GSConstReserve.RSY_USE_KBN_NOSET);
        } else if (rskSid == GSConstReserve.RSK_KBN_CAR) {
            paramMdl.setRsv110PrintKbn(GSConstReserve.RSY_PRINT_KBN_YES);
        }

    }

    /**
     * <br>[機  能] 同時登録ユーザ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param bean 既存登録データ(編集時)
     * @throws SQLException SQL実行時例外
     */
    private void __setUserList(Rsv110ParamModel paramMdl, Rsv110SisetuModel bean)
            throws SQLException {

        int usrSid = reqMdl_.getSmodel().getUsrsid();

        //デフォルト表示グループ
        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        String dfGpSidStr = rsvSchBiz.getDispDefaultGroupSidStr(con_, usrSid);

        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getRsv110GroupSid(), dfGpSidStr);
        paramMdl.setRsv110GroupSid(dspGpSidStr);
        paramMdl.setRsv111GroupSid(paramMdl.getRsv110GroupSid());

        //同時登録スケジュールグループリスト
        paramMdl.setRsv110SchGroupLabel(rsvSchBiz.getSchGroupCombo(con_, reqMdl_, usrSid));

        //スケジュール閲覧不可のグループ、ユーザを設定
        int sessionUserSid = reqMdl_.getSmodel().getUsrsid();
        SchDao schDao = new SchDao(con_);
        if (GSConstReserve.PROC_MODE_POPUP.equals(paramMdl.getRsv110ProcMode())) {
            paramMdl.setRsv110SchNotAccessGroupList(schDao.getNotAccessGrpList(sessionUserSid));
        } else {
            paramMdl.setRsv110SchNotAccessGroupList(schDao.getNotRegistGrpList(sessionUserSid));
        }
        //編集モード or ポップアップモード時
        String procMode = paramMdl.getRsv110ProcMode();

        if (paramMdl.isRsv110InitFlg()
                && (procMode.equals(GSConstReserve.PROC_MODE_EDIT)
                        || procMode.equals(GSConstReserve.PROC_MODE_POPUP))) {

            //SCD_RSSIDからスケジュールSIDを取得する
            if (bean.getScdRsSid() > 0) {
                int scdSid = getScdSid(bean.getScdRsSid());
                if (scdSid > 0) {

                    //スケジュール管理者設定(共有範囲など)を取得
                    RsvSchAdmConfModel adminConf = rsvSchBiz.getAdmConfModel(con_);

                    ScheduleRsvModel schMdl =
                            getSchData(reqMdl_, scdSid, adminConf, con_);

                    int scdUsrSid = schMdl.getScdUsrSid();
                    if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                        paramMdl.setRsv110SchKbn(GSConstReserve.RSV_SCHKBN_GROUP);

                        //同時登録スケジュールにアクセス不可の場合、未選択に設定する
                        if (paramMdl.getRsv110SchNotAccessGroupList().indexOf(scdUsrSid) < 0) {
                            paramMdl.setRsv110SchGroupSid(String.valueOf(scdUsrSid));
                        } else {
                            paramMdl.setRsv110SchGroupSid("-1");
                        }
                    } else {
                        if (schMdl != null
                                && (schMdl.getUsrInfList() == null
                                || schMdl.getUsrInfList().isEmpty())
                                && schMdl.getScdGrpSid() == GSConstSchedule.DF_SCHGP_ID) {

                            CmnUsrmInfModel myMdl = new CmnUsrmInfModel();
                            myMdl.setUsrSid(schMdl.getScdUsrSid());
                            myMdl.setUsiSei(schMdl.getScdUsrSei());
                            myMdl.setUsiMei(schMdl.getScdUsrMei());

                            ArrayList<CmnUsrmInfModel> myList =
                                    new ArrayList<CmnUsrmInfModel>();

                            myList.add(myMdl);
                            schMdl.setUsrInfList(myList);
                        }

                        if (paramMdl.getSv_users() == null
                                || paramMdl.getSv_users().length == 0) {
                            __setSaveUsersForDb(paramMdl, schMdl.getUsrInfList());
                        }
                    }
                }
            }
        }

        //スケジュールを登録するユーザがいる場合、登録するユーザの名称をセット
        if (paramMdl.getSv_users() != null && paramMdl.getSv_users().length > 0) {
            setUserName(paramMdl, paramMdl.getRsv110SchKbn(), paramMdl.getSv_users(),
                    paramMdl.getRsv110SchGroupSid());
        }
    }

    /**
     * <br>[機  能] DBに登録されているスケジュール登録ユーザ情報を画面パラメータへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param list 同時登録ユーザ情報リスト
     */
    private void __setSaveUsersForDb(Rsv110ParamModel paramMdl, ArrayList<CmnUsrmInfModel> list) {

        ArrayList<String> sv_user_list = new ArrayList<String>();
        if (list != null) {
            for (CmnUsrmInfModel usrMdl : list) {
                sv_user_list.add(String.valueOf(usrMdl.getUsrSid()));
            }
            paramMdl.setSv_users((String[]) sv_user_list.toArray(new String[sv_user_list.size()]));
        }
    }

    /**
     * <br>[機  能] DBに登録されているスケジュール登録ユーザ情報を画面パラメータへ設定する
     * <br>[解  説] 拡張登録のパラメータを設定する
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param list 同時登録ユーザ情報リスト
     */
    private void __setExSaveUsersForDb(Rsv110ParamModel paramMdl, ArrayList<CmnUsrmInfModel> list) {

        ArrayList<String> sv_user_list = new ArrayList<String>();
        if (list != null) {
            for (CmnUsrmInfModel usrMdl : list) {
                sv_user_list.add(String.valueOf(usrMdl.getUsrSid()));
            }
            paramMdl.setRsv111SvUsers(
                    (String[]) sv_user_list.toArray(
                            new String[sv_user_list.size()]));
        }
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する
     * <br>[解  説] 管理者設定の共有範囲が「ユーザ全員で共有」の場合有効な全てのグループを取得する。
     * <br>         「所属グループ内のみ共有可」の場合、ユーザが所属するグループのみを返す。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<ExtendedLabelValueModel> getGroupLabelList(int usrSid) throws SQLException {

        List<ExtendedLabelValueModel> labelList = null;

        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        labelList =
                rsvSchBiz.getGroupLabelForSchedule(
                        con_, reqMdl_, usrSid, false);

        return labelList;
    }

    /**
     * <br>スケジュールSIDからスケジュール情報を取得する
     * @param reqMdl リクエスト情報
     * @param scdSid スケジュールSID
     * @param adminConf 管理者設定
     * @param con コネクション
     * @return ScheduleSearchModel
     * @throws SQLException SQL実行時例外
     */
    public ScheduleRsvModel getSchData(RequestModel reqMdl,
            int scdSid,
            RsvSchAdmConfModel adminConf,
            Connection con)
                    throws SQLException {

        ScheduleRsvModel scdMdl = null;
        CmnUsrmInfModel uMdl = null;

        try {

            RsvScdOperationDao rsvSchDao = new RsvScdOperationDao(con_);
            scdMdl = rsvSchDao.getSchData(scdSid, reqMdl.getSmodel().getUsrsid());

            if (scdMdl == null || (scdMdl != null && scdMdl.getScdSid() < 1)) {
                return null;
            }
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmDao cuDao = new CmnUsrmDao(con);

            //登録者
            uMdl = uDao.getUserInfoJtkb(scdMdl.getScdAuid(), -1);
            if (uMdl != null) {
                scdMdl.setScdAuidSei(uMdl.getUsiSei());
                scdMdl.setScdAuidMei(uMdl.getUsiMei());
                scdMdl.setScdAuidJkbn(cuDao.getUserJkbn(scdMdl.getScdAuid()));
                scdMdl.setScdAuidUkoFlg(uMdl.getUsrUkoFlg());
            }

            //対象ユーザ
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                uMdl = uDao.getUserInfoJtkb(scdMdl.getScdUsrSid(), -1);
                if (uMdl != null) {
                    scdMdl.setScdUsrSei(uMdl.getUsiSei());
                    scdMdl.setScdUsrMei(uMdl.getUsiMei());
                    scdMdl.setScdUsrJkbn(cuDao.getUserJkbn(scdMdl.getScdUsrSid()));
                    scdMdl.setScdUsrUkoFlg(uMdl.getUsrUkoFlg());
                }
            } else {
                scdMdl.setScdUsrSei(
                        getUsrName(
                                reqMdl_,
                                scdMdl.getScdUsrSid(),
                                scdMdl.getScdUsrKbn(),
                                con));
            }
        } catch (SQLException e) {
            log__.error("スケジュール情報の取得に失敗" + e);
            throw e;
        }

        return scdMdl;
    }

    /**
     * <br>ユーザSIDとユーザ区分からユーザ氏名を取得する
     * @param reqMdl リクエスト情報
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param con コネクション
     * @return String ユーザ氏名
     * @throws SQLException SQL実行時例外
     */
    public String getUsrName(RequestModel reqMdl, int usrSid, int usrKbn,
            Connection con)
                    throws SQLException {
        String ret = "";
        if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {

            if (usrSid == GSConstSchedule.SCHEDULE_GROUP) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                ret = gsMsg.getMessage("cmn.group");
            } else {
                GroupDao grpDao = new GroupDao(con);
                ret = grpDao.getGroup(usrSid).getGrpName();
            }

        } else {
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmInfModel uMdl = uDao.getUserInfoJtkb(usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            ret = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
        }
        return ret;
    }

    /**
     * <br>[機  能] スケジュール登録ユーザ/グループ名一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param kbn スケジュール登録区分
     * @param users 選択されているユーザのユーザSID
     * @param grpSid 選択されているグループのグループSID
     * @throws SQLException SQL実行時例外
     */
    public void setUserName(Rsv110ParamModel paramMdl, int kbn, String[] users, String grpSid)
            throws SQLException {

        ArrayList<UsrLabelValueBean> usrArray = new ArrayList<UsrLabelValueBean>();
        if (kbn == GSConstReserve.RSV_SCHKBN_GROUP) {

            if (NullDefault.getInt(grpSid, -1) >= 0) {
                GroupDao grpDao = new GroupDao(con_);
                CmnGroupmModel grpMdl = grpDao.getGroup(Integer.parseInt(grpSid));
                usrArray.add(new UsrLabelValueBean(grpMdl.getGrpName(),
                        String.valueOf(grpMdl.getGrpSid())));
            }
        } else {
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> usrList
            = userBiz.getUserList(con_, users);

            if (usrList != null && !usrList.isEmpty()) {
                for (CmnUsrmInfModel usrData : usrList) {
                    usrArray.add(new UsrLabelValueBean(usrData));
                }
            }
        }

        paramMdl.setRsvSchUserNameArray(usrArray);
    }

    /**
     * <br>[機  能] 施設予約情報の承認区分を更新する
     * <br>[解  説]
     * <br>[備  考] 同時に承認状況を[承認待ち]から[通常]へ更新する
     * @param paramMdl フォーム
     * @param apprKbn 承認区分(承認 or 却下)
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateYoyakuAppr(Rsv110ParamModel paramMdl, int apprKbn, int userSid)
            throws SQLException {
        int rsySid = paramMdl.getRsv110RsySid();

        RsvSisYrkDao sisYrkDao = new RsvSisYrkDao(con_);
        sisYrkDao.updateYoyakuAppr(rsySid, apprKbn, userSid, new UDate());
    }

    /**
     * <br>[機  能] 予約情報登録者ユーザSID取得。
     * <br>[解  説]
     * <br>[備  考]
     * @param  rsySid 予約SID
     * @return int ユーザSID
     * @throws Exception 実行例外
     */
    public int getEntryUserSid(
            int rsySid) throws Exception {

        int entryUserSid = -1;
        Rsv110SisetuModel model = __getYoyakuEditData(rsySid);

        entryUserSid = model.getRsyAuid();
        return entryUserSid;
    }

    /**
     * <br>[機  能] ショートメールで施設予約 申請通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param smailData ショートメール通知情報
     * @throws Exception 実行例外
     */
    public void sendSmail(Rsv110SMailModel smailData) throws Exception {

        //施設予約表示モデル(ショートメール送信用)
        Rsv110SyoninSmlModel rsvModel = new Rsv110SyoninSmlModel();

        UDate now = new UDate();
        String strNow = now.getDateString();
        RsvCommonBiz cmnBiz = new RsvCommonBiz();

        Rsv110SisetuModel model = __getYoyakuEditData(smailData.getRsySid());
        rsvModel.setRsvSid(model.getRsdSid());
        rsvModel.setRsvMokuteki(model.getRsyMok());
        rsvModel.setRsvNaiyou(model.getRsyBiko());
        rsvModel.setSendUserSid(model.getRsyAuid());
        rsvModel.setRsvFrDate(model.getRsyFrDate());
        rsvModel.setRsvToDate(model.getRsyToDate());
        rsvModel.setRsvAdate(model.getRsyEdate());
        rsvModel.setRsvUrl(cmnBiz.createReserveUrl(smailData.getReqMdl(),
                smailData.getRsySid(),
                Integer.parseInt(GSConstReserve.PROC_MODE_EDIT),
                strNow));

        //送信
        sendSmail(smailData.getCon(), smailData.getCntCon(), rsvModel,
                smailData.getAppRootPath(), smailData.getPluginConfig(),
                smailData.getReqMdl(), smailData.getProcMode(), smailData.getDelMode());
    }

    /**
     * <br>[機  能] ショートメールで更新通知を行う。
     * <br>[解  説] 承認設定がされている施設予約が登録した際に行う。
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param rsvModel 投稿内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @param procMode 処理モード
     * @param delMode 削除モード
     * @throws Exception 実行例外
     */
    public void sendSmail(
            Connection con,
            MlCountMtController cntCon,
            Rsv110SyoninSmlModel rsvModel,
            String appRootPath,
            PluginConfig pluginConfig,
            RequestModel reqMdl,
            int procMode,
            int delMode
            ) throws Exception {

        //投稿者名
        GsMessage gsMsg = new GsMessage(reqMdl);


        String aDate = "";
        String frDate = "";
        String toDate = "";

        String bodyml = "";

        aDate = UDateUtil.getSlashYYMD(rsvModel.getRsvAdate());
        frDate = UDateUtil.getSlashYYMD(rsvModel.getRsvFrDate())
                + " "
                + UDateUtil.getSeparateHMS(rsvModel.getRsvFrDate());
        toDate = UDateUtil.getSlashYYMD(rsvModel.getRsvToDate())
                + " "
                + UDateUtil.getSeparateHMS(rsvModel.getRsvToDate());

        //本文
        String tmpPath = __getSmlTemplateFilePathSyonin(appRootPath); //テンプレートファイルパス取得
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        Map<String, String> map = new HashMap<String, String>();
        map.put("RESERVE", rsvModel.getRsvMokuteki());
        map.put("NAIYOU", rsvModel.getRsvNaiyou());
        map.put("NAIYOU", rsvModel.getRsvNaiyou());
        map.put("ADATE", aDate);
        map.put("FRDATE", frDate);
        map.put("TODATE", toDate);
        if (delMode == 1) {
            map.put("URL", " ");
            map.put("INFO", gsMsg.getMessage("reserve.rsv110.mail.3"));
        } else {
            map.put("URL", "URL:" + rsvModel.getRsvUrl());
            map.put("INFO", " ");
        }
        if (procMode == GSConstReserve.RSY_APPR_KBN_APPROVAL) {
            map.put("PROCMODE", gsMsg.getMessage("reserve.rsv110.mail.1"));
        } else if (procMode == GSConstReserve.RSY_APPR_KBN_REJECTION) {
            map.put("PROCMODE", gsMsg.getMessage("reserve.rsv110.mail.2"));
        } else if (procMode == GSConstReserve.RSY_APPR_KBN_NOSET) {
            map.put("PROCMODE", gsMsg.getMessage("reserve.rsv110.mail.4"));
        }
        bodyml = StringUtil.merge(tmpBody, map);

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {

            String textMessage = gsMsg.getMessage("cmn.mail.omit");

            bodyml = textMessage + "\r\n\r\n" + bodyml;

            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);

        }

        List<Integer> sidList = new ArrayList<Integer>();
        sidList.add(rsvModel.getSendUserSid());
        //ショートメール送信用モデルを作成する。
        //
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(sidList);

        //タイトル
        String title = gsMsg.getMessage("reserve.172");
        title = StringUtil.trimRengeString(title,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(title);

        //本文
        smlModel.setSendBody(bodyml);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel,
                pluginConfig, appRootPath, reqMdl);
        sender.execute();
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから登録通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private String __getSmlTemplateFilePathSyonin(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/reserve/smail/syounin_tsuuchi.txt");
        return ret;
    }


    /**
     * <br>[機  能] 施設予約(単票)をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param pconfig プラグイン情報
     * @param userSid セッションユーザSID
     * @return pdfModel 施設予約単票PDFモデル
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行例外
     */
    public RsvTanPdfModel createRsvTanPdf(
            Rsv110ParamModel paramMdl,
            String appRootPath,
            String outTempDir,
            PluginConfig pconfig,
            int userSid)
                    throws IOException, SQLException {
        OutputStream oStream = null;

        //施設予約(単票)PDF出力用モデル
        RsvTanPdfModel pdfModel = __getRsvPdfDataList(paramMdl, pconfig, userSid);

        String saveFileName = "rsvtan" + reqMdl_.getSmodel().getUsrsid() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RsvTanPdfUtil pdfUtil = new RsvTanPdfUtil(reqMdl_);
            pdfUtil.createRsvTanPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("施設予約(単票)PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("施設予約(単票)PDF出力を終了します。");

        return pdfModel;
    }

    /**
     * <br>[機  能] PDF出力用のデータモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param pconfig プラグイン情報
     * @param userSid セッションユーザSID
     * @return PDF出力用モデル
     * @throws SQLException SQL実行時例外
     */
    private RsvTanPdfModel __getRsvPdfDataList(
            Rsv110ParamModel paramMdl,
            PluginConfig pconfig,
            int userSid) throws SQLException {

        RsvTanPdfModel ret = new RsvTanPdfModel();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        //予約情報取得
        Rsv110SisetuModel yrkAddMdl = getYoyakuAddData(paramMdl);
        Rsv110SisetuModel yrkEditMdl = getYoyakuEditData(paramMdl);

        if (yrkEditMdl != null) {

            //新規登録者名をセット
            ret.setPdfRsvAddName(
                    NullDefault.getString(yrkAddMdl.getUsiSei(), "")
                    + " "
                    + NullDefault.getString(yrkAddMdl.getUsiMei(), ""));

            //登録日付をセット
            ret.setPdfRsvAddDate(
                    UDateUtil.getSlashYYMD(yrkAddMdl.getRsyAdate())
                    + " "
                    + UDateUtil.getSeparateHM(yrkAddMdl.getRsyAdate()));

            //最終更新者名をセット
            ret.setPdfRsvEditName(
                    NullDefault.getString(yrkEditMdl.getUsiSei(), "")
                    + " "
                    + NullDefault.getString(yrkEditMdl.getUsiMei(), ""));

            //更新日付をセット
            ret.setPdfRsvEditDate(
                    UDateUtil.getSlashYYMD(yrkEditMdl.getRsyEdate())
                    + " "
                    + UDateUtil.getSeparateHM(yrkEditMdl.getRsyEdate()));

            //施設グループ情報を取得
            Rsv070Model grpMdl = __getGroupData(yrkEditMdl.getRsdSid());
            String sisName = null;
            if (grpMdl != null) {

                //施設グループ
                ret.setPdfRsvGrpName(NullDefault.getString(grpMdl.getRsgName(), ""));
                //施設区分 */
                int rskSid = grpMdl.getRskSid();
                ret.setPdfRsvKbn(rskSid);
                //施設区分名称 */
                ret.setPdfRsvDspKbn(NullDefault.getString(grpMdl.getRskName(), ""));
                //施設名称
                sisName = NullDefault.getString(grpMdl.getRsdName(), "");
                ret.setPdfRsvName(sisName);
                //資産管理番号
                ret.setPdfRsvSisNum(NullDefault.getString(grpMdl.getRsdSnum(), ""));

                //可変項目
                switch (rskSid) {
                //部屋
                case GSConstReserve.RSK_KBN_HEYA:
                    //座席数
                    ret.setPdfRsvSeatNum(NullDefault.getString(grpMdl.getRsdProp1Value(), ""));
                    //喫煙
                    ret.setPdfRsvSmoking(
                            NullDefault.getStringZeroLength(
                                    grpMdl.getRsdProp2Value(),
                                    String.valueOf(GSConstReserve.PROP_KBN_KA)));
                    break;
                //物品
                case GSConstReserve.RSK_KBN_BUPPIN:
                    //個数
                    ret.setPdfRsvNum(NullDefault.getString(grpMdl.getRsdProp1Value(), ""));
                    //社外持出し
                    ret.setPdfRsvTakeOut(
                            NullDefault.getStringZeroLength(
                                    grpMdl.getRsdProp3Value(),
                                    String.valueOf(GSConstReserve.PROP_KBN_KA)));
                    break;
                //車
                case GSConstReserve.RSK_KBN_CAR:
                    //乗員数
                    ret.setPdfRsvCarSeatNum(NullDefault.getString(grpMdl.getRsdProp1Value(), ""));
                    //喫煙
                    ret.setPdfRsvSmoking(
                            NullDefault.getStringZeroLength(
                                    grpMdl.getRsdProp2Value(),
                                    String.valueOf(GSConstReserve.PROP_KBN_KA)));
                    //ナンバー
                    ret.setPdfRsvCarNumber(NullDefault.getString(grpMdl.getRsdProp4Value(), ""));
                    break;
                //書籍
                case GSConstReserve.RSK_KBN_BOOK:
                    //冊数
                    ret.setPdfRsvBookNum(NullDefault.getString(grpMdl.getRsdProp1Value(), ""));
                    //社外持出し
                    ret.setPdfRsvTakeOut(
                            NullDefault.getStringZeroLength(
                                    grpMdl.getRsdProp3Value(),
                                    String.valueOf(GSConstReserve.PROP_KBN_KA)));
                    //ISBN
                    ret.setPdfRsvBookIsbn(NullDefault.getString(grpMdl.getRsdProp5Value(), ""));
                    break;
                default:
                    break;
                }

                //予約可能期限
                ret.setPdfRsvKikan(NullDefault.getString(grpMdl.getRsdProp6Value(), ""));
                //重複登録
                ret.setPdfRsvOverRegKbn(
                        NullDefault.getStringZeroLength(
                                grpMdl.getRsdProp7Value(),
                                String.valueOf(GSConstReserve.PROP_KBN_KA)));
                //備考
                ret.setPdfRsvBiko(NullDefault.getString(grpMdl.getRsdBiko(), ""));
            }

            //利用目的
            ret.setPdfRsvObj(NullDefault.getString(yrkEditMdl.getRsyMok(), ""));
            //期間 開始
            String from = UDateUtil.getYymdJ(yrkEditMdl.getRsyFrDate(), reqMdl_)
                    + " " + UDateUtil.getSeparateHMJ(yrkEditMdl.getRsyFrDate(), reqMdl_);
            ret.setPdfRsvFromDate(from);
            //期間 終了
            String to = UDateUtil.getYymdJ(yrkEditMdl.getRsyToDate(), reqMdl_)
                    + " " + UDateUtil.getSeparateHMJ(yrkEditMdl.getRsyToDate(), reqMdl_);
            ret.setPdfRsvToDate(to);
            //内容
            ret.setPdfRsvSubject(NullDefault.getString(yrkEditMdl.getRsyBiko(), ""));
            //編集権限
            ret.setPdfRsvEditKbn(yrkEditMdl.getRsyEdit());
            //公開区分
            ret.setPdfRsvPublicKbn(yrkEditMdl.getRsyPublic());

            //公開区分=所属グループ・ユーザのみ公開 の場合、公開対象を設定する
            if (ret.getPdfRsvPublicKbn() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                String[] sidList = __getPubTargetSidList(paramMdl.getRsv110RsySid());
                CommonBiz cmnBiz = new CommonBiz();
                ret.setPdfRsvPubUsrGrpList(
                        cmnBiz.getUserLabelList(con_, sidList));
            }

            //担当部署
            ret.setPdfRsvBusyo(NullDefault.getString(yrkEditMdl.getRkyBusyo(), ""));
            //担当・使用者名
            ret.setPdfRsvUseName(NullDefault.getString(yrkEditMdl.getRkyName(), ""));
            // 人数
            ret.setPdfRsvUseNum(NullDefault.getString(yrkEditMdl.getRkyNum(), ""));
            // 区分 (0:未設定 1:会議 2:研修 3:その他)
            String strKbn = "";
            switch (yrkEditMdl.getRkyUseKbn()) {
            case GSConstReserve.RSY_USE_KBN_NOSET:
                strKbn = gsMsg.getMessage("reserve.use.kbn.noset");
                break;
            case GSConstReserve.RSY_USE_KBN_KAIGI:
                strKbn = gsMsg.getMessage("reserve.use.kbn.meeting");
                break;
            case GSConstReserve.RSY_USE_KBN_KENSYU:
                strKbn = gsMsg.getMessage("reserve.use.kbn.training");
                break;
            case GSConstReserve.RSY_USE_KBN_OTHER:
                strKbn = gsMsg.getMessage("reserve.use.kbn.other");
                break;
            default:
                strKbn = gsMsg.getMessage("reserve.use.kbn.noset");
                break;
            }
            ret.setPdfRsvUseKbn(strKbn);
            // 連絡先
            ret.setPdfRsvContact(NullDefault.getString(yrkEditMdl.getRkyContact(), ""));
            // 会議名案内
            ret.setPdfRsvGuide(NullDefault.getString(yrkEditMdl.getRkyGuide(), ""));
            // 駐車場見込み台数
            ret.setPdfRsvParkNum(NullDefault.getString(yrkEditMdl.getRkyParkNum(), ""));
            // 印刷区
            if (yrkEditMdl.getRkyPrintKbn() == GSConstReserve.RSY_PRINT_KBN_NO) {
                ret.setPdfRsvPrintKbn(gsMsg.getMessage("reserve.print.no"));
            } else if (yrkEditMdl.getRkyPrintKbn() == GSConstReserve.RSY_PRINT_KBN_YES) {
                ret.setPdfRsvPrintKbn(gsMsg.getMessage("reserve.print.yes"));
            }
            // 行き先
            ret.setPdfRsvDest(NullDefault.getString(yrkEditMdl.getRkyDest(), ""));


            //スケジュールプラグイン使用可能フラグ
            //スケジュール使用可能
            if (pconfig.getPlugin(GSConstReserve.PLUGIN_ID_SCHEDULE) != null) {
                ret.setPdfRsvSchUseFlg(GSConst.PLUGIN_USE);
                //同時登録ユーザリストセット
                __setPdfUserList(paramMdl, yrkEditMdl, ret);

                //スケジュール使用不可
            } else {
                ret.setPdfRsvSchUseFlg(GSConst.PLUGIN_NOT_USE);
            }

            //ファイル名
            String fileName = UDateUtil.getYymdJ(yrkEditMdl.getRsyFrDate(), reqMdl_);
            fileName += gsMsg.getMessage("reserve.src.21");
            if (sisName != null) {
                fileName += "_" + sisName;
            }
            fileName += "_" + NullDefault.getString(yrkEditMdl.getRsyMok(), "");
            String encFileName = __fileNameCheck(fileName) + ".pdf";
            ret.setFileName(encFileName);

        }

        return ret;
    }

    /**
     * <br>[機  能] 同時登録ユーザ情報をPDFモデルに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param bean 既存登録データ(編集時)
     * @param pdfMdl PDFモデル
     * @throws SQLException SQL実行時例外
     */
    private void __setPdfUserList(
            Rsv110ParamModel paramMdl,
            Rsv110SisetuModel bean,
            RsvTanPdfModel pdfMdl)
                    throws SQLException {

        //デフォルト表示グループ
        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        String[] users = null;
        //SCD_RSSIDからスケジュールSIDを取得する
        if (bean.getScdRsSid() > 0) {
            int scdSid = getScdSid(bean.getScdRsSid());
            if (scdSid > 0) {

                //スケジュール管理者設定(共有範囲など)を取得
                RsvSchAdmConfModel adminConf = rsvSchBiz.getAdmConfModel(con_);
                ScheduleRsvModel schMdl =
                        getSchData(reqMdl_, scdSid, adminConf, con_);

                if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                    pdfMdl.setPdfRsvSchKbn(GSConstReserve.RSV_SCHKBN_GROUP);
                    CmnGroupmDao dao = new CmnGroupmDao(con_);
                    CmnGroupmModel dMdl = dao.select(schMdl.getScdUsrSid());
                    pdfMdl.setPdfRsvSchGrpName(dMdl.getGrpName());
                } else {
                    pdfMdl.setPdfRsvSchKbn(GSConstReserve.RSV_SCHKBN_USER);
                    if (schMdl != null
                            && (schMdl.getUsrInfList() == null
                            || schMdl.getUsrInfList().isEmpty())
                            && schMdl.getScdGrpSid() == GSConstSchedule.DF_SCHGP_ID) {

                        CmnUsrmInfModel myMdl = new CmnUsrmInfModel();
                        myMdl.setUsrSid(schMdl.getScdUsrSid());
                        myMdl.setUsiSei(schMdl.getScdUsrSei());
                        myMdl.setUsiMei(schMdl.getScdUsrMei());

                        ArrayList<CmnUsrmInfModel> myList =
                                new ArrayList<CmnUsrmInfModel>();

                        myList.add(myMdl);
                        schMdl.setUsrInfList(myList);
                    }

                    ArrayList<String> sv_user_list = new ArrayList<String>();
                    if (schMdl.getUsrInfList() != null) {
                        for (CmnUsrmInfModel uInfMdl : schMdl.getUsrInfList()) {
                            sv_user_list.add(String.valueOf(uInfMdl.getUsrSid()));
                        }
                        users = (String[]) sv_user_list.toArray(new String[sv_user_list.size()]);
                    }
                }
            }
        }

        //追加済みユーザSID
        ArrayList<Integer> list = null;
        ArrayList<CmnUsrmInfModel> selectUsrList = null;
        List<Integer> notAccessUsers = null;
        SchDao schDao = new SchDao(con_);
        notAccessUsers = schDao.getNotRegistUserList(reqMdl_.getSmodel().getUsrsid());

        if (users != null && users.length > 0) {
            list = new ArrayList<Integer>();
            for (String user : users) {
                if (notAccessUsers.indexOf(Integer.parseInt(user)) < 0) {
                    list.add(Integer.parseInt(user));
                }
            }
        }

        UserBiz userBiz = new UserBiz();
        if (list != null && list.size() > 0) {
            selectUsrList = (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con_, list);
        }

        List<String> userNameList = new ArrayList<String>();
        if (selectUsrList != null) {
            for (CmnUsrmInfModel usrInfMdl : selectUsrList) {
                String name = usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei();
                userNameList.add(name);
            }
        }
        pdfMdl.setPdfRsvSchUserList(userNameList);

    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String __fileNameCheck(String fileName) {
        String escName = fileName;

        escName = StringUtilHtml.replaceString(escName, "/", "");
        escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
        escName = StringUtilHtml.replaceString(escName, "?", "");
        escName = StringUtilHtml.replaceString(escName, "*", "");
        escName = StringUtilHtml.replaceString(escName, ":", "");
        escName = StringUtilHtml.replaceString(escName, "|", "");
        escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
        escName = StringUtilHtml.replaceString(escName, "<", "");
        escName = StringUtilHtml.replaceString(escName, ">", "");
        escName = StringUtilHtml.replaceString(escName, ".", "");
        escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }

    /**
     * <br>[機  能]オペレーションログ出力用予約削除内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ格納モデル
     * @return オペレーションログ表示内容
     * @throws SQLException SQL実行エラー
     */
    public String getOpLog(Rsv110ParamModel paramMdl) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv210Model sisetsuJyohoMdl = getGroupData(
                yrkDao.getSisDataSid(paramMdl.getRsv110RsySid()));
        Rsv110SisetuModel sisYrkData = __getYoyakuEditData(paramMdl.getRsv110RsySid());
        if (sisYrkData == null) {
            return "";
        }
        StringBuilder opLog = new StringBuilder();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String sisetu = gsMsg.getMessage("cmn.facility.name");
        String kikann = gsMsg.getMessage("cmn.period");
        String mokuteki = gsMsg.getMessage("reserve.72");
        String naiyou = gsMsg.getMessage("cmn.content");
        opLog.append("[");
        opLog.append(sisetu);
        opLog.append("] ");
        opLog.append(sisetsuJyohoMdl.getRsdName());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(kikann);
        opLog.append("] ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(sisYrkData.getRsyFrDate().getYear()),
                String.valueOf(sisYrkData.getRsyFrDate().getMonth()),
                String.valueOf(sisYrkData.getRsyFrDate().getIntDay()),
                String.valueOf(sisYrkData.getRsyFrDate().getIntHour()),
                String.valueOf(sisYrkData.getRsyFrDate().getIntMinute())
        }));
        opLog.append(" ～ ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(sisYrkData.getRsyToDate().getYear()),
                String.valueOf(sisYrkData.getRsyToDate().getMonth()),
                String.valueOf(sisYrkData.getRsyToDate().getIntDay()),
                String.valueOf(sisYrkData.getRsyToDate().getIntHour()),
                String.valueOf(sisYrkData.getRsyToDate().getIntMinute())
        }));
        opLog.append("\n");
        opLog.append("[");
        opLog.append(mokuteki);
        opLog.append("] ");
        opLog.append(sisYrkData.getRsyMok());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(naiyou);
        opLog.append("] ");
        opLog.append(sisYrkData.getRsyBiko());
        return opLog.toString();
    }

    /**
     * <br>[機  能]オペレーションログ出力用予約登録・編集内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sisetsuSid 施設SID
     * @param paramMdl パラメータ格納モデル
     * @return オペレーションログ表示内容
     * @throws SQLException SQL実行エラー
     */
    public String getTourokuOpLog(int sisetsuSid, Rsv110ParamModel paramMdl)
            throws SQLException {
        //ログ出力準備
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String sisetu = gsMsg.getMessage("cmn.facility.name");
        String kikann = gsMsg.getMessage("cmn.period");
        String mokuteki = gsMsg.getMessage("reserve.72");
        String naiyou = gsMsg.getMessage("cmn.content");
        Rsv070Model sisetuMdl = __getGroupData(sisetsuSid);
        StringBuilder opLog = new StringBuilder();
        opLog.append("[");
        opLog.append(sisetu);
        opLog.append("] ");
        opLog.append(sisetuMdl.getRsdName());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(kikann);
        opLog.append("] ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(paramMdl.getRsv110SelectedYearFr()),
                String.valueOf(paramMdl.getRsv110SelectedMonthFr()),
                String.valueOf(paramMdl.getRsv110SelectedDayFr()),
                String.valueOf(paramMdl.getRsv110SelectedHourFr()),
                String.valueOf(paramMdl.getRsv110SelectedMinuteFr())
        }));
        opLog.append(" ～ ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(paramMdl.getRsv110SelectedYearTo()),
                String.valueOf(paramMdl.getRsv110SelectedMonthTo()),
                String.valueOf(paramMdl.getRsv110SelectedDayTo()),
                String.valueOf(paramMdl.getRsv110SelectedHourTo()),
                String.valueOf(paramMdl.getRsv110SelectedMinuteTo())
        }));
        opLog.append("\n");
        opLog.append("[");
        opLog.append(mokuteki);
        opLog.append("] ");
        opLog.append(paramMdl.getRsv110Mokuteki());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(naiyou);
        opLog.append("] ");
        opLog.append(paramMdl.getRsv110Naiyo());
        return opLog.toString();
    }

    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv110ParamModel
     * @param ctrl 採番用コネクション
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @return 施設予約SID, 施設SID
     * @throws SQLException SQL実行時例外
     */
    public int[] updateYoyakuData(
            Rsv110ParamModel paramMdl, MlCountMtController ctrl, int userSid, String appRootPath)
        throws SQLException {

        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();
        int yoyakuSid = -1;
        int sisetuSid = -1;
        //施設SID
        sisetuSid = paramMdl.getRsv110RsdSid();

        UDate now = new UDate();

        //予約開始
        UDate frDate = new UDate();
        frDate.setDate(paramMdl.getRsv110SelectedYearFr(),
                paramMdl.getRsv110SelectedMonthFr(),
                paramMdl.getRsv110SelectedDayFr());
        frDate.setHour(paramMdl.getRsv110SelectedHourFr());
        frDate.setMinute(paramMdl.getRsv110SelectedMinuteFr());
        frDate.setSecond(GSConstReserve.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //予約終了
        UDate toDate = new UDate();
        toDate.setDate(paramMdl.getRsv110SelectedYearTo(),
                paramMdl.getRsv110SelectedMonthTo(),
                paramMdl.getRsv110SelectedDayTo());
        toDate.setHour(paramMdl.getRsv110SelectedHourTo());
        toDate.setMinute(paramMdl.getRsv110SelectedMinuteTo());
        toDate.setSecond(GSConstReserve.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con_, reqMdl_);

        int rsvSchGrpSid = NullDefault.getInt(paramMdl.getRsv110SchGroupSid(), -1);
        String[] users = paramMdl.getSv_users();
        
        //公開区分が対象ユーザ・グループのみ公開の時、削除済みユーザは設定出来ないので
        //リストから除外する。
        if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP
                && paramMdl.getRsv110PubUsrGrpSid().length > 0) {
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con_);
            UserBiz userBiz = new UserBiz();

            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            List<String> usrSids = new ArrayList<String>();
            for (String target : paramMdl.getRsv110PubUsrGrpSid()) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
            ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
            ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();
            //グループ存在チェック
            if (!grpSids.isEmpty()) {
                glist = gdao.selectGroupNmListOrderbyClass(grpSids);
            }
            //ユーザ存在チェック
            if (!usrSids.isEmpty()) {
                ulist = userBiz.getBaseUserList(con_,
                                                usrSids.toArray(new String[usrSids.size()]));
            }
            String[] checkPubUsrGrpSid = new String[glist.size() + ulist.size()];
            int i = 0;
            for (GroupModel gMdl : glist) {
                checkPubUsrGrpSid[i] = "G" + gMdl.getGroupSid();
                i++;
            }
            for (BaseUserModel uMdl : ulist) {
                checkPubUsrGrpSid[i] = String.valueOf(uMdl.getUsrsid());
                i++;
            }
            paramMdl.setRsv110PubUsrGrpSid(checkPubUsrGrpSid);
        }

        //新規 or 複写して登録モード
        if (paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
                || paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {

            log__.debug("新規モード or 複写して登録モード");


            boolean schInsertFlg = (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_USER
                                        && users != null && users.length > 0)
                                || (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP
                                        && rsvSchGrpSid >= 0);
            int rsSid = -1;
            RsvSisYrkModel yrkParam = new RsvSisYrkModel();
            yrkParam.setRsySid(yoyakuSid);
            yrkParam.setRsdSid(sisetuSid);
            yrkParam.setRsyMok(NullDefault.getString(paramMdl.getRsv110Mokuteki(), ""));
            yrkParam.setRsyFrDate(frDate);
            yrkParam.setRsyToDate(toDate);
            yrkParam.setRsyBiko(NullDefault.getString(paramMdl.getRsv110Naiyo(), ""));
            yrkParam.setRsyAuid(usrSid);
            yrkParam.setRsyAdate(now);
            yrkParam.setRsyEuid(usrSid);
            yrkParam.setRsyEdate(now);
            yrkParam.setRsyEdit(paramMdl.getRsv110RsyEdit());
            yrkParam.setRsyPublic(paramMdl.getRsv110Public());
            yrkParam.setScdRsSid(rsSid);

            //承認状況
            RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
            rsvCmnBiz.setSisYrkApprData(con_, sisetuSid, yrkParam, userSid);


            IRsvYoyakuRegister.Builder regBld = IRsvYoyakuRegister.simpleRegistBuilder(
                    con_, reqMdl_, ctrl, appRootPath, yrkParam);

            //公開区分が対象ユーザ・グループのみ公開の場合
            if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                regBld.setPubList(
                        Stream.of(paramMdl.getRsv110PubUsrGrpSid())
                        .map(targetSid -> {
                            RsvDataPubModel rdpMdl = new RsvDataPubModel();
                            if (targetSid.startsWith("G")) {
                                rdpMdl.setRdpType(GSConst.TYPE_GROUP);
                                rdpMdl.setRdpPsid(Integer.parseInt(targetSid.substring(1)));
                            } else {
                                rdpMdl.setRdpType(GSConst.TYPE_USER);
                                rdpMdl.setRdpPsid(Integer.parseInt(targetSid));
                            }
                            return rdpMdl;
                        })
                        .collect(Collectors.toList())
                    );
            }

            //施設予約区分別情報登録
            if (_isRskKbnRegCheck(paramMdl.getRsv110SisetuKbn())) {
                RsvSisKyrkModel kyrkMdl = new RsvSisKyrkModel();
                kyrkMdl.setRsySid(yoyakuSid);
                kyrkMdl.setRkyBusyo(paramMdl.getRsv110Busyo());
                kyrkMdl.setRkyName(paramMdl.getRsv110UseName());
                kyrkMdl.setRkyNum(paramMdl.getRsv110UseNum());
                kyrkMdl.setRkyUseKbn(paramMdl.getRsv110UseKbn());
                kyrkMdl.setRkyContact(paramMdl.getRsv110Contact());
                kyrkMdl.setRkyGuide(paramMdl.getRsv110Guide());
                kyrkMdl.setRkyParkNum(paramMdl.getRsv110ParkNum());
                if (RsvCommonBiz.isUsePrintKbn(appRootPath)) {
                    kyrkMdl.setRkyPrintKbn(paramMdl.getRsv110PrintKbn());
                }
                kyrkMdl.setRkyDest(paramMdl.getRsv110Dest());
                kyrkMdl.setRkyAuid(usrSid);
                kyrkMdl.setRkyAdate(now);
                kyrkMdl.setRkyEuid(usrSid);
                kyrkMdl.setRkyEdate(now);

                regBld.setKyrkMap(Map.of(sisetuSid, kyrkMdl));
            }

            regBld.setUseSch(schInsertFlg);

            //施設予約を登録
            IRsvYoyakuRegister regRsv = regBld.build();
            regRsv.regist();
            yoyakuSid = regRsv.getRsySidMap().values().stream()
                    .findAny()
                    .flatMap(map -> map.values().stream().findAny())
                    .orElse(-1);

            //スケジュールの登録
            if (schInsertFlg) {
                SchDataModel baseSch = __createSchData(frDate, toDate, paramMdl, usrSid, now);
                Set<Integer> userSidSet = new HashSet<>();
                if (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                    baseSch.setScdUsrSid(rsvSchGrpSid);
                    baseSch.setScdUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                    baseSch.setScdTargetGrp(GSConstSchedule.REMINDER_USE_YES);
                } else {
                    int mainTargetSid = Stream.of(users)
                                .findFirst()
                                .map(str -> Integer.parseInt(str))
                                .orElse(-1);
                    userSidSet.addAll(Stream.of(users)
                        .map(Integer::parseInt)
                        .filter(sid -> sid != mainTargetSid)
                        .collect(Collectors.toSet()));
                    baseSch.setScdUsrSid(mainTargetSid);
                    baseSch.setScdUsrKbn(GSConstSchedule.USER_KBN_USER);
                }
                ISchRegister.Builder regSchBld = ISchRegister.simpleRegistBuilder(
                        con_, reqMdl_, ctrl,
                        baseSch);
                regSchBld.setSchResSidMap(regRsv.getRsyGrpsList().stream()
                        .collect(Collectors.toMap(
                                grp -> grp.getTargetDate().getDateString("/"),
                                grp -> grp.getScdRsSid())));
                if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                    regSchBld.setPubList(
                            Stream.of(paramMdl.getRsv110PubUsrGrpSid())
                            .map(targetSid -> {
                                SchDataPubModel sdpMdl = new SchDataPubModel();
                                if (targetSid.startsWith("G")) {
                                    sdpMdl.setSdpType(GSConst.TYPE_GROUP);
                                    sdpMdl.setSdpPsid(Integer.parseInt(targetSid.substring(1)));
                                } else {
                                    sdpMdl.setSdpType(GSConst.TYPE_USER);
                                    sdpMdl.setSdpPsid(Integer.parseInt(targetSid));
                                }
                                return sdpMdl;
                            })
                            .collect(Collectors.toList())
                        );
                }
                regSchBld.setUseRsv(true);
                regSchBld.setUsers(userSidSet);
                ISchRegister regSch = regSchBld.build();
                regSch.regist();
            }

        //編集
        } else if (paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {

            log__.debug("編集モード");

            //予約SID
            yoyakuSid = paramMdl.getRsv110RsySid();

            //スケジュールへ変更を反映するか？
            boolean editSchedule
                = paramMdl.getRsv110ScdReflection() == GSConstReserve.SCD_REFLECTION_OK;

            int oldRsSid = paramMdl.getRsv110ScdRsSid();
            int rsSid = oldRsSid;
            if (editSchedule && rsSid <= 0
                    && (
                        (users != null && users.length > 0)
                        || (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP
                                && rsvSchGrpSid >= 0)
                    )
                ) {
                //リレーションSID採番
                rsSid =
                    (int) ctrl.getSaibanNumber(
                            SaibanModel.SBNSID_SCHEDULE,
                            SaibanModel.SBNSID_SUB_SCH_RES,
                            usrSid);
            }


            //施設SID
            Rsv110SisetuModel yrkMdl = __getYoyakuEditData(paramMdl);

            if (yrkMdl != null) {
                sisetuSid = yrkMdl.getRsdSid();
            }

            RsvSisYrkModel yrkParam = new RsvSisYrkModel();
            yrkParam.setRsySid(yoyakuSid);
            yrkParam.setRsyMok(NullDefault.getString(paramMdl.getRsv110Mokuteki(), ""));
            yrkParam.setRsyFrDate(frDate);
            yrkParam.setRsyToDate(toDate);
            yrkParam.setRsyBiko(NullDefault.getString(paramMdl.getRsv110Naiyo(), ""));
            yrkParam.setRsyEuid(usrSid);
            yrkParam.setRsyEdate(now);
            yrkParam.setRsyEdit(paramMdl.getRsv110RsyEdit());
            yrkParam.setRsyPublic(paramMdl.getRsv110Public());
            yrkParam.setScdRsSid(rsSid);

            //変更前の公開対象を削除
            RsvDataPubDao rdpDao = new RsvDataPubDao(con_);
            rdpDao.delete(yoyakuSid);

            //公開区分が対象ユーザ・グループのみ公開の場合
            if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                RsvDataPubModel rdpMdl = new RsvDataPubModel();
                String[] sidList = paramMdl.getRsv110PubUsrGrpSid();
                for (int i = 0; i < sidList.length; i++) {
                    if (sidList[i].substring(0, 1).equals("G")) {
                        rdpMdl.setRdpPsid(Integer.parseInt(sidList[i].substring(1)));
                        rdpMdl.setRdpType(GSConst.TYPE_GROUP);
                    } else {
                        rdpMdl.setRdpPsid(Integer.parseInt(sidList[i]));
                        rdpMdl.setRdpType(GSConst.TYPE_USER);
                    }
                    rdpMdl.setRsySid(yoyakuSid);
                    rdpDao.insert(rdpMdl);
                }
            }

            //承認状況
            RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
            rsvCmnBiz.setSisYrkApprData(con_, sisetuSid, yrkParam, userSid);

            yrkDao.updateYoyakuData(yrkParam);

            //施設予約区分別情報登録
            if (_isRskKbnRegCheck(paramMdl.getRsv110SisetuKbn())) {
                RsvSisKyrkModel kyrkMdl = new RsvSisKyrkModel();
                kyrkMdl.setRsySid(yoyakuSid);
                kyrkMdl.setRkyBusyo(paramMdl.getRsv110Busyo());
                kyrkMdl.setRkyName(paramMdl.getRsv110UseName());
                kyrkMdl.setRkyNum(paramMdl.getRsv110UseNum());
                kyrkMdl.setRkyUseKbn(paramMdl.getRsv110UseKbn());
                kyrkMdl.setRkyContact(paramMdl.getRsv110Contact());
                kyrkMdl.setRkyGuide(paramMdl.getRsv110Guide());
                kyrkMdl.setRkyParkNum(paramMdl.getRsv110ParkNum());
                if (RsvCommonBiz.isUsePrintKbn(appRootPath)) {
                    kyrkMdl.setRkyPrintKbn(paramMdl.getRsv110PrintKbn());
                }
                kyrkMdl.setRkyDest(paramMdl.getRsv110Dest());
                kyrkMdl.setRkyEuid(usrSid);
                kyrkMdl.setRkyEdate(now);

                RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
                int cnt = kyrkDao.update(kyrkMdl);
                if (cnt == 0) {
                    kyrkMdl.setRkyAuid(usrSid);
                    kyrkMdl.setRkyAdate(now);
                    kyrkDao.insert(kyrkMdl);
                }
            }

            int scdGpSid = -1;
            List<String> addUsers = new ArrayList<String>();
            List<String> delUsers = new ArrayList<String>();
            /** 同時登録ユーザ 既存スケジュールデータ*/
            Map<Integer, RsvScdOperationModel> svUsersScdMap = new HashMap<>();
            IPushServiceOperator psOpe = PushServiceOperator.getInstance(
                    con_, reqMdl_.getDomain());

            //スケジュールと関連付いている & 変更を反映
            if (editSchedule) {

                /** リマインダー設定モード*/
                SchEnumRemindMode remindMode;

                /** リマインダー設定デフォルト設定*/
                Map<Integer, SchPriPushModel> priMap = new HashMap<Integer, SchPriPushModel>();


                if (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                    if (rsvSchGrpSid >= 0) {
                        addUsers.add(String.valueOf(rsvSchGrpSid));
                    }
                    remindMode = SchEnumRemindMode.GROUP;
                    priMap = schCmnBiz.getGroupPriConf(rsvSchGrpSid);
                } else {
                    if (users != null && users.length > 0) {
                        for (String usr : users) {
                            addUsers.add(usr);
                        }
                    }
                    remindMode = SchEnumRemindMode.OTHERS;
                    priMap = schCmnBiz.getUserPriConf(users);

                }

                //添付ファイル更新用
                RsvScdOperationDao opDao = new RsvScdOperationDao(con_);
                SchBinDao schBinDao = new SchBinDao(con_);
                String[] schBinSidList = null;

                //既存のスケジュールを編集する
                if (oldRsSid > 0) {
                    ArrayList<RsvScdOperationModel> retArray =
                        opDao.selectSchList(oldRsSid);

                    ArrayList<RsvScdOperationModel> updArray =
                        new ArrayList<RsvScdOperationModel>();

                    svUsersScdMap =
                            retArray.stream()
                            .collect(
                                    Collectors.toMap(RsvScdOperationModel::getScdUsrSid, e -> e));

                    //更新するユーザSIDリスト
                    List<String> usrSidList = new ArrayList<String>();
                    //更新対象スケジュールのスケジュール拡張SID
                    int sceSid = opDao.selectSceSid(oldRsSid);
                    //更新するスケジュールSIDリスト
                    List<Integer> updScdSidList = new ArrayList<Integer>();

                    if (!retArray.isEmpty()) {
                        //添付情報SIDを取得しておく
                        schBinSidList = schBinDao.getBinSids(retArray.get(0).getScdSid());

                        SchDao schDao = new SchDao(con_);
                        List<Integer> notAccessList = new ArrayList<Integer>();
                        if (retArray.get(0).getScdUsrKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                            notAccessList = schDao.getNotRegistGrpList(usrSid);

                        } else {
                            notAccessList = schDao.getNotRegistUserList(usrSid);
                        }

                        UDate frUd = new UDate();
                        frUd.setYear(paramMdl.getRsv110SelectedYearFr());
                        frUd.setMonth(paramMdl.getRsv110SelectedMonthFr());
                        frUd.setDay(paramMdl.getRsv110SelectedDayFr());
                        frUd.setHour(paramMdl.getRsv110SelectedHourFr());
                        frUd.setMinute(paramMdl.getRsv110SelectedMinuteFr());
                        frUd.setSecond(GSConstReserve.DAY_START_SECOND);
                        frUd.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                        UDate toUd = new UDate();
                        toUd.setYear(paramMdl.getRsv110SelectedYearTo());
                        toUd.setMonth(paramMdl.getRsv110SelectedMonthTo());
                        toUd.setDay(paramMdl.getRsv110SelectedDayTo());
                        toUd.setHour(paramMdl.getRsv110SelectedHourTo());
                        toUd.setMinute(paramMdl.getRsv110SelectedMinuteTo());
                        toUd.setSecond(GSConstReserve.DAY_START_SECOND);
                        toUd.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                        //編集前スケジュールリスト(添付ファイル削除用)
                        List<Integer> oldScdSidList = new ArrayList<Integer>();

                        //既存登録データに対して更新する値を設定
                        for (RsvScdOperationModel mdl : retArray) {
                            oldScdSidList.add(mdl.getScdSid());
                            //アクセス不可グループ or ユーザ を更新対象から除外する
                            if (notAccessList.indexOf(mdl.getScdUsrSid()) >= 0) {
                                svUsersScdMap.remove(mdl.getScdUsrSid());
                                continue;
                            }

                            String updateUsrSid = String.valueOf(mdl.getScdUsrSid());
                            if (addUsers.indexOf(updateUsrSid) >= 0) {
                                updScdSidList.add(mdl.getScdSid());
                                usrSidList.add(String.valueOf(mdl.getScdUsrSid()));
                                mdl.setScdFrDate(frUd);
                                mdl.setScdToDate(toUd);
                                mdl.setScdTitle(
                                        NullDefault.getString(paramMdl.getRsv110Mokuteki(), ""));
                                mdl.setScdValue(
                                        NullDefault.getString(paramMdl.getRsv110Naiyo(), ""));

                                scdGpSid = mdl.getScdGrpSid();
                                if (scdGpSid <= 0 && users != null && users.length > 1
                                        && paramMdl.getRsv110SchKbn()
                                            != GSConstReserve.RSV_SCHKBN_GROUP) {
                                    scdGpSid =
                                        (int) ctrl.getSaibanNumber(
                                                SaibanModel.SBNSID_SCHEDULE,
                                                SaibanModel.SBNSID_SUB_SCH_GP,
                                                usrSid);
                                }
                                mdl.setScdGrpSid(scdGpSid);
                                mdl.setScdEuid(usrSid);
                                mdl.setScdEdate(now);
                                mdl.setScdEdit(RsvScheduleBiz.getScdEditKbn(
                                        paramMdl.getRsv110RsyEdit()));
                                mdl.setScdPublic(RsvScheduleBiz.getScdPublicKbn(
                                        paramMdl.getRsv110Public(), paramMdl.getRsv110SchKbn()));

                                //リマインダー設定
                                SchRemindConfWriter.builder()
                                .setRemindMode(remindMode)
                                .setDefConf(null)
                                .setOldConf(mdl)
                                .setPushUseable(psOpe.isUseable())
                                .build().write(mdl);

                                updArray.add(mdl);
                                addUsers.remove(addUsers.indexOf(updateUsrSid));
                            } else {
                                delUsers.add(updateUsrSid);
                            }
                        }

                        //PUSH通知リスト削除
                        List<Integer> delScdSidList =
                                svUsersScdMap.values().stream()
                                .map(RsvScdOperationModel::getScdSid)
                                .collect(Collectors.toList());
                        schCmnBiz.deletePushList(delScdSidList);

                        //旧スケジュールに紐づく添付ファイルの削除(SCH_BINFのみ)
                        schBinDao.delete(oldScdSidList);

                        //スケジュール_公開対象削除
                        opDao.deleteSdpData(delScdSidList);

                        if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                            for (int scdSid : updScdSidList) {
                                __setSdpMdl(paramMdl, scdSid, GSConstReserve.PROC_MODE_EDIT);
                            }
                        }

                        //更新
                        opDao.updateRsvToScdTi(updArray, scdGpSid);

                        //削除
                        if (!delUsers.isEmpty()) {
                            opDao.deleteScdTiWithUsers(
                                    oldRsSid, delUsers.toArray(new String[delUsers.size()]));
                        }

                        //変更後、スケジュール拡張に紐付くスケジュールが存在しない場合
                        //スケジュール拡張情報を削除する
                        if (opDao.selectExDataCnt(sceSid) == 0) {
                            //スケジュール拡張情報_添付情報削除
                            SchExdataBinDao exBinDao = new SchExdataBinDao(con_);
                            exBinDao.deleteTempFile(Set.of(sceSid));

                            //スケジュール拡張情報_公開対象削除
                            opDao.deleteExPubData(sceSid);

                            //スケジュール拡張データ削除
                            opDao.deleteExData(sceSid);
                        }

                    }
                    //更新PUSH通知リスト登録
                    for (RsvScdOperationModel mdl : updArray) {
                        if (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                            schCmnBiz.insertPushInfGroup(
                                    mdl.getScdSid(),
                                    priMap,
                                    frDate,
                                    mdl.getScdTargetGrp());
                            continue;
                        }
                        SchPriPushModel plMdl = new SchPriPushModel();
                        plMdl.setUsrSid(mdl.getScdUsrSid());
                        plMdl.setSccReminder(mdl.getScdReminder());
                        schCmnBiz.insertPushInfUser(
                                mdl.getScdSid(),
                                plMdl,
                                frDate);
                    }

                }

                //追加されたスケジュールを登録する
                if (!addUsers.isEmpty()) {

                    SchDataModel baseSch = __createSchData(frDate, toDate, paramMdl, usrSid, now);
                    Set<Integer> userSidSet = new HashSet<>();
                    if (paramMdl.getRsv110SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                        baseSch.setScdUsrSid(rsvSchGrpSid);
                        baseSch.setScdUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                        baseSch.setScdTargetGrp(GSConstSchedule.REMINDER_USE_YES);
                    } else {
                        int mainTargetSid = addUsers.stream()
                                    .findFirst()
                                    .map(str -> Integer.parseInt(str))
                                    .orElse(-1);
                        userSidSet.addAll(addUsers.stream()
                            .map(Integer::parseInt)
                            .filter(sid -> sid != mainTargetSid)
                            .collect(Collectors.toSet()));
                        baseSch.setScdUsrSid(mainTargetSid);
                        baseSch.setScdUsrKbn(GSConstSchedule.USER_KBN_USER);
                    }
                    ISchRegister.Builder regSchBld = ISchRegister.simpleRegistBuilder(
                            con_, reqMdl_, ctrl,
                            baseSch);
                    regSchBld.setUseRsv(true);
                    regSchBld.setSchResSidMap(Map.of(frDate.getDateString("/"), rsSid));

                    if (paramMdl.getRsv110Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                        regSchBld.setPubList(
                                Stream.of(paramMdl.getRsv110PubUsrGrpSid())
                                .map(targetSid -> {
                                    SchDataPubModel sdpMdl = new SchDataPubModel();
                                    if (targetSid.startsWith("G")) {
                                        sdpMdl.setSdpType(GSConst.TYPE_GROUP);
                                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid.substring(1)));
                                    } else {
                                        sdpMdl.setSdpType(GSConst.TYPE_USER);
                                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid));
                                    }
                                    return sdpMdl;
                                })
                                .collect(Collectors.toList())
                            );
                    }
                    regSchBld.setUsers(userSidSet);
                    if (scdGpSid > 0) {
                        regSchBld.setOldSchGrpSid(scdGpSid);
                    }

                    ISchRegister regSch = regSchBld.build();
                    regSch.regist();
                }

                if (schBinSidList != null && schBinSidList.length > 0) {
                    ArrayList<RsvScdOperationModel> newRetArray =
                        opDao.selectSchList(rsSid);
                    if (newRetArray.size() > 0) {
                        //スケジュール添付情報の登録
                        for (RsvScdOperationModel mdl : newRetArray) {
                            for (String binSid : schBinSidList) {
                                SchBinModel binMdl = new SchBinModel();
                                binMdl.setScdSid(mdl.getScdSid());
                                binMdl.setBinSid(Long.parseLong(binSid));
                                schBinDao.insert(binMdl);
                            }
                        }
                    } else if (schBinSidList.length > 0) {
                        //編集後、スケジュール同時登録が無ければ添付情報を論理削除する。
                        CmnBinfDao cmnBinfDao = new CmnBinfDao(con_);
                        CmnBinfModel mdl = new CmnBinfModel();
                        mdl.setBinUpuser(userSid);
                        mdl.setBinUpdate(new UDate());
                        mdl.setBinJkbn(GSConst.JTKBN_DELETE);
                        List<Long> binSidList = new ArrayList<Long>();
                        for (String binSid : schBinSidList) {
                            binSidList.add(Long.parseLong(binSid));
                        }
                        cmnBinfDao.updateJKbn(mdl, binSidList);
                    }
                }
            }
        }

        return new int[]{yoyakuSid, sisetuSid};
    }

    /**
     * <br>[機  能] 登録モデルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 開始日時
     * @param toDate 終了日時
     * @param paramMdl Rsv110ParamModel
     * @param usrSid セッションユーザSID
     * @param now 現在日時
     * @return 登録モデル
     * @throws SQLException
     */
    private SchDataModel __createSchData(
            UDate frDate, UDate toDate, Rsv110ParamModel paramMdl,
                                            int usrSid, UDate now) throws SQLException {
        //
        final SchDataModel schMdl = new SchDataModel();


        schMdl.setScdDaily(GSConstSchedule.TIME_EXIST);
        schMdl.setScdFrDate(frDate);
        schMdl.setScdToDate(toDate);
        schMdl.setScdBgcolor(GSConstSchedule.DF_BG_COLOR);
        schMdl.setScdTitle(NullDefault.getString(paramMdl.getRsv110Mokuteki(), ""));
        schMdl.setScdValue(NullDefault.getString(paramMdl.getRsv110Naiyo(), ""));
        schMdl.setScdBiko("");
        schMdl.setScdAuid(usrSid);
        schMdl.setScdAdate(now);
        schMdl.setScdEuid(usrSid);
        schMdl.setScdEdate(now);
        schMdl.setScdEdit(RsvScheduleBiz.getScdEditKbn(paramMdl.getRsv110RsyEdit()));
        schMdl.setScdPublic(RsvScheduleBiz.getScdPublicKbn(
                paramMdl.getRsv110Public(), paramMdl.getRsv110SchKbn()));

        //拡張登録SID
        schMdl.setSceSid(-1);
        schMdl.setScdGrpSid(-1);
        schMdl.setScdRsSid(-1);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con_, reqMdl_);
        schCmnBiz.getUserPriConf(new String[] {String.valueOf(usrSid)})
            .values().stream()
            .findAny()
            .ifPresent(push -> {
                schMdl.setScdReminder(push.getSccReminder());
            });

        return schMdl;
    }

    /**
     * <br>[機  能] ショートメールで通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv110ParamModel
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig PluginConfig
     * @param entryUserSid 登録ユーザSID
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Rsv110ParamModel paramMdl,
        MlCountMtController cntCon,
        int userSid,
        String appRootPath,
        String tempDir,
        PluginConfig pluginConfig,
        int entryUserSid) throws Exception {

        //施設予約表示モデル(ショートメール送信用)
        ReserveSmlModel rsvModel = new ReserveSmlModel();

        UDate now = new UDate();
        String strNow = now.getDateString();
        Rsv110SisetuModel model = __getYoyakuEditData(paramMdl);
        rsvModel.setRsvSid(model.getRsdSid());
        rsvModel.setRsvMokuteki(model.getRsyMok());
        rsvModel.setRsvNaiyou(model.getRsyBiko());
        rsvModel.setUserSid(model.getRsyAuid());
        rsvModel.setRsvFrDate(model.getRsyFrDate());
        rsvModel.setRsvToDate(model.getRsyToDate());
        rsvModel.setRsvAdate(model.getRsyEdate());
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        rsvModel.setRsvUrl(rsvCmnBiz.createReserveUrl(reqMdl_,
                                            paramMdl.getRsv110RsySid(),
                                            Integer.parseInt(GSConstReserve.PROC_MODE_EDIT),
                                            strNow));
        rsvModel.setEditModeFlg(0);
        //送信
        sendSmail(con_, cntCon, rsvModel, appRootPath, pluginConfig, reqMdl_, entryUserSid);

    }

    /**
     * <br>[機  能] 予約情報登録者ユーザSID取得。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv110ParamModel
     * @return int ユーザSID
     * @throws Exception 実行例外
     */
    public int getEntryUserSid(
        Rsv110ParamModel paramMdl) throws Exception {

        int entryUserSid = -1;
        Rsv110SisetuModel model = __getYoyakuEditData(paramMdl);

        entryUserSid = model.getRsyAuid();
        return entryUserSid;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv110ParamModel
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    private Rsv110SisetuModel __getYoyakuEditData(Rsv110ParamModel paramMdl) throws SQLException {
        return __getYoyakuEditData(paramMdl.getRsv110RsySid());
    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setUserCombo(
            Connection con,
            Rsv110ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        String[] selectUserSid = paramMdl.getRsv110PubUsrGrpSid();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }

        //デフォルトユーザを設定
        if (paramMdl.getRsv110PubDefUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getRsv110PubDefUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getRsv110PubDefUsrSid()));
            }
            paramMdl.setRsv110PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }
    }

    /**
     * <br>[機  能] スケジュールの公開対象を削除、登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param scdSid スケジュールSID
     * @param cmdMode 編集区分
     * @throws SQLException SQL実行時例外
     */
    protected void __setSdpMdl(Rsv110ParamModel paramMdl,
            int scdSid, String cmdMode) throws SQLException {

        RsvScdOperationDao rsoDao = new RsvScdOperationDao(con_);
        RsvSchDataPubModel rdpMdl = new RsvSchDataPubModel();
        String[] sidList = paramMdl.getRsv110PubUsrGrpSid();

        //編集時は削除を行う
        if (cmdMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            rsoDao.deleteSdpData(scdSid);
        }

        for (int i = 0; i < sidList.length; i++) {
            if (sidList[i].substring(0, 1).equals("G")) {
                rdpMdl.setSdpPsid(Integer.parseInt(sidList[i].substring(1)));
                rdpMdl.setSdpType(GSConst.TYPE_GROUP);
            } else {
                rdpMdl.setSdpPsid(Integer.parseInt(sidList[i]));
                rdpMdl.setSdpType(GSConst.TYPE_USER);
            }
            rdpMdl.setScdSid(scdSid);
            rsoDao.insert(rdpMdl);
        }
    }


    /**
     * <br>[機  能] 指定した施設予約情報の公開対象ユーザ/グループSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySid 施設予約SID
     * @return 公開対象ユーザ/グループSID
     * @throws SQLException SQL実行時例外
     */
    private String[] __getPubTargetSidList(int rsySid) throws SQLException {
        RsvDataPubDao ripDao = new RsvDataPubDao(con_);
        ArrayList<String> usrGrpList = new ArrayList<String>();
        List<RsvDataPubModel> ripList = ripDao.select(rsySid);
        for (RsvDataPubModel ripMdl : ripList) {
            if (ripMdl.getRdpType() == GSConst.TYPE_GROUP) {
                usrGrpList.add("G" + String.valueOf(ripMdl.getRdpPsid()));
            } else {
                usrGrpList.add(String.valueOf(ripMdl.getRdpPsid()));
            }
        }

        return usrGrpList.toArray(new String[usrGrpList.size()]);
    }
}