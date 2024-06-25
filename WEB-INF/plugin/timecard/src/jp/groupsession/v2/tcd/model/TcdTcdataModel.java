package jp.groupsession.v2.tcd.model;

import java.math.BigDecimal;
import java.sql.Time;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>TCD_TCDATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdTcdataModel extends AbstractModel {

    /** USR_SID mapping */
    private int usrSid__;
    /** TCD_DATE mapping */
    private UDate tcdDate__;
    /** TCD_INTIME mapping */
    private Time tcdIntime__;
    /** TCD_OUTTIME mapping */
    private Time tcdOuttime__;
    /** TCD_STRIKE_INTIME mapping */
    private Time tcdStrikeIntime__;
    /** TCD_STRIKE_OUTTIME mapping */
    private Time tcdStrikeOuttime__;
    /** TCD_BIKO mapping */
    private String tcdBiko__;
    /** TCD_STATUS mapping */
    private int tcdStatus__;
    /** TCD_HOLOTHER mapping */
    private String tcdHolother__;
    /** TCD_HOLCNT mapping */
    private BigDecimal tcdHolcnt__;
    /** TCD_CHKKBN mapping */
    private int tcdChkkbn__;
    /** TCD_SOUKBN mapping */
    private int tcdSoukbn__;
    /** TCD_LOCK_FLG mapping */
    private int tcdLockFlg__;
    /** TCD_AUID mapping */
    private int tcdAuid__;
    /** TCD_ADATE mapping */
    private UDate tcdAdate__;
    /** TCD_EUID mapping */
    private int tcdEuid__;
    /** TCD_EDATE mapping */
    private UDate tcdEdate__;
    /** 打刻時間ロックフラグ */
    private int tcdLockStrike__ = GSConstTimecard.UNLOCK_FLG;
    /** 始業終業時刻ロックフラグ */
    private int tcdLockTime__ = GSConstTimecard.UNLOCK_FLG;
    /** 遅刻早退区分ロックフラグ */
    private int tcdLockLate__ = GSConstTimecard.UNLOCK_FLG;
    /** 休日区分ロックフラグ */
    private int tcdLockHoliday__ = GSConstTimecard.UNLOCK_FLG;
    /** 時間帯ロックフラグ */
    private int tcdLockTimezone__ = GSConstTimecard.UNLOCK_FLG;
    /** TTI_SID mapping */
    private int ttiSid__;
    /** THI_SID mapping */
    private int thiSid__;
    /** TTI_RYAKU mapping */
    private String ttiRyaku__;

    /**
     * <p>Default Constructor
     */
    public TcdTcdataModel() {
    }

    /**
     * <p>tcdChkkbn を取得します。
     * @return tcdChkkbn
     */
    public int getTcdChkkbn() {
        return tcdChkkbn__;
    }

    /**
     * <p>tcdChkkbn をセットします。
     * @param tcdChkkbn tcdChkkbn
     */
    public void setTcdChkkbn(int tcdChkkbn) {
        tcdChkkbn__ = tcdChkkbn;
    }

    /**
     * <p>tcdSoukbn を取得します。
     * @return tcdSoukbn
     */
    public int getTcdSoukbn() {
        return tcdSoukbn__;
    }

    /**
     * <p>tcdSoukbn をセットします。
     * @param tcdSoukbn tcdSoukbn
     */
    public void setTcdSoukbn(int tcdSoukbn) {
        tcdSoukbn__ = tcdSoukbn;
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
     * <p>get TCD_DATE value
     * @return TCD_DATE value
     */
    public UDate getTcdDate() {
        return tcdDate__;
    }

    /**
     * <p>set TCD_DATE value
     * @param tcdDate TCD_DATE value
     */
    public void setTcdDate(UDate tcdDate) {
        tcdDate__ = tcdDate;
    }

    /**
     * <p>get TCD_INTIME value
     * @return TCD_INTIME value
     */
    public Time getTcdIntime() {
        return tcdIntime__;
    }

    /**
     * <p>set TCD_INTIME value
     * @param tcdIntime TCD_INTIME value
     */
    public void setTcdIntime(Time tcdIntime) {
        tcdIntime__ = tcdIntime;
    }

    /**
     * <p>get TCD_OUTTIME value
     * @return TCD_OUTTIME value
     */
    public Time getTcdOuttime() {
        return tcdOuttime__;
    }

    /**
     * <p>set TCD_OUTTIME value
     * @param tcdOuttime TCD_OUTTIME value
     */
    public void setTcdOuttime(Time tcdOuttime) {
        tcdOuttime__ = tcdOuttime;
    }

    /**
     * <p>get TCD_BIKO value
     * @return TCD_BIKO value
     */
    public String getTcdBiko() {
        return tcdBiko__;
    }

    /**
     * <p>set TCD_BIKO value
     * @param tcdBiko TCD_BIKO value
     */
    public void setTcdBiko(String tcdBiko) {
        tcdBiko__ = tcdBiko;
    }

    /**
     * <p>get TCD_STATUS value
     * @return TCD_STATUS value
     */
    public int getTcdStatus() {
        return tcdStatus__;
    }

    /**
     * <p>set TCD_STATUS value
     * @param tcdStatus TCD_STATUS value
     */
    public void setTcdStatus(int tcdStatus) {
        tcdStatus__ = tcdStatus;
    }

    /**
     * <p>get TCD_HOLOTHER value
     * @return TCD_HOLOTHER value
     */
    public String getTcdHolother() {
        return tcdHolother__;
    }

    /**
     * <p>set TCD_HOLOTHER value
     * @param tcdHolother TCD_HOLOTHER value
     */
    public void setTcdHolother(String tcdHolother) {
        tcdHolother__ = tcdHolother;
    }

    /**
     * <p>get TCD_HOLCNT value
     * @return TCD_HOLCNT value
     */
    public BigDecimal getTcdHolcnt() {
        return tcdHolcnt__;
    }

    /**
     * <p>set TCD_HOLCNT value
     * @param tcdHolcnt TCD_HOLCNT value
     */
    public void setTcdHolcnt(BigDecimal tcdHolcnt) {
        tcdHolcnt__ = tcdHolcnt;
    }

    /**
     * <p>get TCD_LOCK_FLG value
     * @return TCD_LOCK_FLG value
     */
    public int getTcdLockFlg() {
        return tcdLockFlg__;
    }

    /**
     * <p>set TCD_LOCK_FLG value
     * @param tcdLockFlg TCD_LOCK_FLG value
     */
    public void setTcdLockFlg(int tcdLockFlg) {
        tcdLockFlg__ = tcdLockFlg;
    }

    /**
     * <p>get TCD_AUID value
     * @return TCD_AUID value
     */
    public int getTcdAuid() {
        return tcdAuid__;
    }

    /**
     * <p>set TCD_AUID value
     * @param tcdAuid TCD_AUID value
     */
    public void setTcdAuid(int tcdAuid) {
        tcdAuid__ = tcdAuid;
    }

    /**
     * <p>get TCD_ADATE value
     * @return TCD_ADATE value
     */
    public UDate getTcdAdate() {
        return tcdAdate__;
    }

    /**
     * <p>set TCD_ADATE value
     * @param tcdAdate TCD_ADATE value
     */
    public void setTcdAdate(UDate tcdAdate) {
        tcdAdate__ = tcdAdate;
    }

    /**
     * <p>get TCD_EUID value
     * @return TCD_EUID value
     */
    public int getTcdEuid() {
        return tcdEuid__;
    }

    /**
     * <p>set TCD_EUID value
     * @param tcdEuid TCD_EUID value
     */
    public void setTcdEuid(int tcdEuid) {
        tcdEuid__ = tcdEuid;
    }

    /**
     * <p>get TCD_EDATE value
     * @return TCD_EDATE value
     */
    public UDate getTcdEdate() {
        return tcdEdate__;
    }

    /**
     * <p>set TCD_EDATE value
     * @param tcdEdate TCD_EDATE value
     */
    public void setTcdEdate(UDate tcdEdate) {
        tcdEdate__ = tcdEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcdDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcdIntime__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcdOuttime__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(tcdBiko__, ""));
        buf.append(",");
        buf.append(tcdStatus__);
        buf.append(",");
        buf.append(thiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(tcdHolother__, ""));
        buf.append(",");
        buf.append(tcdHolcnt__);
        buf.append(",");
        buf.append(tcdLockFlg__);
        buf.append(",");
        buf.append(tcdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcdAdate__, ""));
        buf.append(",");
        buf.append(tcdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tcdEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>tcdStrikeIntime を取得します。
     * @return tcdStrikeIntime
     */
    public Time getTcdStrikeIntime() {
        return tcdStrikeIntime__;
    }

    /**
     * <p>tcdStrikeIntime をセットします。
     * @param tcdStrikeIntime tcdStrikeIntime
     */
    public void setTcdStrikeIntime(Time tcdStrikeIntime) {
        tcdStrikeIntime__ = tcdStrikeIntime;
    }

    /**
     * <p>tcdStrikeOuttime を取得します。
     * @return tcdStrikeOuttime
     */
    public Time getTcdStrikeOuttime() {
        return tcdStrikeOuttime__;
    }

    /**
     * <p>tcdStrikeOuttime をセットします。
     * @param tcdStrikeOuttime tcdStrikeOuttime
     */
    public void setTcdStrikeOuttime(Time tcdStrikeOuttime) {
        tcdStrikeOuttime__ = tcdStrikeOuttime;
    }

    /**
     * <p>tcdLockHoliday を取得します。
     * @return tcdLockHoliday
     */
    public int getTcdLockHoliday() {
        return tcdLockHoliday__;
    }

    /**
     * <p>tcdLockHoliday をセットします。
     * @param tcdLockHoliday tcdLockHoliday
     */
    public void setTcdLockHoliday(int tcdLockHoliday) {
        tcdLockHoliday__ = tcdLockHoliday;
    }

    /**
     * <p>tcdLockLate を取得します。
     * @return tcdLockLate
     */
    public int getTcdLockLate() {
        return tcdLockLate__;
    }

    /**
     * <p>tcdLockLate をセットします。
     * @param tcdLockLate tcdLockLate
     */
    public void setTcdLockLate(int tcdLockLate) {
        tcdLockLate__ = tcdLockLate;
    }

    /**
     * <p>tcdLockStrike を取得します。
     * @return tcdLockStrike
     */
    public int getTcdLockStrike() {
        return tcdLockStrike__;
    }

    /**
     * <p>tcdLockStrike をセットします。
     * @param tcdLockStrike tcdLockStrike
     */
    public void setTcdLockStrike(int tcdLockStrike) {
        tcdLockStrike__ = tcdLockStrike;
    }

    /**
     * <p>tcdLockTime を取得します。
     * @return tcdLockTime
     */
    public int getTcdLockTime() {
        return tcdLockTime__;
    }

    /**
     * <p>tcdLockTime をセットします。
     * @param tcdLockTime tcdLockTime
     */
    public void setTcdLockTime(int tcdLockTime) {
        tcdLockTime__ = tcdLockTime;
    }

    /**
     * <p>tcdLockTimezone を取得します。
     * @return tcdLockTimezone
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#tcdLockTimezone__
     */
    public int getTcdLockTimezone() {
        return tcdLockTimezone__;
    }

    /**
     * <p>tcdLockTimezone をセットします。
     * @param tcdLockTimezone tcdLockTimezone
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#tcdLockTimezone__
     */
    public void setTcdLockTimezone(int tcdLockTimezone) {
        tcdLockTimezone__ = tcdLockTimezone;
    }

    /**
     * <p>ttiSid を取得します。
     * @return ttiSid
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#ttiSid__
     */
    public int getTtiSid() {
        return ttiSid__;
    }

    /**
     * <p>ttiSid をセットします。
     * @param ttiSid ttiSid
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#ttiSid__
     */
    public void setTtiSid(int ttiSid) {
        ttiSid__ = ttiSid;
    }

    /**
     * <p>thiSid を取得します。
     * @return thiSid
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#thiSid__
     */
    public int getThiSid() {
        return thiSid__;
    }

    /**
     * <p>thiSid をセットします。
     * @param thiSid thiSid
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#thiSid__
     */
    public void setThiSid(int thiSid) {
        thiSid__ = thiSid;
    }

    /**
     * <p>ttiRyaku を取得します。
     * @return ttiRyaku
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#ttiRyaku__
     */
    public String getTtiRyaku() {
        return ttiRyaku__;
    }

    /**
     * <p>ttiRyaku をセットします。
     * @param ttiRyaku ttiRyaku
     * @see jp.groupsession.v2.tcd.model.TcdTcdataModel#ttiRyaku__
     */
    public void setTtiRyaku(String ttiRyaku) {
        ttiRyaku__ = ttiRyaku;
    }


}
