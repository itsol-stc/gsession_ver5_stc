package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpTmpTargetModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>NTP_TEMPLATE_TARGET Data Access Object
 */
public class NtpTmpTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpTmpTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpTmpTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpTmpTargetDao(Connection con) {
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
            sql.addSql("drop table NTP_TEMPLATE_TARGET");

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
     * <p>Insert NTP_TEMPLATE_TARGET Data Bindding JavaBean
     * @param nttSid 日報SID
     * @param ntgSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void insert(int nttSid, int ntgSid)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_TEMPLATE_TARGET(");
            sql.addSql("   NTT_SID,");
            sql.addSql("   NTG_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);
            sql.addIntValue(ntgSid);
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
     * <p>Select NTP_TEMPLATE_TARGET All Data
     * @return List in NTP_TEMPLATE_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpTmpTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpTmpTargetModel> ret = new ArrayList<NtpTmpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTT_SID,");
            sql.addSql("   NTG_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_TEMPLATE_TARGET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpTmpTargetFromRs(rs));
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
     * <p>Select NTP_TEMPLATE_TARGET
     * @param  nttSid 日報SID
     * @return NTP_TEMPLATE_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpTmpTargetModel> select(int nttSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpTmpTargetModel ngMdl = null;
        ArrayList<NtpTmpTargetModel> ret = new ArrayList<NtpTmpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_TEMPLATE_TARGET.NTT_SID,");
            sql.addSql("   NTP_TEMPLATE_TARGET.NTG_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");

            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTG_SID, NTS_SORT");
            sql.addSql("      from NTP_TARGET_SORT");
            sql.addSql("     ) TARGET_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TEMPLATE_TARGET.NTG_SID = TARGET_SORT.NTG_SID ");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   TARGET_SORT.NTS_SORT desc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ngMdl = new NtpTmpTargetModel();
                ngMdl = __getNtpTmpTargetFromRs(rs);
                ret.add(ngMdl);
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
     * <p>Select NTP_TEMPLATE_TARGET
     * @param  ntgSid 目標SID
     * @return NTP_TEMPLATE_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpTmpTargetModel> selectForTarget(int ntgSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpTmpTargetModel ngMdl = null;
        ArrayList<NtpTmpTargetModel> ret = new ArrayList<NtpTmpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_TEMPLATE_TARGET.NTT_SID,");
            sql.addSql("   NTP_TEMPLATE_TARGET.NTG_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTP_TEMPLATE_SORT.NTT_SID,");
            sql.addSql("             NTP_TEMPLATE_SORT.NPS_SORT");
            sql.addSql("      from NTP_TEMPLATE_SORT");
            sql.addSql("     ) TEMPLATE_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TEMPLATE_TARGET.NTT_SID = TEMPLATE_SORT.NTT_SID ");
            sql.addSql(" where ");
            sql.addSql("   NTG_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   TEMPLATE_SORT.NPS_SORT desc ");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntgSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ngMdl = new NtpTmpTargetModel();
                ngMdl = __getNtpTmpTargetFromRs(rs);
                ret.add(ngMdl);
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
     * <p>Select NTP_TEMPLATE_TARGET
     * @param  nttSid 日報SID
     * @return NTP_TEMPLATE_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getNtgSids(int nttSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");
            sql.addSql("   order by NTG_SID ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("NTG_SID"));
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
     * <p>レコード件数を取得する
     * @param nttSid NTT_SID
     * @param ntgSid NTG_SID
     * @return NTP_TEMPLATE_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public int count(int nttSid, int ntgSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");
            sql.addSql(" and ");
            sql.addSql("   NTG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);
            sql.addIntValue(ntgSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>指定したテンプレートの目標数を取得する
     * @param nttSid NTT_SID
     * @return NTP_TEMPLATE_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public int count(int nttSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NTT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete NTP_TEMPLATE_TARGET
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");

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
     * <p>Delete NTP_TEMPLATE_TARGET
     * @param nttSid 日報SID
     * @param ntgSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nttSid, int ntgSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql(" where");
            sql.addSql("   NTT_SID=?");
            sql.addSql(" and");
            sql.addSql("   NTG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);
            sql.addIntValue(ntgSid);

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
     * <p>Delete NTP_TEMPLATE_TARGET
     * @param nttSid 日報SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nttSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql(" where");
            sql.addSql("   NTT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nttSid);

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
     * <p>Delete NTP_TEMPLATE_TARGET
     * @param ntgSid テンプレートSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteTarget(int ntgSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_TEMPLATE_TARGET");
            sql.addSql(" where");
            sql.addSql("   NTG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntgSid);

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

//
//    /**
//     * <p>Delete NTP_COMMENT
//     * @param bdate NIP_DATE
//     * @throws SQLException SQL実行例外
//     * @return count 削除件数
//     */
//    public int deleteOldNippouGood(UDate bdate) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        int count = 0;
//        Connection con = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" delete");
//            sql.addSql(" from");
//            sql.addSql("   NTP_TEMPLATE_TARGET NTG");
//            sql.addSql(" where ");
//            sql.addSql("  (NTG.NTT_SID) in");
//            sql.addSql("   (");
//            sql.addSql("    select NTD.NIP_SID from NTP_DATA NTD");
//            sql.addSql("    where ");
//            sql.addSql("    NTD.NIP_DATE <= ?");
//            sql.addSql("   ) ");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.addDateValue(bdate);
//
//            log__.info(sql.toLogString());
//            sql.setParameter(pstmt);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return count;
//    }

    /**
     * <p>Create NTP_TEMPLATE_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpTmpTargetModel
     * @throws SQLException SQL実行例外
     */
    private NtpTmpTargetModel
              __getNtpTmpTargetFromRs(ResultSet rs) throws SQLException {
        NtpTmpTargetModel bean = new NtpTmpTargetModel();
        bean.setNttSid(rs.getInt("NTT_SID"));
        bean.setNtgSid(rs.getInt("NTG_SID"));
        return bean;
    }

    /**
     * <br>[機  能] 日報いいねテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpTmpTargetModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpTmpTargetModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_TEMPLATE_TARGET(");
                sql.addSql("   NTT_SID,");
                sql.addSql("   NTG_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNttSid());
                sql.addIntValue(bean.getNtgSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
