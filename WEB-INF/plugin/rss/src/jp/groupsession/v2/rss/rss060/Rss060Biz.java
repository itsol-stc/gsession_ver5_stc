package jp.groupsession.v2.rss.rss060;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rometools.rome.feed.synd.SyndEntry;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.dao.RssAconfDao;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssPositionMainDao;
import jp.groupsession.v2.rss.model.RssAconfModel;
import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.rss.model.RssPositionMainModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] RSSリーダー メイン画面表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss060Biz {
    /** 画面ID */
    public static final String SCR_ID = "rss060";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss060Biz.class);

    /**
     * <br>[機  能] RSSフィード情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void setFeedList(Rss060ParamModel paramMdl, Connection con, int userSid,
                            RequestModel reqMdl, MlCountMtController cntCon) throws Exception {
        log__.debug("START");

        //RSS情報一覧を取得する
        RssDataDao rssDataDao = new RssDataDao(con);
        ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                GSContext.TEMP_FILE_UTIL);
        List<RssModel> rssDataList = rssDataDao.getMainRssDataList(itmp, userSid);

        //RSS管理者情報を取得する
        RssAconfDao aconfDao = new RssAconfDao(con);
        RssAconfModel aconfMdl = aconfDao.select();
        UDate updateTime = new UDate();
        if (aconfMdl == null) {

            boolean commit = false;
            try {
                //設定がない場合
                UDate now = new UDate();
                aconfMdl = new RssAconfModel();
                aconfMdl.setRacReadtime(GSConstRss.RSS_INF_UPDATE_TIME);
                aconfMdl.setRacAuid(GSConst.SYSTEM_USER_ADMIN);
                aconfMdl.setRacAdate(now);
                aconfMdl.setRacEuid(GSConst.SYSTEM_USER_ADMIN);
                aconfMdl.setRacEdate(now);
                aconfDao.insert(aconfMdl);

                con.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("RSS管理者設定の登録に失敗", e);
                throw e;
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        }

        updateTime.addMinute(aconfMdl.getRacReadtime() * -1);

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);

        List<String> updateRssSidList = new ArrayList<String>();
        String dir = getTempDir(reqMdl);

        //RSS情報一覧を設定
        Map<Integer, GSFeedList> updateFeedMap = new HashMap<Integer, GSFeedList>();
        List<GSFeedList> flist =
            __setRssList(rssDataList, updateTime, httpOperation, dir,
                        updateRssSidList, updateFeedMap, reqMdl);

        if (updateRssSidList.size() > 0) {
            File dirPath = new File(getTempDir(reqMdl));
            RssBiz rssBiz = new RssBiz(reqMdl);
            rssBiz.updateRssMasta(con, userSid, reqMdl, cntCon, dirPath);
        }

        //URLが規定フォーマットではない場合、変更する
        for (int idx = 0; idx < flist.size(); idx++) {
            if (!ValidateUtil.isHttpUrlFormat(flist.get(idx).getFurl())) {
                flist.get(idx).setFurl("");
            }
        }
        
        paramMdl.setRss060SidUpdate(updateRssSidList);
        paramMdl.setRss060Flist(flist);

        //テンポラリディレクトリ削除
        deleteTempDir(reqMdl);

        log__.debug("End");
    }

    /**
     * <br>[機  能] RSS情報一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param rssDataList RSS一覧
     * @param updateTime システム時刻
     * @param httpOperation HttpOperation
     * @param dir RSSフィード情報保存用ディレクトリパス
     * @param updateRssSidList 更新をかけるRSSSIDのリスト
     * @param updateFeedMap Map
     * @param reqMdl リクエスト情報
     * @return List in GSFeedList
     * @throws Exception 実行例外
     */
    private List<GSFeedList> __setRssList(
        List<RssModel> rssDataList,
        UDate updateTime,
        HttpOperation httpOperation,
        String dir,
        List<String> updateRssSidList,
        Map<Integer, GSFeedList> updateFeedMap,
        RequestModel reqMdl) throws Exception {

        RssBiz rssBiz = new RssBiz(reqMdl);
        List<GSFeedList> flist = new ArrayList<GSFeedList>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        //データを取得できませんでした
        String textNoData = gsMsg.getMessage("rss.31");
        for (RssModel rssData : rssDataList) {
            GSFeedList feed = rssData.getFeedData();
            if (feed != null) {
                feed.setFeedUrl(rssData.getRsdUrlFeed());
            } else {
                feed = rssBiz.getRssFeed(rssData, httpOperation);
                feed.setFtitle(textNoData);
                feed.setFeedSid(rssData.getRssSid());
                feed.setFurl(rssData.getRsdUrl());
                feed.setReadError(true);
            }

            boolean addFeed = true;
            int rssViewCount = rssData.getRsdFeedCount();

            rssData.setRsdFeedCount(GSConstRss.RSS_FEED_COUNT);
            if (feed == null
            || rssData.getFeedUpdateTime().getTimeMillis() < updateTime.getTimeMillis()) {
                try {
                    feed = rssBiz.getRssFeed(rssData, httpOperation);

                    IOTools.isDirCheck(dir, true);
                    ObjectFile objFile = new ObjectFile(dir, String.valueOf(rssData.getRssSid()));
                    objFile.save(feed);

                    updateRssSidList.add(String.valueOf(rssData.getRssSid()));
                    updateFeedMap.put(Integer.valueOf(feed.getFeedSid()), feed);
                } catch (Exception e) {
                    log__.error("RSSフィードの読み込みに失敗 :" + rssData.getRsdTitle(), e);
                    addFeed = false;
                }
            } else {
                feed.setFtitle(rssData.getRsdTitle());
                feed.setFurl(rssData.getRsdUrl());
                feed.setFeedPosition(rssData.getRspPosition());
            }

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
        }
        return flist;
    }

    /**
     * <br>[機  能] RSS位置設定設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param rssSidListLeft RSSSID一覧(左)
     * @param rssSidListRight RSSSID一覧(右)
     * @throws Exception SQL実行時例外
     * @return RSS位置情報モデル
     */
    public RssPositionMainModel saveRssPositionMain(Connection con, int userSid,
                                String[] rssSidListLeft, String[] rssSidListRight)
    throws Exception {

        log__.debug("-- saveRssPositionMain START --");

        RssPositionMainDao rssPositionMainDao = new RssPositionMainDao(con);
        rssPositionMainDao.deleteUsersPosition(userSid);

        //フォーラム集計情報の登録
        RssPositionMainModel rssPositionMainModel = new RssPositionMainModel();
        rssPositionMainModel.setUsrSid(userSid);
        rssPositionMainModel.setRpmOrder(0); //自動採番用に0を設定
        rssPositionMainModel.setRpmAuid(userSid);
        rssPositionMainModel.setRpmAdate(new UDate());
        rssPositionMainModel.setRpmEuid(userSid);
        rssPositionMainModel.setRpmEdate(rssPositionMainModel.getRpmAdate());

        __insertRssPositionMain(rssPositionMainDao, rssPositionMainModel,
                                rssSidListLeft, GSConstRss.RSS_POSITIONFLG_LEFT);
        __insertRssPositionMain(rssPositionMainDao, rssPositionMainModel,
                                rssSidListRight, GSConstRss.RSS_POSITIONFLG_RIGHT);

        log__.debug("-- saveRssPositionMain END --");
        return rssPositionMainModel;
    }

    /**
     * <br>[機  能] RSS位置情報_メイン画面 の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rssPositionMainDao RSS位置情報_メイン画面DAO
     * @param rssPositionMainModel RSS位置情報_メイン画面Model
     * @param rssSidList RSSSID
     * @param position 位置(左 or 右)
     * @throws SQLException SQL実行時例外
     */
    private void __insertRssPositionMain(RssPositionMainDao rssPositionMainDao,
                                        RssPositionMainModel rssPositionMainModel,
                                        String[] rssSidList, int position)
    throws SQLException {

        if (rssSidList != null) {
            int order = 0;
            List<Integer> addRssSidList = new ArrayList<Integer>();
            rssPositionMainModel.setRpmPosition(position);

            for (String strRssSid : rssSidList) {
                int rssSid = Integer.parseInt(strRssSid);
                if (!addRssSidList.contains(rssSid)) {
                    rssPositionMainModel.setRssSid(rssSid);
                    rssPositionMainModel.setRpmOrder(order);
                    rssPositionMainDao.insert(rssPositionMainModel);
                    order++;

                    addRssSidList.add(rssSid);
                }
            }
        }
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        RssBiz rssBiz = new RssBiz();
        return rssBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        RssBiz rssBiz = new RssBiz();
        rssBiz.deleteTempDir(reqMdl, SCR_ID);
    }
}
