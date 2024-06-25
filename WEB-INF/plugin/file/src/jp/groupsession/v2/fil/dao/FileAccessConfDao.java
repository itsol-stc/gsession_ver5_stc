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
import jp.groupsession.v2.fil.model.FileAccessConfModel;

/**
 * <p>FILE_ACCESS_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileAccessConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileAccessConfDao.class);

    /**
     * <p>Default Constructor
     */
    public FileAccessConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileAccessConfDao(Connection con) {
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
            sql.addSql("drop table FILE_ACCESS_CONF");

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
            sql.addSql(" create table FILE_ACCESS_CONF (");
            sql.addSql("   FCB_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_KBN NUMBER(10,0) not null,");
            sql.addSql("   FAA_AUTH NUMBER(10,0) not null,");
            sql.addSql("   primary key (FCB_SID,USR_SID,USR_KBN)");
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
     * <p>Insert FILE_ACCESS_CONF Data Bindding JavaBean
     * @param bean FILE_ACCESS_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileAccessConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_ACCESS_CONF(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getUsrKbn());
            sql.addIntValue(bean.getFaaAuth());
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
     * キャビネットアクセス設定を登録
     * @param fcbSid キャビネットSID
     * @param sids ユーザSID
     * @param usrKbn ユーザ区分 (0=ユーザ 1=グループ)
     * @param auth 権限 0=閲覧のみ 1=追加・編集・削除可能
     * @throws SQLException SQL実行時例外
     */
    public void insert(int fcbSid, ArrayList<Integer> sids, int usrKbn, int auth)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_ACCESS_CONF(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (sids != null) {
                for (Integer sid : sids) {
                    sql.addIntValue(fcbSid);
                    sql.addIntValue(sid.intValue());
                    sql.addIntValue(usrKbn);
                    sql.addIntValue(auth);
                    log__.info(sql.toLogString());
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
     * キャビネットアクセス設定を登録
     * @param fcbSid キャビネットSID
     * @param sid ユーザSID
     * @param usrKbn ユーザ区分 (0=ユーザ 1=グループ)
     * @param auth 権限 0=閲覧のみ 1=追加・編集・削除可能
     * @throws SQLException SQL実行時例外
     */
    public void insert(int fcbSid, int sid, int usrKbn, int auth)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_ACCESS_CONF(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(sid);
            sql.addIntValue(usrKbn);
            sql.addIntValue(auth);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
            sql.clearValue();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update FILE_ACCESS_CONF Data Bindding JavaBean
     * @param bean FILE_ACCESS_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileAccessConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" set ");
            sql.addSql("   FAA_AUTH=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFaaAuth());
            //where
            sql.addIntValue(bean.getFcbSid());
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
     * <p>Select FILE_ACCESS_CONF All Data
     * @return List in FILE_ACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileAccessConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileAccessConfModel> ret = new ArrayList<FileAccessConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACCESS_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileAccessConfFromRs(rs));
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
     * <p>権限区分を指定し設定されているユーザSID又はG+グループSIDの配列を取得する
     * @param fcbSid キャビネットSID
     * @param auth 権限区分
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public String[] getAccessUser(int fcbSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and ");
            sql.addSql("   FAA_AUTH=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(auth);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("USR_KBN") == GSConstFile.USER_KBN_GROUP) {
                    ret.add("G" + rs.getString("USR_SID"));
                } else {
                    ret.add(rs.getString("USR_SID"));
                }
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
     * <p>キャビネットSID、ユーザSID、権限区分を指定してユーザが権限が有るか判定する
     * @param fcbSid キャビネットSID
     * @param userSid ユーザSID
     * @param auth 権限区分に-1を指定すると条件から除外
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessUser(int fcbSid, int userSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNTA");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_KBN=?");
            sql.addIntValue(fcbSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            if (auth != -1) {
                sql.addSql(" and");
                sql.addSql("   FAA_AUTH=?");
                sql.addIntValue(auth);
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CNTA") > 0) {
                    ret = true;

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
     * <p>キャビネットSID、ユーザSID、権限区分を指定してユーザが所属するグループに権限が有るか判定する
     * @param fcbSid キャビネットSID
     * @param userSid ユーザSID
     * @param auth 権限区分に-1を指定すると条件から除外
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessUserForBelongGroup(
            int fcbSid, int userSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNTB");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID in (");
            sql.addSql("    select");
            sql.addSql("      GRP_SID");
            /*
            sql.addSql("      GRP_SID,");
            sql.addSql("      USR_SID");
            */
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      USR_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   USR_KBN=?");
            sql.addSql("");
            sql.addIntValue(fcbSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            if (auth != -1) {
                sql.addSql(" and");
                sql.addSql("   FAA_AUTH=?");
                sql.addIntValue(auth);
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CNTB") > 0) {
                    ret = true;

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
     * <p>キャビネットSID、ユーザSID、権限区分を指定してユーザに権限が有るか判定する
     * @param fcbSid キャビネットSID
     * @param userSid ユーザSID
     * @param auth 権限区分に-1を指定すると条件から除外
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessUserGroup(
            int fcbSid, int userSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNTB");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FILE_ACCESS_CONF.FCB_SID = ?");
            sql.addIntValue(fcbSid);
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     USR_SID in (");
            sql.addSql("       select");
            sql.addSql("         USR_SID");
            sql.addSql("       from");
            sql.addSql("         FILE_ACCESS_CONF");
            sql.addSql("       where");
            sql.addSql("         USR_KBN = ?");
            sql.addSql("         and FCB_SID = ?");
            sql.addSql("         and USR_SID = ?");
            sql.addSql("     )");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(fcbSid);
            sql.addIntValue(userSid);
            sql.addSql("     or USR_SID in (");
            sql.addSql("       select");
            sql.addSql("         file.USR_SID");
            sql.addSql("       from");
            sql.addSql("         CMN_BELONGM CMN,");
            sql.addSql("         FILE_ACCESS_CONF FILE");
            sql.addSql("       where");
            sql.addSql("         FILE.USR_SID = cmn.GRP_SID");
            sql.addSql("         and FILE.USR_KBN = ?");
            sql.addSql("         and FILE.FCB_SID = ?");
            sql.addSql("         and CMN.USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(fcbSid);
            sql.addIntValue(userSid);
            if (auth != -1) {
                sql.addSql(" and");
                sql.addSql("   FAA_AUTH=?");
                sql.addIntValue(auth);
            }


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CNTB") > 0) {
                    ret = true;

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
     * <p>Select FILE_ACCESS_CONF
     * @param fcbSid FCB_SID
     * @param usrSid USR_SID
     * @param usrKbn USR_KBN
     * @return FILE_ACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public FileAccessConfModel select(int fcbSid, int usrSid, int usrKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileAccessConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FAA_AUTH");
            sql.addSql(" from");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileAccessConfFromRs(rs);
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
     * <p>Delete FILE_ACCESS_CONF
     * @param fcbSid FCB_SID
     * @param usrSid USR_SID
     * @param usrKbn USR_KBN
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fcbSid, int usrSid, int usrKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrKbn);

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
     * <p>Delete FILE_ACCESS_CONF
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
            sql.addSql("   FILE_ACCESS_CONF");
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
     * <p>Delete FILE_ACCESS_CONF
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
            sql.addSql("   FILE_ACCESS_CONF");
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
     * <p>Create FILE_ACCESS_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileAccessConfModel
     * @throws SQLException SQL実行例外
     */
    private FileAccessConfModel __getFileAccessConfFromRs(ResultSet rs) throws SQLException {
        FileAccessConfModel bean = new FileAccessConfModel();
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrKbn(rs.getInt("USR_KBN"));
        bean.setFaaAuth(rs.getInt("FAA_AUTH"));
        return bean;
    }
}
