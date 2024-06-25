package jp.groupsession.v2.fil.fil020.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * キャビネット詳細アクセス制御一覧表示用のモデル
 * @author JTS
 *
 */
public class FileAccessUserModel extends CmnUsrmInfModel {

    /** キャビネット管理者区分 */
    private int cabinetAdminKbn__;
    /** キャビネットアクセス区分 */
    private int cabinetAccessKbn__;

    /**
     * @return cabinetAccessKbn
     */
    public int getCabinetAccessKbn() {
        return cabinetAccessKbn__;
    }
    /**
     * @param cabinetAccessKbn 設定する cabinetAccessKbn
     */
    public void setCabinetAccessKbn(int cabinetAccessKbn) {
        cabinetAccessKbn__ = cabinetAccessKbn;
    }
    /**
     * @return cabinetAdminKbn
     */
    public int getCabinetAdminKbn() {
        return cabinetAdminKbn__;
    }
    /**
     * @param cabinetAdminKbn 設定する cabinetAdminKbn
     */
    public void setCabinetAdminKbn(int cabinetAdminKbn) {
        cabinetAdminKbn__ = cabinetAdminKbn;
    }


}
