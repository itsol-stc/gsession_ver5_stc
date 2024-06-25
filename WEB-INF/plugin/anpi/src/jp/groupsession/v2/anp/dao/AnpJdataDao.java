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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.model.AnpJdataModel;

/**
 * <p>ANP_JDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpJdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpJdataDao.class);

    /**
     * <p>Default Constructor
     */
    public AnpJdataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AnpJdataDao(Connection con) {
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
            sql.addSql("drop table ANP_JDATA");

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
            sql.addSql(" create table ANP_JDATA (");
            sql.addSql("   APH_SID integer not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   APD_MAILADR varchar(768),");
            sql.addSql("   APD_JOKYO_FLG integer not null,");
            sql.addSql("   APD_PLACE_FLG integer not null,");
            sql.addSql("   APD_SYUSYA_FLG integer not null,");
            sql.addSql("   APD_COMMENT varchar(300),");
            sql.addSql("   APD_HDATE timestamp,");
            sql.addSql("   APD_SCOUNT integer not null,");
            sql.addSql("   APD_CDATE timestamp,");
            sql.addSql("   APD_RDATE timestamp,");
            sql.addSql("   APD_HAISIN_FLG integer not null,");
            sql.addSql("   APD_AUID integer not null,");
            sql.addSql("   APD_ADATE timestamp not null,");
            sql.addSql("   APD_EUID integer not null,");
            sql.addSql("   APD_EDATE timestamp not null,");
            sql.addSql("   primary key (APH_SID,USR_SID)");
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
     * <p>Insert ANP_JDATA Data Bindding JavaBean
     * @param bean ANP_JDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AnpJdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ANP_JDATA(");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getApdMailadr());
            sql.addIntValue(bean.getApdJokyoFlg());
            sql.addIntValue(bean.getApdPlaceFlg());
            sql.addIntValue(bean.getApdSyusyaFlg());
            sql.addStrValue(bean.getApdComment());
            sql.addDateValue(bean.getApdHdate());
            sql.addIntValue(bean.getApdScount());
            sql.addDateValue(bean.getApdCdate());
            sql.addDateValue(bean.getApdRdate());
            sql.addIntValue(bean.getApdHaisinFlg());
            sql.addIntValue(bean.getApdAuid());
            sql.addDateValue(bean.getApdAdate());
            sql.addIntValue(bean.getApdEuid());
            sql.addDateValue(bean.getApdEdate());
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
     * <p>Update ANP_JDATA Data Bindding JavaBean
     * @param bean ANP_JDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AnpJdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" set ");
            sql.addSql("   APD_MAILADR=?,");
            sql.addSql("   APD_JOKYO_FLG=?,");
            sql.addSql("   APD_PLACE_FLG=?,");
            sql.addSql("   APD_SYUSYA_FLG=?,");
            sql.addSql("   APD_COMMENT=?,");
            sql.addSql("   APD_HDATE=?,");
            sql.addSql("   APD_SCOUNT=?,");
            sql.addSql("   APD_CDATE=?,");
            sql.addSql("   APD_RDATE=?,");
            sql.addSql("   APD_HAISIN_FLG=?,");
            sql.addSql("   APD_EUID=?,");
            sql.addSql("   APD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApdMailadr());
            sql.addIntValue(bean.getApdJokyoFlg());
            sql.addIntValue(bean.getApdPlaceFlg());
            sql.addIntValue(bean.getApdSyusyaFlg());
            sql.addStrValue(bean.getApdComment());
            sql.addDateValue(bean.getApdHdate());
            sql.addIntValue(bean.getApdScount());
            sql.addDateValue(bean.getApdCdate());
            sql.addDateValue(bean.getApdRdate());
            sql.addIntValue(bean.getApdHaisinFlg());
            sql.addIntValue(bean.getApdEuid());
            sql.addDateValue(bean.getApdEdate());
            //where
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select ANP_JDATA All Data
     * @return List in ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpJdataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpJdataModel> ret = new ArrayList<AnpJdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_JDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpJdataFromRs(rs));
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
     * <p>Select ANP_JDATA
     * @param aphSid APH_SID
     * @param usrSid USR_SID
     * @return ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public AnpJdataModel select(int aphSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpJdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpJdataFromRs(rs);
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
     * <p>Delete ANP_JDATA(1データ)
     * @param aphSid APH_SID
     * @param usrSid USR_SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int aphSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);
            sql.addIntValue(usrSid);

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
     * <p>Delete ANP_JDATA
     * @param aphSid APH_SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
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
            sql.addSql("   ANP_JDATA");
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
     * <p>ANP_JDATA カウント
     * @param aphSid APH_SID
     * @return カウント数
     * @throws SQLException SQL実行例外
     */
    public int count(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

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
     * <p>Select ANP_JDATA All Data
     * @param aphSid APH_SID
     * @return List in ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpJdataModel> select(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpJdataModel> ret = new ArrayList<AnpJdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpJdataFromRs(rs));
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
     * <p>Select ANP_JDATA 正規ユーザのみ取得する
     * @param aphSid APH_SID
     * @return List in ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpJdataModel> selectRegisteredUser(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpJdataModel> ret = new ArrayList<AnpJdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and exists (");
            sql.addSql("   select * from CMN_USRM");
            sql.addSql("    where CMN_USRM.USR_SID = ANP_JDATA.USR_SID and USR_JKBN = 0)");
            sql.addSql(" order by ");
            sql.addSql("   USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpJdataFromRs(rs));
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
     * <p>Select ANP_JDATA
     * @param aphSid APH_SID
     * @param usrSid USR_SID
     * @return ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public AnpJdataModel selectRegisteredUser(int aphSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpJdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and exists (");
            sql.addSql("   select * from CMN_USRM");
            sql.addSql("    where CMN_USRM.USR_SID = ANP_JDATA.USR_SID and USR_JKBN = 0)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAnpJdataFromRs(rs);
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
     * <p>未返信者データのみ取得します
     * @param aphSid APH_SID
     * @return List in ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpJdataModel> selectMiHensin(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpJdataModel> ret = new ArrayList<AnpJdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and ");
            sql.addSql("   APD_RDATE is null");
            sql.addSql(" and exists (");
            sql.addSql("   select * from CMN_USRM");
            sql.addSql("    where CMN_USRM.USR_SID = ANP_JDATA.USR_SID and USR_JKBN = 0)");
            sql.addSql(" order by ");
            sql.addSql("   USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpJdataFromRs(rs));
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
     * <p>返信済ユーザのデータのみ取得します
     * @param aphSid APH_SID
     * @return List in ANP_JDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<AnpJdataModel> selectHensinZumi(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AnpJdataModel> ret = new ArrayList<AnpJdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APH_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APD_MAILADR,");
            sql.addSql("   APD_JOKYO_FLG,");
            sql.addSql("   APD_PLACE_FLG,");
            sql.addSql("   APD_SYUSYA_FLG,");
            sql.addSql("   APD_COMMENT,");
            sql.addSql("   APD_HDATE,");
            sql.addSql("   APD_SCOUNT,");
            sql.addSql("   APD_CDATE,");
            sql.addSql("   APD_RDATE,");
            sql.addSql("   APD_HAISIN_FLG,");
            sql.addSql("   APD_AUID,");
            sql.addSql("   APD_ADATE,");
            sql.addSql("   APD_EUID,");
            sql.addSql("   APD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and ");
            sql.addSql("   APD_RDATE is not null");
            sql.addSql(" and exists (");
            sql.addSql("   select * from CMN_USRM");
            sql.addSql("    where CMN_USRM.USR_SID = ANP_JDATA.USR_SID and USR_JKBN = 0)");
            sql.addSql(" order by ");
            sql.addSql("   USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(aphSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAnpJdataFromRs(rs));
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
     * <p>ユーザアクセス時の更新処理
     * @param bean ANP_JDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUserAccess(AnpJdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" set ");
            sql.addSql("   APD_CDATE=?,");
            sql.addSql("   APD_EUID=?,");
            sql.addSql("   APD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getApdCdate());
            sql.addIntValue(bean.getApdEuid());
            sql.addDateValue(bean.getApdEdate());
            //where
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>ユーザ入力時の更新処理
     * @param bean ANP_JDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUserInput(AnpJdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" set ");
            sql.addSql("   APD_JOKYO_FLG=?,");
            sql.addSql("   APD_PLACE_FLG=?,");
            sql.addSql("   APD_SYUSYA_FLG=?,");
            sql.addSql("   APD_COMMENT=?,");
            sql.addSql("   APD_RDATE=?,");
            sql.addSql("   APD_EUID=?,");
            sql.addSql("   APD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getApdJokyoFlg());
            sql.addIntValue(bean.getApdPlaceFlg());
            sql.addIntValue(bean.getApdSyusyaFlg());
            sql.addStrValue(bean.getApdComment());
            sql.addDateValue(bean.getApdRdate());
            sql.addIntValue(bean.getApdEuid());
            sql.addDateValue(bean.getApdEdate());
            //where
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>再送信時の更新処理
     * @param bean ANP_JDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSaisousin(AnpJdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" set ");
            sql.addSql("   APD_MAILADR=?,");
            sql.addSql("   APD_JOKYO_FLG=?,");
            sql.addSql("   APD_PLACE_FLG=?,");
            sql.addSql("   APD_SYUSYA_FLG=?,");
            sql.addSql("   APD_COMMENT=?,");

            if (bean.getApdHaisinFlg() == GSConstAnpi.HAISIN_FLG_OK) {
                sql.addSql("   APD_HDATE=?,");
                sql.addSql("   APD_SCOUNT=APD_SCOUNT+1,");
            }
            sql.addSql("   APD_RDATE=?,");
            sql.addSql("   APD_HAISIN_FLG=?,");
            sql.addSql("   APD_EUID=?,");
            sql.addSql("   APD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApdMailadr());
            sql.addIntValue(bean.getApdJokyoFlg());
            sql.addIntValue(bean.getApdPlaceFlg());
            sql.addIntValue(bean.getApdSyusyaFlg());
            sql.addStrValue(bean.getApdComment());

            if (bean.getApdHaisinFlg() == GSConstAnpi.HAISIN_FLG_OK) {
                sql.addDateValue(bean.getApdHdate());
            }
            sql.addDateValue(bean.getApdRdate());
            sql.addIntValue(bean.getApdHaisinFlg());
            sql.addIntValue(bean.getApdEuid());
            sql.addDateValue(bean.getApdEdate());
            //where
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>配信中状態からの更新処理
     * @param bean ANP_JDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSendFlg(AnpJdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ANP_JDATA");
            sql.addSql(" set ");
            sql.addSql("   APD_HDATE=?,");
            sql.addSql("   APD_HAISIN_FLG=?,");
            sql.addSql("   APD_EUID=?,");
            sql.addSql("   APD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APH_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addDateValue(bean.getApdHdate());
            sql.addIntValue(bean.getApdHaisinFlg());
            sql.addIntValue(bean.getApdEuid());
            sql.addDateValue(bean.getApdEdate());
            //where
            sql.addIntValue(bean.getAphSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Create ANP_JDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AnpJdataModel
     * @throws SQLException SQL実行例外
     */
    private AnpJdataModel __getAnpJdataFromRs(ResultSet rs) throws SQLException {
        AnpJdataModel bean = new AnpJdataModel();
        bean.setAphSid(rs.getInt("APH_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setApdMailadr(rs.getString("APD_MAILADR"));
        bean.setApdJokyoFlg(rs.getInt("APD_JOKYO_FLG"));
        bean.setApdPlaceFlg(rs.getInt("APD_PLACE_FLG"));
        bean.setApdSyusyaFlg(rs.getInt("APD_SYUSYA_FLG"));
        bean.setApdComment(rs.getString("APD_COMMENT"));
        bean.setApdHdate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_HDATE")));
        bean.setApdScount(rs.getInt("APD_SCOUNT"));
        bean.setApdCdate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_CDATE")));
        bean.setApdRdate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_RDATE")));
        bean.setApdHaisinFlg(rs.getInt("APD_HAISIN_FLG"));
        bean.setApdAuid(rs.getInt("APD_AUID"));
        bean.setApdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_ADATE")));
        bean.setApdEuid(rs.getInt("APD_EUID"));
        bean.setApdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_EDATE")));
        return bean;
    }
    
    /**
    *
    * <br>[機  能] 指定SIDのデータサイズを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList 指定SID
    * @param aphSid 安否確認SID
    * @return データサイズ
    * @throws SQLException SQL実行時例外
    */
   public Long getDiskSize(List<Integer> sidList, int aphSid) throws SQLException {

       if (sidList == null || sidList.size() == 0) {
           return (long) 0;
       }
       long ret = 0;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select  ");
           sql.addSql("   sum(octet_length(APD_MAILADR)) as APD_MAILADR,");
           sql.addSql("   sum(octet_length(APD_COMMENT)) as APD_COMMENT");
           sql.addSql(" from ");
           sql.addSql("   ANP_JDATA ");
           sql.addSql(" where ");
           sql.addSql("   APH_SID = ? ");
           sql.addIntValue(aphSid);
           sql.addSql(" and ");
           sql.addSql("   USR_SID in ( ");
           for (int idx = 0; idx < sidList.size(); idx++) {
               if (idx != 0) {
                   sql.addSql("   , ");        
               }
               sql.addSql("   ? ");
               sql.addIntValue(sidList.get(idx));
           }
           sql.addSql("   ) ");
           

           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("APD_MAILADR");
               ret += rs.getLong("APD_COMMENT");
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
   * <br>[機  能] 安否確認SIDから削除分のデータサイズを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param aphSid 安否確認SID
   * @return データサイズ
   * @throws SQLException SQL実行時例外
   */
  public Long getDiskSizeDelete(int aphSid) throws SQLException {

      long ret = 0;

      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Connection con = null;
      con = getCon();

      try {
          //SQL文
          SqlBuffer sql = new SqlBuffer();
          sql.addSql(" select  ");
          sql.addSql("   sum(octet_length(APD_MAILADR)) as APD_MAILADR,");
          sql.addSql("   sum(octet_length(APD_COMMENT)) as APD_COMMENT");
          sql.addSql(" from ");
          sql.addSql("   ANP_JDATA ");
          sql.addSql(" where ");
          sql.addSql("   APH_SID = ? ");
          sql.addIntValue(aphSid);

          pstmt = con.prepareStatement(sql.toSqlString());
          sql.setParameter(pstmt);
          log__.info(sql.toLogString());
          rs = pstmt.executeQuery();
          if (rs.next()) {
              ret = rs.getLong("APD_MAILADR");
              ret += rs.getLong("APD_COMMENT");
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