package jp.groupsession.v2.tcd.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>TCD_HOLIDAY_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdHolidayInfModel implements Serializable {

    /** THI_SID mapping */
    private int thiSid__;
    /** THI_NAME mapping */
    private String thiName__;
    /** THI_HOLTOTAL_KBN mapping */
    private int thiHoltotalKbn__;
    /** THI_LIMIT mapping */
    private int thiLimit__;
    /** THI_ORDER mapping */
    private int thiOrder__;
    /** THI_AUID mapping */
    private int thiAuid__;
    /** THI_ADATE mapping */
    private UDate thiAdate__;
    /** THI_EUID mapping */
    private int thiEuid__;
    /** THI_EDATE mapping */
    private UDate thiEdate__;
    /** THI_CONTENT_KBN mapping */
    private int thiContentKbn__;

    /**
     * <p>Default Constructor
     */
    public TcdHolidayInfModel() {
    }

    /**
     * <p>get THI_SID value
     * @return THI_SID value
     */
    public int getThiSid() {
        return thiSid__;
    }

    /**
     * <p>set THI_SID value
     * @param thiSid THI_SID value
     */
    public void setThiSid(int thiSid) {
        thiSid__ = thiSid;
    }

    /**
     * <p>get THI_NAME value
     * @return THI_NAME value
     */
    public String getThiName() {
        return thiName__;
    }

    /**
     * <p>set THI_NAME value
     * @param thiName THI_NAME value
     */
    public void setThiName(String thiName) {
        thiName__ = thiName;
    }

    /**
     * <p>get THI_HOLTOTAL_KBN value
     * @return THI_HOLTOTAL_KBN value
     */
    public int getThiHoltotalKbn() {
        return thiHoltotalKbn__;
    }

    /**
     * <p>set THI_HOLTOTAL_KBN value
     * @param thiHoltotalKbn THI_HOLTOTAL_KBN value
     */
    public void setThiHoltotalKbn(int thiHoltotalKbn) {
        thiHoltotalKbn__ = thiHoltotalKbn;
    }

    /**
     * <p>get THI_LIMIT value
     * @return THI_LIMIT value
     */
    public int getThiLimit() {
        return thiLimit__;
    }

    /**
     * <p>set THI_LIMIT value
     * @param thiLimit THI_LIMIT value
     */
    public void setThiLimit(int thiLimit) {
        thiLimit__ = thiLimit;
    }

    /**
     * <p>get THI_ORDER value
     * @return THI_ORDER value
     */
    public int getThiOrder() {
        return thiOrder__;
    }

    /**
     * <p>set THI_ORDER value
     * @param thiOrder THI_ORDER value
     */
    public void setThiOrder(int thiOrder) {
        thiOrder__ = thiOrder;
    }

    /**
     * <p>get THI_AUID value
     * @return THI_AUID value
     */
    public int getThiAuid() {
        return thiAuid__;
    }

    /**
     * <p>set THI_AUID value
     * @param thiAuid THI_AUID value
     */
    public void setThiAuid(int thiAuid) {
        thiAuid__ = thiAuid;
    }

    /**
     * <p>get THI_ADATE value
     * @return THI_ADATE value
     */
    public UDate getThiAdate() {
        return thiAdate__;
    }

    /**
     * <p>set THI_ADATE value
     * @param thiAdate THI_ADATE value
     */
    public void setThiAdate(UDate thiAdate) {
        thiAdate__ = thiAdate;
    }

    /**
     * <p>get THI_EUID value
     * @return THI_EUID value
     */
    public int getThiEuid() {
        return thiEuid__;
    }

    /**
     * <p>set THI_EUID value
     * @param thiEuid THI_EUID value
     */
    public void setThiEuid(int thiEuid) {
        thiEuid__ = thiEuid;
    }

    /**
     * <p>get THI_EDATE value
     * @return THI_EDATE value
     */
    public UDate getThiEdate() {
        return thiEdate__;
    }

    /**
     * <p>set THI_EDATE value
     * @param thiEdate THI_EDATE value
     */
    public void setThiEdate(UDate thiEdate) {
        thiEdate__ = thiEdate;
    }

    /**
     * <p>thiContentKbn を取得します。
     * @return thiContentKbn
     * @see jp.groupsession.v2.tcd.model.TcdHolidayInfModel#thiContentKbn__
     */
    public int getThiContentKbn() {
        return thiContentKbn__;
    }

    /**
     * <p>thiContentKbn をセットします。
     * @param thiContentKbn thiContentKbn
     * @see jp.groupsession.v2.tcd.model.TcdHolidayInfModel#thiContentKbn__
     */
    public void setThiContentKbn(int thiContentKbn) {
        thiContentKbn__ = thiContentKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(thiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(thiName__, ""));
        buf.append(",");
        buf.append(thiHoltotalKbn__);
        buf.append(",");
        buf.append(thiLimit__);
        buf.append(",");
        buf.append(thiOrder__);
        buf.append(",");
        buf.append(thiContentKbn__);
        buf.append(",");
        buf.append(thiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(thiAdate__, ""));
        buf.append(",");
        buf.append(thiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(thiEdate__, ""));
        return buf.toString();
    }

}
