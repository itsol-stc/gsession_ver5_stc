package jp.groupsession.v2.prj.prj150.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] プロジェクト管理 会社情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150CompanyModel extends AbstractModel {

    /** 会社SID */
    private int companySid__ = 0;
    /** 会社拠点SID */
    private int companyBaseSid__ = 0;
    /** 会社名 */
    private String companyName__ = null;

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
