package jp.groupsession.v2.ptl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.model.PtlPortalConfReadModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTAL_CONF_READ Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalConfReadDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortalConfReadDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortalConfReadDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortalConfReadDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTAL_CONF_READ");

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
            sql.addSql(" create table PTL_PORTAL_CONF_READ (");
            sql.addSql("   PTL_SID integer not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   GRP_SID integer not null,");
            sql.addSql("   primary key (PTL_SID,USR_SID,GRP_SID)");
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
     * <p>Insert PTL_PORTAL_CONF_READ Data Bindding JavaBean
     * @param bean PTL_PORTAL_CONF_READ Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortalConfReadModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTAL_CONF_READ(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtlSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>ポータル閲覧メンバーの登録を行う
     * @param ptlSid ポータルSID
     * @param usrGrpSidList ユーザSID・グループSID一覧
     * @throws SQLException SQL実行例外
     */
    public void insert(int ptlSid, String[] usrGrpSidList) throws SQLException {

        if (usrGrpSidList == null || usrGrpSidList.length <= 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //新規登録
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql(" PTL_PORTAL_CONF_READ(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   " + String.valueOf(ptlSid) + ",");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (String userGrpSid : usrGrpSidList) {
                String str = NullDefault.getString(userGrpSid, "-1");
                if (str.contains(GSConstPortal.MEMBER_GROUP_HEADSTR)) {
                    //グループ
                    userGrpSid = (str.substring(1, str.length()));
                    pstmt.setInt(1, -1);
                    pstmt.setInt(2, Integer.parseInt(userGrpSid));
                } else {
                    //ユーザ
                    userGrpSid = str;
                    pstmt.setInt(1, Integer.parseInt(userGrpSid));
                    pstmt.setInt(2, -1);
                }

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update PTL_PORTAL_CONF_READ Data Bindding JavaBean
     * @param bean PTL_PORTAL_CONF_READ Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortalConfReadModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_CONF_READ");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getPtlSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select PTL_PORTAL_CONF_READ All Data
     * @return List in PTL_PORTAL_CONF_READModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalConfReadModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalConfReadModel> ret = new ArrayList<PtlPortalConfReadModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL_CONF_READ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalConfReadFromRs(rs));
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
     * <p>Select PTL_PORTAL_CONF_READ
     * @param ptlSid PTL_SID
     * @return PTL_PORTAL_CONF_READModelList
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalConfReadModel> select(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalConfReadModel> ret = new ArrayList<PtlPortalConfReadModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_CONF_READ");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalConfReadFromRs(rs));
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
     * <p>Select PTL_PORTAL_CONF_READ
     * @param usrSid USR_SID
     * @return PTL_PORTAL_CONF_READModelList
     * @throws SQLException SQL実行例外
     */
    public int selectUsrConf(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_CONF_READ");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = 1;
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
     * <p>Delete PTL_PORTAL_CONF_READ
     * @param ptlSid PTL_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_CONF_READ");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

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
     * <p>Delete PTL_PORTAL_CONF_READ
     * @param userSid ユーザSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteForUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_CONF_READ");
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
     * <p>Delete PTL_PORTAL_CONF_READ
     * @param grpSid グループSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteForGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_CONF_READ");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

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
     * <p>Create PTL_PORTAL_CONF_READ Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortalConfReadModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortalConfReadModel __getPtlPortalConfReadFromRs(ResultSet rs) throws SQLException {
        PtlPortalConfReadModel bean = new PtlPortalConfReadModel();
        bean.setPtlSid(rs.getInt("PTL_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        return bean;
    }
}
