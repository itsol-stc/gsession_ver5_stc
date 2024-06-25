package jp.groupsession.v2.rss.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RSS_POSITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssPositionModel implements Serializable {

    /** RSS_SID mapping */
    private int rssSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RSP_POSITION mapping */
    private int rspPosition__;
    /** RSP_ORDER mapping */
    private int rspOrder__;
    /** RSP_AUID mapping */
    private int rspAuid__;
    /** RSP_ADATE mapping */
    private UDate rspAdate__;
    /** RSP_EUID mapping */
    private int rspEuid__;
    /** RSP_EDATE mapping */
    private UDate rspEdate__;

    /**
     * <p>Default Constructor
     */
    public RssPositionModel() {
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
     * <p>get RSP_POSITION value
     * @return RSP_POSITION value
     */
    public int getRspPosition() {
        return rspPosition__;
    }

    /**
     * <p>set RSP_POSITION value
     * @param rspPosition RSP_POSITION value
     */
    public void setRspPosition(int rspPosition) {
        rspPosition__ = rspPosition;
    }

    /**
     * <p>get RSP_ORDER value
     * @return RSP_ORDER value
     */
    public int getRspOrder() {
        return rspOrder__;
    }

    /**
     * <p>set RSP_ORDER value
     * @param rspOrder RSP_ORDER value
     */
    public void setRspOrder(int rspOrder) {
        rspOrder__ = rspOrder;
    }

    /**
     * <p>get RSP_AUID value
     * @return RSP_AUID value
     */
    public int getRspAuid() {
        return rspAuid__;
    }

    /**
     * <p>set RSP_AUID value
     * @param rspAuid RSP_AUID value
     */
    public void setRspAuid(int rspAuid) {
        rspAuid__ = rspAuid;
    }

    /**
     * <p>get RSP_ADATE value
     * @return RSP_ADATE value
     */
    public UDate getRspAdate() {
        return rspAdate__;
    }

    /**
     * <p>set RSP_ADATE value
     * @param rspAdate RSP_ADATE value
     */
    public void setRspAdate(UDate rspAdate) {
        rspAdate__ = rspAdate;
    }

    /**
     * <p>get RSP_EUID value
     * @return RSP_EUID value
     */
    public int getRspEuid() {
        return rspEuid__;
    }

    /**
     * <p>set RSP_EUID value
     * @param rspEuid RSP_EUID value
     */
    public void setRspEuid(int rspEuid) {
        rspEuid__ = rspEuid;
    }

    /**
     * <p>get RSP_EDATE value
     * @return RSP_EDATE value
     */
    public UDate getRspEdate() {
        return rspEdate__;
    }

    /**
     * <p>set RSP_EDATE value
     * @param rspEdate RSP_EDATE value
     */
    public void setRspEdate(UDate rspEdate) {
        rspEdate__ = rspEdate;
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
        buf.append(rspPosition__);
        buf.append(",");
        buf.append(rspOrder__);
        buf.append(",");
        buf.append(rspAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rspAdate__, ""));
        buf.append(",");
        buf.append(rspEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rspEdate__, ""));
        return buf.toString();
    }

}
