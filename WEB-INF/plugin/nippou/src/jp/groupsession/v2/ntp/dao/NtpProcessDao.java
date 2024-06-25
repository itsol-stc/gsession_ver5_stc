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
import jp.groupsession.v2.ntp.model.NtpProcessModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_PROCESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpProcessDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpProcessDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpProcessDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpProcessDao(Connection con) {
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
            sql.addSql("drop table NTP_PROCESS");

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
            sql.addSql(" create table NTP_PROCESS (");
            sql.addSql("   NGP_SID NUMBER(10,0) not null,");
            sql.addSql("   NGY_SID NUMBER(10,0) not null,");
            sql.addSql("   NGP_CODE varchar(8) not null,");
            sql.addSql("   NGP_NAME varchar(50) not null,");
            sql.addSql("   NGP_ACCOUNT varchar(1000),");
            sql.addSql("   NGP_SORT NUMBER(10,0),");
            sql.addSql("   NGP_AUID NUMBER(10,0),");
            sql.addSql("   NGP_ADATE varchar(23),");
            sql.addSql("   NGP_EUID NUMBER(10,0),");
            sql.addSql("   NGP_EDATE varchar(23),");
            sql.addSql("   primary key (NGP_SID)");
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
     * <p>Insert NTP_PROCESS Data Bindding JavaBean
     * @param bean NTP_PROCESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpProcessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_PROCESS(");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGP_CODE,");
            sql.addSql("   NGP_NAME,");
            sql.addSql("   NGP_ACCOUNT,");
            sql.addSql("   NGP_SORT,");
            sql.addSql("   NGP_AUID,");
            sql.addSql("   NGP_ADATE,");
            sql.addSql("   NGP_EUID,");
            sql.addSql("   NGP_EDATE");
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
            sql.addIntValue(bean.getNgpSid());
            sql.addIntValue(bean.getNgySid());
            sql.addStrValue(bean.getNgpCode());
            sql.addStrValue(bean.getNgpName());
            sql.addStrValue(bean.getNgpAccount());
            sql.addIntValue(bean.getNgpSort());
            sql.addIntValue(bean.getNgpAuid());
            sql.addDateValue(bean.getNgpAdate());
            sql.addIntValue(bean.getNgpEuid());
            sql.addDateValue(bean.getNgpEdate());
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
     * <p>Update NTP_PROCESS Data Bindding JavaBean
     * @param bean NTP_PROCESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpProcessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" set ");
            sql.addSql("   NGY_SID=?,");
            sql.addSql("   NGP_CODE=?,");
            sql.addSql("   NGP_NAME=?,");
            sql.addSql("   NGP_ACCOUNT=?,");
            sql.addSql("   NGP_SORT=?,");
            sql.addSql("   NGP_EUID=?,");
            sql.addSql("   NGP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NGP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNgySid());
            sql.addStrValue(bean.getNgpCode());
            sql.addStrValue(bean.getNgpName());
            sql.addStrValue(bean.getNgpAccount());
            sql.addIntValue(bean.getNgpSort());
            sql.addIntValue(bean.getNgpEuid());
            sql.addDateValue(bean.getNgpEdate());
            //where
            sql.addIntValue(bean.getNgpSid());

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
     * <p>Update NTP_PROCESS Data Bindding JavaBean
     * @param bean NTP_PROCESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSort(NtpProcessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" set ");
            sql.addSql("   NGP_SORT=?,");
            sql.addSql("   NGP_EUID=?,");
            sql.addSql("   NGP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NGP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNgpSort());
            sql.addIntValue(bean.getNgpEuid());
            sql.addDateValue(bean.getNgpEdate());
            //where
            sql.addIntValue(bean.getNgpSid());

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
     * <p>Select NTP_PROCESS All Data
     * @return List in NTP_PROCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpProcessModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpProcessModel> ret = new ArrayList<NtpProcessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGP_CODE,");
            sql.addSql("   NGP_NAME,");
            sql.addSql("   NGP_ACCOUNT,");
            sql.addSql("   NGP_SORT,");
            sql.addSql("   NGP_AUID,");
            sql.addSql("   NGP_ADATE,");
            sql.addSql("   NGP_EUID,");
            sql.addSql("   NGP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_PROCESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpProcessFromRs(rs));
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
     * <p>Select NTP_PROCESS
     * @param ngpSid NGP_SID
     * @return NTP_PROCESSModel
     * @throws SQLException SQL実行例外
     */
    public NtpProcessModel select(int ngpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpProcessModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGP_CODE,");
            sql.addSql("   NGP_NAME,");
            sql.addSql("   NGP_ACCOUNT,");
            sql.addSql("   NGP_SORT,");
            sql.addSql("   NGP_AUID,");
            sql.addSql("   NGP_ADATE,");
            sql.addSql("   NGP_EUID,");
            sql.addSql("   NGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpProcessFromRs(rs);
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
     * <br>[機  能] 指定したプロセスコードに該当するプロセス情報が存在するかをチェックする
     * <br>[解  説] 指定したプロセスSID > 0 の場合、指定したプロセスSID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param ngySid 業種SID
     * @param ngpSid プロセスSID
     * @param ngpCode プロセスコード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existProcess(int ngySid, int ngpSid, String ngpCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NGP_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGP_CODE=?");
            sql.addStrValue(ngpCode);

            if (ngySid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NGY_SID = ?");
                sql.addIntValue(ngySid);
            }
            if (ngpSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NGP_SID != ?");
                sql.addIntValue(ngpSid);
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
     * <br>[機  能] 指定したプロセスコードに該当するプロセス情報が存在するかをチェックする
     * <br>[解  説] 指定したプロセスSID > 0 の場合、指定したプロセスSID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param ngyCode 業種コード
     * @param ngpCode プロセスコード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existProcess(String ngyCode, String ngpCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NGP_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGP_CODE=?");
            sql.addStrValue(ngpCode);

            sql.addSql(" and ");
            sql.addSql("   NGY_SID = ");
            sql.addSql(" (select NGY_SID from NTP_GYOMU where NGY_CODE = '" + ngyCode + "' )");
//            sql.addStrValue(ngyCode);

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
     * <p>Select NTP_PROCESS
     * @param ngySid NGY_SID
     * @return NTP_PROCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpProcessModel> getGyomuProcess(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpProcessModel> ret = new ArrayList<NtpProcessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGP_CODE,");
            sql.addSql("   NGP_NAME,");
            sql.addSql("   NGP_ACCOUNT,");
            sql.addSql("   NGP_SORT,");
            sql.addSql("   NGP_AUID,");
            sql.addSql("   NGP_ADATE,");
            sql.addSql("   NGP_EUID,");
            sql.addSql("   NGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpProcessFromRs(rs));
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
     * <p>Select NTP_PROCESS
     * @param ngpCode NGP_CODE
     * @return NTP_PROCESSModel
     * @throws SQLException SQL実行例外
     */
    public NtpProcessModel selectCode(String ngpCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpProcessModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGP_CODE,");
            sql.addSql("   NGP_NAME,");
            sql.addSql("   NGP_ACCOUNT,");
            sql.addSql("   NGP_SORT,");
            sql.addSql("   NGP_AUID,");
            sql.addSql("   NGP_ADATE,");
            sql.addSql("   NGP_EUID,");
            sql.addSql("   NGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGP_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(ngpCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpProcessFromRs(rs);
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
     * <p>Delete NTP_PROCESS
     * @param ngpSid NGP_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int ngpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngpSid);

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
     * <p>業務SIDを指定してプロセスを削除する
     * @param ngySid NGP_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete_ngy(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NGY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngySid);

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
     * <p>Create NTP_PROCESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpProcessModel
     * @throws SQLException SQL実行例外
     */
    private NtpProcessModel __getNtpProcessFromRs(ResultSet rs) throws SQLException {
        NtpProcessModel bean = new NtpProcessModel();
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNgpCode(rs.getString("NGP_CODE"));
        bean.setNgpName(rs.getString("NGP_NAME"));
        bean.setNgpAccount(rs.getString("NGP_ACCOUNT"));
        bean.setNgpSort(rs.getInt("NGP_SORT"));
        bean.setNgpAuid(rs.getInt("NGP_AUID"));
        bean.setNgpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NGP_ADATE")));
        bean.setNgpEuid(rs.getInt("NGP_EUID"));
        bean.setNgpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NGP_EDATE")));
        return bean;
    }
}
