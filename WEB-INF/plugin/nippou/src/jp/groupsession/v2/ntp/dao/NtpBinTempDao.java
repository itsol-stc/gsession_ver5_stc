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
import jp.groupsession.v2.ntp.model.NtpBinTempuModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_BIN_TEMPU Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpBinTempDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpBinTempDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpBinTempDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpBinTempDao(Connection con) {
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
            sql.addSql("drop table NTP_BIN_TEMPU");

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
            sql.addSql(" create table NTP_BIN_TEMPU (");
            sql.addSql("   NBT_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   NBT_AUID NUMBER(10,0),");
            sql.addSql("   NBT_ADATE varchar(23) not null,");
            sql.addSql("   NBT_EUID NUMBER(10,0),");
            sql.addSql("   NBT_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NBT_SID)");
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
     * <p>Insert NTP_BIN_TEMPU Data Bindding JavaBean
     * @param bean NTP_BIN_TEMPU Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpBinTempuModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_BIN_TEMPU(");
            sql.addSql("   NBT_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   NBT_AUID,");
            sql.addSql("   NBT_ADATE,");
            sql.addSql("   NBT_EUID,");
            sql.addSql("   NBT_EDATE");
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
            sql.addIntValue(bean.getNbtSid());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getBinSid());
            sql.addIntValue(bean.getNbtAuid());
            sql.addDateValue(bean.getNbtAdate());
            sql.addIntValue(bean.getNbtEuid());
            sql.addDateValue(bean.getNbtEdate());
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
     * <p>Update NTP_BIN_TEMPU Data Bindding JavaBean
     * @param bean NTP_BIN_TEMPU Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpBinTempuModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_BIN_TEMPU");
            sql.addSql(" set ");
            sql.addSql("   NIP_SID=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   NBT_AUID=?,");
            sql.addSql("   NBT_ADATE=?,");
            sql.addSql("   NBT_EUID=?,");
            sql.addSql("   NBT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NBT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getBinSid());
            sql.addIntValue(bean.getNbtAuid());
            sql.addDateValue(bean.getNbtAdate());
            sql.addIntValue(bean.getNbtEuid());
            sql.addDateValue(bean.getNbtEdate());
            //where
            sql.addIntValue(bean.getNbtSid());

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
     * <p>Select NTP_BIN_TEMPU All Data
     * @return List in NTP_BIN_TEMPUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpBinTempuModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpBinTempuModel> ret = new ArrayList<NtpBinTempuModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NBT_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   NBT_AUID,");
            sql.addSql("   NBT_ADATE,");
            sql.addSql("   NBT_EUID,");
            sql.addSql("   NBT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN_TEMPU");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpBinTempuFromRs(rs));
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
     * <p>Select NTP_BIN_TEMPU
     * @param nbtSid NBT_SID
     * @return NTP_BIN_TEMPUModel
     * @throws SQLException SQL実行例外
     */
    public NtpBinTempuModel select(int nbtSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpBinTempuModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NBT_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   NBT_AUID,");
            sql.addSql("   NBT_ADATE,");
            sql.addSql("   NBT_EUID,");
            sql.addSql("   NBT_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN_TEMPU");
            sql.addSql(" where ");
            sql.addSql("   NBT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nbtSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpBinTempuFromRs(rs);
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
     * <p>Delete NTP_BIN_TEMPU
     * @param nbtSid NBT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nbtSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN_TEMPU");
            sql.addSql(" where ");
            sql.addSql("   NBT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nbtSid);

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
     * <p>Create NTP_BIN_TEMPU Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpBinTempuModel
     * @throws SQLException SQL実行例外
     */
    private NtpBinTempuModel __getNtpBinTempuFromRs(ResultSet rs) throws SQLException {
        NtpBinTempuModel bean = new NtpBinTempuModel();
        bean.setNbtSid(rs.getInt("NBT_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setBinSid(rs.getInt("BIN_SID"));
        bean.setNbtAuid(rs.getInt("NBT_AUID"));
        bean.setNbtAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NBT_ADATE")));
        bean.setNbtEuid(rs.getInt("NBT_EUID"));
        bean.setNbtEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NBT_EDATE")));
        return bean;
    }
}
