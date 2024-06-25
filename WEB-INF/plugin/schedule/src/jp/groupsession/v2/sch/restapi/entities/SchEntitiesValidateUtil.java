package jp.groupsession.v2.sch.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 複数のメソッドから実行されるバリデートを外だし
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchEntitiesValidateUtil {
    /** 処理モード 登録・編集*/
    public static final int MODE_EDIT = 0;
    /** 処理モード 削除*/
    public static final int MODE_DELETE = 1;
    /** 処理モード 出欠応答*/
    public static final int MODE_ANSER = 2;
    /** 処理モード リマインダー通知*/
    public static final int MODE_EDIT_REMIND = 3;

    /**
     *
     * <br>[機  能] 施設予約登録権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param ctx
     * @return 施設予約登録権限
     * @throws SQLException
     */
    public static Collection<? extends RestApiPermissionException> validateReserve(
            IPostParamModel param,
            RestApiContext ctx) throws SQLException {
        List<RestApiPermissionException> valErr = new ArrayList<>();
        if (param.getUseTimeFlg() != GSConstSchedule.TIME_EXIST) {
            return valErr;
        }
        if (param.getFacilityIdArray() == null) {
            return valErr;
        }
        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();
        GsMessage gsMsg = new GsMessage(reqMdl);

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
        RsvCommonBiz rsvBiz = new RsvCommonBiz();

        Map<String, RsvSisDataModel> rsdMap = param.getFacilityMap(ctx);
        String[] rsdIds = param.getFacilityIdArray();
        UDate frDate = param.getStartDateTime();
        UDate toDate = param.getEndDateTime();

        for (int i = 0; i < rsdIds.length; i++) {
            if (!rsdMap.containsKey(rsdIds[i])) {
                continue;
            }
            int rsdSid = rsdMap.get(rsdIds[i]).getRsdSid();
            RsvSisDataModel dataMdl = rsdMap.get(rsdIds[i]);
            if (dataMdl == null) {
                continue;
            }
            //予約可能期限チェック(期限が設定されていればチェックする)
            String kigen = dataMdl.getRsdProp6();
            if (!StringUtil.isNullZeroString(kigen)) {

                //施設グループ管理者の場合は予約可能期限チェックをパスする
                if (!rsvBiz.isGroupAdmin(con, rsdSid,
                        reqMdl.getSmodel().getUsrsid())) {
                    UDate now = new UDate();
                    UDate udKigen = now.cloneUDate();
                    udKigen.addDay(Integer.parseInt(kigen));

                    String kigenYmd = udKigen.getDateString();
                    String chkYmd = toDate.getDateString();

                    if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {
                        String textDayAfter = gsMsg.getMessage("cmn.days.after");
                        String kigenStr =
                                "※"
                                        + dataMdl.getRsdProp6()
                                        + textDayAfter;
                        valErr.add(new RestApiPermissionException(
                                EnumError.PARAM_OTHER_INVALID,
                                "error.kigen.over2.sisetu", kigenStr)
                                );
                    }
                }
            }
            //重複のチェック(重複登録 = 不可の場合にチェック)
            String tyohuku = dataMdl.getRsdProp7();
            if (!StringUtil.isNullZeroString(tyohuku)
                    && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {

                List<RsvSisYrkModel> ngList = new ArrayList<RsvSisYrkModel>();
                //施設予約重複チェック
                //編集モード
                if (param instanceof IPutParamModel) {
                    ArrayList<RsvSisYrkModel> yrkList = null;
                    SchDataModel oldMdl = ((ITargetParamModel) param).getOldData(ctx);
                    if (oldMdl.getScdRsSid() != -1) {
                        yrkList = yrkDao.getScheduleRserveData(
                                oldMdl.getScdRsSid()
                                );
                    }
                    RsvSisYrkModel yrkMdl = null;

                    yrkMdl = yrkList.stream()
                            .filter(yrk -> yrk.getRsdSid() == rsdSid)
                            .findAny()
                            .orElse(null);

                    if (yrkMdl == null) {
                        ngList = yrkDao.getYrkNgList(
                                -1, rsdSid, frDate, toDate);

                    } else {
                        ngList = yrkDao.getYrkNgList(
                                yrkMdl.getRsySid(), yrkMdl.getRsdSid(), frDate, toDate);
                    }
                //新規モード

                } else if (param instanceof IPostParamModel) {

                    ngList = yrkDao.getYrkNgList(-1, rsdSid,
                            frDate, toDate);

                }

                //重複チェック
                if (ngList != null && ngList.size() > 0) {

                    for (RsvSisYrkModel yrkModel : ngList) {

                        String schTime = UDateUtil.getYymdJ(yrkModel.getRsyFrDate(),
                                reqMdl);
                        schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyFrDate(),
                                reqMdl);
                        schTime += "～";
                        schTime += UDateUtil.getYymdJ(yrkModel.getRsyToDate(), reqMdl);
                        schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyToDate(),
                                reqMdl);
                        valErr.add(
                                new RestApiPermissionException(
                                    ReasonCode.EnumError.DUPLICATION,
                                    "errors.free.msg",
                                    String.format("%s %s",
                                        ctx.getMessageResources()
                                        .getMessage(
                                                reqMdl.getLocale(),
                                                "error.select.dup.list",
                                                gsMsg.getMessage("cmn.reserve")
                                                ),
                                        ctx.getMessageResources()
                                        .getMessage(
                                                reqMdl.getLocale(),
                                                "error.input.dup.sch",
                                                schTime,
                                                yrkModel.getRsdName(),
                                                yrkModel.getRsyMok()
                                                )

                                        )
                                    )
                                );

                    }
                }
            }
        }

        return valErr;
    }
    /**
     *
     * <br>[機  能] 同時登録スケジュール重複チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @param ctx RESTAPIコンテキスト
     * @param mode 1:NG 2:警告を表示
     * @return 入力チェック例外リスト
     * @throws SQLException
     */
    public static Collection<? extends RestApiPermissionException> validateSchRepeatCheck(
            IPostParamModel param,
            RestApiContext ctx, int mode) throws SQLException {
        List<RestApiPermissionException> valErr = new ArrayList<>();

        //グループスケジュールの場合はチェックを行わない
        if (param.getTargetType() == GSConstSchedule.USER_KBN_GROUP) {
            return valErr;
        }

        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();
        int sessionUsrSid = ctx.getRequestUserSid();
        GsMessage gsMsg = new GsMessage(reqMdl);

        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

        //重複登録 NGスケジュール一覧を取得する。
        List<SchDataModel> rptSchList
        = SchEntitiesValidateUtil.getSchWarningList(param, ctx, mode);
        if (rptSchList != null && rptSchList.size() > 0) {


            String title = "";
            for (SchDataModel ngMdl : rptSchList) {

                //公開区分で判定してタイトルを取得
                title = schBiz.getDspTitle(ngMdl, sessionUsrSid);

                String schTime = UDateUtil.getYymdJ(ngMdl.getScdFrDate(), reqMdl);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdFrDate(), reqMdl);
                schTime += "～";
                schTime += UDateUtil.getYymdJ(ngMdl.getScdToDate(), reqMdl);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdToDate(), reqMdl);
                valErr.add(
                        new RestApiPermissionException(
                            ReasonCode.EnumError.DUPLICATION,
                            "errors.free.msg",
                            String.format("%s %s",
                                ctx.getMessageResources()
                                .getMessage(
                                        reqMdl.getLocale(),
                                        "error.select.dup.list",
                                        gsMsg.getMessage("schedule.108")
                                        ),
                                ctx.getMessageResources()
                                .getMessage(
                                        reqMdl.getLocale(),
                                        "error.input.dup.sch",
                                        schTime,
                                        title,
                                        ngMdl.getScdUserName()
                                        )

                                )
                            )
                        );

            }
        }


        return valErr;
    }
    /**
     * <br>[機  能] 重複登録の警告スケジュール一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param ctx RESTAPIコンテキスト
     * @param mode 1:NG 2:警告を表示
     * @return 警告スケジュールリスト
     * @throws SQLException SQLExceptionm
     */
    public static List<SchDataModel> getSchWarningList(
            IPostParamModel param,
            RestApiContext ctx,
            int mode
            ) throws SQLException {
        List<SchDataModel> rptSchList = new ArrayList<SchDataModel>();

        if (param.getUseTimeFlg()
                == GSConstSchedule.TIME_NOT_EXIST) {
            return rptSchList;
        }
        if (param.getTargetId() == null) {
            return rptSchList;
        }
        if (param.getStartDateTime() == null) {
            return rptSchList;
        }
        if (param.getEndDateTime() == null) {
            return rptSchList;
        }

        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();
        int sessionUsrSid = ctx.getRequestUserSid();

        //同時登録メンバー
        String[] sv_userId = param.getSameScheduledUserIdArray();

        //個人設定を取得する。
        SchPriConfModel priModel = param.getPconf(ctx);

        //自分の予定の場合は編集可能フラグ
        SchCommonBiz schBiz = new SchCommonBiz(reqMdl);
        SchRepeatKbnModel repertMdl = schBiz.getRepertKbn(con, priModel, sessionUsrSid);
        boolean mySchOkFlg = repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK;

        SchPriConfDao priConfDao = new SchPriConfDao(con);

        //ユーザリストを作成
        List<Integer> usrSidList =
                new ArrayList<>(
                        Arrays.stream(sv_userId)
                        .filter(id -> param.getUserMap(ctx).containsKey(id))
                        .map(id -> param.getUserMap(ctx).get(id).getUsrSid())
                        .collect(Collectors.toList()));

        //ユーザリストに被登録者を含める
        if (param.getTargetType() == GSConstSchedule.USER_KBN_USER) {
            int targetSid = param.getUserMap(ctx).get(param.getTargetId()).getUsrSid();
            if (!mySchOkFlg
                    || param.getUserMap(
                            ctx).get(param.getTargetId()).getUsrSid() == sessionUsrSid) {
                usrSidList.add(targetSid);
            }

        }
        UDate frDate = param.getStartDateTime();
        UDate toDate = param.getEndDateTime();

        int frYear = frDate.getYear();
        int frMonth = frDate.getMonth();
        int frDay = frDate.getIntDay();

        int frHour = frDate.getIntHour();
        int frMin = frDate.getIntMinute();

        int toYear = toDate.getYear();
        int toMonth = toDate.getMonth();
        int toDay = toDate.getIntDay();


        int toHour = toDate.getIntHour();
        int toMin = toDate.getIntMinute();
        int toSec = GSConstSchedule.DAY_START_SECOND;
        int toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;


        //予約開始
        UDate chkFrDate = new UDate();
        chkFrDate.setDate(frYear, frMonth, frDay);
        chkFrDate.setHour(frHour);
        chkFrDate.setMinute(frMin);
        chkFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        chkFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

        //予約終了
        UDate chkToDate = new UDate();
        chkToDate.setDate(toYear, toMonth, toDay);
        chkToDate.setHour(toHour);
        chkToDate.setMinute(toMin);
        chkToDate.setSecond(toSec);
        chkToDate.setMilliSecond(toMiliSec);


        //編集スケジュールSID
        int schSid = 0;
        if (param instanceof IPutParamModel) {
            schSid =
                    Optional.ofNullable(
                            ((IPutParamModel) param).getScheduleSid()
                            )
                    .orElse(0);
        }

        SchDataDao schDao = new SchDataDao(con);
        int schGrpSid = -1;
        int batchRef = GSConstSchedule.SAME_EDIT_OFF;
        if (param instanceof IPutParamModel) {
            batchRef =
                    ((IPutParamModel) param).getSameScheduledEditFlg();
        }
        if (batchRef == 1) {
            //同時修正する場合
            SchDataModel bean = new SchDataModel();
            bean.setScdSid(schSid);
            SchDataModel schModel = schDao.select(bean);

            if (schModel != null) {
                schGrpSid = schModel.getScdGrpSid();
            }
        }
        SchAdmConfModel admConf = param.getAconf(ctx);
        boolean canEditRepeatKbn = schBiz.canEditRepertKbn(admConf);
        if (mode == GSConstSchedule.SCH_REPEAT_KBN_NG) {
            List<Integer> ngUsrList = null;
            if (canEditRepeatKbn) {
                //重複登録不可にしているユーザリストを取得
                ngUsrList = priConfDao.getUsrSidListRepeatKbn(usrSidList,
                        GSConstSchedule.SCH_REPEAT_KBN_NG);
            } else {
                if (admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG) {
                    ngUsrList = new ArrayList<Integer>();
                    ngUsrList.addAll(usrSidList);
                }
            }
            if (ngUsrList != null && !ngUsrList.isEmpty()) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList =
                        schDao.getSchData(ngUsrList,
                                schSid,
                                chkFrDate,
                                chkToDate,
                                schGrpSid,
                                GSConstSchedule.NOT_COPY_FLG);
            }

        } else if (mode == GSConstSchedule.SCH_REPEAT_KBN_WARNING) {

            //重複登録警告にしているユーザリストを取得
            List<Integer> warningUsrList = null;
            if (canEditRepeatKbn) {
                warningUsrList = priConfDao.getUsrSidListRepeatKbn(usrSidList,
                        GSConstSchedule.SCH_REPEAT_KBN_WARNING);
            } else {
                warningUsrList = new ArrayList<Integer>();
                if (admConf.getSadRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_OK) {
                    warningUsrList.addAll(usrSidList);
                }
            }

            //セッションユーザをチェックに含める
            if (param.getTargetType() == GSConstSchedule.USER_KBN_USER) {
                int targetSid = param.getUserMap(ctx).get(param.getTargetId()).getUsrSid();
                if (mySchOkFlg
                        && param.getUserMap(ctx).get(
                                param.getTargetId()
                                ).getUsrSid() == sessionUsrSid) {
                    warningUsrList.add(targetSid);
                }

            }

            if (warningUsrList != null && !warningUsrList.isEmpty()) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList = schDao.getSchData(
                        warningUsrList,
                        schSid,
                        chkFrDate,
                        chkToDate,
                        schGrpSid,
                        GSConstSchedule.NOT_COPY_FLG);
            }
        }

        return rptSchList;
    }
    /**
     *
     * <br>[機  能] 施設予約編集権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param ctx
     * @return 入力チェック例外リスト
     * @throws SQLException
     */
    public static Collection<? extends RestApiValidateException> validateEditResPowerCheck(
            IDeleteParamModel param,
            RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();
        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();
        GsMessage gsMsg = new GsMessage(reqMdl);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con, reqMdl);


        //編集スケジュールSID
        int schSid = Optional.ofNullable(param.getScheduleSid())
                .orElse(0);

        //編集権限のない施設数を取得する。
        if (param.getSameFacilityReserveEditFlg() == 1) {

            boolean rsvAdmin = param.isRsvAdmin(ctx);
            int count = schCmnBiz.getCanNotEditRsvCount(ctx.getRequestUserSid(), schSid, rsvAdmin);

            if (count > 0) {
                //施設予約アクセス権限なし
                valErr.add(new RestApiValidateException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.myself.auth")
                        .setParamName(
                                "scheduleSid"
                                )
                        );
                return valErr;
            }

            String textChange = gsMsg.getMessage("cmn.change");

            RelationBetweenScdAndRsvChkBiz rsvChkBiz
            = new RelationBetweenScdAndRsvChkBiz(reqMdl, con);
            int errorCd = rsvChkBiz.isRsvEdit(
                    schSid,
                    RelationBetweenScdAndRsvChkBiz.CHK_KBN_TANITU);
            if (errorCd == RelationBetweenScdAndRsvChkBiz.ERR_CD_SCD_CANNOT_EDIT) {
                //施設予約に対する編集
                String textRsvEdit = gsMsg.getMessage("schedule.src.32");
                valErr.add(new RestApiValidateException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.edit.power.user", textRsvEdit, textChange)
                        .setParamName(
                                "scheduleSid"
                                )
                        );

            }

        }
        return valErr;
    }
    /**
     *
     * <br>[機  能] 編集権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param ctx RESTAPIコンテキスト
     * @return 例外リスト
     * @throws SQLException
     */
    public static List<RestApiValidateException> validateExistData(
            ITargetParamModel param,
            RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();
        RequestModel reqMdl = ctx.getRequestModel();
        Connection con = ctx.getCon();
        GsMessage gsMsg = new GsMessage(reqMdl);
        SchDataModel oldMdl = param.getOldData(ctx);
        if (oldMdl == null) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.none.edit.data",
                    gsMsg.getMessage("schedule.108"),
                    gsMsg.getMessage("cmn.edit")
                    )
                    .setParamName(
                            "scheduleSid"
                            );
        } else {
            //指定スケジュールの編集権限チェック
            SchCommonBiz schCmnBiz = new SchCommonBiz();
            if (!schCmnBiz.canRegistSchedule(con, oldMdl, reqMdl.getSmodel().getUsrsid())) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.scd.auth")
                        .setParamName(
                                "scheduleSid"
                                );
            }
        }


        return valErr;
    }

    /**
     *
     * <br>[機  能] 同時登録スケジュールの編集権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param ctx
     * @return 入力チェック例外リスト
     * @throws SQLException
     */
    public static List<RestApiValidateException> validateSchEditPowerCheck(
            ITargetParamModel param,
            RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();
        RequestModel reqMdl = ctx.getRequestModel();
        Connection con__ = ctx.getCon();
        GsMessage gsMsg = new GsMessage(reqMdl);
        SchDataModel scdMdl = param.getOldData(ctx);

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con__,
                usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);


        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con__);

        if (scdMdl == null) {
            return valErr;
        }

        //指定スケジュールの編集権限チェック
        SchCommonBiz schCmnBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
        if (!schCmnBiz.canRegistSchedule(con__, scdMdl, reqMdl.getSmodel().getUsrsid())) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.scd.auth")
                    .setParamName(
                            "scheduleSid"
                            );
        }

        //出欠確認スケジュールは単体削除不可
        if (param.getMode() == MODE_DELETE
                && param instanceof IDeleteParamModel
                && scdMdl.getScdAttendKbn() != GSConstSchedule.ATTEND_KBN_NO
                && ((IDeleteParamModel) param).getSameScheduledEditFlg()
                == GSConstSchedule.SAME_EDIT_OFF) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.edit.single.attend.schedule")
                    .setParamName(
                            "scheduleSid"
                            );
        }

        //出欠確認するかつ、出欠登録者区分が「１：登録者以外」かつ同時登録者に「０：登録者」が存在する場合、編集不可
        if (param.getMode() != MODE_ANSER
                && param.getMode() != MODE_EDIT_REMIND
                && scdMdl.getScdAttendKbn() != GSConstSchedule.ATTEND_KBN_NO
                && scdMdl.getScdAttendAuKbn() == GSConstSchedule.ATTEND_REGIST_USER_NO) {
            ArrayList<ScheduleSearchModel> schDataList = null;
            schDataList = schCmnBiz.getSchDataList(scdMdl.getScdSid(), adminConf, con__);
            boolean flg = false;
            if (param.getMode() == MODE_EDIT) {
                flg = true;
            }
            for (ScheduleSearchModel mdl : schDataList) {
                if (mdl.getScdAttendAuKbn() != GSConstSchedule.ATTEND_REGIST_USER_NO) {
                    flg = true;
                    break;
                }
            }
            if (flg) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.cant.edit.single.attend.schedule")
                        .setParamName(
                                "scheduleSid"
                                );
            }

        }
        //出欠応答時、出欠登録者区分が「０：登録者」の場合、編集不可
        if (param.getMode() == MODE_ANSER
                && scdMdl.getScdAttendKbn() != GSConstSchedule.ATTEND_KBN_NO
                && scdMdl.getScdAttendAuKbn() != GSConstSchedule.ATTEND_REGIST_USER_NO) {

            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.anser.attend.request.schedule")
                    .setParamName(
                            "scheduleSid"
                            );


        }
        //出欠応答時、出欠確認以外のスケジュールの場合
        if (param.getMode() == MODE_ANSER
                && scdMdl.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_NO) {

            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.none.edit.data",
                    gsMsg.getMessage("schedule.sch040.3"),
                    gsMsg.getMessage("cmn.edit")
                    )
                    .setParamName(
                            "scheduleSid"
                            );


        }


        //編集権限をチェックする
        int scdPublic = scdMdl.getScdPublic();

        boolean errorFlg = false;
        boolean douziErrorFlg = false;
        String textSimultaneousEdit = "";
        Sch010Biz biz = new Sch010Biz(reqMdl);
        //公開範囲が「公開」「所属グループのみ」
        if (scdPublic == GSConstSchedule.DSP_PUBLIC
                || scdPublic == GSConstSchedule.DSP_BELONG_GROUP) {

            if (!biz.isEditOk(scdMdl, sessionUsrSid, isAdmin, con__, false)) {
                textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                errorFlg = true;
            } else if (param instanceof IDeleteParamModel
                    && ((IDeleteParamModel) param).getSameScheduledEditFlg()
                    != GSConstSchedule.SAME_EDIT_OFF
                    && !biz.isAllEditOk(scdMdl, adminConf,
                            reqMdl, sessionUsrSid, isAdmin, con__)) {
                //同時登録スケジュールに対する編集
                douziErrorFlg = true;
            }

            //公開区分「非公開」・「予定あり」
        }  else if (scdPublic == GSConstSchedule.DSP_NOT_PUBLIC
                || scdPublic == GSConstSchedule.DSP_YOTEIARI) {

            //登録・編集者は許可
            if (scdMdl.getScdAuid() != sessionUsrSid
                    && scdMdl.getScdEuid() != sessionUsrSid) {
                GroupBiz gpBiz = new GroupBiz();
                //登録者・編集者・被登録者だけ編集可能(ユーザスケジュール)
                if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                    if (scdMdl.getScdUsrSid() != sessionUsrSid) {
                        errorFlg = true;
                    }
                } else {
                    //グループスケジュールで「本人」はいないためエラー
                    if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_OWN) {
                        errorFlg = true;
                        //所属グループ・未設定の場合は所属チェック
                    } else if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_GROUP
                            || scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_NONE) {
                        if (!gpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con__)) {
                            errorFlg = true;
                        }
                    }
                }
                if (errorFlg) {
                    textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                }

            }

            if (!errorFlg
                    && !biz.isEditOk(scdMdl, sessionUsrSid, isAdmin, con__, false)) {
                textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                errorFlg = true;
            //元スケジュールに編集権限がある場合のみ同時登録チェック
            } else if (!errorFlg
                    && param instanceof IDeleteParamModel
                    && ((IDeleteParamModel) param).getSameFacilityReserveEditFlg()
                    != GSConstSchedule.SAME_EDIT_OFF
                    && !biz.isAllEditOk(scdMdl, adminConf,
                            reqMdl, sessionUsrSid, isAdmin, con__)) {
                //同時登録スケジュールに対する編集
                douziErrorFlg = true;
            }

            //公開区分「指定ユーザ・グループのみ公開」
        }  else if (scdPublic == GSConstSchedule.DSP_USRGRP) {
            //登録・編集者は許可
            if (scdMdl.getScdAuid() != sessionUsrSid
                    && scdMdl.getScdEuid() != sessionUsrSid) {
                //登録者・編集者・被登録者だけ編集可能(ユーザスケジュール)
                if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                    if (scdMdl.getScdUsrSid() != sessionUsrSid) {
                        //対象スケジュール公開対象に存在しない場合、エラー
                        SchDataPubDao schPubDao = new SchDataPubDao(con__);
                        List<Integer> pubScdSidList
                        = schPubDao.getUserPubScdSidList(sessionUsrSid,
                                Arrays.asList(scdMdl.getScdSid()));
                        if (pubScdSidList.isEmpty()) {
                            errorFlg = true;
                            textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                        }
                    }
                }

            }
            if (!errorFlg
                    && !biz.isEditOk(scdMdl, sessionUsrSid, isAdmin, con__, false)) {
                textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                errorFlg = true;
                //元スケジュールに編集権限がある場合のみ同時登録チェック
            } else if (!errorFlg
                    && param instanceof IDeleteParamModel
                    && ((IDeleteParamModel) param).getSameScheduledEditFlg()
                    != GSConstSchedule.SAME_EDIT_OFF
                    && !biz.isAllEditOk(scdMdl, adminConf,
                            reqMdl, sessionUsrSid, isAdmin, con__)) {
                //同時登録スケジュールに対する編集
                douziErrorFlg = true;
            }

            //公開区分「タイトルのみ公開」
        }  else if (scdPublic == GSConstSchedule.DSP_TITLE) {

            //登録・編集者・被登録者は許可(ユーザスケジュール)
            if (scdMdl.getScdAuid() != sessionUsrSid
                    && scdMdl.getScdEuid() != sessionUsrSid) {
                if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                    if (scdMdl.getScdUsrSid() != sessionUsrSid) {
                        errorFlg = true;
                        textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                    }
                }
            }

            if (!errorFlg
                    && !biz.isEditOk(scdMdl, sessionUsrSid, isAdmin, con__, false)) {
                textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                errorFlg = true;
            //元スケジュールに編集権限がある場合のみ同時登録チェック
            } else if (!errorFlg
                    && param instanceof IDeleteParamModel
                    && ((IDeleteParamModel) param).getSameScheduledEditFlg()
                        != GSConstSchedule.SAME_EDIT_OFF
                    && !biz.isAllEditOk(scdMdl, adminConf,
                            reqMdl, sessionUsrSid, isAdmin, con__)) {
                //同時登録スケジュールに対する編集
                douziErrorFlg = true;
            }
        }

        if (douziErrorFlg) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.schedule.edit.cant.user")
                    .setParamName(
                            "scheduleSid"
                            );
        } else if (errorFlg) {
            String action = null;
            action = gsMsg.getMessage("cmn.edit");

            if (param instanceof IDeleteParamModel) {
                action = gsMsg.getMessage("cmn.delete");
            }
            if (param instanceof IPutParamModel) {
                action = gsMsg.getMessage("cmn.edit");
            }

            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.edit.power.user",
                    textSimultaneousEdit,
                    action)
                    .setParamName(
                            "scheduleSid"
                            );
        }

        return valErr;
    }
}
