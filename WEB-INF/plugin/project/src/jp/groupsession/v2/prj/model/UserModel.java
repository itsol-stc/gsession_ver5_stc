package jp.groupsession.v2.prj.model;

/**
 * <br>[機  能] プロジェクトで使用するユーザ情報を格納するModelクラス
 * <br>[解  説] メンバー、管理者、担当者等の取得時に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserModel {

    /** ユーザSID */
    private int userSid__;
    /** 姓 */
    private String sei__;
    /** 名 */
    private String mei__;
    /** 状態区分 */
    private int status__;
    /** ログイン停止フラグ */
    private int usrUkoFlg__;
    /** 管理者区分 */
    private int adminKbn__;
    /** 役職 */
    private String position__;
    /** メンバーID */
    private String memberKey__;
    /** 在席状況 */
    private int zaisekiKbn__;
    /** 在席状況メッセージ */
    private String zaisekiMsg__;

    /**
     * <p>memberKey を取得します。
     * @return memberKey
     */
    public String getMemberKey() {
        return memberKey__;
    }
    /**
     * <p>memberKey をセットします。
     * @param memberKey memberKey
     */
    public void setMemberKey(String memberKey) {
        memberKey__ = memberKey;
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
     * <p>status を取得します。
     * @return status
     */
    public int getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(int status) {
        status__ = status;
    }
    /**
     * <p>adminKbn を取得します。
     * @return adminKbn
     */
    public int getAdminKbn() {
        return adminKbn__;
    }
    /**
     * <p>adminKbn をセットします。
     * @param adminKbn adminKbn
     */
    public void setAdminKbn(int adminKbn) {
        adminKbn__ = adminKbn;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>position を取得します。
     * @return position
     */
    public String getPosition() {
        return position__;
    }
    /**
     * <p>position をセットします。
     * @param position position
     */
    public void setPosition(String position) {
        position__ = position;
    }
    /**
     * <p>zaisekiKbn を取得します。
     * @return zaisekiKbn
     */
    public int getZaisekiKbn() {
        return zaisekiKbn__;
    }
    /**
     * <p>zaisekiKbn をセットします。
     * @param zaisekiKbn zaisekiKbn
     */
    public void setZaisekiKbn(int zaisekiKbn) {
        zaisekiKbn__ = zaisekiKbn;
    }
    /**
     * <p>zaisekiMsg を取得します。
     * @return zaisekiMsg
     */
    public String getZaisekiMsg() {
        return zaisekiMsg__;
    }
    /**
     * <p>zaisekiMsg をセットします。
     * @param zaisekiMsg zaisekiMsg
     */
    public void setZaisekiMsg(String zaisekiMsg) {
        zaisekiMsg__ = zaisekiMsg;
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
