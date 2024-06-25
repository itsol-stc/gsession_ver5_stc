package jp.groupsession.v2.api.timecard.edit;

import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] タイムカード情報の登録・変更を行うWEBAPIパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiTcdEditParamModel extends AbstractParamModel {
    /** 編集モード */
    private int cmdMode__ = GSConstTimecard.CMDMODE_EDIT;

    /** ユーザID */
    private String userId__ = null;
    /** 登録日 */
    private String targetDate__ = null;
    /** 時間帯 */
    private String timezoneType__ = null;
    /** 打刻始業時間 */
    private String stampStartTime__ = null;
    /** 打刻終業時間 */
    private String stampEndTime__ = null;
    /** 始業時間 */
    private String startTime__ = null;
    /** 終業時間 */
    private String endTime__ = null;
    /** 備考 */
    private String remarksText__ = null;
    /** 遅刻区分 */
    private String chikokuFlg__ = null;
    /** 早退区分 */
    private String soutaiFlg__ = null;
    /** 休日区分 */
    private String holidayType__ = null;
    /** 休日日数 */
    private String holidayNum__ = null;
    /** 休日内容 */
    private String holidayText__ = null;


    /**
     * <p>cmdMode を取得します。
     * @return cmdMode
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditParamModel#cmdMode__
     */
    public int getCmdMode() {
        return cmdMode__;
    }
    /**
     * <p>cmdMode をセットします。
     * @param cmdMode cmdMode
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditParamModel#cmdMode__
     */
    public void setCmdMode(int cmdMode) {
        cmdMode__ = cmdMode;
    }
    /**
     * <p>userId を取得します。
     * @return userId
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#userId__
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#userId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>targetDate を取得します。
     * @return targetDate
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#targetDate__
     */
    public String getTargetDate() {
        return targetDate__;
    }
    /**
     * <p>targetDate をセットします。
     * @param targetDate targetDate
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#targetDate__
     */
    public void setTargetDate(String targetDate) {
        targetDate__ = targetDate;
    }
    /**
     * <p>timezoneType を取得します。
     * @return timezoneType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#timezoneType__
     */
    public String getTimezoneType() {
        return timezoneType__;
    }
    /**
     * <p>timezoneType をセットします。
     * @param timezoneType timezoneType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#timezoneType__
     */
    public void setTimezoneType(String timezoneType) {
        timezoneType__ = timezoneType;
    }
    /**
     * <p>stampStartTime を取得します。
     * @return stampStartTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampStartTime__
     */
    public String getStampStartTime() {
        return stampStartTime__;
    }
    /**
     * <p>stampStartTime をセットします。
     * @param stampStartTime stampStartTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampStartTime__
     */
    public void setStampStartTime(String stampStartTime) {
        stampStartTime__ = stampStartTime;
    }
    /**
     * <p>stampEndTime を取得します。
     * @return stampEndTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampEndTime__
     */
    public String getStampEndTime() {
        return stampEndTime__;
    }
    /**
     * <p>stampEndTime をセットします。
     * @param stampEndTime stampEndTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampEndTime__
     */
    public void setStampEndTime(String stampEndTime) {
        stampEndTime__ = stampEndTime;
    }
    /**
     * <p>startTime を取得します。
     * @return startTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#startTime__
     */
    public String getStartTime() {
        return startTime__;
    }
    /**
     * <p>startTime をセットします。
     * @param startTime startTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#startTime__
     */
    public void setStartTime(String startTime) {
        startTime__ = startTime;
    }
    /**
     * <p>endTime を取得します。
     * @return endTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#endTime__
     */
    public String getEndTime() {
        return endTime__;
    }
    /**
     * <p>endTime をセットします。
     * @param endTime endTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#endTime__
     */
    public void setEndTime(String endTime) {
        endTime__ = endTime;
    }
    /**
     * <p>remarksText を取得します。
     * @return remarksText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#remarksText__
     */
    public String getRemarksText() {
        return remarksText__;
    }
    /**
     * <p>remarksText をセットします。
     * @param remarksText remarksText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#remarksText__
     */
    public void setRemarksText(String remarksText) {
        remarksText__ = remarksText;
    }
    /**
     * <p>chikokuFlg を取得します。
     * @return chikokuFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#chikokuFlg__
     */
    public String getChikokuFlg() {
        return chikokuFlg__;
    }
    /**
     * <p>chikokuFlg をセットします。
     * @param chikokuFlg chikokuFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#chikokuFlg__
     */
    public void setChikokuFlg(String chikokuFlg) {
        chikokuFlg__ = chikokuFlg;
    }
    /**
     * <p>soutaiFlg を取得します。
     * @return soutaiFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#soutaiFlg__
     */
    public String getSoutaiFlg() {
        return soutaiFlg__;
    }
    /**
     * <p>soutaiFlg をセットします。
     * @param soutaiFlg soutaiFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#soutaiFlg__
     */
    public void setSoutaiFlg(String soutaiFlg) {
        soutaiFlg__ = soutaiFlg;
    }
    /**
     * <p>holidayType を取得します。
     * @return holidayType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayType__
     */
    public String getHolidayType() {
        return holidayType__;
    }
    /**
     * <p>holidayType をセットします。
     * @param holidayType holidayType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayType__
     */
    public void setHolidayType(String holidayType) {
        holidayType__ = holidayType;
    }
    /**
     * <p>holidayNum を取得します。
     * @return holidayNum
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayNum__
     */
    public String getHolidayNum() {
        return holidayNum__;
    }
    /**
     * <p>holidayNum をセットします。
     * @param holidayNum holidayNum
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayNum__
     */
    public void setHolidayNum(String holidayNum) {
        holidayNum__ = holidayNum;
    }
    /**
     * <p>holidayText を取得します。
     * @return holidayText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayText__
     */
    public String getHolidayText() {
        return holidayText__;
    }
    /**
     * <p>holidayText をセットします。
     * @param holidayText holidayText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayText__
     */
    public void setHolidayText(String holidayText) {
        holidayText__ = holidayText;
    }
}