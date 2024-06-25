package jp.groupsession.v2.ntp.dao;

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
import jp.groupsession.v2.ntp.model.NtpPriConfModel;

/**
 * <p>NTP_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpPriConfDao(Connection con) {
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
            sql.addSql("drop table NTP_PRI_CONF");

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
            sql.addSql(" create table NTP_PRI_CONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NPR_DSP_GROUP NUMBER(10,0) not null,");
            sql.addSql("   NPR_SORT_KEY1 NUMBER(10,0) not null,");
            sql.addSql("   NPR_SORT_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   NPR_SORT_KEY2 NUMBER(10,0) not null,");
            sql.addSql("   NPR_SORT_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   NPR_INI_FR_DATE varchar(23) not null,");
            sql.addSql("   NPR_INI_TO_DATE varchar(23) not null,");
            sql.addSql("   NPR_INI_FCOLOR NUMBER(10,0) not null,");
            sql.addSql("   NPR_DSP_LIST NUMBER(10,0) not null,");
            sql.addSql("   NPR_AUTO_RELOAD NUMBER(10,0) not null,");
            sql.addSql("   NPR_DSP_MYGROUP NUMBER(10,0) not null,");
            sql.addSql("   NPR_SMAIL NUMBER(10,0) not null,");
            sql.addSql("   NPR_CMT_SMAIL NUMBER(10,0) not null,");
            sql.addSql("   NPR_GOOD_SMAIL NUMBER(10,0) not null,");
            sql.addSql("   NPR_SCH_KBN NUMBER(10,0) not null,");
            sql.addSql("   NPR_DSP POSITION(10,0) not null,");
            sql.addSql("   NPR_AUID NUMBER(10,0) not null,");
            sql.addSql("   NPR_ADATE varchar(23) not null,");
            sql.addSql("   NPR_EUID NUMBER(10,0) not null,");
            sql.addSql("   NPR_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID)");
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
     * <p>Insert NTP_PRI_CONF Data Bindding JavaBean
     * @param bean NTP_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPR_DSP_GROUP,");
            sql.addSql("   NPR_SORT_KEY1,");
            sql.addSql("   NPR_SORT_ORDER1,");
            sql.addSql("   NPR_SORT_KEY2,");
            sql.addSql("   NPR_SORT_ORDER2,");
            sql.addSql("   NPR_INI_FR_DATE,");
            sql.addSql("   NPR_INI_TO_DATE,");
            sql.addSql("   NPR_INI_FCOLOR,");
            sql.addSql("   NPR_DSP_LIST,");
            sql.addSql("   NPR_AUTO_RELOAD,");
            sql.addSql("   NPR_DSP_MYGROUP,");
            sql.addSql("   NPR_SMAIL,");
            sql.addSql("   NPR_CMT_SMAIL,");
            sql.addSql("   NPR_GOOD_SMAIL,");
            sql.addSql("   NPR_SCH_KBN,");
            sql.addSql("   NPR_DSP_POSITION,");
            sql.addSql("   NPR_AUID,");
            sql.addSql("   NPR_ADATE,");
            sql.addSql("   NPR_EUID,");
            sql.addSql("   NPR_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNprDspGroup());
            sql.addIntValue(bean.getNprSortKey1());
            sql.addIntValue(bean.getNprSortOrder1());
            sql.addIntValue(bean.getNprSortKey2());
            sql.addIntValue(bean.getNprSortOrder2());
            sql.addDateValue(bean.getNprIniFrDate());
            sql.addDateValue(bean.getNprIniToDate());
            sql.addIntValue(bean.getNprIniFcolor());
            sql.addIntValue(bean.getNprDspList());
            sql.addIntValue(bean.getNprAutoReload());
            sql.addIntValue(bean.getNprDspMygroup());
            sql.addIntValue(bean.getNprSmail());
            sql.addIntValue(bean.getNprCmtSmail());
            sql.addIntValue(bean.getNprGoodSmail());
            sql.addIntValue(bean.getNprSchKbn());
            sql.addIntValue(bean.getNprDspPosition());
            sql.addIntValue(bean.getNprAuid());
            sql.addDateValue(bean.getNprAdate());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
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
     * <p>Update NTP_PRI_CONF Data Bindding JavaBean
     * @param bean NTP_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_DSP_GROUP=?,");
            sql.addSql("   NPR_SORT_KEY1=?,");
            sql.addSql("   NPR_SORT_ORDER1=?,");
            sql.addSql("   NPR_SORT_KEY2=?,");
            sql.addSql("   NPR_SORT_ORDER2=?,");
            sql.addSql("   NPR_INI_FR_DATE=?,");
            sql.addSql("   NPR_INI_TO_DATE=?,");
            sql.addSql("   NPR_INI_FCOLOR=?,");
            sql.addSql("   NPR_DSP_LIST=?,");
            sql.addSql("   NPR_AUTO_RELOAD=?,");
            sql.addSql("   NPR_DSP_MYGROUP=?,");
            sql.addSql("   NPR_SMAIL=?,");
            sql.addSql("   NPR_CMT_SMAIL=?,");
            sql.addSql("   NPR_GOOD_SMAIL=?,");
            sql.addSql("   NPR_SCH_KBN=?,");
            sql.addSql("   NPR_DSP_POSITION=?,");
            sql.addSql("   NPR_AUID=?,");
            sql.addSql("   NPR_ADATE=?,");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNprDspGroup());
            sql.addIntValue(bean.getNprSortKey1());
            sql.addIntValue(bean.getNprSortOrder1());
            sql.addIntValue(bean.getNprSortKey2());
            sql.addIntValue(bean.getNprSortOrder2());
            sql.addDateValue(bean.getNprIniFrDate());
            sql.addDateValue(bean.getNprIniToDate());
            sql.addIntValue(bean.getNprIniFcolor());
            sql.addIntValue(bean.getNprDspList());
            sql.addIntValue(bean.getNprAutoReload());
            sql.addIntValue(bean.getNprDspMygroup());
            sql.addIntValue(bean.getNprSmail());
            sql.addIntValue(bean.getNprCmtSmail());
            sql.addIntValue(bean.getNprGoodSmail());
            sql.addIntValue(bean.getNprSchKbn());
            sql.addIntValue(bean.getNprDspPosition());
            sql.addIntValue(bean.getNprAuid());
            sql.addDateValue(bean.getNprAdate());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>Select NTP_PRI_CONF All Data
     * @return List in NTP_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpPriConfModel> ret = new ArrayList<NtpPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPR_DSP_GROUP,");
            sql.addSql("   NPR_SORT_KEY1,");
            sql.addSql("   NPR_SORT_ORDER1,");
            sql.addSql("   NPR_SORT_KEY2,");
            sql.addSql("   NPR_SORT_ORDER2,");
            sql.addSql("   NPR_INI_FR_DATE,");
            sql.addSql("   NPR_INI_TO_DATE,");
            sql.addSql("   NPR_INI_FCOLOR,");
            sql.addSql("   NPR_DSP_LIST,");
            sql.addSql("   NPR_AUTO_RELOAD,");
            sql.addSql("   NPR_DSP_MYGROUP,");
            sql.addSql("   NPR_SMAIL,");
            sql.addSql("   NPR_CMT_SMAIL,");
            sql.addSql("   NPR_GOOD_SMAIL,");
            sql.addSql("   NPR_SCH_KBN,");
            sql.addSql("   NPR_DSP_POSITION,");
            sql.addSql("   NPR_AUID,");
            sql.addSql("   NPR_ADATE,");
            sql.addSql("   NPR_EUID,");
            sql.addSql("   NPR_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpPriConfiFromRs(rs));
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
     * <p>Select NTP_PRI_CONF
     * @param usrSid USR_SID
     * @return NTP_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public NtpPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   NPR_DSP_GROUP,");
            sql.addSql("   NPR_SORT_KEY1,");
            sql.addSql("   NPR_SORT_ORDER1,");
            sql.addSql("   NPR_SORT_KEY2,");
            sql.addSql("   NPR_SORT_ORDER2,");
            sql.addSql("   NPR_INI_FR_DATE,");
            sql.addSql("   NPR_INI_TO_DATE,");
            sql.addSql("   NPR_INI_FCOLOR,");
            sql.addSql("   NPR_DSP_LIST,");
            sql.addSql("   NPR_AUTO_RELOAD,");
            sql.addSql("   NPR_DSP_MYGROUP,");
            sql.addSql("   NPR_SMAIL,");
            sql.addSql("   NPR_CMT_SMAIL,");
            sql.addSql("   NPR_GOOD_SMAIL,");
            sql.addSql("   NPR_SCH_KBN,");
            sql.addSql("   NPR_DSP_POSITION,");
            sql.addSql("   NPR_AUID,");
            sql.addSql("   NPR_ADATE,");
            sql.addSql("   NPR_EUID,");
            sql.addSql("   NPR_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpPriConfiFromRs(rs);
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
     * <p>Delete NTP_PRI_CONF
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>日報登録初期値の更新を行う
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateInitData(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_INI_FR_DATE=?,");
            sql.addSql("   NPR_INI_TO_DATE=?,");
            sql.addSql("   NPR_INI_FCOLOR=?,");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getNprIniFrDate());
            sql.addDateValue(bean.getNprIniToDate());
            sql.addIntValue(bean.getNprIniFcolor());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>日報個人設定の開始・終了時刻を更新する
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateFromTo(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>日報個人設定のソート、デフォルト表示グループを更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateGroupDisp(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_DSP_GROUP=?,");
            sql.addSql("   NPR_SORT_KEY1=?,");
            sql.addSql("   NPR_SORT_ORDER1=?,");
            sql.addSql("   NPR_SORT_KEY2=?,");
            sql.addSql("   NPR_SORT_ORDER2=?,");
            sql.addSql("   NPR_DSP_MYGROUP=?,");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNprDspGroup());
            sql.addIntValue(bean.getNprSortKey1());
            sql.addIntValue(bean.getNprSortOrder1());
            sql.addIntValue(bean.getNprSortKey2());
            sql.addIntValue(bean.getNprSortOrder2());
            sql.addIntValue(bean.getNprDspMygroup());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>日報個人設定の一覧表示件数を更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateDsp(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_DSP_LIST=?,");
            sql.addSql("   NPR_AUTO_RELOAD=?,");
            sql.addSql("   NPR_DSP_POSITION=?,");
            sql.addSql("   NPR_DSP_GROUP=?,");
            sql.addSql("   NPR_SORT_KEY1=?,");
            sql.addSql("   NPR_SORT_ORDER1=?,");
            sql.addSql("   NPR_SORT_KEY2=?,");
            sql.addSql("   NPR_SORT_ORDER2=?,");
            sql.addSql("   NPR_DSP_MYGROUP=?,");
            sql.addSql("   NPR_SCH_KBN=?,");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNprDspList());
            sql.addIntValue(bean.getNprAutoReload());
            sql.addIntValue(bean.getNprDspPosition());
            sql.addIntValue(bean.getNprDspGroup());
            sql.addIntValue(bean.getNprSortKey1());
            sql.addIntValue(bean.getNprSortOrder1());
            sql.addIntValue(bean.getNprSortKey2());
            sql.addIntValue(bean.getNprSortOrder2());
            sql.addIntValue(bean.getNprDspMygroup());
            sql.addIntValue(bean.getNprSchKbn());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>日報個人設定のショートメール通知設定を更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateSmail(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_SMAIL=?,");
            sql.addSql("   NPR_CMT_SMAIL=?,");
            sql.addSql("   NPR_GOOD_SMAIL=?,");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNprSmail());
            sql.addIntValue(bean.getNprCmtSmail());
            sql.addIntValue(bean.getNprGoodSmail());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>日報個人設定のショートメール通知設定を更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateSchKbn(NtpPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   NPR_SCH_KBN=?,");
            sql.addSql("   NPR_EUID=?,");
            sql.addSql("   NPR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNprSchKbn());
            sql.addIntValue(bean.getNprEuid());
            sql.addDateValue(bean.getNprEdate());
            //where
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
     * <p>Create NTP_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpPriConfModel
     * @throws SQLException SQL実行例外
     */
    private NtpPriConfModel __getNtpPriConfiFromRs(ResultSet rs) throws SQLException {
        NtpPriConfModel bean = new NtpPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNprDspGroup(rs.getInt("NPR_DSP_GROUP"));
        bean.setNprSortKey1(rs.getInt("NPR_SORT_KEY1"));
        bean.setNprSortOrder1(rs.getInt("NPR_SORT_ORDER1"));
        bean.setNprSortKey2(rs.getInt("NPR_SORT_KEY2"));
        bean.setNprSortOrder2(rs.getInt("NPR_SORT_ORDER2"));
        bean.setNprIniFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("NPR_INI_FR_DATE")));
        bean.setNprIniToDate(UDate.getInstanceTimestamp(rs.getTimestamp("NPR_INI_TO_DATE")));
        bean.setNprIniFcolor(rs.getInt("NPR_INI_FCOLOR"));
        bean.setNprDspList(rs.getInt("NPR_DSP_LIST"));
        bean.setNprAutoReload(rs.getInt("NPR_AUTO_RELOAD"));
        bean.setNprDspMygroup(rs.getInt("NPR_DSP_MYGROUP"));
        bean.setNprSmail(rs.getInt("NPR_SMAIL"));
        bean.setNprCmtSmail(rs.getInt("NPR_CMT_SMAIL"));
        bean.setNprGoodSmail(rs.getInt("NPR_GOOD_SMAIL"));
        bean.setNprDspPosition(rs.getInt("NPR_DSP_POSITION"));
        bean.setNprSchKbn(rs.getInt("NPR_SCH_KBN"));
        bean.setNprAuid(rs.getInt("NPR_AUID"));
        bean.setNprAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPR_ADATE")));
        bean.setNprEuid(rs.getInt("NPR_EUID"));
        bean.setNprEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPR_EDATE")));
        return bean;
    }
}
