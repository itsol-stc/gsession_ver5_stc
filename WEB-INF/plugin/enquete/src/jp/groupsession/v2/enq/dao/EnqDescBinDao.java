package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.enq.model.EnqDescBinModel;

/**
 * <p>ENQ_DESC_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqDescBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqDescBinDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqDescBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqDescBinDao(Connection con) {
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
            sql.addSql("drop table ENQ_DESC_BIN");

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
            sql.addSql(" create table ENQ_DESC_BIN (");
            sql.addSql("   EMN_SID bigint not null,");
            sql.addSql("   EDB_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (EMN_SID,EDB_SID)");
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
     * <p>Insert ENQ_DESC_BIN Data Bindding JavaBean
     * @param bean ENQ_DESC_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqDescBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_DESC_BIN(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EDB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEdbSid());
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
     * <p>Update ENQ_DESC_BIN Data Bindding JavaBean
     * @param bean ENQ_DESC_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqDescBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_DESC_BIN");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EDB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEdbSid());

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
     * <p>Select ENQ_DESC_BIN All Data
     * @return List in ENQ_DESC_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqDescBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqDescBinModel> ret = new ArrayList<EnqDescBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EDB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   ENQ_DESC_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqDescBinFromRs(rs));
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
     * <p>Select ENQ_DESC_BIN
     * @param emnSid EMN_SID
     * @param edbSid EDB_SID
     * @return ENQ_DESC_BINModel
     * @throws SQLException SQL実行例外
     */
    public EnqDescBinModel select(long emnSid, int edbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqDescBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EDB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   ENQ_DESC_BIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EDB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(edbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqDescBinFromRs(rs);
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
     * <p>Delete ENQ_DESC_BIN
     * @param emnSid EMN_SID
     * @param edbSid EDB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long emnSid, int edbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_DESC_BIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EDB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(edbSid);

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
     * <br>[機  能] 指定したアンケートに関連する説明添付情報を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteDescBin(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_DESC_BIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);

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
     * <br>[機  能] 指定したアンケートに関連する説明添付情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return 添付情報一覧
     * @throws SQLException
     */
    public List<EnqDescBinModel> selectDescBinList(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqDescBinModel> ret = new ArrayList<EnqDescBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EDB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   ENQ_DESC_BIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addLongValue(emnSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getEnqDescBinFromRs(rs));
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
     * <p>アンケート_説明添付情報の一括登録を行う
     * @param emnSid アンケートSID
     * @param edbList ファイルSIDリスト
     * @param bodyBinSidList バイナリSIDの一覧
     * @throws SQLException SQL実行例外
     */
    public void insertEnqDescBinData(long emnSid, List<String> edbList, List<String> bodyBinSidList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql(" ENQ_DESC_BIN(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EDB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   " + emnSid + ",");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            String logString = sql.toLogString();
            pstmt = con.prepareStatement(sql.toSqlString());

            for (int i = 0; i < bodyBinSidList.size(); ++i) {
                pstmt.setInt(1, Integer.parseInt(edbList.get(i)));
                pstmt.setLong(2, Long.parseLong(bodyBinSidList.get(i)));
                logString = StringUtil.substitute(logString, "?", edbList.get(i));
                log__.info(logString);
                logString = StringUtil.substitute(logString, "?", bodyBinSidList.get(i));
                log__.info(logString);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したアンケート_説明添付情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSidList アンケートSID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Long> emnSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ENQ_DESC_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   ENQ_DESC_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   ENQ_DESC_BIN.EMN_SID in (");

            for (int idx = 0; idx < emnSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(emnSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <p>Create ENQ_DESC_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqDescBinModel
     * @throws SQLException SQL実行例外
     */
    private EnqDescBinModel __getEnqDescBinFromRs(ResultSet rs) throws SQLException {
        EnqDescBinModel bean = new EnqDescBinModel();
        bean.setEmnSid(rs.getLong("EMN_SID"));
        bean.setEdbSid(rs.getInt("EDB_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
