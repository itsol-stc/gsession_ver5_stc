package jp.groupsession.v2.rss.rss010;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.dao.RssAconfDao;
import jp.groupsession.v2.rss.dao.RssDao;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.dao.RssPositionDao;
import jp.groupsession.v2.rss.dao.RssUconfDao;
import jp.groupsession.v2.rss.model.RssAconfModel;
import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.rss.model.RssPositionModel;
import jp.groupsession.v2.rss.model.RssUconfModel;

/**
 * <br>[機  能] RSSリーダー一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss010Biz {
    /** 画面ID */
    public static final String SCR_ID = "rss010";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss010Biz.class);

    /**
     * <br>[機  能] RSSフィード情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userMdl セッションユーザ情報
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void setFeedList(Rss010ParamModel paramMdl, Connection con,
                            RequestModel reqMdl,
                            BaseUserModel userMdl,
                            MlCountMtController cntCon) throws Exception {
        log__.debug("START");

        //管理者設定ボタン表示フラグを設定
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstRss.PLUGIN_ID_RSS);
        if (adminUser) {
            paramMdl.setRss010viewAdminBtn(1);
        }

        int userSid = userMdl.getUsrsid();

        RssDataDao rssDataDao = new RssDataDao(con);
        RssInfomDao rssInfoDao = new RssInfomDao(con);

        con.setAutoCommit(true);
        //左側の登録RSS情報を取得する。
        ITempFileUtil tempFileUtil = (ITempFileUtil) GroupSession.getContext()
                .get(GSContext.TEMP_FILE_UTIL);
        List<RssModel> rssDataList = rssDataDao.getRssDataList(tempFileUtil,
                userSid, GSConstRss.RSS_MAIN_VIEWFLG_NOT_MAINVIEW);
        
        //RSS登録ランキング一覧を取得
        RssDao rssDao = new RssDao(con);
        List<RssModel> rankingList = rssDao.getRssCountDataList(tempFileUtil,
                GSConstRss.DSP_PAGE_NUM, GSConstRss.RANKING_MAX_COUNT, userSid);

        //新着ランキング表示日数を取得
        RssUconfDao ruDao = new RssUconfDao(con);
        RssUconfModel uconfMdl = ruDao.select(userSid);
        int newRunkDspDay = GSConstRss.NEW_DEFO_DSP_COUNT;
        if (uconfMdl != null) {
            newRunkDspDay = uconfMdl.getRucNewCnt();
        }
        newRunkDspDay = newRunkDspDay * (-1);

        //新着ランキング一覧を取得する
        ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                GSContext.TEMP_FILE_UTIL);
        List<RssInfomModel> rssNewrankingList = rssInfoDao.getNewRankingRssList(itmp,
                GSConstRss.NEWRANKING_MAX_COUNT, userSid, newRunkDspDay);

        con.setAutoCommit(false);
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

        //テンポラリディレクトリパスを取得
        String dir = getTempDir(reqMdl);

        HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);
        List<String> updateRssSidList = new ArrayList<String>();

        //左側の登録RSS情報を設定する。
        RssBiz rssBiz = new RssBiz(reqMdl);
        List<GSFeedList> flist =
            rssBiz.setRssList(rssDataList, updateTime, httpOperation, dir, updateRssSidList,
                            reqMdl.getDomain());
        if (updateRssSidList.size() > 0) {
            File dirPath = new File(getTempDir(reqMdl));
            rssBiz.updateRssMasta(con, userSid, reqMdl, cntCon, dirPath);
        }
        //URLが規定フォーマットではない場合、変更する
        for (int idx = 0; idx < flist.size(); idx++) {
            if (!ValidateUtil.isHttpUrlFormat(flist.get(idx).getFurl())) {
                flist.get(idx).setFurl("");
            }
        }

        paramMdl.setRss010SidUpdate(updateRssSidList);
        paramMdl.setRss010Flist(flist);

        //登録ランキング一覧を設定
        rankingList = rssBiz.getRanking(rankingList, httpOperation);
        paramMdl.setRankingList(rankingList);

        //新着ランキング一覧を設定
        rssNewrankingList = rssBiz.getNewranking(rssNewrankingList, httpOperation);
        paramMdl.setNewRankingList(rssNewrankingList);

        //テンポラリディレクトリを削除
        deleteTempDir(reqMdl);

        log__.debug("End");
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
     */
    public void saveRssPosition(Connection con, int userSid,
                                String[] rssSidListLeft, String[] rssSidListRight)
    throws Exception {

        log__.debug("-- saveMainScreenPosition START --");

        RssPositionDao rssPositionDao = new RssPositionDao(con);
        rssPositionDao.deleteUsersPosition(userSid);

        //フォーラム集計情報の登録
        RssPositionModel rssPositionModel = new RssPositionModel();
        rssPositionModel.setUsrSid(userSid);
        rssPositionModel.setRspOrder(0); //自動採番用に0を設定
        rssPositionModel.setRspAuid(userSid);
        rssPositionModel.setRspAdate(new UDate());
        rssPositionModel.setRspEuid(userSid);
        rssPositionModel.setRspEdate(rssPositionModel.getRspAdate());

        if (rssSidListLeft != null) {
            for (String rssSid : rssSidListLeft) {
                rssPositionModel.setRssSid(Integer.parseInt(rssSid));
                rssPositionModel.setRspPosition(GSConstRss.RSS_POSITIONFLG_LEFT);
                rssPositionDao.insert(rssPositionModel);
            }
        }

        if (rssSidListRight != null) {
            for (String rssSid : rssSidListRight) {
                rssPositionModel.setRssSid(Integer.parseInt(rssSid));
                rssPositionModel.setRspPosition(GSConstRss.RSS_POSITIONFLG_RIGHT);
                rssPositionDao.insert(rssPositionModel);
            }
        }

        log__.debug("-- saveMainScreenPosition END --");
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
