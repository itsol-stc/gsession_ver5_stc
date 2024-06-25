package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>RSV_SIS_YRK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisYrkModel implements Serializable {

    /** RSY_SID mapping */
    private int rsySid__;
    /** RSD_SID mapping */
    private int rsdSid__;
    /** RSY_YGRP_SID mapping */
    private int rsyYgrpSid__;
    /** RSY_MOK mapping */
    private String rsyMok__;
    /** RSY_FR_DATE mapping */
    private UDate rsyFrDate__;
    /** RSY_TO_DATE mapping */
    private UDate rsyToDate__;
    /** RSY_BIKO mapping */
    private String rsyBiko__;
    /** RSY_AUID mapping */
    private int rsyAuid__;
    /** RSY_ADATE mapping */
    private UDate rsyAdate__;
    /** RSY_EUID mapping */
    private int rsyEuid__;
    /** RSY_EDATE mapping */
    private UDate rsyEdate__;
    /** SCD_RSSID mapping */
    private int scdRsSid__ = -1;
    /** RSY_EDIT mapping */
    private int rsyEdit__;
    /** RSR_RSID mapping */
    private int rsrRsid__ = -1;
    /** RSY_APPR_STATUS mapping */
    private int rsyApprStatus__;
    /** RSY_APPR_KBN mapping */
    private int rsyApprKbn__;
    /** RSY_APPR_UID mapping */
    private int rsyApprUid__;
    /** RSY_APPR_DATE mapping */
    private UDate rsyApprDate__;
    /** RSD_NAME mapping */
    private String rsdName__;
    /** RSG_SID mapping */
    private int rsgSid__;
    /** RSY_PUBLIC mapping */
    private int rsyPublic__;


    /**
     * <p>rsrRsid__ を取得します。
     * @return rsrRsid
     */
    public int getRsrRsid() {
        return rsrRsid__;
    }
    /**
     * <p>rsrRsid__ をセットします。
     * @param rsrRsid rsrRsid__
     */
    public void setRsrRsid(int rsrRsid) {
        rsrRsid__ = rsrRsid;
    }
    /**
     * <p>rsyEdit__ を取得します。
     * @return rsyEdit
     */
    public int getRsyEdit() {
        return rsyEdit__;
    }
    /**
     * <p>rsyEdit__ をセットします。
     * @param rsyEdit rsyEdit__
     */
    public void setRsyEdit(int rsyEdit) {
        rsyEdit__ = rsyEdit;
    }
    /**
     * <p>scdRsSid を取得します。
     * @return scdRsSid
     */
    public int getScdRsSid() {
        return scdRsSid__;
    }

    /**
     * <p>scdRsSid をセットします。
     * @param scdRsSid scdRsSid
     */
    public void setScdRsSid(int scdRsSid) {
        scdRsSid__ = scdRsSid;
    }

    /**
     * <p>Default Constructor
     */
    public RsvSisYrkModel() {
    }

    /**
     * <p>get RSY_SID value
     * @return RSY_SID value
     */
    public int getRsySid() {
        return rsySid__;
    }

    /**
     * <p>set RSY_SID value
     * @param rsySid RSY_SID value
     */
    public void setRsySid(int rsySid) {
        rsySid__ = rsySid;
    }

    /**
     * <p>get RSD_SID value
     * @return RSD_SID value
     */
    public int getRsdSid() {
        return rsdSid__;
    }

    /**
     * <p>set RSD_SID value
     * @param rsdSid RSD_SID value
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
    }

    /**
     * <p>get RSY_YGRP_SID value
     * @return RSY_YGRP_SID value
     */
    public int getRsyYgrpSid() {
        return rsyYgrpSid__;
    }

    /**
     * <p>set RSY_YGRP_SID value
     * @param rsyYgrpSid RSY_YGRP_SID value
     */
    public void setRsyYgrpSid(int rsyYgrpSid) {
        rsyYgrpSid__ = rsyYgrpSid;
    }

    /**
     * <p>get RSY_MOK value
     * @return RSY_MOK value
     */
    public String getRsyMok() {
        return rsyMok__;
    }

    /**
     * <p>set RSY_MOK value
     * @param rsyMok RSY_MOK value
     */
    public void setRsyMok(String rsyMok) {
        rsyMok__ = rsyMok;
    }

    /**
     * <p>get RSY_FR_DATE value
     * @return RSY_FR_DATE value
     */
    public UDate getRsyFrDate() {
        return rsyFrDate__;
    }

    /**
     * <p>set RSY_FR_DATE value
     * @param rsyFrDate RSY_FR_DATE value
     */
    public void setRsyFrDate(UDate rsyFrDate) {
        rsyFrDate__ = rsyFrDate;
    }

    /**
     * <p>get RSY_TO_DATE value
     * @return RSY_TO_DATE value
     */
    public UDate getRsyToDate() {
        return rsyToDate__;
    }

    /**
     * <p>set RSY_TO_DATE value
     * @param rsyToDate RSY_TO_DATE value
     */
    public void setRsyToDate(UDate rsyToDate) {
        rsyToDate__ = rsyToDate;
    }

    /**
     * <p>get RSY_BIKO value
     * @return RSY_BIKO value
     */
    public String getRsyBiko() {
        return rsyBiko__;
    }

    /**
     * <p>set RSY_BIKO value
     * @param rsyBiko RSY_BIKO value
     */
    public void setRsyBiko(String rsyBiko) {
        rsyBiko__ = rsyBiko;
    }

    /**
     * <p>get RSY_APPR_STATUS value
     * @return RSY_APPR_STATUS value
     */
    public int getRsyApprStatus() {
        return rsyApprStatus__;
    }

    /**
     * <p>set RSY_APPR_STATUS value
     * @param rsyApprStatus RSY_APPR_STATUS value
     */
    public void setRsyApprStatus(int rsyApprStatus) {
        rsyApprStatus__ = rsyApprStatus;
    }

    /**
     * <p>get RSY_APPR_KBN value
     * @return RSY_APPR_KBN value
     */
    public int getRsyApprKbn() {
        return rsyApprKbn__;
    }

    /**
     * <p>set RSY_APPR_KBN value
     * @param rsyApprKbn RSY_APPR_KBN value
     */
    public void setRsyApprKbn(int rsyApprKbn) {
        rsyApprKbn__ = rsyApprKbn;
    }

    /**
     * <p>get RSY_APPR_UID value
     * @return RSY_APPR_UID value
     */
    public int getRsyApprUid() {
        return rsyApprUid__;
    }

    /**
     * <p>set RSY_APPR_UID value
     * @param rsyApprUid RSY_APPR_UID value
     */
    public void setRsyApprUid(int rsyApprUid) {
        rsyApprUid__ = rsyApprUid;
    }

    /**
     * <p>get RSY_APPR_DATE value
     * @return RSY_APPR_DATE value
     */
    public UDate getRsyApprDate() {
        return rsyApprDate__;
    }

    /**
     * <p>set RSY_APPR_DATE value
     * @param rsyApprDate RSY_APPR_DATE value
     */
    public void setRsyApprDate(UDate rsyApprDate) {
        rsyApprDate__ = rsyApprDate;
    }

    /**
     * <p>get RSY_AUID value
     * @return RSY_AUID value
     */
    public int getRsyAuid() {
        return rsyAuid__;
    }

    /**
     * <p>set RSY_AUID value
     * @param rsyAuid RSY_AUID value
     */
    public void setRsyAuid(int rsyAuid) {
        rsyAuid__ = rsyAuid;
    }

    /**
     * <p>get RSY_ADATE value
     * @return RSY_ADATE value
     */
    public UDate getRsyAdate() {
        return rsyAdate__;
    }

    /**
     * <p>set RSY_ADATE value
     * @param rsyAdate RSY_ADATE value
     */
    public void setRsyAdate(UDate rsyAdate) {
        rsyAdate__ = rsyAdate;
    }

    /**
     * <p>get RSY_EUID value
     * @return RSY_EUID value
     */
    public int getRsyEuid() {
        return rsyEuid__;
    }

    /**
     * <p>set RSY_EUID value
     * @param rsyEuid RSY_EUID value
     */
    public void setRsyEuid(int rsyEuid) {
        rsyEuid__ = rsyEuid;
    }

    /**
     * <p>get RSY_EDATE value
     * @return RSY_EDATE value
     */
    public UDate getRsyEdate() {
        return rsyEdate__;
    }

    /**
     * <p>set RSY_EDATE value
     * @param rsyEdate RSY_EDATE value
     */
    public void setRsyEdate(UDate rsyEdate) {
        rsyEdate__ = rsyEdate;
    }
    /**
     * <p>rsdName を取得します。
     * @return rsdName
     */
    public String getRsdName() {
        return rsdName__;
    }
    /**
     * <p>rsdName をセットします。
     * @param rsdName rsdName
     */
    public void setRsdName(String rsdName) {
        rsdName__ = rsdName;
    }

    /**
     * <p>rsgSid を取得します。
     * @return rsgSid
     */
    public int getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid をセットします。
     * @param rsgSid rsgSid
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }

    /**
     * rsyPublicを取得します。
     * @return rsyPublic
     * */
    public int getRsyPublic() {
        return rsyPublic__;
    }
    /**
     * rsyPublicをセットします。
     * @param rsyPublic rsyPublic
     * */
    public void setRsyPublic(int rsyPublic) {
        rsyPublic__ = rsyPublic;
    }

}