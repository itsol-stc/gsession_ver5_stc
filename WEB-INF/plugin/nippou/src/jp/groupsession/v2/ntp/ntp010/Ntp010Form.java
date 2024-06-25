package jp.groupsession.v2.ntp.ntp010;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040AddressModel;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] 日報 週間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp010Form extends AbstractGsForm {

    //共通・モード
    /** 処理モード */
    private String cmd__ = null;
    /** 画面モード */
    private String dspMod__ = null;
    /** 画面モード(一覧用) */
    private String listMod__ = null;

    /** 検索キーワード */
    private String ntp010searchWord__;

    //表示条件
    /** 表示開始日付 */
    private String ntp010DspDate__ = null;
    /** 表示グループSID */
    private String ntp010DspGpSid__;
    /** ユーザSID */
    private String ntp010SelectUsrSid__;
    /** ユーザSID */
    private String ntp010SelectUsrAreaSid__;
    /** ユーザ区分 */
    private String ntp010SelectUsrKbn__ = String.valueOf(GSConstNippou.USER_KBN_USER);
    /** 日報登録日付 */
    private String ntp010SelectDate__ = null;
    /** 日報SID */
    private String ntp010NipSid__;
    /** 共有範囲 */
    private int ntp010CrangeKbn__ = GSConst.CRANGE_SHARE_ALL;

    /** 自動リロード時間 */
    private int ntp010Reload__ = GSConstNippou.AUTO_RELOAD_10MIN;

    //表示内容
    /** ヘッダー表示用年月 */
    private String ntp010StrDspDate__ = null;
    /** 週間カレンダー */
    @SuppressWarnings("all")
    private ArrayList ntp010CalendarList__ = null;
    /** グループコンボ */
    private List<NtpLabelValueModel> ntp010GpLabelList__ = null;
    /** 日報上段リスト */
    @SuppressWarnings("all")
    private ArrayList ntp010TopList__ = null;
    /** 日報下段リスト */
    @SuppressWarnings("all")
    private ArrayList ntp010BottomList__ = null;

    /** 一括登録用キー */
    @SuppressWarnings("all")
    private ArrayList rsvSelectedIkkatuKey__ = null;
    /** 一括登録用キー選択値 */
    private String[] rsvIkkatuKey__ = null;
    /** 一括登録用キー作成フラグ */
    private boolean rsvIkkatuFlg__ = true;

    /** 管理者権限有無*/
    private int adminKbn__;
    /** グループ所属有無*/
    private int belongKbn__;
    /** 登録・編集権限*/
    private int authAddEditKbn__;

    //その他プラグインの利用可能状況
    /** 在席管理プラグイン利用可:0・不可:1*/
    private int zaisekiUseOk__ = GSConstNippou.PLUGIN_USE;
    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConstNippou.PLUGIN_USE;
    /** アドレス帳プラグイン利用可:0・不可:1*/
    private int addressUseOk__ = GSConstNippou.PLUGIN_USE;
    /** スケジュールプラグイン利用可:0・不可:1*/
    private int scheduleUseOk__ = GSConstNippou.PLUGIN_USE;
    /** プロジェクトプラグイン利用可:0・不可:1*/
    private int projectUseOk__ = GSConstNippou.PLUGIN_USE;

    /** 遷移元 メイン管理者メニュー画面から遷移した場合:1 その他:0*/
    private int backScreen__ = 0;

    /** パラメータ：案件SID*/
    private int paramAnkenSid__ = 0;

    /** 案件履歴リスト*/
    private List<NtpAnkenModel> ankenHistoryList__ = null;
    /** 企業・顧客履歴リスト*/
    private List<Ntp040AddressModel> companyHistoryList__ = null;

    /** 案件履歴SID*/
    private String ntp010HistoryAnkenSid__;
    /** 企業・顧客履歴 会社SID*/
    private String ntp010HistoryCompSid__;
    /** 企業・顧客履歴 拠点SID*/
    private String ntp010HistoryCompBaseSid__;
    /** セッションユーザSID*/
    private String ntp010SessionUsrId__;

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
     * <p>ntp010searchWord を取得します。
     * @return ntp010searchWord
     */
    public String getNtp010searchWord() {
        return ntp010searchWord__;
    }



    /**
     * <p>ntp010searchWord をセットします。
     * @param ntp010searchWord ntp010searchWord
     */
    public void setNtp010searchWord(String ntp010searchWord) {
        ntp010searchWord__ = ntp010searchWord;
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
     * @return ntp010BottomList を戻します。
     */
    @SuppressWarnings("all")
    public ArrayList getNtp010BottomList() {
        return ntp010BottomList__;
    }
    /**
     * @param ntp010BottomList 設定する ntp010BottomList。
     */
    @SuppressWarnings("all")
    public void setNtp010BottomList(ArrayList ntp010BottomList) {
        ntp010BottomList__ = ntp010BottomList;
    }
    /**
     * @return ntp010CalendarList を戻します。
     */
    @SuppressWarnings("all")
    public ArrayList getNtp010CalendarList() {
        return ntp010CalendarList__;
    }
    /**
     * @param ntp010CalendarList 設定する ntp010CalendarList。
     */
    @SuppressWarnings("all")
    public void setNtp010CalendarList(ArrayList ntp010CalendarList) {
        ntp010CalendarList__ = ntp010CalendarList;
    }



    /**
     * @return ntp010DspDate を戻します。
     */
    public String getNtp010DspDate() {
        return ntp010DspDate__;
    }



    /**
     * @param ntp010DspDate 設定する ntp010DspDate。
     */
    public void setNtp010DspDate(String ntp010DspDate) {
        ntp010DspDate__ = ntp010DspDate;
    }



    /**
     * @return ntp010DspGpSid を戻します。
     */
    public String getNtp010DspGpSid() {
        return ntp010DspGpSid__;
    }



    /**
     * @param ntp010DspGpSid 設定する ntp010DspGpSid。
     */
    public void setNtp010DspGpSid(String ntp010DspGpSid) {
        ntp010DspGpSid__ = ntp010DspGpSid;
    }



    /**
     * @return ntp010GpLabelList を戻します。
     */
    public List <NtpLabelValueModel> getNtp010GpLabelList() {
        return ntp010GpLabelList__;
    }



    /**
     * @param ntp010GpLabelList 設定する ntp010GpLabelList。
     */
    public void setNtp010GpLabelList(List<NtpLabelValueModel> ntp010GpLabelList) {
        ntp010GpLabelList__ = ntp010GpLabelList;
    }



    /**
     * @return ntp010NipSid を戻します。
     */
    public String getNtp010NipSid() {
        return ntp010NipSid__;
    }



    /**
     * @param ntp010NipSid 設定する ntp010NipSid。
     */
    public void setNtp010NipSid(String ntp010NipSid) {
        ntp010NipSid__ = ntp010NipSid;
    }



    /**
     * <p>ntp010CrangeKbnを取得する。
     * @return ntp010CrangeKbn
     * */
    public int getNtp010CrangeKbn() {
        return ntp010CrangeKbn__;
    }



    /**
     * <p>ntp010CrangeKbnをセットする。
     * @param ntp010CrangeKbn ntp010CrangeKbn
     * */
    public void setNtp010CrangeKbn(int ntp010CrangeKbn) {
        ntp010CrangeKbn__ = ntp010CrangeKbn;
    }



    /**
     * @return ntp010SelectDate を戻します。
     */
    public String getNtp010SelectDate() {
        return ntp010SelectDate__;
    }



    /**
     * @param ntp010SelectDate 設定する ntp010SelectDate。
     */
    public void setNtp010SelectDate(String ntp010SelectDate) {
        ntp010SelectDate__ = ntp010SelectDate;
    }



    /**
     * @return ntp010SelectUsrKbn を戻します。
     */
    public String getNtp010SelectUsrKbn() {
        return ntp010SelectUsrKbn__;
    }



    /**
     * @param ntp010SelectUsrKbn 設定する ntp010SelectUsrKbn。
     */
    public void setNtp010SelectUsrKbn(String ntp010SelectUsrKbn) {
        ntp010SelectUsrKbn__ = ntp010SelectUsrKbn;
    }



    /**
     * @return ntp010SelectUsrSid を戻します。
     */
    public String getNtp010SelectUsrSid() {
        return ntp010SelectUsrSid__;
    }



    /**
     * @param ntp010SelectUsrSid 設定する ntp010SelectUsrSid。
     */
    public void setNtp010SelectUsrSid(String ntp010SelectUsrSid) {
        ntp010SelectUsrSid__ = ntp010SelectUsrSid;
    }



    /**
     * @return ntp010StrDspDate を戻します。
     */
    public String getNtp010StrDspDate() {
        return ntp010StrDspDate__;
    }



    /**
     * @param ntp010StrDspDate 設定する ntp010StrDspDate。
     */
    public void setNtp010StrDspDate(String ntp010StrDspDate) {
        ntp010StrDspDate__ = ntp010StrDspDate;
    }



    /**
     * @return ntp010TopList を戻します。
     */
    @SuppressWarnings("all")
    public ArrayList getNtp010TopList() {
        return ntp010TopList__;
    }
    /**
     * @param ntp010TopList 設定する ntp010TopList。
     */
    @SuppressWarnings("all")
    public void setNtp010TopList(ArrayList ntp010TopList) {
        ntp010TopList__ = ntp010TopList;
    }


    /**
     * <p>ntp010Reload を取得します。
     * @return ntp010Reload
     */
    public int getNtp010Reload() {
        return ntp010Reload__;
    }

    /**
     * <p>ntp010Reload をセットします。
     * @param ntp010Reload ntp010Reload
     */
    public void setNtp010Reload(int ntp010Reload) {
        ntp010Reload__ = ntp010Reload;
    }


    /**
     * <p>rsvSelectedIkkatuKey を取得します。
     * @return rsvSelectedIkkatuKey
     */
    @SuppressWarnings("all")
    public ArrayList getRsvSelectedIkkatuKey() {
        return rsvSelectedIkkatuKey__;
    }
    /**
     * <p>rsvSelectedIkkatuKey をセットします。
     * @param rsvSelectedIkkatuKey  rsvSelectedIkkatuKey
     */
    @SuppressWarnings("all")
    public void setRsvSelectedIkkatuKey(ArrayList rsvSelectedIkkatuKey) {
        this.rsvSelectedIkkatuKey__ = rsvSelectedIkkatuKey;
    }

    /**
     * <p>rsvIkkatuKey を取得します。
     * @return rsvIkkatuKey
     */
    public String[] getRsvIkkatuKey() {
        return rsvIkkatuKey__;
    }

    /**
     * <p>rsvIkkatuKey をセットします。
     * @param rsvIkkatuKey  rsvIkkatuKey
     */
    public void setRsvIkkatuKey(String[] rsvIkkatuKey) {
        this.rsvIkkatuKey__ = rsvIkkatuKey;
    }

    /**
     * <p>rsvIkkatuFlg を取得します。
     * @return rsvIkkatuFlg__
     */
    public boolean getRsvIkkatuFlg() {
        return rsvIkkatuFlg__;
    }

    /**
     * <p>rsvIkkatuFlg をセットします。
     * @param rsvIkkatuFlg  rsvIkkatuFlg
     */
    public void setRsvIkkatuFlg(boolean rsvIkkatuFlg) {
        this.rsvIkkatuFlg__ = rsvIkkatuFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return errors エラー
     */
    public ActionErrors validateSelectCheck() {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //未選択チェック
        if (rsvIkkatuKey__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text",
                    GSConstNippou.TEXT_SELECT_NIPPOU);
            StrutsUtil.addMessage(errors, msg, "rsvIkkatuKey");
        } else {
            if (rsvIkkatuKey__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text",
                        GSConstNippou.TEXT_SELECT_NIPPOU);
                StrutsUtil.addMessage(errors, msg, "rsvIkkatuKey");
            }
        }

        return errors;
    }

    /**
     * <p>addressUseOk を取得します。
     * @return addressUseOk
     */
    public int getAddressUseOk() {
        return addressUseOk__;
    }

    /**
     * <p>addressUseOk をセットします。
     * @param addressUseOk addressUseOk
     */
    public void setAddressUseOk(int addressUseOk) {
        addressUseOk__ = addressUseOk;
    }

    /**
     * <p>authAddEditKbn を取得します。
     * @return authAddEditKbn
     */
    public int getAuthAddEditKbn() {
        return authAddEditKbn__;
    }

    /**
     * <p>authAddEditKbn をセットします。
     * @param authAddEditKbn authAddEditKbn
     */
    public void setAuthAddEditKbn(int authAddEditKbn) {
        authAddEditKbn__ = authAddEditKbn;
    }

    /**
     * <p>ntp010SelectUsrAreaSid を取得します。
     * @return ntp010SelectUsrAreaSid
     */
    public String getNtp010SelectUsrAreaSid() {
        return ntp010SelectUsrAreaSid__;
    }

    /**
     * <p>ntp010SelectUsrAreaSid をセットします。
     * @param ntp010SelectUsrAreaSid ntp010SelectUsrAreaSid
     */
    public void setNtp010SelectUsrAreaSid(String ntp010SelectUsrAreaSid) {
        ntp010SelectUsrAreaSid__ = ntp010SelectUsrAreaSid;
    }

    /**
     * <p>paramAnkenSid を取得します。
     * @return paramAnkenSid
     */
    public int getParamAnkenSid() {
        return paramAnkenSid__;
    }

    /**
     * <p>paramAnkenSid をセットします。
     * @param paramAnkenSid paramAnkenSid
     */
    public void setParamAnkenSid(int paramAnkenSid) {
        paramAnkenSid__ = paramAnkenSid;
    }

    /**
     * <p>ankenHistoryList を取得します。
     * @return ankenHistoryList
     */
    public List<NtpAnkenModel> getAnkenHistoryList() {
        return ankenHistoryList__;
    }

    /**
     * <p>ankenHistoryList をセットします。
     * @param ankenHistoryList ankenHistoryList
     */
    public void setAnkenHistoryList(List<NtpAnkenModel> ankenHistoryList) {
        ankenHistoryList__ = ankenHistoryList;
    }

    /**
     * <p>companyHistoryList を取得します。
     * @return companyHistoryList
     */
    public List<Ntp040AddressModel> getCompanyHistoryList() {
        return companyHistoryList__;
    }

    /**
     * <p>companyHistoryList をセットします。
     * @param companyHistoryList companyHistoryList
     */
    public void setCompanyHistoryList(List<Ntp040AddressModel> companyHistoryList) {
        companyHistoryList__ = companyHistoryList;
    }

    /**
     * <p>ntp010HistoryAnkenSid を取得します。
     * @return ntp010HistoryAnkenSid
     */
    public String getNtp010HistoryAnkenSid() {
        return ntp010HistoryAnkenSid__;
    }

    /**
     * <p>ntp010HistoryAnkenSid をセットします。
     * @param ntp010HistoryAnkenSid ntp010HistoryAnkenSid
     */
    public void setNtp010HistoryAnkenSid(String ntp010HistoryAnkenSid) {
        ntp010HistoryAnkenSid__ = ntp010HistoryAnkenSid;
    }

    /**
     * <p>ntp010HistoryCompSid を取得します。
     * @return ntp010HistoryCompSid
     */
    public String getNtp010HistoryCompSid() {
        return ntp010HistoryCompSid__;
    }

    /**
     * <p>ntp010HistoryCompSid をセットします。
     * @param ntp010HistoryCompSid ntp010HistoryCompSid
     */
    public void setNtp010HistoryCompSid(String ntp010HistoryCompSid) {
        ntp010HistoryCompSid__ = ntp010HistoryCompSid;
    }

    /**
     * <p>ntp010HistoryCompBaseSid を取得します。
     * @return ntp010HistoryCompBaseSid
     */
    public String getNtp010HistoryCompBaseSid() {
        return ntp010HistoryCompBaseSid__;
    }

    /**
     * <p>ntp010HistoryCompBaseSid をセットします。
     * @param ntp010HistoryCompBaseSid ntp010HistoryCompBaseSid
     */
    public void setNtp010HistoryCompBaseSid(String ntp010HistoryCompBaseSid) {
        ntp010HistoryCompBaseSid__ = ntp010HistoryCompBaseSid;
    }

    /**
     * <p>ntp010SessionUsrId を取得します。
     * @return ntp010SessionUsrId
     */
    public String getNtp010SessionUsrId() {
        return ntp010SessionUsrId__;
    }

    /**
     * <p>ntp010SessionUsrId をセットします。
     * @param ntp010SessionUsrId ntp010SessionUsrId
     */
    public void setNtp010SessionUsrId(String ntp010SessionUsrId) {
        ntp010SessionUsrId__ = ntp010SessionUsrId;
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
     * <p>projectUseOk を取得します。
     * @return projectUseOk
     */
    public int getProjectUseOk() {
        return projectUseOk__;
    }

    /**
     * <p>projectUseOk をセットします。
     * @param projectUseOk projectUseOk
     */
    public void setProjectUseOk(int projectUseOk) {
        projectUseOk__ = projectUseOk;
    }

}
