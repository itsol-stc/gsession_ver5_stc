package jp.groupsession.v2.sch.sch040.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュールで使用するアドレス帳情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040AddressModel extends AbstractModel {

    /** アドレス帳SID */
    private int adrSid__ = 0;
    /** 会社SID */
    private int companySid__ = 0;
    /** 会社拠点SID */
    private int companyBaseSid__ = 0;
    /** アドレス帳名称 */
    private String adrName__ = null;

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

}
