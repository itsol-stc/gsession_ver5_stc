package jp.groupsession.v2.rsv.rsv010;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.RsvCalenderModel;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvWeekModelBeforConv;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvHidDayModel;
import jp.groupsession.v2.rsv.model.RsvHidGroupModel;
import jp.groupsession.v2.rsv.model.RsvHidModel;
import jp.groupsession.v2.rsv.model.RsvHidSisetuModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.pdf.RsvSyuPdfModel;
import jp.groupsession.v2.rsv.pdf.RsvSyuPdfUtil;
import jp.groupsession.v2.rsv.rsv190.Rsv190ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約一覧 週間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv010Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv010Biz.class);
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
    public Rsv010Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 管理者フラグを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setAdmFlg(Rsv010ParamModel paramMdl) throws SQLException {

        paramMdl.setRsvAdmFlg(_isAdmin(reqMdl_, con_));

    }

    /**
     * <br>[機  能] 施設グループ編集可否フラグをセットする
     * <br>[解  説]
     * <br>[備  考] 施設グループの編集が可能か判定しフラグをセットする。
     *              (※下記のいずれかの条件を満たすか)
     *              1:管理者グループに所属している。
     *              2:いずれかの施設グループの管理者として設定されている。
     *              3:「権限設定をしない」となっている施設グループ1つでも存在する。
     *
     * @param paramMdl Rsv010ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setGroupEditFlg(Rsv010ParamModel paramMdl) throws SQLException {

        paramMdl.setRsvGroupEditFlg(_isAllGroupEditAuthority(reqMdl_, con_));

    }

    /**
     * <br>[機  能] コンボリストを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外 
     * @throws IllegalAccessException 日時設定時例外
     */
    public void setComboList(Rsv010ParamModel paramMdl) throws SQLException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //グループコンボリスト
        paramMdl.setRsvGrpLabelList(_getGroupComboList2(con_, reqMdl_));

        //選択グループ
        if (paramMdl.getRsvSelectedGrpSid() == -2) {
            //施設予約個人設定を取得
            RsvUserModel mdl = _isRsvUser(reqMdl_, con_);
            if (mdl == null) {
                paramMdl.setRsvSelectedGrpSid(GSConstReserve.COMBO_PLEASE_CHOICE);
            } else {
                paramMdl.setRsvSelectedGrpSid(mdl.getRsgSid());
            }
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        
        if (paramMdl.getRsv010sisetuFreeFromDate() == null) {
            dateBiz.setDateParam(paramMdl, "rsv010sisetuFreeFromDate", "rsv010sisetuFreeFromY",
                    "rsv010sisetuFreeFromMo", "rsv010sisetuFreeFromD", null);
        }
        if (paramMdl.getRsv010sisetuFreeFromTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv010sisetuFreeFromTime",
                    "rsv010sisetuFreeFromH", "rsv010sisetuFreeFromMi", null);
        }
        if (paramMdl.getRsv010sisetuFreeToDate() == null) {
            dateBiz.setDateParam(paramMdl, "rsv010sisetuFreeToDate", "rsv010sisetuFreeToY",
                    "rsv010sisetuFreeToMo", "rsv010sisetuFreeToD", null);
        }
        if (paramMdl.getRsv010sisetuFreeToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv010sisetuFreeToTime",
                    "rsv010sisetuFreeToH", "rsv010sisetuFreeToMi", null);
        }

        GsMessage gsMsg = new GsMessage(reqMdl_);
        /* 絞り込み用グループのコンボ**/
        getGrpNarrowDownCmbList(paramMdl);

        /* 施設区分コンボ**/
        ArrayList<LabelValueBean> sisetuKbnLabelList = new ArrayList<LabelValueBean>();
        sisetuKbnLabelList.add(new LabelValueBean(gsMsg.getMessage("cmn.all"),
                Integer.toString(GSConstReserve.RSK_KBN_ALL)));
        sisetuKbnLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.112"),
                Integer.toString(GSConstReserve.RSK_KBN_HEYA)));
        sisetuKbnLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.113"),
                Integer.toString(GSConstReserve.RSK_KBN_BUPPIN)));
        sisetuKbnLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.114"),
                Integer.toString(GSConstReserve.RSK_KBN_CAR)));
        sisetuKbnLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.115"),
                Integer.toString(GSConstReserve.RSK_KBN_BOOK)));
        sisetuKbnLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.use.kbn.other"),
                Integer.toString(GSConstReserve.RSK_KBN_OTHER)));
        paramMdl.setRsv010SisetuKbnList(sisetuKbnLabelList);
    }

    /**
     * <br>[機  能]絞り込み用グループのコンボリスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param  paramMdl Rsv010ParamModel
     * @throws SQLException SQLException
     */
    public void getGrpNarrowDownCmbList(Rsv010ParamModel paramMdl)
            throws SQLException {

        BaseUserModel usModel = _getSessionUserModel(reqMdl_);
        //ユーザ区分 = 管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        RsvSisGrpDao dao = new RsvSisGrpDao(con_);
        ArrayList<RsvSisGrpModel> ret = null;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (adminUser) {
            //全グループ取得
            ret = dao.selectAllGroupData(paramMdl.getRsv010sisetuKbn());
        } else {
            //閲覧可能グループ取得
            ret = dao.getCanReadDataSisetuKbn(usModel.getUsrsid(), paramMdl.getRsv010sisetuKbn());
        }

        String lavelStr = "";
        GsMessage gsMsg = new GsMessage(reqMdl_);

        //選択してください
        lavelStr = gsMsg.getMessage("cmn.select.plz");
        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_PLEASE_CHOICE)));

        //全て
        lavelStr = gsMsg.getMessage("cmn.all");
        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisGrpModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsgName(),
                            String.valueOf(mdl.getRsgSid())));
        }
        paramMdl.setRsvGrpNarrowDownList(labelList);
    }

    /**
     * <br>[機  能]絞り込み用初期設定
     * <br>[解  説]
     * <br>[備  考]
     * @param  paramMdl Rsv010ParamModel
     * @throws SQLException SQLException
     */
    public void setNarrowDownData(Rsv010ParamModel paramMdl)
            throws SQLException {

        UDate date = new UDate();

        int usrSid = reqMdl_.getSmodel().getUsrsid();
        RsvCommonBiz rcBiz = new RsvCommonBiz();
        RsvAdmConfModel aMdl = rcBiz.getRsvAdminData(con_, usrSid);
        RsvUserModel uMdl = rcBiz.getRevUserModel(usrSid, con_);
        UDate frDate = rcBiz.getFrDateAuth(aMdl, uMdl);
        UDate toDate = rcBiz.getToDateAuth(aMdl, uMdl);
        paramMdl.setRsv010InitFlg(0);

        /** キーワード区分*/
        paramMdl.setRsv010KeyWordkbn(0);
        /** キーワード対象（資産管理番号)*/
        paramMdl.setRsv010sisetuKeywordSisan(1);
        /** キーワード対象（施設名)*/
        paramMdl.setRsv010sisetuKeywordSisetu(1);
        /** キーワード対象（備考)*/
        paramMdl.setRsv010sisetuKeywordBiko(1);
        /** キーワード対象（ナンバー)*/
        paramMdl.setRsv010sisetuKeywordNo(1);
        /** キーワード対象（ISBN)*/
        paramMdl.setRsv010sisetuKeywordIsbn(1);
        /** 空き状況*/
        paramMdl.setRsv010sisetuFree(1);
        /** 空き範囲開始年*/
        paramMdl.setRsv010sisetuFreeFromY(date.getYear());
        /** 空き範囲開始月*/
        paramMdl.setRsv010sisetuFreeFromMo(date.getMonth());
        /** 空き範囲開始日*/
        paramMdl.setRsv010sisetuFreeFromD(date.getIntDay());
        /** 空き範囲開始時*/
        paramMdl.setRsv010sisetuFreeFromH(frDate.getIntHour());
        /** 空き範囲開始分*/
        paramMdl.setRsv010sisetuFreeFromMi(frDate.getIntMinute());
        /** 空き範囲終了年*/
        paramMdl.setRsv010sisetuFreeToY(date.getYear());
        /** 空き範囲終了月*/
        paramMdl.setRsv010sisetuFreeToMo(date.getMonth());
        /** 空き範囲終了日*/
        paramMdl.setRsv010sisetuFreeToD(date.getIntDay());
        /** 空き範囲終了時*/
        paramMdl.setRsv010sisetuFreeToH(toDate.getIntHour());
        /** 空き範囲終了分*/
        paramMdl.setRsv010sisetuFreeToMi(toDate.getIntMinute());
        /** 施設区分*/
        paramMdl.setRsv010sisetuKbn(0);
        /** グループ*/
        paramMdl.setRsv010grpNarrowDown(0);
        /** 喫煙*/
        paramMdl.setRsv010sisetuSmoky(0);
        /** 社外持ち出し*/
        paramMdl.setRsv010sisetuTakeout(0);
    }


    /**
     * <br>[機  能] 施設グループコンボリストを設定する
     * <br>[解  説] 施設予約[週間]POPUP(Rsv190Form)
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setGroupComboList(Rsv190ParamModel paramMdl) throws SQLException {

        paramMdl.setRsvGrpLabelList(_getGroupComboList(true, con_, reqMdl_));

        if (paramMdl.getRsvSelectedGrpSid() == -1) {
            //施設予約個人設定を取得
            RsvUserModel mdl = _isRsvUser(reqMdl_, con_);
            if (mdl == null) {
                paramMdl.setRsvSelectedGrpSid(GSConstReserve.COMBO_DEFAULT_VALUE);
            } else {
                paramMdl.setRsvSelectedGrpSid(mdl.getRsgSid());
            }
        }
    }
    /**
     * <br>[機  能] 表示日付の移動を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param moveDay 移動日数
     * @param today 今日へ移動
     */
    public void setMoveDspData(Rsv010ParamModel paramMdl, int moveDay, boolean today) {
        RsvCommonBiz biz = new RsvCommonBiz();
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        paramMdl.setRsvDspFrom(udate.getDateString());
        paramMdl.setRsv100selectedFromYear(udate.getYear());
        paramMdl.setRsv100selectedFromMonth(udate.getMonth());
        paramMdl.setRsv100selectedFromDay(udate.getIntDay());
    }
    /**
     * <br>[機  能] 表示日付の移動を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param moveDay 移動日数
     * @param today 今日へ移動
     */
    public void setMoveDspData(Rsv190ParamModel paramMdl, int moveDay, boolean today) {
        RsvCommonBiz biz = new RsvCommonBiz();
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
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
     * @param paramMdl Rsv010ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void set010Reloadtime(Connection con, Rsv010ParamModel paramMdl, int sessionUsrSid)
    throws SQLException {

        paramMdl.setRsv010Reload(getReloadtime(con, sessionUsrSid));

    }
    /**
     * <br>[機  能] 自動リロード時間を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @return int リロード時間
     * @throws SQLException SQL実行時例外
     */
    public int getReloadtime(Connection con, int sessionUsrSid)
    throws SQLException {

        int reloadTime = GSConstReserve.AUTO_RELOAD_10MIN;
        RsvUserDao dao = new RsvUserDao(con);
        RsvUserModel model = dao.select(sessionUsrSid);
        if (model != null) {
            reloadTime = model.getRsuReload();
        }
        return reloadTime;
    }
    /**
     * <br>[機  能] 施設予約情報一覧[週間]をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv010ParamModel
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setYoyakuWeek(Rsv010ParamModel paramMdl, int sessionUsrSid
            ) throws SQLException {

        log__.debug("週間カレンダーと予約情報設定");
        //表示開始日
        RsvCommonBiz biz = new RsvCommonBiz();
        String strDspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());
        paramMdl.setRsvDspFrom(strDspDate);

        //一覧のヘッダに表示する年月を設定
        GsMessage gsMsg = new GsMessage(reqMdl_);
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);
        paramMdl.setRsvDispYm(
                gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()})
                + dspDate.getStrMonth()
                + gsMsg.getMessage("cmn.month"));

        //カレンダー設定
        paramMdl.setRsv010CalendarList(__getWeekCalender(dspDate.cloneUDate()));

        //施設毎の予約情報取得
        if (paramMdl.getRsv010SiborikomiFlg() == 0) {
            paramMdl.setRsv010SisetuList(__getWeekList(paramMdl,
                    dspDate.cloneUDate(), sessionUsrSid));
        } else {
            paramMdl.setRsv010SisetuList(__getWeeksiborikomiList(paramMdl,
                    dspDate.cloneUDate(), sessionUsrSid));
        }
    }

    /**
     * <br>[機  能] 施設予約情報一覧[週間]をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setYoyakuWeek(Rsv190ParamModel paramMdl, int sessionUsrSid) throws SQLException {

        log__.debug("週間カレンダーと予約情報設定");

        //表示開始日
        RsvCommonBiz biz = new RsvCommonBiz();
        String strDspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());
        paramMdl.setRsvDspFrom(strDspDate);

        paramMdl.setRsvDspFrom(strDspDate);

        //一覧のヘッダに表示する年月を設定
        GsMessage gsMsg = new GsMessage(reqMdl_);
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);
        paramMdl.setRsvDispYm(
                gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()})
                + dspDate.getStrMonth()
                + gsMsg.getMessage("cmn.month"));

        //カレンダー設定
        paramMdl.setRsv010CalendarList(__getWeekCalender(dspDate.cloneUDate()));

        //施設毎の予約情報取得
        paramMdl.setRsv010SisetuList(__getWeekList(paramMdl, dspDate.cloneUDate(), sessionUsrSid));
    }
    /**
     * <br>[機  能] 指定日付からプラス6日分のカレンダーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dspDate 指定日付
     * @return ArrayList 1週間分のカレンダー
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvCalenderModel> __getWeekCalender(UDate dspDate)
        throws SQLException {

        if (dspDate == null) {
            return null;
        }

        ArrayList<RsvCalenderModel> calList =
            new ArrayList<RsvCalenderModel>(GSConstReserve.WEEK_DAY_COUNT);

        //開始日設定
        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstReserve.DAY_START_HOUR);
        frDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        frDate.setSecond(GSConstReserve.DAY_START_SECOND);

        //終了日設定
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstReserve.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstReserve.DAY_END_HOUR);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);

        //カレンダー取得
        CmnHolidayDao holDao = new CmnHolidayDao(con_);
        HashMap<String, CmnHolidayModel> holMap =
            holDao.getHoliDayList(frDate, toDate);

        CmnHolidayModel holMdl = null;
        UDate now = new UDate();
        int todayYear = now.getYear();
        int todayMonth = now.getMonth();
        int todayDay = now.getIntDay();

        //1週間分のカレンダーを設定
        RsvCalenderModel calMdl = null;
        GsMessage gsMsg = new GsMessage(reqMdl_);
        CommonBiz cmnBiz = new CommonBiz();
        for (int i = 0; i < GSConstReserve.WEEK_DAY_COUNT; i++) {
            calMdl = new RsvCalenderModel();
            calMdl.setDspDate(dspDate.getDateString());

            String todayKbn = "1";
            if (todayYear == dspDate.getYear()
                    && todayMonth == dspDate.getMonth()
                    && todayDay == dspDate.getIntDay()) {
                todayKbn = "0";
            }
            calMdl.setTodayKbn(todayKbn);

            calMdl.setWeekKbn(String.valueOf(dspDate.getWeek()));
            calMdl.setDspDayString(
                    String.valueOf(dspDate.getIntDay())
                    + gsMsg.getMessage("cmn.day")
                    + "("
                    + UDateUtil.getStrWeek(dspDate.getWeek(), reqMdl_)
                    + ")");
            //休日情報を設定
            holMdl = holMap.get(dspDate.getDateString());
            if (holMdl != null) {
                calMdl.setHolidayKbn(String.valueOf(GSConstReserve.HOLIDAY_TRUE));
            } else {
                calMdl.setHolidayKbn(String.valueOf(GSConstReserve.HOLIDAY_FALSE));
            }
            //六曜
            //セッション情報を取得
            HttpSession session = reqMdl_.getSession();
            BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
            int sessionUsrSid = usModel.getUsrsid();
            if (cmnBiz.getRokuyoDspKbn(con_, sessionUsrSid)) {
                String rokuyou = cmnBiz.getRokuyou(dspDate);
                int rkyKbn = cmnBiz.setRkyKbn(rokuyou);
                calMdl.setRokuyou(String.valueOf(rkyKbn));
            } else {
                calMdl.setRokuyou(String.valueOf(""));
            }
            calList.add(calMdl);
            dspDate.addDay(1);
        }
        return calList;
    }

    /**
     * <br>[機  能] 1週間の予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param dspDate 指定日付
     * @param sessionUsrSid セッションユーザSID
     * @return ArrayList 1週間分のカレンダー
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getWeekList(
            Rsv010ParamModel paramMdl, UDate dspDate, int sessionUsrSid)
        throws SQLException {

        //検索条件 開始日付
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstReserve.DAY_START_HOUR);
        fromDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        fromDate.setSecond(GSConstReserve.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了日付
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstReserve.DAY_END_HOUR);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con_);
        HashMap<String, CmnHolidayModel> holMap =
            holDao.getHoliDayList(fromDate, toDate);

        //1週間の予約情報取得
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con_);
        ArrayList<RsvSisetuModel> ret = new ArrayList<RsvSisetuModel>();

        //選択してくださいを選択時は施設を表示しない
        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_PLEASE_CHOICE) {
            return ret;
        }
        paramMdl.setRsv010SiborikomiFlg(0);
        ArrayList<RsvWeekModelBeforConv> weekList = null;

        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            //全ての場合
            CommonBiz cmnBiz = new CommonBiz();

            BaseUserModel usModel = reqMdl_.getSmodel();

            //システム管理者権限を取得
            boolean admFlg = cmnBiz.isPluginAdmin(con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);
            List<RsvSisGrpModel> rsgMdlList = null;
            if (admFlg) {
                //全グループ一覧を取得する。
                rsgMdlList = rsgDao.selectAllGroupData();
            } else {
                //アクセス可能グループ一覧を取得する。
                rsgMdlList = rsgDao.getCanReadData(sessionUsrSid);
            }

            List<Integer> canAccessGrp = new ArrayList<Integer>();
            if (rsgMdlList != null && rsgMdlList.size() > 0) {
                for (RsvSisGrpModel model : rsgMdlList) {
                    canAccessGrp.add(model.getRsgSid());
                }
            }

            //アクセス可能なグループが存在する場合のみ検索を行う
            if (!canAccessGrp.isEmpty()) {
                weekList = dao.getYrkList(canAccessGrp, -1, fromDate, toDate);
            } else {
                weekList = new ArrayList<RsvWeekModelBeforConv>();
            }

        } else {
            //グループ指定の場合

            //施設グループ閲覧権限
            if (!_isReadRsvGrp(con_, reqMdl_, paramMdl.getRsvSelectedGrpSid())) {
                paramMdl.setRsvSelectedGrpSid(GSConstReserve.COMBO_PLEASE_CHOICE);
                return ret;
            }

            weekList = dao.getYrkList(paramMdl.getRsvSelectedGrpSid(), -1, fromDate, toDate);
        }


        //検索結果を画面表示用に変換
        ret = __getConvWeekList(paramMdl, weekList, holMap, dspDate);

        return ret;
    }

    /**
     * <br>[機  能] 1週間の予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param dspDate 指定日付
     * @param sessionUsrSid セッションユーザSID
     * @return ArrayList 1週間分のカレンダー
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getWeeksiborikomiList(
            Rsv010ParamModel paramMdl, UDate dspDate, int sessionUsrSid)
        throws SQLException {

        //検索条件 開始日付
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstReserve.DAY_START_HOUR);
        fromDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        fromDate.setSecond(GSConstReserve.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了日付
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstReserve.DAY_END_HOUR);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con_);
        HashMap<String, CmnHolidayModel> holMap =
            holDao.getHoliDayList(fromDate, toDate);

        //1週間の予約情報取得
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con_);
        ArrayList<RsvSisetuModel> ret = new ArrayList<RsvSisetuModel>();

        //選択してくださいを選択時は施設を表示しない
        if (paramMdl.getRsv010grpNarrowDown() == GSConstReserve.COMBO_PLEASE_CHOICE) {
            return ret;
        }
        paramMdl.setRsv010SiborikomiFlg(1);
        ArrayList<RsvWeekModelBeforConv> weekList = null;

        //検索モデル作成
        Rsv010SearchModel sMdl = setSearchModel(paramMdl);

        if (paramMdl.getRsv010grpNarrowDown() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            //全ての場合
            CommonBiz cmnBiz = new CommonBiz();

            BaseUserModel usModel = reqMdl_.getSmodel();

            //システム管理者権限を取得
            boolean admFlg = cmnBiz.isPluginAdmin(con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);
            List<RsvSisGrpModel> rsgMdlList = null;
            if (admFlg) {
                //全グループ一覧を取得する。
                rsgMdlList = rsgDao.selectAllGroupData();
            } else {
                //アクセス可能グループ一覧を取得する。
                rsgMdlList = rsgDao.getCanReadData(sessionUsrSid);
            }

            List<Integer> canAccessGrp = new ArrayList<Integer>();
            if (rsgMdlList != null && rsgMdlList.size() > 0) {
                for (RsvSisGrpModel model : rsgMdlList) {
                    canAccessGrp.add(model.getRsgSid());
                }
            }

            //アクセス可能なグループが存在する場合のみ検索を行う
            if (!canAccessGrp.isEmpty()) {
                weekList = dao.getYrkList(canAccessGrp, sMdl, fromDate, toDate);
            } else {
                weekList = new ArrayList<RsvWeekModelBeforConv>();
            }

        } else {
            //グループ指定の場合

            //施設グループ閲覧権限
            if (!_isReadRsvGrp(con_, reqMdl_, paramMdl.getRsv010grpNarrowDown())) {
                paramMdl.setRsv010grpNarrowDown(GSConstReserve.COMBO_PLEASE_CHOICE);
                return ret;
            }


            weekList = dao.getYrkList(sMdl, fromDate, toDate);
        }


        //検索結果を画面表示用に変換
        ret = __getConvWeekList(paramMdl, weekList, holMap, dspDate);

        return ret;
    }

    /**
     * <br>[機  能] 検索モデル作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv010ParamModel
     * @return Rsv010SearchModel
     */
    private Rsv010SearchModel setSearchModel(Rsv010ParamModel paramMdl) {

        Rsv010SearchModel sMdl = new Rsv010SearchModel();
        /** キーワード*/
        sMdl.setSisetuKeyword(paramMdl.getRsv010svSisetuKeyword());
        /** キーワード区分*/
        sMdl.setKeyWordkbn(paramMdl.getRsv010svKeyWordkbn());
        /** キーワード対象（資産管理番号)*/
        sMdl.setSisetuKeywordSisan(paramMdl.getRsv010svSisetuKeywordSisan());
        /** キーワード対象（施設名)*/
        sMdl.setSisetuKeywordSisetu(paramMdl.getRsv010svSisetuKeywordSisetu());
        /** キーワード対象（備考)*/
        sMdl.setSisetuKeywordBiko(paramMdl.getRsv010svSisetuKeywordBiko());
        /** キーワード対象（ナンバー)*/
        sMdl.setSisetuKeywordNo(paramMdl.getRsv010svSisetuKeywordNo());
        /** キーワード対象（ISBN)*/
        sMdl.setSisetuKeywordIsbn(paramMdl.getRsv010svSisetuKeywordIsbn());
        /** 空き状況*/
        sMdl.setSisetuFree(paramMdl.getRsv010svSisetuFree());
        /** 空き範囲開始年*/
        sMdl.setSisetuFreeFromY(paramMdl.getRsv010svSisetuFreeFromY());
        /** 空き範囲開始月*/
        sMdl.setSisetuFreeFromMo(paramMdl.getRsv010svSisetuFreeFromMo());
        /** 空き範囲開始日*/
        sMdl.setSisetuFreeFromD(paramMdl.getRsv010svSisetuFreeFromD());
        /** 空き範囲開始時*/
        sMdl.setSisetuFreeFromH(paramMdl.getRsv010svSisetuFreeFromH());
        /** 空き範囲開始分*/
        sMdl.setSisetuFreeFromMi(paramMdl.getRsv010svSisetuFreeFromMi());
        /** 空き範囲終了年*/
        sMdl.setSisetuFreeToY(paramMdl.getRsv010svSisetuFreeToY());
        /** 空き範囲終了月*/
        sMdl.setSisetuFreeToMo(paramMdl.getRsv010svSisetuFreeToMo());
        /** 空き範囲終了日*/
        sMdl.setSisetuFreeToD(paramMdl.getRsv010svSisetuFreeToD());
        /** 空き範囲終了時*/
        sMdl.setSisetuFreeToH(paramMdl.getRsv010svSisetuFreeToH());
        /** 空き範囲終了分*/
        sMdl.setSisetuFreeToMi(paramMdl.getRsv010svSisetuFreeToMi());
        /** 施設区分*/
        sMdl.setSisetuKbn(paramMdl.getRsv010svSisetuKbn());
        /** グループ*/
        sMdl.setGrpNarrowDown(paramMdl.getRsv010svGrpNarrowDown());
        /** 喫煙*/
        sMdl.setSisetuSmoky(paramMdl.getRsv010svSisetuSmoky());
        /** 座席数*/
        sMdl.setSisetuChere(paramMdl.getRsv010svSisetuChere());
        /** 社外持ち出し*/
        sMdl.setSisetuTakeout(paramMdl.getRsv010svSisetuTakeout());
        return sMdl;
    }


    /**
     * <br>[機  能] 1週間の予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param dspDate 指定日付
     * @param sessionUsrSid セッションユーザSID
     * @return ArrayList 1週間分のカレンダー
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getWeekList(
            Rsv190ParamModel paramMdl, UDate dspDate, int sessionUsrSid)
        throws SQLException {

        //検索条件 開始日付
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstReserve.DAY_START_HOUR);
        fromDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        fromDate.setSecond(GSConstReserve.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了日付
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstReserve.DAY_END_HOUR);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con_);
        HashMap<String, CmnHolidayModel> holMap =
            holDao.getHoliDayList(fromDate, toDate);

        ArrayList<RsvSisetuModel> ret = new ArrayList<RsvSisetuModel>();
        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con_);

        //施設グループ閲覧権限
        if (_isReadRsvGrp(con_, reqMdl_, paramMdl.getRsvSelectedGrpSid())) {
            return ret;
        }

        //1週間の予約情報取得
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);

        ArrayList<RsvWeekModelBeforConv> weekList = null;

        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            //全ての場合

            //アクセス可能グループ一覧を取得する。
            List<RsvSisGrpModel> rsgMdlList = rsgDao.getCanReadData(sessionUsrSid);

            List<Integer> canAccessGrp = new ArrayList<Integer>();
            if (rsgMdlList != null && rsgMdlList.size() > 0) {
                for (RsvSisGrpModel model : rsgMdlList) {
                    canAccessGrp.add(model.getRsgSid());
                }
            }

            weekList = dao.getYrkList(canAccessGrp, -1, fromDate, toDate);

        } else {
            //グループ指定の場合
            weekList = dao.getYrkList(paramMdl.getRsvSelectedGrpSid(), -1, fromDate, toDate);
        }


        //検索結果を画面表示用に変換
        ret = __getConvWeekList(weekList, holMap, dspDate);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を画面表示用に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param retList 検索結果リスト
     * @param holMap 休日情報
     * @param dspDate 表示日付(開始)
     * @return sisetuList 変換結果
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getConvWeekList(
            ArrayList<RsvWeekModelBeforConv> retList,
            HashMap<String, CmnHolidayModel> holMap,
            UDate dspDate) throws SQLException {

        return __getConvWeekList(null, retList, holMap, dspDate);
    }

    /**
     * <br>[機  能] 予約情報を画面表示用に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param retList 検索結果リスト
     * @param holMap 休日情報
     * @param dspDate 表示日付(開始)
     * @return sisetuList 変換結果
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getConvWeekList(
            Rsv010ParamModel paramMdl,
            ArrayList<RsvWeekModelBeforConv> retList,
            HashMap<String, CmnHolidayModel> holMap,
            UDate dspDate) throws SQLException {

        if (retList.isEmpty()) {
            return null;
        }

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

        int saveRsdSid = -1;
        boolean firstRecord = true;
        ArrayList<RsvSisetuModel> sisetuList =
            new ArrayList<RsvSisetuModel>();
        ArrayList<String> dspArray = new ArrayList<String>();

        //セッションユーザSID
        BaseUserModel usModel = reqMdl_.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        //システム管理者権限を取得
        CommonBiz cmnBiz = new CommonBiz();
        boolean admFlg = cmnBiz.isPluginAdmin(con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        HashMap <Integer, Integer> map = new HashMap<Integer, Integer>();
        int authKbn = 0;

        //施設情報設定
        for (RsvWeekModelBeforConv sisetu : retList) {

            UDate date = dspDate.cloneUDate();
            int rsdSid = sisetu.getRsdSid();
            RsvSisetuModel sisetuMdl = new RsvSisetuModel();;

            if (rsdSid != saveRsdSid) {

                if (map.containsKey(sisetu.getRsgSid())) {
                    authKbn = map.get(sisetu.getRsgSid());
                } else {
                    boolean editOkFlg = _isEditRsvGrp(
                            con_, sisetu.getRsgSid(), sessionUsrSid, admFlg);
                    if (editOkFlg) {
                        map.put(sisetu.getRsgSid(), GSConstReserve.RSV_ACCESS_KBN_WRITE);
                        authKbn = GSConstReserve.RSV_ACCESS_KBN_WRITE;
                    } else {
                        map.put(sisetu.getRsgSid(), GSConstReserve.RSV_ACCESS_KBN_READ);
                        authKbn = GSConstReserve.RSV_ACCESS_KBN_READ;
                    }

                }

                //施設情報セット
                sisetuMdl.setRsgSid(sisetu.getRsgSid());
                sisetuMdl.setRsdSid(sisetu.getRsdSid());
                sisetuMdl.setRsdName(sisetu.getRsdName());
                sisetuMdl.setRsdInfoSisetuKbnSid(sisetu.getRsdInfoSisetuKbnSid());

                //登録ボタン表示区分
                sisetuMdl.setRacAuth(authKbn);

                sisetuMdl.setRsdSisetuId(sisetu.getRsdInfoSisetuId());
                sisetuMdl.setRsdSisanKanri(sisetu.getRsdInfoSisanKanri());
                sisetuMdl.setRsdProp1Value(sisetu.getRsdInfoProp1Value());
                sisetuMdl.setRsdProp2Value(sisetu.getRsdInfoProp2Value());
                sisetuMdl.setRsdProp3Value(sisetu.getRsdInfoProp3Value());
                sisetuMdl.setRsdProp4Value(sisetu.getRsdInfoProp4Value());
                sisetuMdl.setRsdProp5Value(sisetu.getRsdInfoProp5Value());
                sisetuMdl.setRsdProp6Value(sisetu.getRsdInfoProp6Value());
                sisetuMdl.setRsdProp7Value(sisetu.getRsdInfoProp7Value());
                sisetuMdl.setRsdPlaceImgCom1(sisetu.getRsdInfoPlaceImgCom1());
                sisetuMdl.setRsdPlaceImgCom2(sisetu.getRsdInfoPlaceImgCom2());
                sisetuMdl.setRsdPlaceImgCom3(sisetu.getRsdInfoPlaceImgCom3());
                sisetuMdl.setRsdPlaceImgCom4(sisetu.getRsdInfoPlaceImgCom4());
                sisetuMdl.setRsdPlaceImgCom5(sisetu.getRsdInfoPlaceImgCom5());
                sisetuMdl.setRsdPlaceImgCom6(sisetu.getRsdInfoPlaceImgCom6());
                sisetuMdl.setRsdPlaceImgCom7(sisetu.getRsdInfoPlaceImgCom7());
                sisetuMdl.setRsdPlaceImgCom8(sisetu.getRsdInfoPlaceImgCom8());
                sisetuMdl.setRsdPlaceImgCom9(sisetu.getRsdInfoPlaceImgCom9());
                sisetuMdl.setRsdPlaceImgCom10(sisetu.getRsdInfoPlaceImgCom10());
                sisetuMdl.setRsdBiko(
                        StringUtilHtml.transToHTmlPlusAmparsant(sisetu.getRsdInfoBiko()));
                sisetuMdl.setRsdInfoPlaCom(sisetu.getRsdInfoPlaCom());

                //施設情報表示区分セット
                sisetuMdl.setRsdInfoSisetuIdDspKbn(sisetu.getRsdInfoSisetuIdDspKbn());
                sisetuMdl.setRsdInfoSisanKanriDspKbn(sisetu.getRsdInfoSisanKanriDspKbn());
                sisetuMdl.setRsdInfoProp1ValueDspKbn(sisetu.getRsdInfoProp1ValueDspKbn());
                sisetuMdl.setRsdInfoProp2ValueDspKbn(sisetu.getRsdInfoProp2ValueDspKbn());
                sisetuMdl.setRsdInfoProp3ValueDspKbn(sisetu.getRsdInfoProp3ValueDspKbn());
                sisetuMdl.setRsdInfoProp4ValueDspKbn(sisetu.getRsdInfoProp4ValueDspKbn());
                sisetuMdl.setRsdInfoProp5ValueDspKbn(sisetu.getRsdInfoProp5ValueDspKbn());
                sisetuMdl.setRsdInfoProp6ValueDspKbn(sisetu.getRsdInfoProp6ValueDspKbn());
                sisetuMdl.setRsdInfoProp7ValueDspKbn(sisetu.getRsdInfoProp7ValueDspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom1DspKbn(sisetu.getRsdInfoPlaceImgCom1DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom2DspKbn(sisetu.getRsdInfoPlaceImgCom2DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom3DspKbn(sisetu.getRsdInfoPlaceImgCom3DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom4DspKbn(sisetu.getRsdInfoPlaceImgCom4DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom5DspKbn(sisetu.getRsdInfoPlaceImgCom5DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom6DspKbn(sisetu.getRsdInfoPlaceImgCom6DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom7DspKbn(sisetu.getRsdInfoPlaceImgCom7DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom8DspKbn(sisetu.getRsdInfoPlaceImgCom8DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom9DspKbn(sisetu.getRsdInfoPlaceImgCom9DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom10DspKbn(sisetu.getRsdInfoPlaceImgCom10DspKbn());
                sisetuMdl.setRsdInfoBikoDspKbn(sisetu.getRsdInfoBikoDspKbn());
                sisetuMdl.setRsdInfoPlaComDspKbn(sisetu.getRsdInfoPlaComDspKbn());
                sisetuMdl.setRsdInfoSisetuImgDspKbn(sisetu.getRsdInfoSisetuImgDspKbn());

                //施設予約の承認
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                sisetuMdl.setRsdApprKbnFlg(rsvCmnBiz.isApprSis(con_, rsdSid, -1));
                sisetuMdl.setRsdApprDspFlg(
                        sisetu.getRsdApprDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON);

                //施設情報ヘッダセット
                __setSisetuHeader(paramMdl, sisetu.getRsdInfoSisetuKbnSid(), sisetuMdl);

                saveRsdSid = rsdSid;

                ArrayList<RsvYoyakuDayModel> yoyakuDayList =
                    new ArrayList<RsvYoyakuDayModel>();

                //該当施設の予約情報を処理
                for (int i = 0; i < GSConstReserve.WEEK_DAY_COUNT; i++) {

                    RsvYoyakuDayModel yoyakuDayMdl = new RsvYoyakuDayModel();
                    yoyakuDayMdl.setYrkDateStr(date.getDateString());
                    yoyakuDayMdl.setYrkYobi(date.getWeek());
                    String ikkatuKey = date.getDateString() + "-" + String.valueOf(saveRsdSid);
                    //一括登録用キー(yyyyMMdd-施設SID)
                    yoyakuDayMdl.setIkkatuKey(ikkatuKey);
                    dspArray.add(ikkatuKey);

                    //休日情報セット
                    CmnHolidayModel holMdl = holMap.get(date.getDateString());
                    if (holMdl != null && firstRecord) {
                        yoyakuDayMdl.setHolName(holMdl.getHolName());
                    }

                    ArrayList<RsvYoyakuModel> yoyakuList =
                        new ArrayList<RsvYoyakuModel>();

                    for (RsvWeekModelBeforConv yrk : retList) {

                        //予約開始日付無し = 外部結合にて施設の情報だけ取得したもの
                        if (saveRsdSid != yrk.getRsdSid()
                            || yrk.getRsyFrDate() == null) {
                            continue;
                        }

                        //本日の予約か
                        if (__isTodayYoyaku(yrk, date)) {
                            RsvYoyakuModel yrkDayMdl = new RsvYoyakuModel();
                            yrkDayMdl.setRsySid(yrk.getRsySid());
                            yrkDayMdl.setYrkRiyoDateStr(__getTimeString(yrk, date));
                            if (kjnSetteiKbn2 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                                yrkDayMdl.setYrkName(yrk.getUsiSei() + "  " + yrk.getUsiMei());
                            }

                            //「予定あり」メッセージ
                            GsMessage gsMsg = new GsMessage(reqMdl_);
                            String textPlans = gsMsg.getMessage("reserve.177");

                            RsvCommonBiz rsvBiz = new RsvCommonBiz();
                            int publicFlg = rsvBiz.isCheckPubRange(
                                    con_, yrk.getRsySid(), sessionUsrSid);

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
                    }
                    yoyakuDayMdl.setYoyakuList(yoyakuList);
                    yoyakuDayList.add(yoyakuDayMdl);
                    date.addDay(1);
                }

                RsvWeekModel weekMdl = new RsvWeekModel();
                weekMdl.setYoyakuDayList(yoyakuDayList);
                ArrayList<RsvWeekModel> weekList = new ArrayList<RsvWeekModel>();
                weekList.add(weekMdl);

                sisetuMdl.setRsvWeekList(weekList);
                sisetuList.add(sisetuMdl);
                firstRecord = false;
            }
        }

        if (paramMdl != null && paramMdl.isRsvIkkatuTorokuFlg()) {

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

        return sisetuList;
    }

    /**
     * <br>[機  能] 施設区分に応じて施設のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param rskSid 施設区分SID
     * @param mdl モデル
     */
    private void __setSisetuHeader(Rsv010ParamModel paramMdl, int rskSid, RsvSisetuModel mdl) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv010PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv010PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv010PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv010PropHeaderName7(gsMsg.getMessage("reserve.136"));
                mdl.setRsvPropHeaderName8(gsMsg.getMessage("reserve.128"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv010PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv010PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv010PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv010PropHeaderName7(gsMsg.getMessage("reserve.136"));
                mdl.setRsvPropHeaderName8(gsMsg.getMessage("reserve.130"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv010PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv010PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv010PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv010PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv010PropHeaderName7(gsMsg.getMessage("reserve.136"));
                mdl.setRsvPropHeaderName8(gsMsg.getMessage("reserve.129"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv010PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv010PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv010PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv010PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv010PropHeaderName7(gsMsg.getMessage("reserve.136"));
                mdl.setRsvPropHeaderName8(gsMsg.getMessage("reserve.131"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv010PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv010PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
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
        GsMessage gsMsg = new GsMessage(reqMdl_);
        UDate udDay = new UDate();
        udDay.setDate(saveDay);
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
     * <br>[機  能] 施設画像データを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv010ParamModel
     * @param tempDir テンポラリディレクトリ
     * @param appRoot アプリケーションルートパス
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void get010SisetuImgData(
            Rsv010ParamModel paramMdl, String tempDir, String appRoot, int usrSid)
    throws SQLException, TempFileException, IOException, IOToolsException   {

        //施設予約個人設定で画像表示を許可しているか確認
        RsvUserDao ruDao = new RsvUserDao(con_);
        RsvUserModel ruMdl = ruDao.select(usrSid);

        if (ruMdl == null) {
            __setRsvBinData(paramMdl, tempDir, appRoot, usrSid);
        } else {
            if (ruMdl.getRsuImgDsp() == GSConstReserve.SISETU_IMG_ON) {
                __setRsvBinData(paramMdl, tempDir, appRoot, usrSid);
            }
        }
    }

    /**
     * <br>[機  能] 施設画像データを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv010ParamModel
     * @param tempDir テンポラリディレクトリ
     * @param appRoot アプリケーションルートパス
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    private void __setRsvBinData(
            Rsv010ParamModel paramMdl, String tempDir, String appRoot, int usrSid)
    throws SQLException, TempFileException, IOException, IOToolsException   {

        //施設データ取得
        RsvBinDao rbDao = new RsvBinDao(con_);
        CmnBinfModel dataRet = null;
        ArrayList<RsvSisetuModel> dspMdlList = new ArrayList<RsvSisetuModel>();

        for (RsvSisetuModel bean : paramMdl.getRsv010SisetuList()) {
            dataRet = rbDao.getWriteTmpFile(bean.getRsdSid());
            if (dataRet == null) {
                //施設画像が存在しない場合
                bean.setSisetuImgBinSid(Long.valueOf(0));
            } else {
                //週間・日間に表示する施設画像データをセット
                bean.setSisetuImgBinSid(dataRet.getBinSid());
            }
            dspMdlList.add(bean);
        }

        //表示用施設予約データ
        paramMdl.setRsv010SisetuList(dspMdlList);
    }

    /**
     * <br>[機  能] 週間施設予約をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv010ParamModel
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @return pdfModel SmlPdfModel
     * @throws IOException IO実行時例外
     */
    public RsvSyuPdfModel createRsvSyuPdf(
            Rsv010ParamModel paramMdl,
            Connection con,
            String appRootPath,
            String outTempDir)
        throws IOException {
        OutputStream oStream = null;
        GsMessage msg = new GsMessage(reqMdl_);

        //ヘッダー年月
        String headDate = paramMdl.getRsvDispYm();
        //表示グループ
        String dispGroup = new String();
        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            dispGroup = msg.getMessage("cmn.all");
        } else {
            for (LabelValueBean bean : paramMdl.getRsvGrpLabelList()) {
                if (paramMdl.getRsv010SisetuList() != null) {
                    for (RsvSisetuModel mdl : paramMdl.getRsv010SisetuList()) {
                        if (Integer.valueOf(bean.getValue()) == mdl.getRsgSid()) {
                            dispGroup = bean.getLabel();
                            break;
                        }
                    }
                } else {
                    dispGroup = bean.getLabel();
                }
            }
        }
        CommonBiz cmnBiz = new CommonBiz();
        for (int i = 0; i < paramMdl.getRsv010CalendarList().size(); i++) {
            paramMdl.getRsv010CalendarList().get(i).setRokuyou(
                    cmnBiz.setRkyName(paramMdl.getRsv010CalendarList().get(i).getRokuyou()));
        }

        ArrayList<RsvCalenderModel> calList = paramMdl.getRsv010CalendarList();
        ArrayList<RsvSisetuModel> sisetuList = paramMdl.getRsv010SisetuList();

        //施設予約PDF出力用モデル
        RsvSyuPdfModel pdfModel = new RsvSyuPdfModel();
        //ヘッダー年月
        pdfModel.setHeadDate(headDate);
        //表示グループ
        pdfModel.setDispGroup(dispGroup);
        //週間カレンダー
        pdfModel.setCalendarList(calList);
        pdfModel.setSisetuList(sisetuList);
        //施設情報
        pdfModel.setPropHeaderName2(paramMdl.getRsv010PropHeaderName2());
        pdfModel.setPropHeaderName3(paramMdl.getRsv010PropHeaderName3());
        pdfModel.setPropHeaderName4(paramMdl.getRsv010PropHeaderName4());
        pdfModel.setPropHeaderName5(paramMdl.getRsv010PropHeaderName5());
        pdfModel.setPropHeaderName6(paramMdl.getRsv010PropHeaderName6());
        pdfModel.setPropHeaderName7(paramMdl.getRsv010PropHeaderName7());


        String outBookName = pdfModel.getHeadDate()
                + msg.getMessage("reserve.rsvmain.4")
                + "(" + msg.getMessage("cmn.weeks") + ")";
        String encOutBookName = fileNameCheck(outBookName) + ".pdf";
        pdfModel.setFileName(encOutBookName);

        //セッションユーザSID
        //テンポラリディレクトリに保存時のファイル名
        String saveFileName = "rsvSyu_" + paramMdl.getRsvDspFrom() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RsvSyuPdfUtil pdfUtil = new RsvSyuPdfUtil(reqMdl_);
            pdfUtil.createRsvSyukanPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("施設予約PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("施設予約PDF出力を終了します。");

        return pdfModel;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String fileNameCheck(String fileName) {
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
     * <br>[機  能] 施設が閲覧可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @throws SQLException SQL実行時例外
     * @return true:閲覧可能  false:閲覧不可
     */
    public boolean isReadSisetsu(int rsdSid) throws SQLException {
        //施設SIDより施設グループSIDを取得する
        RsvSisDataModel searchMdl = new RsvSisDataModel();
        searchMdl.setRsdSid(rsdSid);
        RsvSisDataDao dao = new RsvSisDataDao(con_);
        RsvSisDataModel mdl = dao.select(searchMdl);
        if (mdl != null) {
            int rsgSid = mdl.getRsgSid();
            if (_isReadRsvGrp(con_, reqMdl_, rsgSid)) {
                return true;
            }
        }
       return false;
    }
}