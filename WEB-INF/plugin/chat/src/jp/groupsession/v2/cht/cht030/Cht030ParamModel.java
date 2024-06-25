package jp.groupsession.v2.cht.cht030;

import jp.groupsession.v2.cht.cht010.Cht010ParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能] チャット 個人設定のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht030ParamModel extends Cht010ParamModel {

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
