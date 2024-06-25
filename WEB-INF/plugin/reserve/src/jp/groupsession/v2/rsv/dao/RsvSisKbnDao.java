package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.rsv.model.RsvSisKbnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSV_SIS_KBN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisKbnDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisKbnDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisKbnDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisKbnDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_SIS_KBN Data Bindding JavaBean
     * @param bean RSV_SIS_KBN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisKbnModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_KBN(");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSK_NAME,");
            sql.addSql("   RSK_SORT,");
            sql.addSql("   RSK_AUID,");
            sql.addSql("   RSK_ADATE,");
            sql.addSql("   RSK_EUID,");
            sql.addSql("   RSK_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRskSid());
            sql.addStrValue(bean.getRskName());
            sql.addIntValue(bean.getRskSort());
            sql.addIntValue(bean.getRskAuid());
            sql.addDateValue(bean.getRskAdate());
            sql.addIntValue(bean.getRskEuid());
            sql.addDateValue(bean.getRskEdate());
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
     * <p>Update RSV_SIS_KBN Data Bindding JavaBean
     * @param bean RSV_SIS_KBN Data Bindding JavaBean
     * @return cnt update count
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisKbnModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_KBN");
            sql.addSql(" set ");
            sql.addSql("   RSK_NAME=?,");
            sql.addSql("   RSK_SORT=?,");
            sql.addSql("   RSK_AUID=?,");
            sql.addSql("   RSK_ADATE=?,");
            sql.addSql("   RSK_EUID=?,");
            sql.addSql("   RSK_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRskName());
            sql.addIntValue(bean.getRskSort());
            sql.addIntValue(bean.getRskAuid());
            sql.addDateValue(bean.getRskAdate());
            sql.addIntValue(bean.getRskEuid());
            sql.addDateValue(bean.getRskEdate());
            //where
            sql.addIntValue(bean.getRskSid());

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
     * <p>Select RSV_SIS_KBN All Data
     * @return List in RSV_SIS_KBNModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisKbnModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisKbnModel> ret = new ArrayList<RsvSisKbnModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSK_NAME,");
            sql.addSql("   RSK_SORT,");
            sql.addSql("   RSK_AUID,");
            sql.addSql("   RSK_ADATE,");
            sql.addSql("   RSK_EUID,");
            sql.addSql("   RSK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_KBN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisKbnFromRs(rs));
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
     * <br>[機  能] 施設区分を全件取得する
     * <br>[解  説]
     * <br>[備  考] ソート順に従う
     *
     * @param userSid ユーザSID
     * @return ret ReserveCommonModel
     * @throws SQLException SQL実行例外
     */
    /**
     * <p>Select RSV_SIS_KBN All Data
     * @return List in RSV_SIS_KBNModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisKbnModel> selectAllGrpKbn() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisKbnModel> ret = new ArrayList<RsvSisKbnModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSK_NAME,");
            sql.addSql("   RSK_SORT,");
            sql.addSql("   RSK_AUID,");
            sql.addSql("   RSK_ADATE,");
            sql.addSql("   RSK_EUID,");
            sql.addSql("   RSK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_KBN");
            sql.addSql(" order by");
            sql.addSql("   RSK_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisKbnFromRs(rs));
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
     * <p>Select RSV_SIS_KBN
     * @param bean RSV_SIS_KBN Model
     * @return RSV_SIS_KBNModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisKbnModel select(RsvSisKbnModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisKbnModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSK_NAME,");
            sql.addSql("   RSK_SORT,");
            sql.addSql("   RSK_AUID,");
            sql.addSql("   RSK_ADATE,");
            sql.addSql("   RSK_EUID,");
            sql.addSql("   RSK_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KBN");
            sql.addSql(" where ");
            sql.addSql("   RSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRskSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisKbnFromRs(rs);
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
     * <p>Delete RSV_SIS_KBN
     * @param bean RSV_SIS_KBN Model
     * @return cnt delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvSisKbnModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KBN");
            sql.addSql(" where ");
            sql.addSql("   RSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRskSid());

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
     * <p>Create RSV_SIS_KBN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisKbnModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisKbnModel __getRsvSisKbnFromRs(ResultSet rs) throws SQLException {
        RsvSisKbnModel bean = new RsvSisKbnModel();
        bean.setRskSid(rs.getInt("RSK_SID"));
        bean.setRskName(rs.getString("RSK_NAME"));
        bean.setRskSort(rs.getInt("RSK_SORT"));
        bean.setRskAuid(rs.getInt("RSK_AUID"));
        bean.setRskAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSK_ADATE")));
        bean.setRskEuid(rs.getInt("RSK_EUID"));
        bean.setRskEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSK_EDATE")));
        return bean;
    }
}