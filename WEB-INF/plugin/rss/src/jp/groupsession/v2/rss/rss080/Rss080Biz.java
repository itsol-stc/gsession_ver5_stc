package jp.groupsession.v2.rss.rss080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.rss.RssUsedDataBiz;
import jp.groupsession.v2.rss.dao.RssDao;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.dao.RssPositionDao;
import jp.groupsession.v2.rss.dao.RssPositionMainDao;
import jp.groupsession.v2.rss.model.RssModel;

/**
 * <br>[機  能]RSSリーダー メンテナンス画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss080Biz.class);

    /**
     * <br>[機  能] RSSフィード情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setFeedList(Rss080ParamModel paramMdl, Connection con)
    throws Exception {
        log__.debug("START");

        //最大件数
        RssInfomDao rssInfoDao = new RssInfomDao(con);
        int searchCnt = rssInfoDao.getRssInfoCount();
        int maxCnt = GSConstRss.RSSRANKING_VIEWCNT;
        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getRss080page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setRss080page1(page);
        paramMdl.setRss080page2(page);
        paramMdl.setStartIndex((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setPageLabelList(PageUtil.createPageOptions(searchCnt, maxCnt));

        int sort = paramMdl.getRss080sortKey();
        int order = paramMdl.getRss080orderKey();

        //RSS集計情報を取得
        RssDao rssDao = new RssDao(con);
        List<RssModel> resultList = rssDao.getRssCountDataAllList(page, maxCnt, sort, order);

        Map<Integer, RssModel> map = new HashMap<Integer, RssModel>();
        List<RssModel> dspList = new ArrayList<RssModel>();
        for (RssModel model : resultList) {
            int sid = model.getRssSid();
            if (!map.containsKey(sid)) {
                if (!ValidateUtil.isHttpUrlFormat(model.getRsdUrl())) {
                    model.setRsdUrl("");
                }
                dspList.add(model);
                map.put(sid, model);
            }
        }
        paramMdl.setResultList(dspList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] RSS情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rssSid RSSSID
     * @throws SQLException SQL実行例外
     * @throws Exception RSSフィードデータの削除時に例外発生
     */
    public void deleteRssData(Connection con, int rssSid)
    throws SQLException, Exception {
        log__.debug("START");

        //RSS情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        RssDataDao rssDao = new RssDataDao(con);
        List<Integer> userSidList = rssDao.getUsedUserList(rssSid);
        RssUsedDataBiz usedDataBiz = new RssUsedDataBiz(con);
        usedDataBiz.insertRssDataSize(rssSid, userSidList, false);

        //RSSマスタのデータ使用量を登録(削除対象のデータ使用量を減算)
        usedDataBiz.insertRssInfoSize(rssSid, false);

        //RSS情報の削除
        rssDao.deleteRss(rssSid);

        //RSS位置情報の削除
        RssPositionDao rssPositionDao = new RssPositionDao(con);
        rssPositionDao.deleteRssPos(rssSid);

        RssInfomDao rssInfoDao = new RssInfomDao(con);
        if (rssInfoDao.selectToDontUseData(rssSid) > 0) {
            //RSSフィードデータの削除
            ITempFileUtil tempFileUtil = 
                    (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
            tempFileUtil.deleteAllLogicalDeletedRssFeedData(con, rssSid);            
        }
        //RSSマスタの削除
        rssInfoDao.deleteToDontUseData(rssSid);

        //RSS位置情報_メイン画面の削除
        RssPositionMainDao rssPositionMainDao = new RssPositionMainDao(con);
        rssPositionMainDao.deleteUsersPosition(rssSid);

        log__.debug("End");
    }
}
