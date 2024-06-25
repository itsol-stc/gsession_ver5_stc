package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.model.BmkAconfModel;

/**
 * <p>BMK_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkAconfDao(Connection con) {
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
            sql.addSql("drop table BMK_ACONF");

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
            sql.addSql(" create table BMK_ACONF (");
            sql.addSql("   BAC_PUB_EDIT integer not null,");
            sql.addSql("   BAC_GRP_EDIT integer not null,");
            sql.addSql("   BAC_LIMIT integer not null,");
            sql.addSql("   BAC_AUID integer not null,");
            sql.addSql("   BAC_ADATE varchar(23) not null,");
            sql.addSql("   BAC_EUID integer not null,");
            sql.addSql("   BAC_EDATE varchar(23) not null");
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
     * <p>Insert BMK_ACONF Data Bindding JavaBean
     * @param bean BMK_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_ACONF(");
            sql.addSql("   BAC_PUB_EDIT,");
            sql.addSql("   BAC_GRP_EDIT,");
            sql.addSql("   BAC_LIMIT,");
            sql.addSql("   BAC_AUID,");
            sql.addSql("   BAC_ADATE,");
            sql.addSql("   BAC_EUID,");
            sql.addSql("   BAC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacPubEdit());
            sql.addIntValue(bean.getBacGrpEdit());
            sql.addIntValue(bean.getBacLimit());
            sql.addIntValue(bean.getBacAuid());
            sql.addDateValue(bean.getBacAdate());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());
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
     * <p>Update BMK_ACONF Data Bindding JavaBean
     * @param bean BMK_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_ACONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_PUB_EDIT=?,");
            sql.addSql("   BAC_GRP_EDIT=?,");
            sql.addSql("   BAC_LIMIT=?,");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacPubEdit());
            sql.addIntValue(bean.getBacGrpEdit());
            sql.addIntValue(bean.getBacLimit());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());

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
     * <br>[機  能] ブックマーク 管理者設定を更新する
     * <br>[解  説]
     * <br>[備  考] [ブックマーク登録時情報取得制限]以外の設定を更新
     * @param bean BMK_ACONF All Data
     * @return 更新件数
     * @throws SQLException
     */
    public int updateAuthority(BmkAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_ACONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_PUB_EDIT=?,");
            sql.addSql("   BAC_GRP_EDIT=?,");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacPubEdit());
            sql.addIntValue(bean.getBacGrpEdit());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());

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
     * <br>[機  能] ブックマーク 管理者設定の[ブックマーク登録時情報取得制限]を更新する
     * <br>[解  説]
     * <br>[備  考] 以外の設定を更新
     * @param bean BMK_ACONF All Data
     * @return 更新件数
     * @throws SQLException
     */
    public int updateInfoLimit(BmkAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_ACONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_LIMIT=?,");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacLimit());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());

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
     * <p>Select BMK_ACONF All Data
     * @return List in BMK_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkAconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkAconfModel> ret = new ArrayList<BmkAconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BAC_PUB_EDIT,");
            sql.addSql("   BAC_GRP_EDIT,");
            sql.addSql("   BAC_LIMIT,");
            sql.addSql("   BAC_AUID,");
            sql.addSql("   BAC_ADATE,");
            sql.addSql("   BAC_EUID,");
            sql.addSql("   BAC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkAconfFromRs(rs));
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
     * <p>Select BMK_ACONF All Data
     * @return List in BMK_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public BmkAconfModel selectAConf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkAconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BAC_PUB_EDIT,");
            sql.addSql("   BAC_GRP_EDIT,");
            sql.addSql("   BAC_LIMIT,");
            sql.addSql("   BAC_AUID,");
            sql.addSql("   BAC_ADATE,");
            sql.addSql("   BAC_EUID,");
            sql.addSql("   BAC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkAconfFromRs(rs);
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
     * <p>Create BMK_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkAconfModel
     * @throws SQLException SQL実行例外
     */
    private BmkAconfModel __getBmkAconfFromRs(ResultSet rs) throws SQLException {
        BmkAconfModel bean = new BmkAconfModel();
        bean.setBacPubEdit(rs.getInt("BAC_PUB_EDIT"));
        bean.setBacGrpEdit(rs.getInt("BAC_GRP_EDIT"));
        bean.setBacLimit(rs.getInt("BAC_LIMIT"));
        bean.setBacAuid(rs.getInt("BAC_AUID"));
        bean.setBacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BAC_ADATE")));
        bean.setBacEuid(rs.getInt("BAC_EUID"));
        bean.setBacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BAC_EDATE")));
        return bean;
    }
}
