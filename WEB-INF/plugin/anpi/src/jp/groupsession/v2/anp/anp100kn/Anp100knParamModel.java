package jp.groupsession.v2.anp.anp100kn;

import jp.groupsession.v2.anp.anp100.Anp100ParamModel;

/**
 * <br>[機  能] 管理者設定・メールテンプレート編集確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp100knParamModel extends Anp100ParamModel {

    /** 本文１（表示用） */
    private String anp100knDspText1__;
    /** 本文２（表示用） */
    private String anp100knDspText2__;

    /**
     * <p>anp100knDspText1 を取得します。
     * @return anp100knDspText1
     */
    public String getAnp100knDspText1() {
        return anp100knDspText1__;
    }
    /**
     * <p>anp100knDspText1 をセットします。
     * @param anp100knDspText1 anp100knDspText1
     */
    public void setAnp100knDspText1(String anp100knDspText1) {
        anp100knDspText1__ = anp100knDspText1;
    }
    /**
     * <p>anp100knDspText2 を取得します。
     * @return anp100knDspText2
     */
    public String getAnp100knDspText2() {
        return anp100knDspText2__;
    }
    /**
     * <p>anp100knDspText2 をセットします。
     * @param anp100knDspText2 anp100knDspText2
     */
    public void setAnp100knDspText2(String anp100knDspText2) {
        anp100knDspText2__ = anp100knDspText2;
    }
}