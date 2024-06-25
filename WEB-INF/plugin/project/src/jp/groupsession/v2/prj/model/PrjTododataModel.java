package jp.groupsession.v2.prj.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>PRJ_TODODATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTododataModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTD_SID mapping */
    private int ptdSid__;
    /** PTD_NO mapping */
    private int ptdNo__;
    /** PTD_CATEGORY mapping */
    private int ptdCategory__;
    /** PTD_TITLE mapping */
    private String ptdTitle__;
    /** PTD_DATE_PLAN mapping */
    private UDate ptdDatePlan__;
    /** PRJ_DATE_LIMIT mapping */
    private UDate prjDateLimit__;
    /** PTD_DATE_START mapping */
    private UDate ptdDateStart__;
    /** PTD_DATE_END mapping */
    private UDate ptdDateEnd__;
    /** PTD_PLAN_KOSU mapping */
    private BigDecimal ptdPlanKosu__;
    /** PTD_RESULTS_KOSU mapping */
    private BigDecimal ptdResultsKosu__;
    /** PTD_ALARM_KBN mapping */
    private int ptdAlarmKbn__;
    /** PTD_IMPORTANCE mapping */
    private int ptdImportance__;
    /** PSH_SID mapping */
    private int pshSid__;
    /** PTS_SID mapping */
    private int ptsSid__;
    /** PTD_CONTENT mapping */
    private String ptdContent__;
    /** PTD_AUID mapping */
    private int ptdAuid__;
    /** PTD_ADATE mapping */
    private UDate ptdAdate__;
    /** PTD_EUID mapping */
    private int ptdEuid__;
    /** PTD_EDATE mapping */
    private UDate ptdEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTododataModel() {
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
     * <p>get PTD_CATEGORY value
     * @return PTD_CATEGORY value
     */
    public int getPtdCategory() {
        return ptdCategory__;
    }

    /**
     * <p>set PTD_CATEGORY value
     * @param ptdCategory PTD_CATEGORY value
     */
    public void setPtdCategory(int ptdCategory) {
        ptdCategory__ = ptdCategory;
    }

    /**
     * <p>get PTD_TITLE value
     * @return PTD_TITLE value
     */
    public String getPtdTitle() {
        return ptdTitle__;
    }

    /**
     * <p>set PTD_TITLE value
     * @param ptdTitle PTD_TITLE value
     */
    public void setPtdTitle(String ptdTitle) {
        ptdTitle__ = ptdTitle;
    }

    /**
     * <p>get PTD_DATE_PLAN value
     * @return PTD_DATE_PLAN value
     */
    public UDate getPtdDatePlan() {
        return ptdDatePlan__;
    }

    /**
     * <p>set PTD_DATE_PLAN value
     * @param ptdDatePlan PTD_DATE_PLAN value
     */
    public void setPtdDatePlan(UDate ptdDatePlan) {
        ptdDatePlan__ = ptdDatePlan;
    }

    /**
     * <p>get PRJ_DATE_LIMIT value
     * @return PRJ_DATE_LIMIT value
     */
    public UDate getPrjDateLimit() {
        return prjDateLimit__;
    }

    /**
     * <p>set PRJ_DATE_LIMIT value
     * @param prjDateLimit PRJ_DATE_LIMIT value
     */
    public void setPrjDateLimit(UDate prjDateLimit) {
        prjDateLimit__ = prjDateLimit;
    }

    /**
     * <p>get PTD_DATE_START value
     * @return PTD_DATE_START value
     */
    public UDate getPtdDateStart() {
        return ptdDateStart__;
    }

    /**
     * <p>set PTD_DATE_START value
     * @param ptdDateStart PTD_DATE_START value
     */
    public void setPtdDateStart(UDate ptdDateStart) {
        ptdDateStart__ = ptdDateStart;
    }

    /**
     * <p>get PTD_DATE_END value
     * @return PTD_DATE_END value
     */
    public UDate getPtdDateEnd() {
        return ptdDateEnd__;
    }

    /**
     * <p>set PTD_DATE_END value
     * @param ptdDateEnd PTD_DATE_END value
     */
    public void setPtdDateEnd(UDate ptdDateEnd) {
        ptdDateEnd__ = ptdDateEnd;
    }

    /**
     * <p>get PTD_ALARM_KBN value
     * @return PTD_ALARM_KBN value
     */
    public int getPtdAlarmKbn() {
        return ptdAlarmKbn__;
    }

    /**
     * <p>set PTD_ALARM_KBN value
     * @param ptdAlarmKbn PTD_ALARM_KBN value
     */
    public void setPtdAlarmKbn(int ptdAlarmKbn) {
        ptdAlarmKbn__ = ptdAlarmKbn;
    }

    /**
     * <p>get PTD_IMPORTANCE value
     * @return PTD_IMPORTANCE value
     */
    public int getPtdImportance() {
        return ptdImportance__;
    }

    /**
     * <p>set PTD_IMPORTANCE value
     * @param ptdImportance PTD_IMPORTANCE value
     */
    public void setPtdImportance(int ptdImportance) {
        ptdImportance__ = ptdImportance;
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
     * <p>get PTD_CONTENT value
     * @return PTD_CONTENT value
     */
    public String getPtdContent() {
        return ptdContent__;
    }

    /**
     * <p>set PTD_CONTENT value
     * @param ptdContent PTD_CONTENT value
     */
    public void setPtdContent(String ptdContent) {
        ptdContent__ = ptdContent;
    }

    /**
     * <p>get PTD_AUID value
     * @return PTD_AUID value
     */
    public int getPtdAuid() {
        return ptdAuid__;
    }

    /**
     * <p>set PTD_AUID value
     * @param ptdAuid PTD_AUID value
     */
    public void setPtdAuid(int ptdAuid) {
        ptdAuid__ = ptdAuid;
    }

    /**
     * <p>get PTD_ADATE value
     * @return PTD_ADATE value
     */
    public UDate getPtdAdate() {
        return ptdAdate__;
    }

    /**
     * <p>set PTD_ADATE value
     * @param ptdAdate PTD_ADATE value
     */
    public void setPtdAdate(UDate ptdAdate) {
        ptdAdate__ = ptdAdate;
    }

    /**
     * <p>get PTD_EUID value
     * @return PTD_EUID value
     */
    public int getPtdEuid() {
        return ptdEuid__;
    }

    /**
     * <p>set PTD_EUID value
     * @param ptdEuid PTD_EUID value
     */
    public void setPtdEuid(int ptdEuid) {
        ptdEuid__ = ptdEuid;
    }

    /**
     * <p>get PTD_EDATE value
     * @return PTD_EDATE value
     */
    public UDate getPtdEdate() {
        return ptdEdate__;
    }

    /**
     * <p>set PTD_EDATE value
     * @param ptdEdate PTD_EDATE value
     */
    public void setPtdEdate(UDate ptdEdate) {
        ptdEdate__ = ptdEdate;
    }

    /**
     * <p>ptdSid を取得します。
     * @return ptdSid
     */
    public int getPtdSid() {
        return ptdSid__;
    }

    /**
     * <p>ptdSid をセットします。
     * @param ptdSid ptdSid
     */
    public void setPtdSid(int ptdSid) {
        ptdSid__ = ptdSid;
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
     * <p>ptdNo を取得します。
     * @return ptdNo
     */
    public int getPtdNo() {
        return ptdNo__;
    }

    /**
     * <p>ptdNo をセットします。
     * @param ptdNo ptdNo
     */
    public void setPtdNo(int ptdNo) {
        ptdNo__ = ptdNo;
    }

    /**
     * <p>ptdPlanKosu を取得します。
     * @return ptdPlanKosu
     */
    public BigDecimal getPtdPlanKosu() {
        return ptdPlanKosu__;
    }

    /**
     * <p>ptdPlanKosu をセットします。
     * @param ptdPlanKosu ptdPlanKosu
     */
    public void setPtdPlanKosu(BigDecimal ptdPlanKosu) {
        ptdPlanKosu__ = ptdPlanKosu;
    }

    /**
     * <p>ptdResultsKosu を取得します。
     * @return ptdResultsKosu
     */
    public BigDecimal getPtdResultsKosu() {
        return ptdResultsKosu__;
    }

    /**
     * <p>ptdResultsKosu をセットします。
     * @param ptdResultsKosu ptdResultsKosu
     */
    public void setPtdResultsKosu(BigDecimal ptdResultsKosu) {
        ptdResultsKosu__ = ptdResultsKosu;
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
        buf.append(ptdNo__);
        buf.append(",");
        buf.append(ptdCategory__);
        buf.append(",");
        buf.append(NullDefault.getString(ptdTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptdDatePlan__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(prjDateLimit__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptdDateStart__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptdDateEnd__, ""));
        buf.append(",");
        buf.append(ptdAlarmKbn__);
        buf.append(",");
        buf.append(ptdImportance__);
        buf.append(",");
        buf.append(pshSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ptdContent__, ""));
        buf.append(",");
        buf.append(ptdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptdAdate__, ""));
        buf.append(",");
        buf.append(ptdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptdEdate__, ""));
        return buf.toString();
    }

}
