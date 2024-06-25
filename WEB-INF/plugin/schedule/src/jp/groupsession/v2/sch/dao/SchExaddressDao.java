package jp.groupsession.v2.sch.dao;

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
import jp.groupsession.v2.sch.model.SchExaddressModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SCH_EXADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchExaddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchExaddressDao.class);

    /**
     * <p>Default Constructor
     */
    public SchExaddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchExaddressDao(Connection con) {
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
            sql.addSql("drop table SCH_EXADDRESS");

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
            sql.addSql(" create table SCH_EXADDRESS (");
            sql.addSql("   SCE_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   SEA_AUID NUMBER(10,0) not null,");
            sql.addSql("   SEA_ADATE varchar(23) not null,");
            sql.addSql("   SEA_EUID NUMBER(10,0) not null,");
            sql.addSql("   SEA_EDATE varchar(23) not null,");
            sql.addSql("   primary key (SCE_SID,ADR_SID)");
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
     * <p>Insert SCH_EXADDRESS Data Bindding JavaBean
     * @param bean SCH_EXADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchExaddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_EXADDRESS(");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   SEA_AUID,");
            sql.addSql("   SEA_ADATE,");
            sql.addSql("   SEA_EUID,");
            sql.addSql("   SEA_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSceSid());
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getSeaAuid());
            sql.addDateValue(bean.getSeaAdate());
            sql.addIntValue(bean.getSeaEuid());
            sql.addDateValue(bean.getSeaEdate());
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
     * <p>Update SCH_EXADDRESS Data Bindding JavaBean
     * @param bean SCH_EXADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchExaddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_EXADDRESS");
            sql.addSql(" set ");
            sql.addSql("   SEA_AUID=?,");
            sql.addSql("   SEA_ADATE=?,");
            sql.addSql("   SEA_EUID=?,");
            sql.addSql("   SEA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSeaAuid());
            sql.addDateValue(bean.getSeaAdate());
            sql.addIntValue(bean.getSeaEuid());
            sql.addDateValue(bean.getSeaEdate());
            //where
            sql.addIntValue(bean.getSceSid());
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
     * <p>Select SCH_EXADDRESS All Data
     * @return List in SCH_EXADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExaddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchExaddressModel> ret = new ArrayList<SchExaddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   SEA_AUID,");
            sql.addSql("   SEA_ADATE,");
            sql.addSql("   SEA_EUID,");
            sql.addSql("   SEA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExaddressFromRs(rs));
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
     * <p>Select SCH_EXADDRESS Data
     * @param sceSid SCE_SID
     * @return List in SCH_EXADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExaddressModel> select(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchExaddressModel> ret = new ArrayList<SchExaddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   SEA_AUID,");
            sql.addSql("   SEA_ADATE,");
            sql.addSql("   SEA_EUID,");
            sql.addSql("   SEA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");
            sql.addIntValue(sceSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExaddressFromRs(rs));
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
     * <p>Select SCH_EXADDRESS
     * @param sceSid SCE_SID
     * @param adrSid ADR_SID
     * @return SCH_EXADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public SchExaddressModel select(int sceSid, int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchExaddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   SEA_AUID,");
            sql.addSql("   SEA_ADATE,");
            sql.addSql("   SEA_EUID,");
            sql.addSql("   SEA_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_EXADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchExaddressFromRs(rs);
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
     * <p>Delete SCH_EXADDRESS
     * @param sceSid SCE_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

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
     * <p>Delete SCH_EXADDRESS
     * @param sceSid SCE_SID
     * @param adrSid ADR_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(int sceSid, int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
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
     * <p>Create SCH_EXADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchExaddressModel
     * @throws SQLException SQL実行例外
     */
    private SchExaddressModel __getSchExaddressFromRs(ResultSet rs) throws SQLException {
        SchExaddressModel bean = new SchExaddressModel();
        bean.setSceSid(rs.getInt("SCE_SID"));
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setSeaAuid(rs.getInt("SEA_AUID"));
        bean.setSeaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SEA_ADATE")));
        bean.setSeaEuid(rs.getInt("SEA_EUID"));
        bean.setSeaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SEA_EDATE")));
        return bean;
    }
}
