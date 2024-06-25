package jp.groupsession.v2.ntp.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <p>Extends LabelValueBean For Nippou Plugin
 *
 * @author JTS
 */
public class NtpLabelValueModel extends UsrLabelValueBean {
    /**
     * コンストラクタ
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル指定
     */
    public NtpLabelValueModel(String label, String value, String style) {
        setLabel(label);
        setValue(value);
        setStyleClass(style);
    }
    /**
     * コンストラクタ
     * @param usrMdl ユーザ情報
     * @param style スタイル指定
     */
    public NtpLabelValueModel(CmnUsrmInfModel usrMdl, String style) {
        super(usrMdl);
        setStyleClass(style);
    }
}
