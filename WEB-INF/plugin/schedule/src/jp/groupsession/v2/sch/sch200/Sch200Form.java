package jp.groupsession.v2.sch.sch200;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch040.Sch040Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] スケジュール 個人週間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200Form extends Sch040Form {
    //表示内容
    /** ヘッダー表示用年月 */
    private String sch200StrDspDate__ = null;
    /** ヘッダーユーザ名称 */
    private String sch200StrUserName__ = null;
    /** スケジュールリスト */
    private ArrayList<Sch200WeekOfModel> sch200ScheduleList__ = null;
    /** 初期表示フラグ*/
    private int sch200InitFlg__ = 0;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> sch200UsrLabelList__ = null;
    /** 自動リロード時間 */
    private int sch200Reload__ = GSConstSchedule.AUTO_RELOAD_10MIN;
    /** 表示開始曜日 */
    private int sch200DspStartWeek__ = GSConstSchedule.CHANGE_WEEK_SUN;
    /** 表示開始日 */
    private String sch200FrDate__ = null;
    /** 表示終了日 */
    private String sch200ToDate__ = null;
    /** 表示年 */
    private int sch200Year__ = 0;
    /** 表示月 */
    private int sch200Month__ = 0;
    /** 表示日 */
    private int sch200Day__ = 0;
    /** 再読み込み時間 */
    private int sch020Reload__;
    /** 同時登録スケジュールへ反映有無 */
    private String sch200BatchRef__ = "1";
    /** 時間指定有無 0=有り 1=無し */
    private String sch200TimeKbn__ = String.valueOf(GSConstSchedule.TIME_EXIST);
    /** 同時登録施設予約へ反映有無 */
    private String sch200ResBatchRef__ = "1";
    /** 表示イベント */
    private JSONObject sch200JsonEvent__ = null;
    /** イベントタイトル */
    private String sch200Title__ = null;
    /** マイグループフラグ */
    private boolean sch200MyGroupFlg__ = false;
    /** JSONデータ */
    private ArrayList<String> sch200JsonList__ = null;
    /** 移動日数 */
    private int sch200DayDelta__ = 0;
    /** 移動時間 */
    private int sch200MinuteDelta__ = 0;
    /** 表示位置 */
    private int sch200EventPosition__ = GSConstSchedule.DSP_ALL_DAY;
    /** エラー */
    private ActionErrors sch200ActionErrors__ = null;
    /** エラー数 */
    private int sch200ActionErrorsCnt__ = 0;
    /** エラー行数 */
    private int sch200ErrorsRowCnt__ = 0;
    /** イベント区分 0:drop 1:resize*/
    private int sch200EventKbn__ = GSConstSchedule.EVENT_DROP;
    /** グループ変更フラグ 0:通常 1:変更*/
    private int sch200ChGroupFlg__ = 0;
    /** ドラッグアンドドロップ　リサイズイベント終了時 0:登録処理する 1:登録処理しない*/
    private int sch200Cancel__ = 0;
    /** 同時登録重複登録不可ユーザリスト 0:なし 1:あり*/
    private int sch200CantAddUserFlg__ = 0;
    /** 同時登録重複登録不可ユーザリスト 0:なし 1:あり*/
    private int sch200WarningAddUserFlg__ = 0;
    /**
     * <p>sch200CantAddUserFlg を取得します。
     * @return sch200CantAddUserFlg
     */
    public int getSch200CantAddUserFlg() {
        return sch200CantAddUserFlg__;
    }
    /**
     * <p>sch200CantAddUserFlg をセットします。
     * @param sch200CantAddUserFlg sch200CantAddUserFlg
     */
    public void setSch200CantAddUserFlg(int sch200CantAddUserFlg) {
        sch200CantAddUserFlg__ = sch200CantAddUserFlg;
    }
    /**
     * <p>sch200ActionErrors を取得します。
     * @return sch200ActionErrors
     */
    public ActionErrors getSch200ActionErrors() {
        return sch200ActionErrors__;
    }
    /**
     * <p>sch200ActionErrors をセットします。
     * @param sch200ActionErrors sch200ActionErrors
     */
    public void setSch200ActionErrors(ActionErrors sch200ActionErrors) {
        sch200ActionErrors__ = sch200ActionErrors;
    }
    /**
     * <p>sch200DayDelta を取得します。
     * @return sch200DayDelta
     */
    public int getSch200DayDelta() {
        return sch200DayDelta__;
    }
    /**
     * <p>sch200DayDelta をセットします。
     * @param sch200DayDelta sch200DayDelta
     */
    public void setSch200DayDelta(int sch200DayDelta) {
        sch200DayDelta__ = sch200DayDelta;
    }
    /**
     * <p>sch200MinuteDelta を取得します。
     * @return sch200MinuteDelta
     */
    public int getSch200MinuteDelta() {
        return sch200MinuteDelta__;
    }
    /**
     * <p>sch200MinuteDelta をセットします。
     * @param sch200MinuteDelta sch200MinuteDelta
     */
    public void setSch200MinuteDelta(int sch200MinuteDelta) {
        sch200MinuteDelta__ = sch200MinuteDelta;
    }
    /**
     * <p>sch200UsrLabelList を取得します。
     * @return sch200UsrLabelList
     */
    public List<UsrLabelValueBean> getSch200UsrLabelList() {
        return sch200UsrLabelList__;
    }
    /**
     * <p>sch200UsrLabelList をセットします。
     * @param sch200UsrLabelList sch200UsrLabelList
     */
    public void setSch200UsrLabelList(List<UsrLabelValueBean> sch200UsrLabelList) {
        sch200UsrLabelList__ = sch200UsrLabelList;
    }
    /**
     * @return sch200ScheduleList を戻します。
     */
    public ArrayList<Sch200WeekOfModel> getSch200ScheduleList() {
        return sch200ScheduleList__;
    }
    /**
     * @param sch200ScheduleList 設定する sch200ScheduleList。
     */
    public void setSch200ScheduleList(ArrayList<Sch200WeekOfModel> sch200ScheduleList) {
        sch200ScheduleList__ = sch200ScheduleList;
    }
    /**
     * @return sch200StrDspDate を戻します。
     */
    public String getSch200StrDspDate() {
        return sch200StrDspDate__;
    }
    /**
     * @param sch200StrDspDate 設定する sch200StrDspDate。
     */
    public void setSch200StrDspDate(String sch200StrDspDate) {
        sch200StrDspDate__ = sch200StrDspDate;
    }
    /**
     * @return sch200StrUserName を戻します。
     */
    public String getSch200StrUserName() {
        return sch200StrUserName__;
    }
    /**
     * @param sch200StrUserName 設定する sch200StrUserName。
     */
    public void setSch200StrUserName(String sch200StrUserName) {
        sch200StrUserName__ = sch200StrUserName;
    }
    /**
     * <p>sch200Reload を取得します。
     * @return sch200Reload
     */
    public int getSch200Reload() {
        return sch200Reload__;
    }
    /**
     * <p>sch200Reload をセットします。
     * @param sch200Reload sch200Reload
     */
    public void setSch200Reload(int sch200Reload) {
        sch200Reload__ = sch200Reload;
    }
    /**
     * @return sch200DspStartWeek
     */
    public int getSch200DspStartWeek() {
        return sch200DspStartWeek__;
    }
    /**
     * @param sch200DspStartWeek 設定する sch200DspStartWeek
     */
    public void setSch200DspStartWeek(int sch200DspStartWeek) {
        sch200DspStartWeek__ = sch200DspStartWeek;
    }
    /**
     * @return sch200MyGroupFlg
     */
    public boolean isSch200MyGroupFlg() {
        return sch200MyGroupFlg__;
    }
    /**
     * @param sch200MyGroupFlg 設定する sch200MyGroupFlg
     */
    public void setSch200MyGroupFlg(boolean sch200MyGroupFlg) {
        sch200MyGroupFlg__ = sch200MyGroupFlg;
    }
    /**
     * <p>sch200Day を取得します。
     * @return sch200Day
     */
    public int getSch200Day() {
        return sch200Day__;
    }
    /**
     * <p>sch200Day をセットします。
     * @param sch200Day sch200Day
     */
    public void setSch200Day(int sch200Day) {
        sch200Day__ = sch200Day;
    }
    /**
     * <p>sch200Month を取得します。
     * @return sch200Month
     */
    public int getSch200Month() {
        return sch200Month__;
    }
    /**
     * <p>sch200Month をセットします。
     * @param sch200Month sch200Month
     */
    public void setSch200Month(int sch200Month) {
        sch200Month__ = sch200Month;
    }
    /**
     * <p>sch200Year を取得します。
     * @return sch200Year
     */
    public int getSch200Year() {
        return sch200Year__;
    }
    /**
     * <p>sch200Year をセットします。
     * @param sch200Year sch200Year
     */
    public void setSch200Year(int sch200Year) {
        sch200Year__ = sch200Year;
    }
    /**
     * <p>sch200JsonList を取得します。
     * @return sch200JsonList
     */
    public ArrayList<String> getSch200JsonList() {
        return sch200JsonList__;
    }
    /**
     * <p>sch200JsonList をセットします。
     * @param sch200JsonList sch200JsonList
     */
    public void setSch200JsonList(ArrayList<String> sch200JsonList) {
        sch200JsonList__ = sch200JsonList;
    }
    /**
     * <p>sch200JsonEvent を取得します。
     * @return sch200JsonEvent
     */
    public JSONObject getSch200JsonEvent() {
        return sch200JsonEvent__;
    }
    /**
     * <p>sch200JsonEvent をセットします。
     * @param sch200JsonEvent sch200JsonEvent
     */
    public void setSch200JsonEvent(JSONObject sch200JsonEvent) {
        sch200JsonEvent__ = sch200JsonEvent;
    }
    /**
     * <p>sch020Reload を取得します。
     * @return sch020Reload
     */
    public int getSch020Reload() {
        return sch020Reload__;
    }
    /**
     * <p>sch020Reload をセットします。
     * @param sch020Reload sch020Reload
     */
    public void setSch020Reload(int sch020Reload) {
        sch020Reload__ = sch020Reload;
    }
    /**
     * <p>sch200EventPosition を取得します。
     * @return sch200EventPosition
     */
    public int getSch200EventPosition() {
        return sch200EventPosition__;
    }
    /**
     * <p>sch200EventPosition をセットします。
     * @param sch200EventPosition sch200EventPosition
     */
    public void setSch200EventPosition(int sch200EventPosition) {
        sch200EventPosition__ = sch200EventPosition;
    }
    /**
     * <p>sch200BatchRef を取得します。
     * @return sch200BatchRef
     */
    public String getSch200BatchRef() {
        return sch200BatchRef__;
    }
    /**
     * <p>sch200BatchRef をセットします。
     * @param sch200BatchRef sch200BatchRef
     */
    public void setSch200BatchRef(String sch200BatchRef) {
        sch200BatchRef__ = sch200BatchRef;
    }
    /**
     * <p>sch200TimeKbn を取得します。
     * @return sch200TimeKbn
     */
    public String getSch200TimeKbn() {
        return sch200TimeKbn__;
    }
    /**
     * <p>sch200TimeKbn をセットします。
     * @param sch200TimeKbn sch200TimeKbn
     */
    public void setSch200TimeKbn(String sch200TimeKbn) {
        sch200TimeKbn__ = sch200TimeKbn;
    }
    /**
     * <p>sch200ResBatchRef を取得します。
     * @return sch200ResBatchRef
     */
    public String getSch200ResBatchRef() {
        return sch200ResBatchRef__;
    }
    /**
     * <p>sch200ResBatchRef をセットします。
     * @param sch200ResBatchRef sch200ResBatchRef
     */
    public void setSch200ResBatchRef(String sch200ResBatchRef) {
        sch200ResBatchRef__ = sch200ResBatchRef;
    }
    /**
     * <p>sch200FrDate を取得します。
     * @return sch200FrDate
     */
    public String getSch200FrDate() {
        return sch200FrDate__;
    }
    /**
     * <p>sch200FrDate をセットします。
     * @param sch200FrDate sch200FrDate
     */
    public void setSch200FrDate(String sch200FrDate) {
        sch200FrDate__ = sch200FrDate;
    }
    /**
     * <p>sch200ToDate を取得します。
     * @return sch200ToDate
     */
    public String getSch200ToDate() {
        return sch200ToDate__;
    }
    /**
     * <p>sch200ToDate をセットします。
     * @param sch200ToDate sch200ToDate
     */
    public void setSch200ToDate(String sch200ToDate) {
        sch200ToDate__ = sch200ToDate;
    }
    /**
     * <p>sch200Title を取得します。
     * @return sch200Title
     */
    public String getSch200Title() {
        return sch200Title__;
    }
    /**
     * <p>sch200Title をセットします。
     * @param sch200Title sch200Title
     */
    public void setSch200Title(String sch200Title) {
        sch200Title__ = sch200Title;
    }
    /**
     * <p>sch200ActionErrorsCnt を取得します。
     * @return sch200ActionErrorsCnt
     */
    public int getSch200ActionErrorsCnt() {
        return sch200ActionErrorsCnt__;
    }
    /**
     * <p>sch200ActionErrorsCnt をセットします。
     * @param sch200ActionErrorsCnt sch200ActionErrorsCnt
     */
    public void setSch200ActionErrorsCnt(int sch200ActionErrorsCnt) {
        sch200ActionErrorsCnt__ = sch200ActionErrorsCnt;
    }
    /**
     * <p>sch200ErrorsRowCnt を取得します。
     * @return sch200ErrorsRowCnt
     */
    public int getSch200ErrorsRowCnt() {
        return sch200ErrorsRowCnt__;
    }
    /**
     * <p>sch200ErrorsRowCnt をセットします。
     * @param sch200ErrorsRowCnt sch200ErrorsRowCnt
     */
    public void setSch200ErrorsRowCnt(int sch200ErrorsRowCnt) {
        sch200ErrorsRowCnt__ = sch200ErrorsRowCnt;
    }
    /**
     * <p>sch200EventKbn を取得します。
     * @return sch200EventKbn
     */
    public int getSch200EventKbn() {
        return sch200EventKbn__;
    }
    /**
     * <p>sch200EventKbn をセットします。
     * @param sch200EventKbn sch200EventKbn
     */
    public void setSch200EventKbn(int sch200EventKbn) {
        sch200EventKbn__ = sch200EventKbn;
    }
    /**
     * <p>sch200ChGroupFlg を取得します。
     * @return sch200ChGroupFlg
     */
    public int getSch200ChGroupFlg() {
        return sch200ChGroupFlg__;
    }
    /**
     * <p>sch200ChGroupFlg をセットします。
     * @param sch200ChGroupFlg sch200ChGroupFlg
     */
    public void setSch200ChGroupFlg(int sch200ChGroupFlg) {
        sch200ChGroupFlg__ = sch200ChGroupFlg;
    }
    /**
     * <p>sch200Cancel を取得します。
     * @return sch200Cancel
     */
    public int getSch200Cancel() {
        return sch200Cancel__;
    }
    /**
     * <p>sch200Cancel をセットします。
     * @param sch200Cancel sch200Cancel
     */
    public void setSch200Cancel(int sch200Cancel) {
        sch200Cancel__ = sch200Cancel;
    }
    /**
     * <p>sch200InitFlg を取得します。
     * @return sch200InitFlg
     */
    public int getSch200InitFlg() {
        return sch200InitFlg__;
    }
    /**
     * <p>sch200InitFlg をセットします。
     * @param sch200InitFlg sch200InitFlg
     */
    public void setSch200InitFlg(int sch200InitFlg) {
        sch200InitFlg__ = sch200InitFlg;
    }
    /**
     * <p>sch200WarningAddUserFlg を取得します。
     * @return sch200WarningAddUserFlg
     */
    public int getSch200WarningAddUserFlg() {
        return sch200WarningAddUserFlg__;
    }
    /**
     * <p>sch200WarningAddUserFlg をセットします。
     * @param sch200WarningAddUserFlg sch200WarningAddUserFlg
     */
    public void setSch200WarningAddUserFlg(int sch200WarningAddUserFlg) {
        sch200WarningAddUserFlg__ = sch200WarningAddUserFlg;
    }
}
