package jp.groupsession.v2.anp.anp999;

import jp.groupsession.v2.anp.AbstractAnpiMobileForm;


/**
 * <br>[機  能] 安否確認モバイル版のメッセージ画面のForm
 * <br>[解  説]
 * <br>[備  考]
 *
 */
public class Anp999Form extends AbstractAnpiMobileForm {

    /** OKボタンタイプ */
    public static final int TYPE_OK = 0;
    /** OK,Cancelボタンタイプ */
    public static final int TYPE_OKCANCEL = 1;
    /** OK,戻るボタンタイプ */
    public static final int TYPE_OKBACK = 2;
    /** エラー送信ボタンタイプ */
    public static final int TYPE_ERROR_REPORT = 3;
    /** 警告画面 */
    public static final int ICON_WARN = 0;
    /** インフォメーション画面 */
    public static final int ICON_INFO = 1;

    /** OKボタン */
    public static final String TYPE_OK_VALUE = "ok";

    /** リトライ先 */
    private String retryURL__;
    /** ボタンタイプ */
    private int type__ = -1;
    /** アイコンイメージ */
    private int icon__;
    /** エラー報告ボタンURL */
    private String urlReport__;
    /** OKボタンに表示する文字 */
    private String okBtnValue__ = "ＯＫ";
    /** 画面タイトル */
    private String title__;
    /** 画面メッセージ */
    private String message__;

    /** エラーログ（動作環境付加） */
    private String errorLog__;
    /** エラーログ */
    private String errorLogOnly__;

    /** 動作環境 */
    private String detailInfo__;

    /**
     * <p>retryURL を取得します。
     * @return retryURL
     */
    public String getRetryURL() {
        return retryURL__;
    }
    /**
     * <p>retryURL をセットします。
     * @param retryURL retryURL
     */
    public void setRetryURL(String retryURL) {
        retryURL__ = retryURL;
    }
    /**
     * <p>icon__を取得します。
     * @return icon__を戻します。
     */
    public int getIcon() {
        return icon__;
    }
    /**
     * <p>icon__をセットします。
     * @param icon icon__を設定。
     */
    public void setIcon(int icon) {
        icon__ = icon;
    }
    /**
     * <p>message__を取得します。
     * @return message__を戻します。
     */
    public String getMessage() {
        return message__;
    }
    /**
     * <p>message__をセットします。
     * @param message message__を設定。
     */
    public void setMessage(String message) {
        message__ = message;
    }
    /**
     * <p>title__を取得します。
     * @return title__を戻します。
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title__をセットします。
     * @param title title__を設定。
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>type__を取得します。
     * @return type__を戻します。
     */
    public int getType() {
        return type__;
    }
    /**
     * <p>type__をセットします。
     * @param type type__を設定。
     */
    public void setType(int type) {
        type__ = type;
    }
    /**
     * @return okBtnValue を戻します。
     */
    public String getOkBtnValue() {
        return okBtnValue__;
    }
    /**
     * @param okBtnValue okBtnValue を設定。
     */
    public void setOkBtnValue(String okBtnValue) {
        okBtnValue__ = okBtnValue;
    }
    /**
     * <p>errorLog を取得します。
     * @return errorLog
     */
    public String getErrorLog() {
        return errorLog__;
    }
    /**
     * <p>errorLog をセットします。
     * @param errorLog errorLog
     */
    public void setErrorLog(String errorLog) {
        errorLog__ = errorLog;
    }
    /**
     * <p>urlReport を取得します。
     * @return urlReport
     */
    public String getUrlReport() {
        return urlReport__;
    }
    /**
     * <p>urlReport をセットします。
     * @param urlReport urlReport
     */
    public void setUrlReport(String urlReport) {
        urlReport__ = urlReport;
    }
    /**
     * @return detailInfo
     */
    public String getDetailInfo() {
        return detailInfo__;
    }
    /**
     * @param detailInfo 設定する detailInfo
     */
    public void setDetailInfo(String detailInfo) {
        detailInfo__ = detailInfo;
    }
    /**
     * @return errorLogOnly
     */
    public String getErrorLogOnly() {
        return errorLogOnly__;
    }
    /**
     * @param errorLogOnly 設定する errorLogOnly
     */
    public void setErrorLogOnly(String errorLogOnly) {
        errorLogOnly__ = errorLogOnly;
    }
}