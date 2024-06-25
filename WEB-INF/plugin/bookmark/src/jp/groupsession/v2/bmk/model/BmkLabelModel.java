package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkLabelModel implements Serializable {

    /** BLB_SID mapping */
    private int blbSid__;
    /** BLB_KBN mapping */
    private int blbKbn__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** BLB_NAME mapping */
    private String blbName__;
    /** BLB_AUID mapping */
    private int blbAuid__;
    /** BLB_ADATE mapping */
    private UDate blbAdate__;
    /** BLB_EUID mapping */
    private int blbEuid__;
    /** BLB_EDATE mapping */
    private UDate blbEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkLabelModel() {
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
     * <p>get BLB_KBN value
     * @return BLB_KBN value
     */
    public int getBlbKbn() {
        return blbKbn__;
    }

    /**
     * <p>set BLB_KBN value
     * @param blbKbn BLB_KBN value
     */
    public void setBlbKbn(int blbKbn) {
        blbKbn__ = blbKbn;
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
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get BLB_NAME value
     * @return BLB_NAME value
     */
    public String getBlbName() {
        return blbName__;
    }

    /**
     * <p>set BLB_NAME value
     * @param blbName BLB_NAME value
     */
    public void setBlbName(String blbName) {
        blbName__ = blbName;
    }

    /**
     * <p>get BLB_AUID value
     * @return BLB_AUID value
     */
    public int getBlbAuid() {
        return blbAuid__;
    }

    /**
     * <p>set BLB_AUID value
     * @param blbAuid BLB_AUID value
     */
    public void setBlbAuid(int blbAuid) {
        blbAuid__ = blbAuid;
    }

    /**
     * <p>get BLB_ADATE value
     * @return BLB_ADATE value
     */
    public UDate getBlbAdate() {
        return blbAdate__;
    }

    /**
     * <p>set BLB_ADATE value
     * @param blbAdate BLB_ADATE value
     */
    public void setBlbAdate(UDate blbAdate) {
        blbAdate__ = blbAdate;
    }

    /**
     * <p>get BLB_EUID value
     * @return BLB_EUID value
     */
    public int getBlbEuid() {
        return blbEuid__;
    }

    /**
     * <p>set BLB_EUID value
     * @param blbEuid BLB_EUID value
     */
    public void setBlbEuid(int blbEuid) {
        blbEuid__ = blbEuid;
    }

    /**
     * <p>get BLB_EDATE value
     * @return BLB_EDATE value
     */
    public UDate getBlbEdate() {
        return blbEdate__;
    }

    /**
     * <p>set BLB_EDATE value
     * @param blbEdate BLB_EDATE value
     */
    public void setBlbEdate(UDate blbEdate) {
        blbEdate__ = blbEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(blbSid__);
        buf.append(",");
        buf.append(blbKbn__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(NullDefault.getString(blbName__, ""));
        buf.append(",");
        buf.append(blbAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(blbAdate__, ""));
        buf.append(",");
        buf.append(blbEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(blbEdate__, ""));
        return buf.toString();
    }

}
