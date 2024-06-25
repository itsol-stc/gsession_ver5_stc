package jp.groupsession.v2.api.smail.mail.search;

import org.apache.oro.text.perl.Perl5Util;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] 検索条件に該当するショートメールリストを取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-mail/search",
plugin = "smail", name = "ショートメール詳細検索",
url = "/api/smail/mail/search.do", reqtype = "GET")
public class ApiSmlMailSearchForm extends AbstractApiForm {

    /** アカウントSID*/
    @ApiParam(name = "sacSid", viewName = "アカウントSID")
    private int sacSid__  = -1;

    /** メール種別*/
    @ApiParam(name = "smlKbn", viewName = "メール種別")
    private int smlKbn__ = -1;

    /** データ開始位置*/
    private int offset__  = 0;

    /** データ取得件数*/
    private int count__   = 1;

    /** 検索日時(開始) */
    private String fromTime__;
    /** 検索日時(終了) */
    private String toTime__;

    /** メールマーク*/
    private int mark__ = -1;

    /** 検索キーワード*/
    private String keyword__;

    /** 検索キーワード対象*/
    private int targetKbn__ = 3;

    /** 差出人*/
    private int sendFrom__ = -1;

    /** 宛先*/
    private int[] sendTo__;
    
    /** メールSID*/
    private int smlSid__ = -1;


    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }
    /**
     * <p>sacSid をセットします。
     * @param sacSid アカウントSID
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>smlKbn を取得します。
     * @return smlKbn
     */
    public int getSmlKbn() {
        return smlKbn__;
    }
    /**
     * <p>smlKbn をセットします。
     * @param smlKbn メール区分
     */
    public void setSmlKbn(int smlKbn) {
        smlKbn__ = smlKbn;
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
        return this.convertDateString(fromTime__, false);
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
        return this.convertDateString(toTime__, true);
    }

    /**
     * <p>mark を取得します。
     * @return mark
     */
    public int getMark() {
        return mark__;
    }
    /**
     * <p>mark をセットします。
     * @param mark 検索条件 マーク
     */
    public void setMark(int mark) {
        mark__ = mark;
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
     * @param keyword 検索キーワード
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }

    /**
     * <p>targetKbn を取得します。
     * @return targetKbn
     */
    public int getTargetKbn() {
        return targetKbn__;
    }
    /**
     * <p>targetKbn をセットします。
     * @param targetKbn 検索キーワード対象
     */
    public void setTargetKbn(int targetKbn) {
        targetKbn__ = targetKbn;
    }

    /**
     * <p>sendFrom を取得します。
     * @return sendFrom
     */
    public int getSendFrom() {
        return sendFrom__;
    }
    /**
     * <p>sendFrom をセットします。
     * @param sendFrom 検索条件 送信ユーザ
     */
    public void setSendFrom(int sendFrom) {
        sendFrom__ = sendFrom;
    }

    /**
     * <p>sendTo を取得します。
     * @return sendTo
     */
    public int[] getSendTo() {
        return sendTo__;
    }
    /**
     * <p>sendTo をセットします。
     * @param sendTo 検索条件 宛先
     */
    public void setSendTo(int[] sendTo) {
        sendTo__ = sendTo;
    }

    /**
     * <p>smlSid を取得します。
     * @return smlSid
     * @see jp.groupsession.v2.api.smail.mail.search.ApiSmlMailSearchForm#smlSid
     */
    public int getSmlSid() {
        return smlSid__;
    }
    /**
     * <p>smlSid をセットします。
     * @param smlSid smlSid
     * @see jp.groupsession.v2.api.smail.mail.search.ApiSmlMailSearchForm#smlSid
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }
    
    /**
     * <br>[機  能] 日時データ 文字列 → Date型 へ変換
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dateTime 変換したい日時文字列
     * @param isMax    最大値判定
     * @return 日時データ(Date型)
     */
    private UDate convertDateString(String dateTime, boolean isMax) {
        if (dateTime != null && dateTime.length() >= 10) {
            // 日付フォーマットチェック
            Perl5Util util = new Perl5Util();
            if (util.match("/^[0-9]{4}/[0-9]{2}/[0-9]{2}$/", dateTime.substring(0, 10))) {
                UDate date = UDateUtil.getUDate(dateTime.substring(0, 4),
                                                dateTime.substring(5, 7),
                                                dateTime.substring(8, 10));
                if (isMax) {
                    date.setMaxHhMmSs();  // 最大値基準
                } else {
                    date.setZeroHhMmSs(); // 0基準
                }

                // 時間フォーマットチェック
                if (dateTime.length() >= 19) {
                    if (util.match("/^[0-9]{2}:[0-9]{2}:[0-9]{2}$/", dateTime.substring(11))) {
                        date.setHour(Integer.valueOf(dateTime.substring(11, 13)));
                        date.setMinute(Integer.valueOf(dateTime.substring(14, 16)));
                        date.setSecond(Integer.valueOf(dateTime.substring(17, 19)));
                    }
                } else if (dateTime.length() >= 16) {
                    if (util.match("/^[0-9]{2}:[0-9]{2}$/", dateTime.substring(11))) {
                        date.setHour(Integer.valueOf(dateTime.substring(11, 13)));
                        date.setMinute(Integer.valueOf(dateTime.substring(14, 16)));
                    }
                }
                return date;
            }
        }
        return null;
    }
}
