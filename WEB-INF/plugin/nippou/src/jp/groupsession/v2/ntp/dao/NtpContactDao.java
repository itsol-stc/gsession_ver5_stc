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
import jp.groupsession.v2.ntp.model.NtpContactModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_CONTACT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpContactDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpContactDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpContactDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpContactDao(Connection con) {
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
            sql.addSql("drop table NTP_CONTACT");

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
            sql.addSql(" create table NTP_CONTACT (");
            sql.addSql("   NCN_SID NUMBER(10,0) not null,");
            sql.addSql("   NCN_CODE varchar(5) not null,");
            sql.addSql("   NCN_NAME varchar(50) not null,");
            sql.addSql("   NCN_AUID NUMBER(10,0),");
            sql.addSql("   NCN_ADATE varchar(23),");
            sql.addSql("   NCN_EUID NUMBER(10,0),");
            sql.addSql("   NCN_EDATE varchar(23),");
            sql.addSql("   primary key (NCN_SID)");
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
     * <p>Insert NTP_CONTACT Data Bindding JavaBean
     * @param bean NTP_CONTACT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpContactModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_CONTACT(");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NCN_CODE,");
            sql.addSql("   NCN_NAME,");
            sql.addSql("   NCN_AUID,");
            sql.addSql("   NCN_ADATE,");
            sql.addSql("   NCN_EUID,");
            sql.addSql("   NCN_EDATE");
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
            sql.addIntValue(bean.getNcnSid());
            sql.addStrValue(bean.getNcnCode());
            sql.addStrValue(bean.getNcnName());
            sql.addIntValue(bean.getNcnAuid());
            sql.addDateValue(bean.getNcnAdate());
            sql.addIntValue(bean.getNcnEuid());
            sql.addDateValue(bean.getNcnEdate());
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
     * <p>Update NTP_CONTACT Data Bindding JavaBean
     * @param bean NTP_CONTACT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpContactModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" set ");
            sql.addSql("   NCN_CODE=?,");
            sql.addSql("   NCN_NAME=?,");
            sql.addSql("   NCN_EUID=?,");
            sql.addSql("   NCN_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NCN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNcnCode());
            sql.addStrValue(bean.getNcnName());
            sql.addIntValue(bean.getNcnEuid());
            sql.addDateValue(bean.getNcnEdate());
            //where
            sql.addIntValue(bean.getNcnSid());

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
     * <p>Select NTP_CONTACT All Data
     * @return List in NTP_CONTACTModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpContactModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpContactModel> ret = new ArrayList<NtpContactModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NCN_CODE,");
            sql.addSql("   NCN_NAME,");
            sql.addSql("   NCN_AUID,");
            sql.addSql("   NCN_ADATE,");
            sql.addSql("   NCN_EUID,");
            sql.addSql("   NCN_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" order by ");
            sql.addSql("   NCN_CODE asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpContactFromRs(rs));
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
     * <p>Select NTP_CONTACT
     * @param ncnSid NCN_SID
     * @return NTP_CONTACTModel
     * @throws SQLException SQL実行例外
     */
    public NtpContactModel select(int ncnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpContactModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NCN_CODE,");
            sql.addSql("   NCN_NAME,");
            sql.addSql("   NCN_AUID,");
            sql.addSql("   NCN_ADATE,");
            sql.addSql("   NCN_EUID,");
            sql.addSql("   NCN_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   NCN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ncnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpContactFromRs(rs);
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
     * <p>Select NTP_CONTACT
     * @param ncnCode NCN_CODE
     * @return NTP_CONTACTModel
     * @throws SQLException SQL実行例外
     */
    public NtpContactModel selectCode(String ncnCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpContactModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NCN_CODE,");
            sql.addSql("   NCN_NAME,");
            sql.addSql("   NCN_AUID,");
            sql.addSql("   NCN_ADATE,");
            sql.addSql("   NCN_EUID,");
            sql.addSql("   NCN_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   NCN_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(ncnCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpContactFromRs(rs);
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
     * <br>[機  能] 指定したコンタクトコードに該当するコンタクト情報が存在するかをチェックする
     * <br>[解  説] 指定したコンタクトSID > 0 の場合、指定したコンタクトSID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param ncnSid コンタクトSID
     * @param ncnCode コンタクトコード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existContact(int ncnSid, String ncnCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NCN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   NCN_CODE=?");
            sql.addStrValue(ncnCode);

            if (ncnSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NCN_SID != ?");
                sql.addIntValue(ncnSid);
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
     * <p>Delete NTP_CONTACT
     * @param ncnSid NCN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int ncnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" where ");
            sql.addSql("   NCN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ncnSid);

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
     * <p>Create NTP_CONTACT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpContactModel
     * @throws SQLException SQL実行例外
     */
    private NtpContactModel __getNtpContactFromRs(ResultSet rs) throws SQLException {
        NtpContactModel bean = new NtpContactModel();
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNcnCode(rs.getString("NCN_CODE"));
        bean.setNcnName(rs.getString("NCN_NAME"));
        bean.setNcnAuid(rs.getInt("NCN_AUID"));
        bean.setNcnAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NCN_ADATE")));
        bean.setNcnEuid(rs.getInt("NCN_EUID"));
        bean.setNcnEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NCN_EDATE")));
        return bean;
    }
}
