package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchColMsgModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SCH_COL_MSG Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchColMsgDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchColMsgDao.class);

    /**
     * <p>Default Constructor
     */
    public SchColMsgDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchColMsgDao(Connection con) {
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
            sql.addSql("drop table SCH_COL_MSG");

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
            sql.addSql(" create table SCH_COL_MSG (");
            sql.addSql("   SCM_ID NUMBER(10,0) not null,");
            sql.addSql("   SCM_MSG varchar(30),");
            sql.addSql("   SCM_AUID NUMBER(10,0) not null,");
            sql.addSql("   SCM_ADATE varchar(23) not null,");
            sql.addSql("   SCM_EUID NUMBER(10,0) not null,");
            sql.addSql("   SCM_EDATE varchar(23) not null");
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
     * <p>Insert SCH_COL_MSG Data Bindding JavaBean
     * @param bean SCH_COL_MSG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchColMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_COL_MSG(");
            sql.addSql("   SCM_ID,");
            sql.addSql("   SCM_MSG,");
            sql.addSql("   SCM_AUID,");
            sql.addSql("   SCM_ADATE,");
            sql.addSql("   SCM_EUID,");
            sql.addSql("   SCM_EDATE");
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
            sql.addIntValue(bean.getScmId());
            sql.addStrValue(bean.getScmMsg());
            sql.addIntValue(bean.getScmAuid());
            sql.addDateValue(bean.getScmAdate());
            sql.addIntValue(bean.getScmEuid());
            sql.addDateValue(bean.getScmEdate());
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
     * <p>Update SCH_COL_MSG Data Bindding JavaBean
     * @param bean SCH_COL_MSG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchColMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_COL_MSG");
            sql.addSql(" set ");
            sql.addSql("   SCM_ID=?,");
            sql.addSql("   SCM_MSG=?,");
            sql.addSql("   SCM_EUID=?,");
            sql.addSql("   SCM_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScmId());
            sql.addStrValue(bean.getScmMsg());
            sql.addIntValue(bean.getScmEuid());
            sql.addDateValue(bean.getScmEdate());

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
     * <p>Delete SCH_COL_MSG Data All
     * @return 更新件数
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
            sql.addSql("   SCH_COL_MSG");

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
     * <p>Select SCH_COL_MSG  Data
     * @param id コメントID
     * @return SchColMsgModel SCH_COL_MSGModel
     * @throws SQLException SQL実行例外
     */
    public SchColMsgModel select(int id) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchColMsgModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCM_ID,");
            sql.addSql("   SCM_MSG,");
            sql.addSql("   SCM_AUID,");
            sql.addSql("   SCM_ADATE,");
            sql.addSql("   SCM_EUID,");
            sql.addSql("   SCM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_COL_MSG");
            sql.addSql(" where ");
            sql.addSql("   SCM_ID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(id);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchColMsgFromRs(rs);
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
     * <p>Select SCH_COL_MSG All Data
     * @return List in SCH_COL_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SchColMsgModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchColMsgModel> ret = new ArrayList<SchColMsgModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCM_ID,");
            sql.addSql("   SCM_MSG,");
            sql.addSql("   SCM_AUID,");
            sql.addSql("   SCM_ADATE,");
            sql.addSql("   SCM_EUID,");
            sql.addSql("   SCM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_COL_MSG");
            sql.addSql(" order by ");
            sql.addSql("   SCM_ID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchColMsgFromRs(rs));
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
     * <p>Select SCH_COL_MSG All Msg
     * @return List in SCH_COL_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectMsg() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCM_ID,");
            sql.addSql("   SCM_MSG,");
            sql.addSql("   SCM_AUID,");
            sql.addSql("   SCM_ADATE,");
            sql.addSql("   SCM_EUID,");
            sql.addSql("   SCM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_COL_MSG");
            sql.addSql(" order by ");
            sql.addSql("   SCM_ID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(NullDefault.getString(rs.getString("SCM_MSG"), ""));
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
     * <p>Create SCH_COL_MSG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchColMsgModel
     * @throws SQLException SQL実行例外
     */
    private SchColMsgModel __getSchColMsgFromRs(ResultSet rs) throws SQLException {
        SchColMsgModel bean = new SchColMsgModel();
        bean.setScmId(rs.getInt("SCM_ID"));
        bean.setScmMsg(rs.getString("SCM_MSG"));
        bean.setScmAuid(rs.getInt("SCM_AUID"));
        bean.setScmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCM_ADATE")));
        bean.setScmEuid(rs.getInt("SCM_EUID"));
        bean.setScmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCM_EDATE")));
        return bean;
    }
}
