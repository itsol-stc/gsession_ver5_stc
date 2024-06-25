package jp.groupsession.v2.anp.anp040kn;

import jp.groupsession.v2.anp.anp040.Anp040ParamModel;


/**
 * <br>[機  能] 個人設定・表示設定確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp040knParamModel extends Anp040ParamModel {

    /** 表示用のグループ名 */
    private String anp040knDispGrpNm__ = null;

    /**
     * <p>表示用グループ名を取得します
     * @return anp040knDispGrpNm
     */
    public String getAnp040knDispGrpNm() {
        return anp040knDispGrpNm__;
    }

    /**
     * <p>表示用グループ名を設定します
     * @param anp040knDispGrpNm セットする anp040knDispGrpNm
     */
    public void setAnp040knDispGrpNm(String anp040knDispGrpNm) {
        anp040knDispGrpNm__ = anp040knDispGrpNm;
    }

}