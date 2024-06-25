package jp.groupsession.v2.ntp.excel;

/**
 * <br>[機  能] インポートサンプルExcel出力時のパラメータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class NtpSampleExcelModel {

    /** アプリケーションルートパス */
    String appRootPath__ = null;
    /** フォーマットファイル名 */
    String formatFile__ = null;
    /** 出力ファイル名 */
    String outPutFile__ = null;

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
     * <p>formatFile を取得します。
     * @return formatFile
     */
    public String getFormatFile() {
        return formatFile__;
    }
    /**
     * <p>formatFile をセットします。
     * @param formatFile formatFile
     */
    public void setFormatFile(String formatFile) {
        formatFile__ = formatFile;
    }
    /**
     * <p>outPutFile を取得します。
     * @return outPutFile
     */
    public String getOutPutFile() {
        return outPutFile__;
    }
    /**
     * <p>outPutFile をセットします。
     * @param outPutFile outPutFile
     */
    public void setOutPutFile(String outPutFile) {
        outPutFile__ = outPutFile;
    }
}