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
import jp.groupsession.v2.ntp.model.NtpTargetModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_TARGET Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpTargetDao(Connection con) {
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
            sql.addSql("drop table NTP_TARGET");

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
     * <p>Insert NTP_TARGET Data Bindding JavaBean
     * @param bean NTP_TARGET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_TARGET(");
            sql.addSql("   NTG_SID,");
            sql.addSql("   NTG_NAME,");
            sql.addSql("   NTG_DETAIL,");
            sql.addSql("   NTG_UNIT,");
            sql.addSql("   NTG_DEF,");
            sql.addSql("   NTG_AUID,");
            sql.addSql("   NTG_ADATE,");
            sql.addSql("   NTG_EUID,");
            sql.addSql("   NTG_EDATE");
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
            sql.addIntValue(bean.getNtgSid());
            sql.addStrValue(bean.getNtgName());
            sql.addStrValue(bean.getNtgDetail());
            sql.addStrValue(bean.getNtgUnit());
            sql.addLongValue(bean.getNtgDef());
            sql.addIntValue(bean.getNtgAuid());
            sql.addDateValue(bean.getNtgAdate());
            sql.addIntValue(bean.getNtgEuid());
            sql.addDateValue(bean.getNtgEdate());
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
     * <p>Update NTP_TARGET Data Bindding JavaBean
     * @param bean NTP_TARGET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_TARGET");
            sql.addSql(" set ");
            sql.addSql("   NTG_NAME=? ,");
            sql.addSql("   NTG_DETAIL=? ,");
            sql.addSql("   NTG_UNIT=? ,");
            sql.addSql("   NTG_DEF=? ,");
            sql.addSql("   NTG_EUID=? ,");
            sql.addSql("   NTG_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNtgName());
            sql.addStrValue(bean.getNtgDetail());
            sql.addStrValue(bean.getNtgUnit());
            sql.addLongValue(bean.getNtgDef());
            sql.addIntValue(bean.getNtgEuid());
            sql.addDateValue(bean.getNtgEdate());
            //where
            sql.addIntValue(bean.getNtgSid());

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
     * <p>Select NTP_TARGET All Data
     * @return List in NTP_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpTargetModel> ret = new ArrayList<NtpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   NTP_TARGET.NTG_SID AS NTG_SID, ");
            sql.addSql("   NTP_TARGET.NTG_NAME AS NTG_NAME, ");
            sql.addSql("   NTP_TARGET.NTG_DETAIL AS NTG_DETAIL, ");
            sql.addSql("   NTP_TARGET.NTG_UNIT AS NTG_UNIT, ");
            sql.addSql("   NTP_TARGET.NTG_DEF AS NTG_DEF, ");
            sql.addSql("   NTP_TARGET.NTG_AUID AS NTG_AUID, ");
            sql.addSql("   NTP_TARGET.NTG_ADATE AS NTG_ADATE, ");
            sql.addSql("   NTP_TARGET.NTG_EUID AS NTG_EUID, ");
            sql.addSql("   NTP_TARGET.NTG_EDATE AS NTG_EDATE, ");
            sql.addSql("   TARGET_SORT.NTS_SORT AS NTS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_TARGET");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTG_SID, NTS_SORT");
            sql.addSql("      from NTP_TARGET_SORT ");
            sql.addSql("     ) TARGET_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TARGET.NTG_SID = TARGET_SORT.NTG_SID ");
            sql.addSql(" order by ");
            sql.addSql("   TARGET_SORT.NTS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpTargetFromRs(rs));
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
     * <p>Select NTP_TARGET All Data
     * @param ntgSids 目標SID
     * @return List in NTP_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpTargetModel> getNtpTargetList(List<Integer> ntgSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpTargetModel> ret = new ArrayList<NtpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   NTP_TARGET.NTG_SID AS NTG_SID, ");
            sql.addSql("   NTP_TARGET.NTG_NAME AS NTG_NAME, ");
            sql.addSql("   NTP_TARGET.NTG_DETAIL AS NTG_DETAIL, ");
            sql.addSql("   NTP_TARGET.NTG_UNIT AS NTG_UNIT, ");
            sql.addSql("   NTP_TARGET.NTG_DEF AS NTG_DEF, ");
            sql.addSql("   NTP_TARGET.NTG_AUID AS NTG_AUID, ");
            sql.addSql("   NTP_TARGET.NTG_ADATE AS NTG_ADATE, ");
            sql.addSql("   NTP_TARGET.NTG_EUID AS NTG_EUID, ");
            sql.addSql("   NTP_TARGET.NTG_EDATE AS NTG_EDATE, ");
            sql.addSql("   TARGET_SORT.NTS_SORT AS NTS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_TARGET");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTG_SID, NTS_SORT");
            sql.addSql("      from NTP_TARGET_SORT ");
            sql.addSql("     ) TARGET_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TARGET.NTG_SID = TARGET_SORT.NTG_SID ");

            if (!ntgSids.isEmpty()) {
                sql.addSql("   where");
                sql.addSql("   NTP_TARGET.NTG_SID in (");


                for (int i = 0; i < ntgSids.size(); i++) {
                    if (i > 0) {
                        sql.addSql("     , ");
                    }
                    sql.addSql(String.valueOf(ntgSids.get(i)));
                }

                sql.addSql("   )");
            }

            sql.addSql(" order by ");
            sql.addSql("   TARGET_SORT.NTS_SORT desc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpTargetFromRs(rs));
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
     * <p>Select NTP_TARGET
     * @param ngySid NGY_SID
     * @return NTP_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public NtpTargetModel select(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpTargetModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG_SID,");
            sql.addSql("   NTG_NAME,");
            sql.addSql("   NTG_DETAIL,");
            sql.addSql("   NTG_UNIT,");
            sql.addSql("   NTG_DEF,");
            sql.addSql("   NTG_AUID,");
            sql.addSql("   NTG_ADATE,");
            sql.addSql("   NTG_EUID,");
            sql.addSql("   NTG_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ngySid);

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
//            sql.addSql("   NTP_TARGET");
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
     * <p>Delete NTP_TARGET
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
            sql.addSql("   NTP_TARGET");
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
     * <p>Create NTP_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpGyomuModel
     * @throws SQLException SQL実行例外
     */
    private NtpTargetModel __getNtpTargetFromRs(ResultSet rs) throws SQLException {
        NtpTargetModel bean = new NtpTargetModel();
        bean.setNtgSid(rs.getInt("NTG_SID"));
        bean.setNtgName(rs.getString("NTG_NAME"));
        bean.setNtgDetail(rs.getString("NTG_DETAIL"));
        bean.setNtgUnit(rs.getString("NTG_UNIT"));
        bean.setNtgDef(rs.getLong("NTG_DEF"));
        bean.setNtgAuid(rs.getInt("NTG_AUID"));
        bean.setNtgAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTG_ADATE")));
        bean.setNtgEuid(rs.getInt("NTG_EUID"));
        bean.setNtgEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTG_EDATE")));
        return bean;
    }
}
