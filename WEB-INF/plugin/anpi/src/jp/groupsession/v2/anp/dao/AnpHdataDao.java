package jp.groupsession.v2.anp.dao;

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
import jp.groupsession.v2.anp.model.AnpHdataModel;

/**
 * <p>ANP_HDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpHdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpHdataDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpHdataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpHdataDao(Connection con) {
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
            sql.addSql("drop table ANP_HDATA");

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
            sql.addSql(" create table ANP_HDATA (");
            sql.addSql("   APH_SID integer not null,");
            sql.addSql("   APH_SUBJECT varchar(60),");
            sql.addSql("   APH_TEXT1 varchar(3000),");
            sql.addSql("   APH_TEXT2 varchar(3000),");
            sql.addSql("   APH_HUID integer not null,");
            sql.addSql("   APH_HDATE timestamp not null,");
            sql.addSql("   APH_SUID integer,");
            sql.addSql("   APH_SDATE timestamp,");
            sql.addSql("   APH_SCOUNT integer not null,");
            sql.addSql("   APH_END_FLG integer not null,");
            sql.addSql("   APH_EUID integer,");
            sql.addSql("   APH_EDATE timestamp,");
            sql.addSql("   APH_KNREN_FLG integer not null,");
            sql.addSql("   primary key (APH_SID)");
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
     * <p>Insert ANP_HDATA Data Bindding JavaBean
     * @param bean ANP_HDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpHdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_HDATA(");
            sql.addSql("   APH_SID,");
            sql.addSql("   APH_SUBJECT,");
            sql.addSql("   APH_TEXT1,");
            sql.addSql("   APH_TEXT2,");
            sql.addSql("   APH_HUID,");
            sql.addSql("   APH_HDATE,");
            sql.addSql("   APH_SUID,");
            sql.addSql("   APH_SDATE,");
            sql.addSql("   APH_SCOUNT,");
            sql.addSql("   APH_END_FLG,");
            sql.addSql("   APH_EUID,");
            sql.addSql("   APH_EDATE,");
            sql.addSql("   APH_KNREN_FLG,");
            sql.addSql("   APH_VIEW_MAIN");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAphSid());
            sql.addStrValue(bean.getAphSubject());
            sql.addStrValue(bean.getAphText1());
            sql.addStrValue(bean.getAphText2());
            sql.addIntValue(bean.getAphHuid());
            sql.addDateValue(bean.getAphHdate());
            sql.addIntValue(bean.getAphSuid());
            sql.addDateValue(bean.getAphSdate());
            sql.addIntValue(bean.getAphScount());
            sql.addIntValue(bean.getAphEndFlg());
            sql.addIntValue(bean.getAphEuid());
            sql.addDateValue(bean.getAphEdate());
            sql.addIntValue(bean.getAphKnrenFlg());
            sql.addIntValue(bean.getAphViewMain());
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
     * <p>Update ANP_HDATA Data Bindding JavaBean
     * @param bean ANP_HDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpHdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_HDATA");
            sql.addSql(" set ");
            sql.addSql("   APH_SUBJECT=?,");
            sql.addSql("   APH_TEXT1=?,");
            sql.addSql("   APH_TEXT2=?,");
            sql.addSql("   APH_HUID=?,");
            sql.addSql("   APH_HDATE=?,");
            sql.addSql("   APH_SUID=?,");
            sql.addSql("   APH_SDATE=?,");
            sql.addSql("   APH_SCOUNT=?,");
            sql.addSql("   APH_END_FLG=?,");
            sql.addSql("   APH_EUID=?,");
            sql.addSql("   APH_EDATE=?,");
            sql.addSql("   APH_KNREN_FLG=?,");
            sql.addSql("   APH_VIEW_MAIN=?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAphSubject());
            sql.addStrValue(bean.getAphText1());
            sql.addStrValue(bean.getAphText2());
            sql.addIntValue(bean.getAphHuid());
            sql.addDateValue(bean.getAphHdate());
            sql.addIntValue(bean.getAphSuid());
            sql.addDateValue(bean.getAphSdate());
            sql.addIntValue(bean.getAphScount());
            sql.addIntValue(bean.getAphEndFlg());
            sql.addIntValue(bean.getAphEuid());
            sql.addDateValue(bean.getAphEdate());
            sql.addIntValue(bean.getAphKnrenFlg());
            sql.addIntValue(bean.getAphViewMain());
            //where
            sql.addIntValue(bean.getAphSid());

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
     * <p>Select ANP_HDATA All Data
     * @return List in ANP_HDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpHdataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpHdataModel> ret = new ArrayList<AnpHdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   APH_SUBJECT,");
            sql.addSql("   APH_TEXT1,");
            sql.addSql("   APH_TEXT2,");
            sql.addSql("   APH_HUID,");
            sql.addSql("   APH_HDATE,");
            sql.addSql("   APH_SUID,");
            sql.addSql("   APH_SDATE,");
            sql.addSql("   APH_SCOUNT,");
            sql.addSql("   APH_END_FLG,");
            sql.addSql("   APH_EUID,");
            sql.addSql("   APH_EDATE,");
            sql.addSql("   APH_KNREN_FLG,");
            sql.addSql("   APH_VIEW_MAIN");
            sql.addSql(" from ");
            sql.addSql("   ANP_HDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpHdataFromRs(rs));
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
     * <p>Select ANP_HDATA
     * @param aphSid APH_SID
     * @return ANP_HDATAModel
     * @throws SQLException SQL実行例外
     */
    public AnpHdataModel select(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpHdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APH_SID,");
            sql.addSql("   APH_SUBJECT,");
            sql.addSql("   APH_TEXT1,");
            sql.addSql("   APH_TEXT2,");
            sql.addSql("   APH_HUID,");
            sql.addSql("   APH_HDATE,");
            sql.addSql("   APH_SUID,");
            sql.addSql("   APH_SDATE,");
            sql.addSql("   APH_SCOUNT,");
            sql.addSql("   APH_END_FLG,");
            sql.addSql("   APH_EUID,");
            sql.addSql("   APH_EDATE,");
            sql.addSql("   APH_KNREN_FLG,");
            sql.addSql("   APH_VIEW_MAIN");
            sql.addSql(" from");
            sql.addSql("   ANP_HDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpHdataFromRs(rs);
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
     * <p>Delete ANP_HDATA
     * @param aphSid APH_SID
     * @throws SQLException SQL実行例外
     * @return 更新件数
     */
    public int delete(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ANP_HDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

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
     * <p>配信中のデータがあるかどうかを確認する
     * @return List in ANP_HDATAModel
     * @throws SQLException SQL実行例外
     */
    public AnpHdataModel selectInHaisin() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpHdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APH_SID,");
            sql.addSql("   APH_SUBJECT,");
            sql.addSql("   APH_TEXT1,");
            sql.addSql("   APH_TEXT2,");
            sql.addSql("   APH_HUID,");
            sql.addSql("   APH_HDATE,");
            sql.addSql("   APH_SUID,");
            sql.addSql("   APH_SDATE,");
            sql.addSql("   APH_SCOUNT,");
            sql.addSql("   APH_END_FLG,");
            sql.addSql("   APH_EUID,");
            sql.addSql("   APH_EDATE,");
            sql.addSql("   APH_KNREN_FLG,");
            sql.addSql("   APH_VIEW_MAIN");
            sql.addSql(" from");
            sql.addSql("   ANP_HDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_END_FLG <> 1");
            sql.addSql(" order by");
            sql.addSql("   APH_HDATE desc");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpHdataFromRs(rs);
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
     * <p>再送信時の更新を行う
     * @param bean ANP_HDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSaisousin(AnpHdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_HDATA");
            sql.addSql(" set ");
            sql.addSql("   APH_SUID=?,");
            sql.addSql("   APH_SDATE=?,");
            sql.addSql("   APH_SCOUNT=APH_SCOUNT + 1");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAphSuid());
            sql.addDateValue(bean.getAphSdate());
            //where
            sql.addIntValue(bean.getAphSid());

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
     * <p>配信完了
     * @param userSid 更新ユーザSID
     * @throws SQLException SQL実行例外
     * @return 更新件数
     */
    public int updateFinish(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ANP_HDATA");
            sql.addSql(" set");
            sql.addSql("   APH_END_FLG = 1,");
            sql.addSql("   APH_EUID = ?,");
            sql.addSql("   APH_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   APH_END_FLG <> 1");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addDateValue(new UDate());

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
           sql.addSql("   sum(octet_length(APH_SUBJECT)) as APH_SUBJECT,");
           sql.addSql("   sum(octet_length(APH_TEXT1)) as APH_TEXT1,");
           sql.addSql("   sum(octet_length(APH_TEXT2)) as APH_TEXT2");
           sql.addSql(" from ANP_HDATA ");
           sql.addSql(" where APH_SID in ");
           sql.addSql(inSidStr);

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("APH_SUBJECT");
               ret += rs.getLong("APH_TEXT1");
               ret += rs.getLong("APH_TEXT2");
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
     * <p>Create ANP_HDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpHdataModel
     * @throws SQLException SQL実行例外
     */
    private AnpHdataModel __getAnpHdataFromRs(ResultSet rs) throws SQLException {
        AnpHdataModel bean = new AnpHdataModel();
        bean.setAphSid(rs.getInt("APH_SID"));
        bean.setAphSubject(rs.getString("APH_SUBJECT"));
        bean.setAphText1(rs.getString("APH_TEXT1"));
        bean.setAphText2(rs.getString("APH_TEXT2"));
        bean.setAphHuid(rs.getInt("APH_HUID"));
        bean.setAphHdate(UDate.getInstanceTimestamp(rs.getTimestamp("APH_HDATE")));
        bean.setAphSuid(rs.getInt("APH_SUID"));
        bean.setAphSdate(UDate.getInstanceTimestamp(rs.getTimestamp("APH_SDATE")));
        bean.setAphScount(rs.getInt("APH_SCOUNT"));
        bean.setAphEndFlg(rs.getInt("APH_END_FLG"));
        bean.setAphEuid(rs.getInt("APH_EUID"));
        bean.setAphEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APH_EDATE")));
        bean.setAphKnrenFlg(rs.getInt("APH_KNREN_FLG"));
        bean.setAphViewMain(rs.getInt("APH_VIEW_MAIN"));
        return bean;
    }
}
