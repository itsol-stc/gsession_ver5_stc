package jp.groupsession.v2.ntp.ntp100;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ParameterUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010UsrModel;
import jp.groupsession.v2.ntp.ntp010.SimpleNippouModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060Form;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 日報 日報一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp100Form extends Ntp060Form {


    //表示内容
    /** ヘッダー表示用年月 */
    private String ntp100StrDspDate__ = null;
    /** ヘッダーユーザ名称 */
    private String ntp100StrUserName__ = null;
    /** ユーザモデル */
    Ntp010UsrModel ntp100UsrMdl__ = null;
    /** スケジュールリスト */
    private ArrayList<SimpleNippouModel> ntp100NippouList__ = null;
    /** 表示ページ */
    private int ntp100PageNum__ = 1;
    /** ページ1 */
    private int ntp100Slt_page1__;
    /** ページ2 */
    private int ntp100Slt_page2__;
    /** ページラベル */
    private ArrayList<LabelValueBean> ntp100PageLabel__;

    //検索条件
    /** グループ */
    private String ntp100SltGroup__ = null;
    /** ユーザ */
    private String ntp100SltUser__ = null;
    /** 日報日付from年 */
    private String ntp100SltYearFr__ = null;
    /** 日報日付from月 */
    private String ntp100SltMonthFr__ = null;
    /** 日報日付from日 */
    private String ntp100SltDayFr__ = null;
    /** 日報日付to年 */
    private String ntp100SltYearTo__ = null;
    /** 日報日付to月 */
    private String ntp100SltMonthTo__ = null;
    /** 日報日付to日 */
    private String ntp100SltDayTo__ = null;
    /** 日報日付from年月日 */
    private String ntp100SltDateFr__ = null;
    /** 日報日付to年月日 */
    private String ntp100SltDateTo__ = null;

    /** 案件SID */
    private String ntp100AnkenSid__ = null;
    /** 企業SID */
    private String ntp100CompanySid__ = null;
    /** 拠点SID */
    private String ntp100CompanyBaseSid__ = null;

    /** 検索時キーワード */
    private String ntp100KeyValue__ = null;
    /** キーワード検索区分 */
    private String ntp100KeyWordkbn__ = String.valueOf(GSConstNippou.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] ntp100SearchTarget__ = null;
    /** タイトルカラー */
    private String[] ntp100Bgcolor__ = null;
    /** 見込み度 */
    private String[] ntp100Mikomido__ = null;


    /** オーダーキー */
    private int ntp100OrderKey1__ = GSConstNippou.ORDER_KEY_DESC;
    /** ソートキー */
    private int ntp100SortKey1__ = GSConstNippou.SORT_KEY_FRDATE;
    /** オーダーキー2 */
    private int ntp100OrderKey2__ = GSConstNippou.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int ntp100SortKey2__ = GSConstNippou.SORT_KEY_NAME;

    /** 検索時グループ */
    private String ntp100SvSltGroup__ = null;
    /** 検索時ユーザ */
    private String ntp100SvSltUser__ = null;
    /** 検索時開始日from年 */
    private String ntp100SvSltYearFr__ = null;
    /** 検索時開始日from月 */
    private String ntp100SvSltMonthFr__ = null;
    /** 検索時開始日from日 */
    private String ntp100SvSltDayFr__ = null;
    /** 検索時開始日to年 */
    private String ntp100SvSltYearTo__ = null;
    /** 検索時開始日to月 */
    private String ntp100SvSltMonthTo__ = null;
    /** 検索時開始日to日 */
    private String ntp100SvSltDayTo__ = null;

    /** 検索時キーワード */
    private String ntp100SvKeyValue__ = null;
    /** キーワード検索区分 */
    private String ntp100SvKeyWordkbn__ = String.valueOf(GSConstNippou.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] ntp100SvSearchTarget__ = null;
    /** オーダーキー */
    private int ntp100SvOrderKey1__ = GSConstNippou.ORDER_KEY_ASC;
    /** ソートキー */
    private int ntp100SvSortKey1__ = GSConstNippou.SORT_KEY_FRDATE;
    /** オーダーキー2 */
    private int ntp100SvOrderKey2__ = GSConstNippou.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int ntp100SvSortKey2__ = GSConstNippou.SORT_KEY_NAME;
    /** タイトルカラー */
    private String[] ntp100SvBgcolor__ = null;
    /** 見込み度 */
    private String[] ntp100SvMikomido__ = null;

    /** 案件SID */
    private String ntp100SvAnkenSid__ = null;
    /** 企業SID */
    private String ntp100SvCompanySid__ = null;
    /** 拠点SID */
    private String ntp100SvCompanyBaseSid__ = null;

    /** 活動分類 */
    private String ntp100SvKtbunrui__ = null;
    /** 活動方法  */
    private String ntp100SvKthouhou__ = null;

    /** グループラベル */
    private List<NtpLabelValueModel> ntp100GroupLabel__;
    /** ユーザラベル */
    private List<UsrLabelValueBean> ntp100UserLabel__;
    /** 日報日付from年ラベル */
    private List<LabelValueBean> ntp100YearLabel__;
    /** 日報日付from月ラベル */
    private List<LabelValueBean> ntp100MonthLabel__;
    /** 日報日付from日ラベル */
    private List<LabelValueBean> ntp100DayLabel__;

    /** ソートキーリスト */
    private List<LabelValueBean> sortKeyList__ = null;
    /** カラーコメントリスト */
    private ArrayList < String > ntp100ColorMsgList__ = null;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int ntp100searchUse__ = GSConst.PLUGIN_USE;
    /** ユーザId */
    private String ntp100SelectUsrSid__ = null;
    /** ユーザId */
    private String ntp100SelectNtpAreaSid__ = null;
    /** WEB検索ワード */
    private String ntp100WebSearchWord__ = "";



    /** 活動分類 */
    private String ntp100Ktbunrui__ = null;
    /** 活動方法  */
    private String ntp100Kthouhou__ = null;
    /** 活動分類リスト */
    private List<LabelValueBean> ntp100KtbunruiLavel__ = null;
    /** 活動方法リスト */
    private List<LabelValueBean> ntp100KthouhouLavel__ = null;


    /** 案件情報 */
    private NtpAnkenModel ntp100NtpAnkenModel__ = null;
    /** 会社情報 */
    private AdrCompanyModel ntp100AdrCompanyMdl__ = null;
    /** 会社拠点情報  */
    private AdrCompanyBaseModel ntp100AdrCompanyBaseMdl__ = null;

    /** CSV出力項目 */
    private String[] ntp100CsvOutField__ = Ntp100Biz.getDefultCsvOut();

    /**
     * 検索条件パラメータをSAVEフィールドへ移行します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {
        ntp100SvSltGroup__ = ntp100SltGroup__;
        ntp100SvSltUser__ = ntp100SltUser__;
        ntp100SvSltYearFr__ = ntp100SltYearFr__;
        ntp100SvSltMonthFr__ = ntp100SltMonthFr__;
        ntp100SvSltDayFr__ = ntp100SltDayFr__;
        ntp100SvSltYearTo__ = ntp100SltYearTo__;
        ntp100SvSltMonthTo__ = ntp100SltMonthTo__;
        ntp100SvSltDayTo__ = ntp100SltDayTo__;
        ntp100SvKeyValue__ = getNtp010searchWord();
        ntp100SvKeyWordkbn__ = ntp100KeyWordkbn__;
        ntp100SvSearchTarget__ = ntp100SearchTarget__;
        ntp100SvOrderKey1__ = ntp100OrderKey1__;
        ntp100SvSortKey1__ = ntp100SortKey1__;
        ntp100SvOrderKey2__ = ntp100OrderKey2__;
        ntp100SvSortKey2__ = ntp100SortKey2__;
        ntp100SvBgcolor__ = ntp100Bgcolor__;
        ntp100WebSearchWord__ = StringUtil.toSingleCortationEscape(ntp100SvKeyValue__);
        ntp100SvKtbunrui__ = ntp100Ktbunrui__;
        ntp100SvKthouhou__ = ntp100Kthouhou__;
        ntp100SvAnkenSid__ = ntp100AnkenSid__;
        ntp100SvCompanySid__ = ntp100CompanySid__;
        ntp100SvCompanyBaseSid__ = ntp100CompanyBaseSid__;
        ntp100SvMikomido__ = ntp100Mikomido__;
    }


    /**
     * <p>ntp100KeyWordkbn を取得します。
     * @return ntp100KeyWordkbn
     */
    public String getNtp100KeyWordkbn() {
        return ntp100KeyWordkbn__;
    }

    /**
     * <p>ntp100KeyWordkbn をセットします。
     * @param ntp100KeyWordkbn ntp100KeyWordkbn
     */
    public void setNtp100KeyWordkbn(String ntp100KeyWordkbn) {
        ntp100KeyWordkbn__ = ntp100KeyWordkbn;
    }

    /**
     * <p>ntp100SearchTarget を取得します。
     * @return ntp100SearchTarget
     */
    public String[] getNtp100SearchTarget() {
        return ntp100SearchTarget__;
    }

    /**
     * <p>ntp100SearchTarget をセットします。
     * @param ntp100SearchTarget ntp100SearchTarget
     */
    public void setNtp100SearchTarget(String[] ntp100SearchTarget) {
        ntp100SearchTarget__ = ntp100SearchTarget;
    }

    /**
     * <p>ntp100OrderKey2 を取得します。
     * @return ntp100OrderKey2
     */
    public int getNtp100OrderKey2() {
        return ntp100OrderKey2__;
    }

    /**
     * <p>ntp100OrderKey2 をセットします。
     * @param ntp100OrderKey2 ntp100OrderKey2
     */
    public void setNtp100OrderKey2(int ntp100OrderKey2) {
        ntp100OrderKey2__ = ntp100OrderKey2;
    }

    /**
     * <p>ntp100SortKey2 を取得します。
     * @return ntp100SortKey2
     */
    public int getNtp100SortKey2() {
        return ntp100SortKey2__;
    }

    /**
     * <p>ntp100SortKey2 をセットします。
     * @param ntp100SortKey2 ntp100SortKey2
     */
    public void setNtp100SortKey2(int ntp100SortKey2) {
        ntp100SortKey2__ = ntp100SortKey2;
    }

    /**
     * <p>ntp100SvOrderKey1 を取得します。
     * @return ntp100SvOrderKey1
     */
    public int getNtp100SvOrderKey1() {
        return ntp100SvOrderKey1__;
    }

    /**
     * <p>ntp100SvOrderKey1 をセットします。
     * @param ntp100SvOrderKey1 ntp100SvOrderKey1
     */
    public void setNtp100SvOrderKey1(int ntp100SvOrderKey1) {
        ntp100SvOrderKey1__ = ntp100SvOrderKey1;
    }

    /**
     * <p>ntp100SvOrderKey2 を取得します。
     * @return ntp100SvOrderKey2
     */
    public int getNtp100SvOrderKey2() {
        return ntp100SvOrderKey2__;
    }

    /**
     * <p>ntp100SvOrderKey2 をセットします。
     * @param ntp100SvOrderKey2 ntp100SvOrderKey2
     */
    public void setNtp100SvOrderKey2(int ntp100SvOrderKey2) {
        ntp100SvOrderKey2__ = ntp100SvOrderKey2;
    }

    /**
     * <p>ntp100SvSortKey1 を取得します。
     * @return ntp100SvSortKey1
     */
    public int getNtp100SvSortKey1() {
        return ntp100SvSortKey1__;
    }

    /**
     * <p>ntp100SvSortKey1 をセットします。
     * @param ntp100SvSortKey1 ntp100SvSortKey1
     */
    public void setNtp100SvSortKey1(int ntp100SvSortKey1) {
        ntp100SvSortKey1__ = ntp100SvSortKey1;
    }

    /**
     * <p>ntp100SvSortKey2 を取得します。
     * @return ntp100SvSortKey2
     */
    public int getNtp100SvSortKey2() {
        return ntp100SvSortKey2__;
    }

    /**
     * <p>ntp100SvSortKey2 をセットします。
     * @param ntp100SvSortKey2 ntp100SvSortKey2
     */
    public void setNtp100SvSortKey2(int ntp100SvSortKey2) {
        ntp100SvSortKey2__ = ntp100SvSortKey2;
    }

    /**
     * <p>sortKeyList を取得します。
     * @return sortKeyList
     */
    public List<LabelValueBean> getSortKeyList() {
        return sortKeyList__;
    }

    /**
     * <p>sortKeyList をセットします。
     * @param sortKeyList sortKeyList
     */
    public void setSortKeyList(List<LabelValueBean> sortKeyList) {
        sortKeyList__ = sortKeyList;
    }

    /**
     * <p>ntp100SvKeyWordkbn を取得します。
     * @return ntp100SvKeyWordkbn
     */
    public String getNtp100SvKeyWordkbn() {
        return ntp100SvKeyWordkbn__;
    }

    /**
     * <p>ntp100SvKeyWordkbn をセットします。
     * @param ntp100SvKeyWordkbn ntp100SvKeyWordkbn
     */
    public void setNtp100SvKeyWordkbn(String ntp100SvKeyWordkbn) {
        ntp100SvKeyWordkbn__ = ntp100SvKeyWordkbn;
    }

    /**
     * <p>ntp100SvSearchTarget を取得します。
     * @return ntp100SvSearchTarget
     */
    public String[] getNtp100SvSearchTarget() {
        return ntp100SvSearchTarget__;
    }

    /**
     * <p>ntp100SvSearchTarget をセットします。
     * @param ntp100SvSearchTarget ntp100SvSearchTarget
     */
    public void setNtp100SvSearchTarget(String[] ntp100SvSearchTarget) {
        ntp100SvSearchTarget__ = ntp100SvSearchTarget;
    }

    /**
     * <p>ntp100SvKeyValue を取得します。
     * @return ntp100SvKeyValue
     */
    public String getNtp100SvKeyValue() {
        return ntp100SvKeyValue__;
    }
    /**
     * <p>ntp100SvKeyValue をセットします。
     * @param ntp100SvKeyValue ntp100SvKeyValue
     */
    public void setNtp100SvKeyValue(String ntp100SvKeyValue) {
        ntp100SvKeyValue__ = ntp100SvKeyValue;
    }
    /**
     * <p>ntp100SvSltGroup を取得します。
     * @return ntp100SvSltGroup
     */
    public String getNtp100SvSltGroup() {
        return ntp100SvSltGroup__;
    }
    /**
     * <p>ntp100SvSltGroup をセットします。
     * @param ntp100SvSltGroup ntp100SvSltGroup
     */
    public void setNtp100SvSltGroup(String ntp100SvSltGroup) {
        ntp100SvSltGroup__ = ntp100SvSltGroup;
    }

    /**
     * <p>ntp100SvSltUser を取得します。
     * @return ntp100SvSltUser
     */
    public String getNtp100SvSltUser() {
        return ntp100SvSltUser__;
    }
    /**
     * <p>ntp100SvSltUser をセットします。
     * @param ntp100SvSltUser ntp100SvSltUser
     */
    public void setNtp100SvSltUser(String ntp100SvSltUser) {
        ntp100SvSltUser__ = ntp100SvSltUser;
    }
    /**
     * <p>ntp100GroupLabel を取得します。
     * @return ntp100GroupLabel
     */
    public List<NtpLabelValueModel> getNtp100GroupLabel() {
        return ntp100GroupLabel__;
    }
    /**
     * <p>ntp100GroupLabel をセットします。
     * @param ntp100GroupLabel ntp100GroupLabel
     */
    public void setNtp100GroupLabel(List<NtpLabelValueModel> ntp100GroupLabel) {
        ntp100GroupLabel__ = ntp100GroupLabel;
    }

    /**
     * <p>ntp100SltGroup を取得します。
     * @return ntp100SltGroup
     */
    public String getNtp100SltGroup() {
        return ntp100SltGroup__;
    }
    /**
     * <p>ntp100SltGroup をセットします。
     * @param ntp100SltGroup ntp100SltGroup
     */
    public void setNtp100SltGroup(String ntp100SltGroup) {
        ntp100SltGroup__ = ntp100SltGroup;
    }

    /**
     * <p>ntp100SltUser を取得します。
     * @return ntp100SltUser
     */
    public String getNtp100SltUser() {
        return ntp100SltUser__;
    }
    /**
     * <p>ntp100SltUser をセットします。
     * @param ntp100SltUser ntp100SltUser
     */
    public void setNtp100SltUser(String ntp100SltUser) {
        ntp100SltUser__ = ntp100SltUser;
    }
    /**
     * <p>ntp100UserLabel を取得します。
     * @return ntp100UserLabel
     */
    public List<UsrLabelValueBean> getNtp100UserLabel() {
        return ntp100UserLabel__;
    }
    /**
     * <p>ntp100UserLabel をセットします。
     * @param ntp100UserLabel ntp100UserLabel
     */
    public void setNtp100UserLabel(List<UsrLabelValueBean> ntp100UserLabel) {
        ntp100UserLabel__ = ntp100UserLabel;
    }
    /**
     * <p>ntp100PageLabel を取得します。
     * @return ntp100PageLabel
     */
    public ArrayList<LabelValueBean> getNtp100PageLabel() {
        return ntp100PageLabel__;
    }
    /**
     * <p>ntp100PageLabel をセットします。
     * @param ntp100PageLabel ntp100PageLabel
     */
    public void setNtp100PageLabel(ArrayList<LabelValueBean> ntp100PageLabel) {
        ntp100PageLabel__ = ntp100PageLabel;
    }
    /**
     * <p>ntp100Slt_page1 を取得します。
     * @return ntp100Slt_page1
     */
    public int getNtp100Slt_page1() {
        return ntp100Slt_page1__;
    }
    /**
     * <p>ntp100Slt_page1 をセットします。
     * @param ntp100SltPage1 ntp100Slt_page1
     */
    public void setNtp100Slt_page1(int ntp100SltPage1) {
        ntp100Slt_page1__ = ntp100SltPage1;
    }
    /**
     * <p>ntp100Slt_page2 を取得します。
     * @return ntp100Slt_page2
     */
    public int getNtp100Slt_page2() {
        return ntp100Slt_page2__;
    }
    /**
     * <p>ntp100Slt_page2 をセットします。
     * @param ntp100SltPage2 ntp100Slt_page2
     */
    public void setNtp100Slt_page2(int ntp100SltPage2) {
        ntp100Slt_page2__ = ntp100SltPage2;
    }
    /**
     * <p>ntp100PageNum を取得します。
     * @return ntp100PageNum
     */
    public int getNtp100PageNum() {
        return ntp100PageNum__;
    }
    /**
     * <p>ntp100PageNum をセットします。
     * @param ntp100PageNum ntp100PageNum
     */
    public void setNtp100PageNum(int ntp100PageNum) {
        ntp100PageNum__ = ntp100PageNum;
    }
    /**
     * <p>ntp100OrderKey を取得します。
     * @return ntp100OrderKey
     */
    public int getNtp100OrderKey1() {
        return ntp100OrderKey1__;
    }
    /**
     * <p>ntp100OrderKey をセットします。
     * @param ntp100OrderKey ntp100OrderKey
     */
    public void setNtp100OrderKey1(int ntp100OrderKey) {
        ntp100OrderKey1__ = ntp100OrderKey;
    }
    /**
     * <p>ntp100SortKey を取得します。
     * @return ntp100SortKey
     */
    public int getNtp100SortKey1() {
        return ntp100SortKey1__;
    }
    /**
     * <p>ntp100SortKey をセットします。
     * @param ntp100SortKey ntp100SortKey
     */
    public void setNtp100SortKey1(int ntp100SortKey) {
        ntp100SortKey1__ = ntp100SortKey;
    }
    /**
     * <p>ntp100UsrMdl を取得します。
     * @return ntp100UsrMdl
     */
    public Ntp010UsrModel getNtp100UsrMdl() {
        return ntp100UsrMdl__;
    }
    /**
     * <p>ntp100UsrMdl をセットします。
     * @param ntp100UsrMdl ntp100UsrMdl
     */
    public void setNtp100UsrMdl(Ntp010UsrModel ntp100UsrMdl) {
        ntp100UsrMdl__ = ntp100UsrMdl;
    }
    /**
     * <p>ntp100NippouList を取得します。
     * @return ntp100NippouList
     */
    public ArrayList<SimpleNippouModel> getNtp100NippouList() {
        return ntp100NippouList__;
    }
    /**
     * <p>ntp100NippouList をセットします。
     * @param ntp100NippouList ntp100NippouList
     */
    public void setNtp100NippouList(ArrayList<SimpleNippouModel> ntp100NippouList) {
        ntp100NippouList__ = ntp100NippouList;
    }
    /**
     * <p>ntp100StrDspDate を取得します。
     * @return ntp100StrDspDate
     */
    public String getNtp100StrDspDate() {
        return ntp100StrDspDate__;
    }
    /**
     * <p>ntp100StrDspDate をセットします。
     * @param ntp100StrDspDate ntp100StrDspDate
     */
    public void setNtp100StrDspDate(String ntp100StrDspDate) {
        ntp100StrDspDate__ = ntp100StrDspDate;
    }
    /**
     * <p>ntp100StrUserName を取得します。
     * @return ntp100StrUserName
     */
    public String getNtp100StrUserName() {
        return ntp100StrUserName__;
    }
    /**
     * <p>ntp100StrUserName をセットします。
     * @param ntp100StrUserName ntp100StrUserName
     */
    public void setNtp100StrUserName(String ntp100StrUserName) {
        ntp100StrUserName__ = ntp100StrUserName;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionErrors validateNtp100Check(ActionMapping map,
            RequestModel reqMdl,
            Connection con) throws SQLException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        Ntp100Biz nt100Biz = new Ntp100Biz(reqMdl);
        String kyouyuuhaniErrMsg = "cant.execute.out.crange";
        boolean gpOk = false;
        //グループSIDの共有範囲チェック
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        List<NtpLabelValueModel> gpList = nt100Biz.getGroupLabelList(con, sessionUsrSid);
        for (NtpLabelValueModel gpModel : gpList) {
            if (gpModel.getValue().equals(ntp100SltGroup__)) {
                gpOk = true;
                break;
            }
        }
        if (!gpOk) {
            msg = new ActionMessage(kyouyuuhaniErrMsg,
                    gsMsg.getMessage("cmn.group"),
                    gsMsg.getMessage("cmn.search"));
            errors.add("cant.execute.out.crange", msg);

        } else {
        //ユーザSIDの共有範囲チェック
            boolean myGroupFlg = false;
            int dspGpSid = 0;
            if (SchCommonBiz.isMyGroupSid(ntp100SltGroup__)) {
                myGroupFlg = true;
            }
            dspGpSid = SchCommonBiz.getDspGroupSid(ntp100SltGroup__);

            List<UsrLabelValueBean> userList = nt100Biz.getUserLabelList(con,
                    dspGpSid, sessionUsrSid, myGroupFlg);
            boolean usrOk = false;
            for (UsrLabelValueBean user : userList) {
                if (user.getValue().equals(ntp100SltUser__)) {
                    usrOk = true;
                    break;
                }
            }
            if (!usrOk) {
                msg = new ActionMessage(kyouyuuhaniErrMsg,
                        gsMsg.getMessage("cmn.user"),
                        gsMsg.getMessage("cmn.search"));
                errors.add("cant.execute.out.crange", msg);
            }
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        int errorCnt = errors.size();
        
        String textStartDateStart = gsMsg.getMessage("schedule.src.69"); 
        errors.add(dateBiz.setYmdParam(this, "ntp100SltDateFr",
                "ntp100SltYearFr", "ntp100SltMonthFr", "ntp100SltDayFr", textStartDateStart));
        
        String textStartDateEnd = gsMsg.getMessage("schedule.src.71"); 
        errors.add(dateBiz.setYmdParam(this, "ntp100SltDateTo",
                "ntp100SltYearTo", "ntp100SltMonthTo", "ntp100SltDayTo", textStartDateEnd));
        
        //個別チェックOKの場合
        if (errorCnt == errors.size()) {
            int sFYear = Integer.parseInt(ntp100SltYearFr__);
            int sFMonth = Integer.parseInt(ntp100SltMonthFr__);
            int sFDay = Integer.parseInt(ntp100SltDayFr__);
            UDate sFrDate = new UDate();
            sFrDate.setDate(sFYear, sFMonth, sFDay);
            sFrDate.setSecond(GSConstNippou.DAY_START_SECOND);
            sFrDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
            
            int sTYear = Integer.parseInt(ntp100SltYearTo__);
            int sTMonth = Integer.parseInt(ntp100SltMonthTo__);
            int sTDay = Integer.parseInt(ntp100SltDayTo__);
            UDate sToDate = new UDate();
            sToDate.setDate(sTYear, sTMonth, sTDay);
            sToDate.setSecond(GSConstNippou.DAY_START_SECOND);
            sToDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
            
            //from～to大小チェック
            if (sFrDate.compareDateYMD(sToDate) == UDate.SMALL) {
                //開始 <= 終了
                String textStartOrEnd = gsMsg.getMessage("cmn.start.or.end");
//              開始日付：開始・終了
                String textStartDateStartEnd = gsMsg.getMessage("schedule.src.70");
                msg = new ActionMessage("error.input.comp.text",
                        textStartDateStartEnd, textStartOrEnd);
                errors.add("" + "error.input.comp.text", msg);
            }
        }

        /** メッセージ キーワード **/
        String textKeyword = gsMsg.getMessage("cmn.keyword");

        if (!StringUtil.isNullZeroString(getNtp010searchWord())) {
            //内容のチェック
            if (__checkRange(
                    errors,
                    getNtp010searchWord(),
                    "ntp100KeyValue",
                    textKeyword,
                    GSConstNippou.MAX_LENGTH_TITLE)) {

                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(getNtp010searchWord())) {
                    msg = new ActionMessage("error.input.spase.start",
                                            textKeyword);
                    StrutsUtil.addMessage(errors, msg, "ntp100KeyValue__");
                } else {
                    __checkJisString(
                            errors,
                            getNtp010searchWord(),
                            "ntp100KeyValue__",
                            textKeyword);
                }
            }
        }
        //ＫＥＹワードチェック
        validateSearchTarget(errors, getNtp010searchWord(), ntp100SearchTarget__, reqMdl);

        //見込み度
        String textMikomido = GSConstNippou.TEXT_MIKOMI;
        if (ntp100Mikomido__ == null || ntp100Mikomido__.length < 1) {
            msg = new ActionMessage("error.select.required.text", textMikomido);
            errors.add("error.select.required.text", msg);
        }

        //タイトルカラー
        String textTitileColor = gsMsg.getMessage("schedule.128");
        if (ntp100Bgcolor__ == null || ntp100Bgcolor__.length < 1) {
            msg = new ActionMessage("error.select.required.text", textTitileColor);
            errors.add("error.select.required.text", msg);
        }

        //ソート順
        String textSort = gsMsg.getMessage("cmn.sort.order");
        if (ntp100SortKey1__ == ntp100SortKey2__) {
            msg = new ActionMessage("error.select.dup.list",
                    textSort);
            errors.add("error.select.dup.list", msg);

        }
        return errors;
    }
    /**
     *
     * <br>[機  能] 検索条件パラメータ（SV）チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return false : 不正なパラメータあり
     * @throws SQLException
     */
    public boolean validateNtp100Research(RequestModel reqMdl, Connection con) throws SQLException {
        // 検索時グループ
        if (StringUtil.isNullZeroString(ntp100SvSltGroup__)) {
            return false;
        }
        if (ntp100SvSltGroup__.startsWith(GSConstNippou.MY_GROUP_STRING)) {
            if (!ValidateUtil.isNumberHaifun(
                        ntp100SvSltGroup__.substring(GSConstNippou.MY_GROUP_STRING.length()))) {
                return false;
            }
        } else if (!ValidateUtil.isNumberHaifun(
                        ntp100SvSltGroup__)) {
            return false;
        }

        // 検索時ユーザ
        if (!ValidateUtil.isNumberHaifun(
                ntp100SvSltUser__)) {
            return false;
        }
        Ntp100Biz nt100Biz = new Ntp100Biz(reqMdl);

        //グループSIDの共有範囲チェック
        boolean gpOk = false;
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        List<NtpLabelValueModel> gpList = nt100Biz.getGroupLabelList(con, sessionUsrSid);
        for (NtpLabelValueModel gpModel : gpList) {
            if (gpModel.getValue().equals(ntp100SvSltGroup__)) {
                gpOk = true;
                break;
            }
        }
        if (!gpOk) {
            return false;

        } else {
        //ユーザSIDの共有範囲チェック
            boolean myGroupFlg = false;
            int dspGpSid = 0;
            if (SchCommonBiz.isMyGroupSid(ntp100SvSltGroup__)) {
                myGroupFlg = true;
            }
            dspGpSid = SchCommonBiz.getDspGroupSid(ntp100SvSltGroup__);

            List<UsrLabelValueBean> userList = nt100Biz.getUserLabelList(con,
                    dspGpSid, sessionUsrSid, myGroupFlg);
            boolean usrOk = false;
            for (UsrLabelValueBean user : userList) {
                if (user.getValue().equals(ntp100SvSltUser__)) {
                    usrOk = true;
                    break;
                }
            }
            if (!usrOk) {
                return false;
            }
        }

        //開始年月日チェックフラグ(true=入力OK、false=NG)
        if (!ValidateUtil.isNumber(
                ntp100SvSltYearFr__)) {
            return false;
        }
        if (!ValidateUtil.isNumber(
                ntp100SvSltMonthFr__)) {
            return false;
        }
        if (!ValidateUtil.isNumber(
                ntp100SvSltDayFr__)) {
            return false;
        }

        int sFYear = Integer.parseInt(ntp100SvSltYearFr__);
        int sFMonth = Integer.parseInt(ntp100SvSltMonthFr__);
        int sFDay = Integer.parseInt(ntp100SvSltDayFr__);

        UDate sFrDate = new UDate();
        sFrDate.setDate(sFYear, sFMonth, sFDay);
        sFrDate.setSecond(GSConstNippou.DAY_START_SECOND);
        sFrDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
        if (sFrDate.getYear() != sFYear
        || sFrDate.getMonth() != sFMonth
        || sFrDate.getIntDay() != sFDay) {
            return false;
        }


        //終了年月日チェックフラグ(true=入力OK、false=NG)
        if (!ValidateUtil.isNumber(
                ntp100SvSltYearTo__)) {
            return false;
        }
        if (!ValidateUtil.isNumber(
                ntp100SvSltMonthTo__)) {
            return false;
        }
        if (!ValidateUtil.isNumber(
                ntp100SvSltDayTo__)) {
            return false;
        }
        int sTYear = Integer.parseInt(ntp100SvSltYearTo__);
        int sTMonth = Integer.parseInt(ntp100SvSltMonthTo__);
        int sTDay = Integer.parseInt(ntp100SvSltDayTo__);

        UDate sToDate = new UDate();
        sToDate.setDate(sTYear, sTMonth, sTDay);
        sToDate.setSecond(GSConstNippou.DAY_START_SECOND);
        sToDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
        if (sToDate.getYear() != sTYear
        || sToDate.getMonth() != sTMonth
        || sToDate.getIntDay() != sTDay) {
            //開始日付：開始・終了
            return false;
        }

        //個別チェックOKの場合
        //from～to大小チェック
        if (sFrDate.compareDateYMD(sToDate) == UDate.SMALL) {
            //開始 <= 終了
            return false;
        }

        ActionErrors errors = new ActionErrors();
        // 検索時キーワード
        if (!StringUtil.isNullZeroString(ntp100SvKeyValue__)) {
            errors = GSValidateNippou.validateCmnFieldText(errors,
                    "ntp100SvKeyValue",
                    ntp100SvKeyValue__,
                    "ntp100SvKeyValue",
                    GSConstNippou.MAX_LENGTH_TITLE,
                    false);
            //内容のチェック
            if (errors.size() > 0) {
                return false;
            }
            // 検索対象
            if (ntp100SvSearchTarget__ == null
                    || ntp100SvSearchTarget__.length == 0) {
                return false;
            }
            for (String chkStr : ntp100SvSearchTarget__) {
                if (ArrayUtils.indexOf(
                        new int[] {
                                GSConstNippou.SEARCH_TARGET_TITLE,
                                GSConstNippou.SEARCH_TARGET_HONBUN
                        },
                        NullDefault.getInt(chkStr, -1)) < 0) {
                    return false;
                }

            }
        }

        // キーワード検索区分
        if (ArrayUtils.indexOf(new int[] {GSConstNippou.KEY_WORD_KBN_AND,
                GSConstNippou.KEY_WORD_KBN_OR},
                NullDefault.getInt(ntp100SvKeyWordkbn__, -1)) < 0) {
            return false;
        }

        // オーダーキー
        if (ArrayUtils.indexOf(new int[] {GSConstNippou.ORDER_KEY_ASC,
                GSConstNippou.ORDER_KEY_DESC},
                ntp100SvOrderKey1__) < 0) {
            return false;
        }
        // ソートキー
        if (ArrayUtils.indexOf(GSConstNippou.LIST_SORT_KEY_ALL,
                ntp100SvSortKey1__) < 0) {
            return false;
        }
        // オーダーキー2
        if (ArrayUtils.indexOf(new int[] {GSConstNippou.ORDER_KEY_ASC,
                GSConstNippou.ORDER_KEY_DESC},
                ntp100SvOrderKey2__) < 0) {
            return false;
        }
        // ソートキー2
        if (ArrayUtils.indexOf(GSConstNippou.LIST_SORT_KEY_ALL,
                ntp100SvSortKey2__) < 0) {
            return false;
        }
        // タイトルカラー
        if (ntp100SvBgcolor__ != null) {
            for (String chkStr : ntp100SvBgcolor__) {
                if (ArrayUtils.indexOf(
                        GSConstNippou.BGCOLOR_ALL,
                        NullDefault.getInt(chkStr, -1)) < 0) {
                    return false;
                }

            }
        }
        // 見込み度
        if (ntp100SvMikomido__ != null) {
            for (String chkStr : ntp100SvMikomido__) {
                if (ArrayUtils.indexOf(
                        GSConstNippou.MIKOMI_ALL,
                        chkStr) < 0) {
                    return false;
                }

            }
        }

        // 案件SID
        if (!StringUtil.isNullZeroString(ntp100SvAnkenSid__)
                && !ValidateUtil.isNumberHaifun(
            ntp100SvAnkenSid__)) {
             return false;
        }

        // 企業SID
        if (!StringUtil.isNullZeroString(ntp100SvCompanySid__)
                && !ValidateUtil.isNumberHaifun(
            ntp100SvCompanySid__)) {
             return false;
        }

        // 拠点SID
        if (!StringUtil.isNullZeroString(ntp100SvCompanyBaseSid__)
                && !ValidateUtil.isNumberHaifun(
            ntp100SvCompanyBaseSid__)) {
             return false;
        }


        // 活動分類
        if (!StringUtil.isNullZeroString(ntp100SvKtbunrui__)
                && !ValidateUtil.isNumberHaifun(
            ntp100SvKtbunrui__)) {
             return false;
        }

        // 活動方法
        if (!StringUtil.isNullZeroString(ntp100SvKthouhou__)
                && !ValidateUtil.isNumberHaifun(
            ntp100SvKthouhou__)) {
             return false;
        }


        return true;
    }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目がJIS第2水準文字かチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkJisString(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;
        //JIS第2水準文字か
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }
    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param keyWord キーワード
     * @param searchTarget 検索対象
     * @param reqMdl リクエストモデル
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchTarget(
        ActionErrors errors,
        String keyWord,
        String[] searchTarget,
        RequestModel reqMdl) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //キーワード未入力時はチェックなし
        if (NullDefault.getString(keyWord, "").length() < 1) {
            return false;
        }

        if (searchTarget == null || searchTarget.length < 1) {
            //検索対象
            String textSort = gsMsg.getMessage("cmn.search2");
            //未選択の場合エラー
            msg = new ActionMessage(
                    "error.select.required.text", textSort);
            StrutsUtil.addMessage(errors, msg, "target");
            return true;
        }

        return false;
    }

    /**
     * <p>ntp100Bgcolor を取得します。
     * @return ntp100Bgcolor
     */
    public String[] getNtp100Bgcolor() {
        return ntp100Bgcolor__;
    }

    /**
     * <p>ntp100Bgcolor をセットします。
     * @param ntp100Bgcolor ntp100Bgcolor
     */
    public void setNtp100Bgcolor(String[] ntp100Bgcolor) {
        ntp100Bgcolor__ = ntp100Bgcolor;
    }

    /**
     * <p>ntp100ColorMsgList を取得します。
     * @return ntp100ColorMsgList
     */
    public ArrayList<String> getNtp100ColorMsgList() {
        return ntp100ColorMsgList__;
    }

    /**
     * <p>ntp100ColorMsgList をセットします。
     * @param ntp100ColorMsgList ntp100ColorMsgList
     */
    public void setNtp100ColorMsgList(ArrayList<String> ntp100ColorMsgList) {
        ntp100ColorMsgList__ = ntp100ColorMsgList;
    }

    /**
     * <p>ntp100SvBgcolor を取得します。
     * @return ntp100SvBgcolor
     */
    public String[] getNtp100SvBgcolor() {
        return ntp100SvBgcolor__;
    }

    /**
     * <p>ntp100SvBgcolor をセットします。
     * @param ntp100SvBgcolor ntp100SvBgcolor
     */
    public void setNtp100SvBgcolor(String[] ntp100SvBgcolor) {
        ntp100SvBgcolor__ = ntp100SvBgcolor;
    }

    /**
     * <p>ntp100searchUse を取得します。
     * @return ntp100searchUse
     */
    public int getNtp100searchUse() {
        return ntp100searchUse__;
    }

    /**
     * <p>ntp100searchUse をセットします。
     * @param ntp100searchUse ntp100searchUse
     */
    public void setNtp100searchUse(int ntp100searchUse) {
        ntp100searchUse__ = ntp100searchUse;
    }

    /**
     * <p>ntp100SelectUsrSid を取得します。
     * @return ntp100SelectUsrSid
     */
    public String getNtp100SelectUsrSid() {
        return ntp100SelectUsrSid__;
    }

    /**
     * <p>ntp100SelectUsrSid をセットします。
     * @param ntp100SelectUsrSid ntp100SelectUsrSid
     */
    public void setNtp100SelectUsrSid(String ntp100SelectUsrSid) {
        ntp100SelectUsrSid__ = ntp100SelectUsrSid;
    }

    /**
     * <p>ntp100WebSearchWord を取得します。
     * @return ntp100WebSearchWord
     */
    public String getNtp100WebSearchWord() {
        return ntp100WebSearchWord__;
    }

    /**
     * <p>ntp100WebSearchWord をセットします。
     * @param ntp100WebSearchWord ntp100WebSearchWord
     */
    public void setNtp100WebSearchWord(String ntp100WebSearchWord) {
        ntp100WebSearchWord__ = ntp100WebSearchWord;
    }

    /**
     * <p>ntp100Ktbunrui を取得します。
     * @return ntp100Ktbunrui
     */
    public String getNtp100Ktbunrui() {
        return ntp100Ktbunrui__;
    }

    /**
     * <p>ntp100Ktbunrui をセットします。
     * @param ntp100Ktbunrui ntp100Ktbunrui
     */
    public void setNtp100Ktbunrui(String ntp100Ktbunrui) {
        ntp100Ktbunrui__ = ntp100Ktbunrui;
    }

    /**
     * <p>ntp100Kthouhou を取得します。
     * @return ntp100Kthouhou
     */
    public String getNtp100Kthouhou() {
        return ntp100Kthouhou__;
    }

    /**
     * <p>ntp100Kthouhou をセットします。
     * @param ntp100Kthouhou ntp100Kthouhou
     */
    public void setNtp100Kthouhou(String ntp100Kthouhou) {
        ntp100Kthouhou__ = ntp100Kthouhou;
    }

    /**
     * <p>ntp100KtbunruiLavel を取得します。
     * @return ntp100KtbunruiLavel
     */
    public List<LabelValueBean> getNtp100KtbunruiLavel() {
        return ntp100KtbunruiLavel__;
    }

    /**
     * <p>ntp100KtbunruiLavel をセットします。
     * @param ntp100KtbunruiLavel ntp100KtbunruiLavel
     */
    public void setNtp100KtbunruiLavel(List<LabelValueBean> ntp100KtbunruiLavel) {
        ntp100KtbunruiLavel__ = ntp100KtbunruiLavel;
    }

    /**
     * <p>ntp100KthouhouLavel を取得します。
     * @return ntp100KthouhouLavel
     */
    public List<LabelValueBean> getNtp100KthouhouLavel() {
        return ntp100KthouhouLavel__;
    }

    /**
     * <p>ntp100KthouhouLavel をセットします。
     * @param ntp100KthouhouLavel ntp100KthouhouLavel
     */
    public void setNtp100KthouhouLavel(List<LabelValueBean> ntp100KthouhouLavel) {
        ntp100KthouhouLavel__ = ntp100KthouhouLavel;
    }

    /**
     * <p>ntp100Mikomido を取得します。
     * @return ntp100Mikomido
     */
    public String[] getNtp100Mikomido() {
        return ntp100Mikomido__;
    }

    /**
     * <p>ntp100Mikomido をセットします。
     * @param ntp100Mikomido ntp100Mikomido
     */
    public void setNtp100Mikomido(String[] ntp100Mikomido) {
        ntp100Mikomido__ = ntp100Mikomido;
    }

    /**
     * <p>ntp100SvMikomido を取得します。
     * @return ntp100SvMikomido
     */
    public String[] getNtp100SvMikomido() {
        return ntp100SvMikomido__;
    }

    /**
     * <p>ntp100SvMikomido をセットします。
     * @param ntp100SvMikomido ntp100SvMikomido
     */
    public void setNtp100SvMikomido(String[] ntp100SvMikomido) {
        ntp100SvMikomido__ = ntp100SvMikomido;
    }

    /**
     * <p>ntp100YearLabel を取得します。
     * @return ntp100YearLabel
     */
    public List<LabelValueBean> getNtp100YearLabel() {
        return ntp100YearLabel__;
    }

    /**
     * <p>ntp100YearLabel をセットします。
     * @param ntp100YearLabel ntp100YearLabel
     */
    public void setNtp100YearLabel(List<LabelValueBean> ntp100YearLabel) {
        ntp100YearLabel__ = ntp100YearLabel;
    }

    /**
     * <p>ntp100MonthLabel を取得します。
     * @return ntp100MonthLabel
     */
    public List<LabelValueBean> getNtp100MonthLabel() {
        return ntp100MonthLabel__;
    }

    /**
     * <p>ntp100MonthLabel をセットします。
     * @param ntp100MonthLabel ntp100MonthLabel
     */
    public void setNtp100MonthLabel(List<LabelValueBean> ntp100MonthLabel) {
        ntp100MonthLabel__ = ntp100MonthLabel;
    }

    /**
     * <p>ntp100DayLabel を取得します。
     * @return ntp100DayLabel
     */
    public List<LabelValueBean> getNtp100DayLabel() {
        return ntp100DayLabel__;
    }

    /**
     * <p>ntp100DayLabel をセットします。
     * @param ntp100DayLabel ntp100DayLabel
     */
    public void setNtp100DayLabel(List<LabelValueBean> ntp100DayLabel) {
        ntp100DayLabel__ = ntp100DayLabel;
    }

    /**
     * <p>ntp100SltYearFr を取得します。
     * @return ntp100SltYearFr
     */
    public String getNtp100SltYearFr() {
        return ntp100SltYearFr__;
    }

    /**
     * <p>ntp100SltYearFr をセットします。
     * @param ntp100SltYearFr ntp100SltYearFr
     */
    public void setNtp100SltYearFr(String ntp100SltYearFr) {
        ntp100SltYearFr__ = ntp100SltYearFr;
    }

    /**
     * <p>ntp100SltMonthFr を取得します。
     * @return ntp100SltMonthFr
     */
    public String getNtp100SltMonthFr() {
        return ntp100SltMonthFr__;
    }

    /**
     * <p>ntp100SltMonthFr をセットします。
     * @param ntp100SltMonthFr ntp100SltMonthFr
     */
    public void setNtp100SltMonthFr(String ntp100SltMonthFr) {
        ntp100SltMonthFr__ = ntp100SltMonthFr;
    }

    /**
     * <p>ntp100SltDayFr を取得します。
     * @return ntp100SltDayFr
     */
    public String getNtp100SltDayFr() {
        return ntp100SltDayFr__;
    }

    /**
     * <p>ntp100SltDayFr をセットします。
     * @param ntp100SltDayFr ntp100SltDayFr
     */
    public void setNtp100SltDayFr(String ntp100SltDayFr) {
        ntp100SltDayFr__ = ntp100SltDayFr;
    }

    /**
     * <p>ntp100SltYearTo を取得します。
     * @return ntp100SltYearTo
     */
    public String getNtp100SltYearTo() {
        return ntp100SltYearTo__;
    }

    /**
     * <p>ntp100SltYearTo をセットします。
     * @param ntp100SltYearTo ntp100SltYearTo
     */
    public void setNtp100SltYearTo(String ntp100SltYearTo) {
        ntp100SltYearTo__ = ntp100SltYearTo;
    }

    /**
     * <p>ntp100SltMonthTo を取得します。
     * @return ntp100SltMonthTo
     */
    public String getNtp100SltMonthTo() {
        return ntp100SltMonthTo__;
    }

    /**
     * <p>ntp100SltMonthTo をセットします。
     * @param ntp100SltMonthTo ntp100SltMonthTo
     */
    public void setNtp100SltMonthTo(String ntp100SltMonthTo) {
        ntp100SltMonthTo__ = ntp100SltMonthTo;
    }

    /**
     * <p>ntp100SltDayTo を取得します。
     * @return ntp100SltDayTo
     */
    public String getNtp100SltDayTo() {
        return ntp100SltDayTo__;
    }

    /**
     * <p>ntp100SltDayTo をセットします。
     * @param ntp100SltDayTo ntp100SltDayTo
     */
    public void setNtp100SltDayTo(String ntp100SltDayTo) {
        ntp100SltDayTo__ = ntp100SltDayTo;
    }

    /**
     * <p>ntp100SltDateFr を取得します。
     * @return ntp100SltDateFr
     * @see jp.groupsession.v2.ntp.ntp100.Ntp100Form#ntp100SltDateFr__
     */
    public String getNtp100SltDateFr() {
        return ntp100SltDateFr__;
    }


    /**
     * <p>ntp100SltDateFr をセットします。
     * @param ntp100SltDateFr ntp100SltDateFr
     * @see jp.groupsession.v2.ntp.ntp100.Ntp100Form#ntp100SltDateFr__
     */
    public void setNtp100SltDateFr(String ntp100SltDateFr) {
        ntp100SltDateFr__ = ntp100SltDateFr;
    }


    /**
     * <p>ntp100SltDateTo を取得します。
     * @return ntp100SltDateTo
     * @see jp.groupsession.v2.ntp.ntp100.Ntp100Form#ntp100SltDateTo__
     */
    public String getNtp100SltDateTo() {
        return ntp100SltDateTo__;
    }


    /**
     * <p>ntp100SltDateTo をセットします。
     * @param ntp100SltDateTo ntp100SltDateTo
     * @see jp.groupsession.v2.ntp.ntp100.Ntp100Form#ntp100SltDateTo__
     */
    public void setNtp100SltDateTo(String ntp100SltDateTo) {
        ntp100SltDateTo__ = ntp100SltDateTo;
    }


    /**
     * <p>ntp100SvSltYearFr を取得します。
     * @return ntp100SvSltYearFr
     */
    public String getNtp100SvSltYearFr() {
        return ntp100SvSltYearFr__;
    }

    /**
     * <p>ntp100SvSltYearFr をセットします。
     * @param ntp100SvSltYearFr ntp100SvSltYearFr
     */
    public void setNtp100SvSltYearFr(String ntp100SvSltYearFr) {
        ntp100SvSltYearFr__ = ntp100SvSltYearFr;
    }

    /**
     * <p>ntp100SvSltMonthFr を取得します。
     * @return ntp100SvSltMonthFr
     */
    public String getNtp100SvSltMonthFr() {
        return ntp100SvSltMonthFr__;
    }

    /**
     * <p>ntp100SvSltMonthFr をセットします。
     * @param ntp100SvSltMonthFr ntp100SvSltMonthFr
     */
    public void setNtp100SvSltMonthFr(String ntp100SvSltMonthFr) {
        ntp100SvSltMonthFr__ = ntp100SvSltMonthFr;
    }

    /**
     * <p>ntp100SvSltDayFr を取得します。
     * @return ntp100SvSltDayFr
     */
    public String getNtp100SvSltDayFr() {
        return ntp100SvSltDayFr__;
    }

    /**
     * <p>ntp100SvSltDayFr をセットします。
     * @param ntp100SvSltDayFr ntp100SvSltDayFr
     */
    public void setNtp100SvSltDayFr(String ntp100SvSltDayFr) {
        ntp100SvSltDayFr__ = ntp100SvSltDayFr;
    }

    /**
     * <p>ntp100SvSltYearTo を取得します。
     * @return ntp100SvSltYearTo
     */
    public String getNtp100SvSltYearTo() {
        return ntp100SvSltYearTo__;
    }

    /**
     * <p>ntp100SvSltYearTo をセットします。
     * @param ntp100SvSltYearTo ntp100SvSltYearTo
     */
    public void setNtp100SvSltYearTo(String ntp100SvSltYearTo) {
        ntp100SvSltYearTo__ = ntp100SvSltYearTo;
    }

    /**
     * <p>ntp100SvSltMonthTo を取得します。
     * @return ntp100SvSltMonthTo
     */
    public String getNtp100SvSltMonthTo() {
        return ntp100SvSltMonthTo__;
    }

    /**
     * <p>ntp100SvSltMonthTo をセットします。
     * @param ntp100SvSltMonthTo ntp100SvSltMonthTo
     */
    public void setNtp100SvSltMonthTo(String ntp100SvSltMonthTo) {
        ntp100SvSltMonthTo__ = ntp100SvSltMonthTo;
    }

    /**
     * <p>ntp100SvSltDayTo を取得します。
     * @return ntp100SvSltDayTo
     */
    public String getNtp100SvSltDayTo() {
        return ntp100SvSltDayTo__;
    }

    /**
     * <p>ntp100SvSltDayTo をセットします。
     * @param ntp100SvSltDayTo ntp100SvSltDayTo
     */
    public void setNtp100SvSltDayTo(String ntp100SvSltDayTo) {
        ntp100SvSltDayTo__ = ntp100SvSltDayTo;
    }

    /**
     * <p>ntp100AnkenSid を取得します。
     * @return ntp100AnkenSid
     */
    public String getNtp100AnkenSid() {
        return ntp100AnkenSid__;
    }

    /**
     * <p>ntp100AnkenSid をセットします。
     * @param ntp100AnkenSid ntp100AnkenSid
     */
    public void setNtp100AnkenSid(String ntp100AnkenSid) {
        ntp100AnkenSid__ = ntp100AnkenSid;
    }

    /**
     * <p>ntp100CompanySid を取得します。
     * @return ntp100CompanySid
     */
    public String getNtp100CompanySid() {
        return ntp100CompanySid__;
    }

    /**
     * <p>ntp100CompanySid をセットします。
     * @param ntp100CompanySid ntp100CompanySid
     */
    public void setNtp100CompanySid(String ntp100CompanySid) {
        ntp100CompanySid__ = ntp100CompanySid;
    }

    /**
     * <p>ntp100CompanyBaseSid を取得します。
     * @return ntp100CompanyBaseSid
     */
    public String getNtp100CompanyBaseSid() {
        return ntp100CompanyBaseSid__;
    }

    /**
     * <p>ntp100CompanyBaseSid をセットします。
     * @param ntp100CompanyBaseSid ntp100CompanyBaseSid
     */
    public void setNtp100CompanyBaseSid(String ntp100CompanyBaseSid) {
        ntp100CompanyBaseSid__ = ntp100CompanyBaseSid;
    }

    /**
     * <p>ntp100SvAnkenSid を取得します。
     * @return ntp100SvAnkenSid
     */
    public String getNtp100SvAnkenSid() {
        return ntp100SvAnkenSid__;
    }

    /**
     * <p>ntp100SvAnkenSid をセットします。
     * @param ntp100SvAnkenSid ntp100SvAnkenSid
     */
    public void setNtp100SvAnkenSid(String ntp100SvAnkenSid) {
        ntp100SvAnkenSid__ = ntp100SvAnkenSid;
    }

    /**
     * <p>ntp100SvCompanySid を取得します。
     * @return ntp100SvCompanySid
     */
    public String getNtp100SvCompanySid() {
        return ntp100SvCompanySid__;
    }

    /**
     * <p>ntp100SvCompanySid をセットします。
     * @param ntp100SvCompanySid ntp100SvCompanySid
     */
    public void setNtp100SvCompanySid(String ntp100SvCompanySid) {
        ntp100SvCompanySid__ = ntp100SvCompanySid;
    }

    /**
     * <p>ntp100SvCompanyBaseSid を取得します。
     * @return ntp100SvCompanyBaseSid
     */
    public String getNtp100SvCompanyBaseSid() {
        return ntp100SvCompanyBaseSid__;
    }

    /**
     * <p>ntp100SvCompanyBaseSid をセットします。
     * @param ntp100SvCompanyBaseSid ntp100SvCompanyBaseSid
     */
    public void setNtp100SvCompanyBaseSid(String ntp100SvCompanyBaseSid) {
        ntp100SvCompanyBaseSid__ = ntp100SvCompanyBaseSid;
    }

    /**
     * <p>ntp100SvKtbunrui を取得します。
     * @return ntp100SvKtbunrui
     */
    public String getNtp100SvKtbunrui() {
        return ntp100SvKtbunrui__;
    }

    /**
     * <p>ntp100SvKtbunrui をセットします。
     * @param ntp100SvKtbunrui ntp100SvKtbunrui
     */
    public void setNtp100SvKtbunrui(String ntp100SvKtbunrui) {
        ntp100SvKtbunrui__ = ntp100SvKtbunrui;
    }

    /**
     * <p>ntp100SvKthouhou を取得します。
     * @return ntp100SvKthouhou
     */
    public String getNtp100SvKthouhou() {
        return ntp100SvKthouhou__;
    }

    /**
     * <p>ntp100SvKthouhou をセットします。
     * @param ntp100SvKthouhou ntp100SvKthouhou
     */
    public void setNtp100SvKthouhou(String ntp100SvKthouhou) {
        ntp100SvKthouhou__ = ntp100SvKthouhou;
    }

    /**
     * <p>ntp100NtpAnkenModel を取得します。
     * @return ntp100NtpAnkenModel
     */
    public NtpAnkenModel getNtp100NtpAnkenModel() {
        return ntp100NtpAnkenModel__;
    }

    /**
     * <p>ntp100NtpAnkenModel をセットします。
     * @param ntp100NtpAnkenModel ntp100NtpAnkenModel
     */
    public void setNtp100NtpAnkenModel(NtpAnkenModel ntp100NtpAnkenModel) {
        ntp100NtpAnkenModel__ = ntp100NtpAnkenModel;
    }

    /**
     * <p>ntp100AdrCompanyMdl を取得します。
     * @return ntp100AdrCompanyMdl
     */
    public AdrCompanyModel getNtp100AdrCompanyMdl() {
        return ntp100AdrCompanyMdl__;
    }

    /**
     * <p>ntp100AdrCompanyMdl をセットします。
     * @param ntp100AdrCompanyMdl ntp100AdrCompanyMdl
     */
    public void setNtp100AdrCompanyMdl(AdrCompanyModel ntp100AdrCompanyMdl) {
        ntp100AdrCompanyMdl__ = ntp100AdrCompanyMdl;
    }

    /**
     * <p>ntp100AdrCompanyBaseMdl を取得します。
     * @return ntp100AdrCompanyBaseMdl
     */
    public AdrCompanyBaseModel getNtp100AdrCompanyBaseMdl() {
        return ntp100AdrCompanyBaseMdl__;
    }

    /**
     * <p>ntp100AdrCompanyBaseMdl をセットします。
     * @param ntp100AdrCompanyBaseMdl ntp100AdrCompanyBaseMdl
     */
    public void setNtp100AdrCompanyBaseMdl(
            AdrCompanyBaseModel ntp100AdrCompanyBaseMdl) {
        ntp100AdrCompanyBaseMdl__ = ntp100AdrCompanyBaseMdl;
    }

    /**
     * <p>ntp100KeyValue を取得します。
     * @return ntp100KeyValue
     */
    public String getNtp100KeyValue() {
        return ntp100KeyValue__;
    }

    /**
     * <p>ntp100KeyValue をセットします。
     * @param ntp100KeyValue ntp100KeyValue
     */
    public void setNtp100KeyValue(String ntp100KeyValue) {
        ntp100KeyValue__ = ntp100KeyValue;
    }

    /**
     * <p>ntp100SelectNtpAreaSid を取得します。
     * @return ntp100SelectNtpAreaSid
     */
    public String getNtp100SelectNtpAreaSid() {
        return ntp100SelectNtpAreaSid__;
    }

    /**
     * <p>ntp100SelectNtpAreaSid をセットします。
     * @param ntp100SelectNtpAreaSid ntp100SelectNtpAreaSid
     */
    public void setNtp100SelectNtpAreaSid(String ntp100SelectNtpAreaSid) {
        ntp100SelectNtpAreaSid__ = ntp100SelectNtpAreaSid;
    }

    /**
     * <p>ntp100CsvOutField を取得します。
     * @return ntp100CsvOutField
     */
    public String[] getNtp100CsvOutField() {
        return ntp100CsvOutField__;
    }

    /**
     * <p>ntp100CsvOutField をセットします。
     * @param ntp100CsvOutField ntp100CsvOutField
     */
    public void setNtp100CsvOutField(String[] ntp100CsvOutField) {
        ntp100CsvOutField__ = ntp100CsvOutField;
    }
    /**
     *
     * <br>[機  能] 検索項目引継ぎ用パラメータを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return hiddenで設定されるパラメータ
     */
    public List<LabelValueBean> getSearchHiddenParamList() {
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>(
                ParameterUtil.getParameterLabels(this,
                new String[] {
                        "ntp010searchWord",
                        "ntp100SelectNtpAreaSid",
                        "ntp100AnkenSid",
                        "ntp100CompanySid",
                        "ntp100CompanyBaseSid",
                        "ntp100SvAnkenSid",
                        "ntp100SvCompanySid",
                        "ntp100SvCompanyBaseSid",
                        "ntp100Ktbunrui",
                        "ntp100Kthouhou",
                        "ntp100SvKtbunrui",
                        "ntp100SvKthouhou",
                        "ntp100SltGroup",
                        "ntp100SltUser",
                        "ntp100SvSltGroup",
                        "ntp100SvSltUser",
                        "ntp100SltYearFr",
                        "ntp100SltMonthFr",
                        "ntp100SltDayFr",
                        "ntp100SltYearTo",
                        "ntp100SltMonthTo",
                        "ntp100SltDayTo",
                        "ntp100SvSltYearFr",
                        "ntp100SvSltMonthFr",
                        "ntp100SvSltDayFr",
                        "ntp100SvSltYearTo",
                        "ntp100SvSltMonthTo",
                        "ntp100SvSltDayTo",
                        "ntp100KeyWordkbn",
                        "ntp100KeyValue",
                        "ntp100SvKeyWordkbn",
                        "ntp100SvKeyValue",
                        "ntp100PageNum",
                        "ntp100Slt_page1",
                        "ntp100Slt_page2",
                        "ntp100OrderKey1",
                        "ntp100SortKey1",
                        "ntp100OrderKey2",
                        "ntp100SortKey2",
                        "ntp100SvOrderKey1",
                        "ntp100SvSortKey1",
                        "ntp100SvOrderKey2",
                        "ntp100SvSortKey2",
                        "ntp100SvSearchTarget",
                        "ntp100SearchTarget",
                        "ntp100SvBgcolor",
                        "ntp100Bgcolor",
                        "ntp100SvMikomido",
                        "ntp100Mikomido",
                        "ntp100CsvOutField"
                }));

        return ret;

    }

}