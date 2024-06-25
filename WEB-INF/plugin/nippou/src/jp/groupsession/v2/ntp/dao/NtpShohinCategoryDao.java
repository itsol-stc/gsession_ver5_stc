package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_SHOHIN_CATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpShohinCategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpShohinCategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpShohinCategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpShohinCategoryDao(Connection con) {
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
            sql.addSql("drop table NTP_SHOHIN_CATEGORY");

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
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table NTP_SHOHIN_CATEGORY (");
            sql.addSql("   NSC_SID   integer     not null,");
            sql.addSql("   NSC_NAME  varchar(20) not null,");
            sql.addSql("   NSC_BIKO  varchar(300),");
            sql.addSql("   NSC_SORT  integer     not null,");
            sql.addSql("   NSC_AUID  integer     not null,");
            sql.addSql("   NSC_ADATE timestamp   not null,");
            sql.addSql("   NSC_EUID  integer     not null,");
            sql.addSql("   NSC_EDATE timestamp   not null,");
            sql.addSql("   primary key (ALB_SID)");
            sql.addSql(" )");

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
     * <p>Insert NTP_SHOHIN_CATEGORY Data Bindding JavaBean
     * @param bean NTP_SHOHIN_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpShohinCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SHOHIN_CATEGORY(");
            sql.addSql("   NSC_SID,");
            sql.addSql("   NSC_NAME,");
            sql.addSql("   NSC_BIKO,");
            sql.addSql("   NSC_SORT,");
            sql.addSql("   NSC_AUID,");
            sql.addSql("   NSC_ADATE,");
            sql.addSql("   NSC_EUID,");
            sql.addSql("   NSC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNscSid());
            sql.addStrValue(bean.getNscName());
            sql.addStrValue(bean.getNscBiko());
            sql.addIntValue(bean.getNscSort());
            sql.addIntValue(bean.getNscAuid());
            sql.addDateValue(bean.getNscAdate());
            sql.addIntValue(bean.getNscEuid());
            sql.addDateValue(bean.getNscEdate());
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
     * <p>Update NTP_SHOHIN_CATEGORY Data Bindding JavaBean
     * @param bean NTP_SHOHIN_CATEGORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpShohinCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   NSC_NAME=?,");
            sql.addSql("   NSC_BIKO=?,");
            sql.addSql("   NSC_EUID=?,");
            sql.addSql("   NSC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNscName());
            sql.addStrValue(bean.getNscBiko());
            sql.addIntValue(bean.getNscEuid());
            sql.addDateValue(bean.getNscEdate());
            //where
            sql.addIntValue(bean.getNscSid());

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
     * <br>[機  能] 表示順序を変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param nscSid 商品SID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int nscSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   NSC_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(nscSid);

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

    /**
     * <br>[機  能] ソート順のアップデート
     * <br>[解  説]
     * <br>[備  考]
     * @param sid カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void sortUpdate(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" set");
            sql.addSql("   NSC_SORT=");
            sql.addSql("     (select NSC_SORT ");
            sql.addSql("      from");
            sql.addSql("      NTP_SHOHIN_CATEGORY ");
            sql.addSql("     where ");
            sql.addSql("     NSC_SID=?)-1");
            sql.addSql(" where");
            sql.addSql("   NSC_SID=?");
            sql.addIntValue(sid);
            sql.addIntValue(sid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select NTP_SHOHIN_CATEGORY All Data
     * @return List in NTP_SHOHIN_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpShohinCategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpShohinCategoryModel> ret = new ArrayList<NtpShohinCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSC_SID,");
            sql.addSql("   NSC_NAME,");
            sql.addSql("   NSC_BIKO,");
            sql.addSql("   NSC_SORT,");
            sql.addSql("   NSC_AUID,");
            sql.addSql("   NSC_ADATE,");
            sql.addSql("   NSC_EUID,");
            sql.addSql("   NSC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" order by ");
            sql.addSql("   NSC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpShohinFromRs(rs));
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
     * <p>指定されたカテゴリSID内の商品リストを作成します。
     * @param albSid カテゴリSID
     * @return List in NTP_SHOHIN_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpShohinCategoryModel> getLabelLInCategory(int albSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpShohinCategoryModel> ret = new ArrayList<NtpShohinCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSC_SID,");
            sql.addSql("   NSC_NAME,");
            sql.addSql("   NSC_BIKO,");
            sql.addSql("   NSC_SORT,");
            sql.addSql("   NSC_AUID,");
            sql.addSql("   NSC_ADATE,");
            sql.addSql("   NSC_EUID,");
            sql.addSql("   NSC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID");
            sql.addSql(" in (");
            sql.addSql("     select ");
            sql.addSql("       ALB_SID");
            sql.addSql("     from ");
            sql.addSql("       NTP_SHOHIN");
            sql.addSql("     where ");
            sql.addSql("       NSC_SID=?");
            sql.addSql(" )");
            sql.addSql(" order by ");
            sql.addSql("   ALB_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(albSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getNtpShohinFromRs(rs));
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
     * <p>Select NTP_SHOHIN_CATEGORY
     * @param nscSid NSC_SID
     * @return NTP_SHOHIN_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public NtpShohinCategoryModel select(int nscSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpShohinCategoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NSC_SID,");
            sql.addSql("   NSC_NAME,");
            sql.addSql("   NSC_BIKO,");
            sql.addSql("   NSC_SORT,");
            sql.addSql("   NSC_AUID,");
            sql.addSql("   NSC_ADATE,");
            sql.addSql("   NSC_EUID,");
            sql.addSql("   NSC_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nscSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpShohinFromRs(rs);
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
     * <br>[機  能] ソート順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return int ソート順の最大値
     * @throws SQLException SQL実行例外
     */
    public int getSortMax() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(NSC_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN_CATEGORY");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("MAX");
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
     * <p>Delete NTP_SHOHIN_CATEGORY
     * @param alcSid ALB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int alcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> list = new ArrayList<Integer>();


        try {
            list = sortNumArrange(alcSid);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(alcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

            for (int i = 0; i < list.size(); i++) {
                sortUpdate(list.get(i));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 削除カテゴリソート順以下のカテゴリSID取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sid カテゴリSID
     * @return カテゴリSIDのList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> sortNumArrange(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            NtpShohinCategoryModel model = select(sid);
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NSC_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN_CATEGORY");
            sql.addSql(" where");
            sql.addSql("   NSC_SORT > ?");
            sql.addIntValue(model.getNscSort());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("NSC_SID"));
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
     * <p>Create NTP_SHOHIN_CATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpShohinCategoryModel
     * @throws SQLException SQL実行例外
     */
    private NtpShohinCategoryModel __getNtpShohinFromRs(ResultSet rs) throws SQLException {
        NtpShohinCategoryModel bean = new NtpShohinCategoryModel();
        bean.setNscSid(rs.getInt("NSC_SID"));
        bean.setNscName(rs.getString("NSC_NAME"));
        bean.setNscBiko(rs.getString("NSC_BIKO"));
        bean.setNscSort(rs.getInt("NSC_SORT"));
        bean.setNscAuid(rs.getInt("NSC_AUID"));
        bean.setNscAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NSC_ADATE")));
        bean.setNscEuid(rs.getInt("NSC_EUID"));
        bean.setNscEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NSC_EDATE")));
        return bean;
    }
}
