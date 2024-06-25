package jp.groupsession.v2.zsk.zsk070;

import jp.groupsession.v2.zsk.zsk010.Zsk010Form;

/**
 * <br>[機  能] 在席管理 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk070Form extends Zsk010Form {

    /** 座席表メンバー表示設定可能か */
    private boolean zsk070canMemEdit__ = false;

    /**
     * <p>zsk070canMemEdit を取得します。
     * @return zsk070canMemEdit
     */
    public boolean isZsk070canMemEdit() {
        return zsk070canMemEdit__;
    }
    /**
     * <p>zsk070canMemEdit をセットします。
     * @param zsk070canMemEdit zsk070canMemEdit
     */
    public void setZsk070canMemEdit(boolean zsk070canMemEdit) {
        zsk070canMemEdit__ = zsk070canMemEdit;
    }
}
