package jp.groupsession.v2.anp.dao;

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
import jp.groupsession.v2.anp.model.AnpPriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ANP_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpPriConfDao(Connection con) {
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
            sql.addSql("drop table ANP_PRI_CONF");

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
            sql.addSql(" create table ANP_PRI_CONF (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   APP_MAIN_KBN integer not null,");
            sql.addSql("   APP_LIST_COUNT integer not null,");
            sql.addSql("   APP_DSP_GROUP integer not null,");
            sql.addSql("   APP_DSP_MYGROUP integer,");
            sql.addSql("   APP_ALLGROUP_FLG integer not null,");
            sql.addSql("   APP_MAILADR varchar(150),");
            sql.addSql("   APP_TELNO varchar(60),");
            sql.addSql("   APP_AUID integer not null,");
            sql.addSql("   APP_ADATE timestamp not null,");
            sql.addSql("   APP_EUID integer not null,");
            sql.addSql("   APP_EDATE timestamp not null,");
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
     * <p>Insert ANP_PRI_CONF Data Bindding JavaBean
     * @param bean ANP_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   APP_MAIN_KBN,");
            sql.addSql("   APP_LIST_COUNT,");
            sql.addSql("   APP_DSP_GROUP,");
            sql.addSql("   APP_DSP_MYGROUP,");
            sql.addSql("   APP_ALLGROUP_FLG,");
            sql.addSql("   APP_MAILADR,");
            sql.addSql("   APP_TELNO,");
            sql.addSql("   APP_AUID,");
            sql.addSql("   APP_ADATE,");
            sql.addSql("   APP_EUID,");
            sql.addSql("   APP_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getAppMainKbn());
            sql.addIntValue(bean.getAppListCount());
            sql.addIntValue(bean.getAppDspGroup());
            sql.addIntValue(bean.getAppDspMygroup());
            sql.addIntValue(bean.getAppAllGroupFlg());
            sql.addStrValue(bean.getAppMailadr());
            sql.addStrValue(bean.getAppTelno());
            sql.addIntValue(bean.getAppAuid());
            sql.addDateValue(bean.getAppAdate());
            sql.addIntValue(bean.getAppEuid());
            sql.addDateValue(bean.getAppEdate());
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
     * <p>Update ANP_PRI_CONF Data Bindding JavaBean
     * @param bean ANP_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   APP_MAIN_KBN=?,");
            sql.addSql("   APP_LIST_COUNT=?,");
            sql.addSql("   APP_DSP_GROUP=?,");
            sql.addSql("   APP_DSP_MYGROUP=?,");
            sql.addSql("   APP_ALLGROUP_FLG=?,");
            sql.addSql("   APP_MAILADR=?,");
            sql.addSql("   APP_TELNO=?,");
            sql.addSql("   APP_AUID=?,");
            sql.addSql("   APP_ADATE=?,");
            sql.addSql("   APP_EUID=?,");
            sql.addSql("   APP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAppMainKbn());
            sql.addIntValue(bean.getAppListCount());
            sql.addIntValue(bean.getAppDspGroup());
            sql.addIntValue(bean.getAppDspMygroup());
            sql.addIntValue(bean.getAppAllGroupFlg());
            sql.addStrValue(bean.getAppMailadr());
            sql.addStrValue(bean.getAppTelno());
            sql.addIntValue(bean.getAppAuid());
            sql.addDateValue(bean.getAppAdate());
            sql.addIntValue(bean.getAppEuid());
            sql.addDateValue(bean.getAppEdate());
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
     * <p>Select ANP_PRI_CONF All Data
     * @return List in ANP_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpPriConfModel> ret = new ArrayList<AnpPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   APP_MAIN_KBN,");
            sql.addSql("   APP_LIST_COUNT,");
            sql.addSql("   APP_DSP_GROUP,");
            sql.addSql("   APP_DSP_MYGROUP,");
            sql.addSql("   APP_ALLGROUP_FLG,");
            sql.addSql("   APP_MAILADR,");
            sql.addSql("   APP_TELNO,");
            sql.addSql("   APP_AUID,");
            sql.addSql("   APP_ADATE,");
            sql.addSql("   APP_EUID,");
            sql.addSql("   APP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpPriConfFromRs(rs));
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
     * <p>Select ANP_PRI_CONF
     * @param usrSid USR_SID
     * @return ANP_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public AnpPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   APP_MAIN_KBN,");
            sql.addSql("   APP_LIST_COUNT,");
            sql.addSql("   APP_DSP_GROUP,");
            sql.addSql("   APP_DSP_MYGROUP,");
            sql.addSql("   APP_ALLGROUP_FLG,");
            sql.addSql("   APP_MAILADR,");
            sql.addSql("   APP_TELNO,");
            sql.addSql("   APP_AUID,");
            sql.addSql("   APP_ADATE,");
            sql.addSql("   APP_EUID,");
            sql.addSql("   APP_EDATE");
            sql.addSql(" from");
            sql.addSql("   ANP_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpPriConfFromRs(rs);
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
     * <p>Delete ANP_PRI_CONF
     * @param usrSid USR_SID
     * @return 更新件数
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
            sql.addSql("   ANP_PRI_CONF");
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
     * <p>個人設定（表示設定）を更新
     * @param  bean モデル
     * @throws SQLException SQL実行例外
     */
    public void doUpdateAnp040kn(AnpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        boolean commit = false;
        con = getCon();

        try {
            con.setAutoCommit(false);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   APP_MAIN_KBN=?,");
            sql.addSql("   APP_LIST_COUNT=?,");
            sql.addSql("   APP_DSP_GROUP=?,");
            sql.addSql("   APP_DSP_MYGROUP=?,");
            sql.addSql("   APP_ALLGROUP_FLG=?,");
            sql.addSql("   APP_EUID=?,");
            sql.addSql("   APP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAppMainKbn());
            sql.addIntValue(bean.getAppListCount());
            sql.addIntValue(bean.getAppDspGroup());
            sql.addIntValue(bean.getAppDspMygroup());
            sql.addIntValue(bean.getAppAllGroupFlg());
            sql.addIntValue(bean.getAppEuid());
            sql.addDateValue(new UDate());
            //where
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            commit = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>個人設定（連絡先設定）を更新
     * @param  bean モデル
     * @throws SQLException SQL実行例外
     */
    public void doUpdateAnp050kn(AnpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        boolean commit = false;
        Connection con = null;
        con = getCon();

        try {
            con.setAutoCommit(false);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   APP_MAILADR=?,");
            sql.addSql("   APP_TELNO=?,");
            sql.addSql("   APP_EUID=?,");
            sql.addSql("   APP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAppMailadr());
            sql.addStrValue(bean.getAppTelno());
            sql.addIntValue(bean.getAppEuid());
            sql.addDateValue(new UDate());
            //where
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            commit = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create ANP_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpPriConfModel
     * @throws SQLException SQL実行例外
     */
    private AnpPriConfModel __getAnpPriConfFromRs(ResultSet rs) throws SQLException {
        AnpPriConfModel bean = new AnpPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setAppMainKbn(rs.getInt("APP_MAIN_KBN"));
        bean.setAppListCount(rs.getInt("APP_LIST_COUNT"));
        bean.setAppDspGroup(rs.getInt("APP_DSP_GROUP"));
        bean.setAppDspMygroup(rs.getInt("APP_DSP_MYGROUP"));
        bean.setAppAllGroupFlg(rs.getInt("APP_ALLGROUP_FLG"));
        bean.setAppMailadr(rs.getString("APP_MAILADR"));
        bean.setAppTelno(rs.getString("APP_TELNO"));
        bean.setAppAuid(rs.getInt("APP_AUID"));
        bean.setAppAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APP_ADATE")));
        bean.setAppEuid(rs.getInt("APP_EUID"));
        bean.setAppEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APP_EDATE")));
        return bean;
    }

}
