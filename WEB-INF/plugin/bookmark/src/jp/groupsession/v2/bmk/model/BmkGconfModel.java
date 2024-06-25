package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_GCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkGconfModel implements Serializable {

    /** GRP_SID mapping */
    private int grpSid__;
    /** BGC_EDIT mapping */
    private int bgcEdit__;
    /** BGC_AUID mapping */
    private int bgcAuid__;
    /** BGC_ADATE mapping */
    private UDate bgcAdate__;
    /** BGC_EUID mapping */
    private int bgcEuid__;
    /** BGC_EDATE mapping */
    private UDate bgcEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkGconfModel() {
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get BGC_EDIT value
     * @return BGC_EDIT value
     */
    public int getBgcEdit() {
        return bgcEdit__;
    }

    /**
     * <p>set BGC_EDIT value
     * @param bgcEdit BGC_EDIT value
     */
    public void setBgcEdit(int bgcEdit) {
        bgcEdit__ = bgcEdit;
    }

    /**
     * <p>get BGC_AUID value
     * @return BGC_AUID value
     */
    public int getBgcAuid() {
        return bgcAuid__;
    }

    /**
     * <p>set BGC_AUID value
     * @param bgcAuid BGC_AUID value
     */
    public void setBgcAuid(int bgcAuid) {
        bgcAuid__ = bgcAuid;
    }

    /**
     * <p>get BGC_ADATE value
     * @return BGC_ADATE value
     */
    public UDate getBgcAdate() {
        return bgcAdate__;
    }

    /**
     * <p>set BGC_ADATE value
     * @param bgcAdate BGC_ADATE value
     */
    public void setBgcAdate(UDate bgcAdate) {
        bgcAdate__ = bgcAdate;
    }

    /**
     * <p>get BGC_EUID value
     * @return BGC_EUID value
     */
    public int getBgcEuid() {
        return bgcEuid__;
    }

    /**
     * <p>set BGC_EUID value
     * @param bgcEuid BGC_EUID value
     */
    public void setBgcEuid(int bgcEuid) {
        bgcEuid__ = bgcEuid;
    }

    /**
     * <p>get BGC_EDATE value
     * @return BGC_EDATE value
     */
    public UDate getBgcEdate() {
        return bgcEdate__;
    }

    /**
     * <p>set BGC_EDATE value
     * @param bgcEdate BGC_EDATE value
     */
    public void setBgcEdate(UDate bgcEdate) {
        bgcEdate__ = bgcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(grpSid__);
        buf.append(",");
        buf.append(bgcEdit__);
        buf.append(",");
        buf.append(bgcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bgcAdate__, ""));
        buf.append(",");
        buf.append(bgcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bgcEdate__, ""));
        return buf.toString();
    }

}
