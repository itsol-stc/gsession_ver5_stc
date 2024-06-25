package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_PUBLIC_EDIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkPublicEditModel implements Serializable {

    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** BPE_AUID mapping */
    private int bpeAuid__;
    /** BPE_ADATE mapping */
    private UDate bpeAdate__;
    /** BPE_EUID mapping */
    private int bpeEuid__;
    /** BPE_EDATE mapping */
    private UDate bpeEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkPublicEditModel() {
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
     * <p>get BPE_AUID value
     * @return BPE_AUID value
     */
    public int getBpeAuid() {
        return bpeAuid__;
    }

    /**
     * <p>set BPE_AUID value
     * @param bpeAuid BPE_AUID value
     */
    public void setBpeAuid(int bpeAuid) {
        bpeAuid__ = bpeAuid;
    }

    /**
     * <p>get BPE_ADATE value
     * @return BPE_ADATE value
     */
    public UDate getBpeAdate() {
        return bpeAdate__;
    }

    /**
     * <p>set BPE_ADATE value
     * @param bpeAdate BPE_ADATE value
     */
    public void setBpeAdate(UDate bpeAdate) {
        bpeAdate__ = bpeAdate;
    }

    /**
     * <p>get BPE_EUID value
     * @return BPE_EUID value
     */
    public int getBpeEuid() {
        return bpeEuid__;
    }

    /**
     * <p>set BPE_EUID value
     * @param bpeEuid BPE_EUID value
     */
    public void setBpeEuid(int bpeEuid) {
        bpeEuid__ = bpeEuid;
    }

    /**
     * <p>get BPE_EDATE value
     * @return BPE_EDATE value
     */
    public UDate getBpeEdate() {
        return bpeEdate__;
    }

    /**
     * <p>set BPE_EDATE value
     * @param bpeEdate BPE_EDATE value
     */
    public void setBpeEdate(UDate bpeEdate) {
        bpeEdate__ = bpeEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(bpeAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bpeAdate__, ""));
        buf.append(",");
        buf.append(bpeEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bpeEdate__, ""));
        return buf.toString();
    }

}
