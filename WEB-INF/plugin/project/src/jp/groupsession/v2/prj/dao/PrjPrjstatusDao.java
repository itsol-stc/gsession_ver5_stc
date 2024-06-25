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
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_PRJSTATUS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjPrjstatusDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPrjstatusDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjPrjstatusDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjPrjstatusDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_PRJSTATUS Data Bindding JavaBean
     * @param bean PRJ_PRJSTATUS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjPrjstatusModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_PRJSTATUS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRS_SID,");
            sql.addSql("   PRS_SORT,");
            sql.addSql("   PRS_NAME,");
            sql.addSql("   PRS_RATE,");
            sql.addSql("   PRS_AUID,");
            sql.addSql("   PRS_ADATE,");
            sql.addSql("   PRS_EUID,");
            sql.addSql("   PRS_EDATE");
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
            sql.addIntValue(bean.getPrsSid());
            sql.addIntValue(bean.getPrsSort());
            sql.addStrValue(bean.getPrsName());
            sql.addIntValue(bean.getPrsRate());
            sql.addIntValue(bean.getPrsAuid());
            sql.addDateValue(bean.getPrsAdate());
            sql.addIntValue(bean.getPrsEuid());
            sql.addDateValue(bean.getPrsEdate());
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
     * <p>Update PRJ_PRJSTATUS Data Bindding JavaBean
     * @param bean PRJ_PRJSTATUS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjPrjstatusModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" set ");
            sql.addSql("   PRS_SORT=?,");
            sql.addSql("   PRS_NAME=?,");
            sql.addSql("   PRS_AUID=?,");
            sql.addSql("   PRS_ADATE=?,");
            sql.addSql("   PRS_EUID=?,");
            sql.addSql("   PRS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PRS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrsSort());
            sql.addStrValue(bean.getPrsName());
            sql.addIntValue(bean.getPrsAuid());
            sql.addDateValue(bean.getPrsAdate());
            sql.addIntValue(bean.getPrsEuid());
            sql.addDateValue(bean.getPrsEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPrsSid());

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
     * <p>Select PRJ_PRJSTATUS All Data
     * @return List in PRJ_PRJSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjPrjstatusModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjPrjstatusModel> ret = new ArrayList<PrjPrjstatusModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRS_SID,");
            sql.addSql("   PRS_SORT,");
            sql.addSql("   PRS_NAME,");
            sql.addSql("   PRS_RATE,");
            sql.addSql("   PRS_AUID,");
            sql.addSql("   PRS_ADATE,");
            sql.addSql("   PRS_EUID,");
            sql.addSql("   PRS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" order by PRS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjstatusFromRs(rs));
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
     * <br>[機  能] プロジェクト状態を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return PRJ_PRJSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjPrjstatusModel> selectProjects(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjPrjstatusModel> ret = new ArrayList<PrjPrjstatusModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRS_SID,");
            sql.addSql("   PRS_SORT,");
            sql.addSql("   PRS_NAME,");
            sql.addSql("   PRS_RATE,");
            sql.addSql("   PRS_AUID,");
            sql.addSql("   PRS_ADATE,");
            sql.addSql("   PRS_EUID,");
            sql.addSql("   PRS_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" order by PRS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjstatusFromRs(rs));
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
     * <p>Select PRJ_PRJSTATUS
     * @param prjSid PRJ_SID
     * @param prsSid PRS_SID
     * @return PRJ_PRJSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public PrjPrjstatusModel select(int prjSid, int prsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjPrjstatusModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRS_SID,");
            sql.addSql("   PRS_SORT,");
            sql.addSql("   PRS_NAME,");
            sql.addSql("   PRS_RATE,");
            sql.addSql("   PRS_AUID,");
            sql.addSql("   PRS_ADATE,");
            sql.addSql("   PRS_EUID,");
            sql.addSql("   PRS_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PRS_SID=?");
            sql.addSql(" order by PRS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(prsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjPrjstatusFromRs(rs);
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
     * <p>Select PRJ_PRJSTATUS
     * @param prjSid PRJ_SID
     * @return PRJ_PRJSTATUSModel
     * @throws SQLException SQL実行例外
     */
    public int selectMaxSortValue(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRS_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" order by PRS_SORT desc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("PRS_SORT");
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
     * <p>Delete PRJ_PRJSTATUS
     * @param prjSid PRJ_SID
     * @param prsSid PRS_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int prjSid, int prsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PRS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(prsSid);

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
     * <p>Delete PRJ_PRJSTATUS
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
            sql.addSql("   PRJ_PRJSTATUS");
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
     * <p>Create PRJ_PRJSTATUS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjPrjstatusModel
     * @throws SQLException SQL実行例外
     */
    private PrjPrjstatusModel __getPrjPrjstatusFromRs(ResultSet rs) throws SQLException {
        PrjPrjstatusModel bean = new PrjPrjstatusModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPrsSid(rs.getInt("PRS_SID"));
        bean.setPrsSort(rs.getInt("PRS_SORT"));
        bean.setPrsName(rs.getString("PRS_NAME"));
        bean.setPrsRate(rs.getInt("PRS_RATE"));
        bean.setPrsAuid(rs.getInt("PRS_AUID"));
        bean.setPrsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRS_ADATE")));
        bean.setPrsEuid(rs.getInt("PRS_EUID"));
        bean.setPrsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRS_EDATE")));
        return bean;
    }
}
