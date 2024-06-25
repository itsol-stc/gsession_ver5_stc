package jp.groupsession.v2.cir.cir020.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 回覧先一覧の取得条件を格納するModelクラス（添付ファイル情報は除く）
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020KnDataSearchModel extends AbstractModel {

    /** 回覧板SID */
    private int cirSid__;
    /** 選択グループ */
    private int selectGrp__;
    /** ソートキー */
    private int sortKey__;
    /** オーダーキー */
    private int orderKey__;

    /**
     * <p>cirSid を取得します。
     * @return cirSid
     */
    public int getCirSid() {
        return cirSid__;
    }
    /**
     * <p>cirSid をセットします。
     * @param cirSid cirSid
     */
    public void setCirSid(int cirSid) {
        cirSid__ = cirSid;
    }
    /**
     * <p>selectGrp を取得します。
     * @return selectGrp
     */
    public int getSelectGrp() {
        return selectGrp__;
    }
    /**
     * <p>selectGrp をセットします。
     * @param selectGrp selectGrp
     */
    public void setSelectGrp(int selectGrp) {
        selectGrp__ = selectGrp;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }
    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }
}
