package jp.groupsession.v2.rsv.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 更新通知用ショートメール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ReserveSmlModel {

    /** 予約情報SID */
    private int rsvSid__ = 0;
    /** 利用目的 */
    private String rsvMokuteki__  = null;
    /** 内容 */
    private String rsvNaiyou__ = null;
    /** ユーザSID(更新者) */
    private int userSid__ = 0;
    /** 更新 開始日時 */
    private UDate rsvFrDate__ = null;
    /** 更新 終了日時 */
    private UDate rsvToDate__ = null;
    /** 更新日時 */
    private UDate rsvAdate__ = null;
    /** URL */
    private String rsvUrl__ = null;

    /** 繰り返しか一般かどうかのフラグ 0=一般登録 1=繰り返し登録 */
    private int editModeFlg__ = -1;
    /**
     * @return rsvSid
     */
    public int getRsvSid() {
        return rsvSid__;
    }
    /**
     * @param rsvSid セットする rsvSid
     */
    public void setRsvSid(int rsvSid) {
        rsvSid__ = rsvSid;
    }
    /**
     * @return rsvMokuteki
     */
    public String getRsvMokuteki() {
        return rsvMokuteki__;
    }
    /**
     * @param rsvMokuteki セットする rsvMokuteki
     */
    public void setRsvMokuteki(String rsvMokuteki) {
        rsvMokuteki__ = rsvMokuteki;
    }
    /**
     * @return rsvNaiyou
     */
    public String getRsvNaiyou() {
        return rsvNaiyou__;
    }
    /**
     * @param rsvNaiyou セットする rsvNaiyou
     */
    public void setRsvNaiyou(String rsvNaiyou) {
        rsvNaiyou__ = rsvNaiyou;
    }
    /**
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * @param userSid セットする userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * @return rsvFrDate
     */
    public UDate getRsvFrDate() {
        return rsvFrDate__;
    }
    /**
     * @param rsvFrDate セットする rsvFrDate
     */
    public void setRsvFrDate(UDate rsvFrDate) {
        rsvFrDate__ = rsvFrDate;
    }
    /**
     * @return rsvToDate
     */
    public UDate getRsvToDate() {
        return rsvToDate__;
    }
    /**
     * @param rsvToDate セットする rsvToDate
     */
    public void setRsvToDate(UDate rsvToDate) {
        rsvToDate__ = rsvToDate;
    }
    /**
     * @return rsvAdate
     */
    public UDate getRsvAdate() {
        return rsvAdate__;
    }
    /**
     * @param rsvAdate セットする rsvAdate
     */
    public void setRsvAdate(UDate rsvAdate) {
        rsvAdate__ = rsvAdate;
    }
    /**
     * @return rsvUrl
     */
    public String getRsvUrl() {
        return rsvUrl__;
    }
    /**
     * @param rsvUrl セットする rsvUrl
     */
    public void setRsvUrl(String rsvUrl) {
        rsvUrl__ = rsvUrl;
    }
    /**
     * @return editModeFlg
     */
    public int getEditModeFlg() {
        return editModeFlg__;
    }
    /**
     * @param editModeFlg セットする editModeFlg
     */
    public void setEditModeFlg(int editModeFlg) {
        editModeFlg__ = editModeFlg;
    }
}
