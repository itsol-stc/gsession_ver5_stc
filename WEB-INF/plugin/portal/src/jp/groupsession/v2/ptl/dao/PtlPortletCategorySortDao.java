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
import jp.groupsession.v2.ptl.model.PtlPortletCategorySortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTLET_CATEGORY_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletCategorySortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortletCategorySortDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortletCategorySortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortletCategorySortDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTLET_CATEGORY_SORT");

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
            sql.addSql(" create table PTL_PORTLET_CATEGORY_SORT (");
            sql.addSql("   PLC_SID integer not null,");
            sql.addSql("   PCS_SORT integer not null,");
            sql.addSql("   primary key (PLC_SID,PCS_SORT)");
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
     * <p>Insert PTL_PORTLET_CATEGORY_SORT Data Bindding JavaBean
     * @param bean PTL_PORTLET_CATEGORY_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortletCategorySortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET_CATEGORY_SORT(");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PCS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPlcSid());
            sql.addIntValue(bean.getPcsSort());
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
     * <p>Update PTL_PORTLET_CATEGORY_SORT Data Bindding JavaBean
     * @param bean PTL_PORTLET_CATEGORY_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortletCategorySortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTLET_CATEGORY_SORT");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   PLC_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCS_SORT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getPlcSid());
            sql.addIntValue(bean.getPcsSort());

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
     * <p>Select PTL_PORTLET_CATEGORY_SORT All Data
     * @return List in PTL_PORTLET_CATEGORY_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletCategorySortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletCategorySortModel> ret = new ArrayList<PtlPortletCategorySortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PCS_SORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET_CATEGORY_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortletCategorySortFromRs(rs));
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
     * <p>Select PTL_PORTLET_CATEGORY_SORT
     * @param plcSid PLC_SID
     * @param pcsSort PCS_SORT
     * @return PTL_PORTLET_CATEGORY_SORTModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortletCategorySortModel select(int plcSid, int pcsSort) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortletCategorySortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PCS_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_CATEGORY_SORT");
            sql.addSql(" where ");
            sql.addSql("   PLC_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCS_SORT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(plcSid);
            sql.addIntValue(pcsSort);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortletCategorySortFromRs(rs);
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
     * <p>Delete PTL_PORTLET_CATEGORY_SORT
     * @param plcSid PLC_SID
     * @throws SQLException SQL実行例外
     * @return int 削除数
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
            sql.addSql("   PTL_PORTLET_CATEGORY_SORT");
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
     * [機  能] ソートの最大値登録
     * [解  説] 指定されたカテゴリをソートの最大値で登録する。
     * [備  考]
     * @param catSid カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void insertMaxSort(int catSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET_CATEGORY_SORT");
            sql.addSql(" select");
            sql.addSql("   ?,");
            sql.addSql("   coalesce(max(PTL_PORTLET_CATEGORY_SORT.PCS_SORT), 0) + 1");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_CATEGORY_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(catSid);

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
     * <p>Create PTL_PORTLET_CATEGORY_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortletCategorySortModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortletCategorySortModel __getPtlPortletCategorySortFromRs(ResultSet rs)
    throws SQLException {
        PtlPortletCategorySortModel bean = new PtlPortletCategorySortModel();
        bean.setPlcSid(rs.getInt("PLC_SID"));
        bean.setPcsSort(rs.getInt("PCS_SORT"));
        return bean;
    }
}
