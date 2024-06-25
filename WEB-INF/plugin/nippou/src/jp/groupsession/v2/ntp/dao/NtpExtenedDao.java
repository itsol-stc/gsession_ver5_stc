package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpExtenedModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_EXTENED Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpExtenedDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpExtenedDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpExtenedDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpExtenedDao(Connection con) {
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
            sql.addSql("drop table NTP_EXTENED");

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
            sql.addSql(" create table NTP_EXTENED (");
            sql.addSql("   NEX_SID NUMBER(10,0) not null,");
            sql.addSql("   NEX_KBN NUMBER(10,0) not null,");
            sql.addSql("   NEX_DWEEK1 NUMBER(10,0),");
            sql.addSql("   NEX_DWEEK2 NUMBER(10,0),");
            sql.addSql("   NEX_DWEEK3 NUMBER(10,0),");
            sql.addSql("   NEX_DWEEK4 NUMBER(10,0),");
            sql.addSql("   NEX_DWEEK5 NUMBER(10,0),");
            sql.addSql("   NEX_DWEEK6 NUMBER(10,0),");
            sql.addSql("   NEX_DWEEK7 NUMBER(10,0),");
            sql.addSql("   NEX_DAY NUMBER(10,0),");
            sql.addSql("   NEX_WEEK NUMBER(10,0),");
            sql.addSql("   NEX_TIME_FR varchar(23) not null,");
            sql.addSql("   NEX_TIME_TO varchar(23) not null,");
            sql.addSql("   NEX_KADO_HH NUMBER(10,0) not null,");
            sql.addSql("   NEX_KADO_MM NUMBER(10,0) not null,");
            sql.addSql("   NEX_TRAN_KBN NUMBER(10,0) not null,");
            sql.addSql("   NEX_DATE_FR varchar(23) not null,");
            sql.addSql("   NEX_DATE_TO varchar(23) not null,");
            sql.addSql("   NEX_MGY_SID NUMBER(10,0) not null,");
            sql.addSql("   NEX_NAN_SID NUMBER(10,0) not null,");
            sql.addSql("   NEX_ACO_SID NUMBER(10,0),");
            sql.addSql("   NEX_ABO_SID NUMBER(10,0),");
            sql.addSql("   NEX_TITLE varchar(50) not null,");
            sql.addSql("   NEX_TITLE_CLO NUMBER(10,0),");
            sql.addSql("   NEX_MPR_SID NUMBER(10,0) not null,");
            sql.addSql("   NEX_MKB_SID NUMBER(10,0) not null,");
            sql.addSql("   NEX_MKH_SID NUMBER(10,0) not null,");
            sql.addSql("   NEX_TIEUP_SID NUMBER(10,0),");
            sql.addSql("   NEX_KEIZOKU NUMBER(10,0) not null,");
            sql.addSql("   NEX_ACTEND varchar(23),");
            sql.addSql("   NEX_DETAIL varchar(1000) not null,");
            sql.addSql("   NEX_ASSIGN varchar(1000),");
            sql.addSql("   NEX_KINGAKU NUMBER(10,0),");
            sql.addSql("   NEX_MIKOMI NUMBER(10,0),");
            sql.addSql("   NEX_SYOKAN varchar(1000),");
            sql.addSql("   NEX_PUBLIC NUMBER(10,0),");
            sql.addSql("   NEX_EDIT NUMBER(10,0),");
            sql.addSql("   NEX_AUID NUMBER(10,0),");
            sql.addSql("   NEX_ADATE varchar(23) not null,");
            sql.addSql("   NEX_EUID NUMBER(10,0),");
            sql.addSql("   NEX_EDATE varchar(23) not null,");
            sql.addSql("   primary key (NEX_SID)");
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
     * <p>Insert NTP_EXTENED Data Bindding JavaBean
     * @param bean NTP_EXTENED Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpExtenedModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_EXTENED(");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NEX_KBN,");
            sql.addSql("   NEX_DWEEK1,");
            sql.addSql("   NEX_DWEEK2,");
            sql.addSql("   NEX_DWEEK3,");
            sql.addSql("   NEX_DWEEK4,");
            sql.addSql("   NEX_DWEEK5,");
            sql.addSql("   NEX_DWEEK6,");
            sql.addSql("   NEX_DWEEK7,");
            sql.addSql("   NEX_DAY,");
            sql.addSql("   NEX_WEEK,");
            sql.addSql("   NEX_TIME_FR,");
            sql.addSql("   NEX_TIME_TO,");
            sql.addSql("   NEX_KADO_HH,");
            sql.addSql("   NEX_KADO_MM,");
            sql.addSql("   NEX_TRAN_KBN,");
            sql.addSql("   NEX_DATE_FR,");
            sql.addSql("   NEX_DATE_TO,");
            sql.addSql("   NEX_MGY_SID,");
            sql.addSql("   NEX_NAN_SID,");
            sql.addSql("   NEX_ACO_SID,");
            sql.addSql("   NEX_ABO_SID,");
            sql.addSql("   NEX_TITLE,");
            sql.addSql("   NEX_TITLE_CLO,");
            sql.addSql("   NEX_MPR_SID,");
            sql.addSql("   NEX_MKB_SID,");
            sql.addSql("   NEX_MKH_SID,");
            sql.addSql("   NEX_TIEUP_SID,");
            sql.addSql("   NEX_KEIZOKU,");
            sql.addSql("   NEX_ACTEND,");
            sql.addSql("   NEX_DETAIL,");
            sql.addSql("   NEX_ASSIGN,");
            sql.addSql("   NEX_KINGAKU,");
            sql.addSql("   NEX_MIKOMI,");
            sql.addSql("   NEX_SYOKAN,");
            sql.addSql("   NEX_PUBLIC,");
            sql.addSql("   NEX_EDIT,");
            sql.addSql("   NEX_AUID,");
            sql.addSql("   NEX_ADATE,");
            sql.addSql("   NEX_EUID,");
            sql.addSql("   NEX_EDATE");
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
            sql.addIntValue(bean.getNexSid());
            sql.addIntValue(bean.getNexKbn());
            sql.addIntValue(bean.getNexDweek1());
            sql.addIntValue(bean.getNexDweek2());
            sql.addIntValue(bean.getNexDweek3());
            sql.addIntValue(bean.getNexDweek4());
            sql.addIntValue(bean.getNexDweek5());
            sql.addIntValue(bean.getNexDweek6());
            sql.addIntValue(bean.getNexDweek7());
            sql.addIntValue(bean.getNexDay());
            sql.addIntValue(bean.getNexWeek());
            sql.addDateValue(bean.getNexTimeFr());
            sql.addDateValue(bean.getNexTimeTo());
            sql.addIntValue(bean.getNexKadoHh());
            sql.addIntValue(bean.getNexKadoMm());
            sql.addIntValue(bean.getNexTranKbn());
            sql.addDateValue(bean.getNexDateFr());
            sql.addDateValue(bean.getNexDateTo());
            sql.addIntValue(bean.getNexMgySid());
            sql.addIntValue(bean.getNexNanSid());
            sql.addIntValue(bean.getNexAcoSid());
            sql.addIntValue(bean.getNexAboSid());
            sql.addStrValue(bean.getNexTitle());
            sql.addIntValue(bean.getNexTitleClo());
            sql.addIntValue(bean.getNexMprSid());
            sql.addIntValue(bean.getNexMkbSid());
            sql.addIntValue(bean.getNexMkhSid());
            sql.addIntValue(bean.getNexTieupSid());
            sql.addIntValue(bean.getNexKeizoku());
            sql.addDateValue(bean.getNexActend());
            sql.addStrValue(bean.getNexDetail());
            sql.addStrValue(bean.getNexAssign());
            sql.addIntValue(bean.getNexKingaku());
            sql.addIntValue(bean.getNexMikomi());
            sql.addStrValue(bean.getNexSyokan());
            sql.addIntValue(bean.getNexPublic());
            sql.addIntValue(bean.getNexEdit());
            sql.addIntValue(bean.getNexAuid());
            sql.addDateValue(bean.getNexAdate());
            sql.addIntValue(bean.getNexEuid());
            sql.addDateValue(bean.getNexEdate());
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
     * <p>Update NTP_EXTENED Data Bindding JavaBean
     * @param bean NTP_EXTENED Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpExtenedModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_EXTENED");
            sql.addSql(" set ");
            sql.addSql("   NEX_KBN=?,");
            sql.addSql("   NEX_DWEEK1=?,");
            sql.addSql("   NEX_DWEEK2=?,");
            sql.addSql("   NEX_DWEEK3=?,");
            sql.addSql("   NEX_DWEEK4=?,");
            sql.addSql("   NEX_DWEEK5=?,");
            sql.addSql("   NEX_DWEEK6=?,");
            sql.addSql("   NEX_DWEEK7=?,");
            sql.addSql("   NEX_DAY=?,");
            sql.addSql("   NEX_WEEK=?,");
            sql.addSql("   NEX_TIME_FR=?,");
            sql.addSql("   NEX_TIME_TO=?,");
            sql.addSql("   NEX_KADO_HH=?,");
            sql.addSql("   NEX_KADO_MM=?,");
            sql.addSql("   NEX_TRAN_KBN=?,");
            sql.addSql("   NEX_DATE_FR=?,");
            sql.addSql("   NEX_DATE_TO=?,");
            sql.addSql("   NEX_MGY_SID=?,");
            sql.addSql("   NEX_NAN_SID=?,");
            sql.addSql("   NEX_ACO_SID=?,");
            sql.addSql("   NEX_ABO_SID=?,");
            sql.addSql("   NEX_TITLE=?,");
            sql.addSql("   NEX_TITLE_CLO=?,");
            sql.addSql("   NEX_MPR_SID=?,");
            sql.addSql("   NEX_MKB_SID=?,");
            sql.addSql("   NEX_MKH_SID=?,");
            sql.addSql("   NEX_TIEUP_SID=?,");
            sql.addSql("   NEX_KEIZOKU=?,");
            sql.addSql("   NEX_ACTEND=?,");
            sql.addSql("   NEX_DETAIL=?,");
            sql.addSql("   NEX_ASSIGN=?,");
            sql.addSql("   NEX_KINGAKU=?,");
            sql.addSql("   NEX_MIKOMI=?,");
            sql.addSql("   NEX_SYOKAN=?,");
            sql.addSql("   NEX_PUBLIC=?,");
            sql.addSql("   NEX_EDIT=?,");
            sql.addSql("   NEX_AUID=?,");
            sql.addSql("   NEX_ADATE=?,");
            sql.addSql("   NEX_EUID=?,");
            sql.addSql("   NEX_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NEX_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNexKbn());
            sql.addIntValue(bean.getNexDweek1());
            sql.addIntValue(bean.getNexDweek2());
            sql.addIntValue(bean.getNexDweek3());
            sql.addIntValue(bean.getNexDweek4());
            sql.addIntValue(bean.getNexDweek5());
            sql.addIntValue(bean.getNexDweek6());
            sql.addIntValue(bean.getNexDweek7());
            sql.addIntValue(bean.getNexDay());
            sql.addIntValue(bean.getNexWeek());
            sql.addDateValue(bean.getNexTimeFr());
            sql.addDateValue(bean.getNexTimeTo());
            sql.addIntValue(bean.getNexKadoHh());
            sql.addIntValue(bean.getNexKadoMm());
            sql.addIntValue(bean.getNexTranKbn());
            sql.addDateValue(bean.getNexDateFr());
            sql.addDateValue(bean.getNexDateTo());
            sql.addIntValue(bean.getNexMgySid());
            sql.addIntValue(bean.getNexNanSid());
            sql.addIntValue(bean.getNexAcoSid());
            sql.addIntValue(bean.getNexAboSid());
            sql.addStrValue(bean.getNexTitle());
            sql.addIntValue(bean.getNexTitleClo());
            sql.addIntValue(bean.getNexMprSid());
            sql.addIntValue(bean.getNexMkbSid());
            sql.addIntValue(bean.getNexMkhSid());
            sql.addIntValue(bean.getNexTieupSid());
            sql.addIntValue(bean.getNexKeizoku());
            sql.addDateValue(bean.getNexActend());
            sql.addStrValue(bean.getNexDetail());
            sql.addStrValue(bean.getNexAssign());
            sql.addIntValue(bean.getNexKingaku());
            sql.addIntValue(bean.getNexMikomi());
            sql.addStrValue(bean.getNexSyokan());
            sql.addIntValue(bean.getNexPublic());
            sql.addIntValue(bean.getNexEdit());
            sql.addIntValue(bean.getNexAuid());
            sql.addDateValue(bean.getNexAdate());
            sql.addIntValue(bean.getNexEuid());
            sql.addDateValue(bean.getNexEdate());
            //where
            sql.addIntValue(bean.getNexSid());

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
     * <p>Select NTP_EXTENED All Data
     * @return List in NTP_EXTENEDModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpExtenedModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpExtenedModel> ret = new ArrayList<NtpExtenedModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NEX_KBN,");
            sql.addSql("   NEX_DWEEK1,");
            sql.addSql("   NEX_DWEEK2,");
            sql.addSql("   NEX_DWEEK3,");
            sql.addSql("   NEX_DWEEK4,");
            sql.addSql("   NEX_DWEEK5,");
            sql.addSql("   NEX_DWEEK6,");
            sql.addSql("   NEX_DWEEK7,");
            sql.addSql("   NEX_DAY,");
            sql.addSql("   NEX_WEEK,");
            sql.addSql("   NEX_TIME_FR,");
            sql.addSql("   NEX_TIME_TO,");
            sql.addSql("   NEX_KADO_HH,");
            sql.addSql("   NEX_KADO_MM,");
            sql.addSql("   NEX_TRAN_KBN,");
            sql.addSql("   NEX_DATE_FR,");
            sql.addSql("   NEX_DATE_TO,");
            sql.addSql("   NEX_MGY_SID,");
            sql.addSql("   NEX_NAN_SID,");
            sql.addSql("   NEX_ACO_SID,");
            sql.addSql("   NEX_ABO_SID,");
            sql.addSql("   NEX_TITLE,");
            sql.addSql("   NEX_TITLE_CLO,");
            sql.addSql("   NEX_MPR_SID,");
            sql.addSql("   NEX_MKB_SID,");
            sql.addSql("   NEX_MKH_SID,");
            sql.addSql("   NEX_TIEUP_SID,");
            sql.addSql("   NEX_KEIZOKU,");
            sql.addSql("   NEX_ACTEND,");
            sql.addSql("   NEX_DETAIL,");
            sql.addSql("   NEX_ASSIGN,");
            sql.addSql("   NEX_KINGAKU,");
            sql.addSql("   NEX_MIKOMI,");
            sql.addSql("   NEX_SYOKAN,");
            sql.addSql("   NEX_PUBLIC,");
            sql.addSql("   NEX_EDIT,");
            sql.addSql("   NEX_AUID,");
            sql.addSql("   NEX_ADATE,");
            sql.addSql("   NEX_EUID,");
            sql.addSql("   NEX_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_EXTENED");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpExtenedFromRs(rs));
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
     * <p>Select NTP_EXTENED
     * @param nexSid NEX_SID
     * @return NTP_EXTENEDModel
     * @throws SQLException SQL実行例外
     */
    public NtpExtenedModel select(int nexSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpExtenedModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NEX_KBN,");
            sql.addSql("   NEX_DWEEK1,");
            sql.addSql("   NEX_DWEEK2,");
            sql.addSql("   NEX_DWEEK3,");
            sql.addSql("   NEX_DWEEK4,");
            sql.addSql("   NEX_DWEEK5,");
            sql.addSql("   NEX_DWEEK6,");
            sql.addSql("   NEX_DWEEK7,");
            sql.addSql("   NEX_DAY,");
            sql.addSql("   NEX_WEEK,");
            sql.addSql("   NEX_TIME_FR,");
            sql.addSql("   NEX_TIME_TO,");
            sql.addSql("   NEX_KADO_HH,");
            sql.addSql("   NEX_KADO_MM,");
            sql.addSql("   NEX_TRAN_KBN,");
            sql.addSql("   NEX_DATE_FR,");
            sql.addSql("   NEX_DATE_TO,");
            sql.addSql("   NEX_MGY_SID,");
            sql.addSql("   NEX_NAN_SID,");
            sql.addSql("   NEX_ACO_SID,");
            sql.addSql("   NEX_ABO_SID,");
            sql.addSql("   NEX_TITLE,");
            sql.addSql("   NEX_TITLE_CLO,");
            sql.addSql("   NEX_MPR_SID,");
            sql.addSql("   NEX_MKB_SID,");
            sql.addSql("   NEX_MKH_SID,");
            sql.addSql("   NEX_TIEUP_SID,");
            sql.addSql("   NEX_KEIZOKU,");
            sql.addSql("   NEX_ACTEND,");
            sql.addSql("   NEX_DETAIL,");
            sql.addSql("   NEX_ASSIGN,");
            sql.addSql("   NEX_KINGAKU,");
            sql.addSql("   NEX_MIKOMI,");
            sql.addSql("   NEX_SYOKAN,");
            sql.addSql("   NEX_PUBLIC,");
            sql.addSql("   NEX_EDIT,");
            sql.addSql("   NEX_AUID,");
            sql.addSql("   NEX_ADATE,");
            sql.addSql("   NEX_EUID,");
            sql.addSql("   NEX_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_EXTENED");
            sql.addSql(" where ");
            sql.addSql("   NEX_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nexSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpExtenedFromRs(rs);
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
     * <p>Delete NTP_EXTENED
     * @param nexSid NEX_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int nexSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_EXTENED");
            sql.addSql(" where ");
            sql.addSql("   NEX_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nexSid);

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
     * <p>Create NTP_EXTENED Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpExtenedModel
     * @throws SQLException SQL実行例外
     */
    private NtpExtenedModel __getNtpExtenedFromRs(ResultSet rs) throws SQLException {
        NtpExtenedModel bean = new NtpExtenedModel();
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNexKbn(rs.getInt("NEX_KBN"));
        bean.setNexDweek1(rs.getInt("NEX_DWEEK1"));
        bean.setNexDweek2(rs.getInt("NEX_DWEEK2"));
        bean.setNexDweek3(rs.getInt("NEX_DWEEK3"));
        bean.setNexDweek4(rs.getInt("NEX_DWEEK4"));
        bean.setNexDweek5(rs.getInt("NEX_DWEEK5"));
        bean.setNexDweek6(rs.getInt("NEX_DWEEK6"));
        bean.setNexDweek7(rs.getInt("NEX_DWEEK7"));
        bean.setNexDay(rs.getInt("NEX_DAY"));
        bean.setNexWeek(rs.getInt("NEX_WEEK"));
        bean.setNexTimeFr(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_TIME_FR")));
        bean.setNexTimeTo(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_TIME_TO")));
        bean.setNexKadoHh(rs.getInt("NEX_KADO_HH"));
        bean.setNexKadoMm(rs.getInt("NEX_KADO_MM"));
        bean.setNexTranKbn(rs.getInt("NEX_TRAN_KBN"));
        bean.setNexDateFr(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_DATE_FR")));
        bean.setNexDateTo(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_DATE_TO")));
        bean.setNexMgySid(rs.getInt("NEX_MGY_SID"));
        bean.setNexNanSid(rs.getInt("NEX_NAN_SID"));
        bean.setNexAcoSid(rs.getInt("NEX_ACO_SID"));
        bean.setNexAboSid(rs.getInt("NEX_ABO_SID"));
        bean.setNexTitle(rs.getString("NEX_TITLE"));
        bean.setNexTitleClo(rs.getInt("NEX_TITLE_CLO"));
        bean.setNexMprSid(rs.getInt("NEX_MPR_SID"));
        bean.setNexMkbSid(rs.getInt("NEX_MKB_SID"));
        bean.setNexMkhSid(rs.getInt("NEX_MKH_SID"));
        bean.setNexTieupSid(rs.getInt("NEX_TIEUP_SID"));
        bean.setNexKeizoku(rs.getInt("NEX_KEIZOKU"));
        bean.setNexActend(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_ACTEND")));
        bean.setNexDetail(rs.getString("NEX_DETAIL"));
        bean.setNexAssign(rs.getString("NEX_ASSIGN"));
        bean.setNexKingaku(rs.getInt("NEX_KINGAKU"));
        bean.setNexMikomi(rs.getInt("NEX_MIKOMI"));
        bean.setNexSyokan(rs.getString("NEX_SYOKAN"));
        bean.setNexPublic(rs.getInt("NEX_PUBLIC"));
        bean.setNexEdit(rs.getInt("NEX_EDIT"));
        bean.setNexAuid(rs.getInt("NEX_AUID"));
        bean.setNexAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_ADATE")));
        bean.setNexEuid(rs.getInt("NEX_EUID"));
        bean.setNexEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_EDATE")));
        return bean;
    }
}
