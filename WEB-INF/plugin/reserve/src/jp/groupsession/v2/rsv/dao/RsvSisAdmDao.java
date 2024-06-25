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
import jp.groupsession.v2.rsv.model.RsvSisAdmModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSV_SIS_ADM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisAdmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisAdmDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisAdmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisAdmDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_SIS_ADM Data Bindding JavaBean
     * @param bean RSV_SIS_ADM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisAdmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_ADM(");
            sql.addSql("   RSG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RSA_AUID,");
            sql.addSql("   RSA_ADATE,");
            sql.addSql("   RSA_EUID,");
            sql.addSql("   RSA_EDATE");
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
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getRsaAuid());
            sql.addDateValue(bean.getRsaAdate());
            sql.addIntValue(bean.getRsaEuid());
            sql.addDateValue(bean.getRsaEdate());
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
     * <p>Update RSV_SIS_ADM Data Bindding JavaBean
     * @param bean RSV_SIS_ADM Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisAdmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" set ");
            sql.addSql("   RSA_AUID=?,");
            sql.addSql("   RSA_ADATE=?,");
            sql.addSql("   RSA_EUID=?,");
            sql.addSql("   RSA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsaAuid());
            sql.addDateValue(bean.getRsaAdate());
            sql.addIntValue(bean.getRsaEuid());
            sql.addDateValue(bean.getRsaEdate());
            //where
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select RSV_SIS_ADM All Data
     * @return List in RSV_SIS_ADMModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisAdmModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisAdmModel> ret = new ArrayList<RsvSisAdmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RSA_AUID,");
            sql.addSql("   RSA_ADATE,");
            sql.addSql("   RSA_EUID,");
            sql.addSql("   RSA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_ADM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisAdmFromRs(rs));
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
     * <p>ユーザを指定し管理者権限を持っている施設管理者情報を取得
     * @param userSid ユーザSID
     * @return List in RSV_SIS_ADMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisAdmModel> select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisAdmModel> ret = new ArrayList<RsvSisAdmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RSA_AUID,");
            sql.addSql("   RSA_ADATE,");
            sql.addSql("   RSA_EUID,");
            sql.addSql("   RSA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisAdmFromRs(rs));
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
     * <p>Select RSV_SIS_ADM
     * @param bean RSV_SIS_ADM Model
     * @return RSV_SIS_ADMModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisAdmModel select(RsvSisAdmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisAdmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RSA_AUID,");
            sql.addSql("   RSA_ADATE,");
            sql.addSql("   RSA_EUID,");
            sql.addSql("   RSA_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisAdmFromRs(rs);
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
     * <p>Delete RSV_SIS_ADM
     * @param bean RSV_SIS_ADM Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvSisAdmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Delete RSV_SIS_ADM
     * @param rsgSid groupSid
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int rsgSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsgSid);

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
     * <p>Create RSV_SIS_ADM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisAdmModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisAdmModel __getRsvSisAdmFromRs(ResultSet rs) throws SQLException {
        RsvSisAdmModel bean = new RsvSisAdmModel();
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setRsaAuid(rs.getInt("RSA_AUID"));
        bean.setRsaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSA_ADATE")));
        bean.setRsaEuid(rs.getInt("RSA_EUID"));
        bean.setRsaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSA_EDATE")));
        return bean;
    }
}