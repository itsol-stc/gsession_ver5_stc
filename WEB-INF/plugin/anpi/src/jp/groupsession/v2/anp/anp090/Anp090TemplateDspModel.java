package jp.groupsession.v2.anp.anp090;

import java.io.Serializable;

/**
 * <br>[機  能] 安否確認 メールテンプレート一覧画面に表示するテンプレート情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp090TemplateDspModel implements Serializable {

    /** テンプレートSID */
    private int templateSid__ = 0;
    /** テンプレート名 */
    private String templateName__ = null;

    /** 並び順 */
    private int templateSort__;
    /** 表示順(画面用) */
    private String templateValue__ = null;

    /**
     * <p>templateSid を取得します。
     * @return templateSid
     */
    public int getTemplateSid() {
        return templateSid__;
    }
    /**
     * <p>templateSid をセットします。
     * @param templateSid templateSid
     */
    public void setTemplateSid(int templateSid) {
        templateSid__ = templateSid;
    }
    /**
     * <p>templateName を取得します。
     * @return templateName
     */
    public String getTemplateName() {
        return templateName__;
    }
    /**
     * <p>templateName をセットします。
     * @param templateName templateName
     */
    public void setTemplateName(String templateName) {
        templateName__ = templateName;
    }
    /**
     * <p>templateSort を取得します。
     * @return templateSort
     */
    public int getTemplateSort() {
        return templateSort__;
    }
    /**
     * <p>templateSort をセットします。
     * @param templateSort templateSort
     */
    public void setTemplateSort(int templateSort) {
        templateSort__ = templateSort;
    }
    /**
     * <p>templateValue を取得します。
     * @return templateValue
     */
    public String getTemplateValue() {
        return templateValue__;
    }
    /**
     * <p>templateValue をセットします。
     * @param templateValue templateValue
     */
    public void setTemplateValue(String templateValue) {
        templateValue__ = templateValue;
    }
}
