package jp.groupsession.v2.sch.sch010;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュールで使用するユーザ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010UsrModel extends AbstractModel {

    /** ユーザ表示名称 */
    private String usrName__ = null;
    /** ユーザSID */
    private int usrSid__;
    /** ユーザ区分 */
    private int usrKbn__;
    /** 在席状況 */
    private int zaisekiKbn__;
    /** 在席状況メッセージ */
    private String zaisekiMsg__;
    /** ショートメール有効送信先フラグ 0:無効 1:有効 */
    private int smlAble__ = 0;
    /** スケジュール 登録可能フラグ */
    private boolean schRegistFlg__ = false;
    /** ユーザ無効化フラグ */
    private int schUkoFlg__ = 0;

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
     * @return zaisekiKbn を戻します。
     */
    public int getZaisekiKbn() {
        return zaisekiKbn__;
    }
    /**
     * @param zaisekiKbn 設定する zaisekiKbn。
     */
    public void setZaisekiKbn(int zaisekiKbn) {
        zaisekiKbn__ = zaisekiKbn;
    }
    /**
     * @return usrKbn を戻します。
     */
    public int getUsrKbn() {
        return usrKbn__;
    }
    /**
     * @param usrKbn 設定する usrKbn。
     */
    public void setUsrKbn(int usrKbn) {
        usrKbn__ = usrKbn;
    }
    /**
     * @return usrName を戻します。
     */
    public String getUsrName() {
        return usrName__;
    }
    /**
     * @param usrName 設定する usrName。
     */
    public void setUsrName(String usrName) {
        usrName__ = usrName;
    }
    /**
     * @return usrSid を戻します。
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid。
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>smlAble を取得します。
     * @return smlAble
     */
    public int getSmlAble() {
        return smlAble__;
    }
    /**
     * <p>smlAble をセットします。
     * @param smlAble smlAble
     */
    public void setSmlAble(int smlAble) {
        smlAble__ = smlAble;
    }
    /**
     * <p>schRegistFlg を取得します。
     * @return schRegistFlg
     */
    public boolean isSchRegistFlg() {
        return schRegistFlg__;
    }
    /**
     * <p>schRegistFlg をセットします。
     * @param schRegistFlg schRegistFlg
     */
    public void setSchRegistFlg(boolean schRegistFlg) {
        schRegistFlg__ = schRegistFlg;
    }
    /**
     * <p>schMukoFlg を取得します。
     * @return schUkoFlg
     */
    public int getSchUkoFlg() {
        return schUkoFlg__;
    }
    /**
     * <p>schMukoFlg をセットします。
     * @param schUkoFlg schUkoFlg
     */
    public void setSchUkoFlg(int schUkoFlg) {
        schUkoFlg__ = schUkoFlg;
    }
}
