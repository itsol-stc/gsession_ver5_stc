package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.model.BmkSchemeModel;

/**
 * <p>BMK_SCHEME Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BmkSchemeDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkSchemeDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkSchemeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkSchemeDao(Connection con) {
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
            sql.addSql("drop table BMK_SCHEME");

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
            sql.addSql(" create table BMK_SCHEME (");
            sql.addSql("   BSC_SCHEME varchar(20) not null,");
            sql.addSql("   primary key (BSC_SCHEME)");
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
     * <p>Insert BMK_SCHEME Data Bindding JavaBean
     * @param bean BMK_SCHEME Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkSchemeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_SCHEME(");
            sql.addSql("   BSC_SCHEME");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBscScheme());
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
     * <p>Update BMK_SCHEME Data Bindding JavaBean
     * @param bean BMK_SCHEME Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkSchemeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_SCHEME");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   BSC_SCHEME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addStrValue(bean.getBscScheme());

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
     * <p>Select BMK_SCHEME All Data
     * @return List in BMK_SCHEMEModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkSchemeModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkSchemeModel> ret = new ArrayList<BmkSchemeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BSC_SCHEME");
            sql.addSql(" from ");
            sql.addSql("   BMK_SCHEME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkSchemeFromRs(rs));
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
     * <p>Select BMK_SCHEME
     * @param bscScheme BSC_SCHEME
     * @return BMK_SCHEMEModel
     * @throws SQLException SQL実行例外
     */
    public BmkSchemeModel select(String bscScheme) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkSchemeModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BSC_SCHEME");
            sql.addSql(" from");
            sql.addSql("   BMK_SCHEME");
            sql.addSql(" where ");
            sql.addSql("   BSC_SCHEME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bscScheme);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkSchemeFromRs(rs);
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
     * <p>Delete BMK_SCHEME
     * @param bscScheme BSC_SCHEME
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(String bscScheme) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_SCHEME");
            sql.addSql(" where ");
            sql.addSql("   BSC_SCHEME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bscScheme);

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
     * <p>Create BMK_SCHEME Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkSchemeModel
     * @throws SQLException SQL実行例外
     */
    private BmkSchemeModel __getBmkSchemeFromRs(ResultSet rs) throws SQLException {
        BmkSchemeModel bean = new BmkSchemeModel();
        bean.setBscScheme(rs.getString("BSC_SCHEME"));
        return bean;
    }
}
