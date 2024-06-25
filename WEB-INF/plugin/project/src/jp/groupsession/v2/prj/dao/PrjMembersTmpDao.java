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
import jp.groupsession.v2.prj.model.PrjMembersTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_MEMBERS_TMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjMembersTmpDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjMembersTmpDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjMembersTmpDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjMembersTmpDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_MEMBERS_TMP Data Bindding JavaBean
     * @param bean PRJ_MEMBERS_TMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjMembersTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_MEMBERS_TMP(");
            sql.addSql("   PRT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PMT_EMPLOYEE_KBN,");
            sql.addSql("   PMT_ADMIN_KBN,");
            sql.addSql("   PMT_AUID,");
            sql.addSql("   PMT_ADATE,");
            sql.addSql("   PMT_EUID,");
            sql.addSql("   PMT_EDATE");
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
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPmtEmployeeKbn());
            sql.addIntValue(bean.getPmtAdminKbn());
            sql.addIntValue(bean.getPmtAuid());
            sql.addDateValue(bean.getPmtAdate());
            sql.addIntValue(bean.getPmtEuid());
            sql.addDateValue(bean.getPmtEdate());
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
     * <p>Update PRJ_MEMBERS_TMP Data Bindding JavaBean
     * @param bean PRJ_MEMBERS_TMP Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(PrjMembersTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" set ");
            sql.addSql("   PMT_ADMIN_KBN=?,");
            sql.addSql("   PMT_AUID=?,");
            sql.addSql("   PMT_ADATE=?,");
            sql.addSql("   PMT_EUID=?,");
            sql.addSql("   PMT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PMT_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPmtAdminKbn());
            sql.addIntValue(bean.getPmtAuid());
            sql.addDateValue(bean.getPmtAdate());
            sql.addIntValue(bean.getPmtEuid());
            sql.addDateValue(bean.getPmtEdate());
            //where
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPmtEmployeeKbn());

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
     * <p>Select PRJ_MEMBERS_TMP All Data
     * @return List in PRJ_MEMBERS_TMPModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMembersTmpModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjMembersTmpModel> ret = new ArrayList<PrjMembersTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PMT_EMPLOYEE_KBN,");
            sql.addSql("   PMT_ADMIN_KBN,");
            sql.addSql("   PMT_AUID,");
            sql.addSql("   PMT_ADATE,");
            sql.addSql("   PMT_EUID,");
            sql.addSql("   PMT_EDATE");
            sql.addSql("   PMT_MEM_KEY");
            sql.addSql(" from ");
            sql.addSql("   PRJ_MEMBERS_TMP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjMembersTmpFromRs(rs));
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
     * <p>Select PRJ_MEMBERS_TMP
     * @param bean PRJ_MEMBERS_TMP Model
     * @return PRJ_MEMBERS_TMPModel
     * @throws SQLException SQL実行例外
     */
    public PrjMembersTmpModel select(PrjMembersTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjMembersTmpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PMT_EMPLOYEE_KBN,");
            sql.addSql("   PMT_ADMIN_KBN,");
            sql.addSql("   PMT_AUID,");
            sql.addSql("   PMT_ADATE,");
            sql.addSql("   PMT_EUID,");
            sql.addSql("   PMT_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PMT_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPmtEmployeeKbn());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjMembersTmpFromRs(rs);
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
     * <p>Delete PRJ_MEMBERS_TMP
     * @param bean PRJ_MEMBERS_TMP Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(PrjMembersTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PMT_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPmtEmployeeKbn());

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
     * <p>Delete PRJ_MEMBERS_TMP
     * @param prtSid prtSid
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteAll(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

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
     * <br>[機  能] プロジェクトテンプレートメンバー(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjTmpSidList 削除SIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deletePrjTemplateMember(ArrayList<PrjPrjdataTmpModel> prjTmpSidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (prjTmpSidList == null || prjTmpSidList.size() < 1) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID in (");

            for (int i = 0; i < prjTmpSidList.size(); i++) {
                sql.addSql("?");
                PrjPrjdataTmpModel param = prjTmpSidList.get(i);
                sql.addIntValue(param.getPrtSid());
                if (i < prjTmpSidList.size() - 1) {
                    sql.addSql(", ");
                }
            }
            sql.addSql(")");

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
     * <br>[機  能] プロジェクトテンプレートメンバー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid PRT_SID
     * @return PRJ_MEMBERS_TMP Model
     * @throws SQLException SQL実行例外
     */
    public List<PrjMembersTmpModel> getPrjMembersTmp(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjMembersTmpModel> ret = new ArrayList<PrjMembersTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PMT_EMPLOYEE_KBN,");
            sql.addSql("   PMT_ADMIN_KBN,");
            sql.addSql("   PMT_AUID,");
            sql.addSql("   PMT_ADATE,");
            sql.addSql("   PMT_EUID,");
            sql.addSql("   PMT_EDATE,");
            sql.addSql("   PMT_MEM_KEY");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjMembersTmpFromRs(rs));
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
     * <p>Create PRJ_MEMBERS_TMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjMembersTmpModel
     * @throws SQLException SQL実行例外
     */
    private PrjMembersTmpModel __getPrjMembersTmpFromRs(ResultSet rs) throws SQLException {
        PrjMembersTmpModel bean = new PrjMembersTmpModel();
        bean.setPrtSid(rs.getInt("PRT_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setPmtEmployeeKbn(rs.getInt("PMT_EMPLOYEE_KBN"));
        bean.setPmtAdminKbn(rs.getInt("PMT_ADMIN_KBN"));
        bean.setPmtAuid(rs.getInt("PMT_AUID"));
        bean.setPmtAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PMT_ADATE")));
        bean.setPmtEuid(rs.getInt("PMT_EUID"));
        bean.setPmtEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PMT_EDATE")));
        bean.setPmtMemKey(rs.getString("PMT_MEM_KEY"));
        return bean;
    }
}