package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_TODOCATEGORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodocategoryModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTC_CATEGORY_SID mapping */
    private int ptcCategorySid__;
    /** PTC_SORT mapping */
    private int ptcSort__;
    /** PTC_NAME mapping */
    private String ptcName__;
    /** PTC_AUID mapping */
    private int ptcAuid__;
    /** PTC_ADATE mapping */
    private UDate ptcAdate__;
    /** PTC_EUID mapping */
    private int ptcEuid__;
    /** PTC_EDATE mapping */
    private UDate ptcEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTodocategoryModel() {
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
     * <p>get PTC_CATEGORY_SID value
     * @return PTC_CATEGORY_SID value
     */
    public int getPtcCategorySid() {
        return ptcCategorySid__;
    }

    /**
     * <p>set PTC_CATEGORY_SID value
     * @param ptcCategorySid PTC_CATEGORY_SID value
     */
    public void setPtcCategorySid(int ptcCategorySid) {
        ptcCategorySid__ = ptcCategorySid;
    }

    /**
     * <p>get PTC_SORT value
     * @return PTC_SORT value
     */
    public int getPtcSort() {
        return ptcSort__;
    }

    /**
     * <p>set PTC_SORT value
     * @param ptcSort PTC_SORT value
     */
    public void setPtcSort(int ptcSort) {
        ptcSort__ = ptcSort;
    }

    /**
     * <p>get PTC_NAME value
     * @return PTC_NAME value
     */
    public String getPtcName() {
        return ptcName__;
    }

    /**
     * <p>set PTC_NAME value
     * @param ptcName PTC_NAME value
     */
    public void setPtcName(String ptcName) {
        ptcName__ = ptcName;
    }

    /**
     * <p>get PTC_AUID value
     * @return PTC_AUID value
     */
    public int getPtcAuid() {
        return ptcAuid__;
    }

    /**
     * <p>set PTC_AUID value
     * @param ptcAuid PTC_AUID value
     */
    public void setPtcAuid(int ptcAuid) {
        ptcAuid__ = ptcAuid;
    }

    /**
     * <p>get PTC_ADATE value
     * @return PTC_ADATE value
     */
    public UDate getPtcAdate() {
        return ptcAdate__;
    }

    /**
     * <p>set PTC_ADATE value
     * @param ptcAdate PTC_ADATE value
     */
    public void setPtcAdate(UDate ptcAdate) {
        ptcAdate__ = ptcAdate;
    }

    /**
     * <p>get PTC_EUID value
     * @return PTC_EUID value
     */
    public int getPtcEuid() {
        return ptcEuid__;
    }

    /**
     * <p>set PTC_EUID value
     * @param ptcEuid PTC_EUID value
     */
    public void setPtcEuid(int ptcEuid) {
        ptcEuid__ = ptcEuid;
    }

    /**
     * <p>get PTC_EDATE value
     * @return PTC_EDATE value
     */
    public UDate getPtcEdate() {
        return ptcEdate__;
    }

    /**
     * <p>set PTC_EDATE value
     * @param ptcEdate PTC_EDATE value
     */
    public void setPtcEdate(UDate ptcEdate) {
        ptcEdate__ = ptcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(ptcCategorySid__);
        buf.append(",");
        buf.append(ptcSort__);
        buf.append(",");
        buf.append(NullDefault.getString(ptcName__, ""));
        buf.append(",");
        buf.append(ptcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptcAdate__, ""));
        buf.append(",");
        buf.append(ptcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptcEdate__, ""));
        return buf.toString();
    }

}
