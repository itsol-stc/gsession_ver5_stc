package jp.groupsession.v2.zsk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.zsk.model.ZaiFixUpdateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ZAI_FIX_UPDATE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiFixUpdateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZaiFixUpdateDao.class);

    /**
     * <p>Default Constructor
     */
    public ZaiFixUpdateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ZaiFixUpdateDao(Connection con) {
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
            sql.addSql("drop table ZAI_FIX_UPDATE");

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
            sql.addSql(" create table ZAI_FIX_UPDATE (");
            sql.addSql("   ZFU_UPDATE_KBN NUMBER(10,0) not null,");
            sql.addSql("   ZFU_FIX_UPDATE_TIME NUMBER(10,0) not null,");
            sql.addSql("   ZFU_STATUS NUMBER(10,0) not null,");
            sql.addSql("   ZFU_MSG varchar(10) not null,");
            sql.addSql("   ZFU_AUID NUMBER(10,0) not null,");
            sql.addSql("   ZFU_ADATE varchar(23) not null,");
            sql.addSql("   ZFU_EUID NUMBER(10,0) not null,");
            sql.addSql("   ZFU_EDATE varchar(23) not null");
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
     * <p>Insert ZAI_FIX_UPDATE Data Bindding JavaBean
     * @param bean ZAI_FIX_UPDATE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ZaiFixUpdateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_FIX_UPDATE(");
            sql.addSql("   ZFU_UPDATE_KBN,");
            sql.addSql("   ZFU_FIX_UPDATE_TIME,");
            sql.addSql("   ZFU_STATUS,");
            sql.addSql("   ZFU_MSG,");
            sql.addSql("   ZFU_AUID,");
            sql.addSql("   ZFU_ADATE,");
            sql.addSql("   ZFU_EUID,");
            sql.addSql("   ZFU_EDATE");
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
            sql.addIntValue(bean.getZfuUpdateKbn());
            sql.addIntValue(bean.getZfuFixUpdateTime());
            sql.addIntValue(bean.getZfuStatus());
            sql.addStrValue(bean.getZfuMsg());
            sql.addIntValue(bean.getZfuAuid());
            sql.addDateValue(bean.getZfuAdate());
            sql.addIntValue(bean.getZfuEuid());
            sql.addDateValue(bean.getZfuEdate());
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
     * <p>Update ZAI_FIX_UPDATE Data Bindding JavaBean
     * @param bean ZAI_FIX_UPDATE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ZaiFixUpdateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_FIX_UPDATE");
            sql.addSql(" set ");
            sql.addSql("   ZFU_UPDATE_KBN=?,");
            sql.addSql("   ZFU_FIX_UPDATE_TIME=?,");
            sql.addSql("   ZFU_STATUS=?,");
            sql.addSql("   ZFU_MSG=?,");
            sql.addSql("   ZFU_AUID=?,");
            sql.addSql("   ZFU_ADATE=?,");
            sql.addSql("   ZFU_EUID=?,");
            sql.addSql("   ZFU_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZfuUpdateKbn());
            sql.addIntValue(bean.getZfuFixUpdateTime());
            sql.addIntValue(bean.getZfuStatus());
            sql.addStrValue(bean.getZfuMsg());
            sql.addIntValue(bean.getZfuAuid());
            sql.addDateValue(bean.getZfuAdate());
            sql.addIntValue(bean.getZfuEuid());
            sql.addDateValue(bean.getZfuEdate());

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
     * <p>Select ZAI_FIX_UPDATE All Data
     * @return List in ZAI_FIX_UPDATEModel
     * @throws SQLException SQL実行例外
     */
    public ZaiFixUpdateModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiFixUpdateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZFU_UPDATE_KBN,");
            sql.addSql("   ZFU_FIX_UPDATE_TIME,");
            sql.addSql("   ZFU_STATUS,");
            sql.addSql("   ZFU_MSG,");
            sql.addSql("   ZFU_AUID,");
            sql.addSql("   ZFU_ADATE,");
            sql.addSql("   ZFU_EUID,");
            sql.addSql("   ZFU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_FIX_UPDATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getZaiFixUpdateFromRs(rs);
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
     * <p>Delete ZAI_FIX_UPDATE
     * @return count
     * @throws SQLException SQL実行例外
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
            sql.addSql("   ZAI_FIX_UPDATE");

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
     * <p>Create ZAI_GPRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiGpriConfModel
     * @throws SQLException SQL実行例外
     */
    private ZaiFixUpdateModel __getZaiFixUpdateFromRs(ResultSet rs) throws SQLException {
        ZaiFixUpdateModel bean = new ZaiFixUpdateModel();

        bean.setZfuUpdateKbn(rs.getInt("ZFU_UPDATE_KBN"));
        bean.setZfuFixUpdateTime(rs.getInt("ZFU_FIX_UPDATE_TIME"));
        bean.setZfuStatus(rs.getInt("ZFU_STATUS"));
        bean.setZfuMsg(rs.getString("ZFU_MSG"));
        bean.setZfuAuid(rs.getInt("ZFU_AUID"));
        bean.setZfuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZFU_ADATE")));
        bean.setZfuEuid(rs.getInt("ZFU_EUID"));
        bean.setZfuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZFU_EDATE")));
        return bean;
    }
}