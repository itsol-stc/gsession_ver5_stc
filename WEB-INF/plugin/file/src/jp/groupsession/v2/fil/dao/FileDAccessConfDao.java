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
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileAccessConfModel;
import jp.groupsession.v2.fil.model.FileDAccessConfModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>FILE_DACCESS_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDAccessConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileDAccessConfDao.class);

    /**
     * <p>Default Constructor
     */
    public FileDAccessConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileDAccessConfDao(Connection con) {
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
            sql.addSql("drop table FILE_DACCESS_CONF");

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
            sql.addSql(" create table FILE_DACCESS_CONF (");
            sql.addSql("   FDR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_KBN NUMBER(10,0) not null,");
            sql.addSql("   FDA_AUTH NUMBER(10,0) not null,");
            sql.addSql("   primary key (FDR_SID,USR_SID,USR_KBN)");
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
     * <p>Insert FILE_DACCESS_CONF Data Bindding JavaBean
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
            sql.addSql(" FILE_DACCESS_CONF(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FDA_AUTH");
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
     * ディレクトリアクセス設定を登録
     * @param fdrSid ディレクトリSID
     * @param sids ユーザSID
     * @param usrKbn ユーザ区分 (0=ユーザ 1=グループ)
     * @param auth 権限 0=閲覧のみ 1=追加・編集・削除可能
     * @throws SQLException SQL実行時例外
     */
    public void insert(int fdrSid, ArrayList<Integer> sids, int usrKbn, int auth)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_DACCESS_CONF(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FDA_AUTH");
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
                    sql.addIntValue(fdrSid);
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
     * @param fdrSid キャビネットSID
     * @param sid ユーザSID
     * @param usrKbn ユーザ区分 (0=ユーザ 1=グループ)
     * @param auth 権限 0=閲覧のみ 1=追加・編集・削除可能
     * @throws SQLException SQL実行時例外
     */
    public void insert(int fdrSid, int sid, int usrKbn, int auth)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_DACCESS_CONF(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FDA_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
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
     * <p>Update FILE_DACCESS_CONF Data Bindding JavaBean
     * @param bean FILE_DACCESS_CONF Data Bindding JavaBean
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
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" set ");
            sql.addSql("   FDA_AUTH=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
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
     * <p>Select FILE_DACCESS_CONF All Data
     * @return List in FILE_DACCESS_CONFModel
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
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FDA_AUTH");
            sql.addSql(" from ");
            sql.addSql("   FILE_DACCESS_CONF");

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
     * <p>Select FILE_DACCESS_CONF
     * @param fdrSid FDR_SID
     * @param usrSid USR_SID
     * @param usrKbn USR_KBN
     * @return FILE_DACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public FileAccessConfModel select(int fdrSid, int usrSid, int usrKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileAccessConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FDA_AUTH");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
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
     * <p>指定したディレクトリSIDを持つレコードを全て取得します。
     * @param dirSid ディレクトリSID
     * @param accessPower アクセス権限区分 0:閲覧 1:フルアクセス
     * @return 指定ディレクトリに対してアクセス権限のあるユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<FileDAccessConfModel> getAccessConf(int dirSid, int accessPower)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDAccessConfModel> ret = new ArrayList<FileDAccessConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_KBN,");
            sql.addSql("   FDA_AUTH");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   FDA_AUTH=?");

            sql.addIntValue(dirSid);
            sql.addIntValue(accessPower);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDAccessConfFromRs(rs));
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
     * <p>指定したディレクトリに対するアクセス権限が存在するかを取得する
     * @param dirSid ディレクトリSID
     * @return 指定ディレクトリに対してアクセス権限のあるユーザSID
     * @throws SQLException SQL実行例外
     */
    public boolean existsAccessConf(int dirSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1 from FILE_DACCESS_CONF");
            sql.addSql(" where FDR_SID=?");
            sql.addIntValue(dirSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <p>指定のディレクトリ以下のサブディレクトリとファイルのアクセス設定情報を取得削除する
     * @param fdrSid ディレクトリSID
     * @return List in FILE_DACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public int getCount(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from ");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Delete FILE_DACCESS_CONF
     * @param fdrSid FDR_SID
     * @param usrSid USR_SID
     * @param usrKbn USR_KBN
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fdrSid, int usrSid, int usrKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
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
     * <p>Delete FILE_DACCESS_CONF
     * @param fdrSid FDR_SID
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);

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
     * <p>Delete FILE_DACCESS_CONF
     * @param fdrSids FDR_SID
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(List<Integer> fdrSids) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        if (fdrSids.size() == 0) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" where FDR_SID in (");
            for (int idx = 0; idx < fdrSids.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(fdrSids.get(idx));
            }
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <p>指定ディレクトリ以下のサブディレクトリとファイルのアクセス設定を削除する
     * @param fdrSid ディレクトリSID
     * @param isChildOnly true:指定ディレクトリを親に持つ子のみ削除、false:指定ディレクトリと子の両方を削除
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int deleteSubDirectoriesFiles(int fdrSid, boolean isChildOnly) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

        try {
            if (h2db) {
                List<Integer> sids = __getAllSubDirectoriesSid(fdrSid, isChildOnly);
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   FILE_DACCESS_CONF");
                sql.addSql(" where FILE_DACCESS_CONF.FDR_SID = ?");

                pstmt = con.prepareStatement(sql.toSqlString());
                for (Integer sid : sids) {
                    sql.addIntValue(sid);
                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    count += pstmt.executeUpdate();
                    sql.clearValue();
                }
            } else {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   FILE_DACCESS_CONF");
                sql.addSql(" where exists");
                sql.addSql("   ( ");
                // ↓ 上位～下位のディレクトリ情報を取得するための階層問い合わせ -----------*
                sql.addSql("   with recursive rec(FDR_SID, FDR_PARENT_SID, PATH) as (");
                sql.addSql("   select A.FDR_SID, A.FDR_PARENT_SID, array[A.FDR_SID]");
                sql.addSql("     from FILE_DIRECTORY A");
                if (isChildOnly) {
                    sql.addSql("    where A.FDR_PARENT_SID = ?");
                } else {
                    sql.addSql("    where A.FDR_SID = ?");
                }
                sql.addSql("    union all");
                sql.addSql("   select B.FDR_SID, B.FDR_PARENT_SID, A.PATH || B.FDR_SID");
                sql.addSql("     from rec A");
                sql.addSql("     join FILE_DIRECTORY B on A.FDR_SID = B.FDR_PARENT_SID)");
                // ↑ ---------------------------------------------------------------------*
                sql.addSql("   select *");
                sql.addSql("     from rec");
                sql.addSql("    where rec.FDR_SID = FILE_DACCESS_CONF.FDR_SID");
                sql.addSql("   )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(fdrSid);

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count = pstmt.executeUpdate();
            }
         } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>親ディレクトリ以上の直近のディレクトリで編集、又は閲覧が可能なユーザが所属するグループSIDの配列を取得する
     * @param parentSid 親ディレクトリSID
     * @param auth 権限区分
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public String[] getAccessParentUserForBelongGroup(int parentSid, int auth)
                                                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION");

            sql.addSql(" where");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(parentSid);

            // アクセス可能かどうか判別
            sql.addSql(" and (");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
            sql.addSql(" or exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
            sql.addSql("    and");
            sql.addSql("      A.USR_KBN = ?");
            sql.addSql("    and");
            sql.addSql("      A.USR_SID = CMN_BELONGM.USR_SID");
            sql.addSql("    and");
            sql.addSql("      A.FDA_AUTH >= ?");
            sql.addSql("   ))");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(auth);

            sql.addSql(" order by ");
            sql.addSql("   CMN_BELONGM.GRP_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("GRP_SID"));
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
     * <p>親ディレクトリ以上の直近のディレクトリで編集、又は閲覧が可能なG+グループSIDの配列を取得する
     * @param parentSid 親ディレクトリSID
     * @param auth 権限区分
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public String[] getAccessParentGroup(int parentSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_GROUPM.GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM,");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION");

            sql.addSql(" where");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addIntValue(CmnGroupmDao.GRP_JKBN_LIVING);
            sql.addIntValue(parentSid);

            // アクセス可能かどうか判別
            sql.addSql(" and (");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
            sql.addSql(" or exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
            sql.addSql("    and");
            sql.addSql("      A.USR_KBN = ?");
            sql.addSql("    and");
            sql.addSql("      A.USR_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("    and");
            sql.addSql("      A.FDA_AUTH >= ?");
            sql.addSql("   ))");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(auth);

            sql.addSql(" order by");
            sql.addSql("   CMN_GROUPM.GRP_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add("G" + rs.getString("GRP_SID"));
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
     * <p>指定グループに所属し、この親ディレクトリ以上の直近のディレクトリで編集、又は閲覧が可能なユーザSIDの配列を取得する
     * @param parentSid 親ディレクトリSID
     * @param groupSid グループSID（-1の場合、該当する全てのユーザ）
     * @param auth 権限区分
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public String[] getAccessParentUser(int parentSid, int groupSid, int auth)
                                                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION");

            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(parentSid);

            if (groupSid != -1) {
                sql.addSql(" and exists");
                sql.addSql("   (select *");
                sql.addSql("    from");
                sql.addSql("      CMN_BELONGM");
                sql.addSql("    where");
                sql.addSql("      CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
                sql.addSql("    and");
                sql.addSql("      CMN_BELONGM.GRP_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(groupSid);
            }

            // アクセス可能かどうか判別
            sql.addSql(" and (");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
            sql.addSql(" or exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
            sql.addSql("    and");
            sql.addSql("      A.USR_KBN = ?");
            sql.addSql("    and");
            sql.addSql("      A.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("    and");
            sql.addSql("      A.FDA_AUTH >= ?");
            sql.addSql("   ))");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(auth);

            sql.addSql(" order by");
            sql.addSql("   CMN_USRM.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>権限区分を指定し設定されているユーザSID又はG+グループSIDの配列を取得する
     * @param fdrSid ディレクトリSID
     * @param auth 権限区分
     * @return String[] in SID
     * @throws SQLException SQL実行例外
     */
    public String[] getAccessUser(int fdrSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DACCESS_CONF.USR_KBN,");
            sql.addSql("   FILE_DACCESS_CONF.USR_SID");

            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION,");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql("   left join CMN_USRM on CMN_USRM.USR_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql("   left join CMN_GROUPM on CMN_GROUPM.GRP_SID = FILE_DACCESS_CONF.USR_SID");

            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = FILE_DACCESS_CONF.FDR_SID");
            sql.addSql(" and ");
            sql.addSql("   FILE_DACCESS_CONF.FDA_AUTH = ?");
            sql.addSql(" and ");
            sql.addSql("   ((FILE_DACCESS_CONF.USR_KBN = ? and");
            sql.addSql("     CMN_USRM.USR_JKBN = ?) or");
            sql.addSql("    (FILE_DACCESS_CONF.USR_KBN = ? and");
            sql.addSql("     CMN_GROUPM.GRP_JKBN = ?))");
            sql.addSql(" group by");
            sql.addSql("   FILE_DACCESS_CONF.USR_KBN,");
            sql.addSql("   FILE_DACCESS_CONF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(auth);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(CmnGroupmDao.GRP_JKBN_LIVING);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     *
     * <br>[機  能] 指定ディレクトリのサブディレクトリのSIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param isChildOnly true:指定ディレクトリを親に持つ子のみ、false:指定ディレクトリと子の両方
     * @return List in FDR_SID
     * @throws SQLException SQL実行例外
     */
    private List<Integer> __getAllSubDirectoriesSid(
            int fdrSid, boolean  isChildOnly) throws SQLException {

        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();
        FileDirectoryDao dDao = new FileDirectoryDao(con);
        List<FileDirectoryModel> sub;
        if (isChildOnly) {
          sub = dDao.getLowDirectory(fdrSid, -1);
        } else {
          sub = new ArrayList<FileDirectoryModel>();
          FileDirectoryModel bean = dDao.select(fdrSid, -1);
          if (bean != null) {
              sub.add(bean);
          }
        }
        for (FileDirectoryModel bean : sub) {
            FilTreeBiz treeBiz = new FilTreeBiz(con);
            FilChildTreeModel tree = treeBiz.getChildOfTarget(bean);
            for (FileDirectoryModel dir : tree.getListOfDir()) {
                ret.add(dir.getFdrSid());
            }
            for (FileDirectoryModel dir : tree.getListOfFile()) {
                ret.add(dir.getFdrSid());
            }
        }
        return ret;
    }
    /**
     * <p>指定のディレクトリ以下のサブディレクトリとファイルのアクセス設定情報を取得する
     * @param fdrSid ディレクトリSID
     * @return List in FILE_DACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileAccessConfModel> getAccessSubDirectoriesFiles(int fdrSid) throws SQLException {
        return getAccessSubDirectoriesFiles(fdrSid, true);
    }
    /**
     * <p>指定のディレクトリ以下のサブディレクトリとファイルのアクセス設定情報を取得する
     * @param fdrSid ディレクトリSID
     * @param isChildOnly true:指定ディレクトリを親に持つ子のみ、false:指定ディレクトリと子の両方
     * @return List in FILE_DACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileAccessConfModel> getAccessSubDirectoriesFiles(
            int fdrSid, boolean  isChildOnly
            ) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileAccessConfModel> ret = new ArrayList<FileAccessConfModel>();
        con = getCon();
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            if (h2db) {
                List<Integer> subDirSids = __getAllSubDirectoriesSid(fdrSid, isChildOnly);
                sql.addSql(" select ");
                sql.addSql("   A.FDR_SID,");
                sql.addSql("   A.USR_SID,");
                sql.addSql("   A.USR_KBN,");
                sql.addSql("   A.FDA_AUTH");
                sql.addSql(" from ");
                sql.addSql("   FILE_DACCESS_CONF A");
                sql.addSql(" where ");
                sql.addSql("   A.FDR_SID = ?");
                pstmt = con.prepareStatement(sql.toSqlString());
                for (Integer subDirSid : subDirSids) {
                    sql.addIntValue(subDirSid);
                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        ret.add(__getFileAccessConfFromRs(rs));
                    }
                    sql.clearValue();
                    JDBCUtil.closeResultSet(rs);
                }

            } else {
                // ↓ 上位～下位のディレクトリ情報を取得するための階層問い合わせ -----------*
                sql.addSql(" with recursive rec(FDR_SID, FDR_PARENT_SID, PATH) as (");
                sql.addSql(" select A.FDR_SID, A.FDR_PARENT_SID, array[A.FDR_SID]");
                sql.addSql("  from FILE_DIRECTORY A");
                if (isChildOnly) {
                    sql.addSql(" where A.FDR_PARENT_SID = ?");
                } else {
                    sql.addSql(" where A.FDR_SID = ?");
                }
                sql.addSql(" union all");
                sql.addSql(" select B.FDR_SID, B.FDR_PARENT_SID, A.PATH || B.FDR_SID");
                sql.addSql("  from rec A");
                sql.addSql("  join FILE_DIRECTORY B on A.FDR_SID = B.FDR_PARENT_SID)");
                // ↑ ---------------------------------------------------------------------*
                sql.addSql(" select ");
                sql.addSql("   A.FDR_SID,");
                sql.addSql("   A.USR_SID,");
                sql.addSql("   A.USR_KBN,");
                sql.addSql("   A.FDA_AUTH");
                sql.addSql(" from ");
                sql.addSql("   rec");
                sql.addSql(" inner join");
                sql.addSql("   FILE_DACCESS_CONF A");
                sql.addSql("   on rec.FDR_SID = A.FDR_SID");
                sql.addSql(" order by");
                sql.addSql("   PATH desc, A.FDA_AUTH");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(fdrSid);

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ret.add(__getFileAccessConfFromRs(rs));
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
     * <p>ディレクトリSID、ユーザSID、権限区分を指定し、アクセス権限があるかどうか判別する
     * @param fdrSid ディレクトリSID
     * @param userSid ユーザSID
     * @param auth 権限区分 (除外する場合は、-1を指定)
     * @return boolean in true:アクセス権限あり／false;アクセス権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessUser(int fdrSid, int userSid, int auth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNT");

            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION");

            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addIntValue(fdrSid);

            // アクセス可能かどうか判別
            sql.addSql(" and (");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

            sql.addSql(" or exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
            if (auth != -1) {
                sql.addSql("    and");
                sql.addSql("      A.FDA_AUTH = ?");
                sql.addIntValue(auth);
            }
            sql.addSql("    and (");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       A.USR_SID = ?) or");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       exists");
            sql.addSql("         (select *");
            sql.addSql("          from");
            sql.addSql("            CMN_BELONGM B");
            sql.addSql("          where");
            sql.addSql("            B.GRP_SID = A.USR_SID");
            sql.addSql("          and");
            sql.addSql("            B.USR_SID = ?");
            sql.addSql("         )))");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(userSid);
            sql.addSql("   ))");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CNT") > 0) {
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
     * <p>バイナリSIDを指定し、そのファイルに対するアクセス権限があるかどうか判別する
     * @param binSid ファイルバイナリSID
     * @param userSid ユーザSID
     * @return boolean in true:アクセス権限あり／false;アクセス権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessBinFile(Long binSid, int userSid) throws SQLException {
        return isAccessBinFile(binSid, userSid, true);
    }
    /**
     * <p>バイナリSIDを指定し、そのファイルに対するアクセス権限があるかどうか判別する
     * @param binSid ファイルバイナリSID
     * @param userSid ユーザSID
     * @param isOnlyNewversion 最新バージョンのみ:true
     * @return boolean in true:アクセス権限あり／false;アクセス権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessBinFile(Long binSid,
            int userSid,
            boolean isOnlyNewversion) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNT");

            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY,");
            if (isOnlyNewversion) {
                sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
                sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION,");
            }
            sql.addSql("   FILE_FILE_BIN");

            sql.addSql(" where");
            if (isOnlyNewversion) {
                sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
                sql.addSql(" and");
            }
            sql.addSql("   FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = ?");
            sql.addLongValue(binSid);

            // アクセス可能かどうか判別
            sql.addSql(" and (");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
            sql.addSql(" or exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
            sql.addSql("    and (");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       A.USR_SID = ?) or");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       exists");
            sql.addSql("         (select *");
            sql.addSql("          from");
            sql.addSql("            CMN_BELONGM B");
            sql.addSql("          where");
            sql.addSql("            B.GRP_SID = A.USR_SID");
            sql.addSql("          and");
            sql.addSql("            B.USR_SID = ?");
            sql.addSql("         )))");
            sql.addSql("   ))");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CNT") > 0) {
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
     * <p>Create FILE_ACCESS_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileAccessConfModel
     * @throws SQLException SQL実行例外
     */
    private FileAccessConfModel __getFileAccessConfFromRs(ResultSet rs) throws SQLException {
        FileAccessConfModel bean = new FileAccessConfModel();
        bean.setFcbSid(rs.getInt("FDR_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrKbn(rs.getInt("USR_KBN"));
        bean.setFaaAuth(rs.getInt("FDA_AUTH"));
        return bean;
    }

    /**
     * <p>Create FILE_DACCESS_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileAccessConfModel
     * @throws SQLException SQL実行例外
     */
    private FileDAccessConfModel __getFileDAccessConfFromRs(ResultSet rs) throws SQLException {
        FileDAccessConfModel bean = new FileDAccessConfModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrKbn(rs.getInt("USR_KBN"));
        bean.setFdaAuth(rs.getInt("FDA_AUTH"));
        return bean;
    }
}
