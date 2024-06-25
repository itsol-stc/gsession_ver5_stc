package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

/**
 * <p>RSV_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvBinModel implements Serializable {

    /** RSD_SID mapping */
    private int rsdSid__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);
    /** RSD_IMG_KBN mapping */
    private int rsdImgKbn__;
    /** RSD_DSP_KBN mapping */
    private int rsdDspKbn__;

    /**
     * <p>Default Constructor
     */
    public RsvBinModel() {
    }

    /**
     * <p>get RSD_SID value
     * @return RSD_SID value
     */
    public int getRsdSid() {
        return rsdSid__;
    }

    /**
     * <p>set RSD_SID value
     * @param rsdSid RSD_SID value
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
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
     * <p>get RSD_IMG_KBN value
     * @return RSD_IMG_KBN value
     */
    public int getRsdImgKbn() {
        return rsdImgKbn__;
    }

    /**
     * <p>set RSD_IMG_KBN value
     * @param rsdImgKbn RSD_IMG_KBN value
     */
    public void setRsdImgKbn(int rsdImgKbn) {
        rsdImgKbn__ = rsdImgKbn;
    }

    /**
     * <p>get RSD_DSP_KBN value
     * @return RSD_DSP_KBN value
     */
    public int getRsdDspKbn() {
        return rsdDspKbn__;
    }

    /**
     * <p>set RSD_DSP_KBN value
     * @param rsdDspKbn RSD_DSP_KBN value
     */
    public void setRsdDspKbn(int rsdDspKbn) {
        rsdDspKbn__ = rsdDspKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rsdSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(rsdImgKbn__);
        buf.append(",");
        buf.append(rsdDspKbn__);
        return buf.toString();
    }

}
