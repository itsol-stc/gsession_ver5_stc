package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>RSV_SIS_KBN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisKbnModel implements Serializable {

    /** RSK_SID mapping */
    private int rskSid__;
    /** RSK_NAME mapping */
    private String rskName__;
    /** RSK_SORT mapping */
    private int rskSort__;
    /** RSK_AUID mapping */
    private int rskAuid__;
    /** RSK_ADATE mapping */
    private UDate rskAdate__;
    /** RSK_EUID mapping */
    private int rskEuid__;
    /** RSK_EDATE mapping */
    private UDate rskEdate__;

    /**
     * <p>Default Constructor
     */
    public RsvSisKbnModel() {
    }

    /**
     * <p>get RSK_SID value
     * @return RSK_SID value
     */
    public int getRskSid() {
        return rskSid__;
    }

    /**
     * <p>set RSK_SID value
     * @param rskSid RSK_SID value
     */
    public void setRskSid(int rskSid) {
        rskSid__ = rskSid;
    }

    /**
     * <p>get RSK_NAME value
     * @return RSK_NAME value
     */
    public String getRskName() {
        return rskName__;
    }

    /**
     * <p>set RSK_NAME value
     * @param rskName RSK_NAME value
     */
    public void setRskName(String rskName) {
        rskName__ = rskName;
    }

    /**
     * <p>get RSK_SORT value
     * @return RSK_SORT value
     */
    public int getRskSort() {
        return rskSort__;
    }

    /**
     * <p>set RSK_SORT value
     * @param rskSort RSK_SORT value
     */
    public void setRskSort(int rskSort) {
        rskSort__ = rskSort;
    }

    /**
     * <p>get RSK_AUID value
     * @return RSK_AUID value
     */
    public int getRskAuid() {
        return rskAuid__;
    }

    /**
     * <p>set RSK_AUID value
     * @param rskAuid RSK_AUID value
     */
    public void setRskAuid(int rskAuid) {
        rskAuid__ = rskAuid;
    }

    /**
     * <p>get RSK_ADATE value
     * @return RSK_ADATE value
     */
    public UDate getRskAdate() {
        return rskAdate__;
    }

    /**
     * <p>set RSK_ADATE value
     * @param rskAdate RSK_ADATE value
     */
    public void setRskAdate(UDate rskAdate) {
        rskAdate__ = rskAdate;
    }

    /**
     * <p>get RSK_EUID value
     * @return RSK_EUID value
     */
    public int getRskEuid() {
        return rskEuid__;
    }

    /**
     * <p>set RSK_EUID value
     * @param rskEuid RSK_EUID value
     */
    public void setRskEuid(int rskEuid) {
        rskEuid__ = rskEuid;
    }

    /**
     * <p>get RSK_EDATE value
     * @return RSK_EDATE value
     */
    public UDate getRskEdate() {
        return rskEdate__;
    }

    /**
     * <p>set RSK_EDATE value
     * @param rskEdate RSK_EDATE value
     */
    public void setRskEdate(UDate rskEdate) {
        rskEdate__ = rskEdate;
    }
}