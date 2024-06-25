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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileCallDataModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <p>FILE_CALL_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCallDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCallDataDao.class);

    /**
     * <p>Default Constructor
     */
    public FileCallDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileCallDataDao(Connection con) {
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
            sql.addSql("drop table FILE_CALL_DATA");

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
            sql.addSql(" create table FILE_CALL_DATA (");
            sql.addSql("   FDR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (FDR_SID,USR_SID)");
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
     * <p>Insert FILE_CALL_DATA Data Bindding JavaBean
     * @param bean FILE_CALL_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileCallDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CALL_DATA(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrSid());
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
     * <p>Update FILE_CALL_DATA Data Bindding JavaBean
     * @param bean FILE_CALL_DATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileCallDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CALL_DATA");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getFdrSid());
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
     * <p>Select FILE_CALL_DATA All Data
     * @return List in FILE_CALL_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCallDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCallDataModel> ret = new ArrayList<FileCallDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CALL_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCallDataFromRs(rs));
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
     * <p>Select FILE_CALL_DATA
     * @param fdrSid FDR_SID
     * @param usrSid USR_SID
     * @return FILE_CALL_DATAModel
     * @throws SQLException SQL実行例外
     */
    public FileCallDataModel select(int fdrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCallDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_DATA");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileCallDataFromRs(rs);
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
     * <p>ユーザSIDを指定し未確認な更新通知情報のディレクトリ情報を取得
     * @param usrSid USR_SID
     * @param isAdmin true:システム or プラグイン管理者 false:一般ユーザ
     * @return count 更新通知件数
     * @throws SQLException SQL実行例外
     */
    public int getUpdateCallDataCnt(int usrSid, boolean isAdmin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_DATA,");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION,");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where");
            sql.addSql("   FILE_CALL_DATA.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_CALL_DATA.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            //閲覧が許可されていない場合は対象外とする
            /*sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     FILE_CABINET.FCB_ACCESS_KBN = ?");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       FILE_CABINET.FCB_ACCESS_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         exists (");
            sql.addSql("           select 1 from FILE_ACCESS_CONF");
            sql.addSql("           where FILE_CALL_DATA.USR_SID = FILE_ACCESS_CONF.USR_SID");
            sql.addSql("           and FILE_ACCESS_CONF.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("           and FILE_ACCESS_CONF.USR_KBN = ?");
            sql.addSql("         )");
            sql.addSql("       or");
            sql.addSql("         exists (");
            sql.addSql("           select");
            sql.addSql("             1");
            sql.addSql("           from");
            sql.addSql("             CMN_BELONGM,");
            sql.addSql("             FILE_ACCESS_CONF");
            sql.addSql("           where FILE_CALL_DATA.USR_SID = CMN_BELONGM.USR_SID");
            sql.addSql("           and FILE_ACCESS_CONF.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("           and FILE_ACCESS_CONF.USR_KBN = ?");
            sql.addSql("           and CMN_BELONGM.GRP_SID = FILE_ACCESS_CONF.USR_SID");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
            sql.addIntValue(GSConstFile.ACCESS_KBN_ON);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);*/

            //管理者ユーザ以外の場合、ディレクトリのアクセス設定で判別する
            if (!isAdmin) {
                sql.addSql(" and (");
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                //キャビネット管理者
                sql.addSql(" or exists");
                sql.addSql("   (select 1 from FILE_CABINET_ADMIN");
                sql.addSql("    where");
                sql.addSql("      FILE_CABINET_ADMIN.FCB_SID = FILE_DIRECTORY.FCB_SID");
                sql.addSql("    and ");
                sql.addSql("      FILE_CABINET_ADMIN.USR_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(usrSid);

                //個別アクセス制限
                sql.addSql(" or exists");
                sql.addSql("   (select *");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = FILE_CALL_DATA.USR_SID) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select *");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = FILE_CALL_DATA.USR_SID");
                sql.addSql("         )))");
                sql.addSql("   ))");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>ユーザSIDを指定し未確認な更新通知情報のディレクトリ情報を取得
     * @param usrSid USR_SID
     * @param isAdmin true:システム or プラグイン管理者 false:一般ユーザ
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return FILE_SHORTCUT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getUpdateCallData(
            int usrSid, boolean isAdmin, int offset, int limit)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DIRECTORY.FDR_SID as FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN as FDR_KBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN as FDR_VER_KBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL as FDR_LEVEL,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_DIRECTORY.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN as FDR_JTKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_EUID as FDR_EUID,");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE as FDR_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_MARK as FCB_MARK");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_DATA,");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION,");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where");
            sql.addSql("   FILE_CALL_DATA.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_CALL_DATA.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            //管理者ユーザ以外の場合、ディレクトリのアクセス設定で判別する
            if (!isAdmin) {
                sql.addSql(" and (");
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                //キャビネット管理者
                sql.addSql(" or exists");
                sql.addSql("   (select 1 from FILE_CABINET_ADMIN");
                sql.addSql("    where");
                sql.addSql("      FILE_CABINET_ADMIN.FCB_SID = FILE_DIRECTORY.FCB_SID");
                sql.addSql("    and ");
                sql.addSql("      FILE_CABINET_ADMIN.USR_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(usrSid);

                //個別アクセス制限
                sql.addSql(" or exists");
                sql.addSql("   (select *");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = FILE_CALL_DATA.USR_SID) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select *");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = FILE_CALL_DATA.USR_SID");
                sql.addSql("         )))");
                sql.addSql("   ))");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            }

            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE DESC,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                ret.add(__getFileDirectoryFromRsolusMark(rs));
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
     * <p>Delete FILE_CALL_DATA
     * @param fdrSid FDR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
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
            sql.addSql("   FILE_CALL_DATA");
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
     * <p>Delete FILE_CALL_DATA
     * @param fdrSid FDR_SID
     * @param usrSid USR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int fdrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_DATA");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
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
     * <p>Delete FILE_CALL_DATA
     * @param fdrMdlList FileDirectoryModelリスト
     * @param usrSid USR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(List<FileDirectoryModel> fdrMdlList, int usrSid) throws SQLException {

        int count = 0;
        if (fdrMdlList != null && fdrMdlList.size() < 1) {
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
            sql.addSql("   FILE_CALL_DATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(usrSid);
            sql.addSql(" and");
            sql.addSql("  (");
            int n = 0;
            for (FileDirectoryModel fdrMdl : fdrMdlList) {
                if (n > 0) {
                    sql.addSql(" or ");
                }
                sql.addSql("   FDR_SID=?");
                sql.addIntValue(fdrMdl.getFdrSid());
                n++;
            }
            sql.addSql("  )");

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
     * <br>[機  能] 指定されたディレクトリ(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除対象のディレクトリ情報モデルリスト
     * @throws SQLException 例外
     */
    public void deleteDir(ArrayList<FileDirectoryModel> delList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_DATA");
            if (delList != null) {
                sql.addSql(" where ");
                sql.addSql("   FDR_SID=-1");
                for (FileDirectoryModel bean : delList) {
                    sql.addSql(" or");
                    sql.addSql("   FDR_SID=?");
                    sql.addIntValue(bean.getFdrSid());
                }
            }

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>指定したディレクトリSIDを親に持つディレクトリの更新確認情報を削除する
     * @param fdrSid FDR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteParentDir(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from FILE_CALL_DATA");
            sql.addSql("  where FDR_SID in (select FDR_SID");
            sql.addSql("                      from FILE_DIRECTORY");
            sql.addSql("                     where FDR_PARENT_SID = ?");
            sql.addSql("                   )");

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
     * <p>Create FILE_CALL_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCallDataModel
     * @throws SQLException SQL実行例外
     */
    private FileCallDataModel __getFileCallDataFromRs(ResultSet rs) throws SQLException {
        FileCallDataModel bean = new FileCallDataModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }

    /**
     * <p>Create FILE_DIRECTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private FileDirectoryModel __getFileDirectoryFromRsolusMark(ResultSet rs) throws SQLException {
        FileDirectoryModel bean = new FileDirectoryModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
        bean.setFdrKbn(rs.getInt("FDR_KBN"));
        bean.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
        bean.setFdrLevel(rs.getInt("FDR_LEVEL"));
        bean.setFdrName(rs.getString("FDR_NAME"));
        bean.setFdrBiko(rs.getString("FDR_BIKO"));
        bean.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
        bean.setFdrAuid(rs.getInt("FDR_AUID"));
        bean.setFdrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_ADATE")));
        bean.setFdrEuid(rs.getInt("FDR_EUID"));
        bean.setFdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE")));
        bean.setFcbMark(rs.getLong("FCB_MARK"));
        return bean;
    }
}
