package jp.groupsession.v2.anp.anp121kn;

import jp.groupsession.v2.anp.anp121.Anp121Form;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;


/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121knForm extends Anp121Form {

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

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     */
    public void setHiddenParamAnp121(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("anp121knFileName", anp121knFileName__);

    }

}