package jp.groupsession.v2.ntp.dao;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpCommentModel;

/**
 * <p>NTP_COMMENT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpCommentDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpCommentDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpCommentDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpCommentDao(Connection con) {
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
            sql.addSql("drop table NTP_COMMENT");

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
            sql.addSql(" create table NTP_COMMENT (");
            sql.addSql("   NPC_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NPC_COMMENT varchar(1000) not null,");
            sql.addSql("   NPC_VIEW_KBN NUMBER(10,0) not null,");
            sql.addSql("   NPC_AUID NUMBER(10,0),");
            sql.addSql("   NPC_ADATE varchar(23) not null,");
            sql.addSql("   NPC_EUID NUMBER(10,0),");
            sql.addSql("   NPC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NPC_SID)");
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
     * <p>Insert NTP_COMMENT Data Bindding JavaBean
     * @param bean NTP_COMMENT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpCommentModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_COMMENT(");
            sql.addSql("   NPC_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPC_COMMENT,");
            sql.addSql("   NPC_VIEW_KBN,");
            sql.addSql("   NPC_AUID,");
            sql.addSql("   NPC_ADATE,");
            sql.addSql("   NPC_EUID,");
            sql.addSql("   NPC_EDATE");
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
            sql.addIntValue(bean.getNpcSid());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getNpcComment());
            sql.addIntValue(bean.getNpcViewKbn());
            sql.addIntValue(bean.getNpcAuid());
            sql.addDateValue(bean.getNpcAdate());
            sql.addIntValue(bean.getNpcEuid());
            sql.addDateValue(bean.getNpcEdate());
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
     * <p>Update NTP_COMMENT Data Bindding JavaBean
     * @param bean NTP_COMMENT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpCommentModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" set ");
            sql.addSql("   NIP_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   NPC_COMMENT=?,");
            sql.addSql("   NPC_VIEW_KBN=?,");
            sql.addSql("   NPC_AUID=?,");
            sql.addSql("   NPC_ADATE=?,");
            sql.addSql("   NPC_EUID=?,");
            sql.addSql("   NPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NPC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getNpcComment());
            sql.addIntValue(bean.getNpcViewKbn());
            sql.addIntValue(bean.getNpcAuid());
            sql.addDateValue(bean.getNpcAdate());
            sql.addIntValue(bean.getNpcEuid());
            sql.addDateValue(bean.getNpcEdate());
            //where
            sql.addIntValue(bean.getNpcSid());

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
     * <p>Select NTP_COMMENT All Data
     * @return List in NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpCommentModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpCommentModel> ret = new ArrayList<NtpCommentModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NPC_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPC_COMMENT,");
            sql.addSql("   NPC_VIEW_KBN,");
            sql.addSql("   NPC_AUID,");
            sql.addSql("   NPC_ADATE,");
            sql.addSql("   NPC_EUID,");
            sql.addSql("   NPC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_COMMENT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpCommentFromRs(rs));
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
     * <p>Select NTP_COMMENT
     * @param npcSid NPC_SID
     * @return NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public NtpCommentModel select(int npcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpCommentModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NPC_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPC_COMMENT,");
            sql.addSql("   NPC_VIEW_KBN,");
            sql.addSql("   NPC_AUID,");
            sql.addSql("   NPC_ADATE,");
            sql.addSql("   NPC_EUID,");
            sql.addSql("   NPC_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NPC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(npcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpCommentFromRs(rs);
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
     * <p>Select NTP_COMMENT
     * @param nipSid NIP_SID
     * @return NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpCommentModel> getNpcList(int nipSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpCommentModel npcMdl = null;
        ArrayList<NtpCommentModel> ret = new ArrayList<NtpCommentModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NPC_SID,");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPC_COMMENT,");
            sql.addSql("   NPC_VIEW_KBN,");
            sql.addSql("   NPC_AUID,");
            sql.addSql("   NPC_ADATE,");
            sql.addSql("   NPC_EUID,");
            sql.addSql("   NPC_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");
            sql.addSql(" order by NPC_ADATE ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                npcMdl = new NtpCommentModel();
                npcMdl = __getNtpCommentFromRs(rs);
                ret.add(npcMdl);
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
     * <p>コメントの件数を取得
     * @param nipSid NIP_SID
     * @return NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public int getNpcCount(int nipSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * 同じ日報にコメントをしているユーザのSIDを取得
     * @param nipSid NIP_SID
     * @param usrIds 除外するユーザ
     * @return NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getNpcUsrList(
            int nipSid, List<Integer> usrIds) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            for (int i = 0; i < usrIds.size(); i++) {
                if (usrIds.size() == 1 || (i == usrIds.size() - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" ) ");
            sql.addSql("group by USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            for (int usrId : usrIds) {
                sql.addIntValue(usrId);
            }

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] 指定した日報情報の日報コメント件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSidList NIP_SID
     * @return int コメントの件数
     * @throws SQLException SQL実行例外
     */
    public int getCommentCount(List<Integer> nipSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NIP_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID in (");

            for (int idx = 0; idx < nipSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(nipSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Delete NTP_COMMENT
     * @param npcSid NPC_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(int npcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NPC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(npcSid);

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
     * <p>Delete NTP_COMMENT
     * @param ntpSid NTP_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int deleteNtpData(int ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);

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
     * <p>Delete NTP_COMMENT
     * @param bdate NIP_DATE
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int deleteOldNippouCmt(UDate bdate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT NTC");
            sql.addSql(" where ");
            sql.addSql("  (NTC.NIP_SID) in");
            sql.addSql("   (");
            sql.addSql("    select NTD.NIP_SID from NTP_DATA NTD");
            sql.addSql("    where ");
            sql.addSql("    NTD.NIP_DATE <= ?");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bdate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create NTP_COMMENT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpCommentModel
     * @throws SQLException SQL実行例外
     */
    private NtpCommentModel __getNtpCommentFromRs(ResultSet rs) throws SQLException {
        NtpCommentModel bean = new NtpCommentModel();
        bean.setNpcSid(rs.getInt("NPC_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNpcComment(rs.getString("NPC_COMMENT"));
        bean.setNpcViewKbn(rs.getInt("NPC_VIEW_KBN"));
        bean.setNpcAuid(rs.getInt("NPC_AUID"));
        bean.setNpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPC_ADATE")));
        bean.setNpcEuid(rs.getInt("NPC_EUID"));
        bean.setNpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPC_EDATE")));
        return bean;
    }
    
    /**
    *
    * <br>[機  能] 指定SIDのデータサイズを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param sid 指定SID
    * @return データサイズ
    * @throws SQLException SQL実行時例外
    */
   public long getDiskSize(int sid) throws SQLException {

       long ret = 0;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select  ");
           sql.addSql("   sum(octet_length(NPC_COMMENT)) as NPC_COMMENT");
           sql.addSql(" from ");
           sql.addSql("   NTP_COMMENT ");
           sql.addSql(" where ");
           sql.addSql("   NPC_SID = ? ");
           sql.addIntValue(sid);
           
           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("NPC_COMMENT");
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
   * <br>[機  能] 指定SIDのデータサイズを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param sidList 指定SID
   * @return データサイズ
   * @throws SQLException SQL実行時例外
   */
  public long getDiskSize(List<Integer> sidList) throws SQLException {

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
          sql.addSql(" select  ");
          sql.addSql("   sum(octet_length(NPC_COMMENT)) as NPC_COMMENT");
          sql.addSql(" from ");
          sql.addSql("   NTP_COMMENT ");
          sql.addSql(" where ");
          sql.addSql("   NIP_SID in ");
          sql.addSql(inSidStr);
          
          pstmt = con.prepareStatement(sql.toSqlString());
          sql.setParameter(pstmt);
          log__.info(sql.toLogString());
          rs = pstmt.executeQuery();
          if (rs.next()) {
              ret = rs.getLong("NPC_COMMENT");
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
