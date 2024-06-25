package jp.groupsession.v2.ntp.ntp060;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.ntp030.Ntp030Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 案件検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060Form extends Ntp030Form {

    /** 遷移元 */
    private String ntp060ReturnPage__ = null;
    /** 案件一覧 */
    private ArrayList<Ntp060AnkenModel> ntp060AnkenList__ = null;
    /** 案件SID */
    private int ntp060NanSid__ = -1;
    /** 処理モード */
    private String ntp060ProcMode__ = GSConstNippou.CMD_ADD;
    /** ソートキーリスト一覧 */
    private List<LabelValueBean> ntp060SortList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> ntp060PageCmbList__ = null;
    /** 業務リスト一覧 */
    private List<LabelValueBean> ntp060GyomuList__ = null;
    /** プロセスリスト一覧 */
    private List<LabelValueBean> ntp060ProcessList__ = null;
    /** コンタクトリスト一覧 */
    private List<LabelValueBean> ntp060ContactList__ = null;
    /** 状態一覧 */
    private List<LabelValueBean> ntp060StateList__ = null;
    /** 年リスト */
    private List<LabelValueBean> ntp060YearList__ = null;
    /** 月リスト */
    private List<LabelValueBean> ntp060MonthList__ = null;
    /** 日リスト */
    private List<LabelValueBean> ntp060DayList__ = null;
    /** 初期化フラグ */
    private int ntp060InitFlg__ = 0;
    /** ページ */
    private int ntp060Page__ = 0;
    /** ページ上段 */
    private int ntp060PageTop__ = 0;
    /** ページ下段 */
    private int ntp060PageBottom__ = 0;
    /** ソートキー１ */
    private int ntp060SortKey1__ = GSConstNippou.SORT_KEY_NAN_KOUSHIN;
    /** オーダキー１ */
    private int ntp060OrderKey1__ = GSConstNippou.ORDER_KEY_DESC;
    /** ソートキー２ */
    private int ntp060SortKey2__ = -1;
    /** オーダキー２ */
    private int ntp060OrderKey2__ = GSConstNippou.ORDER_KEY_ASC;
    /** 企業コード */
    private String ntp060AcoCode__ = null;
    /** 企業名 */
    private String ntp060AcoName__ = null;
    /** 企業名カナ */
    private String ntp060AcoNameKana__ = null;
    /** 拠点名 */
    private String ntp060AbaName__ = null;
    /** 商品名 */
    private String ntp060ShohinName__ = null;
    /** 業務内容SID */
    private int ntp060NgySid__ = -1;
    /** プロセスSID */
    private int ntp060NgpSid__ = -1;
    /** コンタクトSID */
    private int ntp060NcnSid__ = -1;
    /** 状態 */
    private int ntp060State__ = GSConstNippou.STATE_UNCOMPLETE;
    /** 見込度 */
    private String[] ntp060Mikomi__ = GSConstNippou.MIKOMI_ALL;
    /** 商談結果 */
    private String[] ntp060Syodan__ = GSConstNippou.SYODAN_ALL;
    /** 案件コード */
    private String ntp060NanCode__ = null;
    /** 案件名 */
    private String ntp060NanName__ = null;
    /** 見積金額 */
    private String ntp060NanKinMitumori__ = null;
    /** 受注金額 */
    private String ntp060NanKinJutyu__ = null;
    /** 見積金額区分 */
    private int ntp060NanKinMitumoriKbn__ = Ntp060Biz.PRICE_MORE;
    /** 受注金額区分 */
    private int ntp060NanKinJutyuKbn__ = Ntp060Biz.PRICE_MORE;
    /** 案件登録 開始年 */
    private String ntp060FrYear__ = null;
    /** 案件登録 開始月 */
    private String ntp060FrMonth__ = null;
    /** 案件登録 開始日 */
    private String ntp060FrDay__ = null;
    /** 案件登録 終了年 */
    private String ntp060ToYear__ = null;
    /** 案件登録 終了月 */
    private String ntp060ToMonth__ = null;
    /** 案件登録 終了日 */
    private String ntp060ToDay__ = null;
    /** 案件登録 開始年月日 */
    private String ntp060FrDate__ = null;
    /** 案件登録 終了年月日 */
    private String ntp060ToDate__ = null;
    /** カテゴリリスト一覧 */
    private List<LabelValueBean> ntp060CategoryList__ = null;
    /** カテゴリSID */
    private int ntp060CatSid__ = -1;
    /**
     * @return ntp060ReturnPage
     */
    public String getNtp060ReturnPage() {
        return ntp060ReturnPage__;
    }
    /**
     * @param ntp060ReturnPage 設定する ntp060ReturnPage
     */
    public void setNtp060ReturnPage(String ntp060ReturnPage) {
        ntp060ReturnPage__ = ntp060ReturnPage;
    }
    /**
     * @return ntp060InitFlg
     */
    public int getNtp060InitFlg() {
        return ntp060InitFlg__;
    }
    /**
     * @param ntp060InitFlg 設定する ntp060InitFlg
     */
    public void setNtp060InitFlg(int ntp060InitFlg) {
        ntp060InitFlg__ = ntp060InitFlg;
    }
    /**
     * @return ntp060AbaName
     */
    public String getNtp060AbaName() {
        return ntp060AbaName__;
    }
    /**
     * @param ntp060AbaName 設定する ntp060AbaName
     */
    public void setNtp060AbaName(String ntp060AbaName) {
        ntp060AbaName__ = ntp060AbaName;
    }
    /**
     * @return ntp060AcoCode
     */
    public String getNtp060AcoCode() {
        return ntp060AcoCode__;
    }
    /**
     * @param ntp060AcoCode 設定する ntp060AcoCode
     */
    public void setNtp060AcoCode(String ntp060AcoCode) {
        ntp060AcoCode__ = ntp060AcoCode;
    }
    /**
     * @return ntp060AcoName
     */
    public String getNtp060AcoName() {
        return ntp060AcoName__;
    }
    /**
     * @param ntp060AcoName 設定する ntp060AcoName
     */
    public void setNtp060AcoName(String ntp060AcoName) {
        ntp060AcoName__ = ntp060AcoName;
    }
    /**
     * @return ntp060AcoNameKana
     */
    public String getNtp060AcoNameKana() {
        return ntp060AcoNameKana__;
    }
    /**
     * @param ntp060AcoNameKana 設定する ntp060AcoNameKana
     */
    public void setNtp060AcoNameKana(String ntp060AcoNameKana) {
        ntp060AcoNameKana__ = ntp060AcoNameKana;
    }
    /**
     * @return ntp060ContactList
     */
    public List<LabelValueBean> getNtp060ContactList() {
        return ntp060ContactList__;
    }
    /**
     * @param ntp060ContactList 設定する ntp060ContactList
     */
    public void setNtp060ContactList(List<LabelValueBean> ntp060ContactList) {
        ntp060ContactList__ = ntp060ContactList;
    }
    /**
     * @return ntp060DayList
     */
    public List<LabelValueBean> getNtp060DayList() {
        return ntp060DayList__;
    }
    /**
     * @param ntp060DayList 設定する ntp060DayList
     */
    public void setNtp060DayList(List<LabelValueBean> ntp060DayList) {
        ntp060DayList__ = ntp060DayList;
    }
    /**
     * @return ntp060FrDay
     */
    public String getNtp060FrDay() {
        return ntp060FrDay__;
    }
    /**
     * @param ntp060FrDay 設定する ntp060FrDay
     */
    public void setNtp060FrDay(String ntp060FrDay) {
        ntp060FrDay__ = ntp060FrDay;
    }
    /**
     * @return ntp060FrMonth
     */
    public String getNtp060FrMonth() {
        return ntp060FrMonth__;
    }
    /**
     * @param ntp060FrMonth 設定する ntp060FrMonth
     */
    public void setNtp060FrMonth(String ntp060FrMonth) {
        ntp060FrMonth__ = ntp060FrMonth;
    }
    /**
     * @return ntp060FrYear
     */
    public String getNtp060FrYear() {
        return ntp060FrYear__;
    }
    /**
     * @param ntp060FrYear 設定する ntp060FrYear
     */
    public void setNtp060FrYear(String ntp060FrYear) {
        ntp060FrYear__ = ntp060FrYear;
    }
    /**
     * @return ntp060GyomuList
     */
    public List<LabelValueBean> getNtp060GyomuList() {
        return ntp060GyomuList__;
    }
    /**
     * @param ntp060GyomuList 設定する ntp060GyomuList
     */
    public void setNtp060GyomuList(List<LabelValueBean> ntp060GyomuList) {
        ntp060GyomuList__ = ntp060GyomuList;
    }
    /**
     * @return ntp060Mikomi
     */
    public String[] getNtp060Mikomi() {
        return ntp060Mikomi__;
    }
    /**
     * @param ntp060Mikomi 設定する ntp060Mikomi
     */
    public void setNtp060Mikomi(String[] ntp060Mikomi) {
        ntp060Mikomi__ = ntp060Mikomi;
    }
    /**
     * @return ntp060Syodan
     */
    public String[] getNtp060Syodan() {
        return ntp060Syodan__;
    }
    /**
     * @param ntp060Syodan 設定する ntp060Syodan
     */
    public void setNtp060Syodan(String[] ntp060Syodan) {
        ntp060Syodan__ = ntp060Syodan;
    }
    /**
     * @return ntp060MonthList
     */
    public List<LabelValueBean> getNtp060MonthList() {
        return ntp060MonthList__;
    }
    /**
     * @param ntp060MonthList 設定する ntp060MonthList
     */
    public void setNtp060MonthList(List<LabelValueBean> ntp060MonthList) {
        ntp060MonthList__ = ntp060MonthList;
    }
    /**
     * @return ntp060NanCode
     */
    public String getNtp060NanCode() {
        return ntp060NanCode__;
    }
    /**
     * @param ntp060NanCode 設定する ntp060NanCode
     */
    public void setNtp060NanCode(String ntp060NanCode) {
        ntp060NanCode__ = ntp060NanCode;
    }
    /**
     * @return ntp060NanKinJutyu
     */
    public String getNtp060NanKinJutyu() {
        return ntp060NanKinJutyu__;
    }
    /**
     * @param ntp060NanKinJutyu 設定する ntp060NanKinJutyu
     */
    public void setNtp060NanKinJutyu(String ntp060NanKinJutyu) {
        ntp060NanKinJutyu__ = ntp060NanKinJutyu;
    }
    /**
     * @return ntp060NanKinJutyuKbn
     */
    public int getNtp060NanKinJutyuKbn() {
        return ntp060NanKinJutyuKbn__;
    }
    /**
     * @param ntp060NanKinJutyuKbn 設定する ntp060NanKinJutyuKbn
     */
    public void setNtp060NanKinJutyuKbn(int ntp060NanKinJutyuKbn) {
        ntp060NanKinJutyuKbn__ = ntp060NanKinJutyuKbn;
    }
    /**
     * @return ntp060NanKinMitumori
     */
    public String getNtp060NanKinMitumori() {
        return ntp060NanKinMitumori__;
    }
    /**
     * @param ntp060NanKinMitumori 設定する ntp060NanKinMitumori
     */
    public void setNtp060NanKinMitumori(String ntp060NanKinMitumori) {
        ntp060NanKinMitumori__ = ntp060NanKinMitumori;
    }
    /**
     * @return ntp060NanKinMitumoriKbn
     */
    public int getNtp060NanKinMitumoriKbn() {
        return ntp060NanKinMitumoriKbn__;
    }
    /**
     * @param ntp060NanKinMitumoriKbn 設定する ntp060NanKinMitumoriKbn
     */
    public void setNtp060NanKinMitumoriKbn(int ntp060NanKinMitumoriKbn) {
        ntp060NanKinMitumoriKbn__ = ntp060NanKinMitumoriKbn;
    }
    /**
     * @return ntp060NanName
     */
    public String getNtp060NanName() {
        return ntp060NanName__;
    }
    /**
     * @param ntp060NanName 設定する ntp060NanName
     */
    public void setNtp060NanName(String ntp060NanName) {
        ntp060NanName__ = ntp060NanName;
    }
    /**
     * @return ntp060NcnSid
     */
    public int getNtp060NcnSid() {
        return ntp060NcnSid__;
    }
    /**
     * @param ntp060NcnSid 設定する ntp060NcnSid
     */
    public void setNtp060NcnSid(int ntp060NcnSid) {
        ntp060NcnSid__ = ntp060NcnSid;
    }
    /**
     * @return ntp060NgpSid
     */
    public int getNtp060NgpSid() {
        return ntp060NgpSid__;
    }
    /**
     * @param ntp060NgpSid 設定する ntp060NgpSid
     */
    public void setNtp060NgpSid(int ntp060NgpSid) {
        ntp060NgpSid__ = ntp060NgpSid;
    }
    /**
     * @return ntp060NgySid
     */
    public int getNtp060NgySid() {
        return ntp060NgySid__;
    }
    /**
     * @param ntp060NgySid 設定する ntp060NgySid
     */
    public void setNtp060NgySid(int ntp060NgySid) {
        ntp060NgySid__ = ntp060NgySid;
    }
    /**
     * @return ntp060NanSid
     */
    public int getNtp060NanSid() {
        return ntp060NanSid__;
    }
    /**
     * @param ntp060NanSid 設定する ntp060NanSid
     */
    public void setNtp060NanSid(int ntp060NanSid) {
        ntp060NanSid__ = ntp060NanSid;
    }
    /**
     * @return ntp060OrderKey1
     */
    public int getNtp060OrderKey1() {
        return ntp060OrderKey1__;
    }
    /**
     * @param ntp060OrderKey1 設定する ntp060OrderKey1
     */
    public void setNtp060OrderKey1(int ntp060OrderKey1) {
        ntp060OrderKey1__ = ntp060OrderKey1;
    }
    /**
     * @return ntp060OrderKey2
     */
    public int getNtp060OrderKey2() {
        return ntp060OrderKey2__;
    }
    /**
     * @param ntp060OrderKey2 設定する ntp060OrderKey2
     */
    public void setNtp060OrderKey2(int ntp060OrderKey2) {
        ntp060OrderKey2__ = ntp060OrderKey2;
    }
    /**
     * @return ntp060Page
     */
    public int getNtp060Page() {
        return ntp060Page__;
    }
    /**
     * @param ntp060Page 設定する ntp060Page
     */
    public void setNtp060Page(int ntp060Page) {
        ntp060Page__ = ntp060Page;
    }
    /**
     * @return ntp060PageBottom
     */
    public int getNtp060PageBottom() {
        return ntp060PageBottom__;
    }
    /**
     * @param ntp060PageBottom 設定する ntp060PageBottom
     */
    public void setNtp060PageBottom(int ntp060PageBottom) {
        ntp060PageBottom__ = ntp060PageBottom;
    }
    /**
     * @return ntp060PageCmbList
     */
    public List<LabelValueBean> getNtp060PageCmbList() {
        return ntp060PageCmbList__;
    }
    /**
     * @param ntp060PageCmbList 設定する ntp060PageCmbList
     */
    public void setNtp060PageCmbList(List<LabelValueBean> ntp060PageCmbList) {
        ntp060PageCmbList__ = ntp060PageCmbList;
    }
    /**
     * @return ntp060PageTop
     */
    public int getNtp060PageTop() {
        return ntp060PageTop__;
    }
    /**
     * @param ntp060PageTop 設定する ntp060PageTop
     */
    public void setNtp060PageTop(int ntp060PageTop) {
        ntp060PageTop__ = ntp060PageTop;
    }
    /**
     * @return ntp060ProcessList
     */
    public List<LabelValueBean> getNtp060ProcessList() {
        return ntp060ProcessList__;
    }
    /**
     * @param ntp060ProcessList 設定する ntp060ProcessList
     */
    public void setNtp060ProcessList(List<LabelValueBean> ntp060ProcessList) {
        ntp060ProcessList__ = ntp060ProcessList;
    }
    /**
     * @return ntp060ProcMode
     */
    public String getNtp060ProcMode() {
        return ntp060ProcMode__;
    }
    /**
     * @param ntp060ProcMode 設定する ntp060ProcMode
     */
    public void setNtp060ProcMode(String ntp060ProcMode) {
        ntp060ProcMode__ = ntp060ProcMode;
    }
//    /**
//     * @return ntp060SearchFlg
//     */
//    public int getNtp060SearchFlg() {
//        return ntp060SearchFlg__;
//    }
//    /**
//     * @param ntp060SearchFlg 設定する ntp060SearchFlg
//     */
//    public void setNtp060SearchFlg(int ntp060SearchFlg) {
//        ntp060SearchFlg__ = ntp060SearchFlg;
//    }
    /**
     * @return ntp060ShohinName
     */
    public String getNtp060ShohinName() {
        return ntp060ShohinName__;
    }
    /**
     * @param ntp060ShohinName 設定する ntp060ShohinName
     */
    public void setNtp060ShohinName(String ntp060ShohinName) {
        ntp060ShohinName__ = ntp060ShohinName;
    }
    /**
     * @return ntp060Sort1List
     */
    public List<LabelValueBean> getNtp060SortList() {
        return ntp060SortList__;
    }
    /**
     * @param ntp060SortList 設定する ntp060SortList
     */
    public void setNtp060SortList(List<LabelValueBean> ntp060SortList) {
        ntp060SortList__ = ntp060SortList;
    }

    /**
     * @return ntp060SortKey1
     */
    public int getNtp060SortKey1() {
        return ntp060SortKey1__;
    }
    /**
     * @param ntp060SortKey1 設定する ntp060SortKey1
     */
    public void setNtp060SortKey1(int ntp060SortKey1) {
        ntp060SortKey1__ = ntp060SortKey1;
    }
    /**
     * @return ntp060SortKey2
     */
    public int getNtp060SortKey2() {
        return ntp060SortKey2__;
    }
    /**
     * @param ntp060SortKey2 設定する ntp060SortKey2
     */
    public void setNtp060SortKey2(int ntp060SortKey2) {
        ntp060SortKey2__ = ntp060SortKey2;
    }

    /**
     * @return ntp060ToDay
     */
    public String getNtp060ToDay() {
        return ntp060ToDay__;
    }
    /**
     * @param ntp060ToDay 設定する ntp060ToDay
     */
    public void setNtp060ToDay(String ntp060ToDay) {
        ntp060ToDay__ = ntp060ToDay;
    }
    /**
     * @return ntp060ToMonth
     */
    public String getNtp060ToMonth() {
        return ntp060ToMonth__;
    }
    /**
     * @param ntp060ToMonth 設定する ntp060ToMonth
     */
    public void setNtp060ToMonth(String ntp060ToMonth) {
        ntp060ToMonth__ = ntp060ToMonth;
    }
    /**
     * @return ntp060ToYear
     */
    public String getNtp060ToYear() {
        return ntp060ToYear__;
    }
    /**
     * @param ntp060ToYear 設定する ntp060ToYear
     */
    public void setNtp060ToYear(String ntp060ToYear) {
        ntp060ToYear__ = ntp060ToYear;
    }
    /**
     * @return ntp060YearList
     */
    public List<LabelValueBean> getNtp060YearList() {
        return ntp060YearList__;
    }
    /**
     * @param ntp060YearList 設定する ntp060YearList
     */
    public void setNtp060YearList(List<LabelValueBean> ntp060YearList) {
        ntp060YearList__ = ntp060YearList;
    }
    /**
     * <p>ntp060FrDate を取得します。
     * @return ntp060FrDate
     * @see jp.groupsession.v2.ntp.ntp060.Ntp060Form#ntp060FrDate__
     */
    public String getNtp060FrDate() {
        return ntp060FrDate__;
    }
    /**
     * <p>ntp060FrDate をセットします。
     * @param ntp060FrDate ntp060FrDate
     * @see jp.groupsession.v2.ntp.ntp060.Ntp060Form#ntp060FrDate__
     */
    public void setNtp060FrDate(String ntp060FrDate) {
        ntp060FrDate__ = ntp060FrDate;
    }
    /**
     * <p>ntp060ToDate を取得します。
     * @return ntp060ToDate
     * @see jp.groupsession.v2.ntp.ntp060.Ntp060Form#ntp060ToDate__
     */
    public String getNtp060ToDate() {
        return ntp060ToDate__;
    }
    /**
     * <p>ntp060ToDate をセットします。
     * @param ntp060ToDate ntp060ToDate
     * @see jp.groupsession.v2.ntp.ntp060.Ntp060Form#ntp060ToDate__
     */
    public void setNtp060ToDate(String ntp060ToDate) {
        ntp060ToDate__ = ntp060ToDate;
    }
    /**
     * @return ntp060AnkenList
     */
    public ArrayList<Ntp060AnkenModel> getNtp060AnkenList() {
        return ntp060AnkenList__;
    }
    /**
     * @param ntp060AnkenList 設定する ntp060AnkenList
     */
    public void setNtp060AnkenList(ArrayList<Ntp060AnkenModel> ntp060AnkenList) {
        ntp060AnkenList__ = ntp060AnkenList;
    }
    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     * @param form 案件検索フォーム
     */
    public void setNtp060HiddenParam(Cmn999Form msgForm, Ntp060Form form) {
        /** 遷移元 */
        msgForm.addHiddenParam("ntp060ReturnPage", ntp060ReturnPage__);
        /** 初期化フラグ */
        msgForm.addHiddenParam("ntp060InitFlg", ntp060InitFlg__);
        /** ページ */
        msgForm.addHiddenParam("ntp060Page", ntp060Page__);
        /** ページ上段 */
        msgForm.addHiddenParam("ntp060PageTop", ntp060PageTop__);
        /** ページ下段 */
        msgForm.addHiddenParam("ntp060PageBottom", ntp060PageBottom__);
        /** ソートキー１ */
        msgForm.addHiddenParam("ntp060SortKey1", ntp060SortKey1__);
        /** オーダキー１ */
        msgForm.addHiddenParam("ntp060OrderKey1", ntp060OrderKey1__);
        /** ソートキー２ */
        msgForm.addHiddenParam("ntp060SortKey2", ntp060SortKey2__);
        /** オーダキー２ */
        msgForm.addHiddenParam("ntp060OrderKey2", ntp060OrderKey2__);
        /** 企業コード */
        msgForm.addHiddenParam("ntp060AcoCode", ntp060AcoCode__);
        /** 企業名 */
        msgForm.addHiddenParam("ntp060AcoName", ntp060AcoName__);
        /** 企業名カナ */
        msgForm.addHiddenParam("ntp060AcoNameKana", ntp060AcoNameKana__);
        /** 拠点名 */
        msgForm.addHiddenParam("ntp060AbaName", ntp060AbaName__);
        /** 商品名 */
        msgForm.addHiddenParam("ntp060ShohinName", ntp060ShohinName__);
        /** 業務内容SID */
        msgForm.addHiddenParam("ntp060NgySid", ntp060NgySid__);
        /** プロセスSID */
        msgForm.addHiddenParam("ntp060NgpSid", ntp060NgpSid__);
        /** コンタクトSID */
        msgForm.addHiddenParam("ntp060NcnSid", ntp060NcnSid__);
        /** 見込度 */
        msgForm.addHiddenParam("ntp060Mikomi", ntp060Mikomi__);
        /** 商談結果 */
        msgForm.addHiddenParam("ntp060Syodan", ntp060Syodan__);
        /** 案件コード */
        msgForm.addHiddenParam("ntp060NanCode", ntp060NanCode__);
        /** 案件名 */
        msgForm.addHiddenParam("ntp060NanName", ntp060NanName__);
        /** 状態 */
        msgForm.addHiddenParam("ntp060State", ntp060State__);
        /** 見積金額 */
        msgForm.addHiddenParam("ntp060NanKinMitumori", ntp060NanKinMitumori__);
        /** 受注金額 */
        msgForm.addHiddenParam("ntp060NanKinJutyu", ntp060NanKinJutyu__);
        /** 見積金額区分 */
        msgForm.addHiddenParam("ntp060NanKinMitumoriKbn", ntp060NanKinMitumoriKbn__);
        /** 受注金額区分 */
        msgForm.addHiddenParam("ntp060NanKinJutyuKbn", ntp060NanKinJutyuKbn__);
        /** 案件登録 開始年 */
        msgForm.addHiddenParam("ntp060FrYear", ntp060FrYear__);
        /** 案件登録 開始月 */
        msgForm.addHiddenParam("ntp060FrMonth", ntp060FrMonth__);
        /** 案件登録 開始日 */
        msgForm.addHiddenParam("ntp060FrDay", ntp060FrDay__);
        /** 案件登録 終了年 */
        msgForm.addHiddenParam("ntp060ToYear", ntp060ToYear__);
        /** 案件登録 終了月 */
        msgForm.addHiddenParam("ntp060ToMonth", ntp060ToMonth__);
        /** 案件登録 終了日 */
        msgForm.addHiddenParam("ntp060ToDay", ntp060ToDay__);
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionErrors validateCheck(HttpServletRequest req)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //案件コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_ANKEN_CODE,
                ntp060NanCode__,
               "ntp060NanCode",
                GSConstNippou.MAX_LENGTH_ANKEN_CODE,
                false);

        //案件名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_ANKEN_NAME,
                ntp060NanName__,
               "ntp060NanName",
                GSConstNippou.MAX_LENGTH_ANKEN_NAME,
                false);

        //見積金額入力チェック
        if (!StringUtil.isNullZeroStringSpace(ntp060NanKinMitumori__)) {
            GSValidateNippou.validateCmnFieldTextNumber(errors,
                    GSConstNippou.TEXT_ANKEN_MITUMORI,
                    ntp060NanKinMitumori__.replaceAll(",", ""),
                   "ntp060NanKinMitumori",
                    GSConstNippou.MAX_LENGTH_ANKEN_MITUMORI,
                    false);
        }


        //受注金額入力チェック
        if (!StringUtil.isNullZeroStringSpace(ntp060NanKinJutyu__)) {
            GSValidateNippou.validateCmnFieldTextNumber(errors,
                    GSConstNippou.TEXT_ANKEN_JUCYU,
                    ntp060NanKinJutyu__.replaceAll(",", ""),
                   "ntp060NanKinJutyu",
                    GSConstNippou.MAX_LENGTH_ANKEN_JUCYU,
                    false);
        }

        //企業コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_CODE,
                ntp060AcoCode__,
               "ntp060AcoCode",
                GSConstNippou.MAX_LENGTH_COMPANY_CODE,
                false);

        //会社名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_NAME,
                ntp060AcoName__,
               "ntp060AcoName",
                GSConstNippou.MAX_LENGTH_COMPANY_NAME,
                false);

        //会社名カナ入力チェック
        GSValidateNippou.validateCmnFieldTextKana(errors,
                GSConstNippou.TEXT_COMPANY_NAME_KN,
                ntp060AcoNameKana__,
               "会社名カナ",
                GSConstNippou.MAX_LENGTH_COMPANY_NAME_KN,
                false);

        //拠点名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_BASE_NAME,
                ntp060AbaName__,
               "ntp060AbaName",
                GSConstNippou.MAX_LENGTH_BASE_NAME,
                false);

        //商品名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_NAME,
                ntp060ShohinName__,
               "ntp060ShohinName",
                GSConstNippou.MAX_LENGTH_SHOHIN_NAME,
                false);







        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        //案件登録日付 From
        String textTourokuFr = GSConstNippou.TEXT_ANKEN_DATE_FROM;
        //案件登録日付 To
        String textTourokuTo = GSConstNippou.TEXT_ANKEN_DATE_TO;
        
        if (!StringUtil.isNullZeroString(ntp060FrDate__)) {
            errors.add(dateBiz.setYmdParam(this, "ntp060FrDate",
                    "ntp060FrYear", "ntp060FrMonth", "ntp060FrDay", textTourokuFr));
        }
        
        if (!StringUtil.isNullZeroString(ntp060ToDate__)) {
            errors.add(dateBiz.setYmdParam(this, "ntp060ToDate",
                    "ntp060ToYear", "ntp060ToMonth", "ntp060ToDay", textTourokuTo));
        }
        
        if (errors.size() <= 0
                && !NullDefault.getString(ntp060FrYear__, "").equals("")
                && !NullDefault.getString(ntp060FrMonth__, "").equals("")
                && !NullDefault.getString(ntp060FrDay__, "").equals("")
                && !NullDefault.getString(ntp060ToYear__, "").equals("")
                && !NullDefault.getString(ntp060ToMonth__, "").equals("")
                && !NullDefault.getString(ntp060ToDay__, "").equals("")) {
                    //大小チェック
                    UDate dateStart = new UDate();
                    dateStart.setDate(NullDefault.getInt(ntp060FrYear__, -1),
                                      NullDefault.getInt(ntp060FrMonth__, -1),
                                      NullDefault.getInt(ntp060FrDay__, -1));
                    UDate dateEnd = new UDate();
                    dateEnd.setDate(NullDefault.getInt(ntp060ToYear__, -1),
                                    NullDefault.getInt(ntp060ToMonth__, -1),
                                    NullDefault.getInt(ntp060ToDay__, -1));
                    GSValidateNippou.validateDataRange(
                            errors,
                            textTourokuFr,
                            textTourokuTo,
                            dateStart,
                            dateEnd);
                }


//        //日付論理チェック
//        UDate fromDate = new UDate();
//        fromDate.setTime(0);
//        fromDate.setDate(Integer.parseInt(ntp060FrYear__),
//            Integer.parseInt(ntp060FrMonth__), Integer.parseInt(ntp060FrDay__));
//
//        boolean fromDateOk = true;
//        if (fromDate.getYear() != Integer.parseInt(ntp060FrYear__)
//        || fromDate.getMonth() != Integer.parseInt(ntp060FrMonth__)
//        || fromDate.getIntDay() != Integer.parseInt(ntp060FrDay__)) {
//
//            msg = new ActionMessage("error.input.notfound.date",
//                GSConstNippou.TEXT_ANKEN_DATE_FROM);
//            StrutsUtil.addMessage(errors, msg, "ntp060FrYear__");
//            fromDateOk = false;
//        }
//
//        //日付論理チェック
//        UDate toDate = new UDate();
//        toDate.setTime(0);
//        toDate.setDate(Integer.parseInt(ntp060ToYear__),
//            Integer.parseInt(ntp060ToMonth__), Integer.parseInt(ntp060ToDay__));
//
//        if (toDate.getYear() != Integer.parseInt(ntp060ToYear__)
//        || toDate.getMonth() != Integer.parseInt(ntp060ToMonth__)
//        || toDate.getIntDay() != Integer.parseInt(ntp060ToDay__)) {
//
//            msg = new ActionMessage("error.input.notfound.date",
//                GSConstNippou.TEXT_ANKEN_DATE_TO);
//            StrutsUtil.addMessage(errors, msg, "ntp060ToYear__");
//        } else if (fromDateOk && fromDate.compareDateYMD(toDate) == UDate.SMALL) {
//            msg = new ActionMessage("error.input.comp.text",
//                    GSConstNippou.TEXT_ANKEN_DATE_FROM_TO,
//                    GSConstNippou.TEXT_ANKEN_DATE_MIN);
//            StrutsUtil.addMessage(errors, msg, "ntp060ToYear__");
//        }


        //プロセス入力チェック
        if (ntp060NgySid__ != -1 && ntp060NgpSid__ == -1) {
            msg = new ActionMessage("error.select.required.text", GSConstNippou.TEXT_PROCESS_NAME);
            StrutsUtil.addMessage(
                      errors, msg, "ntp060NgpSid__");
        }
        return errors;
    }
    /**
     * <p>ntp060State を取得します。
     * @return ntp060State
     */
    public int getNtp060State() {
        return ntp060State__;
    }
    /**
     * <p>ntp060State をセットします。
     * @param ntp060State ntp060State
     */
    public void setNtp060State(int ntp060State) {
        ntp060State__ = ntp060State;
    }
    /**
     * <p>ntp060StateList を取得します。
     * @return ntp060StateList
     */
    public List<LabelValueBean> getNtp060StateList() {
        return ntp060StateList__;
    }
    /**
     * <p>ntp060StateList をセットします。
     * @param ntp060StateList ntp060StateList
     */
    public void setNtp060StateList(List<LabelValueBean> ntp060StateList) {
        ntp060StateList__ = ntp060StateList;
    }
    /**
     * <p>ntp060CategoryList を取得します。
     * @return ntp060CategoryList
     */
    public List<LabelValueBean> getNtp060CategoryList() {
        return ntp060CategoryList__;
    }
    /**
     * <p>ntp060CategoryList をセットします。
     * @param ntp060CategoryList ntp060CategoryList
     */
    public void setNtp060CategoryList(List<LabelValueBean> ntp060CategoryList) {
        ntp060CategoryList__ = ntp060CategoryList;
    }
    /**
     * <p>ntp060CatSid を取得します。
     * @return ntp060CatSid
     */
    public int getNtp060CatSid() {
        return ntp060CatSid__;
    }
    /**
     * <p>ntp060CatSid をセットします。
     * @param ntp060CatSid ntp060CatSid
     */
    public void setNtp060CatSid(int ntp060CatSid) {
        ntp060CatSid__ = ntp060CatSid;
    }
}
