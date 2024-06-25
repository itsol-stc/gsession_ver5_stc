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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.model.NtpDataModel;

/**
 * <p>NTP_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpDataDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpDataDao(Connection con) {
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
            sql.addSql("drop table NTP_DATA");

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
            sql.addSql(" create table NTP_DATA (");
            sql.addSql("   NIP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_DATE varchar(23) not null,");
            sql.addSql("   NIP_FR_TIME varchar(23) not null,");
            sql.addSql("   NIP_TO_TIME varchar(23) not null,");
            sql.addSql("   NIP_KADO_HH NUMBER(10,0) not null,");
            sql.addSql("   NIP_KADO_MM NUMBER(10,0) not null,");
            sql.addSql("   NIP_MGY_SID NUMBER(10,0) not null,");
            sql.addSql("   NAN_SID NUMBER(10,0) not null,");
            sql.addSql("   ACO_SID NUMBER(10,0),");
            sql.addSql("   ABA_SID NUMBER(10,0),");
            sql.addSql("   NIP_TITLE varchar(50) not null,");
            sql.addSql("   NIP_TITLE_CLO NUMBER(10,0),");
            sql.addSql("   MPR_SID NUMBER(10,0) not null,");
            sql.addSql("   MKB_SID NUMBER(10,0) not null,");
            sql.addSql("   MKH_SID NUMBER(10,0) not null,");
            sql.addSql("   NIP_TIEUP_SID NUMBER(10,0),");
            sql.addSql("   NIP_KEIZOKU NUMBER(10,0) not null,");
            sql.addSql("   NIP_ACTEND varchar(23),");
            sql.addSql("   NIP_DETAIL varchar(1000) not null,");
            sql.addSql("   NIP_ACTION_DATE timestamp,");
            sql.addSql("   NIP_ACTION varchar(1000),");
            sql.addSql("   NIP_ACTDATE_KBN NUMBER(10,0),");
            sql.addSql("   NIP_ASSIGN varchar(1000),");
            sql.addSql("   NIP_KINGAKU NUMBER(10,0),");
            sql.addSql("   NIP_MIKOMI NUMBER(10,0),");
            sql.addSql("   NIP_SYOKAN varchar(1000),");
            sql.addSql("   NIP_PUBLIC NUMBER(10,0),");
            sql.addSql("   NIP_EDIT NUMBER(10,0),");
            sql.addSql("   NEX_SID NUMBER(10,0),");
            sql.addSql("   NIP_AUID NUMBER(10,0),");
            sql.addSql("   NIP_ADATE varchar(23) not null,");
            sql.addSql("   NIP_EUID NUMBER(10,0),");
            sql.addSql("   NIP_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NIP_SID)");
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
     * <p>Insert NTP_DATA Data Bindding JavaBean
     * @param bean NTP_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_DATA(");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ACTION_DATE,");
            sql.addSql("   NIP_ACTION,");
            sql.addSql("   NIP_ACTDATE_KBN,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
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
            sql.addIntValue(bean.getNipSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getNipDate());
            sql.addDateValue(bean.getNipFrTime());
            sql.addDateValue(bean.getNipToTime());
            sql.addIntValue(bean.getNipKadoHh());
            sql.addIntValue(bean.getNipKadoMm());
            sql.addIntValue(bean.getNipMgySid());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addStrValue(bean.getNipTitle());
            sql.addIntValue(bean.getNipTitleClo());
            sql.addIntValue(bean.getMprSid());
            sql.addIntValue(bean.getMkbSid());
            sql.addIntValue(bean.getMkhSid());
            sql.addIntValue(bean.getNipTieupSid());
            sql.addIntValue(bean.getNipKeizoku());
            sql.addDateValue(bean.getNipActend());
            sql.addStrValue(bean.getNipDetail());
            sql.addDateValue(bean.getNipActionDate());
            sql.addStrValue(bean.getNipAction());
            sql.addIntValue(bean.getNipActDateKbn());
            sql.addStrValue(bean.getNipAssign());
            sql.addIntValue(bean.getNipKingaku());
            sql.addIntValue(bean.getNipMikomi());
            sql.addStrValue(bean.getNipSyokan());
            sql.addIntValue(bean.getNipPublic());
            sql.addIntValue(bean.getNipEdit());
            sql.addIntValue(bean.getNexSid());
            sql.addIntValue(bean.getNipAuid());
            sql.addDateValue(bean.getNipAdate());
            sql.addIntValue(bean.getNipEuid());
            sql.addDateValue(bean.getNipEdate());
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
     * <p>Insert NTP_DATA DataList
     * @param beanList NTP_DATA DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<NtpDataModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_DATA(");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ACTION_DATE,");
            sql.addSql("   NIP_ACTION,");
            sql.addSql("   NIP_ACTDATE_KBN,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
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

            for (NtpDataModel bean : beanList) {
                sql.addIntValue(bean.getNipSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addDateValue(bean.getNipDate());
                sql.addDateValue(bean.getNipFrTime());
                sql.addDateValue(bean.getNipToTime());
                sql.addIntValue(bean.getNipKadoHh());
                sql.addIntValue(bean.getNipKadoMm());
                sql.addIntValue(bean.getNipMgySid());
                sql.addIntValue(bean.getNanSid());
                sql.addIntValue(bean.getAcoSid());
                sql.addIntValue(bean.getAbaSid());
                sql.addStrValue(bean.getNipTitle());
                sql.addIntValue(bean.getNipTitleClo());
                sql.addIntValue(bean.getMprSid());
                sql.addIntValue(bean.getMkbSid());
                sql.addIntValue(bean.getMkhSid());
                sql.addIntValue(bean.getNipTieupSid());
                sql.addIntValue(bean.getNipKeizoku());
                sql.addDateValue(bean.getNipActend());
                sql.addStrValue(bean.getNipDetail());
                sql.addDateValue(bean.getNipActionDate());
                sql.addStrValue(bean.getNipAction());
                sql.addIntValue(bean.getNipActDateKbn());
                sql.addStrValue(bean.getNipAssign());
                sql.addIntValue(bean.getNipKingaku());
                sql.addIntValue(bean.getNipMikomi());
                sql.addStrValue(bean.getNipSyokan());
                sql.addIntValue(bean.getNipPublic());
                sql.addIntValue(bean.getNipEdit());
                sql.addIntValue(bean.getNexSid());
                sql.addIntValue(bean.getNipAuid());
                sql.addDateValue(bean.getNipAdate());
                sql.addIntValue(bean.getNipEuid());
                sql.addDateValue(bean.getNipEdate());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <p>Update NTP_DATA Data Bindding JavaBean
     * @param bean NTP_DATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_DATA");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   NIP_DATE=?,");
            sql.addSql("   NIP_FR_TIME=?,");
            sql.addSql("   NIP_TO_TIME=?,");
            sql.addSql("   NIP_KADO_HH=?,");
            sql.addSql("   NIP_KADO_MM=?,");
            sql.addSql("   NIP_MGY_SID=?,");
            sql.addSql("   NAN_SID=?,");
            sql.addSql("   ACO_SID=?,");
            sql.addSql("   ABA_SID=?,");
            sql.addSql("   NIP_TITLE=?,");
            sql.addSql("   NIP_TITLE_CLO=?,");
            sql.addSql("   MPR_SID=?,");
            sql.addSql("   MKB_SID=?,");
            sql.addSql("   MKH_SID=?,");
            sql.addSql("   NIP_TIEUP_SID=?,");
            sql.addSql("   NIP_KEIZOKU=?,");
            sql.addSql("   NIP_ACTEND=?,");
            sql.addSql("   NIP_DETAIL=?,");
            sql.addSql("   NIP_ACTION_DATE=?,");
            sql.addSql("   NIP_ACTION=?,");
            sql.addSql("   NIP_ACTDATE_KBN=?,");
            sql.addSql("   NIP_ASSIGN=?,");
            sql.addSql("   NIP_KINGAKU=?,");
            sql.addSql("   NIP_MIKOMI=?,");
            sql.addSql("   NIP_SYOKAN=?,");
            sql.addSql("   NIP_PUBLIC=?,");
            sql.addSql("   NIP_EDIT=?,");
            sql.addSql("   NEX_SID=?,");
            sql.addSql("   NIP_AUID=?,");
            sql.addSql("   NIP_ADATE=?,");
            sql.addSql("   NIP_EUID=?,");
            sql.addSql("   NIP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getNipDate());
            sql.addDateValue(bean.getNipFrTime());
            sql.addDateValue(bean.getNipToTime());
            sql.addIntValue(bean.getNipKadoHh());
            sql.addIntValue(bean.getNipKadoMm());
            sql.addIntValue(bean.getNipMgySid());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addStrValue(bean.getNipTitle());
            sql.addIntValue(bean.getNipTitleClo());
            sql.addIntValue(bean.getMprSid());
            sql.addIntValue(bean.getMkbSid());
            sql.addIntValue(bean.getMkhSid());
            sql.addIntValue(bean.getNipTieupSid());
            sql.addIntValue(bean.getNipKeizoku());
            sql.addDateValue(bean.getNipActend());
            sql.addStrValue(bean.getNipDetail());
            sql.addDateValue(bean.getNipActionDate());
            sql.addStrValue(bean.getNipAction());
            sql.addIntValue(bean.getNipActDateKbn());
            sql.addStrValue(bean.getNipAssign());
            sql.addIntValue(bean.getNipKingaku());
            sql.addIntValue(bean.getNipMikomi());
            sql.addStrValue(bean.getNipSyokan());
            sql.addIntValue(bean.getNipPublic());
            sql.addIntValue(bean.getNipEdit());
            sql.addIntValue(bean.getNexSid());
            sql.addIntValue(bean.getNipAuid());
            sql.addDateValue(bean.getNipAdate());
            sql.addIntValue(bean.getNipEuid());
            sql.addDateValue(bean.getNipEdate());
            //where
            sql.addIntValue(bean.getNipSid());

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
     * <p>Select NTP_DATA All Data
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ACTION_DATE,");
            sql.addSql("   NIP_ACTION,");
            sql.addSql("   NIP_ACTDATE_KBN,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpDataFromRs(rs));
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
     * <p>Select NTP_DATA
     * @param nipSid NIP_SID
     * @return NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public NtpDataModel select(int nipSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ACTION_DATE,");
            sql.addSql("   NIP_ACTION,");
            sql.addSql("   NIP_ACTDATE_KBN,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpDataFromRs(rs);
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
     * <p>指定範囲の日報データを取得する
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpDataModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ACTION_DATE,");
            sql.addSql("   NIP_ACTION,");
            sql.addSql("   NIP_ACTDATE_KBN,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");
            sql.addSql(" order by ");
            sql.addSql("   NIP_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpDataFromRs(rs));
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
     * <p>全件数を取得する
     * @return NTP_DATA count
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete NTP_DATA
     * @param nipSid NIP_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(int nipSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   NIP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);

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
     * <p>Delete NTP_DATA
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     */
    public void deleteUsrData(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <br>[機  能] 指定したユーザの日報情報(SID)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return 日報情報(SID
     * @throws SQLException
     */
    public List<Integer> getUserDataList(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NIP_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("NIP_SID"));
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
     * <p>削除する日報のSIDを取得します
     * <br>基準日も含みます。また時間、分、秒も比較対照になります。
     * @param bdate 基準日
     * @return int 削除数
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getDeleteNipSid(UDate bdate) throws SQLException {

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        ArrayList<String> ret = new ArrayList<String>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NIP_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   NIP_DATE <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bdate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(String.valueOf(rs.getInt("NIP_SID")));
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
     * <p>削除基準日を指定し、(終了日付が)基準日より古い日報情報を削除します
     * <br>基準日も含みます。また時間、分、秒も比較対照になります。
     * @param bdate 基準日
     * @return int 削除数
     * @throws SQLException SQL実行例外
     */
    public int deleteOldNippou(UDate bdate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   NIP_DATE <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bdate);

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
     * <br>[機  能] 指定された日報のバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param ntpSid 日報SID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfForWrite(int ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select BIN_SID from NTP_BIN");
            sql.addSql("      where NTP_SID = ?");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(ntpSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <p>Create NTP_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpDataModel
     * @throws SQLException SQL実行例外
     */
    private NtpDataModel __getNtpDataFromRs(ResultSet rs) throws SQLException {
        NtpDataModel bean = new NtpDataModel();
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNipDate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_DATE")));
        bean.setNipFrTime(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME")));
        bean.setNipToTime(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME")));
        bean.setNipKadoHh(rs.getInt("NIP_KADO_HH"));
        bean.setNipKadoMm(rs.getInt("NIP_KADO_MM"));
        bean.setNipMgySid(rs.getInt("NIP_MGY_SID"));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNipTitle(rs.getString("NIP_TITLE"));
        bean.setNipTitleClo(rs.getInt("NIP_TITLE_CLO"));
        bean.setMprSid(rs.getInt("MPR_SID"));
        bean.setMkbSid(rs.getInt("MKB_SID"));
        bean.setMkhSid(rs.getInt("MKH_SID"));
        bean.setNipTieupSid(rs.getInt("NIP_TIEUP_SID"));
        bean.setNipKeizoku(rs.getInt("NIP_KEIZOKU"));
        bean.setNipActend(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_ACTEND")));
        bean.setNipDetail(rs.getString("NIP_DETAIL"));
        bean.setNipActionDate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_ACTION_DATE")));
        bean.setNipAction(rs.getString("NIP_ACTION"));
        bean.setNipActDateKbn(rs.getInt("NIP_ACTDATE_KBN"));
        bean.setNipAssign(rs.getString("NIP_ASSIGN"));
        bean.setNipKingaku(rs.getInt("NIP_KINGAKU"));
        bean.setNipMikomi(rs.getInt("NIP_MIKOMI"));
        bean.setNipSyokan(rs.getString("NIP_SYOKAN"));
        bean.setNipPublic(rs.getInt("NIP_PUBLIC"));
        bean.setNipEdit(rs.getInt("NIP_EDIT"));
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNipAuid(rs.getInt("NIP_AUID"));
        bean.setNipAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_ADATE")));
        bean.setNipEuid(rs.getInt("NIP_EUID"));
        bean.setNipEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_EDATE")));
        return bean;
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
           sql.addSql("   sum(octet_length(NIP_TITLE)) as NIP_TITLE,");
           sql.addSql("   sum(octet_length(NIP_DETAIL)) as NIP_DETAIL,");
           sql.addSql("   sum(octet_length(NIP_ASSIGN)) as NIP_ASSIGN,");
           sql.addSql("   sum(octet_length(NIP_SYOKAN)) as NIP_SYOKAN,");
           sql.addSql("   sum(octet_length(NIP_ACTION)) as NIP_ACTION");
           sql.addSql(" from ");
           sql.addSql("   NTP_DATA ");
           sql.addSql(" where ");
           sql.addSql("   NIP_SID in ");
           sql.addSql(inSidStr);
           
           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("NIP_TITLE");
               ret += rs.getLong("NIP_DETAIL");
               ret += rs.getLong("NIP_ASSIGN");
               ret += rs.getLong("NIP_SYOKAN");
               ret += rs.getLong("NIP_ACTION");
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
