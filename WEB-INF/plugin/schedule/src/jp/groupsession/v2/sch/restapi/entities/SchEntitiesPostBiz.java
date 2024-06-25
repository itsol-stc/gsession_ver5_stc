package jp.groupsession.v2.sch.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.ISchRegister.Builder;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchRemindConfWriter;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;

public class SchEntitiesPostBiz {

    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 実行結果*/
    private SchEntitiesResultModel result__ = null;
    /** 実行結果 スケジュールSID*/
    private Integer resultScheduleSid__;
    /** テンポラリディレクトリ*/
    private final String tempDirPath__;
    /** パラメータ*/
    private SchEntitiesPostParamModel param__;
    /** 採番コントローラ*/
    private MlCountMtController mlCnt__;



    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     * @param tempDirPath テンポラリディレクトリパス
     */
    public SchEntitiesPostBiz(SchEntitiesPostParamModel param,
            RestApiContext ctx,
            String tempDirPath) {
        param__ = param;
        ctx__ = ctx;
        tempDirPath__ = tempDirPath;
        try {
            mlCnt__
            = GroupSession.getResourceManager().getCountController(
                    ctx.getRequestModel());
        } catch (Exception e) {
            throw new RuntimeException("採番コントローラ取得失敗", e);
        }

    }
    /**
     *
     * <br>[機  能] ビジネスロジックの実行
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    public void execute() throws SQLException {
        boolean defAutoCommit = ctx__.getCon().getAutoCommit();
        ctx__.getCon().setAutoCommit(false);
        //登録処理
        __postSchedule();
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);


        //実行結果取得
        result__ = __createResult();

    }

    /**
     *
     * <br>[機  能] スケジュール登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __postSchedule() throws SQLException {
        RequestModel reqMdl = ctx__.getRequestModel();
        Connection con = ctx__.getCon();
        SchDataModel schMdl = null;
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg =
                cmnBiz.isCanUsePlugin(
                        GSConstMain.PLUGIN_ID_SMAIL,
                        ctx__.getPluginConfig());

        //登録モデルを作成
        schMdl = new SchDataModel();
        /** 選択ユーザSID */
        int selectUsrSid = -1;
        if (param__.getTargetType() == GSConstSchedule.USER_KBN_USER) {
            selectUsrSid = param__.getUserMap(ctx__)
                            .get(param__.getTargetId())
                            .getUsrSid();
        }
        if (param__.getTargetType() == GSConstSchedule.USER_KBN_GROUP) {
            selectUsrSid = param__.getGroupMap(ctx__)
                    .get(param__.getTargetId())
                    .getGrpSid();

        }
        schMdl.setScdUsrSid(selectUsrSid);
        schMdl.setScdUsrKbn(param__.getTargetType());


        UDate frDate = param__.getStartDateTime();
        UDate toDate = param__.getEndDateTime();
        UDate now = new UDate();

        schMdl.setScdDaily(param__.getUseTimeFlg());
        if (schMdl.getScdDaily() != GSConstSchedule.TIME_EXIST) {
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        }

        schMdl.setScdFrDate(frDate);
        schMdl.setScdToDate(toDate);
        schMdl.setScdBgcolor(param__.getColorType());
        schMdl.setScdTitle(param__.getTitleText());
        schMdl.setScdValue(NullDefault.getString(param__.getBodyText(), ""));
        schMdl.setScdBiko(NullDefault.getString(param__.getMemoText(), ""));
        schMdl.setScdPublic(param__.getPublicType());

        schMdl.setScdAuid(ctx__.getRequestUserSid());
        schMdl.setScdAdate(now);
        schMdl.setScdEuid(ctx__.getRequestUserSid());
        schMdl.setScdEdate(now);
        //編集区分
        schMdl.setScdEdit(param__.getEditType());
        //拡張登録SID
        int extSid = -1;
        schMdl.setSceSid(extSid);

        int scdSid = -1;
        int scdGpSid = GSConstSchedule.DF_SCHGP_ID;
        int scdResSid = GSConstSchedule.DF_SCHGP_ID;

        schMdl.setScdSid(scdSid);


        String[] svUsers = Arrays.stream(
                param__.getSameScheduledUserIdArray())
                .map(id -> param__.getUserMap(ctx__).get(id).getUsrSid())
                .map(sid -> String.valueOf(sid))
                .toArray(String[]::new);

        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            svUsers = null;
        }
        schMdl.setScdGrpSid(scdGpSid);
        String[] svReserves = Arrays.stream(
                param__.getFacilityIdArray())
                .map(id -> param__.getFacilityMap(ctx__).get(id).getRsdSid())
                .map(sid -> String.valueOf(sid))
                .toArray(String[]::new);
        schMdl.setScdRsSid(scdResSid);

        int attendKbn = param__.getUseAttendFlg();
        schMdl.setScdAttendKbn(attendKbn);
        if (attendKbn == GSConstSchedule.ATTEND_KBN_YES) {
            schMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_YES);
            schMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_YES);
        } else {
            schMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
            schMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
        }

        //リマインダー通知
        IPushServiceOperator psOpe = PushServiceOperator.getInstance(con, reqMdl.getDomain());
        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(schMdl.getScdUsrKbn(),
                ctx__.getRequestUserSid(),
                schMdl.getScdUsrSid());

        SchPriPushModel defConf = param__.getRemindDefConf(ctx__);

        SchRemindConfWriter.builder()
        .setDefConf(defConf)
        .setOldConf(null)
        .setReminder(
                param__.getRemindTimingType()
                )
        .setRemindMode(remindMode)
        .setTargetGrp(
                param__.getRemindGrpFlg()
                )
        .setTimeKbn(param__.getUseTimeFlg())
        .setPushUseable(
                psOpe.isUseable()
                )
        .build().write(schMdl);

        ISchRegister.Builder regBld;
        regBld = ISchRegister.simpleRegistBuilder(con, reqMdl, mlCnt__, schMdl);


        //公開対象の登録
        if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            regBld.setPubList(
                Stream.concat(
                    Stream.of(param__.getPublicTargetUserIdArray())
                        .map(id -> param__.getUserMap(ctx__).get(id).getUsrSid())
                        .map(sid -> {
                            SchDataPubModel sdpMdl = new SchDataPubModel();
                            sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                            sdpMdl.setSdpPsid(sid);
                            return sdpMdl;
                        }),
                    Stream.of(param__.getPublicTargetGroupIdArray())
                        .map(id -> param__.getGroupMap(ctx__).get(id).getGrpSid())
                        .map(sid -> {
                            SchDataPubModel sdpMdl = new SchDataPubModel();
                            sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                            sdpMdl.setSdpPsid(sid);
                            return sdpMdl;
                        })
                    )
                .collect(Collectors.toList())
            );
        }
        //同時登録分
        if (svUsers != null) {
            regBld.setUsers(
                    Stream.of(svUsers)
                        .map(Integer::parseInt)
                        .collect(Collectors.toSet())
                    );
        }
        //添付ファイルの登録
        String tempDir = tempDirPath__;
        List<Long> binSidList;
        try {
            binSidList = cmnBiz.insertBinInfo(
                    con, tempDir,
                    ctx__.getAppRootPath(),
                    mlCnt__,
                    ctx__.getRequestUserSid(),
                    new UDate())
                    .stream()
                    .map(str -> Long.parseLong(str))
                    .collect(Collectors.toList());
        } catch (TempFileException e) {
            throw new RuntimeException("添付ファイル登録に失敗", e);
        }
        regBld.setBinSidList(binSidList);



        //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を登録
        regBld.setUseContact(param__.getUseContactFlg() == 1);
        __setClient(regBld, ctx__.getRequestUserSid(),
                param__.getClientCompanyBaseIdArray(),
                param__.getClientAddressSidArray());

        regBld.setUseRsv(svReserves != null && svReserves.length > 0);

        //スケジュール登録ロジッククラス設定完了
        ISchRegister reg = regBld.build();

        //スケジュール・関連情報登録実行
        reg.regist();
        //form値にsidを保存
        reg.getScdSidMap().entrySet().stream().findAny()
            .flatMap(entGrp ->
                    entGrp.getValue().entrySet().stream()
                        .filter(ent -> Objects.equals(entGrp.getKey().getScdUsrSid(), ent.getKey()))
                        .map(ent -> ent.getValue())
                        .findAny()
                    )
            .ifPresent(sid -> resultScheduleSid__ = sid);
        scdResSid = reg.getScdSidMap().keySet().stream()
                .findAny()
                .map(grp -> grp.getScdResSid())
                .orElse(-1);
        schMdl.setScdRsSid(scdResSid);

        //ショートメール通知
        if (smailPluginUseFlg) {
            for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp
                    : reg.getScdSidMap().entrySet()) {
                SchDataGroupModel grp = entGrp.getKey();
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    int addUserSid = entry.getKey();
                    SchDataModel smlBaseSch = reg.getSchModel(grp, addUserSid);

                    //URL取得
                    String url = SchEntitiesUtil.createScheduleUrlDefo(
                            ctx__,
                            GSConstSchedule.CMD_EDIT,
                            String.valueOf(smlBaseSch.getScdSid()),
                            String.valueOf(smlBaseSch.getScdUsrSid()),
                            smlBaseSch.getScdFrDate().getDateString(),
                            String.valueOf(smlBaseSch.getScdUsrKbn())
                            );
                    //選択登録先
                    if (addUserSid == grp.getScdUsrSid()) {
                        try {
                            schBiz.sendPlgSmail(
                                    con, mlCnt__, smlBaseSch,
                                    ctx__.getAppRootPath(),
                                    ctx__.getPluginConfig(),
                                    smailPluginUseFlg, url);
                        } catch (Exception e) {
                            throw new RuntimeException("メール通知実行時例外", e);
                        }
                        continue;
                    }
                    //同時登録
                    if (schMdl.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {
                        try {
                            schBiz.sendAttendSmail(con, mlCnt__, schMdl, ctx__.getAppRootPath(),
                                    ctx__.getPluginConfig(), smailPluginUseFlg, url, 0);
                        } catch (Exception e) {
                            throw new RuntimeException("メール通知実行時例外", e);
                        }
                    } else {
                        try {
                            schBiz.sendPlgSmail(
                                    con, mlCnt__, smlBaseSch,
                                    ctx__.getAppRootPath(),
                                    ctx__.getPluginConfig(),
                                    smailPluginUseFlg, url);
                        } catch (Exception e) {
                            throw new RuntimeException("メール通知実行時例外", e);
                        }
                    }
                }
            }
        }

        __insertReserve(
                schMdl,
                null,
                svReserves,
                -1,
                ctx__.getAppRootPath(),
                ctx__.getPluginConfig(),
                smailPluginUseFlg);


    }
    private void __setClient(Builder regBld, int requestUserSid,
            String[] clientCompanyBaseCodeArray, int[] clientAddressSidArray) throws SQLException {
        String[] acoSids = new String[clientCompanyBaseCodeArray.length];
        String[] abaSids = new String[clientCompanyBaseCodeArray.length];
        String[] adrSids = new String[clientAddressSidArray.length];

        for (int i = 0; i < clientCompanyBaseCodeArray.length; i++) {
            String companyId =
                    Optional.ofNullable(clientCompanyBaseCodeArray[i])
                    .filter(code -> code.indexOf(":") >= 0)
                    .map(code -> code.substring(0, code.lastIndexOf(":")))
                    .orElse(null);
            int abaSid = Optional.ofNullable(clientCompanyBaseCodeArray[i])
                    .filter(code -> code.indexOf(":") >= 0)
                    .filter(code -> code.lastIndexOf(":") < code.length() - 1)
                    .map(code -> code.substring(code.lastIndexOf(":") + 1))
                    .map(sid -> Integer.valueOf(sid))
                    .orElse(-1);
            acoSids[i] =
                    String.valueOf(
                        param__.getClientCompanyMap(ctx__)
                            .get(companyId)
                            .getAcoSid()
                            );
            abaSids[i] =
                    String.valueOf(
                        param__.getClientBaseMap(ctx__)
                            .get(abaSid)
                            .getAbaSid()
                            );
        }
        for (int i = 0; i < clientAddressSidArray.length; i++) {
            adrSids[i] =
                    String.valueOf(
                            param__.getAddresMap(ctx__)
                                .get(clientAddressSidArray[i])
                                .getAdrSid()
                            );
        }

        regBld.setAdrSidArr(adrSids);
        regBld.setAbaSidArr(abaSids);
        regBld.setAcoSidArr(acoSids);
    }
    /**
     * <br>[機  能] 施設予約を新規登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールモデル
     * @param oldMdl 編集前スケジュールモデル
     * @param svReserves 施設SIDリスト
     * @param rsrSid 施設拡張SID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @throws SQLException
     * @throws Exception SQL実行時例外
     */
    void __insertReserve(SchDataModel schMdl,
            SchDataModel oldMdl,
            String[] svReserves,
            int rsrSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg
            ) throws SQLException {
        Connection con = ctx__.getCon();
        RequestModel reqMdl = ctx__.getRequestModel();

        ArrayList<int []> sidDataList = new ArrayList<int []>();
        //施設予約を登録する場合
        if (svReserves != null && svReserves.length > 0) {
            RsvSisYrkModel yrkParam = __createRsyMdl(schMdl, null);
            yrkParam.setRsrRsid(rsrSid);
            yrkParam.setRsdSid(
                    Stream.of(svReserves)
                        .map(str -> Integer.parseInt(str))
                        .findAny().get());
            IRsvYoyakuRegister.Builder regRsvBld =
                    IRsvYoyakuRegister.simpleRegistBuilder(
                            ctx__.getCon(),
                            ctx__.getRequestModel(),
                            mlCnt__,
                            appRootPath,
                            yrkParam);
            regRsvBld.setUseSch(true);
            regRsvBld.setSchResSidMap(
                    Map.of(schMdl.getScdFrDate().getDateString("/"),
                            schMdl.getScdRsSid())
                    );
            regRsvBld.setRsdSids(Stream.of(svReserves)
                        .map(str -> Integer.parseInt(str))
                        .collect(Collectors.toSet()));

            //公開対象の登録
            if (Objects.equals(yrkParam.getRsyPublic(), GSConstReserve.PUBLIC_KBN_USRGRP)) {

                regRsvBld.setPubList(
                        Stream.concat(
                            Stream.of(param__.getPublicTargetUserIdArray())
                                .map(id -> param__.getUserMap(ctx__).get(id).getUsrSid())
                                .map(sid -> {
                                    RsvDataPubModel sdpMdl = new RsvDataPubModel();
                                    sdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_USER);
                                    sdpMdl.setRdpPsid(sid);
                                    return sdpMdl;
                                }),
                            Stream.of(param__.getPublicTargetGroupIdArray())
                                .map(id -> param__.getGroupMap(ctx__).get(id).getGrpSid())
                                .map(sid -> {
                                    RsvDataPubModel sdpMdl = new RsvDataPubModel();
                                    sdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_GROUP);
                                    sdpMdl.setRdpPsid(sid);
                                    return sdpMdl;
                                })
                            )
                        .collect(Collectors.toList())
                    );

            }
            //施設予約を登録
            IRsvYoyakuRegister regRsv = regRsvBld.build();
            regRsv.regist();
            sidDataList.addAll(
                regRsv.getRsySidMap().values().stream()
                    .flatMap(map -> map.entrySet().stream())
                    .map(entry -> new int []{entry.getValue(), entry.getKey()})
                    .collect(Collectors.toList())
                );

            //施設予約用ショートメール通知
            if (smailPluginUseFlg) {
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                for (Entry<Integer, Integer> ent : regRsv.getRsySidMap().values().stream()
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toList())) {
                    int sisetsuSid = ent.getKey();
                    int sessionUsrSid = schMdl.getScdEuid();
                    int yoyakuSid = ent.getValue();
                    //選択した施設に承認設定がされている場合
                    if (rsvCmnBiz.isApprSis(con, sisetsuSid, sessionUsrSid)) {
                        //ショートメールで通知

                            RsvRegSmailModel regMdl = new RsvRegSmailModel();
                            regMdl.setCon(con);
                            regMdl.setReqMdl(reqMdl);
                            regMdl.setRsySid(yoyakuSid);
                            regMdl.setRsdSid(sisetsuSid);
                            regMdl.setCntCon(mlCnt__);
                            regMdl.setUserSid(sessionUsrSid);
                            regMdl.setAppRootPath(appRootPath);
                            regMdl.setPluginConfig(plconf);
                            try {
                                rsvCmnBiz.sendRegSmail(regMdl,
                                            SchEntitiesUtil.createReserveUrl(
                                                ctx__,
                                                yoyakuSid));
                            } catch (Exception e) {
                                throw new RuntimeException("メール通知実行時例外", e);
                            }

                    }
                }
            }
        }
    }


    /**
     * <br>[機  能] 登録用施設予約モデルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param baseMdl 生成元 スケジュールモデル
     * @param oldMdl 旧スケジュールモデル
     * @return 登録用施設予約モデル
     */
    private RsvSisYrkModel __createRsyMdl(SchDataModel baseMdl, SchDataModel oldMdl) {
        RsvSisYrkModel yrkParam = new RsvSisYrkModel();
        String moku = NullDefault.getString(baseMdl.getScdTitle(), "");
        yrkParam.setRsyMok(moku);
        yrkParam.setRsyFrDate(baseMdl.getScdFrDate());
        yrkParam.setRsyToDate(baseMdl.getScdToDate());
        yrkParam.setRsyBiko(NullDefault.getString(baseMdl.getScdValue(), ""));
        yrkParam.setRsyAuid(baseMdl.getScdEuid());
        yrkParam.setRsyAdate(baseMdl.getScdEdate());
        yrkParam.setRsyEuid(baseMdl.getScdEuid());
        yrkParam.setRsyEdate(baseMdl.getScdEdate());
        if (oldMdl != null) {
            yrkParam.setRsyAuid(oldMdl.getScdAuid());
            yrkParam.setRsyAdate(oldMdl.getScdAdate());
        }

        yrkParam.setScdRsSid(GSConstSchedule.DF_SCHGP_ID);
        yrkParam.setRsyEdit(baseMdl.getScdEdit());

        //施設予約と公開区分が異なるので、変換してからセット
        int schPublic = baseMdl.getScdPublic();
        int rsvPublic = GSConstReserve.PUBLIC_KBN_ALL;
        if (schPublic == GSConstSchedule.DSP_NOT_PUBLIC
                || schPublic == GSConstSchedule.DSP_YOTEIARI) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_PLANS;
        } else if (schPublic == GSConstSchedule.DSP_BELONG_GROUP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_GROUP;
        } else if (schPublic == GSConstSchedule.DSP_USRGRP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_USRGRP;
        } else if (schPublic == GSConstSchedule.DSP_TITLE) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_TITLE;
        }
        yrkParam.setRsyPublic(rsvPublic);

        return yrkParam;
    }

    /**
     * <p>result を取得します。
     * @return result
     * @see SchEntitiesPostBiz#result__
     */
    public SchEntitiesResultModel getResult() {
        return result__;
    }

    /**
     *
     * <br>[機  能] 実行結果モデルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果モデル
     */
    private SchEntitiesResultModel __createResult() {
        SchEntitiesGetParamModel qParam = new SchEntitiesGetParamModel();
        qParam.setScheduleSid(resultScheduleSid__);
        SchEntitiesQueryBiz qBiz = new SchEntitiesQueryBiz(
                qParam,
                ctx__);
        qBiz.execute();
        List<SchEntitiesResultModel> result = qBiz.getResult();
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }


}
