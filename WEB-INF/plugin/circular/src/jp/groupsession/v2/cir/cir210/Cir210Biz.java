package jp.groupsession.v2.cir.cir210;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.StatisticsBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 回覧板 管理者設定 統計情報画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Cir210Biz {

    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Cir210Biz.class);

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
    public Cir210Biz(Connection con,
                     RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cir210ParamModel
     * @param buMdl セッションユーザモデル
     * @param pluginConfig プラグイン設定
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(
            Cir210ParamModel paramMdl,
            BaseUserModel buMdl,
            PluginConfig pluginConfig)
                    throws SQLException {
        Cir210Dao dao = new Cir210Dao(con__);

        //GS管理者権限情報を設定
        if (buMdl.getAdminFlg()) {
            paramMdl.setCir210GsAdminFlg(true);
        }

        //プラグインが使用可能であり、かつログインユーザにプラグインの管理者権限があるか
        List <String> pluginList = new ArrayList<String>();
        for (Plugin plugin: pluginConfig.getPluginDataList()) {
            pluginList.add(plugin.getId());
        }
        CommonBiz cmnBiz = new CommonBiz();
        if (pluginList.contains(GSConst.PLUGINID_WML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_WML)) {
            paramMdl.setCir210CtrlFlgWml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_SML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_SML)) {
            paramMdl.setCir210CtrlFlgSml(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_FILE)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_FILE)) {
            paramMdl.setCir210CtrlFlgFil(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_BULLETIN)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_BULLETIN)) {
            paramMdl.setCir210CtrlFlgBbs(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_CHAT)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_CHAT)) {
            paramMdl.setCir210CtrlFlgCht(true);
        }

        //表示件数リスト取得
        paramMdl.setCir210DspNumLabel(StatisticsBiz.getDspNumList());

        //表示件数
        int limit = paramMdl.getCir210DspNum();

        //日付リスト
        ArrayList<Cir210DspModel> dateList = new ArrayList<Cir210DspModel>();

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //表示単位
        int dateUnit = paramMdl.getCir210DateUnit();

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
        ArrayList<Cir210DspModel> jsonDateList = new ArrayList<Cir210DspModel>(dateList);

        //選択日付の日数よりページ情報を取得する
        //現在ページ、スタート行
        int nowPage = paramMdl.getCir210NowPage();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(dateListSize, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setCir210DspPage1(nowPage);
        paramMdl.setCir210DspPage2(nowPage);
        paramMdl.setCir210NowPage(nowPage);
        paramMdl.setCir210PageLabel(PageUtil.createPageOptions(dateListSize, limit));


        //１ページの表示範囲の日付リストを生成する
        List<Cir210DspModel> pageList = __getPageDspDateList(dateList, nowPage, limit);
        UDate searchFrDate = new UDate();
        UDate searchToDate = new UDate();

//        if (pageList.size() > 0) {
//          searchFrDate = getDateFromString(pageList.get(0).getDate());
//          searchFrDate.setZeroHhMmSs();
//          searchToDate = getDateFromString(pageList.get(pageList.size() - 1).getDate());
//          searchToDate.setMaxHhMmSs();
//        }

        //グラフ表示のため指定範囲を全件取得
        searchFrDate = udateFr.cloneUDate();
        searchToDate = udateTo.cloneUDate();

        //集計一覧を取得する
        Map<String, Cir210DspModel> jcirLogMap = null;
        Map<String, Cir210DspModel> scirLogMap = null;
        if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別の場合、日毎の集計情報を週単位にまとめる
            Map<String, Cir210DspModel> dayJcirLogMap =
                    dao.getJcirLogSum(searchFrDate, searchToDate, dateUnit);
            Map<String, Cir210DspModel> dayScirLogMap =
                    dao.getScirLogSum(searchFrDate, searchToDate, dateUnit);
            List<Map<String, Cir210DspModel>> weekLogMapList =
                    __getLogMapByWeek(dayJcirLogMap, dayScirLogMap, dateList);
            jcirLogMap = weekLogMapList.get(0);
            scirLogMap = weekLogMapList.get(1);

        } else {
            //月別、日別の場合
            jcirLogMap = dao.getJcirLogSum(searchFrDate, searchToDate, dateUnit);
            scirLogMap = dao.getScirLogSum(searchFrDate, searchToDate, dateUnit);
        }

        //表示用リストへ格納する
        for (Cir210DspModel mdl : pageList) {
            Cir210DspModel jcirLogMdl = jcirLogMap.get(mdl.getDate());
            Cir210DspModel scirLogMdl = scirLogMap.get(mdl.getDate());
            if (jcirLogMdl != null) {
                mdl.setJcirNum(jcirLogMdl.getJcirNum());
                mdl.setStrJcirNum(StatisticsBiz.formatNum(jcirLogMdl.getJcirNum()));
            }
            if (scirLogMdl != null) {
                mdl.setScirNum(scirLogMdl.getScirNum());
                mdl.setStrScirNum(StatisticsBiz.formatNum(scirLogMdl.getScirNum()));
            }
        }
        paramMdl.setCir210LogCountList(pageList);

        //合計値を取得する
        UDate clUdateFr = udateFr.cloneUDate();
        UDate clUdateTo = udateTo.cloneUDate();
        int sumJcirLog = dao.getTotalJushinLogSum(clUdateFr, clUdateTo);
        int sumScirLog = dao.getTotalSoushinLogSum(clUdateFr, clUdateTo);
        paramMdl.setCir210SumJcirNum(StatisticsBiz.formatNum(sumJcirLog));
        paramMdl.setCir210SumScirNum(StatisticsBiz.formatNum(sumScirLog));

        //平均値を設定する
        DecimalFormat aveFormat = new DecimalFormat("#,###.#");
        double aveJcir = (double) sumJcirLog / dateListSize;
        double aveScir = (double) sumScirLog / dateListSize;
        paramMdl.setCir210AveJcirNum(aveFormat.format(aveJcir));
        paramMdl.setCir210AveScirNum(aveFormat.format(aveScir));

        //統計情報の最初の画面を設定する
        if (paramMdl.getLogCountBack() == null) {
            paramMdl.setLogCountBack(GSConst.PLUGINID_CIR);
        }

        //グラフ用データを作成する
        __makeGraphData(jsonDateList, jcirLogMap, scirLogMap, paramMdl, dateUnit);
    }

    /**
     * <br>[機  能] 月別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param dao Cir210Dao
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @exception SQLException SQL実行時例外
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByMonth(
            Cir210Dao dao, Cir210ParamModel paramMdl, int dateUnit)
                    throws SQLException {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //集計データのMAX日付(現在の日付)とmin日付を取得
        UDate[] logMaxMin = dao.getLogCountSumMaxMinDate();
        //年の表示下限をmin日付の1年前に設定
        UDate minLogDate = NullDefault.getUDate(logMaxMin[0], new UDate());
        minLogDate.addYear(-1);
        //年の表示上限をMAX日付の1年後に設定
        UDate maxLogDate = new UDate();
        maxLogDate.addYear(1);

        //年月コンボ作成
        paramMdl.setCir210DspYearLabel(StatisticsBiz.getDspYearList(
                minLogDate.getYear(), maxLogDate.getYear(), reqMdl__));
        paramMdl.setCir210DspMonthLabel(StatisticsBiz.getMonthList(reqMdl__));

        //TO年月を設定
        UDate defaultToDate = new UDate();
        String dateToYear = paramMdl.getCir210DateMonthlyToYear();
        String dateToMonth = paramMdl.getCir210DateMonthlyToMonth();
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
        paramMdl.setCir210DateMonthlyToYear(dateToYear);
        paramMdl.setCir210DateMonthlyToMonth(dateToMonth);

        //FROM年月を設定
        //初期表示を12ヶ月分に設定
        UDate defaultFrDate = new UDate();
        defaultFrDate.setYear(Integer.parseInt(dateToYear));
        defaultFrDate.setMonth(Integer.parseInt(dateToMonth));
        defaultFrDate.addYear(-1);
        defaultFrDate.addMonth(1);
        String dateFrYear = paramMdl.getCir210DateMonthlyFrYear();
        String dateFrMonth = paramMdl.getCir210DateMonthlyFrMonth();
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
        paramMdl.setCir210DateMonthlyFrYear(dateFrYear);
        paramMdl.setCir210DateMonthlyFrMonth(dateFrMonth);

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
    private UDate[] __setDateByWeek(Cir210ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //TO年月週を設定
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getCir210DateWeeklyToStr();
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
        String dateFrStr = paramMdl.getCir210DateWeeklyFrStr();
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
        paramMdl.setCir210DateWeeklyFrStr(dateFrStr);
        paramMdl.setCir210DateWeeklyToStr(dateToStr);

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
    private UDate[] __setDateByDay(Cir210ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //直近の一か月分をデフォルトの値として設定する
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getCir210DateDailyToStr();
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
        String dateFrStr = paramMdl.getCir210DateDailyFrStr();
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
        paramMdl.setCir210DateDailyFrStr(dateFrStr);
        paramMdl.setCir210DateDailyToStr(dateToStr);

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] 指定した期間の月ごと一覧リストを取得する
     * <br>[解  説] 2000年1月～2001年1月までなら13月分のリストを作成する
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Cir210DspModel> __getKikanListByMonth(UDate dateFr , UDate dateTo) {

        ArrayList<Cir210DspModel> ret = new ArrayList<Cir210DspModel>();

        while (dateFr.compareDateYM(dateTo) > UDate.SMALL) {
            Cir210DspModel mdl = new Cir210DspModel();
            mdl.setDate(String.valueOf(dateFr.getYear()) + dateFr.getStrMonth());
            mdl.setuDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymJ(dateFr, reqMdl__));
            mdl.setJcirNum(0);
            mdl.setStrJcirNum("0");
            mdl.setScirNum(0);
            mdl.setStrScirNum("0");
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
    private ArrayList<Cir210DspModel> __getKikanListByWeek(UDate dateFr , UDate dateTo) {

        ArrayList<Cir210DspModel> ret = new ArrayList<Cir210DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            //該当週の終了日
            UDate dateFrWeekend = dateFr.cloneUDate();
            dateFrWeekend.addDay(6);

            Cir210DspModel mdl = new Cir210DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setuDate(dateFr.cloneUDate());
            if (dateFrWeekend.compareDateYMD(dateTo) > UDate.SMALL) {
                //該当週の終了日が指定期間内に収まる場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateFrWeekend, reqMdl__));
            } else {
                //該当週の終了日が指定期間を超えている場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateTo, reqMdl__));
            }
            mdl.setJcirNum(0);
            mdl.setStrJcirNum("0");
            mdl.setScirNum(0);
            mdl.setStrScirNum("0");
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
    private ArrayList<Cir210DspModel> __getKikanListByDay(UDate dateFr , UDate dateTo) {

        ArrayList<Cir210DspModel> ret = new ArrayList<Cir210DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            Cir210DspModel mdl = new Cir210DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setuDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymdJwithStrWeek(dateFr, reqMdl__));
            mdl.setJcirNum(0);
            mdl.setStrJcirNum("0");
            mdl.setScirNum(0);
            mdl.setStrScirNum("0");
            ret.add(mdl);
            dateFr.addDay(1);
        }
        return ret;
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
    private static List<Cir210DspModel> __getPageDspDateList(
            ArrayList<Cir210DspModel> dateList, int nowPage , int limit) {

        List<Cir210DspModel> ret = new ArrayList<Cir210DspModel>();

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
     * <br>[機  能] 日毎の集計情報から週毎の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dayJcirLogMap 送信回覧板の集計情報
     * @param dayScirLogMap 受信回覧板の集計情報
     * @param dateList 日付リスト
     * @return 週毎の集計情報のリスト
     */
    private List<Map<String, Cir210DspModel>> __getLogMapByWeek(
            Map<String, Cir210DspModel> dayJcirLogMap,
            Map<String, Cir210DspModel> dayScirLogMap,
            List<Cir210DspModel> dateList) {
        Map<String, Cir210DspModel> jcirLogMap = new HashMap<String, Cir210DspModel>();
        Map<String, Cir210DspModel> scirLogMap = new HashMap<String, Cir210DspModel>();
        List<Map<String, Cir210DspModel>> ret = new ArrayList<Map<String, Cir210DspModel>>();

        //週別表示用に日毎のデータを週単位にまとめ、表示用リストへ格納する
        for (Cir210DspModel mdl : dateList) {

            //対象の週の開始日と終了日
            String strBeginOfWeek = mdl.getDate();
            UDate beginOfWeek = mdl.getuDate();
            UDate endOfWeek = beginOfWeek.cloneUDate();
            endOfWeek.addDay(6);

            //1週間分をまとめる
            int jcirNum = 0;
            int scirNum = 0;
            UDate targetDay = beginOfWeek.cloneUDate();
            while (targetDay.compareDateYMD(endOfWeek) != UDate.SMALL) {
                String strTargetDay = targetDay.getStrYear()
                                                + "-" + targetDay.getStrMonth()
                                                + "-" + targetDay.getStrDay();
                Cir210DspModel jcirLogMdl = dayJcirLogMap.get(strTargetDay);
                Cir210DspModel scirLogMdl = dayScirLogMap.get(strTargetDay);
                if (jcirLogMdl != null) {
                    jcirNum = jcirNum + jcirLogMdl.getJcirNum();
                }
                if (scirLogMdl != null) {
                    scirNum = scirNum + scirLogMdl.getScirNum();
                }
                targetDay.addDay(1);
            }

            //受信回覧板集計一覧に1週間分まとめたデータを格納する
            Cir210DspModel graphJcirMdl = new Cir210DspModel();
            graphJcirMdl.setDate(strBeginOfWeek);
            graphJcirMdl.setJcirNum(jcirNum);
            jcirLogMap.put(strBeginOfWeek, graphJcirMdl);

            //送信回覧板集計一覧に1週間分まとめたデータを格納する
            Cir210DspModel graphScirMdl = new Cir210DspModel();
            graphScirMdl.setDate(strBeginOfWeek);
            graphScirMdl.setScirNum(scirNum);
            scirLogMap.put(strBeginOfWeek, graphScirMdl);
        }

        ret.add(jcirLogMap);
        ret.add(scirLogMap);
        return ret;
    }

    /**
     * <br>[機  能] グラフ用データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonDateList グラフ用日付リスト
     * @param jcirLogMap 受信回覧板集計一覧
     * @param scirLogMap 送信回覧板集計一覧
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     */
    private void __makeGraphData(
            ArrayList<Cir210DspModel> jsonDateList,
            Map<String, Cir210DspModel> jcirLogMap,
            Map<String, Cir210DspModel> scirLogMap,
            Cir210ParamModel paramMdl,
            int dateUnit) {

        StringBuilder jsonDateData = new StringBuilder();
        StringBuilder jsonJcirLogData = new StringBuilder();
        StringBuilder jsonScirLogData = new StringBuilder();
        for (int i = 0; i < jsonDateList.size(); i++) {
            if (i == 0) {
                jsonDateData.append("[");
                jsonJcirLogData.append("[");
                jsonScirLogData.append("[");
            }

            Cir210DspModel dataMdl = jsonDateList.get(i);
            UDate date = dataMdl.getuDate();
            Cir210DspModel jcirLogMdl = jcirLogMap.get(dataMdl.getDate());
            Cir210DspModel scirLogMdl = scirLogMap.get(dataMdl.getDate());

            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                jsonDateData.append("'" + date.getStrYear() + "/" + date.getStrMonth() + "'");
            } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "-'");
            } else {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "'");
            }

            if (jcirLogMdl != null) {
                jsonJcirLogData.append(jcirLogMdl.getJcirNum());
            } else {
                jsonJcirLogData.append("0");
            }

            if (scirLogMdl != null) {
                jsonScirLogData.append(scirLogMdl.getScirNum());
            } else {
                jsonScirLogData.append("0");
            }

            if (i == jsonDateList.size() - 1) {
                jsonDateData.append("]");
                jsonJcirLogData.append("]");
                jsonScirLogData.append("]");
            } else {
                jsonDateData.append(",");
                jsonJcirLogData.append(",");
                jsonScirLogData.append(",");
            }
        }
        paramMdl.setJsonDateData(jsonDateData.toString());
        paramMdl.setJsonJcirData(jsonJcirLogData.toString());
        paramMdl.setJsonScirData(jsonScirLogData.toString());
    }
}