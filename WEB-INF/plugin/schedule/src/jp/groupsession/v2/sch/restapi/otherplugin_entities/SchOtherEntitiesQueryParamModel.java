package jp.groupsession.v2.sch.restapi.otherplugin_entities;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
@ParamModel
public class SchOtherEntitiesQueryParamModel {
    /**ユーザID                                       */
    @NotBlank
    private String userId__;
    /**日付 FROM                                     */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate fromDate__;
    {
        fromDate__ = new UDate();
        fromDate__.setZeroDdHhMmSs();
    }
    /**日付 TO                                       */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate toDate__;
    {
        toDate__ = new UDate();
        toDate__.addYear(1);
        toDate__.setMaxHhMmSs();
    }
    /**
     * <p>usrerId を取得します。
     * @return usrerId
     * @see SchOtherEntitiesQueryParamModel#usrerId__
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>usrerId をセットします。
     * @param userId usrerId
     * @see SchOtherEntitiesQueryParamModel#usrerId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>fromDate を取得します。
     * @return fromDate
     * @see SchOtherEntitiesQueryParamModel#fromDate__
     */
    public UDate getFromDate() {
        return fromDate__;
    }
    /**
     * <p>fromDate をセットします。
     * @param fromDate fromDate
     * @see SchOtherEntitiesQueryParamModel#fromDate__
     */
    public void setFromDate(UDate fromDate) {
        fromDate__ = fromDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     * @see SchOtherEntitiesQueryParamModel#toDate__
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     * @see SchOtherEntitiesQueryParamModel#toDate__
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }

}
