package jp.groupsession.v2.ntp.ntp010;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>日報モデル
 * @author JTS
 */
public class SimpleNippouModel extends AbstractModel {

    /** 日報SID */
    private int ntpSid__;
    /** 日報時間文字列 */
    private String time__;
    /** 日報タイトル文字列 */
    private String title__;
//    /** 日報確認者未確認区分 --追加*/
//    private int nkk_chkkbn__;
//    /** 日報確認者全数 --追加*/
//    private int nkk_allcnt__;
//    /** 日報確認者確認済数 --追加*/
//    private int nkk_chkcnt__;

    /** 日報確認区分  0:未確認   1:確認済 */
    private int ntp_chkKbn__;

    /** コメント存在区分  0:1桁   1:2桁  2:3桁以上*/
    private int ntp_cmtkbn__;
    /** コメント件数  */
    private int ntp_cmtCnt__;

    /** いいね存在区分  0:1桁   1:2桁  2:3桁以上*/
    private int ntp_goodkbn__;
    /** いいね件数  */
    private int ntp_goodCnt__;

    //日間表示で使用する項目
    /** 背景色 */
    private int titleColor__;
    /** 日報開始日時*/
    private UDate fromTime__;
    /** 日報終了日時*/
    private UDate toTime__;
    //一覧にて使用する項目
    /** 日報表示日付*/
    private String ntpDateStr__;
    /** 日報日付*/
    private String ntpDspDateStr__;
    /** 開始日時文字列*/
    private String fromTimeStr__;
    /** 終了日時文字列*/
    private String toTiemStr__;
    /** 内容 */
    private String detail__;
    /** 次のアクション */
    private String action__;
    /** 被登録者SID */
    private String userSid__;
    /** 被登録者氏名 */
    private String userName__;
    /** 被登録者区分 */
    private String userKbn__;
    /** 被登録者ログイン停止フラグ */
    private int userUkoFlg__;
    /** 案件SID */
    private int ankenSid__;
    /** 案件名 */
    private String ankenName__;
    /** 案件閲覧可能か*/
    private boolean ankenViewable__ = true;
    /**
     * <p>userKbn を取得します。
     * @return userKbn
     */
    public String getUserKbn() {
        return userKbn__;
    }
    /**
     * <p>userKbn をセットします。
     * @param userKbn userKbn
     */
    public void setUserKbn(String userKbn) {
        userKbn__ = userKbn;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public String getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(String userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }
    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }
    /**
     * <p>detail を取得します。
     * @return valueStr
     */
    public String getDetail() {
        return detail__;
    }
    /**
     * <p>valueStr をセットします。
     * @param detail detail
     */
    public void setDetail(String detail) {
        detail__ = detail;
    }
    /**
     * <p>fromDateStr を取得します。
     * @return fromDateStr
     */
    public String getFromDateStr() {
        return fromTimeStr__;
    }
    /**
     * <p>fromDateStr をセットします。
     * @param fromDateStr fromDateStr
     */
    public void setFromDateStr(String fromDateStr) {
        fromTimeStr__ = fromDateStr;
    }
    /**
     * <p>toDateStr を取得します。
     * @return toDateStr
     */
    public String getToDateStr() {
        return toTiemStr__;
    }
    /**
     * <p>toDateStr をセットします。
     * @param toDateStr toDateStr
     */
    public void setToDateStr(String toDateStr) {
        toTiemStr__ = toDateStr;
    }
    /**
     * @return titleColor を戻します。
     */
    public int getTitleColor() {
        return titleColor__;
    }
    /**
     * @param titleColor 設定する titleColor。
     */
    public void setTitleColor(int titleColor) {
        titleColor__ = titleColor;
    }
    /**
     * @return fromDate を戻します。
     */
    public UDate getFromTime() {
        return fromTime__;
    }
    /**
     * @param fromTime 設定する fromTime。
     */
    public void setFromTime(UDate fromTime) {
        fromTime__ = fromTime;
    }
    /**
     * @return toDate を戻します。
     */
    public UDate getToTime() {
        return toTime__;
    }
    /**
     * @param toTime 設定する toTime。
     */
    public void setToTime(UDate toTime) {
        toTime__ = toTime;
    }
    /**
     * @return schSid を戻します。
     */
    public int getNtpSid() {
        return ntpSid__;
    }
    /**
     * @param ntpSid 設定する ntpSid。
     */
    public void setNtpSid(int ntpSid) {
        ntpSid__ = ntpSid;
    }
    /**
     * @return time を戻します。
     */
    public String getTime() {
        return time__;
    }
    /**
     * @param time 設定する time。
     */
    public void setTime(String time) {
        time__ = time;
    }
    /**
     * @return title を戻します。
     */
    public String getTitle() {
        return title__;
    }
    /**
     * @param title 設定する title。
     */
    public void setTitle(String title) {
        title__ = title;
    }
//    /**
//     * @return nkk_chkkbn を戻します。
//     */
//    public int getNkk_chkkbn() {
//        return nkk_chkkbn__;
//    }
//    /**
//     * @param nkk_chkkbn 設定する nkkchkkbn。
//     */
//    public void setNkk_chkkbn(int nkkchkkbn) {
//        nkk_chkkbn__ = nkkchkkbn;
//    }
//    /**
//     * @return nkk_allcnt を戻します。
//     */
//    public int getNkk_allcnt() {
//        return nkk_allcnt__;
//    }
//    /**
//     * @param nkk_allcnt 設定する nkkallcnt。
//     */
//    public void setNkk_allcnt(int nkkallcnt) {
//        nkk_allcnt__ = nkkallcnt;
//    }
//    /**
//     * @return nkk_chkcnt を戻します。
//     */
//    public int getNkk_chkcnt() {
//        return nkk_chkcnt__;
//    }
//    /**
//     * @param nkk_chkcnt 設定する nkkchkcnt
//     */
//    public void setNkk_chkcnt(int nkkchkcnt) {
//        nkk_chkcnt__ = nkkchkcnt;
//    }
    /**
     * <p>ntpDateStr を取得します。
     * @return ntpDateStr
     */
    public String getNtpDateStr() {
        return ntpDateStr__;
    }
    /**
     * <p>ntpDateStr をセットします。
     * @param ntpDateStr ntpDateStr
     */
    public void setNtpDateStr(String ntpDateStr) {
        ntpDateStr__ = ntpDateStr;
    }
    /**
     * <p>fromTimeStr を取得します。
     * @return fromTimeStr
     */
    public String getFromTimeStr() {
        return fromTimeStr__;
    }
    /**
     * <p>fromTimeStr をセットします。
     * @param fromTimeStr fromTimeStr
     */
    public void setFromTimeStr(String fromTimeStr) {
        fromTimeStr__ = fromTimeStr;
    }
    /**
     * <p>toTiemStr を取得します。
     * @return toTiemStr
     */
    public String getToTiemStr() {
        return toTiemStr__;
    }
    /**
     * <p>toTiemStr をセットします。
     * @param toTiemStr toTiemStr
     */
    public void setToTiemStr(String toTiemStr) {
        toTiemStr__ = toTiemStr;
    }
    /**
     * <p>ankenName を取得します。
     * @return ankenName
     */
    public String getAnkenName() {
        return ankenName__;
    }
    /**
     * <p>ankenName をセットします。
     * @param ankenName ankenName
     */
    public void setAnkenName(String ankenName) {
        ankenName__ = ankenName;
    }
    /**
     * <p>ankenSid を取得します。
     * @return ankenSid
     */
    public int getAnkenSid() {
        return ankenSid__;
    }
    /**
     * <p>ankenSid をセットします。
     * @param ankenSid ankenSid
     */
    public void setAnkenSid(int ankenSid) {
        ankenSid__ = ankenSid;
    }
    /**
     * <p>ntpDspDateStr を取得します。
     * @return ntpDspDateStr
     */
    public String getNtpDspDateStr() {
        return ntpDspDateStr__;
    }
    /**
     * <p>ntpDspDateStr をセットします。
     * @param ntpDspDateStr ntpDspDateStr
     */
    public void setNtpDspDateStr(String ntpDspDateStr) {
        ntpDspDateStr__ = ntpDspDateStr;
    }

    /**
     * <p>ntp_cmtCnt を取得します。
     * @return ntp_cmtCnt
     */
    public int getNtp_cmtCnt() {
        return ntp_cmtCnt__;
    }
    /**
     * <p>ntp_cmtCnt をセットします。
     * @param ntpCmtCnt ntp_cmtCnt
     */
    public void setNtp_cmtCnt(int ntpCmtCnt) {
        ntp_cmtCnt__ = ntpCmtCnt;
    }
    /**
     * <p>ntp_cmtkbn を取得します。
     * @return ntp_cmtkbn
     */
    public int getNtp_cmtkbn() {
        return ntp_cmtkbn__;
    }
    /**
     * <p>ntp_cmtkbn をセットします。
     * @param ntpCmtkbn ntp_cmtkbn
     */
    public void setNtp_cmtkbn(int ntpCmtkbn) {
        ntp_cmtkbn__ = ntpCmtkbn;
    }
    /**
     * <p>ntp_goodkbn を取得します。
     * @return ntp_goodkbn
     */
    public int getNtp_goodkbn() {
        return ntp_goodkbn__;
    }
    /**
     * <p>ntp_goodkbn をセットします。
     * @param ntpGoodkbn ntp_goodkbn
     */
    public void setNtp_goodkbn(int ntpGoodkbn) {
        ntp_goodkbn__ = ntpGoodkbn;
    }
    /**
     * <p>ntp_goodCnt を取得します。
     * @return ntp_goodCnt
     */
    public int getNtp_goodCnt() {
        return ntp_goodCnt__;
    }
    /**
     * <p>ntp_goodCnt をセットします。
     * @param ntpGoodCnt ntp_goodCnt
     */
    public void setNtp_goodCnt(int ntpGoodCnt) {
        ntp_goodCnt__ = ntpGoodCnt;
    }
    /**
     * <p>ntp_chkKbn を取得します。
     * @return ntp_chkKbn
     */
    public int getNtp_chkKbn() {
        return ntp_chkKbn__;
    }
    /**
     * <p>ntp_chkKbn をセットします。
     * @param ntpChkKbn ntp_chkKbn
     */
    public void setNtp_chkKbn(int ntpChkKbn) {
        ntp_chkKbn__ = ntpChkKbn;
    }
    /**
     * <p>action を取得します。
     * @return action
     */
    public String getAction() {
        return action__;
    }
    /**
     * <p>action をセットします。
     * @param action action
     */
    public void setAction(String action) {
        action__ = action;
    }
    /**
     * <p>userUkoFlg を取得します。
     * @return userUkoFlg
     */
    public int getUserUkoFlg() {
        return userUkoFlg__;
    }
    /**
     * <p>userUkoFlg をセットします。
     * @param userUkoFlg userUkoFlg
     */
    public void setUserUkoFlg(int userUkoFlg) {
        userUkoFlg__ = userUkoFlg;
    }
    /**
     * <p>ankenViewable を取得します。
     * @return ankenViewable
     */
    public boolean isAnkenViewable() {
        return ankenViewable__;
    }
    /**
     * <p>ankenViewable をセットします。
     * @param ankenViewable ankenViewable
     */
    public void setAnkenViewable(boolean ankenViewable) {
        ankenViewable__ = ankenViewable;
    }

}
