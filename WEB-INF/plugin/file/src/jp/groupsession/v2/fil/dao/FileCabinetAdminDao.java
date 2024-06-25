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
import jp.groupsession.v2.fil.model.FileCabinetAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>FILE_CABINET_ADMIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCabinetAdminDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCabinetAdminDao.class);

    /**
     * <p>Default Constructor
     */
    public FileCabinetAdminDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileCabinetAdminDao(Connection con) {
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
            sql.addSql("drop table FILE_CABINET_ADMIN");

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
            sql.addSql(" create table FILE_CABINET_ADMIN (");
            sql.addSql("   FCB_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (FCB_SID,USR_SID)");
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
     * <p>Insert FILE_CABINET_ADMIN Data Bindding JavaBean
     * @param bean FILE_CABINET_ADMIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileCabinetAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET_ADMIN(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>キャビネットの管理者を登録します
     * @param fcbSid キャビネットSID
     * @param users 管理者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void insert(int fcbSid, ArrayList<Integer> users) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET_ADMIN(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (users != null) {
                for (Integer usrSid : users) {
                    sql.addIntValue(fcbSid);
                    sql.addIntValue(usrSid.intValue());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                    sql.clearValue();
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update FILE_CABINET_ADMIN Data Bindding JavaBean
     * @param bean FILE_CABINET_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileCabinetAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getFcbSid());
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
     * <p>Select FILE_CABINET_ADMIN All Data
     * @return List in FILE_CABINET_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCabinetAdminModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetAdminModel> ret = new ArrayList<FileCabinetAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET_ADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCabinetAdminFromRs(rs));
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
     * <p>Select FILE_CABINET_ADMIN
     * @param fcbSid FCB_SID
     * @param usrSid USR_SID
     * @return FILE_CABINET_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public FileCabinetAdminModel select(int fcbSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCabinetAdminModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileCabinetAdminFromRs(rs);
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
     * <p>Select FILE_CABINET_ADMIN
     * @param fcbSid FCB_SID
     * @return FILE_CABINET_ADMINModel List
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetAdminModel> select(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetAdminModel> ret = new ArrayList<FileCabinetAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCabinetAdminFromRs(rs));
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
     * <p>Select FILE_CABINET_ADMIN
     * @param fcbSid FCB_SID
     * @return String[] ユーザSIDの配列
     * @throws SQLException SQL実行例外
     */
    public String[] getAdminUserSid(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[ret.size()]);
    }
    /**
     * <p>Delete FILE_CABINET_ADMIN
     * @param fcbSid FCB_SID
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fcbSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
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
     * <p>Delete FILE_CABINET_ADMIN
     * @param fcbSid FCB_SID
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

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
     * <p>Delete FILE_CABINET_ADMIN
     * @param fcbSids FCB_SIDリスト
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(ArrayList<Integer> fcbSids) throws SQLException {

        int count = 0;
        if (fcbSids == null || fcbSids.isEmpty()) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID in (");
            boolean firstFlg = true;
            for (int fcbSid : fcbSids) {
                if (!firstFlg) {
                    sql.addSql("   ,");
                } else {
                    firstFlg = false;
                }
                sql.addSql("   ?");
                sql.addIntValue(fcbSid);
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create FILE_CABINET_ADMIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCabinetAdminModel
     * @throws SQLException SQL実行例外
     */
    private FileCabinetAdminModel __getFileCabinetAdminFromRs(ResultSet rs) throws SQLException {
        FileCabinetAdminModel bean = new FileCabinetAdminModel();
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
