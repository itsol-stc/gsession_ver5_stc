package jp.groupsession.v2.zsk.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ZAI_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiAdmConfModel implements Serializable {

    /** ZAC_NAISEN_KBN mapping */
    private int zacNaisenKbn__;
    /** ZAC_AID mapping */
    private int zacAid__;
    /** ZAC_ADATE mapping */
    private UDate zacAdate__;
    /** ZAC_EID mapping */
    private int zacEid__;
    /** ZAC_EDATE mapping */
    private UDate zacEdate__;
    /** ZAC_SORT_KBN mapping */
    private int zacSortKbn__;
    /** ZAC_SORT_KEY1 mapping */
    private int zacSortKey1__;
    /** ZAC_SORT_ORDER1 mapping */
    private int zacSortOrder1__;
    /** ZAC_SORT_KEY2 mapping */
    private int zacSortKey2__;
    /** ZAC_SORT_ORDER2 mapping */
    private int zacSortOrder2__;

    /**
     * <p>Default Constructor
     */
    public ZaiAdmConfModel() {
    }

    /**
     * <p>get ZAC_NAISEN_KBN value
     * @return ZAC_NAISEN_KBN value
     */
    public int getZacNaisenKbn() {
        return zacNaisenKbn__;
    }

    /**
     * <p>set ZAC_NAISEN_KBN value
     * @param zacNaisenKbn ZAC_NAISEN_KBN value
     */
    public void setZacNaisenKbn(int zacNaisenKbn) {
        zacNaisenKbn__ = zacNaisenKbn;
    }

    /**
     * <p>get ZAC_AID value
     * @return ZAC_AID value
     */
    public int getZacAid() {
        return zacAid__;
    }

    /**
     * <p>set ZAC_AID value
     * @param zacAid ZAC_AID value
     */
    public void setZacAid(int zacAid) {
        zacAid__ = zacAid;
    }

    /**
     * <p>get ZAC_ADATE value
     * @return ZAC_ADATE value
     */
    public UDate getZacAdate() {
        return zacAdate__;
    }

    /**
     * <p>set ZAC_ADATE value
     * @param zacAdate ZAC_ADATE value
     */
    public void setZacAdate(UDate zacAdate) {
        zacAdate__ = zacAdate;
    }

    /**
     * <p>get ZAC_EID value
     * @return ZAC_EID value
     */
    public int getZacEid() {
        return zacEid__;
    }

    /**
     * <p>set ZAC_EID value
     * @param zacEid ZAC_EID value
     */
    public void setZacEid(int zacEid) {
        zacEid__ = zacEid;
    }

    /**
     * <p>get ZAC_EDATE value
     * @return ZAC_EDATE value
     */
    public UDate getZacEdate() {
        return zacEdate__;
    }

    /**
     * <p>set ZAC_EDATE value
     * @param zacEdate ZAC_EDATE value
     */
    public void setZacEdate(UDate zacEdate) {
        zacEdate__ = zacEdate;
    }

    /**
     * <p>get ZAC_SORT_KBN value
     * @return ZAC_SORT_KBN value
     */
    public int getZacSortKbn() {
        return zacSortKbn__;
    }

    /**
     * <p>set ZAC_SORT_KBN value
     * @param zacSortKbn ZAC_SORT_KBN value
     */
    public void setZacSortKbn(int zacSortKbn) {
        zacSortKbn__ = zacSortKbn;
    }

    /**
     * <p>get ZAC_SORT_KEY1 value
     * @return ZAC_SORT_KEY1 value
     */
    public int getZacSortKey1() {
        return zacSortKey1__;
    }

    /**
     * <p>set ZAC_SORT_KEY1 value
     * @param zacSortKey1 ZAC_SORT_KEY1 value
     */
    public void setZacSortKey1(int zacSortKey1) {
        zacSortKey1__ = zacSortKey1;
    }

    /**
     * <p>get ZAC_SORT_ORDER1 value
     * @return ZAC_SORT_ORDER1 value
     */
    public int getZacSortOrder1() {
        return zacSortOrder1__;
    }

    /**
     * <p>set ZAC_SORT_ORDER1 value
     * @param zacSortOrder1 ZAC_SORT_ORDER1 value
     */
    public void setZacSortOrder1(int zacSortOrder1) {
        zacSortOrder1__ = zacSortOrder1;
    }

    /**
     * <p>get ZAC_SORT_KEY2 value
     * @return ZAC_SORT_KEY2 value
     */
    public int getZacSortKey2() {
        return zacSortKey2__;
    }

    /**
     * <p>set ZAC_SORT_KEY2 value
     * @param zacSortKey2 ZAC_SORT_KEY2 value
     */
    public void setZacSortKey2(int zacSortKey2) {
        zacSortKey2__ = zacSortKey2;
    }

    /**
     * <p>get ZAC_SORT_ORDER2 value
     * @return ZAC_SORT_ORDER2 value
     */
    public int getZacSortOrder2() {
        return zacSortOrder2__;
    }

    /**
     * <p>set ZAC_SORT_ORDER2 value
     * @param zacSortOrder2 ZAC_SORT_ORDER2 value
     */
    public void setZacSortOrder2(int zacSortOrder2) {
        zacSortOrder2__ = zacSortOrder2;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(zacNaisenKbn__);
        buf.append(",");
        buf.append(zacAid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zacAdate__, ""));
        buf.append(",");
        buf.append(zacEid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zacEdate__, ""));
        buf.append(",");
        buf.append(zacSortKbn__);
        buf.append(",");
        buf.append(zacSortKey1__);
        buf.append(",");
        buf.append(zacSortOrder1__);
        buf.append(",");
        buf.append(zacSortKey2__);
        buf.append(",");
        buf.append(zacSortOrder2__);
        return buf.toString();
    }

}
