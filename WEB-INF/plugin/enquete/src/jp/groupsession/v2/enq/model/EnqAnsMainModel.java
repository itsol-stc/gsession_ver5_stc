package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_ANS_MAIN Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAnsMainModel implements Serializable {

    /** EMN_SID mapping */
    private long emnSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** EAM_STS_KBN mapping */
    private int eamStsKbn__;
    /** EQM_ANS_DATE mapping */
    private UDate eqmAnsDate__;
    /** EAM_AUID mapping */
    private int eamAuid__;
    /** EAM_ADATE mapping */
    private UDate eamAdate__;
    /** EAM_EUID mapping */
    private int eamEuid__;
    /** EAM_EDATE mapping */
    private UDate eamEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqAnsMainModel() {
    }

    /**
     * <p>get EMN_SID value
     * @return EMN_SID value
     */
    public long getEmnSid() {
        return emnSid__;
    }

    /**
     * <p>set EMN_SID value
     * @param emnSid EMN_SID value
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get EAM_STS_KBN value
     * @return EAM_STS_KBN value
     */
    public int getEamStsKbn() {
        return eamStsKbn__;
    }

    /**
     * <p>set EAM_STS_KBN value
     * @param eamStsKbn EAM_STS_KBN value
     */
    public void setEamStsKbn(int eamStsKbn) {
        eamStsKbn__ = eamStsKbn;
    }

    /**
     * <p>get EQM_ANS_DATE value
     * @return EQM_ANS_DATE value
     */
    public UDate getEqmAnsDate() {
        return eqmAnsDate__;
    }

    /**
     * <p>set EQM_ANS_DATE value
     * @param eqmAnsDate EQM_ANS_DATE value
     */
    public void setEqmAnsDate(UDate eqmAnsDate) {
        eqmAnsDate__ = eqmAnsDate;
    }

    /**
     * <p>get EAM_AUID value
     * @return EAM_AUID value
     */
    public int getEamAuid() {
        return eamAuid__;
    }

    /**
     * <p>set EAM_AUID value
     * @param eamAuid EAM_AUID value
     */
    public void setEamAuid(int eamAuid) {
        eamAuid__ = eamAuid;
    }

    /**
     * <p>get EAM_ADATE value
     * @return EAM_ADATE value
     */
    public UDate getEamAdate() {
        return eamAdate__;
    }

    /**
     * <p>set EAM_ADATE value
     * @param eamAdate EAM_ADATE value
     */
    public void setEamAdate(UDate eamAdate) {
        eamAdate__ = eamAdate;
    }

    /**
     * <p>get EAM_EUID value
     * @return EAM_EUID value
     */
    public int getEamEuid() {
        return eamEuid__;
    }

    /**
     * <p>set EAM_EUID value
     * @param eamEuid EAM_EUID value
     */
    public void setEamEuid(int eamEuid) {
        eamEuid__ = eamEuid;
    }

    /**
     * <p>get EAM_EDATE value
     * @return EAM_EDATE value
     */
    public UDate getEamEdate() {
        return eamEdate__;
    }

    /**
     * <p>set EAM_EDATE value
     * @param eamEdate EAM_EDATE value
     */
    public void setEamEdate(UDate eamEdate) {
        eamEdate__ = eamEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(emnSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(eamStsKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqmAnsDate__, ""));
        buf.append(",");
        buf.append(eamAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eamAdate__, ""));
        buf.append(",");
        buf.append(eamEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eamEdate__, ""));
        return buf.toString();
    }

}
