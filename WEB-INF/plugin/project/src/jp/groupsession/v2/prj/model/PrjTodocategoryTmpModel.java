package jp.groupsession.v2.prj.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>PRJ_TODOCATEGORY_TMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodocategoryTmpModel implements Serializable {

    /** PRT_SID mapping */
    private int prtSid__;
    /** PCT_CATEGORY_SID mapping */
    private int pctCategorySid__;
    /** PCT_SORT mapping */
    private int pctSort__;
    /** PCT_NAME mapping */
    private String pctName__;
    /** PCT_AUID mapping */
    private int pctAuid__;
    /** PCT_ADATE mapping */
    private UDate pctAdate__;
    /** PCT_EUID mapping */
    private int pctEuid__;
    /** PCT_EDATE mapping */
    private UDate pctEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTodocategoryTmpModel() {
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
     * <p>get PCT_CATEGORY_SID value
     * @return PCT_CATEGORY_SID value
     */
    public int getPctCategorySid() {
        return pctCategorySid__;
    }

    /**
     * <p>set PCT_CATEGORY_SID value
     * @param pctCategorySid PCT_CATEGORY_SID value
     */
    public void setPctCategorySid(int pctCategorySid) {
        pctCategorySid__ = pctCategorySid;
    }

    /**
     * <p>get PCT_SORT value
     * @return PCT_SORT value
     */
    public int getPctSort() {
        return pctSort__;
    }

    /**
     * <p>set PCT_SORT value
     * @param pctSort PCT_SORT value
     */
    public void setPctSort(int pctSort) {
        pctSort__ = pctSort;
    }

    /**
     * <p>get PCT_NAME value
     * @return PCT_NAME value
     */
    public String getPctName() {
        return pctName__;
    }

    /**
     * <p>set PCT_NAME value
     * @param pctName PCT_NAME value
     */
    public void setPctName(String pctName) {
        pctName__ = pctName;
    }

    /**
     * <p>get PCT_AUID value
     * @return PCT_AUID value
     */
    public int getPctAuid() {
        return pctAuid__;
    }

    /**
     * <p>set PCT_AUID value
     * @param pctAuid PCT_AUID value
     */
    public void setPctAuid(int pctAuid) {
        pctAuid__ = pctAuid;
    }

    /**
     * <p>get PCT_ADATE value
     * @return PCT_ADATE value
     */
    public UDate getPctAdate() {
        return pctAdate__;
    }

    /**
     * <p>set PCT_ADATE value
     * @param pctAdate PCT_ADATE value
     */
    public void setPctAdate(UDate pctAdate) {
        pctAdate__ = pctAdate;
    }

    /**
     * <p>get PCT_EUID value
     * @return PCT_EUID value
     */
    public int getPctEuid() {
        return pctEuid__;
    }

    /**
     * <p>set PCT_EUID value
     * @param pctEuid PCT_EUID value
     */
    public void setPctEuid(int pctEuid) {
        pctEuid__ = pctEuid;
    }

    /**
     * <p>get PCT_EDATE value
     * @return PCT_EDATE value
     */
    public UDate getPctEdate() {
        return pctEdate__;
    }

    /**
     * <p>set PCT_EDATE value
     * @param pctEdate PCT_EDATE value
     */
    public void setPctEdate(UDate pctEdate) {
        pctEdate__ = pctEdate;
    }
}