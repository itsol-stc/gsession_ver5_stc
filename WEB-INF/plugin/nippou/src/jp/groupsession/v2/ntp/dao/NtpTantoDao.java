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
import jp.groupsession.v2.ntp.model.NtpTantoModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_TANTO Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpTantoDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpTantoDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpTantoDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpTantoDao(Connection con) {
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
            sql.addSql("drop table NTP_TANTO");

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
            sql.addSql(" create table NTP_TANTO (");
            sql.addSql("   NTA_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   NTA_AUID NUMBER(10,0),");
            sql.addSql("   NTA_ADATE varchar(23) not null,");
            sql.addSql("   NTA_EUID NUMBER(10,0),");
            sql.addSql("   NTA_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NTA_SID)");
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
     * <p>Insert NTP_TANTO Data Bindding JavaBean
     * @param bean NTP_TANTO Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpTantoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_TANTO(");
            sql.addSql("   NTA_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   ADR_SID,");
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
            sql.addIntValue(bean.getNtaSid());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getAdrSid());
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
     * <p>Update NTP_TANTO Data Bindding JavaBean
     * @param bean NTP_TANTO Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpTantoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_TANTO");
            sql.addSql(" set ");
            sql.addSql("   NIP_SID=?,");
            sql.addSql("   ADR_SID=?,");
            sql.addSql("   NTA_AUID=?,");
            sql.addSql("   NTA_ADATE=?,");
            sql.addSql("   NTA_EUID=?,");
            sql.addSql("   NTA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NTA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getNtaAuid());
            sql.addDateValue(bean.getNtaAdate());
            sql.addIntValue(bean.getNtaEuid());
            sql.addDateValue(bean.getNtaEdate());
            //where
            sql.addIntValue(bean.getNtaSid());

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
     * <p>Select NTP_TANTO All Data
     * @return List in NTP_TANTOModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpTantoModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpTantoModel> ret = new ArrayList<NtpTantoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTA_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   NTA_AUID,");
            sql.addSql("   NTA_ADATE,");
            sql.addSql("   NTA_EUID,");
            sql.addSql("   NTA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_TANTO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpTantoFromRs(rs));
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
     * <p>Select NTP_TANTO
     * @param ntaSid NTA_SID
     * @return NTP_TANTOModel
     * @throws SQLException SQL実行例外
     */
    public NtpTantoModel select(int ntaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpTantoModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTA_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   NTA_AUID,");
            sql.addSql("   NTA_ADATE,");
            sql.addSql("   NTA_EUID,");
            sql.addSql("   NTA_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_TANTO");
            sql.addSql(" where ");
            sql.addSql("   NTA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpTantoFromRs(rs);
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
     * <p>Delete NTP_TANTO
     * @param ntaSid NTA_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int ntaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_TANTO");
            sql.addSql(" where ");
            sql.addSql("   NTA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntaSid);

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
     * <p>Create NTP_TANTO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpTantoModel
     * @throws SQLException SQL実行例外
     */
    private NtpTantoModel __getNtpTantoFromRs(ResultSet rs) throws SQLException {
        NtpTantoModel bean = new NtpTantoModel();
        bean.setNtaSid(rs.getInt("NTA_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setNtaAuid(rs.getInt("NTA_AUID"));
        bean.setNtaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTA_ADATE")));
        bean.setNtaEuid(rs.getInt("NTA_EUID"));
        bean.setNtaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTA_EDATE")));
        return bean;
    }
}
