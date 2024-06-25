package jp.groupsession.v2.sch.sch120;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.sch.sch020.Sch020Form;

/**
 * <br>[機  能] スケジュール 施設予約一覧POPUP(日間)画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch120Form extends Sch020Form {

    /** 遷移モード(通常・拡張) */
    private String sch120MoveMode__ = String.valueOf(GSConstSchedule.MOVE_NO);

    /** 表示開始時間(HH) */
    private String sch120FromHour__;
    /** 表示終了時間(HH) */
    private String sch120ToHour__;
    /** 表示全体Cols */
    private String sch120TotalCols__;
    /** 自動リロード時間 */
    private int sch120Reload__ = GSConstSchedule.AUTO_RELOAD_10MIN;

    //表示項目
    /** ヘッダー表示年月日 */
    private String sch120StrDate__;
    /** タイムチャートリスト */
    private ArrayList<String> sch120TimeChartList__;
    /** １時間あたりのメモリ個数*/
    private String sch120MemoriCount__;

    /** 表示する施設グループSID */
    private String sch120ResDspGpSid__;
    /** タイムチャートリスト */
    private ArrayList<String> rsv020TimeChartList__ = null;
    /** 予約情報リスト */
    private ArrayList<RsvSisetuModel> rsv020DaylyList__ = null;

    /** ユーザーリスト（登録予定） */
    private String[] sv_users__ = null;
    /** ユーザーリスト（選択ユーザ）*/
    private String[] users_l__ = null;
    /** 拡張ユーザーリスト（登録予定） */
    private String[] sch041SvUsers__ = null;
    /** 拡張ユーザーリスト（選択ユーザ）*/
    private String[] sch041users_l__ = null;

    /** 施設リスト（登録予定） */
    private String[] svReserveUsers__ = null;
    /** 施設リスト（選択）*/
    private String[] reserve_l__ = null;
    /** 拡張施設リスト（登録予定） */
    private String[] sch041SvReserve__ = null;
    /** 拡張施設リスト（選択）*/
    private String[] sch041Reserve_l__ = null;

    /**
     * <p>sch120MoveMode を取得します。
     * @return sch120MoveMode
     */
    public String getSch120MoveMode() {
        return sch120MoveMode__;
    }
    /**
     * <p>sch120MoveMode をセットします。
     * @param sch120MoveMode sch120MoveMode
     */
    public void setSch120MoveMode(String sch120MoveMode) {
        sch120MoveMode__ = sch120MoveMode;
    }
    /**
     * <p>sch041Reserve_l を取得します。
     * @return sch041Reserve_l
     */
    public String[] getSch041Reserve_l() {
        return sch041Reserve_l__;
    }
    /**
     * <p>sch041Reserve_l をセットします。
     * @param sch041Reserve sch041Reserve_l
     */
    public void setSch041Reserve_l(String[] sch041Reserve) {
        sch041Reserve_l__ = sch041Reserve;
    }
    /**
     * <p>sch041SvReserve を取得します。
     * @return sch041SvReserve
     */
    public String[] getSch041SvReserve() {
        return sch041SvReserve__;
    }
    /**
     * <p>sch041SvReserve をセットします。
     * @param sch041SvReserve sch041SvReserve
     */
    public void setSch041SvReserve(String[] sch041SvReserve) {
        sch041SvReserve__ = sch041SvReserve;
    }
    /**
     * <p>sch041SvUsers を取得します。
     * @return sch041SvUsers
     */
    public String[] getSch041SvUsers() {
        return sch041SvUsers__;
    }
    /**
     * <p>sch041SvUsers をセットします。
     * @param sch041SvUsers sch041SvUsers
     */
    public void setSch041SvUsers(String[] sch041SvUsers) {
        sch041SvUsers__ = sch041SvUsers;
    }
    /**
     * <p>sch041users_l を取得します。
     * @return sch041users_l
     */
    public String[] getSch041users_l() {
        return sch041users_l__;
    }
    /**
     * <p>sch041users_l をセットします。
     * @param sch041users sch041users_l
     */
    public void setSch041users_l(String[] sch041users) {
        sch041users_l__ = sch041users;
    }
    /**
     * <p>reserve_l を取得します。
     * @return reserve_l
     */
    public String[] getReserve_l() {
        return reserve_l__;
    }
    /**
     * <p>reserve_l をセットします。
     * @param reserve reserve_l
     */
    public void setReserve_l(String[] reserve) {
        reserve_l__ = reserve;
    }
    /**
     * <p>svReserveUsers を取得します。
     * @return svReserveUsers
     */
    public String[] getSvReserveUsers() {
        return svReserveUsers__;
    }
    /**
     * <p>svReserveUsers をセットします。
     * @param svReserveUsers svReserveUsers
     */
    public void setSvReserveUsers(String[] svReserveUsers) {
        svReserveUsers__ = svReserveUsers;
    }
    /**
     * <p>sv_users を取得します。
     * @return sv_users
     */
    public String[] getSv_users() {
        return sv_users__;
    }
    /**
     * <p>sv_users をセットします。
     * @param svusers sv_users
     */
    public void setSv_users(String[] svusers) {
        sv_users__ = svusers;
    }
    /**
     * <p>users_l を取得します。
     * @return users_l
     */
    public String[] getUsers_l() {
        return users_l__;
    }
    /**
     * <p>users_l をセットします。
     * @param users users_l
     */
    public void setUsers_l(String[] users) {
        users_l__ = users;
    }
    /**
     * <p>rsv020DaylyList を取得します。
     * @return rsv020DaylyList
     */
    public ArrayList<RsvSisetuModel> getRsv020DaylyList() {
        return rsv020DaylyList__;
    }
    /**
     * <p>rsv020DaylyList をセットします。
     * @param rsv020DaylyList rsv020DaylyList
     */
    public void setRsv020DaylyList(ArrayList<RsvSisetuModel> rsv020DaylyList) {
        rsv020DaylyList__ = rsv020DaylyList;
    }
    /**
     * <p>rsv020TimeChartList を取得します。
     * @return rsv020TimeChartList
     */
    public ArrayList<String> getRsv020TimeChartList() {
        return rsv020TimeChartList__;
    }
    /**
     * <p>rsv020TimeChartList をセットします。
     * @param rsv020TimeChartList rsv020TimeChartList
     */
    public void setRsv020TimeChartList(ArrayList<String> rsv020TimeChartList) {
        rsv020TimeChartList__ = rsv020TimeChartList;
    }
    /**
     * <p>sch120ResDspGpSid を取得します。
     * @return sch120ResDspGpSid
     */
    public String getSch120ResDspGpSid() {
        return sch120ResDspGpSid__;
    }
    /**
     * <p>sch120ResDspGpSid をセットします。
     * @param sch120ResDspGpSid sch120ResDspGpSid
     */
    public void setSch120ResDspGpSid(String sch120ResDspGpSid) {
        sch120ResDspGpSid__ = sch120ResDspGpSid;
    }
    /**
     * <p>sch120FromHour を取得します。
     * @return sch120FromHour
     */
    public String getSch120FromHour() {
        return sch120FromHour__;
    }
    /**
     * <p>sch120FromHour をセットします。
     * @param sch120FromHour sch120FromHour
     */
    public void setSch120FromHour(String sch120FromHour) {
        sch120FromHour__ = sch120FromHour;
    }
    /**
     * <p>sch120MemoriCount を取得します。
     * @return sch120MemoriCount
     */
    public String getSch120MemoriCount() {
        return sch120MemoriCount__;
    }
    /**
     * <p>sch120MemoriCount をセットします。
     * @param sch120MemoriCount sch120MemoriCount
     */
    public void setSch120MemoriCount(String sch120MemoriCount) {
        sch120MemoriCount__ = sch120MemoriCount;
    }
    /**
     * <p>sch120Reload を取得します。
     * @return sch120Reload
     */
    public int getSch120Reload() {
        return sch120Reload__;
    }
    /**
     * <p>sch120Reload をセットします。
     * @param sch120Reload sch120Reload
     */
    public void setSch120Reload(int sch120Reload) {
        sch120Reload__ = sch120Reload;
    }
    /**
     * <p>sch120StrDate を取得します。
     * @return sch120StrDate
     */
    public String getSch120StrDate() {
        return sch120StrDate__;
    }
    /**
     * <p>sch120StrDate をセットします。
     * @param sch120StrDate sch120StrDate
     */
    public void setSch120StrDate(String sch120StrDate) {
        sch120StrDate__ = sch120StrDate;
    }
    /**
     * <p>sch120TimeChartList を取得します。
     * @return sch120TimeChartList
     */
    public ArrayList<String> getSch120TimeChartList() {
        return sch120TimeChartList__;
    }
    /**
     * <p>sch120TimeChartList をセットします。
     * @param sch120TimeChartList sch120TimeChartList
     */
    public void setSch120TimeChartList(ArrayList<String> sch120TimeChartList) {
        sch120TimeChartList__ = sch120TimeChartList;
    }
    /**
     * <p>sch120ToHour を取得します。
     * @return sch120ToHour
     */
    public String getSch120ToHour() {
        return sch120ToHour__;
    }
    /**
     * <p>sch120ToHour をセットします。
     * @param sch120ToHour sch120ToHour
     */
    public void setSch120ToHour(String sch120ToHour) {
        sch120ToHour__ = sch120ToHour;
    }
    /**
     * <p>sch120TotalCols を取得します。
     * @return sch120TotalCols
     */
    public String getSch120TotalCols() {
        return sch120TotalCols__;
    }
    /**
     * <p>sch120TotalCols をセットします。
     * @param sch120TotalCols sch120TotalCols
     */
    public void setSch120TotalCols(String sch120TotalCols) {
        sch120TotalCols__ = sch120TotalCols;
    }

}
