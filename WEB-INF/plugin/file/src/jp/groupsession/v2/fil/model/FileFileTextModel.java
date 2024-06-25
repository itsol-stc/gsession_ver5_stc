package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>FILE_FILE_TEXT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileFileTextModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** FDR_VERSION mapping */
    private int fdrVersion__;
    /** FCB_SID mapping */
    private int fcbSid__;
    /** FFT_SEC_NO mapping */
    private int fftSecNo__ = 0;
    /** FFT_TEXT mapping */
    private String fftText__;
    /** FFT_BIKO mapping */
    private String fftBiko__;
    /** FFT_READ_KBN mapping */
    private int fftReadKbn__;

    /**
     * <p>Default Constructor
     */
    public FileFileTextModel() {
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
     * <P>get FFT_SEC_NO value
     * @return FFT_SEC_NO value
     */
    public int getFftSecNo() {
        return fftSecNo__;
    }

    /**
     * <p>set FFT_SEC_NO value
     * @param fftSecNo FFT_SEC_NO value
     */
    public void setFftSecNo(int fftSecNo) {
        fftSecNo__ = fftSecNo;
    }

    /**
     * <p>get FFT_TEXT value
     * @return FFT_TEXT value
     */
    public String getFftText() {
        return fftText__;
    }

    /**
     * <p>set FFT_TEXT value
     * @param fftText FFT_TEXT value
     */
    public void setFftText(String fftText) {
        fftText__ = fftText;
    }

    /**
     * <p>get FFT_BIKO value
     * @return FFT_BIKO value
     */
    public String getFftBiko() {
        return fftBiko__;
    }

    /**
     * <p>set FFT_BIKO value
     * @param fftBiko FFT_BIKO value
     */
    public void setFftBiko(String fftBiko) {
        fftBiko__ = fftBiko;
    }

    /**
     * <p>get FFT_READ_KBN value
     * @return FFT_READ_KBN value
     */
    public int getFftReadKbn() {
        return fftReadKbn__;
    }

    /**
     * <p>set FFT_READ_KBN value
     * @param fftReadKbn FFT_READ_KBN value
     */
    public void setFftReadKbn(int fftReadKbn) {
        fftReadKbn__ = fftReadKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fdrSid__);
        buf.append(",");
        buf.append(fftSecNo__);
        buf.append(",");
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(NullDefault.getString(fftText__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(fftBiko__, ""));
        buf.append(",");
        buf.append(fftReadKbn__);
        return buf.toString();
    }

}
