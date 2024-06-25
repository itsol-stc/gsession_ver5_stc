package jp.groupsession.v2.anp.anp121kn;

import jp.groupsession.v2.anp.anp121.Anp121ParamModel;


/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121knParamModel extends Anp121ParamModel {

    /** 取込みファイル名 */
    private String anp121knFileName__ = null;

    /**
     * <p>取り込みファイル名を取得します
     * @return anp121knFileName
     */
    public String getAnp121knFileName() {
        return anp121knFileName__;
    }

    /**
     * <p>取り込みファイル名を設定します
     * @param anp121knFileName セットする anp121knFileName
     */
    public void setAnp121knFileName(String anp121knFileName) {
        anp121knFileName__ = anp121knFileName;
    }
}