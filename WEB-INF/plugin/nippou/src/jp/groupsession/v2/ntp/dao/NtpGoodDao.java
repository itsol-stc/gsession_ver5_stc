package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpGoodModel;


/**
 * <p>NTP_GOOD Data Access Object
 */
public class NtpGoodDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpGoodDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpGoodDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpGoodDao(Connection con) {
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
            sql.addSql("drop table NTP_GOOD");

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
     * <p>Insert NTP_GOOD Data Bindding JavaBean
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
            sql.addSql(" NTP_GOOD(");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select NTP_GOOD All Data
     * @return List in NTP_GOODModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpGoodModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpGoodModel> ret = new ArrayList<NtpGoodModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_GOOD");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpGoodFromRs(rs));
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
     * <p>Select NTP_GOOD
     * @param  ntpSid 日報SID
     * @return NTP_GOODModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpGoodModel> select(int ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpGoodModel ngMdl = null;
        ArrayList<NtpGoodModel> ret = new ArrayList<NtpGoodModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_GOOD");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ngMdl = new NtpGoodModel();
                ngMdl = __getNtpGoodFromRs(rs);
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
     *
     * <br>[機  能] 日報に紐づくいいねMapを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSidSet 日報SID リスト
     * @return Map
     * @throws SQLException SQL実行時例外
     */
    public HashMap<Integer, List<NtpGoodModel>> getIineMap(
            Collection<Integer> nipSidSet) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpGoodModel ngMdl = null;
        HashMap<Integer, List<NtpGoodModel>> ret = new HashMap<Integer, List<NtpGoodModel>>();
        con = getCon();
        if (nipSidSet == null || nipSidSet.size() <= 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_GOOD");
            sql.addSql(" where ");
            int i = 0;
            sql.addSql("   (NTP_SID in (");
            for (Integer sid : nipSidSet) {
                if (sid == null) {
                    continue;
                }
                if (i > 0 && i % 1000 == 0) {
                    sql.addSql("  ) or NTP_SID in (");
                }
                if (i % 1000 != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql(String.valueOf(sid));
                i++;
            }
            sql.addSql("  ))");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ngMdl = new NtpGoodModel();
                ngMdl = __getNtpGoodFromRs(rs);
                int nipSid = ngMdl.getNtpSid();
                List<NtpGoodModel> list;
                if (ret.containsKey(nipSid)) {
                    list = ret.get(nipSid);
                } else {
                    list = new ArrayList<>();
                    ret.put(nipSid, list);
                }
                list.add(ngMdl);
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
     * @return NTP_GOODModel
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
            sql.addSql("   NTP_GOOD");
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
     * <p>指定した日報のいいね数を取得する
     * @param ntpSid NTP_SID
     * @return NTP_GOODModel
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
            sql.addSql("   NTP_GOOD");
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
     * <p>Delete NTP_GOOD
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
            sql.addSql("   NTP_GOOD");

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
     * <p>Delete NTP_GOOD
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
            sql.addSql("   NTP_GOOD");
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
     * <p>Delete NTP_GOOD
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
            sql.addSql("   NTP_GOOD");
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
     * <p>Delete NTP_COMMENT
     * @param bdate NIP_DATE
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int deleteOldNippouGood(UDate bdate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_GOOD NTG");
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
     * <p>Create NTP_GOOD Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpGoodModel
     * @throws SQLException SQL実行例外
     */
    private NtpGoodModel
              __getNtpGoodFromRs(ResultSet rs) throws SQLException {
        NtpGoodModel bean = new NtpGoodModel();
        bean.setNtpSid(rs.getInt("NTP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }

    /**
     * <br>[機  能] 日報いいねテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpGoodModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpGoodModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_GOOD(");
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
