package jp.groupsession.v2.anp.anp090;

import java.util.List;

import jp.groupsession.v2.anp.anp070.Anp070Form;

/**
 * <br>[機  能] 管理者設定・メールテンプレート選択画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp090Form extends Anp070Form {

    /** メールテンプレート一覧 */
    private List<Anp090TemplateDspModel> anp090TempList__;
    /** 選択メールテンプレートSID */
    private String anp090SelectSid__;
    /** チェックされている並び順 */
    private String anp090SortTemplate__ = null;
    /** 並び替え対象のラベル */
    private String[] anp090SortLabel__ = null;
    /**
     * <p>anp090TempList を取得します。
     * @return anp090TempList
     */
    public List<Anp090TemplateDspModel> getAnp090TempList() {
        return anp090TempList__;
    }
    /**
     * <p>anp090TempList をセットします。
     * @param anp090TempList anp090TempList
     */
    public void setAnp090TempList(List<Anp090TemplateDspModel> anp090TempList) {
        anp090TempList__ = anp090TempList;
    }
    /**
     * <p>anp090SelectSid を取得します。
     * @return anp090SelectSid
     */
    public String getAnp090SelectSid() {
        return anp090SelectSid__;
    }
    /**
     * <p>anp090SelectSid をセットします。
     * @param anp090SelectSid anp090SelectSid
     */
    public void setAnp090SelectSid(String anp090SelectSid) {
        anp090SelectSid__ = anp090SelectSid;
    }
    /**
     * <p>anp090SortTemplate を取得します。
     * @return anp090SortTemplate
     */
    public String getAnp090SortTemplate() {
        return anp090SortTemplate__;
    }
    /**
     * <p>anp090SortTemplate をセットします。
     * @param anp090SortTemplate anp090SortTemplate
     */
    public void setAnp090SortTemplate(String anp090SortTemplate) {
        anp090SortTemplate__ = anp090SortTemplate;
    }
    /**
     * <p>anp090SortLabel を取得します。
     * @return anp090SortLabel
     */
    public String[] getAnp090SortLabel() {
        return anp090SortLabel__;
    }
    /**
     * <p>anp090SortLabel をセットします。
     * @param anp090SortLabel anp090SortLabel
     */
    public void setAnp090SortLabel(String[] anp090SortLabel) {
        anp090SortLabel__ = anp090SortLabel;
    }


}