package jp.groupsession.v2.ip.ipk070;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.ipk060.Ipk060ParamModel;
import jp.groupsession.v2.ip.model.IpkSearchModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] IP管理 詳細検索画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk070ParamModel extends Ipk060ParamModel {
    //ページコンボ
    /** 現在ページ数 */
    private String ipk070PageNow__ = "1";
    /** ページコンボ上 */
    private String ipk070Page1__ = "0";
    /** ページコンボ下 */
    private String ipk070Page2__ = "0";
    /** ページコンボ最大ページ数 */
    private String ipk070MaxPageNum__;

    /** ネットワーク名 */
    private String ipk070SltNet__ = null;
    /** グループ */
    private String ipk070SltGroup__ = null;
    /** ユーザ */
    private String ipk070SltUser__ = null;
    /** 検索結果表示用リスト */
    private ArrayList<IpkSearchModel> searchModelList__ = null;
    /** キーワード */
    private String ipk070KeyWord__ = null;
    /** キーワード検索区分 */
    private String ipk070KeyWordkbn__ = IpkConst.SEARCH_KEYWORD_AND;
    /** 検索対象 */
    private String[] ipk070SearchTarget__ = null;

    //ソート順
    /** ソートキー1 */
    private String ipk070SearchSortKey1__ = "4";
    /** オーダーキー1 */
    private String ipk070SearchOrderKey1__ = "0";
    /** ソートキー2 */
    private String ipk070SearchSortKey2__ = "0";
    /** オーダーキー2 */
    private String ipk070SearchOrderKey2__ = "0";

    //ラベルリスト
    /** グループラベルリスト */
    private List<LabelValueBean> ipk070GroupLabel__ = null;
    /** ユーザラベルリスト */
    private List<UsrLabelValueBean> ipk070UserLabel__ = null;
    /** ソートキーラベルリスト */
    private List<LabelValueBean> ipk070SortKeyLabelList__ = null;
    /** ページラベル */
    private ArrayList<LabelValueBean> ipk070PageLabel__;
    /** ネットワーク名ラベルリスト */
    private List<LabelValueBean> ipk070NetNameLabel__ = null;

    //svパラメータ
    /** ネットワーク名 */
    private String ipk070SvSltNet__ = null;
    /** ネットワークSID */
    private String ipk070SvNetSid__ = null;
    /** グループSID */
    private String ipk070SvGrpSid__ = null;
    /** 管理ユーザ */
    private String ipk070SvUsrSid__ = null;
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
    /** ipk050ネットワークSID */
    private String ipk050NetSid__ = "0";

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
     * <p>ipk070GroupLabel を取得します。
     * @return ipk070GroupLabel
     */
    public List<LabelValueBean> getIpk070GroupLabel() {
        return ipk070GroupLabel__;
    }

    /**
     * <p>ipk070GroupLabel をセットします。
     * @param ipk070GroupLabel ipk070GroupLabel
     */
    public void setIpk070GroupLabel(List<LabelValueBean> ipk070GroupLabel) {
        ipk070GroupLabel__ = ipk070GroupLabel;
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
     * <p>ipk070PageLabel を取得します。
     * @return ipk070PageLabel
     */
    public ArrayList<LabelValueBean> getIpk070PageLabel() {
        return ipk070PageLabel__;
    }

    /**
     * <p>ipk070PageLabel をセットします。
     * @param ipk070PageLabel ipk070PageLabel
     */
    public void setIpk070PageLabel(ArrayList<LabelValueBean> ipk070PageLabel) {
        ipk070PageLabel__ = ipk070PageLabel;
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
     * <p>ipk070UserLabel を取得します。
     * @return ipk070UserLabel
     */
    public List<UsrLabelValueBean> getIpk070UserLabel() {
        return ipk070UserLabel__;
    }

    /**
     * <p>ipk070UserLabel をセットします。
     * @param ipk070UserLabel ipk070UserLabel
     */
    public void setIpk070UserLabel(List<UsrLabelValueBean> ipk070UserLabel) {
        ipk070UserLabel__ = ipk070UserLabel;
    }

    /**
     * <p>ipk070SortKeyLabelList を取得します。
     * @return ipk070SortKeyLabelList
     */
    public List<LabelValueBean> getIpk070SortKeyLabelList() {
        return ipk070SortKeyLabelList__;
    }

    /**
     * <p>ipk070SortKeyLabelList をセットします。
     * @param ipk070SortKeyLabelList ipk070SortKeyLabelList
     */
    public void setIpk070SortKeyLabelList(
            List<LabelValueBean> ipk070SortKeyLabelList) {
        ipk070SortKeyLabelList__ = ipk070SortKeyLabelList;
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
     * <p>searchModelList を取得します。
     * @return searchModelList
     */
    public ArrayList<IpkSearchModel> getSearchModelList() {
        return searchModelList__;
    }

    /**
     * <p>searchModelList をセットします。
     * @param searchModelList searchModelList
     */
    public void setSearchModelList(ArrayList<IpkSearchModel> searchModelList) {
        searchModelList__ = searchModelList;
    }

    /**
     * <p>ipk070NetNameLabel を取得します。
     * @return ipk070NetNameLabel
     */
    public List<LabelValueBean> getIpk070NetNameLabel() {
        return ipk070NetNameLabel__;
    }

    /**
     * <p>ipk070NetNameLabel をセットします。
     * @param ipk070NetNameLabel ipk070NetNameLabel
     */
    public void setIpk070NetNameLabel(List<LabelValueBean> ipk070NetNameLabel) {
        ipk070NetNameLabel__ = ipk070NetNameLabel;
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
     * <p>ipk070Page1 を取得します。
     * @return ipk070Page1
     */
    public String getIpk070Page1() {
        return ipk070Page1__;
    }


    /**
     * <p>ipk070Page1 をセットします。
     * @param ipk070Page1 ipk070Page1
     */
    public void setIpk070Page1(String ipk070Page1) {
        ipk070Page1__ = ipk070Page1;
    }

    /**
     * <p>ipk070Page2 を取得します。
     * @return ipk070Page2
     */
    public String getIpk070Page2() {
        return ipk070Page2__;
    }

    /**
     * <p>ipk070Page2 をセットします。
     * @param ipk070Page2 ipk070Page2
     */
    public void setIpk070Page2(String ipk070Page2) {
        ipk070Page2__ = ipk070Page2;
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
}