package jp.groupsession.v2.zsk.model;

/**
 * <br>[機  能] 在席状況を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZaiIndexPlusModel extends ZaiIndexModel {

    /** 在席状況 */
    private int uioStatus__;
    /** ユーザ状態区分 */
    private int usrJkbn__;
    /** ユーザログイン停止フラグ */
    private int usrUkoFlg__;

    /**
     * <p>uioStatus を取得します。
     * @return uioStatus
     */
    public int getUioStatus() {
        return uioStatus__;
    }

    /**
     * <p>uioStatus をセットします。
     * @param uioStatus uioStatus
     */
    public void setUioStatus(int uioStatus) {
        uioStatus__ = uioStatus;
    }

    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }

    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
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
