package jp.groupsession.v2.bmk.dao;

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
import jp.groupsession.v2.bmk.model.BmkGconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_GCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkGconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkGconfDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkGconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkGconfDao(Connection con) {
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
            sql.addSql("drop table BMK_GCONF");

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
            sql.addSql(" create table BMK_GCONF (");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   BGC_EDIT NUMBER(10,0) not null,");
            sql.addSql("   BGC_AUID NUMBER(10,0) not null,");
            sql.addSql("   BGC_ADATE varchar(23) not null,");
            sql.addSql("   BGC_EUID NUMBER(10,0) not null,");
            sql.addSql("   BGC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (GRP_SID)");
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
     * <p>Insert BMK_GCONF Data Bindding JavaBean
     * @param bean BMK_GCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkGconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_GCONF(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGC_EDIT,");
            sql.addSql("   BGC_AUID,");
            sql.addSql("   BGC_ADATE,");
            sql.addSql("   BGC_EUID,");
            sql.addSql("   BGC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getBgcEdit());
            sql.addIntValue(bean.getBgcAuid());
            sql.addDateValue(bean.getBgcAdate());
            sql.addIntValue(bean.getBgcEuid());
            sql.addDateValue(bean.getBgcEdate());
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
     * <p>Update BMK_GCONF Data Bindding JavaBean
     * @param bean BMK_GCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkGconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_GCONF");
            sql.addSql(" set ");
            sql.addSql("   BGC_EDIT=?,");
            sql.addSql("   BGC_EUID=?,");
            sql.addSql("   BGC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBgcEdit());
            sql.addIntValue(bean.getBgcEuid());
            sql.addDateValue(bean.getBgcEdate());
            //where
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select BMK_GCONF All Data
     * @return List in BMK_GCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkGconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkGconfModel> ret = new ArrayList<BmkGconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGC_EDIT,");
            sql.addSql("   BGC_AUID,");
            sql.addSql("   BGC_ADATE,");
            sql.addSql("   BGC_EUID,");
            sql.addSql("   BGC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_GCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkGconfFromRs(rs));
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
     * <p>Select BMK_GCONF All Data
     * @return List in BMK_GCONFModel
     * @param gsid グループSID
     * @throws SQLException SQL実行例外
     */
    public BmkGconfModel selectGConf(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkGconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGC_EDIT,");
            sql.addSql("   BGC_AUID,");
            sql.addSql("   BGC_ADATE,");
            sql.addSql("   BGC_EUID,");
            sql.addSql("   BGC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_GCONF");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkGconfFromRs(rs);
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
     * <p>Select BMK_GCONF
     * @param grpSid GRP_SID
     * @return BMK_GCONFModel
     * @throws SQLException SQL実行例外
     */
    public BmkGconfModel select(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkGconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGC_EDIT,");
            sql.addSql("   BGC_AUID,");
            sql.addSql("   BGC_ADATE,");
            sql.addSql("   BGC_EUID,");
            sql.addSql("   BGC_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_GCONF");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkGconfFromRs(rs);
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
     * <p>Delete BMK_GCONF
     * @param grpSid GRP_SID
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int delete(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_GCONF");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

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
     * <p>Create BMK_GCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkGconfModel
     * @throws SQLException SQL実行例外
     */
    private BmkGconfModel __getBmkGconfFromRs(ResultSet rs) throws SQLException {
        BmkGconfModel bean = new BmkGconfModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setBgcEdit(rs.getInt("BGC_EDIT"));
        bean.setBgcAuid(rs.getInt("BGC_AUID"));
        bean.setBgcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BGC_ADATE")));
        bean.setBgcEuid(rs.getInt("BGC_EUID"));
        bean.setBgcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BGC_EDATE")));
        return bean;
    }
}
