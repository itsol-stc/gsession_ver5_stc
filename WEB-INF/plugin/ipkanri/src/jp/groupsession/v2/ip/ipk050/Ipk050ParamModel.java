package jp.groupsession.v2.ip.ipk050;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.ipk040.Ipk040ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] IP管理 IPアドレス登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk050ParamModel extends Ipk040ParamModel {
    /** コメント(HTML用) */
    private String iadMsgHtml__;

    /** 使用者SID */
    private String[] adminSidList__;
    /** 使用者 選択グループ */
    private String groupSelect__ = "1";
    /** 使用者ユーザ一覧(選択済) */
    private ArrayList<UsrLabelValueBean> employerUserList__ = null;
    /** ネットワーク管理者 UI */
    private UserGroupSelector adminSidListUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("ipk.ipk020.5", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("ipk.ipk020.6", null))
                        .chainParameterName(
                                "adminSidList")
                    )
                .chainGroupSelectionParamName("groupSelect")
                .build();

    /** 添付ファイル IPアドレス公開*/
    private String[] ipk050KoukaiFiles__ = null;
    /** 添付ファイル IPアドレス非公開*/
    private String[] ipk050HikoukaiFiles__ = null;
    /** ファイルコンボ(公開) */
    private List < LabelValueBean > ipk050KoukaiFileLabelList__ = null;
    /** ファイルコンボ(非公開) */
    private List < LabelValueBean > ipk050HikoukaiFileLabelList__ = null;
    /** 資産管理番号 */
    private String iadPrtMngNum__;
    /** 管理権限フラグ（IPアドレス使用者以上の権限があれば、true） */
    private boolean ipk050AdminFlg__;
    /** 管理権限フラグ（ネットワーク管理者以上の権限があれば、true） */
    private boolean ipk050NetAdminFlg__;
    /** 添付ファイル存在フラグ */
    private boolean ipk050TempExist__;
    /** CPUコンボ */
    private List < LabelValueBean > ipk050cpuLabelList__ = null;
    /** メモリコンボ */
    private List < LabelValueBean > ipk050memoryLabelList__ = null;
    /** HDコンボ */
    private List < LabelValueBean > ipk050hdLabelList__ = null;
    /** 選択ているCPU */
    private String cpuSelect__ = "0";
    /** 選択ているメモリ */
    private String memorySelect__ = "0";
    /** 選択ているHD */
    private String hdSelect__ = "0";
    /** CPU名 */
    private String cpuName__;
    /** メモリ名 */
    private String memoryName__;
    /** HD名 */
    private String hdName__;
    /** ipk050ネットワークSID */
    private String ipk050NetSid__ = "0";
    /** ネットワーク名 */
    private String ipk070SvSltNet__ = null;
    /** ネットワークSID */
    private String ipk070SvNetSid__ = null;
    /** ユーザ */
    private String ipk070SvUsrSid__ = null;
    /** グループ */
    private String ipk070SvGrpSid__ = null;
    /** キーワード */
    private String ipk070SvKeyWord__ = null;
    /** キーワード検索区分 */
    private String ipk070SvKeyWordkbn__ = IpkConst.SEARCH_KEYWORD_AND;
    /** 検索対象 */
    private String[] ipk070SvSearchTarget__ = null;
    /** ソートキー1 */
    private String ipk070SvSearchSortKey1__ = "0";
    /** オーダーキー1 */
    private String ipk070SvSearchOrderKey1__ = "0";
    /** ソートキー2 */
    private String ipk070SvSearchSortKey2__ = "1";
    /** オーダーキー2 */
    private String ipk070SvSearchOrderKey2__ = "0";
    /** 現在ページ数 */
    private String ipk070PageNow__ = "1";
    /** ページコンボ最大ページ数 */
    private String ipk070MaxPageNum__;
    /** ネットワーク名 */
    private String ipk070SltNet__ = null;
    /** グループ */
    private String ipk070SltGroup__ = null;
    /** ユーザ */
    private String ipk070SltUser__ = null;
    /** キーワード */
    private String ipk070KeyWord__ = null;
    /** キーワード検索区分 */
    private String ipk070KeyWordkbn__ = IpkConst.SEARCH_KEYWORD_AND;
    /** 検索対象 */
    private String[] ipk070SearchTarget__ = null;

    /** ヘルプモード */
    private String ipk050helpMode__ = null;

    //ソート順
    /** ソートキー1 */
    private String ipk070SearchSortKey1__ = "4";
    /** オーダーキー1 */
    private String ipk070SearchOrderKey1__ = "0";
    /** ソートキー2 */
    private String ipk070SearchSortKey2__ = "0";
    /** オーダーキー2 */
    private String ipk070SearchOrderKey2__ = "0";

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ipk050ScrollFlg__ = "0";

    /** 表示区分 0=登録 1=編集 2=詳細 */
    private String ipk050DspKbn__ = "0";

    /**
     * <p>ipk070MaxPageNum を取得します。
     * @return ipk070MaxPageNum
     */
    public String getIpk070MaxPageNum() {
        return ipk070MaxPageNum__;
    }
    /**
     * <p>ipk070MaxPageNum をセットします。
     * @param ipk070MaxPageNum ipk070MaxPageNum
     */
    public void setIpk070MaxPageNum(String ipk070MaxPageNum) {
        ipk070MaxPageNum__ = ipk070MaxPageNum;
    }
    /**
     * <p>ipk070PageNow を取得します。
     * @return ipk070PageNow
     */
    public String getIpk070PageNow() {
        return ipk070PageNow__;
    }
    /**
     * <p>ipk070PageNow をセットします。
     * @param ipk070PageNow ipk070PageNow
     */
    public void setIpk070PageNow(String ipk070PageNow) {
        ipk070PageNow__ = ipk070PageNow;
    }
    /**
     * <p>ipk050TempExist を取得します。
     * @return ipk050TempExist
     */
    public boolean isIpk050TempExist() {
        return ipk050TempExist__;
    }
    /**
     * <p>ipk050TempExist をセットします。
     * @param ipk050TempExist ipk050TempExist
     */
    public void setIpk050TempExist(boolean ipk050TempExist) {
        ipk050TempExist__ = ipk050TempExist;
    }
    /**
     * <p>ipk050AdminFlg を取得します。
     * @return ipk050AdminFlg
     */
    public boolean isIpk050AdminFlg() {
        return ipk050AdminFlg__;
    }
    /**
     * <p>ipk050AdminFlg をセットします。
     * @param ipk050AdminFlg ipk050AdminFlg
     */
    public void setIpk050AdminFlg(boolean ipk050AdminFlg) {
        ipk050AdminFlg__ = ipk050AdminFlg;
    }
    /**
     * <p>iadPrtMngNum を取得します。
     * @return iadPrtMngNum
     */
    public String getIadPrtMngNum() {
        return iadPrtMngNum__;
    }
    /**
     * <p>iadPrtMngNum をセットします。
     * @param iadPrtMngNum iadPrtMngNum
     */
    public void setIadPrtMngNum(String iadPrtMngNum) {
        iadPrtMngNum__ = iadPrtMngNum;
    }
    /**
     * <p>ipk050HikoukaiFiles を取得します。
     * @return ipk050HikoukaiFiles
     */
    public String[] getIpk050HikoukaiFiles() {
        return ipk050HikoukaiFiles__;
    }
    /**
     * <p>ipk050HikoukaiFiles をセットします。
     * @param ipk050HikoukaiFiles ipk050HikoukaiFiles
     */
    public void setIpk050HikoukaiFiles(String[] ipk050HikoukaiFiles) {
        ipk050HikoukaiFiles__ = ipk050HikoukaiFiles;
    }
    /**
     * <p>ipk050KoukaiFiles を取得します。
     * @return ipk050KoukaiFiles
     */
    public String[] getIpk050KoukaiFiles() {
        return ipk050KoukaiFiles__;
    }
    /**
     * <p>ipk050KoukaiFiles をセットします。
     * @param ipk050KoukaiFiles ipk050KoukaiFiles
     */
    public void setIpk050KoukaiFiles(String[] ipk050KoukaiFiles) {
        ipk050KoukaiFiles__ = ipk050KoukaiFiles;
    }
    /**
     * <p>ipk050HikoukaiFileLabelList を取得します。
     * @return ipk050HikoukaiFileLabelList
     */
    public List<LabelValueBean> getIpk050HikoukaiFileLabelList() {
        return ipk050HikoukaiFileLabelList__;
    }
    /**
     * <p>ipk050HikoukaiFileLabelList をセットします。
     * @param ipk050HikoukaiFileLabelList ipk050HikoukaiFileLabelList
     */
    public void setIpk050HikoukaiFileLabelList(
            List<LabelValueBean> ipk050HikoukaiFileLabelList) {
        ipk050HikoukaiFileLabelList__ = ipk050HikoukaiFileLabelList;
    }
    /**
     * <p>ipk050KoukaiFileLabelList を取得します。
     * @return ipk050KoukaiFileLabelList
     */
    public List<LabelValueBean> getIpk050KoukaiFileLabelList() {
        return ipk050KoukaiFileLabelList__;
    }
    /**
     * <p>ipk050KoukaiFileLabelList をセットします。
     * @param ipk050KoukaiFileLabelList ipk050KoukaiFileLabelList
     */
    public void setIpk050KoukaiFileLabelList(
            List<LabelValueBean> ipk050KoukaiFileLabelList) {
        ipk050KoukaiFileLabelList__ = ipk050KoukaiFileLabelList;
    }
    /**
     * <p>adminSidList を取得します。
     * @return adminSidList
     */
    public String[] getAdminSidList() {
        return adminSidList__;
    }
    /**
     * <p>adminSidList をセットします。
     * @param adminSidList adminSidList
     */
    public void setAdminSidList(String[] adminSidList) {
        adminSidList__ = adminSidList;
    }
    /**
     * <p>groupSelect を取得します。
     * @return groupSelect
     */
    public String getGroupSelect() {
        return groupSelect__;
    }
    /**
     * <p>groupSelect をセットします。
     * @param groupSelect groupSelect
     */
    public void setGroupSelect(String groupSelect) {
        groupSelect__ = groupSelect;
    }
    /**
     * <p>employerUserList を取得します。
     * @return employerUserList
     */
    public ArrayList<UsrLabelValueBean> getEmployerUserList() {
        return employerUserList__;
    }
    /**
     * <p>employerUserList をセットします。
     * @param employerUserList employerUserList
     */
    public void setEmployerUserList(ArrayList<UsrLabelValueBean> employerUserList) {
        employerUserList__ = employerUserList;
    }
    /**
     * <p>adminSidListUI を取得します。
     * @return adminSidListUI
     */
    public UserGroupSelector getAdminSidListUI() {
        return adminSidListUI__;
    }
    /**
     * <p>adminSidListUI をセットします。
     * @param adminSidListUI adminSidListUI
     */
    public void setAdminSidListUI(UserGroupSelector adminSidListUI) {
        adminSidListUI__ = adminSidListUI;
    }
    /**
     * <p>ipk050NetAdminFlg を取得します。
     * @return ipk050NetAdminFlg
     */
    public boolean isIpk050NetAdminFlg() {
        return ipk050NetAdminFlg__;
    }
    /**
     * <p>ipk050NetAdminFlg をセットします。
     * @param ipk050NetAdminFlg ipk050NetAdminFlg
     */
    public void setIpk050NetAdminFlg(boolean ipk050NetAdminFlg) {
        ipk050NetAdminFlg__ = ipk050NetAdminFlg;
    }
    /**
     * <p>iadMsgHtml を取得します。
     * @return iadMsgHtml
     */
    public String getIadMsgHtml() {
        return iadMsgHtml__;
    }
    /**
     * <p>iadMsgHtml をセットします。
     * @param iadMsgHtml iadMsgHtml
     */
    public void setIadMsgHtml(String iadMsgHtml) {
        iadMsgHtml__ = iadMsgHtml;
    }
    /**
     * <p>cpuSelect を取得します。
     * @return cpuSelect
     */
    public String getCpuSelect() {
        return cpuSelect__;
    }
    /**
     * <p>cpuSelect をセットします。
     * @param cpuSelect cpuSelect
     */
    public void setCpuSelect(String cpuSelect) {
        cpuSelect__ = cpuSelect;
    }
    /**
     * <p>hdSelect を取得します。
     * @return hdSelect
     */
    public String getHdSelect() {
        return hdSelect__;
    }
    /**
     * <p>hdSelect をセットします。
     * @param hdSelect hdSelect
     */
    public void setHdSelect(String hdSelect) {
        hdSelect__ = hdSelect;
    }
    /**
     * <p>ipk050cpuLabelList を取得します。
     * @return ipk050cpuLabelList
     */
    public List<LabelValueBean> getIpk050cpuLabelList() {
        return ipk050cpuLabelList__;
    }
    /**
     * <p>ipk050cpuLabelList をセットします。
     * @param ipk050cpuLabelList ipk050cpuLabelList
     */
    public void setIpk050cpuLabelList(List<LabelValueBean> ipk050cpuLabelList) {
        ipk050cpuLabelList__ = ipk050cpuLabelList;
    }
    /**
     * <p>ipk050hdLabelList を取得します。
     * @return ipk050hdLabelList
     */
    public List<LabelValueBean> getIpk050hdLabelList() {
        return ipk050hdLabelList__;
    }
    /**
     * <p>ipk050hdLabelList をセットします。
     * @param ipk050hdLabelList ipk050hdLabelList
     */
    public void setIpk050hdLabelList(List<LabelValueBean> ipk050hdLabelList) {
        ipk050hdLabelList__ = ipk050hdLabelList;
    }
    /**
     * <p>ipk050memoryLabelList を取得します。
     * @return ipk050memoryLabelList
     */
    public List<LabelValueBean> getIpk050memoryLabelList() {
        return ipk050memoryLabelList__;
    }
    /**
     * <p>ipk050memoryLabelList をセットします。
     * @param ipk050memoryLabelList ipk050memoryLabelList
     */
    public void setIpk050memoryLabelList(List<LabelValueBean> ipk050memoryLabelList) {
        ipk050memoryLabelList__ = ipk050memoryLabelList;
    }
    /**
     * <p>memorySelect を取得します。
     * @return memorySelect
     */
    public String getMemorySelect() {
        return memorySelect__;
    }
    /**
     * <p>memorySelect をセットします。
     * @param memorySelect memorySelect
     */
    public void setMemorySelect(String memorySelect) {
        memorySelect__ = memorySelect;
    }
    /**
     * <p>cpuName を取得します。
     * @return cpuName
     */
    public String getCpuName() {
        return cpuName__;
    }
    /**
     * <p>cpuName をセットします。
     * @param cpuName cpuName
     */
    public void setCpuName(String cpuName) {
        cpuName__ = cpuName;
    }
    /**
     * <p>hdName を取得します。
     * @return hdName
     */
    public String getHdName() {
        return hdName__;
    }
    /**
     * <p>hdName をセットします。
     * @param hdName hdName
     */
    public void setHdName(String hdName) {
        hdName__ = hdName;
    }
    /**
     * <p>memoryName を取得します。
     * @return memoryName
     */
    public String getMemoryName() {
        return memoryName__;
    }
    /**
     * <p>memoryName をセットします。
     * @param memoryName memoryName
     */
    public void setMemoryName(String memoryName) {
        memoryName__ = memoryName;
    }
    /**
     * <p>ipk050NetSid を取得します。
     * @return ipk050NetSid
     */
    public String getIpk050NetSid() {
        return ipk050NetSid__;
    }
    /**
     * <p>ipk050NetSid をセットします。
     * @param ipk050NetSid ipk050NetSid
     */
    public void setIpk050NetSid(String ipk050NetSid) {
        ipk050NetSid__ = ipk050NetSid;
    }
    /**
     * <p>ipk070SvKeyWord を取得します。
     * @return ipk070SvKeyWord
     */
    public String getIpk070SvKeyWord() {
        return ipk070SvKeyWord__;
    }
    /**
     * <p>ipk070SvKeyWord をセットします。
     * @param ipk070SvKeyWord ipk070SvKeyWord
     */
    public void setIpk070SvKeyWord(String ipk070SvKeyWord) {
        ipk070SvKeyWord__ = ipk070SvKeyWord;
    }
    /**
     * <p>ipk070SvKeyWordkbn を取得します。
     * @return ipk070SvKeyWordkbn
     */
    public String getIpk070SvKeyWordkbn() {
        return ipk070SvKeyWordkbn__;
    }
    /**
     * <p>ipk070SvKeyWordkbn をセットします。
     * @param ipk070SvKeyWordkbn ipk070SvKeyWordkbn
     */
    public void setIpk070SvKeyWordkbn(String ipk070SvKeyWordkbn) {
        ipk070SvKeyWordkbn__ = ipk070SvKeyWordkbn;
    }
    /**
     * <p>ipk070SvNetSid を取得します。
     * @return ipk070SvNetSid
     */
    public String getIpk070SvNetSid() {
        return ipk070SvNetSid__;
    }
    /**
     * <p>ipk070SvNetSid をセットします。
     * @param ipk070SvNetSid ipk070SvNetSid
     */
    public void setIpk070SvNetSid(String ipk070SvNetSid) {
        ipk070SvNetSid__ = ipk070SvNetSid;
    }
    /**
     * <p>ipk070SvSearchOrderKey1 を取得します。
     * @return ipk070SvSearchOrderKey1
     */
    public String getIpk070SvSearchOrderKey1() {
        return ipk070SvSearchOrderKey1__;
    }
    /**
     * <p>ipk070SvSearchOrderKey1 をセットします。
     * @param ipk070SvSearchOrderKey1 ipk070SvSearchOrderKey1
     */
    public void setIpk070SvSearchOrderKey1(String ipk070SvSearchOrderKey1) {
        ipk070SvSearchOrderKey1__ = ipk070SvSearchOrderKey1;
    }
    /**
     * <p>ipk070SvSearchOrderKey2 を取得します。
     * @return ipk070SvSearchOrderKey2
     */
    public String getIpk070SvSearchOrderKey2() {
        return ipk070SvSearchOrderKey2__;
    }
    /**
     * <p>ipk070SvSearchOrderKey2 をセットします。
     * @param ipk070SvSearchOrderKey2 ipk070SvSearchOrderKey2
     */
    public void setIpk070SvSearchOrderKey2(String ipk070SvSearchOrderKey2) {
        ipk070SvSearchOrderKey2__ = ipk070SvSearchOrderKey2;
    }
    /**
     * <p>ipk070SvSearchSortKey1 を取得します。
     * @return ipk070SvSearchSortKey1
     */
    public String getIpk070SvSearchSortKey1() {
        return ipk070SvSearchSortKey1__;
    }
    /**
     * <p>ipk070SvSearchSortKey1 をセットします。
     * @param ipk070SvSearchSortKey1 ipk070SvSearchSortKey1
     */
    public void setIpk070SvSearchSortKey1(String ipk070SvSearchSortKey1) {
        ipk070SvSearchSortKey1__ = ipk070SvSearchSortKey1;
    }
    /**
     * <p>ipk070SvSearchSortKey2 を取得します。
     * @return ipk070SvSearchSortKey2
     */
    public String getIpk070SvSearchSortKey2() {
        return ipk070SvSearchSortKey2__;
    }
    /**
     * <p>ipk070SvSearchSortKey2 をセットします。
     * @param ipk070SvSearchSortKey2 ipk070SvSearchSortKey2
     */
    public void setIpk070SvSearchSortKey2(String ipk070SvSearchSortKey2) {
        ipk070SvSearchSortKey2__ = ipk070SvSearchSortKey2;
    }
    /**
     * <p>ipk070SvSearchTarget を取得します。
     * @return ipk070SvSearchTarget
     */
    public String[] getIpk070SvSearchTarget() {
        return ipk070SvSearchTarget__;
    }
    /**
     * <p>ipk070SvSearchTarget をセットします。
     * @param ipk070SvSearchTarget ipk070SvSearchTarget
     */
    public void setIpk070SvSearchTarget(String[] ipk070SvSearchTarget) {
        ipk070SvSearchTarget__ = ipk070SvSearchTarget;
    }
    /**
     * <p>ipk070SvSltNet を取得します。
     * @return ipk070SvSltNet
     */
    public String getIpk070SvSltNet() {
        return ipk070SvSltNet__;
    }
    /**
     * <p>ipk070SvSltNet をセットします。
     * @param ipk070SvSltNet ipk070SvSltNet
     */
    public void setIpk070SvSltNet(String ipk070SvSltNet) {
        ipk070SvSltNet__ = ipk070SvSltNet;
    }
    /**
     * <p>ipk070SvUsrSid を取得します。
     * @return ipk070SvUsrSid
     */
    public String getIpk070SvUsrSid() {
        return ipk070SvUsrSid__;
    }
    /**
     * <p>ipk070SvUsrSid をセットします。
     * @param ipk070SvUsrSid ipk070SvUsrSid
     */
    public void setIpk070SvUsrSid(String ipk070SvUsrSid) {
        ipk070SvUsrSid__ = ipk070SvUsrSid;
    }
    /**
     * <p>ipk070KeyWord を取得します。
     * @return ipk070KeyWord
     */
    public String getIpk070KeyWord() {
        return ipk070KeyWord__;
    }
    /**
     * <p>ipk070KeyWord をセットします。
     * @param ipk070KeyWord ipk070KeyWord
     */
    public void setIpk070KeyWord(String ipk070KeyWord) {
        ipk070KeyWord__ = ipk070KeyWord;
    }
    /**
     * <p>ipk070KeyWordkbn を取得します。
     * @return ipk070KeyWordkbn
     */
    public String getIpk070KeyWordkbn() {
        return ipk070KeyWordkbn__;
    }
    /**
     * <p>ipk070KeyWordkbn をセットします。
     * @param ipk070KeyWordkbn ipk070KeyWordkbn
     */
    public void setIpk070KeyWordkbn(String ipk070KeyWordkbn) {
        ipk070KeyWordkbn__ = ipk070KeyWordkbn;
    }
    /**
     * <p>ipk070SearchTarget を取得します。
     * @return ipk070SearchTarget
     */
    public String[] getIpk070SearchTarget() {
        return ipk070SearchTarget__;
    }
    /**
     * <p>ipk070SearchTarget をセットします。
     * @param ipk070SearchTarget ipk070SearchTarget
     */
    public void setIpk070SearchTarget(String[] ipk070SearchTarget) {
        ipk070SearchTarget__ = ipk070SearchTarget;
    }
    /**
     * <p>ipk070SltGroup を取得します。
     * @return ipk070SltGroup
     */
    public String getIpk070SltGroup() {
        return ipk070SltGroup__;
    }
    /**
     * <p>ipk070SltGroup をセットします。
     * @param ipk070SltGroup ipk070SltGroup
     */
    public void setIpk070SltGroup(String ipk070SltGroup) {
        ipk070SltGroup__ = ipk070SltGroup;
    }
    /**
     * <p>ipk070SltNet を取得します。
     * @return ipk070SltNet
     */
    public String getIpk070SltNet() {
        return ipk070SltNet__;
    }
    /**
     * <p>ipk070SltNet をセットします。
     * @param ipk070SltNet ipk070SltNet
     */
    public void setIpk070SltNet(String ipk070SltNet) {
        ipk070SltNet__ = ipk070SltNet;
    }
    /**
     * <p>ipk070SltUser を取得します。
     * @return ipk070SltUser
     */
    public String getIpk070SltUser() {
        return ipk070SltUser__;
    }
    /**
     * <p>ipk070SltUser をセットします。
     * @param ipk070SltUser ipk070SltUser
     */
    public void setIpk070SltUser(String ipk070SltUser) {
        ipk070SltUser__ = ipk070SltUser;
    }
    /**
     * <p>ipk070SearchOrderKey1 を取得します。
     * @return ipk070SearchOrderKey1
     */
    public String getIpk070SearchOrderKey1() {
        return ipk070SearchOrderKey1__;
    }
    /**
     * <p>ipk070SearchOrderKey1 をセットします。
     * @param ipk070SearchOrderKey1 ipk070SearchOrderKey1
     */
    public void setIpk070SearchOrderKey1(String ipk070SearchOrderKey1) {
        ipk070SearchOrderKey1__ = ipk070SearchOrderKey1;
    }
    /**
     * <p>ipk070SearchOrderKey2 を取得します。
     * @return ipk070SearchOrderKey2
     */
    public String getIpk070SearchOrderKey2() {
        return ipk070SearchOrderKey2__;
    }
    /**
     * <p>ipk070SearchOrderKey2 をセットします。
     * @param ipk070SearchOrderKey2 ipk070SearchOrderKey2
     */
    public void setIpk070SearchOrderKey2(String ipk070SearchOrderKey2) {
        ipk070SearchOrderKey2__ = ipk070SearchOrderKey2;
    }
    /**
     * <p>ipk070SearchSortKey1 を取得します。
     * @return ipk070SearchSortKey1
     */
    public String getIpk070SearchSortKey1() {
        return ipk070SearchSortKey1__;
    }
    /**
     * <p>ipk070SearchSortKey1 をセットします。
     * @param ipk070SearchSortKey1 ipk070SearchSortKey1
     */
    public void setIpk070SearchSortKey1(String ipk070SearchSortKey1) {
        ipk070SearchSortKey1__ = ipk070SearchSortKey1;
    }
    /**
     * <p>ipk070SearchSortKey2 を取得します。
     * @return ipk070SearchSortKey2
     */
    public String getIpk070SearchSortKey2() {
        return ipk070SearchSortKey2__;
    }
    /**
     * <p>ipk070SearchSortKey2 をセットします。
     * @param ipk070SearchSortKey2 ipk070SearchSortKey2
     */
    public void setIpk070SearchSortKey2(String ipk070SearchSortKey2) {
        ipk070SearchSortKey2__ = ipk070SearchSortKey2;
    }
    /**
     * <p>ipk070SvGrpSid を取得します。
     * @return ipk070SvGrpSid
     */
    public String getIpk070SvGrpSid() {
        return ipk070SvGrpSid__;
    }
    /**
     * <p>ipk070SvGrpSid をセットします。
     * @param ipk070SvGrpSid ipk070SvGrpSid
     */
    public void setIpk070SvGrpSid(String ipk070SvGrpSid) {
        ipk070SvGrpSid__ = ipk070SvGrpSid;
    }
    /**
     * <p>ipk050helpMode を取得します。
     * @return ipk050helpMode
     */
    public String getIpk050helpMode() {
        return ipk050helpMode__;
    }
    /**
     * <p>ipk050helpMode をセットします。
     * @param ipk050helpMode ipk050helpMode
     */
    public void setIpk050helpMode(String ipk050helpMode) {
        ipk050helpMode__ = ipk050helpMode;
    }
    /**
     * @return ipk050ScrollFlg
     */
    public String getIpk050ScrollFlg() {
        return ipk050ScrollFlg__;
    }
    /**
     * @param ipk050ScrollFlg 設定する ipk050ScrollFlg
     */
    public void setIpk050ScrollFlg(String ipk050ScrollFlg) {
        ipk050ScrollFlg__ = ipk050ScrollFlg;
    }
    /**
     * @return ipk050DspKbn
     */
    public String getIpk050DspKbn() {
        return ipk050DspKbn__;
    }
    /**
     * @param ipk050DspKbn セットする ipk050DspKbn
     */
    public void setIpk050DspKbn(String ipk050DspKbn) {
        ipk050DspKbn__ = ipk050DspKbn;
    }
}