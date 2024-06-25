package jp.groupsession.v2.tcd.tcd010;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;

/**
 * <br>[機  能] タイムカード 一覧画面 タイムカード一覧の１日分の情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd010Model extends TcdTcdataModel {

    /** 年月日(YYMMDD) */
    private String tcd010Ymd__;
    /** 年 */
    private int tcd010Year__;
    /** 月 */
    private int tcd010Month__;
    /** 日 */
    private int tcd010Day__;
    /** 曜日 */
    private int tcd010Week__;
    /** 曜日文字列 */
    private String tcd010WeekStr__;
    /** 始業時間 */
    private String tcd010ShigyouStr__;
    /** 終業時間 */
    private String tcd010SyugyouStr__;
    /** 打刻始業時間 */
    private String tcd010StrikeShigyouStr__;
    /** 打刻終業時間 */
    private String tcd010StrikeSyugyouStr__;
    /** 時間帯名称 */
    private String tcd010TimezoneName__;
    /** 備考 */
    private String tcd010Bikou__;
    /** 休日区分 */
    private int tcd010Kubun__ = 0;
    /** 休日区分名称 */
    private String tcd010Kyujitsu__;
    //休日情報
    /** 休日区分 */
    private int holKbn__ = 0;
    /** 休日名称 */
    private String holName__;

    /** 不正データフラグ */
    private int failFlg__ = 0;
    /** データロックフラグ */
    private int lockFlg__ = 0;

    /** 打刻ボタン表示区分(始業) 0:表示しない 1:表示する */
    private int dakokuBtnStrKbn__ = GSConstTimecard.DAKOKUBTN_DSP_NOT;
    /** 打刻ボタン表示区分(終業) 0:表示しない 1:表示する */
    private int dakokuBtnEndKbn__ = GSConstTimecard.DAKOKUBTN_DSP_NOT;

    /**
     * <p>dakokuBtnEndKbn を取得します。
     * @return dakokuBtnEndKbn
     */
    public int getDakokuBtnEndKbn() {
        return dakokuBtnEndKbn__;
    }

    /**
     * <p>dakokuBtnEndKbn をセットします。
     * @param dakokuBtnEndKbn dakokuBtnEndKbn
     */
    public void setDakokuBtnEndKbn(int dakokuBtnEndKbn) {
        dakokuBtnEndKbn__ = dakokuBtnEndKbn;
    }

    /**
     * <p>dakokuBtnStrKbn を取得します。
     * @return dakokuBtnStrKbn
     */
    public int getDakokuBtnStrKbn() {
        return dakokuBtnStrKbn__;
    }

    /**
     * <p>dakokuBtnStrKbn をセットします。
     * @param dakokuBtnStrKbn dakokuBtnStrKbn
     */
    public void setDakokuBtnStrKbn(int dakokuBtnStrKbn) {
        dakokuBtnStrKbn__ = dakokuBtnStrKbn;
    }

    /**
     * <p>lockFlg を取得します。
     * @return lockFlg
     */
    public int getLockFlg() {
        return lockFlg__;
    }

    /**
     * <p>lockFlg をセットします。
     * @param lockFlg lockFlg
     */
    public void setLockFlg(int lockFlg) {
        lockFlg__ = lockFlg;
    }

    /**
     * <p>failFlg を取得します。
     * @return failFlg
     */
    public int getFailFlg() {
        return failFlg__;
    }

    /**
     * <p>failFlg をセットします。
     * @param failFlg failFlg
     */
    public void setFailFlg(int failFlg) {
        failFlg__ = failFlg;
    }

    /**
     * <p>tcd010Kubun を取得します。
     * @return tcd010Kubun
     */
    public int getTcd010Kubun() {
        return tcd010Kubun__;
    }

    /**
     * <p>tcd010Kubun をセットします。
     * @param tcd010Kubun tcd010Kubun
     */
    public void setTcd010Kubun(int tcd010Kubun) {
        tcd010Kubun__ = tcd010Kubun;
    }

    /**
     * <p>tcd010Bikou を取得します。
     * @return tcd010Bikou
     */
    public String getTcd010Bikou() {
        return tcd010Bikou__;
    }

    /**
     * <p>tcd010Bikou をセットします。
     * @param tcd010Bikou tcd010Bikou
     */
    public void setTcd010Bikou(String tcd010Bikou) {
        tcd010Bikou__ = tcd010Bikou;
    }

    /**
     * <p>tcd010Day を取得します。
     * @return tcd010Day
     */
    public int getTcd010Day() {
        return tcd010Day__;
    }

    /**
     * <p>tcd010Day をセットします。
     * @param tcd010Day tcd010Day
     */
    public void setTcd010Day(int tcd010Day) {
        tcd010Day__ = tcd010Day;
    }

    /**
     * <p>tcd010Kyujitsu を取得します。
     * @return tcd010Kyujitsu
     */
    public String getTcd010Kyujitsu() {
        return tcd010Kyujitsu__;
    }

    /**
     * <p>tcd010Kyujitsu をセットします。
     * @param tcd010Kyujitsu tcd010Kyujitsu
     */
    public void setTcd010Kyujitsu(String tcd010Kyujitsu) {
        tcd010Kyujitsu__ = tcd010Kyujitsu;
    }

    /**
     * <p>tcd010Month を取得します。
     * @return tcd010Month
     */
    public int getTcd010Month() {
        return tcd010Month__;
    }

    /**
     * <p>tcd010Month をセットします。
     * @param tcd010Month tcd010Month
     */
    public void setTcd010Month(int tcd010Month) {
        tcd010Month__ = tcd010Month;
    }

    /**
     * <p>tcd010ShigyouStr を取得します。
     * @return tcd010ShigyouStr
     */
    public String getTcd010ShigyouStr() {
        return tcd010ShigyouStr__;
    }

    /**
     * <p>tcd010ShigyouStr をセットします。
     * @param tcd010ShigyouStr tcd010ShigyouStr
     */
    public void setTcd010ShigyouStr(String tcd010ShigyouStr) {
        tcd010ShigyouStr__ = tcd010ShigyouStr;
    }

    /**
     * <p>tcd010SyugyouStr を取得します。
     * @return tcd010SyugyouStr
     */
    public String getTcd010SyugyouStr() {
        return tcd010SyugyouStr__;
    }

    /**
     * <p>tcd010SyugyouStr をセットします。
     * @param tcd010SyugyouStr tcd010SyugyouStr
     */
    public void setTcd010SyugyouStr(String tcd010SyugyouStr) {
        tcd010SyugyouStr__ = tcd010SyugyouStr;
    }

    /**
     * <p>tcd010Week を取得します。
     * @return tcd010Week
     */
    public int getTcd010Week() {
        return tcd010Week__;
    }

    /**
     * <p>tcd010Week をセットします。
     * @param tcd010Week tcd010Week
     */
    public void setTcd010Week(int tcd010Week) {
        tcd010Week__ = tcd010Week;
    }

    /**
     * <p>tcd010WeekStr を取得します。
     * @return tcd010WeekStr
     */
    public String getTcd010WeekStr() {
        return tcd010WeekStr__;
    }

    /**
     * <p>tcd010WeekStr をセットします。
     * @param tcd010WeekStr tcd010WeekStr
     */
    public void setTcd010WeekStr(String tcd010WeekStr) {
        tcd010WeekStr__ = tcd010WeekStr;
    }

    /**
     * <p>tcd010Year を取得します。
     * @return tcd010Year
     */
    public int getTcd010Year() {
        return tcd010Year__;
    }

    /**
     * <p>tcd010Year をセットします。
     * @param tcd010Year tcd010Year
     */
    public void setTcd010Year(int tcd010Year) {
        tcd010Year__ = tcd010Year;
    }

    /**
     * <p>tcd010Ymd を取得します。
     * @return tcd010Ymd
     */
    public String getTcd010Ymd() {
        return tcd010Ymd__;
    }

    /**
     * <p>tcd010Ymd をセットします。
     * @param tcd010Ymd tcd010Ymd
     */
    public void setTcd010Ymd(String tcd010Ymd) {
        tcd010Ymd__ = tcd010Ymd;
    }

    /**
     * <p>holName を取得します。
     * @return holName
     */
    public String getHolName() {
        return holName__;
    }

    /**
     * <p>holName をセットします。
     * @param holName holName
     */
    public void setHolName(String holName) {
        holName__ = holName;
    }

    /**
     * <p>holKbn を取得します。
     * @return holKbn
     */
    public int getHolKbn() {
        return holKbn__;
    }

    /**
     * <p>holKbn をセットします。
     * @param holKbn holKbn
     */
    public void setHolKbn(int holKbn) {
        holKbn__ = holKbn;
    }

    /**
     * <p>この日が土曜かどうかを返す</p>
     * @return 土曜:true 土曜ではない:false
     */
    public boolean isSATURDAY() {
        if (UDate.SATURDAY == getTcdDate().getWeek()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>この日が日曜かどうかを返す</p>
     * @return 日曜:true 日曜ではない:false
     */
    public boolean isSUNDAY() {
        if (UDate.SATURDAY == getTcdDate().getWeek()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>tcd010StrikeShigyouStr を取得します。
     * @return tcd010StrikeShigyouStr
     */
    public String getTcd010StrikeShigyouStr() {
        return tcd010StrikeShigyouStr__;
    }

    /**
     * <p>tcd010StrikeShigyouStr をセットします。
     * @param tcd010StrikeShigyouStr tcd010StrikeShigyouStr
     */
    public void setTcd010StrikeShigyouStr(String tcd010StrikeShigyouStr) {
        tcd010StrikeShigyouStr__ = tcd010StrikeShigyouStr;
    }

    /**
     * <p>tcd010StrikeSyugyouStr を取得します。
     * @return tcd010StrikeSyugyouStr
     */
    public String getTcd010StrikeSyugyouStr() {
        return tcd010StrikeSyugyouStr__;
    }

    /**
     * <p>tcd010StrikeSyugyouStr をセットします。
     * @param tcd010StrikeSyugyouStr tcd010StrikeSyugyouStr
     */
    public void setTcd010StrikeSyugyouStr(String tcd010StrikeSyugyouStr) {
        tcd010StrikeSyugyouStr__ = tcd010StrikeSyugyouStr;
    }
    /**
     * <p>tcd010TimezoneName を取得します。
     * @return tcd010TimezoneName
     * @see jp.groupsession.v2.tcd.tcd010.Tcd010Model#tcd010TimezoneName__
     */
    public String getTcd010TimezoneName() {
        return tcd010TimezoneName__;
    }

    /**
     * <p>tcd010TimezoneName をセットします。
     * @param tcd010TimezoneName tcd010TimezoneName
     * @see jp.groupsession.v2.tcd.tcd010.Tcd010Model#tcd010TimezoneName__
     */
    public void setTcd010TimezoneName(String tcd010TimezoneName) {
        tcd010TimezoneName__ = tcd010TimezoneName;
    }


//  /**
//  * <p>この日が休日かどうかを返す</p>
//  * <p>休日情報テーブルを元に返すので、日曜=休日ではない</p>
//  * @return 休日:true 休日ではない:false
//  */
// public boolean isHoliday() {
//     if (getHolKbn() != 0) {
//         return false;
//     } else {
//         return true;
//     }
// }
}
