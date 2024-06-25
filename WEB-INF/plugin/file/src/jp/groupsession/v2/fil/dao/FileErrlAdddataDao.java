package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

/**
 * <p>FILE_ERRL_ADDDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileErrlAdddataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileErrlAdddataDao.class);

    /**
     * <p>Default Constructor
     */
    public FileErrlAdddataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileErrlAdddataDao(Connection con) {
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
            sql.addSql("drop table FILE_ERRL_ADDDATA");

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
            sql.addSql(" create table FILE_ERRL_ADDDATA (");
            sql.addSql("   FEA_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   FFL_EXT varchar(50),");
            sql.addSql("   FFL_FILE_SIZE integer not null,");
            sql.addSql("   FCB_SID integer not null,");
            sql.addSql("   FDR_PARENT_SID integer not null,");
            sql.addSql("   FDR_NAME varchar(255) not null,");
            sql.addSql("   FDR_BIKO varchar(1000),");
            sql.addSql("   FFR_UP_CMT varchar(1000),");
            sql.addSql("   FDR_AUID integer not null,");
            sql.addSql("   FDR_ADATE timestamp not null,");
            sql.addSql("   FEA_DEFGRP_NAME varchar(50) not null,");
            sql.addSql("   primary key (FEA_SID)");
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
     * <p>Insert FILE_ERRL_ADDDATA Data Bindding JavaBean
     * @param bean FILE_ERRL_ADDDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileErrlAdddataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_ERRL_ADDDATA(");
            sql.addSql("   FEA_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FFR_UP_CMT,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FEA_DEFGRP_NAME");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFeaSid());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getFflExt());
            sql.addLongValue(bean.getFflFileSize());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addStrValue(bean.getFdrName());
            sql.addStrValue(bean.getFdrBiko());
            sql.addStrValue(bean.getFfrUpCmt());
            sql.addIntValue(bean.getFdrAuid());
            sql.addDateValue(bean.getFdrAdate());
            sql.addStrValue(bean.getFeaDefgrpName());
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
     * <br>[機  能] ファイルの仮登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList 仮登録ファイル情報リスト
     * @throws SQLException SQL実行例外
     */
    public void insert(List<FileErrlAdddataModel> beanList) throws SQLException {

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
            sql.addSql(" FILE_ERRL_ADDDATA(");
            sql.addSql("   FEA_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FFR_UP_CMT,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FEA_DEFGRP_NAME");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (FileErrlAdddataModel bean : beanList) {
                sql.addIntValue(bean.getFeaSid());
                sql.addLongValue(bean.getBinSid());
                sql.addStrValue(bean.getFflExt());
                sql.addLongValue(bean.getFflFileSize());
                sql.addIntValue(bean.getFcbSid());
                sql.addIntValue(bean.getFdrParentSid());
                sql.addStrValue(bean.getFdrName());
                sql.addStrValue(bean.getFdrBiko());
                sql.addStrValue(bean.getFfrUpCmt());
                sql.addIntValue(bean.getFdrAuid());
                sql.addDateValue(bean.getFdrAdate());
                sql.addStrValue(bean.getFeaDefgrpName());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update FILE_ERRL_ADDDATA Data Bindding JavaBean
     * @param bean FILE_ERRL_ADDDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileErrlAdddataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   FFL_EXT=?,");
            sql.addSql("   FFL_FILE_SIZE=?,");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FDR_PARENT_SID=?,");
            sql.addSql("   FDR_NAME=?,");
            sql.addSql("   FDR_BIKO=?,");
            sql.addSql("   FFR_UP_CMT=?,");
            sql.addSql("   FDR_AUID=?,");
            sql.addSql("   FDR_ADATE=?,");
            sql.addSql("   FEA_DEFGRP_NAME=?");
            sql.addSql(" where ");
            sql.addSql("   FEA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getFflExt());
            sql.addLongValue(bean.getFflFileSize());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addStrValue(bean.getFdrName());
            sql.addStrValue(bean.getFdrBiko());
            sql.addStrValue(bean.getFfrUpCmt());
            sql.addIntValue(bean.getFdrAuid());
            sql.addDateValue(bean.getFdrAdate());
            sql.addStrValue(bean.getFeaDefgrpName());
            //where
            sql.addIntValue(bean.getFeaSid());

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
     * <p>Select FILE_ERRL_ADDDATA All Data
     * @return List in FILE_ERRL_ADDDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FEA_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FFR_UP_CMT,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FEA_DEFGRP_NAME");
            sql.addSql(" from ");
            sql.addSql("   FILE_ERRL_ADDDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <p>Select FILE_ERRL_ADDDATA
     * @param feaSid FEA_SID
     * @return FILE_ERRL_ADDDATAModel
     * @throws SQLException SQL実行例外
     */
    public FileErrlAdddataModel select(int feaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileErrlAdddataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FEA_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FFR_UP_CMT,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where ");
            sql.addSql("   FEA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(feaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileErrlAdddataFromRs(rs);
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
     * <p>Select FILE_ERRL_ADDDATA
     * @param feaSidList FEA_SID List
     * @return FILE_ERRL_ADDDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> select(Collection<Integer> feaSidList) throws SQLException {

        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();
        if (feaSidList == null || feaSidList.isEmpty()) {
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
            sql.addSql("   FEA_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FFR_UP_CMT,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where ");

            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = feaSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("     FILE_ERRL_ADDDATA.FEA_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("     )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 指定されたファイルツリーを取得する。(単体登録表示用)
     * <br>[解  説] 権限チェックが含まれないため単独使用禁止
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @param feaSidList 仮登録ファイルSIDリスト
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> getErrlFileTreeList(
            int fcbSid, List<Integer> feaSidList) throws SQLException {

        List<String> ret = new ArrayList<String>();
        if (feaSidList == null || feaSidList.isEmpty()) {
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
            sql.addSql("   FEA_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FDR_PARENT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where");
            sql.addSql("   FCB_SID = ?");
            sql.addIntValue(fcbSid);
            sql.addSql(" and");
            sql.addSql("   FEA_SID in (");
            for (int idx = 0; idx < feaSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(",");
                }
                sql.addSql(" ?");
                sql.addIntValue(feaSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            while (rs.next()) {
                StringBuilder key = new StringBuilder("");
                key.append(String.valueOf("0"));
                key.append(sep);
                key.append(String.valueOf(rs.getInt("FDR_PARENT_SID")));
                key.append(sep);
                key.append(rs.getString("FDR_NAME"));
                key.append(sep);
                key.append(String.valueOf("0"));
                key.append(sep);
                key.append(GSConstFile.MODE_SINGLE);
                key.append(sep);
                key.append(String.valueOf(fcbSid));
                key.append(sep);
                key.append(String.valueOf(rs.getLong("FEA_SID")));
                key.append(sep);
                key.append(String.valueOf(rs.getLong("BIN_SID")));
                key.append(sep);
                key.append(GSConstFile.DIRECTORY_LEVEL_0);
                ret.add(key.toString());
                key = null;

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
     * <br>[機  能] 指定したキャビネット内の、仮登録ファイル件数を取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分け無効キャビネット専用
     * @param fdrSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public long getFileCountFolder(Collection<Integer> fdrSidList,
            BaseUserModel usModel) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        if (fdrSidList == null || fdrSidList.isEmpty()) {
            return ret;
        }
        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            //フィールド名宣言部
            sql.addSql(" select");
            sql.addSql("   count(FEA_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }

            //登録先フォルダ参照
            if (!isAdmin) {
                sql.addSql("  ,FILE_DIRECTORY");
            }

            //in句を500件毎に分割
            //数値限定のためプレースホルダーを使用しない
            sql.addSql(" where (");
            List<Integer> exeList = new ArrayList<Integer>();
            Iterator<Integer> itr = fdrSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("   )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql(" )");

            if (!isAdmin) {
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addSql(" and");
                sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }

            // キャビネット作成権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   (");

                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <br>[機  能] 仮登録ファイル件数を取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分けキャビネット用
     * @param fcbSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public long getFileCountAutoSortCabinet(List<Integer> fcbSidList,
            BaseUserModel usModel) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        if (fcbSidList == null || fcbSidList.size() == 0) {
            return 0;
        }
        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FEA_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            // キャビネット管理者 参照のための join句
            sql.addSql("     left join FILE_CABINET_ADMIN");
            sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
            sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
            sql.addIntValue(usModel.getUsrsid());
            //登録先参照
            sql.addSql("  ,FILE_CABINET");

            //in句を500件毎に分割
            //数値限定のためプレースホルダーを使用しない
            sql.addSql(" where (");
            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = fcbSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("   )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER = ?");
            sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            // キャビネット管理者権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {

                sql.addSql(" and ");
                sql.addSql("   (");
                // キャビネット管理者の場合、削除されていなければＯＫ
                sql.addSql("     FILE_CABINET_ADMIN.FCB_SID is not null ");

                sql.addSql("     or");
                // アクセス設定なし
                sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN = ?");
                sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
                sql.addSql("     or");
                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_ACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("    and A.FAA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <br>[機  能] 仮登録ファイルを取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分けキャビネット用
     * @param fcbSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileAutoSortCabinet(List<Integer> fcbSidList,
            BaseUserModel usModel) throws SQLException {
        return getFileAutoSortCabinet(fcbSidList, usModel, 1, 0);
    }
    /**
     * <br>[機  能] 仮登録ファイルを取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分けキャビネット用
     * @param fcbSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @param page ページ番号
     * @param limit 1ページあたりの取得数
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileAutoSortCabinet(List<Integer> fcbSidList,
            BaseUserModel usModel,
            int page, int limit) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();
        if (fcbSidList == null || fcbSidList.size() < 1) {
            return ret;
        }

        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_DEFGRP_NAME as FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            // キャビネット管理者 参照のための join句
            sql.addSql("     left join FILE_CABINET_ADMIN");
            sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
            sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
            sql.addIntValue(usModel.getUsrsid());
            //登録先参照
            sql.addSql("  ,FILE_CABINET");

            //in句を500件毎に分割
            //数値限定のためプレースホルダーを使用しない
            sql.addSql(" where (");
            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = fcbSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("   )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER = ?");
            sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            // プラグイン管理者権限ユーザ以下の場合、各アクセス権限を参照
            // プラグイン管理者は削除フォルダ内も取得する
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   (");
                // キャビネット管理者の場合、削除されていなければＯＫ
                sql.addSql("     FILE_CABINET_ADMIN.FCB_SID is not null ");

                sql.addSql("     or");
                // アクセス設定なし
                sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN = ?");
                sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
                sql.addSql("     or");
                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_ACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("    and A.FAA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }
            sql.addSql(" order by");
            sql.addSql("   FEA_SID");

            if (limit > 0) {
                int offset = PageUtil.getOffset(page, limit);
                sql.setPagingValue(offset, limit);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 仮登録ファイル件数を取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分け無効キャビネット専用
     * @param fcbSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public long getFileCountCabinet(List<Integer> fcbSidList,
            BaseUserModel usModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);
        if (fcbSidList == null || fcbSidList.size() == 0) {
            return 0;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FEA_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            //登録先フォルダ参照
            sql.addSql("   left join FILE_DIRECTORY");
            sql.addSql("     on");
            sql.addSql("       FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql("      and");
            sql.addSql("       FILE_DIRECTORY.FDR_KBN = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addSql("  ,FILE_CABINET");
            // キャビネット管理者 参照のための join句
            if (!isAdmin) {
                sql.addSql("   left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_CABINET.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }
            //in句を500件毎に分割
            //数値限定のためプレースホルダーを使用しない
            sql.addSql(" where (");
            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = fcbSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("   )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER = ?");
            sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            // キャビネット管理者権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);

                sql.addSql(" and ");
                sql.addSql("   (");
                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <br>[機  能] 仮登録ファイル件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public long getFileCountNoSavePath(int fcbSid, BaseUserModel usModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FEA_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
                sql.addSql("   ,FILE_DIRECTORY");
            }

            sql.addSql(" where ");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = ?");
            sql.addIntValue(fcbSid);
            sql.addSql(" and ");
            sql.addSql("   not exists (");
            sql.addSql("     select *");
            sql.addSql("     from ");
            sql.addSql("       FILE_DIRECTORY ");
            sql.addSql("     where ");
            sql.addSql("       FILE_DIRECTORY.FDR_SID = FILE_ERRL_ADDDATA.FDR_PARENT_SID ");
            sql.addSql("     and ");
            sql.addSql("       FILE_DIRECTORY.FDR_JTKBN=? ");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addSql("   )");

            // キャビネット作成権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                //プラグイン管理者以外は削除されたフォルダを制限
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql(" and ");
                sql.addSql("    FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
                sql.addSql(" and ");
                sql.addSql("  FILE_DIRECTORY.FDR_JTKBN=? ");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);

                sql.addSql(" and ");
                sql.addSql("   (");

                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");

            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <br>[機  能] 指定したキャビネット内の、保存先が存在しない仮登録ファイルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileNoSavePath(
            int fcbSid,
            BaseUserModel usModel) throws SQLException {
        return getFileNoSavePath(fcbSid, usModel, 0, 0);
    }
    /**
     * <br>[機  能] 指定したキャビネット内の、保存先が存在しない仮登録ファイルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usModel セッションユーザ
     * @param page ページ番号
     * @param limit 1ページあたりの取得数
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileNoSavePath(
            int fcbSid,
            BaseUserModel usModel,
            int page,
            int limit) throws SQLException {

        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_DEFGRP_NAME as FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
                sql.addSql("   ,FILE_DIRECTORY");
            }
            sql.addSql(" where ");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = ?");
            sql.addIntValue(fcbSid);

            sql.addSql(" and ");
            sql.addSql("   not exists (");
            sql.addSql("     select *");
            sql.addSql("     from ");
            sql.addSql("       FILE_DIRECTORY ");
            sql.addSql("     where ");
            sql.addSql("       FILE_DIRECTORY.FDR_SID = FILE_ERRL_ADDDATA.FDR_PARENT_SID ");
            sql.addSql("     and ");
            sql.addSql("       FILE_DIRECTORY.FDR_JTKBN=? ");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addSql("   )");

            // キャビネット作成権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                //プラグイン管理者以外は削除されたフォルダを制限
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql(" and ");
                sql.addSql("    FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
                sql.addSql(" and ");
                sql.addSql("  FILE_DIRECTORY.FDR_JTKBN=? ");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);

                sql.addSql(" and ");
                sql.addSql("   (");

                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }

            if (limit > 0) {
                int offset = PageUtil.getOffset(page, limit);
                sql.setPagingValue(offset, limit);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 指定したキャビネット内の、仮登録ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分け無効キャビネット専用
     * @param fcbSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileCabinet(
            List<Integer> fcbSidList,
            BaseUserModel usModel) throws SQLException {

        return getFileCabinet(fcbSidList, usModel, 1, 0);
    }

    /**
     * <br>[機  能] 指定したキャビネット内の、仮登録ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考] 自動振り分け無効キャビネット専用
     * @param fcbSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @param page ページ番号
     * @param limit 1ページあたりの取得数
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileCabinet(
            List<Integer> fcbSidList,
            BaseUserModel usModel,
            int page, int limit) throws SQLException {

        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();
        if (fcbSidList == null || fcbSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_DEFGRP_NAME as FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            //登録先フォルダ参照
            sql.addSql("   left join FILE_DIRECTORY");
            sql.addSql("     on");
            sql.addSql("       FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql("      and");
            sql.addSql("       FILE_DIRECTORY.FDR_KBN = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addSql("  ,FILE_CABINET");
            // キャビネット管理者 参照のための join句
            if (!isAdmin) {
                sql.addSql("   left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_CABINET.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }

            //in句を500件毎に分割
            //数値限定のためプレースホルダーを使用しない
            sql.addSql(" where (");
            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = fcbSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("   )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER = ?");
            sql.addIntValue(GSConstFile.SORT_FOLDER_NONE);
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            // キャビネット管理者権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);

                sql.addSql(" and ");
                sql.addSql("   (");
                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }


            sql.addSql(" order by");
            sql.addSql("   FEA_SID");

            if (limit > 0) {
                int offset = PageUtil.getOffset(page, limit);
                sql.setPagingValue(offset, limit);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 指定したフォルダ内の、仮登録ファイル情報を取得する
     * <br>[解  説] アクセス制限設定で登録できないフォルダ直下のファイルは除外される
     * <br>[備  考] 自動振り分け無効キャビネット専用
     * @param fdrSidList フォルダのディレクトリSID
     * @param usModel セッションユーザ
     * @param page ページ番号
     * @param limit 1ページあたりの取得数
     * @return 指定したフォルダ内の仮登録ファイル一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getFileFolder(
            Collection<Integer> fdrSidList,
            BaseUserModel usModel,  int page, int limit) throws SQLException {

        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();

        if (fdrSidList == null || fdrSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_DEFGRP_NAME as FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }

            //登録先フォルダ参照
            if (!isAdmin) {
                sql.addSql("  ,FILE_DIRECTORY");
            }

            //in句を500件毎に分割
            //数値限定のためプレースホルダーを使用しない
            sql.addSql(" where (");
            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = fdrSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("   )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql(" )");

            if (!isAdmin) {
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addSql(" and");
                sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }

            // キャビネット作成権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {

                sql.addSql(" and ");
                sql.addSql("   (");

                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");

            }

            sql.addSql(" order by");
            sql.addSql("   FEA_SID");

            if (limit > 0) {
                int offset = PageUtil.getOffset(page, limit);
                sql.setPagingValue(offset, limit);
            }
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 仮登録ファイルが存在する電帳法キャビネットSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usModel セッションユーザ
     * @return エラー
     */
    public List<Integer> getFcbSidList(BaseUserModel usModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                //保存先フォルダ 参照のためのjoin
                sql.addSql("   left join");
                sql.addSql("     FILE_DIRECTORY");
                sql.addSql("   on");
                sql.addSql("     (FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql("   and");
                sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_ERRL_ADDDATA.FDR_PARENT_SID)");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);


                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("     on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("     and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }
            sql.addSql("   ,FILE_CABINET");

            if (!isAdmin) {
                sql.addSql("   left join ");
                // キャビネット ディレクトリ追加変更権限 参照のための join句
                sql.addSql("   (select 1 as CAB_EDIT_KBN,");
                sql.addSql("    A.FCB_SID as FCB_SID");
                sql.addSql("    from");
                sql.addSql("      FILE_ACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("    A.FAA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addSql("       A.USR_SID = ?) or");
                sql.addIntValue(usModel.getUsrsid());
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addIntValue(usModel.getUsrsid());
                sql.addSql("         )))");
                sql.addSql("   ) CAB_ACCESS");
                sql.addSql("       on FILE_CABINET.FCB_SID = CAB_ACCESS.FCB_SID");
            }
            sql.addSql(" where ");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);


            // キャビネット作成権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   (");
                //自動振り分けキャビネットの場合
                sql.addSql("     (");
                sql.addSql("       FILE_CABINET.FCB_SORT_FOLDER = ?");
                sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                sql.addSql("       and ");
                sql.addSql("       (");
                // キャビネット管理者の場合、削除されていなければＯＫ
                sql.addSql("         FILE_CABINET_ADMIN.FCB_SID is not null ");

                sql.addSql("         or");
                // アクセス設定なし
                sql.addSql("         FILE_CABINET.FCB_ACCESS_KBN = ?");
                sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
                sql.addSql("         or");
                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("         CAB_ACCESS.CAB_EDIT_KBN = ?");
                sql.addIntValue(Integer.valueOf(GSConstFile.ACCESS_KBN_WRITE));
                sql.addSql("       )");

                sql.addSql("     )");
                sql.addSql("     or ");

                //自動振り分けのないキャビネットの場合
                sql.addSql("     (");
                sql.addSql("       FILE_CABINET.FCB_SORT_FOLDER = ?");
                sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                sql.addSql("       and ");
                sql.addSql("       FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
                sql.addSql("       and ");
                sql.addSql("       (");
                // キャビネット管理者の場合、削除されていなければＯＫ
                sql.addSql("         FILE_CABINET_ADMIN.FCB_SID is not null ");
                sql.addSql("         or");
                //アクセス制限無し
                sql.addSql("           FILE_DIRECTORY.FDR_ACCESS_SID = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
                //アクセス制限有の場合、編集権限を持つユーザかを判定
                sql.addSql("         or");
                sql.addSql("           exists (");
                sql.addSql("             select 1 from FILE_DACCESS_CONF A");
                sql.addSql("             where");
                sql.addSql("               A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("             and A.FDA_AUTH = 1 ");
                sql.addSql("             and (");
                sql.addSql("               (");
                sql.addSql("                 A.USR_KBN = ? and");
                sql.addSql("                 A.USR_SID = ?");
                sql.addSql("               ) or");
                sql.addSql("               (A.USR_KBN = ? and");
                sql.addSql("               exists");
                sql.addSql("               (select 1");
                sql.addSql("                from");
                sql.addSql("                  CMN_BELONGM B");
                sql.addSql("                where");
                sql.addSql("                  B.GRP_SID = A.USR_SID");
                sql.addSql("                and");
                sql.addSql("                  B.USR_SID = ?");
                sql.addSql("               )))");
                sql.addSql("           )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addSql("   )");

            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("FCB_SID"));
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
     * <br>[機  能] 仮登録ファイルSIDを指定し、それに対応する親ディレクトリSIDを取得します
     * <br>[解  説]
     * <br>[備  考] 存在するディレクトリのみ取得します
     * @param feaSidList
     * @return エラー
     */
    public Map<Integer, Integer> getParentExist(List<Integer> feaSidList) throws SQLException {

        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        if (feaSidList == null || feaSidList.isEmpty()) {
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
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA,");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID in (");
            for (int i = 0; i < feaSidList.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(" ?");
                sql.addIntValue(feaSidList.get(i));
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("FEA_SID"), rs.getInt("FDR_PARENT_SID"));
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
     * <p>Delete FILE_ERRL_ADDDATA
     * @param feaSid FEA_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int feaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where ");
            sql.addSql("   FEA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(feaSid);

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
     * <p>Delete FILE_ERRL_ADDDATA
     * @param fcbSidList 削除対象FCB_SIDリスト
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCabinet(List<Integer> fcbSidList) throws SQLException {

        int count = 0;
        if (fcbSidList == null || fcbSidList.isEmpty()) {
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
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID in (");
            for (int idx = 0; idx < fcbSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(",");
                }
                sql.addSql(" ?");
                sql.addIntValue(fcbSidList.get(idx));
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
     * <p>Delete FILE_ERRL_ADDDATA
     * @param feaSidList FEA_SID List
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(List<Integer> feaSidList) throws SQLException {

        int count = 0;
        if (feaSidList.size() < 1) {
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
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where ");
            sql.addSql("   FEA_SID in (");
            for (int idx = 0; idx < feaSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(",");
                }
                sql.addSql(" ?");
                sql.addIntValue(feaSidList.get(idx));
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
     * <br>[機  能] 指定した仮登録ファイルのうち、ユーザが編集可能な情報を取得する
     * <br>[解  説] アクセス制限設定で登録できないフォルダ直下のファイルは除外される
     * <br>[備  考] 自動振り分けなしキャビネット用
     * @param feaSidList 仮登録ファイルSID
     * @param usModel セッションユーザ情報
     * @return 仮登録ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getErrlAddDataListNoAutoSortCabinet(
            Collection<Integer> feaSidList, BaseUserModel usModel) throws SQLException {
        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();

        if (feaSidList == null || feaSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_DEFGRP_NAME as FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                //保存先フォルダ 参照のためのjoin
                sql.addSql("   left join");
                sql.addSql("     FILE_DIRECTORY");
                sql.addSql("   on");
                sql.addSql("     (FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql("   and");
                sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_ERRL_ADDDATA.FDR_PARENT_SID)");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);


                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("     on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("     and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }
            sql.addSql("   ,FILE_CABINET");

            sql.addSql(" where ");
            sql.addSql("   (");

            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = feaSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("     FILE_ERRL_ADDDATA.FEA_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("     )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER = ?");
            sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            // プラグイン権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);

                sql.addSql(" and ");
                sql.addSql("   (");
                // キャビネット管理者の場合、ＯＫ
                sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定なし
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ? or");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and A.FDA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }

            sql.addSql(" order by");
            sql.addSql("   FEA_SID");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 指定した仮登録ファイルのうち、ユーザが編集可能な情報を取得する
     * <br>[解  説] アクセス制限設定で登録できないフォルダ直下のファイルは除外される
     * <br>[備  考]
     * @param feaSidList 仮登録ファイルSID
     * @param usModel セッションユーザ情報
     * @return 仮登録ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getErrlAddDataListAutoSortCabinet(
            Collection<Integer> feaSidList, BaseUserModel usModel) throws SQLException {
        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();

        if (feaSidList == null || feaSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_SID as FEA_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_EXT as FFL_EXT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFL_FILE_SIZE as FFL_FILE_SIZE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_ERRL_ADDDATA.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_ERRL_ADDDATA.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_ERRL_ADDDATA.FEA_DEFGRP_NAME as FEA_DEFGRP_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            if (!isAdmin) {
                // キャビネット管理者 参照のための join句
                sql.addSql("     left join FILE_CABINET_ADMIN");
                sql.addSql("       on (FILE_CABINET_ADMIN.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?)");
                sql.addIntValue(usModel.getUsrsid());
            }
            sql.addSql("   ,FILE_CABINET");

            sql.addSql(" where ");
            sql.addSql("   (");

            List<Integer> exeList = new ArrayList<>();
            Iterator<Integer> itr = feaSidList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500
                        && itr.hasNext()) {
                    continue;
                }
                sql.addSql("     FILE_ERRL_ADDDATA.FEA_SID in (");
                sql.addSql(
                        exeList.stream()
                        .map(sid -> sid.toString())
                        .collect(Collectors.joining(","))
                        );
                sql.addSql("     )");
                if (itr.hasNext()) {
                    sql.addSql(" or");
                }
                exeList.clear();
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER = ?");
            sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            // キャビネット管理者権限ユーザ以下の場合、各アクセス権限を参照
            if (!isAdmin) {
                sql.addSql(" and ");
                sql.addSql("   (");
                // キャビネット管理者の場合、削除されていなければＯＫ
                sql.addSql("     FILE_CABINET_ADMIN.FCB_SID is not null ");

                sql.addSql("     or");
                // アクセス設定なし
                sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN = ?");
                sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
                sql.addSql("     or");
                // アクセス設定に該当ユーザがいるかどうか
                sql.addSql("   exists ");
                sql.addSql("   (select 1");
                sql.addSql("    from");
                sql.addSql("      FILE_ACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
                sql.addSql("    and A.FAA_AUTH = 1 ");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select 1");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(usModel.getUsrsid());
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(usModel.getUsrsid());

                sql.addSql("   )");
            }

            sql.addSql(" order by");
            sql.addSql("   FEA_SID");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileErrlAdddataFromRs(rs));
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
     * <br>[機  能] 指定した仮登録ファイルのうち、ユーザが編集可能な情報を取得する
     * <br>[解  説] アクセス制限設定で登録できないフォルダ直下のファイルは除外される
     * <br>[備  考]
     * @param feaSidList 仮登録ファイルSID
     * @param usModel セッションユーザ情報
     * @return 仮登録ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<FileErrlAdddataModel> getErrlAddDataList(
            Collection<Integer> feaSidList, BaseUserModel usModel) throws SQLException {

        List<FileErrlAdddataModel> ret = new ArrayList<FileErrlAdddataModel>();

        if (feaSidList == null || feaSidList.size() < 1) {
            return ret;
        }

        ret.addAll(
                getErrlAddDataListAutoSortCabinet(feaSidList, usModel)
                );
        Set<Integer> added = ret.stream()
                                .map(mdl -> mdl.getFeaSid())
                                .collect(Collectors.toSet());

        ret.addAll(
                getErrlAddDataListNoAutoSortCabinet(
                        feaSidList.stream()
                            .filter(sid -> added.contains(sid) == false)
                            .collect(Collectors.toSet()),
                        usModel)
                );
        ret = ret.stream()
                .sorted(Comparator.comparing(FileErrlAdddataModel::getFeaSid))
                .collect(Collectors.toList());

        return ret;
    }

    /**
     * <p>Create FILE_ERRL_ADDDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileErrlAdddataModel
     * @throws SQLException SQL実行例外
     */
    private FileErrlAdddataModel __getFileErrlAdddataFromRs(ResultSet rs) throws SQLException {
        FileErrlAdddataModel bean = new FileErrlAdddataModel();
        bean.setFeaSid(rs.getInt("FEA_SID"));
        bean.setBinSid(rs.getInt("BIN_SID"));
        bean.setFflExt(rs.getString("FFL_EXT"));
        bean.setFflFileSize(rs.getInt("FFL_FILE_SIZE"));
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
        bean.setFdrName(rs.getString("FDR_NAME"));
        bean.setFdrBiko(rs.getString("FDR_BIKO"));
        bean.setFfrUpCmt(rs.getString("FFR_UP_CMT"));
        bean.setFdrAuid(rs.getInt("FDR_AUID"));
        bean.setFdrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_ADATE")));
        bean.setFeaDefgrpName(rs.getString("FEA_DEFGRP_NAME"));
        return bean;
    }

}
