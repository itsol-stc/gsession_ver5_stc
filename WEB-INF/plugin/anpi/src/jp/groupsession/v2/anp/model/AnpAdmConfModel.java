package jp.groupsession.v2.anp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ANP_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpAdmConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** APA_AUID mapping */
    private int apaAuid__;
    /** APA_ADATE mapping */
    private UDate apaAdate__;
    /** APA_EUID mapping */
    private int apaEuid__;
    /** APA_EDATE mapping */
    private UDate apaEdate__;

    /**
     * <p>Default Constructor
     */
    public AnpAdmConfModel() {
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
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get APA_AUID value
     * @return APA_AUID value
     */
    public int getApaAuid() {
        return apaAuid__;
    }

    /**
     * <p>set APA_AUID value
     * @param apaAuid APA_AUID value
     */
    public void setApaAuid(int apaAuid) {
        apaAuid__ = apaAuid;
    }

    /**
     * <p>get APA_ADATE value
     * @return APA_ADATE value
     */
    public UDate getApaAdate() {
        return apaAdate__;
    }

    /**
     * <p>set APA_ADATE value
     * @param apaAdate APA_ADATE value
     */
    public void setApaAdate(UDate apaAdate) {
        apaAdate__ = apaAdate;
    }

    /**
     * <p>get APA_EUID value
     * @return APA_EUID value
     */
    public int getApaEuid() {
        return apaEuid__;
    }

    /**
     * <p>set APA_EUID value
     * @param apaEuid APA_EUID value
     */
    public void setApaEuid(int apaEuid) {
        apaEuid__ = apaEuid;
    }

    /**
     * <p>get APA_EDATE value
     * @return APA_EDATE value
     */
    public UDate getApaEdate() {
        return apaEdate__;
    }

    /**
     * <p>set APA_EDATE value
     * @param apaEdate APA_EDATE value
     */
    public void setApaEdate(UDate apaEdate) {
        apaEdate__ = apaEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(apaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apaAdate__, ""));
        buf.append(",");
        buf.append(apaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apaEdate__, ""));
        return buf.toString();
    }

}
