package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjAdmConfModel implements Serializable {

    /** PAC_PRJ_EDIT mapping */
    private int pacPrjEdit__;
    /** PAC_AUID mapping */
    private int pacAuid__;
    /** PAC_ADATE mapping */
    private UDate pacAdate__;
    /** PAC_EUID mapping */
    private int pacEuid__;
    /** PAC_EDATE mapping */
    private UDate pacEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjAdmConfModel() {
    }

    /**
     * <p>get PAC_PRJ_EDIT value
     * @return PAC_PRJ_EDIT value
     */
    public int getPacPrjEdit() {
        return pacPrjEdit__;
    }

    /**
     * <p>set PAC_PRJ_EDIT value
     * @param pacPrjEdit PAC_PRJ_EDIT value
     */
    public void setPacPrjEdit(int pacPrjEdit) {
        pacPrjEdit__ = pacPrjEdit;
    }

    /**
     * <p>get PAC_AUID value
     * @return PAC_AUID value
     */
    public int getPacAuid() {
        return pacAuid__;
    }

    /**
     * <p>set PAC_AUID value
     * @param pacAuid PAC_AUID value
     */
    public void setPacAuid(int pacAuid) {
        pacAuid__ = pacAuid;
    }

    /**
     * <p>get PAC_ADATE value
     * @return PAC_ADATE value
     */
    public UDate getPacAdate() {
        return pacAdate__;
    }

    /**
     * <p>set PAC_ADATE value
     * @param pacAdate PAC_ADATE value
     */
    public void setPacAdate(UDate pacAdate) {
        pacAdate__ = pacAdate;
    }

    /**
     * <p>get PAC_EUID value
     * @return PAC_EUID value
     */
    public int getPacEuid() {
        return pacEuid__;
    }

    /**
     * <p>set PAC_EUID value
     * @param pacEuid PAC_EUID value
     */
    public void setPacEuid(int pacEuid) {
        pacEuid__ = pacEuid;
    }

    /**
     * <p>get PAC_EDATE value
     * @return PAC_EDATE value
     */
    public UDate getPacEdate() {
        return pacEdate__;
    }

    /**
     * <p>set PAC_EDATE value
     * @param pacEdate PAC_EDATE value
     */
    public void setPacEdate(UDate pacEdate) {
        pacEdate__ = pacEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(pacPrjEdit__);
        buf.append(",");
        buf.append(pacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pacAdate__, ""));
        buf.append(",");
        buf.append(pacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pacEdate__, ""));
        return buf.toString();
    }

}
