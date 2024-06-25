package jp.groupsession.v2.cht.cht130;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht030.Cht030ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] チャット 表示設定のパラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht130ParamModel extends Cht030ParamModel {

    /** 初期表示区分 */
    private int cht130InitFlg__ = GSConstChat.DSP_FIRST;
    /** 選択SID */
    private int cht130SelectSid__ = -1;
    /** 選択区分 */
    private int cht130SelectKbn__ = -1;
    /** デフォルトフラグ */
    private int cht130DefFlg__ = -1;
    /** チャットグループ */
    private int cht130GrpGroup__ = -1;
    /** ユーザ */
    private int cht130User__ = -1;
    /** グループ */
    private int cht130UsrGroup__ = -1;
    /** チャットグループリスト*/
    private List <LabelValueBean> cht130ChtGroupList__ = null;
    /** グループリスト*/
    private List <LabelValueBean> cht130GroupList__ = null;
    /** ユーザリスト*/
    private List <UsrLabelValueBean> cht130UserList__ = null;

    /**
     * <p>cht130DefFlg を取得します。
     * @return cht130DefFlg
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130DefFlg__
     */
    public int getCht130DefFlg() {
        return cht130DefFlg__;
    }
    /**
     * <p>cht130DefFlg をセットします。
     * @param cht130DefFlg cht130DefFlg
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130DefFlg__
     */
    public void setCht130DefFlg(int cht130DefFlg) {
        cht130DefFlg__ = cht130DefFlg;
    }
    /**
     * <p>cht130GrpGroup を取得します。
     * @return cht130GrpGroup
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130GrpGroup__
     */
    public int getCht130GrpGroup() {
        return cht130GrpGroup__;
    }
    /**
     * <p>cht130GrpGroup をセットします。
     * @param cht130GrpGroup cht130GrpGroup
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130GrpGroup__
     */
    public void setCht130GrpGroup(int cht130GrpGroup) {
        cht130GrpGroup__ = cht130GrpGroup;
    }
    /**
     * <p>cht130User を取得します。
     * @return cht130User
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130User__
     */
    public int getCht130User() {
        return cht130User__;
    }
    /**
     * <p>cht130User をセットします。
     * @param cht130User cht130User
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130User__
     */
    public void setCht130User(int cht130User) {
        cht130User__ = cht130User;
    }
    /**
     * <p>cht130UsrGroup を取得します。
     * @return cht130UsrGroup
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130UsrGroup__
     */
    public int getCht130UsrGroup() {
        return cht130UsrGroup__;
    }
    /**
     * <p>cht130UsrGroup をセットします。
     * @param cht130UsrGroup cht130UsrGroup
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130UsrGroup__
     */
    public void setCht130UsrGroup(int cht130UsrGroup) {
        cht130UsrGroup__ = cht130UsrGroup;
    }
    /**
     * <p>cht130ChtGroupList を取得します。
     * @return cht130ChtGroupList
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130ChtGroupList__
     */
    public List<LabelValueBean> getCht130ChtGroupList() {
        return cht130ChtGroupList__;
    }
    /**
     * <p>cht130ChtGroupList をセットします。
     * @param cht130ChtGroupList cht130ChtGroupList
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130ChtGroupList__
     */
    public void setCht130ChtGroupList(List<LabelValueBean> cht130ChtGroupList) {
        cht130ChtGroupList__ = cht130ChtGroupList;
    }
    /**
     * <p>cht130GroupList を取得します。
     * @return cht130GroupList
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130GroupList__
     */
    public List<LabelValueBean> getCht130GroupList() {
        return cht130GroupList__;
    }
    /**
     * <p>cht130GroupList をセットします。
     * @param cht130GroupList cht130GroupList
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130GroupList__
     */
    public void setCht130GroupList(List<LabelValueBean> cht130GroupList) {
        cht130GroupList__ = cht130GroupList;
    }
    /**
     * <p>cht130UserList を取得します。
     * @return cht130UserList
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130UserList__
     */
    public List<UsrLabelValueBean> getCht130UserList() {
        return cht130UserList__;
    }
    /**
     * <p>cht130UserList をセットします。
     * @param cht130UserList cht130UserList
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130UserList__
     */
    public void setCht130UserList(List<UsrLabelValueBean> cht130UserList) {
        cht130UserList__ = cht130UserList;
    }
    /**
     * <p>cht130SelectSid を取得します。
     * @return cht130SelectSid
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130SelectSid__
     */
    public int getCht130SelectSid() {
        return cht130SelectSid__;
    }
    /**
     * <p>cht130SelectSid をセットします。
     * @param cht130SelectSid cht130SelectSid
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130SelectSid__
     */
    public void setCht130SelectSid(int cht130SelectSid) {
        cht130SelectSid__ = cht130SelectSid;
    }
    /**
     * <p>cht130InitFlg を取得します。
     * @return cht130InitFlg
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130InitFlg__
     */
    public int getCht130InitFlg() {
        return cht130InitFlg__;
    }
    /**
     * <p>cht130InitFlg をセットします。
     * @param cht130InitFlg cht130InitFlg
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130InitFlg__
     */
    public void setCht130InitFlg(int cht130InitFlg) {
        cht130InitFlg__ = cht130InitFlg;
    }
    /**
     * <p>cht130SelectKbn を取得します。
     * @return cht130SelectKbn
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130SelectKbn__
     */
    public int getCht130SelectKbn() {
        return cht130SelectKbn__;
    }
    /**
     * <p>cht130SelectKbn をセットします。
     * @param cht130SelectKbn cht130SelectKbn
     * @see jp.groupsession.v2.cht.cht130.Cht130ParamModel#cht130SelectKbn__
     */
    public void setCht130SelectKbn(int cht130SelectKbn) {
        cht130SelectKbn__ = cht130SelectKbn;
    }

}
