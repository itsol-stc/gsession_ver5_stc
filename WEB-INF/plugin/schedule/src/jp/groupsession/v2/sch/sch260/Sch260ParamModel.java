package jp.groupsession.v2.sch.sch260;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.sch.sch250.Sch250ParamModel;
import jp.groupsession.v2.sch.ui.parts.schedulelist.ScheduleListSelector;

/**
 * <br>[機  能] 表示リスト登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch260ParamModel extends Sch250ParamModel {

    /** マイスケジュール名 */
    private String sch260scheduleName__ = null;
    /** 対象 */
    private String[] sch260subject__ = null;
    /** 対象 グループ */
    private int sch260subjectGroup__  = -1;
    /** 対象(選択用) */
    private String[] sch260subjectSelect__  = null;
    /** 対象(未選択用) */
    private String[] sch260subjectNoSelect__ = null;
    /** 備考 */
    private String sch260biko__ = null;
    /** 初期表示フラグ */
    private int sch260initFlg__ = 0;
    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** 対象並び順 */
    private String[] sch260orderList__ = null;
    /** 対象 選択UI*/
    private ScheduleListSelector sch260subjectUI__ = null;
    /**
     * <p>sch260scheduleName を取得します。
     * @return sch260scheduleName
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260scheduleName__
     */
    public String getSch260scheduleName() {
        return sch260scheduleName__;
    }
    /**
     * <p>sch260scheduleName をセットします。
     * @param sch260scheduleName sch260scheduleName
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260scheduleName__
     */
    public void setSch260scheduleName(String sch260scheduleName) {
        sch260scheduleName__ = sch260scheduleName;
    }
    /**
     * <p>sch260subject を取得します。
     * @return sch260subject
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subject__
     */
    public String[] getSch260subject() {
        return sch260subject__;
    }
    /**
     * <p>sch260subject をセットします。
     * @param sch260subject sch260subject
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subject__
     */
    public void setSch260subject(String[] sch260subject) {
        sch260subject__ = sch260subject;
    }
    /**
     * <p>sch260subjectGroup を取得します。
     * @return sch260subjectGroup
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectGroup__
     */
    public int getSch260subjectGroup() {
        return sch260subjectGroup__;
    }
    /**
     * <p>sch260subjectGroup をセットします。
     * @param sch260subjectGroup sch260subjectGroup
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectGroup__
     */
    public void setSch260subjectGroup(int sch260subjectGroup) {
        sch260subjectGroup__ = sch260subjectGroup;
    }
    /**
     * <p>sch260subjectSelect を取得します。
     * @return sch260subjectSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectSelect__
     */
    public String[] getSch260subjectSelect() {
        return sch260subjectSelect__;
    }
    /**
     * <p>sch260subjectSelect をセットします。
     * @param sch260subjectSelect sch260subjectSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectSelect__
     */
    public void setSch260subjectSelect(String[] sch260subjectSelect) {
        sch260subjectSelect__ = sch260subjectSelect;
    }
    /**
     * <p>sch260subjectNoSelect を取得します。
     * @return sch260subjectNoSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectNoSelect__
     */
    public String[] getSch260subjectNoSelect() {
        return sch260subjectNoSelect__;
    }
    /**
     * <p>sch260subjectNoSelect をセットします。
     * @param sch260subjectNoSelect sch260subjectNoSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectNoSelect__
     */
    public void setSch260subjectNoSelect(String[] sch260subjectNoSelect) {
        sch260subjectNoSelect__ = sch260subjectNoSelect;
    }
    /**
     * <p>sch260biko を取得します。
     * @return sch260biko
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#sch260biko__
     */
    public String getSch260biko() {
        return sch260biko__;
    }
    /**
     * <p>sch260biko をセットします。
     * @param sch260biko sch260biko
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#sch260biko__
     */
    public void setSch260biko(String sch260biko) {
        sch260biko__ = sch260biko;
    }
    /**
     * <p>sch260initFlg を取得します。
     * @return sch260initFlg
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#sch260initFlg__
     */
    public int getSch260initFlg() {
        return sch260initFlg__;
    }
    /**
     * <p>sch260initFlg をセットします。
     * @param sch260initFlg sch260initFlg
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#sch260initFlg__
     */
    public void setSch260initFlg(int sch260initFlg) {
        sch260initFlg__ = sch260initFlg;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#groupCombo__
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#groupCombo__
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>sch260orderList を取得します。
     * @return sch260orderList
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260orderList__
     */
    public String[] getSch260orderList() {
        return sch260orderList__;
    }
    /**
     * <p>sch260orderList をセットします。
     * @param sch260orderList sch260orderList
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260orderList__
     */
    public void setSch260orderList(String[] sch260orderList) {
        sch260orderList__ = sch260orderList;
    }
    /**
     * <p>sch260subjectUI を取得します。
     * @return sch260subjectUI
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#sch260subjectUI__
     */
    public ScheduleListSelector getSch260subjectUI() {
        return sch260subjectUI__;
    }
    /**
     * <p>sch260subjectUI をセットします。
     * @param sch260subjectUI sch260subjectUI
     * @see jp.groupsession.v2.sch.sch260.Sch260ParamModel#sch260subjectUI__
     */
    public void setSch260subjectUI(ScheduleListSelector sch260subjectUI) {
        sch260subjectUI__ = sch260subjectUI;
    }
}
