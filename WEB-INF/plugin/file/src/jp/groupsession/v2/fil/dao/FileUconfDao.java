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
import jp.groupsession.v2.fil.model.FileUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>FILE_UCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileUconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileUconfDao.class);

    /**
     * <p>Default Constructor
     */
    public FileUconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileUconfDao(Connection con) {
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
            sql.addSql("drop table FILE_UCONF");

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
            sql.addSql(" create table FILE_UCONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   FUC_MAIN_OKINI NUMBER(10,0) not null,");
            sql.addSql("   FUC_MAIN_CALL NUMBER(10,0) not null,");
            sql.addSql("   FUC_RIREKI_CNT NUMBER(10,0) not null,");
            sql.addSql("   FUC_SMAIL_SEND NUMBER(10,0) not null,");
            sql.addSql("   FUC_CALL NUMBER(10,0) not null,");
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
     * <p>Insert FILE_UCONF Data Bindding JavaBean
     * @param bean FILE_UCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_UCONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   FUC_MAIN_OKINI,");
            sql.addSql("   FUC_MAIN_CALL,");
            sql.addSql("   FUC_RIREKI_CNT,");
            sql.addSql("   FUC_SMAIL_SEND,");
            sql.addSql("   FUC_CALL");
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
            sql.addIntValue(bean.getFucMainOkini());
            sql.addIntValue(bean.getFucMainCall());
            sql.addIntValue(bean.getFucRirekiCnt());
            sql.addIntValue(bean.getFucSmailSend());
            sql.addIntValue(bean.getFucCall());
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
     * <p>Update FILE_UCONF Data Bindding JavaBean
     * @param bean FILE_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_UCONF");
            sql.addSql(" set ");
            sql.addSql("   FUC_MAIN_OKINI=?,");
            sql.addSql("   FUC_MAIN_CALL=?,");
            sql.addSql("   FUC_RIREKI_CNT=?,");
            sql.addSql("   FUC_SMAIL_SEND=?,");
            sql.addSql("   FUC_CALL=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFucMainOkini());
            sql.addIntValue(bean.getFucMainCall());
            sql.addIntValue(bean.getFucRirekiCnt());
            sql.addIntValue(bean.getFucSmailSend());
            sql.addIntValue(bean.getFucCall());
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
     * <p>Update FILE_UCONF Data Bindding JavaBean
     * @param bean FILE_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDspConf(FileUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_UCONF");
            sql.addSql(" set ");
            sql.addSql("   FUC_MAIN_OKINI=?,");
            sql.addSql("   FUC_MAIN_CALL=?,");
            sql.addSql("   FUC_RIREKI_CNT=?,");
            sql.addSql("   FUC_CALL=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFucMainOkini());
            sql.addIntValue(bean.getFucMainCall());
            sql.addIntValue(bean.getFucRirekiCnt());
            sql.addIntValue(bean.getFucCall());
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
     * <p>Update FILE_UCONF Data Bindding JavaBean
     * @param bean FILE_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSmailConf(FileUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_UCONF");
            sql.addSql(" set ");
            sql.addSql("   FUC_SMAIL_SEND=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFucSmailSend());
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
     * <p>Select FILE_UCONF All Data
     * @return List in FILE_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileUconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList <FileUconfModel> ret = new ArrayList<FileUconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   FUC_MAIN_OKINI,");
            sql.addSql("   FUC_MAIN_CALL,");
            sql.addSql("   FUC_RIREKI_CNT,");
            sql.addSql("   FUC_SMAIL_SEND,");
            sql.addSql("   FUC_CALL");
            sql.addSql(" from ");
            sql.addSql("   FILE_UCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileUconfFromRs(rs));
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
     * <p>Select FILE_UCONF
     * @param usrSid USR_SID
     * @return FILE_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public FileUconfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileUconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   FUC_MAIN_OKINI,");
            sql.addSql("   FUC_MAIN_CALL,");
            sql.addSql("   FUC_RIREKI_CNT,");
            sql.addSql("   FUC_SMAIL_SEND,");
            sql.addSql("   FUC_CALL");
                       sql.addSql(" from");
            sql.addSql("   FILE_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileUconfFromRs(rs);
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
     * <p>Delete FILE_UCONF
     * @param usrSid USR_SID
     * @return count 件数
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
            sql.addSql("   FILE_UCONF");
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
     * <p>Create FILE_UCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileUconfModel
     * @throws SQLException SQL実行例外
     */
    private FileUconfModel __getFileUconfFromRs(ResultSet rs) throws SQLException {
        FileUconfModel bean = new FileUconfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setFucMainOkini(rs.getInt("FUC_MAIN_OKINI"));
        bean.setFucMainCall(rs.getInt("FUC_MAIN_CALL"));
        bean.setFucRirekiCnt(rs.getInt("FUC_RIREKI_CNT"));
        bean.setFucSmailSend(rs.getInt("FUC_SMAIL_SEND"));
        bean.setFucCall(rs.getInt("FUC_CALL"));
        return bean;
    }
}
