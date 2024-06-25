package jp.groupsession.v2.prj.prj150;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>プロジェクト管理 プロジェクトメンバー情報(表示用)を格納するModelクラス
 * @author JTS
 *
 */
public class Prj150DspModel extends AbstractGsForm {

    /** アドレス帳SID */
    private int adrSid__ = 0;
    /** 会社SID */
    private int companySid__ = 0;
    /** 会社拠点SID */
    private int companyBaseSid__ = 0;
    /** アドレス帳名称 */
    private String adrName__ = null;
    /** メールアドレス */
    private String adrMail__ = null;
    /** 電話番号 */
    private String adrTel__ = null;
    /** 会社名 */
    private String companyName__ = null;
    /** 拠点名 */
    private String companyBaseName__ = null;
    /** 行番号 */
    private int gaibuRowNumber__;
    /** ソート順 */
    private String gaibuSort__;

    /**
     * <p>adrName を取得します。
     * @return adrName
     */
    public String getAdrName() {
        return adrName__;
    }
    /**
     * <p>adrName をセットします。
     * @param adrName adrName
     */
    public void setAdrName(String adrName) {
        adrName__ = adrName;
    }
    /**
     * <p>adrSid を取得します。
     * @return adrSid
     */
    public int getAdrSid() {
        return adrSid__;
    }
    /**
     * <p>adrSid をセットします。
     * @param adrSid adrSid
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
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
     * @return adrMail
     */
    public String getAdrMail() {
        return adrMail__;
    }
    /**
     * @param adrMail 設定する adrMail
     */
    public void setAdrMail(String adrMail) {
        adrMail__ = adrMail;
    }
    /**
     * @return adrTel
     */
    public String getAdrTel() {
        return adrTel__;
    }
    /**
     * @param adrTel 設定する adrTel
     */
    public void setAdrTel(String adrTel) {
        adrTel__ = adrTel;
    }
    /**
     * @return companyName
     */
    public String getCompanyName() {
        return companyName__;
    }
    /**
     * @param companyName 設定する companyName
     */
    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }
    /**
     * <p>gaibuRowNumber を取得します。
     * @return gaibuRowNumber
     */
    public int getGaibuRowNumber() {
        return gaibuRowNumber__;
    }
    /**
     * <p>gaibuRowNumber をセットします。
     * @param gaibuRowNumber gaibuRowNumber
     */
    public void setGaibuRowNumber(int gaibuRowNumber) {
        gaibuRowNumber__ = gaibuRowNumber;
    }
    /**
     * <p>gaibuSort を取得します。
     * @return gaibuSort
     */
    public String getGaibuSort() {
        return gaibuSort__;
    }
    /**
     * <p>gaibuSort をセットします。
     * @param gaibuSort gaibuSort
     */
    public void setGaibuSort(String gaibuSort) {
        gaibuSort__ = gaibuSort;
    }
    /**
     * @return companyBaseName
     */
    public String getCompanyBaseName() {
        return companyBaseName__;
    }
    /**
     * @param companyBaseName 設定する companyBaseName
     */
    public void setCompanyBaseName(String companyBaseName) {
        companyBaseName__ = companyBaseName;
    }
}
