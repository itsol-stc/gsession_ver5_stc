package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rsv.model.RsvDatausedSumModel;

/**
 * <p>RSV_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvDatausedSumDao(Connection con) {
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
            sql.addSql("drop table RSV_DATAUSED_SUM");

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
            sql.addSql(" create table RSV_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   RSV_SYRK_SUM_SIZE bigint not null,");
            sql.addSql("   RSV_DATA_SUM_SIZE bigint not null");
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
     * <p>Insert RSV_DATAUSED_SUM Data Bindding JavaBean
     * @param bean RSV_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   RSV_SYRK_SUM_SIZE,");
            sql.addSql("   RSV_DATA_SUM_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getRsvSyrkSumSize());
            sql.addLongValue(bean.getRsvDataSumSize());
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
     * <p>Update RSV_DATAUSED_SUM Data Bindding JavaBean
     * @param bean RSV_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   RSV_SYRK_SUM_SIZE=?,");
            sql.addSql("   RSV_DATA_SUM_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getRsvSyrkSumSize());
            sql.addLongValue(bean.getRsvDataSumSize());

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
     * <p>Delete RSV_DATAUSED_SUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select RSV_DATAUSED_SUM All Data
     * @return List in RSV_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvDatausedSumModel> ret = new ArrayList<RsvDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   RSV_SYRK_SUM_SIZE,");
            sql.addSql("   RSV_DATA_SUM_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSV_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvDatausedSumFromRs(rs));
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
     * <br>[機  能] 使用データサイズの「集計」を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの「集計」
     * @throws SQLException
     */
    public RsvDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   RSV_SYRK_SUM_SIZE,");
            sql.addSql("   RSV_DATA_SUM_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSV_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvDatausedSumFromRs(rs);
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
     * <br>[機  能] 使用データサイズの集計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの集計
     * @throws SQLException
     */
    public RsvDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(RSV_SYRK_SUM_SIZE) as SUM_RSV_SYRK_SUM_SIZE,");
            sql.addSql("   sum(RSV_DATA_SUM_SIZE) as SUM_RSV_DATA_SUM_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSV_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RsvDatausedSumModel();
                ret.setRsvSyrkSumSize(rs.getLong("SUM_RSV_SYRK_SUM_SIZE"));
                ret.setRsvDataSumSize(rs.getLong("SUM_RSV_DATA_SUM_SIZE"));
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
     * <p>Create RSV_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private RsvDatausedSumModel __getRsvDatausedSumFromRs(ResultSet rs) throws SQLException {
        RsvDatausedSumModel bean = new RsvDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setRsvSyrkSumSize(rs.getLong("RSV_SYRK_SUM_SIZE"));
        bean.setRsvDataSumSize(rs.getLong("RSV_DATA_SUM_SIZE"));
        return bean;
    }
}
