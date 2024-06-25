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
import jp.groupsession.v2.ntp.model.NtpProcessSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報プロセス並び順に対する各種操作を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpProcessSortDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpProcessSortDao.class);


    /**
     * <p>Default Constructor
     */
    public NtpProcessSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpProcessSortDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert NTP_PROCESS_SORT Data Bindding JavaBean
     * @param beanList NTP_PROCESS_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<NtpProcessSortModel> beanList)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (NtpProcessSortModel bean : beanList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_PROCESS_SORT(");
                sql.addSql("   NGP_SID,");
                sql.addSql("   NGY_SID,");
                sql.addSql("   NPS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNgpSid());
                sql.addIntValue(bean.getNgySid());
                sql.addLongValue(bean.getNpsSort());
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
     * <p>Delete NTP_PROCESS_SORT
     * @param ngySid 業務SID
     * @return delete count
     * @throws SQLException SQL実行例外
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
            sql.addSql("   NTP_PROCESS_SORT");
            sql.addSql(" where");
            sql.addSql("   NGY_SID = ?");
            sql.addIntValue(ngySid);

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
     * <br>[機  能] 業務内容毎のソートの最大値+1を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param ngySid 業務内容SID
     * @return ソートの最大値
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort(int ngySid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int sort = 1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MAX(NTP_PROCESS_SORT.NPS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_PROCESS_SORT");
            sql.addSql(" where ");
            sql.addSql("   NTP_PROCESS_SORT.NGY_SID = ?");
            sql.addIntValue(ngySid);

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
     * <p>Select NTP_GYOMU All Data
     * @return List in NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpProcessSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpProcessSortModel> ret = new ArrayList<NtpProcessSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NGP_SID, ");
            sql.addSql("   NGY_SID, ");
            sql.addSql("   NPS_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_PROCESS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpProcessSortFromRs(rs));
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
    private NtpProcessSortModel __getNtpProcessSortFromRs(
            ResultSet rs) throws SQLException {
        NtpProcessSortModel bean = new NtpProcessSortModel();
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNpsSort(rs.getInt("NPS_SORT"));
        return bean;
    }

    /**
     * <br>[機  能] 業務ソートテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpProcessSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpProcessSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_PROCESS_SORT(");
                sql.addSql("   NGP_SID,");
                sql.addSql("   NGY_SID,");
                sql.addSql("   NPS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNgpSid());
                sql.addIntValue(bean.getNgySid());
                sql.addIntValue(bean.getNpsSort());
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
     * @param ngpSid プロセスSID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int ngySid, int ngpSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PROCESS_SORT");
            sql.addSql(" set ");
            sql.addSql("   NPS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   NGY_SID=?");
            sql.addSql(" and ");
            sql.addSql("   NGP_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(ngySid);
            sql.addIntValue(ngpSid);

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