package jp.groupsession.v2.ip.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] IP管理 入力チェックモデル
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class ValidateCheckModel extends AbstractModel {

    /** 変更前のIPアドレス */
    private String beforeIpad__;
    /**  IPアドレス */
    private String ipAddress__;
    /** ネットワークアドレス */
    private String networkAddress__;
    /** サブネットマスク */
    private String subnetMask__;
    /**  登録されているIPアドレスリスト */
    private ArrayList<String> ipAddressList__;
    /** 登録されているネットワークアドレスリスト */
    private ArrayList<String> netadList__;
    /** 変更前のネットワークアドレス */
    private String beforeNetad__;
    /** 入力されたIPアドレスと同じIPアドレスの個数 */
    private int ipadCount__;
    /** ページコマンド */
    private String editCmd__;
    /** インポート処理モード */
    private String mode__;
    /** CSVファイル内のIPアドレスリスト */
    private ArrayList<String> ipadListCsv__;
    /** ネットワークSID */
    private int netSid__;
    /**
     * <p>ipadListCsv を取得します。
     * @return ipadListCsv
     */
    public ArrayList<String> getIpadListCsv() {
        return ipadListCsv__;
    }
    /**
     * <p>ipadListCsv をセットします。
     * @param ipadListCsv ipadListCsv
     */
    public void setIpadListCsv(ArrayList<String> ipadListCsv) {
        ipadListCsv__ = ipadListCsv;
    }
    /**
     * <p>mode を取得します。
     * @return mode
     */
    public String getMode() {
        return mode__;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(String mode) {
        mode__ = mode;
    }
    /**
     * <p>ipadCount を取得します。
     * @return ipadCount
     */
    public int getIpadCount() {
        return ipadCount__;
    }
    /**
     * <p>ipadCount をセットします。
     * @param ipadCount ipadCount
     */
    public void setIpadCount(int ipadCount) {
        ipadCount__ = ipadCount;
    }
    /**
     * <p>beforeNetad を取得します。
     * @return beforeNetad
     */
    public String getBeforeNetad() {
        return beforeNetad__;
    }
    /**
     * <p>beforeNetad をセットします。
     * @param beforeNetad beforeNetad
     */
    public void setBeforeNetad(String beforeNetad) {
        beforeNetad__ = beforeNetad;
    }
    /**
     * <p>beforeIpad を取得します。
     * @return beforeIpad
     */
    public String getBeforeIpad() {
        return beforeIpad__;
    }
    /**
     * <p>beforeIpad をセットします。
     * @param beforeIpad beforeIpad
     */
    public void setBeforeIpad(String beforeIpad) {
        beforeIpad__ = beforeIpad;
    }
    /**
     * <p>ipAddress を取得します。
     * @return ipAddress
     */
    public String getIpAddress() {
        return ipAddress__;
    }
    /**
     * <p>ipAddress をセットします。
     * @param ipAddress ipAddress
     */
    public void setIpAddress(String ipAddress) {
        ipAddress__ = ipAddress;
    }
    /**
     * <p>ipAddressList を取得します。
     * @return ipAddressList
     */
    public ArrayList<String> getIpAddressList() {
        return ipAddressList__;
    }
    /**
     * <p>ipAddressList をセットします。
     * @param ipAddressList ipAddressList
     */
    public void setIpAddressList(ArrayList<String> ipAddressList) {
        ipAddressList__ = ipAddressList;
    }
    /**
     * <p>networkAddress を取得します。
     * @return networkAddress
     */
    public String getNetworkAddress() {
        return networkAddress__;
    }
    /**
     * <p>networkAddress をセットします。
     * @param networkAddress networkAddress
     */
    public void setNetworkAddress(String networkAddress) {
        networkAddress__ = networkAddress;
    }
    /**
     * <p>subnetMask を取得します。
     * @return subnetMask
     */
    public String getSubnetMask() {
        return subnetMask__;
    }
    /**
     * <p>subnetMask をセットします。
     * @param subnetMask subnetMask
     */
    public void setSubnetMask(String subnetMask) {
        subnetMask__ = subnetMask;
    }
    /**
     * <p>netadList を取得します。
     * @return netadList
     */
    public ArrayList<String> getNetadList() {
        return netadList__;
    }
    /**
     * <p>netadList をセットします。
     * @param netadList netadList
     */
    public void setNetadList(ArrayList<String> netadList) {
        netadList__ = netadList;
    }
    /**
     * <p>editCmd を取得します。
     * @return editCmd
     */
    public String getEditCmd() {
        return editCmd__;
    }
    /**
     * <p>editCmd をセットします。
     * @param editCmd editCmd
     */
    public void setEditCmd(String editCmd) {
        editCmd__ = editCmd;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public int getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(int netSid) {
        netSid__ = netSid;
    }
}