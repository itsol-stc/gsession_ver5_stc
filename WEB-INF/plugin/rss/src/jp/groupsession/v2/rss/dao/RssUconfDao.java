package jp.groupsession.v2.rss.dao;

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
import jp.groupsession.v2.rss.model.RssUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSS_UCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RssUconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssUconfDao.class);

    /**
     * <p>Default Constructor
     */
    public RssUconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssUconfDao(Connection con) {
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
            sql.addSql("drop table RSS_UCONF");

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
            sql.addSql(" create table RSS_UCONF (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   RUC_NEW_CNT integer not null,");
            sql.addSql("   RUC_AUID integer not null,");
            sql.addSql("   RUC_ADATE timestamp not null,");
            sql.addSql("   RUC_EUID integer not null,");
            sql.addSql("   RUC_EDATE timestamp not null,");
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
     * <p>Insert RSS_UCONF Data Bindding JavaBean
     * @param bean RSS_UCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RssUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSS_UCONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   RUC_NEW_CNT,");
            sql.addSql("   RUC_AUID,");
            sql.addSql("   RUC_ADATE,");
            sql.addSql("   RUC_EUID,");
            sql.addSql("   RUC_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRucNewCnt());
            sql.addIntValue(bean.getRucAuid());
            sql.addDateValue(bean.getRucAdate());
            sql.addIntValue(bean.getRucEuid());
            sql.addDateValue(bean.getRucEdate());
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
     * <p>Update RSS_UCONF Data Bindding JavaBean
     * @param bean RSS_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RssUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_UCONF");
            sql.addSql(" set ");
            sql.addSql("   RUC_NEW_CNT=?,");
            sql.addSql("   RUC_AUID=?,");
            sql.addSql("   RUC_ADATE=?,");
            sql.addSql("   RUC_EUID=?,");
            sql.addSql("   RUC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRucNewCnt());
            sql.addIntValue(bean.getRucAuid());
            sql.addDateValue(bean.getRucAdate());
            sql.addIntValue(bean.getRucEuid());
            sql.addDateValue(bean.getRucEdate());
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
     * <p>個人設定情報を更新する
     * @param bean RSS_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUconf(RssUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_UCONF");
            sql.addSql(" set ");
            sql.addSql("   RUC_NEW_CNT=?,");
            sql.addSql("   RUC_EUID=?,");
            sql.addSql("   RUC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRucNewCnt());
            sql.addIntValue(bean.getRucEuid());
            sql.addDateValue(bean.getRucEdate());
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
     * <p>Select RSS_UCONF All Data
     * @return List in RSS_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<RssUconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RssUconfModel> ret = new ArrayList<RssUconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   RUC_NEW_CNT,");
            sql.addSql("   RUC_AUID,");
            sql.addSql("   RUC_ADATE,");
            sql.addSql("   RUC_EUID,");
            sql.addSql("   RUC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSS_UCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRssUconfFromRs(rs));
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
     * <p>Select RSS_UCONF
     * @param usrSid USR_SID
     * @return RSS_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public RssUconfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RssUconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   RUC_NEW_CNT,");
            sql.addSql("   RUC_AUID,");
            sql.addSql("   RUC_ADATE,");
            sql.addSql("   RUC_EUID,");
            sql.addSql("   RUC_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSS_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRssUconfFromRs(rs);
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
     * <p>Delete RSS_UCONF
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 件数
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
            sql.addSql("   RSS_UCONF");
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
     * <p>Create RSS_UCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RssUconfModel
     * @throws SQLException SQL実行例外
     */
    private RssUconfModel __getRssUconfFromRs(ResultSet rs) throws SQLException {
        RssUconfModel bean = new RssUconfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRucNewCnt(rs.getInt("RUC_NEW_CNT"));
        bean.setRucAuid(rs.getInt("RUC_AUID"));
        bean.setRucAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RUC_ADATE")));
        bean.setRucEuid(rs.getInt("RUC_EUID"));
        bean.setRucEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RUC_EDATE")));
        return bean;
    }
}
