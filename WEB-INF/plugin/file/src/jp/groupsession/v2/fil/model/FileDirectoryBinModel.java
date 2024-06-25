package jp.groupsession.v2.fil.model;
import java.io.Serializable;

/**
 * <p>FILE_DIRECTORY_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDirectoryBinModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** FDR_VERSION mapping */
    private int fdrVersion__;
    /** BIN_SID mapping */
    private Long binSid__ = Long.valueOf(0);

    /**
     * <p>Default Constructor
     */
    public FileDirectoryBinModel() {
    }

    /**
     * <p>get FDR_SID value
     * @return FDR_SID value
     */
    public int getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>set FDR_SID value
     * @param fdrSid FDR_SID value
     */
    public void setFdrSid(int fdrSid) {
        fdrSid__ = fdrSid;
    }

    /**
     * <p>get FDR_VERSION value
     * @return FDR_VERSION value
     */
    public int getFdrVersion() {
        return fdrVersion__;
    }

    /**
     * <p>set FDR_VERSION value
     * @param fdrVersion FDR_VERSION value
     */
    public void setFdrVersion(int fdrVersion) {
        fdrVersion__ = fdrVersion;
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

}
