package jp.groupsession.v2.ntp.ntp040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.model.NippouSearchModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpMikomidoMsgModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CompanyModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DataModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenDao;
import jp.groupsession.v2.ntp.ntp100.Ntp100ParamModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 日報登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040ParamModel extends Ntp100ParamModel {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp040ParamModel.class);
    /** 日報データ有無フラグ true=有り false:無し*/
    private boolean ntp040DataFlg__ = false;

    /** 施設予約プラグイン使用有無 0=使用 1=未使用*/
    private int reservePluginKbn__;
    /** アドレス帳プラグイン使用有無 0=使用 1=未使用*/
    private int addressPluginKbn__;
    /** WEB検索プラグイン使用有無 0=使用 1=未使用*/
    private int searchPluginKbn__;

    /** 日報対象ユーザ名称 */
    private String ntp040UsrName__ = null;
    /** 日報対象ユーザbinSid */
    private Long ntp040UsrBinSid__ = new Long(0);
    /** 日報対象ユーザbinSid区分 */
    private int ntp040UsrPctKbn__ = 0;
    /** 日報対象ユーザ ログイン停止フラグ */
    private int ntp040UsrUkoFlg__ = 0;

    /** 日報登録者名称 */
    private String ntp040AddUsrName__ = null;
    /** 日報登録者削除区分 */
    private String ntp040AddUsrJkbn__ = null;
    /** 日報登録日時 */
    private String ntp040AddDate__ = null;
    /** 背景色 */
    private String ntp040Bgcolor__ = null;
    /** 背景色 初期値 */
    private String ntp040BgcolorInit__ = null;
    /** 日報タイトル */
    private String ntp040Title__ = null;
    /** 日報内容 */
    private String ntp040Value__ = null;
    /** 次のアクション 年 */
    private String ntp040NxtActYear__ = null;
    /** 次のアクション 月 */
    private String ntp040NxtActMonth__ = null;
    /** 次のアクション 日 */
    private String ntp040NxtActDay__ = null;
    /** 次のアクション 年月日 */
    private String ntp040NxtActDate__ = null;
    /** 次のアクション */
    private String ntp040NextAction__ = null;
    /** 次のアクション 日付区分 0:指定なし 1:指定あり*/
    private int ntp040ActDateKbn__ = 0;
    /** 日報内容 */
    private String ntp040DefaultValue__ = null;
    /** 日報内容2 */
    private String ntp040DefaultValue2__ = null;
    /** 日報備考 */
    private String ntp040Biko__ = null;
    /** 公開区分 */
    private String ntp040Public__ = null;
    /** 開始年 */
    private String ntp040FrYear__ = null;
    /** 開始月 */
    private String ntp040FrMonth__ = null;
    /** 開始日 */
    private String ntp040FrDay__ = null;
    /** 年 */
    private String ntp040InitYear__ = null;
    /** 月 */
    private String ntp040InitMonth__ = null;
    /** 日 */
    private String ntp040InitDay__ = null;
    /** 年月日 */
    private String ntp040InitDate__ = null;
    /** 開始時 */
    private String ntp040FrHour__ = null;
    /** 開始分 */
    private String ntp040FrMin__ = null;
    /** 終了年 */
    private String ntp040ToYear__ = null;
    /** 終了月 */
    private String ntp040ToMonth__ = null;
    /** 終了日 */
    private String ntp040ToDay__ = null;
    /** 終了時 */
    private String ntp040ToHour__ = null;
    /** 終了分 */
    private String ntp040ToMin__ = null;
    /** 開始年月日 */
    private String ntp040FrDate__ = null;
    /** 終了 年月日 */
    private String ntp040ToDate__ = null;
    /** 開始時間 */
    private String ntp040FrTime__ = null;
    /** 終了 時間 */
    private String ntp040ToTime__ = null;
    /** 分単位 */
    private int ntp040HourDivision__;

    /** 開始時(初期表示値) */
    private String ntp040DefFrHour__ = null;
    /** 開始分(初期表示値) */
    private String ntp040DefFrMin__ = null;
    /** 終了時(初期表示値) */
    private String ntp040DefToHour__ = null;
    /** 終了分(初期表示値) */
    private String ntp040DefToMin__ = null;

    /** 画面日付 曜日 */
    private String ntp040DspDateKbnStr__ = null;
    /** 画面日付 曜日 区分 */
    private String ntp040DspDateKbn__ = null;

    /** 日報画面遷移フラグ  0:通常 1:編集画面で報告日付編集後に遷移*/
    private int ntp040DspMoveFlg__ = 0;

    /** 日報データ編集権限  0:権限あり 1:権限なし*/
    private int ntp040EditKbn__ = 0;
    /** アドレス帳利用権限  0:権限あり 1:権限なし*/
    private int ntp040AddressUseOk__ = 0;
    /** ショートメール利用権限  0:権限あり 1:権限なし*/
    private int ntp040SmailUseOk__ = 0;

    /** 目標データリスト */
    private List<Ntp040TargetParam> targetDataList__ = null;

    /** 日報データ */
    private String nippouData__ = null;
    /** 日報データリスト */
    private List<Ntp040Param> nippouDataList__ = null;
    /** 活動分類 */
    private String ntp040Ktbunrui__ = null;
    /** 活動方法  */
    private String ntp040Kthouhou__ = null;
    /** 活動分類リスト */
    private List<LabelValueBean> ntp040KtbunruiLavel__ = null;
    /** 活動方法リスト */
    private List<LabelValueBean> ntp040KthouhouLavel__ = null;
    /** 見込み度  */
    private String ntp040Mikomido__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] ntp040selectFiles__ = null;

    /** プラグインID */
    private String ntp040pluginId__ = GSConstNippou.PLUGIN_ID_NIPPOU;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String ntp040BinSid__;

    /** 日報データリスト  */
    private List<Ntp040DataModel> ntp040DataModelList__;

    /** 添付ファイルリスト */
    private ArrayList<CmnBinfModel> ntp040FileList__ = null;

    /** 閲覧ユーザ情報 */
    private CmnUsrmInfModel ntp040UsrInfMdl__ = null;

    /** 同時登録グループSID */
    private String ntp040GroupSid__ = null;
    /** セーブユーザーリスト */
    private String[] sv_users__ = null;
    /** ユーザーリスト（同時登録）*/
    private String[] users_r__ = null;

    /** 年リスト */
    private ArrayList <LabelValueBean> ntp040YearLavel__ = null;
    /** 月リスト */
    private ArrayList <LabelValueBean> ntp040MonthLavel__ = null;
    /** 日リスト */
    private ArrayList <LabelValueBean> ntp040DayLavel__ = null;
    /** 時リスト */
    private ArrayList <LabelValueBean> ntp040HourLavel__ = null;
    /** 分リスト */
    private ArrayList <LabelValueBean> ntp040MinuteLavel__ = null;
    /** 同時登録グループリスト */
    private List<NtpLabelValueModel> ntp040GroupLavel__ = null;
    /** 同時登録グループ所属ユーザリスト */
    private ArrayList <CmnUsrmInfModel> ntp040BelongLavel__ = null;
    /** 同時登録ユーザリスト */
    private ArrayList <CmnUsrmInfModel> ntp040SelectUsrLavel__ = null;
    /** 既登録の同時登録ユーザリスト */
    private ArrayList <CmnUsrmInfModel> ntp040AddedUsrLavel__ = null;
    /** カラーコメントリスト */
    private ArrayList <String> ntp040ColorMsgList__ = null;
    /** 見込み度基準リスト */
    private ArrayList <NtpMikomidoMsgModel> ntp040MikomidoMsgList__ = null;
    /** 見込み度基準存在フラグ  0:なし 1:あり*/
    private int ntp040MikomidoFlg__ = 0;

    /** 繰り返しで登録日報表示フラグ */
    private boolean ntp040ExTextDspFlg__ = false;

    /** 同時登録日報へ反映有無 */
    private String ntp040BatchRef__ = "1";

    /** 編集権限設定 0=未設定 1=本人のみ 2=所属グループ */
    private String ntp040Edit__ = null;

    /** 時間指定有無 0=有り 1=無し */
    private String ntp040TimeKbn__ = String.valueOf(GSConstSchedule.TIME_EXIST);

    //施設予約
    /** 同時登録施設予約へ反映有無 */
    private String ntp040ResBatchRef__ = "1";
    /** 同時登録施設グループSID */
    private String ntp040ReserveGroupSid__ = null;
    /** セーブ施設リスト */
    private String[] svReserveUsers__ = null;
    /** 施設リスト（同時登録）*/
    private String[] reserve_r__ = null;

    /** 同時登録施設グループリスト */
    private List<LabelValueBean> ntp040ReserveGroupLavel__ = null;
    /** 同時登録施設グループ所属施設リスト */
    private ArrayList <RsvSisDataModel> ntp040ReserveBelongLavel__ = null;
    /** 同時登録施設リスト */
    private ArrayList <RsvSisDataModel> ntp040ReserveSelectLavel__ = null;

    /** 同時登録されたアクセス権限のない施設予約数 */
    private int ntp040CantReadRsvCount__ = 0;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ntp040InitFlg__ = String.valueOf(GSConstSchedule.INIT_FLG);

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ntp040ScrollFlg__ = "0";

    /** 複写フラグ 0=通常 1=複写 */
    private String ntp040CopyFlg__ = GSConstSchedule.NOT_COPY_FLG;

    //案件情報
    /** 案件SID */
    private int ntp040AnkenSid__;
    /** 案件名 */
    private String ntp040AnkenName__ = null;
    /** 案件コード */
    private String ntp040AnkenCode__ = null;

    //会社情報
    /** 会社SID */
    private int ntp040CompanySid__;
    /** 会社拠点SID */
    private int ntp040CompanyBaseSid__;
    /** 会社名 */
    private String ntp040CompanyName__ = null;
    /** 会社コード */
    private String ntp040CompanyCode__ = null;
    /** 会社コード */
    private String ntp040CompanyBaseName__ = null;

    /** 担当者(アドレス情報) */
    private String[] ntp040AddressId__ = null;
    /** コンタクト履歴に反映 */
    private int ntp040contact__ = 0;

    /** 削除対象の会社ID */
    private String ntp040delCompanyId__ = null;
    /** 削除対象の会社拠点ID */
    private String ntp040delCompanyBaseId__ = null;

    /** 会社情報一覧 */
    private List<Ntp040CompanyModel> ntp040CompanyList__ = null;

    /** 項目 案件 0:使用する 1:使用しない */
    private int ntp040AnkenUse__ = GSConstNippou.ITEM_USE;
    /** 項目 企業・顧客 0:使用する 1:使用しない */
    private int ntp040CompanyUse__ = GSConstNippou.ITEM_USE;
    /** 項目 活動分類/方法 0:使用する 1:使用しない */
    private int ntp040KtBriHhuUse__ = GSConstNippou.ITEM_USE;
    /** 項目 見込み度 0:使用する 1:使用しない */
    private int ntp040MikomidoUse__ = GSConstNippou.ITEM_USE;
    /** 項目 添付ファイル 0:使用する 1:使用しない */
    private int ntp040TmpFileUse__ = GSConstNippou.ITEM_USE;
    /** 項目 次のアクション 0:使用する 1:使用しない */
    private int ntp040NextActionUse__ = GSConstNippou.ITEM_USE;

    /** 項目 案件、企業・顧客 表示区分 0:両方 1:案件 2:企業・顧客 3：両方なし*/
    private int ntp040AnkenCompanyUse__ = GSConstNippou.ITEM_BOTH;


    /** 目標情報 */
    private List<Ntp040DspTargetModel> ntp040DspTargetMdlList__;
//    private Ntp040DspTargetModel ntp040DspTargetMdl__;

    /** 目標編集権限 */
    private int ntp040TargetAdmKbn__ = GSConst.USER_NOT_ADMIN;

    /** アドレス帳履歴 ページ番号 */
    private int ntp040AdrHistoryPageNum__ = 1;

    /** 案件履歴 ページ番号 */
    private int ntp040AnkenHistoryPageNum__ = 1;

    /** スケジュール画面遷移URL*/
    private String ntp040schUrl__;


    /** 検索画面パラメータ*/
    private String ntp040100SvSearchTarget__;
    /** 検索画面パラメータ*/
    private String ntp040100SearchTarget__;
    /** 検索画面パラメータ*/
    private String ntp040100SvBgcolor__;
    /** 検索画面パラメータ*/
    private String ntp040100Bgcolor__;
    /** 検索画面パラメータ*/
    private String ntp040100SvMikomido__;
    /** 検索画面パラメータ*/
    private String ntp040100Mikomido__;

    /** 時間パラメータ*/
    private String ntp040YearLavelStr__;
    /** 時間パラメータ*/
    private String ntp040MonthLavelStr__;
    /** 時間パラメータ*/
    private String ntp040DayLavelStr__;
    /** 時間パラメータ*/
    private String ntp040HourLavelStr__;
    /** 時間パラメータ*/
    private String ntp040MinuteLavelStr__;
    /** 時間パラメータ*/
    private String ntp040KtbunruiLavelStr__;
    /** 時間パラメータ*/
    private String ntp040KthouhouLavelStr__;

    /** 前の日報SID */
    private int ntp040PrevNtpSid__ = 0;
    /** 本日報SID */
    private int ntp040TodayNtpSid__ = 0;
    /** 次の日報SID */
    private int ntp040NextNtpSid__ = 0;

    /** 前・次 表示区分  */
    private int ntp040PrevNextKbn__ = 0;
    /** 前日報日付 */
    private String ntp040PrevNtpDate__;
    /** 今日報日付 */
    private String ntp040TodayNtpDate__;
    /** 次日報日付 */
    private String ntp040NextNtpDate__;

    /** ボタン用の処理モード */
    private String ntp040BtnCmd__;

    /**
     * <p>ntp040TimeKbn を取得します。
     * @return ntp040TimeKbn
     */
    public String getNtp040TimeKbn() {
        return ntp040TimeKbn__;
    }

    /**
     * <p>ntp040TimeKbn をセットします。
     * @param ntp040TimeKbn ntp040TimeKbn
     */
    public void setNtp040TimeKbn(String ntp040TimeKbn) {
        ntp040TimeKbn__ = ntp040TimeKbn;
    }

    /**
     * <p>ntp040ColorMsgList を取得します。
     * @return ntp040ColorMsgList
     */
    public ArrayList<String> getNtp040ColorMsgList() {
        return ntp040ColorMsgList__;
    }

    /**
     * <p>ntp040ColorMsgList をセットします。
     * @param ntp040ColorMsgList ntp040ColorMsgList
     */
    public void setNtp040ColorMsgList(ArrayList<String> ntp040ColorMsgList) {
        ntp040ColorMsgList__ = ntp040ColorMsgList;
    }

    /**
     * <p>ntp040DataFlg を取得します。
     * @return ntp040DataFlg
     */
    public boolean isNtp040DataFlg() {
        return ntp040DataFlg__;
    }

    /**
     * <p>ntp040DataFlg をセットします。
     * @param ntp040DataFlg ntp040DataFlg
     */
    public void setNtp040DataFlg(boolean ntp040DataFlg) {
        ntp040DataFlg__ = ntp040DataFlg;
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
     * <p>ntp040ResBatchRef を取得します。
     * @return ntp040ResBatchRef
     */
    public String getNtp040ResBatchRef() {
        return ntp040ResBatchRef__;
    }

    /**
     * <p>ntp040ResBatchRef をセットします。
     * @param ntp040ResBatchRef ntp040ResBatchRef
     */
    public void setNtp040ResBatchRef(String ntp040ResBatchRef) {
        ntp040ResBatchRef__ = ntp040ResBatchRef;
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
     * <p>ntp040ReserveBelongLavel を取得します。
     * @return ntp040ReserveBelongLavel
     */
    public ArrayList<RsvSisDataModel> getNtp040ReserveBelongLavel() {
        return ntp040ReserveBelongLavel__;
    }

    /**
     * <p>ntp040ReserveBelongLavel をセットします。
     * @param ntp040ReserveBelongLavel ntp040ReserveBelongLavel
     */
    public void setNtp040ReserveBelongLavel(
            ArrayList<RsvSisDataModel> ntp040ReserveBelongLavel) {
        ntp040ReserveBelongLavel__ = ntp040ReserveBelongLavel;
    }

    /**
     * <p>ntp040ReserveGroupLavel を取得します。
     * @return ntp040ReserveGroupLavel
     */
    public List<LabelValueBean> getNtp040ReserveGroupLavel() {
        return ntp040ReserveGroupLavel__;
    }

    /**
     * <p>ntp040ReserveGroupLavel をセットします。
     * @param ntp040ReserveGroupLavel ntp040ReserveGroupLavel
     */
    public void setNtp040ReserveGroupLavel(
            List<LabelValueBean> ntp040ReserveGroupLavel) {
        ntp040ReserveGroupLavel__ = ntp040ReserveGroupLavel;
    }

    /**
     * <p>ntp040ReserveGroupSid を取得します。
     * @return ntp040ReserveGroupSid
     */
    public String getNtp040ReserveGroupSid() {
        return ntp040ReserveGroupSid__;
    }

    /**
     * <p>ntp040ReserveGroupSid をセットします。
     * @param ntp040ReserveGroupSid ntp040ReserveGroupSid
     */
    public void setNtp040ReserveGroupSid(String ntp040ReserveGroupSid) {
        ntp040ReserveGroupSid__ = ntp040ReserveGroupSid;
    }

    /**
     * <p>ntp040ReserveSelectLavel を取得します。
     * @return ntp040ReserveSelectLavel
     */
    public ArrayList<RsvSisDataModel> getNtp040ReserveSelectLavel() {
        return ntp040ReserveSelectLavel__;
    }

    /**
     * <p>ntp040ReserveSelectLavel をセットします。
     * @param ntp040ReserveSelectLavel ntp040ReserveSelectLavel
     */
    public void setNtp040ReserveSelectLavel(
            ArrayList<RsvSisDataModel> ntp040ReserveSelectLavel) {
        ntp040ReserveSelectLavel__ = ntp040ReserveSelectLavel;
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
     * <p>ntp040AddedUsrLavel を取得します。
     * @return ntp040AddedUsrLavel
     */
    public ArrayList<CmnUsrmInfModel> getNtp040AddedUsrLavel() {
        return ntp040AddedUsrLavel__;
    }


    /**
     * <p>ntp040AddedUsrLavel をセットします。
     * @param ntp040AddedUsrLavel ntp040AddedUsrLavel
     */
    public void setNtp040AddedUsrLavel(
            ArrayList<CmnUsrmInfModel> ntp040AddedUsrLavel) {
        ntp040AddedUsrLavel__ = ntp040AddedUsrLavel;
    }


    /**
     * <p>ntp040Edit を取得します。
     * @return ntp040Edit
     */
    public String getNtp040Edit() {
        return ntp040Edit__;
    }


    /**
     * <p>ntp040Edit をセットします。
     * @param ntp040Edit ntp040Edit
     */
    public void setNtp040Edit(String ntp040Edit) {
        ntp040Edit__ = ntp040Edit;
    }

    /**
     * <p>ntp040BatchRef を取得します。
     * @return ntp040BatchRef
     */
    public String getNtp040BatchRef() {
        return ntp040BatchRef__;
    }


    /**
     * <p>ntp040BatchRef をセットします。
     * @param ntp040BatchRef ntp040BatchRef
     */
    public void setNtp040BatchRef(String ntp040BatchRef) {
        ntp040BatchRef__ = ntp040BatchRef;
    }


    /**
     * @return sv_users を戻します。
     */
    public String[] getSv_users() {
        return sv_users__;
    }


    /**
     * @return ntp040AddUsrJkbn を戻します。
     */
    public String getNtp040AddUsrJkbn() {
        return ntp040AddUsrJkbn__;
    }


    /**
     * @param ntp040AddUsrJkbn 設定する ntp040AddUsrJkbn。
     */
    public void setNtp040AddUsrJkbn(String ntp040AddUsrJkbn) {
        ntp040AddUsrJkbn__ = ntp040AddUsrJkbn;
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
     * @return ntp040FrHour を戻します。
     */
    public String getNtp040FrHour() {
        return ntp040FrHour__;
    }


    /**
     * @param ntp040FrHour 設定する ntp040FrHour。
     */
    public void setNtp040FrHour(String ntp040FrHour) {
        ntp040FrHour__ = ntp040FrHour;
    }


    /**
     * @return ntp040FrMin を戻します。
     */
    public String getNtp040FrMin() {
        return ntp040FrMin__;
    }


    /**
     * @param ntp040FrMin 設定する ntp040FrMin。
     */
    public void setNtp040FrMin(String ntp040FrMin) {
        ntp040FrMin__ = ntp040FrMin;
    }


    /**
     * @return ntp040ToHour を戻します。
     */
    public String getNtp040ToHour() {
        return ntp040ToHour__;
    }


    /**
     * @param ntp040ToHour 設定する ntp040ToHour。
     */
    public void setNtp040ToHour(String ntp040ToHour) {
        ntp040ToHour__ = ntp040ToHour;
    }


    /**
     * @return ntp040ToMin を戻します。
     */
    public String getNtp040ToMin() {
        return ntp040ToMin__;
    }


    /**
     * @param ntp040ToMin 設定する ntp040ToMin。
     */
    public void setNtp040ToMin(String ntp040ToMin) {
        ntp040ToMin__ = ntp040ToMin;
    }
    
    
    /**
     * <p>ntp040FrDate を取得します。
     * @return ntp040FrDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040FrDate__
     */
    public String getNtp040FrDate() {
        return ntp040FrDate__;
    }

    /**
     * <p>ntp040FrDate をセットします。
     * @param ntp040FrDate ntp040FrDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040FrDate__
     */
    public void setNtp040FrDate(String ntp040FrDate) {
        ntp040FrDate__ = ntp040FrDate;
    }

    /**
     * <p>ntp040ToDate を取得します。
     * @return ntp040ToDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040ToDate__
     */
    public String getNtp040ToDate() {
        return ntp040ToDate__;
    }

    /**
     * <p>ntp040ToDate をセットします。
     * @param ntp040ToDate ntp040ToDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040ToDate__
     */
    public void setNtp040ToDate(String ntp040ToDate) {
        ntp040ToDate__ = ntp040ToDate;
    }

    /**
     * <p>ntp040FrTime を取得します。
     * @return ntp040FrTime
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040FrTime__
     */
    public String getNtp040FrTime() {
        return ntp040FrTime__;
    }

    /**
     * <p>ntp040FrTime をセットします。
     * @param ntp040FrTime ntp040FrTime
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040FrTime__
     */
    public void setNtp040FrTime(String ntp040FrTime) {
        ntp040FrTime__ = ntp040FrTime;
    }

    /**
     * <p>ntp040ToTime を取得します。
     * @return ntp040ToTime
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040ToTime__
     */
    public String getNtp040ToTime() {
        return ntp040ToTime__;
    }

    /**
     * <p>ntp040ToTime をセットします。
     * @param ntp040ToTime ntp040ToTime
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040Form#ntp040ToTime__
     */
    public void setNtp040ToTime(String ntp040ToTime) {
        ntp040ToTime__ = ntp040ToTime;
    }
    
    /**
     * <p>ntp040HourDivision を取得します。
     * @return ntp040HourDivision
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040ParamModel#ntp040HourDivision__
     */
    public int getNtp040HourDivision() {
        return ntp040HourDivision__;
    }

    /**
     * <p>ntp040HourDivision をセットします。
     * @param ntp040HourDivision ntp040HourDivision
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040ParamModel#ntp040HourDivision__
     */
    public void setNtp040HourDivision(int ntp040HourDivision) {
        ntp040HourDivision__ = ntp040HourDivision;
    }

    /**
     * @return ntp040DefFrHour を戻します。
     */
    public String getNtp040DefFrHour() {
        return ntp040DefFrHour__;
    }

    /**
     * @param ntp040DefFrHour 設定する ntp040DefFrHour。
     */
    public void setNtp040DefFrHour(String ntp040DefFrHour) {
        ntp040DefFrHour__ = ntp040DefFrHour;
    }

    /**
     * @return ntp040DefFrMin を戻します。
     */
    public String getNtp040DefFrMin() {
        return ntp040DefFrMin__;
    }

    /**
     * @param ntp040DefFrMin 設定する ntp040DefFrMin。
     */
    public void setNtp040DefFrMin(String ntp040DefFrMin) {
        ntp040DefFrMin__ = ntp040DefFrMin;
    }

    /**
     * @return ntp040DefToHour を戻します。
     */
    public String getNtp040DefToHour() {
        return ntp040DefToHour__;
    }

    /**
     * @param ntp040DefToHour 設定する ntp040DefToHour。
     */
    public void setNtp040DefToHour(String ntp040DefToHour) {
        ntp040DefToHour__ = ntp040DefToHour;
    }

    /**
     * @return ntp040DefToMin を戻します。
     */
    public String getNtp040DefToMin() {
        return ntp040DefToMin__;
    }

    /**
     * @param ntp040DefToMin 設定する ntp040DefToMin。
     */
    public void setNtp040DefToMin(String ntp040DefToMin) {
        ntp040DefToMin__ = ntp040DefToMin;
    }

    /**
     * @return ntp040GroupSid を戻します。
     */
    public String getNtp040GroupSid() {
        return ntp040GroupSid__;
    }


    /**
     * @param ntp040GroupSid 設定する ntp040GroupSid。
     */
    public void setNtp040GroupSid(String ntp040GroupSid) {
        ntp040GroupSid__ = ntp040GroupSid;
    }


    /**
     * @return ntp040AddUsrName を戻します。
     */
    public String getNtp040AddUsrName() {
        return ntp040AddUsrName__;
    }


    /**
     * @param ntp040AddUsrName 設定する ntp040AddUsrName。
     */
    public void setNtp040AddUsrName(String ntp040AddUsrName) {
        ntp040AddUsrName__ = ntp040AddUsrName;
    }


    /**
     * @return ntp040BelongLavel を戻します。
     */
    public ArrayList < CmnUsrmInfModel > getNtp040BelongLavel() {
        return ntp040BelongLavel__;
    }


    /**
     * @param ntp040BelongLavel 設定する ntp040BelongLavel。
     */
    public void setNtp040BelongLavel(ArrayList < CmnUsrmInfModel > ntp040BelongLavel) {
        ntp040BelongLavel__ = ntp040BelongLavel;
    }


    /**
     * @return ntp040Bgcolor を戻します。
     */
    public String getNtp040Bgcolor() {
        return ntp040Bgcolor__;
    }


    /**
     * @param ntp040Bgcolor 設定する ntp040Bgcolor。
     */
    public void setNtp040Bgcolor(String ntp040Bgcolor) {
        ntp040Bgcolor__ = ntp040Bgcolor;
    }


    /**
     * @return ntp040Biko を戻します。
     */
    public String getNtp040Biko() {
        return ntp040Biko__;
    }


    /**
     * @param ntp040Biko 設定する ntp040Biko。
     */
    public void setNtp040Biko(String ntp040Biko) {
        ntp040Biko__ = ntp040Biko;
    }


    /**
     * @return ntp040DayLavel を戻します。
     */
    public ArrayList<LabelValueBean> getNtp040DayLavel() {
        return ntp040DayLavel__;
    }


    /**
     * @param ntp040DayLavel 設定する ntp040DayLavel。
     */
    public void setNtp040DayLavel(ArrayList < LabelValueBean > ntp040DayLavel) {
        ntp040DayLavel__ = ntp040DayLavel;
    }


    /**
     * @return ntp040FrDay を戻します。
     */
    public String getNtp040FrDay() {
        return ntp040FrDay__;
    }


    /**
     * @param ntp040FrDay 設定する ntp040FrDay。
     */
    public void setNtp040FrDay(String ntp040FrDay) {
        ntp040FrDay__ = ntp040FrDay;
    }


    /**
     * @return ntp040FrMonth を戻します。
     */
    public String getNtp040FrMonth() {
        return ntp040FrMonth__;
    }


    /**
     * @param ntp040FrMonth 設定する ntp040FrMonth。
     */
    public void setNtp040FrMonth(String ntp040FrMonth) {
        ntp040FrMonth__ = ntp040FrMonth;
    }


    /**
     * @return ntp040FrYear を戻します。
     */
    public String getNtp040FrYear() {
        return ntp040FrYear__;
    }


    /**
     * @param ntp040FrYear 設定する ntp040FrYear。
     */
    public void setNtp040FrYear(String ntp040FrYear) {
        ntp040FrYear__ = ntp040FrYear;
    }


    /**
     * @return ntp040GroupLavel を戻します。
     */
    public List<NtpLabelValueModel> getNtp040GroupLavel() {
        return ntp040GroupLavel__;
    }


    /**
     * @param list 設定する ntp040GroupLavel。
     */
    public void setNtp040GroupLavel(List<NtpLabelValueModel> list) {
        ntp040GroupLavel__ = list;
    }


    /**
     * @return ntp040MonthLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp040MonthLavel() {
        return ntp040MonthLavel__;
    }


    /**
     * @param ntp040MonthLavel 設定する ntp040MonthLavel。
     */
    public void setNtp040MonthLavel(ArrayList < LabelValueBean > ntp040MonthLavel) {
        ntp040MonthLavel__ = ntp040MonthLavel;
    }


    /**
     * @return ntp040Public を戻します。
     */
    public String getNtp040Public() {
        return ntp040Public__;
    }


    /**
     * @param ntp040Public 設定する ntp040Public。
     */
    public void setNtp040Public(String ntp040Public) {
        ntp040Public__ = ntp040Public;
    }


    /**
     * @return ntp040SelectUsrLavel を戻します。
     */
    public ArrayList < CmnUsrmInfModel > getNtp040SelectUsrLavel() {
        return ntp040SelectUsrLavel__;
    }


    /**
     * @param ntp040SelectUsrLavel 設定する ntp040SelectUsrLavel。
     */
    public void setNtp040SelectUsrLavel(ArrayList < CmnUsrmInfModel > ntp040SelectUsrLavel) {
        ntp040SelectUsrLavel__ = ntp040SelectUsrLavel;
    }


    /**
     * @return ntp040Title を戻します。
     */
    public String getNtp040Title() {
        return ntp040Title__;
    }


    /**
     * @param ntp040Title 設定する ntp040Title。
     */
    public void setNtp040Title(String ntp040Title) {
        ntp040Title__ = ntp040Title;
    }


    /**
     * @return ntp040ToDay を戻します。
     */
    public String getNtp040ToDay() {
        return ntp040ToDay__;
    }


    /**
     * @param ntp040ToDay 設定する ntp040ToDay。
     */
    public void setNtp040ToDay(String ntp040ToDay) {
        ntp040ToDay__ = ntp040ToDay;
    }


    /**
     * @return ntp040ToMonth を戻します。
     */
    public String getNtp040ToMonth() {
        return ntp040ToMonth__;
    }


    /**
     * @param ntp040ToMonth 設定する ntp040ToMonth。
     */
    public void setNtp040ToMonth(String ntp040ToMonth) {
        ntp040ToMonth__ = ntp040ToMonth;
    }


    /**
     * @return ntp040ToYear を戻します。
     */
    public String getNtp040ToYear() {
        return ntp040ToYear__;
    }


    /**
     * @param ntp040ToYear 設定する ntp040ToYear。
     */
    public void setNtp040ToYear(String ntp040ToYear) {
        ntp040ToYear__ = ntp040ToYear;
    }


    /**
     * @return ntp040UsrName を戻します。
     */
    public String getNtp040UsrName() {
        return ntp040UsrName__;
    }


    /**
     * @param ntp040UsrName 設定する ntp040UsrName。
     */
    public void setNtp040UsrName(String ntp040UsrName) {
        ntp040UsrName__ = ntp040UsrName;
    }


    /**
     * @return ntp040Value を戻します。
     */
    public String getNtp040Value() {
        return ntp040Value__;
    }


    /**
     * @param ntp040Value 設定する ntp040Value。
     */
    public void setNtp040Value(String ntp040Value) {
        ntp040Value__ = ntp040Value;
    }


    /**
     * @return ntp040YearLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp040YearLavel() {
        return ntp040YearLavel__;
    }


    /**
     * @param ntp040YearLavel 設定する ntp040YearLavel。
     */
    public void setNtp040YearLavel(ArrayList < LabelValueBean > ntp040YearLavel) {
        ntp040YearLavel__ = ntp040YearLavel;
    }


    /**
     * @return ntp040HourLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp040HourLavel() {
        return ntp040HourLavel__;
    }


    /**
     * @param ntp040HourLavel 設定する ntp040HourLavel。
     */
    public void setNtp040HourLavel(ArrayList < LabelValueBean > ntp040HourLavel) {
        ntp040HourLavel__ = ntp040HourLavel;
    }


    /**
     * @return ntp040MinuteLavel を戻します。
     */
    public ArrayList < LabelValueBean > getNtp040MinuteLavel() {
        return ntp040MinuteLavel__;
    }


    /**
     * @param ntp040MinuteLavel 設定する ntp040MinuteLavel。
     */
    public void setNtp040MinuteLavel(ArrayList < LabelValueBean > ntp040MinuteLavel) {
        ntp040MinuteLavel__ = ntp040MinuteLavel;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, ActionErrors> validateCheck(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            int sessionUsrSid) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = null;
        boolean isAdd = true;
        Ntp040Biz biz = new Ntp040Biz(con, reqMdl);
        NippouSearchModel ntpMdl = null;
        //行番号とエラー内容を格納するMAP
        Map<Integer, ActionErrors> errorsMap = new TreeMap<Integer, ActionErrors>();

        if (getCmd().equals("editNtp")) {
            isAdd = false;
            int changeNtpSid = NullDefault.getInt(
                    getNtp010NipSid(), -1);
            List<NippouSearchModel> ntpList = biz.getNtpDataSingle(changeNtpSid, con);
            if (ntpList.size() > 0) {
                ntpMdl = ntpList.get(0);
                //編集対象日報のユーザSIDおよび日報SIDから、閲覧権限チェック
                //編集対象ユーザSID
                int selectUsrSid = ntpMdl.getUsrSid();
                //対象の日報がセッションユーザのものでない場合
                if (sessionUsrSid != selectUsrSid) {
                    //セッションユーザが閲覧不可のユーザSIDを取得
                    NippouDao ntpDao = new NippouDao(con);
                    List<Integer> notAccessSids = ntpDao.getNotAccessUserList(sessionUsrSid);
                    HttpSession session = reqMdl.getSession();
                    BaseUserModel umodel =
                            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
                    CommonBiz cmnBiz = new CommonBiz();
                    errors = new ActionErrors();
                    //セッションユーザが管理者ではない場合
                    if (!cmnBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
                        msg = new ActionMessage("error.edit.power.user",
                                gsMsg.getMessage("cmn.admin"), gsMsg.getMessage("cmn.edit"));
                        errors.add("error.add.not.admin", msg);
                    //セッションユーザに対象ユーザの閲覧権限がない場合
                    } else if (notAccessSids.contains(selectUsrSid)) {
                        msg = new ActionMessage("error.edit.power.user",
                                gsMsg.getMessage("cmn.edit"), gsMsg.getMessage("cmn.edit"));
                        errors.add("error.edit.not.auth", msg);
                    }
                    if (errors.size() > 0) {
                        errorsMap.put(Integer.valueOf(-1), errors);
                    }
                }
            } else {
                //対象日報が削除されていた場合
                errors = new ActionErrors();
                msg = new ActionMessage("search.notfound.tdfkcode", gsMsg.getMessage("ntp.1"));
                errors.add("error.can.not.edit", msg);
            }
        }

        //登録の場合、対象ユーザSIDと登録先で登録権限の確認をする
        if (isAdd) {
            //登録先ユーザSID
            int selectUsrSid = nippouDataList__.get(0).getSelectUsrSid();
            //対象の日報がセッションユーザのものでない場合
            if (sessionUsrSid != selectUsrSid) {
                //セッションユーザが閲覧不可のユーザSIDを取得
                NippouDao ntpDao = new NippouDao(con);
                List<Integer> notAccessSids = ntpDao.getNotAccessUserList(sessionUsrSid);
                HttpSession session = reqMdl.getSession();
                BaseUserModel umodel =
                        (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
                CommonBiz cmnBiz = new CommonBiz();
                errors = new ActionErrors();
                //セッションユーザが管理者ではない場合
                if (!cmnBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.admin"), gsMsg.getMessage("cmn.entry"));
                    errors.add("error.add.not.admin", msg);
                //セッションユーザに対象ユーザの閲覧権限がない場合
                } else if (notAccessSids.contains(selectUsrSid)) {
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.edit"), gsMsg.getMessage("cmn.entry"));
                    errors.add("error.edit.not.auth", msg);
                }
                if (errors.size() > 0) {
                    errorsMap.put(Integer.valueOf(-1), errors);
                }
            }
        }


        //目標データ
        if (targetDataList__ != null && !targetDataList__.isEmpty()) {
            errors = new ActionErrors();

            for (Ntp040TargetParam targetData : targetDataList__) {

                String targetVal = targetData.getRecordStr();

                GSValidateNippou.validateCmnFieldTextNumber(
                        errors,
                        GSConstNippou.TEXT_MOKUHYOU,
                        targetVal,
                       "targetVal",
                        GSConstNippou.MAX_LENGTH_RECORD,
                        true);
            }

            if (errors.size() > 0) {
                errorsMap.put(Integer.valueOf(-1), errors);
            }
        }

        //日報データ
        for (Ntp040Param ntpData : nippouDataList__) {

            errors = new ActionErrors();

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
            String nextAction     = String.valueOf(ntpData.getActionStr());
            String actYear        = String.valueOf(ntpData.getActionYear());
            String actMonth       = String.valueOf(ntpData.getActionMonth());
            String actDay         = String.valueOf(ntpData.getActionDay());
            String actionKbn  = String.valueOf(ntpData.getActDateKbn());

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
                        "報告日");
                errors.add("error.input.notfound.date", msg);
                log__.debug("error:1");
            } else {
                fromOk = true;
            }

            //終了年月日チェックフラグ(true=入力OK、false=NG)
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

            if (fromOk
                       && ntp040TimeKbn__.equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {

                if (frHour.equals("-1")
                 || frMin.equals("-1")
                 || toHour.equals("-1")
                 || toMin.equals("-1")
                        ) {
                            //時分
                            String textHourMinute = gsMsg.getMessage("schedule.src.36");
                            msg = new ActionMessage("error.input.required.text", textHourMinute);
                            errors.add("" + "error.input.required.text", msg);
                            fromOk = false;

                        }
            }

            //個別チェックOKの場合
            if (fromOk) {

                if (ntp040TimeKbn__.equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
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
                    String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
                    //開始・終了
                    String textStartEnd = gsMsg.getMessage("cmn.start.end");
                    msg = new ActionMessage("error.input.comp.text",
                            textStartEnd, textStartLessEnd);
                    errors.add("" + "error.input.comp.text", msg);
                    log__.debug("error:5");
                }

            }
            if (ntpData.getAnkenSid() > 0
                    && (isAdd
                            || (ntpMdl != null
                            && ntpMdl.getNanSid() != ntpData.getAnkenSid()))) {
                //新規登録時と編集時の案件SIDが違う場合は案件の権限チェック
                Ntp060AnkenDao ankenDao = new Ntp060AnkenDao(con);
                NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl);

                List<Integer> nanSidList = ankenDao.getNoPermissionAnkenSids(
                        Arrays.asList(Integer.valueOf(ntpData.getAnkenSid())),
                        GSConst.SP_AUTH_EDIT, sessionUsrSid,
                        ntpCmnBiz.isNippouAdmin(con, reqMdl));
                if (nanSidList.size() > 0) {
                    String action;
                    if (isAdd) {
                        action = gsMsg.getMessage("cmn.entry");
                    } else {
                        action = gsMsg.getMessage("cmn.edit");
                    }
                    msg = new ActionMessage("error.edit.power.notfound",
                            gsMsg.getMessage("ntp.11"),
                            action);
                    errors.add("" + "error.edit.power.notfound.anken" + number, msg);

                }
            }


            //タイトルのチェック
            if (__checkNoInput(errors, title, "ntp040Title",
                    gsMsg.getMessage("cmn.title"))) {
                if (__checkRange(
                        errors,
                        title,
                        "ntp040Title",
                        gsMsg.getMessage("cmn.title"),
                        GSConstNippou.MAX_LENGTH_TITLE)) {
                    //先頭スペースチェック
                    if (ValidateUtil.isSpaceStart(title)) {
                        //タイトル
                        String textTitle = gsMsg.getMessage("cmn.title");
                        msg = new ActionMessage("error.input.spase.start",
                                textTitle);
                        StrutsUtil.addMessage(errors, msg, "ntp040Title");
                    } else {
                        //タブ文字が含まれている
                        if (ValidateUtil.isTab(title)) {
                            //タイトル
                            String textTitle = gsMsg.getMessage("cmn.title");
                            msg = new ActionMessage("error.input.tab.text", textTitle);
                            StrutsUtil.addMessage(errors, msg, "ntp040Title");
                        } else {
                            __checkJisString(
                                    errors,
                                    title,
                                    "ntp040Title",
                                    gsMsg.getMessage("cmn.title"));
                        }
                    }
                }

            }
            boolean valueError = false;
            //内容のチェック
            if (__checkRangeTextarea(
                    errors,
                    naiyou,
                    "ntp040Value",
                    gsMsg.getMessage("cmn.content"),
                    GSConstSchedule.MAX_LENGTH_VALUE)) {
                if (!StringUtil.isNullZeroString(naiyou)) {
                    //スペースのみチェック
                    if (!valueError && ValidateUtil.isSpaceOrKaigyou(naiyou)) {
                        msg = new ActionMessage("error.input.spase.cl.only",
                                gsMsg.getMessage("cmn.content"));
                        StrutsUtil.addMessage(errors, msg, "ntp040Value");
                        valueError = true;
                    }
                    if (!valueError) {
                        //JIS
                        __checkJisString(
                                errors,
                                naiyou,
                                "ntp040Value",
                                gsMsg.getMessage("cmn.content"));
                    }
                }
            }

            if (ntp040NextActionUse__ == GSConstNippou.ITEM_USE) {

                if (!actionKbn.equals("0")) {
                    int aYear = -1;
                    int aMonth = -1;
                    int aDay = -1;
                    if (!StringUtil.isNullZeroStringSpace(actYear)) {
                        aYear = Integer.parseInt(actYear);
                        aMonth = Integer.parseInt(actMonth);
                        aDay = Integer.parseInt(actDay);
                    }

                    UDate actDate = new UDate();
                    actDate.setDate(aYear, aMonth, aDay);
                    actDate.setSecond(GSConstSchedule.DAY_START_SECOND);
                    actDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
                    if (actDate.getYear() != aYear
                    || actDate.getMonth() != aMonth
                    || actDate.getIntDay() != aDay) {
                        msg = new ActionMessage("error.input.notfound.date",
                                "次のアクション日");
                        errors.add("error.input.notfound.date", msg);
                        log__.debug("error:1");
                    }
                }

                boolean nextActionError = false;
                //次のアクションチェック
                if (__checkRangeTextarea(
                        errors,
                        nextAction,
                        "ntp040Action",
                        "次のアクション",
                        GSConstSchedule.MAX_LENGTH_VALUE)) {
                    if (!StringUtil.isNullZeroString(nextAction)) {
                        //スペースのみチェック
                        if (!nextActionError && ValidateUtil.isSpaceOrKaigyou(nextAction)) {
                            msg = new ActionMessage("error.input.spase.cl.only",
                                    "次のアクション");
                            StrutsUtil.addMessage(errors, msg, "ntp040Action");
                            nextActionError = true;
                        }
                        if (!nextActionError) {
                            //JIS
                            __checkJisString(
                                    errors,
                                    nextAction,
                                    "ntp040Action",
                                    "次のアクション");
                        }
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
        ntp040delCompanyId__ = "";
        ntp040delCompanyBaseId__ = "";
        addressPluginKbn__ = 0;
        searchPluginKbn__ = 0;
        ntp040FrYear__ = "";
        ntp040FrMonth__ = "";
        ntp040FrDay__ = "";
    }

    /**
     * <p>ntp040AddDate を取得します。
     * @return ntp040AddDate
     */
    public String getNtp040AddDate() {
        return ntp040AddDate__;
    }

    /**
     * <p>ntp040AddDate をセットします。
     * @param ntp040AddDate ntp040AddDate
     */
    public void setNtp040AddDate(String ntp040AddDate) {
        ntp040AddDate__ = ntp040AddDate;
    }

    /**
     * <p>ntp040InitFlg を取得します。
     * @return ntp040InitFlg
     */
    public String getNtp040InitFlg() {
        return ntp040InitFlg__;
    }

    /**
     * <p>ntp040InitFlg をセットします。
     * @param ntp040InitFlg ntp040InitFlg
     */
    public void setNtp040InitFlg(String ntp040InitFlg) {
        ntp040InitFlg__ = ntp040InitFlg;
    }

    /**
     * <p>ntp040AddressId を取得します。
     * @return ntp040AddressId
     */
    public String[] getNtp040AddressId() {
        return ntp040AddressId__;
    }

    /**
     * <p>ntp040AddressId をセットします。
     * @param ntp040AddressId ntp040AddressId
     */
    public void setNtp040AddressId(String[] ntp040AddressId) {
        ntp040AddressId__ = ntp040AddressId;
    }

    /**
     * <p>ntp040CompanyList を取得します。
     * @return ntp040CompanyList
     */
    public List<Ntp040CompanyModel> getNtp040CompanyList() {
        return ntp040CompanyList__;
    }

    /**
     * <p>ntp040CompanyList をセットします。
     * @param ntp040CompanyList ntp040CompanyList
     */
    public void setNtp040CompanyList(List<Ntp040CompanyModel> ntp040CompanyList) {
        ntp040CompanyList__ = ntp040CompanyList;
    }

    /**
     * <p>ntp040contact を取得します。
     * @return ntp040contact
     */
    public int getNtp040contact() {
        return ntp040contact__;
    }

    /**
     * <p>ntp040contact をセットします。
     * @param ntp040contact ntp040contact
     */
    public void setNtp040contact(int ntp040contact) {
        ntp040contact__ = ntp040contact;
    }

    /**
     * <p>ntp040delCompanyBaseId を取得します。
     * @return ntp040delCompanyBaseId
     */
    public String getNtp040delCompanyBaseId() {
        return ntp040delCompanyBaseId__;
    }

    /**
     * <p>ntp040delCompanyBaseId をセットします。
     * @param ntp040delCompanyBaseId ntp040delCompanyBaseId
     */
    public void setNtp040delCompanyBaseId(String ntp040delCompanyBaseId) {
        ntp040delCompanyBaseId__ = ntp040delCompanyBaseId;
    }

    /**
     * <p>ntp040delCompanyId を取得します。
     * @return ntp040delCompanyId
     */
    public String getNtp040delCompanyId() {
        return ntp040delCompanyId__;
    }

    /**
     * <p>ntp040delCompanyId をセットします。
     * @param ntp040delCompanyId ntp040delCompanyId
     */
    public void setNtp040delCompanyId(String ntp040delCompanyId) {
        ntp040delCompanyId__ = ntp040delCompanyId;
    }
    /**
     * @return ntp040ScrollFlg
     */
    public String getNtp040ScrollFlg() {
        return ntp040ScrollFlg__;
    }

    /**
     * @param ntp040ScrollFlg セットする ntp040ScrollFlg
     */
    public void setNtp040ScrollFlg(String ntp040ScrollFlg) {
        ntp040ScrollFlg__ = ntp040ScrollFlg;
    }

    /**
     * <p>ntp040ExTextDspFlg を取得します。
     * @return ntp040ExTextDspFlg
     */
    public boolean isNtp040ExTextDspFlg() {
        return ntp040ExTextDspFlg__;
    }

    /**
     * <p>ntp040ExTextDspFlg をセットします。
     * @param ntp040ExTextDspFlg ntp040ExTextDspFlg
     */
    public void setNtp040ExTextDspFlg(boolean ntp040ExTextDspFlg) {
        ntp040ExTextDspFlg__ = ntp040ExTextDspFlg;
    }

    /**
     * <p>ntp040CopyFlg を取得します。
     * @return ntp040CopyFlg
     */
    public String getNtp040CopyFlg() {
        return ntp040CopyFlg__;
    }

    /**
     * <p>ntp040CopyFlg をセットします。
     * @param ntp040CopyFlg ntp040CopyFlg
     */
    public void setNtp040CopyFlg(String ntp040CopyFlg) {
        ntp040CopyFlg__ = ntp040CopyFlg;
    }

    /**
     * <p>ntp040CantReadRsvCount を取得します。
     * @return ntp040CantReadRsvCount
     */
    public int getNtp040CantReadRsvCount() {
        return ntp040CantReadRsvCount__;
    }

    /**
     * <p>ntp040CantReadRsvCount をセットします。
     * @param ntp040CantReadRsvCount ntp040CantReadRsvCount
     */
    public void setNtp040CantReadRsvCount(int ntp040CantReadRsvCount) {
        ntp040CantReadRsvCount__ = ntp040CantReadRsvCount;
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
    public List<Ntp040Param> getNippouDataList() {
        return nippouDataList__;
    }

    /**
     * <p>nippouDataList をセットします。
     * @param nippouDataList nippouDataList
     */
    public void setNippouDataList(List<Ntp040Param> nippouDataList) {
        nippouDataList__ = nippouDataList;
    }

    /**
     * <p>ntp040KtbunruiLavel を取得します。
     * @return ntp040KtbunruiLavel
     */
    public List<LabelValueBean> getNtp040KtbunruiLavel() {
        return ntp040KtbunruiLavel__;
    }

    /**
     * <p>ntp040KtbunruiLavel をセットします。
     * @param ntp040KtbunruiLavel ntp040KtbunruiLavel
     */
    public void setNtp040KtbunruiLavel(List<LabelValueBean> ntp040KtbunruiLavel) {
        ntp040KtbunruiLavel__ = ntp040KtbunruiLavel;
    }

    /**
     * <p>ntp040KthouhouLavel を取得します。
     * @return ntp040KthouhouLavel
     */
    public List<LabelValueBean> getNtp040KthouhouLavel() {
        return ntp040KthouhouLavel__;
    }

    /**
     * <p>ntp040KthouhouLavel をセットします。
     * @param ntp040KthouhouLavel ntp040KthouhouLavel
     */
    public void setNtp040KthouhouLavel(List<LabelValueBean> ntp040KthouhouLavel) {
        ntp040KthouhouLavel__ = ntp040KthouhouLavel;
    }

    /**
     * <p>ntp040Ktbunrui を取得します。
     * @return ntp040Ktbunrui
     */
    public String getNtp040Ktbunrui() {
        return ntp040Ktbunrui__;
    }

    /**
     * <p>ntp040Ktbunrui をセットします。
     * @param ntp040Ktbunrui ntp040Ktbunrui
     */
    public void setNtp040Ktbunrui(String ntp040Ktbunrui) {
        ntp040Ktbunrui__ = ntp040Ktbunrui;
    }

    /**
     * <p>ntp040Kthouhou を取得します。
     * @return ntp040Kthouhou
     */
    public String getNtp040Kthouhou() {
        return ntp040Kthouhou__;
    }

    /**
     * <p>ntp040Kthouhou をセットします。
     * @param ntp040Kthouhou ntp040Kthouhou
     */
    public void setNtp040Kthouhou(String ntp040Kthouhou) {
        ntp040Kthouhou__ = ntp040Kthouhou;
    }

    /**
     * <p>ntp040Mikomido を取得します。
     * @return ntp040Mikomido
     */
    public String getNtp040Mikomido() {
        return ntp040Mikomido__;
    }

    /**
     * <p>ntp040Mikomido をセットします。
     * @param ntp040Mikomido ntp040Mikomido
     */
    public void setNtp040Mikomido(String ntp040Mikomido) {
        ntp040Mikomido__ = ntp040Mikomido;
    }

    /**
     * <p>ntp040selectFiles を取得します。
     * @return ntp040selectFiles
     */
    public String[] getNtp040selectFiles() {
        return ntp040selectFiles__;
    }

    /**
     * <p>ntp040selectFiles をセットします。
     * @param ntp040selectFiles ntp040selectFiles
     */
    public void setNtp040selectFiles(String[] ntp040selectFiles) {
        ntp040selectFiles__ = ntp040selectFiles;
    }

    /**
     * <p>ntp040FileList をセットします。
     * @param ntp040FileList ntp040FileList
     */
    public void setNtp040FileList(ArrayList<CmnBinfModel> ntp040FileList) {
        ntp040FileList__ = ntp040FileList;
    }

    /**
     * <p>ntp040DataModelList を取得します。
     * @return ntp040DataModelList
     */
    public List<Ntp040DataModel> getNtp040DataModelList() {
        return ntp040DataModelList__;
    }

    /**
     * <p>ntp040DataModelList をセットします。
     * @param ntp040DataModelList ntp040DataModelList
     */
    public void setNtp040DataModelList(List<Ntp040DataModel> ntp040DataModelList) {
        ntp040DataModelList__ = ntp040DataModelList;
    }

    /**
     * <p>ntp040pluginId を取得します。
     * @return ntp040pluginId
     */
    public String getNtp040pluginId() {
        return ntp040pluginId__;
    }

    /**
     * <p>ntp040pluginId をセットします。
     * @param ntp040pluginId ntp040pluginId
     */
    public void setNtp040pluginId(String ntp040pluginId) {
        ntp040pluginId__ = ntp040pluginId;
    }

    /**
     * <p>ntp040BinSid を取得します。
     * @return ntp040BinSid
     */
    public String getNtp040BinSid() {
        return ntp040BinSid__;
    }

    /**
     * <p>ntp040BinSid をセットします。
     * @param ntp040BinSid ntp040BinSid
     */
    public void setNtp040BinSid(String ntp040BinSid) {
        ntp040BinSid__ = ntp040BinSid;
    }

    /**
     * <p>ntp040FileList を取得します。
     * @return ntp040FileList
     */
    public ArrayList<CmnBinfModel> getNtp040FileList() {
        return ntp040FileList__;
    }

    /**
     * <p>ntp040UsrInfMdl を取得します。
     * @return ntp040UsrInfMdl
     */
    public CmnUsrmInfModel getNtp040UsrInfMdl() {
        return ntp040UsrInfMdl__;
    }

    /**
     * <p>ntp040UsrInfMdl をセットします。
     * @param ntp040UsrInfMdl ntp040UsrInfMdl
     */
    public void setNtp040UsrInfMdl(CmnUsrmInfModel ntp040UsrInfMdl) {
        ntp040UsrInfMdl__ = ntp040UsrInfMdl;
    }

    /**
     * <p>ntp040EditKbn を取得します。
     * @return ntp040EditKbn
     */
    public int getNtp040EditKbn() {
        return ntp040EditKbn__;
    }

    /**
     * <p>ntp040EditKbn をセットします。
     * @param ntp040EditKbn ntp040EditKbn
     */
    public void setNtp040EditKbn(int ntp040EditKbn) {
        ntp040EditKbn__ = ntp040EditKbn;
    }

    /**
     * <p>ntp040AddressUseOk を取得します。
     * @return ntp040AddressUseOk
     */
    public int getNtp040AddressUseOk() {
        return ntp040AddressUseOk__;
    }

    /**
     * <p>ntp040AddressUseOk をセットします。
     * @param ntp040AddressUseOk ntp040AddressUseOk
     */
    public void setNtp040AddressUseOk(int ntp040AddressUseOk) {
        ntp040AddressUseOk__ = ntp040AddressUseOk;
    }

    /**
     * <p>ntp040SmailUseOk を取得します。
     * @return ntp040SmailUseOk
     */
    public int getNtp040SmailUseOk() {
        return ntp040SmailUseOk__;
    }

    /**
     * <p>ntp040SmailUseOk をセットします。
     * @param ntp040SmailUseOk ntp040SmailUseOk
     */
    public void setNtp040SmailUseOk(int ntp040SmailUseOk) {
        ntp040SmailUseOk__ = ntp040SmailUseOk;
    }

    /**
     * <p>ntp040BgcolorInit を取得します。
     * @return ntp040BgcolorInit
     */
    public String getNtp040BgcolorInit() {
        return ntp040BgcolorInit__;
    }

    /**
     * <p>ntp040BgcolorInit をセットします。
     * @param ntp040BgcolorInit ntp040BgcolorInit
     */
    public void setNtp040BgcolorInit(String ntp040BgcolorInit) {
        ntp040BgcolorInit__ = ntp040BgcolorInit;
    }

    /**
     * <p>ntp040DspMoveFlg を取得します。
     * @return ntp040DspMoveFlg
     */
    public int getNtp040DspMoveFlg() {
        return ntp040DspMoveFlg__;
    }

    /**
     * <p>ntp040DspMoveFlg をセットします。
     * @param ntp040DspMoveFlg ntp040DspMoveFlg
     */
    public void setNtp040DspMoveFlg(int ntp040DspMoveFlg) {
        ntp040DspMoveFlg__ = ntp040DspMoveFlg;
    }

    /**
     * <p>targetDataList を取得します。
     * @return targetDataList
     */
    public List<Ntp040TargetParam> getTargetDataList() {
        return targetDataList__;
    }

    /**
     * <p>targetDataList をセットします。
     * @param targetDataList targetDataList
     */
    public void setTargetDataList(List<Ntp040TargetParam> targetDataList) {
        targetDataList__ = targetDataList;
    }

    /**
     * <p>ntp040TargetAdmKbn を取得します。
     * @return ntp040TargetAdmKbn
     */
    public int getNtp040TargetAdmKbn() {
        return ntp040TargetAdmKbn__;
    }

    /**
     * <p>ntp040TargetAdmKbn をセットします。
     * @param ntp040TargetAdmKbn ntp040TargetAdmKbn
     */
    public void setNtp040TargetAdmKbn(int ntp040TargetAdmKbn) {
        ntp040TargetAdmKbn__ = ntp040TargetAdmKbn;
    }

    /**
     * <p>ntp040AnkenUse を取得します。
     * @return ntp040AnkenUse
     */
    public int getNtp040AnkenUse() {
        return ntp040AnkenUse__;
    }

    /**
     * <p>ntp040AnkenUse をセットします。
     * @param ntp040AnkenUse ntp040AnkenUse
     */
    public void setNtp040AnkenUse(int ntp040AnkenUse) {
        ntp040AnkenUse__ = ntp040AnkenUse;
    }

    /**
     * <p>ntp040CompanyUse を取得します。
     * @return ntp040CompanyUse
     */
    public int getNtp040CompanyUse() {
        return ntp040CompanyUse__;
    }

    /**
     * <p>ntp040CompanyUse をセットします。
     * @param ntp040CompanyUse ntp040CompanyUse
     */
    public void setNtp040CompanyUse(int ntp040CompanyUse) {
        ntp040CompanyUse__ = ntp040CompanyUse;
    }

    /**
     * <p>ntp040KtBriHhuUse を取得します。
     * @return ntp040KtBriHhuUse
     */
    public int getNtp040KtBriHhuUse() {
        return ntp040KtBriHhuUse__;
    }

    /**
     * <p>ntp040KtBriHhuUse をセットします。
     * @param ntp040KtBriHhuUse ntp040KtBriHhuUse
     */
    public void setNtp040KtBriHhuUse(int ntp040KtBriHhuUse) {
        ntp040KtBriHhuUse__ = ntp040KtBriHhuUse;
    }

    /**
     * <p>ntp040MikomidoUse を取得します。
     * @return ntp040MikomidoUse
     */
    public int getNtp040MikomidoUse() {
        return ntp040MikomidoUse__;
    }

    /**
     * <p>ntp040MikomidoUse をセットします。
     * @param ntp040MikomidoUse ntp040MikomidoUse
     */
    public void setNtp040MikomidoUse(int ntp040MikomidoUse) {
        ntp040MikomidoUse__ = ntp040MikomidoUse;
    }

    /**
     * <p>ntp040TmpFileUse を取得します。
     * @return ntp040TmpFileUse
     */
    public int getNtp040TmpFileUse() {
        return ntp040TmpFileUse__;
    }

    /**
     * <p>ntp040TmpFileUse をセットします。
     * @param ntp040TmpFileUse ntp040TmpFileUse
     */
    public void setNtp040TmpFileUse(int ntp040TmpFileUse) {
        ntp040TmpFileUse__ = ntp040TmpFileUse;
    }

    /**
     * <p>ntp040AnkenCompanyUse を取得します。
     * @return ntp040AnkenCompanyUse
     */
    public int getNtp040AnkenCompanyUse() {
        return ntp040AnkenCompanyUse__;
    }

    /**
     * <p>ntp040AnkenCompanyUse をセットします。
     * @param ntp040AnkenCompanyUse ntp040AnkenCompanyUse
     */
    public void setNtp040AnkenCompanyUse(int ntp040AnkenCompanyUse) {
        ntp040AnkenCompanyUse__ = ntp040AnkenCompanyUse;
    }

    /**
     * <p>ntp040AnkenSid を取得します。
     * @return ntp040AnkenSid
     */
    public int getNtp040AnkenSid() {
        return ntp040AnkenSid__;
    }

    /**
     * <p>ntp040AnkenSid をセットします。
     * @param ntp040AnkenSid ntp040AnkenSid
     */
    public void setNtp040AnkenSid(int ntp040AnkenSid) {
        ntp040AnkenSid__ = ntp040AnkenSid;
    }

    /**
     * <p>ntp040AnkenName を取得します。
     * @return ntp040AnkenName
     */
    public String getNtp040AnkenName() {
        return ntp040AnkenName__;
    }

    /**
     * <p>ntp040AnkenName をセットします。
     * @param ntp040AnkenName ntp040AnkenName
     */
    public void setNtp040AnkenName(String ntp040AnkenName) {
        ntp040AnkenName__ = ntp040AnkenName;
    }

    /**
     * <p>ntp040AnkenCode を取得します。
     * @return ntp040AnkenCode
     */
    public String getNtp040AnkenCode() {
        return ntp040AnkenCode__;
    }

    /**
     * <p>ntp040AnkenCode をセットします。
     * @param ntp040AnkenCode ntp040AnkenCode
     */
    public void setNtp040AnkenCode(String ntp040AnkenCode) {
        ntp040AnkenCode__ = ntp040AnkenCode;
    }

    /**
     * <p>ntp040CompanySid を取得します。
     * @return ntp040CompanySid
     */
    public int getNtp040CompanySid() {
        return ntp040CompanySid__;
    }

    /**
     * <p>ntp040CompanySid をセットします。
     * @param ntp040CompanySid ntp040CompanySid
     */
    public void setNtp040CompanySid(int ntp040CompanySid) {
        ntp040CompanySid__ = ntp040CompanySid;
    }

    /**
     * <p>ntp040CompanyBaseSid を取得します。
     * @return ntp040CompanyBaseSid
     */
    public int getNtp040CompanyBaseSid() {
        return ntp040CompanyBaseSid__;
    }

    /**
     * <p>ntp040CompanyBaseSid をセットします。
     * @param ntp040CompanyBaseSid ntp040CompanyBaseSid
     */
    public void setNtp040CompanyBaseSid(int ntp040CompanyBaseSid) {
        ntp040CompanyBaseSid__ = ntp040CompanyBaseSid;
    }

    /**
     * <p>ntp040CompanyName を取得します。
     * @return ntp040CompanyName
     */
    public String getNtp040CompanyName() {
        return ntp040CompanyName__;
    }

    /**
     * <p>ntp040CompanyName をセットします。
     * @param ntp040CompanyName ntp040CompanyName
     */
    public void setNtp040CompanyName(String ntp040CompanyName) {
        ntp040CompanyName__ = ntp040CompanyName;
    }

    /**
     * <p>ntp040CompanyCode を取得します。
     * @return ntp040CompanyCode
     */
    public String getNtp040CompanyCode() {
        return ntp040CompanyCode__;
    }

    /**
     * <p>ntp040CompanyCode をセットします。
     * @param ntp040CompanyCode ntp040CompanyCode
     */
    public void setNtp040CompanyCode(String ntp040CompanyCode) {
        ntp040CompanyCode__ = ntp040CompanyCode;
    }

    /**
     * <p>ntp040CompanyBaseName を取得します。
     * @return ntp040CompanyBaseName
     */
    public String getNtp040CompanyBaseName() {
        return ntp040CompanyBaseName__;
    }

    /**
     * <p>ntp040CompanyBaseName をセットします。
     * @param ntp040CompanyBaseName ntp040CompanyBaseName
     */
    public void setNtp040CompanyBaseName(String ntp040CompanyBaseName) {
        ntp040CompanyBaseName__ = ntp040CompanyBaseName;
    }

    /**
     * <p>ntp040AdrHistoryPageNum を取得します。
     * @return ntp040AdrHistoryPageNum
     */
    public int getNtp040AdrHistoryPageNum() {
        return ntp040AdrHistoryPageNum__;
    }

    /**
     * <p>ntp040AdrHistoryPageNum をセットします。
     * @param ntp040AdrHistoryPageNum ntp040AdrHistoryPageNum
     */
    public void setNtp040AdrHistoryPageNum(int ntp040AdrHistoryPageNum) {
        ntp040AdrHistoryPageNum__ = ntp040AdrHistoryPageNum;
    }

    /**
     * <p>ntp040DefaultValue を取得します。
     * @return ntp040DefaultValue
     */
    public String getNtp040DefaultValue() {
        return ntp040DefaultValue__;
    }

    /**
     * <p>ntp040DefaultValue をセットします。
     * @param ntp040DefaultValue ntp040DefaultValue
     */
    public void setNtp040DefaultValue(String ntp040DefaultValue) {
        ntp040DefaultValue__ = ntp040DefaultValue;
    }

    /**
     * <p>ntp040DefaultValue2 を取得します。
     * @return ntp040DefaultValue2
     */
    public String getNtp040DefaultValue2() {
        return ntp040DefaultValue2__;
    }

    /**
     * <p>ntp040DefaultValue2 をセットします。
     * @param ntp040DefaultValue2 ntp040DefaultValue2
     */
    public void setNtp040DefaultValue2(String ntp040DefaultValue2) {
        ntp040DefaultValue2__ = ntp040DefaultValue2;
    }

    /**
     * <p>ntp040InitYear を取得します。
     * @return ntp040InitYear
     */
    public String getNtp040InitYear() {
        return ntp040InitYear__;
    }

    /**
     * <p>ntp040InitYear をセットします。
     * @param ntp040InitYear ntp040InitYear
     */
    public void setNtp040InitYear(String ntp040InitYear) {
        ntp040InitYear__ = ntp040InitYear;
    }

    /**
     * <p>ntp040InitMonth を取得します。
     * @return ntp040InitMonth
     */
    public String getNtp040InitMonth() {
        return ntp040InitMonth__;
    }

    /**
     * <p>ntp040InitMonth をセットします。
     * @param ntp040InitMonth ntp040InitMonth
     */
    public void setNtp040InitMonth(String ntp040InitMonth) {
        ntp040InitMonth__ = ntp040InitMonth;
    }

    /**
     * <p>ntp040InitDay を取得します。
     * @return ntp040InitDay
     */
    public String getNtp040InitDay() {
        return ntp040InitDay__;
    }

    /**
     * <p>ntp040InitDay をセットします。
     * @param ntp040InitDay ntp040InitDay
     */
    public void setNtp040InitDay(String ntp040InitDay) {
        ntp040InitDay__ = ntp040InitDay;
    }
    
    /**
     * <p>ntp040InitDate を取得します。
     * @return ntp040InitDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040ParamModel#ntp040InitDate__
     */
    public String getNtp040InitDate() {
        return ntp040InitDate__;
    }

    /**
     * <p>ntp040InitDate をセットします。
     * @param ntp040InitDate ntp040InitDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040ParamModel#ntp040InitDate__
     */
    public void setNtp040InitDate(String ntp040InitDate) {
        ntp040InitDate__ = ntp040InitDate;
    }

    /**
     * <p>ntp040NextAction を取得します。
     * @return ntp040NextAction
     */
    public String getNtp040NextAction() {
        return ntp040NextAction__;
    }

    /**
     * <p>ntp040NextAction をセットします。
     * @param ntp040NextAction ntp040NextAction
     */
    public void setNtp040NextAction(String ntp040NextAction) {
        ntp040NextAction__ = ntp040NextAction;
    }

    /**
     * <p>ntp040NextActionUse を取得します。
     * @return ntp040NextActionUse
     */
    public int getNtp040NextActionUse() {
        return ntp040NextActionUse__;
    }

    /**
     * <p>ntp040NextActionUse をセットします。
     * @param ntp040NextActionUse ntp040NextActionUse
     */
    public void setNtp040NextActionUse(int ntp040NextActionUse) {
        ntp040NextActionUse__ = ntp040NextActionUse;
    }

    /**
     * <p>ntp040NxtActYear を取得します。
     * @return ntp040NxtActYear
     */
    public String getNtp040NxtActYear() {
        return ntp040NxtActYear__;
    }

    /**
     * <p>ntp040NxtActYear をセットします。
     * @param ntp040NxtActYear ntp040NxtActYear
     */
    public void setNtp040NxtActYear(String ntp040NxtActYear) {
        ntp040NxtActYear__ = ntp040NxtActYear;
    }

    /**
     * <p>ntp040NxtActMonth を取得します。
     * @return ntp040NxtActMonth
     */
    public String getNtp040NxtActMonth() {
        return ntp040NxtActMonth__;
    }

    /**
     * <p>ntp040NxtActMonth をセットします。
     * @param ntp040NxtActMonth ntp040NxtActMonth
     */
    public void setNtp040NxtActMonth(String ntp040NxtActMonth) {
        ntp040NxtActMonth__ = ntp040NxtActMonth;
    }

    /**
     * <p>ntp040NxtActDay を取得します。
     * @return ntp040NxtActDay
     */
    public String getNtp040NxtActDay() {
        return ntp040NxtActDay__;
    }

    /**
     * <p>ntp040NxtActDay をセットします。
     * @param ntp040NxtActDay ntp040NxtActDay
     */
    public void setNtp040NxtActDay(String ntp040NxtActDay) {
        ntp040NxtActDay__ = ntp040NxtActDay;
    }
    
    /**
     * <p>ntp040NxtActDate を取得します。
     * @return ntp040NxtActDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040ParamModel#ntp040NxtActDate__
     */
    public String getNtp040NxtActDate() {
        return ntp040NxtActDate__;
    }

    /**
     * <p>ntp040NxtActDate をセットします。
     * @param ntp040NxtActDate ntp040NxtActDate
     * @see jp.groupsession.v2.ntp.ntp040.Ntp040ParamModel#ntp040NxtActDate__
     */
    public void setNtp040NxtActDate(String ntp040NxtActDate) {
        ntp040NxtActDate__ = ntp040NxtActDate;
    }

    /**
     * <p>ntp040ActDateKbn を取得します。
     * @return ntp040ActDateKbn
     */
    public int getNtp040ActDateKbn() {
        return ntp040ActDateKbn__;
    }

    /**
     * <p>ntp040ActDateKbn をセットします。
     * @param ntp040ActDateKbn ntp040ActDateKbn
     */
    public void setNtp040ActDateKbn(int ntp040ActDateKbn) {
        ntp040ActDateKbn__ = ntp040ActDateKbn;
    }

    /**
     * <p>ntp040schUrl を取得します。
     * @return ntp040schUrl
     */
    public String getNtp040schUrl() {
        return ntp040schUrl__;
    }

    /**
     * <p>ntp040schUrl をセットします。
     * @param ntp040schUrl ntp040schUrl
     */
    public void setNtp040schUrl(String ntp040schUrl) {
        ntp040schUrl__ = ntp040schUrl;
    }

    /**
     * <p>ntp040100SvSearchTarget を取得します。
     * @return ntp040100SvSearchTarget
     */
    public String getNtp040100SvSearchTarget() {
        return ntp040100SvSearchTarget__;
    }

    /**
     * <p>ntp040100SvSearchTarget をセットします。
     * @param ntp040100SvSearchTarget ntp040100SvSearchTarget
     */
    public void setNtp040100SvSearchTarget(String ntp040100SvSearchTarget) {
        ntp040100SvSearchTarget__ = ntp040100SvSearchTarget;
    }

    /**
     * <p>ntp040100SearchTarget を取得します。
     * @return ntp040100SearchTarget
     */
    public String getNtp040100SearchTarget() {
        return ntp040100SearchTarget__;
    }

    /**
     * <p>ntp040100SearchTarget をセットします。
     * @param ntp040100SearchTarget ntp040100SearchTarget
     */
    public void setNtp040100SearchTarget(String ntp040100SearchTarget) {
        ntp040100SearchTarget__ = ntp040100SearchTarget;
    }

    /**
     * <p>ntp040100SvBgcolor を取得します。
     * @return ntp040100SvBgcolor
     */
    public String getNtp040100SvBgcolor() {
        return ntp040100SvBgcolor__;
    }

    /**
     * <p>ntp040100SvBgcolor をセットします。
     * @param ntp040100SvBgcolor ntp040100SvBgcolor
     */
    public void setNtp040100SvBgcolor(String ntp040100SvBgcolor) {
        ntp040100SvBgcolor__ = ntp040100SvBgcolor;
    }

    /**
     * <p>ntp040100Bgcolor を取得します。
     * @return ntp040100Bgcolor
     */
    public String getNtp040100Bgcolor() {
        return ntp040100Bgcolor__;
    }

    /**
     * <p>ntp040100Bgcolor をセットします。
     * @param ntp040100Bgcolor ntp040100Bgcolor
     */
    public void setNtp040100Bgcolor(String ntp040100Bgcolor) {
        ntp040100Bgcolor__ = ntp040100Bgcolor;
    }

    /**
     * <p>ntp040100SvMikomido を取得します。
     * @return ntp040100SvMikomido
     */
    public String getNtp040100SvMikomido() {
        return ntp040100SvMikomido__;
    }

    /**
     * <p>ntp040100SvMikomido をセットします。
     * @param ntp040100SvMikomido ntp040100SvMikomido
     */
    public void setNtp040100SvMikomido(String ntp040100SvMikomido) {
        ntp040100SvMikomido__ = ntp040100SvMikomido;
    }

    /**
     * <p>ntp040100Mikomido を取得します。
     * @return ntp040100Mikomido
     */
    public String getNtp040100Mikomido() {
        return ntp040100Mikomido__;
    }

    /**
     * <p>ntp040100Mikomido をセットします。
     * @param ntp040100Mikomido ntp040100Mikomido
     */
    public void setNtp040100Mikomido(String ntp040100Mikomido) {
        ntp040100Mikomido__ = ntp040100Mikomido;
    }

    /**
     * <p>ntp040YearLavelStr を取得します。
     * @return ntp040YearLavelStr
     */
    public String getNtp040YearLavelStr() {
        return ntp040YearLavelStr__;
    }

    /**
     * <p>ntp040YearLavelStr をセットします。
     * @param ntp040YearLavelStr ntp040YearLavelStr
     */
    public void setNtp040YearLavelStr(String ntp040YearLavelStr) {
        ntp040YearLavelStr__ = ntp040YearLavelStr;
    }

    /**
     * <p>ntp040MonthLavelStr を取得します。
     * @return ntp040MonthLavelStr
     */
    public String getNtp040MonthLavelStr() {
        return ntp040MonthLavelStr__;
    }

    /**
     * <p>ntp040MonthLavelStr をセットします。
     * @param ntp040MonthLavelStr ntp040MonthLavelStr
     */
    public void setNtp040MonthLavelStr(String ntp040MonthLavelStr) {
        ntp040MonthLavelStr__ = ntp040MonthLavelStr;
    }

    /**
     * <p>ntp040DayLavelStr を取得します。
     * @return ntp040DayLavelStr
     */
    public String getNtp040DayLavelStr() {
        return ntp040DayLavelStr__;
    }

    /**
     * <p>ntp040DayLavelStr をセットします。
     * @param ntp040DayLavelStr ntp040DayLavelStr
     */
    public void setNtp040DayLavelStr(String ntp040DayLavelStr) {
        ntp040DayLavelStr__ = ntp040DayLavelStr;
    }

    /**
     * <p>ntp040HourLavelStr を取得します。
     * @return ntp040HourLavelStr
     */
    public String getNtp040HourLavelStr() {
        return ntp040HourLavelStr__;
    }

    /**
     * <p>ntp040HourLavelStr をセットします。
     * @param ntp040HourLavelStr ntp040HourLavelStr
     */
    public void setNtp040HourLavelStr(String ntp040HourLavelStr) {
        ntp040HourLavelStr__ = ntp040HourLavelStr;
    }

    /**
     * <p>ntp040MinuteLavelStr を取得します。
     * @return ntp040MinuteLavelStr
     */
    public String getNtp040MinuteLavelStr() {
        return ntp040MinuteLavelStr__;
    }

    /**
     * <p>ntp040MinuteLavelStr をセットします。
     * @param ntp040MinuteLavelStr ntp040MinuteLavelStr
     */
    public void setNtp040MinuteLavelStr(String ntp040MinuteLavelStr) {
        ntp040MinuteLavelStr__ = ntp040MinuteLavelStr;
    }

    /**
     * <p>ntp040KtbunruiLavelStr を取得します。
     * @return ntp040KtbunruiLavelStr
     */
    public String getNtp040KtbunruiLavelStr() {
        return ntp040KtbunruiLavelStr__;
    }

    /**
     * <p>ntp040KtbunruiLavelStr をセットします。
     * @param ntp040KtbunruiLavelStr ntp040KtbunruiLavelStr
     */
    public void setNtp040KtbunruiLavelStr(String ntp040KtbunruiLavelStr) {
        ntp040KtbunruiLavelStr__ = ntp040KtbunruiLavelStr;
    }

    /**
     * <p>ntp040KthouhouLavelStr を取得します。
     * @return ntp040KthouhouLavelStr
     */
    public String getNtp040KthouhouLavelStr() {
        return ntp040KthouhouLavelStr__;
    }

    /**
     * <p>ntp040KthouhouLavelStr をセットします。
     * @param ntp040KthouhouLavelStr ntp040KthouhouLavelStr
     */
    public void setNtp040KthouhouLavelStr(String ntp040KthouhouLavelStr) {
        ntp040KthouhouLavelStr__ = ntp040KthouhouLavelStr;
    }

    /**
     * <p>ntp040UsrBinSid を取得します。
     * @return ntp040UsrBinSid
     */
    public Long getNtp040UsrBinSid() {
        return ntp040UsrBinSid__;
    }

    /**
     * <p>ntp040UsrBinSid をセットします。
     * @param ntp040UsrBinSid ntp040UsrBinSid
     */
    public void setNtp040UsrBinSid(Long ntp040UsrBinSid) {
        ntp040UsrBinSid__ = ntp040UsrBinSid;
    }

    /**
     * <p>ntp040UsrPctKbn を取得します。
     * @return ntp040UsrPctKbn
     */
    public int getNtp040UsrPctKbn() {
        return ntp040UsrPctKbn__;
    }

    /**
     * <p>ntp040UsrPctKbn をセットします。
     * @param ntp040UsrPctKbn ntp040UsrPctKbn
     */
    public void setNtp040UsrPctKbn(int ntp040UsrPctKbn) {
        ntp040UsrPctKbn__ = ntp040UsrPctKbn;
    }

    /**
     * <p>ntp040NextNtpSid を取得します。
     * @return ntp040NextNtpSid
     */
    public int getNtp040NextNtpSid() {
        return ntp040NextNtpSid__;
    }

    /**
     * <p>ntp040NextNtpSid をセットします。
     * @param ntp040NextNtpSid ntp040NextNtpSid
     */
    public void setNtp040NextNtpSid(int ntp040NextNtpSid) {
        ntp040NextNtpSid__ = ntp040NextNtpSid;
    }

    /**
     * <p>ntp040PrevNtpSid を取得します。
     * @return ntp040PrevNtpSid
     */
    public int getNtp040PrevNtpSid() {
        return ntp040PrevNtpSid__;
    }

    /**
     * <p>ntp040PrevNtpSid をセットします。
     * @param ntp040PrevNtpSid ntp040PrevNtpSid
     */
    public void setNtp040PrevNtpSid(int ntp040PrevNtpSid) {
        ntp040PrevNtpSid__ = ntp040PrevNtpSid;
    }

    /**
     * <p>ntp040PrevNextKbn を取得します。
     * @return ntp040PrevNextKbn
     */
    public int getNtp040PrevNextKbn() {
        return ntp040PrevNextKbn__;
    }

    /**
     * <p>ntp040PrevNextKbn をセットします。
     * @param ntp040PrevNextKbn ntp040PrevNextKbn
     */
    public void setNtp040PrevNextKbn(int ntp040PrevNextKbn) {
        ntp040PrevNextKbn__ = ntp040PrevNextKbn;
    }

    /**
     * <p>ntp040PrevNtpDate を取得します。
     * @return ntp040PrevNtpDate
     */
    public String getNtp040PrevNtpDate() {
        return ntp040PrevNtpDate__;
    }

    /**
     * <p>ntp040PrevNtpDate をセットします。
     * @param ntp040PrevNtpDate ntp040PrevNtpDate
     */
    public void setNtp040PrevNtpDate(String ntp040PrevNtpDate) {
        ntp040PrevNtpDate__ = ntp040PrevNtpDate;
    }

    /**
     * <p>ntp040NextNtpDate を取得します。
     * @return ntp040NextNtpDate
     */
    public String getNtp040NextNtpDate() {
        return ntp040NextNtpDate__;
    }

    /**
     * <p>ntp040NextNtpDate をセットします。
     * @param ntp040NextNtpDate ntp040NextNtpDate
     */
    public void setNtp040NextNtpDate(String ntp040NextNtpDate) {
        ntp040NextNtpDate__ = ntp040NextNtpDate;
    }

    /**
     * <p>ntp040DspDateKbnStr を取得します。
     * @return ntp040DspDateKbnStr
     */
    public String getNtp040DspDateKbnStr() {
        return ntp040DspDateKbnStr__;
    }

    /**
     * <p>ntp040DspDateKbnStr をセットします。
     * @param ntp040DspDateKbnStr ntp040DspDateKbnStr
     */
    public void setNtp040DspDateKbnStr(String ntp040DspDateKbnStr) {
        ntp040DspDateKbnStr__ = ntp040DspDateKbnStr;
    }

    /**
     * <p>ntp040DspDateKbn を取得します。
     * @return ntp040DspDateKbn
     */
    public String getNtp040DspDateKbn() {
        return ntp040DspDateKbn__;
    }

    /**
     * <p>ntp040DspDateKbn をセットします。
     * @param ntp040DspDateKbn ntp040DspDateKbn
     */
    public void setNtp040DspDateKbn(String ntp040DspDateKbn) {
        ntp040DspDateKbn__ = ntp040DspDateKbn;
    }

    /**
     * <p>ntp040TodayNtpSid を取得します。
     * @return ntp040TodayNtpSid
     */
    public int getNtp040TodayNtpSid() {
        return ntp040TodayNtpSid__;
    }

    /**
     * <p>ntp040TodayNtpSid をセットします。
     * @param ntp040TodayNtpSid ntp040TodayNtpSid
     */
    public void setNtp040TodayNtpSid(int ntp040TodayNtpSid) {
        ntp040TodayNtpSid__ = ntp040TodayNtpSid;
    }

    /**
     * <p>ntp040TodayNtpDate を取得します。
     * @return ntp040TodayNtpDate
     */
    public String getNtp040TodayNtpDate() {
        return ntp040TodayNtpDate__;
    }

    /**
     * <p>ntp040TodayNtpDate をセットします。
     * @param ntp040TodayNtpDate ntp040TodayNtpDate
     */
    public void setNtp040TodayNtpDate(String ntp040TodayNtpDate) {
        ntp040TodayNtpDate__ = ntp040TodayNtpDate;
    }

    /**
     * <p>ntp040MikomidoMsgList を取得します。
     * @return ntp040MikomidoMsgList
     */
    public ArrayList<NtpMikomidoMsgModel> getNtp040MikomidoMsgList() {
        return ntp040MikomidoMsgList__;
    }

    /**
     * <p>ntp040MikomidoMsgList をセットします。
     * @param ntp040MikomidoMsgList ntp040MikomidoMsgList
     */
    public void setNtp040MikomidoMsgList(
            ArrayList<NtpMikomidoMsgModel> ntp040MikomidoMsgList) {
        ntp040MikomidoMsgList__ = ntp040MikomidoMsgList;
    }

    /**
     * <p>ntp040MikomidoFlg を取得します。
     * @return ntp040MikomidoFlg
     */
    public int getNtp040MikomidoFlg() {
        return ntp040MikomidoFlg__;
    }

    /**
     * <p>ntp040MikomidoFlg をセットします。
     * @param ntp040MikomidoFlg ntp040MikomidoFlg
     */
    public void setNtp040MikomidoFlg(int ntp040MikomidoFlg) {
        ntp040MikomidoFlg__ = ntp040MikomidoFlg;
    }

    /**
     * <p>ntp040DspTargetMdlList を取得します。
     * @return ntp040DspTargetMdlList
     */
    public List<Ntp040DspTargetModel> getNtp040DspTargetMdlList() {
        return ntp040DspTargetMdlList__;
    }

    /**
     * <p>ntp040DspTargetMdlList をセットします。
     * @param ntp040DspTargetMdlList ntp040DspTargetMdlList
     */
    public void setNtp040DspTargetMdlList(
            List<Ntp040DspTargetModel> ntp040DspTargetMdlList) {
        ntp040DspTargetMdlList__ = ntp040DspTargetMdlList;
    }

    /**
     * <p>ntp040AnkenHistoryPageNum を取得します。
     * @return ntp040AnkenHistoryPageNum
     */
    public int getNtp040AnkenHistoryPageNum() {
        return ntp040AnkenHistoryPageNum__;
    }

    /**
     * <p>ntp040AnkenHistoryPageNum をセットします。
     * @param ntp040AnkenHistoryPageNum ntp040AnkenHistoryPageNum
     */
    public void setNtp040AnkenHistoryPageNum(int ntp040AnkenHistoryPageNum) {
        ntp040AnkenHistoryPageNum__ = ntp040AnkenHistoryPageNum;
    }

    /**
     * <p>ntp040BtnCmd を取得します。
     * @return ntp040BtnCmd
     */
    public String getNtp040BtnCmd() {
        return ntp040BtnCmd__;
    }

    /**
     * <p>ntp040BtnCmd をセットします。
     * @param ntp040BtnCmd ntp040BtnCmd
     */
    public void setNtp040BtnCmd(String ntp040BtnCmd) {
        ntp040BtnCmd__ = ntp040BtnCmd;
    }

    /**
     * <p>ntp040UsrUkoFlg を取得します。
     * @return ntp040UsrUkoFlg
     */
    public int getNtp040UsrUkoFlg() {
        return ntp040UsrUkoFlg__;
    }

    /**
     * <p>ntp040UsrUkoFlg をセットします。
     * @param ntp040UsrUkoFlg ntp040UsrUkoFlg
     */
    public void setNtp040UsrUkoFlg(int ntp040UsrUkoFlg) {
        ntp040UsrUkoFlg__ = ntp040UsrUkoFlg;
    }
}