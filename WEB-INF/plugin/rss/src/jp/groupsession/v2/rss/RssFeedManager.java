package jp.groupsession.v2.rss;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.model.RssModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

/**
 * <br>[機  能] RSSフィード情報を取得するための機能を実装したクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssFeedManager {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssFeedManager.class);
    /** リクエスト情報 */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RssFeedManager(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] RSSフィード情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return RSSフィード情報一覧
     * @throws Exception 実行例外
     */
    public synchronized List<GSFeedList> getFeedList(Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        List<GSFeedList> flist = new ArrayList<GSFeedList>();
        ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                GSContext.TEMP_FILE_UTIL);
        RssDataDao rssDao = new RssDataDao(con);
        List<RssModel> rssDataList = rssDao.getRssDataList(itmp,
                userSid, GSConstRss.RSS_MAIN_VIEWFLG_NOT_MAINVIEW);

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);
        JDBCUtil.closeConnection(con);

        for (RssModel rssData : rssDataList) {
            try {
                GSFeedList feed = __getRssFeed(rssData, httpOperation);
                flist.add(feed);
            } catch (Exception e) {
                log__.error("RSSフィードの読み込みに失敗 :" + rssData.getRsdTitle(), e);
            }
        }

        log__.debug("End");
        return flist;
    }

    /**
     * <br>[機  能] RSSフィードを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssData RSS情報
     * @param httpOperation HttpOperation
     * @return RSSフィード情報
     * @throws Exception RSSフィードの取得に失敗
     */
    @SuppressWarnings("unchecked")
    private GSFeedList __getRssFeed(RssModel rssData, HttpOperation httpOperation)
    throws Exception {

        // URL, HTTPを元にフィードを取得する
        RssBiz rssBiz = new RssBiz(reqMdl__);
        SyndFeed feed = rssBiz.getFeedData(rssData.getRsdUrlFeed(), httpOperation);
        List<SyndEntry> entrys = (List<SyndEntry>) feed.getEntries();

        GSFeedList feedlist = new GSFeedList();
        feedlist.setFeedSid(rssData.getRssSid());
        feedlist.setFeedPosition(rssData.getRspPosition());
        feedlist.setFtitle(rssData.getRsdTitle());
        feedlist.setFurl(rssData.getRsdUrl());
        feedlist.setFeedUrl(rssData.getRsdUrlFeed());

        //件数制御
        if (entrys != null && entrys.size() > 0) {
            ArrayList<SyndEntry> fentrys = new ArrayList<SyndEntry>();
            int i = 0;
            for (SyndEntry ent : entrys) {
                i++;
                if (i > rssData.getRsdFeedCount()) {
                    break;
                }
                fentrys.add(ent);

                @SuppressWarnings("all")
                Class cls = ent.getInterface();
                log__.debug("ClassName = " + cls.getName());

                @SuppressWarnings("all")
                List mods = ent.getModules();
                for (Object obj : mods) {
                    log__.debug("  mod = " + obj.toString());
                }
            }
            feedlist.setFeedList(fentrys);
        }
        return feedlist;
    }
}
