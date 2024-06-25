package jp.groupsession.v2.rsv.rsv111kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.ReserveSmlModel;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvScdOperationModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.other.RsvSchDataPubModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.rsv.rsv110.Rsv110Biz;
import jp.groupsession.v2.rsv.rsv110.Rsv110SisetuModel;
import jp.groupsession.v2.rsv.rsv111.Rsv111Biz;
import jp.groupsession.v2.rsv.rsv111.Rsv111ParamModel;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchExdataModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 施設予約 施設予約拡張登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv111knBiz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv111knBiz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;
    /**削除ログ*/
    StringBuilder delLog__ = new StringBuilder();
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv111knBiz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Rsv111knParamModel paramMdl) throws SQLException {

        String procMode = paramMdl.getRsv110ProcMode();
        int rsdSid = -1;

        //新規モード
        if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)) {

            log__.debug("新規モード");

            //新規登録者名をセット
            BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
            paramMdl.setRsv110Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

            rsdSid = paramMdl.getRsv110RsdSid();

        //編集モード or 複写して登録モード
        } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)
                || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {

            log__.debug("編集モード or 複写して登録モード");

            //予約情報取得
            Rsv110SisetuModel yrkAddMdl = __getYoyakuAddData(paramMdl);
            Rsv110SisetuModel yrkEditMdl = __getYoyakuEditData(paramMdl);
            if (yrkEditMdl != null) {

                //施設予約拡張SIDを取得
                int rsrRsid = paramMdl.getRsv111RsrRsid();

                //複写して登録or施設予約拡張SIDが-1のとき
                if (procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD) || rsrRsid == -1) {
                    //新規登録者名をセット
                    BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
                    paramMdl.setRsv110Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

                //編集
                } else {
                    //新規登録者
                    paramMdl.setRsv110AuId(yrkAddMdl.getRsyAuid());
                    paramMdl.setRsv110Torokusya(
                            NullDefault.getString(yrkAddMdl.getUsiSei(), "")
                            + "  "
                            + NullDefault.getString(yrkAddMdl.getUsiMei(), ""));
                    paramMdl.setRsv110AddUsrJKbn(yrkAddMdl.getUsrJkbn());
                    paramMdl.setRsv110AddUsrUkoFlg(yrkAddMdl.getUsrUkoFlg());
                    //最終更新者
                    paramMdl.setRsv110EuId(yrkAddMdl.getRsyEuid());
                    paramMdl.setRsv110Koshinsya(
                            NullDefault.getString(yrkEditMdl.getUsiSei(), "")
                            + "  "
                            + NullDefault.getString(yrkEditMdl.getUsiMei(), ""));
                    paramMdl.setRsv110EditUsrJKbn(yrkEditMdl.getUsrJkbn());
                    paramMdl.setRsv110EditUsrUkoFlg(yrkEditMdl.getUsrUkoFlg());
                    //関連するスケジュールデータ存在フラグを設定
                    __existSchData(paramMdl);
                }

                rsdSid = yrkEditMdl.getRsdSid();
            }

            //登録済データの日付リストを設定
            __setOldKurikaeshiDataList(paramMdl);
        }

        //新規に登録する日付リストを設定
        __setNewKurikaeshiDataList(paramMdl);

        //施設グループ情報を取得
        Rsv070Model grpMdl = __getGroupData(rsdSid);
        if (grpMdl != null) {
            int rskSid = grpMdl.getRskSid();

            //施設区分毎に入力可能な項目を設定
            __setSisetuHeader(paramMdl, rskSid);

            //施設グループ情報セット
            __setGroupData(paramMdl, grpMdl);
        }

        //期間開始
        paramMdl.setYoyakuFrString(__convertUdateToYmd(paramMdl, 1));
        //期間終了
        paramMdl.setYoyakuToString(__convertUdateToYmd(paramMdl, 2));
        //時間開始
        paramMdl.setYoyakuTimeFrString(__convertUdateToHm(paramMdl, 1));
        //時間終了
        paramMdl.setYoyakuTimeToString(__convertUdateToHm(paramMdl, 2));
        //内容
        paramMdl.setRsv111knRsrBiko(StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(paramMdl.getRsv111RsrBiko(), "")));

        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setRsv111knPubUsrGrpList(
                cmnBiz.getUserLabelList(con_, paramMdl.getRsv111PubUsrGrpSid()));

        //スケジュールを登録するユーザがいる場合、登録するユーザの名称をセット
        Rsv110Biz biz110 = new Rsv110Biz(reqMdl_, con_);
        biz110.setUserName(paramMdl, paramMdl.getRsv111SchKbn(),
                        paramMdl.getRsv111SvUsers(), paramMdl.getRsv111SchGroupSid());
        biz110 = null;

        if (paramMdl.getRsv110RsySid() > 0) {
            paramMdl.setRsvDataFlg(true);
        } else {
            paramMdl.setRsvDataFlg(false);
        }

    }

    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @param ctrl 採番用コネクション
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @return sidDataList 予約SID、施設SIDリスト
     * @throws SQLException SQL実行時例外
     * @throws Exception スケジュール 添付ファイル情報の削除に失敗
     */
    public List<int []> updateYoyakuData(
            Rsv111knParamModel paramMdl,
            MlCountMtController ctrl,
            int userSid,
            String appRootPath,
            String tempDir)
        throws SQLException, Exception {


        Rsv111Biz biz = new Rsv111Biz(reqMdl_, con_);
        RsvScdOperationDao scdDao = new RsvScdOperationDao(con_);
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con_);
        RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
        RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con_);
        RsvDataPubDao rdpDao = new RsvDataPubDao(con_);
        RsvExdataPubDao repDao = new RsvExdataPubDao(con_);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con_, reqMdl_);

        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int sessionUsrSid = usrMdl.getUsrsid();
        UDate now = new UDate();
        List<int []> sidDataList = new ArrayList<int []>();


        ArrayList<Integer> scdRsSidArray = new ArrayList<Integer>();
        ArrayList<Integer> usrSidArray = new ArrayList<Integer>();
        ArrayList<Integer> rsdSidArray = new ArrayList<Integer>();
        int scdUsrKbn = GSConstSchedule.USER_KBN_USER;

        HashMap<String, String> dayMap = biz.getInsertDateList(paramMdl);
        ArrayList<String> dayList = new ArrayList<String>(dayMap.values());

        RsvSisRyrkModel oldExdataMdl = ryrkDao.select(paramMdl.getRsv111RsrRsid());

        final List<Long> scdBinSids = new ArrayList<>();
        //スケジュール拡張SID取得
        int sceSid = scdDao.selectSceSid(paramMdl.getRsv110ScdRsSid());
        Rsv110SisetuModel yrkRet = null;
        //予約情報取得
        yrkRet = __getYoyakuEditData(paramMdl);

        String procMode = paramMdl.getRsv110ProcMode();
        boolean schInsertFlg = false;
        if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)
                || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)) {

             //スケジュールが選択されている
             int schKbn = paramMdl.getRsv111SchKbn();
             if ((schKbn == GSConstReserve.RSV_SCHKBN_GROUP
                     && NullDefault.getInt(paramMdl.getRsv111SchGroupSid(), -1) >= 0)
             || (schKbn == GSConstReserve.RSV_SCHKBN_USER
                     && paramMdl.getRsv111SvUsers() != null
                     && paramMdl.getRsv111SvUsers().length > 0)) {
                 schInsertFlg = true;
             }
         //編集モード
        } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
             //「スケジュールへ反映」が選択されている かつ
             //スケジュールと関連付いている または スケジュールが入力されている
             if (paramMdl.getRsv111ScdReflection() == GSConstReserve.SCD_REFLECTION_OK
             && ((paramMdl.getRsv110ScdRsSid() > 0 && paramMdl.isRsv111ExistSchDateFlg())
                 || (__checkInputSchedule(paramMdl)))) {
                 //施設情報とスケジュール更新
                 schInsertFlg = true;
             }
        }

        if (sceSid > 0) {
            //拡張登録された全ての施設のスケジュールリレーションSIDを取得
            scdRsSidArray = scdDao.selectKakutyoAllScdRsSid(sceSid);
            //登録対象ユーザ取得
            usrSidArray = scdDao.selectKakutyoAllUsrSid(sceSid);
            //登録対象施設取得
            rsdSidArray = yrkDao.getKakutyoAllRsdSid(scdRsSidArray);
            //拡張添付ファイル情報を取得
            scdBinSids.addAll(Stream.of(scdDao.getBinSids(sceSid))
                            .map(str -> Long.parseLong(str))
                            .collect(Collectors.toList())
                            );
                        ;
            //スケジュール ユーザ区分
            scdUsrKbn = scdDao.selectKakutyoUsrKbn(sceSid);
        } else if (yrkRet != null) {
            //施設のスケジュールリレーションSIDを取得
            scdRsSidArray.add(yrkRet.getScdRsSid());
            //登録対象ユーザ取得
            usrSidArray = scdDao.selectScdUsrSid(yrkRet.getScdRsSid());
            //登録対象施設取得
            rsdSidArray.add(yrkRet.getRsdSid());

            //スケジュール ユーザ区分
            scdUsrKbn = scdDao.getSchUsrKbn(yrkRet.getScdRsSid());
        }

        RsvSisYrkModel baseYrk = __getNewYrkBaseModel(paramMdl, sessionUsrSid, now, oldExdataMdl);
        //編集時 編集前の施設SIDを登録対象とする
        //編集時はパラメータの施設SID指定が -1になることがあるため
        if (rsdSidArray.size() > 0) {
            rsdSidArray.stream().findAny().ifPresent(sid -> baseYrk.setRsdSid(sid));
            rsdSidArray = new ArrayList<>(rsdSidArray.stream()
                    .filter(sid -> sid != baseYrk.getRsdSid())
                    .collect(Collectors.toList()));

        }


        RsvSisRyrkModel updRyrk =
                __getRyrkBaseModel(paramMdl, sessionUsrSid, now, oldExdataMdl);

        IRsvYoyakuRegister.Builder regBld = IRsvYoyakuRegister.kurikaesiRegistBuilder(
                con_, reqMdl_, ctrl, appRootPath, baseYrk,
                dayList.stream()
                    .map(str -> UDate.getInstanceStr(str))
                    .collect(Collectors.toList()),
                updRyrk
                );
        regBld.setRsdSids(new HashSet<>(rsdSidArray));

        //施設予約区分別情報登録
        if (_isRskKbnRegCheck(paramMdl.getRsv110SisetuKbn())) {
            RsvSisKyrkModel kyrkMdl =
                    __getKyrModel(
                            paramMdl, -1, sessionUsrSid, now, appRootPath);
            regBld.setKyrkMap(
                    Stream.concat(
                            Stream.of(baseYrk.getRsdSid()),
                            rsdSidArray.stream())
                        .collect(Collectors.toMap(
                                sid -> sid,
                                sid -> kyrkMdl,
                                (key1, key2) -> kyrkMdl))
                        );
        }

        //公開区分が対象ユーザ・グループのみ公開の場合
        if (paramMdl.getRsv111RsrPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
            //削除済みユーザは設定出来ないのでリストから除外する。
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con_);
            UserBiz userBiz = new UserBiz();

            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            List<String> usrSids = new ArrayList<String>();
            for (String target : paramMdl.getRsv111PubUsrGrpSid()) {
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
                glist = gdao.selectGroupNmListOrderbyClass(grpSids);
            }
            //ユーザ存在チェック
            if (!usrSids.isEmpty()) {
                ulist = userBiz.getBaseUserList(con_,
                                                usrSids.toArray(new String[usrSids.size()]));
            }
            String[] checkPubUsrGrpSid = new String[glist.size() + ulist.size()];
            int i = 0;
            for (GroupModel gMdl : glist) {
                checkPubUsrGrpSid[i] = "G" + gMdl.getGroupSid();
                i++;
            }
            for (BaseUserModel uMdl : ulist) {
                checkPubUsrGrpSid[i] = String.valueOf(uMdl.getUsrsid());
                i++;
            }
            paramMdl.setRsv111PubUsrGrpSid(checkPubUsrGrpSid);
            regBld.setPubList(
                    Stream.of(paramMdl.getRsv111PubUsrGrpSid())
                    .map(targetSid -> {
                        RsvDataPubModel rdpMdl = new RsvDataPubModel();
                        if (targetSid.startsWith("G")) {
                            rdpMdl.setRdpType(GSConst.TYPE_GROUP);
                            rdpMdl.setRdpPsid(Integer.parseInt(targetSid.substring(1)));
                        } else {
                            rdpMdl.setRdpType(GSConst.TYPE_USER);
                            rdpMdl.setRdpPsid(Integer.parseInt(targetSid));
                        }
                        return rdpMdl;
                    })
                    .collect(Collectors.toList())
                );
        }


        regBld.setUseSch(schInsertFlg);

        //施設予約を登録
        IRsvYoyakuRegister regRsv = regBld.build();
        regRsv.regist();
        sidDataList =
                regRsv.getRsySidMap().values().stream()
                        .flatMap(map -> map.entrySet().stream())
                        .map(ent -> new int[] {ent.getValue(), ent.getKey()})
                        .collect(Collectors.toList());
        if (schInsertFlg) {
            String[] users = paramMdl.getRsv111SvUsers();
            int rsvSchGrpSid = NullDefault.getInt(paramMdl.getRsv111SchGroupSid(), -1);

            //既存データの登録者、登録日時取得
            RsvScdOperationModel oldMdl = scdDao.selectOldScdData(sceSid);
            if (oldMdl == null) {
                //ベースデータセット
                oldMdl = new RsvScdOperationModel();
                oldMdl.setSceBgcolor(GSConstSchedule.DF_BG_COLOR);
                oldMdl.setSceBiko("");
                oldMdl.setScePublic(GSConstSchedule.DSP_PUBLIC);
                oldMdl.setSceAuid(baseYrk.getRsyAuid());
                oldMdl.setSceAdate(baseYrk.getRsyAdate());
                oldMdl.setSceEdit(GSConstSchedule.EDIT_CONF_OWN);
            }

            SchDataModel schMdl = new SchDataModel();
            if (paramMdl.getRsv111SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                schMdl.setScdUsrSid(rsvSchGrpSid);
                schMdl.setScdUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                schMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_YES);
            } else {
                int mainTargetSid = Stream.of(users)
                            .findFirst()
                            .map(str -> Integer.parseInt(str))
                            .orElse(-1);
                schMdl.setScdUsrSid(mainTargetSid);
                schMdl.setScdUsrKbn(GSConstSchedule.USER_KBN_USER);
            }

            //登録モデルのベース作成
            schMdl.setScdFrDate(baseYrk.getRsyFrDate());
            schMdl.setScdToDate(baseYrk.getRsyToDate());
            schMdl.setScdDaily(0);
            schMdl.setScdBgcolor(oldMdl.getSceBgcolor());
            schMdl.setScdTitle(NullDefault.getString(paramMdl.getRsv111RsrMok(), ""));
            schMdl.setScdValue(NullDefault.getString(paramMdl.getRsv111RsrBiko(), ""));
            schMdl.setScdBiko(NullDefault.getString(oldMdl.getSceBiko(), ""));
            schMdl.setScdPublic(RsvScheduleBiz.getScdPublicKbn(
                paramMdl.getRsv111RsrPublic(), paramMdl.getRsv111SchKbn()));
            schMdl.setScdAuid(oldMdl.getSceAuid());
            schMdl.setScdAdate(oldMdl.getSceAdate());
            schMdl.setScdEuid(sessionUsrSid);
            schMdl.setScdEdate(now);
            schMdl.setScdEdit(RsvScheduleBiz.getScdEditKbn(paramMdl.getRsv111RsrEdit()));
            schMdl.setScdPublic(RsvScheduleBiz.getScdPublicKbn(
                    paramMdl.getRsv111RsrPublic(), paramMdl.getRsv111SchKbn()));
            schCmnBiz.getUserPriConf(new String[] {String.valueOf(sessionUsrSid)})
                .values().stream()
                .findAny()
                .ifPresent(push -> {
                    schMdl.setScdReminder(push.getSccReminder());
                });
            schMdl.setScdGrpSid(GSConstSchedule.DF_SCHGP_ID);
            schMdl.setScdRsSid(GSConstSchedule.DF_SCHGP_ID);
            ISchRegister.Builder regSchBld;
            regSchBld = ISchRegister.kurikaesiRegistBuilder(con_, reqMdl_, ctrl, schMdl,
                    dayList.stream()
                        .map(str -> UDate.getInstanceStr(str))
                        .collect(Collectors.toList()),
                        __createSchExData(paramMdl, oldMdl, sessionUsrSid, now));

            regSchBld.setSchResSidMap(regRsv.getRsyGrpsList().stream()
                    .collect(Collectors.toMap(
                            grp -> grp.getTargetDate().getDateString("/"),
                            grp -> grp.getScdRsSid())));

            //添付ファイルの登録
            regSchBld.setBinSidList(scdBinSids);

            //公開対象の登録
            if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                regSchBld.setPubList(
                    Stream.of(paramMdl.getRsv111PubUsrGrpSid())
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
            if (paramMdl.getRsv111SchKbn() == GSConstReserve.RSV_SCHKBN_USER && users != null) {
                regSchBld.setUsers(
                        Stream.of(users)
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet())
                        );
            }
            regSchBld.setUseRsv(true);

            //スケジュール登録ロジッククラス設定完了
            ISchRegister reg = regSchBld.build();

            //スケジュール・関連情報登録実行
            reg.regist();
        }
        if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            //既存施設予約削除
            if (yrkRet.getRsrRsid() > 0) {
                ArrayList<Integer> rsySids = yrkDao.getYrkDataSidList(yrkRet.getRsrRsid());
                //施設情報削除
                yrkDao.deleteRyrkData(yrkRet.getRsrRsid());
                //施設予約拡張情報削除
                ryrkDao.delete(yrkRet.getRsrRsid());
                //施設予約区分別情報削除
                kyrkDao.delete(rsySids);
                //施設予約拡張区分別情報削除
                kryrkDao.delete(yrkRet.getRsrRsid());
                //施設予約公開対象削除
                rdpDao.delete(rsySids);
                //施設予約拡張公開対象削除
                repDao.delete(yrkRet.getRsrRsid());

            } else if (paramMdl.getRsv110RsySid() > 0) {
                RsvSisYrkModel param = new RsvSisYrkModel();
                param.setRsySid(paramMdl.getRsv110RsySid());
                yrkDao.delete(param);
                kyrkDao.delete(paramMdl.getRsv110RsySid());
                rdpDao.delete(paramMdl.getRsv110RsySid());
            }


            //「スケジュールへ反映」が選択されている かつ
            //スケジュールと関連付いている または スケジュールが入力されている
            if (schInsertFlg) {
                SchBinDao schBinDao = new SchBinDao(con_);
                SchDao schDao = new SchDao(con_);
                //スケジュールアクセス不可グループ or ユーザを取得
                int sessionUserSid = reqMdl_.getSmodel().getUsrsid();
                List<Integer> notAccessList = new ArrayList<Integer>();
                if (scdUsrKbn == GSConstReserve.RSV_SCHKBN_GROUP) {
                    notAccessList = schDao.getNotRegistGrpList(sessionUserSid);
                } else {
                    notAccessList = schDao.getNotRegistUserList(sessionUserSid);
                }

                //登録対象ユーザからスケジュールアクセス不可グループ or ユーザを除外する
                ArrayList<Integer> schAccessUserList = new ArrayList<Integer>();
                ArrayList<Integer> schNotAccessUserList = new ArrayList<Integer>();
                for (int scdUsrSid : usrSidArray) {
                    if (notAccessList.indexOf(scdUsrSid) < 0) {
                        schAccessUserList.add(scdUsrSid);
                    } else {
                        schNotAccessUserList.add(scdUsrSid);
                    }
                }
                //削除対象スケジュールのスケジュールユーザSID
                String[] delScdUsrSid = new String[schAccessUserList.size()];
                for (int scdUsrIdx = 0; scdUsrIdx < schAccessUserList.size(); scdUsrIdx++) {
                    delScdUsrSid[scdUsrIdx]
                            = String.valueOf(schAccessUserList.get(scdUsrIdx));
                }

                if (sceSid > 0) {
                    List<Integer> scdSidList = scdDao.getScdSid(sceSid);
                    //スケジュール削除
                    scdDao.deleteKakutyoDataWithUsers(sceSid, delScdUsrSid);
                    if (scdUsrKbn == GSConstReserve.RSV_SCHKBN_GROUP) {
                        for (String grpSid : delScdUsrSid) {
                            schCmnBiz.deleteGroupPushList(scdSidList, Integer.parseInt(grpSid));
                        }
                    } else {
                        schCmnBiz.deleteUserPushList(scdSidList, delScdUsrSid);
                    }

                    //スケジュール添付情報の削除
                    schBinDao.deleteTempFile(scdSidList);

                    //スケジュール公開対象の削除
                    scdDao.deleteSdpData(scdSidList);

                    //スケジュール拡張に紐付くスケジュールが無くなった
                    if (scdDao.selectExDataCnt(sceSid) == 0) {
                        SchExdataBinDao schExBinDao = new SchExdataBinDao(con_);
                        //スケジュール拡張データ削除
                        scdDao.deleteExData(sceSid);

                        //スケジュール拡張情報_添付ファイル情報削除
                        schExBinDao.deleteTempFile(Set.of(sceSid));

                        //スケジュール拡張情報_公開対象削除
                        scdDao.deleteExPubData(sceSid);
                    }
                } else {
                    //スケジュール削除
                    if (yrkRet.getScdRsSid() > 0) {

                        schCmnBiz.deleteUserPushList(yrkRet.getScdRsSid(), delScdUsrSid);
                        scdDao.deleteScdTiWithUsers(yrkRet.getScdRsSid(), delScdUsrSid);
                        ArrayList<RsvScdOperationModel> oldScdList =
                                scdDao.selectSchList(yrkRet.getScdRsSid());

                        List<Integer> delScdSidList =
                                oldScdList.stream()
                                .map(s -> s.getScdSid())
                                .collect(Collectors.toList());

                        //スケジュール添付情報の削除
                        schBinDao.deleteTempFile(delScdSidList);

                        //スケジュール公開対象の削除
                        scdDao.deleteSdpData(delScdSidList);
                    }
                }
            }
       }

        return sidDataList;
    }
    /**
     * <br>[機  能]オペレーションログ出力用予約削除内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ格納モデル
     * @throws SQLException SQL実行エラー
     */
    private void __creatDelOpLog(Rsv111knParamModel paramMdl) throws SQLException {

        //予約情報取得
        Rsv110SisetuModel yrkMdl = __getYoyakuEditData(paramMdl);
        Rsv070Model sisetsuJyohoMdl = __getGroupData(yrkMdl.getRsdSid());

        StringBuilder opLog = new StringBuilder();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        opLog.append("[");
        opLog.append(gsMsg.getMessage("cmn.facility.name"));
        opLog.append("]");
        opLog.append(sisetsuJyohoMdl.getRsdName());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(gsMsg.getMessage("cmn.period"));
        opLog.append("]");

        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(yrkMdl.getRsyFrDate().getYear()),
                String.valueOf(yrkMdl.getRsyFrDate().getMonth()),
                String.valueOf(yrkMdl.getRsyFrDate().getIntDay()),
                String.valueOf(yrkMdl.getRsyFrDate().getIntHour()),
                String.valueOf(yrkMdl.getRsyFrDate().getIntMinute())
        }));
        opLog.append(" ～ ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(yrkMdl.getRsyToDate().getYear()),
                String.valueOf(yrkMdl.getRsyToDate().getMonth()),
                String.valueOf(yrkMdl.getRsyToDate().getIntDay()),
                String.valueOf(yrkMdl.getRsyToDate().getIntHour()),
                String.valueOf(yrkMdl.getRsyToDate().getIntMinute())
        }));
        opLog.append("\n");
        opLog.append("[");
        opLog.append(gsMsg.getMessage("reserve.72"));
        opLog.append("]");
        opLog.append(yrkMdl.getRsyMok());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(gsMsg.getMessage("cmn.content"));
        opLog.append("]");
        opLog.append(yrkMdl.getRsyBiko());
        delLog__ = opLog;
    }
    /**
     * <br>[機  能] 予約を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void deleteYoyakuData(Rsv111knParamModel paramMdl) throws SQLException {

        int rsrRsid = paramMdl.getRsv111RsrRsid();
        int sceSid = -1;
        RsvDataPubDao rdpDao = new RsvDataPubDao(con_);
        RsvExdataPubDao repDao = new RsvExdataPubDao(con_);
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con_);
        RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
        RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con_);
        RsvScdOperationDao scdDao = new RsvScdOperationDao(con_);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con_, reqMdl_);
        RsvSisYrkModel selParam = new RsvSisYrkModel();
        selParam.setRsySid(paramMdl.getRsv110RsySid());
        RsvSisYrkModel yrkBase = yrkDao.select(selParam);

        __creatDelOpLog(paramMdl);

        //スケジュールと関連付いている
        if (paramMdl.getRsv110ScdRsSid() > 0) {
            //スケジュール拡張SID取得
            sceSid = scdDao.selectSceSid(paramMdl.getRsv110ScdRsSid());

            //スケジュールが繰り返し登録されている
            ArrayList<Integer> scdRsSidArray = new ArrayList<Integer>();
            if (sceSid > 0) {
                //拡張登録された全ての施設のスケジュールリレーションSIDを取得
                ArrayList<Integer> rsSidList = scdDao.selectKakutyoAllScdRsSid(sceSid);
                for (int rsSid : rsSidList) {
                    if (rsSid > 0) {
                        scdRsSidArray.add(rsSid);
                    }
                }

            } else {
                RsvSisYrkModel param = new RsvSisYrkModel();
                param.setRsySid(paramMdl.getRsv110RsySid());
                RsvSisYrkModel yrkRet = yrkDao.select(param);
                if (yrkRet != null && yrkRet.getScdRsSid() > 0) {
                    scdRsSidArray.add(yrkRet.getScdRsSid());
                }
            }

            //施設予約SID取得
            ArrayList<Integer> rsySidArray = scdDao.selectKakutyoAllRsysid(scdRsSidArray);
            //施設拡張SID取得
            ArrayList<Integer> rsrRsidArray = scdDao.selectKakutyoAllRsrRsid(scdRsSidArray);

            //公開対象情報を削除
            rdpDao.deleteList(rsySidArray);
            //施設予約情報削除
            yrkDao.deleteRyrkData(scdRsSidArray);
            //施設予約区分別情報削除
            kyrkDao.delete(rsySidArray);

            //施設予約が繰り返し登録されている
            if (paramMdl.getRsv111RsrRsid() > 0) {

                //拡張公開対象情報を削除
                repDao.delete(rsrRsidArray);
                //施設予約拡張情報削除
                ryrkDao.delete(rsrRsidArray);
                //施設予約拡張区分別情報削除
                kryrkDao.delete(rsrRsidArray);
            }

        } else {
            //施設予約が繰り返し登録されている
            if (paramMdl.getRsv111RsrRsid() > 0) {
                //削除する施設予約SIDを取得
                ArrayList<Integer> rsySids = yrkDao.getYrkDataSidList(rsrRsid);
                //公開対象情報を削除
                rdpDao.deleteList(rsySids);
                //施設予約情報削除
                yrkDao.deleteRyrkData(rsrRsid);
                //施設予約区分別情報削除
                kyrkDao.delete(rsySids);

                //拡張公開対象情報を削除
                repDao.delete(rsrRsid);
                //施設予約拡張情報削除
                ryrkDao.delete(rsrRsid);
                //施設予約拡張区分別情報削除
                kryrkDao.delete(rsrRsid);


            } else {

                //公開対象情報を削除
                rdpDao.delete(paramMdl.getRsv110RsySid());
                //施設予約情報削除
                RsvSisYrkModel param = new RsvSisYrkModel();
                param.setRsySid(paramMdl.getRsv110RsySid());
                yrkDao.delete(param);
                //施設予約区分別情報削除
                kyrkDao.delete(paramMdl.getRsv110RsySid());

            }
        }

        //スケジュールと関連付いている & 「スケジュールへ反映」が選択されている
        if (paramMdl.getRsv110ScdRsSid() > 0
                && paramMdl.getRsv111ScdReflection() == GSConstReserve.SCD_REFLECTION_OK) {

            int scdUsrKbn = 0;
            ArrayList<Integer> usrSidArray = new ArrayList<Integer>();
            List<Integer> scdSidList = scdDao.getScdSid(sceSid);
            if (sceSid > 0) {
                //登録対象ユーザ取得
                usrSidArray = scdDao.selectKakutyoAllUsrSid(sceSid);
                //スケジュール ユーザ区分
                scdUsrKbn = scdDao.selectKakutyoUsrKbn(sceSid);
            } else {
                //登録対象ユーザ取得
                usrSidArray = scdDao.selectScdUsrSid(yrkBase.getScdRsSid());
                //スケジュール ユーザ区分
                scdUsrKbn = scdDao.getSchUsrKbn(yrkBase.getScdRsSid());
            }

            //スケジュールアクセス不可グループ or ユーザを取得
            int sessionUserSid = reqMdl_.getSmodel().getUsrsid();
            SchDao schDao = new SchDao(con_);
            List<Integer> notAccessList = new ArrayList<Integer>();
            if (scdUsrKbn == GSConstReserve.RSV_SCHKBN_GROUP) {
                notAccessList = schDao.getNotRegistGrpList(sessionUserSid);
            } else {
                notAccessList = schDao.getNotRegistUserList(sessionUserSid);
            }

            //登録対象ユーザからスケジュールアクセス不可グループ or ユーザを除外する
            ArrayList<Integer> schAccessUserList = new ArrayList<Integer>();
            ArrayList<Integer> schNotAccessUserList = new ArrayList<Integer>();
            for (int scdUsrSid : usrSidArray) {
                if (notAccessList.indexOf(scdUsrSid) < 0) {
                    schAccessUserList.add(scdUsrSid);
                } else {
                    schNotAccessUserList.add(scdUsrSid);
                }
            }
            usrSidArray = schAccessUserList;

            //削除対象スケジュールのスケジュールユーザSID
            String[] delScdUsrSid = new String[schAccessUserList.size()];
            for (int scdUsrIdx = 0; scdUsrIdx < schAccessUserList.size(); scdUsrIdx++) {
                delScdUsrSid[scdUsrIdx]
                        = String.valueOf(schAccessUserList.get(scdUsrIdx));
            }

            //スケジュールが繰り返し登録されているデータ
            if (sceSid != -1) {


                //削除対象となるスケジュールのSIDを取得
                List<Integer> delScdSidList
                    = scdDao.getKakutyoDataWithUsers(sceSid, delScdUsrSid);
                SchBinDao schBinDao = new SchBinDao(con_);
                if (!delScdSidList.isEmpty()) {
                    schBinDao.deleteTempFile(delScdSidList);
                    //スケジュール公開対象削除
                    scdDao.deleteSdpData(delScdSidList);
                }

                //スケジュール削除
                scdDao.deleteKakutyoDataWithUsers(sceSid, delScdUsrSid);

                if (scdUsrKbn == GSConstReserve.RSV_SCHKBN_GROUP) {
                    for (String usrSidStr : delScdUsrSid) {
                        int usrSid = Integer.parseInt(usrSidStr);
                        schCmnBiz.deleteGroupPushList(scdSidList, usrSid);
                    }
                } else {
                    //PUSH通知リスト削除
                    schCmnBiz.deleteUserPushList(scdSidList, delScdUsrSid);
                }

                //スケジュール拡張に紐付くスケジュールが無くなった
                if (scdDao.selectExDataCnt(sceSid) == 0) {
                    //スケジュール拡張情報_添付情報削除
                    SchExdataBinDao exBinDao = new SchExdataBinDao(con_);
                    exBinDao.deleteTempFile(Set.of(sceSid));

                    //スケジュール拡張情報_公開対象削除
                    scdDao.deleteExPubData(sceSid);

                    //スケジュール拡張データ削除
                    scdDao.deleteExData(sceSid);
                }
            }
        }
    }

    /**
     * <br>[機  能] 拡張予約データモデル取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param now システム日付
     * @param old 旧モデル
     * @return ret 拡張予約データモデル
     */
    private RsvSisRyrkModel __getRyrkBaseModel(Rsv111knParamModel paramMdl,
                                                int sessionUsrSid,
                                                UDate now,
                                                RsvSisRyrkModel old) {

        RsvSisRyrkModel ret = new RsvSisRyrkModel();

        //区分
        ret.setRsrKbn(paramMdl.getRsv111RsrKbn());
        //曜日(日曜)
        ret.setRsrDweek1(paramMdl.getRsv111RsrDweek1());
        //曜日(月曜)
        ret.setRsrDweek2(paramMdl.getRsv111RsrDweek2());
        //曜日(火曜)
        ret.setRsrDweek3(paramMdl.getRsv111RsrDweek3());
        //曜日(水曜)
        ret.setRsrDweek4(paramMdl.getRsv111RsrDweek4());
        //曜日(木曜)
        ret.setRsrDweek5(paramMdl.getRsv111RsrDweek5());
        //曜日(金曜)
        ret.setRsrDweek6(paramMdl.getRsv111RsrDweek6());
        //曜日(土曜)
        ret.setRsrDweek7(paramMdl.getRsv111RsrDweek7());
        //日
        ret.setRsrDay(paramMdl.getRsv111RsrDay());
        //週
        ret.setRsrWeek(paramMdl.getRsv111RsrWeek());
        //毎年 月
        ret.setRsrMonthYearly(paramMdl.getRsv111RsrMonthOfYearly());
        //毎年 日
        ret.setRsrDayYearly(paramMdl.getRsv111RsrDayOfYearly());

        //時分from
        UDate timeFr = now.cloneUDate();
        timeFr.setHour(Integer.parseInt(paramMdl.getRsv111RsrTimeHourFr()));
        timeFr.setMinute(Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteFr()));
        ret.setRsrTimeFr(timeFr);

        //時分to
        UDate timeTo = now.cloneUDate();
        timeTo.setHour(Integer.parseInt(paramMdl.getRsv111RsrTimeHourTo()));
        timeTo.setMinute(Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteTo()));
        ret.setRsrTimeTo(timeTo);

        //振替区分
        ret.setRsrTranKbn(paramMdl.getRsv111RsrTranKbn());

        //反映期間from
        UDate dateFr = new UDate();
        dateFr.setDate(
                Integer.parseInt(paramMdl.getRsv111RsrDateYearFr()),
                Integer.parseInt(paramMdl.getRsv111RsrDateMonthFr()),
                Integer.parseInt(paramMdl.getRsv111RsrDateDayFr()));
        ret.setRsrDateFr(dateFr);

        //反映期間to
        UDate dateTo = new UDate();
        dateTo.setDate(
                Integer.parseInt(paramMdl.getRsv111RsrDateYearTo()),
                Integer.parseInt(paramMdl.getRsv111RsrDateMonthTo()),
                Integer.parseInt(paramMdl.getRsv111RsrDateDayTo()));
        ret.setRsrDateTo(dateTo);

        //利用目的
        ret.setRsrMok(NullDefault.getString(paramMdl.getRsv111RsrMok(), ""));
        //内容
        ret.setRsrBiko(NullDefault.getString(paramMdl.getRsv111RsrBiko(), ""));
        //編集権限設定
        ret.setRsrEdit(paramMdl.getRsv111RsrEdit());
        //公開区分設定
        ret.setRsrPublic(paramMdl.getRsv111RsrPublic());
        //登録者ID
        ret.setRsrAuid(sessionUsrSid);
        //登録日時
        ret.setRsrAdate(now);
        if (old != null) {
            //登録者ID
            ret.setRsrAuid(old.getRsrAuid());
            //登録日時
            ret.setRsrAdate(old.getRsrAdate());
        }
        //更新者ID
        ret.setRsrEuid(sessionUsrSid);
        //更新日時
        ret.setRsrEdate(now);

        return ret;
    }

    /**
     * <br>[機  能] 予約データモデル取得
     * <br>[解  説]
     * <br>[備  考] 新規登録時
     *
     * @param paramMdl Rsv111knParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param now システム日付
     * @param oldExMdl 旧モデル
     * @return ret 予約データモデル
     * @throws SQLException SQL実行時例外
     */
    private RsvSisYrkModel __getNewYrkBaseModel(Rsv111knParamModel paramMdl,
                                                 int sessionUsrSid,
                                                 UDate now,
                                                 RsvSisRyrkModel oldExMdl)
        throws SQLException {

        RsvSisYrkModel ret = new RsvSisYrkModel();
        ret.setRsdSid(paramMdl.getRsv110RsdSid());
        //利用目的
        ret.setRsyMok(NullDefault.getString(paramMdl.getRsv111RsrMok(), ""));

        UDate frUd = new UDate();
        UDate toUd = new UDate();

        frUd.setDate(now.getDateString());
        frUd.setHour(Integer.parseInt(paramMdl.getRsv111RsrTimeHourFr()));
        frUd.setMinute(Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteFr()));
        frUd.setSecond(GSConstReserve.DAY_START_SECOND);
        frUd.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        toUd.setDate(now.getDateString());
        toUd.setHour(Integer.parseInt(paramMdl.getRsv111RsrTimeHourTo()));
        toUd.setMinute(Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteTo()));
        toUd.setSecond(GSConstReserve.DAY_START_SECOND);
        toUd.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //予約開始
        ret.setRsyFrDate(frUd);
        //予約終了
        ret.setRsyToDate(toUd);
        //備考
        ret.setRsyBiko(NullDefault.getString(paramMdl.getRsv111RsrBiko(), ""));
        //編集権限
        ret.setRsyEdit(paramMdl.getRsv111RsrEdit());
        //公開区分
        ret.setRsyPublic(paramMdl.getRsv111RsrPublic());

        //登録者ID
        ret.setRsyAuid(sessionUsrSid);
        //登録日時
        ret.setRsyAdate(now);
        if (oldExMdl != null) {
            //登録者ID
            ret.setRsyAuid(oldExMdl.getRsrAuid());
            //登録日時
            ret.setRsyAdate(oldExMdl.getRsrAdate());
        }
        //更新者ID
        ret.setRsyEuid(sessionUsrSid);
        //更新日時
        ret.setRsyEdate(now);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    private Rsv110SisetuModel __getYoyakuEditData(
            Rsv111knParamModel paramMdl) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuEditData(paramMdl.getRsv110RsySid());

        return ret;
    }

     /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    private Rsv110SisetuModel __getYoyakuAddData(
            Rsv111knParamModel paramMdl) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuAddData(paramMdl.getRsv110RsySid());

        return ret;
    }

    /**
     * <br>[機  能] 施設グループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsdSid 施設SID
     * @return ret 取得結果
     * @throws SQLException SQL実行時例外
     */
    private Rsv070Model __getGroupData(int rsdSid) throws SQLException {

        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        Rsv070Model ret = dataDao.getPopUpSisetuData(rsdSid);

        return ret;
    }

    /**
     * <br>[機  能] 施設区分に応じて施設のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuHeader(Rsv111knParamModel paramMdl, int rskSid) {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv110PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv110PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv110PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv110PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv110PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv110PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv110PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv110PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv110PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
    }

    /**
     * <br>[機  能] DBから取得した施設グループ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @param dbMdl DB取得結果
     */
    private void __setGroupData(Rsv111knParamModel paramMdl,
                                  Rsv070Model dbMdl) {

        //所属グループ名
        paramMdl.setRsv110GrpName(NullDefault.getString(dbMdl.getRsgName(), ""));
        //施設区分名称 */
        paramMdl.setRsv110SisetuKbnName(NullDefault.getString(dbMdl.getRskName(), ""));
        //施設名称
        paramMdl.setRsv110SisetuName(NullDefault.getString(dbMdl.getRsdName(), ""));
        //資産管理番号
        paramMdl.setRsv110SisanKanri(NullDefault.getString(dbMdl.getRsdSnum(), ""));
        //可変項目1
        paramMdl.setRsv110Prop1Value(NullDefault.getString(dbMdl.getRsdProp1Value(), ""));
        //可変項目2
        paramMdl.setRsv110Prop2Value(
                NullDefault.getStringZeroLength(
                        dbMdl.getRsdProp2Value(),
                        String.valueOf(GSConstReserve.PROP_KBN_KA)));
        //可変項目3
        paramMdl.setRsv110Prop3Value(
                NullDefault.getStringZeroLength(
                        dbMdl.getRsdProp3Value(),
                        String.valueOf(GSConstReserve.PROP_KBN_KA)));
        //可変項目4
        paramMdl.setRsv110Prop4Value(NullDefault.getString(dbMdl.getRsdProp4Value(), ""));
        //可変項目5
        paramMdl.setRsv110Prop5Value(NullDefault.getString(dbMdl.getRsdProp5Value(), ""));
        //可変項目6
        paramMdl.setRsv110Prop6Value(NullDefault.getString(dbMdl.getRsdProp6Value(), ""));
        //可変項目7
        paramMdl.setRsv110Prop7Value(
                NullDefault.getStringZeroLength(
                        dbMdl.getRsdProp7Value(),
                        String.valueOf(GSConstReserve.PROP_KBN_KA)));
        //備考
        paramMdl.setRsv110Biko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(dbMdl.getRsdBiko(), "")));
    }

    /**
     * <br>[機  能] 今回登録する日付リストを設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setNewKurikaeshiDataList(Rsv111knParamModel paramMdl) throws SQLException {

        ArrayList<String> newList = new ArrayList<String>();

        //登録日のチェック
        Rsv111Biz biz = new Rsv111Biz(reqMdl_, con_);
        HashMap<String, String> dayMap = biz.getInsertDateList(paramMdl);

        if (!dayMap.isEmpty()) {
            ArrayList<String> dayList = new ArrayList<String>(dayMap.values());
            Collections.sort(dayList);

            for (String day : dayList) {
                UDate dayUd = new UDate();
                dayUd.setDate(day);
                newList.add(UDateUtil.getSlashYYMD(dayUd));
            }
        }

        paramMdl.setTargetDay(newList);
    }

    /**
     * <br>[機  能] 登録済データの日付リストを設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setOldKurikaeshiDataList(Rsv111knParamModel paramMdl) throws SQLException {

        ArrayList<String> ret = new ArrayList<String>();

        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        //予約拡張SID取得
        int rsrRsid = paramMdl.getRsv111RsrRsid();

        if (rsrRsid > 0) {
            ret = dao.getKurikaeshiDataList(rsrRsid);
        } else {
            //予約SID取得
            int rsySid = paramMdl.getRsv110RsySid();
            ret = dao.getTanituDataList(rsySid);
        }

        paramMdl.setOldDay(ret);
    }

    /**
     * <br>[機  能] 年月日選択値をyyyy/MM/ddに変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @param convKbn 1:from 2:to
     * @return convDateString 変換後
     */
    private String __convertUdateToYmd(Rsv111knParamModel paramMdl, int convKbn) {

        String convDateString = "";
        StringBuilder convBuf = new StringBuilder();

        int intYear = -1;
        int intMonth = -1;
        int intDay = -1;

        if (convKbn == 1) {
            intYear = Integer.parseInt(paramMdl.getRsv111RsrDateYearFr());
            intMonth = Integer.parseInt(paramMdl.getRsv111RsrDateMonthFr());
            intDay = Integer.parseInt(paramMdl.getRsv111RsrDateDayFr());
        } else if (convKbn == 2) {
            intYear = Integer.parseInt(paramMdl.getRsv111RsrDateYearTo());
            intMonth = Integer.parseInt(paramMdl.getRsv111RsrDateMonthTo());
            intDay = Integer.parseInt(paramMdl.getRsv111RsrDateDayTo());
        }

        UDate convUd = new UDate();
        convUd.setDate(intYear, intMonth, intDay);
        convBuf.append(UDateUtil.getSlashYYMD(convUd));
        convDateString = convBuf.toString();

        return convDateString;
    }

    /**
     * <br>[機  能] 時間選択値をhh:mmに変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @param convKbn 1:from 2:to
     * @return convDateString 変換後
     */
    private String __convertUdateToHm(Rsv111knParamModel paramMdl, int convKbn) {

        String convDateString = "";
        StringBuilder convBuf = new StringBuilder();

        String strHour = "";
        String strMinute = "";

        if (convKbn == 1) {
            strHour = StringUtil.toDecFormat(paramMdl.getRsv111RsrTimeHourFr(), "00");
            strMinute = StringUtil.toDecFormat(paramMdl.getRsv111RsrTimeMinuteFr(), "00");
        } else if (convKbn == 2) {
            strHour = StringUtil.toDecFormat(paramMdl.getRsv111RsrTimeHourTo(), "00");
            strMinute = StringUtil.toDecFormat(paramMdl.getRsv111RsrTimeMinuteTo(), "00");
        }

        convBuf.append(strHour);
        convBuf.append("：");
        convBuf.append(strMinute);
        convDateString = convBuf.toString();

        return convDateString;
    }

    /**
     * <br>[機  能] 施設予約データに対応するスケジュールデータが存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv111knParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __existSchData(Rsv111ParamModel paramMdl)
        throws SQLException {

        RsvScdOperationDao scdDao = new RsvScdOperationDao(con_);

        //スケジュール拡張SID取得
        int sceSid = scdDao.selectSceSid(paramMdl.getRsv110ScdRsSid());

        if (sceSid < 1) {
            paramMdl.setRsv111ExistSchDateFlg(false);
            return;
        }

        int exdataCnt = scdDao.selectExDataCnt(sceSid);
        if (exdataCnt < 1) {
            //スケジュール拡張に紐付くスケジュールが無い
            paramMdl.setRsv111ExistSchDateFlg(false);
            return;
        }

        paramMdl.setRsv111ExistSchDateFlg(true);
    }

    /**
     * <br>[機  能] スケジュール拡張情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv111knParamModel
     * @param oldMdl 変更前スケジュール拡張情報(新規登録時はnull);
     * @param sessionUsrSid セッションユーザSID
     * @param now 現在日時
     * @return 登録したスケジュール拡張情報
     * @throws SQLException SQL実行時例外
     */
    public SchExdataModel __createSchExData(Rsv111knParamModel paramMdl,
                                    RsvScdOperationModel oldMdl, int sessionUsrSid, UDate now)
    throws SQLException {

        SchExdataModel exMdl = new SchExdataModel();
        exMdl.setSceKbn(paramMdl.getRsv111RsrKbn());
        exMdl.setSceDweek1(paramMdl.getRsv111RsrDweek1());
        exMdl.setSceDweek2(paramMdl.getRsv111RsrDweek2());
        exMdl.setSceDweek3(paramMdl.getRsv111RsrDweek3());
        exMdl.setSceDweek4(paramMdl.getRsv111RsrDweek4());
        exMdl.setSceDweek5(paramMdl.getRsv111RsrDweek5());
        exMdl.setSceDweek6(paramMdl.getRsv111RsrDweek6());
        exMdl.setSceDweek7(paramMdl.getRsv111RsrDweek7());
        exMdl.setSceDay(paramMdl.getRsv111RsrDay());
        exMdl.setSceWeek(paramMdl.getRsv111RsrWeek());
        exMdl.setSceMonthOfYearly(paramMdl.getRsv111RsrMonthOfYearly());
        exMdl.setSceDayOfYearly(paramMdl.getRsv111RsrDayOfYearly());

        //時分from
        UDate timeFr = now.cloneUDate();
        timeFr.setHour(Integer.parseInt(paramMdl.getRsv111RsrTimeHourFr()));
        timeFr.setMinute(Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteFr()));
        timeFr.setSecond(GSConstReserve.DAY_START_SECOND);
        timeFr.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);
        exMdl.setSceTimeFr(timeFr);

        //時分to
        UDate timeTo = now.cloneUDate();
        timeTo.setHour(Integer.parseInt(paramMdl.getRsv111RsrTimeHourTo()));
        timeTo.setMinute(Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteTo()));
        timeTo.setSecond(GSConstReserve.DAY_START_SECOND);
        timeTo.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);
        exMdl.setSceTimeTo(timeTo);

        exMdl.setSceTranKbn(paramMdl.getRsv111RsrTranKbn());

        //反映期間from
        UDate dateFr = new UDate();
        dateFr.setDate(
                Integer.parseInt(paramMdl.getRsv111RsrDateYearFr()),
                Integer.parseInt(paramMdl.getRsv111RsrDateMonthFr()),
                Integer.parseInt(paramMdl.getRsv111RsrDateDayFr()));
        dateFr.setZeroHhMmSs();
        exMdl.setSceDateFr(dateFr);

        //反映期間to
        UDate dateTo = new UDate();
        dateTo.setDate(
                Integer.parseInt(paramMdl.getRsv111RsrDateYearTo()),
                Integer.parseInt(paramMdl.getRsv111RsrDateMonthTo()),
                Integer.parseInt(paramMdl.getRsv111RsrDateDayTo()));
        dateTo.setZeroHhMmSs();
        exMdl.setSceDateTo(dateTo);

        if (oldMdl != null) {
            exMdl.setSceBgcolor(oldMdl.getSceBgcolor());
            exMdl.setSceBiko(NullDefault.getString(oldMdl.getSceBiko(), ""));
            exMdl.setSceAuid(oldMdl.getSceAuid());
            exMdl.setSceAdate(oldMdl.getSceAdate());
        } else {
            exMdl.setSceBgcolor(GSConstSchedule.DF_BG_COLOR);
            exMdl.setSceBiko("");
            exMdl.setSceAuid(sessionUsrSid);
            exMdl.setSceAdate(now);
        }

        exMdl.setSceEdit(RsvScheduleBiz.getScdEditKbn(paramMdl.getRsv111RsrEdit()));
        exMdl.setScePublic(RsvScheduleBiz.getScdPublicKbn(
                paramMdl.getRsv111RsrPublic(), paramMdl.getRsv111SchKbn()));
        exMdl.setSceTitle(NullDefault.getString(paramMdl.getRsv111RsrMok(), ""));
        exMdl.setSceValue(NullDefault.getString(paramMdl.getRsv111RsrBiko(), ""));
        exMdl.setSceEuid(sessionUsrSid);
        exMdl.setSceEdate(now);


        return exMdl;
    }

    /**
     * <br>[機  能] スケジュールが入力されているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv111knParamModel
     * @return true:入力されている false:未入力
     */
    private boolean __checkInputSchedule(Rsv111knParamModel paramMdl) {
        int schKbn = paramMdl.getRsv111SchKbn();
        int rsvSchGrpSid = NullDefault.getInt(paramMdl.getRsv111SchGroupSid(), -1);
        String[] users = paramMdl.getRsv111SvUsers();
        return (schKbn == GSConstReserve.RSV_SCHKBN_GROUP && rsvSchGrpSid >= 0)
        || (schKbn == GSConstReserve.RSV_SCHKBN_USER && users != null && users.length > 0);
    }

    /**
     * <br>[機  能] 予約情報登録者ユーザSID取得。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv111knParamModel
     * @return int ユーザSID
     * @throws Exception 実行例外
     */
    public int getEntryUserSid(
        Rsv111knParamModel paramMdl) throws Exception {

        int entryUserSid = -1;
        Rsv110SisetuModel model = __getYoyakuEditData(paramMdl);

        entryUserSid = model.getRsyAuid();
        return entryUserSid;
    }

    /**
     * <br>[機  能] ショートメールで通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv111knParamModel
     * @param cntCon MlCountMtController
     * @param userSid 更新者SID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig PluginConfig
     * @param entryUserSid 登録ユーザSID
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Rsv111knParamModel paramMdl,
        MlCountMtController cntCon,
        int userSid,
        String appRootPath,
        String tempDir,
        PluginConfig pluginConfig,
        int entryUserSid) throws Exception {

        //施設予約表示モデル(ショートメール送信用)
        ReserveSmlModel rsvModel = new ReserveSmlModel();

        UDate now = new UDate();
        String strNow = now.getDateString();
        UDate fromUDate = new UDate();
        UDate toUDate = new UDate();
        Rsv110SisetuModel model = new Rsv110SisetuModel();
        rsvModel.setRsvSid(model.getRsdSid());
        rsvModel.setRsvMokuteki(paramMdl.getRsv111RsrMok());
        rsvModel.setRsvNaiyou(paramMdl.getRsv111RsrBiko());
        rsvModel.setUserSid(userSid);

        fromUDate.setTimeStamp(Integer.parseInt(paramMdl.getRsv111RsrDateYearFr()),
                               Integer.parseInt(paramMdl.getRsv111RsrDateMonthFr()),
                               Integer.parseInt(paramMdl.getRsv111RsrDateDayFr()),
                               Integer.parseInt(paramMdl.getRsv111RsrTimeHourFr()),
                               Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteFr()),
                               0);

        toUDate.setTimeStamp(Integer.parseInt(paramMdl.getRsv111RsrDateYearTo()),
                             Integer.parseInt(paramMdl.getRsv111RsrDateMonthTo()),
                             Integer.parseInt(paramMdl.getRsv111RsrDateDayTo()),
                             Integer.parseInt(paramMdl.getRsv111RsrTimeHourTo()),
                             Integer.parseInt(paramMdl.getRsv111RsrTimeMinuteTo()),
                             0);

        rsvModel.setRsvFrDate(fromUDate);
        rsvModel.setRsvToDate(toUDate);
        rsvModel.setRsvAdate(now);

        rsvModel.setRsvUrl(createReserveLoopUrl(reqMdl_,
                                            paramMdl.getExtendSid(),
                                            Integer.parseInt(GSConstReserve.PROC_MODE_EDIT),
                                            strNow));
        rsvModel.setEditModeFlg(1);

        //送信
        sendSmail(con_, cntCon, rsvModel, appRootPath, pluginConfig, reqMdl_, entryUserSid);

    }

    /**
     * <br>[機  能] 施設予約区分別データモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param rsrRsid 施設予約SID
     * @param sessionUsrSid セッションユーザSID
     * @param now 時間
     * @param appRootPath アプリケーションルートパス
     * @return 施設予約拡張区分別データモデル
     */
    private RsvSisKyrkModel __getKyrModel(
            Rsv111knParamModel paramMdl,
            int rsrRsid,
            int sessionUsrSid,
            UDate now,
            String appRootPath) {

        RsvSisKyrkModel mdl = new RsvSisKyrkModel();
        //予約SID
        mdl.setRsySid(rsrRsid);
        mdl.setRkyBusyo(paramMdl.getRsv111Busyo());
        mdl.setRkyName(paramMdl.getRsv111UseName());
        mdl.setRkyNum(paramMdl.getRsv111UseNum());
        mdl.setRkyUseKbn(paramMdl.getRsv111UseKbn());
        mdl.setRkyContact(paramMdl.getRsv111Contact());
        mdl.setRkyGuide(paramMdl.getRsv111Guide());
        mdl.setRkyParkNum(paramMdl.getRsv111ParkNum());
        if (RsvCommonBiz.isUsePrintKbn(appRootPath)) {
            mdl.setRkyPrintKbn(paramMdl.getRsv111PrintKbn());
        }
        mdl.setRkyDest(paramMdl.getRsv111Dest());
        mdl.setRkyAuid(sessionUsrSid);
        mdl.setRkyAdate(now);
        mdl.setRkyEuid(sessionUsrSid);
        mdl.setRkyEdate(now);

        return mdl;
    }



    /**
     *
     * <br>[機  能] 削除登録時のログを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ログ内容
     */
    public String getDelOpLog() {
        if (delLog__ != null) {
            return delLog__.toString();
        }
        return null;
    }
    /**
     * <br>[機  能]オペレーションログ出力用予約登録・編集内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sisetsuSid 施設SID
     * @param paramMdl パラメータ格納モデル
     * @return オペレーションログ表示内容
     * @throws SQLException SQL実行エラー
     */
    public String getOpLog(int sisetsuSid, Rsv111knParamModel paramMdl)
            throws SQLException {
        //ログ出力準備
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String sisetu = gsMsg.getMessage("cmn.facility.name");
        String kikann = gsMsg.getMessage("cmn.period");
        String mokuteki = gsMsg.getMessage("reserve.72");
        String naiyou = gsMsg.getMessage("cmn.content");
        Rsv070Model sisetuMdl = __getGroupData(sisetsuSid);
        StringBuilder opLog = new StringBuilder();
        opLog.append("[");
        opLog.append(sisetu);
        opLog.append("] ");
        opLog.append(sisetuMdl.getRsdName());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(kikann);
        opLog.append("] ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(paramMdl.getRsv111RsrDateYearFr()),
                String.valueOf(paramMdl.getRsv111RsrDateMonthFr()),
                String.valueOf(paramMdl.getRsv111RsrDateDayFr()),
                String.valueOf(paramMdl.getRsv111RsrTimeHourFr()),
                String.valueOf(paramMdl.getRsv111RsrTimeMinuteFr())
        }));
        opLog.append(" ～ ");
        opLog.append(gsMsg.getMessage("cmn.view.date", new String[] {
                String.valueOf(paramMdl.getRsv111RsrDateYearTo()),
                String.valueOf(paramMdl.getRsv111RsrDateMonthTo()),
                String.valueOf(paramMdl.getRsv111RsrDateDayTo()),
                String.valueOf(paramMdl.getRsv111RsrTimeHourTo()),
                String.valueOf(paramMdl.getRsv111RsrTimeMinuteTo())
        }));
        opLog.append("\n");
        opLog.append("[");
        opLog.append(mokuteki);
        opLog.append("] ");
        opLog.append(paramMdl.getRsv111RsrMok());
        opLog.append("\n");
        opLog.append("[");
        opLog.append(naiyou);
        opLog.append("] ");
        opLog.append(paramMdl.getRsv111RsrBiko());
        return opLog.toString();
    }

    /**
     * <br>[機  能] スケジュールの公開対象を削除、登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param scdSid スケジュールSID
     * @param cmdMode 編集区分
     * @throws SQLException SQL実行時例外
     */
    protected void __setSdpMdl(Rsv111knParamModel paramMdl,
            int scdSid, String cmdMode) throws SQLException {

        RsvScdOperationDao rsoDao = new RsvScdOperationDao(con_);
        RsvSchDataPubModel rdpMdl = new RsvSchDataPubModel();
        String[] sidList = paramMdl.getRsv111PubUsrGrpSid();

        //編集時は削除を行う
        if (cmdMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            rsoDao.deleteSdpData(scdSid);
        }

        for (int i = 0; i < sidList.length; i++) {
            if (sidList[i].substring(0, 1).equals("G")) {
                rdpMdl.setSdpPsid(Integer.parseInt(sidList[i].substring(1)));
                rdpMdl.setSdpType(GSConst.TYPE_GROUP);
            } else {
                rdpMdl.setSdpPsid(Integer.parseInt(sidList[i]));
                rdpMdl.setSdpType(GSConst.TYPE_USER);
            }
            rdpMdl.setScdSid(scdSid);
            rsoDao.insert(rdpMdl);
        }
    }
}