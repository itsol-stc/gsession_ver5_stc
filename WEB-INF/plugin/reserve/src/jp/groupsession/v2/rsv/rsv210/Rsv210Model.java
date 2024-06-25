package jp.groupsession.v2.rsv.rsv210;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 施設予約一括登録画面 施設情報などを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv210Model extends AbstractModel {

    /** 施設グループ名称 */
    private String rsgName__ = null;
    /** 施設SID */
    private int rsdSid__ = -1;
    /** 施設名称 */
    private String rsdName__ = null;
    /** 予約可能期限 */
    private String rsdProp6__ = null;
    /** 重複登録 */
    private String rsdProp7__ = null;

    /**
     * <p>rsdName__ を取得します。
     * @return rsdName
     */
    public String getRsdName() {
        return rsdName__;
    }
    /**
     * <p>rsdName__ をセットします。
     * @param rsdName rsdName__
     */
    public void setRsdName(String rsdName) {
        rsdName__ = rsdName;
    }
    /**
     * <p>rsdProp6__ を取得します。
     * @return rsdProp6
     */
    public String getRsdProp6() {
        return rsdProp6__;
    }
    /**
     * <p>rsdProp6__ をセットします。
     * @param rsdProp6 rsdProp6__
     */
    public void setRsdProp6(String rsdProp6) {
        rsdProp6__ = rsdProp6;
    }
    /**
     * <p>rsdProp7__ を取得します。
     * @return rsdProp7
     */
    public String getRsdProp7() {
        return rsdProp7__;
    }
    /**
     * <p>rsdProp7__ をセットします。
     * @param rsdProp7 rsdProp7__
     */
    public void setRsdProp7(String rsdProp7) {
        rsdProp7__ = rsdProp7;
    }
    /**
     * <p>rsdSid__ を取得します。
     * @return rsdSid
     */
    public int getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid__ をセットします。
     * @param rsdSid rsdSid__
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
    }
    /**
     * <p>rsgName__ を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName__ をセットします。
     * @param rsgName rsgName__
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }
}