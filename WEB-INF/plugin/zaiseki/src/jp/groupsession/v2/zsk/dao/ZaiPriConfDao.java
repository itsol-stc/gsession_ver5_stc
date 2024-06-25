package jp.groupsession.v2.zsk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ZAI_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZaiPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public ZaiPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ZaiPriConfDao(Connection con) {
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
            sql.addSql("drop table ZAI_PRI_CONF");

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
            sql.addSql(" create table ZAI_PRI_CONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   ZIF_SID NUMBER(10,0) not null,");
            sql.addSql("   ZPC_RELOAD NUMBER(10,0) not null,");
            sql.addSql("   ZPC_AID NUMBER(10,0) not null,");
            sql.addSql("   ZPC_ADATE varchar(23) not null,");
            sql.addSql("   ZPC_EID NUMBER(10,0) not null,");
            sql.addSql("   ZPC_EDATE varchar(23) not null,");
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
     * <p>Insert ZAI_PRI_CONF Data Bindding JavaBean
     * @param bean ZAI_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ZaiPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZPC_RELOAD,");
            sql.addSql("   ZPC_AID,");
            sql.addSql("   ZPC_ADATE,");
            sql.addSql("   ZPC_EID,");
            sql.addSql("   ZPC_EDATE,");
            sql.addSql("   ZPC_SORT_KEY1,");
            sql.addSql("   ZPC_SORT_ORDER1,");
            sql.addSql("   ZPC_SORT_KEY2,");
            sql.addSql("   ZPC_SORT_ORDER2");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getZifSid());
            sql.addIntValue(bean.getZpcReload());
            sql.addIntValue(bean.getZpcAid());
            sql.addDateValue(bean.getZpcAdate());
            sql.addIntValue(bean.getZpcEid());
            sql.addDateValue(bean.getZpcEdate());
            sql.addIntValue(bean.getZpcSortKey1());
            sql.addIntValue(bean.getZpcSortOrder1());
            sql.addIntValue(bean.getZpcSortKey2());
            sql.addIntValue(bean.getZpcSortOrder2());

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
     * <p>Update ZAI_PRI_CONF Data Bindding JavaBean
     * @param bean ZAI_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int updateZpcReload(ZaiPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   ZPC_RELOAD=?,");
            sql.addSql("   ZPC_EID=?,");
            sql.addSql("   ZPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZpcReload());
            sql.addIntValue(bean.getZpcEid());
            sql.addDateValue(bean.getZpcEdate());
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
     * <p>Update ZAI_PRI_CONF Data Bindding JavaBean
     * @param bean ZAI_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int updateZifSid(ZaiPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   ZIF_SID=?,");
            sql.addSql("   ZPC_EID=?,");
            sql.addSql("   ZPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZifSid());
            sql.addIntValue(bean.getZpcEid());
            sql.addDateValue(bean.getZpcEdate());
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
     * <p>Select ZAI_PRI_CONF All Data
     * @return List in ZAI_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ZaiPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiPriConfModel> ret = new ArrayList<ZaiPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZPC_RELOAD,");
            sql.addSql("   ZPC_AID,");
            sql.addSql("   ZPC_ADATE,");
            sql.addSql("   ZPC_EID,");
            sql.addSql("   ZPC_EDATE,");
            sql.addSql("   ZPC_SORT_KEY1,");
            sql.addSql("   ZPC_SORT_ORDER1,");
            sql.addSql("   ZPC_SORT_KEY2,");
            sql.addSql("   ZPC_SORT_ORDER2");
            sql.addSql(" from ");
            sql.addSql("   ZAI_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZaiPriConfFromRs(rs));
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
     * <p>Select ZAI_PRI_CONF
     * @param bean ZAI_PRI_CONF Model
     * @return ZAI_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ZaiPriConfModel select(ZaiPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZPC_RELOAD,");
            sql.addSql("   ZPC_AID,");
            sql.addSql("   ZPC_ADATE,");
            sql.addSql("   ZPC_EID,");
            sql.addSql("   ZPC_EDATE,");
            sql.addSql("   ZPC_SORT_KEY1,");
            sql.addSql("   ZPC_SORT_ORDER1,");
            sql.addSql("   ZPC_SORT_KEY2,");
            sql.addSql("   ZPC_SORT_ORDER2");
            sql.addSql(" from");
            sql.addSql("   ZAI_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiPriConfFromRs(rs);
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
     * <p>ユーザSIDを指定し在席管理の個人設定を取得する
     * @param usrSid ユーザSID
     * @return ZAI_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ZaiPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZPC_RELOAD,");
            sql.addSql("   ZPC_AID,");
            sql.addSql("   ZPC_ADATE,");
            sql.addSql("   ZPC_EID,");
            sql.addSql("   ZPC_EDATE,");
            sql.addSql("   ZPC_SORT_KEY1,");
            sql.addSql("   ZPC_SORT_ORDER1,");
            sql.addSql("   ZPC_SORT_KEY2,");
            sql.addSql("   ZPC_SORT_ORDER2");
            sql.addSql(" from");
            sql.addSql("   ZAI_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiPriConfFromRs(rs);
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
     * <p>Delete ZAI_PRI_CONF
     * @param bean ZAI_PRI_CONF Model
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(ZaiPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 座席表メンバ表示順設定を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新値
     * @return count 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateZskMemberSetting(ZaiPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_PRI_CONF");
            sql.addSql(" set");
            sql.addSql("   ZPC_EID = ?,");
            sql.addSql("   ZPC_EDATE = ?,");
            sql.addSql("   ZPC_SORT_KEY1 = ?,");
            sql.addSql("   ZPC_SORT_ORDER1 = ?,");
            sql.addSql("   ZPC_SORT_KEY2 = ?,");
            sql.addSql("   ZPC_SORT_ORDER2 = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZpcEid());
            sql.addDateValue(bean.getZpcEdate());
            sql.addIntValue(bean.getZpcSortKey1());
            sql.addIntValue(bean.getZpcSortOrder1());
            sql.addIntValue(bean.getZpcSortKey2());
            sql.addIntValue(bean.getZpcSortOrder2());
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
     * <p>Create ZAI_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiPriConfModel
     * @throws SQLException SQL実行例外
     */
    private ZaiPriConfModel __getZaiPriConfFromRs(ResultSet rs) throws SQLException {
        ZaiPriConfModel bean = new ZaiPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setZifSid(rs.getInt("ZIF_SID"));
        bean.setZpcReload(rs.getInt("ZPC_RELOAD"));
        bean.setZpcAid(rs.getInt("ZPC_AID"));
        bean.setZpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZPC_ADATE")));
        bean.setZpcEid(rs.getInt("ZPC_EID"));
        bean.setZpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZPC_EDATE")));
        bean.setZpcSortKey1(rs.getInt("ZPC_SORT_KEY1"));
        bean.setZpcSortOrder1(rs.getInt("ZPC_SORT_ORDER1"));
        bean.setZpcSortKey2(rs.getInt("ZPC_SORT_KEY2"));
        bean.setZpcSortOrder2(rs.getInt("ZPC_SORT_ORDER2"));

        return bean;
    }
}
