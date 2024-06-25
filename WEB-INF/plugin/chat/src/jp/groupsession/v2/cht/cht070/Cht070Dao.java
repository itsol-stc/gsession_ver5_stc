package jp.groupsession.v2.cht.cht070;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] チャット統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht070Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht070Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Cht070Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Cht070Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] メッセージデータの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLogMaxMinDate() throws SQLException {
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
            sql.addSql("   CHT_LOG_COUNT_SUM ");

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
     * <br>[機  能] 指定された期間の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Cht070DspModel> getMessageLogCountDataSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Cht070DspModel> ret = new HashMap<String, Cht070DspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("       CLS_YEAR_MONTH as CLDATE, ");
            } else {
                sql.addSql("       CLS_DATE as CLDATE, ");
            }
            sql.addSql("       sum(CLS_SEND_CNT) as CNT_SUM, ");
            sql.addSql("       sum(CLS_SEND_UCNT) as CNT_USER, ");
            sql.addSql("       sum(CLS_SEND_GCNT) as CNT_GROUP,");
            sql.addSql("       sum(CLS_USER_CNT) as CNT_SEND");
            sql.addSql("     from ");
            sql.addSql("       CHT_LOG_COUNT_SUM ");
            sql.addSql("     where ");
            sql.addSql("       CLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       CLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       CLDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Cht070DspModel model = new Cht070DspModel();
                String strDate = rs.getString("CLDATE");
                model.setStrDate(strDate);
                model.setUserNum(rs.getInt("CNT_USER"));
                model.setGroupNum(rs.getInt("CNT_GROUP"));
                model.setSumNum(rs.getInt("CNT_SUM"));
                model.setSendNum(rs.getInt("CNT_SEND"));
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
}
