package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpMikomidoMsgModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_MIKOMIDO_MSG Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpMikomidoMsgDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpMikomidoMsgDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpMikomidoMsgDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpMikomidoMsgDao(Connection con) {
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
            sql.addSql("drop table NTP_MIKOMIDO_MSG");

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
            sql.addSql(" create table NTP_MIKOMIDO_MSG (");
            sql.addSql("   NMM_ID NUMBER(10,0) not null,");
            sql.addSql("   NMM_MSG varchar(1000),");
            sql.addSql("   NMM_AUID NUMBER(10,0),");
            sql.addSql("   NMM_ADATE varchar(23) not null,");
            sql.addSql("   NMM_EUID NUMBER(10,0),");
            sql.addSql("   NMM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NMM_ID)");
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
     * <p>Insert NTP_MIKOMIDO_MSG Data Bindding JavaBean
     * @param bean NTP_MIKOMIDO_MSG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpMikomidoMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_MIKOMIDO_MSG(");
            sql.addSql("   NMM_ID,");
            sql.addSql("   NMM_MSG,");
            sql.addSql("   NMM_AUID,");
            sql.addSql("   NMM_ADATE,");
            sql.addSql("   NMM_EUID,");
            sql.addSql("   NMM_EDATE");
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
            sql.addIntValue(bean.getNmmId());
            sql.addStrValue(bean.getNmmMsg());
            sql.addIntValue(bean.getNmmAuid());
            sql.addDateValue(bean.getNmmAdate());
            sql.addIntValue(bean.getNmmEuid());
            sql.addDateValue(bean.getNmmEdate());
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
     * <p>Update NTP_MIKOMIDO_MSG Data Bindding JavaBean
     * @param bean NTP_MIKOMIDO_MSG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpMikomidoMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_MIKOMIDO_MSG");
            sql.addSql(" set ");
            sql.addSql("   NMM_MSG=?,");
            sql.addSql("   NMM_AUID=?,");
            sql.addSql("   NMM_ADATE=?,");
            sql.addSql("   NMM_EUID=?,");
            sql.addSql("   NMM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NMM_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNmmMsg());
            sql.addIntValue(bean.getNmmAuid());
            sql.addDateValue(bean.getNmmAdate());
            sql.addIntValue(bean.getNmmEuid());
            sql.addDateValue(bean.getNmmEdate());
            //where
            sql.addIntValue(bean.getNmmId());

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
     * <p>Select NTP_MIKOMIDO_MSG All Data
     * @return List in NTP_MIKOMIDO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpMikomidoMsgModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpMikomidoMsgModel> ret = new ArrayList<NtpMikomidoMsgModel>();
        NtpMikomidoMsgModel nmmMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NMM_ID,");
            sql.addSql("   NMM_MSG,");
            sql.addSql("   NMM_AUID,");
            sql.addSql("   NMM_ADATE,");
            sql.addSql("   NMM_EUID,");
            sql.addSql("   NMM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_MIKOMIDO_MSG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {

                nmmMdl = new NtpMikomidoMsgModel();
                nmmMdl = __getNtpColMsgFromRs(rs);

                if (rs.getInt("NMM_ID") == 0) {
                    nmmMdl.setNmmName("10%");
                } else if (rs.getInt("NMM_ID") == 1) {
                    nmmMdl.setNmmName("30%");
                } else if (rs.getInt("NMM_ID") == 2) {
                    nmmMdl.setNmmName("50%");
                } else if (rs.getInt("NMM_ID") == 3) {
                    nmmMdl.setNmmName("70%");
                } else if (rs.getInt("NMM_ID") == 4) {
                    nmmMdl.setNmmName("100%");
                }

                ret.add(nmmMdl);
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
     * <p>Select NTP_MIKOMIDO_MSG
     * @param ncmId NMM_ID
     * @return NTP_MIKOMIDO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public NtpMikomidoMsgModel select(int ncmId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpMikomidoMsgModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NMM_ID,");
            sql.addSql("   NMM_MSG,");
            sql.addSql("   NMM_AUID,");
            sql.addSql("   NMM_ADATE,");
            sql.addSql("   NMM_EUID,");
            sql.addSql("   NMM_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_MIKOMIDO_MSG");
            sql.addSql(" where ");
            sql.addSql("   NMM_ID=?");

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
            sql.addSql("   NMM_ID,");
            sql.addSql("   NMM_MSG,");
            sql.addSql("   NMM_AUID,");
            sql.addSql("   NMM_ADATE,");
            sql.addSql("   NMM_EUID,");
            sql.addSql("   NMM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_MIKOMIDO_MSG");
            sql.addSql(" order by ");
            sql.addSql("   NMM_ID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String colMsg =
                    StringUtilHtml.transToHTmlForTextArea(
                            NullDefault.getString(rs.getString("NMM_MSG"), ""));
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
     * <p>Delete NTP_MIKOMIDO_MSG
     * @param ncmId NMM_ID
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
            sql.addSql("   NTP_MIKOMIDO_MSG");
            sql.addSql(" where ");
            sql.addSql("   NMM_ID=?");

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
     * <p>Create NTP_MIKOMIDO_MSG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpMikomidoMsgModel
     * @throws SQLException SQL実行例外
     */
    private NtpMikomidoMsgModel __getNtpColMsgFromRs(ResultSet rs) throws SQLException {
        NtpMikomidoMsgModel bean = new NtpMikomidoMsgModel();
        bean.setNmmId(rs.getInt("NMM_ID"));
        bean.setNmmMsg(rs.getString("NMM_MSG"));
        bean.setNmmAuid(rs.getInt("NMM_AUID"));
        bean.setNmmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NMM_ADATE")));
        bean.setNmmEuid(rs.getInt("NMM_EUID"));
        bean.setNmmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NMM_EDATE")));
        return bean;
    }
}
