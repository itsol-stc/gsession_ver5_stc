package jp.groupsession.v2.ip.ipk020;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.ipk010.Ipk010ParamModel;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] IP管理 ネットワーク登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk020ParamModel extends Ipk010ParamModel {
    /** ネットワーク名 */
    private String netName__;
    /** ネットワークアドレス1 */
    private String netNetad1__;
    /** ネットワークアドレス2 */
    private String netNetad2__;
    /** ネットワークアドレス3 */
    private String netNetad3__;
    /** ネットワークアドレス4 */
    private String netNetad4__;
    /** サブネットマスク1 */
    private String netSabnet1__;
    /** サブネットマスク2 */
    private String netSabnet2__;
    /** サブネットマスク3 */
    private String netSabnet3__;
    /** サブネットマスク4 */
    private String netSabnet4__;
    /** ネットワーク 公開/非公開 */
    private String netDsp__ = IpkConst.IPK_NET_DSP;
    /** コメント */
    private String netMsg__;
    /** コメント(HTML用) */
    private String netMsgHtml__;
    /** 表示順 */
    private String netSort__;

    /** ネットワーク管理者SID */
    private String[] adminSidList__ = null;
    /** ネットワーク管理者 グループ */
    private String groupSelect__ = "1";
    /** ネットワーク管理者ユーザ一覧(選択済) */
    private ArrayList<UsrLabelValueBean> adminUserList__ = null;
    /** ネットワーク管理者 UI */
    private UserGroupSelector adminSidListUI__ = null;

    /** ネットワークSID */
    private String netSid__;
    /** 添付ファイル 公開*/
    private String[] ipk020KoukaiFiles__ = null;
    /** 添付ファイル 非公開 */
    private String[] ipk020HikoukaiFiles__ = null;
    /** ファイルコンボ */
    private List < LabelValueBean > ipk020KoukaiFileLabelList__ = null;
    /** ファイルコンボ非公開 */
    private List < LabelValueBean > ipk020HikoukaiFileLabelList__ = null;
    /** ネットワークの添付ファイル情報 公開ならば1、非公開ならば0 */
    private String ipk020TempDsp__ = "0";
    /** 管理者権限 GS、全ネットワーク、ネットワークの管理者ならばtrue、それ以外はfalse */
    private boolean ipk020AdminFlg__ = false;
    /** ネットワークの添付ファイル情報リスト 公開*/
    private ArrayList<IpkBinModel> koukaiBinFileInfList__ = null;
    /** ネットワークの添付ファイル情報リスト 非公開*/
    private ArrayList<IpkBinModel> hikoukaiBinFileInfList__ = null;
    /** バイナリSID */
    private String binSid__;
    /** ヘルプモード */
    private String ipk020helpMode__ = IpkConst.IPK_HELP_TOUROKU;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ipk020ScrollFlg__ = "0";

    /**
     * <p>ipk020helpMode を取得します。
     * @return ipk020helpMode
     */
    public String getIpk020helpMode() {
        return ipk020helpMode__;
    }

    /**
     * <p>ipk020helpMode をセットします。
     * @param ipk020helpMode ipk020helpMode
     */
    public void setIpk020helpMode(String ipk020helpMode) {
        ipk020helpMode__ = ipk020helpMode;
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public String getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(String binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>ipk020AdminFlg を取得します。
     * @return ipk020AdminFlg
     */
    public boolean isIpk020AdminFlg() {
        return ipk020AdminFlg__;
    }

    /**
     * <p>ipk020AdminFlg をセットします。
     * @param ipk020AdminFlg ipk020AdminFlg
     */
    public void setIpk020AdminFlg(boolean ipk020AdminFlg) {
        ipk020AdminFlg__ = ipk020AdminFlg;
    }

    /**
     * <p>ipk020TempDsp を取得します。
     * @return ipk020TempDsp
     */
    public String getIpk020TempDsp() {
        return ipk020TempDsp__;
    }

    /**
     * <p>ipk020TempDsp をセットします。
     * @param ipk020TempDsp ipk020TempDsp
     */
    public void setIpk020TempDsp(String ipk020TempDsp) {
        ipk020TempDsp__ = ipk020TempDsp;
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
     * <p>adminUserList を取得します。
     * @return adminUserList
     */
    public ArrayList<UsrLabelValueBean> getAdminUserList() {
        return adminUserList__;
    }
    /**
     * <p>adminUserList をセットします。
     * @param adminUserList adminUserList
     */
    public void setAdminUserList(ArrayList<UsrLabelValueBean> adminUserList) {
        adminUserList__ = adminUserList;
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
     * <p>netMsg を取得します。
     * @return netMsg
     */
    public String getNetMsg() {
        return netMsg__;
    }
    /**
     * <p>netMsg をセットします。
     * @param netMsg netMsg
     */
    public void setNetMsg(String netMsg) {
        netMsg__ = netMsg;
    }
    /**
     * <p>netName を取得します。
     * @return netName
     */
    public String getNetName() {
        return netName__;
    }
    /**
     * <p>netName をセットします。
     * @param netName netName
     */
    public void setNetName(String netName) {
        netName__ = netName;
    }
    /**
     * <p>netNetad1 を取得します。
     * @return netNetad1
     */
    public String getNetNetad1() {
        return netNetad1__;
    }
    /**
     * <p>netNetad1 をセットします。
     * @param netNetad1 netNetad1
     */
    public void setNetNetad1(String netNetad1) {
        netNetad1__ = netNetad1;
    }
    /**
     * <p>netNetad2 を取得します。
     * @return netNetad2
     */
    public String getNetNetad2() {
        return netNetad2__;
    }
    /**
     * <p>netNetad2 をセットします。
     * @param netNetad2 netNetad2
     */
    public void setNetNetad2(String netNetad2) {
        netNetad2__ = netNetad2;
    }
    /**
     * <p>netNetad3 を取得します。
     * @return netNetad3
     */
    public String getNetNetad3() {
        return netNetad3__;
    }
    /**
     * <p>netNetad3 をセットします。
     * @param netNetad3 netNetad3
     */
    public void setNetNetad3(String netNetad3) {
        netNetad3__ = netNetad3;
    }
    /**
     * <p>netNetad4 を取得します。
     * @return netNetad4
     */
    public String getNetNetad4() {
        return netNetad4__;
    }
    /**
     * <p>netNetad4 をセットします。
     * @param netNetad4 netNetad4
     */
    public void setNetNetad4(String netNetad4) {
        netNetad4__ = netNetad4;
    }
    /**
     * <p>netSabnet1 を取得します。
     * @return netSabnet1
     */
    public String getNetSabnet1() {
        return netSabnet1__;
    }
    /**
     * <p>netSabnet1 をセットします。
     * @param netSabnet1 netSabnet1
     */
    public void setNetSabnet1(String netSabnet1) {
        netSabnet1__ = netSabnet1;
    }
    /**
     * <p>netSabnet2 を取得します。
     * @return netSabnet2
     */
    public String getNetSabnet2() {
        return netSabnet2__;
    }
    /**
     * <p>netSabnet2 をセットします。
     * @param netSabnet2 netSabnet2
     */
    public void setNetSabnet2(String netSabnet2) {
        netSabnet2__ = netSabnet2;
    }
    /**
     * <p>netSabnet3 を取得します。
     * @return netSabnet3
     */
    public String getNetSabnet3() {
        return netSabnet3__;
    }
    /**
     * <p>netSabnet3 をセットします。
     * @param netSabnet3 netSabnet3
     */
    public void setNetSabnet3(String netSabnet3) {
        netSabnet3__ = netSabnet3;
    }
    /**
     * <p>netSabnet4 を取得します。
     * @return netSabnet4
     */
    public String getNetSabnet4() {
        return netSabnet4__;
    }
    /**
     * <p>netSabnet4 をセットします。
     * @param netSabnet4 netSabnet4
     */
    public void setNetSabnet4(String netSabnet4) {
        netSabnet4__ = netSabnet4;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public String getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(String netSid) {
        netSid__ = netSid;
    }
    /**
     * <p>netSort を取得します。
     * @return netSort
     */
    public String getNetSort() {
        return netSort__;
    }
    /**
     * <p>netSort をセットします。
     * @param netSort netSort
     */
    public void setNetSort(String netSort) {
        netSort__ = netSort;
    }
    /**
     * <p>ipk020HikoukaiFiles を取得します。
     * @return ipk020HikoukaiFiles
     */
    public String[] getIpk020HikoukaiFiles() {
        return ipk020HikoukaiFiles__;
    }

    /**
     * <p>ipk020HikoukaiFiles をセットします。
     * @param ipk020HikoukaiFiles ipk020HikoukaiFiles
     */
    public void setIpk020HikoukaiFiles(String[] ipk020HikoukaiFiles) {
        ipk020HikoukaiFiles__ = ipk020HikoukaiFiles;
    }

    /**
     * <p>ipk020KoukaiFiles を取得します。
     * @return ipk020KoukaiFiles
     */
    public String[] getIpk020KoukaiFiles() {
        return ipk020KoukaiFiles__;
    }

    /**
     * <p>ipk020KoukaiFiles をセットします。
     * @param ipk020KoukaiFiles ipk020KoukaiFiles
     */
    public void setIpk020KoukaiFiles(String[] ipk020KoukaiFiles) {
        ipk020KoukaiFiles__ = ipk020KoukaiFiles;
    }

    /**
     * <p>ipk020HikoukaiFileLabelList を取得します。
     * @return ipk020HikoukaiFileLabelList
     */
    public List<LabelValueBean> getIpk020HikoukaiFileLabelList() {
        return ipk020HikoukaiFileLabelList__;
    }

    /**
     * <p>ipk020HikoukaiFileLabelList をセットします。
     * @param ipk020HikoukaiFileLabelList ipk020HikoukaiFileLabelList
     */
    public void setIpk020HikoukaiFileLabelList(
            List<LabelValueBean> ipk020HikoukaiFileLabelList) {
        ipk020HikoukaiFileLabelList__ = ipk020HikoukaiFileLabelList;
    }

    /**
     * <p>ipk020KoukaiFileLabelList を取得します。
     * @return ipk020KoukaiFileLabelList
     */
    public List<LabelValueBean> getIpk020KoukaiFileLabelList() {
        return ipk020KoukaiFileLabelList__;
    }

    /**
     * <p>ipk020KoukaiFileLabelList をセットします。
     * @param ipk020KoukaiFileLabelList ipk020KoukaiFileLabelList
     */
    public void setIpk020KoukaiFileLabelList(
            List<LabelValueBean> ipk020KoukaiFileLabelList) {
        ipk020KoukaiFileLabelList__ = ipk020KoukaiFileLabelList;
    }
    /**
     * <p>hikoukaiBinFileInfList を取得します。
     * @return hikoukaiBinFileInfList
     */
    public ArrayList<IpkBinModel> getHikoukaiBinFileInfList() {
        return hikoukaiBinFileInfList__;
    }

    /**
     * <p>hikoukaiBinFileInfList をセットします。
     * @param hikoukaiBinFileInfList hikoukaiBinFileInfList
     */
    public void setHikoukaiBinFileInfList(
            ArrayList<IpkBinModel> hikoukaiBinFileInfList) {
        hikoukaiBinFileInfList__ = hikoukaiBinFileInfList;
    }

    /**
     * <p>koukaiBinFileInfList を取得します。
     * @return koukaiBinFileInfList
     */
    public ArrayList<IpkBinModel> getKoukaiBinFileInfList() {
        return koukaiBinFileInfList__;
    }

    /**
     * <p>koukaiBinFileInfList をセットします。
     * @param koukaiBinFileInfList koukaiBinFileInfList
     */
    public void setKoukaiBinFileInfList(ArrayList<IpkBinModel> koukaiBinFileInfList) {
        koukaiBinFileInfList__ = koukaiBinFileInfList;
    }

    /**
     * <p>netMsgHtml を取得します。
     * @return netMsgHtml
     */
    public String getNetMsgHtml() {
        return netMsgHtml__;
    }

    /**
     * <p>netMsgHtml をセットします。
     * @param netMsgHtml netMsgHtml
     */
    public void setNetMsgHtml(String netMsgHtml) {
        netMsgHtml__ = netMsgHtml;
    }

    /**
     * <p>netDsp を取得します。
     * @return netDsp
     */
    public String getNetDsp() {
        return netDsp__;
    }

    /**
     * <p>netDsp をセットします。
     * @param netDsp netDsp
     */
    public void setNetDsp(String netDsp) {
        netDsp__ = netDsp;
    }

    /**
     * @return ipk020ScrollFlg
     */
    public String getIpk020ScrollFlg() {
        return ipk020ScrollFlg__;
    }

    /**
     * @param ipk020ScrollFlg 設定する ipk020ScrollFlg
     */
    public void setIpk020ScrollFlg(String ipk020ScrollFlg) {
        ipk020ScrollFlg__ = ipk020ScrollFlg;
    }
}