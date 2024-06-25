package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_BIN_TEMPU Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpBinTempuModel implements Serializable {

    /** NBT_SID mapping */
    private int nbtSid__;
    /** NIP_SID mapping */
    private int nipSid__;
    /** BIN_SID mapping */
    private int binSid__;
    /** NBT_AUID mapping */
    private int nbtAuid__;
    /** NBT_ADATE mapping */
    private UDate nbtAdate__;
    /** NBT_EUID mapping */
    private int nbtEuid__;
    /** NBT_EDATE mapping */
    private UDate nbtEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpBinTempuModel() {
    }

    /**
     * <p>get NBT_SID value
     * @return NBT_SID value
     */
    public int getNbtSid() {
        return nbtSid__;
    }

    /**
     * <p>set NBT_SID value
     * @param nbtSid NBT_SID value
     */
    public void setNbtSid(int nbtSid) {
        nbtSid__ = nbtSid;
    }

    /**
     * <p>get NIP_SID value
     * @return NIP_SID value
     */
    public int getNipSid() {
        return nipSid__;
    }

    /**
     * <p>set NIP_SID value
     * @param nipSid NIP_SID value
     */
    public void setNipSid(int nipSid) {
        nipSid__ = nipSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public int getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(int binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get NBT_AUID value
     * @return NBT_AUID value
     */
    public int getNbtAuid() {
        return nbtAuid__;
    }

    /**
     * <p>set NBT_AUID value
     * @param nbtAuid NBT_AUID value
     */
    public void setNbtAuid(int nbtAuid) {
        nbtAuid__ = nbtAuid;
    }

    /**
     * <p>get NBT_ADATE value
     * @return NBT_ADATE value
     */
    public UDate getNbtAdate() {
        return nbtAdate__;
    }

    /**
     * <p>set NBT_ADATE value
     * @param nbtAdate NBT_ADATE value
     */
    public void setNbtAdate(UDate nbtAdate) {
        nbtAdate__ = nbtAdate;
    }

    /**
     * <p>get NBT_EUID value
     * @return NBT_EUID value
     */
    public int getNbtEuid() {
        return nbtEuid__;
    }

    /**
     * <p>set NBT_EUID value
     * @param nbtEuid NBT_EUID value
     */
    public void setNbtEuid(int nbtEuid) {
        nbtEuid__ = nbtEuid;
    }

    /**
     * <p>get NBT_EDATE value
     * @return NBT_EDATE value
     */
    public UDate getNbtEdate() {
        return nbtEdate__;
    }

    /**
     * <p>set NBT_EDATE value
     * @param nbtEdate NBT_EDATE value
     */
    public void setNbtEdate(UDate nbtEdate) {
        nbtEdate__ = nbtEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nbtSid__);
        buf.append(",");
        buf.append(nipSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(nbtAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nbtAdate__, ""));
        buf.append(",");
        buf.append(nbtEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nbtEdate__, ""));
        return buf.toString();
    }

}
