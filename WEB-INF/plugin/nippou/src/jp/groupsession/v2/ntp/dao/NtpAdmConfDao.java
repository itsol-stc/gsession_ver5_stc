package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpAdmConfDao(Connection con) {
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
            sql.addSql("drop table NTP_ADM_CONF");

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
            sql.addSql(" create table NTP_ADM_CONF (");
            sql.addSql("   NAC_CRANGE NUMBER(10,0) not null,");
            sql.addSql("   NAC_ATDEL_FLG NUMBER(10,0) not null,");
            sql.addSql("   NAC_ATDEL_Y NUMBER(10,0),");
            sql.addSql("   NAC_ATDEL_M NUMBER(10,0),");
            sql.addSql("   NAC_HOUR_DIV NUMBER(10,0) not null,");
            sql.addSql("   NAC_SML_KBN(10,0) not null,");
            sql.addSql("   NAC_SML_NOTICE_KBN(10,0) not null,");
            sql.addSql("   NAC_HOUR_DIV NUMBER(10,0) not null,");
            sql.addSql("   NAC_SML_NOTICE_GRP(10,0) not null,");
            sql.addSql("   NAC_CSML_KBN(10,0) not null,");
            sql.addSql("   NAC_CSML_NOTICE_KBN(10,0) not null,");
            sql.addSql("   NAC_GSML_KBN(10,0) not null,");
            sql.addSql("   NAC_GSML_NOTICE_KBN(10,0) not null,");
            sql.addSql("   NAC_ADATE varchar(23) not null,");
            sql.addSql("   NAC_EUID NUMBER(10,0) not null,");
            sql.addSql("   NAC_EDATE varchar(23) not null");
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
     * <p>Insert NTP_ADM_CONF Data Bindding JavaBean
     * @param bean NTP_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_ADM_CONF(");
            sql.addSql("   NAC_CRANGE,");
            sql.addSql("   NAC_ATDEL_FLG,");
            sql.addSql("   NAC_ATDEL_Y,");
            sql.addSql("   NAC_ATDEL_M,");
            sql.addSql("   NAC_HOUR_DIV,");
            sql.addSql("   NAC_SML_KBN,");
            sql.addSql("   NAC_SML_NOTICE_KBN,");
            sql.addSql("   NAC_SML_NOTICE_GRP,");
            sql.addSql("   NAC_CSML_KBN,");
            sql.addSql("   NAC_CSML_NOTICE_KBN,");
            sql.addSql("   NAC_GSML_KBN,");
            sql.addSql("   NAC_GSML_NOTICE_KBN,");
            sql.addSql("   NAC_AUID,");
            sql.addSql("   NAC_ADATE,");
            sql.addSql("   NAC_EUID,");
            sql.addSql("   NAC_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNacCrange());
            sql.addIntValue(bean.getNacAtdelFlg());
            sql.addIntValue(bean.getNacAtdelY());
            sql.addIntValue(bean.getNacAtdelM());
            sql.addIntValue(bean.getNacHourDiv());
            sql.addIntValue(bean.getNacSmlKbn());
            sql.addIntValue(bean.getNacSmlNoticeKbn());
            sql.addIntValue(bean.getNacSmlNoticeGrp());
            sql.addIntValue(bean.getNacCsmlKbn());
            sql.addIntValue(bean.getNacCsmlNoticeKbn());
            sql.addIntValue(bean.getNacGsmlKbn());
            sql.addIntValue(bean.getNacGsmlNoticeKbn());
            sql.addIntValue(bean.getNacAuid());
            sql.addDateValue(bean.getNacAdate());
            sql.addIntValue(bean.getNacEuid());
            sql.addDateValue(bean.getNacEdate());
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
     * <p>Update NTP_ADM_CONF Data Bindding JavaBean
     * @param bean NTP_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   NAC_CRANGE=?,");
            sql.addSql("   NAC_ATDEL_FLG=?,");
            sql.addSql("   NAC_ATDEL_Y=?,");
            sql.addSql("   NAC_ATDEL_M=?,");
            sql.addSql("   NAC_HOUR_DIV=?,");
            sql.addSql("   NAC_SML_KBN=?,");
            sql.addSql("   NAC_SML_NOTICE_KBN=?,");
            sql.addSql("   NAC_SML_NOTICE_GRP=?,");
            sql.addSql("   NAC_CSML_KBN=?,");
            sql.addSql("   NAC_CSML_NOTICE_KBN=?,");
            sql.addSql("   NAC_GSML_KBN=?,");
            sql.addSql("   NAC_GSML_NOTICE_KBN=?,");
            sql.addSql("   NAC_AUID=?,");
            sql.addSql("   NAC_ADATE=?,");
            sql.addSql("   NAC_EUID=?,");
            sql.addSql("   NAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNacCrange());
            sql.addIntValue(bean.getNacAtdelFlg());
            sql.addIntValue(bean.getNacAtdelY());
            sql.addIntValue(bean.getNacAtdelM());
            sql.addIntValue(bean.getNacHourDiv());
            sql.addIntValue(bean.getNacSmlKbn());
            sql.addIntValue(bean.getNacSmlNoticeKbn());
            sql.addIntValue(bean.getNacSmlNoticeGrp());
            sql.addIntValue(bean.getNacCsmlKbn());
            sql.addIntValue(bean.getNacCsmlNoticeKbn());
            sql.addIntValue(bean.getNacGsmlKbn());
            sql.addIntValue(bean.getNacGsmlNoticeKbn());
            sql.addIntValue(bean.getNacAuid());
            sql.addDateValue(bean.getNacAdate());
            sql.addIntValue(bean.getNacEuid());
            sql.addDateValue(bean.getNacEdate());

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
     * <p>自動削除設定をアップデートする
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAutoDelete(NtpAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   NAC_ATDEL_FLG=?,");
            sql.addSql("   NAC_ATDEL_Y=?,");
            sql.addSql("   NAC_ATDEL_M=?,");
            sql.addSql("   NAC_EUID=?,");
            sql.addSql("   NAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNacAtdelFlg());
            sql.addIntValue(bean.getNacAtdelY());
            sql.addIntValue(bean.getNacAtdelM());
            sql.addIntValue(bean.getNacEuid());
            sql.addDateValue(bean.getNacEdate());

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
     * <p>Select NTP_ADM_CONF All Data
     * @return List in NTP_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public NtpAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAC_CRANGE,");
            sql.addSql("   NAC_ATDEL_FLG,");
            sql.addSql("   NAC_ATDEL_Y,");
            sql.addSql("   NAC_ATDEL_M,");
            sql.addSql("   NAC_HOUR_DIV,");
            sql.addSql("   NAC_SML_KBN,");
            sql.addSql("   NAC_SML_NOTICE_KBN,");
            sql.addSql("   NAC_SML_NOTICE_GRP,");
            sql.addSql("   NAC_CSML_KBN,");
            sql.addSql("   NAC_CSML_NOTICE_KBN,");
            sql.addSql("   NAC_GSML_KBN,");
            sql.addSql("   NAC_GSML_NOTICE_KBN,");
            sql.addSql("   NAC_AUID,");
            sql.addSql("   NAC_ADATE,");
            sql.addSql("   NAC_EUID,");
            sql.addSql("   NAC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getNtpAdminConfiFromRs(rs);
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
     * <p>Create NTP_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private NtpAdmConfModel __getNtpAdminConfiFromRs(ResultSet rs) throws SQLException {
        NtpAdmConfModel bean = new NtpAdmConfModel();
        bean.setNacCrange(rs.getInt("NAC_CRANGE"));
        bean.setNacAtdelFlg(rs.getInt("NAC_ATDEL_FLG"));
        bean.setNacAtdelY(rs.getInt("NAC_ATDEL_Y"));
        bean.setNacAtdelM(rs.getInt("NAC_ATDEL_M"));
        bean.setNacHourDiv(rs.getInt("NAC_HOUR_DIV"));
        bean.setNacSmlKbn(rs.getInt("NAC_SML_KBN"));
        bean.setNacSmlNoticeKbn(rs.getInt("NAC_SML_NOTICE_KBN"));
        bean.setNacSmlNoticeGrp(rs.getInt("NAC_SML_NOTICE_GRP"));
        bean.setNacCsmlKbn(rs.getInt("NAC_CSML_KBN"));
        bean.setNacCsmlNoticeKbn(rs.getInt("NAC_CSML_NOTICE_KBN"));
        bean.setNacGsmlKbn(rs.getInt("NAC_GSML_KBN"));
        bean.setNacGsmlNoticeKbn(rs.getInt("NAC_GSML_NOTICE_KBN"));
        bean.setNacAuid(rs.getInt("NAC_AUID"));
        bean.setNacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAC_ADATE")));
        bean.setNacEuid(rs.getInt("NAC_EUID"));
        bean.setNacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAC_EDATE")));
        return bean;
    }
}
