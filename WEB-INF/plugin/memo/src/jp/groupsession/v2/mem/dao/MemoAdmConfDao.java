package jp.groupsession.v2.mem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.mem.model.MemoAdmConfModel;

/**
 * <p>MEMO_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoAdmConfDao(Connection con) {
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
            sql.addSql("drop table MEMO_ADM_CONF");

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
            sql.addSql(" create table MEMO_ADM_CONF (");
            sql.addSql("   MAC_ATDEL_KBN integer not null,");
            sql.addSql("   MAC_ATDEL_Y integer,");
            sql.addSql("   MAC_ATDEL_M integer,");
            sql.addSql("   MAC_AUID integer not null,");
            sql.addSql("   MAC_ADATE timestamp not null,");
            sql.addSql("   MAC_EUID integer not null,");
            sql.addSql("   MAC_EDATE timestamp not null");
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
     * <p>Insert MEMO_ADM_CONF Data Bindding JavaBean
     * @param bean MEMO_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_ADM_CONF(");
            sql.addSql("   MAC_ATDEL_KBN,");
            sql.addSql("   MAC_ATDEL_Y,");
            sql.addSql("   MAC_ATDEL_M,");
            sql.addSql("   MAC_AUID,");
            sql.addSql("   MAC_ADATE,");
            sql.addSql("   MAC_EUID,");
            sql.addSql("   MAC_EDATE");
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
            sql.addIntValue(bean.getMacAtdelKbn());
            sql.addIntValue(bean.getMacAtdelY());
            sql.addIntValue(bean.getMacAtdelM());
            sql.addIntValue(bean.getMacAuid());
            sql.addDateValue(bean.getMacAdate());
            sql.addIntValue(bean.getMacEuid());
            sql.addDateValue(bean.getMacEdate());
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
     * <p>Update MEMO_ADM_CONF Data Bindding JavaBean
     * @param bean MEMO_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   MAC_ATDEL_KBN=?,");
            sql.addSql("   MAC_ATDEL_Y=?,");
            sql.addSql("   MAC_ATDEL_M=?,");
            sql.addSql("   MAC_AUID=?,");
            sql.addSql("   MAC_ADATE=?,");
            sql.addSql("   MAC_EUID=?,");
            sql.addSql("   MAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMacAtdelKbn());
            sql.addIntValue(bean.getMacAtdelY());
            sql.addIntValue(bean.getMacAtdelM());
            sql.addIntValue(bean.getMacAuid());
            sql.addDateValue(bean.getMacAdate());
            sql.addIntValue(bean.getMacEuid());
            sql.addDateValue(bean.getMacEdate());

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
     * <br>[機  能] 管理者設定 自動削除設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean MEMO_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDelConf(MemoAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   MAC_ATDEL_KBN=?,");
            sql.addSql("   MAC_ATDEL_Y=?,");
            sql.addSql("   MAC_ATDEL_M=?,");
            sql.addSql("   MAC_EUID=?,");
            sql.addSql("   MAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMacAtdelKbn());
            sql.addIntValue(bean.getMacAtdelY());
            sql.addIntValue(bean.getMacAtdelM());
            sql.addIntValue(bean.getMacEuid());
            sql.addDateValue(bean.getMacEdate());

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
     * <p>Select MEMO_ADM_CONF All Data
     * @return List in MEMO_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public MemoAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoAdmConfModel macModel = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MAC_ATDEL_KBN,");
            sql.addSql("   MAC_ATDEL_Y,");
            sql.addSql("   MAC_ATDEL_M,");
            sql.addSql("   MAC_AUID,");
            sql.addSql("   MAC_ADATE,");
            sql.addSql("   MAC_EUID,");
            sql.addSql("   MAC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                macModel = __getMemoAdmConfFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return macModel;
    }

    /**
     * <p>Create MEMO_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private MemoAdmConfModel __getMemoAdmConfFromRs(ResultSet rs) throws SQLException {
        MemoAdmConfModel bean = new MemoAdmConfModel();
        bean.setMacAtdelKbn(rs.getInt("MAC_ATDEL_KBN"));
        bean.setMacAtdelY(rs.getInt("MAC_ATDEL_Y"));
        bean.setMacAtdelM(rs.getInt("MAC_ATDEL_M"));
        bean.setMacAuid(rs.getInt("MAC_AUID"));
        bean.setMacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAC_ADATE")));
        bean.setMacEuid(rs.getInt("MAC_EUID"));
        bean.setMacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAC_EDATE")));
        return bean;
    }
}
