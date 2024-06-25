package jp.groupsession.v2.prj.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;

/**
 * <p>PRJ_TODOCATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodocategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodocategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodocategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodocategoryDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODOCATEGORY Data Bindding JavaBean
     * @param bean PRJ_TODOCATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodocategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOCATEGORY(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTC_CATEGORY_SID,");
            sql.addSql("   PTC_SORT,");
            sql.addSql("   PTC_NAME,");
            sql.addSql("   PTC_AUID,");
            sql.addSql("   PTC_ADATE,");
            sql.addSql("   PTC_EUID,");
            sql.addSql("   PTC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtcCategorySid());
            sql.addIntValue(bean.getPtcSort());
            sql.addStrValue(bean.getPtcName());
            sql.addIntValue(bean.getPtcAuid());
            sql.addDateValue(bean.getPtcAdate());
            sql.addIntValue(bean.getPtcEuid());
            sql.addDateValue(bean.getPtcEdate());
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
     * <p>Update PRJ_TODOCATEGORY Data Bindding JavaBean
     * @param bean PRJ_TODOCATEGORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodocategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" set ");
            sql.addSql("   PTC_SORT=?,");
            sql.addSql("   PTC_NAME=?,");
            sql.addSql("   PTC_AUID=?,");
            sql.addSql("   PTC_ADATE=?,");
            sql.addSql("   PTC_EUID=?,");
            sql.addSql("   PTC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTC_CATEGORY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtcSort());
            sql.addStrValue(bean.getPtcName());
            sql.addIntValue(bean.getPtcAuid());
            sql.addDateValue(bean.getPtcAdate());
            sql.addIntValue(bean.getPtcEuid());
            sql.addDateValue(bean.getPtcEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtcCategorySid());

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
     * <p>Select PRJ_TODOCATEGORY All Data
     * @return List in PRJ_TODOCATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodocategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodocategoryModel> ret = new ArrayList<PrjTodocategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTC_CATEGORY_SID,");
            sql.addSql("   PTC_SORT,");
            sql.addSql("   PTC_NAME,");
            sql.addSql("   PTC_AUID,");
            sql.addSql("   PTC_ADATE,");
            sql.addSql("   PTC_EUID,");
            sql.addSql("   PTC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" order by PTC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodocategoryFromRs(rs));
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
     * <br>[機  能] TODOカテゴリを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return List in PRJ_TODOCATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodocategoryModel> selectProjects(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodocategoryModel> ret = new ArrayList<PrjTodocategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTC_CATEGORY_SID,");
            sql.addSql("   PTC_SORT,");
            sql.addSql("   PTC_NAME,");
            sql.addSql("   PTC_AUID,");
            sql.addSql("   PTC_ADATE,");
            sql.addSql("   PTC_EUID,");
            sql.addSql("   PTC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" order by PTC_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodocategoryFromRs(rs));
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
     * <br>[機  能] TODOカテゴリマッピングを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid PRJ_SID
     * @return HashMap in PRJ_TODOCATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, PrjTodocategoryModel> selectProjectsMap(int prjSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, PrjTodocategoryModel> ret =
            new HashMap<String, PrjTodocategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTC_CATEGORY_SID,");
            sql.addSql("   PTC_SORT,");
            sql.addSql("   PTC_NAME,");
            sql.addSql("   PTC_AUID,");
            sql.addSql("   PTC_ADATE,");
            sql.addSql("   PTC_EUID,");
            sql.addSql("   PTC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" order by PTC_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PrjTodocategoryModel mdl = __getPrjTodocategoryFromRs(rs);
                ret.put(mdl.getPtcName(), mdl);
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
     * <p>Select PRJ_TODOCATEGORY
     * @param prjSid PRJ_SID
     * @param ptcCategorySid PTC_CATEGORY_SID
     * @return PRJ_TODOCATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodocategoryModel select(int prjSid, int ptcCategorySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodocategoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTC_CATEGORY_SID,");
            sql.addSql("   PTC_SORT,");
            sql.addSql("   PTC_NAME,");
            sql.addSql("   PTC_AUID,");
            sql.addSql("   PTC_ADATE,");
            sql.addSql("   PTC_EUID,");
            sql.addSql("   PTC_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTC_CATEGORY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptcCategorySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodocategoryFromRs(rs);
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
     * <p>Delete PRJ_TODOCATEGORY
     * @param prjSid PRJ_SID
     * @param ptcCategorySid PTC_CATEGORY_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int prjSid, int ptcCategorySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTC_CATEGORY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptcCategorySid);

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
     * <p>Delete PRJ_TODOCATEGORY
     * @param prjSid PRJ_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deletePrjAll(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

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
     * <p>Create PRJ_TODOCATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodocategoryModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodocategoryModel __getPrjTodocategoryFromRs(ResultSet rs) throws SQLException {
        PrjTodocategoryModel bean = new PrjTodocategoryModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtcCategorySid(rs.getInt("PTC_CATEGORY_SID"));
        bean.setPtcSort(rs.getInt("PTC_SORT"));
        bean.setPtcName(rs.getString("PTC_NAME"));
        bean.setPtcAuid(rs.getInt("PTC_AUID"));
        bean.setPtcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTC_ADATE")));
        bean.setPtcEuid(rs.getInt("PTC_EUID"));
        bean.setPtcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTC_EDATE")));
        return bean;
    }
}
