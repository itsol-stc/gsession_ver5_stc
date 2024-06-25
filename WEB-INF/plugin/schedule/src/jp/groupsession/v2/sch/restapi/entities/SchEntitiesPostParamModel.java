package jp.groupsession.v2.sch.restapi.entities;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextArea;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;

/**
 *
 * <br>[機  能] スケジュール登録パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SchEntitiesPostParamModel implements IPostParamModel {

    /**スケジュール対象タイプ                                      */
    @NotBlank
    @Selectable({"0", "1"})
    private int targetType__;
    /**登録対象ID                                       */
    @NotBlank
    @MaxLength(256)
    private String targetId__;
    /**開始日時                                        */
    @NotBlank
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    private UDate startDateTime__;
    /**終了日時                                         */
    @NotBlank
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    private UDate endDateTime__;
    /**時間使用フラグ                                       */
    @Selectable({"0", "1"})
    private int useTimeFlg__ = 0;
    /**タイトル                                         */
    @NotBlank
    @MaxLength(GSConstSchedule.MAX_LENGTH_TITLE)
    @TextField
    private String titleText__;
    /**文字色                                       */
    @Selectable({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    private Integer colorType__ = 0;
    /**内容                                        */
    @MaxLength(GSConstSchedule.MAX_LENGTH_VALUE)
    @TextArea
    private String bodyText__;
    /**備考                                        */
    @MaxLength(GSConstSchedule.MAX_LENGTH_BIKO)
    @TextArea
    private String memoText__;
    /** 重複警告無視フラグ*/
    @Selectable({"0", "1"})
    private int warnCommitFlg__;


    /**編集区分                                          */
    @Selectable({
        "" + GSConstSchedule.EDIT_CONF_NONE,
        "" + GSConstSchedule.EDIT_CONF_OWN,
        "" + GSConstSchedule.EDIT_CONF_GROUP
    })
    private Integer editType__;
    /**公開区分                                          */
    @Selectable({
        "" + GSConstSchedule.DSP_PUBLIC,
        "" + GSConstSchedule.DSP_NOT_PUBLIC,
        "" + GSConstSchedule.DSP_YOTEIARI,
        "" + GSConstSchedule.DSP_BELONG_GROUP,
        "" + GSConstSchedule.DSP_USRGRP,
        "" + GSConstSchedule.DSP_TITLE
    })
    private Integer publicType__;
    /**個別公開対象ユーザ                                        []         */
    private String[] publicTargetUserIdArray__;
    /**個別公開対象グループ                                       []         */
    private String[] publicTargetGroupIdArray__;
    /**通知対象:グループ                                         */
    @Selectable({"0", "1"})
    private Integer remindGrpFlg__;
    /**リマインダー時間                                          */
    @Selectable({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"})
    private Integer remindTimingType__;
    /**出欠確認区分                                        */
    @Selectable({"0", "1"})
    private int useAttendFlg__;
    /**同時登録ユーザID配列                                      []         */
    private String[] sameScheduledUserIdArray__ = new String[] {};
    /**同時登録施設ID配列                                       []         */
    private String[] facilityIdArray__ = new String[] {};
    /**コンタクト履歴保管                                        0:使用しない 1:使用する */
    @Selectable({"0", "1"})
    private int useContactFlg__ = 0;

    /**会社拠点ID配列                                       []         */
    private String[] clientCompanyBaseIdArray__;
    /**顧客アドレスSID配列                                      []        */
    private int[] clientAddressSidArray__;


    /** ユーザ情報Map*/
    private Map<String, CmnUserModel> userMap__;
    /** グループ情報Map*/
    private Map<String, CmnGroupmModel> groupMap__;
    /** 施設情報Map*/
    private Map<String, RsvSisDataModel> facilityMap__;
    /** 施設管理者権限*/
    private Boolean rsvAdmin__;
    /** スケジュール個人設定*/
    private SchPriConfModel pconf__;
    /** スケジュール管理者設定*/
    SchAdmConfModel aconf__;

    /** リマインドデフォルト設定*/
    private SchPriPushModel remaindDefConf__;
    /**アドレス情報*/
    private Map<Integer, Sch040AddressModel> addressMap__;
    /**企業情報*/
    private Map<String, AdrCompanyModel> clientCompanyMap__;
    /**企業拠点情報*/
    private Map<Integer, AdrCompanyBaseModel> clientBaseMap__;


    /* (非 Javadoc)
     * @see IParamModel#getTargetType()
     */
    @Override
    public int getTargetType() {
        return targetType__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setTargetType(int)
     */
    @Override
    public void setTargetType(int targetType) {
        targetType__ = targetType;
    }
    /* (非 Javadoc)
     * @see IParamModel#getTargetId()
     */
    @Override
    public String getTargetId() {
        return targetId__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setTargetId(java.lang.String)
     */
    @Override
    public void setTargetId(String targetId) {
        targetId__ = targetId;
    }
    /* (非 Javadoc)
     * @see IParamModel#getStartDateTime()
     */
    @Override
    public UDate getStartDateTime() {
        return startDateTime__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setStartDateTime(jp.co.sjts.util.date.UDate)
     */
    @Override
    public void setStartDateTime(UDate startDateTime) {
        startDateTime__ = startDateTime;
    }
    /* (非 Javadoc)
     * @see IParamModel#getEndDateTime()
     */
    @Override
    public UDate getEndDateTime() {
        return endDateTime__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setEndDateTime(jp.co.sjts.util.date.UDate)
     */
    @Override
    public void setEndDateTime(UDate endDateTime) {
        endDateTime__ = endDateTime;
    }
    /* (非 Javadoc)
     * @see IParamModel#getUseTimeFlg()
     */
    @Override
    public int getUseTimeFlg() {
        return useTimeFlg__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setUseTimeFlg(int)
     */
    @Override
    public void setUseTimeFlg(int useTimeFlg) {
        useTimeFlg__ = useTimeFlg;
    }
    /* (非 Javadoc)
     * @see IParamModel#getTitleText()
     */
    @Override
    public String getTitleText() {
        return titleText__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setTitleText(java.lang.String)
     */
    @Override
    public void setTitleText(String titleText) {
        titleText__ = titleText;
    }
    /* (非 Javadoc)
     * @see IParamModel#getColorType()
     */
    @Override
    public Integer getColorType() {
        return colorType__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setColorType(int)
     */
    @Override
    public void setColorType(Integer colorType) {
        colorType__ = colorType;
    }
    /**
     * <p>bodyText を取得します。
     * @return bodyText
     * @see SchEntitiesPostParamModel#bodyText__
     */
    @Override
    public String getBodyText() {
        return bodyText__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setBodyText(java.lang.String)
     */
    @Override
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }
    /* (非 Javadoc)
     * @see IParamModel#getMemoText()
     */
    @Override
    public String getMemoText() {
        return memoText__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setMemoText(java.lang.String)
     */
    @Override
    public void setMemoText(String memoText) {
        memoText__ = memoText;
    }
    /**
     * <p>editType を取得します。
     * @return editType
     * @see SchEntitiesPostParamModel#editType__
     */
    @Override
    public Integer getEditType() {
        return editType__;
    }
    /**
     * <p>editType をセットします。
     * @param editType editType
     * @see SchEntitiesPostParamModel#editType__
     */
    @Override
    public void setEditType(Integer editType) {
        editType__ = editType;
    }
    /* (非 Javadoc)
     * @see IParamModel#getPublicType()
     */
    @Override
    public Integer getPublicType() {
        return publicType__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setPublicType(java.lang.Integer)
     */
    @Override
    public void setPublicType(Integer publicType) {
        publicType__ = publicType;
    }
    /* (非 Javadoc)
     * @see IParamModel#getPublicTargetUserIdArray()
     */
    @Override
    public String[] getPublicTargetUserIdArray() {
        return publicTargetUserIdArray__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setPublicTargetUserIdArray(java.lang.String[])
     */
    @Override
    public void setPublicTargetUserIdArray(String[] publicTargetUserIdArray) {
        publicTargetUserIdArray__ = publicTargetUserIdArray;
    }
    /* (非 Javadoc)
     * @see IParamModel#getPublicTargetGroupIdArray()
     */
    @Override
    public String[] getPublicTargetGroupIdArray() {
        return publicTargetGroupIdArray__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setPublicTargetGroupIdArray(java.lang.String[])
     */
    @Override
    public void setPublicTargetGroupIdArray(String[] publicTargetGroupIdArray) {
        publicTargetGroupIdArray__ = publicTargetGroupIdArray;
    }
    /* (非 Javadoc)
     * @see IParamModel#getRemindGrpFlg()
     */
    @Override
    public Integer getRemindGrpFlg() {
        return remindGrpFlg__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setRemindGrpFlg(int)
     */
    @Override
    public void setRemindGrpFlg(Integer remindGrpFlg) {
        remindGrpFlg__ = remindGrpFlg;
    }
    /* (非 Javadoc)
     * @see IParamModel#getRemindTimingType()
     */
    @Override
    public Integer getRemindTimingType() {
        return remindTimingType__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setRemindTimingType(int)
     */
    @Override
    public void setRemindTimingType(Integer remindTimingType) {
        remindTimingType__ = remindTimingType;
    }
    /* (非 Javadoc)
     * @see IParamModel#getUseAttendFlg()
     */
    @Override
    public int getUseAttendFlg() {
        return useAttendFlg__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setUseAttendFlg(int)
     */
    @Override
    public void setUseAttendFlg(int useAttendFlg) {
        useAttendFlg__ = useAttendFlg;
    }
    /* (非 Javadoc)
     * @see IParamModel#getSameScheduledUserIdArray()
     */
    @Override
    public String[] getSameScheduledUserIdArray() {
        return sameScheduledUserIdArray__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setSameScheduledUserIdArray(java.lang.String[])
     */
    @Override
    public void setSameScheduledUserIdArray(String[] sameScheduledUserIdArray) {
        sameScheduledUserIdArray__ = sameScheduledUserIdArray;
    }
    /* (非 Javadoc)
     * @see IParamModel#getFacilityIdArray()
     */
    @Override
    public String[] getFacilityIdArray() {
        return facilityIdArray__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setFacilityIdArray(java.lang.String[])
     */
    @Override
    public void setFacilityIdArray(String[] facilityIdArray) {
        facilityIdArray__ = facilityIdArray;
    }
    /* (非 Javadoc)
     * @see IParamModel#getUseContactFlg()
     */
    @Override
    public int getUseContactFlg() {
        return useContactFlg__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setUseContactFlg(int)
     */
    @Override
    public void setUseContactFlg(int useContactFlg) {
        useContactFlg__ = useContactFlg;
    }
    /* (非 Javadoc)
     * @see IParamModel#getClientCompanyBaseCodeArray()
     */
    @Override
    public String[] getClientCompanyBaseIdArray() {
        return clientCompanyBaseIdArray__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setClientCompanyBaseCodeArray(java.lang.String[])
     */
    @Override
    public void setClientCompanyBaseIdArray(String[] clientCompanyBaseIdArray) {
        clientCompanyBaseIdArray__ = clientCompanyBaseIdArray;
    }
    /**
     * <p>clientAddressSidArray を取得します。
     * @return clientAddressSidArray
     * @see SchEntitiesPostParamModel#clientAddressSidArray__
     */
    @Override
    public int[] getClientAddressSidArray() {
        return clientAddressSidArray__;
    }
    /* (非 Javadoc)
     * @see IParamModel#setClientAddressSidArray(int[])
     */
    @Override
    public void setClientAddressSidArray(int[] clientAddressSidArray) {
        clientAddressSidArray__ = clientAddressSidArray;
    }



    /**
     * <p>wornCommitFlg を取得します。
     * @return wornCommitFlg
     * @see #warnCommitFlg__
     */
    @Override
    public int getWarnCommitFlg() {
        return warnCommitFlg__;
    }
    /**
     * <p>wornCommitFlg をセットします。
     * @param warnCommitFlg warnCommitFlg
     * @see #warnCommitFlg__
     */
    @Override
    public void setWarnCommitFlg(int warnCommitFlg) {
        warnCommitFlg__ = warnCommitFlg;
    }
    @Validator
    @Override
    public void validate(RestApiContext ctx) throws SQLException {
        IPostParamModel.super.validate(ctx);
    }
    /* (非 Javadoc)
     * @see IParamModel#getClientCompanyMap(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public Map<String, AdrCompanyModel> getClientCompanyMap(
            RestApiContext ctx) throws SQLException {
        if (clientCompanyMap__ != null) {
            return clientCompanyMap__;
        }
        AdrCompanyDao     acoDao = new AdrCompanyDao(ctx.getCon());
        clientCompanyMap__ = acoDao.select(
                Arrays.stream(getClientCompanyBaseIdArray())
                    .filter(code -> code.indexOf(":") >= 0)
                    .map(code -> code.substring(0, code.lastIndexOf(":")))
                    .toArray(String[]::new))
                .stream()
                .collect(Collectors.toMap(
                            aco -> aco.getAcoCode(),
                            aco -> aco
                        ));

        return clientCompanyMap__;
    }

    /* (非 Javadoc)
     * @see IParamModel#getClientBaseMap(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public Map<Integer, AdrCompanyBaseModel> getClientBaseMap(
            RestApiContext ctx) throws SQLException {
        if (clientBaseMap__ != null) {
            return clientBaseMap__;
        }
        AdrCompanyBaseModel nothing =
                new AdrCompanyBaseModel();

        AdrCompanyBaseDao abaDao = new AdrCompanyBaseDao(ctx.getCon());
        clientBaseMap__ =
                Stream.concat(
                    abaDao.select(
                            getClientCompanyMap(ctx).values().stream()
                            .map(aco -> aco.getAcoSid())
                            .collect(Collectors.toSet()))
                        .stream(),
                        Stream.of(nothing)
                    )
                .collect(Collectors.toMap(aba -> aba.getAbaSid(), aba -> aba));


        return clientBaseMap__;
    }
    /* (非 Javadoc)
     * @see IParamModel#getAddresMap(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public Map<Integer, Sch040AddressModel> getAddresMap(
            RestApiContext ctx) throws SQLException {

        if (addressMap__ != null) {
            return addressMap__;
        }

        Sch040Dao sch040dao = new Sch040Dao(ctx.getCon());
        addressMap__ = sch040dao.getAddressList(ctx.getCon(),
                Arrays.stream(getClientAddressSidArray())
                .mapToObj(sid -> String.valueOf(sid))
                .toArray(String[]::new),
                ctx.getRequestUserSid())
        .stream()
        .collect(Collectors.toMap(
                adr -> adr.getAdrSid(),
                adr -> adr));
        return addressMap__;
    }



    /* (非 Javadoc)
     * @see IParamModel#getGroupMap(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public Map<String, CmnGroupmModel> getGroupMap(RestApiContext ctx) {
        if (groupMap__ != null) {
            return groupMap__;
        }
        try {
            CmnGroupmDao grpDao = new CmnGroupmDao(ctx.getCon());
            groupMap__ = new HashMap<String, CmnGroupmModel>();


            String[] grpIds = Stream.of(
                    Stream.of(getTargetId())
                    .filter(id ->
                        getTargetType() == GSConstSchedule.USER_KBN_GROUP),
                    Optional.ofNullable(getPublicTargetGroupIdArray())
                        .map(arr -> Stream.of(arr))
                        .orElse(Stream.of())
                    )
                    .flatMap(src -> src)
                    .toArray(String[]::new);
            if (grpIds.length > 0) {
                groupMap__.putAll(
                        grpDao.selectGrpData(grpIds, GSConst.JTKBN_TOROKU)
                        .stream()
                        .collect(Collectors.toMap(
                                grp -> grp.getGrpId(),
                                grp -> grp))

                );
            }
            return groupMap__;
            } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }

    }
    /* (非 Javadoc)
     * @see IParamModel#getUserMap(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public Map<String, CmnUserModel> getUserMap(RestApiContext ctx) {
        if (userMap__ != null) {
            return userMap__;
        }
        UserSearchDao usDao = new UserSearchDao(ctx.getCon());

        try {
            userMap__ = usDao.getUsersDataList(
                    Stream.of(
                            Stream.of(getTargetId())
                            .filter(id ->
                                getTargetType() == GSConstSchedule.USER_KBN_USER),
                            Optional.ofNullable(getPublicTargetUserIdArray())
                                .map(arr -> Stream.of(arr))
                                .orElse(Stream.of()),
                            Optional.ofNullable(getSameScheduledUserIdArray())
                                .map(arr -> Stream.of(arr))
                                .orElse(Stream.of())
                            )
                            .flatMap(src -> src)
                            .toArray(String[]::new)

                    ).stream()
                    .collect(Collectors.toMap(
                            usr -> usr.getUsrLgid(),
                            usr -> usr));
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }
        return userMap__;
    }
    /* (非 Javadoc)
     * @see IParamModel#getFacilityMap(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public Map<String, RsvSisDataModel> getFacilityMap(
            RestApiContext ctx)  {
        if (facilityMap__ != null) {
            return facilityMap__;
        }
        RsvSisDataDao sisDataDao = new RsvSisDataDao(ctx.getCon());

        try {
            List<RsvSisDataModel> selectResList =
                    sisDataDao.select(
                            getFacilityIdArray());
            Set<Integer> accessableSids = new HashSet<>();
            if (isRsvAdmin(ctx)) {
                //全施設
                accessableSids.addAll(
                        sisDataDao.selectGrpSisetuList(
                                selectResList.stream()
                                .map(rsv -> rsv.getRsdSid())
                                .collect(Collectors.toList())
                                ).stream()
                                .map(sis -> sis.getRsdSid())
                                .collect(Collectors.toSet())
                        );
            } else {
                //閲覧権限のある施設
                accessableSids.addAll(
                        sisDataDao.selectGrpSisetuCanReadList(
                                selectResList.stream()
                                .map(rsv -> rsv.getRsdSid())
                                .collect(Collectors.toList()),
                                ctx.getRequestUserSid())
                                .stream()
                                .map(sis -> sis.getRsdSid())
                                .collect(Collectors.toSet())
                        );
            }
            facilityMap__ = selectResList.stream()
                    .filter(sis -> accessableSids.contains(sis.getRsdSid()))
                    .collect(Collectors.toMap(
                            rsv -> rsv.getRsdId(),
                            rsv -> rsv));
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }
        return facilityMap__;

    }

    /* (非 Javadoc)
     * @see IParamModel#isRsvAdmin(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public boolean isRsvAdmin(RestApiContext ctx) {
        if (rsvAdmin__ != null) {
            return rsvAdmin__;
        }
        try {
            CommonBiz cmnBiz = new CommonBiz();

            rsvAdmin__ = cmnBiz.isPluginAdmin(ctx.getCon(),
                    ctx.getRequestUserSid(),
                    GSConstSchedule.PLUGIN_ID_RESERVE);
            return rsvAdmin__;
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }

    }
    /* (非 Javadoc)
     * @see IParamModel#getRemindDefConf(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public SchPriPushModel getRemindDefConf(RestApiContext ctx) throws SQLException {
        if (remaindDefConf__ != null) {
            return remaindDefConf__;
        }
        SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());

        if (getTargetType() == GSConstSchedule.USER_KBN_USER) {
            remaindDefConf__ =  SchPriPushModel.getInstance(
                    schBiz.getSchPriConfModel(ctx.getCon(), ctx.getRequestUserSid())
                    );
        } else {
            remaindDefConf__ =  SchPriPushModel.getInstance(
                        new SchDataModel()
                    );
        }
        return remaindDefConf__;
    }
    /* (非 Javadoc)
     * @see IParamModel#getPconf(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
   @Override
public SchPriConfModel getPconf(RestApiContext ctx) throws SQLException {
       if (pconf__ != null) {
           return pconf__;
       }
       SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
       pconf__ = schBiz.getSchPriConfModel(ctx.getCon(), ctx.getRequestUserSid());
       return pconf__;
   }
   /* (非 Javadoc)
 * @see IParamModel#getAconf(jp.groupsession.v2.restapi.controller.RestApiContext)
 */
   @Override
public SchAdmConfModel getAconf(RestApiContext ctx) throws SQLException {
       if (aconf__ != null) {
           return aconf__;
       }
       SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
       aconf__ = schBiz.getAdmConfModel(ctx.getCon());
       return aconf__;
   }



}
