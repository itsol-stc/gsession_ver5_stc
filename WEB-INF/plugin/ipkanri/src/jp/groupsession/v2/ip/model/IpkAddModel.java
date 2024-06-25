package jp.groupsession.v2.ip.model;

import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.ip.IpkConst;

/**
 * <br>[機  能] IP管理 IPアドレス情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkAddModel extends AbstractModel {

    /** ネットワークアドレス */
    private String netNetad__;
    /** サブネットマスク */
    private String netSabnet__;
    /** ネットワーク名 */
    private String netName__;
    /** サブネットマスク */
    private String netDsp__;
    /** IPアドレスSID */
    private int newIadSid__;
    /** IPアドレスSID */
    private int iadSid__;
    /** ネットワークSID */
    private int netSid__;
    /** マシン名 */
    private String iadName__;
    /** IPアドレス */
    private long iadIpad__;
    /** IPアドレス表示用 */
    private String iadIpadDsp__;
    /** 使用区分 */
    private String iadUseKbn__ = IpkConst.USEDKBN_SIYOU;
    /** コメント */
    private String iadMsg__;
    /** 登録者ID */
    private int iadAuid__;
    /** 登録日時 */
    private UDate iadAdate__;
    /** 更新ID */
    private int iadEuid__;
    /** 更新日時 */
    private UDate iadEdate__;
    /** 使用者姓 */
    private String userSei__;
    /** 使用者名 */
    private String userMei__;
    /** 使用者姓名 */
    private ArrayList<IpkAddAdmModel> userSeiMei__;
    /** 使用者フラグ */
    private boolean iadAdmFlg__;
    /** 資産管理番号 */
    private String iadPrtMngNum__ = null;
    /** CPU名 */
    private String iadCpuName__;
    /** メモリ名 */
    private String iadMemoryName__;
    /** HD名 */
    private String iadHdName__;
    /** CPUSID */
    private int iadCpu__;
    /** メモリSID */
    private int iadMemory__;
    /** HDSID */
    private int iadHd__;
    /** オフセット */
    private int iadOffset__;
    /** 1ページ表示件数 */
    private int iadLimit__;
    /** オーダーキー */
    private int orderKey__;
    /** ソートキー */
    private int sortKey__;
    /** 変更前のIPアドレス */
    private long beforeIpad__;
    /** ページコマンド */
    private String cmd__;
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
     * <p>beforeIpad を取得します。
     * @return beforeIpad
     */
    public long getBeforeIpad() {
        return beforeIpad__;
    }

    /**
     * <p>beforeIpad をセットします。
     * @param beforeIpad beforeIpad
     */
    public void setBeforeIpad(long beforeIpad) {
        beforeIpad__ = beforeIpad;
    }

    /**
     * <p>iadLimit を取得します。
     * @return iadLimit
     */
    public int getIadLimit() {
        return iadLimit__;
    }

    /**
     * <p>iadLimit をセットします。
     * @param iadLimit iadLimit
     */
    public void setIadLimit(int iadLimit) {
        iadLimit__ = iadLimit;
    }

    /**
     * <p>iadOffset を取得します。
     * @return iadOffset
     */
    public int getIadOffset() {
        return iadOffset__;
    }

    /**
     * <p>iadAdmFlg を取得します。
     * @return iadAdmFlg
     */
    public boolean isIadAdmFlg() {
        return iadAdmFlg__;
    }

    /**
     * <p>iadAdmFlg をセットします。
     * @param iadAdmFlg iadAdmFlg
     */
    public void setIadAdmFlg(boolean iadAdmFlg) {
        iadAdmFlg__ = iadAdmFlg;
    }

    /**
     * <p>iadOffset をセットします。
     * @param iadOffset iadOffset
     */
    public void setIadOffset(int iadOffset) {
        iadOffset__ = iadOffset;
    }
    /**
     * <p>userMei を取得します。
     * @return userMei
     */
    public String getUserMei() {
        return userMei__;
    }
    /**
     * <p>userMei をセットします。
     * @param userMei userMei
     */
    public void setUserMei(String userMei) {
        userMei__ = userMei;
    }
    /**
     * <p>userSei を取得します。
     * @return userSei
     */
    public String getUserSei() {
        return userSei__;
    }
    /**
     * <p>userSei をセットします。
     * @param userSei userSei
     */
    public void setUserSei(String userSei) {
        userSei__ = userSei;
    }
    /**
     * @return iadAuid を戻します。
     */
    public int getIadAuid() {
        return iadAuid__;
    }
    /**
     * @param num 設定する num
     */
    public void setIadAuid(int num) {
        this.iadAuid__ = num;
    }
    /**
     * <p>iadAdate を取得します。
     * @return iadAdate
     */
    public UDate getIadAdate() {
        return iadAdate__;
    }

    /**
     * <p>iadAdate をセットします。
     * @param iadAdate iadAdate
     */
    public void setIadAdate(UDate iadAdate) {
        iadAdate__ = iadAdate;
    }

    /**
     * <p>iadEdate を取得します。
     * @return iadEdate
     */
    public UDate getIadEdate() {
        return iadEdate__;
    }

    /**
     * <p>iadEdate をセットします。
     * @param iadEdate iadEdate
     */
    public void setIadEdate(UDate iadEdate) {
        iadEdate__ = iadEdate;
    }

    /**
     * @return iadEuid を戻します。
     */
    public int getIadEuid() {
        return iadEuid__;
    }
    /**
     * @param num 設定する num
     */
    public void setIadEuid(int num) {
        this.iadEuid__ = num;
    }
    /**
     * @return iadMsg を戻します。
     */
    public String getIadMsg() {
        return iadMsg__;
    }
    /**
     * @param str 設定する str
     */
    public void setIadMsg(String str) {
        this.iadMsg__ = str;
    }
    /**
     * @return iadName を戻します。
     */
    public String getIadName() {
        return iadName__;
    }
    /**
     * @param num 設定する num
     */
    public void setIadName(String num) {
        this.iadName__ = num;
    }
    /**
     * @return iadSid を戻します。
     */
    public int getIadSid() {
        return iadSid__;
    }
    /**
     * @param num 設定する num
     */
    public void setIadSid(int num) {
        this.iadSid__ = num;
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
     * @return netSid を戻します。
     */
    public int getNetSid() {
        return netSid__;
    }
    /**
     * @param intSid 設定する intSid
     */
    public void setNetSid(int intSid) {
        this.netSid__ = intSid;
    }
    /**
     * <p>iadIpad を取得します。
     * @return iadIpad
     */
    public long getIadIpad() {
        return iadIpad__;
    }
    /**
     * <p>iadIpad をセットします。
     * @param addIpad addIpad
     */
    public void setIadIpad(long addIpad) {
        this.iadIpad__ = addIpad;
    }
    /**
     * <p>newIadSid を取得します。
     * @return newIadSid
     */
    public int getNewIadSid() {
        return newIadSid__;
    }
    /**
     * <p>newIadSid をセットします。
     * @param newIadSid newIadSid
     */
    public void setNewIadSid(int newIadSid) {
        newIadSid__ = newIadSid;
    }
    /**
     * <p>userSeiMei を取得します。
     * @return userSeiMei
     */
    public ArrayList<IpkAddAdmModel> getUserSeiMei() {
        return userSeiMei__;
    }

    /**
     * <p>userSeiMei をセットします。
     * @param userSeiMei userSeiMei
     */
    public void setUserSeiMei(ArrayList<IpkAddAdmModel> userSeiMei) {
        userSeiMei__ = userSeiMei;
    }

    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }

    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }

    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }

    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
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
     * <p>iadIpadDsp を取得します。
     * @return iadIpadDsp
     */
    public String getIadIpadDsp() {
        return iadIpadDsp__;
    }

    /**
     * <p>iadIpadDsp をセットします。
     * @param iadIpadDsp iadIpadDsp
     */
    public void setIadIpadDsp(String iadIpadDsp) {
        iadIpadDsp__ = iadIpadDsp;
    }

    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }

    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
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
     * <p>iadCpu を取得します。
     * @return iadCpu
     */
    public int getIadCpu() {
        return iadCpu__;
    }

    /**
     * <p>iadCpu をセットします。
     * @param iadCpu iadCpu
     */
    public void setIadCpu(int iadCpu) {
        iadCpu__ = iadCpu;
    }

    /**
     * <p>iadHd を取得します。
     * @return iadHd
     */
    public int getIadHd() {
        return iadHd__;
    }

    /**
     * <p>iadHd をセットします。
     * @param iadHd iadHd
     */
    public void setIadHd(int iadHd) {
        iadHd__ = iadHd;
    }

    /**
     * <p>iadMemory を取得します。
     * @return iadMemory
     */
    public int getIadMemory() {
        return iadMemory__;
    }

    /**
     * <p>iadMemory をセットします。
     * @param iadMemory iadMemory
     */
    public void setIadMemory(int iadMemory) {
        iadMemory__ = iadMemory;
    }

    /**
     * <p>iadCpuName を取得します。
     * @return iadCpuName
     */
    public String getIadCpuName() {
        return iadCpuName__;
    }

    /**
     * <p>iadCpuName をセットします。
     * @param iadCpuName iadCpuName
     */
    public void setIadCpuName(String iadCpuName) {
        iadCpuName__ = iadCpuName;
    }

    /**
     * <p>iadHdName を取得します。
     * @return iadHdName
     */
    public String getIadHdName() {
        return iadHdName__;
    }

    /**
     * <p>iadHdName をセットします。
     * @param iadHdName iadHdName
     */
    public void setIadHdName(String iadHdName) {
        iadHdName__ = iadHdName;
    }

    /**
     * <p>iadMemoryName を取得します。
     * @return iadMemoryName
     */
    public String getIadMemoryName() {
        return iadMemoryName__;
    }

    /**
     * <p>iadMemoryName をセットします。
     * @param iadMemoryName iadMemoryName
     */
    public void setIadMemoryName(String iadMemoryName) {
        iadMemoryName__ = iadMemoryName;
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