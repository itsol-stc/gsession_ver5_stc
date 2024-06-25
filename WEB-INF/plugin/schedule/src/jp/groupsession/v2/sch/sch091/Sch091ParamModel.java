package jp.groupsession.v2.sch.sch091;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sch.sch100.Sch100ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 個人設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch091ParamModel extends Sch100ParamModel {

    /** 開始 時 */
    private int sch091DefFrH__ = -1;
    /** 開始 分 */
    private int sch091DefFrM__ = -1;
    /** 終了 時 */
    private int sch091DefToH__ = -1;
    /** 終了 分 */
    private int sch091DefToM__ = -1;
    /** 開始時間 */
    private String sch091DefFrTime__ = null;
    /** 開始時間 */
    private String sch091DefToTime__ = null;
    /** 公開フラグ */
    private int sch091PubFlg__ = GSConstSchedule.DSP_PUBLIC;
    /** タイトル色 */
    private String sch091Fcolor__ = null;
    /** 編集権限 */
    private int sch091Edit__ = GSConstSchedule.EDIT_CONF_NONE;
    /** カラーコメントリスト */
    private ArrayList < String > sch091ColorMsgList__ = null;
    /** 同時修正 */
    private int sch091Same__ = GSConstSchedule.SAME_EDIT_ON;
    /** 時間 編集許可 */
    private boolean sch091TimeFlg__ = false;
    /** 編集権限 編集許可 */
    private boolean sch091EditFlg__ = false;
    /** 公開区分 編集許可 */
    private boolean sch091PublicFlg__ = false;
    /** 同時修正 編集許可 */
    private boolean sch091SameFlg__ = false;
    /** 初期表示フラグ */
    private int sch091initFlg__ = 0;
    /** タイトル色区分 */
    private int sch091colorKbn__ = GSConstSchedule.SAD_MSG_NO_ADD;

    /** リマインダー通知 表示フラグ */
    private int sch091ReminderDspFlg__ = GSConstSchedule.REMINDER_USE_YES;
    /** リマインダー通知 通知時間 */
    private String sch091ReminderTime__;

    /** デフォルト表示画面 */
    private int sch091DefDsp__ = -1;

    /** デフォルトユーザSID  */
    private int sch091PubDefUsrSid__ = 0;
    /** 公開対象 ユーザ グループ */
    private String sch091PubUserGroup__ =
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象 グループコンボ */
    private List<LabelValueBean> sch091PubGroupCombo__ = null;
    /** 公開対象ユーザ・グループSID */
    private String[] sch091PubUsrGrpSid__ = new String[0];
    /** 追加済み 公開対象ユーザ・グループSID */
    private String[] sch091LeftUsrGrpSid__ = new String[0];
    /** 追加用 公開対象ユーザ・グループSID */
    private String[] sch091RightUsrGrpSid__ = new String[0];

    /** 分単位 */
    private int sch091HourDiv__ = 0;
    /** 公開対象ユーザ選択 UI*/
    private UserGroupSelector sch091PubUsrGrpSidUI__ = null;

    /**
     * <p>sch091ColorMsgList を取得します。
     * @return sch091ColorMsgList
     */
    public ArrayList<String> getSch091ColorMsgList() {
        return sch091ColorMsgList__;
    }

    /**
     * <p>sch091ColorMsgList をセットします。
     * @param sch091ColorMsgList sch091ColorMsgList
     */
    public void setSch091ColorMsgList(ArrayList<String> sch091ColorMsgList) {
        sch091ColorMsgList__ = sch091ColorMsgList;
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Sch091ParamModel() {

//        //ラベル(時)
//        sch091HourLabel__ = new ArrayList<LabelValueBean>();
//        for (int i = 0; i < 24; i++) {
//            sch091HourLabel__.add(new LabelValueBean(i + "時", Integer.toString(i)));
//        }
//
//        //ラベル(分)
//        sch091MinuteLabel__ = new ArrayList<LabelValueBean>();
//        for (int i = 0; i < 60; i += GSConstSchedule.HOUR_MEMORI_COUNT) {
//            sch091MinuteLabel__.add(new LabelValueBean(i + "分", Integer.toString(i)));
//        }
    }

    /**
     * <p>sch091Edit を取得します。
     * @return sch091Edit
     */
    public int getSch091Edit() {
        return sch091Edit__;
    }

    /**
     * <p>sch091Edit をセットします。
     * @param sch091Edit sch091Edit
     */
    public void setSch091Edit(int sch091Edit) {
        sch091Edit__ = sch091Edit;
    }

    /**
     * <p>sch091Fcolor を取得します。
     * @return sch091Fcolor
     */
    public String getSch091Fcolor() {
        return sch091Fcolor__;
    }

    /**
     * <p>sch091Fcolor をセットします。
     * @param sch091Fcolor sch091Fcolor
     */
    public void setSch091Fcolor(String sch091Fcolor) {
        sch091Fcolor__ = sch091Fcolor;
    }

    /**
     * <p>sch091DefFrH を取得します。
     * @return sch091DefFrH
     */
    public int getSch091DefFrH() {
        return sch091DefFrH__;
    }

    /**
     * <p>sch091DefFrH をセットします。
     * @param sch091DefFrH sch091DefFrH
     */
    public void setSch091DefFrH(int sch091DefFrH) {
        sch091DefFrH__ = sch091DefFrH;
    }

    /**
     * <p>sch091DefFrM を取得します。
     * @return sch091DefFrM
     */
    public int getSch091DefFrM() {
        return sch091DefFrM__;
    }

    /**
     * <p>sch091DefFrM をセットします。
     * @param sch091DefFrM sch091DefFrM
     */
    public void setSch091DefFrM(int sch091DefFrM) {
        sch091DefFrM__ = sch091DefFrM;
    }

    /**
     * <p>sch091DefToH を取得します。
     * @return sch091DefToH
     */
    public int getSch091DefToH() {
        return sch091DefToH__;
    }

    /**
     * <p>sch091DefToH をセットします。
     * @param sch091DefToH sch091DefToH
     */
    public void setSch091DefToH(int sch091DefToH) {
        sch091DefToH__ = sch091DefToH;
    }

    /**
     * <p>sch091DefToM を取得します。
     * @return sch091DefToM
     */
    public int getSch091DefToM() {
        return sch091DefToM__;
    }

    /**
     * <p>sch091DefToM をセットします。
     * @param sch091DefToM sch091DefToM
     */
    public void setSch091DefToM(int sch091DefToM) {
        sch091DefToM__ = sch091DefToM;
    }

    /**
     * <p>sch091DefFrTime を取得します。
     * @return sch091DefFrTime
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091DefFrTime__
     */
    public String getSch091DefFrTime() {
        return sch091DefFrTime__;
    }

    /**
     * <p>sch091DefFrTime をセットします。
     * @param sch091DefFrTime sch091DefFrTime
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091DefFrTime__
     */
    public void setSch091DefFrTime(String sch091DefFrTime) {
        sch091DefFrTime__ = sch091DefFrTime;
    }

    /**
     * <p>sch091DefToTime を取得します。
     * @return sch091DefToTime
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091DefToTime__
     */
    public String getSch091DefToTime() {
        return sch091DefToTime__;
    }

    /**
     * <p>sch091DefToTime をセットします。
     * @param sch091DefToTime sch091DefToTime
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091DefToTime__
     */
    public void setSch091DefToTime(String sch091DefToTime) {
        sch091DefToTime__ = sch091DefToTime;
    }

    /**
     * <p>sch091PubFlg を取得します。
     * @return sch091PubFlg
     */
    public int getSch091PubFlg() {
        return sch091PubFlg__;
    }

    /**
     * <p>sch091PubFlg をセットします。
     * @param sch091PubFlg sch091PubFlg
     */
    public void setSch091PubFlg(int sch091PubFlg) {
        sch091PubFlg__ = sch091PubFlg;
    }

    /**
     * <p>sch091TimeFlgを取得します。
     * @return sch091TimeFlg
     * */
    public boolean isSch091TimeFlg() {
        return sch091TimeFlg__;
    }

    /**
     * <p>sch091TimeFlgをセットします。
     * @param sch091TimeFlg sch091TimeFlg
     * */
    public void setSch091TimeFlg(boolean sch091TimeFlg) {
        sch091TimeFlg__ = sch091TimeFlg;
    }

    /**
     * <p>sch091EditFlg を取得します。
     * @return sch091EditFlg
     */
    public boolean isSch091EditFlg() {
        return sch091EditFlg__;
    }

    /**
     * <p>sch091EditFlg をセットします。
     * @param sch091EditFlg sch091EditFlg
     */
    public void setSch091EditFlg(boolean sch091EditFlg) {
        sch091EditFlg__ = sch091EditFlg;
    }

    /**
     * <p>sch091Same を取得します。
     * @return sch091Same
     */
    public int getSch091Same() {
        return sch091Same__;
    }

    /**
     * <p>sch091Same をセットします。
     * @param sch091Same sch091Same
     */
    public void setSch091Same(int sch091Same) {
        sch091Same__ = sch091Same;
    }

    /**
     * <p>sch091initFlg を取得します。
     * @return sch091initFlg
     */
    public int getSch091initFlg() {
        return sch091initFlg__;
    }

    /**
     * <p>sch091initFlg をセットします。
     * @param sch091initFlg sch091initFlg
     */
    public void setSch091initFlg(int sch091initFlg) {
        sch091initFlg__ = sch091initFlg;
    }

    /**
     * <p>sch091colorKbn を取得します。
     * @return sch091colorKbn
     */
    public int getSch091colorKbn() {
        return sch091colorKbn__;
    }

    /**
     * <p>sch091colorKbn をセットします。
     * @param sch091colorKbn sch091colorKbn
     */
    public void setSch091colorKbn(int sch091colorKbn) {
        sch091colorKbn__ = sch091colorKbn;
    }

    /**
     * <p>sch091ReminderDspFlg を取得します。
     * @return sch091ReminderDspFlg
     * @see jp.groupsession.v2.sch.sch091.Sch091ParamModel#sch091ReminderDspFlg__
     */
    public int getSch091ReminderDspFlg() {
        return sch091ReminderDspFlg__;
    }

    /**
     * <p>sch091ReminderDspFlg をセットします。
     * @param sch091ReminderDspFlg sch091ReminderDspFlg
     * @see jp.groupsession.v2.sch.sch091.Sch091ParamModel#sch091ReminderDspFlg__
     */
    public void setSch091ReminderDspFlg(int sch091ReminderDspFlg) {
        sch091ReminderDspFlg__ = sch091ReminderDspFlg;
    }

    /**
     * <p>sch091PublicFlg を取得します。
     * @return sch091PublicFlg
     */
    public boolean isSch091PublicFlg() {
        return sch091PublicFlg__;
    }

    /**
     * <p>sch091PublicFlg をセットします。
     * @param sch091PublicFlg sch091PublicFlg
     */
    public void setSch091PublicFlg(boolean sch091PublicFlg) {
        sch091PublicFlg__ = sch091PublicFlg;
    }

    /**
     * <p>sch091SameFlg を取得します。
     * @return sch091SameFlg
     */
    public boolean isSch091SameFlg() {
        return sch091SameFlg__;
    }

    /**
     * <p>sch091SameFlg をセットします。
     * @param sch091SameFlg sch091SameFlg
     */
    public void setSch091SameFlg(boolean sch091SameFlg) {
        sch091SameFlg__ = sch091SameFlg;
    }

    /**
     * <p>sch091ReminderTime を取得します。
     * @return sch091ReminderTime
     * @see jp.groupsession.v2.sch.sch091.Sch091ParamModel#sch091ReminderTime__
     */
    public String getSch091ReminderTime() {
        return sch091ReminderTime__;
    }

    /**
     * <p>sch091ReminderTime をセットします。
     * @param sch091ReminderTime sch091ReminderTime
     * @see jp.groupsession.v2.sch.sch091.Sch091ParamModel#sch091ReminderTime__
     */
    public void setSch091ReminderTime(String sch091ReminderTime) {
        sch091ReminderTime__ = sch091ReminderTime;
    }

    /**
     * <p>sch091DefDsp を取得します。
     * @return sch091DefDsp
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091DefDsp__
     */
    public int getSch091DefDsp() {
        return sch091DefDsp__;
    }

    /**
     * <p>sch091DefDsp をセットします。
     * @param sch091DefDsp sch091DefDsp
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091DefDsp__
     */
    public void setSch091DefDsp(int sch091DefDsp) {
        sch091DefDsp__ = sch091DefDsp;
    }

    /**
     * <p>sch091PubDefUsrSid を取得します。
     * @return sch091PubDefUsrSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubDefUsrSid__
     */
    public int getSch091PubDefUsrSid() {
        return sch091PubDefUsrSid__;
    }

    /**
     * <p>sch091PubDefUsrSid をセットします。
     * @param sch091PubDefUsrSid sch091PubDefUsrSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubDefUsrSid__
     */
    public void setSch091PubDefUsrSid(int sch091PubDefUsrSid) {
        sch091PubDefUsrSid__ = sch091PubDefUsrSid;
    }

    /**
     * <p>sch091PubUserGroup を取得します。
     * @return sch091PubUserGroup
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubUserGroup__
     */
    public String getSch091PubUserGroup() {
        return sch091PubUserGroup__;
    }

    /**
     * <p>sch091PubUserGroup をセットします。
     * @param sch091PubUserGroup sch091PubUserGroup
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubUserGroup__
     */
    public void setSch091PubUserGroup(String sch091PubUserGroup) {
        sch091PubUserGroup__ = sch091PubUserGroup;
    }

    /**
     * <p>sch091PubGroupCombo を取得します。
     * @return sch091PubGroupCombo
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubGroupCombo__
     */
    public List<LabelValueBean> getSch091PubGroupCombo() {
        return sch091PubGroupCombo__;
    }

    /**
     * <p>sch091PubGroupCombo をセットします。
     * @param sch091PubGroupCombo sch091PubGroupCombo
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubGroupCombo__
     */
    public void setSch091PubGroupCombo(List<LabelValueBean> sch091PubGroupCombo) {
        sch091PubGroupCombo__ = sch091PubGroupCombo;
    }

    /**
     * <p>sch091PubUsrGrpSid を取得します。
     * @return sch091PubUsrGrpSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubUsrGrpSid__
     */
    public String[] getSch091PubUsrGrpSid() {
        return sch091PubUsrGrpSid__;
    }

    /**
     * <p>sch091PubUsrGrpSid をセットします。
     * @param sch091PubUsrGrpSid sch091PubUsrGrpSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091PubUsrGrpSid__
     */
    public void setSch091PubUsrGrpSid(String[] sch091PubUsrGrpSid) {
        sch091PubUsrGrpSid__ = sch091PubUsrGrpSid;
    }

    /**
     * <p>sch091LeftUsrGrpSid を取得します。
     * @return sch091LeftUsrGrpSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091LeftUsrGrpSid__
     */
    public String[] getSch091LeftUsrGrpSid() {
        return sch091LeftUsrGrpSid__;
    }

    /**
     * <p>sch091LeftUsrGrpSid をセットします。
     * @param sch091LeftUsrGrpSid sch091LeftUsrGrpSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091LeftUsrGrpSid__
     */
    public void setSch091LeftUsrGrpSid(String[] sch091LeftUsrGrpSid) {
        sch091LeftUsrGrpSid__ = sch091LeftUsrGrpSid;
    }

    /**
     * <p>sch091RightUsrGrpSid を取得します。
     * @return sch091RightUsrGrpSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091RightUsrGrpSid__
     */
    public String[] getSch091RightUsrGrpSid() {
        return sch091RightUsrGrpSid__;
    }

    /**
     * <p>sch091RightUsrGrpSid をセットします。
     * @param sch091RightUsrGrpSid sch091RightUsrGrpSid
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091RightUsrGrpSid__
     */
    public void setSch091RightUsrGrpSid(String[] sch091RightUsrGrpSid) {
        sch091RightUsrGrpSid__ = sch091RightUsrGrpSid;
    }

    /**
     * <p>sch091HourDiv を取得します。
     * @return sch091HourDiv
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091HourDiv__
     */
    public int getSch091HourDiv() {
        return sch091HourDiv__;
    }

    /**
     * <p>sch091HourDiv をセットします。
     * @param sch091HourDiv sch091HourDiv
     * @see jp.groupsession.v2.sch.sch091.Sch091Form#sch091HourDiv__
     */
    public void setSch091HourDiv(int sch091HourDiv) {
        sch091HourDiv__ = sch091HourDiv;
    }
    /**
     * <p>sch091PubUsrGrpSidUI を取得します。
     * @return sch091PubUsrGrpSidUI
     * @see jp.groupsession.v2.sch.sch091.Sch091ParamModel#sch091PubUsrGrpSidUI__
     */
    public UserGroupSelector getSch091PubUsrGrpSidUI() {
        return sch091PubUsrGrpSidUI__;
    }

    /**
     * <p>sch091PubUsrGrpSidUI をセットします。
     * @param sch091PubUsrGrpSidUI sch091PubUsrGrpSidUI
     * @see jp.groupsession.v2.sch.sch091.Sch091ParamModel#sch091PubUsrGrpSidUI__
     */
    public void setSch091PubUsrGrpSidUI(UserGroupSelector sch091PubUsrGrpSidUI) {
        sch091PubUsrGrpSidUI__ = sch091PubUsrGrpSidUI;
    }
}