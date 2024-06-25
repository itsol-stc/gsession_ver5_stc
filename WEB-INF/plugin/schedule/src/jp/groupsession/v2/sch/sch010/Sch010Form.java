package jp.groupsession.v2.sch.sch010;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.model.SchEasyRegisterModel;
import jp.groupsession.v2.sch.model.SchHidModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.model.SimpleCalenderModel;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] スケジュール 週間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010Form extends AbstractGsForm {

    //共通・モード
    /** 処理モード */
    private String cmd__ = null;
    /** 画面モード */
    private String dspMod__ = null;
    /** 画面モード(一覧用) */
    private String listMod__ = null;
    /** 一括登録フラグ */
    private int schIkkatsuFlg__ = 0;
    /** 一括登録表示モード */
    private int schIkkatsuViewMode__ = 0;
    /** 一括登録確認表示モード */
    private int schIkkatsuKakuninViewMode__ = 0;
    /** 一括登録対象 */
    private String[] schIkkatuTorokuKey__ = null;
    /** 一括登録対象(画面外選択値の保持用) */
    private String[] schIkkatuTorokuSaveKey__ = null;
    /** 一括登録対象(画面外選択値の各種情報) */
    private List<List<SchHidModel>> schIkkatuTorokuHideList__ = null;
    /** 一括登録 削除対象キー */
    private String schIkkatsuRemoveKey__ = null;

    /** スケジュール共有範囲  0=共有範囲制限なし 1=所属グループのみ*/
    private int sch010CrangeKbn__;

    /** 検索キーワード */
    private String sch010searchWord__;

    //表示条件
    /** 表示開始日付 */
    private String sch010DspDate__ = null;
    /** 表示グループSID */
    private String sch010DspGpSid__;
    /** ユーザSID */
    private String sch010SelectUsrSid__;
    /** ユーザ区分 */
    private String sch010SelectUsrKbn__;
    /** スケジュール登録日付 */
    private String sch010SelectDate__ = null;
    /** スケジュールSID */
    private String sch010SchSid__;

    /** 自動リロード時間 */
    private int sch010Reload__ = GSConstSchedule.AUTO_RELOAD_10MIN;

    //表示内容
    /** ヘッダー表示用年月 */
    private String sch010StrDspDate__ = null;
    /** 一覧のヘッダに表示する六曜 */
    private String schDispRokuyou__ = null;
    /** 週間カレンダー */
    private ArrayList<SimpleCalenderModel> sch010CalendarList__ = null;
    /** グループコンボ */
    private List<SchLabelValueModel> sch010GpLabelList__ = null;
    /** スケジュール上段リスト */
    private ArrayList<Sch010WeekOfModel> sch010TopList__ = null;
    /** スケジュール下段リスト */
    private ArrayList<Sch010WeekOfModel> sch010BottomList__ = null;
    /** スケジュールリスト内のログインユーザのスケジュール */
    private Sch010WeekOfModel sch010UserSchedule__ = null;
    /** ログインユーザのユーザ情報 */
    private Sch010UsrModel sch010UserData__ = null;

    /** 管理者権限有無*/
    private int adminKbn__;
    /** グループ所属有無*/
    private int belongKbn__;
    /** セッションユーザのデフォルトグループSID */
    private String sysDfGroupSid__;
    /** 表示グループ閲覧権限 */
    private boolean schAccessGroup__ = true;
    /** 閲覧を許可しないグループの一覧 */
    private List<Integer> schNotAccessGroupList__;
    /** 閲覧を許可しないユーザの一覧 */
    private List<Integer> schNotAccessUserList__;

    //その他プラグインの利用可能状況
    /** 在席管理プラグイン利用可:0・不可:1*/
    private int zaisekiUseOk__ = GSConstSchedule.PLUGIN_USE;
    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConstSchedule.PLUGIN_USE;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 表示区分 0:初期表示 1:初期表示済 2:ショートメールURLリンク先から遷移*/
    private int dspKbn__ = 0;
    /** 表示日付の移動を行ったかどうかの区分 0:未実行 1:実行 */
    private int changeDateFlg__ = 0;

    /** メイン画面から遷移したかどうかの区分 0:していない 1:メイン画面から遷移した */
    private int sch010FromMain__ = 0;
    /** 初期表示区分 初期表示時：0*/
    private int iniDsp__ = GSConstSchedule.INIT_FLG;

    /** 簡易登録モデル*/
    private SchEasyRegisterModel easyRegister__ = new SchEasyRegisterModel();

    /**
     * <p>sysDfGroupSid を取得します。
     * @return sysDfGroupSid
     */
    public String getSysDfGroupSid() {
        return sysDfGroupSid__;
    }

    /**
     * <p>sysDfGroupSid をセットします。
     * @param sysDfGroupSid sysDfGroupSid
     */
    public void setSysDfGroupSid(String sysDfGroupSid) {
        sysDfGroupSid__ = sysDfGroupSid;
    }

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }

    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }

    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }

    /**
     * <p>zaisekiUseOk を取得します。
     * @return zaisekiUseOk
     */
    public int getZaisekiUseOk() {
        return zaisekiUseOk__;
    }

    /**
     * <p>zaisekiUseOk をセットします。
     * @param zaisekiUseOk zaisekiUseOk
     */
    public void setZaisekiUseOk(int zaisekiUseOk) {
        zaisekiUseOk__ = zaisekiUseOk;
    }

    /**
     * <p>sch010searchWord を取得します。
     * @return sch010searchWord
     */
    public String getSch010searchWord() {
        return sch010searchWord__;
    }



    /**
     * <p>sch010searchWord をセットします。
     * @param sch010searchWord sch010searchWord
     */
    public void setSch010searchWord(String sch010searchWord) {
        sch010searchWord__ = sch010searchWord;
    }



    /**
     * <p>belongKbn を取得します。
     * @return belongKbn
     */
    public int getBelongKbn() {
        return belongKbn__;
    }



    /**
     * <p>belongKbn をセットします。
     * @param belongKbn belongKbn
     */
    public void setBelongKbn(int belongKbn) {
        belongKbn__ = belongKbn;
    }



    /**
     * <p>listMod を取得します。
     * @return listMod
     */
    public String getListMod() {
        return listMod__;
    }



    /**
     * <p>listMod をセットします。
     * @param listMod listMod
     */
    public void setListMod(String listMod) {
        listMod__ = listMod;
    }



    /**
     * @return adminKbn を戻します。
     */
    public int getAdminKbn() {
        return adminKbn__;
    }



    /**
     * @param adminKbn 設定する adminKbn。
     */
    public void setAdminKbn(int adminKbn) {
        adminKbn__ = adminKbn;
    }



    /**
     * @return cmd を戻します。
     */
    public String getCmd() {
        return cmd__;
    }



    /**
     * @param cmd 設定する cmd。
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }



    /**
     * @return dspMod を戻します。
     */
    public String getDspMod() {
        return dspMod__;
    }



    /**
     * @param dspMod 設定する dspMod。
     */
    public void setDspMod(String dspMod) {
        dspMod__ = dspMod;
    }



    /**
     * @return sch010BottomList を戻します。
     */
    public ArrayList<Sch010WeekOfModel> getSch010BottomList() {
        return sch010BottomList__;
    }



    /**
     * @param sch010BottomList 設定する sch010BottomList。
     */
    public void setSch010BottomList(ArrayList<Sch010WeekOfModel> sch010BottomList) {
        sch010BottomList__ = sch010BottomList;
    }



    /**
     * <p>sch010UserSchedule を取得します。
     * @return sch010UserSchedule
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#sch010UserSchedule__
     */
    public Sch010WeekOfModel getSch010UserSchedule() {
        return sch010UserSchedule__;
    }

    /**
     * <p>sch010UserSchedule をセットします。
     * @param sch010UserSchedule sch010UserSchedule
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#sch010UserSchedule__
     */
    public void setSch010UserSchedule(Sch010WeekOfModel sch010UserSchedule) {
        sch010UserSchedule__ = sch010UserSchedule;
    }

    /**
     * @return sch010CalendarList を戻します。
     */
    public ArrayList<SimpleCalenderModel> getSch010CalendarList() {
        return sch010CalendarList__;
    }



    /**
     * @param sch010CalendarList 設定する sch010CalendarList。
     */
    public void setSch010CalendarList(ArrayList<SimpleCalenderModel> sch010CalendarList) {
        sch010CalendarList__ = sch010CalendarList;
    }



    /**
     * @return sch010DspDate を戻します。
     */
    public String getSch010DspDate() {
        return sch010DspDate__;
    }



    /**
     * @param sch010DspDate 設定する sch010DspDate。
     */
    public void setSch010DspDate(String sch010DspDate) {
        sch010DspDate__ = sch010DspDate;
    }



    /**
     * @return sch010DspGpSid を戻します。
     */
    public String getSch010DspGpSid() {
        return sch010DspGpSid__;
    }



    /**
     * @param sch010DspGpSid 設定する sch010DspGpSid。
     */
    public void setSch010DspGpSid(String sch010DspGpSid) {
        sch010DspGpSid__ = sch010DspGpSid;
    }



    /**
     * @return sch010GpLabelList を戻します。
     */
    public List <SchLabelValueModel> getSch010GpLabelList() {
        return sch010GpLabelList__;
    }



    /**
     * @param sch010GpLabelList 設定する sch010GpLabelList。
     */
    public void setSch010GpLabelList(List<SchLabelValueModel> sch010GpLabelList) {
        sch010GpLabelList__ = sch010GpLabelList;
    }



    /**
     * @return sch010SchSid を戻します。
     */
    public String getSch010SchSid() {
        return sch010SchSid__;
    }



    /**
     * @param sch010SchSid 設定する sch010SchSid。
     */
    public void setSch010SchSid(String sch010SchSid) {
        sch010SchSid__ = sch010SchSid;
    }



    /**
     * @return sch010SelectDate を戻します。
     */
    public String getSch010SelectDate() {
        return sch010SelectDate__;
    }



    /**
     * @param sch010SelectDate 設定する sch010SelectDate。
     */
    public void setSch010SelectDate(String sch010SelectDate) {
        sch010SelectDate__ = sch010SelectDate;
    }



    /**
     * @return sch010SelectUsrKbn を戻します。
     */
    public String getSch010SelectUsrKbn() {
        return sch010SelectUsrKbn__;
    }



    /**
     * @param sch010SelectUsrKbn 設定する sch010SelectUsrKbn。
     */
    public void setSch010SelectUsrKbn(String sch010SelectUsrKbn) {
        sch010SelectUsrKbn__ = sch010SelectUsrKbn;
    }



    /**
     * @return sch010SelectUsrSid を戻します。
     */
    public String getSch010SelectUsrSid() {
        return sch010SelectUsrSid__;
    }



    /**
     * @param sch010SelectUsrSid 設定する sch010SelectUsrSid。
     */
    public void setSch010SelectUsrSid(String sch010SelectUsrSid) {
        sch010SelectUsrSid__ = sch010SelectUsrSid;
    }



    /**
     * @return sch010StrDspDate を戻します。
     */
    public String getSch010StrDspDate() {
        return sch010StrDspDate__;
    }



    /**
     * @param sch010StrDspDate 設定する sch010StrDspDate。
     */
    public void setSch010StrDspDate(String sch010StrDspDate) {
        sch010StrDspDate__ = sch010StrDspDate;
    }



    /**
     * @return sch010TopList を戻します。
     */
    public ArrayList<Sch010WeekOfModel> getSch010TopList() {
        return sch010TopList__;
    }



    /**
     * @param sch010TopList 設定する sch010TopList。
     */
    public void setSch010TopList(ArrayList<Sch010WeekOfModel> sch010TopList) {
        sch010TopList__ = sch010TopList;
    }



    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(ActionMapping map, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    /**
     * <p>sch010Reload を取得します。
     * @return sch010Reload
     */
    public int getSch010Reload() {
        return sch010Reload__;
    }

    /**
     * <p>sch010Reload をセットします。
     * @param sch010Reload sch010Reload
     */
    public void setSch010Reload(int sch010Reload) {
        sch010Reload__ = sch010Reload;
    }

    /**
     * <p>dspKbn を取得します。
     * @return dspKbn
     */
    public int getDspKbn() {
        return dspKbn__;
    }

    /**
     * <p>dspKbn をセットします。
     * @param dspKbn dspKbn
     */
    public void setDspKbn(int dspKbn) {
        dspKbn__ = dspKbn;
    }

    /**
     * @return changeDateFlg を取得します。
     */
    public int getChangeDateFlg() {
        return changeDateFlg__;
    }

    /**
     * @param changeDateFlg をセットします。
     */
    public void setChangeDateFlg(int changeDateFlg) {
        changeDateFlg__ = changeDateFlg;
    }

    /**
     * <p>sch010CrangeKbn を取得します。
     * @return sch010CrangeKbn
     */
    public int getSch010CrangeKbn() {
        return sch010CrangeKbn__;
    }

    /**
     * <p>sch010CrangeKbn をセットします。
     * @param sch010CrangeKbn sch010CrangeKbn
     */
    public void setSch010CrangeKbn(int sch010CrangeKbn) {
        sch010CrangeKbn__ = sch010CrangeKbn;
    }

    /**
     * <p>schAccessGroup を取得します。
     * @return schAccessGroup
     */
    public boolean isSchAccessGroup() {
        return schAccessGroup__;
    }

    /**
     * <p>schAccessGroup をセットします。
     * @param schAccessGroup schAccessGroup
     */
    public void setSchAccessGroup(boolean schAccessGroup) {
        schAccessGroup__ = schAccessGroup;
    }

    /**
     * <p>schNotAccessGroupList を取得します。
     * @return schNotAccessGroupList
     */
    public List<Integer> getSchNotAccessGroupList() {
        return schNotAccessGroupList__;
    }

    /**
     * <p>schNotAccessGroupList をセットします。
     * @param schNotAccessGroupList schNotAccessGroupList
     */
    public void setSchNotAccessGroupList(List<Integer> schNotAccessGroupList) {
        schNotAccessGroupList__ = schNotAccessGroupList;
    }

    /**
     * <p>schNotAccessUserList を取得します。
     * @return schNotAccessUserList
     */
    public List<Integer> getSchNotAccessUserList() {
        return schNotAccessUserList__;
    }

    /**
     * <p>schNotAccessUserList をセットします。
     * @param schNotAccessUserList schNotAccessUserList
     */
    public void setSchNotAccessUserList(List<Integer> schNotAccessUserList) {
        schNotAccessUserList__ = schNotAccessUserList;
    }

    /**
     * sch010FromMainを取得します。
     *  @return sch010FromMain
     * */
    public int getSch010FromMain() {
        return sch010FromMain__;
    }

    /**
     * sch010FromMainをセットします。
     * @param sch010FromMain sch010FromMain
     * */
    public void setSch010FromMain(int sch010FromMain) {
        sch010FromMain__ = sch010FromMain;
    }

    /**
     * iniDspを取得します。
     * @return iniDsp
     * */
    public int getIniDsp() {
        return iniDsp__;
    }

    /**
     * iniDspをセットします。
     * @param iniDsp iniDsp
     * */
    public void setIniDsp(int iniDsp) {
        iniDsp__ = iniDsp;
    }

    /**
     * <p>schDispRokuyou を取得します。
     * @return schDispRokuyou
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schDispRokuyou__
     */
    public String getSchDispRokuyou() {
        return schDispRokuyou__;
    }

    /**
     * <p>schDispRokuyou をセットします。
     * @param schDispRokuyou schDispRokuyou
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schDispRokuyou__
     */
    public void setSchDispRokuyou(String schDispRokuyou) {
        schDispRokuyou__ = schDispRokuyou;
    }

    /**
     * <p>easyRegister を取得します。
     * @return easyRegister
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#easyRegister__
     */
    public SchEasyRegisterModel getEasyRegister() {
        return easyRegister__;
    }

    /**
     * <p>easyRegister をセットします。
     * @param easyRegister easyRegister
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#easyRegister__
     */
    public void setEasyRegister(SchEasyRegisterModel easyRegister) {
        easyRegister__ = easyRegister;
    }

    /**
     * <p>sch010UserData を取得します。
     * @return sch010UserData
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#sch010UserData__
     */
    public Sch010UsrModel getSch010UserData() {
        return sch010UserData__;
    }

    /**
     * <p>sch010UserData をセットします。
     * @param sch010UserData sch010UserData
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#sch010UserData__
     */
    public void setSch010UserData(Sch010UsrModel sch010UserData) {
        sch010UserData__ = sch010UserData;
    }

    /**
     * <p>schIkkatsuFlg を取得します。
     * @return schIkkatsuFlg
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatsuFlg__
     */
    public int getSchIkkatsuFlg() {
        return schIkkatsuFlg__;
    }

    /**
     * <p>schIkkatsuFlg をセットします。
     * @param schIkkatsuFlg schIkkatsuFlg
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatsuFlg__
     */
    public void setSchIkkatsuFlg(int schIkkatsuFlg) {
        schIkkatsuFlg__ = schIkkatsuFlg;
    }

    /**
     * <p>schIkkatsuViewMode を取得します。
     * @return schIkkatsuViewMode
     * @see jp.groupsession.v2.sch.sch010.Sch010ParamModel#schIkkatsuViewMode__
     */
    public int getSchIkkatsuViewMode() {
        return schIkkatsuViewMode__;
    }

    /**
     * <p>schIkkatsuViewMode をセットします。
     * @param schIkkatsuViewMode schIkkatsuViewMode
     * @see jp.groupsession.v2.sch.sch010.Sch010ParamModel#schIkkatsuViewMode__
     */
    public void setSchIkkatsuViewMode(int schIkkatsuViewMode) {
        schIkkatsuViewMode__ = schIkkatsuViewMode;
    }
    /**
     * <p>schIkkatsuKakuninViewMode を取得します。
     * @return schIkkatsuKakuninViewMode
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatsuKakuninViewMode__
     */
    public int getSchIkkatsuKakuninViewMode() {
        return schIkkatsuKakuninViewMode__;
    }

    /**
     * <p>schIkkatsuKakuninViewMode をセットします。
     * @param schIkkatsuKakuninViewMode schIkkatsuKakuninViewMode
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatsuKakuninViewMode__
     */
    public void setSchIkkatsuKakuninViewMode(int schIkkatsuKakuninViewMode) {
        schIkkatsuKakuninViewMode__ = schIkkatsuKakuninViewMode;
    }

    /**
     * <p>schIkkatuTorokuKey を取得します。
     * @return schIkkatuTorokuKey
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatuTorokuKey__
     */
    public String[] getSchIkkatuTorokuKey() {
        return schIkkatuTorokuKey__;
    }

    /**
     * <p>schIkkatuTorokuKey をセットします。
     * @param schIkkatuTorokuKey schIkkatuTorokuKey
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatuTorokuKey__
     */
    public void setSchIkkatuTorokuKey(String[] schIkkatuTorokuKey) {
        schIkkatuTorokuKey__ = schIkkatuTorokuKey;
    }

    /**
     * <p>schIkkatuTorokuSaveKey を取得します。
     * @return schIkkatuTorokuSaveKey
     * @see jp.groupsession.v2.sch.sch010.Sch010ParamModel#schIkkatuTorokuSaveKey__
     */
    public String[] getSchIkkatuTorokuSaveKey() {
        return schIkkatuTorokuSaveKey__;
    }

    /**
     * <p>schIkkatuTorokuSaveKey をセットします。
     * @param schIkkatuTorokuSaveKey schIkkatuTorokuSaveKey
     * @see jp.groupsession.v2.sch.sch010.Sch010ParamModel#schIkkatuTorokuSaveKey__
     */
    public void setSchIkkatuTorokuSaveKey(String[] schIkkatuTorokuSaveKey) {
        schIkkatuTorokuSaveKey__ = schIkkatuTorokuSaveKey;
    }

    /**
     * <p>schIkkatuTorokuHideList を取得します。
     * @return schIkkatuTorokuHideList
     * @see jp.groupsession.v2.sch.sch010.Sch010ParamModel#schIkkatuTorokuHideList__
     */
    public List<List<SchHidModel>> getSchIkkatuTorokuHideList() {
        return schIkkatuTorokuHideList__;
    }

    /**
     * <p>schIkkatuTorokuHideList をセットします。
     * @param schIkkatuTorokuHideList schIkkatuTorokuHideList
     * @see jp.groupsession.v2.sch.sch010.Sch010ParamModel#schIkkatuTorokuHideList__
     */
    public void setSchIkkatuTorokuHideList(
            List<List<SchHidModel>> schIkkatuTorokuHideList) {
        schIkkatuTorokuHideList__ = schIkkatuTorokuHideList;
    }

    /**
     * <p>schIkkatsuRemoveKey を取得します。
     * @return schIkkatsuRemoveKey
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatsuRemoveKey__
     */
    public String getSchIkkatsuRemoveKey() {
        return schIkkatsuRemoveKey__;
    }

    /**
     * <p>schIkkatsuRemoveKey をセットします。
     * @param schIkkatsuRemoveKey schIkkatsuRemoveKey
     * @see jp.groupsession.v2.sch.sch010.Sch010Form#schIkkatsuRemoveKey__
     */
    public void setSchIkkatsuRemoveKey(String schIkkatsuRemoveKey) {
        schIkkatsuRemoveKey__ = schIkkatsuRemoveKey;
    }

}
