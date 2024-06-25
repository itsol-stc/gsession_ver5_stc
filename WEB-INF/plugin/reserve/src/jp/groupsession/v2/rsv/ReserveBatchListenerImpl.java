package jp.groupsession.v2.rsv;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvDatausedSumDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvDatausedSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 施設予約についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ReserveBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ReserveBatchListenerImpl.class);

    /**
     * <br>[機  能] 日次バッチ起動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        log__.debug("施設予約バッチ処理開始");
        String pluginName = "施設予約";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);

            //施設予約管理者設定を取得
            RsvAdmConfDao admDao = new RsvAdmConfDao(con);
            RsvAdmConfModel admMdl = admDao.select();
            if (admMdl != null && admMdl.getRacAdlKbn() != GSConstReserve.RSV_RADIO_OFF) {
                int adlYear = admMdl.getRacAdlYear();
                int adlMonth = admMdl.getRacAdlMonth();

                //削除する境界の日付を設定する
                UDate delUd = new UDate();
                log__.debug("現在日 = " + delUd.getDateString());
                log__.debug("削除条件 経過年 = " + adlYear);
                log__.debug("削除条件 経過年 = " + adlMonth);

                delUd.addYear((adlYear * -1));
                delUd.addMonth((adlMonth * -1));
                delUd.setHour(GSConstReserve.DAY_END_HOUR);
                delUd.setMinute(GSConstReserve.DAY_END_MINUTES);
                delUd.setSecond(GSConstReserve.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

                log__.debug("削除境界線(これ以前のデータを削除) = " + delUd.getTimeStamp());

                ArrayList<Integer> deleteSidList = yrkDao.getRsySidsDeleteForAdmin(delUd);
                yrkDao.deleteForAdmin(delUd);

                RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con);
                kyrkDao.delete(deleteSidList);

                RsvDataPubDao pubDao = new RsvDataPubDao(con);
                pubDao.deleteList(deleteSidList);

                con.commit();
            }

            log__.info("使用データサイズの再集計 開始");

            //使用データサイズの再集計
            RsvDatausedSumDao dataUsedDao = new RsvDatausedSumDao(con);
            RsvDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);

            long yrkSize = 0;
            yrkSize += yrkDao.getTotalDataSize();
            RsvSisRyrkDao rYrkDao = new RsvSisRyrkDao(con);
            yrkSize += rYrkDao.getTotalDataSize();
            RsvSisKyrkDao kYrkDao = new RsvSisKyrkDao(con);
            yrkSize += kYrkDao.getTotalDataSize();
            sumMdl.setRsvSyrkSumSize(yrkSize);

            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getRsvSyrkSumSize();
            usedDisk += sumMdl.getRsvDataSumSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstReserve.PLUGIN_ID_RESERVE, usedDisk);

            con.commit();
            log__.info("使用データサイズの再集計 終了");

            commitFlg = true;

        } catch (Exception e) {
            log__.error("施設予約バッチ処理失敗", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } finally {

            log__.debug("施設予約バッチ処理終了");

            if (commitFlg) {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            } else {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }
    }

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception {
    }

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {
    }
}