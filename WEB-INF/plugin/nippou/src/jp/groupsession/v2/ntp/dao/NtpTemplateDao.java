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
import jp.groupsession.v2.ntp.model.NtpTemplateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_TEMPLATE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpTemplateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpTemplateDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpTemplateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpTemplateDao(Connection con) {
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
            sql.addSql("drop table NTP_TEMPLATE");

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

    /**
     * <p>Insert NTP_TEMPLATE Data Bindding JavaBean
     * @param bean NTP_TEMPLATE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_TEMPLATE(");
            sql.addSql("   NTT_SID,");
            sql.addSql("   NTT_NAME,");
            sql.addSql("   NTT_ANKEN,");
            sql.addSql("   NTT_COMP,");
            sql.addSql("   NTT_KATUDO,");
            sql.addSql("   NTT_MIKOMI,");
            sql.addSql("   NTT_TEMP,");
            sql.addSql("   NTT_ACTION,");
            sql.addSql("   NTT_DETAIL,");
            sql.addSql("   NTT_AUID,");
            sql.addSql("   NTT_ADATE,");
            sql.addSql("   NTT_EUID,");
            sql.addSql("   NTT_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNttSid());
            sql.addStrValue(bean.getNttName());
            sql.addIntValue(bean.getNttAnken());
            sql.addIntValue(bean.getNttComp());
            sql.addIntValue(bean.getNttKatudo());
            sql.addIntValue(bean.getNttMikomi());
            sql.addIntValue(bean.getNttTemp());
            sql.addIntValue(bean.getNttAction());
            sql.addStrValue(bean.getNttDetail());
            sql.addIntValue(bean.getNttAuid());
            sql.addDateValue(bean.getNttAdate());
            sql.addIntValue(bean.getNttEuid());
            sql.addDateValue(bean.getNttEdate());
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
     * <p>Update NTP_TEMPLATE Data Bindding JavaBean
     * @param bean NTP_TEMPLATE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   NTT_NAME=?,");
            sql.addSql("   NTT_ANKEN=?,");
            sql.addSql("   NTT_COMP=?,");
            sql.addSql("   NTT_KATUDO=?,");
            sql.addSql("   NTT_MIKOMI=?,");
            sql.addSql("   NTT_TEMP=?,");
            sql.addSql("   NTT_ACTION=?,");
            sql.addSql("   NTT_DETAIL=?,");
            sql.addSql("   NTT_EUID=?,");
            sql.addSql("   NTT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNttName());
            sql.addIntValue(bean.getNttAnken());
            sql.addIntValue(bean.getNttComp());
            sql.addIntValue(bean.getNttKatudo());
            sql.addIntValue(bean.getNttMikomi());
            sql.addIntValue(bean.getNttTemp());
            sql.addIntValue(bean.getNttAction());
            sql.addStrValue(bean.getNttDetail());
            sql.addIntValue(bean.getNttEuid());
            sql.addDateValue(bean.getNttEdate());
            //where
            sql.addIntValue(bean.getNttSid());

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
     * <p>Select NTP_TEMPLATE All Data
     * @return List in NTP_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpTemplateModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpTemplateModel> ret = new ArrayList<NtpTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTT_SID,");
            sql.addSql("   NTT_NAME,");
            sql.addSql("   NTT_ANKEN,");
            sql.addSql("   NTT_COMP,");
            sql.addSql("   NTT_KATUDO,");
            sql.addSql("   NTT_MIKOMI,");
            sql.addSql("   NTT_TEMP,");
            sql.addSql("   NTT_ACTION,");
            sql.addSql("   NTT_DETAIL,");
            sql.addSql("   NTT_AUID,");
            sql.addSql("   NTT_ADATE,");
            sql.addSql("   NTT_EUID,");
            sql.addSql("   NTT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_TEMPLATE");
            sql.addSql(" order by ");
            sql.addSql("   NTT_NAME asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNttTemplateFromRs(rs));
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
     * <p>Select NTP_TEMPLATE
     * @param nttSid NTT_SID
     * @return NTP_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public NtpTemplateModel select(int nttSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTT_SID,");
            sql.addSql("   NTT_NAME,");
            sql.addSql("   NTT_ANKEN,");
            sql.addSql("   NTT_COMP,");
            sql.addSql("   NTT_KATUDO,");
            sql.addSql("   NTT_MIKOMI,");
            sql.addSql("   NTT_TEMP,");
            sql.addSql("   NTT_ACTION,");
            sql.addSql("   NTT_DETAIL,");
            sql.addSql("   NTT_AUID,");
            sql.addSql("   NTT_ADATE,");
            sql.addSql("   NTT_EUID,");
            sql.addSql("   NTT_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNttTemplateFromRs(rs);
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
     * <p>Delete NTP_TEMPLATE
     * @param nttSid NTT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nttSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);

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
     * <p>Create NTP_TEMPLATE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpTemplateModel
     * @throws SQLException SQL実行例外
     */
    private NtpTemplateModel __getNttTemplateFromRs(ResultSet rs) throws SQLException {
        NtpTemplateModel bean = new NtpTemplateModel();
        bean.setNttSid(rs.getInt("NTT_SID"));
        bean.setNttName(rs.getString("NTT_NAME"));
        bean.setNttAnken(rs.getInt("NTT_ANKEN"));
        bean.setNttComp(rs.getInt("NTT_COMP"));
        bean.setNttKatudo(rs.getInt("NTT_KATUDO"));
        bean.setNttMikomi(rs.getInt("NTT_MIKOMI"));
        bean.setNttTemp(rs.getInt("NTT_TEMP"));
        bean.setNttAction(rs.getInt("NTT_ACTION"));
        bean.setNttDetail(rs.getString("NTT_DETAIL"));
        bean.setNttAuid(rs.getInt("NTT_AUID"));
        bean.setNttAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTT_ADATE")));
        bean.setNttEuid(rs.getInt("NTT_EUID"));
        bean.setNttEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTT_EDATE")));
        return bean;
    }
}