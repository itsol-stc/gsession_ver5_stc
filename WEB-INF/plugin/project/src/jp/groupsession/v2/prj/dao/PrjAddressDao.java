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
import jp.groupsession.v2.prj.model.PrjAddressModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_ADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PrjAddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjAddressDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjAddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjAddressDao(Connection con) {
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
            sql.addSql("drop table PRJ_ADDRESS");

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
            sql.addSql(" create table PRJ_ADDRESS (");
            sql.addSql("   PRJ_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   PRA_AUID NUMBER(10,0) not null,");
            sql.addSql("   PRA_ADATE varchar(23) not null,");
            sql.addSql("   PRA_EUID NUMBER(10,0) not null,");
            sql.addSql("   PRA_EDATE varchar(23) not null,");
            sql.addSql("   PRA_SORT NUMBER(10,0) not null,");
            sql.addSql("   primary key (PRJ_SID,ADR_SID)");
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
     * <p>Insert PRJ_ADDRESS Data Bindding JavaBean
     * @param bean PRJ_ADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_ADDRESS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   PRA_AUID,");
            sql.addSql("   PRA_ADATE,");
            sql.addSql("   PRA_EUID,");
            sql.addSql("   PRA_EDATE,");
            sql.addSql("   PRA_SORT");
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
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getPraAuid());
            sql.addDateValue(bean.getPraAdate());
            sql.addIntValue(bean.getPraEuid());
            sql.addDateValue(bean.getPraEdate());
            sql.addIntValue(bean.getPraSort());
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
     * <p>Update PRJ_ADDRESS Data Bindding JavaBean
     * @param bean PRJ_ADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_ADDRESS");
            sql.addSql(" set ");
            sql.addSql("   PRA_AUID=?,");
            sql.addSql("   PRA_ADATE=?,");
            sql.addSql("   PRA_EUID=?,");
            sql.addSql("   PRA_EDATE=?,");
            sql.addSql("   PRA_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPraAuid());
            sql.addDateValue(bean.getPraAdate());
            sql.addIntValue(bean.getPraEuid());
            sql.addDateValue(bean.getPraEdate());
            sql.addIntValue(bean.getPraSort());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getAdrSid());

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
     * <p>Select PRJ_ADDRESS All Data
     * @return List in PRJ_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjAddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjAddressModel> ret = new ArrayList<PrjAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   PRA_AUID,");
            sql.addSql("   PRA_ADATE,");
            sql.addSql("   PRA_EUID,");
            sql.addSql("   PRA_EDATE,");
            sql.addSql("   PRA_SORT");
            sql.addSql(" from ");
            sql.addSql("   PRJ_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjAddressFromRs(rs));
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
     * <p>Select PRJ_ADDRESS
     * @param prjSid PRJ_SID
     * @param adrSid ADR_SID
     * @return PRJ_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public PrjAddressModel select(int prjSid, int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjAddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   PRA_AUID,");
            sql.addSql("   PRA_ADATE,");
            sql.addSql("   PRA_EUID,");
            sql.addSql("   PRA_EDATE,");
            sql.addSql("   PRA_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjAddressFromRs(rs);
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
     * <p>Select PRJ_ADDRESS
     * @param prjSid PRJ_SID
     * @return PRJ_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjAddressModel> getAddSidList(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjAddressModel> ret = new ArrayList<PrjAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   PRA_AUID,");
            sql.addSql("   PRA_ADATE,");
            sql.addSql("   PRA_EUID,");
            sql.addSql("   PRA_EDATE,");
            sql.addSql("   PRA_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PrjAddressModel bean = new PrjAddressModel();
                bean.setPrjSid(rs.getInt("PRJ_SID"));
                bean.setAdrSid(rs.getInt("ADR_SID"));
                bean.setPraAuid(rs.getInt("PRA_AUID"));
                bean.setPraAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRA_ADATE")));
                bean.setPraEuid(rs.getInt("PRA_EUID"));
                bean.setPraEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRA_EDATE")));
                bean.setPraSort(rs.getInt("PRA_SORT"));
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
     * <p>Delete PRJ_ADDRESS
     * @param prjSid PRJ_SID
     * @param adrSid ADR_SID
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int delete(int prjSid, int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(adrSid);

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
     * <p>Delete PRJ_ADDRESS
     * @param prjSid プロジェクトSID
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int deletePrjAdd(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_ADDRESS");
            sql.addSql(" where");
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
     * <p>Create PRJ_ADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjAddressModel
     * @throws SQLException SQL実行例外
     */
    private PrjAddressModel __getPrjAddressFromRs(ResultSet rs) throws SQLException {
        PrjAddressModel bean = new PrjAddressModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setPraAuid(rs.getInt("PRA_AUID"));
        bean.setPraAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRA_ADATE")));
        bean.setPraEuid(rs.getInt("PRA_EUID"));
        bean.setPraEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRA_EDATE")));
        bean.setPraSort(rs.getInt("PRA_SORT"));
        return bean;
    }
}
