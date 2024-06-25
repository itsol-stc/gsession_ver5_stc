package jp.groupsession.v2.tcd.tcd010;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardCalcBiz;
import jp.groupsession.v2.tcd.TimecardUtil;
import jp.groupsession.v2.tcd.dao.TcdDatausedSumDao;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.dao.TcdUserGrpDao;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdPriConfModel;
import jp.groupsession.v2.tcd.model.TcdTotalValueModel;
import jp.groupsession.v2.tcd.model.TcdUserGrpModel;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;
import jp.groupsession.v2.tcd.tcd020.Tcd020Biz;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] タイムカード 一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd010Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd010Biz.class);

    /** 画面ID */
    public static final String SCR_ID = "tcd010";

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Tcd010ParamModel paramMdl,
            RequestModel reqMdl, Connection con) throws SQLException {
        log__.debug("タイムカード表示情報取得開始");
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        paramMdl.setTcdBackScreen(GSConstTimecard.TCD_BACKSCREEN_TOP);

        //ユーザ種別を判定 一般・グループ管理者・管理者
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int userKbn = tcBiz.getUserKbn(con, usModel);
        paramMdl.setUsrKbn(String.valueOf(userKbn));

        //計算用ビジネスロジック
        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);

        //グループコンボ
        List<LabelValueBean> groupLabel = null;
        List<UsrLabelValueBean> userLabel = null;
        //ユーザ切替用グループ・ユーザコンボ作成(管理グループ)
        groupLabel = getGroupLabelList(con, sessionUsrSid, userKbn, reqMdl);
        paramMdl.setTcd010GpLabelList(groupLabel);
        int dspGpSid = NullDefault.getInt(
                paramMdl.getSltGroupSid(), getInitGpSid(sessionUsrSid, groupLabel, con));

        //休日区分情報一覧を取得
        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();
        paramMdl.setHolList(holDataList);

        //基本設定を取得
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);

        int csvImportFlg = GSConstTimecard.IMPORT_FLG_NG;

        //ユーザ種別 = 一般ユーザの場合
        if (userKbn == GSConstTimecard.USER_KBN_NORMAL) {
            //選択されたグループが所属グループかを確認
            int dspUserSid = sessionUsrSid;
            if (!StringUtil.isNullZeroString(paramMdl.getUsrSid())) {
                dspUserSid = Integer.parseInt(paramMdl.getUsrSid());
            }
            GroupBiz grpBiz = new GroupBiz();
            if (!grpBiz.isBelongGroup(dspUserSid, dspGpSid, con)) {
                CmnBelongmDao belongDao = new CmnBelongmDao(con);
                dspGpSid = belongDao.selectUserBelongGroupDef(dspUserSid);
            }

            //基本設定を確認
            if (admConf.getTacLockFlg() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockStrike() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockLate() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockHoliday() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockTimezone() == GSConstTimecard.UNLOCK_FLG) {

                csvImportFlg = GSConstTimecard.IMPORT_FLG_OK;
            }
        }

        //ユーザ種別 = グループ管理者の場合、選択されたグループのグループ管理者かを確認
        if (userKbn == GSConstTimecard.USER_KBN_GRPADM) {
            csvImportFlg = GSConstTimecard.IMPORT_FLG_OK;
            boolean grpAdminUser = false;
            for (LabelValueBean grpLabel : groupLabel) {
                if (dspGpSid == Integer.parseInt(grpLabel.getValue())) {
                    grpAdminUser = true;
                    break;
                }
            }
            if (!grpAdminUser) {
                dspGpSid = Integer.parseInt(groupLabel.get(0).getValue());
            }
        }

        //ユーザ種別 = 管理者ユーザの場合
        if (userKbn == GSConstTimecard.USER_KBN_ADMIN) {
            csvImportFlg = GSConstTimecard.IMPORT_FLG_OK;
        }

        paramMdl.setSltGroupSid(String.valueOf(dspGpSid));
        userLabel = getUserLabelList(con, dspGpSid);
        paramMdl.setTcd010UsrLabelList(userLabel);
        paramMdl.setCsvImportFlg(csvImportFlg);

        //表示ユーザ
        String dspUsrSid = "";
        if (StringUtil.isNullZeroStringSpace(paramMdl.getUsrSid())) {
            dspUsrSid = getInitUsrSid(sessionUsrSid, userLabel, con);
        } else {
            dspUsrSid = getInitUsrSid(Integer.parseInt(paramMdl.getUsrSid()), userLabel, con);
        }
        log__.debug("dspUsrSid==>" + dspUsrSid);
        paramMdl.setUsrSid(dspUsrSid);
        paramMdl.setMyselfFlg(paramMdl.getUsrSid().equals(String.valueOf(sessionUsrSid)));


        //勤務表フォーマット登録チェック
        TcdPriConfModel priConf = tcBiz.getTcdPriConfModel(sessionUsrSid, con);
        __checkFormatFile(admConf, priConf, paramMdl, con);

        //表示年月を取得
        UDate sysDate = TimecardBiz.getDspUDate(new UDate(), admConf);
        String strDspDate =
            NullDefault.getString(
                paramMdl.getTcdDspFrom(),
                sysDate.getDateString());
        if (strDspDate.isEmpty()) {
            strDspDate = sysDate.getDateString();
        }
        paramMdl.setTcdDspFrom(strDspDate);
        paramMdl.setYear(strDspDate.substring(0, 4));
        paramMdl.setMonth(strDspDate.substring(4, 6));

        //タイムカード一覧情報を取得
        ArrayList<Tcd010Model> tcd010List = new ArrayList<Tcd010Model>();
        tcd010List = getTimeCardList(paramMdl, Integer.parseInt(dspUsrSid), admConf, con, reqMdl);
        ArrayList<Integer> ttiSidList = new ArrayList<Integer>();
        for (Tcd010Model mdl : tcd010List) {
            ttiSidList.add(mdl.getTtiSid());
        }
        Set<Integer> set = new HashSet<Integer>(ttiSidList);
        ttiSidList.clear();
        ttiSidList.addAll(set);

        paramMdl.setTcd010List(tcd010List);

        //前月・当月の集計値を取得
        TcdTotalValueModel lastValueMdl
            = __getLastTotalValue(paramMdl, reqMdl, sessionUsrSid, admConf, con);
        paramMdl.setLastMonthMdl(lastValueMdl);
        TcdTotalValueModel thisValueMdl
            = __getThisTotalValue(paramMdl, reqMdl, sessionUsrSid, admConf, con);
        paramMdl.setThisMonthMdl(thisValueMdl);

        UDate lastDate = new UDate();
        lastDate.setDate(Integer.parseInt(paramMdl.getYear()),
                        Integer.parseInt(paramMdl.getMonth()), 1);
        paramMdl.setThisMonthString(lastDate.getStrMonth());
        lastDate.addMonth(-1);
        paramMdl.setLastMonthString(lastDate.getStrMonth());
        //不正データ処理
        if (tcBiz.isFailDataExist(Integer.parseInt(dspUsrSid), con, admConf)) {
            paramMdl.setTcd010FailFlg(String.valueOf(GSConstTimecard.DATA_FAIL));
        } else {
            paramMdl.setTcd010FailFlg(String.valueOf(GSConstTimecard.DATA_NOT_FAIL));
        }

        //年間集計値を取得//
        TimecardDao tcddao = new TimecardDao(con);

        //期首月を取得
        paramMdl.setKishuMonth(NullDefault.getString(String.valueOf(tcddao.getKishuMonth()), ""));
        //年度を取得
        paramMdl.setNendYear(getNend(Integer.parseInt(paramMdl.getKishuMonth()), paramMdl));
        UDate nenkan = new UDate();
        nenkan.setDate(Integer.parseInt(paramMdl.getNendYear()),
                Integer.parseInt(paramMdl.getKishuMonth()), 1);
        paramMdl.setKishuMonth(nenkan.getStrMonth());

        UDate fdate = new UDate();
        fdate.setYear(Integer.parseInt(paramMdl.getNendYear()));
        fdate.setMonth(Integer.parseInt(paramMdl.getKishuMonth()));
        calBiz.setTimeCardCal(Integer.parseInt(paramMdl.getNendYear()),
                Integer.parseInt(paramMdl.getKishuMonth()), admConf.getTacSimebi(), fdate);


        UDate date = new UDate();

        //期末月を取得
        nenkan.addMonth(11);
        paramMdl.setKimatuMonth(nenkan.getStrMonth());
        UDate tdate = calBiz.setTimeCardCal(nenkan.getYear(),
                nenkan.getMonth(), admConf.getTacSimebi(), date);
        paramMdl.setEndYear(nenkan.getStrYear());

        //年間のデータを取得
        TcdTotalValueModel yearMdl = new TcdTotalValueModel();
        //計算用ビジネスロジック
        yearMdl = calBiz.getTotalValueModel(
                Integer.parseInt(paramMdl.getUsrSid()), fdate, tdate, sessionUsrSid, con, reqMdl);

        //有給日数情報の取得
        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        int nendo = Integer.parseInt(paramMdl.getNendYear());

        TcdYukyudataModel tydMdl = tydDao.getYukyuData(Integer.parseInt(dspUsrSid), nendo);
        BigDecimal yukyuUseCnt = yearMdl.getYuukyuDays();
        if (tydMdl != null) {
            //有休残数の設定
            BigDecimal yukyuNissuu = tydMdl.getTcyYukyu();
            BigDecimal yukyuZan = yukyuNissuu.subtract(yukyuUseCnt);
            paramMdl.setTcd010YukyuZan(yukyuZan.toString());
        } else {
            GsMessage gsMsg = new GsMessage(reqMdl);
            paramMdl.setTcd010YukyuZan(gsMsg.getMessage("tcd.tcd010.19"));
        }
        paramMdl.setTotalYearMdl(yearMdl);

        //有休使用日数のチェック
        String warnMsg = tcBiz.getYukyuWarnMessage(con, tydMdl, yukyuUseCnt);
        paramMdl.setWarnMessage(warnMsg);

        //月別集計値//
        ArrayList<TcdTotalValueModel> monthTotalList = new ArrayList<TcdTotalValueModel>();
        TcdTotalValueModel monthTotalMdl = new TcdTotalValueModel();
        UDate from = new UDate();
        from.setDate(Integer.parseInt(paramMdl.getNendYear()),
                Integer.parseInt(paramMdl.getKishuMonth()), 1);
        for (int i = 1; i <= 12; i++) {
            from.setDate(from.getYear(),
                    from.getMonth(), 1);
            monthTotalMdl = __getTotalValue(paramMdl, reqMdl, sessionUsrSid, admConf, con, from);
            monthTotalMdl.setKadoMonth(from.getStrMonth());
            monthTotalList.add(monthTotalMdl);
            from.addMonth(1);
        }
        paramMdl.setMonthTtlList(monthTotalList);

        //ロック処理
        if (userKbn == GSConstTimecard.USER_KBN_NORMAL
         && admConf.getTacLockFlg() == GSConstTimecard.LOCK_FLG) {
            paramMdl.setTcd010LockFlg(String.valueOf(GSConstTimecard.LOCK_FLG));
        } else {
            paramMdl.setTcd010LockFlg(String.valueOf(GSConstTimecard.UNLOCK_FLG));
        }
        log__.debug("paramMdl.getTcd010LockFlg()==>" + paramMdl.getTcd010LockFlg());
        log__.debug("paramMdl.getTcd010FailFlg()==>" + paramMdl.getTcd010FailFlg());
    }

    /**
     * <br>[機  能] 勤務表フォーマットの登録/未登録チェックを行う
     * <br>[解  説] 登録：Excel出力する 未登録：個人設定にしたがって設定
     * <br>[備  考]
     * @param admConf 基本設定
     * @param priConf 個人設定
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __checkFormatFile(TcdAdmConfModel admConf, TcdPriConfModel priConf,
            Tcd010ParamModel paramMdl, Connection con) throws SQLException {

        if (admConf.getTacWorkreportKbn() == 0) {
            if (priConf.getTpcKinmuOut() == GSConstTimecard.KINMU_PDF) {
                paramMdl.setKinmuOut(String.valueOf(GSConstTimecard.KINMU_PDF));
            } else {
                paramMdl.setKinmuOut(String.valueOf(GSConstTimecard.KINMU_EXCEL));
            }
        } else {
            paramMdl.setKinmuOut(String.valueOf(GSConstTimecard.KINMU_EXCEL));
        }
    }

    /**
     * <br>[機  能] 前月のタイムカード集計値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @param admConf 基本設定
     * @param con コネクション
     * @return TcdTotalValueModel 集計値モデル
     * @throws SQLException SQL実行時例外
     */
    private TcdTotalValueModel __getLastTotalValue(
            Tcd010ParamModel paramMdl,
            RequestModel reqMdl,
            int sessionUsrSid,
            TcdAdmConfModel admConf,
            Connection con) throws SQLException {

        //計算用ビジネスロジック
        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);
        int usrSid = Integer.parseInt(paramMdl.getUsrSid());
        int year = Integer.parseInt(paramMdl.getYear());
        int month = Integer.parseInt(paramMdl.getMonth());
        if (month == 1) {
            year--;
            month = 12;
        } else {
            month--;
        }
        UDate fdate = new UDate();
        UDate tdate = calBiz.setTimeCardCal(year, month, admConf.getTacSimebi(), fdate);
        TcdTotalValueModel ret
            = calBiz.getTotalValueModel(usrSid, fdate, tdate, sessionUsrSid, con, reqMdl);
        return ret;
    }

    /**
     * <br>[機  能] 今月のタイムカード集計値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @param admConf 基本設定
     * @param con コネクション
     * @return TcdTotalValueModel 集計モデル
     * @throws SQLException SQL実行時例外
     */
    private TcdTotalValueModel __getThisTotalValue(
            Tcd010ParamModel paramMdl,
            RequestModel reqMdl,
            int sessionUsrSid,
            TcdAdmConfModel admConf,
            Connection con) throws SQLException {
        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);
        int usrSid = Integer.parseInt(paramMdl.getUsrSid());
        int year = Integer.parseInt(paramMdl.getYear());
        int month = Integer.parseInt(paramMdl.getMonth());
        UDate fdate = new UDate();
        UDate tdate = calBiz.setTimeCardCal(year, month, admConf.getTacSimebi(), fdate);

        TcdTotalValueModel ret
            = calBiz.getTotalValueModel(usrSid, fdate, tdate, sessionUsrSid, con, reqMdl);
        return ret;
    }

    /**
     * <br>[機  能] 指定時間に当てはまるかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param from 開始時間
     * @param to 終了時間
     * @param target チェック対象時間
     * @return 現在の日付が範囲内ならtrue
     */
    public static boolean isRange(final UDate from, final UDate to, final UDate target) {

        if (from == null || to == null || target == null) {
            return false;
        }
        long fromNum = from.getTime();
        long toNum = to.getTime();
        long tarNum = target.getTime();
        return fromNum <= tarNum && tarNum <= toNum;
    }

    /**
     * <br>[機  能] 一ヶ月分のタイムカード情報を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param admConf 基本設定
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Tcd010Model> getTimeCardList(
            Tcd010ParamModel paramMdl,
            int usrSid,
            TcdAdmConfModel admConf,
            Connection con,
            RequestModel reqMdl) throws SQLException {

        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);

        //締め日
        int sime = admConf.getTacSimebi();
        UDate sysDate = new UDate();

        ArrayList<Tcd010Model> ret = new ArrayList<Tcd010Model>();
        int year = Integer.parseInt(paramMdl.getYear());
        int month = Integer.parseInt(paramMdl.getMonth());
        //休日情報取得
        UDate date = new UDate();
        //from～toを設定
        UDate endDate = calBiz.setTimeCardCal(year, month, sime, date);

        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(date, endDate);

        TcdTcdataDao tcdDao = new TcdTcdataDao(con);
        HashMap<String, Tcd010Model> timeMap = tcdDao.getTimeCardMap(usrSid, date, endDate);
        Tcd010Model tcdMdl = null;

        //休日区分情報一覧を取得
        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();

        //開始～終了日
        int interval = admConf.getTacInterval();
        while (date.compareDateYMD(endDate) != UDate.SMALL) {

            tcdMdl = timeMap.get(date.getDateString());
            Time inTime = null;

            if (tcdMdl == null) {
                tcdMdl = new Tcd010Model();
            } else {
                //始業時間、終業時間の調整を行う
                inTime = TimecardBiz.adjustIntime(tcdMdl.getTcdIntime(), interval);
                Time outTime = TimecardBiz.adjustOuttime(tcdMdl.getTcdOuttime(), interval);

                //From > To の場合、24時間をプラス
                boolean addToTime = tcdMdl.getTcdOuttime() != null
                 && tcdMdl.getTcdIntime() != null
                 && tcdMdl.getTcdIntime().compareTo(tcdMdl.getTcdOuttime()) == UDate.LARGE;


                tcdMdl.setTcd010ShigyouStr(TimecardUtil.getTimeString(inTime));
                if (addToTime) {
                    tcdMdl.setTcd010SyugyouStr(TimecardUtil.getTimeString(outTime, 24));
                } else {
                    tcdMdl.setTcd010SyugyouStr(TimecardUtil.getTimeString(outTime));
                }

                //打刻時間
                tcdMdl.setTcd010StrikeShigyouStr(
                        TimecardUtil.getTimeString(tcdMdl.getTcdStrikeIntime()));

                if (tcdMdl.getTcdStrikeIntime() != null && tcdMdl.getTcdStrikeOuttime() != null
                 && tcdMdl.getTcdStrikeIntime().compareTo(
                         tcdMdl.getTcdStrikeOuttime()) == UDate.LARGE) {

                    tcdMdl.setTcd010StrikeSyugyouStr(TimecardUtil.getTimeString(
                            tcdMdl.getTcdStrikeOuttime(), 24));
                } else {
                    tcdMdl.setTcd010StrikeSyugyouStr(TimecardUtil.getTimeString(
                            tcdMdl.getTcdStrikeOuttime()));
                }

                tcdMdl.setTcd010Bikou(tcdMdl.getTcdBiko());
                String holidayName = "";
                for (TcdHolidayInfModel holData : holDataList) {
                    if (tcdMdl.getThiSid() == holData.getThiSid()) {
                        holidayName = holData.getThiName();
                        break;
                    }
                }
                tcdMdl.setTcd010Kyujitsu(holidayName);

            }
            //時間帯設定
            if (tcdMdl.getTtiSid() == 0) {
                tcdMdl.setTtiSid(0);
            }

            //カレンダー情報付加
            tcdMdl.setTcd010Ymd(date.getDateString());
            tcdMdl.setTcd010Year(date.getYear());
            tcdMdl.setTcd010Month(date.getMonth());
            tcdMdl.setTcd010Day(date.getIntDay());
            tcdMdl.setTcd010Week(date.getWeek());
            tcdMdl.setTcd010WeekStr(TimecardUtil.toStrWeek(date.getWeek(), reqMdl));
            //休日情報付加
            CmnHolidayModel holMdl = holMap.get(date.getDateString());
            if (holMdl != null) {
                tcdMdl.setHolKbn(1);
                tcdMdl.setHolName(holMdl.getHolName());
            }
            //不正データ判定
            //(システム日付以前で始業時刻から24時間以上経過していて終業時刻が未登録のもの)
            TimecardBiz tcBiz = new TimecardBiz(reqMdl);
            if (tcdMdl.getTcdIntime() != null) {

                if (sysDate.compareDateYMD(tcdMdl.getTcdDate()) == UDate.SMALL
                        && tcdMdl.getTcdOuttime() == null
                        && tcBiz.isOver24HoursOrTenAM(
                                tcdMdl.getTcdDate(), inTime, tcdMdl.getTcdStrikeIntime())) {
                    tcdMdl.setFailFlg(GSConstTimecard.DATA_FAIL);
                } else {
                    tcdMdl.setFailFlg(GSConstTimecard.DATA_NOT_FAIL);
                }
            }
            //ロックフラグ制御
            tcdMdl.setTcdLockFlg(GSConstTimecard.UNLOCK_FLG);

            ret.add(tcdMdl);
            //本日のタイムカードデータ
            if (sysDate.compareDateYMD(date) == UDate.EQUAL) {
                log__.debug("*****現在日付：" + sysDate.getYear() + "年"
                                              + sysDate.getMonth() + "月"
                                              + sysDate.getIntDay() + "日");
                //打刻ボタンの表示設定を行う
                setDakokuBtn(paramMdl, reqMdl, ret, admConf, con, usrSid);
            }

            //日付を進める
            date.addDay(1);
        }

        return ret;
    }



    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する
     * <br>[解  説] 管理者設定の共有範囲が「ユーザ全員で共有」の場合有効な全てのグループを取得する。
     * <br>「所属グループ内のみ共有可」の場合、ユーザが所属するグループのみを返す。
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ種別
     * @param reqMdl リクエスト情報
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getGroupLabelList(Connection con,
            int usrSid, int usrKbn, RequestModel reqMdl) throws SQLException {

        List < LabelValueBean > labelList = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (usrKbn == GSConstTimecard.USER_KBN_ADMIN) {
            //管理者
            GroupBiz gpBiz = new GroupBiz();
            labelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);
            labelList.remove(0);
        } else if (usrKbn == GSConstTimecard.USER_KBN_GRPADM) {
            //グループ管理権限をもつグループのみ
            labelList = getGroupLabel(con, reqMdl, usrSid, false, 0);
        } else if (usrKbn == GSConstTimecard.USER_KBN_NORMAL) {
            //一般ユーザ
            labelList = getGroupLabel(con, reqMdl, usrSid, false, 1);

        }
        log__.debug("getGroupLabelList.size()==>" + labelList.size());
        return labelList;
    }

    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @return ユーザリスト
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserLabelList(Connection con, int groupSid)
    throws SQLException {

        List < UsrLabelValueBean > labelList = null;
        String[] execludeusid = new String[] {
                Integer.toString(GSConst.SYSTEM_USER_ADMIN),
                Integer.toString(GSConst.SYSTEM_USER_MAIL) };
        UserBiz usrBiz = new UserBiz();
        labelList = usrBiz.getUserLabelList(con, groupSid, execludeusid);
        return labelList;
    }

    /**
     * <br>[機  能] グループリストの中にデフォルトグループが存在した場合そのグループSIDを返します。
     * <br>[解  説]
     * <br>[備  考] デフォルトグループが存在しない場合、リストの先頭を返します。
     * @param userSid ユーザSID
     * @param groupLabel グループラベルリスト
     * @param con コネクション
     * @return int グループSID
     * @throws SQLException SQL実行時例外
     */
    public int getInitGpSid(int userSid, List<LabelValueBean> groupLabel, Connection con)
    throws SQLException {

        int ret = 0;
        GroupBiz gpBiz = new GroupBiz();
        int dfGpSid = gpBiz.getDefaultGroupSid(userSid, con);
        LabelValueBean labelBean = null;
        for (int i = 0; i < groupLabel.size(); i++) {
            labelBean = groupLabel.get(i);
            if (Integer.parseInt(labelBean.getValue()) == dfGpSid) {
                ret = dfGpSid;
            }
        }
        if (ret == 0) {
            labelBean = groupLabel.get(0);
            ret = Integer.parseInt(labelBean.getValue());
        }

        log__.debug("getInitGpSid==>" + ret);
        return ret;
    }

    /**
     * <br>[機  能] ユーザリストの中にセッションユーザが存在した場合そのユーザSIDを返します。
     * <br>[解  説]
     * <br>[備  考] セッションユーザが存在しない場合、リストの先頭を返します。
     * @param userSid ユーザSID
     * @param userLabel ユーザラベルリスト
     * @param con コネクション
     * @return int グループSID
     * @throws SQLException SQL実行時例外
     */
    public String getInitUsrSid(int userSid, List<UsrLabelValueBean> userLabel, Connection con)
    throws SQLException {
        String strUserSid = String.valueOf(userSid);
        String ret = "-1";
        if (userLabel == null || userLabel.size() == 0) {
            return ret;
        }
        LabelValueBean labelBean = null;
        for (int i = 0; i < userLabel.size(); i++) {
            labelBean = userLabel.get(i);
            log__.debug("labelBean.getValue()==>" + labelBean.getValue());
            log__.debug("strUserSid==>" + strUserSid);
            if (labelBean.getValue().equals(strUserSid)) {
                ret = strUserSid;
            }
        }
        if (ret.equals("-1")) {
            labelBean = userLabel.get(0);
            ret = labelBean.getValue();
        }
        return ret;
    }

    /**
     * <br>[機  能] タイムカード情報を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報｣
     * @return int 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteTcdData(Tcd010ParamModel paramMdl, int sessionUsrSid, Connection con,
                            RequestModel reqMdl)
    throws SQLException {
        int ret = 0;

        int usrSid = Integer.parseInt(paramMdl.getUsrSid());
        int year = Integer.parseInt(paramMdl.getYear());
        int month = Integer.parseInt(paramMdl.getMonth());
        String[] days = paramMdl.getSelectDay();
        TimecardBiz biz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = biz.getTcdAdmConfModel(sessionUsrSid, con);

        TcdDatausedSumDao tdsDao = new TcdDatausedSumDao(con);
        UDate ud = new UDate();
        Tcd020Biz tcd020biz = new Tcd020Biz();
        ArrayList<UDate> delListDate = new ArrayList<UDate>();
        for (int i = 0; i < days.length; i++) {
            ud = tcd020biz.getEditDate(year, month,
                    Integer.parseInt(days[i]), admConf.getTacSimebi());
            delListDate.add(ud);
        }
        tdsDao.insertDelDiff(delListDate, usrSid);
        
        TcdTcdataDao dao = new TcdTcdataDao(con);
        dao.delete(usrSid, year, month, days, admConf.getTacSimebi(), reqMdl);
        
        return ret;
    }

    /**
     * <br>[機  能] 勤務表エクセル出力用のタイムカードリストを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<Tcd010Model> getXlsTimeCardList(
            RequestModel reqMdl,
            Tcd010ParamModel paramMdl,
            Connection con) throws SQLException {

        int targetUserSid = getTargetUserSid(reqMdl, paramMdl, con);

        //基本設定を取得
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(targetUserSid, con);


        return getTimeCardList(paramMdl, targetUserSid, admConf, con, reqMdl);
    }

    /**
     * <br>[機  能] Xls出力時の対象ユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return targetUserSid
     * @throws SQLException SQL実行時例外
     */
    public int getTargetUserSid(
            RequestModel reqMdl,
            Tcd010ParamModel paramMdl,
            Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int targetUserSid = usModel.getUsrsid(); //セッションユーザSID

        //ユーザ種別を判定 一般・グループ管理者・管理者
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int userKbn = tcBiz.getUserKbn(con, usModel);
        if (GSConstTimecard.USER_KBN_ADMIN == userKbn
                || GSConstTimecard.USER_KBN_GRPADM == userKbn) {
            targetUserSid = Integer.parseInt(paramMdl.getUsrSid());
        }
        return targetUserSid;
    }

    /**
     * <br>[機  能] 現在の年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param kishuMonth 期首月
     * @param paramMdl パラメータ情報
     * @return nendYear
     */
    public String getNend(int kishuMonth,
            Tcd010ParamModel paramMdl) {
        int nendYear = 0;
        int thisYear = Integer.parseInt(paramMdl.getYear());
        int nowMonth = Integer.parseInt(paramMdl.getMonth());
        if (nowMonth < kishuMonth) {
            nendYear = thisYear - 1;
        } else {
            nendYear = thisYear;
        }
        return Integer.toString(nendYear);
    }

    /**
     * <br>[機  能] 指定した月のタイムカード集計値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @param admConf 基本設定
     * @param con コネクション
     * @param from 開始日付
     * @return TcdTotalValueModel 集計モデル
     * @throws SQLException SQL実行時例外
     */
    private TcdTotalValueModel __getTotalValue(
            Tcd010ParamModel paramMdl,
            RequestModel reqMdl,
            int sessionUsrSid,
            TcdAdmConfModel admConf,
            Connection con,
            UDate from) throws SQLException {
        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);
        int usrSid = Integer.parseInt(paramMdl.getUsrSid());
        int year = from.getYear();
        int month = from.getMonth();
        UDate tdate = calBiz.setTimeCardCal(year, month, admConf.getTacSimebi(), from);
        TcdTotalValueModel ret
            = calBiz.getTotalValueModel(usrSid, from, tdate, sessionUsrSid, con, reqMdl);
        return ret;
    }

    /**
     * <br>[機  能] 階層構造を反映させたグループリストを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param userSid ユーザSID
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @param mkCmbFlg コンボ作成フラグ 0:グループ管理権限をもつグループのみ 1:一般ユーザ
     * @return GroupModel in ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabel(Connection con,
                                                    RequestModel reqMdl,
                                                    int userSid,
                                                    boolean sentakuFlg,
                                                    int mkCmbFlg)
    throws SQLException {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        TcdUserGrpDao gpDao = new TcdUserGrpDao(con);

        //グループリストを取得
        List<TcdUserGrpModel> list = new ArrayList<TcdUserGrpModel>();
        if (mkCmbFlg == 0) {
            //グループ管理権限をもつグループのみ
            list = gpDao.selectGroupAdminOrderbyClass(userSid);

        } else if (mkCmbFlg == 1) {
            //一般ユーザ
            list = gpDao.selectGroupDataListOrderbyClass(userSid);
        }

        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator<TcdUserGrpModel> it = list.iterator();
        ArrayList<GroupModel> grpList = new ArrayList<GroupModel>();

        while (it.hasNext()) {
            TcdUserGrpModel gcmodel = it.next();
            GroupModel gtModel = new GroupModel();

            //グループSIDをセット
            gtModel.setGroupSid(gcmodel.getGroupSid());
            //グループ階層レベル
            gtModel.setClassLevel(gcmodel.getLowGrpLevel());
            //グループ名
            gtModel.setGroupName(__getGroupName(gtModel.getClassLevel(), gcmodel.getGroupName()));

            log__.debug("グループSID = " + gtModel.getGroupSid());
            log__.debug("グループ名(階層反映済) = " + gtModel.getGroupName());
            log__.debug("階層Lv = " + gtModel.getClassLevel());
            grpList.add(gtModel);
        }
        labelList = getGroupDspList(sentakuFlg, reqMdl, grpList);
        return labelList;
    }

    /**
     * <br>[機  能] グループ階層を反映させたグループ名を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param classLevel グループ階層
     * @param groupName グループ名
     * @return グループ階層を反映させたグループ名
     */
    private String __getGroupName(int classLevel, String groupName) {

        String gpName = "";
        gpName = getCombSpace(classLevel) + groupName;

        return gpName;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する(コンボボックス用)
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param level 階層ﾚﾍﾞﾙ
     * @return グループ情報一覧
     */
    public String getCombSpace(int level) {

        String space = "";

        switch (level) {
            case 1:
            default :
                space = "";
                break;
            case 2:
                space = "　";
                break;
            case 3:
                space = "　　";
                break;
            case 4:
                space = "　　　";
                break;
            case 5:
                space = "　　　　";
                break;
            case 6:
                space = "　　　　　";
                break;
            case 7:
                space = "　　　　　　";
                break;
            case 8:
                space = "　　　　　　　";
                break;
            case 9:
                space = "　　　　　　　　";
                break;
            case 10:
                space = "　　　　　　　　　";
                break;
        }


        return space;
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sentakuFlg true:「選択してください」のラベルを作成する, false:作成しない
     * @param reqMdl リクエスト情報
     * @param gpList グループリスト
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupDspList(boolean sentakuFlg,
                                                 RequestModel reqMdl,
                                                 List<GroupModel> gpList) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (sentakuFlg) {
            labelList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        }

        //グループリスト取得
        for (GroupModel gmodel : gpList) {
            labelList.add(new LabelValueBean(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid())));
        }
        return labelList;
    }

    /**
     *
     * <br>[機  能] 打刻ボタンの表示設定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param tcdMdlList カレンダーデータ
     * @param admConf 基本設定
     * @param con コネクション
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setDakokuBtn(Tcd010ParamModel paramMdl,
                            RequestModel reqMdl,
                            ArrayList<Tcd010Model> tcdMdlList,
                            TcdAdmConfModel admConf,
                            Connection con,
                            int usrSid) throws SQLException {

        //当日のタイムカードデータを取得
        Tcd010Model tcdMdl = tcdMdlList.get(tcdMdlList.size() - 1);

        //不正データ処理
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        if (tcBiz.isFailDataExist(usrSid, con, admConf)) {
            //不正日付の場合、打刻ボタン非表示
            return;
        }

        //昨日のタイムカードデータを取得
        Tcd010Model yersterDayTcd = null;
        Time yersterDayStr = null;
        Time yersterDayEnd = null;
        if (tcdMdlList.size() > 1) {
            yersterDayTcd = tcdMdlList.get(tcdMdlList.size() - 2);
            yersterDayStr = yersterDayTcd.getTcdIntime();
            yersterDayEnd = yersterDayTcd.getTcdOuttime();
        }

        //タイムカードのボタン表示チェック
        TimecardBiz checkBtn = new TimecardBiz();
        int btnDspKbn = checkBtn.checkDakokuBtn(tcdMdl.getTcdIntime(), tcdMdl.getTcdOuttime(),
                                                yersterDayStr, yersterDayEnd);

        if (btnDspKbn == GSConstTimecard.STATUS_NOT_END) {
            tcdMdl.setDakokuBtnStrKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
            tcdMdl.setDakokuBtnEndKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
            yersterDayTcd.setDakokuBtnStrKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
            yersterDayTcd.setDakokuBtnEndKbn(GSConstTimecard.DAKOKUBTN_DSP_OK);
        } else if (btnDspKbn == GSConstTimecard.STATUS_FREE) {
            tcdMdl.setDakokuBtnStrKbn(GSConstTimecard.DAKOKUBTN_DSP_OK);
            tcdMdl.setDakokuBtnEndKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
        } else if (btnDspKbn == GSConstTimecard.STATUS_HAFE) {
            tcdMdl.setDakokuBtnStrKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
            tcdMdl.setDakokuBtnEndKbn(GSConstTimecard.DAKOKUBTN_DSP_OK);
        } else if (btnDspKbn == GSConstTimecard.STATUS_FIN) {
            tcdMdl.setDakokuBtnStrKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
            tcdMdl.setDakokuBtnEndKbn(GSConstTimecard.DAKOKUBTN_DSP_NOT);
        }

    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        TimecardBiz tcdCmnBiz = new TimecardBiz();
        return tcdCmnBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        TimecardBiz tcdCmnBiz = new TimecardBiz();
        tcdCmnBiz.deleteTempDir(reqMdl, SCR_ID);
    }
}
