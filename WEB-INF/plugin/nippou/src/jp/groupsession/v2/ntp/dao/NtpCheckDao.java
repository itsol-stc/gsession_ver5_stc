package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpCheckModel;


/**
 * <p>NTP_CHECK Data Access Object
 */
public class NtpCheckDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpCheckDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpCheckDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpCheckDao(Connection con) {
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
            sql.addSql("drop table NTP_CHECK");

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
     * <p>Insert NTP_CHECK Data Bindding JavaBean
     * @param ntpSid 日報SID
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void insert(int ntpSid, int usrSid)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_CHECK(");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" select ");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" where ");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from NTP_CHECK");
            sql.addSql("     where ");
            sql.addSql("       NTP_SID=?");
            sql.addSql("     and ");
            sql.addSql("       USR_SID=?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);
            sql.addLongValue(usrSid);
            sql.addIntValue(ntpSid);
            sql.addLongValue(usrSid);
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
     * <p>Select NTP_CHECK All Data
     * @return List in NTP_CHECKModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpCheckModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpCheckModel> ret = new ArrayList<NtpCheckModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_CHECK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpCheckFromRs(rs));
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
     * <p>Select NTP_CHECK
     * @param  ntpSid 日報SID
     * @return NTP_CHECKModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpCheckModel> select(int ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpCheckModel ngMdl = null;
        ArrayList<NtpCheckModel> ret = new ArrayList<NtpCheckModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ngMdl = new NtpCheckModel();
                ngMdl = __getNtpCheckFromRs(rs);
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
     * <p>レコード件数を取得する
     * @param ntpSid NTP_SID
     * @param usrSid USR_SID
     * @return NTP_CHECKModel
     * @throws SQLException SQL実行例外
     */
    public int count(int ntpSid, int usrSid) throws SQLException {

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
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);
            sql.addIntValue(usrSid);

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
     * <p>指定した日報の確認数を取得する
     * @param ntpSid NTP_SID
     * @return NTP_CHECKModel
     * @throws SQLException SQL実行例外
     */
    public int count(int ntpSid) throws SQLException {

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
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);

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
     * <p>Delete NTP_CHECK
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
            sql.addSql("   NTP_CHECK");

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
     * <p>Delete NTP_CHECK
     * @param ntpSid 日報SID
     * @param usrSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int ntpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where");
            sql.addSql("   NTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);
            sql.addIntValue(usrSid);

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
     * <p>Delete NTP_CHECK
     * @param ntpSid 日報SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where");
            sql.addSql("   NTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);

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
     * <p>Delete NTP_CHECK
     * @param ntpSids 日報SID
     * @param usrSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(List<Integer> ntpSids, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where");
            sql.addSql("   NTP_SID in (");

            for (int i = 0; i < ntpSids.size(); i++) {
                if (i == ntpSids.size() - 1) {
                    sql.addSql(String.valueOf(ntpSids.get(i)));
                } else {
                    sql.addSql(String.valueOf(ntpSids.get(i)) + ",");
                }
            }

            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <p>Delete NTP_CHECK
     * @param ntpSids 日報SID
     * @param usrSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int resetData(List<Integer> ntpSids, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where");
            sql.addSql("   NTP_SID in (");

            for (int i = 0; i < ntpSids.size(); i++) {
                if (i == ntpSids.size() - 1) {
                    sql.addSql(String.valueOf(ntpSids.get(i)));
                } else {
                    sql.addSql(String.valueOf(ntpSids.get(i)) + ",");
                }
            }

            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in(");
            sql.addSql(String.valueOf(usrSid));
            sql.addSql(" )");
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
     * <p>Delete NTP_COMMENT
     * @param bdate NIP_DATE
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int deleteOldNippouCheck(UDate bdate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK NTG");
            sql.addSql(" where ");
            sql.addSql("  (NTG.NTP_SID) in");
            sql.addSql("   (");
            sql.addSql("    select NTD.NIP_SID from NTP_DATA NTD");
            sql.addSql("    where ");
            sql.addSql("    NTD.NIP_DATE <= ?");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bdate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create NTP_CHECK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpCheckModel
     * @throws SQLException SQL実行例外
     */
    private NtpCheckModel
              __getNtpCheckFromRs(ResultSet rs) throws SQLException {
        NtpCheckModel bean = new NtpCheckModel();
        bean.setNtpSid(rs.getInt("NTP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }

    /**
     * <br>[機  能] 日報確認テーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpCheckModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpCheckModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_CHECK(");
                sql.addSql("   NTP_SID,");
                sql.addSql("   USR_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNtpSid());
                sql.addIntValue(bean.getUsrSid());
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
