package jp.groupsession.v2.api.dao;

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
import jp.groupsession.v2.api.model.ApiConfModel;
import jp.groupsession.v2.cmn.dao.base.IApiConfDao;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;

/**
 * <p>API_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiConfDao extends AbstractDao implements IApiConfDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiConfDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiConfDao(Connection con) {
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
            sql.addSql("drop table API_CONF");

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
            sql.addSql(" create table API_CONF (");
            sql.addSql("   APC_TOAKEN_USE integer not null,");
            sql.addSql("   APC_TOAKEN_IP varchar(256),");
            sql.addSql("   APC_TOAKEN_LIFE integer not null,");
            sql.addSql("   APC_BASIC_USE integer not null,");
            sql.addSql("   APC_BASIC_IP varchar(256)");
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

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.dao.IApiConfDao
     * #insert(jp.groupsession.v2.cmn.model.base.IApiConfModel)
     */
    @Override
    public void insert(IApiConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" API_CONF(");
            sql.addSql("   APC_TOAKEN_USE,");
            sql.addSql("   APC_TOAKEN_IP,");
            sql.addSql("   APC_TOAKEN_LIFE,");
            sql.addSql("   APC_BASIC_USE,");
            sql.addSql("   APC_BASIC_IP,");
            sql.addSql("   APC_AUTO_DEL");
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
            sql.addIntValue(bean.getApcToakenUse());
            sql.addStrValue(bean.getApcToakenIp());
            sql.addIntValue(bean.getApcToakenLife());
            sql.addIntValue(bean.getApcBasicUse());
            sql.addStrValue(bean.getApcBasicIp());
            sql.addIntValue(bean.getApcAutoDel());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.dao.IApiConfDao
     * #update(jp.groupsession.v2.cmn.model.base.IApiConfModel)
     */
    @Override
    public int update(IApiConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   API_CONF");
            sql.addSql(" set ");
            sql.addSql("   APC_TOAKEN_USE=?,");
            sql.addSql("   APC_TOAKEN_IP=?,");
            sql.addSql("   APC_TOAKEN_LIFE=?,");
            sql.addSql("   APC_BASIC_USE=?,");
            sql.addSql("   APC_BASIC_IP=?,");
            sql.addSql("   APC_AUTO_DEL=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getApcToakenUse());
            sql.addStrValue(bean.getApcToakenIp());
            sql.addIntValue(bean.getApcToakenLife());
            sql.addIntValue(bean.getApcBasicUse());
            sql.addStrValue(bean.getApcBasicIp());
            sql.addIntValue(bean.getApcAutoDel());

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

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.dao.IApiConfDao#select()
     */
    @Override
    public List<IApiConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IApiConfModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APC_TOAKEN_USE,");
            sql.addSql("   APC_TOAKEN_IP,");
            sql.addSql("   APC_TOAKEN_LIFE,");
            sql.addSql("   APC_BASIC_USE,");
            sql.addSql("   APC_BASIC_IP,");
            sql.addSql("   APC_AUTO_DEL");
            sql.addSql(" from ");
            sql.addSql("   API_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getApiConfFromRs(rs));
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
     * <p>Create API_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ApiConfModel
     * @throws SQLException SQL実行例外
     */
    private ApiConfModel __getApiConfFromRs(ResultSet rs) throws SQLException {
        ApiConfModel bean = new ApiConfModel();
        bean.setApcToakenUse(rs.getInt("APC_TOAKEN_USE"));
        bean.setApcToakenIp(rs.getString("APC_TOAKEN_IP"));
        bean.setApcToakenLife(rs.getInt("APC_TOAKEN_LIFE"));
        bean.setApcBasicUse(rs.getInt("APC_BASIC_USE"));
        bean.setApcBasicIp(rs.getString("APC_BASIC_IP"));
        bean.setApcAutoDel(rs.getInt("APC_AUTO_DEL"));
        return bean;
    }
}
