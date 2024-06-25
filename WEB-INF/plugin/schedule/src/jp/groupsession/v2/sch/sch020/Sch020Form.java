package jp.groupsession.v2.sch.sch020;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch010.Sch010Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 月間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch020Form extends Sch010Form {


    //表示内容
    /** ヘッダー表示用年月 */
    private String sch020StrDspDate__ = null;
    /** ヘッダーユーザ名称 */
    private String sch020StrUserName__ = null;
    /** スケジュールリスト */
    private ArrayList<Sch020MonthOfModel> sch020ScheduleList__ = null;

    /** 選択ユーザ */
    private String sch020SelectUsrSid__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> sch020UsrLabelList__ = null;
    /** 自動リロード時間 */
    private int sch020Reload__ = GSConstSchedule.AUTO_RELOAD_10MIN;
    /** 表示開始曜日 */
    private int sch020DspStartWeek__ = GSConstSchedule.CHANGE_WEEK_SUN;

    /** マイグループフラグ */
    private boolean sch020MyGroupFlg__ = false;
    /** スケジュール登録可能フラグ */
    private boolean sch020RegistFlg__ = false;

    /**
     * <p>sch020SelectUsrSid を取得します。
     * @return sch020SelectUsrSid
     */
    public String getSch020SelectUsrSid() {
        return sch020SelectUsrSid__;
    }
    /**
     * <p>sch020SelectUsrSid をセットします。
     * @param sch020SelectUsrSid sch020SelectUsrSid
     */
    public void setSch020SelectUsrSid(String sch020SelectUsrSid) {
        sch020SelectUsrSid__ = sch020SelectUsrSid;
    }
    /**
     * <p>sch020UsrLabelList を取得します。
     * @return sch020UsrLabelList
     */
    public List<UsrLabelValueBean> getSch020UsrLabelList() {
        return sch020UsrLabelList__;
    }
    /**
     * <p>sch020UsrLabelList をセットします。
     * @param sch020UsrLabelList sch020UsrLabelList
     */
    public void setSch020UsrLabelList(List<UsrLabelValueBean> sch020UsrLabelList) {
        sch020UsrLabelList__ = sch020UsrLabelList;
    }
    /**
     * @return sch020ScheduleList を戻します。
     */
    public ArrayList<Sch020MonthOfModel> getSch020ScheduleList() {
        return sch020ScheduleList__;
    }
    /**
     * @param sch020ScheduleList 設定する sch020ScheduleList。
     */
    public void setSch020ScheduleList(ArrayList<Sch020MonthOfModel> sch020ScheduleList) {
        sch020ScheduleList__ = sch020ScheduleList;
    }
    /**
     * @return sch020StrDspDate を戻します。
     */
    public String getSch020StrDspDate() {
        return sch020StrDspDate__;
    }
    /**
     * @param sch020StrDspDate 設定する sch020StrDspDate。
     */
    public void setSch020StrDspDate(String sch020StrDspDate) {
        sch020StrDspDate__ = sch020StrDspDate;
    }
    /**
     * @return sch020StrUserName を戻します。
     */
    public String getSch020StrUserName() {
        return sch020StrUserName__;
    }
    /**
     * @param sch020StrUserName 設定する sch020StrUserName。
     */
    public void setSch020StrUserName(String sch020StrUserName) {
        sch020StrUserName__ = sch020StrUserName;
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
     * @return sch020DspStartWeek
     */
    public int getSch020DspStartWeek() {
        return sch020DspStartWeek__;
    }
    /**
     * @param sch020DspStartWeek 設定する sch020DspStartWeek
     */
    public void setSch020DspStartWeek(int sch020DspStartWeek) {
        sch020DspStartWeek__ = sch020DspStartWeek;
    }
    /**
     * @return sch020MyGroupFlg
     */
    public boolean isSch020MyGroupFlg() {
        return sch020MyGroupFlg__;
    }
    /**
     * @param sch020MyGroupFlg 設定する sch020MyGroupFlg
     */
    public void setSch020MyGroupFlg(boolean sch020MyGroupFlg) {
        sch020MyGroupFlg__ = sch020MyGroupFlg;
    }
    /**
     * <p>sch020RegistFlg を取得します。
     * @return sch020RegistFlg
     */
    public boolean isSch020RegistFlg() {
        return sch020RegistFlg__;
    }
    /**
     * <p>sch020RegistFlg をセットします。
     * @param sch020RegistFlg sch020RegistFlg
     */
    public void setSch020RegistFlg(boolean sch020RegistFlg) {
        sch020RegistFlg__ = sch020RegistFlg;
    }
}
