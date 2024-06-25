package jp.groupsession.v2.rss.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rss.model.RssPositionModel;

/**
 * <p>RSS_POSITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssPositionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssPositionDao.class);

    /**
     * <p>Default Constructor
     */
    public RssPositionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssPositionDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSS_POSITION Data Bindding JavaBean
     * @param bean RSS_POSITION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RssPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        boolean order = bean.getRspOrder() > 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_POSITION(");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSP_POSITION,");
            sql.addSql("   RSP_ORDER,");
            sql.addSql("   RSP_AUID,");
            sql.addSql("   RSP_ADATE,");
            sql.addSql("   RSP_EUID,");
            sql.addSql("   RSP_EDATE");
            sql.addSql(" )");
            if (order) {
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");
            } else {
                sql.addSql(" select");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   COALESCE(max(RSP_ORDER), 0) + 1,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" from");
                sql.addSql("   RSS_POSITION");
                sql.addSql(" where");
                sql.addSql("   USR_SID=?");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRspPosition());
            if (order) {
                sql.addIntValue(bean.getRspOrder());
            }
            sql.addIntValue(bean.getRspAuid());
            sql.addDateValue(bean.getRspAdate());
            sql.addIntValue(bean.getRspEuid());
            sql.addDateValue(bean.getRspEdate());
            if (!order) {
                sql.addIntValue(bean.getUsrSid());
            }
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
     * <p>Update RSS_POSITION Data Bindding JavaBean
     * @param bean RSS_POSITION Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RssPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_POSITION");
            sql.addSql(" set ");
            sql.addSql("   RSP_POSITION=?,");
            sql.addSql("   RSP_ORDER=?,");
            sql.addSql("   RSP_EUID=?,");
            sql.addSql("   RSP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRspPosition());
            sql.addIntValue(bean.getRspOrder());
            sql.addIntValue(bean.getRspEuid());
            sql.addDateValue(bean.getRspEdate());
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
     * <p>Select RSS_POSITION All Data
     * @return List in RSS_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<RssPositionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssPositionModel> ret = new ArrayList<RssPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSP_POSITION,");
            sql.addSql("   RSP_ORDER,");
            sql.addSql("   RSP_AUID,");
            sql.addSql("   RSP_ADATE,");
            sql.addSql("   RSP_EUID,");
            sql.addSql("   RSP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_POSITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssPositionFromRs(rs));
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
     * <p>Select RSS_POSITION
     * @param bean RSS_POSITION Model
     * @return RSS_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public RssPositionModel select(RssPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSP_POSITION,");
            sql.addSql("   RSP_ORDER,");
            sql.addSql("   RSP_AUID,");
            sql.addSql("   RSP_ADATE,");
            sql.addSql("   RSP_EUID,");
            sql.addSql("   RSP_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_POSITION");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssPositionFromRs(rs);
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
     * <p>Delete RSS_POSITION
     * @param rssSid RSSSID
     * @param usrSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int rssSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_POSITION");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rssSid);
            sql.addIntValue(usrSid);

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
     * <p>RSS位置情報データを削除する
     * @param rssSid RSSSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteRssPos(int rssSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_POSITION");
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
     * <p>指定されたユーザのRSS位置情報を全て削除する
     * @param usrSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteUsersPosition(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_POSITION");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <p>Create RSS_POSITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssPositionModel
     * @throws SQLException SQL実行例外
     */
    private RssPositionModel __getRssPositionFromRs(ResultSet rs) throws SQLException {
        RssPositionModel bean = new RssPositionModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRspPosition(rs.getInt("RSP_POSITION"));
        bean.setRspOrder(rs.getInt("RSP_ORDER"));
        bean.setRspAuid(rs.getInt("RSP_AUID"));
        bean.setRspAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSP_ADATE")));
        bean.setRspEuid(rs.getInt("RSP_EUID"));
        bean.setRspEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSP_EDATE")));
        return bean;
    }
}
