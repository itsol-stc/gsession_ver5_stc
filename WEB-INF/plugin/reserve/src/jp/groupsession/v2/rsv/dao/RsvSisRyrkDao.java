package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;

/**
 * <p>RSV_SIS_RYRK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisRyrkDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisRyrkDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisRyrkDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisRyrkDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_SIS_RYRK Data Bindding JavaBean
     * @param bean RSV_SIS_RYRK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisRyrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_RYRK(");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSR_KBN,");
            sql.addSql("   RSR_DWEEK1,");
            sql.addSql("   RSR_DWEEK2,");
            sql.addSql("   RSR_DWEEK3,");
            sql.addSql("   RSR_DWEEK4,");
            sql.addSql("   RSR_DWEEK5,");
            sql.addSql("   RSR_DWEEK6,");
            sql.addSql("   RSR_DWEEK7,");
            sql.addSql("   RSR_DAY,");
            sql.addSql("   RSR_WEEK,");
            sql.addSql("   RSR_MONTH_YEARLY,");
            sql.addSql("   RSR_DAY_YEARLY,");
            sql.addSql("   RSR_TIME_FR,");
            sql.addSql("   RSR_TIME_TO,");
            sql.addSql("   RSR_TRAN_KBN,");
            sql.addSql("   RSR_DATE_FR,");
            sql.addSql("   RSR_DATE_TO,");
            sql.addSql("   RSR_MOK,");
            sql.addSql("   RSR_BIKO,");
            sql.addSql("   RSR_EDIT,");
            sql.addSql("   RSR_PUBLIC,");
            sql.addSql("   RSR_AUID,");
            sql.addSql("   RSR_ADATE,");
            sql.addSql("   RSR_EUID,");
            sql.addSql("   RSR_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsrRsid());
            sql.addIntValue(bean.getRsrKbn());
            sql.addIntValue(bean.getRsrDweek1());
            sql.addIntValue(bean.getRsrDweek2());
            sql.addIntValue(bean.getRsrDweek3());
            sql.addIntValue(bean.getRsrDweek4());
            sql.addIntValue(bean.getRsrDweek5());
            sql.addIntValue(bean.getRsrDweek6());
            sql.addIntValue(bean.getRsrDweek7());
            sql.addIntValue(bean.getRsrDay());
            sql.addIntValue(bean.getRsrWeek());
            sql.addIntValue(bean.getRsrMonthYearly());
            sql.addIntValue(bean.getRsrDayYearly());
            sql.addDateValue(bean.getRsrTimeFr());
            sql.addDateValue(bean.getRsrTimeTo());
            sql.addIntValue(bean.getRsrTranKbn());
            sql.addDateValue(bean.getRsrDateFr());
            sql.addDateValue(bean.getRsrDateTo());
            sql.addStrValue(bean.getRsrMok());
            sql.addStrValue(bean.getRsrBiko());
            sql.addIntValue(bean.getRsrEdit());
            sql.addIntValue(bean.getRsrPublic());
            sql.addIntValue(bean.getRsrAuid());
            sql.addDateValue(bean.getRsrAdate());
            sql.addIntValue(bean.getRsrEuid());
            sql.addDateValue(bean.getRsrEdate());
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
     * <p>Update RSV_SIS_RYRK Data Bindding JavaBean
     * @param bean RSV_SIS_RYRK Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisRyrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_RYRK");
            sql.addSql(" set ");
            sql.addSql("   RSR_KBN=?,");
            sql.addSql("   RSR_DWEEK1=?,");
            sql.addSql("   RSR_DWEEK2=?,");
            sql.addSql("   RSR_DWEEK3=?,");
            sql.addSql("   RSR_DWEEK4=?,");
            sql.addSql("   RSR_DWEEK5=?,");
            sql.addSql("   RSR_DWEEK6=?,");
            sql.addSql("   RSR_DWEEK7=?,");
            sql.addSql("   RSR_DAY=?,");
            sql.addSql("   RSR_WEEK=?,");
            sql.addSql("   RSR_MONTH_YEARLY=?,");
            sql.addSql("   RSR_DAY_YEARLY=?,");
            sql.addSql("   RSR_TIME_FR=?,");
            sql.addSql("   RSR_TIME_TO=?,");
            sql.addSql("   RSR_TRAN_KBN=?,");
            sql.addSql("   RSR_DATE_FR=?,");
            sql.addSql("   RSR_DATE_TO=?,");
            sql.addSql("   RSR_MOK=?,");
            sql.addSql("   RSR_BIKO=?,");
            sql.addSql("   RSR_EDIT=?,");
            sql.addSql("   RSR_PUBLIC=?,");
            sql.addSql("   RSR_AUID=?,");
            sql.addSql("   RSR_ADATE=?,");
            sql.addSql("   RSR_EUID=?,");
            sql.addSql("   RSR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsrKbn());
            sql.addIntValue(bean.getRsrDweek1());
            sql.addIntValue(bean.getRsrDweek2());
            sql.addIntValue(bean.getRsrDweek3());
            sql.addIntValue(bean.getRsrDweek4());
            sql.addIntValue(bean.getRsrDweek5());
            sql.addIntValue(bean.getRsrDweek6());
            sql.addIntValue(bean.getRsrDweek7());
            sql.addIntValue(bean.getRsrDay());
            sql.addIntValue(bean.getRsrWeek());
            sql.addIntValue(bean.getRsrMonthYearly());
            sql.addIntValue(bean.getRsrDayYearly());
            sql.addDateValue(bean.getRsrTimeFr());
            sql.addDateValue(bean.getRsrTimeTo());
            sql.addIntValue(bean.getRsrTranKbn());
            sql.addDateValue(bean.getRsrDateFr());
            sql.addDateValue(bean.getRsrDateTo());
            sql.addStrValue(bean.getRsrMok());
            sql.addStrValue(bean.getRsrBiko());
            sql.addIntValue(bean.getRsrEdit());
            sql.addIntValue(bean.getRsrPublic());
            sql.addIntValue(bean.getRsrAuid());
            sql.addDateValue(bean.getRsrAdate());
            sql.addIntValue(bean.getRsrEuid());
            sql.addDateValue(bean.getRsrEdate());
            //where
            sql.addIntValue(bean.getRsrRsid());

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
     * <p>Select RSV_SIS_RYRK All Data
     * @return List in RSV_SIS_RYRKModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisRyrkModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisRyrkModel> ret = new ArrayList<RsvSisRyrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSR_KBN,");
            sql.addSql("   RSR_DWEEK1,");
            sql.addSql("   RSR_DWEEK2,");
            sql.addSql("   RSR_DWEEK3,");
            sql.addSql("   RSR_DWEEK4,");
            sql.addSql("   RSR_DWEEK5,");
            sql.addSql("   RSR_DWEEK6,");
            sql.addSql("   RSR_DWEEK7,");
            sql.addSql("   RSR_DAY,");
            sql.addSql("   RSR_WEEK,");
            sql.addSql("   RSR_MONTH_YEARLY,");
            sql.addSql("   RSR_DAY_YEARLY,");
            sql.addSql("   RSR_TIME_FR,");
            sql.addSql("   RSR_TIME_TO,");
            sql.addSql("   RSR_TRAN_KBN,");
            sql.addSql("   RSR_DATE_FR,");
            sql.addSql("   RSR_DATE_TO,");
            sql.addSql("   RSR_MOK,");
            sql.addSql("   RSR_BIKO,");
            sql.addSql("   RSR_EDIT,");
            sql.addSql("   RSR_PUBLIC,");
            sql.addSql("   RSR_AUID,");
            sql.addSql("   RSR_ADATE,");
            sql.addSql("   RSR_EUID,");
            sql.addSql("   RSR_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_RYRK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisRyrkFromRs(rs));
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
     * <p>Select RSV_SIS_RYRK
     * @param bean RSV_SIS_RYRK Model
     * @return RSV_SIS_RYRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisRyrkModel select(RsvSisRyrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisRyrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSR_KBN,");
            sql.addSql("   RSR_DWEEK1,");
            sql.addSql("   RSR_DWEEK2,");
            sql.addSql("   RSR_DWEEK3,");
            sql.addSql("   RSR_DWEEK4,");
            sql.addSql("   RSR_DWEEK5,");
            sql.addSql("   RSR_DWEEK6,");
            sql.addSql("   RSR_DWEEK7,");
            sql.addSql("   RSR_DAY,");
            sql.addSql("   RSR_WEEK,");
            sql.addSql("   RSR_MONTH_YEARLY,");
            sql.addSql("   RSR_DAY_YEARLY,");
            sql.addSql("   RSR_TIME_FR,");
            sql.addSql("   RSR_TIME_TO,");
            sql.addSql("   RSR_TRAN_KBN,");
            sql.addSql("   RSR_DATE_FR,");
            sql.addSql("   RSR_DATE_TO,");
            sql.addSql("   RSR_MOK,");
            sql.addSql("   RSR_BIKO,");
            sql.addSql("   RSR_EDIT,");
            sql.addSql("   RSR_PUBLIC,");
            sql.addSql("   RSR_AUID,");
            sql.addSql("   RSR_ADATE,");
            sql.addSql("   RSR_EUID,");
            sql.addSql("   RSR_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsrRsid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisRyrkFromRs(rs);
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
     * <p>Select RSV_SIS_RYRK
     * @param rsrRsid SID
     * @return RSV_SIS_RYRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisRyrkModel select(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisRyrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSR_KBN,");
            sql.addSql("   RSR_DWEEK1,");
            sql.addSql("   RSR_DWEEK2,");
            sql.addSql("   RSR_DWEEK3,");
            sql.addSql("   RSR_DWEEK4,");
            sql.addSql("   RSR_DWEEK5,");
            sql.addSql("   RSR_DWEEK6,");
            sql.addSql("   RSR_DWEEK7,");
            sql.addSql("   RSR_DAY,");
            sql.addSql("   RSR_WEEK,");
            sql.addSql("   RSR_MONTH_YEARLY,");
            sql.addSql("   RSR_DAY_YEARLY,");
            sql.addSql("   RSR_TIME_FR,");
            sql.addSql("   RSR_TIME_TO,");
            sql.addSql("   RSR_TRAN_KBN,");
            sql.addSql("   RSR_DATE_FR,");
            sql.addSql("   RSR_DATE_TO,");
            sql.addSql("   RSR_MOK,");
            sql.addSql("   RSR_BIKO,");
            sql.addSql("   RSR_EDIT,");
            sql.addSql("   RSR_PUBLIC,");
            sql.addSql("   RSR_AUID,");
            sql.addSql("   RSR_ADATE,");
            sql.addSql("   RSR_EUID,");
            sql.addSql("   RSR_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisRyrkFromRs(rs);
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
     * <p>スケジュールSIDから施設予約拡張データを取得する
     * <p>拡張データが存在しない場合はnullを返します。
     * @param scdSid スケジュールSID
     * @return RSV_SIS_RYRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisRyrkModel selectFromScdSid(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisRyrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_RYRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_RYRK.RSR_KBN,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK1,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK2,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK3,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK4,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK5,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK6,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DWEEK7,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DAY,");
            sql.addSql("   RSV_SIS_RYRK.RSR_WEEK,");
            sql.addSql("   RSV_SIS_RYRK.RSR_MONTH_YEARLY,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DAY_YEARLY,");
            sql.addSql("   RSV_SIS_RYRK.RSR_TIME_FR,");
            sql.addSql("   RSV_SIS_RYRK.RSR_TIME_TO,");
            sql.addSql("   RSV_SIS_RYRK.RSR_TRAN_KBN,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DATE_FR,");
            sql.addSql("   RSV_SIS_RYRK.RSR_DATE_TO,");
            sql.addSql("   RSV_SIS_RYRK.RSR_MOK,");
            sql.addSql("   RSV_SIS_RYRK.RSR_BIKO,");
            sql.addSql("   RSV_SIS_RYRK.RSR_EDIT,");
            sql.addSql("   RSV_SIS_RYRK.RSR_PUBLIC,");
            sql.addSql("   RSV_SIS_RYRK.RSR_AUID,");
            sql.addSql("   RSV_SIS_RYRK.RSR_ADATE,");
            sql.addSql("   RSV_SIS_RYRK.RSR_EUID,");
            sql.addSql("   RSV_SIS_RYRK.RSR_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK,");
            sql.addSql("(");
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql(" (");
            sql.addSql(" select");
            sql.addSql("   SCH_DATA.SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
//            sql.addSql("   SCH_DATA.SCD_SID=?");
//            sql.addValue(scdSid);
            sql.addSql("   SCH_DATA.SCD_SID=" + scdSid);
            sql.addSql(" ) as RSSID");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID=RSSID.SCD_RSSID");
            sql.addSql(") as RSID");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_RYRK.RSR_RSID=RSID.RSR_RSID");
            sql.addSql("");

            pstmt = con.prepareStatement(sql.toSqlString());


            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisRyrkFromRs(rs);
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
     * <p>全レコード件数を取得する
     * @return レコード件数
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");

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
     * <br>[機  能] データサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return データサイズ集計
     * @throws SQLException SQL実行時例外
     */
    public long getTotalDataSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSR_MOK)) as SIZE1,");
            sql.addSql("   sum(octet_length(RSR_BIKO)) as SIZE2");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("SIZE1");
                dataSize += rs.getLong("SIZE2");
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
     * <p>Delete RSV_SIS_RYRK
     * @param bean RSV_SIS_RYRK Model
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvSisRyrkModel bean) throws SQLException {
        if (bean == null || bean.getRsrRsid() <= 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsrRsid());

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
     * <br>[機  能] 指定された拡張予約SIDのデータを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 拡張予約SID
     * @throws SQLException 例外
     */
    public void delete(int rsrRsid) throws SQLException {
        if (rsrRsid <= 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

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
     * <br>[機  能] 指定された拡張予約SIDのデータを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsidList 拡張予約SID配列
     * @throws SQLException 例外
     */
    public void delete(ArrayList<Integer> rsrRsidList) throws SQLException {
        if (rsrRsidList == null || rsrRsidList.size() <= 0) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_RYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID in (");

            for (int i = 0; i < rsrRsidList.size(); i++) {

                sql.addSql("?");
                sql.addIntValue(rsrRsidList.get(i));

                if (i != rsrRsidList.size() - 1) {
                    sql.addSql(", ");
                }
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
     * <p>Create RSV_SIS_RYRK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisRyrkModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisRyrkModel __getRsvSisRyrkFromRs(ResultSet rs) throws SQLException {
        RsvSisRyrkModel bean = new RsvSisRyrkModel();
        bean.setRsrRsid(rs.getInt("RSR_RSID"));
        bean.setRsrKbn(rs.getInt("RSR_KBN"));
        bean.setRsrDweek1(rs.getInt("RSR_DWEEK1"));
        bean.setRsrDweek2(rs.getInt("RSR_DWEEK2"));
        bean.setRsrDweek3(rs.getInt("RSR_DWEEK3"));
        bean.setRsrDweek4(rs.getInt("RSR_DWEEK4"));
        bean.setRsrDweek5(rs.getInt("RSR_DWEEK5"));
        bean.setRsrDweek6(rs.getInt("RSR_DWEEK6"));
        bean.setRsrDweek7(rs.getInt("RSR_DWEEK7"));
        bean.setRsrDay(rs.getInt("RSR_DAY"));
        bean.setRsrWeek(rs.getInt("RSR_WEEK"));
        bean.setRsrMonthYearly(rs.getInt("RSR_MONTH_YEARLY"));
        bean.setRsrDayYearly(rs.getInt("RSR_DAY_YEARLY"));
        bean.setRsrTimeFr(UDate.getInstanceTimestamp(rs.getTimestamp("RSR_TIME_FR")));
        bean.setRsrTimeTo(UDate.getInstanceTimestamp(rs.getTimestamp("RSR_TIME_TO")));
        bean.setRsrTranKbn(rs.getInt("RSR_TRAN_KBN"));
        bean.setRsrDateFr(UDate.getInstanceTimestamp(rs.getTimestamp("RSR_DATE_FR")));
        bean.setRsrDateTo(UDate.getInstanceTimestamp(rs.getTimestamp("RSR_DATE_TO")));
        bean.setRsrMok(rs.getString("RSR_MOK"));
        bean.setRsrBiko(rs.getString("RSR_BIKO"));
        bean.setRsrEdit(rs.getInt("RSR_EDIT"));
        bean.setRsrPublic(rs.getInt("RSR_PUBLIC"));
        bean.setRsrAuid(rs.getInt("RSR_AUID"));
        bean.setRsrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSR_ADATE")));
        bean.setRsrEuid(rs.getInt("RSR_EUID"));
        bean.setRsrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSR_EDATE")));
        return bean;
    }
}