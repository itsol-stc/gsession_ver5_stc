package jp.groupsession.v2.cir.cir070;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir010.Cir010Form;

/**
 * <br>[機  能] 回覧板 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir070Form extends Cir010Form {

    /** 回覧板自動削除フラグ */
    private int cir070CirDelAdminConf__ = GSConstCircular.CIR_DEL_KBN_ADM_SETTING;

    /** 初期値 表示区分 */
    private int cir070InitSettingDsp__ = 0;

    /**
     * <p>cir070CirDelAdminConf を取得します。
     * @return cir070CirDelAdminConf
     */
    public int getCir070CirDelAdminConf() {
        return cir070CirDelAdminConf__;
    }

    /**
     * <p>cir070CirDelAdminConf をセットします。
     * @param cir070CirDelAdminConf cir070CirDelAdminConf
     */
    public void setCir070CirDelAdminConf(int cir070CirDelAdminConf) {
        cir070CirDelAdminConf__ = cir070CirDelAdminConf;
    }

    /**
     * <p>cir070InitSettingDsp を取得します。
     * @return cir070InitSettingDsp
     */
    public int getCir070InitSettingDsp() {
        return cir070InitSettingDsp__;
    }

    /**
     * <p>cir070InitSettingDsp をセットします。
     * @param cir070InitSettingDsp cir070InitSettingDsp
     */
    public void setCir070InitSettingDsp(int cir070InitSettingDsp) {
        cir070InitSettingDsp__ = cir070InitSettingDsp;
    }

}