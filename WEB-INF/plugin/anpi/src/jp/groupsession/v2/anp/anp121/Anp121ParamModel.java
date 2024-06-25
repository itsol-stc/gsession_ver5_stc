package jp.groupsession.v2.anp.anp121;

import java.util.List;

import jp.groupsession.v2.anp.anp110.Anp110ParamModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121ParamModel extends Anp110ParamModel {

    /** 添付ファイル(コンボで選択中) */
    private String[] anp121selectFile__ = null;

    /** 取り込みファイルコンボ */
    private List<LabelValueBean> anp121fileCombo__ = null;

    /**
     * <p>添付ファイルを取得します
     * @return anp121selectFile
     */
    public String[] getAnp121selectFile() {
        return anp121selectFile__;
    }

    /**
     * <p>添付ファイルを設定します
     * @param anp121selectFile セットする anp121selectFile
     */
    public void setAnp121selectFile(String[] anp121selectFile) {
        anp121selectFile__ = anp121selectFile;
    }

    /**
     * <p>取り込みファイルコンボを取得します
     * @return anp121fileCombo
     */
    public List<LabelValueBean> getAnp121fileCombo() {
        return anp121fileCombo__;
    }

    /**
     * <p>取り込みファイルコンボを設定します
     * @param anp121fileCombo セットする anp121fileCombo
     */
    public void setAnp121fileCombo(List<LabelValueBean> anp121fileCombo) {
        anp121fileCombo__ = anp121fileCombo;
    }
}