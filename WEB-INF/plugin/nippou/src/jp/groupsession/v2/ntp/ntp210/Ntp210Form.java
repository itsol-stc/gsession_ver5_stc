package jp.groupsession.v2.ntp.ntp210;

import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp061.Ntp061Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 案件情報ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp210Form extends Ntp061Form {

    /** 案件SID */
    private int ntp210NanSid__ = -1;
    /** 登録者名 */
    private String ntp210TourokuName__ = null;
    /** 案件コード */
    private String ntp210NanCode__ = null;
    /** 案件名 */
    private String ntp210NanName__ = null;
    /** 案件詳細 */
    private String ntp210NanSyosai__ = null;
    /** 見積金額 */
    private String ntp210NanKinMitumori__ = null;
    /** 受注金額 */
    private String ntp210NanKinJutyu__ = null;
    /** 見込度 */
    private int ntp210NanMikomi__ = GSConstNippou.MIKOMI_10;
    /** 商談結果 */
    private int ntp210NanSyodan__ = GSConstNippou.SYODAN_CHU;
    /** 状態 */
    private int ntp210NanState__ = GSConstNippou.STATE_UNCOMPLETE;
    /** 業務内容SID */
    private int ntp210NgySid__ = -1;
    /** プロセスSID */
    private int ntp210NgpSid__ = -1;
    /** コンタクトSID */
    private int ntp210NcnSid__ = -1;

    /** 企業コード */
    private String ntp210AcoCode__ = null;

    /** 選択商品チェックボックスリスト */
    private String[] ntp210ChkShohinSidList__ = null;

    /** 追加済み商品一覧 */
    private List<LabelValueBean> ntp210ShohinList__ = null;
    /** 追加済み商品(選択) */
    private String[] ntp210SelectShohin__ = null;

    /** 業種名 */
    private String ntp210GyoushuName__ = null;
    /** プロセス名 */
    private String ntp210ProcessName__ = null;
    /** コンタクト名 */
    private String ntp210ContactName__ = null;

    /** 会社SID  */
    private String ntp210CompanySid__ = null;
    /** 会社コード  */
    private String ntp210CompanyCode__ = null;
    /** 会社名  */
    private String ntp210CompanyName__ = null;
    /** 会社拠点SID  */
    private String ntp210CompanyBaseSid__ = null;
    /** 会社拠点コード  */
    private String ntp210CompanyBaseCode__ = null;
    /** 会社拠点名  */
    private String ntp210CompanyBaseName__ = null;

    /** 案件登録日付 */
    private String ntp210Date__ = null;

    /** 履歴表示フラグ 0:通常 1:履歴 */
    private int ntp210HisFlg__ = 0;
    /** 案件履歴SID */
    private int ntp210NahSid__ = -1;

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

    /**
     * <p>ntp210NanCode を取得します。
     * @return ntp210NanCode
     */
    public String getNtp210NanCode() {
        return ntp210NanCode__;
    }

    /**
     * <p>ntp210NanCode をセットします。
     * @param ntp210NanCode ntp210NanCode
     */
    public void setNtp210NanCode(String ntp210NanCode) {
        ntp210NanCode__ = ntp210NanCode;
    }

    /**
     * <p>ntp210NanName を取得します。
     * @return ntp210NanName
     */
    public String getNtp210NanName() {
        return ntp210NanName__;
    }

    /**
     * <p>ntp210NanName をセットします。
     * @param ntp210NanName ntp210NanName
     */
    public void setNtp210NanName(String ntp210NanName) {
        ntp210NanName__ = ntp210NanName;
    }

    /**
     * <p>ntp210NanSyosai を取得します。
     * @return ntp210NanSyosai
     */
    public String getNtp210NanSyosai() {
        return ntp210NanSyosai__;
    }

    /**
     * <p>ntp210NanSyosai をセットします。
     * @param ntp210NanSyosai ntp210NanSyosai
     */
    public void setNtp210NanSyosai(String ntp210NanSyosai) {
        ntp210NanSyosai__ = ntp210NanSyosai;
    }

    /**
     * <p>ntp210NanKinMitumori を取得します。
     * @return ntp210NanKinMitumori
     */
    public String getNtp210NanKinMitumori() {
        return ntp210NanKinMitumori__;
    }

    /**
     * <p>ntp210NanKinMitumori をセットします。
     * @param ntp210NanKinMitumori ntp210NanKinMitumori
     */
    public void setNtp210NanKinMitumori(String ntp210NanKinMitumori) {
        ntp210NanKinMitumori__ = ntp210NanKinMitumori;
    }

    /**
     * <p>ntp210NanKinJutyu を取得します。
     * @return ntp210NanKinJutyu
     */
    public String getNtp210NanKinJutyu() {
        return ntp210NanKinJutyu__;
    }

    /**
     * <p>ntp210NanKinJutyu をセットします。
     * @param ntp210NanKinJutyu ntp210NanKinJutyu
     */
    public void setNtp210NanKinJutyu(String ntp210NanKinJutyu) {
        ntp210NanKinJutyu__ = ntp210NanKinJutyu;
    }

    /**
     * <p>ntp210NanMikomi を取得します。
     * @return ntp210NanMikomi
     */
    public int getNtp210NanMikomi() {
        return ntp210NanMikomi__;
    }

    /**
     * <p>ntp210NanMikomi をセットします。
     * @param ntp210NanMikomi ntp210NanMikomi
     */
    public void setNtp210NanMikomi(int ntp210NanMikomi) {
        ntp210NanMikomi__ = ntp210NanMikomi;
    }

    /**
     * <p>ntp210NanSyodan を取得します。
     * @return ntp210NanSyodan
     */
    public int getNtp210NanSyodan() {
        return ntp210NanSyodan__;
    }

    /**
     * <p>ntp210NanSyodan をセットします。
     * @param ntp210NanSyodan ntp210NanSyodan
     */
    public void setNtp210NanSyodan(int ntp210NanSyodan) {
        ntp210NanSyodan__ = ntp210NanSyodan;
    }

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
     * <p>ntp210NgpSid を取得します。
     * @return ntp210NgpSid
     */
    public int getNtp210NgpSid() {
        return ntp210NgpSid__;
    }

    /**
     * <p>ntp210NgpSid をセットします。
     * @param ntp210NgpSid ntp210NgpSid
     */
    public void setNtp210NgpSid(int ntp210NgpSid) {
        ntp210NgpSid__ = ntp210NgpSid;
    }

    /**
     * <p>ntp210NcnSid を取得します。
     * @return ntp210NcnSid
     */
    public int getNtp210NcnSid() {
        return ntp210NcnSid__;
    }

    /**
     * <p>ntp210NcnSid をセットします。
     * @param ntp210NcnSid ntp210NcnSid
     */
    public void setNtp210NcnSid(int ntp210NcnSid) {
        ntp210NcnSid__ = ntp210NcnSid;
    }

    /**
     * <p>ntp210AcoCode を取得します。
     * @return ntp210AcoCode
     */
    public String getNtp210AcoCode() {
        return ntp210AcoCode__;
    }

    /**
     * <p>ntp210AcoCode をセットします。
     * @param ntp210AcoCode ntp210AcoCode
     */
    public void setNtp210AcoCode(String ntp210AcoCode) {
        ntp210AcoCode__ = ntp210AcoCode;
    }

    /**
     * <p>ntp210ChkShohinSidList を取得します。
     * @return ntp210ChkShohinSidList
     */
    public String[] getNtp210ChkShohinSidList() {
        return ntp210ChkShohinSidList__;
    }

    /**
     * <p>ntp210ChkShohinSidList をセットします。
     * @param ntp210ChkShohinSidList ntp210ChkShohinSidList
     */
    public void setNtp210ChkShohinSidList(String[] ntp210ChkShohinSidList) {
        ntp210ChkShohinSidList__ = ntp210ChkShohinSidList;
    }

    /**
     * <p>ntp210ShohinList を取得します。
     * @return ntp210ShohinList
     */
    public List<LabelValueBean> getNtp210ShohinList() {
        return ntp210ShohinList__;
    }

    /**
     * <p>ntp210ShohinList をセットします。
     * @param ntp210ShohinList ntp210ShohinList
     */
    public void setNtp210ShohinList(List<LabelValueBean> ntp210ShohinList) {
        ntp210ShohinList__ = ntp210ShohinList;
    }

    /**
     * <p>ntp210SelectShohin を取得します。
     * @return ntp210SelectShohin
     */
    public String[] getNtp210SelectShohin() {
        return ntp210SelectShohin__;
    }

    /**
     * <p>ntp210SelectShohin をセットします。
     * @param ntp210SelectShohin ntp210SelectShohin
     */
    public void setNtp210SelectShohin(String[] ntp210SelectShohin) {
        ntp210SelectShohin__ = ntp210SelectShohin;
    }

    /**
     * <p>ntp210CompanySid を取得します。
     * @return ntp210CompanySid
     */
    public String getNtp210CompanySid() {
        return ntp210CompanySid__;
    }

    /**
     * <p>ntp210CompanySid をセットします。
     * @param ntp210CompanySid ntp210CompanySid
     */
    public void setNtp210CompanySid(String ntp210CompanySid) {
        ntp210CompanySid__ = ntp210CompanySid;
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
     * <p>ntp210CompanyBaseSid を取得します。
     * @return ntp210CompanyBaseSid
     */
    public String getNtp210CompanyBaseSid() {
        return ntp210CompanyBaseSid__;
    }

    /**
     * <p>ntp210CompanyBaseSid をセットします。
     * @param ntp210CompanyBaseSid ntp210CompanyBaseSid
     */
    public void setNtp210CompanyBaseSid(String ntp210CompanyBaseSid) {
        ntp210CompanyBaseSid__ = ntp210CompanyBaseSid;
    }

    /**
     * <p>ntp210CompanyBaseCode を取得します。
     * @return ntp210CompanyBaseCode
     */
    public String getNtp210CompanyBaseCode() {
        return ntp210CompanyBaseCode__;
    }

    /**
     * <p>ntp210CompanyBaseCode をセットします。
     * @param ntp210CompanyBaseCode ntp210CompanyBaseCode
     */
    public void setNtp210CompanyBaseCode(String ntp210CompanyBaseCode) {
        ntp210CompanyBaseCode__ = ntp210CompanyBaseCode;
    }

    /**
     * <p>ntp210CompanyBaseName を取得します。
     * @return ntp210CompanyBaseName
     */
    public String getNtp210CompanyBaseName() {
        return ntp210CompanyBaseName__;
    }

    /**
     * <p>ntp210CompanyBaseName をセットします。
     * @param ntp210CompanyBaseName ntp210CompanyBaseName
     */
    public void setNtp210CompanyBaseName(String ntp210CompanyBaseName) {
        ntp210CompanyBaseName__ = ntp210CompanyBaseName;
    }

    /**
     * <p>ntp210Date を取得します。
     * @return ntp210Date
     */
    public String getNtp210Date() {
        return ntp210Date__;
    }

    /**
     * <p>ntp210Date をセットします。
     * @param ntp210Date ntp210Date
     */
    public void setNtp210Date(String ntp210Date) {
        ntp210Date__ = ntp210Date;
    }

    /**
     * <p>ntp210NanSid を取得します。
     * @return ntp210NanSid
     */
    public int getNtp210NanSid() {
        return ntp210NanSid__;
    }

    /**
     * <p>ntp210NanSid をセットします。
     * @param ntp210NanSid ntp210NanSid
     */
    public void setNtp210NanSid(int ntp210NanSid) {
        ntp210NanSid__ = ntp210NanSid;
    }

    /**
     * <p>ntp210GyoushuName を取得します。
     * @return ntp210GyoushuName
     */
    public String getNtp210GyoushuName() {
        return ntp210GyoushuName__;
    }

    /**
     * <p>ntp210GyoushuName をセットします。
     * @param ntp210GyoushuName ntp210GyoushuName
     */
    public void setNtp210GyoushuName(String ntp210GyoushuName) {
        ntp210GyoushuName__ = ntp210GyoushuName;
    }

    /**
     * <p>ntp210ProcessName を取得します。
     * @return ntp210ProcessName
     */
    public String getNtp210ProcessName() {
        return ntp210ProcessName__;
    }

    /**
     * <p>ntp210ProcessName をセットします。
     * @param ntp210ProcessName ntp210ProcessName
     */
    public void setNtp210ProcessName(String ntp210ProcessName) {
        ntp210ProcessName__ = ntp210ProcessName;
    }

    /**
     * <p>ntp210ContactName を取得します。
     * @return ntp210ContactName
     */
    public String getNtp210ContactName() {
        return ntp210ContactName__;
    }

    /**
     * <p>ntp210ContactName をセットします。
     * @param ntp210ContactName ntp210ContactName
     */
    public void setNtp210ContactName(String ntp210ContactName) {
        ntp210ContactName__ = ntp210ContactName;
    }

    /**
     * <p>ntp210NanState を取得します。
     * @return ntp210NanState
     */
    public int getNtp210NanState() {
        return ntp210NanState__;
    }

    /**
     * <p>ntp210NanState をセットします。
     * @param ntp210NanState ntp210NanState
     */
    public void setNtp210NanState(int ntp210NanState) {
        ntp210NanState__ = ntp210NanState;
    }

    /**
     * <p>ntp210HisFlg を取得します。
     * @return ntp210HisFlg
     */
    public int getNtp210HisFlg() {
        return ntp210HisFlg__;
    }

    /**
     * <p>ntp210HisFlg をセットします。
     * @param ntp210HisFlg ntp210HisFlg
     */
    public void setNtp210HisFlg(int ntp210HisFlg) {
        ntp210HisFlg__ = ntp210HisFlg;
    }

    /**
     * <p>ntp210NahSid を取得します。
     * @return ntp210NahSid
     */
    public int getNtp210NahSid() {
        return ntp210NahSid__;
    }

    /**
     * <p>ntp210NahSid をセットします。
     * @param ntp210NahSid ntp210NahSid
     */
    public void setNtp210NahSid(int ntp210NahSid) {
        ntp210NahSid__ = ntp210NahSid;
    }
}