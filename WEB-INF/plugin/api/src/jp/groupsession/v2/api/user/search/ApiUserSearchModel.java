package jp.groupsession.v2.api.user.search;

import jp.groupsession.v2.usr.usr040.ShainSearchModel;
/**
 *
 * <br>[機  能] Api ユーザ検索モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiUserSearchModel extends ShainSearchModel {

    /** ページ */
    private int page__ = 1;
    /** 1ページの最大表示件数 */
    private int results__ = 50;
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
     * <p>results を取得します。
     * @return results
     */
    public int getResults() {
        return results__;
    }
    /**
     * <p>results をセットします。
     * @param results results
     */
    public void setResults(int results) {
        results__ = results;
    }

}
