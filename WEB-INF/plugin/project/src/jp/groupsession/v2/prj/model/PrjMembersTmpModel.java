package jp.groupsession.v2.prj.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>PRJ_MEMBERS_TMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjMembersTmpModel implements Serializable {

    /** PRT_SID mapping */
    private int prtSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** PMT_EMPLOYEE_KBN mapping */
    private int pmtEmployeeKbn__;
    /** PMT_ADMIN_KBN mapping */
    private int pmtAdminKbn__;
    /** PMT_AUID mapping */
    private int pmtAuid__;
    /** PMT_ADATE mapping */
    private UDate pmtAdate__;
    /** PMT_EUID mapping */
    private int pmtEuid__;
    /** PMT_EDATE mapping */
    private UDate pmtEdate__;
    /** PMT_MEM_KEY mapping */
    private String pmtMemKey__;

    /**
     * <p>pmtMemKey を取得します。
     * @return pmtMemKey
     */
    public String getPmtMemKey() {
        return pmtMemKey__;
    }

    /**
     * <p>pmtMemKey をセットします。
     * @param pmtMemKey pmtMemKey
     */
    public void setPmtMemKey(String pmtMemKey) {
        pmtMemKey__ = pmtMemKey;
    }

    /**
     * <p>Default Constructor
     */
    public PrjMembersTmpModel() {
    }

    /**
     * <p>get PRT_SID value
     * @return PRT_SID value
     */
    public int getPrtSid() {
        return prtSid__;
    }

    /**
     * <p>set PRT_SID value
     * @param prtSid PRT_SID value
     */
    public void setPrtSid(int prtSid) {
        prtSid__ = prtSid;
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
     * <p>get PMT_EMPLOYEE_KBN value
     * @return PMT_EMPLOYEE_KBN value
     */
    public int getPmtEmployeeKbn() {
        return pmtEmployeeKbn__;
    }

    /**
     * <p>set PMT_EMPLOYEE_KBN value
     * @param pmtEmployeeKbn PMT_EMPLOYEE_KBN value
     */
    public void setPmtEmployeeKbn(int pmtEmployeeKbn) {
        pmtEmployeeKbn__ = pmtEmployeeKbn;
    }

    /**
     * <p>get PMT_ADMIN_KBN value
     * @return PMT_ADMIN_KBN value
     */
    public int getPmtAdminKbn() {
        return pmtAdminKbn__;
    }

    /**
     * <p>set PMT_ADMIN_KBN value
     * @param pmtAdminKbn PMT_ADMIN_KBN value
     */
    public void setPmtAdminKbn(int pmtAdminKbn) {
        pmtAdminKbn__ = pmtAdminKbn;
    }

    /**
     * <p>get PMT_AUID value
     * @return PMT_AUID value
     */
    public int getPmtAuid() {
        return pmtAuid__;
    }

    /**
     * <p>set PMT_AUID value
     * @param pmtAuid PMT_AUID value
     */
    public void setPmtAuid(int pmtAuid) {
        pmtAuid__ = pmtAuid;
    }

    /**
     * <p>get PMT_ADATE value
     * @return PMT_ADATE value
     */
    public UDate getPmtAdate() {
        return pmtAdate__;
    }

    /**
     * <p>set PMT_ADATE value
     * @param pmtAdate PMT_ADATE value
     */
    public void setPmtAdate(UDate pmtAdate) {
        pmtAdate__ = pmtAdate;
    }

    /**
     * <p>get PMT_EUID value
     * @return PMT_EUID value
     */
    public int getPmtEuid() {
        return pmtEuid__;
    }

    /**
     * <p>set PMT_EUID value
     * @param pmtEuid PMT_EUID value
     */
    public void setPmtEuid(int pmtEuid) {
        pmtEuid__ = pmtEuid;
    }

    /**
     * <p>get PMT_EDATE value
     * @return PMT_EDATE value
     */
    public UDate getPmtEdate() {
        return pmtEdate__;
    }

    /**
     * <p>set PMT_EDATE value
     * @param pmtEdate PMT_EDATE value
     */
    public void setPmtEdate(UDate pmtEdate) {
        pmtEdate__ = pmtEdate;
    }
}