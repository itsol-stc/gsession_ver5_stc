package jp.groupsession.v2.ntp.ntp200;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;

/**
 * <br>[機  能] 日報 案件検索画面の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp200SearchModel extends NtpAnkenModel {
    /** 企業コード */
    private String ntp200AcoCode__ = null;
    /** 企業名 */
    private String ntp200AcoName__ = null;
    /** 企業名カナ */
    private String ntp200AcoNameKana__ = null;
    /** 拠点名 */
    private String ntp200AbaName__ = null;
    /** カテゴリSID */
    private int ntp200ShohinCategory__ = -1;
    /** 商品名 */
    private String ntp200ShohinName__ = null;
    /** 案件登録 開始年月日 */
    private UDate ntp200FrDate__ = null;
    /** 案件登録 終了年月日 */
    private UDate ntp200ToDate__ = null;
    /** 状態 */
    private int ntp200State__ = -1;
    /** 見込度 */
    private String[] ntp200Mikomi__ = null;
    /** 商談結果 */
    private int ntp200Syodan__ = -1;

    /** 見積金額区分 */
    private int nhnKinMitumoriKbn__ = Ntp200Biz.PRICE_MORE;
    /** 受注金額区分 */
    private int nhnKinJutyuKbn__ = Ntp200Biz.PRICE_MORE;
    /** ソートキー１ */
    private int sortKey1__ = GSConstNippou.ANKEN_SEARCH_SORT_KOUSHIN;
    /** オーダキー１ */
    private int orderKey1__ = GSConstNippou.ORDER_KEY_DESC;
    /** ソートキー２ */
    private int sortKey2__ = -1;
    /** オーダキー２ */
    private int orderKey2__ = GSConstNippou.ORDER_KEY_DESC;

    /** 管理者権限検索  */
    private boolean adminSearch__ = false;
    /** 検索者ユーザSID*/
    private int sessionUsrSid__ = -1;
    /** 権限区分*/
    private int permitKbn__ = GSConst.SP_AUTH_VIEWONLY;

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
     * @return ntp200AbaName
     */
    public String getNtp200AbaName() {
        return ntp200AbaName__;
    }
    /**
     * @param ntp200AbaName 設定する ntp200AbaName
     */
    public void setNtp200AbaName(String ntp200AbaName) {
        ntp200AbaName__ = ntp200AbaName;
    }
    /**
     * @return ntp200AcoCode
     */
    public String getNtp200AcoCode() {
        return ntp200AcoCode__;
    }
    /**
     * @param ntp200AcoCode 設定する ntp200AcoCode
     */
    public void setNtp200AcoCode(String ntp200AcoCode) {
        ntp200AcoCode__ = ntp200AcoCode;
    }
    /**
     * @return ntp200AcoName
     */
    public String getNtp200AcoName() {
        return ntp200AcoName__;
    }
    /**
     * @param ntp200AcoName 設定する ntp200AcoName
     */
    public void setNtp200AcoName(String ntp200AcoName) {
        ntp200AcoName__ = ntp200AcoName;
    }
    /**
     * @return ntp200AcoNameKana
     */
    public String getNtp200AcoNameKana() {
        return ntp200AcoNameKana__;
    }
    /**
     * @param ntp200AcoNameKana 設定する ntp200AcoNameKana
     */
    public void setNtp200AcoNameKana(String ntp200AcoNameKana) {
        ntp200AcoNameKana__ = ntp200AcoNameKana;
    }
    /**
     * @return ntp200FrDate
     */
    public UDate getNtp200FrDate() {
        return ntp200FrDate__;
    }
    /**
     * @param ntp200FrDate 設定する ntp200FrDate
     */
    public void setNtp200FrDate(UDate ntp200FrDate) {
        ntp200FrDate__ = ntp200FrDate;
    }
    /**
     * @return ntp200ShohinName
     */
    public String getNtp200ShohinName() {
        return ntp200ShohinName__;
    }
    /**
     * @param ntp200ShohinName 設定する ntp200ShohinName
     */
    public void setNtp200ShohinName(String ntp200ShohinName) {
        ntp200ShohinName__ = ntp200ShohinName;
    }
    /**
     * @return ntp200ToDate
     */
    public UDate getNtp200ToDate() {
        return ntp200ToDate__;
    }
    /**
     * @param ntp200ToDate 設定する ntp200ToDate
     */
    public void setNtp200ToDate(UDate ntp200ToDate) {
        ntp200ToDate__ = ntp200ToDate;
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
     * @return ntp200Mikomi
     */
    public String[] getNtp200Mikomi() {
        return ntp200Mikomi__;
    }
    /**
     * @param ntp200Mikomi 設定する ntp200Mikomi
     */
    public void setNtp200Mikomi(String[] ntp200Mikomi) {
        ntp200Mikomi__ = ntp200Mikomi;
    }

    /**
     * <p>ntp200State を取得します。
     * @return ntp200State
     */
    public int getNtp200State() {
        return ntp200State__;
    }
    /**
     * <p>ntp200State をセットします。
     * @param ntp200State ntp200State
     */
    public void setNtp200State(int ntp200State) {
        ntp200State__ = ntp200State;
    }
    /**
     * <p>ntp200Syodan を取得します。
     * @return ntp200Syodan
     */
    public int getNtp200Syodan() {
        return ntp200Syodan__;
    }
    /**
     * <p>ntp200Syodan をセットします。
     * @param ntp200Syodan ntp200Syodan
     */
    public void setNtp200Syodan(int ntp200Syodan) {
        ntp200Syodan__ = ntp200Syodan;
    }
    /**
     * <p>ntp200ShohinCategory を取得します。
     * @return ntp200ShohinCategory
     */
    public int getNtp200ShohinCategory() {
        return ntp200ShohinCategory__;
    }
    /**
     * <p>ntp200ShohinCategory をセットします。
     * @param ntp200ShohinCategory ntp200ShohinCategory
     */
    public void setNtp200ShohinCategory(int ntp200ShohinCategory) {
        ntp200ShohinCategory__ = ntp200ShohinCategory;
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
     * <p>permitKbn を取得します。
     * @return permitKbn
     */
    public int getPermitKbn() {
        return permitKbn__;
    }
    /**
     * <p>permitKbn をセットします。
     * @param permitKbn permitKbn
     */
    public void setPermitKbn(int permitKbn) {
        permitKbn__ = permitKbn;
    }
}
