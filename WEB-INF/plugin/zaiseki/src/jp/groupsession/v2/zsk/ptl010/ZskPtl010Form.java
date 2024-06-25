package jp.groupsession.v2.zsk.ptl010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.zsk010.Zsk010Form;

/**
 * <br>[機  能] 在席管理 ポートレット グループメンバーのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskPtl010Form extends Zsk010Form {

    /** メイン画面表示フラグ */
    private int zskPtlDspFlg__ = GSConstZaiseki.MAINGRP_NOT_DSP;

    /** 表示グループSID */
    private int dspGrpSid__ = -1;
    /** ポータルSID */
    private int ptlPortalSid__ = -1;

    /** グループ名 */
    private String grpName__ = "";

    /** グループ所属ユーザ一覧*/
    private ArrayList<UserSearchModel> userList__;
    /** スケジュール一覧*/
    private ArrayList<ZskPtl010Model> scheduleList__;

    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConst.PLUGIN_USE;
    /** 在席管理プラグイン利用可:0・不可:1*/
    private int zaisekiUseOk__ = GSConst.PLUGIN_USE;
    /** スケジュールプラグイン利用可:0・不可:1*/
    private int schUseOk__ = GSConst.PLUGIN_USE;

    /** 選択ユーザSID */
    private String zskSelectUsrSid__;
    /** 選択スケジュールSID */
    private String zskSelectSchSid__;

    /** 写真 ファイル名 */
    private String photoFileName__;
    /** 写真 ファイルパス */
    private String photoFilePath__;

    /** スケジュール表示フラグ */
    private int zaiSchViewDf__ = GSConstZaiseki.MAIN_SCH_DSP;
    /** スケジュール登録可能フラグ */
    private boolean zaiSchRegistFlg__ = false;

    /** 在席トップ画面URL */
    private String zskTopUrl__;

    /** 閲覧ユーザSID */
    private int dspUserSid__;

    /**
     * <p>ptlPortalSid を取得します。
     * @return ptlPortalSid
     */
    public int getPtlPortalSid() {
        return ptlPortalSid__;
    }

    /**
     * <p>ptlPortalSid をセットします。
     * @param ptlPortalSid ptlPortalSid
     */
    public void setPtlPortalSid(int ptlPortalSid) {
        ptlPortalSid__ = ptlPortalSid;
    }

    /**
     * <p>dspGrpSid を取得します。
     * @return dspGrpSid
     */
    public int getDspGrpSid() {
        return dspGrpSid__;
    }

    /**
     * <p>dspGrpSid をセットします。
     * @param dspGrpSid dspGrpSid
     */
    public void setDspGrpSid(int dspGrpSid) {
        dspGrpSid__ = dspGrpSid;
    }

    /**
     * <p>userList を取得します。
     * @return userList
     */
    public ArrayList<UserSearchModel> getUserList() {
        return userList__;
    }

    /**
     * <p>userList をセットします。
     * @param userList userList
     */
    public void setUserList(ArrayList<UserSearchModel> userList) {
        userList__ = userList;
    }

    /**
     * <p>scheduleList を取得します。
     * @return scheduleList
     */
    public ArrayList<ZskPtl010Model> getScheduleList() {
        return scheduleList__;
    }

    /**
     * <p>scheduleList をセットします。
     * @param scheduleList scheduleList
     */
    public void setScheduleList(ArrayList<ZskPtl010Model> scheduleList) {
        scheduleList__ = scheduleList;
    }

    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }

    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }

    /**
     * <p>zaisekiUseOk を取得します。
     * @return zaisekiUseOk
     */
    public int getZaisekiUseOk() {
        return zaisekiUseOk__;
    }

    /**
     * <p>zaisekiUseOk をセットします。
     * @param zaisekiUseOk zaisekiUseOk
     */
    public void setZaisekiUseOk(int zaisekiUseOk) {
        zaisekiUseOk__ = zaisekiUseOk;
    }

    /**
     * <p>schUseOk を取得します。
     * @return schUseOk
     */
    public int getSchUseOk() {
        return schUseOk__;
    }

    /**
     * <p>schUseOk をセットします。
     * @param schUseOk schUseOk
     */
    public void setSchUseOk(int schUseOk) {
        schUseOk__ = schUseOk;
    }

    /**
     * <p>zskSelectUsrSid を取得します。
     * @return zskSelectUsrSid
     */
    public String getZskSelectUsrSid() {
        return zskSelectUsrSid__;
    }

    /**
     * <p>zskSelectUsrSid をセットします。
     * @param zskSelectUsrSid zskSelectUsrSid
     */
    public void setZskSelectUsrSid(String zskSelectUsrSid) {
        zskSelectUsrSid__ = zskSelectUsrSid;
    }

    /**
     * <p>zskSelectSchSid を取得します。
     * @return zskSelectSchSid
     */
    public String getZskSelectSchSid() {
        return zskSelectSchSid__;
    }

    /**
     * <p>zskSelectSchSid をセットします。
     * @param zskSelectSchSid zskSelectSchSid
     */
    public void setZskSelectSchSid(String zskSelectSchSid) {
        zskSelectSchSid__ = zskSelectSchSid;
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
     * <p>zaiSchViewDf を取得します。
     * @return zaiSchViewDf
     */
    public int getZaiSchViewDf() {
        return zaiSchViewDf__;
    }

    /**
     * <p>zaiSchViewDf をセットします。
     * @param zaiSchViewDf zaiSchViewDf
     */
    public void setZaiSchViewDf(int zaiSchViewDf) {
        zaiSchViewDf__ = zaiSchViewDf;
    }

    /**
     * <p>zaiSchRegistFlg を取得します。
     * @return zaiSchRegistFlg
     */
    public boolean isZaiSchRegistFlg() {
        return zaiSchRegistFlg__;
    }

    /**
     * <p>zaiSchRegistFlg をセットします。
     * @param zaiSchRegistFlg zaiSchRegistFlg
     */
    public void setZaiSchRegistFlg(boolean zaiSchRegistFlg) {
        zaiSchRegistFlg__ = zaiSchRegistFlg;
    }

    /**
     * <p>zskTopUrl を取得します。
     * @return zskTopUrl
     */
    public String getZskTopUrl() {
        return zskTopUrl__;
    }

    /**
     * <p>zskTopUrl をセットします。
     * @param zskTopUrl zskTopUrl
     */
    public void setZskTopUrl(String zskTopUrl) {
        zskTopUrl__ = zskTopUrl;
    }

    /**
     * <p>grpName を取得します。
     * @return grpName
     */
    public String getGrpName() {
        return grpName__;
    }

    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }

    /**
     * <p>zskPtlDspFlg を取得します。
     * @return zskPtlDspFlg
     */
    public int getZskPtlDspFlg() {
        return zskPtlDspFlg__;
    }

    /**
     * <p>zskPtlDspFlg をセットします。
     * @param zskPtlDspFlg zskPtlDspFlg
     */
    public void setZskPtlDspFlg(int zskPtlDspFlg) {
        zskPtlDspFlg__ = zskPtlDspFlg;
    }

    /**
     * @return dspUserSid
     */
    public int getDspUserSid() {
        return dspUserSid__;
    }

    /**
     * @param dspUserSid セットする dspUserSid
     */
    public void setDspUserSid(int dspUserSid) {
        dspUserSid__ = dspUserSid;
    }


}