package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.rsv.model.RsvInitPubModel;

/**
 * <p>RSV_INIT_PUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvInitPubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvInitPubDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvInitPubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvInitPubDao(Connection con) {
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
            sql.addSql("drop table RSV_INIT_PUB");

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
            sql.addSql(" create table RSV_INIT_PUB (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   RIP_TYPE integer not null,");
            sql.addSql("   RIP_PSID integer not null,");
            sql.addSql("   primary key (USR_SID,RIP_TYPE,RIP_PSID)");
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
     * <p>Insert RSV_INIT_PUB Data Bindding JavaBean
     * @param bean RSV_INIT_PUB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvInitPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_INIT_PUB(");
            sql.addSql("   USR_SID,");
            sql.addSql("   RIP_TYPE,");
            sql.addSql("   RIP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRipType());
            sql.addIntValue(bean.getRipPsid());
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
     * <p>Update RSV_INIT_PUB Data Bindding JavaBean
     * @param bean RSV_INIT_PUB Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvInitPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_INIT_PUB");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RIP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RIP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRipType());
            sql.addIntValue(bean.getRipPsid());

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
     * <p>Select RSV_INIT_PUB All Data
     * @return List in RSV_INIT_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvInitPubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvInitPubModel> ret = new ArrayList<RsvInitPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   RIP_TYPE,");
            sql.addSql("   RIP_PSID");
            sql.addSql(" from ");
            sql.addSql("   RSV_INIT_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvInitPubFromRs(rs));
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
     * <br>[機  能] 施設予約初期値設定_公開対象を取得
     * <br>[解  説] 
     * <br>[備  考] 
     * @param userSid セッションユーザSID
     * @return 公開対象リスト
     * @throws SQLException SQL実行例外
     */
    public List<RsvInitPubModel> select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvInitPubModel> ret = new ArrayList<RsvInitPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   RIP_TYPE,");
            sql.addSql("   RIP_PSID");
            sql.addSql(" from ");
            sql.addSql("   RSV_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvInitPubFromRs(rs));
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
     * <p>Select RSV_INIT_PUB
     * @param usrSid USR_SID
     * @param ripType RIP_TYPE
     * @param ripPsid RIP_PSID
     * @return RSV_INIT_PUBModel
     * @throws SQLException SQL実行例外
     */
    public RsvInitPubModel select(int usrSid, int ripType, int ripPsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvInitPubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   RIP_TYPE,");
            sql.addSql("   RIP_PSID");
            sql.addSql(" from");
            sql.addSql("   RSV_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RIP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RIP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(ripType);
            sql.addIntValue(ripPsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvInitPubFromRs(rs);
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
     * <br>[機  能] 施設予約初期値設定_公開対象を削除する
     * <br>[解  説] 
     * <br>[備  考] 
     * @param userSid セッションユーザSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <p>Delete RSV_INIT_PUB
     * @param usrSid USR_SID
     * @param ripType RIP_TYPE
     * @param ripPsid RIP_PSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int ripType, int ripPsid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RIP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RIP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(ripType);
            sql.addIntValue(ripPsid);

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
     * <p>Create RSV_INIT_PUB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvInitPubModel
     * @throws SQLException SQL実行例外
     */
    private RsvInitPubModel __getRsvInitPubFromRs(ResultSet rs) throws SQLException {
        RsvInitPubModel bean = new RsvInitPubModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRipType(rs.getInt("RIP_TYPE"));
        bean.setRipPsid(rs.getInt("RIP_PSID"));
        return bean;
    }
}
