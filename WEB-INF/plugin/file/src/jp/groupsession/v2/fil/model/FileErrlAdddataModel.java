package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_ERRL_ADDDATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileErrlAdddataModel implements Serializable {

    /** FEA_SID mapping */
    private int feaSid__;
    /** BIN_SID mapping */
    private long binSid__;
    /** FFL_EXT mapping */
    private String fflExt__;
    /** FFL_FILE_SIZE mapping */
    private long fflFileSize__;
    /** FCB_SID mapping */
    private int fcbSid__;
    /** FDR_PARENT_SID mapping */
    private int fdrParentSid__;
    /** FDR_NAME mapping */
    private String fdrName__;
    /** FDR_BIKO mapping */
    private String fdrBiko__;
    /** FFR_UP_CMT mapping */
    private String ffrUpCmt__;
    /** FDR_AUID mapping */
    private int fdrAuid__;
    /** FDR_ADATE mapping */
    private UDate fdrAdate__;
    /** FEA_DEFGRP_NAME mapping */
    private String feaDefgrpName__;

    /**
     * <p>Default Constructor
     */
    public FileErrlAdddataModel() {
    }

    /**
     * <p>get FEA_SID value
     * @return FEA_SID value
     */
    public int getFeaSid() {
        return feaSid__;
    }

    /**
     * <p>set FEA_SID value
     * @param feaSid FEA_SID value
     */
    public void setFeaSid(int feaSid) {
        feaSid__ = feaSid;
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
     * <p>get FFL_EXT value
     * @return FFL_EXT value
     */
    public String getFflExt() {
        return fflExt__;
    }

    /**
     * <p>set FFL_EXT value
     * @param fflExt FFL_EXT value
     */
    public void setFflExt(String fflExt) {
        fflExt__ = fflExt;
    }

    /**
     * <p>get FFL_FILE_SIZE value
     * @return FFL_FILE_SIZE value
     */
    public long getFflFileSize() {
        return fflFileSize__;
    }

    /**
     * <p>set FFL_FILE_SIZE value
     * @param fflFileSize FFL_FILE_SIZE value
     */
    public void setFflFileSize(long fflFileSize) {
        fflFileSize__ = fflFileSize;
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
     * <p>get FDR_PARENT_SID value
     * @return FDR_PARENT_SID value
     */
    public int getFdrParentSid() {
        return fdrParentSid__;
    }

    /**
     * <p>set FDR_PARENT_SID value
     * @param fdrParentSid FDR_PARENT_SID value
     */
    public void setFdrParentSid(int fdrParentSid) {
        fdrParentSid__ = fdrParentSid;
    }

    /**
     * <p>get FDR_NAME value
     * @return FDR_NAME value
     */
    public String getFdrName() {
        return fdrName__;
    }

    /**
     * <p>set FDR_NAME value
     * @param fdrName FDR_NAME value
     */
    public void setFdrName(String fdrName) {
        fdrName__ = fdrName;
    }

    /**
     * <p>get FDR_BIKO value
     * @return FDR_BIKO value
     */
    public String getFdrBiko() {
        return fdrBiko__;
    }

    /**
     * <p>set FDR_BIKO value
     * @param fdrBiko FDR_BIKO value
     */
    public void setFdrBiko(String fdrBiko) {
        fdrBiko__ = fdrBiko;
    }

    /**
     * <p>get FFR_UP_CMT value
     * @return FFR_UP_CMT value
     */
    public String getFfrUpCmt() {
        return ffrUpCmt__;
    }

    /**
     * <p>set FFR_UP_CMT value
     * @param ffrUpCmt FFR_UP_CMT value
     */
    public void setFfrUpCmt(String ffrUpCmt) {
        ffrUpCmt__ = ffrUpCmt;
    }

    /**
     * <p>get FDR_AUID value
     * @return FDR_AUID value
     */
    public int getFdrAuid() {
        return fdrAuid__;
    }

    /**
     * <p>set FDR_AUID value
     * @param fdrAuid FDR_AUID value
     */
    public void setFdrAuid(int fdrAuid) {
        fdrAuid__ = fdrAuid;
    }

    /**
     * <p>get FDR_ADATE value
     * @return FDR_ADATE value
     */
    public UDate getFdrAdate() {
        return fdrAdate__;
    }

    /**
     * <p>set FDR_ADATE value
     * @param fdrAdate FDR_ADATE value
     */
    public void setFdrAdate(UDate fdrAdate) {
        fdrAdate__ = fdrAdate;
    }

    /**
     * <p>get FEA_DEFGRP_NAME value
     * @return FEA_DEFGRP_NAME value
     */
    public String getFeaDefgrpName() {
        return feaDefgrpName__;
    }

    /**
     * <p>set FEA_DEFGRP_NAME value
     * @param feaDefgrpName FEA_DEFGRP_NAME value
     */
    public void setFeaDefgrpName(String feaDefgrpName) {
        feaDefgrpName__ = feaDefgrpName;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(feaSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(NullDefault.getString(fflExt__, ""));
        buf.append(",");
        buf.append(fflFileSize__);
        buf.append(",");
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(fdrParentSid__);
        buf.append(",");
        buf.append(NullDefault.getString(fdrName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(fdrBiko__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ffrUpCmt__, ""));
        buf.append(",");
        buf.append(fdrAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fdrAdate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(feaDefgrpName__, ""));
        return buf.toString();
    }

}
