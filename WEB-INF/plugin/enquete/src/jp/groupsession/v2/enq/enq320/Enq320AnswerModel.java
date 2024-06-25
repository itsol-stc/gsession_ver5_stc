package jp.groupsession.v2.enq.enq320;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.enq.GSConstEnquete;

/**
 * <br>[機  能] 対象者情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320AnswerModel extends AbstractModel {
    /** ユーザSID */
    private int userSid__ = 0;
    /** 状態 */
    private int status__ = 0;
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
    /** 匿名 */
    private int anony__ = GSConstEnquete.EMN_ANONNY_NON;
    /**
     * <p>anony を取得します。
     * @return anony
     */
    public int getAnony() {
        return anony__;
    }
    /**
     * <p>anony をセットします。
     * @param anony anony
     */
    public void setAnony(int anony) {
        anony__ = anony;
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
