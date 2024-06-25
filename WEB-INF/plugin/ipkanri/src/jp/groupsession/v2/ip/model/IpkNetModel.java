package jp.groupsession.v2.ip.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] IP管理 ネットワーク情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkNetModel extends AbstractModel {

    /** ネットワークSID */
    private int netSid__;
    /** ネットワーク名 */
    private String netName__;
    /** ネットワークアドレス */
    private String netNetad__;
    /** サブネットマスク */
    private String netSabnet__;
    /** ネットワーク公開区分 */
    private String netDsp__;
    /** コメント */
    private String netMsg__;
    /** 表示順 */
    private int netSort__;
    /** 登録者ID */
    private int netAuid__;
    /** 登録日時 */
    private UDate netAdate__;
    /** 更新ID */
    private int netEuid__;
    /** 更新日時 */
    private UDate netEdate__;
    /** ユーザSID */
    private int usrSid__;
    /** ユーザ姓 */
    private String usiSei__;
    /** ユーザ名 */
    private String usiMei__;
    /** 登録用ネットワーク管理者リスト */
    private int[] adminSidList__;
    /** 採番ネットワークSID */
    private int newNetSid__;
    /** ネットワーク管理者 */
    private boolean netAdm__;
    /** テンポラリディレクトリパス */
    private String tempDir__;
    /** アプリケーションのルートパス */
    private String appRootPath__;
    /**
     * <p>appRootPath を取得します。
     * @return appRootPath
     */
    public String getAppRootPath() {
        return appRootPath__;
    }
    /**
     * <p>appRootPath をセットします。
     * @param appRootPath appRootPath
     */
    public void setAppRootPath(String appRootPath) {
        appRootPath__ = appRootPath;
    }
    /**
     * <p>tempDir を取得します。
     * @return tempDir
     */
    public String getTempDir() {
        return tempDir__;
    }
    /**
     * <p>tempDir をセットします。
     * @param tempDir tempDir
     */
    public void setTempDir(String tempDir) {
        tempDir__ = tempDir;
    }
    /**
     * <p>adminSidList を取得します。
     * @return adminSidList
     */
    public int[] getAdminSidList() {
        return adminSidList__;
    }
    /**
     * <p>adminSidList をセットします。
     * @param adminSidList adminSidList
     */
    public void setAdminSidList(int[] adminSidList) {
        this.adminSidList__ = adminSidList;
    }

    /**
     * <p>netAdm を取得します。
     * @return netAdm
     */
    public boolean isNetAdm() {
        return netAdm__;
    }
    /**
     * <p>netAdm をセットします。
     * @param netAdm netAdm
     */
    public void setNetAdm(boolean netAdm) {
        netAdm__ = netAdm;
    }
    /**
     * <p>netAuid を取得します。
     * @return netAuid
     */
    public int getNetAuid() {
        return netAuid__;
    }
    /**
     * <p>netAuid をセットします。
     * @param netAuid netAuid
     */
    public void setNetAuid(int netAuid) {
        netAuid__ = netAuid;
    }
    /**
     * <p>netAdate を取得します。
     * @return netAdate
     */
    public UDate getNetAdate() {
        return netAdate__;
    }
    /**
     * <p>netAdate をセットします。
     * @param netAdate netAdate
     */
    public void setNetAdate(UDate netAdate) {
        netAdate__ = netAdate;
    }
    /**
     * <p>netEdate を取得します。
     * @return netEdate
     */
    public UDate getNetEdate() {
        return netEdate__;
    }
    /**
     * <p>netEdate をセットします。
     * @param netEdate netEdate
     */
    public void setNetEdate(UDate netEdate) {
        netEdate__ = netEdate;
    }
    /**
     * <p>netEuid を取得します。
     * @return netEuid
     */
    public int getNetEuid() {
        return netEuid__;
    }
    /**
     * <p>netEuid をセットします。
     * @param netEuid netEuid
     */
    public void setNetEuid(int netEuid) {
        netEuid__ = netEuid;
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
     * <p>netSort を取得します。
     * @return netSort
     */
    public int getNetSort() {
        return netSort__;
    }
    /**
     * <p>netSort をセットします。
     * @param netSort netSort
     */
    public void setNetSort(int netSort) {
        netSort__ = netSort;
    }
    /**
     * <p>newNetSid を取得します。
     * @return newNetSid
     */
    public int getNewNetSid() {
        return newNetSid__;
    }
    /**
     * <p>newNetSid をセットします。
     * @param newNetSid newNetSid
     */
    public void setNewNetSid(int newNetSid) {
        newNetSid__ = newNetSid;
    }
    /**
     * <p>usiMei を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei をセットします。
     * @param usiMei usiMei
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>usiSei を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei をセットします。
     * @param usiSei usiSei
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
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
}