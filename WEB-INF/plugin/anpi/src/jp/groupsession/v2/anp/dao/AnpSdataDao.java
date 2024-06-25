package jp.groupsession.v2.anp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.model.AnpSdataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ANP_SDATA Data Access Object
 *
 * @author JTS
 */
public class AnpSdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpSdataDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpSdataDao() {

    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpSdataDao(Connection con) {
        super(con);
    }

    /**
     *
     * <p>Drop Table
     *@throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table ANP_SDATA");

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
     *@throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   create table ANP_SDATA (");
            sql.addSql("     APH_SID integer not null,");
            sql.addSql("     APS_TYPE integer not null,");
            sql.addSql("     USR_SID integer not null,");
            sql.addSql("     GRP_SID integer not null,");
            sql.addSql("     primary key (APH_SID, APS_TYPE, USR_SID, GRP_SID)");
            sql.addSql("   );");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <p>Insert ANP_SDATA Data Bindding JavaBean
     * @param bean ANP_SDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpSdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_SDATA(");
            sql.addSql("   APH_SID,");
            sql.addSql("   APS_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getApsType());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>Update ANP_SDATA Data Bindding JavaBean
     * @param bean ANP_SDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpSdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_SDATA");
            sql.addSql(" set ");
            sql.addSql("   USR_SID = ? ,");
            sql.addSql("   GRP_SID = ?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID = ?");
            sql.addSql("   and");
            sql.addSql("   APS_TYPE = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            //where
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getApsType());

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
     * <p> 指定した安否SIDの件数を返す
     * @param aphSid 安否SID
     * @return count 件数
     * @throws SQLException SQL実行例外
     */
    public int count(int aphSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   ANP_SDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addIntValue(aphSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p> Select ANP_SDATA All Data
     * @return List in ANP_SDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpSdataModel> select() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpSdataModel> ret = new ArrayList<AnpSdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   APS_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   ANP_SDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpSdataFromRs(rs));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p> Select ANP_SDATA All Data
     * @param aphSid APH_SID
     * @return List in ANP_SDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpSdataModel> select(int aphSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpSdataModel> ret = new ArrayList<AnpSdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   APS_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   ANP_SDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpSdataFromRs(rs));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete ANP_SDATA
     * @param aphSid APH_SID
     * @throws SQLException SQL実行例外
     * @return 更新件数
     */
    public int delete(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ANP_SDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

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
     * <p>Create ANP_SDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpSdataModel
     * @throws SQLException SQL実行例外
     */
    private AnpSdataModel __getAnpSdataFromRs(ResultSet rs) throws SQLException {
        AnpSdataModel bean = new AnpSdataModel();
        bean.setAphSid(rs.getInt("APH_SID"));
        bean.setApsType(rs.getInt("APS_TYPE"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        return bean;
    }
}
