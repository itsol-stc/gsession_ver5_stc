package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_TODOSTATUS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodostatusDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodostatusDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodostatusDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodostatusDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODOSTATUS Data Bindding JavaBean
     * @param bean PRJ_TODOSTATUS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodostatusModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOSTATUS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTS_NAME,");
            sql.addSql("   PTS_RATE,");
            sql.addSql("   PTS_SORT,");
            sql.addSql("   PTS_AUID,");
            sql.addSql("   PTS_ADATE,");
            sql.addSql("   PTS_EUID,");
            sql.addSql("   PTS_EDATE");
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
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtsSid());
            sql.addStrValue(bean.getPtsName());
            sql.addIntValue(bean.getPtsRate());
            sql.addIntValue(bean.getPtsSort());
            sql.addIntValue(bean.getPtsAuid());
            sql.addDateValue(bean.getPtsAdate());
            sql.addIntValue(bean.getPtsEuid());
            sql.addDateValue(bean.getPtsEdate());
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
     * <p>Update PRJ_TODOSTATUS Data Bindding JavaBean
     * @param bean PRJ_TODOSTATUS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodostatusModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" set ");
            sql.addSql("   PTS_NAME=?,");
            sql.addSql("   PTS_RATE=?,");
            sql.addSql("   PTS_SORT=?,");
            sql.addSql("   PTS_AUID=?,");
            sql.addSql("   PTS_ADATE=?,");
            sql.addSql("   PTS_EUID=?,");
            sql.addSql("   PTS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPtsName());
            sql.addIntValue(bean.getPtsRate());
            sql.addIntValue(bean.getPtsSort());
            sql.addIntValue(bean.getPtsAuid());
            sql.addDateValue(bean.getPtsAdate());
            sql.addIntValue(bean.getPtsEuid());
            sql.addDateValue(bean.getPtsEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtsSid());

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
     * <p>Select PRJ_TODOSTATUS All Data
     * @return List in PRJ_TODOSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodostatusModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodostatusModel> ret = new ArrayList<PrjTodostatusModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTS_NAME,");
            sql.addSql("   PTS_RATE,");
            sql.addSql("   PTS_SORT,");
            sql.addSql("   PTS_AUID,");
            sql.addSql("   PTS_ADATE,");
            sql.addSql("   PTS_EUID,");
            sql.addSql("   PTS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" order by PTS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodostatusFromRs(rs));
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
     * <br>[機  能] TODO状態を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return PRJ_TODOSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodostatusModel> selectProjects(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodostatusModel> ret = new ArrayList<PrjTodostatusModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTS_NAME,");
            sql.addSql("   PTS_RATE,");
            sql.addSql("   PTS_SORT,");
            sql.addSql("   PTS_AUID,");
            sql.addSql("   PTS_ADATE,");
            sql.addSql("   PTS_EUID,");
            sql.addSql("   PTS_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" order by PTS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodostatusFromRs(rs));
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
     * <br>[機  能] TODO状態マッピングを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid PRJ_SID
     * @return HashMap in PRJ_TODOSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, PrjTodostatusModel> selectProjectsMap(int prjSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, PrjTodostatusModel> ret =
            new HashMap<String, PrjTodostatusModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTS_NAME,");
            sql.addSql("   PTS_RATE,");
            sql.addSql("   PTS_SORT,");
            sql.addSql("   PTS_AUID,");
            sql.addSql("   PTS_ADATE,");
            sql.addSql("   PTS_EUID,");
            sql.addSql("   PTS_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" order by PTS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrjTodostatusModel mdl = __getPrjTodostatusFromRs(rs);
                ret.put(String.valueOf(mdl.getPtsRate()), mdl);
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
     * <p>Select PRJ_TODOSTATUS
     * @param prjSid PRJ_SID
     * @param ptsSid PTS_SID
     * @return PRJ_TODOSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodostatusModel select(int prjSid, int ptsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodostatusModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTS_NAME,");
            sql.addSql("   PTS_RATE,");
            sql.addSql("   PTS_SORT,");
            sql.addSql("   PTS_AUID,");
            sql.addSql("   PTS_ADATE,");
            sql.addSql("   PTS_EUID,");
            sql.addSql("   PTS_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_SID=?");
            sql.addSql(" order by PTS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodostatusFromRs(rs);
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
     * <p>指定された状態が存在するかを判定する
     * @param prjSid PRJ_SID
     * @param ptsSid PTS_SID
     * @return true: 存在する false: 存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existStatus(int prjSid, int ptsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PTS_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Delete PRJ_TODOSTATUS
     * @param prjSid PRJ_SID
     * @param ptsSid PTS_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int prjSid, int ptsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptsSid);

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
     * <p>Delete PRJ_TODOSTATUS
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
            sql.addSql("   PRJ_TODOSTATUS");
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
     * <p>Create PRJ_TODOSTATUS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodostatusModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodostatusModel __getPrjTodostatusFromRs(ResultSet rs) throws SQLException {
        PrjTodostatusModel bean = new PrjTodostatusModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtsSid(rs.getInt("PTS_SID"));
        bean.setPtsName(rs.getString("PTS_NAME"));
        bean.setPtsRate(rs.getInt("PTS_RATE"));
        bean.setPtsSort(rs.getInt("PTS_SORT"));
        bean.setPtsAuid(rs.getInt("PTS_AUID"));
        bean.setPtsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTS_ADATE")));
        bean.setPtsEuid(rs.getInt("PTS_EUID"));
        bean.setPtsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTS_EDATE")));
        return bean;
    }
}
