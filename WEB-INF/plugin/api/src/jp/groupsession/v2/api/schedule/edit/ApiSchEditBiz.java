package jp.groupsession.v2.api.schedule.edit;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.api.schedule.search.ApiSchSearchBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.rsv.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchRemindConfWriter;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchCompanyModel;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.model.SchPushListModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.sch.sch040.model.Sch040ContactModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能] スケジュール編集WEBAPIビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchEditBiz {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    /** コネクション */
    public Connection con__ = null;
    /** リクエストモデル*/
    public RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;

    /** パラメータモデル */
    ApiSchEditParamModel formParam__;
    /** 処理モード 登録・編集*/
    public static final int MODE_EDIT = 0;
    /** 処理モード 削除*/
    public static final int MODE_DELETE = 1;
    /** 処理モード 出欠応答*/
    public static final int MODE_ANSER = 2;

    /**
     * <p>コンストラクタ
     */
    public ApiSchEditBiz() {
    }

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     * @param cntCon MlCountMtController
     */
    public ApiSchEditBiz(Connection con,
            RequestModel reqMdl, MlCountMtController cntCon) {
        super();
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }

    /**
     * <p>formParam を取得します。
     * @return formParam
     */
    public ApiSchEditParamModel getFormParam() {
        return formParam__;
    }
    /**
     * <p>formParam をセットします。
     * @param formParam formParam
     */
    public void setFormParam(ApiSchEditParamModel formParam) {
        formParam__ = formParam;
    }


    /**
     * <br>[機  能] スケジュールを新規登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 登録者SID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @throws Exception SQL実行時例外
     */
    public void insertSchedule(
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg
            ) throws Exception {

        SchDataModel schMdl = null;
        SchCommonBiz schBiz = new SchCommonBiz(con__, reqMdl__);

        //登録モデルを作成
        schMdl = new SchDataModel();
        UDate frDate = formParam__.getFrDate();
        UDate toDate = formParam__.getToDate();
        UDate now = new UDate();

        schMdl.setScdDaily(NullDefault.getInt(formParam__.getTimeKbn(),
                GSConstSchedule.TIME_EXIST));
        /** 選択ユーザSID */
        int selectUsrSid = getTargetSid(con__, formParam__.getUserKbn(),
                                        formParam__.getUsrSid(), formParam__.getTargetId(),
                                        reqMdl__.getSmodel().getUsrsid());
        /** 選択ユーザ区分 */
        int selectUsrKbn = NullDefault.getInt(formParam__.getUserKbn(),
                GSConstSchedule.USER_KBN_USER);

        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        }

        schMdl.setScdFrDate(frDate);
        schMdl.setScdToDate(toDate);
        schMdl.setScdBgcolor(
                NullDefault.getInt(formParam__.getColorKbn(), GSConstSchedule.DF_BG_COLOR));
        schMdl.setScdTitle(formParam__.getTitle());
        schMdl.setScdValue(NullDefault.getString(formParam__.getNaiyo(), ""));
        schMdl.setScdBiko(NullDefault.getString(formParam__.getBiko(), ""));
        schMdl.setScdPublic(
                NullDefault.getInt(formParam__.getSchKf(), GSConstSchedule.DSP_PUBLIC));

        schMdl.setScdAuid(userSid);
        schMdl.setScdAdate(now);
        schMdl.setScdEuid(userSid);
        schMdl.setScdEdate(now);
        //編集区分
        schMdl.setScdEdit(
                NullDefault.getInt(formParam__.getSchEf(), GSConstSchedule.EDIT_CONF_NONE));
        //拡張登録SID
        int extSid = -1;
        schMdl.setSceSid(extSid);

        int scdSid = -1;
        int scdGpSid = GSConstSchedule.DF_SCHGP_ID;
        int scdResSid = GSConstSchedule.DF_SCHGP_ID;

        schMdl.setScdSid(scdSid);
        schMdl.setScdUsrSid(selectUsrSid);
        schMdl.setScdUsrKbn(selectUsrKbn);


        String[] svUsers = formParam__.getSameScheduledUser();
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            svUsers = null;
        }
        schMdl.setScdGrpSid(scdGpSid);
        String[] svReserves = formParam__.getReserves();
        schMdl.setScdRsSid(scdResSid);

        int attendKbn = NullDefault.getInt(formParam__.getAttendKbn(),
                GSConstSchedule.ATTEND_KBN_NO);
        schMdl.setScdAttendKbn(attendKbn);
        if (attendKbn == GSConstSchedule.ATTEND_KBN_YES) {
            schMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_YES);
            schMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_YES);
        } else {
            schMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
            schMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
        }

        //リマインダー通知
        IPushServiceOperator psOpe = PushServiceOperator.getInstance(con__, reqMdl__.getDomain());
        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(schMdl.getScdUsrKbn(),
                userSid,
                schMdl.getScdUsrSid());

        SchPriPushModel defConf = null;
        if (selectUsrKbn == GSConstSchedule.USER_KBN_USER) {
            defConf =  SchPriPushModel.getInstance(
                    schBiz.getSchPriConfModel(con__, selectUsrSid)
                    );
        } else {
            defConf =  SchPriPushModel.getInstance(
                        new SchDataModel()
                    );
        }
        SchRemindConfWriter.builder()
        .setDefConf(defConf)
        .setOldConf(null)
        .setReminder(
                NullDefault.getInt(
                    formParam__.getReminder(),
                    (i) -> (SchEnumReminderTime.valueOf(i) != null),
                    defConf.getSccReminder())
                )
        .setRemindMode(remindMode)
        .setTargetGrp(
                NullDefault.getInt(formParam__.getTargetGrp(),
                        (i) -> (i == GSConstSchedule.REMINDER_USE_YES
                               || i == GSConstSchedule.REMINDER_USE_NO),
                        GSConstSchedule.REMINDER_USE_YES)
                )
        .setTimeKbn(NullDefault.getInt(
                formParam__.getTimeKbn(), GSConstSchedule.TIME_EXIST))
        .setPushUseable(
                psOpe.isUseable()
                )
        .build().write(schMdl);



        ISchRegister.Builder regBld;
        regBld = ISchRegister.simpleRegistBuilder(con__, reqMdl__, cntCon__, schMdl);


        //公開対象の登録
        if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            regBld.setPubList(
                Stream.of(formParam__.getSchKfTarget())
                .map(targetSid -> {
                    SchDataPubModel sdpMdl = new SchDataPubModel();
                    if (targetSid.startsWith("G")) {
                        sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid.substring(1)));
                    } else {
                        sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid));
                    }
                    return sdpMdl;
                })
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
        //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を登録
        regBld.setUseContact(NullDefault.getInt(formParam__.getContactFlg(), 0) == 1);

        Map<String, Sch040CompanyModel> comMap =
        makeCompanyMap(userSid,
                formParam__.getAcoSid(),
                formParam__.getAbaSid(),
                formParam__.getAdress());
        String[] acoSids = new String[comMap.size()];
        String[] abaSids = new String[comMap.size()];
        int index = 0;
        for (String key : comMap.keySet()) {
            Sch040CompanyModel company = comMap.get(key);
            acoSids[index] = String.valueOf(company.getCompanySid());
            abaSids[index] = String.valueOf(company.getCompanyBaseSid());
            index++;
        }
        regBld.setAdrSidArr(formParam__.getAdress());
        regBld.setAbaSidArr(abaSids);
        regBld.setAcoSidArr(acoSids);

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
            .ifPresent(sid -> formParam__.setSchSid(String.valueOf(sid)));
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
                    String url = createScheduleUrlDefo(GSConstSchedule.CMD_EDIT,
                                                       String.valueOf(smlBaseSch.getScdSid()),
                                                       String.valueOf(smlBaseSch.getScdUsrSid()),
                                                       smlBaseSch.getScdFrDate().getDateString(),
                                                       String.valueOf(smlBaseSch.getScdUsrKbn())
                                                       );
                    //選択登録先
                    if (addUserSid == grp.getScdUsrSid()) {
                        schBiz.sendPlgSmail(
                                con__, cntCon__, smlBaseSch,
                                appRootPath, plconf, smailPluginUseFlg, url);
                        continue;
                    }
                    //同時登録
                    if (schMdl.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {
                        schBiz.sendAttendSmail(con__, cntCon__, schMdl, appRootPath,
                                plconf, smailPluginUseFlg, url, 0);
                    } else {
                        schBiz.sendPlgSmail(
                                con__, cntCon__, smlBaseSch,
                                appRootPath, plconf, smailPluginUseFlg, url);
                    }
                }
            }
        }

        __insertReserve(
                schMdl,
                null,
                svReserves,
                -1,
                appRootPath,
                plconf,
                smailPluginUseFlg);

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
     * @throws Exception SQL実行時例外
     */
    void __insertReserve(SchDataModel schMdl,
            SchDataModel oldMdl,
            String[] svReserves,
            int rsrSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg
            ) throws Exception {

        ArrayList<int []> sidDataList = new ArrayList<int []>();
        //施設予約を登録する場合
        if (svReserves != null && svReserves.length > 0) {
            RsvSisYrkModel yrkParam = __createRsyMdl(schMdl, oldMdl);
            yrkParam.setRsrRsid(rsrSid);
            yrkParam.setRsdSid(
                    Stream.of(svReserves)
                        .map(str -> Integer.parseInt(str))
                        .findAny().get());
            IRsvYoyakuRegister.Builder regRsvBld =
                    IRsvYoyakuRegister.simpleRegistBuilder(
                            con__, reqMdl__, cntCon__, appRootPath, yrkParam);
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
                    Stream.of(formParam__.getSchKfTarget())
                        .map(targetSid -> {
                            RsvDataPubModel rdpMdl = new RsvDataPubModel();
                            if (targetSid.startsWith("G")) {
                                rdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_GROUP);
                                rdpMdl.setRdpPsid(Integer.parseInt(targetSid.substring(1)));
                            } else {
                                rdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_USER);
                                rdpMdl.setRdpPsid(Integer.parseInt(targetSid));
                            }
                            return rdpMdl;
                        })
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
                    if (rsvCmnBiz.isApprSis(con__, sisetsuSid, sessionUsrSid)) {
                        //ショートメールで通知

                            RsvRegSmailModel regMdl = new RsvRegSmailModel();
                            regMdl.setCon(con__);
                            regMdl.setReqMdl(reqMdl__);
                            regMdl.setRsySid(yoyakuSid);
                            regMdl.setRsdSid(sisetsuSid);
                            regMdl.setCntCon(cntCon__);
                            regMdl.setUserSid(sessionUsrSid);
                            regMdl.setAppRootPath(appRootPath);
                            regMdl.setPluginConfig(plconf);

                            rsvCmnBiz.sendRegSmail(regMdl, __createReserveUrl(yoyakuSid));

                    }
                }
            }
        }
    }

    /**
     * <br>[機  能] 会社、アドレス帳マップの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid usrSid
     * @param acoSids 会社SId
     * @param abaSids 会社拠点SID
     * @param adrSids アドレスSID
     * @return 会社、アドレス帳マップ
     * @throws SQLException SQL実行時例外
     *
     */
    public Map<String, Sch040CompanyModel>makeCompanyMap(int userSid,
            String[] acoSids,
            String[] abaSids,
            String[] adrSids) throws SQLException {
        Map<String, Sch040CompanyModel> companyMap = new HashMap<String, Sch040CompanyModel>();

        Sch040CompanyModel noCompanyModel = new Sch040CompanyModel();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //会社登録無し
        String textCmpDataNone = gsMsg.getMessage("schedule.src.87");
        noCompanyModel.setCompanyName(textCmpDataNone);
        noCompanyModel.setCompanyAddress(null);
        noCompanyModel.setCompanySid(0);
        noCompanyModel.setCompanyBaseSid(0);
        companyMap.put("0:0", noCompanyModel);

        Sch040Biz sch040biz = new Sch040Biz(con__, reqMdl__, cntCon__);
        Sch040Dao sch040Dao = new Sch040Dao(con__);

        if (acoSids != null && abaSids != null) {

            for (int index = 0; index < acoSids.length; index++) {
                int acoSid = Integer.parseInt(acoSids[index]);
                int abaSid = Integer.parseInt(abaSids[index]);
                Sch040CompanyModel companyData =
                        sch040biz.createCompanyData(sch040Dao, acoSid, abaSid);
                if (companyData != null) {
                    String companyId = acoSid + ":" + abaSid;
                    companyMap.put(companyId, companyData);
                }
            }
        }


        //アドレス情報を取得
        List<Sch040AddressModel> addressList
                    = sch040Dao.getAddressList(con__, adrSids, userSid);
        List<String> addressSidList = new ArrayList<String>();
        if (addressList != null) {

            for (Sch040AddressModel adrData : addressList) {

                String companyId = adrData.getCompanySid() + ":" + adrData.getCompanyBaseSid();
                Sch040CompanyModel companyData = companyMap.get(companyId);
                if (companyData == null) {
                    companyData = sch040biz.createCompanyData(sch040Dao,
                                                    adrData.getCompanySid(),
                                                    adrData.getCompanyBaseSid());
                    if (companyData != null) {
                        companyMap.put(companyId, companyData);
                    }
                }
                if (companyData == null) {
                    companyId = "0:0";
                    companyData = companyMap.get(companyId);
                }
                addressSidList.add(String.valueOf(adrData.getAdrSid()));
                companyData.getAddressDataList().add(adrData);
                companyMap.put(companyId, companyData);
            }
        }
        return companyMap;
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

        Sch040Biz sch040Biz = new Sch040Biz(con__, reqMdl__, cntCon__);

        //会社情報Mappingを登録
        Map<String, Sch040CompanyModel> comMap =
                makeCompanyMap(sessionUserSid,
                        formParam__.getAcoSid(),
                        formParam__.getAbaSid(),
                        formParam__.getAdress());
        String[] acoSids = new String[comMap.size()];
        String[] abaSids = new String[comMap.size()];
        int index = 0;
        for (String key : comMap.keySet()) {
            Sch040CompanyModel company = comMap.get(key);
            acoSids[index] = String.valueOf(company.getCompanySid());
            abaSids[index] = String.valueOf(company.getCompanyBaseSid());
            index++;
        }


        List<SchCompanyModel> companyList = sch040Biz.createCompanyModel(scdSidList,
                                                            acoSids,
                                                            abaSids,
                                                            sessionUserSid, date);
        if (companyList != null) {
            SchCompanyDao companyDao = new SchCompanyDao(con__);
            for (SchCompanyModel companyModel : companyList) {
                companyDao.insert(companyModel);
            }
        }

        //アドレス帳情報Mapping、コンタクト履歴を登録する
        String[] addressId = formParam__.getAdress();
        List<SchAddressModel> addressList = sch040Biz.createAddressModel(scdSidList, addressId,
                                                            sessionUserSid, date);
        if (addressList != null) {
            SchAddressDao addressDao = new SchAddressDao(con__);
            boolean contactFlg = (NullDefault.getInt(formParam__.getContactFlg(), 0) == 1);

            String contactTitle = formParam__.getTitle();
            String[] startDate = new String[5];
            startDate[0] = String.valueOf(formParam__.getFrDate().getYear());
            startDate[1] = String.valueOf(formParam__.getFrDate().getMonth());
            startDate[2] = String.valueOf(formParam__.getFrDate().getIntDay());
            startDate[3] = String.valueOf(formParam__.getFrDate().getIntHour());
            startDate[4] = String.valueOf(formParam__.getFrDate().getIntMinute());
            String[] endDate = new String[5];
            endDate[0] = String.valueOf(formParam__.getToDate().getYear());
            endDate[1] = String.valueOf(formParam__.getToDate().getMonth());
            endDate[2] = String.valueOf(formParam__.getToDate().getIntDay());
            endDate[3] = String.valueOf(formParam__.getToDate().getIntHour());
            endDate[4] = String.valueOf(formParam__.getToDate().getIntMinute());


            int adcGrpSid = -1;
            Map<Integer, Integer> contactMap = new HashMap<Integer, Integer>();
            if (contactFlg && addressId != null) {
                //アドレス帳情報が複数選択されている場合はコンタクト履歴グループSIDを採番する
                if (addressId.length > 1) {
                    adcGrpSid = (int) cntCon__.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                            GSConst.SBNSID_SUB_CONTACT_GRP,
                                                            sessionUserSid);
                }

                //コンタクト履歴の登録
                for (String adrSid : addressId) {
                    Sch040ContactModel contactMdl
                            = sch040Biz.createContactModel(Integer.parseInt(adrSid), adcGrpSid,
                                                contactTitle, startDate, endDate,
                                                sessionUserSid, date);
                    AdrContactModel model = new AdrContactModel();
                    try {
                        BeanUtils.copyProperties(model, contactMdl);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    AdrContactDao adcDao = new AdrContactDao(con__);
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
     * <br>[機  能] スケジュールを更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param oldMdl 編集前データ
     * @throws Exception SQL実行時例外
     */
    public void updateSchedule(
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            ScheduleSearchModel oldMdl) throws Exception {


        //管理者設定を取得
        SchCommonBiz biz = new SchCommonBiz(con__, reqMdl__);
        SchAdmConfModel adminConf = biz.getAdmConfModel(con__);
        SchDataDao schDao = new SchDataDao(con__);
        int timeKbn = NullDefault.getInt(formParam__.getTimeKbn(), GSConstSchedule.TIME_EXIST);

        String scdSid = formParam__.getSchSid();
        SchDataModel scdMdl = new SchDataModel();
        UDate now = new UDate();
        UDate frDate = formParam__.getFrDate();
        UDate toDate = formParam__.getToDate();
        /** 選択ユーザSID */
        int selectUsrSid = getTargetSid(con__, formParam__.getUserKbn(),
                formParam__.getUsrSid(), formParam__.getTargetId(),
                reqMdl__.getSmodel().getUsrsid());
        /** 選択ユーザ区分 */
        int selectUsrKbn = NullDefault.getInt(formParam__.getUserKbn(),
                GSConstSchedule.USER_KBN_USER);
        scdMdl.setScdDaily(timeKbn);

        if (scdMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        }

        scdMdl.setScdSid(Integer.parseInt(scdSid));
        scdMdl.setScdFrDate(frDate);
        scdMdl.setScdToDate(toDate);
        scdMdl.setScdBgcolor(NullDefault.getInt(formParam__.getColorKbn(),
                GSConstSchedule.DF_BG_COLOR));
        scdMdl.setScdTitle(formParam__.getTitle());
        scdMdl.setScdValue(NullDefault.getString(formParam__.getNaiyo(), ""));
        scdMdl.setScdBiko(NullDefault.getString(formParam__.getBiko(), ""));
        scdMdl.setScdPublic(
                NullDefault.getInt(formParam__.getSchKf(), GSConstSchedule.DSP_PUBLIC));

        scdMdl.setScdAuid(userSid);
        scdMdl.setScdAdate(now);
        scdMdl.setScdEuid(userSid);
        scdMdl.setScdEdate(now);
        int attendKbn = NullDefault.getInt(formParam__.getAttendKbn(),
                GSConstSchedule.ATTEND_KBN_NO);
        scdMdl.setScdAttendKbn(attendKbn);
        if (attendKbn == GSConstSchedule.ATTEND_KBN_YES) {
            scdMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_YES);
            scdMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_YES);
        } else {
            scdMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
            scdMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
        }

        //編集区分
        scdMdl.setScdEdit(
                NullDefault.getInt(formParam__.getSchEf(), GSConstSchedule.EDIT_CONF_NONE));

        SchDataPubDao schPubDao = new SchDataPubDao(con__);

        //拡張登録SID
        int extSid = oldMdl.getSceSid();
        scdMdl.setSceSid(extSid);
        //スケジュール施設予約SID
        int resSid = oldMdl.getScdRsSid();
        scdMdl.setScdRsSid(resSid);
        String[] svReserves = null;
        if (timeKbn == GSConstSchedule.TIME_EXIST) {
            svReserves = formParam__.getReserves();
        }

        int scdResSid = GSConstSchedule.DF_SCHGP_ID;

        //施設拡張取得(スケジュール情報を削除する前に取得)
        RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con__);
        RsvSisRyrkModel ryrkMdl = null;
        if (NullDefault.getInt(formParam__.getBatchResRef(), 1) == 1
                || timeKbn == GSConstSchedule.TIME_NOT_EXIST) {
            ryrkMdl = ryrkDao.selectFromScdSid(Integer.parseInt(scdSid));
        }
        //リマインダー通知
        IPushServiceOperator psOpe = PushServiceOperator.getInstance(con__, reqMdl__.getDomain());
        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(
                selectUsrKbn,
                userSid,
                selectUsrSid);

        SchPriPushModel defConf = null;
        if (selectUsrKbn == GSConstSchedule.USER_KBN_USER) {
               defConf =  SchPriPushModel.getInstance(
                        biz.getSchPriConfModel(con__, selectUsrSid)
                        );
        } else {
            defConf =  SchPriPushModel.getInstance(
                        new SchDataModel()
                    );
        }
        SchRemindConfWriter.builder()
        .setDefConf(defConf)
        .setOldConf(SchPriPushModel.getInstance(oldMdl))
        .setRemindMode(remindMode)
        .setTargetGrp(
                NullDefault.getInt(
                        formParam__.getTargetGrp(),
                        (i) -> (i == GSConstSchedule.REMINDER_USE_YES
                        || i == GSConstSchedule.REMINDER_USE_NO),
                        GSConstSchedule.REMINDER_USE_YES)
                )
        .setReminder(
                NullDefault.getInt(
                    formParam__.getReminder(),
                    (i) -> (SchEnumReminderTime.valueOf(i) != null),
                    defConf.getSccReminder())
                )
        .setTimeKbn(NullDefault.getInt(
                formParam__.getTimeKbn(),
                GSConstSchedule.TIME_EXIST))
        .setPushUseable(
                psOpe.isUseable()
                )
        .build().write(scdMdl);

        if (NullDefault.getInt(formParam__.getBatchRef(), 1) == 0) {
            //同時登録反映無しの場合
            scdMdl.setScdGrpSid(GSConstSchedule.DF_SCHGP_ID);
            //施設予約へ反映する場合、新たに採番
            if (NullDefault.getInt(formParam__.getBatchResRef(), 1) == 1) {
                if (svReserves != null && svReserves.length > 0) {
                    //スケジュール施設予約SID（施設予約有りの場合）
                    scdResSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                            SaibanModel.SBNSID_SUB_SCH_RES, userSid);
                    scdMdl.setScdRsSid(scdResSid);
                    schDao.updateRsSid(resSid, scdResSid);
                }
            }
            //選択スケジュールを更新
            schDao.updateSchedule(scdMdl);

            //スケジュール_公開対象を更新(再登録)
            schPubDao.delete(Arrays.asList(oldMdl.getScdSid()));
            if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                __insertSchDataPub(con__, Arrays.asList(scdMdl.getScdSid()));
            }

            //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を更新
            __updateSchCompany(Integer.parseInt(scdSid), scdMdl.getScdUsrSid(),
                            scdMdl.getScdEdate(), scdMdl.getScdEuid());

            //URL取得
            String url = createScheduleUrlDefo(GSConstSchedule.CMD_EDIT,
                                               String.valueOf(scdMdl.getScdSid()),
                                               String.valueOf(scdMdl.getScdUsrSid()),
                                               scdMdl.getScdFrDate().getDateString(),
                                               String.valueOf(scdMdl.getScdUsrKbn())
                                               );
            biz.sendPlgSmail(
                    con__, cntCon__, scdMdl, appRootPath, plconf, smailPluginUseFlg, url);

            //編集前のデータで出欠確認を行っていた場合、リレーションで紐付いている
            //回答側のスケジュールの出欠確認データをリセットする
            if (oldMdl.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {
                //グループ
                if (oldMdl.getScdGrpSid() != GSConstSchedule.DF_SCHGP_ID) {
                    schDao.updateAttendReset(oldMdl.getScdGrpSid());
                }
            }
            //通知予定リストの登録
            SchPushListModel splMdl = new SchPushListModel();
            splMdl.setScdSid(Integer.parseInt(scdSid));
            splMdl.setUsrSid(scdMdl.getScdUsrSid());
            splMdl.setSplReminder(biz.getReminderDate(
                    frDate.cloneUDate(), scdMdl.getScdReminder()));
            splMdl.setSplReminderKbn(scdMdl.getScdReminder());

            if (Integer.parseInt(formParam__.getUserKbn()) != GSConstSchedule.USER_KBN_GROUP) {
                biz.updateUserPushData(splMdl);
            } else {
                biz.updateGroupPushData(
                        Integer.parseInt(scdSid), scdMdl.getScdUsrSid(), frDate.cloneUDate());
            }

        } else {
            //同時登録ユーザへ反映更新

            scdMdl.setScdUsrSid(selectUsrSid);
            scdMdl.setScdUsrKbn(Integer.parseInt(formParam__.getUserKbn()));

            ISchRegister reg =  __deleteInsertScheduleDate(
                    appRootPath, plconf, smailPluginUseFlg, adminConf,
                    scdMdl, oldMdl,
                    psOpe);
            scdResSid =
                    reg.getScdGrpsList().stream()
                        .findAny()
                        .map(SchDataGroupModel::getScdResSid)
                        .orElse(GSConstSchedule.DF_SCHGP_ID);

            scdMdl.setScdRsSid(scdResSid);
            //form値にsidを保存
            reg.getScdSidMap().entrySet().stream().findAny()
                .flatMap(entGrp ->
                        entGrp.getValue().entrySet().stream()
                            .filter(ent -> Objects.equals(
                                    entGrp.getKey().getScdUsrSid(),
                                    ent.getKey()))
                            .map(ent -> ent.getValue())
                            .findAny()
                        )
                .ifPresent(sid -> formParam__.setSchSid(String.valueOf(sid)));

        }

        int rsrSid = -1;
        //施設予約への更新判定 時間指定無しの場合は更新
        if (NullDefault.getInt(formParam__.getBatchResRef(), 1) == 1
                || timeKbn == GSConstSchedule.TIME_NOT_EXIST) {

            if (ryrkMdl != null) {
                rsrSid = ryrkMdl.getRsrRsid();
            }
            __insertReserve(scdMdl,
                    oldMdl,
                    svReserves,
                    rsrSid,
                    appRootPath,
                    plconf,
                    smailPluginUseFlg);

            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);

            if (resSid > -1) {
                //削除する施設予約SIDを取得する
                RsvSisYrkDao rsyDao = new RsvSisYrkDao(con__);
                ArrayList<Integer> rsySidList = rsyDao.getScheduleRserveSids(resSid);
                //施設予約区分別情報を削除
                if (rsySidList != null && rsySidList.size() > 0) {
                    RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con__);
                    kyrkDao.delete(rsySidList);

                    //施設予約公開対象を削除
                    RsvDataPubDao rsvPubDao = new RsvDataPubDao(con__);
                    rsvPubDao.deleteList(rsySidList);
                }

                //旧施設予約情報を削除
                yrkDao.deleteScdRsSid(resSid);
            }

            //ひも付いている施設予約情報が無くなった場合、予約拡張データを削除
            if (rsrSid > -1 && yrkDao.getYrkDataCnt(rsrSid) < 1) {
                //件数取得し0件の場合
                ryrkDao.delete(rsrSid);
                //施設予約拡張区分別情報削除
                RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con__);
                kryrkDao.delete(rsrSid);

                //施設予約拡張情報_公開対象を削除
                RsvExdataPubDao rsvExPubDao = new RsvExdataPubDao(con__);
                rsvExPubDao.delete(rsrSid);
            }
        }

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
     * @param oldMdl 旧スケジュールモデル
     * @param psOpe Push通知実行クラスインタフェース
     * @return 登録実行後のISchRegister
     * @throws SQLException
     * @throws TempFileException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    private ISchRegister __deleteInsertScheduleDate(
            String appRootPath,
            PluginConfig plconf, boolean smailPluginUseFlg,
            SchAdmConfModel admConf,
            SchDataModel scdMdl,
            SchDataModel oldMdl,
            IPushServiceOperator psOpe)
            throws SQLException, TempFileException,
            UnsupportedEncodingException, Exception {
        int userSid = reqMdl__.getSmodel().getUsrsid();
        String scdSid = formParam__.getSchSid();
        String[] svUsers = formParam__.getSameScheduledUser();
        String[] svReserves = null;
        if (NullDefault.getInt(formParam__.getTimeKbn(), GSConstSchedule.TIME_EXIST)
                == GSConstSchedule.TIME_EXIST) {
            svReserves = formParam__.getReserves();
        }
        Map<Integer, Integer> attendMap = null;
        Map<Integer, String> attendCommentMap = null;
        //出欠確認区分「確認する」の場合
        int sendAttendMailType = oldMdl.getScdAttendKbn();
        /** 旧スケジュールの通知設定マップ*/
        Map<Integer, SchPriPushModel> oldPriPushMap = new HashMap<>();

        SchCommonBiz schBiz = new SchCommonBiz(con__, reqMdl__);

        //同時登録スケジュールSIDリスト
        ScheduleSearchDao ssDao = new ScheduleSearchDao(con__);
        ArrayList<Integer> scds = ssDao.getScheduleUsrs(
                Integer.parseInt(scdSid),
                userSid,
                admConf.getSadCrange(),
                GSConstSchedule.SSP_AUTHFILTER_EDIT
                );
        //旧スケジュール通知設定
        ArrayList<Integer> oldScdSids = new ArrayList<>(Stream.concat(
                        Stream.of(Integer.parseInt(scdSid)),
                        scds.stream())
                        .collect(Collectors.toList()));
        if (scdMdl.getScdUsrKbn()
                == GSConstSchedule.USER_KBN_USER) {
            SchDataDao schDao = new SchDataDao(con__);
            if (sendAttendMailType == GSConstSchedule.ATTEND_KBN_YES) {
                //各ユーザ出欠確認の回答データを引き継ぐため、削除前のスケジュールデータより
                //ユーザSID：出欠パラメータを取得する
                attendMap = schDao.selectAttendData(Integer.valueOf(scdSid));
                attendCommentMap = schDao.selectAttendDataComment(Integer.valueOf(scdSid));
                //出席確認者は出席
                attendMap.put(scdMdl.getScdUsrSid(), GSConstSchedule.ATTEND_ANS_YES);
            }
            if (sendAttendMailType == GSConstSchedule.ATTEND_KBN_YES
                    && NullDefault.getInt(formParam__.getAttendMailResendKbn(), 0) == 0) {
                sendAttendMailType = -1;
            }
            oldPriPushMap.putAll(
                    schDao.getSchedules(oldScdSids).stream()
                        .collect(
                                Collectors.toMap(SchDataModel::getScdUsrSid,
                                        SchPriPushModel::getInstance))
                    );

        }

        //新スケジュールを登録
        ISchRegister.Builder regBld;
        regBld = ISchRegister.simpleRegistBuilder(con__, reqMdl__, cntCon__, scdMdl);

        //更新時引継ぐ情報を設定
        regBld.setSchExtSid(scdMdl.getSceSid());
        regBld.setOldPushMap(oldPriPushMap);
        regBld.setOldAttendMap(attendMap);
        regBld.setOldAttendCommentMap(attendCommentMap);

        //添付ファイルの登録
        SchBinDao binDao = new SchBinDao(con__);
        List<Long> binSidList =
                Stream.of(binDao.getBinSids(oldMdl.getScdSid()))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        regBld.setBinSidList(binSidList);

        //公開対象の登録
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            regBld.setPubList(
                Stream.of(formParam__.getSchKfTarget())
                .map(targetSid -> {
                    SchDataPubModel sdpMdl = new SchDataPubModel();
                    if (targetSid.startsWith("G")) {
                        sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid.substring(1)));
                    } else {
                        sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid));
                    }
                    return sdpMdl;
                })
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
        //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を登録
        regBld.setUseContact(NullDefault.getInt(formParam__.getContactFlg(), 0) == 1);
        Map<String, Sch040CompanyModel> comMap =
        makeCompanyMap(userSid,
                formParam__.getAcoSid(),
                formParam__.getAbaSid(),
                formParam__.getAdress());
        String[] acoSids = new String[comMap.size()];
        String[] abaSids = new String[comMap.size()];
        int index = 0;
        for (String key : comMap.keySet()) {
            Sch040CompanyModel company = comMap.get(key);
            acoSids[index] = String.valueOf(company.getCompanySid());
            abaSids[index] = String.valueOf(company.getCompanyBaseSid());
            index++;
        }
        regBld.setAdrSidArr(formParam__.getAdress());
        regBld.setAbaSidArr(abaSids);
        regBld.setAcoSidArr(acoSids);
        regBld.setUseRsv(svReserves != null && svReserves.length > 0);

        //スケジュール登録ロジッククラス設定完了
        ISchRegister reg = regBld.build();

        //スケジュール・関連情報登録実行
        reg.regist();

        //ショートメール通知
        if (smailPluginUseFlg) {
            for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp
                    : reg.getScdSidMap().entrySet()) {
                SchDataGroupModel grp = entGrp.getKey();
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    int addUserSid = entry.getKey();
                    SchDataModel smlBaseSch = reg.getSchModel(grp, addUserSid);

                    //URL取得
                    String url = createScheduleUrlDefo(GSConstSchedule.CMD_EDIT,
                                                       String.valueOf(smlBaseSch.getScdSid()),
                                                       String.valueOf(smlBaseSch.getScdUsrSid()),
                                                       smlBaseSch.getScdFrDate().getDateString(),
                                                       String.valueOf(smlBaseSch.getScdUsrKbn())
                                                       );
                    //選択登録先
                    if (addUserSid == grp.getScdUsrSid()) {
                        schBiz.sendPlgSmail(
                                con__, cntCon__, smlBaseSch,
                                appRootPath, plconf, smailPluginUseFlg, url);
                        continue;
                    }
                    //同時登録ユーザ
                    if (scdMdl.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {
                        if (sendAttendMailType >= 0) {
                            schBiz.sendAttendSmail(con__, cntCon__, smlBaseSch, appRootPath,
                                    plconf, smailPluginUseFlg, url, sendAttendMailType);
                        }
                    } else {
                        schBiz.sendPlgSmail(con__,
                                cntCon__,
                                smlBaseSch,
                                appRootPath,
                                plconf,
                                smailPluginUseFlg,
                                url);
                    }

                }
            }
        }

        //旧スケジュールを削除
        __deleteSchedule(
                oldScdSids,
                NullDefault.getInt(formParam__.getContactFlg(), 0)
                );


        return reg;
    }
    /**
     * <br>[機  能] 既存スケジュール情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param deleteScdSidList 削除対象リスト
     * @param contactEditFlg コンタクト履歴編集フラグ
     * @throws SQLException
     */
    private void __deleteSchedule(
            List<Integer> deleteScdSidList, int contactEditFlg) throws SQLException {
        SchBinDao binDao = new SchBinDao(con__);
        SchPushListDao splDao = new SchPushListDao(con__);
        SchDataPubDao sdpDao = new SchDataPubDao(con__);
        SchDataDao schDao = new SchDataDao(con__);
        schDao.delete(new ArrayList<Integer>(deleteScdSidList));
        splDao.delete(new ArrayList<Integer>(deleteScdSidList));

        binDao.deleteTempFile(deleteScdSidList);

        //編集元の公開対象を削除
        sdpDao.delete(deleteScdSidList);

        //変更前スケジュールの会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を削除
        deleteSchCompany(con__,
                deleteScdSidList,
                contactEditFlg);
    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSidList スケジュールSID
     * @param contactFlg コンタクト履歴変更有無
     * @throws SQLException SQL実行時例外
     */
    public void deleteSchCompany(Connection con, List<Integer> scdSidList, int contactFlg)
            throws SQLException {

        SchCompanyDao companyDao = new SchCompanyDao(con);
        companyDao.delete(scdSidList);

        SchDao dao = new SchDao(con);
        SchAddressDao addressDao = new SchAddressDao(con);
        for (Integer scdSid : scdSidList) {
            if (contactFlg == 1) {
                dao.deleteScheduleContact(con, scdSid);
            }
            addressDao.delete(scdSid);
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

        Sch040Biz sch040Biz = new Sch040Biz(con__, reqMdl__, cntCon__);
        List<Integer> scdSidList = new ArrayList<Integer>();
        scdSidList.add(scdSid);
        int contact = Integer.valueOf(formParam__.getContactFlg());
        sch040Biz.deleteSchCompany(con__, scdSidList, contact);

        Map<Integer, Integer> scdUserMap = new HashMap<Integer, Integer>();
        scdUserMap.put(scdSid, userSid);
        __insertSchCompany(scdSidList, scdUserMap, sessionUserSid, date);
    }
    /**
     * <br>[機  能] スケジュール一般登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd 処理モード
     * @param sch010SchSid スケジュールSID
     * @param usrSid ユーザーSID
     * @param frDateStr スケジュール日付文字列(yyyyMMdd)
     * @param userKbn ユーザー区分（0:ユーザ 1:グループ）
     * @return スケジュール一般登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createScheduleUrlDefo(String cmd,
                                          String sch010SchSid, String usrSid,
                                          String frDateStr,
                                          String userKbn)
    throws UnsupportedEncodingException {


        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl += "/" + GSConstSchedule.PLUGIN_ID_SCHEDULE;
            paramUrl += "/sch040.do";
            paramUrl += "?sch010SelectDate=" + frDateStr;
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&sch010SchSid=" + sch010SchSid;
            paramUrl += "&sch010SelectUsrSid=" + usrSid;
            paramUrl += "&sch010SelectUsrKbn=" + userKbn;
            paramUrl += "&sch010DspDate=" + frDateStr;
            paramUrl += "&dspMod=" + GSConstSchedule.DSP_MOD_WEEK;

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }


    }
    /**
     * <br>[機  能] スケジュールの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param actionString エラーメッセージ内アクション名称
     * @param oldMdl 編集前データ
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateExistData(
            ActionErrors errors,
            String actionString,
            ScheduleSearchModel oldMdl) throws SQLException {

        if (oldMdl == null) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //変更
            ActionMessage msg = new ActionMessage("error.none.edit.data",
                    textSchedule, actionString);
            StrutsUtil.addMessage(errors, msg, "detail");
        } else {
            //指定スケジュールの編集権限チェック
            SchCommonBiz schCmnBiz = new SchCommonBiz();
            if (!schCmnBiz.canRegistSchedule(con__, oldMdl, reqMdl__.getSmodel().getUsrsid())) {
                //変更
                ActionMessage msg = new ActionMessage("error.scd.auth");
                StrutsUtil.addMessage(errors, msg, "detail");
            }
        }

        return errors;
    }
    /**
     * <br>[機  能] スケジュールの対象ユーザチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param schSid スケジュールSID
     * @param sessionUsrSid セッションユーザSID
     * @param actionString エラーメッセージ内アクション名称
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateUserCheck(
            ActionErrors errors,
            int schSid,
            int sessionUsrSid,
            String actionString) throws SQLException {
        //セッション情報を取得
        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con__);

        Sch040Biz biz = new Sch040Biz(con__, reqMdl__);
        ScheduleSearchModel scdMdl = biz.getSchData(schSid, adminConf, con__);
        if (scdMdl == null) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //変更
            ActionMessage msg = new ActionMessage("error.none.edit.data",
                    textSchedule, actionString);
            StrutsUtil.addMessage(errors, msg, "detail");
        } else if (scdMdl.getScdUsrSid() != sessionUsrSid) {
            //対象スケジュールはセッションユーザ以外のデータ
            ActionMessage msg = new ActionMessage("error.scd.auth");
            StrutsUtil.addMessage(errors, msg, "detail");
        }

        return errors;
    }

    /**
     * <br>[機  能] 同時登録スケジュールの編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param schSid スケジュールSID
     * @param batchRef 同時編集フラグ 0:同時編集しない 1:同時編集する
     * @param mode モード 0：編集・登録 1:削除 2:出欠応答
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSchPowerCheck(
            ActionErrors errors,
            int schSid,
            int batchRef,
            int mode) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //変更
        String textChange = gsMsg.getMessage("cmn.change");
        //同時登録スケジュールの編集権限チェック
        if (schSid != -1) {

            //セッション情報を取得
            BaseUserModel usModel = reqMdl__.getSmodel();
            int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
            CommonBiz commonBiz = new CommonBiz();
            boolean isAdmin = commonBiz.isPluginAdmin(con__,
                    usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
            //管理者設定を取得
            SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
            SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con__);

            Sch040Biz sch040biz = new Sch040Biz(con__, reqMdl__);
            ScheduleSearchModel scdMdl = sch040biz.getSchData(schSid, adminConf, con__);
            if (scdMdl == null) {
                String textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                msg = new ActionMessage("error.edit.power.user",
                        textSimultaneousEdit,
                        textChange);
                StrutsUtil.addMessage(errors, msg, "adduser");
                return errors;
            } else {
                //指定スケジュールの編集権限チェック
                SchCommonBiz schCmnBiz = new SchCommonBiz();
                if (!schCmnBiz.canRegistSchedule(con__, scdMdl, reqMdl__.getSmodel().getUsrsid())) {
                    //変更
                    msg = new ActionMessage("error.scd.auth");
                    StrutsUtil.addMessage(errors, msg, "detail");
                    return errors;
                }
            }

            //出欠確認スケジュールは単体削除不可
            if (mode == MODE_DELETE && scdMdl.getScdAttendKbn() != GSConstSchedule.ATTEND_KBN_NO
                    && batchRef == GSConstSchedule.SAME_EDIT_OFF) {
                msg = new ActionMessage("error.cant.edit.single.attend.schedule");
                StrutsUtil.addMessage(errors, msg, "attend");
                return errors;
            }
            //出欠確認するかつ、出欠登録者区分が「１：登録者以外」かつ同時登録者に「０：登録者」が存在する場合、編集不可
            if (mode != MODE_ANSER && scdMdl.getScdAttendKbn() != GSConstSchedule.ATTEND_KBN_NO
                    && scdMdl.getScdAttendAuKbn() == GSConstSchedule.ATTEND_REGIST_USER_NO) {
                ArrayList<ScheduleSearchModel> schDataList = null;
                SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl__);
                schDataList = schCmnBiz.getSchDataList(scdMdl.getScdSid(), adminConf, con__);
                boolean flg = false;
                if (mode == 0) {
                    flg = true;
                }
                for (ScheduleSearchModel mdl : schDataList) {
                    if (mdl.getScdAttendAuKbn() != GSConstSchedule.ATTEND_REGIST_USER_NO) {
                        flg = true;
                        break;
                    }
                }
                if (flg) {

                    msg = new ActionMessage("error.cant.edit.attend.schedule");
                    StrutsUtil.addMessage(errors, msg, "attend");
                    return errors;
                }

            }
            //出欠応答時、出欠登録者区分が「０：登録者」の場合、編集不可
            if (mode == MODE_ANSER && scdMdl.getScdAttendKbn() != GSConstSchedule.ATTEND_KBN_NO
                    && scdMdl.getScdAttendAuKbn() != GSConstSchedule.ATTEND_REGIST_USER_NO) {
                msg = new ActionMessage("error.cant.anser.attend.request.schedule");
                StrutsUtil.addMessage(errors, msg, "attend");
                return errors;

            }

            //編集権限をチェックする
            int scdPublic = scdMdl.getScdPublic();

            boolean errorFlg = false;
            boolean douziErrorFlg = false;
            String textSimultaneousEdit = "";
            Sch010Biz biz = new Sch010Biz(reqMdl__);
            //公開範囲が「公開」「所属グループのみ」
            if (scdPublic == GSConstSchedule.DSP_PUBLIC
                    || scdPublic == GSConstSchedule.DSP_BELONG_GROUP) {

                if (!biz.isEditOk(scdMdl, sessionUsrSid, isAdmin, con__, false)) {
                    textSimultaneousEdit = gsMsg.getMessage("schedule.9");
                    errorFlg = true;
                } else if (batchRef != GSConstSchedule.SAME_EDIT_OFF
                        && !biz.isAllEditOk(scdMdl, adminConf,
                                reqMdl__, sessionUsrSid, isAdmin, con__)) {
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

                //元スケジュールに編集権限がある場合のみ同時登録チェック
                if (!errorFlg && batchRef != GSConstSchedule.SAME_EDIT_OFF
                        && !biz.isAllEditOk(scdMdl, adminConf,
                                reqMdl__, sessionUsrSid, isAdmin, con__)) {
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

                //元スケジュールに編集権限がある場合のみ同時登録チェック
                if (!errorFlg && batchRef != GSConstSchedule.SAME_EDIT_OFF
                        && !biz.isAllEditOk(scdMdl, adminConf,
                                reqMdl__, sessionUsrSid, isAdmin, con__)) {
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

                //元スケジュールに編集権限がある場合のみ同時登録チェック
                if (!errorFlg && batchRef != GSConstSchedule.SAME_EDIT_OFF
                        && !biz.isAllEditOk(scdMdl, adminConf,
                                reqMdl__, sessionUsrSid, isAdmin, con__)) {
                    //同時登録スケジュールに対する編集
                    douziErrorFlg = true;
                }
            }

            if (douziErrorFlg) {
                msg = new ActionMessage("error.schedule.edit.cant.user");
                StrutsUtil.addMessage(errors, msg, "adduser");
                return errors;
            } else if (errorFlg) {
                msg = new ActionMessage("error.edit.power.user",
                        textSimultaneousEdit,
                        textChange);
                StrutsUtil.addMessage(errors, msg, "adduser");
                return errors;
            }

        }
        return errors;
    }
    /**
     * <br>[機  能] 同時登録施設予約の編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param schSid スケジュールSID
     * @param batchResRef 同時編集フラグ
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateResPowerCheck(
            ActionErrors errors,
            int schSid,
            int batchResRef) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //アクセス権限チェック
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();


        //編集権限のない施設数を取得する。
        if (batchResRef == 1) {
            boolean rsvAdmin = cmnBiz.isPluginAdmin(
                    con__, usModel, GSConstSchedule.PLUGIN_ID_RESERVE);
            int count = getCanNotEditRsvCount(usModel.getUsrsid(), schSid, rsvAdmin);

            if (count > 0) {
                //施設予約アクセス権限なし
                msg = new ActionMessage("error.myself.auth");
                StrutsUtil.addMessage(errors, msg, "error.myself.auth");
                return errors;
            }

        }


        //変更
        String textChange = gsMsg.getMessage("cmn.change");
        //同時登録施設予約の編集権限チェック
        if (schSid != -1 && batchResRef == 1) {

            RelationBetweenScdAndRsvChkBiz rsvChkBiz
            = new RelationBetweenScdAndRsvChkBiz(reqMdl__, con__);
            int errorCd = rsvChkBiz.isRsvEdit(
                    schSid,
                    RelationBetweenScdAndRsvChkBiz.CHK_KBN_TANITU);
            log__.debug("施設予約の編集権限チェック:エラーコード==>" + errorCd);
            if (errorCd == RelationBetweenScdAndRsvChkBiz.ERR_CD_SCD_CANNOT_EDIT) {
                //施設予約に対する編集
                String textRsvEdit = gsMsg.getMessage("schedule.src.32");
                msg = new ActionMessage("error.edit.power.user", textRsvEdit, textChange);
                StrutsUtil.addMessage(errors, msg, "addres");
            }
        }
        return errors;
    }
    /**
     * <br>[機  能] アクセス権限のない施設数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid ユーザSID
     * @param scdSid スケジュールSID
     * @param rsvAdmin 施設予約管理者
     * @return count 施設数
     * @throws SQLException SQLExceptionm
     */
    public int getCanNotEditRsvCount(
            int sessionUsrSid,
            int scdSid,
            boolean rsvAdmin
            ) throws SQLException {
        int count = 0;

        if (rsvAdmin) {
            return count;
        }

        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con__);

        ArrayList<Integer> allRsdList = schRsvDao.getScheduleReserveData(scdSid);
        if (allRsdList == null || allRsdList.size() == 0) {
            return count;
        }

        //施設SIDリストを取得
        ArrayList<Integer> rsdList
            = schRsvDao.getCanEditScheduleReserveData(scdSid, sessionUsrSid);

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
     * <br>[機  能] 施設予約登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsvSid 施設SID
     * @return 施設予約登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
   private String __createReserveUrl(int rsvSid) throws UnsupportedEncodingException {
       UDate now = new UDate();
       String date = now.getDateString();

       AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
       try {

           String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
           paramUrl += "/" + GSConstReserve.PLUGIN_ID_RESERVE;
           paramUrl += "/rsv110.do";

           paramUrl += "?rsv110ProcMode=" + Integer.parseInt(GSConstReserve.PROC_MODE_EDIT);
           paramUrl += "&rsv110RsySid=" + rsvSid;
           paramUrl += "&rsvDspFrom=" + date;

           String reserveUrl = urlBiz.getAccessUrl(reqMdl__, paramUrl);
           return reserveUrl;
       } catch (URISyntaxException e) {
           return null;
       }
   }

   /**
    * <br>[機  能]オペログ用日付変換メソッド
    * <br>[解  説]
    * <br>[備  考]
    * @param date 日付
    * @return 日付
    */
   private String __getScheduleDate(UDate date) {

       GsMessage gsMsg = new GsMessage(reqMdl__);
       String year = String.valueOf(date.getYear());
       String month = String.valueOf(date.getMonth());
       String day = String.valueOf(date.getIntDay());
       String hour = String.valueOf(date.getIntHour());
       String min = String.valueOf(date.getIntMinute());
       String retDate = gsMsg.getMessage("cmn.view.date", new String[] {
               year,
               month,
               day,
               hour,
               min});
       return retDate;
   }

   /**
    * <br>[機  能]オペログ変更前作成
    * <br>[解  説]
    * <br>[備  考]
    * @param scdSid スケジュールＳＩＤ
    * @param deleteFlg 削除時使用
    * @param douziUser 同時登録ユーザ
     * @param oldMdl 編集前データ
    * @return オペレーションログ
    * @throws SQLException SQLException
    */
   public String opLogBefore(int scdSid, boolean deleteFlg, int douziUser,
           ScheduleSearchModel oldMdl) throws SQLException {

       //同時登録ユーザ取得
       ArrayList<SchDataModel> sameInputSchedules = new ArrayList<SchDataModel>();
       if (!deleteFlg || (deleteFlg && douziUser == 1)) {
           ScheduleSearchDao schSearchDao = new ScheduleSearchDao(con__);
           ArrayList<Integer> removeUsrsList = new ArrayList<Integer>();
           removeUsrsList.add(reqMdl__.getSmodel().getUsrsid());
           sameInputSchedules = schSearchDao.getSameScheduleList(oldMdl.getScdGrpSid(),
                   removeUsrsList);
       }

       //施設情報取得
       ApiSchSearchBiz searchBiz = new ApiSchSearchBiz(con__, reqMdl__);
       ArrayList<RsvSisDataModel> selectResList = new ArrayList<RsvSisDataModel>();
       selectResList =
               searchBiz.getSelectResList(con__, scdSid, reqMdl__.getSmodel().getUsrsid(), true);

       ApiSchEditParamModel paramMdl = new ApiSchEditParamModel();
       ArrayList<String> userName = new ArrayList<String>();
       if (sameInputSchedules != null && sameInputSchedules.size() > 0) {
           for (SchDataModel sMdl : sameInputSchedules) {
               userName.add(String.valueOf(sMdl.getScdUsrSid()));
           }
           paramMdl.setSameScheduledUser(userName.toArray(new String[userName.size()]));
       }
       paramMdl.setTitle(oldMdl.getScdTitle());
       paramMdl.setNaiyo(oldMdl.getScdValue());
       paramMdl.setFrDate(oldMdl.getScdFrDate());
       paramMdl.setToDate(oldMdl.getScdToDate());
       ArrayList<String> sisetuName = new ArrayList<String>();
       if (selectResList != null && selectResList.size() > 0) {
           for (RsvSisDataModel sisetu : selectResList) {
               sisetuName.add(String.valueOf(sisetu.getRsdSid()));
           }
           paramMdl.setReserves(sisetuName.toArray(new String[sisetuName.size()]));
       }
       int beforeFlg = 1;
       int editFlg = -1;
       if (deleteFlg) {
           beforeFlg = 0;
       }
       return opLogCreate(paramMdl, beforeFlg, editFlg);
   }

   /**
    * <br>[機  能]オペログ作成
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param beforeFlg 変更前・変更後フラグ
    * @param editFlg 同時修正フラグ
    * @return オペレーションログ
    * @throws SQLException SQLException
    */
   public String opLogCreate(ApiSchEditParamModel paramMdl,
           int beforeFlg, int editFlg) throws SQLException {

       GsMessage gsMsg = new GsMessage(reqMdl__);
       StringBuilder sbValue = new StringBuilder();

       //同時登録ユーザ作成
       String userName = reqMdl__.getSmodel().getUsisei() + " " + reqMdl__.getSmodel().getUsimei();
       if (paramMdl.getSameScheduledUser() != null && paramMdl.getSameScheduledUser().length > 0) {
           UserBiz userBiz = new UserBiz();
           ArrayList <CmnUsrmInfModel> selectUsrList = null;
           selectUsrList =
                   (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con__,
                           paramMdl.getSameScheduledUser());
           if (selectUsrList != null) {
               for (int idx = 0; idx < selectUsrList.size(); idx++) {
                   userName += ", " + selectUsrList.get(idx).getUsiSei()
                           + " " + selectUsrList.get(idx).getUsiMei();
               }
           }
       }

       //施設作成
       String sisetu = "";
       if (paramMdl.getReserves() != null && paramMdl.getReserves().length > 0) {
           ArrayList<RsvSisDataModel> selectResList = null;
           RsvSisDataDao dataDao = new RsvSisDataDao(con__);
           ArrayList <Integer> resList = new ArrayList<Integer>();

           for (int i = 0; i < paramMdl.getReserves().length; i++) {
               resList.add(Integer.parseInt(paramMdl.getReserves()[i]));
           }
           selectResList = dataDao.selectGrpSisetuList(resList);
           StringBuilder sb = new StringBuilder();
           for (int idx = 0; idx < selectResList.size(); idx++) {
               if (idx != 0) {
                   sb.append(", ");
               }
               String sisetuName = selectResList.get(idx).getRsdName();
               sb.append(sisetuName);
           }
           sisetu = sb.toString();
       }


       if (beforeFlg == 1) {
           sbValue.append(gsMsg.getMessage("schedule.sch040.1"));
           sbValue.append("\n");
       } else if (beforeFlg == 2) {
           sbValue.append("\n");
           sbValue.append(gsMsg.getMessage("schedule.sch040.2"));
           sbValue.append("\n");
       }
       sbValue.append("[");
       sbValue.append(gsMsg.getMessage("sml.155"));
       sbValue.append("]");
       sbValue.append(userName);
       sbValue.append("\n");
       sbValue.append("[");
       sbValue.append(gsMsg.getMessage("schedule.sch100.11"));
       sbValue.append("]");
       sbValue.append(__getScheduleDate(paramMdl.getFrDate()));
       sbValue.append("\n");
       sbValue.append("[");
       sbValue.append(gsMsg.getMessage("schedule.sch100.16"));
       sbValue.append("]");
       sbValue.append(__getScheduleDate(paramMdl.getToDate()));
       sbValue.append("\n");
       sbValue.append("[");
       sbValue.append(gsMsg.getMessage("cmn.title"));
       sbValue.append("]");
       sbValue.append(paramMdl.getTitle());
       sbValue.append("\n");
       sbValue.append("[");
       sbValue.append(gsMsg.getMessage("cmn.content"));
       sbValue.append("]");
       sbValue.append(paramMdl.getNaiyo());
       if (editFlg == 0 || editFlg == 1) {
           sbValue.append("\n");
           sbValue.append("[");
           sbValue.append(gsMsg.getMessage("schedule.32"));
           sbValue.append("]");
           if (editFlg == 0) {
               sbValue.append(gsMsg.getMessage("schedule.33"));
           } else {
               sbValue.append(gsMsg.getMessage("schedule.34"));
           }
       }
       if (!sisetu.equals("")) {
           sbValue.append("\n");
           sbValue.append("[");
           sbValue.append(gsMsg.getMessage("cmn.facility.name"));
           sbValue.append("]");
           sbValue.append(sisetu);
       } else {
           sbValue.append("");
       }
       return sbValue.toString();
   }

   /**
    * <br>[機  能] 登録対象者SIDを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param userKbn ユーザ区分
    * @param usrSid 登録対象者SID
    * @param targetId 登録対象者ID
    * @param sessionUsrSid セッションユーザSID
    * @return 登録対象者SID
    * @throws SQLException
    */
   public int getTargetSid(Connection con, String userKbn, String usrSid, String targetId,
                           int sessionUsrSid)
   throws SQLException {

       boolean grpUsrFlg = (NullDefault.getInt(userKbn, 0) == GSConstSchedule.USER_KBN_GROUP);

       CmnGroupmDao grpDao = new CmnGroupmDao(con);
       CmnUsrmDao usrDao = new CmnUsrmDao(con);

       int targetSid = -1;
       if (StringUtil.isNullZeroString(targetId)) {
           if (grpUsrFlg) {
               targetSid = NullDefault.getInt(usrSid, -1);
               if (targetSid != -1) {
                   //グループIDの存在チェック
                   CmnGroupmModel grpMdl = grpDao.select(targetSid);
                   if (grpMdl != null && grpMdl.getGrpJkbn() == CmnGroupmDao.GRP_JKBN_LIVING) {
                       targetSid = grpMdl.getGrpSid();
                   } else {
                       targetSid = -1;
                   }
               }
           } else {
               //ユーザ区分 = ユーザ、かつ登録者SID未設定の場合、セッションユーザを登録対象とする
               targetSid = NullDefault.getInt(usrSid, sessionUsrSid);
               if (targetSid != sessionUsrSid) {
                   //ユーザSIDの存在チェック
                   CmnUsrmModel usrMdl = usrDao.select(targetSid);
                   if (usrMdl != null && usrMdl.getUsrJkbn() == GSConstUser.USER_JTKBN_ACTIVE
                   && usrMdl.getUsrSid() > GSConstUser.USER_RESERV_SID) {
                       targetSid = usrMdl.getUsrSid();
                   } else {
                       targetSid = -1;
                   }
               }

           }

       } else {

           if (grpUsrFlg) {
               //グループIDが存在する場合、グループSIDを取得
               CmnGroupmModel grpMdl = grpDao.getGroupInf(targetId);
               if (grpMdl != null) {
                   targetSid = grpMdl.getGrpSid();
               }
           } else {
               //ユーザIDが存在する場合、ユーザSIDを取得
               CmnUsrmModel usrMdl = usrDao.getUserSid(targetId);
               if (usrMdl != null && usrMdl.getUsrSid() > GSConstUser.USER_RESERV_SID) {
                   targetSid = usrMdl.getUsrSid();
               }
           }
       }

       return targetSid;
   }


    /**
     * <br>[機  能] スケジュール_公開対象を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSidList スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    private void __insertSchDataPub(Connection con, List<Integer> scdSidList)
    throws SQLException {

        String[] pubList = formParam__.getSchKfTarget();
        if (pubList == null || pubList.length <= 0) {
            return;
        }

        SchDataPubDao schPubDao = new SchDataPubDao(con);
        SchDataPubModel schPubMdl = new SchDataPubModel();

        for (int scdSid : scdSidList) {
            schPubMdl.setScdSid(scdSid);

            for (String publicUser : pubList) {
                if (publicUser.startsWith("G")) {
                    schPubMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                    schPubMdl.setSdpPsid(Integer.parseInt(publicUser.substring(1)));
                } else {
                    schPubMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                    schPubMdl.setSdpPsid(Integer.parseInt(publicUser));
                }

                schPubDao.insert(schPubMdl);
            }
        }
    }

}
