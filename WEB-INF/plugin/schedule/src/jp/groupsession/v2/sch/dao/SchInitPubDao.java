package jp.groupsession.v2.sch.dao;

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
import jp.groupsession.v2.sch.model.SchInitPubModel;

/**
 * <p>SCH_INIT_PUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchInitPubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchInitPubDao.class);

    /**
     * <p>Default Constructor
     */
    public SchInitPubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchInitPubDao(Connection con) {
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
            sql.addSql("drop table SCH_INIT_PUB");

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
            sql.addSql(" create table SCH_INIT_PUB (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   SIP_TYPE integer not null,");
            sql.addSql("   SIP_PSID integer not null,");
            sql.addSql("   primary key (USR_SID,SIP_TYPE,SIP_PSID)");
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
     * <p>Insert SCH_INIT_PUB Data Bindding JavaBean
     * @param bean SCH_INIT_PUB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchInitPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_INIT_PUB(");
            sql.addSql("   USR_SID,");
            sql.addSql("   SIP_TYPE,");
            sql.addSql("   SIP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSipType());
            sql.addIntValue(bean.getSipPsid());
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
     * <p>Update SCH_INIT_PUB Data Bindding JavaBean
     * @param bean SCH_INIT_PUB Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchInitPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_INIT_PUB");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SIP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SIP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSipType());
            sql.addIntValue(bean.getSipPsid());

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
     * <p>Select SCH_INIT_PUB All Data
     * @return List in SCH_INIT_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<SchInitPubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchInitPubModel> ret = new ArrayList<SchInitPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   SIP_TYPE,");
            sql.addSql("   SIP_PSID");
            sql.addSql(" from ");
            sql.addSql("   SCH_INIT_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchInitPubFromRs(rs));
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
     * <br>[機  能] ユーザSIDを指定し、公開対象一覧を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return 公開対象一覧
     * @throws SQLException SQL実行例外
     */
    public List<SchInitPubModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchInitPubModel> ret = new ArrayList<SchInitPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   SIP_TYPE,");
            sql.addSql("   SIP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchInitPubFromRs(rs));
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
     * <p>Select SCH_INIT_PUB
     * @param usrSid USR_SID
     * @param sipType SIP_TYPE
     * @param sipPsid SIP_PSID
     * @return SCH_INIT_PUBModel
     * @throws SQLException SQL実行例外
     */
    public SchInitPubModel select(int usrSid, int sipType, int sipPsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchInitPubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   SIP_TYPE,");
            sql.addSql("   SIP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SIP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SIP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(sipType);
            sql.addIntValue(sipPsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchInitPubFromRs(rs);
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
     * <br>[機  能] スケジュール初期値設定_公開対象を削除する
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
            sql.addSql("   SCH_INIT_PUB");
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
     * <p>Delete SCH_INIT_PUB
     * @param usrSid USR_SID
     * @param sipType SIP_TYPE
     * @param sipPsid SIP_PSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int sipType, int sipPsid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_INIT_PUB");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SIP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SIP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(sipType);
            sql.addIntValue(sipPsid);

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
     * <p>Create SCH_INIT_PUB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchInitPubModel
     * @throws SQLException SQL実行例外
     */
    private SchInitPubModel __getSchInitPubFromRs(ResultSet rs) throws SQLException {
        SchInitPubModel bean = new SchInitPubModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSipType(rs.getInt("SIP_TYPE"));
        bean.setSipPsid(rs.getInt("SIP_PSID"));
        return bean;
    }
}
