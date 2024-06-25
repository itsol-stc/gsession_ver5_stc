package jp.groupsession.v2.sch.dao;

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
import jp.groupsession.v2.sch.model.SchMyviewlistModel;

/**
 * <p>SCH_MYVIEWLIST Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchMyviewlistDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchMyviewlistDao.class);

    /**
     * <p>Default Constructor
     */
    public SchMyviewlistDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchMyviewlistDao(Connection con) {
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
            sql.addSql("drop table SCH_MYVIEWLIST");

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
            sql.addSql(" create table SCH_MYVIEWLIST (");
            sql.addSql("   SMY_SID integer not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   SMY_NAME varchar(50) not null,");
            sql.addSql("   SMY_BIKO varchar(1000),");
            sql.addSql("   SMY_SORT integer not null,");
            sql.addSql("   MGP_AUID integer not null,");
            sql.addSql("   MGP_ADATE timestamp not null,");
            sql.addSql("   MGP_EUID integer not null,");
            sql.addSql("   MGP_EDATE timestamp not null,");
            sql.addSql("   primary key (SMY_SID)");
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
     * <p>Insert SCH_MYVIEWLIST Data Bindding JavaBean
     * @param bean SCH_MYVIEWLIST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchMyviewlistModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_MYVIEWLIST(");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SMY_NAME,");
            sql.addSql("   SMY_BIKO,");
            sql.addSql("   SMY_SORT,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
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
            sql.addIntValue(bean.getSmySid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSmyName());
            sql.addStrValue(bean.getSmyBiko());
            sql.addIntValue(bean.getSmySort());
            sql.addIntValue(bean.getMgpAuid());
            sql.addDateValue(bean.getMgpAdate());
            sql.addIntValue(bean.getMgpEuid());
            sql.addDateValue(bean.getMgpEdate());
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
     * <p>Update SCH_MYVIEWLIST Data Bindding JavaBean
     * @param bean SCH_MYVIEWLIST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchMyviewlistModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_MYVIEWLIST");
            sql.addSql(" set ");
            sql.addSql("   SMY_NAME=?,");
            sql.addSql("   SMY_BIKO=?,");
            sql.addSql("   MGP_EUID=?,");
            sql.addSql("   MGP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SMY_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSmyName());
            sql.addStrValue(bean.getSmyBiko());
            sql.addIntValue(bean.getMgpEuid());
            sql.addDateValue(bean.getMgpEdate());
            //where
            sql.addIntValue(bean.getSmySid());
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
     * <p>Select SCH_MYVIEWLIST All Data
     * @return List in SCH_MYVIEWLISTModel
     * @throws SQLException SQL実行例外
     */
    public List<SchMyviewlistModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchMyviewlistModel> ret = new ArrayList<SchMyviewlistModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SMY_NAME,");
            sql.addSql("   SMY_BIKO,");
            sql.addSql("   SMY_SORT,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_MYVIEWLIST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchMyviewlistFromRs(rs));
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
     * <p>Select SCH_MYVIEWLIST
     * @param usrSid USR_SID
     * @return SCH_MYVIEWLISTModel
     * @throws SQLException SQL実行例外
     */
    public List<SchMyviewlistModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SchMyviewlistModel> ret = new ArrayList<SchMyviewlistModel>();
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SMY_NAME,");
            sql.addSql("   SMY_BIKO,");
            sql.addSql("   SMY_SORT,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_MYVIEWLIST");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   SMY_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchMyviewlistFromRs(rs));
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
     * <br>[機  能] 指定したSIDの表示リスト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示リストSID
     * @param usrSid ユーザSID
     * @return 表示リスト情報
     * @throws SQLException SQL実行時例外
     */
    public SchMyviewlistModel select(int smySid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SchMyviewlistModel ret = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SMY_NAME,");
            sql.addSql("   SMY_BIKO,");
            sql.addSql("   SMY_SORT,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_MYVIEWLIST");
            sql.addSql(" where ");
            sql.addSql("   SMY_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   SMY_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smySid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchMyviewlistFromRs(rs);
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
     * <br>[機  能] ソート順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return ソート順の最大値
     * @throws SQLException SQL実行時例外
     */
    public int getMaxSort(int usrSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 1;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(SMY_SORT) SMY_SORT");
            sql.addSql(" from");
            sql.addSql("   SCH_MYVIEWLIST");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("SMY_SORT");
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
     * <p>Delete SCH_MYVIEWLIST
     * @param smySid SMY_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int smySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_MYVIEWLIST");
            sql.addSql(" where ");
            sql.addSql("   SMY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smySid);

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
     * <p>Create SCH_MYVIEWLIST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchMyviewlistModel
     * @throws SQLException SQL実行例外
     */
    private SchMyviewlistModel __getSchMyviewlistFromRs(ResultSet rs) throws SQLException {
        SchMyviewlistModel bean = new SchMyviewlistModel();
        bean.setSmySid(rs.getInt("SMY_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSmyName(rs.getString("SMY_NAME"));
        bean.setSmyBiko(rs.getString("SMY_BIKO"));
        bean.setSmySort(rs.getInt("SMY_SORT"));
        bean.setMgpAuid(rs.getInt("MGP_AUID"));
        bean.setMgpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MGP_ADATE")));
        bean.setMgpEuid(rs.getInt("MGP_EUID"));
        bean.setMgpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MGP_EDATE")));
        return bean;
    }
}
