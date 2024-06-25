package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_TODOSTATUS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodostatusModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTS_SID mapping */
    private int ptsSid__;
    /** PTS_NAME mapping */
    private String ptsName__;
    /** PTS_RATE mapping */
    private int ptsRate__;
    /** PTS_SORT mapping */
    private int ptsSort__;
    /** PTS_AUID mapping */
    private int ptsAuid__;
    /** PTS_ADATE mapping */
    private UDate ptsAdate__;
    /** PTS_EUID mapping */
    private int ptsEuid__;
    /** PTS_EDATE mapping */
    private UDate ptsEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTodostatusModel() {
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
     * <p>get PTS_SID value
     * @return PTS_SID value
     */
    public int getPtsSid() {
        return ptsSid__;
    }

    /**
     * <p>set PTS_SID value
     * @param ptsSid PTS_SID value
     */
    public void setPtsSid(int ptsSid) {
        ptsSid__ = ptsSid;
    }

    /**
     * <p>get PTS_NAME value
     * @return PTS_NAME value
     */
    public String getPtsName() {
        return ptsName__;
    }

    /**
     * <p>set PTS_NAME value
     * @param ptsName PTS_NAME value
     */
    public void setPtsName(String ptsName) {
        ptsName__ = ptsName;
    }

    /**
     * <p>get PTS_RATE value
     * @return PTS_RATE value
     */
    public int getPtsRate() {
        return ptsRate__;
    }

    /**
     * <p>set PTS_RATE value
     * @param ptsRate PTS_RATE value
     */
    public void setPtsRate(int ptsRate) {
        ptsRate__ = ptsRate;
    }

    /**
     * <p>get PTS_SORT value
     * @return PTS_SORT value
     */
    public int getPtsSort() {
        return ptsSort__;
    }

    /**
     * <p>set PTS_SORT value
     * @param ptsSort PTS_SORT value
     */
    public void setPtsSort(int ptsSort) {
        ptsSort__ = ptsSort;
    }

    /**
     * <p>get PTS_AUID value
     * @return PTS_AUID value
     */
    public int getPtsAuid() {
        return ptsAuid__;
    }

    /**
     * <p>set PTS_AUID value
     * @param ptsAuid PTS_AUID value
     */
    public void setPtsAuid(int ptsAuid) {
        ptsAuid__ = ptsAuid;
    }

    /**
     * <p>get PTS_ADATE value
     * @return PTS_ADATE value
     */
    public UDate getPtsAdate() {
        return ptsAdate__;
    }

    /**
     * <p>set PTS_ADATE value
     * @param ptsAdate PTS_ADATE value
     */
    public void setPtsAdate(UDate ptsAdate) {
        ptsAdate__ = ptsAdate;
    }

    /**
     * <p>get PTS_EUID value
     * @return PTS_EUID value
     */
    public int getPtsEuid() {
        return ptsEuid__;
    }

    /**
     * <p>set PTS_EUID value
     * @param ptsEuid PTS_EUID value
     */
    public void setPtsEuid(int ptsEuid) {
        ptsEuid__ = ptsEuid;
    }

    /**
     * <p>get PTS_EDATE value
     * @return PTS_EDATE value
     */
    public UDate getPtsEdate() {
        return ptsEdate__;
    }

    /**
     * <p>set PTS_EDATE value
     * @param ptsEdate PTS_EDATE value
     */
    public void setPtsEdate(UDate ptsEdate) {
        ptsEdate__ = ptsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(ptsSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ptsName__, ""));
        buf.append(",");
        buf.append(ptsRate__);
        buf.append(",");
        buf.append(ptsSort__);
        buf.append(",");
        buf.append(ptsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptsAdate__, ""));
        buf.append(",");
        buf.append(ptsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptsEdate__, ""));
        return buf.toString();
    }

}
