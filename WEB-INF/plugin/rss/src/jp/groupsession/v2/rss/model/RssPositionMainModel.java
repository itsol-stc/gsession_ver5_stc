package jp.groupsession.v2.rss.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RSS_POSITION_MAIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssPositionMainModel implements Serializable {

    /** RSS_SID mapping */
    private int rssSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RPM_POSITION mapping */
    private int rpmPosition__;
    /** RPM_ORDER mapping */
    private int rpmOrder__;
    /** RPM_AUID mapping */
    private int rpmAuid__;
    /** RPM_ADATE mapping */
    private UDate rpmAdate__;
    /** RPM_EUID mapping */
    private int rpmEuid__;
    /** RPM_EDATE mapping */
    private UDate rpmEdate__;

    /**
     * <p>Default Constructor
     */
    public RssPositionMainModel() {
    }

    /**
     * <p>get RSS_SID value
     * @return RSS_SID value
     */
    public int getRssSid() {
        return rssSid__;
    }

    /**
     * <p>set RSS_SID value
     * @param rssSid RSS_SID value
     */
    public void setRssSid(int rssSid) {
        rssSid__ = rssSid;
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
     * <p>get RPM_POSITION value
     * @return RPM_POSITION value
     */
    public int getRpmPosition() {
        return rpmPosition__;
    }

    /**
     * <p>set RPM_POSITION value
     * @param rpmPosition RPM_POSITION value
     */
    public void setRpmPosition(int rpmPosition) {
        rpmPosition__ = rpmPosition;
    }

    /**
     * <p>get RPM_ORDER value
     * @return RPM_ORDER value
     */
    public int getRpmOrder() {
        return rpmOrder__;
    }

    /**
     * <p>set RPM_ORDER value
     * @param rpmOrder RPM_ORDER value
     */
    public void setRpmOrder(int rpmOrder) {
        rpmOrder__ = rpmOrder;
    }

    /**
     * <p>get RPM_AUID value
     * @return RPM_AUID value
     */
    public int getRpmAuid() {
        return rpmAuid__;
    }

    /**
     * <p>set RPM_AUID value
     * @param rpmAuid RPM_AUID value
     */
    public void setRpmAuid(int rpmAuid) {
        rpmAuid__ = rpmAuid;
    }

    /**
     * <p>get RPM_ADATE value
     * @return RPM_ADATE value
     */
    public UDate getRpmAdate() {
        return rpmAdate__;
    }

    /**
     * <p>set RPM_ADATE value
     * @param rpmAdate RPM_ADATE value
     */
    public void setRpmAdate(UDate rpmAdate) {
        rpmAdate__ = rpmAdate;
    }

    /**
     * <p>get RPM_EUID value
     * @return RPM_EUID value
     */
    public int getRpmEuid() {
        return rpmEuid__;
    }

    /**
     * <p>set RPM_EUID value
     * @param rpmEuid RPM_EUID value
     */
    public void setRpmEuid(int rpmEuid) {
        rpmEuid__ = rpmEuid;
    }

    /**
     * <p>get RPM_EDATE value
     * @return RPM_EDATE value
     */
    public UDate getRpmEdate() {
        return rpmEdate__;
    }

    /**
     * <p>set RPM_EDATE value
     * @param rpmEdate RPM_EDATE value
     */
    public void setRpmEdate(UDate rpmEdate) {
        rpmEdate__ = rpmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rssSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rpmPosition__);
        buf.append(",");
        buf.append(rpmOrder__);
        buf.append(",");
        buf.append(rpmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rpmAdate__, ""));
        buf.append(",");
        buf.append(rpmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rpmEdate__, ""));
        return buf.toString();
    }

}
