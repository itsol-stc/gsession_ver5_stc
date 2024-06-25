package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryTmpModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_TODOCATEGORY_TMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodocategoryTmpDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodocategoryTmpDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodocategoryTmpDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodocategoryTmpDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODOCATEGORY_TMP Data Bindding JavaBean
     * @param bean PRJ_TODOCATEGORY_TMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodocategoryTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOCATEGORY_TMP(");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PCT_CATEGORY_SID,");
            sql.addSql("   PCT_SORT,");
            sql.addSql("   PCT_NAME,");
            sql.addSql("   PCT_AUID,");
            sql.addSql("   PCT_ADATE,");
            sql.addSql("   PCT_EUID,");
            sql.addSql("   PCT_EDATE");
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
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPctCategorySid());
            sql.addIntValue(bean.getPctSort());
            sql.addStrValue(bean.getPctName());
            sql.addIntValue(bean.getPctAuid());
            sql.addDateValue(bean.getPctAdate());
            sql.addIntValue(bean.getPctEuid());
            sql.addDateValue(bean.getPctEdate());
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
     * <p>Update PRJ_TODOCATEGORY_TMP Data Bindding JavaBean
     * @param bean PRJ_TODOCATEGORY_TMP Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodocategoryTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" set ");
            sql.addSql("   PCT_SORT=?,");
            sql.addSql("   PCT_NAME=?,");
            sql.addSql("   PCT_AUID=?,");
            sql.addSql("   PCT_ADATE=?,");
            sql.addSql("   PCT_EUID=?,");
            sql.addSql("   PCT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCT_CATEGORY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPctSort());
            sql.addStrValue(bean.getPctName());
            sql.addIntValue(bean.getPctAuid());
            sql.addDateValue(bean.getPctAdate());
            sql.addIntValue(bean.getPctEuid());
            sql.addDateValue(bean.getPctEdate());
            //where
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPctCategorySid());

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
     * <p>Select PRJ_TODOCATEGORY_TMP All Data
     * @return List in PRJ_TODOCATEGORY_TMPModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodocategoryTmpModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjTodocategoryTmpModel> ret = new ArrayList<PrjTodocategoryTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PCT_CATEGORY_SID,");
            sql.addSql("   PCT_SORT,");
            sql.addSql("   PCT_NAME,");
            sql.addSql("   PCT_AUID,");
            sql.addSql("   PCT_ADATE,");
            sql.addSql("   PCT_EUID,");
            sql.addSql("   PCT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodocategoryTmpFromRs(rs));
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
     * <p>Select PRJ_TODOCATEGORY_TMP
     * @param bean PRJ_TODOCATEGORY_TMP Model
     * @return PRJ_TODOCATEGORY_TMPModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodocategoryTmpModel select(PrjTodocategoryTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodocategoryTmpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PCT_CATEGORY_SID,");
            sql.addSql("   PCT_SORT,");
            sql.addSql("   PCT_NAME,");
            sql.addSql("   PCT_AUID,");
            sql.addSql("   PCT_ADATE,");
            sql.addSql("   PCT_EUID,");
            sql.addSql("   PCT_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCT_CATEGORY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPctCategorySid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodocategoryTmpFromRs(rs);
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
     * <p>Delete PRJ_TODOCATEGORY_TMP
     * @param bean PRJ_TODOCATEGORY_TMP Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(PrjTodocategoryTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCT_CATEGORY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPctCategorySid());

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
     * <p>Delete PRJ_TODOCATEGORY_TMP
     * @param prtSid prtSid
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteAll(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

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
     * <br>[機  能] プロジェクトテンプレートTODOカテゴリ(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjTmpSidList 削除SIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deletePrjTemplateTodoCat(ArrayList<PrjPrjdataTmpModel> prjTmpSidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (prjTmpSidList == null || prjTmpSidList.size() < 1) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID in (");

            for (int i = 0; i < prjTmpSidList.size(); i++) {
                sql.addSql("?");
                PrjPrjdataTmpModel param = prjTmpSidList.get(i);
                sql.addIntValue(param.getPrtSid());
                if (i < prjTmpSidList.size() - 1) {
                    sql.addSql(", ");
                }
            }
            sql.addSql(")");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリ_テンプレート用を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid PRT_SID
     * @return List in PRJ_TODOCATEGORY_TMP Model
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodocategoryTmpModel> selectTmpProjects(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodocategoryTmpModel> ret = new ArrayList<PrjTodocategoryTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PCT_CATEGORY_SID,");
            sql.addSql("   PCT_SORT,");
            sql.addSql("   PCT_NAME,");
            sql.addSql("   PCT_AUID,");
            sql.addSql("   PCT_ADATE,");
            sql.addSql("   PCT_EUID,");
            sql.addSql("   PCT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   PCT_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodocategoryTmpFromRs(rs));
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
     * <p>Create PRJ_TODOCATEGORY_TMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodocategoryTmpModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodocategoryTmpModel __getPrjTodocategoryTmpFromRs(ResultSet rs)
        throws SQLException {
        PrjTodocategoryTmpModel bean = new PrjTodocategoryTmpModel();
        bean.setPrtSid(rs.getInt("PRT_SID"));
        bean.setPctCategorySid(rs.getInt("PCT_CATEGORY_SID"));
        bean.setPctSort(rs.getInt("PCT_SORT"));
        bean.setPctName(rs.getString("PCT_NAME"));
        bean.setPctAuid(rs.getInt("PCT_AUID"));
        bean.setPctAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PCT_ADATE")));
        bean.setPctEuid(rs.getInt("PCT_EUID"));
        bean.setPctEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PCT_EDATE")));
        return bean;
    }
}