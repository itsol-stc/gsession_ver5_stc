package jp.groupsession.v2.rss.main;

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
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.dao.RssAconfDao;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.model.RssAconfModel;
import jp.groupsession.v2.rss.model.RssModel;

/**
 * <br>[機  能] RSSリーダー(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssMainBiz {
    /** 画面ID */
    public static final String SCR_ID = "rssmain";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssMainBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssMainBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void setInitData(
        RssMainParamModel paramMdl,
        int userSid,
        RequestModel reqMdl,
        MlCountMtController cntCon) throws Exception {
        log__.debug("start");

        //RSS管理者情報を取得する
        RssAconfDao aconfDao = new RssAconfDao(con__);
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

                con__.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("RSS管理者設定の登録に失敗", e);
                throw e;
            } finally {
                if (!commit) {
                    con__.rollback();
                }
            }
        }

        updateTime.addMinute(aconfMdl.getRacReadtime() * -1);

        RssDataDao rssDataDao = new RssDataDao(con__);
        ITempFileUtil itmp = (ITempFileUtil) GroupSession.getContext().get(
                GSContext.TEMP_FILE_UTIL);
        List<RssModel> rssDataList = rssDataDao.getMainRssDataList(itmp, userSid);
        con__.setAutoCommit(true);

        HttpOperation httpOperation = new HttpOperation(con__, GSConstRss.TIMEOUT);

        String dir = getTempDir(reqMdl);

        List<String> updateRssSidList = new ArrayList<String>();
        RssBiz rssBiz = new RssBiz(reqMdl);

        try {

            List<GSFeedList> flist =
                rssBiz.setRssList(rssDataList, updateTime, httpOperation, dir, updateRssSidList,
                                reqMdl.getDomain());

            if (updateRssSidList.size() > 0) {
                File dirPath = new File(getTempDir(reqMdl));
                rssBiz.updateRssMasta(con__, userSid, reqMdl, cntCon, dirPath);
            }

            //URLが規定フォーマットではない場合、変更する
            for (int idx = 0; idx < flist.size(); idx++) {
                if (!ValidateUtil.isHttpUrlFormat(flist.get(idx).getFurl())) {
                    flist.get(idx).setFurl("");
                }
            }
            
            paramMdl.setSidUpdate(updateRssSidList);
            paramMdl.setFlist(flist);

        } finally {
            //テンポラリディレクトリを削除
            deleteTempDir(reqMdl);
        }

        log__.debug("end");
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
