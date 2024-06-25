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
import jp.groupsession.v2.ntp.model.NtpTemplateSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報テンプレート並び順に対する各種操作を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpTemplateSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpTemplateSortDao.class);


    /**
     * <p>Default Constructor
     */
    public NtpTemplateSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpTemplateSortDao(Connection con) {
        super(con);
    }


    /**
     * <p>Select NTP_GYOMU All Data
     * @return List in NTP_GYOMUModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpTemplateSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpTemplateSortModel> ret = new ArrayList<NtpTemplateSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTT_SID, ");
            sql.addSql("   NPS_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_TEMPLATE_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpTemplateSortFromRs(rs));
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
    private NtpTemplateSortModel __getNtpTemplateSortFromRs(ResultSet rs) throws SQLException {
        NtpTemplateSortModel bean = new NtpTemplateSortModel();
        bean.setNttSid(rs.getInt("NTT_SID"));
        bean.setNpsSort(rs.getInt("NPS_SORT"));
        return bean;
    }

    /**
     * <p>Delete NTP_TEMPLATE_SORT
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
            sql.addSql("   NTP_TEMPLATE_SORT");

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
     * <br>[機  能] ソートの最大値+1を返す。
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
            sql.addSql("   MAX(NTP_TEMPLATE_SORT.NPS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_TEMPLATE_SORT");

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
     * <br>[機  能] データを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpTemplateSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpTemplateSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_TEMPLATE_SORT(");
                sql.addSql("   NTT_SID,");
                sql.addSql("   NPS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNttSid());
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
     * <br>[機  能] ソート順を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param nttSid テンプレートSID
     * @param 
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int nttSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update ");
                sql.addSql("   NTP_TEMPLATE_SORT");
                sql.addSql(" set ");
                sql.addSql("   NPS_SORT=?");
                sql.addSql(" where ");
                sql.addSql("   NTT_SID=?");
                
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(sort);
                sql.addIntValue(nttSid);
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
