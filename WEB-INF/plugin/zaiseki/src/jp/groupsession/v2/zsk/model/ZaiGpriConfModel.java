package jp.groupsession.v2.zsk.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ZAI_GPRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiGpriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** ZGC_GRP mapping */
    private int zgcGrp__;
    /** ZGC_GKBN mapping */
    private int zgcGkbn__;
    /** ZSC_VIEW_KBN mapping */
    private int zgcViewKbn__;
    /** ZGC_SORT_KEY1 mapping */
    private int zgcSortKey1__;
    /** ZGC_SORT_ORDER1 mapping */
    private int zgcSortOrder1__;
    /** ZGC_SORT_KEY2 mapping */
    private int zgcSortKey2__;
    /** ZGC_SORT_ORDER2 mapping */
    private int zgcSortOrder2__;
    /** ZSC_SCH_VIEW_DF mapping */
    private int zgcSchViewDf__;
    /** ZGC_AUID mapping */
    private int zgcAuid__;
    /** ZGC_ADATE mapping */
    private UDate zgcAdate__;
    /** ZGC_EUID mapping */
    private int zgcEuid__;
    /** ZGC_EDATE mapping */
    private UDate zgcEdate__;

    /**
     * <p>Default Constructor
     */
    public ZaiGpriConfModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get ZGC_GRP value
     * @return ZGC_GRP value
     */
    public int getZgcGrp() {
        return zgcGrp__;
    }

    /**
     * <p>set ZGC_GRP value
     * @param zgcGrp ZGC_GRP value
     */
    public void setZgcGrp(int zgcGrp) {
        zgcGrp__ = zgcGrp;
    }

    /**
     * <p>get ZGC_GKBN value
     * @return ZGC_GKBN value
     */
    public int getZgcGkbn() {
        return zgcGkbn__;
    }

    /**
     * <p>set ZGC_GKBN value
     * @param zgcGkbn ZGC_GKBN value
     */
    public void setZgcGkbn(int zgcGkbn) {
        zgcGkbn__ = zgcGkbn;
    }

    /**
     * <p>get ZGC_AUID value
     * @return ZGC_AUID value
     */
    public int getZgcAuid() {
        return zgcAuid__;
    }

    /**
     * <p>set ZGC_AUID value
     * @param zgcAuid ZGC_AUID value
     */
    public void setZgcAuid(int zgcAuid) {
        zgcAuid__ = zgcAuid;
    }

    /**
     * <p>get ZGC_ADATE value
     * @return ZGC_ADATE value
     */
    public UDate getZgcAdate() {
        return zgcAdate__;
    }

    /**
     * <p>set ZGC_ADATE value
     * @param zgcAdate ZGC_ADATE value
     */
    public void setZgcAdate(UDate zgcAdate) {
        zgcAdate__ = zgcAdate;
    }

    /**
     * <p>get ZGC_EUID value
     * @return ZGC_EUID value
     */
    public int getZgcEuid() {
        return zgcEuid__;
    }

    /**
     * <p>set ZGC_EUID value
     * @param zgcEuid ZGC_EUID value
     */
    public void setZgcEuid(int zgcEuid) {
        zgcEuid__ = zgcEuid;
    }

    /**
     * <p>get ZGC_EDATE value
     * @return ZGC_EDATE value
     */
    public UDate getZgcEdate() {
        return zgcEdate__;
    }

    /**
     * <p>set ZGC_EDATE value
     * @param zgcEdate ZGC_EDATE value
     */
    public void setZgcEdate(UDate zgcEdate) {
        zgcEdate__ = zgcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(zgcGrp__);
        buf.append(",");
        buf.append(zgcGkbn__);
        buf.append(",");
        buf.append(zgcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zgcAdate__, ""));
        buf.append(",");
        buf.append(zgcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zgcEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>zgcSortKey1 を取得します。
     * @return zgcSortKey1
     */
    public int getZgcSortKey1() {
        return zgcSortKey1__;
    }

    /**
     * <p>zgcSortKey1 をセットします。
     * @param zgcSortKey1 zgcSortKey1
     */
    public void setZgcSortKey1(int zgcSortKey1) {
        zgcSortKey1__ = zgcSortKey1;
    }

    /**
     * <p>zgcSortKey2 を取得します。
     * @return zgcSortKey2
     */
    public int getZgcSortKey2() {
        return zgcSortKey2__;
    }

    /**
     * <p>zgcSortKey2 をセットします。
     * @param zgcSortKey2 zgcSortKey2
     */
    public void setZgcSortKey2(int zgcSortKey2) {
        zgcSortKey2__ = zgcSortKey2;
    }

    /**
     * <p>zgcSortOrder1 を取得します。
     * @return zgcSortOrder1
     */
    public int getZgcSortOrder1() {
        return zgcSortOrder1__;
    }

    /**
     * <p>zgcSortOrder1 をセットします。
     * @param zgcSortOrder1 zgcSortOrder1
     */
    public void setZgcSortOrder1(int zgcSortOrder1) {
        zgcSortOrder1__ = zgcSortOrder1;
    }

    /**
     * <p>zgcSortOrder2 を取得します。
     * @return zgcSortOrder2
     */
    public int getZgcSortOrder2() {
        return zgcSortOrder2__;
    }

    /**
     * <p>zgcSortOrder2 をセットします。
     * @param zgcSortOrder2 zgcSortOrder2
     */
    public void setZgcSortOrder2(int zgcSortOrder2) {
        zgcSortOrder2__ = zgcSortOrder2;
    }

    /**
     * <p>zgcSchViewDf を取得します。
     * @return zgcSchViewDf
     */
    public int getZgcSchViewDf() {
        return zgcSchViewDf__;
    }

    /**
     * <p>zgcSchViewDf をセットします。
     * @param zgcSchViewDf zgcSchViewDf
     */
    public void setZgcSchViewDf(int zgcSchViewDf) {
        zgcSchViewDf__ = zgcSchViewDf;
    }

    /**
     * <p>zgcViewKbn を取得します。
     * @return zgcViewKbn
     */
    public int getZgcViewKbn() {
        return zgcViewKbn__;
    }

    /**
     * <p>zgcViewKbn をセットします。
     * @param zgcViewKbn zgcViewKbn
     */
    public void setZgcViewKbn(int zgcViewKbn) {
        zgcViewKbn__ = zgcViewKbn;
    }

}
