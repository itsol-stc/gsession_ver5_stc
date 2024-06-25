package jp.groupsession.v2.anp.anp060kn.model;

import java.io.Serializable;

import jp.groupsession.v2.anp.anp060kn.Anp060knForm;

/**
 * <br>[機  能] 送信者一覧行モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060knSenderModel implements Serializable {

    /** グループフラグ 0:個人  1:グループ*/
    private int grpFlg__ = Anp060knForm.GROUP_SELECT_NO;
    /** ユーザ or グループSID */
    private String sid__;
    /** ユーザ名 or グループ名 */
    private String name__;
    /** メールアドレス設定フラグ 0:設定無し  1:設定有り*/
    private int mailadr__ = Anp060knForm.MAIL_SET_NO;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__ = 0;
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
    /**
     * <p>grpFlg を取得します。
     * @return grpFlg
     */
    public int getGrpFlg() {
        return grpFlg__;
    }
    /**
     * <p>grpFlg をセットします。
     * @param grpFlg grpFlg
     */
    public void setGrpFlg(int grpFlg) {
        grpFlg__ = grpFlg;
    }
    /**
     * <p>sid を取得します。
     * @return sid
     */
    public String getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     */
    public void setSid(String sid) {
        sid__ = sid;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>mailadr を取得します。
     * @return mailadr
     */
    public int getMailadr() {
        return mailadr__;
    }
    /**
     * <p>mailadr をセットします。
     * @param mailadr mailadr
     */
    public void setMailadr(int mailadr) {
        mailadr__ = mailadr;
    }
}
