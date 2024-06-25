package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_GROUP_EDIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkGroupEditModel implements Serializable {

    /** GRP_SID mapping */
    private int grpSid__;
    /** BGE_GRP_SID mapping */
    private int bgeGrpSid__;
    /** BGE_USR_SID mapping */
    private int bgeUsrSid__;
    /** BGE_AUID mapping */
    private int bgeAuid__;
    /** BGE_ADATE mapping */
    private UDate bgeAdate__;
    /** BGE_EUID mapping */
    private int bgeEuid__;
    /** BGE_EDATE mapping */
    private UDate bgeEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkGroupEditModel() {
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
     * <p>get BGE_GRP_SID value
     * @return BGE_GRP_SID value
     */
    public int getBgeGrpSid() {
        return bgeGrpSid__;
    }

    /**
     * <p>set BGE_GRP_SID value
     * @param bgeGrpSid BGE_GRP_SID value
     */
    public void setBgeGrpSid(int bgeGrpSid) {
        bgeGrpSid__ = bgeGrpSid;
    }

    /**
     * <p>get BGE_USR_SID value
     * @return BGE_USR_SID value
     */
    public int getBgeUsrSid() {
        return bgeUsrSid__;
    }

    /**
     * <p>set BGE_USR_SID value
     * @param bgeUsrSid BGE_USR_SID value
     */
    public void setBgeUsrSid(int bgeUsrSid) {
        bgeUsrSid__ = bgeUsrSid;
    }

    /**
     * <p>get BGE_AUID value
     * @return BGE_AUID value
     */
    public int getBgeAuid() {
        return bgeAuid__;
    }

    /**
     * <p>set BGE_AUID value
     * @param bgeAuid BGE_AUID value
     */
    public void setBgeAuid(int bgeAuid) {
        bgeAuid__ = bgeAuid;
    }

    /**
     * <p>get BGE_ADATE value
     * @return BGE_ADATE value
     */
    public UDate getBgeAdate() {
        return bgeAdate__;
    }

    /**
     * <p>set BGE_ADATE value
     * @param bgeAdate BGE_ADATE value
     */
    public void setBgeAdate(UDate bgeAdate) {
        bgeAdate__ = bgeAdate;
    }

    /**
     * <p>get BGE_EUID value
     * @return BGE_EUID value
     */
    public int getBgeEuid() {
        return bgeEuid__;
    }

    /**
     * <p>set BGE_EUID value
     * @param bgeEuid BGE_EUID value
     */
    public void setBgeEuid(int bgeEuid) {
        bgeEuid__ = bgeEuid;
    }

    /**
     * <p>get BGE_EDATE value
     * @return BGE_EDATE value
     */
    public UDate getBgeEdate() {
        return bgeEdate__;
    }

    /**
     * <p>set BGE_EDATE value
     * @param bgeEdate BGE_EDATE value
     */
    public void setBgeEdate(UDate bgeEdate) {
        bgeEdate__ = bgeEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(grpSid__);
        buf.append(",");
        buf.append(bgeGrpSid__);
        buf.append(",");
        buf.append(bgeUsrSid__);
        buf.append(",");
        buf.append(bgeAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bgeAdate__, ""));
        buf.append(",");
        buf.append(bgeEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bgeEdate__, ""));
        return buf.toString();
    }

}
