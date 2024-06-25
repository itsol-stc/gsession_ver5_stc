package jp.groupsession.v2.zsk.model;

/**
 * <br>[機  能] 座席表の情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZaiInfoPlusModel extends ZaiInfoModel {

    /** 座席表ファイル名 */
    private String zifFileName__;
    /** 座席表ファイルパス */
    private String zifFilePath__;
    /**
     * <p>zifFileName を取得します。
     * @return zifFileName
     */
    public String getZifFileName() {
        return zifFileName__;
    }
    /**
     * <p>zifFileName をセットします。
     * @param zifFileName zifFileName
     */
    public void setZifFileName(String zifFileName) {
        zifFileName__ = zifFileName;
    }
    /**
     * <p>zifFilePath を取得します。
     * @return zifFilePath
     */
    public String getZifFilePath() {
        return zifFilePath__;
    }
    /**
     * <p>zifFilePath をセットします。
     * @param zifFilePath zifFilePath
     */
    public void setZifFilePath(String zifFilePath) {
        zifFilePath__ = zifFilePath;
    }

}
