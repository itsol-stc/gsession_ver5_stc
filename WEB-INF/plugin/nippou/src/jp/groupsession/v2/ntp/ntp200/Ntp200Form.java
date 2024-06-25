package jp.groupsession.v2.ntp.ntp200;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 案件検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp200Form extends Ntp060Form {

    /** -------------------------案件選択画面パラメータ-------------------------  */

    /** 遷移元 */
    private String ntp200ReturnPage__ = null;
    /** 案件一覧 */
    private ArrayList<Ntp200AnkenModel> ntp200AnkenList__ = null;
    /** 案件SID */
    private int ntp200NanSid__ = -1;
    /** 処理モード */
    private String ntp200ProcMode__ = GSConstNippou.CMD_ADD;
    /** ソートキーリスト一覧 */
    private List<LabelValueBean> ntp200SortList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> ntp200PageCmbList__ = null;
    /** 業務リスト一覧 */
    private List<LabelValueBean> ntp200GyomuList__ = null;
    /** プロセスリスト一覧 */
    private List<LabelValueBean> ntp200ProcessList__ = null;
    /** コンタクトリスト一覧 */
    private List<LabelValueBean> ntp200ContactList__ = null;
    /** 状態一覧 */
    private List<LabelValueBean> ntp200StateList__ = null;
    /** 状態一覧 */
    private List<LabelValueBean> ntp200AnkenStateList__ = null;
    /** 年リスト */
    private List<LabelValueBean> ntp200YearList__ = null;
    /** 月リスト */
    private List<LabelValueBean> ntp200MonthList__ = null;
    /** 日リスト */
    private List<LabelValueBean> ntp200DayList__ = null;

    /** 初期化フラグ */
    private int ntp200InitFlg__ = 0;
    /** ページ */
    private int ntp200Page__ = 0;
    /** ページ上段 */
    private int ntp200PageTop__ = 0;
    /** ページ下段 */
    private int ntp200PageBottom__ = 0;
    /** ソートキー１ */
    private int ntp200SortKey1__ = GSConstNippou.ANKEN_SEARCH_SORT_KOUSHIN;
    /** オーダキー１ */
    private int ntp200OrderKey1__ = GSConstNippou.ORDER_KEY_DESC;
    /** ソートキー２ */
    private int ntp200SortKey2__ = -1;
    /** オーダキー２ */
    private int ntp200OrderKey2__ = GSConstNippou.ORDER_KEY_ASC;
    /** 企業コード */
    private String ntp200AcoCode__ = null;
    /** 企業名 */
    private String ntp200AcoName__ = null;
    /** 企業名カナ */
    private String ntp200AcoNameKana__ = null;
    /** 拠点名 */
    private String ntp200AbaName__ = null;
    /** 商品名 */
    private List<NtpShohinModel> ntp200ShohinList__ = null;
    /** 業務内容SID */
    private int ntp200NgySid__ = -1;
    /** プロセスSID */
    private int ntp200NgpSid__ = -1;
    /** コンタクトSID */
    private int ntp200NcnSid__ = -1;
    /** 見込度 */
    private String[] ntp200Mikomi__ = null;
    /** 商談結果 */
    private String[] ntp200Syodan__ = null;
    /** 案件コード */
    private String ntp200NanCode__ = null;
    /** 案件名 */
    private String ntp200NanName__ = null;
    /** 状態 */
    private int ntp200State__ = GSConstNippou.STATE_UNCOMPLETE;
    /** 案件状態 */
    private int ntp200AnkenState__ = GSConstNippou.SYODAN_CHU;
    /** 見積金額 */
    private String ntp200NanKinMitumori__ = null;
    /** 受注金額 */
    private String ntp200NanKinJutyu__ = null;
    /** 見積金額区分 */
    private int ntp200NanKinMitumoriKbn__ = Ntp200Biz.PRICE_MORE;
    /** 受注金額区分 */
    private int ntp200NanKinJutyuKbn__ = Ntp200Biz.PRICE_MORE;
    /** 案件登録 開始年 */
    private String ntp200FrYear__ = null;
    /** 案件登録 開始月 */
    private String ntp200FrMonth__ = null;
    /** 案件登録 開始日 */
    private String ntp200FrDay__ = null;
    /** 案件登録 終了年 */
    private String ntp200ToYear__ = null;
    /** 案件登録 終了月 */
    private String ntp200ToMonth__ = null;
    /** 案件登録 終了日 */
    private String ntp200ToDay__ = null;

    /** 親画面ID */
    private String ntp200parentPageId__ = null;
    /** 親画面行番号 */
    private String ntp200RowNumber__ = null;

    /** 商品名 */
    private String ntp200ShohinName__ = null;
    /** カテゴリリスト一覧 */
    private List<LabelValueBean> ntp200CategoryList__ = null;
    /** カテゴリSID */
    private int ntp200CatSid__ = -1;

    /**
     * @return ntp200ReturnPage
     */
    public String getNtp200ReturnPage() {
        return ntp200ReturnPage__;
    }
    /**
     * @param ntp200ReturnPage 設定する ntp200ReturnPage
     */
    public void setNtp200ReturnPage(String ntp200ReturnPage) {
        ntp200ReturnPage__ = ntp200ReturnPage;
    }
    /**
     * @return ntp200InitFlg
     */
    public int getNtp200InitFlg() {
        return ntp200InitFlg__;
    }
    /**
     * @param ntp200InitFlg 設定する ntp200InitFlg
     */
    public void setNtp200InitFlg(int ntp200InitFlg) {
        ntp200InitFlg__ = ntp200InitFlg;
    }
    /**
     * @return ntp200AbaName
     */
    public String getNtp200AbaName() {
        return ntp200AbaName__;
    }
    /**
     * @param ntp200AbaName 設定する ntp200AbaName
     */
    public void setNtp200AbaName(String ntp200AbaName) {
        ntp200AbaName__ = ntp200AbaName;
    }
    /**
     * @return ntp200AcoCode
     */
    public String getNtp200AcoCode() {
        return ntp200AcoCode__;
    }
    /**
     * @param ntp200AcoCode 設定する ntp200AcoCode
     */
    public void setNtp200AcoCode(String ntp200AcoCode) {
        ntp200AcoCode__ = ntp200AcoCode;
    }
    /**
     * @return ntp200AcoName
     */
    public String getNtp200AcoName() {
        return ntp200AcoName__;
    }
    /**
     * @param ntp200AcoName 設定する ntp200AcoName
     */
    public void setNtp200AcoName(String ntp200AcoName) {
        ntp200AcoName__ = ntp200AcoName;
    }
    /**
     * @return ntp200AcoNameKana
     */
    public String getNtp200AcoNameKana() {
        return ntp200AcoNameKana__;
    }
    /**
     * @param ntp200AcoNameKana 設定する ntp200AcoNameKana
     */
    public void setNtp200AcoNameKana(String ntp200AcoNameKana) {
        ntp200AcoNameKana__ = ntp200AcoNameKana;
    }
    /**
     * @return ntp200ContactList
     */
    public List<LabelValueBean> getNtp200ContactList() {
        return ntp200ContactList__;
    }
    /**
     * @param ntp200ContactList 設定する ntp200ContactList
     */
    public void setNtp200ContactList(List<LabelValueBean> ntp200ContactList) {
        ntp200ContactList__ = ntp200ContactList;
    }
    /**
     * @return ntp200DayList
     */
    public List<LabelValueBean> getNtp200DayList() {
        return ntp200DayList__;
    }
    /**
     * @param ntp200DayList 設定する ntp200DayList
     */
    public void setNtp200DayList(List<LabelValueBean> ntp200DayList) {
        ntp200DayList__ = ntp200DayList;
    }
    /**
     * @return ntp200FrDay
     */
    public String getNtp200FrDay() {
        return ntp200FrDay__;
    }
    /**
     * @param ntp200FrDay 設定する ntp200FrDay
     */
    public void setNtp200FrDay(String ntp200FrDay) {
        ntp200FrDay__ = ntp200FrDay;
    }
    /**
     * @return ntp200FrMonth
     */
    public String getNtp200FrMonth() {
        return ntp200FrMonth__;
    }
    /**
     * @param ntp200FrMonth 設定する ntp200FrMonth
     */
    public void setNtp200FrMonth(String ntp200FrMonth) {
        ntp200FrMonth__ = ntp200FrMonth;
    }
    /**
     * @return ntp200FrYear
     */
    public String getNtp200FrYear() {
        return ntp200FrYear__;
    }
    /**
     * @param ntp200FrYear 設定する ntp200FrYear
     */
    public void setNtp200FrYear(String ntp200FrYear) {
        ntp200FrYear__ = ntp200FrYear;
    }
    /**
     * @return ntp200GyomuList
     */
    public List<LabelValueBean> getNtp200GyomuList() {
        return ntp200GyomuList__;
    }
    /**
     * @param ntp200GyomuList 設定する ntp200GyomuList
     */
    public void setNtp200GyomuList(List<LabelValueBean> ntp200GyomuList) {
        ntp200GyomuList__ = ntp200GyomuList;
    }
    /**
     * @return ntp200Mikomi
     */
    public String[] getNtp200Mikomi() {
        return ntp200Mikomi__;
    }
    /**
     * @param ntp200Mikomi 設定する ntp200Mikomi
     */
    public void setNtp200Mikomi(String[] ntp200Mikomi) {
        ntp200Mikomi__ = ntp200Mikomi;
    }
    /**
     * @return ntp200Syodan
     */
    public String[] getNtp200Syodan() {
        return ntp200Syodan__;
    }
    /**
     * @param ntp200Syodan 設定する ntp200Syodan
     */
    public void setNtp200Syodan(String[] ntp200Syodan) {
        ntp200Syodan__ = ntp200Syodan;
    }
    /**
     * @return ntp200MonthList
     */
    public List<LabelValueBean> getNtp200MonthList() {
        return ntp200MonthList__;
    }
    /**
     * @param ntp200MonthList 設定する ntp200MonthList
     */
    public void setNtp200MonthList(List<LabelValueBean> ntp200MonthList) {
        ntp200MonthList__ = ntp200MonthList;
    }
    /**
     * @return ntp200NanCode
     */
    public String getNtp200NanCode() {
        return ntp200NanCode__;
    }
    /**
     * @param ntp200NanCode 設定する ntp200NanCode
     */
    public void setNtp200NanCode(String ntp200NanCode) {
        ntp200NanCode__ = ntp200NanCode;
    }
    /**
     * @return ntp200NanKinJutyu
     */
    public String getNtp200NanKinJutyu() {
        return ntp200NanKinJutyu__;
    }
    /**
     * @param ntp200NanKinJutyu 設定する ntp200NanKinJutyu
     */
    public void setNtp200NanKinJutyu(String ntp200NanKinJutyu) {
        ntp200NanKinJutyu__ = ntp200NanKinJutyu;
    }
    /**
     * @return ntp200NanKinJutyuKbn
     */
    public int getNtp200NanKinJutyuKbn() {
        return ntp200NanKinJutyuKbn__;
    }
    /**
     * @param ntp200NanKinJutyuKbn 設定する ntp200NanKinJutyuKbn
     */
    public void setNtp200NanKinJutyuKbn(int ntp200NanKinJutyuKbn) {
        ntp200NanKinJutyuKbn__ = ntp200NanKinJutyuKbn;
    }
    /**
     * @return ntp200NanKinMitumori
     */
    public String getNtp200NanKinMitumori() {
        return ntp200NanKinMitumori__;
    }
    /**
     * @param ntp200NanKinMitumori 設定する ntp200NanKinMitumori
     */
    public void setNtp200NanKinMitumori(String ntp200NanKinMitumori) {
        ntp200NanKinMitumori__ = ntp200NanKinMitumori;
    }
    /**
     * @return ntp200NanKinMitumoriKbn
     */
    public int getNtp200NanKinMitumoriKbn() {
        return ntp200NanKinMitumoriKbn__;
    }
    /**
     * @param ntp200NanKinMitumoriKbn 設定する ntp200NanKinMitumoriKbn
     */
    public void setNtp200NanKinMitumoriKbn(int ntp200NanKinMitumoriKbn) {
        ntp200NanKinMitumoriKbn__ = ntp200NanKinMitumoriKbn;
    }
    /**
     * @return ntp200NanName
     */
    public String getNtp200NanName() {
        return ntp200NanName__;
    }
    /**
     * @param ntp200NanName 設定する ntp200NanName
     */
    public void setNtp200NanName(String ntp200NanName) {
        ntp200NanName__ = ntp200NanName;
    }
    /**
     * @return ntp200NcnSid
     */
    public int getNtp200NcnSid() {
        return ntp200NcnSid__;
    }
    /**
     * @param ntp200NcnSid 設定する ntp200NcnSid
     */
    public void setNtp200NcnSid(int ntp200NcnSid) {
        ntp200NcnSid__ = ntp200NcnSid;
    }
    /**
     * @return ntp200NgpSid
     */
    public int getNtp200NgpSid() {
        return ntp200NgpSid__;
    }
    /**
     * @param ntp200NgpSid 設定する ntp200NgpSid
     */
    public void setNtp200NgpSid(int ntp200NgpSid) {
        ntp200NgpSid__ = ntp200NgpSid;
    }
    /**
     * @return ntp200NgySid
     */
    public int getNtp200NgySid() {
        return ntp200NgySid__;
    }
    /**
     * @param ntp200NgySid 設定する ntp200NgySid
     */
    public void setNtp200NgySid(int ntp200NgySid) {
        ntp200NgySid__ = ntp200NgySid;
    }
    /**
     * @return ntp200NanSid
     */
    public int getNtp200NanSid() {
        return ntp200NanSid__;
    }
    /**
     * @param ntp200NanSid 設定する ntp200NanSid
     */
    public void setNtp200NanSid(int ntp200NanSid) {
        ntp200NanSid__ = ntp200NanSid;
    }
    /**
     * @return ntp200OrderKey1
     */
    public int getNtp200OrderKey1() {
        return ntp200OrderKey1__;
    }
    /**
     * @param ntp200OrderKey1 設定する ntp200OrderKey1
     */
    public void setNtp200OrderKey1(int ntp200OrderKey1) {
        ntp200OrderKey1__ = ntp200OrderKey1;
    }
    /**
     * @return ntp200OrderKey2
     */
    public int getNtp200OrderKey2() {
        return ntp200OrderKey2__;
    }
    /**
     * @param ntp200OrderKey2 設定する ntp200OrderKey2
     */
    public void setNtp200OrderKey2(int ntp200OrderKey2) {
        ntp200OrderKey2__ = ntp200OrderKey2;
    }
    /**
     * @return ntp200Page
     */
    public int getNtp200Page() {
        return ntp200Page__;
    }
    /**
     * @param ntp200Page 設定する ntp200Page
     */
    public void setNtp200Page(int ntp200Page) {
        ntp200Page__ = ntp200Page;
    }
    /**
     * @return ntp200PageBottom
     */
    public int getNtp200PageBottom() {
        return ntp200PageBottom__;
    }
    /**
     * @param ntp200PageBottom 設定する ntp200PageBottom
     */
    public void setNtp200PageBottom(int ntp200PageBottom) {
        ntp200PageBottom__ = ntp200PageBottom;
    }
    /**
     * @return ntp200PageCmbList
     */
    public List<LabelValueBean> getNtp200PageCmbList() {
        return ntp200PageCmbList__;
    }
    /**
     * @param ntp200PageCmbList 設定する ntp200PageCmbList
     */
    public void setNtp200PageCmbList(List<LabelValueBean> ntp200PageCmbList) {
        ntp200PageCmbList__ = ntp200PageCmbList;
    }
    /**
     * @return ntp200PageTop
     */
    public int getNtp200PageTop() {
        return ntp200PageTop__;
    }
    /**
     * @param ntp200PageTop 設定する ntp200PageTop
     */
    public void setNtp200PageTop(int ntp200PageTop) {
        ntp200PageTop__ = ntp200PageTop;
    }
    /**
     * @return ntp200ProcessList
     */
    public List<LabelValueBean> getNtp200ProcessList() {
        return ntp200ProcessList__;
    }
    /**
     * @param ntp200ProcessList 設定する ntp200ProcessList
     */
    public void setNtp200ProcessList(List<LabelValueBean> ntp200ProcessList) {
        ntp200ProcessList__ = ntp200ProcessList;
    }
    /**
     * @return ntp200ProcMode
     */
    public String getNtp200ProcMode() {
        return ntp200ProcMode__;
    }
    /**
     * @param ntp200ProcMode 設定する ntp200ProcMode
     */
    public void setNtp200ProcMode(String ntp200ProcMode) {
        ntp200ProcMode__ = ntp200ProcMode;
    }
    /**
     * @return ntp200Sort1List
     */
    public List<LabelValueBean> getNtp200SortList() {
        return ntp200SortList__;
    }
    /**
     * @param ntp200SortList 設定する ntp200SortList
     */
    public void setNtp200SortList(List<LabelValueBean> ntp200SortList) {
        ntp200SortList__ = ntp200SortList;
    }

    /**
     * @return ntp200SortKey1
     */
    public int getNtp200SortKey1() {
        return ntp200SortKey1__;
    }
    /**
     * @param ntp200SortKey1 設定する ntp200SortKey1
     */
    public void setNtp200SortKey1(int ntp200SortKey1) {
        ntp200SortKey1__ = ntp200SortKey1;
    }
    /**
     * @return ntp200SortKey2
     */
    public int getNtp200SortKey2() {
        return ntp200SortKey2__;
    }
    /**
     * @param ntp200SortKey2 設定する ntp200SortKey2
     */
    public void setNtp200SortKey2(int ntp200SortKey2) {
        ntp200SortKey2__ = ntp200SortKey2;
    }

    /**
     * @return ntp200ToDay
     */
    public String getNtp200ToDay() {
        return ntp200ToDay__;
    }
    /**
     * @param ntp200ToDay 設定する ntp200ToDay
     */
    public void setNtp200ToDay(String ntp200ToDay) {
        ntp200ToDay__ = ntp200ToDay;
    }
    /**
     * @return ntp200ToMonth
     */
    public String getNtp200ToMonth() {
        return ntp200ToMonth__;
    }
    /**
     * @param ntp200ToMonth 設定する ntp200ToMonth
     */
    public void setNtp200ToMonth(String ntp200ToMonth) {
        ntp200ToMonth__ = ntp200ToMonth;
    }
    /**
     * @return ntp200ToYear
     */
    public String getNtp200ToYear() {
        return ntp200ToYear__;
    }
    /**
     * @param ntp200ToYear 設定する ntp200ToYear
     */
    public void setNtp200ToYear(String ntp200ToYear) {
        ntp200ToYear__ = ntp200ToYear;
    }
    /**
     * @return ntp200YearList
     */
    public List<LabelValueBean> getNtp200YearList() {
        return ntp200YearList__;
    }
    /**
     * @param ntp200YearList 設定する ntp200YearList
     */
    public void setNtp200YearList(List<LabelValueBean> ntp200YearList) {
        ntp200YearList__ = ntp200YearList;
    }
    /**
     * @return ntp200AnkenList
     */
    public ArrayList<Ntp200AnkenModel> getNtp200AnkenList() {
        return ntp200AnkenList__;
    }
    /**
     * @param ntp200AnkenList 設定する ntp200AnkenList
     */
    public void setNtp200AnkenList(ArrayList<Ntp200AnkenModel> ntp200AnkenList) {
        ntp200AnkenList__ = ntp200AnkenList;
    }
    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     * @param form 案件検索フォーム
     */
    public void setNtp200HiddenParam(Cmn999Form msgForm, Ntp200Form form) {
        /** 遷移元 */
        msgForm.addHiddenParam("ntp200ReturnPage", ntp200ReturnPage__);
        /** 初期化フラグ */
        msgForm.addHiddenParam("ntp200InitFlg", ntp200InitFlg__);
        /** ページ */
        msgForm.addHiddenParam("ntp200Page", ntp200Page__);
        /** ページ上段 */
        msgForm.addHiddenParam("ntp200PageTop", ntp200PageTop__);
        /** ページ下段 */
        msgForm.addHiddenParam("ntp200PageBottom", ntp200PageBottom__);
        /** ソートキー１ */
        msgForm.addHiddenParam("ntp200SortKey1", ntp200SortKey1__);
        /** オーダキー１ */
        msgForm.addHiddenParam("ntp200OrderKey1", ntp200OrderKey1__);
        /** ソートキー２ */
        msgForm.addHiddenParam("ntp200SortKey2", ntp200SortKey2__);
        /** オーダキー２ */
        msgForm.addHiddenParam("ntp200OrderKey2", ntp200OrderKey2__);
        /** 企業コード */
        msgForm.addHiddenParam("ntp200AcoCode", ntp200AcoCode__);
        /** 企業名 */
        msgForm.addHiddenParam("ntp200AcoName", ntp200AcoName__);
        /** 企業名カナ */
        msgForm.addHiddenParam("ntp200AcoNameKana", ntp200AcoNameKana__);
        /** 拠点名 */
        msgForm.addHiddenParam("ntp200AbaName", ntp200AbaName__);
        /** 業務内容SID */
        msgForm.addHiddenParam("ntp200NgySid", ntp200NgySid__);
        /** プロセスSID */
        msgForm.addHiddenParam("ntp200NgpSid", ntp200NgpSid__);
        /** コンタクトSID */
        msgForm.addHiddenParam("ntp200NcnSid", ntp200NcnSid__);
        /** 見込度 */
        msgForm.addHiddenParam("ntp200Mikomi", ntp200Mikomi__);
        /** 商談結果 */
        msgForm.addHiddenParam("ntp200Syodan", ntp200Syodan__);
        /** 案件コード */
        msgForm.addHiddenParam("ntp200NanCode", ntp200NanCode__);
        /** 案件名 */
        msgForm.addHiddenParam("ntp200NanName", ntp200NanName__);
        /** 見積金額 */
        msgForm.addHiddenParam("ntp200NanKinMitumori", ntp200NanKinMitumori__);
        /** 受注金額 */
        msgForm.addHiddenParam("ntp200NanKinJutyu", ntp200NanKinJutyu__);
        /** 見積金額区分 */
        msgForm.addHiddenParam("ntp200NanKinMitumoriKbn", ntp200NanKinMitumoriKbn__);
        /** 受注金額区分 */
        msgForm.addHiddenParam("ntp200NanKinJutyuKbn", ntp200NanKinJutyuKbn__);
        /** 案件登録 開始年 */
        msgForm.addHiddenParam("ntp200FrYear", ntp200FrYear__);
        /** 案件登録 開始月 */
        msgForm.addHiddenParam("ntp200FrMonth", ntp200FrMonth__);
        /** 案件登録 開始日 */
        msgForm.addHiddenParam("ntp200FrDay", ntp200FrDay__);
        /** 案件登録 終了年 */
        msgForm.addHiddenParam("ntp200ToYear", ntp200ToYear__);
        /** 案件登録 終了月 */
        msgForm.addHiddenParam("ntp200ToMonth", ntp200ToMonth__);
        /** 案件登録 終了日 */
        msgForm.addHiddenParam("ntp200ToDay", ntp200ToDay__);
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck()
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        //案件コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_ANKEN_CODE,
                ntp200NanCode__,
               "ntp200NanCode",
                GSConstNippou.MAX_LENGTH_ANKEN_CODE,
                false);

        //案件名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_ANKEN_NAME,
                ntp200NanName__,
               "ntp200NanName",
                GSConstNippou.MAX_LENGTH_ANKEN_NAME,
                false);

        //見積金額入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_ANKEN_MITUMORI,
                ntp200NanKinMitumori__,
               "ntp200NanKinMitumori",
                GSConstNippou.MAX_LENGTH_ANKEN_MITUMORI,
                false);

        //受注金額入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_ANKEN_JUCYU,
                ntp200NanKinJutyu__,
               "ntp200NanKinJutyu",
                GSConstNippou.MAX_LENGTH_ANKEN_JUCYU,
                false);

        //企業コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_CODE,
                ntp200AcoCode__,
               "ntp200AcoCode",
                GSConstNippou.MAX_LENGTH_COMPANY_CODE,
                false);

        //会社名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_NAME,
                ntp200AcoName__,
               "ntp200AcoName",
                GSConstNippou.MAX_LENGTH_COMPANY_NAME,
                false);

        //会社名カナ入力チェック
        GSValidateNippou.validateCmnFieldTextKana(errors,
                GSConstNippou.TEXT_COMPANY_NAME_KN,
                ntp200AcoNameKana__,
               "会社名カナ",
                GSConstNippou.MAX_LENGTH_COMPANY_NAME_KN,
                false);

        //拠点名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_BASE_NAME,
                ntp200AbaName__,
               "ntp200AbaName",
                GSConstNippou.MAX_LENGTH_BASE_NAME,
                false);

        return errors;
    }
    /**
     * <p>ntp200parentPageId を取得します。
     * @return ntp200parentPageId
     */
    public String getNtp200parentPageId() {
        return ntp200parentPageId__;
    }
    /**
     * <p>ntp200parentPageId をセットします。
     * @param ntp200parentPageId ntp200parentPageId
     */
    public void setNtp200parentPageId(String ntp200parentPageId) {
        ntp200parentPageId__ = ntp200parentPageId;
    }
    /**
     * <p>ntp200RowNumber を取得します。
     * @return ntp200RowNumber
     */
    public String getNtp200RowNumber() {
        return ntp200RowNumber__;
    }
    /**
     * <p>ntp200RowNumber をセットします。
     * @param ntp200RowNumber ntp200RowNumber
     */
    public void setNtp200RowNumber(String ntp200RowNumber) {
        ntp200RowNumber__ = ntp200RowNumber;
    }
    /**
     * <p>ntp200StateList を取得します。
     * @return ntp200StateList
     */
    public List<LabelValueBean> getNtp200StateList() {
        return ntp200StateList__;
    }
    /**
     * <p>ntp200StateList をセットします。
     * @param ntp200StateList ntp200StateList
     */
    public void setNtp200StateList(List<LabelValueBean> ntp200StateList) {
        ntp200StateList__ = ntp200StateList;
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
     * <p>ntp200State を取得します。
     * @return ntp200State
     */
    public int getNtp200State() {
        return ntp200State__;
    }
    /**
     * <p>ntp200State をセットします。
     * @param ntp200State ntp200State
     */
    public void setNtp200State(int ntp200State) {
        ntp200State__ = ntp200State;
    }
    /**
     * <p>ntp200AnkenState を取得します。
     * @return ntp200AnkenState
     */
    public int getNtp200AnkenState() {
        return ntp200AnkenState__;
    }
    /**
     * <p>ntp200AnkenState をセットします。
     * @param ntp200AnkenState ntp200AnkenState
     */
    public void setNtp200AnkenState(int ntp200AnkenState) {
        ntp200AnkenState__ = ntp200AnkenState;
    }
    /**
     * <p>ntp200AnkenStateList を取得します。
     * @return ntp200AnkenStateList
     */
    public List<LabelValueBean> getNtp200AnkenStateList() {
        return ntp200AnkenStateList__;
    }
    /**
     * <p>ntp200AnkenStateList をセットします。
     * @param ntp200AnkenStateList ntp200AnkenStateList
     */
    public void setNtp200AnkenStateList(List<LabelValueBean> ntp200AnkenStateList) {
        ntp200AnkenStateList__ = ntp200AnkenStateList;
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
    /**
     * <p>ntp200CategoryList を取得します。
     * @return ntp200CategoryList
     */
    public List<LabelValueBean> getNtp200CategoryList() {
        return ntp200CategoryList__;
    }
    /**
     * <p>ntp200CategoryList をセットします。
     * @param ntp200CategoryList ntp200CategoryList
     */
    public void setNtp200CategoryList(List<LabelValueBean> ntp200CategoryList) {
        ntp200CategoryList__ = ntp200CategoryList;
    }
    /**
     * <p>ntp200CatSid を取得します。
     * @return ntp200CatSid
     */
    public int getNtp200CatSid() {
        return ntp200CatSid__;
    }
    /**
     * <p>ntp200CatSid をセットします。
     * @param ntp200CatSid ntp200CatSid
     */
    public void setNtp200CatSid(int ntp200CatSid) {
        ntp200CatSid__ = ntp200CatSid;
    }
}
