package jp.groupsession.v2.rsv.rsv030;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvWeekModelBeforConv;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvHidDayModel;
import jp.groupsession.v2.rsv.model.RsvHidGroupModel;
import jp.groupsession.v2.rsv.model.RsvHidModel;
import jp.groupsession.v2.rsv.model.RsvHidSisetuModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.pdf.RsvGekPdfModel;
import jp.groupsession.v2.rsv.pdf.RsvGekPdfUtil;
import jp.groupsession.v2.rsv.rsv010.Rsv010Biz;
import jp.groupsession.v2.rsv.rsv020.Rsv020Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約一覧 月間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv030Biz extends Rsv010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv020Biz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv030Biz(RequestModel reqMdl, Connection con) {
        super(reqMdl, con);
    }

    /**
     * <br>[機  能] 表示日付の移動を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv030ParamModel
     * @param moveMonth 移動月数
     * @param today 今月へ移動
     */
    public void setMoveDspData(Rsv030ParamModel paramMdl, int moveMonth, boolean today) {

        RsvCommonBiz biz = new RsvCommonBiz();
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addMonth(moveMonth);
        paramMdl.setRsvDspFrom(udate.getDateString());
        paramMdl.setRsv100selectedFromYear(udate.getYear());
        paramMdl.setRsv100selectedFromMonth(udate.getMonth());
        paramMdl.setRsv100selectedFromDay(udate.getIntDay());
    }

    /**
     * <br>[機  能] 自動リロード時間を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Rsv030ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void set030Reloadtime(Connection con, Rsv030ParamModel paramMdl, int sessionUsrSid)
    throws SQLException {

        paramMdl.setRsv030Reload(getReloadtime(con, sessionUsrSid));

    }

    /**
     * <br>[機  能] 施設グループ・施設コンボリストを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv030ParamModel
     * @throws SQLException SQL実行時例外
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Biz
     * @see #setGroupComboList(jp.groupsession.v2.rsv.rsv010.Rsv010Form)
     */
    public void setGroupComboList(Rsv030ParamModel paramMdl) throws SQLException {

        //施設グループ設定
        paramMdl.setRsvGrpLabelList(_getGroupComboList(true, con_, reqMdl_));

        //施設設定
        paramMdl.setRsvSisetuLabelList(
                _getSisetuComboList(
                        paramMdl.getRsvSelectedGrpSid(), con_, reqMdl_));
    }

    /**
     * <br>[機  能] 施設予約情報一覧[月間]をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv030ParamModel
     * @param buModel ユーザモデル
     * @throws SQLException SQL実行時例外
     */
    public void setYoyakuMonth(
            Rsv030ParamModel paramMdl, BaseUserModel buModel) throws SQLException {

        log__.debug("月間カレンダーと予約情報設定");
        RsvCommonBiz biz = new RsvCommonBiz();
        //表示開始日
        String strDspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());

        paramMdl.setRsvDspFrom(strDspDate);

        //一覧のヘッダに表示する年月を設定
        UDate dspDate = new UDate();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        dspDate.setDate(strDspDate);
        paramMdl.setRsvDispYm(
                gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()})
                + dspDate.getStrMonth()
                + gsMsg.getMessage("cmn.month"));

        //カレンダー開始・終了日付
        UDate frDate = __getStartCalenderDate(dspDate);
        UDate toDate = __getEndCalenderDate(dspDate);

        //予約情報取得
        paramMdl.setRsv030MonthList(__getMonthList(paramMdl, dspDate, frDate, toDate));

        int rsdSid = paramMdl.getRsvSelectedSisetuSid();
        if (rsdSid > 0) {
            RsvSisDataDao sisDao = new RsvSisDataDao(con_);
            RsvSisDataModel bean = new RsvSisDataModel();
            bean.setRsdSid(rsdSid);
            RsvSisDataModel sisModel = sisDao.select(bean);
            if (sisModel != null) {
                CommonBiz cmnBiz = new CommonBiz();
                //システム管理者フラグ
                boolean admFlg = cmnBiz.isPluginAdmin(
                        con_, buModel, GSConstReserve.PLUGIN_ID_RESERVE);

                //施設グループのアクセス権限を設定する。
                if (!_isEditRsvGrp(con_, sisModel.getRsgSid(), buModel.getUsrsid(), admFlg)) {
                    paramMdl.setRsv030AccessAuth(GSConstReserve.RSV_ACCESS_KBN_READ);
                }
            }
        }

    }

    /**
     * <br>[機  能] 1ヶ月の予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv030ParamModel
     * @param dspDate 画面表示日付
     * @param frDate カレンダー開始日付
     * @param toDate カレンダー終了日付
     * @return ArrayList 1ヶ月のカレンダー
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvWeekModel> __getMonthList(Rsv030ParamModel paramMdl,
                                                    UDate dspDate,
                                                    UDate frDate,
                                                    UDate toDate)
        throws SQLException {

        //検索条件 開始日付
        frDate.setHour(GSConstReserve.DAY_START_HOUR);
        frDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        frDate.setSecond(GSConstReserve.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了日付
        toDate.setHour(GSConstReserve.DAY_END_HOUR);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con_);
        HashMap<String, CmnHolidayModel> holMap =
            holDao.getHoliDayList(frDate, toDate);

        //1週間の予約情報取得
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        ArrayList<RsvWeekModelBeforConv> weekList =
            dao.getYrkList(
                    paramMdl.getRsvSelectedGrpSid(),
                    paramMdl.getRsvSelectedSisetuSid(),
                    frDate, toDate);

        //検索結果を画面表示用に変換
        ArrayList<RsvWeekModel> ret =
            __getConvMonthList(paramMdl, weekList, holMap, dspDate, frDate, toDate);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を画面表示用に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv030ParamModel
     * @param retList 検索結果リスト
     * @param holMap 休日情報
     * @param dspDate 画面表示日付
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @return convList 変換結果
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvWeekModel> __getConvMonthList(Rsv030ParamModel paramMdl,
                                                        ArrayList<RsvWeekModelBeforConv> retList,
                                                        HashMap<String, CmnHolidayModel> holMap,
                                                        UDate dspDate,
                                                        UDate frDate,
                                                        UDate toDate) throws SQLException {

        int kjnSetteiKbn1 = -1;
        int kjnSetteiKbn2 = -1;

        //施設予約個人設定を取得
        RsvUserModel rsvUsrMdl = _isRsvUser(reqMdl_, con_);
        if (rsvUsrMdl != null) {
            //個人設定がされている場合は設定情報を取得
            kjnSetteiKbn1 = rsvUsrMdl.getRsuDit1();
            kjnSetteiKbn2 = rsvUsrMdl.getRsuDit2();
        } else {
            //個人設定が未設定の場合は全部表示する
            kjnSetteiKbn1 = GSConstReserve.KOJN_SETTEI_DSP_OK;
            kjnSetteiKbn2 = GSConstReserve.KOJN_SETTEI_DSP_OK;
        }

        ArrayList<RsvYoyakuDayModel> yoyakuDayList =
            new ArrayList<RsvYoyakuDayModel>();
        ArrayList<String> dspArray = new ArrayList<String>();

        UDate now = new UDate();
        int todayYear = now.getYear();
        int todayMonth = now.getMonth();
        int todayDay = now.getIntDay();
        CommonBiz cmnBiz = new CommonBiz();

        //該当施設の予約情報を処理
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {

            RsvYoyakuDayModel yoyakuDayMdl = new RsvYoyakuDayModel();
            yoyakuDayMdl.setYrkDateDay(String.valueOf(frDate.getIntDay()));
            yoyakuDayMdl.setYrkDateStr(frDate.getDateString());
            yoyakuDayMdl.setYrkYobi(frDate.getWeek());
            //セッション情報を取得
            HttpSession session = reqMdl_.getSession();
            BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
            int sessionUsrSid = usModel.getUsrsid();
            if (cmnBiz.getRokuyoDspKbn(con_, sessionUsrSid)) {
                yoyakuDayMdl.setYrkRokuyou(cmnBiz.getRokuyou(frDate));
            }

            String todayKbn = "1";
            if (todayYear == frDate.getYear()
                    && todayMonth == frDate.getMonth()
                    && todayDay == frDate.getIntDay()) {
                todayKbn = "0";
            }
            yoyakuDayMdl.setTodayKbn(todayKbn);

            String ikkatuKey
                = frDate.getDateString()
                + "-"
                + String.valueOf(paramMdl.getRsvSelectedSisetuSid());
            //一括登録用キー(yyyyMMdd-施設SID)
            yoyakuDayMdl.setIkkatuKey(ikkatuKey);
            dspArray.add(ikkatuKey);

            //同月判定
            if (__isThisMonth(dspDate, frDate)) {
                yoyakuDayMdl.setYrkMonthKbn(GSConstReserve.SAME_MONTH);
            } else {
                yoyakuDayMdl.setYrkMonthKbn(GSConstReserve.DIFFERENT_MONTH);
            }

            //休日情報セット
            CmnHolidayModel holMdl = holMap.get(frDate.getDateString());
            if (holMdl != null) {
                yoyakuDayMdl.setHolName(holMdl.getHolName());
            }

            ArrayList<RsvYoyakuModel> yoyakuList =
                new ArrayList<RsvYoyakuModel>();

            for (RsvWeekModelBeforConv yrk : retList) {

                if (yrk.getRsyFrDate() == null) {
                    continue;
                }

                //本日の予約か
                if (__isTodayYoyaku(yrk, frDate)) {
                    RsvYoyakuModel yrkDayMdl = new RsvYoyakuModel();
                    yrkDayMdl.setRsySid(yrk.getRsySid());
                    yrkDayMdl.setYrkRiyoDateStr(__getTimeString(yrk, frDate));

                    if (kjnSetteiKbn2 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                        yrkDayMdl.setYrkName(yrk.getUsiSei() + "  " + yrk.getUsiMei());
                    }

                    //セッションユーザが公開区分内にあるか判定
                    RsvCommonBiz rsvBiz = new RsvCommonBiz();
                    int publicFlg = rsvBiz.isCheckPubRange(
                            con_, yrk.getRsySid(), sessionUsrSid);

                    //エラーメッセージ
                    GsMessage gsMsg = new GsMessage(reqMdl_);
                    String textPlan = gsMsg.getMessage("reserve.177");

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
                                yrkDayMdl.setRsyMok(textPlan);
                            }
                        }
                        yrkDayMdl.setPublic(GSConstReserve.PUBLIC_KBN_PLANS);
                    }

                    yrkDayMdl.setRsyApprStatus(yrk.getRsyApprStatus());
                    yrkDayMdl.setRsyApprKbn(yrk.getRsyApprKbn());
                    yrkDayMdl.setRsyNaiyo(yrk.getRsyNaiyo());
                    yrkDayMdl.setUsrJKbn(yrk.getUsrJkbn());
                    yrkDayMdl.setUsrUkoFlg(yrk.getUsrUkoFlg());
                    yoyakuList.add(yrkDayMdl);
                }
            }
            yoyakuDayMdl.setYoyakuList(yoyakuList);
            yoyakuDayList.add(yoyakuDayMdl);
            frDate.addDay(1);
        }

        RsvWeekModel weekMdl = new RsvWeekModel();
        weekMdl.setYoyakuDayList(yoyakuDayList);

        ArrayList<RsvWeekModel> convList = new ArrayList<RsvWeekModel>();
        convList.add(weekMdl);

        if (paramMdl.isRsvIkkatuTorokuFlg()) {
            //選択された施設キーから画面に表示しきれていないキーを取得
            ArrayList<String> hiddArray =
                __getHiddenSelectedList(paramMdl.getRsvIkkatuTorokuKey(), dspArray);
            paramMdl.setRsvSelectedIkkatuTorokuKey(hiddArray);

            Collections.sort(hiddArray);
            String saveDay = null;
            String saveHiddDayKey = null;
            RsvSisDataDao dao = new RsvSisDataDao(con_);
            ArrayList<String> searchArray = new ArrayList<String>();
            ArrayList<RsvHidDayModel> hiddList = new ArrayList<RsvHidDayModel>();

            //画面に表示しきれていないキーがあれば処理
            if (!hiddArray.isEmpty()) {

                for (String hiddKey : hiddArray) {

                    //キーから日付部分を取得
                    String hiddDayKey = hiddKey.substring(0, hiddKey.indexOf("-"));

                    if (saveDay == null) {
                        saveDay = hiddDayKey;
                        saveHiddDayKey = hiddDayKey;
                    } else if (!saveDay.equals(hiddDayKey)) {

                        ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                        hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));

                        //配列とキーを初期化
                        searchArray = new ArrayList<String>();
                        saveDay = hiddDayKey;
                        saveHiddDayKey = hiddDayKey;
                    }

                    //キーの施設SID部分を追加
                    String hiddSidKey = hiddKey.substring(hiddKey.indexOf("-") + 1);
                    searchArray.add(hiddSidKey);
                }

                if (!searchArray.isEmpty()) {
                    //リスト末尾
                    ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                    hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));
                }
            }

            paramMdl.setRsvIkkatuTorokuHiddenList(hiddList);
        }

        return convList;
    }

    /**
     * <br>[機  能] チェック中のhiddenリストを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param checkAry 現在まで入力されているチェックボックスの値
     * @param viewList 一覧データ（最大20件まで）
     * @return hiddenリスト
     */
    private ArrayList<String> __getHiddenSelectedList(String[] checkAry,
                                                       ArrayList<String> viewList) {

        //ハッシュリストの作成
        HashSet<String> map = new HashSet<String>();

        if (viewList != null) {
            for (String viewKey : viewList) {
                map.add(viewKey);
            }
        }

        ArrayList<String> hiddenList = new ArrayList<String>();
        if (checkAry != null) {
            //入力値(hidden値)から表示データのパラメータを除外して登録
            for (int i = 0; i < checkAry.length; i++) {
                if (!map.contains(checkAry[i])) {
                    hiddenList.add(checkAry[i]);
                }
            }
        }

        return hiddenList;
    }

    /**
     * <br>[機  能] DB取得結果を画面表示用に変換する
     * <br>[解  説]
     * <br>
     * <br>   施設グループ    施設
     * <br>       A            1
     * <br>       A            2
     * <br>       A            3
     * <br>       B            4
     * <br>       B            5
     * <br>       B            6
     * <br>
     * <br>   DBから上記の形式で取得したリストを
     * <br>
     * <br>   施設グループ    施設
     * <br>       A            1 2 3
     * <br>       B            4 5 6
     * <br>
     * <br>   の形式へ変換する
     * <br>
     * <br>[備  考]
     *
     * @param hiddDayList DB取得結果リスト
     * @param saveDay キー
     * @param saveHiddDayKey 日付文字列yyyyMMdd
     * @return ret 変換後モデル
     */
    private RsvHidDayModel __getDaylySisetuList(ArrayList<RsvHidModel> hiddDayList,
                                                 String saveDay,
                                                 String saveHiddDayKey) {

        //画面表示用に変換
        int saveRsgSid = -1;
        String saveRsgName = null;
        ArrayList<RsvHidSisetuModel> hidSisetuList =
            new ArrayList<RsvHidSisetuModel>();
        ArrayList<RsvHidGroupModel> hidGroupList =
            new ArrayList<RsvHidGroupModel>();

        RsvHidDayModel day = new RsvHidDayModel();
        UDate udDay = new UDate();
        udDay.setDate(saveDay);
        GsMessage gsMsg = new GsMessage(reqMdl_);
        day.setHidDayStr(
                gsMsg.getMessage("cmn.year", new String[] {udDay.getStrYear()})
                + udDay.getStrMonth()
                + gsMsg.getMessage("cmn.month")
                + udDay.getStrDay()
                + gsMsg.getMessage("cmn.day") + "（"
                + UDateUtil.getStrWeek(udDay.getWeek(), reqMdl_)
                + "）");

        for (RsvHidModel dbMdl : hiddDayList) {

            if (saveRsgSid == -1) {
                saveRsgSid = dbMdl.getRsgSid();
                saveRsgName = dbMdl.getRsgName();

                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
                continue;

            //同じグループに所属する施設をまとめる
            } else if (saveRsgSid == dbMdl.getRsgSid()) {

                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
                continue;

            //グループブレイク
            } else if (saveRsgSid != dbMdl.getRsgSid()) {

                RsvHidGroupModel group = new RsvHidGroupModel();
                group.setRsgName(saveRsgName);
                group.setSisetuList(hidSisetuList);
                hidGroupList.add(group);

                saveRsgSid = dbMdl.getRsgSid();
                saveRsgName = dbMdl.getRsgName();

                hidSisetuList = new ArrayList<RsvHidSisetuModel>();
                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
            }

            day.setGrpList(hidGroupList);
        }
        RsvHidGroupModel group = new RsvHidGroupModel();
        group.setRsgName(saveRsgName);
        group.setSisetuList(hidSisetuList);
        hidGroupList.add(group);
        day.setGrpList(hidGroupList);

        return day;
    }

    /**
     * <br>[機  能] 月間カレンダー表示時の開始日付を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 表示年月
     * @return UDate 開始日付
     */
    private UDate __getStartCalenderDate(UDate date) {

        UDate frDate = date.cloneUDate();
        frDate.setDay(1);

        //曜日を取得
        int week = -1;
        boolean flg = true;
        while (flg) {
            week = frDate.getWeek();
            if (week == UDate.SUNDAY) {
                flg = false;
            } else {
                frDate.addDay(-1);
            }
        }
        return frDate;
    }

    /**
     * <br>[機  能] 月間カレンダー表示時の終了日付を取得します
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 表示年月
     * @return UDate 終了日付
     */
    private UDate __getEndCalenderDate(UDate date) {

        UDate toDate = date.cloneUDate();
        int maxDay = toDate.getMaxDayOfMonth();
        toDate.setDay(maxDay);

        int week = -1;
        boolean flg = true;
        while (flg) {
            week = toDate.getWeek();
            if (week == UDate.SATURDAY) {
                flg = false;
            } else {
                toDate.addDay(1);
            }
        }
        return toDate;
    }

    /**
     * <br>[機  能] 年月が同じが判定します
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 比較対象1
     * @param compDate 比較対象1
     * @return true:同年同月 false:同じではない
     */
    private boolean __isThisMonth(UDate date, UDate compDate) {
        if (date.equalsYear(compDate) && date.equalsMonth(compDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 予約情報が指定日付の予約か判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yrk 予約情報
     * @param date 指定日付
     * @return ret true:指定日 false:指定日以外
     */
    private boolean __isTodayYoyaku(RsvWeekModelBeforConv yrk, UDate date) {

        boolean ret = false;
        UDate frDate = yrk.getRsyFrDate();
        UDate toDate = yrk.getRsyToDate();

        if (frDate.compareDateYMD(date) != UDate.SMALL
                && toDate.compareDateYMD(date) != UDate.LARGE) {

            //Toが0:00の場合は除外する(日またぎの予約としない)
            if (toDate.getYear() == date.getYear()
                    && toDate.getMonth() == date.getMonth()
                    && toDate.getIntDay() == date.getIntDay()
                    && toDate.getIntHour() == GSConstReserve.DAY_START_HOUR
                    && toDate.getIntMinute() == GSConstReserve.DAY_START_MINUTES) {
            } else {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 予約時間表示を画面表示用に編集
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yrk 予約情報
     * @param date 指定日付
     * @return String 画面表示用時間
     */
    private String __getTimeString(RsvWeekModelBeforConv yrk, UDate date) {

        StringBuilder buf = new StringBuilder();
        UDate frDate = yrk.getRsyFrDate();
        UDate toDate = yrk.getRsyToDate();

        boolean flg = false;
        //予約開始日が今日か判定
        if (date.compareDateYMD(frDate) == UDate.EQUAL) {
            buf.append(frDate.getStrHour());
            buf.append(":");
            buf.append(frDate.getStrMinute());
            buf.append("-");
            flg = true;
        }
        //予約終了日が今日か判定
        if (date.compareDateYMD(toDate) == UDate.EQUAL) {
            if (!flg) {
                buf.append("-");
            }
            buf.append(toDate.getStrHour());
            buf.append(":");
            buf.append(toDate.getStrMinute());

        } else {
            //終了が翌日の0:00の場合、本日の24:00と表示する
            UDate nextDate = date.cloneUDate();
            nextDate.addDay(1);
            if (toDate.getYear() == nextDate.getYear()
                && toDate.getMonth() == nextDate.getMonth()
                && toDate.getIntDay() == nextDate.getIntDay()
                && toDate.getIntHour() == GSConstReserve.DAY_START_HOUR
                && toDate.getIntMinute() == GSConstReserve.DAY_START_MINUTES) {
                if (!flg) {
                    buf.append("-");
                }
                buf.append("24");
                buf.append(":");
                buf.append("00");
            } else {
                if (!flg) {
                    buf.append("");
                }
            }
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] 施設予約[月間]をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv030ParamModel
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @return pdfModel SmlPdfModel
     * @throws IOException IO実行時例外
     */
    public RsvGekPdfModel createRsvGekPdf(
            Rsv030ParamModel paramMdl,
            Connection con,
            String appRootPath,
            String outTempDir)
        throws IOException {
        OutputStream oStream = null;
        GsMessage msg = new GsMessage(reqMdl_);

        BaseUserModel usModel = reqMdl_.getSmodel();

        //施設予約PDF出力用モデル
        RsvGekPdfModel pdfMdl = new RsvGekPdfModel();

        //年月
        pdfMdl.setRsvGekDspDate(paramMdl.getRsvDspFrom());
        //ヘッダー表示用年月日(曜)
        pdfMdl.setRsvGekHeadDate(paramMdl.getRsvDispYm());
        //表示グループ
        String dspGrp = null;
        for (LabelValueBean bean : paramMdl.getRsvGrpLabelList()) {
            if (bean.getValue().equals(String.valueOf(paramMdl.getRsvSelectedGrpSid()))) {
                dspGrp = bean.getLabel();
            }
        }
        pdfMdl.setRsvGekDspGroup(dspGrp);
        //表示施設
        String dspSis = null;
        for (LabelValueBean bean : paramMdl.getRsvSisetuLabelList()) {
            if (bean.getValue().equals(String.valueOf(paramMdl.getRsvSelectedSisetuSid()))) {
                dspSis = bean.getLabel();
            }
        }
        pdfMdl.setRsvGekDspSisetsu(dspSis);
        //施設毎の予約情報リスト
        pdfMdl.setRsvGekMonthList(paramMdl.getRsv030MonthList());

        //ファイル名
        String outBookName = paramMdl.getRsvDispYm()
                + msg.getMessage("reserve.rsvmain.4")
                + "(" + msg.getMessage("cmn.monthly") + ")";

        //エスケープ処理したファイル名
        String encOutBookName = __fileNameCheck(outBookName) + ".pdf";
        pdfMdl.setRsvGekFileName(encOutBookName);

        //セッションユーザSID
        int sessionUsrSid = usModel.getUsrsid();
        String saveFileName = "rsvGek_" + paramMdl.getRsvDspFrom() + "_" + sessionUsrSid + ".pdf";
        //保存先ファイル名
        pdfMdl.setSaveGekFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RsvGekPdfUtil pdfUtil = new RsvGekPdfUtil(reqMdl_);
            pdfUtil.createRsvGekkanPdf(pdfMdl, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("施設予約[月間]PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("施設予約[月間]PDF出力を終了します。");

        return pdfMdl;
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
}