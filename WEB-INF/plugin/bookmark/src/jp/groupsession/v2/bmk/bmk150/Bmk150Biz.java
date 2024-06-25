package jp.groupsession.v2.bmk.bmk150;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010InfoModel;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 新着ブックマーク一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk150Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bmk150Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk150ParamModel
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(Bmk150ParamModel paramMdl, Connection con, BaseUserModel userMdl)
    throws Exception {
        log__.debug("初期表示処理");
        Bmk150Dao bmkDao = new Bmk150Dao(con, reqMdl__);
        //個人設定情報取得
        BmkUconfDao ucDao = new BmkUconfDao(con);
        BmkUconfModel ucMdl = new BmkUconfModel();

        List<Bmk010InfoModel> newBmkList = new ArrayList<Bmk010InfoModel>();

        //新着ブックマーク表示日数を設定
        ucMdl = ucDao.select(userMdl.getUsrsid());
        int newBmkDspCnt = GSConstBookmark.NEW_DEFO_DSP_COUNT;
        if (ucMdl != null) {
            newBmkDspCnt = ucMdl.getBucNewCnt();
        }

        //全データ件数
        int maxCount = bmkDao.cntNewBmk(userMdl.getUsrsid(), newBmkDspCnt);
        if (maxCount == 0) {
            paramMdl.setBmk150NewList(newBmkList);
            return;
        }

        int limit = 10;

        //現在ページ、スタート行
        int nowPage = paramMdl.getBmk150PageNum();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }

        //新着ブックマーク情報を取得
        newBmkList = bmkDao.selectNewBmk(userMdl.getUsrsid(),
                                         newBmkDspCnt,
                                         offset,
                                         limit);

        //ページング
        paramMdl.setBmk150PageNum(nowPage);
        paramMdl.setBmk150Slt_page1(nowPage);
        paramMdl.setBmk150Slt_page2(nowPage);
        paramMdl.setBmk150PageLabel(PageUtil.createPageOptions(maxCount, limit));

        paramMdl.setBmk150NewList(newBmkList);
    }
}
