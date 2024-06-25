package jp.groupsession.v2.cht.dao;

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
import jp.groupsession.v2.cht.model.ChtLogCountModel;

/**
 * <p>CHT_LOG_COUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtLogCountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtLogCountDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtLogCountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtLogCountDao(Connection con) {
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
            sql.addSql("drop table CHT_LOG_COUNT");

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
            sql.addSql(" create table CHT_LOG_COUNT (");
            sql.addSql("   CLC_USR_SID integer not null,");
            sql.addSql("   CLC_CHAT_KBN integer not null,");
            sql.addSql("   CLC_DATE timestamp not null");
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
     * <p>Insert CHT_LOG_COUNT Data Bindding JavaBean
     * @param bean CHT_LOG_COUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_LOG_COUNT(");
            sql.addSql("   CLC_USR_SID,");
            sql.addSql("   CLC_CHAT_KBN,");
            sql.addSql("   CLC_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClcUsrSid());
            sql.addIntValue(bean.getClcChatKbn());
            sql.addDateValue(bean.getClcDate());
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
     * <p>Update CHT_LOG_COUNT Data Bindding JavaBean
     * @param bean CHT_LOG_COUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_LOG_COUNT");
            sql.addSql(" set ");
            sql.addSql("   CLC_USR_SID=?,");
            sql.addSql("   CLC_CHAT_KBN=?,");
            sql.addSql("   CLC_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClcUsrSid());
            sql.addIntValue(bean.getClcChatKbn());
            sql.addDateValue(bean.getClcDate());

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
     * <p>delete CHT_LOG_COUNT Data Bindding JavaBean
     * @param date 現在日時
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        UDate preDay = date.cloneUDate();
        preDay.addDay(-1);
        preDay.setZeroHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_LOG_COUNT");
            sql.addSql(" where ");
            sql.addSql("   CLC_DATE <= ?");
            sql.addDateValue(preDay);

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select CHT_LOG_COUNT All Data
     * @return List in CHT_LOG_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtLogCountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtLogCountModel> ret = new ArrayList<ChtLogCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLC_USR_SID,");
            sql.addSql("   CLC_CHAT_KBN,");
            sql.addSql("   CLC_DATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_LOG_COUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtLogCountFromRs(rs));
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
     * <p>Create CHT_LOG_COUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtLogCountModel
     * @throws SQLException SQL実行例外
     */
    private ChtLogCountModel __getChtLogCountFromRs(ResultSet rs) throws SQLException {
        ChtLogCountModel bean = new ChtLogCountModel();
        bean.setClcUsrSid(rs.getInt("CLC_USR_SID"));
        bean.setClcChatKbn(rs.getInt("CLC_CHAT_KBN"));
        bean.setClcDate(UDate.getInstanceTimestamp(rs.getTimestamp("CLC_DATE")));
        return bean;
    }
}
