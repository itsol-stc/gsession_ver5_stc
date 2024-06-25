package jp.groupsession.v2.ip.dao;

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
import jp.groupsession.v2.ip.model.IpkDatausedSumModel;

/**
 * <p>IPK_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class IpkDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkDatausedSumDao(Connection con) {
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
            sql.addSql("drop table IPK_DATAUSED_SUM");

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
            sql.addSql(" create table IPK_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   IPK_DATA_SIZE bigint not null");
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
     * <p>Insert IPK_DATAUSED_SUM Data Bindding JavaBean
     * @param bean IPK_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" IPK_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   IPK_DATA_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getIpkDataSize());
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
     * <p>Update IPK_DATAUSED_SUM Data Bindding JavaBean
     * @param bean IPK_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(IpkDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   IPK_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   IPK_DATA_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getIpkDataSize());

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
     * <p>Delete IPK_DATAUSED_SUM
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
            sql.addSql("   IPK_DATAUSED_SUM");

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
     * <p>Select IPK_DATAUSED_SUM All Data
     * @return List in IPK_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<IpkDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkDatausedSumModel> ret = new ArrayList<IpkDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   IPK_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   IPK_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkDatausedSumFromRs(rs));
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
    public IpkDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        IpkDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   IPK_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   IPK_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getIpkDatausedSumFromRs(rs);
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
    public IpkDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        IpkDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(IPK_DATA_SIZE) as SUM_IPK_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   IPK_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new IpkDatausedSumModel();
                ret.setIpkDataSize(rs.getLong("SUM_IPK_DATA_SIZE"));
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
     * <p>Create IPK_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created IpkDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private IpkDatausedSumModel __getIpkDatausedSumFromRs(ResultSet rs) throws SQLException {
        IpkDatausedSumModel bean = new IpkDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setIpkDataSize(rs.getLong("IPK_DATA_SIZE"));
        return bean;
    }
}
