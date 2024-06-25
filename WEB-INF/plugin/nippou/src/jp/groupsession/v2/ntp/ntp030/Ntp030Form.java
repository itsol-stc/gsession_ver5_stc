package jp.groupsession.v2.ntp.ntp030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp020.Ntp020Form;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030CompanyModel;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030DataModel;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030UsrLabelModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 日間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030Form extends Ntp020Form {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp030Form.class);
    /** 日報データ有無フラグ true=有り false:無し*/
    private boolean ntp030DataFlg__ = false;

    /** セッションユーザSID */
    private String ntp030SessionSid__;
    /** 施設予約プラグイン使用有無 0=使用 1=未使用*/
    private int reservePluginKbn__;
    /** アドレス帳プラグイン使用有無 0=使用 1=未使用*/
    private int addressPluginKbn__;
    /** WEB検索プラグイン使用有無 0=使用 1=未使用*/
    private int searchPluginKbn__;
    /** 選択ユーザSID */
    private String ntp030SelectUsrSid__;
    /** ラベル最終表示日時 */
    private String ntp030LabelDate__;

    /** Offset(データ取得時使用) */
    private int ntp030Offset__ = 1;

    /** 日報対象ユーザ名称 */
    private String ntp030UsrName__ = null;
    /** 日報登録者名称 */
    private String ntp030AddUsrName__ = null;
    /** 日報登録者削除区分 */
    private String ntp030AddUsrJkbn__ = null;
    /** 日報登録日時 */
    private String ntp030AddDate__ = null;
    /** 背景色 */
    private String ntp030Bgcolor__ = null;
    /** 背景色 初期値 */
    private String ntp030BgcolorInit__ = null;
    /** 日報タイトル */
    private String ntp030Title__ = null;
    /** 日報内容 */
    private String ntp030Value__ = null;
    /** 次のアクション 年 */
    private String ntp030NxtActYear__ = null;
    /** 次のアクション 月 */
    private String ntp030NxtActMonth__ = null;
    /** 次のアクション 日 */
    private String ntp030NxtActDay__ = null;
    /** 次のアクション */
    private String ntp030NextAction__ = null;
    /** 次のアクション 日付区分 0:指定なし 1:指定あり*/
    private int ntp030ActDateKbn__ = 0;
    /** 日報備考 */
    private String ntp030Biko__ = null;
    /** 公開区分 */
    private String ntp030Public__ = null;
    /** 開始年 */
    private String ntp030FrYear__ = null;
    /** 開始月 */
    private String ntp030FrMonth__ = null;
    /** 開始日 */
    private String ntp030FrDay__ = null;
    /** 開始時 */
    private String ntp030FrHour__ = null;
    /** 開始分 */
    private String ntp030FrMin__ = null;
    /** 終了年 */
    private String ntp030ToYear__ = null;
    /** 終了月 */
    private String ntp030ToMonth__ = null;
    /** 終了日 */
    private String ntp030ToDay__ = null;
    /** 終了時 */
    private String ntp030ToHour__ = null;
    /** 終了分 */
    private String ntp030ToMin__ = null;

    /** 日報画面遷移フラグ  0:通常 1:編集画面で報告日付編集後に遷移*/
    private int ntp030DspMoveFlg__ = 0;

    /** 日報データ編集権限  0:権限あり 1:権限なし*/
    private int ntp030EditKbn__ = 0;
    /** アドレス帳利用権限  0:権限あり 1:権限なし*/
    private int ntp030AddressUseOk__ = 0;
    /** ショートメール利用権限  0:権限あり 1:権限なし*/
    private int ntp030SmailUseOk__ = 0;

    /** 日報データ */
    private String nippouData__ = null;
    /** 日報データリスト */
    private List<Ntp030Param> ntp030nippouDataList__ = null;
    /** 活動分類 */
    private String ntp030Ktbunrui__ = null;
    /** 活動方法  */
    private String ntp030Kthouhou__ = null;
    /** 活動分類リスト */
    private List<LabelValueBean> ntp030KtbunruiLavel__ = null;
    /** 活動方法リスト */
    private List<LabelValueBean> ntp030KthouhouLavel__ = null;
    /** 見込み度  */
    private String ntp030Mikomido__ = null;

    /** プラグインID */
    private String ntp030pluginId__ = GSConstNippou.PLUGIN_ID_NIPPOU;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String ntp030BinSid__;

    /** 日報データリスト  */
    private List<Ntp030DataModel> ntp030DataModelList__;

    /** 添付ファイルリスト */
    private ArrayList<CmnBinfModel> ntp030FileList__ = null;

    /** 閲覧ユーザ情報 */
    private CmnUsrmInfModel ntp030UsrInfMdl__ = null;

    /** 同時登録グループSID */
    private String ntp030GroupSid__ = null;
    /** セーブユーザーリスト */
    private String[] sv_users__ = null;
    /** ユーザーリスト（同時登録）*/
    private String[] users_r__ = null;

    /** 年リスト */
    private ArrayList <LabelValueBean> ntp030YearLavel__ = null;
    /** 月リスト */
    private ArrayList <LabelValueBean> ntp030MonthLavel__ = null;
    /** 日リスト */
    private ArrayList <LabelValueBean> ntp030DayLavel__ = null;
    /** 時リスト */
    private ArrayList <LabelValueBean> ntp030HourLavel__ = null;
    /** 分リスト */
    private ArrayList <LabelValueBean> ntp030MinuteLavel__ = null;
    /** 同時登録グループリスト */
    private List<NtpLabelValueModel> ntp030GroupLavel__ = null;
    /** 同時登録グループ所属ユーザリスト */
    private ArrayList <CmnUsrmInfModel> ntp030BelongLavel__ = null;
    /** 同時登録ユーザリスト */
    private ArrayList <CmnUsrmInfModel> ntp030SelectUsrLavel__ = null;
    /** 既登録の同時登録ユーザリスト */
    private ArrayList <CmnUsrmInfModel> ntp030AddedUsrLavel__ = null;
    /** カラーコメントリスト */
    private ArrayList <String> ntp030ColorMsgList__ = null;

    /** 繰り返しで登録日報表示フラグ */
    private boolean ntp030ExTextDspFlg__ = false;

    /** 同時登録日報へ反映有無 */
    private String ntp030BatchRef__ = "1";

    /** 編集権限設定 0=未設定 1=本人のみ 2=所属グループ */
    private String ntp030Edit__ = null;

    /** 時間指定有無 0=有り 1=無し */
    private String ntp030TimeKbn__ = String.valueOf(GSConstSchedule.TIME_EXIST);

    //施設予約
    /** 同時登録施設予約へ反映有無 */
    private String ntp030ResBatchRef__ = "1";
    /** 同時登録施設グループSID */
    private String ntp030ReserveGroupSid__ = null;
    /** セーブ施設リスト */
    private String[] svReserveUsers__ = null;
    /** 施設リスト（同時登録）*/
    private String[] reserve_r__ = null;

    /** 同時登録施設グループリスト */
    private List<LabelValueBean> ntp030ReserveGroupLavel__ = null;
    /** 同時登録施設グループ所属施設リスト */
    private ArrayList <RsvSisDataModel> ntp030ReserveBelongLavel__ = null;
    /** 同時登録施設リスト */
    private ArrayList <RsvSisDataModel> ntp030ReserveSelectLavel__ = null;

    /** 同時登録されたアクセス権限のない施設予約数 */
    private int ntp030CantReadRsvCount__ = 0;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ntp030InitFlg__ = String.valueOf(GSConstSchedule.INIT_FLG);

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ntp030ScrollFlg__ = "0";

    /** 複写フラグ 0=通常 1=複写 */
    private String ntp030CopyFlg__ = GSConstSchedule.NOT_COPY_FLG;

    //会社情報
    /** 会社SID */
    private String[] ntp030CompanySid__ = null;
    /** 会社拠点SID */
    private String[] ntp030CompanyBaseSid__ = null;
    /** 担当者(アドレス情報) */
    private String[] ntp030AddressId__ = null;
    /** コンタクト履歴に反映 */
    private int ntp030contact__ = 0;

    /** 削除対象の会社ID */
    private String ntp030delCompanyId__ = null;
    /** 削除対象の会社拠点ID */
    private String ntp030delCompanyBaseId__ = null;

    /** 会社情報一覧 */
    private List<Ntp030CompanyModel> ntp030CompanyList__ = null;


    /** ユーザコンボ */
    private List<Ntp030UsrLabelModel> ntp030UsrLabelList__ = null;


    /** ソート順 */
    private int ntp030Sort__ = GSConstNippou.DATE_DESC_EDATE_DESC;

    /** ソートラベル */
    private ArrayList <LabelValueBean> ntp030SortLabel__ = null;

    /**
     * <p>ntp030TimeKbn を取得します。
     * @return ntp030TimeKbn
     */
    public String getNtp030TimeKbn() {
        return ntp030TimeKbn__;
    }

    /**
     * <p>ntp030TimeKbn をセットします。
     * @param ntp030TimeKbn ntp030TimeKbn
     */
    public void setNtp030TimeKbn(String ntp030TimeKbn) {
        ntp030TimeKbn__ = ntp030TimeKbn;
    }

    /**
     * <p>ntp030ColorMsgList を取得します。
     * @return ntp030ColorMsgList
     */
    public ArrayList<String> getNtp030ColorMsgList() {
        return ntp030ColorMsgList__;
    }

    /**
     * <p>ntp030ColorMsgList をセットします。
     * @param ntp030ColorMsgList ntp030ColorMsgList
     */
    public void setNtp030ColorMsgList(ArrayList<String> ntp030ColorMsgList) {
        ntp030ColorMsgList__ = ntp030ColorMsgList;
    }

    /**
     * <p>ntp030DataFlg を取得します。
     * @return ntp030DataFlg
     */
    public boolean isNtp030DataFlg() {
        return ntp030DataFlg__;
    }

    /**
     * <p>ntp030DataFlg をセットします。
     * @param ntp030DataFlg ntp030DataFlg
     */
    public void setNtp030DataFlg(boolean ntp030DataFlg) {
        ntp030DataFlg__ = ntp030DataFlg;
    }

    /**
     * <p>reservePluginKbn を取得します。
     * @return reservePluginKbn
     */
    public int getReservePluginKbn() {
        return reservePluginKbn__;
    }

    /**
     * <p>reservePluginKbn をセットします。
     * @param reservePluginKbn reservePluginKbn
     */
    public void setReservePluginKbn(int reservePluginKbn) {
        reservePluginKbn__ = reservePluginKbn;
    }

    /**
     * <p>addressPluginKbn を取得します。
     * @return addressPluginKbn
     */
    public int getAddressPluginKbn() {
        return addressPluginKbn__;
    }

    /**
     * <p>addressPluginKbn をセットします。
     * @param addressPluginKbn addressPluginKbn
     */
    public void setAddressPluginKbn(int addressPluginKbn) {
        addressPluginKbn__ = addressPluginKbn;
    }

    /**
     * <p>searchPluginKbn を取得します。
     * @return searchPluginKbn
     */
    public int getSearchPluginKbn() {
        return searchPluginKbn__;
    }

    /**
     * <p>searchPluginKbn をセットします。
     * @param searchPluginKbn searchPluginKbn
     */
    public void setSearchPluginKbn(int searchPluginKbn) {
        searchPluginKbn__ = searchPluginKbn;
    }

    /**
     * <p>ntp030ResBatchRef を取得します。
     * @return ntp030ResBatchRef
     */
    public String getNtp030ResBatchRef() {
        return ntp030ResBatchRef__;
    }

    /**
     * <p>ntp030ResBatchRef をセットします。
     * @param ntp030ResBatchRef ntp030ResBatchRef
     */
    public void setNtp030ResBatchRef(String ntp030ResBatchRef) {
        ntp030ResBatchRef__ = ntp030ResBatchRef;
    }

    /**
     * <p>reserve_r を取得します。
     * @return reserve_r
     */
    public String[] getReserve_r() {
        return reserve_r__;
    }

    /**
     * <p>reserve_r をセットします。
     * @param reserver reserve_r
     */
    public void setReserve_r(String[] reserver) {
        reserve_r__ = reserver;
    }

    /**
     * <p>ntp030ReserveBelongLavel を取得します。
     * @return ntp030ReserveBelongLavel
     */
    public ArrayList<RsvSisDataModel> getNtp030ReserveBelongLavel() {
        return ntp030ReserveBelongLavel__;
    }

    /**
     * <p>ntp030ReserveBelongLavel をセットします。
     * @param ntp030ReserveBelongLavel ntp030ReserveBelongLavel
     */
    public void setNtp030ReserveBelongLavel(
            ArrayList<RsvSisDataModel> ntp030ReserveBelongLavel) {
        ntp030ReserveBelongLavel__ = ntp030ReserveBelongLavel;
    }

    /**
     * <p>ntp030ReserveGroupLavel を取得します。
     * @return ntp030ReserveGroupLavel
     */
    public List<LabelValueBean> getNtp030ReserveGroupLavel() {
        return ntp030ReserveGroupLavel__;
    }

    /**
     * <p>ntp030ReserveGroupLavel をセットします。
     * @param ntp030ReserveGroupLavel ntp030ReserveGroupLavel
     */
    public void setNtp030ReserveGroupLavel(
            List<LabelValueBean> ntp030ReserveGroupLavel) {
        ntp030ReserveGroupLavel__ = ntp030ReserveGroupLavel;
    }

    /**
     * <p>ntp030ReserveGroupSid を取得します。
     * @return ntp030ReserveGroupSid
     */
    public String getNtp030ReserveGroupSid() {
        return ntp030ReserveGroupSid__;
    }

    /**
     * <p>ntp030ReserveGroupSid をセットします。
     * @param ntp030ReserveGroupSid ntp030ReserveGroupSid
     */
    public void setNtp030ReserveGroupSid(String ntp030ReserveGroupSid) {
        ntp030ReserveGroupSid__ = ntp030ReserveGroupSid;
    }

    /**
     * <p>ntp030ReserveSelectLavel を取得します。
     * @return ntp030ReserveSelectLavel
     */
    public ArrayList<RsvSisDataModel> getNtp030ReserveSelectLavel() {
        return ntp030ReserveSelectLavel__;
    }

    /**
     * <p>ntp030ReserveSelectLavel をセットします。
     * @param ntp030ReserveSelectLavel ntp030ReserveSelectLavel
     */
    public void setNtp030ReserveSelectLavel(
            ArrayList<RsvSisDataModel> ntp030ReserveSelectLavel) {
        ntp030ReserveSelectLavel__ = ntp030ReserveSelectLavel;
    }

    /**
     * <p>svReserveUsers を取得します。
     * @return svReserveUsers
     */
    public String[] getSvReserveUsers() {
        return svReserveUsers__;
    }

    /**
     * <p>svReserveUsers をセットします。
     * @param svReserveUsers svReserveUsers
     */
    public void setSvReserveUsers(String[] svReserveUsers) {
        svReserveUsers__ = svReserveUsers;
    }

    /**
     * <p>ntp030AddedUsrLavel を取得します。
     * @return ntp030AddedUsrLavel
     */
    public ArrayList<CmnUsrmInfModel> getNtp030AddedUsrLavel() {
        return ntp030AddedUsrLavel__;
    }


    /**
     * <p>ntp030AddedUsrLavel をセットします。
     * @param ntp030AddedUsrLavel ntp030AddedUsrLavel
     */
    public void setNtp030AddedUsrLavel(
            ArrayList<CmnUsrmInfModel> ntp030AddedUsrLavel) {
        ntp030AddedUsrLavel__ = ntp030AddedUsrLavel;
    }


    /**
     * <p>ntp030Edit を取得します。
     * @return ntp030Edit
     */
    public String getNtp030Edit() {
        return ntp030Edit__;
    }


    /**
     * <p>ntp030Edit をセットします。
     * @param ntp030Edit ntp030Edit
     */
    public void setNtp030Edit(String ntp030Edit) {
        ntp030Edit__ = ntp030Edit;
    }

    /**
     * <p>ntp030BatchRef を取得します。
     * @return ntp030BatchRef
     */
    public String getNtp030BatchRef() {
        return ntp030BatchRef__;
    }


    /**
     * <p>ntp030BatchRef をセットします。
     * @param ntp030BatchRef ntp030BatchRef
     */
    public void setNtp030BatchRef(String ntp030BatchRef) {
        ntp030BatchRef__ = ntp030BatchRef;
    }


    /**
     * @return sv_users を戻します。
     */
    public String[] getSv_users() {
        return sv_users__;
    }


    /**
     * @return ntp030AddUsrJkbn を戻します。
     */
    public String getNtp030AddUsrJkbn() {
        return ntp030AddUsrJkbn__;
    }


    /**
     * @param ntp030AddUsrJkbn 設定する ntp030AddUsrJkbn。
     */
    public void setNtp030AddUsrJkbn(String ntp030AddUsrJkbn) {
        ntp030AddUsrJkbn__ = ntp030AddUsrJkbn;
    }


    /**
     * @param svUsers 設定する sv_users。
     */
    public void setSv_users(String[] svUsers) {
        sv_users__ = svUsers;
    }


    /**
     * @return users_r を戻します。
     */
    public String[] getUsers_r() {
        return users_r__;
    }


    /**
     * @param usersr 設定する users_r。
     */
    public void setUsers_r(String[] usersr) {
        users_r__ = usersr;
    }


    /**
     * @return ntp030FrHour を戻します。
     */
    public String getNtp030FrHour() {
        return ntp030FrHour__;
    }


    /**
     * @param ntp030FrHour 設定する ntp030FrHour。
     */
    public void setNtp030FrHour(String ntp030FrHour) {
        ntp030FrHour__ = ntp030FrHour;
    }


    /**
     * @return ntp030FrMin を戻します。
     */
    public String getNtp030FrMin() {
        return ntp030FrMin__;
    }


    /**
     * @param ntp030FrMin 設定する ntp030FrMin。
     */
    public void setNtp030FrMin(String ntp030FrMin) {
        ntp030FrMin__ = ntp030FrMin;
    }


    /**
     * @return ntp030ToHour を戻します。
     */
    public String getNtp030ToHour() {
        return ntp030ToHour__;
    }


    /**
     * @param ntp030ToHour 設定する ntp030ToHour。
     */
    public void setNtp030ToHour(String ntp030ToHour) {
        ntp030ToHour__ = ntp030ToHour;
    }


    /**
     * @return ntp030ToMin を戻します。
     */
    public String getNtp030ToMin() {
        return ntp030ToMin__;
    }


    /**
     * @param ntp030ToMin 設定する ntp030ToMin。
     */
    public void setNtp030ToMin(String ntp030ToMin) {
        ntp030ToMin__ = ntp030ToMin;
    }

    /**
     * @return ntp030GroupSid を戻します。
     */
    public String getNtp030GroupSid() {
        return ntp030GroupSid__;
    }


    /**
     * @param ntp030GroupSid 設定する ntp030GroupSid。
     */
    public void setNtp030GroupSid(String ntp030GroupSid) {
        ntp030GroupSid__ = ntp030GroupSid;
    }


    /**
     * @return ntp030AddUsrName を戻します。
     */
    public String getNtp030AddUsrName() {
        return ntp030AddUsrName__;
    }


    /**
     * @param ntp030AddUsrName 設定する ntp030AddUsrName。
     */
    public void setNtp030AddUsrName(String ntp030AddUsrName) {
        ntp030AddUsrName__ = ntp030AddUsrName;
    }


    /**
     * @return ntp030BelongLavel を戻します。
     */
    public ArrayList < CmnUsrmInfModel > getNtp030BelongLavel() {
        return ntp030BelongLavel__;
    }


    /**
     * @param ntp030BelongLavel 設定する ntp030BelongLavel。
     */
    public void setNtp030BelongLavel(ArrayList < CmnUsrmInfModel > ntp030BelongLavel) {
        ntp030BelongLavel__ = ntp030BelongLavel;
    }


    /**
     * @return ntp030Bgcolor を戻します。
     */
    public String getNtp030Bgcolor() {
        return ntp030Bgcolor__;
    }


    /**
     * @param ntp030Bgcolor 設定する ntp030Bgcolor。
     */
    public void setNtp030Bgcolor(String ntp030Bgcolor) {
        ntp030Bgcolor__ = ntp030Bgcolor;
    }


    /**
     * @return ntp030Biko を戻します。
     */
    public String getNtp030Biko() {
        return ntp030Biko__;
    }


    /**
     * @param ntp030Biko 設定する ntp030Biko。
     */
    public void setNtp030Biko(String ntp030Biko) {
        ntp030Biko__ = ntp030Biko;
    }


    /**
     * @return ntp030DayLavel を戻します。
     */
    public ArrayList<LabelValueBean> getNtp030DayLavel() {
        return ntp030DayLavel__;
    }


    /**
     * @param ntp030DayLavel 設定する ntp030DayLavel。
     */
    public void setNtp030DayLavel(ArrayList < LabelValueBean > ntp030DayLavel) {
        ntp030DayLavel__ = ntp030DayLavel;
    }


    /**
     * @return ntp030FrDay を戻します。
     */
    public String getNtp030FrDay() {
        return ntp030FrDay__;
    }


    /**
     * @param ntp030FrDay 設定する ntp030FrDay。
     */
    public void setNtp030FrDay(String ntp030FrDay) {
        ntp030FrDay__ = ntp030FrDay;
    }


    /**
     * @return ntp030FrMonth を戻します。
     */
    public String getNtp030FrMonth() {
        return ntp030FrMonth__;
    }


    /**
     * @param ntp030FrMonth 設定する ntp030FrMonth。
     */
    public void setNtp030FrMonth(String ntp030FrMonth) {
        ntp030FrMonth__ = ntp030FrMonth;
    }


    /**
     * @return ntp030FrYear を戻します。
     */
    public String getNtp030FrYear() {
        return ntp030FrYear__;
    }


    /**
     * @param ntp030FrYear 設定する ntp030FrYear。
     */
    public void setNtp030FrYear(String ntp030FrYear) {
        ntp030FrYear__ = ntp030FrYear;
    }


    /**
     * @return ntp030GroupLavel を戻します。
     */
    public List<NtpLabelValueModel> getNtp030GroupLavel() {
        return ntp030GroupLavel__;
    }


    /**
     * @param list 設定する ntp030GroupLavel。
     */
    public void setNtp030GroupLavel(List<NtpLabelValueModel> list) {
        ntp030GroupLavel__ = list;
    }


    /**
     * @return ntp030MonthLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp030MonthLavel() {
        return ntp030MonthLavel__;
    }


    /**
     * @param ntp030MonthLavel 設定する ntp030MonthLavel。
     */
    public void setNtp030MonthLavel(ArrayList < LabelValueBean > ntp030MonthLavel) {
        ntp030MonthLavel__ = ntp030MonthLavel;
    }


    /**
     * @return ntp030Public を戻します。
     */
    public String getNtp030Public() {
        return ntp030Public__;
    }


    /**
     * @param ntp030Public 設定する ntp030Public。
     */
    public void setNtp030Public(String ntp030Public) {
        ntp030Public__ = ntp030Public;
    }


    /**
     * @return ntp030SelectUsrLavel を戻します。
     */
    public ArrayList < CmnUsrmInfModel > getNtp030SelectUsrLavel() {
        return ntp030SelectUsrLavel__;
    }


    /**
     * @param ntp030SelectUsrLavel 設定する ntp030SelectUsrLavel。
     */
    public void setNtp030SelectUsrLavel(ArrayList < CmnUsrmInfModel > ntp030SelectUsrLavel) {
        ntp030SelectUsrLavel__ = ntp030SelectUsrLavel;
    }


    /**
     * @return ntp030Title を戻します。
     */
    public String getNtp030Title() {
        return ntp030Title__;
    }


    /**
     * @param ntp030Title 設定する ntp030Title。
     */
    public void setNtp030Title(String ntp030Title) {
        ntp030Title__ = ntp030Title;
    }


    /**
     * @return ntp030ToDay を戻します。
     */
    public String getNtp030ToDay() {
        return ntp030ToDay__;
    }


    /**
     * @param ntp030ToDay 設定する ntp030ToDay。
     */
    public void setNtp030ToDay(String ntp030ToDay) {
        ntp030ToDay__ = ntp030ToDay;
    }


    /**
     * @return ntp030ToMonth を戻します。
     */
    public String getNtp030ToMonth() {
        return ntp030ToMonth__;
    }


    /**
     * @param ntp030ToMonth 設定する ntp030ToMonth。
     */
    public void setNtp030ToMonth(String ntp030ToMonth) {
        ntp030ToMonth__ = ntp030ToMonth;
    }


    /**
     * @return ntp030ToYear を戻します。
     */
    public String getNtp030ToYear() {
        return ntp030ToYear__;
    }


    /**
     * @param ntp030ToYear 設定する ntp030ToYear。
     */
    public void setNtp030ToYear(String ntp030ToYear) {
        ntp030ToYear__ = ntp030ToYear;
    }


    /**
     * @return ntp030UsrName を戻します。
     */
    public String getNtp030UsrName() {
        return ntp030UsrName__;
    }


    /**
     * @param ntp030UsrName 設定する ntp030UsrName。
     */
    public void setNtp030UsrName(String ntp030UsrName) {
        ntp030UsrName__ = ntp030UsrName;
    }


    /**
     * @return ntp030Value を戻します。
     */
    public String getNtp030Value() {
        return ntp030Value__;
    }


    /**
     * @param ntp030Value 設定する ntp030Value。
     */
    public void setNtp030Value(String ntp030Value) {
        ntp030Value__ = ntp030Value;
    }


    /**
     * @return ntp030YearLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp030YearLavel() {
        return ntp030YearLavel__;
    }


    /**
     * @param ntp030YearLavel 設定する ntp030YearLavel。
     */
    public void setNtp030YearLavel(ArrayList < LabelValueBean > ntp030YearLavel) {
        ntp030YearLavel__ = ntp030YearLavel;
    }


    /**
     * @return ntp030HourLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp030HourLavel() {
        return ntp030HourLavel__;
    }


    /**
     * @param ntp030HourLavel 設定する ntp030HourLavel。
     */
    public void setNtp030HourLavel(ArrayList < LabelValueBean > ntp030HourLavel) {
        ntp030HourLavel__ = ntp030HourLavel;
    }


    /**
     * @return ntp030MinuteLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp030MinuteLavel() {
        return ntp030MinuteLavel__;
    }


    /**
     * @param ntp030MinuteLavel 設定する ntp030MinuteLavel。
     */
    public void setNtp030MinuteLavel(ArrayList < LabelValueBean > ntp030MinuteLavel) {
        ntp030MinuteLavel__ = ntp030MinuteLavel;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, ActionErrors> validateCheck(
            ActionMapping map,
            HttpServletRequest req,
            Connection con,
            int sessionUsrSid) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        //行番号とエラー内容を格納するMAP
        Map<Integer, ActionErrors> errorsMap = new TreeMap<Integer, ActionErrors>();

        for (Ntp030Param ntpData : ntp030nippouDataList__) {

            ActionErrors errors = new ActionErrors();

            String number         = String.valueOf(ntpData.getRowId());
            String ntpYear        = String.valueOf(ntpData.getNtpYear());
            String ntpMonth       = String.valueOf(ntpData.getNtpMonth());
            String ntpDay         = String.valueOf(ntpData.getNtpDay());
            String frHour         = String.valueOf(ntpData.getFrHour());
            String frMin          = String.valueOf(ntpData.getFrMin());
            String toHour         = String.valueOf(ntpData.getToHour());
            String toMin          = String.valueOf(ntpData.getToMin());
            String title          = String.valueOf(ntpData.getTitle());
            String naiyou         = String.valueOf(ntpData.getValueStr());

            //開始年月日チェックフラグ(true=入力OK、false=NG)
            boolean fromOk = false;

            int iSYear = -1;
            if (!StringUtil.isNullZeroStringSpace(ntpYear)) {
                iSYear = Integer.parseInt(ntpYear);
            }
            int iSMonth = Integer.parseInt(ntpMonth);
            int iSDay = Integer.parseInt(ntpDay);

            UDate frDate = new UDate();
            frDate.setDate(iSYear, iSMonth, iSDay);
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (frDate.getYear() != iSYear
            || frDate.getMonth() != iSMonth
            || frDate.getIntDay() != iSDay) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage(req, "schedule.sch100.10"));
                errors.add("error.input.notfound.date", msg);
                log__.debug("error:1");
            } else {
                fromOk = true;
            }

            //終了年月日チェックフラグ(true=入力OK、false=NG)
            boolean toOk = false;

            int iEYear = -1;
            if (!StringUtil.isNullZeroStringSpace(ntpYear)) {
                iEYear = Integer.parseInt(ntpYear);
            }
            int iEMonth = Integer.parseInt(ntpMonth);
            int iEDay = Integer.parseInt(ntpDay);

            UDate toDate = new UDate();
            toDate.setDate(iEYear, iEMonth, iEDay);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (toDate.getYear() != iEYear
            || toDate.getMonth() != iEMonth
            || toDate.getIntDay() != iEDay) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage(req, "schedule.sch100.15"));
                errors.add("error.input.notfound.date", msg);
                log__.debug("error:2");
            } else {
                toOk = true;
            }

            if (fromOk && toOk
                       && ntp030TimeKbn__.equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {

                if (frHour.equals("-1")
                 || frMin.equals("-1")
                 || toHour.equals("-1")
                 || toMin.equals("-1")
                        ) {
                            //時分
                            String textHourMinute = gsMsg.getMessage(req, "schedule.src.36");
                            msg = new ActionMessage("error.input.required.text", textHourMinute);
                            errors.add("" + "error.input.required.text", msg);
                            fromOk = false;

                        }
            }

            //個別チェックOKの場合
            if (fromOk && toOk) {

                if (ntp030TimeKbn__.equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
                    if (frHour.equals("-1") && frMin.equals("-1")) {
                        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
                        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
                    } else {
                        frDate.setHour(Integer.parseInt(frHour));
                        frDate.setMinute(Integer.parseInt(frMin));
                    }
                    if (toHour.equals("-1") && toMin.equals("-1")) {
                        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
                        toDate.setMinute(GSConstSchedule.DAY_SYSMAX_MINUTES);
                    } else {
                        toDate.setHour(Integer.parseInt(toHour));
                        toDate.setMinute(Integer.parseInt(toMin));
                    }
                } else {
                    frDate.setZeroHhMmSs();
                    toDate.setMaxHhMmSs();
                }

                //from～to大小チェック
                if (frDate.compare(frDate, toDate) != UDate.LARGE) {
                    //開始 < 終了
                    String textStartLessEnd = gsMsg.getMessage(req, "cmn.start.lessthan.end");
                    //開始・終了
                    String textStartEnd = gsMsg.getMessage(req, "cmn.start.end");
                    msg = new ActionMessage("error.input.comp.text",
                            textStartEnd, textStartLessEnd);
                    errors.add("" + "error.input.comp.text", msg);
                    log__.debug("error:5");
                }

            }

            //タイトルのチェック
            if (__checkNoInput(errors, title, "ntp030Title",
                    gsMsg.getMessage(req, "cmn.title"))) {
                if (__checkRange(
                        errors,
                        title,
                        "ntp030Title",
                        gsMsg.getMessage(req, "cmn.title"),
                        GSConstSchedule.MAX_LENGTH_TITLE)) {
                    //先頭スペースチェック
                    if (ValidateUtil.isSpaceStart(title)) {
                        //タイトル
                        String textTitle = gsMsg.getMessage(req, "cmn.title");
                        msg = new ActionMessage("error.input.spase.start",
                                textTitle);
                        StrutsUtil.addMessage(errors, msg, "ntp030Title");
                    } else {
                        __checkJisString(
                                errors,
                                title,
                                "ntp030Title",
                                gsMsg.getMessage(req, "cmn.title"));
                    }
                }

            }
            boolean valueError = false;
            //内容のチェック
            if (__checkRangeTextarea(
                    errors,
                    naiyou,
                    "ntp030Value",
                    gsMsg.getMessage(req, "cmn.content"),
                    GSConstSchedule.MAX_LENGTH_VALUE)) {
                if (!StringUtil.isNullZeroString(naiyou)) {
                    //スペースのみチェック
                    if (!valueError && ValidateUtil.isSpaceOrKaigyou(naiyou)) {
                        msg = new ActionMessage("error.input.spase.cl.only",
                                gsMsg.getMessage(req, "cmn.content"));
                        StrutsUtil.addMessage(errors, msg, "ntp030Value");
                        valueError = true;
                    }

                    if (!valueError) {
                        //JIS
                        __checkJisString(
                                errors,
                                naiyou,
                                "ntp030Value",
                                gsMsg.getMessage(req, "cmn.content"));
                    }
                }
            }

            if (errors.size() > 0) {
                errorsMap.put(Integer.valueOf(number), errors);
            }

        }
        return errorsMap;
    }

    /**
     * <br>[機  能] 指定された項目の未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkNoInput(ActionErrors errors,
                                    String value,
                                    String element,
                                    String elementName) {
        boolean result = true;
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text", elementName);
            errors.add(element + "error.input.required.text", msg);
            result = false;
            log__.debug("error:6");
        } else {
            //スペースのみの入力かチェック
            if (ValidateUtil.isSpace(value)) {
                msg = new ActionMessage("error.input.spase.only", elementName);
                errors.add(element + "error.input.spase.only", msg);
                result = false;
            }

        }

        return result;
    }

    /**
     * <br>[機  能] 同時登録日報の編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSchPowerCheck(
            ActionMapping map,
            HttpServletRequest req,
            ActionErrors errors,
            Connection con) throws SQLException {
        return errors;
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
            log__.debug("error:7");
        }
        return result;
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
    private boolean __checkRangeTextarea(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.textarea", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.textarea", msg);
            result = false;
            log__.debug("error:7");
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
     * <br>[機  能] 不要なパラメータを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void clearParam() {
        ntp030delCompanyId__ = "";
        ntp030delCompanyBaseId__ = "";
        addressPluginKbn__ = 0;
        searchPluginKbn__ = 0;
        ntp030FrYear__ = "";
        ntp030FrMonth__ = "";
        ntp030FrDay__ = "";
    }

    /**
     * <p>ntp030AddDate を取得します。
     * @return ntp030AddDate
     */
    public String getNtp030AddDate() {
        return ntp030AddDate__;
    }

    /**
     * <p>ntp030AddDate をセットします。
     * @param ntp030AddDate ntp030AddDate
     */
    public void setNtp030AddDate(String ntp030AddDate) {
        ntp030AddDate__ = ntp030AddDate;
    }

    /**
     * <p>ntp030InitFlg を取得します。
     * @return ntp030InitFlg
     */
    public String getNtp030InitFlg() {
        return ntp030InitFlg__;
    }

    /**
     * <p>ntp030InitFlg をセットします。
     * @param ntp030InitFlg ntp030InitFlg
     */
    public void setNtp030InitFlg(String ntp030InitFlg) {
        ntp030InitFlg__ = ntp030InitFlg;
    }

    /**
     * <p>ntp030AddressId を取得します。
     * @return ntp030AddressId
     */
    public String[] getNtp030AddressId() {
        return ntp030AddressId__;
    }

    /**
     * <p>ntp030AddressId をセットします。
     * @param ntp030AddressId ntp030AddressId
     */
    public void setNtp030AddressId(String[] ntp030AddressId) {
        ntp030AddressId__ = ntp030AddressId;
    }

    /**
     * <p>ntp030CompanyBaseSid を取得します。
     * @return ntp030CompanyBaseSid
     */
    public String[] getNtp030CompanyBaseSid() {
        return ntp030CompanyBaseSid__;
    }

    /**
     * <p>ntp030CompanyBaseSid をセットします。
     * @param ntp030CompanyBaseSid ntp030CompanyBaseSid
     */
    public void setNtp030CompanyBaseSid(String[] ntp030CompanyBaseSid) {
        ntp030CompanyBaseSid__ = ntp030CompanyBaseSid;
    }

    /**
     * <p>ntp030CompanyList を取得します。
     * @return ntp030CompanyList
     */
    public List<Ntp030CompanyModel> getNtp030CompanyList() {
        return ntp030CompanyList__;
    }

    /**
     * <p>ntp030CompanyList をセットします。
     * @param ntp030CompanyList ntp030CompanyList
     */
    public void setNtp030CompanyList(List<Ntp030CompanyModel> ntp030CompanyList) {
        ntp030CompanyList__ = ntp030CompanyList;
    }

    /**
     * <p>ntp030CompanySid を取得します。
     * @return ntp030CompanySid
     */
    public String[] getNtp030CompanySid() {
        return ntp030CompanySid__;
    }

    /**
     * <p>ntp030CompanySid をセットします。
     * @param ntp030CompanySid ntp030CompanySid
     */
    public void setNtp030CompanySid(String[] ntp030CompanySid) {
        ntp030CompanySid__ = ntp030CompanySid;
    }

    /**
     * <p>ntp030contact を取得します。
     * @return ntp030contact
     */
    public int getNtp030contact() {
        return ntp030contact__;
    }

    /**
     * <p>ntp030contact をセットします。
     * @param ntp030contact ntp030contact
     */
    public void setNtp030contact(int ntp030contact) {
        ntp030contact__ = ntp030contact;
    }

    /**
     * <p>ntp030delCompanyBaseId を取得します。
     * @return ntp030delCompanyBaseId
     */
    public String getNtp030delCompanyBaseId() {
        return ntp030delCompanyBaseId__;
    }

    /**
     * <p>ntp030delCompanyBaseId をセットします。
     * @param ntp030delCompanyBaseId ntp030delCompanyBaseId
     */
    public void setNtp030delCompanyBaseId(String ntp030delCompanyBaseId) {
        ntp030delCompanyBaseId__ = ntp030delCompanyBaseId;
    }

    /**
     * <p>ntp030delCompanyId を取得します。
     * @return ntp030delCompanyId
     */
    public String getNtp030delCompanyId() {
        return ntp030delCompanyId__;
    }

    /**
     * <p>ntp030delCompanyId をセットします。
     * @param ntp030delCompanyId ntp030delCompanyId
     */
    public void setNtp030delCompanyId(String ntp030delCompanyId) {
        ntp030delCompanyId__ = ntp030delCompanyId;
    }
    /**
     * @return ntp030ScrollFlg
     */
    public String getNtp030ScrollFlg() {
        return ntp030ScrollFlg__;
    }

    /**
     * @param ntp030ScrollFlg セットする ntp030ScrollFlg
     */
    public void setNtp030ScrollFlg(String ntp030ScrollFlg) {
        ntp030ScrollFlg__ = ntp030ScrollFlg;
    }

    /**
     * <p>ntp030ExTextDspFlg を取得します。
     * @return ntp030ExTextDspFlg
     */
    public boolean isNtp030ExTextDspFlg() {
        return ntp030ExTextDspFlg__;
    }

    /**
     * <p>ntp030ExTextDspFlg をセットします。
     * @param ntp030ExTextDspFlg ntp030ExTextDspFlg
     */
    public void setNtp030ExTextDspFlg(boolean ntp030ExTextDspFlg) {
        ntp030ExTextDspFlg__ = ntp030ExTextDspFlg;
    }

    /**
     * <p>ntp030CopyFlg を取得します。
     * @return ntp030CopyFlg
     */
    public String getNtp030CopyFlg() {
        return ntp030CopyFlg__;
    }

    /**
     * <p>ntp030CopyFlg をセットします。
     * @param ntp030CopyFlg ntp030CopyFlg
     */
    public void setNtp030CopyFlg(String ntp030CopyFlg) {
        ntp030CopyFlg__ = ntp030CopyFlg;
    }

    /**
     * <p>ntp030CantReadRsvCount を取得します。
     * @return ntp030CantReadRsvCount
     */
    public int getNtp030CantReadRsvCount() {
        return ntp030CantReadRsvCount__;
    }

    /**
     * <p>ntp030CantReadRsvCount をセットします。
     * @param ntp030CantReadRsvCount ntp030CantReadRsvCount
     */
    public void setNtp030CantReadRsvCount(int ntp030CantReadRsvCount) {
        ntp030CantReadRsvCount__ = ntp030CantReadRsvCount;
    }

    /**
     * <p>log__ を取得します。
     * @return log__
     */
    public static Log getLog__() {
        return log__;
    }

    /**
     * <p>log__ をセットします。
     * @param log log__
     */
    public static void setLog__(Log log) {
        log__ = log;
    }

    /**
     * <p>nippouData を取得します。
     * @return nippouData
     */
    public String getNippouData() {
        return nippouData__;
    }

    /**
     * <p>nippouData をセットします。
     * @param nippouData nippouData
     */
    public void setNippouData(String nippouData) {
        nippouData__ = nippouData;
    }

    /**
     * <p>nippouDataList を取得します。
     * @return nippouDataList
     */
    public List<Ntp030Param> getNtp030nippouDataList() {
        return ntp030nippouDataList__;
    }

    /**
     * <p>nippouDataList をセットします。
     * @param nippouDataList nippouDataList
     */
    public void setNtp030nippouDataList(List<Ntp030Param> nippouDataList) {
        ntp030nippouDataList__ = nippouDataList;
    }

    /**
     * <p>ntp030KtbunruiLavel を取得します。
     * @return ntp030KtbunruiLavel
     */
    public List<LabelValueBean> getNtp030KtbunruiLavel() {
        return ntp030KtbunruiLavel__;
    }

    /**
     * <p>ntp030KtbunruiLavel をセットします。
     * @param ntp030KtbunruiLavel ntp030KtbunruiLavel
     */
    public void setNtp030KtbunruiLavel(List<LabelValueBean> ntp030KtbunruiLavel) {
        ntp030KtbunruiLavel__ = ntp030KtbunruiLavel;
    }

    /**
     * <p>ntp030KthouhouLavel を取得します。
     * @return ntp030KthouhouLavel
     */
    public List<LabelValueBean> getNtp030KthouhouLavel() {
        return ntp030KthouhouLavel__;
    }

    /**
     * <p>ntp030KthouhouLavel をセットします。
     * @param ntp030KthouhouLavel ntp030KthouhouLavel
     */
    public void setNtp030KthouhouLavel(List<LabelValueBean> ntp030KthouhouLavel) {
        ntp030KthouhouLavel__ = ntp030KthouhouLavel;
    }

    /**
     * <p>ntp030Ktbunrui を取得します。
     * @return ntp030Ktbunrui
     */
    public String getNtp030Ktbunrui() {
        return ntp030Ktbunrui__;
    }

    /**
     * <p>ntp030Ktbunrui をセットします。
     * @param ntp030Ktbunrui ntp030Ktbunrui
     */
    public void setNtp030Ktbunrui(String ntp030Ktbunrui) {
        ntp030Ktbunrui__ = ntp030Ktbunrui;
    }

    /**
     * <p>ntp030Kthouhou を取得します。
     * @return ntp030Kthouhou
     */
    public String getNtp030Kthouhou() {
        return ntp030Kthouhou__;
    }

    /**
     * <p>ntp030Kthouhou をセットします。
     * @param ntp030Kthouhou ntp030Kthouhou
     */
    public void setNtp030Kthouhou(String ntp030Kthouhou) {
        ntp030Kthouhou__ = ntp030Kthouhou;
    }

    /**
     * <p>ntp030Mikomido を取得します。
     * @return ntp030Mikomido
     */
    public String getNtp030Mikomido() {
        return ntp030Mikomido__;
    }

    /**
     * <p>ntp030Mikomido をセットします。
     * @param ntp030Mikomido ntp030Mikomido
     */
    public void setNtp030Mikomido(String ntp030Mikomido) {
        ntp030Mikomido__ = ntp030Mikomido;
    }

    /**
     * <p>ntp030FileList をセットします。
     * @param ntp030FileList ntp030FileList
     */
    public void setNtp030FileList(ArrayList<CmnBinfModel> ntp030FileList) {
        ntp030FileList__ = ntp030FileList;
    }

    /**
     * <p>ntp030DataModelList を取得します。
     * @return ntp030DataModelList
     */
    public List<Ntp030DataModel> getNtp030DataModelList() {
        return ntp030DataModelList__;
    }

    /**
     * <p>ntp030DataModelList をセットします。
     * @param ntp030DataModelList ntp030DataModelList
     */
    public void setNtp030DataModelList(List<Ntp030DataModel> ntp030DataModelList) {
        ntp030DataModelList__ = ntp030DataModelList;
    }

    /**
     * <p>ntp030pluginId を取得します。
     * @return ntp030pluginId
     */
    public String getNtp030pluginId() {
        return ntp030pluginId__;
    }

    /**
     * <p>ntp030pluginId をセットします。
     * @param ntp030pluginId ntp030pluginId
     */
    public void setNtp030pluginId(String ntp030pluginId) {
        ntp030pluginId__ = ntp030pluginId;
    }

    /**
     * <p>ntp030BinSid を取得します。
     * @return ntp030BinSid
     */
    public String getNtp030BinSid() {
        return ntp030BinSid__;
    }

    /**
     * <p>ntp030BinSid をセットします。
     * @param ntp030BinSid ntp030BinSid
     */
    public void setNtp030BinSid(String ntp030BinSid) {
        ntp030BinSid__ = ntp030BinSid;
    }

    /**
     * <p>ntp030FileList を取得します。
     * @return ntp030FileList
     */
    public ArrayList<CmnBinfModel> getNtp030FileList() {
        return ntp030FileList__;
    }

    /**
     * <p>ntp030UsrInfMdl を取得します。
     * @return ntp030UsrInfMdl
     */
    public CmnUsrmInfModel getNtp030UsrInfMdl() {
        return ntp030UsrInfMdl__;
    }

    /**
     * <p>ntp030UsrInfMdl をセットします。
     * @param ntp030UsrInfMdl ntp030UsrInfMdl
     */
    public void setNtp030UsrInfMdl(CmnUsrmInfModel ntp030UsrInfMdl) {
        ntp030UsrInfMdl__ = ntp030UsrInfMdl;
    }

    /**
     * <p>ntp030EditKbn を取得します。
     * @return ntp030EditKbn
     */
    public int getNtp030EditKbn() {
        return ntp030EditKbn__;
    }

    /**
     * <p>ntp030EditKbn をセットします。
     * @param ntp030EditKbn ntp030EditKbn
     */
    public void setNtp030EditKbn(int ntp030EditKbn) {
        ntp030EditKbn__ = ntp030EditKbn;
    }

    /**
     * <p>ntp030AddressUseOk を取得します。
     * @return ntp030AddressUseOk
     */
    public int getNtp030AddressUseOk() {
        return ntp030AddressUseOk__;
    }

    /**
     * <p>ntp030AddressUseOk をセットします。
     * @param ntp030AddressUseOk ntp030AddressUseOk
     */
    public void setNtp030AddressUseOk(int ntp030AddressUseOk) {
        ntp030AddressUseOk__ = ntp030AddressUseOk;
    }

    /**
     * <p>ntp030SmailUseOk を取得します。
     * @return ntp030SmailUseOk
     */
    public int getNtp030SmailUseOk() {
        return ntp030SmailUseOk__;
    }

    /**
     * <p>ntp030SmailUseOk をセットします。
     * @param ntp030SmailUseOk ntp030SmailUseOk
     */
    public void setNtp030SmailUseOk(int ntp030SmailUseOk) {
        ntp030SmailUseOk__ = ntp030SmailUseOk;
    }

    /**
     * <p>ntp030BgcolorInit を取得します。
     * @return ntp030BgcolorInit
     */
    public String getNtp030BgcolorInit() {
        return ntp030BgcolorInit__;
    }

    /**
     * <p>ntp030BgcolorInit をセットします。
     * @param ntp030BgcolorInit ntp030BgcolorInit
     */
    public void setNtp030BgcolorInit(String ntp030BgcolorInit) {
        ntp030BgcolorInit__ = ntp030BgcolorInit;
    }

    /**
     * <p>ntp030DspMoveFlg を取得します。
     * @return ntp030DspMoveFlg
     */
    public int getNtp030DspMoveFlg() {
        return ntp030DspMoveFlg__;
    }

    /**
     * <p>ntp030DspMoveFlg をセットします。
     * @param ntp030DspMoveFlg ntp030DspMoveFlg
     */
    public void setNtp030DspMoveFlg(int ntp030DspMoveFlg) {
        ntp030DspMoveFlg__ = ntp030DspMoveFlg;
    }

    /**
     * <p>ntp030UsrLabelList を取得します。
     * @return ntp030UsrLabelList
     */
    public List<Ntp030UsrLabelModel> getNtp030UsrLabelList() {
        return ntp030UsrLabelList__;
    }

    /**
     * <p>ntp030UsrLabelList をセットします。
     * @param ntp030UsrLabelList ntp030UsrLabelList
     */
    public void setNtp030UsrLabelList(List<Ntp030UsrLabelModel> ntp030UsrLabelList) {
        ntp030UsrLabelList__ = ntp030UsrLabelList;
    }

    /**
     * <p>ntp030SelectUsrSid を取得します。
     * @return ntp030SelectUsrSid
     */
    public String getNtp030SelectUsrSid() {
        return ntp030SelectUsrSid__;
    }

    /**
     * <p>ntp030SelectUsrSid をセットします。
     * @param ntp030SelectUsrSid ntp030SelectUsrSid
     */
    public void setNtp030SelectUsrSid(String ntp030SelectUsrSid) {
        ntp030SelectUsrSid__ = ntp030SelectUsrSid;
    }

    /**
     * <p>ntp030Offset を取得します。
     * @return ntp030Offset
     */
    public int getNtp030Offset() {
        return ntp030Offset__;
    }

    /**
     * <p>ntp030Offset をセットします。
     * @param ntp030Offset ntp030Offset
     */
    public void setNtp030Offset(int ntp030Offset) {
        ntp030Offset__ = ntp030Offset;
    }

    /**
     * <p>ntp030LabelDate を取得します。
     * @return ntp030LabelDate
     */
    public String getNtp030LabelDate() {
        return ntp030LabelDate__;
    }

    /**
     * <p>ntp030LabelDate をセットします。
     * @param ntp030LabelDate ntp030LabelDate
     */
    public void setNtp030LabelDate(String ntp030LabelDate) {
        ntp030LabelDate__ = ntp030LabelDate;
    }

    /**
     * <p>ntp030SessionSid を取得します。
     * @return ntp030SessionSid
     */
    public String getNtp030SessionSid() {
        return ntp030SessionSid__;
    }

    /**
     * <p>ntp030SessionSid をセットします。
     * @param ntp030SessionSid ntp030SessionSid
     */
    public void setNtp030SessionSid(String ntp030SessionSid) {
        ntp030SessionSid__ = ntp030SessionSid;
    }

    /**
     * <p>ntp030Sort を取得します。
     * @return ntp030Sort
     */
    public int getNtp030Sort() {
        return ntp030Sort__;
    }

    /**
     * <p>ntp030Sort をセットします。
     * @param ntp030Sort ntp030Sort
     */
    public void setNtp030Sort(int ntp030Sort) {
        ntp030Sort__ = ntp030Sort;
    }

    /**
     * <p>ntp030SortLabel を取得します。
     * @return ntp030SortLabel
     */
    public ArrayList<LabelValueBean> getNtp030SortLabel() {
        return ntp030SortLabel__;
    }

    /**
     * <p>ntp030SortLabel をセットします。
     * @param ntp030SortLabel ntp030SortLabel
     */
    public void setNtp030SortLabel(ArrayList<LabelValueBean> ntp030SortLabel) {
        ntp030SortLabel__ = ntp030SortLabel;
    }

    /**
     * <p>ntp030NxtActYear を取得します。
     * @return ntp030NxtActYear
     */
    public String getNtp030NxtActYear() {
        return ntp030NxtActYear__;
    }

    /**
     * <p>ntp030NxtActYear をセットします。
     * @param ntp030NxtActYear ntp030NxtActYear
     */
    public void setNtp030NxtActYear(String ntp030NxtActYear) {
        ntp030NxtActYear__ = ntp030NxtActYear;
    }

    /**
     * <p>ntp030NxtActMonth を取得します。
     * @return ntp030NxtActMonth
     */
    public String getNtp030NxtActMonth() {
        return ntp030NxtActMonth__;
    }

    /**
     * <p>ntp030NxtActMonth をセットします。
     * @param ntp030NxtActMonth ntp030NxtActMonth
     */
    public void setNtp030NxtActMonth(String ntp030NxtActMonth) {
        ntp030NxtActMonth__ = ntp030NxtActMonth;
    }

    /**
     * <p>ntp030NxtActDay を取得します。
     * @return ntp030NxtActDay
     */
    public String getNtp030NxtActDay() {
        return ntp030NxtActDay__;
    }

    /**
     * <p>ntp030NxtActDay をセットします。
     * @param ntp030NxtActDay ntp030NxtActDay
     */
    public void setNtp030NxtActDay(String ntp030NxtActDay) {
        ntp030NxtActDay__ = ntp030NxtActDay;
    }

    /**
     * <p>ntp030NextAction を取得します。
     * @return ntp030NextAction
     */
    public String getNtp030NextAction() {
        return ntp030NextAction__;
    }

    /**
     * <p>ntp030NextAction をセットします。
     * @param ntp030NextAction ntp030NextAction
     */
    public void setNtp030NextAction(String ntp030NextAction) {
        ntp030NextAction__ = ntp030NextAction;
    }

    /**
     * <p>ntp030ActDateKbn を取得します。
     * @return ntp030ActDateKbn
     */
    public int getNtp030ActDateKbn() {
        return ntp030ActDateKbn__;
    }

    /**
     * <p>ntp030ActDateKbn をセットします。
     * @param ntp030ActDateKbn ntp030ActDateKbn
     */
    public void setNtp030ActDateKbn(int ntp030ActDateKbn) {
        ntp030ActDateKbn__ = ntp030ActDateKbn;
    }
}
