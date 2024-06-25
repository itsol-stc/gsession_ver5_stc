package jp.groupsession.v2.mem.dao;

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
import jp.groupsession.v2.mem.model.MemoBelongLabelModel;

/**
 * <p>MEMO_BELONG_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoBelongLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoBelongLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoBelongLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoBelongLabelDao(Connection con) {
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
            sql.addSql("drop table MEMO_BELONG_LABEL");

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
            sql.addSql(" create table MEMO_BELONG_LABEL (");
            sql.addSql("   MEM_SID bigint not null,");
            sql.addSql("   MML_SID integer not null,");
            sql.addSql("   primary key (MEM_SID,MML_SID)");
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
     * <p>Insert MEMO_BELONG_LABEL Data Bindding JavaBean
     * @param bean MEMO_BELONG_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_BELONG_LABEL(");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MML_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getMemSid());
            sql.addIntValue(bean.getMmlSid());
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
     * <p>Update MEMO_BELONG_LABEL Data Bindding JavaBean
     * @param bean MEMO_BELONG_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");
            sql.addSql(" and");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addLongValue(bean.getMemSid());
            sql.addIntValue(bean.getMmlSid());

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
     * <p>Select MEMO_BELONG_LABEL All Data
     * @return List in MEMO_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoBelongLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoBelongLabelModel> ret = new ArrayList<MemoBelongLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MML_SID");
            sql.addSql(" from ");
            sql.addSql("   MEMO_BELONG_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoBelongLabelFromRs(rs));
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
     * <p>Select MEMO_BELONG_LABEL
     * @param memSid MEM_SID
     * @param mmlSid MML_SID
     * @return MEMO_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public MemoBelongLabelModel select(long memSid, int mmlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoBelongLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MML_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");
            sql.addSql(" and");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);
            sql.addIntValue(mmlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoBelongLabelFromRs(rs);
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
     * <p>Delete MEMO_BELONG_LABEL
     * @param memSid MEM_SID
     * @param mmlSid MML_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(long memSid, int mmlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");
            sql.addSql(" and");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);
            sql.addIntValue(mmlSid);

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
     * <p>Delete MEMO_BELONG_LABEL
     * @param mmlSid MML_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(int mmlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mmlSid);

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
     * <p>メモSIDを指定しメモラベル付与情報を削除します
     * @param mems メモSIDリスト
     * @return 削除数
     * @throws SQLException SQL実行例外
     */
    public int delete(ArrayList<Long> mems) throws SQLException {

        int count = 0;
        if (mems.size() == 0) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID in(");
            if (mems.size() > 0) {
                sql.addSql("   ?");
                sql.addLongValue(mems.get(0));
            }
            for (int i = 1; i < mems.size(); i++) {
                sql.addSql("   ,?");
                sql.addLongValue(mems.get(i));
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <p>Create MEMO_BELONG_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoBelongLabelModel
     * @throws SQLException SQL実行例外
     */
    private MemoBelongLabelModel __getMemoBelongLabelFromRs(ResultSet rs) throws SQLException {
        MemoBelongLabelModel bean = new MemoBelongLabelModel();
        bean.setMemSid(rs.getLong("MEM_SID"));
        bean.setMmlSid(rs.getInt("MML_SID"));
        return bean;
    }
}
