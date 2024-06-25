package jp.groupsession.v2.mem.dao;

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
import jp.groupsession.v2.mem.model.MemoPriConfModel;

/**
 * <p>MEMO_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoPriConfDao(Connection con) {
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
            sql.addSql("drop table MEMO_PRI_CONF");

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
            sql.addSql(" create table MEMO_PRI_CONF (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   MPC_DSP_MODE integer not null,");
            sql.addSql("   MPC_AUID integer not null,");
            sql.addSql("   MPC_ADATE timestamp not null,");
            sql.addSql("   MPC_EUID integer not null,");
            sql.addSql("   MPC_EDATE timestamp not null,");
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
     * <p>Insert MEMO_PRI_CONF Data Bindding JavaBean
     * @param bean MEMO_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MPC_DSP_MODE,");
            sql.addSql("   MPC_AUID,");
            sql.addSql("   MPC_ADATE,");
            sql.addSql("   MPC_EUID,");
            sql.addSql("   MPC_EDATE");
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
            sql.addIntValue(bean.getMpcDspMode());
            sql.addIntValue(bean.getMpcAuid());
            sql.addDateValue(bean.getMpcAdate());
            sql.addIntValue(bean.getMpcEuid());
            sql.addDateValue(bean.getMpcEdate());
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
     * <br>[機  能] メモの表示モードを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param model MemoPriConfModel
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDspMode(MemoPriConfModel model) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   MPC_DSP_MODE=?,");
            sql.addSql("   MPC_EUID=?,");
            sql.addSql("   MPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getMpcDspMode());
            sql.addIntValue(model.getMpcEuid());
            sql.addDateValue(model.getMpcEdate());
            //where
            sql.addIntValue(model.getUsrSid());

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
     * <p>Update MEMO_PRI_CONF Data Bindding JavaBean
     * @param bean MEMO_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   MPC_DSP_MODE=?,");
            sql.addSql("   MPC_AUID=?,");
            sql.addSql("   MPC_ADATE=?,");
            sql.addSql("   MPC_EUID=?,");
            sql.addSql("   MPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMpcDspMode());
            sql.addIntValue(bean.getMpcAuid());
            sql.addDateValue(bean.getMpcAdate());
            sql.addIntValue(bean.getMpcEuid());
            sql.addDateValue(bean.getMpcEdate());
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
     * <p>Select MEMO_PRI_CONF All Data
     * @return List in MEMO_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoPriConfModel> ret = new ArrayList<MemoPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MPC_DSP_MODE,");
            sql.addSql("   MPC_AUID,");
            sql.addSql("   MPC_ADATE,");
            sql.addSql("   MPC_EUID,");
            sql.addSql("   MPC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoPriConfFromRs(rs));
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
     * <p>Select MEMO_PRI_CONF
     * @param usrSid USR_SID
     * @return MEMO_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public MemoPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MPC_DSP_MODE,");
            sql.addSql("   MPC_AUID,");
            sql.addSql("   MPC_ADATE,");
            sql.addSql("   MPC_EUID,");
            sql.addSql("   MPC_EDATE");
            sql.addSql(" from");
            sql.addSql("   MEMO_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoPriConfFromRs(rs);
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
     * <p>Delete MEMO_PRI_CONF
     * @param usrSid USR_SID
     * @return 削除件数
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
            sql.addSql("   MEMO_PRI_CONF");
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
     * <p>Create MEMO_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoPriConfModel
     * @throws SQLException SQL実行例外
     */
    private MemoPriConfModel __getMemoPriConfFromRs(ResultSet rs) throws SQLException {
        MemoPriConfModel bean = new MemoPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMpcDspMode(rs.getInt("MPC_DSP_MODE"));
        bean.setMpcAuid(rs.getInt("MPC_AUID"));
        bean.setMpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MPC_ADATE")));
        bean.setMpcEuid(rs.getInt("MPC_EUID"));
        bean.setMpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MPC_EDATE")));
        return bean;
    }
}
