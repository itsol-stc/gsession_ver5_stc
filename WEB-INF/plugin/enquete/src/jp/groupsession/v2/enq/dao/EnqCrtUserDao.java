package jp.groupsession.v2.enq.dao;

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
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.model.EnqCrtUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ENQ_CRT_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqCrtUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqCrtUserDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqCrtUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqCrtUserDao(Connection con) {
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
            sql.addSql("drop table ENQ_CRT_USER");

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
            sql.addSql(" create table ENQ_CRT_USER (");
            sql.addSql("   ECU_KBN integer not null,");
            sql.addSql("   ECU_SID bigint not null,");
            sql.addSql("   ECU_AUID integer not null,");
            sql.addSql("   ECU_ADATE timestamp not null,");
            sql.addSql("   ECU_EUID integer not null,");
            sql.addSql("   ECU_EDATE timestamp not null,");
            sql.addSql("   primary key (ECU_KBN,ECU_SID)");
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
     * <p>Insert ENQ_CRT_USER Data Binding JavaBean
     * @param bean ENQ_CRT_USER Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqCrtUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_CRT_USER(");
            sql.addSql("   ECU_KBN,");
            sql.addSql("   ECU_SID,");
            sql.addSql("   ECU_AUID,");
            sql.addSql("   ECU_ADATE,");
            sql.addSql("   ECU_EUID,");
            sql.addSql("   ECU_EDATE");
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
            sql.addIntValue(bean.getEcuKbn());
            sql.addLongValue(bean.getEcuSid());
            sql.addIntValue(bean.getEcuAuid());
            sql.addDateValue(bean.getEcuAdate());
            sql.addIntValue(bean.getEcuEuid());
            sql.addDateValue(bean.getEcuEdate());
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
     * <p>Update ENQ_CRT_USER Data Binding JavaBean
     * @param bean ENQ_CRT_USER Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqCrtUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_CRT_USER");
            sql.addSql(" set ");
            sql.addSql("   ECU_AUID=?,");
            sql.addSql("   ECU_ADATE=?,");
            sql.addSql("   ECU_EUID=?,");
            sql.addSql("   ECU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ECU_KBN=?");
            sql.addSql(" and");
            sql.addSql("   ECU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEcuAuid());
            sql.addDateValue(bean.getEcuAdate());
            sql.addIntValue(bean.getEcuEuid());
            sql.addDateValue(bean.getEcuEdate());
            //where
            sql.addIntValue(bean.getEcuKbn());
            sql.addLongValue(bean.getEcuSid());

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
     * <p>Select ENQ_CRT_USER All Data
     * @return List in ENQ_CRT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqCrtUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqCrtUserModel> ret = new ArrayList<EnqCrtUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ECU_KBN,");
            sql.addSql("   ECU_SID,");
            sql.addSql("   ECU_AUID,");
            sql.addSql("   ECU_ADATE,");
            sql.addSql("   ECU_EUID,");
            sql.addSql("   ECU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_CRT_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqCrtUserFromRs(rs));
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
     * <p>Select ENQ_CRT_USER
     * @param ecuKbn ECU_KBN
     * @param ecuSid ECU_SID
     * @return ENQ_CRT_USERModel
     * @throws SQLException SQL実行例外
     */
    public EnqCrtUserModel select(int ecuKbn, long ecuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqCrtUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ECU_KBN,");
            sql.addSql("   ECU_SID,");
            sql.addSql("   ECU_AUID,");
            sql.addSql("   ECU_ADATE,");
            sql.addSql("   ECU_EUID,");
            sql.addSql("   ECU_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_CRT_USER");
            sql.addSql(" where ");
            sql.addSql("   ECU_KBN=?");
            sql.addSql(" and");
            sql.addSql("   ECU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ecuKbn);
            sql.addLongValue(ecuSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqCrtUserFromRs(rs);
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
     * <br>[機  能] 論理削除されたユーザとグループを除いた、アンケート作成可能者を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return List in ENQ_CRT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqCrtUserModel> selectList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqCrtUserModel> ret = new ArrayList<EnqCrtUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ECU_KBN,");
            sql.addSql("   ECU_SID");
            sql.addSql(" from");
            sql.addSql("   ENQ_CRT_USER");
            sql.addSql(" where ECU_KBN = 1");
            sql.addSql("   and exists (");
            sql.addSql("       select 1");
            sql.addSql("         from CMN_USRM");
            sql.addSql("        where USR_JKBN = 0");
            sql.addSql("          and ECU_SID = USR_SID");
            sql.addSql("   )");
            sql.addSql(" union");
            sql.addSql(" select");
            sql.addSql("   ECU_KBN,");
            sql.addSql("   ECU_SID");
            sql.addSql(" from");
            sql.addSql("   ENQ_CRT_USER");
            sql.addSql(" where ECU_KBN = 2");
            sql.addSql("   and exists (");
            sql.addSql("       select 1");
            sql.addSql("         from CMN_GROUPM");
            sql.addSql("        where GRP_JKBN = 0");
            sql.addSql("          and ECU_SID = GRP_SID");
            sql.addSql("   )");


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqCrtUsers(rs));
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
     * <br>[機  能] 指定したユーザがアンケート作成可能者かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return true: アンケート作成可能者、 false: アンケート作成可能者に含まれない
     * @throws SQLException SQL実行例外
     */
    public boolean existUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ECU_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_CRT_USER");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     ECU_KBN = ?");
            sql.addSql("   and");
            sql.addSql("     ECU_SID = ?");
            sql.addSql("   )");
            sql.addSql("   or");
            sql.addSql("   (");
            sql.addSql("     ECU_KBN = ?");
            sql.addSql("   and");
            sql.addSql("     ECU_SID in (");
            sql.addSql("       select GRP_SID from CMN_BELONGM");
            sql.addSql("       where USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstEnquete.TAISYO_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstEnquete.TAISYO_KBN_GROUP);
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getLong("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Delete ENQ_CRT_USER
     * @param ecuKbn ECU_KBN
     * @param ecuSid ECU_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ecuKbn, long ecuSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_CRT_USER");
            sql.addSql(" where ");
            sql.addSql("   ECU_KBN=?");
            sql.addSql(" and");
            sql.addSql("   ECU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ecuKbn);
            sql.addLongValue(ecuSid);

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
     * <br>[機  能] アンケート作成対象者全件削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteAll() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_CRT_USER");

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
     * <p>Create ENQ_CRT_USER Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqCrtUserModel
     * @throws SQLException SQL実行例外
     */
    private EnqCrtUserModel __getEnqCrtUserFromRs(ResultSet rs) throws SQLException {
        EnqCrtUserModel bean = new EnqCrtUserModel();
        bean.setEcuKbn(rs.getInt("ECU_KBN"));
        bean.setEcuSid(rs.getInt("ECU_SID"));
        bean.setEcuAuid(rs.getInt("ECU_AUID"));
        bean.setEcuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ECU_ADATE")));
        bean.setEcuEuid(rs.getInt("ECU_EUID"));
        bean.setEcuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ECU_EDATE")));
        return bean;
    }

    /**
     * <p>Create ENQ_CRT_USER Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqCrtUserModel
     * @throws SQLException SQL実行例外
     */
    private EnqCrtUserModel __getEnqCrtUsers(ResultSet rs) throws SQLException {
        EnqCrtUserModel bean = new EnqCrtUserModel();
        bean.setEcuKbn(rs.getInt("ECU_KBN"));
        bean.setEcuSid(rs.getInt("ECU_SID"));
        return bean;
    }
}
