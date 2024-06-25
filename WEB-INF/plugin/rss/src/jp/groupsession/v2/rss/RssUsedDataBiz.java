package jp.groupsession.v2.rss;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssDatausedSumDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.model.RssDatausedSumModel;

/**
 * <br>[機  能] RSSプラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public RssUsedDataBiz(Connection con) {
        con__ = con;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

//    /**
//     * <br>[機  能] RSS情報の使用データサイズを登録する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rssSid RSSSID
//     * @param entryType true: 加算、false: 減算
//     * @throws SQLException SQL実行例外
//     */
//    public void insertRssSize(int rssSid, boolean entryType)
//    throws SQLException {
//        insertRssSize(Arrays.asList(rssSid), entryType);
//    }

//    /**
//     * <br>[機  能] RSS情報の使用データサイズを登録する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rssSidList RSSSIDリスト
//     * @param entryType true: 加算、false: 減算
//     * @throws SQLException SQL実行例外
//     */
//    public void insertRssSize(List<Integer> rssSidList, boolean entryType)
//    throws SQLException {
//
//        if (rssSidList == null || rssSidList.isEmpty()) {
//            return;
//        }
//
//        //RSSマスタ情報のサイズを取得
//        RssInfomDao infoDao = new RssInfomDao(con__);
//        long rssSize = UsedSizeConst.SIZE_RSS_INFOM * rssSidList.size();
//
//        if (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_POSTGRES) {
//            //PostgreSQLの場合、pg_largeobjectから取得
//            rssSize += infoDao.getRssFeedDataSizeInPostgreSQL(rssSidList);
//        } else {
//            //H2 Databaseの場合、対象フィールドから直接取得
//            rssSize += infoDao.getRssFeedDataSize(rssSidList);
//        }
//
//        //RSSマスタ情報のサイズを取得
//        RssDataDao rssDataDao = new RssDataDao(con__);
//        rssSize += rssDataDao.getRssDataCount(rssSidList) * UsedSizeConst.SIZE_RSS_INFOM;
//
//        //減算の場合はデータサイズを負数に反転する
//        if (!entryType) {
//            rssSize *= -1;
//        }
//
//        //使用データサイズを登録
//        RssDatausedSumModel dataUsedMdl = new RssDatausedSumModel();
//        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
//        dataUsedMdl.setRssDataSize(rssSize);
//        RssDatausedSumDao dataUsedDao = new RssDatausedSumDao(con__);
//        dataUsedDao.insert(dataUsedMdl);
//    }

    /**
     * <br>[機  能] RSS情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @param userSid ユーザSID
     * @param entryType true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertRssDataSize(int rssSid, int userSid, boolean entryType)
    throws SQLException {
        insertRssDataSize(rssSid, Arrays.asList(userSid), entryType);
    }

    /**
     * <br>[機  能] RSS情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @param userSidList ユーザSID
     * @param entryType true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertRssDataSize(int rssSid, List<Integer> userSidList, boolean entryType)
    throws SQLException {

        if (rssSid <= 0 || userSidList == null || userSidList.isEmpty()) {
            return;
        }

        //RSS情報のサイズを取得
        RssDataDao rssDataDao = new RssDataDao(con__);
        long rssSize = rssDataDao.getRssDataSize(rssSid, userSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            rssSize *= -1;
        }

        //使用データサイズの登録を行う
        __insertDatausedSum(rssSize);
    }

    /**
     * <br>[機  能] RSSマスタの使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @param entryType true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertRssInfoSize(int rssSid, boolean entryType)
    throws SQLException {

        if (rssSid <= 0) {
            return;
        }

        //RSSマスタのサイズを取得
        RssInfomDao infoDao = new RssInfomDao(con__);
        long rssSize = infoDao.getRssDataSize(rssSid);

        //RSSマスタ RSSフィード情報のサイズを取得
        if (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_POSTGRES) {
            //PostgreSQLの場合、pg_largeobjectから取得
            rssSize += infoDao.getRssFeedDataSizeInPostgreSQL(rssSid);
        } else {
            //H2 Databaseの場合、対象フィールドから直接取得
            rssSize += infoDao.getRssFeedDataSize(rssSid);
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            rssSize *= -1;
        }

        //使用データサイズの登録を行う
        __insertDatausedSum(rssSize);
    }

    /**
     * <br>[機  能] RSS 使用データサイズ集計の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSize 使用データサイズ
     * @throws SQLException
     */
    private void __insertDatausedSum(long rssSize) throws SQLException {
        RssDatausedSumModel dataUsedMdl = new RssDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setRssDataSize(rssSize);
        RssDatausedSumDao dataUsedDao = new RssDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}
