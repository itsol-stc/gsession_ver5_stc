package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <p>CHT_GROUP_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChatMessageModel implements Serializable {

    /** メッセージSID */
    private long messageSid__;
    /** 選択SID */
    private int selectSid__;
    /** ユーザSID*/
    private int usrSid__;
    /** ユーザ名*/
    private String usrName__;
    /** ユーザ削除区分*/
    private int usrJkbn__;
    /** ユーザ有効フラグ*/
    private int usrUkoFlg__;
    /** ユーザ添付SID*/
    private int usrBinSid__;
    /** ユーザ添付公開区分*/
    private int usrPictKf__;
    /** 投稿内容 */
    private String messageText__;
    /** 添付SID*/
    private long binSid__;
    /** 投稿状態*/
    private int messageKbn__;
    /** 登録者ID */
    private int insertUid__;
    /** 登録日時 */
    private UDate insertDate__;
    /** 更新者ID */
    private int updateUid__;
    /** 更新日時 */
    private UDate updateDate__;
    /** 添付ファイル情報*/
    private CmnBinfModel tmpFile__;
    /** 自身既読フラグ*/
    private int ownKidoku__;
    /** 相手既読フラグ*/
    private int partnerKidoku__;
    /** 登録日*/
    private String entryDay__;
    /** 登録時間*/
    private String entryTime__;
    /** 更新日*/
    private String updateDay__;
    /** 更新時間*/
    private String updateTime__;


    /**
     * <p>Default Constructor
     */
    public ChatMessageModel() {
    }


    /**
     * <p>messageSid を取得します。
     * @return messageSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#messageSid__
     */
    public long getMessageSid() {
        return messageSid__;
    }


    /**
     * <p>messageSid をセットします。
     * @param messageSid messageSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#messageSid__
     */
    public void setMessageSid(long messageSid) {
        messageSid__ = messageSid;
    }


    /**
     * <p>selectSid を取得します。
     * @return selectSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#selectSid__
     */
    public int getSelectSid() {
        return selectSid__;
    }


    /**
     * <p>selectSid をセットします。
     * @param selectSid selectSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#selectSid__
     */
    public void setSelectSid(int selectSid) {
        selectSid__ = selectSid;
    }


    /**
     * <p>messageText を取得します。
     * @return messageText
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#messageText__
     */
    public String getMessageText() {
        return messageText__;
    }


    /**
     * <p>messageText をセットします。
     * @param messageText messageText
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#messageText__
     */
    public void setMessageText(String messageText) {
        messageText__ = messageText;
    }


    /**
     * <p>binSid を取得します。
     * @return binSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#binSid__
     */
    public long getBinSid() {
        return binSid__;
    }


    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#binSid__
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }


    /**
     * <p>insertUid を取得します。
     * @return insertUid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#insertUid__
     */
    public int getInsertUid() {
        return insertUid__;
    }


    /**
     * <p>insertUid をセットします。
     * @param insertUid insertUid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#insertUid__
     */
    public void setInsertUid(int insertUid) {
        insertUid__ = insertUid;
    }


    /**
     * <p>insertDate を取得します。
     * @return insertDate
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#insertDate__
     */
    public UDate getInsertDate() {
        return insertDate__;
    }


    /**
     * <p>insertDate をセットします。
     * @param insertDate insertDate
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#insertDate__
     */
    public void setInsertDate(UDate insertDate) {
        insertDate__ = insertDate;
    }


    /**
     * <p>updateUid を取得します。
     * @return updateUid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateUid__
     */
    public int getUpdateUid() {
        return updateUid__;
    }


    /**
     * <p>updateUid をセットします。
     * @param updateUid updateUid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateUid__
     */
    public void setUpdateUid(int updateUid) {
        updateUid__ = updateUid;
    }


    /**
     * <p>updateDate を取得します。
     * @return updateDate
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateDate__
     */
    public UDate getUpdateDate() {
        return updateDate__;
    }


    /**
     * <p>updateDate をセットします。
     * @param updateDate updateDate
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateDate__
     */
    public void setUpdateDate(UDate updateDate) {
        updateDate__ = updateDate;
    }


    /**
     * <p>tmpFile を取得します。
     * @return tmpFile
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#tmpFile__
     */
    public CmnBinfModel getTmpFile() {
        return tmpFile__;
    }


    /**
     * <p>tmpFile をセットします。
     * @param tmpFile tmpFile
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#tmpFile__
     */
    public void setTmpFile(CmnBinfModel tmpFile) {
        tmpFile__ = tmpFile;
    }


    /**
     * <p>ownKidoku を取得します。
     * @return ownKidoku
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#ownKidoku__
     */
    public int getOwnKidoku() {
        return ownKidoku__;
    }


    /**
     * <p>ownKidoku をセットします。
     * @param ownKidoku ownKidoku
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#ownKidoku__
     */
    public void setOwnKidoku(int ownKidoku) {
        ownKidoku__ = ownKidoku;
    }


    /**
     * <p>partnerKidoku を取得します。
     * @return partnerKidoku
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#partnerKidoku__
     */
    public int getPartnerKidoku() {
        return partnerKidoku__;
    }


    /**
     * <p>partnerKidoku をセットします。
     * @param partnerKidoku partnerKidoku
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#partnerKidoku__
     */
    public void setPartnerKidoku(int partnerKidoku) {
        partnerKidoku__ = partnerKidoku;
    }


    /**
     * <p>entryDay を取得します。
     * @return entryDay
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#entryDay__
     */
    public String getEntryDay() {
        return entryDay__;
    }


    /**
     * <p>entryDay をセットします。
     * @param entryDay entryDay
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#entryDay__
     */
    public void setEntryDay(String entryDay) {
        entryDay__ = entryDay;
    }


    /**
     * <p>usrSid を取得します。
     * @return usrSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrSid__
     */
    public int getUsrSid() {
        return usrSid__;
    }


    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrSid__
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }


    /**
     * <p>usrName を取得します。
     * @return usrName
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrName__
     */
    public String getUsrName() {
        return usrName__;
    }


    /**
     * <p>usrName をセットします。
     * @param usrName usrName
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrName__
     */
    public void setUsrName(String usrName) {
        usrName__ = usrName;
    }


    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrJkbn__
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }


    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrJkbn__
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }


    /**
     * <p>usrUkouFlg を取得します。
     * @return usrUkouFlg
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrUkouFlg__
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }


    /**
     * <p>usrUkouFlg をセットします。
     * @param usrUkoFlg usrUkouFlg
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrUkouFlg__
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }


    /**
     * <p>messageKbn を取得します。
     * @return messageKbn
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#messageKbn__
     */
    public int getMessageKbn() {
        return messageKbn__;
    }


    /**
     * <p>messageKbn をセットします。
     * @param messageKbn messageKbn
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#messageKbn__
     */
    public void setMessageKbn(int messageKbn) {
        messageKbn__ = messageKbn;
    }


    /**
     * <p>entryTime を取得します。
     * @return entryTime
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#entryTime__
     */
    public String getEntryTime() {
        return entryTime__;
    }


    /**
     * <p>entryTime をセットします。
     * @param entryTime entryTime
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#entryTime__
     */
    public void setEntryTime(String entryTime) {
        entryTime__ = entryTime;
    }


    /**
     * <p>updateDay を取得します。
     * @return updateDay
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateDay__
     */
    public String getUpdateDay() {
        return updateDay__;
    }


    /**
     * <p>updateDay をセットします。
     * @param updateDay updateDay
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateDay__
     */
    public void setUpdateDay(String updateDay) {
        updateDay__ = updateDay;
    }


    /**
     * <p>updateTime を取得します。
     * @return updateTime
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateTime__
     */
    public String getUpdateTime() {
        return updateTime__;
    }


    /**
     * <p>updateTime をセットします。
     * @param updateTime updateTime
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#updateTime__
     */
    public void setUpdateTime(String updateTime) {
        updateTime__ = updateTime;
    }


    /**
     * <p>usrBinSid を取得します。
     * @return usrBinSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrBinSid__
     */
    public int getUsrBinSid() {
        return usrBinSid__;
    }


    /**
     * <p>usrBinSid をセットします。
     * @param usrBinSid usrBinSid
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrBinSid__
     */
    public void setUsrBinSid(int usrBinSid) {
        usrBinSid__ = usrBinSid;
    }


    /**
     * <p>usrPictKf を取得します。
     * @return usrPictKf
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrPictKf__
     */
    public int getUsrPictKf() {
        return usrPictKf__;
    }


    /**
     * <p>usrPictKf をセットします。
     * @param usrPictKf usrPictKf
     * @see jp.groupsession.v2.cht.model.ChatMessageModel#usrPictKf__
     */
    public void setUsrPictKf(int usrPictKf) {
        usrPictKf__ = usrPictKf;
    }
}