package jp.groupsession.v2.fil.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_DIRECTORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDirectoryModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** FDR_VERSION mapping */
    private int fdrVersion__;
    /** FCB_SID mapping */
    private int fcbSid__;
    /** FDR_PARENT_SID mapping */
    private int fdrParentSid__;
    /** FDR_KBN mapping */
    private int fdrKbn__;
    /** FDR_VER_KBN mapping */
    private int fdrVerKbn__;
    /** FDR_LEVEL mapping */
    private int fdrLevel__;
    /** FDR_NAME mapping */
    private String fdrName__;
    /** FDR_BIKO mapping */
    private String fdrBiko__;
    /** FDR_JTKBN mapping */
    private int fdrJtkbn__;
    /** FDR_AUID mapping */
    private int fdrAuid__;
    /** FDR_ADATE mapping */
    private UDate fdrAdate__;
    /** FDR_EUID mapping */
    private int fdrEuid__;
    /** FDR_EDATE mapping */
    private UDate fdrEdate__;
    /** FDR_EGID mapping */
    private int fdrEgid__;

    /** FCB_MARK mapping */
    private long fcbMark__;

    /** BIN_SID mapping */
    private Long binSid__;
    /** 更新者名 */
    private String editUsrName__;
    /** 更新者状態区分 */
    private String editUsrJkbn__;
    /** アクセス設定SID */
    private int fdrAccessSid__;
    /** FDR_TRADE_DATE mapping */
    private UDate fdrTradeDate__;
    /** FDR_TRADE_TARGET mapping */
    private String fdrTradeTarget__;
    /** FDR_TRADE_MONEYKBN mapping */
    private int fdrTradeMoneykbn__;
    /** FDR_TRADE_MONEY mapping */
    private BigDecimal fdrTradeMoney__;
    /** EMT_SID mapping */
    private int emtSid__;

    /**
     * <p>Default Constructor
     */
    public FileDirectoryModel() {
    }

    /**
     * <p>get FDR_SID value
     * @return FDR_SID value
     */
    public int getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>set FDR_SID value
     * @param fdrSid FDR_SID value
     */
    public void setFdrSid(int fdrSid) {
        fdrSid__ = fdrSid;
    }

    /**
     * <p>get FDR_VERSION value
     * @return FDR_VERSION value
     */
    public int getFdrVersion() {
        return fdrVersion__;
    }

    /**
     * <p>set FDR_VERSION value
     * @param fdrVersion FDR_VERSION value
     */
    public void setFdrVersion(int fdrVersion) {
        fdrVersion__ = fdrVersion;
    }

    /**
     * <p>get FCB_SID value
     * @return FCB_SID value
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>set FCB_SID value
     * @param fcbSid FCB_SID value
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>get FDR_PARENT_SID value
     * @return FDR_PARENT_SID value
     */
    public int getFdrParentSid() {
        return fdrParentSid__;
    }

    /**
     * <p>set FDR_PARENT_SID value
     * @param fdrParentSid FDR_PARENT_SID value
     */
    public void setFdrParentSid(int fdrParentSid) {
        fdrParentSid__ = fdrParentSid;
    }

    /**
     * <p>get FDR_KBN value
     * @return FDR_KBN value
     */
    public int getFdrKbn() {
        return fdrKbn__;
    }

    /**
     * <p>set FDR_KBN value
     * @param fdrKbn FDR_KBN value
     */
    public void setFdrKbn(int fdrKbn) {
        fdrKbn__ = fdrKbn;
    }

    /**
     * <p>get FDR_VER_KBN value
     * @return FDR_VER_KBN value
     */
    public int getFdrVerKbn() {
        return fdrVerKbn__;
    }

    /**
     * <p>set FDR_VER_KBN value
     * @param fdrVerKbn FDR_VER_KBN value
     */
    public void setFdrVerKbn(int fdrVerKbn) {
        fdrVerKbn__ = fdrVerKbn;
    }

    /**
     * <p>get FDR_LEVEL value
     * @return FDR_LEVEL value
     */
    public int getFdrLevel() {
        return fdrLevel__;
    }

    /**
     * <p>set FDR_LEVEL value
     * @param fdrLevel FDR_LEVEL value
     */
    public void setFdrLevel(int fdrLevel) {
        fdrLevel__ = fdrLevel;
    }

    /**
     * <p>get FDR_NAME value
     * @return FDR_NAME value
     */
    public String getFdrName() {
        return fdrName__;
    }

    /**
     * <p>set FDR_NAME value
     * @param fdrName FDR_NAME value
     */
    public void setFdrName(String fdrName) {
        fdrName__ = fdrName;
    }

    /**
     * <p>get FDR_BIKO value
     * @return FDR_BIKO value
     */
    public String getFdrBiko() {
        return fdrBiko__;
    }

    /**
     * <p>set FDR_BIKO value
     * @param fdrBiko FDR_BIKO value
     */
    public void setFdrBiko(String fdrBiko) {
        fdrBiko__ = fdrBiko;
    }

    /**
     * <p>get FDR_JTKBN value
     * @return FDR_JTKBN value
     */
    public int getFdrJtkbn() {
        return fdrJtkbn__;
    }

    /**
     * <p>set FDR_JTKBN value
     * @param fdrJtkbn FDR_JTKBN value
     */
    public void setFdrJtkbn(int fdrJtkbn) {
        fdrJtkbn__ = fdrJtkbn;
    }

    /**
     * <p>get FDR_AUID value
     * @return FDR_AUID value
     */
    public int getFdrAuid() {
        return fdrAuid__;
    }

    /**
     * <p>set FDR_AUID value
     * @param fdrAuid FDR_AUID value
     */
    public void setFdrAuid(int fdrAuid) {
        fdrAuid__ = fdrAuid;
    }

    /**
     * <p>get FDR_ADATE value
     * @return FDR_ADATE value
     */
    public UDate getFdrAdate() {
        return fdrAdate__;
    }

    /**
     * <p>set FDR_ADATE value
     * @param fdrAdate FDR_ADATE value
     */
    public void setFdrAdate(UDate fdrAdate) {
        fdrAdate__ = fdrAdate;
    }

    /**
     * <p>get FDR_EUID value
     * @return FDR_EUID value
     */
    public int getFdrEuid() {
        return fdrEuid__;
    }

    /**
     * <p>set FDR_EUID value
     * @param fdrEuid FDR_EUID value
     */
    public void setFdrEuid(int fdrEuid) {
        fdrEuid__ = fdrEuid;
    }

    /**
     * <p>get FDR_EDATE value
     * @return FDR_EDATE value
     */
    public UDate getFdrEdate() {
        return fdrEdate__;
    }

    /**
     * <p>set FDR_EDATE value
     * @param fdrEdate FDR_EDATE value
     */
    public void setFdrEdate(UDate fdrEdate) {
        fdrEdate__ = fdrEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fdrSid__);
        buf.append(",");
        buf.append(fdrVersion__);
        buf.append(",");
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(fdrParentSid__);
        buf.append(",");
        buf.append(fdrKbn__);
        buf.append(",");
        buf.append(fdrVerKbn__);
        buf.append(",");
        buf.append(fdrLevel__);
        buf.append(",");
        buf.append(NullDefault.getString(fdrName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(fdrBiko__, ""));
        buf.append(",");
        buf.append(fdrJtkbn__);
        buf.append(",");
        buf.append(fdrAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fdrAdate__, ""));
        buf.append(",");
        buf.append(fdrEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fdrEdate__, ""));

        return buf.toString();
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>editUsrName を取得します。
     * @return editUsrName
     */
    public String getEditUsrName() {
        return editUsrName__;
    }

    /**
     * <p>editUsrName をセットします。
     * @param editUsrName editUsrName
     */
    public void setEditUsrName(String editUsrName) {
        editUsrName__ = editUsrName;
    }

    /**
     * <p>editUsrJkbn を取得します。
     * @return editUsrJkbn
     */
    public String getEditUsrJkbn() {
        return editUsrJkbn__;
    }

    /**
     * <p>editUsrJkbn をセットします。
     * @param editUsrJkbn editUsrJkbn
     */
    public void setEditUsrJkbn(String editUsrJkbn) {
        editUsrJkbn__ = editUsrJkbn;
    }

    /**
     * <p>fcbMark を取得します。
     * @return fcbMark
     */
    public long getFcbMark() {
        return fcbMark__;
    }

    /**
     * <p>fcbMark をセットします。
     * @param fcbMark fcbMark
     */
    public void setFcbMark(long fcbMark) {
        fcbMark__ = fcbMark;
    }

    /**
     * <p>fdrEgid を取得します。
     * @return fdrEgid
     */
    public int getFdrEgid() {
        return fdrEgid__;
    }

    /**
     * <p>fdrEgid をセットします。
     * @param fdrEgid fdrEgid
     */
    public void setFdrEgid(int fdrEgid) {
        fdrEgid__ = fdrEgid;
    }

    /**
     * <p>fdrAccessSid を取得します。
     * @return fdrAccessSid
     */
    public int getFdrAccessSid() {
        return fdrAccessSid__;
    }

    /**
     * <p>fdrAccessSid をセットします。
     * @param fdrAccessSid fdrAccessSid
     */
    public void setFdrAccessSid(int fdrAccessSid) {
        fdrAccessSid__ = fdrAccessSid;
    }

    /**
     * <p>get FDR_TRADE_DATE value
     * @return FDR_TRADE_DATE value
     */
    public UDate getFdrTradeDate() {
        return fdrTradeDate__;
    }

    /**
     * <p>set FDR_TRADE_DATE value
     * @param fdrTradeDate FDR_TRADE_DATE value
     */
    public void setFdrTradeDate(UDate fdrTradeDate) {
        fdrTradeDate__ = fdrTradeDate;
    }

    /**
     * <p>get FDR_TRADE_TARGET value
     * @return FDR_TRADE_TARGET value
     */
    public String getFdrTradeTarget() {
        return fdrTradeTarget__;
    }

    /**
     * <p>set FDR_TRADE_TARGET value
     * @param fdrTradeTarget FDR_TRADE_TARGET value
     */
    public void setFdrTradeTarget(String fdrTradeTarget) {
        fdrTradeTarget__ = fdrTradeTarget;
    }

    /**
     * <p>get FDR_TRADE_MONEYKBN value
     * @return FDR_TRADE_MONEYKBN value
     */
    public int getFdrTradeMoneykbn() {
        return fdrTradeMoneykbn__;
    }

    /**
     * <p>set FDR_TRADE_MONEYKBN value
     * @param fdrTradeMoneykbn FDR_TRADE_MONEYKBN value
     */
    public void setFdrTradeMoneykbn(int fdrTradeMoneykbn) {
        fdrTradeMoneykbn__ = fdrTradeMoneykbn;
    }

    /**
     * <p>get FDR_TRADE_MONEY value
     * @return FDR_TRADE_MONEY value
     */
    public BigDecimal getFdrTradeMoney() {
        return fdrTradeMoney__;
    }

    /**
     * <p>set FDR_TRADE_MONEY value
     * @param fdrTradeMoney FDR_TRADE_MONEY value
     */
    public void setFdrTradeMoney(BigDecimal fdrTradeMoney) {
        fdrTradeMoney__ = fdrTradeMoney;
    }

    /**
     * <p>get EMT_SID value
     * @return EMT_SID value
     */
    public int getEmtSid() {
        return emtSid__;
    }

    /**
     * <p>set EMT_SID value
     * @param emtSid EMT_SID value
     */
    public void setEmtSid(int emtSid) {
        emtSid__ = emtSid;
    }
}
