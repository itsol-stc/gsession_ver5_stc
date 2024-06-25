package jp.groupsession.v2.sch.ptl010;

import jp.groupsession.v2.sch.model.SchEasyRegisterModel;
import jp.groupsession.v2.sch.sch010.Sch010ParamModel;

/**
 * <br>[機  能] スケジュール ポートレット グループスケジュール画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchPtl010ParamModel extends Sch010ParamModel {

    /** メイン画面表示フラグ */
    private int schPtlDspFlg__ = 1;
    /** グループ名 */
    private String schPtl010GrpName__ = "";

    /** ポートレットアイテムID */
    private String schPtl010ItemId__ = "";
    /** 表示グループSID */
    private int schDspGrpSid__ = -1;
    /** ポータルSID */
    private int ptlPortalSid__ = -1;

    //共通項目
    /** 管理者権限有無 1:権限有り 2:権限無し*/
    private int adminKbn__;

    //週間スケジュール
    /** 週間スケジュール 表示日付 */
    private String schWeekDate__;
    /** 週間スケジュール情報モデル */
    private SchPtl010Model schWeekMdl__;
    //スケジュール共通
    /** 選択日付 */
    private String schSelectDate__;
    /** 選択ユーザSID */
    private String schSelectUsrSid__;
    /** 選択スケジュールSID */
    private String schSelectSchSid__;
    /** 動作区分*/
    private int moveKbn__ = 0;
    /** スケジュール登録可能フラグ */
    private boolean schRegistFlg__ = false;
    /** 簡易登録モデル*/
    private SchEasyRegisterModel easyRegister__ = new SchEasyRegisterModel();

    /**
     * <p>schDspGrpSid を取得します。
     * @return schDspGrpSid
     */
    public int getSchDspGrpSid() {
        return schDspGrpSid__;
    }
    /**
     * <p>schDspGrpSid をセットします。
     * @param schDspGrpSid schDspGrpSid
     */
    public void setSchDspGrpSid(int schDspGrpSid) {
        schDspGrpSid__ = schDspGrpSid;
    }
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
     * <p>adminKbn を取得します。
     * @return adminKbn
     */
    public int getAdminKbn() {
        return adminKbn__;
    }
    /**
     * <p>adminKbn をセットします。
     * @param adminKbn adminKbn
     */
    public void setAdminKbn(int adminKbn) {
        adminKbn__ = adminKbn;
    }
    /**
     * <p>schWeekDate を取得します。
     * @return schWeekDate
     */
    public String getSchWeekDate() {
        return schWeekDate__;
    }
    /**
     * <p>schWeekDate をセットします。
     * @param schWeekDate schWeekDate
     */
    public void setSchWeekDate(String schWeekDate) {
        schWeekDate__ = schWeekDate;
    }
    /**
     * <p>schWeekMdl を取得します。
     * @return schWeekMdl
     */
    public SchPtl010Model getSchWeekMdl() {
        return schWeekMdl__;
    }
    /**
     * <p>schWeekMdl をセットします。
     * @param schWeekMdl schWeekMdl
     */
    public void setSchWeekMdl(SchPtl010Model schWeekMdl) {
        schWeekMdl__ = schWeekMdl;
    }
    /**
     * <p>schSelectDate を取得します。
     * @return schSelectDate
     */
    public String getSchSelectDate() {
        return schSelectDate__;
    }
    /**
     * <p>schSelectDate をセットします。
     * @param schSelectDate schSelectDate
     */
    public void setSchSelectDate(String schSelectDate) {
        schSelectDate__ = schSelectDate;
    }
    /**
     * <p>schSelectUsrSid を取得します。
     * @return schSelectUsrSid
     */
    public String getSchSelectUsrSid() {
        return schSelectUsrSid__;
    }
    /**
     * <p>schSelectUsrSid をセットします。
     * @param schSelectUsrSid schSelectUsrSid
     */
    public void setSchSelectUsrSid(String schSelectUsrSid) {
        schSelectUsrSid__ = schSelectUsrSid;
    }
    /**
     * <p>schSelectSchSid を取得します。
     * @return schSelectSchSid
     */
    public String getSchSelectSchSid() {
        return schSelectSchSid__;
    }
    /**
     * <p>schSelectSchSid をセットします。
     * @param schSelectSchSid schSelectSchSid
     */
    public void setSchSelectSchSid(String schSelectSchSid) {
        schSelectSchSid__ = schSelectSchSid;
    }
    /**
     * <p>moveKbn を取得します。
     * @return moveKbn
     */
    public int getMoveKbn() {
        return moveKbn__;
    }
    /**
     * <p>moveKbn をセットします。
     * @param moveKbn moveKbn
     */
    public void setMoveKbn(int moveKbn) {
        moveKbn__ = moveKbn;
    }
    /**
     * <p>schRegistFlg を取得します。
     * @return schRegistFlg
     */
    public boolean isSchRegistFlg() {
        return schRegistFlg__;
    }
    /**
     * <p>schRegistFlg をセットします。
     * @param schRegistFlg schRegistFlg
     */
    public void setSchRegistFlg(boolean schRegistFlg) {
        schRegistFlg__ = schRegistFlg;
    }
    /**
     * <p>schPtlDspFlg を取得します。
     * @return schPtlDspFlg
     */
    public int getSchPtlDspFlg() {
        return schPtlDspFlg__;
    }
    /**
     * <p>schPtlDspFlg をセットします。
     * @param schPtlDspFlg schPtlDspFlg
     */
    public void setSchPtlDspFlg(int schPtlDspFlg) {
        schPtlDspFlg__ = schPtlDspFlg;
    }
    /**
     * <p>schPtl010ItemId を取得します。
     * @return schPtl010ItemId
     */
    public String getSchPtl010ItemId() {
        return schPtl010ItemId__;
    }
    /**
     * <p>schPtl010ItemId をセットします。
     * @param schPtl010ItemId schPtl010ItemId
     */
    public void setSchPtl010ItemId(String schPtl010ItemId) {
        schPtl010ItemId__ = schPtl010ItemId;
    }
    /**
     * <p>schPtl010GrpName を取得します。
     * @return schPtl010GrpName
     */
    public String getSchPtl010GrpName() {
        return schPtl010GrpName__;
    }
    /**
     * <p>schPtl010GrpName をセットします。
     * @param schPtl010GrpName schPtl010GrpName
     */
    public void setSchPtl010GrpName(String schPtl010GrpName) {
        schPtl010GrpName__ = schPtl010GrpName;
    }
    /**
     * <p>easyRegister を取得します。
     * @return easyRegister
     * @see jp.groupsession.v2.sch.ptl010.SchPtl010ParamModel#easyRegister__
     */
    public SchEasyRegisterModel getEasyRegister() {
        return easyRegister__;
    }
    /**
     * <p>easyRegister をセットします。
     * @param easyRegister easyRegister
     * @see jp.groupsession.v2.sch.ptl010.SchPtl010ParamModel#easyRegister__
     */
    public void setEasyRegister(SchEasyRegisterModel easyRegister) {
        easyRegister__ = easyRegister;
    }
}