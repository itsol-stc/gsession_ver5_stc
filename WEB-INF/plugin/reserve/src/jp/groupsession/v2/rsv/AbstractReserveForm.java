package jp.groupsession.v2.rsv;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] 施設予約プラグインで共通使用するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractReserveForm extends AbstractGsForm {

    /** 期間 未設定 選択 */
    public static final int DATEKBN_SELECT = 1;

    /** 管理者フラグ */
    private boolean rsvAdmFlg__;
    /** 施設グループ編集可否フラグ true:編集可 false:編集不可 */
    private boolean rsvGroupEditFlg__ = false;
    /** 施設グループコンボ選択値 */
    private int rsvSelectedGrpSid__ = -2;
    /** 表示日付 */
    private String rsvDspFrom__ = null;
    /** 選択済み施設SID */
    private int rsvSelectedSisetuSid__ = -1;
    /** 選択済み施設予約SID */
    private int rsvSelectedYoyakuSid__;
    /** 選択済日付 */
    private String rsvSelectedDate__ = null;
    /** 戻り先画面 */
    private String rsvBackPgId__ = null;
    /** 施設予約 印刷区分使用区分 */
    private int rsvPrintUseKbn__ = GSConstReserve.RSV_PRINT_USE_NO;

    //施設利用状況照会画面

    /** 開始年 */
    private int rsv100selectedFromYear__ = -1;
    /** 開始月 */
    private int rsv100selectedFromMonth__ = -1;
    /** 開始日 */
    private int rsv100selectedFromDay__ = -1;
    /** 開始年月日 */
    private String rsv100selectedFromDate__ = null;
    /** 終了年 */
    private int rsv100selectedToYear__ = -1;
    /** 終了月 */
    private int rsv100selectedToMonth__ = -1;
    /** 終了日 */
    private int rsv100selectedToDay__ = -1;
    /** 終了年月日 */
    private String rsv100selectedToDate__ = null;
    /** 期間 未設定 */
    private int rsv100dateKbn__ = 0;
    //フラグ
    /** 初期表示フラグ */
    private boolean rsv100InitFlg__ = true;
    /** 検索フラグ */
    private boolean rsv100SearchFlg__ = false;
    /** 検索フラグ(保存) */
    private boolean rsv100SearchSvFlg__ = false;
    //キー値
    /** ソートキー */
    private int rsv100SortKey__ = GSConstReserve.RSV_SORT_FROM;
    /** オーダーキー */
    private int rsv100OrderKey__ = GSConst.ORDER_KEY_ASC;
    /** ページ(上) */
    private int rsv100PageTop__ = 1;
    /** ページ(下) */
    private int rsv100PageBottom__ = 1;

    /** キーワード */
    private String rsv100KeyWord__ = null;
    /** 検索条件 */
    private int rsv100SearchCondition__ = 0;
    /** 検索対象 (利用目的) */
    private int rsv100TargetMok__ = 0;
    /** 検索対象 (内容) */
    private int rsv100TargetNiyo__ = 0;
    /** 承認状況 */
    private int rsv100apprStatus__ = GSConstReserve.SRH_APPRSTATUS_ALL;

    /** CSV出力項目 */
    private String[] rsv100CsvOutField__ = null;

    /** 第一キー選択値 */
    private int rsv100SelectedKey1__ = 1;
    /** 第二キー選択値 */
    private int rsv100SelectedKey2__ = 2;
    /** 第一キー ソート */
    private int rsv100SelectedKey1Sort__ = 0;
    /** 第二キー ソート */
    private int rsv100SelectedKey2Sort__ = 0;

    //セーブ用フィールド
    /** 開始年 */
    private int rsv100svFromYear__;
    /** 開始月 */
    private int rsv100svFromMonth__;
    /** 開始日 */
    private int rsv100svFromDay__;
    /** 終了年 */
    private int rsv100svToYear__;
    /** 終了月 */
    private int rsv100svToMonth__;
    /** 終了日 */
    private int rsv100svToDay__;
    /** 期間 未設定 */
    private int rsv100svDateKbn__ = 0;
    /** グループ1 */
    private int rsv100svGrp1__;
    /** グループ2 */
    private int rsv100svGrp2__;

    /** キーワード */
    private String rsv100svKeyWord__;
    /** 検索条件 */
    private int rsv100svSearchCondition__;
    /** 検索対象 (利用目的) */
    private int rsv100svTargetMok__;
    /** 検索対象 (内容) */
    private int rsv100svTargetNiyo__;
    /** 検索対象 (承認状況) */
    private int rsv100svApprStatus__;
    /** 第一キー選択値 */
    private int rsv100svSelectedKey1__;
    /** 第二キー選択値 */
    private int rsv100svSelectedKey2__;
    /** 第一キー ソート */
    private int rsv100svSelectedKey1Sort__;
    /** 第二キー ソート */
    private int rsv100svSelectedKey2Sort__;
    /** 選択施設グループの施設区分(save) */
    private int rsv100svSelectSisKbn__;


    //絞り込み
    /** 詳細絞り込みフラグ*/
    private int rsv010LearnMoreFlg__ = 0;

    /** キーワード*/
    private String rsv010sisetuKeyword__ = null;
    /** キーワード区分*/
    private int rsv010KeyWordkbn__;
    /** キーワード対象（資産管理番号)*/
    private int rsv010sisetuKeywordSisan__;
    /** キーワード対象（施設名)*/
    private int rsv010sisetuKeywordSisetu__;
    /** キーワード対象（備考)*/
    private int rsv010sisetuKeywordBiko__;
    /** キーワード対象（ナンバー)*/
    private int rsv010sisetuKeywordNo__;
    /** キーワード対象（ISBN)*/
    private int rsv010sisetuKeywordIsbn__;
    /** 空き状況*/
    private int rsv010sisetuFree__;
    /** 空き範囲開始年*/
    private int rsv010sisetuFreeFromY__;
    /** 空き範囲開始月*/
    private int rsv010sisetuFreeFromMo__;
    /** 空き範囲開始日*/
    private int rsv010sisetuFreeFromD__;
    /** 空き範囲開始時*/
    private int rsv010sisetuFreeFromH__;
    /** 空き範囲開始分*/
    private int rsv010sisetuFreeFromMi__;
    /** 空き範囲終了年*/
    private int rsv010sisetuFreeToY__;
    /** 空き範囲終了月*/
    private int rsv010sisetuFreeToMo__;
    /** 空き範囲終了日*/
    private int rsv010sisetuFreeToD__;
    /** 空き範囲終了時*/
    private int rsv010sisetuFreeToH__;
    /** 空き範囲終了分*/
    private int rsv010sisetuFreeToMi__;
    /** 空き範囲開始日付 */
    private String rsv010sisetuFreeFromDate__;
    /** 空き範囲開始時間 */
    private String rsv010sisetuFreeFromTime__;
    /** 空き範囲終了日付 */
    private String rsv010sisetuFreeToDate__;
    /** 空き範囲終了時間 */
    private String rsv010sisetuFreeToTime__;
    /** 施設区分*/
    private int rsv010sisetuKbn__;
    
    /** グループ*/
    private int rsv010grpNarrowDown__;
    /** 喫煙*/
    private int rsv010sisetuSmoky__;
    /** 座席数*/
    private String rsv010sisetuChere__;
    /** 社外持ち出し*/
    private int rsv010sisetuTakeout__;

    //保管用
    /** キーワード*/
    private String rsv010svSisetuKeyword__ = null;
    /** キーワード区分*/
    private int rsv010svKeyWordkbn__;
    /** キーワード対象（資産管理番号)*/
    private int rsv010svSisetuKeywordSisan__;
    /** キーワード対象（施設名)*/
    private int rsv010svSisetuKeywordSisetu__;
    /** キーワード対象（備考)*/
    private int rsv010svSisetuKeywordBiko__;
    /** キーワード対象（ナンバー)*/
    private int rsv010svSisetuKeywordNo__;
    /** キーワード対象（ISBN)*/
    private int rsv010svSisetuKeywordIsbn__;
    /** 空き状況*/
    private int rsv010svSisetuFree__;
    /** 空き範囲開始年*/
    private int rsv010svSisetuFreeFromY__;
    /** 空き範囲開始月*/
    private int rsv010svSisetuFreeFromMo__;
    /** 空き範囲開始日*/
    private int rsv010svSisetuFreeFromD__;
    /** 空き範囲開始時*/
    private int rsv010svSisetuFreeFromH__;
    /** 空き範囲開始分*/
    private int rsv010svSisetuFreeFromMi__;
    /** 空き範囲終了年*/
    private int rsv010svSisetuFreeToY__;
    /** 空き範囲終了月*/
    private int rsv010svSisetuFreeToMo__;
    /** 空き範囲終了日*/
    private int rsv010svSisetuFreeToD__;
    /** 空き範囲終了時*/
    private int rsv010svSisetuFreeToH__;
    /** 空き範囲終了分*/
    private int rsv010svSisetuFreeToMi__;
    /** 施設区分*/
    private int rsv010svSisetuKbn__;
    /** グループ*/
    private int rsv010svGrpNarrowDown__;
    /** 喫煙*/
    private int rsv010svSisetuSmoky__;
    /** 座席数*/
    private String rsv010svSisetuChere__;
    /** 社外持ち出し*/
    private int rsv010svSisetuTakeout__;

    /** 初期表示フラグ */
    private int rsv010InitFlg__ = 1;

    /** 絞り込み検索フラグ*/
    private int rsv010SiborikomiFlg__ = 0;

    /**
     * <p>rsv100svFromDay__ を取得します。
     * @return rsv100svFromDay
     */
    public int getRsv100svFromDay() {
        return rsv100svFromDay__;
    }
    /**
     * <p>rsv100svFromDay__ をセットします。
     * @param rsv100svFromDay rsv100svFromDay__
     */
    public void setRsv100svFromDay(int rsv100svFromDay) {
        rsv100svFromDay__ = rsv100svFromDay;
    }
    /**
     * <p>rsv100svFromMonth__ を取得します。
     * @return rsv100svFromMonth
     */
    public int getRsv100svFromMonth() {
        return rsv100svFromMonth__;
    }
    /**
     * <p>rsv100svFromMonth__ をセットします。
     * @param rsv100svFromMonth rsv100svFromMonth__
     */
    public void setRsv100svFromMonth(int rsv100svFromMonth) {
        rsv100svFromMonth__ = rsv100svFromMonth;
    }
    /**
     * <p>rsv100svFromYear__ を取得します。
     * @return rsv100svFromYear
     */
    public int getRsv100svFromYear() {
        return rsv100svFromYear__;
    }
    /**
     * <p>rsv100svFromYear__ をセットします。
     * @param rsv100svFromYear rsv100svFromYear__
     */
    public void setRsv100svFromYear(int rsv100svFromYear) {
        rsv100svFromYear__ = rsv100svFromYear;
    }
    /**
     * <p>rsv100svGrp1__ を取得します。
     * @return rsv100svGrp1
     */
    public int getRsv100svGrp1() {
        return rsv100svGrp1__;
    }
    /**
     * <p>rsv100svGrp1__ をセットします。
     * @param rsv100svGrp1 rsv100svGrp1__
     */
    public void setRsv100svGrp1(int rsv100svGrp1) {
        rsv100svGrp1__ = rsv100svGrp1;
    }
    /**
     * <p>rsv100svGrp2__ を取得します。
     * @return rsv100svGrp2
     */
    public int getRsv100svGrp2() {
        return rsv100svGrp2__;
    }
    /**
     * <p>rsv100svGrp2__ をセットします。
     * @param rsv100svGrp2 rsv100svGrp2__
     */
    public void setRsv100svGrp2(int rsv100svGrp2) {
        rsv100svGrp2__ = rsv100svGrp2;
    }
    /**
     * <p>rsv100svKeyWord__ を取得します。
     * @return rsv100svKeyWord
     */
    public String getRsv100svKeyWord() {
        return rsv100svKeyWord__;
    }
    /**
     * <p>rsv100svKeyWord__ をセットします。
     * @param rsv100svKeyWord rsv100svKeyWord__
     */
    public void setRsv100svKeyWord(String rsv100svKeyWord) {
        rsv100svKeyWord__ = rsv100svKeyWord;
    }
    /**
     * <p>rsv100svSearchCondition__ を取得します。
     * @return rsv100svSearchCondition
     */
    public int getRsv100svSearchCondition() {
        return rsv100svSearchCondition__;
    }
    /**
     * <p>rsv100svSearchCondition__ をセットします。
     * @param rsv100svSearchCondition rsv100svSearchCondition__
     */
    public void setRsv100svSearchCondition(int rsv100svSearchCondition) {
        rsv100svSearchCondition__ = rsv100svSearchCondition;
    }
    /**
     * <p>rsv100svSelectedKey1__ を取得します。
     * @return rsv100svSelectedKey1
     */
    public int getRsv100svSelectedKey1() {
        return rsv100svSelectedKey1__;
    }
    /**
     * <p>rsv100svSelectedKey1__ をセットします。
     * @param rsv100svSelectedKey1 rsv100svSelectedKey1__
     */
    public void setRsv100svSelectedKey1(int rsv100svSelectedKey1) {
        rsv100svSelectedKey1__ = rsv100svSelectedKey1;
    }
    /**
     * <p>rsv100svSelectedKey1Sort__ を取得します。
     * @return rsv100svSelectedKey1Sort
     */
    public int getRsv100svSelectedKey1Sort() {
        return rsv100svSelectedKey1Sort__;
    }
    /**
     * <p>rsv100svSelectedKey1Sort__ をセットします。
     * @param rsv100svSelectedKey1Sort rsv100svSelectedKey1Sort__
     */
    public void setRsv100svSelectedKey1Sort(int rsv100svSelectedKey1Sort) {
        rsv100svSelectedKey1Sort__ = rsv100svSelectedKey1Sort;
    }
    /**
     * <p>rsv100svSelectedKey2__ を取得します。
     * @return rsv100svSelectedKey2
     */
    public int getRsv100svSelectedKey2() {
        return rsv100svSelectedKey2__;
    }
    /**
     * <p>rsv100svSelectedKey2__ をセットします。
     * @param rsv100svSelectedKey2 rsv100svSelectedKey2__
     */
    public void setRsv100svSelectedKey2(int rsv100svSelectedKey2) {
        rsv100svSelectedKey2__ = rsv100svSelectedKey2;
    }
    /**
     * <p>rsv100svSelectedKey2Sort__ を取得します。
     * @return rsv100svSelectedKey2Sort
     */
    public int getRsv100svSelectedKey2Sort() {
        return rsv100svSelectedKey2Sort__;
    }
    /**
     * <p>rsv100svSelectedKey2Sort__ をセットします。
     * @param rsv100svSelectedKey2Sort rsv100svSelectedKey2Sort__
     */
    public void setRsv100svSelectedKey2Sort(int rsv100svSelectedKey2Sort) {
        rsv100svSelectedKey2Sort__ = rsv100svSelectedKey2Sort;
    }
    /**
     * <p>rsv100svTargetMok__ を取得します。
     * @return rsv100svTargetMok
     */
    public int getRsv100svTargetMok() {
        return rsv100svTargetMok__;
    }
    /**
     * <p>rsv100svTargetMok__ をセットします。
     * @param rsv100svTargetMok rsv100svTargetMok__
     */
    public void setRsv100svTargetMok(int rsv100svTargetMok) {
        rsv100svTargetMok__ = rsv100svTargetMok;
    }
    /**
     * <p>rsv100svTargetNiyo__ を取得します。
     * @return rsv100svTargetNiyo
     */
    public int getRsv100svTargetNiyo() {
        return rsv100svTargetNiyo__;
    }
    /**
     * <p>rsv100svTargetNiyo__ をセットします。
     * @param rsv100svTargetNiyo rsv100svTargetNiyo__
     */
    public void setRsv100svTargetNiyo(int rsv100svTargetNiyo) {
        rsv100svTargetNiyo__ = rsv100svTargetNiyo;
    }
    /**
     * <p>rsv100svToDay__ を取得します。
     * @return rsv100svToDay
     */
    public int getRsv100svToDay() {
        return rsv100svToDay__;
    }
    /**
     * <p>rsv100svToDay__ をセットします。
     * @param rsv100svToDay rsv100svToDay__
     */
    public void setRsv100svToDay(int rsv100svToDay) {
        rsv100svToDay__ = rsv100svToDay;
    }
    /**
     * <p>rsv100svToMonth__ を取得します。
     * @return rsv100svToMonth
     */
    public int getRsv100svToMonth() {
        return rsv100svToMonth__;
    }
    /**
     * <p>rsv100svToMonth__ をセットします。
     * @param rsv100svToMonth rsv100svToMonth__
     */
    public void setRsv100svToMonth(int rsv100svToMonth) {
        rsv100svToMonth__ = rsv100svToMonth;
    }
    /**
     * <p>rsv100svToYear__ を取得します。
     * @return rsv100svToYear
     */
    public int getRsv100svToYear() {
        return rsv100svToYear__;
    }
    /**
     * <p>rsv100svToYear__ をセットします。
     * @param rsv100svToYear rsv100svToYear__
     */
    public void setRsv100svToYear(int rsv100svToYear) {
        rsv100svToYear__ = rsv100svToYear;
    }
    /**
     * <p>rsv100SelectedKey1__ を取得します。
     * @return rsv100SelectedKey1
     */
    public int getRsv100SelectedKey1() {
        return rsv100SelectedKey1__;
    }
    /**
     * <p>rsv100SelectedKey1__ をセットします。
     * @param rsv100SelectedKey1 rsv100SelectedKey1__
     */
    public void setRsv100SelectedKey1(int rsv100SelectedKey1) {
        rsv100SelectedKey1__ = rsv100SelectedKey1;
    }
    /**
     * <p>rsv100SelectedKey1Sort__ を取得します。
     * @return rsv100SelectedKey1Sort
     */
    public int getRsv100SelectedKey1Sort() {
        return rsv100SelectedKey1Sort__;
    }
    /**
     * <p>rsv100SelectedKey1Sort__ をセットします。
     * @param rsv100SelectedKey1Sort rsv100SelectedKey1Sort__
     */
    public void setRsv100SelectedKey1Sort(int rsv100SelectedKey1Sort) {
        rsv100SelectedKey1Sort__ = rsv100SelectedKey1Sort;
    }
    /**
     * <p>rsv100SelectedKey2__ を取得します。
     * @return rsv100SelectedKey2
     */
    public int getRsv100SelectedKey2() {
        return rsv100SelectedKey2__;
    }
    /**
     * <p>rsv100SelectedKey2__ をセットします。
     * @param rsv100SelectedKey2 rsv100SelectedKey2__
     */
    public void setRsv100SelectedKey2(int rsv100SelectedKey2) {
        rsv100SelectedKey2__ = rsv100SelectedKey2;
    }
    /**
     * <p>rsv100SelectedKey2Sort__ を取得します。
     * @return rsv100SelectedKey2Sort
     */
    public int getRsv100SelectedKey2Sort() {
        return rsv100SelectedKey2Sort__;
    }
    /**
     * <p>rsv100SelectedKey2Sort__ をセットします。
     * @param rsv100SelectedKey2Sort rsv100SelectedKey2Sort__
     */
    public void setRsv100SelectedKey2Sort(int rsv100SelectedKey2Sort) {
        rsv100SelectedKey2Sort__ = rsv100SelectedKey2Sort;
    }
    /**
     * <p>rsv100KeyWord__ を取得します。
     * @return rsv100KeyWord
     */
    public String getRsv100KeyWord() {
        return rsv100KeyWord__;
    }
    /**
     * <p>rsv100KeyWord__ をセットします。
     * @param rsv100KeyWord rsv100KeyWord__
     */
    public void setRsv100KeyWord(String rsv100KeyWord) {
        rsv100KeyWord__ = rsv100KeyWord;
    }
    /**
     * <p>rsv100SearchCondition__ を取得します。
     * @return rsv100SearchCondition
     */
    public int getRsv100SearchCondition() {
        return rsv100SearchCondition__;
    }
    /**
     * <p>rsv100SearchCondition__ をセットします。
     * @param rsv100SearchCondition rsv100SearchCondition__
     */
    public void setRsv100SearchCondition(int rsv100SearchCondition) {
        rsv100SearchCondition__ = rsv100SearchCondition;
    }
    /**
     * <p>rsv100TargetMok__ を取得します。
     * @return rsv100TargetMok
     */
    public int getRsv100TargetMok() {
        return rsv100TargetMok__;
    }
    /**
     * <p>rsv100TargetMok__ をセットします。
     * @param rsv100TargetMok rsv100TargetMok__
     */
    public void setRsv100TargetMok(int rsv100TargetMok) {
        rsv100TargetMok__ = rsv100TargetMok;
    }
    /**
     * <p>rsv100TargetNiyo__ を取得します。
     * @return rsv100TargetNiyo
     */
    public int getRsv100TargetNiyo() {
        return rsv100TargetNiyo__;
    }
    /**
     * <p>rsv100TargetNiyo__ をセットします。
     * @param rsv100TargetNiyo rsv100TargetNiyo__
     */
    public void setRsv100TargetNiyo(int rsv100TargetNiyo) {
        rsv100TargetNiyo__ = rsv100TargetNiyo;
    }
    /**
     * <p>rsvSelectedYoyakuSid__ を取得します。
     * @return rsvSelectedYoyakuSid
     */
    public int getRsvSelectedYoyakuSid() {
        return rsvSelectedYoyakuSid__;
    }
    /**
     * <p>rsvSelectedYoyakuSid__ をセットします。
     * @param rsvSelectedYoyakuSid rsvSelectedYoyakuSid__
     */
    public void setRsvSelectedYoyakuSid(int rsvSelectedYoyakuSid) {
        rsvSelectedYoyakuSid__ = rsvSelectedYoyakuSid;
    }
    /**
     * <p>rsvSelectedDate__ を取得します。
     * @return rsvSelectedDate
     */
    public String getRsvSelectedDate() {
        return rsvSelectedDate__;
    }
    /**
     * <p>rsvSelectedDate__ をセットします。
     * @param rsvSelectedDate rsvSelectedDate__
     */
    public void setRsvSelectedDate(String rsvSelectedDate) {
        rsvSelectedDate__ = rsvSelectedDate;
    }
    /**
     * <p>rsvAdmFlg__ を取得します。
     * @return rsvAdmFlg
     */
    public boolean isRsvAdmFlg() {
        return rsvAdmFlg__;
    }
    /**
     * <p>rsvAdmFlg__ をセットします。
     * @param rsvAdmFlg rsvAdmFlg__
     */
    public void setRsvAdmFlg(boolean rsvAdmFlg) {
        rsvAdmFlg__ = rsvAdmFlg;
    }
    /**
     * <p>rsvGroupEditFlg__ を取得します。
     * @return rsvGroupEditFlg
     */
    public boolean isRsvGroupEditFlg() {
        return rsvGroupEditFlg__;
    }
    /**
     * <p>rsvGroupEditFlg__ をセットします。
     * @param rsvGroupEditFlg rsvGroupEditFlg__
     */
    public void setRsvGroupEditFlg(boolean rsvGroupEditFlg) {
        rsvGroupEditFlg__ = rsvGroupEditFlg;
    }
    /**
     * <p>rsvSelectedGrpSid__ を取得します。
     * @return rsvSelectedGrpSid
     */
    public int getRsvSelectedGrpSid() {
        return rsvSelectedGrpSid__;
    }
    /**
     * <p>rsvSelectedGrpSid__ をセットします。
     * @param rsvSelectedGrpSid rsvSelectedGrpSid__
     */
    public void setRsvSelectedGrpSid(int rsvSelectedGrpSid) {
        rsvSelectedGrpSid__ = rsvSelectedGrpSid;
    }
    /**
     * <p>rsvDspFrom__ を取得します。
     * @return rsvDspFrom
     */
    public String getRsvDspFrom() {
        return rsvDspFrom__;
    }
    /**
     * <p>rsvDspFrom__ をセットします。
     * @param rsvDspFrom rsvDspFrom__
     */
    public void setRsvDspFrom(String rsvDspFrom) {
        rsvDspFrom__ = rsvDspFrom;
    }
    /**
     * <p>rsvSelectedSisetuSid__ を取得します。
     * @return rsvSelectedSisetuSid
     */
    public int getRsvSelectedSisetuSid() {
        return rsvSelectedSisetuSid__;
    }
    /**
     * <p>rsvSelectedSisetuSid__ をセットします。
     * @param rsvSelectedSisetuSid rsvSelectedSisetuSid__
     */
    public void setRsvSelectedSisetuSid(int rsvSelectedSisetuSid) {
        rsvSelectedSisetuSid__ = rsvSelectedSisetuSid;
    }
    /**
     * <p>rsvBackPgId を取得します。
     * @return rsvBackPgId
     */
    public String getRsvBackPgId() {
        return rsvBackPgId__;
    }
    /**
     * <p>rsvBackPgId をセットします。
     * @param rsvBackPgId rsvBackPgId
     */
    public void setRsvBackPgId(String rsvBackPgId) {
        rsvBackPgId__ = rsvBackPgId;
    }
    /**
     * <p>rsv100selectedFromMonth を取得します。
     * @return rsv100selectedFromMonth
     */
    public int getRsv100selectedFromMonth() {
        return rsv100selectedFromMonth__;
    }

    /**
     * <p>rsv100selectedFromMonth をセットします。
     * @param rsv100selectedFromMonth rsv100selectedFromMonth
     */
    public void setRsv100selectedFromMonth(int rsv100selectedFromMonth) {
        rsv100selectedFromMonth__ = rsv100selectedFromMonth;
    }

    /**
     * <p>rsv100selectedFromYear を取得します。
     * @return rsv100selectedFromYear
     */
    public int getRsv100selectedFromYear() {
        return rsv100selectedFromYear__;
    }

    /**
     * <p>rsv100selectedFromYear をセットします。
     * @param rsv100selectedFromYear rsv100selectedFromYear
     */
    public void setRsv100selectedFromYear(int rsv100selectedFromYear) {
        rsv100selectedFromYear__ = rsv100selectedFromYear;
    }
    /**
     * <p>rsv100selectedToMonth を取得します。
     * @return rsv100selectedToMonth
     */
    public int getRsv100selectedToMonth() {
        return rsv100selectedToMonth__;
    }

    /**
     * <p>rsv100selectedToMonth をセットします。
     * @param rsv100selectedToMonth rsv100selectedToMonth
     */
    public void setRsv100selectedToMonth(int rsv100selectedToMonth) {
        rsv100selectedToMonth__ = rsv100selectedToMonth;
    }

    /**
     * <p>rsv100selectedToYear を取得します。
     * @return rsv100selectedToYear
     */
    public int getRsv100selectedToYear() {
        return rsv100selectedToYear__;
    }

    /**
     * <p>rsv100selectedToYear をセットします。
     * @param rsv100selectedToYear rsv100selectedToYear
     */
    public void setRsv100selectedToYear(int rsv100selectedToYear) {
        rsv100selectedToYear__ = rsv100selectedToYear;
    }
    /**
     * <p>rsv100selectedFromDay を取得します。
     * @return rsv100selectedFromDay
     */
    public int getRsv100selectedFromDay() {
        return rsv100selectedFromDay__;
    }

    /**
     * <p>rsv100selectedFromDay をセットします。
     * @param rsv100selectedFromDay rsv100selectedFromDay
     */
    public void setRsv100selectedFromDay(int rsv100selectedFromDay) {
        rsv100selectedFromDay__ = rsv100selectedFromDay;
    }

    /**
     * <p>rsv100selectedToDay を取得します。
     * @return rsv100selectedToDay
     */
    public int getRsv100selectedToDay() {
        return rsv100selectedToDay__;
    }

    /**
     * <p>rsv100selectedToDay をセットします。
     * @param rsv100selectedToDay rsv100selectedToDay
     */
    public void setRsv100selectedToDay(int rsv100selectedToDay) {
        rsv100selectedToDay__ = rsv100selectedToDay;
    }

    /**
     * <p>rsv100InitFlg を取得します。
     * @return rsv100InitFlg
     */
    public boolean isRsv100InitFlg() {
        return rsv100InitFlg__;
    }

    /**
     * <p>rsv100InitFlg をセットします。
     * @param rsv100InitFlg rsv100InitFlg
     */
    public void setRsv100InitFlg(boolean rsv100InitFlg) {
        rsv100InitFlg__ = rsv100InitFlg;
    }
    /**
     * <p>rsv100OrderKey を取得します。
     * @return rsv100OrderKey
     */
    public int getRsv100OrderKey() {
        return rsv100OrderKey__;
    }

    /**
     * <p>rsv100OrderKey をセットします。
     * @param rsv100OrderKey rsv100OrderKey
     */
    public void setRsv100OrderKey(int rsv100OrderKey) {
        rsv100OrderKey__ = rsv100OrderKey;
    }

    /**
     * <p>rsv100PageBottom を取得します。
     * @return rsv100PageBottom
     */
    public int getRsv100PageBottom() {
        return rsv100PageBottom__;
    }

    /**
     * <p>rsv100PageBottom をセットします。
     * @param rsv100PageBottom rsv100PageBottom
     */
    public void setRsv100PageBottom(int rsv100PageBottom) {
        rsv100PageBottom__ = rsv100PageBottom;
    }
    /**
     * <p>rsv100PageTop を取得します。
     * @return rsv100PageTop
     */
    public int getRsv100PageTop() {
        return rsv100PageTop__;
    }

    /**
     * <p>rsv100PageTop をセットします。
     * @param rsv100PageTop rsv100PageTop
     */
    public void setRsv100PageTop(int rsv100PageTop) {
        rsv100PageTop__ = rsv100PageTop;
    }

    /**
     * <p>rsv100SearchFlg を取得します。
     * @return rsv100SearchFlg
     */
    public boolean isRsv100SearchFlg() {
        return rsv100SearchFlg__;
    }

    /**
     * <p>rsv100SearchFlg をセットします。
     * @param rsv100SearchFlg rsv100SearchFlg
     */
    public void setRsv100SearchFlg(boolean rsv100SearchFlg) {
        rsv100SearchFlg__ = rsv100SearchFlg;
    }
    /**
     * <p>rsv100SortKey を取得します。
     * @return rsv100SortKey
     */
    public int getRsv100SortKey() {
        return rsv100SortKey__;
    }

    /**
     * <p>rsv100SortKey をセットします。
     * @param rsv100SortKey rsv100SortKey
     */
    public void setRsv100SortKey(int rsv100SortKey) {
        rsv100SortKey__ = rsv100SortKey;
    }
    /**
     * <p>rsv100CsvOutField を取得します。
     * @return rsv100CsvOutField
     */
    public String[] getRsv100CsvOutField() {
        return rsv100CsvOutField__;
    }
    /**
     * <p>rsv100CsvOutField をセットします。
     * @param rsv100CsvOutField rsv100CsvOutField
     */
    public void setRsv100CsvOutField(String[] rsv100CsvOutField) {
        rsv100CsvOutField__ = rsv100CsvOutField;
    }
    /**
     * <p>rsv100SearchSvFlg を取得します。
     * @return rsv100SearchSvFlg
     */
    public boolean isRsv100SearchSvFlg() {
        return rsv100SearchSvFlg__;
    }
    /**
     * <p>rsv100SearchSvFlg をセットします。
     * @param rsv100SearchSvFlg rsv100SearchSvFlg
     */
    public void setRsv100SearchSvFlg(boolean rsv100SearchSvFlg) {
        rsv100SearchSvFlg__ = rsv100SearchSvFlg;
    }
    /**
     * @return rsv100dateKbn
     */
    public int getRsv100dateKbn() {
        return rsv100dateKbn__;
    }
    /**
     * @param rsv100dateKbn 設定する rsv100dateKbn
     */
    public void setRsv100dateKbn(int rsv100dateKbn) {
        rsv100dateKbn__ = rsv100dateKbn;
    }
    /**
     * @return rsv100svDateKbn
     */
    public int getRsv100svDateKbn() {
        return rsv100svDateKbn__;
    }
    /**
     * @param rsv100svDateKbn 設定する rsv100svDateKbn
     */
    public void setRsv100svDateKbn(int rsv100svDateKbn) {
        rsv100svDateKbn__ = rsv100svDateKbn;
    }
    /**
     * @return rsv100apprStatus
     */
    public int getRsv100apprStatus() {
        return rsv100apprStatus__;
    }
    /**
     * @param rsv100apprStatus 設定する rsv100apprStatus
     */
    public void setRsv100apprStatus(int rsv100apprStatus) {
        rsv100apprStatus__ = rsv100apprStatus;
    }
    /**
     * @return rsv100svApprStatus
     */
    public int getRsv100svApprStatus() {
        return rsv100svApprStatus__;
    }
    /**
     * @param rsv100svApprStatus 設定する rsv100svApprStatus
     */
    public void setRsv100svApprStatus(int rsv100svApprStatus) {
        rsv100svApprStatus__ = rsv100svApprStatus;
    }
    /**
     * <p>rsvPrintUseKbn を取得します。
     * @return rsvPrintUseKbn
     */
    public int getRsvPrintUseKbn() {
        return rsvPrintUseKbn__;
    }
    /**
     * <p>rsvPrintUseKbn をセットします。
     * @param rsvPrintUseKbn rsvPrintUseKbn
     */
    public void setRsvPrintUseKbn(int rsvPrintUseKbn) {
        rsvPrintUseKbn__ = rsvPrintUseKbn;
    }
    /**
     * <p>rsv100svSelectSisKbn を取得します。
     * @return rsv100svSelectSisKbn
     */
    public int getRsv100svSelectSisKbn() {
        return rsv100svSelectSisKbn__;
    }
    /**
     * <p>rsv100svSelectSisKbn をセットします。
     * @param rsv100svSelectSisKbn rsv100svSelectSisKbn
     */
    public void setRsv100svSelectSisKbn(int rsv100svSelectSisKbn) {
        rsv100svSelectSisKbn__ = rsv100svSelectSisKbn;
    }
    /**
     * <p>rsv010LearnMoreFlg を取得します。
     * @return rsv010LearnMoreFlg
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010LearnMoreFlg__
     */
    public int getRsv010LearnMoreFlg() {
        return rsv010LearnMoreFlg__;
    }
    /**
     * <p>rsv010LearnMoreFlg をセットします。
     * @param rsv010LearnMoreFlg rsv010LearnMoreFlg
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010LearnMoreFlg__
     */
    public void setRsv010LearnMoreFlg(int rsv010LearnMoreFlg) {
        rsv010LearnMoreFlg__ = rsv010LearnMoreFlg;
    }
    /**
     * <p>rsv010sisetuKeyword を取得します。
     * @return rsv010sisetuKeyword
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeyword__
     */
    public String getRsv010sisetuKeyword() {
        return rsv010sisetuKeyword__;
    }
    /**
     * <p>rsv010sisetuKeyword をセットします。
     * @param rsv010sisetuKeyword rsv010sisetuKeyword
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeyword__
     */
    public void setRsv010sisetuKeyword(String rsv010sisetuKeyword) {
        rsv010sisetuKeyword__ = rsv010sisetuKeyword;
    }
    /**
     * <p>rsv010KeyWordkbn を取得します。
     * @return rsv010KeyWordkbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010KeyWordkbn__
     */
    public int getRsv010KeyWordkbn() {
        return rsv010KeyWordkbn__;
    }
    /**
     * <p>rsv010KeyWordkbn をセットします。
     * @param rsv010KeyWordkbn rsv010KeyWordkbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010KeyWordkbn__
     */
    public void setRsv010KeyWordkbn(int rsv010KeyWordkbn) {
        rsv010KeyWordkbn__ = rsv010KeyWordkbn;
    }
    /**
     * <p>rsv010sisetuFree を取得します。
     * @return rsv010sisetuFree
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFree__
     */
    public int getRsv010sisetuFree() {
        return rsv010sisetuFree__;
    }
    /**
     * <p>rsv010sisetuFree をセットします。
     * @param rsv010sisetuFree rsv010sisetuFree
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFree__
     */
    public void setRsv010sisetuFree(int rsv010sisetuFree) {
        rsv010sisetuFree__ = rsv010sisetuFree;
    }
    /**
     * <p>rsv010sisetuFreeFromY を取得します。
     * @return rsv010sisetuFreeFromY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromY__
     */
    public int getRsv010sisetuFreeFromY() {
        return rsv010sisetuFreeFromY__;
    }
    /**
     * <p>rsv010sisetuFreeFromY をセットします。
     * @param rsv010sisetuFreeFromY rsv010sisetuFreeFromY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromY__
     */
    public void setRsv010sisetuFreeFromY(int rsv010sisetuFreeFromY) {
        rsv010sisetuFreeFromY__ = rsv010sisetuFreeFromY;
    }
    /**
     * <p>rsv010sisetuFreeFromMo を取得します。
     * @return rsv010sisetuFreeFromMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromMo__
     */
    public int getRsv010sisetuFreeFromMo() {
        return rsv010sisetuFreeFromMo__;
    }
    /**
     * <p>rsv010sisetuFreeFromMo をセットします。
     * @param rsv010sisetuFreeFromMo rsv010sisetuFreeFromMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromMo__
     */
    public void setRsv010sisetuFreeFromMo(int rsv010sisetuFreeFromMo) {
        rsv010sisetuFreeFromMo__ = rsv010sisetuFreeFromMo;
    }
    /**
     * <p>rsv010sisetuFreeFromD を取得します。
     * @return rsv010sisetuFreeFromD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromD__
     */
    public int getRsv010sisetuFreeFromD() {
        return rsv010sisetuFreeFromD__;
    }
    /**
     * <p>rsv010sisetuFreeFromD をセットします。
     * @param rsv010sisetuFreeFromD rsv010sisetuFreeFromD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromD__
     */
    public void setRsv010sisetuFreeFromD(int rsv010sisetuFreeFromD) {
        rsv010sisetuFreeFromD__ = rsv010sisetuFreeFromD;
    }
    /**
     * <p>rsv010sisetuFreeFromH を取得します。
     * @return rsv010sisetuFreeFromH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromH__
     */
    public int getRsv010sisetuFreeFromH() {
        return rsv010sisetuFreeFromH__;
    }
    /**
     * <p>rsv010sisetuFreeFromH をセットします。
     * @param rsv010sisetuFreeFromH rsv010sisetuFreeFromH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromH__
     */
    public void setRsv010sisetuFreeFromH(int rsv010sisetuFreeFromH) {
        rsv010sisetuFreeFromH__ = rsv010sisetuFreeFromH;
    }
    /**
     * <p>rsv010sisetuFreeFromMi を取得します。
     * @return rsv010sisetuFreeFromMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromMi__
     */
    public int getRsv010sisetuFreeFromMi() {
        return rsv010sisetuFreeFromMi__;
    }
    /**
     * <p>rsv010sisetuFreeFromMi をセットします。
     * @param rsv010sisetuFreeFromMi rsv010sisetuFreeFromMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromMi__
     */
    public void setRsv010sisetuFreeFromMi(int rsv010sisetuFreeFromMi) {
        rsv010sisetuFreeFromMi__ = rsv010sisetuFreeFromMi;
    }
    /**
     * <p>rsv010sisetuFreeToY を取得します。
     * @return rsv010sisetuFreeToY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToY__
     */
    public int getRsv010sisetuFreeToY() {
        return rsv010sisetuFreeToY__;
    }
    /**
     * <p>rsv010sisetuFreeToY をセットします。
     * @param rsv010sisetuFreeToY rsv010sisetuFreeToY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToY__
     */
    public void setRsv010sisetuFreeToY(int rsv010sisetuFreeToY) {
        rsv010sisetuFreeToY__ = rsv010sisetuFreeToY;
    }
    /**
     * <p>rsv010sisetuFreeToMo を取得します。
     * @return rsv010sisetuFreeToMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToMo__
     */
    public int getRsv010sisetuFreeToMo() {
        return rsv010sisetuFreeToMo__;
    }
    /**
     * <p>rsv010sisetuFreeToMo をセットします。
     * @param rsv010sisetuFreeToMo rsv010sisetuFreeToMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToMo__
     */
    public void setRsv010sisetuFreeToMo(int rsv010sisetuFreeToMo) {
        rsv010sisetuFreeToMo__ = rsv010sisetuFreeToMo;
    }
    /**
     * <p>rsv010sisetuFreeToD を取得します。
     * @return rsv010sisetuFreeToD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToD__
     */
    public int getRsv010sisetuFreeToD() {
        return rsv010sisetuFreeToD__;
    }
    /**
     * <p>rsv010sisetuFreeToD をセットします。
     * @param rsv010sisetuFreeToD rsv010sisetuFreeToD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToD__
     */
    public void setRsv010sisetuFreeToD(int rsv010sisetuFreeToD) {
        rsv010sisetuFreeToD__ = rsv010sisetuFreeToD;
    }
    /**
     * <p>rsv010sisetuFreeToH を取得します。
     * @return rsv010sisetuFreeToH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToH__
     */
    public int getRsv010sisetuFreeToH() {
        return rsv010sisetuFreeToH__;
    }
    /**
     * <p>rsv010sisetuFreeToH をセットします。
     * @param rsv010sisetuFreeToH rsv010sisetuFreeToH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToH__
     */
    public void setRsv010sisetuFreeToH(int rsv010sisetuFreeToH) {
        rsv010sisetuFreeToH__ = rsv010sisetuFreeToH;
    }
    /**
     * <p>rsv010sisetuFreeToMi を取得します。
     * @return rsv010sisetuFreeToMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToMi__
     */
    public int getRsv010sisetuFreeToMi() {
        return rsv010sisetuFreeToMi__;
    }
    /**
     * <p>rsv010sisetuFreeToMi をセットします。
     * @param rsv010sisetuFreeToMi rsv010sisetuFreeToMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToMi__
     */
    public void setRsv010sisetuFreeToMi(int rsv010sisetuFreeToMi) {
        rsv010sisetuFreeToMi__ = rsv010sisetuFreeToMi;
    }
    /**
     * <p>rsv010sisetuFreeFromDate を取得します。
     * @return rsv010sisetuFreeFromDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromDate__
     */
    public String getRsv010sisetuFreeFromDate() {
        return rsv010sisetuFreeFromDate__;
    }
    /**
     * <p>rsv010sisetuFreeFromDate をセットします。
     * @param rsv010sisetuFreeFromDate rsv010sisetuFreeFromDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromDate__
     */
    public void setRsv010sisetuFreeFromDate(String rsv010sisetuFreeFromDate) {
        rsv010sisetuFreeFromDate__ = rsv010sisetuFreeFromDate;
    }
    /**
     * <p>rsv010sisetuFreeFromTime を取得します。
     * @return rsv010sisetuFreeFromTime
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromTime__
     */
    public String getRsv010sisetuFreeFromTime() {
        return rsv010sisetuFreeFromTime__;
    }
    /**
     * <p>rsv010sisetuFreeFromTime をセットします。
     * @param rsv010sisetuFreeFromTime rsv010sisetuFreeFromTime
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeFromTime__
     */
    public void setRsv010sisetuFreeFromTime(String rsv010sisetuFreeFromTime) {
        this.rsv010sisetuFreeFromTime__ = rsv010sisetuFreeFromTime;
    }
    /**
     * <p>rsv010sisetuFreeToDate を取得します。
     * @return rsv010sisetuFreeToDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToDate__
     */
    public String getRsv010sisetuFreeToDate() {
        return rsv010sisetuFreeToDate__;
    }
    /**
     * <p>rsv010sisetuFreeToDate をセットします。
     * @param rsv010sisetuFreeToDate rsv010sisetuFreeToDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToDate__
     */
    public void setRsv010sisetuFreeToDate(String rsv010sisetuFreeToDate) {
        rsv010sisetuFreeToDate__ = rsv010sisetuFreeToDate;
    }
    /**
     * <p>rsv010sisetuFreeToTime を取得します。
     * @return rsv010sisetuFreeToTime
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToTime__
     */
    public String getRsv010sisetuFreeToTime() {
        return rsv010sisetuFreeToTime__;
    }
    /**
     * <p>rsv010sisetuFreeToTime をセットします。
     * @param rsv010sisetuFreeToTime rsv010sisetuFreeToTime
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuFreeToTime__
     */
    public void setRsv010sisetuFreeToTime(String rsv010sisetuFreeToTime) {
        this.rsv010sisetuFreeToTime__ = rsv010sisetuFreeToTime;
    }
    /**
     * <p>rsv010sisetuKbn を取得します。
     * @return rsv010sisetuKbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKbn__
     */
    public int getRsv010sisetuKbn() {
        return rsv010sisetuKbn__;
    }
    /**
     * <p>rsv010sisetuKbn をセットします。
     * @param rsv010sisetuKbn rsv010sisetuKbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKbn__
     */
    public void setRsv010sisetuKbn(int rsv010sisetuKbn) {
        rsv010sisetuKbn__ = rsv010sisetuKbn;
    }
    /**
     * <p>rsv010grpNarrowDown を取得します。
     * @return rsv010grpNarrowDown
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010grpNarrowDown__
     */
    public int getRsv010grpNarrowDown() {
        return rsv010grpNarrowDown__;
    }
    /**
     * <p>rsv010grpNarrowDown をセットします。
     * @param rsv010grpNarrowDown rsv010grpNarrowDown
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010grpNarrowDown__
     */
    public void setRsv010grpNarrowDown(int rsv010grpNarrowDown) {
        rsv010grpNarrowDown__ = rsv010grpNarrowDown;
    }
    /**
     * <p>rsv010sisetuSmoky を取得します。
     * @return rsv010sisetuSmoky
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuSmoky__
     */
    public int getRsv010sisetuSmoky() {
        return rsv010sisetuSmoky__;
    }
    /**
     * <p>rsv010sisetuSmoky をセットします。
     * @param rsv010sisetuSmoky rsv010sisetuSmoky
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuSmoky__
     */
    public void setRsv010sisetuSmoky(int rsv010sisetuSmoky) {
        rsv010sisetuSmoky__ = rsv010sisetuSmoky;
    }
    /**
     * <p>rsv010sisetuChere を取得します。
     * @return rsv010sisetuChere
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuChere__
     */
    public String getRsv010sisetuChere() {
        return rsv010sisetuChere__;
    }
    /**
     * <p>rsv010sisetuChere をセットします。
     * @param rsv010sisetuChere rsv010sisetuChere
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuChere__
     */
    public void setRsv010sisetuChere(String rsv010sisetuChere) {
        rsv010sisetuChere__ = rsv010sisetuChere;
    }
    /**
     * <p>rsv010sisetuTakeout を取得します。
     * @return rsv010sisetuTakeout
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuTakeout__
     */
    public int getRsv010sisetuTakeout() {
        return rsv010sisetuTakeout__;
    }
    /**
     * <p>rsv010sisetuTakeout をセットします。
     * @param rsv010sisetuTakeout rsv010sisetuTakeout
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuTakeout__
     */
    public void setRsv010sisetuTakeout(int rsv010sisetuTakeout) {
        rsv010sisetuTakeout__ = rsv010sisetuTakeout;
    }
    /**
     * <p>rsv010svSisetuKeyword を取得します。
     * @return rsv010svSisetuKeyword
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeyword__
     */
    public String getRsv010svSisetuKeyword() {
        return rsv010svSisetuKeyword__;
    }
    /**
     * <p>rsv010svSisetuKeyword をセットします。
     * @param rsv010svSisetuKeyword rsv010svSisetuKeyword
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeyword__
     */
    public void setRsv010svSisetuKeyword(String rsv010svSisetuKeyword) {
        rsv010svSisetuKeyword__ = rsv010svSisetuKeyword;
    }
    /**
     * <p>rsv010svKeyWordkbn を取得します。
     * @return rsv010svKeyWordkbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svKeyWordkbn__
     */
    public int getRsv010svKeyWordkbn() {
        return rsv010svKeyWordkbn__;
    }
    /**
     * <p>rsv010svKeyWordkbn をセットします。
     * @param rsv010svKeyWordkbn rsv010svKeyWordkbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svKeyWordkbn__
     */
    public void setRsv010svKeyWordkbn(int rsv010svKeyWordkbn) {
        rsv010svKeyWordkbn__ = rsv010svKeyWordkbn;
    }
    /**
     * <p>rsv010svSisetuFree を取得します。
     * @return rsv010svSisetuFree
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFree__
     */
    public int getRsv010svSisetuFree() {
        return rsv010svSisetuFree__;
    }
    /**
     * <p>rsv010svSisetuFree をセットします。
     * @param rsv010svSisetuFree rsv010svSisetuFree
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFree__
     */
    public void setRsv010svSisetuFree(int rsv010svSisetuFree) {
        rsv010svSisetuFree__ = rsv010svSisetuFree;
    }
    /**
     * <p>rsv010svSisetuFreeFromY を取得します。
     * @return rsv010svSisetuFreeFromY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromY__
     */
    public int getRsv010svSisetuFreeFromY() {
        return rsv010svSisetuFreeFromY__;
    }
    /**
     * <p>rsv010svSisetuFreeFromY をセットします。
     * @param rsv010svSisetuFreeFromY rsv010svSisetuFreeFromY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromY__
     */
    public void setRsv010svSisetuFreeFromY(int rsv010svSisetuFreeFromY) {
        rsv010svSisetuFreeFromY__ = rsv010svSisetuFreeFromY;
    }
    /**
     * <p>rsv010svSisetuFreeFromMo を取得します。
     * @return rsv010svSisetuFreeFromMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromMo__
     */
    public int getRsv010svSisetuFreeFromMo() {
        return rsv010svSisetuFreeFromMo__;
    }
    /**
     * <p>rsv010svSisetuFreeFromMo をセットします。
     * @param rsv010svSisetuFreeFromMo rsv010svSisetuFreeFromMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromMo__
     */
    public void setRsv010svSisetuFreeFromMo(int rsv010svSisetuFreeFromMo) {
        rsv010svSisetuFreeFromMo__ = rsv010svSisetuFreeFromMo;
    }
    /**
     * <p>rsv010svSisetuFreeFromD を取得します。
     * @return rsv010svSisetuFreeFromD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromD__
     */
    public int getRsv010svSisetuFreeFromD() {
        return rsv010svSisetuFreeFromD__;
    }
    /**
     * <p>rsv010svSisetuFreeFromD をセットします。
     * @param rsv010svSisetuFreeFromD rsv010svSisetuFreeFromD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromD__
     */
    public void setRsv010svSisetuFreeFromD(int rsv010svSisetuFreeFromD) {
        rsv010svSisetuFreeFromD__ = rsv010svSisetuFreeFromD;
    }
    /**
     * <p>rsv010svSisetuFreeFromH を取得します。
     * @return rsv010svSisetuFreeFromH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromH__
     */
    public int getRsv010svSisetuFreeFromH() {
        return rsv010svSisetuFreeFromH__;
    }
    /**
     * <p>rsv010svSisetuFreeFromH をセットします。
     * @param rsv010svSisetuFreeFromH rsv010svSisetuFreeFromH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromH__
     */
    public void setRsv010svSisetuFreeFromH(int rsv010svSisetuFreeFromH) {
        rsv010svSisetuFreeFromH__ = rsv010svSisetuFreeFromH;
    }
    /**
     * <p>rsv010svSisetuFreeFromMi を取得します。
     * @return rsv010svSisetuFreeFromMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromMi__
     */
    public int getRsv010svSisetuFreeFromMi() {
        return rsv010svSisetuFreeFromMi__;
    }
    /**
     * <p>rsv010svSisetuFreeFromMi をセットします。
     * @param rsv010svSisetuFreeFromMi rsv010svSisetuFreeFromMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeFromMi__
     */
    public void setRsv010svSisetuFreeFromMi(int rsv010svSisetuFreeFromMi) {
        rsv010svSisetuFreeFromMi__ = rsv010svSisetuFreeFromMi;
    }
    /**
     * <p>rsv010svSisetuFreeToY を取得します。
     * @return rsv010svSisetuFreeToY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToY__
     */
    public int getRsv010svSisetuFreeToY() {
        return rsv010svSisetuFreeToY__;
    }
    /**
     * <p>rsv010svSisetuFreeToY をセットします。
     * @param rsv010svSisetuFreeToY rsv010svSisetuFreeToY
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToY__
     */
    public void setRsv010svSisetuFreeToY(int rsv010svSisetuFreeToY) {
        rsv010svSisetuFreeToY__ = rsv010svSisetuFreeToY;
    }
    /**
     * <p>rsv010svSisetuFreeToMo を取得します。
     * @return rsv010svSisetuFreeToMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToMo__
     */
    public int getRsv010svSisetuFreeToMo() {
        return rsv010svSisetuFreeToMo__;
    }
    /**
     * <p>rsv010svSisetuFreeToMo をセットします。
     * @param rsv010svSisetuFreeToMo rsv010svSisetuFreeToMo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToMo__
     */
    public void setRsv010svSisetuFreeToMo(int rsv010svSisetuFreeToMo) {
        rsv010svSisetuFreeToMo__ = rsv010svSisetuFreeToMo;
    }
    /**
     * <p>rsv010svSisetuFreeToD を取得します。
     * @return rsv010svSisetuFreeToD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToD__
     */
    public int getRsv010svSisetuFreeToD() {
        return rsv010svSisetuFreeToD__;
    }
    /**
     * <p>rsv010svSisetuFreeToD をセットします。
     * @param rsv010svSisetuFreeToD rsv010svSisetuFreeToD
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToD__
     */
    public void setRsv010svSisetuFreeToD(int rsv010svSisetuFreeToD) {
        rsv010svSisetuFreeToD__ = rsv010svSisetuFreeToD;
    }
    /**
     * <p>rsv010svSisetuFreeToH を取得します。
     * @return rsv010svSisetuFreeToH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToH__
     */
    public int getRsv010svSisetuFreeToH() {
        return rsv010svSisetuFreeToH__;
    }
    /**
     * <p>rsv010svSisetuFreeToH をセットします。
     * @param rsv010svSisetuFreeToH rsv010svSisetuFreeToH
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToH__
     */
    public void setRsv010svSisetuFreeToH(int rsv010svSisetuFreeToH) {
        rsv010svSisetuFreeToH__ = rsv010svSisetuFreeToH;
    }
    /**
     * <p>rsv010svSisetuFreeToMi を取得します。
     * @return rsv010svSisetuFreeToMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToMi__
     */
    public int getRsv010svSisetuFreeToMi() {
        return rsv010svSisetuFreeToMi__;
    }
    /**
     * <p>rsv010svSisetuFreeToMi をセットします。
     * @param rsv010svSisetuFreeToMi rsv010svSisetuFreeToMi
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuFreeToMi__
     */
    public void setRsv010svSisetuFreeToMi(int rsv010svSisetuFreeToMi) {
        rsv010svSisetuFreeToMi__ = rsv010svSisetuFreeToMi;
    }
    /**
     * <p>rsv010svSisetuKbn を取得します。
     * @return rsv010svSisetuKbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKbn__
     */
    public int getRsv010svSisetuKbn() {
        return rsv010svSisetuKbn__;
    }
    /**
     * <p>rsv010svSisetuKbn をセットします。
     * @param rsv010svSisetuKbn rsv010svSisetuKbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKbn__
     */
    public void setRsv010svSisetuKbn(int rsv010svSisetuKbn) {
        rsv010svSisetuKbn__ = rsv010svSisetuKbn;
    }
    /**
     * <p>rsv010svGrpNarrowDown を取得します。
     * @return rsv010svGrpNarrowDown
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svGrpNarrowDown__
     */
    public int getRsv010svGrpNarrowDown() {
        return rsv010svGrpNarrowDown__;
    }
    /**
     * <p>rsv010svGrpNarrowDown をセットします。
     * @param rsv010svGrpNarrowDown rsv010svGrpNarrowDown
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svGrpNarrowDown__
     */
    public void setRsv010svGrpNarrowDown(int rsv010svGrpNarrowDown) {
        rsv010svGrpNarrowDown__ = rsv010svGrpNarrowDown;
    }
    /**
     * <p>rsv010svSisetuSmoky を取得します。
     * @return rsv010svSisetuSmoky
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuSmoky__
     */
    public int getRsv010svSisetuSmoky() {
        return rsv010svSisetuSmoky__;
    }
    /**
     * <p>rsv010svSisetuSmoky をセットします。
     * @param rsv010svSisetuSmoky rsv010svSisetuSmoky
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuSmoky__
     */
    public void setRsv010svSisetuSmoky(int rsv010svSisetuSmoky) {
        rsv010svSisetuSmoky__ = rsv010svSisetuSmoky;
    }
    /**
     * <p>rsv010svSisetuChere を取得します。
     * @return rsv010svSisetuChere
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuChere__
     */
    public String getRsv010svSisetuChere() {
        return rsv010svSisetuChere__;
    }
    /**
     * <p>rsv010svSisetuChere をセットします。
     * @param rsv010svSisetuChere rsv010svSisetuChere
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuChere__
     */
    public void setRsv010svSisetuChere(String rsv010svSisetuChere) {
        rsv010svSisetuChere__ = rsv010svSisetuChere;
    }
    /**
     * <p>rsv010svSisetuTakeout を取得します。
     * @return rsv010svSisetuTakeout
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuTakeout__
     */
    public int getRsv010svSisetuTakeout() {
        return rsv010svSisetuTakeout__;
    }
    /**
     * <p>rsv010svSisetuTakeout をセットします。
     * @param rsv010svSisetuTakeout rsv010svSisetuTakeout
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuTakeout__
     */
    public void setRsv010svSisetuTakeout(int rsv010svSisetuTakeout) {
        rsv010svSisetuTakeout__ = rsv010svSisetuTakeout;
    }
    /**
     * <p>rsv010svSisetuKeywordSisan を取得します。
     * @return rsv010svSisetuKeywordSisan
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordSisan__
     */
    public int getRsv010svSisetuKeywordSisan() {
        return rsv010svSisetuKeywordSisan__;
    }
    /**
     * <p>rsv010svSisetuKeywordSisan をセットします。
     * @param rsv010svSisetuKeywordSisan rsv010svSisetuKeywordSisan
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordSisan__
     */
    public void setRsv010svSisetuKeywordSisan(int rsv010svSisetuKeywordSisan) {
        rsv010svSisetuKeywordSisan__ = rsv010svSisetuKeywordSisan;
    }
    /**
     * <p>rsv010svSisetuKeywordSisetu を取得します。
     * @return rsv010svSisetuKeywordSisetu
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordSisetu__
     */
    public int getRsv010svSisetuKeywordSisetu() {
        return rsv010svSisetuKeywordSisetu__;
    }
    /**
     * <p>rsv010svSisetuKeywordSisetu をセットします。
     * @param rsv010svSisetuKeywordSisetu rsv010svSisetuKeywordSisetu
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordSisetu__
     */
    public void setRsv010svSisetuKeywordSisetu(int rsv010svSisetuKeywordSisetu) {
        rsv010svSisetuKeywordSisetu__ = rsv010svSisetuKeywordSisetu;
    }
    /**
     * <p>rsv010svSisetuKeywordBiko を取得します。
     * @return rsv010svSisetuKeywordBiko
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordBiko__
     */
    public int getRsv010svSisetuKeywordBiko() {
        return rsv010svSisetuKeywordBiko__;
    }
    /**
     * <p>rsv010svSisetuKeywordBiko をセットします。
     * @param rsv010svSisetuKeywordBiko rsv010svSisetuKeywordBiko
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordBiko__
     */
    public void setRsv010svSisetuKeywordBiko(int rsv010svSisetuKeywordBiko) {
        rsv010svSisetuKeywordBiko__ = rsv010svSisetuKeywordBiko;
    }
    /**
     * <p>rsv010svSisetuKeywordNo を取得します。
     * @return rsv010svSisetuKeywordNo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordNo__
     */
    public int getRsv010svSisetuKeywordNo() {
        return rsv010svSisetuKeywordNo__;
    }
    /**
     * <p>rsv010svSisetuKeywordNo をセットします。
     * @param rsv010svSisetuKeywordNo rsv010svSisetuKeywordNo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordNo__
     */
    public void setRsv010svSisetuKeywordNo(int rsv010svSisetuKeywordNo) {
        rsv010svSisetuKeywordNo__ = rsv010svSisetuKeywordNo;
    }
    /**
     * <p>rsv010svSisetuKeywordIsbn を取得します。
     * @return rsv010svSisetuKeywordIsbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordIsbn__
     */
    public int getRsv010svSisetuKeywordIsbn() {
        return rsv010svSisetuKeywordIsbn__;
    }
    /**
     * <p>rsv010svSisetuKeywordIsbn をセットします。
     * @param rsv010svSisetuKeywordIsbn rsv010svSisetuKeywordIsbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010svSisetuKeywordIsbn__
     */
    public void setRsv010svSisetuKeywordIsbn(int rsv010svSisetuKeywordIsbn) {
        rsv010svSisetuKeywordIsbn__ = rsv010svSisetuKeywordIsbn;
    }
    /**
     * <p>rsv010sisetuKeywordSisan を取得します。
     * @return rsv010sisetuKeywordSisan
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordSisan__
     */
    public int getRsv010sisetuKeywordSisan() {
        return rsv010sisetuKeywordSisan__;
    }
    /**
     * <p>rsv010sisetuKeywordSisan をセットします。
     * @param rsv010sisetuKeywordSisan rsv010sisetuKeywordSisan
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordSisan__
     */
    public void setRsv010sisetuKeywordSisan(int rsv010sisetuKeywordSisan) {
        rsv010sisetuKeywordSisan__ = rsv010sisetuKeywordSisan;
    }
    /**
     * <p>rsv010sisetuKeywordSisetu を取得します。
     * @return rsv010sisetuKeywordSisetu
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordSisetu__
     */
    public int getRsv010sisetuKeywordSisetu() {
        return rsv010sisetuKeywordSisetu__;
    }
    /**
     * <p>rsv010sisetuKeywordSisetu をセットします。
     * @param rsv010sisetuKeywordSisetu rsv010sisetuKeywordSisetu
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordSisetu__
     */
    public void setRsv010sisetuKeywordSisetu(int rsv010sisetuKeywordSisetu) {
        rsv010sisetuKeywordSisetu__ = rsv010sisetuKeywordSisetu;
    }
    /**
     * <p>rsv010sisetuKeywordBiko を取得します。
     * @return rsv010sisetuKeywordBiko
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordBiko__
     */
    public int getRsv010sisetuKeywordBiko() {
        return rsv010sisetuKeywordBiko__;
    }
    /**
     * <p>rsv010sisetuKeywordBiko をセットします。
     * @param rsv010sisetuKeywordBiko rsv010sisetuKeywordBiko
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordBiko__
     */
    public void setRsv010sisetuKeywordBiko(int rsv010sisetuKeywordBiko) {
        rsv010sisetuKeywordBiko__ = rsv010sisetuKeywordBiko;
    }
    /**
     * <p>rsv010sisetuKeywordNo を取得します。
     * @return rsv010sisetuKeywordNo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordNo__
     */
    public int getRsv010sisetuKeywordNo() {
        return rsv010sisetuKeywordNo__;
    }
    /**
     * <p>rsv010sisetuKeywordNo をセットします。
     * @param rsv010sisetuKeywordNo rsv010sisetuKeywordNo
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordNo__
     */
    public void setRsv010sisetuKeywordNo(int rsv010sisetuKeywordNo) {
        rsv010sisetuKeywordNo__ = rsv010sisetuKeywordNo;
    }
    /**
     * <p>rsv010sisetuKeywordIsbn を取得します。
     * @return rsv010sisetuKeywordIsbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordIsbn__
     */
    public int getRsv010sisetuKeywordIsbn() {
        return rsv010sisetuKeywordIsbn__;
    }
    /**
     * <p>rsv010sisetuKeywordIsbn をセットします。
     * @param rsv010sisetuKeywordIsbn rsv010sisetuKeywordIsbn
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010sisetuKeywordIsbn__
     */
    public void setRsv010sisetuKeywordIsbn(int rsv010sisetuKeywordIsbn) {
        rsv010sisetuKeywordIsbn__ = rsv010sisetuKeywordIsbn;
    }
    /**
     * <p>rsv010InitFlg を取得します。
     * @return rsv010InitFlg
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010InitFlg__
     */
    public int getRsv010InitFlg() {
        return rsv010InitFlg__;
    }
    /**
     * <p>rsv010InitFlg をセットします。
     * @param rsv010InitFlg rsv010InitFlg
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010InitFlg__
     */
    public void setRsv010InitFlg(int rsv010InitFlg) {
        rsv010InitFlg__ = rsv010InitFlg;
    }
    /**
     * <p>rsv010SiborikomiFlg を取得します。
     * @return rsv010SiborikomiFlg
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010SiborikomiFlg__
     */
    public int getRsv010SiborikomiFlg() {
        return rsv010SiborikomiFlg__;
    }
    /**
     * <p>rsv010SiborikomiFlg をセットします。
     * @param rsv010SiborikomiFlg rsv010SiborikomiFlg
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv010SiborikomiFlg__
     */
    public void setRsv010SiborikomiFlg(int rsv010SiborikomiFlg) {
        rsv010SiborikomiFlg__ = rsv010SiborikomiFlg;
    }
    /**
     * <p>rsv100selectedFromDate を取得します。
     * @return rsv100selectedFromDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv100selectedFromDate__
     */
    public String getRsv100selectedFromDate() {
        return rsv100selectedFromDate__;
    }
    /**
     * <p>rsv100selectedFromDate をセットします。
     * @param rsv100selectedFromDate rsv100selectedFromDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv100selectedFromDate__
     */
    public void setRsv100selectedFromDate(String rsv100selectedFromDate) {
        rsv100selectedFromDate__ = rsv100selectedFromDate;
    }
    /**
     * <p>rsv100selectedToDate を取得します。
     * @return rsv100selectedToDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv100selectedToDate__
     */
    public String getRsv100selectedToDate() {
        return rsv100selectedToDate__;
    }
    /**
     * <p>rsv100selectedToDate をセットします。
     * @param rsv100selectedToDate rsv100selectedToDate
     * @see jp.groupsession.v2.rsv.AbstractReserveForm#rsv100selectedToDate__
     */
    public void setRsv100selectedToDate(String rsv100selectedToDate) {
        rsv100selectedToDate__ = rsv100selectedToDate;
    }
}