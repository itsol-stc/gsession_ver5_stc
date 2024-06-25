package jp.groupsession.v2.rsv.model.other;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 拡張コンボボックスのデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExtendedLabelValueModel extends LabelValueBean {

    /** コンボボックスのスタイル指定用区分 */
    private String styleClass__;
    /** 閲覧区分 */
    private boolean viewKbn__ = true;

    /**
     * <p>コンストラクタ
     * @param label ラベル
     * @param value バリュー
     */
    public ExtendedLabelValueModel(String label, String value) {
        setLabel(label);
        setValue(value);
    }

    /**
     * <p>コンストラクタ(選択不可ラベル作成時に使用）
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル指定
     */
    public ExtendedLabelValueModel(String label, String value, String style) {
        setLabel(label);
        setValue(value);
        setStyleClass(style);
    }

    /**
     * <p>コンストラクタ(選択不可ラベル作成時に使用）
     * @param label ラベル
     * @param value バリュー
     * @param viewKbn スタイル指定
     */
    public ExtendedLabelValueModel(String label, String value, boolean viewKbn) {
        setLabel(label);
        setValue(value);
        setStyleClass(null);
        setViewKbn(viewKbn);
    }

    /**
     * <p>コンストラクタ(選択不可ラベル作成時に使用）
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル指定
     * @param viewKbn 閲覧区分
     */
    public ExtendedLabelValueModel(String label, String value, String style, boolean viewKbn) {
        setLabel(label);
        setValue(value);
        setStyleClass(style);
        setViewKbn(viewKbn);
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
