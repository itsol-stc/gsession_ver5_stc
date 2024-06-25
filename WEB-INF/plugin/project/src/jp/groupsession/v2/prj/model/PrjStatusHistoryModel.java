package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_STATUS_HISTORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjStatusHistoryModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTD_SID mapping */
    private int ptdSid__;
    /** PSH_SID mapping */
    private int pshSid__;
    /** PTS_SID mapping */
    private int ptsSid__;
    /** PSH_REASON mapping */
    private String pshReason__;
    /** PSH_AUID mapping */
    private int pshAuid__;
    /** PSH_ADATE mapping */
    private UDate pshAdate__;
    /** PSH_EUID mapping */
    private int pshEuid__;
    /** PSH_EDATE mapping */
    private UDate pshEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjStatusHistoryModel() {
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
     * <p>get PTD_SID value
     * @return PTD_SID value
     */
    public int getPtdSid() {
        return ptdSid__;
    }

    /**
     * <p>set PTD_SID value
     * @param ptdSid PTD_SID value
     */
    public void setPtdSid(int ptdSid) {
        ptdSid__ = ptdSid;
    }

    /**
     * <p>get PSH_SID value
     * @return PSH_SID value
     */
    public int getPshSid() {
        return pshSid__;
    }

    /**
     * <p>set PSH_SID value
     * @param pshSid PSH_SID value
     */
    public void setPshSid(int pshSid) {
        pshSid__ = pshSid;
    }

    /**
     * <p>get PSH_REASON value
     * @return PSH_REASON value
     */
    public String getPshReason() {
        return pshReason__;
    }

    /**
     * <p>set PSH_REASON value
     * @param pshReason PSH_REASON value
     */
    public void setPshReason(String pshReason) {
        pshReason__ = pshReason;
    }

    /**
     * <p>get PSH_AUID value
     * @return PSH_AUID value
     */
    public int getPshAuid() {
        return pshAuid__;
    }

    /**
     * <p>set PSH_AUID value
     * @param pshAuid PSH_AUID value
     */
    public void setPshAuid(int pshAuid) {
        pshAuid__ = pshAuid;
    }

    /**
     * <p>get PSH_ADATE value
     * @return PSH_ADATE value
     */
    public UDate getPshAdate() {
        return pshAdate__;
    }

    /**
     * <p>set PSH_ADATE value
     * @param pshAdate PSH_ADATE value
     */
    public void setPshAdate(UDate pshAdate) {
        pshAdate__ = pshAdate;
    }

    /**
     * <p>get PSH_EUID value
     * @return PSH_EUID value
     */
    public int getPshEuid() {
        return pshEuid__;
    }

    /**
     * <p>set PSH_EUID value
     * @param pshEuid PSH_EUID value
     */
    public void setPshEuid(int pshEuid) {
        pshEuid__ = pshEuid;
    }

    /**
     * <p>get PSH_EDATE value
     * @return PSH_EDATE value
     */
    public UDate getPshEdate() {
        return pshEdate__;
    }

    /**
     * <p>set PSH_EDATE value
     * @param pshEdate PSH_EDATE value
     */
    public void setPshEdate(UDate pshEdate) {
        pshEdate__ = pshEdate;
    }

    /**
     * <p>ptsSid を取得します。
     * @return ptsSid
     */
    public int getPtsSid() {
        return ptsSid__;
    }

    /**
     * <p>ptsSid をセットします。
     * @param ptsSid ptsSid
     */
    public void setPtsSid(int ptsSid) {
        ptsSid__ = ptsSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(ptdSid__);
        buf.append(",");
        buf.append(pshSid__);
        buf.append(",");
        buf.append(ptsSid__);
        buf.append(",");
        buf.append(NullDefault.getString(pshReason__, ""));
        buf.append(",");
        buf.append(pshAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pshAdate__, ""));
        buf.append(",");
        buf.append(pshEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pshEdate__, ""));
        return buf.toString();
    }

}
