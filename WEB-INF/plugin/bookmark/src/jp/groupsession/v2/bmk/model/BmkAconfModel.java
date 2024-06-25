package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkAconfModel implements Serializable {

    /** BAC_PUB_EDIT mapping */
    private int bacPubEdit__;
    /** BAC_GRP_EDIT mapping */
    private int bacGrpEdit__;
    /** BAC_LIMIT mapping */
    private int bacLimit__;
    /** BAC_AUID mapping */
    private int bacAuid__;
    /** BAC_ADATE mapping */
    private UDate bacAdate__;
    /** BAC_EUID mapping */
    private int bacEuid__;
    /** BAC_EDATE mapping */
    private UDate bacEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkAconfModel() {
    }

    /**
     * <p>get BAC_PUB_EDIT value
     * @return BAC_PUB_EDIT value
     */
    public int getBacPubEdit() {
        return bacPubEdit__;
    }

    /**
     * <p>set BAC_PUB_EDIT value
     * @param bacPubEdit BAC_PUB_EDIT value
     */
    public void setBacPubEdit(int bacPubEdit) {
        bacPubEdit__ = bacPubEdit;
    }

    /**
     * <p>get BAC_GRP_EDIT value
     * @return BAC_GRP_EDIT value
     */
    public int getBacGrpEdit() {
        return bacGrpEdit__;
    }

    /**
     * <p>set BAC_GRP_EDIT value
     * @param bacGrpEdit BAC_GRP_EDIT value
     */
    public void setBacGrpEdit(int bacGrpEdit) {
        bacGrpEdit__ = bacGrpEdit;
    }

    /**
     * <p>get BAC_LIMIT value
     * @return BAC_LIMIT value
     */
    public int getBacLimit() {
        return bacLimit__;
    }

    /**
     * <p>set BAC_LIMIT value
     * @param bacGrpEdit BAC_LIMIT value
     */
    public void setBacLimit(int bacLimit) {
        bacLimit__ = bacLimit;
    }

    /**
     * <p>get BAC_AUID value
     * @return BAC_AUID value
     */
    public int getBacAuid() {
        return bacAuid__;
    }

    /**
     * <p>set BAC_AUID value
     * @param bacAuid BAC_AUID value
     */
    public void setBacAuid(int bacAuid) {
        bacAuid__ = bacAuid;
    }

    /**
     * <p>get BAC_ADATE value
     * @return BAC_ADATE value
     */
    public UDate getBacAdate() {
        return bacAdate__;
    }

    /**
     * <p>set BAC_ADATE value
     * @param bacAdate BAC_ADATE value
     */
    public void setBacAdate(UDate bacAdate) {
        bacAdate__ = bacAdate;
    }

    /**
     * <p>get BAC_EUID value
     * @return BAC_EUID value
     */
    public int getBacEuid() {
        return bacEuid__;
    }

    /**
     * <p>set BAC_EUID value
     * @param bacEuid BAC_EUID value
     */
    public void setBacEuid(int bacEuid) {
        bacEuid__ = bacEuid;
    }

    /**
     * <p>get BAC_EDATE value
     * @return BAC_EDATE value
     */
    public UDate getBacEdate() {
        return bacEdate__;
    }

    /**
     * <p>set BAC_EDATE value
     * @param bacEdate BAC_EDATE value
     */
    public void setBacEdate(UDate bacEdate) {
        bacEdate__ = bacEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(bacPubEdit__);
        buf.append(",");
        buf.append(bacGrpEdit__);
        buf.append(",");
        buf.append(bacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bacAdate__, ""));
        buf.append(",");
        buf.append(bacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bacEdate__, ""));
        return buf.toString();
    }

}
