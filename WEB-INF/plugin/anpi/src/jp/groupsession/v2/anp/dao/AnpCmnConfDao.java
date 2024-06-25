package jp.groupsession.v2.anp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.model.AnpCmnConfModel;

/**
 * <p>ANP_CMN_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpCmnConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpCmnConfDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpCmnConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpCmnConfDao(Connection con) {
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
            sql.addSql("drop table ANP_CMN_CONF");

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
            sql.addSql(" create table ANP_CMN_CONF (");
            sql.addSql("   APC_URL_BASE varchar(600) not null,");
            sql.addSql("   APC_URL_KBN integer not null,");
            sql.addSql("   APC_ADDRESS varchar(768) not null,");
            sql.addSql("   APC_SEND_HOST varchar(300) not null,");
            sql.addSql("   APC_SEND_PORT integer not null,");
            sql.addSql("   APC_SEND_USER varchar(300),");
            sql.addSql("   APC_SEND_PASS varchar(300),");
            sql.addSql("   APC_SEND_SSL integer not null,");
            sql.addSql("   APC_SMTP_AUTH integer not null,");
            sql.addSql("   APC_AUID integer not null,");
            sql.addSql("   APC_ADATE timestamp not null,");
            sql.addSql("   APC_EUID integer not null,");
            sql.addSql("   APC_EDATE timestamp not null,");
            sql.addSql("   APC_AUTH_TYPE integer not null,");
            sql.addSql("   COT_SID integer not null");
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
     * <p>Insert ANP_CMN_CONF Data Bindding JavaBean
     * @param bean ANP_CMN_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpCmnConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_CMN_CONF(");
            sql.addSql("   APC_URL_BASE,");
            sql.addSql("   APC_URL_KBN,");
            sql.addSql("   APC_ADDRESS,");
            sql.addSql("   APC_SEND_HOST,");
            sql.addSql("   APC_SEND_PORT,");
            sql.addSql("   APC_SEND_USER,");
            sql.addSql("   APC_SEND_PASS,");
            sql.addSql("   APC_SEND_SSL,");
            sql.addSql("   APC_SMTP_AUTH,");
            sql.addSql("   APC_AUID,");
            sql.addSql("   APC_ADATE,");
            sql.addSql("   APC_EUID,");
            sql.addSql("   APC_EDATE,");
            sql.addSql("   APC_AUTH_TYPE,");
            sql.addSql("   COT_SID");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApcUrlBase());
            sql.addIntValue(bean.getApcUrlKbn());
            sql.addStrValue(bean.getApcAddress());
            sql.addStrValue(bean.getApcSendHost());
            sql.addIntValue(bean.getApcSendPort());
            sql.addStrValue(bean.getApcSendUser());
            sql.addStrValue(bean.getApcSendPass());
            sql.addIntValue(bean.getApcSendSsl());
            sql.addIntValue(bean.getApcSmtpAuth());
            sql.addIntValue(bean.getApcAuid());
            sql.addDateValue(bean.getApcAdate());
            sql.addIntValue(bean.getApcEuid());
            sql.addDateValue(bean.getApcEdate());
            sql.addIntValue(bean.getApcAuthType());
            sql.addIntValue(bean.getCotSid());
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
     * <p>Update ANP_CMN_CONF Data Bindding JavaBean
     * @param bean ANP_CMN_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpCmnConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_CMN_CONF");
            sql.addSql(" set ");
            sql.addSql("   APC_URL_BASE=?,");
            sql.addSql("   APC_URL_KBN=?,");
            sql.addSql("   APC_ADDRESS=?,");
            sql.addSql("   APC_SEND_HOST=?,");
            sql.addSql("   APC_SEND_PORT=?,");
            sql.addSql("   APC_SEND_USER=?,");
            sql.addSql("   APC_SEND_PASS=?,");
            sql.addSql("   APC_SEND_SSL=?,");
            sql.addSql("   APC_SMTP_AUTH=?,");
            sql.addSql("   APC_EUID=?,");
            sql.addSql("   APC_EDATE=?,");
            sql.addSql("   APC_AUTH_TYPE=?,");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApcUrlBase());
            sql.addIntValue(bean.getApcUrlKbn());
            sql.addStrValue(bean.getApcAddress());
            sql.addStrValue(bean.getApcSendHost());
            sql.addIntValue(bean.getApcSendPort());
            sql.addStrValue(bean.getApcSendUser());
            sql.addStrValue(bean.getApcSendPass());
            sql.addIntValue(bean.getApcSendSsl());
            sql.addIntValue(bean.getApcSmtpAuth());
            sql.addIntValue(bean.getApcEuid());
            sql.addDateValue(bean.getApcEdate());
            sql.addIntValue(bean.getApcAuthType());
            sql.addIntValue(bean.getCotSid());

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
     * <p>Select ANP_CMN_CONF All Data
     * @return List in ANP_CMN_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpCmnConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpCmnConfModel> ret = new ArrayList<AnpCmnConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APC_URL_BASE,");
            sql.addSql("   APC_URL_KBN,");
            sql.addSql("   APC_ADDRESS,");
            sql.addSql("   APC_SEND_HOST,");
            sql.addSql("   APC_SEND_PORT,");
            sql.addSql("   APC_SEND_USER,");
            sql.addSql("   APC_SEND_PASS,");
            sql.addSql("   APC_SEND_SSL,");
            sql.addSql("   APC_SMTP_AUTH,");
            sql.addSql("   APC_AUID,");
            sql.addSql("   APC_ADATE,");
            sql.addSql("   APC_EUID,");
            sql.addSql("   APC_EDATE,");
            sql.addSql("   APC_AUTH_TYPE,");
            sql.addSql("   COT_SID");
            sql.addSql(" from ");
            sql.addSql("   ANP_CMN_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpCmnConfFromRs(rs));
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
     * <p>Create ANP_CMN_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpCmnConfModel
     * @throws SQLException SQL実行例外
     */
    private AnpCmnConfModel __getAnpCmnConfFromRs(ResultSet rs) throws SQLException {
        AnpCmnConfModel bean = new AnpCmnConfModel();
        bean.setApcUrlBase(rs.getString("APC_URL_BASE"));
        bean.setApcUrlKbn(rs.getInt("APC_URL_KBN"));
        bean.setApcAddress(rs.getString("APC_ADDRESS"));
        bean.setApcSendHost(rs.getString("APC_SEND_HOST"));
        bean.setApcSendPort(rs.getInt("APC_SEND_PORT"));
        bean.setApcSendUser(rs.getString("APC_SEND_USER"));
        bean.setApcSendPass(rs.getString("APC_SEND_PASS"));
        bean.setApcSendSsl(rs.getInt("APC_SEND_SSL"));
        bean.setApcSmtpAuth(rs.getInt("APC_SMTP_AUTH"));
        bean.setApcAuid(rs.getInt("APC_AUID"));
        bean.setApcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APC_ADATE")));
        bean.setApcEuid(rs.getInt("APC_EUID"));
        bean.setApcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APC_EDATE")));
        bean.setApcAuthType(rs.getInt("APC_AUTH_TYPE"));
        bean.setCotSid(rs.getInt("COT_SID"));
        return bean;
    }
}
