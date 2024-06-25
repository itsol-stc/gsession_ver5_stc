package jp.groupsession.v2.fil.fil270;

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
 * <br>[機  能] ファイル管理 管理者設定 統計情報画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Fil270Biz {

    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Fil270Biz.class);

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
    public Fil270Biz(Connection con,
                     RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil270ParamModel
     * @param buMdl セッションユーザモデル
     * @param pluginConfig プラグイン設定
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(
            Fil270ParamModel paramMdl,
            BaseUserModel buMdl,
            PluginConfig pluginConfig)
                    throws SQLException {
        Fil270Dao dao = new Fil270Dao(con__);

        //GS管理者権限情報を設定
        if (buMdl.getAdminFlg()) {
            paramMdl.setFil270GsAdminFlg(true);
        }

        //プラグインが使用可能であり、かつログインユーザにプラグインの管理者権限があるか
        List <String> pluginList = new ArrayList<String>();
        for (Plugin plugin: pluginConfig.getPluginDataList()) {
            pluginList.add(plugin.getId());
        }
        CommonBiz cmnBiz = new CommonBiz();
        if (pluginList.contains(GSConst.PLUGINID_WML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_WML)) {
            paramMdl.setFil270CtrlFlgWml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_SML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_SML)) {
            paramMdl.setFil270CtrlFlgSml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_CIR)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_CIR)) {
            paramMdl.setFil270CtrlFlgCir(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_BULLETIN)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_BULLETIN)) {
            paramMdl.setFil270CtrlFlgBbs(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_CHAT)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_CHAT)) {
            paramMdl.setFil270CtrlFlgCht(true);
        }

        //表示件数リスト取得
        paramMdl.setFil270DspNumLabel(StatisticsBiz.getDspNumList());

        //表示件数
        int limit = paramMdl.getFil270DspNum();

        //日付リスト
        ArrayList<Fil270DspModel> dateList = new ArrayList<Fil270DspModel>();

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //表示単位
        int dateUnit = paramMdl.getFil270DateUnit();

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
        ArrayList<Fil270DspModel> jsonDateList = new ArrayList<Fil270DspModel>(dateList);

        //選択日付の日数よりページ情報を取得する
        //現在ページ、スタート行
        int nowPage = paramMdl.getFil270NowPage();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(dateListSize, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setFil270DspPage1(nowPage);
        paramMdl.setFil270DspPage2(nowPage);
        paramMdl.setFil270NowPage(nowPage);
        paramMdl.setFil270PageLabel(PageUtil.createPageOptions(dateListSize, limit));


        //１ページの表示範囲の日付リストを生成する
        List<Fil270DspModel> pageList = __getPageDspDateList(dateList, nowPage, limit);
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
        Map<String, Fil270DspModel> dlLogMap = null;
        Map<String, Fil270DspModel> ulLogMap = null;
        if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別の場合、日毎の集計情報を週単位にまとめる
            Map<String, Fil270DspModel> dayDlLogMap =
                    dao.getDownloadLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            Map<String, Fil270DspModel> dayUlLogMap =
                    dao.getUploadLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            List<Map<String, Fil270DspModel>> weekLogMapList =
                    __getLogMapByWeek(dayDlLogMap, dayUlLogMap, dateList);
            dlLogMap = weekLogMapList.get(0);
            ulLogMap = weekLogMapList.get(1);

        } else {
            //月別、日別の場合
            dlLogMap = dao.getDownloadLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            ulLogMap = dao.getUploadLogCountDataSum(searchFrDate, searchToDate, dateUnit);
        }

        //表示用リストへ格納する
        for (Fil270DspModel mdl : pageList) {
            Fil270DspModel dlLogCntData = dlLogMap.get(mdl.getDate());
            Fil270DspModel ulLogCntData = ulLogMap.get(mdl.getDate());
            if (dlLogCntData != null) {
                mdl.setDownloadNum(dlLogCntData.getDownloadNum());
                mdl.setStrDownloadNum(StatisticsBiz.formatNum(dlLogCntData.getDownloadNum()));
            }
            if (ulLogCntData != null) {
                mdl.setUploadNum(ulLogCntData.getUploadNum());
                mdl.setStrUploadNum(StatisticsBiz.formatNum(ulLogCntData.getUploadNum()));
            }
        }
        paramMdl.setFil270LogCountList(pageList);

        //合計値を取得する
        UDate clUdateFr = udateFr.cloneUDate();
        UDate clUdateTo = udateTo.cloneUDate();
        int sumDownloadLog = dao.getTotalDownloadLogSum(clUdateFr, clUdateTo);
        int sumUploadLog = dao.getTotalUploadLogSum(clUdateFr, clUdateTo);
        paramMdl.setFil270SumDownloadNum(StatisticsBiz.formatNum(sumDownloadLog));
        paramMdl.setFil270SumUploadNum(StatisticsBiz.formatNum(sumUploadLog));

        //平均値を設定する
        DecimalFormat aveFormat = new DecimalFormat("#,###.#");
        double aveDownload = (double) sumDownloadLog / dateListSize;
        double aveUpload = (double) sumUploadLog / dateListSize;
        paramMdl.setFil270AveDownloadNum(aveFormat.format(aveDownload));
        paramMdl.setFil270AveUploadNum(aveFormat.format(aveUpload));

        //統計情報の最初の画面を設定する
        if (paramMdl.getLogCountBack() == null) {
            paramMdl.setLogCountBack(GSConst.PLUGIN_ID_FILE);
        }

        //グラフ用データを作成する
        __makeGraphData(jsonDateList, dlLogMap, ulLogMap, paramMdl, dateUnit);
    }

    /**
     * <br>[機  能] 月別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param dao Fil270Dao
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @exception SQLException SQL実行時例外
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByMonth(
            Fil270Dao dao, Fil270ParamModel paramMdl, int dateUnit)
                    throws SQLException {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //集計データのMAX日付(現在の日付)とmin日付を取得
        UDate [] logMaxMinDate = __getLogMaxMinDate(con__, dateUnit);
        //年の表示下限をmin日付の1年前に設定
        UDate minLogDate = NullDefault.getUDate(logMaxMinDate[0], new UDate());
        minLogDate.addYear(-1);
        //年の表示上限をMAX日付の1年後に設定
        UDate maxLogDate = new UDate();
        maxLogDate.addYear(1);

        //年月コンボ作成
        paramMdl.setFil270DspYearLabel(StatisticsBiz.getDspYearList(
                minLogDate.getYear(), maxLogDate.getYear(), reqMdl__));
        paramMdl.setFil270DspMonthLabel(StatisticsBiz.getMonthList(reqMdl__));

        //TO年月を設定
        UDate defaultToDate = new UDate();
        String dateToYear = paramMdl.getFil270DateMonthlyToYear();
        String dateToMonth = paramMdl.getFil270DateMonthlyToMonth();
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
        paramMdl.setFil270DateMonthlyToYear(dateToYear);
        paramMdl.setFil270DateMonthlyToMonth(dateToMonth);

        //FROM年月を設定
        //初期表示を12ヶ月分に設定
        UDate defaultFrDate = new UDate();
        defaultFrDate.setYear(Integer.parseInt(dateToYear));
        defaultFrDate.setMonth(Integer.parseInt(dateToMonth));
        defaultFrDate.addYear(-1);
        defaultFrDate.addMonth(1);
        String dateFrYear = paramMdl.getFil270DateMonthlyFrYear();
        String dateFrMonth = paramMdl.getFil270DateMonthlyFrMonth();
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
        paramMdl.setFil270DateMonthlyFrYear(dateFrYear);
        paramMdl.setFil270DateMonthlyFrMonth(dateFrMonth);

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
    private UDate[] __setDateByWeek(Fil270ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //TO年月週を設定
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getFil270DateWeeklyToStr();
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
        String dateFrStr = paramMdl.getFil270DateWeeklyFrStr();
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
        paramMdl.setFil270DateWeeklyFrStr(dateFrStr);
        paramMdl.setFil270DateWeeklyToStr(dateToStr);

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
    private UDate[] __setDateByDay(Fil270ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //直近の一か月分をデフォルトの値として設定する
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getFil270DateDailyToStr();
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
        String dateFrStr = paramMdl.getFil270DateDailyFrStr();
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
        paramMdl.setFil270DateDailyFrStr(dateFrStr);
        paramMdl.setFil270DateDailyToStr(dateToStr);

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
    private ArrayList<Fil270DspModel> __getKikanListByMonth(UDate dateFr, UDate dateTo) {

        ArrayList<Fil270DspModel> ret = new ArrayList<Fil270DspModel>();

        while (dateFr.compareDateYM(dateTo) > UDate.SMALL) {
            Fil270DspModel mdl = new Fil270DspModel();
            mdl.setDate(String.valueOf(dateFr.getYear()) + dateFr.getStrMonth());
            mdl.setuDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymJ(dateFr, reqMdl__));
            mdl.setDownloadNum(0);
            mdl.setStrDownloadNum("0");
            mdl.setUploadNum(0);
            mdl.setStrUploadNum("0");
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
    private ArrayList<Fil270DspModel> __getKikanListByWeek(UDate dateFr,  UDate dateTo) {

        ArrayList<Fil270DspModel> ret = new ArrayList<Fil270DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            //該当週の終了日
            UDate dateFrWeekend = dateFr.cloneUDate();
            dateFrWeekend.addDay(6);

            Fil270DspModel mdl = new Fil270DspModel();
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
            mdl.setDownloadNum(0);
            mdl.setStrDownloadNum("0");
            mdl.setUploadNum(0);
            mdl.setStrUploadNum("0");
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
    private ArrayList<Fil270DspModel> __getKikanListByDay(UDate dateFr, UDate dateTo) {

        ArrayList<Fil270DspModel> ret = new ArrayList<Fil270DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            Fil270DspModel mdl = new Fil270DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setuDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymdJwithStrWeek(dateFr, reqMdl__));
            mdl.setDownloadNum(0);
            mdl.setStrDownloadNum("0");
            mdl.setUploadNum(0);
            mdl.setStrUploadNum("0");
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
    private static List<Fil270DspModel> __getPageDspDateList(
            ArrayList<Fil270DspModel> dateList, int nowPage, int limit) {

        List<Fil270DspModel> ret = new ArrayList<Fil270DspModel>();

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
     * @param dayDlLogMap ダウンロード集計情報
     * @param dayUlLogMap アップロード集計情報
     * @param dateList 日付リスト
     * @return 週毎の集計情報のリスト
     */
    private List<Map<String, Fil270DspModel>> __getLogMapByWeek(
            Map<String, Fil270DspModel> dayDlLogMap,
            Map<String, Fil270DspModel> dayUlLogMap,
            List<Fil270DspModel> dateList) {
        Map<String, Fil270DspModel> dlLogMap = new HashMap<String, Fil270DspModel>();
        Map<String, Fil270DspModel> ulLogMap = new HashMap<String, Fil270DspModel>();
        List<Map<String, Fil270DspModel>> ret = new ArrayList<Map<String, Fil270DspModel>>();

        //週別表示用に日毎のデータを週単位にまとめ、表示用リストへ格納する
        for (Fil270DspModel mdl : dateList) {

            //対象の週の開始日と終了日
            String strBeginOfWeek = mdl.getDate();
            UDate beginOfWeek = mdl.getuDate();
            UDate endOfWeek = beginOfWeek.cloneUDate();
            endOfWeek.addDay(6);

            //1週間分をまとめる
            int dlNum = 0;
            int ulNum = 0;
            UDate targetDay = beginOfWeek.cloneUDate();
            while (targetDay.compareDateYMD(endOfWeek) != UDate.SMALL) {
                String strTargetDay = targetDay.getStrYear()
                                                + "-" + targetDay.getStrMonth()
                                                + "-" + targetDay.getStrDay();
                Fil270DspModel dlLogMdl = dayDlLogMap.get(strTargetDay);
                Fil270DspModel ulLogMdl = dayUlLogMap.get(strTargetDay);
                if (dlLogMdl != null) {
                    dlNum = dlNum + dlLogMdl.getDownloadNum();
                }
                if (ulLogMdl != null) {
                    ulNum = ulNum + ulLogMdl.getUploadNum();
                }
                targetDay.addDay(1);
            }

            //ダウンロード集計一覧に1週間分まとめたデータを格納する
            Fil270DspModel graphDlMdl = new Fil270DspModel();
            graphDlMdl.setDate(strBeginOfWeek);
            graphDlMdl.setDownloadNum(dlNum);
            dlLogMap.put(strBeginOfWeek, graphDlMdl);

            //アップロード集計一覧に1週間分まとめたデータを格納する
            Fil270DspModel graphUlMdl = new Fil270DspModel();
            graphUlMdl.setDate(strBeginOfWeek);
            graphUlMdl.setUploadNum(ulNum);
            ulLogMap.put(strBeginOfWeek, graphUlMdl);
        }

        ret.add(dlLogMap);
        ret.add(ulLogMap);
        return ret;
    }

    /**
     * <br>[機  能] アップロードとダウンロードの集計データから最古日付と最新日付を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param dateUnit 日付単位
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行時例外
     */
    private UDate[] __getLogMaxMinDate(Connection con, int dateUnit) throws SQLException {
        Fil270Dao dao = new Fil270Dao(con);
        UDate[] ret = null;

        //アップロードとダウンロードの最古日時・最大日時を取得
        UDate[] ulMaxMin = dao.getUploadLogSumMaxMinDate();
        UDate[] dlMaxMin = dao.getDownloadLogSumMaxMinDate();

        //日付の最小値
        UDate minUpload = ulMaxMin[0];
        UDate minDownload = dlMaxMin[0];
        UDate minLogDate = new UDate();
        if (minUpload != null && minDownload == null) {
            //アップロード日の最古日時がnullではなく、ダウンロード日の最古日時がnullである場合
            minLogDate = minUpload;
        } else if (minUpload == null && minDownload != null) {
            //アップロード日の最古日時がnullであり、ダウンロード日の最古日時がnullではない場合
            minLogDate = minDownload;
        } else if (minUpload != null && minDownload != null) {
            //アップロード日の最古日時とダウンロード日の最古日時がnullではない場合
            if (minLogDate.compare(minUpload, minDownload) == UDate.LARGE) {
                minLogDate = minUpload;
            } else {
                minLogDate = minDownload;
            }
        }

        //日付の最大値
        UDate maxUpload = ulMaxMin[1];
        UDate maxDownload = dlMaxMin[1];
        UDate maxLogDate = new UDate();
        if (maxUpload != null && maxDownload == null) {
            //アップロード日の最新日時がnullではなく、ダウンロード日の最新日時がnullである場合
            maxLogDate = maxUpload;
        } else if (maxUpload == null && maxDownload != null) {
            //アップロード日の最新日時がnullであり、ダウンロード日の最新日時がnullではない場合
            maxLogDate = maxDownload;
        } else if (maxUpload != null && maxDownload != null) {
            //アップロード日の最新日時とダウンロード日の最新日時がnullではない場合
            if (maxLogDate.compare(maxUpload, maxDownload) == UDate.LARGE) {
                maxLogDate = maxDownload;
            } else {
                maxLogDate = maxUpload;
            }
        }

        ret = new UDate[] {minLogDate, maxLogDate};
        return ret;
    }

    /**
     * <br>[機  能] グラフ用データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonDateList グラフ用日付リスト
     * @param dlLogMap ダウンロード集計一覧
     * @param ulLogMap アップロード集計一覧
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     */
    private void __makeGraphData(
            ArrayList<Fil270DspModel> jsonDateList,
            Map<String, Fil270DspModel> dlLogMap,
            Map<String, Fil270DspModel> ulLogMap,
            Fil270ParamModel paramMdl,
            int dateUnit) {

        StringBuilder jsonDateData = new StringBuilder();
        StringBuilder jsonDownloadLogData = new StringBuilder();
        StringBuilder jsonUploadLogData = new StringBuilder();
        for (int i = 0; i < jsonDateList.size(); i++) {
            if (i == 0) {
                jsonDateData.append("[");
                jsonDownloadLogData.append("[");
                jsonUploadLogData.append("[");
            }

            Fil270DspModel dataMdl = jsonDateList.get(i);
            UDate date = dataMdl.getuDate();
            Fil270DspModel jcirLogMdl = dlLogMap.get(dataMdl.getDate());
            Fil270DspModel scirLogMdl = ulLogMap.get(dataMdl.getDate());

            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                jsonDateData.append("'" + date.getStrYear() + "/" + date.getStrMonth() + "'");
            } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "-'");
            } else {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "'");
            }

            if (jcirLogMdl != null) {
                jsonDownloadLogData.append(jcirLogMdl.getDownloadNum());
            } else {
                jsonDownloadLogData.append("0");
            }

            if (scirLogMdl != null) {
                jsonUploadLogData.append(scirLogMdl.getUploadNum());
            } else {
                jsonUploadLogData.append("0");
            }

            if (i == jsonDateList.size() - 1) {
                jsonDateData.append("]");
                jsonDownloadLogData.append("]");
                jsonUploadLogData.append("]");
            } else {
                jsonDateData.append(",");
                jsonDownloadLogData.append(",");
                jsonUploadLogData.append(",");
            }
        }
        paramMdl.setJsonDateData(jsonDateData.toString());
        paramMdl.setJsonDownloadData(jsonDownloadLogData.toString());
        paramMdl.setJsonUploadData(jsonUploadLogData.toString());
    }
}