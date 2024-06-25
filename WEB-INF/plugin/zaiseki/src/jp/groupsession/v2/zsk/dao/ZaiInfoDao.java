package jp.groupsession.v2.zsk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.zsk.model.ZaiInfoModel;
import jp.groupsession.v2.zsk.model.ZaiInfoPlusModel;
import jp.groupsession.v2.zsk.zsk030.Zsk030Model;

/**
 * <p>ZAI_INFO Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiInfoDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZaiInfoDao.class);

    /**
     * <p>Default Constructor
     */
    public ZaiInfoDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ZaiInfoDao(Connection con) {
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
            sql.addSql("drop table ZAI_INFO");

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
            sql.addSql(" create table ZAI_INFO (");
            sql.addSql("   ZIF_SID NUMBER(10,0) not null,");
            sql.addSql("   ZIF_NAME varchar(50) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   ZIF_SORT NUMBER(10,0) not null,");
            sql.addSql("   ZIF_MSG varchar(1000),");
            sql.addSql("   ZIF_AUID NUMBER(10,0) not null,");
            sql.addSql("   ZIF_ADATE varchar(23) not null,");
            sql.addSql("   ZIF_EUID NUMBER(10,0) not null,");
            sql.addSql("   ZIF_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ZIF_SID)");
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
     * <p>Insert ZAI_INFO Data Bindding JavaBean
     * @param bean ZAI_INFO Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ZaiInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_INFO(");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_MSG,");
            sql.addSql("   ZIF_AUID,");
            sql.addSql("   ZIF_ADATE,");
            sql.addSql("   ZIF_EUID,");
            sql.addSql("   ZIF_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZifSid());
            sql.addStrValue(bean.getZifName());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getZifSort());
            sql.addStrValue(bean.getZifMsg());
            sql.addIntValue(bean.getZifAuid());
            sql.addDateValue(bean.getZifAdate());
            sql.addIntValue(bean.getZifEuid());
            sql.addDateValue(bean.getZifEdate());
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
     * <p>Update ZAI_INFO Data Bindding JavaBean
     * @param bean ZAI_INFO Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ZaiInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" set ");
            sql.addSql("   ZIF_NAME=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   ZIF_SORT=?,");
            sql.addSql("   ZIF_MSG=?,");
            sql.addSql("   ZIF_AUID=?,");
            sql.addSql("   ZIF_ADATE=?,");
            sql.addSql("   ZIF_EUID=?,");
            sql.addSql("   ZIF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getZifName());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getZifSort());
            sql.addStrValue(bean.getZifMsg());
            sql.addIntValue(bean.getZifAuid());
            sql.addDateValue(bean.getZifAdate());
            sql.addIntValue(bean.getZifEuid());
            sql.addDateValue(bean.getZifEdate());
            //where
            sql.addIntValue(bean.getZifSid());

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
     * <p>Update ZAI_INFO Data Bindding JavaBean
     * @param bean ZAI_INFO Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateNameAndImage(ZaiInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" set ");
            sql.addSql("   ZIF_NAME=?,");
            sql.addSql("   ZIF_SORT=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   ZIF_EUID=?,");
            sql.addSql("   ZIF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getZifName());
            sql.addIntValue(bean.getZifSort());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getZifEuid());
            sql.addDateValue(bean.getZifEdate());
            //where
            sql.addIntValue(bean.getZifSid());

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
     * 座席表に紐づく添付情報を更新します
     * @param zifSid 座席表SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateBinInfo(String zifSid) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       ZAI_INFO.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       ZAI_INFO");
            sql.addSql("     where");
            sql.addSql("       ZAI_INFO.ZIF_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(Integer.parseInt(zifSid));

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
     * <p>Select ZAI_INFO All Data
     * @return List in ZAI_INFOModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ZaiInfoModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiInfoModel> ret = new ArrayList<ZaiInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_MSG,");
            sql.addSql("   ZIF_AUID,");
            sql.addSql("   ZIF_ADATE,");
            sql.addSql("   ZIF_EUID,");
            sql.addSql("   ZIF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" order by ");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   ZIF_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZaiInfoFromRs(rs));
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
     * <p>在席一覧表示用モデルのリストを取得する
     * @return List in ZAI_INFOModel + 最終更新日付文字列
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Zsk030Model> getZsk030ModelList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Zsk030Model> ret = new ArrayList<Zsk030Model>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_MSG,");
            sql.addSql("   ZIF_AUID,");
            sql.addSql("   ZIF_ADATE,");
            sql.addSql("   ZIF_EUID,");
            sql.addSql("   ZIF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" order by ");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZsk030ModelFromRs(rs));
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
     * <p>Select ZAI_INFO
     * @param bean ZAI_INFO Model
     * @return ZAI_INFOModel
     * @throws SQLException SQL実行例外
     */
    public ZaiInfoModel select(ZaiInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiInfoModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_MSG,");
            sql.addSql("   ZIF_AUID,");
            sql.addSql("   ZIF_ADATE,");
            sql.addSql("   ZIF_EUID,");
            sql.addSql("   ZIF_EDATE");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZifSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiInfoFromRs(rs);
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
     * <p>座席表SIDを指定し座席表情報を取得する
     * @param sid 座席表SID
     * @return ZAI_INFOModel
     * @throws SQLException SQL実行例外
     */
    public ZaiInfoModel select(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiInfoModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_MSG,");
            sql.addSql("   ZIF_AUID,");
            sql.addSql("   ZIF_ADATE,");
            sql.addSql("   ZIF_EUID,");
            sql.addSql("   ZIF_EDATE");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiInfoFromRs(rs);
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
     * <p>Select ZAI_INFO
     * @param sid ZAI_INFO SID
     * @return ZAI_INFOModel
     * @throws SQLException SQL実行例外
     */
    public ZaiInfoPlusModel getZaiInfoPlusModel(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiInfoPlusModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIF_NAME,");
            sql.addSql("   ZAI_INFO.BIN_SID,");
            sql.addSql("   ZIF_SORT,");
            sql.addSql("   ZIF_MSG,");
            sql.addSql("   ZIF_AUID,");
            sql.addSql("   ZIF_ADATE,");
            sql.addSql("   ZIF_EUID,");
            sql.addSql("   ZIF_EDATE,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BIN_SID,");
            sql.addSql("         BIN_FILE_NAME,");
            sql.addSql("         BIN_FILE_PATH");
            sql.addSql("       from");
            sql.addSql("         CMN_BINF");
            sql.addSql("       where");
            sql.addSql("         BIN_JKBN = 0");
            sql.addSql("      ) BINF");
            sql.addSql("    on");
            sql.addSql("      ZAI_INFO.BIN_SID = BINF.BIN_SID");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiInfoPlusFromRs(rs);
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
     * <br>[機  能] 指定したバイナリSIDが座席票の画像かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @return true:座席票のSID  false:それ以外
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckZaiImage(Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return cnt > 0;
    }

    /**
     * <br>[機  能] 指定した座席表情報のデータサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param zifSid 座席表SID
     * @return データサイズ
     * @throws SQLException SQL実行例外
     */
    public long getDataSize(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(ZIF_NAME)) as NAME_SIZE,");
            sql.addSql("   sum(octet_length(ZIF_MSG)) as MSG_SIZE");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" where");
            sql.addSql("   ZIF_SID = ?");
            sql.addIntValue(zifSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("NAME_SIZE");
                dataSize += rs.getLong("MSG_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dataSize;
    }

    /**
     * <br>[機  能] 指定した座席表情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param zifSid 座席表SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getSumFileSize(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   ZAI_INFO.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   ZIF_SID = ?");
            sql.addIntValue(zifSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <p>Delete ZAI_INFO
     * @param bean ZAI_INFO Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(ZaiInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZifSid());

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
     * <p>Delete ZAI_INFO
     * @param sid ZIF_SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_INFO");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);

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

//    /**
//     * 座席表画像ファイルをテンポラリディレクトリへ保存する
//     * @param fileName 画像ファイル名
//     * @param tempFilePath 画像ファイルパス
//     * @param appRoot アプリケーションのルートパス
//     * @param tempDir テンポラリディレクトリ
//     * @throws IOException 画像ファイルの保存に失敗
//     * @throws IOToolsException 画像ファイルの保存に失敗
//     */
//    private void __savePhotoFile(String fileName, String tempFilePath,
//                                String appRoot, String tempDir)
//    throws IOException, IOToolsException {
//
//        CommonBiz biz = new CommonBiz();
//        //添付ファイル保存用パス(フルパス)
//        String filePath = biz.getSaveFullPath(appRoot, tempFilePath);
//        //ファイルの有効性チェック(ない場合に作成)
//        IOTools.isFileCheck(tempDir, fileName, true);
//        //添付ファイルを保存
//        IOTools.copyBinFile(filePath, tempDir + fileName);
//    }
    /**
     * <p>Create ZAI_INFO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiInfoModel
     * @throws SQLException SQL実行例外
     */
    private ZaiInfoModel __getZaiInfoFromRs(ResultSet rs) throws SQLException {
        ZaiInfoModel bean = new ZaiInfoModel();
        bean.setZifSid(rs.getInt("ZIF_SID"));
        bean.setZifName(rs.getString("ZIF_NAME"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setZifSort(rs.getInt("ZIF_SORT"));
        bean.setZifMsg(rs.getString("ZIF_MSG"));
        bean.setZifAuid(rs.getInt("ZIF_AUID"));
        bean.setZifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_ADATE")));
        bean.setZifEuid(rs.getInt("ZIF_EUID"));
        bean.setZifEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_EDATE")));
        return bean;
    }

    /**
     * <p>Create ZAI_INFO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiInfoModel
     * @throws SQLException SQL実行例外
     */
    private ZaiInfoPlusModel __getZaiInfoPlusFromRs(ResultSet rs) throws SQLException {
        ZaiInfoPlusModel bean = new ZaiInfoPlusModel();
        bean.setZifSid(rs.getInt("ZIF_SID"));
        bean.setZifName(rs.getString("ZIF_NAME"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setZifSort(rs.getInt("ZIF_SORT"));
        bean.setZifMsg(rs.getString("ZIF_MSG"));
        bean.setZifAuid(rs.getInt("ZIF_AUID"));
        bean.setZifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_ADATE")));
        bean.setZifEuid(rs.getInt("ZIF_EUID"));
        bean.setZifEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_EDATE")));
        bean.setZifFileName(rs.getString("BIN_FILE_NAME"));
        bean.setZifFilePath(rs.getString("BIN_FILE_PATH"));
        return bean;
    }
    /**
     * <p>座席一覧表示用モデルをResultSetから生成します
     * @param rs ResultSet
     * @return created ZaiInfoModel
     * @throws SQLException SQL実行例外
     */
    private Zsk030Model __getZsk030ModelFromRs(ResultSet rs) throws SQLException {
        Zsk030Model bean = new Zsk030Model();
        bean.setZifSid(rs.getInt("ZIF_SID"));
        bean.setZifName(rs.getString("ZIF_NAME"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setZifSort(rs.getInt("ZIF_SORT"));
        bean.setZifMsg(rs.getString("ZIF_MSG"));
        bean.setZifAuid(rs.getInt("ZIF_AUID"));
        bean.setZifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_ADATE")));
        bean.setZifEuid(rs.getInt("ZIF_EUID"));
        bean.setZifEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_EDATE")));
        bean.setLastUpdateDate(
                UDateUtil.getSlashYYMD(
                        UDate.getInstanceTimestamp(rs.getTimestamp("ZIF_EDATE"))));
        return bean;
    }
}
