package jp.groupsession.v2.enq.enq800;

import jp.groupsession.v2.enq.enq010.Enq010Form;

/**
 * <br>[機  能] アンケート 個人設定メニュー画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq800Form extends Enq010Form {


    /** 遷移元 メイン画面の個人設定メニューから遷移した場合:2 その他:0 */
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
