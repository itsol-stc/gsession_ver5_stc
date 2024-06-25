package jp.groupsession.v2.bmk.bmk080;

import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.dao.BmkUrlDataDao;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.bmk.model.BmkUrlDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>登録ランキング画面のビジネスロジッククラス
 * @author JTS
 *
 */
public class Bmk080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk080Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bmk080Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk080ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Bmk080ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        //表示件数の取得
        BmkUconfDao dao = new BmkUconfDao(con);
        BmkUconfModel model = dao.select(userSid);

        int maxCnt = Integer.parseInt(GSConstBookmark.DEFAULT_BMKCOUNT);

        if (model != null) {
            maxCnt = model.getBucCount();
        }

        //最大件数
        BmkUrlDataDao urlDao = new BmkUrlDataDao(con, reqMdl__);
        int searchCnt = urlDao.getUrlBmkCount();

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBmk080PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBmk080PageTop(page);
        paramMdl.setBmk080PageBottom(page);
        paramMdl.setBmk080Page((page - 1) * maxCnt);

        log__.debug("PAGE = " + paramMdl.getBmk080Page());
        log__.debug("PAGE TOP = " + paramMdl.getBmk080PageTop());
        log__.debug("PAGE BOTTOM = " + paramMdl.getBmk080PageBottom());
        log__.debug("searchCnt = " + searchCnt);
        log__.debug("maxCnt = " + maxCnt);

        //ページコンボ設定
        paramMdl.setBmk080PageLabelList(PageUtil.createPageOptions(searchCnt, maxCnt));

        List<BmkUrlDataModel> resultList = urlDao.getRankingBmkList(page, maxCnt, userSid);
        paramMdl.setBmk080ResultList(resultList);

        log__.debug("End");
    }
}
