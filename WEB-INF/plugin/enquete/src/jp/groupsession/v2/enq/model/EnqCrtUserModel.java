package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_CRT_USER Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqCrtUserModel implements Serializable {

    /** ECU_KBN mapping */
    private int ecuKbn__;
    /** ECU_SID mapping */
    private long ecuSid__;
    /** ECU_AUID mapping */
    private int ecuAuid__;
    /** ECU_ADATE mapping */
    private UDate ecuAdate__;
    /** ECU_EUID mapping */
    private int ecuEuid__;
    /** ECU_EDATE mapping */
    private UDate ecuEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqCrtUserModel() {
    }

    /**
     * <p>get ECU_KBN value
     * @return ECU_KBN value
     */
    public int getEcuKbn() {
        return ecuKbn__;
    }

    /**
     * <p>set ECU_KBN value
     * @param ecuKbn ECU_KBN value
     */
    public void setEcuKbn(int ecuKbn) {
        ecuKbn__ = ecuKbn;
    }

    /**
     * <p>get ECU_SID value
     * @return ECU_SID value
     */
    public long getEcuSid() {
        return ecuSid__;
    }

    /**
     * <p>set ECU_SID value
     * @param ecuSid ECU_SID value
     */
    public void setEcuSid(long ecuSid) {
        ecuSid__ = ecuSid;
    }

    /**
     * <p>get ECU_AUID value
     * @return ECU_AUID value
     */
    public int getEcuAuid() {
        return ecuAuid__;
    }

    /**
     * <p>set ECU_AUID value
     * @param ecuAuid ECU_AUID value
     */
    public void setEcuAuid(int ecuAuid) {
        ecuAuid__ = ecuAuid;
    }

    /**
     * <p>get ECU_ADATE value
     * @return ECU_ADATE value
     */
    public UDate getEcuAdate() {
        return ecuAdate__;
    }

    /**
     * <p>set ECU_ADATE value
     * @param ecuAdate ECU_ADATE value
     */
    public void setEcuAdate(UDate ecuAdate) {
        ecuAdate__ = ecuAdate;
    }

    /**
     * <p>get ECU_EUID value
     * @return ECU_EUID value
     */
    public int getEcuEuid() {
        return ecuEuid__;
    }

    /**
     * <p>set ECU_EUID value
     * @param ecuEuid ECU_EUID value
     */
    public void setEcuEuid(int ecuEuid) {
        ecuEuid__ = ecuEuid;
    }

    /**
     * <p>get ECU_EDATE value
     * @return ECU_EDATE value
     */
    public UDate getEcuEdate() {
        return ecuEdate__;
    }

    /**
     * <p>set ECU_EDATE value
     * @param ecuEdate ECU_EDATE value
     */
    public void setEcuEdate(UDate ecuEdate) {
        ecuEdate__ = ecuEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ecuKbn__);
        buf.append(",");
        buf.append(ecuSid__);
        buf.append(",");
        buf.append(ecuAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ecuAdate__, ""));
        buf.append(",");
        buf.append(ecuEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ecuEdate__, ""));
        return buf.toString();
    }

}
