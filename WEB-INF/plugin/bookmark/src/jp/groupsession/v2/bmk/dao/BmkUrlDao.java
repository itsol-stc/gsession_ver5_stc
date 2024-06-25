package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.model.BmkUrlModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_URL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BmkUrlDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkUrlDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkUrlDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkUrlDao(Connection con) {
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
            sql.addSql("drop table BMK_URL");

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
            sql.addSql(" create table BMK_URL (");
            sql.addSql("   BMU_SID NUMBER(10,0) not null,");
            sql.addSql("   BMU_URL varchar(1000) not null,");
            sql.addSql("   BMU_TITLE varchar(150) not null,");
            sql.addSql("   BMU_PUB_DATE varchar(23),");
            sql.addSql("   BMU_AUID NUMBER(10,0) not null,");
            sql.addSql("   BMU_ADATE varchar(23) not null,");
            sql.addSql("   BMU_EUID NUMBER(10,0) not null,");
            sql.addSql("   BMU_EDATE varchar(23) not null,");
            sql.addSql("   primary key (BMU_SID)");
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
     * <p>Insert BMK_URL Data Bindding JavaBean
     * @param bean BMK_URL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkUrlModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_URL(");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMU_URL,");
            sql.addSql("   BMU_TITLE,");
            sql.addSql("   BMU_PUB_DATE,");
            sql.addSql("   BMU_AUID,");
            sql.addSql("   BMU_ADATE,");
            sql.addSql("   BMU_EUID,");
            sql.addSql("   BMU_EDATE");
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
            sql.addIntValue(bean.getBmuSid());
            sql.addStrValue(bean.getBmuUrl());
            sql.addStrValue(bean.getBmuTitle());
            sql.addDateValue(bean.getBmuPubDate());
            sql.addIntValue(bean.getBmuAuid());
            sql.addDateValue(bean.getBmuAdate());
            sql.addIntValue(bean.getBmuEuid());
            sql.addDateValue(bean.getBmuEdate());
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
     * <p>Update BMK_URL Data Bindding JavaBean
     * @param bean BMK_URL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkUrlModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_URL");
            sql.addSql(" set ");
            sql.addSql("   BMU_TITLE=?,");
            sql.addSql("   BMU_PUB_DATE=?,");
            sql.addSql("   BMU_EUID=?,");
            sql.addSql("   BMU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BMU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBmuTitle());
            sql.addDateValue(bean.getBmuPubDate());
            sql.addIntValue(bean.getBmuEuid());
            sql.addDateValue(bean.getBmuEdate());
            //where
            sql.addIntValue(bean.getBmuSid());

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
     * <p>Select BMK_URL All Data
     * @return List in BMK_URLModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkUrlModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkUrlModel> ret = new ArrayList<BmkUrlModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMU_URL,");
            sql.addSql("   BMU_TITLE,");
            sql.addSql("   BMU_PUB_DATE,");
            sql.addSql("   BMU_AUID,");
            sql.addSql("   BMU_ADATE,");
            sql.addSql("   BMU_EUID,");
            sql.addSql("   BMU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkUrlFromRs(rs));
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
     * <p>Select BMK_URL
     * @param bmuSid BMU_SID
     * @return BMK_URLModel
     * @throws SQLException SQL実行例外
     */
    public BmkUrlModel select(int bmuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkUrlModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMU_URL,");
            sql.addSql("   BMU_TITLE,");
            sql.addSql("   BMU_PUB_DATE,");
            sql.addSql("   BMU_AUID,");
            sql.addSql("   BMU_ADATE,");
            sql.addSql("   BMU_EUID,");
            sql.addSql("   BMU_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_URL");
            sql.addSql(" where ");
            sql.addSql("   BMU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmuSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkUrlFromRs(rs);
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
     * <p>URLからURL情報を取得
     * @param bmuUrl BMU_URL
     * @return BMK_URLModel
     * @throws SQLException SQL実行例外
     */
    public BmkUrlModel select(String bmuUrl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkUrlModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMU_URL,");
            sql.addSql("   BMU_TITLE,");
            sql.addSql("   BMU_PUB_DATE,");
            sql.addSql("   BMU_AUID,");
            sql.addSql("   BMU_ADATE,");
            sql.addSql("   BMU_EUID,");
            sql.addSql("   BMU_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_URL");
            sql.addSql(" where ");
            sql.addSql("   BMU_URL=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bmuUrl);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkUrlFromRs(rs);
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
     * <p>Delete BMK_URL
     * @param bmuSid BMU_SID
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int delete(int bmuSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_URL");
            sql.addSql(" where ");
            sql.addSql("   BMU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmuSid);

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
     * <p>Create BMK_URL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkUrlModel
     * @throws SQLException SQL実行例外
     */
    private BmkUrlModel __getBmkUrlFromRs(ResultSet rs) throws SQLException {
        BmkUrlModel bean = new BmkUrlModel();
        bean.setBmuSid(rs.getInt("BMU_SID"));
        bean.setBmuUrl(rs.getString("BMU_URL"));
        bean.setBmuTitle(rs.getString("BMU_TITLE"));
        bean.setBmuPubDate(UDate.getInstanceTimestamp(rs.getTimestamp("BMU_PUB_DATE")));
        bean.setBmuAuid(rs.getInt("BMU_AUID"));
        bean.setBmuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMU_ADATE")));
        bean.setBmuEuid(rs.getInt("BMU_EUID"));
        bean.setBmuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMU_EDATE")));
        return bean;
    }
}
