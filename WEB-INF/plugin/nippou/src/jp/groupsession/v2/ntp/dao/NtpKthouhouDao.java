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
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_KTHOUHOU Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpKthouhouDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpKthouhouDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpKthouhouDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpKthouhouDao(Connection con) {
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
            sql.addSql("drop table NTP_KTHOUHOU");

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
            sql.addSql(" create table NTP_KTHOUHOU (");
            sql.addSql("   NKH_SID NUMBER(10,0) not null,");
            sql.addSql("   NKH_CODE varchar(5) not null,");
            sql.addSql("   NKH_NAME varchar(50) not null,");
            sql.addSql("   NKH_AUID NUMBER(10,0),");
            sql.addSql("   NKH_ADATE varchar(23),");
            sql.addSql("   NKH_EUID NUMBER(10,0),");
            sql.addSql("   NKH_EDATE varchar(23),");
            sql.addSql("   primary key (NKH_SID)");
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
     * <p>Insert NTP_KTHOUHOU Data Bindding JavaBean
     * @param bean NTP_KTHOUHOU Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpKthouhouModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_KTHOUHOU(");
            sql.addSql("   NKH_SID,");
            sql.addSql("   NKH_CODE,");
            sql.addSql("   NKH_NAME,");
            sql.addSql("   NKH_AUID,");
            sql.addSql("   NKH_ADATE,");
            sql.addSql("   NKH_EUID,");
            sql.addSql("   NKH_EDATE");
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
            sql.addIntValue(bean.getNkhSid());
            sql.addStrValue(bean.getNkhCode());
            sql.addStrValue(bean.getNkhName());
            sql.addIntValue(bean.getNkhAuid());
            sql.addDateValue(bean.getNkhAdate());
            sql.addIntValue(bean.getNkhEuid());
            sql.addDateValue(bean.getNkhEdate());
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
     * <p>Update NTP_KTHOUHOU Data Bindding JavaBean
     * @param bean NTP_KTHOUHOU Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpKthouhouModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql(" set ");
            sql.addSql("   NKH_CODE=?,");
            sql.addSql("   NKH_NAME=?,");
            sql.addSql("   NKH_EUID=?,");
            sql.addSql("   NKH_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NKH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNkhCode());
            sql.addStrValue(bean.getNkhName());
            sql.addIntValue(bean.getNkhEuid());
            sql.addDateValue(bean.getNkhEdate());
            //where
            sql.addIntValue(bean.getNkhSid());

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
     * <p>Select NTP_KTHOUHOU All Data
     * @return List in NTP_KTHOUHOUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpKthouhouModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpKthouhouModel> ret = new ArrayList<NtpKthouhouModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NKH_SID,");
            sql.addSql("   NKH_CODE,");
            sql.addSql("   NKH_NAME,");
            sql.addSql("   NKH_AUID,");
            sql.addSql("   NKH_ADATE,");
            sql.addSql("   NKH_EUID,");
            sql.addSql("   NKH_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql(" order by ");
            sql.addSql("   NKH_CODE asc");


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpKthouhouFromRs(rs));
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
     * <p>Select NTP_KTHOUHOU
     * @param nkhSid NKH_SID
     * @return NTP_KTHOUHOUModel
     * @throws SQLException SQL実行例外
     */
    public NtpKthouhouModel select(int nkhSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpKthouhouModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKH_SID,");
            sql.addSql("   NKH_CODE,");
            sql.addSql("   NKH_NAME,");
            sql.addSql("   NKH_AUID,");
            sql.addSql("   NKH_ADATE,");
            sql.addSql("   NKH_EUID,");
            sql.addSql("   NKH_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql(" where ");
            sql.addSql("   NKH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nkhSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpKthouhouFromRs(rs);
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
     * <p>Select NTP_KTHOUHOU
     * @param nkhCode NKH_CODE
     * @return NTP_KTHOUHOUModel
     * @throws SQLException SQL実行例外
     */
    public NtpKthouhouModel select(String nkhCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpKthouhouModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKH_SID,");
            sql.addSql("   NKH_CODE,");
            sql.addSql("   NKH_NAME,");
            sql.addSql("   NKH_AUID,");
            sql.addSql("   NKH_ADATE,");
            sql.addSql("   NKH_EUID,");
            sql.addSql("   NKH_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql(" where ");
            sql.addSql("   NKH_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(nkhCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpKthouhouFromRs(rs);
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
     * <br>[機  能] 指定した活動方法コードに該当する活動方法情報が存在するかをチェックする
     * <br>[解  説] 指定した活動方法SID > 0 の場合、指定した活動方法SID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param nkhSid 活動方法SID
     * @param nkhCode 活動方法コード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existKthouhou(int nkhSid, String nkhCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NKH_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql(" where ");
            sql.addSql("   NKH_CODE=?");
            sql.addStrValue(nkhCode);

            if (nkhSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NKH_SID != ?");
                sql.addIntValue(nkhSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }
    /**
     * <p>Delete NTP_KTHOUHOU
     * @param nkhSid NKH_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nkhSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql(" where ");
            sql.addSql("   NKH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nkhSid);

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
     * <p>Create NTP_KTHOUHOU Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpKthouhouModel
     * @throws SQLException SQL実行例外
     */
    private NtpKthouhouModel __getNtpKthouhouFromRs(ResultSet rs) throws SQLException {
        NtpKthouhouModel bean = new NtpKthouhouModel();
        bean.setNkhSid(rs.getInt("NKH_SID"));
        bean.setNkhCode(rs.getString("NKH_CODE"));
        bean.setNkhName(rs.getString("NKH_NAME"));
        bean.setNkhAuid(rs.getInt("NKH_AUID"));
        bean.setNkhAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKH_ADATE")));
        bean.setNkhEuid(rs.getInt("NKH_EUID"));
        bean.setNkhEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKH_EDATE")));
        return bean;
    }
}
