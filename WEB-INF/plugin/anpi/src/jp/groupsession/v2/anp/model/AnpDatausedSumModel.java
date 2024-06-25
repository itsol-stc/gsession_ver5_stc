package jp.groupsession.v2.anp.model;

import java.io.Serializable;

/**
 * <p>ANP_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** ANP_HDATA_SIZE mapping */
    private long anpHdataSize__;
    /** ANP_JDATA_SIZE mapping */
    private long anpJdataSize__;
    /** ANP_MTEP_SIZE mapping */
    private long anpMtepSize__;

    /**
     * <p>Default Constructor
     */
    public AnpDatausedSumModel() {
    }

    /**
     * <p>get SUM_TYPE value
     * @return SUM_TYPE value
     */
    public int getSumType() {
        return sumType__;
    }

    /**
     * <p>set SUM_TYPE value
     * @param sumType SUM_TYPE value
     */
    public void setSumType(int sumType) {
        sumType__ = sumType;
    }

    /**
     * <p>get ANP_HDATA_SIZE value
     * @return ANP_HDATA_SIZE value
     */
    public long getAnpHdataSize() {
        return anpHdataSize__;
    }

    /**
     * <p>set ANP_HDATA_SIZE value
     * @param anpHdataSize ANP_HDATA_SIZE value
     */
    public void setAnpHdataSize(long anpHdataSize) {
        anpHdataSize__ = anpHdataSize;
    }

    /**
     * <p>get ANP_JDATA_SIZE value
     * @return ANP_JDATA_SIZE value
     */
    public long getAnpJdataSize() {
        return anpJdataSize__;
    }

    /**
     * <p>set ANP_JDATA_SIZE value
     * @param anpJdataSize ANP_JDATA_SIZE value
     */
    public void setAnpJdataSize(long anpJdataSize) {
        anpJdataSize__ = anpJdataSize;
    }

    /**
     * <p>get ANP_MTEP_SIZE value
     * @return ANP_MTEP_SIZE value
     */
    public long getAnpMtepSize() {
        return anpMtepSize__;
    }

    /**
     * <p>set ANP_MTEP_SIZE value
     * @param anpMtepSize ANP_MTEP_SIZE value
     */
    public void setAnpMtepSize(long anpMtepSize) {
        anpMtepSize__ = anpMtepSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(anpHdataSize__);
        buf.append(",");
        buf.append(anpJdataSize__);
        buf.append(",");
        buf.append(anpMtepSize__);
        return buf.toString();
    }

}
