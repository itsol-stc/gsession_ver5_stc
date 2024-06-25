package jp.groupsession.v2.prj.model;


/**
 * <br>[機  能] TODOコメントを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TodocommentModel extends PrjTodocommentModel {

    /** 姓 */
    private String sei__;
    /** 名 */
    private String mei__;
    /** 状態区分 */
    private int status__;
    /** 登録日時(表示用) */
    private String strPcmAdate__;
    /** 削除権限 */
    private boolean deleteKbn__;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__;

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
     * <p>strPcmAdate を取得します。
     * @return strPcmAdate
     */
    public String getStrPcmAdate() {
        return strPcmAdate__;
    }
    /**
     * <p>strPcmAdate をセットします。
     * @param strPcmAdate strPcmAdate
     */
    public void setStrPcmAdate(String strPcmAdate) {
        strPcmAdate__ = strPcmAdate;
    }
    /**
     * <p>deleteKbn を取得します。
     * @return deleteKbn
     */
    public boolean isDeleteKbn() {
        return deleteKbn__;
    }
    /**
     * <p>deleteKbn をセットします。
     * @param deleteKbn deleteKbn
     */
    public void setDeleteKbn(boolean deleteKbn) {
        deleteKbn__ = deleteKbn;
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
