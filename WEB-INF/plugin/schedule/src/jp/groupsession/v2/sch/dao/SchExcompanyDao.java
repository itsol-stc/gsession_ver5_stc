package jp.groupsession.v2.sch.dao;

import jp.co.sjts.util.date.UDate;
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
import jp.groupsession.v2.sch.model.SchExcompanyModel;

/**
 * <p>SCH_EXCOMPANY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchExcompanyDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchExcompanyDao.class);

    /**
     * <p>Default Constructor
     */
    public SchExcompanyDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchExcompanyDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert SCH_EXCOMPANY Data Bindding JavaBean
     * @param bean SCH_EXCOMPANY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchExcompanyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_EXCOMPANY(");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
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
            sql.addIntValue(bean.getSceSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getSccAuid());
            sql.addDateValue(bean.getSccAdate());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
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
     * <p>Update SCH_EXCOMPANY Data Bindding JavaBean
     * @param bean SCH_EXCOMPANY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchExcompanyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_EXCOMPANY");
            sql.addSql(" set ");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
            //where
            sql.addIntValue(bean.getSceSid());
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
     * <p>Select SCH_EXCOMPANY All Data
     * @return List in SCH_EXCOMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExcompanyModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchExcompanyModel> ret = new ArrayList<SchExcompanyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXCOMPANY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExcompanyFromRs(rs));
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
     * <p>Select SCH_EXCOMPANY Data
     * @param sceSid SCE_SID
     * @return List in SCH_EXCOMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExcompanyModel> select(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchExcompanyModel> ret = new ArrayList<SchExcompanyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXCOMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addIntValue(sceSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExcompanyFromRs(rs));
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
     * <p>Select SCH_EXCOMPANY
     * @param sceSid SCE_SID
     * @param acoSid ACO_SID
     * @param abaSid ABA_SID
     * @return SCH_EXCOMPANYModel
     * @throws SQLException SQL実行例外
     */
    public SchExcompanyModel select(int sceSid, int acoSid, int abaSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchExcompanyModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_EXCOMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addIntValue(acoSid);
            sql.addIntValue(abaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchExcompanyFromRs(rs);
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
     * <p>Delete SCH_EXCOMPANY
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
            sql.addSql("   SCH_EXCOMPANY");
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
     * <p>Delete SCH_EXCOMPANY
     * @param sceSidList List in SCE_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(List<Integer> sceSidList) throws SQLException {

        if (sceSidList == null || sceSidList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXCOMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in (");

            sql.addSql("     ?");
            sql.addIntValue(sceSidList.get(0));

            for (int index = 1; index < sceSidList.size(); index++) {
                sql.addSql("     ,?");
                sql.addIntValue(sceSidList.get(index));
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create SCH_EXCOMPANY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchExcompanyModel
     * @throws SQLException SQL実行例外
     */
    private SchExcompanyModel __getSchExcompanyFromRs(ResultSet rs) throws SQLException {
        SchExcompanyModel bean = new SchExcompanyModel();
        bean.setSceSid(rs.getInt("SCE_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setSccAuid(rs.getInt("SCC_AUID"));
        bean.setSccAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_ADATE")));
        bean.setSccEuid(rs.getInt("SCC_EUID"));
        bean.setSccEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_EDATE")));
        return bean;
    }
}
