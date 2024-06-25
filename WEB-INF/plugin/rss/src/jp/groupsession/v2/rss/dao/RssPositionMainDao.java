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
import jp.groupsession.v2.rss.model.RssPositionMainModel;

/**
 * <p>RSS_POSITION_MAIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssPositionMainDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssPositionMainDao.class);

    /**
     * <p>Default Constructor
     */
    public RssPositionMainDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssPositionMainDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table RSS_POSITION_MAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table RSS_POSITION_MAIN (");
            sql.addSql("   RSS_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   RPM_POSITION NUMBER(10,0) not null,");
            sql.addSql("   RPM_ORDER NUMBER(10,0) not null,");
            sql.addSql("   RPM_AUID NUMBER(10,0) not null,");
            sql.addSql("   RPM_ADATE varchar(23) not null,");
            sql.addSql("   RPM_EUID NUMBER(10,0) not null,");
            sql.addSql("   RPM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (RSS_SID,USR_SID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert RSS_POSITION_MAIN Data Bindding JavaBean
     * @param bean RSS_POSITION_MAIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RssPositionMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_POSITION_MAIN(");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RPM_POSITION,");
            sql.addSql("   RPM_ORDER,");
            sql.addSql("   RPM_AUID,");
            sql.addSql("   RPM_ADATE,");
            sql.addSql("   RPM_EUID,");
            sql.addSql("   RPM_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRpmPosition());
            sql.addIntValue(bean.getRpmOrder());
            sql.addIntValue(bean.getRpmAuid());
            sql.addDateValue(bean.getRpmAdate());
            sql.addIntValue(bean.getRpmEuid());
            sql.addDateValue(bean.getRpmEdate());
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
     * <p>Update RSS_POSITION_MAIN Data Bindding JavaBean
     * @param bean RSS_POSITION_MAIN Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RssPositionMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_POSITION_MAIN");
            sql.addSql(" set ");
            sql.addSql("   RPM_POSITION=?,");
            sql.addSql("   RPM_ORDER=?,");
            sql.addSql("   RPM_AUID=?,");
            sql.addSql("   RPM_ADATE=?,");
            sql.addSql("   RPM_EUID=?,");
            sql.addSql("   RPM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRpmPosition());
            sql.addIntValue(bean.getRpmOrder());
            sql.addIntValue(bean.getRpmAuid());
            sql.addDateValue(bean.getRpmAdate());
            sql.addIntValue(bean.getRpmEuid());
            sql.addDateValue(bean.getRpmEdate());
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
     * <p>Select RSS_POSITION_MAIN All Data
     * @return List in RSS_POSITION_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<RssPositionMainModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssPositionMainModel> ret = new ArrayList<RssPositionMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RPM_POSITION,");
            sql.addSql("   RPM_ORDER,");
            sql.addSql("   RPM_AUID,");
            sql.addSql("   RPM_ADATE,");
            sql.addSql("   RPM_EUID,");
            sql.addSql("   RPM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_POSITION_MAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssPositionMainFromRs(rs));
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
     * <p>Select RSS_POSITION_MAIN
     * @param bean RSS_POSITION_MAIN Model
     * @return RSS_POSITION_MAINModel
     * @throws SQLException SQL実行例外
     */
    public RssPositionMainModel select(RssPositionMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssPositionMainModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RPM_POSITION,");
            sql.addSql("   RPM_ORDER,");
            sql.addSql("   RPM_AUID,");
            sql.addSql("   RPM_ADATE,");
            sql.addSql("   RPM_EUID,");
            sql.addSql("   RPM_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_POSITION_MAIN");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssSid());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssPositionMainFromRs(rs);
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
     * <p>Delete RSS_POSITION_MAIN
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
            sql.addSql("   RSS_POSITION_MAIN");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");
            sql.addSql(" and");
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
     * <p>指定されたユーザのRSS位置情報_メイン画面を全て削除する
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
            sql.addSql("   RSS_POSITION_MAIN");
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
     * <p>Create RSS_POSITION_MAIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssPositionMainModel
     * @throws SQLException SQL実行例外
     */
    private RssPositionMainModel __getRssPositionMainFromRs(ResultSet rs) throws SQLException {
        RssPositionMainModel bean = new RssPositionMainModel();
        bean.setRssSid(rs.getInt("RSS_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRpmPosition(rs.getInt("RPM_POSITION"));
        bean.setRpmOrder(rs.getInt("RPM_ORDER"));
        bean.setRpmAuid(rs.getInt("RPM_AUID"));
        bean.setRpmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RPM_ADATE")));
        bean.setRpmEuid(rs.getInt("RPM_EUID"));
        bean.setRpmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RPM_EDATE")));
        return bean;
    }
}
