package jp.groupsession.v2.enq.csv;

import java.util.ArrayList;

/**
 *
 * <br>[機  能]アンケート CSVエクスポート用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqCsvModel {

    /** ユーザSID */
    private int userSid__ = -1;
    /** 設問種別 */
    private int queKbn__ = -1;

    /** タイトル */
    private String title__ = null;
    /** グループ */
    private String group__ = null;
    /** ユーザ */
    private String user__ = null;
    /** 回答/未回答 フラグ*/
    private int statusFlg__ = 0;
    /** 回答/未回答 状況 */
    private String status__ = null;
    /** 回答値 */
    private ArrayList<String> ansValue__ = null;
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
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
     * <p>statusFlg を取得します。
     * @return statusFlg
     */
    public int getStatusFlg() {
        return statusFlg__;
    }
    /**
     * <p>statusFlg をセットします。
     * @param statusFlg statusFlg
     */
    public void setStatusFlg(int statusFlg) {
        statusFlg__ = statusFlg;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public String getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(String status) {
        status__ = status;
    }
    /**
     * <p>ansValue を取得します。
     * @return ansValue
     */
    public ArrayList<String> getAnsValue() {
        return ansValue__;
    }
    /**
     * <p>ansValue をセットします。
     * @param ansValue ansValue
     */
    public void setAnsValue(ArrayList<String> ansValue) {
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
     * <p>queKbn を取得します。
     * @return queKbn
     */
    public int getQueKbn() {
        return queKbn__;
    }
    /**
     * <p>queKbn をセットします。
     * @param queKbn queKbn
     */
    public void setQueKbn(int queKbn) {
        queKbn__ = queKbn;
    }




}
