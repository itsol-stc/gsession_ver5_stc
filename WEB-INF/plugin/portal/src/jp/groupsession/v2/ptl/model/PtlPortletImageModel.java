package jp.groupsession.v2.ptl.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>PTL_PORTLET_IMAGE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletImageModel implements Serializable {

    /** PLT_SID mapping */
    private int pltSid__;
    /** PLI_SID mapping */
    private long pliSid__;
    /** BIN_SID mapping */
    private long binSid__;
    /** PLI_NAME mapping */
    private String pliName__;


    /** BIN_FILE_NAME mapping */
    private String binFileName__;
    /** BIN_FILE_PATH mapping */
    private String binFilePath__;
    /** BIN_FILE_EXTENSION mapping */
    private String binFileExtension__;
    /** BIN_FILE_SIZE mapping */
    private long binFileSize__;

    /**
     * <p>Default Constructor
     */
    public PtlPortletImageModel() {
    }

    /**
     * <p>get PLT_SID value
     * @return PLT_SID value
     */
    public int getPltSid() {
        return pltSid__;
    }

    /**
     * <p>set PLT_SID value
     * @param pltSid PLT_SID value
     */
    public void setPltSid(int pltSid) {
        pltSid__ = pltSid;
    }

    /**
     * <p>get PLI_SID value
     * @return PLI_SID value
     */
    public long getPliSid() {
        return pliSid__;
    }

    /**
     * <p>set PLI_SID value
     * @param pliSid PLI_SID value
     */
    public void setPliSid(long pliSid) {
        pliSid__ = pliSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get PLI_NAME value
     * @return PLI_NAME value
     */
    public String getPliName() {
        return pliName__;
    }

    /**
     * <p>set PLI_NAME value
     * @param pliName PLI_NAME value
     */
    public void setPliName(String pliName) {
        pliName__ = pliName;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(pltSid__);
        buf.append(",");
        buf.append(pliSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(NullDefault.getString(pliName__, ""));
        return buf.toString();
    }

    /**
     * <p>binFileName を取得します。
     * @return binFileName
     */
    public String getBinFileName() {
        return binFileName__;
    }

    /**
     * <p>binFileName をセットします。
     * @param binFileName binFileName
     */
    public void setBinFileName(String binFileName) {
        binFileName__ = binFileName;
    }

    /**
     * <p>binFilePath を取得します。
     * @return binFilePath
     */
    public String getBinFilePath() {
        return binFilePath__;
    }

    /**
     * <p>binFilePath をセットします。
     * @param binFilePath binFilePath
     */
    public void setBinFilePath(String binFilePath) {
        binFilePath__ = binFilePath;
    }

    /**
     * <p>binFileExtension を取得します。
     * @return binFileExtension
     */
    public String getBinFileExtension() {
        return binFileExtension__;
    }

    /**
     * <p>binFileExtension をセットします。
     * @param binFileExtension binFileExtension
     */
    public void setBinFileExtension(String binFileExtension) {
        binFileExtension__ = binFileExtension;
    }

    /**
     * <p>binFileSize を取得します。
     * @return binFileSize
     */
    public long getBinFileSize() {
        return binFileSize__;
    }

    /**
     * <p>binFileSize をセットします。
     * @param binFileSize binFileSize
     */
    public void setBinFileSize(long binFileSize) {
        binFileSize__ = binFileSize;
    }

}
