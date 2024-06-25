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
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpKakuninModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_KAKUNIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpKakuninDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpKakuninDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpKakuninDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpKakuninDao(Connection con) {
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
            sql.addSql("drop table NTP_KAKUNIN");

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
            sql.addSql(" create table NTP_KAKUNIN (");
            sql.addSql("   NKK_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NKK_CHECK NUMBER(10,0) not null,");
            sql.addSql("   NKK_DATE_CHECK varchar(23),");
            sql.addSql("   NKK_AUID NUMBER(10,0),");
            sql.addSql("   NKK_ADATE varchar(23) not null,");
            sql.addSql("   NKK_EUID NUMBER(10,0),");
            sql.addSql("   NKK_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NKK_SID)");
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
     * <p>Insert NTP_KAKUNIN Data Bindding JavaBean
     * @param bean NTP_KAKUNIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpKakuninModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_KAKUNIN(");
            sql.addSql("   NKK_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NKK_CHECK,");
            sql.addSql("   NKK_DATE_CHECK,");
            sql.addSql("   NKK_AUID,");
            sql.addSql("   NKK_ADATE,");
            sql.addSql("   NKK_EUID,");
            sql.addSql("   NKK_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNkkSid());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNkkCheck());
            sql.addDateValue(bean.getNkkDateCheck());
            sql.addIntValue(bean.getNkkAuid());
            sql.addDateValue(bean.getNkkAdate());
            sql.addIntValue(bean.getNkkEuid());
            sql.addDateValue(bean.getNkkEdate());
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
     * <p>Update NTP_KAKUNIN Data Bindding JavaBean
     * @param bean NTP_KAKUNIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpKakuninModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_KAKUNIN");
            sql.addSql(" set ");
            sql.addSql("   NIP_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   NKK_CHECK=?,");
            sql.addSql("   NKK_DATE_CHECK=?,");
            sql.addSql("   NKK_AUID=?,");
            sql.addSql("   NKK_ADATE=?,");
            sql.addSql("   NKK_EUID=?,");
            sql.addSql("   NKK_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NKK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNkkCheck());
            sql.addDateValue(bean.getNkkDateCheck());
            sql.addIntValue(bean.getNkkAuid());
            sql.addDateValue(bean.getNkkAdate());
            sql.addIntValue(bean.getNkkEuid());
            sql.addDateValue(bean.getNkkEdate());
            //where
            sql.addIntValue(bean.getNkkSid());

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
     * <p>Update NTP_KAKUNIN Data Bindding JavaBean
     * @param bean NTP_KAKUNINModel
     * @param usrSid ユーザSID
     * @param udYrk 報告日
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateKakunin(NtpKakuninModel bean, int usrSid, UDate udYrk)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_KAKUNIN");
            sql.addSql(" set ");
            sql.addSql("   NKK_CHECK=?,");
            sql.addSql("   NKK_DATE_CHECK=?,");
            sql.addSql("   NKK_EUID=?,");
            sql.addSql("   NKK_EDATE=?");
            sql.addSql(" where exists(");
            sql.addSql(" select ");
            sql.addSql("   1 ");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   NTP_KAKUNIN.NIP_SID = NTP_DATA.NIP_SID");
            sql.addSql(" and ");
            sql.addSql("   NTP_KAKUNIN.NKK_CHECK=?");
            sql.addSql(" and ");
            sql.addSql("   NTP_KAKUNIN.USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   NTP_DATA.NIP_DATE=?");
            sql.addSql(" and ");
            sql.addSql("   NTP_DATA.USR_SID=?");
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstNippou.KAKUNIN_YES);
            sql.addDateValue(bean.getNkkDateCheck());
            sql.addIntValue(bean.getNkkEuid());
            sql.addDateValue(bean.getNkkEdate());
            //where
            sql.addIntValue(GSConstNippou.KAKUNIN_NO);
            sql.addIntValue(bean.getNkkEuid());
            sql.addDateValue(udYrk);
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
     * <p>Select NTP_KAKUNIN All Data
     * @return List in NTP_KAKUNINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpKakuninModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpKakuninModel> ret = new ArrayList<NtpKakuninModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NKK_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NKK_CHECK,");
            sql.addSql("   NKK_DATE_CHECK,");
            sql.addSql("   NKK_AUID,");
            sql.addSql("   NKK_ADATE,");
            sql.addSql("   NKK_EUID,");
            sql.addSql("   NKK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_KAKUNIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpKakuninFromRs(rs));
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
     * <p>Select NTP_KAKUNIN
     * @param nkkSid NKK_SID
     * @return NTP_KAKUNINModel
     * @throws SQLException SQL実行例外
     */
    public NtpKakuninModel select(int nkkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpKakuninModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKK_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NKK_CHECK,");
            sql.addSql("   NKK_DATE_CHECK,");
            sql.addSql("   NKK_AUID,");
            sql.addSql("   NKK_ADATE,");
            sql.addSql("   NKK_EUID,");
            sql.addSql("   NKK_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_KAKUNIN");
            sql.addSql(" where ");
            sql.addSql("   NKK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nkkSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpKakuninFromRs(rs);
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
     * <p>Delete NTP_KAKUNIN
     * @param nkkSid NKK_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nkkSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_KAKUNIN");
            sql.addSql(" where ");
            sql.addSql("   NKK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nkkSid);

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
     * <p>Create NTP_KAKUNIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpKakuninModel
     * @throws SQLException SQL実行例外
     */
    private NtpKakuninModel __getNtpKakuninFromRs(ResultSet rs) throws SQLException {
        NtpKakuninModel bean = new NtpKakuninModel();
        bean.setNkkSid(rs.getInt("NKK_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNkkCheck(rs.getInt("NKK_CHECK"));
        bean.setNkkDateCheck(UDate.getInstanceTimestamp(rs.getTimestamp("NKK_DATE_CHECK")));
        bean.setNkkAuid(rs.getInt("NKK_AUID"));
        bean.setNkkAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKK_ADATE")));
        bean.setNkkEuid(rs.getInt("NKK_EUID"));
        bean.setNkkEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKK_EDATE")));
        return bean;
    }

}
