package jp.groupsession.v2.rsv.rsv200kn;

import jp.groupsession.v2.rsv.model.RsvSisDataModel;

/**
 * <br>[機  能] 施設予約 施設一括設定確認画面 施設属性などを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200knModel extends RsvSisDataModel {

    /** 更新フラグ 施設属性1 */
    private boolean rsdProp1UpdFlg__ = false;
    /** 更新フラグ 施設属性2 */
    private boolean rsdProp2UpdFlg__ = false;
    /** 更新フラグ 施設属性3 */
    private boolean rsdProp3UpdFlg__ = false;
    /** 更新フラグ 施設属性4 */
    private boolean rsdProp4UpdFlg__ = false;
    /** 更新フラグ 施設属性5 */
    private boolean rsdProp5UpdFlg__ = false;
    /** 更新フラグ 施設属性6 */
    private boolean rsdProp6UpdFlg__ = false;
    /** 更新フラグ 施設属性7 */
    private boolean rsdProp7UpdFlg__ = false;
    /** 更新フラグ 備考 */
    private boolean rsdRsdBikoUpdFlg__ = false;
    /** 更新対象施設一覧 */
    private String[] targetSiset__ = null;

    /**
     * <p>rsdProp1UpdFlg__ を取得します。
     * @return rsdProp1UpdFlg
     */
    public boolean isRsdProp1UpdFlg() {
        return rsdProp1UpdFlg__;
    }
    /**
     * <p>rsdProp1UpdFlg__ をセットします。
     * @param rsdProp1UpdFlg rsdProp1UpdFlg__
     */
    public void setRsdProp1UpdFlg(boolean rsdProp1UpdFlg) {
        rsdProp1UpdFlg__ = rsdProp1UpdFlg;
    }
    /**
     * <p>rsdProp2UpdFlg__ を取得します。
     * @return rsdProp2UpdFlg
     */
    public boolean isRsdProp2UpdFlg() {
        return rsdProp2UpdFlg__;
    }
    /**
     * <p>rsdProp2UpdFlg__ をセットします。
     * @param rsdProp2UpdFlg rsdProp2UpdFlg__
     */
    public void setRsdProp2UpdFlg(boolean rsdProp2UpdFlg) {
        rsdProp2UpdFlg__ = rsdProp2UpdFlg;
    }
    /**
     * <p>rsdProp3UpdFlg__ を取得します。
     * @return rsdProp3UpdFlg
     */
    public boolean isRsdProp3UpdFlg() {
        return rsdProp3UpdFlg__;
    }
    /**
     * <p>rsdProp3UpdFlg__ をセットします。
     * @param rsdProp3UpdFlg rsdProp3UpdFlg__
     */
    public void setRsdProp3UpdFlg(boolean rsdProp3UpdFlg) {
        rsdProp3UpdFlg__ = rsdProp3UpdFlg;
    }
    /**
     * <p>rsdProp4UpdFlg__ を取得します。
     * @return rsdProp4UpdFlg
     */
    public boolean isRsdProp4UpdFlg() {
        return rsdProp4UpdFlg__;
    }
    /**
     * <p>rsdProp4UpdFlg__ をセットします。
     * @param rsdProp4UpdFlg rsdProp4UpdFlg__
     */
    public void setRsdProp4UpdFlg(boolean rsdProp4UpdFlg) {
        rsdProp4UpdFlg__ = rsdProp4UpdFlg;
    }
    /**
     * <p>rsdProp5UpdFlg__ を取得します。
     * @return rsdProp5UpdFlg
     */
    public boolean isRsdProp5UpdFlg() {
        return rsdProp5UpdFlg__;
    }
    /**
     * <p>rsdProp5UpdFlg__ をセットします。
     * @param rsdProp5UpdFlg rsdProp5UpdFlg__
     */
    public void setRsdProp5UpdFlg(boolean rsdProp5UpdFlg) {
        rsdProp5UpdFlg__ = rsdProp5UpdFlg;
    }
    /**
     * <p>rsdProp6UpdFlg__ を取得します。
     * @return rsdProp6UpdFlg
     */
    public boolean isRsdProp6UpdFlg() {
        return rsdProp6UpdFlg__;
    }
    /**
     * <p>rsdProp6UpdFlg__ をセットします。
     * @param rsdProp6UpdFlg rsdProp6UpdFlg__
     */
    public void setRsdProp6UpdFlg(boolean rsdProp6UpdFlg) {
        rsdProp6UpdFlg__ = rsdProp6UpdFlg;
    }
    /**
     * <p>rsdProp7UpdFlg__ を取得します。
     * @return rsdProp7UpdFlg
     */
    public boolean isRsdProp7UpdFlg() {
        return rsdProp7UpdFlg__;
    }
    /**
     * <p>rsdProp7UpdFlg__ をセットします。
     * @param rsdProp7UpdFlg rsdProp7UpdFlg__
     */
    public void setRsdProp7UpdFlg(boolean rsdProp7UpdFlg) {
        rsdProp7UpdFlg__ = rsdProp7UpdFlg;
    }
    /**
     * <p>rsdRsdBikoUpdFlg__ を取得します。
     * @return rsdRsdBikoUpdFlg
     */
    public boolean isRsdRsdBikoUpdFlg() {
        return rsdRsdBikoUpdFlg__;
    }
    /**
     * <p>rsdRsdBikoUpdFlg__ をセットします。
     * @param rsdRsdBikoUpdFlg rsdRsdBikoUpdFlg__
     */
    public void setRsdRsdBikoUpdFlg(boolean rsdRsdBikoUpdFlg) {
        rsdRsdBikoUpdFlg__ = rsdRsdBikoUpdFlg;
    }
    /**
     * <p>targetSiset__ を取得します。
     * @return targetSiset
     */
    public String[] getTargetSiset() {
        return targetSiset__;
    }
    /**
     * <p>targetSiset__ をセットします。
     * @param targetSiset targetSiset__
     */
    public void setTargetSiset(String[] targetSiset) {
        targetSiset__ = targetSiset;
    }
}