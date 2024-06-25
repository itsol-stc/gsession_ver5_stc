package jp.groupsession.v2.cht.model;

import java.io.Serializable;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;

/**
 * <br>[機  能] チャット 検索情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChatSearchModel implements Serializable {

    /** キーワード */
    private String keyword__ = null;
    /** キーワードリスト */
    private List<String> keywordList__ = null;
    /** キーワード検索方法 */
    private int andOr__ = GSConstChat.KEYWORDKBN_AND;
    /** 検索条件:グループID */
    private int groupId__ = GSConstChat.SEARCH_GROUPID_IN;
    /** 検索条件:グループ名 */
    private int groupName__ = GSConstChat.SEARCH_GROUPNAME_IN;
    /** 検索条件:備考 */
    private int groupInfo__ = GSConstChat.SEARCH_GROUPINFO_IN;
    /** カテゴリ */
    private int category__ = -1;
    /** 状態区分 */
    private int statusKbn__ = GSConstChat.SEARCH_STATUSKBN_ALL;
    /** グループ */
    private int selectGroup__ = -1;
    /** ユーザ */
    private int selectUser__ = -1;
    /** メンバー検索条件：管理者 */
    private int adminMember__ = GSConstChat.SEARCH_GROUPADMIN_IN;
    /** メンバー検索条件：一般ユーザ */
    private int generalMember__ = GSConstChat.SEARCH_GENERALUSER_IN;
    /** 作成日時From */
    private UDate createDateFr__ = null;
    /** 作成日時To */
    private UDate createDateTo__ = null;
    /** 最終投稿日時From */
    private UDate upDateFr__ = null;
    /** 最終投稿日時To */
    private UDate upDateTo__ = null;

    /** ソート対象 */
    private int sortKey__ = -1;
    /** 並び順 */
    private int orderKey__ = -1;

    /** ページ */
    private int page__ = 0;
    /** ページ毎の最大表示件数 */
    private int maxCnt__ = 0;


    /**
     * <p>keyword を取得します。
     * @return keyword
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#keyword__
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#keyword__
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    /**
     * <p>andOr を取得します。
     * @return andOr
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#andOr__
     */
    public int getAndOr() {
        return andOr__;
    }
    /**
     * <p>andOr をセットします。
     * @param andOr andOr
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#andOr__
     */
    public void setAndOr(int andOr) {
        andOr__ = andOr;
    }
    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#groupId__
     */
    public int getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#groupId__
     */
    public void setGroupId(int groupId) {
        groupId__ = groupId;
    }
    /**
     * <p>groupName を取得します。
     * @return groupName
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#groupName__
     */
    public int getGroupName() {
        return groupName__;
    }
    /**
     * <p>groupName をセットします。
     * @param groupName groupName
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#groupName__
     */
    public void setGroupName(int groupName) {
        groupName__ = groupName;
    }
    /**
     * <p>groupInfo を取得します。
     * @return groupInfo
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#groupInfo__
     */
    public int getGroupInfo() {
        return groupInfo__;
    }
    /**
     * <p>groupInfo をセットします。
     * @param groupInfo groupInfo
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#groupInfo__
     */
    public void setGroupInfo(int groupInfo) {
        groupInfo__ = groupInfo;
    }
    /**
     * <p>category を取得します。
     * @return category
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#category__
     */
    public int getCategory() {
        return category__;
    }
    /**
     * <p>category をセットします。
     * @param category category
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#category__
     */
    public void setCategory(int category) {
        category__ = category;
    }
    /**
     * <p>statusKbn を取得します。
     * @return statusKbn
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#statusKbn__
     */
    public int getStatusKbn() {
        return statusKbn__;
    }
    /**
     * <p>statusKbn をセットします。
     * @param statusKbn statusKbn
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#statusKbn__
     */
    public void setStatusKbn(int statusKbn) {
        statusKbn__ = statusKbn;
    }
    /**
     * <p>selectGroup を取得します。
     * @return selectGroup
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#selectGroup__
     */
    public int getSelectGroup() {
        return selectGroup__;
    }
    /**
     * <p>selectGroup をセットします。
     * @param selectGroup selectGroup
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#selectGroup__
     */
    public void setSelectGroup(int selectGroup) {
        selectGroup__ = selectGroup;
    }
    /**
     * <p>selectUser を取得します。
     * @return selectUser
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#selectUser__
     */
    public int getSelectUser() {
        return selectUser__;
    }
    /**
     * <p>selectUser をセットします。
     * @param selectUser selectUser
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#selectUser__
     */
    public void setSelectUser(int selectUser) {
        selectUser__ = selectUser;
    }
    /**
     * <p>adminMember を取得します。
     * @return adminMember
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#adminMember__
     */
    public int getAdminMember() {
        return adminMember__;
    }
    /**
     * <p>adminMember をセットします。
     * @param adminMember adminMember
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#adminMember__
     */
    public void setAdminMember(int adminMember) {
        adminMember__ = adminMember;
    }
    /**
     * <p>generalMember を取得します。
     * @return generalMember
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#generalMember__
     */
    public int getGeneralMember() {
        return generalMember__;
    }
    /**
     * <p>generalMember をセットします。
     * @param generalMember generalMember
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#generalMember__
     */
    public void setGeneralMember(int generalMember) {
        generalMember__ = generalMember;
    }
    /**
     * <p>createDateFr を取得します。
     * @return createDateFr
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#createDateFr__
     */
    public UDate getCreateDateFr() {
        return createDateFr__;
    }
    /**
     * <p>createDateFr をセットします。
     * @param createDateFr createDateFr
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#createDateFr__
     */
    public void setCreateDateFr(UDate createDateFr) {
        createDateFr__ = createDateFr;
    }
    /**
     * <p>createDateTo を取得します。
     * @return createDateTo
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#createDateTo__
     */
    public UDate getCreateDateTo() {
        return createDateTo__;
    }
    /**
     * <p>createDateTo をセットします。
     * @param createDateTo createDateTo
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#createDateTo__
     */
    public void setCreateDateTo(UDate createDateTo) {
        createDateTo__ = createDateTo;
    }
    /**
     * <p>upDateFr を取得します。
     * @return upDateFr
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#upDateFr__
     */
    public UDate getUpDateFr() {
        return upDateFr__;
    }
    /**
     * <p>upDateFr をセットします。
     * @param upDateFr upDateFr
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#upDateFr__
     */
    public void setUpDateFr(UDate upDateFr) {
        upDateFr__ = upDateFr;
    }
    /**
     * <p>upDateTo を取得します。
     * @return upDateTo
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#upDateTo__
     */
    public UDate getUpDateTo() {
        return upDateTo__;
    }
    /**
     * <p>upDateTo をセットします。
     * @param upDateTo upDateTo
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#upDateTo__
     */
    public void setUpDateTo(UDate upDateTo) {
        upDateTo__ = upDateTo;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#sortKey__
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#sortKey__
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>orderKey を取得します。
     * @return orderKey
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#orderKey__
     */
    public int getOrderKey() {
        return orderKey__;
    }
    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#orderKey__
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }
    /**
     * <p>page を取得します。
     * @return page
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#page__
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#page__
     */
    public void setPage(int page) {
        page__ = page;
    }
    /**
     * <p>maxCnt を取得します。
     * @return maxCnt
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#maxCnt__
     */
    public int getMaxCnt() {
        return maxCnt__;
    }
    /**
     * <p>maxCnt をセットします。
     * @param maxCnt maxCnt
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#maxCnt__
     */
    public void setMaxCnt(int maxCnt) {
        maxCnt__ = maxCnt;
    }
    /**
     * <p>keywordList を取得します。
     * @return keywordList
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#keywordList__
     */
    public List<String> getKeywordList() {
        return keywordList__;
    }
    /**
     * <p>keywordList をセットします。
     * @param keywordList keywordList
     * @see jp.groupsession.v2.cht.model.ChatSearchModel#keywordList__
     */
    public void setKeywordList(List<String> keywordList) {
        keywordList__ = keywordList;
    }


}
