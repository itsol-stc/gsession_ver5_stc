package jp.groupsession.v2.anp.dao;

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
import jp.groupsession.v2.anp.model.AnpDatausedSumModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>ANP_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpDatausedSumDao(Connection con) {
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
            sql.addSql("drop table ANP_DATAUSED_SUM");

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
            sql.addSql(" create table ANP_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   ANP_HDATA_SIZE bigint not null,");
            sql.addSql("   ANP_JDATA_SIZE bigint not null,");
            sql.addSql("   ANP_MTEP_SIZE bigint not null");
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
     * <p>Insert ANP_DATAUSED_SUM Data Bindding JavaBean
     * @param bean ANP_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   ANP_HDATA_SIZE,");
            sql.addSql("   ANP_JDATA_SIZE,");
            sql.addSql("   ANP_MTEP_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getAnpHdataSize());
            sql.addLongValue(bean.getAnpJdataSize());
            sql.addLongValue(bean.getAnpMtepSize());
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
     * <p>Update ANP_DATAUSED_SUM Data Bindding JavaBean
     * @param bean ANP_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   ANP_HDATA_SIZE=?,");
            sql.addSql("   ANP_JDATA_SIZE=?,");
            sql.addSql("   ANP_MTEP_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getAnpHdataSize());
            sql.addLongValue(bean.getAnpJdataSize());
            sql.addLongValue(bean.getAnpMtepSize());

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
     * <p>Select ANP_DATAUSED_SUM All Data
     * @return List in ANP_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AnpDatausedSumModel> ret = new ArrayList<AnpDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   ANP_HDATA_SIZE,");
            sql.addSql("   ANP_JDATA_SIZE,");
            sql.addSql("   ANP_MTEP_SIZE");
            sql.addSql(" from ");
            sql.addSql("   ANP_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpDatausedSumFromRs(rs));
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
    public AnpDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   ANP_HDATA_SIZE,");
            sql.addSql("   ANP_JDATA_SIZE,");
            sql.addSql("   ANP_MTEP_SIZE");
            sql.addSql(" from ");
            sql.addSql("   ANP_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpDatausedSumFromRs(rs);
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
    public AnpDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(ANP_HDATA_SIZE) as SUM_ANP_HDATA_SIZE,");
            sql.addSql("   sum(ANP_JDATA_SIZE) as SUM_ANP_JDATA_SIZE,");
            sql.addSql("   sum(ANP_MTEP_SIZE) as SUM_ANP_MTEP_SIZE");
            sql.addSql(" from ");
            sql.addSql("   ANP_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new AnpDatausedSumModel();
                ret.setAnpHdataSize(rs.getLong("SUM_ANP_HDATA_SIZE"));
                ret.setAnpJdataSize(rs.getLong("SUM_ANP_JDATA_SIZE"));
                ret.setAnpMtepSize(rs.getLong("SUM_ANP_MTEP_SIZE"));
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
            sql.addSql("   ANP_DATAUSED_SUM");

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
    *
    * <br>[機  能] データ削除時の差分値の作成
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList 削除対象SID
    * @throws SQLException SQL実行時例外
    */
   public void insertMailDelDiff(List<Integer> sidList) throws SQLException {
       __insertMailDiff(sidList, -1);
   }
   /**
    *
    * <br>[機  能] データ追加時の差分値の作成
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList 追加対象SID
    * @throws SQLException SQL実行時例外
    */
   public void insertMailAddDiff(List<Integer> sidList) throws SQLException {
       __insertMailDiff(sidList, 1);
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
   private void __insertMailDiff(List<Integer> sidList, int keisu) throws SQLException {

       if (sidList == null || sidList.size() == 0) {
           return;
       }

       Connection con = null;
       con = getCon();

       AnpMtempDao amDao = new AnpMtempDao(con);

       AnpDatausedSumModel mdl = new AnpDatausedSumModel();
       mdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
       mdl.setAnpHdataSize(0);
       mdl.setAnpJdataSize(0);
       mdl.setAnpMtepSize(keisu * amDao.getDiskSize(sidList));
       insert(mdl);
   }
   
   /**
   *
   * <br>[機  能] データ削除時の差分値の作成
   * <br>[解  説]
   * <br>[備  考]
   * @param sidList 削除対象SID
   * @throws SQLException SQL実行時例外
   */
  public void insertHdataDelDiff(List<Integer> sidList) throws SQLException {
      __insertHdataDiff(sidList, -1);
  }
  /**
   *
   * <br>[機  能] データ追加時の差分値の作成
   * <br>[解  説]
   * <br>[備  考]
   * @param sidList 追加対象SID
   * @throws SQLException SQL実行時例外
   */
  public void insertHdataAddDiff(List<Integer> sidList) throws SQLException {
      __insertHdataDiff(sidList, 1);
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
  private void __insertHdataDiff(List<Integer> sidList, int keisu) throws SQLException {

      if (sidList == null || sidList.size() == 0) {
          return;
      }

      Connection con = null;
      con = getCon();

      AnpHdataDao ahDao = new AnpHdataDao(con);

      AnpDatausedSumModel mdl = new AnpDatausedSumModel();
      mdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
      mdl.setAnpHdataSize(keisu * ahDao.getDiskSize(sidList));
      mdl.setAnpJdataSize(0);
      mdl.setAnpMtepSize(0);
      insert(mdl);
  }
  
  /**
  *
  * <br>[機  能] データ削除時の差分値の作成
  * <br>[解  説]
  * <br>[備  考]
  * @param aphSid 安否確認SID
  * @throws SQLException SQL実行時例外
  */
 public void insertJdataDelDiff(int aphSid) throws SQLException {
     List<Integer> sidList = null;
     __insertJdataDiff(sidList, -1, aphSid, 2);
 }
 
 /**
 *
 * <br>[機  能] データ削除時の差分値の作成
 * <br>[解  説]
 * <br>[備  考]
 * @param sidList 追加対象SID
 * @param aphSid 安否確認SID
 * @throws SQLException SQL実行時例外
 */
public void insertJdataDelDiff(List<Integer> sidList, int aphSid) throws SQLException {
    __insertJdataDiff(sidList, -1, aphSid, 1);
}
 
 /**
  *
  * <br>[機  能] データ追加時の差分値の作成
  * <br>[解  説]
  * <br>[備  考]
  * @param sidList 追加対象SID
  * @param aphSid 安否確認SID
  * @throws SQLException SQL実行時例外
  */
 public void insertJdataAddDiff(List<Integer> sidList, int aphSid) throws SQLException {
     __insertJdataDiff(sidList, 1, aphSid, 1);
 }

 /**
 *
 * <br>[機  能] 差分値の作成
 * <br>[解  説]
 * <br>[備  考]
 * @param sidList 対象SID
 * @param keisu 追加時：１ 削除時 -1
 * @param aphSid 安否確認SID
 * @param kbn 取得SQL判定
 * @throws SQLException SQL実行時例外
 */
 private void __insertJdataDiff(List<Integer> sidList, int keisu, int aphSid, int kbn) 
         throws SQLException {

     Connection con = null;
     con = getCon();

     AnpJdataDao ajDao = new AnpJdataDao(con);

     AnpDatausedSumModel mdl = new AnpDatausedSumModel();
     mdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
     mdl.setAnpHdataSize(0);
     if (kbn == 1) {
         mdl.setAnpJdataSize(keisu * ajDao.getDiskSize(sidList, aphSid));         
     } else {
         mdl.setAnpJdataSize(keisu * ajDao.getDiskSizeDelete(aphSid));
     }
     
     mdl.setAnpMtepSize(0);
     insert(mdl);
 }

    /**
     * <p>Create ANP_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private AnpDatausedSumModel __getAnpDatausedSumFromRs(ResultSet rs) throws SQLException {
        AnpDatausedSumModel bean = new AnpDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setAnpHdataSize(rs.getLong("ANP_HDATA_SIZE"));
        bean.setAnpJdataSize(rs.getLong("ANP_JDATA_SIZE"));
        bean.setAnpMtepSize(rs.getLong("ANP_MTEP_SIZE"));
        return bean;
    }
}
