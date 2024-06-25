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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.model.FileDirectoryBinModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <p>FILE_DIRECTORY_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDirectoryBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileDirectoryBinDao.class);

    /**
     * <p>Default Constructor
     */
    public FileDirectoryBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileDirectoryBinDao(Connection con) {
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
            sql.addSql("drop table FILE_DIRECTORY_BIN");

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
            sql.addSql(" create table FILE_DIRECTORY_BIN (");
            sql.addSql("   FDR_SID NUMBER(10,0) not null,");
            sql.addSql("   FDR_VERSION NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (FDR_SID,FDR_VERSION,BIN_SID)");
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
     * <p>Insert FILE_DIRECTORY_BIN Data Bindding JavaBean
     * @param bean FILE_DIRECTORY_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileDirectoryBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_DIRECTORY_BIN(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addLongValue(bean.getBinSid());
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
     * <p>Update FILE_DIRECTORY_BIN Data Bindding JavaBean
     * @param bean FILE_DIRECTORY_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileDirectoryBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addLongValue(bean.getBinSid());

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
     * <p>Select FILE_DIRECTORY_BIN All Data
     * @return List in FILE_DIRECTORY_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryBinModel> ret = new ArrayList<FileDirectoryBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryBinFromRs(rs));
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
     * <p>Select FILE_DIRECTORY_BIN
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @return List in FILE_DIRECTORY_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryBinModel> select(int fdrSid, int fdrVersion)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDirectoryBinModel> ret = new ArrayList<FileDirectoryBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN");
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
            while (rs.next()) {
                ret.add(__getFileDirectoryBinFromRs(rs));
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
     * <p>バイナリーSID一覧を取得
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @return List in Integer
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Long> getBinSidList(int fdrSid, int fdrVersion)
    throws SQLException {

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
            sql.addSql("   FILE_DIRECTORY_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY_BIN.FDR_VERSION = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_SID asc");
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVersion);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(Long.valueOf(rs.getLong("BIN_SID")));
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
     * <p>Select FILE_DIRECTORY_BIN
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @param binSid BIN_SID
     * @return FILE_DIRECTORY_BINModel
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryBinModel select(int fdrSid, int fdrVersion, Long binSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileDirectoryBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVersion);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileDirectoryBinFromRs(rs);
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
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY_BIN.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY_BIN.FDR_VERSION = ?");
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
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                bean.setBinFileSizeDsp(strSize);
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
     * <p>ディレクトリ情報一覧を元に添付ファイルのバイナリーSIDの一覧を取得する
     * @param dirList ArrayList in FileDirectoryModel
     * @return ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getBinSids(ArrayList<FileDirectoryModel> dirList)
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
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN");
            if (dirList != null) {
                sql.addSql(" where ");
                sql.addSql("   FDR_SID=-1");
                for (FileDirectoryModel mdl : dirList) {
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
     * <br>[機  能] 指定されたディレクトリの全バージョンを取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param listOfDir ディレクトリ(フォルダ)リスト
     * @return ret 取得したディレクトリモデルリスト
     * @throws SQLException 例外
     */
    public ArrayList<FileDirectoryBinModel> getFileListAllVersion(
            ArrayList<FileDirectoryModel> listOfDir) throws SQLException {

        ArrayList<FileDirectoryBinModel> ret = new ArrayList<FileDirectoryBinModel>();
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
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN");
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
                ret.add(__getFileDirectoryBinFromRs(rs));
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
     * <p>ディレクトリ添付情報をを削除する。
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
            sql.addSql("   FILE_DIRECTORY_BIN");
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
     * <p>Delete FILE_DIRECTORY_BIN
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION
     * @param binSid BIN_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int fdrSid, int fdrVersion, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVersion);
            sql.addLongValue(binSid);

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
            sql.addSql("   FILE_DIRECTORY_BIN");

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
     * <p>Create FILE_DIRECTORY_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileDirectoryBinModel
     * @throws SQLException SQL実行例外
     */
    private FileDirectoryBinModel __getFileDirectoryBinFromRs(ResultSet rs) throws SQLException {
        FileDirectoryBinModel bean = new FileDirectoryBinModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
