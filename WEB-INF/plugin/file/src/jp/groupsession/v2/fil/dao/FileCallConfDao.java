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
import jp.groupsession.v2.fil.model.FileCallConfModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>FILE_CALL_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCallConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCallConfDao.class);

    /**
     * <p>Default Constructor
     */
    public FileCallConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileCallConfDao(Connection con) {
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
            sql.addSql("drop table FILE_CALL_CONF");

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
            sql.addSql(" create table FILE_CALL_CONF (");
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
     * <p>Insert FILE_CALL_CONF Data Bindding JavaBean
     * @param bean FILE_CALL_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileCallConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CALL_CONF(");
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
     * <p>Update FILE_CALL_CONF Data Bindding JavaBean
     * @param bean FILE_CALL_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileCallConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CALL_CONF");
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
     * <p>Select FILE_CALL_CONF All Data
     * @return List in FILE_CALL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCallConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileCallConfModel> ret = new ArrayList<FileCallConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CALL_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCallConfFromRs(rs));
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
     * <p>Select FILE_CALL_CONF
     * @param fdrSid FDR_SID ※更新対象のファイルSID
     * @param usrSid ユーザSID
     * @return FILE_CALL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCallConfModel> getCallConf(int fdrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileCallConfModel> ret = new ArrayList<FileCallConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CALL.FDR_SID as FDR_SID,");
            sql.addSql("   CALL.USR_SID as USR_SID,");
            sql.addSql("   COALESCE(UCONF.FUC_SMAIL_SEND,0) as FUC_SMAIL_SEND");
            sql.addSql(" from");
            //sql.addSql("   FILE_CABINET,");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY");
            sql.addSql("    where FDR_SID = ?");
            sql.addSql("    group by FDR_SID) DIR_MAXVERSION,");
            sql.addSql("   FILE_CALL_CONF CALL");
            sql.addSql("   left join");
            sql.addSql("     FILE_UCONF UCONF");
            sql.addSql("   on CALL.USR_SID = UCONF.USR_SID,");
            sql.addSql("   CMN_USRM USRM");
            /*sql.addSql(" where ");
            sql.addSql("   CALL.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CALL.USR_SID != ?");*/
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID = CALL.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   CALL.USR_SID != ?");     //更新者以外
            sql.addSql(" and ");
            sql.addSql("   CALL.USR_SID = USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   USRM.USR_JKBN = ?");
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            //閲覧が許可されていないユーザは対象外とする
            /*sql.addSql(" and");
            sql.addSql("   CALL.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");*/
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            /*sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     FILE_CABINET.FCB_ACCESS_KBN = ?");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       FILE_CABINET.FCB_ACCESS_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         CALL.USR_SID in (");
            sql.addSql("           select USR_SID from FILE_ACCESS_CONF");
            sql.addSql("           where FILE_ACCESS_CONF.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("           and FILE_ACCESS_CONF.USR_KBN = ?");
            sql.addSql("         )");
            sql.addSql("       or");
            sql.addSql("         CALL.USR_SID in (");
            sql.addSql("           select");
            sql.addSql("             CMN_BELONGM.USR_SID");
            sql.addSql("           from");
            sql.addSql("             CMN_BELONGM,");
            sql.addSql("             FILE_ACCESS_CONF");
            sql.addSql("           where FILE_ACCESS_CONF.FCB_SID = FILE_CABINET.FCB_SID");
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
            //ディレクトリのアクセス設定で判別する
            sql.addSql(" and (");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");

            //対象ディレクトリのアクセス権限を持つユーザ
            sql.addSql(" or exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
            sql.addSql("    and (");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       A.USR_SID = CALL.USR_SID) or");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       exists");
            sql.addSql("         (select *");
            sql.addSql("          from");
            sql.addSql("            CMN_BELONGM B");
            sql.addSql("          where");
            sql.addSql("            B.GRP_SID = A.USR_SID");
            sql.addSql("          and");
            sql.addSql("            B.USR_SID = CALL.USR_SID");
            sql.addSql("          )))");
            sql.addSql("   )");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);

            //キャビネット管理者
            sql.addSql(" or exists");
            sql.addSql("   (");
            sql.addSql("     select * from FILE_CABINET_ADMIN C");
            sql.addSql("     where");
            sql.addSql("       C.FCB_SID = FILE_DIRECTORY.FCB_SID");
            sql.addSql("     and");
            sql.addSql("       C.USR_SID = CALL.USR_SID");
            sql.addSql("   )");

            //プラグイン管理者
            sql.addSql(" or exists");
            sql.addSql("   (");
            sql.addSql("     select * from");
            sql.addSql("       CMN_PLUGIN_ADMIN D");
            sql.addSql("       left join");
            sql.addSql("         CMN_BELONGM");
            sql.addSql("       on");
            sql.addSql("         D.GRP_SID > 0");
            sql.addSql("       and");
            sql.addSql("         D.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("     where");
            sql.addSql("       D.PCT_PID = ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         D.USR_SID = CALL.USR_SID");
            sql.addSql("       or");
            sql.addSql("         CMN_BELONGM.USR_SID = CALL.USR_SID");
            sql.addSql("       )");
            sql.addSql("   )");
            sql.addStrValue(GSConstFile.PLUGIN_ID_FILE);

            //システム管理者
            sql.addSql(" or exists");
            sql.addSql("   (");
            sql.addSql("     select * from CMN_BELONGM E");
            sql.addSql("     where");
            sql.addSql("       E.GRP_SID = ?");
            sql.addSql("     and");
            sql.addSql("       E.USR_SID = CALL.USR_SID");
            sql.addSql("   )");
            sql.addIntValue(GSConstUser.SID_ADMIN);

            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FileCallConfModel bean = new FileCallConfModel();
                bean.setFdrSid(rs.getInt("FDR_SID"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setFucSmailSend(rs.getInt("FUC_SMAIL_SEND"));
                ret.add(bean);
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
     * <p>Select FILE_CALL_CONF
     * @param fdrSid FDR_SID
     * @param usrSid USR_SID
     * @return FILE_CALL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public FileCallConfModel select(int fdrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCallConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_CONF");
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
                ret = __getFileCallConfFromRs(rs);
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
     * <p>Select FILE_CALL_CONF
     * @param fdrSid FDR_SID
     * @return FILE_CALL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCallConfModel> select(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCallConfModel> ret = new ArrayList<FileCallConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CALL_CONF");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCallConfFromRs(rs));
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
     * <p>キャビネットSIDを指定してキャビネットのrootディレクトリに通知設定がされているか判定する
     * @param fcbSid FCB_SID
     * @param usrSid USR_SID
     * @return FILE_CALL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public boolean isCabinetCallSetting(int fcbSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   1");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   FILE_CALL_CONF");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID=?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN=?");
            sql.addSql(" and");
            sql.addSql("   FILE_CALL_CONF.FDR_SID=FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CALL_CONF.USR_SID=?");
            sql.setPagingValue(0, 1);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ret = rs.next();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete FILE_CALL_CONF
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
            sql.addSql("   FILE_CALL_CONF");
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
     * <p>Delete FILE_CALL_CONF
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
            sql.addSql("   FILE_CALL_CONF");
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
     * <p>Delete FILE_CALL_CONF
     * @param fdrMdlList List in FileDirectoryModel
     * @param usrSid USR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(List<FileDirectoryModel> fdrMdlList, int usrSid) throws SQLException {

        int count = 0;
        if (fdrMdlList == null || fdrMdlList.size() < 1) {
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
            sql.addSql("   FILE_CALL_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(usrSid);
            sql.addSql(" and");
            sql.addSql("   (");
            int n = 0;
            for (FileDirectoryModel fdrMdl : fdrMdlList) {
                if (n > 0) {
                    sql.addSql(" or ");
                }
                sql.addSql(" FDR_SID=?");
                sql.addIntValue(fdrMdl.getFdrSid());
                n++;
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
            sql.addSql("   FILE_CALL_CONF");
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
     * <p>指定したディレクトリSIDを親に持つディレクトリの更新通知情報を削除する
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
            sql.addSql(" delete from FILE_CALL_CONF");
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
     * <p>指定したキャビネットに関係し、かつアクセスが許可されていないユーザの更新通知情報を全て削除する
     * @param fcbSid キャビネットSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteWithCabinetWithoutUser(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from FILE_CALL_CONF");
            sql.addSql(" where");
            sql.addSql("   FDR_SID in (");
            sql.addSql("     select FDR_SID from FILE_DIRECTORY");
            sql.addSql("     where FCB_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in (");
            sql.addSql("     select USR_SID from FILE_ACCESS_CONF");
            sql.addSql("     where FCB_SID = ?");
            sql.addSql("     and USR_KBN = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in (");
            sql.addSql("     select");
            sql.addSql("       CMN_BELONGM.USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       FILE_ACCESS_CONF");
            sql.addSql("     where FILE_ACCESS_CONF.FCB_SID = ?");
            sql.addSql("     and FILE_ACCESS_CONF.USR_KBN = ?");
            sql.addSql("     and CMN_BELONGM.GRP_SID = FILE_ACCESS_CONF.USR_SID");
            sql.addSql("   )");
            sql.addIntValue(fcbSid);
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create FILE_CALL_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCallConfModel
     * @throws SQLException SQL実行例外
     */
    private FileCallConfModel __getFileCallConfFromRs(ResultSet rs) throws SQLException {
        FileCallConfModel bean = new FileCallConfModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
