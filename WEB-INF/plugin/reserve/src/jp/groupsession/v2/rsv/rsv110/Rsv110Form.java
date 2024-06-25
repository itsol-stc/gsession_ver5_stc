package jp.groupsession.v2.rsv.rsv110;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.rsv.RsvValidateUtil;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.rsv.rsv030.Rsv030Form;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.rsv.ui.parts.schedule.RsvScheduleSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 施設予約登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110Form extends Rsv030Form implements ISelectorUseForm {

    /** 新規モード時、予約デフォルト日付 */
    private String rsv110SinkiDefaultDate__ = null;
    /** 処理モード */
    private String rsv110ProcMode__ = null;
    /** 初期表示フラグ */
    private boolean rsv110InitFlg__ = true;
    /** 施設予約SID */
    private int rsv110RsySid__ = -1;
    /** 施設SID */
    private int rsv110RsdSid__ = -1;
    /** 所属グループ名称 */
    private String rsv110GrpName__ = null;
    /** 公開区分 */
    private int rsv110Public__ = 0;
    /** 施設区分 */
    private int rsv110SisetuKbn__ = 0;
    /** 施設区分名称 */
    private String rsv110SisetuKbnName__ = null;
    /** 資産管理番号 */
    private String rsv110SisanKanri__ = null;
    /** 施設名称 */
    private String rsv110SisetuName__ = null;
    /** 表示項目1項目名称 */
    private String rsv110PropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsv110PropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsv110PropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsv110PropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsv110PropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsv110PropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsv110PropHeaderName7__ = null;
    /** 表示項目1項目 */
    private String rsv110Prop1Value__ = null;
    /** 表示項目2項目 */
    private String rsv110Prop2Value__ = null;
    /** 表示項目3項目 */
    private String rsv110Prop3Value__ = null;
    /** 表示項目4項目 */
    private String rsv110Prop4Value__ = null;
    /** 表示項目5項目 */
    private String rsv110Prop5Value__ = null;
    /** 表示項目6項目 */
    private String rsv110Prop6Value__ = null;
    /** 表示項目7項目 */
    private String rsv110Prop7Value__ = null;
    /** 備考 */
    private String rsv110Biko__ = null;
    /** 登録者SID */
    private int rsv110AuId__ = 0;
    /** 登録者 */
    private String rsv110Torokusya__ = null;
    /** 更新者SID */
    private int rsv110EuId__ = 0;
    /** 更新者 */
    private String rsv110Koshinsya__ = null;
    /** 更新者削除区分*/
    private int rsv110EditUsrJKbn__ = 0;
    /** 更新者有効フラグ */
    private int rsv110EditUsrUkoFlg__ = 0;
    /** 登録者削除区分*/
    private int rsv110AddUsrJKbn__ = 0;
    /** 登録者有効フラグ */
    private int rsv110AddUsrUkoFlg__ = 0;
    /** 利用目的 */
    private String rsv110Mokuteki__ = null;
    /** 利用目的(エスケープ処理  承認・却下時のメッセージ用) */
    private String rsv110MokutekiEsc__ = null;
    /** 内容 */
    private String rsv110Naiyo__ = null;
    /** 年コンボFr選択値 */
    private int rsv110SelectedYearFr__ = -1;
    /** 年コンボTo選択値 */
    private int rsv110SelectedYearTo__ = -1;
    /** 月コンボFr選択値 */
    private int rsv110SelectedMonthFr__ = -1;
    /** 月コンボTo選択値 */
    private int rsv110SelectedMonthTo__ = -1;
    /** 月コンボリスト */
    private ArrayList<LabelValueBean> rsv110MonthComboList__ = null;
    /** 日コンボFr選択値 */
    private int rsv110SelectedDayFr__ = -1;
    /** 日コンボTo選択値 */
    private int rsv110SelectedDayTo__ = -1;
    /** 年月日Fr選択値 */
    private String rsv110SelectedDateFr__ = null;
    /** 年月日To選択値 */
    private String rsv110SelectedDateTo__ = null;
    /** 時コンボFr選択値 */
    private int rsv110SelectedHourFr__ = -1;
    /** 時コンボTo選択値 */
    private int rsv110SelectedHourTo__ = -1;
    /** 分コンボFr選択値 */
    private int rsv110SelectedMinuteFr__ = -1;
    /** 分コンボTo選択値 */
    private int rsv110SelectedMinuteTo__ = -1;
    /** 時分Fr選択値 */
    private String rsv110SelectedTimeFr__ = null;
    /** 時分To選択値 */
    private String rsv110SelectedTimeTo__ = null;
    /** 時間表示間隔 */
    private int racHourDiv__ =  GSConstReserve.DF_HOUR_DIVISION;
    /** 予約開始(表示形式) */
    private String yoyakuFrString__ = null;
    /** 予約終了(表示形式) */
    private String yoyakuToString__ = null;
    /** 編集権限 */
    private int rsv110RsyEdit__ = GSConstReserve.EDIT_AUTH_NONE;
    /** 編集モード時、変更権限有り・無し */
    private boolean rsv110EditAuth__ = true;
    /** スケジュールリレーションSID */
    private int rsv110ScdRsSid__;
    /** ヘッダ部表示非表示フラグ */
    private String rsv110HeaderDspFlg__ = "1";
    /** 反映可否 */
    private int rsv110ScdReflection__ = GSConstReserve.SCD_REFLECTION_OK;
    /** 反映可否 */
    private int rsv111ScdReflection__ = GSConstReserve.SCD_REFLECTION_OK;
    /** 施設予約登録日時 */
    private String rsv110AddDate__ = null;
    /** 施設予約更新日時 */
    private String rsv110EditDate__ = null;
    /** 施設予約と関連したスケジュールデータ存在フラグ */
    private boolean rsv110ExistSchDateFlg__ = false;
    /** 却下した施設予約を削除 */
    private int rsv110rejectDel__ = 0;
    /** 承認ボタン表示フラグ */
    private int rsv110ApprBtnFlg__ = 1;

    //----------- 施設区分別情報 ----------------------------
    /** 担当部署 */
    private String rsv110Busyo__ = null;
    /** 担当・使用者名 */
    private String rsv110UseName__ = null;
    /** 人数 */
    private String rsv110UseNum__ = null;
    /** 区分 (0:未設定 1:会議 2:研修 3:その他)*/
    private int rsv110UseKbn__ = GSConstReserve.RSY_USE_KBN_NOSET;
    /** 連絡先 */
    private String rsv110Contact__ = null;
    /** 会議名案内 */
    private String rsv110Guide__ = null;
    /** 駐車場見込み台数 */
    private String rsv110ParkNum__ = null;
    /** 印刷区分 */
    private int rsv110PrintKbn__ = 0;
    /** 行き先 */
    private String rsv110Dest__ = null;

    //----------- スケジュール同時登録 ----------------------------
    /** スケジュールプラグイン使用有無 0=使用 1=未使用 */
    private int schedulePluginKbn__;
    /** スケジュール共有範囲  0=共有範囲制限なし 1=所属グループのみ */
    private int rsv110SchCrangeKbn__;

    /** スケジュール登録区分 0:ユーザ 1:グループ */
    private int rsv110SchKbn__ = GSConstReserve.RSV_SCHKBN_USER;

    /** スケジュール登録 対象ユーザ グループ */
    private String rsv110GroupSid__ = null;
    /** スケジュール登録 対象ユーザ 選択済みユーザ */
    private String[] sv_users__ = null;
    /** スケジュール登録 対象ユーザ 選択済みユーザ UI */
    private RsvScheduleSelector rsv110SchUserUI__ =
            RsvScheduleSelector.builder()
            .chainLabel(new GsMessageReq("schedule.3", null))
            .chainType(EnumSelectType.USER)
            .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
            .chainSelect(
                    Select.builder()
                    .chainLabel(new GsMessageReq("cmn.target.user", null))
                    .chainParameterName(
                            "sv_users")
                )
            .chainGroupSelectionParamName("rsv110GroupSid")
            .build();


    /** 同時登録スケジュールグループSID */
    private String rsv110SchGroupSid__ = "-1";
    /** 同時登録スケジュールグループリスト */
    private List<ExtendedLabelValueModel> rsv110SchGroupLabel__ = null;

    /** スケジュールの閲覧を許可しないグループの一覧 */
    private List<Integer> rsv110SchNotAccessGroupList__;

    /** デフォルトユーザSID  */
    private int rsv110PubDefUsrSid__ = 0;
    /** 公開対象 ユーザ・グループリスト(表示用) */
    private List <UsrLabelValueBean> rsv110PubUsrGrpList__ = null;

    /** 公開対象 グループ */
    private String rsv110PubUserGroup__ = 
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象 ユーザ・グループSID */
    private String[] rsv110PubUsrGrpSid__ = new String[0];
    /** 公開対象ユーザ・グループ UI */
    private UserGroupSelector rsv110PubUsrGrpUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("main.exposed", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(
                    Select.builder()
                    .chainParameterName(
                            "rsv110PubUsrGrpSid")
                )
            .chainGroupSelectionParamName("rsv110PubUserGroup")
            .build();

    /** スケジュール登録区分 0:ユーザ 1:グループ */
    private int rsv111SchKbn__ = GSConstReserve.RSV_SCHKBN_USER;
    /** 同時登録スケジュールグループSID */
    private String rsv111SchGroupSid__ = "-1";

    /** 繰り返し登録 スケジュール登録 対象ユーザ グループ */
    private String rsv111GroupSid__ = null;
    /** 繰り返し登録 スケジュール登録 対象ユーザ 選択済みユーザ */
    private String[] rsv111SvUsers__ = null;

    /** スケジュール作成ユーザ/グループ名称 */
    private ArrayList<UsrLabelValueBean> rsvSchUserNameArray__ = null;
    //-------------------------------------------------


    /****************************************
    *
    *  繰返し登録情報
    *
    ****************************************/

    /** 拡張画面 初期表示フラグ */
    private boolean rsv111InitFlg__ = true;
    /** 施設予約拡張SID */
    private int rsv111RsrRsid__ = -1;
    /** 区分 */
    private int rsv111RsrKbn__;
    /** 曜日(日曜) */
    private int rsv111RsrDweek1__;
    /** 曜日(月曜) */
    private int rsv111RsrDweek2__;
    /** 曜日(火曜) */
    private int rsv111RsrDweek3__;
    /** 曜日(水曜) */
    private int rsv111RsrDweek4__;
    /** 曜日(木曜) */
    private int rsv111RsrDweek5__;
    /** 曜日(金曜) */
    private int rsv111RsrDweek6__;
    /** 曜日(土曜) */
    private int rsv111RsrDweek7__;
    /** 毎年 月 */
    private int rsv111RsrMonthOfYearly__;
    /** 毎年 日 */
    private int rsv111RsrDayOfYearly__;
    /** 日 */
    private int rsv111RsrDay__;
    /** 週 */
    private int rsv111RsrWeek__;
    /** 反映期間from 年 */
    private String rsv111RsrDateYearFr__;
    /** 反映期間from 月 */
    private String rsv111RsrDateMonthFr__;
    /** 反映期間from 日 */
    private String rsv111RsrDateDayFr__;
    /** 反映期間from 年月日 */
    private String rsv111RsrDateFr__;
    /** 反映期間To 年 */
    private String rsv111RsrDateYearTo__;
    /** 反映期間To 月 */
    private String rsv111RsrDateMonthTo__;
    /** 反映期間To 日 */
    private String rsv111RsrDateDayTo__;
    /** 反映期間To 年月日 */
    private String rsv111RsrDateTo__;
    /** 時間from 時 */
    private String rsv111RsrTimeHourFr__;
    /** 時間from 分 */
    private String rsv111RsrTimeMinuteFr__;
    /** 時間from 時分 */
    private String rsv111RsrTimeFr__;
    /** 時間To 時 */
    private String rsv111RsrTimeHourTo__;
    /** 時間To 分 */
    private String rsv111RsrTimeMinuteTo__;
    /** 時間To 時分 */
    private String rsv111RsrTimeTo__;
    /** 振替区分 */
    private int rsv111RsrTranKbn__;
    /** 利用目的 */
    private String rsv111RsrMok__;
    /** 内容 */
    private String rsv111RsrBiko__;
    /** 権限設定 */
    private int rsv111RsrEdit__ = GSConstReserve.EDIT_AUTH_NONE;
    /** 公開区分 */
    private int rsv111RsrPublic__ = GSConstReserve.PUBLIC_KBN_ALL;

    /** 公開対象 ユーザ グループ */
    private String rsv111PubUserGroup__ = 
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象ユーザ・グループSID */
    private String[] rsv111PubUsrGrpSid__ = new String[0];

    /** 担当部署 */
    private String rsv111Busyo__ = null;
    /** 担当・使用者名 */
    private String rsv111UseName__ = null;
    /** 人数 */
    private String rsv111UseNum__ = null;
    /** 区分 (0:未設定 1:会議 2:研修 3:その他)*/
    private int rsv111UseKbn__ = GSConstReserve.RSY_USE_KBN_NOSET;
    /** 連絡先 */
    private String rsv111Contact__ = null;
    /** 会議名案内 */
    private String rsv111Guide__ = null;
    /** 駐車場見込み台数 */
    private String rsv111ParkNum__ = null;
    /** 印刷区分 */
    private int rsv111PrintKbn__ = 0;
    /** 行き先 */
    private String rsv111Dest__ = null;

    /** ショートメールの通知リンクからの遷移 */
    private boolean smailSeniFlg__ = false;

    /** データ存在フラグ */
    private boolean rsvDataFlg__ = false;

    /** 午前開始時 */
    private int rsv110AmFrHour__;
    /** 午前開始分 */
    private int rsv110AmFrMin__;
    /** 午前終了時 */
    private int rsv110AmToHour__;
    /** 午前終了分 */
    private int rsv110AmToMin__;

    /** 午後開始時 */
    private int rsv110PmFrHour__;
    /** 午後開始分 */
    private int rsv110PmFrMin__;
    /** 午後終了時 */
    private int rsv110PmToHour__;
    /** 午後終了分 */
    private int rsv110PmToMin__;

    /** 終日開始時 */
    private int rsv110AllDayFrHour__;
    /** 終日開始分 */
    private int rsv110AllDayFrMin__;
    /** 終日終了時 */
    private int rsv110AllDayToHour__;
    /** 終日終了分 */
    private int rsv110AllDayToMin__;

    /**
     * <p>rsv110ScdReflection__ を取得します。
     * @return rsv110ScdReflection
     */
    public int getRsv110ScdReflection() {
        return rsv110ScdReflection__;
    }
    /**
     * <p>rsv110ScdReflection__ をセットします。
     * @param rsv110ScdReflection rsv110ScdReflection__
     */
    public void setRsv110ScdReflection(int rsv110ScdReflection) {
        rsv110ScdReflection__ = rsv110ScdReflection;
    }
    /**
     * <p>rsv110ScdRsSid__ を取得します。
     * @return rsv110ScdRsSid
     */
    public int getRsv110ScdRsSid() {
        return rsv110ScdRsSid__;
    }
    /**
     * <p>rsv110ScdRsSid__ をセットします。
     * @param rsv110ScdRsSid rsv110ScdRsSid__
     */
    public void setRsv110ScdRsSid(int rsv110ScdRsSid) {
        rsv110ScdRsSid__ = rsv110ScdRsSid;
    }
    /**
     * <p>rsv110EditAuth__ を取得します。
     * @return rsv110EditAuth
     */
    public boolean isRsv110EditAuth() {
        return rsv110EditAuth__;
    }
    /**
     * <p>rsv110EditAuth__ をセットします。
     * @param rsv110EditAuth rsv110EditAuth__
     */
    public void setRsv110EditAuth(boolean rsv110EditAuth) {
        rsv110EditAuth__ = rsv110EditAuth;
    }
    /**
     * <p>rsv110RsyEdit__ を取得します。
     * @return rsv110RsyEdit
     */
    public int getRsv110RsyEdit() {
        return rsv110RsyEdit__;
    }
    /**
     * <p>rsv110RsyEdit__ をセットします。
     * @param rsv110RsyEdit rsv110RsyEdit__
     */
    public void setRsv110RsyEdit(int rsv110RsyEdit) {
        rsv110RsyEdit__ = rsv110RsyEdit;
    }
    /**
     * <p>rsv110SinkiDefaultDate__ を取得します。
     * @return rsv110SinkiDefaultDate
     */
    public String getRsv110SinkiDefaultDate() {
        return rsv110SinkiDefaultDate__;
    }
    /**
     * <p>rsv110SinkiDefaultDate__ をセットします。
     * @param rsv110SinkiDefaultDate rsv110SinkiDefaultDate__
     */
    public void setRsv110SinkiDefaultDate(String rsv110SinkiDefaultDate) {
        rsv110SinkiDefaultDate__ = rsv110SinkiDefaultDate;
    }
    /**
     * <p>rsv110InitFlg__ を取得します。
     * @return rsv110InitFlg
     */
    public boolean isRsv110InitFlg() {
        return rsv110InitFlg__;
    }
    /**
     * <p>rsv110InitFlg__ をセットします。
     * @param rsv110InitFlg rsv110InitFlg__
     */
    public void setRsv110InitFlg(boolean rsv110InitFlg) {
        rsv110InitFlg__ = rsv110InitFlg;
    }
    /**
     * <p>rsv110ProcMode__ を取得します。
     * @return rsv110ProcMode
     */
    public String getRsv110ProcMode() {
        return rsv110ProcMode__;
    }
    /**
     * <p>rsv110ProcMode__ をセットします。
     * @param rsv110ProcMode rsv110ProcMode__
     */
    public void setRsv110ProcMode(String rsv110ProcMode) {
        rsv110ProcMode__ = rsv110ProcMode;
    }
    /**
     * <p>rsv110RsdSid__ を取得します。
     * @return rsv110RsdSid
     */
    public int getRsv110RsdSid() {
        return rsv110RsdSid__;
    }
    /**
     * <p>rsv110RsdSid__ をセットします。
     * @param rsv110RsdSid rsv110RsdSid__
     */
    public void setRsv110RsdSid(int rsv110RsdSid) {
        rsv110RsdSid__ = rsv110RsdSid;
    }
    /**
     * <p>rsv110RsySid__ を取得します。
     * @return rsv110RsySid
     */
    public int getRsv110RsySid() {
        return rsv110RsySid__;
    }
    /**
     * <p>rsv110RsySid__ をセットします。
     * @param rsv110RsySid rsv110RsySid__
     */
    public void setRsv110RsySid(int rsv110RsySid) {
        rsv110RsySid__ = rsv110RsySid;
    }
    /**
     * <p>rsv110Biko__ を取得します。
     * @return rsv110Biko
     */
    public String getRsv110Biko() {
        return rsv110Biko__;
    }
    /**
     * <p>rsv110Biko__ をセットします。
     * @param rsv110Biko rsv110Biko__
     */
    public void setRsv110Biko(String rsv110Biko) {
        rsv110Biko__ = rsv110Biko;
    }
    /**
     * <p>rsv110GrpName__ を取得します。
     * @return rsv110GrpName
     */
    public String getRsv110GrpName() {
        return rsv110GrpName__;
    }
    /**
     * <p>rsv110GrpName__ をセットします。
     * @param rsv110GrpName rsv110GrpName__
     */
    public void setRsv110GrpName(String rsv110GrpName) {
        rsv110GrpName__ = rsv110GrpName;
    }
    /**
     * rsv110Publicを取得します。
     * @return rsv110Public
     * */
    public int getRsv110Public() {
        return rsv110Public__;
    }
    /**
     * rsv110Publicをセットします。
     * @param rsv110Public rsv110Public
     * */
    public void setRsv110Public(int rsv110Public) {
        rsv110Public__ = rsv110Public;
    }
    /**
     * <p>rsv110Mokuteki__ を取得します。
     * @return rsv110Mokuteki
     */
    public String getRsv110Mokuteki() {
        return rsv110Mokuteki__;
    }
    /**
     * <p>rsv110Mokuteki__ をセットします。
     * @param rsv110Mokuteki rsv110Mokuteki__
     */
    public void setRsv110Mokuteki(String rsv110Mokuteki) {
        rsv110Mokuteki__ = rsv110Mokuteki;
    }
    /**
     * <p>rsv110MonthComboList__ を取得します。
     * @return rsv110MonthComboList
     */
    public ArrayList<LabelValueBean> getRsv110MonthComboList() {
        return rsv110MonthComboList__;
    }
    /**
     * <p>rsv110MonthComboList__ をセットします。
     * @param rsv110MonthComboList rsv110MonthComboList__
     */
    public void setRsv110MonthComboList(
            ArrayList<LabelValueBean> rsv110MonthComboList) {
        rsv110MonthComboList__ = rsv110MonthComboList;
    }
    /**
     * <p>rsv110Naiyo__ を取得します。
     * @return rsv110Naiyo
     */
    public String getRsv110Naiyo() {
        return rsv110Naiyo__;
    }
    /**
     * <p>rsv110Naiyo__ をセットします。
     * @param rsv110Naiyo rsv110Naiyo__
     */
    public void setRsv110Naiyo(String rsv110Naiyo) {
        rsv110Naiyo__ = rsv110Naiyo;
    }
    /**
     * <p>rsv110Prop1Value__ を取得します。
     * @return rsv110Prop1Value
     */
    public String getRsv110Prop1Value() {
        return rsv110Prop1Value__;
    }
    /**
     * <p>rsv110Prop1Value__ をセットします。
     * @param rsv110Prop1Value rsv110Prop1Value__
     */
    public void setRsv110Prop1Value(String rsv110Prop1Value) {
        rsv110Prop1Value__ = rsv110Prop1Value;
    }
    /**
     * <p>rsv110Prop2Value__ を取得します。
     * @return rsv110Prop2Value
     */
    public String getRsv110Prop2Value() {
        return rsv110Prop2Value__;
    }
    /**
     * <p>rsv110Prop2Value__ をセットします。
     * @param rsv110Prop2Value rsv110Prop2Value__
     */
    public void setRsv110Prop2Value(String rsv110Prop2Value) {
        rsv110Prop2Value__ = rsv110Prop2Value;
    }
    /**
     * <p>rsv110Prop3Value__ を取得します。
     * @return rsv110Prop3Value
     */
    public String getRsv110Prop3Value() {
        return rsv110Prop3Value__;
    }
    /**
     * <p>rsv110Prop3Value__ をセットします。
     * @param rsv110Prop3Value rsv110Prop3Value__
     */
    public void setRsv110Prop3Value(String rsv110Prop3Value) {
        rsv110Prop3Value__ = rsv110Prop3Value;
    }
    /**
     * <p>rsv110PropHeaderName1__ を取得します。
     * @return rsv110PropHeaderName1
     */
    public String getRsv110PropHeaderName1() {
        return rsv110PropHeaderName1__;
    }
    /**
     * <p>rsv110PropHeaderName1__ をセットします。
     * @param rsv110PropHeaderName1 rsv110PropHeaderName1__
     */
    public void setRsv110PropHeaderName1(String rsv110PropHeaderName1) {
        rsv110PropHeaderName1__ = rsv110PropHeaderName1;
    }
    /**
     * <p>rsv110PropHeaderName2__ を取得します。
     * @return rsv110PropHeaderName2
     */
    public String getRsv110PropHeaderName2() {
        return rsv110PropHeaderName2__;
    }
    /**
     * <p>rsv110PropHeaderName2__ をセットします。
     * @param rsv110PropHeaderName2 rsv110PropHeaderName2__
     */
    public void setRsv110PropHeaderName2(String rsv110PropHeaderName2) {
        rsv110PropHeaderName2__ = rsv110PropHeaderName2;
    }
    /**
     * <p>rsv110PropHeaderName3__ を取得します。
     * @return rsv110PropHeaderName3
     */
    public String getRsv110PropHeaderName3() {
        return rsv110PropHeaderName3__;
    }
    /**
     * <p>rsv110PropHeaderName3__ をセットします。
     * @param rsv110PropHeaderName3 rsv110PropHeaderName3__
     */
    public void setRsv110PropHeaderName3(String rsv110PropHeaderName3) {
        rsv110PropHeaderName3__ = rsv110PropHeaderName3;
    }
    /**
     * <p>rsv110SelectedDayFr__ を取得します。
     * @return rsv110SelectedDayFr
     */
    public int getRsv110SelectedDayFr() {
        return rsv110SelectedDayFr__;
    }
    /**
     * <p>rsv110SelectedDayFr__ をセットします。
     * @param rsv110SelectedDayFr rsv110SelectedDayFr__
     */
    public void setRsv110SelectedDayFr(int rsv110SelectedDayFr) {
        rsv110SelectedDayFr__ = rsv110SelectedDayFr;
    }
    /**
     * <p>rsv110SelectedDayTo__ を取得します。
     * @return rsv110SelectedDayTo
     */
    public int getRsv110SelectedDayTo() {
        return rsv110SelectedDayTo__;
    }
    /**
     * <p>rsv110SelectedDayTo__ をセットします。
     * @param rsv110SelectedDayTo rsv110SelectedDayTo__
     */
    public void setRsv110SelectedDayTo(int rsv110SelectedDayTo) {
        rsv110SelectedDayTo__ = rsv110SelectedDayTo;
    }
    /**
     * <p>rsv110SelectedHourFr__ を取得します。
     * @return rsv110SelectedHourFr
     */
    public int getRsv110SelectedHourFr() {
        return rsv110SelectedHourFr__;
    }
    /**
     * <p>rsv110SelectedHourFr__ をセットします。
     * @param rsv110SelectedHourFr rsv110SelectedHourFr__
     */
    public void setRsv110SelectedHourFr(int rsv110SelectedHourFr) {
        rsv110SelectedHourFr__ = rsv110SelectedHourFr;
    }
    /**
     * <p>rsv110SelectedHourTo__ を取得します。
     * @return rsv110SelectedHourTo
     */
    public int getRsv110SelectedHourTo() {
        return rsv110SelectedHourTo__;
    }
    /**
     * <p>rsv110SelectedHourTo__ をセットします。
     * @param rsv110SelectedHourTo rsv110SelectedHourTo__
     */
    public void setRsv110SelectedHourTo(int rsv110SelectedHourTo) {
        rsv110SelectedHourTo__ = rsv110SelectedHourTo;
    }
    /**
     * <p>rsv110SelectedMinuteFr__ を取得します。
     * @return rsv110SelectedMinuteFr
     */
    public int getRsv110SelectedMinuteFr() {
        return rsv110SelectedMinuteFr__;
    }
    /**
     * <p>rsv110SelectedMinuteFr__ をセットします。
     * @param rsv110SelectedMinuteFr rsv110SelectedMinuteFr__
     */
    public void setRsv110SelectedMinuteFr(int rsv110SelectedMinuteFr) {
        rsv110SelectedMinuteFr__ = rsv110SelectedMinuteFr;
    }
    /**
     * <p>rsv110SelectedMinuteTo__ を取得します。
     * @return rsv110SelectedMinuteTo
     */
    public int getRsv110SelectedMinuteTo() {
        return rsv110SelectedMinuteTo__;
    }
    /**
     * <p>rsv110SelectedMinuteTo__ をセットします。
     * @param rsv110SelectedMinuteTo rsv110SelectedMinuteTo__
     */
    public void setRsv110SelectedMinuteTo(int rsv110SelectedMinuteTo) {
        rsv110SelectedMinuteTo__ = rsv110SelectedMinuteTo;
    }
    /**
     * <p>rsv110SelectedMonthFr__ を取得します。
     * @return rsv110SelectedMonthFr
     */
    public int getRsv110SelectedMonthFr() {
        return rsv110SelectedMonthFr__;
    }
    /**
     * <p>rsv110SelectedMonthFr__ をセットします。
     * @param rsv110SelectedMonthFr rsv110SelectedMonthFr__
     */
    public void setRsv110SelectedMonthFr(int rsv110SelectedMonthFr) {
        rsv110SelectedMonthFr__ = rsv110SelectedMonthFr;
    }
    /**
     * <p>rsv110SelectedMonthTo__ を取得します。
     * @return rsv110SelectedMonthTo
     */
    public int getRsv110SelectedMonthTo() {
        return rsv110SelectedMonthTo__;
    }
    /**
     * <p>rsv110SelectedMonthTo__ をセットします。
     * @param rsv110SelectedMonthTo rsv110SelectedMonthTo__
     */
    public void setRsv110SelectedMonthTo(int rsv110SelectedMonthTo) {
        rsv110SelectedMonthTo__ = rsv110SelectedMonthTo;
    }
    /**
     * <p>rsv110SelectedYearFr__ を取得します。
     * @return rsv110SelectedYearFr
     */
    public int getRsv110SelectedYearFr() {
        return rsv110SelectedYearFr__;
    }
    /**
     * <p>rsv110SelectedYearFr__ をセットします。
     * @param rsv110SelectedYearFr rsv110SelectedYearFr__
     */
    public void setRsv110SelectedYearFr(int rsv110SelectedYearFr) {
        rsv110SelectedYearFr__ = rsv110SelectedYearFr;
    }
    /**
     * <p>rsv110SelectedYearTo__ を取得します。
     * @return rsv110SelectedYearTo
     */
    public int getRsv110SelectedYearTo() {
        return rsv110SelectedYearTo__;
    }
    /**
     * <p>rsv110SelectedYearTo__ をセットします。
     * @param rsv110SelectedYearTo rsv110SelectedYearTo__
     */
    public void setRsv110SelectedYearTo(int rsv110SelectedYearTo) {
        rsv110SelectedYearTo__ = rsv110SelectedYearTo;
    }
    /**
     * <p>rsv110SisanKanri__ を取得します。
     * @return rsv110SisanKanri
     */
    public String getRsv110SisanKanri() {
        return rsv110SisanKanri__;
    }
    /**
     * <p>rsv110SisanKanri__ をセットします。
     * @param rsv110SisanKanri rsv110SisanKanri__
     */
    public void setRsv110SisanKanri(String rsv110SisanKanri) {
        rsv110SisanKanri__ = rsv110SisanKanri;
    }
    /**
     * <p>rsv110SisetuKbnName__ を取得します。
     * @return rsv110SisetuKbnName
     */
    public String getRsv110SisetuKbnName() {
        return rsv110SisetuKbnName__;
    }
    /**
     * <p>rsv110SisetuKbnName__ をセットします。
     * @param rsv110SisetuKbnName rsv110SisetuKbnName__
     */
    public void setRsv110SisetuKbnName(String rsv110SisetuKbnName) {
        rsv110SisetuKbnName__ = rsv110SisetuKbnName;
    }
    /**
     * <p>rsv110SisetuName__ を取得します。
     * @return rsv110SisetuName
     */
    public String getRsv110SisetuName() {
        return rsv110SisetuName__;
    }
    /**
     * <p>rsv110SisetuName__ をセットします。
     * @param rsv110SisetuName rsv110SisetuName__
     */
    public void setRsv110SisetuName(String rsv110SisetuName) {
        rsv110SisetuName__ = rsv110SisetuName;
    }
    /**
     * <p>rsv110Torokusya__ を取得します。
     * @return rsv110Torokusya
     */
    public String getRsv110Torokusya() {
        return rsv110Torokusya__;
    }
    /**
     * <p>rsv110Torokusya__ をセットします。
     * @param rsv110Torokusya rsv110Torokusya__
     */
    public void setRsv110Torokusya(String rsv110Torokusya) {
        rsv110Torokusya__ = rsv110Torokusya;
    }
    /**
     * <p>rsv110Koshinsya__ を取得します。
     * @return rsv110Koshinsya
     */
    public String getRsv110Koshinsya() {
        return rsv110Koshinsya__;
    }
    /**
     * <p>rsv110Koshinsya__ をセットします。
     * @param rsv110Koshinsya rsv110Koshinsya__
     */
    public void setRsv110Koshinsya(String rsv110Koshinsya) {
        rsv110Koshinsya__ = rsv110Koshinsya;
    }
    /**
     * <p>rsv110EditUsrUkoFlg を取得します。
     * @return rsv110EditUsrUkoFlg
     */
    public int getRsv110EditUsrUkoFlg() {
        return rsv110EditUsrUkoFlg__;
    }
    /**
     * <p>rsv110EditUsrUkoFlg をセットします。
     * @param rsv110EditUsrUkoFlg rsv110EditUsrUkoFlg
     */
    public void setRsv110EditUsrUkoFlg(int rsv110EditUsrUkoFlg) {
        rsv110EditUsrUkoFlg__ = rsv110EditUsrUkoFlg;
    }
    /**
     * <p>rsv110AddUsrUkoFlg を取得します。
     * @return rsv110AddUsrUkoFlg
     */
    public int getRsv110AddUsrUkoFlg() {
        return rsv110AddUsrUkoFlg__;
    }
    /**
     * <p>rsv110AddUsrUkoFlg をセットします。
     * @param rsv110AddUsrUkoFlg rsv110AddUsrUkoFlg
     */
    public void setRsv110AddUsrUkoFlg(int rsv110AddUsrUkoFlg) {
        rsv110AddUsrUkoFlg__ = rsv110AddUsrUkoFlg;
    }
    /**
     * <p>yoyakuFrString__ を取得します。
     * @return yoyakuFrString
     */
    public String getYoyakuFrString() {
        return yoyakuFrString__;
    }
    /**
     * <p>yoyakuFrString__ をセットします。
     * @param yoyakuFrString yoyakuFrString__
     */
    public void setYoyakuFrString(String yoyakuFrString) {
        yoyakuFrString__ = yoyakuFrString;
    }
    /**
     * <p>yoyakuToString__ を取得します。
     * @return yoyakuToString
     */
    public String getYoyakuToString() {
        return yoyakuToString__;
    }
    /**
     * <p>yoyakuToString__ をセットします。
     * @param yoyakuToString yoyakuToString__
     */
    public void setYoyakuToString(String yoyakuToString) {
        yoyakuToString__ = yoyakuToString;
    }
    /**
     * <p>rsv110Prop4Value__ を取得します。
     * @return rsv110Prop4Value
     */
    public String getRsv110Prop4Value() {
        return rsv110Prop4Value__;
    }
    /**
     * <p>rsv110Prop4Value__ をセットします。
     * @param rsv110Prop4Value rsv110Prop4Value__
     */
    public void setRsv110Prop4Value(String rsv110Prop4Value) {
        rsv110Prop4Value__ = rsv110Prop4Value;
    }
    /**
     * <p>rsv110Prop5Value__ を取得します。
     * @return rsv110Prop5Value
     */
    public String getRsv110Prop5Value() {
        return rsv110Prop5Value__;
    }
    /**
     * <p>rsv110Prop5Value__ をセットします。
     * @param rsv110Prop5Value rsv110Prop5Value__
     */
    public void setRsv110Prop5Value(String rsv110Prop5Value) {
        rsv110Prop5Value__ = rsv110Prop5Value;
    }
    /**
     * <p>rsv110PropHeaderName4__ を取得します。
     * @return rsv110PropHeaderName4
     */
    public String getRsv110PropHeaderName4() {
        return rsv110PropHeaderName4__;
    }
    /**
     * <p>rsv110PropHeaderName4__ をセットします。
     * @param rsv110PropHeaderName4 rsv110PropHeaderName4__
     */
    public void setRsv110PropHeaderName4(String rsv110PropHeaderName4) {
        rsv110PropHeaderName4__ = rsv110PropHeaderName4;
    }
    /**
     * <p>rsv110PropHeaderName5__ を取得します。
     * @return rsv110PropHeaderName5
     */
    public String getRsv110PropHeaderName5() {
        return rsv110PropHeaderName5__;
    }
    /**
     * <p>rsv110PropHeaderName5__ をセットします。
     * @param rsv110PropHeaderName5 rsv110PropHeaderName5__
     */
    public void setRsv110PropHeaderName5(String rsv110PropHeaderName5) {
        rsv110PropHeaderName5__ = rsv110PropHeaderName5;
    }
    /**
     * <p>rsv110Prop6Value__ を取得します。
     * @return rsv110Prop6Value
     */
    public String getRsv110Prop6Value() {
        return rsv110Prop6Value__;
    }
    /**
     * <p>rsv110Prop6Value__ をセットします。
     * @param rsv110Prop6Value rsv110Prop6Value__
     */
    public void setRsv110Prop6Value(String rsv110Prop6Value) {
        rsv110Prop6Value__ = rsv110Prop6Value;
    }
    /**
     * <p>rsv110Prop7Value__ を取得します。
     * @return rsv110Prop7Value
     */
    public String getRsv110Prop7Value() {
        return rsv110Prop7Value__;
    }
    /**
     * <p>rsv110Prop7Value__ をセットします。
     * @param rsv110Prop7Value rsv110Prop7Value__
     */
    public void setRsv110Prop7Value(String rsv110Prop7Value) {
        rsv110Prop7Value__ = rsv110Prop7Value;
    }
    /**
     * <p>rsv110PropHeaderName6__ を取得します。
     * @return rsv110PropHeaderName6
     */
    public String getRsv110PropHeaderName6() {
        return rsv110PropHeaderName6__;
    }
    /**
     * <p>rsv110PropHeaderName6__ をセットします。
     * @param rsv110PropHeaderName6 rsv110PropHeaderName6__
     */
    public void setRsv110PropHeaderName6(String rsv110PropHeaderName6) {
        rsv110PropHeaderName6__ = rsv110PropHeaderName6;
    }
    /**
     * <p>rsv110PropHeaderName7__ を取得します。
     * @return rsv110PropHeaderName7
     */
    public String getRsv110PropHeaderName7() {
        return rsv110PropHeaderName7__;
    }
    /**
     * <p>rsv110PropHeaderName7__ をセットします。
     * @param rsv110PropHeaderName7 rsv110PropHeaderName7__
     */
    public void setRsv110PropHeaderName7(String rsv110PropHeaderName7) {
        rsv110PropHeaderName7__ = rsv110PropHeaderName7;
    }
    /**
     * <p>rsv111InitFlg__ を取得します。
     * @return rsv111InitFlg
     */
    public boolean isRsv111InitFlg() {
        return rsv111InitFlg__;
    }
    /**
     * <p>rsv111InitFlg__ をセットします。
     * @param rsv111InitFlg rsv111InitFlg__
     */
    public void setRsv111InitFlg(boolean rsv111InitFlg) {
        rsv111InitFlg__ = rsv111InitFlg;
    }
    /**
     * <p>rsv111RsrBiko__ を取得します。
     * @return rsv111RsrBiko
     */
    public String getRsv111RsrBiko() {
        return rsv111RsrBiko__;
    }
    /**
     * <p>rsv111RsrBiko__ をセットします。
     * @param rsv111RsrBiko rsv111RsrBiko__
     */
    public void setRsv111RsrBiko(String rsv111RsrBiko) {
        rsv111RsrBiko__ = rsv111RsrBiko;
    }
    /**
     * <p>rsv111RsrDay__ を取得します。
     * @return rsv111RsrDay
     */
    public int getRsv111RsrDay() {
        return rsv111RsrDay__;
    }
    /**
     * <p>rsv111RsrDay__ をセットします。
     * @param rsv111RsrDay rsv111RsrDay__
     */
    public void setRsv111RsrDay(int rsv111RsrDay) {
        rsv111RsrDay__ = rsv111RsrDay;
    }
    /**
     * <p>rsv111RsrDweek1__ を取得します。
     * @return rsv111RsrDweek1
     */
    public int getRsv111RsrDweek1() {
        return rsv111RsrDweek1__;
    }
    /**
     * <p>rsv111RsrDweek1__ をセットします。
     * @param rsv111RsrDweek1 rsv111RsrDweek1__
     */
    public void setRsv111RsrDweek1(int rsv111RsrDweek1) {
        rsv111RsrDweek1__ = rsv111RsrDweek1;
    }
    /**
     * <p>rsv111RsrDweek2__ を取得します。
     * @return rsv111RsrDweek2
     */
    public int getRsv111RsrDweek2() {
        return rsv111RsrDweek2__;
    }
    /**
     * <p>rsv111RsrDweek2__ をセットします。
     * @param rsv111RsrDweek2 rsv111RsrDweek2__
     */
    public void setRsv111RsrDweek2(int rsv111RsrDweek2) {
        rsv111RsrDweek2__ = rsv111RsrDweek2;
    }
    /**
     * <p>rsv111RsrDweek3__ を取得します。
     * @return rsv111RsrDweek3
     */
    public int getRsv111RsrDweek3() {
        return rsv111RsrDweek3__;
    }
    /**
     * <p>rsv111RsrDweek3__ をセットします。
     * @param rsv111RsrDweek3 rsv111RsrDweek3__
     */
    public void setRsv111RsrDweek3(int rsv111RsrDweek3) {
        rsv111RsrDweek3__ = rsv111RsrDweek3;
    }
    /**
     * <p>rsv111RsrDweek4__ を取得します。
     * @return rsv111RsrDweek4
     */
    public int getRsv111RsrDweek4() {
        return rsv111RsrDweek4__;
    }
    /**
     * <p>rsv111RsrDweek4__ をセットします。
     * @param rsv111RsrDweek4 rsv111RsrDweek4__
     */
    public void setRsv111RsrDweek4(int rsv111RsrDweek4) {
        rsv111RsrDweek4__ = rsv111RsrDweek4;
    }
    /**
     * <p>rsv111RsrDweek5__ を取得します。
     * @return rsv111RsrDweek5
     */
    public int getRsv111RsrDweek5() {
        return rsv111RsrDweek5__;
    }
    /**
     * <p>rsv111RsrDweek5__ をセットします。
     * @param rsv111RsrDweek5 rsv111RsrDweek5__
     */
    public void setRsv111RsrDweek5(int rsv111RsrDweek5) {
        rsv111RsrDweek5__ = rsv111RsrDweek5;
    }
    /**
     * <p>rsv111RsrDweek6__ を取得します。
     * @return rsv111RsrDweek6
     */
    public int getRsv111RsrDweek6() {
        return rsv111RsrDweek6__;
    }
    /**
     * <p>rsv111RsrDweek6__ をセットします。
     * @param rsv111RsrDweek6 rsv111RsrDweek6__
     */
    public void setRsv111RsrDweek6(int rsv111RsrDweek6) {
        rsv111RsrDweek6__ = rsv111RsrDweek6;
    }
    /**
     * <p>rsv111RsrDweek7__ を取得します。
     * @return rsv111RsrDweek7
     */
    public int getRsv111RsrDweek7() {
        return rsv111RsrDweek7__;
    }
    /**
     * <p>rsv111RsrDweek7__ をセットします。
     * @param rsv111RsrDweek7 rsv111RsrDweek7__
     */
    public void setRsv111RsrDweek7(int rsv111RsrDweek7) {
        rsv111RsrDweek7__ = rsv111RsrDweek7;
    }
    /**
     * <p>rsv111RsrEdit__ を取得します。
     * @return rsv111RsrEdit
     */
    public int getRsv111RsrEdit() {
        return rsv111RsrEdit__;
    }
    /**
     * <p>rsv111RsrEdit__ をセットします。
     * @param rsv111RsrEdit rsv111RsrEdit__
     */
    public void setRsv111RsrEdit(int rsv111RsrEdit) {
        rsv111RsrEdit__ = rsv111RsrEdit;
    }
    /**
     * <p>rsv111RsrPublicを取得します。
     * @return rsv111RsrPublic
     * */
    public int getRsv111RsrPublic() {
        return rsv111RsrPublic__;
    }
    /**
     * <p>rsv111RsrPublicをセットします。
     * @param rsv111RsrPublic rsv111RsrPublic
     * */
    public void setRsv111RsrPublic(int rsv111RsrPublic) {
        rsv111RsrPublic__ = rsv111RsrPublic;
    }
    /**
     * <p>rsv111RsrKbn__ を取得します。
     * @return rsv111RsrKbn
     */
    public int getRsv111RsrKbn() {
        return rsv111RsrKbn__;
    }
    /**
     * <p>rsv111RsrKbn__ をセットします。
     * @param rsv111RsrKbn rsv111RsrKbn__
     */
    public void setRsv111RsrKbn(int rsv111RsrKbn) {
        rsv111RsrKbn__ = rsv111RsrKbn;
    }
    /**
     * <p>rsv111RsrMok__ を取得します。
     * @return rsv111RsrMok
     */
    public String getRsv111RsrMok() {
        return rsv111RsrMok__;
    }
    /**
     * <p>rsv111RsrMok__ をセットします。
     * @param rsv111RsrMok rsv111RsrMok__
     */
    public void setRsv111RsrMok(String rsv111RsrMok) {
        rsv111RsrMok__ = rsv111RsrMok;
    }
    /**
     * <p>rsv111RsrRsid__ を取得します。
     * @return rsv111RsrRsid
     */
    public int getRsv111RsrRsid() {
        return rsv111RsrRsid__;
    }
    /**
     * <p>rsv111RsrRsid__ をセットします。
     * @param rsv111RsrRsid rsv111RsrRsid__
     */
    public void setRsv111RsrRsid(int rsv111RsrRsid) {
        rsv111RsrRsid__ = rsv111RsrRsid;
    }
    /**
     * <p>rsv111RsrTranKbn__ を取得します。
     * @return rsv111RsrTranKbn
     */
    public int getRsv111RsrTranKbn() {
        return rsv111RsrTranKbn__;
    }
    /**
     * <p>rsv111RsrTranKbn__ をセットします。
     * @param rsv111RsrTranKbn rsv111RsrTranKbn__
     */
    public void setRsv111RsrTranKbn(int rsv111RsrTranKbn) {
        rsv111RsrTranKbn__ = rsv111RsrTranKbn;
    }
    /**
     * <p>rsv111RsrWeek__ を取得します。
     * @return rsv111RsrWeek
     */
    public int getRsv111RsrWeek() {
        return rsv111RsrWeek__;
    }
    /**
     * <p>rsv111RsrWeek__ をセットします。
     * @param rsv111RsrWeek rsv111RsrWeek__
     */
    public void setRsv111RsrWeek(int rsv111RsrWeek) {
        rsv111RsrWeek__ = rsv111RsrWeek;
    }
    /**
     * <p>rsv111RsrDateDayFr__ を取得します。
     * @return rsv111RsrDateDayFr
     */
    public String getRsv111RsrDateDayFr() {
        return rsv111RsrDateDayFr__;
    }
    /**
     * <p>rsv111RsrDateDayFr__ をセットします。
     * @param rsv111RsrDateDayFr rsv111RsrDateDayFr__
     */
    public void setRsv111RsrDateDayFr(String rsv111RsrDateDayFr) {
        rsv111RsrDateDayFr__ = rsv111RsrDateDayFr;
    }
    /**
     * <p>rsv111RsrDateDayTo__ を取得します。
     * @return rsv111RsrDateDayTo
     */
    public String getRsv111RsrDateDayTo() {
        return rsv111RsrDateDayTo__;
    }
    /**
     * <p>rsv111RsrDateDayTo__ をセットします。
     * @param rsv111RsrDateDayTo rsv111RsrDateDayTo__
     */
    public void setRsv111RsrDateDayTo(String rsv111RsrDateDayTo) {
        rsv111RsrDateDayTo__ = rsv111RsrDateDayTo;
    }
    /**
     * <p>rsv111RsrDateMonthFr__ を取得します。
     * @return rsv111RsrDateMonthFr
     */
    public String getRsv111RsrDateMonthFr() {
        return rsv111RsrDateMonthFr__;
    }
    /**
     * <p>rsv111RsrDateMonthFr__ をセットします。
     * @param rsv111RsrDateMonthFr rsv111RsrDateMonthFr__
     */
    public void setRsv111RsrDateMonthFr(String rsv111RsrDateMonthFr) {
        rsv111RsrDateMonthFr__ = rsv111RsrDateMonthFr;
    }
    /**
     * <p>rsv111RsrDateMonthTo__ を取得します。
     * @return rsv111RsrDateMonthTo
     */
    public String getRsv111RsrDateMonthTo() {
        return rsv111RsrDateMonthTo__;
    }
    /**
     * <p>rsv111RsrDateMonthTo__ をセットします。
     * @param rsv111RsrDateMonthTo rsv111RsrDateMonthTo__
     */
    public void setRsv111RsrDateMonthTo(String rsv111RsrDateMonthTo) {
        rsv111RsrDateMonthTo__ = rsv111RsrDateMonthTo;
    }
    /**
     * <p>rsv111RsrDateYearFr__ を取得します。
     * @return rsv111RsrDateYearFr
     */
    public String getRsv111RsrDateYearFr() {
        return rsv111RsrDateYearFr__;
    }
    /**
     * <p>rsv111RsrDateYearFr__ をセットします。
     * @param rsv111RsrDateYearFr rsv111RsrDateYearFr__
     */
    public void setRsv111RsrDateYearFr(String rsv111RsrDateYearFr) {
        rsv111RsrDateYearFr__ = rsv111RsrDateYearFr;
    }
    /**
     * <p>rsv111RsrDateYearTo__ を取得します。
     * @return rsv111RsrDateYearTo
     */
    public String getRsv111RsrDateYearTo() {
        return rsv111RsrDateYearTo__;
    }
    /**
     * <p>rsv111RsrDateYearTo__ をセットします。
     * @param rsv111RsrDateYearTo rsv111RsrDateYearTo__
     */
    public void setRsv111RsrDateYearTo(String rsv111RsrDateYearTo) {
        rsv111RsrDateYearTo__ = rsv111RsrDateYearTo;
    }
    /**
     * <p>rsv111RsrTimeHourFr__ を取得します。
     * @return rsv111RsrTimeHourFr
     */
    public String getRsv111RsrTimeHourFr() {
        return rsv111RsrTimeHourFr__;
    }
    /**
     * <p>rsv111RsrTimeHourFr__ をセットします。
     * @param rsv111RsrTimeHourFr rsv111RsrTimeHourFr__
     */
    public void setRsv111RsrTimeHourFr(String rsv111RsrTimeHourFr) {
        rsv111RsrTimeHourFr__ = rsv111RsrTimeHourFr;
    }
    /**
     * <p>rsv111RsrTimeHourTo__ を取得します。
     * @return rsv111RsrTimeHourTo
     */
    public String getRsv111RsrTimeHourTo() {
        return rsv111RsrTimeHourTo__;
    }
    /**
     * <p>rsv111RsrTimeHourTo__ をセットします。
     * @param rsv111RsrTimeHourTo rsv111RsrTimeHourTo__
     */
    public void setRsv111RsrTimeHourTo(String rsv111RsrTimeHourTo) {
        rsv111RsrTimeHourTo__ = rsv111RsrTimeHourTo;
    }
    /**
     * <p>rsv111RsrTimeMinuteFr__ を取得します。
     * @return rsv111RsrTimeMinuteFr
     */
    public String getRsv111RsrTimeMinuteFr() {
        return rsv111RsrTimeMinuteFr__;
    }
    /**
     * <p>rsv111RsrTimeMinuteFr__ をセットします。
     * @param rsv111RsrTimeMinuteFr rsv111RsrTimeMinuteFr__
     */
    public void setRsv111RsrTimeMinuteFr(String rsv111RsrTimeMinuteFr) {
        rsv111RsrTimeMinuteFr__ = rsv111RsrTimeMinuteFr;
    }
    /**
     * <p>rsv111RsrTimeMinuteTo__ を取得します。
     * @return rsv111RsrTimeMinuteTo
     */
    public String getRsv111RsrTimeMinuteTo() {
        return rsv111RsrTimeMinuteTo__;
    }
    /**
     * <p>rsv111RsrTimeMinuteTo__ をセットします。
     * @param rsv111RsrTimeMinuteTo rsv111RsrTimeMinuteTo__
     */
    public void setRsv111RsrTimeMinuteTo(String rsv111RsrTimeMinuteTo) {
        rsv111RsrTimeMinuteTo__ = rsv111RsrTimeMinuteTo;
    }
    /**
     * <p>rsv111ScdReflection__ を取得します。
     * @return rsv111ScdReflection
     */
    public int getRsv111ScdReflection() {
        return rsv111ScdReflection__;
    }
    /**
     * <p>rsv111ScdReflection__ をセットします。
     * @param rsv111ScdReflection rsv111ScdReflection__
     */
    public void setRsv111ScdReflection(int rsv111ScdReflection) {
        rsv111ScdReflection__ = rsv111ScdReflection;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] 全項目チェック
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validateRsv110All(RequestModel reqMdl, Connection con, int sessionUsrSid)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        ActionErrors errorsInp = validateRsv110Base(reqMdl, con, sessionUsrSid);
        if (errorsInp.isEmpty()) {
            ActionErrors errorsDel = validateRsv110Scd(reqMdl, con);
            if (!errorsDel.isEmpty()) {
                errors.add(errorsDel);
            }
        } else {
            errors.add(errorsInp);
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] OKボタン押下時
     *
     * @param con コネクション
     * @param reqMdl リクエストじょうほう
     * @param sessionUsrSid セッションユーザSID
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validateRsv110Base(RequestModel reqMdl, Connection con, int sessionUsrSid)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        boolean errorFlg = false;

        //利用目的 未入力チェック
        if (StringUtil.isNullZeroString(rsv110Mokuteki__)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv110Mokuteki");
            errorFlg = true;
        //利用目的 桁数チェック
        } else if (rsv110Mokuteki__.length() > GSConstReserve.MAX_LENGTH_MOKUTEKI) {
            msg =
                new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("reserve.72"),
                                String.valueOf(GSConstReserve.MAX_LENGTH_MOKUTEKI));
            StrutsUtil.addMessage(errors, msg, "rsv110Mokuteki");
            errorFlg = true;
        //利用目的 スペースのみチェック
        } else if (ValidateUtil.isSpace(rsv110Mokuteki__)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv110Mokuteki");
            errorFlg = true;
        //利用目的 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(rsv110Mokuteki__)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv110Mokuteki");
            errorFlg = true;
        //利用目的 タブチェック
        } else if (ValidateUtil.isTab(rsv110Mokuteki__)) {
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey,
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv110Mokuteki");
            errorFlg = true;
        //利用目的 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(rsv110Mokuteki__)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110Mokuteki__);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("reserve.72"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, "rsv110Mokuteki");
            errorFlg = true;
        }

      //施設予約区分別情報
        if (RsvCommonBiz.isRskKbnRegCheck(rsv110SisetuKbn__)) {
            //施設区分 部屋の場合
            if (rsv110SisetuKbn__ == GSConstReserve.RSK_KBN_HEYA) {
                //施設予約区分別情報 利用区分
                if (!__isCheckUseKbn(rsv110UseKbn__)) {
                    //選択肢チェック
                    msg = new ActionMessage("error.select.required.text", "利用区分");
                    StrutsUtil.addMessage(errors, msg, "rsv110UseKbn");
                }

                //施設予約区分別情報 連絡先
                if (!StringUtil.isNullZeroString(rsv110Contact__)) {
                    //桁数チェック
                    if (rsv110Contact__.length() > GSConstReserve.MAX_LENGTH_CONTACT) {
                        msg = new ActionMessage("error.input.length.text", "連絡先",
                                String.valueOf(GSConstReserve.MAX_LENGTH_CONTACT));
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");

                    //スペースのみチェック
                    } else if (ValidateUtil.isSpace(rsv110Contact__)) {
                        msg = new ActionMessage("error.input.spase.only", "連絡先");
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");

                    //先頭スペースチェック
                    } else if (ValidateUtil.isSpaceStart(rsv110Contact__)) {
                        msg = new ActionMessage("error.input.spase.start", "連絡先");
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");

                    //JIS第2水準チェック
                    } else if (!GSValidateUtil.isGsJapaneaseString(rsv110Contact__)) {
                        String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110Contact__);
                        msg =
                            new ActionMessage("error.input.njapan.text", "連絡先", nstr);
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");
                    }
                }

                //施設予約区分別情報 会議名案内
                if (!StringUtil.isNullZeroString(rsv110Guide__)) {
                    //桁数チェック
                    if (rsv110Guide__.length() > GSConstReserve.MAX_LENGTH_GUIDE) {
                        msg = new ActionMessage("error.input.length.text", "会議名案内",
                                String.valueOf(GSConstReserve.MAX_LENGTH_GUIDE));
                        StrutsUtil.addMessage(errors, msg, "rsv110Guide");

                    //スペースのみチェック
                    } else if (ValidateUtil.isSpace(rsv110Guide__)) {
                        msg = new ActionMessage("error.input.spase.only", "会議名案内");
                        StrutsUtil.addMessage(errors, msg, "rsv110Guide");

                    //先頭スペースチェック
                    } else if (ValidateUtil.isSpaceStart(rsv110Guide__)) {
                        msg = new ActionMessage("error.input.spase.start", "会議名案内");
                        StrutsUtil.addMessage(errors, msg, "rsv110Guide");

                    //JIS第2水準チェック
                    } else if (!GSValidateUtil.isGsJapaneaseString(rsv110Guide__)) {
                        String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110Guide__);
                        msg =
                            new ActionMessage("error.input.njapan.text", "会議名案内", nstr);
                        StrutsUtil.addMessage(errors, msg, "rsv110Guide");
                    }
                }
                //施設予約区分別情報 駐車場見込み台数
                if (!StringUtil.isNullZeroString(rsv110ParkNum__)) {
                    // 数字以外の文字を入力した場合
                    if (!GSValidateUtil.isNumber(rsv110ParkNum__)) {
                        msg = new ActionMessage("error.input.comp.text",
                                "駐車場見込み台数", gsMsg.getMessage("cmn.numbers"));
                        StrutsUtil.addMessage(errors, msg, "rsv110ParkNum");

                    } else if (rsv110ParkNum__.length() > GSConstReserve.MAX_LENGTH_PARKNUM) {
                        //桁数チェック
                        msg = new ActionMessage("error.input.length.text",
                                "駐車場見込み台数",
                                String.valueOf(GSConstReserve.MAX_LENGTH_PARKNUM));
                        StrutsUtil.addMessage(errors, msg, "rsv110ParkNum");
                    }
                }

            } else if (rsv110SisetuKbn__ == GSConstReserve.RSK_KBN_CAR) {
                //施設区分 車の場合

                //施設予約区分別情報 連絡先
                if (!StringUtil.isNullZeroString(rsv110Contact__)) {
                    //桁数チェック
                    if (rsv110Contact__.length() > GSConstReserve.MAX_LENGTH_CONTACT) {
                        msg = new ActionMessage("error.input.length.text", "連絡先",
                                String.valueOf(GSConstReserve.MAX_LENGTH_CONTACT));
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");

                    //スペースのみチェック
                    } else if (ValidateUtil.isSpace(rsv110Contact__)) {
                        msg = new ActionMessage("error.input.spase.only", "連絡先");
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");

                    //先頭スペースチェック
                    } else if (ValidateUtil.isSpaceStart(rsv110Contact__)) {
                        msg = new ActionMessage("error.input.spase.start", "連絡先");
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");

                    //JIS第2水準チェック
                    } else if (!GSValidateUtil.isGsJapaneaseString(rsv110Contact__)) {
                        String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110Contact__);
                        msg =
                            new ActionMessage("error.input.njapan.text", "連絡先", nstr);
                        StrutsUtil.addMessage(errors, msg, "rsv110Contact");
                    }
                }

                //施設予約区分別情報 行き先
                if (!StringUtil.isNullZeroString(rsv110Dest__)) {
                    //桁数チェック
                    if (rsv110Dest__.length() > GSConstReserve.MAX_LENGTH_DEST) {
                        msg = new ActionMessage("error.input.length.text", "行き先",
                                String.valueOf(GSConstReserve.MAX_LENGTH_DEST));
                        StrutsUtil.addMessage(errors, msg, "rsv110Dest");

                    //スペースのみチェック
                    } else if (ValidateUtil.isSpace(rsv110Dest__)) {
                        msg = new ActionMessage("error.input.spase.only", "行き先");
                        StrutsUtil.addMessage(errors, msg, "rsv110Dest");

                    //先頭スペースチェック
                    } else if (ValidateUtil.isSpaceStart(rsv110Dest__)) {
                        msg = new ActionMessage("error.input.spase.start", "行き先");
                        StrutsUtil.addMessage(errors, msg, "rsv110Dest");

                    //JIS第2水準チェック
                    } else if (!GSValidateUtil.isGsJapaneaseString(rsv110Dest__)) {
                        String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110Dest__);
                        msg =
                            new ActionMessage("error.input.njapan.text", "行き先", nstr);
                        StrutsUtil.addMessage(errors, msg, "rsv110Dest");
                    }
                }
            }
        }


        //開始年月日チェックフラグ
        boolean fromOk = false;
        UDate frDate = new UDate();

        DateTimePickerBiz picker = new DateTimePickerBiz();
        String frDateNameJp = gsMsg.getMessage("reserve.157");
        String toDateNameJp = gsMsg.getMessage("reserve.158");

        int errorCnt = errors.size();
        errors.add(picker.setYmdParam(this,
                "rsv110SelectedDateFr",
                "rsv110SelectedYearFr",
                "rsv110SelectedMonthFr",
                "rsv110SelectedDayFr",
                frDateNameJp));

        if (errorCnt == errors.size()) {
            int iSYear = rsv110SelectedYearFr__;
            int iSMonth = rsv110SelectedMonthFr__;
            int iSDay = rsv110SelectedDayFr__;

            frDate.setDate(iSYear, iSMonth, iSDay);
            frDate.setSecond(GSConstReserve.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

            if (frDate.getYear() != iSYear
                || frDate.getMonth() != iSMonth
                || frDate.getIntDay() != iSDay) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage("reserve.157"));
                StrutsUtil.addMessage(errors, msg, "rsv110KikanFr");
                errorFlg = true;
                errorCnt = errors.size();
            } else {
                fromOk = true;
            }
        }

        //終了年月日チェックフラグ
        boolean toOk = false;
        UDate toDate = new UDate();

        errors.add(picker.setYmdParam(this,
                "rsv110SelectedDateTo",
                "rsv110SelectedYearTo",
                "rsv110SelectedMonthTo",
                "rsv110SelectedDayTo",
                toDateNameJp));

        if (errorCnt == errors.size()) {
            int iEYear = rsv110SelectedYearTo__;
            int iEMonth = rsv110SelectedMonthTo__;
            int iEDay = rsv110SelectedDayTo__;

            toDate.setDate(iEYear, iEMonth, iEDay);
            toDate.setSecond(GSConstReserve.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

            if (toDate.getYear() != iEYear
                || toDate.getMonth() != iEMonth
                || toDate.getIntDay() != iEDay) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage("reserve.158"));
                StrutsUtil.addMessage(errors, msg, "rsv110KikanTo");
                errorFlg = true;
            } else {
                toOk = true;
            }
        }

        //個別チェックOKの場合
        boolean dateOk = false;
        if (fromOk && toOk) {
            errorCnt = errors.size();
            errors.add(picker.setHmParam(this,
                    "rsv110SelectedTimeFr",
                    "rsv110SelectedHourFr",
                    "rsv110SelectedMinuteFr",
                    frDateNameJp));
            errors.add(picker.setHmParam(this,
                    "rsv110SelectedTimeTo",
                    "rsv110SelectedHourTo",
                    "rsv110SelectedMinuteTo",
                    toDateNameJp));

            if (errorCnt == errors.size()) {
                frDate.setHour(rsv110SelectedHourFr__);
                frDate.setMinute(rsv110SelectedMinuteFr__);
                toDate.setHour(rsv110SelectedHourTo__);
                toDate.setMinute(rsv110SelectedMinuteTo__);

                //from～to大小チェック
                if (frDate.compare(frDate, toDate) != UDate.LARGE) {
                    msg = new ActionMessage("error.input.comp.text",
                            gsMsg.getMessage("reserve.156"),
                            gsMsg.getMessage("cmn.start.lessthan.end"));
                    StrutsUtil.addMessage(errors, msg, "rsv110Kikan");
                    errorFlg = true;
                }
                //時間単位チェック
                String frMinute = rsv110SelectedTimeFr__.split(":")[1];
                String toMinute = rsv110SelectedTimeTo__.split(":")[1];
                int frTime = Integer.parseInt(frMinute);
                int toTime = Integer.parseInt(toMinute);
                boolean timeUnit = RsvValidateUtil.validateTimeUnit(
                        con, errors, gsMsg, frTime, toTime);
                if (timeUnit) {
                    dateOk = true;
                } else {
                    errorFlg = true;
                }
            }
        }

        //内容
        if (!StringUtil.isNullZeroString(rsv110Naiyo__)) {
            //内容 桁数チェック
            if (rsv110Naiyo__.length() > GSConstReserve.MAX_LENGTH_NAIYO) {
                msg = new ActionMessage("error.input.length.textarea",
                        gsMsg.getMessage("cmn.content"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_NAIYO));
                StrutsUtil.addMessage(errors, msg, "rsv110Naiyo");
                errorFlg = true;
            }
            //内容 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(rsv110Naiyo__)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage("cmn.content"));
                StrutsUtil.addMessage(errors, msg, "rsv110Naiyo");
                errorFlg = true;
            }
            //内容 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(rsv110Naiyo__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(rsv110Naiyo__);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("cmn.content"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, "rsv110Naiyo__");
                errorFlg = true;
            }
        }

        //施設予約区分別情報
        if (RsvCommonBiz.isRskKbnRegCheck(rsv110SisetuKbn__)) {
            //施設予約区分別情報 担当部署
            if (!StringUtil.isNullZeroString(rsv110Busyo__)) {
                //桁数チェック
                if (rsv110Busyo__.length() > GSConstReserve.MAX_LENGTH_TBUSYO) {
                    msg = new ActionMessage("error.input.length.text", "担当部署",
                            String.valueOf(GSConstReserve.MAX_LENGTH_TBUSYO));
                    StrutsUtil.addMessage(errors, msg, "rsv110Busyo");

                //スペースのみチェック
                } else if (ValidateUtil.isSpace(rsv110Busyo__)) {
                    msg = new ActionMessage("error.input.spase.only", "担当部署");
                    StrutsUtil.addMessage(errors, msg, "rsv110Busyo");

                //先頭スペースチェック
                } else if (ValidateUtil.isSpaceStart(rsv110Busyo__)) {
                    msg = new ActionMessage("error.input.spase.start", "担当部署");
                    StrutsUtil.addMessage(errors, msg, "rsv110Busyo");

                //JIS第2水準チェック
                } else if (!GSValidateUtil.isGsJapaneaseString(rsv110Busyo__)) {
                    String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110Busyo__);
                    msg =
                        new ActionMessage("error.input.njapan.text", "担当部署", nstr);
                    StrutsUtil.addMessage(errors, msg, "rsv110Busyo");
                }
            }

            //施設予約区分別情報 担当・使用者名
            if (!StringUtil.isNullZeroString(rsv110UseName__)) {
                String title = "";
                if (rsv110SisetuKbn__ == GSConstReserve.RSK_KBN_HEYA) {
                    title = "担当者名";
                } else if (rsv110SisetuKbn__ == GSConstReserve.RSK_KBN_CAR) {
                    title = "使用者名";
                }

                //桁数チェック
                if (rsv110UseName__.length() > GSConstReserve.MAX_LENGTH_TNAME) {
                    msg = new ActionMessage("error.input.length.text", title,
                            String.valueOf(GSConstReserve.MAX_LENGTH_TNAME));
                    StrutsUtil.addMessage(errors, msg, "rsv110UseName");

                //スペースのみチェック
                } else if (ValidateUtil.isSpace(rsv110UseName__)) {
                    msg = new ActionMessage("error.input.spase.only", title);
                    StrutsUtil.addMessage(errors, msg, "rsv110UseName");

                //先頭スペースチェック
                } else if (ValidateUtil.isSpaceStart(rsv110UseName__)) {
                    msg = new ActionMessage("error.input.spase.start", title);
                    StrutsUtil.addMessage(errors, msg, "rsv110UseName");

                //JIS第2水準チェック
                } else if (!GSValidateUtil.isGsJapaneaseString(rsv110UseName__)) {
                    String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv110UseName__);
                    msg =
                        new ActionMessage("error.input.njapan.text", title, nstr);
                    StrutsUtil.addMessage(errors, msg, "rsv110UseName");
                }
            }

            //施設予約区分別情報 人数
            if (!StringUtil.isNullZeroString(rsv110UseNum__)) {
                // 数字以外の文字を入力した場合
                if (!GSValidateUtil.isNumber(rsv110UseNum__)) {
                    msg = new ActionMessage("error.input.comp.text",
                            "人数", gsMsg.getMessage("cmn.numbers"));
                    StrutsUtil.addMessage(errors, msg, "rsv110UseNum");

                } else if (rsv110UseNum__.length() > GSConstReserve.MAX_LENGTH_TNUM) {
                    //桁数チェック
                    msg = new ActionMessage("error.input.length.text",
                            "人数",
                            String.valueOf(GSConstReserve.MAX_LENGTH_TNUM));
                    StrutsUtil.addMessage(errors, msg, "rsv110UseNum");
                }
            }
        }

        //予約時間チェック
        if (!errorFlg) {

            int sisetuSid = -1;
            Rsv110Biz biz = new Rsv110Biz(reqMdl, con);

            //新規モード
            if (rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_SINKI)) {
                sisetuSid = rsv110RsdSid__;
            //編集モード or 複写して登録モード
            } else if (rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_EDIT)
                    || rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {

                Rsv110ParamModel paramMdl = new Rsv110ParamModel();
                paramMdl.setParam(this);
                Rsv110SisetuModel yrkMdl = biz.getYoyakuEditData(paramMdl);
                paramMdl.setFormData(this);

                if (yrkMdl != null) {
                    sisetuSid = yrkMdl.getRsdSid();
                }
            }

            //施設の情報を取得
            Rsv210Model dataMdl = biz.getGroupData(sisetuSid);
            if (dataMdl != null) {

                //予約可能期限チェック(期限が設定されていればチェックする)
                String kigen = dataMdl.getRsdProp6();
                if (!StringUtil.isNullZeroString(kigen)) {

                    //施設グループ管理者の場合は予約可能期限チェックをパスする
                    RsvCommonBiz rsvBiz = new RsvCommonBiz();
                    if (!rsvBiz.isGroupAdmin(con, sisetuSid, sessionUsrSid)) {

                        UDate now = new UDate();
                        UDate udKigen = now.cloneUDate();
                        udKigen.addDay(Integer.parseInt(kigen));

                        String kigenYmd = udKigen.getDateString();

                        UDate chkToDate = new UDate();
                        chkToDate.setDate(
                                rsv110SelectedYearTo__,
                                rsv110SelectedMonthTo__,
                                rsv110SelectedDayTo__);
                        String chkYmd = chkToDate.getDateString();

                        if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {

                            String kigenStr =
                                    gsMsg.getMessage("cmn.comments")
                                    + dataMdl.getRsdProp6()
                                    + gsMsg.getMessage("cmn.days.after");

                            msg = new ActionMessage("error.kigen.over2.sisetu", kigenStr);
                            StrutsUtil.addMessage(errors, msg, "sisetu");
                            errorFlg = true;
                        }
                    }
                }

                //重複のチェック(重複登録 = 不可の場合にチェック)
                String tyohuku = dataMdl.getRsdProp7();
                if (!errorFlg
                        && !StringUtil.isNullZeroString(tyohuku)
                        && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {

                    RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);

                    //予約開始
                    UDate chkFrDate = new UDate();
                    chkFrDate.setDate(
                            rsv110SelectedYearFr__,
                            rsv110SelectedMonthFr__,
                            rsv110SelectedDayFr__);
                    chkFrDate.setHour(rsv110SelectedHourFr__);
                    chkFrDate.setMinute(rsv110SelectedMinuteFr__);
                    chkFrDate.setSecond(GSConstReserve.DAY_START_SECOND);
                    chkFrDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                    //予約終了
                    UDate chkToDate = new UDate();
                    chkToDate.setDate(
                            rsv110SelectedYearTo__,
                            rsv110SelectedMonthTo__,
                            rsv110SelectedDayTo__);
                    chkToDate.setHour(rsv110SelectedHourTo__);
                    chkToDate.setMinute(rsv110SelectedMinuteTo__);
                    chkToDate.setSecond(GSConstReserve.DAY_START_SECOND);
                    chkToDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                    List<RsvSisYrkModel> ngList = new ArrayList<RsvSisYrkModel>();

                    //新規モード or 複写して登録モード
                    if (rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_SINKI)
                            || rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
                        ngList = yrkDao.getYrkNgList(-1, sisetuSid, chkFrDate, chkToDate);
                    //編集モード
                    } else if (rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_EDIT)) {
                        ngList = yrkDao.getYrkNgList(
                                 rsv110RsySid__, sisetuSid, chkFrDate, chkToDate);

                    }

                    //重複チェック
                    if (ngList != null && ngList.size() > 0) {

                        String textSchedule = gsMsg.getMessage("cmn.reserve");
                        msg = new ActionMessage("error.input.dup", textSchedule);
                        StrutsUtil.addMessage(errors, msg, "rsv110YrkEr");

                        for (RsvSisYrkModel yrkModel : ngList) {

                            String schTime = UDateUtil.getYymdJ(yrkModel.getRsyFrDate(), reqMdl);
                            schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyFrDate(), reqMdl);
                            schTime += "～";
                            schTime += UDateUtil.getYymdJ(yrkModel.getRsyToDate(), reqMdl);
                            schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyToDate(), reqMdl);

                            msg = new ActionMessage("error.input.dup.rsv",
                                    schTime,
                                    StringUtilHtml.transToHTml(yrkModel.getRsyMok()));

                            StrutsUtil.addMessage(
                                    errors, msg,
                                    "rsv110tyohuku" + String.valueOf(yrkModel.getRsySid()));
                        }
                    }
               }
            }
        }

        //スケジュール 重複チェック
        if (dateOk) {
            if (rsv110ScdReflection__ == GSConstReserve.SCD_REFLECTION_OK) {
                RelationBetweenScdAndRsvChkBiz schChkBiz =
                    new RelationBetweenScdAndRsvChkBiz(reqMdl, con);

                if (rsv110SchKbn__ == GSConstReserve.RSV_SCHKBN_GROUP) {
                    String grpSid = getRsv110SchGroupSid();
                    if (NullDefault.getInt(grpSid, -1) >= 0) {

                        //例外アクセス
                        schChkBiz.validateSpCaceGroupForSchedule(errors, grpSid,
                                sessionUsrSid, "rsv110SchGroupSid");

//                        schChkBiz.validateGroupForSchedule(errors, grpSid,
//                                                        sessionUsrSid, "rsv110SchGroupSid");
                    }

                } else {
                    String[] users = getSv_users();
                    if (rsv110SchKbn__ == GSConstReserve.RSV_SCHKBN_USER
                    && users != null && users.length > 0) {

                        //例外アクセス
                        schChkBiz.validateSpCaceUserForSchedule(
                                errors, users, sessionUsrSid, "sv_users");

                        List<UDate[]> dateList = new ArrayList<UDate[]>();
                        dateList.add(new UDate[] {frDate, toDate});
                        schChkBiz.validateDateForSchedule(
                                errors,
                                dateList,
                                users,
                                getRsv110ScdRsSid(),
                                rsv110ProcMode__.equals(GSConstReserve.PROC_MODE_COPY_ADD),
                                sessionUsrSid,
                                "rsv110Date");
                    }
                }
            }
        }

        //公開対象チェック
        if (rsv110Public__ == GSConstReserve.PUBLIC_KBN_USRGRP) {
            String taisyou = gsMsg.getMessage("reserve.190");
            if (rsv110PubUsrGrpSid__ == null || rsv110PubUsrGrpSid__.length == 0) {
                msg = new ActionMessage(
                        "error.select.required.text", taisyou);
                errors.add("error.select.required.text", msg);
            } else {
                //存在チェック
                ArrayList<Integer> grpSids = new ArrayList<Integer>();
                List<String> usrSids = new ArrayList<String>();
                for (String target : rsv110PubUsrGrpSid__) {
                    if (target.startsWith("G")) {
                        grpSids.add(NullDefault.getInt(
                                target.substring(1), -1));
                    } else {
                        if (NullDefault.getInt(
                                target, -1) > GSConstUser.USER_RESERV_SID) {
                            usrSids.add(target);
                        }
                    }
                }

                ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
                ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();
                //グループ存在チェック
                if (!grpSids.isEmpty()) {
                    UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                    glist = gdao.selectGroupNmListOrderbyClass(grpSids);
                }

                //ユーザ存在チェック
                if (!usrSids.isEmpty()) {
                    UserBiz userBiz = new UserBiz();
                    ulist = userBiz.getBaseUserList(con,
                                                    usrSids.toArray(new String[usrSids.size()]));
                }

                if (ulist.isEmpty() && glist.isEmpty()) {
                    msg = new ActionMessage("error.select.required.text", taisyou);
                    StrutsUtil.addMessage(
                            errors, msg, "rsv110PubUsrGrpSid");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] スケジュールの更新チェック
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateRsv110Scd(RequestModel reqMdl, Connection con)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //スケジュールと関連付いている & 「スケジュールへ反映」が選択されている
        if (rsv110ScdRsSid__ > 0
                && rsv110ScdReflection__ == GSConstReserve.SCD_REFLECTION_OK
                && rsv110ExistSchDateFlg__) {

            RelationBetweenScdAndRsvChkBiz biz =
                new RelationBetweenScdAndRsvChkBiz(reqMdl, con);

            int errCd =
                biz.isScdEdit(
                        rsv110RsySid__,
                        RelationBetweenScdAndRsvChkBiz.CHK_KBN_TANITU);

            if (errCd != RelationBetweenScdAndRsvChkBiz.ERR_CD_NON_ERR) {
                msg = new ActionMessage("error.scd.auth");
                StrutsUtil.addMessage(errors, msg, "auth");
            }
        }

        return errors;
    }
    /**
     * <p>rsv110HeaderDspFlg を取得します。
     * @return rsv110HeaderDspFlg
     */
    public String getRsv110HeaderDspFlg() {
        return rsv110HeaderDspFlg__;
    }
    /**
     * <p>rsv110headerDspFlg をセットします。
     * @param rsv110HeaderDspFlg rsv110HeaderDspFlg
     */
    public void setRsv110HeaderDspFlg(String rsv110HeaderDspFlg) {
        rsv110HeaderDspFlg__ = rsv110HeaderDspFlg;
    }
    /**
     * <p>rsv110AddDate を取得します。
     * @return rsv110AddDate
     */
    public String getRsv110AddDate() {
        return rsv110AddDate__;
    }
    /**
     * <p>rsv110AddDate をセットします。
     * @param rsv110AddDate rsv110AddDate
     */
    public void setRsv110AddDate(String rsv110AddDate) {
        rsv110AddDate__ = rsv110AddDate;
    }
    /**
     * <p>rsv110EditDate を取得します。
     * @return rsv110EditDate
     */
    public String getRsv110EditDate() {
        return rsv110EditDate__;
    }
    /**
     * <p>rsv110EditDate をセットします。
     * @param rsv110EditDate rsv110EditDate
     */
    public void setRsv110EditDate(String rsv110EditDate) {
        rsv110EditDate__ = rsv110EditDate;
    }
    /**
     * <p>rsv110ExistSchDateFlg を取得します。
     * @return rsv110ExistSchDateFlg
     */
    public boolean isRsv110ExistSchDateFlg() {
        return rsv110ExistSchDateFlg__;
    }
    /**
     * <p>rsv110ExistSchDateFlg をセットします。
     * @param rsv110ExistSchDateFlg rsv110ExistSchDateFlg
     */
    public void setRsv110ExistSchDateFlg(boolean rsv110ExistSchDateFlg) {
        rsv110ExistSchDateFlg__ = rsv110ExistSchDateFlg;
    }

    //--------------------------------------------------
    /**
     * <p>rsv110GroupSid を取得します。
     * @return rsv110GroupSid
     */
    public String getRsv110GroupSid() {
        return rsv110GroupSid__;
    }
    /**
     * <p>rsv110GroupSid をセットします。
     * @param rsv110GroupSid rsv110GroupSid
     */
    public void setRsv110GroupSid(String rsv110GroupSid) {
        rsv110GroupSid__ = rsv110GroupSid;
    }
    /**
     * <p>rsv111GroupSid を取得します。
     * @return rsv111GroupSid
     */
    public String getRsv111GroupSid() {
        return rsv111GroupSid__;
    }
    /**
     * <p>rsv111GroupSid をセットします。
     * @param rsv111GroupSid rsv111GroupSid
     */
    public void setRsv111GroupSid(String rsv111GroupSid) {
        rsv111GroupSid__ = rsv111GroupSid;
    }
    /**
     * <p>rsv111SvUsers を取得します。
     * @return rsv111SvUsers
     */
    public String[] getRsv111SvUsers() {
        return rsv111SvUsers__;
    }
    /**
     * <p>rsv111SvUsers をセットします。
     * @param rsv111SvUsers rsv111SvUsers
     */
    public void setRsv111SvUsers(String[] rsv111SvUsers) {
        rsv111SvUsers__ = rsv111SvUsers;
    }
    /**
     * <p>schedulePluginKbn を取得します。
     * @return schedulePluginKbn
     */
    public int getSchedulePluginKbn() {
        return schedulePluginKbn__;
    }
    /**
     * <p>schedulePluginKbn をセットします。
     * @param schedulePluginKbn schedulePluginKbn
     */
    public void setSchedulePluginKbn(int schedulePluginKbn) {
        schedulePluginKbn__ = schedulePluginKbn;
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
     * <p>rsvSchUserNameArray を取得します。
     * @return rsvSchUserNameArray
     */
    public ArrayList<UsrLabelValueBean> getRsvSchUserNameArray() {
        return rsvSchUserNameArray__;
    }
    /**
     * <p>rsvSchUserNameArray をセットします。
     * @param rsvSchUserNameArray rsvSchUserNameArray
     */
    public void setRsvSchUserNameArray(ArrayList<UsrLabelValueBean> rsvSchUserNameArray) {
        rsvSchUserNameArray__ = rsvSchUserNameArray;
    }
    /**
     * <p>rsv110SchGroupSid を取得します。
     * @return rsv110SchGroupSid
     */
    public String getRsv110SchGroupSid() {
        return rsv110SchGroupSid__;
    }
    /**
     * <p>rsv110SchGroupSid をセットします。
     * @param rsv110SchGroupSid rsv110SchGroupSid
     */
    public void setRsv110SchGroupSid(String rsv110SchGroupSid) {
        rsv110SchGroupSid__ = rsv110SchGroupSid;
    }
    /**
     * <p>rsv110SchKbn を取得します。
     * @return rsv110SchKbn
     */
    public int getRsv110SchKbn() {
        return rsv110SchKbn__;
    }
    /**
     * <p>rsv110SchKbn をセットします。
     * @param rsv110SchKbn rsv110SchKbn
     */
    public void setRsv110SchKbn(int rsv110SchKbn) {
        rsv110SchKbn__ = rsv110SchKbn;
    }
    /**
     * <p>rsv111SchGroupSid を取得します。
     * @return rsv111SchGroupSid
     */
    public String getRsv111SchGroupSid() {
        return rsv111SchGroupSid__;
    }
    /**
     * <p>rsv111SchGroupSid をセットします。
     * @param rsv111SchGroupSid rsv111SchGroupSid
     */
    public void setRsv111SchGroupSid(String rsv111SchGroupSid) {
        rsv111SchGroupSid__ = rsv111SchGroupSid;
    }
    /**
     * <p>rsv111SchKbn を取得します。
     * @return rsv111SchKbn
     */
    public int getRsv111SchKbn() {
        return rsv111SchKbn__;
    }
    /**
     * <p>rsv111SchKbn をセットします。
     * @param rsv111SchKbn rsv111SchKbn
     */
    public void setRsv111SchKbn(int rsv111SchKbn) {
        rsv111SchKbn__ = rsv111SchKbn;
    }
    /**
     * <p>rsv110SchGroupLabel を取得します。
     * @return rsv110SchGroupLabel
     */
    public List<ExtendedLabelValueModel> getRsv110SchGroupLabel() {
        return rsv110SchGroupLabel__;
    }
    /**
     * <p>rsv110SchGroupLabel をセットします。
     * @param rsv110SchGroupLabel rsv110SchGroupLabel
     */
    public void setRsv110SchGroupLabel(
            List<ExtendedLabelValueModel> rsv110SchGroupLabel) {
        rsv110SchGroupLabel__ = rsv110SchGroupLabel;
    }
    /**
     * <p>rsv110SchNotAccessGroupList を取得します。
     * @return rsv110SchNotAccessGroupList
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SchNotAccessGroupList__
     */
    public List<Integer> getRsv110SchNotAccessGroupList() {
        return rsv110SchNotAccessGroupList__;
    }
    /**
     * <p>rsv110SchNotAccessGroupList をセットします。
     * @param rsv110SchNotAccessGroupList rsv110SchNotAccessGroupList
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SchNotAccessGroupList__
     */
    public void setRsv110SchNotAccessGroupList(
            List<Integer> rsv110SchNotAccessGroupList) {
        rsv110SchNotAccessGroupList__ = rsv110SchNotAccessGroupList;
    }
    /**
     * <p>rsv110SchCrangeKbn を取得します。
     * @return rsv110SchCrangeKbn
     */
    public int getRsv110SchCrangeKbn() {
        return rsv110SchCrangeKbn__;
    }
    /**
     * <p>rsv110SchCrangeKbn をセットします。
     * @param rsv110SchCrangeKbn rsv110SchCrangeKbn
     */
    public void setRsv110SchCrangeKbn(int rsv110SchCrangeKbn) {
        rsv110SchCrangeKbn__ = rsv110SchCrangeKbn;
    }
    /**
     * <p>rsv110SchUserUI を取得します。
     * @return rsv110SchUserUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SchUserUI__
     */
    public RsvScheduleSelector getRsv110SchUserUI() {
        return rsv110SchUserUI__;
    }
    /**
     * <p>rsv110SchUserUI をセットします。
     * @param rsv110SchUserUI rsv110SchUserUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SchUserUI__
     */
    public void setRsv110SchUserUI(RsvScheduleSelector rsv110SchUserUI) {
        rsv110SchUserUI__ = rsv110SchUserUI;
    }
    /**
     * @return smailSeniFlg
     */
    public boolean isSmailSeniFlg() {
        return smailSeniFlg__;
    }
    /**
     * @param smailSeniFlg セットする smailSeniFlg
     */
    public void setSmailSeniFlg(boolean smailSeniFlg) {
        smailSeniFlg__ = smailSeniFlg;
    }
    /**
     * @return rsvDataFlg
     */
    public boolean isRsvDataFlg() {
        return rsvDataFlg__;
    }
    /**
     * @param rsvDataFlg セットする rsvDataFlg
     */
    public void setRsvDataFlg(boolean rsvDataFlg) {
        rsvDataFlg__ = rsvDataFlg;
    }
    /**
     * <p>rsv110rejectDel を取得します。
     * @return rsv110rejectDel
     */
    public int getRsv110rejectDel() {
        return rsv110rejectDel__;
    }
    /**
     * <p>rsv110rejectDel をセットします。
     * @param rsv110rejectDel rsv110rejectDel
     */
    public void setRsv110rejectDel(int rsv110rejectDel) {
        rsv110rejectDel__ = rsv110rejectDel;
    }
    /**
     * <p>rsv110ApprBtnFlg を取得します。
     * @return rsv110ApprBtnFlg
     */
    public int getRsv110ApprBtnFlg() {
        return rsv110ApprBtnFlg__;
    }
    /**
     * <p>rsv110ApprBtnFlg をセットします。
     * @param rsv110ApprBtnFlg rsv110ApprBtnFlg
     */
    public void setRsv110ApprBtnFlg(int rsv110ApprBtnFlg) {
        rsv110ApprBtnFlg__ = rsv110ApprBtnFlg;
    }
    /**
     * <p>rsv110MokutekiEsc を取得します。
     * @return rsv110MokutekiEsc
     */
    public String getRsv110MokutekiEsc() {
        return rsv110MokutekiEsc__;
    }
    /**
     * <p>rsv110MokutekiEsc をセットします。
     * @param rsv110MokutekiEsc rsv110MokutekiEsc
     */
    public void setRsv110MokutekiEsc(String rsv110MokutekiEsc) {
        rsv110MokutekiEsc__ = rsv110MokutekiEsc;
    }
    /**
     * <p>rsv110SisetuKbn を取得します。
     * @return rsv110SisetuKbn
     */
    public int getRsv110SisetuKbn() {
        return rsv110SisetuKbn__;
    }
    /**
     * <p>rsv110SisetuKbn をセットします。
     * @param rsv110SisetuKbn rsv110SisetuKbn
     */
    public void setRsv110SisetuKbn(int rsv110SisetuKbn) {
        rsv110SisetuKbn__ = rsv110SisetuKbn;
    }
    /**
     * <p>rsv110Busyo を取得します。
     * @return rsv110Busyo
     */
    public String getRsv110Busyo() {
        return rsv110Busyo__;
    }
    /**
     * <p>rsv110Busyo をセットします。
     * @param rsv110Busyo rsv110Busyo
     */
    public void setRsv110Busyo(String rsv110Busyo) {
        rsv110Busyo__ = rsv110Busyo;
    }
    /**
     * <p>rsv110UseName を取得します。
     * @return rsv110UseName
     */
    public String getRsv110UseName() {
        return rsv110UseName__;
    }
    /**
     * <p>rsv110UseName をセットします。
     * @param rsv110UseName rsv110UseName
     */
    public void setRsv110UseName(String rsv110UseName) {
        rsv110UseName__ = rsv110UseName;
    }
    /**
     * <p>rsv110UseNum を取得します。
     * @return rsv110UseNum
     */
    public String getRsv110UseNum() {
        return rsv110UseNum__;
    }
    /**
     * <p>rsv110UseNum をセットします。
     * @param rsv110UseNum rsv110UseNum
     */
    public void setRsv110UseNum(String rsv110UseNum) {
        rsv110UseNum__ = rsv110UseNum;
    }
    /**
     * <p>rsv110UseKbn を取得します。
     * @return rsv110UseKbn
     */
    public int getRsv110UseKbn() {
        return rsv110UseKbn__;
    }
    /**
     * <p>rsv110UseKbn をセットします。
     * @param rsv110UseKbn rsv110UseKbn
     */
    public void setRsv110UseKbn(int rsv110UseKbn) {
        rsv110UseKbn__ = rsv110UseKbn;
    }
    /**
     * <p>rsv110Contact を取得します。
     * @return rsv110Contact
     */
    public String getRsv110Contact() {
        return rsv110Contact__;
    }
    /**
     * <p>rsv110Contact をセットします。
     * @param rsv110Contact rsv110Contact
     */
    public void setRsv110Contact(String rsv110Contact) {
        rsv110Contact__ = rsv110Contact;
    }
    /**
     * <p>rsv110Guide を取得します。
     * @return rsv110Guide
     */
    public String getRsv110Guide() {
        return rsv110Guide__;
    }
    /**
     * <p>rsv110Guide をセットします。
     * @param rsv110Guide rsv110Guide
     */
    public void setRsv110Guide(String rsv110Guide) {
        rsv110Guide__ = rsv110Guide;
    }
    /**
     * <p>rsv110ParkNum を取得します。
     * @return rsv110ParkNum
     */
    public String getRsv110ParkNum() {
        return rsv110ParkNum__;
    }
    /**
     * <p>rsv110ParkNum をセットします。
     * @param rsv110ParkNum rsv110ParkNum
     */
    public void setRsv110ParkNum(String rsv110ParkNum) {
        rsv110ParkNum__ = rsv110ParkNum;
    }
    /**
     * <p>rsv110Dest を取得します。
     * @return rsv110Dest
     */
    public String getRsv110Dest() {
        return rsv110Dest__;
    }
    /**
     * <p>rsv110Dest をセットします。
     * @param rsv110Dest rsv110Dest
     */
    public void setRsv110Dest(String rsv110Dest) {
        rsv110Dest__ = rsv110Dest;
    }
    /**
     * <p>rsv111Busyo を取得します。
     * @return rsv111Busyo
     */
    public String getRsv111Busyo() {
        return rsv111Busyo__;
    }
    /**
     * <p>rsv111Busyo をセットします。
     * @param rsv111Busyo rsv111Busyo
     */
    public void setRsv111Busyo(String rsv111Busyo) {
        rsv111Busyo__ = rsv111Busyo;
    }
    /**
     * <p>rsv111UseName を取得します。
     * @return rsv111UseName
     */
    public String getRsv111UseName() {
        return rsv111UseName__;
    }
    /**
     * <p>rsv111UseName をセットします。
     * @param rsv111UseName rsv111UseName
     */
    public void setRsv111UseName(String rsv111UseName) {
        rsv111UseName__ = rsv111UseName;
    }
    /**
     * <p>rsv111UseNum を取得します。
     * @return rsv111UseNum
     */
    public String getRsv111UseNum() {
        return rsv111UseNum__;
    }
    /**
     * <p>rsv111UseNum をセットします。
     * @param rsv111UseNum rsv111UseNum
     */
    public void setRsv111UseNum(String rsv111UseNum) {
        rsv111UseNum__ = rsv111UseNum;
    }
    /**
     * <p>rsv111UseKbn を取得します。
     * @return rsv111UseKbn
     */
    public int getRsv111UseKbn() {
        return rsv111UseKbn__;
    }
    /**
     * <p>rsv111UseKbn をセットします。
     * @param rsv111UseKbn rsv111UseKbn
     */
    public void setRsv111UseKbn(int rsv111UseKbn) {
        rsv111UseKbn__ = rsv111UseKbn;
    }
    /**
     * <p>rsv111Contact を取得します。
     * @return rsv111Contact
     */
    public String getRsv111Contact() {
        return rsv111Contact__;
    }
    /**
     * <p>rsv111Contact をセットします。
     * @param rsv111Contact rsv111Contact
     */
    public void setRsv111Contact(String rsv111Contact) {
        rsv111Contact__ = rsv111Contact;
    }
    /**
     * <p>rsv111Guide を取得します。
     * @return rsv111Guide
     */
    public String getRsv111Guide() {
        return rsv111Guide__;
    }
    /**
     * <p>rsv111Guide をセットします。
     * @param rsv111Guide rsv111Guide
     */
    public void setRsv111Guide(String rsv111Guide) {
        rsv111Guide__ = rsv111Guide;
    }
    /**
     * <p>rsv111ParkNum を取得します。
     * @return rsv111ParkNum
     */
    public String getRsv111ParkNum() {
        return rsv111ParkNum__;
    }
    /**
     * <p>rsv111ParkNum をセットします。
     * @param rsv111ParkNum rsv111ParkNum
     */
    public void setRsv111ParkNum(String rsv111ParkNum) {
        rsv111ParkNum__ = rsv111ParkNum;
    }
    /**
     * <p>rsv111Dest を取得します。
     * @return rsv111Dest
     */
    public String getRsv111Dest() {
        return rsv111Dest__;
    }
    /**
     * <p>rsv111Dest をセットします。
     * @param rsv111Dest rsv111Dest
     */
    public void setRsv111Dest(String rsv111Dest) {
        rsv111Dest__ = rsv111Dest;
    }
    /**
     * <p>rsv110PrintKbn を取得します。
     * @return rsv110PrintKbn
     */
    public int getRsv110PrintKbn() {
        return rsv110PrintKbn__;
    }
    /**
     * <p>rsv110PrintKbn をセットします。
     * @param rsv110PrintKbn rsv110PrintKbn
     */
    public void setRsv110PrintKbn(int rsv110PrintKbn) {
        rsv110PrintKbn__ = rsv110PrintKbn;
    }
    /**
     * <p>rsv111PrintKbn を取得します。
     * @return rsv111PrintKbn
     */
    public int getRsv111PrintKbn() {
        return rsv111PrintKbn__;
    }
    /**
     * <p>rsv111PrintKbn をセットします。
     * @param rsv111PrintKbn rsv111PrintKbn
     */
    public void setRsv111PrintKbn(int rsv111PrintKbn) {
        rsv111PrintKbn__ = rsv111PrintKbn;
    }

    /**
     * <br>[機  能] 指定した数字が利用区分に当てはまるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param useKbn 利用区分
     * @return true：OK  false：範囲外
     */
    private boolean __isCheckUseKbn(int useKbn) {

        int kbn = Integer.valueOf(useKbn);
        if (kbn == GSConstReserve.RSY_USE_KBN_NOSET
                || kbn == GSConstReserve.RSY_USE_KBN_KAIGI
                || kbn == GSConstReserve.RSY_USE_KBN_KENSYU
                || kbn == GSConstReserve.RSY_USE_KBN_OTHER) {
            return true;
        }
        return false;
    }
    /**
     * <p>rsv111RsrMonthOfYearly を取得します。
     * @return rsv111RsrMonthOfYearly
     */
    public int getRsv111RsrMonthOfYearly() {
        return rsv111RsrMonthOfYearly__;
    }
    /**
     * <p>rsv111RsrMonthOfYearly をセットします。
     * @param rsv111RsrMonthOfYearly rsv111RsrMonthOfYearly
     */
    public void setRsv111RsrMonthOfYearly(int rsv111RsrMonthOfYearly) {
        rsv111RsrMonthOfYearly__ = rsv111RsrMonthOfYearly;
    }
    /**
     * <p>rsv111RsrDayOfYearly を取得します。
     * @return rsv111RsrDayOfYearly
     */
    public int getRsv111RsrDayOfYearly() {
        return rsv111RsrDayOfYearly__;
    }
    /**
     * <p>rsv111RsrDayOfYearly をセットします。
     * @param rsv111RsrDayOfYearly rsv111RsrDayOfYearly
     */
    public void setRsv111RsrDayOfYearly(int rsv111RsrDayOfYearly) {
        rsv111RsrDayOfYearly__ = rsv111RsrDayOfYearly;
    }
    /**
     * <p>rsv110AmFrHour を取得します。
     * @return rsv110AmFrHour
     */
    public int getRsv110AmFrHour() {
        return rsv110AmFrHour__;
    }
    /**
     * <p>rsv110AmFrHour をセットします。
     * @param rsv110AmFrHour rsv110AmFrHour
     */
    public void setRsv110AmFrHour(int rsv110AmFrHour) {
        rsv110AmFrHour__ = rsv110AmFrHour;
    }
    /**
     * <p>rsv110AmFrMin を取得します。
     * @return rsv110AmFrMin
     */
    public int getRsv110AmFrMin() {
        return rsv110AmFrMin__;
    }
    /**
     * <p>rsv110AmFrMin をセットします。
     * @param rsv110AmFrMin rsv110AmFrMin
     */
    public void setRsv110AmFrMin(int rsv110AmFrMin) {
        rsv110AmFrMin__ = rsv110AmFrMin;
    }
    /**
     * <p>rsv110AmToHour を取得します。
     * @return rsv110AmToHour
     */
    public int getRsv110AmToHour() {
        return rsv110AmToHour__;
    }
    /**
     * <p>rsv110AmToHour をセットします。
     * @param rsv110AmToHour rsv110AmToHour
     */
    public void setRsv110AmToHour(int rsv110AmToHour) {
        rsv110AmToHour__ = rsv110AmToHour;
    }
    /**
     * <p>rsv110AmToMin を取得します。
     * @return rsv110AmToMin
     */
    public int getRsv110AmToMin() {
        return rsv110AmToMin__;
    }
    /**
     * <p>rsv110AmToMin をセットします。
     * @param rsv110AmToMin rsv110AmToMin
     */
    public void setRsv110AmToMin(int rsv110AmToMin) {
        rsv110AmToMin__ = rsv110AmToMin;
    }
    /**
     * <p>rsv110PmFrHour を取得します。
     * @return rsv110PmFrHour
     */
    public int getRsv110PmFrHour() {
        return rsv110PmFrHour__;
    }
    /**
     * <p>rsv110PmFrHour をセットします。
     * @param rsv110PmFrHour rsv110PmFrHour
     */
    public void setRsv110PmFrHour(int rsv110PmFrHour) {
        rsv110PmFrHour__ = rsv110PmFrHour;
    }
    /**
     * <p>rsv110PmFrMin を取得します。
     * @return rsv110PmFrMin
     */
    public int getRsv110PmFrMin() {
        return rsv110PmFrMin__;
    }
    /**
     * <p>rsv110PmFrMin をセットします。
     * @param rsv110PmFrMin rsv110PmFrMin
     */
    public void setRsv110PmFrMin(int rsv110PmFrMin) {
        rsv110PmFrMin__ = rsv110PmFrMin;
    }
    /**
     * <p>rsv110PmToHour を取得します。
     * @return rsv110PmToHour
     */
    public int getRsv110PmToHour() {
        return rsv110PmToHour__;
    }
    /**
     * <p>rsv110PmToHour をセットします。
     * @param rsv110PmToHour rsv110PmToHour
     */
    public void setRsv110PmToHour(int rsv110PmToHour) {
        rsv110PmToHour__ = rsv110PmToHour;
    }
    /**
     * <p>rsv110PmToMin を取得します。
     * @return rsv110PmToMin
     */
    public int getRsv110PmToMin() {
        return rsv110PmToMin__;
    }
    /**
     * <p>rsv110PmToMin をセットします。
     * @param rsv110PmToMin rsv110PmToMin
     */
    public void setRsv110PmToMin(int rsv110PmToMin) {
        rsv110PmToMin__ = rsv110PmToMin;
    }
    /**
     * <p>rsv110AllDayFrHour を取得します。
     * @return rsv110AllDayFrHour
     */
    public int getRsv110AllDayFrHour() {
        return rsv110AllDayFrHour__;
    }
    /**
     * <p>rsv110AllDayFrHour をセットします。
     * @param rsv110AllDayFrHour rsv110AllDayFrHour
     */
    public void setRsv110AllDayFrHour(int rsv110AllDayFrHour) {
        rsv110AllDayFrHour__ = rsv110AllDayFrHour;
    }
    /**
     * <p>rsv110AllDayFrMin を取得します。
     * @return rsv110AllDayFrMin
     */
    public int getRsv110AllDayFrMin() {
        return rsv110AllDayFrMin__;
    }
    /**
     * <p>rsv110AllDayFrMin をセットします。
     * @param rsv110AllDayFrMin rsv110AllDayFrMin
     */
    public void setRsv110AllDayFrMin(int rsv110AllDayFrMin) {
        rsv110AllDayFrMin__ = rsv110AllDayFrMin;
    }
    /**
     * <p>rsv110AllDayToHour を取得します。
     * @return rsv110AllDayToHour
     */
    public int getRsv110AllDayToHour() {
        return rsv110AllDayToHour__;
    }
    /**
     * <p>rsv110AllDayToHour をセットします。
     * @param rsv110AllDayToHour rsv110AllDayToHour
     */
    public void setRsv110AllDayToHour(int rsv110AllDayToHour) {
        rsv110AllDayToHour__ = rsv110AllDayToHour;
    }
    /**
     * <p>rsv110AllDayToMin を取得します。
     * @return rsv110AllDayToMin
     */
    public int getRsv110AllDayToMin() {
        return rsv110AllDayToMin__;
    }
    /**
     * <p>rsv110AllDayToMin をセットします。
     * @param rsv110AllDayToMin rsv110AllDayToMin
     */
    public void setRsv110AllDayToMin(int rsv110AllDayToMin) {
        rsv110AllDayToMin__ = rsv110AllDayToMin;
    }
    /**
     * <p>rsv110EditUsrJKbn を取得します。
     * @return rsv110EditUsrJKbn
     */
    public int getRsv110EditUsrJKbn() {
        return rsv110EditUsrJKbn__;
    }
    /**
     * <p>rsv110UsrJKbn をセットします。
     * @param rsv110UsrJKbn rsv110UsrJKbn
     */
    public void setRsv110EditUsrJKbn(int rsv110UsrJKbn) {
        rsv110EditUsrJKbn__ = rsv110UsrJKbn;
    }
    /**
     * <p>rsv110AddUsrJKbn を取得します。
     * @return rsv110AddUsrJKbn
     */
    public int getRsv110AddUsrJKbn() {
        return rsv110AddUsrJKbn__;
    }
    /**
     * <p>rsv110UsrJKbn をセットします。
     * @param rsv110UsrJKbn rsv110UsrJKbn
     */
    public void setRsv110AddUsrJKbn(int rsv110UsrJKbn) {
        rsv110AddUsrJKbn__ = rsv110UsrJKbn;
    }
    /**
     * <p>rsv110PubDefUsrSid を取得します。
     * @return rsv110PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubDefUsrSid__
     */
    public int getRsv110PubDefUsrSid() {
        return rsv110PubDefUsrSid__;
    }
    /**
     * <p>rsv110PubDefUsrSid をセットします。
     * @param rsv110PubDefUsrSid rsv110PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubDefUsrSid__
     */
    public void setRsv110PubDefUsrSid(int rsv110PubDefUsrSid) {
        rsv110PubDefUsrSid__ = rsv110PubDefUsrSid;
    }
    /**
     * <p>rsv110PubUserGroup を取得します。
     * @return rsv110PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubUserGroup__
     */
    public String getRsv110PubUserGroup() {
        return rsv110PubUserGroup__;
    }
    /**
     * <p>rsv110PubUserGroup をセットします。
     * @param rsv110PubUserGroup rsv110PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubUserGroup__
     */
    public void setRsv110PubUserGroup(String rsv110PubUserGroup) {
        rsv110PubUserGroup__ = rsv110PubUserGroup;
    }
    /**
     * <p>rsv110PubUsrGrpSid を取得します。
     * @return rsv110PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubUsrGrpSid__
     */
    public String[] getRsv110PubUsrGrpSid() {
        return rsv110PubUsrGrpSid__;
    }
    /**
     * <p>rsv110PubUsrGrpSid をセットします。
     * @param rsv110PubUsrGrpSid rsv110PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubUsrGrpSid__
     */
    public void setRsv110PubUsrGrpSid(String[] rsv110PubUsrGrpSid) {
        rsv110PubUsrGrpSid__ = rsv110PubUsrGrpSid;
    }
    /**
     * <p>rsv110PubUsrGrpUI を取得します。
     * @return rsv110PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubUsrGrpUI__
     */
    public UserGroupSelector getRsv110PubUsrGrpUI() {
        return rsv110PubUsrGrpUI__;
    }
    /**
     * <p>rsv110PubUsrGrpUI をセットします。
     * @param rsv110PubUsrGrpUI rsv110PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110PubUsrGrpUI__
     */
    public void setRsv110PubUsrGrpUI(UserGroupSelector rsv110PubUsrGrpUI) {
        rsv110PubUsrGrpUI__ = rsv110PubUsrGrpUI;
    }
    /**
     * <p>rsv111PubUserGroup を取得します。
     * @return rsv111PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111PubUserGroup__
     */
    public String getRsv111PubUserGroup() {
        return rsv111PubUserGroup__;
    }
    /**
     * <p>rsv111PubUserGroup をセットします。
     * @param rsv111PubUserGroup rsv111PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111PubUserGroup__
     */
    public void setRsv111PubUserGroup(String rsv111PubUserGroup) {
        rsv111PubUserGroup__ = rsv111PubUserGroup;
    }
    /**
     * <p>rsv111PubUsrGrpSid を取得します。
     * @return rsv111PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111PubUsrGrpSid__
     */
    public String[] getRsv111PubUsrGrpSid() {
        return rsv111PubUsrGrpSid__;
    }
    /**
     * <p>rsv111PubUsrGrpSid をセットします。
     * @param rsv111PubUsrGrpSid rsv111PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111PubUsrGrpSid__
     */
    public void setRsv111PubUsrGrpSid(String[] rsv111PubUsrGrpSid) {
        rsv111PubUsrGrpSid__ = rsv111PubUsrGrpSid;
    }
    /**
     * <p>rsv110PubUsrGrpList を取得します。
     * @return rsv110PubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110ParamModel#rsv110PubUsrGrpList__
     */
    public List<UsrLabelValueBean> getRsv110PubUsrGrpList() {
        return rsv110PubUsrGrpList__;
    }
    /**
     * <p>rsv110PubUsrGrpList をセットします。
     * @param rsv110PubUsrGrpList rsv110PubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110ParamModel#rsv110PubUsrGrpList__
     */
    public void setRsv110PubUsrGrpList(
            List<UsrLabelValueBean> rsv110PubUsrGrpList) {
        rsv110PubUsrGrpList__ = rsv110PubUsrGrpList;
    }
    /**
     * <p>rsv110SelectedDateFr を取得します。
     * @return rsv110SelectedDateFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedDateFr__
     */
    public String getRsv110SelectedDateFr() {
        return rsv110SelectedDateFr__;
    }
    /**
     * <p>rsv110SelectedDateFr をセットします。
     * @param rsv110SelectedDateFr rsv110SelectedDateFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedDateFr__
     */
    public void setRsv110SelectedDateFr(String rsv110SelectedDateFr) {
        rsv110SelectedDateFr__ = rsv110SelectedDateFr;
    }
    /**
     * <p>rsv110SelectedDateTo を取得します。
     * @return rsv110SelectedDateTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedDateTo__
     */
    public String getRsv110SelectedDateTo() {
        return rsv110SelectedDateTo__;
    }
    /**
     * <p>rsv110SelectedDateTo をセットします。
     * @param rsv110SelectedDateTo rsv110SelectedDateTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedDateTo__
     */
    public void setRsv110SelectedDateTo(String rsv110SelectedDateTo) {
        rsv110SelectedDateTo__ = rsv110SelectedDateTo;
    }
    /**
     * <p>rsv110SelectedTimeFr を取得します。
     * @return rsv110SelectedTimeFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedTimeFr__
     */
    public String getRsv110SelectedTimeFr() {
        return rsv110SelectedTimeFr__;
    }
    /**
     * <p>rsv110SelectedTimeFr をセットします。
     * @param rsv110SelectedTimeFr rsv110SelectedTimeFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedTimeFr__
     */
    public void setRsv110SelectedTimeFr(String rsv110SelectedTimeFr) {
        rsv110SelectedTimeFr__ = rsv110SelectedTimeFr;
    }
    /**
     * <p>rsv110SelectedTimeTo を取得します。
     * @return rsv110SelectedTimeTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedTimeTo__
     */
    public String getRsv110SelectedTimeTo() {
        return rsv110SelectedTimeTo__;
    }
    /**
     * <p>rsv110SelectedTimeTo をセットします。
     * @param rsv110SelectedTimeTo rsv110SelectedTimeTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110SelectedTimeTo__
     */
    public void setRsv110SelectedTimeTo(String rsv110SelectedTimeTo) {
        rsv110SelectedTimeTo__ = rsv110SelectedTimeTo;
    }
    /**
     * <p>rsv111RsrDateFr を取得します。
     * @return rsv111RsrDateFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrDateFr__
     */
    public String getRsv111RsrDateFr() {
        return rsv111RsrDateFr__;
    }
    /**
     * <p>rsv111RsrDateFr をセットします。
     * @param rsv111RsrDateFr rsv111RsrDateFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrDateFr__
     */
    public void setRsv111RsrDateFr(String rsv111RsrDateFr) {
        rsv111RsrDateFr__ = rsv111RsrDateFr;
    }
    /**
     * <p>rsv111RsrDateTo を取得します。
     * @return rsv111RsrDateTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrDateTo__
     */
    public String getRsv111RsrDateTo() {
        return rsv111RsrDateTo__;
    }
    /**
     * <p>rsv111RsrDateTo をセットします。
     * @param rsv111RsrDateTo rsv111RsrDateTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrDateTo__
     */
    public void setRsv111RsrDateTo(String rsv111RsrDateTo) {
        rsv111RsrDateTo__ = rsv111RsrDateTo;
    }
    /**
     * <p>rsv111RsrTimeFr を取得します。
     * @return rsv111RsrTimeFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrTimeFr__
     */
    public String getRsv111RsrTimeFr() {
        return rsv111RsrTimeFr__;
    }
    /**
     * <p>rsv111RsrTimeFr をセットします。
     * @param rsv111RsrTimeFr rsv111RsrTimeFr
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrTimeFr__
     */
    public void setRsv111RsrTimeFr(String rsv111RsrTimeFr) {
        rsv111RsrTimeFr__ = rsv111RsrTimeFr;
    }
    /**
     * <p>rsv111RsrTimeTo を取得します。
     * @return rsv111RsrTimeTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrTimeTo__
     */
    public String getRsv111RsrTimeTo() {
        return rsv111RsrTimeTo__;
    }
    /**
     * <p>rsv111RsrTimeTo をセットします。
     * @param rsv111RsrTimeTo rsv111RsrTimeTo
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111RsrTimeTo__
     */
    public void setRsv111RsrTimeTo(String rsv111RsrTimeTo) {
        rsv111RsrTimeTo__ = rsv111RsrTimeTo;
    }
    /**
     * <p>racHourDiv を取得します。
     * @return racHourDiv
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#racHourDiv__
     */
    public int getRacHourDiv() {
        return racHourDiv__;
    }
    /**
     * <p>racHourDiv をセットします。
     * @param racHourDiv racHourDiv
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#racHourDiv__
     */
    public void setRacHourDiv(int racHourDiv) {
        racHourDiv__ = racHourDiv;
    }
    /**
     * <p>rsv110AuId を取得します。
     * @return rsv110AuId
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110AuId__
     */
    public int getRsv110AuId() {
        return rsv110AuId__;
    }
    /**
     * <p>rsv110AuId をセットします。
     * @param rsv110AuId rsv110AuId
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110AuId__
     */
    public void setRsv110AuId(int rsv110AuId) {
        rsv110AuId__ = rsv110AuId;
    }
    /**
     * <p>rsv110EuId を取得します。
     * @return rsv110EuId
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110EuId__
     */
    public int getRsv110EuId() {
        return rsv110EuId__;
    }
    /**
     * <p>rsv110EuId をセットします。
     * @param rsv110EuId rsv110EuId
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv110EuId__
     */
    public void setRsv110EuId(int rsv110EuId) {
        rsv110EuId__ = rsv110EuId;
    }
}