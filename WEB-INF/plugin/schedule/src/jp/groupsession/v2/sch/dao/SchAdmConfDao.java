package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchAdmConfModel;

/**
 * <p>SCH_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public SchAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchAdmConfDao(Connection con) {
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
            sql.addSql("drop table SCH_ADM_CONF");

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
            sql.addSql(" create table SCH_ADM_CONF (");
            sql.addSql("   SAD_CRANGE NUMBER(10,0) not null,");
            sql.addSql("   SAD_ATDEL_FLG NUMBER(10,0) not null,");
            sql.addSql("   SAD_ATDEL_Y NUMBER(10,0),");
            sql.addSql("   SAD_ATDEL_M NUMBER(10,0),");
            sql.addSql("   SAD_HOUR_DIV NUMBER(10,0),");
            sql.addSql("   SAD_SORT_KBN NUMBER(10,0) not null,");
            sql.addSql("   SAD_SORT_KEY1 NUMBER(10,0) not null,");
            sql.addSql("   SAD_SORT_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   SAD_SORT_KEY2 NUMBER(10,0) not null,");
            sql.addSql("   SAD_SORT_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_EDIT_STYPE NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_EDIT NUMBER(10,0) not null,");
            sql.addSql("   SAD_REPEAT_STYPE NUMBER(10,0) not null,");
            sql.addSql("   SAD_REPEAT_KBN NUMBER(10,0) not null,");
            sql.addSql("   SAD_REPEAT_MY_KBN NUMBER(10,0) not null,");
            sql.addSql("   SAD_AUID NUMBER(10,0) not null,");
            sql.addSql("   SAD_ADATE varchar(23) not null,");
            sql.addSql("   SAD_EUID NUMBER(10,0) not null,");
            sql.addSql("   SAD_EDATE varchar(23) not null,");
            sql.addSql("   SAD_DSP_GROUP NUMBER(10,0) not null,");
            sql.addSql("   SAD_MSG_COLOR_KBN(10,0) not null,");
            sql.addSql("   SAD_SMAIL_SEND_KBN(10,0) not null,");
            sql.addSql("   SAD_SMAIL(10,0) not null,");
            sql.addSql("   SAD_SMAIL_GROUP(10,0) not null,");
            sql.addSql("   SAD_SMAIL_ATTEND(10,0) not null,");
            sql.addSql("   SAD_INI_PUBLIC (10,0) not null,");
            sql.addSql("   SAD_PUBLIC_KBN (10,0) not null,");
            sql.addSql("   SAD_INI_SAME_STYPE (10,0) not null,");
            sql.addSql("   SAD_INI_SAME (10,0) not null,");
            sql.addSql("   SAD_AM_FR_H NUMBER(10,0) not null,");
            sql.addSql("   SAD_AM_FR_M NUMBER(10,0) not null,");
            sql.addSql("   SAD_AM_TO_H NUMBER(10,0) not null,");
            sql.addSql("   SAD_AM_TO_M NUMBER(10,0) not null,");
            sql.addSql("   SAD_PM_FR_H NUMBER(10,0) not null,");
            sql.addSql("   SAD_PM_FR_M NUMBER(10,0) not null,");
            sql.addSql("   SAD_PM_TO_H NUMBER(10,0) not null,");
            sql.addSql("   SAD_PM_TO_M NUMBER(10,0) not null,");
            sql.addSql("   SAD_ALL_FR_H NUMBER(10,0) not null,");
            sql.addSql("   SAD_ALL_FR_M NUMBER(10,0) not null,");
            sql.addSql("   SAD_ALL_TO_H NUMBER(10,0) not null,");
            sql.addSql("   SAD_ALL_TO_M NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_TIME_STYPE NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_FRH NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_FRM NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_TOH NUMBER(10,0) not null,");
            sql.addSql("   SAD_INI_TOM NUMBER(10,0) not null ");
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
     * <p>Insert SCH_ADM_CONF Data Bindding JavaBean
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_ADM_CONF(");
            sql.addSql("   SAD_CRANGE,");
            sql.addSql("   SAD_ATDEL_FLG,");
            sql.addSql("   SAD_ATDEL_Y,");
            sql.addSql("   SAD_ATDEL_M,");
            sql.addSql("   SAD_HOUR_DIV,");
            sql.addSql("   SAD_SORT_KBN,");
            sql.addSql("   SAD_SORT_KEY1,");
            sql.addSql("   SAD_SORT_ORDER1,");
            sql.addSql("   SAD_SORT_KEY2,");
            sql.addSql("   SAD_SORT_ORDER2,");
            sql.addSql("   SAD_INI_EDIT_STYPE,");
            sql.addSql("   SAD_INI_EDIT,");
            sql.addSql("   SAD_REPEAT_STYPE,");
            sql.addSql("   SAD_REPEAT_KBN,");
            sql.addSql("   SAD_REPEAT_MY_KBN,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE,");
            sql.addSql("   SAD_DSP_GROUP,");
            sql.addSql("   SAD_MSG_COLOR_KBN,");
            sql.addSql("   SAD_SMAIL_SEND_KBN,");
            sql.addSql("   SAD_SMAIL,");
            sql.addSql("   SAD_SMAIL_GROUP,");
            sql.addSql("   SAD_SMAIL_ATTEND,");
            sql.addSql("   SAD_INI_PUBLIC,");
            sql.addSql("   SAD_PUBLIC_KBN,");
            sql.addSql("   SAD_INI_SAME_STYPE,");
            sql.addSql("   SAD_INI_SAME,");
            sql.addSql("   SAD_AM_FR_H,");
            sql.addSql("   SAD_AM_FR_M,");
            sql.addSql("   SAD_AM_TO_H,");
            sql.addSql("   SAD_AM_TO_M,");
            sql.addSql("   SAD_PM_FR_H,");
            sql.addSql("   SAD_PM_FR_M,");
            sql.addSql("   SAD_PM_TO_H,");
            sql.addSql("   SAD_PM_TO_M,");
            sql.addSql("   SAD_ALL_FR_H,");
            sql.addSql("   SAD_ALL_FR_M,");
            sql.addSql("   SAD_ALL_TO_H,");
            sql.addSql("   SAD_ALL_TO_M,");
            sql.addSql("   SAD_INI_TIME_STYPE,");
            sql.addSql("   SAD_INI_FRH,");
            sql.addSql("   SAD_INI_FRM,");
            sql.addSql("   SAD_INI_TOH,");
            sql.addSql("   SAD_INI_TOM");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadCrange());
            sql.addIntValue(bean.getSadAtdelFlg());
            sql.addIntValue(bean.getSadAtdelY());
            sql.addIntValue(bean.getSadAtdelM());
            sql.addIntValue(bean.getSadHourDiv());
            sql.addIntValue(bean.getSadSortKbn());
            sql.addIntValue(bean.getSadSortKey1());
            sql.addIntValue(bean.getSadSortOrder1());
            sql.addIntValue(bean.getSadSortKey2());
            sql.addIntValue(bean.getSadSortOrder2());
            sql.addIntValue(bean.getSadIniEditStype());
            sql.addIntValue(bean.getSadIniEdit());
            sql.addIntValue(bean.getSadRepeatStype());
            sql.addIntValue(bean.getSadRepeatKbn());
            sql.addIntValue(bean.getSadRepeatMyKbn());
            sql.addIntValue(bean.getSadAuid());
            sql.addDateValue(bean.getSadAdate());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());
            sql.addIntValue(bean.getSadDspGroup());
            sql.addIntValue(bean.getSadMsgColorKbn());
            sql.addIntValue(bean.getSadSmailSendKbn());
            sql.addIntValue(bean.getSadSmail());
            sql.addIntValue(bean.getSadSmailGroup());
            sql.addIntValue(bean.getSadSmailAttend());
            sql.addIntValue(bean.getSadIniPublic());
            sql.addIntValue(bean.getSadInitPublicStype());
            sql.addIntValue(bean.getSadIniSameStype());
            sql.addIntValue(bean.getSadIniSame());
            sql.addIntValue(bean.getSadAmFrH());
            sql.addIntValue(bean.getSadAmFrM());
            sql.addIntValue(bean.getSadAmToH());
            sql.addIntValue(bean.getSadAmToM());
            sql.addIntValue(bean.getSadPmFrH());
            sql.addIntValue(bean.getSadPmFrM());
            sql.addIntValue(bean.getSadPmToH());
            sql.addIntValue(bean.getSadPmToM());
            sql.addIntValue(bean.getSadAllFrH());
            sql.addIntValue(bean.getSadAllFrM());
            sql.addIntValue(bean.getSadAllToH());
            sql.addIntValue(bean.getSadAllToM());
            sql.addIntValue(bean.getSadIniTimeStype());
            sql.addIntValue(bean.getSadIniFrH());
            sql.addIntValue(bean.getSadIniFrM());
            sql.addIntValue(bean.getSadIniToH());
            sql.addIntValue(bean.getSadIniToM());

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
     * <p>Update SCH_ADM_CONF Data Bindding JavaBean
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchAdmConfModel bean) throws SQLException {
        SqlBuffer sql = new SqlBuffer();
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_CRANGE=?,");
            sql.addSql("   SAD_ATDEL_FLG=?,");
            sql.addSql("   SAD_ATDEL_Y=?,");
            sql.addSql("   SAD_ATDEL_M=?,");
            sql.addSql("   SAD_HOUR_DIV=?,");
            sql.addSql("   SAD_SORT_KBN=?,");
            sql.addSql("   SAD_SORT_KEY1=?,");
            sql.addSql("   SAD_SORT_ORDER1=?,");
            sql.addSql("   SAD_SORT_KEY2=?,");
            sql.addSql("   SAD_SORT_ORDER2=?,");
            sql.addSql("   SAD_INI_TIME_STYPE=?,");
            sql.addSql("   SAD_INI_FRH=?,");
            sql.addSql("   SAD_INI_FRM=?,");
            sql.addSql("   SAD_INI_TOH=?,");
            sql.addSql("   SAD_INI_TOM=?,");
            sql.addSql("   SAD_INI_EDIT_STYPE=?,");
            sql.addSql("   SAD_INI_EDIT=?,");
            sql.addSql("   SAD_REPEAT_STYPE=?,");
            sql.addSql("   SAD_REPEAT_KBN=?,");
            sql.addSql("   SAD_REPEAT_MY_KBN=?,");
            sql.addSql("   SAD_AUID=?,");
            sql.addSql("   SAD_ADATE=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?,");
            sql.addSql("   SAD_DSP_GROUP=?,");
            sql.addSql("   SAD_MSG_COLOR_KBN=?,");
            sql.addSql("   SAD_SMAIL_SEND_KBN=?,");
            sql.addSql("   SAD_SMAIL=?,");
            sql.addSql("   SAD_SMAIL_GROUP=?,");
            sql.addSql("   SAD_SMAIL_ATTEND=?,");
            sql.addSql("   SAD_INI_PUBLIC=?,");
            sql.addSql("   SAD_PUBLIC_KBN=?,");
            sql.addSql("   SAD_INI_SAME_STYPE=?,");
            sql.addSql("   SAD_INI_SAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadCrange());
            sql.addIntValue(bean.getSadAtdelFlg());
            sql.addIntValue(bean.getSadAtdelY());
            sql.addIntValue(bean.getSadAtdelM());
            sql.addIntValue(bean.getSadHourDiv());
            sql.addIntValue(bean.getSadSortKbn());
            sql.addIntValue(bean.getSadSortKey1());
            sql.addIntValue(bean.getSadSortOrder1());
            sql.addIntValue(bean.getSadSortKey2());
            sql.addIntValue(bean.getSadSortOrder2());
            sql.addIntValue(bean.getSadIniTimeStype());
            sql.addIntValue(bean.getSadIniFrH());
            sql.addIntValue(bean.getSadIniFrM());
            sql.addIntValue(bean.getSadIniToH());
            sql.addIntValue(bean.getSadIniToM());
            sql.addIntValue(bean.getSadIniEditStype());
            sql.addIntValue(bean.getSadIniEdit());
            sql.addIntValue(bean.getSadRepeatStype());
            sql.addIntValue(bean.getSadRepeatKbn());
            sql.addIntValue(bean.getSadRepeatMyKbn());
            sql.addIntValue(bean.getSadAuid());
            sql.addDateValue(bean.getSadAdate());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());
            sql.addIntValue(bean.getSadDspGroup());
            sql.addIntValue(bean.getSadMsgColorKbn());
            sql.addIntValue(bean.getSadSmailSendKbn());
            sql.addIntValue(bean.getSadSmail());
            sql.addIntValue(bean.getSadSmailGroup());
            sql.addIntValue(bean.getSadSmailAttend());
            sql.addIntValue(bean.getSadIniPublic());
            sql.addIntValue(bean.getSadInitPublicStype());
            sql.addIntValue(bean.getSadIniSameStype());
            sql.addIntValue(bean.getSadIniSame());

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
     * <p>Update SCH_ADM_CONF Data Bindding JavaBean
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAll(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_CRANGE=?,");
            sql.addSql("   SAD_ATDEL_FLG=?,");
            sql.addSql("   SAD_ATDEL_Y=?,");
            sql.addSql("   SAD_ATDEL_M=?,");
            sql.addSql("   SAD_HOUR_DIV=?,");
            sql.addSql("   SAD_AUID=?,");
            sql.addSql("   SAD_ADATE=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?,");
            sql.addSql("   SAD_SORT_KBN=?,");
            sql.addSql("   SAD_SORT_KEY1=?,");
            sql.addSql("   SAD_SORT_ORDER1=?,");
            sql.addSql("   SAD_SORT_KEY2=?,");
            sql.addSql("   SAD_SORT_ORDER2=?,");
            sql.addSql("   SAD_DSP_GROUP=?,");
            sql.addSql("   SAD_MSG_COLOR_KBN=?,");
            sql.addSql("   SAD_SMAIL_SEND_KBN=?,");
            sql.addSql("   SAD_SMAIL=?,");
            sql.addSql("   SAD_SMAIL_GROUP=?,");
            sql.addSql("   SAD_SMAIL_ATTEND=?,");
            sql.addSql("   SAD_INI_PUBLIC=?,");
            sql.addSql("   SAD_PUBLIC_KBN=?,");
            sql.addSql("   SAD_INI_SAME_STYPE=?,");
            sql.addSql("   SAD_INI_SAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadCrange());
            sql.addIntValue(bean.getSadAtdelFlg());
            sql.addIntValue(bean.getSadAtdelY());
            sql.addIntValue(bean.getSadAtdelM());
            sql.addIntValue(bean.getSadHourDiv());
            sql.addIntValue(bean.getSadAuid());
            sql.addDateValue(bean.getSadAdate());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());
            sql.addIntValue(bean.getSadSortKbn());
            sql.addIntValue(bean.getSadSortKey1());
            sql.addIntValue(bean.getSadSortOrder1());
            sql.addIntValue(bean.getSadSortKey2());
            sql.addIntValue(bean.getSadSortOrder2());
            sql.addIntValue(bean.getSadDspGroup());
            sql.addIntValue(bean.getSadMsgColorKbn());
            sql.addIntValue(bean.getSadSmailSendKbn());
            sql.addIntValue(bean.getSadSmail());
            sql.addIntValue(bean.getSadSmailGroup());
            sql.addIntValue(bean.getSadSmailAttend());
            sql.addIntValue(bean.getSadIniPublic());
            sql.addIntValue(bean.getSadInitPublicStype());
            sql.addIntValue(bean.getSadIniSameStype());
            sql.addIntValue(bean.getSadIniSame());

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
     * <p>共有範囲設定のアップデートを行う
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCrange(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_CRANGE=?,");
            sql.addSql("   SAD_HOUR_DIV=?,");
            sql.addSql("   SAD_MSG_COLOR_KBN=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?,");
            sql.addSql("   SAD_AM_FR_H=?,");
            sql.addSql("   SAD_AM_FR_M=?,");
            sql.addSql("   SAD_AM_TO_H=?,");
            sql.addSql("   SAD_AM_TO_M=?,");
            sql.addSql("   SAD_PM_FR_H=?,");
            sql.addSql("   SAD_PM_FR_M=?,");
            sql.addSql("   SAD_PM_TO_H=?,");
            sql.addSql("   SAD_PM_TO_M=?,");
            sql.addSql("   SAD_ALL_FR_H=?,");
            sql.addSql("   SAD_ALL_FR_M=?,");
            sql.addSql("   SAD_ALL_TO_H=?,");
            sql.addSql("   SAD_ALL_TO_M=?,");
            sql.addSql("   SAD_REPEAT_STYPE=?,");
            sql.addSql("   SAD_REPEAT_KBN=?,");
            sql.addSql("   SAD_REPEAT_MY_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadCrange());
            sql.addIntValue(bean.getSadHourDiv());
            sql.addIntValue(bean.getSadMsgColorKbn());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());

            sql.addIntValue(bean.getSadAmFrH());
            sql.addIntValue(bean.getSadAmFrM());
            sql.addIntValue(bean.getSadAmToH());
            sql.addIntValue(bean.getSadAmToM());
            sql.addIntValue(bean.getSadPmFrH());
            sql.addIntValue(bean.getSadPmFrM());
            sql.addIntValue(bean.getSadPmToH());
            sql.addIntValue(bean.getSadPmToM());
            sql.addIntValue(bean.getSadAllFrH());
            sql.addIntValue(bean.getSadAllFrM());
            sql.addIntValue(bean.getSadAllToH());
            sql.addIntValue(bean.getSadAllToM());
            sql.addIntValue(bean.getSadRepeatStype());
            sql.addIntValue(bean.getSadRepeatKbn());
            sql.addIntValue(bean.getSadRepeatMyKbn());

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
     * <p>自動削除設定をアップデートする
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAutoDelete(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_ATDEL_FLG=?,");
            sql.addSql("   SAD_ATDEL_Y=?,");
            sql.addSql("   SAD_ATDEL_M=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadAtdelFlg());
            sql.addIntValue(bean.getSadAtdelY());
            sql.addIntValue(bean.getSadAtdelM());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());

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
     * <p>メンバー表示設定をアップデートする
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMemDsp(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_SORT_KBN=?,");
            sql.addSql("   SAD_SORT_KEY1=?,");
            sql.addSql("   SAD_SORT_ORDER1=?,");
            sql.addSql("   SAD_SORT_KEY2=?,");
            sql.addSql("   SAD_SORT_ORDER2=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?,");
            sql.addSql("   SAD_DSP_GROUP=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadSortKbn());
            sql.addIntValue(bean.getSadSortKey1());
            sql.addIntValue(bean.getSadSortOrder1());
            sql.addIntValue(bean.getSadSortKey2());
            sql.addIntValue(bean.getSadSortOrder2());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());
            sql.addIntValue(bean.getSadDspGroup());

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
     * <p>初期設定をアップデートする
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateInitSetting(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_INI_TIME_STYPE=?,");
            sql.addSql("   SAD_INI_FRH=?,");
            sql.addSql("   SAD_INI_FRM=?,");
            sql.addSql("   SAD_INI_TOH=?,");
            sql.addSql("   SAD_INI_TOM=?,");
            sql.addSql("   SAD_INI_EDIT_STYPE=?,");
            sql.addSql("   SAD_INI_EDIT=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?,");
            sql.addSql("   SAD_INI_PUBLIC=?,");
            sql.addSql("   SAD_PUBLIC_KBN=?,");
            sql.addSql("   SAD_INI_SAME_STYPE=?,");
            sql.addSql("   SAD_INI_SAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadIniTimeStype());
            sql.addIntValue(bean.getSadIniFrH());
            sql.addIntValue(bean.getSadIniFrM());
            sql.addIntValue(bean.getSadIniToH());
            sql.addIntValue(bean.getSadIniToM());
            sql.addIntValue(bean.getSadIniEditStype());
            sql.addIntValue(bean.getSadIniEdit());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());
            sql.addIntValue(bean.getSadIniPublic());
            sql.addIntValue(bean.getSadInitPublicStype());
            sql.addIntValue(bean.getSadIniSameStype());
            sql.addIntValue(bean.getSadIniSame());

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
     * <p>重複登録設定をアップデートする
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateRepertKbn(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_REPEAT_STYPE=?,");
            sql.addSql("   SAD_REPEAT_KBN=?,");
            sql.addSql("   SAD_REPEAT_MY_KBN=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadRepeatStype());
            sql.addIntValue(bean.getSadRepeatKbn());
            sql.addIntValue(bean.getSadRepeatMyKbn());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());

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
     * <p>ショートメール通知設定をアップデートする
     * @param bean SCH_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSmailKbn(SchAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   SAD_SMAIL_SEND_KBN=?,");
            sql.addSql("   SAD_SMAIL=?,");
            sql.addSql("   SAD_SMAIL_GROUP=?,");
            sql.addSql("   SAD_SMAIL_ATTEND=?,");
            sql.addSql("   SAD_EUID=?,");
            sql.addSql("   SAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSadSmailSendKbn());
            sql.addIntValue(bean.getSadSmail());
            sql.addIntValue(bean.getSadSmailGroup());
            sql.addIntValue(bean.getSadSmailAttend());
            sql.addIntValue(bean.getSadEuid());
            sql.addDateValue(bean.getSadEdate());

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
     * <p>Select SCH_ADM_CONF All Data
     * @return List in SCH_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public SchAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAD_CRANGE,");
            sql.addSql("   SAD_ATDEL_FLG,");
            sql.addSql("   SAD_ATDEL_Y,");
            sql.addSql("   SAD_ATDEL_M,");
            sql.addSql("   SAD_HOUR_DIV,");
            sql.addSql("   SAD_SORT_KBN,");
            sql.addSql("   SAD_SORT_KEY1,");
            sql.addSql("   SAD_SORT_ORDER1,");
            sql.addSql("   SAD_SORT_KEY2,");
            sql.addSql("   SAD_SORT_ORDER2,");
            sql.addSql("   SAD_INI_EDIT_STYPE,");
            sql.addSql("   SAD_INI_EDIT,");
            sql.addSql("   SAD_REPEAT_STYPE,");
            sql.addSql("   SAD_REPEAT_KBN,");
            sql.addSql("   SAD_REPEAT_MY_KBN,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE,");
            sql.addSql("   SAD_DSP_GROUP,");
            sql.addSql("   SAD_MSG_COLOR_KBN,");
            sql.addSql("   SAD_SMAIL_SEND_KBN,");
            sql.addSql("   SAD_SMAIL,");
            sql.addSql("   SAD_SMAIL_GROUP,");
            sql.addSql("   SAD_SMAIL_ATTEND,");
            sql.addSql("   SAD_INI_PUBLIC,");
            sql.addSql("   SAD_PUBLIC_KBN,");
            sql.addSql("   SAD_INI_SAME_STYPE,");
            sql.addSql("   SAD_INI_SAME,");
            sql.addSql("   SAD_AM_FR_H,");
            sql.addSql("   SAD_AM_FR_M,");
            sql.addSql("   SAD_AM_TO_H,");
            sql.addSql("   SAD_AM_TO_M,");
            sql.addSql("   SAD_PM_FR_H,");
            sql.addSql("   SAD_PM_FR_M,");
            sql.addSql("   SAD_PM_TO_H,");
            sql.addSql("   SAD_PM_TO_M,");
            sql.addSql("   SAD_ALL_FR_H,");
            sql.addSql("   SAD_ALL_FR_M,");
            sql.addSql("   SAD_ALL_TO_H,");
            sql.addSql("   SAD_ALL_TO_M,");
            sql.addSql("   SAD_INI_TIME_STYPE,");
            sql.addSql("   SAD_INI_FRH,");
            sql.addSql("   SAD_INI_FRM,");
            sql.addSql("   SAD_INI_TOH,");
            sql.addSql("   SAD_INI_TOM");
            sql.addSql(" from ");
            sql.addSql("   SCH_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchAdmConfFromRs(rs);
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
     * <p>Create SCH_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private SchAdmConfModel __getSchAdmConfFromRs(ResultSet rs) throws SQLException {
        SchAdmConfModel bean = new SchAdmConfModel();
        bean.setSadCrange(rs.getInt("SAD_CRANGE"));
        bean.setSadAtdelFlg(rs.getInt("SAD_ATDEL_FLG"));
        bean.setSadAtdelY(rs.getInt("SAD_ATDEL_Y"));
        bean.setSadAtdelM(rs.getInt("SAD_ATDEL_M"));
        bean.setSadHourDiv(rs.getInt("SAD_HOUR_DIV"));
        bean.setSadSortKbn(rs.getInt("SAD_SORT_KBN"));
        bean.setSadSortKey1(rs.getInt("SAD_SORT_KEY1"));
        bean.setSadSortOrder1(rs.getInt("SAD_SORT_ORDER1"));
        bean.setSadSortKey2(rs.getInt("SAD_SORT_KEY2"));
        bean.setSadSortOrder2(rs.getInt("SAD_SORT_ORDER2"));
        bean.setSadIniTimeStype(rs.getInt("SAD_INI_TIME_STYPE"));
        bean.setSadIniFrH(rs.getInt("SAD_INI_FRH"));
        bean.setSadIniFrM(rs.getInt("SAD_INI_FRM"));
        bean.setSadIniToH(rs.getInt("SAD_INI_TOH"));
        bean.setSadIniToM(rs.getInt("SAD_INI_TOM"));
        bean.setSadIniEditStype(rs.getInt("SAD_INI_EDIT_STYPE"));
        bean.setSadIniEdit(rs.getInt("SAD_INI_EDIT"));
        bean.setSadRepeatStype(rs.getInt("SAD_REPEAT_STYPE"));
        bean.setSadRepeatKbn(rs.getInt("SAD_REPEAT_KBN"));
        bean.setSadRepeatMyKbn(rs.getInt("SAD_REPEAT_MY_KBN"));
        bean.setSadAuid(rs.getInt("SAD_AUID"));
        bean.setSadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAD_ADATE")));
        bean.setSadEuid(rs.getInt("SAD_EUID"));
        bean.setSadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAD_EDATE")));
        bean.setSadDspGroup(rs.getInt("SAD_DSP_GROUP"));
        bean.setSadMsgColorKbn(rs.getInt("SAD_MSG_COLOR_KBN"));
        bean.setSadSmailSendKbn(rs.getInt("SAD_SMAIL_SEND_KBN"));
        bean.setSadSmail(rs.getInt("SAD_SMAIL"));
        bean.setSadSmailGroup(rs.getInt("SAD_SMAIL_GROUP"));
        bean.setSadSmailAttend(rs.getInt("SAD_SMAIL_ATTEND"));
        bean.setSadIniPublic(rs.getInt("SAD_INI_PUBLIC"));
        bean.setSadInitPublicStype(rs.getInt("SAD_PUBLIC_KBN"));
        bean.setSadIniSameStype(rs.getInt("SAD_INI_SAME_STYPE"));
        bean.setSadIniSame(rs.getInt("SAD_INI_SAME"));

        bean.setSadAmFrH(rs.getInt("SAD_AM_FR_H"));
        bean.setSadAmFrM(rs.getInt("SAD_AM_FR_M"));
        bean.setSadAmToH(rs.getInt("SAD_AM_TO_H"));
        bean.setSadAmToM(rs.getInt("SAD_AM_TO_M"));
        bean.setSadPmFrH(rs.getInt("SAD_PM_FR_H"));
        bean.setSadPmFrM(rs.getInt("SAD_PM_FR_M"));
        bean.setSadPmToH(rs.getInt("SAD_PM_TO_H"));
        bean.setSadPmToM(rs.getInt("SAD_PM_TO_M"));
        bean.setSadAllFrH(rs.getInt("SAD_ALL_FR_H"));
        bean.setSadAllFrM(rs.getInt("SAD_ALL_FR_M"));
        bean.setSadAllToH(rs.getInt("SAD_ALL_TO_H"));
        bean.setSadAllToM(rs.getInt("SAD_ALL_TO_M"));
        return bean;
    }
}
