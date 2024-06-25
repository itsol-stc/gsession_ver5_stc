package jp.groupsession.v2.sch.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.exception.RestApiExceptionNest;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchInitPubDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchInitPubModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.struts.msg.GsMessage;

public interface IPostParamModel {
    /**
     * <p>mode を取得します。
     * @return mode
     * @see SchEntitiesPostParamModel#cmn360mode__
     */
    default int getMode() {
        return SchEntitiesValidateUtil.MODE_EDIT;
    };

    /**
     * <p>targetType を取得します。
     * @return targetType
     * @see SchEntitiesPostParamModel#targetType__
     */
    int getTargetType();

    /**
     * <p>targetType をセットします。
     * @param targetType targetType
     * @see SchEntitiesPostParamModel#targetType__
     */
    void setTargetType(int targetType);

    /**
     * <p>targetId を取得します。
     * @return targetId
     * @see SchEntitiesPostParamModel#targetId__
     */
    String getTargetId();

    /**
     * <p>targetId をセットします。
     * @param targetId targetId
     * @see SchEntitiesPostParamModel#targetId__
     */
    void setTargetId(String targetId);

    /**
     * <p>startDateTime を取得します。
     * @return startDateTime
     * @see SchEntitiesPostParamModel#startDateTime__
     */
    UDate getStartDateTime();

    /**
     * <p>startDateTime をセットします。
     * @param startDateTime startDateTime
     * @see SchEntitiesPostParamModel#startDateTime__
     */
    void setStartDateTime(UDate startDateTime);

    /**
     * <p>endDateTime を取得します。
     * @return endDateTime
     * @see SchEntitiesPostParamModel#endDateTime__
     */
    UDate getEndDateTime();

    /**
     * <p>endDateTime をセットします。
     * @param endDateTime endDateTime
     * @see SchEntitiesPostParamModel#endDateTime__
     */
    void setEndDateTime(UDate endDateTime);

    /**
     * <p>useTimeFlg を取得します。
     * @return useTimeFlg
     * @see SchEntitiesPostParamModel#useTimeFlg__
     */
    int getUseTimeFlg();

    /**
     * <p>useTimeFlg をセットします。
     * @param useTimeFlg useTimeFlg
     * @see SchEntitiesPostParamModel#useTimeFlg__
     */
    void setUseTimeFlg(int useTimeFlg);

    /**
     * <p>titleText を取得します。
     * @return titleText
     * @see SchEntitiesPostParamModel#titleText__
     */
    String getTitleText();

    /**
     * <p>titleText をセットします。
     * @param titleText titleText
     * @see SchEntitiesPostParamModel#titleText__
     */
    void setTitleText(String titleText);

    /**
     * <p>colorType を取得します。
     * @return colorType
     * @see SchEntitiesPostParamModel#colorType__
     */
    Integer getColorType();

    /**
     * <p>colorType をセットします。
     * @param colorType colorType
     * @see SchEntitiesPostParamModel#colorType__
     */
    void setColorType(Integer colorType);

    /**
     * <p>bodyText をセットします。
     * @param bodyText bodyText
     * @see SchEntitiesPostParamModel#bodyText__
     */
    void setBodyText(String bodyText);

    /**
     * <p>memoText を取得します。
     * @return memoText
     * @see SchEntitiesPostParamModel#memoText__
     */
    String getMemoText();

    /**
     * <p>memoText をセットします。
     * @param memoText memoText
     * @see SchEntitiesPostParamModel#memoText__
     */
    void setMemoText(String memoText);

    /**
     * <p>publicType を取得します。
     * @return publicType
     * @see SchEntitiesPostParamModel#publicType__
     */
    Integer getPublicType();

    /**
     * <p>publicType をセットします。
     * @param publicType publicType
     * @see SchEntitiesPostParamModel#publicType__
     */
    void setPublicType(Integer publicType);

    /**
     * <p>publicTargetUserIdArray を取得します。
     * @return publicTargetUserIdArray
     * @see SchEntitiesPostParamModel#publicTargetUserIdArray__
     */
    String[] getPublicTargetUserIdArray();

    /**
     * <p>publicTargetUserIdArray をセットします。
     * @param publicTargetUserIdArray publicTargetUserIdArray
     * @see SchEntitiesPostParamModel#publicTargetUserIdArray__
     */
    void setPublicTargetUserIdArray(String[] publicTargetUserIdArray);

    /**
     * <p>publicTargetGroupIdArray を取得します。
     * @return publicTargetGroupIdArray
     * @see SchEntitiesPostParamModel#publicTargetGroupIdArray__
     */
    String[] getPublicTargetGroupIdArray();

    /**
     * <p>publicTargetGroupIdArray をセットします。
     * @param publicTargetGroupIdArray publicTargetGroupIdArray
     * @see SchEntitiesPostParamModel#publicTargetGroupIdArray__
     */
    void setPublicTargetGroupIdArray(String[] publicTargetGroupIdArray);

    /**
     * <p>remindGrpFlg を取得します。
     * @return remindGrpFlg
     * @see SchEntitiesPostParamModel#remindGrpFlg__
     */
    Integer getRemindGrpFlg();

    /**
     * <p>remindGrpFlg をセットします。
     * @param remindGrpFlg remindGrpFlg
     * @see SchEntitiesPostParamModel#remindGrpFlg__
     */
    void setRemindGrpFlg(Integer remindGrpFlg);

    /**
     * <p>remindTimingType を取得します。
     * @return remindTimingType
     * @see SchEntitiesPostParamModel#remindTimingType__
     */
    Integer getRemindTimingType();

    /**
     * <p>remindTimingType をセットします。
     * @param remindTimingType remindTimingType
     * @see SchEntitiesPostParamModel#remindTimingType__
     */
    void setRemindTimingType(Integer remindTimingType);

    /**
     * <p>useAttendFlg を取得します。
     * @return useAttendFlg
     * @see SchEntitiesPostParamModel#useAttendFlg__
     */
    int getUseAttendFlg();

    /**
     * <p>useAttendFlg をセットします。
     * @param useAttendFlg useAttendFlg
     * @see SchEntitiesPostParamModel#useAttendFlg__
     */
    void setUseAttendFlg(int useAttendFlg);

    /**
     * <p>sameScheduledUserIdArray を取得します。
     * @return sameScheduledUserIdArray
     * @see SchEntitiesPostParamModel#sameScheduledUserIdArray__
     */
    String[] getSameScheduledUserIdArray();

    /**
     * <p>sameScheduledUserIdArray をセットします。
     * @param sameScheduledUserIdArray sameScheduledUserIdArray
     * @see SchEntitiesPostParamModel#sameScheduledUserIdArray__
     */
    void setSameScheduledUserIdArray(String[] sameScheduledUserIdArray);

    /**
     * <p>facilityIdArray を取得します。
     * @return facilityIdArray
     * @see SchEntitiesPostParamModel#facilityIdArray__
     */
    String[] getFacilityIdArray();

    /**
     * <p>facilityIdArray をセットします。
     * @param facilityIdArray facilityIdArray
     * @see SchEntitiesPostParamModel#facilityIdArray__
     */
    void setFacilityIdArray(String[] facilityIdArray);

    /**
     * <p>useContactFlg を取得します。
     * @return useContactFlg
     * @see SchEntitiesPostParamModel#useContactFlg__
     */
    int getUseContactFlg();

    /**
     * <p>useContactFlg をセットします。
     * @param useContactFlg useContactFlg
     * @see SchEntitiesPostParamModel#useContactFlg__
     */
    void setUseContactFlg(int useContactFlg);

    /**
     * <p>clientCompanyBaseIdArray を取得します。
     * @return clientCompanyBaseIdArray
     * @see SchEntitiesPostParamModel#clientCompanyBaseIdArray__
     */
    String[] getClientCompanyBaseIdArray();

    /**
     * <p>clientCompanyBaseIdArray をセットします。
     * @param clientCompanyBaseIdArray clientCompanyBaseIdArray
     * @see SchEntitiesPostParamModel#clientCompanyBaseIdArray__
     */
    void setClientCompanyBaseIdArray(String[] clientCompanyBaseIdArray);

    /**
     * <p>clientAddressSidArray をセットします。
     * @param clientAddressSidArray clientAddressSidArray
     * @see SchEntitiesPostParamModel#clientAddressSidArray__
     */
    void setClientAddressSidArray(int[] clientAddressSidArray);



    /**
     *
     * <br>[機  能] 企業情報Map 取得
     * <br>[解  説] 2回目以降はキャッシュを利用する
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @return 企業情報
     * @throws SQLException
     */
    Map<String, AdrCompanyModel> getClientCompanyMap(RestApiContext ctx)
            throws SQLException;

    /**
     *
     * <br>[機  能] 企業拠点情報Map 取得
     * <br>[解  説] 2回目以降はキャッシュを利用する
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @return 企業拠点情報
     * @throws SQLException
     */
    Map<Integer, AdrCompanyBaseModel> getClientBaseMap(RestApiContext ctx)
            throws SQLException;

    /**
     *
     * <br>[機  能] アドレス情報Map取得
     * <br>[解  説] 2回目以降はキャッシュを利用する
     * <br>[備  考]
     * @param ctx
     * @return アドレス情報
     */
    Map<Integer, Sch040AddressModel> getAddresMap(RestApiContext ctx)
            throws SQLException;

    /**
     *
     * <br>[機  能] グループ情報Map取得
     * <br>[解  説] 2回目以降はキャッシュを利用する
     * <br>[備  考]
     * @param ctx
     * @return グループ情報
     */
    Map<String, CmnGroupmModel> getGroupMap(RestApiContext ctx);

    /**
     *
     * <br>[機  能] ユーザ情報Map 取得
     * <br>[解  説] 2回目以降はキャッシュを利用する
     * <br>[備  考]
     * @param ctx
     * @return ユーザ情報
     * @throws SQLException
     */
    Map<String, CmnUserModel> getUserMap(RestApiContext ctx);

    /**
     *
     * <br>[機  能] 登録対象施設情報 MAPを取得する
     * <br>[解  説] 2回目以降はキャッシュを利用する
     * <br>[備  考]
     * @param ctx
     * @return 登録対象施設情報
     */
    Map<String, RsvSisDataModel> getFacilityMap(RestApiContext ctx);

    /**
     * <p>rsvAdmin を取得します。
     * @param ctx
     * @return rsvAdmin
     * @see SchEntitiesPostParamModel#rsvAdmin__
     */
    boolean isRsvAdmin(RestApiContext ctx);

    /**
     *
     * <br>[機  能] リマインダーデフォルト設定を取得する
     * <br>[解  説] ２回目以降はキャッシュを返す
     * <br>[備  考]
     * @param ctx
     * @return defConf
     * @throws SQLException
     */
    SchPriPushModel getRemindDefConf(RestApiContext ctx) throws SQLException;

    /**
        *
        * <br>[機  能] 個人設定を取得する
        * <br>[解  説] ２回目以降はキャッシュを返す
        * <br>[備  考]
        * @param ctx
        * @return defConf
        * @throws SQLException
        */
    SchPriConfModel getPconf(RestApiContext ctx) throws SQLException;

    /**
        *
        * <br>[機  能] 管理者設定を取得する
        * <br>[解  説] ２回目以降はキャッシュを返す
        * <br>[備  考]
        * @param ctx
        * @return defConf
        * @throws SQLException
        */
    SchAdmConfModel getAconf(RestApiContext ctx) throws SQLException;

    /**
     * <p>bodyText を取得します。
     * @return bodyText
     * @see SchEntitiesPostParamModel#bodyText__
     */
    String getBodyText();

    /**
     * <p>editType を取得します。
     * @return editType
     * @see SchEntitiesPostParamModel#editType__
     */
    Integer getEditType();

    /**
     * <p>editType をセットします。
     * @param editType editType
     * @see SchEntitiesPostParamModel#editType__
     */
    void setEditType(Integer editType);
    /**
     *
     * <br>[機  能] パラメータ初期化設定
     * <br>[解  説] バリデート実行前に呼ぶ
     * <br>[備  考]
     * @param ctx
     * @throws SQLException
     */
    default void defaultInit(RestApiContext ctx) throws SQLException {
        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();
        int sessionUsrSid = ctx.getRequestUserSid();

        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, sessionUsrSid);
        //管理者設定を取得
        SchAdmConfModel adminConf = biz.getAdmConfModel(con);

        //タイトルカラー
        if (getColorType() == null) {
            setColorType(pconf.getSccIniFcolor());
        }
        //編集区分
        if (getEditType() == null) {
            setEditType(biz.getInitEditAuth(con, pconf)); // pconf.getSccIniEdit();
        }

        //公開区分(ユーザスケジュール)
        if (getPublicType() == null
                && getTargetType() == GSConstSchedule.USER_KBN_USER) {
            setPublicType(biz.getInitPubAuth(con, pconf));  // pconf.getSccIniPublic();

            //公開対象
            SchInitPubDao sipDao = new SchInitPubDao(con);
            List<SchInitPubModel> sipMdlList = new ArrayList<SchInitPubModel>();
            int publicStype = adminConf.getSadInitPublicStype();
            if (publicStype == GSConstSchedule.SAD_INIPUBLIC_STYPE_ADM
            && adminConf.getSadIniPublic() == GSConstSchedule.DSP_USRGRP) {
                sipMdlList = sipDao.select(0);
            } else if (publicStype == GSConstSchedule.SAD_INIPUBLIC_STYPE_USER
            && pconf.getSccIniPublic() == GSConstSchedule.DSP_USRGRP) {
                sipMdlList = sipDao.select(sessionUsrSid);
            }

            //ユーザ情報Map
            Map<Integer, CmnUserModel> userMap = new HashMap<Integer, CmnUserModel>();
            userMap.putAll(
                    new UserSearchDao(con)
                    .getUsersDataList(
                            sipMdlList.stream()
                                .filter(sipMdl ->
                                    sipMdl.getSipType() != GSConstSchedule.USER_KBN_GROUP)
                                .map(sipMdl -> sipMdl.getSipPsid())
                                .collect(Collectors.toSet())
                            )
                    .stream()
                    .collect(Collectors.toMap(
                            usr -> usr.getUsrSid(),
                            usr -> usr))
                    );
            setPublicTargetUserIdArray(
                    sipMdlList.stream()
                    .filter(sipMdl ->
                    (sipMdl.getSipType() == GSConstSchedule.USER_KBN_USER
                            && userMap.containsKey(sipMdl.getSipPsid())
                            )
                    )
                    .map(sipMdl -> userMap.get(sipMdl.getSipPsid()).getUsrLgid())
                    .toArray(String[]::new)
                    );

            //グループ情報Map
            Map<Integer, CmnGroupmModel> groupMap = new HashMap<Integer, CmnGroupmModel>();
            int[] grpSids = sipMdlList.stream()
                .filter(sipMdl -> sipMdl.getSipType() == GSConstSchedule.USER_KBN_GROUP)
                .mapToInt(sipMdl -> sipMdl.getSipPsid())
                .toArray();
            if (grpSids.length > 0) {
                groupMap.putAll(
                        new GroupDao(con)
                        .getGroups(grpSids)
                        .stream()
                        .collect(Collectors.toMap(
                                grp -> grp.getGrpSid(),
                                grp -> grp))
                        );
            }
            setPublicTargetGroupIdArray(
                    sipMdlList.stream()
                    .filter(sipMdl ->
                    (sipMdl.getSipType() == GSConstSchedule.USER_KBN_GROUP
                            && groupMap.containsKey(sipMdl.getSipPsid())
                            )
                    )
                    .map(sipMdl -> groupMap.get(sipMdl.getSipPsid()).getGrpId())
                    .toArray(String[]::new)
                    );
        }
        //公開区分(グループスケジュール)
        if (getPublicType() == null
                && getTargetType() == GSConstSchedule.USER_KBN_GROUP) {
            setPublicType(biz.getInitPubAuth(con, pconf));  // pconf.getSccIniPublic();
            if (getPublicType() != GSConstSchedule.DSP_PUBLIC) {
                setPublicType(GSConstSchedule.DSP_NOT_PUBLIC);
            }
        }

        SchPriPushModel defConf = getRemindDefConf(ctx);
        setRemindTimingType(
                Optional.ofNullable(getRemindTimingType())
                .filter((i) -> (SchEnumReminderTime.valueOf(i) != null))
                .orElse(defConf.getSccReminder())
                );

        setRemindGrpFlg(
                Optional.ofNullable(getRemindGrpFlg())
                .filter((i) -> (i == GSConstSchedule.REMINDER_USE_YES
                        || i == GSConstSchedule.REMINDER_USE_NO))
                .orElse(GSConstSchedule.REMINDER_USE_YES)
                );

        if (getUseTimeFlg() != GSConstSchedule.TIME_EXIST) {
            getStartDateTime().setHour(GSConstSchedule.DAY_START_HOUR);
            getStartDateTime().setMinute(GSConstSchedule.DAY_START_MINUTES);
            getEndDateTime().setHour(GSConstSchedule.DAY_END_HOUR);
            getEndDateTime().setMinute(GSConstSchedule.DAY_END_MINUTES);
        }

    }

    /**
     *
     * <br>[機  能] パラメータ 入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx
     * @throws SQLException
     */
    default void validate(RestApiContext ctx) throws SQLException {
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();
        int sessionUsrSid = ctx.getRequestUserSid();
        List<RestApiValidateException> valErr = new ArrayList<>();

        defaultInit(ctx);

        SchDao schDao = new SchDao(con);

        Map<String, CmnUserModel> containsUserMap = getUserMap(ctx);

        Map<String, CmnGroupmModel> containsGroupMap = getGroupMap(ctx);

        //登録対象者IDの存在チェック
        if (getTargetId() != null) {
            if (getTargetType() == GSConstSchedule.USER_KBN_USER) {
                if (!containsUserMap.containsKey(getTargetId())) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.user"))
                            .setParamName("targetId")
                            );
                //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
                //ユーザスケジュール登録権限チェック
                } else if (!schDao.canRegistUserSchedule(
                        containsUserMap.get(getTargetId()).getUsrSid(),
                        sessionUsrSid)) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.user"))
                            .setParamName("targetId")
                            );
                }

            } else {
                if (!containsGroupMap.containsKey(getTargetId())) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.group"))
                            .setParamName("targetId")
                            );

                } else if (!schDao.canRegistGroupSchedule(
                        containsGroupMap.get(
                                getTargetId()).getGrpSid(),
                        sessionUsrSid)) {
                    //グループスケジュール登録権限チェック
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.group"))
                            .setParamName("targetId")
                            );
                }

            }
        }
        //指定公開対象者IDの存在チェック
        if (getPublicTargetUserIdArray() != null) {
            for (int i = 0; i < getPublicTargetUserIdArray().length; i++) {
                if (!containsUserMap.containsKey(getPublicTargetUserIdArray()[i])) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.user"))
                            .setParamName(String.format("publicTargetUserIdArray[%d]", i))
                            );
                }
            }
        }
        if (getPublicTargetGroupIdArray() != null) {
            for (int i = 0; i < getPublicTargetGroupIdArray().length; i++) {
                if (!containsGroupMap.containsKey(getPublicTargetGroupIdArray()[i])) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.group"))
                            .setParamName(String.format("publicTargetGroupIdArray[%d]", i))
                            );
                }
            }
        }
        //同時登録対象者IDの存在チェック
        if (getSameScheduledUserIdArray() != null) {
            for (int i = 0; i < getSameScheduledUserIdArray().length; i++) {
                if (!containsUserMap.containsKey(getSameScheduledUserIdArray()[i])) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("cmn.user"))
                            .setParamName(String.format("sameScheduledUserIdArray[%d]", i))
                            );
                //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
                //ユーザスケジュール登録権限チェック
                } else if (!schDao.canRegistUserSchedule(
                        containsUserMap.get(getSameScheduledUserIdArray()[i]).getUsrSid(),
                        sessionUsrSid)) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.user"),
                            gsMsg.getMessage("cmn.entry")
                            )
                            .setParamName(String.format("sameScheduledUserIdArray[%d]", i))
                            );
                }

            }
        }

        //公開区分（グループスケジュール）
        if (getPublicType() != null && getTargetType() == GSConstSchedule.USER_KBN_GROUP
                && getPublicType() != GSConstSchedule.DSP_PUBLIC
                && getPublicType() != GSConstSchedule.DSP_NOT_PUBLIC) {
            throw new RestApiValidateException(
                    EnumError.PARAM_FORMAT,
                    "error.input.comp.text.either",
                    "publicType",
                    Arrays.stream(
                        new String[] {
                                "" + GSConstSchedule.DSP_PUBLIC,
                                "" + GSConstSchedule.DSP_NOT_PUBLIC,
                        }
                        )
                        .collect(Collectors.joining(","))
                    );

        }
        //公開対象チェック
        if (getPublicType() != null && getPublicType() == GSConstSchedule.DSP_USRGRP) {
            //未選択チェック
            if (getPublicTargetUserIdArray().length <= 0
                    && getPublicTargetGroupIdArray().length <= 0) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.select.required.text",
                        "publicTargetUserIdArray")
                        .setParamName(
                                "publicTargetUserIdArray",
                                "publicTargetGroupIdArray"
                                )
                        );
            }
        }
//
//
        CommonBiz cmnBiz = new CommonBiz();
        if (getFacilityIdArray() != null && getFacilityIdArray().length > 0) {

            RsvSisDataDao sisDataDao = new RsvSisDataDao(con);
            //施設予約の管理者
            boolean rsvAdmin = cmnBiz.isPluginAdmin(con,
                    reqMdl.getSmodel(),
                    GSConstSchedule.PLUGIN_ID_RESERVE);
            List<RsvSisDataModel> selectResList =
                    sisDataDao.select(
                            getFacilityIdArray());

            if (rsvAdmin) {
                //全施設
                selectResList =
                        sisDataDao.selectGrpSisetuList(
                                selectResList.stream()
                                .map(rsv -> rsv.getRsdSid())
                                .collect(Collectors.toList())
                                );
            } else {
                //閲覧権限のある施設
                selectResList =
                        sisDataDao.selectGrpSisetuCanReadList(
                                selectResList.stream()
                                .map(rsv -> rsv.getRsdSid())
                                .collect(Collectors.toList()),
                                        sessionUsrSid);
            }
            Map<String, RsvSisDataModel> facilityMap = getFacilityMap(ctx);
            for (int i = 0; i < getFacilityIdArray().length; i++) {
                if (!facilityMap.containsKey(getFacilityIdArray()[i])) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_OTHER_INVALID,
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.institution"),
                            gsMsg.getMessage("schedule.151"))
                            .setParamName(
                                    String.format(
                                            "facilityIdArray[%d]",
                                            i)
                                    )
                            );

                }
            }
        }

        //日付開始終了
        if (getStartDateTime() != null && getEndDateTime() != null) {
            UDate frdate = getStartDateTime();
            UDate todate = getEndDateTime();
            if (frdate.compareDateYMDHM(todate) != UDate.LARGE) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_COLLABORATION,
                        "error.input.comp.text",
                        "startDateTime, endDateTime",
                        "startDateTime < endDateTime")
                        .setParamName(
                                "startDateTime",
                                "endDateTime"
                                )
                        );

            }
        }
        //アドレスチェック
        if (Optional.ofNullable(getClientAddressSidArray())
                .map(arr -> (arr.length > 0))
                .orElse(false)) {
            Map<Integer, Sch040AddressModel> exAddress =
                    getAddresMap(ctx);
            for (int i = 0; i < getClientAddressSidArray().length; i++) {
                if (!exAddress.containsKey(getClientAddressSidArray()[i])) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("address.src.2"))
                            .setParamName(
                                    String.format(
                                            "clientAddressSidArray[%d]", i))
                            );

                }
            }

        }

        //拠点存在チェック
        if (Optional.ofNullable(getClientCompanyBaseIdArray())
                .map(arr -> (arr.length > 0))
                .orElse(false)) {
            Map<String, AdrCompanyModel> exCompany =
                    getClientCompanyMap(ctx);
            Map<Integer, AdrCompanyBaseModel> exBase = getClientBaseMap(ctx);
            for (int i = 0; i < getClientCompanyBaseIdArray().length; i++) {
                String companyId =
                        Optional.ofNullable(getClientCompanyBaseIdArray()[i])
                        .filter(code -> code.indexOf(":") >= 0)
                        .map(code -> code.substring(0, code.lastIndexOf(":")))
                        .orElse("");
                int abaSid = Optional.ofNullable(getClientCompanyBaseIdArray()[i])
                        .filter(code -> code.indexOf(":") >= 0)
                        .filter(code -> code.lastIndexOf(":") < code.length() - 1)
                        .map(code -> code.substring(code.lastIndexOf(":") + 1))
                        .map(sid -> Integer.valueOf(sid))
                        .orElse(-1);
                //拠点SIDフォーマットエラー
                if (companyId.length() == 0) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("address.118"))
                            .setParamName(
                                    String.format(
                                            "clientAddressSidArray[%d]", i))
                            );
                    continue;
                }
                if (!exCompany.containsKey(companyId)) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("address.118"))
                            .setParamName(
                                    String.format(
                                            "clientAddressSidArray[%d]", i))
                            );
                    continue;
                }
                if (abaSid == 0) {
                    continue;
                }
                if (!exBase.containsKey(abaSid)) {
                    valErr.add(new RestApiValidateException(
                            EnumError.PARAM_IMPERMISSIBLE,
                            "search.data.notfound",
                            gsMsg.getMessage("address.118"))
                            .setParamName(
                                    String.format(
                                            "clientAddressSidArray[%d]", i))
                            );
                    continue;

                }
            }
        }
        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
        //スケジュール重複登録チェック
        List<RestApiPermissionException> dupErr = new ArrayList<>();
        dupErr.addAll(SchEntitiesValidateUtil.validateSchRepeatCheck(
                this,
                ctx,
                GSConstSchedule.SCH_REPEAT_KBN_NG));
        //同時登録その他施設予約エラーチェック
        dupErr.addAll(SchEntitiesValidateUtil.validateReserve(this, ctx));


        if (dupErr.size() > 0) {
            throw new RestApiExceptionNest(403, dupErr
                    .stream()
                    .map(e -> (RestApiException) e)
                    .collect(Collectors.toList()));
        }

    }

    /**
     * <p>clientAddressSidArray を取得します。
     * @return clientAddressSidArray
     * @see SchEntitiesPostParamModel#clientAddressSidArray__
     */
    int[] getClientAddressSidArray();
    /**
     *
     * <br>[機  能] 登録対象SIDを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx
     * @return ScdUsrSid
     */
    default int getTargetSid(RestApiContext ctx) {
        if (getTargetType() == GSConstSchedule.USER_KBN_USER) {
            return getUserMap(ctx).get(getTargetId()).getUsrSid();
        } else {
            return getGroupMap(ctx).get(getTargetId()).getGrpSid();

        }
    }

    /**
     * <p>warnCommitFlg を取得します。
     * @return warnCommitFlg
     * @see warnCommitFlg__
     */
    int getWarnCommitFlg();

    /**
     * <p>wornCommitFlg をセットします。
     * @param warnCommitFlg warnCommitFlg
     * @see #warnCommitFlg__
     */
    void setWarnCommitFlg(int warnCommitFlg);


}
