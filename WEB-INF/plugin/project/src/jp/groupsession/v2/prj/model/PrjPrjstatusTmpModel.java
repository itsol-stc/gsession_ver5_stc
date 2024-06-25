package jp.groupsession.v2.prj.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>PRJ_PRJSTATUS_TMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjPrjstatusTmpModel implements Serializable {

    /** PRT_SID mapping */
    private int prtSid__;
    /** PTT_SID mapping */
    private int pttSid__;
    /** PTT_SORT mapping */
    private int pttSort__;
    /** PTT_NAME mapping */
    private String pttName__;
    /** PTT_RATE mapping */
    private int pttRate__;
    /** PTT_AUID mapping */
    private int pttAuid__;
    /** PTT_ADATE mapping */
    private UDate pttAdate__;
    /** PTT_EUID mapping */
    private int pttEuid__;
    /** PTT_EDATE mapping */
    private UDate pttEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjPrjstatusTmpModel() {
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
     * <p>get PTT_SID value
     * @return PTT_SID value
     */
    public int getPttSid() {
        return pttSid__;
    }

    /**
     * <p>set PTT_SID value
     * @param pttSid PTT_SID value
     */
    public void setPttSid(int pttSid) {
        pttSid__ = pttSid;
    }

    /**
     * <p>get PTT_SORT value
     * @return PTT_SORT value
     */
    public int getPttSort() {
        return pttSort__;
    }

    /**
     * <p>set PTT_SORT value
     * @param pttSort PTT_SORT value
     */
    public void setPttSort(int pttSort) {
        pttSort__ = pttSort;
    }

    /**
     * <p>get PTT_NAME value
     * @return PTT_NAME value
     */
    public String getPttName() {
        return pttName__;
    }

    /**
     * <p>set PTT_NAME value
     * @param pttName PTT_NAME value
     */
    public void setPttName(String pttName) {
        pttName__ = pttName;
    }

    /**
     * <p>get PTT_RATE value
     * @return PTT_RATE value
     */
    public int getPttRate() {
        return pttRate__;
    }

    /**
     * <p>set PTT_RATE value
     * @param pttRate PTT_RATE value
     */
    public void setPttRate(int pttRate) {
        pttRate__ = pttRate;
    }

    /**
     * <p>get PTT_AUID value
     * @return PTT_AUID value
     */
    public int getPttAuid() {
        return pttAuid__;
    }

    /**
     * <p>set PTT_AUID value
     * @param pttAuid PTT_AUID value
     */
    public void setPttAuid(int pttAuid) {
        pttAuid__ = pttAuid;
    }

    /**
     * <p>get PTT_ADATE value
     * @return PTT_ADATE value
     */
    public UDate getPttAdate() {
        return pttAdate__;
    }

    /**
     * <p>set PTT_ADATE value
     * @param pttAdate PTT_ADATE value
     */
    public void setPttAdate(UDate pttAdate) {
        pttAdate__ = pttAdate;
    }

    /**
     * <p>get PTT_EUID value
     * @return PTT_EUID value
     */
    public int getPttEuid() {
        return pttEuid__;
    }

    /**
     * <p>set PTT_EUID value
     * @param pttEuid PTT_EUID value
     */
    public void setPttEuid(int pttEuid) {
        pttEuid__ = pttEuid;
    }

    /**
     * <p>get PTT_EDATE value
     * @return PTT_EDATE value
     */
    public UDate getPttEdate() {
        return pttEdate__;
    }

    /**
     * <p>set PTT_EDATE value
     * @param pttEdate PTT_EDATE value
     */
    public void setPttEdate(UDate pttEdate) {
        pttEdate__ = pttEdate;
    }
}