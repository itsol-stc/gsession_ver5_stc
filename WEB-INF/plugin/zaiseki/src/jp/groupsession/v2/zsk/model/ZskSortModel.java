package jp.groupsession.v2.zsk.model;

import java.io.Serializable;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.zsk.GSConstZaiseki;

/**
 * <br>[機  能] 在席管理の表示順情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskSortModel implements Serializable {

    /** 表示順項目1 */
    int sort1__ = GSConstZaiseki.SORT_KEY_NAME;
    /** 表示順項目1のソートオーダー */
    int order1__ = GSConst.ORDER_KEY_ASC;
    /** 表示順項目2 */
    int sort2__ = GSConstZaiseki.SORT_KEY_NAME;
    /** 表示順項目2のソートオーダー */
    int order2__ = GSConst.ORDER_KEY_ASC;
    /**
     * <p>order1 を取得します。
     * @return order1
     */
    public int getOrder1() {
        return order1__;
    }
    /**
     * <p>order1 をセットします。
     * @param order1 order1
     */
    public void setOrder1(int order1) {
        order1__ = order1;
    }
    /**
     * <p>order2 を取得します。
     * @return order2
     */
    public int getOrder2() {
        return order2__;
    }
    /**
     * <p>order2 をセットします。
     * @param order2 order2
     */
    public void setOrder2(int order2) {
        order2__ = order2;
    }
    /**
     * <p>sort1 を取得します。
     * @return sort1
     */
    public int getSort1() {
        return sort1__;
    }
    /**
     * <p>sort1 をセットします。
     * @param sort1 sort1
     */
    public void setSort1(int sort1) {
        sort1__ = sort1;
    }
    /**
     * <p>sort2 を取得します。
     * @return sort2
     */
    public int getSort2() {
        return sort2__;
    }
    /**
     * <p>sort2 をセットします。
     * @param sort2 sort2
     */
    public void setSort2(int sort2) {
        sort2__ = sort2;
    }
}
