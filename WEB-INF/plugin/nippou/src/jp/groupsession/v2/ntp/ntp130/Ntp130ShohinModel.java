package jp.groupsession.v2.ntp.ntp130;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <br>[機  能] 日報 商品情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp130ShohinModel extends NtpShohinModel {
    /** 販売価格表示用 */
    private String ntp130PriceSale__;
    /** 原価価格表示用 */
    private String ntp130PriceCost__;
    /** 登録日表示用 */
    private String ntp130ADate__;
    /** 更新日表示用 */
    private String ntp130EDate__;
    /** カテゴリ名 */
    private String nscName__;

    /** 商品リスト ポップアップ表示用 */
    private List<NtpShohinModel> shohinDataList__;
    /** 最大ページ数 ポップアップ表示用 */
    private int maxPageSize__;
    /** ページ番号 ポップアップ表示用 */
    private int pageNum__;

    /**
     * @return ntp130PriceCost
     */
    public String getNtp130PriceCost() {
        return ntp130PriceCost__;
    }
    /**
     * @param ntp130PriceCost 設定する ntp130PriceCost
     */
    public void setNtp130PriceCost(String ntp130PriceCost) {
        ntp130PriceCost__ = ntp130PriceCost;
    }
    /**
     * @return ntp130PriceSale
     */
    public String getNtp130PriceSale() {
        return ntp130PriceSale__;
    }
    /**
     * @param ntp130PriceSale 設定する ntp130PriceSale
     */
    public void setNtp130PriceSale(String ntp130PriceSale) {
        ntp130PriceSale__ = ntp130PriceSale;
    }
    /**
     * <p>ntp130ADate を取得します。
     * @return ntp130ADate
     */
    public String getNtp130ADate() {
        return ntp130ADate__;
    }
    /**
     * <p>ntp130ADate をセットします。
     * @param ntp130aDate ntp130ADate
     */
    public void setNtp130ADate(String ntp130aDate) {
        ntp130ADate__ = ntp130aDate;
    }
    /**
     * <p>ntp130EDate を取得します。
     * @return ntp130EDate
     */
    public String getNtp130EDate() {
        return ntp130EDate__;
    }
    /**
     * <p>ntp130EDate をセットします。
     * @param ntp130eDate ntp130EDate
     */
    public void setNtp130EDate(String ntp130eDate) {
        ntp130EDate__ = ntp130eDate;
    }
    /**
     * <p>maxPageSize を取得します。
     * @return maxPageSize
     */
    public int getMaxPageSize() {
        return maxPageSize__;
    }
    /**
     * <p>maxPageSize をセットします。
     * @param maxPageSize maxPageSize
     */
    public void setMaxPageSize(int maxPageSize) {
        maxPageSize__ = maxPageSize;
    }
    /**
     * <p>pageNum を取得します。
     * @return pageNum
     */
    public int getPageNum() {
        return pageNum__;
    }
    /**
     * <p>pageNum をセットします。
     * @param pageNum pageNum
     */
    public void setPageNum(int pageNum) {
        pageNum__ = pageNum;
    }
    /**
     * <p>shohinDataList を取得します。
     * @return shohinDataList
     */
    public List<NtpShohinModel> getShohinDataList() {
        return shohinDataList__;
    }
    /**
     * <p>shohinDataList をセットします。
     * @param shohinDataList shohinDataList
     */
    public void setShohinDataList(List<NtpShohinModel> shohinDataList) {
        shohinDataList__ = shohinDataList;
    }
    /**
     * <p>nscName を取得します。
     * @return nscName
     */
    public String getNscName() {
        return nscName__;
    }
    /**
     * <p>nscName をセットします。
     * @param nscName nscName
     */
    public void setNscName(String nscName) {
        nscName__ = nscName;
    }
}
