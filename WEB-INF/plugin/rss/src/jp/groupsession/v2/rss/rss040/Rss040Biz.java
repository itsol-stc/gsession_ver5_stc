package jp.groupsession.v2.rss.rss040;

import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.dao.RssDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.model.RssModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] RSSリーダー 登録ランキング画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss040Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param itmp TempFileUtil
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setInitData(Rss040ParamModel paramMdl, Connection con, ITempFileUtil itmp,
                            int userSid, RequestModel reqMdl)
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
        int page = paramMdl.getRss040page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setRss040page1(page);
        paramMdl.setRss040page2(page);
        paramMdl.setStartIndex((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setPageLabelList(PageUtil.createPageOptions(searchCnt, maxCnt));

        //RSS集計情報を取得
        RssDao rssDao = new RssDao(con);
        List<RssModel> resultList = rssDao.getRssCountDataList(itmp, page, maxCnt, userSid);

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);
        JDBCUtil.closeConnection(con);
        RssBiz rssBiz = new RssBiz(reqMdl);
        resultList = rssBiz.getRanking(resultList, httpOperation);
        paramMdl.setResultList(resultList);

        log__.debug("End");
    }
}
