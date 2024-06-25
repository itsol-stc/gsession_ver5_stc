package jp.groupsession.v2.rsv.rsv310;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvWeekModelBeforConv;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] スケジュール 施設予約一覧POPUP(日間)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv310Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv310Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set HttpServletRequest
     * @param reqMdl RequestModel
     */
    public Rsv310Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 初期表示画面情報を取得します
     * @param paramMdl Sch120ParamModel
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Rsv310ParamModel getInitData(
            Rsv310ParamModel paramMdl,
            Connection con,
            PluginConfig pconfig) throws SQLException {


        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        //１時間あたりのメモリ数は5分(12)で固定
        int memCount = GSConstSchedule.HOUR_DIVISION_COUNT_5;


        //開始時間、終了時間
        int hourFr = GSConstReserve.DEFAULT_START_HOUR;
        int hourTo = GSConstReserve.DEFAULT_END_HOUR;

        //管理者設定取得
        RsvAdmConfDao admconfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel admMdl = admconfDao.select();

        //個人設定取得
        RsvUserModel rsvUsr = _isRsvUser(reqMdl__, con);
        //日間表示時間帯区分 = 管理者強制 の場合、管理者設定の時間帯を使用する
        if (admMdl != null && admMdl.getRacDtmKbn() == GSConstReserve.RAC_DTMKBN_ADM) {
            hourFr = admMdl.getRacDtmFr();
            hourTo = admMdl.getRacDtmTo();
        } else {
            //個人設定取得
            if (rsvUsr != null) {
                //個人設定がされている場合は表示時間を取得
                hourFr = rsvUsr.getRsuDtmFr();
                hourTo = rsvUsr.getRsuDtmTo();
            } else if (admMdl != null) {
                //個人設定が存在しない場合は管理者設定の表示時間を取得
                hourFr = admMdl.getRacDtmFr();
                hourTo = admMdl.getRacDtmTo();
            }
        }

        //リロード時間
        int reloadTime = GSConstReserve.AUTO_RELOAD_10MIN;
        if (rsvUsr != null) {
            reloadTime = rsvUsr.getRsuReload();
        }
        paramMdl.setRsv310Reload(reloadTime);

        int totalCols = (hourTo - hourFr + 1) * memCount + 2;


        //表示開始日
        String strDspDate = null;
        if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV110
                || paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV111) {
            //選択したに日にち
            strDspDate = NullDefault.getString(
                    paramMdl.getRsv310DspDate(), new UDate().getDateString());
        } else if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV210) {
            if (paramMdl.getRsv310InitFlg() == 0) {
                //複数選択した施設の中
                strDspDate = null;
                int date = 0;
                for (int idx = 0; idx < paramMdl.getRsvIkkatuTorokuKey().length; idx++) {
                    String key = paramMdl.getRsvIkkatuTorokuKey()[idx];
                    //キーから日付部分を取得
                    String hiddDayKey = key.substring(0, key.indexOf("-"));
                    if (idx == 0) {
                        date = Integer.valueOf(hiddDayKey);
                    } else {
                        if (date > Integer.valueOf(hiddDayKey)) {
                            date = Integer.valueOf(hiddDayKey);
                        }
                    }
                }

                if (date != 0) {
                    strDspDate = String.valueOf(date);
                } else {
                    strDspDate = new UDate().getDateString();
                }

            } else {
                strDspDate = NullDefault.getString(
                        paramMdl.getRsv310DspDate(), new UDate().getDateString());
            }
        }


        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);


        //施設予約一覧（日間）情報を取得
        setYoyakuDay(paramMdl, hourFr, hourTo, memCount, con);

        //スケジュール情報を取得
        //個人設定よりデフォルト表示グループSIDを取得する。
        //データが存在しない場合、グループが削除されていた場合はデフォルト所属グループを返す
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);

        //デフォルト表示グループ
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        boolean viewGrpFlg = 
                SchCommonBiz.isMyGroupSid(dfGpSidStr)
                || SchCommonBiz.isDspListSid(dfGpSidStr);
        //表示グループ
        int dspGpSid = SchCommonBiz.getDspGroupSid(dfGpSidStr);


        //表示項目取得
        paramMdl.setRsv310DspDate(strDspDate);
        paramMdl.setRsv310StrDate(getHeaderDate(dspDate));
        paramMdl.setRsv310FromHour(String.valueOf(hourFr));
        paramMdl.setRsv310ToHour(String.valueOf(hourTo));
        paramMdl.setRsv310TotalCols(String.valueOf(totalCols));
        paramMdl.setRsv310MemoriCount(String.valueOf(memCount));
        paramMdl.setRsv310TimeChartList(__getTimeChart(hourFr, hourTo));

        CommonBiz cmnBiz = new CommonBiz();
        //一覧のヘッダに表示する六曜を設定
        if (cmnBiz.getRokuyoDspKbn(con, sessionUsrSid)) {
            paramMdl.setRsvDispRokuyou(cmnBiz.getRokuyou(dspDate));
        }

        //表示するユーザSIDをリストへ設定
        ArrayList<Integer> usrSids = __getDspUserList(paramMdl, sessionUsrSid);

        //特例アクセスでアクセス可能なユーザのみ取得する
        usrSids = scBiz.getAccessUserList(con, sessionUsrSid, usrSids);

        //本人グループのスケジュールを取得
        paramMdl.setSch010TopList(
                __getDayScheduleTopList(
                        dspDate.cloneUDate(),
                        dspGpSid,
                        hourFr,
                        hourTo,
                        sessionUsrSid,
                        viewGrpFlg,
                        pconfig,
                        con));

        int rsvSchKbn = 0;
        String schGroupSid = null;
        if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV110) {
            rsvSchKbn = paramMdl.getRsv110SchKbn();
            schGroupSid = paramMdl.getRsv110SchGroupSid();
        } else if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV111) {
            rsvSchKbn = paramMdl.getRsv111SchKbn();
            schGroupSid = paramMdl.getRsv111SchGroupSid();
        } else if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV210) {
            rsvSchKbn = paramMdl.getRsv210SchKbn();
            schGroupSid = paramMdl.getRsv210SchGroupSid();
        }
        //同時登録 グループ
        if (rsvSchKbn == GSConstReserve.RSV_SCHKBN_GROUP) {

            if (Integer.valueOf(schGroupSid) > -1) {
                SchDao scheduleDao = new SchDao(con);
                boolean accessGrp = scheduleDao.canAccessGroupSchedule(
                        Integer.valueOf(schGroupSid), sessionUsrSid);
                //アクセス可能か
                if (accessGrp) {
                    paramMdl.setSch010BottomList(
                            __getDayScheduleBottomGroup(
                                    dspDate.cloneUDate(),
                                    Integer.valueOf(schGroupSid),
                                    hourFr,
                                    hourTo,
                                    sessionUsrSid,
                                    con));
                }
            }
        } else {

            //グループメンバーのスケジュールを取得
            paramMdl.setSch010BottomList(
                    __getDayScheduleBottomList(
                            dspDate.cloneUDate(),
                            usrSids,
                            hourFr,
                            hourTo,
                            sessionUsrSid,
                            con,
                            sessionUsrSid,
                            pconfig));
        }


        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(
                con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);

        if (adminUser) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        paramMdl.setRsv310InitFlg(1);

        return paramMdl;
    }

    /**
     * <br>表示するユーザSIDのリストを取得する
     * @param paramMdl フォーム
     * @param sessionUsrSid セッションユーザSID
     * @return ArrayList ユーザSIDのリスト
     */
    private ArrayList<Integer> __getDspUserList(Rsv310ParamModel paramMdl, int sessionUsrSid) {
        String strSessionUsrSid = String.valueOf(sessionUsrSid);
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        if (paramMdl.getRsv310MoveMode().equals(String.valueOf(GSConstSchedule.MOVE_NO))) {
            if (paramMdl.getSv_users() != null) {
                for (String usrSid : paramMdl.getSv_users()) {
                    if (!usrSid.equals(strSessionUsrSid)) {
                        usrSids.add(Integer.valueOf(usrSid));
                    }
                }
            }
            if (paramMdl.getUsers_l() != null) {
                for (String usrSid : paramMdl.getUsers_l()) {
                    if (!usrSid.equals(strSessionUsrSid)) {
                        usrSids.add(Integer.valueOf(usrSid));
                    }
                }
            }
        } else {
            //拡張
            if (paramMdl.getRsv111SvUsers() != null) {
                for (String usrSid : paramMdl.getRsv111SvUsers()) {
                    if (!usrSid.equals(strSessionUsrSid)) {
                        usrSids.add(Integer.valueOf(usrSid));
                    }
                }
            }
            if (paramMdl.getUsers_l() != null) {
                for (String usrSid : paramMdl.getUsers_l()) {
                    if (!usrSid.equals(strSessionUsrSid)) {
                        usrSids.add(Integer.valueOf(usrSid));
                    }
                }
            }
        }

        return usrSids;
    }


    /**
     * <br>指定日付のタイムチャートを取得する
     * @param frHour 開始時刻
     * @param toHour 終了時刻
     * @return ArrayList タイムチャートリスト
     */
    private ArrayList<String> __getTimeChart(int frHour, int toHour) {

        ArrayList<String> timeList = new ArrayList<String>();
        for (int i = frHour; i <= toHour; i++) {
            timeList.add(String.valueOf(i));
        }
        return timeList;
    }

    /**
     * <br>グループと指定ユーザの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param grpSid 表示グループSID
     * @param fromHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param usrSid ユーザSID
     * @param viewGrpFlg グループスケジュール表示フラグ true:表示 false:非表示
     * @param pconfig プラグインコンフィグ
     * @param con コネクション
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Sch010WeekOfModel> __getDayScheduleTopList(
            UDate dspDate,
            int grpSid,
            int fromHour,
            int toHour,
            int usrSid,
            boolean viewGrpFlg,
            PluginConfig pconfig,
            Connection con) throws SQLException {

        //抽出範囲整形
        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(fromHour);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(toHour);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_END_MILLISECOND);

        //グループ・指定ユーザのcolListを保持
        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();
        //ユーザ情報を保持
        Sch010UsrModel usMdl = null;
        ArrayList < Sch010DayOfModel > colList = null;
        //DBスケジュール情報
        ArrayList < SchDataModel > schDataList = null;
        //ユーザ別、１週間分のスケジュール
        Sch010WeekOfModel weekMdl = null;

        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        Sch010DayOfModel dayMdl = null;
        ArrayList < SimpleScheduleModel > dayMdlList = null;
        SimpleScheduleModel dspSchMdl = null;
        UDate date = dspDate.cloneUDate();
        SchDataModel schMdl = null;

        //閲覧可能グループかを判定
        boolean accessGrp = true;
        if (!viewGrpFlg) {
            SchDao scheduleDao = new SchDao(con);
            accessGrp = scheduleDao.canAccessGroupSchedule(grpSid, usrSid);
        }

        //グループ
        if (!viewGrpFlg && accessGrp) {
            GroupDao grpDao = new GroupDao(con);
            CmnGroupmModel grpMdl = grpDao.getGroup(grpSid);
            weekMdl = new Sch010WeekOfModel();
            colList = new ArrayList<Sch010DayOfModel>();
            usMdl = new Sch010UsrModel();
            usMdl.setUsrSid(grpSid);
            usMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
            usMdl.setZaisekiKbn(GSConst.UIOSTS_IN);

            CommonBiz cmnBiz = new CommonBiz();
            //在席管理は利用可能か判定
            if (!cmnBiz.isCanUsePlugin(GSConstReserve.PLUGIN_ID_ZAISEKI, pconfig)) {
                weekMdl.setZaisekiUseOk(GSConstReserve.PLUGIN_NOT_USE);
            }
            if (grpMdl != null) {
                usMdl.setUsrName(grpMdl.getGrpName());
            }
            weekMdl.setSch010UsrMdl(usMdl);

            //スケジュール情報を取得(グループ)
            schDataList = schDao.select(
                    grpSid,
                    GSConstSchedule.USER_KBN_GROUP,
                    GSConstSchedule.DSP_PUBLIC,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_DAY,
                    usrSid,
                    true);

            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleScheduleModel>();
            dayMdl = new Sch010DayOfModel();
            dayMdl.setSchDate(date.getDateString());
            dayMdl.setUsrSid(grpSid);
            dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
            dayMdl.setWeekKbn(date.getWeek());
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                dspSchMdl = new SimpleScheduleModel();
                dspSchMdl.setSchSid(schMdl.getScdSid());
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setTime(getTimeString(schMdl, frDate, toDate));
                dspSchMdl.setPublic(schMdl.getScdPublic());
                dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                dspSchMdl.setFromDate(schMdl.getScdFrDate());
                dspSchMdl.setToDate(schMdl.getScdToDate());
                dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                dspSchMdl.setValueStr(schMdl.getScdValue());

                //鍵アイコン表示判定
                SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
                dspSchMdl.setPublicIconFlg(
                        scBiz.getPublicIconFlg(schMdl, usrSid, true, false));
                dayMdlList.add(dspSchMdl);
            }
            dayMdl.setSchDataList(dayMdlList);
            colList.add(dayMdl);
            weekMdl.setSch010SchList(colList);
            rowList.add(weekMdl);
        }

        //指定ユーザ
        weekMdl = new Sch010WeekOfModel();
        colList = new ArrayList<Sch010DayOfModel>();
        usMdl = new Sch010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
        usMdl.setZaisekiKbn(usrInfMdl.getUioStatus());
        usMdl.setZaisekiMsg(usrInfMdl.getUioComment());

        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConstReserve.PLUGIN_ID_ZAISEKI, pconfig)) {
            weekMdl.setZaisekiUseOk(GSConstReserve.PLUGIN_NOT_USE);
        }
        weekMdl.setSch010UsrMdl(usMdl);

        //スケジュール情報を取得(指定ユーザ)
        schDataList = schDao.select(
                usrSid,
                GSConstSchedule.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_DAY,
                usrSid);

        date = dspDate.cloneUDate();
        //１日分のスケジュール
        dayMdlList = new ArrayList<SimpleScheduleModel>();
        dayMdl = new Sch010DayOfModel();
        dayMdl.setHolidayName(null);
        dayMdl.setSchDate(date.getDateString());
        dayMdl.setUsrSid(usrSid);
        dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
        dayMdl.setWeekKbn(date.getWeek());
        schMdl = null;
        for (int j = 0; j < schDataList.size(); j++) {
            //スケジュール１個
            schMdl = schDataList.get(j);
            dspSchMdl = new SimpleScheduleModel();
            dspSchMdl.setSchSid(schMdl.getScdSid());
            dspSchMdl.setTitle(schMdl.getScdTitle());
            dspSchMdl.setTime(getTimeString(schMdl, frDate, toDate));
            dspSchMdl.setPublic(schMdl.getScdPublic());
            dspSchMdl.setBgColor(schMdl.getScdBgcolor());
            dspSchMdl.setFromDate(schMdl.getScdFrDate());
            dspSchMdl.setToDate(schMdl.getScdToDate());
            dspSchMdl.setTimeKbn(schMdl.getScdDaily());
            dspSchMdl.setValueStr(schMdl.getScdValue());

            //鍵アイコン表示判定
            SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
            dspSchMdl.setPublicIconFlg(
                    scBiz.getPublicIconFlg(schMdl, usrSid, true, false));
            dayMdlList.add(dspSchMdl);

        }
        dayMdl.setSchDataList(dayMdlList);
        colList.add(dayMdl);
        weekMdl.setSch010SchList(colList);
        rowList.add(weekMdl);

        return rowList;
    }

    /**
     * <br>表示グループに所属するユーザの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param usrSids 表示ユーザSID
     * @param frHour 開始時間
     * @param toHour 終了時間
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param pconfig プラグインコンフィグ
     * @return ArrayList 役職>姓カナ>名カナの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Sch010WeekOfModel> __getDayScheduleBottomList(
            UDate dspDate,
            ArrayList<Integer> usrSids,
            int frHour,
            int toHour,
            int usrSid,
            Connection con,
            int sessionUsrSid,
            PluginConfig pconfig) throws SQLException {

        //所属ユーザを取得
        UserSearchDao usDao = new UserSearchDao(con);

        //スケジュール個人設定で取得した表示順を取得する。
        SchCommonBiz sBiz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = sBiz.getSchPriConfModel(con, usrSid);
        int sortKey1 = pconf.getSccSortKey1();
        int orderKey1 = pconf.getSccSortOrder1();
        int sortKey2 = pconf.getSccSortKey2();
        int orderKey2 = pconf.getSccSortOrder2();

        //グループメンバー取得
        ArrayList<UserSearchModel> belongList = null;
        belongList = usDao.getUsersInfoJtkb(usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
        //一括で生成する様に変更
        ArrayList<Sch010WeekOfModel> rowList = getDailyUserScheduleNew(
                belongList, dspDate.cloneUDate(), frHour, toHour, usrSid, con, pconfig);
        return rowList;
    }

    /**
     * <br>スケジュール登録に選択したグループの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param grpSid グループSID
     * @param frHour 開始時間
     * @param toHour 終了時間
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @return ArrayList 役職>姓カナ>名カナの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Sch010WeekOfModel> __getDayScheduleBottomGroup(
            UDate dspDate,
            int grpSid,
            int frHour,
            int toHour,
            int usrSid,
            Connection con) throws SQLException {

        //抽出範囲整形
        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(frHour);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(toHour);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_END_MILLISECOND);

        //グループ・指定ユーザのcolListを保持
        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();
        //ユーザ情報を保持
        Sch010UsrModel usMdl = null;
        ArrayList < Sch010DayOfModel > colList = null;
        //DBスケジュール情報
        ArrayList < SchDataModel > schDataList = null;
        //ユーザ別、１週間分のスケジュール
        Sch010WeekOfModel weekMdl = null;

        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        Sch010DayOfModel dayMdl = null;
        ArrayList < SimpleScheduleModel > dayMdlList = null;
        SimpleScheduleModel dspSchMdl = null;
        UDate date = dspDate.cloneUDate();
        SchDataModel schMdl = null;

        GroupDao grpDao = new GroupDao(con);
        CmnGroupmModel grpMdl = grpDao.getGroup(grpSid);
        weekMdl = new Sch010WeekOfModel();
        colList = new ArrayList<Sch010DayOfModel>();
        usMdl = new Sch010UsrModel();
        usMdl.setUsrSid(grpSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
        usMdl.setZaisekiKbn(GSConst.UIOSTS_IN);

        if (grpMdl != null) {
            usMdl.setUsrName(grpMdl.getGrpName());
        }
        weekMdl.setSch010UsrMdl(usMdl);

        //スケジュール情報を取得(グループ)
        schDataList = schDao.select(
                grpSid,
                GSConstSchedule.USER_KBN_GROUP,
                GSConstSchedule.DSP_PUBLIC,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_DAY,
                usrSid,
                true);

        //１日分のスケジュール
        dayMdlList = new ArrayList<SimpleScheduleModel>();
        dayMdl = new Sch010DayOfModel();
        dayMdl.setSchDate(date.getDateString());
        dayMdl.setUsrSid(grpSid);
        dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
        dayMdl.setWeekKbn(date.getWeek());
        for (int j = 0; j < schDataList.size(); j++) {
            //スケジュール１個
            schMdl = schDataList.get(j);
            dspSchMdl = new SimpleScheduleModel();

            //グループに所属しているか判定
            GroupBiz gBiz = new GroupBiz();
            boolean belongGrpHnt =
                    gBiz.isBelongGroup(usrSid, grpSid, con);

            //他ユーザ
            if (schMdl.getScdAuid() == usrSid
                    || schMdl.getScdEuid() == usrSid) {
                //登録者、編集者の場合は表示する
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
                dspSchMdl.setTitle(schMdl.getScdTitle());

            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                    && belongGrpHnt) {
                //非公開かつ所属ユーザ
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
                dspSchMdl.setTitle(schMdl.getScdTitle());
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                    && !belongGrpHnt) {
                //非公開かつ所属ユーザではない
                continue;
            } else {
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
            }

            dspSchMdl.setSchSid(schMdl.getScdSid());
            dspSchMdl.setTime(getTimeString(schMdl, frDate, toDate));
            dspSchMdl.setBgColor(schMdl.getScdBgcolor());
            dspSchMdl.setFromDate(schMdl.getScdFrDate());
            dspSchMdl.setToDate(schMdl.getScdToDate());
            dspSchMdl.setTimeKbn(schMdl.getScdDaily());
            dspSchMdl.setValueStr(schMdl.getScdValue());
            dayMdlList.add(dspSchMdl);
        }
        dayMdl.setSchDataList(dayMdlList);
        colList.add(dayMdl);
        weekMdl.setSch010SchList(colList);
        rowList.add(weekMdl);


        return rowList;
    }

    /**
     * <br>ユーザ毎の日間のスケジュールを取得します
     * @param belongList ユーザ情報リスト
     * @param dspDate 表示開始日付
     * @param frHour 開始時間
     * @param toHour 終了時間
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @return Sch010WeekOfModel 週間スケジュール
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Sch010WeekOfModel> getDailyUserScheduleNew(
            ArrayList<UserSearchModel> belongList,
            UDate dspDate,
            int frHour,
            int toHour,
            int usrSid,
            Connection con,
            PluginConfig pconfig) throws SQLException {

        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();

        //取得範囲整形
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(frHour);
        fromDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        fromDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(toHour);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_END_MILLISECOND);
        Sch010WeekOfModel weekMdl = null;

        //スケジュール情報を取得(指定ユーザ)
        //DBスケジュール情報
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        ArrayList<SchDataModel> schDataList = schDao.selectUsers(
                belongList,
                GSConstSchedule.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstSchedule.DSP_MOD_DAY);
        //所属ユーザループ
        UserSearchModel dbUsrMdl = null;
        for (int i = 0; i < belongList.size(); i++) {
            dbUsrMdl = belongList.get(i);
            //ユーザ別に１週間のスケジュールを取得
            weekMdl = __getUserWeekOfModel(dspDate, dbUsrMdl,
                    schDataList, fromDate, toDate, usrSid, con, pconfig);
            rowList.add(weekMdl);
        }
        return rowList;
    }

    /**
     * ユーザ毎の１日のスケジュールモデルを生成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param dspDate 表示開始日
     * @param dbUsrMdl 生成対象のユーザ情報
     * @param schDataList スケジュール情報
     * @param fromDate 開始日付
     * @param toDate 終了日付
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @return Sch010WeekOfModel
     * @throws SQLException SQL実行時例外
     */
    private Sch010WeekOfModel __getUserWeekOfModel(
            UDate dspDate,
            UserSearchModel dbUsrMdl,
            ArrayList < SchDataModel > schDataList,
            UDate fromDate,
            UDate toDate,
            int sessionUsrSid,
            Connection con,
            PluginConfig pconfig) throws SQLException {

        Sch010WeekOfModel weekMdl = new Sch010WeekOfModel();
        ArrayList<Sch010DayOfModel> colList = new ArrayList<Sch010DayOfModel>();
        //指定ユーザ
        int usrSid = dbUsrMdl.getUsrSid();
        Sch010UsrModel usMdl = new Sch010UsrModel();
        usMdl.setUsrName(dbUsrMdl.getUsiSei() + " " + dbUsrMdl.getUsiMei());
        usMdl.setSchUkoFlg(dbUsrMdl.getUsrUkoFlg());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
        usMdl.setSchUkoFlg(dbUsrMdl.getUsrUkoFlg());
        usMdl.setZaisekiKbn(dbUsrMdl.getUioStatus());
        usMdl.setZaisekiMsg(dbUsrMdl.getUioComment());
        usMdl.setSchUkoFlg(dbUsrMdl.getUsrUkoFlg());
        weekMdl.setSch010UsrMdl(usMdl);

        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConstReserve.PLUGIN_ID_ZAISEKI, pconfig)) {
            weekMdl.setZaisekiUseOk(GSConstReserve.PLUGIN_NOT_USE);
        }

        UDate date = dspDate.cloneUDate();
        ArrayList<SimpleScheduleModel> dayMdlList = null;
        Sch010DayOfModel dayMdl = null;
        SimpleScheduleModel dspSchMdl = null;

        //１日分のスケジュール
        dayMdlList = new ArrayList<SimpleScheduleModel>();
        dayMdl = new Sch010DayOfModel();
        dayMdl.setHolidayName(null);
        dayMdl.setSchDate(date.getDateString());
        dayMdl.setUsrSid(usrSid);
        dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
        dayMdl.setWeekKbn(date.getWeek());
        SchDataModel schMdl = null;
        //予定あり
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textYoteiari = gsMsg.getMessage("schedule.src.9");

        //セッションユーザの所属グループを格納
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        List<Integer> belongGpSidList = bdao.selectUserBelongGroupSid(sessionUsrSid);
        boolean schReference = false;
        boolean publicUserMatch = false;

        for (int j = 0; j < schDataList.size(); j++) {
            //スケジュール１個
            schMdl = schDataList.get(j);
            if (schMdl.getScdUsrSid() != usrSid) {
                continue;
            }

            //指定ユーザと所属グループが一致しているか
            boolean belongGrpHnt = __getSchUsrBelongHnt(schMdl.getScdUserBlongGpList(),
                    belongGpSidList);

            //本日のスケジュールか判定
            dspSchMdl = new SimpleScheduleModel();
            dspSchMdl.setSchSid(schMdl.getScdSid());

            if (schMdl.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
                //公開
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setPublic(schMdl.getScdPublic());
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
                //予定あり
                dspSchMdl.setTitle(textYoteiari);
                dspSchMdl.setPublic(schMdl.getScdPublic());
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                //非公開
                continue;
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                    && belongGrpHnt) {
                //所属グループのみ公開
               dspSchMdl.setTitle(schMdl.getScdTitle());
               dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);
               dspSchMdl.setPublic(schMdl.getScdPublic());

            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                    && !(belongGrpHnt)) {
                //閲覧可能な所属グループではないユーザには「予定あり」
                dspSchMdl.setTitle(textYoteiari);
                dspSchMdl.setPublic(GSConstSchedule.DSP_YOTEIARI);

            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                //指定ユーザグループのみ公開
                SchDataPubDao scdDao = new SchDataPubDao(con);
                schReference = scdDao.select(dspSchMdl.getSchSid(), sessionUsrSid);
                if (schReference) {
                    dspSchMdl.setTitle(schMdl.getScdTitle());
                    publicUserMatch = true;
                } else {
                    dspSchMdl.setTitle(textYoteiari);
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                }
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_TITLE) {
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setPublic(schMdl.getScdPublic());
            }

            //鍵アイコン表示判定
            SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
            dspSchMdl.setPublicIconFlg(
                    scBiz.getPublicIconFlg(schMdl, sessionUsrSid, belongGrpHnt, publicUserMatch));

            if (schMdl.getScdAuid() == sessionUsrSid
                    || schMdl.getScdEuid() == sessionUsrSid
                    || (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                        && belongGrpHnt)
                    || (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP
                        && schReference)) {
                //他ユーザ
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
            }
            dspSchMdl.setTime(getTimeString(schMdl, fromDate, toDate));
            dspSchMdl.setBgColor(schMdl.getScdBgcolor());
            dspSchMdl.setFromDate(schMdl.getScdFrDate());
            dspSchMdl.setToDate(schMdl.getScdToDate());
            dspSchMdl.setTimeKbn(schMdl.getScdDaily());
            dspSchMdl.setValueStr(schMdl.getScdValue());
            dayMdlList.add(dspSchMdl);

        }
        dayMdl.setSchDataList(dayMdlList);
        colList.add(dayMdl);
        weekMdl.setSch010SchList(colList);
        return weekMdl;
    }


    /**
     * <br>スケジュール時間表示を画面表示用に編集します
     * @param schMdl スケジュール情報
     * @param dFrDate 表示開始日時
     * @param dToDate 表示終了日時
     * @return String 画面表示用時間
     */
    public static String getTimeString(SchDataModel schMdl, UDate dFrDate, UDate dToDate) {

        StringBuilder buf = new StringBuilder();
        UDate frDate = schMdl.getScdFrDate();
        UDate toDate = schMdl.getScdToDate();
        UDate cmpToDate = null;
        if (dToDate.getIntHour() == GSConstSchedule.DAY_END_HOUR) {
            cmpToDate = dToDate.cloneUDate();
            cmpToDate.addDay(1);
            cmpToDate.setHour(GSConstSchedule.DAY_START_HOUR);
            cmpToDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
            cmpToDate.setSecond(GSConstSchedule.DAY_END_SECOND);
            cmpToDate.setMilliSecond(GSConstSchedule.DAY_END_MILLISECOND);
        } else {
            cmpToDate = dToDate.cloneUDate();
        }


        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            boolean flg = false;
            //スケジュール開始日時が表示範囲か判定
            if (frDate.betweenYMDHM(dFrDate, dToDate)) {
                buf.append(frDate.getStrHour());
                buf.append(":");
                buf.append(frDate.getStrMinute());
                buf.append("-");
                flg = true;
            }
            //スケジュール終了日時が表示範囲か判定
            if (toDate.betweenYMDHM(dFrDate, cmpToDate)) {
                if (flg == false) {
                    buf.append("-");
                }
                if (toDate.getIntHour() == GSConstSchedule.DAY_START_HOUR
                        && toDate.getIntMinute() == GSConstSchedule.DAY_START_MINUTES) {
                    buf.append("24");
                    buf.append(":");
                    buf.append("00");
                } else {
                    buf.append(toDate.getStrHour());
                    buf.append(":");
                    buf.append(toDate.getStrMinute());
                }

            }
        }
        log__.debug("getTimeString ==>" + buf.toString());
        return buf.toString();
    }

    /**
     * <br>ヘッダー部分へ表示する日付文字列を作成する
     * @param date 表示日付
     * @return String 日付文字列(YYYY年MM月DD日(W))
     */
    public String getHeaderDate(UDate date) {

        String ret = "";

        if (date == null) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //年
        String textYear = gsMsg.getMessage("cmn.year", new String[] {date.getStrYear()});
        StringBuilder buf = new StringBuilder();
        buf.append(textYear);
        buf.append(date.getStrMonth());
        buf.append(gsMsg.getMessage("cmn.month"));
        buf.append(date.getStrDay());
        buf.append(gsMsg.getMessage("cmn.day"));
        buf.append("(");
        buf.append(Sch010Biz.getStrWeek(date.getWeek(), reqMdl__));
        buf.append(")");
        ret = buf.toString();
        return ret;
    }


    /**
     * <br>[機  能] 施設予約情報一覧[日間]をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param hourFr 開始時間
     * @param hourTo 終了時間
     * @param memCount １時間のメモリ数(管理者設定値)
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setYoyakuDay(
            Rsv310ParamModel paramMdl, int hourFr, int hourTo, int memCount, Connection con)
                    throws SQLException {
        log__.debug("日間カレンダーと予約情報設定");

        //表示開始日
        String strDspDate =
                NullDefault.getString(
                        paramMdl.getRsv310DspDate(),
                        new UDate().getDateString());

        paramMdl.setRsv310DspDate(strDspDate);

        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //タイムチャート作成
        paramMdl.setRsv020TimeChartList(__getTimeChart(paramMdl, hourFr, hourTo));

        //施設毎の予約情報取得
        paramMdl.setRsv020DaylyList(
                __getDaylyList(
                        paramMdl,
                        dspDate.cloneUDate(),
                        hourFr,
                        hourTo,
                        con));
    }

    /**
     * <br>[機  能] タイムチャートを作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param hourFr 開始時間
     * @param hourTo 終了時間
     * @return timeList タイムチャート
     */
    private ArrayList<String> __getTimeChart(Rsv310ParamModel paramMdl,
            int hourFr,
            int hourTo) {

        ArrayList<String> timeList = new ArrayList<String>();
        for (int i = hourFr; i <= hourTo; i++) {
            timeList.add(String.valueOf(i));
        }
        return timeList;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param dspDate 指定日付
     * @param hourFr 開始時間
     * @param hourTo 終了時間
     * @param con コネクション
     * @return ArrayList 予約リスト
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getDaylyList(Rsv310ParamModel paramMdl,
            UDate dspDate,
            int hourFr,
            int hourTo,
            Connection con)
                    throws SQLException {

        //検索条件 開始時間
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(hourFr);
        fromDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        fromDate.setSecond(GSConstReserve.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了時間
        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(hourTo);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);


        //予約情報取得
        //表示する施設SIDを取得
        ArrayList<Integer> sisetuSids = new ArrayList<Integer>();
        if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV110
                || paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV111) {
            sisetuSids.add(paramMdl.getRsvSelectedSisetuSid());
        } else if (paramMdl.getPopDspMode() == Rsv310Form.POP_DSP_MODE_RSV210) {
            for (String sisSid : paramMdl.getRsvIkkatuTorokuKey()) {
                //キーの施設SID部分を追加
                String hiddSidKey = sisSid.substring(sisSid.indexOf("-") + 1);
                sisetuSids.add(Integer.valueOf(hiddSidKey));
            }
        }


        RsvSisYrkDao dao = new RsvSisYrkDao(con);
        ArrayList<RsvWeekModelBeforConv> daylyList =
                dao.getYrkList(sisetuSids, fromDate, toDate);

        //検索結果を画面表示用に変換
        ArrayList<RsvSisetuModel> ret =
                __getConvDayLyList(paramMdl, daylyList, dspDate, fromDate, toDate, con);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を画面表示用に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param retList 検索結果リスト
     * @param dspDate 表示日付(開始)
     * @param fromDate 表示開始
     * @param toDate 表示終了
     * @param con コネクション
     * @return sisetuList 変換結果
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getConvDayLyList(Rsv310ParamModel paramMdl,
            ArrayList<RsvWeekModelBeforConv> retList,
            UDate dspDate,
            UDate fromDate,
            UDate toDate,
            Connection con
            ) throws SQLException {

        if (retList.isEmpty()) {
            return null;
        }

        int kjnSetteiKbn1 = GSConstReserve.KOJN_SETTEI_DSP_OK;
        int kjnSetteiKbn2 = GSConstReserve.KOJN_SETTEI_DSP_OK;

        int saveRsdSid = -1;
        ArrayList<RsvSisetuModel> sisetuList =
                new ArrayList<RsvSisetuModel>();
        ArrayList<String> dspArray = new ArrayList<String>();

        //施設情報設定
        for (RsvWeekModelBeforConv sisetu : retList) {
            UDate date = dspDate.cloneUDate();
            int rsdSid = sisetu.getRsdSid();
            RsvSisetuModel sisetuMdl = new RsvSisetuModel();;

            if (rsdSid != saveRsdSid) {
                //施設情報セット
                sisetuMdl.setRsgSid(sisetu.getRsgSid());
                sisetuMdl.setRsdSid(sisetu.getRsdSid());
                sisetuMdl.setRsdName(sisetu.getRsdName());
                saveRsdSid = rsdSid;

                ArrayList<RsvYoyakuDayModel> yoyakuDayList =
                        new ArrayList<RsvYoyakuDayModel>();

                RsvYoyakuDayModel yoyakuDayMdl = new RsvYoyakuDayModel();
                yoyakuDayMdl.setYrkDateStr(date.getDateString());

                ArrayList<RsvYoyakuModel> yoyakuList =
                        new ArrayList<RsvYoyakuModel>();

                for (RsvWeekModelBeforConv yrk : retList) {

                    //予約開始日付無し = 外部結合にて施設の情報だけ取得したもの
                    if (saveRsdSid != yrk.getRsdSid()
                            || yrk.getRsyFrDate() == null) {
                        continue;
                    }

                    RsvYoyakuModel yrkDayMdl = new RsvYoyakuModel();
                    yrkDayMdl.setRsySid(yrk.getRsySid());
                    yrkDayMdl.setYrkRiyoDateStr(__getTimeString(yrk, fromDate, toDate));
                    yrkDayMdl.setRsyFrDate(yrk.getRsyFrDate());
                    yrkDayMdl.setRsyToDate(yrk.getRsyToDate());

                    if (kjnSetteiKbn2 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                        yrkDayMdl.setYrkName(yrk.getUsiSei() + "  " + yrk.getUsiMei());
                    }

                    //「予定あり」メッセージ
                    GsMessage gsMsg = new GsMessage(reqMdl__);
                    String textPlans = gsMsg.getMessage("reserve.177");

                    RsvCommonBiz rsvBiz = new RsvCommonBiz();
                    int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
                    int publicFlg = rsvBiz.isCheckPubRange(
                            con, yrk.getRsySid(), sessionUsrSid);

                    //公開区分別表示
                    if (publicFlg == GSConstReserve.POWRANGE_DEL) {
                        continue;
                    }
                    if (publicFlg == GSConstReserve.POWRANGE_IN) {
                        if (kjnSetteiKbn1 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                            yrkDayMdl.setRsyMok(yrk.getRsyMok());
                        }
                        yrkDayMdl.setPublic(GSConstReserve.PUBLIC_KBN_ALL);
                    } else {
                        if (kjnSetteiKbn1 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                            if (publicFlg == GSConstReserve.RANGE_OUT_TITLE) {
                                yrkDayMdl.setRsyMok(yrk.getRsyMok());
                            } else {
                                yrkDayMdl.setRsyMok(textPlans);
                            }
                        }
                        yrkDayMdl.setPublic(GSConstReserve.PUBLIC_KBN_PLANS);
                    }

                    yrkDayMdl.setRsyNaiyo(yrk.getRsyNaiyo());
                    yrkDayMdl.setRsyApprStatus(yrk.getRsyApprStatus());
                    yrkDayMdl.setRsyApprKbn(yrk.getRsyApprKbn());
                    yrkDayMdl.setUsrJKbn(yrk.getUsrJkbn());
                    yrkDayMdl.setUsrUkoFlg(yrk.getUsrUkoFlg());
                    yoyakuList.add(yrkDayMdl);
                }

                String ikkatuKey = date.getDateString() + "-" + String.valueOf(saveRsdSid);
                //一括登録用キー(yyyyMMdd-施設SID)
                yoyakuDayMdl.setIkkatuKey(ikkatuKey);
                dspArray.add(ikkatuKey);

                yoyakuDayMdl.setYoyakuList(yoyakuList);
                yoyakuDayList.add(yoyakuDayMdl);

                RsvWeekModel weekMdl = new RsvWeekModel();
                weekMdl.setYoyakuDayList(yoyakuDayList);
                ArrayList<RsvWeekModel> weekList = new ArrayList<RsvWeekModel>();
                weekList.add(weekMdl);

                sisetuMdl.setRsvWeekList(weekList);
                sisetuList.add(sisetuMdl);
            }
        }

        return sisetuList;
    }

    /**
     * <br>[機  能] 予約時間表示を画面表示用に編集
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yrk 予約情報
     * @param dFrDate 表示開始日時
     * @param dToDate 表示終了日時
     * @return String 画面表示用時間
     */
    private String __getTimeString(RsvWeekModelBeforConv yrk, UDate dFrDate, UDate dToDate) {

        StringBuilder buf = new StringBuilder();
        UDate frDate = yrk.getRsyFrDate();
        UDate toDate = yrk.getRsyToDate();
        UDate cmpToDate = null;

        if (dToDate.getIntHour() == GSConstReserve.DAY_END_HOUR) {
            cmpToDate = dToDate.cloneUDate();
            cmpToDate.addDay(1);
            cmpToDate.setHour(GSConstReserve.DAY_START_HOUR);
            cmpToDate.setMinute(GSConstReserve.DAY_START_MINUTES);
            cmpToDate.setSecond(GSConstReserve.DAY_END_SECOND);
            cmpToDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);
        } else {
            cmpToDate = dToDate.cloneUDate();
        }

        boolean flg = false;

        //スケジュール開始日時が表示範囲か判定
        if (frDate.betweenYMDHM(dFrDate, dToDate)) {
            buf.append(frDate.getStrHour());
            buf.append(":");
            buf.append(frDate.getStrMinute());
            buf.append("-");
            flg = true;
        }
        //スケジュール終了日時が表示範囲か判定
        if (toDate.betweenYMDHM(dFrDate, cmpToDate)) {
            if (flg == false) {
                buf.append("-");
            }
            if (toDate.getIntHour() == GSConstReserve.DAY_START_HOUR
                    && toDate.getIntMinute() == GSConstReserve.DAY_START_MINUTES) {
                buf.append("24");
                buf.append(":");
                buf.append("00");
            } else {
                buf.append(toDate.getStrHour());
                buf.append(":");
                buf.append(toDate.getStrMinute());
            }
        } else {
            if (!flg) {
                buf.append("");
            }
        }

        return buf.toString();
    }
    /**
     * <br>[機  能] 同じグループに所属しているか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param belongSids ユーザ1所属グループSID
     * @param belongSids2 ユーザ2所属グループSID
     * @return 所属フラグ
     */
    private boolean __getSchUsrBelongHnt(List<Integer> belongSids, List<Integer> belongSids2) {
        boolean belongFlg = false;
        if (belongSids != null && !belongSids.isEmpty()
                && belongSids2 != null && !belongSids2.isEmpty()) {
            for (int gpSid : belongSids) {
                if (belongSids2.indexOf(gpSid) > -1) {
                    belongFlg = true;
                    return belongFlg;
                }
            }
        }
        return belongFlg;
    }

}
