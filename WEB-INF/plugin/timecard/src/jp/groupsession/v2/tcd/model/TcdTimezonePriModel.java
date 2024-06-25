package jp.groupsession.v2.tcd.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>TCD_TIMEZONE_PRI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdTimezonePriModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** TTI_SID mapping */
    private int ttiSid__;
    /** TTP_DEFAULT mapping */
    private int ttpDefault__;
    /** TTI_AUID mapping */
    private int ttpAuid__;
    /** TTI_ADATE mapping */
    private UDate ttpAdate__;
    /** TTI_NAME mapping */
    private String ttiName__;
    /** TTI_RYAKU mapping */
    private String ttiRyaku__;

    /**
     * <p>Default Constructor
     */
    public TcdTimezonePriModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get TTI_SID value
     * @return TTI_SID value
     */
    public int getTtiSid() {
        return ttiSid__;
    }

    /**
     * <p>set TTI_SID value
     * @param ttiSid TTI_SID value
     */
    public void setTtiSid(int ttiSid) {
        ttiSid__ = ttiSid;
    }

    /**
     * <p>get TTP_DEFAULT value
     * @return TTP_DEFAULT value
     */
    public int getTtpDefault() {
        return ttpDefault__;
    }

    /**
     * <p>set TTP_DEFAULT value
     * @param ttpDefault TTP_DEFAULT value
     */
    public void setTtpDefault(int ttpDefault) {
        ttpDefault__ = ttpDefault;
    }

    /**
     * <p>ttpAuid を取得します。
     * @return ttpAuid
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttpAuid__
     */
    public int getTtpAuid() {
        return ttpAuid__;
    }

    /**
     * <p>ttpAuid をセットします。
     * @param ttpAuid ttpAuid
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttpAuid__
     */
    public void setTtpAuid(int ttpAuid) {
        ttpAuid__ = ttpAuid;
    }

    /**
     * <p>ttpAdate を取得します。
     * @return ttpAdate
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttpAdate__
     */
    public UDate getTtpAdate() {
        return ttpAdate__;
    }

    /**
     * <p>ttpAdate をセットします。
     * @param ttpAdate ttpAdate
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttpAdate__
     */
    public void setTtpAdate(UDate ttpAdate) {
        ttpAdate__ = ttpAdate;
    }

    /**
     * <p>ttiName を取得します。
     * @return ttiName
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttiName__
     */
    public String getTtiName() {
        return ttiName__;
    }

    /**
     * <p>ttiName をセットします。
     * @param ttiName ttiName
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttiName__
     */
    public void setTtiName(String ttiName) {
        ttiName__ = ttiName;
    }

    /**
     * <p>ttiRyaku を取得します。
     * @return ttiRyaku
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttiRyaku__
     */
    public String getTtiRyaku() {
        return ttiRyaku__;
    }

    /**
     * <p>ttiRyaku をセットします。
     * @param ttiRyaku ttiRyaku
     * @see jp.groupsession.v2.tcd.model.TcdTimezonePriModel#ttiRyaku__
     */
    public void setTtiRyaku(String ttiRyaku) {
        ttiRyaku__ = ttiRyaku;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ttiSid__);
        buf.append(",");
        buf.append(ttpDefault__);
        buf.append(",");
        buf.append(ttpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ttpAdate__, ""));
        return buf.toString();
    }

}
