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
import jp.groupsession.v2.bmk.model.BmkUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_UCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkUconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkUconfDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkUconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkUconfDao(Connection con) {
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
            sql.addSql("drop table BMK_UCONF");

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
            sql.addSql(" create table BMK_UCONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   BUC_COUNT NUMBER(10,0) not null,");
            sql.addSql("   BUC_MAIN_MY NUMBER(10,0) not null,");
            sql.addSql("   BUC_MAIN_NEW NUMBER(10,0) not null,");
            sql.addSql("   BUC_NEW_CNT NUMBER(10,0) not null,");
            sql.addSql("   BUC_AUID NUMBER(10,0) not null,");
            sql.addSql("   BUC_ADATE varchar(23) not null,");
            sql.addSql("   BUC_EUID NUMBER(10,0) not null,");
            sql.addSql("   BUC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID)");
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
     * <p>Insert BMK_UCONF Data Bindding JavaBean
     * @param bean BMK_UCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_UCONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   BUC_COUNT,");
            sql.addSql("   BUC_MAIN_MY,");
            sql.addSql("   BUC_MAIN_NEW,");
            sql.addSql("   BUC_NEW_CNT,");
            sql.addSql("   BUC_AUID,");
            sql.addSql("   BUC_ADATE,");
            sql.addSql("   BUC_EUID,");
            sql.addSql("   BUC_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBucCount());
            sql.addIntValue(bean.getBucMainMy());
            sql.addIntValue(bean.getBucMainNew());
            sql.addIntValue(bean.getBucNewCnt());
            sql.addIntValue(bean.getBucAuid());
            sql.addDateValue(bean.getBucAdate());
            sql.addIntValue(bean.getBucEuid());
            sql.addDateValue(bean.getBucEdate());
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
     * <p>Update BMK_UCONF Data Bindding JavaBean
     * @param bean BMK_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_UCONF");
            sql.addSql(" set ");
            sql.addSql("   BUC_COUNT=?,");
            sql.addSql("   BUC_MAIN_MY=?,");
            sql.addSql("   BUC_MAIN_NEW=?,");
            sql.addSql("   BUC_NEW_CNT=?,");
            sql.addSql("   BUC_EUID=?,");
            sql.addSql("   BUC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBucCount());
            sql.addIntValue(bean.getBucMainMy());
            sql.addIntValue(bean.getBucMainNew());
            sql.addIntValue(bean.getBucNewCnt());
            sql.addIntValue(bean.getBucEuid());
            sql.addDateValue(bean.getBucEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select BMK_UCONF All Data
     * @return List in BMK_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkUconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkUconfModel> ret = new ArrayList<BmkUconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   BUC_COUNT,");
            sql.addSql("   BUC_MAIN_MY,");
            sql.addSql("   BUC_MAIN_NEW,");
            sql.addSql("   BUC_NEW_CNT,");
            sql.addSql("   BUC_AUID,");
            sql.addSql("   BUC_ADATE,");
            sql.addSql("   BUC_EUID,");
            sql.addSql("   BUC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_UCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkUconfFromRs(rs));
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
     * <p>Select BMK_UCONF
     * @param usrSid USR_SID
     * @return BMK_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public BmkUconfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkUconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   BUC_COUNT,");
            sql.addSql("   BUC_MAIN_MY,");
            sql.addSql("   BUC_MAIN_NEW,");
            sql.addSql("   BUC_NEW_CNT,");
            sql.addSql("   BUC_AUID,");
            sql.addSql("   BUC_ADATE,");
            sql.addSql("   BUC_EUID,");
            sql.addSql("   BUC_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkUconfFromRs(rs);
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
     * <p>Delete BMK_UCONF
     * @param usrSid USR_SID
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <p>Create BMK_UCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkUconfModel
     * @throws SQLException SQL実行例外
     */
    private BmkUconfModel __getBmkUconfFromRs(ResultSet rs) throws SQLException {
        BmkUconfModel bean = new BmkUconfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBucCount(rs.getInt("BUC_COUNT"));
        bean.setBucMainMy(rs.getInt("BUC_MAIN_MY"));
        bean.setBucMainNew(rs.getInt("BUC_MAIN_NEW"));
        bean.setBucNewCnt(rs.getInt("BUC_NEW_CNT"));
        bean.setBucAuid(rs.getInt("BUC_AUID"));
        bean.setBucAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BUC_ADATE")));
        bean.setBucEuid(rs.getInt("BUC_EUID"));
        bean.setBucEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BUC_EDATE")));
        return bean;
    }
}
