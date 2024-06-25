package jp.groupsession.v2.rss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.rss.model.RssDataModel;
import jp.groupsession.v2.rss.model.RssModel;

/**
 * <p>RSS_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssDataDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssDataDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSS_DATA Data Bindding JavaBean
     * @param bean RSS_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RssDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_DATA(");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSD_TITLE,");
            sql.addSql("   RSD_URL_FEED,");
            sql.addSql("   RSD_URL,");
            sql.addSql("   RSD_VIEW,");
            sql.addSql("   RSD_MAIN_VIEW,");
            sql.addSql("   RSD_FEED_COUNT,");
            sql.addSql("   RSD_AUTH,");
            sql.addSql("   RSD_AUTH_ID,");
            sql.addSql("   RSD_AUTH_PSWD,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getRsdTitle());
            sql.addStrValue(bean.getRsdUrlFeed());
            sql.addStrValue(bean.getRsdUrl());
            sql.addIntValue(bean.getRsdView());
            sql.addIntValue(bean.getRsdMainView());
            sql.addIntValue(bean.getRsdFeedCount());
            sql.addIntValue(bean.getRsdAuth());
            sql.addStrValue(bean.getRsdAuthId());
            sql.addStrValue(bean.getRsdAuthPswd());
            sql.addIntValue(bean.getRsdAuid());
            sql.addDateValue(bean.getRsdAdate());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update RSS_DATA Data Bindding JavaBean
     * @param bean RSS_DATA Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RssDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_DATA");
            sql.addSql(" set ");
            sql.addSql("   RSD_TITLE=?,");
            sql.addSql("   RSD_URL_FEED=?,");
            sql.addSql("   RSD_URL=?,");
            sql.addSql("   RSD_MAIN_VIEW=?,");
            sql.addSql("   RSD_FEED_COUNT=?,");
            sql.addSql("   RSD_AUTH=?,");
            sql.addSql("   RSD_AUTH_ID=?,");
            sql.addSql("   RSD_AUTH_PSWD=?,");
            sql.addSql("   RSD_EUID=?,");
            sql.addSql("   RSD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRsdTitle());
            sql.addStrValue(bean.getRsdUrlFeed());
            sql.addStrValue(bean.getRsdUrl());
            sql.addIntValue(bean.getRsdMainView());
            sql.addIntValue(bean.getRsdFeedCount());
            sql.addIntValue(bean.getRsdAuth());
            sql.addStrValue(bean.getRsdAuthId());
            sql.addStrValue(bean.getRsdAuthPswd());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            //where
            sql.addIntValue(bean.getRssSid());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <p>Select RSS_DATA All Data
     * @return List in RSS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RssDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssDataModel> ret = new ArrayList<RssDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSD_TITLE,");
            sql.addSql("   RSD_URL_FEED,");
            sql.addSql("   RSD_URL,");
            sql.addSql("   RSD_VIEW,");
            sql.addSql("   RSD_MAIN_VIEW,");
            sql.addSql("   RSD_FEED_COUNT,");
            sql.addSql("   RSD_AUTH,");
            sql.addSql("   RSD_AUTH_ID,");
            sql.addSql("   RSD_AUTH_PSWD,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssDataFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }
    /**
     * <p>Select RSS_DATA All Data
     * @param usrSid ユーザSID
     * @param view 表示フラグ -1の時は全て取得する
     * @return List in RSS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RssDataModel> select(int usrSid, int view) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssDataModel> ret = new ArrayList<RssDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSD_TITLE,");
            sql.addSql("   RSD_URL_FEED,");
            sql.addSql("   RSD_URL,");
            sql.addSql("   RSD_VIEW,");
            sql.addSql("   RSD_MAIN_VIEW,");
            sql.addSql("   RSD_FEED_COUNT,");
            sql.addSql("   RSD_AUTH,");
            sql.addSql("   RSD_AUTH_ID,");
            sql.addSql("   RSD_AUTH_PSWD,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(usrSid);
            if (view != -1) {
                sql.addSql(" and");
                sql.addSql("   RSD_VIEW=?");
                sql.addIntValue(view);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssDataFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定されたユーザが購読済みのRSSSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return RSSSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getRssSidList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> rssSidList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rssSidList.add(rs.getInt("RSS_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return rssSidList;
    }

    /**
     * <p>Select RSS_DATA
     * @param rssSid RSSSID
     * @param userSid ユーザSID
     * @return RSS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public RssDataModel getRssData(int rssSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSD_TITLE,");
            sql.addSql("   RSD_URL_FEED,");
            sql.addSql("   RSD_URL,");
            sql.addSql("   RSD_VIEW,");
            sql.addSql("   RSD_MAIN_VIEW,");
            sql.addSql("   RSD_FEED_COUNT,");
            sql.addSql("   RSD_AUTH,");
            sql.addSql("   RSD_AUTH_ID,");
            sql.addSql("   RSD_AUTH_PSWD,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rssSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssDataFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>フィードURLを元にRSS情報を取得する
     * @param userSid ユーザSID
     * @param feedUrl フィードURL
     * @return RSS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public RssDataModel getRssDataToFeedUrl(int userSid, String feedUrl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSD_TITLE,");
            sql.addSql("   RSD_URL_FEED,");
            sql.addSql("   RSD_URL,");
            sql.addSql("   RSD_VIEW,");
            sql.addSql("   RSD_MAIN_VIEW,");
            sql.addSql("   RSD_FEED_COUNT,");
            sql.addSql("   RSD_AUTH,");
            sql.addSql("   RSD_AUTH_ID,");
            sql.addSql("   RSD_AUTH_PSWD,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   RSD_URL_FEED=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addStrValue(feedUrl);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssDataFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] RSS情報一覧を取得する
     * <br>[解  説] RSS位置情報でソートを行う
     * <br>[備  考]
     * @param itemp TempFileUtil
     * @param usrSid ユーザSID
     * @param mainView メイン表示フラグ -1の時は全て取得する
     * @return List in RSS_DATAModel
     * @throws Exception 実行例外
     */
    public List<RssModel> getRssDataList(ITempFileUtil itemp, int usrSid, int mainView)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssModel> ret = new ArrayList<RssModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_INFOM.RSM_FEEDDATA as RSM_FEEDDATA,");
            sql.addSql("   RSS_INFOM.RSM_UPDATE_TIME as RSM_UPDATE_TIME,");
            sql.addSql("   RSS_DATA.RSS_SID as RSS_SID,");
            sql.addSql("   RSS_DATA.USR_SID as USR_SID,");
            sql.addSql("   RSS_DATA.RSD_TITLE as RSD_TITLE,");
            sql.addSql("   RSS_DATA.RSD_URL_FEED as RSD_URL_FEED,");
            sql.addSql("   RSS_DATA.RSD_URL as RSD_URL,");
            sql.addSql("   RSS_DATA.RSD_VIEW as RSD_VIEW,");
            sql.addSql("   RSS_DATA.RSD_FEED_COUNT as RSD_FEED_COUNT,");
            sql.addSql("   RSS_DATA.RSD_AUTH as RSD_AUTH,");
            sql.addSql("   RSS_DATA.RSD_AUTH_ID as RSD_AUTH_ID,");
            sql.addSql("   RSS_DATA.RSD_AUTH_PSWD as RSD_AUTH_PSWD,");
            sql.addSql("   COALESCE(RSS_POSITION.RSP_POSITION, ?) as RSP_POSITION");
            sql.addIntValue(GSConstRss.RSS_POSITIONFLG_LEFT);
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM,");
            sql.addSql("   RSS_DATA");
            sql.addSql("   left join");
            sql.addSql("     RSS_POSITION");
            sql.addSql("   on");
            sql.addSql("     RSS_DATA.RSS_SID = RSS_POSITION.RSS_SID");
            sql.addSql("   and");
            sql.addSql("     RSS_DATA.USR_SID = RSS_POSITION.USR_SID");
            sql.addSql(" where");
            sql.addSql("   RSS_INFOM.RSS_SID=RSS_DATA.RSS_SID");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.USR_SID=?");
            sql.addIntValue(usrSid);
            if (mainView != -1) {
                sql.addSql(" and");
                sql.addSql("   RSS_DATA.RSD_MAIN_VIEW=?");
                sql.addIntValue(mainView);
            }
            sql.addSql(" order by");
            sql.addSql("   COALESCE(RSS_POSITION.RSP_ORDER, 0)");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RssModel bean = new RssModel();
                bean.setRssSid(rs.getInt("RSS_SID"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setRsdTitle(rs.getString("RSD_TITLE"));
                bean.setRsdUrlFeed(rs.getString("RSD_URL_FEED"));
                bean.setRsdUrl(rs.getString("RSD_URL"));
                bean.setRsdView(rs.getInt("RSD_VIEW"));
                bean.setRsdFeedCount(rs.getInt("RSD_FEED_COUNT"));
                bean.setRspPosition(rs.getInt("RSP_POSITION"));
                bean.setRsdAuth(rs.getInt("RSD_AUTH"));
                bean.setRsdAuthId(rs.getString("RSD_AUTH_ID"));
                bean.setRsdAuthPswd(rs.getString("RSD_AUTH_PSWD"));

                bean.setFeedUpdateTime(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));

                bean.setFeedData((GSFeedList) itemp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));

                ret.add(bean);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            con.setAutoCommit(autoCommit);
        }

        return ret;
    }

    /**
     * <br>[機  能] メイン画面に表示するRSS情報一覧を取得する
     * <br>[解  説] RSS位置情報_メイン画面でソートを行う
     * <br>[備  考]
     * @param itmp TempFileUtil
     * @param usrSid ユーザSID
     * @return List in RSS_DATAModel
     * @throws Exception 実行例外
     */
    public List<RssModel> getMainRssDataList(ITempFileUtil itmp, int usrSid)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssModel> ret = new ArrayList<RssModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_INFOM.RSM_FEEDDATA as RSM_FEEDDATA,");
            sql.addSql("   RSS_INFOM.RSM_UPDATE_TIME as RSM_UPDATE_TIME,");
            sql.addSql("   RSS_DATA.RSS_SID as RSS_SID,");
            sql.addSql("   RSS_DATA.USR_SID as USR_SID,");
            sql.addSql("   RSS_DATA.RSD_TITLE as RSD_TITLE,");
            sql.addSql("   RSS_DATA.RSD_URL_FEED as RSD_URL_FEED,");
            sql.addSql("   RSS_DATA.RSD_URL as RSD_URL,");
            sql.addSql("   RSS_DATA.RSD_VIEW as RSD_VIEW,");
            sql.addSql("   RSS_DATA.RSD_FEED_COUNT as RSD_FEED_COUNT,");
            sql.addSql("   RSS_DATA.RSD_AUTH as RSD_AUTH,");
            sql.addSql("   RSS_DATA.RSD_AUTH_ID as RSD_AUTH_ID,");
            sql.addSql("   RSS_DATA.RSD_AUTH_PSWD as RSD_AUTH_PSWD,");
            sql.addSql("   COALESCE(RSS_POSITION_MAIN.RPM_POSITION, ?) as RPM_POSITION");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM,");
            sql.addSql("   RSS_DATA");
            sql.addSql("   left join");
            sql.addSql("     RSS_POSITION_MAIN");
            sql.addSql("   on");
            sql.addSql("     RSS_DATA.RSS_SID = RSS_POSITION_MAIN.RSS_SID");
            sql.addSql("   and");
            sql.addSql("     RSS_DATA.USR_SID = RSS_POSITION_MAIN.USR_SID");
            sql.addSql(" where");
            sql.addSql("   RSS_INFOM.RSS_SID=RSS_DATA.RSS_SID");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.RSD_MAIN_VIEW=?");
            sql.addSql(" order by");
            sql.addSql("   COALESCE(RSS_POSITION_MAIN.RPM_ORDER, 0)");

            sql.addIntValue(GSConstRss.RSS_POSITIONFLG_LEFT);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstRss.RSS_MAIN_VIEWFLG_SHOW);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RssModel bean = new RssModel();
                bean.setRssSid(rs.getInt("RSS_SID"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setRsdTitle(rs.getString("RSD_TITLE"));
                bean.setRsdUrlFeed(rs.getString("RSD_URL_FEED"));
                bean.setRsdUrl(rs.getString("RSD_URL"));
                bean.setRsdView(rs.getInt("RSD_VIEW"));
                bean.setRsdFeedCount(rs.getInt("RSD_FEED_COUNT"));
                bean.setRspPosition(rs.getInt("RPM_POSITION"));
                bean.setRsdAuth(rs.getInt("RSD_AUTH"));
                bean.setRsdAuthId(rs.getString("RSD_AUTH_ID"));
                bean.setRsdAuthPswd(rs.getString("RSD_AUTH_PSWD"));

                bean.setFeedUpdateTime(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));

                bean.setFeedData((GSFeedList) itmp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));

                ret.add(bean);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            con.setAutoCommit(autoCommit);
        }

        return ret;
    }

    /**
     * <p>Delete RSS_DATA
     * @param rssSid RSSSID
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int rssSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rssSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>指定したRSSを削除する
     * @param rssSid RSSSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteRss(int rssSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rssSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>指定したユーザのRSSを削除する
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteUsersRss(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] RSS情報一覧を取得する
     * <br>[解  説] RSS位置情報でソートを行う
     * <br>[備  考]
     * @param itmp TempFileUtil
     * @param mainView メイン表示フラグ -1の時は全て取得する
     * @return List in RSS_DATAModel
     * @throws Exception 実行例外
     */
    public List<RssModel> getRssAllDataList(ITempFileUtil itmp, int mainView)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssModel> ret = new ArrayList<RssModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_INFOM.RSM_FEEDDATA as RSM_FEEDDATA,");
            sql.addSql("   RSS_INFOM.RSM_UPDATE_TIME as RSM_UPDATE_TIME,");
            sql.addSql("   RSS_DATA.RSS_SID as RSS_SID,");
            sql.addSql("   RSS_DATA.USR_SID as USR_SID,");
            sql.addSql("   RSS_DATA.RSD_TITLE as RSD_TITLE,");
            sql.addSql("   RSS_DATA.RSD_URL_FEED as RSD_URL_FEED,");
            sql.addSql("   RSS_DATA.RSD_URL as RSD_URL,");
            sql.addSql("   RSS_DATA.RSD_VIEW as RSD_VIEW,");
            sql.addSql("   RSS_DATA.RSD_FEED_COUNT as RSD_FEED_COUNT,");
            sql.addSql("   RSS_DATA.RSD_AUTH as RSD_AUTH,");
            sql.addSql("   RSS_DATA.RSD_AUTH_ID as RSD_AUTH_ID,");
            sql.addSql("   RSS_DATA.RSD_AUTH_PSWD as RSD_AUTH_PSWD,");
            sql.addSql("   COALESCE(RSS_POSITION.RSP_POSITION, ?) as RSP_POSITION");
            sql.addIntValue(GSConstRss.RSS_POSITIONFLG_LEFT);
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM,");
            sql.addSql("   RSS_DATA");
            sql.addSql("   left join");
            sql.addSql("     RSS_POSITION");
            sql.addSql("   on");
            sql.addSql("     RSS_DATA.RSS_SID = RSS_POSITION.RSS_SID");
            sql.addSql("   and");
            sql.addSql("     RSS_DATA.USR_SID = RSS_POSITION.USR_SID");
            sql.addSql(" where");
            sql.addSql("   RSS_INFOM.RSS_SID=RSS_DATA.RSS_SID");
            if (mainView != -1) {
                sql.addSql(" and");
                sql.addSql("   RSS_DATA.RSD_MAIN_VIEW=?");
                sql.addIntValue(mainView);
            }
            sql.addSql(" order by");
            sql.addSql("   COALESCE(RSS_POSITION.RSP_ORDER, 0)");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RssModel bean = new RssModel();
                bean.setRssSid(rs.getInt("RSS_SID"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setRsdTitle(rs.getString("RSD_TITLE"));
                bean.setRsdUrlFeed(rs.getString("RSD_URL_FEED"));
                bean.setRsdUrl(rs.getString("RSD_URL"));
                bean.setRsdView(rs.getInt("RSD_VIEW"));
                bean.setRsdFeedCount(rs.getInt("RSD_FEED_COUNT"));
                bean.setRspPosition(rs.getInt("RSP_POSITION"));
                bean.setRsdAuth(rs.getInt("RSD_AUTH"));
                bean.setRsdAuthId(rs.getString("RSD_AUTH_ID"));
                bean.setRsdAuthPswd(rs.getString("RSD_AUTH_PSWD"));

                bean.setFeedUpdateTime(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));

                bean.setFeedData((GSFeedList) itmp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));

                ret.add(bean);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            con.setAutoCommit(autoCommit);
        }

        return ret;
    }

    /**
     * <p>指定されたRSS情報を使用するユーザのSIDを取得する
     * @param rssSid RSSSID
     * @return ユーザSID
     * @throws Exception 実行例外
     */
    public List<Integer> getUsedUserList(int rssSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSS_SID = ?");
            sql.addIntValue(rssSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したRSS情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @param userSidList ユーザSID
     * @return RSS情報のデータサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getRssDataSize(int rssSid, List<Integer> userSidList) throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSD_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(RSD_URL_FEED)) as FEED_SIZE,");
            sql.addSql("   sum(octet_length(RSD_URL)) as URL_SIZE,");
            sql.addSql("   sum(octet_length(RSD_AUTH_ID)) as ID_SIZE,");
            sql.addSql("   sum(octet_length(RSD_AUTH_PSWD)) as PSWD_SIZE");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addIntValue(rssSid);

            sql.addSql(" and ");
            sql.addSql("   USR_SID in (");

            for (int idx = 0; idx < userSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(userSidList.get(idx));
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("FEED_SIZE");
                dataSize += rs.getLong("URL_SIZE");
                dataSize += rs.getLong("ID_SIZE");
                dataSize += rs.getLong("PSWD_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <p>Create RSS_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssDataModel
     * @throws SQLException SQL実行例外
     */
    private RssDataModel __getRssDataFromRs(ResultSet rs) throws SQLException {
        RssDataModel bean = new RssDataModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRsdTitle(rs.getString("RSD_TITLE"));
        bean.setRsdUrlFeed(rs.getString("RSD_URL_FEED"));
        bean.setRsdUrl(rs.getString("RSD_URL"));
        bean.setRsdView(rs.getInt("RSD_VIEW"));
        bean.setRsdMainView(rs.getInt("RSD_MAIN_VIEW"));
        bean.setRsdFeedCount(rs.getInt("RSD_FEED_COUNT"));
        bean.setRsdAuth(rs.getInt("RSD_AUTH"));
        bean.setRsdAuthId(rs.getString("RSD_AUTH_ID"));
        bean.setRsdAuthPswd(rs.getString("RSD_AUTH_PSWD"));
        bean.setRsdAuid(rs.getInt("RSD_AUID"));
        bean.setRsdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSD_ADATE")));
        bean.setRsdEuid(rs.getInt("RSD_EUID"));
        bean.setRsdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSD_EDATE")));
        return bean;
    }
}
