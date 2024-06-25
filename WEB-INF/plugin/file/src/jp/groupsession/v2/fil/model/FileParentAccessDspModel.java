package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <br>[機  能] 親ディレクトリのアクセス制限ユーザ表示モデル
 * <br>[解  説]
 * <br>[備  考]
 * @author r_tsushima
 *
 */
public class FileParentAccessDspModel implements Serializable {

    /** ユーザ名*/
    private String userName__;
    /** グループフラグ*/
    private int grpFlg__;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__;


    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }

    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName__ = userName;
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
        this.grpFlg__ = grpFlg;
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
