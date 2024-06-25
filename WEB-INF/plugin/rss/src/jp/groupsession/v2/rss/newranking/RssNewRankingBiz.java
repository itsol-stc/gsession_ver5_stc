package jp.groupsession.v2.rss.newranking;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.dao.RssUconfDao;
import jp.groupsession.v2.rss.model.RssUconfModel;

/**
 * <br>[機  能] RSSリーダー 新着RSS(メイン画面表示用)のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssNewRankingBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssNewRankingBiz.class);

    /**
     * <br>[機  能] RSSフィード情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setFeedList(RssNewRankingParamModel paramMdl, Connection con,
            int userSid, RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        con.setAutoCommit(false);
        boolean commitFlg = false;

        List<RssInfomModel> rssNewrankingList = null;
        try {

            //新着ランキング表示日数を取得
            RssUconfDao ruDao = new RssUconfDao(con);
            RssUconfModel uconfMdl = ruDao.select(userSid);
            int newRunkDspDay = GSConstRss.NEW_DEFO_DSP_COUNT;
            if (uconfMdl != null) {
                newRunkDspDay = uconfMdl.getRucNewCnt();
            }
            newRunkDspDay = newRunkDspDay * (-1);

            //RSS新着ランキング一覧を取得する
            RssInfomDao rssInfoDao = new RssInfomDao(con);
            ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                    GSContext.TEMP_FILE_UTIL);
            rssNewrankingList = rssInfoDao.getNewRankingRssList(itmp,
                    GSConstRss.NEWRANKING_MAX_COUNT, userSid, newRunkDspDay);
            
            commitFlg = true;
        } catch (Exception e) {
            log__.error("RSSフィード情報一覧に失敗", e);
            throw e;
        } finally {
            if (commitFlg == true) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);
        JDBCUtil.closeConnection(con);

        //新着ランキング一覧を設定
        RssBiz rssBiz = new RssBiz(reqMdl);
        rssNewrankingList = rssBiz.getNewranking(rssNewrankingList, httpOperation);
        paramMdl.setNewRankingList(rssNewrankingList);
        
        //URLが規定フォーマットではない場合、変更する
        for (int idx = 0; idx < rssNewrankingList.size(); idx++) {
            if (!ValidateUtil.isHttpUrlFormat(
                    rssNewrankingList.get(idx).getUrl())) {
                rssNewrankingList.get(idx).setUrl("");
            }
        }

        log__.debug("End");
    }
}
