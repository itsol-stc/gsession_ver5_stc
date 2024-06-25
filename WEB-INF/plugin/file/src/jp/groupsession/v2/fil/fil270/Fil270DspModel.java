package jp.groupsession.v2.fil.fil270;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] ファイル管理統計情報表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Fil270DspModel implements Serializable {

    /** 日付 */
    private String date__;
    /** 日付 (UDate) */
    private UDate uDate__;
    /** 表示用日付 */
    private String dspDate__;
    /** ダウンロード件数 */
    private int downloadNum__;
    /** ダウンロード件数（表示用） */
    private String strDownloadNum__;
    /** アップロード件数 */
    private int uploadNum__;
    /** アップロード件数（表示用） */
    private String strUploadNum__;

    /**
     * <p>date を取得します。
     * @return date
     */
    public String getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setDate(String date) {
        date__ = date;
    }
    /**
     * <p>dspDate を取得します。
     * @return dspDate
     */
    public String getDspDate() {
        return dspDate__;
    }
    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }
    /**
     * <p>downloadNum を取得します。
     * @return downloadNum
     */
    public int getDownloadNum() {
        return downloadNum__;
    }
    /**
     * <p>downloadNum をセットします。
     * @param downloadNum downloadNum
     */
    public void setDownloadNum(int downloadNum) {
        downloadNum__ = downloadNum;
    }
    /**
     * <p>strDownloadNum を取得します。
     * @return strDownloadNum
     */
    public String getStrDownloadNum() {
        return strDownloadNum__;
    }
    /**
     * <p>strDownloadNum をセットします。
     * @param strDownloadNum strDownloadNum
     */
    public void setStrDownloadNum(String strDownloadNum) {
        strDownloadNum__ = strDownloadNum;
    }
    /**
     * <p>uploadNum を取得します。
     * @return uploadNum
     */
    public int getUploadNum() {
        return uploadNum__;
    }
    /**
     * <p>uploadNum をセットします。
     * @param uploadNum uploadNum
     */
    public void setUploadNum(int uploadNum) {
        uploadNum__ = uploadNum;
    }
    /**
     * <p>strUploadNum を取得します。
     * @return strUploadNum
     */
    public String getStrUploadNum() {
        return strUploadNum__;
    }
    /**
     * <p>strUploadNum をセットします。
     * @param strUploadNum strUploadNum
     */
    public void setStrUploadNum(String strUploadNum) {
        strUploadNum__ = strUploadNum;
    }
    /**
     * <p>uDate を取得します。
     * @return uDate
     */
    public UDate getuDate() {
        return uDate__;
    }
    /**
     * <p>uDate をセットします。
     * @param uDate uDate
     */
    public void setuDate(UDate uDate) {
        uDate__ = uDate;
    }

}