package jp.groupsession.v2.api.adress.companybase.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
/**
 *
 * <br>[機  能] WEB API アドレス帳 会社拠点一覧取得情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCompanyBaseModel {
    /** 企業モデル*/
    private AdrCompanyModel aco__;
    /** 企業拠点モデル*/
    private AdrCompanyBaseModel aba__;
    /** 都道府県名*/
    private String tdfkName__;

    /**
     * コンストラクタ
     */
    public ApiCompanyBaseModel() {
        super();
        aco__ = new AdrCompanyModel();
        aba__ = new AdrCompanyBaseModel();
    }
    /**
     * @return acoSid
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoSid()
     */
    public int getAcoSid() {
        return aco__.getAcoSid();
    }
    /**
     * @param acoSid ACO_SID value
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoSid(int)
     */
    public void setAcoSid(int acoSid) {
        aco__.setAcoSid(acoSid);
    }
    /**
     * @return acoCode
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoCode()
     */
    public String getAcoCode() {
        return aco__.getAcoCode();
    }
    /**
     * @param acoCode acoCode
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoCode(java.lang.String)
     */
    public void setAcoCode(String acoCode) {
        aco__.setAcoCode(acoCode);
    }
    /**
     * @return acoName
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoName()
     */
    public String getAcoName() {
        return aco__.getAcoName();
    }
    /**
     * @param acoName acoName
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoName(java.lang.String)
     */
    public void setAcoName(String acoName) {
        aco__.setAcoName(acoName);
    }
    /**
     * @return acoNameKn
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoNameKn()
     */
    public String getAcoNameKn() {
        return aco__.getAcoNameKn();
    }
    /**
     * @param acoNameKn acoNameKn
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoNameKn(java.lang.String)
     */
    public void setAcoNameKn(String acoNameKn) {
        aco__.setAcoNameKn(acoNameKn);
    }
    /**
     * @return acoUrl
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoUrl()
     */
    public String getAcoUrl() {
        return aco__.getAcoUrl();
    }
    /**
     * @param acoUrl acoUrl
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoUrl(java.lang.String)
     */
    public void setAcoUrl(String acoUrl) {
        aco__.setAcoUrl(acoUrl);
    }
    /**
     * @return acoBiko
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoBiko()
     */
    public String getAcoBiko() {
        return aco__.getAcoBiko();
    }
    /**
     * @param acoBiko acoBiko
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoBiko(java.lang.String)
     */
    public void setAcoBiko(String acoBiko) {
        aco__.setAcoBiko(acoBiko);
    }
    /**
     * @return acoAuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoAuid()
     */
    public int getAcoAuid() {
        return aco__.getAcoAuid();
    }
    /**
     * @param acoAuid acoAuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoAuid(int)
     */
    public void setAcoAuid(int acoAuid) {
        aco__.setAcoAuid(acoAuid);
    }
    /**
     * @return acoAdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoAdate()
     */
    public UDate getAcoAdate() {
        return aco__.getAcoAdate();
    }
    /**
     * @param acoAdate acoAdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoAdate(jp.co.sjts.util.date.UDate)
     */
    public void setAcoAdate(UDate acoAdate) {
        aco__.setAcoAdate(acoAdate);
    }
    /**
     * @return acoEuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoEuid()
     */
    public int getAcoEuid() {
        return aco__.getAcoEuid();
    }
    /**
     * @param acoEuid acoEuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoEuid(int)
     */
    public void setAcoEuid(int acoEuid) {
        aco__.setAcoEuid(acoEuid);
    }
    /**
     * @return acoEdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoEdate()
     */
    public UDate getAcoEdate() {
        return aco__.getAcoEdate();
    }
    /**
     * @param acoEdate acoEdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoEdate(jp.co.sjts.util.date.UDate)
     */
    public void setAcoEdate(UDate acoEdate) {
        aco__.setAcoEdate(acoEdate);
    }
    /**
     * @return acoSini
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#getAcoSini()
     */
    public String getAcoSini() {
        return aco__.getAcoSini();
    }
    /**
     * @param acoSini acoSini
     * @see jp.groupsession.v2.adr.model.AdrCompanyModel#setAcoSini(java.lang.String)
     */
    public void setAcoSini(String acoSini) {
        aco__.setAcoSini(acoSini);
    }
    /**
     * @return abaSid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaSid()
     */
    public int getAbaSid() {
        return aba__.getAbaSid();
    }
    /**
     * @param abaSid abaSid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaSid(int)
     */
    public void setAbaSid(int abaSid) {
        aba__.setAbaSid(abaSid);
    }
    /**
     * @return abaType
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaType()
     */
    public int getAbaType() {
        return aba__.getAbaType();
    }
    /**
     * @param abaType abaType
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaType(int)
     */
    public void setAbaType(int abaType) {
        aba__.setAbaType(abaType);
    }
    /**
     * @return abaName
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaName()
     */
    public String getAbaName() {
        return aba__.getAbaName();
    }
    /**
     * @param abaName abaName
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaName(java.lang.String)
     */
    public void setAbaName(String abaName) {
        aba__.setAbaName(abaName);
    }
    /**
     * @return abaPostno1
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaPostno1()
     */
    public String getAbaPostno1() {
        return aba__.getAbaPostno1();
    }
    /**
     * @param abaPostno1 abaPostno1
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaPostno1(java.lang.String)
     */
    public void setAbaPostno1(String abaPostno1) {
        aba__.setAbaPostno1(abaPostno1);
    }
    /**
     * @return abaPostno2
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaPostno2()
     */
    public String getAbaPostno2() {
        return aba__.getAbaPostno2();
    }
    /**
     * @param abaPostno2 abaPostno2
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaPostno2(java.lang.String)
     */
    public void setAbaPostno2(String abaPostno2) {
        aba__.setAbaPostno2(abaPostno2);
    }
    /**
     * @return tdfSid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getTdfSid()
     */
    public int getTdfSid() {
        return aba__.getTdfSid();
    }
    /**
     * @param tdfSid tdfSid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setTdfSid(int)
     */
    public void setTdfSid(int tdfSid) {
        aba__.setTdfSid(tdfSid);
    }
    /**
     * @return abaAddr1
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaAddr1()
     */
    public String getAbaAddr1() {
        return aba__.getAbaAddr1();
    }
    /**
     * @param abaAddr1 abaAddr1
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaAddr1(java.lang.String)
     */
    public void setAbaAddr1(String abaAddr1) {
        aba__.setAbaAddr1(abaAddr1);
    }
    /**
     * @return abaAddr2
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaAddr2()
     */
    public String getAbaAddr2() {
        return aba__.getAbaAddr2();
    }
    /**
     * @param abaAddr2 abaAddr2
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaAddr2(java.lang.String)
     */
    public void setAbaAddr2(String abaAddr2) {
        aba__.setAbaAddr2(abaAddr2);
    }
    /**
     * @return abaBiko
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaBiko()
     */
    public String getAbaBiko() {
        return aba__.getAbaBiko();
    }
    /**
     * @param abaBiko abaBiko
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaBiko(java.lang.String)
     */
    public void setAbaBiko(String abaBiko) {
        aba__.setAbaBiko(abaBiko);
    }
    /**
     * @return abaAuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaAuid()
     */
    public int getAbaAuid() {
        return aba__.getAbaAuid();
    }
    /**
     * @param abaAuid abaAuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaAuid(int)
     */
    public void setAbaAuid(int abaAuid) {
        aba__.setAbaAuid(abaAuid);
    }
    /**
     * @return abaAdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaAdate()
     */
    public UDate getAbaAdate() {
        return aba__.getAbaAdate();
    }
    /**
     * @param abaAdate abaAdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaAdate(jp.co.sjts.util.date.UDate)
     */
    public void setAbaAdate(UDate abaAdate) {
        aba__.setAbaAdate(abaAdate);
    }
    /**
     * @return abaEuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaEuid()
     */
    public int getAbaEuid() {
        return aba__.getAbaEuid();
    }
    /**
     * @param abaEuid abaEuid
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaEuid(int)
     */
    public void setAbaEuid(int abaEuid) {
        aba__.setAbaEuid(abaEuid);
    }
    /**
     * @return abaEdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#getAbaEdate()
     */
    public UDate getAbaEdate() {
        return aba__.getAbaEdate();
    }
    /**
     * @param abaEdate abaEdate
     * @see jp.groupsession.v2.adr.model.AdrCompanyBaseModel#setAbaEdate(jp.co.sjts.util.date.UDate)
     */
    public void setAbaEdate(UDate abaEdate) {
        aba__.setAbaEdate(abaEdate);
    }
    /**
     * <p>aco をセットします。
     * @param aco aco
     */
    public void setAco(AdrCompanyModel aco) {
        this.aco__ = aco;
    }
    /**
     * <p>aba をセットします。
     * @param aba aba
     */
    public void setAba(AdrCompanyBaseModel aba) {
        this.aba__ = aba;
    }
    /**
     * <p>tdfkName を取得します。
     * @return tdfkName
     */
    public String getTdfkName() {
        return tdfkName__;
    }
    /**
     * <p>tdfkName をセットします。
     * @param tdfkName tdfkName
     */
    public void setTdfkName(String tdfkName) {
        tdfkName__ = tdfkName;
    }


}
