package jp.groupsession.v2.zsk.maingrp;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;

/**
 * <br>[機  能] 在席管理(メイン画面表示用 メンバー)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskMaingrpParamModel extends AbstractParamModel {

    /** メイン画面表示フラグ */
    private int zskGrpDspFlg__ = GSConstZaiseki.MAINGRP_NOT_DSP;

    /** グループ所属ユーザ一覧*/
    private ArrayList<UserSearchModel> userList__;
    /** スケジュール一覧*/
    private ArrayList<ZskMaingrpModel> scheduleList__;

    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConstSchedule.PLUGIN_USE;
    /** 在席管理プラグイン利用可:0・不可:1*/
    private int zaisekiUseOk__ = GSConstSchedule.PLUGIN_USE;
    /** スケジュールプラグイン利用可:0・不可:1*/
    private int schUseOk__ = GSConstSchedule.PLUGIN_USE;

    /** 選択ユーザSID */
    private String zskSelectUsrSid__;
    /** 選択スケジュールSID */
    private String zskSelectSchSid__;

    /** 写真 ファイル名 */
    private String photoFileName__;
    /** 写真 ファイルパス */
    private String photoFilePath__;

    /** グループコンボ ラベル */
    private List<SchLabelValueModel> zaiGrpLabelList__;
    /** グループコンボ 選択値 */
    private String zaiGrpSid__ = "-1";

    /** スケジュール表示フラグ */
    private int zaiSchViewDf__ = GSConstZaiseki.MAIN_SCH_DSP;
    /** スケジュール登録可能フラグ */
    private boolean zaiSchRegistFlg__ = false;

    /** 在席トップ画面URL */
    private String zskTopUrl__;

    /** 閲覧ユーザSID */
    private int dspUserSid__;

    /**
     * @return zskTopUrl__ を戻します。
     */
    public String getZskTopUrl() {
        return zskTopUrl__;
    }
    /**
     * @param zskTopUrl 設定する zskTopUrl__。
     */
    public void setZskTopUrl(String zskTopUrl) {
        zskTopUrl__ = zskTopUrl;
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
    public ArrayList<ZskMaingrpModel> getScheduleList() {
        return scheduleList__;
    }

    /**
     * <p>scheduleList をセットします。
     * @param scheduleList scheduleList
     */
    public void setScheduleList(ArrayList<ZskMaingrpModel> scheduleList) {
        scheduleList__ = scheduleList;
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
     * <p>zaiGrpLabelList を取得します。
     * @return zaiGrpLabelList
     */
    public List<SchLabelValueModel> getZaiGrpLabelList() {
        return zaiGrpLabelList__;
    }

    /**
     * <p>zaiGrpLabelList をセットします。
     * @param zaiGrpLabelList zaiGrpLabelList
     */
    public void setZaiGrpLabelList(List<SchLabelValueModel> zaiGrpLabelList) {
        zaiGrpLabelList__ = zaiGrpLabelList;
    }

    /**
     * <p>zaiGrpSid を取得します。
     * @return zaiGrpSid
     */
    public String getZaiGrpSid() {
        return zaiGrpSid__;
    }

    /**
     * <p>zaiGrpSid をセットします。
     * @param zaiGrpSid zaiGrpSid
     */
    public void setZaiGrpSid(String zaiGrpSid) {
        zaiGrpSid__ = zaiGrpSid;
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
     * <p>zskGrpDspFlg を取得します。
     * @return zskGrpDspFlg
     */
    public int getZskGrpDspFlg() {
        return zskGrpDspFlg__;
    }

    /**
     * <p>zskGrpDspFlg をセットします。
     * @param zskGrpDspFlg zskGrpDspFlg
     */
    public void setZskGrpDspFlg(int zskGrpDspFlg) {
        zskGrpDspFlg__ = zskGrpDspFlg;
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
