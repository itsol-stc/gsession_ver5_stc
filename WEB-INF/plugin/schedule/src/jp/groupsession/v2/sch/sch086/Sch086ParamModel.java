package jp.groupsession.v2.sch.sch086;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sch.sch080.Sch080ParamModel;

/**
 * <br>[機  能] スケジュール 管理者設定 スケジュール初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch086ParamModel extends Sch080ParamModel {

    /** 初期表示フラグ */
    private int sch086init__ = 0;

    /**時間 選択区分*/
    private int sch086TimeType__ = GSConstSchedule.SAD_INITIME_STYPE_USER;
    /** 開始 時 */
    private int sch086DefFrH__ = -1;
    /** 開始 分 */
    private int sch086DefFrM__ = -1;
    /** 終了 時 */
    private int sch086DefToH__ = -1;
    /** 終了 分 */
    private int sch086DefToM__ = -1;
    /** 開始時間 */
    private String sch086DefFrTime__ = null;
    /** 開始時間 */
    private String sch086DefToTime__ = null;

    /** 編集権限 選択区分 */
    private int sch086EditType__ = GSConstSchedule.SAD_INIEDIT_STYPE_USER;
    /** 編集権限 */
    private int sch086Edit__ = GSConstSchedule.EDIT_CONF_NONE;

    /** 公開区分 選択区分 */
    private int sch086PublicType__ = GSConstSchedule.SAD_INIPUBLIC_STYPE_USER;
    /** 公開区分 */
    private int sch086Public__ = GSConstSchedule.DSP_PUBLIC;

    /** 同時編集 選択区分 */
    private int sch086SameType__ = GSConstSchedule.SAD_INISAME_STYPE_USER;
    /** 同時編集 */
    private int sch086Same__ = GSConstSchedule.SAME_EDIT_ON;

    /** デフォルトユーザSID  */
    private int sch086PubDefUsrSid__ = 0;
    /** 公開対象 ユーザ グループ */
    private String sch086PubUserGroup__ =
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象 グループコンボ */
    private List<LabelValueBean> sch086PubGroupCombo__ = null;
    /** 公開対象ユーザ・グループSID */
    private String[] sch086PubUsrGrpSid__ = new String[0];
    /** 追加済み 公開対象ユーザ・グループSID */
    private String[] sch086LeftUsrGrpSid__ = new String[0];
    /** 追加用 公開対象ユーザ・グループSID */
    private String[] sch086RightUsrGrpSid__ = new String[0];

    /** 分単位 */
    private int sch086HourDiv__ = 0;
    /** 公開対象ユーザ選択 UI*/
    private UserGroupSelector sch086PubUsrGrpSidUI__ = null;

    /**
     * <p>sch086Edit を取得します。
     * @return sch086Edit
     */
    public int getSch086Edit() {
        return sch086Edit__;
    }
    /**
     * <p>sch086Edit をセットします。
     * @param sch086Edit sch086Edit
     */
    public void setSch086Edit(int sch086Edit) {
        sch086Edit__ = sch086Edit;
    }
    /***
     * <p>sch086TimeTypeを取得します。
     * @return sch086TimeType
     */
    public int getSch086TimeType() {
        return sch086TimeType__;
    }
    /**
     * <p>sch086TimeTypeをセットします。
     * @param sch086TimeType sch086TimeType
     *
     * */
    public void setSch086TimeType(int sch086TimeType) {
        sch086TimeType__ = sch086TimeType;
    }
    /**
     * <p>sch091DefFrH を取得します。
     * @return sch091DefFrH
     */
    public int getSch086DefFrH() {
        return sch086DefFrH__;
    }

    /**
     * <p>sch086DefFrH をセットします。
     * @param sch086DefFrH sch086DefFrH
     */
    public void setSch086DefFrH(int sch086DefFrH) {
        sch086DefFrH__ = sch086DefFrH;
    }

    /**
     * <p>sch086DefFrM を取得します。
     * @return sch086DefFrM
     */
    public int getSch086DefFrM() {
        return sch086DefFrM__;
    }

    /**
     * <p>sch086DefFrM をセットします。
     * @param sch086DefFrM sch086DefFrM
     */
    public void setSch086DefFrM(int sch086DefFrM) {
        sch086DefFrM__ = sch086DefFrM;
    }

    /**
     * <p>sch086DefToH を取得します。
     * @return sch086DefToH
     */
    public int getSch086DefToH() {
        return sch086DefToH__;
    }

    /**
     * <p>sch086DefToH をセットします。
     * @param sch086DefToH sch086DefToH
     */
    public void setSch086DefToH(int sch086DefToH) {
        sch086DefToH__ = sch086DefToH;
    }

    /**
     * <p>sch086DefToM を取得します。
     * @return sch086DefToM
     */
    public int getSch086DefToM() {
        return sch086DefToM__;
    }

    /**
     * <p>sch086DefToM をセットします。
     * @param sch086DefToM sch086DefToM
     */
    public void setSch086DefToM(int sch086DefToM) {
        sch086DefToM__ = sch086DefToM;
    }

    /**
     * <p>sch086DefFrTime を取得します。
     * @return sch086DefFrTime
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086DefFrTime__
     */
    public String getSch086DefFrTime() {
        return sch086DefFrTime__;
    }

    /**
     * <p>sch086DefFrTime をセットします。
     * @param sch086DefFrTime sch086DefFrTime
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086DefFrTime__
     */
    public void setSch086DefFrTime(String sch086DefFrTime) {
        sch086DefFrTime__ = sch086DefFrTime;
    }

    /**
     * <p>sch086DefToTime を取得します。
     * @return sch086DefToTime
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086DefToTime__
     */
    public String getSch086DefToTime() {
        return sch086DefToTime__;
    }

    /**
     * <p>sch086DefToTime をセットします。
     * @param sch086DefToTime sch086DefToTime
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086DefToTime__
     */
    public void setSch086DefToTime(String sch086DefToTime) {
        sch086DefToTime__ = sch086DefToTime;
    }

    /**
     * <p>sch086EditType を取得します。
     * @return sch086EditType
     */
    public int getSch086EditType() {
        return sch086EditType__;
    }
    /**
     * <p>sch086EditType をセットします。
     * @param sch086EditType sch086EditType
     */
    public void setSch086EditType(int sch086EditType) {
        sch086EditType__ = sch086EditType;
    }
    /**
     * <p>sch086init を取得します。
     * @return sch086init
     */
    public int getSch086init() {
        return sch086init__;
    }
    /**
     * <p>sch086init をセットします。
     * @param sch086init sch086init
     */
    public void setSch086init(int sch086init) {
        sch086init__ = sch086init;
    }
    /**
     * <p>sch086PublicType を取得します。
     * @return sch086PublicType
     */
    public int getSch086PublicType() {
        return sch086PublicType__;
    }
    /**
     * <p>sch086PublicType をセットします。
     * @param sch086PublicType sch086PublicType
     */
    public void setSch086PublicType(int sch086PublicType) {
        sch086PublicType__ = sch086PublicType;
    }
    /**
     * <p>sch086Public を取得します。
     * @return sch086Public
     */
    public int getSch086Public() {
        return sch086Public__;
    }
    /**
     * <p>sch086Public をセットします。
     * @param sch086Public sch086Public
     */
    public void setSch086Public(int sch086Public) {
        sch086Public__ = sch086Public;
    }
    /**
     * <p>sch086SameType を取得します。
     * @return sch086SameType
     */
    public int getSch086SameType() {
        return sch086SameType__;
    }
    /**
     * <p>sch086SameType をセットします。
     * @param sch086SameType sch086SameType
     */
    public void setSch086SameType(int sch086SameType) {
        sch086SameType__ = sch086SameType;
    }
    /**
     * <p>sch086Same を取得します。
     * @return sch086Same
     */
    public int getSch086Same() {
        return sch086Same__;
    }
    /**
     * <p>sch086Same をセットします。
     * @param sch086Same sch086Same
     */
    public void setSch086Same(int sch086Same) {
        sch086Same__ = sch086Same;
    }
    /**
     * <p>sch086PubDefUsrSid を取得します。
     * @return sch086PubDefUsrSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubDefUsrSid__
     */
    public int getSch086PubDefUsrSid() {
        return sch086PubDefUsrSid__;
    }
    /**
     * <p>sch086PubDefUsrSid をセットします。
     * @param sch086PubDefUsrSid sch086PubDefUsrSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubDefUsrSid__
     */
    public void setSch086PubDefUsrSid(int sch086PubDefUsrSid) {
        sch086PubDefUsrSid__ = sch086PubDefUsrSid;
    }
    /**
     * <p>sch086PubUserGroup を取得します。
     * @return sch086PubUserGroup
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubUserGroup__
     */
    public String getSch086PubUserGroup() {
        return sch086PubUserGroup__;
    }
    /**
     * <p>sch086PubUserGroup をセットします。
     * @param sch086PubUserGroup sch086PubUserGroup
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubUserGroup__
     */
    public void setSch086PubUserGroup(String sch086PubUserGroup) {
        sch086PubUserGroup__ = sch086PubUserGroup;
    }
    /**
     * <p>sch086PubGroupCombo を取得します。
     * @return sch086PubGroupCombo
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubGroupCombo__
     */
    public List<LabelValueBean> getSch086PubGroupCombo() {
        return sch086PubGroupCombo__;
    }
    /**
     * <p>sch086PubGroupCombo をセットします。
     * @param sch086PubGroupCombo sch086PubGroupCombo
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubGroupCombo__
     */
    public void setSch086PubGroupCombo(List<LabelValueBean> sch086PubGroupCombo) {
        sch086PubGroupCombo__ = sch086PubGroupCombo;
    }
    /**
     * <p>sch086PubUsrGrpSid を取得します。
     * @return sch086PubUsrGrpSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubUsrGrpSid__
     */
    public String[] getSch086PubUsrGrpSid() {
        return sch086PubUsrGrpSid__;
    }
    /**
     * <p>sch086PubUsrGrpSid をセットします。
     * @param sch086PubUsrGrpSid sch086PubUsrGrpSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086PubUsrGrpSid__
     */
    public void setSch086PubUsrGrpSid(String[] sch086PubUsrGrpSid) {
        sch086PubUsrGrpSid__ = sch086PubUsrGrpSid;
    }
    /**
     * <p>sch086LeftUsrGrpSid を取得します。
     * @return sch086LeftUsrGrpSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086LeftUsrGrpSid__
     */
    public String[] getSch086LeftUsrGrpSid() {
        return sch086LeftUsrGrpSid__;
    }
    /**
     * <p>sch086LeftUsrGrpSid をセットします。
     * @param sch086LeftUsrGrpSid sch086LeftUsrGrpSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086LeftUsrGrpSid__
     */
    public void setSch086LeftUsrGrpSid(String[] sch086LeftUsrGrpSid) {
        sch086LeftUsrGrpSid__ = sch086LeftUsrGrpSid;
    }
    /**
     * <p>sch086RightUsrGrpSid を取得します。
     * @return sch086RightUsrGrpSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086RightUsrGrpSid__
     */
    public String[] getSch086RightUsrGrpSid() {
        return sch086RightUsrGrpSid__;
    }
    /**
     * <p>sch086RightUsrGrpSid をセットします。
     * @param sch086RightUsrGrpSid sch086RightUsrGrpSid
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086RightUsrGrpSid__
     */
    public void setSch086RightUsrGrpSid(String[] sch086RightUsrGrpSid) {
        sch086RightUsrGrpSid__ = sch086RightUsrGrpSid;
    }
    /**
     * <p>sch086HourDiv を取得します。
     * @return sch086HourDiv
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086HourDiv__
     */
    public int getSch086HourDiv() {
        return sch086HourDiv__;
    }
    /**
     * <p>sch086HourDiv をセットします。
     * @param sch086HourDiv sch086HourDiv
     * @see jp.groupsession.v2.sch.sch086.Sch086Form#sch086HourDiv__
     */
    public void setSch086HourDiv(int sch086HourDiv) {
        sch086HourDiv__ = sch086HourDiv;
    }
    /**
     * <p>sch086PubUsrGrpSidUI を取得します。
     * @return sch086PubUsrGrpSidUI
     * @see jp.groupsession.v2.sch.sch086.Sch086ParamModel#sch086PubUsrGrpSidUI__
     */
    public UserGroupSelector getSch086PubUsrGrpSidUI() {
        return sch086PubUsrGrpSidUI__;
    }
    /**
     * <p>sch086PubUsrGrpSidUI をセットします。
     * @param sch086PubUsrGrpSidUI sch086PubUsrGrpSidUI
     * @see jp.groupsession.v2.sch.sch086.Sch086ParamModel#sch086PubUsrGrpSidUI__
     */
    public void setSch086PubUsrGrpSidUI(UserGroupSelector sch086PubUsrGrpSidUI) {
        sch086PubUsrGrpSidUI__ = sch086PubUsrGrpSidUI;
    }
}