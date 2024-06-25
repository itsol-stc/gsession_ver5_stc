package jp.groupsession.v2.bmk;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.bmk.dao.BmkDatausedSumDao;
import jp.groupsession.v2.bmk.model.BmkDatausedSumModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 施設予約についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(BmkBatchListenerImpl.class);

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

        log__.debug("ブックマークバッチ処理開始");
        boolean commit = false;
        try {
            //使用データサイズの再集計
            BmkDatausedSumDao dataUsedDao = new BmkDatausedSumDao(con);
            BmkDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);

            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getBmkBookmarkSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConst.PLUGINID_BMK, usedDisk);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ブックマークバッチ処理失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            log__.debug("ブックマークバッチ処理終了");
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