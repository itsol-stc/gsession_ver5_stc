package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_GROUP_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChatMidokuModel implements Serializable {

    /** 名称*/
    private String midokuName__;

    /** 区分 1:ユーザ 2:グループ*/
    private int midokuKbn__;

    /** 選択SID*/
    private int midokuSid__;

    /** 状態区分 ユーザ削除*/
    private int midokuJkbn__;

    /** 状態区分 ユーザ無効*/
    private int usrUkoFlg__;

    /** 状態区分 アーカイブ 0:非アーカイブ 1:アーカイブ*/
    private int archiveFlg__;

    /** 最終投稿日時*/
    private UDate midokuDate__;

    /** 未読件数*/
    private int midokuCount__;

    /**最終投稿日時(表示用)*/
    private String midokuDispDate__;

    /**
     * <p>Default Constructor
     */
    public ChatMidokuModel() {
    }




    /**
     * <p>midokuName を取得します。
     * @return midokuName
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuName__
     */
    public String getMidokuName() {
        return midokuName__;
    }




    /**
     * <p>midokuName をセットします。
     * @param midokuName midokuName
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuName__
     */
    public void setMidokuName(String midokuName) {
        midokuName__ = midokuName;
    }




    /**
     * <p>midokuKbn を取得します。
     * @return midokuKbn
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuKbn__
     */
    public int getMidokuKbn() {
        return midokuKbn__;
    }




    /**
     * <p>midokuKbn をセットします。
     * @param midokuKbn midokuKbn
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuKbn__
     */
    public void setMidokuKbn(int midokuKbn) {
        midokuKbn__ = midokuKbn;
    }




    /**
     * <p>midokuSid を取得します。
     * @return midokuSid
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuSid__
     */
    public int getMidokuSid() {
        return midokuSid__;
    }




    /**
     * <p>midokuSid をセットします。
     * @param midokuSid midokuSid
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuSid__
     */
    public void setMidokuSid(int midokuSid) {
        midokuSid__ = midokuSid;
    }

    /**
     * <p>midokuJkbn を取得します。
     * @return midokuJkbn
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuJkbn__
     */
    public int getMidokuJkbn() {
        return midokuJkbn__;
    }




    /**
     * <p>midokuJkbn をセットします。
     * @param midokuJkbn midokuJkbn
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuJkbn__
     */
    public void setMidokuJkbn(int midokuJkbn) {
        midokuJkbn__ = midokuJkbn;
    }




    /**
     * <p>midokuDate を取得します。
     * @return midokuDate
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuDate__
     */
    public UDate getMidokuDate() {
        return midokuDate__;
    }




    /**
     * <p>midokuDate をセットします。
     * @param midokuDate midokuDate
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuDate__
     */
    public void setMidokuDate(UDate midokuDate) {
        midokuDate__ = midokuDate;
    }

    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#usrUkoFlg__
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }




    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#usrUkoFlg__
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }




    /**
     * <p>midokuCount を取得します。
     * @return midokuCount
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuCount__
     */
    public int getMidokuCount() {
        return midokuCount__;
    }




    /**
     * <p>midokuCount をセットします。
     * @param midokuCount midokuCount
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuCount__
     */
    public void setMidokuCount(int midokuCount) {
        midokuCount__ = midokuCount;
    }




    /**
     * <p>midokuDispDate を取得します。
     * @return midokuDispDate
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuDispDate__
     */
    public String getMidokuDispDate() {
        return midokuDispDate__;
    }




    /**
     * <p>midokuDispDate をセットします。
     * @param midokuDispDate midokuDispDate
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#midokuDispDate__
     */
    public void setMidokuDispDate(String midokuDispDate) {
        midokuDispDate__ = midokuDispDate;
    }




    /**
     * <p>archiveFlg を取得します。
     * @return archiveFlg
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#archiveFlg__
     */
    public int getArchiveFlg() {
        return archiveFlg__;
    }




    /**
     * <p>archiveFlg をセットします。
     * @param archiveFlg archiveFlg
     * @see jp.groupsession.v2.cht.model.ChatMidokuModel#archiveFlg__
     */
    public void setArchiveFlg(int archiveFlg) {
        archiveFlg__ = archiveFlg;
    }
}