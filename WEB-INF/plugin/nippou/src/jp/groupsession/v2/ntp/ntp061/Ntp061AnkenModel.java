package jp.groupsession.v2.ntp.ntp061;

import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 案件情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp061AnkenModel extends NtpAnkenModel {
    /** 業務SID */
    private int ntp061NgySid__;
    /** 企業コード */
    private String ntp061CompanyCode__;
    /** 企業名 */
    private String ntp061CompanyName__;
    /** 拠点名 */
    private String ntp061BaseName__;
    /** 登録者 */
    private UsrLabelValueBean ntp061TourokuName__;

    /**
     * @return ntp061CompanyCode
     */
    public String getNtp061CompanyCode() {
        return ntp061CompanyCode__;
    }
    /**
     * @param ntp061CompanyCode 設定する ntp061CompanyCode
     */
    public void setNtp061CompanyCode(String ntp061CompanyCode) {
        ntp061CompanyCode__ = ntp061CompanyCode;
    }
    /**
     * @return ntp061BaseName
     */
    public String getNtp061BaseName() {
        return ntp061BaseName__;
    }
    /**
     * @param ntp061BaseName 設定する ntp061BaseName
     */
    public void setNtp061BaseName(String ntp061BaseName) {
        ntp061BaseName__ = ntp061BaseName;
    }
    /**
     * @return ntp061CompanyName
     */
    public String getNtp061CompanyName() {
        return ntp061CompanyName__;
    }
    /**
     * @param ntp061CompanyName 設定する ntp061CompanyName
     */
    public void setNtp061CompanyName(String ntp061CompanyName) {
        ntp061CompanyName__ = ntp061CompanyName;
    }
    /**
     * @return ntp061TourokuName
     */
    public UsrLabelValueBean getNtp061TourokuName() {
        return ntp061TourokuName__;
    }
    /**
     * @param ntp061TourokuName 設定する ntp061TourokuName
     */
    public void setNtp061TourokuName(UsrLabelValueBean ntp061TourokuName) {
        ntp061TourokuName__ = ntp061TourokuName;
    }
    /**
     * @return ntp061NgySid
     */
    public int getNtp061NgySid() {
        return ntp061NgySid__;
    }
    /**
     * @param ntp061NgySid 設定する ntp061NgySid
     */
    public void setNtp061NgySid(int ntp061NgySid) {
        ntp061NgySid__ = ntp061NgySid;
    }

}
