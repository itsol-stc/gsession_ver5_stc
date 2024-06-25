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
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTLET_CATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletCategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortletCategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortletCategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortletCategoryDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTLET_CATEGORY");

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
            sql.addSql(" create table PTL_PORTLET_CATEGORY (");
            sql.addSql("   PLC_SID integer not null,");
            sql.addSql("   PLC_NAME varchar(100) not null,");
            sql.addSql("   PLC_BIKO varchar(500),");
            sql.addSql("   primary key (PLC_SID)");
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
     * <p>Insert PTL_PORTLET_CATEGORY Data Bindding JavaBean
     * @param bean PTL_PORTLET_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortletCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET_CATEGORY(");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PLC_NAME,");
            sql.addSql("   PLC_BIKO");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPlcSid());
            sql.addStrValue(bean.getPlcName());
            sql.addStrValue(bean.getPlcBiko());
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
     * <p>Update PTL_PORTLET_CATEGORY Data Bindding JavaBean
     * @param bean PTL_PORTLET_CATEGORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortletCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTLET_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   PLC_NAME=?,");
            sql.addSql("   PLC_BIKO=?");
            sql.addSql(" where ");
            sql.addSql("   PLC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPlcName());
            sql.addStrValue(bean.getPlcBiko());
            //where
            sql.addIntValue(bean.getPlcSid());

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
     * <p>Select PTL_PORTLET_CATEGORY All Data
     * @return List in PTL_PORTLET_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletCategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletCategoryModel> ret = new ArrayList<PtlPortletCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PLC_NAME,");
            sql.addSql("   PLC_BIKO");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET_CATEGORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortletCategoryFromRs(rs));
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
     * <p>Select PTL_PORTLET_CATEGORY
     * @param plcSid PLC_SID
     * @return PTL_PORTLET_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortletCategoryModel select(int plcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortletCategoryModel ret = new PtlPortletCategoryModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PLC_NAME,");
            sql.addSql("   PLC_BIKO");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PLC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(plcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortletCategoryFromRs(rs);
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
     * <p>Delete PTL_PORTLET_CATEGORY
     * @param plcSid PLC_SID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int delete(int plcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PLC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(plcSid);

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
     * <p>Select PTL_PORTLET_CATEGORY All Data
     * @return List in PTL_PORTLET_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletCategoryModel> selectSortAll() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletCategoryModel> ret = new ArrayList<PtlPortletCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_PORTLET_CATEGORY.PLC_SID,");
            sql.addSql("   PTL_PORTLET_CATEGORY.PLC_NAME,");
            sql.addSql("   PTL_PORTLET_CATEGORY.PLC_BIKO,");
            sql.addSql("   PTL_PORTLET_CATEGORY_SORT.PCS_SORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET_CATEGORY");
            sql.addSql("   left join");
            sql.addSql("     PTL_PORTLET_CATEGORY_SORT");
            sql.addSql("   on");
            sql.addSql("     PTL_PORTLET_CATEGORY.PLC_SID = PTL_PORTLET_CATEGORY_SORT.PLC_SID");
            sql.addSql(" order by ");
            sql.addSql("   PCS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PtlPortletCategoryModel bean = new PtlPortletCategoryModel();
                bean.setPlcSid(rs.getInt("PLC_SID"));
                bean.setPlcName(rs.getString("PLC_NAME"));
                bean.setPlcBiko(rs.getString("PLC_BIKO"));
                bean.setPlcSort(rs.getInt("PCS_SORT"));
                ret.add(bean);
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
     * <p>Create PTL_PORTLET_CATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortletCategoryModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortletCategoryModel __getPtlPortletCategoryFromRs(ResultSet rs)
    throws SQLException {
        PtlPortletCategoryModel bean = new PtlPortletCategoryModel();
        bean.setPlcSid(rs.getInt("PLC_SID"));
        bean.setPlcName(rs.getString("PLC_NAME"));
        bean.setPlcBiko(rs.getString("PLC_BIKO"));
        return bean;
    }
}
