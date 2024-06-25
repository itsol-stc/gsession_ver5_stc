package jp.groupsession.v2.prj.prj020;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.prj030.Prj030ParamModel;
import jp.groupsession.v2.prj.prj150.model.Prj150DspMdl;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] プロジェクト登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj020ParamModel extends Prj030ParamModel {

    /** プラグインID */
    private String prj020pluginId__ = GSConstProject.PLUGIN_ID_PROJECT;

    //入力項目
    /** マイプロジェクト区分 */
    private int prj020prjMyKbn__;
    /** プロジェクトID */
    private String prj020prjId__;
    /** プロジェクト名称 */
    private String prj020prjName__;
    /** プロジェクト略称 */
    private String prj020prjNameS__;
    /** 予算 */
    private String prj020yosan__;
    /** 公開・非公開 */
    private int prj020koukai__;
    /** 開始年 */
    private String prj020startYear__;
    /** 開始月 */
    private String prj020startMonth__;
    /** 開始日 */
    private String prj020startDay__;
    /** 終了年 */
    private String prj020endYear__;
    /** 終了月 */
    private String prj020endMonth__;
    /** 終了日 */
    private String prj020endDay__;
    /** 状態 */
    private int prj020status__;
    /** 目標・目的 */
    private String prj020mokuhyou__;
    /** 内容 */
    private String prj020naiyou__;
    /** グループ(所属メンバー) */
    private int prj020group__;
    /** メンバー(所属メンバー) */
    private String[] prj020syozokuMember__;
    /** 未所属ユーザ(所属メンバー) */
    private String[] prj020user__;
    /** 所属メンバー */
    private String[] prj020hdnMember__;
    /** 管理者(プロジェクト管理者) */
    private String[] prj020adminMember__;
    /** メンバー(プロジェクト管理者) */
    private String[] prj020prjMember__;
    /** プロジェクト管理者 */
    private String[] prj020hdnAdmin__;
    /** 編集権限 */
    private int prj020kengen__;
    /** コピー元プロジェクトSID */
    private int copyProjectSid__;
    /** ショートメール通知 */
    private int prj020smailKbn__;


    /** 開始年月日 */
    private String prj020startDate__;
    /** 終了年月日 */
    private String prj020endDate__;

    //表示項目
    /** グループ(所属メンバー)ラベル */
    private List<LabelValueBean> groupLabel__;
    /** メンバー(所属メンバー)ラベル */
    private List<UsrLabelValueBean> syozokuMemberLabel__;
    /** 未所属ユーザ(所属メンバー)ラベル */
    private List<UsrLabelValueBean> userLabel__;

    /** 状態ラベル */
    private List<LabelValueBean> statusLabel__;

    //プロジェクトメンバ設定外部キー項目
    /** 会社SID */
    private String[] prj150CompanySid__ = null;
    /** 会社拠点SID */
    private String[] prj150CompanyBaseSid__ = null;
    /** 担当者(アドレス情報)save */
    private String[] prj150AddressIdSv__ = null;
    /** アドレス情報 */
    private List<Prj150DspMdl> prj020AddDataList__ = null;
    /** アドレスメンバが登録されているかのフラグ */
    private int prj020AddMemAllDelFlg__ = 0;
    /** プロジェクト編集画面表示フラグ */
    private int prj020EditDspFlg__ = 0;
    /** ユーザを削除対象 */
    private int prj020UsrDel__ = 0;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String prj020ScrollFlg__ = "0";
    /** バイナリSID */
    private Long prj020BinSid__ = new Long(0);
    /** アイコンファイル名 */
    private String prj020IcoName__;
    /** アイコンファイル保存名 */
    private String prj020IcoSaveName__;
    /** アイコンセットフラグ */
    private int prj020IcoSetFlg__ = 0;

    /** 初期表示フラグ */
    private boolean prj020initFlg__ = true;
    /** 所属メンバー SIDのみ */
    private String[] prj020hdnMemberSid__;
    /** 所属メンバー UI*/
    private UserGroupSelector prj020hdnMemberUI__ = null;
    /** プロジェクト管理者 UI*/
    private Prj020AdminMemberSelector prj020adminSelectUI__ = null;

    /** グループSID 管理者 */
    private int prj020groupSidAdm__ = Integer.parseInt(GSConstProject.GROUP_COMBO_VALUE_USER);

    /**
     * @return prj020AddMemAllDelFlg
     */
    public int getPrj020AddMemAllDelFlg() {
        return prj020AddMemAllDelFlg__;
    }

    /**
     * @param prj020AddMemAllDelFlg 設定する prj020AddMemAllDelFlg
     */
    public void setPrj020AddMemAllDelFlg(int prj020AddMemAllDelFlg) {
        prj020AddMemAllDelFlg__ = prj020AddMemAllDelFlg;
    }

    /**
     * <p>prj020prjId を取得します。
     * @return prj020prjId
     */
    public String getPrj020prjId() {
        return prj020prjId__;
    }

    /**
     * <p>prj020prjId をセットします。
     * @param prj020prjId prj020prjId
     */
    public void setPrj020prjId(String prj020prjId) {
        prj020prjId__ = prj020prjId;
    }

    /**
     * <p>prj020prjName を取得します。
     * @return prj020prjName
     */
    public String getPrj020prjName() {
        return prj020prjName__;
    }

    /**
     * <p>prj020prjName をセットします。
     * @param prj020prjName prj020prjName
     */
    public void setPrj020prjName(String prj020prjName) {
        prj020prjName__ = prj020prjName;
    }

    /**
     * <p>prj020prjNameS を取得します。
     * @return prj020prjNameS
     */
    public String getPrj020prjNameS() {
        return prj020prjNameS__;
    }

    /**
     * <p>prj020prjNameS をセットします。
     * @param prj020prjNameS prj020prjNameS
     */
    public void setPrj020prjNameS(String prj020prjNameS) {
        prj020prjNameS__ = prj020prjNameS;
    }

    /**
     * <p>prj020yosan を取得します。
     * @return prj020yosan
     */
    public String getPrj020yosan() {
        return prj020yosan__;
    }

    /**
     * <p>prj020yosan をセットします。
     * @param prj020yosan prj020yosan
     */
    public void setPrj020yosan(String prj020yosan) {
        prj020yosan__ = prj020yosan;
    }

    /**
     * <p>prj020koukai を取得します。
     * @return prj020koukai
     */
    public int getPrj020koukai() {
        return prj020koukai__;
    }

    /**
     * <p>prj020koukai をセットします。
     * @param prj020koukai prj020koukai
     */
    public void setPrj020koukai(int prj020koukai) {
        prj020koukai__ = prj020koukai;
    }

    /**
     * <p>prj020startYear を取得します。
     * @return prj020startYear
     */
    public String getPrj020startYear() {
        return prj020startYear__;
    }

    /**
     * <p>prj020startYear をセットします。
     * @param prj020startYear prj020startYear
     */
    public void setPrj020startYear(String prj020startYear) {
        prj020startYear__ = prj020startYear;
    }

    /**
     * <p>prj020startMonth を取得します。
     * @return prj020startMonth
     */
    public String getPrj020startMonth() {
        return prj020startMonth__;
    }

    /**
     * <p>prj020startMonth をセットします。
     * @param prj020startMonth prj020startMonth
     */
    public void setPrj020startMonth(String prj020startMonth) {
        prj020startMonth__ = prj020startMonth;
    }

    /**
     * <p>prj020startDay を取得します。
     * @return prj020startDay
     */
    public String getPrj020startDay() {
        return prj020startDay__;
    }

    /**
     * <p>prj020startDay をセットします。
     * @param prj020startDay prj020startDay
     */
    public void setPrj020startDay(String prj020startDay) {
        prj020startDay__ = prj020startDay;
    }

    /**
     * <p>prj020endYear を取得します。
     * @return prj020endYear
     */
    public String getPrj020endYear() {
        return prj020endYear__;
    }

    /**
     * <p>prj020endYear をセットします。
     * @param prj020endYear prj020endYear
     */
    public void setPrj020endYear(String prj020endYear) {
        prj020endYear__ = prj020endYear;
    }

    /**
     * <p>prj020endMonth を取得します。
     * @return prj020endMonth
     */
    public String getPrj020endMonth() {
        return prj020endMonth__;
    }

    /**
     * <p>prj020endMonth をセットします。
     * @param prj020endMonth prj020endMonth
     */
    public void setPrj020endMonth(String prj020endMonth) {
        prj020endMonth__ = prj020endMonth;
    }

    /**
     * <p>prj020endDay を取得します。
     * @return prj020endDay
     */
    public String getPrj020endDay() {
        return prj020endDay__;
    }

    /**
     * <p>prj020endDay をセットします。
     * @param prj020endDay prj020endDay
     */
    public void setPrj020endDay(String prj020endDay) {
        prj020endDay__ = prj020endDay;
    }

    /**
     * <p>prj020status を取得します。
     * @return prj020status
     */
    public int getPrj020status() {
        return prj020status__;
    }

    /**
     * <p>prj020status をセットします。
     * @param prj020status prj020status
     */
    public void setPrj020status(int prj020status) {
        prj020status__ = prj020status;
    }

    /**
     * <p>prj020mokuhyou を取得します。
     * @return prj020mokuhyou
     */
    public String getPrj020mokuhyou() {
        return prj020mokuhyou__;
    }

    /**
     * <p>prj020mokuhyou をセットします。
     * @param prj020mokuhyou prj020mokuhyou
     */
    public void setPrj020mokuhyou(String prj020mokuhyou) {
        prj020mokuhyou__ = prj020mokuhyou;
    }

    /**
     * <p>prj020naiyou を取得します。
     * @return prj020naiyou
     */
    public String getPrj020naiyou() {
        return prj020naiyou__;
    }

    /**
     * <p>prj020naiyou をセットします。
     * @param prj020naiyou prj020naiyou
     */
    public void setPrj020naiyou(String prj020naiyou) {
        prj020naiyou__ = prj020naiyou;
    }

    /**
     * <p>prj020group を取得します。
     * @return prj020group
     */
    public int getPrj020group() {
        return prj020group__;
    }

    /**
     * <p>prj020group をセットします。
     * @param prj020group prj020group
     */
    public void setPrj020group(int prj020group) {
        prj020group__ = prj020group;
    }

    /**
     * <p>prj020syozokuMember を取得します。
     * @return prj020syozokuMember
     */
    public String[] getPrj020syozokuMember() {
        return prj020syozokuMember__;
    }

    /**
     * <p>prj020syozokuMember をセットします。
     * @param prj020syozokuMember prj020syozokuMember
     */
    public void setPrj020syozokuMember(String[] prj020syozokuMember) {
        prj020syozokuMember__ = prj020syozokuMember;
    }

    /**
     * <p>prj020user を取得します。
     * @return prj020user
     */
    public String[] getPrj020user() {
        return prj020user__;
    }

    /**
     * <p>prj020user をセットします。
     * @param prj020user prj020user
     */
    public void setPrj020user(String[] prj020user) {
        prj020user__ = prj020user;
    }

    /**
     * <p>prj020hdnMember を取得します。
     * @return prj020hdnMember
     */
    public String[] getPrj020hdnMember() {
        return prj020hdnMember__;
    }

    /**
     * <p>prj020hdnMember をセットします。
     * @param prj020hdnMember prj020hdnMember
     */
    public void setPrj020hdnMember(String[] prj020hdnMember) {
        prj020hdnMember__ = prj020hdnMember;
    }

    /**
     * <p>prj020adminMember を取得します。
     * @return prj020adminMember
     */
    public String[] getPrj020adminMember() {
        return prj020adminMember__;
    }

    /**
     * <p>prj020adminMember をセットします。
     * @param prj020adminMember prj020adminMember
     */
    public void setPrj020adminMember(String[] prj020adminMember) {
        prj020adminMember__ = prj020adminMember;
    }

    /**
     * <p>prj020prjMember を取得します。
     * @return prj020prjMember
     */
    public String[] getPrj020prjMember() {
        return prj020prjMember__;
    }

    /**
     * <p>prj020prjMember をセットします。
     * @param prj020prjMember prj020prjMember
     */
    public void setPrj020prjMember(String[] prj020prjMember) {
        prj020prjMember__ = prj020prjMember;
    }

    /**
     * <p>prj020hdnAdmin を取得します。
     * @return prj020hdnAdmin
     */
    public String[] getPrj020hdnAdmin() {
        return prj020hdnAdmin__;
    }

    /**
     * <p>prj020hdnAdmin をセットします。
     * @param prj020hdnAdmin prj020hdnAdmin
     */
    public void setPrj020hdnAdmin(String[] prj020hdnAdmin) {
        prj020hdnAdmin__ = prj020hdnAdmin;
    }

    /**
     * <p>prj020kengen を取得します。
     * @return prj020kengen
     */
    public int getPrj020kengen() {
        return prj020kengen__;
    }

    /**
     * <p>prj020kengen をセットします。
     * @param prj020kengen prj020kengen
     */
    public void setPrj020kengen(int prj020kengen) {
        prj020kengen__ = prj020kengen;
    }

    /**
     * <p>groupLabel を取得します。
     * @return groupLabel
     */
    public List<LabelValueBean> getGroupLabel() {
        return groupLabel__;
    }

    /**
     * <p>groupLabel をセットします。
     * @param groupLabel groupLabel
     */
    public void setGroupLabel(List<LabelValueBean> groupLabel) {
        groupLabel__ = groupLabel;
    }

    /**
     * <p>userLabel を取得します。
     * @return userLabel
     */
    public List<UsrLabelValueBean> getUserLabel() {
        return userLabel__;
    }

    /**
     * <p>userLabel をセットします。
     * @param userLabel userLabel
     */
    public void setUserLabel(List<UsrLabelValueBean> userLabel) {
        userLabel__ = userLabel;
    }

    /**
     * <p>syozokuMemberLabel を取得します。
     * @return syozokuMemberLabel
     */
    public List<UsrLabelValueBean> getSyozokuMemberLabel() {
        return syozokuMemberLabel__;
    }

    /**
     * <p>syozokuMemberLabel をセットします。
     * @param syozokuMemberLabel syozokuMemberLabel
     */
    public void setSyozokuMemberLabel(List<UsrLabelValueBean> syozokuMemberLabel) {
        syozokuMemberLabel__ = syozokuMemberLabel;
    }

    /**
     * <p>statusLabel を取得します。
     * @return statusLabel
     */
    public List<LabelValueBean> getStatusLabel() {
        return statusLabel__;
    }

    /**
     * <p>statusLabel をセットします。
     * @param statusLabel statusLabel
     */
    public void setStatusLabel(List<LabelValueBean> statusLabel) {
        statusLabel__ = statusLabel;
    }

    /**
     * <p>copyProjectSid を取得します。
     * @return copyProjectSid
     */
    public int getCopyProjectSid() {
        return copyProjectSid__;
    }

    /**
     * <p>copyProjectSid をセットします。
     * @param copyProjectSid copyProjectSid
     */
    public void setCopyProjectSid(int copyProjectSid) {
        copyProjectSid__ = copyProjectSid;
    }
    /**
     * <p>prj020prjMyKbn を取得します。
     * @return prj020prjMyKbn
     */
    public int getPrj020prjMyKbn() {
        return prj020prjMyKbn__;
    }
    /**
     * <p>prj020prjMyKbn をセットします。
     * @param prj020prjMyKbn prj020prjMyKbn
     */
    public void setPrj020prjMyKbn(int prj020prjMyKbn) {
        prj020prjMyKbn__ = prj020prjMyKbn;
    }
    /**
     * <p>prj020smailKbn を取得します。
     * @return prj020smailKbn
     */
    public int getPrj020smailKbn() {
        return prj020smailKbn__;
    }
    /**
     * <p>prj020smailKbn をセットします。
     * @param prj020smailKbn prj020smailKbn
     */
    public void setPrj020smailKbn(int prj020smailKbn) {
        prj020smailKbn__ = prj020smailKbn;
    }

    /**
     * @return prj150CompanyBaseSid
     */
    public String[] getPrj150CompanyBaseSid() {
        return prj150CompanyBaseSid__;
    }

    /**
     * @param prj150CompanyBaseSid 設定する prj150CompanyBaseSid
     */
    public void setPrj150CompanyBaseSid(String[] prj150CompanyBaseSid) {
        prj150CompanyBaseSid__ = prj150CompanyBaseSid;
    }

    /**
     * @return prj150CompanySid
     */
    public String[] getPrj150CompanySid() {
        return prj150CompanySid__;
    }

    /**
     * @param prj150CompanySid 設定する prj150CompanySid
     */
    public void setPrj150CompanySid(String[] prj150CompanySid) {
        prj150CompanySid__ = prj150CompanySid;
    }

    /**
     * @return prj020AddDataList
     */
    public List<Prj150DspMdl> getPrj020AddDataList() {
        return prj020AddDataList__;
    }

    /**
     * @param prj020AddDataList 設定する prj020AddDataList
     */
    public void setPrj020AddDataList(List<Prj150DspMdl> prj020AddDataList) {
        prj020AddDataList__ = prj020AddDataList;
    }

    /**
     * @return prj150AddressIdSv
     */
    public String[] getPrj150AddressIdSv() {
        return prj150AddressIdSv__;
    }

    /**
     * @param prj150AddressIdSv 設定する prj150AddressIdSv
     */
    public void setPrj150AddressIdSv(String[] prj150AddressIdSv) {
        prj150AddressIdSv__ = prj150AddressIdSv;
    }

    /**
     * @return prj020EditDspFlg
     */
    public int getPrj020EditDspFlg() {
        return prj020EditDspFlg__;
    }

    /**
     * @param prj020EditDspFlg 設定する prj020EditDspFlg
     */
    public void setPrj020EditDspFlg(int prj020EditDspFlg) {
        prj020EditDspFlg__ = prj020EditDspFlg;
    }

    /**
     * @return prj020UsrDel
     */
    public int getPrj020UsrDel() {
        return prj020UsrDel__;
    }

    /**
     * @param prj020UsrDel 設定する prj020UsrDel
     */
    public void setPrj020UsrDel(int prj020UsrDel) {
        prj020UsrDel__ = prj020UsrDel;
    }

    /**
     * @return prj020ScrollFlg
     */
    public String getPrj020ScrollFlg() {
        return prj020ScrollFlg__;
    }

    /**
     * @param prj020ScrollFlg 設定する prj020ScrollFlg
     */
    public void setPrj020ScrollFlg(String prj020ScrollFlg) {
        prj020ScrollFlg__ = prj020ScrollFlg;
    }

    /**
     * <p>prj020BinSid を取得します。
     * @return prj020BinSid
     */
    public Long getPrj020BinSid() {
        return prj020BinSid__;
    }

    /**
     * <p>prj020BinSid をセットします。
     * @param prj020BinSid prj020BinSid
     */
    public void setPrj020BinSid(Long prj020BinSid) {
        prj020BinSid__ = prj020BinSid;
    }

    /**
     * <p>prj020IcoName を取得します。
     * @return prj020IcoName
     */
    public String getPrj020IcoName() {
        return prj020IcoName__;
    }

    /**
     * <p>prj020IcoName をセットします。
     * @param prj020IcoName prj020IcoName
     */
    public void setPrj020IcoName(String prj020IcoName) {
        prj020IcoName__ = prj020IcoName;
    }

    /**
     * <p>prj020IcoSaveName を取得します。
     * @return prj020IcoSaveName
     */
    public String getPrj020IcoSaveName() {
        return prj020IcoSaveName__;
    }

    /**
     * <p>prj020IcoSaveName をセットします。
     * @param prj020IcoSaveName prj020IcoSaveName
     */
    public void setPrj020IcoSaveName(String prj020IcoSaveName) {
        prj020IcoSaveName__ = prj020IcoSaveName;
    }

    /**
     * <p>prj020pluginId を取得します。
     * @return prj020pluginId
     */
    public String getPrj020pluginId() {
        return prj020pluginId__;
    }

    /**
     * <p>prj020pluginId をセットします。
     * @param prj020pluginId prj020pluginId
     */
    public void setPrj020pluginId(String prj020pluginId) {
        prj020pluginId__ = prj020pluginId;
    }

    /**
     * <p>prj020IcoSetFlg を取得します。
     * @return prj020IcoSetFlg
     */
    public int getPrj020IcoSetFlg() {
        return prj020IcoSetFlg__;
    }

    /**
     * <p>prj020IcoSetFlg をセットします。
     * @param prj020IcoSetFlg prj020IcoSetFlg
     */
    public void setPrj020IcoSetFlg(int prj020IcoSetFlg) {
        prj020IcoSetFlg__ = prj020IcoSetFlg;
    }

    /**
     * <p>prj020initFlg を取得します。
     * @return prj020initFlg
     */
    public boolean isPrj020initFlg() {
        return prj020initFlg__;
    }

    /**
     * <p>prj020initFlg をセットします。
     * @param prj020initFlg prj020initFlg
     */
    public void setPrj020initFlg(boolean prj020initFlg) {
        prj020initFlg__ = prj020initFlg;
    }

    /**
     * <p>prj020hdnMemberSid を取得します。
     * @return prj020hdnMemberSid
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020hdnMemberSid__
     */
    public String[] getPrj020hdnMemberSid() {
        return prj020hdnMemberSid__;
    }

    /**
     * <p>prj020hdnMemberSid をセットします。
     * @param prj020hdnMemberSid prj020hdnMemberSid
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020hdnMemberSid__
     */
    public void setPrj020hdnMemberSid(String[] prj020hdnMemberSid) {
        prj020hdnMemberSid__ = prj020hdnMemberSid;
    }

    /**
     * <p>prj020hdnMemberUI を取得します。
     * @return prj020hdnMemberUI
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020hdnMemberUI__
     */
    public UserGroupSelector getPrj020hdnMemberUI() {
        return prj020hdnMemberUI__;
    }

    /**
     * <p>prj020hdnMemberUI をセットします。
     * @param prj020hdnMemberUI prj020hdnMemberUI
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020hdnMemberUI__
     */
    public void setPrj020hdnMemberUI(UserGroupSelector prj020hdnMemberUI) {
        prj020hdnMemberUI__ = prj020hdnMemberUI;
    }

    /**
     * <p>prj020adminSelectUI を取得します。
     * @return prj020adminSelectUI
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020adminSelectUI__
     */
    public Prj020AdminMemberSelector getPrj020adminSelectUI() {
        return prj020adminSelectUI__;
    }

    /**
     * <p>prj020adminSelectUI をセットします。
     * @param prj020adminSelectUI prj020adminSelectUI
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020adminSelectUI__
     */
    public void setPrj020adminSelectUI(
            Prj020AdminMemberSelector prj020adminSelectUI) {
        prj020adminSelectUI__ = prj020adminSelectUI;
    }

    /**
     * <p>prj020groupSidAdm を取得します。
     * @return prj020groupSidAdm
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020groupSidAdm__
     */
    public int getPrj020groupSidAdm() {
        return prj020groupSidAdm__;
    }

    /**
     * <p>prj020groupSidAdm をセットします。
     * @param prj020groupSidAdm prj020groupSidAdm
     * @see jp.groupsession.v2.prj.prj020.Prj020ParamModel#prj020groupSidAdm__
     */
    public void setPrj020groupSidAdm(int prj020groupSidAdm) {
        prj020groupSidAdm__ = prj020groupSidAdm;
    }

    /**
     * <p>prj020startDate を取得します。
     * @return prj020startDate
     * @see jp.groupsession.v2.prj.prj020.Prj020Form#prj020startDate__
     */
    public String getPrj020startDate() {
        return prj020startDate__;
    }

    /**
     * <p>prj020startDate をセットします。
     * @param prj020startDate prj020startDate
     * @see jp.groupsession.v2.prj.prj020.Prj020Form#prj020startDate__
     */
    public void setPrj020startDate(String prj020startDate) {
        prj020startDate__ = prj020startDate;
    }

    /**
     * <p>prj020endDate を取得します。
     * @return prj020endDate
     * @see jp.groupsession.v2.prj.prj020.Prj020Form#prj020endDate__
     */
    public String getPrj020endDate() {
        return prj020endDate__;
    }

    /**
     * <p>prj020endDate をセットします。
     * @param prj020endDate prj020endDate
     * @see jp.groupsession.v2.prj.prj020.Prj020Form#prj020endDate__
     */
    public void setPrj020endDate(String prj020endDate) {
        prj020endDate__ = prj020endDate;
    }
}