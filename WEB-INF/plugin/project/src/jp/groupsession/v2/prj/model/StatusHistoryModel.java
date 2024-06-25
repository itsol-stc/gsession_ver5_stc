package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] TODO状態、TODOの変更履歴を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class StatusHistoryModel {

    /** 状態名称 */
    private String statusName__;
    /** 進捗率 */
    private int rate__;
    /** 変更履歴SID */
    private int hisSid__;
    /** 登録日時 */
    private UDate addDate__;
    /** 登録日時(表示用) */
    private String strAddDate__;
    /** 状態変更理由 */
    private String reason__;
    /** 姓 */
    private String sei__;
    /** 名 */
    private String mei__;
    /** ユーザ状態区分 */
    private int userStatus__;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__;

    /**
     * <p>statusName を取得します。
     * @return statusName
     */
    public String getStatusName() {
        return statusName__;
    }
    /**
     * <p>statusName をセットします。
     * @param statusName statusName
     */
    public void setStatusName(String statusName) {
        statusName__ = statusName;
    }
    /**
     * <p>rate を取得します。
     * @return rate
     */
    public int getRate() {
        return rate__;
    }
    /**
     * <p>rate をセットします。
     * @param rate rate
     */
    public void setRate(int rate) {
        rate__ = rate;
    }
    /**
     * <p>addDate を取得します。
     * @return addDate
     */
    public UDate getAddDate() {
        return addDate__;
    }
    /**
     * <p>addDate をセットします。
     * @param addDate addDate
     */
    public void setAddDate(UDate addDate) {
        addDate__ = addDate;
    }
    /**
     * <p>reason を取得します。
     * @return reason
     */
    public String getReason() {
        return reason__;
    }
    /**
     * <p>reason をセットします。
     * @param reason reason
     */
    public void setReason(String reason) {
        reason__ = reason;
    }
    /**
     * <p>sei を取得します。
     * @return sei
     */
    public String getSei() {
        return sei__;
    }
    /**
     * <p>sei をセットします。
     * @param sei sei
     */
    public void setSei(String sei) {
        sei__ = sei;
    }
    /**
     * <p>mei を取得します。
     * @return mei
     */
    public String getMei() {
        return mei__;
    }
    /**
     * <p>mei をセットします。
     * @param mei mei
     */
    public void setMei(String mei) {
        mei__ = mei;
    }
    /**
     * <p>userStatus を取得します。
     * @return userStatus
     */
    public int getUserStatus() {
        return userStatus__;
    }
    /**
     * <p>userStatus をセットします。
     * @param userStatus userStatus
     */
    public void setUserStatus(int userStatus) {
        userStatus__ = userStatus;
    }
    /**
     * <p>strAddDate を取得します。
     * @return strAddDate
     */
    public String getStrAddDate() {
        return strAddDate__;
    }
    /**
     * <p>strAddDate をセットします。
     * @param strAddDate strAddDate
     */
    public void setStrAddDate(String strAddDate) {
        strAddDate__ = strAddDate;
    }
    /**
     * <p>hisSid を取得します。
     * @return hisSid
     */
    public int getHisSid() {
        return hisSid__;
    }
    /**
     * <p>hisSid をセットします。
     * @param hisSid hisSid
     */
    public void setHisSid(int hisSid) {
        hisSid__ = hisSid;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }

}
