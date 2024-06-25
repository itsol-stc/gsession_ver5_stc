package jp.groupsession.v2.rsv.rsv111;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110ParamModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110SisetuModel;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.rsv.ui.parts.schedule.RsvScheduleSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設予約拡張登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv111ParamModel extends Rsv110ParamModel {

    /** 週コンボリスト */
    private ArrayList<LabelValueBean> rsv111WeekList__ = null;
    /** 日コンボリスト */
    private ArrayList<LabelValueBean> rsv111DayList__ = null;
    /** 日コンボリスト(拡張) */
    private ArrayList<LabelValueBean> rsv111ExDayList__ = null;
    /** 日コンボリスト(拡張) */
    private ArrayList<LabelValueBean> rsv111ExDayOfYearlyList__ = null;

    /** ヘッダ部表示非表示フラグ */
    private String rsv111HeaderDspFlg__ = "1";
    /** 施設予約と関連したスケジュールデータ存在フラグ */
    private boolean rsv111ExistSchDateFlg__ = false;

    /** 繰り返し登録 スケジュール登録 対象ユーザ 選択済みユーザ UI */
    private RsvScheduleSelector rsv111SchUserUI__ = null;
    /** 繰り返し登録 公開対象ユーザ・グループ UI */
    private UserGroupSelector rsv111PubUsrGrpUI__ = null;


    /**
     * <p>rsv111HeaderDspFlg を取得します。
     * @return rsv111HeaderDspFlg
     */
    public String getRsv111HeaderDspFlg() {
        return rsv111HeaderDspFlg__;
    }
    /**
     * <p>rsv111HeaderDspFlg をセットします。
     * @param rsv111HeaderDspFlg rsv111HeaderDspFlg
     */
    public void setRsv111HeaderDspFlg(String rsv111HeaderDspFlg) {
        rsv111HeaderDspFlg__ = rsv111HeaderDspFlg;
    }
    /**
     * <p>rsv111DayList__ を取得します。
     * @return rsv111DayList
     */
    public ArrayList<LabelValueBean> getRsv111DayList() {
        return rsv111DayList__;
    }
    /**
     * <p>rsv111DayList__ をセットします。
     * @param rsv111DayList rsv111DayList__
     */
    public void setRsv111DayList(ArrayList<LabelValueBean> rsv111DayList) {
        rsv111DayList__ = rsv111DayList;
    }
    /**
     * <p>rsv111ExDayList を取得します。
     * @return rsv111ExDayList
     */
    public ArrayList<LabelValueBean> getRsv111ExDayList() {
        return rsv111ExDayList__;
    }
    /**
     * <p>rsv111ExDayList をセットします。
     * @param rsv111ExDayList rsv111ExDayList
     */
    public void setRsv111ExDayList(ArrayList<LabelValueBean> rsv111ExDayList) {
        rsv111ExDayList__ = rsv111ExDayList;
    }
    /**
     * <p>rsv111WeekList__ を取得します。
     * @return rsv111WeekList
     */
    public ArrayList<LabelValueBean> getRsv111WeekList() {
        return rsv111WeekList__;
    }
    /**
     * <p>rsv111WeekList__ をセットします。
     * @param rsv111WeekList rsv111WeekList__
     */
    public void setRsv111WeekList(ArrayList<LabelValueBean> rsv111WeekList) {
        rsv111WeekList__ = rsv111WeekList;
    }
    /**
     * <p>rsv111SchUserUI を取得します。
     * @return rsv111SchUserUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111SchUserUI__
     */
    public RsvScheduleSelector getRsv111SchUserUI() {
        return rsv111SchUserUI__;
    }
    /**
     * <p>rsv111SchUserUI をセットします。
     * @param rsv111SchUserUI rsv111SchUserUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111SchUserUI__
     */
    public void setRsv111SchUserUI(RsvScheduleSelector rsv111SchUserUI) {
        rsv111SchUserUI__ = rsv111SchUserUI;
    }
    /**
     * <p>rsv111PubUsrGrpUI を取得します。
     * @return rsv111PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111PubUsrGrpUI__
     */
    public UserGroupSelector getRsv111PubUsrGrpUI() {
        return rsv111PubUsrGrpUI__;
    }
    /**
     * <p>rsv111PubUsrGrpUI をセットします。
     * @param rsv111PubUsrGrpUI rsv111PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv110.Rsv110Form#rsv111PubUsrGrpUI__
     */
    public void setRsv111PubUsrGrpUI(UserGroupSelector rsv111PubUsrGrpUI) {
        rsv111PubUsrGrpUI__ = rsv111PubUsrGrpUI;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] 全項目チェック
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @return errors エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateRsv111All(RequestModel reqMdl, Connection con,
                                        int sessionUsrSid)
        throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionErrors errorsInp = validateRsv111Base(reqMdl, con, sessionUsrSid);
        if (errorsInp.isEmpty()) {
            ActionErrors errorsDel = validateRsv111Scd(reqMdl, con);
            if (!errorsDel.isEmpty()) {
                errors.add(errorsDel);
            }
        } else {
            errors.add(errorsInp);
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @return errors エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateRsv111Base(RequestModel reqMdl, Connection con,
                                            int sessionUsrSid)
        throws Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        boolean errorFlg = false;

        String rsrMok = getRsv111RsrMok();
        //利用目的 未入力チェック
        if (StringUtil.isNullZeroString(rsrMok)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsrMok");
            errorFlg = true;
        //利用目的 桁数チェック
        } else if (rsrMok.length() > GSConstReserve.MAX_LENGTH_MOKUTEKI) {
            msg =
                new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("reserve.72"),
                                String.valueOf(GSConstReserve.MAX_LENGTH_MOKUTEKI));
            StrutsUtil.addMessage(errors, msg, "rsrMok");
            errorFlg = true;
        //利用目的 スペースのみチェック
        } else if (ValidateUtil.isSpace(rsrMok)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsrMok");
            errorFlg = true;
        //利用目的 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(rsrMok)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsrMok");
            errorFlg = true;
        //利用目的 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(rsrMok)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(rsrMok);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("reserve.72"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, "rsrMok");
            errorFlg = true;
        }

        //拡張区分
        int rsrKbn = getRsv111RsrKbn();

        if (rsrKbn == GSConstReserve.KAKUTYO_KBN_EVERY_WEEK) {
            int weekCnt = 0;
            weekCnt += getRsv111RsrDweek1();
            weekCnt += getRsv111RsrDweek2();
            weekCnt += getRsv111RsrDweek3();
            weekCnt += getRsv111RsrDweek4();
            weekCnt += getRsv111RsrDweek5();
            weekCnt += getRsv111RsrDweek6();
            weekCnt += getRsv111RsrDweek7();

            if (weekCnt == 0) {
                msg = new ActionMessage("error.select.required.text",
                        gsMsg.getMessage("cmn.dayofweek"));
                StrutsUtil.addMessage(errors, msg, "rsrKbn1");
                errorFlg = true;
            }
        } else if (rsrKbn == GSConstReserve.KAKUTYO_KBN_EVERY_MONTH) {
            if (getRsv111RsrWeek() == 0 && getRsv111RsrDay() == 0) {
                msg = new ActionMessage("error.select.required.text",
                                         gsMsg.getMessage("reserve.src.26"));
                StrutsUtil.addMessage(errors, msg, "rsrKbn2");
                errorFlg = true;
            //週・日付の同時選択
            } else if (getRsv111RsrWeek() != 0 && getRsv111RsrDay() != 0) {
                msg = new ActionMessage("error.select.required.text",
                                        gsMsg.getMessage("reserve.src.27"));
                StrutsUtil.addMessage(errors, msg, "rsrKbn3");
                errorFlg = true;
            }
        }

        //期間年月日チェックフラグ
        boolean fromOk = false;

        int iSYear = Integer.parseInt(getRsv111RsrDateYearFr());
        int iSMonth = Integer.parseInt(getRsv111RsrDateMonthFr());
        int iSDay = Integer.parseInt(getRsv111RsrDateDayFr());

        UDate frDate = new UDate();
        frDate.setDate(iSYear, iSMonth, iSDay);
        frDate.setSecond(GSConstReserve.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        if (frDate.getYear() != iSYear
            || frDate.getMonth() != iSMonth
            || frDate.getIntDay() != iSDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("reserve.157"));
            StrutsUtil.addMessage(errors, msg, "rsv111RsrDateFrom");
            errorFlg = true;
        } else {
            fromOk = true;
        }

        //期間年月日チェックフラグ
        boolean toOk = false;

        int iEYear = Integer.parseInt(getRsv111RsrDateYearTo());
        int iEMonth = Integer.parseInt(getRsv111RsrDateMonthTo());
        int iEDay = Integer.parseInt(getRsv111RsrDateDayTo());

        UDate toDate = new UDate();
        toDate.setDate(iEYear, iEMonth, iEDay);
        toDate.setSecond(GSConstReserve.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        if (toDate.getYear() != iEYear
            || toDate.getMonth() != iEMonth
            || toDate.getIntDay() != iEDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("reserve.158"));
            StrutsUtil.addMessage(errors, msg, "rsv111RsrDateTo");
            errorFlg = true;
        } else {
            toOk = true;
        }

        //個別チェックOKの場合
        if (fromOk && toOk) {
            //from～to大小チェック
            int dchk = frDate.compare(frDate, toDate);
            if (dchk != UDate.EQUAL
                    && dchk != UDate.LARGE) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("reserve.156"),
                        gsMsg.getMessage("cmn.start.lessthan.end"));
                StrutsUtil.addMessage(errors, msg, "rsv111Kikan");
                errorFlg = true;
            }
        }

        //時間チェック
        String timeFrom =
            StringUtil.toDecFormat(getRsv111RsrTimeHourFr(), "00")
            + StringUtil.toDecFormat(getRsv111RsrTimeMinuteFr(), "00");
        String timeTo =
            StringUtil.toDecFormat(getRsv111RsrTimeHourTo(), "00")
            + StringUtil.toDecFormat(getRsv111RsrTimeMinuteTo(), "00");

        //from～to大小チェック
        if (Integer.parseInt(timeFrom) >= Integer.parseInt(timeTo)) {
            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage("reserve.159"),
                    gsMsg.getMessage("cmn.start.lessthan.end"));
            StrutsUtil.addMessage(errors, msg, "rsv111Time");
            errorFlg = true;
        }

        //内容
        String rsvBiko = getRsv111RsrBiko();
        if (!StringUtil.isNullZeroString(rsvBiko)) {
            //内容 桁数チェック
            if (rsvBiko.length() > GSConstReserve.MAX_LENGTH_NAIYO) {
                msg = new ActionMessage("error.input.length.textarea",
                        gsMsg.getMessage("cmn.content"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_NAIYO));
                StrutsUtil.addMessage(errors, msg, "rsvBiko");
                errorFlg = true;
            }
            //内容 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(rsvBiko)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage("cmn.content"));
                StrutsUtil.addMessage(errors, msg, "rsvBiko");
                errorFlg = true;
            }
            //内容 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(rsvBiko)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(rsvBiko);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("cmn.content"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, "rsvBiko");
                errorFlg = true;
            }
        }

        if (!errorFlg) {

            //登録日のチェック
            Rsv111Biz biz = new Rsv111Biz(reqMdl, con);
            HashMap<String, String> dayMap = biz.getInsertDateList(this);

            if (dayMap.isEmpty()) {
                msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("cmn.date2"));
                StrutsUtil.addMessage(errors, msg, "daymap");
                errorFlg = true;
            } else {

                ArrayList<String> dayList = new ArrayList<String>(dayMap.values());
                Collections.sort(dayList);

                int sisetuSid = -1;
                String procMode = getRsv110ProcMode();

                //新規モード
                if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)) {
                    sisetuSid = getRsv110RsdSid();
                //編集モード or 複写して登録モード
                } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)
                        || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
                    Rsv110SisetuModel yrkMdl = biz.getYoyakuEditData(this);
                    if (yrkMdl != null) {
                        sisetuSid = yrkMdl.getRsdSid();
                    }
                }

                //施設の情報を取得
                Rsv210Model dataMdl = biz.getGroupCheckData(sisetuSid);
                if (dataMdl != null) {

                    //予約可能期限チェック(期限が設定されていればチェックする)
                    String kigen = dataMdl.getRsdProp6();
                    if (!StringUtil.isNullZeroString(kigen)) {

                        //施設グループ管理者の場合は予約可能期限チェックをパスする
                        RsvCommonBiz rsvBiz = new RsvCommonBiz();
                        if (!rsvBiz.isGroupAdmin(con, sisetuSid, sessionUsrSid)) {

                            UDate now = new UDate();
                            UDate udKigen = now.cloneUDate();
                            udKigen.addDay(Integer.parseInt(kigen));

                            String kigenYmd = udKigen.getDateString();

                            //ソート済の配列の最後(最後の日付)を取得
                            String toDateStr = dayList.get(dayList.size() - 1);
                            UDate toDateUd = new UDate();
                            toDateUd.setDate(toDateStr);
                            String chkYmd = toDateUd.getDateString();

                            if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {
                                String kigenStr =
                                        gsMsg.getMessage("cmn.comments")
                                        + dataMdl.getRsdProp6()
                                        + gsMsg.getMessage("cmn.days.after");

                                msg = new ActionMessage("error.kigen.over2.sisetu", kigenStr);
                                StrutsUtil.addMessage(errors, msg, "sisetu");
                                errorFlg = true;
                            }
                        }
                    }

                    //重複のチェック(重複登録 = 不可の場合にチェック)
                    String tyohuku = dataMdl.getRsdProp7();
                    if (!errorFlg
                            && !StringUtil.isNullZeroString(tyohuku)
                            && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {

                        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
                        int timeHourFrom = Integer.parseInt(getRsv111RsrTimeHourFr());
                        int timeMinuteFrom = Integer.parseInt(getRsv111RsrTimeMinuteFr());
                        int timeHourTo = Integer.parseInt(getRsv111RsrTimeHourTo());
                        int timeMinuteTo = Integer.parseInt(getRsv111RsrTimeMinuteTo());
                        List<RsvSisYrkModel> ngList = new ArrayList<RsvSisYrkModel>();

                        for (String dayStr : dayList) {

                            //予約開始
                            UDate chkFrDate = new UDate();
                            chkFrDate.setDate(dayStr);
                            chkFrDate.setHour(timeHourFrom);
                            chkFrDate.setMinute(timeMinuteFrom);
                            chkFrDate.setSecond(GSConstReserve.DAY_START_SECOND);
                            chkFrDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                            //予約終了
                            UDate chkToDate = new UDate();
                            chkToDate.setDate(dayStr);
                            chkToDate.setHour(timeHourTo);
                            chkToDate.setMinute(timeMinuteTo);
                            chkToDate.setSecond(GSConstReserve.DAY_START_SECOND);
                            chkToDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                            //新規モード or 複写して登録モード
                            if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)
                                    || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
                                ngList = yrkDao.getYrkNgList(
                                        -1,
                                        sisetuSid,
                                        chkFrDate,
                                        chkToDate);
                            //編集モード
                            } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {

                                if (getRsv111RsrRsid() > 0) {
                                    ngList = yrkDao.getKakutyoYrkNgList(
                                            getRsv111RsrRsid(),
                                            sisetuSid,
                                            chkFrDate,
                                            chkToDate);

                                } else {
                                    ngList = yrkDao.getYrkNgList(
                                            getRsv110RsySid(),
                                            sisetuSid,
                                            chkFrDate,
                                            chkToDate);
                                }
                            }

                            //重複チェック
                            if (ngList != null && ngList.size() > 0) {

                                String textSchedule = gsMsg.getMessage("cmn.reserve");
                                msg = new ActionMessage("error.input.dup", textSchedule);
                                StrutsUtil.addMessage(errors, msg, "rsv110YrkEr");

                                for (RsvSisYrkModel yrkModel : ngList) {

                                    String schTime
                                           = UDateUtil.getYymdJ(yrkModel.getRsyFrDate(), reqMdl);
                                    schTime
                                          += UDateUtil.getSeparateHMJ(yrkModel.getRsyFrDate(),
                                                                      reqMdl);
                                    schTime
                                          += "～";
                                    schTime
                                          += UDateUtil.getYymdJ(yrkModel.getRsyToDate(), reqMdl);
                                    schTime
                                          += UDateUtil.getSeparateHMJ(yrkModel.getRsyToDate(),
                                                                      reqMdl);


                                    msg = new ActionMessage("error.input.dup.rsv",
                                            schTime,
                                            StringUtilHtml.transToHTmlPlusAmparsant(
                                                    yrkModel.getRsyMok()));

                                    StrutsUtil.addMessage(errors, msg,
                                            "rsv111tyohuku" + String.valueOf(yrkModel.getRsySid()));
                                }
                            }
                        }
                    }

                    //スケジュール 重複チェック
                    RelationBetweenScdAndRsvChkBiz schChkBiz =
                        new RelationBetweenScdAndRsvChkBiz(reqMdl, con);
                    if (getRsv111SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                        String grpSid = getRsv111SchGroupSid();
                        if (NullDefault.getInt(grpSid, -1) >= 0) {
                            schChkBiz.validateGroupForSchedule(errors, grpSid,
                                                            sessionUsrSid, "rsv111SchGroupSid");
                        }
                    } else {
                        //String[] users = getSv_users();
                        String[] users = getRsv111SvUsers();
                        if (users != null && users.length > 0) {
                            int timeHourFrom = Integer.parseInt(getRsv111RsrTimeHourFr());
                            int timeMinuteFrom = Integer.parseInt(getRsv111RsrTimeMinuteFr());
                            int timeHourTo = Integer.parseInt(getRsv111RsrTimeHourTo());
                            int timeMinuteTo = Integer.parseInt(getRsv111RsrTimeMinuteTo());

                            List<UDate[]> dateList = new ArrayList<UDate[]>();
                            for (String dayStr : dayList) {
                                //予約開始
                                UDate chkFrDate = new UDate();
                                chkFrDate.setDate(dayStr);
                                chkFrDate.setHour(timeHourFrom);
                                chkFrDate.setMinute(timeMinuteFrom);
                                chkFrDate.setSecond(GSConstReserve.DAY_START_SECOND);
                                chkFrDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                                //予約終了
                                UDate chkToDate = new UDate();
                                chkToDate.setDate(dayStr);
                                chkToDate.setHour(timeHourTo);
                                chkToDate.setMinute(timeMinuteTo);
                                chkToDate.setSecond(GSConstReserve.DAY_START_SECOND);
                                chkToDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

                                dateList.add(new UDate[] {chkFrDate, chkToDate});
                            }

                            //関連するスケジュールを全てチェック対象とする
                            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
                            List<Integer> scdRsList
                                = yrkDao.getScdRsSid(getRsv111RsrRsid());
                            if (!scdRsList.isEmpty()) {

                                int[] scdRsSidAry = new int[scdRsList.size()];
                                for (int idx = 0; idx < scdRsList.size(); idx++) {
                                    scdRsSidAry[idx] = scdRsList.get(idx);
                                }

                                schChkBiz.validateDateForSchedule(
                                        errors,
                                        dateList,
                                        users,
                                        scdRsSidAry,
                                        procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD),
                                        sessionUsrSid,
                                        "rsv111Date");
                            }
                        }
                    }
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] スケジュールの更新チェック
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateRsv111Scd(RequestModel reqMdl, Connection con)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        RelationBetweenScdAndRsvChkBiz biz =
            new RelationBetweenScdAndRsvChkBiz(reqMdl, con);

        int errCd = 0;

        //スケジュールと関連付いている & 「スケジュールへ反映」が選択されている
        if (getRsv110ScdRsSid() > 0
                && getRsv111ScdReflection() == GSConstReserve.SCD_REFLECTION_OK
                && isRsv111ExistSchDateFlg()) {

            if (getRsv111RsrRsid() > 0) {
                errCd =
                    biz.isScdEdit(
                            getRsv110RsySid(),
                            RelationBetweenScdAndRsvChkBiz.CHK_KBN_KURIKAESHI);
            } else {
                errCd =
                    biz.isScdEdit(
                            getRsv110RsySid(),
                            RelationBetweenScdAndRsvChkBiz.CHK_KBN_TANITU);
            }

            if (errCd != RelationBetweenScdAndRsvChkBiz.ERR_CD_NON_ERR) {
                msg = new ActionMessage("error.scd.auth");
                StrutsUtil.addMessage(errors, msg, "auth");
            }

        } else if (getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)
                && isRsv111ExistSchDateFlg()) {

            //編集可能かチェック
            errCd = biz.isEditMeRsv(getRsv110RsySid());
            if (errCd != RelationBetweenScdAndRsvChkBiz.ERR_CD_NON_ERR) {
                msg = new ActionMessage("error.myself.auth");
                StrutsUtil.addMessage(errors, msg, "myselft.auth");
            }

        }
        return errors;
    }
    /**
     * <p>rsv111ExistSchDateFlg を取得します。
     * @return rsv111ExistSchDateFlg
     */
    public boolean isRsv111ExistSchDateFlg() {
        return rsv111ExistSchDateFlg__;
    }
    /**
     * <p>rsv111ExistSchDateFlg をセットします。
     * @param rsv111ExistSchDateFlg rsv111ExistSchDateFlg
     */
    public void setRsv111ExistSchDateFlg(boolean rsv111ExistSchDateFlg) {
        rsv111ExistSchDateFlg__ = rsv111ExistSchDateFlg;
    }
    /**
     * <p>rsv111ExDayOfYearlyList を取得します。
     * @return rsv111ExDayOfYearlyList
     */
    public ArrayList<LabelValueBean> getRsv111ExDayOfYearlyList() {
        return rsv111ExDayOfYearlyList__;
    }
    /**
     * <p>rsv111ExDayOfYearlyList をセットします。
     * @param rsv111ExDayOfYearlyList rsv111ExDayOfYearlyList
     */
    public void setRsv111ExDayOfYearlyList(ArrayList<LabelValueBean> rsv111ExDayOfYearlyList) {
        rsv111ExDayOfYearlyList__ = rsv111ExDayOfYearlyList;
    }
}