package jp.groupsession.v2.fil.dao;

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
import jp.groupsession.v2.fil.model.FileAconfModel;

/**
 * <p>FILE_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public FileAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileAconfDao(Connection con) {
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
            sql.addSql("drop table FILE_ACONF");

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
            sql.addSql(" create table FILE_ACONF (");
            sql.addSql("   FAC_CRT_KBN NUMBER(10,0) not null,");
            sql.addSql("   FAC_FILE_SIZE NUMBER(10,0) not null,");
            sql.addSql("   FAC_SAVE_DAYS NUMBER(10,0) not null,");
            sql.addSql("   FAC_LOCK_KBN NUMBER(10,0) not null,");
            sql.addSql("   FAC_VER_KBN NUMBER(10,0) not null,");
            sql.addSql("   FAC_AUID NUMBER(10,0) not null,");
            sql.addSql("   FAC_ADATE varchar(23) not null,");
            sql.addSql("   FAC_EUID NUMBER(10,0) not null,");
            sql.addSql("   FAC_EDATE varchar(23) not null,");
            sql.addSql("   FAC_SMAIL_SEND_KBN NUMBER(10,0) not null,");
            sql.addSql("   FAC_SMAIL_SEND NUMBER(10,0) not null,");
            sql.addSql("   FAC_PERSONAL_KBN integer defualt 0 not null,");
            sql.addSql("   FAC_USE_KBN integer defualt 0 not null,");
            sql.addSql("   FAC_PERSONAL_CAPA integer default 0 not null,");
            sql.addSql("   FAC_PERSONAL_SIZE integer,");
            sql.addSql("   FAC_PERSONAL_WARN integer,");
            sql.addSql("   FAC_PERSONAL_VER integer default 0 not null,");
            sql.addSql("   FAC_PERSONAL_EDATE varchar(23)");
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
     * <p>Insert FILE_ACONF Data Bindding JavaBean
     * @param bean FILE_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_ACONF(");
            sql.addSql("   FAC_CRT_KBN,");
            sql.addSql("   FAC_FILE_SIZE,");
            sql.addSql("   FAC_SAVE_DAYS,");
            sql.addSql("   FAC_LOCK_KBN,");
            sql.addSql("   FAC_VER_KBN,");
            sql.addSql("   FAC_AUID,");
            sql.addSql("   FAC_ADATE,");
            sql.addSql("   FAC_EUID,");
            sql.addSql("   FAC_EDATE,");
            sql.addSql("   FAC_SMAIL_SEND_KBN,");
            sql.addSql("   FAC_SMAIL_SEND,");
            sql.addSql("   FAC_WARN_CNT,");
            sql.addSql("   FAC_PERSONAL_KBN,");
            sql.addSql("   FAC_USE_KBN,");
            sql.addSql("   FAC_PERSONAL_CAPA,");
            sql.addSql("   FAC_PERSONAL_SIZE,");
            sql.addSql("   FAC_PERSONAL_WARN,");
            sql.addSql("   FAC_PERSONAL_VER,");
            sql.addSql("   FAC_PERSONAL_EDATE");
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
            sql.addIntValue(bean.getFacCrtKbn());
            sql.addIntValue(bean.getFacFileSize());
            sql.addIntValue(bean.getFacSaveDays());
            sql.addIntValue(bean.getFacLockKbn());
            sql.addIntValue(bean.getFacVerKbn());
            sql.addIntValue(bean.getFacAuid());
            sql.addDateValue(bean.getFacAdate());
            sql.addIntValue(bean.getFacEuid());
            sql.addDateValue(bean.getFacEdate());
            sql.addIntValue(bean.getFacSmailSendKbn());
            sql.addIntValue(bean.getFacSmailSend());
            sql.addIntValue(bean.getFacWarnCnt());
            sql.addIntValue(bean.getFacPersonalKbn());
            sql.addIntValue(bean.getFacUseKbn());
            sql.addIntValue(bean.getFacPersonalCapa());
            sql.addIntValue(bean.getFacPersonalSize());
            sql.addIntValue(bean.getFacPersonalWarn());
            sql.addIntValue(bean.getFacPersonalVer());
            sql.addDateValue(bean.getFacPersonalEdate());

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
     * <p>Update FILE_ACONF Data Bindding JavaBean
     * @param bean FILE_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_ACONF");
            sql.addSql(" set ");
            sql.addSql("   FAC_CRT_KBN=?,");
            sql.addSql("   FAC_FILE_SIZE=?,");
            sql.addSql("   FAC_SAVE_DAYS=?,");
            sql.addSql("   FAC_LOCK_KBN=?,");
            sql.addSql("   FAC_VER_KBN=?,");
            sql.addSql("   FAC_EUID=?,");
            sql.addSql("   FAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFacCrtKbn());
            sql.addIntValue(bean.getFacFileSize());
            sql.addIntValue(bean.getFacSaveDays());
            sql.addIntValue(bean.getFacLockKbn());
            sql.addIntValue(bean.getFacVerKbn());
            sql.addIntValue(bean.getFacEuid());
            sql.addDateValue(bean.getFacEdate());

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
     * <p>Update FILE_ACONF Data Bindding JavaBean
     * @param bean FILE_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePersonal(FileAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_ACONF");
            sql.addSql(" set ");
            sql.addSql("   FAC_PERSONAL_KBN=?,");
            sql.addSql("   FAC_USE_KBN=?,");
            sql.addSql("   FAC_PERSONAL_CAPA=?,");
            sql.addSql("   FAC_PERSONAL_SIZE=?,");
            sql.addSql("   FAC_PERSONAL_WARN=?,");
            sql.addSql("   FAC_PERSONAL_VER=?,");
            sql.addSql("   FAC_PERSONAL_EDATE=?,");
            sql.addSql("   FAC_EUID=?,");
            sql.addSql("   FAC_EDATE=?");
            sql.addSql("   ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFacPersonalKbn());
            sql.addIntValue(bean.getFacUseKbn());
            sql.addIntValue(bean.getFacPersonalCapa());
            sql.addIntValue(bean.getFacPersonalSize());
            sql.addIntValue(bean.getFacPersonalWarn());
            sql.addIntValue(bean.getFacPersonalVer());
            sql.addDateValue(bean.getFacPersonalEdate());
            sql.addIntValue(bean.getFacEuid());
            sql.addDateValue(bean.getFacEdate());

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
     * <br>[機  能] ショートメール通知設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 管理者設定情報
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSmailSend(FileAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_ACONF");
            sql.addSql(" set ");
            sql.addSql("   FAC_SMAIL_SEND_KBN=?,");
            sql.addSql("   FAC_SMAIL_SEND=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFacSmailSendKbn());
            sql.addIntValue(bean.getFacSmailSend());

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
     * <p>Select FILE_ACONF All Data
     * @return List in FILE_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public FileAconfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileAconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FAC_CRT_KBN,");
            sql.addSql("   FAC_FILE_SIZE,");
            sql.addSql("   FAC_SAVE_DAYS,");
            sql.addSql("   FAC_LOCK_KBN,");
            sql.addSql("   FAC_VER_KBN,");
            sql.addSql("   FAC_AUID,");
            sql.addSql("   FAC_ADATE,");
            sql.addSql("   FAC_EUID,");
            sql.addSql("   FAC_EDATE,");
            sql.addSql("   FAC_SMAIL_SEND_KBN,");
            sql.addSql("   FAC_SMAIL_SEND,");
            sql.addSql("   FAC_WARN_CNT,");
            sql.addSql("   FAC_PERSONAL_KBN,");
            sql.addSql("   FAC_USE_KBN,");
            sql.addSql("   FAC_PERSONAL_CAPA,");
            sql.addSql("   FAC_PERSONAL_SIZE,");
            sql.addSql("   FAC_PERSONAL_WARN,");
            sql.addSql("   FAC_PERSONAL_VER,");
            sql.addSql("   FAC_PERSONAL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getFileAconfFromRs(rs);
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
     * <p>Select FILE_ACONF All Data
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
            sql.addSql("   FILE_ACONF");

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
     * <p>Create FILE_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileAconfModel
     * @throws SQLException SQL実行例外
     */
    private FileAconfModel __getFileAconfFromRs(ResultSet rs) throws SQLException {
        FileAconfModel bean = new FileAconfModel();
        bean.setFacCrtKbn(rs.getInt("FAC_CRT_KBN"));
        bean.setFacFileSize(rs.getInt("FAC_FILE_SIZE"));
        bean.setFacSaveDays(rs.getInt("FAC_SAVE_DAYS"));
        bean.setFacLockKbn(rs.getInt("FAC_LOCK_KBN"));
        bean.setFacVerKbn(rs.getInt("FAC_VER_KBN"));
        bean.setFacAuid(rs.getInt("FAC_AUID"));
        bean.setFacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FAC_ADATE")));
        bean.setFacEuid(rs.getInt("FAC_EUID"));
        bean.setFacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FAC_EDATE")));
        bean.setFacSmailSendKbn(rs.getInt("FAC_SMAIL_SEND_KBN"));
        bean.setFacSmailSend(rs.getInt("FAC_SMAIL_SEND"));
        bean.setFacWarnCnt(rs.getInt("FAC_WARN_CNT"));
        bean.setFacPersonalKbn(rs.getInt("FAC_PERSONAL_KBN"));
        bean.setFacUseKbn(rs.getInt("FAC_USE_KBN"));
        bean.setFacPersonalCapa(rs.getInt("FAC_PERSONAL_CAPA"));
        bean.setFacPersonalSize(rs.getInt("FAC_PERSONAL_SIZE"));
        bean.setFacPersonalWarn(rs.getInt("FAC_PERSONAL_WARN"));
        bean.setFacPersonalVer(rs.getInt("FAC_PERSONAL_VER"));
        bean.setFacPersonalEdate(
                UDate.getInstanceTimestamp(rs.getTimestamp("FAC_PERSONAL_EDATE")));
        return bean;
    }
}
