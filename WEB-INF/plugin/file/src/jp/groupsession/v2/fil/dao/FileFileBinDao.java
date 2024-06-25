package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinDspModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;


/**
 * <p>FILE_FILE_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileFileBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileFileBinDao.class);

    /**
     * <p>Default Constructor
     */
    public FileFileBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileFileBinDao(Connection con) {
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
            sql.addSql("drop table FILE_FILE_BIN");

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
            sql.addSql(" create table FILE_FILE_BIN (");
            sql.addSql("   FDR_SID NUMBER(10,0) not null,");
            sql.addSql("   FDR_VERSION NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   FFL_EXT varchar(255) not null,");
            sql.addSql("   FFL_FILE_SIZE NUMBER(10,0) not null,");
            sql.addSql("   FFL_LOCK_KBN NUMBER(10,0) not null,");
            sql.addSql("   FFL_LOCK_USER NUMBER(10,0) not null,");
            sql.addSql("   FFL_LOCK_DATE varchar(23),");
            sql.addSql("   primary key (FDR_SID,FDR_VERSION)");
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
     * <p>Insert FILE_FILE_BIN Data Bindding JavaBean
     * @param bean FILE_FILE_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileFileBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_BIN(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getFflExt());
            sql.addLongValue(bean.getFflFileSize());
            sql.addIntValue(bean.getFflLockKbn());
            sql.addIntValue(bean.getFflLockUser());
            sql.addDateValue(bean.getFflLockDate());
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
     * <p>Insert FILE_FILE_BIN Data Bindding JavaBean
     * @param beanList FILE_FILE_BIN DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<FileFileBinModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_BIN(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (FileFileBinModel bean : beanList) {
                sql.addIntValue(bean.getFdrSid());
                sql.addIntValue(bean.getFdrVersion());
                sql.addLongValue(bean.getBinSid());
                sql.addStrValue(bean.getFflExt());
                sql.addLongValue(bean.getFflFileSize());
                sql.addIntValue(bean.getFflLockKbn());
                sql.addIntValue(bean.getFflLockUser());
                sql.addDateValue(bean.getFflLockDate());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update FILE_FILE_BIN Data Bindding JavaBean
     * @param bean FILE_FILE_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileFileBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   FFL_EXT=?,");
            sql.addSql("   FFL_FILE_SIZE=?,");
            sql.addSql("   FFL_LOCK_KBN=?,");
            sql.addSql("   FFL_LOCK_USER=?,");
            sql.addSql("   FFL_LOCK_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getFflExt());
            sql.addLongValue(bean.getFflFileSize());
            sql.addIntValue(bean.getFflLockKbn());
            sql.addIntValue(bean.getFflLockUser());
            sql.addDateValue(bean.getFflLockDate());
            //where
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());

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
     * <p>ロック区分を更新する。
     * @param bean FILE_FILE_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateLockKbn(FileFileBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" set ");
            sql.addSql("   FFL_LOCK_KBN=?,");
            sql.addSql("   FFL_LOCK_USER=?,");
            sql.addSql("   FFL_LOCK_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFflLockKbn());
            sql.addIntValue(bean.getFflLockUser());
            sql.addDateValue(bean.getFflLockDate());
            //where
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());

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
     * <p>ロック区分を更新する。
     * @param bean FILE_FILE_BIN Data Bindding JavaBean
     * @param fdrSids ファイルSIDリスト
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateLockKbn(FileFileBinModel bean, String[] fdrSids) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" set ");
            sql.addSql("   FFL_LOCK_KBN=?,");
            sql.addSql("   FFL_LOCK_USER=?,");
            sql.addSql("   FFL_LOCK_DATE=?");
            sql.addIntValue(bean.getFflLockKbn());
            sql.addIntValue(bean.getFflLockUser());
            sql.addDateValue(bean.getFflLockDate());

            //where
            sql.addSql(" where ");
            int i = 0;
            for (String fdrSid : fdrSids) {
                if (i > 0) {
                    sql.addSql(" or ");
                }
                sql.addSql("   FDR_SID=?");
                sql.addIntValue(NullDefault.getInt(fdrSid, -1));
                i++;
            }

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
     * <p>Select FILE_FILE_BIN All Data
     * @return List in FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileBinModel> ret = new ArrayList<FileFileBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from ");
            sql.addSql("   FILE_FILE_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileBinFromRs(rs));
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
     * <p>Select FILE_FILE_BIN All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileBinModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileBinModel> ret = new ArrayList<FileFileBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from ");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" order by ");
            sql.addSql("   FDR_SID asc,");
            sql.addSql("   FDR_VERSION asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileBinFromRs(rs));
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
     * <p>全件数を取得する
     * @return List in FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   FILE_FILE_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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
     * <p>Select FILE_FILE_BIN
     * @param fdrSid FDR_SID
     * @return FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileBinModel> select(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileBinModel> ret = new ArrayList<FileFileBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileBinFromRs(rs));
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
     * <p>指定したディレクトリSIDを親に持つファイル情報を取得する
     * @param fdrSid FDR_SID
     * @return FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileBinModel> selectParent(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileBinModel> ret = new ArrayList<FileFileBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select FDR_SID");
            sql.addSql("       ,FDR_VERSION");
            sql.addSql("       ,BIN_SID");
            sql.addSql("       ,FFL_EXT");
            sql.addSql("       ,FFL_FILE_SIZE");
            sql.addSql("       ,FFL_LOCK_KBN");
            sql.addSql("       ,FFL_LOCK_USER");
            sql.addSql("       ,FFL_LOCK_DATE");
            sql.addSql("   from FILE_FILE_BIN");
            sql.addSql("  where FDR_SID in (select FDR_SID");
            sql.addSql("                      from FILE_DIRECTORY");
            sql.addSql("                     where FDR_PARENT_SID = ?");
            sql.addSql("                   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileBinFromRs(rs));
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
     * <p>Select FILE_FILE_BIN
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @return FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public FileFileBinModel select(int fdrSid, int fdrVersion) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileFileBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVersion);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileFileBinFromRs(rs);
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
     * <br>[機  能] 指定されたディレクトリの全バージョンを取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param listOfDir ディレクトリ(フォルダ)リスト
     * @return ret 取得したディレクトリモデルリスト
     * @throws SQLException 例外
     */
    public ArrayList<FileFileBinModel> getFileListAllVersion(
            ArrayList<FileDirectoryModel> listOfDir) throws SQLException {
        
        ArrayList<FileFileBinModel> ret = new ArrayList<FileFileBinModel>();
        if (listOfDir == null || listOfDir.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID in (");

            for (int i = 0; i < listOfDir.size(); i++) {

                FileDirectoryModel bean = (FileDirectoryModel) listOfDir.get(i);
                sql.addSql("?");
                sql.addIntValue(bean.getFdrSid());

                if (i != listOfDir.size() - 1) {
                    sql.addSql(", ");
                }
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getFileFileBinFromRs(rs));
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
     * <p>指定したバージョンより古い情報を取得する。
     * @param fdrSid FDR_SID
     * @param fdrVarKbn FDR_VERSION
     * @return FILE_FILE_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileBinModel> getOldVersion(int fdrSid, int fdrVarKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileBinModel> ret = new ArrayList<FileFileBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION < ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVarKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileBinFromRs(rs));
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
     * <br>[機  能] 添付ファイル情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param version バージョンSID
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getBinList(int fdrSid, int version) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_SID asc");
            sql.addIntValue(fdrSid);
            sql.addIntValue(version);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel bean = new CmnBinfModel();
                bean.setBinSid(rs.getLong("BIN_SID"));
                bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
                bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
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
     * <br>[機  能] バイナリーSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Long> getBinSidList(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Long> ret = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_SID asc");
            sql.addIntValue(fdrSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getLong("BIN_SID"));
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
     * <br>[機  能] バイナリーSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param version バージョンSID
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Long> getBinSidList(int fdrSid, int version) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Long> ret = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_SID asc");
            sql.addIntValue(fdrSid);
            sql.addIntValue(version);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getLong("BIN_SID"));
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
     * <br>[機  能] バイナリーSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param version バージョンSID
     * @return CMN_BINF.BIN_SID
     * @throws SQLException SQL実行例外
     */
    public Long getCmnBinSid(int fdrSid, int version) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Long ret = Long.valueOf(0);
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addIntValue(fdrSid);
            sql.addIntValue(version);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getLong("BIN_SID");
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
     * <p>ディレクトリ情報一覧を元に添付ファイルのバイナリーSIDの一覧を取得する
     * @param fileDirList ArrayList in FileDirectoryModel
     * @return ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getBinSids(List<FileDirectoryModel> fileDirList)
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
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            if (fileDirList != null) {
                sql.addSql(" where ");
                sql.addSql("   FDR_SID=-1");
                for (FileDirectoryModel mdl : fileDirList) {
                    sql.addSql(" or");
                    sql.addSql("   FDR_SID=?");
                    sql.addIntValue(mdl.getFdrSid());
                }
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("BIN_SID"));
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
     * <p>ディレクトリ情報一覧を元に、ディレクトリSIDに対応した添付ファイルのバイナリーSIDマップを取得する
     * @param fdrSidList ArrayList in Integer
     * @return ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Long> getBinSidsMap(List<Integer> fdrSidList)
    throws SQLException {

        Map<Integer, Long> ret = new HashMap<Integer, Long>();
        if (fdrSidList == null || fdrSidList.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_FILE_BIN.FDR_SID as FDR_SID,");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_EXT as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_FILE_SIZE as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_KBN as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_USER as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_DATE as BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");

            //最新のファイル情報を取得
            sql.addSql("   (");
            sql.addSql("     select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("     from FILE_FILE_BIN");
            if (fdrSidList != null) {
                sql.addSql("     where ");
                sql.addSql("       FDR_SID=-1");
                for (int fdrSid : fdrSidList) {
                    sql.addSql("     or");
                    sql.addSql("       FDR_SID=?");
                    sql.addIntValue(fdrSid);
                }
            }
            sql.addSql("     group by FDR_SID");
            sql.addSql("   ) FILE_BIN_MAXVER");

            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = FILE_BIN_MAXVER.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = FILE_BIN_MAXVER.FDR_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("FDR_SID"), rs.getLong("BIN_SID"));
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
     * <p>指定したファイルの最新バージョンの情報を取得する。
     * @param fdrSid FDR_SID
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public FileFileBinModel getNewFile(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileFileBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_FILE_BIN.FDR_SID as FDR_SID,");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_FILE_BIN.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_KBN as FFL_LOCK_KBN,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_USER as FFL_LOCK_USER,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_DATE as FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_BIN group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addIntValue(fdrSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileFileBinFromRs(rs);
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
     * <p>指定したファイルのロック中の最新バージョンの情報を取得する。
     * @param fdrMdls FileDirectoryModel
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getLockNewFile(List<FileDirectoryModel> fdrMdls) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        if (fdrMdls == null || fdrMdls.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_FILE_BIN.FDR_SID as FDR_SID,");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_FILE_BIN.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_FILE_BIN.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_KBN as FFL_LOCK_KBN,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_USER as FFL_LOCK_USER,");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_DATE as FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_BIN group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_KBN  = ?");
            sql.addIntValue(GSConstFile.LOCK_KBN_ON);
            sql.addSql(" and");
            sql.addSql("    FILE_FILE_BIN.FDR_SID in (");
            int i = 0;
            for (FileDirectoryModel fdrMdl : fdrMdls) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(fdrMdl.getFdrSid());
                i++;
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");


            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("FDR_SID"));
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
     * <p>指定したファイルの最新バージョンの情報を取得する。(ロックユーザ名も取得する)
     * @param fdrSid FDR_SID
     * @return FileFileBinDspModel
     * @throws SQLException SQL実行例外
     */
    public FileFileBinDspModel getNewFileUsrName(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileFileBinDspModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_BIN.FDR_SID as FDR_SID,");
            sql.addSql("   FILE_BIN.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_BIN.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_BIN.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_BIN.FFL_LOCK_KBN as FFL_LOCK_KBN,");
            sql.addSql("   FILE_BIN.FFL_LOCK_USER as FFL_LOCK_USER,");
            sql.addSql("   FILE_BIN.FFL_LOCK_DATE as FFL_LOCK_DATE,");
            sql.addSql("   FILE_BIN.USI_SEI as USI_SEI,");
            sql.addSql("   FILE_BIN.USI_MEI as USI_MEI");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     FILE_FILE_BIN.FDR_SID as FDR_SID,");
            sql.addSql("     FILE_FILE_BIN.FDR_VERSION as FDR_VERSION,");
            sql.addSql("     FILE_FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("     FILE_FILE_BIN.FFL_EXT as FFL_EXT,");
            sql.addSql("     FILE_FILE_BIN.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_KBN as FFL_LOCK_KBN,");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_USER as FFL_LOCK_USER,");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_DATE as FFL_LOCK_DATE,");
            sql.addSql("     USRM.USI_SEI as USI_SEI,");
            sql.addSql("     USRM.USI_MEI as USI_MEI");
            sql.addSql("   from");
            sql.addSql("     (FILE_FILE_BIN left join ");
            sql.addSql("     CMN_USRM_INF USRM on FILE_FILE_BIN.FFL_LOCK_USER=USRM.USR_SID)");
            sql.addSql("     )");
            sql.addSql("     as FILE_BIN,");

            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_BIN group by FDR_SID) DIR_MAXVERSION");

            sql.addSql(" where");
            sql.addSql("   FILE_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_BIN.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_BIN.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addIntValue(fdrSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileFileBinDspFromRs(rs);
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
     * <p>指定したファイルが格納されているキャビネットのSIDを取得する。
     * @param binSid バイナリSID
     * @param isOnlyNewVersion 最新のファイル場合のみSIDを返すか
     * @return キャビネットSID
     * @throws SQLException SQL実行例外
     */
    public int getCabinetSid(long binSid, boolean isOnlyNewVersion) throws SQLException {
        if (isOnlyNewVersion) {
            return getCabinetSid(binSid);
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int fcbSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DIRECTORY.FCB_SID as FCB_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = FILE_DIRECTORY.FDR_VERSION");

            sql.addLongValue(binSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fcbSid = rs.getInt("FCB_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fcbSid;

    }
    /**
     * <p>指定したファイルが格納されているキャビネットのSIDを取得する。
     * @param binSid バイナリSID
     * @return キャビネットSID
     * @throws SQLException SQL実行例外
     */
    public int getCabinetSid(long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int fcbSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DIRECTORY.FCB_SID as FCB_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_BIN group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.BIN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = FILE_DIRECTORY.FDR_SID");

            sql.addLongValue(binSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fcbSid = rs.getInt("FCB_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fcbSid;
    }

    /**
     * <p>指定したバイナリSIDがファイル情報テーブルに存在するかカウントする。
     * @param binSid バイナリSID
     * @return キャビネットSID
     * @throws SQLException SQL実行例外
     */
    public int getBinCount(long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where");
            sql.addSql("   BIN_SID = ?");

            sql.addLongValue(binSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
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
     * <p>Delete FILE_FILE_BIN
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int fdrSid, int fdrVersion) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVersion);

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
     * <p>Delete FILE_FILE_BIN
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
            sql.addSql("   FILE_FILE_BIN");
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
            sql.addSql("   FILE_FILE_BIN");
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
     * <p>指定したバージョンより古いものを削除する。
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteOldVersion(int fdrSid, int fdrVersion) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION < ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVersion);

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
     * <br>[機  能] 論理削除されたファイルの関連情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSidList ディレクトリSIDリスト
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int insertDelFileData(List<Integer> fdrSidList) throws SQLException {

        int count = 0;
        if (fdrSidList == null || fdrSidList.size() == 0) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into FILE_FILE_BIN (");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" )");
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION + 1,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_BIN,");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      FDR_SID as MAX_FDR_SID,");
            sql.addSql("      max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_BIN");
            sql.addSql("    where ");
            sql.addSql("      FDR_SID in (");
            for (int idx = 0; idx < fdrSidList.size(); idx++) {
                sql.addSql("        ?");
                if (idx != fdrSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(fdrSidList.get(idx));
            }
            sql.addSql("      )");
            sql.addSql("    group by FDR_SID) BIN_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_FILE_BIN.FDR_SID = BIN_MAXVERSION.MAX_FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_FILE_BIN.FDR_VERSION = BIN_MAXVERSION.MAXVERSION");
            sql.addSql(" order by ");
            sql.addSql("   FDR_SID");

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
     * <br>[機  能] 指定したバイナリSIDに関するファイル情報が存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     * @param binSid ファイルバイナリSID
     * @param isOnlyNewversion 最新バージョンのみ:true
     * @param deleteCheck 論理削除チェック:true
     * @return boolean in true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existsBinFile(Long binSid,
            boolean isOnlyNewversion,
            boolean deleteCheck) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1");
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

            if (deleteCheck) {
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create FILE_FILE_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileFileBinModel
     * @throws SQLException SQL実行例外
     */
    private FileFileBinModel __getFileFileBinFromRs(ResultSet rs) throws SQLException {
        FileFileBinModel bean = new FileFileBinModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setFflExt(rs.getString("FFL_EXT"));
        bean.setFflFileSize(rs.getLong("FFL_FILE_SIZE"));
        bean.setFflLockKbn(rs.getInt("FFL_LOCK_KBN"));
        bean.setFflLockUser(rs.getInt("FFL_LOCK_USER"));
        bean.setFflLockDate(UDate.getInstanceTimestamp(rs.getTimestamp("FFL_LOCK_DATE")));

        return bean;
    }

    /**
     * <p>Create FILE_FILE_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileFileBinModel
     * @throws SQLException SQL実行例外
     */
    private FileFileBinDspModel __getFileFileBinDspFromRs(ResultSet rs) throws SQLException {
        FileFileBinDspModel bean = new FileFileBinDspModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setFflExt(rs.getString("FFL_EXT"));
        bean.setFflFileSize(rs.getLong("FFL_FILE_SIZE"));
        bean.setFflLockKbn(rs.getInt("FFL_LOCK_KBN"));
        bean.setFflLockUser(rs.getInt("FFL_LOCK_USER"));
        bean.setFflLockDate(UDate.getInstanceTimestamp(rs.getTimestamp("FFL_LOCK_DATE")));
        bean.setFdrLockUsrName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
        return bean;
    }


}
