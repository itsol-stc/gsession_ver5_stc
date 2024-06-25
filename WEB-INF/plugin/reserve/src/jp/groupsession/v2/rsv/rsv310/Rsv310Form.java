package jp.groupsession.v2.rsv.rsv310;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.rsv.rsv020.Rsv020Form;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;

/**
 * <br>[機  能] 施設予約 空き状況一覧POPUP画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv310Form extends Rsv020Form {

    /** 表示モード 施設予約登録画面(rsv110) */
    public static final int POP_DSP_MODE_RSV110 = 1;
    /** 表示モード 施設予約拡張登録画面(rsv111) */
    public static final int POP_DSP_MODE_RSV111 = 2;
    /** 表示モード 施設予約一括登録画面(rsv210) */
    public static final int POP_DSP_MODE_RSV210 = 3;


    /** 初期表示フラグ 0:初期 */
    private int rsv310InitFlg__ = 0;
    /** 処理モード */
    private String cmd__ = null;
    /** 遷移モード(通常・拡張) */
    private String rsv310MoveMode__ = String.valueOf(GSConstSchedule.MOVE_NO);

    /** ポップアップ表示モード */
    private int popDspMode__ = Rsv310Form.POP_DSP_MODE_RSV110;

    /** 表示開始時間(HH) */
    private String rsv310FromHour__;
    /** 表示終了時間(HH) */
    private String rsv310ToHour__;
    /** 表示全体Cols */
    private String rsv310TotalCols__;
    /** 自動リロード時間 */
    private int rsv310Reload__ = GSConstReserve.AUTO_RELOAD_10MIN;

    //表示項目
    /** ヘッダー表示年月日 */
    private String rsv310StrDate__;
    /** タイムチャートリスト */
    private ArrayList<String> rsv310TimeChartList__;
    /** １時間あたりのメモリ個数*/
    private String rsv310MemoriCount__;
    /** スケジュール上段リスト */
    private ArrayList<Sch010WeekOfModel> sch010TopList__ = null;
    /** スケジュール下段リスト */
    private ArrayList<Sch010WeekOfModel> sch010BottomList__ = null;


    //表示条件
    /** 施設SID */
    private int rsvSelectedSisetuSid__ = -1;
    /** 表示開始日付 */
    private String rsv310DspDate__ = null;
    /** 拡張同時登録ユーザーリスト */
    private String[] rsv111SvUsers__ = null;

    /** スケジュール登録区分 0:ユーザ 1:グループ */
    private int rsv110SchKbn__ = GSConstReserve.RSV_SCHKBN_USER;
    /** 同時登録スケジュールグループSID */
    private String rsv110SchGroupSid__ = "-1";
    /** スケジュール登録区分 0:ユーザ 1:グループ */
    private int rsv111SchKbn__ = GSConstReserve.RSV_SCHKBN_USER;
    /** 同時登録スケジュールグループSID */
    private String rsv111SchGroupSid__ = "-1";
    /** スケジュール登録区分 0:ユーザ 1:グループ */
    private int rsv210SchKbn__ = GSConstReserve.RSV_SCHKBN_USER;
    /** 同時登録スケジュールグループSID */
    private String rsv210SchGroupSid__ = "-1";


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

    //その他プラグインの利用可能状況
    /** スケジュールプラグイン利用可:0・不可:1*/
    private int scheduleUseOk__ = GSConstReserve.PLUGIN_USE;
    /** 在席管理プラグイン利用可：0・不可：1*/
    private int zaisekiUseOk__ = GSConstReserve.PLUGIN_USE;

    /** 管理者権限有無*/
    private int adminKbn__;


    /**
     * <p>rsv310MoveMode を取得します。
     * @return rsv310MoveMode
     */
    public String getRsv310MoveMode() {
        return rsv310MoveMode__;
    }
    /**
     * <p>rsv310MoveMode をセットします。
     * @param rsv310MoveMode rsv310MoveMode
     */
    public void setRsv310MoveMode(String rsv310MoveMode) {
        rsv310MoveMode__ = rsv310MoveMode;
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
     * <p>rsv310FromHour を取得します。
     * @return rsv310FromHour
     */
    public String getRsv310FromHour() {
        return rsv310FromHour__;
    }
    /**
     * <p>rsv310FromHour をセットします。
     * @param rsv310FromHour rsv310FromHour
     */
    public void setRsv310FromHour(String rsv310FromHour) {
        rsv310FromHour__ = rsv310FromHour;
    }
    /**
     * <p>rsv310MemoriCount を取得します。
     * @return rsv310MemoriCount
     */
    public String getRsv310MemoriCount() {
        return rsv310MemoriCount__;
    }
    /**
     * <p>rsv310MemoriCount をセットします。
     * @param rsv310MemoriCount rsv310MemoriCount
     */
    public void setRsv310MemoriCount(String rsv310MemoriCount) {
        rsv310MemoriCount__ = rsv310MemoriCount;
    }
    /**
     * <p>rsv310Reload を取得します。
     * @return rsv310Reload
     */
    public int getRsv310Reload() {
        return rsv310Reload__;
    }
    /**
     * <p>rsv310Reload をセットします。
     * @param rsv310Reload rsv310Reload
     */
    public void setRsv310Reload(int rsv310Reload) {
        rsv310Reload__ = rsv310Reload;
    }
    /**
     * <p>rsv310StrDate を取得します。
     * @return rsv310StrDate
     */
    public String getRsv310StrDate() {
        return rsv310StrDate__;
    }
    /**
     * <p>rsv310StrDate をセットします。
     * @param rsv310StrDate rsv310StrDate
     */
    public void setRsv310StrDate(String rsv310StrDate) {
        rsv310StrDate__ = rsv310StrDate;
    }
    /**
     * <p>rsv310TimeChartList を取得します。
     * @return rsv310TimeChartList
     */
    public ArrayList<String> getRsv310TimeChartList() {
        return rsv310TimeChartList__;
    }
    /**
     * <p>rsv310TimeChartList をセットします。
     * @param rsv310TimeChartList rsv310TimeChartList
     */
    public void setRsv310TimeChartList(ArrayList<String> rsv310TimeChartList) {
        rsv310TimeChartList__ = rsv310TimeChartList;
    }
    /**
     * <p>rsv310ToHour を取得します。
     * @return rsv310ToHour
     */
    public String getRsv310ToHour() {
        return rsv310ToHour__;
    }
    /**
     * <p>rsv310ToHour をセットします。
     * @param rsv310ToHour rsv310ToHour
     */
    public void setRsv310ToHour(String rsv310ToHour) {
        rsv310ToHour__ = rsv310ToHour;
    }
    /**
     * <p>rsv310TotalCols を取得します。
     * @return rsv310TotalCols
     */
    public String getRsv310TotalCols() {
        return rsv310TotalCols__;
    }
    /**
     * <p>rsv310TotalCols をセットします。
     * @param rsv310TotalCols rsv310TotalCols
     */
    public void setRsv310TotalCols(String rsv310TotalCols) {
        rsv310TotalCols__ = rsv310TotalCols;
    }
    /**
     * <p>rsv310DspDate を取得します。
     * @return rsv310DspDate
     */
    public String getRsv310DspDate() {
        return rsv310DspDate__;
    }
    /**
     * <p>rsv310DspDate をセットします。
     * @param rsv310DspDate rsv310DspDate
     */
    public void setRsv310DspDate(String rsv310DspDate) {
        rsv310DspDate__ = rsv310DspDate;
    }
    /**
     * <p>adminKbn を取得します。
     * @return adminKbn
     */
    public int getAdminKbn() {
        return adminKbn__;
    }
    /**
     * <p>adminKbn をセットします。
     * @param adminKbn adminKbn
     */
    public void setAdminKbn(int adminKbn) {
        adminKbn__ = adminKbn;
    }
    /**
     * <p>sch010TopList を取得します。
     * @return sch010TopList
     */
    public ArrayList<Sch010WeekOfModel> getSch010TopList() {
        return sch010TopList__;
    }
    /**
     * <p>sch010TopList をセットします。
     * @param sch010TopList sch010TopList
     */
    public void setSch010TopList(ArrayList<Sch010WeekOfModel> sch010TopList) {
        sch010TopList__ = sch010TopList;
    }
    /**
     * <p>sch010BottomList を取得します。
     * @return sch010BottomList
     */
    public ArrayList<Sch010WeekOfModel> getSch010BottomList() {
        return sch010BottomList__;
    }
    /**
     * <p>sch010BottomList をセットします。
     * @param sch010BottomList sch010BottomList
     */
    public void setSch010BottomList(ArrayList<Sch010WeekOfModel> sch010BottomList) {
        sch010BottomList__ = sch010BottomList;
    }
    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }
    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }
    /**
     * <p>popDspMode を取得します。
     * @return popDspMode
     */
    public int getPopDspMode() {
        return popDspMode__;
    }
    /**
     * <p>popDspMode をセットします。
     * @param popDspMode popDspMode
     */
    public void setPopDspMode(int popDspMode) {
        popDspMode__ = popDspMode;
    }
    /**
     * <p>rsv310InitFlg を取得します。
     * @return rsv310InitFlg
     */
    public int getRsv310InitFlg() {
        return rsv310InitFlg__;
    }
    /**
     * <p>rsv310InitFlg をセットします。
     * @param rsv310InitFlg rsv310InitFlg
     */
    public void setRsv310InitFlg(int rsv310InitFlg) {
        rsv310InitFlg__ = rsv310InitFlg;
    }
    /**
     * <p>rsv111SvUsers を取得します。
     * @return rsv111SvUsers
     */
    public String[] getRsv111SvUsers() {
        return rsv111SvUsers__;
    }
    /**
     * <p>rsv111SvUsers をセットします。
     * @param rsv111SvUsers rsv111SvUsers
     */
    public void setRsv111SvUsers(String[] rsv111SvUsers) {
        rsv111SvUsers__ = rsv111SvUsers;
    }
    /**
     * <p>rsv110SchKbn を取得します。
     * @return rsv110SchKbn
     */
    public int getRsv110SchKbn() {
        return rsv110SchKbn__;
    }
    /**
     * <p>rsv110SchKbn をセットします。
     * @param rsv110SchKbn rsv110SchKbn
     */
    public void setRsv110SchKbn(int rsv110SchKbn) {
        rsv110SchKbn__ = rsv110SchKbn;
    }
    /**
     * <p>rsv110SchGroupSid を取得します。
     * @return rsv110SchGroupSid
     */
    public String getRsv110SchGroupSid() {
        return rsv110SchGroupSid__;
    }
    /**
     * <p>rsv110SchGroupSid をセットします。
     * @param rsv110SchGroupSid rsv110SchGroupSid
     */
    public void setRsv110SchGroupSid(String rsv110SchGroupSid) {
        rsv110SchGroupSid__ = rsv110SchGroupSid;
    }
    /**
     * <p>rsv111SchGroupSid を取得します。
     * @return rsv111SchGroupSid
     */
    public String getRsv111SchGroupSid() {
        return rsv111SchGroupSid__;
    }
    /**
     * <p>rsv111SchGroupSid をセットします。
     * @param rsv111SchGroupSid rsv111SchGroupSid
     */
    public void setRsv111SchGroupSid(String rsv111SchGroupSid) {
        rsv111SchGroupSid__ = rsv111SchGroupSid;
    }
    /**
     * <p>rsv111SchKbn を取得します。
     * @return rsv111SchKbn
     */
    public int getRsv111SchKbn() {
        return rsv111SchKbn__;
    }
    /**
     * <p>rsv111SchKbn をセットします。
     * @param rsv111SchKbn rsv111SchKbn
     */
    public void setRsv111SchKbn(int rsv111SchKbn) {
        rsv111SchKbn__ = rsv111SchKbn;
    }
    /**
     * <p>scheduleUseOk を取得します。
     * @return scheduleUseOk
     */
    public int getScheduleUseOk() {
        return scheduleUseOk__;
    }
    /**
     * <p>scheduleUseOk をセットします。
     * @param scheduleUseOk scheduleUseOk
     */
    public void setScheduleUseOk(int scheduleUseOk) {
        scheduleUseOk__ = scheduleUseOk;
    }
    /**
     * <p>zaisekiUseOk を取得します。
     * @return zaisekiUseOk
     * @see jp.groupsession.v2.rsv.rsv310.Rsv310Form#zaisekiUseOk__
     */
    public int getZaisekiUseOk() {
        return zaisekiUseOk__;
    }
    /**
     * <p>zaisekiUseOk をセットします。
     * @param zaisekiUseOk zaisekiUseOk
     * @see jp.groupsession.v2.rsv.rsv310.Rsv310Form#zaisekiUseOk__
     */
    public void setZaisekiUseOk(int zaisekiUseOk) {
        zaisekiUseOk__ = zaisekiUseOk;
    }
    /**
     * <p>rsv210SchKbn を取得します。
     * @return rsv210SchKbn
     */
    public int getRsv210SchKbn() {
        return rsv210SchKbn__;
    }
    /**
     * <p>rsv210SchKbn をセットします。
     * @param rsv210SchKbn rsv210SchKbn
     */
    public void setRsv210SchKbn(int rsv210SchKbn) {
        rsv210SchKbn__ = rsv210SchKbn;
    }
    /**
     * <p>rsv210SchGroupSid を取得します。
     * @return rsv210SchGroupSid
     */
    public String getRsv210SchGroupSid() {
        return rsv210SchGroupSid__;
    }
    /**
     * <p>rsv210SchGroupSid をセットします。
     * @param rsv210SchGroupSid rsv210SchGroupSid
     */
    public void setRsv210SchGroupSid(String rsv210SchGroupSid) {
        rsv210SchGroupSid__ = rsv210SchGroupSid;
    }
    /**
     * <p>rsvSelectedSisetuSid を取得します。
     * @return rsvSelectedSisetuSid
     */
    public int getRsvSelectedSisetuSid() {
        return rsvSelectedSisetuSid__;
    }
    /**
     * <p>rsvSelectedSisetuSid をセットします。
     * @param rsvSelectedSisetuSid rsvSelectedSisetuSid
     */
    public void setRsvSelectedSisetuSid(int rsvSelectedSisetuSid) {
        rsvSelectedSisetuSid__ = rsvSelectedSisetuSid;
    }

}
