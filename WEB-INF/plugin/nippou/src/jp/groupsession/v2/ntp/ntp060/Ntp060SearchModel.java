package jp.groupsession.v2.ntp.ntp060;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;

/**
 * <br>[機  能] 案件情報の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060SearchModel extends NtpAnkenModel {
    /** 企業コード */
    private String ntp060AcoCode__ = null;
    /** 企業名 */
    private String ntp060AcoName__ = null;
    /** 企業名カナ */
    private String ntp060AcoNameKana__ = null;
    /** 拠点名 */
    private String ntp060AbaName__ = null;
    /** 商品カテゴリ */
    private int ntp060ShohinCategory__ = -1;
    /** 商品名 */
    private String ntp060ShohinName__ = null;
    /** 案件登録 開始年月日 */
    private UDate ntp060FrDate__ = null;
    /** 案件登録 終了年月日 */
    private UDate ntp060ToDate__ = null;
    /** 見込度 */
    private String[] ntp060Mikomi__ = null;
    /** 商談結果 */
    private String[] ntp060Syodan__ = null;
    /** 状態  */
    private int ntp060State__ = Ntp060Biz.STATE_ALL;

    /** 見積金額区分 */
    private int nhnKinMitumoriKbn__ = Ntp060Biz.PRICE_MORE;
    /** 受注金額区分 */
    private int nhnKinJutyuKbn__ = Ntp060Biz.PRICE_MORE;
    /** ソートキー１ */
    private int sortKey1__ = -1;
    /** オーダキー１ */
    private int orderKey1__ = GSConstNippou.ORDER_KEY_ASC;
    /** ソートキー２ */
    private int sortKey2__ = -1;
    /** オーダキー２ */
    private int orderKey2__ = GSConstNippou.ORDER_KEY_ASC;

    /** 管理者権限検索  */
    private boolean adminSearch__ = false;
    /** 検索者ユーザSID*/
    private int sessionUsrSid__ = -1;

    /**
     * @return nhnKinJutyuKbn
     */
    public int getNhnKinJutyuKbn() {
        return nhnKinJutyuKbn__;
    }
    /**
     * @param nhnKinJutyuKbn 設定する nhnKinJutyuKbn
     */
    public void setNhnKinJutyuKbn(int nhnKinJutyuKbn) {
        nhnKinJutyuKbn__ = nhnKinJutyuKbn;
    }
    /**
     * @return nhnKinMitumoriKbn
     */
    public int getNhnKinMitumoriKbn() {
        return nhnKinMitumoriKbn__;
    }
    /**
     * @param nhnKinMitumoriKbn 設定する nhnKinMitumoriKbn
     */
    public void setNhnKinMitumoriKbn(int nhnKinMitumoriKbn) {
        nhnKinMitumoriKbn__ = nhnKinMitumoriKbn;
    }
    /**
     * @return ntp060AbaName
     */
    public String getNtp060AbaName() {
        return ntp060AbaName__;
    }
    /**
     * @param ntp060AbaName 設定する ntp060AbaName
     */
    public void setNtp060AbaName(String ntp060AbaName) {
        ntp060AbaName__ = ntp060AbaName;
    }
    /**
     * @return ntp060AcoCode
     */
    public String getNtp060AcoCode() {
        return ntp060AcoCode__;
    }
    /**
     * @param ntp060AcoCode 設定する ntp060AcoCode
     */
    public void setNtp060AcoCode(String ntp060AcoCode) {
        ntp060AcoCode__ = ntp060AcoCode;
    }
    /**
     * @return ntp060AcoName
     */
    public String getNtp060AcoName() {
        return ntp060AcoName__;
    }
    /**
     * @param ntp060AcoName 設定する ntp060AcoName
     */
    public void setNtp060AcoName(String ntp060AcoName) {
        ntp060AcoName__ = ntp060AcoName;
    }
    /**
     * @return ntp060AcoNameKana
     */
    public String getNtp060AcoNameKana() {
        return ntp060AcoNameKana__;
    }
    /**
     * @param ntp060AcoNameKana 設定する ntp060AcoNameKana
     */
    public void setNtp060AcoNameKana(String ntp060AcoNameKana) {
        ntp060AcoNameKana__ = ntp060AcoNameKana;
    }
    /**
     * @return ntp060FrDate
     */
    public UDate getNtp060FrDate() {
        return ntp060FrDate__;
    }
    /**
     * @param ntp060FrDate 設定する ntp060FrDate
     */
    public void setNtp060FrDate(UDate ntp060FrDate) {
        ntp060FrDate__ = ntp060FrDate;
    }
    /**
     * @return ntp060ShohinName
     */
    public String getNtp060ShohinName() {
        return ntp060ShohinName__;
    }
    /**
     * @param ntp060ShohinName 設定する ntp060ShohinName
     */
    public void setNtp060ShohinName(String ntp060ShohinName) {
        ntp060ShohinName__ = ntp060ShohinName;
    }
    /**
     * @return ntp060ToDate
     */
    public UDate getNtp060ToDate() {
        return ntp060ToDate__;
    }
    /**
     * @param ntp060ToDate 設定する ntp060ToDate
     */
    public void setNtp060ToDate(UDate ntp060ToDate) {
        ntp060ToDate__ = ntp060ToDate;
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
    /**
     * @return ntp060Mikomi
     */
    public String[] getNtp060Mikomi() {
        return ntp060Mikomi__;
    }
    /**
     * @param ntp060Mikomi 設定する ntp060Mikomi
     */
    public void setNtp060Mikomi(String[] ntp060Mikomi) {
        ntp060Mikomi__ = ntp060Mikomi;
    }
    /**
     * @return ntp060Syodan
     */
    public String[] getNtp060Syodan() {
        return ntp060Syodan__;
    }
    /**
     * @param ntp060Syodan 設定する ntp060Syodan
     */
    public void setNtp060Syodan(String[] ntp060Syodan) {
        ntp060Syodan__ = ntp060Syodan;
    }
    /**
     * <p>ntp060State を取得します。
     * @return ntp060State
     */
    public int getNtp060State() {
        return ntp060State__;
    }
    /**
     * <p>ntp060State をセットします。
     * @param ntp060State ntp060State
     */
    public void setNtp060State(int ntp060State) {
        ntp060State__ = ntp060State;
    }
    /**
     * <p>ntp060ShohinCategory を取得します。
     * @return ntp060ShohinCategory
     */
    public int getNtp060ShohinCategory() {
        return ntp060ShohinCategory__;
    }
    /**
     * <p>ntp060ShohinCategory をセットします。
     * @param ntp060ShohinCategory ntp060ShohinCategory
     */
    public void setNtp060ShohinCategory(int ntp060ShohinCategory) {
        ntp060ShohinCategory__ = ntp060ShohinCategory;
    }
    /**
     * <p>adminSearch を取得します。
     * @return adminSearch
     */
    public boolean isAdminSearch() {
        return adminSearch__;
    }
    /**
     * <p>adminSearch をセットします。
     * @param adminSearch adminSearch
     */
    public void setAdminSearch(boolean adminSearch) {
        adminSearch__ = adminSearch;
    }
    /**
     * <p>sessionUsrSid を取得します。
     * @return sessionUsrSid
     */
    public int getSessionUsrSid() {
        return sessionUsrSid__;
    }
    /**
     * <p>sessionUsrSid をセットします。
     * @param sessionUsrSid sessionUsrSid
     */
    public void setSessionUsrSid(int sessionUsrSid) {
        sessionUsrSid__ = sessionUsrSid;
    }
}
