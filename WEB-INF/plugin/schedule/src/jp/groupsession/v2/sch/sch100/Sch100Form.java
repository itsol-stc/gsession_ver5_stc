package jp.groupsession.v2.sch.sch100;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.GSValidateSchedule;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.sch.sch030.Sch030Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch100Form extends Sch030Form {


    //表示内容
    /** ヘッダー表示用年月 */
    private String sch100StrDspDate__ = null;
    /** ヘッダーユーザ名称 */
    private String sch100StrUserName__ = null;
    /** ユーザモデル */
    Sch010UsrModel sch100UsrMdl__ = null;
    /** スケジュールリスト */
    private ArrayList<SimpleScheduleModel> sch100ScheduleList__ = null;
    /** 表示ページ */
    private int sch100PageNum__ = 1;
    /** ページ1 */
    private int sch100Slt_page1__;
    /** ページ2 */
    private int sch100Slt_page2__;
    /** ページラベル */
    private ArrayList<LabelValueBean> sch100PageLabel__;

    //検索条件
    /** グループ */
    private String sch100SltGroup__ = null;
    /** ユーザ */
    private String sch100SltUser__ = null;
    /** 開始日from年 */
    private String sch100SltStartYearFr__ = null;
    /** 開始日from月 */
    private String sch100SltStartMonthFr__ = null;
    /** 開始日from日 */
    private String sch100SltStartDayFr__ = null;
    /** 開始日to年 */
    private String sch100SltStartYearTo__ = null;
    /** 開始日to月 */
    private String sch100SltStartMonthTo__ = null;
    /** 開始日to日 */
    private String sch100SltStartDayTo__ = null;
    /** 終了日from年 */
    private String sch100SltEndYearFr__ = null;
    /** 終了日from月 */
    private String sch100SltEndMonthFr__ = null;
    /** 終了日from日 */
    private String sch100SltEndDayFr__ = null;
    /** 終了日to年 */
    private String sch100SltEndYearTo__ = null;
    /** 終了日to月 */
    private String sch100SltEndMonthTo__ = null;
    /** 終了日to日 */
    private String sch100SltEndDayTo__ = null;

    /** 開始From年月日 */
    private String sch100StartFrDate__ = null;
    /** 開始To年月日 */
    private String sch100StartToDate__ = null;
    /** 終了From年月日 */
    private String sch100EndFrDate__ = null;
    /** 終了To年月日 */
    private String sch100EndToDate__ = null;

    /** キーワード検索区分 */
    private String sch100KeyWordkbn__ = String.valueOf(GSConstSchedule.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] sch100SearchTarget__ = null;
    /** タイトルカラー */
    private String[] sch100Bgcolor__ = null;

    /** タイトルカラー区分 */
    private int sch100colorKbn__ = GSConstSchedule.SAD_MSG_NO_ADD;

    /** オーダーキー */
    private int sch100OrderKey1__ = GSConstSchedule.ORDER_KEY_ASC;
    /** ソートキー */
    private int sch100SortKey1__ = GSConstSchedule.SORT_KEY_FRDATE;
    /** オーダーキー2 */
    private int sch100OrderKey2__ = GSConstSchedule.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int sch100SortKey2__ = GSConstSchedule.SORT_KEY_TODATE;

    /** 検索時グループ */
    private String sch100SvSltGroup__ = null;
    /** 検索時ユーザ */
    private String sch100SvSltUser__ = null;
    /** 検索時開始日from年 */
    private String sch100SvSltStartYearFr__ = null;
    /** 検索時開始日from月 */
    private String sch100SvSltStartMonthFr__ = null;
    /** 検索時開始日from日 */
    private String sch100SvSltStartDayFr__ = null;
    /** 検索時開始日to年 */
    private String sch100SvSltStartYearTo__ = null;
    /** 検索時開始日to月 */
    private String sch100SvSltStartMonthTo__ = null;
    /** 検索時開始日to日 */
    private String sch100SvSltStartDayTo__ = null;
    /** 検索時終了日from年 */
    private String sch100SvSltEndYearFr__ = null;
    /** 検索時終了日from月 */
    private String sch100SvSltEndMonthFr__ = null;
    /** 検索時終了日from日 */
    private String sch100SvSltEndDayFr__ = null;
    /** 検索時終了日to年 */
    private String sch100SvSltEndYearTo__ = null;
    /** 検索時終了日to月 */
    private String sch100SvSltEndMonthTo__ = null;
    /** 検索時終了日to日 */
    private String sch100SvSltEndDayTo__ = null;

    /** 検索時キーワード */
    private String sch100SvKeyValue__ = null;
    /** キーワード検索区分 */
    private String sch100SvKeyWordkbn__ = String.valueOf(GSConstSchedule.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] sch100SvSearchTarget__ = null;
    /** オーダーキー */
    private int sch100SvOrderKey1__ = GSConstSchedule.ORDER_KEY_ASC;
    /** ソートキー */
    private int sch100SvSortKey1__ = GSConstSchedule.SORT_KEY_FRDATE;
    /** オーダーキー2 */
    private int sch100SvOrderKey2__ = GSConstSchedule.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int sch100SvSortKey2__ = GSConstSchedule.SORT_KEY_TODATE;
    /** タイトルカラー */
    private String[] sch100SvBgcolor__ = null;

    /** グループラベル */
    private List<SchLabelValueModel> sch100GroupLabel__;
    /** ユーザラベル */
    private List<UsrLabelValueBean> sch100UserLabel__;
    /** ソートキーリスト */
    private List<LabelValueBean> sortKeyList__ = null;
    /** カラーコメントリスト */
    private ArrayList < String > sch100ColorMsgList__ = null;


    /** ユーザId */
    private String sch100SelectUsrSid__ = null;
    /** CSV出力項目 */
    private String[] sch100CsvOutField__ = null;
    /** 選択されたアカウント */
    private int[] sch100SelectScdSid__;

    /** ボタン用の処理モード */
    private String sch100BtnCmd__ = "";
    
    /** リマインダー通知 通知時間リスト */
    private List<LabelValueBean> reminderTimeList__ = new ArrayList<LabelValueBean>();
    

    /**
     * <p>sch100CsvOutField を取得します。
     * @return sch100CsvOutField
     */
    public String[] getSch100CsvOutField() {
        return sch100CsvOutField__;
    }

    /**
     * <p>sch100CsvOutField をセットします。
     * @param sch100CsvOutField sch100CsvOutField
     */
    public void setSch100CsvOutField(String[] sch100CsvOutField) {
        sch100CsvOutField__ = sch100CsvOutField;
    }

    /**
     * 検索条件パラメータをSAVEフィールドへ移行します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {
        sch100SvSltGroup__ = sch100SltGroup__;
        sch100SvSltUser__ = sch100SltUser__;
        sch100SvSltStartYearFr__ = sch100SltStartYearFr__;
        sch100SvSltStartMonthFr__ = sch100SltStartMonthFr__;
        sch100SvSltStartDayFr__ = sch100SltStartDayFr__;
        sch100SvSltStartYearTo__ = sch100SltStartYearTo__;
        sch100SvSltStartMonthTo__ = sch100SltStartMonthTo__;
        sch100SvSltStartDayTo__ = sch100SltStartDayTo__;
        sch100SvSltEndYearFr__ = sch100SltEndYearFr__;
        sch100SvSltEndMonthFr__ = sch100SltEndMonthFr__;
        sch100SvSltEndDayFr__ = sch100SltEndDayFr__;
        sch100SvSltEndYearTo__ = sch100SltEndYearTo__;
        sch100SvSltEndMonthTo__ = sch100SltEndMonthTo__;
        sch100SvSltEndDayTo__ = sch100SltEndDayTo__;
        sch100SvKeyValue__ = getSch010searchWord();
        sch100SvKeyWordkbn__ = sch100KeyWordkbn__;
        sch100SvSearchTarget__ = sch100SearchTarget__;
        sch100SvOrderKey1__ = sch100OrderKey1__;
        sch100SvSortKey1__ = sch100SortKey1__;
        sch100SvOrderKey2__ = sch100OrderKey2__;
        sch100SvSortKey2__ = sch100SortKey2__;
        sch100SvBgcolor__ = sch100Bgcolor__;
    }

    /**
     * <br>[機  能] 編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validatePowerCheck(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        //削除対象未選択チェック
        errors = validateSelectCheck(reqMdl, errors, con);
        if (errors.size() > 0) {
            return errors;
        }
        //同時登録スケジュールの編集権限チェック
        errors = validateSchCheck(reqMdl, errors, con);
        //施設予約エラーチェック
        errors = validateRsvCheck(reqMdl, errors, con);
        return errors;
    }

    /**
     * <br>[機  能] スケジュール一括削除の未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSelectCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (sch100SelectScdSid__ == null || sch100SelectScdSid__.length < 1) {
            String schedule = gsMsg.getMessage("schedule.108");
            msg = new ActionMessage("error.select.required.text", schedule);
            errors.add("error.select.required.text", msg);
        }
        return errors;
    }

    /**
     * <br>[機  能] スケジュールの編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSchCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SchDataDao scdDao = new SchDataDao(con);
        SchDataModel scdMdl = new SchDataModel();
        ScheduleSearchDao searchDao = new ScheduleSearchDao(con);
        SchDao schDao = new SchDao(con);
        CommonBiz cmnBiz = new CommonBiz();
        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
        CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);

        boolean powerFlg = true;
        String edit = gsMsg.getMessage("schedule.src.33");
        String delete = gsMsg.getMessage("cmn.delete");
        int usrErrorCnt = 0;
        int grpErrorCnt = 0;

        for (int i = 0; i < sch100SelectScdSid__.length; i++) {
            scdMdl = scdDao.getEditCheckData(sch100SelectScdSid__[i]);

            if (scdMdl == null) {
                if (errors.isEmpty()) {
                    msg = new ActionMessage("schedule.sch200.21");
                    StrutsUtil.addMessage(errors, msg, "schedule.sch200.21");
                }
                continue;
            }

            List<String> errUsrGrpSidList = new ArrayList<String>();
            //特例アクセス
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //同時登録ユーザ、及びスケジュール登録対象者を設定
                List<CmnUsrmInfModel> usrMdlList =
                        searchDao.getScheduleUsrList(
                        sch100SelectScdSid__[i],
                        scdMdl.getScdUsrSid(),
                        GSConstSchedule.USER_KBN_USER,
                        GSConstSchedule.SSP_AUTHFILTER_NONE,
                        sessionUsrSid);
                usrMdlList.add(usrInfDao.select(scdMdl.getScdUsrSid()));

                for (CmnUsrmInfModel model : usrMdlList) {
                    if (!errUsrGrpSidList.contains(String.valueOf(model.getUsrSid()))) {
                        powerFlg = schDao.canRegistUserSchedule(model.getUsrSid(), sessionUsrSid);
                        if (!powerFlg) {
                            errUsrGrpSidList.add(String.valueOf(model.getUsrSid()));
                            usrErrorCnt++;
                        }
                    }
                }
            } else if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                if (!errUsrGrpSidList.contains("G" + String.valueOf(scdMdl.getScdUsrSid()))) {
                    powerFlg = schDao.canRegistGroupSchedule(scdMdl.getScdUsrSid(), sessionUsrSid);
                    if (!powerFlg) {
                        errUsrGrpSidList.add("G" + String.valueOf(scdMdl.getScdUsrSid()));
                        grpErrorCnt++;
                    }
                }
            }

            String errUsrGrpName = "";
            ArrayList<UsrLabelValueBean> errNameLabel
                = cmnBiz.getUserLabelList(con, errUsrGrpSidList.toArray(
                                                    new String[errUsrGrpSidList.size()]));
            for (UsrLabelValueBean label : errNameLabel) {
                if (errUsrGrpName.length() > 0) {
                    errUsrGrpName += "<br>";
                }
                errUsrGrpName += label.getLabel();
            }

            if (errUsrGrpName.length() > 0) {
                if (usrErrorCnt != 0 && grpErrorCnt != 0) {
                    msg = new ActionMessage("error.cant.delete.usergrpschedule", errUsrGrpName);
                    StrutsUtil.addMessage(errors, msg, "error.cant.delete.usergrpschedule");
                } else if (usrErrorCnt != 0) {
                    msg = new ActionMessage("error.cant.delete.userschedule", errUsrGrpName);
                    StrutsUtil.addMessage(errors, msg, "error.cant.delete.usergrpschedule");
                } else if (grpErrorCnt != 0) {
                    msg = new ActionMessage("error.cant.delete.grpschedule", errUsrGrpName);
                    StrutsUtil.addMessage(errors, msg, "error.cant.delete.usergrpschedule");
                }
                break;
            }

            //閲覧権限チェック
            if (!schCmnBiz.checkViewOk(scdMdl, sessionUsrSid, con)) {
                msg = new ActionMessage("error.edit.power.user", edit, delete);
                StrutsUtil.addMessage(errors, msg, "error.edit.power.user");
                break;
            }

            //編集権限チェック
            powerFlg = schCmnBiz.isAllEditOk(sch100SelectScdSid__[i], con);
            if (!powerFlg) {
                msg = new ActionMessage("error.schedule.edit.cant.user");
                StrutsUtil.addMessage(errors, msg, "error.schedule.edit.cant.user");
                break;
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 同時登録施設予約の編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateRsvCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();
        //変更
        String textChange = gsMsg.getMessage("cmn.change");

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);

        for (int scdSid : sch100SelectScdSid__) {
        
            //編集権限のない施設数を取得する。
            boolean rsvAdmin = cmnBiz.isPluginAdmin(
                    con, usModel, GSConstSchedule.PLUGIN_ID_RESERVE);

            int count = __getCanNotEditRsvCount(
                    scdSid, usModel.getUsrsid(), con, rsvAdmin);

            if (count > 0) {
                //施設予約アクセス権限なし
                msg = new ActionMessage("error.myself.auth");
                StrutsUtil.addMessage(errors, msg, "error.myself.auth");
                return errors;
            }

            //同時登録施設予約の編集権限チェック
            RelationBetweenScdAndRsvChkBiz rsvChkBiz
            = new RelationBetweenScdAndRsvChkBiz(reqMdl, con);
            int errorCd = rsvChkBiz.isRsvEdit(
                    scdSid,
                    RelationBetweenScdAndRsvChkBiz.CHK_KBN_TANITU);
            if (errorCd == RelationBetweenScdAndRsvChkBiz.ERR_CD_SCD_CANNOT_EDIT) {
                //施設予約に対する編集
                String textRsvEdit = gsMsg.getMessage("schedule.src.32");
                msg = new ActionMessage("error.edit.power.user", textRsvEdit, textChange);
                StrutsUtil.addMessage(errors, msg, "reserve.edit");
                return errors;
            }

            //同時登録施設予約の公開権限チェック
            if (!schCmnBiz.checkRsvPublicRange(con, scdSid, usModel.getUsrsid())) {
                String textRsvEdit = gsMsg.getMessage("schedule.src.32");
                msg = new ActionMessage("error.edit.power.user", textRsvEdit, textChange);
                StrutsUtil.addMessage(errors, msg, "reserve.edit");
                return errors;
            }

        }
        return errors;
    }

    /**
     * <br>[機  能] アクセス権限のない施設数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param schSid スケジュールSID
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @param rsvAdmin 施設予約管理者
     * @return count 施設数
     * @throws SQLException SQLExceptionm
     */
    private int __getCanNotEditRsvCount(
            int schSid,
            int sessionUsrSid,
            Connection con,
            boolean rsvAdmin
            ) throws SQLException {

        int count = 0;
        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);

        if (rsvAdmin) {
            return count;
        }

        ArrayList<Integer> allRsdList = schRsvDao.getScheduleReserveData(schSid);
        if (allRsdList == null || allRsdList.size() == 0) {
            return count;
        }

        //施設SIDリストを取得
        ArrayList<Integer> rsdList
        = schRsvDao.getCanEditScheduleReserveData(schSid, sessionUsrSid);

        if (rsdList.size() == allRsdList.size()) {
            return count;
        }

        for (Integer rsdSid : allRsdList) {
            if (!rsdList.contains(rsdSid)) {
                count++;
            }
        }
        return count;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        msgForm.addHiddenParam("CMD", "search");
        msgForm.addHiddenParam("dspMod", getDspMod());
        msgForm.addHiddenParam("sch010DspDate", getSch010DspDate());
        msgForm.addHiddenParam("changeDateFlg", getChangeDateFlg());
        msgForm.addHiddenParam("iniDsp", getIniDsp());
        msgForm.addHiddenParam("sch010SelectUsrSid", getSch010SelectUsrSid());
        msgForm.addHiddenParam("sch010SelectUsrKbn", getSch010SelectUsrKbn());
        msgForm.addHiddenParam("sch010DspGpSid", getSch010DspGpSid());
        msgForm.addHiddenParam("sch010SelectDate", getSch010SelectDate());
        msgForm.addHiddenParam("sch010SchSid", getSch010SchSid());
        msgForm.addHiddenParam("sch010CrangeKbn", getSch010CrangeKbn());
        msgForm.addHiddenParam("sch010searchWord", getSch010searchWord());
        msgForm.addHiddenParam("sch100SvSltGroup", getSch100SvSltGroup());
        msgForm.addHiddenParam("sch100SvSltUser", getSch100SvSltUser());
        msgForm.addHiddenParam("sch100SvSltStartYearFr", getSch100SvSltStartYearFr());
        msgForm.addHiddenParam("sch100SvSltStartMonthFr", getSch100SvSltStartMonthFr());
        msgForm.addHiddenParam("sch100SvSltStartDayFr", getSch100SvSltStartDayFr());
        msgForm.addHiddenParam("sch100SvSltStartYearTo", getSch100SvSltStartYearTo());
        msgForm.addHiddenParam("sch100SvSltStartMonthTo", getSch100SvSltStartMonthTo());
        msgForm.addHiddenParam("sch100SvSltStartDayTo", getSch100SvSltStartDayTo());
        msgForm.addHiddenParam("sch100SvSltEndYearFr", getSch100SvSltEndYearFr());
        msgForm.addHiddenParam("sch100SvSltEndMonthFr", getSch100SvSltEndMonthFr());
        msgForm.addHiddenParam("sch100SvSltEndDayFr", getSch100SvSltEndDayFr());
        msgForm.addHiddenParam("sch100SvSltEndYearTo", getSch100SvSltEndYearTo());
        msgForm.addHiddenParam("sch100SvSltEndMonthTo", getSch100SvSltEndMonthTo());
        msgForm.addHiddenParam("sch100SvSltEndDayTo", getSch100SvSltEndDayTo());
        msgForm.addHiddenParam("sch100SvKeyWordkbn", getSch100SvKeyWordkbn());
        msgForm.addHiddenParam("sch100SvKeyValue", getSch100SvKeyValue());
        msgForm.addHiddenParam("sch100SvOrderKey1", getSch100SvOrderKey1());
        msgForm.addHiddenParam("sch100SvSortKey1", getSch100SvSortKey1());
        msgForm.addHiddenParam("sch100SvOrderKey2", getSch100SvOrderKey2());
        msgForm.addHiddenParam("sch100SvSortKey2", getSch100SvSortKey2());
        msgForm.addHiddenParam("sch100SvSearchTarget", getSch100SvSearchTarget());
        msgForm.addHiddenParam("sch100SvBgcolor", getSch100SvBgcolor());
        msgForm.addHiddenParam("sch100SltGroup", getSch100SltGroup());
        msgForm.addHiddenParam("sch100SltUser", getSch100SltUser());
        msgForm.addHiddenParam("sch100SltStartYearFr", getSch100SltStartYearFr());
        msgForm.addHiddenParam("sch100SltStartMonthFr", getSch100SltStartMonthFr());
        msgForm.addHiddenParam("sch100SltStartDayFr", getSch100SltStartDayFr());
        msgForm.addHiddenParam("sch100SltStartYearTo", getSch100SltStartYearTo());
        msgForm.addHiddenParam("sch100SltStartMonthTo", getSch100SltStartMonthTo());
        msgForm.addHiddenParam("sch100SltStartDayTo", getSch100SltStartDayTo());
        msgForm.addHiddenParam("sch100SltEndYearFr", getSch100SltEndYearFr());
        msgForm.addHiddenParam("sch100SltEndMonthFr", getSch100SltEndMonthFr());
        msgForm.addHiddenParam("sch100SltEndDayFr", getSch100SltEndDayFr());
        msgForm.addHiddenParam("sch100SltEndYearTo", getSch100SltEndYearTo());
        msgForm.addHiddenParam("sch100SltEndMonthTo", getSch100SltEndMonthTo());
        msgForm.addHiddenParam("sch100SltEndDayTo", getSch100SltEndDayTo());
        msgForm.addHiddenParam("sch100KeyWordkbn", getSch100KeyWordkbn());
        msgForm.addHiddenParam("sch100OrderKey1", getSch100OrderKey1());
        msgForm.addHiddenParam("sch100SortKey1", getSch100SortKey1());
        msgForm.addHiddenParam("sch100OrderKey2", getSch100OrderKey2());
        msgForm.addHiddenParam("sch100SortKey2", getSch100SortKey2());
        msgForm.addHiddenParam("sch100SearchTarget", getSch100SearchTarget());
        msgForm.addHiddenParam("sch100Bgcolor", getSch100Bgcolor());
        msgForm.addHiddenParam("sch100PageNum", getSch100PageNum());
        msgForm.addHiddenParam("listMod", getListMod());
        msgForm.addHiddenParam("sch100SelectScdSid", getSch100SelectScdSid());
        msgForm.addHiddenParam("sch100CsvOutField", getSch100CsvOutField());
    }

    /**
     * <p>sch100KeyWordkbn を取得します。
     * @return sch100KeyWordkbn
     */
    public String getSch100KeyWordkbn() {
        return sch100KeyWordkbn__;
    }

    /**
     * <p>sch100KeyWordkbn をセットします。
     * @param sch100KeyWordkbn sch100KeyWordkbn
     */
    public void setSch100KeyWordkbn(String sch100KeyWordkbn) {
        sch100KeyWordkbn__ = sch100KeyWordkbn;
    }

    /**
     * <p>sch100SearchTarget を取得します。
     * @return sch100SearchTarget
     */
    public String[] getSch100SearchTarget() {
        return sch100SearchTarget__;
    }

    /**
     * <p>sch100SearchTarget をセットします。
     * @param sch100SearchTarget sch100SearchTarget
     */
    public void setSch100SearchTarget(String[] sch100SearchTarget) {
        sch100SearchTarget__ = sch100SearchTarget;
    }

    /**
     * <p>sch100OrderKey2 を取得します。
     * @return sch100OrderKey2
     */
    public int getSch100OrderKey2() {
        return sch100OrderKey2__;
    }

    /**
     * <p>sch100OrderKey2 をセットします。
     * @param sch100OrderKey2 sch100OrderKey2
     */
    public void setSch100OrderKey2(int sch100OrderKey2) {
        sch100OrderKey2__ = sch100OrderKey2;
    }

    /**
     * <p>sch100SortKey2 を取得します。
     * @return sch100SortKey2
     */
    public int getSch100SortKey2() {
        return sch100SortKey2__;
    }

    /**
     * <p>sch100SortKey2 をセットします。
     * @param sch100SortKey2 sch100SortKey2
     */
    public void setSch100SortKey2(int sch100SortKey2) {
        sch100SortKey2__ = sch100SortKey2;
    }

    /**
     * <p>sch100SvOrderKey1 を取得します。
     * @return sch100SvOrderKey1
     */
    public int getSch100SvOrderKey1() {
        return sch100SvOrderKey1__;
    }

    /**
     * <p>sch100SvOrderKey1 をセットします。
     * @param sch100SvOrderKey1 sch100SvOrderKey1
     */
    public void setSch100SvOrderKey1(int sch100SvOrderKey1) {
        sch100SvOrderKey1__ = sch100SvOrderKey1;
    }

    /**
     * <p>sch100SvOrderKey2 を取得します。
     * @return sch100SvOrderKey2
     */
    public int getSch100SvOrderKey2() {
        return sch100SvOrderKey2__;
    }

    /**
     * <p>sch100SvOrderKey2 をセットします。
     * @param sch100SvOrderKey2 sch100SvOrderKey2
     */
    public void setSch100SvOrderKey2(int sch100SvOrderKey2) {
        sch100SvOrderKey2__ = sch100SvOrderKey2;
    }

    /**
     * <p>sch100SvSortKey1 を取得します。
     * @return sch100SvSortKey1
     */
    public int getSch100SvSortKey1() {
        return sch100SvSortKey1__;
    }

    /**
     * <p>sch100SvSortKey1 をセットします。
     * @param sch100SvSortKey1 sch100SvSortKey1
     */
    public void setSch100SvSortKey1(int sch100SvSortKey1) {
        sch100SvSortKey1__ = sch100SvSortKey1;
    }

    /**
     * <p>sch100SvSortKey2 を取得します。
     * @return sch100SvSortKey2
     */
    public int getSch100SvSortKey2() {
        return sch100SvSortKey2__;
    }

    /**
     * <p>sch100SvSortKey2 をセットします。
     * @param sch100SvSortKey2 sch100SvSortKey2
     */
    public void setSch100SvSortKey2(int sch100SvSortKey2) {
        sch100SvSortKey2__ = sch100SvSortKey2;
    }

    /**
     * <p>sortKeyList を取得します。
     * @return sortKeyList
     */
    public List<LabelValueBean> getSortKeyList() {
        return sortKeyList__;
    }

    /**
     * <p>sortKeyList をセットします。
     * @param sortKeyList sortKeyList
     */
    public void setSortKeyList(List<LabelValueBean> sortKeyList) {
        sortKeyList__ = sortKeyList;
    }

    /**
     * <p>sch100SvKeyWordkbn を取得します。
     * @return sch100SvKeyWordkbn
     */
    public String getSch100SvKeyWordkbn() {
        return sch100SvKeyWordkbn__;
    }

    /**
     * <p>sch100SvKeyWordkbn をセットします。
     * @param sch100SvKeyWordkbn sch100SvKeyWordkbn
     */
    public void setSch100SvKeyWordkbn(String sch100SvKeyWordkbn) {
        sch100SvKeyWordkbn__ = sch100SvKeyWordkbn;
    }

    /**
     * <p>sch100SvSearchTarget を取得します。
     * @return sch100SvSearchTarget
     */
    public String[] getSch100SvSearchTarget() {
        return sch100SvSearchTarget__;
    }

    /**
     * <p>sch100SvSearchTarget をセットします。
     * @param sch100SvSearchTarget sch100SvSearchTarget
     */
    public void setSch100SvSearchTarget(String[] sch100SvSearchTarget) {
        sch100SvSearchTarget__ = sch100SvSearchTarget;
    }

    /**
     * <p>sch100SvKeyValue を取得します。
     * @return sch100SvKeyValue
     */
    public String getSch100SvKeyValue() {
        return sch100SvKeyValue__;
    }
    /**
     * <p>sch100SvKeyValue をセットします。
     * @param sch100SvKeyValue sch100SvKeyValue
     */
    public void setSch100SvKeyValue(String sch100SvKeyValue) {
        sch100SvKeyValue__ = sch100SvKeyValue;
    }
    /**
     * <p>sch100SvSltEndDayFr を取得します。
     * @return sch100SvSltEndDayFr
     */
    public String getSch100SvSltEndDayFr() {
        return sch100SvSltEndDayFr__;
    }
    /**
     * <p>sch100SvSltEndDayFr をセットします。
     * @param sch100SvSltEndDayFr sch100SvSltEndDayFr
     */
    public void setSch100SvSltEndDayFr(String sch100SvSltEndDayFr) {
        sch100SvSltEndDayFr__ = sch100SvSltEndDayFr;
    }
    /**
     * <p>sch100SvSltEndDayTo を取得します。
     * @return sch100SvSltEndDayTo
     */
    public String getSch100SvSltEndDayTo() {
        return sch100SvSltEndDayTo__;
    }
    /**
     * <p>sch100SvSltEndDayTo をセットします。
     * @param sch100SvSltEndDayTo sch100SvSltEndDayTo
     */
    public void setSch100SvSltEndDayTo(String sch100SvSltEndDayTo) {
        sch100SvSltEndDayTo__ = sch100SvSltEndDayTo;
    }
    /**
     * <p>sch100SvSltEndMonthFr を取得します。
     * @return sch100SvSltEndMonthFr
     */
    public String getSch100SvSltEndMonthFr() {
        return sch100SvSltEndMonthFr__;
    }
    /**
     * <p>sch100SvSltEndMonthFr をセットします。
     * @param sch100SvSltEndMonthFr sch100SvSltEndMonthFr
     */
    public void setSch100SvSltEndMonthFr(String sch100SvSltEndMonthFr) {
        sch100SvSltEndMonthFr__ = sch100SvSltEndMonthFr;
    }
    /**
     * <p>sch100SvSltEndMonthTo を取得します。
     * @return sch100SvSltEndMonthTo
     */
    public String getSch100SvSltEndMonthTo() {
        return sch100SvSltEndMonthTo__;
    }
    /**
     * <p>sch100SvSltEndMonthTo をセットします。
     * @param sch100SvSltEndMonthTo sch100SvSltEndMonthTo
     */
    public void setSch100SvSltEndMonthTo(String sch100SvSltEndMonthTo) {
        sch100SvSltEndMonthTo__ = sch100SvSltEndMonthTo;
    }
    /**
     * <p>sch100SvSltEndYearFr を取得します。
     * @return sch100SvSltEndYearFr
     */
    public String getSch100SvSltEndYearFr() {
        return sch100SvSltEndYearFr__;
    }
    /**
     * <p>sch100SvSltEndYearFr をセットします。
     * @param sch100SvSltEndYearFr sch100SvSltEndYearFr
     */
    public void setSch100SvSltEndYearFr(String sch100SvSltEndYearFr) {
        sch100SvSltEndYearFr__ = sch100SvSltEndYearFr;
    }
    /**
     * <p>sch100SvSltEndYearTo を取得します。
     * @return sch100SvSltEndYearTo
     */
    public String getSch100SvSltEndYearTo() {
        return sch100SvSltEndYearTo__;
    }
    /**
     * <p>sch100SvSltEndYearTo をセットします。
     * @param sch100SvSltEndYearTo sch100SvSltEndYearTo
     */
    public void setSch100SvSltEndYearTo(String sch100SvSltEndYearTo) {
        sch100SvSltEndYearTo__ = sch100SvSltEndYearTo;
    }
    /**
     * <p>sch100SvSltGroup を取得します。
     * @return sch100SvSltGroup
     */
    public String getSch100SvSltGroup() {
        return sch100SvSltGroup__;
    }
    /**
     * <p>sch100SvSltGroup をセットします。
     * @param sch100SvSltGroup sch100SvSltGroup
     */
    public void setSch100SvSltGroup(String sch100SvSltGroup) {
        sch100SvSltGroup__ = sch100SvSltGroup;
    }
    /**
     * <p>sch100SvSltStartDayFr を取得します。
     * @return sch100SvSltStartDayFr
     */
    public String getSch100SvSltStartDayFr() {
        return sch100SvSltStartDayFr__;
    }
    /**
     * <p>sch100SvSltStartDayFr をセットします。
     * @param sch100SvSltStartDayFr sch100SvSltStartDayFr
     */
    public void setSch100SvSltStartDayFr(String sch100SvSltStartDayFr) {
        sch100SvSltStartDayFr__ = sch100SvSltStartDayFr;
    }
    /**
     * <p>sch100SvSltStartDayTo を取得します。
     * @return sch100SvSltStartDayTo
     */
    public String getSch100SvSltStartDayTo() {
        return sch100SvSltStartDayTo__;
    }
    /**
     * <p>sch100SvSltStartDayTo をセットします。
     * @param sch100SvSltStartDayTo sch100SvSltStartDayTo
     */
    public void setSch100SvSltStartDayTo(String sch100SvSltStartDayTo) {
        sch100SvSltStartDayTo__ = sch100SvSltStartDayTo;
    }
    /**
     * <p>sch100SvSltStartMonthFr を取得します。
     * @return sch100SvSltStartMonthFr
     */
    public String getSch100SvSltStartMonthFr() {
        return sch100SvSltStartMonthFr__;
    }
    /**
     * <p>sch100SvSltStartMonthFr をセットします。
     * @param sch100SvSltStartMonthFr sch100SvSltStartMonthFr
     */
    public void setSch100SvSltStartMonthFr(String sch100SvSltStartMonthFr) {
        sch100SvSltStartMonthFr__ = sch100SvSltStartMonthFr;
    }
    /**
     * <p>sch100SvSltStartMonthTo を取得します。
     * @return sch100SvSltStartMonthTo
     */
    public String getSch100SvSltStartMonthTo() {
        return sch100SvSltStartMonthTo__;
    }
    /**
     * <p>sch100SvSltStartMonthTo をセットします。
     * @param sch100SvSltStartMonthTo sch100SvSltStartMonthTo
     */
    public void setSch100SvSltStartMonthTo(String sch100SvSltStartMonthTo) {
        sch100SvSltStartMonthTo__ = sch100SvSltStartMonthTo;
    }
    /**
     * <p>sch100SvSltStartYearFr を取得します。
     * @return sch100SvSltStartYearFr
     */
    public String getSch100SvSltStartYearFr() {
        return sch100SvSltStartYearFr__;
    }
    /**
     * <p>sch100SvSltStartYearFr をセットします。
     * @param sch100SvSltStartYearFr sch100SvSltStartYearFr
     */
    public void setSch100SvSltStartYearFr(String sch100SvSltStartYearFr) {
        sch100SvSltStartYearFr__ = sch100SvSltStartYearFr;
    }
    /**
     * <p>sch100SvSltStartYearTo を取得します。
     * @return sch100SvSltStartYearTo
     */
    public String getSch100SvSltStartYearTo() {
        return sch100SvSltStartYearTo__;
    }
    /**
     * <p>sch100SvSltStartYearTo をセットします。
     * @param sch100SvSltStartYearTo sch100SvSltStartYearTo
     */
    public void setSch100SvSltStartYearTo(String sch100SvSltStartYearTo) {
        sch100SvSltStartYearTo__ = sch100SvSltStartYearTo;
    }
    /**
     * <p>sch100SvSltUser を取得します。
     * @return sch100SvSltUser
     */
    public String getSch100SvSltUser() {
        return sch100SvSltUser__;
    }
    /**
     * <p>sch100SvSltUser をセットします。
     * @param sch100SvSltUser sch100SvSltUser
     */
    public void setSch100SvSltUser(String sch100SvSltUser) {
        sch100SvSltUser__ = sch100SvSltUser;
    }
    /**
     * <p>sch100GroupLabel を取得します。
     * @return sch100GroupLabel
     */
    public List<SchLabelValueModel> getSch100GroupLabel() {
        return sch100GroupLabel__;
    }
    /**
     * <p>sch100GroupLabel をセットします。
     * @param sch100GroupLabel sch100GroupLabel
     */
    public void setSch100GroupLabel(List<SchLabelValueModel> sch100GroupLabel) {
        sch100GroupLabel__ = sch100GroupLabel;
    }

    /**
     * <p>sch100SltEndDayFr を取得します。
     * @return sch100SltEndDayFr
     */
    public String getSch100SltEndDayFr() {
        return sch100SltEndDayFr__;
    }
    /**
     * <p>sch100SltEndDayFr をセットします。
     * @param sch100SltEndDayFr sch100SltEndDayFr
     */
    public void setSch100SltEndDayFr(String sch100SltEndDayFr) {
        sch100SltEndDayFr__ = sch100SltEndDayFr;
    }
    /**
     * <p>sch100SltEndDayTo を取得します。
     * @return sch100SltEndDayTo
     */
    public String getSch100SltEndDayTo() {
        return sch100SltEndDayTo__;
    }
    /**
     * <p>sch100SltEndDayTo をセットします。
     * @param sch100SltEndDayTo sch100SltEndDayTo
     */
    public void setSch100SltEndDayTo(String sch100SltEndDayTo) {
        sch100SltEndDayTo__ = sch100SltEndDayTo;
    }
    /**
     * <p>sch100SltEndMonthFr を取得します。
     * @return sch100SltEndMonthFr
     */
    public String getSch100SltEndMonthFr() {
        return sch100SltEndMonthFr__;
    }
    /**
     * <p>sch100SltEndMonthFr をセットします。
     * @param sch100SltEndMonthFr sch100SltEndMonthFr
     */
    public void setSch100SltEndMonthFr(String sch100SltEndMonthFr) {
        sch100SltEndMonthFr__ = sch100SltEndMonthFr;
    }
    /**
     * <p>sch100SltEndMonthTo を取得します。
     * @return sch100SltEndMonthTo
     */
    public String getSch100SltEndMonthTo() {
        return sch100SltEndMonthTo__;
    }
    /**
     * <p>sch100SltEndMonthTo をセットします。
     * @param sch100SltEndMonthTo sch100SltEndMonthTo
     */
    public void setSch100SltEndMonthTo(String sch100SltEndMonthTo) {
        sch100SltEndMonthTo__ = sch100SltEndMonthTo;
    }
    /**
     * <p>sch100SltEndYearFr を取得します。
     * @return sch100SltEndYearFr
     */
    public String getSch100SltEndYearFr() {
        return sch100SltEndYearFr__;
    }
    /**
     * <p>sch100SltEndYearFr をセットします。
     * @param sch100SltEndYearFr sch100SltEndYearFr
     */
    public void setSch100SltEndYearFr(String sch100SltEndYearFr) {
        sch100SltEndYearFr__ = sch100SltEndYearFr;
    }
    /**
     * <p>sch100SltEndYearTo を取得します。
     * @return sch100SltEndYearTo
     */
    public String getSch100SltEndYearTo() {
        return sch100SltEndYearTo__;
    }
    /**
     * <p>sch100SltEndYearTo をセットします。
     * @param sch100SltEndYearTo sch100SltEndYearTo
     */
    public void setSch100SltEndYearTo(String sch100SltEndYearTo) {
        sch100SltEndYearTo__ = sch100SltEndYearTo;
    }
    /**
     * <p>sch100SltGroup を取得します。
     * @return sch100SltGroup
     */
    public String getSch100SltGroup() {
        return sch100SltGroup__;
    }
    /**
     * <p>sch100SltGroup をセットします。
     * @param sch100SltGroup sch100SltGroup
     */
    public void setSch100SltGroup(String sch100SltGroup) {
        sch100SltGroup__ = sch100SltGroup;
    }
    /**
     * <p>sch100SltStartDayFr を取得します。
     * @return sch100SltStartDayFr
     */
    public String getSch100SltStartDayFr() {
        return sch100SltStartDayFr__;
    }
    /**
     * <p>sch100SltStartDayFr をセットします。
     * @param sch100SltStartDayFr sch100SltStartDayFr
     */
    public void setSch100SltStartDayFr(String sch100SltStartDayFr) {
        sch100SltStartDayFr__ = sch100SltStartDayFr;
    }
    /**
     * <p>sch100SltStartDayTo を取得します。
     * @return sch100SltStartDayTo
     */
    public String getSch100SltStartDayTo() {
        return sch100SltStartDayTo__;
    }
    /**
     * <p>sch100SltStartDayTo をセットします。
     * @param sch100SltStartDayTo sch100SltStartDayTo
     */
    public void setSch100SltStartDayTo(String sch100SltStartDayTo) {
        sch100SltStartDayTo__ = sch100SltStartDayTo;
    }
    /**
     * <p>sch100SltStartMonthFr を取得します。
     * @return sch100SltStartMonthFr
     */
    public String getSch100SltStartMonthFr() {
        return sch100SltStartMonthFr__;
    }
    /**
     * <p>sch100SltStartMonthFr をセットします。
     * @param sch100SltStartMonthFr sch100SltStartMonthFr
     */
    public void setSch100SltStartMonthFr(String sch100SltStartMonthFr) {
        sch100SltStartMonthFr__ = sch100SltStartMonthFr;
    }
    /**
     * <p>sch100SltStartMonthTo を取得します。
     * @return sch100SltStartMonthTo
     */
    public String getSch100SltStartMonthTo() {
        return sch100SltStartMonthTo__;
    }
    /**
     * <p>sch100SltStartMonthTo をセットします。
     * @param sch100SltStartMonthTo sch100SltStartMonthTo
     */
    public void setSch100SltStartMonthTo(String sch100SltStartMonthTo) {
        sch100SltStartMonthTo__ = sch100SltStartMonthTo;
    }
    /**
     * <p>sch100SltStartYearFr を取得します。
     * @return sch100SltStartYearFr
     */
    public String getSch100SltStartYearFr() {
        return sch100SltStartYearFr__;
    }
    /**
     * <p>sch100SltStartYearFr をセットします。
     * @param sch100SltStartYearFr sch100SltStartYearFr
     */
    public void setSch100SltStartYearFr(String sch100SltStartYearFr) {
        sch100SltStartYearFr__ = sch100SltStartYearFr;
    }
    /**
     * <p>sch100SltStartYearTo を取得します。
     * @return sch100SltStartYearTo
     */
    public String getSch100SltStartYearTo() {
        return sch100SltStartYearTo__;
    }
    /**
     * <p>sch100SltStartYearTo をセットします。
     * @param sch100SltStartYearTo sch100SltStartYearTo
     */
    public void setSch100SltStartYearTo(String sch100SltStartYearTo) {
        sch100SltStartYearTo__ = sch100SltStartYearTo;
    }
    /**
     * <p>sch100SltUser を取得します。
     * @return sch100SltUser
     */
    public String getSch100SltUser() {
        return sch100SltUser__;
    }
    /**
     * <p>sch100SltUser をセットします。
     * @param sch100SltUser sch100SltUser
     */
    public void setSch100SltUser(String sch100SltUser) {
        sch100SltUser__ = sch100SltUser;
    }
    /**
     * <p>sch100UserLabel を取得します。
     * @return sch100UserLabel
     */
    public List<UsrLabelValueBean> getSch100UserLabel() {
        return sch100UserLabel__;
    }
    /**
     * <p>sch100UserLabel をセットします。
     * @param sch100UserLabel sch100UserLabel
     */
    public void setSch100UserLabel(List<UsrLabelValueBean> sch100UserLabel) {
        sch100UserLabel__ = sch100UserLabel;
    }
    /**
     * <p>sch100PageLabel を取得します。
     * @return sch100PageLabel
     */
    public ArrayList<LabelValueBean> getSch100PageLabel() {
        return sch100PageLabel__;
    }
    /**
     * <p>sch100PageLabel をセットします。
     * @param sch100PageLabel sch100PageLabel
     */
    public void setSch100PageLabel(ArrayList<LabelValueBean> sch100PageLabel) {
        sch100PageLabel__ = sch100PageLabel;
    }
    /**
     * <p>sch100Slt_page1 を取得します。
     * @return sch100Slt_page1
     */
    public int getSch100Slt_page1() {
        return sch100Slt_page1__;
    }
    /**
     * <p>sch100Slt_page1 をセットします。
     * @param sch100SltPage1 sch100Slt_page1
     */
    public void setSch100Slt_page1(int sch100SltPage1) {
        sch100Slt_page1__ = sch100SltPage1;
    }
    /**
     * <p>sch100Slt_page2 を取得します。
     * @return sch100Slt_page2
     */
    public int getSch100Slt_page2() {
        return sch100Slt_page2__;
    }
    /**
     * <p>sch100Slt_page2 をセットします。
     * @param sch100SltPage2 sch100Slt_page2
     */
    public void setSch100Slt_page2(int sch100SltPage2) {
        sch100Slt_page2__ = sch100SltPage2;
    }
    /**
     * <p>sch100PageNum を取得します。
     * @return sch100PageNum
     */
    public int getSch100PageNum() {
        return sch100PageNum__;
    }
    /**
     * <p>sch100PageNum をセットします。
     * @param sch100PageNum sch100PageNum
     */
    public void setSch100PageNum(int sch100PageNum) {
        sch100PageNum__ = sch100PageNum;
    }
    /**
     * <p>sch100OrderKey を取得します。
     * @return sch100OrderKey
     */
    public int getSch100OrderKey1() {
        return sch100OrderKey1__;
    }
    /**
     * <p>sch100OrderKey をセットします。
     * @param sch100OrderKey sch100OrderKey
     */
    public void setSch100OrderKey1(int sch100OrderKey) {
        sch100OrderKey1__ = sch100OrderKey;
    }
    /**
     * <p>sch100SortKey を取得します。
     * @return sch100SortKey
     */
    public int getSch100SortKey1() {
        return sch100SortKey1__;
    }
    /**
     * <p>sch100SortKey をセットします。
     * @param sch100SortKey sch100SortKey
     */
    public void setSch100SortKey1(int sch100SortKey) {
        sch100SortKey1__ = sch100SortKey;
    }
    /**
     * <p>sch100UsrMdl を取得します。
     * @return sch100UsrMdl
     */
    public Sch010UsrModel getSch100UsrMdl() {
        return sch100UsrMdl__;
    }
    /**
     * <p>sch100UsrMdl をセットします。
     * @param sch100UsrMdl sch100UsrMdl
     */
    public void setSch100UsrMdl(Sch010UsrModel sch100UsrMdl) {
        sch100UsrMdl__ = sch100UsrMdl;
    }
    /**
     * <p>sch100ScheduleList を取得します。
     * @return sch100ScheduleList
     */
    public ArrayList<SimpleScheduleModel> getSch100ScheduleList() {
        return sch100ScheduleList__;
    }
    /**
     * <p>sch100ScheduleList をセットします。
     * @param sch100ScheduleList sch100ScheduleList
     */
    public void setSch100ScheduleList(ArrayList<SimpleScheduleModel> sch100ScheduleList) {
        sch100ScheduleList__ = sch100ScheduleList;
    }
    /**
     * <p>sch100StrDspDate を取得します。
     * @return sch100StrDspDate
     */
    public String getSch100StrDspDate() {
        return sch100StrDspDate__;
    }
    /**
     * <p>sch100StrDspDate をセットします。
     * @param sch100StrDspDate sch100StrDspDate
     */
    public void setSch100StrDspDate(String sch100StrDspDate) {
        sch100StrDspDate__ = sch100StrDspDate;
    }
    /**
     * <p>sch100StrUserName を取得します。
     * @return sch100StrUserName
     */
    public String getSch100StrUserName() {
        return sch100StrUserName__;
    }
    /**
     * <p>sch100StrUserName をセットします。
     * @param sch100StrUserName sch100StrUserName
     */
    public void setSch100StrUserName(String sch100StrUserName) {
        sch100StrUserName__ = sch100StrUserName;
    }

    /**
     * <br>[機  能] CSV出力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors
     */
    public ActionErrors validateCsvOutCheck(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        String eprefix = "schedule";

        //CSV出力項目にチェック１つもないとき
        if (getSch100CsvOutField() == null) {
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(req, "reserve.output.item"));
            StrutsUtil.addMessage(errors, msg, eprefix + "export");
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return エラー
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validateSch100Check(ActionMapping map, HttpServletRequest req)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        int errSize = 0;

        //開始年月日チェックフラグ(true=入力OK、false=NG)
        boolean fromOk = false;
        UDate sFrDate = new UDate();
        String strFrDateNameJp = gsMsg.getMessage("schedule.src.69");

        errors.add(picker.setYmdParam(this, "sch100StartFrDate",
                "sch100SltStartYearFr", "sch100SltStartMonthFr",
                "sch100SltStartDayFr", strFrDateNameJp));
        if (errSize == errors.size()) {
            int sFYear = Integer.parseInt(sch100SltStartYearFr__);
            int sFMonth = Integer.parseInt(sch100SltStartMonthFr__);
            int sFDay = Integer.parseInt(sch100SltStartDayFr__);

            sFrDate.setDate(sFYear, sFMonth, sFDay);
            sFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            sFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (sFrDate.getYear() != sFYear
            || sFrDate.getMonth() != sFMonth
            || sFrDate.getIntDay() != sFDay) {
                //開始日付：開始
                String textStartDateStart = gsMsg.getMessage(req, "schedule.src.69");
                msg = new ActionMessage("error.input.notfound.date", textStartDateStart);
                errors.add("error.input.notfound.date", msg);
            } else {
                fromOk = true;
            }
        }

        //終了年月日チェックフラグ(true=入力OK、false=NG)
        boolean toOk = false;
        UDate sToDate = new UDate();
        String strToDateNameJp = gsMsg.getMessage("schedule.src.71");

        errors.add(picker.setYmdParam(this, "sch100StartToDate",
                "sch100SltStartYearTo", "sch100SltStartMonthTo",
                "sch100SltStartDayTo", strToDateNameJp));
        if (errSize == errors.size()) {
            int sTYear = Integer.parseInt(sch100SltStartYearTo__);
            int sTMonth = Integer.parseInt(sch100SltStartMonthTo__);
            int sTDay = Integer.parseInt(sch100SltStartDayTo__);

            sToDate.setDate(sTYear, sTMonth, sTDay);
            sToDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            sToDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (sToDate.getYear() != sTYear
            || sToDate.getMonth() != sTMonth
            || sToDate.getIntDay() != sTDay) {
                //開始日付：終了
                String textStartDateEnd = gsMsg.getMessage(req, "schedule.src.71");
                msg = new ActionMessage("error.input.notfound.date", textStartDateEnd);
                errors.add("error.input.notfound.date", msg);
            } else {
                toOk = true;
            }
        }
        errSize = errors.size();

        //個別チェックOKの場合
        if (fromOk && toOk) {
            //from～to大小チェック
            if (sFrDate.compareDateYMD(sToDate) == UDate.SMALL) {
                //開始 <= 終了
                String textStartOrEnd = gsMsg.getMessage(req, "cmn.start.or.end");
                //開始日付：開始・終了
                String textStartDateStartEnd = gsMsg.getMessage(req, "schedule.src.70");
                msg = new ActionMessage("error.input.comp.text",
                        textStartDateStartEnd, textStartOrEnd);
                errors.add("" + "error.input.comp.text", msg);
            }
        }

        //開始年月日チェックフラグ(true=入力OK、false=NG)
        boolean eFromOk = false;
        UDate eFrDate = new UDate();
        String endFrDateNameJp = gsMsg.getMessage("schedule.src.72");

        errors.add(picker.setYmdParam(this, "sch100EndFrDate",
                "sch100SltEndYearFr", "sch100SltEndMonthFr",
                "sch100SltEndDayFr", endFrDateNameJp));
        if (errSize == errors.size()) {
            int eFYear = Integer.parseInt(sch100SltEndYearFr__);
            int eFMonth = Integer.parseInt(sch100SltEndMonthFr__);
            int eFDay = Integer.parseInt(sch100SltEndDayFr__);

            eFrDate.setDate(eFYear, eFMonth, eFDay);
            eFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            eFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (eFrDate.getYear() != eFYear
            || eFrDate.getMonth() != eFMonth
            || eFrDate.getIntDay() != eFDay) {
                //終了日付：開始
                String textEndDateStart = gsMsg.getMessage(req, "schedule.src.72");
                msg = new ActionMessage("error.input.notfound.date", textEndDateStart);
                errors.add("error.input.notfound.date", msg);
            } else {
                eFromOk = true;
            }
        }

        //終了年月日チェックフラグ(true=入力OK、false=NG)
        boolean eToOk = false;
        UDate eToDate = new UDate();
        String endToDateNameJp = gsMsg.getMessage("schedule.src.75");

        errors.add(picker.setYmdParam(this, "sch100EndToDate",
                "sch100SltEndYearTo", "sch100SltEndMonthTo",
                "sch100SltEndDayTo", endToDateNameJp));
        if (errSize == errors.size()) {
            int eTYear = Integer.parseInt(sch100SltEndYearTo__);
            int eTMonth = Integer.parseInt(sch100SltEndMonthTo__);
            int eTDay = Integer.parseInt(sch100SltEndDayTo__);

            eToDate.setDate(eTYear, eTMonth, eTDay);
            eToDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            eToDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (eToDate.getYear() != eTYear
            || eToDate.getMonth() != eTMonth
            || eToDate.getIntDay() != eTDay) {
                //終了日付：終了
                String textEndDateEnd = gsMsg.getMessage(req, "schedule.src.75");
                msg = new ActionMessage("error.input.notfound.date", textEndDateEnd);
                errors.add("error.input.notfound.date", msg);
            } else {
                eToOk = true;
            }
        }

        //個別チェックOKの場合
        if (eFromOk && eToOk) {
            //from～to大小チェック
            //終了日付：開始・終了
            String textEndDateStartEnd = gsMsg.getMessage(req, "schedule.src.73");
            if (eFrDate.compareDateYMD(eToDate) == UDate.SMALL) {
                //開始 <= 終了
                String textStartOrEnd = gsMsg.getMessage(req, "cmn.start.or.end");
                msg = new ActionMessage("error.input.comp.text",
                                             textEndDateStartEnd, textStartOrEnd);
                errors.add("" + "error.input.comp.text", msg);
            }
        }
        /** メッセージ キーワード **/
        String textKeyword = gsMsg.getMessage(req, "cmn.keyword");

        if (!StringUtil.isNullZeroString(getSch010searchWord())) {
            //内容のチェック
            if (__checkRange(
                    errors,
                    getSch010searchWord(),
                    "sch100KeyValue",
                    textKeyword,
                    GSConstSchedule.MAX_LENGTH_TITLE)) {

                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(getSch010searchWord())) {
                    msg = new ActionMessage("error.input.spase.start",
                                            textKeyword);
                    StrutsUtil.addMessage(errors, msg, "sch100KeyValue__");
                } else {
                    __checkJisString(
                            errors,
                            getSch010searchWord(),
                            "sch100KeyValue__",
                            textKeyword);
                }
            }
        }
        //ＫＥＹワードチェック
        validateSearchTarget(errors, getSch010searchWord(), sch100SearchTarget__, req);

//        //タイトルカラー
//        String textTitileColor = gsMsg.getMessage(req, "schedule.128");
//        if (sch100Bgcolor__ == null || sch100Bgcolor__.length < 1) {
//            msg = new ActionMessage("error.select.required.text", textTitileColor);
//            errors.add("error.select.required.text", msg);
//        }

        //ソート順
        String textSort = gsMsg.getMessage(req, "cmn.sort.order");
        if (sch100SortKey1__ == sch100SortKey2__) {
            msg = new ActionMessage("error.select.dup.list",
                    textSort);
            errors.add("error.select.dup.list", msg);

        }
        return errors;
    }
    /**
    *
    * <br>[機  能] 検索条件パラメータ（SV）チェック
    * <br>[解  説]
    * <br>[備  考]
    * @return false : 不正なパラメータあり
    */
   public boolean validateSch100Research() {
       // 検索時グループ
       if (StringUtil.isNullZeroString(sch100SvSltGroup__)) {
           return false;
       }
       if (sch100SvSltGroup__.startsWith(GSConstSchedule.MY_GROUP_STRING)
               || sch100SvSltGroup__.startsWith(GSConstSchedule.DSP_LIST_STRING)) {
           if (!ValidateUtil.isNumberHaifun(
                       sch100SvSltGroup__.substring(GSConstSchedule.MY_GROUP_STRING.length()))
               && !ValidateUtil.isNumberHaifun(
                       sch100SvSltGroup__.substring(GSConstSchedule.DSP_LIST_STRING.length()))) {
               return false;
           }
       } else if (!ValidateUtil.isNumberHaifun(
                       sch100SvSltGroup__)) {
           return false;
       }

       // 検索時ユーザ
       if (sch100SvSltUser__.startsWith("G")) {
           if (!ValidateUtil.isNumberHaifun(sch100SvSltUser__.substring(1))) {
               return false;
           }
       } else if (!ValidateUtil.isNumberHaifun(
               sch100SvSltUser__)) {
           return false;
       }

       // 検索時開始日from年
       if (!ValidateUtil.isNumber(
               sch100SvSltStartYearFr__)) {
           return false;
       }
       // 検索時開始日from月
       if (!ValidateUtil.isNumber(
               sch100SvSltStartMonthFr__)) {
           return false;
       }
       // 検索時開始日from日
       if (!ValidateUtil.isNumber(
           sch100SvSltStartDayFr__)) {
            return false;
       }

       // 検索時開始日to年
       if (!ValidateUtil.isNumber(
           sch100SvSltStartYearTo__)) {
            return false;
       }

       // 検索時開始日to月
       if (!ValidateUtil.isNumber(
           sch100SvSltStartMonthTo__)) {
            return false;
       }

       // 検索時開始日to日
       if (!ValidateUtil.isNumber(
           sch100SvSltStartDayTo__)) {
            return false;
       }

       // 検索時終了日from年
       if (!ValidateUtil.isNumber(
           sch100SvSltEndYearFr__)) {
            return false;
       }

       // 検索時終了日from月
       if (!ValidateUtil.isNumber(
           sch100SvSltEndMonthFr__)) {
            return false;
       }

       // 検索時終了日from日
       if (!ValidateUtil.isNumber(
           sch100SvSltEndDayFr__)) {
            return false;
       }

       // 検索時終了日to年
       if (!ValidateUtil.isNumber(
           sch100SvSltEndYearTo__)) {
            return false;
       }

       // 検索時終了日to月
       if (!ValidateUtil.isNumber(
           sch100SvSltEndMonthTo__)) {
            return false;
       }

       // 検索時終了日to日
       if (!ValidateUtil.isNumber(
           sch100SvSltEndDayTo__)) {
            return false;
       }
       int sFYear = Integer.parseInt(sch100SvSltStartYearFr__);
       int sFMonth = Integer.parseInt(sch100SvSltStartMonthFr__);
       int sFDay = Integer.parseInt(sch100SvSltStartDayFr__);

       UDate sFrDate = new UDate();
       sFrDate.setDate(sFYear, sFMonth, sFDay);
       sFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
       sFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
       if (sFrDate.getYear() != sFYear
       || sFrDate.getMonth() != sFMonth
       || sFrDate.getIntDay() != sFDay) {
           return false;
       }

       //終了年月日チェックフラグ(true=入力OK、false=NG)
       int sTYear = Integer.parseInt(sch100SvSltStartYearTo__);
       int sTMonth = Integer.parseInt(sch100SvSltStartMonthTo__);
       int sTDay = Integer.parseInt(sch100SvSltStartDayTo__);

       UDate sToDate = new UDate();
       sToDate.setDate(sTYear, sTMonth, sTDay);
       sToDate.setSecond(GSConstSchedule.DAY_START_SECOND);
       sToDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
       if (sToDate.getYear() != sTYear
       || sToDate.getMonth() != sTMonth
       || sToDate.getIntDay() != sTDay) {
           return false;
       }

       //個別チェックOKの場合
       //from～to大小チェック
       if (sFrDate.compareDateYMD(sToDate) == UDate.SMALL) {
           return false;
       }

       int eFYear = Integer.parseInt(sch100SvSltEndYearFr__);
       int eFMonth = Integer.parseInt(sch100SvSltEndMonthFr__);
       int eFDay = Integer.parseInt(sch100SvSltEndDayFr__);

       UDate eFrDate = new UDate();
       eFrDate.setDate(eFYear, eFMonth, eFDay);
       eFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
       eFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
       if (eFrDate.getYear() != eFYear
       || eFrDate.getMonth() != eFMonth
       || eFrDate.getIntDay() != eFDay) {
           return false;
       }

       int eTYear = Integer.parseInt(sch100SvSltEndYearTo__);
       int eTMonth = Integer.parseInt(sch100SvSltEndMonthTo__);
       int eTDay = Integer.parseInt(sch100SvSltEndDayTo__);

       UDate eToDate = new UDate();
       eToDate.setDate(eTYear, eTMonth, eTDay);
       eToDate.setSecond(GSConstSchedule.DAY_START_SECOND);
       eToDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
       if (eToDate.getYear() != eTYear
       || eToDate.getMonth() != eTMonth
       || eToDate.getIntDay() != eTDay) {
           //終了日付：終了
           return false;
       }

       //from～to大小チェック
       //終了日付：開始・終了
       if (eFrDate.compareDateYMD(eToDate) == UDate.SMALL) {
           return false;
       }

       ActionErrors errors = new ActionErrors();
       // 検索時キーワード
       if (!StringUtil.isNullZeroString(sch100SvKeyValue__)) {
           errors = GSValidateSchedule.validateCmnFieldText(errors,
                   "sch100SvKeyValue",
                   sch100SvKeyValue__,
                   "sch100SvKeyValue",
                   GSConstSchedule.MAX_LENGTH_TITLE,
                   false);
           //内容のチェック
           if (errors.size() > 0) {
               return false;
           }
           // 検索対象
           if (sch100SvSearchTarget__ == null
                   || sch100SvSearchTarget__.length == 0) {
               return false;
           }
           for (String chkStr : sch100SvSearchTarget__) {
               if (ArrayUtils.indexOf(
                       new int[] {
                               GSConstSchedule.SEARCH_TARGET_TITLE,
                               GSConstSchedule.SEARCH_TARGET_HONBUN
                       },
                       NullDefault.getInt(chkStr, -1)) < 0) {
                   return false;
               }

           }
       }

       // キーワード検索区分
       if (ArrayUtils.indexOf(new int[] {GSConstSchedule.KEY_WORD_KBN_AND,
               GSConstSchedule.KEY_WORD_KBN_OR},
               NullDefault.getInt(sch100SvKeyWordkbn__, -1)) < 0) {
           return false;
       }

       // オーダーキー
       if (ArrayUtils.indexOf(new int[] {GSConstSchedule.ORDER_KEY_ASC,
               GSConstSchedule.ORDER_KEY_DESC},
               sch100SvOrderKey1__) < 0) {
           return false;
       }
       // ソートキー
       if (ArrayUtils.indexOf(GSConstSchedule.LIST_SORT_KEY_ALL,
               sch100SvSortKey1__) < 0) {
           return false;
       }
       // オーダーキー2
       if (ArrayUtils.indexOf(new int[] {GSConstSchedule.ORDER_KEY_ASC,
               GSConstSchedule.ORDER_KEY_DESC},
               sch100SvOrderKey2__) < 0) {
           return false;
       }
       // ソートキー2
       if (ArrayUtils.indexOf(GSConstSchedule.LIST_SORT_KEY_ALL,
               sch100SvSortKey2__) < 0) {
           return false;
       }
       // タイトルカラー
       if (sch100SvBgcolor__ != null) {
           for (String chkStr : sch100SvBgcolor__) {
               if (ArrayUtils.indexOf(
                       GSConstSchedule.BGCOLOR_ALL,
                       NullDefault.getInt(chkStr, -1)) < 0) {
                   return false;
               }

           }
       }
       return true;
   }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目がJIS第2水準文字かチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkJisString(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;
        //JIS第2水準文字か
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }
    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param keyWord キーワード
     * @param searchTarget 検索対象
     * @param req リクエスト
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchTarget(
        ActionErrors errors,
        String keyWord,
        String[] searchTarget,
        HttpServletRequest req) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //キーワード未入力時はチェックなし
        if (NullDefault.getString(keyWord, "").length() < 1) {
            return false;
        }

        if (searchTarget == null || searchTarget.length < 1) {
            //検索対象
            String textSort = gsMsg.getMessage(req, "cmn.search2");
            //未選択の場合エラー
            msg = new ActionMessage(
                    "error.select.required.text", textSort);
            StrutsUtil.addMessage(errors, msg, "target");
            return true;
        }

        return false;
    }

    /**
     * <p>sch100Bgcolor を取得します。
     * @return sch100Bgcolor
     */
    public String[] getSch100Bgcolor() {
        return sch100Bgcolor__;
    }

    /**
     * <p>sch100Bgcolor をセットします。
     * @param sch100Bgcolor sch100Bgcolor
     */
    public void setSch100Bgcolor(String[] sch100Bgcolor) {
        sch100Bgcolor__ = sch100Bgcolor;
    }

    /**
     * <p>sch100ColorMsgList を取得します。
     * @return sch100ColorMsgList
     */
    public ArrayList<String> getSch100ColorMsgList() {
        return sch100ColorMsgList__;
    }

    /**
     * <p>sch100ColorMsgList をセットします。
     * @param sch100ColorMsgList sch100ColorMsgList
     */
    public void setSch100ColorMsgList(ArrayList<String> sch100ColorMsgList) {
        sch100ColorMsgList__ = sch100ColorMsgList;
    }

    /**
     * <p>sch100SvBgcolor を取得します。
     * @return sch100SvBgcolor
     */
    public String[] getSch100SvBgcolor() {
        return sch100SvBgcolor__;
    }

    /**
     * <p>sch100SvBgcolor をセットします。
     * @param sch100SvBgcolor sch100SvBgcolor
     */
    public void setSch100SvBgcolor(String[] sch100SvBgcolor) {
        sch100SvBgcolor__ = sch100SvBgcolor;
    }

    /**
     * <p>sch100SelectUsrSid を取得します。
     * @return sch100SelectUsrSid
     */
    public String getSch100SelectUsrSid() {
        return sch100SelectUsrSid__;
    }

    /**
     * <p>sch100SelectUsrSid をセットします。
     * @param sch100SelectUsrSid sch100SelectUsrSid
     */
    public void setSch100SelectUsrSid(String sch100SelectUsrSid) {
        sch100SelectUsrSid__ = sch100SelectUsrSid;
    }

    /**
     * <p>sch100BtnCmd を取得します。
     * @return sch100BtnCmd
     */
    public String getSch100BtnCmd() {
        return sch100BtnCmd__;
    }

    /**
     * <p>sch100BtnCmd をセットします。
     * @param sch100BtnCmd sch100BtnCmd
     */
    public void setSch100BtnCmd(String sch100BtnCmd) {
        sch100BtnCmd__ = sch100BtnCmd;
    }

    /**
     * <p>sch100colorKbn を取得します。
     * @return sch100colorKbn
     */
    public int getSch100colorKbn() {
        return sch100colorKbn__;
    }

    /**
     * <p>sch100colorKbn をセットします。
     * @param sch100colorKbn sch100colorKbn
     */
    public void setSch100colorKbn(int sch100colorKbn) {
        sch100colorKbn__ = sch100colorKbn;
    }

    /**
     * <p>reminderTimeList を取得します。
     * @return reminderTimeList
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#reminderTimeList__
     */
    public List<LabelValueBean> getReminderTimeList() {
        return reminderTimeList__;
    }

    /**
     * <p>reminderTimeList をセットします。
     * @param reminderTimeList reminderTimeList
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#reminderTimeList__
     */
    public void setReminderTimeList(List<LabelValueBean> reminderTimeList) {
        reminderTimeList__ = reminderTimeList;
    }

    /**
     * <p>sch100SelectScdSid を取得します。
     * @return sch100SelectScdSid
     * @see jp.groupsession.v2.sch.sch100.Sch100ParamModel#sch100SelectScdSid__
     */
    public int[] getSch100SelectScdSid() {
        return sch100SelectScdSid__;
    }

    /**
     * <p>sch100SelectScdSid をセットします。
     * @param sch100SelectScdSid sch100selectScdSid
     * @see jp.groupsession.v2.sch.sch100.Sch100ParamModel#sch100SelectScdSid__
     */
    public void setSch100SelectScdSid(int[] sch100SelectScdSid) {
        sch100SelectScdSid__ = sch100SelectScdSid;
    }

    /**
     * <p>sch100StartFrDate を取得します。
     * @return sch100StartFrDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100StartFrDate__
     */
    public String getSch100StartFrDate() {
        return sch100StartFrDate__;
    }

    /**
     * <p>sch100StartFrDate をセットします。
     * @param sch100StartFrDate sch100StartFrDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100StartFrDate__
     */
    public void setSch100StartFrDate(String sch100StartFrDate) {
        sch100StartFrDate__ = sch100StartFrDate;
    }

    /**
     * <p>sch100StartToDate を取得します。
     * @return sch100StartToDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100StartToDate__
     */
    public String getSch100StartToDate() {
        return sch100StartToDate__;
    }

    /**
     * <p>sch100StartToDate をセットします。
     * @param sch100StartToDate sch100StartToDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100StartToDate__
     */
    public void setSch100StartToDate(String sch100StartToDate) {
        sch100StartToDate__ = sch100StartToDate;
    }

    /**
     * <p>sch100EndFrDate を取得します。
     * @return sch100EndFrDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100EndFrDate__
     */
    public String getSch100EndFrDate() {
        return sch100EndFrDate__;
    }

    /**
     * <p>sch100EndFrDate をセットします。
     * @param sch100EndFrDate sch100EndFrDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100EndFrDate__
     */
    public void setSch100EndFrDate(String sch100EndFrDate) {
        sch100EndFrDate__ = sch100EndFrDate;
    }

    /**
     * <p>sch100EndToDate を取得します。
     * @return sch100EndToDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100EndToDate__
     */
    public String getSch100EndToDate() {
        return sch100EndToDate__;
    }

    /**
     * <p>sch100EndToDate をセットします。
     * @param sch100EndToDate sch100EndToDate
     * @see jp.groupsession.v2.sch.sch100.Sch100Form#sch100EndToDate__
     */
    public void setSch100EndToDate(String sch100EndToDate) {
        sch100EndToDate__ = sch100EndToDate;
    }
}