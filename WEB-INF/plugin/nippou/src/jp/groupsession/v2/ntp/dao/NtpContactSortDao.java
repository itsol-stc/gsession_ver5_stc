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
import jp.groupsession.v2.ntp.model.NtpContactSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報顧客源泉並び順に関する各種を操作を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpContactSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpContactSortDao.class);


    /**
     * <p>Default Constructor
     */
    public NtpContactSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpContactSortDao(Connection con) {
        super(con);
    }

    /**
     * <p>Select NTP_CONTACT All Data
     * @return List in NTP_CONTACTModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpContactSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpContactSortModel> ret = new ArrayList<NtpContactSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NCS_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_CONTACT_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpContactSortFromRs(rs));
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
     * <p>Create NTP_CONTACT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpContactModel
     * @throws SQLException SQL実行例外
     */
    private NtpContactSortModel __getNtpContactSortFromRs(ResultSet rs) throws SQLException {
        NtpContactSortModel bean = new NtpContactSortModel();
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNcsSort(rs.getInt("NCS_SORT"));
        return bean;
    }

    /**
     * <p>Insert NTP_CONTACT_SORT Data Bindding JavaBean
     * @param beanList NTP_CONTACT_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<NtpContactSortModel> beanList)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (NtpContactSortModel bean : beanList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_CONTACT_SORT(");
                sql.addSql("   NCN_SID,");
                sql.addSql("   NCS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNcnSid());
                sql.addLongValue(bean.getNcsSort());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Delete NTP_CONTACT_SORT
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
            sql.addSql("   NTP_CONTACT_SORT");

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
     * <br>[機  能] コンタクトのソートの最大値+1を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @return ソートの最大値
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int sort = 1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MAX(NTP_CONTACT_SORT.NCS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_CONTACT_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sort = rs.getInt("MAX_SORT") + 1;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sort;
    }

    /**
     * <br>[機  能] コンタクトソートテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpContactSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpContactSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_CONTACT_SORT(");
                sql.addSql("   NCN_SID,");
                sql.addSql("   NCS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNcnSid());
                sql.addIntValue(bean.getNcsSort());
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
     * <br>[機  能] 表示順序を変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ncnSid コンタクトSID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int ncnSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_CONTACT_SORT");
            sql.addSql(" set ");
            sql.addSql("   NCS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   NCN_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(ncnSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
