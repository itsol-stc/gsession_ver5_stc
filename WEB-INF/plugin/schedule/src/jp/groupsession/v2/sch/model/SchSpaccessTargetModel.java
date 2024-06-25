package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_SPACCESS_TARGET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchSpaccessTargetModel implements Serializable {

    /** SSA_SID mapping */
    private int ssaSid__;
    /** SST_TYPE mapping */
    private int sstType__;
    /** SST_TSID mapping */
    private int sstTsid__;

    /**
     * <p>Default Constructor
     */
    public SchSpaccessTargetModel() {
    }

    /**
     * <p>get SSA_SID value
     * @return SSA_SID value
     */
    public int getSsaSid() {
        return ssaSid__;
    }

    /**
     * <p>set SSA_SID value
     * @param ssaSid SSA_SID value
     */
    public void setSsaSid(int ssaSid) {
        ssaSid__ = ssaSid;
    }

    /**
     * <p>get SST_TYPE value
     * @return SST_TYPE value
     */
    public int getSstType() {
        return sstType__;
    }

    /**
     * <p>set SST_TYPE value
     * @param sstType SST_TYPE value
     */
    public void setSstType(int sstType) {
        sstType__ = sstType;
    }

    /**
     * <p>get SST_TSID value
     * @return SST_TSID value
     */
    public int getSstTsid() {
        return sstTsid__;
    }

    /**
     * <p>set SST_TSID value
     * @param sstTsid SST_TSID value
     */
    public void setSstTsid(int sstTsid) {
        sstTsid__ = sstTsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ssaSid__);
        buf.append(",");
        buf.append(sstType__);
        buf.append(",");
        buf.append(sstTsid__);
        return buf.toString();
    }

}
