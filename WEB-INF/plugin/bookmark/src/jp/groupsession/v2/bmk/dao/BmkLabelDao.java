package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.model.BmkLabelModel;

/**
 * <p>BMK_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkLabelDao(Connection con) {
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
            sql.addSql("drop table BMK_LABEL");

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
            sql.addSql(" create table BMK_LABEL (");
            sql.addSql("   BLB_SID NUMBER(10,0) not null,");
            sql.addSql("   BLB_KBN NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   BLB_NAME varchar(20) not null,");
            sql.addSql("   BLB_AUID NUMBER(10,0) not null,");
            sql.addSql("   BLB_ADATE varchar(23) not null,");
            sql.addSql("   BLB_EUID NUMBER(10,0) not null,");
            sql.addSql("   BLB_EDATE varchar(23) not null,");
            sql.addSql("   primary key (BLB_SID)");
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
     * <p>Insert BMK_LABEL Data Bindding JavaBean
     * @param bean BMK_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_LABEL(");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BLB_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BLB_NAME,");
            sql.addSql("   BLB_AUID,");
            sql.addSql("   BLB_ADATE,");
            sql.addSql("   BLB_EUID,");
            sql.addSql("   BLB_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBlbSid());
            sql.addIntValue(bean.getBlbKbn());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addStrValue(bean.getBlbName());
            sql.addIntValue(bean.getBlbAuid());
            sql.addDateValue(bean.getBlbAdate());
            sql.addIntValue(bean.getBlbEuid());
            sql.addDateValue(bean.getBlbEdate());
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
     * <p>Update BMK_LABEL Data Bindding JavaBean
     * @param bean BMK_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" set ");
            sql.addSql("   BLB_KBN=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   BLB_NAME=?,");
            sql.addSql("   BLB_AUID=?,");
            sql.addSql("   BLB_ADATE=?,");
            sql.addSql("   BLB_EUID=?,");
            sql.addSql("   BLB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBlbKbn());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addStrValue(bean.getBlbName());
            sql.addIntValue(bean.getBlbAuid());
            sql.addDateValue(bean.getBlbAdate());
            sql.addIntValue(bean.getBlbEuid());
            sql.addDateValue(bean.getBlbEdate());
            //where
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
     * <p>Update BMK_LABEL Data Bindding JavaBean
     * @param bean BMK_LABEL Data Bindding JavaBean
     * @param blbSid ラベルSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkLabelModel bean, int blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" set ");
            sql.addSql("   BLB_NAME=?,");
            sql.addSql("   BLB_AUID=?,");
            sql.addSql("   BLB_ADATE=?");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBlbName());
            sql.addIntValue(bean.getBlbEuid());
            sql.addDateValue(bean.getBlbEdate());
            //where
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
     * <p>Select BMK_LABEL All Data
     * @return List in BMK_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkLabelModel> ret = new ArrayList<BmkLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BLB_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BLB_NAME,");
            sql.addSql("   BLB_AUID,");
            sql.addSql("   BLB_ADATE,");
            sql.addSql("   BLB_EUID,");
            sql.addSql("   BLB_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkLabelFromRs(rs));
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
     * <p>Select BMK_LABEL
     * @param blbSid BLB_SID
     * @return BMK_LABELModel
     * @throws SQLException SQL実行例外
     */
    public BmkLabelModel select(int blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BLB_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BLB_NAME,");
            sql.addSql("   BLB_AUID,");
            sql.addSql("   BLB_ADATE,");
            sql.addSql("   BLB_EUID,");
            sql.addSql("   BLB_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(blbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkLabelFromRs(rs);
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
     * <p>Select BMK_LABEL
     * @param blbSid BLB_SID
     * @return BMK_LABELModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BmkLabelModel> select(String[] blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkLabelModel> ret = new ArrayList<BmkLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BLB_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BLB_NAME,");
            sql.addSql("   BLB_AUID,");
            sql.addSql("   BLB_ADATE,");
            sql.addSql("   BLB_EUID,");
            sql.addSql("   BLB_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL");
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
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkLabelFromRs(rs));
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
     * <p>ラベル区分,ユーザSID,グループSID,ラベルSIDからラベル情報を取得
     * @param blbKbn ラベル区分
     * @param usrSid ユーザSID
     * @param grpSid グループSID
     * @param blbName ラベル名
     * @return BMK_LABELModel
     * @throws SQLException SQL実行例外
     */
    public BmkLabelModel select(int blbKbn, int usrSid, int grpSid, String blbName)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BLB_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BLB_NAME,");
            sql.addSql("   BLB_AUID,");
            sql.addSql("   BLB_ADATE,");
            sql.addSql("   BLB_EUID,");
            sql.addSql("   BLB_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_KBN=?");
            sql.addIntValue(blbKbn);
            if (blbKbn == GSConstBookmark.BMK_KBN_KOJIN) {
                sql.addSql(" and ");
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSid);
            }
            if (blbKbn == GSConstBookmark.BMK_KBN_GROUP) {
                sql.addSql(" and ");
                sql.addSql("   GRP_SID=?");
                sql.addIntValue(grpSid);
            }
            sql.addSql(" and ");
            sql.addSql("   BLB_NAME=?");
            sql.addStrValue(blbName);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkLabelFromRs(rs);
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
     * <p>ユーザSID,グループSIDからラベルSIDを取得
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @return BMK_LABELModel
     * @throws SQLException SQL実行例外
     */
    public String[] select(int grpSid, int usrSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BLB_SID");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL");
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
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("BLB_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[0]);
    }
    /**
     * <p>Select BMK_LABEL
     * @param bmkSid BMK_SID
     * @return BMK_LABELModel
     * @throws SQLException SQL実行例外
     */
    public String getBookmarkLabel(int bmkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BLB_NAME");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID in (");
            sql.addSql("     select");
            sql.addSql("       BLB_SID");
            sql.addSql("     from");
            sql.addSql("       BMK_BELONG_LABEL");
            sql.addSql("     where ");
            sql.addSql("       BMK_SID = ?");
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmkSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret += rs.getString("BLB_NAME");
                if (!rs.isLast()) {
                    ret += " ";
                }
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
     * <br>[機  能] ラベル情報の取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param labelSidList ラベルSIDリスト
     * @return ラベル情報リスト
     * @throws SQLException SQL実行例外
     */
    public List<BmkLabelModel> select(List<Integer> labelSidList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BmkLabelModel> ret = new ArrayList<BmkLabelModel>();
        con = getCon();

        if (labelSidList == null || labelSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BLB_SID,");
            sql.addSql("   BLB_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BLB_NAME,");
            sql.addSql("   BLB_AUID,");
            sql.addSql("   BLB_ADATE,");
            sql.addSql("   BLB_EUID,");
            sql.addSql("   BLB_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BLB_SID in (");
            for (int i = 0; i < labelSidList.size(); i++) {
                if (i != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(labelSidList.get(i));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkLabelFromRs(rs));
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
     * <p>Delete BMK_LABEL
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
            sql.addSql("   BMK_LABEL");
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
     * <p>Delete BMK_LABEL
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
            sql.addSql("   BMK_LABEL");
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
     * <p>Delete BMK_LABEL
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return 削除件数
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
            sql.addSql("   BMK_LABEL");
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
     * <p>Create BMK_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkLabelModel
     * @throws SQLException SQL実行例外
     */
    private BmkLabelModel __getBmkLabelFromRs(ResultSet rs) throws SQLException {
        BmkLabelModel bean = new BmkLabelModel();
        bean.setBlbSid(rs.getInt("BLB_SID"));
        bean.setBlbKbn(rs.getInt("BLB_KBN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setBlbName(rs.getString("BLB_NAME"));
        bean.setBlbAuid(rs.getInt("BLB_AUID"));
        bean.setBlbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BLB_ADATE")));
        bean.setBlbEuid(rs.getInt("BLB_EUID"));
        bean.setBlbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BLB_EDATE")));
        return bean;
    }
}
