package jp.groupsession.v2.tcd.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>TCD_YUKYUDATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdYukyudataModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** TCY_NENDO mapping */
    private int tcyNendo__;
    /** TCY_YUKYU mapping */
    private BigDecimal tcyYukyu__;
    /** TCY_AUID mapping */
    private int tcyAuid__;
    /** TCY_ADATE mapping */
    private UDate tcyAdate__;
    /** TCY_EUID mapping */
    private int tcyEuid__;
    /** TCY_EDATE mapping */
    private UDate tcyEdate__;

    /**
     * <p>Default Constructor
     */
    public TcdYukyudataModel() {
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
     * <p>get TCY_NENDO value
     * @return TCY_NENDO value
     */
    public int getTcyNendo() {
        return tcyNendo__;
    }

    /**
     * <p>set TCY_NENDO value
     * @param tcyNendo TCY_NENDO value
     */
    public void setTcyNendo(int tcyNendo) {
        tcyNendo__ = tcyNendo;
    }

    /**
     * <p>get TCY_YUKYU value
     * @return TCY_YUKYU value
     */
    public BigDecimal getTcyYukyu() {
        return tcyYukyu__;
    }

    /**
     * <p>set TCY_YUKYU value
     * @param tcyYukyu TCY_YUKYU value
     */
    public void setTcyYukyu(BigDecimal tcyYukyu) {
        tcyYukyu__ = tcyYukyu;
    }

    /**
     * <p>get TCY_AUID value
     * @return TCY_AUID value
     */
    public int getTcyAuid() {
        return tcyAuid__;
    }

    /**
     * <p>set TCY_AUID value
     * @param tcyAuid TCY_AUID value
     */
    public void setTcyAuid(int tcyAuid) {
        tcyAuid__ = tcyAuid;
    }

    /**
     * <p>get TCY_ADATE value
     * @return TCY_ADATE value
     */
    public UDate getTcyAdate() {
        return tcyAdate__;
    }

    /**
     * <p>set TCY_ADATE value
     * @param tcyAdate TCY_ADATE value
     */
    public void setTcyAdate(UDate tcyAdate) {
        tcyAdate__ = tcyAdate;
    }

    /**
     * <p>get TCY_EUID value
     * @return TCY_EUID value
     */
    public int getTcyEuid() {
        return tcyEuid__;
    }

    /**
     * <p>set TCY_EUID value
     * @param tcyEuid TCY_EUID value
     */
    public void setTcyEuid(int tcyEuid) {
        tcyEuid__ = tcyEuid;
    }

    /**
     * <p>get TCY_EDATE value
     * @return TCY_EDATE value
     */
    public UDate getTcyEdate() {
        return tcyEdate__;
    }

    /**
     * <p>set TCY_EDATE value
     * @param tcyEdate TCY_EDATE value
     */
    public void setTcyEdate(UDate tcyEdate) {
        tcyEdate__ = tcyEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(tcyNendo__);
        buf.append(",");
        buf.append(tcyYukyu__);
        buf.append(",");
        buf.append(tcyAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcyAdate__, ""));
        buf.append(",");
        buf.append(tcyEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcyEdate__, ""));
        return buf.toString();
    }

}
