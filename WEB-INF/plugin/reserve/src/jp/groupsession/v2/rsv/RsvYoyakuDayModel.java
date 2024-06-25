package jp.groupsession.v2.rsv;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 予約情報(1日)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvYoyakuDayModel extends AbstractModel {

    /** 日付(D) */
    private String yrkDateDay__ = null;
    /** 日付(YMD) */
    private String yrkDateStr__ = null;
    /** 曜日 */
    private int yrkYobi__;
    /** 六曜 */
    private String yrkRokuyou__ = null;
    /** 休日名称 */
    private String holName__ = null;
    /** 当月区分 */
    private int yrkMonthKbn__;
    /** 一括登録用キー */
    private String ikkatuKey__ = null;
    /** チェック済フラグ(※日間表示時使用) */
    private boolean checkedFlg__ = false;
    /** 予約情報 */
    private ArrayList<RsvYoyakuModel> yoyakuList__ = null;
    /** 当日区分 */
    private String todayKbn__ = null;
    /** 施設アクセス区分 */
    private int racAuth__;

    /**
     * <p>todayKbn__ を取得します。
     * @return todayKbn
     */
    public String getTodayKbn() {
        return todayKbn__;
    }
    /**
     * <p>todayKbn__ をセットします。
     * @param todayKbn todayKbn__
     */
    public void setTodayKbn(String todayKbn) {
        todayKbn__ = todayKbn;
    }
    /**
     * <p>checkedFlg__ を取得します。
     * @return checkedFlg
     */
    public boolean isCheckedFlg() {
        return checkedFlg__;
    }
    /**
     * <p>checkedFlg__ をセットします。
     * @param checkedFlg checkedFlg__
     */
    public void setCheckedFlg(boolean checkedFlg) {
        checkedFlg__ = checkedFlg;
    }
    /**
     * <p>yrkDateStr__ を取得します。
     * @return yrkDateStr
     */
    public String getYrkDateStr() {
        return yrkDateStr__;
    }
    /**
     * <p>yrkDateStr__ をセットします。
     * @param yrkDateStr yrkDateStr__
     */
    public void setYrkDateStr(String yrkDateStr) {
        yrkDateStr__ = yrkDateStr;
    }
    /**
     * <p>holName__ を取得します。
     * @return holName
     */
    public String getHolName() {
        return holName__;
    }
    /**
     * <p>holName__ をセットします。
     * @param holName holName__
     */
    public void setHolName(String holName) {
        holName__ = holName;
    }
    /**
     * <p>yoyakuList__ を取得します。
     * @return yoyakuList
     */
    public ArrayList<RsvYoyakuModel> getYoyakuList() {
        return yoyakuList__;
    }
    /**
     * <p>yoyakuList__ をセットします。
     * @param yoyakuList yoyakuList__
     */
    public void setYoyakuList(ArrayList<RsvYoyakuModel> yoyakuList) {
        yoyakuList__ = yoyakuList;
    }
    /**
     * <p>yrkYobi__ を取得します。
     * @return yrkYobi
     */
    public int getYrkYobi() {
        return yrkYobi__;
    }
    /**
     * <p>yrkYobi__ をセットします。
     * @param yrkYobi yrkYobi__
     */
    public void setYrkYobi(int yrkYobi) {
        yrkYobi__ = yrkYobi;
    }
    /**
     * <p>yrkRokuyou を取得します。
     * @return yrkRokuyou
     * @see jp.groupsession.v2.rsv.RsvYoyakuDayModel#yrkRokuyou__
     */
    public String getYrkRokuyou() {
        return yrkRokuyou__;
    }
    /**
     * <p>yrkRokuyou をセットします。
     * @param yrkRokuyou yrkRokuyou
     * @see jp.groupsession.v2.rsv.RsvYoyakuDayModel#yrkRokuyou__
     */
    public void setYrkRokuyou(String yrkRokuyou) {
        yrkRokuyou__ = yrkRokuyou;
    }
    /**
     * <p>yrkMonthKbn__ を取得します。
     * @return yrkMonthKbn
     */
    public int getYrkMonthKbn() {
        return yrkMonthKbn__;
    }
    /**
     * <p>yrkMonthKbn__ をセットします。
     * @param yrkMonthKbn yrkMonthKbn__
     */
    public void setYrkMonthKbn(int yrkMonthKbn) {
        yrkMonthKbn__ = yrkMonthKbn;
    }
    /**
     * <p>yrkDateDay__ を取得します。
     * @return yrkDateDay
     */
    public String getYrkDateDay() {
        return yrkDateDay__;
    }
    /**
     * <p>yrkDateDay__ をセットします。
     * @param yrkDateDay yrkDateDay__
     */
    public void setYrkDateDay(String yrkDateDay) {
        yrkDateDay__ = yrkDateDay;
    }
    /**
     * <p>ikkatuKey__ を取得します。
     * @return ikkatuKey
     */
    public String getIkkatuKey() {
        return ikkatuKey__;
    }
    /**
     * <p>ikkatuKey__ をセットします。
     * @param ikkatuKey ikkatuKey__
     */
    public void setIkkatuKey(String ikkatuKey) {
        ikkatuKey__ = ikkatuKey;
    }
    /**
     * <p>racAuth を取得します。
     * @return racAuth
     */
    public int getRacAuth() {
        return racAuth__;
    }
    /**
     * <p>racAuth をセットします。
     * @param racAuth racAuth
     */
    public void setRacAuth(int racAuth) {
        racAuth__ = racAuth;
    }
}