package jp.groupsession.v2.api.api010;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
/**
 *
 * <br>[機  能] API管理者設定 パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api010ParamModel extends AbstractParamModel {
    /** 遷移元 メイン管理者メニュー画面から遷移した場合:1 その他:0*/
    private int backScreen__ = 0;

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     * @see jp.groupsession.v2.api.api010.Api010ParamModel#backScreen__
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     * @see jp.groupsession.v2.api.api010.Api010ParamModel#backScreen__
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }

}
