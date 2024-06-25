package jp.groupsession.v2.bmk.dao;

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
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.model.BmkGroupEditModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_GROUP_EDIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkGroupEditDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkGroupEditDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkGroupEditDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkGroupEditDao(Connection con) {
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
            sql.addSql("drop table BMK_GROUP_EDIT");

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
            sql.addSql(" create table BMK_GROUP_EDIT (");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   BGE_GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   BGE_USR_SID NUMBER(10,0) not null,");
            sql.addSql("   BGE_AUID NUMBER(10,0) not null,");
            sql.addSql("   BGE_ADATE varchar(23) not null,");
            sql.addSql("   BGE_EUID NUMBER(10,0) not null,");
            sql.addSql("   BGE_EDATE varchar(23) not null,");
            sql.addSql("   primary key (GRP_SID,BGE_GRP_SID,BGE_USR_SID)");
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
     * <p>Insert BMK_GROUP_EDIT Data Bindding JavaBean
     * @param bean BMK_GROUP_EDIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkGroupEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_GROUP_EDIT(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGE_GRP_SID,");
            sql.addSql("   BGE_USR_SID,");
            sql.addSql("   BGE_AUID,");
            sql.addSql("   BGE_ADATE,");
            sql.addSql("   BGE_EUID,");
            sql.addSql("   BGE_EDATE");
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
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getBgeGrpSid());
            sql.addIntValue(bean.getBgeUsrSid());
            sql.addIntValue(bean.getBgeAuid());
            sql.addDateValue(bean.getBgeAdate());
            sql.addIntValue(bean.getBgeEuid());
            sql.addDateValue(bean.getBgeEdate());
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
     * <p>Update BMK_GROUP_EDIT Data Bindding JavaBean
     * @param bean BMK_GROUP_EDIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkGroupEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" set ");
            sql.addSql("   BGE_AUID=?,");
            sql.addSql("   BGE_ADATE=?,");
            sql.addSql("   BGE_EUID=?,");
            sql.addSql("   BGE_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BGE_GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BGE_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBgeAuid());
            sql.addDateValue(bean.getBgeAdate());
            sql.addIntValue(bean.getBgeEuid());
            sql.addDateValue(bean.getBgeEdate());
            //where
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getBgeGrpSid());
            sql.addIntValue(bean.getBgeUsrSid());

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
     * <p>Select BMK_GROUP_EDIT All Data
     * @return List in BMK_GROUP_EDITModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkGroupEditModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkGroupEditModel> ret = new ArrayList<BmkGroupEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGE_GRP_SID,");
            sql.addSql("   BGE_USR_SID,");
            sql.addSql("   BGE_AUID,");
            sql.addSql("   BGE_ADATE,");
            sql.addSql("   BGE_EUID,");
            sql.addSql("   BGE_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_GROUP_EDIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkGroupEditFromRs(rs));
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
     * <p>Select BMK_GROUP_EDIT All Data
     * @param gSid グループSID
     * @return List in BMK_GROUP_EDITModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkGroupEditModel> select(int gSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkGroupEditModel> ret = new ArrayList<BmkGroupEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGE_GRP_SID,");
            sql.addSql("   BGE_USR_SID,");
            sql.addSql("   BGE_AUID,");
            sql.addSql("   BGE_ADATE,");
            sql.addSql("   BGE_EUID,");
            sql.addSql("   BGE_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkGroupEditFromRs(rs));
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
     * <p>Select BMK_GROUP_EDIT
     * @param grpSid GRP_SID
     * @param bgeGrpSid BGE_GRP_SID
     * @param bgeUsrSid BGE_USR_SID
     * @return BMK_GROUP_EDITModel
     * @throws SQLException SQL実行例外
     */
    public BmkGroupEditModel select(int grpSid, int bgeGrpSid, int bgeUsrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkGroupEditModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGE_GRP_SID,");
            sql.addSql("   BGE_USR_SID,");
            sql.addSql("   BGE_AUID,");
            sql.addSql("   BGE_ADATE,");
            sql.addSql("   BGE_EUID,");
            sql.addSql("   BGE_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BGE_GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BGE_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            sql.addIntValue(bgeGrpSid);
            sql.addIntValue(bgeUsrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkGroupEditFromRs(rs);
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
     * <p>グループブックマークの編集権限を判定
     * @param grpEdit 編集権限区分
     * @param grpSid 選択グループSID
     * @param bgeGrpSid セッションユーザSIDが所属するグループSID
     * @param bgeUsrSid セッションユーザSID
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean selectPow(int grpEdit, int grpSid, int bgeGrpSid, int bgeUsrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        if (grpEdit != GSConstBookmark.EDIT_POW_GROUP && grpEdit != GSConstBookmark.EDIT_POW_USER) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BGE_GRP_SID,");
            sql.addSql("   BGE_USR_SID,");
            sql.addSql("   BGE_AUID,");
            sql.addSql("   BGE_ADATE,");
            sql.addSql("   BGE_EUID,");
            sql.addSql("   BGE_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addIntValue(grpSid);
            if (grpEdit == GSConstBookmark.EDIT_POW_GROUP) {
                sql.addSql(" and");
                sql.addSql("   BGE_GRP_SID=?");
                sql.addIntValue(bgeGrpSid);
            }
            if (grpEdit == GSConstBookmark.EDIT_POW_USER) {
                sql.addSql(" and");
                sql.addSql("   BGE_USR_SID=?");
                sql.addIntValue(bgeUsrSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>Delete BMK_GROUP_EDIT
     * @param grpSid GRP_SID
     * @param bgeGrpSid BGE_GRP_SID
     * @param bgeUsrSid BGE_USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int grpSid, int bgeGrpSid, int bgeUsrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BGE_GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BGE_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            sql.addIntValue(bgeGrpSid);
            sql.addIntValue(bgeUsrSid);

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
     * <p>Delete BMK_GROUP_EDIT
     * @param grpSid GRP_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_GROUP_EDIT");
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
     * <p>Delete BMK_GROUP_EDIT
     * @param grpSid GRP_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql("   or");
            sql.addSql("   BGE_GRP_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
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
     * <p>Delete BMK_GROUP_EDIT
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteUser(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_GROUP_EDIT");
            sql.addSql(" where ");
            sql.addSql("   BGE_USR_SID=?");
            sql.addIntValue(usrSid);
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
     * <p>Create BMK_GROUP_EDIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkGroupEditModel
     * @throws SQLException SQL実行例外
     */
    private BmkGroupEditModel __getBmkGroupEditFromRs(ResultSet rs) throws SQLException {
        BmkGroupEditModel bean = new BmkGroupEditModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setBgeGrpSid(rs.getInt("BGE_GRP_SID"));
        bean.setBgeUsrSid(rs.getInt("BGE_USR_SID"));
        bean.setBgeAuid(rs.getInt("BGE_AUID"));
        bean.setBgeAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BGE_ADATE")));
        bean.setBgeEuid(rs.getInt("BGE_EUID"));
        bean.setBgeEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BGE_EDATE")));
        return bean;
    }
}
