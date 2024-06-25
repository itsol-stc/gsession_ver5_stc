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
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_KTBUNRUI Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpKtbunruiDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpKtbunruiDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpKtbunruiDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpKtbunruiDao(Connection con) {
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
            sql.addSql("drop table NTP_KTBUNRUI");

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
            sql.addSql(" create table NTP_KTBUNRUI (");
            sql.addSql("   NKB_SID NUMBER(10,0) not null,");
            sql.addSql("   NKB_CODE varchar(5) not null,");
            sql.addSql("   NKB_NAME varchar(50) not null,");
            sql.addSql("   NKB_TIEUP_KBN NUMBER(10,0) not null,");
            sql.addSql("   NKB_AUID NUMBER(10,0),");
            sql.addSql("   NKB_ADATE varchar(23),");
            sql.addSql("   NKB_EUID NUMBER(10,0),");
            sql.addSql("   NKB_EDATE varchar(23),");
            sql.addSql("   primary key (NKB_SID)");
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
     * <p>Insert NTP_KTBUNRUI Data Bindding JavaBean
     * @param bean NTP_KTBUNRUI Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpKtbunruiModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_KTBUNRUI(");
            sql.addSql("   NKB_SID,");
            sql.addSql("   NKB_CODE,");
            sql.addSql("   NKB_NAME,");
            sql.addSql("   NKB_TIEUP_KBN,");
            sql.addSql("   NKB_AUID,");
            sql.addSql("   NKB_ADATE,");
            sql.addSql("   NKB_EUID,");
            sql.addSql("   NKB_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNkbSid());
            sql.addStrValue(bean.getNkbCode());
            sql.addStrValue(bean.getNkbName());
            sql.addIntValue(bean.getNkbTieupKbn());
            sql.addIntValue(bean.getNkbAuid());
            sql.addDateValue(bean.getNkbAdate());
            sql.addIntValue(bean.getNkbEuid());
            sql.addDateValue(bean.getNkbEdate());
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
     * <p>Update NTP_KTBUNRUI Data Bindding JavaBean
     * @param bean NTP_KTBUNRUI Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpKtbunruiModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql(" set ");
            sql.addSql("   NKB_CODE=?,");
            sql.addSql("   NKB_NAME=?,");
            sql.addSql("   NKB_TIEUP_KBN=?,");
            sql.addSql("   NKB_EUID=?,");
            sql.addSql("   NKB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NKB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNkbCode());
            sql.addStrValue(bean.getNkbName());
            sql.addIntValue(bean.getNkbTieupKbn());
            sql.addIntValue(bean.getNkbEuid());
            sql.addDateValue(bean.getNkbEdate());
            //where
            sql.addIntValue(bean.getNkbSid());

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
     * <p>Select NTP_KTBUNRUI All Data
     * @return List in NTP_KTBUNRUIModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpKtbunruiModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpKtbunruiModel> ret = new ArrayList<NtpKtbunruiModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NKB_SID,");
            sql.addSql("   NKB_CODE,");
            sql.addSql("   NKB_NAME,");
            sql.addSql("   NKB_TIEUP_KBN,");
            sql.addSql("   NKB_AUID,");
            sql.addSql("   NKB_ADATE,");
            sql.addSql("   NKB_EUID,");
            sql.addSql("   NKB_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql(" order by ");
            sql.addSql("   NKB_CODE asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpKtbunruiFromRs(rs));
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
     * <p>Select NTP_KTBUNRUI
     * @param nkbSid NKB_SID
     * @return NTP_KTBUNRUIModel
     * @throws SQLException SQL実行例外
     */
    public NtpKtbunruiModel select(int nkbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpKtbunruiModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKB_SID,");
            sql.addSql("   NKB_CODE,");
            sql.addSql("   NKB_NAME,");
            sql.addSql("   NKB_TIEUP_KBN,");
            sql.addSql("   NKB_AUID,");
            sql.addSql("   NKB_ADATE,");
            sql.addSql("   NKB_EUID,");
            sql.addSql("   NKB_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql(" where ");
            sql.addSql("   NKB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nkbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpKtbunruiFromRs(rs);
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
     * <p>Select NTP_KTBUNRUI
     * @param nkbCode NKB_CODE
     * @return NTP_KTBUNRUIModel
     * @throws SQLException SQL実行例外
     */
    public NtpKtbunruiModel select(String nkbCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpKtbunruiModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKB_SID,");
            sql.addSql("   NKB_CODE,");
            sql.addSql("   NKB_NAME,");
            sql.addSql("   NKB_TIEUP_KBN,");
            sql.addSql("   NKB_AUID,");
            sql.addSql("   NKB_ADATE,");
            sql.addSql("   NKB_EUID,");
            sql.addSql("   NKB_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql(" where ");
            sql.addSql("   NKB_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(nkbCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpKtbunruiFromRs(rs);
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
     * <br>[機  能] 指定した活動分類コードに該当する活動分類情報が存在するかをチェックする
     * <br>[解  説] 指定した活動分類SID > 0 の場合、指定した活動分類SID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param nkbSid 活動分類SID
     * @param nkbCode 活動分類コード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existKtbunrui(int nkbSid, String nkbCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NKB_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql(" where ");
            sql.addSql("   NKB_CODE=?");
            sql.addStrValue(nkbCode);

            if (nkbSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NKB_SID != ?");
                sql.addIntValue(nkbSid);
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
     * <p>Delete NTP_KTBUNRUI
     * @param nkbSid NKB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nkbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql(" where ");
            sql.addSql("   NKB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nkbSid);

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
     * <p>Create NTP_KTBUNRUI Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpKtbunruiModel
     * @throws SQLException SQL実行例外
     */
    private NtpKtbunruiModel __getNtpKtbunruiFromRs(ResultSet rs) throws SQLException {
        NtpKtbunruiModel bean = new NtpKtbunruiModel();
        bean.setNkbSid(rs.getInt("NKB_SID"));
        bean.setNkbCode(rs.getString("NKB_CODE"));
        bean.setNkbName(rs.getString("NKB_NAME"));
        bean.setNkbTieupKbn(rs.getInt("NKB_TIEUP_KBN"));
        bean.setNkbAuid(rs.getInt("NKB_AUID"));
        bean.setNkbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKB_ADATE")));
        bean.setNkbEuid(rs.getInt("NKB_EUID"));
        bean.setNkbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKB_EDATE")));
        return bean;
    }
}
