package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_CABINET_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCabinetBinModel implements Serializable {

    /** FCB_SID mapping */
    private int fcbSid__;
    /** BIN_SID mapping */
    private Long binSid__ = Long.valueOf(0);

    /**
     * <p>Default Constructor
     */
    public FileCabinetBinModel() {
    }

    /**
     * <p>get FCB_SID value
     * @return FCB_SID value
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>set FCB_SID value
     * @param fcbSid FCB_SID value
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
