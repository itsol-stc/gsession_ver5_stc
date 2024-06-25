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
import jp.groupsession.v2.anp.model.AnpMtempModel;

/**
 * <p>ANP_MTEMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpMtempDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpMtempDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpMtempDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpMtempDao(Connection con) {
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
            sql.addSql("drop table ANP_MTEMP");

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
            sql.addSql(" create table ANP_MTEMP (");
            sql.addSql("   APM_SID integer not null,");
            sql.addSql("   APM_TITLE varchar(60) not null,");
            sql.addSql("   APM_SUBJECT varchar(60),");
            sql.addSql("   APM_TEXT1 varchar(3000),");
            sql.addSql("   APM_TEXT2 varchar(3000),");
            sql.addSql("   APM_AUID integer not null,");
            sql.addSql("   APM_ADATE timestamp not null,");
            sql.addSql("   APM_EUID integer not null,");
            sql.addSql("   APM_EDATE timestamp not null,");
            sql.addSql("   primary key (APM_SID)");
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
     * <p>Insert ANP_MTEMP Data Bindding JavaBean
     * @param bean ANP_MTEMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpMtempModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_MTEMP(");
            sql.addSql("   APM_SID,");
            sql.addSql("   APM_TITLE,");
            sql.addSql("   APM_SUBJECT,");
            sql.addSql("   APM_TEXT1,");
            sql.addSql("   APM_TEXT2,");
            sql.addSql("   APM_AUID,");
            sql.addSql("   APM_ADATE,");
            sql.addSql("   APM_EUID,");
            sql.addSql("   APM_EDATE");
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
            sql.addIntValue(bean.getApmSid());
            sql.addStrValue(bean.getApmTitle());
            sql.addStrValue(bean.getApmSubject());
            sql.addStrValue(bean.getApmText1());
            sql.addStrValue(bean.getApmText2());
            sql.addIntValue(bean.getApmAuid());
            sql.addDateValue(bean.getApmAdate());
            sql.addIntValue(bean.getApmEuid());
            sql.addDateValue(bean.getApmEdate());
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
     * <p>Update ANP_MTEMP Data Bindding JavaBean
     * @param bean ANP_MTEMP Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpMtempModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_MTEMP");
            sql.addSql(" set ");
            sql.addSql("   APM_TITLE=?,");
            sql.addSql("   APM_SUBJECT=?,");
            sql.addSql("   APM_TEXT1=?,");
            sql.addSql("   APM_TEXT2=?,");
            sql.addSql("   APM_EUID=?,");
            sql.addSql("   APM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApmTitle());
            sql.addStrValue(bean.getApmSubject());
            sql.addStrValue(bean.getApmText1());
            sql.addStrValue(bean.getApmText2());
            sql.addIntValue(bean.getApmEuid());
            sql.addDateValue(bean.getApmEdate());
            //where
            sql.addIntValue(bean.getApmSid());

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
     * <p>Select ANP_MTEMP All Data
     * @return List in ANP_MTEMPModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpMtempModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpMtempModel> ret = new ArrayList<AnpMtempModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APM_SID,");
            sql.addSql("   APM_TITLE,");
            sql.addSql("   APM_SUBJECT,");
            sql.addSql("   APM_TEXT1,");
            sql.addSql("   APM_TEXT2,");
            sql.addSql("   APM_AUID,");
            sql.addSql("   APM_ADATE,");
            sql.addSql("   APM_EUID,");
            sql.addSql("   APM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_MTEMP");
            sql.addSql(" order by ");
            sql.addSql("   APM_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpMtempFromRs(rs));
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
     * <p>Select ANP_MTEMP
     * @param apmSid APM_SID
     * @return ANP_MTEMPModel
     * @throws SQLException SQL実行例外
     */
    public AnpMtempModel select(int apmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpMtempModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APM_SID,");
            sql.addSql("   APM_TITLE,");
            sql.addSql("   APM_SUBJECT,");
            sql.addSql("   APM_TEXT1,");
            sql.addSql("   APM_TEXT2,");
            sql.addSql("   APM_AUID,");
            sql.addSql("   APM_ADATE,");
            sql.addSql("   APM_EUID,");
            sql.addSql("   APM_EDATE");
            sql.addSql(" from");
            sql.addSql("   ANP_MTEMP");
            sql.addSql(" where ");
            sql.addSql("   APM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(apmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpMtempFromRs(rs);
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
     * <p>Delete ANP_MTEMP
     * @param apmSid APM_SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int apmSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ANP_MTEMP");
            sql.addSql(" where ");
            sql.addSql("   APM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(apmSid);

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
   public Long getDiskSize(List<Integer> sidList) throws SQLException {

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
           sql.addSql("   sum(octet_length(APM_TITLE)) as APM_TITLE,");
           sql.addSql("   sum(octet_length(APM_SUBJECT)) as APM_SUBJECT,");
           sql.addSql("   sum(octet_length(APM_TEXT1)) as APM_TEXT1,");
           sql.addSql("   sum(octet_length(APM_TEXT2)) as APM_TEXT2");
           sql.addSql(" from ");
           sql.addSql("   ANP_MTEMP ");
           sql.addSql(" where ");
           sql.addSql("   APM_SID in ");
           sql.addSql(inSidStr);

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("APM_TITLE");
               ret += rs.getLong("APM_SUBJECT");
               ret += rs.getLong("APM_TEXT1");
               ret += rs.getLong("APM_TEXT2");
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
     * <p>Create ANP_MTEMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpMtempModel
     * @throws SQLException SQL実行例外
     */
    private AnpMtempModel __getAnpMtempFromRs(ResultSet rs) throws SQLException {
        AnpMtempModel bean = new AnpMtempModel();
        bean.setApmSid(rs.getInt("APM_SID"));
        bean.setApmTitle(rs.getString("APM_TITLE"));
        bean.setApmSubject(rs.getString("APM_SUBJECT"));
        bean.setApmText1(rs.getString("APM_TEXT1"));
        bean.setApmText2(rs.getString("APM_TEXT2"));
        bean.setApmAuid(rs.getInt("APM_AUID"));
        bean.setApmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APM_ADATE")));
        bean.setApmEuid(rs.getInt("APM_EUID"));
        bean.setApmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APM_EDATE")));
        return bean;
    }
}
