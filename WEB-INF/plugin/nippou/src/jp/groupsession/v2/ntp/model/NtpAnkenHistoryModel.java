package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;

/**
 * <p>NTP_ANKEN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpAnkenHistoryModel extends NtpAnkenModel {

    /** NAH_SID mapping */
    private int nahSid__;
    /** NAN_MONTH mapping */
    private UDate nanMonth__;
    /**
     * <p>nahSid を取得します。
     * @return nahSid
     */
    public int getNahSid() {
        return nahSid__;
    }

    /**
     * <p>nahSid をセットします。
     * @param nahSid nahSid
     */
    public void setNahSid(int nahSid) {
        nahSid__ = nahSid;
    }

    /**
     * <p>nanMonth を取得します。
     * @return nanMonth
     */
    public UDate getNanMonth() {
        return nanMonth__;
    }

    /**
     * <p>nanMonth をセットします。
     * @param nanMonth nanMonth
     */
    public void setNanMonth(UDate nanMonth) {
        nanMonth__ = nanMonth;
    }
}
