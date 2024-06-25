package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_BELONG_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkBelongLabelModel implements Serializable {

    /** BMK_SID mapping */
    private int bmkSid__;
    /** BLB_SID mapping */
    private int blbSid__;
    /** BBL_AUID mapping */
    private int bblAuid__;
    /** BBL_ADATE mapping */
    private UDate bblAdate__;
    /** BBL_EUID mapping */
    private int bblEuid__;
    /** BBL_EDATE mapping */
    private UDate bblEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkBelongLabelModel() {
    }

    /**
     * <p>get BMK_SID value
     * @return BMK_SID value
     */
    public int getBmkSid() {
        return bmkSid__;
    }

    /**
     * <p>set BMK_SID value
     * @param bmkSid BMK_SID value
     */
    public void setBmkSid(int bmkSid) {
        bmkSid__ = bmkSid;
    }

    /**
     * <p>get BLB_SID value
     * @return BLB_SID value
     */
    public int getBlbSid() {
        return blbSid__;
    }

    /**
     * <p>set BLB_SID value
     * @param blbSid BLB_SID value
     */
    public void setBlbSid(int blbSid) {
        blbSid__ = blbSid;
    }

    /**
     * <p>get BBL_AUID value
     * @return BBL_AUID value
     */
    public int getBblAuid() {
        return bblAuid__;
    }

    /**
     * <p>set BBL_AUID value
     * @param bblAuid BBL_AUID value
     */
    public void setBblAuid(int bblAuid) {
        bblAuid__ = bblAuid;
    }

    /**
     * <p>get BBL_ADATE value
     * @return BBL_ADATE value
     */
    public UDate getBblAdate() {
        return bblAdate__;
    }

    /**
     * <p>set BBL_ADATE value
     * @param bblAdate BBL_ADATE value
     */
    public void setBblAdate(UDate bblAdate) {
        bblAdate__ = bblAdate;
    }

    /**
     * <p>get BBL_EUID value
     * @return BBL_EUID value
     */
    public int getBblEuid() {
        return bblEuid__;
    }

    /**
     * <p>set BBL_EUID value
     * @param bblEuid BBL_EUID value
     */
    public void setBblEuid(int bblEuid) {
        bblEuid__ = bblEuid;
    }

    /**
     * <p>get BBL_EDATE value
     * @return BBL_EDATE value
     */
    public UDate getBblEdate() {
        return bblEdate__;
    }

    /**
     * <p>set BBL_EDATE value
     * @param bblEdate BBL_EDATE value
     */
    public void setBblEdate(UDate bblEdate) {
        bblEdate__ = bblEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(bmkSid__);
        buf.append(",");
        buf.append(blbSid__);
        buf.append(",");
        buf.append(bblAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bblAdate__, ""));
        buf.append(",");
        buf.append(bblEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bblEdate__, ""));
        return buf.toString();
    }

}
