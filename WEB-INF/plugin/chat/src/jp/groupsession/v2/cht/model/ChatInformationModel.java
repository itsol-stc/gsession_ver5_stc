package jp.groupsession.v2.cht.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <p>CHT_GROUP_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChatInformationModel implements Serializable {

    /**ユーザ・グループ共に使用*/
    /** チャット区分 */
    private int chatKbn__;
    /** 選択SID*/
    private int chatSid__;
    /** 名称 */
    private String chatName__;
    /** 一般グループ*/
    private List<CmnGroupmModel> generalGroup__;
    /** 一般メンバー*/
    private List<CmnUsrmInfModel> generalMember__;
    /** アーカイブ */
    private int chatArchive__;

    /**グループにのみ使用*/
    /** カテゴリSID*/
    private int categorySid__;
    /** カテゴリ名*/
    private String categoryName__;
    /** チャットグループID*/
    private String chatId__;
    /** 管理者グループ*/
    private List<CmnGroupmModel> adminGroup__;
    /** 管理者メンバー */
    private List<CmnUsrmInfModel> adminMember__;
    /** 備考*/
    private String chatBiko__;
    /** 登録日時 */
    private UDate insertDate__;
    /** 最終確認日時*/
    private UDate lastSendDate__;
    /** 登録日時(文字列) */
    private String strInsertDate__ = null;
    /** 最終確認日時(文字列) */
    private String strLastSendDate__ = null;


    /**
     * <p>Default Constructor
     */
    public ChatInformationModel() {
        adminGroup__ = new ArrayList<CmnGroupmModel>();
        adminMember__ = new ArrayList<CmnUsrmInfModel>();
        generalGroup__ = new ArrayList<CmnGroupmModel>();
        generalMember__ = new ArrayList<CmnUsrmInfModel>();
    }



    /**
     * <p>chatKbn を取得します。
     * @return chatKbn
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatKbn__
     */
    public int getChatKbn() {
        return chatKbn__;
    }



    /**
     * <p>chatKbn をセットします。
     * @param chatKbn chatKbn
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatKbn__
     */
    public void setChatKbn(int chatKbn) {
        chatKbn__ = chatKbn;
    }



    /**
     * <p>chatSid を取得します。
     * @return chatSid
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatSid__
     */
    public int getChatSid() {
        return chatSid__;
    }



    /**
     * <p>chatSid をセットします。
     * @param chatSid chatSid
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatSid__
     */
    public void setChatSid(int chatSid) {
        chatSid__ = chatSid;
    }



    /**
     * <p>chatName を取得します。
     * @return chatName
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatName__
     */
    public String getChatName() {
        return chatName__;
    }



    /**
     * <p>chatName をセットします。
     * @param chatName chatName
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatName__
     */
    public void setChatName(String chatName) {
        chatName__ = chatName;
    }



    /**
     * <p>generalMember を取得します。
     * @return generalMember
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#generalMember__
     */
    public List<CmnUsrmInfModel> getGeneralMember() {
        return generalMember__;
    }



    /**
     * <p>generalMember をセットします。
     * @param generalMember generalMember
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#generalMember__
     */
    public void setGeneralMember(List<CmnUsrmInfModel> generalMember) {
        generalMember__ = generalMember;
    }



    /**
     * <p>chatArchive を取得します。
     * @return chatArchive
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatArchive__
     */
    public int getChatArchive() {
        return chatArchive__;
    }



    /**
     * <p>chatArchive をセットします。
     * @param chatArchive chatArchive
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatArchive__
     */
    public void setChatArchive(int chatArchive) {
        chatArchive__ = chatArchive;
    }



    /**
     * <p>categorySid を取得します。
     * @return categorySid
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#categorySid__
     */
    public int getCategorySid() {
        return categorySid__;
    }



    /**
     * <p>categorySid をセットします。
     * @param categorySid categorySid
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#categorySid__
     */
    public void setCategorySid(int categorySid) {
        categorySid__ = categorySid;
    }



    /**
     * <p>categoryName を取得します。
     * @return categoryName
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#categoryName__
     */
    public String getCategoryName() {
        return categoryName__;
    }



    /**
     * <p>categoryName をセットします。
     * @param categoryName categoryName
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#categoryName__
     */
    public void setCategoryName(String categoryName) {
        categoryName__ = categoryName;
    }



    /**
     * <p>chatId を取得します。
     * @return chatId
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatId__
     */
    public String getChatId() {
        return chatId__;
    }



    /**
     * <p>chatId をセットします。
     * @param chatId chatId
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatId__
     */
    public void setChatId(String chatId) {
        chatId__ = chatId;
    }



    /**
     * <p>adminMember を取得します。
     * @return adminMember
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#adminMember__
     */
    public List<CmnUsrmInfModel> getAdminMember() {
        return adminMember__;
    }



    /**
     * <p>adminMember をセットします。
     * @param adminMember adminMember
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#adminMember__
     */
    public void setAdminMember(List<CmnUsrmInfModel> adminMember) {
        adminMember__ = adminMember;
    }



    /**
     * <p>chatBiko を取得します。
     * @return chatBiko
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatBiko__
     */
    public String getChatBiko() {
        return chatBiko__;
    }



    /**
     * <p>chatBiko をセットします。
     * @param chatBiko chatBiko
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#chatBiko__
     */
    public void setChatBiko(String chatBiko) {
        chatBiko__ = chatBiko;
    }



    /**
     * <p>insertDate を取得します。
     * @return insertDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#insertDate__
     */
    public UDate getInsertDate() {
        return insertDate__;
    }



    /**
     * <p>insertDate をセットします。
     * @param insertDate insertDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#insertDate__
     */
    public void setInsertDate(UDate insertDate) {
        insertDate__ = insertDate;
    }



    /**
     * <p>lastSendDate を取得します。
     * @return lastSendDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#lastSendDate__
     */
    public UDate getLastSendDate() {
        return lastSendDate__;
    }



    /**
     * <p>lastSendDate をセットします。
     * @param lastSendDate lastSendDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#lastSendDate__
     */
    public void setLastSendDate(UDate lastSendDate) {
        lastSendDate__ = lastSendDate;
    }



    /**
     * <p>strInsertDate を取得します。
     * @return strInsertDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#strInsertDate__
     */
    public String getStrInsertDate() {
        return strInsertDate__;
    }



    /**
     * <p>strInsertDate をセットします。
     * @param strInsertDate strInsertDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#strInsertDate__
     */
    public void setStrInsertDate(String strInsertDate) {
        strInsertDate__ = strInsertDate;
    }



    /**
     * <p>strLastSendDate を取得します。
     * @return strLastSendDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#strLastSendDate__
     */
    public String getStrLastSendDate() {
        return strLastSendDate__;
    }



    /**
     * <p>strLastSendDate をセットします。
     * @param strLastSendDate strLastSendDate
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#strLastSendDate__
     */
    public void setStrLastSendDate(String strLastSendDate) {
        strLastSendDate__ = strLastSendDate;
    }



    /**
     * <p>generalGroup を取得します。
     * @return generalGroup
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#generalGroup__
     */
    public List<CmnGroupmModel> getGeneralGroup() {
        return generalGroup__;
    }



    /**
     * <p>generalGroup をセットします。
     * @param generalGroup generalGroup
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#generalGroup__
     */
    public void setGeneralGroup(List<CmnGroupmModel> generalGroup) {
        generalGroup__ = generalGroup;
    }



    /**
     * <p>adminGroup を取得します。
     * @return adminGroup
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#adminGroup__
     */
    public List<CmnGroupmModel> getAdminGroup() {
        return adminGroup__;
    }



    /**
     * <p>adminGroup をセットします。
     * @param adminGroup adminGroup
     * @see jp.groupsession.v2.cht.model.ChatInformationModel#adminGroup__
     */
    public void setAdminGroup(List<CmnGroupmModel> adminGroup) {
        adminGroup__ = adminGroup;
    }

}