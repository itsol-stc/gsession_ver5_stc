package jp.groupsession.v2.ntp.ntp130;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <br>[機  能] 日報 商品一覧画面の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp130SearchModel extends NtpShohinModel {
    /** 販売価格区分 */
    private int nhnPriceSaleKbn__ = Ntp130Biz.PRICE_MORE;
    /** 原価価格区分 */
    private int nhnPriceCostKbn__ = Ntp130Biz.PRICE_MORE;
    /** ソートキー１ */
    private int sortKey1__ = -1;
    /** オーダキー１ */
    private int orderKey1__ = GSConstNippou.ORDER_KEY_ASC;
    /** ソートキー２ */
    private int sortKey2__ = -1;
    /** オーダキー２ */
    private int orderKey2__ = GSConstNippou.ORDER_KEY_ASC;
    /**
     * @return nhnPriceCostKbn
     */
    public int getNhnPriceCostKbn() {
        return nhnPriceCostKbn__;
    }
    /**
     * @param nhnPriceCostKbn 設定する nhnPriceCostKbn
     */
    public void setNhnPriceCostKbn(int nhnPriceCostKbn) {
        nhnPriceCostKbn__ = nhnPriceCostKbn;
    }
    /**
     * @return nhnPriceSaleKbn
     */
    public int getNhnPriceSaleKbn() {
        return nhnPriceSaleKbn__;
    }
    /**
     * @param nhnPriceSaleKbn 設定する nhnPriceSaleKbn
     */
    public void setNhnPriceSaleKbn(int nhnPriceSaleKbn) {
        nhnPriceSaleKbn__ = nhnPriceSaleKbn;
    }
    /**
     * @return orderKey1
     */
    public int getOrderKey1() {
        return orderKey1__;
    }
    /**
     * @param orderKey1 設定する orderKey1
     */
    public void setOrderKey1(int orderKey1) {
        orderKey1__ = orderKey1;
    }
    /**
     * @return orderKey2
     */
    public int getOrderKey2() {
        return orderKey2__;
    }
    /**
     * @param orderKey2 設定する orderKey2
     */
    public void setOrderKey2(int orderKey2) {
        orderKey2__ = orderKey2;
    }
    /**
     * @return sortKey1
     */
    public int getSortKey1() {
        return sortKey1__;
    }
    /**
     * @param sortKey1 設定する sortKey1
     */
    public void setSortKey1(int sortKey1) {
        sortKey1__ = sortKey1;
    }
    /**
     * @return sortKey2
     */
    public int getSortKey2() {
        return sortKey2__;
    }
    /**
     * @param sortKey2 設定する sortKey2
     */
    public void setSortKey2(int sortKey2) {
        sortKey2__ = sortKey2;
    }

}
