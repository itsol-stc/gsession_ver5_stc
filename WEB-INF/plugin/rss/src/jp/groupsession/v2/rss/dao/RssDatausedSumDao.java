package jp.groupsession.v2.rss.dao;

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
import jp.groupsession.v2.rss.model.RssDatausedSumModel;

/**
 * <p>RSS_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RssDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public RssDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssDatausedSumDao(Connection con) {
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
            sql.addSql("drop table RSS_DATAUSED_SUM");

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
            sql.addSql(" create table RSS_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   RSS_DATA_SIZE bigint not null");
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
     * <p>Insert RSS_DATAUSED_SUM Data Bindding JavaBean
     * @param bean RSS_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RssDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   RSS_DATA_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getRssDataSize());
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
     * <p>Update RSS_DATAUSED_SUM Data Bindding JavaBean
     * @param bean RSS_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RssDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   RSS_DATA_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getRssDataSize());

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
     * <p>Delete RSS_DATAUSED_SUM
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
            sql.addSql("   RSS_DATAUSED_SUM");

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
     * <p>Select RSS_DATAUSED_SUM All Data
     * @return List in RSS_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<RssDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RssDatausedSumModel> ret = new ArrayList<RssDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   RSS_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssDatausedSumFromRs(rs));
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
    public RssDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   RSS_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssDatausedSumFromRs(rs);
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
     * <br>[機  能] 使用データサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの合計
     * @throws SQLException
     */
    public RssDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(RSS_DATA_SIZE) as SUM_RSS_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RSS_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RssDatausedSumModel();
                ret.setRssDataSize(rs.getLong("SUM_RSS_DATA_SIZE"));
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
     * <p>Create RSS_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private RssDatausedSumModel __getRssDatausedSumFromRs(ResultSet rs) throws SQLException {
        RssDatausedSumModel bean = new RssDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setRssDataSize(rs.getLong("RSS_DATA_SIZE"));
        return bean;
    }
}
