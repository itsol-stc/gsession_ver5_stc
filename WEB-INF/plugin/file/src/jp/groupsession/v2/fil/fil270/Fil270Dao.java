package jp.groupsession.v2.fil.fil270;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ファイル管理 管理者設定 統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil270Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil270Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Fil270Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Fil270Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された期間のファイルダウンロードの集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Fil270DspModel> getDownloadLogCountData(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Fil270DspModel> ret = new HashMap<String, Fil270DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            sql.addSql("       cast(FDL_DATE as date) as DLDATE, ");
            sql.addSql("       count(*) as DLCNT ");
            sql.addSql("     from ");
            sql.addSql("       FILE_DOWNLOAD_LOG ");
            sql.addSql("     where ");
            sql.addSql("       FDL_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       FDL_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       DLDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Fil270DspModel model = new Fil270DspModel();
                String strDate = rs.getString("DLDATE");
                model.setDate(strDate);
                model.setDownloadNum(rs.getInt("DLCNT"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間のファイルアップロードの集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Fil270DspModel> getUploadLogCountData(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Fil270DspModel> ret = new HashMap<String, Fil270DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            sql.addSql("       cast(FUL_DATE as date) as ULDATE, ");
            sql.addSql("       count(*) as ULCNT ");
            sql.addSql("     from ");
            sql.addSql("       FILE_UPLOAD_LOG ");
            sql.addSql("     where ");
            sql.addSql("       FUL_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       FUL_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       ULDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Fil270DspModel model = new Fil270DspModel();
                String strDate = rs.getString("ULDATE");
                model.setDate(strDate);
                model.setUploadNum(rs.getInt("ULCNT"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間のファイルダウンロード集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalDownloadLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   FILE_DOWNLOAD_LOG ");
            sql.addSql(" where ");
            sql.addSql("   FDL_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   FDL_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 指定された期間のファイルアップロード集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalUploadLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   FILE_UPLOAD_LOG ");
            sql.addSql(" where ");
            sql.addSql("   FUL_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   FUL_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] ダウンロード集計データの最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getDownloadLogMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(FDL_DATE) as MAX_DATE, ");
            sql.addSql("   min(FDL_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   FILE_DOWNLOAD_LOG ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
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
     * <br>[機  能] アップロード集計データの最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getUploadLogMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(FUL_DATE) as MAX_DATE, ");
            sql.addSql("   min(FUL_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   FILE_UPLOAD_LOG ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
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
     * <br>[機  能] 指定された期間のファイルダウンロードの集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Fil270DspModel> getDownloadLogCountDataSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Fil270DspModel> ret = new HashMap<String, Fil270DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("       FLS_MONTH as DLDATE, ");
            } else {
                sql.addSql("       FLS_DATE as DLDATE, ");
            }
            sql.addSql("       sum(FLS_CNT) as DLSUM ");
            sql.addSql("     from ");
            sql.addSql("       FILE_LOG_COUNT_SUM ");
            sql.addSql("     where ");
            sql.addSql("       FLS_KBN = ? ");
            sql.addIntValue(GSConstFile.FLS_KBN_DOWNLOAD);
            sql.addSql("     and ");
            sql.addSql("       FLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       FLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       DLDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Fil270DspModel model = new Fil270DspModel();
                String strDate = rs.getString("DLDATE");
                model.setDate(strDate);
                model.setDownloadNum(rs.getInt("DLSUM"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間のファイルアップロードの集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Fil270DspModel> getUploadLogCountDataSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Fil270DspModel> ret = new HashMap<String, Fil270DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("       FLS_MONTH as ULDATE, ");
            } else {
                sql.addSql("       FLS_DATE as ULDATE, ");
            }
            sql.addSql("       sum(FLS_CNT) as UPSUM ");
            sql.addSql("     from ");
            sql.addSql("       FILE_LOG_COUNT_SUM ");
            sql.addSql("     where ");
            sql.addSql("       FLS_KBN = ? ");
            sql.addIntValue(GSConstFile.FLS_KBN_UPLOAD);
            sql.addSql("     and ");
            sql.addSql("       FLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       FLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       ULDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Fil270DspModel model = new Fil270DspModel();
                String strDate = rs.getString("ULDATE");
                model.setDate(strDate);
                model.setUploadNum(rs.getInt("UPSUM"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間のファイルダウンロード集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalDownloadLogSum(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   sum(FLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   FILE_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   FLS_KBN = ? ");
            sql.addIntValue(GSConstFile.FLS_KBN_DOWNLOAD);
            sql.addSql(" and ");
            sql.addSql("   FLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   FLS_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("SUM");
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
     * <br>[機  能] 指定された期間のファイルアップロード集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalUploadLogSum(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   sum(FLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   FILE_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   FLS_KBN = ? ");
            sql.addIntValue(GSConstFile.FLS_KBN_UPLOAD);
            sql.addSql(" and ");
            sql.addSql("   FLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   FLS_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("SUM");
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
     * <br>[機  能] ダウンロード集計データの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。月別、週別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getDownloadLogSumMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(FLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(FLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   FILE_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   FLS_KBN = ? ");
            sql.addIntValue(GSConstFile.FLS_KBN_DOWNLOAD);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
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
     * <br>[機  能] アップロード集計データの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。月別、週別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getUploadLogSumMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(FLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(FLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   FILE_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   FLS_KBN = ? ");
            sql.addIntValue(GSConstFile.FLS_KBN_UPLOAD);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}