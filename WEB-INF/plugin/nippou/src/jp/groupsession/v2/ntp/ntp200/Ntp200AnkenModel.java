package jp.groupsession.v2.ntp.ntp200;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <br>[機  能] 案件情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp200AnkenModel extends NtpAnkenModel {
    /** 企業コード */
    private String ntp200CompanyCode__;
    /** 企業名 */
    private String ntp200CompanyName__;
    /** 拠点名 */
    private String ntp200BaseName__;
    /** 商品カテゴリ */
    private int ntp200ShohinCategory__ = -1;
    /** 商品名 */
    private String ntp200ShohinName__ = null;
    /** 商品名 */
    private List<NtpShohinModel> ntp200ShohinList__;
    /** 見積金額表示用 */
    private String ntp200KinMitumori__;
    /** 受注金額表示用 */
    private String ntp200KinJutyu__;
    /** 業務名 */
    private String ntp200GyomuName__;
    /** プロセス名 */
    private String ntp200ProcessName__;
    /** コンタクト名 */
    private String ntp200ContactName__;
    /** 登録日付 */
    private String ntp200Date__;

    /**
     * @return ntp200Date
     */
    public String getNtp200Date() {
        return ntp200Date__;
    }
    /**
     * @param ntp200Date 設定する ntp200Date
     */
    public void setNtp200Date(String ntp200Date) {
        ntp200Date__ = ntp200Date;
    }
    /**
     * @return ntp200BaseName
     */
    public String getNtp200BaseName() {
        return ntp200BaseName__;
    }
    /**
     * @param ntp200BaseName 設定する ntp200BaseName
     */
    public void setNtp200BaseName(String ntp200BaseName) {
        ntp200BaseName__ = ntp200BaseName;
    }
    /**
     * @return ntp200CompanyName
     */
    public String getNtp200CompanyName() {
        return ntp200CompanyName__;
    }
    /**
     * @param ntp200CompanyName 設定する ntp200CompanyName
     */
    public void setNtp200CompanyName(String ntp200CompanyName) {
        ntp200CompanyName__ = ntp200CompanyName;
    }
    /**
     * @return ntp200ContactName
     */
    public String getNtp200ContactName() {
        return ntp200ContactName__;
    }
    /**
     * @param ntp200ContactName 設定する ntp200ContactName
     */
    public void setNtp200ContactName(String ntp200ContactName) {
        ntp200ContactName__ = ntp200ContactName;
    }
    /**
     * @return ntp200GyomuName
     */
    public String getNtp200GyomuName() {
        return ntp200GyomuName__;
    }
    /**
     * @param ntp200GyomuName 設定する ntp200GyomuName
     */
    public void setNtp200GyomuName(String ntp200GyomuName) {
        ntp200GyomuName__ = ntp200GyomuName;
    }
    /**
     * @return ntp200KinJutyu
     */
    public String getNtp200KinJutyu() {
        return ntp200KinJutyu__;
    }
    /**
     * @param ntp200KinJutyu 設定する ntp200KinJutyu
     */
    public void setNtp200KinJutyu(String ntp200KinJutyu) {
        ntp200KinJutyu__ = ntp200KinJutyu;
    }
    /**
     * @return ntp200KinMitumori
     */
    public String getNtp200KinMitumori() {
        return ntp200KinMitumori__;
    }
    /**
     * @param ntp200KinMitumori 設定する ntp200KinMitumori
     */
    public void setNtp200KinMitumori(String ntp200KinMitumori) {
        ntp200KinMitumori__ = ntp200KinMitumori;
    }
    /**
     * @return ntp200ProcessName
     */
    public String getNtp200ProcessName() {
        return ntp200ProcessName__;
    }
    /**
     * @param ntp200ProcessName 設定する ntp200ProcessName
     */
    public void setNtp200ProcessName(String ntp200ProcessName) {
        ntp200ProcessName__ = ntp200ProcessName;
    }
    /**
     * <p>ntp200CompanyCode を取得します。
     * @return ntp200CompanyCode
     */
    public String getNtp200CompanyCode() {
        return ntp200CompanyCode__;
    }
    /**
     * <p>ntp200CompanyCode をセットします。
     * @param ntp200CompanyCode ntp200CompanyCode
     */
    public void setNtp200CompanyCode(String ntp200CompanyCode) {
        ntp200CompanyCode__ = ntp200CompanyCode;
    }
    /**
     * <p>ntp200ShohinList を取得します。
     * @return ntp200ShohinList
     */
    public List<NtpShohinModel> getNtp200ShohinList() {
        return ntp200ShohinList__;
    }
    /**
     * <p>ntp200ShohinList をセットします。
     * @param ntp200ShohinList ntp200ShohinList
     */
    public void setNtp200ShohinList(List<NtpShohinModel> ntp200ShohinList) {
        ntp200ShohinList__ = ntp200ShohinList;
    }
    /**
     * <p>ntp200ShohinCategory を取得します。
     * @return ntp200ShohinCategory
     */
    public int getNtp200ShohinCategory() {
        return ntp200ShohinCategory__;
    }
    /**
     * <p>ntp200ShohinCategory をセットします。
     * @param ntp200ShohinCategory ntp200ShohinCategory
     */
    public void setNtp200ShohinCategory(int ntp200ShohinCategory) {
        ntp200ShohinCategory__ = ntp200ShohinCategory;
    }
    /**
     * <p>ntp200ShohinName を取得します。
     * @return ntp200ShohinName
     */
    public String getNtp200ShohinName() {
        return ntp200ShohinName__;
    }
    /**
     * <p>ntp200ShohinName をセットします。
     * @param ntp200ShohinName ntp200ShohinName
     */
    public void setNtp200ShohinName(String ntp200ShohinName) {
        ntp200ShohinName__ = ntp200ShohinName;
    }
}
