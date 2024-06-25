package jp.groupsession.v2.ntp.ntp220.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;

/**
 * <p>NTP_ANKEN Data Bindding JavaBean
 *
 */
public class Ntp220AnkenModel extends NtpAnkenModel {

    /** CONTENT_SID1 */
    private int cntSid1__;
    /** CONTENT_SID2 */
    private int cntSid2__;
    /** CONTENT_NAME1 */
    private String cntName1__;
    /** CONTENT_NAME2 */
    private String cntName2__;
    /** NGY_SID */
    private int ngySid__;
    /**NGY_NAME */
    private String ngyName__;
    /**プロセス */
    private String ngpName__;
    /**見込み度*/
    private String nanMikomiVal__;
    /**顧客源泉*/
    private String ncnName__;
    /**商品 */
    private List<Ntp220ShohinModel> shohinList__;
    /**担当者 */
    private List<CmnUsrmInfModel> tantoUsrInfList__;
    /** NAH_SID */
    private int nahSid__ = -1;
    /**見積もり日 */
    private String nanMitumoriDateStr__;
    /**受注日 */
    private String nanJutyuDateStr__;
    /**見積もり日 区分 0:指定期間内 1:指定期間外 */
    private int nanMitumoriDateKbn__ = 0;
    /**受注日  0:指定期間内 1:指定期間外*/
    private int nanJutyuDateKbn__ = 0;
    /** 稼働時間 */
    private float kadouHours__ = 0f;
    /** 稼働時間合計 */
    private float totalKadouHours__ = 0f;
    /** 閲覧可能*/
    private String viewable__ = "true";
    /**
     * <p>Default Constructor
     */
    public Ntp220AnkenModel() {
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
     * <p>tantoUsrInfList を取得します。
     * @return tantoUsrInfList
     */
    public List<CmnUsrmInfModel> getTantoUsrInfList() {
        return tantoUsrInfList__;
    }

    /**
     * <p>tantoUsrInfList をセットします。
     * @param tantoUsrInfList tantoUsrInfList
     */
    public void setTantoUsrInfList(List<CmnUsrmInfModel> tantoUsrInfList) {
        tantoUsrInfList__ = tantoUsrInfList;
    }

    /**
     * <p>nahSid を取得します。
     * @return nahSid
     */
    public int getNahSid() {
        return nahSid__;
    }

    /**
     * <p>nahSid をセットします。
     * @param nahSid nahSid
     */
    public void setNahSid(int nahSid) {
        nahSid__ = nahSid;
    }

    /**
     * <p>cntName1 を取得します。
     * @return cntName1
     */
    public String getCntName1() {
        return cntName1__;
    }

    /**
     * <p>cntName1 をセットします。
     * @param cntName1 cntName1
     */
    public void setCntName1(String cntName1) {
        cntName1__ = cntName1;
    }

    /**
     * <p>cntName2 を取得します。
     * @return cntName2
     */
    public String getCntName2() {
        return cntName2__;
    }

    /**
     * <p>cntName2 をセットします。
     * @param cntName2 cntName2
     */
    public void setCntName2(String cntName2) {
        cntName2__ = cntName2;
    }

    /**
     * <p>cntSid1 を取得します。
     * @return cntSid1
     */
    public int getCntSid1() {
        return cntSid1__;
    }

    /**
     * <p>cntSid1 をセットします。
     * @param cntSid1 cntSid1
     */
    public void setCntSid1(int cntSid1) {
        cntSid1__ = cntSid1;
    }

    /**
     * <p>cntSid2 を取得します。
     * @return cntSid2
     */
    public int getCntSid2() {
        return cntSid2__;
    }

    /**
     * <p>cntSid2 をセットします。
     * @param cntSid2 cntSid2
     */
    public void setCntSid2(int cntSid2) {
        cntSid2__ = cntSid2;
    }

    /**
     * <p>nanMitumoriDateStr を取得します。
     * @return nanMitumoriDateStr
     */
    public String getNanMitumoriDateStr() {
        return nanMitumoriDateStr__;
    }

    /**
     * <p>nanMitumoriDateStr をセットします。
     * @param nanMitumoriDateStr nanMitumoriDateStr
     */
    public void setNanMitumoriDateStr(String nanMitumoriDateStr) {
        nanMitumoriDateStr__ = nanMitumoriDateStr;
    }

    /**
     * <p>nanJutyuDateStr を取得します。
     * @return nanJutyuDateStr
     */
    public String getNanJutyuDateStr() {
        return nanJutyuDateStr__;
    }

    /**
     * <p>nanJutyuDateStr をセットします。
     * @param nanJutyuDateStr nanJutyuDateStr
     */
    public void setNanJutyuDateStr(String nanJutyuDateStr) {
        nanJutyuDateStr__ = nanJutyuDateStr;
    }

    /**
     * <p>nanMitumoriDateKbn を取得します。
     * @return nanMitumoriDateKbn
     */
    public int getNanMitumoriDateKbn() {
        return nanMitumoriDateKbn__;
    }

    /**
     * <p>nanMitumoriDateKbn をセットします。
     * @param nanMitumoriDateKbn nanMitumoriDateKbn
     */
    public void setNanMitumoriDateKbn(int nanMitumoriDateKbn) {
        nanMitumoriDateKbn__ = nanMitumoriDateKbn;
    }

    /**
     * <p>nanJutyuDateKbn を取得します。
     * @return nanJutyuDateKbn
     */
    public int getNanJutyuDateKbn() {
        return nanJutyuDateKbn__;
    }

    /**
     * <p>nanJutyuDateKbn をセットします。
     * @param nanJutyuDateKbn nanJutyuDateKbn
     */
    public void setNanJutyuDateKbn(int nanJutyuDateKbn) {
        nanJutyuDateKbn__ = nanJutyuDateKbn;
    }

    /**
     * <p>kadouHours を取得します。
     * @return kadouHours
     */
    public float getKadouHours() {
        return kadouHours__;
    }

    /**
     * <p>kadouHours をセットします。
     * @param kadouHours kadouHours
     */
    public void setKadouHours(float kadouHours) {
        kadouHours__ = kadouHours;
    }

    /**
     * <p>totalKadouHours を取得します。
     * @return totalKadouHours
     */
    public float getTotalKadouHours() {
        return totalKadouHours__;
    }

    /**
     * <p>totalKadouHours をセットします。
     * @param totalKadouHours totalKadouHours
     */
    public void setTotalKadouHours(float totalKadouHours) {
        totalKadouHours__ = totalKadouHours;
    }

    /**
     * <p>viewable を取得します。
     * @return viewable
     */
    public String getViewable() {
        return viewable__;
    }

    /**
     * <p>viewable をセットします。
     * @param viewable viewable
     */
    public void setViewable(String viewable) {
        viewable__ = viewable;
    }
    /**
     * <p>viewable をセットします。
     * @param viewable viewable
     */
    public void setViewable(boolean viewable) {
        if (viewable) {
            viewable__ = "true";
            return;
        }
        viewable__ = "false";
    }
}
