package jp.groupsession.v2.sch.biz;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchExaddressDao;
import jp.groupsession.v2.sch.dao.SchExcompanyDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.dao.SchExdataDao;
import jp.groupsession.v2.sch.dao.SchExdataPubDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchBinModel;
import jp.groupsession.v2.sch.model.SchCompanyModel;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchDataPushModel;
import jp.groupsession.v2.sch.model.SchExaddressModel;
import jp.groupsession.v2.sch.model.SchExcompanyModel;
import jp.groupsession.v2.sch.model.SchExdataBinModel;
import jp.groupsession.v2.sch.model.SchExdataModel;
import jp.groupsession.v2.sch.model.SchExdataPubModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.model.SchPushListModel;
/**
 *
 * <br>[機  能] スケジュール情報登録 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchRegister implements ISchRegister {

    /** コネクション */
    private final Connection con__;
    /** リクエストモデル */
    private final RequestModel reqMdl__;
    /** 採番コントローラ */
    private final MlCountMtController cntCon__;

    /** スケジュール個人通知設定マップ 登録対象ユーザSIDがキー */
    private final Map<Integer, SchPriPushModel> priPushConfMap__ = new HashMap<>();
    /** グループ所属ユーザマップ グループSIDがキー */
    private final Map<Integer, List<Integer>> usrBelongMap__ = new HashMap<>();

    /** スケジュール情報 同時登録するスケジュールで共有*/
    private final SchDataModel baseSch__;
    /** 施設予約同時登録の有無 */
    private final boolean isUseRsv__;
    /** コンタクト履歴登録の有無 */
    private final boolean isUseContact__;

    /** 添付ファイルバイナリSID 同時登録するスケジュールで共有*/
    private final List<Long> binSidList__ = new ArrayList<>();
    /** 指定公開対象 SchDataPubModel.scdSidは登録時に使用されない */
    private final List<SchDataPubModel> pubList__ = new ArrayList<>();
    /** 会社SID 同時登録するスケジュールで共有*/
    private final String[] acoSidArr__;
    /** 会社拠点SID 同時登録するスケジュールで共有*/
    private final String[] abaSidArr__;
    /** アドレス情報SID 同時登録するスケジュールで共有*/
    private final String[] adrSidArr__;

    /** スケジュール拡張登録モデル*/
    private final SchExdataModel exMdl__;
    /** 登録方法区分 */
    private final EnumRegistKbn registKbn__;

    /** スケジュール同時登録対象のユーザSIDセット */
    private Set<Integer> users__ = new HashSet<>();

    /** スケジュールグループ 対象SIDマップ*/
    private final Map<SchDataGroupModel, Set<Integer>> scdGrpsTargetMap__ = new HashMap<>();
    /** スケジュールグループ スケジュールSIDマップ*/
    private final Map<SchDataGroupModel, Map<Integer, Integer>> scdGrpsSidMap__ = new HashMap<>();

    /** 更新時の引継ぎ用 出欠確認マップ ユーザSIDがキー 、繰り返し登録時は使用しない*/
    private final Map<Integer, Integer> oldAttendMap__ = new HashMap<>();
    /** 更新時の引継ぎ用 出欠確認コメントマップ ユーザSIDがキー 、繰り返し登録時は使用しない*/
    private final Map<Integer, String> oldAttendCommentMap__ = new HashMap<>();
    /** 更新時の引継ぎ用 通知設定マップ ユーザSIDがキー 、繰り返し登録時は使用しない*/
    private final Map<Integer, SchPriPushModel> oldPushMap__ = new HashMap<>();
    /** 更新時の引継ぎ用 スケジュールグループSID*/
    private final int oldSchGrpSid__;

    /** スケジュール繰り返し登録拡張SID  同時登録するスケジュールで共有*/
    private final int schExtSid__;
    /** 施設予約繰り返し登録拡張SID  同時登録するスケジュールで共有*/
    private final int rsvExtSid__;

    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param baseSch スケジュールモデル
     * @return ビルダー
     */
    public static Builder simpleRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            SchDataModel baseSch
            ) {
        Builder ret = new Builder(con, reqMdl, cntCon);
        ret.registKbn__ = EnumRegistKbn.SIMPLE;
        ret.baseSch__ = baseSch;
        ret.scdGrpsList__.add(new SchDataGroupModel(baseSch));
        return ret;
    }
    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param baseSch スケジュールモデル
     * @param dateList 対象日
     * @param exMdl 繰り返し登録モデル
     * @return ビルダー
     */
    public static Builder kurikaesiRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            SchDataModel baseSch,
            List<UDate> dateList,
            SchExdataModel exMdl
            ) {
        Builder ret = new Builder(con, reqMdl, cntCon);
        ret.registKbn__ = EnumRegistKbn.KURIKAESI;
        ret.baseSch__ = baseSch;
        for (UDate date : dateList) {
            SchDataGroupModel grp = new SchDataGroupModel(baseSch);
            grp.setTargetDate(date);
            UDate from = baseSch.getScdFrDate().cloneUDate();
            UDate to = baseSch.getScdToDate().cloneUDate();
            from.setDate(date.getDateString());
            to.setDate(date.getDateString());
            grp.setFrom(from);
            grp.setTo(to);
            ret.scdGrpsList__.add(grp);
        }
        ret.exMdl__ = exMdl;
        return ret;
    }
    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param baseSch スケジュールモデル
     * @param ikkatuKeyList 一括登録キーリスト
     * @return ビルダー
     */
    public static Builder ikkatuRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            SchDataModel baseSch,
            List<String> ikkatuKeyList
            ) {
        Builder ret = new Builder(con, reqMdl, cntCon);
        ret.registKbn__ = EnumRegistKbn.IKKATU;
        ret.baseSch__ = baseSch;
        for (String torokuKey : ikkatuKeyList) {
            String dateStr = torokuKey.substring(0, torokuKey.indexOf("-"));
            String sidStr = torokuKey.substring(torokuKey.indexOf("-") + 1);
            int selectUsrKbn = GSConstSchedule.USER_KBN_USER;
            if (sidStr.startsWith("G")) {
                sidStr = sidStr.replace("G", "");
                selectUsrKbn = GSConstSchedule.USER_KBN_GROUP;
            }
            UDate date = UDate.getInstanceStr(dateStr);
            SchDataGroupModel grp = new SchDataGroupModel(selectUsrKbn, Integer.parseInt(sidStr));
            grp.setTargetDate(date);
            UDate from = baseSch.getScdFrDate().cloneUDate();
            UDate to = baseSch.getScdToDate().cloneUDate();
            from.setDate(date.getDateString());
            to.setDate(date.getDateString());
            grp.setFrom(from);
            grp.setTo(to);

            ret.scdGrpsList__.add(grp);

        }

        return ret;
    }

    /**
     *
     * 非公開コンストラクタ
     * @param builder ビルダー
     * @throws SQLException SQL実行時例外
     */
    private SchRegister(Builder builder) throws SQLException {
        this.con__ = builder.con__;
        this.reqMdl__ = builder.reqMdl__;
        this.cntCon__ = builder.cntCon__;
        this.registKbn__ = builder.registKbn__;
        this.baseSch__ = builder.baseSch__;

        this.isUseRsv__ = builder.isUseRsv__;
        this.isUseContact__ = builder.isUseContact__;


        builder.scdGrpsList__.stream()
            .forEach(grp -> this.scdGrpsTargetMap__.put(
                    grp,
                    new HashSet<>(Set.of(grp.getScdUsrSid()))));

        Optional.ofNullable(builder.binSidList__)
            .ifPresent(add -> this.binSidList__.addAll(add));

        Optional.ofNullable(builder.pubList__)
            .ifPresent(add -> this.pubList__.addAll(add));

        this.users__.addAll(builder.users__
                .stream()
                //同時登録とメイン登録の重複を除外
                .filter(sid -> Objects.equals(sid, baseSch__.getScdUsrSid()) == false)
                .collect(Collectors.toSet())
                );

        this.exMdl__ = builder.exMdl__;
        this.acoSidArr__ = Optional.ofNullable(builder.acoSidArr__)
                                .stream()
                                .flatMap(Stream::of)
                                .toArray(String[]::new);
        this.abaSidArr__ = Optional.ofNullable(builder.abaSidArr__)
                                .stream()
                                .flatMap(Stream::of)
                                .toArray(String[]::new);
        this.adrSidArr__ = Optional.ofNullable(builder.adrSidArr__)
                                .stream()
                                .flatMap(Stream::of)
                                .toArray(String[]::new);

        Optional.ofNullable(builder.oldAttendMap__)
            .ifPresent(add -> this.oldAttendMap__.putAll(add));
        Optional.ofNullable(builder.oldAttendCommentMap__)
            .ifPresent(add -> this.oldAttendCommentMap__.putAll(add));
        Optional.ofNullable(builder.oldPushMap__)
            .ifPresent(add -> this.oldPushMap__.putAll(add));
        this.oldSchGrpSid__ = builder.oldSchGrpSid__;

        SchCommonBiz cmnBiz = new SchCommonBiz(con__, reqMdl__);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        //登録対象グループ所属一覧Mapを生成する
        __compileBelong();

        //登録対象（同時登録含む）のスケジュール通知個人設定Mapを生成する
        __compilePriPushConf(cmnBiz);

        //必要なSIDの一括採番
        //・スケジュールSID
        __compileSaibanSchedule(sessionUsrSid);

        //・スケジュールグループSID
        __compileSaibanScheduleGroup(sessionUsrSid);

        //・スケジュール拡張登録紐づけSID
        if (builder.schExtSid__ == GSConstSchedule.DF_SCHGP_ID
                && registKbn__ == EnumRegistKbn.KURIKAESI) {
            builder.schExtSid__ = (int) cntCon__.getSaibanNumber(
                    SaibanModel.SBNSID_SCHEDULE,
                    SaibanModel.SBNSID_SUB_SCH_EX,
                    sessionUsrSid);
        }
        this.schExtSid__ = builder.schExtSid__;
        Optional.ofNullable(exMdl__)
            .ifPresent(exm -> exm.setSceSid(schExtSid__));

        this.rsvExtSid__ = builder.rsvExtSid__;
        if (isUseRsv__) {
            //・スケジュール施設予約紐づけSID
            __compileSaibanScheduleReserve(sessionUsrSid, builder.schResSidMap__);
        }

    }
    /**
     * <br>[機  能] 登録対象グループ所属一覧Mapを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __compileBelong() throws SQLException {

        CmnBelongmDao belongDao = new CmnBelongmDao(con__);
        switch (registKbn__) {
            case IKKATU:
                for (SchDataGroupModel grp : scdGrpsTargetMap__.keySet()) {
                    if (grp.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                        continue;
                    }
                    List<Integer> sidList = belongDao.selectBelongLiveUserSid(grp.getScdUsrSid());
                    usrBelongMap__.put(grp.getScdUsrSid(), sidList);
                }
                break;
            case SIMPLE:
            case KURIKAESI:
            default:
                if (baseSch__.getScdUsrKbn() ==  GSConstSchedule.USER_KBN_GROUP) {
                    List<Integer> sidList
                        = belongDao.selectBelongLiveUserSid(baseSch__.getScdUsrSid());
                    usrBelongMap__.put(baseSch__.getScdUsrSid(), sidList);
                }
                break;
        }

    }
    /**
     * <br>[機  能] スケジュール通知個人設定Mapを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmnBiz スケジュール共通Biz
     * @throws SQLException SQL実行時例外
     */
    private void __compilePriPushConf(SchCommonBiz cmnBiz) throws SQLException {
        Set<Integer> targets = new HashSet<>();
        for (SchDataGroupModel grp : scdGrpsTargetMap__.keySet()) {
            if (grp.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                targets.add(grp.getScdUsrSid());
                continue;
            }
            targets.addAll(usrBelongMap__.get(grp.getScdUsrSid()));
        }
        targets.addAll(users__);
        priPushConfMap__.putAll(
                cmnBiz.getUserPriConf(targets
                        .stream()
                        .map(sid -> String.valueOf(sid))
                        .toArray(String[]::new))
                                    );

    }
    /**
     * <br>[機  能] 必要なSIDの一括採番 スケジュールSID
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @return 採番数
     * @throws SQLException SQL実行時例外
     */
    private int __compileSaibanSchedule(int sessionUsrSid) throws SQLException {
        int schCount = 0;
        schCount += scdGrpsTargetMap__.size();
        schCount += scdGrpsTargetMap__.size() * users__.size();

        Iterator<Long> sidItr = cntCon__.getSaibanNumbers(SaibanModel.SBNSID_SCHEDULE,
                SaibanModel.SBNSID_SUB_SCH, sessionUsrSid, schCount)
                .iterator();
        for (SchDataGroupModel grp : scdGrpsTargetMap__.keySet()) {
            Map<Integer, Integer> map = new HashMap<>();

            scdGrpsSidMap__.put(grp, map);

            if (!sidItr.hasNext()) {
                return schCount;
            }
            map.put(grp.getScdUsrSid(), Math.toIntExact(sidItr.next()));
            if (users__.size() == 0) {
                continue;
            }
            for (int targetSid : users__) {
                if (!sidItr.hasNext()) {
                    return schCount;
                }
                map.put(targetSid, Math.toIntExact(sidItr.next()));
            }

        }
        return schCount;
    }
    /**
     * <br>[機  能] 必要なSIDの一括採番 スケジュールグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @return 採番数
     * @throws SQLException SQL実行時例外
     */
    private int __compileSaibanScheduleGroup(int sessionUsrSid) throws SQLException {
        int schCount = 0;
        if (oldSchGrpSid__ != GSConstSchedule.DF_SCHGP_ID) {
            scdGrpsTargetMap__.keySet().stream()
                        .forEach(grp -> {
                            grp.setScdGrpSid(oldSchGrpSid__);
                        });
            return schCount;
        }
        if (users__.size() > 0) {
            schCount = scdGrpsTargetMap__.size();
            Iterator<Long> sidItr = cntCon__.getSaibanNumbers(SaibanModel.SBNSID_SCHEDULE,
                    SaibanModel.SBNSID_SUB_SCH_GP, sessionUsrSid, schCount)
                    .iterator();
            scdGrpsTargetMap__.keySet().stream()
                        .forEach(grp -> {
                            if (!sidItr.hasNext()) {
                                return;
                            }
                            grp.setScdGrpSid(Math.toIntExact(sidItr.next()));
                        });
            return schCount;
        }
        scdGrpsTargetMap__.keySet().stream()
                    .forEach(grp -> {
                        grp.setScdGrpSid(-1);
                    });
        return schCount;

    }
    /**
     *
     * <br>[機  能] スケジュール施設予約紐づけSIDを採番
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param schResMap 採番済みスケジュール施設予約紐づけSID Map
     * @return 採番数
     * @throws SQLException SQL実行時例外
     *
     */
    private int __compileSaibanScheduleReserve(
            int sessionUsrSid,
            Map<String, Integer> schResMap) throws SQLException {
        int sidCount = 0;
        for (SchDataGroupModel grp : scdGrpsTargetMap__.keySet()) {
            Integer sid = schResMap.get(grp.getTargetDate().getDateString("/"));
            if (sid == null) {
                sidCount++;
                continue;
            }
            grp.setScdResSid(sid);
        }
        Iterator<Long> sidItr = cntCon__.getSaibanNumbers(SaibanModel.SBNSID_SCHEDULE,
                        SaibanModel.SBNSID_SUB_SCH_RES, sessionUsrSid, sidCount)
                        .iterator();
        for (SchDataGroupModel grp : scdGrpsTargetMap__.keySet()) {
            if (sidItr.hasNext() == false) {
                return sidCount;
            }
            if (!schResMap.containsKey(grp.getTargetDate().getDateString("/"))) {
                grp.setScdResSid(Math.toIntExact(sidItr.next()));
            }
        }
        return sidCount;

    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.biz.ISchRegister#regist()
     */
    @Override
    public void regist() throws SQLException {
        try {
            __registSchData();

            __registSchDataPub();

            __registSchBin();

            __registAddress();

            __registSchExData();
        } catch (SQLException e) {
            throw e;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            }
            throw e;
        }
    }
    /**
     *
     * <br>[機  能] SchDataModel,SchDataPushModelの登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __registSchData() throws SQLException {
        final List<SchDataModel> regSchList = new ArrayList<>();
        final IPushServiceOperator psOpe =
                PushServiceOperator.getInstance(con__, reqMdl__.getDomain());
        final SchCommonBiz cmnBiz = new SchCommonBiz(con__, reqMdl__);
        final SchDataDao dao = new SchDataDao(con__);
        final SchPushListDao pushListDao = new SchPushListDao(con__);
        //スケジュール 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                dao.insert(regSchList);
                pushListDao.insert(
                    regSchList.stream()
                        .flatMap(schMdl -> __createPushList(schMdl, cmnBiz).stream())
                        .collect(Collectors.toList())
                        );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            regSchList.clear();
        };




        boolean isHttps = false;
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        if (urlBiz.getScheme(reqMdl__).equals("https")) {
            isHttps = true;
        }
        try {
            for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp
                    : scdGrpsSidMap__.entrySet()) {
                SchDataGroupModel grp = entGrp.getKey();
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    SchDataModel regMdl = __createSchModel(grp, entry.getValue(), entry.getKey());

                    //出席確認設定
                    if (Objects.equals(baseSch__.getScdAttendKbn(),
                                        GSConstSchedule.ATTEND_KBN_YES)) {
                        regMdl.setScdEdit(GSConstSchedule.EDIT_CONF_OWN);
                    }
                    if (oldAttendMap__.containsKey(entry.getKey())) {
                        regMdl.setScdAttendAns(oldAttendMap__.get(entry.getKey()));
                    } else if (Objects.equals(entry.getKey(), baseSch__.getScdUsrSid())
                                && Objects.equals(
                                        baseSch__.getScdAttendKbn(),
                                        GSConstSchedule.ATTEND_KBN_YES)) {
                        regMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_YES);
                    } else {
                        regMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
                    }
                    regMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);
                    if (Objects.equals(entry.getKey(), baseSch__.getScdUsrSid())) {
                        regMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_YES);
                    }
                    if (oldAttendCommentMap__.containsKey(entry.getKey())) {
                        regMdl.setScdAttendComment(oldAttendCommentMap__.get(entry.getKey()));
                    }

                    //通知設定
                    SchDataPushModel pushInf =
                            __createScdPushInf(entry.getKey(), psOpe, isHttps, grp);

                    regMdl.setScdTargetGrp(pushInf.getScdTargetGrp());
                    regMdl.setScdReminder(pushInf.getScdReminder());

                    regSchList.add(regMdl);
                    if (regSchList.size() > 500) {
                        runner.run();
                    }
                }
            }
            runner.run();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            }
            throw e;
        }

    }
    /**
     * <br>[機  能] 登録用スケジュールモデルを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param grp スケジュール登録グループ
     * @param scdSid スケジュールSID
     * @param targetSid 登録先SID
     * @return 登録用スケジュールモデル
     */
    private SchDataModel __createSchModel(SchDataGroupModel grp,
            int scdSid, int targetSid) {
        SchDataModel regMdl = new SchDataModel();
        try {
            BeanUtils.copyProperties(regMdl, baseSch__);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        
        regMdl.setScdSid(scdSid);
        regMdl.setScdUsrSid(targetSid);
        regMdl.setScdUsrKbn(grp.getScdUsrKbn());
        
        if (grp.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP
                && regMdl.getScdPublic() != GSConstSchedule.DSP_PUBLIC) {
            regMdl.setScdPublic(GSConstSchedule.DSP_NOT_PUBLIC);
        }

        regMdl.setScdFrDate(grp.getFrom());
        regMdl.setScdToDate(grp.getTo());

        regMdl.setScdGrpSid(grp.getScdGrpSid());
        regMdl.setScdRsSid(grp.getScdResSid());
        regMdl.setSceSid(schExtSid__);
        return regMdl;
    }

    /**
     * <br>[機  能] スケジュール通知リストに設定するかをチェックし追加するモデルリストを返す
     * <br>[解  説] 現在時刻から現在時間 + 8日より前の予約情報である
     *              アプリまたはPCを通知対象にしている
     *              リマインダー通知時間を設定ししている
     *              ユーザSIDがadmin, システムメールではない
     * 上記3点に当てはまる場合のみ登録を行う
     * <br>[備  考]
     * @param model スケジュールモデル
     * @param cmnBiz スケジュール汎用ビジネスロジック
     * @return モデルリスト
     */
    private List<SchPushListModel> __createPushList(SchDataModel model, SchCommonBiz cmnBiz) {

        final UDate now = new UDate();
        final UDate after = now.cloneUDate();
        after.addDay(8);
        final UDate check = model.getScdFrDate();
        final List<SchPriPushModel> pushConfList = new ArrayList<>();
        if (model.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP
                && model.getScdTargetGrp() == GSConstSchedule.REMINDER_USE_YES) {
            Optional.ofNullable(usrBelongMap__.get(model.getScdUsrSid()))
            .stream()
            .flatMap(sids -> sids.stream())
            .map(sid -> priPushConfMap__.get(sid))
            .forEach(pushConf -> {
                pushConfList.add(pushConf);
            });
        }
        if (model.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            pushConfList.add(SchPriPushModel.getInstance(model));
        }
        return pushConfList.stream()
            .filter(priMdl -> cmnBiz.insertPushListCheck(check, priMdl))
            .map(priMdl -> {
                    SchPushListModel pushMdl = new SchPushListModel();

                    pushMdl.setScdSid(model.getScdSid());
                    pushMdl.setUsrSid(priMdl.getUsrSid());
                    UDate remDate = cmnBiz.getReminderDate(
                            check.cloneUDate(),
                            priMdl.getSccReminder());
                    pushMdl.setSplReminder(remDate);
                    pushMdl.setSplReminderKbn(priMdl.getSccReminder());

                return pushMdl;
            })
            .collect(Collectors.toList());


    }

    /**
     * <br>[機  能] スケジュール登録用 通知設定モデルを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid
     * @param psOpe
     * @param isHttps
     * @param grp
     * @return スケジュール登録用 通知設定モデル
     */
    private SchDataPushModel __createScdPushInf(final int usrSid,
            final IPushServiceOperator psOpe, boolean isHttps,
            SchDataGroupModel grp) {
        SchDataPushModel scdPush = new SchDataPushModel(baseSch__);
        int checkUsrSid = usrSid;
        if (grp.getScdUsrSid() != reqMdl__.getSmodel().getUsrsid()) {
            checkUsrSid = grp.getScdUsrSid();
        }

        SchEnumRemindMode remindMode;

        SchPriPushModel defConf = null;
        SchPriPushModel oldConf = oldPushMap__.get(usrSid);
        if (grp.getScdUsrKbn()
            == GSConstSchedule.USER_KBN_USER) {
            defConf =  priPushConfMap__.get(usrSid);
            remindMode = SchEnumRemindMode.valueOf(grp.getScdUsrKbn(),
                reqMdl__.getSmodel().getUsrsid(),
                checkUsrSid);
        } else {
            defConf = SchPriPushModel.getInstance(
                    new SchDataModel()
                    );
            remindMode = SchEnumRemindMode.GROUP;
        }

        SchRemindConfWriter.builder()
         .setDefConf(defConf)
         .setOldConf(oldConf)
         .setReminder(baseSch__.getScdReminder())
         .setRemindMode(remindMode)
         .setTargetGrp(baseSch__.getScdTargetGrp())
         .setTimeKbn(baseSch__.getScdDaily())
         .setPushUseable(psOpe.isUseable())
         .build().write(scdPush);
        return scdPush;
    }
    /**
     *
     * <br>[機  能] SchDataPubModelの登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __registSchDataPub() throws SQLException {
        final SchDataPubDao dao = new SchDataPubDao(con__);
        final List<SchDataPubModel> regList = new ArrayList<>();

        //スケジュール 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                dao.insert(regList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            regList.clear();
        };
        for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp : scdGrpsSidMap__.entrySet()) {
            if (entGrp.getKey().getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                continue;
            }
            for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                for (SchDataPubModel base : pubList__) {
                    SchDataPubModel regMdl = new SchDataPubModel();
                    try {
                        BeanUtils.copyProperties(regMdl, base);

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    regMdl.setScdSid(entry.getValue());
                    regList.add(regMdl);
                    if (regList.size() > 500) {
                        runner.run();
                    }
                }
            }
        }
        runner.run();

    }
    /**
     *
     * <br>[機  能] SchBinModelの登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __registSchBin() throws SQLException {
        final SchBinDao dao = new SchBinDao(con__);
        final List<SchBinModel> regList = new ArrayList<>();
        //スケジュール 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                dao.insert(regList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            regList.clear();
        };

        for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp : scdGrpsSidMap__.entrySet()) {
            for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                for (long binSid : binSidList__) {
                    SchBinModel regMdl = new SchBinModel();
                    regMdl.setScdSid(entry.getValue());
                    regMdl.setBinSid(binSid);
                    regList.add(regMdl);
                    if (regList.size() > 500) {
                        runner.run();
                    }
                }
            }
        }
        runner.run();

    }
    /**
     *
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __registAddress() throws SQLException {
        final int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        final Map<SchDataGroupModel, Map<Integer, Integer>> regGrpAdcMap = new HashMap<>();
        final Map<Integer, SchDataGroupModel> schGrpMap = new HashMap<>();


        final SchCompanyDao comDao = new SchCompanyDao(con__);
        final SchAddressDao adrDao = new SchAddressDao(con__);
        final AdrContactDao cntDao = new AdrContactDao(con__);
        final List<Integer> regList = new ArrayList<>();
        //スケジュールグループ 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                //会社情報Mappingを登録
                if (acoSidArr__ != null && acoSidArr__.length > 0) {
                    List<SchCompanyModel> comList = new ArrayList<>();

                    for (int scdSid : regList) {
                        for (int index = 0;
                                (index < acoSidArr__.length && index < abaSidArr__.length);
                                index++) {
                            SchCompanyModel mdl = new SchCompanyModel();
                            mdl.setAcoSid(Integer.parseInt(acoSidArr__[index]));
                            mdl.setAbaSid(Integer.parseInt(abaSidArr__[index]));
                            mdl.setScdSid(scdSid);
                            mdl.setSccAuid(baseSch__.getScdAuid());
                            mdl.setSccAdate(baseSch__.getScdAdate());
                            mdl.setSccEuid(baseSch__.getScdEuid());
                            mdl.setSccEdate(baseSch__.getScdEdate());
                            comList.add(mdl);
                        }
                    }
                    comDao.insert(comList);
                }
                //アドレス帳情報Mappingを登録する
                if (adrSidArr__ != null && adrSidArr__.length > 0) {
                    List<SchAddressModel> adrList = new ArrayList<>();
                    List<AdrContactModel> contactList = new ArrayList<>();

                    Iterator<Long> contactSidItr = new ArrayList<Long>().iterator();
                    Iterator<Long> contactGrpSidItr = new ArrayList<Long>().iterator();
                    if (isUseContact__) {
                        //コンタクト履歴SID採番
                        contactSidItr = cntCon__.getSaibanNumbers(
                                GSConst.SBNSID_ADDRESS,
                                GSConst.SBNSID_SUB_CONTACT,
                                sessionUsrSid,
                                regGrpAdcMap.size() * adrSidArr__.length)
                                .iterator();
                        //コンタクト履歴グループSID採番
                        if (adrSidArr__.length > 1) {
                            contactGrpSidItr = cntCon__.getSaibanNumbers(
                                    GSConst.SBNSID_ADDRESS,
                                    GSConst.SBNSID_SUB_CONTACT_GRP,
                                    sessionUsrSid,
                                    regGrpAdcMap.size())
                                    .iterator();
                        }
                    }
                    for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp : regGrpAdcMap.entrySet()) {
                        SchDataGroupModel grp = entGrp.getKey();
                        int adcGrpSid = -1;
                        if (contactGrpSidItr.hasNext()) {
                            adcGrpSid = Math.toIntExact(contactGrpSidItr.next());
                        }

                        for (String adrSid : adrSidArr__) {
                            AdrContactModel adcMdl = null;
                            int adcSid = 0;
                            if (contactSidItr.hasNext()) {
                                adcSid = Math.toIntExact(contactSidItr.next());
                                adcMdl = __createContactModel(adcSid, adcGrpSid,
                                        Integer.parseInt(adrSid), grp);
                                contactList.add(adcMdl);
                            }
                            for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                                int scdSid = entry.getValue();
                                SchAddressModel adrMdl = new SchAddressModel();
                                adrMdl.setAdrSid(Integer.parseInt(adrSid));
                                adrMdl.setScdSid(scdSid);
                                adrMdl.setAdcSid(adcSid);
                                adrMdl.setScaAuid(baseSch__.getScdAuid());
                                adrMdl.setScaAdate(baseSch__.getScdAdate());
                                adrMdl.setScaEuid(baseSch__.getScdEuid());
                                adrMdl.setScaEdate(baseSch__.getScdEdate());
                                adrList.add(adrMdl);

                            }

                        }
                    }


                    adrDao.insert(adrList);
                    cntDao.insert(contactList);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            regList.clear();
            regGrpAdcMap.clear();
        };

        for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp : scdGrpsSidMap__.entrySet()) {
            SchDataGroupModel grp = entGrp.getKey();
            regGrpAdcMap.put(grp, entGrp.getValue());

            for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                regList.add(entry.getValue());
                schGrpMap.put(entry.getValue(), grp);

                if (regGrpAdcMap.size() > 500) {
                    runner.run();
                }
            }
        }
        runner.run();


    }
    /**
     * <br>[機  能] コンタクト履歴を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param adcSid コンタクト SID
     * @param adcGrpSid コンタクトグループSID
     * @param adrSid アドレスSID
     * @param grp スケジュールグループ情報
     * @return コンタクト履歴
     */
    private AdrContactModel __createContactModel(int adcSid,
            int adcGrpSid, int adrSid, SchDataGroupModel grp) {


        AdrContactModel contactMdl = new AdrContactModel();
        contactMdl.setAdcSid(adcSid);
        contactMdl.setAdrSid(adrSid);
        contactMdl.setAdcTitle(baseSch__.getScdTitle());
        contactMdl.setAdcType(GSConst.CONTYP_MEETING);
        UDate from = grp.getFrom().cloneUDate();
        from.setZeroHhMmSs();
        UDate to = grp.getTo().cloneUDate();
        to.setZeroHhMmSs();
        if (baseSch__.getScdDaily() != GSConstSchedule.TIME_NOT_EXIST) {
            from.setHour(grp.getFrom().getIntHour());
            from.setMinute(grp.getFrom().getIntMinute());
            to.setHour(grp.getTo().getIntHour());
            to.setMinute(grp.getTo().getIntMinute());
        }
        if (baseSch__.getScdDaily() == GSConstSchedule.TIME_NOT_EXIST) {
            from.setHour(0);
            from.setMinute(0);
            to.setHour(23);
            to.setMinute(55);
        }
        contactMdl.setAdcCttime(from);
        contactMdl.setAdcCttimeTo(to);

        contactMdl.setAdcAuid(baseSch__.getScdEuid());
        contactMdl.setAdcAdate(baseSch__.getScdEdate());
        contactMdl.setAdcEuid(baseSch__.getScdEuid());
        contactMdl.setAdcEdate(baseSch__.getScdEdate());
        contactMdl.setAdcGrpSid(adcGrpSid);
        return contactMdl;
    }

    private void __registSchExData() throws SQLException {
        if (registKbn__ != EnumRegistKbn.KURIKAESI) {
            return;
        }
        SchExdataDao exDao = new SchExdataDao(con__);
        exDao.insert(exMdl__);
        SchExdataBinDao sbeDao = new SchExdataBinDao(con__);
        binSidList__.stream()
                    .map(binSid -> {
                        SchExdataBinModel sbeMdl = new SchExdataBinModel();
                        sbeMdl.setSceSid(schExtSid__);
                        sbeMdl.setBinSid(Math.toIntExact(binSid));
                        return sbeMdl;
                    })
                    .forEach(mdl -> {
                         try {
                            sbeDao.insert(mdl);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

        //公開対象の登録
        SchExdataPubDao sepDao = new SchExdataPubDao(con__);
        pubList__.stream()
                    .map(pub -> {
                        SchExdataPubModel sepMdl = new SchExdataPubModel();
                        sepMdl.setSceSid(schExtSid__);
                        sepMdl.setSepPsid(pub.getSdpPsid());
                        sepMdl.setSepType(pub.getSdpType());
                        return sepMdl;
                    })
                    .forEach(mdl -> {
                         try {
                            sepDao.insert(mdl);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

        //スケジュール拡張情報-会社情報Mappingを登録
        //会社情報Mappingを登録
        if (acoSidArr__ != null && acoSidArr__.length > 0) {
            SchExcompanyModel exCompanyModel = new SchExcompanyModel();
            exCompanyModel.setSceSid(schExtSid__);
            exCompanyModel.setSccAuid(exMdl__.getSceAuid());
            exCompanyModel.setSccAdate(exMdl__.getSceAdate());
            exCompanyModel.setSccEuid(exMdl__.getSceEuid());
            exCompanyModel.setSccEdate(exMdl__.getSceEdate());

            SchExcompanyDao exCompanyDao = new SchExcompanyDao(con__);
            for (int index = 0;
                    (index < acoSidArr__.length && index < abaSidArr__.length);
                    index++) {
                exCompanyModel.setAcoSid(Integer.parseInt(acoSidArr__[index]));
                exCompanyModel.setAbaSid(Integer.parseInt(abaSidArr__[index]));
                exCompanyDao.insert(exCompanyModel);
            }
        }

        //アドレス帳情報Mappingを登録する
        if (adrSidArr__ != null && adrSidArr__.length > 0) {

            SchExaddressModel exAdrMdl = new SchExaddressModel();
            exAdrMdl.setSceSid(schExtSid__);
            exAdrMdl.setSeaAuid(exMdl__.getSceAuid());
            exAdrMdl.setSeaAdate(exMdl__.getSceAdate());
            exAdrMdl.setSeaEuid(exMdl__.getSceEuid());
            exAdrMdl.setSeaEdate(exMdl__.getSceEdate());

            SchExaddressDao exAddressDao = new SchExaddressDao(con__);
            for (String adrSid : adrSidArr__) {
                exAdrMdl.setAdrSid(Integer.parseInt(adrSid));
                exAddressDao.insert(exAdrMdl);
            }
        }

    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.biz.ISchRegister#getRsvExtSid()
     */
    @Override
    public int getRsvExtSid() {
        return rsvExtSid__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.biz.ISchRegister#getScdGrpsList()
     */
    @Override
    public List<SchDataGroupModel> getScdGrpsList() {
         return scdGrpsTargetMap__.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public Map<SchDataGroupModel, Map<Integer, Integer>> getScdSidMap() {
        return scdGrpsSidMap__;
    }
    /**
     *
     * <br>[機  能] スケジュールモデルを取得する
     * <br>[解  説] 引数とベーススケジュールを基に生成して返す
     * <br>[備  考] push通知情報、出席確認情報は反映されていない
     * @param grp スケジュール登録グループ
     * @param targetSid スケジュール登録対象SID
     * @return スケジュールモデル
     */
    @Override
    public SchDataModel getSchModel(SchDataGroupModel grp, int targetSid) {
        int scdSid = Optional.ofNullable(scdGrpsSidMap__.get(grp))
                        .map(map -> map.get(targetSid))
                        .orElseThrow();

        return __createSchModel(grp, scdSid, targetSid);
    }

    /**
     *
     * <br>[機  能] ビルダー
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder implements ISchRegister.Builder {
        /** コネクション */
        private Connection con__;
        /** リクエストモデル */
        private RequestModel reqMdl__;
        /** 採番コントローラ */
        private MlCountMtController cntCon__;

        /** 登録方法区分 */
        private EnumRegistKbn registKbn__;

        /** スケジュール情報 同時登録するスケジュールで共有*/
        private SchDataModel baseSch__;
        /** スケジュール登録対象のユーザSIDセット */
        private Set<Integer> users__ = new HashSet<>();
        /** 施設予約同時登録の有無 */
        private boolean isUseRsv__ = false;
        /** コンタクト履歴登録の有無 */
        private boolean isUseContact__ = false;
        /** スケジュール拡張登録モデル*/
        private SchExdataModel exMdl__;

        /** 添付ファイルバイナリSID 同時登録するスケジュールで共有*/
        private List<Long> binSidList__ = new ArrayList<>();
        /** 指定公開対象 SchDataPubModel.scdSidは登録時に使用されない */
        private List<SchDataPubModel> pubList__  = new ArrayList<>();
        /** 会社SID 同時登録するスケジュールで共有*/
        private String[] acoSidArr__;
        /** 会社拠点SID 同時登録するスケジュールで共有*/
        private String[] abaSidArr__;
        /** アドレス情報SID 同時登録するスケジュールで共有*/
        private String[] adrSidArr__;

        /** スケジュール同時登録情報 List*/
        private final List<SchDataGroupModel> scdGrpsList__ = new ArrayList<>();

        /** 更新時の引継ぎ用 出欠確認マップ ユーザSIDがキー 、繰り返し登録時は使用しない*/
        private Map<Integer, Integer> oldAttendMap__ = new HashMap<>();
        /** 更新時の引継ぎ用 出欠確認コメントマップ ユーザSIDがキー 、繰り返し登録時は使用しない*/
        private Map<Integer, String> oldAttendCommentMap__ = new HashMap<>();
        /** 更新時の引継ぎ用 通知設定マップ ユーザSIDがキー 、繰り返し登録時は使用しない*/
        private Map<? extends Integer, ? extends SchPriPushModel> oldPushMap__ = new HashMap<>();
        /** 更新時の引継ぎ用 スケジュールグループSID*/
        private int oldSchGrpSid__ = GSConstSchedule.DF_SCHGP_ID;
        /** スケジュール繰り返し登録拡張SID  同時登録するスケジュールで共有*/
        private int schExtSid__ = GSConstSchedule.DF_SCHGP_ID;
        /** 施設予約繰り返し登録拡張SID  同時登録するスケジュールで共有*/
        private int rsvExtSid__ = GSConstSchedule.DF_SCHGP_ID;
        /** 施設予約スケジュール紐づけSID  同時登録するスケジュールで共有*/
        private Map<String, Integer> schResSidMap__ = new HashMap<>();

        /**
         *
         * コンストラクタ
         * @param con コネクション
         * @param reqMdl リクエストモデル
         * @param cntCon 採番コントローラ
         */
        private Builder(Connection con, RequestModel reqMdl,
                MlCountMtController cntCon) {
            con__ = con;
            reqMdl__ = reqMdl;
            cntCon__ = cntCon;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setCon(java.sql.Connection)
         */
        @Override
        public Builder setCon(Connection con) {
            con__ = con;
            return this;
        }
        /* (非 Javadoc)
         */
        @Override
        public Builder setReqMdl(RequestModel reqMdl) {
            reqMdl__ = reqMdl;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setBinSidList(java.util.List)
         */
        @Override
        public Builder setBinSidList(List<Long> binSidList) {
            binSidList__ = binSidList;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setPubList(java.util.List)
         */
        @Override
        public Builder setPubList(List<SchDataPubModel> pubList) {
            pubList__ = pubList;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setAcoSidArr(java.lang.String[])
         */
        @Override
        public Builder setAcoSidArr(String[] acoSidArr) {
            acoSidArr__ = acoSidArr;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setAbaSidArr(java.lang.String[])
         */
        @Override
        public Builder setAbaSidArr(String[] abaSidArr) {
            abaSidArr__ = abaSidArr;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setAdrSidArr(java.lang.String[])
         */
        @Override
        public Builder setAdrSidArr(String[] adrSidArr) {
            adrSidArr__ = adrSidArr;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setUsers(java.util.Set)
         */
        @Override
        public Builder setUsers(Set<Integer> users) {
            users__ = users;
            return this;
        }

        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setOldAttendMap(java.util.Map)
         */
        @Override
        public Builder setOldAttendMap(Map<Integer, Integer> oldAttendMap) {
            oldAttendMap__ = oldAttendMap;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setOldAttendCommentMap(java.util.Map)
         */
        @Override
        public Builder setOldAttendCommentMap(Map<Integer, String> oldAttendCommentMap) {
            oldAttendCommentMap__ = oldAttendCommentMap;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setOldPushMap(java.util.Map)
         */
        @Override
        public Builder setOldPushMap(Map<Integer, SchPriPushModel> oldPushMap) {
            oldPushMap__ = oldPushMap;
            return this;
        }
        /**
         * <p>oldSchGrpSid をセットします。
         * @param oldSchGrpSid oldSchGrpSid
         * @return this
         * @see jp.groupsession.v2.sch.biz.SchRegister.Builder#oldSchGrpSid__
         */
        @Override
        public Builder setOldSchGrpSid(int oldSchGrpSid) {
            oldSchGrpSid__ = oldSchGrpSid;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setSchExtSid(int)
         */
        @Override
        public Builder setSchExtSid(int schExtSid) {
            schExtSid__ = schExtSid;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setRsvExtSid(int)
         */
        @Override
        public  Builder setRsvExtSid(int rsvExtSid) {
            rsvExtSid__ = rsvExtSid;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setUseRsv(boolean)
         */
        @Override
        public  Builder setUseRsv(boolean isUseRsv) {
            isUseRsv__ = isUseRsv;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setUseContact(boolean)
         */
        @Override
        public Builder setUseContact(boolean isUseContact) {
            isUseContact__ = isUseContact;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#setSchResSidMap(java.util.Map)
         */
        @Override
        public Builder setSchResSidMap(Map<String, Integer> schResSidMap) {
            schResSidMap__ = schResSidMap;
            return this;
        }
        /* (非 Javadoc)
         * @see jp.groupsession.v2.sch.biz.SchRegisterBuilder#build()
         */
        @Override
        public SchRegister build() throws SQLException {
            return new SchRegister(this);
        }

    }
    /**
     *
     * <br>[機  能] 列挙型 登録方法区分
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private enum EnumRegistKbn {
        /** 単体登録*/
        SIMPLE,
        /** 繰り返し登録*/
        KURIKAESI,
        /** 一括登録*/
        IKKATU
    }


}
