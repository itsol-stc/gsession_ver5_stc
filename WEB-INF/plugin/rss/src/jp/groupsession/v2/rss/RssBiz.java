package jp.groupsession.v2.rss;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.rss.dao.RssAconfDao;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.dao.RssPositionDao;
import jp.groupsession.v2.rss.model.RssAconfModel;
import jp.groupsession.v2.rss.model.RssDataModel;
import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.rss.model.RssPositionModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] RSSリーダープラグインの共通機能
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssBiz.class);

    /** RSSフィード情報更新停止フラグ */
    private static Map<String, Boolean> stopRssUpdateFlgMap__ =
            new ConcurrentHashMap<String, Boolean>();

    /** RSS更新処理 処理件数 */
    private static Map<String, AtomicInteger> rssUpdateCountMap__ =
            new ConcurrentHashMap<String, AtomicInteger>();

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RssBiz() {

    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RssBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public RssBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] フィード情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param feedUrl フィードURL
     * @param con コネクション
     * @return フィード情報
     * @throws Exception フィード情報の取得に失敗
     */
    public SyndFeed getFeedData(String feedUrl, Connection con) throws Exception {
        log__.debug("START");

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);

        log__.debug("END");

        return getFeedData(feedUrl, httpOperation);

    }

    /**
     * <br>[機  能] フィード情報を取得する
     * <br>[解  説]
     * <br>[備  考] フィードを取得できなかった場合NULLを返します。取得できない場合エラーログがwarnレベルで記録されます。
     * @param feedUrl フィードURL
     * @param httpOperation HttpOperation
     * @return フィード情報
     */
    public SyndFeed getFeedData(String feedUrl, HttpOperation httpOperation) {
        log__.debug("START");

        // HTTPを元にフィードを取得するクラス
        GSHttpClientFeedFetcher gsfet = new GSHttpClientFeedFetcher();
        gsfet.setHttpOperation(httpOperation);

        SyndFeed feed = null;
        try {
            feed = gsfet.retrieveFeed(new URL(feedUrl));
        } catch (Exception e) {
            log__.warn("フィードの取得に失敗 url = " + feedUrl, e);
        }

        //データを取得できた場合
        if (feed != null) {
            //title要素が取得できなかった場合、「Untitled(無題)」を設定する
            String title = NullDefault.getString(feed.getTitle(), "無題");
            feed.setTitle(StringUtilHtml.replaceString(title, "\n", "").trim());
            //link要素が取得できなかった場合は、フィードURLを設定する
            feed.setLink(NullDefault.getString(feed.getLink(), feedUrl));
        }
        log__.debug("END");
        return feed;
    }

    /**
     * <br>[機  能] RSSフィードを取得する(自動用)
     * <br>[解  説]
     * <br>[備  考]
     * @param rssInfo RSS情報
     * @param httpOperation HttpOperation
     * @return RSSフィード情報
     * @throws Exception RSSフィードの取得に失敗
     */
    @SuppressWarnings("unchecked")
    public GSFeedList getRssFeedForAutoUpdate(RssInfomModel rssInfo, HttpOperation httpOperation)
    throws Exception {

        // URL, HTTPを元にフィードを取得する
        SyndFeed feed = getFeedData(rssInfo.getRsmUrlFeed(), httpOperation);
        List<SyndEntry> entrys = null;
        GSFeedList feedlist = new GSFeedList();

        if (feed != null) {
            entrys = (List<SyndEntry>) feed.getEntries();
        } else {
            return feedlist;
        }
        feedlist.setFeedSid(rssInfo.getRssSid());
        feedlist.setFtitle(StringUtilHtml.replaceString(feed.getTitle(), "\n", "").trim());
        feedlist.setFurl(feed.getLink());
        feedlist.setFeedUrl(rssInfo.getRsmUrlFeed());
        feedlist.setDescription(feed.getDescription());

        //件数制御
        if (entrys != null && entrys.size() > 0) {
            ArrayList<SyndEntry> fentrys = new ArrayList<SyndEntry>();
            for (SyndEntry ent : entrys) {
                fentrys.add(ent);
                //DescriptionをHTMLで正常に表示できる様に加工
                __toHtmlDescription(ent);
            }
            feedlist.setFeedList(fentrys);
        }
        return feedlist;
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
    public GSFeedList getRssFeed(RssModel rssData, HttpOperation httpOperation)
    throws Exception {

        // URL, HTTPを元にフィードを取得する
        RssBiz rssBiz = new RssBiz();
        SyndFeed feed = rssBiz.getFeedData(rssData.getRsdUrlFeed(), httpOperation);
        GSFeedList feedlist = new GSFeedList();
        List<SyndEntry> entrys = null;
        if (feed != null) {
            entrys = (List<SyndEntry>) feed.getEntries();
        } else {
            return feedlist;
        }


        feedlist.setFeedSid(rssData.getRssSid());
        feedlist.setDescription(feed.getDescription());
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
                //titleからスペース、改行を除去
                String entTitle = ent.getTitle();
                if (entTitle != null) {
                    entTitle = entTitle.trim();
                    entTitle = StringUtil.toDeleteReturnCode(entTitle);
                    ent.setTitle(entTitle);
                }
                //DescriptionをHTMLで正常に表示できる様に加工
                __toHtmlDescription(ent);

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

    /**
     * <br>[機  能] RSS情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param rssModel RssDataModel
     * @return RSSSID
     * @throws Exception RSSの登録に失敗
     */
    public int entryRssData(
        Connection con,
        MlCountMtController cntCon,
        RssDataModel rssModel) throws Exception {

        UDate now = new UDate();
        String feedUrl = rssModel.getRsdUrlFeed();
        int userSid = rssModel.getRsdEuid();
        int authKbn = rssModel.getRsdAuth();
        String authId = rssModel.getRsdAuthId();
        String authPswd = rssModel.getRsdAuthPswd();
        String rsdtitle = rssModel.getRsdTitle();

        int rssSid = 0;
        RssInfomDao rssInfoDao = new RssInfomDao(con);
        RssUsedDataBiz usedDataBiz = new RssUsedDataBiz(con);
        ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                GSContext.TEMP_FILE_UTIL);
        RssInfomModel rssInfoMdl = rssInfoDao.selectToFeedUrl(itmp, feedUrl);
        if (rssInfoMdl == null) {
            //RSSSID採番
            rssSid = (int) cntCon.getSaibanNumber(GSConstRss.SBNSID_RSS,
                                                    GSConstRss.SBNSID_SUB_RSS_ID,
                                                    userSid);

            //RSSマスタの登録
            rssInfoMdl = new RssInfomModel();
            rssInfoMdl.setRssSid(rssSid);
            rssInfoMdl.setRsmUrlFeed(feedUrl);
            rssInfoMdl.setRsmUpdateTime(UDate.getInstance(0));
            rssInfoMdl.setRsmAuth(authKbn);
            rssInfoMdl.setRsmAuthId(authId);
            rssInfoMdl.setRsmAuthPswd(authPswd);
            rssInfoMdl.setRsmAuid(userSid);
            rssInfoMdl.setRsmAdate(now);
            rssInfoMdl.setRsmEuid(userSid);
            rssInfoMdl.setRsmEdate(now);

            rssInfoDao.insert(rssInfoMdl);

            //RSSマスタのデータ使用量を登録
            usedDataBiz.insertRssInfoSize(rssSid, true);

        } else {
            rssSid = rssInfoMdl.getRssSid();
        }

        //RSS情報の登録
        rssModel.setRsdTitle(rsdtitle);
        rssModel.setRssSid(rssSid);
        rssModel.setUsrSid(userSid);
        rssModel.setRsdView(GSConstRss.RSS_VIEWFLG_SHOW);
        rssModel.setRsdAuth(authKbn);
        rssModel.setRsdAuthId(authId);
        rssModel.setRsdAuthPswd(authPswd);
        rssModel.setRsdAuid(userSid);
        rssModel.setRsdAdate(now);
        rssModel.setRsdEdate(now);

        RssDataDao rssDao = new RssDataDao(con);
        rssDao.insert(rssModel);

        //RSS情報のデータ使用量を登録
        usedDataBiz.insertRssDataSize(rssSid, userSid, true);

        //RSS位置情報の登録
        RssPositionModel rssPositionModel = new RssPositionModel();
        rssPositionModel.setRssSid(rssSid);
        rssPositionModel.setUsrSid(userSid);
        rssPositionModel.setRspPosition(GSConstRss.RSS_POSITIONFLG_LEFT);
        rssPositionModel.setRspOrder(0); //自動採番用に0を設定
        rssPositionModel.setRspAuid(userSid);
        rssPositionModel.setRspAdate(now);
        rssPositionModel.setRspEuid(userSid);
        rssPositionModel.setRspEdate(now);

        RssPositionDao rssPositionDao = new RssPositionDao(con);
        rssPositionDao.insert(rssPositionModel);


        return rssSid;
    }

    /**
     * <br>[機  能] RSS情報の存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @return チェックの結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existRssData(int rssSid, int userSid, Connection con)
    throws SQLException {

        RssDataDao rssDao = new RssDataDao(con);
        return rssDao.getRssData(rssSid, userSid) != null;
    }

    /**
     * <br>[機  能] RSS情報の登録処理を行う
     * <br>[解  説] フィード情報の取得に失敗した場合、DBに登録されているの情報を設定する。
     * <br>[備  考] 取得できない場合エラーログがwarnレベルで記録されます。
     * @param rssSid RSSSID
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param mainView メイン表示フラグ
     * @throws Exception 実行例外
     * @return RssDataModel RSS情報モデル
     */
    public RssDataModel insertRssData(
        int rssSid,
        Connection con,
        MlCountMtController cntCon,
        int userSid,
        int mainView) throws Exception {

        RssInfomDao rssInfoDao = new RssInfomDao(con);
        ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                GSContext.TEMP_FILE_UTIL);
        RssInfomModel rssInfoMdl = rssInfoDao.select(itmp, rssSid);

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);

        RssBiz rssBiz = new RssBiz(con, reqMdl__);
        RssDataModel rssModel = new RssDataModel();
        try {
            SyndFeed feed = rssBiz.getFeedData(rssInfoMdl.getRsmUrlFeed(), httpOperation);
            feed.getEntries();

            rssModel.setRsdUrlFeed(rssInfoMdl.getRsmUrlFeed());
            rssModel.setRsdTitle(feed.getTitle());
            rssModel.setRsdUrl(feed.getLink());
            rssModel.setRsdMainView(mainView);
            rssModel.setRsdAuth(rssInfoMdl.getRsmAuth());
            rssModel.setRsdAuthId(rssInfoMdl.getRsmAuthId());
            rssModel.setRsdAuthPswd(rssInfoMdl.getRsmAuthPswd());
            rssModel.setRsdFeedCount(GSConstRss.RSS_DEFAULT_VIEWCNT);
            rssModel.setRsdEuid(userSid);
            rssBiz.entryRssData(con, cntCon, rssModel);
        } catch (Exception e) {
            log__.warn("フィード情報の取得に失敗しました。", e);
            GSFeedList feedList = rssInfoMdl.getRsmFeeddata();
            rssModel.setRsdUrlFeed(rssInfoMdl.getRsmUrlFeed());
            rssModel.setRsdMainView(mainView);
            rssModel.setRsdFeedCount(GSConstRss.RSS_DEFAULT_VIEWCNT);
            rssModel.setRsdEuid(userSid);
            rssModel.setRsdAuth(rssInfoMdl.getRsmAuth());
            rssModel.setRsdAuthId(rssInfoMdl.getRsmAuthId());
            rssModel.setRsdAuthPswd(rssInfoMdl.getRsmAuthPswd());
            GsMessage gsMsg = new GsMessage(reqMdl__);
            //データを取得できませんでした
            String textNoData = gsMsg.getMessage("rss.31");
            if (feedList != null && feedList.getFtitle() != null) {
                rssModel.setRsdTitle(feedList.getFtitle());
            } else {
                rssModel.setRsdTitle(textNoData);
            }
            if (feedList != null && feedList.getFurl() != null) {
                rssModel.setRsdUrl(feedList.getFurl());
            } else {
                rssModel.setRsdUrl("noData");
            }

            rssBiz.entryRssData(con, cntCon, rssModel);
        }
        return rssModel;
    }

    /**
     * <br>[機  能] メイン表示フラグから表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mainView メイン表示フラグ
     * @param reqMdl リクエスト情報
     * @return String 表示文字列
     */
    public static String getMainViewStr(int mainView, RequestModel reqMdl) {

        String mainViewStr = "";

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (mainView == GSConstRss.RSS_MAIN_VIEWFLG_SHOW) {
            String rssMainViewflgStrShow = gsMsg.getMessage("cmn.show");
            mainViewStr = rssMainViewflgStrShow;

        } else if (mainView == GSConstRss.RSS_MAIN_VIEWFLG_NOTSHOW) {
            String rssMainViewflgStrNotShow = gsMsg.getMessage("cmn.hide");
            mainViewStr = rssMainViewflgStrNotShow;

        }
        return mainViewStr;

    }

    /**
     * <br>[機  能] RSSフィード情報の更新を行う。
     * <br>[解  説] 更新基準時間にnullが設定された場合は全てのRSSフィード情報を更新する
     * <br>[備  考]
     * @param con コネクション
     * @param domain ドメイン
     * @param param パラメータ(Object)
     * @param updateTime 更新基準時間
     * @param tempDir テンポラリディレクトリパス
     * @throws Exception RSSフィード情報の更新に失敗
     */
    public void updateFeedData(Connection con, String domain,
                                Object param, UDate updateTime,
                                String tempDir)
    throws Exception {

        incRssUpdateCount(domain);
        try {
            //テンポラリディレクトリ取得、作成
            IOTools.isDirCheck(tempDir, true);

            RssInfomDao feedDao = new RssInfomDao(con);
            List<RssInfomModel> upFeeds = null;
            if (updateTime == null) {
                upFeeds = feedDao.selectAllFeed();
            } else {
                upFeeds = feedDao.selectUpdateFeed(updateTime);
            }

            HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);

            //RSSフィードを取得し、DBにアップデート
            RssUsedDataBiz usedDataBiz = new RssUsedDataBiz(con);
            for (RssInfomModel feed : upFeeds) {
                if (isStopRssUpdateFlg(domain)) {
                    return;
                }

                boolean commitFlg = false;
                try {
                    feed.setRsmUpdateTime(new UDate());
                    feed.setRsmEuid(GSConst.SYSTEM_USER_ADMIN);
                    feed.setRsmEdate(feed.getRsmUpdateTime());
                    //フィードを取得
                    RssBiz rssBiz = new RssBiz(con__, reqMdl__);
                    GSFeedList gsfeed = rssBiz.getRssFeedForAutoUpdate(feed, httpOperation);

                    if (gsfeed == null) {
                        continue;
                    }
                    if (gsfeed.getFtitle() == null) {
                        continue;
                    }
                    if (gsfeed.getFurl() == null) {
                        continue;
                    }

                    //RSSマスタのデータ使用量を登録(変更前のデータ使用量を減算)
                    usedDataBiz.insertRssInfoSize(feed.getRssSid(), false);

                    gsfeed.setFeedUrl(feed.getRsmUrlFeed());
                    //オブジェクトをファイルに保存
                    ObjectFile objFile = new ObjectFile(tempDir, String.valueOf(feed.getRssSid()));
                    objFile.save(gsfeed);

                    //DBを更新
                    String feedFilePath = tempDir + feed.getRssSid();
                    ITempFileUtil tempFileUtil = (ITempFileUtil) GroupSession
                            .getContext().get(GSContext.TEMP_FILE_UTIL);
                    tempFileUtil.updateFeedData(con, feed, new File(feedFilePath),
                            GroupSession.getResourceManager().getCountController(domain));

                    //RSSマスタのデータ使用量を登録
                    usedDataBiz.insertRssInfoSize(feed.getRssSid(), true);

                    commitFlg = true;

                    //テンポラリに保存したファイルを削除
                    IOTools.deleteFile(feedFilePath);
                } catch (Exception e) {
                    log__.error("RSSフィードのアップデートに失敗", e);
                } finally {
                    if (commitFlg) {
                        con.commit();
                    } else {
                        JDBCUtil.rollback(con);
                    }
                }
            }
        } finally {
            decRssUpdateCount(domain);
        }
    }

    /**
     * <br>[機  能] SyndEntryのdescriptionをhtmlで表示する様に変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param ent SyndEntry
     */
    private void __toHtmlDescription(SyndEntry ent) {
        SyndContent desc = ent.getDescription();
        if (desc == null) {
            return;
        }
        String description = desc.getValue();
        //タグを除去する
        String strTmp = StringUtilHtml.deleteHtmlTag(description);
        strTmp = StringUtilHtml.transToHTml(strTmp);
        desc.setValue(strTmp);
    }

    /**
     * <br>[機  能] リスト内のフィードデータがNULLの場合はHTTPからフィード情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rssNewrankingDataList 新着ランキングリスト
     * @param httpOperation HttpOperation
     * @return rssRankingDataList 新着ランキングリスト
     * @throws Exception RSSフィード情報の取得に失敗
     */
    public List<RssInfomModel> getNewranking(
            List<RssInfomModel> rssNewrankingDataList, HttpOperation httpOperation)
    throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //データを取得できませんでした
        String textNoData = gsMsg.getMessage("rss.31");
        for (RssInfomModel model : rssNewrankingDataList) {
            GSFeedList feedList = model.getRsmFeeddata();

            if (feedList == null || feedList.getFtitle() == null) {
                feedList = new GSFeedList();
                SyndFeed newFeed = getFeedData(model.getRsmUrlFeed(), httpOperation);
                if (newFeed != null) {
                    feedList.setFtitle(newFeed.getTitle());
                    feedList.setFurl(newFeed.getLink());
                } else {
                    feedList.setFtitle(textNoData);
                }
                feedList.setFeedUrl(model.getRsmUrlFeed());
            }
            model.setTitle(feedList.getFtitle());
            model.setUrl(feedList.getFurl());
        }
        return rssNewrankingDataList;
    }

    /**
     * <br>[機  能] リスト内のフィードデータがNULLの場合はHTTPからフィード情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rssRankingDataList 登録ランキングリスト
     * @param httpOperation HttpOperation
     * @return rssRankingDataList 登録ランキングリスト
     * @throws Exception RSSフィード情報の取得に失敗
     */
    public List<RssModel> getRanking(List<RssModel> rssRankingDataList,
                                    HttpOperation httpOperation)
    throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //データを取得できませんでした
        String textNoData = gsMsg.getMessage("rss.31");
        for (RssModel rankingMdl : rssRankingDataList) {
            GSFeedList feedList = rankingMdl.getFeedData();
            if (feedList == null || feedList.getFtitle() == null) {
                SyndFeed feed = getFeedData(rankingMdl.getRsdUrlFeed(), httpOperation);
                feedList = new GSFeedList();
                if (feed != null) {
                    rankingMdl.setRsdTitle(feed.getTitle());
                    rankingMdl.setRsdUrl(feed.getLink());
                    rankingMdl.setDescription(feedList.getDescription());
                } else {
                    feedList.setFtitle(textNoData);
                }
            } else {
                rankingMdl.setRsdTitle(feedList.getFtitle());
                rankingMdl.setRsdUrl(feedList.getFurl());
                rankingMdl.setDescription(feedList.getDescription());
            }
        }
        return rssRankingDataList;
    }

    /**
     * <br>[機  能] 登録RSS情報を設定する。
     * <br>[解  説] リストにRSS情報が入っていない時はHTTPからフィード情報を取得する。
     * <br>[備  考]
     * @param rssDataList RSS一覧
     * @param updateTime システム時刻
     * @param httpOperation HttpOperation
     * @param dir RSSフィード情報保存用ディレクトリパス
     * @param updateRssSidList 更新をかけるRSSSIDのリスト
     * @param domain ドメイン
     * @return List in GSFeedList
     */
     public List<GSFeedList> setRssList(
        List<RssModel> rssDataList,
        UDate updateTime,
        HttpOperation httpOperation,
        String dir,
        List<String> updateRssSidList,
        String domain) {

        List<GSFeedList> flist = new ArrayList<GSFeedList>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //データを取得できませんでした
        String textNoData = gsMsg.getMessage("rss.31");
        for (RssModel rssData : rssDataList) {
            if (isStopRssUpdateFlg(domain)) {
                break;
            }

            GSFeedList feed = rssData.getFeedData();

            boolean addFeed = true;
            int rssViewCount = rssData.getRsdFeedCount();
            rssData.setRsdFeedCount(10);
            if (feed == null || feed.getFtitle() == null
            || rssData.getFeedUpdateTime().getTimeMillis() < updateTime.getTimeMillis()) {
                try {
                    feed = getRssFeed(rssData, httpOperation);
                    if (feed == null || feed.getFtitle() == null) {
                        feed.setFtitle(textNoData);
                        feed.setFeedSid(rssData.getRssSid());
                        feed.setFurl(rssData.getRsdUrl());
                        feed.setReadError(true);
                    } else {
                        feed.setReadError(false);
                    }
                    IOTools.isDirCheck(dir, true);
                    ObjectFile objFile = new ObjectFile(dir, String.valueOf(rssData.getRssSid()));
                    objFile.save(feed);

                    updateRssSidList.add(String.valueOf(rssData.getRssSid()));
                } catch (Exception e) {
                    log__.error("RSSフィードの読み込みに失敗 :" + rssData.getRsdTitle(), e);
                    addFeed = false;
                }
            } else {
                feed.setFtitle(rssData.getRsdTitle());
                feed.setFurl(rssData.getRsdUrl());
                feed.setFeedPosition(rssData.getRspPosition());
                updateRssSidList.add(String.valueOf(rssData.getRssSid()));
            }
            feed.setFeedUrl(rssData.getRsdUrlFeed());

            if (addFeed) {

                int count = 1;
                ArrayList<SyndEntry> entryList = new ArrayList<SyndEntry>();

                if (feed.getFeedList() != null) {
                    for (SyndEntry entry : feed.getFeedList()) {
                        if (count > rssViewCount) {
                            break;
                        }
                        entryList.add(entry);
                        count++;
                    }
                }
                rssData.setRsdFeedCount(rssViewCount);

                feed.setFeedList(entryList);
                flist.add(feed);
            }

            //改行コードを削除する
            for (int i = 0; i < feed.getFeedList().size(); i++) {
                String title = feed.getFeedList().get(i).getTitle();
                feed.getFeedList().get(i).setTitle(StringUtil.replaceReturnCode(title, ""));
            }
        }
        return flist;
    }

    /**
     * <br>[機  能] 更新基準日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 更新基準日時
     * @throws SQLException SQL実行時例外
     */
    public UDate getUpdateTime(Connection con) throws SQLException {
        RssAconfDao aconfDao = new RssAconfDao(con);
        RssAconfModel aconfMdl = aconfDao.select();
        UDate updateTime = new UDate();

        int readTime = 30;
        if (aconfMdl != null) {
            readTime = aconfMdl.getRacReadtime();
        }
        updateTime.addMinute(readTime * -1);

        return updateTime;
    }

    /**
     * RSS全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        GsMessage gsMsg = new GsMessage(reqMdl);

        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstRss.PLUGIN_ID_RSS);
        logMdl.setLogPluginName(gsMsg.getMessage("rss.3"));
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), reqMdl));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param reqMdl リクエスト情報
     * @return String
     */
    public String getPgName(String id, RequestModel reqMdl) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        GsMessage gsMsg = new GsMessage(reqMdl);

        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.rss.newranking.RssNewRankingAction")) {
            //メイン RSS新着一覧
            return gsMsg.getMessage("rss.new.man.rss");
        }
        if (id.equals("jp.groupsession.v2.rss.rss010.Rss010Action")) {
            //RSS一覧
            return gsMsg.getMessage("rss.rssmain.1");
        }
        if (id.equals("jp.groupsession.v2.rss.rss030.Rss030Action")) {
            //RSS登録・編集
            return gsMsg.getMessage("rss.subscribe.to.edit");
        }
        if (id.equals("jp.groupsession.v2.rss.rss040.Rss040Action")) {
            //RSSリーダー登録ランキング
            return gsMsg.getMessage("rss.reader.rank");
        }
        if (id.equals("jp.groupsession.v2.rss.rss060.Rss060Action")) {
            //個人設定 メイン画面表示設定
            return gsMsg.getMessage("rss.set.main.dsp");
        }
        if (id.equals("jp.groupsession.v2.rss.rss080.Rss080Action")) {
            //管理者設定 メンテナンス
            return gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("rss.rss070.1");
        }
        if (id.equals("jp.groupsession.v2.rss.rss100.Rss100Action")) {
            //個人設定 新着RSS表示日数設定
            return gsMsg.getMessage("cmn.preferences2") + " "
                        + gsMsg.getMessage("cmn.display.settings");
        }
        return ret;
    }

    /**
     * <p>stopRssUpdateFlg を取得します。
     * @param domain ドメイン
     * @return stopRssUpdateFlg
     */
    public static boolean isStopRssUpdateFlg(String domain) {

        boolean flg = false;
        if (stopRssUpdateFlgMap__.get(domain) != null) {
            flg = stopRssUpdateFlgMap__.get(domain);
        } else {
            stopRssUpdateFlgMap__.put(domain, false);
        }
        return flg;
    }

    /**
     * <p>stopRssUpdateFlg をセットします。
     * @param domain ドメイン
     * @param stopRssUpdateFlg stopRssUpdateFlg
     */
    public static void setStopRssUpdateFlg(String domain, boolean stopRssUpdateFlg) {
        stopRssUpdateFlgMap__.put(domain, stopRssUpdateFlg);
    }

    /**
     * <p>rssUpdateCount を取得します。
     * @param domain ドメイン
     * @return rssUpdateCount
     */
    public static int getRssUpdateCount(String domain) {

        int cnt = 0;
        if (rssUpdateCountMap__.get(domain) != null) {
            cnt = rssUpdateCountMap__.get(domain).intValue();
        }
        return cnt;
    }
    /**
     * <p>rssUpdateCount を初期値設定します。
     * @param domain ドメイン
     * @return 初期値
     */
    private static synchronized AtomicInteger __initRssUpdateCount(String domain) {

        AtomicInteger atmCnt = rssUpdateCountMap__.get(domain);
        if (atmCnt == null) {
            atmCnt = new AtomicInteger(0);
            rssUpdateCountMap__.put(domain, atmCnt);
        }
        return atmCnt;
    }

    /**
     *
     * <br>[機  能] rssUpdateCount をカウントアップします。
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     */
    public static void incRssUpdateCount(String domain) {
        AtomicInteger cnt = rssUpdateCountMap__.get(domain);
        if (cnt == null) {
            cnt = __initRssUpdateCount(domain);
        }
        cnt.incrementAndGet();
    }
    /**
     *
     * <br>[機  能] rssUpdateCount をカウントダウンします。
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     */
    public static void decRssUpdateCount(String domain) {
        AtomicInteger cnt = rssUpdateCountMap__.get(domain);
        if (cnt == null) {
            cnt = __initRssUpdateCount(domain);
        }
        cnt.decrementAndGet();
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                                GSConstRss.PLUGIN_ID_RSS,
                                                dirId);
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     */
    public void deleteTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl,
                                    GSConstRss.PLUGIN_ID_RSS,
                                    dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void createTempDir(RequestModel reqMdl, String dirId)
    throws IOToolsException {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl,
                                GSConstRss.PLUGIN_ID_RSS,
                                dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリの初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl, String dirId)
    throws IOToolsException {
        //テンポラリディレクトリの削除後、再作成
        deleteTempDir(reqMdl, dirId);
        createTempDir(reqMdl, dirId);
    }

    /**
     * [機  能] バッチ処理用のテンポラリディレクトリパスを返す<br>
     * [解  説]
     * [備  考]
     * @param domain ドメイン
     * @param batchId バッチID(batch5Min 等)
     * @return パス
     */
    public String getBatchTempDir(String domain, String batchId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        return tempPathUtil.getBatchTempPath(domain,
                                            batchId,
                                            GSConstRss.PLUGIN_ID_RSS,
                                            String.valueOf(GSConst.SYSTEM_USER_ADMIN));
    }

    /**
     * [機  能] アプリケーションのテンポラリディレクトリのパスを返す<br>
     * [解  説] <br>/${tmpルート}/rss/0/ を返す。
     * [備  考] <br>
     * @param domain ドメイン
     * @param batchId バッチID(batch5Min 等)
     */
    public void deleteBatchTempDir(String domain, String batchId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteBatchTempPath(domain,
                                        batchId,
                                        GSConstRss.PLUGIN_ID_RSS);
    }

    /**
     * <br>[機  能] RSSマスタの更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエストモデル
     * @param cntCon MlCountMtController
     * @param dirPath 
     * @return true: RSSマスタ更新 false: RSSマスタ更新中断
     * @throws Exception 実行例外
     */
    public boolean updateRssMasta(Connection con, int userSid,
            RequestModel reqMdl, MlCountMtController cntCon, File dirPath)
                    throws Exception {
        log__.debug("START");

        String domain = reqMdl.getDomain();
        RssBiz.incRssUpdateCount(domain);

        try {
            //ファイル保存先ディレクトリを設定
            IOTools.isDirCheck(dirPath.getPath(), true);
            File[] rssFileList = dirPath.listFiles();

            ITempFileUtil tempFileUtil = (ITempFileUtil) GroupSession
                    .getContext().get(GSContext.TEMP_FILE_UTIL);

            RssInfomModel rssInfoMdl = new RssInfomModel();
            rssInfoMdl.setRsmUpdateTime(new UDate());
            rssInfoMdl.setRsmEuid(userSid);
            rssInfoMdl.setRsmEdate(rssInfoMdl.getRsmUpdateTime());

            ObjectFile objFile = null;
            for (File rssFile : rssFileList) {
                if (RssBiz.isStopRssUpdateFlg(reqMdl.getDomain())) {
                    return false;
                }

                objFile = new ObjectFile(rssFile.getPath());
                GSFeedList rssData = (GSFeedList) objFile.load();

                if (!rssData.isReadError()) {
                    int rssSid = Integer.parseInt(rssFile.getName());
                    rssInfoMdl.setRssSid(rssSid);
                    tempFileUtil.updateFeedData(con, rssInfoMdl, rssFile, cntCon);
                }
            }
        } finally {
            RssBiz.decRssUpdateCount(domain);

            //テンポラリディレクトリを削除
            deleteTempDir(reqMdl, dirPath.getPath());
        }

        log__.debug("End");
        return true;
    }
}