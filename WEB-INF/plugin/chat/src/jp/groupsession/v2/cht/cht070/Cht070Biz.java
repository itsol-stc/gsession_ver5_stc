package jp.groupsession.v2.cht.cht070;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.StatisticsBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] チャット統計情報画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Cht070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht070Biz.class);

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Cht070Biz(Connection con,
                     RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht070ParamModel
     * @param buMdl セッションユーザモデル
     * @param pluginConfig プラグイン設定
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(
            Cht070ParamModel paramMdl,
            BaseUserModel buMdl,
            PluginConfig pluginConfig)
                    throws SQLException {
        log__.debug("init");
        if (paramMdl.getCht070InitFlg() == GSConstMain.DSP_FIRST) {
            paramMdl.setCht070InitFlg(GSConstMain.DSP_ALREADY);
            paramMdl.setCht070DspGroup(GSConstChat.CHAT_LOG_DSPGROUP_YES);
            paramMdl.setCht070DspUser(GSConstChat.CHAT_LOG_DSPUSER_YES);
            paramMdl.setCht070DspSum(GSConstChat.CHAT_LOG_DSPSUM_YES);
        }
        Cht070Dao dao = new Cht070Dao(con__);
        // 権限情報設定
        __setCtrFlg(paramMdl, buMdl, pluginConfig);

        //表示件数リスト取得
        paramMdl.setCht070DspNumLabel(StatisticsBiz.getDspNumList());

        //表示件数
        int limit = paramMdl.getCht070DspNum();

        //日付リスト
        ArrayList<Cht070DspModel> dateList = new ArrayList<Cht070DspModel>();

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //表示単位
        int dateUnit = paramMdl.getCht070DateUnit();

        //日付を設定する
        if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
            //月別表示の場合
            UDate[] udateFrTo = __setDateByMonth(dao, paramMdl, dateUnit);
            udateFr = udateFrTo[0];
            udateTo = udateFrTo[1];

            //日付一覧リストを取得する
            dateList = __getKikanListByMonth(udateFr.cloneUDate(), udateTo.cloneUDate());

        } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別表示の場合
            UDate[] udateFrTo = __setDateByWeek(paramMdl, dateUnit);
            udateFr = udateFrTo[0];
            udateTo = udateFrTo[1];

            //日付一覧リストを取得する
            dateList = __getKikanListByWeek(udateFr.cloneUDate(), udateTo.cloneUDate());

        } else {
            //日別表示の場合
            UDate[] udateFrTo = __setDateByDay(paramMdl, dateUnit);
            udateFr = udateFrTo[0];
            udateTo = udateFrTo[1];

            //日付一覧リストを取得する
            dateList = __getKikanListByDay(udateFr.cloneUDate(), udateTo.cloneUDate());
        }

        //日付リストのサイズ
        int dateListSize = dateList.size();

        //グラフ用日付リスト
        ArrayList<Cht070DspModel> jsonDateList = new ArrayList<Cht070DspModel>(dateList);

        //選択日付の日数よりページ情報を取得する
        //現在ページ、スタート行
        int nowPage = paramMdl.getCht070NowPage();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(dateListSize, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setCht070DspPage1(nowPage);
        paramMdl.setCht070DspPage2(nowPage);
        paramMdl.setCht070NowPage(nowPage);
        paramMdl.setCht070PageLabel(PageUtil.createPageOptions(dateListSize, limit));


        //１ページの表示範囲の日付リストを生成する
        List<Cht070DspModel> pageList = __getPageDspDateList(dateList, nowPage, limit);
        UDate searchFrDate = new UDate();
        UDate searchToDate = new UDate();

        //グラフ表示のため指定範囲を全件取得
        searchFrDate = udateFr.cloneUDate();
        searchToDate = udateTo.cloneUDate();

        //集計一覧を取得する
        Map<String, Cht070DspModel> logCountMap = null;
        if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別の場合、日毎の集計情報を週単位にまとめる
            Map<String, Cht070DspModel> dayLogMap =
                    dao.getMessageLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            logCountMap = __getLogMapByWeek(dayLogMap, dateList);

        } else {
            //月別、日別の場合
            logCountMap = dao.getMessageLogCountDataSum(searchFrDate, searchToDate, dateUnit);
        }
        //表示用リストへ格納する
        int totalUser = 0;
        int totalGroup = 0;
        int total = 0;
        int totalSend = 0;
        for (Cht070DspModel mdl : pageList) {
            Cht070DspModel logCntData = logCountMap.get(mdl.getStrDate());
            if (logCntData != null) {
                mdl.setSendNum(logCntData.getSendNum());
                mdl.setStrSendNum(StatisticsBiz.formatNum(logCntData.getSendNum()));
                mdl.setUserNum(logCntData.getUserNum());
                mdl.setStrUserNum(StatisticsBiz.formatNum(logCntData.getUserNum()));
                mdl.setGroupNum(logCntData.getGroupNum());
                mdl.setStrGroupNum(StatisticsBiz.formatNum(logCntData.getGroupNum()));
                mdl.setSumNum(logCntData.getSumNum());
                mdl.setStrSumNum(StatisticsBiz.formatNum(logCntData.getSumNum()));
                totalUser += logCntData.getUserNum();
                totalGroup += logCntData.getGroupNum();
                total += logCntData.getSumNum();
                totalSend += logCntData.getSendNum();
            }
        }
        paramMdl.setCht070LogCountList(pageList);
        paramMdl.setCht070TotalMessageUser(StatisticsBiz.formatNum(totalUser));
        paramMdl.setCht070TotalMessageGroup(StatisticsBiz.formatNum(totalGroup));
        paramMdl.setCht070TotalMessage(StatisticsBiz.formatNum(total));
        paramMdl.setCht070TotalSend(StatisticsBiz.formatNum(totalSend));
        //統計情報の最初の画面を設定する
        if (paramMdl.getLogCountBack() == null) {
            paramMdl.setLogCountBack(GSConstChat.PLUGIN_ID_CHAT);
        }

        //グラフ用データを作成する
        __makeGraphData(jsonDateList, logCountMap, paramMdl, dateUnit);
    }

    /**
     * <br>[機  能] 権限情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht070ParamModel
     * @param buMdl セッションユーザモデル
     * @param pluginConfig プラグイン設定
     * @throws SQLException SQL実行時例外
     */
    public void __setCtrFlg(
            Cht070ParamModel paramMdl,
            BaseUserModel buMdl,
            PluginConfig pluginConfig)
                    throws SQLException {
        // GS管理者権限情報を設定
        if (buMdl.getAdminFlg()) {
            paramMdl.setCht070GsAdminFlg(true);
        }
        // プラグインが使用可能であり、かつログインユーザにプラグインの管理者権限があるか
        List <String> pluginList = new ArrayList<String>();
        for (Plugin plugin: pluginConfig.getPluginDataList()) {
            pluginList.add(plugin.getId());
        }
        CommonBiz cmnBiz = new CommonBiz();
        if (pluginList.contains(GSConst.PLUGINID_WML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_WML)) {
            paramMdl.setCht070CtrlFlgWml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_SML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_SML)) {
            paramMdl.setCht070CtrlFlgSml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_CIR)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_CIR)) {
            paramMdl.setCht070CtrlFlgCir(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_FILE)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_FILE)) {
            paramMdl.setCht070CtrlFlgFil(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_BULLETIN)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_BULLETIN)) {
            paramMdl.setCht070CtrlFlgBul(true);
        }
    }
    /**
     * <br>[機  能] 月別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param dao Cht070Dao
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @exception SQLException SQL実行時例外
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByMonth(
            Cht070Dao dao, Cht070ParamModel paramMdl, int dateUnit)
                    throws SQLException {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //集計データのMAX日付(現在の日付)とmin日付を取得
        UDate[] logMaxMinDate = __getLogMaxMinDate(con__, dateUnit);
        //年の表示下限をmin日付の1年前に設定
        UDate minLogDate = NullDefault.getUDate(logMaxMinDate[0], new UDate());
        minLogDate.addYear(-1);
        //年の表示上限をMAX日付の1年後に設定
        UDate maxLogDate = new UDate();
        maxLogDate.addYear(1);

        //年月コンボ作成
        paramMdl.setCht070DspYearLabel(StatisticsBiz.getDspYearList(
                minLogDate.getYear(), maxLogDate.getYear(), reqMdl__));
        paramMdl.setCht070DspMonthLabel(StatisticsBiz.getMonthList(reqMdl__));

        //TO年月を設定
        UDate defaultToDate = new UDate();
        String dateToYear = paramMdl.getCht070DateMonthlyToYear();
        String dateToMonth = paramMdl.getCht070DateMonthlyToMonth();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateToYear)
                || StringUtil.isNullZeroString(dateToMonth)
                || !ValidateUtil.isNumber(dateToYear)
                || !ValidateUtil.isNumber(dateToMonth)) {
            dateToYear = String.valueOf(defaultToDate.getYear());
            dateToMonth = String.valueOf(defaultToDate.getMonth());
        }
        //年コンボの範囲内かチェック
        if (Integer.parseInt(dateToYear) > maxLogDate.getYear()) {
            dateToYear = maxLogDate.getStrYear();
        } else if (Integer.parseInt(dateToYear) < minLogDate.getYear()) {
            dateToYear = minLogDate.getStrYear();
        }
        paramMdl.setCht070DateMonthlyToYear(dateToYear);
        paramMdl.setCht070DateMonthlyToMonth(dateToMonth);

        //FROM年月を設定
        //初期表示を12ヶ月分に設定
        UDate defaultFrDate = new UDate();
        defaultFrDate.setYear(Integer.parseInt(dateToYear));
        defaultFrDate.setMonth(Integer.parseInt(dateToMonth));
        defaultFrDate.addYear(-1);
        defaultFrDate.addMonth(1);
        String dateFrYear = paramMdl.getCht070DateMonthlyFrYear();
        String dateFrMonth = paramMdl.getCht070DateMonthlyFrMonth();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateFrYear)
                || StringUtil.isNullZeroString(dateFrMonth)
                || !ValidateUtil.isNumber(dateFrYear)
                || !ValidateUtil.isNumber(dateFrMonth)) {
            dateFrYear = String.valueOf(defaultFrDate.getYear());
            dateFrMonth = String.valueOf(defaultFrDate.getMonth());
        }
        //年コンボの範囲内かチェック
        if (Integer.parseInt(dateFrYear) > maxLogDate.getYear()) {
            dateFrYear = maxLogDate.getStrYear();
        } else if (Integer.parseInt(dateFrYear) < minLogDate.getYear()) {
            dateFrYear = minLogDate.getStrYear();
        }
        //FROM > TOの場合、TOをFROMと同じに設定
        if (Integer.parseInt(dateFrYear) > Integer.parseInt(dateToYear)
                || (Integer.parseInt(dateFrYear) == Integer.parseInt(dateToYear)
                        && Integer.parseInt(dateFrMonth) > Integer.parseInt(dateToMonth))) {
            dateToYear = dateFrYear;
            dateToMonth = dateFrMonth;
        }
        paramMdl.setCht070DateMonthlyFrYear(dateFrYear);
        paramMdl.setCht070DateMonthlyFrMonth(dateFrMonth);

        //選択日付の範囲より日数を取得
        udateFr.setYear(Integer.parseInt(dateFrYear));
        udateFr.setMonth(Integer.parseInt(dateFrMonth));
        udateFr.setDay(1);
        udateFr.setZeroHhMmSs();
        udateTo.setYear(Integer.parseInt(dateToYear));
        udateTo.setMonth(Integer.parseInt(dateToMonth));
        udateTo.addMonth(1);
        udateTo.setDay(1);
        udateTo.addDay(-1);
        udateTo.setMaxHhMmSs();

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] 週別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByWeek(Cht070ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //TO年月週を設定
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getCht070DateWeeklyToStr();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateToStr)
                || !ValidateUtil.isSlashDateFormat(dateToStr)) {
            dateToStr = defaultToDate.getDateString("/");
        }
        udateTo = StatisticsBiz.getDateFromString(dateToStr);

        //初期表示を12週分に設定
        UDate defaultFrDate = udateTo.cloneUDate();
        defaultFrDate.addDay(-83);
        //nullと空文字チェック、数字チェック
        String dateFrStr = paramMdl.getCht070DateWeeklyFrStr();
        if (StringUtil.isNullZeroString(dateFrStr)
                || !ValidateUtil.isSlashDateFormat(dateFrStr)) {
            dateFrStr = defaultFrDate.getDateString("/");
        }
        udateFr = StatisticsBiz.getDateFromString(dateFrStr);

        //FROM > TOの場合、TOをFROMと同じに設定
        if (udateFr.compareDateYMD(udateTo) == UDate.SMALL) {
            udateTo = udateFr;
            dateToStr = dateFrStr;
        }

        //選択日付の範囲より日数を取得
        udateFr.setZeroHhMmSs();
        udateTo.setMaxHhMmSs();
        paramMdl.setCht070DateWeeklyFrStr(dateFrStr);
        paramMdl.setCht070DateWeeklyToStr(dateToStr);

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] 日別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByDay(Cht070ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //直近の一か月分をデフォルトの値として設定する
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getCht070DateDailyToStr();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateToStr)
                || !ValidateUtil.isSlashDateFormat(dateToStr)) {
            dateToStr = defaultToDate.getDateString("/");
        }
        udateTo = StatisticsBiz.getDateFromString(dateToStr);

        //初期表示を2週間分に設定
        UDate defaultFrDate = udateTo.cloneUDate();
        defaultFrDate.addDay(-13);
        //nullと空文字チェック、数字チェック
        String dateFrStr = paramMdl.getCht070DateDailyFrStr();
        if (StringUtil.isNullZeroString(dateFrStr)
                || !ValidateUtil.isSlashDateFormat(dateFrStr)) {
            dateFrStr = defaultFrDate.getDateString("/");
        }
        udateFr = StatisticsBiz.getDateFromString(dateFrStr);

        //FROM > TOの場合、TOをFROMと同じに設定
        if (udateFr.compareDateYMD(udateTo) == UDate.SMALL) {
            udateTo = udateFr;
            dateToStr = dateFrStr;
        }

        //選択日付の範囲より日数を取得
        udateFr.setZeroHhMmSs();
        udateTo.setMaxHhMmSs();
        paramMdl.setCht070DateDailyFrStr(dateFrStr);
        paramMdl.setCht070DateDailyToStr(dateToStr);

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] １ページの表示範囲の日付リスト
     * <br>[解  説]
     * <br>[備  考]
     * @param dateList 選択日付リスト
     * @param nowPage 現在のページ数
     * @param limit 件数
     * @return 1ページ分の日付リスト
     */
    private static List<Cht070DspModel> __getPageDspDateList(
            ArrayList<Cht070DspModel> dateList, int nowPage, int limit) {

        List<Cht070DspModel> ret = new ArrayList<Cht070DspModel>();

        //存在しないページが指定されていた場合は１ページ目扱い
        if (limit > dateList.size()) {
            nowPage = 1;
        }

        //開始位置
        int fromIndex = (nowPage - 1) * limit;
        //終了位置
        int toIndex = 0;
        if (fromIndex + limit >= dateList.size()) {
            toIndex = dateList.size();
        } else {
            toIndex = fromIndex + limit;
        }

        ret = dateList.subList(fromIndex, toIndex);

        return ret;
    }

    /**
     * <br>[機  能] 指定した期間の月ごと一覧リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Cht070DspModel> __getKikanListByMonth(UDate dateFr, UDate dateTo) {

        ArrayList<Cht070DspModel> ret = new ArrayList<Cht070DspModel>();

        while (dateFr.compareDateYM(dateTo) > UDate.SMALL) {
            Cht070DspModel mdl = new Cht070DspModel();
            mdl.setStrDate(String.valueOf(dateFr.getYear()) + dateFr.getStrMonth());
            mdl.setDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymJ(dateFr, reqMdl__));
            mdl.setSendNum(0);
            mdl.setStrSendNum("0");
            mdl.setUserNum(0);
            mdl.setStrUserNum("0");
            mdl.setGroupNum(0);
            mdl.setStrGroupNum("0");
            mdl.setSumNum(0);
            mdl.setStrSumNum("0");
            ret.add(mdl);
            dateFr.addMonth(1);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した期間の週ごと一覧リストを取得する
     * <br>[解  説] 1月1日～2月1日までなら5週分のリストを作成する
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Cht070DspModel> __getKikanListByWeek(UDate dateFr, UDate dateTo) {

        ArrayList<Cht070DspModel> ret = new ArrayList<Cht070DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            //該当週の終了日
            UDate dateFrWeekend = dateFr.cloneUDate();
            dateFrWeekend.addDay(6);

            Cht070DspModel mdl = new Cht070DspModel();
            mdl.setStrDate(dateFr.getDateStringForSql());
            mdl.setDate(dateFr.cloneUDate());
            if (dateFrWeekend.compareDateYMD(dateTo) > UDate.SMALL) {
                //該当週の終了日が指定期間内に収まる場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateFrWeekend, reqMdl__));
            } else {
                //該当週の終了日が指定期間を超えている場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateTo, reqMdl__));
            }
            mdl.setSendNum(0);
            mdl.setStrSendNum("0");
            mdl.setUserNum(0);
            mdl.setStrUserNum("0");
            mdl.setGroupNum(0);
            mdl.setStrGroupNum("0");
            mdl.setSumNum(0);
            mdl.setStrSumNum("0");
            ret.add(mdl);
            //次の週にする
            dateFr.addDay(7);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した期間の日付一覧リストを取得する
     * <br>[解  説] 1月1日～2月1日までなら32日分のリストを作成する
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Cht070DspModel> __getKikanListByDay(UDate dateFr, UDate dateTo) {

        ArrayList<Cht070DspModel> ret = new ArrayList<Cht070DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            Cht070DspModel mdl = new Cht070DspModel();
            mdl.setStrDate(dateFr.getDateStringForSql());
            mdl.setDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymdJwithStrWeek(dateFr, reqMdl__));
            mdl.setSendNum(0);
            mdl.setStrSendNum("0");
            mdl.setUserNum(0);
            mdl.setStrUserNum("0");
            mdl.setGroupNum(0);
            mdl.setStrGroupNum("0");
            mdl.setSumNum(0);
            mdl.setStrSumNum("0");
            ret.add(mdl);
            dateFr.addDay(1);
        }
        return ret;
    }

    /**
     * <br>[機  能] 日毎の集計情報から週毎の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dayLogMap 日毎集計情報
     * @param dateList 日付リスト
     * @return 週毎の集計情報のリスト
     */
    private Map<String, Cht070DspModel> __getLogMapByWeek(
            Map<String, Cht070DspModel> dayLogMap,
            List<Cht070DspModel> dateList) {
        Map<String, Cht070DspModel> ret = new HashMap<String, Cht070DspModel>();
        //週別表示用に日毎のデータを週単位にまとめ、表示用リストへ格納する
        for (Cht070DspModel mdl : dateList) {
            //対象の週の開始日と終了日
            String strBeginOfWeek = mdl.getStrDate();
            UDate beginOfWeek = mdl.getDate();
            UDate endOfWeek = beginOfWeek.cloneUDate();
            endOfWeek.addDay(6);
            //1週間分をまとめる
            int sendNum = 0;
            int userNum = 0;
            int groupNum = 0;
            int sumNum = 0;
            UDate targetDay = beginOfWeek.cloneUDate();
            while (targetDay.compareDateYMD(endOfWeek) != UDate.SMALL) {
                String strTargetDay = targetDay.getStrYear()
                                                + "-" + targetDay.getStrMonth()
                                                + "-" + targetDay.getStrDay();
                Cht070DspModel dspMdl = dayLogMap.get(strTargetDay);
                if (dspMdl != null) {
                    sendNum = sendNum + dspMdl.getSendNum();
                    userNum = userNum + dspMdl.getUserNum();
                    groupNum = groupNum + dspMdl.getGroupNum();
                    sumNum = sumNum + dspMdl.getSumNum();
                }
                targetDay.addDay(1);
            }
            //閲覧件数集計一覧に1週間分まとめたデータを格納する
            Cht070DspModel graphMdl = new Cht070DspModel();
            graphMdl.setStrDate(strBeginOfWeek);
            graphMdl.setSendNum(sendNum);
            graphMdl.setUserNum(userNum);
            graphMdl.setGroupNum(groupNum);
            graphMdl.setSumNum(sumNum);
            ret.put(strBeginOfWeek, graphMdl);

        }

        return ret;
    }

    /**
     * <br>[機  能] グラフ用データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonDateList グラフ用日付リスト
     * @param logMap 集計一覧
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     */
    private void __makeGraphData(
            ArrayList<Cht070DspModel> jsonDateList,
            Map<String, Cht070DspModel> logMap,
            Cht070ParamModel paramMdl,
            int dateUnit) {
        StringBuilder jsonDateData = new StringBuilder();
        StringBuilder jsonSendData = new StringBuilder();
        StringBuilder jsonUserData = new StringBuilder();
        StringBuilder jsonGroupData = new StringBuilder();
        StringBuilder jsonSumData = new StringBuilder();
        for (int i = 0; i < jsonDateList.size(); i++) {
            if (i == 0) {
                jsonDateData.append("[");
                jsonSendData.append("[");
                jsonUserData.append("[");
                jsonGroupData.append("[");
                jsonSumData.append("[");
            }

            Cht070DspModel dataMdl = jsonDateList.get(i);
            UDate date = dataMdl.getDate();
            Cht070DspModel logCntData = logMap.get(dataMdl.getStrDate());

            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                jsonDateData.append("'" + date.getStrYear() + "/" + date.getStrMonth() + "'");
            } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "-'");
            } else {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "'");
            }

            if (logCntData != null) {
                jsonSendData.append(logCntData.getSendNum());
                jsonUserData.append(logCntData.getUserNum());
                jsonGroupData.append(logCntData.getGroupNum());
                jsonSumData.append(logCntData.getSumNum());
            } else {
                jsonSendData.append("0");
                jsonUserData.append("0");
                jsonGroupData.append("0");
                jsonSumData.append("0");
            }
            if (i == jsonDateList.size() - 1) {
                jsonDateData.append("]");
                jsonSendData.append("]");
                jsonUserData.append("]");
                jsonGroupData.append("]");
                jsonSumData.append("]");
            } else {
                jsonDateData.append(",");
                jsonSendData.append(",");
                jsonUserData.append(",");
                jsonGroupData.append(",");
                jsonSumData.append(",");
            }
        }
        paramMdl.setJsonDateData(jsonDateData.toString());
        paramMdl.setJsonSendData(jsonSendData.toString());
        paramMdl.setJsonUserData(jsonUserData.toString());
        paramMdl.setJsonGroupData(jsonGroupData.toString());
        paramMdl.setJsonSumData(jsonSumData.toString());
    }

    /**
     * <br>[機  能] 集計データから最古日付と最新日付を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param dateUnit 日付単位
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行時例外
     */
    private UDate[] __getLogMaxMinDate(Connection con, int dateUnit) throws SQLException {
        Cht070Dao dao = new Cht070Dao(con);
        UDate[] ret = null;
        //閲覧と投稿の最古日時・最大日時を取得
        UDate[] maxMin = dao.getLogMaxMinDate();
        //日付の最小値
        UDate minView = maxMin[0];
        UDate minLogDate = new UDate();
        if (minView != null) {
            minLogDate = minView;
        }
        //日付の最大値
        UDate maxView = maxMin[1];
        UDate maxLogDate = new UDate();
        if (maxView != null) {
            maxLogDate = maxView;
        }
        ret = new UDate[] {minLogDate, maxLogDate};
        return ret;
    }


}