package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchSpaccessTargetModel;

/**
 * <p>SCH_SPACCESS_TARGET Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchSpaccessTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchSpaccessTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public SchSpaccessTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchSpaccessTargetDao(Connection con) {
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
            sql.addSql("drop table SCH_SPACCESS_TARGET");

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
            sql.addSql(" create table SCH_SPACCESS_TARGET (");
            sql.addSql("   SSA_SID NUMBER(10,0) not null,");
            sql.addSql("   SST_TYPE NUMBER(10,0) not null,");
            sql.addSql("   SST_TSID NUMBER(10,0) not null");
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
     * <p>Insert SCH_SPACCESS_TARGET Data Bindding JavaBean
     * @param bean SCH_SPACCESS_TARGET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchSpaccessTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_SPACCESS_TARGET(");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SST_TYPE,");
            sql.addSql("   SST_TSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSsaSid());
            sql.addIntValue(bean.getSstType());
            sql.addIntValue(bean.getSstTsid());
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
     * <p>Update SCH_SPACCESS_TARGET Data Bindding JavaBean
     * @param bean SCH_SPACCESS_TARGET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchSpaccessTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_SPACCESS_TARGET");
            sql.addSql(" set ");
            sql.addSql("   SSA_SID=?,");
            sql.addSql("   SST_TYPE=?,");
            sql.addSql("   SST_TSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSsaSid());
            sql.addIntValue(bean.getSstType());
            sql.addIntValue(bean.getSstTsid());

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
     * <p>Update SCH_SPACCESS_TARGET Data Bindding JavaBean
     * @param ssaSid SSA_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int ssaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   SCH_SPACCESS_TARGET");
            sql.addSql(" where ");
            sql.addSql("   SSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ssaSid);

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
     * <p>Select SCH_SPACCESS_TARGET All Data
     * @return List in SCH_SPACCESS_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<SchSpaccessTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchSpaccessTargetModel> ret = new ArrayList<SchSpaccessTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SST_TYPE,");
            sql.addSql("   SST_TSID");
            sql.addSql(" from ");
            sql.addSql("   SCH_SPACCESS_TARGET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchSpaccessTargetFromRs(rs));
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
     * <br>[機  能] 指定した特例アクセスの制限対象を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ssaSid 特例アクセスSID
     * @return 制限対象
     * @throws SQLException SQL実行時例外
     */
    public List<SchSpaccessTargetModel> getTargetList(int ssaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchSpaccessTargetModel> ret = new ArrayList<SchSpaccessTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SST_TYPE,");
            sql.addSql("   SST_TSID");
            sql.addSql(" from ");
            sql.addSql("   SCH_SPACCESS_TARGET");
            sql.addSql(" where ");
            sql.addSql("   SSA_SID = ?");
            sql.addIntValue(ssaSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchSpaccessTargetFromRs(rs));
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
     * <p>Create SCH_SPACCESS_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchSpaccessTargetModel
     * @throws SQLException SQL実行例外
     */
    private SchSpaccessTargetModel __getSchSpaccessTargetFromRs(ResultSet rs) throws SQLException {
        SchSpaccessTargetModel bean = new SchSpaccessTargetModel();
        bean.setSsaSid(rs.getInt("SSA_SID"));
        bean.setSstType(rs.getInt("SST_TYPE"));
        bean.setSstTsid(rs.getInt("SST_TSID"));
        return bean;
    }
}
