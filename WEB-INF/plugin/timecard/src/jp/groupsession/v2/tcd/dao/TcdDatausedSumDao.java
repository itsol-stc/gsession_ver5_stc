package jp.groupsession.v2.tcd.dao;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.tcd.model.TcdDatausedSumModel;

/**
 * <p>TCD_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdDatausedSumDao(Connection con) {
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
            sql.addSql("drop table TCD_DATAUSED_SUM");

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
            sql.addSql(" create table TCD_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   TCD_TCDATA_SIZE bigint not null");
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
     * <p>Insert TCD_DATAUSED_SUM Data Bindding JavaBean
     * @param bean TCD_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   TCD_TCDATA_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getTcdTcdataSize());
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
     * <p>Update TCD_DATAUSED_SUM Data Bindding JavaBean
     * @param bean TCD_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   TCD_TCDATA_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getTcdTcdataSize());

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
     * <p>Select TCD_DATAUSED_SUM All Data
     * @return List in TCD_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdDatausedSumModel> ret = new ArrayList<TcdDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   TCD_TCDATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   TCD_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdDatausedSumFromRs(rs));
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
     * <br>[機  能] 使用データサイズの「集計」を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの「集計」
     * @throws SQLException
     */
    public TcdDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   TCD_TCDATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   TCD_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdDatausedSumFromRs(rs);
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
     * <br>[機  能] 使用データサイズの集計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの集計
     * @throws SQLException
     */
    public TcdDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(TCD_TCDATA_SIZE) as SUM_TCD_TCDATA_SIZE");
            sql.addSql(" from ");
            sql.addSql("   TCD_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new TcdDatausedSumModel();
                ret.setTcdTcdataSize(rs.getLong("SUM_TCD_TCDATA_SIZE"));
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
     * <p>Delete TCD_DATAUSED_SUM
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
            sql.addSql("   TCD_DATAUSED_SUM");

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
     * <p>Create TCD_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private TcdDatausedSumModel __getTcdDatausedSumFromRs(ResultSet rs) throws SQLException {
        TcdDatausedSumModel bean = new TcdDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setTcdTcdataSize(rs.getLong("TCD_TCDATA_SIZE"));
        return bean;
    }
    
    
    /**
    *
    * <br>[機  能] データ削除時の差分値の作成
    * <br>[解  説]
    * <br>[備  考]
    * @param dateList 削除対象日付
    * @param usrSid ユーザSID
    * @throws SQLException SQL実行時例外
    */
   public void insertDelDiff(List<UDate> dateList, int usrSid) throws SQLException {
       __insertDiff(dateList, -1, usrSid);
   }
   
   /**
    *
    * <br>[機  能] データ追加時の差分値の作成
    * <br>[解  説]
    * <br>[備  考]
    * @param dateList 追加対象日付
    * @param usrSid ユーザSID
    * @throws SQLException SQL実行時例外
    */
   public void insertAddDiff(List<UDate> dateList, int usrSid) throws SQLException {
       __insertDiff(dateList, 1, usrSid);
   }
   
   /**
   *
   * <br>[機  能] 差分値の作成
   * <br>[解  説]
   * <br>[備  考]
   * @param dateList 対象日付
   * @param keisu 追加時：１ 削除時 -1
   * @param usrSid ユーザSID
   * @throws SQLException SQL実行時例外
   */
   private void __insertDiff(List<UDate> dateList, int keisu, int usrSid) throws SQLException {

       if (dateList == null || dateList.size() == 0) {
           return;
       }

       Connection con = null;
       con = getCon();

       TcdTcdataDao tcdDao = new TcdTcdataDao(con);

       TcdDatausedSumModel mdl = new TcdDatausedSumModel();
       mdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
       mdl.setTcdTcdataSize(keisu * tcdDao.getDataSize(dateList, usrSid));

       insert(mdl);
   }
}
