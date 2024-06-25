package jp.groupsession.v2.rsv.rsv111;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvDateSearchModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.ScheduleRsvModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.rsv.rsv110.Rsv110ParamModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110SisetuModel;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 施設予約 施設予約拡張登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv111Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv111Biz.class);
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
    public Rsv111Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param pconfig プラグイン情報
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void setInitData(Rsv111ParamModel paramMdl,
            PluginConfig pconfig, String appRootPath)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        //印刷区分使用フラグの設定
        paramMdl.setRsvPrintUseKbn(RsvCommonBiz.getPrintUseKbn(appRootPath));

        String procMode = paramMdl.getRsv110ProcMode();
        int rsdSid = -1;
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        Rsv070Model grpMdl = null;

        //スケジュール閲覧不可のグループ、ユーザを設定
        if (pconfig.getPlugin(GSConstReserve.PLUGIN_ID_SCHEDULE) != null) {
            int sessionUserSid = reqMdl_.getSmodel().getUsrsid();
            SchDao schDao = new SchDao(con_);
            paramMdl.setRsv110SchNotAccessGroupList(schDao.getNotRegistGrpList(sessionUserSid));
        }

        //時間表示間隔を設定
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        paramMdl.setRacHourDiv(rsvBiz.getHourDivision(con_));

        //新規
        if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)) {

            //新規登録者名をセット
            paramMdl.setRsv110Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

            rsdSid = paramMdl.getRsv110RsdSid();

            //施設グループ情報を取得
            grpMdl = __getGroupData(rsdSid);

            //年コンボ設定用 開始年
            String dspDate = paramMdl.getRsv110SinkiDefaultDate();

            //月コンボ設定
            __setKikanCombo(paramMdl);

            //使用者 グループコンボ、ユーザコンボを設定
            __setPubUser(con_, paramMdl, reqMdl_);

            //同時登録ユーザリストセット
            __setUserList(paramMdl, null);

            //コンボ設定用 表示日
            UDate selDate = new UDate();
            selDate.setDate(dspDate);
            //毎年 月
            if (paramMdl.getRsv111RsrMonthOfYearly() <= 0) {
                paramMdl.setRsv111RsrMonthOfYearly(selDate.getMonth());
            }
            //毎年 日
            if (paramMdl.getRsv111RsrDayOfYearly() <= 0) {
                paramMdl.setRsv111RsrDayOfYearly(selDate.getIntDay());
            }


            //登録日付を初期化
            paramMdl.setRsv110AddDate(null);

        //編集 or 複写して登録
        } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)
                || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {

            //ショートメールのリンクから直接遷移した場合(拡張登録のみ)
            if (paramMdl.isSmailSeniFlg()) {
                RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
                int sid = yrkDao.getYrkDataSid(paramMdl.getRsv111RsrRsid());
                paramMdl.setRsv110RsySid(sid);
                paramMdl.setRsvBackPgId("rsv010");
                if (sid <= 0) {
                    paramMdl.setRsvDataFlg(false);
                    return;
                }

            }

            //予約情報取得
            Rsv110SisetuModel yrkEditMdl = getYoyakuEditData(paramMdl);
            Rsv110SisetuModel yrkAddMdl = getYoyakuAddData(paramMdl);

            if (yrkEditMdl != null) {
                //施設予約拡張SIDを取得
                int rsrRsid = paramMdl.getRsv111RsrRsid();

                //複写して登録or施設予約拡張SIDが-1のとき
                if (procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD) || rsrRsid == -1) {
                    //新規登録者名をセット
                    paramMdl.setRsv110Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

                //編集
                } else {
                    //新規登録者
                    paramMdl.setRsv110AuId(yrkAddMdl.getRsyAuid());
                    paramMdl.setRsv110Torokusya(
                            NullDefault.getString(yrkAddMdl.getUsiSei(), "")
                            + "  "
                            + NullDefault.getString(yrkAddMdl.getUsiMei(), ""));
                    paramMdl.setRsv110AddUsrJKbn(yrkAddMdl.getUsrJkbn());
                    paramMdl.setRsv110AddUsrUkoFlg(yrkAddMdl.getUsrUkoFlg());
                    //最終更新者
                    paramMdl.setRsv110EuId(yrkAddMdl.getRsyEuid());
                    paramMdl.setRsv110Koshinsya(
                            NullDefault.getString(yrkEditMdl.getUsiSei(), "")
                            + "  "
                            + NullDefault.getString(yrkEditMdl.getUsiMei(), ""));
                    paramMdl.setRsv110EditUsrJKbn(yrkEditMdl.getUsrJkbn());
                    paramMdl.setRsv110EditUsrUkoFlg(yrkEditMdl.getUsrUkoFlg());
                }

                rsdSid = yrkEditMdl.getRsdSid();

                //施設グループ情報を取得
                grpMdl = __getGroupData(rsdSid);

                if (paramMdl.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
                    //登録日付をセット
                    __setDspDate(paramMdl, yrkAddMdl, GSConstReserve.DSP_USR_ADD);
                    //更新日付をセット
                    __setDspDate(paramMdl, yrkEditMdl, GSConstReserve.DSP_USR_EDIT);
                }


                //拡張項目設定
                if (paramMdl.isSmailSeniFlg()) {
                    RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con_);
                    RsvSisRyrkModel ryrkModel = ryrkDao.select(paramMdl.getRsv111RsrRsid());
                    if (ryrkModel == null) {
                        paramMdl.setRsvDataFlg(false);
                        return;
                    }
                    paramMdl.setRsv111SvUsers(null);
                    __setKakutyoData(paramMdl, procMode, ryrkModel, yrkEditMdl);
                }
                //期間コンボ設定
                __setKikanCombo(paramMdl);

                //使用者 グループコンボ、ユーザコンボを設定
                __setPubUser(con_, paramMdl, reqMdl_);

                //関連するスケジュールデータ存在フラグを設定
                __existSchData(paramMdl, yrkEditMdl);

                //同時登録ユーザリストセット
                __setUserList(paramMdl, yrkEditMdl);
                //毎年 月
                if (paramMdl.getRsv111RsrMonthOfYearly() <= 0) {
                    paramMdl.setRsv111RsrMonthOfYearly(yrkEditMdl.getRsyFrDate().getMonth());
                }
                //毎年 日
                if (paramMdl.getRsv111RsrDayOfYearly() <= 0) {
                    paramMdl.setRsv111RsrDayOfYearly(yrkEditMdl.getRsyFrDate().getIntDay());
                }
            }
        }

        if (grpMdl != null) {
            int rskSid = grpMdl.getRskSid();

            //施設区分毎に入力可能な項目を設定
            __setSisetuHeader(paramMdl, rskSid);

            //施設グループ情報セット
            __setGroupData(paramMdl, grpMdl);
        }

        //週コンボを設定する
        __setWeekLabel(paramMdl);

        //日コンボを設定する
        __setDayLabel(paramMdl);

        //毎年 日コンボを設定する
        paramMdl.setRsv111ExDayOfYearlyList(getDayLabel(false, true));

        //毎年 月コンボを設定する

        //時間マスタ
        RsvAdmConfDao confDao = new RsvAdmConfDao(con_);
        RsvAdmConfModel confModel = confDao.select();
        if (confModel == null) {
            RsvCommonBiz biz = new RsvCommonBiz();
            confModel = biz.setInitAdminnConfModel();
        }

        //午前
        paramMdl.setRsv110AmFrHour(confModel.getRacAmTimeFrH());
        paramMdl.setRsv110AmFrMin(confModel.getRacAmTimeFrM());
        paramMdl.setRsv110AmToHour(confModel.getRacAmTimeToH());
        paramMdl.setRsv110AmToMin(confModel.getRacAmTimeToM());

        //午後
        paramMdl.setRsv110PmFrHour(confModel.getRacPmTimeFrH());
        paramMdl.setRsv110PmFrMin(confModel.getRacPmTimeFrM());
        paramMdl.setRsv110PmToHour(confModel.getRacPmTimeToH());
        paramMdl.setRsv110PmToMin(confModel.getRacPmTimeToM());

        //終日
        paramMdl.setRsv110AllDayFrHour(confModel.getRacAllDayTimeFrH());
        paramMdl.setRsv110AllDayFrMin(confModel.getRacAllDayTimeFrM());
        paramMdl.setRsv110AllDayToHour(confModel.getRacAllDayTimeToH());
        paramMdl.setRsv110AllDayToMin(confModel.getRacAllDayTimeToM());

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

        if (paramMdl.getRsv110RsySid() > 0) {
            paramMdl.setRsvDataFlg(true);
        } else {
            paramMdl.setRsvDataFlg(false);
        }

        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String frDateNameJp = gsMsg.getMessage("reserve.157");
        String toDateNameJp = gsMsg.getMessage("reserve.158");
        String timeNameJp = gsMsg.getMessage("reserve.159");
        //拡張日付時間設定
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
     * <br>[機  能] DBから取得した予約情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param dbMdl DBからの取得値
     * @param dspUsr 表示ユーザ 0:登録者 1:更新者
     */
    private void __setDspDate(
            Rsv111ParamModel paramMdl, Rsv110SisetuModel dbMdl, int dspUsr) {
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
            String textAddDate = gsMsg.getMessage("schedule.src.85");
            paramMdl.setRsv110EditDate(
                    textAddDate + " : "
                    + UDateUtil.getSlashYYMD(dbMdl.getRsyEdate())
                    + " "
                    + UDateUtil.getSeparateHM(dbMdl.getRsyEdate()));
        }
    }

    /**
     * <br>[機  能] 月コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setKikanCombo(Rsv111ParamModel paramMdl)
            throws SQLException {
        //月コンボの設定
        paramMdl.setRsv110MonthComboList(__getMonthCombo());
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
    public Rsv210Model getGroupCheckData(int rsdSid) throws SQLException {

        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        Rsv210Model ret = dataDao.getGroupCheckModel(rsdSid);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    public Rsv110SisetuModel getYoyakuEditData(Rsv111ParamModel paramMdl) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuEditData(paramMdl.getRsv110RsySid());

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    public Rsv110SisetuModel getYoyakuAddData(Rsv111ParamModel paramMdl) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuAddData(paramMdl.getRsv110RsySid());

        return ret;
    }

    /**
     * <br>[機  能] 施設区分に応じて施設のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuHeader(Rsv111ParamModel paramMdl, int rskSid) {
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
     * <br>[機  能] DBから取得した施設グループ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param dbMdl DB取得結果
     */
    private void __setGroupData(Rsv111ParamModel paramMdl,
                                  Rsv070Model dbMdl) {

        //所属グループ名
        paramMdl.setRsv110GrpName(NullDefault.getString(dbMdl.getRsgName(), ""));
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
     * <br>[機  能] 週コンボをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     */
    private void __setWeekLabel(Rsv111ParamModel paramMdl) {

        int week = 1;
        GsMessage gsMsg = new GsMessage(reqMdl_);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.noset2"), String.valueOf(0)));
        for (int i = 0; i < 5; i++) {
            labelList.add(
                    new LabelValueBean(
                            MaintenanceUtil.getWeek(week, reqMdl_),
                            String.valueOf(week)));
            week++;
        }
        paramMdl.setRsv111WeekList(labelList);
    }

    /**
     * <br>[機  能] 日コンボをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     */
    private void __setDayLabel(Rsv111ParamModel paramMdl) {

        paramMdl.setRsv111ExDayList(getDayLabel(true, true));
    }
    /**
     * <br>日コンボを生成します
     * @param settingFlg 未設定ラベル有無
     * @param eomFlg 末日の有無
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayLabel(boolean settingFlg, boolean eomFlg) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        /** メッセージ 日 **/
        String strDay = gsMsg.getMessage("cmn.day");
        //登録日時
        String textNoSet = gsMsg.getMessage("cmn.noset2");
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (settingFlg) {
            labelList.add(new LabelValueBean(textNoSet, String.valueOf(0)));
        }
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + strDay, String.valueOf(day)));
            day++;
        }

        //末日
        if (eomFlg) {
            labelList.add(
                    new LabelValueBean(gsMsg.getMessage("tcd.tcd050kn.01"),
                                    Integer.toString(GSConstCommon.LAST_DAY_OF_MONTH)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 月コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return labelList 月コンボリスト
     */
    private ArrayList<LabelValueBean> __getMonthCombo() {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //1月～12月まででコンボを作成
        GsMessage gsMsg = new GsMessage(reqMdl_);
        for (int i = 1; i <= 12; i++) {
            labelList.add(
                    new LabelValueBean(
                            String.valueOf(i) + gsMsg.getMessage("cmn.month"),
                            String.valueOf(i)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 画面パラメータから予約登録する日付リストを取得する
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用する
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @return HashMap 予約登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, String> getInsertDateList(
            Rsv111ParamModel paramMdl) throws SQLException {

        RsvDateSearchModel model = new RsvDateSearchModel();

        //抽出区分
        model.setDateKbn(paramMdl.getRsv111RsrKbn());
        model.setTranKbn(GSConstReserve.FURIKAE_NO);

        if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_WEEK) {
            __setWeekOfWeekly(paramMdl, model);
            model.setTranKbn(paramMdl.getRsv111RsrTranKbn());

        } else if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_MONTH) {

            if (paramMdl.getRsv111RsrWeek() != 0) {
                __setWeekOfWeekly(paramMdl, model);
                model.setWeekOfMonthly(paramMdl.getRsv111RsrWeek());
            } else {
                model.setDayOfMonthly(paramMdl.getRsv111RsrDay());
            }
            model.setTranKbn(paramMdl.getRsv111RsrTranKbn());
        } else if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_YEAR) {
            model.setDayOfYearly(paramMdl.getRsv111RsrDayOfYearly());
            model.setMonthOfYearly(paramMdl.getRsv111RsrMonthOfYearly());
            model.setTranKbn(paramMdl.getRsv111RsrTranKbn());
        }

        //抽出期間From
        UDate frDate = new UDate();
        frDate.setYear(Integer.parseInt(paramMdl.getRsv111RsrDateYearFr()));
        frDate.setMonth(Integer.parseInt(paramMdl.getRsv111RsrDateMonthFr()));
        frDate.setDay(Integer.parseInt(paramMdl.getRsv111RsrDateDayFr()));
        frDate.setZeroHhMmSs();
        model.setFromDate(frDate);

        //抽出期間From
        UDate toDate = new UDate();
        toDate.setYear(Integer.parseInt(paramMdl.getRsv111RsrDateYearTo()));
        toDate.setMonth(Integer.parseInt(paramMdl.getRsv111RsrDateMonthTo()));
        toDate.setDay(Integer.parseInt(paramMdl.getRsv111RsrDateDayTo()));
        toDate.setZeroHhMmSs();
        model.setToDate(toDate);

        //日付リストを取得
        HashMap<String, String> dateMap = getDateList(model);

        return dateMap;
    }

    /**
     * <br>[機  能] 曜日を抽出用モデルに設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param model 抽出用モデル
     */
    private void __setWeekOfWeekly(Rsv111ParamModel paramMdl, RsvDateSearchModel model) {
        model.setWeekOfWeekly1(paramMdl.getRsv111RsrDweek1());
        model.setWeekOfWeekly2(paramMdl.getRsv111RsrDweek2());
        model.setWeekOfWeekly3(paramMdl.getRsv111RsrDweek3());
        model.setWeekOfWeekly4(paramMdl.getRsv111RsrDweek4());
        model.setWeekOfWeekly5(paramMdl.getRsv111RsrDweek5());
        model.setWeekOfWeekly6(paramMdl.getRsv111RsrDweek6());
        model.setWeekOfWeekly7(paramMdl.getRsv111RsrDweek7());
    }

    /**
     * <br>[機  能] 日付指定条件に該当する日付リストを取得する
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用する
     * <br>[備  考]
     *
     * @param model 抽出条件
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, String> getDateList(RsvDateSearchModel model)
        throws SQLException {

        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int sessionUsrSid = usrMdl.getUsrsid();

        UDate frDate = model.getFromDate();
        UDate toDate = model.getToDate();

        if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_YEAR) {
            if (frDate.getMonth() > model.getMonthOfYearly()
                    || (frDate.getIntDay() > model.getDayOfYearly()
                            && frDate.getMonth() == model.getMonthOfYearly())) {
                frDate.addYear(1);
            }
            frDate.setMonth(model.getMonthOfYearly());
            frDate.setDay(model.getDayOfYearly());
        }

        //営業日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con_);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(frDate, toDate);
        TcdAdmConfModel acMdl = getTcdAdmConfModel(sessionUsrSid);

        HashMap<String, String> dateMap = new HashMap<String, String>();
        UDate setDate = null;

        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //抽出条件に該当するか判定
            if (__isMatchCondition(frDate, model)) {
                //振替判定
                setDate = __getConvertDate(frDate, model, holMap, acMdl);
                dateMap.put(setDate.getDateString(), setDate.getDateString());
                log__.debug("登録日付==>" + setDate.getDateString());
            }
            if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_YEAR) {
                frDate.addYear(1);
            } else {
                frDate.addDay(1);
            }
        }

        return dateMap;
    }

    /**
     * <br>[機  能] タイムカード管理者設定を取得
     * <br>[解  説]
     * <br>[備  考] レコードが存在しない場合デフォルト値で作成
     *
     * @param usrSid ユーザSID
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    public TcdAdmConfModel getTcdAdmConfModel(int usrSid) throws SQLException {

        log__.debug("タイムカード管理者設定取得");

        TcdAdmConfDao dao = new TcdAdmConfDao(con_);
        TcdAdmConfModel model = dao.select();

        if (model == null) {
            boolean commit = false;
            try {
                model = new TcdAdmConfModel(usrSid);
                dao.insert(model);
                commit = true;
            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commit) {
                    con_.commit();
                }
            }
        }
        return model;
    }

    /**
     * <br>[機  能] 抽出条件に該当する日付か判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 判定対象の日付
     * @param model 抽出条件
     * @return boolean true:該当 false:該当しない
     */
    private boolean __isMatchCondition(UDate date, RsvDateSearchModel model) {

        if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_DAY) {
            //毎日
            return true;
        } else if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_WEEK) {
            //毎週
            return __isMatchWeek(date.getWeek(), model);
        } else if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_MONTH) {
            //毎月
            if (model.getWeekOfMonthly() != 0) {
                //週・曜日指定
                int weekNo = model.getWeekOfMonthly();
                if (__isMatchWeek(date.getWeek(), model)) {
                    UDate wkDate = date.cloneUDate();
                    int wkWeekOfMonth
                        = MaintenanceUtil.getAccurateWeekOfMonth(wkDate, wkDate.getWeek());
                    log__.debug("********* wkDate==>" + wkDate.getDateString());
                    log__.debug("********* weekNo==>" + weekNo);
                    log__.debug("********* wkWeekOfMonth==>" + wkWeekOfMonth);
                    if (weekNo == wkWeekOfMonth) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                //日指定
                int monthly = model.getDayOfMonthly();
                return date.getIntDay() == CommonBiz.getExDay(date, monthly);
            }
        } else if (model.getDateKbn() == GSConstReserve.KAKUTYO_KBN_EVERY_YEAR) {
            //毎年
            int yearly = model.getDayOfYearly();
            return (model.getMonthOfYearly() == date.getMonth()
                    && CommonBiz.getExDay(date, yearly) == date.getIntDay());
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定した曜日が選択されているか判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param week 週
     * @param model 抽出条件
     * @return true:指定されている false:指定されていない
     */
    private boolean __isMatchWeek(int week, RsvDateSearchModel model) {

        switch (week) {
            case UDate.SUNDAY:
                if (model.getWeekOfWeekly1() == 0) {
                    return false;
                }
                break;
            case UDate.MONDAY:
                if (model.getWeekOfWeekly2() == 0) {
                    return false;
                }
                break;
            case UDate.TUESDAY:
                if (model.getWeekOfWeekly3() == 0) {
                    return false;
                }
                break;
            case UDate.WEDNESDAY:
                if (model.getWeekOfWeekly4() == 0) {
                    return false;
                }
                break;
            case UDate.THURSDAY:
                if (model.getWeekOfWeekly5() == 0) {
                    return false;
                }
                break;
            case UDate.FRIDAY:
                if (model.getWeekOfWeekly6() == 0) {
                    return false;
                }
                break;
            case UDate.SATURDAY:
                if (model.getWeekOfWeekly7() == 0) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 営業日判定を行い非営業日の場合、
     * <br>         振替設定によって日付をコンバート。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 日付
     * @param model 抽出条件
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @return UDate コンバート結果
     */
    private UDate __getConvertDate(UDate date,
                                    RsvDateSearchModel model,
                                    HashMap<String, CmnHolidayModel> holMap,
                                    TcdAdmConfModel acMdl) {

        UDate ret = date.cloneUDate();
        int tran = model.getTranKbn();
        int addDay = 0;
        if (tran == GSConstReserve.FURIKAE_ATO) {
            addDay = 1;
        } else if (tran == GSConstReserve.FURIKAE_MAE) {
            addDay = -1;
        } else {
            return ret;
        }

        //休日として扱う曜日か判定
        boolean fin = true;
        while (fin) {
            if (__isMatchWeek(ret.getWeek(), acMdl)
                    || holMap.containsKey(ret.getDateString())) {
                ret.addDay(addDay);
            } else {
                fin = false;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param week 週
     * @param acMdl 管理設定
     * @return true:休日曜日　false:休日曜日以外
     */
    private boolean __isMatchWeek(int week, TcdAdmConfModel acMdl) {

        switch (week) {
            case UDate.SUNDAY:
                if (acMdl.getTacHolSun() == 0) {
                    return false;
                }
                break;
            case UDate.MONDAY:
                if (acMdl.getTacHolMon() == 0) {
                    return false;
                }
                break;
            case UDate.TUESDAY:
                if (acMdl.getTacHolTue() == 0) {
                    return false;
                }
                break;
            case UDate.WEDNESDAY:
                if (acMdl.getTacHolWed() == 0) {
                    return false;
                }
                break;
            case UDate.THURSDAY:
                if (acMdl.getTacHolThu() == 0) {
                    return false;
                }
                break;
            case UDate.FRIDAY:
                if (acMdl.getTacHolFri() == 0) {
                    return false;
                }
                break;
            case UDate.SATURDAY:
                if (acMdl.getTacHolSat() == 0) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 施設予約データに対応するスケジュールデータが存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param yrkMdl 施設予約データ
     * @throws SQLException SQL実行時例外
     */
    private void __existSchData(Rsv111ParamModel paramMdl, Rsv110SisetuModel yrkMdl)
        throws SQLException {

        RsvScdOperationDao scdDao = new RsvScdOperationDao(con_);
        if (yrkMdl == null) {
            paramMdl.setRsv111ExistSchDateFlg(false);
            return;
        }
        //スケジュール拡張SID取得
        int sceSid = scdDao.selectSceSid(yrkMdl.getScdRsSid());

        if (sceSid < 1) {
            paramMdl.setRsv111ExistSchDateFlg(false);
            return;
        }

        int exdataCnt = scdDao.selectExDataCnt(sceSid);
        if (exdataCnt < 1) {
            //スケジュール拡張に紐付くスケジュールが無い
            paramMdl.setRsv111ExistSchDateFlg(false);
            return;
        }

        paramMdl.setRsv111ExistSchDateFlg(true);
    }

    /**
     * <br>[機  能] 同時登録ユーザ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv111ParamModel
     * @param bean 既存登録データ(編集時)
     * @throws SQLException SQL実行時例外
     */
    private void __setUserList(Rsv111ParamModel paramMdl, Rsv110SisetuModel bean)
        throws SQLException {

        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        //デフォルト表示グループ
        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        String dfGpSidStr = rsvSchBiz.getDispDefaultGroupSidStr(con_, usrSid);

        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getRsv111GroupSid(), dfGpSidStr);
        paramMdl.setRsv111GroupSid(dspGpSidStr);

        //同時登録スケジュールグループリスト
        paramMdl.setRsv110SchGroupLabel(rsvSchBiz.getSchGroupCombo(con_, reqMdl_, usrSid));
    }


    /**
     * <br>[機  能] 拡張予約データをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111ParamModel
     * @param dspDate 処理日付(yyyyMMdd)
     * @param bean 拡張予約モデル
     * @param yrkMdl 施設予約情報
     * @throws SQLException SQL実行時例外
     */
    private void __setKakutyoData(Rsv111ParamModel paramMdl,
                                    String dspDate,
                                    RsvSisRyrkModel bean,
                                    Rsv110SisetuModel yrkMdl)
    throws SQLException {

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
        paramMdl.setRsv111RsrMonthOfYearly(bean.getRsrMonthYearly());
        //毎年 日
        paramMdl.setRsv111RsrDayOfYearly(bean.getRsrDayYearly());

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
            paramMdl.setRsv111PubUsrGrpSid(sidList);
        }

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

                    if (paramMdl.getRsv111SvUsers() == null) {
                        __setExSaveUsersForDb(paramMdl, schMdl.getUsrInfList());
                    }
                }
            }

        }

        //初期表示フラグ
        paramMdl.setRsv111InitFlg(false);
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
            }

            //対象ユーザ
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                uMdl = uDao.getUserInfoJtkb(scdMdl.getScdUsrSid(), -1);
                if (uMdl != null) {
                    scdMdl.setScdUsrSei(uMdl.getUsiSei());
                    scdMdl.setScdUsrMei(uMdl.getUsiMei());
                    scdMdl.setScdUsrJkbn(cuDao.getUserJkbn(scdMdl.getScdUsrSid()));
                }
            } else {
                scdMdl.setScdUsrSei(
                        getUsrName(
                                reqMdl,
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
     * <br>[機  能] DBに登録されているスケジュール登録ユーザ情報を画面パラメータへ設定する
     * <br>[解  説] 拡張登録のパラメータを設定する
     * <br>[備  考]
     * @param paramMdl Rsv111ParamModel
     * @param list 同時登録ユーザ情報リスト
     */
    private void __setExSaveUsersForDb(Rsv110ParamModel paramMdl,
                              ArrayList<CmnUsrmInfModel> list) {

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
     * <br>[機  能] 公開対象を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setPubUser(
            Connection con, 
            Rsv111ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        String[] selectUserSid = paramMdl.getRsv111PubUsrGrpSid();
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
            paramMdl.setRsv111PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }
    }
}