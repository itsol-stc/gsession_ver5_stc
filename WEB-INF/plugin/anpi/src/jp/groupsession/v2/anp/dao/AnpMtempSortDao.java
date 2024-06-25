package jp.groupsession.v2.anp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.model.AnpMtempSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 安否テンプレート並び順に対する各種操作を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpMtempSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpMtempSortDao.class);


    /**
     * <p>Default Constructor
     */
    public AnpMtempSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpMtempSortDao(Connection con) {
        super(con);
    }

    /**
     * <p>Select ANP_MTEMP_SORT All Data
     * @return List in ANP_MTEMP_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpMtempSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpMtempSortModel> ret = new ArrayList<AnpMtempSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APM_SID,");
            sql.addSql("   AMS_SORT");
            sql.addSql(" from ");
            sql.addSql("   ANP_MTEMP_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpMtempSortFromRs(rs));
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
     * <p>Insert ANP_MTEMP_SORT Data Bindding JavaBean
     * @param beanList ANP_MTEMP_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<AnpMtempSortModel> beanList)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (AnpMtempSortModel bean : beanList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" ANP_MTEMP_SORT(");
                sql.addSql("   APM_SID,");
                sql.addSql("   AMS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getApmSid());
                sql.addIntValue(bean.getAmsSort());
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
     * <br>[機  能] ソート順を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ソート順モデル
     * @param sort 変更用ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(AnpMtempSortModel mdl, int sort) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   ANP_MTEMP_SORT");
            sql.addSql(" set ");
            sql.addSql("   AMS_SORT = ?");
            sql.addSql(" where ");
            sql.addSql("   APM_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sort);
            sql.addIntValue(mdl.getApmSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Delete ANP_MTEMP_SORT
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
            sql.addSql("   ANP_MTEMP_SORT");

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
            sql.addSql("   MAX(ANP_MTEMP_SORT.AMS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   ANP_MTEMP_SORT");

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
     * @param bean AnpMtempSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpMtempSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" ANP_MTEMP_SORT(");
                sql.addSql("   APM_SID,");
                sql.addSql("   AMS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getApmSid());
                sql.addIntValue(bean.getAmsSort());
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
     * <p>Create ANP_MTEMP_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpMtempSortModel
     * @throws SQLException SQL実行例外
     */
    private AnpMtempSortModel __getAnpMtempSortFromRs(ResultSet rs) throws SQLException {
        AnpMtempSortModel bean = new AnpMtempSortModel();
        bean.setApmSid(rs.getInt("APM_SID"));
        bean.setAmsSort(rs.getInt("AMS_SORT"));
        return bean;
    }
}
