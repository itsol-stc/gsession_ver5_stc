package jp.groupsession.v2.ntp.ntp061;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr240.model.Adr240Model;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpMikomidoMsgModel;
import jp.groupsession.v2.ntp.ntp130.Ntp130Biz;
import jp.groupsession.v2.ntp.ntp130.Ntp130ShohinModel;
import jp.groupsession.v2.ntp.ntp200.Ntp200ParamModel;
import jp.groupsession.v2.ntp.ui.parts.anken.permit.AnkenPermitSelector;
import jp.groupsession.v2.ntp.ui.parts.anken.tanto.AnkenTantoSelector;

/**
 * <br>[機  能] 日報 案件登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp061ParamModel extends Ntp200ParamModel {

    /**----------------------- 案件登録画面パラメータ ---------------------------*/

    /** 遷移元 */
    private String ntp061ReturnPage__ = null;
    /** 初期化フラグ */
    private int ntp061InitFlg__ = 0;
    /** 複写して登録フラグ 0:通常 1:複写して登録 */
    private int ntp061CopyFlg__ = 0;
    /** 登録者名 */
    private String ntp061TourokuName__ = null;
    /** 登録者ログイン停止 */
    private int ntp061TourokuUsrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /** 案件コード */
    private String ntp061NanCode__ = null;
    /** 案件名 */
    private String ntp061NanName__ = null;
    /** 案件詳細 */
    private String ntp061NanSyosai__ = null;
    /** 見積金額 */
    private String ntp061NanKinMitumori__ = null;
    /** 受注金額 */
    private String ntp061NanKinJutyu__ = null;
    /** 見込度 */
    private int ntp061NanMikomi__ = GSConstNippou.MIKOMI_10;
    /** 商談結果 */
    private int ntp061NanSyodan__ = GSConstNippou.SYODAN_CHU;
    /** 状態 */
    private int ntp061NanState__ = GSConstNippou.STATE_UNCOMPLETE;
    /** 権限区分 閲覧*/
    private int ntp061NanPermitView__ = GSConstNippou.NAP_KBN_ALL;
    /** 権限区分 編集*/
    private int ntp061NanPermitEdit__ = GSConstNippou.NAP_KBN_ALL;

    /** 権限区分 閲覧権限 グループ */
    private String ntp061NanPermitGroup__ = null;
    /** 権限区分 閲覧・編集権限 選択ユーザ・グループ */
    private String[] ntp061NanPermitUserView__ = null;
    /** 権限区分 閲覧・編集権限 選択ユーザ・グループ */
    private String[] ntp061NanPermitUserEdit__ = null;
    /** 権限区分 編集権限 選択ユーザ・グループ */
    private String[] ntp061NanPermitEditUser__ = null;
    /** 権限区分 閲覧・編集権限 UI*/
    private AnkenPermitSelector ntp061NanPermitUI__ = null;
    /** 権限区分 編集権限 UI*/
    private AnkenPermitSelector ntp061NanPermitEditUI__ = null;

    /** 業務内容SID */
    private int ntp061NgySid__ = -1;
    /** プロセスSID */
    private int ntp061NgpSid__ = -1;
    /** コンタクトSID */
    private int ntp061NcnSid__ = -1;
//    /** 案件登録 開始年 */
//    private String ntp061FrYear__ = null;
//    /** 案件登録 開始月 */
//    private String ntp061FrMonth__ = null;
//    /** 案件登録 開始日 */
//    private String ntp061FrDay__ = null;
    /** 企業コード */
    private String ntp061AcoCode__ = null;

    /** 選択商品チェックボックスリスト */
    private String[] ntp061ChkShohinSidList__ = null;

    /** 追加済み商品一覧 */
    private List<LabelValueBean> ntp061ShohinList__ = null;
    /** 追加済み商品(選択) */
    private String[] ntp061SelectShohin__ = null;

    /** 会社SID  */
    private String ntp061CompanySid__ = null;
    /** 会社コード  */
    private String ntp061CompanyCode__ = null;
    /** 会社名  */
    private String ntp061CompanyName__ = null;
    /** 会社拠点SID  */
    private String ntp061CompanyBaseSid__ = null;
    /** 会社拠点コード  */
    private String ntp061CompanyBaseCode__ = null;
    /** 会社拠点名  */
    private String ntp061CompanyBaseName__ = null;

    /** 案件登録日付 */
    private String ntp061Date__ = null;

    /** 日付 年ラベル */
    private List<LabelValueBean> ntp061YearLabel__;
    /** 日付 月ラベル */
    private List<LabelValueBean> ntp061MonthLabel__;
    /** 日付 日ラベル */
    private List<LabelValueBean> ntp061DayLabel__;

    /** 見積もり日付 年 */
    private String ntp061MitumoriYear__ = null;
    /** 見積もり日付 月 */
    private String ntp061MitumoriMonth__ = null;
    /** 見積もり日付 日 */
    private String ntp061MitumoriDay__ = null;
    /** 見積もり日付 年月日 */
    private String ntp061MitumoriDate__ = null;
    /** 受注日付 年 */
    private String ntp061JutyuYear__ = null;
    /** 受注日付 月 */
    private String ntp061JutyuMonth__ = null;
    /** 受注日付 日 */
    private String ntp061JutyuDay__ = null;
    /** 受注日付 年月日 */
    private String ntp061JutyuDate__ = null;

    /**----------------------- 担当者 ---------------------------*/
    /** 担当者グループSID */
    private String ntp061GroupSid__ = null;
    /** セーブユーザーリスト */
    private String[] sv_users__ = null;
    /** 担当者 UI */
    private AnkenTantoSelector tantoListUI__ = null;

    /**----------------------- ポップアップ使用パラメータ ---------------------------*/

    /** ポップアップ区分   0:通常 1:ポップアップ表示*/
    private int ntp061PopKbn__ = 0;
    /** ポップアップ時 登録確認フラグ  0:通常 1:登録確認メッセージ表示*/
    private int ntp061AddFlg__ = 0;
    /** ポップアップ時 登録完了フラグ  0:通常 1:登録完了(案件選択へ戻る) 2:登録完了(日報登録へ戻る)*/
    private int ntp061AddCompFlg__ = 0;
    /** 日報登録用 案件SID*/
    private int ntp061AnkenSid__ = 0;
    /** 会社SID  */
    private String ntp061SvCompanySid__ = null;
    /** 日報登録用 会社コード  */
    private String ntp061SvCompanyCode__ = null;
    /** 日報登録用 会社名  */
    private String ntp061SvCompanyName__ = null;
    /** 日報登録用 会社拠点SID  */
    private String ntp061SvCompanyBaseSid__ = null;
    /** 日報登録用 会社拠点名  */
    private String ntp061SvCompanyBaseName__ = null;


    /**----------------------- アドレス帳パラメータ ---------------------------*/
    /** 選択行 */
    private String ntp061AdrIndex__ = "-1";
    /** 選択文字 */
    private String ntp061AdrStr__ = "-1";
    /** 親画面ID */
    private String ntp061AdrparentPageId__ = null;

    /** 行リスト **/
    private List<LabelValueBean> ntp061AdrIndexList__ = null;
    /** 文字リスト **/
    private List<LabelValueBean> ntp061AdrStrList__ = null;
    /** 会社リスト **/
    private List<Adr240Model> ntp061AdrCompanyList__ = null;


    /** 会社SID */
    private String ntp061AdrCompanySid__ = null;
    /** 会社コード */
    private String ntp061AdrCompanyCode__ = null;
    /** 会社拠点SID */
    private String ntp061AdrCompanyBaseSid__ = null;
    /** 会社名 */
    private String ntp061AdrCompanyName__ = null;

    /** 選択された会社 */
    private String ntp061AdrselectCompany__ = null;

    /** ページ */
    private int ntp061Adrpage__ = 0;
    /** ページ(画面上部) */
    private int ntp061AdrpageTop__ = 0;
    /** ページ(画面下部) */
    private int ntp061AdrpageBottom__ = 0;

    /** モード 0:会社・担当者選択 1：会社のみ選択 */
    private int ntp061Adrmode__ = 0;



    /** 企業コード */
    private String ntp061code__ = null;
    /** 会社名 */
    private String ntp061coName__ = null;
    /** 会社名カナ */
    private String ntp061coNameKn__ = null;
    /** 支店・営業所名 */
    private String ntp061coBaseName__ = null;
    /** 業種 */
    private int ntp061atiSid__ = 0;
    /** 都道府県 */
    private int ntp061tdfk__ = 0;
    /** 備考 */
    private String ntp061biko__ = null;

    /** 企業コード(検索条件保持用) */
    private String ntp061svAdrCode__ = null;
    /** 会社名(検索条件保持用) */
    private String ntp061svAdrCoName__ = null;
    /** 会社名カナ(検索条件保持用) */
    private String ntp061svAdrCoNameKn__ = null;
    /** 支店・営業所名(検索条件保持用) */
    private String ntp061svAdrCoBaseName__ = null;
    /** 業種(検索条件保持用) */
    private int ntp061svAdrAtiSid__ = 0;
    /** 都道府県(検索条件保持用) */
    private int ntp061svAdrTdfk__ = 0;
    /** 備考(検索条件保持用) */
    private String ntp061svAdrBiko__ = null;

    /** ソートキー */
    private int ntp061AdrSortKey__ = GSConstAddress.COMPANY_SORT_CODE;
    /** オーダーキー */
    private int ntp061AdrOrderKey__ = GSConst.ORDER_KEY_ASC;

    /** 検索モード */
    private int ntp061SearchMode__ = GSConstAddress.SEARCH_COMPANY_MODE_50;
    /** 検索フラグ */
    private int ntp061searchFlg__ = 0;

    /** 処理モード 0:通常 1：親画面リロードなし */
    private int ntp061AdrPrsMode__ = 0;
    /** 親画面行番号 */
    private String ntp061AdrRowNumber__ = "";

    /** プロジェクト選択フラグ */
    private int ntp061AdrProAddFlg__ = 0;
    /** プロジェクト選択エラーフラグ */
    private int ntp061AdrProAddErrFlg__ = 0;

    /** ページコンボ */
    private List<LabelValueBean> pageCmbList__ = null;

    /** 画面操作区分   0:通常 1:アドレス帳ポップアップ表示*/
    private int ntp061AdrKbn__ = 0;

    /** 業種コンボ */
    private List<LabelValueBean> atiCmbList__ = null;
    /** 都道府県コンボ */
    private List<LabelValueBean> tdfkCmbList__ = null;

    /**----------------------- 商品追加 ---------------------------*/
    /** 商品一覧 */
    private ArrayList<Ntp130ShohinModel> ntp061ItmShohinList__ = null;
    /** カテゴリリスト一覧 */
    private List<LabelValueBean> ntp061CategoryList__ = null;
    /** カテゴリSID */
    private int ntp061CatSid__ = -1;
    /** 商品内容SID */
    private int ntp061ItmNhnSid__ = -1;
    /** 処理モード */
    private String ntp061ItmProcMode__ = GSConstNippou.CMD_ADD;
    /** ソートキーリスト一覧 */
    private List<LabelValueBean> ntp061ItmSortList__ = null;

    /** 画面表示モード */
    private String ntp061ItmDspMode__ = null;
    /** 遷移元 */
    private String ntp061ItmReturnPage__ = null;
    /** 初期化フラグ */
    private int ntp061ItmInitFlg__ = 0;
    /** 商品コード */
    private String ntp061ItmNhnCode__ = null;
    /** 商品名 */
    private String ntp061ItmNhnName__ = null;
    /** 販売価格 */
    private String ntp061ItmNhnPriceSale__ = null;
    /** 原価価格 */
    private String ntp061ItmNhnPriceCost__ = null;
    /** 販売価格区分 */
    private int ntp061ItmNhnPriceSaleKbn__ = Ntp130Biz.PRICE_MORE;
    /** 販売価格区分 */
    private int ntp061ItmNhnPriceCostKbn__ = Ntp130Biz.PRICE_MORE;
    /** ソートキー１ */
    private int ntp061ItmSortKey1__ = GSConstNippou.SORT_KEY_NHK_EDATE;;
    /** オーダキー１ */
    private int ntp061ItmOrderKey1__ = GSConstNippou.ORDER_KEY_DESC;
    /** ソートキー２ */
    private int ntp061ItmSortKey2__ = -1;
    /** オーダキー２ */
    private int ntp061ItmOrderKey2__ = GSConstNippou.ORDER_KEY_ASC;

    /** ページ */
    private int ntp061ItmPage__ = 0;
    /** ページ上段 */
    private int ntp061ItmPageTop__ = 0;
    /** ページ下段 */
    private int ntp061ItmPageBottom__ = 0;
    /** ページコンボ */
    private List<LabelValueBean> ntp061ItmPageCmbList__ = null;
    /** 商品チェック (現在ページ以外でチェックされている値) */
    private ArrayList<String> ntp061ItmSelectedSid__ = null;
    /** 選択商品チェックボックスリスト */
    private String[] ntp061ItmChkShohinSidList__ = null;
    /** 選択商品チェックボックスリスト(保存用) */
    private String[] ntp061ItmSvChkShohinSidList__ = null;

    /** 画面操作区分   0:通常 1:商品ポップアップ表示*/
    private int ntp061ItmKbn__ = 0;

    /** 見込み度基準存在フラグ  0:なし 1:あり*/
    private int ntp061MikomidoFlg__ = 0;

    /** 見込み度基準リスト */
    private ArrayList <NtpMikomidoMsgModel> ntp061MikomidoMsgList__ = null;

    /**
     * @return ntp061ReturnPage
     */
    public String getNtp061ReturnPage() {
        return ntp061ReturnPage__;
    }
    /**
     * @param ntp061ReturnPage 設定する ntp061ReturnPage
     */
    public void setNtp061ReturnPage(String ntp061ReturnPage) {
        ntp061ReturnPage__ = ntp061ReturnPage;
    }
    /**
     * @return ntp061ChkShohinSidList
     */
    public String[] getNtp061ChkShohinSidList() {
        return ntp061ChkShohinSidList__;
    }
    /**
     * @param ntp061ChkShohinSidList 設定する ntp061ChkShohinSidList
     */
    public void setNtp061ChkShohinSidList(String[] ntp061ChkShohinSidList) {
        ntp061ChkShohinSidList__ = ntp061ChkShohinSidList;
    }
    /**
     * @return ntp061TourokuName
     */
    public String getNtp061TourokuName() {
        return ntp061TourokuName__;
    }
    /**
     * @param ntp061TourokuName 設定する ntp061TourokuName
     */
    public void setNtp061TourokuName(String ntp061TourokuName) {
        ntp061TourokuName__ = ntp061TourokuName;
    }
    /**
     * @return ntp061AcoCode
     */
    public String getNtp061AcoCode() {
        return ntp061AcoCode__;
    }
    /**
     * @param ntp061AcoCode 設定する ntp061AcoCode
     */
    public void setNtp061AcoCode(String ntp061AcoCode) {
        ntp061AcoCode__ = ntp061AcoCode;
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
     * @return ntp061CompanyBaseName
     */
    public String getNtp061CompanyBaseName() {
        return ntp061CompanyBaseName__;
    }
    /**
     * @param ntp061CompanyBaseName 設定する ntp061CompanyBaseName
     */
    public void setNtp061CompanyBaseName(String ntp061CompanyBaseName) {
        ntp061CompanyBaseName__ = ntp061CompanyBaseName;
    }
    /**
//     * @return ntp061FrDay
//     */
//    public String getNtp061FrDay() {
//        return ntp061FrDay__;
//    }
//    /**
//     * @param ntp061FrDay 設定する ntp061FrDay
//     */
//    public void setNtp061FrDay(String ntp061FrDay) {
//        ntp061FrDay__ = ntp061FrDay;
//    }
//    /**
//     * @return ntp061FrMonth
//     */
//    public String getNtp061FrMonth() {
//        return ntp061FrMonth__;
//    }
//    /**
//     * @param ntp061FrMonth 設定する ntp061FrMonth
//     */
//    public void setNtp061FrMonth(String ntp061FrMonth) {
//        ntp061FrMonth__ = ntp061FrMonth;
//    }
//    /**
//     * @return ntp061FrYear
//     */
//    public String getNtp061FrYear() {
//        return ntp061FrYear__;
//    }
//    /**
//     * @param ntp061FrYear 設定する ntp061FrYear
//     */
//    public void setNtp061FrYear(String ntp061FrYear) {
//        ntp061FrYear__ = ntp061FrYear;
//    }
    /**
     * @return ntp061NanCode
     */
    public String getNtp061NanCode() {
        return ntp061NanCode__;
    }
    /**
     * @param ntp061NanCode 設定する ntp061NanCode
     */
    public void setNtp061NanCode(String ntp061NanCode) {
        ntp061NanCode__ = ntp061NanCode;
    }
    /**
     * @return ntp061NanKinJutyu
     */
    public String getNtp061NanKinJutyu() {
        return ntp061NanKinJutyu__;
    }
    /**
     * @param ntp061NanKinJutyu 設定する ntp061NanKinJutyu
     */
    public void setNtp061NanKinJutyu(String ntp061NanKinJutyu) {
        ntp061NanKinJutyu__ = ntp061NanKinJutyu;
    }
    /**
     * @return ntp061NanKinMitumori
     */
    public String getNtp061NanKinMitumori() {
        return ntp061NanKinMitumori__;
    }
    /**
     * @param ntp061NanKinMitumori 設定する ntp061NanKinMitumori
     */
    public void setNtp061NanKinMitumori(String ntp061NanKinMitumori) {
        ntp061NanKinMitumori__ = ntp061NanKinMitumori;
    }
    /**
     * @return ntp061NanMikomi
     */
    public int getNtp061NanMikomi() {
        return ntp061NanMikomi__;
    }
    /**
     * @param ntp061NanMikomi 設定する ntp061NanMikomi
     */
    public void setNtp061NanMikomi(int ntp061NanMikomi) {
        ntp061NanMikomi__ = ntp061NanMikomi;
    }
    /**
     * @return ntp061NanName
     */
    public String getNtp061NanName() {
        return ntp061NanName__;
    }
    /**
     * @param ntp061NanName 設定する ntp061NanName
     */
    public void setNtp061NanName(String ntp061NanName) {
        ntp061NanName__ = ntp061NanName;
    }
    /**
     * @return ntp061NanSyodan
     */
    public int getNtp061NanSyodan() {
        return ntp061NanSyodan__;
    }
    /**
     * @param ntp061NanSyodan 設定する ntp061NanSyodan
     */
    public void setNtp061NanSyodan(int ntp061NanSyodan) {
        ntp061NanSyodan__ = ntp061NanSyodan;
    }
    /**
     * @return ntp061NanSyosai
     */
    public String getNtp061NanSyosai() {
        return ntp061NanSyosai__;
    }
    /**
     * @param ntp061NanSyosai 設定する ntp061NanSyosai
     */
    public void setNtp061NanSyosai(String ntp061NanSyosai) {
        ntp061NanSyosai__ = ntp061NanSyosai;
    }
    /**
     * @return ntp061NcnSid
     */
    public int getNtp061NcnSid() {
        return ntp061NcnSid__;
    }
    /**
     * @param ntp061NcnSid 設定する ntp061NcnSid
     */
    public void setNtp061NcnSid(int ntp061NcnSid) {
        ntp061NcnSid__ = ntp061NcnSid;
    }
    /**
     * @return ntp061NgpSid
     */
    public int getNtp061NgpSid() {
        return ntp061NgpSid__;
    }
    /**
     * @param ntp061NgpSid 設定する ntp061NgpSid
     */
    public void setNtp061NgpSid(int ntp061NgpSid) {
        ntp061NgpSid__ = ntp061NgpSid;
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
    /**
     * @return ntp061ShohinList
     */
    public List<LabelValueBean> getNtp061ShohinList() {
        return ntp061ShohinList__;
    }
    /**
     * @param ntp061ShohinList 設定する ntp061ShohinList
     */
    public void setNtp061ShohinList(List<LabelValueBean> ntp061ShohinList) {
        ntp061ShohinList__ = ntp061ShohinList;
    }
    /**
     * @return ntp061SelectShohin
     */
    public String[] getNtp061SelectShohin() {
        return ntp061SelectShohin__;
    }
    /**
     * @param ntp061SelectShohin 設定する ntp061SelectShohin
     */
    public void setNtp061SelectShohin(String[] ntp061SelectShohin) {
        ntp061SelectShohin__ = ntp061SelectShohin;
    }
    /**
     * @return ntp061InitFlg
     */
    public int getNtp061InitFlg() {
        return ntp061InitFlg__;
    }
    /**
     * @param ntp061InitFlg 設定する ntp061InitFlg
     */
    public void setNtp061InitFlg(int ntp061InitFlg) {
        ntp061InitFlg__ = ntp061InitFlg;
    }
    /**
     * <p>ntp061CompanySid を取得します。
     * @return ntp061CompanySid
     */
    public String getNtp061CompanySid() {
        return ntp061CompanySid__;
    }
    /**
     * <p>ntp061CompanySid をセットします。
     * @param ntp061CompanySid ntp061CompanySid
     */
    public void setNtp061CompanySid(String ntp061CompanySid) {
        ntp061CompanySid__ = ntp061CompanySid;
    }
    /**
     * <p>ntp061CompanyCode を取得します。
     * @return ntp061CompanyCode
     */
    public String getNtp061CompanyCode() {
        return ntp061CompanyCode__;
    }
    /**
     * <p>ntp061CompanyCode をセットします。
     * @param ntp061CompanyCode ntp061CompanyCode
     */
    public void setNtp061CompanyCode(String ntp061CompanyCode) {
        ntp061CompanyCode__ = ntp061CompanyCode;
    }
    /**
     * <p>ntp061CompanyBaseSid を取得します。
     * @return ntp061CompanyBaseSid
     */
    public String getNtp061CompanyBaseSid() {
        return ntp061CompanyBaseSid__;
    }
    /**
     * <p>ntp061CompanyBaseSid をセットします。
     * @param ntp061CompanyBaseSid ntp061CompanyBaseSid
     */
    public void setNtp061CompanyBaseSid(String ntp061CompanyBaseSid) {
        ntp061CompanyBaseSid__ = ntp061CompanyBaseSid;
    }
    /**
     * <p>ntp061CompanyBaseCode を取得します。
     * @return ntp061CompanyBaseCode
     */
    public String getNtp061CompanyBaseCode() {
        return ntp061CompanyBaseCode__;
    }
    /**
     * <p>ntp061CompanyBaseCode をセットします。
     * @param ntp061CompanyBaseCode ntp061CompanyBaseCode
     */
    public void setNtp061CompanyBaseCode(String ntp061CompanyBaseCode) {
        ntp061CompanyBaseCode__ = ntp061CompanyBaseCode;
    }
    /**
     * <p>ntp061PopKbn を取得します。
     * @return ntp061PopKbn
     */
    public int getNtp061PopKbn() {
        return ntp061PopKbn__;
    }
    /**
     * <p>ntp061PopKbn をセットします。
     * @param ntp061PopKbn ntp061PopKbn
     */
    public void setNtp061PopKbn(int ntp061PopKbn) {
        ntp061PopKbn__ = ntp061PopKbn;
    }
    /**
     * <p>ntp061AdrIndex を取得します。
     * @return ntp061AdrIndex
     */
    public String getNtp061AdrIndex() {
        return ntp061AdrIndex__;
    }
    /**
     * <p>ntp061AdrIndex をセットします。
     * @param ntp061AdrIndex ntp061AdrIndex
     */
    public void setNtp061AdrIndex(String ntp061AdrIndex) {
        ntp061AdrIndex__ = ntp061AdrIndex;
    }
    /**
     * <p>ntp061AdrStr を取得します。
     * @return ntp061AdrStr
     */
    public String getNtp061AdrStr() {
        return ntp061AdrStr__;
    }
    /**
     * <p>ntp061AdrStr をセットします。
     * @param ntp061AdrStr ntp061AdrStr
     */
    public void setNtp061AdrStr(String ntp061AdrStr) {
        ntp061AdrStr__ = ntp061AdrStr;
    }
    /**
     * <p>ntp061AdrparentPageId を取得します。
     * @return ntp061AdrparentPageId
     */
    public String getNtp061AdrparentPageId() {
        return ntp061AdrparentPageId__;
    }
    /**
     * <p>ntp061AdrparentPageId をセットします。
     * @param ntp061AdrparentPageId ntp061AdrparentPageId
     */
    public void setNtp061AdrparentPageId(String ntp061AdrparentPageId) {
        ntp061AdrparentPageId__ = ntp061AdrparentPageId;
    }
    /**
     * <p>ntp061AdrIndexList を取得します。
     * @return ntp061AdrIndexList
     */
    public List<LabelValueBean> getNtp061AdrIndexList() {
        return ntp061AdrIndexList__;
    }
    /**
     * <p>ntp061AdrIndexList をセットします。
     * @param ntp061AdrIndexList ntp061AdrIndexList
     */
    public void setNtp061AdrIndexList(List<LabelValueBean> ntp061AdrIndexList) {
        ntp061AdrIndexList__ = ntp061AdrIndexList;
    }
    /**
     * <p>ntp061AdrStrList を取得します。
     * @return ntp061AdrStrList
     */
    public List<LabelValueBean> getNtp061AdrStrList() {
        return ntp061AdrStrList__;
    }
    /**
     * <p>ntp061AdrStrList をセットします。
     * @param ntp061AdrStrList ntp061AdrStrList
     */
    public void setNtp061AdrStrList(List<LabelValueBean> ntp061AdrStrList) {
        ntp061AdrStrList__ = ntp061AdrStrList;
    }
    /**
     * <p>ntp061AdrCompanyList を取得します。
     * @return ntp061AdrCompanyList
     */
    public List<Adr240Model> getNtp061AdrCompanyList() {
        return ntp061AdrCompanyList__;
    }
    /**
     * <p>ntp061AdrCompanyList をセットします。
     * @param ntp061AdrCompanyList ntp061AdrCompanyList
     */
    public void setNtp061AdrCompanyList(List<Adr240Model> ntp061AdrCompanyList) {
        ntp061AdrCompanyList__ = ntp061AdrCompanyList;
    }
    /**
     * <p>ntp061AdrCompanySid を取得します。
     * @return ntp061AdrCompanySid
     */
    public String getNtp061AdrCompanySid() {
        return ntp061AdrCompanySid__;
    }
    /**
     * <p>ntp061AdrCompanySid をセットします。
     * @param ntp061AdrCompanySid ntp061AdrCompanySid
     */
    public void setNtp061AdrCompanySid(String ntp061AdrCompanySid) {
        ntp061AdrCompanySid__ = ntp061AdrCompanySid;
    }
    /**
     * <p>ntp061AdrCompanyCode を取得します。
     * @return ntp061AdrCompanyCode
     */
    public String getNtp061AdrCompanyCode() {
        return ntp061AdrCompanyCode__;
    }
    /**
     * <p>ntp061AdrCompanyCode をセットします。
     * @param ntp061AdrCompanyCode ntp061AdrCompanyCode
     */
    public void setNtp061AdrCompanyCode(String ntp061AdrCompanyCode) {
        ntp061AdrCompanyCode__ = ntp061AdrCompanyCode;
    }
    /**
     * <p>ntp061AdrCompanyBaseSid を取得します。
     * @return ntp061AdrCompanyBaseSid
     */
    public String getNtp061AdrCompanyBaseSid() {
        return ntp061AdrCompanyBaseSid__;
    }
    /**
     * <p>ntp061AdrCompanyBaseSid をセットします。
     * @param ntp061AdrCompanyBaseSid ntp061AdrCompanyBaseSid
     */
    public void setNtp061AdrCompanyBaseSid(String ntp061AdrCompanyBaseSid) {
        ntp061AdrCompanyBaseSid__ = ntp061AdrCompanyBaseSid;
    }
    /**
     * <p>ntp061AdrCompanyName を取得します。
     * @return ntp061AdrCompanyName
     */
    public String getNtp061AdrCompanyName() {
        return ntp061AdrCompanyName__;
    }
    /**
     * <p>ntp061AdrCompanyName をセットします。
     * @param ntp061AdrCompanyName ntp061AdrCompanyName
     */
    public void setNtp061AdrCompanyName(String ntp061AdrCompanyName) {
        ntp061AdrCompanyName__ = ntp061AdrCompanyName;
    }
    /**
     * <p>ntp061AdrselectCompany を取得します。
     * @return ntp061AdrselectCompany
     */
    public String getNtp061AdrselectCompany() {
        return ntp061AdrselectCompany__;
    }
    /**
     * <p>ntp061AdrselectCompany をセットします。
     * @param ntp061AdrselectCompany ntp061AdrselectCompany
     */
    public void setNtp061AdrselectCompany(String ntp061AdrselectCompany) {
        ntp061AdrselectCompany__ = ntp061AdrselectCompany;
    }
    /**
     * <p>ntp061Adrpage を取得します。
     * @return ntp061Adrpage
     */
    public int getNtp061Adrpage() {
        return ntp061Adrpage__;
    }
    /**
     * <p>ntp061Adrpage をセットします。
     * @param ntp061Adrpage ntp061Adrpage
     */
    public void setNtp061Adrpage(int ntp061Adrpage) {
        ntp061Adrpage__ = ntp061Adrpage;
    }
    /**
     * <p>ntp061AdrpageTop を取得します。
     * @return ntp061AdrpageTop
     */
    public int getNtp061AdrpageTop() {
        return ntp061AdrpageTop__;
    }
    /**
     * <p>ntp061AdrpageTop をセットします。
     * @param ntp061AdrpageTop ntp061AdrpageTop
     */
    public void setNtp061AdrpageTop(int ntp061AdrpageTop) {
        ntp061AdrpageTop__ = ntp061AdrpageTop;
    }
    /**
     * <p>ntp061AdrpageBottom を取得します。
     * @return ntp061AdrpageBottom
     */
    public int getNtp061AdrpageBottom() {
        return ntp061AdrpageBottom__;
    }
    /**
     * <p>ntp061AdrpageBottom をセットします。
     * @param ntp061AdrpageBottom ntp061AdrpageBottom
     */
    public void setNtp061AdrpageBottom(int ntp061AdrpageBottom) {
        ntp061AdrpageBottom__ = ntp061AdrpageBottom;
    }
    /**
     * <p>ntp061Adrmode を取得します。
     * @return ntp061Adrmode
     */
    public int getNtp061Adrmode() {
        return ntp061Adrmode__;
    }
    /**
     * <p>ntp061Adrmode をセットします。
     * @param ntp061Adrmode ntp061Adrmode
     */
    public void setNtp061Adrmode(int ntp061Adrmode) {
        ntp061Adrmode__ = ntp061Adrmode;
    }
    /**
     * <p>ntp061AdrPrsMode を取得します。
     * @return ntp061AdrPrsMode
     */
    public int getNtp061AdrPrsMode() {
        return ntp061AdrPrsMode__;
    }
    /**
     * <p>ntp061AdrPrsMode をセットします。
     * @param ntp061AdrPrsMode ntp061AdrPrsMode
     */
    public void setNtp061AdrPrsMode(int ntp061AdrPrsMode) {
        ntp061AdrPrsMode__ = ntp061AdrPrsMode;
    }
    /**
     * <p>ntp061AdrRowNumber を取得します。
     * @return ntp061AdrRowNumber
     */
    public String getNtp061AdrRowNumber() {
        return ntp061AdrRowNumber__;
    }
    /**
     * <p>ntp061AdrRowNumber をセットします。
     * @param ntp061AdrRowNumber ntp061AdrRowNumber
     */
    public void setNtp061AdrRowNumber(String ntp061AdrRowNumber) {
        ntp061AdrRowNumber__ = ntp061AdrRowNumber;
    }
    /**
     * <p>ntp061AdrProAddFlg を取得します。
     * @return ntp061AdrProAddFlg
     */
    public int getNtp061AdrProAddFlg() {
        return ntp061AdrProAddFlg__;
    }
    /**
     * <p>ntp061AdrProAddFlg をセットします。
     * @param ntp061AdrProAddFlg ntp061AdrProAddFlg
     */
    public void setNtp061AdrProAddFlg(int ntp061AdrProAddFlg) {
        ntp061AdrProAddFlg__ = ntp061AdrProAddFlg;
    }
    /**
     * <p>ntp061AdrProAddErrFlg を取得します。
     * @return ntp061AdrProAddErrFlg
     */
    public int getNtp061AdrProAddErrFlg() {
        return ntp061AdrProAddErrFlg__;
    }
    /**
     * <p>ntp061AdrProAddErrFlg をセットします。
     * @param ntp061AdrProAddErrFlg ntp061AdrProAddErrFlg
     */
    public void setNtp061AdrProAddErrFlg(int ntp061AdrProAddErrFlg) {
        ntp061AdrProAddErrFlg__ = ntp061AdrProAddErrFlg;
    }
    /**
     * <p>pageCmbList を取得します。
     * @return pageCmbList
     */
    public List<LabelValueBean> getPageCmbList() {
        return pageCmbList__;
    }
    /**
     * <p>pageCmbList をセットします。
     * @param pageCmbList pageCmbList
     */
    public void setPageCmbList(List<LabelValueBean> pageCmbList) {
        pageCmbList__ = pageCmbList;
    }
    /**
     * <p>ntp061AdrKbn を取得します。
     * @return ntp061AdrKbn
     */
    public int getNtp061AdrKbn() {
        return ntp061AdrKbn__;
    }
    /**
     * <p>ntp061AdrKbn をセットします。
     * @param ntp061AdrKbn ntp061AdrKbn
     */
    public void setNtp061AdrKbn(int ntp061AdrKbn) {
        ntp061AdrKbn__ = ntp061AdrKbn;
    }
    /**
     * <p>ntp061ItmShohinList を取得します。
     * @return ntp061ItmShohinList
     */
    public ArrayList<Ntp130ShohinModel> getNtp061ItmShohinList() {
        return ntp061ItmShohinList__;
    }
    /**
     * <p>ntp061ItmShohinList をセットします。
     * @param ntp061ItmShohinList ntp061ItmShohinList
     */
    public void setNtp061ItmShohinList(
            ArrayList<Ntp130ShohinModel> ntp061ItmShohinList) {
        ntp061ItmShohinList__ = ntp061ItmShohinList;
    }
    /**
     * <p>ntp061ItmNhnSid を取得します。
     * @return ntp061ItmNhnSid
     */
    public int getNtp061ItmNhnSid() {
        return ntp061ItmNhnSid__;
    }
    /**
     * <p>ntp061ItmNhnSid をセットします。
     * @param ntp061ItmNhnSid ntp061ItmNhnSid
     */
    public void setNtp061ItmNhnSid(int ntp061ItmNhnSid) {
        ntp061ItmNhnSid__ = ntp061ItmNhnSid;
    }
    /**
     * <p>ntp061ItmProcMode を取得します。
     * @return ntp061ItmProcMode
     */
    public String getNtp061ItmProcMode() {
        return ntp061ItmProcMode__;
    }
    /**
     * <p>ntp061ItmProcMode をセットします。
     * @param ntp061ItmProcMode ntp061ItmProcMode
     */
    public void setNtp061ItmProcMode(String ntp061ItmProcMode) {
        ntp061ItmProcMode__ = ntp061ItmProcMode;
    }
    /**
     * <p>ntp061ItmSortList を取得します。
     * @return ntp061ItmSortList
     */
    public List<LabelValueBean> getNtp061ItmSortList() {
        return ntp061ItmSortList__;
    }
    /**
     * <p>ntp061ItmSortList をセットします。
     * @param ntp061ItmSortList ntp061ItmSortList
     */
    public void setNtp061ItmSortList(List<LabelValueBean> ntp061ItmSortList) {
        ntp061ItmSortList__ = ntp061ItmSortList;
    }
    /**
     * <p>ntp061ItmDspMode を取得します。
     * @return ntp061ItmDspMode
     */
    public String getNtp061ItmDspMode() {
        return ntp061ItmDspMode__;
    }
    /**
     * <p>ntp061ItmDspMode をセットします。
     * @param ntp061ItmDspMode ntp061ItmDspMode
     */
    public void setNtp061ItmDspMode(String ntp061ItmDspMode) {
        ntp061ItmDspMode__ = ntp061ItmDspMode;
    }
    /**
     * <p>ntp061ItmReturnPage を取得します。
     * @return ntp061ItmReturnPage
     */
    public String getNtp061ItmReturnPage() {
        return ntp061ItmReturnPage__;
    }
    /**
     * <p>ntp061ItmReturnPage をセットします。
     * @param ntp061ItmReturnPage ntp061ItmReturnPage
     */
    public void setNtp061ItmReturnPage(String ntp061ItmReturnPage) {
        ntp061ItmReturnPage__ = ntp061ItmReturnPage;
    }
    /**
     * <p>ntp061ItmInitFlg を取得します。
     * @return ntp061ItmInitFlg
     */
    public int getNtp061ItmInitFlg() {
        return ntp061ItmInitFlg__;
    }
    /**
     * <p>ntp061ItmInitFlg をセットします。
     * @param ntp061ItmInitFlg ntp061ItmInitFlg
     */
    public void setNtp061ItmInitFlg(int ntp061ItmInitFlg) {
        ntp061ItmInitFlg__ = ntp061ItmInitFlg;
    }
    /**
     * <p>ntp061ItmNhnCode を取得します。
     * @return ntp061ItmNhnCode
     */
    public String getNtp061ItmNhnCode() {
        return ntp061ItmNhnCode__;
    }
    /**
     * <p>ntp061ItmNhnCode をセットします。
     * @param ntp061ItmNhnCode ntp061ItmNhnCode
     */
    public void setNtp061ItmNhnCode(String ntp061ItmNhnCode) {
        ntp061ItmNhnCode__ = ntp061ItmNhnCode;
    }
    /**
     * <p>ntp061ItmNhnName を取得します。
     * @return ntp061ItmNhnName
     */
    public String getNtp061ItmNhnName() {
        return ntp061ItmNhnName__;
    }
    /**
     * <p>ntp061ItmNhnName をセットします。
     * @param ntp061ItmNhnName ntp061ItmNhnName
     */
    public void setNtp061ItmNhnName(String ntp061ItmNhnName) {
        ntp061ItmNhnName__ = ntp061ItmNhnName;
    }
    /**
     * <p>ntp061ItmNhnPriceSale を取得します。
     * @return ntp061ItmNhnPriceSale
     */
    public String getNtp061ItmNhnPriceSale() {
        return ntp061ItmNhnPriceSale__;
    }
    /**
     * <p>ntp061ItmNhnPriceSale をセットします。
     * @param ntp061ItmNhnPriceSale ntp061ItmNhnPriceSale
     */
    public void setNtp061ItmNhnPriceSale(String ntp061ItmNhnPriceSale) {
        ntp061ItmNhnPriceSale__ = ntp061ItmNhnPriceSale;
    }
    /**
     * <p>ntp061ItmNhnPriceCost を取得します。
     * @return ntp061ItmNhnPriceCost
     */
    public String getNtp061ItmNhnPriceCost() {
        return ntp061ItmNhnPriceCost__;
    }
    /**
     * <p>ntp061ItmNhnPriceCost をセットします。
     * @param ntp061ItmNhnPriceCost ntp061ItmNhnPriceCost
     */
    public void setNtp061ItmNhnPriceCost(String ntp061ItmNhnPriceCost) {
        ntp061ItmNhnPriceCost__ = ntp061ItmNhnPriceCost;
    }
    /**
     * <p>ntp061ItmNhnPriceSaleKbn を取得します。
     * @return ntp061ItmNhnPriceSaleKbn
     */
    public int getNtp061ItmNhnPriceSaleKbn() {
        return ntp061ItmNhnPriceSaleKbn__;
    }
    /**
     * <p>ntp061ItmNhnPriceSaleKbn をセットします。
     * @param ntp061ItmNhnPriceSaleKbn ntp061ItmNhnPriceSaleKbn
     */
    public void setNtp061ItmNhnPriceSaleKbn(int ntp061ItmNhnPriceSaleKbn) {
        ntp061ItmNhnPriceSaleKbn__ = ntp061ItmNhnPriceSaleKbn;
    }
    /**
     * <p>ntp061ItmNhnPriceCostKbn を取得します。
     * @return ntp061ItmNhnPriceCostKbn
     */
    public int getNtp061ItmNhnPriceCostKbn() {
        return ntp061ItmNhnPriceCostKbn__;
    }
    /**
     * <p>ntp061ItmNhnPriceCostKbn をセットします。
     * @param ntp061ItmNhnPriceCostKbn ntp061ItmNhnPriceCostKbn
     */
    public void setNtp061ItmNhnPriceCostKbn(int ntp061ItmNhnPriceCostKbn) {
        ntp061ItmNhnPriceCostKbn__ = ntp061ItmNhnPriceCostKbn;
    }
    /**
     * <p>ntp061ItmSortKey1 を取得します。
     * @return ntp061ItmSortKey1
     */
    public int getNtp061ItmSortKey1() {
        return ntp061ItmSortKey1__;
    }
    /**
     * <p>ntp061ItmSortKey1 をセットします。
     * @param ntp061ItmSortKey1 ntp061ItmSortKey1
     */
    public void setNtp061ItmSortKey1(int ntp061ItmSortKey1) {
        ntp061ItmSortKey1__ = ntp061ItmSortKey1;
    }
    /**
     * <p>ntp061ItmOrderKey1 を取得します。
     * @return ntp061ItmOrderKey1
     */
    public int getNtp061ItmOrderKey1() {
        return ntp061ItmOrderKey1__;
    }
    /**
     * <p>ntp061ItmOrderKey1 をセットします。
     * @param ntp061ItmOrderKey1 ntp061ItmOrderKey1
     */
    public void setNtp061ItmOrderKey1(int ntp061ItmOrderKey1) {
        ntp061ItmOrderKey1__ = ntp061ItmOrderKey1;
    }
    /**
     * <p>ntp061ItmSortKey2 を取得します。
     * @return ntp061ItmSortKey2
     */
    public int getNtp061ItmSortKey2() {
        return ntp061ItmSortKey2__;
    }
    /**
     * <p>ntp061ItmSortKey2 をセットします。
     * @param ntp061ItmSortKey2 ntp061ItmSortKey2
     */
    public void setNtp061ItmSortKey2(int ntp061ItmSortKey2) {
        ntp061ItmSortKey2__ = ntp061ItmSortKey2;
    }
    /**
     * <p>ntp061ItmOrderKey2 を取得します。
     * @return ntp061ItmOrderKey2
     */
    public int getNtp061ItmOrderKey2() {
        return ntp061ItmOrderKey2__;
    }
    /**
     * <p>ntp061ItmOrderKey2 をセットします。
     * @param ntp061ItmOrderKey2 ntp061ItmOrderKey2
     */
    public void setNtp061ItmOrderKey2(int ntp061ItmOrderKey2) {
        ntp061ItmOrderKey2__ = ntp061ItmOrderKey2;
    }
    /**
     * <p>ntp061ItmPage を取得します。
     * @return ntp061ItmPage
     */
    public int getNtp061ItmPage() {
        return ntp061ItmPage__;
    }
    /**
     * <p>ntp061ItmPage をセットします。
     * @param ntp061ItmPage ntp061ItmPage
     */
    public void setNtp061ItmPage(int ntp061ItmPage) {
        ntp061ItmPage__ = ntp061ItmPage;
    }
    /**
     * <p>ntp061ItmPageTop を取得します。
     * @return ntp061ItmPageTop
     */
    public int getNtp061ItmPageTop() {
        return ntp061ItmPageTop__;
    }
    /**
     * <p>ntp061ItmPageTop をセットします。
     * @param ntp061ItmPageTop ntp061ItmPageTop
     */
    public void setNtp061ItmPageTop(int ntp061ItmPageTop) {
        ntp061ItmPageTop__ = ntp061ItmPageTop;
    }
    /**
     * <p>ntp061ItmPageBottom を取得します。
     * @return ntp061ItmPageBottom
     */
    public int getNtp061ItmPageBottom() {
        return ntp061ItmPageBottom__;
    }
    /**
     * <p>ntp061ItmPageBottom をセットします。
     * @param ntp061ItmPageBottom ntp061ItmPageBottom
     */
    public void setNtp061ItmPageBottom(int ntp061ItmPageBottom) {
        ntp061ItmPageBottom__ = ntp061ItmPageBottom;
    }
    /**
     * <p>ntp061ItmPageCmbList を取得します。
     * @return ntp061ItmPageCmbList
     */
    public List<LabelValueBean> getNtp061ItmPageCmbList() {
        return ntp061ItmPageCmbList__;
    }
    /**
     * <p>ntp061ItmPageCmbList をセットします。
     * @param ntp061ItmPageCmbList ntp061ItmPageCmbList
     */
    public void setNtp061ItmPageCmbList(List<LabelValueBean> ntp061ItmPageCmbList) {
        ntp061ItmPageCmbList__ = ntp061ItmPageCmbList;
    }
    /**
     * <p>ntp061ItmSelectedSid を取得します。
     * @return ntp061ItmSelectedSid
     */
    public ArrayList<String> getNtp061ItmSelectedSid() {
        return ntp061ItmSelectedSid__;
    }
    /**
     * <p>ntp061ItmSelectedSid をセットします。
     * @param ntp061ItmSelectedSid ntp061ItmSelectedSid
     */
    public void setNtp061ItmSelectedSid(ArrayList<String> ntp061ItmSelectedSid) {
        ntp061ItmSelectedSid__ = ntp061ItmSelectedSid;
    }
    /**
     * <p>ntp061ItmChkShohinSidList を取得します。
     * @return ntp061ItmChkShohinSidList
     */
    public String[] getNtp061ItmChkShohinSidList() {
        return ntp061ItmChkShohinSidList__;
    }
    /**
     * <p>ntp061ItmChkShohinSidList をセットします。
     * @param ntp061ItmChkShohinSidList ntp061ItmChkShohinSidList
     */
    public void setNtp061ItmChkShohinSidList(String[] ntp061ItmChkShohinSidList) {
        ntp061ItmChkShohinSidList__ = ntp061ItmChkShohinSidList;
    }
    /**
     * <p>ntp061ItmSvChkShohinSidList を取得します。
     * @return ntp061ItmSvChkShohinSidList
     */
    public String[] getNtp061ItmSvChkShohinSidList() {
        return ntp061ItmSvChkShohinSidList__;
    }
    /**
     * <p>ntp061ItmSvChkShohinSidList をセットします。
     * @param ntp061ItmSvChkShohinSidList ntp061ItmSvChkShohinSidList
     */
    public void setNtp061ItmSvChkShohinSidList(String[] ntp061ItmSvChkShohinSidList) {
        ntp061ItmSvChkShohinSidList__ = ntp061ItmSvChkShohinSidList;
    }
    /**
     * <p>ntp061ItmKbn を取得します。
     * @return ntp061ItmKbn
     */
    public int getNtp061ItmKbn() {
        return ntp061ItmKbn__;
    }
    /**
     * <p>ntp061ItmKbn をセットします。
     * @param ntp061ItmKbn ntp061ItmKbn
     */
    public void setNtp061ItmKbn(int ntp061ItmKbn) {
        ntp061ItmKbn__ = ntp061ItmKbn;
    }
    /**
     * <p>ntp061AddFlg を取得します。
     * @return ntp061AddFlg
     */
    public int getNtp061AddFlg() {
        return ntp061AddFlg__;
    }
    /**
     * <p>ntp061AddFlg をセットします。
     * @param ntp061AddFlg ntp061AddFlg
     */
    public void setNtp061AddFlg(int ntp061AddFlg) {
        ntp061AddFlg__ = ntp061AddFlg;
    }
    /**
     * <p>ntp061AddCompFlg を取得します。
     * @return ntp061AddCompFlg
     */
    public int getNtp061AddCompFlg() {
        return ntp061AddCompFlg__;
    }
    /**
     * <p>ntp061AddCompFlg をセットします。
     * @param ntp061AddCompFlg ntp061AddCompFlg
     */
    public void setNtp061AddCompFlg(int ntp061AddCompFlg) {
        ntp061AddCompFlg__ = ntp061AddCompFlg;
    }
    /**
     * <p>ntp061AnkenSid を取得します。
     * @return ntp061AnkenSid
     */
    public int getNtp061AnkenSid() {
        return ntp061AnkenSid__;
    }
    /**
     * <p>ntp061AnkenSid をセットします。
     * @param ntp061AnkenSid ntp061AnkenSid
     */
    public void setNtp061AnkenSid(int ntp061AnkenSid) {
        ntp061AnkenSid__ = ntp061AnkenSid;
    }
    /**
     * <p>ntp061SvCompanyCode を取得します。
     * @return ntp061SvCompanyCode
     */
    public String getNtp061SvCompanyCode() {
        return ntp061SvCompanyCode__;
    }
    /**
     * <p>ntp061SvCompanyCode をセットします。
     * @param ntp061SvCompanyCode ntp061SvCompanyCode
     */
    public void setNtp061SvCompanyCode(String ntp061SvCompanyCode) {
        ntp061SvCompanyCode__ = ntp061SvCompanyCode;
    }
    /**
     * <p>ntp061SvCompanyName を取得します。
     * @return ntp061SvCompanyName
     */
    public String getNtp061SvCompanyName() {
        return ntp061SvCompanyName__;
    }
    /**
     * <p>ntp061SvCompanyName をセットします。
     * @param ntp061SvCompanyName ntp061SvCompanyName
     */
    public void setNtp061SvCompanyName(String ntp061SvCompanyName) {
        ntp061SvCompanyName__ = ntp061SvCompanyName;
    }
    /**
     * <p>ntp061SvCompanyBaseSid を取得します。
     * @return ntp061SvCompanyBaseSid
     */
    public String getNtp061SvCompanyBaseSid() {
        return ntp061SvCompanyBaseSid__;
    }
    /**
     * <p>ntp061SvCompanyBaseSid をセットします。
     * @param ntp061SvCompanyBaseSid ntp061SvCompanyBaseSid
     */
    public void setNtp061SvCompanyBaseSid(String ntp061SvCompanyBaseSid) {
        ntp061SvCompanyBaseSid__ = ntp061SvCompanyBaseSid;
    }
    /**
     * <p>ntp061SvCompanyBaseName を取得します。
     * @return ntp061SvCompanyBaseName
     */
    public String getNtp061SvCompanyBaseName() {
        return ntp061SvCompanyBaseName__;
    }
    /**
     * <p>ntp061SvCompanyBaseName をセットします。
     * @param ntp061SvCompanyBaseName ntp061SvCompanyBaseName
     */
    public void setNtp061SvCompanyBaseName(String ntp061SvCompanyBaseName) {
        ntp061SvCompanyBaseName__ = ntp061SvCompanyBaseName;
    }
    /**
     * <p>ntp061SvCompanySid を取得します。
     * @return ntp061SvCompanySid
     */
    public String getNtp061SvCompanySid() {
        return ntp061SvCompanySid__;
    }
    /**
     * <p>ntp061SvCompanySid をセットします。
     * @param ntp061SvCompanySid ntp061SvCompanySid
     */
    public void setNtp061SvCompanySid(String ntp061SvCompanySid) {
        ntp061SvCompanySid__ = ntp061SvCompanySid;
    }
    /**
     * <p>ntp061Date を取得します。
     * @return ntp061Date
     */
    public String getNtp061Date() {
        return ntp061Date__;
    }
    /**
     * <p>ntp061Date をセットします。
     * @param ntp061Date ntp061Date
     */
    public void setNtp061Date(String ntp061Date) {
        ntp061Date__ = ntp061Date;
    }

    /**
     * <p>ntp061SearchMode を取得します。
     * @return ntp061SearchMode
     */
    public int getNtp061SearchMode() {
        return ntp061SearchMode__;
    }
    /**
     * <p>ntp061SearchMode をセットします。
     * @param ntp061SearchMode ntp061SearchMode
     */
    public void setNtp061SearchMode(int ntp061SearchMode) {
        ntp061SearchMode__ = ntp061SearchMode;
    }
    /**
     * <p>ntp061searchFlg を取得します。
     * @return ntp061searchFlg
     */
    public int getNtp061searchFlg() {
        return ntp061searchFlg__;
    }
    /**
     * <p>ntp061searchFlg をセットします。
     * @param ntp061searchFlg ntp061searchFlg
     */
    public void setNtp061searchFlg(int ntp061searchFlg) {
        ntp061searchFlg__ = ntp061searchFlg;
    }
    /**
     * <p>ntp061svAdrCode を取得します。
     * @return ntp061svAdrCode
     */
    public String getNtp061svAdrCode() {
        return ntp061svAdrCode__;
    }
    /**
     * <p>ntp061svAdrCode をセットします。
     * @param ntp061svAdrCode ntp061svAdrCode
     */
    public void setNtp061svAdrCode(String ntp061svAdrCode) {
        ntp061svAdrCode__ = ntp061svAdrCode;
    }
    /**
     * <p>ntp061svAdrCoName を取得します。
     * @return ntp061svAdrCoName
     */
    public String getNtp061svAdrCoName() {
        return ntp061svAdrCoName__;
    }
    /**
     * <p>ntp061svAdrCoName をセットします。
     * @param ntp061svAdrCoName ntp061svAdrCoName
     */
    public void setNtp061svAdrCoName(String ntp061svAdrCoName) {
        ntp061svAdrCoName__ = ntp061svAdrCoName;
    }
    /**
     * <p>ntp061svAdrTdfk を取得します。
     * @return ntp061svAdrTdfk
     */
    public int getNtp061svAdrTdfk() {
        return ntp061svAdrTdfk__;
    }
    /**
     * <p>ntp061svAdrTdfk をセットします。
     * @param ntp061svAdrTdfk ntp061svAdrTdfk
     */
    public void setNtp061svAdrTdfk(int ntp061svAdrTdfk) {
        ntp061svAdrTdfk__ = ntp061svAdrTdfk;
    }
    /**
     * <p>ntp061svAdrBiko を取得します。
     * @return ntp061svAdrBiko
     */
    public String getNtp061svAdrBiko() {
        return ntp061svAdrBiko__;
    }
    /**
     * <p>ntp061svAdrBiko をセットします。
     * @param ntp061svAdrBiko ntp061svAdrBiko
     */
    public void setNtp061svAdrBiko(String ntp061svAdrBiko) {
        ntp061svAdrBiko__ = ntp061svAdrBiko;
    }
    /**
     * <p>ntp061AdrSortKey を取得します。
     * @return ntp061AdrSortKey
     */
    public int getNtp061AdrSortKey() {
        return ntp061AdrSortKey__;
    }
    /**
     * <p>ntp061AdrSortKey をセットします。
     * @param ntp061AdrSortKey ntp061AdrSortKey
     */
    public void setNtp061AdrSortKey(int ntp061AdrSortKey) {
        ntp061AdrSortKey__ = ntp061AdrSortKey;
    }
    /**
     * <p>ntp061AdrOrderKey を取得します。
     * @return ntp061AdrOrderKey
     */
    public int getNtp061AdrOrderKey() {
        return ntp061AdrOrderKey__;
    }
    /**
     * <p>ntp061AdrOrderKey をセットします。
     * @param ntp061AdrOrderKey ntp061AdrOrderKey
     */
    public void setNtp061AdrOrderKey(int ntp061AdrOrderKey) {
        ntp061AdrOrderKey__ = ntp061AdrOrderKey;
    }
    /**
     * <p>ntp061svAdrCoNameKn を取得します。
     * @return ntp061svAdrCoNameKn
     */
    public String getNtp061svAdrCoNameKn() {
        return ntp061svAdrCoNameKn__;
    }
    /**
     * <p>ntp061svAdrCoNameKn をセットします。
     * @param ntp061svAdrCoNameKn ntp061svAdrCoNameKn
     */
    public void setNtp061svAdrCoNameKn(String ntp061svAdrCoNameKn) {
        ntp061svAdrCoNameKn__ = ntp061svAdrCoNameKn;
    }
    /**
     * <p>ntp061svAdrCoBaseName を取得します。
     * @return ntp061svAdrCoBaseName
     */
    public String getNtp061svAdrCoBaseName() {
        return ntp061svAdrCoBaseName__;
    }
    /**
     * <p>ntp061svAdrCoBaseName をセットします。
     * @param ntp061svAdrCoBaseName ntp061svAdrCoBaseName
     */
    public void setNtp061svAdrCoBaseName(String ntp061svAdrCoBaseName) {
        ntp061svAdrCoBaseName__ = ntp061svAdrCoBaseName;
    }
    /**
     * <p>ntp061svAdrAtiSid を取得します。
     * @return ntp061svAdrAtiSid
     */
    public int getNtp061svAdrAtiSid() {
        return ntp061svAdrAtiSid__;
    }
    /**
     * <p>ntp061svAdrAtiSid をセットします。
     * @param ntp061svAdrAtiSid ntp061svAdrAtiSid
     */
    public void setNtp061svAdrAtiSid(int ntp061svAdrAtiSid) {
        ntp061svAdrAtiSid__ = ntp061svAdrAtiSid;
    }
    /**
     * <p>ntp061code を取得します。
     * @return ntp061code
     */
    public String getNtp061code() {
        return ntp061code__;
    }
    /**
     * <p>ntp061code をセットします。
     * @param ntp061code ntp061code
     */
    public void setNtp061code(String ntp061code) {
        ntp061code__ = ntp061code;
    }
    /**
     * <p>ntp061coName を取得します。
     * @return ntp061coName
     */
    public String getNtp061coName() {
        return ntp061coName__;
    }
    /**
     * <p>ntp061coName をセットします。
     * @param ntp061coName ntp061coName
     */
    public void setNtp061coName(String ntp061coName) {
        ntp061coName__ = ntp061coName;
    }
    /**
     * <p>ntp061coNameKn を取得します。
     * @return ntp061coNameKn
     */
    public String getNtp061coNameKn() {
        return ntp061coNameKn__;
    }
    /**
     * <p>ntp061coNameKn をセットします。
     * @param ntp061coNameKn ntp061coNameKn
     */
    public void setNtp061coNameKn(String ntp061coNameKn) {
        ntp061coNameKn__ = ntp061coNameKn;
    }
    /**
     * <p>ntp061coBaseName を取得します。
     * @return ntp061coBaseName
     */
    public String getNtp061coBaseName() {
        return ntp061coBaseName__;
    }
    /**
     * <p>ntp061coBaseName をセットします。
     * @param ntp061coBaseName ntp061coBaseName
     */
    public void setNtp061coBaseName(String ntp061coBaseName) {
        ntp061coBaseName__ = ntp061coBaseName;
    }
    /**
     * <p>ntp061atiSid を取得します。
     * @return ntp061atiSid
     */
    public int getNtp061atiSid() {
        return ntp061atiSid__;
    }
    /**
     * <p>ntp061atiSid をセットします。
     * @param ntp061atiSid ntp061atiSid
     */
    public void setNtp061atiSid(int ntp061atiSid) {
        ntp061atiSid__ = ntp061atiSid;
    }
    /**
     * <p>ntp061tdfk を取得します。
     * @return ntp061tdfk
     */
    public int getNtp061tdfk() {
        return ntp061tdfk__;
    }
    /**
     * <p>ntp061tdfk をセットします。
     * @param ntp061tdfk ntp061tdfk
     */
    public void setNtp061tdfk(int ntp061tdfk) {
        ntp061tdfk__ = ntp061tdfk;
    }
    /**
     * <p>ntp061biko を取得します。
     * @return ntp061biko
     */
    public String getNtp061biko() {
        return ntp061biko__;
    }
    /**
     * <p>ntp061biko をセットします。
     * @param ntp061biko ntp061biko
     */
    public void setNtp061biko(String ntp061biko) {
        ntp061biko__ = ntp061biko;
    }

    /**
     * <p>atiCmbList を取得します。
     * @return atiCmbList
     */
    public List<LabelValueBean> getAtiCmbList() {
        return atiCmbList__;
    }
    /**
     * <p>atiCmbList をセットします。
     * @param atiCmbList atiCmbList
     */
    public void setAtiCmbList(List<LabelValueBean> atiCmbList) {
        atiCmbList__ = atiCmbList;
    }
    /**
     * <p>tdfkCmbList を取得します。
     * @return tdfkCmbList
     */
    public List<LabelValueBean> getTdfkCmbList() {
        return tdfkCmbList__;
    }
    /**
     * <p>tdfkCmbList をセットします。
     * @param tdfkCmbList tdfkCmbList
     */
    public void setTdfkCmbList(List<LabelValueBean> tdfkCmbList) {
        tdfkCmbList__ = tdfkCmbList;
    }
    /**
     * <p>ntp061NanState を取得します。
     * @return ntp061NanState
     */
    public int getNtp061NanState() {
        return ntp061NanState__;
    }
    /**
     * <p>ntp061NanState をセットします。
     * @param ntp061NanState ntp061NanState
     */
    public void setNtp061NanState(int ntp061NanState) {
        ntp061NanState__ = ntp061NanState;
    }
    /**
     * <p>ntp061GroupSid を取得します。
     * @return ntp061GroupSid
     */
    public String getNtp061GroupSid() {
        return ntp061GroupSid__;
    }
    /**
     * <p>ntp061GroupSid をセットします。
     * @param ntp061GroupSid ntp061GroupSid
     */
    public void setNtp061GroupSid(String ntp061GroupSid) {
        ntp061GroupSid__ = ntp061GroupSid;
    }
    /**
     * <p>sv_users を取得します。
     * @return sv_users
     */
    public String[] getSv_users() {
        return sv_users__;
    }
    /**
     * <p>sv_users をセットします。
     * @param svUsers sv_users
     */
    public void setSv_users(String[] svUsers) {
        sv_users__ = svUsers;
    }
    /**
     * <p>tantoListUI を取得します。
     * @return tantoListUI
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#tantoListUI__
     */
    public AnkenTantoSelector getTantoListUI() {
        return tantoListUI__;
    }
    /**
     * <p>tantoListUI をセットします。
     * @param tantoListUI tantoListUI
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#tantoListUI__
     */
    public void setTantoListUI(AnkenTantoSelector tantoListUI) {
        tantoListUI__ = tantoListUI;
    }
    /**
     * <p>ntp061MikomidoFlg を取得します。
     * @return ntp061MikomidoFlg
     */
    public int getNtp061MikomidoFlg() {
        return ntp061MikomidoFlg__;
    }
    /**
     * <p>ntp061MikomidoFlg をセットします。
     * @param ntp061MikomidoFlg ntp061MikomidoFlg
     */
    public void setNtp061MikomidoFlg(int ntp061MikomidoFlg) {
        ntp061MikomidoFlg__ = ntp061MikomidoFlg;
    }
    /**
     * <p>ntp061MikomidoMsgList を取得します。
     * @return ntp061MikomidoMsgList
     */
    public ArrayList<NtpMikomidoMsgModel> getNtp061MikomidoMsgList() {
        return ntp061MikomidoMsgList__;
    }
    /**
     * <p>ntp061MikomidoMsgList をセットします。
     * @param ntp061MikomidoMsgList ntp061MikomidoMsgList
     */
    public void setNtp061MikomidoMsgList(
            ArrayList<NtpMikomidoMsgModel> ntp061MikomidoMsgList) {
        ntp061MikomidoMsgList__ = ntp061MikomidoMsgList;
    }
    /**
     * <p>ntp061MitumoriYear を取得します。
     * @return ntp061MitumoriYear
     */
    public String getNtp061MitumoriYear() {
        return ntp061MitumoriYear__;
    }
    /**
     * <p>ntp061MitumoriYear をセットします。
     * @param ntp061MitumoriYear ntp061MitumoriYear
     */
    public void setNtp061MitumoriYear(String ntp061MitumoriYear) {
        ntp061MitumoriYear__ = ntp061MitumoriYear;
    }
    /**
     * <p>ntp061MitumoriMonth を取得します。
     * @return ntp061MitumoriMonth
     */
    public String getNtp061MitumoriMonth() {
        return ntp061MitumoriMonth__;
    }
    /**
     * <p>ntp061MitumoriMonth をセットします。
     * @param ntp061MitumoriMonth ntp061MitumoriMonth
     */
    public void setNtp061MitumoriMonth(String ntp061MitumoriMonth) {
        ntp061MitumoriMonth__ = ntp061MitumoriMonth;
    }
    /**
     * <p>ntp061MitumoriDay を取得します。
     * @return ntp061MitumoriDay
     */
    public String getNtp061MitumoriDay() {
        return ntp061MitumoriDay__;
    }
    /**
     * <p>ntp061MitumoriDay をセットします。
     * @param ntp061MitumoriDay ntp061MitumoriDay
     */
    public void setNtp061MitumoriDay(String ntp061MitumoriDay) {
        ntp061MitumoriDay__ = ntp061MitumoriDay;
    }
    /**
     * <p>ntp061MitumoriDate を取得します。
     * @return ntp061MitumoriDate
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061MitumoriDate__
     */
    public String getNtp061MitumoriDate() {
        return ntp061MitumoriDate__;
    }
    /**
     * <p>ntp061MitumoriDate をセットします。
     * @param ntp061MitumoriDate ntp061MitumoriDate
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061MitumoriDate__
     */
    public void setNtp061MitumoriDate(String ntp061MitumoriDate) {
        ntp061MitumoriDate__ = ntp061MitumoriDate;
    }
    /**
     * <p>ntp061JutyuYear を取得します。
     * @return ntp061JutyuYear
     */
    public String getNtp061JutyuYear() {
        return ntp061JutyuYear__;
    }
    /**
     * <p>ntp061JutyuYear をセットします。
     * @param ntp061JutyuYear ntp061JutyuYear
     */
    public void setNtp061JutyuYear(String ntp061JutyuYear) {
        ntp061JutyuYear__ = ntp061JutyuYear;
    }
    /**
     * <p>ntp061JutyuMonth を取得します。
     * @return ntp061JutyuMonth
     */
    public String getNtp061JutyuMonth() {
        return ntp061JutyuMonth__;
    }
    /**
     * <p>ntp061JutyuMonth をセットします。
     * @param ntp061JutyuMonth ntp061JutyuMonth
     */
    public void setNtp061JutyuMonth(String ntp061JutyuMonth) {
        ntp061JutyuMonth__ = ntp061JutyuMonth;
    }
    /**
     * <p>ntp061JutyuDay を取得します。
     * @return ntp061JutyuDay
     */
    public String getNtp061JutyuDay() {
        return ntp061JutyuDay__;
    }
    /**
     * <p>ntp061JutyuDay をセットします。
     * @param ntp061JutyuDay ntp061JutyuDay
     */
    public void setNtp061JutyuDay(String ntp061JutyuDay) {
        ntp061JutyuDay__ = ntp061JutyuDay;
    }
    /**
     * <p>ntp061JutyuDate を取得します。
     * @return ntp061JutyuDate
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061JutyuDate__
     */
    public String getNtp061JutyuDate() {
        return ntp061JutyuDate__;
    }
    /**
     * <p>ntp061JutyuDate をセットします。
     * @param ntp061JutyuDate ntp061JutyuDate
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061JutyuDate__
     */
    public void setNtp061JutyuDate(String ntp061JutyuDate) {
        ntp061JutyuDate__ = ntp061JutyuDate;
    }
    /**
     * <p>ntp061YearLabel を取得します。
     * @return ntp061YearLabel
     */
    public List<LabelValueBean> getNtp061YearLabel() {
        return ntp061YearLabel__;
    }
    /**
     * <p>ntp061YearLabel をセットします。
     * @param ntp061YearLabel ntp061YearLabel
     */
    public void setNtp061YearLabel(List<LabelValueBean> ntp061YearLabel) {
        ntp061YearLabel__ = ntp061YearLabel;
    }
    /**
     * <p>ntp061MonthLabel を取得します。
     * @return ntp061MonthLabel
     */
    public List<LabelValueBean> getNtp061MonthLabel() {
        return ntp061MonthLabel__;
    }
    /**
     * <p>ntp061MonthLabel をセットします。
     * @param ntp061MonthLabel ntp061MonthLabel
     */
    public void setNtp061MonthLabel(List<LabelValueBean> ntp061MonthLabel) {
        ntp061MonthLabel__ = ntp061MonthLabel;
    }
    /**
     * <p>ntp061DayLabel を取得します。
     * @return ntp061DayLabel
     */
    public List<LabelValueBean> getNtp061DayLabel() {
        return ntp061DayLabel__;
    }
    /**
     * <p>ntp061DayLabel をセットします。
     * @param ntp061DayLabel ntp061DayLabel
     */
    public void setNtp061DayLabel(List<LabelValueBean> ntp061DayLabel) {
        ntp061DayLabel__ = ntp061DayLabel;
    }
    /**
     * <p>ntp061CopyFlg を取得します。
     * @return ntp061CopyFlg
     */
    public int getNtp061CopyFlg() {
        return ntp061CopyFlg__;
    }
    /**
     * <p>ntp061CopyFlg をセットします。
     * @param ntp061CopyFlg ntp061CopyFlg
     */
    public void setNtp061CopyFlg(int ntp061CopyFlg) {
        ntp061CopyFlg__ = ntp061CopyFlg;
    }
    /**
     * <p>ntp061CategoryList を取得します。
     * @return ntp061CategoryList
     */
    public List<LabelValueBean> getNtp061CategoryList() {
        return ntp061CategoryList__;
    }
    /**
     * <p>ntp061CategoryList をセットします。
     * @param ntp061CategoryList ntp061CategoryList
     */
    public void setNtp061CategoryList(List<LabelValueBean> ntp061CategoryList) {
        ntp061CategoryList__ = ntp061CategoryList;
    }
    /**
     * <p>ntp061CatSid を取得します。
     * @return ntp061CatSid
     */
    public int getNtp061CatSid() {
        return ntp061CatSid__;
    }
    /**
     * <p>ntp061CatSid をセットします。
     * @param ntp061CatSid ntp061CatSid
     */
    public void setNtp061CatSid(int ntp061CatSid) {
        ntp061CatSid__ = ntp061CatSid;
    }
    /**
     * <p>ntp061TourokuUsrUkoFlg を取得します。
     * @return ntp061TourokuUsrUkoFlg
     */
    public int getNtp061TourokuUsrUkoFlg() {
        return ntp061TourokuUsrUkoFlg__;
    }
    /**
     * <p>ntp061TourokuUsrUkoFlg をセットします。
     * @param ntp061TourokuUsrUkoFlg ntp061TourokuUsrUkoFlg
     */
    public void setNtp061TourokuUsrUkoFlg(int ntp061TourokuUsrUkoFlg) {
        ntp061TourokuUsrUkoFlg__ = ntp061TourokuUsrUkoFlg;
    }
    /**
     * <p>ntp061NanPermitView を取得します。
     * @return ntp061NanPermitView
     */
    public int getNtp061NanPermitView() {
        return ntp061NanPermitView__;
    }
    /**
     * <p>ntp061NanPermitView をセットします。
     * @param ntp061NanPermitView ntp061NanPermitView
     */
    public void setNtp061NanPermitView(int ntp061NanPermitView) {
        ntp061NanPermitView__ = ntp061NanPermitView;
    }
    /**
     * <p>ntp061NanPermitEdit を取得します。
     * @return ntp061NanPermitEdit
     */
    public int getNtp061NanPermitEdit() {
        return ntp061NanPermitEdit__;
    }
    /**
     * <p>ntp061NanPermitEdit をセットします。
     * @param ntp061NanPermitEdit ntp061NanPermitEdit
     */
    public void setNtp061NanPermitEdit(int ntp061NanPermitEdit) {
        ntp061NanPermitEdit__ = ntp061NanPermitEdit;
    }
    /**
     * <p>ntp061NanPermitGroup を取得します。
     * @return ntp061NanPermitGroup
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitGroup__
     */
    public String getNtp061NanPermitGroup() {
        return ntp061NanPermitGroup__;
    }
    /**
     * <p>ntp061NanPermitGroup をセットします。
     * @param ntp061NanPermitGroup ntp061NanPermitGroup
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitGroup__
     */
    public void setNtp061NanPermitGroup(String ntp061NanPermitGroup) {
        ntp061NanPermitGroup__ = ntp061NanPermitGroup;
    }
    /**
     * <p>ntp061NanPermitUserView を取得します。
     * @return ntp061NanPermitUserView
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitUserView__
     */
    public String[] getNtp061NanPermitUserView() {
        return ntp061NanPermitUserView__;
    }
    /**
     * <p>ntp061NanPermitUserView をセットします。
     * @param ntp061NanPermitUserView ntp061NanPermitUserView
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitUserView__
     */
    public void setNtp061NanPermitUserView(String[] ntp061NanPermitUserView) {
        ntp061NanPermitUserView__ = ntp061NanPermitUserView;
    }
    /**
     * <p>ntp061NanPermitUserEdit を取得します。
     * @return ntp061NanPermitUserEdit
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitUserEdit__
     */
    public String[] getNtp061NanPermitUserEdit() {
        return ntp061NanPermitUserEdit__;
    }
    /**
     * <p>ntp061NanPermitUserEdit をセットします。
     * @param ntp061NanPermitUserEdit ntp061NanPermitUserEdit
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitUserEdit__
     */
    public void setNtp061NanPermitUserEdit(String[] ntp061NanPermitUserEdit) {
        ntp061NanPermitUserEdit__ = ntp061NanPermitUserEdit;
    }
    /**
     * <p>ntp061NanPermitEditUser を取得します。
     * @return ntp061NanPermitEditUser
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitEditUser__
     */
    public String[] getNtp061NanPermitEditUser() {
        return ntp061NanPermitEditUser__;
    }
    /**
     * <p>ntp061NanPermitEditUser をセットします。
     * @param ntp061NanPermitEditUser ntp061NanPermitEditUser
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitEditUser__
     */
    public void setNtp061NanPermitEditUser(String[] ntp061NanPermitEditUser) {
        ntp061NanPermitEditUser__ = ntp061NanPermitEditUser;
    }
    /**
     * <p>ntp061NanPermitUI を取得します。
     * @return ntp061NanPermitUI
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitUI__
     */
    public AnkenPermitSelector getNtp061NanPermitUI() {
        return ntp061NanPermitUI__;
    }
    /**
     * <p>ntp061NanPermitUI をセットします。
     * @param ntp061NanPermitUI ntp061NanPermitUI
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitUI__
     */
    public void setNtp061NanPermitUI(AnkenPermitSelector ntp061NanPermitUI) {
        ntp061NanPermitUI__ = ntp061NanPermitUI;
    }
    /**
     * <p>ntp061NanPermitEditUI を取得します。
     * @return ntp061NanPermitEditUI
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitEditUI__
     */
    public AnkenPermitSelector getNtp061NanPermitEditUI() {
        return ntp061NanPermitEditUI__;
    }
    /**
     * <p>ntp061NanPermitEditUI をセットします。
     * @param ntp061NanPermitEditUI ntp061NanPermitEditUI
     * @see jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel#ntp061NanPermitEditUI__
     */
    public void setNtp061NanPermitEditUI(
            AnkenPermitSelector ntp061NanPermitEditUI) {
        ntp061NanPermitEditUI__ = ntp061NanPermitEditUI;
    }
}