package jp.groupsession.v2.fil.fil200;

import jp.groupsession.v2.fil.fil100.Fil100Form;

/**
 * <br>[機  能] 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil200Form extends Fil100Form {
    /**ショートメール使用可否*/
    private int canUseSml__;
    /**個人キャビネット使用可否*/
    private int canUsePrivate__;
    /** メイン管理者権限フラグ */
    private boolean fil200GsAdminFlg__ = false;

    /**
     * <p>canUseSml を取得します。
     * @return canUseSml
     */
    public int getCanUseSml() {
        return canUseSml__;
    }

    /**
     * <p>canUseSml をセットします。
     * @param canUseSml canUseSml
     */
    public void setCanUseSml(int canUseSml) {
        canUseSml__ = canUseSml;
    }

    /**
     * <p>canUsePrivate を取得します。
     * @return canUsePrivate
     */
    public int getCanUsePrivate() {
        return canUsePrivate__;
    }

    /**
     * <p>canUsePrivate をセットします。
     * @param canUsePrivate canUsePrivate
     */
    public void setCanUsePrivate(int canUsePrivate) {
        canUsePrivate__ = canUsePrivate;
    }

    /**
     * <p>fil200GsAdminFlg を取得します。
     * @return fil200GsAdminFlg
     */
    public boolean isFil200GsAdminFlg() {
        return fil200GsAdminFlg__;
    }

    /**
     * <p>fil200GsAdminFlg をセットします。
     * @param fil200GsAdminFlg fil200GsAdminFlg
     */
    public void setFil200GsAdminFlg(boolean fil200GsAdminFlg) {
        fil200GsAdminFlg__ = fil200GsAdminFlg;
    }

}
