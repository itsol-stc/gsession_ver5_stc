package jp.groupsession.v2.man.man420;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man420ParamModel extends AbstractParamModel {
    /** 自動ユーザインポートフラグ */
    private int man420UsrImpFlg__ = 0;
    /** ユーザインポート時間フラグ */
    private int man420UsrImpTimeSelect__ = 0;
    /** ユーザインポート開始時 */
    private String man420UsrFrHour__ = null;
    /** ユーザインポート時リスト */
    private ArrayList < LabelValueBean > man420UsrHourLabel__ = null;
    /** 初期表示フラグ */
    private int man420InitFlg__ = 1;
    /** インポート対象フォルダ */
    private String man420ImportFolder__ = "";
    /** インポート成功フォルダ */
    private String man420ImpSuccessFolder__ = "";
    /** インポート失敗フォルダ */
    private String man420ImpFailedFolder__ = "";

    /**
     * <p>man420UsrImpAble を取得します。
     * @return man420UsrImpAble
     */
    public int getMan420UsrImpFlg() {
        return man420UsrImpFlg__;
    }

    /**
     * <p>man420UsrImpFlg__ をセットします。
     * @param man420UsrImpFlg man420UsrImpFlg
     */
    public void setMan420UsrImpFlg(int man420UsrImpFlg) {
        man420UsrImpFlg__ = man420UsrImpFlg;
    }
    /**
     * <p>man420UsrImpTimeSelect を取得します。
     * @return man420UsrImpTimeSelect
     */
    public int getMan420UsrImpTimeSelect() {
        return man420UsrImpTimeSelect__;
    }

    /**
     * <p>man420UsrImpTimeSelect をセットします。
     * @param man420UsrImpTimeSelect man420UsrImpTimeSelect
     */
    public void setMan420UsrImpTimeSelect(int man420UsrImpTimeSelect) {
        man420UsrImpTimeSelect__ = man420UsrImpTimeSelect;
    }

    /**
     * <p>man420UsrHourLabel を取得します。
     * @return man420UsrHourLabel
     */
    public ArrayList<LabelValueBean> getMan420UsrHourLabel() {
        return man420UsrHourLabel__;
    }

    /**
     * <p>man420UsrHourLabel をセットします。
     * @param man420UsrHourLabel man420UsrHourLabel
     */
    public void setMan420UsrHourLabel(
            ArrayList<LabelValueBean> man420UsrHourLabel) {
        man420UsrHourLabel__ = man420UsrHourLabel;
    }

    /**
     * <p>man420UsrFrHour を取得します。
     * @return man420UsrFrHour
     */
    public String getMan420UsrFrHour() {
        return man420UsrFrHour__;
    }

    /**
     * <p>man420UsrFrHour をセットします。
     * @param man420UsrFrHour man420UsrFrHour
     */
    public void setMan420UsrFrHour(String man420UsrFrHour) {
        man420UsrFrHour__ = man420UsrFrHour;
    }

    /**
     * <p>man420InitFlg を取得します。
     * @return man420InitFlg
     */
    public int getMan420InitFlg() {
        return man420InitFlg__;
    }

    /**
     * <p>man420InitFlg をセットします。
     * @param man420InitFlg man420InitFlg
     */
    public void setMan420InitFlg(int man420InitFlg) {
        man420InitFlg__ = man420InitFlg;
    }

    /**
     * <p>man420ImportFolder を取得します。
     * @return man420ImportFolder
     */
    public String getMan420ImportFolder() {
        return man420ImportFolder__;
    }

    /**
     * <p>man420ImportFolder をセットします。
     * @param man420ImportFolder man420ImportFolder
     */
    public void setMan420ImportFolder(String man420ImportFolder) {
        man420ImportFolder__ = man420ImportFolder;
    }

    /**
     * <p>man420ImpSuccessFolder を取得します。
     * @return man420ImpSuccessFolder
     */
    public String getMan420ImpSuccessFolder() {
        return man420ImpSuccessFolder__;
    }

    /**
     * <p>man420ImpSuccessFolder をセットします。
     * @param man420ImpSuccessFolder man420ImpSuccessFolder
     */
    public void setMan420ImpSuccessFolder(String man420ImpSuccessFolder) {
        man420ImpSuccessFolder__ = man420ImpSuccessFolder;
    }

    /**
     * <p>man420ImpFailedFolder を取得します。
     * @return man420ImpFailedFolder
     */
    public String getMan420ImpFailedFolder() {
        return man420ImpFailedFolder__;
    }

    /**
     * <p>man420ImpFailedFolder をセットします。
     * @param man420ImpFailedFolder man420ImpFailedFolder
     */
    public void setMan420ImpFailedFolder(String man420ImpFailedFolder) {
        man420ImpFailedFolder__ = man420ImpFailedFolder;
    }
}
