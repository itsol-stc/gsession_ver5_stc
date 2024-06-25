package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_PRJSTATUS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjPrjstatusModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PRS_SID mapping */
    private int prsSid__;
    /** PRS_SORT mapping */
    private int prsSort__;
    /** PRS_NAME mapping */
    private String prsName__;
    /** PRS_RATE mapping */
    private int prsRate__;
    /** PRS_AUID mapping */
    private int prsAuid__;
    /** PRS_ADATE mapping */
    private UDate prsAdate__;
    /** PRS_EUID mapping */
    private int prsEuid__;
    /** PRS_EDATE mapping */
    private UDate prsEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjPrjstatusModel() {
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
     * <p>get PRS_SID value
     * @return PRS_SID value
     */
    public int getPrsSid() {
        return prsSid__;
    }

    /**
     * <p>set PRS_SID value
     * @param prsSid PRS_SID value
     */
    public void setPrsSid(int prsSid) {
        prsSid__ = prsSid;
    }

    /**
     * <p>get PRS_SORT value
     * @return PRS_SORT value
     */
    public int getPrsSort() {
        return prsSort__;
    }

    /**
     * <p>set PRS_SORT value
     * @param prsSort PRS_SORT value
     */
    public void setPrsSort(int prsSort) {
        prsSort__ = prsSort;
    }

    /**
     * <p>get PRS_NAME value
     * @return PRS_NAME value
     */
    public String getPrsName() {
        return prsName__;
    }

    /**
     * <p>set PRS_NAME value
     * @param prsName PRS_NAME value
     */
    public void setPrsName(String prsName) {
        prsName__ = prsName;
    }

    /**
     * <p>get PRS_AUID value
     * @return PRS_AUID value
     */
    public int getPrsAuid() {
        return prsAuid__;
    }

    /**
     * <p>set PRS_AUID value
     * @param prsAuid PRS_AUID value
     */
    public void setPrsAuid(int prsAuid) {
        prsAuid__ = prsAuid;
    }

    /**
     * <p>get PRS_ADATE value
     * @return PRS_ADATE value
     */
    public UDate getPrsAdate() {
        return prsAdate__;
    }

    /**
     * <p>set PRS_ADATE value
     * @param prsAdate PRS_ADATE value
     */
    public void setPrsAdate(UDate prsAdate) {
        prsAdate__ = prsAdate;
    }

    /**
     * <p>get PRS_EUID value
     * @return PRS_EUID value
     */
    public int getPrsEuid() {
        return prsEuid__;
    }

    /**
     * <p>set PRS_EUID value
     * @param prsEuid PRS_EUID value
     */
    public void setPrsEuid(int prsEuid) {
        prsEuid__ = prsEuid;
    }

    /**
     * <p>get PRS_EDATE value
     * @return PRS_EDATE value
     */
    public UDate getPrsEdate() {
        return prsEdate__;
    }

    /**
     * <p>set PRS_EDATE value
     * @param prsEdate PRS_EDATE value
     */
    public void setPrsEdate(UDate prsEdate) {
        prsEdate__ = prsEdate;
    }

    /**
     * <p>prsRate を取得します。
     * @return prsRate
     */
    public int getPrsRate() {
        return prsRate__;
    }

    /**
     * <p>prsRate をセットします。
     * @param prsRate prsRate
     */
    public void setPrsRate(int prsRate) {
        prsRate__ = prsRate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(prsSid__);
        buf.append(",");
        buf.append(prsSort__);
        buf.append(",");
        buf.append(NullDefault.getString(prsName__, ""));
        buf.append(",");
        buf.append(prsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prsAdate__, ""));
        buf.append(",");
        buf.append(prsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prsEdate__, ""));
        return buf.toString();
    }

}
