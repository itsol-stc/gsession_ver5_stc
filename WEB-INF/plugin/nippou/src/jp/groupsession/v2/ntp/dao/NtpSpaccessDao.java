package jp.groupsession.v2.ntp.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpSpaccessModel;

/**
 * <p>NTP_SPACCESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSpaccessDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpSpaccessDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpSpaccessDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpSpaccessDao(Connection con) {
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
            sql.addSql("drop table NTP_SPACCESS");

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
            sql.addSql(" create table NTP_SPACCESS (");
            sql.addSql("   NSA_SID NUMBER(10,0) not null primary key,");
            sql.addSql("   NSA_NAME varchar(2147483647) not null,");
            sql.addSql("   NSA_BIKO varchar(2147483647),");
            sql.addSql("   NSA_AUID NUMBER(10,0),");
            sql.addSql("   NSA_ADATE varchar(23),");
            sql.addSql("   NSA_EUID NUMBER(10,0),");
            sql.addSql("   NSA_EDATE varchar(23)");
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
     * <p>Insert NTP_SPACCESS Data Bindding JavaBean
     * @param bean NTP_SPACCESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpSpaccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SPACCESS(");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NSA_NAME,");
            sql.addSql("   NSA_BIKO,");
            sql.addSql("   NSA_AUID,");
            sql.addSql("   NSA_ADATE,");
            sql.addSql("   NSA_EUID,");
            sql.addSql("   NSA_EDATE");
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
            sql.addIntValue(bean.getNsaSid());
            sql.addStrValue(bean.getNsaName());
            sql.addStrValue(bean.getNsaBiko());
            sql.addIntValue(bean.getNsaAuid());
            sql.addDateValue(bean.getNsaAdate());
            sql.addIntValue(bean.getNsaEuid());
            sql.addDateValue(bean.getNsaEdate());
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
     * <p>Update NTP_SPACCESS Data Bindding JavaBean
     * @param bean NTP_SPACCESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpSpaccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SPACCESS");
            sql.addSql(" set ");
            sql.addSql("   NSA_SID=?,");
            sql.addSql("   NSA_NAME=?,");
            sql.addSql("   NSA_BIKO=?,");
            sql.addSql("   NSA_AUID=?,");
            sql.addSql("   NSA_ADATE=?,");
            sql.addSql("   NSA_EUID=?,");
            sql.addSql("   NSA_EDATE=?");
            sql.addSql(" where");
            sql.addSql("   NSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNsaSid());
            sql.addStrValue(bean.getNsaName());
            sql.addStrValue(bean.getNsaBiko());
            sql.addIntValue(bean.getNsaAuid());
            sql.addDateValue(bean.getNsaAdate());
            sql.addIntValue(bean.getNsaEuid());
            sql.addDateValue(bean.getNsaEdate());
            sql.addIntValue(bean.getNsaSid());

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
     * <p>Update NTP_SPACCESS Data Bindding JavaBean
     * @param nsaSid 日報特例アクセスSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int nsaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SPACCESS");
            sql.addSql(" where");
            sql.addSql("   NSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nsaSid);

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
     * <p>Select NTP_SPACCESS All Data
     * @return ArrayList in NTP_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpSpaccessModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSpaccessModel> ret = new ArrayList<NtpSpaccessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NSA_NAME,");
            sql.addSql("   NSA_BIKO,");
            sql.addSql("   NSA_AUID,");
            sql.addSql("   NSA_ADATE,");
            sql.addSql("   NSA_EUID,");
            sql.addSql("   NSA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSpaccessFromRs(rs));
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
     * <p>Select NTP_SPACCESS All Data
     * @param nsaSid 日報特例アクセスSID
     * @return ArrayList in NTP_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public NtpSpaccessModel select(int nsaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpSpaccessModel ret = new NtpSpaccessModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NSA_NAME,");
            sql.addSql("   NSA_BIKO,");
            sql.addSql("   NSA_AUID,");
            sql.addSql("   NSA_ADATE,");
            sql.addSql("   NSA_EUID,");
            sql.addSql("   NSA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS");
            sql.addSql("  where");
            sql.addSql("   NSA_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nsaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getNtpSpaccessFromRs(rs);
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
     * <p>Create NTP_SPACCESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpSpaccessModel
     * @throws SQLException SQL実行例外
     */
    private NtpSpaccessModel __getNtpSpaccessFromRs(ResultSet rs) throws SQLException {
        NtpSpaccessModel bean = new NtpSpaccessModel();
        bean.setNsaSid(rs.getInt("NSA_SID"));
        bean.setNsaName(rs.getString("NSA_NAME"));
        bean.setNsaBiko(rs.getString("NSA_BIKO"));
        bean.setNsaAuid(rs.getInt("NSA_AUID"));
        bean.setNsaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NSA_ADATE")));
        bean.setNsaEuid(rs.getInt("NSA_EUID"));
        bean.setNsaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NSA_EDATE")));
        return bean;
    }
}
