package jp.groupsession.v2.tcd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;
import jp.groupsession.v2.tcd.tcd130.Tcd130DispModel;
import jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>TCD_TIMEZONE_PRI Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdTimezonePriDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdTimezonePriDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdTimezonePriDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdTimezonePriDao(Connection con) {
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
            sql.addSql("drop table TCD_TIMEZONE_PRI");

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
            sql.addSql(" create table TCD_TIMEZONE_PRI (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   TTI_SID integer not null,");
            sql.addSql("   TTP_DEFAULT integer not null,");
            sql.addSql("   TTP_AUID integer not null,");
            sql.addSql("   TTP_ADATE timestamp not null,");
            sql.addSql("   primary key (USR_SID, TTI_SID)");
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
     * <p>Insert TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @param bean TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdTimezonePriModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_TIMEZONE_PRI(");
            sql.addSql("   USR_SID,");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTP_DEFAULT,");
            sql.addSql("   TTP_AUID,");
            sql.addSql("   TTP_ADATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getTtiSid());
            sql.addIntValue(bean.getTtpDefault());
            sql.addIntValue(bean.getTtpAuid());
            sql.addDateValue(bean.getTtpAdate());
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
     * <p>Insert TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @param bean TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @return count 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insertData(TcdTimezonePriModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_TIMEZONE_PRI(");
            sql.addSql("   USR_SID,");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTP_DEFAULT,");
            sql.addSql("   TTP_AUID,");
            sql.addSql("   TTP_ADATE");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   ? as USR_SID, ");
            sql.addIntValue(bean.getUsrSid());
            sql.addSql("   ? as TTI_SID,");
            sql.addIntValue(bean.getTtiSid());
            sql.addSql("   case when COUNT(*) = 0 then 1 ");
            sql.addSql("   else 0 ");
            sql.addSql("   end as TTP_DEFAULT, ");
            sql.addSql("   ? as TTP_AUID,");
            sql.addIntValue(bean.getTtpAuid());
            sql.addSql("   ? as TTP_ADATE");
            sql.addDateValue(bean.getTtpAdate());
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   TTP_DEFAULT = ?");
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(GSConstTimecard.USED_TIMEZONE_DEFAULT);
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
     * <p>Insert TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @param bean TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int changeDefault(TcdTimezonePriModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" set ");
            sql.addSql("   TTP_DEFAULT = ?,");
            sql.addSql("   TTP_AUID = ?,");
            sql.addSql("   TTP_ADATE = ?");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   TTI_SID = (");
            sql.addSql("     select");
            sql.addSql("       TCD_TIMEZONE_INFO. TTI_SID");
            sql.addSql("     from");
            sql.addSql("       TCD_TIMEZONE_INFO");
            sql.addSql("     where");
            sql.addSql("       TCD_TIMEZONE_INFO.TTI_SORT = (");
            sql.addSql("       select");
            sql.addSql("         min(TCD_TIMEZONE_INFO.TTI_SORT)");
            sql.addSql("       from");
            sql.addSql("         TCD_TIMEZONE_INFO,");
            sql.addSql("         TCD_TIMEZONE_PRI");
            sql.addSql("       where");
            sql.addSql("         TCD_TIMEZONE_PRI.USR_SID = ?");
            sql.addSql("       and");
            sql.addSql("         TCD_TIMEZONE_INFO. TTI_SID = TCD_TIMEZONE_PRI.TTI_SID");
            sql.addSql("     )");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTtpDefault());
            sql.addIntValue(bean.getTtpAuid());
            sql.addDateValue(bean.getTtpAdate());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Update TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @param bean TCD_TIMEZONE_PRI Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdTimezonePriModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" set ");
            sql.addSql("   TTP_DEFAULT=?,");
            sql.addSql("   TTP_AUID=?,");
            sql.addSql("   TTP_ADATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTtpDefault());
            sql.addIntValue(bean.getTtpAuid());
            sql.addDateValue(bean.getTtpAdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getTtiSid());

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
     * <p>Select TCD_TIMEZONE_PRI All Data
     * @return List in TCD_TIMEZONE_PRIModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTimezonePriModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezonePriModel> ret = new ArrayList<TcdTimezonePriModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTP_DEFAULT,");
            sql.addSql("   TTP_AUID,");
            sql.addSql("   TTP_ADATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_PRI");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezonePriFromRs(rs));
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
     * <p>Select TCD_TIMEZONE_PRI
     * @param usrSid USR_SID
     * @param ttiSid TTI_SID
     * @return TCD_TIMEZONE_PRIModel
     * @throws SQLException SQL実行例外
     */
    public TcdTimezonePriModel select(int usrSid, int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdTimezonePriModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTP_DEFAULT,");
            sql.addSql("   TTP_AUID,");
            sql.addSql("   TTP_ADATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(ttiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdTimezonePriFromRs(rs);
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
     * <p>Select TCD_TIMEZONE_PRI
     * @param usrSid USR_SID
     * @return TCD_TIMEZONE_PRIModel
     * @throws SQLException SQL実行例外
     */
    public TcdTimezonePriModel getDefault(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdTimezonePriModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTP_DEFAULT,");
            sql.addSql("   TTP_AUID,");
            sql.addSql("   TTP_ADATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TTP_DEFAULT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstTimecard.USED_TIMEZONE_DEFAULT);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdTimezonePriFromRs(rs);
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
     * <p>Select TCD_TIMEZONE_PRI
     * @param ttiSid TTI_SID
     * @return List<Integer>
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getOnlyUseUserList(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID in (");
            sql.addSql("     select");
            sql.addSql("       TCD_TIMEZONE_PRI.USR_SID");
            sql.addSql("     from");
            sql.addSql("       TCD_TIMEZONE_PRI");
            sql.addSql("     left join");
            sql.addSql("       TCD_TIMEZONE_INFO");
            sql.addSql("     on");
            sql.addSql("       TCD_TIMEZONE_PRI.TTI_SID = TCD_TIMEZONE_INFO.TTI_SID");
            sql.addSql("     where ");
            sql.addSql("       TCD_TIMEZONE_INFO.TTI_USE = 1");
            sql.addSql("     group by ");
            sql.addSql("       TCD_TIMEZONE_PRI.USR_SID");
            sql.addSql("     having ");
            sql.addSql("       count(TCD_TIMEZONE_PRI.USR_SID) = 1");
            sql.addSql("   )");
            sql.addIntValue(ttiSid);

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select TCD_TIMEZONE_PRI
     * @param ttiSid TTI_SID
     * @return List<Integer>
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDefUseUserList(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   TTP_DEFAULT = ?");
            sql.addIntValue(ttiSid);
            sql.addIntValue(GSConstTimecard.USED_TIMEZONE_DEFAULT);

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select TCD_TIMEZONE_PRI
     * @param ttiSid TTI_SID
     * @return TCD_TIMEZONE_PRIModel
     * @throws SQLException SQL実行例外
     */
    public int selectUseUserCount(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
          //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count (TCD_TIMEZONE_PRI.USR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

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
     * <p>Select TCD_TIMEZONE_PRI
     * @param usrSid USR_SID
     * @return TCD_TIMEZONE_PRIModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<TcdTimezonePriModel> selectCanUse(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezonePriModel> ret = new ArrayList<TcdTimezonePriModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_DEFAULT,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_AUID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_ADATE,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_NAME,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_USE,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU ");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI,");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID=TCD_TIMEZONE_INFO.TTI_SID");
            sql.addSql(" and");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_USE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstTimecard.TIMEZONE_USE_KBN_OK);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezonePriListFromRs(rs));
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
     * <p>Select TCD_TIMEZONE_PRI
     * @param usrSid USR_SID
     * @return TCD_TIMEZONE_PRIModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<TcdTimezonePriModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezonePriModel> ret = new ArrayList<TcdTimezonePriModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_DEFAULT,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_AUID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_ADATE,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_NAME,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU ");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI,");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID=TCD_TIMEZONE_INFO.TTI_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezonePriListFromRs(rs));
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
     * <p>時間帯SIDとユーザSIDを指定し使用できる時間帯設定か判定する
     * @param ttiSid
     * @param usrSid
     * @return count 0=存在しない 1以上=存在する
     * @throws SQLException SQL実行例外
     */
    public int getTimezoneUse(int ttiSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_PRI, ");
            sql.addSql("   TCD_TIMEZONE_INFO ");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID=TCD_TIMEZONE_INFO.TTI_SID ");
            sql.addSql(" and ");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID=? ");
            sql.addSql(" and ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID=? ");
            sql.addSql(" and ");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_USE=? ");
            sql.addIntValue(ttiSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstTimecard.TIMEZONE_USE_KBN_OK);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>ユーザSIDを指定し使用できる時間帯設定か判定する
     * @param usrSid
     * @return count 0=存在しない 1以上=存在する
     * @throws SQLException SQL実行例外
     */
    public int getCanUseTimezone(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_PRI, ");
            sql.addSql("   TCD_TIMEZONE_INFO ");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID=TCD_TIMEZONE_INFO.TTI_SID ");
            sql.addSql(" and ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID=? ");
            sql.addSql(" and ");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_USE=? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstTimecard.TIMEZONE_USE_KBN_OK);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select TCD_TIMEZONE_PRI
     * @param ttiSid TTI_SID
     * @return true 使用中
     * @throws SQLException SQL実行例外
     */
    public boolean existUsedUser(int ttiSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join ");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = TCD_TIMEZONE_PRI.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.TTI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_DEFAULT = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);
            sql.addIntValue(GSConstTimecard.USED_TIMEZONE_DEFAULT);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <br>[機  能] 検索件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getSearchDataCount(Tcd130SearchModel model)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(distinct INF.USR_SID) as COUNT");
            __createSearchSql(sql, model);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 検索結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 検索結果
     * @throws SQLException SQL実行例外
     */
    public List<Tcd130DispModel> getSearchDataList(Tcd130SearchModel model)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Tcd130DispModel> ret = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   select");
            sql.addSql("     distinct INF.USR_SID as USR_SID,");
            sql.addSql("     INF.USI_SEI,");
            sql.addSql("     INF.USI_MEI,");
            sql.addSql("     INF.USR_UKO_FLG,");
            sql.addSql("     INF.POS_SID,");
            sql.addSql("   (case      when INF.POS_SID = 0 then 1");
            sql.addSql("      else 0    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            __createSearchSql(sql, model);
            sql.addSql("   order by ");
            sql.addSql("    YAKUSYOKU_EXIST, YAKUSYOKU_SORT, USR_SID");

            int page = model.getPage();
            int maxCnt = model.getMaxCnt();
            if (page <= 0) {
                page = 1;
            }
            sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            ret = new ArrayList<Tcd130DispModel>();
            while (rs.next()) {
                Tcd130DispModel bean = new Tcd130DispModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                bean.setUserUkoFLg(rs.getInt("USR_UKO_FLG"));
                ret.add(bean);
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
     * <br>[機  能] 検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model 検索条件
     */
    private void __createSearchSql(SqlBuffer sql, Tcd130SearchModel model) {

        //SQL文
        sql.addSql("  from ");
        sql.addSql("  ( ");
        sql.addSql("   select");
        sql.addSql("     USR_INFO.USR_SID,");
        sql.addSql("     USR_INFO.USI_SEI,");
        sql.addSql("     USR_INFO.USI_MEI,");
        sql.addSql("     USR_INFO.USR_UKO_FLG,");
        sql.addSql("     USR_INFO.POS_SID,");
        sql.addSql("     TCD_TIMEZONE_PRI.TTI_SID,");
        sql.addSql("     TCD_TIMEZONE_PRI.TTP_DEFAULT");
        sql.addSql("      from ");
        sql.addSql("        ( ");
        sql.addSql("          select");
        sql.addSql("            CMN_USRM.USR_SID,");
        sql.addSql("            CMN_USRM_INF.USI_SEI,");
        sql.addSql("            CMN_USRM_INF.USI_MEI,");
        sql.addSql("            CMN_USRM.USR_UKO_FLG,");
        sql.addSql("            CMN_USRM_INF.POS_SID");
        sql.addSql("          from ");
        sql.addSql("            CMN_USRM,");
        sql.addSql("            CMN_USRM_INF");
        sql.addSql("          where ");
        sql.addSql("            CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("          and ");
        sql.addSql("            CMN_USRM.USR_JKBN = ?");
        sql.addIntValue(GSConst.JTKBN_TOROKU);
        sql.addSql("          and");
        sql.addSql("            CMN_USRM.USR_SID > 100");
        // 検索条件：グループ・ユーザ
        int groupSid = model.getGroupSid();
        int userSid = model.getUserSid();
        if (groupSid != -1 || userSid != -1) {
            sql.addSql("      and ");
            if (userSid != -1) {
                sql.addSql("    CMN_USRM.USR_SID = ?");
                sql.addIntValue(userSid);
            } else {
                sql.addSql("    CMN_USRM.USR_SID in (");
                sql.addSql("     select");
                sql.addSql("       CMN_USRM.USR_SID");
                sql.addSql("     from");
                sql.addSql("       CMN_USRM,");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     where");
                sql.addSql("       CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
                sql.addSql("     and");
                sql.addSql("       CMN_USRM.USR_JKBN = ?");
                sql.addSql("     and");
                sql.addSql("       CMN_BELONGM.GRP_SID = ?");
                sql.addSql("     )");
                sql.addIntValue(GSConst.JTKBN_TOROKU);
                sql.addIntValue(groupSid);
            }
        }
        sql.addSql("        ) USR_INFO");
        sql.addSql("     left join");
        sql.addSql("       TCD_TIMEZONE_PRI ");
        sql.addSql("     on");
        sql.addSql("       USR_INFO.USR_SID = TCD_TIMEZONE_PRI.USR_SID");
        sql.addSql("     ) INF ");
        sql.addSql("     where ");
        sql.addSql("     ( ");
        sql.addSql("       1 = 1 ");
        // 時間帯設定
        int timeZone = model.getTimeZone();
        if (timeZone != -1) {
            sql.addSql(" and ");
            sql.addSql("   INF.TTI_SID = ?");
            sql.addIntValue(timeZone);
        }
        // デフォルト時間帯設定
        int defaultTimeZone = model.getDefaultTimeZone();
        if (defaultTimeZone != -1) {
            sql.addSql(" and ");
            sql.addSql("   INF.USR_SID in (");
            sql.addSql("     select ");
            sql.addSql("       TCD_TIMEZONE_PRI.USR_SID ");
            sql.addSql("     from ");
            sql.addSql("       TCD_TIMEZONE_PRI ");
            sql.addSql("     where ");
            sql.addSql("       TCD_TIMEZONE_PRI.TTI_SID = ?");
            sql.addIntValue(defaultTimeZone);
            sql.addSql("     and ");
            sql.addSql("       TCD_TIMEZONE_PRI.TTP_DEFAULT = ? ");
            sql.addIntValue(GSConstTimecard.USED_TIMEZONE_DEFAULT);
            sql.addSql("   ) ");
        }
        sql.addSql("         ) ");
        if ((timeZone == model.getTacDefaultTimeZone() &&  defaultTimeZone == -1)
                || (timeZone == -1 && defaultTimeZone == model.getTacDefaultTimeZone())
                || (timeZone == model.getTacDefaultTimeZone() 
                && defaultTimeZone == model.getTacDefaultTimeZone())) {
            sql.addSql("      or ");
            sql.addSql("        INF.TTI_SID is null");
        }
    }

    /**
     * <p>Delete TCD_TIMEZONE_PRI
     * @param usrSid USR_SID
     * @param ttiSid TTI_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(ttiSid);

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
     * <p>Delete TCD_TIMEZONE_PRI
     * @param ttiSid TTI_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);
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
     * <p>Delete TCD_TIMEZONE_PRI
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(String[] usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in (");
            //where
            for (int i = 0; i < usrSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(usrSid[i], 0));

                if (i < usrSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create TCD_TIMEZONE_PRI Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdTimezonePriModel
     * @throws SQLException SQL実行例外
     */
    private TcdTimezonePriModel __getTcdTimezonePriFromRs(ResultSet rs) throws SQLException {
        TcdTimezonePriModel bean = new TcdTimezonePriModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTtiSid(rs.getInt("TTI_SID"));
        bean.setTtpDefault(rs.getInt("TTP_DEFAULT"));
        bean.setTtpAuid(rs.getInt("TTP_AUID"));
        bean.setTtpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TTP_ADATE")));
        return bean;
    }

    /**
     * <p>Create TCD_TIMEZONE_PRI Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdTimezonePriModel
     * @throws SQLException SQL実行例外
     */
    private TcdTimezonePriModel __getTcdTimezonePriListFromRs(ResultSet rs) throws SQLException {
        TcdTimezonePriModel bean = new TcdTimezonePriModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTtiSid(rs.getInt("TTI_SID"));
        bean.setTtpDefault(rs.getInt("TTP_DEFAULT"));
        bean.setTtpAuid(rs.getInt("TTP_AUID"));
        bean.setTtpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TTP_ADATE")));
        bean.setTtiName(rs.getString("TTI_NAME"));
        bean.setTtiRyaku(rs.getString("TTI_RYAKU"));
        return bean;
    }
}
