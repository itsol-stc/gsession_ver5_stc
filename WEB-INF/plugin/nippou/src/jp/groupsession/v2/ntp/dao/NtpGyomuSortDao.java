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
import jp.groupsession.v2.ntp.model.NtpGyomuSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報業務並び順に関する各種操作を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpGyomuSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpGyomuSortDao.class);


    /**
     * <p>Default Constructor
     */
    public NtpGyomuSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpGyomuSortDao(Connection con) {
        super(con);
    }


    /**
     * <p>Select NTP_GYOMU All Data
     * @return List in NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpGyomuSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpGyomuSortModel> ret = new ArrayList<NtpGyomuSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NGY_SID, ");
            sql.addSql("   NGS_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_GYOMU_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpGyomuSortFromRs(rs));
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
    private NtpGyomuSortModel __getNtpGyomuSortFromRs(ResultSet rs) throws SQLException {
        NtpGyomuSortModel bean = new NtpGyomuSortModel();
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNgsSort(rs.getInt("NGS_SORT"));
        return bean;
    }

    /**
     * <p>Insert NTP_GYOMU_SORT Data Bindding JavaBean
     * @param beanList NTP_GYOMU_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<NtpGyomuSortModel> beanList)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (NtpGyomuSortModel bean : beanList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_GYOMU_SORT(");
                sql.addSql("   NGY_SID,");
                sql.addSql("   NGS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNgySid());
                sql.addLongValue(bean.getNgsSort());
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
     * <p>Delete NTP_GYOMU_SORT
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
            sql.addSql("   NTP_GYOMU_SORT");

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
     * <br>[機  能] 業務内容のソートの最大値+1を返す。
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
            sql.addSql("   MAX(NTP_GYOMU_SORT.NGS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_GYOMU_SORT");

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
     * <br>[機  能] 業務ソートテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpGyomuSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpGyomuSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_GYOMU_SORT(");
                sql.addSql("   NGY_SID,");
                sql.addSql("   NGS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNgySid());
                sql.addIntValue(bean.getNgsSort());
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
     * @param ngySid 業務SID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int ngySid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_GYOMU_SORT");
            sql.addSql(" set ");
            sql.addSql("   NGS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   NGY_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(ngySid);

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
