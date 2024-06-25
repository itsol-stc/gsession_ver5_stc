package jp.groupsession.v2.api.api010;

import jp.groupsession.v2.struts.AbstractGsForm;
/**
 *
 * <br>[機  能] API管理者設定 フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api010Form extends AbstractGsForm {
    /** 遷移元 メイン管理者メニュー画面から遷移した場合:1 その他:0*/
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
