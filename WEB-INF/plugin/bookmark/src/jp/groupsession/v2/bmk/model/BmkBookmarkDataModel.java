package jp.groupsession.v2.bmk.model;

import java.util.List;


/**
 * <br>[機  能] ブックマーク情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkBookmarkDataModel extends BmkBookmarkModel {
    /** ユーザ名 */
    private String usrName__;
    /** 写真公開フラグ */
    private int userPictKf__ = 0;
    /** 投稿者写真ファイルSid */
    private Long photoFileSid__ = new Long(0);
    /** 投稿者写真ファイル名 */
    private String photoFileName__ = null;
    /** 投稿者写真ファイルパス */
    private String photoFilePath__ = null;
    /** 登録日時 */
    private String strBmkAdate__ = null;
    /** ラベル名 */
    private String labelName__ = null;
    /** ユーザ登録件数 */
    private int userCount__ = 0;
    /** ブックマークURL */
    private String bmkUrl__ = null;
    /** タイトル(表示用) */
    private List<String> bmkTitleDspList__;
    /** ログイン停止フラグ*/
    private int usrUkoFlg__ = 0;
    /**
     * <p>usrName を取得します。
     * @return usrName
     */
    public String getUsrName() {
        return usrName__;
    }

    /**
     * <p>usrName をセットします。
     * @param usrName usrName
     */
    public void setUsrName(String usrName) {
        usrName__ = usrName;
    }

    /**
     * <p>photoFileName を取得します。
     * @return photoFileName
     */
    public String getPhotoFileName() {
        return photoFileName__;
    }

    /**
     * <p>photoFileName をセットします。
     * @param photoFileName photoFileName
     */
    public void setPhotoFileName(String photoFileName) {
        photoFileName__ = photoFileName;
    }

    /**
     * <p>userPictKf を取得します。
     * @return userPictKf
     */
    public int getUserPictKf() {
        return userPictKf__;
    }

    /**
     * <p>userPictKf をセットします。
     * @param userPictKf userPictKf
     */
    public void setUserPictKf(int userPictKf) {
        userPictKf__ = userPictKf;
    }

    /**
     * <p>strBmkAdate を取得します。
     * @return strBmkAdate
     */
    public String getStrBmkAdate() {
        return strBmkAdate__;
    }

    /**
     * <p>strBmkAdate をセットします。
     * @param strBmkAdate strBmkAdate
     */
    public void setStrBmkAdate(String strBmkAdate) {
        strBmkAdate__ = strBmkAdate;
    }

    /**
     * <p>labelName を取得します。
     * @return labelName
     */
    public String getLabelName() {
        return labelName__;
    }

    /**
     * <p>labelName をセットします。
     * @param labelName labelName
     */
    public void setLabelName(String labelName) {
        labelName__ = labelName;
    }

    /**
     * <p>userCount を取得します。
     * @return userCount
     */
    public int getUserCount() {
        return userCount__;
    }

    /**
     * <p>userCount をセットします。
     * @param userCount userCount
     */
    public void setUserCount(int userCount) {
        userCount__ = userCount;
    }

    /**
     * <p>bmkUrl を取得します。
     * @return bmkUrl
     */
    public String getBmkUrl() {
        return bmkUrl__;
    }

    /**
     * <p>bmkUrl をセットします。
     * @param bmkUrl bmkUrl
     */
    public void setBmkUrl(String bmkUrl) {
        bmkUrl__ = bmkUrl;
    }

    /**
     * <p>bmkTitleDspList を取得します。
     * @return bmkTitleDspList
     */
    public List<String> getBmkTitleDspList() {
        return bmkTitleDspList__;
    }

    /**
     * <p>bmkTitleDspList をセットします。
     * @param bmkTitleDspList bmkTitleDspList
     */
    public void setBmkTitleDspList(List<String> bmkTitleDspList) {
        bmkTitleDspList__ = bmkTitleDspList;
    }

    /**
     * <p>photoFilePath を取得します。
     * @return photoFilePath
     */
    public String getPhotoFilePath() {
        return photoFilePath__;
    }

    /**
     * <p>photoFilePath をセットします。
     * @param photoFilePath photoFilePath
     */
    public void setPhotoFilePath(String photoFilePath) {
        photoFilePath__ = photoFilePath;
    }

    /**
     * <p>photoFileSid を取得します。
     * @return photoFileSid
     */
    public Long getPhotoFileSid() {
        return photoFileSid__;
    }

    /**
     * <p>photoFileSid をセットします。
     * @param photoFileSid photoFileSid
     */
    public void setPhotoFileSid(Long photoFileSid) {
        photoFileSid__ = photoFileSid;
    }

    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }

    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
}
