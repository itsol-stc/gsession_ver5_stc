package jp.groupsession.v2.sch.sch200;

/**
 * <br>[機  能] スケジュール 個人週間JSONデータ定義を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200Event {
    /** id */
    private int id__ = 0;
    /** ﾀｲﾄﾙ */
    private String title__ = null;
    /** 開始時刻 */
    private String start__ = null;
    /** 終了時刻 */
    private String end__ = null;
    /** 全日*/
    private boolean allDay__ = false;
    /** url */
    private String url__ = null;
    /** 移動フラグ */
    private boolean editable__ = true;
    /** textColor */
    private String textColor__ = null;
    /** backgroundColor */
    private String backgroundColor__ = null;
    /** selectDate */
    private String ymd__ = null;
    /** selectUsrSid */
    private String uid__ = null;
    /** selectUsrSid */
    private String ukbn__ = null;
    /** 施設予約フラグ true:あり false:なし */
    private boolean rsvFlg__ = false;
    /** 同時登録ユーザフラグ true:あり false:なし */
    private boolean svUsrFlg__ = false;
    /** 公開アイコン表示フラグ */
    private boolean publicIconFlg__ = false;
    /** css class名*/
    private String[] className__ = {};
    /**
     * <p>allDay を取得します。
     * @return allDay
     */
    public boolean isAllDay() {
        return allDay__;
    }
    /**
     * <p>allDay をセットします。
     * @param allDay allDay
     */
    public void setAllDay(boolean allDay) {
        allDay__ = allDay;
    }
    /**
     * <p>editable を取得します。
     * @return editable
     */
    public boolean isEditable() {
        return editable__;
    }
    /**
     * <p>editable をセットします。
     * @param editable editable
     */
    public void setEditable(boolean editable) {
        editable__ = editable;
    }
    /**
     * <p>end を取得します。
     * @return end
     */
    public String getEnd() {
        return end__;
    }
    /**
     * <p>end をセットします。
     * @param end end
     */
    public void setEnd(String end) {
        end__ = end;
    }
    /**
     * <p>id を取得します。
     * @return id
     */
    public int getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(int id) {
        id__ = id;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public String getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(String start) {
        start__ = start;
    }
    /**
     * <p>textColor を取得します。
     * @return textColor
     */
    public String getTextColor() {
        return textColor__;
    }
    /**
     * <p>textColor をセットします。
     * @param textColor textColor
     */
    public void setTextColor(String textColor) {
        textColor__ = textColor;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }
    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * <p>uid を取得します。
     * @return uid
     */
    public String getUid() {
        return uid__;
    }
    /**
     * <p>uid をセットします。
     * @param uid uid
     */
    public void setUid(String uid) {
        uid__ = uid;
    }
    /**
     * <p>ukbn を取得します。
     * @return ukbn
     */
    public String getUkbn() {
        return ukbn__;
    }
    /**
     * <p>ukbn をセットします。
     * @param ukbn ukbn
     */
    public void setUkbn(String ukbn) {
        ukbn__ = ukbn;
    }
    /**
     * <p>ymd を取得します。
     * @return ymd
     */
    public String getYmd() {
        return ymd__;
    }
    /**
     * <p>ymd をセットします。
     * @param ymd ymd
     */
    public void setYmd(String ymd) {
        ymd__ = ymd;
    }
    /**
     * <p>rsvFlg を取得します。
     * @return rsvFlg
     */
    public boolean isRsvFlg() {
        return rsvFlg__;
    }
    /**
     * <p>rsvFlg をセットします。
     * @param rsvFlg rsvFlg
     */
    public void setRsvFlg(boolean rsvFlg) {
        rsvFlg__ = rsvFlg;
    }
    /**
     * <p>svUsrFlg を取得します。
     * @return svUsrFlg
     */
    public boolean isSvUsrFlg() {
        return svUsrFlg__;
    }
    /**
     * <p>svUsrFlg をセットします。
     * @param svUsrFlg svUsrFlg
     */
    public void setSvUsrFlg(boolean svUsrFlg) {
        svUsrFlg__ = svUsrFlg;
    }
    /**
     * <p>backgroundColor を取得します。
     * @return backgroundColor
     */
    public String getBackgroundColor() {
        return backgroundColor__;
    }
    /**
     * <p>backgroundColor をセットします。
     * @param backgroundColor backgroundColor
     */
    public void setBackgroundColor(String backgroundColor) {
        backgroundColor__ = backgroundColor;
    }
    /**
     * <p>className を取得します。
     * @return className
     * @see jp.groupsession.v2.sch.sch200.Sch200Event#className__
     */
    public String[] getClassName() {
        return className__;
    }
    /**
     * <p>className をセットします。
     * @param className className
     * @see jp.groupsession.v2.sch.sch200.Sch200Event#className__
     */
    public void setClassName(String[] className) {
        className__ = className;
    }
    /**
     * <p>publicIconFlg を取得します。
     * @return publicIconFlg
     * @see jp.groupsession.v2.sch.sch200.Sch200Event#publicIconFlg
     */
    public boolean isPublicIconFlg() {
        return publicIconFlg__;
    }
    /**
     * <p>publicIconFlg をセットします。
     * @param publicIconFlg publicIconFlg
     * @see jp.groupsession.v2.sch.sch200.Sch200Event#publicIconFlg
     */
    public void setPublicIconFlg(boolean publicIconFlg) {
        publicIconFlg__ = publicIconFlg;
    }
}
