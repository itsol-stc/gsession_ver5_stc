package jp.groupsession.v2.cht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChtGroupTargetModel;

/**
 * <p>CHT_GROUP_TARGET Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtGroupTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtGroupTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtGroupTargetDao(Connection con) {
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
            sql.addSql("drop table CHT_GROUP_TARGET");

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
            sql.addSql(" create table CHT_GROUP_TARGET (");
            sql.addSql("   CGT_TYPE integer not null,");
            sql.addSql("   CGT_SSID integer not null");
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
     * <p>Delete CHT_GROUP_TARGET
     * @return count 削除数
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
            sql.addSql("   CHT_GROUP_TARGET");

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
     * <p>Insert CHT_GROUP_TARGET Data Bindding JavaBean
     * @param bean CHT_GROUP_TARGET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtGroupTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_TARGET(");
            sql.addSql("   CGT_TYPE,");
            sql.addSql("   CGT_SSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCgtType());
            sql.addIntValue(bean.getCgtSsid());
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
     * <p>Update CHT_GROUP_TARGET Data Bindding JavaBean
     * @param bean CHT_GROUP_TARGET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtGroupTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_TARGET");
            sql.addSql(" set ");
            sql.addSql("   CGT_TYPE=?,");
            sql.addSql("   CGT_SSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCgtType());
            sql.addIntValue(bean.getCgtSsid());

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
     * <p>Select CHT_GROUP_TARGET All Data
     * @return List in CHT_GROUP_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<ChtGroupTargetModel> ret = new ArrayList<ChtGroupTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGT_TYPE,");
            sql.addSql("   CGT_SSID");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_TARGET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupTargetFromRs(rs));
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
     * <p>Select CHT_GROUP_TARGET All Data
     * @param usrSid ユーザSID
     * @return List in CHT_GROUP_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public int select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COUNT(CGT_SSID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_TARGET");
            sql.addSql(" where ");
            sql.addSql("   ( ");
            sql.addSql("    ( ");
            sql.addSql("       CGT_TYPE = ? ");
            sql.addSql("     and ");
            sql.addSql("       CGT_SSID = ? ");
            sql.addSql("    ) ");
            sql.addSql("    or ");
            sql.addSql("    ( ");
            sql.addSql("       CGT_TYPE = ? ");
            sql.addSql("     and ");
            sql.addSql("       CGT_SSID IN ( ");
            sql.addSql("         select ");
            sql.addSql("           GRP_SID ");
            sql.addSql("         from ");
            sql.addSql("            CMN_BELONGM ");
            sql.addSql("         where ");
            sql.addSql("           USR_SID = ? ");
            sql.addSql("       ) ");
            sql.addSql("    ) ");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Create CHT_GROUP_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupTargetModel
     * @throws SQLException SQL実行例外
     */
    private ChtGroupTargetModel __getChtGroupTargetFromRs(ResultSet rs) throws SQLException {
        ChtGroupTargetModel bean = new ChtGroupTargetModel();
        bean.setCgtType(rs.getInt("CGT_TYPE"));
        bean.setCgtSsid(rs.getInt("CGT_SSID"));
        return bean;
    }
}
