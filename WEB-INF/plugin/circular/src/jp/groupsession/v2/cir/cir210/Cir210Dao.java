package jp.groupsession.v2.cir.cir210;

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
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 管理者設定 統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir210Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir210Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Cir210Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Cir210Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された期間の受信回覧板の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 受信回覧板の集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Cir210DspModel> getJcirLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {
        Map <String, Cir210DspModel> ret = new HashMap<String, Cir210DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   cast(CLC_DATE as date) as JDATE, ");
            sql.addSql("   count(CLC_CNT) as JCIR ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   CLC_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_JCIR);
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   JDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cir210DspModel model = new Cir210DspModel();
                String strDate = rs.getString("JDATE");
                model.setDate(strDate);
                model.setJcirNum(rs.getInt("JCIR"));
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
     * <br>[機  能] 指定された期間の送信回覧板の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 送信回覧板の集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Cir210DspModel> getScirLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {
        Map <String, Cir210DspModel> ret = new HashMap<String, Cir210DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   cast(CLC_DATE as date) as SDATE, ");
            sql.addSql("   count(CLC_CNT) as SCIR ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   CLC_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_SCIR);
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   SDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cir210DspModel model = new Cir210DspModel();
                String strDate = rs.getString("SDATE");
                model.setDate(strDate);
                model.setScirNum(rs.getInt("SCIR"));
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
     * <br>[機  能] 指定された期間の受信回覧板の集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalJushinLog(
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
            sql.addSql("   CIR_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   CLC_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_JCIR);
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE <= ? ");

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
     * <br>[機  能] 指定された期間の送信回覧板の集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalSoushinLog(
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
            sql.addSql("   CIR_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   CLC_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_SCIR);
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   CLC_DATE <= ? ");

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
     * <br>[機  能] 集計データの最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLogCountMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(CLC_DATE) as MAX_DATE, ");
            sql.addSql("   min(CLC_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT ");

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
     * <br>[機  能] 指定された期間の受信回覧板の集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 受信回覧板の集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Cir210DspModel> getJcirLogSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {
        Map <String, Cir210DspModel> ret = new HashMap<String, Cir210DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("   CLS_MONTH as JDATE, ");
            } else {
                sql.addSql("   CLS_DATE as JDATE, ");
            }
            sql.addSql("   sum(CLS_CNT) as JCIR ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   CLS_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_JCIR);
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   JDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cir210DspModel model = new Cir210DspModel();
                String strDate = rs.getString("JDATE");
                model.setDate(strDate);
                model.setJcirNum(rs.getInt("JCIR"));
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
     * <br>[機  能] 指定された期間の送信回覧板の集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 送信回覧板の集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Cir210DspModel> getScirLogSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {
        Map <String, Cir210DspModel> ret = new HashMap<String, Cir210DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("   CLS_MONTH as SDATE, ");
            } else {
                sql.addSql("   CLS_DATE as SDATE, ");
            }
            sql.addSql("   sum(CLS_CNT) as SCIR ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   CLS_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_SCIR);
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   SDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cir210DspModel model = new Cir210DspModel();
                String strDate = rs.getString("SDATE");
                model.setDate(strDate);
                model.setScirNum(rs.getInt("SCIR"));
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
     * <br>[機  能] 指定された期間の受信回覧板の集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalJushinLogSum(
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
            sql.addSql("   sum(CLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   CLS_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_JCIR);
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE <= ? ");

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
     * <br>[機  能] 指定された期間の送信回覧板の集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalSoushinLogSum(
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
            sql.addSql("   sum(CLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   CLS_KBN = ? ");
            sql.addIntValue(GSConstCircular.LOG_COUNT_KBN_SCIR);
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE <= ? ");

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
     * <br>[機  能] 集計データの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。月別、週別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLogCountSumMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(CLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(CLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT_SUM ");

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
