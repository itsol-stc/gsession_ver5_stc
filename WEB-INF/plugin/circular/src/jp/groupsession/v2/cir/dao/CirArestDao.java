package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.model.CirArestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CIR_AREST Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirArestDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirArestDao.class);

    /**
     * <p>Default Constructor
     */
    public CirArestDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirArestDao(Connection con) {
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
            sql.addSql("drop table CIR_AREST");

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
            sql.addSql(" create table CIR_AREST (");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (GRP_SID,USR_SID)");
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
     * <p>Insert CIR_AREST Data Bindding JavaBean
     * @param bean CIR_AREST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirArestModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_AREST(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Update CIR_AREST Data Bindding JavaBean
     * @param bean CIR_AREST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirArestModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_AREST");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select CIR_AREST All Data
     * @return List in CIR_ARESTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirArestModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirArestModel> ret = new ArrayList<CirArestModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_AREST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirArestFromRs(rs));
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
     * <p>Select CIR_AREST
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return CIR_ARESTModel
     * @throws SQLException SQL実行例外
     */
    public CirArestModel select(int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirArestModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_AREST");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirArestFromRs(rs);
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
     * <p>Delete CIR_AREST
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 削除行数
     */
    public int delete(int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_AREST");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            sql.addIntValue(usrSid);

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
     * <br>[機  能] 回覧板登録制限設定を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteAll() throws SQLException {
        Statement stmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_AREST");

            log__.info(sql.toLogString());
            stmt = con.createStatement();
            count = stmt.executeUpdate(sql.toSqlString());
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(stmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 回覧板登録制限設定を複数登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param dataList 回覧板登録制限設定
     * @throws SQLException SQL実行時例外
     */
    public void insert(List<CirArestModel> dataList) throws SQLException {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_AREST(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (CirArestModel data : dataList) {
                sql.addIntValue(data.getGrpSid());
                sql.addIntValue(data.getUsrSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
                pstmt.clearParameters();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したユーザが回覧板作成可能者かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return true: 回覧板作成可能者、 false: 回覧板作成者に含まれない
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
            sql.addSql(" select 1 from CIR_AREST");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     GRP_SID = -1");
            sql.addSql("   and");
            sql.addSql("     USR_SID = ?");
            sql.addSql("   )");
            sql.addSql(" or");
            sql.addSql("   (");
            sql.addSql("     USR_SID = -1");
            sql.addSql("   and");
            sql.addSql("     GRP_SID in (");
            sql.addSql("       select GRP_SID from CMN_BELONGM");
            sql.addSql("       where USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Create CIR_AREST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirArestModel
     * @throws SQLException SQL実行例外
     */
    private CirArestModel __getCirArestFromRs(ResultSet rs) throws SQLException {
        CirArestModel bean = new CirArestModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
