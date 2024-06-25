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
import jp.groupsession.v2.ntp.model.NtpGyomuModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_GYOMU Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpGyomuDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpGyomuDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpGyomuDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpGyomuDao(Connection con) {
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
            sql.addSql("drop table NTP_GYOMU");

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
            sql.addSql(" create table NTP_GYOMU (");
            sql.addSql("   NGY_SID NUMBER(10,0) not null,");
            sql.addSql("   NGY_CODE varchar(5) not null,");
            sql.addSql("   NGY_NAME varchar(50) not null,");
            sql.addSql("   NGY_AUID NUMBER(10,0),");
            sql.addSql("   NGY_ADATE varchar(23),");
            sql.addSql("   NGY_EUID NUMBER(10,0),");
            sql.addSql("   NGY_EDATE varchar(23),");
            sql.addSql("   primary key (NGY_SID)");
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
     * <p>Insert NTP_GYOMU Data Bindding JavaBean
     * @param bean NTP_GYOMU Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpGyomuModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_GYOMU(");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGY_CODE,");
            sql.addSql("   NGY_NAME,");
            sql.addSql("   NGY_AUID,");
            sql.addSql("   NGY_ADATE,");
            sql.addSql("   NGY_EUID,");
            sql.addSql("   NGY_EDATE");
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
            sql.addIntValue(bean.getNgySid());
            sql.addStrValue(bean.getNgyCode());
            sql.addStrValue(bean.getNgyName());
            sql.addIntValue(bean.getNgyAuid());
            sql.addDateValue(bean.getNgyAdate());
            sql.addIntValue(bean.getNgyEuid());
            sql.addDateValue(bean.getNgyEdate());
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
     * <p>Update NTP_GYOMU Data Bindding JavaBean
     * @param bean NTP_GYOMU Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpGyomuModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" set ");
            sql.addSql("   NGY_CODE=?,");
            sql.addSql("   NGY_NAME=?,");
            sql.addSql("   NGY_EUID=?,");
            sql.addSql("   NGY_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NGY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNgyCode());
            sql.addStrValue(bean.getNgyName());
            sql.addIntValue(bean.getNgyEuid());
            sql.addDateValue(bean.getNgyEdate());
            //where
            sql.addIntValue(bean.getNgySid());

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
     * <p>Select NTP_GYOMU All Data
     * @return List in NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpGyomuModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpGyomuModel> ret = new ArrayList<NtpGyomuModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_GYOMU.NGY_SID AS NGY_SID, ");
            sql.addSql("   NTP_GYOMU.NGY_CODE AS NGY_CODE, ");
            sql.addSql("   NTP_GYOMU.NGY_NAME AS NGY_NAME, ");
            sql.addSql("   NTP_GYOMU.NGY_AUID AS NGY_AUID, ");
            sql.addSql("   NTP_GYOMU.NGY_ADATE AS NGY_ADATE, ");
            sql.addSql("   NTP_GYOMU.NGY_EUID AS NGY_EUID, ");
            sql.addSql("   NTP_GYOMU.NGY_EDATE AS NGY_EDATE, ");
            sql.addSql("   GYOMU_SORT.NGS_SORT AS NGS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_GYOMU");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NGY_SID, NGS_SORT");
            sql.addSql("      from NTP_GYOMU_SORT ");
            sql.addSql("     ) GYOMU_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_GYOMU.NGY_SID = GYOMU_SORT.NGY_SID ");
            sql.addSql(" order by ");
            sql.addSql("   GYOMU_SORT.NGS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpGyomuFromRs(rs));
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
     * <p>Select NTP_GYOMU
     * @param ngySid NGY_SID
     * @return NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public NtpGyomuModel select(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpGyomuModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGY_CODE,");
            sql.addSql("   NGY_NAME,");
            sql.addSql("   NGY_AUID,");
            sql.addSql("   NGY_ADATE,");
            sql.addSql("   NGY_EUID,");
            sql.addSql("   NGY_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" where ");
            sql.addSql("   NGY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpGyomuFromRs(rs);
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
     * <p>Select NTP_GYOMU
     * @param ngyCode NGY_CODE
     * @return NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public NtpGyomuModel selectCode(String ngyCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpGyomuModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NGY_SID,");
            sql.addSql("   NGY_CODE,");
            sql.addSql("   NGY_NAME,");
            sql.addSql("   NGY_AUID,");
            sql.addSql("   NGY_ADATE,");
            sql.addSql("   NGY_EUID,");
            sql.addSql("   NGY_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" where ");
            sql.addSql("   NGY_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(ngyCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpGyomuFromRs(rs);
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
     * <br>[機  能] 指定した業種コードに該当する業種情報が存在するかをチェックする
     * <br>[解  説] 指定した業種SID > 0 の場合、指定した業種SID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param ngySid 業種SID
     * @param ngyCode 業種コード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existGyomu(int ngySid, String ngyCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NGY_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" where ");
            sql.addSql("   NGY_CODE=?");
            sql.addStrValue(ngyCode);

            if (ngySid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NGY_SID != ?");
                sql.addIntValue(ngySid);
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
     * <p>Delete NTP_GYOMU
     * @param ngySid NGY_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_GYOMU");
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
     * <p>Create NTP_GYOMU Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpGyomuModel
     * @throws SQLException SQL実行例外
     */
    private NtpGyomuModel __getNtpGyomuFromRs(ResultSet rs) throws SQLException {
        NtpGyomuModel bean = new NtpGyomuModel();
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNgyCode(rs.getString("NGY_CODE"));
        bean.setNgyName(rs.getString("NGY_NAME"));
        bean.setNgyAuid(rs.getInt("NGY_AUID"));
        bean.setNgyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NGY_ADATE")));
        bean.setNgyEuid(rs.getInt("NGY_EUID"));
        bean.setNgyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NGY_EDATE")));
        return bean;
    }
}
