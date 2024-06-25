package jp.groupsession.v2.tcd.tcd020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardCalcBiz;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.tcd010.Tcd010Form;


/**
 * <br>[機  能] タイムカード編集画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd020Form extends Tcd010Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd020Form.class);

    /** ユーザー種別 0:一般 1:グループ管理者 2:管理者 */
    private String usrKbn__;

    /** 名前 */
    private String tcd020Name__;
    /** 日付 */
    private String tcd020Date__;
    /** 打刻始業時間　時 */
    private String tcd020StrikeInHour__;
    /** 打刻始業時間　分 */
    private String tcd020StrikeInMinute__;
    /** 終業時間　時 */
    private String tcd020StrikeOutHour__;
    /** 終業時間　分 */
    private String tcd020StrikeOutMinute__;
    /** 始業時間　時 */
    private String tcd020InHour__;
    /** 始業時間　分 */
    private String tcd020InMinute__;
    /** 終業時間　時 */
    private String tcd020OutHour__;
    /** 終業時間　分 */
    private String tcd020OutMinute__;
    /** 備考 */
    private String tcd020Biko__;
    /** 休日区分 */
    private int tcd020HolKbn__;
    /** 休日区分名 */
    private String tcd020HolKbnName__;

    /** その他休日内容 */
    private String tcd020HolValue__;
    /** 休日日数 */
    private String tcd020HolDays__;
    /** 遅刻区分 */
    private String tcd020ChkKbn__ = null;
    /** 早退区分 */
    private String tcd020SouKbn__ = null;
    /** 時間帯 */
    private String tcd020Timezone__ = null;

    /** 使用停止休日区分名 */
    private String tcd020NotUseHolKbnName__;
    /** 使用停止時間帯名 */
    private String tcd020NotUseTimeozoneName__;

    /** 打刻時間 テキスト表示 */
    private String tcd020StrikeTimeStr__;
    /** 開始時間 テキスト表示 */
    private String tcd020InTimeStr__;
    /** 終業時間 テキスト表示 */
    private String tcd020OutTimeStr__;
    /** 時間帯 テキスト表示 */
    private String tcd020TimezoneStr__;

    /** 更新者名 テキスト表示 */
    private String tcd020UpdateUserName__;
    /** 更新日時 テキスト表示 */
    private String tcd020UpdateDate__;

    /** 時リスト */
    private ArrayList < LabelValueBean > tcd020HourLabel__;

    /** 分リスト */
    private ArrayList < LabelValueBean > tcd020MinuteLabel__;

    /** 分リスト（打刻時間） */
    private ArrayList < LabelValueBean > tcd020StrikeMinuteLabel__;

    /** 時間帯リスト */
    private ArrayList < LabelValueBean > tcd020TimezoneList__;

    /** 休日区分リスト */
    private ArrayList < LabelValueBean > tcd020HolKbnList__;

    /** 始業終業時間編集権限 */
    private String tcd020LockFlg__ = String.valueOf(GSConstTimecard.UNLOCK_FLG);
    /** 打刻時間編集権限 */
    private String tcd020LockStrike__ = String.valueOf(GSConstTimecard.UNLOCK_FLG);
    /** 備考入力必須 */
    private String tcd020LockBiko__ = String.valueOf(GSConstTimecard.BIKO_UNNECESSARY_FLG);
    /** 早退区分編集権限 */
    private String tcd020LockLate__ = String.valueOf(GSConstTimecard.UNLOCK_FLG);
    /** 休日編集権限 */
    private String tcd020LockHoliday__ = String.valueOf(GSConstTimecard.UNLOCK_FLG);
    /** 時間帯編集権限 */
    private String tcd020LockTimezone__ = String.valueOf(GSConstTimecard.UNLOCK_FLG);

    /** 複数編集フラグ*/
    private boolean pluralFlg__ = GSConstTimecard.PLURAL_FLG_SINGLE;

    /** 初期表示区分 */
    private int tcd020initFlg__;
    /** 休日内容区分 */
    private String tcd020HolidayContentKbn__;
    /**
     * <p>tcd020initFlg を取得します。
     * @return tcd020initFlg
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020initFlg__
     */
    public int getTcd020initFlg() {
        return tcd020initFlg__;
    }

    /**
     * <p>tcd020initFlg をセットします。
     * @param tcd020initFlg tcd020initFlg
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020initFlg__
     */
    public void setTcd020initFlg(int tcd020initFlg) {
        tcd020initFlg__ = tcd020initFlg;
    }

    /**
     * <p>tcd020LockFlg を取得します。
     * @return tcd020LockFlg
     */
    public String getTcd020LockFlg() {
        return tcd020LockFlg__;
    }

    /**
     * <p>tcd020LockFlg をセットします。
     * @param tcd020LockFlg tcd020LockFlg
     */
    public void setTcd020LockFlg(String tcd020LockFlg) {
        tcd020LockFlg__ = tcd020LockFlg;
    }

    /**
     * <p>tcd020LockHoliday を取得します。
     * @return tcd020LockHoliday
     */
    public String getTcd020LockHoliday() {
        return tcd020LockHoliday__;
    }

    /**
     * <p>tcd020LockHoliday をセットします。
     * @param tcd020LockHoliday tcd020LockHoliday
     */
    public void setTcd020LockHoliday(String tcd020LockHoliday) {
        tcd020LockHoliday__ = tcd020LockHoliday;
    }

    /**
     * <p>tcd020LockLate を取得します。
     * @return tcd020LockLate
     */
    public String getTcd020LockLate() {
        return tcd020LockLate__;
    }

    /**
     * <p>tcd020LockLate をセットします。
     * @param tcd020LockLate tcd020LockLate
     */
    public void setTcd020LockLate(String tcd020LockLate) {
        tcd020LockLate__ = tcd020LockLate;
    }

    /**
     * <p>tcd020LockStrike を取得します。
     * @return tcd020LockStrike
     */
    public String getTcd020LockStrike() {
        return tcd020LockStrike__;
    }

    /**
     * <p>tcd020LockStrike をセットします。
     * @param tcd020LockStrike tcd020LockStrike
     */
    public void setTcd020LockStrike(String tcd020LockStrike) {
        tcd020LockStrike__ = tcd020LockStrike;
    }

    /**
     * <p>tcd020StrikeMinuteLabel を取得します。
     * @return tcd020StrikeMinuteLabel
     */
    public ArrayList<LabelValueBean> getTcd020StrikeMinuteLabel() {
        return tcd020StrikeMinuteLabel__;
    }

    /**
     * <p>tcd020StrikeMinuteLabel をセットします。
     * @param tcd020StrikeMinuteLabel tcd020StrikeMinuteLabel
     */
    public void setTcd020StrikeMinuteLabel(
            ArrayList<LabelValueBean> tcd020StrikeMinuteLabel) {
        tcd020StrikeMinuteLabel__ = tcd020StrikeMinuteLabel;
    }

    /**
     * <p>tcd020TimezoneList を取得します。
     * @return tcd020TimezoneList
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020TimezoneList__
     */
    public ArrayList<LabelValueBean> getTcd020TimezoneList() {
        return tcd020TimezoneList__;
    }

    /**
     * <p>tcd020TimezoneList をセットします。
     * @param tcd020TimezoneList tcd020TimezoneList
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020TimezoneList__
     */
    public void setTcd020TimezoneList(
            ArrayList<LabelValueBean> tcd020TimezoneList) {
        tcd020TimezoneList__ = tcd020TimezoneList;
    }

    /**
     * <p>tcd020HolKbnList を取得します。
     * @return tcd020HolKbnList
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020HolKbnList__
     */
    public ArrayList<LabelValueBean> getTcd020HolKbnList() {
        return tcd020HolKbnList__;
    }

    /**
     * <p>tcd020HolKbnList をセットします。
     * @param tcd020HolKbnList tcd020HolKbnList
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020HolKbnList__
     */
    public void setTcd020HolKbnList(ArrayList<LabelValueBean> tcd020HolKbnList) {
        tcd020HolKbnList__ = tcd020HolKbnList;
    }

    /**
     * <p>tcd020ChkKbn を取得します。
     * @return tcd020ChkKbn
     */
    public String getTcd020ChkKbn() {
        return tcd020ChkKbn__;
    }

    /**
     * <p>tcd020ChkKbn をセットします。
     * @param tcd020ChkKbn tcd020ChkKbn
     */
    public void setTcd020ChkKbn(String tcd020ChkKbn) {
        tcd020ChkKbn__ = tcd020ChkKbn;
    }

    /**
     * <p>tcd020SouKbn を取得します。
     * @return tcd020SouKbn
     */
    public String getTcd020SouKbn() {
        return tcd020SouKbn__;
    }

    /**
     * <p>tcd020SouKbn をセットします。
     * @param tcd020SouKbn tcd020SouKbn
     */
    public void setTcd020SouKbn(String tcd020SouKbn) {
        tcd020SouKbn__ = tcd020SouKbn;
    }

    /**
     * <p>tcd020Timezone を取得します。
     * @return tcd020Timezone
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020Timezone__
     */
    public String getTcd020Timezone() {
        return tcd020Timezone__;
    }

    /**
     * <p>tcd020Timezone をセットします。
     * @param tcd020Timezone tcd020Timezone
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020Timezone__
     */
    public void setTcd020Timezone(String tcd020Timezone) {
        tcd020Timezone__ = tcd020Timezone;
    }

    /**
     * <p>tcd020NotUseHolKbnName を取得します。
     * @return tcd020NotUseHolKbnName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020NotUseHolKbnName__
     */
    public String getTcd020NotUseHolKbnName() {
        return tcd020NotUseHolKbnName__;
    }

    /**
     * <p>tcd020NotUseHolKbnName をセットします。
     * @param tcd020NotUseHolKbnName tcd020NotUseHolKbnName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020NotUseHolKbnName__
     */
    public void setTcd020NotUseHolKbnName(String tcd020NotUseHolKbnName) {
        tcd020NotUseHolKbnName__ = tcd020NotUseHolKbnName;
    }

    /**
     * <p>tcd020Biko を取得します。
     * @return tcd020Biko
     */
    public String getTcd020Biko() {
        return tcd020Biko__;
    }

    /**
     * <p>tcd020Biko をセットします。
     * @param tcd020Biko tcd020Biko
     */
    public void setTcd020Biko(String tcd020Biko) {
        tcd020Biko__ = tcd020Biko;
    }

    /**
     * <p>tcd020Date を取得します。
     * @return tcd020Date
     */
    public String getTcd020Date() {
        return tcd020Date__;
    }

    /**
     * <p>tcd020Date をセットします。
     * @param tcd020Date tcd020Date
     */
    public void setTcd020Date(String tcd020Date) {
        tcd020Date__ = tcd020Date;
    }

    /**
     * <p>tcd020HolDays を取得します。
     * @return tcd020HolDays
     */
    public String getTcd020HolDays() {
        return tcd020HolDays__;
    }

    /**
     * <p>tcd020HolDays をセットします。
     * @param tcd020HolDays tcd020HolDays
     */
    public void setTcd020HolDays(String tcd020HolDays) {
        tcd020HolDays__ = tcd020HolDays;
    }

    /**
     * <p>tcd020HolKbn を取得します。
     * @return tcd020HolKbn
     */
    public int getTcd020HolKbn() {
        return tcd020HolKbn__;
    }

    /**
     * <p>tcd020HolKbn をセットします。
     * @param tcd020HolKbn tcd020HolKbn
     */
    public void setTcd020HolKbn(int tcd020HolKbn) {
        tcd020HolKbn__ = tcd020HolKbn;
    }

    /**
     * <p>tcd020HolKbnName を取得します。
     * @return tcd020HolKbnName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020HolKbnName__
     */
    public String getTcd020HolKbnName() {
        return tcd020HolKbnName__;
    }

    /**
     * <p>tcd020HolKbnName をセットします。
     * @param tcd020HolKbnName tcd020HolKbnName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020HolKbnName__
     */
    public void setTcd020HolKbnName(String tcd020HolKbnName) {
        tcd020HolKbnName__ = tcd020HolKbnName;
    }

    /**
     * <p>tcd020HolValue を取得します。
     * @return tcd020HolValue
     */
    public String getTcd020HolValue() {
        return tcd020HolValue__;
    }

    /**
     * <p>tcd020HolValue をセットします。
     * @param tcd020HolValue tcd020HolValue
     */
    public void setTcd020HolValue(String tcd020HolValue) {
        tcd020HolValue__ = tcd020HolValue;
    }

    /**
     * <p>tcd020HourLabel を取得します。
     * @return tcd020HourLabel
     */
    public ArrayList<LabelValueBean> getTcd020HourLabel() {
        return tcd020HourLabel__;
    }

    /**
     * <p>tcd020HourLabel をセットします。
     * @param tcd020HourLabel tcd020HourLabel
     */
    public void setTcd020HourLabel(ArrayList<LabelValueBean> tcd020HourLabel) {
        tcd020HourLabel__ = tcd020HourLabel;
    }

    /**
     * <p>tcd020InHour を取得します。
     * @return tcd020InHour
     */
    public String getTcd020InHour() {
        return tcd020InHour__;
    }

    /**
     * <p>tcd020InHour をセットします。
     * @param tcd020InHour tcd020InHour
     */
    public void setTcd020InHour(String tcd020InHour) {
        tcd020InHour__ = tcd020InHour;
    }

    /**
     * <p>tcd020InMinute を取得します。
     * @return tcd020InMinute
     */
    public String getTcd020InMinute() {
        return tcd020InMinute__;
    }

    /**
     * <p>tcd020InMinute をセットします。
     * @param tcd020InMinute tcd020InMinute
     */
    public void setTcd020InMinute(String tcd020InMinute) {
        tcd020InMinute__ = tcd020InMinute;
    }

    /**
     * <p>tcd020MinuteLabel を取得します。
     * @return tcd020MinuteLabel
     */
    public ArrayList<LabelValueBean> getTcd020MinuteLabel() {
        return tcd020MinuteLabel__;
    }

    /**
     * <p>tcd020MinuteLabel をセットします。
     * @param tcd020MinuteLabel tcd020MinuteLabel
     */
    public void setTcd020MinuteLabel(ArrayList<LabelValueBean> tcd020MinuteLabel) {
        tcd020MinuteLabel__ = tcd020MinuteLabel;
    }

    /**
     * <p>tcd020Name を取得します。
     * @return tcd020Name
     */
    public String getTcd020Name() {
        return tcd020Name__;
    }

    /**
     * <p>tcd020Name をセットします。
     * @param tcd020Name tcd020Name
     */
    public void setTcd020Name(String tcd020Name) {
        tcd020Name__ = tcd020Name;
    }

    /**
     * <p>tcd020OutHour を取得します。
     * @return tcd020OutHour
     */
    public String getTcd020OutHour() {
        return tcd020OutHour__;
    }

    /**
     * <p>tcd020OutHour をセットします。
     * @param tcd020OutHour tcd020OutHour
     */
    public void setTcd020OutHour(String tcd020OutHour) {
        tcd020OutHour__ = tcd020OutHour;
    }

    /**
     * <p>tcd020OutMinute を取得します。
     * @return tcd020OutMinute
     */
    public String getTcd020OutMinute() {
        return tcd020OutMinute__;
    }

    /**
     * <p>tcd020OutMinute をセットします。
     * @param tcd020OutMinute tcd020OutMinute
     */
    public void setTcd020OutMinute(String tcd020OutMinute) {
        tcd020OutMinute__ = tcd020OutMinute;
    }

    /**
     * <p>tcd020StrikeInHour を取得します。
     * @return tcd020StrikeInHour
     */
    public String getTcd020StrikeInHour() {
        return tcd020StrikeInHour__;
    }

    /**
     * <p>tcd020StrikeInHour をセットします。
     * @param tcd020StrikeInHour tcd020StrikeInHour
     */
    public void setTcd020StrikeInHour(String tcd020StrikeInHour) {
        tcd020StrikeInHour__ = tcd020StrikeInHour;
    }

    /**
     * <p>tcd020StrikeInMinute を取得します。
     * @return tcd020StrikeInMinute
     */
    public String getTcd020StrikeInMinute() {
        return tcd020StrikeInMinute__;
    }

    /**
     * <p>tcd020StrikeInMinute をセットします。
     * @param tcd020StrikeInMinute tcd020StrikeInMinute
     */
    public void setTcd020StrikeInMinute(String tcd020StrikeInMinute) {
        tcd020StrikeInMinute__ = tcd020StrikeInMinute;
    }

    /**
     * <p>tcd020StrikeOutHour を取得します。
     * @return tcd020StrikeOutHour
     */
    public String getTcd020StrikeOutHour() {
        return tcd020StrikeOutHour__;
    }

    /**
     * <p>tcd020StrikeOutHour をセットします。
     * @param tcd020StrikeOutHour tcd020StrikeOutHour
     */
    public void setTcd020StrikeOutHour(String tcd020StrikeOutHour) {
        tcd020StrikeOutHour__ = tcd020StrikeOutHour;
    }

    /**
     * <p>tcd020StrikeOutMinute を取得します。
     * @return tcd020StrikeOutMinute
     */
    public String getTcd020StrikeOutMinute() {
        return tcd020StrikeOutMinute__;
    }

    /**
     * <p>tcd020StrikeOutMinute をセットします。
     * @param tcd020StrikeOutMinute tcd020StrikeOutMinute
     */
    public void setTcd020StrikeOutMinute(String tcd020StrikeOutMinute) {
        tcd020StrikeOutMinute__ = tcd020StrikeOutMinute;
    }

    /**
     * <p>usrKbn を取得します。
     * @return usrKbn
     */
    public String getUsrKbn() {
        return usrKbn__;
    }

    /**
     * <p>usrKbn をセットします。
     * @param usrKbn usrKbn
     */
    public void setUsrKbn(String usrKbn) {
        usrKbn__ = usrKbn;
    }

    /**
     * <p>tcd020LockBiko を取得します。
     * @return tcd020LockBiko
     */
    public String getTcd020LockBiko() {
        return tcd020LockBiko__;
    }

    /**
     * <p>tcd020LockBiko をセットします。
     * @param tcd020LockBiko tcd020LockBiko
     */
    public void setTcd020LockBiko(String tcd020LockBiko) {
        tcd020LockBiko__ = tcd020LockBiko;
    }

    /**
     * <p>pluralFlg を取得します。
     * @return pluralFlg
     */
    public boolean isPluralFlg() {
        return pluralFlg__;
    }

    /**
     * <p>pluralFlg をセットします。
     * @param pluralFlg pluralFlg
     */
    public void setPluralFlg(boolean pluralFlg) {
        pluralFlg__ = pluralFlg;
    }

    /**
     * <p>tcd020InTimeStr を取得します。
     * @return tcd020InTimeStr
     */
    public String getTcd020InTimeStr() {
        return tcd020InTimeStr__;
    }

    /**
     * <p>tcd020InTimeStr をセットします。
     * @param tcd020InTimeStr tcd020InTimeStr
     */
    public void setTcd020InTimeStr(String tcd020InTimeStr) {
        tcd020InTimeStr__ = tcd020InTimeStr;
    }

    /**
     * <p>tcd020OutTimeStr を取得します。
     * @return tcd020OutTimeStr
     */
    public String getTcd020OutTimeStr() {
        return tcd020OutTimeStr__;
    }

    /**
     * <p>tcd020OutTimeStr をセットします。
     * @param tcd020OutTimeStr tcd020OutTimeStr
     */
    public void setTcd020OutTimeStr(String tcd020OutTimeStr) {
        tcd020OutTimeStr__ = tcd020OutTimeStr;
    }


    /**
     * <p>tcd020TimezoneStr を取得します。
     * @return tcd020TimezoneStr
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020TimezoneStr__
     */
    public String getTcd020TimezoneStr() {
        return tcd020TimezoneStr__;
    }

    /**
     * <p>tcd020TimezoneStr をセットします。
     * @param tcd020TimezoneStr tcd020TimezoneStr
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020TimezoneStr__
     */
    public void setTcd020TimezoneStr(String tcd020TimezoneStr) {
        tcd020TimezoneStr__ = tcd020TimezoneStr;
    }

    /**
     * <p>tcd020UpdateUserName を取得します。
     * @return tcd020UpdateUserName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020UpdateUserName__
     */
    public String getTcd020UpdateUserName() {
        return tcd020UpdateUserName__;
    }

    /**
     * <p>tcd020UpdateUserName をセットします。
     * @param tcd020UpdateUserName tcd020UpdateUserName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020UpdateUserName__
     */
    public void setTcd020UpdateUserName(String tcd020UpdateUserName) {
        tcd020UpdateUserName__ = tcd020UpdateUserName;
    }

    /**
     * <p>tcd020UpdateDate を取得します。
     * @return tcd020UpdateDate
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020UpdateDate__
     */
    public String getTcd020UpdateDate() {
        return tcd020UpdateDate__;
    }

    /**
     * <p>tcd020UpdateDate をセットします。
     * @param tcd020UpdateDate tcd020UpdateDate
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020UpdateDate__
     */
    public void setTcd020UpdateDate(String tcd020UpdateDate) {
        tcd020UpdateDate__ = tcd020UpdateDate;
    }

    /**
     * <p>tcd020StrikeTimeStr を取得します。
     * @return tcd020StrikeTimeStr
     */
    public String getTcd020StrikeTimeStr() {
        return tcd020StrikeTimeStr__;
    }

    /**
     * <p>tcd020StrikeTimeStr をセットします。
     * @param tcd020StrikeTimeStr tcd020StrikeTimeStr
     */
    public void setTcd020StrikeTimeStr(String tcd020StrikeTimeStr) {
        tcd020StrikeTimeStr__ = tcd020StrikeTimeStr;
    }

    /**
     * <p>tcd020LockTimezone を取得します。
     * @return tcd020LockTimezone
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020LockTimezone__
     */
    public String getTcd020LockTimezone() {
        return tcd020LockTimezone__;
    }

    /**
     * <p>tcd020LockTimezone をセットします。
     * @param tcd020LockTimezone tcd020LockTimezone
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020LockTimezone__
     */
    public void setTcd020LockTimezone(String tcd020LockTimezone) {
        tcd020LockTimezone__ = tcd020LockTimezone;
    }

    /**
     * <p>tcd020HolidayContentKbn を取得します。
     * @return tcd020HolidayContentKbn
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020HolidayContentKbn__
     */
    public String getTcd020HolidayContentKbn() {
        return tcd020HolidayContentKbn__;
    }

    /**
     * <p>tcd020HolidayContentKbn をセットします。
     * @param tcd020HolidayContentKbn tcd020HolidayContentKbn
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020HolidayContentKbn__
     */
    public void setTcd020HolidayContentKbn(String tcd020HolidayContentKbn) {
        tcd020HolidayContentKbn__ = tcd020HolidayContentKbn;
    }

    /**
     * タイムカード編集画面の入力チェックを行います
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param admConf 基本設定
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException
     * @throws NumberFormatException
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl, TcdAdmConfModel admConf, Connection con)
                    throws NumberFormatException, SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (!StringUtil.isNullZeroString(tcd020Timezone__)) {
            TcdTimezonePriDao dao = new TcdTimezonePriDao(con);
            int count = dao.getTimezoneUse(
                    Integer.parseInt(tcd020Timezone__), Integer.parseInt(getUsrSid()));
            if (count == 0) {
                count = dao.getCanUseTimezone(Integer.parseInt(getUsrSid()));
                if (count > 0
                        || Integer.parseInt(tcd020Timezone__) != admConf.getTacDefTimezone()) {
                    msg = new ActionMessage("tcd020.cannot.use.timezone");
                    errors.add("tcd020.cannot.use.timezone", msg);
                }
            }
        }

        if (!pluralFlg__ && tcd020LockStrike__.equals(String.valueOf(GSConstTimecard.UNLOCK_FLG))) {
            //開始時間
            boolean strikeFromOk = true;
            //時間関連チェック 片側入力
            if (tcd020StrikeInHour__.equals("-1") && !tcd020StrikeInMinute__.equals("-1")
            || !tcd020StrikeInHour__.equals("-1") && tcd020StrikeInMinute__.equals("-1")
            ) {
                String inTime = gsMsg.getMessage("tcd.180");
                msg = new ActionMessage("error.input.notvalidate.data", inTime);
                errors.add("" + "error.input.notvalidate.data", msg);
                strikeFromOk = false;
            }
            //終了時間
            boolean strikeToOk = true;
            if (tcd020StrikeOutHour__.equals("-1") && !tcd020StrikeOutMinute__.equals("-1")
            || !tcd020StrikeOutHour__.equals("-1") && tcd020StrikeOutMinute__.equals("-1")) {
                String outTime = gsMsg.getMessage("tcd.181");
                msg = new ActionMessage("error.input.notvalidate.data", outTime);
                errors.add("" + "error.input.notvalidate.data", msg);
                strikeToOk = false;
            }

            UDate strikeInDate = new UDate();
            strikeInDate.setZeroHhMmSs();
            UDate strikeOutDate = strikeInDate.cloneUDate();
            if (strikeFromOk && strikeToOk) {

                if (!tcd020StrikeInHour__.equals("-1")
                        && !tcd020StrikeOutHour__.equals("-1")) {
                    strikeInDate.setHour(Integer.parseInt(tcd020StrikeInHour__));
                    strikeInDate.setMinute(Integer.parseInt(tcd020StrikeInMinute__));
                    strikeOutDate.setHour(Integer.parseInt(tcd020StrikeOutHour__));
                    strikeOutDate.setMinute(Integer.parseInt(tcd020StrikeOutMinute__));

                    String gsmsg = gsMsg.getMessage("tcd.158");
                    String gsmsg2 = gsMsg.getMessage("tcd.159");

                    //大小チェック
                    if (strikeInDate.compare(strikeInDate, strikeOutDate) != UDate.LARGE) {
                        msg = new ActionMessage("error.input.comp.text", gsmsg, gsmsg2);
                        errors.add("" + "error.input.comp.text", msg);
                        strikeFromOk = false;
                        strikeToOk = false;
                    }

                    //24時間以上の稼動はエラー
                    if (__isOver24HoursStrike(reqMdl)) {
                        String startToEnd = gsMsg.getMessage("tcd.214");
                        String under24 = gsMsg.getMessage("tcd.161");
                        msg = new ActionMessage("error.input.comp.text", startToEnd, under24);
                        errors.add("" + "error.input.comp.text", msg);
                        strikeFromOk = false;
                        strikeToOk = false;
                    }

                } else if (!tcd020StrikeInHour__.equals("-1")
                        && tcd020StrikeOutHour__.equals("-1")) {

                    if (StringUtil.isNullZeroString(getEditDay())) {
                        String outTime = gsMsg.getMessage("tcd.181");
                        //複数編集時はfromのみの入力はエラー
                        msg = new ActionMessage("error.input.required.text", outTime);
                        errors.add("" + "error.input.required.text", msg);
                    }
                    strikeToOk = false;
                }
            }
        }

        if (tcd020LockFlg__.equals(String.valueOf(GSConstTimecard.UNLOCK_FLG))) {
            String gsmsg = "";
            //開始時間
            boolean fromOk = true;
            //時間関連チェック 片側入力
            if (tcd020InHour__.equals("-1") && !tcd020InMinute__.equals("-1")
            || !tcd020InHour__.equals("-1") && tcd020InMinute__.equals("-1")) {
                gsmsg = gsMsg.getMessage("tcd.28");
                msg = new ActionMessage("error.input.notvalidate.data", gsmsg);
                errors.add("" + "error.input.notvalidate.data", msg);
                fromOk = false;
            }
            //終了時間
            boolean toOk = true;
            if (tcd020OutHour__.equals("-1") && !tcd020OutMinute__.equals("-1")
            || !tcd020OutHour__.equals("-1") && tcd020OutMinute__.equals("-1")
            ) {
                gsmsg = gsMsg.getMessage("tcd.24");
                msg = new ActionMessage("error.input.notvalidate.data", gsmsg);
                errors.add("" + "error.input.notvalidate.data", msg);
                toOk = false;
            }

            UDate inDate = new UDate();
            inDate.setZeroHhMmSs();
            UDate outDate = inDate.cloneUDate();
            if (fromOk && toOk) {

                if (tcd020InHour__.equals("-1") && !tcd020OutHour__.equals("-1")) {
                    gsmsg = gsMsg.getMessage("tcd.28");
                    //終了のみはエラー
                    msg = new ActionMessage("error.input.required.text", gsmsg);
                    errors.add("" + "error.input.required.text", msg);
                    fromOk = false;
                    toOk = false;
                } else if (!tcd020InHour__.equals("-1") && !tcd020OutHour__.equals("-1")) {
                    inDate.setHour(Integer.parseInt(tcd020InHour__));
                    inDate.setMinute(Integer.parseInt(tcd020InMinute__));
                    outDate.setHour(Integer.parseInt(tcd020OutHour__));
                    outDate.setMinute(Integer.parseInt(tcd020OutMinute__));
                    //大小チェック
                    if (inDate.compare(inDate, outDate) != UDate.LARGE) {
                        String startEnd = gsMsg.getMessage("tcd.158");
                        String startEnd2 = gsMsg.getMessage("tcd.159");

                        msg = new ActionMessage("error.input.comp.text", startEnd, startEnd2);
                        errors.add("" + "error.input.comp.text", msg);
                        fromOk = false;
                        toOk = false;
                    }
                    //24時間以上の稼動はエラー
                    if (__isOver24Hours(reqMdl)) {
                        String startToEnd = gsMsg.getMessage("tcd.160");
                        String under24 = gsMsg.getMessage("tcd.161");
                        msg = new ActionMessage("error.input.comp.text", startToEnd, under24);
                        errors.add("" + "error.input.comp.text", msg);
                        fromOk = false;
                        toOk = false;
                    }
                } else if (!tcd020InHour__.equals("-1") && tcd020OutHour__.equals("-1")) {

                    gsmsg = gsMsg.getMessage("tcd.24");
                    if (StringUtil.isNullZeroString(getEditDay())) {
                        //複数編集時はfromのみの入力はエラー
                        msg = new ActionMessage("error.input.required.text", gsmsg);
                        errors.add("" + "error.input.required.text", msg);
                    } else {
                        //システム日付以外の日はfromのみの入力はエラー
                        int sime = admConf.getTacSimebi();
                        Tcd020Biz biz = new Tcd020Biz();
                        UDate sysDate = new UDate();
                        UDate editDate = biz.getEditDate(
                                Integer.parseInt(getYear()),
                                Integer.parseInt(getMonth()),
                                Integer.parseInt(getEditDay()),
                                sime);

                        if (sysDate.compareDateYMD(editDate) != UDate.EQUAL) {
                            msg = new ActionMessage("error.input.required.text", gsmsg);
                            errors.add("" + "error.input.required.text", msg);
                        }
                    }
                    toOk = false;
                }

            }
        }

        String biko = gsMsg.getMessage("cmn.memo");
        //備考のチェック
        if (!StringUtil.isNullZeroString(tcd020Biko__)) {
            if (__checkRange(
                    errors,
                    tcd020Biko__,
                    "tcd020Biko",
                    biko,
                    GSConstTimecard.MAX_LENGTH_BIKO)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(tcd020Biko__)) {
                    msg = new ActionMessage("error.input.spase.start", biko);
                    StrutsUtil.addMessage(errors, msg, "tcd020Biko");
                } else {
                    __checkJisString(
                            errors,
                            tcd020Biko__,
                            "tcd020Biko",
                            biko);
                }
            }
        } else if (tcd020LockBiko__.equals(String.valueOf(GSConstTimecard.BIKO_NECESSARY_FLG))) {
            msg = new ActionMessage("error.input.required.text", biko);
            StrutsUtil.addMessage(errors, msg, "tcd020Biko");
        }

        //休日区分存在チェック
        String holKbn = gsMsg.getMessage("tcd.40");
        if (tcd020LockHoliday__.equals(String.valueOf(GSConstTimecard.UNLOCK_FLG))) {
            if (tcd020HolKbn__ != GSConstTimecard.HOL_KBN_UNSELECT) {
                TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
                int cnt = holInfDao.existsHoliday(tcd020HolKbn__, true);
                if (cnt == 0) {
                    msg = new ActionMessage("tcd020.cannot.use.holkbn", holKbn);
                    StrutsUtil.addMessage(errors, msg, "tcd020HolKbn");
                }
            }
        }

        //その他
        if (!StringUtil.isNullZeroString(tcd020HolValue__)) {
            String sonota = gsMsg.getMessage("tcd.182");
            if (__checkRange(
                    errors,
                    tcd020HolValue__,
                    "tcd020HolValue",
                    sonota,
                    GSConstTimecard.MAX_LENGTH_SONOTA)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(tcd020HolValue__)) {
                    msg = new ActionMessage("error.input.spase.start",
                            GSConstTimecard.MAX_LENGTH_BIKO);
                    StrutsUtil.addMessage(errors, msg, "tcd020HolValue");
                } else {
                    __checkJisString(
                            errors,
                            tcd020HolValue__,
                            "tcd020HolValue",
                            sonota);
                }
            }
        }
        String nissu = gsMsg.getMessage("tcd.tcd020.06");
        //休日日数
        if (tcd020LockHoliday__.equals(String.valueOf(GSConstTimecard.UNLOCK_FLG))) {
            if (tcd020HolKbn__ != (GSConstTimecard.HOL_KBN_UNSELECT)) {
                //必須チェック
                if (StringUtil.isNullZeroString(tcd020HolDays__)) {
                    msg = new ActionMessage("error.input.required.text", nissu);
                    StrutsUtil.addMessage(errors, msg, "tcd020HolDays");
                } else if (!GSValidateUtil.isNumberDot(tcd020HolDays__)) {
                    msg = new ActionMessage("error.input.number.text", nissu);
                    StrutsUtil.addMessage(errors, msg, "tcd020HolDays");
                }
            }
        }

        //その他
        if (!StringUtil.isNullZeroString(tcd020HolDays__)) {
            if (__checkRange(
                    errors,
                    tcd020HolDays__,
                    "tcd020HolDays",
                    nissu,
                    GSConstTimecard.MAX_LENGTH_SONOTA)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(tcd020HolDays__)) {
                    msg = new ActionMessage("error.input.spase.start",
                            GSConstTimecard.MAX_LENGTH_HOLDAYS);
                    StrutsUtil.addMessage(errors, msg, "tcd020HolDays");
                } else {
                    String gsmsg = gsMsg.getMessage("tcd.tcd020.09");
                    if (!ValidateUtil.isNumberDot(tcd020HolDays__, 3, 3)) {
                        msg = new ActionMessage("error.input.comp.text", nissu, gsmsg);
                        StrutsUtil.addMessage(errors, msg, "tcd020HolDays");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 稼動時間が24時間以上はエラーとする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return boolean true:24時間以上 false:24時間未満
     */
    private boolean __isOver24Hours(RequestModel reqMdl) {

        if (tcd020InHour__.equals("-1")
         || tcd020InMinute__.equals("-1")
         || tcd020OutHour__.equals("-1")
         || tcd020OutMinute__.equals("-1")) {
            return false;
        }

        int frHm = Integer.parseInt(tcd020InHour__) * 100 + Integer.parseInt(tcd020InMinute__);
        int toHm = Integer.parseInt(tcd020OutHour__) * 100 + Integer.parseInt(tcd020OutMinute__);
        TimecardCalcBiz biz = new TimecardCalcBiz(reqMdl);
        int min = biz.changeTime(frHm, toHm);
        log__.debug("min==>" + min);
        if (min >= 1440) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 打刻時間が24時間以上はエラーとする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return boolean true:24時間以上 false:24時間未満
     */
    private boolean __isOver24HoursStrike(RequestModel reqMdl) {

        if (tcd020StrikeInHour__.equals("-1")
         || tcd020StrikeInMinute__.equals("-1")
         || tcd020StrikeOutHour__.equals("-1")
         || tcd020StrikeOutMinute__.equals("-1")) {
            return false;
        }

        int frHm = Integer.parseInt(tcd020StrikeInHour__) * 100
                 + Integer.parseInt(tcd020StrikeInMinute__);
        int toHm = Integer.parseInt(tcd020StrikeOutHour__) * 100
                 + Integer.parseInt(tcd020StrikeOutMinute__);
        TimecardCalcBiz biz = new TimecardCalcBiz(reqMdl);
        int min = biz.changeTime(frHm, toHm);
        log__.debug("min==>" + min);
        if (min >= 1440) {
            return true;
        }
        return false;
    }
    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text", msg);
            result = false;

        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目がJIS第2水準文字かチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkJisString(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;
        //JIS第2水準文字か
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <p>tcd020NotUseTimeozoneName を取得します。
     * @return tcd020NotUseTimeozoneName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020NotUseTimeozoneName__
     */
    public String getTcd020NotUseTimeozoneName() {
        return tcd020NotUseTimeozoneName__;
    }

    /**
     * <p>tcd020NotUseTimeozoneName をセットします。
     * @param tcd020NotUseTimeozoneName tcd020NotUseTimeozoneName
     * @see jp.groupsession.v2.tcd.tcd020.Tcd020Form#tcd020NotUseTimeozoneName__
     */
    public void setTcd020NotUseTimeozoneName(String tcd020NotUseTimeozoneName) {
        tcd020NotUseTimeozoneName__ = tcd020NotUseTimeozoneName;
    }

}
