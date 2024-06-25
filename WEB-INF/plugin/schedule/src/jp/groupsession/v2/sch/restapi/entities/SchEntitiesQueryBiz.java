package jp.groupsession.v2.sch.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchCompanyModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesResultModel.AddressInfo;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesResultModel.ClientInfo;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesResultModel.TmpFileInfo;
import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch100.ScheduleListSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール検索ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchEntitiesQueryBiz {
    /** パラメータ*/
    private final SchEntitiesQueryParamModel paramQUERY__;

    /** パラメータ*/
    private final SchEntitiesGetParamModel paramGET__;

    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 取得結果*/
    private List<SchEntitiesResultModel> result__ = new ArrayList<>();
    /** 結果 最大件数*/
    private int resultMaxCount__ = 0;
    /***/
    GsMessage gsMsg__;
    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.sch.restapi.entities.SchEntitiesQueryBiz#result__
     */
    public List<SchEntitiesResultModel> getResult() {
        return result__;
    }

    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     */
    public SchEntitiesQueryBiz(SchEntitiesQueryParamModel param, RestApiContext ctx) {
        paramQUERY__ = param;
        paramGET__ = null;
        ctx__ = ctx;
        gsMsg__ = new GsMessage(ctx.getRequestModel());
    }
    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     */
    public SchEntitiesQueryBiz(SchEntitiesGetParamModel param, RestApiContext ctx) {
        paramQUERY__ = null;
        paramGET__ = param;
        ctx__ = ctx;
        gsMsg__ = new GsMessage(ctx.getRequestModel());
    }
    /**
     *
     * <br>[機  能] 検索の実行
     * <br>[解  説]
     * <br>[備  考]
     */
    public void execute() {
        ScheduleSearchDao searchDao = new ScheduleSearchDao(ctx__.getCon());
        if (paramQUERY__ != null) {
            try {
                ScheduleListSearchModel smdl = __createSearchModel();

                SqlBuffer sql = searchDao.makeCountSelectSql(
                        smdl, ctx__.getRequestUserSid(), false, true);
                resultMaxCount__ = searchDao.getScheduleCount(sql);
                //オフセット値が件数より大きい場合にエラーになるため
                if (resultMaxCount__ < smdl.getSchOffset() - 1) {
                    smdl.setSchOffset(resultMaxCount__ + 1);
                }
                SqlBuffer sqlSchedule =
                        searchDao.makeCountSelectSql(smdl, ctx__.getRequestUserSid(), true, false);

                ArrayList<SchDataModel> schDataList =
                        searchDao.getScheduleList(sqlSchedule, false, true);

                result__ = __createResultList(schDataList);
            } catch (SQLException e) {
                throw new RuntimeException("SQL実行に失敗", e);
            }
        } else {
            //単体指定
            try {
                SchDataModel schData = searchDao.getScheduleData(
                        paramGET__.getScheduleSid(),
                        0, ctx__.getRequestUserSid());
                if (schData == null) {
                    resultMaxCount__ = 0;
                    result__ = List.of();
                    return;
                }
                resultMaxCount__ = 1;
                result__ = __createResultList(List.of(schData));

            } catch (SQLException e) {
                throw new RuntimeException("SQL実行に失敗", e);
            }
        }

    }



    private List<SchEntitiesResultModel> __createResultList(
            List<SchDataModel> schDataList) throws SQLException {
        ScheduleSearchDao searchDao = new ScheduleSearchDao(ctx__.getCon());
        List<SchEntitiesResultModel> ret = new ArrayList<>();

        //セッション情報を取得

        CommonBiz cmnBiz = new CommonBiz();
        //スケジュールの管理者
        boolean isSchAdmin = cmnBiz.isPluginAdmin(
                ctx__.getCon(),
                ctx__.getRequestUserSid(),
                GSConstSchedule.PLUGIN_ID_SCHEDULE);

        //施設予約の管理者
        boolean isRsvAdmin = cmnBiz.isPluginAdmin(
                ctx__.getCon(),
                ctx__.getRequestUserSid(),
                GSConstSchedule.PLUGIN_ID_RESERVE);

        SchCommonBiz schCmnBiz = new SchCommonBiz(ctx__.getCon(), ctx__.getRequestModel());




        // --------------------------------------------------------
        //   データ一括取得
        // --------------------------------------------------------

        //公開フラグ取得
        List<Integer> checkScdSidList =
                schDataList.stream()
                .map(s -> s.getScdSid())
                .collect(Collectors.toList());
        SchDataPubDao schPubDao = new SchDataPubDao(ctx__.getCon());
        Map<Integer, List<SchDataPubModel>> schPubMap  =
                schPubDao.select(checkScdSidList);
        List<Integer> pubScdSidList
        = schPubDao.getUserPubScdSidList(ctx__.getRequestUserSid(), checkScdSidList);

        //所属グループ一覧
        List<Integer> belongGrpSid = new CmnBelongmDao(ctx__.getCon())
                .selectUserBelongGroupSid(ctx__.getRequestUserSid());

        //所属グループが同じユーザSid一覧
        List<Integer> belongUsrSid = new GroupDao(ctx__.getCon())
                .getSameGroupUser(ctx__.getRequestUserSid(),
                        schDataList.stream()
                        .filter(sch -> sch.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER)
                        .map(sch -> sch.getScdUsrSid())
                        .collect(Collectors.toList()));

        SchDao schDao = new SchDao(ctx__.getCon());
        Set<Integer> notAccessUserList
        = new HashSet<>(
                schDao.getNotAccessUserList(ctx__.getRequestUserSid()));
        Set<Integer> notRegistUserSet
        = new HashSet<>(
                schDao.getNotRegistUserList(ctx__.getRequestUserSid()));
        Set<Integer> notRegistGroupSet
        = new HashSet<>(
                schDao.getNotRegistGrpList(ctx__.getRequestUserSid()));

        Set<Integer> userSidSet =
                schDataList.stream()
                .flatMap(scData -> {
                    Stream.Builder<Integer> builder = Stream.builder();
                    // 登録ユーザ
                    builder.accept(scData.getScdAuid());
                    // 変更ユーザ
                    builder.accept(scData.getScdEuid());
                    if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                        // 対象ユーザ
                        builder.accept(scData.getScdUsrSid());
                    }
                    // 公開指定
                    Optional.ofNullable(schPubMap.get(scData.getScdSid()))
                        .ifPresent(list ->
                                list.stream()
                                .filter(pub -> pub.getSdpType() == GSConstSchedule.USER_KBN_USER)
                                .forEach(pub -> {
                                    builder.accept(pub.getSdpPsid());
                                })
                            );
                    return builder.build();
                })
                .collect(Collectors.toSet());

        Set<Integer> groupSidSet =
                schDataList.stream()
                .flatMap(scData -> {
                    Stream.Builder<Integer> builder = Stream.builder();
                    // 対象グループ
                    builder.accept(scData.getScdUsrSid());
                    // 公開指定
                    Optional.ofNullable(schPubMap.get(scData.getScdSid()))
                        .stream()
                        .flatMap(list -> list.stream())
                        .filter(pub -> pub.getSdpType() == GSConstSchedule.USER_KBN_GROUP)
                        .forEach(pub -> {
                            builder.accept(pub.getSdpPsid());
                        });
                    return builder.build();
                })
                .collect(Collectors.toSet());

        List<Integer> schduleSidList    = new ArrayList<Integer>();
        Set<Integer> sameScdGrpSidSet = new HashSet<Integer>();
        Map<Integer, EnumDispMode> dispModeMap = new HashMap<>();

        //スケジュール表示設定
        schDataList.stream()
            .forEach(scData -> {
                dispModeMap.put(scData.getScdSid(), EnumDispMode.SECRET);

                if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
                        && scData.getScdUsrSid() == ctx__.getRequestUserSid()) {
                    dispModeMap.put(scData.getScdSid(), EnumDispMode.PUBLIC);
                    //本人
                }
                if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
                        && scData.getScdUsrSid() != ctx__.getRequestUserSid()) {
                    if (scData.getScdPublic() == GSConstSchedule.DSP_TITLE) {
                        dispModeMap.put(scData.getScdSid(), EnumDispMode.TITLE_ONLY);
                    }
                    if (scData.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
                        dispModeMap.put(scData.getScdSid(), EnumDispMode.YOTEIARI);
                    }
                    if (scData.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                        || scData.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                        dispModeMap.put(scData.getScdSid(), EnumDispMode.YOTEIARI);
                        boolean isSameGroup = false;
                        if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                            isSameGroup = belongUsrSid.contains(scData.getScdUsrSid());
                        }
                        if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                            isSameGroup = belongGrpSid.contains(scData.getScdUsrSid());
                        }

                        if (schCmnBiz.checkViewOk(scData, ctx__.getRequestUserSid(),
                                isSameGroup,
                                pubScdSidList.contains(scData.getScdSid())
                                )) {
                            dispModeMap.put(scData.getScdSid(), EnumDispMode.PUBLIC);
                        }
                    }
                }
                if (scData.getScdAuid() == ctx__.getRequestUserSid()
                        || scData.getScdEuid() == ctx__.getRequestUserSid()) {
                    //登録者の場合は表示する
                    dispModeMap.put(scData.getScdSid(), EnumDispMode.PUBLIC);
                }
                if (scData.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
                    dispModeMap.put(scData.getScdSid(), EnumDispMode.PUBLIC);
                }
            });

        schDataList.stream()
            .filter(scData -> {
                if (dispModeMap.get(scData.getScdSid()) == EnumDispMode.SECRET) {
                    return false;
                }
                return true;
            })
            .collect(Collectors.toList());


        for (SchDataModel scData : schDataList) {
            Integer scdSid = Integer.valueOf(scData.getScdSid());
            schduleSidList.add(scdSid);
            if (__getSameInputFlg() == 1
                    && scData.getScdGrpSid() >= 0
                    && dispModeMap.get(scdSid) == EnumDispMode.PUBLIC) {
                // 同時登録
                Integer scdGrpSid = Integer.valueOf(scData.getScdGrpSid());
                sameScdGrpSidSet.add(scdGrpSid);
            }
        }

        //添付ファイル情報
        SchBinDao schBinDao = new SchBinDao(ctx__.getCon());
        Map<Integer, List<CmnBinfModel>> binMap = schBinDao.getBinInfoMap(schduleSidList);

        // 会社・担当者(アドレス)
        Map<Integer, Map<String, ClientInfo>> scdCompanysMap = new HashMap<>();
        if (ctx__.getPluginConfig().getPlugin("address") != null) {
            scdCompanysMap.putAll(__getCompanyMapList(schduleSidList, ctx__.getRequestUserSid()));
        }


        // 施設予約
        Map<Integer, List<RsvSisDataModel>> reserveListMap = new HashMap<>();
        if (ctx__.getPluginConfig().getPlugin("reserve") != null
                && __getSameInputFlg() == GSConstSchedule.SELECT_ON) {
            reserveListMap.putAll(
                __getSelectResList(ctx__.getCon(),
                    schduleSidList,
                    ctx__.getRequestUserSid(),
                    isRsvAdmin));
        }
        // 同時登録スケジュール
        Map<Integer, ArrayList<SchDataModel>> sameInputScheduleMap =
                searchDao.getSameScheduleMap(
                        sameScdGrpSidSet.stream().collect(Collectors.toList()),
                        sameScdGrpSidSet.stream()
                            .collect(Collectors.toMap(
                                    sid -> sid,
                                    sid -> new ArrayList<>(notAccessUserList))));
        userSidSet.addAll(
            sameInputScheduleMap.values().stream()
                .flatMap(list -> list.stream())
                .map(scData -> scData.getScdUsrSid())
                .collect(Collectors.toSet()));


        //ユーザ情報Map
        Map<Integer, CmnUserModel> userMap = new HashMap<Integer, CmnUserModel>();
        if (userSidSet.size() > 0) {
            List<CmnUserModel> uMdlList =
                    new UserSearchDao(ctx__.getCon())
                    .getUsersDataList(userSidSet);
            if (uMdlList != null) {
                for (CmnUserModel uMdl : uMdlList) {
                    userMap.put(uMdl.getUsrSid(), uMdl);
                }
            }
        }
        //グループ名情報Map
        Map<Integer, CmnGroupmModel> groupMap = new HashMap<Integer, CmnGroupmModel>();
        if (groupSidSet.size() > 0) {
            int[] groupIds =
                    groupSidSet.stream()
                    .mapToInt(sid -> sid)
                    .toArray();

            GroupDao gDao = new GroupDao(ctx__.getCon());
            List<CmnGroupmModel> gMdlList = gDao.getGroups(groupIds);
            if (gMdlList != null) {
                for (CmnGroupmModel gMdl : gMdlList) {
                    groupMap.put(gMdl.getGrpSid(), gMdl);
                }
            }
        }

        Function<SchDataModel, SchEntitiesResultModel> converter = (scData) -> {
            SchEntitiesResultModel mdl = new SchEntitiesResultModel();
            mdl.setSchDate(scData);

            //登録ユーザ 対象ユーザ(ユーザ)
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                mdl.setTargetUser(userMap.get(scData.getScdUsrSid()));
            }
            mdl.setAddUser(userMap.get(scData.getScdAuid()));

            //対象グループ
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                mdl.setTargetGroup(groupMap.get(scData.getScdUsrSid()));
            }
            //編集権限
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                if (notRegistUserSet.contains(scData.getScdUsrSid())) {
                    mdl.setAbleEditFlg(0);
                }
            }
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                if (notRegistGroupSet.contains(scData.getScdUsrSid())) {
                    mdl.setAbleEditFlg(0);
                }
            }
            //出席確認の回答スケジュールは編集不可
            if (scData.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES
                    && scData.getScdAttendAuKbn() == GSConstSchedule.ATTEND_REGIST_USER_NO) {
                mdl.setAbleEditFlg(0);

            }

            boolean isSameGroup = false;
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                isSameGroup = belongUsrSid.contains(scData.getScdUsrSid());
            }
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                isSameGroup = belongGrpSid.contains(scData.getScdUsrSid());
            }

            if (!schCmnBiz.isEditOk(scData, ctx__.getRequestUserSid(),
                    isSameGroup,
                    pubScdSidList.contains(scData.getScdSid()),
                    isSchAdmin,
                    false)) {
                mdl.setAbleEditFlg(0);
            }
            //指定公開設定
            mdl.setPublicTargetGroupArray(
                    Optional.ofNullable(schPubMap.get(scData.getScdSid()))
                    .map(list ->
                        list.stream()
                            .filter(pub ->
                                pub.getSdpType() == GSConstSchedule.USER_KBN_GROUP)
                            .map(pub ->
                                new PublicTargetGroupInf(
                                        groupMap.get(pub.getSdpPsid()))
                                )
                            .collect(Collectors.toList())
                        )
                    .orElse(null)
                    );
            mdl.setPublicTargetUserArray(
                    Optional.ofNullable(schPubMap.get(scData.getScdSid()))
                    .map(list ->
                            list.stream()
                            .filter(pub ->
                                pub.getSdpType() == GSConstSchedule.USER_KBN_USER)
                            .map(pub ->
                                new PublicTargetUserInf(
                                        userMap.get(pub.getSdpPsid()))
                                    )
                            .collect(Collectors.toList())
                        )
                    .orElse(null)
                );

            //公開モード
            mdl.setDispMode(dispModeMap.get(scData.getScdSid()));

            //添付ファイル情報
            mdl.setTmpFileArray(
                    Optional.ofNullable(binMap.get(scData.getScdSid()))
                    .map(list ->
                            list.stream()
                            .map(bin -> new TmpFileInfo(bin))
                            .collect(Collectors.toList())
                            )
                    .orElse(null)
                    );

            // 会社・担当者(アドレス)
            mdl.setCompanyMap(
                    scdCompanysMap.get(scData.getScdSid()));

            // 施設予約
            mdl.setReserveList(reserveListMap.get(scData.getScdSid()));

            return mdl;
        };

        String yoteiAri = new GsMessage(ctx__.getRequestModel()).getMessage("schedule.src.9");

        for (SchDataModel scData : schDataList) {
            SchEntitiesResultModel mdl = converter.apply(scData);
            ret.add(mdl);
            mdl.setYoteiariText(yoteiAri);

            // 同時登録スケジュール
            mdl.setSameScheduledArray(
                    Optional.ofNullable(sameInputScheduleMap.get(scData.getScdGrpSid()))
                        .map(list ->
                                list.stream()
                                .filter(sameSch -> sameSch.getScdSid() != scData.getScdSid())
                                .map(sameSch -> {
                                    SchEntitiesResultModel result = converter.apply(sameSch);
                                    result.setDispMode(EnumDispMode.PUBLIC);
                                    return result;
                                })
                                .collect(Collectors.toList())
                            )
                        .orElse(List.of())
                    );

        }

        return ret;



    }
    /** @return 同時登録取得フラグ */
    private int __getSameInputFlg() {
        if (paramQUERY__ != null) {
            return paramQUERY__.getSameInputFlg();
        }
        return 1;
    }

    /**
     *
     * <br>[機  能] 検索条件モデルを作成
     * <br>[解  説]
     * <br>[備  考]
     * @return 検索条件モデル
     * @throws SQLException
     */
    private ScheduleListSearchModel __createSearchModel() throws SQLException {

        ScheduleListSearchModel ret = new ScheduleListSearchModel();

        //セレクトタイプによる検索対象設定
        ret.setSchSltUserBelongGrpFlg(false);
        ret.setSchSltUserSid(-1);

        switch (Optional.ofNullable(paramQUERY__.getSelectType())
                .orElse(EnumScheduleSelectType.USER)) {
                case USER_WITH_BELONGGROUP:
                    ret.setSchSltUserBelongGrpFlg(true);
                case USER:
                    ret.setSchUsrKbn(GSConstSchedule.USER_KBN_USER);
                    AuthDao adao = new AuthDao(ctx__.getCon());
                    BaseUserModel smodel = null;
                    try {
                        smodel = adao.selectLoginNoPwd(paramQUERY__.getUserId(), null);
                    } catch (SQLException e) {
                        throw new RuntimeException("SQL実行エラー", e);
                    }
                    if (smodel == null) {
                        throw new RestApiValidateException(
                                EnumError.PARAM_IMPERMISSIBLE,
                                "search.data.notfound",
                                new GsMessage(ctx__.getRequestModel())
                                .getMessage("cmn.user"));
                    }
                    ret.setSchSltUserSid(smodel.getUsrsid());
                    break;
                case GROUP_WITH_MENBER:
                case GROUP:
                    CmnGroupmModel grp = new CmnGroupmDao(ctx__.getCon())
                    .getGroupInf(paramQUERY__.getGroupId());
                    if (grp == null) {
                        throw new RestApiValidateException(
                                EnumError.PARAM_IMPERMISSIBLE,
                                "search.data.notfound",
                                new GsMessage(ctx__.getRequestModel())
                                .getMessage("cmn.group"));
                    }
                    if (paramQUERY__.getSelectType() == EnumScheduleSelectType.GROUP_WITH_MENBER) {
                        ret.setSchSltUserSid(-1);
                        ret.setSchSltGroupSid(String.valueOf(grp.getGrpSid()));
                        ret.setSchUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                    } else {
                        ret.setSchSltUserSid(grp.getGrpSid());
                        ret.setSchUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                    }
                    break;
                case MYGROUPMEMBER:
                    ret.setSchSltUserSid(-1);
                    ret.setSchSltGroupSid(
                            String.format("%s%d",
                                    GSConstSchedule.MY_GROUP_STRING,
                                    paramQUERY__.getMyGroupSid()));
                    ret.setMyGrpFlg(true);
                    break;
                case SCHEDULELIST:

                    ret.setSchSltUserSid(-1);
                    ret.setSchSltGroupSid(
                            String.format("%s%d",
                                    GSConstSchedule.DSP_LIST_STRING,
                                    paramQUERY__.getListSid()));
                    ret.setDspListFlg(true);
                    break;
                default:
        }
        //      開始日FROM
        ret.setSchStartDateFr(paramQUERY__.getStartFromDate());
        //      開始日TO
        ret.setSchStartDateTo(paramQUERY__.getStartToDate());
        //      終了日FROM
        ret.setSchEndDateFr(paramQUERY__.getEndFromDate());
        //      終了日TO
        ret.setSchEndDateTo(paramQUERY__.getEndToDate());
        //      文字色

        ret.setBgcolor(IntStream.of(paramQUERY__.getColorTypeArray())
                .mapToObj(c -> String.valueOf(c))
                .toArray(String[]::new)
                );
        ret.setSchPublic(-1);
        //      キーワード
        //タイトル・内容についての検索条件がある場合は公開スケジュールを対象に検索
        if (paramQUERY__.getKeywordText() != null) {
            ret.setSchKeyValue(Stream.of(paramQUERY__.getKeywordText().trim().split("\\s"))
                    .collect(Collectors.toList()));
            ret.setSchPublic(GSConstSchedule.DSP_PUBLIC);
        }

        //      キーワード検索対象
        switch (Optional.ofNullable(paramQUERY__.getKeywordTargetType())
                .orElse(EnumKeywordTarget.TITLE_WITH_VALUE)) {
                case TITLE_ONLY:
                    ret.setTargetTitle(true);
                    break;
                case VALUE_ONLY:
                    ret.setTargetValue(true);
                    break;
                case TITLE_WITH_VALUE:
                default:
                    ret.setTargetTitle(true);
                    ret.setTargetValue(true);
        }
        //
        //      キーワード接続方法
        ret.setKeyWordkbn(paramQUERY__.getKeywordJunctionFlg());

        //      取得件数
        ret.setSchLimit(paramQUERY__.getLimit());
        //      取得開始位置
        ret.setSchOffset(paramQUERY__.getOffset() + 1);
        //      ソート1キー
        ret.setSchSortKey(paramQUERY__.getSortKeyType().getValue());
        //      ソート1昇順降順
        ret.setSchOrder(paramQUERY__.getSortOrderFlg());
        //      ソート2キー
        ret.setSchSortKey2(paramQUERY__.getSortKey2Type().getValue());
        //      ソート2昇順降順
        ret.setSchOrder2(paramQUERY__.getSortOrder2Flg());


        return ret;
    }

    /**
     *
     * <br>[機  能] 検索結果 取得一覧を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 検索結果
     */
    public Collection<SchEntitiesResultModel> result() {
        return result__.stream()
                .collect(Collectors.toList());
    }

    /**
     *
     * <br>[機  能] 検索結果 最大件数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 検索結果
     */
    public Integer getMaxCount() {
        return resultMaxCount__;
    }


    /**
     *
     * <br>[機  能]  関連会社情報、アドレス帳情報を一括取得
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSids スケジュールSID一覧
     * @param usrSid  ユーザSID
     * @throws SQLException SQL実行例外
     * @return 会社情報Map配列(キー:スケジュールSID / 値:会社情報Map)
     */
    private Map<Integer, Map<String, SchEntitiesResultModel.ClientInfo>> __getCompanyMapList(
            List<Integer> scdSids,
            int usrSid
            ) throws SQLException {
        Connection con = ctx__.getCon();
        SchCompanyDao companyDao = new SchCompanyDao(con);
        List<SchCompanyModel> schCompanyList = companyDao.select(scdSids);
        Map<Integer, Map<String, SchEntitiesResultModel.ClientInfo>> ret
        = new HashMap<>();

        Map<String, ClientInfo> companyMap = new HashMap<String, ClientInfo>();

        ClientInfo noCompanyModel = new ClientInfo(null, null, gsMsg__);
        companyMap.put("0:0", noCompanyModel);

        List<Integer> acoSids = new ArrayList<Integer>();
        List<Integer> abaSids = new ArrayList<Integer>();

        // 必要な会社or拠点SIDを一覧へ追加
        for (SchCompanyModel company : schCompanyList) {
            Integer acoSid = Integer.valueOf(company.getAcoSid());
            Integer abaSid = Integer.valueOf(company.getAbaSid());
            if (acoSid > 0 && !acoSids.contains(acoSid)) {
                acoSids.add(acoSid);
            }
            if (abaSid > 0 && !abaSids.contains(abaSid)) {
                abaSids.add(abaSid);
            }
        }

        //アドレス情報を取得
        SchAddressDao addressDao = new SchAddressDao(con);
        List<SchAddressModel> addressList = addressDao.select(scdSids);
        Map<Integer, AdrAddressModel> addressMap = new HashMap<Integer, AdrAddressModel>();
        if (addressList != null && addressList.size() > 0) {
            String[] addressId = new String[addressList.size()];
            for (int index = 0; index < addressList.size(); index++) {
                addressId[index] = String.valueOf(addressList.get(index).getAdrSid());
            }
            List<AdrAddressModel> exAddressList =
                    new AdrAddressDao(ctx__.getCon())
                    .select(
                            __getAddressFromBaseMap(addressId, usrSid)
                                 .stream()
                                 .map(adr -> adr.getAdrSid())
                                 .collect(Collectors.toSet()));


            if (exAddressList != null) {
                List<Integer> adrSidList = new ArrayList<Integer>();
                // アドレスに紐付く会社or拠点SIDを一覧へ追加
                for (AdrAddressModel adrData : exAddressList) {
                    Integer acoSid = Integer.valueOf(adrData.getAcoSid());
                    Integer abaSid = Integer.valueOf(adrData.getAbaSid());
                    if (acoSid > 0 && !acoSids.contains(acoSid)) {
                        acoSids.add(acoSid);
                    }
                    if (abaSid > 0 && !abaSids.contains(abaSid)) {
                        abaSids.add(abaSid);
                    }
                    Integer adrSid = Integer.valueOf(adrData.getAdrSid());
                    addressMap.put(adrSid, adrData);
                    adrSidList.add(adrSid);
                }

                // アドレス一覧を並び替える(データ取得順)
                final List<Integer> sortList = new ArrayList<Integer>(adrSidList);
                Collections.sort(addressList, new Comparator<SchAddressModel>() {
                    public int compare(SchAddressModel o1, SchAddressModel o2) {
                        int sort1 = sortList.indexOf(Integer.valueOf(o1.getAdrSid()));
                        int sort2 = sortList.indexOf(Integer.valueOf(o2.getAdrSid()));
                        return (sort1 - sort2);
                    }
                });
            }
        }

        AdrCompanyDao     acoDao = new AdrCompanyDao(con);
        AdrCompanyBaseDao abaDao = new AdrCompanyBaseDao(con);
        Map<Integer, AdrCompanyModel> acoMap = acoDao.select(acoSids)
                                             .stream()
                                             .collect(Collectors.toMap(
                                                         aco -> aco.getAcoSid(),
                                                         aco -> aco
                                                     ));
        Map<Integer, AdrCompanyBaseModel> abaMap = abaDao.select(acoSids)
                .stream()
                .collect(Collectors.toMap(
                            aba -> aba.getAbaSid(),
                            aba -> aba
                        ));

        // スケジュールに使用される会社SID一覧から会社情報を取得
        for (SchCompanyModel company : schCompanyList) {
            Integer acoSid = Integer.valueOf(company.getAcoSid());
            Integer abaSid = Integer.valueOf(company.getAbaSid());

            Integer scdSid = Integer.valueOf(company.getScdSid());
            String companyId = acoSid + ":" + abaSid;

            AdrCompanyModel aco = acoMap.get(acoSid);
            AdrCompanyBaseModel aba = abaMap.get(abaSid);

            if (!ret.containsKey(scdSid)) {
                ret.put(scdSid, new HashMap<>());
            }
            if (!ret.get(scdSid).containsKey(companyId)) {
                ret.get(scdSid).put(companyId, new ClientInfo(aco, aba, gsMsg__));
            }
        }

        // スケジュールに使用されるアドレスSID一覧からアドレス情報を取得
        if (addressList != null) {
            for (SchAddressModel address : addressList) {
                Integer scdSid = Integer.valueOf(address.getScdSid());
                AdrAddressModel adrData = addressMap.get(Integer.valueOf(address.getAdrSid()));

                if (adrData != null) {
                    String companyId = adrData.getAcoSid() + ":" + adrData.getAbaSid();

                    if (!ret.containsKey(scdSid)) {
                        ret.put(scdSid, new HashMap<>());
                    }
                    if (!ret.get(scdSid).containsKey(companyId)) {
                        ret.get(scdSid).put(companyId, new ClientInfo(null, null, gsMsg__));
                    }

                    ClientInfo companyData = ret.get(scdSid).get(companyId);
                    if (companyData != null) {
                        companyData.getAddressArray(
                                ).add(new AddressInfo(adrData));
                    }
                }
            }
        }

        return ret;
    }

    /**
     *
     * <br>[機  能] アドレス情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList sid配列
     * @param usrSid セッションユーザSID
     * @return アドレス情報一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Sch040AddressModel> __getAddressFromBaseMap(String[] sidList,
            int usrSid) throws SQLException {
        Sch040Dao sch040dao = new Sch040Dao(ctx__.getCon());

        List<Sch040AddressModel> exAddressList
        = sch040dao.getAddressList(ctx__.getCon(), sidList, usrSid);
        return exAddressList;
    }

    /**
     * 関連済施設SIDリストを取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSids スケジュールSID一覧
     * @param userSid usrSid
     * @param rsvAdmin 施設予約管理者権限
     * @throws SQLException SQL実行例外
     * @return 施設リスト
     */
    private Map<Integer, List<RsvSisDataModel>> __getSelectResList(
            Connection con, List<Integer> scdSids, int userSid, boolean rsvAdmin)
                    throws SQLException {
        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        RsvSisDataDao dataDao = new RsvSisDataDao(con);

        Map<Integer, List<Integer>> schReservsMap = schRsvDao.getScheduleReserveDataMap(scdSids);

        Map<Integer, List<RsvSisDataModel>> ret = new HashMap<Integer, List<RsvSisDataModel>>();

        if (schReservsMap != null) {
            ArrayList<Integer> reservs = new ArrayList<Integer>();
            for (Integer scdSid : schReservsMap.keySet()) {
                for (Integer reservSid : schReservsMap.get(scdSid)) {
                    if (!reservs.contains(reservSid)) {
                        reservs.add(reservSid);
                    }
                }
            }

            ArrayList<RsvSisDataModel> selectResList = null;
            if (rsvAdmin) {
                //全施設
                selectResList = dataDao.selectGrpSisetuList(reservs);
            } else {
                //閲覧権限のある施設
                selectResList = dataDao.selectGrpSisetuCanReadList(reservs, userSid);
            }

            if (selectResList != null) {
                // 施設予約リスト作成(キー:施設予約SID / 値:施設予約データ)
                Map<Integer, RsvSisDataModel> reservMap = new HashMap<Integer, RsvSisDataModel>();
                List<Integer> rsvSidList = new ArrayList<Integer>();
                for (RsvSisDataModel mdl : selectResList) {
                    Integer reservSid = Integer.valueOf(mdl.getRsdSid());
                    reservMap.put(reservSid, mdl);
                    rsvSidList.add(reservSid);
                }

                // 並び順の指定(施設予約データ取得順)
                final List<Integer> sortList = new ArrayList<Integer>(rsvSidList);
                Comparator<RsvSisDataModel> comparator = new Comparator<RsvSisDataModel>() {
                    public int compare(RsvSisDataModel o1, RsvSisDataModel o2) {
                        int sort1 = sortList.indexOf(Integer.valueOf(o1.getRsdSid()));
                        int sort2 = sortList.indexOf(Integer.valueOf(o2.getRsdSid()));
                        return (sort1 - sort2);
                    }
                };

                // スケジュール毎に施設予約データ一覧を紐付け
                for (Integer scdSid : schReservsMap.keySet()) {
                    List<RsvSisDataModel> list = new ArrayList<RsvSisDataModel>();
                    List<Integer> schReservesList = schReservsMap.get(scdSid);
                    for (Integer reservSid : schReservesList) {
                        if (reservMap.containsKey(reservSid)) {
                            list.add(reservMap.get(reservSid));
                        }
                    }
                    Collections.sort(list, comparator); // 並び替え
                    ret.put(scdSid, list);
                }
            }
        }
        return ret;
    }






}
