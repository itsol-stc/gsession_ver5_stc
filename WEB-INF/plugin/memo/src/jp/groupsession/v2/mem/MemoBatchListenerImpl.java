package jp.groupsession.v2.mem;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.mem.dao.MemoDatausedSumDao;
import jp.groupsession.v2.mem.model.MemoAdmConfModel;
import jp.groupsession.v2.mem.model.MemoDatausedSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] メモについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class MemoBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(MemoBatchListenerImpl.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public MemoBatchListenerImpl() {
        super();
    }

    /**
     * <br>[機  能] 日次バッチ起動時に実行されるJob
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        log__.debug("メモバッチ処理開始");
        String pluginName = "メモ";
        DayJob.outPutStartLog(con, param.getDomain(), pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            MemCommonBiz biz = new MemCommonBiz();
            MemoAdmConfModel conf = biz.getAdmConfModel(con);
            if (conf.getMacAtdelKbn() == GSConstMemo.AUTO_DELETE_KBN_ON) {
                //自動削除実行

                //基準日を作成
                UDate bdate = new UDate();
                int year = conf.getMacAtdelY();
                int month = conf.getMacAtdelM();
                bdate.addYear(-year);
                bdate.addMonth(-month);
                //削除実行
                biz.deleteOldMemo(con, bdate);
            }
            //メモ自身のバッチ処理
            //現在の日付を取得
            UDate tdate = new UDate();

            //削除実行
            biz.deletePeriodMemo(con, tdate);
            con.commit();
            
            log__.info("メモディスク容量計算を開始");
            //使用データサイズの再集計
            MemoDatausedSumDao dataUsedDao = new MemoDatausedSumDao(con);
            MemoDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getMemoDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con,
                    GSConstMain.PLUGIN_ID_MEMO,
                    usedDisk);
            log__.info("メモディスク容量計算を終了");
            
            commitFlg = true;
            con.commit();
            log__.debug("メモバッチ処理終了");
        } catch (Exception e) {
            log__.error("メモバッチ処理失敗", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } finally {
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