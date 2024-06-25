package jp.groupsession.v2.api.smail.account;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.model.SmlAccountAutoDestModel;
/**
 *
 * <br>[機  能] API ショートメールアカウント自動宛先モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlAccountModel extends SmlAccountAutoDestModel {
    /** 名前 */
    private String saaName__;

    /** 無効フラグ */
    private int ukoFlg__ = GSConst.YUKOMUKO_YUKO;

    /**
     * <p>saaName を取得します。
     * @return saaName
     */
    public String getSaaName() {
        return saaName__;
    }
    /**
     * <p>saaName をセットします。
     * @param saaName saaName
     */
    public void setSaaName(String saaName) {
        saaName__ = saaName;
    }

    /**
     * <p>ukoFlg を取得します。
     * @return ukoFlg
     */
    public int getUkoFlg() {
        return ukoFlg__;
    }
    /**
     * <p>ukoFlg をセットします。
     * @param ukoFlg ukoFlg
     */
    public void setUkoFlg(int ukoFlg) {
        ukoFlg__ = ukoFlg;
    }
}
