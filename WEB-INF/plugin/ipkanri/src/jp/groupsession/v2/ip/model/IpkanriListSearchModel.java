package jp.groupsession.v2.ip.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] IP管理 IPアドレス一覧の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkanriListSearchModel extends AbstractModel {

    /** ネットワークSID */
    private int netSid__;
    /** 使用状況区分 */
    private int usedKbn__;
    /** 昇順降順 */
    private int orderKey__;
    /** ソートKEY */
    private int sortKey__;

    /**
     * <p>usedKbn を取得します。
     * @return usedKbn
     */
    public int getUsedKbn() {
        return usedKbn__;
    }
    /**
     * <p>usedKbn をセットします。
     * @param usedKbn usedKbn
     */
    public void setUsedKbn(int usedKbn) {
        usedKbn__ = usedKbn;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public int getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(int netSid) {
        netSid__ = netSid;
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
}