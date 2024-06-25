package jp.groupsession.v2.enq.enq900;

import jp.groupsession.v2.enq.enq010.Enq010ParamModel;

/**
 * <br>[機  能] 管理者設定メニュー画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq900ParamModel extends Enq010ParamModel {

    /** 遷移元 メイン画面の管理者メニューから遷移した場合:1 その他:0 */
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
