package jp.groupsession.v2.ip.model;

import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] IP管理 ネットワーク添付ファイル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkBinModel extends AbstractModel {

    /** ネットワークSID */
    private int netSid__;
    /** 採番ネットワークSID */
    private int newNetSid__;
    /** IPアドレスSID */
    private int iadSid__;
    /** 採番IPアドレスSID */
    private int newIadSid__;
    /** バイナリSID */
    private int usrSid__;
    /** バイナリSID */
    private Long binSid__ = new Long(0);
    /** 現在日付 */
    private UDate now__;
    /** 登録変更フラグ */
    private String cmdMode__;
    /** テンポラリディレクトリパス */
    private String tempDir__;
    /** アプリケーションのルートパス */
    private String appRootPath__;
    /** 添付ファイル名 */
    private String binFileName__;
    /** 添付ファイルパス */
    private String binFilePath__;
    /** 添付ファイルSID */
    private int binFileSid__;
    /** ネットワークの添付ファイル情報公開非公開 */
    private int tempDsp__ = 0;
    /** 添付ファイル名リスト */
    private ArrayList<String> fileNameList__ = null;
    /** 添付ファイルサイズ */
    private String binFileSizeDsp__ = null;
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
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
     * <p>iadSid を取得します。
     * @return iadSid
     */
    public int getIadSid() {
        return iadSid__;
    }
    /**
     * <p>iadSid をセットします。
     * @param iadSid iadSid
     */
    public void setIadSid(int iadSid) {
        iadSid__ = iadSid;
    }
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
     * <p>now を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now をセットします。
     * @param now now
     */
    public void setNow(UDate now) {
        now__ = now;
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
     * <p>cmdMode を取得します。
     * @return cmdMode
     */
    public String getCmdMode() {
        return cmdMode__;
    }
    /**
     * <p>cmdMode をセットします。
     * @param cmdMode cmdMode
     */
    public void setCmdMode(String cmdMode) {
        cmdMode__ = cmdMode;
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
     * <p>binFileName を取得します。
     * @return binFileName
     */
    public String getBinFileName() {
        return binFileName__;
    }
    /**
     * <p>binFileName をセットします。
     * @param binFileName binFileName
     */
    public void setBinFileName(String binFileName) {
        binFileName__ = binFileName;
    }
    /**
     * <p>binFilePath を取得します。
     * @return binFilePath
     */
    public String getBinFilePath() {
        return binFilePath__;
    }
    /**
     * <p>binFilePath をセットします。
     * @param binFilePath binFilePath
     */
    public void setBinFilePath(String binFilePath) {
        binFilePath__ = binFilePath;
    }
    /**
     * <p>binFileSid を取得します。
     * @return binFileSid
     */
    public int getBinFileSid() {
        return binFileSid__;
    }
    /**
     * <p>binFileSid をセットします。
     * @param binFileSid binFileSid
     */
    public void setBinFileSid(int binFileSid) {
        binFileSid__ = binFileSid;
    }
    /**
     * <p>tempDsp を取得します。
     * @return tempDsp
     */
    public int getTempDsp() {
        return tempDsp__;
    }
    /**
     * <p>tempDsp をセットします。
     * @param tempDsp tempDsp
     */
    public void setTempDsp(int tempDsp) {
        tempDsp__ = tempDsp;
    }
    /**
     * <p>fileNameList を取得します。
     * @return fileNameList
     */
    public ArrayList<String> getFileNameList() {
        return fileNameList__;
    }
    /**
     * <p>fileNameList をセットします。
     * @param fileNameList fileNameList
     */
    public void setFileNameList(ArrayList<String> fileNameList) {
        fileNameList__ = fileNameList;
    }
    /**
     * @return binFileSizeDsp
     */
    public String getBinFileSizeDsp() {
        return binFileSizeDsp__;
    }
    /**
     * @param binFileSizeDsp セットする binFileSizeDsp
     */
    public void setBinFileSizeDsp(String binFileSizeDsp) {
        binFileSizeDsp__ = binFileSizeDsp;
    }

}