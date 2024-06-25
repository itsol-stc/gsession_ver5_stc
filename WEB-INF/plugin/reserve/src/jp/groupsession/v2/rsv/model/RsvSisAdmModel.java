package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>RSV_SIS_ADM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisAdmModel implements Serializable {

    /** RSG_SID mapping */
    private int rsgSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** RSA_AUID mapping */
    private int rsaAuid__;
    /** RSA_ADATE mapping */
    private UDate rsaAdate__;
    /** RSA_EUID mapping */
    private int rsaEuid__;
    /** RSA_EDATE mapping */
    private UDate rsaEdate__;

    /**
     * <p>Default Constructor
     */
    public RsvSisAdmModel() {
    }

    /**
     * <p>get RSG_SID value
     * @return RSG_SID value
     */
    public int getRsgSid() {
        return rsgSid__;
    }

    /**
     * <p>set RSG_SID value
     * @param rsgSid RSG_SID value
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
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
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get RSA_AUID value
     * @return RSA_AUID value
     */
    public int getRsaAuid() {
        return rsaAuid__;
    }

    /**
     * <p>set RSA_AUID value
     * @param rsaAuid RSA_AUID value
     */
    public void setRsaAuid(int rsaAuid) {
        rsaAuid__ = rsaAuid;
    }

    /**
     * <p>get RSA_ADATE value
     * @return RSA_ADATE value
     */
    public UDate getRsaAdate() {
        return rsaAdate__;
    }

    /**
     * <p>set RSA_ADATE value
     * @param rsaAdate RSA_ADATE value
     */
    public void setRsaAdate(UDate rsaAdate) {
        rsaAdate__ = rsaAdate;
    }

    /**
     * <p>get RSA_EUID value
     * @return RSA_EUID value
     */
    public int getRsaEuid() {
        return rsaEuid__;
    }

    /**
     * <p>set RSA_EUID value
     * @param rsaEuid RSA_EUID value
     */
    public void setRsaEuid(int rsaEuid) {
        rsaEuid__ = rsaEuid;
    }

    /**
     * <p>get RSA_EDATE value
     * @return RSA_EDATE value
     */
    public UDate getRsaEdate() {
        return rsaEdate__;
    }

    /**
     * <p>set RSA_EDATE value
     * @param rsaEdate RSA_EDATE value
     */
    public void setRsaEdate(UDate rsaEdate) {
        rsaEdate__ = rsaEdate;
    }

}