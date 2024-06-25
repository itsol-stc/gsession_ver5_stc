package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpAdmConfModel implements Serializable {

    /** NAC_CRANGE mapping */
    private int nacCrange__;
    /** NAC_ATDEL_FLG mapping */
    private int nacAtdelFlg__;
    /** NAC_ATDEL_Y mapping */
    private int nacAtdelY__;
    /** NAC_ATDEL_M mapping */
    private int nacAtdelM__;
    /** NAC_HOUR_DIV mapping */
    private int nacHourDiv__;
    /** NAC_SML_KBN mapping */
    private int nacSmlKbn__;
    /** NAC_HOUR_DIV mapping */
    private int nacSmlNoticeKbn__;
    /** NAC_HOUR_DIV mapping */
    private int nacSmlNoticeGrp__;
    /** NAC_CSML_KBN mapping */
    private int nacCsmlKbn__;
    /** NAC_CSML_NOTICE_KBN mapping */
    private int nacCsmlNoticeKbn__;
    /** NAC_GSML_KBN mapping */
    private int nacGsmlKbn__;
    /** NAC_GSML_NOTICE_KBN mapping */
    private int nacGsmlNoticeKbn__;
    /** NAC_KAKUTEI_FLG mapping */
    private int nacKakuteiFlg__;
    /** NAC_AUID mapping */
    private int nacAuid__;
    /** NAC_ADATE mapping */
    private UDate nacAdate__;
    /** NAC_EUID mapping */
    private int nacEuid__;
    /** NAC_EDATE mapping */
    private UDate nacEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpAdmConfModel() {
    }

    /**
     * <p>get NAC_CRANGE value
     * @return NAC_CRANGE value
     */
    public int getNacCrange() {
        return nacCrange__;
    }

    /**
     * <p>set NAC_CRANGE value
     * @param nacCrange NAC_CRANGE value
     */
    public void setNacCrange(int nacCrange) {
        nacCrange__ = nacCrange;
    }

    /**
     * <p>get NAC_ATDEL_FLG value
     * @return NAC_ATDEL_FLG value
     */
    public int getNacAtdelFlg() {
        return nacAtdelFlg__;
    }

    /**
     * <p>set NAC_ATDEL_FLG value
     * @param nacAtdelFlg NAC_ATDEL_FLG value
     */
    public void setNacAtdelFlg(int nacAtdelFlg) {
        nacAtdelFlg__ = nacAtdelFlg;
    }

    /**
     * <p>get NAC_ATDEL_Y value
     * @return NAC_ATDEL_Y value
     */
    public int getNacAtdelY() {
        return nacAtdelY__;
    }

    /**
     * <p>set NAC_ATDEL_Y value
     * @param nacAtdelY NAC_ATDEL_Y value
     */
    public void setNacAtdelY(int nacAtdelY) {
        nacAtdelY__ = nacAtdelY;
    }

    /**
     * <p>get NAC_ATDEL_M value
     * @return NAC_ATDEL_M value
     */
    public int getNacAtdelM() {
        return nacAtdelM__;
    }

    /**
     * <p>set NAC_ATDEL_M value
     * @param nacAtdelM NAC_ATDEL_M value
     */
    public void setNacAtdelM(int nacAtdelM) {
        nacAtdelM__ = nacAtdelM;
    }

    /**
     * <p>get NAC_HOUR_DIV value
     * @return NAC_HOUR_DIV value
     */
    public int getNacHourDiv() {
        return nacHourDiv__;
    }

    /**
     * <p>set NAC_HOUR_DIV value
     * @param nacHourDiv NAC_HOUR_DIV value
     */
    public void setNacHourDiv(int nacHourDiv) {
        nacHourDiv__ = nacHourDiv;
    }

    /**
     * <p>get NAC_KAKUTEI_FLG value
     * @return NAC_KAKUTEI_FLG value
     */
    public int getNacKakuteiFlg() {
        return nacKakuteiFlg__;
    }

    /**
     * <p>set NAC_KAKUTEI_FLG value
     * @param nacKakuteiFlg NAC_KAKUTEI_FLG value
     */
    public void setNacKakuteiFlg(int nacKakuteiFlg) {
        nacKakuteiFlg__ = nacKakuteiFlg;
    }

    /**
     * <p>get NAC_AUID value
     * @return NAC_AUID value
     */
    public int getNacAuid() {
        return nacAuid__;
    }

    /**
     * <p>set NAC_AUID value
     * @param nacAuid NAC_AUID value
     */
    public void setNacAuid(int nacAuid) {
        nacAuid__ = nacAuid;
    }

    /**
     * <p>get NAC_ADATE value
     * @return NAC_ADATE value
     */
    public UDate getNacAdate() {
        return nacAdate__;
    }

    /**
     * <p>set NAC_ADATE value
     * @param nacAdate NAC_ADATE value
     */
    public void setNacAdate(UDate nacAdate) {
        nacAdate__ = nacAdate;
    }

    /**
     * <p>get NAC_EUID value
     * @return NAC_EUID value
     */
    public int getNacEuid() {
        return nacEuid__;
    }

    /**
     * <p>set NAC_EUID value
     * @param nacEuid NAC_EUID value
     */
    public void setNacEuid(int nacEuid) {
        nacEuid__ = nacEuid;
    }

    /**
     * <p>get NAC_EDATE value
     * @return NAC_EDATE value
     */
    public UDate getNacEdate() {
        return nacEdate__;
    }

    /**
     * <p>set NAC_EDATE value
     * @param nacEdate NAC_EDATE value
     */
    public void setNacEdate(UDate nacEdate) {
        nacEdate__ = nacEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nacCrange__);
        buf.append(",");
        buf.append(nacAtdelFlg__);
        buf.append(",");
        buf.append(nacAtdelY__);
        buf.append(",");
        buf.append(nacAtdelM__);
        buf.append(",");
        buf.append(nacHourDiv__);
        buf.append(",");
        buf.append(nacKakuteiFlg__);
        buf.append(",");
        buf.append(nacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nacAdate__, ""));
        buf.append(",");
        buf.append(nacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nacEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nacSmlKbn を取得します。
     * @return nacSmlKbn
     */
    public int getNacSmlKbn() {
        return nacSmlKbn__;
    }

    /**
     * <p>nacSmlKbn をセットします。
     * @param nacSmlKbn nacSmlKbn
     */
    public void setNacSmlKbn(int nacSmlKbn) {
        nacSmlKbn__ = nacSmlKbn;
    }

    /**
     * <p>nacSmlNoticeKbn を取得します。
     * @return nacSmlNoticeKbn
     */
    public int getNacSmlNoticeKbn() {
        return nacSmlNoticeKbn__;
    }

    /**
     * <p>nacSmlNoticeKbn をセットします。
     * @param nacSmlNoticeKbn nacSmlNoticeKbn
     */
    public void setNacSmlNoticeKbn(int nacSmlNoticeKbn) {
        nacSmlNoticeKbn__ = nacSmlNoticeKbn;
    }

    /**
     * <p>nacSmlNoticeGrp を取得します。
     * @return nacSmlNoticeGrp
     */
    public int getNacSmlNoticeGrp() {
        return nacSmlNoticeGrp__;
    }

    /**
     * <p>nacSmlNoticeGrp をセットします。
     * @param nacSmlNoticeGrp nacSmlNoticeGrp
     */
    public void setNacSmlNoticeGrp(int nacSmlNoticeGrp) {
        nacSmlNoticeGrp__ = nacSmlNoticeGrp;
    }

    /**
     * <p>nacCsmlKbn を取得します。
     * @return nacCsmlKbn
     */
    public int getNacCsmlKbn() {
        return nacCsmlKbn__;
    }

    /**
     * <p>nacCsmlKbn をセットします。
     * @param nacCsmlKbn nacCsmlKbn
     */
    public void setNacCsmlKbn(int nacCsmlKbn) {
        nacCsmlKbn__ = nacCsmlKbn;
    }

    /**
     * <p>nacCsmlNoticeKbn を取得します。
     * @return nacCsmlNoticeKbn
     */
    public int getNacCsmlNoticeKbn() {
        return nacCsmlNoticeKbn__;
    }

    /**
     * <p>nacCsmlNoticeKbn をセットします。
     * @param nacCsmlNoticeKbn nacCsmlNoticeKbn
     */
    public void setNacCsmlNoticeKbn(int nacCsmlNoticeKbn) {
        nacCsmlNoticeKbn__ = nacCsmlNoticeKbn;
    }

    /**
     * <p>nacGsmlKbn を取得します。
     * @return nacGsmlKbn
     */
    public int getNacGsmlKbn() {
        return nacGsmlKbn__;
    }

    /**
     * <p>nacGsmlKbn をセットします。
     * @param nacGsmlKbn nacGsmlKbn
     */
    public void setNacGsmlKbn(int nacGsmlKbn) {
        nacGsmlKbn__ = nacGsmlKbn;
    }

    /**
     * <p>nacGsmlNoticeKbn を取得します。
     * @return nacGsmlNoticeKbn
     */
    public int getNacGsmlNoticeKbn() {
        return nacGsmlNoticeKbn__;
    }

    /**
     * <p>nacGsmlNoticeKbn をセットします。
     * @param nacGsmlNoticeKbn nacGsmlNoticeKbn
     */
    public void setNacGsmlNoticeKbn(int nacGsmlNoticeKbn) {
        nacGsmlNoticeKbn__ = nacGsmlNoticeKbn;
    }

}
