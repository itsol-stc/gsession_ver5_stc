package jp.groupsession.v2.tcd.tcd090;

import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.tcd030.Tcd030Form;

/**
 * <br>[機  能] タイムカード 管理者設定 編集権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd090Form extends Tcd030Form {

    /** 始業終業時刻ロックフラグ */
    private String tcd090LockFlg__;
    /** 打刻時間ロックフラグ */
    private String tcd090LockStrike__;
    /** 備考ロックフラグ */
    private String tcd090LockBiko__ = String.valueOf(GSConstTimecard.BIKO_UNNECESSARY_FLG);
    /** 遅刻早退区分ロックフラグ */
    private String tcd090LockLate__;
    /** 休日区分ロックフラグ */
    private String tcd090LockHoliday__;
    /** 時間帯ロックフラグ */
    private String tcd090LockTimezone__;

    /**
     * <p>tcd090LockBiko を取得します。
     * @return tcd090LockBiko
     */
    public String getTcd090LockBiko() {
        return tcd090LockBiko__;
    }
    /**
     * <p>tcd090LockBiko をセットします。
     * @param tcd090LockBiko tcd090LockBiko
     */
    public void setTcd090LockBiko(String tcd090LockBiko) {
        tcd090LockBiko__ = tcd090LockBiko;
    }
    /**
     * <p>tcd090LockHoliday を取得します。
     * @return tcd090LockHoliday
     */
    public String getTcd090LockHoliday() {
        return tcd090LockHoliday__;
    }
    /**
     * <p>tcd090LockHoliday をセットします。
     * @param tcd090LockHoliday tcd090LockHoliday
     */
    public void setTcd090LockHoliday(String tcd090LockHoliday) {
        tcd090LockHoliday__ = tcd090LockHoliday;
    }
    /**
     * <p>tcd090LockLate を取得します。
     * @return tcd090LockLate
     */
    public String getTcd090LockLate() {
        return tcd090LockLate__;
    }
    /**
     * <p>tcd090LockLate をセットします。
     * @param tcd090LockLate tcd090LockLate
     */
    public void setTcd090LockLate(String tcd090LockLate) {
        tcd090LockLate__ = tcd090LockLate;
    }
    /**
     * <p>tcd090LockStrike を取得します。
     * @return tcd090LockStrike
     */
    public String getTcd090LockStrike() {
        return tcd090LockStrike__;
    }
    /**
     * <p>tcd090LockStrike をセットします。
     * @param tcd090LockStrike tcd090LockStrike
     */
    public void setTcd090LockStrike(String tcd090LockStrike) {
        tcd090LockStrike__ = tcd090LockStrike;
    }
    /**
     * <p>tcd090LockFlg を取得します。
     * @return tcd090LockFlg
     */
    public String getTcd090LockFlg() {
        return tcd090LockFlg__;
    }
    /**
     * <p>tcd090LockFlg をセットします。
     * @param tcd090LockFlg tcd090LockFlg
     */
    public void setTcd090LockFlg(String tcd090LockFlg) {
        tcd090LockFlg__ = tcd090LockFlg;
    }
    /**
     * <p>tcd090LockTimezone を取得します。
     * @return tcd090LockTimezone
     * @see jp.groupsession.v2.tcd.tcd090.Tcd090Form#tcd090LockTimezone__
     */
    public String getTcd090LockTimezone() {
        return tcd090LockTimezone__;
    }
    /**
     * <p>tcd090LockTimezone をセットします。
     * @param tcd090LockTimezone tcd090LockTimezone
     * @see jp.groupsession.v2.tcd.tcd090.Tcd090Form#tcd090LockTimezone__
     */
    public void setTcd090LockTimezone(String tcd090LockTimezone) {
        tcd090LockTimezone__ = tcd090LockTimezone;
    }

}