package jp.groupsession.v2.anp.anp110.model;

/**
 * <p>安否確認 管理者設定・緊急連絡先設定状況画面 検索条件MODEL
 *
 * @author JTS
 */
public class Anp110SearchModel {

    /** ユーザSID */
    private int userSid__ = 0;
    /** グループSID */
    private String groupSid__ = null;
    /** フィルター メールアドレス */
    private int mailFlg__;
    /** フィルター 電話番号 */
    private int tellFlg__;

    /**
     * <p>ユーザSIDを取得する
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }

    /**
     * <p>ユーザSIDを設定する
     * @param userSid セットする userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }

    /**
     * <p>グループSIDを取得する
     * @return groupSid
     */
    public String getGroupSid() {
        return groupSid__;
    }

    /**
     * <p>グループSIDを設定する
     * @param groupSid セットする groupSid
     */
    public void setGroupSid(String groupSid) {
        groupSid__ = groupSid;
    }

    /**
     * <p>mailFlg を取得します。
     * @return mailFlg
     */
    public int getMailFlg() {
        return mailFlg__;
    }

    /**
     * <p>mailFlg をセットします。
     * @param mailFlg mailFlg
     */
    public void setMailFlg(int mailFlg) {
        mailFlg__ = mailFlg;
    }

    /**
     * <p>tellFlg を取得します。
     * @return tellFlg
     */
    public int getTellFlg() {
        return tellFlg__;
    }

    /**
     * <p>tellFlg をセットします。
     * @param tellFlg tellFlg
     */
    public void setTellFlg(int tellFlg) {
        tellFlg__ = tellFlg;
    }

}
