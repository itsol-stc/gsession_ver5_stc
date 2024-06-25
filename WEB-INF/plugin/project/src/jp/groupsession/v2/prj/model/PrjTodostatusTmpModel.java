package jp.groupsession.v2.prj.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>PRJ_TODOSTATUS_TMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodostatusTmpModel implements Serializable {

    /** PRT_SID mapping */
    private int prtSid__;
    /** PST_SID mapping */
    private int pstSid__;
    /** PST_NAME mapping */
    private String pstName__;
    /** PST_RATE mapping */
    private int pstRate__;
    /** PST_SORT mapping */
    private int pstSort__;
    /** PST_AUID mapping */
    private int pstAuid__;
    /** PST_ADATE mapping */
    private UDate pstAdate__;
    /** PST_EUID mapping */
    private int pstEuid__;
    /** PST_EDATE mapping */
    private UDate pstEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTodostatusTmpModel() {
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
     * <p>get PST_SID value
     * @return PST_SID value
     */
    public int getPstSid() {
        return pstSid__;
    }

    /**
     * <p>set PST_SID value
     * @param pstSid PST_SID value
     */
    public void setPstSid(int pstSid) {
        pstSid__ = pstSid;
    }

    /**
     * <p>get PST_NAME value
     * @return PST_NAME value
     */
    public String getPstName() {
        return pstName__;
    }

    /**
     * <p>set PST_NAME value
     * @param pstName PST_NAME value
     */
    public void setPstName(String pstName) {
        pstName__ = pstName;
    }

    /**
     * <p>get PST_RATE value
     * @return PST_RATE value
     */
    public int getPstRate() {
        return pstRate__;
    }

    /**
     * <p>set PST_RATE value
     * @param pstRate PST_RATE value
     */
    public void setPstRate(int pstRate) {
        pstRate__ = pstRate;
    }

    /**
     * <p>get PST_SORT value
     * @return PST_SORT value
     */
    public int getPstSort() {
        return pstSort__;
    }

    /**
     * <p>set PST_SORT value
     * @param pstSort PST_SORT value
     */
    public void setPstSort(int pstSort) {
        pstSort__ = pstSort;
    }

    /**
     * <p>get PST_AUID value
     * @return PST_AUID value
     */
    public int getPstAuid() {
        return pstAuid__;
    }

    /**
     * <p>set PST_AUID value
     * @param pstAuid PST_AUID value
     */
    public void setPstAuid(int pstAuid) {
        pstAuid__ = pstAuid;
    }

    /**
     * <p>get PST_ADATE value
     * @return PST_ADATE value
     */
    public UDate getPstAdate() {
        return pstAdate__;
    }

    /**
     * <p>set PST_ADATE value
     * @param pstAdate PST_ADATE value
     */
    public void setPstAdate(UDate pstAdate) {
        pstAdate__ = pstAdate;
    }

    /**
     * <p>get PST_EUID value
     * @return PST_EUID value
     */
    public int getPstEuid() {
        return pstEuid__;
    }

    /**
     * <p>set PST_EUID value
     * @param pstEuid PST_EUID value
     */
    public void setPstEuid(int pstEuid) {
        pstEuid__ = pstEuid;
    }

    /**
     * <p>get PST_EDATE value
     * @return PST_EDATE value
     */
    public UDate getPstEdate() {
        return pstEdate__;
    }

    /**
     * <p>set PST_EDATE value
     * @param pstEdate PST_EDATE value
     */
    public void setPstEdate(UDate pstEdate) {
        pstEdate__ = pstEdate;
    }
}