package jp.groupsession.v2.ntp;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.biz.NtpUsedDataBiz;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.dao.NtpDatausedSumDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpDatausedSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 日報についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(NippouBatchListenerImpl.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public NippouBatchListenerImpl() {
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
        log__.info("日報バッチ処理開始");
        String pluginName = "日報";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            NtpCommonBiz biz = new NtpCommonBiz();
            NtpAdmConfModel conf = biz.getAdmConfModel(con);
            if (conf.getNacAtdelFlg() == GSConstNippou.AUTO_DELETE_ON) {
                //自動削除実行

                //基準日を作成
                UDate bdate = new UDate();
                int year = conf.getNacAtdelY();
                int month = conf.getNacAtdelM();
                bdate.addYear(-year);
                bdate.addMonth(-month);

                //日報情報のデータ使用量を登録(削除対象のデータ使用量を減算)
                NtpDataDao dao = new NtpDataDao(con);
                ArrayList<String> nipSidList = dao.getDeleteNipSid(bdate);
                List<Integer> sidList = new ArrayList<Integer>();
                for (String nipSid : nipSidList) {
                    sidList.add(Integer.parseInt(nipSid));
                }
                NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con);
                usedDataBiz.insertNtpDataSize(sidList, false);

                //日報データ(確認)削除実行
                biz.deleteOldNippouChk(con, bdate);
                //日報データ(コメント)削除実行
                biz.deleteOldNippouCmt(con, bdate);
                //日報データ(いいね)削除実行
                biz.deleteOldNippouGood(con, bdate);
                //日報データ削除実行
                biz.deleteOldNippou(con, bdate);
                con.commit();
            }
            
            log__.info("日報ディスク容量計算を開始");
            //使用データサイズの再集計
            NtpDatausedSumDao dataUsedDao = new NtpDatausedSumDao(con);
            NtpDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getNtpDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstNippou.PLUGIN_ID_NIPPOU, usedDisk);
            con.commit();
            log__.info("日報ディスク容量計算を終了");
            
            commitFlg = true;
            log__.debug("日報バッチ処理終了");
        } catch (Exception e) {
            log__.error("日報バッチ処理失敗", e);
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
