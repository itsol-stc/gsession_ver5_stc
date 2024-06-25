package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** FILE_CABINET_SIZE mapping */
    private long fileCabinetSize__;

    /**
     * <p>Default Constructor
     */
    public FileDatausedSumModel() {
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
     * <p>get FILE_CABINET_SIZE value
     * @return FILE_CABINET_SIZE value
     */
    public long getFileCabinetSize() {
        return fileCabinetSize__;
    }

    /**
     * <p>set FILE_CABINET_SIZE value
     * @param fileCabinetSize FILE_CABINET_SIZE value
     */
    public void setFileCabinetSize(long fileCabinetSize) {
        fileCabinetSize__ = fileCabinetSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(fileCabinetSize__);
        return buf.toString();
    }

}
