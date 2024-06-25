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
import jp.groupsession.v2.ntp.model.NtpAnMemberHistoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_AN_MEMBER_HISTORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpAnMemberHistoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpAnMemberHistoryDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpAnMemberHistoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpAnMemberHistoryDao(Connection con) {
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
            sql.addSql("drop table NTP_AN_MEMBER_HISTORY");

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
            sql.addSql(" create table NTP_AN_MEMBER_HISTORY (");
            sql.addSql("   NAH_SID NUMBER(10,0) not null,");
            sql.addSql("   NAN_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NAM_AUID NUMBER(10,0),");
            sql.addSql("   NAM_ADATE varchar(23) not null,");
            sql.addSql("   NAM_EUID NUMBER(10,0),");
            sql.addSql("   NAM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NAH_SID, USR_SID)");
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
     * <p>Insert NTP_AN_MEMBER_HISTORY Data Bindding JavaBean
     * @param bean NTP_AN_MEMBER_HISTORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpAnMemberHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_AN_MEMBER_HISTORY(");
            sql.addSql("   NAH_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NAM_AUID,");
            sql.addSql("   NAM_ADATE,");
            sql.addSql("   NAM_EUID,");
            sql.addSql("   NAM_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNahSid());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNamAuid());
            sql.addDateValue(bean.getNamAdate());
            sql.addIntValue(bean.getNamEuid());
            sql.addDateValue(bean.getNamEdate());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
//
//    /**
//     * <p>Update NTP_AN_MEMBER_HISTORY Data Bindding JavaBean
//     * @param bean NTP_AN_MEMBER_HISTORY Data Bindding JavaBean
//     * @return 更新件数
//     * @throws SQLException SQL実行例外
//     */
//    public int update(NtpAnMemberHistoryModel bean) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        int count = 0;
//        Connection con = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" update");
//            sql.addSql("   NTP_AN_MEMBER_HISTORY");
//            sql.addSql(" set ");
//            sql.addSql("   NAN_SID=?,");
//            sql.addSql("   USR_SID=?,");
//            sql.addSql("   NAM_EUID=?,");
//            sql.addSql("   NAM_EDATE=?");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.addIntValue(bean.getNanSid());
//            sql.addIntValue(bean.getUsrSid());
//            sql.addIntValue(bean.getNamEuid());
//            sql.addDateValue(bean.getNamEdate());
//
//            log__.info(sql.toLogString());
//            sql.setParameter(pstmt);
//            count = pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return count;
//    }

    /**
     * <p>Select NTP_AN_MEMBER_HISTORY All Data
     * @return List in NTP_AN_MEMBER_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnMemberHistoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpAnMemberHistoryModel> ret = new ArrayList<NtpAnMemberHistoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAH_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NAM_AUID,");
            sql.addSql("   NAM_ADATE,");
            sql.addSql("   NAM_EUID,");
            sql.addSql("   NAM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpAnShohinHistoryFromRs(rs));
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
     * <p>Select NTP_AN_MEMBER_HISTORY
     * @param nahSid NAH_SID
     * @param nanSid NAN_SID
     * @param nhnSid USR_SID
     * @return NTP_AN_MEMBER_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public NtpAnMemberHistoryModel select(int nahSid, int nanSid, int nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpAnMemberHistoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NAH_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NAM_AUID,");
            sql.addSql("   NAM_ADATE,");
            sql.addSql("   NAM_EUID,");
            sql.addSql("   NAM_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   NAH_SID=?");
            sql.addSql(" and ");
            sql.addSql("   NAN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);
            sql.addIntValue(nanSid);
            sql.addIntValue(nhnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpAnShohinHistoryFromRs(rs);
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
     * <p>Select NTP_AN_MEMBER_HISTORY
     * @param nahSid NAH_SID
     * @return NTP_AN_MEMBER_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public String[] select(int nahSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   NAH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("USR_SID")));
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
     * <p>Delete NTP_AN_MEMBER_HISTORY
     * @param nahSid NAH_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nahSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   NAH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);

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
     * <p>Delete NTP_AN_MEMBER_HISTORY
     * @param nanSid NAN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteAnken(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");
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
     * <p>Delete NTP_AN_MEMBER_HISTORY
     * @param nahSid NAH_SID
     * @param nhnSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nahSid, int nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   NAH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);
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
     * <p>Create NTP_AN_MEMBER_HISTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpAnMemberHistoryModel
     * @throws SQLException SQL実行例外
     */
    private NtpAnMemberHistoryModel __getNtpAnShohinHistoryFromRs(ResultSet rs)
                                                             throws SQLException {
        NtpAnMemberHistoryModel bean = new NtpAnMemberHistoryModel();
        bean.setNahSid(rs.getInt("NAH_SID"));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNamAuid(rs.getInt("NAM_AUID"));
        bean.setNamAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAM_ADATE")));
        bean.setNamEuid(rs.getInt("NAM_EUID"));
        bean.setNamEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAM_EDATE")));
        return bean;
    }
}
