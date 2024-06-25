package jp.groupsession.v2.prj.model;

import java.io.Serializable;

/**
 * <p>PRJ_FILE_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjFileBinModel implements Serializable {

    /** PDR_SID mapping */
    private int pdrSid__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);
    /** PFL_EXT mapping */
    private String pflExt__;
    /** PFL_FILE_SIZE mapping */
    private long pflFileSize__;

    /**
     * <p>Default Constructor
     */
    public PrjFileBinModel() {
    }

    /**
     * <p>get PDR_SID value
     * @return PDR_SID value
     */
    public int getPdrSid() {
        return pdrSid__;
    }

    /**
     * <p>set PDR_SID value
     * @param pdrSid PDR_SID value
     */
    public void setPdrSid(int pdrSid) {
        pdrSid__ = pdrSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get PFL_EXT value
     * @return PFL_EXT value
     */
    public String getPflExt() {
        return pflExt__;
    }

    /**
     * <p>set PFL_EXT value
     * @param pflExt PFL_EXT value
     */
    public void setPflExt(String pflExt) {
        pflExt__ = pflExt;
    }

    /**
     * <p>get PFL_FILE_SIZE value
     * @return PFL_FILE_SIZE value
     */
    public long getPflFileSize() {
        return pflFileSize__;
    }

    /**
     * <p>set PFL_FILE_SIZE value
     * @param pflFileSize PFL_FILE_SIZE value
     */
    public void setPflFileSize(long pflFileSize) {
        pflFileSize__ = pflFileSize;
    }
}