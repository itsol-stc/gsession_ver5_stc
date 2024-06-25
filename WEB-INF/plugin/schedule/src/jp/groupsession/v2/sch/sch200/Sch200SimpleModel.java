package jp.groupsession.v2.sch.sch200;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュール情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200SimpleModel extends AbstractModel {

    /** スケジュールSID */
    private int schSid__;
    /** スケジュール時間文字列 */
    private String time__;
    /** スケジュールタイトル文字列 */
    private String title__;
    /** スケジュール公開区分 */
    private int public__;

    //日間表示で使用する項目
    /** 背景色 */
    private int bgColor__;
    /** スケジュール開始日時*/
    private UDate fromDate__;
    /** スケジュール終了日時*/
    private UDate toDate__;
    /** スケジュール時間指定区分 */
    private int timeKbn__;
    //一覧にて使用する項目
    /** 開始日時文字列*/
    private String fromDateStr__;
    /** 終了日時文字列*/
    private String toDateStr__;
    /** 内容 */
    private String valueStr__;
    /** 被登録者SID */
    private String userSid__;
    /** 被登録者氏名 */
    private String userName__;
    /** 被登録者区分 */
    private String userKbn__;
    /** グループスケジュール編集区分 */
    private int grpEdKbn__;
    /** 個人スケジュール編集区分 */
    private int kjnEdKbn__;
    /** 公開アイコン表示フラグ */
    private boolean publicIconFlg__ = false;

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
     * <p>valueStr を取得します。
     * @return valueStr
     */
    public String getValueStr() {
        return valueStr__;
    }
    /**
     * <p>valueStr をセットします。
     * @param valueStr valueStr
     */
    public void setValueStr(String valueStr) {
        valueStr__ = valueStr;
    }
    /**
     * <p>fromDateStr を取得します。
     * @return fromDateStr
     */
    public String getFromDateStr() {
        return fromDateStr__;
    }
    /**
     * <p>fromDateStr をセットします。
     * @param fromDateStr fromDateStr
     */
    public void setFromDateStr(String fromDateStr) {
        fromDateStr__ = fromDateStr;
    }
    /**
     * <p>toDateStr を取得します。
     * @return toDateStr
     */
    public String getToDateStr() {
        return toDateStr__;
    }
    /**
     * <p>toDateStr をセットします。
     * @param toDateStr toDateStr
     */
    public void setToDateStr(String toDateStr) {
        toDateStr__ = toDateStr;
    }
    /**
     * @return timeKbn を戻します。
     */
    public int getTimeKbn() {
        return timeKbn__;
    }
    /**
     * @param timeKbn 設定する timeKbn。
     */
    public void setTimeKbn(int timeKbn) {
        timeKbn__ = timeKbn;
    }
    /**
     * @return bgColor を戻します。
     */
    public int getBgColor() {
        return bgColor__;
    }
    /**
     * @param bgColor 設定する bgColor。
     */
    public void setBgColor(int bgColor) {
        bgColor__ = bgColor;
    }
    /**
     * @return fromDate を戻します。
     */
    public UDate getFromDate() {
        return fromDate__;
    }
    /**
     * @param fromDate 設定する fromDate。
     */
    public void setFromDate(UDate fromDate) {
        fromDate__ = fromDate;
    }
    /**
     * @return toDate を戻します。
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * @param toDate 設定する toDate。
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }
    /**
     * @return public を戻します。
     */
    public int getPublic() {
        return public__;
    }
    /**
     * @param public1 設定する public。
     */
    public void setPublic(int public1) {
        public__ = public1;
    }
    /**
     * @return schSid を戻します。
     */
    public int getSchSid() {
        return schSid__;
    }
    /**
     * @param schSid 設定する schSid。
     */
    public void setSchSid(int schSid) {
        schSid__ = schSid;
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
    /**
     * @return grpEdKbn
     */
    public int getGrpEdKbn() {
        return grpEdKbn__;
    }
    /**
     * @param grpEdKbn 設定する grpEdKbn
     */
    public void setGrpEdKbn(int grpEdKbn) {
        grpEdKbn__ = grpEdKbn;
    }
    /**
     * @return kjnEdKbn
     */
    public int getKjnEdKbn() {
        return kjnEdKbn__;
    }
    /**
     * @param kjnEdKbn 設定する kjnEdKbn
     */
    public void setKjnEdKbn(int kjnEdKbn) {
        kjnEdKbn__ = kjnEdKbn;
    }
    /**
     * <p>publicIconFlg を取得します。
     * @return publicIconFlg
     * @see jp.groupsession.v2.sch.sch200.Sch200SimpleModel#publicIconFlg
     */
    public boolean isPublicIconFlg() {
        return publicIconFlg__;
    }
    /**
     * <p>publicIconFlg をセットします。
     * @param publicIconFlg publicIconFlg
     * @see jp.groupsession.v2.sch.sch200.Sch200SimpleModel#publicIconFlg
     */
    public void setPublicIconFlg(boolean publicIconFlg) {
        publicIconFlg__ = publicIconFlg;
    }
}
