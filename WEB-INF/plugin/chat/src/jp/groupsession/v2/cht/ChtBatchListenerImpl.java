package jp.groupsession.v2.cht;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.biz.ChtUsedDataBiz;
import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cht.dao.ChtDatausedSumDao;
import jp.groupsession.v2.cht.dao.ChtFavoriteDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataSumDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cht.dao.ChtGroupViewDao;
import jp.groupsession.v2.cht.dao.ChtLogCountDao;
import jp.groupsession.v2.cht.dao.ChtLogCountSumDao;
import jp.groupsession.v2.cht.dao.ChtUserDataDao;
import jp.groupsession.v2.cht.dao.ChtUserDataSumDao;
import jp.groupsession.v2.cht.dao.ChtUserPairDao;
import jp.groupsession.v2.cht.dao.ChtUserViewDao;
import jp.groupsession.v2.cht.model.ChatDeleteModel;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtDatausedSumModel;
import jp.groupsession.v2.cht.model.ChtLogCountSumModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] チャットについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ChtBatchListenerImpl.class);

    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        log__.info("チャット 日次バッチ処理開始");
        String pluginName = "チャット";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            /*グループ削除処理*/
            //削除済みグループのチャットグループSIDを取得する
            ChtGroupInfDao cgiDao = new ChtGroupInfDao(con);
            List<Integer> groupDelList = cgiDao.getDelGrpSid();
            ChtGroupDataDao cgdDao = new ChtGroupDataDao(con);
            if (groupDelList.size() > 0) {
                //チャット情報のデータ使用量を登録(削除前のデータ使用量を減算)
                List<Long> cgdSidList = cgdDao.selectCgdSidList(groupDelList);
                ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con);
                usedDataBiz.insertChtDataSize(cgdSidList, ChtUsedDataBiz.TYPE_GROUP, false);

                //チャットグループ情報の削除
                cgiDao.delete(groupDelList);
                //チャットグループユーザ情報の削除
                ChtGroupUserDao cguDao = new ChtGroupUserDao(con);
                cguDao.delete(groupDelList);
                //グループ投稿情報の削除
                List<Integer> binList = cgdDao.selectBinList(groupDelList);
                cgdDao.delete(groupDelList);
                //グループ投稿集計情報の削除
                ChtGroupDataSumDao gdsDao = new ChtGroupDataSumDao(con);
                gdsDao.delete(groupDelList);

                //閲覧情報の削除
                ChtGroupViewDao cgvDao = new ChtGroupViewDao(con);
                cgvDao.delete(groupDelList);
                //お気に入り情報の削除
                ChtFavoriteDao cfDao = new ChtFavoriteDao(con);
                cfDao.delete(groupDelList, GSConstChat.CHAT_KBN_GROUP);
                //添付ファイルの削除
                if (binList.size() > 0) {
                    ChatDao cDao = new ChatDao(con);
                    cDao.updateTempJkbn(binList);
                }
            }

            /* ユーザ削除処理*/
            //削除済みユーザ一覧を取得する
            ChatDao chtDao = new ChatDao(con);
            List<Integer> delUserList = chtDao.getDelUser();
            ChtUserDataDao cudDao = new ChtUserDataDao(con);
            if (delUserList.size() > 0) {
                //ペアSIDの取得
                ChtUserPairDao cupDao = new ChtUserPairDao(con);
                List<Integer> cupSidList = cupDao.selectCupSid();

                if (cupSidList.size() > 0) {

                    //チャット情報のデータ使用量を登録(削除前のデータ使用量を減算)
                    List<Long> cgdSidList = cudDao.selectCudSidList(cupSidList);
                    ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con);
                    usedDataBiz.insertChtDataSize(cgdSidList, ChtUsedDataBiz.TYPE_USER, false);

                    //ペア情報の削除
                    cupDao.delete(cupSidList);
                    List<Integer> binList = cudDao.selectBinList(cupSidList);
                    //投稿情報の削除
                    cudDao.delete(cupSidList);
                    //ユーザ投稿集計情報の削除
                    ChtUserDataSumDao cusDao = new ChtUserDataSumDao(con);
                    cusDao.delete(cupSidList);
                    //閲覧情報の削除
                    ChtUserViewDao cuvDao = new ChtUserViewDao(con);
                    cuvDao.delete(cupSidList);
                    //お気に入り情報の削除
                    ChtFavoriteDao cfDao = new ChtFavoriteDao(con);
                    cfDao.delete(delUserList, GSConstChat.CHAT_KBN_USER);
                    //添付ファイルの削除
                    if (binList.size() > 0) {
                        ChatDao cDao = new ChatDao(con);
                        cDao.updateTempJkbn(binList);
                    }

                }
            }

            /* 統計情報の集計*/
            //1.集計データの取得
            ChtLogCountSumDao clcsDao = new ChtLogCountSumDao(con);
            UDate today = new UDate();
            List<ChtLogCountSumModel> logSumList = clcsDao.getNonRegisteredList(today);
            //2.集計データの登録
            if (logSumList != null && !logSumList.isEmpty()) {
                for (ChtLogCountSumModel logSumMdl : logSumList) {
                    if (clcsDao.updateNitizi(logSumMdl) == 0) {
                        clcsDao.insert(logSumMdl);
                    }
                }
            }
            //3.集計データの削除 前日までのデータを削除
            ChtLogCountDao clcDao = new ChtLogCountDao(con);
            clcDao.delete(today);
            log__.debug("チャット 集計データを削除");

            /* 自動削除処理*/
            //1.管理者設定から自動削除の有無と期間を確認する
            ChtBiz biz = new ChtBiz(con);
            ChtAdmConfModel cacMdl = biz.getChtAconf();
            if (cacMdl.getCacAtdelFlg() == GSConstChat.AUTO_DELETE_YES) {
                //2.投稿情報を削除する
                ChatDeleteModel delMdl = new ChatDeleteModel();
                delMdl.setDelYear(cacMdl.getCacAtdelY());
                delMdl.setDelMonth(cacMdl.getCacAtdelM());
                ChtBiz chtBiz = new ChtBiz(con);
                chtBiz.deleteChtData(delMdl, 0);
            }
            
            //チャット投稿集計情報の更新
            ChtUserDataSumDao cusDao = new ChtUserDataSumDao(con);
            ChtGroupDataSumDao cgsDao = new ChtGroupDataSumDao(con);
            cusDao.updateNow();
            cgsDao.updateNow();
            con.commit();
            log__.info("チャットディスク容量計算を開始");
            //使用データサイズの再集計
            ChtDatausedSumDao dataUsedDao = new ChtDatausedSumDao(con);
            ChtDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getChtDiskSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstChat.PLUGIN_ID_CHAT, usedDisk);
            con.commit();
            commitFlg = true;
            log__.info("チャットディスク容量計算を終了");
        } catch (Exception e) {
            log__.error("チャット 日次バッチ処理失敗", e);
            logException = e;
            throw e;
        } finally {
            log__.info("チャット 日次バッチ処理終了");
            if (!commitFlg) {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            } else {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
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
