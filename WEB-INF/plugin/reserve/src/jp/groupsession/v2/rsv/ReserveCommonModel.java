package jp.groupsession.v2.rsv;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約プラグインで共通使用するModelクラス
 * <br>[解  説] 施設グループに関する情報を格納する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ReserveCommonModel extends AbstractModel {

    /** 管理者として設定されている施設グループ数 */
    private int groupAdmCnt__;
    /** 「権限設定しない」となっている施設グループ数 */
    private int groupAdmNotSetCnt__;

    /**
     * <p>groupAdmCnt__ を取得します。
     * @return groupAdmCnt
     */
    public int getGroupAdmCnt() {
        return groupAdmCnt__;
    }
    /**
     * <p>groupAdmCnt__ をセットします。
     * @param groupAdmCnt groupAdmCnt__
     */
    public void setGroupAdmCnt(int groupAdmCnt) {
        groupAdmCnt__ = groupAdmCnt;
    }
    /**
     * <p>groupAdmNotSetCnt__ を取得します。
     * @return groupAdmNotSetCnt
     */
    public int getGroupAdmNotSetCnt() {
        return groupAdmNotSetCnt__;
    }
    /**
     * <p>groupAdmNotSetCnt__ をセットします。
     * @param groupAdmNotSetCnt groupAdmNotSetCnt__
     */
    public void setGroupAdmNotSetCnt(int groupAdmNotSetCnt) {
        groupAdmNotSetCnt__ = groupAdmNotSetCnt;
    }
}