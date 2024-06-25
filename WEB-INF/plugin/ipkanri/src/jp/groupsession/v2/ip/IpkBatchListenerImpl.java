package jp.groupsession.v2.ip;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.ip.dao.IpkDatausedSumDao;
import jp.groupsession.v2.ip.model.IpkDatausedSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] IP管理についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(IpkBatchListenerImpl.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public IpkBatchListenerImpl() {
        super();
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        boolean commit = false;
        try {
            log__.info("IP管理ディスク容量計算を開始");
            //使用データサイズの再集計
            IpkDatausedSumDao dataUsedDao = new IpkDatausedSumDao(con);
            IpkDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);


            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getIpkDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, IpkConst.PLUGIN_ID_IPKANRI, usedDisk);

            con.commit();
            log__.info("IP管理ディスク容量計算を終了");
            commit = true;
        } catch (SQLException e) {
            log__.error("IP管理 1時間毎バッチの実行に失敗", e);
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
