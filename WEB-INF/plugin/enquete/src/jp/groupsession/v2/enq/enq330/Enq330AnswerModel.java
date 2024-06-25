package jp.groupsession.v2.enq.enq330;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 対象者情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330AnswerModel extends AbstractModel {
    /** ユーザSID */
    private int userSid__ = 0;
    /** グループ */
    private String group__ = null;
    /** グループ 削除フラグ */
    private boolean groupDelFlg__ = false;
    /** ユーザ */
    private String user__ = null;
    /** ユーザ 削除フラグ */
    private boolean userDelFlg__ = false;
    /** ユーザ ログイン停止フラグ */
    private int usrUkoFlg__ = 0;
    /** 回答日 */
    private String ansDate__ = null;
    /** 回答値 */
    private String ansValue__ = null;
    /** 匿名フラグ */
    private boolean anony__ = false;
    /**
     * <p>group を取得します。
     * @return group
     */
    public String getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(String group) {
        group__ = group;
    }
    /**
     * <p>groupDelFlg を取得します。
     * @return groupDelFlg
     */
    public boolean isGroupDelFlg() {
        return groupDelFlg__;
    }
    /**
     * <p>groupDelFlg をセットします。
     * @param groupDelFlg groupDelFlg
     */
    public void setGroupDelFlg(boolean groupDelFlg) {
        groupDelFlg__ = groupDelFlg;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public String getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(String user) {
        user__ = user;
    }
    /**
     * <p>userDelFlg を取得します。
     * @return userDelFlg
     */
    public boolean isUserDelFlg() {
        return userDelFlg__;
    }
    /**
     * <p>userDelFlg をセットします。
     * @param userDelFlg userDelFlg
     */
    public void setUserDelFlg(boolean userDelFlg) {
        userDelFlg__ = userDelFlg;
    }
    /**
     * <p>ansDate を取得します。
     * @return ansDate
     */
    public String getAnsDate() {
        return ansDate__;
    }
    /**
     * <p>ansDate をセットします。
     * @param ansDate ansDate
     */
    public void setAnsDate(String ansDate) {
        ansDate__ = ansDate;
    }
    /**
     * <p>ansValue を取得します。
     * @return ansValue
     */
    public String getAnsValue() {
        return ansValue__;
    }
    /**
     * <p>ansValue をセットします。
     * @param ansValue ansValue
     */
    public void setAnsValue(String ansValue) {
        ansValue__ = ansValue;
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
     * <p>anony を取得します。
     * @return anony
     */
    public boolean isAnony() {
        return anony__;
    }
    /**
     * <p>anony をセットします。
     * @param anony anony
     */
    public void setAnony(boolean anony) {
        anony__ = anony;
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
