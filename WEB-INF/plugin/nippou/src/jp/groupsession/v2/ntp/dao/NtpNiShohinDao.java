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
import jp.groupsession.v2.ntp.model.NtpNiShohinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_NI_SHOHIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpNiShohinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpNiShohinDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpNiShohinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpNiShohinDao(Connection con) {
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
            sql.addSql("drop table NTP_NI_SHOHIN");

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
            sql.addSql(" create table NTP_NI_SHOHIN (");
            sql.addSql("   NSH_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   MHN_SID NUMBER(10,0) not null,");
            sql.addSql("   NSH_NAME varchar(50),");
            sql.addSql("   NSH_PRICE_SALE NUMBER(10,0),");
            sql.addSql("   NSH_PRICE_COST NUMBER(10,0),");
            sql.addSql("   NSH_AUID NUMBER(10,0),");
            sql.addSql("   NSH_ADATE varchar(23) not null,");
            sql.addSql("   NSH_EUID NUMBER(10,0),");
            sql.addSql("   NSH_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NSH_SID)");
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
     * <p>Insert NTP_NI_SHOHIN Data Bindding JavaBean
     * @param bean NTP_NI_SHOHIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpNiShohinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_NI_SHOHIN(");
            sql.addSql("   NSH_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   MHN_SID,");
            sql.addSql("   NSH_NAME,");
            sql.addSql("   NSH_PRICE_SALE,");
            sql.addSql("   NSH_PRICE_COST,");
            sql.addSql("   NSH_AUID,");
            sql.addSql("   NSH_ADATE,");
            sql.addSql("   NSH_EUID,");
            sql.addSql("   NSH_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNshSid());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getMhnSid());
            sql.addStrValue(bean.getNshName());
            sql.addIntValue(bean.getNshPriceSale());
            sql.addIntValue(bean.getNshPriceCost());
            sql.addIntValue(bean.getNshAuid());
            sql.addDateValue(bean.getNshAdate());
            sql.addIntValue(bean.getNshEuid());
            sql.addDateValue(bean.getNshEdate());
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
     * <p>Update NTP_NI_SHOHIN Data Bindding JavaBean
     * @param bean NTP_NI_SHOHIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpNiShohinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_NI_SHOHIN");
            sql.addSql(" set ");
            sql.addSql("   NIP_SID=?,");
            sql.addSql("   MHN_SID=?,");
            sql.addSql("   NSH_NAME=?,");
            sql.addSql("   NSH_PRICE_SALE=?,");
            sql.addSql("   NSH_PRICE_COST=?,");
            sql.addSql("   NSH_AUID=?,");
            sql.addSql("   NSH_ADATE=?,");
            sql.addSql("   NSH_EUID=?,");
            sql.addSql("   NSH_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NSH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getMhnSid());
            sql.addStrValue(bean.getNshName());
            sql.addIntValue(bean.getNshPriceSale());
            sql.addIntValue(bean.getNshPriceCost());
            sql.addIntValue(bean.getNshAuid());
            sql.addDateValue(bean.getNshAdate());
            sql.addIntValue(bean.getNshEuid());
            sql.addDateValue(bean.getNshEdate());
            //where
            sql.addIntValue(bean.getNshSid());

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
     * <p>Select NTP_NI_SHOHIN All Data
     * @return List in NTP_NI_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpNiShohinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpNiShohinModel> ret = new ArrayList<NtpNiShohinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSH_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   MHN_SID,");
            sql.addSql("   NSH_NAME,");
            sql.addSql("   NSH_PRICE_SALE,");
            sql.addSql("   NSH_PRICE_COST,");
            sql.addSql("   NSH_AUID,");
            sql.addSql("   NSH_ADATE,");
            sql.addSql("   NSH_EUID,");
            sql.addSql("   NSH_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_NI_SHOHIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpNiShohinFromRs(rs));
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
     * <p>Select NTP_NI_SHOHIN
     * @param nshSid NSH_SID
     * @return NTP_NI_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public NtpNiShohinModel select(int nshSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpNiShohinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NSH_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   MHN_SID,");
            sql.addSql("   NSH_NAME,");
            sql.addSql("   NSH_PRICE_SALE,");
            sql.addSql("   NSH_PRICE_COST,");
            sql.addSql("   NSH_AUID,");
            sql.addSql("   NSH_ADATE,");
            sql.addSql("   NSH_EUID,");
            sql.addSql("   NSH_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_NI_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NSH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nshSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpNiShohinFromRs(rs);
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
     * <p>Delete NTP_NI_SHOHIN
     * @param nshSid NSH_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nshSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_NI_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NSH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nshSid);

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
     * <p>Create NTP_NI_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpNiShohinModel
     * @throws SQLException SQL実行例外
     */
    private NtpNiShohinModel __getNtpNiShohinFromRs(ResultSet rs) throws SQLException {
        NtpNiShohinModel bean = new NtpNiShohinModel();
        bean.setNshSid(rs.getInt("NSH_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setMhnSid(rs.getInt("MHN_SID"));
        bean.setNshName(rs.getString("NSH_NAME"));
        bean.setNshPriceSale(rs.getInt("NSH_PRICE_SALE"));
        bean.setNshPriceCost(rs.getInt("NSH_PRICE_COST"));
        bean.setNshAuid(rs.getInt("NSH_AUID"));
        bean.setNshAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NSH_ADATE")));
        bean.setNshEuid(rs.getInt("NSH_EUID"));
        bean.setNshEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NSH_EDATE")));
        return bean;
    }
}
