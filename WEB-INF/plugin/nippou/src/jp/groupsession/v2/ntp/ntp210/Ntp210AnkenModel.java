package jp.groupsession.v2.ntp.ntp210;

import jp.groupsession.v2.ntp.model.NtpAnkenModel;

/**
 * <br>[機  能] 案件情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp210AnkenModel extends NtpAnkenModel {
    /** 業務SID */
    private int ntp210NgySid__;
    /** 企業コード */
    private String ntp210CompanyCode__;
    /** 企業名 */
    private String ntp210CompanyName__;
    /** 拠点名 */
    private String ntp210BaseName__;
    /** 登録者名 */
    private String ntp210TourokuName__;
    /**
     * <p>ntp210NgySid を取得します。
     * @return ntp210NgySid
     */
    public int getNtp210NgySid() {
        return ntp210NgySid__;
    }
    /**
     * <p>ntp210NgySid をセットします。
     * @param ntp210NgySid ntp210NgySid
     */
    public void setNtp210NgySid(int ntp210NgySid) {
        ntp210NgySid__ = ntp210NgySid;
    }
    /**
     * <p>ntp210CompanyCode を取得します。
     * @return ntp210CompanyCode
     */
    public String getNtp210CompanyCode() {
        return ntp210CompanyCode__;
    }
    /**
     * <p>ntp210CompanyCode をセットします。
     * @param ntp210CompanyCode ntp210CompanyCode
     */
    public void setNtp210CompanyCode(String ntp210CompanyCode) {
        ntp210CompanyCode__ = ntp210CompanyCode;
    }
    /**
     * <p>ntp210CompanyName を取得します。
     * @return ntp210CompanyName
     */
    public String getNtp210CompanyName() {
        return ntp210CompanyName__;
    }
    /**
     * <p>ntp210CompanyName をセットします。
     * @param ntp210CompanyName ntp210CompanyName
     */
    public void setNtp210CompanyName(String ntp210CompanyName) {
        ntp210CompanyName__ = ntp210CompanyName;
    }
    /**
     * <p>ntp210BaseName を取得します。
     * @return ntp210BaseName
     */
    public String getNtp210BaseName() {
        return ntp210BaseName__;
    }
    /**
     * <p>ntp210BaseName をセットします。
     * @param ntp210BaseName ntp210BaseName
     */
    public void setNtp210BaseName(String ntp210BaseName) {
        ntp210BaseName__ = ntp210BaseName;
    }
    /**
     * <p>ntp210TourokuName を取得します。
     * @return ntp210TourokuName
     */
    public String getNtp210TourokuName() {
        return ntp210TourokuName__;
    }
    /**
     * <p>ntp210TourokuName をセットします。
     * @param ntp210TourokuName ntp210TourokuName
     */
    public void setNtp210TourokuName(String ntp210TourokuName) {
        ntp210TourokuName__ = ntp210TourokuName;
    }
}
