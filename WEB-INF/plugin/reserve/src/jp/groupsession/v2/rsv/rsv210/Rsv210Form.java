package jp.groupsession.v2.rsv.rsv210;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.rsv.RsvValidateUtil;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.rsv.rsv030.Rsv030Form;
import jp.groupsession.v2.rsv.ui.parts.schedule.RsvScheduleSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 施設予約一括登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv210Form extends Rsv030Form implements ISelectorUseForm {

    /** 初期表示フラグ */
    private boolean rsv210InitFlg__ = true;
    /** 登録者 */
    private String rsv210Torokusya__ = null;
    /** 利用目的 */
    private String rsv210Mokuteki__ = null;
    /** 内容 */
    private String rsv210Naiyo__ = null;
    /** 時コンボFr選択値 */
    private int rsv210SelectedHourFr__ = -1;
    /** 時コンボTo選択値 */
    private int rsv210SelectedHourTo__ = -1;
    /** 分コンボFr選択値 */
    private int rsv210SelectedMinuteFr__ = -1;
    /** 分コンボTo選択値 */
    private int rsv210SelectedMinuteTo__ = -1;
    /** 時分Fr選択値 */
    private String rsv210TimeFr__ = null;
    /** 時分To選択値 */
    private String rsv210TimeTo__ = null;
    /** 時間表示間隔 */
    private int rsv210HourDiv__ =  GSConstReserve.DF_HOUR_DIVISION;
    /** 編集権限 */
    private int rsv210RsyEdit__ = GSConstReserve.EDIT_AUTH_NONE;
    /** 公開区分 */
    private int rsv210RsyPublic__ = GSConstReserve.PUBLIC_KBN_ALL;

    /** 一括予約開始日付 */
    private String rsv210YrkFrom__ = null;

    /** 午前開始時 */
    private int rsv210AmFrHour__;
    /** 午前開始分 */
    private int rsv210AmFrMin__;
    /** 午前終了時 */
    private int rsv210AmToHour__;
    /** 午前終了分 */
    private int rsv210AmToMin__;

    /** 午後開始時 */
    private int rsv210PmFrHour__;
    /** 午後開始分 */
    private int rsv210PmFrMin__;
    /** 午後終了時 */
    private int rsv210PmToHour__;
    /** 午後終了分 */
    private int rsv210PmToMin__;

    /** 終日開始時 */
    private int rsv210AllDayFrHour__;
    /** 終日開始分 */
    private int rsv210AllDayFrMin__;
    /** 終日終了時 */
    private int rsv210AllDayToHour__;
    /** 終日終了分 */
    private int rsv210AllDayToMin__;

    //----------- スケジュール同時登録 ----------------------------
    /** スケジュールプラグイン使用有無 0=使用 1=未使用*/
    private int schedulePluginKbn__;

    /** スケジュール登録区分 0:ユーザ 1:グループ */
    private int rsv210SchKbn__ = GSConstReserve.RSV_SCHKBN_USER;
    /** 同時登録スケジュールグループSID */
    private String rsv210SchGroupSid__ = "-1";
    /** 同時登録スケジュールグループリスト */
    private List<ExtendedLabelValueModel> rsv210SchGroupLabel__ = null;

    /** スケジュール共有範囲  0=共有範囲制限なし 1=所属グループのみ*/
    private int rsv210SchCrangeKbn__;
    /** スケジュールの閲覧を許可しないグループの一覧 */
    private List<Integer> rsv210SchNotAccessGroupList__;

    /** 同時登録スケジュールユーザ グループSID */
    private String rsv210GroupSid__ = null;
    /** 同時登録スケジュールユーザ 選択済みユーザSID */
    private String[] sv_users__ = null;
    /** 同時登録スケジュールユーザ UI */
    private RsvScheduleSelector rsv210ScheduleUserUI__ =
            RsvScheduleSelector.builder()
            .chainLabel(new GsMessageReq("schedule.3", null))
            .chainType(EnumSelectType.USER)
            .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
            .chainSelect(
                    Select.builder()
                    .chainLabel(new GsMessageReq("cmn.target.user", null))
                    .chainParameterName(
                            "sv_users")
                )
            .chainGroupSelectionParamName("rsv210GroupSid")
            .build();

    /** スケジュール作成ユーザ名称 */
    private ArrayList<UsrLabelValueBean> userNameArray__ = null;

    /** デフォルトユーザSID  */
    private int rsv210PubDefUsrSid__ = 0;
    /** 公開対象 ユーザ グループ */
    private String rsv210PubUserGroup__ =
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象ユーザ・グループSID */
    private String[] rsv210PubUsrGrpSid__ = new String[0];
    /** 公開対象ユーザ・グループ UI */
    private UserGroupSelector rsv210PubUsrGrpUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("main.exposed", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(
                    Select.builder()
                    .chainParameterName(
                            "rsv210PubUsrGrpSid")
                )
            .chainGroupSelectionParamName("rsv210PubUserGroup")
            .build();

    //-----------------------------------------------------------------

    /**
     * <p>rsv210RsyEdit__ を取得します。
     * @return rsv210RsyEdit
     */
    public int getRsv210RsyEdit() {
        return rsv210RsyEdit__;
    }
    /**
     * <p>rsv210RsyEdit__ をセットします。
     * @param rsv210RsyEdit rsv210RsyEdit__
     */
    public void setRsv210RsyEdit(int rsv210RsyEdit) {
        rsv210RsyEdit__ = rsv210RsyEdit;
    }
    /**
     * <p> rsv210RsyPublicを取得します。
     * @return rsv210RsyPublic
     * */
    public int getRsv210RsyPublic() {
        return rsv210RsyPublic__;
    }
    /**
     * <p> rsv210RsyPublicをセットします。
     * @param rsv210RsyPublic rsv210RsyPublic
     * */
    public void setRsv210RsyPublic(int rsv210RsyPublic) {
        rsv210RsyPublic__ = rsv210RsyPublic;
    }
    /**
     * <p>rsv210InitFlg__ を取得します。
     * @return rsv210InitFlg
     */
    public boolean isRsv210InitFlg() {
        return rsv210InitFlg__;
    }
    /**
     * <p>rsv210InitFlg__ をセットします。
     * @param rsv210InitFlg rsv210InitFlg__
     */
    public void setRsv210InitFlg(boolean rsv210InitFlg) {
        rsv210InitFlg__ = rsv210InitFlg;
    }
    /**
     * <p>rsv210Mokuteki__ を取得します。
     * @return rsv210Mokuteki
     */
    public String getRsv210Mokuteki() {
        return rsv210Mokuteki__;
    }
    /**
     * <p>rsv210Mokuteki__ をセットします。
     * @param rsv210Mokuteki rsv210Mokuteki__
     */
    public void setRsv210Mokuteki(String rsv210Mokuteki) {
        rsv210Mokuteki__ = rsv210Mokuteki;
    }
    /**
     * <p>rsv210Naiyo__ を取得します。
     * @return rsv210Naiyo
     */
    public String getRsv210Naiyo() {
        return rsv210Naiyo__;
    }
    /**
     * <p>rsv210Naiyo__ をセットします。
     * @param rsv210Naiyo rsv210Naiyo__
     */
    public void setRsv210Naiyo(String rsv210Naiyo) {
        rsv210Naiyo__ = rsv210Naiyo;
    }
    /**
     * <p>rsv210SelectedHourFr__ を取得します。
     * @return rsv210SelectedHourFr
     */
    public int getRsv210SelectedHourFr() {
        return rsv210SelectedHourFr__;
    }
    /**
     * <p>rsv210SelectedHourFr__ をセットします。
     * @param rsv210SelectedHourFr rsv210SelectedHourFr__
     */
    public void setRsv210SelectedHourFr(int rsv210SelectedHourFr) {
        rsv210SelectedHourFr__ = rsv210SelectedHourFr;
    }
    /**
     * <p>rsv210SelectedHourTo__ を取得します。
     * @return rsv210SelectedHourTo
     */
    public int getRsv210SelectedHourTo() {
        return rsv210SelectedHourTo__;
    }
    /**
     * <p>rsv210SelectedHourTo__ をセットします。
     * @param rsv210SelectedHourTo rsv210SelectedHourTo__
     */
    public void setRsv210SelectedHourTo(int rsv210SelectedHourTo) {
        rsv210SelectedHourTo__ = rsv210SelectedHourTo;
    }
    /**
     * <p>rsv210SelectedMinuteFr__ を取得します。
     * @return rsv210SelectedMinuteFr
     */
    public int getRsv210SelectedMinuteFr() {
        return rsv210SelectedMinuteFr__;
    }
    /**
     * <p>rsv210SelectedMinuteFr__ をセットします。
     * @param rsv210SelectedMinuteFr rsv210SelectedMinuteFr__
     */
    public void setRsv210SelectedMinuteFr(int rsv210SelectedMinuteFr) {
        rsv210SelectedMinuteFr__ = rsv210SelectedMinuteFr;
    }
    /**
     * <p>rsv210SelectedMinuteTo__ を取得します。
     * @return rsv210SelectedMinuteTo
     */
    public int getRsv210SelectedMinuteTo() {
        return rsv210SelectedMinuteTo__;
    }
    /**
     * <p>rsv210SelectedMinuteTo__ をセットします。
     * @param rsv210SelectedMinuteTo rsv210SelectedMinuteTo__
     */
    public void setRsv210SelectedMinuteTo(int rsv210SelectedMinuteTo) {
        rsv210SelectedMinuteTo__ = rsv210SelectedMinuteTo;
    }
    /**
     * <p>rsv210Torokusya__ を取得します。
     * @return rsv210Torokusya
     */
    public String getRsv210Torokusya() {
        return rsv210Torokusya__;
    }
    /**
     * <p>rsv210Torokusya__ をセットします。
     * @param rsv210Torokusya rsv210Torokusya__
     */
    public void setRsv210Torokusya(String rsv210Torokusya) {
        rsv210Torokusya__ = rsv210Torokusya;
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
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl, int sessionUsrSid)
        throws SQLException, IllegalAccessException,
        InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        boolean errorFlg = false;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //利用目的 未入力チェック
        if (StringUtil.isNullZeroString(rsv210Mokuteki__)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv210Mokuteki");
            errorFlg = true;
        //利用目的 桁数チェック
        } else if (rsv210Mokuteki__.length() > GSConstReserve.MAX_LENGTH_MOKUTEKI) {
            msg =
                new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("reserve.72"),
                                String.valueOf(GSConstReserve.MAX_LENGTH_MOKUTEKI));
            StrutsUtil.addMessage(errors, msg, "rsv210Mokuteki");
            errorFlg = true;
        //利用目的 スペースのみチェック
        } else if (ValidateUtil.isSpace(rsv210Mokuteki__)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv210Mokuteki");
            errorFlg = true;
        //利用目的 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(rsv210Mokuteki__)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv210Mokuteki");
            errorFlg = true;
            //利用目的 タブスペースチェック
        } else if (ValidateUtil.isTab(rsv210Mokuteki__)) {
            msg = new ActionMessage("error.input.tab.text",
                    gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, "rsv210Mokuteki");
            errorFlg = true;
        //利用目的 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(rsv210Mokuteki__)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv210Mokuteki__);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("reserve.72"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, "rsv210Mokuteki");
            errorFlg = true;
        }

        DateTimePickerBiz picker = new DateTimePickerBiz();
        String frTimeNameJp = gsMsg.getMessage("reserve.138");
        String toTimeNameJp = gsMsg.getMessage("reserve.139");
        int errorCnt = errors.size();
        boolean timeError = false;

        //時間大小チェック
        errors.add(picker.setHmParam(this,
                "rsv210TimeFr",
                "rsv210SelectedHourFr",
                "rsv210SelectedMinuteFr",
                frTimeNameJp));
        errors.add(picker.setHmParam(this,
                "rsv210TimeTo",
                "rsv210SelectedHourTo",
                "rsv210SelectedMinuteTo",
                toTimeNameJp));
        if (errorCnt == errors.size()) {
            String timeFrom =
                    StringUtil.toDecFormat(rsv210SelectedHourFr__, "00")
                    + StringUtil.toDecFormat(rsv210SelectedMinuteFr__, "00");

                String timeTo =
                    StringUtil.toDecFormat(rsv210SelectedHourTo__, "00")
                    + StringUtil.toDecFormat(rsv210SelectedMinuteTo__, "00");

                timeError = Integer.parseInt(timeFrom) >= Integer.parseInt(timeTo);
                if (timeError) {
                    msg = new ActionMessage("error.input.comp.text",
                            gsMsg.getMessage("reserve.159"),
                            gsMsg.getMessage("cmn.start.lessthan.end"));
                    StrutsUtil.addMessage(errors, msg, "rsvtime");
                    errorFlg = true;
                }
                //時間単位チェック
                String frMinute = rsv210TimeFr__.split(":")[1];
                String toMinute = rsv210TimeTo__.split(":")[1];
                int frTime = Integer.parseInt(frMinute);
                int toTime = Integer.parseInt(toMinute);
                boolean timeUnit = RsvValidateUtil.validateTimeUnit(
                        con, errors, gsMsg, frTime, toTime);
                if (!timeUnit) {
                    errorFlg = true;
                }
        }

        //内容
        if (!StringUtil.isNullZeroString(rsv210Naiyo__)) {
            //内容 桁数チェック
            if (rsv210Naiyo__.length() > GSConstReserve.MAX_LENGTH_NAIYO) {
                msg = new ActionMessage("error.input.length.textarea",
                        gsMsg.getMessage("cmn.content"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_NAIYO));
                StrutsUtil.addMessage(errors, msg, "rsv210Naiyo");
                errorFlg = true;
            }
            //内容 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(rsv210Naiyo__)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage("cmn.content"));
                StrutsUtil.addMessage(errors, msg, "rsv210Naiyo");
                errorFlg = true;
            }
            //内容 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(rsv210Naiyo__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(rsv210Naiyo__);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("cmn.content"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, "rsv210Naiyo");
                errorFlg = true;
            }
        }

        //予約チェック
        if (!errorFlg) {
            ArrayList<String> hiddArray = __getIkkatuKey();

            RsvSisDataDao dataDao = new RsvSisDataDao(con);
            HashMap<Integer, Rsv210Model> map =
                dataDao.getIkkatuTorokuGroupMap(hiddArray);

            for (String key : hiddArray) {

                errorFlg = false;

                //施設の情報を取得
                String sisetuSid = key.substring(key.indexOf("-") + 1);

                Rsv210Model mapMdl = map.get(Integer.valueOf(sisetuSid));
                if (mapMdl == null) {
                    continue;
                }

                //予約可能期限チェック(期限が設定されていればチェックする)
                String kigen = mapMdl.getRsdProp6();
                if (!StringUtil.isNullZeroString(kigen)) {

                    //施設グループ管理者の場合は予約可能期限チェックをパスする
                    RsvCommonBiz rsvBiz = new RsvCommonBiz();
                    if (!rsvBiz.isGroupAdmin(con, Integer.valueOf(sisetuSid),
                            reqMdl.getSmodel().getUsrsid())) {

                        UDate now = new UDate();
                        UDate udKigen = now.cloneUDate();
                        udKigen.addDay(Integer.parseInt(kigen));

                        String kigenYmd = udKigen.getDateString();
                        String keyYmd = key.substring(0, key.indexOf("-"));

                        if (Integer.parseInt(keyYmd) > Integer.parseInt(kigenYmd)) {

                            UDate errDay = new UDate();
                            errDay.setDate(keyYmd);
                            String errDayStr =
                                    gsMsg.getMessage("cmn.year", new String[] {errDay.getStrYear()})
                                    + errDay.getStrMonth()
                                    + gsMsg.getMessage("cmn.month")
                                    + errDay.getStrDay()
                                    + gsMsg.getMessage("cmn.day") + "（"
                                    + UDateUtil.getStrWeek(errDay.getWeek(), reqMdl)
                                    + "） "
                                    + mapMdl.getRsdName();

                            String kigenStr =
                                    gsMsg.getMessage("cmn.comments")
                                    + mapMdl.getRsdProp6()
                                    + gsMsg.getMessage("cmn.days.after");

                            msg = new ActionMessage("error.kigen.over.sisetu",
                                    errDayStr, kigenStr);
                            StrutsUtil.addMessage(errors, msg, "sisetu" + key);
                            errorFlg = true;
                        }
                    }

                }

                //重複のチェック(重複登録 = 不可の場合にチェック)
                String tyohuku = mapMdl.getRsdProp7();
                if (!errorFlg
                        && !StringUtil.isNullZeroString(tyohuku)
                        && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {

                    RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);

                    UDate[] chkDate = __getYrkDate(key);
                    UDate udYrk = chkDate[0];
                    UDate chkFrDate = chkDate[1];
                    UDate chkToDate = chkDate[2];

                    //登録予定の時間帯に既に予約がある場合はエラー
                    if (!yrkDao.isYrkOk(-1, Integer.parseInt(sisetuSid), chkFrDate, chkToDate)) {

                        String errDayStr =
                                gsMsg.getMessage("cmn.year", new String[] {udYrk.getStrYear()})
                                + udYrk.getStrMonth()
                                + gsMsg.getMessage("cmn.month")
                                + udYrk.getStrDay()
                                + gsMsg.getMessage("cmn.day") + "（"
                                + UDateUtil.getStrWeek(udYrk.getWeek(), reqMdl)
                                + "） "
                                + mapMdl.getRsdName();

                        msg = new ActionMessage("error.yrk.exist.reserve", errDayStr);
                        StrutsUtil.addMessage(errors, msg, "tyohuku" + key);
                    }
                }
            }
        }


        //公開区分
        if (rsv210RsyPublic__ != GSConstReserve.PUBLIC_KBN_ALL
         && rsv210RsyPublic__ != GSConstReserve.PUBLIC_KBN_PLANS
         && rsv210RsyPublic__ != GSConstReserve.PUBLIC_KBN_GROUP
         && rsv210RsyPublic__ != GSConstReserve.PUBLIC_KBN_USRGRP
         && rsv210RsyPublic__ != GSConstReserve.PUBLIC_KBN_TITLE) {
            msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage("cmn.public.kbn"));
            StrutsUtil.addMessage(errors, msg, "rsv210rsyPublic");
        }

        //公開対象
        if (rsv210RsyPublic__ == GSConstReserve.PUBLIC_KBN_USRGRP) {
            //未選択チェック
            if (rsv210PubUsrGrpSid__ == null || rsv210PubUsrGrpSid__.length == 0) {
                msg = new ActionMessage("error.select.required.text",
                                        gsMsg.getMessage("main.exposed"));
                StrutsUtil.addMessage(
                        errors, msg, "sv_publicTarget");
            } else {
                //存在チェック
                ArrayList<Integer> grpSids = new ArrayList<Integer>();
                List<String> usrSids = new ArrayList<String>();
                for (String target : rsv210PubUsrGrpSid__) {
                    if (target.startsWith("G")) {
                        grpSids.add(NullDefault.getInt(
                                target.substring(1), -1));
                    } else {
                        if (NullDefault.getInt(
                                target, -1) > GSConstUser.USER_RESERV_SID) {
                            usrSids.add(target);
                        }
                    }
                }

                ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
                ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();

                //グループ存在チェック
                if (!grpSids.isEmpty()) {
                    UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                    glist = gdao.selectGroupNmListOrderbyClass(grpSids);
                }

                //ユーザ存在チェック
                if (!usrSids.isEmpty()) {
                    UserBiz userBiz = new UserBiz();
                    ulist = userBiz.getBaseUserList(con,
                                                    usrSids.toArray(new String[usrSids.size()]));
                }

                if (ulist.isEmpty() && glist.isEmpty()) {
                    msg = new ActionMessage("error.select.required.text",
                            gsMsg.getMessage("main.exposed"));
                    StrutsUtil.addMessage(
                            errors, msg, "rsv210PubUsrGrpSid");
                }
            }
        }

        //スケジュール重複チェック
        if (!timeError) {

            RelationBetweenScdAndRsvChkBiz schChkBiz =
                    new RelationBetweenScdAndRsvChkBiz(reqMdl, con);

            if (rsv210SchKbn__ == GSConstReserve.RSV_SCHKBN_GROUP) {
                String grpSid = getRsv210SchGroupSid();
                if (NullDefault.getInt(grpSid, -1) >= 0) {
                    //例外アクセス
                    schChkBiz.validateSpCaceGroupForSchedule(errors, grpSid,
                            sessionUsrSid, "rsv210SchGroupSid");
                }
            } else {

                String[] users = getSv_users();
                if (rsv210SchKbn__ == GSConstReserve.RSV_SCHKBN_USER
                        && users != null && users.length > 0) {

                    ArrayList<String> hiddArray = __getIkkatuKey();
                    List<UDate[]> dateList = new ArrayList<UDate[]>();
                    for (String key : hiddArray) {
                        UDate[] chkDate = __getYrkDate(key);
                        dateList.add(new UDate[] {chkDate[1], chkDate[2]});
                    }

                    //例外アクセス
                    schChkBiz.validateSpCaceUserForSchedule(
                            errors, users, sessionUsrSid, "sv_users");

                    schChkBiz.validateDateForSchedule(
                            errors,
                            dateList,
                            users,
                            0,
                            false,
                            sessionUsrSid,
                            "rsv210Date");
                }
            }

        }
        return errors;
    }

    /**
     * <br>[機  能] 一括登録のキーを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 一括登録のキー
     */
    private ArrayList<String> __getIkkatuKey() {
        ArrayList<String> hiddArray = new ArrayList<String>();
        String[] hiddStr = getRsvIkkatuTorokuKey();
        if (hiddStr != null && hiddStr.length > 0) {
            for (String key : hiddStr) {
                hiddArray.add(key);
            }
        }

        Collections.sort(hiddArray);

        return hiddArray;
    }

    /**
     * <br>[機  能] 施設予約日、施設予約開始日時、終了日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param key 一括登録のキー
     * @return 施設予約日、施設予約開始日時、終了日時
     */
    private UDate[] __getYrkDate(String key) {

        String ymdStr = key.substring(0, key.indexOf("-"));
        UDate udYrk = new UDate();
        udYrk.setDate(ymdStr);

        //予約開始
        UDate chkFrDate = new UDate();
        chkFrDate.setDate(
                udYrk.getYear(),
                udYrk.getMonth(),
                udYrk.getIntDay());
        chkFrDate.setHour(rsv210SelectedHourFr__);
        chkFrDate.setMinute(rsv210SelectedMinuteFr__);
        chkFrDate.setSecond(GSConstReserve.DAY_START_SECOND);
        chkFrDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //予約終了
        UDate chkToDate = new UDate();
        chkToDate.setDate(
                udYrk.getYear(),
                udYrk.getMonth(),
                udYrk.getIntDay());
        chkToDate.setHour(rsv210SelectedHourTo__);
        chkToDate.setMinute(rsv210SelectedMinuteTo__);
        chkToDate.setSecond(GSConstReserve.DAY_START_SECOND);
        chkToDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        return new UDate[] {udYrk, chkFrDate, chkToDate};
    }

    /**
     * <p>rsv210GroupSid を取得します。
     * @return rsv210GroupSid
     */
    public String getRsv210GroupSid() {
        return rsv210GroupSid__;
    }
    /**
     * <p>rsv210GroupSid をセットします。
     * @param rsv210GroupSid rsv210GroupSid
     */
    public void setRsv210GroupSid(String rsv210GroupSid) {
        rsv210GroupSid__ = rsv210GroupSid;
    }
    /**
     * <p>schedulePluginKbn を取得します。
     * @return schedulePluginKbn
     */
    public int getSchedulePluginKbn() {
        return schedulePluginKbn__;
    }
    /**
     * <p>schedulePluginKbn をセットします。
     * @param schedulePluginKbn schedulePluginKbn
     */
    public void setSchedulePluginKbn(int schedulePluginKbn) {
        schedulePluginKbn__ = schedulePluginKbn;
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
     * @param svUsers sv_users
     */
    public void setSv_users(String[] svUsers) {
        sv_users__ = svUsers;
    }
    /**
     * <p>userNameArray を取得します。
     * @return userNameArray
     */
    public ArrayList<UsrLabelValueBean> getUserNameArray() {
        return userNameArray__;
    }
    /**
     * <p>userNameArray をセットします。
     * @param userNameArray userNameArray
     */
    public void setUserNameArray(ArrayList<UsrLabelValueBean> userNameArray) {
        userNameArray__ = userNameArray;
    }
    /**
     * <p>rsv210ScheduleUserUI を取得します。
     * @return rsv210ScheduleUserUI
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210ScheduleUserUI__
     */
    public RsvScheduleSelector getRsv210ScheduleUserUI() {
        return rsv210ScheduleUserUI__;
    }
    /**
     * <p>rsv210ScheduleUserUI をセットします。
     * @param rsv210ScheduleUserUI rsv210ScheduleUserUI
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210ScheduleUserUI__
     */
    public void setRsv210ScheduleUserUI(RsvScheduleSelector rsv210ScheduleUserUI) {
        rsv210ScheduleUserUI__ = rsv210ScheduleUserUI;
    }
    /**
     * <p>rsv210SchCrangeKbn を取得します。
     * @return rsv210SchCrangeKbn
     */
    public int getRsv210SchCrangeKbn() {
        return rsv210SchCrangeKbn__;
    }
    /**
     * <p>rsv210SchCrangeKbn をセットします。
     * @param rsv210SchCrangeKbn rsv210SchCrangeKbn
     */
    public void setRsv210SchCrangeKbn(int rsv210SchCrangeKbn) {
        rsv210SchCrangeKbn__ = rsv210SchCrangeKbn;
    }
    /**
     * <p>rsv210SchNotAccessGroupList を取得します。
     * @return rsv210SchNotAccessGroupList
     */
    public List<Integer> getRsv210SchNotAccessGroupList() {
        return rsv210SchNotAccessGroupList__;
    }
    /**
     * <p>rsv210SchNotAccessGroupList をセットします。
     * @param rsv210SchNotAccessGroupList rsv210SchNotAccessGroupList
     */
    public void setRsv210SchNotAccessGroupList(
            List<Integer> rsv210SchNotAccessGroupList) {
        rsv210SchNotAccessGroupList__ = rsv210SchNotAccessGroupList;
    }
    /**
     * <p>rsv210SchGroupLabel を取得します。
     * @return rsv210SchGroupLabel
     */
    public List<ExtendedLabelValueModel> getRsv210SchGroupLabel() {
        return rsv210SchGroupLabel__;
    }
    /**
     * <p>rsv210SchGroupLabel をセットします。
     * @param rsv210SchGroupLabel rsv210SchGroupLabel
     */
    public void setRsv210SchGroupLabel(List<ExtendedLabelValueModel> rsv210SchGroupLabel) {
        rsv210SchGroupLabel__ = rsv210SchGroupLabel;
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
     * <p>rsv210YrkFrom を取得します。
     * @return rsv210YrkFrom
     */
    public String getRsv210YrkFrom() {
        return rsv210YrkFrom__;
    }
    /**
     * <p>rsv210YrkFrom をセットします。
     * @param rsv210YrkFrom rsv210YrkFrom
     */
    public void setRsv210YrkFrom(String rsv210YrkFrom) {
        rsv210YrkFrom__ = rsv210YrkFrom;
    }
    /**
     * <p>rsv210AmFrHour を取得します。
     * @return rsv210AmFrHour
     */
    public int getRsv210AmFrHour() {
        return rsv210AmFrHour__;
    }
    /**
     * <p>rsv210AmFrHour をセットします。
     * @param rsv210AmFrHour rsv210AmFrHour
     */
    public void setRsv210AmFrHour(int rsv210AmFrHour) {
        rsv210AmFrHour__ = rsv210AmFrHour;
    }
    /**
     * <p>rsv210AmFrMin を取得します。
     * @return rsv210AmFrMin
     */
    public int getRsv210AmFrMin() {
        return rsv210AmFrMin__;
    }
    /**
     * <p>rsv210AmFrMin をセットします。
     * @param rsv210AmFrMin rsv210AmFrMin
     */
    public void setRsv210AmFrMin(int rsv210AmFrMin) {
        rsv210AmFrMin__ = rsv210AmFrMin;
    }
    /**
     * <p>rsv210AmToHour を取得します。
     * @return rsv210AmToHour
     */
    public int getRsv210AmToHour() {
        return rsv210AmToHour__;
    }
    /**
     * <p>rsv210AmToHour をセットします。
     * @param rsv210AmToHour rsv210AmToHour
     */
    public void setRsv210AmToHour(int rsv210AmToHour) {
        rsv210AmToHour__ = rsv210AmToHour;
    }
    /**
     * <p>rsv210AmToMin を取得します。
     * @return rsv210AmToMin
     */
    public int getRsv210AmToMin() {
        return rsv210AmToMin__;
    }
    /**
     * <p>rsv210AmToMin をセットします。
     * @param rsv210AmToMin rsv210AmToMin
     */
    public void setRsv210AmToMin(int rsv210AmToMin) {
        rsv210AmToMin__ = rsv210AmToMin;
    }
    /**
     * <p>rsv210PmFrHour を取得します。
     * @return rsv210PmFrHour
     */
    public int getRsv210PmFrHour() {
        return rsv210PmFrHour__;
    }
    /**
     * <p>rsv210PmFrHour をセットします。
     * @param rsv210PmFrHour rsv210PmFrHour
     */
    public void setRsv210PmFrHour(int rsv210PmFrHour) {
        rsv210PmFrHour__ = rsv210PmFrHour;
    }
    /**
     * <p>rsv210PmFrMin を取得します。
     * @return rsv210PmFrMin
     */
    public int getRsv210PmFrMin() {
        return rsv210PmFrMin__;
    }
    /**
     * <p>rsv210PmFrMin をセットします。
     * @param rsv210PmFrMin rsv210PmFrMin
     */
    public void setRsv210PmFrMin(int rsv210PmFrMin) {
        rsv210PmFrMin__ = rsv210PmFrMin;
    }
    /**
     * <p>rsv210PmToHour を取得します。
     * @return rsv210PmToHour
     */
    public int getRsv210PmToHour() {
        return rsv210PmToHour__;
    }
    /**
     * <p>rsv210PmToHour をセットします。
     * @param rsv210PmToHour rsv210PmToHour
     */
    public void setRsv210PmToHour(int rsv210PmToHour) {
        rsv210PmToHour__ = rsv210PmToHour;
    }
    /**
     * <p>rsv210PmToMin を取得します。
     * @return rsv210PmToMin
     */
    public int getRsv210PmToMin() {
        return rsv210PmToMin__;
    }
    /**
     * <p>rsv210PmToMin をセットします。
     * @param rsv210PmToMin rsv210PmToMin
     */
    public void setRsv210PmToMin(int rsv210PmToMin) {
        rsv210PmToMin__ = rsv210PmToMin;
    }
    /**
     * <p>rsv210AllDayFrHour を取得します。
     * @return rsv210AllDayFrHour
     */
    public int getRsv210AllDayFrHour() {
        return rsv210AllDayFrHour__;
    }
    /**
     * <p>rsv210AllDayFrHour をセットします。
     * @param rsv210AllDayFrHour rsv210AllDayFrHour
     */
    public void setRsv210AllDayFrHour(int rsv210AllDayFrHour) {
        rsv210AllDayFrHour__ = rsv210AllDayFrHour;
    }
    /**
     * <p>rsv210AllDayFrMin を取得します。
     * @return rsv210AllDayFrMin
     */
    public int getRsv210AllDayFrMin() {
        return rsv210AllDayFrMin__;
    }
    /**
     * <p>rsv210AllDayFrMin をセットします。
     * @param rsv210AllDayFrMin rsv210AllDayFrMin
     */
    public void setRsv210AllDayFrMin(int rsv210AllDayFrMin) {
        rsv210AllDayFrMin__ = rsv210AllDayFrMin;
    }
    /**
     * <p>rsv210AllDayToHour を取得します。
     * @return rsv210AllDayToHour
     */
    public int getRsv210AllDayToHour() {
        return rsv210AllDayToHour__;
    }
    /**
     * <p>rsv210AllDayToHour をセットします。
     * @param rsv210AllDayToHour rsv210AllDayToHour
     */
    public void setRsv210AllDayToHour(int rsv210AllDayToHour) {
        rsv210AllDayToHour__ = rsv210AllDayToHour;
    }
    /**
     * <p>rsv210AllDayToMin を取得します。
     * @return rsv210AllDayToMin
     */
    public int getRsv210AllDayToMin() {
        return rsv210AllDayToMin__;
    }
    /**
     * <p>rsv210AllDayToMin をセットします。
     * @param rsv210AllDayToMin rsv210AllDayToMin
     */
    public void setRsv210AllDayToMin(int rsv210AllDayToMin) {
        rsv210AllDayToMin__ = rsv210AllDayToMin;
    }
    /**
     * <p>rsv210PubDefUsrSid を取得します。
     * @return rsv210PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubDefUsrSid__
     */
    public int getRsv210PubDefUsrSid() {
        return rsv210PubDefUsrSid__;
    }
    /**
     * <p>rsv210PubDefUsrSid をセットします。
     * @param rsv210PubDefUsrSid rsv210PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubDefUsrSid__
     */
    public void setRsv210PubDefUsrSid(int rsv210PubDefUsrSid) {
        rsv210PubDefUsrSid__ = rsv210PubDefUsrSid;
    }
    /**
     * <p>rsv210PubUserGroup を取得します。
     * @return rsv210PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubUserGroup__
     */
    public String getRsv210PubUserGroup() {
        return rsv210PubUserGroup__;
    }
    /**
     * <p>rsv210PubUserGroup をセットします。
     * @param rsv210PubUserGroup rsv210PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubUserGroup__
     */
    public void setRsv210PubUserGroup(String rsv210PubUserGroup) {
        rsv210PubUserGroup__ = rsv210PubUserGroup;
    }
    /**
     * <p>rsv210PubUsrGrpSid を取得します。
     * @return rsv210PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubUsrGrpSid__
     */
    public String[] getRsv210PubUsrGrpSid() {
        return rsv210PubUsrGrpSid__;
    }
    /**
     * <p>rsv210PubUsrGrpSid をセットします。
     * @param rsv210PubUsrGrpSid rsv210PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubUsrGrpSid__
     */
    public void setRsv210PubUsrGrpSid(String[] rsv210PubUsrGrpSid) {
        rsv210PubUsrGrpSid__ = rsv210PubUsrGrpSid;
    }
    /**
     * <p>rsv210PubUsrGrpUI を取得します。
     * @return rsv210PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubUsrGrpUI__
     */
    public UserGroupSelector getRsv210PubUsrGrpUI() {
        return rsv210PubUsrGrpUI__;
    }
    /**
     * <p>rsv210PubUsrGrpUI をセットします。
     * @param rsv210PubUsrGrpUI rsv210PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210PubUsrGrpUI__
     */
    public void setRsv210PubUsrGrpUI(UserGroupSelector rsv210PubUsrGrpUI) {
        rsv210PubUsrGrpUI__ = rsv210PubUsrGrpUI;
    }
    /**
     * <p>rsv210TimeFr を取得します。
     * @return rsv210TimeFr
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210TimeFr__
     */
    public String getRsv210TimeFr() {
        return rsv210TimeFr__;
    }
    /**
     * <p>rsv210TimeFr をセットします。
     * @param rsv210TimeFr rsv210TimeFr
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210TimeFr__
     */
    public void setRsv210TimeFr(String rsv210TimeFr) {
        rsv210TimeFr__ = rsv210TimeFr;
    }
    /**
     * <p>rsv210TimeTo を取得します。
     * @return rsv210TimeTo
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210TimeTo__
     */
    public String getRsv210TimeTo() {
        return rsv210TimeTo__;
    }
    /**
     * <p>rsv210TimeTo をセットします。
     * @param rsv210TimeTo rsv210TimeTo
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210TimeTo__
     */
    public void setRsv210TimeTo(String rsv210TimeTo) {
        rsv210TimeTo__ = rsv210TimeTo;
    }
    /**
     * <p>rsv210HourDiv を取得します。
     * @return rsv210HourDiv
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210HourDiv__
     */
    public int getRsv210HourDiv() {
        return rsv210HourDiv__;
    }
    /**
     * <p>rsv210HourDiv をセットします。
     * @param rsv210HourDiv rsv210HourDiv
     * @see jp.groupsession.v2.rsv.rsv210.Rsv210Form#rsv210HourDiv__
     */
    public void setRsv210HourDiv(int rsv210HourDiv) {
        rsv210HourDiv__ = rsv210HourDiv;
    }
}