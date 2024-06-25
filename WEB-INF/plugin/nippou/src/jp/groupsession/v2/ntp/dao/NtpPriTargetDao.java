package jp.groupsession.v2.ntp.dao;

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
import jp.groupsession.v2.ntp.model.NtpPriTargetModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_PRI_TARGET Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpPriTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpPriTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpPriTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpPriTargetDao(Connection con) {
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
            sql.addSql("drop table NTP_PRI_TARGET");

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
     * <p>Insert NTP_PRI_TARGET Data Bindding JavaBean
     * @param bean NTP_PRI_TARGET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpPriTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_PRI_TARGET(");
            sql.addSql("   NTG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPG_YEAR,");
            sql.addSql("   NPG_MONTH,");
            sql.addSql("   NPG_DATE,");
            sql.addSql("   NPG_TARGET,");
            sql.addSql("   NPG_RECORD,");
            sql.addSql("   NPG_AUID,");
            sql.addSql("   NPG_ADATE,");
            sql.addSql("   NPG_EUID,");
            sql.addSql("   NPG_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNtgSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNpgYear());
            sql.addIntValue(bean.getNpgMonth());
            sql.addDateValue(bean.getNpgDate());
            sql.addLongValue(bean.getNpgTarget());
            sql.addLongValue(bean.getNpgRecord());
            sql.addIntValue(bean.getNpgAuid());
            sql.addDateValue(bean.getNpgAdate());
            sql.addIntValue(bean.getNpgEuid());
            sql.addDateValue(bean.getNpgEdate());
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
     * <p>Update NTP_PRI_TARGET Data Bindding JavaBean
     * @param bean NTP_PRI_TARGET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpPriTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_TARGET");
            sql.addSql(" set ");
            sql.addSql("   NPG_TARGET=?,");
            sql.addSql("   NPG_RECORD=?,");
            sql.addSql("   NPG_EUID=? ,");
            sql.addSql("   NPG_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   NPG_YEAR=?");
            sql.addSql(" and ");
            sql.addSql("   NPG_MONTH=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getNpgTarget());
            sql.addLongValue(bean.getNpgRecord());
            sql.addIntValue(bean.getNpgEuid());
            sql.addDateValue(bean.getNpgEdate());
            //where
            sql.addIntValue(bean.getNtgSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNpgYear());
            sql.addIntValue(bean.getNpgMonth());

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
//     * <p>Select NTP_PRI_TARGET All Data
//     * @return List in NTP_PRI_TARGETModel
//     * @throws SQLException SQL実行例外
//     */
//    public List<NtpPriTargetModel> select() throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        ArrayList<NtpPriTargetModel> ret = new ArrayList<NtpPriTargetModel>();
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select ");
//            sql.addSql("   NGY_SID,");
//            sql.addSql("   NGY_CODE,");
//            sql.addSql("   NGY_NAME,");
//            sql.addSql("   NGY_AUID,");
//            sql.addSql("   NGY_ADATE,");
//            sql.addSql("   NGY_EUID,");
//            sql.addSql("   NGY_EDATE");
//            sql.addSql(" from ");
//            sql.addSql("   NTP_PRI_TARGET");
//            sql.addSql(" order by ");
//            sql.addSql("   NGY_CODE asc");
//
//            sql.addSql(" select ");
//            sql.addSql("   NTP_PRI_TARGET.NTG_SID AS NTG_SID, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_NAME AS NPG_NAME, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_DETAIL AS NPG_DETAIL, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_UNIT AS NPG_UNIT, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_DEF AS NPG_DEF, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_AUID AS NPG_AUID, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_ADATE AS NPG_ADATE, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_EUID AS NPG_EUID, ");
//            sql.addSql("   NTP_PRI_TARGET.NPG_EDATE AS NPG_EDATE, ");
//            sql.addSql("   TARGET_SORT.NTS_SORT AS NTS_SORT ");
//            sql.addSql(" from ");
//            sql.addSql("   NTP_PRI_TARGET");
//            sql.addSql("   left join");
//            sql.addSql("     (");
//            sql.addSql("      select NTG_SID, NTS_SORT");
//            sql.addSql("      from NTP_PRI_TARGET_SORT ");
//            sql.addSql("     ) TARGET_SORT");
//            sql.addSql("   on");
//            sql.addSql("     NTP_PRI_TARGET.NTG_SID = TARGET_SORT.NTG_SID ");
//            sql.addSql(" order by ");
//            sql.addSql("   TARGET_SORT.NTS_SORT asc ");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            log__.info(sql.toLogString());
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ret.add(__getNtpTargetFromRs(rs));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }


    /**
     * <p>Select NTP_PRI_TARGET
     * @param ntgSid NTG_SID
     * @param usrSid USR_SID
     * @param year NPG_YEAR
     * @param month NPG_MONTH
     * @return NTP_PRI_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public NtpPriTargetModel select(
            int ntgSid, int usrSid, int year, int month) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpPriTargetModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPG_YEAR,");
            sql.addSql("   NPG_MONTH,");
            sql.addSql("   NPG_DATE,");
            sql.addSql("   NPG_TARGET,");
            sql.addSql("   NPG_RECORD,");
            sql.addSql("   NPG_AUID,");
            sql.addSql("   NPG_ADATE,");
            sql.addSql("   NPG_EUID,");
            sql.addSql("   NPG_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   NPG_YEAR=?");
            sql.addSql(" and ");
            sql.addSql("   NPG_MONTH=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntgSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(year);
            sql.addIntValue(month);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpTargetFromRs(rs);
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
     * <p>Select NTP_PRI_TARGET
     * @return NTP_PRI_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpPriTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpPriTargetModel mdl = null;
        List<NtpPriTargetModel> ret = new ArrayList<NtpPriTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPG_YEAR,");
            sql.addSql("   NPG_MONTH,");
            sql.addSql("   NPG_DATE,");
            sql.addSql("   NPG_TARGET,");
            sql.addSql("   NPG_RECORD,");
            sql.addSql("   NPG_AUID,");
            sql.addSql("   NPG_ADATE,");
            sql.addSql("   NPG_EUID,");
            sql.addSql("   NPG_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_TARGET");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mdl = new NtpPriTargetModel();
                mdl = __getNtpTargetFromRs(rs);
                ret.add(mdl);
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
     * <p>一番最近のレコードを取得
     * @param ntgSid NTG_SID
     * @param usrSid USR_SID
     * @return NTP_PRI_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public NtpPriTargetModel getLatelyData(
            int ntgSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpPriTargetModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPG_YEAR,");
            sql.addSql("   NPG_MONTH,");
            sql.addSql("   NPG_DATE,");
            sql.addSql("   NPG_TARGET,");
            sql.addSql("   NPG_RECORD,");
            sql.addSql("   NPG_AUID,");
            sql.addSql("   NPG_ADATE,");
            sql.addSql("   NPG_EUID,");
            sql.addSql("   NPG_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" order by NPG_DATE DESC");
            //sql.addSql(" LIMIT 0, 1");
            sql.setPagingValue(0, 1);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntgSid);
            sql.addIntValue(usrSid);


            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpTargetFromRs(rs);
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
     * <p>Select NTP_PRI_TARGET
     * @param ntgSid NTG_SID
     * @param usrSid USR_SID
     * @param fromDate NPG_DATE
     * @param toDate NPG_DATE
     * @return NTP_PRI_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpPriTargetModel> select(
            int ntgSid, int usrSid, UDate fromDate, UDate toDate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpPriTargetModel> ret = new ArrayList<NtpPriTargetModel>();
        NtpPriTargetModel npgMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPG_YEAR,");
            sql.addSql("   NPG_MONTH,");
            sql.addSql("   NPG_DATE,");
            sql.addSql("   NPG_TARGET,");
            sql.addSql("   NPG_RECORD,");
            sql.addSql("   NPG_AUID,");
            sql.addSql("   NPG_ADATE,");
            sql.addSql("   NPG_EUID,");
            sql.addSql("   NPG_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("     (");
            sql.addSql("      NPG_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      NPG_DATE <= ?");
            sql.addSql("     )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntgSid);
            sql.addIntValue(usrSid);
            sql.addDateValue(fromDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                npgMdl = new NtpPriTargetModel();
                npgMdl = __getNtpTargetFromRs(rs);
                ret.add(npgMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
//
//    /**
//     * <br>[機  能] 指定した業種コードに該当する業種情報が存在するかをチェックする
//     * <br>[解  説] 指定した業種SID > 0 の場合、指定した業種SID以外を存在チェックの条件とする
//     * <br>[備  考]
//     * @param ngySid 業種SID
//     * @param ngyCode 業種コード
//     * @return 判定結果 true:存在する false:存在しない
//     * @throws SQLException SQL実行例外
//     */
//    public boolean existGyomu(int ngySid, String ngyCode) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        boolean result = false;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select");
//            sql.addSql("   count(NGY_SID) as CNT");
//            sql.addSql(" from");
//            sql.addSql("   NTP_PRI_TARGET");
//            sql.addSql(" where ");
//            sql.addSql("   NGY_CODE=?");
//            sql.addStrValue(ngyCode);
//
//            if (ngySid > 0) {
//                sql.addSql(" and ");
//                sql.addSql("   NGY_SID != ?");
//                sql.addIntValue(ngySid);
//            }
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//
//            log__.info(sql.toLogString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                result = rs.getInt("CNT") > 0;
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//
//        return result;
//    }
    /**
     * <p>Delete NTP_PRI_TARGET
     * @param ngySid NGY_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngySid);

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
     * <p>Create NTP_PRI_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpGyomuModel
     * @throws SQLException SQL実行例外
     */
    private NtpPriTargetModel __getNtpTargetFromRs(ResultSet rs) throws SQLException {
        NtpPriTargetModel bean = new NtpPriTargetModel();
        bean.setNtgSid(rs.getInt("NTG_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNpgYear(rs.getInt("NPG_YEAR"));
        bean.setNpgMonth(rs.getInt("NPG_MONTH"));
        bean.setNpgDate(UDate.getInstanceTimestamp(rs.getTimestamp("NPG_DATE")));
        bean.setNpgTarget(rs.getLong("NPG_TARGET"));
        bean.setNpgRecord(rs.getLong("NPG_RECORD"));
        bean.setNpgAuid(rs.getInt("NPG_AUID"));
        bean.setNpgAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPG_ADATE")));
        bean.setNpgEuid(rs.getInt("NPG_EUID"));
        bean.setNpgEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPG_EDATE")));
        return bean;
    }
}
