package jp.groupsession.v2.cir;

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
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAdelDao;
import jp.groupsession.v2.cir.dao.CirDatausedSumDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirLogCountDao;
import jp.groupsession.v2.cir.dao.CirLogCountSumDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cir.model.CirDatausedSumModel;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cir.model.CirLogCountSumModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 回覧板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(CirBatchListenerImpl.class);

    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        log__.debug("回覧板バッチ処理開始");
        String pluginName = "回覧板";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;
        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            /**********************************************************
            *
            * 自動削除設定に従いデータを削除する
            *
            **********************************************************/

            CirCommonBiz cmnBiz = new CirCommonBiz(con);
            CirInitModel cacMdl = new CirInitModel();
            cacMdl = cmnBiz.getCirInitConf(0, con);

            CirInfDao infDao = new CirInfDao(con);
            CirViewDao viewDao = new CirViewDao(con);
            CirCommonBiz cirBiz = new CirCommonBiz();

            //回覧板自動削除
            CirAdelDao delDao = new CirAdelDao(con);
            CirAdelModel adelMdl = delDao.select(0);



            //「管理者が設定する」で設定済み
            if (adelMdl != null
                    && cacMdl.getCinAutoDelKbn() == GSConstCircular.CIR_DEL_KBN_ADM_SETTING) {

                if (adelMdl.getCadJdelKbn() == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                    //受信
                    cirBiz.deleteView(con, adelMdl, 1);
                }

                if (adelMdl.getCadSdelKbn() == GSConstCircular.CIR_AUTO_DEL_LIMIT) {

                    //回覧先の状態区分を更新する(削除)
                    List<Integer> allDelList = infDao.getDeleteCir(adelMdl, 1);
                    if (allDelList != null && !allDelList.isEmpty()) {
                        UDate now = new UDate();
                        CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                        for (Integer delCifSid : allDelList) {
                            viewDao.deleteAllView(delCifSid, 0, now);
                            labelViewDao.deleteCircularLabel(delCifSid);
                        }
                    }

                    //送信
                    cirBiz.deleteInf(con, adelMdl, 1);
                }

                if (adelMdl.getCadDdelKbn() == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                    //ゴミ箱
                    cirBiz.deleteView(con, adelMdl, 2);
                    cirBiz.deleteInf(con, adelMdl, 2);
                }

            //「各ユーザが設定する」で設定済み
            } else if (cacMdl.getCinAutoDelKbn()
                                        == GSConstCircular.CIR_DEL_KBN_USER_SETTING) {

                //ラベル
                CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
                CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                //[自動削除する]に設定されている個人設定データを区分毎に取得
                ArrayList<CirAdelModel> jdelList = delDao.selectAutoDelUserData(1);
                ArrayList<CirAdelModel> sdelList = delDao.selectAutoDelUserData(2);
                ArrayList<CirAdelModel> ddelList = delDao.selectAutoDelUserData(3);

                //受信データ削除
                if (!jdelList.isEmpty()) {
                    //ラベル物理削除
                    labelViewDao.deleteLabel(jdelList, 1);
                    viewDao.delete(jdelList, 1);
                }

                //送信データ削除
                if (!sdelList.isEmpty()) {
                    //ラベル物理削除
                    labelInfDao.deleteLabel(sdelList, 1);
                    //回覧先の状態区分を更新する(削除)
                    viewDao.deleteSendView(sdelList);
                    infDao.delete(sdelList, 1);
                }

                //ゴミデータ削除
                if (!ddelList.isEmpty()) {
                    //ラベル物理削除
                    labelInfDao.deleteLabel(ddelList, 2);
                    labelViewDao.deleteLabel(ddelList, 2);
                    //回覧板論理削除
                    infDao.delete(ddelList, 2);
                    viewDao.delete(ddelList, 2);
                }
            }


            //削除対象の回覧板SIDを取得
            CircularDao cDao = new CircularDao(con);
            String[] delLst = cDao.getDelList();

            //回覧板情報を物理削除、バイナリ情報を論理削除
            cDao.deleteCir(delLst, GSConstUser.SID_ADMIN);

            /* 統計情報の集計*/
            //1.集計データの取得
            CirLogCountSumDao logSumDao = new CirLogCountSumDao(con);
            //2.集計データの登録
            UDate today = new UDate();
            List<CirLogCountSumModel> logSumList = logSumDao.getNonRegisteredList(today);
            if (logSumList != null && !logSumList.isEmpty()) {
                for (CirLogCountSumModel logSumMdl : logSumList) {
                    if (logSumDao.update(logSumMdl) == 0) {
                        logSumDao.insert(logSumMdl);
                    }
                }
            }

            //3.集計データの削除 前日までのデータを削除

            log__.debug("回覧板 統計情報集計データ削除処理開始");
            CirLogCountDao clcDao = new CirLogCountDao(con);
            int cirLogDelCount = clcDao.delete(today);
                log__.debug("回覧板 統計情報集計データ" + cirLogDelCount + "件を削除");

            con.commit();
            log__.info("使用データサイズの再集計 開始");

            //使用データサイズの再集計
            CirDatausedSumDao dataUsedDao = new CirDatausedSumDao(con);
            CirDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getCirDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstCircular.PLUGIN_ID_CIRCULAR, usedDisk);

            con.commit();
            log__.info("使用データサイズの再集計 終了");

            commitFlg = true;

            log__.debug("回覧板バッチ処理終了");

        } catch (Exception e) {
            log__.error("回覧板バッチ処理失敗", e);
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
