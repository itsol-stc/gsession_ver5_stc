package jp.groupsession.v2.cht.model;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <p>メンバーグループを管理するモデルです
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChatGroupComboModel implements Serializable {


    /** グループコンボラベルリスト */
    private List < LabelValueBean > groupList__ = null;
    /** グループコンボ選択 */
    private int selectGroupSid__ = -9;
    /** 管理者メンバー表示ラベル */
    private List < UsrLabelValueBean > leftUserAdminList__ = null;
    /** 一般メンバー表示ラベル */
    private List < UsrLabelValueBean > leftUserGeneralList__ = null;
    /** 右側表示用メンバーラベルリスト */
    private List < UsrLabelValueBean > rightUserList__ = null;

    /** 管理者メンバーSID */
    private String[] memberAdminSid__ = new String[0];
    /** 一般メンバーSID */
    private String[] memberGeneralSid__ = new String[0];
    /** セレクトボックス選択時使用（管理者メンバー） */
    private String[] selectLeftUserAdmin__ = new String[0];
    /** セレクトボックス選択時使用（一般メンバー) */
    private String[] selectLeftUserGeneral__ = new String[0];
    /** セレクトボックス選択時使用(右側表示用メンバー) */
    private String[] selectRightUser__ = new String[0];

    /**
     * <p>groupList を取得します。
     * @return groupList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#groupList__
     */
    public List<LabelValueBean> getGroupList() {
        return groupList__;
    }
    /**
     * <p>groupList をセットします。
     * @param groupList groupList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#groupList__
     */
    public void setGroupList(List<LabelValueBean> groupList) {
        groupList__ = groupList;
    }
    /**
     * <p>leftUserAdminList を取得します。
     * @return leftUserAdminList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#leftUserAdminList__
     */
    public List<UsrLabelValueBean> getLeftUserAdminList() {
        return leftUserAdminList__;
    }
    /**
     * <p>leftUserAdminList をセットします。
     * @param leftUserAdminList leftUserAdminList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#leftUserAdminList__
     */
    public void setLeftUserAdminList(List<UsrLabelValueBean> leftUserAdminList) {
        leftUserAdminList__ = leftUserAdminList;
    }
    /**
     * <p>leftUserGeneralList を取得します。
     * @return leftUserGeneralList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#leftUserGeneralList__
     */
    public List<UsrLabelValueBean> getLeftUserGeneralList() {
        return leftUserGeneralList__;
    }
    /**
     * <p>leftUserGeneralList をセットします。
     * @param leftUserGeneralList leftUserGeneralList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#leftUserGeneralList__
     */
    public void setLeftUserGeneralList(
            List<UsrLabelValueBean> leftUserGeneralList) {
        leftUserGeneralList__ = leftUserGeneralList;
    }
    /**
     * <p>rightUserList を取得します。
     * @return rightUserList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#rightUserList__
     */
    public List<UsrLabelValueBean> getRightUserList() {
        return rightUserList__;
    }
    /**
     * <p>rightUserList をセットします。
     * @param rightUserList rightUserList
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#rightUserList__
     */
    public void setRightUserList(List<UsrLabelValueBean> rightUserList) {
        rightUserList__ = rightUserList;
    }
    /**
     * <p>selectLeftUserAdmin を取得します。
     * @return selectLeftUserAdmin
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectLeftUserAdmin__
     */
    public String[] getSelectLeftUserAdmin() {
        return selectLeftUserAdmin__;
    }
    /**
     * <p>selectLeftUserAdmin をセットします。
     * @param selectLeftUserAdmin selectLeftUserAdmin
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectLeftUserAdmin__
     */
    public void setSelectLeftUserAdmin(String[] selectLeftUserAdmin) {
        selectLeftUserAdmin__ = selectLeftUserAdmin;
    }
    /**
     * <p>selectLeftUserGeneral を取得します。
     * @return selectLeftUserGeneral
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectLeftUserGeneral__
     */
    public String[] getSelectLeftUserGeneral() {
        return selectLeftUserGeneral__;
    }
    /**
     * <p>selectLeftUserGeneral をセットします。
     * @param selectLeftUserGeneral selectLeftUserGeneral
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectLeftUserGeneral__
     */
    public void setSelectLeftUserGeneral(String[] selectLeftUserGeneral) {
        selectLeftUserGeneral__ = selectLeftUserGeneral;
    }
    /**
     * <p>selectRightUser を取得します。
     * @return selectRightUser
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectRightUser__
     */
    public String[] getSelectRightUser() {
        return selectRightUser__;
    }
    /**
     * <p>selectRightUser をセットします。
     * @param selectRightUser selectRightUser
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectRightUser__
     */
    public void setSelectRightUser(String[] selectRightUser) {
        selectRightUser__ = selectRightUser;
    }
    /**
     * <p>memberAdminSid を取得します。
     * @return memberAdminSid
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#memberAdminSid__
     */
    public String[] getMemberAdminSid() {
        return memberAdminSid__;
    }
    /**
     * <p>memberAdminSid をセットします。
     * @param memberAdminSid memberAdminSid
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#memberAdminSid__
     */
    public void setMemberAdminSid(String[] memberAdminSid) {
        memberAdminSid__ = memberAdminSid;
    }
    /**
     * <p>memberGeneralSid を取得します。
     * @return memberGeneralSid
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#memberGeneralSid__
     */
    public String[] getMemberGeneralSid() {
        return memberGeneralSid__;
    }
    /**
     * <p>memberGeneralSid をセットします。
     * @param memberGeneralSid memberGeneralSid
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#memberGeneralSid__
     */
    public void setMemberGeneralSid(String[] memberGeneralSid) {
        memberGeneralSid__ = memberGeneralSid;
    }

    /**
     * <p>selectGroupSid を取得します。
     * @return selectGroupSid
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectGroupSid__
     */
    public int getSelectGroupSid() {
        return selectGroupSid__;
    }
    /**
     * <p>selectGroupSid をセットします。
     * @param selectGroupSid selectGroupSid
     * @see jp.groupsession.v2.cht.model.ChatGroupComboModel#selectGroupSid__
     */
    public void setSelectGroupSid(int selectGroupSid) {
        selectGroupSid__ = selectGroupSid;
    }

}