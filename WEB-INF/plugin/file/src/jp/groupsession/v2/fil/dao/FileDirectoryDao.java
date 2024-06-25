package jp.groupsession.v2.fil.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil040.FileDirectoryDspModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <p>FILE_DIRECTORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDirectoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileDirectoryDao.class);

    /**
     * <p>Default Constructor
     */
    public FileDirectoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileDirectoryDao(Connection con) {
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
            sql.addSql("drop table FILE_DIRECTORY");

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
            sql.addSql(" create table FILE_DIRECTORY (");
            sql.addSql("   FDR_SID NUMBER(10,0) not null,");
            sql.addSql("   FDR_VERSION NUMBER(10,0) not null,");
            sql.addSql("   FCB_SID NUMBER(10,0) not null,");
            sql.addSql("   FDR_PARENT_SID NUMBER(10,0) not null,");
            sql.addSql("   FDR_KBN NUMBER(10,0) not null,");
            sql.addSql("   FDR_VER_KBN NUMBER(10,0) not null,");
            sql.addSql("   FDR_LEVEL NUMBER(10,0) not null,");
            sql.addSql("   FDR_NAME varchar(255) not null,");
            sql.addSql("   FDR_BIKO varchar(3000),");
            sql.addSql("   FDR_JTKBN NUMBER(10,0) not null,");
            sql.addSql("   FDR_AUID NUMBER(10,0) not null,");
            sql.addSql("   FDR_ADATE varchar(23) not null,");
            sql.addSql("   FDR_EUID NUMBER(10,0) not null,");
            sql.addSql("   FDR_EDATE varchar(23) not null,");
            sql.addSql("   FDR_EGID NUMBER(10,0),");
            sql.addSql("   FDR_ACCESS_SID NUMBER(10,0),");
            sql.addSql("   FDR_TRADE_DATE timestamp,");
            sql.addSql("   FDR_TRADE_TARGET varchar(50),");
            sql.addSql("   FDR_TRADE_MONEYKBN integer,");
            sql.addSql("   FDR_TRADE_MONEY decimal(12, 4),");
            sql.addSql("   EMT_SID integer,");
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
     * <p>Insert FILE_DIRECTORY Data Bindding JavaBean
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_DIRECTORY(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
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
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrKbn());
            sql.addIntValue(bean.getFdrVerKbn());
            sql.addIntValue(bean.getFdrLevel());
            sql.addStrValue(bean.getFdrName());
            sql.addStrValue(bean.getFdrBiko());
            sql.addIntValue(bean.getFdrJtkbn());
            sql.addIntValue(bean.getFdrAuid());
            sql.addDateValue(bean.getFdrAdate());
            sql.addIntValue(bean.getFdrEuid());
            sql.addDateValue(bean.getFdrEdate());
            sql.addIntValue(bean.getFdrEgid());
            sql.addIntValue(bean.getFdrAccessSid());
            sql.addDateValue(bean.getFdrTradeDate());
            sql.addStrValue(bean.getFdrTradeTarget());
            sql.addIntValue(bean.getFdrTradeMoneykbn());
            sql.addDecimalValue(bean.getFdrTradeMoney());
            sql.addIntValue(bean.getEmtSid());
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
     * <p>Insert FILE_DIRECTORY Data Bindding JavaBean
     * @param beanList FILE_DIRECTORY DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<FileDirectoryModel> beanList) throws SQLException {

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
            sql.addSql(" FILE_DIRECTORY(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
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

            for (FileDirectoryModel bean : beanList) {
                sql.addIntValue(bean.getFdrSid());
                sql.addIntValue(bean.getFdrVersion());
                sql.addIntValue(bean.getFcbSid());
                sql.addIntValue(bean.getFdrParentSid());
                sql.addIntValue(bean.getFdrKbn());
                sql.addIntValue(bean.getFdrVerKbn());
                sql.addIntValue(bean.getFdrLevel());
                sql.addStrValue(bean.getFdrName());
                sql.addStrValue(bean.getFdrBiko());
                sql.addIntValue(bean.getFdrJtkbn());
                sql.addIntValue(bean.getFdrAuid());
                sql.addDateValue(bean.getFdrAdate());
                sql.addIntValue(bean.getFdrEuid());
                sql.addDateValue(bean.getFdrEdate());
                sql.addIntValue(bean.getFdrEgid());
                sql.addIntValue(bean.getFdrAccessSid());
                sql.addDateValue(bean.getFdrTradeDate());
                sql.addStrValue(bean.getFdrTradeTarget());
                sql.addIntValue(bean.getFdrTradeMoneykbn());
                sql.addDecimalValue(bean.getFdrTradeMoney());
                sql.addIntValue(bean.getEmtSid());
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
     * <p>Update FILE_DIRECTORY Data Bindding JavaBean
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FDR_PARENT_SID=?,");
            sql.addSql("   FDR_KBN=?,");
            sql.addSql("   FDR_VER_KBN=?,");
            sql.addSql("   FDR_LEVEL=?,");
            sql.addSql("   FDR_NAME=?,");
            sql.addSql("   FDR_BIKO=?,");
            sql.addSql("   FDR_JTKBN=?,");
            sql.addSql("   FDR_AUID=?,");
            sql.addSql("   FDR_ADATE=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EDATE=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_ACCESS_SID=?,");
            sql.addSql("   FDR_TRADE_DATE=?,");
            sql.addSql("   FDR_TRADE_TARGET=?,");
            sql.addSql("   FDR_TRADE_MONEYKBN=?,");
            sql.addSql("   FDR_TRADE_MONEY=?,");
            sql.addSql("   EMT_SID=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrKbn());
            sql.addIntValue(bean.getFdrVerKbn());
            sql.addIntValue(bean.getFdrLevel());
            sql.addStrValue(bean.getFdrName());
            sql.addStrValue(bean.getFdrBiko());
            sql.addIntValue(bean.getFdrJtkbn());
            sql.addIntValue(bean.getFdrAuid());
            sql.addDateValue(bean.getFdrAdate());
            sql.addIntValue(bean.getFdrEuid());
            sql.addDateValue(bean.getFdrEdate());
            sql.addIntValue(bean.getFdrEgid());
            sql.addIntValue(bean.getFdrAccessSid());
            sql.addDateValue(bean.getFdrTradeDate());
            sql.addStrValue(bean.getFdrTradeTarget());
            sql.addIntValue(bean.getFdrTradeMoneykbn());
            sql.addDecimalValue(bean.getFdrTradeMoney());
            sql.addIntValue(bean.getEmtSid());
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
     * <p>親ディレクトリSIDを更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateParentSid(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FDR_PARENT_SID=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrEuid());
            sql.addDateValue(bean.getFdrEdate());
            //where
            sql.addIntValue(bean.getFdrSid());

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
     * <p>キャビネットSIDを更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @param sidList ディレクトリSIDList
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCabinetSid(FileDirectoryModel bean,
            ArrayList<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (sidList.size() == 0) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrEuid());
            sql.addDateValue(bean.getFdrEdate());
            sql.addSql(" where ");
            sql.addSql("   FDR_SID in (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(idx));
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
     * <p>親ディレクトリSIDを更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateParentSidByGp(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FDR_PARENT_SID=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrEuid());
            sql.addIntValue(bean.getFdrEgid());
            sql.addDateValue(bean.getFdrEdate());
            //where
            sql.addIntValue(bean.getFdrSid());

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
     * <p>ディレクトリ階層と親ディレクトリSIDを更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateParLv(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FCB_SID=?,");
            sql.addSql("   FDR_LEVEL=?,");
            sql.addSql("   FDR_PARENT_SID=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrLevel());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrEuid());
            sql.addIntValue(bean.getFdrEgid());
            sql.addDateValue(bean.getFdrEdate());
            //where
            sql.addIntValue(bean.getFdrSid());

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
     * <p>ディレクトリ階層を更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateLevel(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FDR_LEVEL=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrLevel());
            sql.addIntValue(bean.getFdrEuid());
            sql.addIntValue(bean.getFdrEgid());
            sql.addDateValue(bean.getFdrEdate());
            //where
            sql.addIntValue(bean.getFdrSid());

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
     * <p>キャビネット用フォルダ(ROOT)を更新します。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @param noNameFlg true:名前の更新無し false:通常
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateRootFolder(FileDirectoryModel bean, boolean noNameFlg) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FDR_VER_KBN=?,");
            if (!noNameFlg) {
                sql.addSql("   FDR_NAME=?,");
            }
            sql.addSql("   FDR_BIKO=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_PARENT_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_KBN=?");
            sql.addSql(" and");
            sql.addSql("   FDR_JTKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(bean.getFdrVerKbn());
            if (!noNameFlg) {
                sql.addStrValue(bean.getFdrName());
            }
            sql.addStrValue(bean.getFdrBiko());
            sql.addIntValue(bean.getFdrEuid());
            sql.addIntValue(bean.getFdrEgid());
            sql.addDateValue(bean.getFdrEdate());
            //where
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrKbn());
            sql.addIntValue(bean.getFdrJtkbn());

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
     * <p>フォルダ情報を更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateFolder(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FDR_NAME=?,");
            sql.addSql("   FDR_BIKO=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_EDATE=?,");
            sql.addSql("   FDR_ACCESS_SID=?,");
            sql.addSql("   FDR_TRADE_DATE=?,");
            sql.addSql("   FDR_TRADE_TARGET=?,");
            sql.addSql("   FDR_TRADE_MONEYKBN=?,");
            sql.addSql("   FDR_TRADE_MONEY=?,");
            sql.addSql("   EMT_SID=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getFdrName());
            sql.addStrValue(bean.getFdrBiko());
            sql.addIntValue(bean.getFdrEuid());
            sql.addIntValue(bean.getFdrEgid());
            sql.addDateValue(bean.getFdrEdate());
            sql.addIntValue(bean.getFdrAccessSid());
            sql.addDateValue(bean.getFdrTradeDate());
            sql.addStrValue(bean.getFdrTradeTarget());
            sql.addIntValue(bean.getFdrTradeMoneykbn());
            sql.addDecimalValue(bean.getFdrTradeMoney());
            sql.addIntValue(bean.getEmtSid());
            //where
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);

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
     * <p>フォルダ情報を更新する。
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJtkbn(FileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FDR_JTKBN=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EGID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrJtkbn());
            sql.addIntValue(bean.getFdrEuid());
            sql.addIntValue(bean.getFdrEgid());
            sql.addDateValue(bean.getFdrEdate());
            //where
            sql.addIntValue(bean.getFdrSid());

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
     * <br>[機  能] ディレクトリの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSidList ディレクトリSIDリスト
     * @param userSid 更新ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int deleteDirectory(List<Integer> fdrSidList, int userSid) throws SQLException {

        int count = 0;
        if (fdrSidList == null || fdrSidList.size() == 0) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            UDate now = new UDate();

            //SQL文
            SqlBuffer sql = new SqlBuffer();

            //1. 既存のデータを論理削除
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FDR_JTKBN=?,");
            sql.addSql("   FDR_EUID=?,");
            sql.addSql("   FDR_EDATE=?");
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            sql.addSql(" where ");
            sql.addSql("   FDR_SID in (");
            for (int idx = 0; idx < fdrSidList.size(); idx++) {
                sql.addSql(" ?");
                if (idx != fdrSidList.size() - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(fdrSidList.get(idx));
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            count = pstmt.executeUpdate();
            pstmt.close();

            //2. 削除履歴を登録（ファイルのみ）
            sql = new SqlBuffer();
            sql.addSql(" insert into FILE_DIRECTORY (");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" )");
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION + 1,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      FDR_SID as MAX_FDR_SID,");
            sql.addSql("      max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY");
            sql.addSql("    where ");
            sql.addSql("      FDR_SID in (");
            for (int idx = 0; idx < fdrSidList.size(); idx++) {
                sql.addSql("        ?");
                if (idx != fdrSidList.size() - 1) {
                    sql.addSql("        ,");
                }
                sql.addIntValue(fdrSidList.get(idx));
            }
            sql.addSql("      )");
            sql.addSql("    group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.MAX_FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = " + GSConstFile.DIRECTORY_KBN_FILE);
            sql.addSql(" order by ");
            sql.addSql("   FDR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            count += pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     *
     * <br>[機  能] 直近のアクセスSIDを取得する
     * <br>[解  説]
     * <br>[備  考] 指定ディレクトリのアクセスSIDが適切であることを前提とする。
     * @param fdrSid 指定ディレクトリ
     * @return アクセスSID
     * @throws SQLException SQL実行時例外
     */
    private int __getParrentAccessSid(int fdrSid) throws SQLException {
        int dirSid = fdrSid;
        PreparedStatement pstmt = null;
        int ret = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("    select distinct ");
            sql.addSql("      FDR_SID, ");
            sql.addSql("      FDR_PARENT_SID, ");
            sql.addSql("      FDR_LEVEL, ");
            sql.addSql("      coalesce(( ");
            sql.addSql("        select distinct");
            sql.addSql("          A.FDR_SID");
            sql.addSql("        from");
            sql.addSql("          FILE_DACCESS_CONF A");
            sql.addSql("        where A.FDR_SID = D.FDR_SID ");
            sql.addSql("      ), 0) as FDR_ACCESS_SID ");
            sql.addSql("    from ");
            sql.addSql("      FILE_DIRECTORY D ");
            sql.addSql("    where FDR_SID = ? ");
            sql.addSql("      and FDR_LEVEL <= ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            for (int i = GSConstFile.MAX_LEVEL; i >= 0; i--) {
                if (dirSid == 0) {
                    break;
                }
                sql.addIntValue(dirSid);
                sql.addIntValue(i);
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    dirSid = rs.getInt("FDR_PARENT_SID");
                    ret = rs.getInt("FDR_ACCESS_SID");
                    i = rs.getInt("FDR_LEVEL");
                    if (ret != 0) {
                        break;
                    }
                } else {
                    return 0;
                }
                sql.clearValue();
                JDBCUtil.closeResultSet(rs);
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
     * <p>指定したディレクトリSID以下のサブディレクトリ・ファイルのアクセス設定SIDを更新する。
     * @param fdrSid ディレクトリSID
     * @param accessKbn アクセス権限 0:制限しない 1:制限する
     * @param auth 含まれるサブフォルダにも適用するか 0:しない 1:する
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    private int __updateAccessSid(int fdrSid, int accessKbn, int auth)
            throws SQLException {

        PreparedStatement pstmt = null;
        PreparedStatement pstmtUpd = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;
        try {

            /*再利用するセレクトステートメント*/
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("    select distinct ");
            sql.addSql("      FDR_SID, ");
            sql.addSql("      ( ");
            sql.addSql("        select");
            sql.addSql("          count(distinct A.FDR_SID)");
            sql.addSql("        from FILE_DACCESS_CONF A");
            sql.addSql("        where A.FDR_SID = D.FDR_SID ");
            sql.addSql("      ) as ACCESS ");
            sql.addSql("    from ");
            sql.addSql("      FILE_DIRECTORY D ");
            sql.addSql("    where FDR_PARENT_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());

            /*再利用するアップデートステートメント*/
            SqlBuffer sqlUpd = new SqlBuffer();
            sqlUpd.addSql("    update ");
            sqlUpd.addSql("      FILE_DIRECTORY ");
            sqlUpd.addSql("    set FDR_ACCESS_SID = ?");
            sqlUpd.addSql("    where FDR_SID = ? ");
            pstmtUpd = con.prepareStatement(sqlUpd.toSqlString());

            int dirSid = fdrSid;
            List<Integer[]> prevList = new ArrayList<Integer[]>();
            int accessSid = 0;
            Integer[] first = new Integer[2];

            if (dirSid != 0) {
                //初回指定ディレクトリの更新
                accessSid = __getParrentAccessSid(dirSid);
                sqlUpd.addIntValue(accessSid);
                sqlUpd.addIntValue(dirSid);
                log__.info(sqlUpd.toLogString());
                sqlUpd.setParameter(pstmtUpd);
                count += pstmtUpd.executeUpdate();
                sqlUpd.clearValue();
            }
            first[0] = dirSid;
            first[1] = accessSid;
            prevList.add(first);
            for (int i = 0; i <= GSConstFile.MAX_LEVEL; i++) {
                List<Integer[]> updList = new ArrayList<Integer[]>();
                //対象サブディレクトリを階層ごとに取得
                for (Integer[] prev : prevList) {
                    sql.addIntValue(prev[0]);
                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        Integer[] upd = new Integer[2];
                        upd[0] = rs.getInt("FDR_SID");
                        //個別アクセス設定がない場合は親のアクセスSIDを使用する
                        if (rs.getInt("ACCESS") > 0) {
                            upd[1] = upd[0];
                        } else {
                            upd[1] = prev[1];
                        }
                        updList.add(upd);
                    }
                    sql.clearValue();
                    JDBCUtil.closeResultSet(rs);
                }
                //対象サブディレクトリを更新
                for (Integer[] upd : updList) {
                    sqlUpd.addIntValue(upd[1]);
                    sqlUpd.addIntValue(upd[0]);
                    log__.info(sqlUpd.toLogString());
                    sqlUpd.setParameter(pstmtUpd);
                    count += pstmtUpd.executeUpdate();
                    sqlUpd.clearValue();
                }
                prevList = updList;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmtUpd);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>指定したディレクトリSID以下のサブディレクトリ・ファイルのアクセス設定SIDを更新する。
     * @param fdrSid ディレクトリSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAccessSid(int fdrSid) throws SQLException {

       return updateAccessSid(fdrSid, 0, 0);
    }

    /**
     * <p>指定したディレクトリSID以下のサブディレクトリ・ファイルのアクセス設定SIDを更新する。
     * @param fdrSid ディレクトリSID
     * @param accessKbn アクセス権限設定区分 0:制限しない 1:制限する
     * @param auth 含まれるサブフォルダにも適用するか 0:しない 1:する
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAccessSid(int fdrSid, int accessKbn, int auth)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

            if (h2db) {
                return __updateAccessSid(fdrSid, accessKbn, auth);
            } else {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   FILE_DIRECTORY");
                sql.addSql(" set ");
                sql.addSql("   FDR_ACCESS_SID = DIRECTORY.ACCESS_SID");
                sql.addSql(" from ");
                // sub-5 ↓ FILE_DACCESS_CONF が存在する直近のディレクトリSIDを取り出す ---------------*
                sql.addSql("   (select distinct");
                sql.addSql("      DIRECTORY.FDR_SID,");
                sql.addSql("      DIRECTORY.ACCESS_SID");
                sql.addSql("    from");
                // sub-4 ↓ FILE_DACCESS_CONF が存在する直近のディレクトリに順位を付加 ----------------*
                sql.addSql("      (select");
                sql.addSql("         DIRECTORY.FDR_SID,");
                sql.addSql("         coalesce(DACCESS.FDR_SID, ?) ACCESS_SID,");
                sql.addSql("         rank() over (partition by DIRECTORY.FDR_SID");
                sql.addSql("           order by case");
                sql.addSql("                    when DACCESS.FDR_SID is null then 99999");
                sql.addSql("                    else rn end) RK");
                sql.addSql("       from");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
                // sub-3 ↓ sub-1 で取り出した下層配列に行番号を付加して縦行に変換 --------------------*
                sql.addSql("         (select");
                sql.addSql("            DIRECTORY.FDR_SID,");
                sql.addSql("            DIRECTORY.PATH,");
                sql.addSql("            generate_subscripts(DIRECTORY.PATH, 1) rn");
                sql.addSql("          from");
                // sub-1 ↓ 上位～下位のディレクトリ情報を取得するための階層問い合わせ -----------------*
                sql.addSql("        (with recursive rec(FDR_SID, FDR_PARENT_SID, PATH) as (");
                sql.addSql("         select A.FDR_SID, A.FDR_PARENT_SID, array[A.FDR_SID]");
                sql.addSql("           from FILE_DIRECTORY A");
                sql.addSql("          where A.FDR_PARENT_SID = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
                sql.addSql("          union all");
                sql.addSql("         select B.FDR_SID, B.FDR_PARENT_SID, B.FDR_SID || A.PATH");
                sql.addSql("           from rec A");
                sql.addSql("           join FILE_DIRECTORY B on A.FDR_SID = B.FDR_PARENT_SID)");
                sql.addSql("         select distinct rec.FDR_SID,PATH");
                sql.addSql("           from rec");
                if (fdrSid != -1) {
                    sql.addSql("              where ? = any(PATH)");
                    sql.addIntValue(fdrSid);
                }
                // sub-1 ↑ -------------------------------------------------------------------*
                sql.addSql("            ) DIRECTORY");
                // sub-3 ↑ -------------------------------------------------------------------*
                sql.addSql("         ) DIRECTORY");
                sql.addSql("       left join");
                sql.addSql("         (select distinct FDR_SID from FILE_DACCESS_CONF) DACCESS");
                sql.addSql("         on DIRECTORY.PATH[rn] = DACCESS.FDR_SID");
                sql.addSql("      ) DIRECTORY");
                // sub-4 ↑ -------------------------------------------------------------------*
                sql.addSql("    where");
                sql.addSql("      DIRECTORY.RK = 1");
                sql.addSql("   ) DIRECTORY");
                // sub-5 ↑ -------------------------------------------------------------------*
                sql.addSql(" where");
                sql.addSql("   FILE_DIRECTORY.FDR_SID = DIRECTORY.FDR_SID");

                pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select FILE_DIRECTORY All Data
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>Select FILE_DIRECTORY All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" order by ");
            sql.addSql("   FDR_SID asc,");
            sql.addSql("   FDR_VERSION asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * @return List in FILE_DIRECTORYModel
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
            sql.addSql("   FILE_DIRECTORY");

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
     * <p>指定したディレクトリのバージョンの情報を取得する。
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION フォルダの場合バージョン管理しないため -1を指定する
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryModel select(int fdrSid, int fdrVersion) throws SQLException {
        return select(fdrSid, fdrVersion, true);
    }

    /**
     * <p>指定したディレクトリのバージョンの情報を取得する。
     * @param fdrSid FDR_SID
     * @param fdrVersion FDR_VERSION フォルダの場合バージョン管理しないため -1を指定する
     * @param getDelFlg 論理削除ファイル取得フラグ
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryModel select(int fdrSid, int fdrVersion, boolean getDelFlg)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileDirectoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addIntValue(fdrSid);
            if (fdrVersion != -1) {
                sql.addSql(" and");
                sql.addSql("   FDR_VERSION=?");
                sql.addIntValue(fdrVersion);
            }
            if (!getDelFlg) {
                sql.addSql(" and");
                sql.addSql("   FDR_JTKBN=?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }


            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileDirectoryFromRs(rs);
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
     * <p>指定したキャビネットSIDを持つディレクトリを取得する。
     * @param cabSid FCB_SID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getDirectory(int cabSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cabSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>指定したディレクトリSIDを親に持つディレクトリを取得する。
     * @param fdrSid FDR_SID
     * @param jtkbn 削除区分 0:通常 9:削除済み -1:すべて
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getChildDirectorySid(int fdrSid, int jtkbn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_PARENT_SID=?");
            sql.addIntValue(fdrSid);
            if (jtkbn >= GSConstFile.JTKBN_NORMAL) {
                sql.addSql(" and");
                sql.addSql("   FDR_JTKBN=?");
                sql.addIntValue(jtkbn);
            }
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
     * <p>指定したディレクトリSIDを親に持つディレクトリを取得する。
     * @param fdrSidList FDR_SID List
     * @param jtkbn 削除区分 0:通常 9:削除済み -1:すべて
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getChildDirectorySid(List<Integer> fdrSidList,
            int jtkbn) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
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
            sql.addSql("   FDR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_PARENT_SID in (");
            int i = 0;
            for (Integer fdrSid : fdrSidList) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(fdrSid);
                i++;
            }
            sql.addSql("   )");

            if (jtkbn >= GSConstFile.JTKBN_NORMAL) {
                sql.addSql(" and");
                sql.addSql("   FDR_JTKBN=?");
                sql.addIntValue(jtkbn);
            }
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
     * <p>指定したディレクトリSIDを親に持つディレクトリを取得する。
     * @param fdrSid FDR_SID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> getChildDirectory(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_PARENT_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_JTKBN=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>指定したディレクトリの最新バージョンの情報を取得する。
     * @param fdrSid FDR_SID
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryModel getNewDirectory(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileDirectoryModel ret = null;
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
            sql.addSql("   FILE_DIRECTORY.FDR_EGID as FDR_EGID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID as FDR_ACCESS_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_DATE as FDR_TRADE_DATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_TARGET as FDR_TRADE_TARGET,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEYKBN as FDR_TRADE_MONEYKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEY as FDR_TRADE_MONEY,");
            sql.addSql("   FILE_DIRECTORY.EMT_SID as EMT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FDR_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileDirectoryFromRs(rs);
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
     * <p>指定したディレクトリの最新バージョンの情報一覧を取得する。
     * @param fdrSids FDR_SIDリスト
     * @return FILE_DIRECTORYModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> getNewDirectoryList(String[] fdrSids) throws SQLException {
        return getNewDirectoryList(fdrSids, true);
    }

    /**
     * <p>指定したディレクトリの最新バージョンの情報一覧を取得する。
     * @param fdrSidList FDR_SIDリスト
     * @return FILE_DIRECTORYModelリスト
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, FileDirectoryModel> getNewDirectoryMap(List<String> fdrSidList)
            throws SQLException {
        if (fdrSidList == null || fdrSidList.size() == 0) {
            return new HashMap<>();
        }

        String[] fdrSids = new String[fdrSidList.size()];
        for (int i = 0; i < fdrSidList.size(); i++) {
            fdrSids[i] = fdrSidList.get(i);
        }

        List<FileDirectoryModel> dirList = getNewDirectoryList(fdrSids, true);
        HashMap<Integer, FileDirectoryModel> ret = new HashMap<Integer, FileDirectoryModel>();
        for (FileDirectoryModel dirMdl : dirList) {
            ret.put(dirMdl.getFdrSid(), dirMdl);
        }
        return ret;
    }

    /**
     * <p>指定したディレクトリの最新バージョンの情報一覧を取得する。
     * @param fdrSids FDR_SIDリスト
     * @param getDelKbn 論理削除取得フラグ
     * @return FILE_DIRECTORYModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> getNewDirectoryList(
            String[] fdrSids, boolean getDelKbn) throws SQLException {

        if (fdrSids == null || fdrSids.length < 1) {
            return null;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
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
            sql.addSql("   FILE_FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_EGID as FDR_EGID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID as FDR_ACCESS_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_DATE as FDR_TRADE_DATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_TARGET as FDR_TRADE_TARGET,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEYKBN as FDR_TRADE_MONEYKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEY as FDR_TRADE_MONEY,");
            sql.addSql("   FILE_DIRECTORY.EMT_SID as EMT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY left join FILE_FILE_BIN");
            sql.addSql("   on FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql("   and FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION,");

            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            if (fdrSids != null || fdrSids.length > 0) {
                sql.addSql("    where FDR_SID in (");
                boolean exists = false;
                for (String fdrSid : fdrSids) {
                    if (exists) {
                        sql.addSql("      ,?");
                    } else {
                        sql.addSql("      ?");
                        exists = true;
                    }
                    sql.addIntValue(Integer.parseInt(fdrSid));
                }
                sql.addSql("    )");
            }
            sql.addSql("    group by FDR_SID) DIR_MAXVERSION");

            sql.addSql(" where");
            sql.addSql(" (");

            int i = 0;
            for (String fdrSid : fdrSids) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql("   FILE_DIRECTORY.FDR_SID = ?");
                sql.addIntValue(Integer.parseInt(fdrSid));
                i++;
            }
            sql.addSql(" )");

            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            if (!getDelKbn) {
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }
            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            FileDirectoryModel model = null;
            while (rs.next()) {
                model = __getFileDirectoryFromRs(rs);
                model.setBinSid(rs.getLong("BIN_SID"));
                ret.add(model);
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
     * <p>親ディレクトリを指定してディレクトリの最新バージョンの情報の一覧を取得する。
     * @param fdrSid FDR_SID
     * @param usrSid ユーザSID
     * @param sort ソートキー
     * @param order オーダーキー
     * @param reqMdl RequestModel
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryDspModel> getNewLowDirectory(
            int fdrSid, int usrSid, int sort, int order, RequestModel reqMdl)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryDspModel> ret = new ArrayList<FileDirectoryDspModel>();
        con = getCon();

        try {
            String sortStr = "";
            if (order == GSConst.ORDER_KEY_ASC) {
                sortStr = "asc";
            } else if (order == GSConst.ORDER_KEY_DESC) {
                sortStr = "desc";
            }
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   DIR.FDR_SID as FDR_SID,");
            sql.addSql("   DIR.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   DIR.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   DIR.FCB_SID as FCB_SID,");
            sql.addSql("   DIR.FDR_KBN as FDR_KBN,");
            sql.addSql("   DIR.FDR_VER_KBN as FDR_VER_KBN,");
            sql.addSql("   DIR.FDR_LEVEL as FDR_LEVEL,");
            sql.addSql("   DIR.FDR_NAME as FDR_NAME,");
            sql.addSql("   DIR.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   DIR.FDR_JTKBN as FDR_JTKBN,");
            sql.addSql("   DIR.FDR_AUID as FDR_AUID,");
            sql.addSql("   DIR.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   DIR.FDR_EUID as FDR_EUID,");
            sql.addSql("   DIR.FDR_EDATE as FDR_EDATE,");
            sql.addSql("   DIR.FDR_EGID as FDR_EGID,");
            sql.addSql("   DIR.FDR_TRADE_DATE as FDR_TRADE_DATE,");
            sql.addSql("   DIR.FDR_TRADE_TARGET as FDR_TRADE_TARGET,");
            sql.addSql("   DIR.FDR_TRADE_MONEY as FDR_TRADE_MONEY,");
            sql.addSql("   DIR.FDR_TRADE_MONEYKBN as FDR_TRADE_MONEYKBN,");
            sql.addSql("   FILE_MONEY_MASTER.FMM_NAME,");
            sql.addSql("   USRM.USI_SEI as USI_SEI,");
            sql.addSql("   USRM.USI_MEI as USI_MEI,");
            sql.addSql("   USR.USR_JKBN as USR_JKBN,");
            sql.addSql("   USR.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   COALESCE(BIN.BIN_SID, -1) as BIN_SID,");
            sql.addSql("   COALESCE(BIN.FFL_EXT, '') as FFL_EXT,");
            sql.addSql("   COALESCE(BIN.FFL_FILE_SIZE, -1) as FFL_FILE_SIZE,");
            sql.addSql("   COALESCE(BIN.FFL_LOCK_KBN, '0') as FFL_LOCK_KBN,");
            sql.addSql("   COALESCE(BIN.FFL_LOCK_USER, -1) as FFL_LOCK_USER,");
            sql.addSql("   BIN.FFL_LOCK_DATE as FFL_LOCK_DATE,");
            sql.addSql("  (case   when   EXISTS(");
            sql.addSql("    select USR_SID from FILE_CALL_CONF");
            sql.addSql("   where FILE_CALL_CONF.USR_SID= ?");
            sql.addSql("   and FILE_CALL_CONF.FDR_SID = DIR.FDR_SID) then 1");
            sql.addSql("   else 0 end) as CALLKBN,");
            sql.addSql("  (case");
            sql.addSql("    when COALESCE(BIN.FFL_LOCK_KBN, '0') = 0 then ''");
            sql.addSql("    else (select ");
            sql.addSql("           LUSRM.USI_SEI || ' ' || LUSRM.USI_MEI as LOCK_UNAME");
            sql.addSql("         from");
            sql.addSql("           CMN_USRM_INF LUSRM");
            sql.addSql("         where");
            sql.addSql("            LUSRM.USR_SID = BIN.FFL_LOCK_USER");
            sql.addSql("         )");
            sql.addSql("  end) as LOCK_UNAME,");
            sql.addSql("  1 as ACKBN");

            sql.addSql(" from");
            sql.addSql("   CMN_USRM USR,");
            sql.addSql("   CMN_USRM_INF USRM,");
            sql.addSql("   FILE_DIRECTORY DIR left join FILE_FILE_BIN BIN");
            sql.addSql("   on DIR.FDR_SID = BIN.FDR_SID");
            sql.addSql("   and DIR.FDR_VERSION = BIN.FDR_VERSION");
            sql.addSql("     left join CMN_GROUPM");
            sql.addSql("       on DIR.FDR_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("     left join FILE_MONEY_MASTER");
            sql.addSql("       on DIR.EMT_SID = FILE_MONEY_MASTER.FMM_SID,");

            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY");
            sql.addSql("    where FDR_PARENT_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   DIR.FDR_PARENT_SID = ?");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_EUID = USR.USR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_EUID = USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_JTKBN = 0");

            sql.addSql(" order by");
            sql.addSql("   DIR.FDR_KBN , ");
            switch (sort) {
            case GSConstFile.SORT_NAME:
                sql.addSql("   FDR_NAME " + sortStr);
                break;
            case GSConstFile.SORT_SIZE:
                sql.addSql("   COALESCE(BIN.FFL_FILE_SIZE, -1) " + sortStr);
                break;
            case GSConstFile.SORT_CALL:
                sql.addSql("   CALLKBN " + sortStr);
                break;
            case GSConstFile.SORT_EDATE:
                sql.addSql("   FDR_EDATE " + sortStr);
                break;
            case GSConstFile.SORT_EUSR:
                sql.addSql("   USRM.USI_SEI || '  ' || USRM.USI_MEI " + sortStr);
                break;
            case GSConstFile.SORT_TRADE_DATE:
                sql.addSql("   FDR_TRADE_DATE " + sortStr);
                break;
            case GSConstFile.SORT_TRADE_TARGET:
                sql.addSql("   FDR_TRADE_TARGET " + sortStr);
                break;
            case GSConstFile.SORT_TRADE_MONEY:
                sql.addSql("   FDR_TRADE_MONEY " + sortStr);
                break;
            default:
                break;
            }
            sql.addIntValue(usrSid);
            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryDspFromRs(rs, reqMdl));
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
     * <p>親ディレクトリを指定してディレクトリの最新バージョンの情報の一覧を取得する。
     * @param fdrSid FDR_SID
     * @param usrSid ユーザSID
     * @param sort ソートキー
     * @param order オーダーキー
     * @param reqMdl RequestModel
     * @return FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryDspModel> getNewLowDirectoryAccessLimit(
            int fdrSid, int usrSid, int sort, int order, RequestModel reqMdl)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryDspModel> ret = new ArrayList<FileDirectoryDspModel>();
        con = getCon();

        try {
            String sortStr = "";
            if (order == GSConst.ORDER_KEY_ASC) {
                sortStr = "asc";
            } else if (order == GSConst.ORDER_KEY_DESC) {
                sortStr = "desc";
            }
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   DIR.FDR_SID as FDR_SID,");
            sql.addSql("   DIR.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   DIR.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   DIR.FCB_SID as FCB_SID,");
            sql.addSql("   DIR.FDR_KBN as FDR_KBN,");
            sql.addSql("   DIR.FDR_VER_KBN as FDR_VER_KBN,");
            sql.addSql("   DIR.FDR_LEVEL as FDR_LEVEL,");
            sql.addSql("   DIR.FDR_NAME as FDR_NAME,");
            sql.addSql("   DIR.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   DIR.FDR_JTKBN as FDR_JTKBN,");
            sql.addSql("   DIR.FDR_AUID as FDR_AUID,");
            sql.addSql("   DIR.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   DIR.FDR_EUID as FDR_EUID,");
            sql.addSql("   DIR.FDR_EDATE as FDR_EDATE,");
            sql.addSql("   DIR.FDR_EGID as FDR_EGID,");
            sql.addSql("   DIR.FDR_TRADE_DATE as FDR_TRADE_DATE,");
            sql.addSql("   DIR.FDR_TRADE_TARGET as FDR_TRADE_TARGET,");
            sql.addSql("   DIR.FDR_TRADE_MONEY as FDR_TRADE_MONEY,");
            sql.addSql("   DIR.FDR_TRADE_MONEYKBN as FDR_TRADE_MONEYKBN,");
            sql.addSql("   FILE_MONEY_MASTER.FMM_NAME,");
            sql.addSql("   USRM.USI_SEI as USI_SEI,");
            sql.addSql("   USRM.USI_MEI as USI_MEI,");
            sql.addSql("   USR.USR_JKBN as USR_JKBN,");
            sql.addSql("   USR.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   COALESCE(BIN.BIN_SID, -1) as BIN_SID,");
            sql.addSql("   COALESCE(BIN.FFL_EXT, '') as FFL_EXT,");
            sql.addSql("   COALESCE(BIN.FFL_FILE_SIZE, -1) as FFL_FILE_SIZE,");
            sql.addSql("   COALESCE(BIN.FFL_LOCK_KBN, '0') as FFL_LOCK_KBN,");
            sql.addSql("   COALESCE(BIN.FFL_LOCK_USER, -1) as FFL_LOCK_USER,");
            sql.addSql("   BIN.FFL_LOCK_DATE as FFL_LOCK_DATE,");
            sql.addSql("  (case   when   EXISTS(");
            sql.addSql("    select USR_SID from FILE_CALL_CONF");
            sql.addSql("   where FILE_CALL_CONF.USR_SID= ?");
            sql.addSql("   and FILE_CALL_CONF.FDR_SID = DIR.FDR_SID) then 1");
            sql.addSql("   else 0 end) as CALLKBN,");
            sql.addSql("  (case");
            sql.addSql("    when COALESCE(BIN.FFL_LOCK_KBN, '0') = 0 then ''");
            sql.addSql("    else (select ");
            sql.addSql("           LUSRM.USI_SEI || ' ' || LUSRM.USI_MEI as LOCK_UNAME");
            sql.addSql("         from");
            sql.addSql("           CMN_USRM_INF LUSRM");
            sql.addSql("         where");
            sql.addSql("            LUSRM.USR_SID = BIN.FFL_LOCK_USER");
            sql.addSql("         )");
            sql.addSql("  end) as LOCK_UNAME,");
            sql.addSql("   COALESCE(DACCESS.FDA_AUTH, ?) as ACKBN");
            sql.addIntValue(usrSid);
            sql.addIntValue(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));

            sql.addSql(" from");
            sql.addSql("   CMN_USRM USR,");
            sql.addSql("   CMN_USRM_INF USRM,");
            sql.addSql("   FILE_DIRECTORY DIR left join FILE_FILE_BIN BIN");
            sql.addSql("   on DIR.FDR_SID = BIN.FDR_SID");
            sql.addSql("   and DIR.FDR_VERSION = BIN.FDR_VERSION");
            sql.addSql("     left join CMN_GROUPM");
            sql.addSql("       on DIR.FDR_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("     left join FILE_MONEY_MASTER");
            sql.addSql("       on DIR.EMT_SID = FILE_MONEY_MASTER.FMM_SID,");

            //ディレクトリのアクセス設定で参照ファイルを制限する
            sql.addSql("   ( ");
            sql.addSql("   select");
            sql.addSql("     ? as FDR_SID,");
            sql.addSql("     ? as FDA_AUTH");
            sql.addSql("   union all");
            sql.addSql("   select");
            sql.addSql("     A.FDR_SID,");
            sql.addSql("     max(A.FDA_AUTH) as FDA_AUTH");
            sql.addSql("   from");
            sql.addSql("     FILE_DACCESS_CONF A");
            sql.addSql("   where");
            sql.addSql("     exists (");
            sql.addSql("       select *");
            sql.addSql("       from");
            sql.addSql("         FILE_DIRECTORY D");
            sql.addSql("       where");
            sql.addSql("         D.FDR_ACCESS_SID = A.FDR_SID");
            sql.addSql("       and");
            sql.addSql("         D.FDR_PARENT_SID = ?");
            sql.addSql("       and");
            sql.addSql("         D.FDR_JTKBN = 0)");
            sql.addSql("   and (");
            sql.addSql("     (A.USR_KBN = ? and");
            sql.addSql("      A.USR_SID = ?) or");
            sql.addSql("     (A.USR_KBN = ? and");
            sql.addSql("      exists");
            sql.addSql("      (select *");
            sql.addSql("         from CMN_BELONGM B");
            sql.addSql("        where B.GRP_SID = A.USR_SID");
            sql.addSql("          and B.USR_SID = ?");
            sql.addSql("      )))");
            sql.addSql("   group by");
            sql.addSql("     A.FDR_SID");
            sql.addSql("   ) DACCESS,");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
            sql.addIntValue(fdrSid);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(usrSid);

            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY");
            sql.addSql("    where FDR_PARENT_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addIntValue(fdrSid);

            sql.addSql(" where");
            sql.addSql("   DIR.FDR_PARENT_SID = ?");
            sql.addIntValue(fdrSid);
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_EUID = USR.USR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_EUID = USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_JTKBN = 0");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_ACCESS_SID = DACCESS.FDR_SID");

            sql.addSql(" order by");
            sql.addSql("   DIR.FDR_KBN ");
            switch (sort) {
            case GSConstFile.SORT_NAME:
                sql.addSql("  , FDR_NAME " + sortStr);
                break;
            case GSConstFile.SORT_SIZE:
                sql.addSql("  , COALESCE(BIN.FFL_FILE_SIZE, -1) " + sortStr);
                break;
            case GSConstFile.SORT_CALL:
                sql.addSql("  , CALLKBN " + sortStr);
                break;
            case GSConstFile.SORT_EDATE:
                sql.addSql("  , FDR_EDATE " + sortStr);
                break;
            case GSConstFile.SORT_TRADE_DATE:
                sql.addSql("  , FDR_TRADE_DATE " + sortStr);
                break;
            case GSConstFile.SORT_TRADE_TARGET:
                sql.addSql("  , FDR_TRADE_TARGET " + sortStr);
                break;
            case GSConstFile.SORT_TRADE_MONEY:
                sql.addSql("  , FDR_TRADE_MONEY " + sortStr);
                break;
            case GSConstFile.SORT_EUSR:
                sql.addSql("  , USRM.USI_SEI || '  ' || USRM.USI_MEI " + sortStr);
                break;
            default:
                break;
            }
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryDspFromRs(rs, reqMdl));
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
     * <p>親ディレクトリを指定してディレクトリ情報を取得する。
     * @param fdrParentSid 親ディレクトリSID
     * @param fdrKbn ディレクトリ区分 -1：指定しない
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> getLowDirectory(int fdrParentSid, int fdrKbn)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_PARENT_SID = ?");
            sql.addIntValue(fdrParentSid);
            if (fdrKbn != -1) {
                sql.addSql(" and ");
                sql.addSql("   FDR_KBN = ?");
                sql.addIntValue(fdrKbn);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <br>[機  能] 指定された親を持つディレクトリを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param listOfParent 親ディレクトリリスト
     * @param listOfDir ディレクトリ(フォルダ)リスト
     * @param listOfFile ディレクトリ(ファイル)リスト
     * @return ret 取得したディレクトリモデルリスト
     * @throws SQLException 例外
     */
    public ArrayList<FileDirectoryModel> setChildDirList(
            ArrayList<FileDirectoryModel> listOfParent,
            ArrayList<FileDirectoryModel> listOfDir,
            ArrayList<FileDirectoryModel> listOfFile) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        FileDirectoryModel mdl = null;

        //ディレクトリ、ファイルが多数存在する場合を考慮し、1,000件ずつ取得する
        for (int count = 0; count < listOfParent.size(); count += 1000) {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   FDR_SID,");
                sql.addSql("   FDR_VERSION,");
                sql.addSql("   FCB_SID,");
                sql.addSql("   FDR_PARENT_SID,");
                sql.addSql("   FDR_KBN,");
                sql.addSql("   FDR_VER_KBN,");
                sql.addSql("   FDR_LEVEL,");
                sql.addSql("   FDR_NAME,");
                sql.addSql("   FDR_BIKO,");
                sql.addSql("   FDR_JTKBN,");
                sql.addSql("   FDR_AUID,");
                sql.addSql("   FDR_ADATE,");
                sql.addSql("   FDR_EUID,");
                sql.addSql("   FDR_EDATE,");
                sql.addSql("   FDR_EGID,");
                sql.addSql("   FDR_ACCESS_SID,");
                sql.addSql("   FDR_TRADE_DATE,");
                sql.addSql("   FDR_TRADE_TARGET,");
                sql.addSql("   FDR_TRADE_MONEYKBN,");
                sql.addSql("   FDR_TRADE_MONEY,");
                sql.addSql("   EMT_SID");
                sql.addSql(" from");
                sql.addSql("   FILE_DIRECTORY");
                sql.addSql(" where ");
                sql.addSql("   FDR_PARENT_SID in (");

                for (int index = count;
                    index < count + 1000 && index < listOfParent.size();
                    index++) {

                    FileDirectoryModel bean = (FileDirectoryModel) listOfParent.get(index);
                    if (index == count) {
                        sql.addSql("     ?");
                    } else {
                        sql.addSql("     ,?");
                    }
                    sql.addIntValue(bean.getFdrSid());
                }
                sql.addSql("   )");

                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    mdl = __getFileDirectoryFromRs(rs);
                    if (mdl.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
                        listOfDir.add(mdl);
                    } else if (mdl.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
                        listOfFile.add(mdl);
                    }

                    ret.add(mdl);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] キャビネットSIDを指定し、キャビネットのディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @return ret キャビネットのディレクトリSID
     * @throws SQLException 例外
     */
    public int getCabinetDirSid(int fcbSid) throws SQLException {

        int ret = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FDR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(GSConstFile.DIRECTORY_LEVEL_0);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("FDR_SID");
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
     * <br>[機  能] ツリーのキーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @param lv 階層LV
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public String[] getTreeList(int fcbSid, int lv)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_SID, -1) as FDR_SID,");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_PARENT_SID, -1) as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FCB_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(lv);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            ArrayList<String> retArray = new ArrayList<String>();
            while (rs.next()) {
                StringBuilder key = new StringBuilder("");
                key.append(String.valueOf(rs.getInt("FDR_SID")));
                key.append(sep);
                key.append(String.valueOf(rs.getInt("FDR_PARENT_SID")));
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FDR_NAME")));
                retArray.add(key.toString());
                key = null;
            }
            if (retArray.isEmpty()) {
                ret = new String[0];
            } else {
                ret = retArray.toArray(new String[retArray.size()]);
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
     * <br>[機  能] ツリーのキーを取得する
     * <br>[解  説] ディレクトリアクセス設定で制限されているディレクトリのみ取得する
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @param lv 階層LV
     * @param authUsrSid アクセス許可ユーザSID
     * @param auth 権限区分
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public String[] getTreeListAccessLimit(int fcbSid, int lv, int authUsrSid, int auth)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_SID, -1) as FDR_SID,");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_PARENT_SID, -1) as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FCB_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addIntValue(fcbSid);

            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(lv);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
          //ディレクトリのアクセス設定で参照ファイルを制限する
            sql.addSql(" and");
            sql.addSql("   (FILE_DIRECTORY.FDR_ACCESS_SID = ?");
            sql.addSql(" or");
            sql.addSql("   exists ( ");
            sql.addSql("     select");
            sql.addSql("       *");
            sql.addSql("     from");
            sql.addSql("       FILE_DACCESS_CONF A");
            sql.addSql("     where");
            sql.addSql("       FILE_DIRECTORY.FDR_ACCESS_SID = A.FDR_SID");
            sql.addSql("     and (");
            sql.addSql("       (A.USR_KBN = ? and");
            sql.addSql("        A.USR_SID = ?) or");
            sql.addSql("       (A.USR_KBN = ? and");
            sql.addSql("        exists");
            sql.addSql("          (select *");
            sql.addSql("           from");
            sql.addSql("             CMN_BELONGM B");
            sql.addSql("           where");
            sql.addSql("             B.GRP_SID = A.USR_SID");
            sql.addSql("           and");
            sql.addSql("             B.USR_SID = ?");
            sql.addSql("          )))");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(authUsrSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(authUsrSid);
            if (auth != -1) {
                sql.addSql("     and");
                sql.addSql("        A.FDA_AUTH = ?");
                sql.addIntValue(auth);
            }
            sql.addSql("   ))");

            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            ArrayList<String> retArray = new ArrayList<String>();
            while (rs.next()) {
                StringBuilder key = new StringBuilder("");
                key.append(String.valueOf(rs.getInt("FDR_SID")));
                key.append(sep);
                key.append(String.valueOf(rs.getInt("FDR_PARENT_SID")));
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FDR_NAME")));
                retArray.add(key.toString());
                key = null;
            }
            if (retArray.isEmpty()) {
                ret = new String[0];
            } else {
                ret = retArray.toArray(new String[retArray.size()]);
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
     * <br>[機  能] 仮登録ファイルが存在する電帳法対応キャビネットのフォルダツリーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSidList キャビネットSID
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> getErrlTreeList(List<Integer> fcbSidList) throws SQLException {

        List<String> ret = new ArrayList<String>();
        if (fcbSidList == null || fcbSidList.isEmpty()) {
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
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_SID, -1) as FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER as FCB_SORT_FOLDER,");
            sql.addSql("   count(FILE_ERRL_ADDDATA.FEA_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   FILE_ERRL_ADDDATA,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FCB_SID in (");
            for (int idx = 0; idx < fcbSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(",");
                }
                sql.addSql(" ?");
                sql.addIntValue(fcbSidList.get(idx));
            }
            sql.addSql("      )");
            sql.addSql("    group by FDR_SID) DIR_MAXVERSION,");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID in (");
            for (int idx = 0; idx < fcbSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(",");
                }
                sql.addSql(" ?");
                sql.addIntValue(fcbSidList.get(idx));
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_ERRL_ADDDATA.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" group by");
            sql.addSql("   FILE_DIRECTORY.FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER");
            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(GSConstFile.DIRECTORY_LEVEL_0);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            while (rs.next()) {
                int parentSid = -1;
                int sortFolder = rs.getInt("FCB_SORT_FOLDER");
                if (sortFolder == GSConstFile.SORT_FOLDER_USE) {
                    parentSid = -1;
                } else if (sortFolder == GSConstFile.SORT_FOLDER_NOT_USE) {
                    parentSid = -2;
                }

                StringBuilder key = new StringBuilder("");
                key.append(String.valueOf(rs.getInt("FDR_SID")));
                key.append(sep);
                key.append(String.valueOf(parentSid));
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FDR_NAME")));
                key.append(sep);
                key.append(String.valueOf(rs.getInt("CNT")));
                key.append(sep);
                key.append(GSConstFile.MODE_MULTI);
                key.append(sep);
                key.append(String.valueOf(rs.getInt("FCB_SID")));
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
     * <br>[機  能] 保存先が存在しないファイルのツリーを取得する。(単体登録表示用)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> getErrlFileTreeListNoParent(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FEA_SID,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_ERRL_ADDDATA");
            sql.addSql(" where");
            sql.addSql("   FILE_ERRL_ADDDATA.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   not exists (");
            sql.addSql("     select 1");
            sql.addSql("     from");
            sql.addSql("       FILE_DIRECTORY");
            sql.addSql("     where");
            sql.addSql("       FILE_DIRECTORY.FDR_SID = FILE_ERRL_ADDDATA.FDR_PARENT_SID");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            while (rs.next()) {
                StringBuilder key = new StringBuilder("");
                key.append(String.valueOf(0));
                key.append(sep);
                key.append(String.valueOf("-1"));
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
     * <br>[機  能] 指定されたキャビネット内のファイルツリーを取得する。(単体登録表示用)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> getErrlFileTreeList(int fcbSid) throws SQLException {

        List<String> ret = new ArrayList<String>();
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
                key.append(String.valueOf("0"));
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
     * <br>[機  能] ツリーのキーを取得する
     * <br>[解  説] ディレクトリ区分がフォルダの場合も取得する
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @param lv 階層LV
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public String[] getCotainFileTreeList(int fcbSid, int lv)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_SID, -1) as FDR_SID,");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_PARENT_SID, -1) as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN as FDR_KBN");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FCB_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(fcbSid);
            sql.addIntValue(lv);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            ArrayList<String> retArray = new ArrayList<String>();
            while (rs.next()) {
                StringBuilder key = new StringBuilder("");
                key.append(String.valueOf(rs.getInt("FDR_SID")));
                key.append(sep);
                key.append(String.valueOf(rs.getInt("FDR_PARENT_SID")));
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FDR_NAME")));
                key.append(sep);
                key.append(String.valueOf(rs.getInt("FDR_KBN")));
                retArray.add(key.toString());
                key = null;
            }
            if (retArray.isEmpty()) {
                ret = new String[0];
            } else {
                ret = retArray.toArray(new String[retArray.size()]);
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
     * <br>[機  能] ツリーのキーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @param lv 階層LV
     * @param fdrSidList ディレクトリSIDリスト
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public String[] getTreeListForMove(int fcbSid, int lv, List<Integer> fdrSidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_SID, -1) as FDR_SID,");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_PARENT_SID, -1) as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(lv);

            if (fdrSidList != null && fdrSidList.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_SID not in(");

                int i = 0;
                for (Integer fdrSid : fdrSidList) {
                    if (i > 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(fdrSid);
                    i++;
                }
                sql.addSql(" )");
            }
            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            ArrayList<String> retArray = new ArrayList<String>();
            while (rs.next()) {
                String key = "";
                key = String.valueOf(rs.getInt("FDR_SID"));
                key = key + sep;
                key = key + String.valueOf(rs.getInt("FDR_PARENT_SID"));
                key = key + sep;
                key = key + StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FDR_NAME"));
                key = key + sep;
                key = key + GSConstFile.ACCESS_KBN_WRITE;
                retArray.add(key);
            }
            if (retArray.isEmpty()) {
                ret = new String[0];
            } else {
                ret = retArray.toArray(new String[retArray.size()]);
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
     * <br>[機  能] ツリーのキーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fcbSid キャビネットSID
     * @param lv 階層LV
     * @param fdrSidList ディレクトリSIDリスト
     * @param authUsrSid アクセス許可ユーザSID
     * @return ret ツリーキーリスト
     * @throws SQLException SQL実行時例外
     */
    public String[] getTreeListForMoveAccessLimit(int fcbSid, int lv,
                            List<Integer> fdrSidList, int authUsrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_SID, -1) as FDR_SID,");
            sql.addSql("   COALESCE(FILE_DIRECTORY.FDR_PARENT_SID, -1) as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME,");
            sql.addSql("   DACCESS.FDA_AUTH");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FCB_SID = ? group by FDR_SID) DIR_MAXVERSION,");
            sql.addIntValue(fcbSid);

            //ディレクトリのアクセス設定で参照ファイルを制限する
            sql.addSql("   ( ");
            sql.addSql("   select");
            sql.addSql("     ? as FDR_SID,");
            sql.addSql("     ? as FDA_AUTH");
            sql.addSql("   union all");
            sql.addSql("   select");
            sql.addSql("     A.FDR_SID,");
            sql.addSql("     max(A.FDA_AUTH) as FDA_AUTH");
            sql.addSql("   from");
            sql.addSql("     FILE_DACCESS_CONF A");
            sql.addSql("   where");
            sql.addSql("     exists (");
            sql.addSql("       select *");
            sql.addSql("       from");
            sql.addSql("         FILE_DIRECTORY D");
            sql.addSql("       where");
            sql.addSql("         D.FDR_ACCESS_SID = A.FDR_SID");
            sql.addSql("       and");
            sql.addSql("         D.FCB_SID = ?");
            sql.addSql("       and");
            sql.addSql("         D.FDR_KBN = ?");
            sql.addSql("       and");
            sql.addSql("         D.FDR_LEVEL = ?)");
            sql.addSql("   and (");
            sql.addSql("     (A.USR_KBN = ? and");
            sql.addSql("      A.USR_SID = ?) or");
            sql.addSql("     (A.USR_KBN = ? and");
            sql.addSql("      exists");
            sql.addSql("      (select *");
            sql.addSql("         from CMN_BELONGM B");
            sql.addSql("        where B.GRP_SID = A.USR_SID");
            sql.addSql("          and B.USR_SID = ?");
            sql.addSql("      )))");
            sql.addSql("   group by");
            sql.addSql("     A.FDR_SID");
            sql.addSql("   ) DACCESS");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(lv);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(authUsrSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(authUsrSid);

            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = DACCESS.FDR_SID");

            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(lv);

            if (fdrSidList != null && fdrSidList.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   FILE_DIRECTORY.FDR_SID not in(");

                int i = 0;
                for (Integer fdrSid : fdrSidList) {
                    if (i > 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(fdrSid);
                    i++;
                }
                sql.addSql(" )");
            }

            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
            ArrayList<String> retArray = new ArrayList<String>();
            while (rs.next()) {
                String key = "";
                key = String.valueOf(rs.getInt("FDR_SID"));
                key = key + sep;
                key = key + String.valueOf(rs.getInt("FDR_PARENT_SID"));
                key = key + sep;
                key = key + StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FDR_NAME"));
                key = key + sep;
                key = key + String.valueOf(rs.getInt("FDA_AUTH"));
                retArray.add(key);
            }
            if (retArray.isEmpty()) {
                ret = new String[0];
            } else {
                ret = retArray.toArray(new String[retArray.size()]);
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
     * <p>個人キャビネット詳細内でアクセス許可されたディレクトリ一覧を取得。
     * @param fcbMdl   キャビネットデータ
     * @param userSid  アクセスするユーザSID
     * @return 検索にヒットしたディレクトリデータ一覧 FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getAuthDirectoryList(FileCabinetModel fcbMdl,
                                                                           int userSid)
            throws SQLException {
        return getAuthDirectoryList(fcbMdl, null, userSid, false);
    }

    /**
     * <p>個人キャビネット詳細内でアクセス許可されたディレクトリ一覧を取得。
     * @param fcbMdl   キャビネットデータ
     * @param curMdl   カレントディレクトリデータ
     * @param userSid  アクセスするユーザSID
     * @param isFile   ファイル取得フラグ(true: ファイルも含める / false: ディレクトリのみ)
     * @return 検索にヒットしたディレクトリデータ一覧 FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getAuthDirectoryList(FileCabinetModel fcbMdl,
                                                              FileDirectoryModel curMdl,
                                                              int userSid,
                                                              boolean isFile)
            throws SQLException {

        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        if (fcbMdl == null || fcbMdl.getFcbPersonalFlg() != GSConstFile.CABINET_KBN_PRIVATE) {
            return ret;
        }

        int fcbSid = fcbMdl.getFcbSid();

        ArrayList<FileDirectoryModel> dirList = getChildDirectoryList(
                fcbSid, curMdl, -1);

        if (dirList == null || dirList.size() == 0) {
            return ret;
        }

        // 個人キャビネットの場合、所有者は全てのフォルダにアクセスできる
        if (fcbMdl.getFcbOwnerSid() == userSid) {
            for (FileDirectoryModel dirMdl : dirList) { // 階層分チェック
                if (isFile || dirMdl.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
                    ret.add(dirMdl); // ディレクトリの場合のみ抽出
                }
            }
            return ret;
        }

        ArrayList<Integer> dirSids = new ArrayList<Integer>();
        HashMap<Integer, FileDirectoryModel> dirMap =
                new HashMap<Integer, FileDirectoryModel>();
        for (FileDirectoryModel dirMdl : dirList) {
            Integer key = Integer.valueOf(dirMdl.getFdrSid());
            dirSids.add(key);
            dirMap.put(key, dirMdl);
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //キャビネットSIDからアクセス制御一覧取得
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DACCESS_CONF.FDR_SID as FDR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" on CMN_BELONGM.GRP_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" where ((");
            sql.addSql("   FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");
            sql.addSql(" )");
            sql.addSql(" or (");
            sql.addSql("   FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = ?");
            sql.addSql(" ))");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(userSid);

            // 指定ディレクトリ以下の階層ディレクトリをチェック
            sql.addSql(" and");
            FileDao.getLoopListSql(sql, "FILE_DACCESS_CONF.FDR_SID", dirSids);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            ArrayList<Integer> authDirSids = new ArrayList<Integer>();
            while (rs.next()) {
                authDirSids.add(Integer.valueOf(rs.getInt("FDR_SID")));
            }

            Set<Integer> addFdrSidSet = new HashSet<Integer>();
            // アクセス権がないディレクトリを除外(逆順で回すことで下階層からチェック)
            for (int i = dirList.size() - 1; i >= 0; i--) {
                FileDirectoryModel dirMdl = dirList.get(i);

                // ディレクトリのアクセス権限をチェック
                if (authDirSids.contains(Integer.valueOf(dirMdl.getFdrSid()))) {
                    // 自ディレクトリ＋親ディレクトリを許可リストへ追加
                    for (int lv = dirMdl.getFdrLevel(); lv >= 0; lv--) { // 階層分チェック
                        //許可ディレクトリ一覧へ追加する。
                        if (isFile || dirMdl.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
                            //ディレクトリ、かつ許可ディレクトリ一覧に存在しない場合のみ抽出
                            if (!addFdrSidSet.contains(dirMdl.getFdrSid())) {
                                ret.add(dirMdl);
                                addFdrSidSet.add(dirMdl.getFdrSid());
                            }
                        }
                        dirMap.remove(dirMdl.getFdrSid()); // 重複チェックしないよう検索マップから削除

                        // チェック終了判定
                        Integer parentSid = Integer.valueOf(dirMdl.getFdrParentSid());
                        if (dirMdl.getFdrParentSid() == 0   // 最上位階層
                         || !dirMap.containsKey(parentSid)  // チェック対象に含まれない(または既にチェック済み)
                         || lv < dirMdl.getFdrLevel()) {    // 階層の逆転が発生した場合、ループの可能性があるので強制終了
                            break;
                        }
                        dirMdl = dirMap.get(parentSid); // 次の親ディレクトリを調査
                    }
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
     * <p>指定したユーザがアクセス可能なディレクトリ情報を取得する
     * @param userSid ユーザSID
     * @param dirMap
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getAccessDirectory(int userSid,
            HashMap<Integer, FileDirectoryModel> dirMap)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        ArrayList<Integer> dirSids = new ArrayList<Integer>();
        //チェック対象のSID一覧を作成
        for (int key : dirMap.keySet()) {
            dirSids.add(key);
        }

        try {
            //キャビネットSIDからアクセス制御一覧取得
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DACCESS_CONF.FDR_SID as FDR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" on CMN_BELONGM.GRP_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" where ((");
            sql.addSql("   FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");
            sql.addSql(" )");
            sql.addSql(" or (");
            sql.addSql("   FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = ?");
            sql.addSql(" ))");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(userSid);

            // 指定ディレクトリ以下の階層ディレクトリをチェック
            sql.addSql(" and");
            FileDao.getLoopListSql(sql, "FILE_DACCESS_CONF.FDR_SID", dirSids);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(Integer.valueOf(rs.getInt("FDR_SID")));
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
     * <p>指定したキャビネットのルートディレクトリ情報を取得する。
     * @param fcbSid キャビネットSID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryModel getRootDirectory(int fcbSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileDirectoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_PARENT_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_LEVEL = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.DIRECTORY_LEVEL_0);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getFileDirectoryFromRs(rs);
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
     * <p>指定したキャビネットのルートディレクトリ情報を取得する。
     * @param fcbSids キャビネットSID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<FileDirectoryModel> getRootDirectory(
            Collection<Integer> fcbSids
            ) throws SQLException {
        Connection con = null;
        List<FileDirectoryModel> ret = new ArrayList<>();
        if (fcbSids == null || fcbSids.isEmpty()) {
            return ret;
        }

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = fcbSids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        //フィールド名宣言部
        sb.append("   FDR_SID,");
        sb.append("   FDR_VERSION,");
        sb.append("   FCB_SID,");
        sb.append("   FDR_PARENT_SID,");
        sb.append("   FDR_KBN,");
        sb.append("   FDR_VER_KBN,");
        sb.append("   FDR_LEVEL,");
        sb.append("   FDR_NAME,");
        sb.append("   FDR_BIKO,");
        sb.append("   FDR_JTKBN,");
        sb.append("   FDR_AUID,");
        sb.append("   FDR_ADATE,");
        sb.append("   FDR_EUID,");
        sb.append("   FDR_EDATE,");
        sb.append("   FDR_EGID,");
        sb.append("   FDR_ACCESS_SID,");
        sb.append("   FDR_TRADE_DATE,");
        sb.append("   FDR_TRADE_TARGET,");
        sb.append("   FDR_TRADE_MONEYKBN,");
        sb.append("   FDR_TRADE_MONEY,");
        sb.append("   EMT_SID");
        sb.append(" from ");
        sb.append("   FILE_DIRECTORY");
        sb.append(" where ");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" FCB_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   FDR_PARENT_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_LEVEL = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addIntValue(GSConstFile.DIRECTORY_LEVEL_0);

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        FileDirectoryModel mdl = __getFileDirectoryFromRs(rs);
                        ret.add(mdl);
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }

    /**
     * <p>指定したキャビネットのディレクトリ情報を取得する。
     * @param fcbSid キャビネットSID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List <FileDirectoryModel> getDirectoryList(int fcbSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        FileDirectoryModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addSql(" order by ");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new FileDirectoryModel();
                bean.setFdrSid(rs.getInt("FDR_SID"));
                bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
                bean.setFdrLevel(rs.getInt("FDR_LEVEL"));
                bean.setFdrName(rs.getString("FDR_NAME"));
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
     * <p>指定したディレクトリ内の仮登録ファイル一覧を取得する
     * @param fdrSid ディレクトリSID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List <FileDirectoryDspModel> getErrlDirectoryList(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List <FileDirectoryDspModel> ret = new ArrayList<FileDirectoryDspModel>();
        FileDirectoryModel bean = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   DIR.FDR_SID as FDR_SID,");
            sql.addSql("   DIR.FDR_NAME as FDR_NAME,");
            sql.addSql("   COALESCE(BIN.BIN_SID, -1) as BIN_SID,");
            sql.addSql("   COALESCE(BIN.FFL_EXT, '') as FFL_EXT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM USR,");
            sql.addSql("   CMN_USRM_INF USRM,");
            sql.addSql("   FILE_DIRECTORY DIR left join FILE_FILE_BIN BIN");
            sql.addSql("   on DIR.FDR_SID = BIN.FDR_SID");
            sql.addSql("   and DIR.FDR_VERSION = BIN.FDR_VERSION");
            sql.addSql("     left join CMN_GROUPM");
            sql.addSql("       on DIR.FDR_EGID = CMN_GROUPM.GRP_SID,");

            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY");
            sql.addSql("    where FDR_PARENT_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   DIR.FDR_PARENT_SID = ?");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   USRM.USR_SID = USR.USR_SID");
            sql.addSql(" and");
            sql.addSql("   DIR.FDR_JTKBN = 0");


            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new FileDirectoryModel();
                bean.setFdrSid(rs.getInt("FDR_SID"));
                bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
                bean.setFdrLevel(rs.getInt("FDR_LEVEL"));
                bean.setFdrName(rs.getString("FDR_NAME"));
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
     * <br>[機  能] キャビネット内の更新後10年経っていないファイルの存在判定
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param date 10年前の日付
     * @return ture:存在する/false:存在しない
     */
    public boolean existFileTenYers(int fcbSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_PARENT_SID > 0");
            sql.addSql(" and");
            sql.addSql("   FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_EDATE > ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addDateValue(date);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 指定ディレクトリ以下のフォルダSIDを全て取得します(引数で指定したSIDを含む)
     * <br>[解  説]
     * <br>[備  考] 子孫ディレクトリSIDを全て取得します。
     * @param fdrSid 親ディレクトリSID
     * @return 更新後10年経っていないファイルを保持しているフォルダ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getLowerDirSid(int fdrSid) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        ret.add(fdrSid);
        PreparedStatement pstmt = null;
        PreparedStatement pstmtUpd = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;
        try {

            /*一番下の階層のフォルダを取得するセレクトステートメント*/
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID ");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY ");
            sql.addSql(" where ");
            sql.addSql("   FDR_PARENT_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   FDR_KBN = ? ");

            sql.addIntValue(fdrSid);
            sql.addIntValue(GSConstFile.DIRECTORY_KBN_FOLDER);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            List<Integer> folderList = new ArrayList<Integer>();
            while (rs.next()) {
                folderList.add(rs.getInt("FDR_SID"));
            }
            ret.addAll(folderList);

            while (folderList.size() > 0) {
                List<Integer> result = new ArrayList<Integer>();
                int count = 0;
                /*再利用するセレクトステートメント*/
                sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   FDR_SID ");
                sql.addSql(" from ");
                sql.addSql("   FILE_DIRECTORY ");
                sql.addSql(" where ");
                sql.addSql("   FDR_PARENT_SID in (");
                for (int dirSid : folderList) {
                    if (count != 0) {
                        sql.addSql(",");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(dirSid);
                    count++;
                }
                sql.addSql("   )");
                sql.addSql(" and ");
                sql.addSql("   FDR_KBN = ? ");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    result.add(rs.getInt("FDR_SID"));
                }
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
                sql.clearValue();
                ret.addAll(result);
                folderList = result;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmtUpd);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }


    /**
     * <br>[機  能] ディレクトリが論理削除されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @return true:論理削除されている/false:論理削除されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isLogicalDelDirectory(int fdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   FDR_JTKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fdrSid);
            sql.addIntValue(GSConstFile.JTKBN_DELETE);

            log__.info(sql.toLogString());
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
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where");

            sql.addSql("   FDR_SID in (-1");

            for (int i = 0; i < delList.size(); i++) {

                FileDirectoryModel bean = (FileDirectoryModel) delList.get(i);
                sql.addSql(",?");
                sql.addIntValue(bean.getFdrSid());
            }
            sql.addSql("   )");

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
     * <p>Delete FILE_DIRECTORY
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
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION = ?");

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
     * <p>指定したディレクトリの全バージョンを削除します。
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
            sql.addSql("   FILE_DIRECTORY");
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
     * <p>キャビネットSIDを指定し削除します。
     * @param fcbSid FCB_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBelongCabinet(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
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
     * <p>キャビネットSIDを指定し削除します。
     * @param dirSid FCB_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBelongParent(int dirSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FDR_PARENT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(dirSid);

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
     * <br>[機  能] 指定したディレクトリの指定バージョン以前のものを削除する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fdrSid ディレクトリSID
     * @param fdrVerKbn バージョン管理区分
     * @throws SQLException 例外
     */
    public void deleteOldVersion(int fdrSid, int fdrVerKbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   FDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_VERSION < ?");

            sql.addIntValue(fdrSid);
            sql.addIntValue(fdrVerKbn);

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
     * <p>論理削除されてから指定日数を経過しているフォルダを取得する。(個人・共有キャビネット内)
     * @param level ディレクトリ階層
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getDeletedFolder(int level) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __setSelectSql(sql);
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_ERRL!=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_JKBN=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");

            sql.addIntValue(GSConstFile.SELECT_CABINET_ERRL);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addIntValue(level);

            sql.addSql(" and not exists ( ");
            sql.addSql("   select 1");
            sql.addSql("   from ");
            sql.addSql("     FILE_DIRECTORY DIR");
            sql.addSql("   where ");
            sql.addSql("     DIR.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>論理削除されてから指定日数を経過しているファイルを取得する。(個人・共有キャビネット内)
     * @param date 基準日付
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getDeletedFile(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __setSelectSql(sql);
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE < ?");
            sql.addDateValue(date);
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_ERRL!=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_JKBN=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN=?");

            sql.addIntValue(GSConstFile.SELECT_CABINET_ERRL);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(GSConstFile.DIRECTORY_FILE);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>最終更新から10年が経過している電帳法フォルダを取得する。
     * @param level ディレクトリ階層
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getDeletedFolderErrl(int level) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __setSelectSql(sql);
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL = ?");
            sql.addIntValue(level);
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_ERRL=?");
            sql.addIntValue(GSConstFile.ERRL_KBN_ON);
            sql.addSql(" and not exists (");
            sql.addSql("   select 1");
            sql.addSql("   from ");
            sql.addSql("     FILE_DIRECTORY DIR");
            sql.addSql("   where ");
            sql.addSql("     DIR.FDR_PARENT_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>最終更新から10年が経過している電帳法ファイルを取得する。
     * @param date 基準日付
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getDeletedFileErrl(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __setSelectSql(sql);
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_FILE);
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_ERRL=?");
            sql.addIntValue(GSConstFile.ERRL_KBN_ON);
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID in (");
            sql.addSql("     select ");
            sql.addSql("       FDR_SID");
            sql.addSql("     from ");
            sql.addSql("       FILE_FILE_REKI");
            sql.addSql("     where ");
            sql.addSql("       FDR_VERSION = (");
            sql.addSql("         select ");
            sql.addSql("           max(fdr_version) - 1");
            sql.addSql("         from ");
            sql.addSql("           FILE_FILE_REKI REKI");
            sql.addSql("         where ");
            sql.addSql("           FILE_FILE_REKI.FDR_SID = REKI.FDR_SID");
            sql.addSql("       )");
            sql.addSql("     and ");
            sql.addSql("       FFR_EDATE < ?");
            sql.addSql("   )");
            sql.addDateValue(date);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>ファイル情報の取得SQLを返します。
     * @param sql SqlBuffer
     * @return 取得SQL
     */
    public SqlBuffer __setSelectSql(SqlBuffer sql) {

        sql.addSql(" select");
        sql.addSql("   FILE_DIRECTORY.FDR_SID,");
        sql.addSql("   FILE_DIRECTORY.FDR_VERSION,");
        sql.addSql("   FILE_DIRECTORY.FCB_SID,");
        sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
        sql.addSql("   FILE_DIRECTORY.FDR_KBN,");
        sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN,");
        sql.addSql("   FILE_DIRECTORY.FDR_LEVEL,");
        sql.addSql("   FILE_DIRECTORY.FDR_NAME,");
        sql.addSql("   FILE_DIRECTORY.FDR_BIKO,");
        sql.addSql("   FILE_DIRECTORY.FDR_JTKBN,");
        sql.addSql("   FILE_DIRECTORY.FDR_AUID,");
        sql.addSql("   FILE_DIRECTORY.FDR_ADATE,");
        sql.addSql("   FILE_DIRECTORY.FDR_EUID,");
        sql.addSql("   FILE_DIRECTORY.FDR_EDATE,");
        sql.addSql("   FILE_DIRECTORY.FDR_EGID,");
        sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID,");
        sql.addSql("   FILE_DIRECTORY.FDR_TRADE_DATE,");
        sql.addSql("   FILE_DIRECTORY.FDR_TRADE_TARGET,");
        sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEYKBN,");
        sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEY,");
        sql.addSql("   FILE_DIRECTORY.EMT_SID");
        sql.addSql(" from");
        sql.addSql("   FILE_DIRECTORY");
        sql.addSql(" inner join FILE_CABINET");
        sql.addSql("   on FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");

        return sql;
    }

    /**
     * <p>指定したディレクトリのディレクトリ種別を取得する。
     * @param fdrSids FDR_SIDリスト
     * @return FILE_DIRECTORYModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getFdrKbn(String[] fdrSids) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        if (fdrSids == null || fdrSids.length < 1) {
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
            sql.addSql("   FDR_KBN as FDR_KBN,");
            sql.addSql("   count(FDR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where");
            sql.addSql(" (");

            int i = 0;
            for (String fdrSid : fdrSids) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql("   FDR_SID = ?");
                sql.addIntValue(Integer.parseInt(fdrSid));
                i++;
            }
            sql.addSql(" )");

            sql.addSql(" group by");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("FDR_KBN"));
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
     * <p>指定したキャビネットタイプに属するディレクトリを取得する。
     * @param cabType 0:共有キャビネット / 1: 個人キャビネット
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getDirectoryWithCabinetType(int cabType)
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
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID as FDR_PARENT_SID,");
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
            sql.addSql("   FILE_DIRECTORY.FDR_EGID as FDR_EGID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID as FDR_ACCESS_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_DATE as FDR_TRADE_DATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_TARGET as FDR_TRADE_TARGET,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEYKBN as FDR_TRADE_MONEYKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEY as FDR_TRADE_MONEY,");
            sql.addSql("   FILE_DIRECTORY.EMT_SID as EMT_SID,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG as FCB_PERSONAL_FLG");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" left join");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" on FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" where");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cabType);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileDirectoryFromRs(rs));
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
     * <p>指定ディレクトに属する子ディレクトリ一覧を取得。
     * @param cabSid キャビネットSID
     * @param dirMdl 指定ディレクトリデータ
     * @param dirKbn ディレクトリ区分
     * @return 下位ディレクトリ一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDirectoryModel> getChildDirectoryList(int cabSid,
            FileDirectoryModel dirMdl, int dirKbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();

        int dirLevel = -1;
        int dirSid   = -1;
        if (dirMdl != null) {
            dirLevel = dirMdl.getFdrLevel();
            dirSid   = dirMdl.getFdrSid();
        }

        try {
            //キャビネットSIDからアクセス制御一覧取得
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DIRECTORY.FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME,");
            sql.addSql("   FILE_DIRECTORY.FDR_BIKO,");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_AUID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ADATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_EUID,");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_EGID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_DATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_TARGET,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEYKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_TRADE_MONEY,");
            sql.addSql("   FILE_DIRECTORY.EMT_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION from FILE_DIRECTORY");
            sql.addSql("    where FCB_SID = ? group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addIntValue(cabSid);
            sql.addIntValue(cabSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            if (dirLevel >= 0) {
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_LEVEL > ?");
                sql.addIntValue(dirLevel);
            }

            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");


            if (dirKbn >= 0) {
                sql.addSql(" and ");
                sql.addSql("   FILE_DIRECTORY.FDR_KBN = ?");
                sql.addIntValue(dirKbn);
            }
            sql.addSql(" order by FDR_LEVEL, FDR_PARENT_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            // 指定下位階層
            ArrayList<Integer> dirSids = new ArrayList<Integer>();
            ArrayList<Integer> parentSids = new ArrayList<Integer>();
            int level = 0;
            if (dirSid >= 0) {
                // 指定フォルダ以下をチェック
                level = dirLevel + 1;
                parentSids.add(Integer.valueOf(dirSid));
            } else {
                // ルートフォルダ指定
                level = 0;
                parentSids.add(Integer.valueOf(0));
            }

            while (rs.next()) {
                FileDirectoryModel mdl = __getFileDirectoryFromRs(rs);

                // 階層チェック
                if (level < mdl.getFdrLevel()) {
                    // 次の階層へ移動した場合、親ディレクトリSID一覧を更新
                    level = mdl.getFdrLevel();
                    parentSids.clear();
                    parentSids.addAll(dirSids);
                    dirSids.clear();
                }

                // 親ディレクトリが属している場合、リスト追加
                Integer parentSid = Integer.valueOf(mdl.getFdrParentSid());
                if (parentSids.contains(parentSid)) {
                    Integer curDirSid = Integer.valueOf(mdl.getFdrSid());
                    ret.add(mdl);
                    dirSids.add(curDirSid);
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
     * ディレクトリモデルデータからキー作成
     * @param level 階層
     * @param list リスト
     * @param auth 許可判定フラグ
     * @return キー一覧
     * @throws SQLException SQL実行例外
     */
    public String[] getKeyStringArray(int level, List<FileDirectoryModel> list, String auth) {
        String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
        ArrayList<String> retArray = new ArrayList<String>();
        String[] ret = null;
        for (FileDirectoryModel mdl : list) {
            if (level == mdl.getFdrLevel()) {
                StringBuilder key = new StringBuilder();
                key.append(String.valueOf(mdl.getFdrSid()));
                key.append(sep);
                key.append(String.valueOf(mdl.getFdrParentSid()));
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(mdl.getFdrName()));
                if (auth != null) {
                    key.append(sep);
                    key.append(auth);
                }
                retArray.add(key.toString());
            }
        }
        if (retArray.isEmpty()) {
            ret = new String[0];
        } else {
            ret = retArray.toArray(new String[retArray.size()]);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した条件に有ったディレクトリのディレクトリSIDを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param parentDirSid 親フォルダSID
     * @param fdrName フォルダ名
     * @return 条件に合致したディレクトリのディレクトリSID
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryModel getErrlDirModel(
            int fcbSid, int parentDirSid, String fdrName) throws SQLException {
        return getErrlDirModel(fcbSid, parentDirSid, fdrName, false);
    }

    /**
     * <br>[機  能] 指定した条件に有ったディレクトリのディレクトリSIDを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param parentDirSid 親フォルダSID
     * @param fdrName フォルダ名
     * @param delOutFlg 削除除外フラグ
     * @return 条件に合致したディレクトリのディレクトリSID
     * @throws SQLException SQL実行例外
     */
    public FileDirectoryModel getErrlDirModel(
            int fcbSid, int parentDirSid, String fdrName, boolean delOutFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileDirectoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE,");
            sql.addSql("   FDR_EGID,");
            sql.addSql("   FDR_ACCESS_SID,");
            sql.addSql("   FDR_TRADE_DATE,");
            sql.addSql("   FDR_TRADE_TARGET,");
            sql.addSql("   FDR_TRADE_MONEYKBN,");
            sql.addSql("   FDR_TRADE_MONEY,");
            sql.addSql("   EMT_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_PARENT_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FDR_NAME = ?");
            sql.addIntValue(fcbSid);
            sql.addIntValue(parentDirSid);
            sql.addStrValue(fdrName);
            if (delOutFlg) {
                sql.addSql(" and");
                sql.addSql("   FDR_JTKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getFileDirectoryFromRs(rs);
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
     * <p>Create FILE_DIRECTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private FileDirectoryModel __getFileDirectoryFromRs(ResultSet rs) throws SQLException {
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
        bean.setFdrEgid(rs.getInt("FDR_EGID"));
        bean.setFdrAccessSid(rs.getInt("FDR_ACCESS_SID"));
        bean.setFdrTradeDate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_TRADE_DATE")));
        bean.setFdrTradeTarget(rs.getString("FDR_TRADE_TARGET"));
        bean.setFdrTradeMoneykbn(rs.getInt("FDR_TRADE_MONEYKBN"));
        bean.setFdrTradeMoney(rs.getBigDecimal("FDR_TRADE_MONEY"));
        bean.setEmtSid(rs.getInt("EMT_SID"));
        return bean;
    }
    /**
     * <p>Create FILE_DIRECTORY DSP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @return created FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private FileDirectoryDspModel __getFileDirectoryDspFromRs(
            ResultSet rs, RequestModel reqMdl) throws SQLException {
        FileDirectoryDspModel bean = new FileDirectoryDspModel();
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
        bean.setFdrEgid(rs.getInt("FDR_EGID"));
        bean.setFdrTradeDate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_TRADE_DATE")));
        bean.setFdrTradeTarget(rs.getString("FDR_TRADE_TARGET"));
        bean.setFdrTradeMoney(rs.getBigDecimal("FDR_TRADE_MONEY"));
        bean.setFdrTradeMoneykbn(rs.getInt("FDR_TRADE_MONEYKBN"));

        if (bean.getFdrTradeDate() != null) {
            bean.setTradeDate(bean.getFdrTradeDate().getDateString("/"));
        }
        String tradeMoney = "-";
        if (bean.getFdrTradeMoneykbn() == GSConstFile.MONEY_KBN_ON) {
            tradeMoney = StringUtil.toCommaUnderZeroTrim(
                    bean.getFdrTradeMoney().toPlainString())
                    + " "
                    + rs.getString("FMM_NAME");
        }
        bean.setTradeMoney(tradeMoney);

        //追加情報
        bean.setFileBinSid(rs.getLong("BIN_SID"));
        BigDecimal bdSize = rs.getBigDecimal("FFL_FILE_SIZE");
        //B→KBへ変換
        String strSize = StringUtil.toCommaFromBigDecimal(
                bdSize.divide(GSConstFile.KB_TO_MB, 1, RoundingMode.HALF_UP));
        bean.setFileSize(strSize + " KB");
        bean.setFflFileSize(rs.getLong("FFL_FILE_SIZE"));
        bean.setCallKbn(rs.getString("CALLKBN"));
        UDate edate = UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE"));
        bean.setEdateString(UDateUtil.getSlashYYMD(edate) + " " + UDateUtil.getSeparateHMS(edate));

        if (bean.getFdrEgid() > 0) {
            bean.setUpUsrName(rs.getString("GRP_NAME"));
            bean.setUpUsrJkbn(rs.getInt("GRP_JKBN"));
            bean.setUpUsrUkoFlg(GSConst.YUKOMUKO_YUKO);
        } else {
            bean.setUpUsrName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
            bean.setUpUsrJkbn(rs.getInt("USR_JKBN"));
            bean.setUpUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        }

        bean.setLockKbn(rs.getString("FFL_LOCK_KBN"));
        int usrSid = -1;
        String textLockDate = "";
        if (rs.getInt("FFL_LOCK_KBN") == GSConstFile.LOCK_KBN_ON) {
            usrSid = rs.getInt("FFL_LOCK_USER");
            UDate lockDate = UDate.getInstanceTimestamp(rs.getTimestamp("FFL_LOCK_DATE"));
            if (lockDate != null) {
                textLockDate = UDateUtil.getYymdJ(lockDate, reqMdl);
                textLockDate += UDateUtil.getSeparateHMJ(lockDate, reqMdl);
            }
        }
        bean.setLockUsrSid(usrSid);
        bean.setLockDate(textLockDate);

        //if (bean.getLockKbn().equals(GSConstFile.LOCK_KBN_ON)) {
            bean.setLockUsrName(rs.getString("LOCK_UNAME"));
        //}

        bean.setAccessKbn(rs.getInt("ACKBN"));

        return bean;
    }
}

