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
import jp.groupsession.v2.bmk.model.BmkPublicEditModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_PUBLIC_EDIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkPublicEditDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkPublicEditDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkPublicEditDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkPublicEditDao(Connection con) {
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
            sql.addSql("drop table BMK_PUBLIC_EDIT");

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
            sql.addSql(" create table BMK_PUBLIC_EDIT (");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   BPE_AUID NUMBER(10,0) not null,");
            sql.addSql("   BPE_ADATE varchar(23) not null,");
            sql.addSql("   BPE_EUID NUMBER(10,0) not null,");
            sql.addSql("   BPE_EDATE varchar(23) not null,");
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
     * <p>Insert BMK_PUBLIC_EDIT Data Bindding JavaBean
     * @param bean BMK_PUBLIC_EDIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkPublicEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_PUBLIC_EDIT(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BPE_AUID,");
            sql.addSql("   BPE_ADATE,");
            sql.addSql("   BPE_EUID,");
            sql.addSql("   BPE_EDATE");
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
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBpeAuid());
            sql.addDateValue(bean.getBpeAdate());
            sql.addIntValue(bean.getBpeEuid());
            sql.addDateValue(bean.getBpeEdate());
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
     * <p>Update BMK_PUBLIC_EDIT Data Bindding JavaBean
     * @param bean BMK_PUBLIC_EDIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkPublicEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_PUBLIC_EDIT");
            sql.addSql(" set ");
            sql.addSql("   BPE_AUID=?,");
            sql.addSql("   BPE_ADATE=?,");
            sql.addSql("   BPE_EUID=?,");
            sql.addSql("   BPE_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBpeAuid());
            sql.addDateValue(bean.getBpeAdate());
            sql.addIntValue(bean.getBpeEuid());
            sql.addDateValue(bean.getBpeEdate());
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
     * <p>Select BMK_PUBLIC_EDIT All Data
     * @return List in BMK_PUBLIC_EDITModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkPublicEditModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkPublicEditModel> ret = new ArrayList<BmkPublicEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BPE_AUID,");
            sql.addSql("   BPE_ADATE,");
            sql.addSql("   BPE_EUID,");
            sql.addSql("   BPE_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_PUBLIC_EDIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkPublicEditFromRs(rs));
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
     * <p>Select BMK_PUBLIC_EDIT
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return BMK_PUBLIC_EDITModel
     * @throws SQLException SQL実行例外
     */
    public BmkPublicEditModel select(int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkPublicEditModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BPE_AUID,");
            sql.addSql("   BPE_ADATE,");
            sql.addSql("   BPE_EUID,");
            sql.addSql("   BPE_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_PUBLIC_EDIT");
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
                ret = __getBmkPublicEditFromRs(rs);
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
     * <p>共有ブックマークの編集権限を判定
     * @param pubEdit 編集権限区分
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean selectPow(int pubEdit, int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        if (pubEdit != GSConstBookmark.EDIT_POW_GROUP && pubEdit != GSConstBookmark.EDIT_POW_USER) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   BMK_PUBLIC_EDIT");
            sql.addSql(" where ");
            if (pubEdit == GSConstBookmark.EDIT_POW_GROUP) {
                sql.addSql("   GRP_SID=?");
                sql.addIntValue(grpSid);
            }
            if (pubEdit == GSConstBookmark.EDIT_POW_USER) {
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSid);
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
     * <p>Delete BMK_PUBLIC_EDIT
     * @return delete count
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
            sql.addSql("   BMK_PUBLIC_EDIT");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <p>Delete BMK_PUBLIC_EDIT
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
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
            sql.addSql("   BMK_PUBLIC_EDIT");
            sql.addSql(" where ");
            if (grpSid != -1) {
                sql.addSql("   GRP_SID=?");
                sql.addIntValue(grpSid);
            } else {
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSid);
            }
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
     * <p>Create BMK_PUBLIC_EDIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkPublicEditModel
     * @throws SQLException SQL実行例外
     */
    private BmkPublicEditModel __getBmkPublicEditFromRs(ResultSet rs) throws SQLException {
        BmkPublicEditModel bean = new BmkPublicEditModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBpeAuid(rs.getInt("BPE_AUID"));
        bean.setBpeAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BPE_ADATE")));
        bean.setBpeEuid(rs.getInt("BPE_EUID"));
        bean.setBpeEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BPE_EDATE")));
        return bean;
    }
}
