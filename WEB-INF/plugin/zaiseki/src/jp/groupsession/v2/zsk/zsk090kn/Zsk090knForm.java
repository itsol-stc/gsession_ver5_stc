package jp.groupsession.v2.zsk.zsk090kn;

import jp.groupsession.v2.zsk.zsk090.Zsk090Form;

/**
 * <br>[機  能] 在席管理 個人設定 自動リロード時間設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */

public class Zsk090knForm extends Zsk090Form {

    /** 自動リロード時間 表示用 */
    private String zsk090knReloadTime__ = null;

    /**
     * <p>zsk090knReloadTime を取得します。
     * @return zsk090knReloadTime
     */
    public String getZsk090knReloadTime() {
        return zsk090knReloadTime__;
    }

    /**
     * <p>zsk090knReloadTime をセットします。
     * @param zsk090knReloadTime zsk090knReloadTime
     */
    public void setZsk090knReloadTime(String zsk090knReloadTime) {
        zsk090knReloadTime__ = zsk090knReloadTime;
    }

}
