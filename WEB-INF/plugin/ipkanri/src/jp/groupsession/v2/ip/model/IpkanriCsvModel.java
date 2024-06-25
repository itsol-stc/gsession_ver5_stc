package jp.groupsession.v2.ip.model;


import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] IP管理 CSV情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkanriCsvModel extends AbstractModel {

    /** ネットワークSID */
    private int netSid__;
    /** ネットワーク名 */
    private String netName__;
    /** ネットワークアドレス */
    private String netNetad__;
    /** サブネットマスク */
    private String netSabnet__;
    /** マシン名 */
    private String iadName__;
    /** IPアドレス */
    private String iadIpad__;
    /** 使用区分 */
    private String iadUseKbn__;
    /** コメント */
    private String iadMsg__;
    /** 使用者姓名 */
    private ArrayList<String> userSeiMei__;
    /** 資産管理番号 */
    private String iadPrtMngNum__;
    /**
     * <p>iadIpad を取得します。
     * @return iadIpad
     */
    public String getIadIpad() {
        return iadIpad__;
    }
    /**
     * <p>iadIpad をセットします。
     * @param iadIpad iadIpad
     */
    public void setIadIpad(String iadIpad) {
        this.iadIpad__ = iadIpad;
    }
    /**
     * <p>iadMsg を取得します。
     * @return iadMsg
     */
    public String getIadMsg() {
        return iadMsg__;
    }
    /**
     * <p>iadMsg をセットします。
     * @param iadMsg iadMsg
     */
    public void setIadMsg(String iadMsg) {
        iadMsg__ = iadMsg;
    }
    /**
     * <p>iadName を取得します。
     * @return iadName
     */
    public String getIadName() {
        return iadName__;
    }
    /**
     * <p>iadName をセットします。
     * @param iadName iadName
     */
    public void setIadName(String iadName) {
        iadName__ = iadName;
    }
    /**
     * <p>iadUseKbn を取得します。
     * @return iadUseKbn
     */
    public String getIadUseKbn() {
        return iadUseKbn__;
    }
    /**
     * <p>iadUseKbn をセットします。
     * @param iadUseKbn iadUseKbn
     */
    public void setIadUseKbn(String iadUseKbn) {
        iadUseKbn__ = iadUseKbn;
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
     * <p>netNetad を取得します。
     * @return netNetad
     */
    public String getNetNetad() {
        return netNetad__;
    }
    /**
     * <p>netNetad をセットします。
     * @param netNetad netNetad
     */
    public void setNetNetad(String netNetad) {
        netNetad__ = netNetad;
    }
    /**
     * <p>netSabnet を取得します。
     * @return netSabnet
     */
    public String getNetSabnet() {
        return netSabnet__;
    }
    /**
     * <p>netSabnet をセットします。
     * @param netSabnet netSabnet
     */
    public void setNetSabnet(String netSabnet) {
        netSabnet__ = netSabnet;
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
    /**
     * <p>userSeiMei を取得します。
     * @return userSeiMei
     */
    public ArrayList<String> getUserSeiMei() {
        return userSeiMei__;
    }
    /**
     * <p>userSeiMei をセットします。
     * @param userSeiMei userSeiMei
     */
    public void setUserSeiMei(ArrayList<String> userSeiMei) {
        userSeiMei__ = userSeiMei;
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
}