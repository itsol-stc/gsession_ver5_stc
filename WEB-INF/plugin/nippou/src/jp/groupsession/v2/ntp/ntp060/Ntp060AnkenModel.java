package jp.groupsession.v2.ntp.ntp060;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <br>[機  能] 日報案件情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060AnkenModel extends NtpAnkenModel {
    /** 企業名 */
    private String ntp060CompanyName__;
    /** 企業名 */
    private String ntp060CompanyCode__;
    /** 拠点名 */
    private String ntp060BaseName__;
    /** 商品名リスト */
    private List<NtpShohinModel> ntp060ShohinList__;
    /** 見積金額表示用 */
    private String ntp060KinMitumori__;
    /** 提出日付 */
    private String ntp060MitumoriDate__;
    /** 受注金額表示用 */
    private String ntp060KinJutyu__;
    /** 受注日付 */
    private String ntp060JutyuDate__;
    /** 業務コード */
    private String ntp060GyomuCode__;
    /** 業務名 */
    private String ntp060GyomuName__;
    /** プロセスコード */
    private String ntp060ProcessCode__;
    /** プロセス名 */
    private String ntp060ProcessName__;
    /** 顧客源泉コード */
    private String ntp060ContactCode__;
    /** 顧客源泉名 */
    private String ntp060ContactName__;
    /** 登録日付 */
    private String ntp060Date__;
    /** 更新日付 */
    private String ntp060Edate__;


    /**
     * @return ntp060Date
     */
    public String getNtp060Date() {
        return ntp060Date__;
    }
    /**
     * @param ntp060Date 設定する ntp060Date
     */
    public void setNtp060Date(String ntp060Date) {
        ntp060Date__ = ntp060Date;
    }
    /**
     * @return ntp060BaseName
     */
    public String getNtp060BaseName() {
        return ntp060BaseName__;
    }
    /**
     * @param ntp060BaseName 設定する ntp060BaseName
     */
    public void setNtp060BaseName(String ntp060BaseName) {
        ntp060BaseName__ = ntp060BaseName;
    }
    /**
     * @return ntp060CompanyName
     */
    public String getNtp060CompanyName() {
        return ntp060CompanyName__;
    }
    /**
     * @param ntp060CompanyName 設定する ntp060CompanyName
     */
    public void setNtp060CompanyName(String ntp060CompanyName) {
        ntp060CompanyName__ = ntp060CompanyName;
    }
    /**
     * @return ntp060ContactName
     */
    public String getNtp060ContactName() {
        return ntp060ContactName__;
    }
    /**
     * @param ntp060ContactName 設定する ntp060ContactName
     */
    public void setNtp060ContactName(String ntp060ContactName) {
        ntp060ContactName__ = ntp060ContactName;
    }
    /**
     * @return ntp060GyomuName
     */
    public String getNtp060GyomuName() {
        return ntp060GyomuName__;
    }
    /**
     * @param ntp060GyomuName 設定する ntp060GyomuName
     */
    public void setNtp060GyomuName(String ntp060GyomuName) {
        ntp060GyomuName__ = ntp060GyomuName;
    }
    /**
     * @return ntp060KinJutyu
     */
    public String getNtp060KinJutyu() {
        return ntp060KinJutyu__;
    }
    /**
     * @param ntp060KinJutyu 設定する ntp060KinJutyu
     */
    public void setNtp060KinJutyu(String ntp060KinJutyu) {
        ntp060KinJutyu__ = ntp060KinJutyu;
    }
    /**
     * @return ntp060KinMitumori
     */
    public String getNtp060KinMitumori() {
        return ntp060KinMitumori__;
    }
    /**
     * @param ntp060KinMitumori 設定する ntp060KinMitumori
     */
    public void setNtp060KinMitumori(String ntp060KinMitumori) {
        ntp060KinMitumori__ = ntp060KinMitumori;
    }
    /**
     * @return ntp060ProcessName
     */
    public String getNtp060ProcessName() {
        return ntp060ProcessName__;
    }
    /**
     * @param ntp060ProcessName 設定する ntp060ProcessName
     */
    public void setNtp060ProcessName(String ntp060ProcessName) {
        ntp060ProcessName__ = ntp060ProcessName;
    }
    /**
     * <p>ntp060ShohinList を取得します。
     * @return ntp060ShohinList
     */
    public List<NtpShohinModel> getNtp060ShohinList() {
        return ntp060ShohinList__;
    }
    /**
     * <p>ntp060ShohinList をセットします。
     * @param ntp060ShohinList ntp060ShohinList
     */
    public void setNtp060ShohinList(List<NtpShohinModel> ntp060ShohinList) {
        ntp060ShohinList__ = ntp060ShohinList;
    }
    /**
     * <p>ntp060Edate を取得します。
     * @return ntp060Edate
     */
    public String getNtp060Edate() {
        return ntp060Edate__;
    }
    /**
     * <p>ntp060Edate をセットします。
     * @param ntp060Edate ntp060Edate
     */
    public void setNtp060Edate(String ntp060Edate) {
        ntp060Edate__ = ntp060Edate;
    }
    /**
     * <p>ntp060MitumoriDate を取得します。
     * @return ntp060MitumoriDate
     */
    public String getNtp060MitumoriDate() {
        return ntp060MitumoriDate__;
    }
    /**
     * <p>ntp060MitumoriDate をセットします。
     * @param ntp060MitumoriDate ntp060MitumoriDate
     */
    public void setNtp060MitumoriDate(String ntp060MitumoriDate) {
        ntp060MitumoriDate__ = ntp060MitumoriDate;
    }
    /**
     * <p>ntp060JutyuDate を取得します。
     * @return ntp060JutyuDate
     */
    public String getNtp060JutyuDate() {
        return ntp060JutyuDate__;
    }
    /**
     * <p>ntp060JutyuDate をセットします。
     * @param ntp060JutyuDate ntp060JutyuDate
     */
    public void setNtp060JutyuDate(String ntp060JutyuDate) {
        ntp060JutyuDate__ = ntp060JutyuDate;
    }
    /**
     * <p>ntp060GyomuCode を取得します。
     * @return ntp060GyomuCode
     */
    public String getNtp060GyomuCode() {
        return ntp060GyomuCode__;
    }
    /**
     * <p>ntp060GyomuCode をセットします。
     * @param ntp060GyomuCode ntp060GyomuCode
     */
    public void setNtp060GyomuCode(String ntp060GyomuCode) {
        ntp060GyomuCode__ = ntp060GyomuCode;
    }
    /**
     * <p>ntp060ProcessCode を取得します。
     * @return ntp060ProcessCode
     */
    public String getNtp060ProcessCode() {
        return ntp060ProcessCode__;
    }
    /**
     * <p>ntp060ProcessCode をセットします。
     * @param ntp060ProcessCode ntp060ProcessCode
     */
    public void setNtp060ProcessCode(String ntp060ProcessCode) {
        ntp060ProcessCode__ = ntp060ProcessCode;
    }
    /**
     * <p>ntp060ContactCode を取得します。
     * @return ntp060ContactCode
     */
    public String getNtp060ContactCode() {
        return ntp060ContactCode__;
    }
    /**
     * <p>ntp060ContactCode をセットします。
     * @param ntp060ContactCode ntp060ContactCode
     */
    public void setNtp060ContactCode(String ntp060ContactCode) {
        ntp060ContactCode__ = ntp060ContactCode;
    }
    /**
     * <p>ntp060CompanyCode を取得します。
     * @return ntp060CompanyCode
     */
    public String getNtp060CompanyCode() {
        return ntp060CompanyCode__;
    }
    /**
     * <p>ntp060CompanyCode をセットします。
     * @param ntp060CompanyCode ntp060CompanyCode
     */
    public void setNtp060CompanyCode(String ntp060CompanyCode) {
        ntp060CompanyCode__ = ntp060CompanyCode;
    }
}
