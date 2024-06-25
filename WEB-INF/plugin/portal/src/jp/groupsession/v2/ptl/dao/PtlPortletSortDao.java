package jp.groupsession.v2.ptl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ptl.model.PtlPortletSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTLET_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortletSortDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortletSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortletSortDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTLET_SORT");

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
            sql.addSql(" create table PTL_PORTLET_SORT (");
            sql.addSql("   PLT_SID integer not null,");
            sql.addSql("   PLS_SORT integer not null,");
            sql.addSql("   primary key (PLT_SID,PLS_SORT)");
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
     * <p>Insert PTL_PORTLET_SORT Data Bindding JavaBean
     * @param bean PTL_PORTLET_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortletSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET_SORT(");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPltSid());
            sql.addIntValue(bean.getPlsSort());
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
     * <p>Select PTL_PORTLET_SORT All Data
     * @return List in PTL_PORTLET_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletSortModel> ret = new ArrayList<PtlPortletSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLS_SORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortletSortFromRs(rs));
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
     * <p>Select PTL_PORTLET_SORT
     * @param pltSid PLT_SID
     * @param plsSort PLS_SORT
     * @return PTL_PORTLET_SORTModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortletSortModel select(int pltSid, int plsSort) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortletSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLS_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_SORT");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PLS_SORT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);
            sql.addIntValue(plsSort);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortletSortFromRs(rs);
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
     * <p>Delete PTL_PORTLET_SORT
     * @param pltSid PLT_SID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int delete(int pltSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_SORT");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);

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
     * <p>カテゴリ内のソート最大値で登録
     * @param ptlSid 採番したポートレットSID
     * @throws SQLException SQL実行例外
     */
    public void insertMaxSort(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET_SORT");
            sql.addSql(" select");
            sql.addSql("   " + ptlSid + ",");
            sql.addSql("   coalesce(max(PTL_PORTLET_SORT.PLS_SORT), 0) + 1");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create PTL_PORTLET_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortletSortModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortletSortModel __getPtlPortletSortFromRs(ResultSet rs) throws SQLException {
        PtlPortletSortModel bean = new PtlPortletSortModel();
        bean.setPltSid(rs.getInt("PLT_SID"));
        bean.setPlsSort(rs.getInt("PLS_SORT"));
        return bean;
    }
}
