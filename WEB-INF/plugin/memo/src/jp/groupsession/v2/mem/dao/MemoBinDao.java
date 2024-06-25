package jp.groupsession.v2.mem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.mem.model.MemoBinModel;

/**
 * <p>MEMO_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoBinDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoBinDao(Connection con) {
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
            sql.addSql("drop table MEMO_BIN");

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
            sql.addSql(" create table MEMO_BIN (");
            sql.addSql("   MEM_SID bigint not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (MEM_SID,BIN_SID)");
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
     * <p>Insert MEMO_BIN Data Bindding JavaBean
     * @param bean MEMO_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_BIN(");
            sql.addSql("   MEM_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getMemSid());
            sql.addLongValue(bean.getBinSid());
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
     * <p>Update MEMO_BIN Data Bindding JavaBean
     * @param bean MEMO_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addLongValue(bean.getMemSid());
            sql.addLongValue(bean.getBinSid());

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
     * <p>Select MEMO_BIN All Data
     * @return List in MEMO_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoBinModel> ret = new ArrayList<MemoBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MEM_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   MEMO_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoBinFromRs(rs));
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
     * <p>Select MEMO_BIN
     * @param memSid MEM_SID
     * @param binSid BIN_SID
     * @return MEMO_BINModel
     * @throws SQLException SQL実行例外
     */
    public MemoBinModel select(long memSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEM_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_BIN");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoBinFromRs(rs);
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
     * <br>[機  能] 添付ファイルが有るメモのSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param targetSidList 対象メモのSIDのリスト
     * @return 添付ファイルが有るメモのSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Long> getMemoBinList(
            List<Long> targetSidList) throws SQLException {

        List<Long> ret = new ArrayList<Long>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   MEM_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_BIN");
            if (!targetSidList.isEmpty()) {
                sql.addSql(" where");
                sql.addSql("   MEM_SID in (");
                for (int i = 0; i < targetSidList.size(); i++) {
                    sql.addSql("   ?");
                    sql.addLongValue(targetSidList.get(i));
                    if (i < targetSidList.size() - 1) {
                        sql.addSql(",");
                    }
                }
                sql.addSql(")");
            }

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
     * <p>Delete MEMO_BIN
     * @param memSid MEM_SID
     * @param binSid BIN_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(long memSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_BIN");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);
            sql.addLongValue(binSid);

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
     * <br>[機  能] メモSIDを指定しメモバイナリ情報を削除します
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
            sql.addSql("   MEMO_BIN");
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
     * <p>Create MEMO_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoBinModel
     * @throws SQLException SQL実行例外
     */
    private MemoBinModel __getMemoBinFromRs(ResultSet rs) throws SQLException {
        MemoBinModel bean = new MemoBinModel();
        bean.setMemSid(rs.getLong("MEM_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }

    /**
     *
     * <br>[機  能] 指定SIDのデータサイズを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList 指定SID
     * @return データサイズ
     * @throws SQLException SQL実行時例外
     */
    public Long getBelongBinSize(List<Long> sidList) throws SQLException {

        if (sidList == null || sidList.size() == 0) {
            return (long) 0;
        }
        String inSidStr = sidList.stream()
                .map(sid -> sid.toString())
                .collect(Collectors.joining(",", "(", ")"));
        long ret = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as SIZE ");
            sql.addSql(" from ");
            sql.addSql("   MEMO_BIN ");
            sql.addSql(" inner join ");
            sql.addSql("   CMN_BINF ");
            sql.addSql(" on ");
            sql.addSql("   MEMO_BIN.BIN_SID = CMN_BINF.BIN_SID ");
            sql.addSql(" where ");
            sql.addSql("   MEMO_BIN.MEM_SID in");
            sql.addSql(inSidStr);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret += rs.getInt("SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

}
