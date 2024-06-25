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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.mem.model.MemoDatausedSumModel;

/**
 * <p>MEMO_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoDatausedSumDao(Connection con) {
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
            sql.addSql("drop table MEMO_DATAUSED_SUM");

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
            sql.addSql(" create table MEMO_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   MEMO_DATA_SIZE bigint not null");
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
     * <p>Insert MEMO_DATAUSED_SUM Data Bindding JavaBean
     * @param bean MEMO_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   MEMO_DATA_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getMemoDataSize());
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
     * <p>Update MEMO_DATAUSED_SUM Data Bindding JavaBean
     * @param bean MEMO_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   MEMO_DATA_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getMemoDataSize());

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
     * <p>Select MEMO_DATAUSED_SUM All Data
     * @return List in MEMO_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoDatausedSumModel> ret = new ArrayList<MemoDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   MEMO_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoDatausedSumFromRs(rs));
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
     * <p>Create MEMO_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private MemoDatausedSumModel __getMemoDatausedSumFromRs(ResultSet rs) throws SQLException {
        MemoDatausedSumModel bean = new MemoDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setMemoDataSize(rs.getLong("MEMO_DATA_SIZE"));
        return bean;
    }

    /**
     * <br>[機  能] 使用データサイズの「集計」データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの「集計」
     * @throws SQLException
     */
    public MemoDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   MEMO_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoDatausedSumFromRs(rs);
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
     *
     * <br>[機  能] データ削除時の差分値の作成
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList 削除対象SID
     * @throws SQLException SQL実行時例外
     */
    public void insertDelDiff(List<Long> sidList) throws SQLException {
        __insertDiff(sidList, -1);
    }
    /**
     *
     * <br>[機  能] データ追加時の差分値の作成
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList 追加対象SID
     * @throws SQLException SQL実行時例外
     */
    public void insertAddDiff(List<Long> sidList) throws SQLException {
        __insertDiff(sidList, 1);
    }

    /**
    *
    * <br>[機  能] 差分値の作成
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList 対象SID
    * @param keisu 追加時：１ 削除時 -1
    * @throws SQLException SQL実行時例外
    */
    private void __insertDiff(List<Long> sidList, int keisu) throws SQLException {

        if (sidList == null || sidList.size() == 0) {
            return;
        }

        Connection con = null;
        con = getCon();

        MemoDataDao memDao = new MemoDataDao(con);
        MemoBinDao mbinDao = new MemoBinDao(con);

        MemoDatausedSumModel mdl = new MemoDatausedSumModel();
        mdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        mdl.setMemoDataSize(keisu
                * (memDao.getMemSize(sidList) + mbinDao.getBelongBinSize(sidList)));

        insert(mdl);
    }

    /**
     * <br>[機  能] 使用データサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの合計
     * @throws SQLException
     */
    public MemoDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(String.format("  %d as SUM_TYPE,",
                    GSConst.USEDDATA_SUMTYPE_TOTAL));
            sql.addSql("  sum(MEMO_DATA_SIZE) as MEMO_DATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoDatausedSumFromRs(rs);
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
     * <p>Delete MEMO_DATAUSED_SUM
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
            sql.addSql("   MEMO_DATAUSED_SUM");

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

}
