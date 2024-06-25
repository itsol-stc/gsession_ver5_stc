package jp.groupsession.v2.sch.restapi.entities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchRemindConfWriter;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchBinModel;
import jp.groupsession.v2.sch.model.SchCompanyModel;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.sch040.model.Sch040ContactModel;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchEntitiesPutBiz  {
    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 実行前*/
    private SchEntitiesResultModel prev__ = null;
    /** 実行結果*/
    private SchEntitiesResultModel result__ = null;
    /** 実行結果 スケジュールSID*/
    private Integer resultScheduleSid__;
    /** テンポラリディレクトリ*/
    private final String tempDirPath__;
    /** パラメータ*/
    private SchEntitiesPutParamModel param__;
    /** 採番コントローラ*/
    private MlCountMtController mlCnt__;
    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     * @param tempDirPath テンポラリディレクトリパス
     */
    public SchEntitiesPutBiz(SchEntitiesPutParamModel param,
            RestApiContext ctx, String tempDirPath) {
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
        //実行前状態取得
        prev__ = __createResult(param__.getScheduleSid());

        ctx__.getCon().setAutoCommit(false);
        //登録処理
        __putSchedule();
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);


        //実行結果取得
        result__ = __createResult(resultScheduleSid__);

    }
    /**
     *
     * <br>[機  能] 更新実行
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     * @throws Exception
     */
    private void __putSchedule() throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg =
                cmnBiz.isCanUsePlugin(
                        GSConstMain.PLUGIN_ID_SMAIL,
                        ctx__.getPluginConfig());
        //既存を引き継ぐ添付ファイルを展開
        if (param__.getBinSidArray() != null || param__.getBinSidArray().length > 0) {
            String dateStr = new UDate().getDateString();
            String domain = ctx__.getRequestModel().getDomain();

            //既に登録されているファイルが存在する場合、ファイル番号を最新からにする
            int i = 1;
            List < String > fileList = IOTools.getFileNames(tempDirPath__);
            if (fileList != null) {
                i += fileList.size();
            }
            try {
                for (long binSid : param__.getBinSidArray()) {
                    CmnBinfModel binMdl = cmnBiz.getBinInfo(ctx__.getCon(), binSid, domain);
                    if (binMdl != null) {
                        //添付ファイルをテンポラリディレクトリにコピーする。
                        cmnBiz.saveTempFile(
                                dateStr,
                                binMdl,
                                ctx__.getAppRootPath(),
                                tempDirPath__,
                                i);
                        i++;
                    }
                }
            } catch (TempFileException | IOException | IOToolsException e) {
                throw new RuntimeException("添付ファイル展開に失敗", e);
            }
        }


        __updateScheduleDate(ctx__.getRequestUserSid(),
                ctx__.getAppRootPath(),
                ctx__.getPluginConfig(),
                smailPluginUseFlg,
                param__.getOldData(ctx__));

    }

    /**
     * <br>[機  能] スケジュールを更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param schDataModel 編集前データ
     * @return sidDataList 予約SID、施設SIDリスト
     * @throws SQLException
     * @throws Exception 実行時例外
     */
    private ArrayList<int []> __updateScheduleDate(
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            SchDataModel schDataModel) throws SQLException  {

        Connection con = ctx__.getCon();
        RequestModel reqMdl =  ctx__.getRequestModel();
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

        //管理者設定を取得
        SchAdmConfModel adminConf = param__.getAconf(ctx__);

        CommonBiz cmnBiz = new CommonBiz();
        String scdSid = String.valueOf(param__.getScheduleSid());

        ArrayList<int []> sidDataList = new ArrayList<int []>();

        SchDataModel scdMdl = new SchDataModel();
        UDate now = new UDate();
        UDate frDate = param__.getStartDateTime();
        UDate toDate = param__.getEndDateTime();
        if (param__.getUseTimeFlg() != GSConstSchedule.TIME_EXIST) {
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        }

        scdMdl.setScdDaily(param__.getUseTimeFlg());
        scdMdl.setScdSid(Integer.parseInt(scdSid));
        scdMdl.setScdFrDate(frDate);
        scdMdl.setScdToDate(toDate);
        scdMdl.setScdBgcolor(param__.getColorType());
        scdMdl.setScdTitle(param__.getTitleText());
        scdMdl.setScdValue(NullDefault.getString(param__.getBodyText(), ""));
        scdMdl.setScdBiko(NullDefault.getString(param__.getMemoText(), ""));
        scdMdl.setScdPublic(
                param__.getPublicType()
                );
        scdMdl.setScdAuid(userSid);
        scdMdl.setScdAdate(now);
        scdMdl.setScdEuid(userSid);
        scdMdl.setScdEdate(now);

        //編集区分
        scdMdl.setScdEdit(
                param__.getEditType()
                );

        //出欠確認区分
        if (param__.getTargetType() == GSConstSchedule.USER_KBN_USER) {
            if (param__.getUseAttendFlg() == GSConstSchedule.ATTEND_KBN_YES) {
                scdMdl.setScdAttendKbn(GSConstSchedule.ATTEND_KBN_YES);
                scdMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_YES);
                scdMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_YES);
                scdMdl.setScdEdit(GSConstSchedule.EDIT_CONF_OWN);
                scdMdl.setScdAttendComment("");
            } else {
                scdMdl.setScdAttendKbn(GSConstSchedule.ATTEND_KBN_NO);
                scdMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
                scdMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
            }
        } else {
            scdMdl.setScdAttendKbn(GSConstSchedule.ATTEND_KBN_NO);
            scdMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
            scdMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
        }

        SchDataDao schDao = new SchDataDao(con);

        //拡張登録SID
        int extSid = schDataModel.getSceSid();
        scdMdl.setSceSid(extSid);
        //スケジュール施設予約SID
        int resSid = schDataModel.getScdRsSid();
        scdMdl.setScdRsSid(resSid);
        String[] svReserves = null;
        if (param__.getUseTimeFlg() == GSConstSchedule.TIME_EXIST) {
            svReserves = Arrays.stream(param__.getFacilityIdArray())
                    .map(fac -> param__.getFacilityMap(ctx__).get(fac).getRsdSid())
                    .map(sid -> String.valueOf(sid))
                    .toArray(String[]::new);
        }

        int scdResSid = GSConstSchedule.DF_SCHGP_ID;
        int newScdSid = -1;

        //施設拡張取得(スケジュール情報を削除する前に取得)
        RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con);
        RsvSisRyrkModel ryrkMdl = null;
        if (param__.getSameFacilityReserveEditFlg() == 1
                || param__.getUseTimeFlg() == GSConstSchedule.TIME_NOT_EXIST) {
            ryrkMdl = ryrkDao.selectFromScdSid(Integer.parseInt(scdSid));
        }

        if (param__.getTargetType() == GSConstSchedule.USER_KBN_USER) {
            if (param__.getUseAttendFlg() == GSConstSchedule.ATTEND_KBN_YES) {
                param__.setSameScheduledEditFlg(GSConstSchedule.ATTEND_KBN_YES);
            }

        }
        scdMdl.setScdUsrSid(param__.getTargetSid(ctx__));
        scdMdl.setScdUsrKbn(param__.getTargetType());

        /** 同時登録ユーザSID*/
        String[] svUsers =
                Arrays.stream(param__.getSameScheduledUserIdArray())
                .map(id -> param__.getUserMap(ctx__).get(id).getUsrSid())
                .map(sid -> String.valueOf(sid))
                .toArray(String[]::new);

        /** 登録対象ユーザ リマインダーデフォルト設定 同時登録ユーザと対象ユーザの設定を取得*/
        Map<Integer, SchPriPushModel> svUsersConfMap =
                schBiz.getUserPriConf(
                        Stream.concat(Stream.of(Optional.ofNullable(svUsers).orElse(new String[] {})
                                ), Stream.of(Optional.ofNullable((String) null).orElseGet(() -> {
                                    if (Objects.equals(param__.getTargetType(),
                                            GSConstSchedule.USER_KBN_GROUP)) {
                                        return "-1";
                                    }
                                    return String.valueOf(param__.getTargetSid(ctx__));
                                }))).toArray(String[]::new));

        //リマインダー通知設定反映
        int usrKbn = param__.getTargetType();
        int selectUsrSid = param__.getTargetSid(ctx__);
        BaseUserModel usMdl = reqMdl.getSmodel();
        int sessionUsrSid = usMdl.getUsrsid();

        IPushServiceOperator psOpe = PushServiceOperator.getInstance(con, reqMdl.getDomain());
        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(usrKbn,
                sessionUsrSid,
                selectUsrSid);

        SchRemindConfWriter.builder()
        .setDefConf(
                svUsersConfMap.get(selectUsrSid)
                )
        .setOldConf(
                SchPriPushModel.getInstance(schDataModel)
                )
        .setReminder(
                param__.getRemindTimingType()
                )
        .setRemindMode(remindMode)
        .setTargetGrp(
                param__.getRemindGrpFlg()
                )
        .setTimeKbn(
                param__.getUseTimeFlg()
                )
        .setPushUseable(
                psOpe.isUseable())
        .build().write(scdMdl);


        if (param__.getSameScheduledEditFlg() == 0) {
            //同時登録反映無しの場合
            scdMdl.setScdGrpSid(GSConstSchedule.DF_SCHGP_ID);
            scdMdl.setScdAttendKbn(GSConstSchedule.ATTEND_KBN_NO);
            scdMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
            scdMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
            scdMdl.setScdAttendComment("");

            //施設予約へ反映する場合、新たに採番
            if (param__.getSameFacilityReserveEditFlg() == 1) {
                if (svReserves != null && svReserves.length > 0) {
                    //スケジュール施設予約SID（施設予約有りの場合）
                    scdResSid = (int) mlCnt__.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                            SaibanModel.SBNSID_SUB_SCH_RES, userSid);
                    scdMdl.setScdRsSid(scdResSid);
                    schDao.updateRsSid(resSid, scdResSid);
                }
            }

            resultScheduleSid__ = scdMdl.getScdSid();

            //選択スケジュールを更新
            schDao.updateSchedule(scdMdl);

            //添付ファイルの削除，登録
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

            SchBinDao binDao = new SchBinDao(con);

            List<Integer> delSid = new ArrayList<Integer>();
            delSid.add(Integer.parseInt(scdSid));
            binDao.deleteTempFile(delSid);

            SchBinModel binMdl = new SchBinModel();
            binMdl.setScdSid(Integer.parseInt(scdSid));
            for (long binSid : binSidList) {
                binMdl.setBinSid(binSid);
                binDao.insert(binMdl);
            }

            //公開対象を更新
            __updateDisplayTarget(scdMdl.getScdSid(), scdMdl.getScdPublic());

            //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を更新
            __updateSchCompany(Integer.parseInt(scdSid), scdMdl.getScdUsrSid(),
                    scdMdl.getScdEdate(), scdMdl.getScdEuid());

            //URL取得
            String url = SchEntitiesUtil.createScheduleUrlDefo(
                    ctx__,
                    GSConstSchedule.CMD_EDIT,
                    String.valueOf(scdSid),
                    String.valueOf(scdMdl.getScdUsrSid()),
                    scdMdl.getScdFrDate().getDateString(),
                    String.valueOf(scdMdl.getScdUsrKbn()));
            try {
                schBiz.sendPlgSmail(
                        con, mlCnt__, scdMdl, appRootPath, plconf, smailPluginUseFlg, url);
            } catch (Exception e) {
                throw new RuntimeException("メール通知実行時例外", e);
            }


            //編集前のデータで出欠確認を行っていた場合、リレーションで紐付いている
            //回答側のスケジュールの出欠確認データをリセットする
            if (schDataModel.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {
                //グループ
                if (schDataModel.getScdGrpSid() != GSConstSchedule.DF_SCHGP_ID) {
                    schDao.updateAttendReset(schDataModel.getScdGrpSid());
                }
            }


            //通知予定リストの更新
            SchPushListDao splDao = new SchPushListDao(con);
            splDao.delete(Integer.parseInt(scdSid));
            if (param__.getUseTimeFlg()
                    == GSConstSchedule.TIME_EXIST) {
                if (usrKbn == GSConstSchedule.USER_KBN_USER) {
                    schBiz.insertPushInfUser(scdMdl);
                } else if (usrKbn == GSConstSchedule.USER_KBN_GROUP
                        && param__.getRemindGrpFlg()
                        == GSConstSchedule.REMINDER_USE_YES) {
                    Map<Integer, SchPriPushModel> reminderGroupMap =
                            schBiz.getGroupPriConf(scdMdl.getScdUsrSid());
                    schBiz.insertPushInfGroup(newScdSid, reminderGroupMap,
                            frDate.cloneUDate(), scdMdl.getScdTargetGrp());
                }
            }

        } else {
            //同時登録ユーザへ反映更新
            ISchRegister reg =  __deleteInsertScheduleDate(
                    appRootPath, plconf, smailPluginUseFlg, adminConf,
                    scdMdl,
                    psOpe);
            scdResSid =
                    reg.getScdGrpsList().stream()
                    .findAny()
                    .map(SchDataGroupModel::getScdResSid)
                    .orElse(GSConstSchedule.DF_SCHGP_ID);
        }

        int rsrSid = -1;
        //施設予約への更新判定 時間指定無しの場合は更新
        if (param__.getSameFacilityReserveEditFlg() == 1
                || param__.getUseTimeFlg() == GSConstSchedule.TIME_NOT_EXIST) {

            if (ryrkMdl != null) {
                rsrSid = ryrkMdl.getRsrRsid();
            }

            //施設予約を登録
            int yoyakuSid = -1;
            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
            RsvDataPubDao rdpDao = new RsvDataPubDao(con);
            if (svReserves != null) {
                for (int i = 0; i < svReserves.length; i++) {
                    yoyakuSid = (int) mlCnt__.getSaibanNumber(
                            GSConstReserve.SBNSID_RESERVE,
                            GSConstReserve.SBNSID_SUB_YOYAKU,
                            userSid);

                    //更新前の施設予約を取得
                    RsvSisYrkModel oldRsvMdl = null;
                    if (schDataModel.getScdRsSid() >= 0) {
                        oldRsvMdl = yrkDao.select(
                                Integer.parseInt(svReserves[i]), schDataModel.getScdRsSid());
                    }

                    RsvSisYrkModel yrkParam = new RsvSisYrkModel();
                    yrkParam.setRsySid(yoyakuSid);
                    yrkParam.setRsdSid(Integer.parseInt(svReserves[i]));
                    String moku = NullDefault.getString(param__.getTitleText(), "");
                    yrkParam.setRsyMok(moku);
                    yrkParam.setRsyFrDate(frDate);
                    yrkParam.setRsyToDate(toDate);
                    yrkParam.setRsyBiko(NullDefault.getString(param__.getBodyText(), ""));
                    if (oldRsvMdl != null) {
                        yrkParam.setRsyAuid(oldRsvMdl.getRsyAuid());
                        yrkParam.setRsyAdate(oldRsvMdl.getRsyAdate());
                    } else {
                        yrkParam.setRsyAuid(scdMdl.getScdAuid());
                        yrkParam.setRsyAdate(scdMdl.getScdAdate());
                    }
                    yrkParam.setRsyEuid(userSid);
                    yrkParam.setRsyEdate(now);
                    yrkParam.setScdRsSid(scdResSid);
                    //施設拡張SID
                    yrkParam.setRsrRsid(rsrSid);

                    yrkParam.setRsyEdit(param__.getEditType());

                    //施設予約と公開区分が異なるので、変換してからセット
                    int schPublic = param__.getPublicType();
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

                    //承認状況
                    RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                    rsvCmnBiz.setSisYrkApprData(con,  yrkParam.getRsdSid(), yrkParam, userSid);

                    yrkDao.insert(yrkParam);

                    //公開対象の登録
                    if (rsvPublic == GSConstReserve.PUBLIC_KBN_USRGRP) {
                        List<RsvDataPubModel> pubList = new ArrayList<>();
                        for (String id : param__.getPublicTargetUserIdArray()) {
                            RsvDataPubModel rdpMdl = new RsvDataPubModel();
                            rdpMdl.setRsySid(yrkParam.getRsySid());
                            rdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_USER);
                            rdpMdl.setRdpPsid(param__.getUserMap(ctx__).get(id).getUsrSid());
                            pubList.add(rdpMdl);
                        }
                        for (String id : param__.getPublicTargetGroupIdArray()) {
                            RsvDataPubModel rdpMdl = new RsvDataPubModel();
                            rdpMdl.setRsySid(yrkParam.getRsySid());
                            rdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_GROUP);
                            rdpMdl.setRdpPsid(param__.getGroupMap(ctx__).get(id).getGrpSid());
                            pubList.add(rdpMdl);
                        }
                        rdpDao.insert(pubList);
                    }

                    sidDataList.add(new int []{yoyakuSid, Integer.parseInt(svReserves[i])});

                    //施設予約区分別情報を登録（スケジュールからの場合は全て初期値）
                    RsvSisDataDao dataDao = new RsvSisDataDao(con);
                    Rsv070Model mdl = dataDao.getPopUpSisetuData(Integer.parseInt(svReserves[i]));
                    if (mdl != null) {

                        if (RsvCommonBiz.isRskKbnRegCheck(mdl.getRskSid())) {
                            RsvCommonBiz rsvBiz = new RsvCommonBiz();
                            RsvSisKyrkModel kyrkMdl =
                                    rsvBiz.getSisKbnInitData(
                                            con, reqMdl, mdl.getRskSid(), appRootPath);
                            RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con);
                            //編集前施設予約区分情報モデル
                            RsvSisKyrkModel oldKyrkModel = null;
                            if (oldRsvMdl != null) {
                                oldKyrkModel = kyrkDao.select(oldRsvMdl.getRsySid());
                            }

                            kyrkMdl.setRsySid(yoyakuSid);
                            if (oldKyrkModel != null) {
                                kyrkMdl.setRkyAuid(oldKyrkModel.getRkyAuid());
                                kyrkMdl.setRkyAdate(oldKyrkModel.getRkyAdate());
                            } else {
                                kyrkMdl.setRkyAuid(userSid);
                                kyrkMdl.setRkyAdate(now);
                            }
                            kyrkMdl.setRkyEuid(userSid);
                            kyrkMdl.setRkyEdate(now);

                            kyrkDao.insert(kyrkMdl);
                        }
                    }
                }
            }

            if (resSid > -1) {
                //削除するの施設予約SIDを取得する
                RsvSisYrkDao rsyDao = new RsvSisYrkDao(con);
                ArrayList<Integer> rsySidList = rsyDao.getScheduleRserveSids(resSid);
                if (rsySidList != null && rsySidList.size() > 0) {
                    //施設予約区分別情報を削除
                    RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con);
                    kyrkDao.delete(rsySidList);

                    //施設予約公開対象を削除
                    rdpDao.deleteList(rsySidList);
                }

                //旧施設予約情報を削除
                yrkDao.deleteScdRsSid(resSid);

            }

            //ひも付いている施設予約情報が無くなった場合、予約拡張データを削除
            if (rsrSid > -1 && yrkDao.getYrkDataCnt(rsrSid) < 1) {
                //件数取得し0件の場合
                ryrkDao.delete(rsrSid);
                //施設予約拡張区分別情報削除
                RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con);
                kryrkDao.delete(rsrSid);

                //施設予約拡張公開情報を削除
                RsvExdataPubDao repDao = new RsvExdataPubDao(con);
                repDao.delete(rsrSid);
            }
        }

        return sidDataList;
    }
    /**
     * <br>[機  能] 既存スケジュールを削除し、新規登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションパス
     * @param plconf プラグインコンフィグ
     * @param smailPluginUseFlg ショートメール使用フラグ
     * @param admConf 管理者設定
     * @param scdMdl 登録ベーススケジュールモデル
     * @param psOpe Push通知実行クラスインタフェース
     * @return 登録実行後のISchRegister
     * @throws SQLException
     */
    private ISchRegister __deleteInsertScheduleDate(
            String appRootPath,
            PluginConfig plconf, boolean smailPluginUseFlg,
            SchAdmConfModel admConf,
            SchDataModel scdMdl,
            IPushServiceOperator psOpe)
            throws SQLException {
        Connection con = ctx__.getCon();
        RequestModel reqMdl = ctx__.getRequestModel();

        int userSid = ctx__.getRequestUserSid();
        String scdSid = String.valueOf(param__.getScheduleSid());
        /** 同時登録ユーザSID*/
        String[] svUsers =
                Arrays.stream(param__.getSameScheduledUserIdArray())
                .map(id -> param__.getUserMap(ctx__).get(id).getUsrSid())
                .map(sid -> String.valueOf(sid))
                .toArray(String[]::new);
        String[] svReserves = null;
        if (param__.getUseTimeFlg() == GSConstSchedule.TIME_EXIST) {
            svReserves = Arrays.stream(param__.getFacilityIdArray())
                    .map(fac -> param__.getFacilityMap(ctx__).get(fac).getRsdSid())
                    .map(sid -> String.valueOf(sid))
                    .toArray(String[]::new);
        }

        //出欠確認区分「確認する」の場合
        int sendAttendMailType = param__.getOldData(ctx__).getScdAttendKbn();

        Map<Integer, Integer> attendMap = null;
        Map<Integer, String> attendCommentMap = null;
        /** 旧スケジュールの通知設定マップ*/
        Map<Integer, SchPriPushModel> oldPriPushMap = new HashMap<>();


        CommonBiz cmnBiz = new CommonBiz();
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

        //同時登録スケジュールSIDリスト
        ScheduleSearchDao ssDao = new ScheduleSearchDao(con);
        ArrayList<Integer> scds = ssDao.getScheduleUsrs(
                Integer.parseInt(scdSid),
                userSid,
                admConf.getSadCrange(),
                GSConstSchedule.SSP_AUTHFILTER_EDIT
                );

        ArrayList<Integer> oldScdSids = new ArrayList<>(Stream.concat(
                        Stream.of(Integer.parseInt(scdSid)),
                        scds.stream())
                        .collect(Collectors.toList()));
        if (param__.getTargetType()
                == GSConstSchedule.USER_KBN_USER) {
            SchDataDao schDao = new SchDataDao(con);
            //出欠確認区分「確認する」の場合
            if (param__.getUseAttendFlg()
                    == GSConstSchedule.ATTEND_KBN_YES) {
                //各ユーザ出欠確認の回答データを引き継ぐため、削除前のスケジュールデータより
                //ユーザSID：出欠パラメータを取得する
                attendMap = schDao.selectAttendData(Integer.valueOf(scdSid));
                attendCommentMap = schDao.selectAttendDataComment(Integer.valueOf(scdSid));
                //出席確認者は出席
                attendMap.put(scdMdl.getScdUsrSid(), GSConstSchedule.ATTEND_ANS_YES);
            }
            //ユーザスケジュールの場合、旧スケジュールの通知設定を取得
            oldPriPushMap.putAll(
                    schDao.getSchedules(oldScdSids).stream()
                        .collect(
                                Collectors.toMap(SchDataModel::getScdUsrSid,
                                        SchPriPushModel::getInstance))
                    );
        }


        //新スケジュールを登録
        ISchRegister.Builder regBld;
        regBld = ISchRegister.simpleRegistBuilder(con, reqMdl, mlCnt__, scdMdl);

        //更新時引継ぐ情報を設定
        regBld.setSchExtSid(scdMdl.getSceSid());
        regBld.setOldPushMap(oldPriPushMap);
        regBld.setOldAttendMap(attendMap);
        regBld.setOldAttendCommentMap(attendCommentMap);

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

        //公開対象の登録
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            List<SchDataPubModel> pubList = new ArrayList<>();
            for (String id : param__.getPublicTargetUserIdArray()) {
                SchDataPubModel sdpMdl = new SchDataPubModel();
                sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                sdpMdl.setSdpPsid(param__.getUserMap(ctx__).get(id).getUsrSid());
                pubList.add(sdpMdl);
            }
            for (String id : param__.getPublicTargetGroupIdArray()) {
                SchDataPubModel sdpMdl = new SchDataPubModel();
                sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                sdpMdl.setSdpPsid(param__.getGroupMap(ctx__).get(id).getGrpSid());
                pubList.add(sdpMdl);
            }

            regBld.setPubList(
                    pubList
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
        //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を登録
        String[] clientCompanyBaseCodeArray =
                param__.getClientCompanyBaseIdArray();
        int[] clientAddressSidArray =
                param__.getClientAddressSidArray();
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

        regBld.setUseContact((param__.getUseContactFlg() == 1));
        regBld.setAdrSidArr(adrSids);
        regBld.setAbaSidArr(abaSids);
        regBld.setAcoSidArr(acoSids);

        regBld.setUseRsv(svReserves != null && svReserves.length > 0);

        //スケジュール登録ロジッククラス設定完了
        ISchRegister reg = regBld.build();

        //スケジュール・関連情報登録実行
        reg.regist();

        resultScheduleSid__ = reg.getScdSidMap().values()
            .stream()
            .flatMap(m -> m.entrySet().stream())
            .filter(entry -> entry.getKey() == param__.getTargetSid(ctx__))
            .map(entry -> entry.getValue())
            .findAny()
            .orElse(-1);

        //ショートメール通知
        if (smailPluginUseFlg) {
            for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp
                    : reg.getScdSidMap().entrySet()) {
                SchDataGroupModel grp = entGrp.getKey();
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    int addUserSid = entry.getKey();
                    SchDataModel smlBaseSch = reg.getSchModel(grp, addUserSid);
                    String url = SchEntitiesUtil.createScheduleUrlDefo(
                            ctx__,
                            GSConstSchedule.CMD_EDIT,
                            String.valueOf(scdSid),
                            String.valueOf(scdMdl.getScdUsrSid()),
                            scdMdl.getScdFrDate().getDateString(),
                            String.valueOf(scdMdl.getScdUsrKbn()));

                    //選択登録先
                    if (addUserSid == grp.getScdUsrSid()) {
                        try {
                            schBiz.sendPlgSmail(
                                    con, mlCnt__, smlBaseSch,
                                    appRootPath, plconf, smailPluginUseFlg, url);
                        } catch (Exception e) {
                            throw new RuntimeException("メール通知実行時例外", e);
                        }

                        continue;
                    }
                    //同時登録ユーザ
                    if (scdMdl.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {
                        if (sendAttendMailType >= 0) {
                            try {
                                schBiz.sendAttendSmail(con, mlCnt__, smlBaseSch, appRootPath,
                                        plconf, smailPluginUseFlg, url, sendAttendMailType);
                            } catch (Exception e) {
                                throw new RuntimeException("メール通知実行時例外", e);
                            }
                        }
                    } else {
                        try {
                            schBiz.sendPlgSmail(con,
                                    mlCnt__,
                                    smlBaseSch,
                                    appRootPath,
                                    plconf,
                                    smailPluginUseFlg,
                                    url);
                        } catch (Exception e) {
                            throw new RuntimeException("メール通知実行時例外", e);
                        }
                    }
                }
            }
        }

        //旧スケジュールを削除
        SchEntitiesUtil.deleteSchedule(
                ctx__,
                oldScdSids,
                param__.getUseContactFlg()
                );


        return reg;
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
     * <p>prev を取得します。
     * @return result
     * @see SchEntitiesPostBiz#prev__
     */
    public SchEntitiesResultModel getPrev() {
        return prev__;
    }
    /**
     *
     * <br>[機  能] 実行結果モデルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param resultScheduleSid 対象SID
     * @return 実行結果モデル
     */
    private SchEntitiesResultModel __createResult(int resultScheduleSid) {
        SchEntitiesGetParamModel qParam = new SchEntitiesGetParamModel();
        qParam.setScheduleSid(resultScheduleSid);
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
    /**
     * <br>[機  能] 公開対象の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param pubKbn 公開区分
     * @throws SQLException SQL実行時例外
     */
    private void __updateDisplayTarget(int scdSid, int pubKbn) throws SQLException {
        Connection con = ctx__.getCon();
        SchDataPubDao sdpDao = new SchDataPubDao(con);
        List<Integer> delSid = new ArrayList<Integer>();
        delSid.add(scdSid);
        sdpDao.delete(delSid);

        if (pubKbn == GSConstSchedule.DSP_USRGRP) {
            List<SchDataPubModel> pubList = new ArrayList<>();
            for (String id : param__.getPublicTargetUserIdArray()) {
                SchDataPubModel sdpMdl = new SchDataPubModel();
                sdpMdl.setScdSid(scdSid);
                sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                sdpMdl.setSdpPsid(param__.getUserMap(ctx__).get(id).getUsrSid());
                pubList.add(sdpMdl);
            }
            for (String id : param__.getPublicTargetGroupIdArray()) {
                SchDataPubModel sdpMdl = new SchDataPubModel();
                sdpMdl.setScdSid(scdSid);
                sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                sdpMdl.setSdpPsid(param__.getGroupMap(ctx__).get(id).getGrpSid());
                pubList.add(sdpMdl);
            }
            sdpDao.insert(pubList);
        }
    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param userSid 登録/更新ユーザSID
     * @param date 更新日付
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __updateSchCompany(int scdSid,
            int userSid, UDate date, int sessionUserSid)
                    throws SQLException {
        Connection con = ctx__.getCon();

        List<Integer> scdSidList = new ArrayList<Integer>();
        scdSidList.add(scdSid);

        SchEntitiesUtil.deleteSchCompany(con, scdSidList, param__.getUseContactFlg());

        Map<Integer, Integer> scdUserMap = new HashMap<Integer, Integer>();
        scdUserMap.put(scdSid, userSid);
        __insertSchCompany(scdSidList, scdUserMap, sessionUserSid, date);
    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @param scdUserMap スケジュールSIDとユーザSIDのMapping
     * @param sessionUserSid セッションユーザSID
     * @param date 更新日付
     * @throws SQLException SQL実行時例外
     */
    private void __insertSchCompany(
            List<Integer> scdSidList,
            Map<Integer, Integer> scdUserMap,
            int sessionUserSid, UDate date)
                    throws SQLException {
        Connection con = ctx__.getCon();
        String[] clientCompanyBaseCodeArray =
                param__.getClientCompanyBaseIdArray();
        int[] clientAddressSidArray =
                param__.getClientAddressSidArray();
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

        //会社情報Mappingを登録
        List<SchCompanyModel> companyList = createCompanyModel(scdSidList,
                acoSids,
                abaSids,
                sessionUserSid, date);
        if (companyList != null) {
            SchCompanyDao companyDao = new SchCompanyDao(con);
            for (SchCompanyModel companyModel : companyList) {
                companyDao.insert(companyModel);
            }
        }

        List<SchAddressModel> addressList = createAddressModel(scdSidList, adrSids,
                sessionUserSid, date);
        if (addressList != null) {
            SchAddressDao addressDao = new SchAddressDao(con);
            boolean contactFlg = (param__.getUseContactFlg() == 1);

            String contactTitle = param__.getTitleText();
            String[] startDate = new String[5];
            startDate[0] = String.valueOf(
                    param__.getStartDateTime().getYear()
                    );
            startDate[1] = String.valueOf(
                    param__.getStartDateTime().getMonth()
                    );
            startDate[2] = String.valueOf(
                    param__.getStartDateTime().getIntDay()
                    );
            startDate[3] = String.valueOf(
                    param__.getStartDateTime().getIntHour()
                    );
            startDate[4] = String.valueOf(
                    param__.getStartDateTime().getIntMinute()
                    );
            String[] endDate = new String[5];
            endDate[0] = String.valueOf(
                    param__.getEndDateTime().getYear()
                    );
            endDate[1] = String.valueOf(
                    param__.getEndDateTime().getMonth()
                    );
            endDate[2] = String.valueOf(
                    param__.getEndDateTime().getIntDay()
                    );
            endDate[3] = String.valueOf(
                    param__.getEndDateTime().getIntHour()
                    );
            endDate[4] = String.valueOf(
                    param__.getEndDateTime().getIntMinute()
                    );

            int adcGrpSid = -1;
            Map<Integer, Integer> contactMap = new HashMap<Integer, Integer>();
            if (contactFlg && adrSids != null) {
                //アドレス帳情報が複数選択されている場合はコンタクト履歴グループSIDを採番する
                if (adrSids.length > 1) {
                    adcGrpSid = (int) mlCnt__.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                            GSConst.SBNSID_SUB_CONTACT_GRP,
                            sessionUserSid);
                }

                //コンタクト履歴の登録
                for (String adrSid : adrSids) {
                    Sch040ContactModel contactMdl
                    = createContactModel(Integer.parseInt(adrSid), adcGrpSid,
                            contactTitle, startDate, endDate,
                            sessionUserSid, date);
                    AdrContactModel model = new AdrContactModel();
                    try {
                        BeanUtils.copyProperties(model, contactMdl);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    AdrContactDao adcDao = new AdrContactDao(con);
                    adcDao.insert(model);
                    contactMap.put(contactMdl.getAdrSid(), contactMdl.getAdcSid());
                }

            }

            for (SchAddressModel adrMdl : addressList) {
                if (contactFlg) {
                    adrMdl.setAdcSid(contactMap.get(adrMdl.getAdrSid()));
                }

                addressDao.insert(adrMdl);
            }
        }
    }
    /**
     * <br>[機  能] スケジュール-会社情報Mapping Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @param acoSidList 会社SID
     * @param abaSidList 会社拠点SID
     * @param userSid セッションユーザSID
     * @param date 登録/更新日付
     * @return スケジュール-会社情報Mapping Model
     */
    public List<SchCompanyModel> createCompanyModel(List<Integer> scdSidList,
            String[] acoSidList,
            String[] abaSidList,
            int userSid, UDate date) {

        List<SchCompanyModel> companyList = null;

        if (acoSidList != null && abaSidList != null) {

            companyList = new ArrayList<SchCompanyModel>();

            for (int scdSid : scdSidList) {
                for (int index = 0; index < acoSidList.length; index++) {
                    SchCompanyModel companyModel = new SchCompanyModel();
                    companyModel.setScdSid(scdSid);
                    companyModel.setAcoSid(Integer.parseInt(acoSidList[index]));
                    companyModel.setAbaSid(Integer.parseInt(abaSidList[index]));
                    companyModel.setSccAuid(userSid);
                    companyModel.setSccAdate(date);
                    companyModel.setSccEuid(userSid);
                    companyModel.setSccEdate(date);

                    companyList.add(companyModel);
                }
            }
        }

        return companyList;
    }
    /**
     * <br>[機  能] スケジュール-アドレス帳情報Mapping Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @param adrSidList アドレス帳SID
     * @param userSid セッションユーザSID
     * @param date 登録/更新日付
     * @return スケジュール-会社情報Mapping Model
     */
    public List<SchAddressModel> createAddressModel(List<Integer> scdSidList, String[] adrSidList,
            int userSid, UDate date) {

        List<SchAddressModel> addressList = null;

        if (adrSidList != null) {

            addressList = new ArrayList<SchAddressModel>();

            for (Integer scdSid : scdSidList) {
                for (String adrSid : adrSidList) {
                    SchAddressModel addressModel = new SchAddressModel();
                    addressModel.setScdSid(scdSid);
                    addressModel.setAdrSid(Integer.parseInt(adrSid));
                    addressModel.setScaAuid(userSid);
                    addressModel.setScaAdate(date);
                    addressModel.setScaEuid(userSid);
                    addressModel.setScaEdate(date);

                    addressList.add(addressModel);
                }
            }
        }

        return addressList;
    }
    /**
     * <br>[機  能] コンタクト履歴Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレスSID
     * @param adcGrpSid アドレスグループSID
     * @param title タイトル
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @param userSid 登録/更新セッションユーザSID
     * @param date 登録/更新日付
     * @return コンタクト履歴Model
     * @throws SQLException コンタクト履歴SIDの採番に失敗
     */
    public Sch040ContactModel createContactModel(int adrSid, int adcGrpSid,
            String title,
            String[] startDate, String[] endDate,
            int userSid, UDate date)
                    throws SQLException {

        int adcSid = (int) mlCnt__.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                GSConst.SBNSID_SUB_CONTACT,
                userSid);

        Sch040ContactModel contactMdl = new Sch040ContactModel();
        contactMdl.setAdcSid(adcSid);
        contactMdl.setAdrSid(adrSid);
        contactMdl.setAdcTitle(title);
        contactMdl.setAdcType(GSConst.CONTYP_MEETING);

        if (StringUtil.isNullZeroString(startDate[3])) {
            startDate[3] = "0";
        }
        if (StringUtil.isNullZeroString(startDate[4])) {
            startDate[4] = "0";
        }
        contactMdl.setAdcCttime(__createDate(startDate));

        if (StringUtil.isNullZeroString(endDate[3])) {
            endDate[3] = "23";
        }
        if (StringUtil.isNullZeroString(endDate[4])) {
            endDate[4] = "55";
        }
        contactMdl.setAdcCttimeTo(__createDate(endDate));

        contactMdl.setAdcAuid(userSid);
        contactMdl.setAdcAdate(date);
        contactMdl.setAdcEuid(userSid);
        contactMdl.setAdcEdate(date);
        contactMdl.setAdcGrpSid(adcGrpSid);

        return contactMdl;
    }
    /**
     * <br>[機  能] UDateの作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param dateElement 日時(年、月、日、時、分)
     * @return UDate
     */
    private UDate __createDate(String[] dateElement) {
        UDate date = new UDate();
        date.setZeroHhMmSs();
        date.setDate(Integer.parseInt(dateElement[0]),
                Integer.parseInt(dateElement[1]),
                Integer.parseInt(dateElement[2]));
        date.setHour(Integer.parseInt(dateElement[3]));
        date.setMinute(Integer.parseInt(dateElement[4]));

        return date;
    }


}
