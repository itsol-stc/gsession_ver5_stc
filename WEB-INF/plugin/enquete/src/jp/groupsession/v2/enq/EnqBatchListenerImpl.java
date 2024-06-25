package jp.groupsession.v2.enq;

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
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqAutodeleteDao;
import jp.groupsession.v2.enq.dao.EnqDatausedSumDao;
import jp.groupsession.v2.enq.model.EnqAutodeleteModel;
import jp.groupsession.v2.enq.model.EnqDatausedSumModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqBatchListenerImpl implements IBatchListener {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqBatchListenerImpl.class);

    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        log__.debug("アンケート日時バッチ処理開始");
        
        String pluginName = "アンケート";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        boolean commitFlg = false;

        try {
            con.setAutoCommit(false);

            UDate now = new UDate();
            UDate sendDate = now.cloneUDate();
            UDate draftDate = now.cloneUDate();

            // 自動削除区分を取得
            EnqAutodeleteDao dao = new EnqAutodeleteDao(con);
            EnqAutodeleteModel mdl = dao.select();
            if (mdl != null) {
                // 発信フォルダ自動削除
                if (mdl.getEadSendKbn() == GSConstEnquete.DELETE_KBN_ON) {
                    int year = mdl.getEadSendYear();
                    int month = mdl.getEadSendMonth();
                    sendDate = enqBiz.getThresholdUDate(-year, -month, sendDate);
                    enqBiz.deleteSendEnq(con, sendDate, now, GSConstUser.SID_ADMIN);
                }

                // 草稿フォルダ自動削除
                if (mdl.getEadDraftKbn() == GSConstEnquete.DELETE_KBN_ON) {
                    int year = mdl.getEadDraftYear();
                    int month = mdl.getEadDraftMonth();
                    sendDate = enqBiz.getThresholdUDate(-year, -month, draftDate);
                    enqBiz.deleteDraftEnq(con, sendDate, now, GSConstUser.SID_ADMIN);
                }
                con.commit();
            }

            log__.info("アンケートディスク容量計算を開始");
            //使用データサイズの再集計
            EnqDatausedSumDao dataUsedDao = new EnqDatausedSumDao(con);
            EnqDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getEnqDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstEnquete.PLUGIN_ID_ENQUETE, usedDisk);
            commitFlg = true;
            con.commit();
            log__.info("アンケートディスク容量計算を終了");
        } catch (Exception e) {
            log__.error("アンケート日時バッチ処理に失敗", e);
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