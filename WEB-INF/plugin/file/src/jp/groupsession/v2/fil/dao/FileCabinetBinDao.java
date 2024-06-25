package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.model.FileCabinetBinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>FILE_CABINET_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCabinetBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCabinetBinDao.class);

    /**
     * <p>Default Constructor
     */
    public FileCabinetBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileCabinetBinDao(Connection con) {
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
            sql.addSql("drop table FILE_CABINET_BIN");

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
            sql.addSql(" create table FILE_CABINET_BIN (");
            sql.addSql("   FCB_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (FCB_SID,BIN_SID)");
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
     * <p>Insert FILE_CABINET_BIN Data Bindding JavaBean
     * @param bean FILE_CABINET_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileCabinetBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET_BIN(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
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
     * キャビネット添付情報を登録
     * @param fcbSid キャビネットSID
     * @param sids バイナリーSID
     * @throws SQLException SQL実行時例外
     */
    public void insert(int fcbSid, List<String> sids) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET_BIN(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (sids != null) {
                for (String sid : sids) {
                    sql.addIntValue(fcbSid);
                    sql.addLongValue(Long.parseLong(sid));
                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                    sql.clearValue();
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update FILE_CABINET_BIN Data Bindding JavaBean
     * @param bean FILE_CABINET_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileCabinetBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getFcbSid());
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
     * <p>Select FILE_CABINET_BIN All Data
     * @return List in FILE_CABINET_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<FileCabinetBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetBinModel> ret = new ArrayList<FileCabinetBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCabinetBinFromRs(rs));
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
     * <p>キャビネットSIDから添付ファイルのバイナリーSIDを取得
     * @param fcbSid キャビネットSID
     * @return ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectBinSids(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET_BIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("BIN_SID"));
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
     * <p>Select FILE_CABINET_BIN
     * @param fcbSid FCB_SID
     * @param binSid BIN_SID
     * @return FILE_CABINET_BINModel
     * @throws SQLException SQL実行例外
     */
    public FileCabinetBinModel select(int fcbSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCabinetBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_BIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileCabinetBinFromRs(rs);
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
     * <br>[機  能] 添付ファイル情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return List in BIN_SID
     * @throws SQLException SQL実行例外
     */
    public String[] getBinList(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> binSidList = new ArrayList<String>();
        String[] ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_CABINET_BIN.BIN_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_BIN");
            sql.addSql(" where");
            sql.addSql("   FILE_CABINET_BIN.FCB_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   FILE_CABINET_BIN.BIN_SID asc");
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                binSidList.add(String.valueOf(rs.getLong("BIN_SID")));
            }

            ret = (String[]) binSidList.toArray(new String[binSidList.size()]);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete FILE_CABINET_BIN
     * @param fcbSid FCB_SID
     * @param binSid BIN_SID
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fcbSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_BIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
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
     * <p>Delete FILE_CABINET_BIN
     * @param fcbSid FCB_SID
     * @throws SQLException SQL実行例外
     * @return int 件数
     */
    public int delete(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_BIN");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

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
     * <p>Create FILE_CABINET_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCabinetBinModel
     * @throws SQLException SQL実行例外
     */
    private FileCabinetBinModel __getFileCabinetBinFromRs(ResultSet rs) throws SQLException {
        FileCabinetBinModel bean = new FileCabinetBinModel();
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
