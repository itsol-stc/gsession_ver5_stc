package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FilePcbOwnerModel;

/**
 * <p>FILE_PCB_OWNER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FilePcbOwnerDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilePcbOwnerDao.class);

    /**
     * <p>Default Constructor
     */
    public FilePcbOwnerDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FilePcbOwnerDao(Connection con) {
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
            sql.addSql("drop table FILE_PCB_OWNER");

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
            sql.addSql(" create table FILE_PCB_OWNER (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_KBN NUMBER(10,0) not null,");
            sql.addSql("   primary key (USR_SID,USR_KBN)");
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
     * <p>Insert FILE_PCB_OWNER Data Bindding JavaBean
     * @param bean FILE_PCB_OWNER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FilePcbOwnerModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_PCB_OWNER(");
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
     * <p>Update FILE_PCB_OWNER Data Bindding JavaBean
     * @param bean FILE_PCB_OWNER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FilePcbOwnerModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_PCB_OWNER");
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
     * <p>Select FILE_PCB_OWNER All Data
     * @return List in FILE_PCB_OWNERModel
     * @throws SQLException SQL実行例外
     */
    public List<FilePcbOwnerModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FilePcbOwnerModel> ret = new ArrayList<FilePcbOwnerModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_PCB_OWNER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFilePcbUsrFromRs(rs));
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
     * @return List in FILE_PCB_OWNERModel
     * @throws SQLException SQL実行例外
     */
    public List<FilePcbOwnerModel> select(int usrKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FilePcbOwnerModel> ret = new ArrayList<FilePcbOwnerModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_PCB_OWNER");
            sql.addSql(" where ");
            sql.addSql("   USR_KBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(usrKbn);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFilePcbUsrFromRs(rs));
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
     * <p>許可されたユーザSID一覧を取得する。
     * @return List ユーザSID一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getUserSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql(" from (");
            sql.addSql("   select");
            sql.addSql("     USR_SID");
            sql.addSql("   from");
            sql.addSql("     FILE_PCB_OWNER");
            sql.addSql("   where");
            sql.addSql("     USR_KBN = ?");
            sql.addSql(" ) as FILE_PCB_OWNER");
            sql.addSql(" left join ");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" on CMN_BELONGM.GRP_SID = FILE_PCB_OWNER.USR_SID");
            sql.addSql(" union all ");
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID");
            sql.addSql(" from (");
            sql.addSql("   select");
            sql.addSql("     USR_SID");
            sql.addSql("   from");
            sql.addSql("     FILE_PCB_OWNER");
            sql.addSql("   where");
            sql.addSql("     USR_KBN = ?");
            sql.addSql(" ) as FILE_PCB_OWNER");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_PCB_OWNER.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(GSConstFile.USER_KBN_USER);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer usrSid = Integer.valueOf(rs.getInt("USR_SID"));
                if (!ret.contains(usrSid)) { // 重複チェック
                    ret.add(usrSid);
                }
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
            sql.addSql("    FILE_PCB_OWNER");
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
            sql.addSql("   FILE_PCB_OWNER");

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
     * <p>Create FILE_PCB_OWNER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FilePcbUsrModel
     * @throws SQLException SQL実行例外
     */
    private FilePcbOwnerModel __getFilePcbUsrFromRs(ResultSet rs) throws SQLException {
        FilePcbOwnerModel bean = new FilePcbOwnerModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrKbn(rs.getInt("USR_KBN"));
        return bean;
    }
}
