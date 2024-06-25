package jp.groupsession.v2.rss.rss090;

import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.rss.dao.RssDao;
import jp.groupsession.v2.rss.model.RssModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] RSSリーダー RSS購読メンバー一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss090Biz.class);

    /**
     * <br>[機  能] RSSフィード購読ユーザ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setUsrList(Rss090ParamModel paramMdl, Connection con)
    throws Exception {
        log__.debug("START");

        int rssSid = paramMdl.getRssSid();

        //最大件数
        RssDao rssInfoDao = new RssDao(con);
        int searchCnt = rssInfoDao.getRssUserCnt(rssSid);
        int maxCnt = GSConstRss.RSSRANKING_VIEWCNT;
        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getRss090page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setRss090page1(page);
        paramMdl.setRss090page2(page);
        paramMdl.setStartIndex((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setPageLabelList(PageUtil.createPageOptions(searchCnt, maxCnt));

        //RSS集計情報を取得
        RssDao rssDao = new RssDao(con);
        List<RssModel> resultList = rssDao.getRssUserList(page, maxCnt, rssSid);
        paramMdl.setUserDataList(resultList);

        log__.debug("End");
    }
}
