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
import jp.groupsession.v2.prj.model.PrjTodostatusTmpModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_TODOSTATUS_TMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodostatusTmpDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodostatusTmpDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodostatusTmpDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodostatusTmpDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODOSTATUS_TMP Data Bindding JavaBean
     * @param bean PRJ_TODOSTATUS_TMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodostatusTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOSTATUS_TMP(");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PST_SID,");
            sql.addSql("   PST_NAME,");
            sql.addSql("   PST_RATE,");
            sql.addSql("   PST_SORT,");
            sql.addSql("   PST_AUID,");
            sql.addSql("   PST_ADATE,");
            sql.addSql("   PST_EUID,");
            sql.addSql("   PST_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPstSid());
            sql.addStrValue(bean.getPstName());
            sql.addIntValue(bean.getPstRate());
            sql.addIntValue(bean.getPstSort());
            sql.addIntValue(bean.getPstAuid());
            sql.addDateValue(bean.getPstAdate());
            sql.addIntValue(bean.getPstEuid());
            sql.addDateValue(bean.getPstEdate());
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
     * <p>Update PRJ_TODOSTATUS_TMP Data Bindding JavaBean
     * @param bean PRJ_TODOSTATUS_TMP Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodostatusTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOSTATUS_TMP");
            sql.addSql(" set ");
            sql.addSql("   PST_NAME=?,");
            sql.addSql("   PST_RATE=?,");
            sql.addSql("   PST_SORT=?,");
            sql.addSql("   PST_AUID=?,");
            sql.addSql("   PST_ADATE=?,");
            sql.addSql("   PST_EUID=?,");
            sql.addSql("   PST_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PST_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPstName());
            sql.addIntValue(bean.getPstRate());
            sql.addIntValue(bean.getPstSort());
            sql.addIntValue(bean.getPstAuid());
            sql.addDateValue(bean.getPstAdate());
            sql.addIntValue(bean.getPstEuid());
            sql.addDateValue(bean.getPstEdate());
            //where
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPstSid());

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
     * <p>Select PRJ_TODOSTATUS_TMP All Data
     * @return List in PRJ_TODOSTATUS_TMPModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodostatusTmpModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjTodostatusTmpModel> ret = new ArrayList<PrjTodostatusTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PST_SID,");
            sql.addSql("   PST_NAME,");
            sql.addSql("   PST_RATE,");
            sql.addSql("   PST_SORT,");
            sql.addSql("   PST_AUID,");
            sql.addSql("   PST_ADATE,");
            sql.addSql("   PST_EUID,");
            sql.addSql("   PST_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOSTATUS_TMP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodostatusTmpFromRs(rs));
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
     * <p>Select PRJ_TODOSTATUS_TMP
     * @param bean PRJ_TODOSTATUS_TMP Model
     * @return PRJ_TODOSTATUS_TMPModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodostatusTmpModel select(PrjTodostatusTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodostatusTmpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PST_SID,");
            sql.addSql("   PST_NAME,");
            sql.addSql("   PST_RATE,");
            sql.addSql("   PST_SORT,");
            sql.addSql("   PST_AUID,");
            sql.addSql("   PST_ADATE,");
            sql.addSql("   PST_EUID,");
            sql.addSql("   PST_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PST_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPstSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodostatusTmpFromRs(rs);
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
     * <p>Delete PRJ_TODOSTATUS_TMP
     * @param bean PRJ_TODOSTATUS_TMP Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(PrjTodostatusTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PST_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPstSid());

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
     * <p>Delete PRJ_TODOSTATUS_TMP
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
            sql.addSql("   PRJ_TODOSTATUS_TMP");
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
     * <br>[機  能] プロジェクトテンプレートTODO状態(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjTmpSidList 削除SIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deletePrjTemplateTodoSts(ArrayList<PrjPrjdataTmpModel> prjTmpSidList)
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
            sql.addSql("   PRJ_TODOSTATUS_TMP");
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
     * <br>[機  能] TODO状態_プロジェクト用を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid PRT_SID
     * @return PRJ_TODOSTATUS_TMP Model
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodostatusTmpModel> selectTmpProjects(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodostatusTmpModel> ret = new ArrayList<PrjTodostatusTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PST_SID,");
            sql.addSql("   PST_NAME,");
            sql.addSql("   PST_RATE,");
            sql.addSql("   PST_SORT,");
            sql.addSql("   PST_AUID,");
            sql.addSql("   PST_ADATE,");
            sql.addSql("   PST_EUID,");
            sql.addSql("   PST_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   PST_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodostatusTmpFromRs(rs));
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
     * <p>Create PRJ_TODOSTATUS_TMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodostatusTmpModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodostatusTmpModel __getPrjTodostatusTmpFromRs(ResultSet rs) throws SQLException {
        PrjTodostatusTmpModel bean = new PrjTodostatusTmpModel();
        bean.setPrtSid(rs.getInt("PRT_SID"));
        bean.setPstSid(rs.getInt("PST_SID"));
        bean.setPstName(rs.getString("PST_NAME"));
        bean.setPstRate(rs.getInt("PST_RATE"));
        bean.setPstSort(rs.getInt("PST_SORT"));
        bean.setPstAuid(rs.getInt("PST_AUID"));
        bean.setPstAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PST_ADATE")));
        bean.setPstEuid(rs.getInt("PST_EUID"));
        bean.setPstEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PST_EDATE")));
        return bean;
    }
}