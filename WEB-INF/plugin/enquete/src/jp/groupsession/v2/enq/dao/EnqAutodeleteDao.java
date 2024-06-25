package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.enq.model.EnqAutodeleteModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ENQ_AUTODELETE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAutodeleteDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqAutodeleteDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqAutodeleteDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqAutodeleteDao(Connection con) {
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
            sql.addSql("drop table ENQ_AUTODELETE");

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
            sql.addSql(" create table ENQ_AUTODELETE (");
            sql.addSql("   EAD_SEND_KBN integer not null,");
            sql.addSql("   EAD_SEND_YEAR integer not null,");
            sql.addSql("   EAD_SEND_MONTH integer not null,");
            sql.addSql("   EAD_DRAFT_KBN integer not null,");
            sql.addSql("   EAD_DRAFT_YEAR integer not null,");
            sql.addSql("   EAD_DRAFT_MONTH integer not null,");
            sql.addSql("   EAD_AUID integer not null,");
            sql.addSql("   EAD_ADATE timestamp not null,");
            sql.addSql("   EAD_EUID integer not null,");
            sql.addSql("   EAD_EDATE timestamp not null");
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
     * <p>Insert ENQ_AUTODELETE Data Bindding JavaBean
     * @param bean ENQ_AUTODELETE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqAutodeleteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_AUTODELETE(");
            sql.addSql("   EAD_SEND_KBN,");
            sql.addSql("   EAD_SEND_YEAR,");
            sql.addSql("   EAD_SEND_MONTH,");
            sql.addSql("   EAD_DRAFT_KBN,");
            sql.addSql("   EAD_DRAFT_YEAR,");
            sql.addSql("   EAD_DRAFT_MONTH,");
            sql.addSql("   EAD_AUID,");
            sql.addSql("   EAD_ADATE,");
            sql.addSql("   EAD_EUID,");
            sql.addSql("   EAD_EDATE");
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
            sql.addIntValue(bean.getEadSendKbn());
            sql.addIntValue(bean.getEadSendYear());
            sql.addIntValue(bean.getEadSendMonth());
            sql.addIntValue(bean.getEadDraftKbn());
            sql.addIntValue(bean.getEadDraftYear());
            sql.addIntValue(bean.getEadDraftMonth());
            sql.addIntValue(bean.getEadAuid());
            sql.addDateValue(bean.getEadAdate());
            sql.addIntValue(bean.getEadEuid());
            sql.addDateValue(bean.getEadEdate());
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
     * <p>Update ENQ_AUTODELETE Data Bindding JavaBean
     * @param bean ENQ_AUTODELETE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqAutodeleteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_AUTODELETE");
            sql.addSql(" set ");
            sql.addSql("   EAD_SEND_KBN=?,");
            sql.addSql("   EAD_SEND_YEAR=?,");
            sql.addSql("   EAD_SEND_MONTH=?,");
            sql.addSql("   EAD_DRAFT_KBN=?,");
            sql.addSql("   EAD_DRAFT_YEAR=?,");
            sql.addSql("   EAD_DRAFT_MONTH=?,");
            sql.addSql("   EAD_AUID=?,");
            sql.addSql("   EAD_ADATE=?,");
            sql.addSql("   EAD_EUID=?,");
            sql.addSql("   EAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEadSendKbn());
            sql.addIntValue(bean.getEadSendYear());
            sql.addIntValue(bean.getEadSendMonth());
            sql.addIntValue(bean.getEadDraftKbn());
            sql.addIntValue(bean.getEadDraftYear());
            sql.addIntValue(bean.getEadDraftMonth());
            sql.addIntValue(bean.getEadAuid());
            sql.addDateValue(bean.getEadAdate());
            sql.addIntValue(bean.getEadEuid());
            sql.addDateValue(bean.getEadEdate());

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
     * <p>Select ENQ_AUTODELETE All Data
     * @return List in ENQ_AUTODELETEModel
     * @throws SQLException SQL実行例外
     */
    public EnqAutodeleteModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqAutodeleteModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EAD_SEND_KBN,");
            sql.addSql("   EAD_SEND_YEAR,");
            sql.addSql("   EAD_SEND_MONTH,");
            sql.addSql("   EAD_DRAFT_KBN,");
            sql.addSql("   EAD_DRAFT_YEAR,");
            sql.addSql("   EAD_DRAFT_MONTH,");
            sql.addSql("   EAD_AUID,");
            sql.addSql("   EAD_ADATE,");
            sql.addSql("   EAD_EUID,");
            sql.addSql("   EAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_AUTODELETE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqAutodeleteFromRs(rs);
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
     * <p>Create ENQ_AUTODELETE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqAutodeleteModel
     * @throws SQLException SQL実行例外
     */
    private EnqAutodeleteModel __getEnqAutodeleteFromRs(ResultSet rs) throws SQLException {
        EnqAutodeleteModel bean = new EnqAutodeleteModel();
        bean.setEadSendKbn(rs.getInt("EAD_SEND_KBN"));
        bean.setEadSendYear(rs.getInt("EAD_SEND_YEAR"));
        bean.setEadSendMonth(rs.getInt("EAD_SEND_MONTH"));
        bean.setEadDraftKbn(rs.getInt("EAD_DRAFT_KBN"));
        bean.setEadDraftYear(rs.getInt("EAD_DRAFT_YEAR"));
        bean.setEadDraftMonth(rs.getInt("EAD_DRAFT_MONTH"));
        bean.setEadAuid(rs.getInt("EAD_AUID"));
        bean.setEadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EAD_ADATE")));
        bean.setEadEuid(rs.getInt("EAD_EUID"));
        bean.setEadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EAD_EDATE")));
        return bean;
    }
}
