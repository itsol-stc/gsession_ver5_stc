package jp.groupsession.v2.rss.rss030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.RssUsedDataBiz;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.dao.RssPositionDao;
import jp.groupsession.v2.rss.dao.RssPositionMainDao;
import jp.groupsession.v2.rss.model.RssDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] RSSリーダー RSS登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss030Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、RSS情報を設定する
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException 実行例外
     */
    public void setInitData(String cmd, RequestModel reqMdl, Rss030ParamModel paramMdl,
                            Connection con, int userSid)
    throws SQLException {
        log__.debug("START");

        List<LabelValueBean> viewCntList = new ArrayList<LabelValueBean>();
        for (int cnt = 1; cnt <= GSConstRss.MIDASI_MAX_COUNT; cnt++) {
            String count = String.valueOf(cnt);
            GsMessage gsMsg = new GsMessage(reqMdl);
            //件
            String textKen = gsMsg.getMessage("cmn.number");
            LabelValueBean label = new LabelValueBean(count + textKen, count);
            viewCntList.add(label);
        }
        paramMdl.setViewCntList(viewCntList);

        if (cmd.equals("rssEdit") && paramMdl.getRssCmdMode() == GSConstRss.RSSCMDMODE_EDIT) {
            RssDataDao rssDao = new RssDataDao(con);
            RssDataModel rssMdl = rssDao.getRssData(paramMdl.getRssSid(), userSid);

            paramMdl.setRssTitle(rssMdl.getRsdTitle());
            paramMdl.setRssFeedUrl(rssMdl.getRsdUrlFeed());
            paramMdl.setRssBeforeFeedUrl(rssMdl.getRsdUrlFeed());
            paramMdl.setRssUrl(rssMdl.getRsdUrl());
            paramMdl.setRss030ViewCnt(rssMdl.getRsdFeedCount());
            paramMdl.setRss030mainView(rssMdl.getRsdMainView());
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] RSS情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rssSid RSSSID
     * @param userSid ユーザSID
     * @throws SQLException 実行例外
     * @throws Exception RSSフィードデータ削除時に例外発生
     */
    public void deleteRssData(Connection con, int rssSid, int userSid)
    throws SQLException, Exception {
        log__.debug("START");

        //RSS情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        RssUsedDataBiz usedDataBiz = new RssUsedDataBiz(con);
        usedDataBiz.insertRssDataSize(rssSid, userSid, false);

        //RSS情報の削除
        RssDataDao rssDao = new RssDataDao(con);
        rssDao.delete(rssSid, userSid);

        //RSS位置情報の削除
        RssPositionDao rssPositionDao = new RssPositionDao(con);
        rssPositionDao.delete(rssSid, userSid);

        //削除対象のRSSマスタが実際に削除される場合
        //RSSマスタのデータ使用量を登録(削除対象のデータ使用量を減算)
        RssInfomDao rssInfoDao = new RssInfomDao(con);
        if (!rssInfoDao.existsUsedUser(rssSid)) {
            //RSSフィードデータの削除
            ITempFileUtil tempFileUtil = 
                    (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
            tempFileUtil.deleteAllLogicalDeletedRssFeedData(con, rssSid);            

            //削除対象のRSSを購読するユーザが存在しない = RSSマスタが削除されるため
            //データ使用量登録を行う
            usedDataBiz.insertRssInfoSize(rssSid, false);
        }

        //RSSマスタの削除
        rssInfoDao.deleteToDontUseData(rssSid);

        //RSS位置情報_メイン画面の削除
        RssPositionMainDao rssPositionMainDao = new RssPositionMainDao(con);
        rssPositionMainDao.delete(rssSid, userSid);

        log__.debug("End");
    }
    
    /**
     * <br>[機  能] RSS情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void insertRssData(Rss030ParamModel paramMdl,
                                Connection con,
                                MlCountMtController cntCon,
                                int userSid,
                                RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //更新用のRssDataModelを取得する
        RssDataModel rssModel = __getRssDataModel(paramMdl, userSid);

        RssBiz rssBiz = new RssBiz(reqMdl);
        rssBiz.entryRssData(con, cntCon, rssModel);

        log__.debug("End");
    }

    /**
     * <br>[機  能] RSS情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void updateRssData(Rss030ParamModel paramMdl,
                                Connection con,
                                MlCountMtController cntCon,
                                int userSid,
                                RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();
        int rssSid = paramMdl.getRssSid();

        RssUsedDataBiz usedDataBiz = new RssUsedDataBiz(con);

        //RSS情報を取得する
        RssDataDao rssDao = new RssDataDao(con);
        RssDataModel rssMdl = rssDao.getRssData(rssSid, userSid);
        if (!rssMdl.getRsdUrlFeed().equals(paramMdl.getRssFeedUrl())) {
            //RSS情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertRssDataSize(rssSid, userSid, false);

            //フィードURLが変更されていた場合は新規登録を行う
            rssDao.delete(rssSid, userSid);
            RssPositionDao positionDao = new RssPositionDao(con);
            positionDao.delete(rssSid, userSid);

            RssInfomDao rssInfoDao = new RssInfomDao(con);
            if (rssInfoDao.selectToDontUseData(rssSid) > 0) {
                //RSSフィードデータの削除
                ITempFileUtil tempFileUtil = 
                        (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
                tempFileUtil.deleteAllLogicalDeletedRssFeedData(con, rssSid);            
            }

            //削除対象のRSSを購読するユーザが存在しない = RSSマスタが削除されるため
            //RSSマスタのデータ使用量登録を行う(削除対象のデータ使用量を減算)
            if (!rssInfoDao.existsUsedUser(rssSid)) {
                usedDataBiz.insertRssInfoSize(rssSid, false);
            }

            //購読者が0人の場合、RSSマスタを削除する
            rssInfoDao.deleteToDontUseData(rssSid);

            //更新用のRssDataModelを取得する
            RssDataModel rssModel = __getRssDataModel(paramMdl, userSid);

            RssBiz rssBiz = new RssBiz(reqMdl);
            rssBiz.entryRssData(con, cntCon, rssModel);
        } else {

            //RSS情報のデータ使用量を登録(変更前)
            usedDataBiz.insertRssDataSize(rssSid, userSid, false);

            //RSS情報の更新
            RssDataModel rssModel = __getRssDataModel(paramMdl, userSid);
            rssModel.setRssSid(paramMdl.getRssSid());
            rssModel.setUsrSid(userSid);
            rssModel.setRsdAuth(paramMdl.getRssAuth());
            rssModel.setRsdAuthId(paramMdl.getRssAuthId());
            rssModel.setRsdAuthPswd(paramMdl.getRssAuthPswd());
            rssModel.setRsdEdate(now);

            rssDao.update(rssModel);

            //RSS情報のデータ使用量を登録
            usedDataBiz.insertRssDataSize(rssSid, userSid, true);
        }
        log__.debug("End");
    }

    /**
     * <br>[機  能] 更新用のRssDataModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @return RssDataModel
     */
    private RssDataModel __getRssDataModel(Rss030ParamModel paramMdl, int userSid) {

        RssDataModel rssModel = new RssDataModel();
        rssModel.setRsdUrlFeed(paramMdl.getRssFeedUrl());
        //スペース・改行を省く
        rssModel.setRsdTitle(paramMdl.getRssTitle());
        rssModel.setRsdUrl(paramMdl.getRssUrl());
        rssModel.setRsdMainView(paramMdl.getRss030mainView());
        rssModel.setRsdFeedCount(paramMdl.getRss030ViewCnt());
        rssModel.setRsdAuth(paramMdl.getRssAuth());
        rssModel.setRsdAuthId(paramMdl.getRssAuthId());
        rssModel.setRsdAuthPswd(paramMdl.getRssAuthPswd());
        rssModel.setRsdEuid(userSid);
        return rssModel;
    }

}
