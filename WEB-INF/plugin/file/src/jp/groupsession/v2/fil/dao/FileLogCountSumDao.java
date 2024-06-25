package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileLogCountSumModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>FILE_LOG_COUNT_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class FileLogCountSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileLogCountSumDao.class);

    /**
     * <p>Default Constructor
     */
    public FileLogCountSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileLogCountSumDao(Connection con) {
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
            sql.addSql("drop table FILE_LOG_COUNT_SUM");

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
            sql.addSql(" create table FILE_LOG_COUNT_SUM (");
            sql.addSql("   FLS_KBN NUMBER(10,0) not null,");
            sql.addSql("   FLS_DATE Date not null,");
            sql.addSql("   FLS_MONTH NUMBER(10,0) not null,");
            sql.addSql("   FLS_CNT Date not null,");
            sql.addSql("   FLS_EDATE varchar(23) not null");
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
     * <p>Insert FILE_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean FILE_LOG_COUNT_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_LOG_COUNT_SUM(");
            sql.addSql("   FLS_KBN,");
            sql.addSql("   FLS_DATE,");
            sql.addSql("   FLS_MONTH,");
            sql.addSql("   FLS_CNT,");
            sql.addSql("   FLS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFlsKbn());
            sql.addDateValue(bean.getFlsDate());
            sql.addIntValue(bean.getFlsMonth());
            sql.addLongValue(bean.getFlsCnt());
            sql.addDateValue(bean.getFlsEdate());
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
     * <p>Update FILE_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean FILE_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   FLS_MONTH=?,");
            sql.addSql("   FLS_CNT=?,");
            sql.addSql("   FLS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FLS_KBN=?");
            sql.addSql(" and");
            sql.addSql("   FLS_DATE=?");

            sql.addIntValue(bean.getFlsMonth());
            sql.addLongValue(bean.getFlsCnt());
            sql.addDateValue(bean.getFlsEdate());
            sql.addIntValue(bean.getFlsKbn());
            sql.addDateValue(bean.getFlsDate());

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
     * <p>Select FILE_LOG_COUNT_SUM All Data
     * @return List in FILE_LOG_COUNT_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<FileLogCountSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileLogCountSumModel> ret = new ArrayList<FileLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FLS_KBN,");
            sql.addSql("   FLS_DATE,");
            sql.addSql("   FLS_MONTH,");
            sql.addSql("   FLS_CNT,");
            sql.addSql("   FLS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   FILE_LOG_COUNT_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileLogCountSumFromRs(rs));
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
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param flsKbn ログ区分
     * @param date 指定日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public FileLogCountSumModel getSumLogCount(int flsKbn, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileLogCountSumModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            if (flsKbn == GSConstFile.FLS_KBN_DOWNLOAD) {
                //ファイルダウンロードの集計
                sql.addSql(" select");
                sql.addSql("   cast(FDL_DATE as date) as LOG_DATE,");
                sql.addSql("   count(*) as CNT,");
                sql.addSql("   max(FDL_DATE) as MAX_DATE");
                sql.addSql(" from");
                sql.addSql("   FILE_DOWNLOAD_LOG");
                sql.addSql(" where");
                sql.addSql("   FDL_DATE >= ?");
                sql.addSql(" and");
                sql.addSql("   FDL_DATE <= ?");
                sql.addSql(" group by");
                sql.addSql("   LOG_DATE");
            } else {
                //ファイルアップロードの集計
                sql.addSql(" select");
                sql.addSql("   cast(FUL_DATE as date) as LOG_DATE,");
                sql.addSql("   count(*) as CNT,");
                sql.addSql("   max(FUL_DATE) as MAX_DATE");
                sql.addSql(" from");
                sql.addSql("   FILE_UPLOAD_LOG");
                sql.addSql(" where");
                sql.addSql("   FUL_DATE >= ?");
                sql.addSql(" and");
                sql.addSql("   FUL_DATE <= ?");
                sql.addSql(" group by");
                sql.addSql("   LOG_DATE");
            }

            UDate frDate = date.cloneUDate();
            frDate.setZeroHhMmSs();
            UDate toDate = date.cloneUDate();
            toDate.setMaxHhMmSs();
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = new FileLogCountSumModel();
                model.setFlsKbn(flsKbn);

                UDate flsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
                model.setFlsDate(flsDate);
                model.setFlsMonth(Integer.parseInt(flsDate.getDateString().substring(0, 6)));
                model.setFlsCnt(rs.getLong("CNT"));
                model.setFlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }

    /**
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説] 集計結果(FILE_LOG_COUNT_SUM)に未登録のデータのみを対象とする。基準日含む過去3日間
     * <br>[備  考]
     * @param flsKbn ログ区分
     * @param date 基準日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<FileLogCountSumModel> getNonRegisteredList(int flsKbn, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileLogCountSumModel> logSumList = new ArrayList<FileLogCountSumModel>();
        con = getCon();
        UDate from = date.cloneUDate();
        from.addDay(-2);
        from.setZeroHhMmSs();
        UDate to = date.cloneUDate();
        to.setMaxHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LOG_SUM.LOG_DATE as LOG_DATE,");
            sql.addSql("   LOG_SUM.CNT as CNT,");
            sql.addSql("   LOG_SUM.MAX_DATE as MAX_DATE");
            sql.addSql(" from");

            if (flsKbn == GSConstFile.FLS_KBN_DOWNLOAD) {
                //ファイルダウンロードの集計
                sql.addSql("   (");
                sql.addSql("    select");
                sql.addSql("      cast(FDL_DATE as date) as LOG_DATE,");
                sql.addSql("      count(*) as CNT,");
                sql.addSql("      max(FDL_DATE) as MAX_DATE");
                sql.addSql("    from");
                sql.addSql("      FILE_DOWNLOAD_LOG");
                sql.addSql("    where ");
                sql.addSql("      FDL_DATE >= ?");
                sql.addDateValue(from);
                sql.addSql("    and");
                sql.addSql("      FDL_DATE <= ?");
                sql.addDateValue(to);
                sql.addSql("    group by");
                sql.addSql("      LOG_DATE");
                sql.addSql("   ) LOG_SUM");
            } else {
                //ファイルアップロードの集計
                sql.addSql("   (");
                sql.addSql("    select");
                sql.addSql("      cast(FUL_DATE as date) as LOG_DATE,");
                sql.addSql("      count(*) as CNT,");
                sql.addSql("      max(FUL_DATE) as MAX_DATE");
                sql.addSql("    from");
                sql.addSql("      FILE_UPLOAD_LOG");
                sql.addSql("    where ");
                sql.addSql("      FUL_DATE >= ?");
                sql.addDateValue(from);
                sql.addSql("    and");
                sql.addSql("      FUL_DATE <= ?");
                sql.addDateValue(to);
                sql.addSql("    group by");
                sql.addSql("      LOG_DATE");
                sql.addSql("   ) LOG_SUM");
            }

            sql.addSql(" where");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from FILE_LOG_COUNT_SUM");
            sql.addSql("     where");
            sql.addSql("       FILE_LOG_COUNT_SUM.FLS_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       LOG_SUM.LOG_DATE = FILE_LOG_COUNT_SUM.FLS_DATE");
            sql.addSql("     and");
            sql.addSql("       LOG_SUM.MAX_DATE = FILE_LOG_COUNT_SUM.FLS_EDATE");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   LOG_SUM.LOG_DATE");

            sql.addIntValue(flsKbn);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FileLogCountSumModel model = new FileLogCountSumModel();
                model.setFlsKbn(flsKbn);

                UDate flsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
                model.setFlsDate(flsDate);
                model.setFlsMonth(Integer.parseInt(flsDate.getDateString().substring(0, 6)));
                model.setFlsCnt(rs.getLong("CNT"));
                model.setFlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE")));

                logSumList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return logSumList;
    }

    /**
     * <p>Create FILE_LOG_COUNT_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileLogCountSumModel
     * @throws SQLException SQL実行例外
     */
    private FileLogCountSumModel __getFileLogCountSumFromRs(ResultSet rs) throws SQLException {
        FileLogCountSumModel bean = new FileLogCountSumModel();
        bean.setFlsKbn(rs.getInt("FLS_KBN"));
        bean.setFlsDate(UDate.getInstanceTimestamp(rs.getTimestamp("FLS_DATE")));
        bean.setFlsMonth(rs.getInt("FLS_MONTH"));
        bean.setFlsCnt(rs.getInt("FLS_CNT"));
        bean.setFlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FLS_EDATE")));
        return bean;
    }
}
