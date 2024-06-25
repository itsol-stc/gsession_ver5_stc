package jp.groupsession.v2.bmk.bmk070;

import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDao;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDataDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.dao.BmkUrlDao;
import jp.groupsession.v2.bmk.dao.BmkUrlDataDao;
import jp.groupsession.v2.bmk.model.BmkBookmarkDataModel;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.bmk.model.BmkUrlDataModel;
import jp.groupsession.v2.bmk.model.BmkUrlModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>コメント・評価画面のビジネスロジッククラス
 * @author JTS
 *
 */
public class Bmk070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk070Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** 登録ユーザなし */
    public static final int BOOKMARK_NOT_USER = 1;
    /** 公開ブックマークなし */
    public static final int BOOKMARK_NOT_PUBLIC = 2;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bmk070Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk070ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @param appRoot アプリケーションのルートパス
     * @throws Exception 実行例外
     */
    public void setInitData(Bmk070ParamModel paramMdl, Connection con, int userSid,
        String appRoot) throws Exception {
        log__.debug("START");

        //表示件数の取得
        BmkUconfDao dao = new BmkUconfDao(con);
        BmkUconfModel model = dao.select(userSid);

        int maxCnt = Integer.parseInt(GSConstBookmark.DEFAULT_BMKCOUNT);

        if (model != null) {
            maxCnt = model.getBucCount();
        }

        int bmuSid = paramMdl.getEditBmuSid();

        //指定URLSIDがユーザブックマークに登録済みか判定
        BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
        BmkBookmarkModel bmkMdl = bmkDao.getUsrBookmark(bmuSid, userSid);

        if (bmkMdl == null) {
            paramMdl.setEditBmkSid(-1);
        } else {
            paramMdl.setEditBmkSid(bmkMdl.getBmkSid());
        }

        //URL情報の設定
        BmkUrlDataDao urlDataDao = new BmkUrlDataDao(con, reqMdl__);
        BmkUrlDataModel urlDataMdl = urlDataDao.getUrlInfo(bmuSid);

        if (urlDataMdl == null) {
            //ブックマークユーザなし
            BmkUrlDao urlDao = new BmkUrlDao(con);
            BmkUrlModel urlMdl = urlDao.select(bmuSid);

            if (urlMdl == null) {
                //指定URL情報なし(削除からの戻り遷移)
                log__.debug("*****指定URL情報なし");
                return;
            }

            urlDataMdl = new BmkUrlDataModel();
            urlDataMdl.setBmuUrl(urlMdl.getBmuUrl());
            urlDataMdl.setBmuTitle(urlMdl.getBmuTitle());
            urlDataMdl.setBmuSid(urlMdl.getBmuSid());
            BookmarkBiz bBiz = new BookmarkBiz(con, reqMdl__);
            urlDataMdl.setBmkTitleDspList(bBiz.getStringCutList(60, urlMdl.getBmuTitle()));
            urlDataMdl.setBmkUrlDspList(bBiz.getStringCutList(90, urlMdl.getBmuUrl()));

            paramMdl.setBmk070NotViewBmk(BOOKMARK_NOT_USER);
            paramMdl.setBmk070ResultUrl(urlDataMdl);
            return;
        }

        paramMdl.setBmk070ResultUrl(urlDataMdl);

        //最大件数
        BmkBookmarkDataDao bmkDataDao = new BmkBookmarkDataDao(con, reqMdl__);
        int searchCnt = bmkDataDao.getBookmarkCount(paramMdl.getEditBmuSid());
        if (searchCnt == 0) {
            //公開ブックマーク件数0件
            log__.debug("*****公開ブックマーク件数0件");
            paramMdl.setBmk070NotViewBmk(BOOKMARK_NOT_PUBLIC);
            return;
        }

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBmk070PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setBmk070PageTop(page);
        paramMdl.setBmk070PageBottom(page);
        paramMdl.setBmk070Page((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setBmk070PageLabelList(PageUtil.createPageOptions(searchCnt, maxCnt));

        //各登録ブックマーク情報取得
        List<BmkBookmarkDataModel> resultList
            = bmkDataDao.getCommentList(page, maxCnt, bmuSid, appRoot,
                paramMdl.getBmk070OrderKey(), paramMdl.getBmk070SortKey());

        BmkLabelDao lblDao = new BmkLabelDao(con);
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.no");

        //取得ブックマーク一覧のラベル情報設定
        for (BmkBookmarkDataModel list : resultList) {
            String lblName = lblDao.getBookmarkLabel(list.getBmkSid());
            if (lblName.equals("")) {
                lblName = msg;
            }
            list.setLabelName(lblName);
        }
        paramMdl.setBmk070ResultList(resultList);

        log__.debug("End");
    }
}
