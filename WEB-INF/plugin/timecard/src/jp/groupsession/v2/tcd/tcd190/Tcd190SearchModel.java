package jp.groupsession.v2.tcd.tcd190;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数一覧画面で使用する検索用クラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd190SearchModel {
    
    /** 年度 */
    private int nendo__;
    
    /** グループSID */
    private int groupSid__;
    
    /** ソートキー */
    private int sortKey__;
    
    /** ソート順 */
    private int sortOrder__;
    
    /** ページ番号 */
    private int page__;
    
    /** 期首月 */
    private int kishuMonth__;
    
    /** 期末月 */
    private int kimatuMonth__;
    
    /**
     * <p>nendo を取得します。
     * @return nendo
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#nendo__
     */
    public int getNendo() {
        return nendo__;
    }

    /**
     * <p>nendo をセットします。
     * @param nendo nendo
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#nendo__
     */
    public void setNendo(int nendo) {
        nendo__ = nendo;
    }

    /**
     * <p>groupSid を取得します。
     * @return groupSid
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#groupSid__
     */
    public int getGroupSid() {
        return groupSid__;
    }

    /**
     * <p>groupSid をセットします。
     * @param groupSid groupSid
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#groupSid__
     */
    public void setGroupSid(int groupSid) {
        groupSid__ = groupSid;
    }

    /**
     * <p>sortKey を取得します。
     * @return sortKey
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#sortKey__
     */
    public int getSortKey() {
        return sortKey__;
    }

    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#sortKey__
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }

    /**
     * <p>sortOrder を取得します。
     * @return sortOrder
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#sortOrder__
     */
    public int getSortOrder() {
        return sortOrder__;
    }

    /**
     * <p>sortOrder をセットします。
     * @param sortOrder sortOrder
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#sortOrder__
     */
    public void setSortOrder(int sortOrder) {
        sortOrder__ = sortOrder;
    }

    /**
     * <p>page を取得します。
     * @return page
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#page__
     */
    public int getPage() {
        return page__;
    }

    /**
     * <p>page をセットします。
     * @param page page
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#page__
     */
    public void setPage(int page) {
        page__ = page;
    }

    /**
     * <p>kishuMonth を取得します。
     * @return kishuMonth
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#kishuMonth__
     */
    public int getKishuMonth() {
        return kishuMonth__;
    }

    /**
     * <p>kishuMonth をセットします。
     * @param kishuMonth kishuMonth
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#kishuMonth__
     */
    public void setKishuMonth(int kishuMonth) {
        kishuMonth__ = kishuMonth;
    }

    /**
     * <p>kimatuMonth を取得します。
     * @return kimatuMonth
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#kimatuMonth__
     */
    public int getKimatuMonth() {
        return kimatuMonth__;
    }

    /**
     * <p>kimatuMonth をセットします。
     * @param kimatuMonth kimatuMonth
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel#kimatuMonth__
     */
    public void setKimatuMonth(int kimatuMonth) {
        kimatuMonth__ = kimatuMonth;
    }

}
