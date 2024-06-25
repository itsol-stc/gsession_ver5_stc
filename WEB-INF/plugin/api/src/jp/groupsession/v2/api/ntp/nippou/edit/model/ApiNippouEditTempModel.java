package jp.groupsession.v2.api.ntp.nippou.edit.model;
/**
 * <br>[機  能] WEBAPI 日報添付ファイル編集構造
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouEditTempModel {
    /** 編集コマンド*/
    private String cmd__;
    /** バイナリSID*/
    private int binSid__;
    /** ファイルネーム*/
    private String binFileName__;
    /** バイナリデータ*/
    private byte[] binData__;
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
     * <p>binSid を取得します。
     * @return binSid
     */
    public int getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(int binSid) {
        binSid__ = binSid;
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
     * <p>binData を取得します。
     * @return binData
     */
    public byte[] getBinData() {
        return binData__;
    }
    /**
     * <p>binData をセットします。
     * @param binData binData
     */
    public void setBinData(byte[] binData) {
        binData__ = binData;
    }


}
