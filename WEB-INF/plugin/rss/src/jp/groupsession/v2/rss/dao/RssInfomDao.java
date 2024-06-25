package jp.groupsession.v2.rss.dao;

import java.io.File;
import java.io.InputStream;
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
import jp.co.sjts.util.jdbc.SqlFile;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>RSS_INFOM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssInfomDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssInfomDao.class);

    /**
     * <p>Default Constructor
     */
    public RssInfomDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssInfomDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSS_INFOM Data Bindding JavaBean
     * @param bean RSS_INFOM Data Bindding JavaBean
     * @throws Exception 実行例外
     */
    public void insert(RssInfomModel bean) throws Exception {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_INFOM(");
            sql.addSql("   RSS_SID,");
            sql.addSql("   RSM_URL_FEED,");
            sql.addSql("   RSM_UPDATE_TIME,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());
            sql.addStrValue(bean.getRsmUrlFeed());
            sql.addDateValue(bean.getRsmUpdateTime());
            sql.addIntValue(bean.getRsmAuth());
            sql.addStrValue(bean.getRsmAuthId());
            sql.addStrValue(bean.getRsmAuthPswd());
            sql.addIntValue(bean.getRsmAuid());
            sql.addDateValue(bean.getRsmAdate());
            sql.addIntValue(bean.getRsmEuid());
            sql.addDateValue(bean.getRsmEdate());
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
     * <p>Insert RSS_INFOM Data Bindding JavaBean
     * @param bean RSS_INFOM Data Bindding JavaBean
     * @param file FeedをObjectにしたファイル
     * @throws Exception 実行例外
     */
    public void insert2(RssInfomModel bean, File file) throws Exception {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        SqlFile sfile = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_INFOM(");
            sql.addSql("   RSS_SID,");
            sql.addSql("   RSM_URL_FEED,");
            sql.addSql("   RSM_FEEDDATA,");
            sql.addSql("   RSM_UPDATE_TIME,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());
            sql.addStrValue(bean.getRsmUrlFeed());
            sfile = new SqlFile();
            sfile.setFile(file);
            sql.addFileValue(sfile);
            sql.addDateValue(bean.getRsmUpdateTime());
            sql.addIntValue(bean.getRsmAuth());
            sql.addStrValue(bean.getRsmAuthId());
            sql.addStrValue(bean.getRsmAuthPswd());
            sql.addIntValue(bean.getRsmAuid());
            sql.addDateValue(bean.getRsmAdate());
            sql.addIntValue(bean.getRsmEuid());
            sql.addDateValue(bean.getRsmEdate());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            InputStream in = sfile.getInStream();
            in.close();
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update RSS_INFOM Data Bindding JavaBean
     * @param bean RSS_INFOM Data Bindding JavaBean
     * @return update count
     * @throws Exception 実行例外
     */
    public int update(RssInfomModel bean) throws Exception {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" set ");
            sql.addSql("   RSM_URL_FEED=?,");
            sql.addSql("   RSM_EUID=?,");
            sql.addSql("   RSM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addStrValue(bean.getRsmUrlFeed());
            sql.addIntValue(bean.getRsmEuid());
            sql.addDateValue(bean.getRsmEdate());
            //where
            sql.addIntValue(bean.getRssSid());

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
     * <br>[機  能] フィード情報をクリアする
     * <br>[解  説] RSSフィード情報(RSM_FEEDDATA)にNULLを設定する
     * <br>[備  考]
     * @param rsmUpdateTime RSSフィード更新時間
     * @param userSid 更新者
     * @param date 更新日時
     * @throws SQLException SQL実行時例外
     */
    public void clearFeedData(UDate rsmUpdateTime, int userSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" set ");
            sql.addSql("   RSM_FEEDDATA=null,");
            sql.addSql("   RSM_UPDATE_TIME=?,");
            sql.addSql("   RSM_EUID=?,");
            sql.addSql("   RSM_EDATE=?");

            sql.addDateValue(rsmUpdateTime);
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select RSS_INFOM All Data
     * @return List in RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public List<RssInfomModel> select() throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssInfomModel> ret = new ArrayList<RssInfomModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID,");
            sql.addSql("   RSM_URL_FEED,");
//            sql.addSql("   RSM_FEEDDATA,");
            sql.addSql("   RSM_UPDATE_TIME,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssInfomConvFromRs(rs));
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
     * <br>[機  能] 全てのフィード情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public List<RssInfomModel> selectAllFeed() throws Exception {
        return selectUpdateFeed(null);
    }

    /**
     * <br>[機  能] 自動更新での更新対象を取得する。
     * <br>[解  説] 引数で指定した日時より前に更新されたフィードを更新対象とする。
     * <br>[備  考]
     * @param dtime 基準日時 この日時より前に更新されたフィードを取得する。
     * @return List in RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public List<RssInfomModel> selectUpdateFeed(UDate dtime) throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssInfomModel> ret = new ArrayList<RssInfomModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID,");
            sql.addSql("   RSM_URL_FEED,");
            sql.addSql("   RSM_UPDATE_TIME,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");

            if (dtime != null) {
                sql.addSql(" where ");
                sql.addSql("   RSM_UPDATE_TIME < ?");
                sql.addDateValue(dtime);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RssInfomModel bean = new RssInfomModel();
                bean.setRssSid(rs.getInt("RSS_SID"));
                bean.setRsmUrlFeed(rs.getString("RSM_URL_FEED"));
                bean.setRsmAuth(rs.getInt("RSM_AUTH"));
                bean.setRsmAuthId(rs.getString("RSM_AUTH_ID"));
                bean.setRsmAuthPswd(rs.getString("RSM_AUTH_PSWD"));
                bean.setRsmUpdateTime(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));
                bean.setRsmAuid(rs.getInt("RSM_AUID"));
                bean.setRsmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_ADATE")));
                ret.add(bean);
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
     * <p>RSSマスタの件数を取得する
     * @return RSSマスタの件数
     * @throws SQLException SQL実行例外
     */
    public int getRssCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(RSS_SID) as rssCount");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("rssCount");
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
     * <p>RSS情報が存在するRSSマスタの件数を取得する
     * @return RSSマスタの件数
     * @throws SQLException SQL実行例外
     */
    public int getRssInfoCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(RSS_SID) as rssCount");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where");
            sql.addSql("   RSS_SID in (");
            sql.addSql("     select ");
            sql.addSql("       RSS_DATA.RSS_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM,");
            sql.addSql("       RSS_DATA");
            sql.addSql("     where");
            sql.addSql("       RSS_DATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and");
            sql.addSql("       CMN_USRM.USR_JKBN = 0");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("rssCount");
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
     * <p>Select RSS_INFOM
     * @param itmp TempFileUtil
     * @param rssSid RSSSID
     * @return RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public RssInfomModel select(ITempFileUtil itmp, int rssSid) throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssInfomModel ret = null;
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_SID,");
            sql.addSql("   RSM_URL_FEED,");
            sql.addSql("   RSM_FEEDDATA,");
            sql.addSql("   RSM_UPDATE_TIME,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rssSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssInfomFromRs(itmp, rs);
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
     * <p>フィードURLによりRSSマスタの検索を行う
     * @param itmp TempFileUtil
     * @param feedUrl フィードURL
     * @return RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public RssInfomModel selectToFeedUrl(ITempFileUtil itmp, String feedUrl) throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssInfomModel ret = null;
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_SID,");
            sql.addSql("   RSM_URL_FEED,");
            sql.addSql("   RSM_FEEDDATA,");
            sql.addSql("   RSM_UPDATE_TIME,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where ");
            sql.addSql("   RSM_URL_FEED=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(feedUrl);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssInfomFromRs(itmp, rs);
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
     * <p>Delete RSS_INFOM
     * @param rssSid RSSSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int rssSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
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
     * <br>[機  能] ユーザに使用されていないRSS情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int selectToDontUseData(int rssSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  count(RSS_SID) as RSS_CNT");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   RSS_SID not in (");
            sql.addSql("     select RSS_DATA.RSS_SID from CMN_USRM, RSS_DATA");
            sql.addSql("     where RSS_DATA.RSS_SID = ?");
            sql.addSql("     and RSS_DATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(rssSid);
            sql.addIntValue(rssSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("RSS_CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    
    /**
     * <br>[機  能] ユーザに使用されていないRSS情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteToDontUseData(int rssSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   RSS_SID not in (");
            sql.addSql("     select RSS_DATA.RSS_SID from CMN_USRM, RSS_DATA");
            sql.addSql("     where RSS_DATA.RSS_SID = ?");
            sql.addSql("     and RSS_DATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(rssSid);
            sql.addIntValue(rssSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したRSSマスタを使用するユーザが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @return 確認結果 true: 使用するユーザが存在する、false: 存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existsUsedUser(int rssSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean ret = false;

        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   RSS_SID in (");
            sql.addSql("     select RSS_DATA.RSS_SID from CMN_USRM, RSS_DATA");
            sql.addSql("     where RSS_DATA.RSS_SID = ?");
            sql.addSql("     and RSS_DATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(rssSid);
            sql.addIntValue(rssSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <p>RSS一覧を新着順に取得する(過去一週間以内)
     * @param itmp TempFileUtil
     * @param maxCount 取得件数
     * @param userSid ユーザSID
     * @return List in RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public List<RssInfomModel> getNewRssList(ITempFileUtil itmp, int maxCount, int userSid)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssInfomModel> ret = new ArrayList<RssInfomModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RINF.RSS_SID,");
            sql.addSql("   RINF.RSM_URL_FEED,");
            sql.addSql("   RINF.RSM_FEEDDATA,");
            sql.addSql("   RINF.RSM_UPDATE_TIME,");
            sql.addSql("   RINF.RSM_AUTH,");
            sql.addSql("   RINF.RSM_AUTH_ID,");
            sql.addSql("   RINF.RSM_AUTH_PAWD,");
            sql.addSql("   RINF.RSM_AUID,");
            sql.addSql("   RINF.RSM_ADATE,");
            sql.addSql("   RINF.RSM_EUID,");
            sql.addSql("   RINF.RSM_EDATE,");
            sql.addSql("   RSSKOUDOKU.koudokuCount");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM as RINF");
            sql.addSql("   left join (");
            sql.addSql("     select count(*) as koudokuCount, RSS_DATA.RSS_SID");
            sql.addSql("     from RSS_DATA");
            sql.addSql("     where RSS_DATA.USR_SID=?");
            sql.addSql("     group by RSS_SID");
            sql.addSql("     ) as RSSKOUDOKU");
            sql.addSql("   on");
            sql.addSql("   RINF.RSS_SID = RSSKOUDOKU.RSS_SID");

            sql.addSql(" where ");
            sql.addSql("   RSM_ADATE > ?");
            sql.addSql(" order by");
            sql.addSql("   RSM_ADATE desc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            UDate bdate = new UDate();
            bdate.resetTime();
            bdate.addDay(-7);
            sql.addDateValue(bdate);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int count = 1;
            while (rs.next()) {
                if (count++ > maxCount) {
                    break;
                }

                ret.add(__getRssInfomRankingFromRs(itmp, rs));
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
     * <p>RSS一覧を新着順に取得する(過去一週間以内)
     * @param itmp TempFileUtil
     * @param maxCount 取得件数
     * @param userSid ユーザSID
     * @param dspCntDay RSS表示日数
     * @return List in RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public List<RssInfomModel> getNewRankingRssList(ITempFileUtil itmp,
                                                     int maxCount,
                                                     int userSid,
                                                     int dspCntDay)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssInfomModel> ret = new ArrayList<RssInfomModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   distinct RINF.RSS_SID,");
            sql.addSql("   RINF.RSM_FEEDDATA,");
            sql.addSql("   RINF.RSM_URL_FEED,");
            sql.addSql("   RINF.RSM_AUTH,");
            sql.addSql("   RINF.RSM_AUTH_ID,");
            sql.addSql("   RINF.RSM_AUTH_PSWD,");
            sql.addSql("   RSSDATA.koudokuCount,");
            sql.addSql("   RINF.RSM_ADATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATA as RDATA,");
            sql.addSql("   RSS_INFOM as RINF");
            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       count(*) as koudokuCount,");
            sql.addSql("       RSS_DATA.RSS_SID,");
            sql.addSql("       RSS_DATA.USR_SID");
            sql.addSql("     from ");
            sql.addSql("       RSS_DATA");
            sql.addSql("     where");
            sql.addSql("       RSS_DATA.USR_SID = ?");
            sql.addSql("     group by");
            sql.addSql("       RSS_SID, USR_SID");
            sql.addSql("     ) as RSSDATA");
            sql.addSql("   on");
            sql.addSql("   RINF.RSS_SID = RSSDATA.RSS_SID");
            sql.addSql(" where ");
            sql.addSql("   RINF.RSS_SID = RDATA.RSS_SID");
            sql.addSql("   and");
            sql.addSql("   RSM_ADATE > ?");
            sql.addSql(" order by");
            sql.addSql("   RSM_ADATE desc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            UDate bdate = new UDate();
            bdate.resetTime();
            bdate.addDay(dspCntDay);
            sql.addDateValue(bdate);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int count = 1;
            while (rs.next()) {
                if (count++ > maxCount) {
                    break;
                }

                ret.add(__getRssInfomNewRankingFromRs(itmp, rs));
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
     * <p>RSS一覧を取得する
     * @param itmp TempFileUtil
     * @return List in RSS_INFOMModel
     * @throws Exception 実行例外
     */
    public List<RssInfomModel> getRssInfoList(ITempFileUtil itmp)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssInfomModel> ret = new ArrayList<RssInfomModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   distinct RSS_SID,");
            sql.addSql("   RSM_FEEDDATA,");
            sql.addSql("   RSM_URL_FEED,");
            sql.addSql("   RSM_AUTH,");
            sql.addSql("   RSM_AUTH_ID,");
            sql.addSql("   RSM_AUTH_PSWD,");
            sql.addSql("   RSM_ADATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RssInfomModel bean = new RssInfomModel();
                bean.setRssSid(rs.getInt("RSS_SID"));
                bean.setRsmUrlFeed(rs.getString("RSM_URL_FEED"));
                bean.setRsmAuth(rs.getInt("RSM_AUTH"));
                bean.setRsmAuthId(rs.getString("RSM_AUTH_ID"));
                bean.setRsmAuthPswd(rs.getString("RSM_AUTH_PSWD"));

                bean.setRsmFeeddata((GSFeedList) itmp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));
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
     * <br>[機  能] 指定したRSSマスタのデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考] フィード情報(RSM_FEEDDATA)は除外する
     * @param rssSid RSSSID
     * @return RSSマスタのデータサイズ合計(フィード情報以外)
     * @throws SQLException SQL実行例外
     */
    public long getRssDataSize(int rssSid) throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSM_URL_FEED)) as FEED_SIZE,");
            sql.addSql("   sum(octet_length(RSM_AUTH_ID)) as ID_SIZE,");
            sql.addSql("   sum(octet_length(RSM_AUTH_PSWD)) as PSWD_SIZE");
            sql.addSql(" from");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addIntValue(rssSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("FEED_SIZE");
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
     * <p>指定されたRSSマスタのフィード情報のデータサイズを取得する
     * @param rssSid RSSSID
     * @return フィード情報のデータサイズ
     * @throws Exception 実行例外
     */
    public long getRssFeedDataSize(int rssSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(octet_length(RSM_FEEDDATA)) as FEED_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where");
            sql.addSql("   RSS_SID = ?");
            sql.addIntValue(rssSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getLong("FEED_SIZE");
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
     * <p>指定されたRSSマスタのフィード情報のデータサイズを取得する(PostgreSQL環境)
     * @param rssSid RSSSID
     * @return フィード情報のデータサイズ
     * @throws Exception 実行例外
     */
    public long getRssFeedDataSizeInPostgreSQL(int rssSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(octet_length(data)) as FEED_SIZE");
            sql.addSql(" from ");
            sql.addSql("   pg_largeobject");
            sql.addSql(" where ");
            sql.addSql("   loid in (");
            sql.addSql("     select");
            sql.addSql("       RSM_FEEDDATA");
            sql.addSql("     from");
            sql.addSql("       RSS_INFOM");
            sql.addSql("     where");
            sql.addSql("       RSS_SID = ?");
            sql.addSql("  )");
            sql.addIntValue(rssSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getLong("FEED_SIZE");
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
     * <p>Create RSS_INFOM Data Bindding JavaBean From ResultSet
     * @param itmp TempFileUtil
     * @param rs ResultSet
     * @return created RssInfomModel
     * @throws Exception 実行例外
     */
    private RssInfomModel __getRssInfomFromRs(ITempFileUtil itmp, ResultSet rs) throws Exception {
        RssInfomModel bean = new RssInfomModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setRsmUrlFeed(rs.getString("RSM_URL_FEED"));
        bean.setRsmAuth(rs.getInt("RSM_AUTH"));
        bean.setRsmAuthId(rs.getString("RSM_AUTH_ID"));
        bean.setRsmAuthPswd(rs.getString("RSM_AUTH_PSWD"));
        bean.setRsmUpdateTime(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));
        bean.setRsmAuid(rs.getInt("RSM_AUID"));
        bean.setRsmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_ADATE")));
        bean.setRsmEuid(rs.getInt("RSM_EUID"));
        bean.setRsmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_EDATE")));
        bean.setRsmFeeddata((GSFeedList) itmp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));

        return bean;
    }

    /**
     * <p>Create RSS_INFOM Data Bindding JavaBean From ResultSet
     * @param itmp TempFileUtil
     * @param rs ResultSet
     * @return created RssInfomModel
     * @throws Exception 実行例外
     */
    private RssInfomModel __getRssInfomRankingFromRs(ITempFileUtil itmp, ResultSet rs)
    throws Exception {
        RssInfomModel bean = new RssInfomModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setRsmUrlFeed(rs.getString("RSM_URL_FEED"));
        bean.setRsmUpdateTime(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));
        bean.setKoudokuCount(rs.getInt("koudokuCount"));
        bean.setRsmAuth(rs.getInt("RSM_AUTH"));
        bean.setRsmAuthId(rs.getString("RSM_AUTH_ID"));
        bean.setRsmAuthPswd(rs.getString("RSM_AUTH_PSWD"));
        bean.setRsmAuid(rs.getInt("RSM_AUID"));
        bean.setRsmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_ADATE")));
        bean.setRsmEuid(rs.getInt("RSM_EUID"));
        bean.setRsmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_EDATE")));

        bean.setRsmFeeddata((GSFeedList) itmp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));

        return bean;
    }

    /**
     * <p>Create RSS_INFOM Data Bindding JavaBean From ResultSet
     * @param itmp TempFileUtil
     * @param rs ResultSet
     * @return created RssInfomModel
     * @throws Exception 実行例外
     */
    private RssInfomModel __getRssInfomNewRankingFromRs(ITempFileUtil itmp, ResultSet rs)
    throws Exception {
        RssInfomModel bean = new RssInfomModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setRsmUrlFeed(rs.getString("RSM_URL_FEED"));
        bean.setKoudokuCount(rs.getInt("koudokuCount"));
        bean.setRsmAuth(rs.getInt("RSM_AUTH"));
        bean.setRsmAuthId(rs.getString("RSM_AUTH_ID"));
        bean.setRsmAuthPswd(rs.getString("RSM_AUTH_PSWD"));
        bean.setRsmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_ADATE")));
        bean.setRsmFeeddata((GSFeedList) itmp.readLobObjectFieldInMem(rs, "RSM_FEEDDATA"));

        return bean;
    }
    /**
     * <p>Create RSS_INFOM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssInfomModel
     * @throws Exception 実行例外
     */
    private RssInfomModel __getRssInfomConvFromRs(ResultSet rs) throws Exception {
        RssInfomModel bean = new RssInfomModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setRsmUrlFeed(rs.getString("RSM_URL_FEED"));
        bean.setRsmAuth(rs.getInt("RSM_AUTH"));
        bean.setRsmAuthId(rs.getString("RSM_AUTH_ID"));
        bean.setRsmAuthPswd(rs.getString("RSM_AUTH_PSWD"));
        bean.setRsmUpdateTime(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));
        bean.setRsmAuid(rs.getInt("RSM_AUID"));
        bean.setRsmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_ADATE")));
        bean.setRsmEuid(rs.getInt("RSM_EUID"));
        bean.setRsmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_EDATE")));

        return bean;
    }
}
