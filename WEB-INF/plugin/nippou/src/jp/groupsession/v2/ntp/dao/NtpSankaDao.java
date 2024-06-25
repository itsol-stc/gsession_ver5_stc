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
import jp.groupsession.v2.ntp.model.NtpSankaModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_SANKA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSankaDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpSankaDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpSankaDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpSankaDao(Connection con) {
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
            sql.addSql("drop table NTP_SANKA");

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
            sql.addSql(" create table NTP_SANKA (");
            sql.addSql("   NSN_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NTA_AUID NUMBER(10,0),");
            sql.addSql("   NTA_ADATE varchar(23) not null,");
            sql.addSql("   NTA_EUID NUMBER(10,0),");
            sql.addSql("   NTA_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NSN_SID)");
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
     * <p>Insert NTP_SANKA Data Bindding JavaBean
     * @param bean NTP_SANKA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpSankaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SANKA(");
            sql.addSql("   NSN_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NTA_AUID,");
            sql.addSql("   NTA_ADATE,");
            sql.addSql("   NTA_EUID,");
            sql.addSql("   NTA_EDATE");
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
            sql.addIntValue(bean.getNsnSid());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNtaAuid());
            sql.addDateValue(bean.getNtaAdate());
            sql.addIntValue(bean.getNtaEuid());
            sql.addDateValue(bean.getNtaEdate());
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
     * <p>Update NTP_SANKA Data Bindding JavaBean
     * @param bean NTP_SANKA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpSankaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SANKA");
            sql.addSql(" set ");
            sql.addSql("   NIP_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   NTA_AUID=?,");
            sql.addSql("   NTA_ADATE=?,");
            sql.addSql("   NTA_EUID=?,");
            sql.addSql("   NTA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NSN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNtaAuid());
            sql.addDateValue(bean.getNtaAdate());
            sql.addIntValue(bean.getNtaEuid());
            sql.addDateValue(bean.getNtaEdate());
            //where
            sql.addIntValue(bean.getNsnSid());

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
     * <p>Select NTP_SANKA All Data
     * @return List in NTP_SANKAModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpSankaModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpSankaModel> ret = new ArrayList<NtpSankaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSN_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NTA_AUID,");
            sql.addSql("   NTA_ADATE,");
            sql.addSql("   NTA_EUID,");
            sql.addSql("   NTA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_SANKA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSankaFromRs(rs));
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
     * <p>Select NTP_SANKA
     * @param nsnSid NSN_SID
     * @return NTP_SANKAModel
     * @throws SQLException SQL実行例外
     */
    public NtpSankaModel select(int nsnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpSankaModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NSN_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NTA_AUID,");
            sql.addSql("   NTA_ADATE,");
            sql.addSql("   NTA_EUID,");
            sql.addSql("   NTA_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_SANKA");
            sql.addSql(" where ");
            sql.addSql("   NSN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nsnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpSankaFromRs(rs);
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
     * <p>Delete NTP_SANKA
     * @param nsnSid NSN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nsnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SANKA");
            sql.addSql(" where ");
            sql.addSql("   NSN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nsnSid);

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
     * <p>Create NTP_SANKA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpSankaModel
     * @throws SQLException SQL実行例外
     */
    private NtpSankaModel __getNtpSankaFromRs(ResultSet rs) throws SQLException {
        NtpSankaModel bean = new NtpSankaModel();
        bean.setNsnSid(rs.getInt("NSN_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNtaAuid(rs.getInt("NTA_AUID"));
        bean.setNtaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTA_ADATE")));
        bean.setNtaEuid(rs.getInt("NTA_EUID"));
        bean.setNtaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTA_EDATE")));
        return bean;
    }
}
