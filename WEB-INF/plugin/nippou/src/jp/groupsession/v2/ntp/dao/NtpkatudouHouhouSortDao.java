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
import jp.groupsession.v2.ntp.model.NtpKatudouHouhouSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_KTHOUHOU_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpkatudouHouhouSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpkatudouHouhouSortDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpkatudouHouhouSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpkatudouHouhouSortDao(Connection con) {
        super(con);
    }

    /**
     * <p>Select NTP_GYOMU All Data
     * @return List in NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpKatudouHouhouSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpKatudouHouhouSortModel> ret
               = new ArrayList<NtpKatudouHouhouSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NKH_SID, ");
            sql.addSql("   NHS_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTHOUHOU_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getKatudouHouhouSortFromRs(rs));
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
    private NtpKatudouHouhouSortModel __getKatudouHouhouSortFromRs(
            ResultSet rs) throws SQLException {
        NtpKatudouHouhouSortModel bean = new NtpKatudouHouhouSortModel();
        bean.setNkhSid(rs.getInt("NKH_SID"));
        bean.setNhsSort(rs.getInt("NHS_SORT"));
        return bean;
    }

    /**
     * <p>Insert NTP_KTHOUHOU_SORT Data Bindding JavaBean
     * @param beanList NTP_KTHOUHOU_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<NtpKatudouHouhouSortModel> beanList)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (NtpKatudouHouhouSortModel bean : beanList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_KTHOUHOU_SORT(");
                sql.addSql("   NKH_SID,");
                sql.addSql("   NHS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNkhSid());
                sql.addLongValue(bean.getNhsSort());
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
     * <p>Delete NTP_KTHOUHOU_SORT
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
            sql.addSql("   NTP_KTHOUHOU_SORT");

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
     * <br>[機  能] 活動方法のソートの最大値+1を返す。
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
            sql.addSql("   MAX(NTP_KTHOUHOU_SORT.NHS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTHOUHOU_SORT");

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
     * <br>[機  能] 活動方法ソートテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpKatudouHouhouSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpKatudouHouhouSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_KTHOUHOU_SORT(");
                sql.addSql("   NKH_SID,");
                sql.addSql("   NHS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNkhSid());
                sql.addIntValue(bean.getNhsSort());
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
     * @param nkhSid 活動方法SID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int nkhSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_KTHOUHOU_SORT");
            sql.addSql(" set ");
            sql.addSql("   NHS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   NKH_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(nkhSid);

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
