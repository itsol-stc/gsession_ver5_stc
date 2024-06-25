package jp.groupsession.v2.cht.cht110;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht100.Cht100ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

/**
 *
 * <br>[機  能] チャットグループ追加/編集のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht110ParamModel extends Cht100ParamModel {

    /** 初期表示区分 */
    private int cht110InitFlg__ = GSConstChat.DSP_FIRST;
    /** チャットグループＩＤ */
    private String cht110groupId__ = null;
    /** グループ名 */
    private String cht110groupName__ = null;
    /** カテゴリ */
    private int cht110category__ = -1;

    /** メンバー グループ */
    private String cht110memberGroup__ = null;
    /** メンバー 管理メンバー */
    private String[] cht110memberAdmin__ = null;
    /** メンバー 一般メンバー */
    private String[] cht110memberNormal__ = null;
    /** メンバー UI */
    private UserGroupSelector cht110memberUI__ = null;

    /** 作成日時 */
    private String cht110createDate__ = null;
    /** 最終投稿日時 */
    private String cht110updateDate__ = null;
    /**  備考 */
    private String cht110biko__ = null;
    /** 状態区分 */
    private int cht110compFlg__ = GSConstChat.CHAT_ARCHIVE_NOT_MODE;
    /** カテゴリリスト*/
    private List <LabelValueBean> cht110CategoryList__ = null;
    /** 編集前所属ユーザ */
    private String[] oldMemberSids__ = null;

    /**
     * <p>cht110groupId を取得します。
     * @return cht110groupId
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110groupId__
     */
    public String getCht110groupId() {
        return cht110groupId__;
    }

    /**
     * <p>cht110groupId をセットします。
     * @param cht110groupId cht110groupId
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110groupId__
     */
    public void setCht110groupId(String cht110groupId) {
        cht110groupId__ = cht110groupId;
    }

    /**
     * <p>cht110groupName を取得します。
     * @return cht110groupName
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110groupName__
     */
    public String getCht110groupName() {
        return cht110groupName__;
    }

    /**
     * <p>cht110groupName をセットします。
     * @param cht110groupName cht110groupName
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110groupName__
     */
    public void setCht110groupName(String cht110groupName) {
        cht110groupName__ = cht110groupName;
    }

    /**
     * <p>cht110category を取得します。
     * @return cht110category
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110category__
     */
    public int getCht110category() {
        return cht110category__;
    }

    /**
     * <p>cht110category をセットします。
     * @param cht110category cht110category
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110category__
     */
    public void setCht110category(int cht110category) {
        cht110category__ = cht110category;
    }

    /**
     * <p>cht110biko を取得します。
     * @return cht110biko
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110biko__
     */
    public String getCht110biko() {
        return cht110biko__;
    }

    /**
     * <p>cht110biko をセットします。
     * @param cht110biko cht110biko
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110biko__
     */
    public void setCht110biko(String cht110biko) {
        cht110biko__ = cht110biko;
    }



    /**
     * <p>cht110CategoryList を取得します。
     * @return cht110CategoryList
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110CategoryList__
     */
    public List<LabelValueBean> getCht110CategoryList() {
        return cht110CategoryList__;
    }

    /**
     * <p>cht110CategoryList をセットします。
     * @param cht110CategoryList cht110CategoryList
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110CategoryList__
     */
    public void setCht110CategoryList(List<LabelValueBean> cht110CategoryList) {
        cht110CategoryList__ = cht110CategoryList;
    }

    /**
     * <p>cht110memberGroup を取得します。
     * @return cht110memberGroup
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberGroup__
     */
    public String getCht110memberGroup() {
        return cht110memberGroup__;
    }

    /**
     * <p>cht110memberGroup をセットします。
     * @param cht110memberGroup cht110memberGroup
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberGroup__
     */
    public void setCht110memberGroup(String cht110memberGroup) {
        cht110memberGroup__ = cht110memberGroup;
    }

    /**
     * <p>cht110memberAdmin を取得します。
     * @return cht110memberAdmin
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberAdmin__
     */
    public String[] getCht110memberAdmin() {
        return cht110memberAdmin__;
    }

    /**
     * <p>cht110memberAdmin をセットします。
     * @param cht110memberAdmin cht110memberAdmin
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberAdmin__
     */
    public void setCht110memberAdmin(String[] cht110memberAdmin) {
        cht110memberAdmin__ = cht110memberAdmin;
    }

    /**
     * <p>cht110memberNormal を取得します。
     * @return cht110memberNormal
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberNormal__
     */
    public String[] getCht110memberNormal() {
        return cht110memberNormal__;
    }

    /**
     * <p>cht110memberNormal をセットします。
     * @param cht110memberNormal cht110memberNormal
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberNormal__
     */
    public void setCht110memberNormal(String[] cht110memberNormal) {
        cht110memberNormal__ = cht110memberNormal;
    }

    /**
     * <p>cht110memberUI を取得します。
     * @return cht110memberUI
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberUI__
     */
    public UserGroupSelector getCht110memberUI() {
        return cht110memberUI__;
    }

    /**
     * <p>cht110memberUI をセットします。
     * @param cht110memberUI cht110memberUI
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110memberUI__
     */
    public void setCht110memberUI(UserGroupSelector cht110memberUI) {
        cht110memberUI__ = cht110memberUI;
    }

    /**
     * <p>cht110compFlg を取得します。
     * @return cht110compFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110compFlg__
     */
    public int getCht110compFlg() {
        return cht110compFlg__;
    }

    /**
     * <p>cht110compFlg をセットします。
     * @param cht110compFlg cht110compFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110compFlg__
     */
    public void setCht110compFlg(int cht110compFlg) {
        cht110compFlg__ = cht110compFlg;
    }

    /**
     * <p>cht110createDate を取得します。
     * @return cht110createDate
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110createDate__
     */
    public String getCht110createDate() {
        return cht110createDate__;
    }

    /**
     * <p>cht110createDate をセットします。
     * @param cht110createDate cht110createDate
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110createDate__
     */
    public void setCht110createDate(String cht110createDate) {
        cht110createDate__ = cht110createDate;
    }

    /**
     * <p>cht110updateDate を取得します。
     * @return cht110updateDate
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110updateDate__
     */
    public String getCht110updateDate() {
        return cht110updateDate__;
    }

    /**
     * <p>cht110updateDate をセットします。
     * @param cht110updateDate cht110updateDate
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110updateDate__
     */
    public void setCht110updateDate(String cht110updateDate) {
        cht110updateDate__ = cht110updateDate;
    }

    /**
     * <p>cht110InitFlg を取得します。
     * @return cht110InitFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110InitFlg__
     */
    public int getCht110InitFlg() {
        return cht110InitFlg__;
    }

    /**
     * <p>cht110InitFlg をセットします。
     * @param cht110InitFlg cht110InitFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#cht110InitFlg__
     */
    public void setCht110InitFlg(int cht110InitFlg) {
        cht110InitFlg__ = cht110InitFlg;
    }

    /**
     * <p>oldMemberSids を取得します。
     * @return oldMemberSids
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#oldMemberSids__
     */
    public String[] getOldMemberSids() {
        return oldMemberSids__;
    }

    /**
     * <p>oldMemberSids をセットします。
     * @param oldMemberSids oldMemberSids
     * @see jp.groupsession.v2.cht.cht110.Cht110ParamModel#oldMemberSids__
     */
    public void setOldMemberSids(String[] oldMemberSids) {
        oldMemberSids__ = oldMemberSids;
    }

}
