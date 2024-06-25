package jp.groupsession.v2.rss.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssUsedDataBiz;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.dao.RssInfomDao;
import jp.groupsession.v2.rss.dao.RssPositionDao;
import jp.groupsession.v2.rss.dao.RssPositionMainDao;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] RSSリーダーについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssUserGroupListenerImpl implements IUserGroupListener {
    
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssUserGroupListenerImpl.class);

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param cntCon MlCountMtController
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon,
            Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

        //RSS情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        RssDataDao rssDao = new RssDataDao(con);
        List<Integer> rssSidList = rssDao.getRssSidList(usid);
        RssUsedDataBiz usedDataBiz = new RssUsedDataBiz(con);
        for (int rssSid : rssSidList) {
            usedDataBiz.insertRssDataSize(rssSid, usid, false);
        }

        //RSS位置情報の削除
        RssPositionDao rssPositionDao = new RssPositionDao(con);
        rssPositionDao.deleteUsersPosition(usid);

        //RSS位置情報_メイン画面の削除
        RssPositionMainDao rssPositionMainDao = new RssPositionMainDao(con);
        rssPositionMainDao.deleteUsersPosition(usid);

        //RSS情報の削除
        rssDao.deleteUsersRss(usid);

        //RSSマスタの削除
        RssInfomDao rssInfoDao = new RssInfomDao(con);
        for (Integer rssSid : rssSidList) {
            if (rssInfoDao.selectToDontUseData(rssSid) > 0) {
                //RSSフィードデータの削除
                ITempFileUtil tempFileUtil = 
                        (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
                try {
                    tempFileUtil.deleteAllLogicalDeletedRssFeedData(con, rssSid);
                } catch (Exception e) {
                    log__.error("rssFeedの削除に失敗" + e);
                }            
            }

            //RSSマスタのデータ使用量を登録(削除対象のデータ使用量を減算)
            if (!rssInfoDao.existsUsedUser(rssSid)) {
                usedDataBiz.insertRssInfoSize(rssSid, false);
            }

            rssInfoDao.deleteToDontUseData(rssSid);
        }
    }

    /**
     * <br>[機  能] グループ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid, int eusid) throws SQLException {
    }

    /**
     * <br>[機  能] グループ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(Connection con, int gsid, int eusid, RequestModel reqMdl)
         throws SQLException {
    }

    /**
     * <br>[機  能] ユーザの所属グループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids, int eusid)
    throws SQLException {
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid, int eusid)
    throws SQLException {
    }
}
