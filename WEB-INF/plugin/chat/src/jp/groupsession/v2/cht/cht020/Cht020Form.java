package jp.groupsession.v2.cht.cht020;

import jp.groupsession.v2.cht.cht010.Cht010Form;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能] チャット 管理者設定のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht020Form extends Cht010Form {

    /** 遷移元 メイン個人設定:2 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

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
