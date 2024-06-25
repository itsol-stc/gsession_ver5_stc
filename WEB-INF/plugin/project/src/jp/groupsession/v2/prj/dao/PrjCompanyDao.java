package jp.groupsession.v2.prj.dao;

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
import jp.groupsession.v2.prj.model.PrjCompanyModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_COMPANY Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PrjCompanyDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjCompanyDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjCompanyDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjCompanyDao(Connection con) {
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
            sql.addSql("drop table PRJ_COMPANY");

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
            sql.addSql(" create table PRJ_COMPANY (");
            sql.addSql("   PRJ_SID NUMBER(10,0) not null,");
            sql.addSql("   ACO_SID NUMBER(10,0) not null,");
            sql.addSql("   ABA_SID NUMBER(10,0) not null,");
            sql.addSql("   PRC_AUID NUMBER(10,0) not null,");
            sql.addSql("   PRC_ADATE varchar(23) not null,");
            sql.addSql("   PRC_EUID NUMBER(10,0) not null,");
            sql.addSql("   PRC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (PRJ_SID,ACO_SID,ABA_SID)");
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
     * <p>Insert PRJ_COMPANY Data Bindding JavaBean
     * @param bean PRJ_COMPANY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjCompanyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_COMPANY(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   PRC_AUID,");
            sql.addSql("   PRC_ADATE,");
            sql.addSql("   PRC_EUID,");
            sql.addSql("   PRC_EDATE");
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
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getPrcAuid());
            sql.addDateValue(bean.getPrcAdate());
            sql.addIntValue(bean.getPrcEuid());
            sql.addDateValue(bean.getPrcEdate());
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
     * <p>Update PRJ_COMPANY Data Bindding JavaBean
     * @param bean PRJ_COMPANY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjCompanyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_COMPANY");
            sql.addSql(" set ");
            sql.addSql("   PRC_AUID=?,");
            sql.addSql("   PRC_ADATE=?,");
            sql.addSql("   PRC_EUID=?,");
            sql.addSql("   PRC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrcAuid());
            sql.addDateValue(bean.getPrcAdate());
            sql.addIntValue(bean.getPrcEuid());
            sql.addDateValue(bean.getPrcEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());

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
     * <p>Select PRJ_COMPANY All Data
     * @return List in PRJ_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjCompanyModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjCompanyModel> ret = new ArrayList<PrjCompanyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   PRC_AUID,");
            sql.addSql("   PRC_ADATE,");
            sql.addSql("   PRC_EUID,");
            sql.addSql("   PRC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_COMPANY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjCompanyFromRs(rs));
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
     * <p>Select PRJ_COMPANY
     * @param prjSid PRJ_SID
     * @return PRJ_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjCompanyModel> getComSidList(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjCompanyModel> ret = new ArrayList<PrjCompanyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   PRC_AUID,");
            sql.addSql("   PRC_ADATE,");
            sql.addSql("   PRC_EUID,");
            sql.addSql("   PRC_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PrjCompanyModel bean = new PrjCompanyModel();
                bean.setPrjSid(rs.getInt("PRJ_SID"));
                bean.setAcoSid(rs.getInt("ACO_SID"));
                bean.setAbaSid(rs.getInt("ABA_SID"));
                bean.setPrcAuid(rs.getInt("PRC_AUID"));
                bean.setPrcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRC_ADATE")));
                bean.setPrcEuid(rs.getInt("PRC_EUID"));
                bean.setPrcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRC_EDATE")));
                ret.add(bean);
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
     * <p>Select PRJ_COMPANY
     * @param prjSid PRJ_SID
     * @param acoSid ACO_SID
     * @param abaSid ABA_SID
     * @return PRJ_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public PrjCompanyModel select(int prjSid, int acoSid, int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjCompanyModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   PRC_AUID,");
            sql.addSql("   PRC_ADATE,");
            sql.addSql("   PRC_EUID,");
            sql.addSql("   PRC_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(acoSid);
            sql.addIntValue(abaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjCompanyFromRs(rs);
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
     * <p>会社情報の件数を取得する
     * @param prjSid PRJ_SID
     * @param acoSid ACO_SID
     * @param abaSid ABA_SID
     * @return PRJ_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public int getprjComCount(int prjSid, int acoSid, int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count (PRJ_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(acoSid);
            sql.addIntValue(abaSid);

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
     * <p>Delete PRJ_COMPANY
     * @param prjSid PRJ_SID
     * @param acoSid ACO_SID
     * @param abaSid ABA_SID
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int delete(int prjSid, int acoSid, int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(acoSid);
            sql.addIntValue(abaSid);

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
     * <p>Delete PRJ_COMPANY
     * @param prjSid プロジェクトSID
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int deletePrjCom(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

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
     * <p>Create PRJ_COMPANY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjCompanyModel
     * @throws SQLException SQL実行例外
     */
    private PrjCompanyModel __getPrjCompanyFromRs(ResultSet rs) throws SQLException {
        PrjCompanyModel bean = new PrjCompanyModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setPrcAuid(rs.getInt("PRC_AUID"));
        bean.setPrcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRC_ADATE")));
        bean.setPrcEuid(rs.getInt("PRC_EUID"));
        bean.setPrcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRC_EDATE")));
        return bean;
    }
}
