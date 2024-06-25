package jp.groupsession.v2.bmk.bmk120;

import jp.groupsession.v2.bmk.bmk010.Bmk010Form;

/**
 * <br>[機  能] 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk120Form extends Bmk010Form {
    /** 遷移元 メイン個人設定メニュー画面から遷移した場合:2 その他:0*/
    private int backScreen__ = 0;

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
}