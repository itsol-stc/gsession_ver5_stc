package jp.groupsession.v2.api.webmail.mail.search;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.webmail.mail.ApiWmlMailBiz;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.wml.wml010.Wml010Const;

/**
 * <br>[機  能] 検索条件に該当するメール情報一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-mail/search",
plugin = "webmail", name = "メール一覧取得",
url = "/api/webmail/mail/search.do", reqtype = "GET")
public class ApiWmlMailSearchForm extends AbstractApiForm {

    /** 受信チェック時間(秒) */
    public static final int SEARCH_RECEIVE_MAIL_TIME = 30;

    /** アカウントSID*/
    @ApiParam(name = "wacSid", viewName = "アカウントSID")
    private int wacSid__  = -1;

    /** ディレクトリSID*/
    private long wdrSid__  = -1;

    /** データ開始位置*/
    private int offset__  = 0;

    /** データ取得件数*/
    private int count__   = 1;

    /** 検索日時(開始) */
    private String fromTime__ = null;
    /** 検索日時(終了) */
    private String toTime__ = null;

    /** 検索ワード*/
    private String keyword__ = null;

    /** ラベルSID*/
    private int labelSid__ = 0;

    /** 差出人*/
    private String sendFrom__ = null;

    /** 宛先*/
    private String sendTo__ = null;

    /** 添付ファイルチェック*/
    private boolean tempFile__ = false;

    /** 未読/既読 チェック*/
    private int readKbn__ = Wml010Const.SEARCH_READKBN_NOSET; // 0:未設定 / 1:未読 / 2:既読

    /** 新着更新フラグ*/
    private boolean newFlg__ = false;


    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid アカウントSID
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>wdrSid を取得します。
     * @return wdrSid
     */
    public long getWdrSid() {
        return wdrSid__;
    }
    /**
     * <p>wdrSid をセットします。
     * @param wdrSid アカウントディレクトリSID
     */
    public void setWdrSid(long wdrSid) {
        wdrSid__ = wdrSid;
    }

    /**
     * <p>offset を取得します。
     * @return count
     */
    public int getOffset() {
        return offset__;
    }
    /**
     * <p>offset をセットします。
     * @param offset オフセット位置
     */
    public void setOffset(int offset) {
        offset__ = offset;
    }

    /**
     * <p>count を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }
    /**
     * <p>count をセットします。
     * @param count 取得件数
     */
    public void setCount(int count) {
        count__ = count;
    }

    /**
     * <p>fromTime を取得します。
     * @return fromTime
     */
    public String getFromTime() {
        return fromTime__;
    }
    /**
     * <p>fromTime をセットします。
     * @param fromTime 日時指定 開始
     */
    public void setFromTime(String fromTime) {
        fromTime__ = fromTime;
    }
    /**
     * <p>fromTime を UDate型 で取得します。
     * @return 日付データ
     */
    public UDate getFromTimeDate() {
        return ApiWmlMailBiz.convertSlashDateTimeFormat(fromTime__, false);
    }

    /**
     * <p>toTime を取得します。
     * @return toTime
     */
    public String getToTime() {
        return toTime__;
    }
    /**
     * <p>toTime をセットします。
     * @param toTime 日時指定 終了
     */
    public void setToTime(String toTime) {
        toTime__ = toTime;
    }
    /**
     * <p>toTime を UDate型 で取得します。
     * @return 日付データ
     */
    public UDate getToTimeDate() {
        return ApiWmlMailBiz.convertSlashDateTimeFormat(toTime__, true);
    }

    /**
     * <p>keyword を取得します。
     * @return keyword
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * <p>keyword をセットします。
     * @param keyword 検索条件 キーワード
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }

    /**
     * <p>labelSid を取得します。
     * @return labelSid
     */
    public int getLabelSid() {
        return labelSid__;
    }
    /**
     * <p>labelSid をセットします。
     * @param labelSid 検索条件 ラベル
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }

    /**
     * <p>sendFrom を取得します。
     * @return sendFrom
     */
    public String getSendFrom() {
        return sendFrom__;
    }
    /**
     * <p>sendFrom をセットします。
     * @param sendFrom 検索条件 送信ユーザ
     */
    public void setSendFrom(String sendFrom) {
        sendFrom__ = sendFrom;
    }

    /**
     * <p>sendTo を取得します。
     * @return sendTo
     */
    public String getSendTo() {
        return sendTo__;
    }
    /**
     * <p>sendTo をセットします。
     * @param sendTo 検索条件 宛先
     */
    public void setSendTo(String sendTo) {
        sendTo__ = sendTo;
    }

    /**
     * <p>tempFile を取得します。
     * @return tempFile
     */
    public boolean isTempFile() {
        return tempFile__;
    }
    /**
     * <p>tempFile をセットします。
     * @param tempFile 検索条件 添付ファイルチェック
     */
    public void setTempFile(boolean tempFile) {
        tempFile__ = tempFile;
    }

    /**
     * <p>readKbn を取得します。
     * @return readKbn
     */
    public int getReadKbn() {
        return readKbn__;
    }
    /**
     * <p>readKbn をセットします。
     * @param readKbn 検索条件 未読・既読 区分
     */
    public void setReadKbn(int readKbn) {
        readKbn__ = readKbn;
    }

    /**
     * <p>newFlg を取得します。
     * @return newFlg
     */
    public boolean isNewFlg() {
        return newFlg__;
    }
    /**
     * <p>newFlg をセットします。
     * @param newFlg 新着更新フラグ
     */
    public void setNewFlg(boolean newFlg) {
        newFlg__ = newFlg;
    }
}
