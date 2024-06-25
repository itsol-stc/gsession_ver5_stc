package jp.groupsession.v2.anp.dao;

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
import jp.groupsession.v2.anp.model.AnpAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ANP_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpAdmConfDao(Connection con) {
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
            sql.addSql("drop table ANP_ADM_CONF");

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
            sql.addSql(" create table ANP_ADM_CONF (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   GRP_SID integer not null,");
            sql.addSql("   APA_AUID integer not null,");
            sql.addSql("   APA_ADATE timestamp not null,");
            sql.addSql("   APA_EUID integer not null,");
            sql.addSql("   APA_EDATE timestamp not null,");
            sql.addSql("   primary key (USR_SID, GRP_SID)");
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
     * <p>Insert ANP_ADM_CONF Data Bindding JavaBean
     * @param bean ANP_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_ADM_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   APA_AUID,");
            sql.addSql("   APA_ADATE,");
            sql.addSql("   APA_EUID,");
            sql.addSql("   APA_EDATE");
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
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getApaAuid());
            sql.addDateValue(bean.getApaAdate());
            sql.addIntValue(bean.getApaEuid());
            sql.addDateValue(bean.getApaEdate());

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
     * <p>Update ANP_ADM_CONF Data Bindding JavaBean
     * @param bean ANP_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   APA_EUID=?,");
            sql.addSql("   APA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql("   and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getApaEuid());
            sql.addDateValue(bean.getApaEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select ANP_ADM_CONF All Data
     * @return List in ANP_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpAdmConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpAdmConfModel> ret = new ArrayList<AnpAdmConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   APA_AUID,");
            sql.addSql("   APA_ADATE,");
            sql.addSql("   APA_EUID,");
            sql.addSql("   APA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpAdmConfFromRs(rs));
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
     * <p>Select ANP_ADM_CONF
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @return ANP_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public AnpAdmConfModel select(int usrSid, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   APA_AUID,");
            sql.addSql("   APA_ADATE,");
            sql.addSql("   APA_EUID,");
            sql.addSql("   APA_EDATE");
            sql.addSql(" from");
            sql.addSql("   ANP_ADM_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql("   and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpAdmConfFromRs(rs);
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
     * <p>Delete ANP_ADM_CONF alldata
     * @throws SQLException SQL実行例外
     * @return 更新件数
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ANP_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete ANP_ADM_CONF
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @throws SQLException SQL実行例外
     * @return 更新件数
     */
    public int delete(int usrSid, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ANP_ADM_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql("   and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

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
     * <p>Select ANP_ADM_CONF
     * @param usrSid USR_SID
     * @return ANP_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public boolean existsData(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   ANP_ADM_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT") > 0;
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
     * <p>Create ANP_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private AnpAdmConfModel __getAnpAdmConfFromRs(ResultSet rs) throws SQLException {
        AnpAdmConfModel bean = new AnpAdmConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setApaAuid(rs.getInt("APA_AUID"));
        bean.setApaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APA_ADATE")));
        bean.setApaEuid(rs.getInt("APA_EUID"));
        bean.setApaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APA_EDATE")));
        return bean;
    }
}
