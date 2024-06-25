package jp.groupsession.v2.rss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rss.model.RssAconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSS_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public RssAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssAconfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSS_ACONF Data Bindding JavaBean
     * @param bean RSS_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RssAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_ACONF(");
            sql.addSql("   RAC_READTIME,");
            sql.addSql("   RAC_AUID,");
            sql.addSql("   RAC_ADATE,");
            sql.addSql("   RAC_EUID,");
            sql.addSql("   RAC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRacReadtime());
            sql.addIntValue(bean.getRacAuid());
            sql.addDateValue(bean.getRacAdate());
            sql.addIntValue(bean.getRacEuid());
            sql.addDateValue(bean.getRacEdate());
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
     * <p>Update RSS_ACONF Data Bindding JavaBean
     * @param bean RSS_ACONF Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RssAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_ACONF");
            sql.addSql(" set ");
            sql.addSql("   RAC_READTIME=?,");
            sql.addSql("   RAC_AUID=?,");
            sql.addSql("   RAC_ADATE=?,");
            sql.addSql("   RAC_EUID=?,");
            sql.addSql("   RAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRacReadtime());
            sql.addIntValue(bean.getRacAuid());
            sql.addDateValue(bean.getRacAdate());
            sql.addIntValue(bean.getRacEuid());
            sql.addDateValue(bean.getRacEdate());

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
     * <p>Select RSS_ACONF
     * @return RSS_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public RssAconfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssAconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RAC_READTIME,");
            sql.addSql("   RAC_AUID,");
            sql.addSql("   RAC_ADATE,");
            sql.addSql("   RAC_EUID,");
            sql.addSql("   RAC_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssAconfFromRs(rs);
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
     * <p>Delete RSS_ACONF
     * @param bean RSS_ACONF Model
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RssAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSS_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create RSS_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssAconfModel
     * @throws SQLException SQL実行例外
     */
    private RssAconfModel __getRssAconfFromRs(ResultSet rs) throws SQLException {
        RssAconfModel bean = new RssAconfModel();
        bean.setRacReadtime(rs.getInt("RAC_READTIME"));
        bean.setRacAuid(rs.getInt("RAC_AUID"));
        bean.setRacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RAC_ADATE")));
        bean.setRacEuid(rs.getInt("RAC_EUID"));
        bean.setRacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RAC_EDATE")));
        return bean;
    }
}
