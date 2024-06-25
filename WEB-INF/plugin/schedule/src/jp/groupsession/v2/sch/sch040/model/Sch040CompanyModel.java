package jp.groupsession.v2.sch.sch040.model;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュールで使用する会社情報(アドレス帳)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040CompanyModel extends AbstractModel {

    /** 会社SID */
    private int companySid__ = 0;
    /** 会社拠点SID */
    private int companyBaseSid__ = 0;
    /** 会社名 */
    private String companyName__ = null;
    /** 会社名(検索用) */
    private String companyNameSearch__ = null;
    /** 会社住所 */
    private String companyAddress__ = null;

    /** アドレス帳情報一覧 */
    private List<Sch040AddressModel> addressDataList__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Sch040CompanyModel() {
        addressDataList__ = new ArrayList<Sch040AddressModel>();
    }

    /**
     * <p>companyAddress を取得します。
     * @return companyAddress
     */
    public String getCompanyAddress() {
        return companyAddress__;
    }

    /**
     * <p>companyAddress をセットします。
     * @param companyAddress companyAddress
     */
    public void setCompanyAddress(String companyAddress) {
        companyAddress__ = companyAddress;
    }

    /**
     * <p>companyBaseSid を取得します。
     * @return companyBaseSid
     */
    public int getCompanyBaseSid() {
        return companyBaseSid__;
    }

    /**
     * <p>companyBaseSid をセットします。
     * @param companyBaseSid companyBaseSid
     */
    public void setCompanyBaseSid(int companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
    }

    /**
     * <p>companyName を取得します。
     * @return companyName
     */
    public String getCompanyName() {
        return companyName__;
    }

    /**
     * <p>companyName をセットします。
     * @param companyName companyName
     */
    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }

    /**
     * <p>companyNameSearch を取得します。
     * @return companyNameSearch
     */
    public String getCompanyNameSearch() {
        return companyNameSearch__;
    }

    /**
     * <p>companyNameSearch をセットします。
     * @param companyNameSearch companyNameSearch
     */
    public void setCompanyNameSearch(String companyNameSearch) {
        companyNameSearch__ = companyNameSearch;
    }

    /**
     * <p>companySid を取得します。
     * @return companySid
     */
    public int getCompanySid() {
        return companySid__;
    }

    /**
     * <p>companySid をセットします。
     * @param companySid companySid
     */
    public void setCompanySid(int companySid) {
        companySid__ = companySid;
    }

    /**
     * <p>addressDataList を取得します。
     * @return addressDataList
     */
    public List<Sch040AddressModel> getAddressDataList() {
        return addressDataList__;
    }

    /**
     * <p>addressDataList をセットします。
     * @param addressDataList addressDataList
     */
    public void setAddressDataList(List<Sch040AddressModel> addressDataList) {
        addressDataList__ = addressDataList;
    }
}
