package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_COMPANY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PrjCompanyModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** ACO_SID mapping */
    private int acoSid__;
    /** ABA_SID mapping */
    private int abaSid__;
    /** PRC_AUID mapping */
    private int prcAuid__;
    /** PRC_ADATE mapping */
    private UDate prcAdate__;
    /** PRC_EUID mapping */
    private int prcEuid__;
    /** PRC_EDATE mapping */
    private UDate prcEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjCompanyModel() {
    }

    /**
     * <p>get PRJ_SID value
     * @return PRJ_SID value
     */
    public int getPrjSid() {
        return prjSid__;
    }

    /**
     * <p>set PRJ_SID value
     * @param prjSid PRJ_SID value
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }

    /**
     * <p>get ACO_SID value
     * @return ACO_SID value
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>set ACO_SID value
     * @param acoSid ACO_SID value
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>get ABA_SID value
     * @return ABA_SID value
     */
    public int getAbaSid() {
        return abaSid__;
    }

    /**
     * <p>set ABA_SID value
     * @param abaSid ABA_SID value
     */
    public void setAbaSid(int abaSid) {
        abaSid__ = abaSid;
    }

    /**
     * <p>get PRC_AUID value
     * @return PRC_AUID value
     */
    public int getPrcAuid() {
        return prcAuid__;
    }

    /**
     * <p>set PRC_AUID value
     * @param prcAuid PRC_AUID value
     */
    public void setPrcAuid(int prcAuid) {
        prcAuid__ = prcAuid;
    }

    /**
     * <p>get PRC_ADATE value
     * @return PRC_ADATE value
     */
    public UDate getPrcAdate() {
        return prcAdate__;
    }

    /**
     * <p>set PRC_ADATE value
     * @param prcAdate PRC_ADATE value
     */
    public void setPrcAdate(UDate prcAdate) {
        prcAdate__ = prcAdate;
    }

    /**
     * <p>get PRC_EUID value
     * @return PRC_EUID value
     */
    public int getPrcEuid() {
        return prcEuid__;
    }

    /**
     * <p>set PRC_EUID value
     * @param prcEuid PRC_EUID value
     */
    public void setPrcEuid(int prcEuid) {
        prcEuid__ = prcEuid;
    }

    /**
     * <p>get PRC_EDATE value
     * @return PRC_EDATE value
     */
    public UDate getPrcEdate() {
        return prcEdate__;
    }

    /**
     * <p>set PRC_EDATE value
     * @param prcEdate PRC_EDATE value
     */
    public void setPrcEdate(UDate prcEdate) {
        prcEdate__ = prcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(acoSid__);
        buf.append(",");
        buf.append(abaSid__);
        buf.append(",");
        buf.append(prcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prcAdate__, ""));
        buf.append(",");
        buf.append(prcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prcEdate__, ""));
        return buf.toString();
    }

}
