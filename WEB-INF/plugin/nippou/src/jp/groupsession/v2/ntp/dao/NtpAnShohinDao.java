package jp.groupsession.v2.ntp.dao;


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
import jp.groupsession.v2.ntp.model.NtpAnShohinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_AN_SHOHIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpAnShohinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpAnShohinDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpAnShohinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpAnShohinDao(Connection con) {
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
            sql.addSql("drop table NTP_AN_SHOHIN");

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
            sql.addSql(" create table NTP_AN_SHOHIN (");
            sql.addSql("   NAN_SID NUMBER(10,0) not null,");
            sql.addSql("   NHN_SID NUMBER(10,0) not null,");
            sql.addSql("   NAS_AUID NUMBER(10,0),");
            sql.addSql("   NAS_ADATE varchar(23) not null,");
            sql.addSql("   NAS_EUID NUMBER(10,0),");
            sql.addSql("   NAS_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NAN_SID,NHN_SID)");
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
     * <p>Insert NTP_AN_SHOHIN Data Bindding JavaBean
     * @param bean NTP_AN_SHOHIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpAnShohinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_AN_SHOHIN(");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NAS_AUID,");
            sql.addSql("   NAS_ADATE,");
            sql.addSql("   NAS_EUID,");
            sql.addSql("   NAS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getNhnSid());
            sql.addIntValue(bean.getNasAuid());
            sql.addDateValue(bean.getNasAdate());
            sql.addIntValue(bean.getNasEuid());
            sql.addDateValue(bean.getNasEdate());
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
     * <p>Update NTP_AN_SHOHIN Data Bindding JavaBean
     * @param bean NTP_AN_SHOHIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpAnShohinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_AN_SHOHIN");
            sql.addSql(" set ");
            sql.addSql("   NAN_SID=?,");
            sql.addSql("   NHN_SID=?,");
            sql.addSql("   NAS_EUID=?,");
            sql.addSql("   NAS_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getNhnSid());
            sql.addIntValue(bean.getNasEuid());
            sql.addDateValue(bean.getNasEdate());

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
     * <p>Select NTP_AN_SHOHIN All Data
     * @return List in NTP_AN_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnShohinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpAnShohinModel> ret = new ArrayList<NtpAnShohinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NAS_AUID,");
            sql.addSql("   NAS_ADATE,");
            sql.addSql("   NAS_EUID,");
            sql.addSql("   NAS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_AN_SHOHIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpAnShohinFromRs(rs));
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
     * <p>Select NTP_AN_SHOHIN
     * @param nanSid NAN_SID
     * @param nhnSid NHN_SID
     * @return NTP_AN_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public NtpAnShohinModel select(int nanSid, int nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpAnShohinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NAS_AUID,");
            sql.addSql("   NAS_ADATE,");
            sql.addSql("   NAS_EUID,");
            sql.addSql("   NAS_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");
            sql.addSql(" and");
            sql.addSql("   NHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);
            sql.addIntValue(nhnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpAnShohinFromRs(rs);
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
     * <p>Select NTP_AN_SHOHIN
     * @param nanSid NAN_SID
     * @return NTP_AN_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public String[] select(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("NHN_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[0]);
    }

    /**
     * <p>Select NTP_AN_SHOHIN_HISTORY
     * @param nahSid NAH_SID
     * @return NTP_AN_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public String[] selectFromHistory(int nahSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_SHOHIN_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   NAH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("NHN_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[0]);
    }

    /**
     * <p>Delete NTP_AN_SHOHIN
     * @param nanSid NAN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

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
     * <p>Delete NTP_AN_SHOHIN
     * @param nanSid NAN_SID
     * @param nhnSid NHN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nanSid, int nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");
            sql.addSql(" and");
            sql.addSql("   NHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);
            sql.addIntValue(nhnSid);

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
     * <p>Create NTP_AN_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpAnShohinModel
     * @throws SQLException SQL実行例外
     */
    private NtpAnShohinModel __getNtpAnShohinFromRs(ResultSet rs) throws SQLException {
        NtpAnShohinModel bean = new NtpAnShohinModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNhnSid(rs.getInt("NHN_SID"));
        bean.setNasAuid(rs.getInt("NAS_AUID"));
        bean.setNasAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAS_ADATE")));
        bean.setNasEuid(rs.getInt("NAS_EUID"));
        bean.setNasEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAS_EDATE")));
        return bean;
    }
}
