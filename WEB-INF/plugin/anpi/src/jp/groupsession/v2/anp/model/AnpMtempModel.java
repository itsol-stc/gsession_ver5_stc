package jp.groupsession.v2.anp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ANP_MTEMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpMtempModel implements Serializable {

    /** APM_SID mapping */
    private int apmSid__;
    /** APM_TITLE mapping */
    private String apmTitle__;
    /** APM_SUBJECT mapping */
    private String apmSubject__;
    /** APM_TEXT1 mapping */
    private String apmText1__;
    /** APM_TEXT2 mapping */
    private String apmText2__;
    /** APM_AUID mapping */
    private int apmAuid__;
    /** APM_ADATE mapping */
    private UDate apmAdate__;
    /** APM_EUID mapping */
    private int apmEuid__;
    /** APM_EDATE mapping */
    private UDate apmEdate__;

    /**
     * <p>Default Constructor
     */
    public AnpMtempModel() {
    }

    /**
     * <p>get APM_SID value
     * @return APM_SID value
     */
    public int getApmSid() {
        return apmSid__;
    }

    /**
     * <p>set APM_SID value
     * @param apmSid APM_SID value
     */
    public void setApmSid(int apmSid) {
        apmSid__ = apmSid;
    }

    /**
     * <p>get APM_TITLE value
     * @return APM_TITLE value
     */
    public String getApmTitle() {
        return apmTitle__;
    }

    /**
     * <p>set APM_TITLE value
     * @param apmTitle APM_TITLE value
     */
    public void setApmTitle(String apmTitle) {
        apmTitle__ = apmTitle;
    }

    /**
     * <p>get APM_SUBJECT value
     * @return APM_SUBJECT value
     */
    public String getApmSubject() {
        return apmSubject__;
    }

    /**
     * <p>set APM_SUBJECT value
     * @param apmSubject APM_SUBJECT value
     */
    public void setApmSubject(String apmSubject) {
        apmSubject__ = apmSubject;
    }

    /**
     * <p>get APM_TEXT1 value
     * @return APM_TEXT1 value
     */
    public String getApmText1() {
        return apmText1__;
    }

    /**
     * <p>set APM_TEXT1 value
     * @param apmText1 APM_TEXT1 value
     */
    public void setApmText1(String apmText1) {
        apmText1__ = apmText1;
    }

    /**
     * <p>get APM_TEXT2 value
     * @return APM_TEXT2 value
     */
    public String getApmText2() {
        return apmText2__;
    }

    /**
     * <p>set APM_TEXT2 value
     * @param apmText2 APM_TEXT2 value
     */
    public void setApmText2(String apmText2) {
        apmText2__ = apmText2;
    }

    /**
     * <p>get APM_AUID value
     * @return APM_AUID value
     */
    public int getApmAuid() {
        return apmAuid__;
    }

    /**
     * <p>set APM_AUID value
     * @param apmAuid APM_AUID value
     */
    public void setApmAuid(int apmAuid) {
        apmAuid__ = apmAuid;
    }

    /**
     * <p>get APM_ADATE value
     * @return APM_ADATE value
     */
    public UDate getApmAdate() {
        return apmAdate__;
    }

    /**
     * <p>set APM_ADATE value
     * @param apmAdate APM_ADATE value
     */
    public void setApmAdate(UDate apmAdate) {
        apmAdate__ = apmAdate;
    }

    /**
     * <p>get APM_EUID value
     * @return APM_EUID value
     */
    public int getApmEuid() {
        return apmEuid__;
    }

    /**
     * <p>set APM_EUID value
     * @param apmEuid APM_EUID value
     */
    public void setApmEuid(int apmEuid) {
        apmEuid__ = apmEuid;
    }

    /**
     * <p>get APM_EDATE value
     * @return APM_EDATE value
     */
    public UDate getApmEdate() {
        return apmEdate__;
    }

    /**
     * <p>set APM_EDATE value
     * @param apmEdate APM_EDATE value
     */
    public void setApmEdate(UDate apmEdate) {
        apmEdate__ = apmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(apmSid__);
        buf.append(",");
        buf.append(NullDefault.getString(apmTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(apmSubject__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(apmText1__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(apmText2__, ""));
        buf.append(",");
        buf.append(apmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apmAdate__, ""));
        buf.append(",");
        buf.append(apmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apmEdate__, ""));
        return buf.toString();
    }

}
