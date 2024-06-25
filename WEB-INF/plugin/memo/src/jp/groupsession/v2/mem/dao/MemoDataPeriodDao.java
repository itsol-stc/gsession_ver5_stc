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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.mem.model.MemoDataPeriodModel;

/**
 * <p>MEMO_DATA_PERIOD Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoDataPeriodDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoDataPeriodDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoDataPeriodDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoDataPeriodDao(Connection con) {
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
            sql.addSql("drop table MEMO_DATA_PERIOD");

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
            sql.addSql(" create table MEMO_DATA_PERIOD (");
            sql.addSql("   MEM_SID bigint not null,");
            sql.addSql("   MDP_DEL_DATE Date not null,");
            sql.addSql("   primary key (MEM_SID)");
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
     * <p>Insert MEMO_DATA_PERIOD Data Bindding JavaBean
     * @param bean MEMO_DATA_PERIOD Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoDataPeriodModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_DATA_PERIOD(");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MDP_DEL_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getMemSid());
            sql.addDateValue(bean.getMdpDelDate());
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
     * <p>Update MEMO_DATA_PERIOD Data Bindding JavaBean
     * @param bean MEMO_DATA_PERIOD Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoDataPeriodModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_DATA_PERIOD");
            sql.addSql(" set ");
            sql.addSql("   MDP_DEL_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getMdpDelDate());
            //where
            sql.addLongValue(bean.getMemSid());

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
     * <p>Select MEMO_DATA_PERIOD All Data
     * @return List in MEMO_DATA_PERIODModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoDataPeriodModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoDataPeriodModel> ret = new ArrayList<MemoDataPeriodModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MDP_DEL_DATE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_DATA_PERIOD");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoDataPeriodFromRs(rs));
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
     * <p>Select MEMO_DATA_PERIOD
     * @param memSid MEM_SID
     * @return MEMO_DATA_PERIODModel
     * @throws SQLException SQL実行例外
     */
    public MemoDataPeriodModel select(long memSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoDataPeriodModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MDP_DEL_DATE");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA_PERIOD");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoDataPeriodFromRs(rs);
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
     * <br>[機  能] 削除期限 <= 現在の日付となるメモSIDリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param todate 日付
     * @return 削除対象のメモ一覧
     * @throws SQLException SQL実行例外
     */
    public List<Long> getPeriodMemoList(UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Long> ret  = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MEM_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA_PERIOD");
            sql.addSql(" where ");
            sql.addSql("   MDP_DEL_DATE <= ?");
            sql.addDateValue(todate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("MEM_SID"));
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
     * <p>Delete MEMO_DATA_PERIOD
     * @param memSid MEM_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(long memSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA_PERIOD");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);

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
     * <br>[機  能] メモSIDを指定しメモ削除期限情報を削除します
     * <br>[解  説]
     * <br>[備  考]
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
            sql.addSql("   MEMO_DATA_PERIOD");
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
     * <p>Create MEMO_DATA_PERIOD Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoDataPeriodModel
     * @throws SQLException SQL実行例外
     */
    private MemoDataPeriodModel __getMemoDataPeriodFromRs(ResultSet rs) throws SQLException {
        MemoDataPeriodModel bean = new MemoDataPeriodModel();
        bean.setMemSid(rs.getLong("MEM_SID"));
        bean.setMdpDelDate(UDate.getInstanceTimestamp(rs.getTimestamp("MDP_DEL_DATE")));
        return bean;
    }
}
