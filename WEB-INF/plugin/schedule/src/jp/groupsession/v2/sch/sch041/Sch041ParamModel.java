package jp.groupsession.v2.sch.sch041;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sch.sch040.Sch040ParamModel;
import jp.groupsession.v2.sch.ui.parts.reserve.ShisetuSelector;
import jp.groupsession.v2.sch.ui.parts.sameuserselect.SameUserSelector;

/**
 * <br>[機  能] スケジュール繰り返し登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041ParamModel extends Sch040ParamModel {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch041ParamModel.class);

    /** 削除対象の会社ID */
    private String sch041delCompanyId__ = null;
    /** 削除対象の会社拠点ID */
    private String sch041delCompanyBaseId__ = null;
    /** タイトル色区分 */
    private int sch041colorKbn__ = GSConstSchedule.SAD_MSG_NO_ADD;

    /** 日リスト */
    private ArrayList < LabelValueBean > sch041DayLabel__ = null;
    /** 週リスト */
    private ArrayList < LabelValueBean > sch041WeekLabel__ = null;

    /** 拡張設定 日リスト */
    private ArrayList < LabelValueBean > sch041ExDayLabel__ = null;
    /** 拡張設定 日リスト */
    private ArrayList < LabelValueBean > sch041ExDayOfYearlyLabel__ = null;
    /** 拡張設定 日付設定区分リスト */
    private ArrayList < LabelValueBean > sch041ConfKbnLabel__ = null;

    /** 同時登録ユーザ選択 UI*/
    private SameUserSelector sch041SameUserUI__ = null;
    /** 公開対象ユーザ選択 UI*/
    private UserGroupSelector sch041DisplayTargetUI__ = null;
    /** 施設選択 UI*/
    private ShisetuSelector sch041SameReserveUI__ = null;


    /** リマインダー通知 通知設定欄モード */
    private SchEnumRemindMode sch041ReminderEditMode__;

    /**
     * <p>sch041ExDayLabel を取得します。
     * @return sch041ExDayLabel
     */
    public ArrayList<LabelValueBean> getSch041ExDayLabel() {
        return sch041ExDayLabel__;
    }

    /**
     * <p>sch041ExDayLabel をセットします。
     * @param sch041ExDayLabel sch041ExDayLabel
     */
    public void setSch041ExDayLabel(ArrayList<LabelValueBean> sch041ExDayLabel) {
        sch041ExDayLabel__ = sch041ExDayLabel;
    }

    /**
     * <p>sch041WeekLabel を取得します。
     * @return sch041WeekLabel
     */
    public ArrayList<LabelValueBean> getSch041WeekLabel() {
        return sch041WeekLabel__;
    }

    /**
     * <p>sch041WeekLabel をセットします。
     * @param sch041WeekLabel sch041WeekLabel
     */
    public void setSch041WeekLabel(ArrayList<LabelValueBean> sch041WeekLabel) {
        sch041WeekLabel__ = sch041WeekLabel;
    }

    /**
     * <p>sch041DayLabel を取得します。
     * @return sch041DayLabel
     */
    public ArrayList<LabelValueBean> getSch041DayLabel() {
        return sch041DayLabel__;
    }

    /**
     * <p>sch041DayLabel をセットします。
     * @param sch041DayLabel sch041DayLabel
     */
    public void setSch041DayLabel(ArrayList<LabelValueBean> sch041DayLabel) {
        sch041DayLabel__ = sch041DayLabel;
    }

    /**
     * <p>sch041ConfKbnLabel を取得します。
     * @return sch041ConfKbnLabel
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041ConfKbnLabel__
     */
    public ArrayList<LabelValueBean> getSch041ConfKbnLabel() {
        return sch041ConfKbnLabel__;
    }

    /**
     * <p>sch041ConfKbnLabel をセットします。
     * @param sch041ConfKbnLabel sch041ConfKbnLabel
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041ConfKbnLabel__
     */
    public void setSch041ConfKbnLabel(
            ArrayList<LabelValueBean> sch041ConfKbnLabel) {
        sch041ConfKbnLabel__ = sch041ConfKbnLabel;
    }

    /**
     * <p>sch041delCompanyBaseId を取得します。
     * @return sch041delCompanyBaseId
     */
    public String getSch041delCompanyBaseId() {
        return sch041delCompanyBaseId__;
    }

    /**
     * <p>sch041delCompanyBaseId をセットします。
     * @param sch041delCompanyBaseId sch041delCompanyBaseId
     */
    public void setSch041delCompanyBaseId(String sch041delCompanyBaseId) {
        sch041delCompanyBaseId__ = sch041delCompanyBaseId;
    }

    /**
     * <p>sch041delCompanyId を取得します。
     * @return sch041delCompanyId
     */
    public String getSch041delCompanyId() {
        return sch041delCompanyId__;
    }

    /**
     * <p>sch041delCompanyId をセットします。
     * @param sch041delCompanyId sch041delCompanyId
     */
    public void setSch041delCompanyId(String sch041delCompanyId) {
        sch041delCompanyId__ = sch041delCompanyId;
    }

    /**
     * <p>sch041colorKbn を取得します。
     * @return sch041colorKbn
     */
    public int getSch041colorKbn() {
        return sch041colorKbn__;
    }

    /**
     * <p>sch041colorKbn をセットします。
     * @param sch041colorKbn sch041colorKbn
     */
    public void setSch041colorKbn(int sch041colorKbn) {
        sch041colorKbn__ = sch041colorKbn;
    }

    /**
     * <p>sch041ExDayOfYearlyLabel を取得します。
     * @return sch041ExDayOfYearlyLabel
     */
    public ArrayList < LabelValueBean > getSch041ExDayOfYearlyLabel() {
        return sch041ExDayOfYearlyLabel__;
    }

    /**
     * <p>sch041ExDayOfYearlyLabel をセットします。
     * @param sch041ExDayOfYearlyLabel sch041ExDayOfYearlyLabel
     */
    public void setSch041ExDayOfYearlyLabel(ArrayList < LabelValueBean > sch041ExDayOfYearlyLabel) {
        sch041ExDayOfYearlyLabel__ = sch041ExDayOfYearlyLabel;
    }

    /**
     * <p>sch041ReminderEditMode を取得します。
     * @return sch041ReminderEditMode
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041ReminderEditMode__
     */
    public SchEnumRemindMode getSch041ReminderEditMode() {
        return sch041ReminderEditMode__;
    }

    /**
     * <p>sch041ReminderEditMode をセットします。
     * @param sch041ReminderEditMode sch041ReminderEditMode
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041ReminderEditMode__
     */
    public void setSch041ReminderEditMode(SchEnumRemindMode sch041ReminderEditMode) {
        sch041ReminderEditMode__ = sch041ReminderEditMode;
    }

    /**
     * <p>sch041SameUserUI を取得します。
     * @return sch041SameUserUI
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041SameUserUI__
     */
    public SameUserSelector getSch041SameUserUI() {
        return sch041SameUserUI__;
    }

    /**
     * <p>sch041SameUserUI をセットします。
     * @param sch041SameUserUI sch041SameUserUI
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041SameUserUI__
     */
    public void setSch041SameUserUI(SameUserSelector sch041SameUserUI) {
        sch041SameUserUI__ = sch041SameUserUI;
    }

    /**
     * <p>sch041DisplayTargetUI を取得します。
     * @return sch041DisplayTargetUI
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041DisplayTargetUI__
     */
    public UserGroupSelector getSch041DisplayTargetUI() {
        return sch041DisplayTargetUI__;
    }

    /**
     * <p>sch041DisplayTargetUI をセットします。
     * @param sch041DisplayTargetUI sch041DisplayTargetUI
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041DisplayTargetUI__
     */
    public void setSch041DisplayTargetUI(UserGroupSelector sch041DisplayTargetUI) {
        sch041DisplayTargetUI__ = sch041DisplayTargetUI;
    }

    /**
     * <p>sch041SameReserveUI を取得します。
     * @return sch041SameReserveUI
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041SameReserveUI__
     */
    public ShisetuSelector getSch041SameReserveUI() {
        return sch041SameReserveUI__;
    }

    /**
     * <p>sch041SameReserveUI をセットします。
     * @param sch041SameReserveUI sch041SameReserveUI
     * @see jp.groupsession.v2.sch.sch041.Sch041ParamModel#sch041SameReserveUI__
     */
    public void setSch041SameReserveUI(ShisetuSelector sch041SameReserveUI) {
        sch041SameReserveUI__ = sch041SameReserveUI;
    }
}
