package jp.groupsession.v2.anp.anp100;

import jp.groupsession.v2.anp.anp090.Anp090ParamModel;

/**
 * <br>[機  能] 管理者設定・メールテンプレート編集画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp100ParamModel extends Anp090ParamModel {

    /** テンプレート名 */
    private String anp100Title__;
    /** 件名 */
    private String anp100Subject__;
    /** 本文１ */
    private String anp100Text1__;
    /** 本文２ */
    private String anp100Text2__;
    /**
     * <p>anp100Title を取得します。
     * @return anp100Title
     */
    public String getAnp100Title() {
        return anp100Title__;
    }
    /**
     * <p>anp100Title をセットします。
     * @param anp100Title anp100Title
     */
    public void setAnp100Title(String anp100Title) {
        anp100Title__ = anp100Title;
    }
    /**
     * <p>anp100Subject を取得します。
     * @return anp100Subject
     */
    public String getAnp100Subject() {
        return anp100Subject__;
    }
    /**
     * <p>anp100Subject をセットします。
     * @param anp100Subject anp100Subject
     */
    public void setAnp100Subject(String anp100Subject) {
        anp100Subject__ = anp100Subject;
    }
    /**
     * <p>anp100Text1 を取得します。
     * @return anp100Text1
     */
    public String getAnp100Text1() {
        return anp100Text1__;
    }
    /**
     * <p>anp100Text1 をセットします。
     * @param anp100Text1 anp100Text1
     */
    public void setAnp100Text1(String anp100Text1) {
        anp100Text1__ = anp100Text1;
    }
    /**
     * <p>anp100Text2 を取得します。
     * @return anp100Text2
     */
    public String getAnp100Text2() {
        return anp100Text2__;
    }
    /**
     * <p>anp100Text2 をセットします。
     * @param anp100Text2 anp100Text2
     */
    public void setAnp100Text2(String anp100Text2) {
        anp100Text2__ = anp100Text2;
    }
}