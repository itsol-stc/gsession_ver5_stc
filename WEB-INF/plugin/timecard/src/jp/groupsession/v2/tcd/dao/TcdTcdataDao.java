package jp.groupsession.v2.tcd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.tcd.csv.TcdCsvModel;
import jp.groupsession.v2.tcd.csv.TcdCsvRecordListenerImpl;
import jp.groupsession.v2.tcd.model.TcdCsvSearchModel;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;
import jp.groupsession.v2.tcd.tcd010.Tcd010Model;
import jp.groupsession.v2.tcd.tcd020.Tcd020Biz;

/**
 * <p>TCD_TCDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdTcdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdTcdataDao.class);
    
    public static final int SIZE_TCD_DATA__ = 16;

    /**
     * <p>Default Constructor
     */
    public TcdTcdataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdTcdataDao(Connection con) {
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
            sql.addSql("drop table TCD_TCDATA");

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
            sql.addSql(" create table TCD_TCDATA (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   TCD_DATE Date not null,");
            sql.addSql("   TCD_INTIME varchar(8),");
            sql.addSql("   TCD_OUTTIME varchar(8),");
            sql.addSql("   TCD_STRIKE_INTIME varchar(8),");
            sql.addSql("   TCD_STRIKE_OUTTIME varchar(8),");
            sql.addSql("   TCD_BIKO varchar(100),");
            sql.addSql("   TCD_STATUS NUMBER(10,0) not null,");
            sql.addSql("   THI_SID NUMBER(10,0) not null,");
            sql.addSql("   TCD_HOLOTHER varchar(10),");
            sql.addSql("   TCD_HOLCNT NUMBER(10,0),");
            sql.addSql("   TCD_CHKKBN NUMBER(10,0) not null,");
            sql.addSql("   TCD_SOUKBN NUMBER(10,0) not null,");
            sql.addSql("   TCD_LOCK_FLG NUMBER(10,0) not null,");
            sql.addSql("   TCD_AUID NUMBER(10,0) not null,");
            sql.addSql("   TCD_ADATE varchar(26) not null,");
            sql.addSql("   TCD_EUID NUMBER(10,0) not null,");
            sql.addSql("   TCD_EDATE varchar(26) not null,");
            sql.addSql("   primary key (USR_SID,TCD_DATE)");
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
     * <p>Insert TCD_TCDATA Data Bindding JavaBean
     * @param bean TCD_TCDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdTcdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_TCDATA(");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCD_DATE,");
            sql.addSql("   TCD_INTIME,");
            sql.addSql("   TCD_OUTTIME,");
            sql.addSql("   TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_BIKO,");
            sql.addSql("   TCD_STATUS,");
            sql.addSql("   THI_SID,");
            sql.addSql("   TCD_HOLOTHER,");
            sql.addSql("   TCD_HOLCNT,");
            sql.addSql("   TCD_CHKKBN,");
            sql.addSql("   TCD_SOUKBN,");
            sql.addSql("   TCD_LOCK_FLG,");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TCD_AUID,");
            sql.addSql("   TCD_ADATE,");
            sql.addSql("   TCD_EUID,");
            sql.addSql("   TCD_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   cast(? as time),");
            sql.addSql("   cast(? as time),");
            sql.addSql("   cast(? as time),");
            sql.addSql("   cast(? as time),");
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
            sql.addDateValue(bean.getTcdDate());
            if (null != bean.getTcdIntime()) {
                sql.addStrValue(bean.getTcdIntime().toString()); // Time型をhh:mm:ss形式に
            } else {
                sql.addDateValue((UDate) null);
            }

            if (null != bean.getTcdOuttime()) {
                sql.addStrValue(bean.getTcdOuttime().toString()); // 同上
            } else {
                sql.addDateValue((UDate) null);
            }

            if (null != bean.getTcdStrikeIntime()) {
                sql.addStrValue(bean.getTcdStrikeIntime().toString()); // 同上
            } else {
                sql.addDateValue((UDate) null);
            }

            if (null != bean.getTcdStrikeOuttime()) {
                sql.addStrValue(bean.getTcdStrikeOuttime().toString()); // 同上
            } else {
                sql.addDateValue((UDate) null);
            }

            if (null != bean.getTcdBiko()) {
                sql.addStrValue(bean.getTcdBiko());
            } else {
                sql.addStrValue((String) null);
            }
            sql.addIntValue(bean.getTcdStatus());
            sql.addIntValue(bean.getThiSid());
            sql.addStrValue(bean.getTcdHolother());
            sql.addDecimalValue(bean.getTcdHolcnt());
            sql.addIntValue(bean.getTcdChkkbn());
            sql.addIntValue(bean.getTcdSoukbn());
            sql.addIntValue(bean.getTcdLockFlg());
            sql.addIntValue(bean.getTtiSid());
            sql.addIntValue(bean.getTcdAuid());
            sql.addDateValue(bean.getTcdAdate());
            sql.addIntValue(bean.getTcdEuid());
            sql.addDateValue(bean.getTcdEdate());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            int count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update TCD_TCDATA Data Bindding JavaBean
     * @param bean TCD_TCDATA Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdTcdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" set ");
            sql.addSql("   TCD_INTIME=?,");
            sql.addSql("   TCD_OUTTIME=?,");
            sql.addSql("   TCD_STRIKE_INTIME=?,");
            sql.addSql("   TCD_STRIKE_OUTTIME=?,");
            sql.addSql("   TCD_BIKO=?,");
            sql.addSql("   TCD_STATUS=?,");
            sql.addSql("   THI_SID=?,");
            sql.addSql("   TCD_HOLOTHER=?,");
            sql.addSql("   TCD_HOLCNT=?,");
            sql.addSql("   TCD_CHKKBN=?,");
            sql.addSql("   TCD_SOUKBN=?,");
            sql.addSql("   TCD_LOCK_FLG=?,");
            sql.addSql("   TCD_EUID=?,");
            sql.addSql("   TCD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (bean.getTcdIntime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdIntime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            if (bean.getTcdOuttime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdOuttime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            if (bean.getTcdStrikeIntime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdStrikeIntime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            if (bean.getTcdStrikeOuttime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdStrikeOuttime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            sql.addStrValue(bean.getTcdBiko());
            sql.addIntValue(bean.getTcdStatus());
            sql.addIntValue(bean.getThiSid());
            sql.addStrValue(bean.getTcdHolother());
            sql.addDecimalValue(bean.getTcdHolcnt());
            sql.addIntValue(bean.getTcdChkkbn());
            sql.addIntValue(bean.getTcdSoukbn());
            sql.addIntValue(bean.getTcdLockFlg());
            sql.addIntValue(bean.getTcdEuid());
            sql.addDateValue(bean.getTcdEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getTcdDate());

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
     * <p>メイン画面用タイムカード更新
     * @param bean TCD_TCDATA Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDaily(TcdTcdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" set ");
            sql.addSql("   TCD_INTIME=?,");
            sql.addSql("   TCD_OUTTIME=?,");
            sql.addSql("   TCD_STRIKE_INTIME=?,");
            sql.addSql("   TCD_STRIKE_OUTTIME=?,");
            sql.addSql("   TCD_STATUS=?,");
            sql.addSql("   TCD_CHKKBN=?,");
            sql.addSql("   TCD_SOUKBN=?,");
            sql.addSql("   TCD_EUID=?,");
            sql.addSql("   TCD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            if (bean.getTcdIntime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdIntime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            if (bean.getTcdOuttime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdOuttime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            if (bean.getTcdStrikeIntime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdStrikeIntime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            if (bean.getTcdStrikeOuttime() != null) {
                sql.addDateValue(UDate.getInstance(bean.getTcdStrikeOuttime().getTime()));
            } else {
                sql.addDateValue((UDate) null);
            }

            sql.addIntValue(bean.getTcdStatus());
            sql.addIntValue(bean.getTcdChkkbn());
            sql.addIntValue(bean.getTcdSoukbn());
            sql.addIntValue(bean.getTcdEuid());
            sql.addDateValue(bean.getTcdEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getTcdDate());

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
     * <p>Select TCD_TCDATA All Data
     * @return List in TCD_TCDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTcdataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTcdataModel> ret = new ArrayList<TcdTcdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCD_DATE,");
            sql.addSql("   TCD_INTIME,");
            sql.addSql("   TCD_OUTTIME,");
            sql.addSql("   TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_BIKO,");
            sql.addSql("   TCD_STATUS,");
            sql.addSql("   THI_SID,");
            sql.addSql("   TCD_HOLOTHER,");
            sql.addSql("   TCD_HOLCNT,");
            sql.addSql("   TCD_CHKKBN,");
            sql.addSql("   TCD_SOUKBN,");
            sql.addSql("   TCD_LOCK_FLG,");
            sql.addSql("   TCD_AUID,");
            sql.addSql("   TCD_ADATE,");
            sql.addSql("   TCD_EUID,");
            sql.addSql("   TCD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTcdataFromRs(rs));
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
     * <p>Select TCD_TCDATA All Data
     * @param usrSid ユーザSID
     * @return List in TCD_TCDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTcdataModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTcdataModel> ret = new ArrayList<TcdTcdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCD_DATE,");
            sql.addSql("   TCD_INTIME,");
            sql.addSql("   TCD_OUTTIME,");
            sql.addSql("   TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_BIKO,");
            sql.addSql("   TCD_STATUS,");
            sql.addSql("   THI_SID,");
            sql.addSql("   TCD_HOLOTHER,");
            sql.addSql("   TCD_HOLCNT,");
            sql.addSql("   TCD_CHKKBN,");
            sql.addSql("   TCD_SOUKBN,");
            sql.addSql("   TCD_LOCK_FLG,");
            sql.addSql("   TCD_AUID,");
            sql.addSql("   TCD_ADATE,");
            sql.addSql("   TCD_EUID,");
            sql.addSql("   TCD_EDATE,");
            sql.addSql("   TTI_SID");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTcdataFromRsBase(rs));
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
     * <p>タイムカード登録情報のなかから最小値を取得
     * <br>登録情報が無い場合はシステム日付から年を取得
     * @return int 最少年
     * @throws SQLException SQL実行例外
     */
    public UDate getMinNendo() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate sysDate = new UDate();
        UDate ret = sysDate;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   min(TCD_DATE) as MINDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getTimestamp("MINDATE") != null) {
                UDate minDate = UDate.getInstanceTimestamp(rs.getTimestamp("MINDATE"));
                ret = minDate;
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
     * <p>タイムカード登録情報のなかから最小値を取得
     * <br>登録情報が無い場合はシステム日付から年を取得
     * @return int 最少年
     * @throws SQLException SQL実行例外
     */
    public int getMinYear() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate sysDate = new UDate();
        int ret = sysDate.getYear();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   min(TCD_DATE) as MINDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getTimestamp("MINDATE") != null) {
                UDate minDate = UDate.getInstanceTimestamp(rs.getTimestamp("MINDATE"));
                ret = minDate.getYear();
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
     * <p>タイムカード登録情報のなかから最大値を取得
     * <br>登録情報が無い場合はシステム日付から年を取得
     * @return int 最大値
     * @throws SQLException SQL実行例外
     */
    public int getMaxYear() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate sysDate = new UDate();
        int ret = sysDate.getYear();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(TCD_DATE) as MAXDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getTimestamp("MAXDATE") != null) {
                UDate maxDate = UDate.getInstanceTimestamp(rs.getTimestamp("MAXDATE"));
                ret = maxDate.getYear();
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
     * <p>Select TCD_TCDATA
     * @param bean TCD_TCDATA Model
     * @return TCD_TCDATAModel
     * @throws SQLException SQL実行例外
     */
    public TcdTcdataModel select(TcdTcdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdTcdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TCD_TCDATA.USR_SID,");
            sql.addSql("   TCD_TCDATA.TCD_DATE,");
            sql.addSql("   TCD_TCDATA.TCD_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_BIKO,");
            sql.addSql("   TCD_TCDATA.TCD_STATUS,");
            sql.addSql("   TCD_TCDATA.THI_SID,");
            sql.addSql("   TCD_TCDATA.TCD_HOLOTHER,");
            sql.addSql("   TCD_TCDATA.TCD_HOLCNT,");
            sql.addSql("   TCD_TCDATA.TCD_CHKKBN,");
            sql.addSql("   TCD_TCDATA.TCD_SOUKBN,");
            sql.addSql("   TCD_TCDATA.TCD_LOCK_FLG,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU,");
            sql.addSql("   TCD_TCDATA.TCD_AUID,");
            sql.addSql("   TCD_TCDATA.TCD_ADATE,");
            sql.addSql("   TCD_TCDATA.TCD_EUID,");
            sql.addSql("   TCD_TCDATA.TCD_EDATE,");
            // 休日関連を追加
            sql.addSql("   CMN_HOLIDAY.HOL_NAME,");
            sql.addSql("   CMN_HOLIDAY.HOL_KBN,");
            //時間帯情報を追加
            sql.addSql("   TCD_TCDATA.TTI_SID");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA left join CMN_HOLIDAY");
            sql.addSql("   on TCD_TCDATA.TCD_DATE = CMN_HOLIDAY.HOL_DATE");
            sql.addSql(" right join TCD_TIMEZONE_INFO ");
            sql.addSql("   on TCD_TCDATA.TTI_SID = TCD_TIMEZONE_INFO.TTI_SID");
            sql.addSql(" where ");
            sql.addSql("   TCD_TCDATA.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_TCDATA.TCD_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getTcdDate());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdTcdataFromRs(rs);
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
     * <p>指定した年月のデータを全て取得する
     * @param uSid ユーザーSID
     * @param fdate from日付
     * @param tdate to日付
     * @return List in Tcd010Model 結果を格納したListオブジェクト
     * @throws SQLException SQL実行例外
     */
    public List<Tcd010Model> select(int uSid, UDate fdate, UDate tdate)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Tcd010Model> ret = new ArrayList<Tcd010Model>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCD_DATE,");
            sql.addSql("   TCD_INTIME,");
            sql.addSql("   TCD_OUTTIME,");
            sql.addSql("   TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_BIKO,");
            sql.addSql("   TCD_STATUS,");
            sql.addSql("   THI_SID,");
            sql.addSql("   TCD_HOLOTHER,");
            sql.addSql("   TCD_HOLCNT,");
            sql.addSql("   TCD_CHKKBN,");
            sql.addSql("   TCD_SOUKBN,");
            sql.addSql("   TCD_LOCK_FLG,");
            sql.addSql("   TCD_AUID,");
            sql.addSql("   TCD_ADATE,");
            sql.addSql("   TCD_EUID,");
            sql.addSql("   TCD_EDATE,");
            sql.addSql("   TTI_SID");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   TCD_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   TCD_DATE <= ?");
            sql.addSql(" order by");
            sql.addSql("   TCD_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(uSid);
            sql.addDateValue(fdate);
            sql.addDateValue(tdate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getTcd010FromRs(rs));
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
     * <p>指定した条件のデータをCSVへ出力する
     * @param searchMdl 検索条件
     * @param sessionUserSid セッションユーザSID
     * @param rl リスナークラス
     * @return List in Tcd010Model 結果を格納したListオブジェクト
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public int createTimecardForCsv(
            TcdCsvSearchModel searchMdl, int sessionUserSid, TcdCsvRecordListenerImpl rl)
            throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            CmnUsrmInfDao usDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usMdl = usDao.select(searchMdl.getTcdCsvUserSid());
            String userName = usMdl.getUsiSei() + " " +  usMdl.getUsiMei();
            String syainNo = NullDefault.getString(usMdl.getUsiSyainNo(), "");
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   TCD_TCDATA.TCD_DATE,");
            sql.addSql("   TCD_TCDATA.TCD_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_BIKO,");
            sql.addSql("   TCD_TCDATA.TCD_STATUS,");
            sql.addSql("   TCD_TCDATA.THI_SID as THI_SID,");
            sql.addSql("   TCD_HOLIDAY_INF.THI_NAME as THI_NAME,");
            sql.addSql("   TCD_HOLIDAY_INF.THI_HOLTOTAL_KBN as THI_HOLTOTAL_KBN,");
            sql.addSql("   TCD_TCDATA.TCD_HOLOTHER,");
            sql.addSql("   TCD_TCDATA.TCD_HOLCNT,");
            sql.addSql("   TCD_TCDATA.TCD_CHKKBN,");
            sql.addSql("   TCD_TCDATA.TCD_SOUKBN,");
            sql.addSql("   TCD_TCDATA.TCD_LOCK_FLG,");
            sql.addSql("   TCD_TCDATA.TCD_AUID,");
            sql.addSql("   TCD_TCDATA.TCD_ADATE,");
            sql.addSql("   TCD_TCDATA.TCD_EUID,");
            sql.addSql("   TCD_TCDATA.TCD_EDATE,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" left join ");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" on ");
            sql.addSql("   TCD_TCDATA.THI_SID = TCD_HOLIDAY_INF.THI_SID");
            sql.addSql(" left join ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" on ");
            sql.addSql("   TCD_TCDATA.TTI_SID = TCD_TIMEZONE_INFO.TTI_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID = TCD_TCDATA.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   TCD_TCDATA.TCD_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   TCD_TCDATA.TCD_DATE <= ?");
            sql.addSql(" order by");
            sql.addSql("   TCD_TCDATA.TCD_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(searchMdl.getTcdCsvUserSid());
            sql.addDateValue(searchMdl.getTcdCsvFrDate());
            sql.addDateValue(searchMdl.getTcdCsvToDate());
            UDate frDate = searchMdl.getTcdCsvFrDate().cloneUDate();
            UDate toDate = searchMdl.getTcdCsvToDate().cloneUDate();
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            TcdCsvModel model = null;
            TcdCsvModel blankModel = null;
            while (rs.next()) {
                model = __getTcdCsvFromRs(rs);
                while (frDate.compareDateYMD(model.getTcdDate()) == UDate.LARGE) {
                    blankModel = new TcdCsvModel();
                    blankModel.setTcdDate(frDate);
                    blankModel.setUserName(userName);
                    blankModel.setSyainNo(syainNo);
                    rl.setRecord(blankModel);
                    frDate.addDay(1);
                }
                rl.setRecord(model);
                frDate.addDay(1);
            }
            while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
                blankModel = new TcdCsvModel();
                blankModel.setTcdDate(frDate);
                blankModel.setUserName(userName);
                blankModel.setSyainNo(syainNo);
                rl.setRecord(blankModel);
                frDate.addDay(1);
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
     * <p>指定した年月のデータを全て取得する
     * @param uSid ユーザーSID
     * @param frDate from日付
     * @param toDate to日付
     * @return List in Tcd010Model 結果を格納したListオブジェクト
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, Tcd010Model> getTimeCardMap(int uSid, UDate frDate, UDate toDate)
            throws SQLException {
        // 休日関連追加済み
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, Tcd010Model> ret = new HashMap<String, Tcd010Model>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TCD_TCDATA.USR_SID,");
            sql.addSql("   TCD_TCDATA.TCD_DATE,");
            sql.addSql("   TCD_TCDATA.TCD_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_BIKO,");
            sql.addSql("   TCD_TCDATA.TCD_STATUS,");
            sql.addSql("   TCD_TCDATA.THI_SID,");
            sql.addSql("   TCD_TCDATA.TCD_HOLOTHER,");
            sql.addSql("   TCD_TCDATA.TCD_HOLCNT,");
            sql.addSql("   TCD_TCDATA.TCD_CHKKBN,");
            sql.addSql("   TCD_TCDATA.TCD_SOUKBN,");
            sql.addSql("   TCD_TCDATA.TCD_LOCK_FLG,");
            sql.addSql("   TCD_TCDATA.TCD_AUID,");
            sql.addSql("   TCD_TCDATA.TCD_ADATE,");
            sql.addSql("   TCD_TCDATA.TCD_EUID,");
            sql.addSql("   TCD_TCDATA.TCD_EDATE,");
            sql.addSql("   TCD_TCDATA.TTI_SID,");
            sql.addSql("   TCD_TCDATA.THI_SID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" left join");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" on");
            sql.addSql("   TCD_TCDATA.TTI_SID = TCD_TIMEZONE_INFO.TTI_SID");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   TCD_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   TCD_DATE <= ?");
            sql.addSql(" order by");
            sql.addSql("   TCD_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(uSid);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            Tcd010Model model = null;
            while (rs.next()) {
                model = __getTcd010FromRs(rs);
                model.setTcd010TimezoneName(rs.getString("TTI_RYAKU"));
                ret.put(model.getTcdDate().getDateString(), model);
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
     * <p>指定日付以前の不正データを取得する
     * @param userSid ユーザSID
     * @param date 指定日付
     * @return ArrayList in TcdTcdataModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<TcdTcdataModel> getFailTimecardData(int userSid, UDate date)
    throws SQLException {

        ArrayList<TcdTcdataModel> ret = new ArrayList<TcdTcdataModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TCD_TCDATA.USR_SID,");
            sql.addSql("   TCD_TCDATA.TCD_DATE,");
            sql.addSql("   TCD_TCDATA.TCD_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_INTIME,");
            sql.addSql("   TCD_TCDATA.TCD_STRIKE_OUTTIME,");
            sql.addSql("   TCD_TCDATA.TCD_BIKO,");
            sql.addSql("   TCD_TCDATA.TCD_STATUS,");
            sql.addSql("   TCD_TCDATA.TTI_SID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU,");
            sql.addSql("   TCD_TCDATA.THI_SID,");
            sql.addSql("   TCD_TCDATA.TCD_HOLOTHER,");
            sql.addSql("   TCD_TCDATA.TCD_HOLCNT,");
            sql.addSql("   TCD_TCDATA.TCD_CHKKBN,");
            sql.addSql("   TCD_TCDATA.TCD_SOUKBN,");
            sql.addSql("   TCD_TCDATA.TCD_LOCK_FLG,");
            sql.addSql("   TCD_TCDATA.TCD_AUID,");
            sql.addSql("   TCD_TCDATA.TCD_ADATE,");
            sql.addSql("   TCD_TCDATA.TCD_EUID,");
            sql.addSql("   TCD_TCDATA.TCD_EDATE,");
            // 休日関連を追加
            sql.addSql("   CMN_HOLIDAY.HOL_NAME,");
            sql.addSql("   CMN_HOLIDAY.HOL_KBN");

            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA left join CMN_HOLIDAY");
            sql.addSql("   on TCD_TCDATA.TCD_DATE = CMN_HOLIDAY.HOL_DATE");
            sql.addSql("   right join TCD_TIMEZONE_INFO");
            sql.addSql("   on TCD_TCDATA.TTI_SID = TCD_TIMEZONE_INFO.TTI_SID");
            sql.addSql(" where ");
            sql.addSql("   TCD_TCDATA.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_TCDATA.TCD_DATE<?");
            sql.addSql(" and");
            sql.addSql("   TCD_TCDATA.TCD_INTIME is not null");
            sql.addSql(" and");
            sql.addSql("   TCD_TCDATA.TCD_OUTTIME is null");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addDateValue(date);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTcdataFromRs(rs));
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
     * <p>
     * 指定したユーザー、日付のTcdTcdataModelを返す.
     * <p>
     * データが存在しない場合はnullを返す
     * </p>
     * @param uSid ユーザーSID
     * @param year 年
     * @param month 月
     * @param day 日
     * @return TcdTcdataModel selectした結果を格納したModel
     * @throws SQLException SQL実行例外
     */
    public TcdTcdataModel select(int uSid, int year, int month, int day)
            throws SQLException {
        // 引数からTcdTcdataModelを作成して別のメソッドに渡す
        TcdTcdataModel tcdm = new TcdTcdataModel();
        tcdm.setUsrSid(uSid);

        UDate ud = new UDate();
        ud.setZeroHhMmSs();
        ud.setDate(year, month, day);
        tcdm.setTcdDate(ud);

        return this.select(tcdm);
    }

    /**
     * <p>Delete TCD_TCDATA
     * @param bean TCD_TCDATA Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public  int delete(TcdTcdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCD_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getTcdDate());

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
     * <p>
     * 指定したユーザー、日付のTcdTcdataModelを返す.
     * <p>
     * データが存在しない場合はnullを返す
     * </p>
     * @param uSid ユーザーSID
     * @param year 年
     * @param month 月
     * @param days 日
     * @param sime 締め日
     * @param reqMdl リクエスト情報
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int uSid,
                      int year,
                      int month,
                      String[] days,
                      int sime,
                      RequestModel reqMdl)
            throws SQLException {

        int ret = 0;
        TcdTcdataModel tcdm = new TcdTcdataModel();
        tcdm.setUsrSid(uSid);

        UDate ud = new UDate();
        Tcd020Biz biz = new Tcd020Biz();
        for (int i = 0; i < days.length; i++) {
            ud = biz.getEditDate(year, month, Integer.parseInt(days[i]), sime);
            tcdm.setTcdDate(ud);
            int cnt = this.delete(tcdm);
            ret = ret + cnt;
        }
        return ret;
    }

    /**
     * <p> 対象の時間帯情報を使用しているタイムカードが存在するか判定
     * @param ttiSid 時間帯情報SID
     * @return List in TCD_TCDATAModel
     * @throws SQLException SQL実行例外
     */
    public boolean isExistUsedTimeZone(int ttiSid) throws SQLException {

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
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = TCD_TCDATA.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("   TCD_TCDATA.TTI_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <p> 対象の休日区分を使用しているタイムカードが存在するか判定
     * @param thiSid 時間帯情報SID
     * @return ret 存在件数
     * @throws SQLException SQL実行例外
     */
    public int selectUseData(int thiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   TCD_TCDATA");
            sql.addSql(" where ");
            sql.addSql("   THI_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(thiSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
    *
    * <br>[機  能] 指定SIDのデータサイズを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param dateList 指定SID
    * @param usrSid ユーザSID
    * @return データサイズ
    * @throws SQLException SQL実行時例外
    */
   public Long getDataSize(List<UDate> dateList, int usrSid) throws SQLException {

       if (dateList == null || dateList.size() == 0) {
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
           sql.addSql("   sum(octet_length(TCD_BIKO)) as TCD_BIKO,");
           sql.addSql(String.format(" count(TCD_DATE) * %d as SIZE ", SIZE_TCD_DATA__));
           sql.addSql(" from ");
           sql.addSql("   TCD_TCDATA ");
           sql.addSql(" where ");
           sql.addSql("   USR_SID = ? ");
           sql.addIntValue(usrSid);
           sql.addSql(" and ");
           sql.addSql("   TCD_DATE in ( ");
           
           for (int idx = 0; idx < dateList.size(); idx++) {
               if (idx != 0) {
                   sql.addSql("   , ");    
               }
               sql.addSql("   ? ");
               sql.addDateValue(dateList.get(idx));
           }
           sql.addSql("   ) ");

           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("TCD_BIKO");
               ret += rs.getLong("SIZE");
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
     * <p>Create TCD_TCDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdTcdataModel
     * @throws SQLException SQL実行例外
     */
    private TcdTcdataModel __getTcdTcdataFromRsBase(ResultSet rs) throws SQLException {
        TcdTcdataModel bean = new TcdTcdataModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTcdDate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_DATE")));
        bean.setTcdIntime(rs.getTime("TCD_INTIME"));
        bean.setTcdOuttime(rs.getTime("TCD_OUTTIME"));
        bean.setTcdStrikeIntime(rs.getTime("TCD_STRIKE_INTIME"));
        bean.setTcdStrikeOuttime(rs.getTime("TCD_STRIKE_OUTTIME"));
        bean.setTcdBiko(rs.getString("TCD_BIKO"));
        bean.setTcdStatus(rs.getInt("TCD_STATUS"));
        bean.setThiSid(rs.getInt("THI_SID"));
        bean.setTcdHolother(rs.getString("TCD_HOLOTHER"));
        bean.setTcdHolcnt(rs.getBigDecimal("TCD_HOLCNT"));
        bean.setTcdChkkbn(rs.getInt("TCD_CHKKBN"));
        bean.setTcdSoukbn(rs.getInt("TCD_SOUKBN"));
        bean.setTcdLockFlg(rs.getInt("TCD_LOCK_FLG"));
        bean.setTcdAuid(rs.getInt("TCD_AUID"));
        bean.setTcdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_ADATE")));
        bean.setTcdEuid(rs.getInt("TCD_EUID"));
        bean.setTcdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_EDATE")));
        bean.setTtiSid(rs.getInt("TTI_SID"));
        return bean;
    }
    
    /**
     * <p>Create TCD_TCDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdTcdataModel
     * @throws SQLException SQL実行例外
     */
    private TcdTcdataModel __getTcdTcdataFromRs(ResultSet rs) throws SQLException {
        TcdTcdataModel bean = new TcdTcdataModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTcdDate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_DATE")));
        bean.setTcdIntime(rs.getTime("TCD_INTIME"));
        bean.setTcdOuttime(rs.getTime("TCD_OUTTIME"));
        bean.setTcdStrikeIntime(rs.getTime("TCD_STRIKE_INTIME"));
        bean.setTcdStrikeOuttime(rs.getTime("TCD_STRIKE_OUTTIME"));
        bean.setTcdBiko(rs.getString("TCD_BIKO"));
        bean.setTcdStatus(rs.getInt("TCD_STATUS"));
        bean.setThiSid(rs.getInt("THI_SID"));
        bean.setTcdHolother(rs.getString("TCD_HOLOTHER"));
        bean.setTcdHolcnt(rs.getBigDecimal("TCD_HOLCNT"));
        bean.setTcdChkkbn(rs.getInt("TCD_CHKKBN"));
        bean.setTcdSoukbn(rs.getInt("TCD_SOUKBN"));
        bean.setTcdLockFlg(rs.getInt("TCD_LOCK_FLG"));
        bean.setTcdAuid(rs.getInt("TCD_AUID"));
        bean.setTcdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_ADATE")));
        bean.setTcdEuid(rs.getInt("TCD_EUID"));
        bean.setTcdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_EDATE")));
        bean.setTtiSid(rs.getInt("TTI_SID"));
        bean.setTtiRyaku(rs.getString("TTI_RYAKU"));
        return bean;
    }

    /**
     * <p>Create TCD_TCDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created Tcd010Model
     * @throws SQLException SQL実行例外
     */
    private Tcd010Model __getTcd010FromRs(ResultSet rs) throws SQLException {
        Tcd010Model bean = new Tcd010Model();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTcdDate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_DATE")));
        bean.setTcdIntime(rs.getTime("TCD_INTIME"));
        bean.setTcdOuttime(rs.getTime("TCD_OUTTIME"));
        bean.setTcdStrikeIntime(rs.getTime("TCD_STRIKE_INTIME"));
        bean.setTcdStrikeOuttime(rs.getTime("TCD_STRIKE_OUTTIME"));
        bean.setTcdBiko(rs.getString("TCD_BIKO"));
        bean.setTcdStatus(rs.getInt("TCD_STATUS"));
        bean.setThiSid(rs.getInt("THI_SID"));
        bean.setTcdHolother(rs.getString("TCD_HOLOTHER"));
        bean.setTcdHolcnt(rs.getBigDecimal("TCD_HOLCNT"));
        bean.setTcdChkkbn(rs.getInt("TCD_CHKKBN"));
        bean.setTcdSoukbn(rs.getInt("TCD_SOUKBN"));
        bean.setTcdLockFlg(rs.getInt("TCD_LOCK_FLG"));
        bean.setTcdAuid(rs.getInt("TCD_AUID"));
        bean.setTcdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_ADATE")));
        bean.setTcdEuid(rs.getInt("TCD_EUID"));
        bean.setTcdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_EDATE")));
        bean.setTtiSid(rs.getInt("TTI_SID"));

        return bean;
    }
    /**
     * <p>CSV出力用モデルを生成
     * @param rs ResultSet
     * @return created TcdCsvModel
     * @throws SQLException SQL実行例外
     */
    private TcdCsvModel __getTcdCsvFromRs(ResultSet rs) throws SQLException {
        TcdCsvModel bean = new TcdCsvModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
        bean.setSyainNo(NullDefault.getString(rs.getString("USI_SYAIN_NO"), ""));

        bean.setTcdDate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_DATE")));
        bean.setTcdIntime(rs.getTime("TCD_INTIME"));
        bean.setTcdOuttime(rs.getTime("TCD_OUTTIME"));
        bean.setTcdStrikeIntime(rs.getTime("TCD_STRIKE_INTIME"));
        bean.setTcdStrikeOuttime(rs.getTime("TCD_STRIKE_OUTTIME"));
        bean.setTcdBiko(rs.getString("TCD_BIKO"));
        bean.setTcdStatus(rs.getInt("TCD_STATUS"));
        bean.setHolKbn(rs.getInt("THI_SID"));
        bean.setThiName(rs.getString("THI_NAME"));
        bean.setThiHoltotalKbn(rs.getInt("THI_HOLTOTAL_KBN"));
        bean.setTcdHolother(rs.getString("TCD_HOLOTHER"));
        bean.setTcdHolcnt(rs.getBigDecimal("TCD_HOLCNT"));
        bean.setTcdChkkbn(rs.getInt("TCD_CHKKBN"));
        bean.setTcdSoukbn(rs.getInt("TCD_SOUKBN"));
        bean.setTcdLockFlg(rs.getInt("TCD_LOCK_FLG"));
        bean.setTcdAuid(rs.getInt("TCD_AUID"));
        bean.setTcdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_ADATE")));
        bean.setTcdEuid(rs.getInt("TCD_EUID"));
        bean.setTcdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCD_EDATE")));
        bean.setTtiRyaku(rs.getString("TTI_RYAKU"));
        return bean;
    }
}
