package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>FILE_MONEY_MASTER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileMoneyMasterModel implements Serializable {

    /** FMM_SID mapping */
    private int fmmSid__;
    /** FMM_NAME mapping */
    private String fmmName__;
    /** FMM_SORT mapping */
    private int fmmSort__;

    /**
     * <p>Default Constructor
     */
    public FileMoneyMasterModel() {
    }

    /**
     * <p>get FMM_SID value
     * @return FMM_SID value
     */
    public int getFmmSid() {
        return fmmSid__;
    }

    /**
     * <p>set FMM_SID value
     * @param fmmSid FMM_SID value
     */
    public void setFmmSid(int fmmSid) {
        fmmSid__ = fmmSid;
    }

    /**
     * <p>get FMM_NAME value
     * @return FMM_NAME value
     */
    public String getFmmName() {
        return fmmName__;
    }

    /**
     * <p>set FMM_NAME value
     * @param fmmName FMM_NAME value
     */
    public void setFmmName(String fmmName) {
        fmmName__ = fmmName;
    }

    /**
     * <p>get FMM_SORT value
     * @return FMM_SORT value
     */
    public int getFmmSort() {
        return fmmSort__;
    }

    /**
     * <p>set FMM_SORT value
     * @param fmmSort FMM_SORT value
     */
    public void setFmmSort(int fmmSort) {
        fmmSort__ = fmmSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(fmmSid__);
        buf.append(",");
        buf.append(NullDefault.getString(fmmName__, ""));
        buf.append(",");
        buf.append(fmmSort__);
        return buf.toString();
    }

}
