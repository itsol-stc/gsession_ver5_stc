package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.model.BmkBelongLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_BELONG_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkBelongLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkBelongLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkBelongLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkBelongLabelDao(Connection con) {
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
            sql.addSql("drop table BMK_BELONG_LABEL");

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
            sql.addSql(" create table BMK_BELONG_LABEL (");
            sql.addSql("   BMK_SID NUMBER(10,0) not null,");
            sql.addSql("   BLB_SID NUMBER(10,0) not null,");
            sql.addSql("   BBL_AUID NUMBER(10,0) not null,");
            sql.addSql("   BBL_ADATE varchar(23) not null,");
            sql.addSql("   BBL_EUID NUMBER(10,0) not null,");
            sql.addSql("   BBL_EDATE varchar(23) not null,");
            sql.addSql("   primary key (BMK_SID,BLB_SID)");
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
     * <p>Insert BMK_BELONG_LABEL Data Bindding JavaBean
     * @param bean BMK_BELONG_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_BELONG_LABEL(");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BBL_AUID,");
            sql.addSql("   BBL_ADATE,");
            sql.addSql("   BBL_EUID,");
            sql.addSql("   BBL_EDATE");
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
            sql.addIntValue(bean.getBmkSid());
            sql.addIntValue(bean.getBlbSid());
            sql.addIntValue(bean.getBblAuid());
            sql.addDateValue(bean.getBblAdate());
            sql.addIntValue(bean.getBblEuid());
            sql.addDateValue(bean.getBblEdate());
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
     * <p>Insert BMK_BELONG_LABEL Data Bindding JavaBean
     * @param bean BmkBelongLabelModel
     * @param bmkSid ラベルSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkBelongLabelModel bean, String[] bmkSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (bmkSid == null || bmkSid.length < 1) {
            return;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_BELONG_LABEL(");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BBL_AUID,");
            sql.addSql("   BBL_ADATE,");
            sql.addSql("   BBL_EUID,");
            sql.addSql("   BBL_EDATE");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   BMK_BOOKMARK.BMK_SID,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addIntValue(bean.getBlbSid());
            sql.addIntValue(bean.getBblAuid());
            sql.addDateValue(bean.getBblAdate());
            sql.addIntValue(bean.getBblEuid());
            sql.addDateValue(bean.getBblEdate());
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where");
            sql.addSql("   BMK_BOOKMARK.BMK_SID in (");
            for (int i = 0; i < bmkSid.length; i++) {
                sql.addSql("  ?");
                sql.addIntValue(NullDefault.getInt(bmkSid[i], 0));
                if (i != bmkSid.length - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Update BMK_BELONG_LABEL Data Bindding JavaBean
     * @param bean BMK_BELONG_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" set ");
            sql.addSql("   BBL_AUID=?,");
            sql.addSql("   BBL_ADATE=?,");
            sql.addSql("   BBL_EUID=?,");
            sql.addSql("   BBL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBblAuid());
            sql.addDateValue(bean.getBblAdate());
            sql.addIntValue(bean.getBblEuid());
            sql.addDateValue(bean.getBblEdate());
            //where
            sql.addIntValue(bean.getBmkSid());
            sql.addIntValue(bean.getBlbSid());

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
     * <p>Update BMK_BELONG_LABEL Data Bindding JavaBean
     * @param bean BMK_BELONG_LABEL Data Bindding JavaBean
     * @param blbSid 更新対象ラベルSIDリスト
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkBelongLabelModel bean, String[] blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" set ");
            sql.addSql("   BLB_SID=?,");
            sql.addSql("   BBL_EUID=?,");
            sql.addSql("   BBL_EDATE=?");

            sql.addIntValue(bean.getBlbSid());
            sql.addIntValue(bean.getBblEuid());
            sql.addDateValue(bean.getBblEdate());

            sql.addSql(" where ");
            sql.addSql("   BLB_SID in (");
            for (int i = 0; i < blbSid.length; i++) {
                sql.addSql("  ?");
                sql.addStrValue(blbSid[i]);
                if (i != blbSid.length - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   )");

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
     * <p>Select BMK_BELONG_LABEL All Data
     * @return List in BMK_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkBelongLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkBelongLabelModel> ret = new ArrayList<BmkBelongLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BBL_AUID,");
            sql.addSql("   BBL_ADATE,");
            sql.addSql("   BBL_EUID,");
            sql.addSql("   BBL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_BELONG_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkBelongLabelFromRs(rs));
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
     * <p>Select BMK_BELONG_LABEL
     * @param bmkSid BMK_SID
     * @param blbSid BLB_SID
     * @return BMK_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public BmkBelongLabelModel select(int bmkSid, int blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkBelongLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BBL_AUID,");
            sql.addSql("   BBL_ADATE,");
            sql.addSql("   BBL_EUID,");
            sql.addSql("   BBL_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmkSid);
            sql.addIntValue(blbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkBelongLabelFromRs(rs);
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
     * <p>Select BMK_BELONG_LABEL
     * @param blbSid BLB_SID
     * @return BMK_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> select(String[] blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   distinct BMK_SID");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID in (");
            for (int i = 0; i < blbSid.length; i++) {
                sql.addSql("  ?");
                sql.addIntValue(NullDefault.getInt(blbSid[i], 0));
                if (i != blbSid.length - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("BMK_SID")));
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
     * <p>Delete BMK_BELONG_LABEL
     * @param bmkSid BMK_SID
     * @param blbSid BLB_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int bmkSid, int blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmkSid);
            sql.addIntValue(blbSid);

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
     * <p>Delete BMK_BELONG_LABEL
     * @param blbSid BLB_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(blbSid);

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
     * <p>Delete BMK_BELONG_LABEL
     * @param blbSid BLB_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(String[] blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID in (");

            for (int i = 0; i < blbSid.length; i++) {
                sql.addSql("  ?");
                sql.addIntValue(NullDefault.getInt(blbSid[i], 0));
                if (i != blbSid.length - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   ) ");
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
     * <p>ブックマークSIDからラベル付与情報を削除
     * @param bmkSid ブックマークSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBmkSid(int bmkSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmkSid);

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
     * <p>Create BMK_BELONG_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkBelongLabelModel
     * @throws SQLException SQL実行例外
     */
    private BmkBelongLabelModel __getBmkBelongLabelFromRs(ResultSet rs) throws SQLException {
        BmkBelongLabelModel bean = new BmkBelongLabelModel();
        bean.setBmkSid(rs.getInt("BMK_SID"));
        bean.setBlbSid(rs.getInt("BLB_SID"));
        bean.setBblAuid(rs.getInt("BBL_AUID"));
        bean.setBblAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BBL_ADATE")));
        bean.setBblEuid(rs.getInt("BBL_EUID"));
        bean.setBblEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BBL_EDATE")));
        return bean;
    }
}
