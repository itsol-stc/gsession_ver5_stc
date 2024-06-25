package jp.groupsession.v2.cht.dao;

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
import jp.groupsession.v2.cht.model.ChtSpaccessModel;

/**
 * <p>CHT_SPACCESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtSpaccessDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtSpaccessDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtSpaccessDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtSpaccessDao(Connection con) {
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
            sql.addSql("drop table CHT_SPACCESS");

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
            sql.addSql(" create table CHT_SPACCESS (");
            sql.addSql("   CHS_SID integer not null,");
            sql.addSql("   CHS_NAME varchar(50) not null,");
            sql.addSql("   CHS_BIKO varchar(1000),");
            sql.addSql("   CHS_AUID integer not null,");
            sql.addSql("   CHS_ADATE timestamp not null,");
            sql.addSql("   CHS_EUID integer not null,");
            sql.addSql("   CHS_EDATE timestamp not null,");
            sql.addSql("   primary key (CHS_SID)");
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
     * <p>Insert CHT_SPACCESS Data Bindding JavaBean
     * @param bean CHT_SPACCESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtSpaccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_SPACCESS(");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CHS_NAME,");
            sql.addSql("   CHS_BIKO,");
            sql.addSql("   CHS_AUID,");
            sql.addSql("   CHS_ADATE,");
            sql.addSql("   CHS_EUID,");
            sql.addSql("   CHS_EDATE");
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
            sql.addIntValue(bean.getChsSid());
            sql.addStrValue(bean.getChsName());
            sql.addStrValue(bean.getChsBiko());
            sql.addIntValue(bean.getChsAuid());
            sql.addDateValue(bean.getChsAdate());
            sql.addIntValue(bean.getChsEuid());
            sql.addDateValue(bean.getChsEdate());
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
     * <p>Update CHT_SPACCESS Data Bindding JavaBean
     * @param bean CHT_SPACCESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtSpaccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_SPACCESS");
            sql.addSql(" set ");
            sql.addSql("   CHS_NAME=?,");
            sql.addSql("   CHS_BIKO=?,");
            sql.addSql("   CHS_EUID=?,");
            sql.addSql("   CHS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getChsName());
            sql.addStrValue(bean.getChsBiko());
            sql.addIntValue(bean.getChsEuid());
            sql.addDateValue(bean.getChsEdate());
            //where
            sql.addIntValue(bean.getChsSid());

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
     * <p>Select CHT_SPACCESS All Data
     * @return List in CHT_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtSpaccessModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtSpaccessModel> ret = new ArrayList<ChtSpaccessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CHS_NAME,");
            sql.addSql("   CHS_BIKO,");
            sql.addSql("   CHS_AUID,");
            sql.addSql("   CHS_ADATE,");
            sql.addSql("   CHS_EUID,");
            sql.addSql("   CHS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtSpaccessFromRs(rs));
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
     * <p>Select CHT_SPACCESS
     * @param chsSid CHS_SID
     * @return CHT_SPACCESSModel
     * @throws SQLException SQL実行例外
     */
    public ChtSpaccessModel select(int chsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtSpaccessModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CHS_NAME,");
            sql.addSql("   CHS_BIKO,");
            sql.addSql("   CHS_AUID,");
            sql.addSql("   CHS_ADATE,");
            sql.addSql("   CHS_EUID,");
            sql.addSql("   CHS_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_SPACCESS");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(chsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtSpaccessFromRs(rs);
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
     * <p>Delete CHT_SPACCESS
     * @param chsSid CHS_SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int chsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_SPACCESS");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(chsSid);

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
     * <p>Create CHT_SPACCESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtSpaccessModel
     * @throws SQLException SQL実行例外
     */
    private ChtSpaccessModel __getChtSpaccessFromRs(ResultSet rs) throws SQLException {
        ChtSpaccessModel bean = new ChtSpaccessModel();
        bean.setChsSid(rs.getInt("CHS_SID"));
        bean.setChsName(rs.getString("CHS_NAME"));
        bean.setChsBiko(rs.getString("CHS_BIKO"));
        bean.setChsAuid(rs.getInt("CHS_AUID"));
        bean.setChsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CHS_ADATE")));
        bean.setChsEuid(rs.getInt("CHS_EUID"));
        bean.setChsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CHS_EDATE")));
        return bean;
    }
}
