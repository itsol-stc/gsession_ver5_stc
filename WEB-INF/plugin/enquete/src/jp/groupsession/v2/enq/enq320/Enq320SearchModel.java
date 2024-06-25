package jp.groupsession.v2.enq.enq320;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アンケート 結果確認（一覧）画面の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320SearchModel extends AbstractModel {

    /** アンケートSID */
    private long emnSid__ = 0;
    /** グループ */
    private int group__ = -1;
    /** ユーザ */
    private int user__ = -1;
    /** 状態 回答 */
    private int stsAns__ = 0;
    /** 状態 未回答 */
    private int stsNon__ = 0;
    /** ソートキー */
    private int sortKey__ = 0;
    /** 並び順 */
    private int order__ = 0;
    /** ページ */
    private int page__ = 0;
    /** 1ページの最大件数 */
    private int maxCount__ = 0;
    /**
     * <p>emnSid を取得します。
     * @return emnSid
     */
    public long getEmnSid() {
        return emnSid__;
    }
    /**
     * <p>emnSid をセットします。
     * @param emnSid emnSid
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }
    /**
     * <p>group を取得します。
     * @return group
     */
    public int getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(int group) {
        group__ = group;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public int getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(int user) {
        user__ = user;
    }
    /**
     * <p>stsAns を取得します。
     * @return stsAns
     */
    public int getStsAns() {
        return stsAns__;
    }
    /**
     * <p>stsAns をセットします。
     * @param stsAns stsAns
     */
    public void setStsAns(int stsAns) {
        stsAns__ = stsAns;
    }
    /**
     * <p>stsNon を取得します。
     * @return stsNon
     */
    public int getStsNon() {
        return stsNon__;
    }
    /**
     * <p>stsNon をセットします。
     * @param stsNon stsNon
     */
    public void setStsNon(int stsNon) {
        stsNon__ = stsNon;
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
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
    }
    /**
     * <p>page を取得します。
     * @return page
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(int page) {
        page__ = page;
    }
    /**
     * <p>maxCount を取得します。
     * @return maxCount
     */
    public int getMaxCount() {
        return maxCount__;
    }
    /**
     * <p>maxCount をセットします。
     * @param maxCount maxCount
     */
    public void setMaxCount(int maxCount) {
        maxCount__ = maxCount;
    }
}
