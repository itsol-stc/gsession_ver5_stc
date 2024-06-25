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
import jp.groupsession.v2.ntp.model.NtpPriKakuninModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_PRI_KAKUNIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpPriKakuninDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpPriKakuninDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpPriKakuninDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpPriKakuninDao(Connection con) {
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
            sql.addSql("drop table NTP_PRI_KAKUNIN");

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
            sql.addSql(" create table NTP_PRI_KAKUNIN (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NPK_DFT_USR NUMBER(10,0) not null,");
            sql.addSql("   NPK_AUID NUMBER(10,0),");
            sql.addSql("   NPK_ADATE varchar(23) not null,");
            sql.addSql("   NPK_EUID NUMBER(10,0),");
            sql.addSql("   NPK_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID,NPK_DFT_USR)");
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
     * <p>Insert NTP_PRI_KAKUNIN Data Bindding JavaBean
     * @param bean NTP_PRI_KAKUNIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpPriKakuninModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_PRI_KAKUNIN(");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPK_DFT_USR,");
            sql.addSql("   NPK_AUID,");
            sql.addSql("   NPK_ADATE,");
            sql.addSql("   NPK_EUID,");
            sql.addSql("   NPK_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNpkDftUsr());
            sql.addIntValue(bean.getNpkAuid());
            sql.addDateValue(bean.getNpkAdate());
            sql.addIntValue(bean.getNpkEuid());
            sql.addDateValue(bean.getNpkEdate());
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
     * <p>Update NTP_PRI_KAKUNIN Data Bindding JavaBean
     * @param bean NTP_PRI_KAKUNIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpPriKakuninModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_KAKUNIN");
            sql.addSql(" set ");
            sql.addSql("   NPK_AUID=?,");
            sql.addSql("   NPK_ADATE=?,");
            sql.addSql("   NPK_EUID=?,");
            sql.addSql("   NPK_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   NPK_DFT_USR=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNpkAuid());
            sql.addDateValue(bean.getNpkAdate());
            sql.addIntValue(bean.getNpkEuid());
            sql.addDateValue(bean.getNpkEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNpkDftUsr());

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
     * <p>Select NTP_PRI_KAKUNIN All Data
     * @return List in NTP_PRI_KAKUNINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpPriKakuninModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpPriKakuninModel> ret = new ArrayList<NtpPriKakuninModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPK_DFT_USR,");
            sql.addSql("   NPK_AUID,");
            sql.addSql("   NPK_ADATE,");
            sql.addSql("   NPK_EUID,");
            sql.addSql("   NPK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_PRI_KAKUNIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpPriKakuninFromRs(rs));
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
     * <p>Select NTP_PRI_KAKUNIN
     * @param usrSid USR_SID
     * @param npkDftUsr NPK_DFT_USR
     * @return NTP_PRI_KAKUNINModel
     * @throws SQLException SQL実行例外
     */
    public NtpPriKakuninModel select(int usrSid, int npkDftUsr) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpPriKakuninModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPK_DFT_USR,");
            sql.addSql("   NPK_AUID,");
            sql.addSql("   NPK_ADATE,");
            sql.addSql("   NPK_EUID,");
            sql.addSql("   NPK_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_KAKUNIN");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   NPK_DFT_USR=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(npkDftUsr);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpPriKakuninFromRs(rs);
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
     * <p>ユーザSIDから確認者情報を取得
     * @param usrSid USR_SID
     * @return NTP_PRI_KAKUNINModel
     * @throws SQLException SQL実行例外
     */
    public List <NtpPriKakuninModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList <NtpPriKakuninModel> ret = new ArrayList <NtpPriKakuninModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPK_DFT_USR,");
            sql.addSql("   NPK_AUID,");
            sql.addSql("   NPK_ADATE,");
            sql.addSql("   NPK_EUID,");
            sql.addSql("   NPK_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_KAKUNIN");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpPriKakuninFromRs(rs));
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
     * <p>Delete NTP_PRI_KAKUNIN
     * @param usrSid USR_SID
     * @param npkDftUsr NPK_DFT_USR
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int npkDftUsr) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_KAKUNIN");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   NPK_DFT_USR=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(npkDftUsr);

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
     * <p>ユーザSIDを指定して削除
     * @param usrSid USR_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_KAKUNIN");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create NTP_PRI_KAKUNIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpPriKakuninModel
     * @throws SQLException SQL実行例外
     */
    private NtpPriKakuninModel __getNtpPriKakuninFromRs(ResultSet rs) throws SQLException {
        NtpPriKakuninModel bean = new NtpPriKakuninModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNpkDftUsr(rs.getInt("NPK_DFT_USR"));
        bean.setNpkAuid(rs.getInt("NPK_AUID"));
        bean.setNpkAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPK_ADATE")));
        bean.setNpkEuid(rs.getInt("NPK_EUID"));
        bean.setNpkEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPK_EDATE")));
        return bean;
    }
}
