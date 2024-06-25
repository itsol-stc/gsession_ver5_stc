package jp.groupsession.v2.anp.anp130;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.anp130.dao.Anp130Dao;
import jp.groupsession.v2.anp.anp130.model.Anp130SenderModel;
import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;


/**
 * <br>[機  能] 管理者設定・配信履歴画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp130Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp130Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp130Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp130ParamModel anp130Model,
            RequestModel reqMdl,
                            Connection con)
                            throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        Anp130Dao dao = new Anp130Dao(con);

        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //送信者一覧のページ内容をセット
        int limit = priConf.getAppListCount();  //表示行数
        int maxCount = dao.getListCount();      //全データ数

        int nowPage = anp130Model.getAnp130NowPage();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }

        log__.debug("全データ数　 = " + maxCount);
        anp130Model.setAnp130NowPage(nowPage);
        anp130Model.setAnp130DspPage1(nowPage);
        anp130Model.setAnp130DspPage2(nowPage);
        anp130Model.setAnp130PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //配信履歴リストを取得
        List<Anp130SenderModel> list = dao.getListInfo(start, limit);
        anp130Model.setAnp130HaisinList(list);

    }

    /**
     * <br>[機  能] 選択された配信履歴一覧データを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp130Model パラメータモデル
     * @param con DBコネクション
     * @throws SQLException SQL実行例外
     * @throws IOException IO例外
     * @throws IOToolsException IOTools例外
     */
    public void doDelete(Anp130ParamModel anp130Model, Connection con)
                throws SQLException, IOToolsException, IOException {

        log__.debug("配信履歴データ削除");
        boolean commitFlg = false;

        try {
            con.setAutoCommit(false);

            AnpHdataDao hDao = new AnpHdataDao(con);
            AnpJdataDao jDao = new AnpJdataDao(con);
            List<String> list = new ArrayList<String>();
            list = Arrays.asList(anp130Model.getAnp130DelSidList());

            con.setAutoCommit(false);
            AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
            for (String anpSid : list) {
                log__.debug("配信履歴SID = " + anpSid);
                adsDao.insertHdataDelDiff(Arrays.asList(Integer.parseInt(anpSid)));
                adsDao.insertJdataDelDiff(Integer.parseInt(anpSid));
                
                hDao.delete(Integer.parseInt(anpSid));
                jDao.delete(Integer.parseInt(anpSid));
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

}
