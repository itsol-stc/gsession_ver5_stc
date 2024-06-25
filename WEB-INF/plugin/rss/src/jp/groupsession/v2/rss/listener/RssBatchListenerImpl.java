package jp.groupsession.v2.rss.listener;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.dao.RssDatausedSumDao;
import jp.groupsession.v2.rss.model.RssDatausedSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] RSSについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(RssBatchListenerImpl.class);
    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        boolean commit = false;
        try {
            log__.info("RSSディスク容量計算を開始");
            //使用データサイズの再集計
            RssDatausedSumDao dataUsedDao = new RssDatausedSumDao(con);
            RssDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);


            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getRssDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstRss.PLUGIN_ID_RSS, usedDisk);
            con.commit();
            commit = true;

            log__.info("RSSディスク容量計算を終了");
        } catch (SQLException e) {
            log__.error("RSS 日次バッチの実行に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
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

        //テンポラリディレクトリパスを取得
        RssBiz rssBiz = new RssBiz();
        String batchId = "batchHour";
        String batchTempDir = rssBiz.getBatchTempDir(param.getDomain(), batchId);

        //規定時間を過ぎても更新されていないRSSフィード情報を更新する。
        rssBiz.updateFeedData(
                con, param.getDomain(), param.getGsContext(), rssBiz.getUpdateTime(con),
                batchTempDir);

        //テンポラリディレクトリを削除
        rssBiz.deleteBatchTempDir(param.getDomain(), batchId);
    }

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {

//        log__.debug("RSSバッチ処理開始");
//        //
//        UDate ud = new UDate();
//        int mm = ud.getIntMinute();
//
//        if (mm % 15 > 0) {
//            System.gc(); //GCによりH2のテンポラリファイルを削除
//            log__.debug("RSSバッチ処理終了");
//            return;
//        }
//
//        log__.debug("RSSバッチ処理終了");
    }
}