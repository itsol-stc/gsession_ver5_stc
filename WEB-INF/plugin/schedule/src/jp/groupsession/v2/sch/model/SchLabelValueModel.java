package jp.groupsession.v2.sch.model;

import org.apache.struts.util.LabelValueBean;

/**
 * <p>Extends LabelValueBean For Schedule Plugin
 *
 * @author JTS
 */
public class SchLabelValueModel extends LabelValueBean {

    /** コンボボックスのスタイル指定用区分 */
    private String styleClass__;
    /** 閲覧区分 */
    private boolean viewKbn__ = true;

    /**
     * コンストラクタ
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル指定
     */
    public SchLabelValueModel(String label, String value, String style) {
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
    /**
     * <p>viewKbn を取得します。
     * @return viewKbn
     */
    public boolean isViewKbn() {
        return viewKbn__;
    }
    /**
     * <p>viewKbn をセットします。
     * @param viewKbn viewKbn
     */
    public void setViewKbn(boolean viewKbn) {
        viewKbn__ = viewKbn;
    }
}
