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
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileTextModel;


/**
 * <p>FILE_FILE_TEXT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileFileTextDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileFileTextDao.class);

    /**
     * <p>Default Constructor
     */
    public FileFileTextDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileFileTextDao(Connection con) {
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
            sql.addSql("drop table FILE_FILE_TEXT");

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
            sql.addSql(" create table FILE_FILE_TEXT (");
            sql.addSql("   FDR_SID NUMBER(10,0) not null,");
            sql.addSql("   FDR_VERSION NUMBER(10,0) not null,");
            sql.addSql("   FCB_SID NUMBER(10,0) not null,");
            sql.addSql("   FFT_SEC_NO NUMBER(10,0) not null,");
            sql.addSql("   FFT_TEXT text,");
            sql.addSql("   FFT_BIKO varchar(3000),");
            sql.addSql("   FFT_READ_KBN NUMBER(10,0) not null,");
            sql.addSql("   primary key (FDR_SID,FDR_VERSION,FFT_SEC_NO)");
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
     * <p>Insert FILE_FILE_TEXT Data Bindding JavaBean
     * @param bean FILE_FILE_TEXT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileFileTextModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_TEXT(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FFT_SEC_NO,");
            sql.addSql("   FFT_TEXT,");
            sql.addSql("   FFT_BIKO,");
            sql.addSql("   FFT_READ_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
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
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFftSecNo());
            sql.addStrValue(bean.getFftText());
            sql.addStrValue(bean.getFftBiko());
            sql.addIntValue(bean.getFftReadKbn());
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
     * <p>Update FILE_FILE_TEXT Data Bindding JavaBean
     * @param bean FILE_FILE_TEXT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileFileTextModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_FILE_TEXT");
            sql.addSql(" set ");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FFT_TEXT=?,");
            sql.addSql("   FFT_BIKO=?,");
            sql.addSql("   FFT_READ_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   FDR_VERSION=?");
            sql.addSql(" and ");
            sql.addSql("   FFT_SEC_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addStrValue(bean.getFftText());
            sql.addStrValue(bean.getFftBiko());
            sql.addIntValue(bean.getFftReadKbn());
            //where
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addIntValue(bean.getFftSecNo());

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
     * <p>Select FILE_FILE_TEXT All Data
     * @return List in FILE_FILE_TEXTModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileTextModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileTextModel> ret = new ArrayList<FileFileTextModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FFT_SEC_NO,");
            sql.addSql("   FFT_TEXT,");
            sql.addSql("   FFT_BIKO,");
            sql.addSql("   FFT_READ_KBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_FILE_TEXT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileTextFromRs(rs));
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
     * <p>ファイル中身のテキストを読み込んでいないファイル一覧を取得する
     * @param jtkbn 状態区分
     * @return List in FILE_FILE_TEXTModel
     * @throws SQLException SQL実行例外
     */
    public List<FileFileTextModel> getNoReadFileList(int jtkbn)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileFileTextModel> ret = new ArrayList<FileFileTextModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY");
            sql.addSql("    where FDR_KBN=?");
            sql.addSql("    and FDR_JTKBN=? ");
            sql.addSql("    group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and not exists");
            sql.addSql("   (select *");
            sql.addSql("    from");
            sql.addSql("      FILE_FILE_TEXT");
            sql.addSql("    where");
            sql.addSql("      FILE_DIRECTORY.FDR_SID = FILE_FILE_TEXT.FDR_SID");
            sql.addSql("    and");
            sql.addSql("      FILE_DIRECTORY.FDR_VERSION = FILE_FILE_TEXT.FDR_VERSION");
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID");
            sql.addIntValue(GSConstFile.DIRECTORY_FILE);
            sql.addIntValue(jtkbn);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileFileTextSidFromRs(rs));
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
     * <br>[機  能] 「バージョンが最新バージョンと異なるテキスト情報」のバージョンを最新バージョンに更新する
     * <br>[解  説] 削除済みファイルのみを対象とする
     * <br>[備  考]
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateVersionForOldDeleteFile() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_FILE_TEXT");
            sql.addSql(" set");
            sql.addSql("   FDR_VERSION = FILE_FILE_TEXT.FDR_VERSION + 1");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("     from FILE_DIRECTORY");
            sql.addSql("     where FDR_KBN=?");
            sql.addSql("     and FDR_JTKBN=?");
            sql.addSql("     group by FDR_SID");
            sql.addSql("   ) DIR_MAXVERSION");
            sql.addSql(" where ");
            sql.addSql("   FILE_FILE_TEXT.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and ");
            sql.addSql("   FILE_FILE_TEXT.FDR_VERSION < DIR_MAXVERSION.MAXVERSION");
            sql.addIntValue(GSConstFile.DIRECTORY_FILE);
            sql.addIntValue(GSConst.JTKBN_DELETE);

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
     * <p>Delete FILE_FILE_TEXT
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
            sql.addSql("   FILE_FILE_TEXT");
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
     * <p>Delete FILE_FILE_TEXT
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
            sql.addSql("   FILE_FILE_TEXT");
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
            sql.addSql("   FILE_FILE_TEXT");
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
     * <p>Create FILE_FILE_TEXT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileFileTextModel
     * @throws SQLException SQL実行例外
     */
    private FileFileTextModel __getFileFileTextFromRs(ResultSet rs) throws SQLException {
        FileFileTextModel bean = new FileFileTextModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFftSecNo(rs.getInt("FFT_SEC_NO"));
        bean.setFftText(rs.getString("FFT_TEXT"));
        bean.setFftText(rs.getString("FFT_BIKO"));
        bean.setFftReadKbn(rs.getInt("FFT_READ_KBN"));

        return bean;
    }


    /**
     * <p>Create FILE_FILE_TEXT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileFileTextModel
     * @throws SQLException SQL実行例外
     */
    private FileFileTextModel __getFileFileTextSidFromRs(ResultSet rs) throws SQLException {
        FileFileTextModel bean = new FileFileTextModel();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setFcbSid(rs.getInt("FCB_SID"));

        return bean;
    }
}
