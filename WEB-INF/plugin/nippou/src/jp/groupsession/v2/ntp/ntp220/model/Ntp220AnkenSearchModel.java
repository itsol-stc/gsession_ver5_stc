package jp.groupsession.v2.ntp.ntp220.model;
import java.util.List;

import jp.groupsession.v2.ntp.model.NtpAnkenModel;

/**
 * <p>NTP_ANKEN Data Bindding JavaBean
 *
 */
public class Ntp220AnkenSearchModel extends NtpAnkenModel {

    /**検索パラメータ false:なし true:あり*/
    private boolean searchPramFlg__ = false;

    /** 検索項目 */
    private String searchContent__;

    /** ACO_NAME */
    private String acoName__;
    /** ABA_NAME */
    private String abaName__;
    /** NGY_SID */
    private int ngySid__;
    /**NGY_NAME */
    private String ngyName__;
    /**プロセス */
    private String ngpName__;
    /**見込み度(検索用)*/
    private int nanMikomiSearchVal__ = -1;
    /**見込み度*/
    private String nanMikomiVal__;
    /**顧客源泉*/
    private String ncnName__;
    /**shohinList */
    private List<Ntp220ShohinModel> shohinList__;
    /**案件SID */
    private List<Integer> ankenSidList__;
    /**業種SID */
    private int gyoushuSid__ = -1;
    /**商品カテゴリSID */
    private int shohinCatSid__ = -1;

    /**
     * <p>Default Constructor
     */
    public Ntp220AnkenSearchModel() {
    }

    /**
     * <p>acoName を取得します。
     * @return acoName
     */
    public String getAcoName() {
        return acoName__;
    }

    /**
     * <p>acoName をセットします。
     * @param acoName acoName
     */
    public void setAcoName(String acoName) {
        acoName__ = acoName;
    }

    /**
     * <p>abaName を取得します。
     * @return abaName
     */
    public String getAbaName() {
        return abaName__;
    }

    /**
     * <p>abaName をセットします。
     * @param abaName abaName
     */
    public void setAbaName(String abaName) {
        abaName__ = abaName;
    }

    /**
     * <p>shohinList を取得します。
     * @return shohinList
     */
    public List<Ntp220ShohinModel> getShohinList() {
        return shohinList__;
    }

    /**
     * <p>shohinList をセットします。
     * @param shohinList shohinList
     */
    public void setShohinList(List<Ntp220ShohinModel> shohinList) {
        shohinList__ = shohinList;
    }

    /**
     * <p>ngySid を取得します。
     * @return ngySid
     */
    public int getNgySid() {
        return ngySid__;
    }

    /**
     * <p>ngySid をセットします。
     * @param ngySid ngySid
     */
    public void setNgySid(int ngySid) {
        ngySid__ = ngySid;
    }

    /**
     * <p>ngyName を取得します。
     * @return ngyName
     */
    public String getNgyName() {
        return ngyName__;
    }

    /**
     * <p>ngyName をセットします。
     * @param ngyName ngyName
     */
    public void setNgyName(String ngyName) {
        ngyName__ = ngyName;
    }

    /**
     * <p>ngpName を取得します。
     * @return ngpName
     */
    public String getNgpName() {
        return ngpName__;
    }

    /**
     * <p>ngpName をセットします。
     * @param ngpName ngpName
     */
    public void setNgpName(String ngpName) {
        ngpName__ = ngpName;
    }

    /**
     * <p>nanMikomiVal を取得します。
     * @return nanMikomiVal
     */
    public String getNanMikomiVal() {
        return nanMikomiVal__;
    }

    /**
     * <p>nanMikomiVal をセットします。
     * @param nanMikomiVal nanMikomiVal
     */
    public void setNanMikomiVal(String nanMikomiVal) {
        nanMikomiVal__ = nanMikomiVal;
    }

    /**
     * <p>ncnName を取得します。
     * @return ncnName
     */
    public String getNcnName() {
        return ncnName__;
    }

    /**
     * <p>ncnName をセットします。
     * @param ncnName ncnName
     */
    public void setNcnName(String ncnName) {
        ncnName__ = ncnName;
    }

    /**
     * <p>searchContent を取得します。
     * @return searchContent
     */
    public String getSearchContent() {
        return searchContent__;
    }

    /**
     * <p>searchContent をセットします。
     * @param searchContent searchContent
     */
    public void setSearchContent(String searchContent) {
        searchContent__ = searchContent;
    }

    /**
     * <p>ankenSidList を取得します。
     * @return ankenSidList
     */
    public List<Integer> getAnkenSidList() {
        return ankenSidList__;
    }

    /**
     * <p>ankenSidList をセットします。
     * @param ankenSidList ankenSidList
     */
    public void setAnkenSidList(List<Integer> ankenSidList) {
        ankenSidList__ = ankenSidList;
    }

    /**
     * <p>searchPramFlg を取得します。
     * @return searchPramFlg
     */
    public boolean isSearchPramFlg() {
        return searchPramFlg__;
    }

    /**
     * <p>searchPramFlg をセットします。
     * @param searchPramFlg searchPramFlg
     */
    public void setSearchPramFlg(boolean searchPramFlg) {
        searchPramFlg__ = searchPramFlg;
    }

    /**
     * <p>nanMikomiSearchVal を取得します。
     * @return nanMikomiSearchVal
     */
    public int getNanMikomiSearchVal() {
        return nanMikomiSearchVal__;
    }

    /**
     * <p>nanMikomiSearchVal をセットします。
     * @param nanMikomiSearchVal nanMikomiSearchVal
     */
    public void setNanMikomiSearchVal(int nanMikomiSearchVal) {
        nanMikomiSearchVal__ = nanMikomiSearchVal;
    }

    /**
     * <p>gyoushuSid を取得します。
     * @return gyoushuSid
     */
    public int getGyoushuSid() {
        return gyoushuSid__;
    }

    /**
     * <p>gyoushuSid をセットします。
     * @param gyoushuSid gyoushuSid
     */
    public void setGyoushuSid(int gyoushuSid) {
        gyoushuSid__ = gyoushuSid;
    }

    /**
     * <p>shohinCatSid を取得します。
     * @return shohinCatSid
     */
    public int getShohinCatSid() {
        return shohinCatSid__;
    }

    /**
     * <p>shohinCatSid をセットします。
     * @param shohinCatSid shohinCatSid
     */
    public void setShohinCatSid(int shohinCatSid) {
        shohinCatSid__ = shohinCatSid;
    }
}
