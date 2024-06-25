package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileCrtConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>FILE_CRT_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCrtConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCrtConfDao.class);

    /**
     * <p>Default Constructor
     */
    public FileCrtConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileCrtConfDao(Connection con) {
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
            sql.addSql("drop table FILE_CRT_CONF");

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
            sql.addSql(" create table FILE_CRT_CONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_KBN NUMBER(10,0) not null");
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
     * <p>Insert FILE_CRT_CONF Data Bindding JavaBean
     * @param bean FILE_CRT_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileCrtConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CRT_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getUsrKbn());
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
     * <p>Update FILE_CRT_CONF Data Bindding JavaBean
     * @param bean FILE_CRT_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileCrtConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CRT_CONF");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   USR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getUsrKbn());

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
     * <p>Select FILE_CRT_CONF All Data
     * @return List in FILE_CRT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCrtConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileCrtConfModel> ret = new ArrayList<FileCrtConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_CRT_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCrtConfFromRs(rs));
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
     * <p>ユーザ区分を指定してデータを取得する。
     * @param usrKbn USR_KBN
     * @return List in FILE_CRT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCrtConfModel> select(int usrKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCrtConfModel> ret = new ArrayList<FileCrtConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_CRT_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_KBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(usrKbn);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCrtConfFromRs(rs));
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
     * <p>ユーザSIDを指定して作成権限が有るか判定する。
     * @param userSid USR_SID
     * @return boolean true:作成権限有り　false:作成権限無し
     * @throws SQLException SQL実行例外
     */
    public boolean isCreateAuth(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    USR_SID,");
            sql.addSql("    USR_KBN");
            sql.addSql("  from");
            sql.addSql("    FILE_CRT_CONF");
            sql.addSql("  where");
            sql.addSql("  (");
            sql.addSql("    USR_SID = ?");
            sql.addSql("  and");
            sql.addSql("    USR_KBN = ?");
            sql.addSql("  )");
            sql.addSql("  or");
            sql.addSql("  (");
            sql.addSql("    USR_SID in (");
            sql.addSql("    select GRP_SID from CMN_BELONGM where USR_SID=?");
            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    USR_KBN = ?");
            sql.addSql("  )");
            sql.addSql("");
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>全レコード情報を削除する。
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   FILE_CRT_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create FILE_CRT_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCrtConfModel
     * @throws SQLException SQL実行例外
     */
    private FileCrtConfModel __getFileCrtConfFromRs(ResultSet rs) throws SQLException {
        FileCrtConfModel bean = new FileCrtConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrKbn(rs.getInt("USR_KBN"));
        return bean;
    }
}
