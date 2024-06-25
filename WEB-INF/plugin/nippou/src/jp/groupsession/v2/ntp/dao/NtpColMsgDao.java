package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpColMsgModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_COL_MSG Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpColMsgDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpColMsgDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpColMsgDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpColMsgDao(Connection con) {
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
            sql.addSql("drop table NTP_COL_MSG");

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
            sql.addSql(" create table NTP_COL_MSG (");
            sql.addSql("   NCM_ID NUMBER(10,0) not null,");
            sql.addSql("   NCM_MSG varchar(50),");
            sql.addSql("   NCM_AUID NUMBER(10,0),");
            sql.addSql("   NCM_ADATE varchar(23) not null,");
            sql.addSql("   NCM_EUID NUMBER(10,0),");
            sql.addSql("   NCM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NCM_ID)");
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
     * <p>Insert NTP_COL_MSG Data Bindding JavaBean
     * @param bean NTP_COL_MSG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpColMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_COL_MSG(");
            sql.addSql("   NCM_ID,");
            sql.addSql("   NCM_MSG,");
            sql.addSql("   NCM_AUID,");
            sql.addSql("   NCM_ADATE,");
            sql.addSql("   NCM_EUID,");
            sql.addSql("   NCM_EDATE");
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
            sql.addIntValue(bean.getNcmId());
            sql.addStrValue(bean.getNcmMsg());
            sql.addIntValue(bean.getNcmAuid());
            sql.addDateValue(bean.getNcmAdate());
            sql.addIntValue(bean.getNcmEuid());
            sql.addDateValue(bean.getNcmEdate());
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
     * <p>Update NTP_COL_MSG Data Bindding JavaBean
     * @param bean NTP_COL_MSG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpColMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_COL_MSG");
            sql.addSql(" set ");
            sql.addSql("   NCM_MSG=?,");
            sql.addSql("   NCM_AUID=?,");
            sql.addSql("   NCM_ADATE=?,");
            sql.addSql("   NCM_EUID=?,");
            sql.addSql("   NCM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NCM_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNcmMsg());
            sql.addIntValue(bean.getNcmAuid());
            sql.addDateValue(bean.getNcmAdate());
            sql.addIntValue(bean.getNcmEuid());
            sql.addDateValue(bean.getNcmEdate());
            //where
            sql.addIntValue(bean.getNcmId());

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
     * <p>Select NTP_COL_MSG All Data
     * @return List in NTP_COL_MSGModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpColMsgModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpColMsgModel> ret = new ArrayList<NtpColMsgModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NCM_ID,");
            sql.addSql("   NCM_MSG,");
            sql.addSql("   NCM_AUID,");
            sql.addSql("   NCM_ADATE,");
            sql.addSql("   NCM_EUID,");
            sql.addSql("   NCM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_COL_MSG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpColMsgFromRs(rs));
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
     * <p>Select NTP_COL_MSG
     * @param ncmId NCM_ID
     * @return NTP_COL_MSGModel
     * @throws SQLException SQL実行例外
     */
    public NtpColMsgModel select(int ncmId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpColMsgModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NCM_ID,");
            sql.addSql("   NCM_MSG,");
            sql.addSql("   NCM_AUID,");
            sql.addSql("   NCM_ADATE,");
            sql.addSql("   NCM_EUID,");
            sql.addSql("   NCM_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_COL_MSG");
            sql.addSql(" where ");
            sql.addSql("   NCM_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ncmId);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpColMsgFromRs(rs);
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
     * <p>Select SCH_COL_MSG All Msg
     * @return List in SCH_COL_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectMsg() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NCM_ID,");
            sql.addSql("   NCM_MSG,");
            sql.addSql("   NCM_AUID,");
            sql.addSql("   NCM_ADATE,");
            sql.addSql("   NCM_EUID,");
            sql.addSql("   NCM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_COL_MSG");
            sql.addSql(" order by ");
            sql.addSql("   NCM_ID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String colMsg =
                    StringUtilHtml.transToHTmlForTextArea(
                            NullDefault.getString(rs.getString("NCM_MSG"), ""));
                ret.add(colMsg);
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
     * <p>Delete NTP_COL_MSG
     * @param ncmId NCM_ID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int ncmId) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_COL_MSG");
            sql.addSql(" where ");
            sql.addSql("   NCM_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ncmId);

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
     * <p>Create NTP_COL_MSG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpColMsgModel
     * @throws SQLException SQL実行例外
     */
    private NtpColMsgModel __getNtpColMsgFromRs(ResultSet rs) throws SQLException {
        NtpColMsgModel bean = new NtpColMsgModel();
        bean.setNcmId(rs.getInt("NCM_ID"));
        bean.setNcmMsg(rs.getString("NCM_MSG"));
        bean.setNcmAuid(rs.getInt("NCM_AUID"));
        bean.setNcmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NCM_ADATE")));
        bean.setNcmEuid(rs.getInt("NCM_EUID"));
        bean.setNcmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NCM_EDATE")));
        return bean;
    }
}
