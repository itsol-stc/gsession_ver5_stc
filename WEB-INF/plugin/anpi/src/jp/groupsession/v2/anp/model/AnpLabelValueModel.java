package jp.groupsession.v2.anp.model;

import org.apache.struts.util.LabelValueBean;

/**
 * <p>Extends LabelValueBean For Schedule Plugin
 *
 * @author JTS
 */
public class AnpLabelValueModel extends LabelValueBean {

    /** コンボボックスのスタイル指定用区分 */
    private String styleClass__;

    /**
     * コンストラクタ
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル指定
     */
    public AnpLabelValueModel(String label, String value, String style) {
        setLabel(label);
        setValue(value);
        setStyleClass(style);
    }
    /**
     * <p>styleClass を取得します。
     * @return styleClass
     */
    public String getStyleClass() {
        return styleClass__;
    }

    /**
     * <p>styleClass をセットします。
     * @param styleClass styleClass
     */
    public void setStyleClass(String styleClass) {
        styleClass__ = styleClass;
    }

}
