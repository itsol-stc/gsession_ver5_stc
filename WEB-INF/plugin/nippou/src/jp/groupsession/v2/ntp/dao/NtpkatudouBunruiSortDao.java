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
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpKatudouBunruiSortModel;


/**
 * <p>NTP_KTBUNRUI_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpkatudouBunruiSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpkatudouBunruiSortDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpkatudouBunruiSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpkatudouBunruiSortDao(Connection con) {
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
            sql.addSql("drop table NTP_KTBUNRUI_SORT");

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
     * <p>Insert NTP_KTBUNRUI_SORT Data Bindding JavaBean
     * @param beanList NTP_KTBUNRUI_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<NtpKatudouBunruiSortModel> beanList)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (NtpKatudouBunruiSortModel bean : beanList) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_KTBUNRUI_SORT(");
                sql.addSql("   NKB_SID,");
                sql.addSql("   NKS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNkbSid());
                sql.addLongValue(bean.getNksSort());
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
     * <p>Select NTP_KTBUNRUI_SORT All Data
     * @return List in NTP_KTBUNRUI_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpKatudouBunruiSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpKatudouBunruiSortModel> ret = new ArrayList<NtpKatudouBunruiSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NKB_SID,");
            sql.addSql("   NKS_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTBUNRUI_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpKatudouBunruiSortFromRs(rs));
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
     * <p>Select NTP_KTBUNRUI_SORT
     * @param wacSid NKB_SID
     * @param usrSid USR_SID
     * @return NTP_KTBUNRUI_SORTModel
     * @throws SQLException SQL実行例外
     */
    public NtpKatudouBunruiSortModel select(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpKatudouBunruiSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKB_SID,");
            sql.addSql("   NKS_SORT");
            sql.addSql(" from");
            sql.addSql("   NTP_KTBUNRUI_SORT");
            sql.addSql(" where ");
            sql.addSql("   NKB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpKatudouBunruiSortFromRs(rs);
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
     * @param wacSid NKB_SID
     * @param usrSid USR_SID
     * @return NTP_KTBUNRUI_SORTModel
     * @throws SQLException SQL実行例外
     */
    public int count(int wacSid, int usrSid) throws SQLException {

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
            sql.addSql("   NTP_KTBUNRUI_SORT");
            sql.addSql(" where ");
            sql.addSql("   NKB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <p>Delete NTP_KTBUNRUI_SORT
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
            sql.addSql("   NTP_KTBUNRUI_SORT");

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
     * <p>Create NTP_KTBUNRUI_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpKatudouBunruiSortModel
     * @throws SQLException SQL実行例外
     */
    private NtpKatudouBunruiSortModel
              __getNtpKatudouBunruiSortFromRs(ResultSet rs) throws SQLException {
        NtpKatudouBunruiSortModel bean = new NtpKatudouBunruiSortModel();
        bean.setNkbSid(rs.getInt("NKB_SID"));
        bean.setNksSort(rs.getInt("NKS_SORT"));
        return bean;
    }

    /**
     * <br>[機  能] 活動分類のソートの最大値+1を返す。
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
            sql.addSql("   MAX(NTP_KTBUNRUI_SORT.NKS_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTBUNRUI_SORT");

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
     * <br>[機  能] 活動分類ソートテーブルにデータを追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean NtpKatudouBunruiSortModel
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpKatudouBunruiSortModel bean)
                                                 throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" NTP_KTBUNRUI_SORT(");
                sql.addSql("   NKB_SID,");
                sql.addSql("   NKS_SORT");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getNkbSid());
                sql.addIntValue(bean.getNksSort());
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
     * @param nkbSid 活動分類SID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int nkbSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_KTBUNRUI_SORT");
            sql.addSql(" set ");
            sql.addSql("   NKS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   NKB_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(nkbSid);

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
