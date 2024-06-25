package jp.groupsession.v2.rsv.biz;

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

import org.apache.commons.beanutils.BeanUtils;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvExdataPubModel;
import jp.groupsession.v2.rsv.model.RsvSisKryrkModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.RsvYrkGroupModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;

public class RsvYoyakuRegister implements IRsvYoyakuRegister {

    /** コネクション */
    private final Connection con__;
    /** リクエストモデル */
    private final RequestModel reqMdl__;
    /** 採番コントローラ */
    private final MlCountMtController cntCon__;
    /** アプリケーションパス*/
    private final String appRootPath__;

    /** 登録方法区分 */
    private final EnumRegistKbn registKbn__;
    /** 施設予約情報 同時登録する施設予約で共有*/
    private final RsvSisYrkModel baseYrk__;
    /** 指定公開対象 RsvDataPubModel.rsySidは登録時に使用しない */
    private final List<RsvDataPubModel> pubList__ = new ArrayList<>();

    /** 施設予約同時登録グループ 対象施設SIDマップ*/
    private final Map<RsvYrkGroupModel, Set<Integer>> rsyGrpsTargetMap__ = new HashMap<>();
    /** 施設予約同時登録グループ 施設予約SIDマップ*/
    private final Map<RsvYrkGroupModel, Map<Integer, Integer>> rsyGrpsSidMap__ = new HashMap<>();
    /** 施設予約同時登録 施設SID*/
    private final Set<Integer> rsdSids__ = new HashSet<>();
    /** 登録時 承認マップ*/
    private final Map<Integer, RsyApprModel> rsdApprMap__ = new HashMap<>();

    /** 施設予約区分別情報Map 施設SIDがキー*/
    private final Map<Integer, RsvSisKyrkModel> kyrkMap__ = new HashMap<>();

    /** 施設予約繰り返し登録拡張SID  同時登W録するスケジュールで共有*/
    private int rsvExtSid__ = -1;

    /** 施設予約繰り返し登録拡張モデル  同時登W録するスケジュールで共有*/
    private final RsvSisRyrkModel rsvExtModel__;
    /** スケジュール連携フラグ*/
    private final boolean isUseSch__;

    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param appRootPath アプリケーションパス
     * @param baseYrk スケジュールモデル
     * @return ビルダー
     * @throws SQLException SQL実行時例外
     */
    public static Builder simpleRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            String appRootPath,
            RsvSisYrkModel baseYrk
            ) throws SQLException {
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        Builder ret = new Builder(con, reqMdl, cntCon, appRootPath);
        ret.registKbn__ = EnumRegistKbn.SIMPLE;
        ret.baseYrk__ = baseYrk;

        //施設予約承認初期値を設定
        rsvCmnBiz.setSisYrkApprData(
                con, baseYrk.getRsdSid(), baseYrk, reqMdl.getSmodel().getUsrsid());
        ret.rsdApprMap__.put(baseYrk.getRsdSid(), new RsyApprModel(baseYrk));

        RsvYrkGroupModel grp = new RsvYrkGroupModel(baseYrk);
        ret.rsyGrpsTargetMap__.put(grp, new HashSet<>(Set.of(grp.getRsdSid())));
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
     * @param appRootPath アプリケーションパス
     * @param baseYrk スケジュールモデル
     * @param dateList 対象日
     * @param exMdl 繰り返し登録モデル
     * @return ビルダー
     * @throws SQLException SQL実行時例外
     */
    public static Builder kurikaesiRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            String appRootPath,
            RsvSisYrkModel baseYrk,
            List<UDate> dateList,
            RsvSisRyrkModel exMdl
            ) throws SQLException {
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        Builder ret = new Builder(con, reqMdl, cntCon, appRootPath);
        ret.registKbn__ = EnumRegistKbn.KURIKAESI;
        ret.baseYrk__ = baseYrk;

        //施設予約承認初期値を設定
        rsvCmnBiz.setSisYrkApprData(
                con, baseYrk.getRsdSid(), baseYrk, reqMdl.getSmodel().getUsrsid());
        ret.rsdApprMap__.put(baseYrk.getRsdSid(), new RsyApprModel(baseYrk));

        Set<Integer> rsdSids = new HashSet<>(Set.of(baseYrk.getRsdSid()));
        for (UDate date : dateList) {
            RsvYrkGroupModel grp = new RsvYrkGroupModel(baseYrk);
            grp.setTargetDate(date);
            UDate from = baseYrk.getRsyFrDate().cloneUDate();
            UDate to = baseYrk.getRsyToDate().cloneUDate();
            from.setDate(date.getDateString());
            to.setDate(date.getDateString());
            grp.setFrom(from);
            grp.setTo(to);
            ret.rsyGrpsTargetMap__.put(grp, rsdSids);
        }
        ret.rsvExtModel__ = exMdl;
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
     * @param appRootPath アプリケーションパス
     * @param baseYrk スケジュールモデル
     * @param ikkatuKeyList 一括登録キーリスト
     * @return ビルダー
     * @throws SQLException SQL実行時例外
     */
    public static Builder ikkatuRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            String appRootPath,
            RsvSisYrkModel baseYrk,
            List<String> ikkatuKeyList
            ) throws SQLException {
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        Builder ret = new Builder(con, reqMdl, cntCon, appRootPath);
        ret.registKbn__ = EnumRegistKbn.IKKATU;
        ret.baseYrk__ = baseYrk;
        Map<String, RsvYrkGroupModel> grpMap = new HashMap<>();

        for (String torokuKey : ikkatuKeyList) {
            String dateStr = torokuKey.substring(0, torokuKey.indexOf("-"));
            String sidStr = torokuKey.substring(torokuKey.indexOf("-") + 1);
            int rsdSid = Integer.parseInt(sidStr);

            RsvYrkGroupModel grp = grpMap.get(dateStr);
            if (!ret.rsdApprMap__.containsKey(rsdSid)) {
                //施設予約承認初期値を設定
                rsvCmnBiz.setSisYrkApprData(
                        con, rsdSid, baseYrk, reqMdl.getSmodel().getUsrsid());
                ret.rsdApprMap__.put(rsdSid, new RsyApprModel(baseYrk));
            }

            if (grp != null) {
                ret.rsyGrpsTargetMap__.get(grp).add(rsdSid);
                continue;
            }

            UDate date = UDate.getInstanceStr(dateStr);
            grp = new RsvYrkGroupModel(baseYrk);
            grp.setTargetDate(date);
            UDate from = baseYrk.getRsyFrDate().cloneUDate();
            UDate to = baseYrk.getRsyToDate().cloneUDate();
            from.setDate(date.getDateString());
            to.setDate(date.getDateString());
            grp.setFrom(from);
            grp.setTo(to);


            grp.setRsdSid(rsdSid);
            ret.rsyGrpsTargetMap__.put(grp, new HashSet<>(Set.of(grp.getRsdSid())));
            grpMap.put(dateStr, grp);

        }

        return ret;
    }
    /**
     *
     * コンストラクタ
     * @param builder ビルダー
     * @throws SQLException SQL実行時例外
     */
    private RsvYoyakuRegister(Builder builder) throws SQLException {
        super();
        this.con__ = builder.con__;
        this.reqMdl__ = builder.reqMdl__;
        this.cntCon__ = builder.cntCon__;
        this.registKbn__ = builder.registKbn__;
        this.baseYrk__ = builder.baseYrk__;

        this.rsvExtModel__ = builder.rsvExtModel__;
        this.isUseSch__ = builder.isUseSch__;

        Optional.ofNullable(builder.pubList__)
            .ifPresent(pubList -> this.pubList__.addAll(pubList));

        Optional.ofNullable(builder.rsyGrpsTargetMap__)
            .ifPresent(rsyGrpsTargetMap -> this.rsyGrpsTargetMap__.putAll(rsyGrpsTargetMap));

        Optional.ofNullable(builder.kyrkMap__)
            .ifPresent(kyrkMap -> this.kyrkMap__.putAll(kyrkMap));

        if (registKbn__ != EnumRegistKbn.IKKATU) {
            this.rsdSids__.addAll(builder.rsdSids__
                    .stream()
                    //同時登録とメイン登録の重複を除外
                    .filter(sid -> Objects.equals(sid, baseYrk__.getRsdSid()) == false)
                    .collect(Collectors.toSet())
                    );
        }

        this.appRootPath__ = builder.appRootPath__;

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        //必要なSIDの一括採番


        //・施設予約SID
        __compileSaibanYoyaku(sessionUsrSid);


        //・施設予約拡張登録紐づけSID
        if (registKbn__ == EnumRegistKbn.KURIKAESI
                && rsvExtSid__ == -1) {
            rsvExtSid__ = (int) cntCon__.getSaibanNumber(
                    GSConstReserve.SBNSID_RESERVE,
                    GSConstReserve.SBNSID_SUB_KAKUTYO,
                    sessionUsrSid);
            Optional.ofNullable(rsvExtModel__)
            .ifPresent(exm -> exm.setRsrRsid(rsvExtSid__));
        }
        if (isUseSch__) {
            __compileSaibanScheduleReserve(
                    sessionUsrSid,
                    builder.schResSidMap__);
        }
        //施設予約区分別情報（デフォルト値の取得）
        __compileKyrkMap();


    }
    /**
     *
     * <br>[機  能] 施設予約SIDの一括採番
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @return 採番数
     * @throws SQLException SQL実行時例外
     */
    private int __compileSaibanYoyaku(int sessionUsrSid) throws SQLException {
        int count = 0;

        count = Math.toIntExact(rsyGrpsTargetMap__.values().stream()
                    .flatMap(grp -> grp.stream())
                    .count());
        if (registKbn__ != EnumRegistKbn.IKKATU) {
            count += rsyGrpsTargetMap__.size() * rsdSids__.size();
        }

        Iterator<Long> sidItr = cntCon__.getSaibanNumbers(
                GSConstReserve.SBNSID_RESERVE,
                GSConstReserve.SBNSID_SUB_YOYAKU,
                sessionUsrSid,
                count)
                .iterator();
        for (Entry<RsvYrkGroupModel, Set<Integer>> entGrp : rsyGrpsTargetMap__.entrySet()) {

            if (!sidItr.hasNext()) {
                return count;
            }
            Map<Integer, Integer> map = new HashMap<>();
            rsyGrpsSidMap__.put(entGrp.getKey(), map);

            for (int targetSid : entGrp.getValue()) {
                if (!sidItr.hasNext()) {
                    return count;
                }
                map.put(targetSid, Math.toIntExact(sidItr.next()));
            }
            if (registKbn__ == EnumRegistKbn.IKKATU) {
                continue;
            }
            for (int targetSid : rsdSids__) {
                if (!sidItr.hasNext()) {
                    return count;
                }
                map.put(targetSid, Math.toIntExact(sidItr.next()));
            }
        }
        return count;

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
        for (RsvYrkGroupModel grp : rsyGrpsTargetMap__.keySet()) {
            Integer sid = schResMap.get(grp.getTargetDate().getDateString("/"));
            if (sid == null) {
                sidCount++;
                continue;
            }
            grp.setScdRsSid(sid);
        }
        Iterator<Long> sidItr = cntCon__.getSaibanNumbers(SaibanModel.SBNSID_SCHEDULE,
                        SaibanModel.SBNSID_SUB_SCH_RES, sessionUsrSid, sidCount)
                        .iterator();
        for (RsvYrkGroupModel grp : rsyGrpsTargetMap__.keySet()) {
            if (sidItr.hasNext() == false) {
                return sidCount;
            }
            if (!schResMap.containsKey(grp.getTargetDate().getDateString("/"))) {
                grp.setScdRsSid(Math.toIntExact(sidItr.next()));
            }
        }
        return sidCount;

    }
    /**
     *
     * <br>[機  能] 施設予約区分別情報設定の生成
     * <br>[解  説] 区分設定をしていない施設のデフォルト値の取得
     * <br>[備  考]
     * @throws SQLException
     */
    private void __compileKyrkMap() throws SQLException {

        RsvSisDataDao dataDao = new RsvSisDataDao(con__);
        Set<Integer> rsdSids = rsyGrpsTargetMap__.values().stream()
                    .flatMap(grp -> grp.stream())
                    .filter(rsdSid -> kyrkMap__.containsKey(rsdSid) == false)
                    .collect(Collectors.toSet());

        for (int rsdSid : rsdSids) {
            if (kyrkMap__.containsKey(rsdSid)) {
                continue;
            }
            Rsv070Model mdl = dataDao.getPopUpSisetuData(rsdSid);
            if (mdl != null) {
                if (RsvCommonBiz.isRskKbnRegCheck(mdl.getRskSid())) {
                    RsvCommonBiz rsvBiz = new RsvCommonBiz();
                    RsvSisKyrkModel kyrkMdl =
                            rsvBiz.getSisKbnInitData(
                                    con__, reqMdl__, mdl.getRskSid(), appRootPath__);
                    kyrkMap__.put(rsdSid, kyrkMdl);
                }
            }

        }

    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister#regist()
     */
    @Override
    public void regist() throws SQLException {
        try {
            __registRsyData();

            __registRskData();

            __registRsyDataPub();

            __registRsyExData();
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
     * <br>[機  能] 施設予約データ登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __registRsyData() throws SQLException {
        final List<RsvSisYrkModel> regList = new ArrayList<>();

        RsvSisYrkDao dao = new RsvSisYrkDao(con__);
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();

        //施設予約 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                dao.insert(regList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            regList.clear();
        };

        try {
            for (Entry<RsvYrkGroupModel, Map<Integer, Integer>> entGrp
                    : rsyGrpsSidMap__.entrySet()) {
                RsvYrkGroupModel grp = entGrp.getKey();
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    RsvSisYrkModel regMdl = new RsvSisYrkModel();
                    try {
                        BeanUtils.copyProperties(regMdl, baseYrk__);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    Optional.ofNullable(rsdApprMap__.get(regMdl.getRsdSid()))
                        .ifPresent(apprMdl -> {
                            regMdl.setRsyApprKbn(apprMdl.getRsyApprKbn());
                            regMdl.setRsyApprStatus(apprMdl.getRsyApprStatus());
                        });
                    regMdl.setRsySid(entry.getValue());
                    regMdl.setRsdSid(entry.getKey());

                    regMdl.setRsyFrDate(grp.getFrom());
                    regMdl.setRsyToDate(grp.getTo());

                    regMdl.setScdRsSid(grp.getScdRsSid());
                    regMdl.setRsrRsid(rsvExtSid__);

                    rsvCmnBiz.setSisYrkApprData(
                            con__,
                            regMdl.getRsdSid(),
                            regMdl,
                            baseYrk__.getRsyEuid());

                    regList.add(regMdl);
                    if (regList.size() > 500) {
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
     *
     * <br>[機  能] 施設予約区分別データ登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __registRskData() {
        final List<RsvSisKyrkModel> regList = new ArrayList<>();

        RsvSisKyrkDao dao = new RsvSisKyrkDao(con__);
        //施設予約 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                dao.insert(regList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            regList.clear();
        };

        for (Entry<RsvYrkGroupModel, Map<Integer, Integer>> entGrp
                : rsyGrpsSidMap__.entrySet()) {
            for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                if (kyrkMap__.containsKey(entry.getKey())) {
                    RsvSisKyrkModel regMdl = new RsvSisKyrkModel();
                    try {
                        BeanUtils.copyProperties(regMdl, kyrkMap__.get(entry.getKey()));
                    } catch (IllegalAccessException
                            | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    regMdl.setRsySid(entry.getValue());
                    regMdl.setRkyAuid(baseYrk__.getRsyAuid());
                    regMdl.setRkyAdate(baseYrk__.getRsyAdate());
                    regMdl.setRkyEuid(baseYrk__.getRsyEuid());
                    regMdl.setRkyEdate(baseYrk__.getRsyAdate());
                    regList.add(regMdl);
                }

                if (regList.size() > 500) {
                    runner.run();
                }
            }
        }
        runner.run();
    }

    /**
     *
     * <br>[機  能] 施設予約公開データ登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __registRsyDataPub() throws SQLException {
        final List<RsvDataPubModel> regList = new ArrayList<>();

        RsvDataPubDao dao = new RsvDataPubDao(con__);
        //施設予約 500件ごとの実行関数を設定
        Runnable runner = () -> {
            try {
                dao.insert(regList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            regList.clear();
        };

        try {
            for (Entry<RsvYrkGroupModel, Map<Integer, Integer>> entGrp
                    : rsyGrpsSidMap__.entrySet()) {
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    for (RsvDataPubModel base : pubList__) {
                        RsvDataPubModel regMdl = new RsvDataPubModel();
                        try {
                            BeanUtils.copyProperties(regMdl, base);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        regMdl.setRsySid(entry.getValue());
                        regList.add(regMdl);
                        if (regList.size() > 500) {
                            runner.run();
                        }
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
     *
     * <br>[機  能] 施設予約繰り返しデータ登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __registRsyExData() throws SQLException {
        if (registKbn__ != EnumRegistKbn.KURIKAESI) {
            return;
        }
        RsvSisRyrkDao exDao = new RsvSisRyrkDao(con__);
        exDao.insert(rsvExtModel__);

        //公開対象の登録
        RsvExdataPubDao repDao = new RsvExdataPubDao(con__);
        pubList__.stream()
                    .map(pub -> {
                        RsvExdataPubModel repMdl = new RsvExdataPubModel();
                        repMdl.setRsrRsid(rsvExtModel__.getRsrRsid());
                        repMdl.setRepPsid(pub.getRdpPsid());
                        repMdl.setRepType(pub.getRdpType());
                        return repMdl;
                    })
                    .forEach(mdl -> {
                         try {
                            repDao.insert(mdl);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

        //施設区分別情報の登録
        if (kyrkMap__.containsKey(baseYrk__.getRsdSid())) {
            RsvSisKryrkDao rkrDao = new RsvSisKryrkDao(con__);
            RsvSisKyrkModel baseKyrk = kyrkMap__.get(baseYrk__.getRsdSid());
            RsvSisKryrkModel rkrMdl = new RsvSisKryrkModel();
            rkrMdl.setRsrRsid(rsvExtModel__.getRsrRsid());
            rkrMdl.setRkrBusyo(baseKyrk.getRkyBusyo());
            rkrMdl.setRkrName(baseKyrk.getRkyName());
            rkrMdl.setRkrNum(baseKyrk.getRkyNum());
            rkrMdl.setRkrUseKbn(baseKyrk.getRkyUseKbn());
            rkrMdl.setRkrContact(baseKyrk.getRkyContact());
            rkrMdl.setRkrGuide(baseKyrk.getRkyGuide());
            rkrMdl.setRkrParkNum(baseKyrk.getRkyParkNum());
            rkrMdl.setRkrPrintKbn(baseKyrk.getRkyPrintKbn());
            rkrMdl.setRkrDest(baseKyrk.getRkyDest());
            rkrMdl.setRkrAuid(baseYrk__.getRsyAuid());
            rkrMdl.setRkrAdate(baseYrk__.getRsyAdate());
            rkrMdl.setRkrEuid(baseYrk__.getRsyEuid());
            rkrMdl.setRkrEdate(baseYrk__.getRsyEdate());
            rkrDao.insert(rkrMdl);
        }

    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister#getRsvExtSid()
     */
    @Override
    public int getRsvExtSid() {
        return rsvExtSid__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister#getRsyGrpsList()
     */
    @Override
    public List<RsvYrkGroupModel> getRsyGrpsList() {
         return rsyGrpsSidMap__.keySet().stream().collect(Collectors.toList());
    }
    @Override
    public Map<RsvYrkGroupModel, Map<Integer, Integer>> getRsySidMap() {
        return rsyGrpsSidMap__;
    }


    /**
     *
     * <br>[機  能] ビルダー
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder implements IRsvYoyakuRegister.Builder {
        /** コネクション */
        private final Connection con__;
        /** リクエストモデル */
        private final RequestModel reqMdl__;
        /** 採番コントローラ */
        private final MlCountMtController cntCon__;
        /** アプリケーションルートパス */
        private final String appRootPath__;
        /** 登録方法区分 */
        private EnumRegistKbn registKbn__;
        /** 施設予約情報 同時登録する施設予約で共有*/
        private RsvSisYrkModel baseYrk__;
        /** 指定公開対象 RsvDataPubModel.rsySidは登録時に使用しない */
        private List<RsvDataPubModel> pubList__;

        /** 施設予約同時登録 施設SID*/
        private Set<Integer> rsdSids__ = new HashSet<>();
        /** 施設予約同時登録グループ 対象施設SIDマップ*/
        private final Map<RsvYrkGroupModel, Set<Integer>> rsyGrpsTargetMap__ = new HashMap<>();
        /** 登録時 承認マップ*/
        private final Map<Integer, RsyApprModel> rsdApprMap__ = new HashMap<>();
        /** 施設予約区分別情報Map*/
        private Map<Integer, RsvSisKyrkModel> kyrkMap__ = new HashMap<>();

        /** 施設予約繰り返し登録拡張モデル  同時登録するスケジュールで共有*/
        private RsvSisRyrkModel rsvExtModel__;
        /** 施設予約スケジュール紐づけSID  同時登録するスケジュールで共有*/
        private Map<String, Integer> schResSidMap__ = new HashMap<>();

        /** スケジュール連携フラグ*/
        private boolean isUseSch__;

        /**
         *
         * コンストラクタ
         * @param con
         * @param reqMdl
         * @param cntCon
         * @param appRootPath
         */
        private Builder(Connection con, RequestModel reqMdl,
                MlCountMtController cntCon, String appRootPath) {
            con__ = con;
            reqMdl__ = reqMdl;
            cntCon__ = cntCon;
            appRootPath__ = appRootPath;
        }


        @Override
        public Builder setPubList(List<RsvDataPubModel> pubList) {
            pubList__ = pubList;
            return this;
        }

        @Override
        public Builder setSchResSidMap(Map<String, Integer> schResSidMap) {
            schResSidMap__ = schResSidMap;
            return this;
        }

        @Override
        public Builder setKyrkMap(Map<Integer, RsvSisKyrkModel> kyrkMap) {
            kyrkMap__ = kyrkMap;
            return this;
        }


        @Override
        public Builder setUseSch(boolean isUseSch) {
            isUseSch__ = isUseSch;
            return this;
        }

        @Override
        public Builder setRsdSids(Set<Integer> rsdSids) {
            rsdSids__ = rsdSids;
            return this;
        }

        @Override
        public RsvYoyakuRegister build() throws SQLException {
            return new RsvYoyakuRegister(this);
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
    /**
     *
     * <br>[機  能] 登録時 承認設定モデル
     * <br>[解  説] 施設毎の初期承認設定格納モデル
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class RsyApprModel {
        /** RSY_APPR_STATUS mapping */
        private final int rsyApprStatus__;
        /** RSY_APPR_KBN mapping */
        private final int rsyApprKbn__;
        public RsyApprModel(RsvSisYrkModel baseYrk) {
            rsyApprStatus__ = baseYrk.getRsyApprStatus();
            rsyApprKbn__ = baseYrk.getRsyApprKbn();
        }
        public int getRsyApprStatus() {
            return rsyApprStatus__;
        }
        public int getRsyApprKbn() {
            return rsyApprKbn__;
        }

    }


}
