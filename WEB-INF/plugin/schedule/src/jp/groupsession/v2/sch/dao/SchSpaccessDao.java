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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchSpaccessModel;

/**
 * <p>SCH_SPACCESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchSpaccessDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchSpaccessDao.class);

    /**
     * <p>Default Constructor
     */
    public SchSpaccessDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchSpaccessDao(Connection con) {
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
            sql.addSql("drop table SCH_SPACCESS");

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
            sql.addSql(" create table SCH_SPACCESS (");
            sql.addSql("   SSA_SID NUMBER(10,0) not null,");
            sql.addSql("   SSA_NAME varchar(50) not null,");
            sql.addSql("   SSA_BIKO varchar(1000),");
            sql.addSql("   SSA_AUID NUMBER(10,0) not null,");
            sql.addSql("   SSA_ADATE varchar(23) not null,");
            sql.addSql("   SSA_EUID NUMBER(10,0) not null,");
            sql.addSql("   SSA_EDATE varchar(23) not null,");
            sql.addSql("   primary key (SSA_SID)");
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
     * <p>Insert SCH_SPACCESS Data Bindding JavaBean
     * @param bean SCH_SPACCESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchSpaccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_SPACCESS(");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SSA_NAME,");
            sql.addSql("   SSA_BIKO,");
            sql.addSql("   SSA_AUID,");
            sql.addSql("   SSA_ADATE,");
            sql.addSql("   SSA_EUID,");
            sql.addSql("   SSA_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSsaSid());
            sql.addStrValue(bean.getSsaName());
            sql.addStrValue(bean.getSsaBiko());
            sql.addIntValue(bean.getSsaAuid());
            sql.addDateValue(bean.getSsaAdate());
            sql.addIntValue(bean.getSsaEuid());
            sql.addDateValue(bean.getSsaEdate());
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
     * <p>Update SCH_SPACCESS Data Bindding JavaBean
     * @param bean SCH_SPACCESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchSpaccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_SPACCESS");
            sql.addSql(" set ");
            sql.addSql("   SSA_NAME=?,");
            sql.addSql("   SSA_BIKO=?,");
            sql.addSql("   SSA_EUID=?,");
            sql.addSql("   SSA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSsaName());
            sql.addStrValue(bean.getSsaBiko());
            sql.addIntValue(bean.getSsaEuid());
            sql.addDateValue(bean.getSsaEdate());
            //where
            sql.addIntValue(bean.getSsaSid());

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
     * <p>Select SCH_SPACCESS All Data
     * @return List in SCH_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchSpaccessModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchSpaccessModel> ret = new ArrayList<SchSpaccessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SSA_NAME,");
            sql.addSql("   SSA_BIKO,");
            sql.addSql("   SSA_AUID,");
            sql.addSql("   SSA_ADATE,");
            sql.addSql("   SSA_EUID,");
            sql.addSql("   SSA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_SPACCESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchSpaccessFromRs(rs));
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
     * <p>Select SCH_SPACCESS
     * @param ssaSid SSA_SID
     * @return SCH_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public SchSpaccessModel select(int ssaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchSpaccessModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SSA_NAME,");
            sql.addSql("   SSA_BIKO,");
            sql.addSql("   SSA_AUID,");
            sql.addSql("   SSA_ADATE,");
            sql.addSql("   SSA_EUID,");
            sql.addSql("   SSA_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_SPACCESS");
            sql.addSql(" where ");
            sql.addSql("   SSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ssaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchSpaccessFromRs(rs);
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
     * <p>Select SCH_SPACCESS
     * @param ssaSidList SSA_SID
     * @return SCH_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchSpaccessModel> select(List<Integer> ssaSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchSpaccessModel> ret = new ArrayList<SchSpaccessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SSA_SID,");
            sql.addSql("   SSA_NAME,");
            sql.addSql("   SSA_BIKO,");
            sql.addSql("   SSA_AUID,");
            sql.addSql("   SSA_ADATE,");
            sql.addSql("   SSA_EUID,");
            sql.addSql("   SSA_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_SPACCESS");
            if (!ssaSidList.isEmpty() && ssaSidList.size() > 0) {
                sql.addSql(" where ");
                sql.addSql("   SSA_SID in (");
                for (int i = 0; i < ssaSidList.size(); i++) {
                    if (i == 0) {
                        sql.addSql("   ?");
                    } else {
                        sql.addSql("   ,?");
                    }
                    sql.addIntValue(ssaSidList.get(i));
                }
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchSpaccessFromRs(rs));
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
     * <p>Delete SCH_SPACCESS
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
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_SPACCESS");
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
     * <p>Create SCH_SPACCESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchSpaccessModel
     * @throws SQLException SQL実行例外
     */
    private SchSpaccessModel __getSchSpaccessFromRs(ResultSet rs) throws SQLException {
        SchSpaccessModel bean = new SchSpaccessModel();
        bean.setSsaSid(rs.getInt("SSA_SID"));
        bean.setSsaName(rs.getString("SSA_NAME"));
        bean.setSsaBiko(rs.getString("SSA_BIKO"));
        bean.setSsaAuid(rs.getInt("SSA_AUID"));
        bean.setSsaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SSA_ADATE")));
        bean.setSsaEuid(rs.getInt("SSA_EUID"));
        bean.setSsaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SSA_EDATE")));
        return bean;
    }
}
