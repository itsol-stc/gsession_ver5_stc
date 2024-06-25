package jp.groupsession.v2.mem.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>MEMO_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoAdmConfModel implements Serializable {

    /** MAC_ATDEL_KBN mapping */
    private int macAtdelKbn__;
    /** MAC_ATDEL_Y mapping */
    private int macAtdelY__;
    /** MAC_ATDEL_M mapping */
    private int macAtdelM__;
    /** MAC_AUID mapping */
    private int macAuid__;
    /** MAC_ADATE mapping */
    private UDate macAdate__;
    /** MAC_EUID mapping */
    private int macEuid__;
    /** MAC_EDATE mapping */
    private UDate macEdate__;

    /**
     * <p>Default Constructor
     */
    public MemoAdmConfModel() {
    }

    /**
     * <p>get MAC_ATDEL_KBN value
     * @return MAC_ATDEL_KBN value
     */
    public int getMacAtdelKbn() {
        return macAtdelKbn__;
    }

    /**
     * <p>set MAC_ATDEL_KBN value
     * @param macAtdelKbn MAC_ATDEL_KBN value
     */
    public void setMacAtdelKbn(int macAtdelKbn) {
        macAtdelKbn__ = macAtdelKbn;
    }

    /**
     * <p>get MAC_ATDEL_Y value
     * @return MAC_ATDEL_Y value
     */
    public int getMacAtdelY() {
        return macAtdelY__;
    }

    /**
     * <p>set MAC_ATDEL_Y value
     * @param macAtdelY MAC_ATDEL_Y value
     */
    public void setMacAtdelY(int macAtdelY) {
        macAtdelY__ = macAtdelY;
    }

    /**
     * <p>get MAC_ATDEL_M value
     * @return MAC_ATDEL_M value
     */
    public int getMacAtdelM() {
        return macAtdelM__;
    }

    /**
     * <p>set MAC_ATDEL_M value
     * @param macAtdelM MAC_ATDEL_M value
     */
    public void setMacAtdelM(int macAtdelM) {
        macAtdelM__ = macAtdelM;
    }

    /**
     * <p>get MAC_AUID value
     * @return MAC_AUID value
     */
    public int getMacAuid() {
        return macAuid__;
    }

    /**
     * <p>set MAC_AUID value
     * @param macAuid MAC_AUID value
     */
    public void setMacAuid(int macAuid) {
        macAuid__ = macAuid;
    }

    /**
     * <p>get MAC_ADATE value
     * @return MAC_ADATE value
     */
    public UDate getMacAdate() {
        return macAdate__;
    }

    /**
     * <p>set MAC_ADATE value
     * @param macAdate MAC_ADATE value
     */
    public void setMacAdate(UDate macAdate) {
        macAdate__ = macAdate;
    }

    /**
     * <p>get MAC_EUID value
     * @return MAC_EUID value
     */
    public int getMacEuid() {
        return macEuid__;
    }

    /**
     * <p>set MAC_EUID value
     * @param macEuid MAC_EUID value
     */
    public void setMacEuid(int macEuid) {
        macEuid__ = macEuid;
    }

    /**
     * <p>get MAC_EDATE value
     * @return MAC_EDATE value
     */
    public UDate getMacEdate() {
        return macEdate__;
    }

    /**
     * <p>set MAC_EDATE value
     * @param macEdate MAC_EDATE value
     */
    public void setMacEdate(UDate macEdate) {
        macEdate__ = macEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(macAtdelKbn__);
        buf.append(",");
        buf.append(macAtdelY__);
        buf.append(",");
        buf.append(macAtdelM__);
        buf.append(",");
        buf.append(macAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(macAdate__, ""));
        buf.append(",");
        buf.append(macEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(macEdate__, ""));
        return buf.toString();
    }

}
