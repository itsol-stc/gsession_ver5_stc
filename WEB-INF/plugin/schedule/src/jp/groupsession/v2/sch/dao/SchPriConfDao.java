package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;

/**
 * <p>SCH_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public SchPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchPriConfDao(Connection con) {
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
            sql.addSql("drop table SCH_PRI_CONF");

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
            sql.addSql(" create table SCH_PRI_CONF (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   SCC_FR_DATE timestamp not null,");
            sql.addSql("   SCC_TO_DATE timestamp not null,");
            sql.addSql("   SCC_DSP_GROUP integer not null,");
            sql.addSql("   SCC_SORT_KEY1 integer not null,");
            sql.addSql("   SCC_SORT_ORDER1 integer not null,");
            sql.addSql("   SCC_SORT_KEY2 integer not null,");
            sql.addSql("   SCC_SORT_ORDER2 integer not null,");
            sql.addSql("   SCC_INI_FR_DATE timestamp not null,");
            sql.addSql("   SCC_INI_TO_DATE timestamp not null,");
            sql.addSql("   SCC_INI_PUBLIC integer not null,");
            sql.addSql("   SCC_INI_FCOLOR integer not null,");
            sql.addSql("   SCC_INI_EDIT integer not null,");
            sql.addSql("   SCC_DSP_LIST integer not null,");
            sql.addSql("   SCC_DSP_MYGROUP integer,");
            sql.addSql("   SCC_SMAIL integer not null,");
            sql.addSql("   SCC_SMAIL_GROUP integer not null,");
            sql.addSql("   SCC_RELOAD integer not null,");
            sql.addSql("   SCC_INI_WEEK integer not null,");
            sql.addSql("   SCC_SORT_EDIT integer not null,");
            sql.addSql("   SCC_DEF_DSP integer not null,");
            sql.addSql("   SCC_REPEAT_KBN integer not null,");
            sql.addSql("   SCC_REPEAT_MY_KBN integer not null,");
            sql.addSql("   SCC_AUID integer not null,");
            sql.addSql("   SCC_ADATE timestamp not null,");
            sql.addSql("   SCC_EUID integer not null,");
            sql.addSql("   SCC_EDATE timestamp not null,");
            sql.addSql("   SCC_GRP_SHOW_KBN integer not null,");
            sql.addSql("   SCC_SMAIL_ATTEND integer not null,");
            sql.addSql("   SCC_INI_SAME integer not null,");
            sql.addSql("   SCC_REMINDER integer not null,");
            sql.addSql("   SCC_DSP_VIEWLIST integer not null,");
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
     * <p>Insert SCH_PRI_CONF Data Bindding JavaBean
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   SCC_FR_DATE,");
            sql.addSql("   SCC_TO_DATE,");
            sql.addSql("   SCC_DSP_GROUP,");
            sql.addSql("   SCC_SORT_KEY1,");
            sql.addSql("   SCC_SORT_ORDER1,");
            sql.addSql("   SCC_SORT_KEY2,");
            sql.addSql("   SCC_SORT_ORDER2,");
            sql.addSql("   SCC_INI_FR_DATE,");
            sql.addSql("   SCC_INI_TO_DATE,");
            sql.addSql("   SCC_INI_PUBLIC,");
            sql.addSql("   SCC_INI_FCOLOR,");
            sql.addSql("   SCC_INI_EDIT,");
            sql.addSql("   SCC_DSP_LIST,");
            sql.addSql("   SCC_DSP_MYGROUP,");
            sql.addSql("   SCC_SMAIL,");
            sql.addSql("   SCC_SMAIL_GROUP,");
            sql.addSql("   SCC_RELOAD,");
            sql.addSql("   SCC_INI_WEEK,");
            sql.addSql("   SCC_SORT_EDIT,");
            sql.addSql("   SCC_DEF_DSP,");
            sql.addSql("   SCC_REPEAT_KBN,");
            sql.addSql("   SCC_REPEAT_MY_KBN,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE,");
            sql.addSql("   SCC_GRP_SHOW_KBN,");
            sql.addSql("   SCC_SMAIL_ATTEND,");
            sql.addSql("   SCC_INI_SAME,");
            sql.addSql("   SCC_REMINDER,");
            sql.addSql("   SCC_DSP_VIEWLIST");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getSccFrDate());
            sql.addDateValue(bean.getSccToDate());
            sql.addIntValue(bean.getSccDspGroup());
            sql.addIntValue(bean.getSccSortKey1());
            sql.addIntValue(bean.getSccSortOrder1());
            sql.addIntValue(bean.getSccSortKey2());
            sql.addIntValue(bean.getSccSortOrder2());
            sql.addDateValue(bean.getSccIniFrDate());
            sql.addDateValue(bean.getSccIniToDate());
            sql.addIntValue(bean.getSccIniPublic());
            sql.addIntValue(bean.getSccIniFcolor());
            sql.addIntValue(bean.getSccIniEdit());
            sql.addIntValue(bean.getSccDspList());
            sql.addIntValue(bean.getSccDspMygroup());
            sql.addIntValue(bean.getSccSmail());
            sql.addIntValue(bean.getSccSmailGroup());
            sql.addIntValue(bean.getSccReload());
            sql.addIntValue(bean.getSccIniWeek());
            sql.addIntValue(bean.getSccSortEdit());
            sql.addIntValue(bean.getSccDefDsp());
            sql.addIntValue(bean.getSccRepeatKbn());
            sql.addIntValue(bean.getSccRepeatMyKbn());
            sql.addIntValue(bean.getSccAuid());
            sql.addDateValue(bean.getSccAdate());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
            sql.addIntValue(bean.getSccGrpShowKbn());
            sql.addIntValue(bean.getSccSmailAttend());
            sql.addIntValue(bean.getSccIniSame());
            sql.addIntValue(bean.getSccReminder());
            sql.addIntValue(bean.getSccDspViewlist());
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
     * <p>Update SCH_PRI_CONF Data Bindding JavaBean
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_FR_DATE=?,");
            sql.addSql("   SCC_TO_DATE=?,");
            sql.addSql("   SCC_DSP_GROUP=?,");
            sql.addSql("   SCC_SORT_KEY1=?,");
            sql.addSql("   SCC_SORT_ORDER1=?,");
            sql.addSql("   SCC_SORT_KEY2=?,");
            sql.addSql("   SCC_SORT_ORDER2=?,");
            sql.addSql("   SCC_INI_FR_DATE=?,");
            sql.addSql("   SCC_INI_TO_DATE=?,");
            sql.addSql("   SCC_INI_PUBLIC=?,");
            sql.addSql("   SCC_INI_FCOLOR=?,");
            sql.addSql("   SCC_INI_EDIT=?,");
            sql.addSql("   SCC_DSP_LIST=?,");
            sql.addSql("   SCC_DSP_MYGROUP=?,");
            sql.addSql("   SCC_SMAIL=?,");
            sql.addSql("   SCC_SMAIL_GROUP=?,");
            sql.addSql("   SCC_RELOAD=?,");
            sql.addSql("   SCC_INI_WEEK=?,");
            sql.addSql("   SCC_SORT_EDIT=?,");
            sql.addSql("   SCC_DEF_DSP=?,");
            sql.addSql("   SCC_REPEAT_KBN=?,");
            sql.addSql("   SCC_REPEAT_MY_KBN=?,");
            sql.addSql("   SCC_AUID=?,");
            sql.addSql("   SCC_ADATE=?,");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?,");
            sql.addSql("   SCC_GRP_SHOW_KBN=?,");
            sql.addSql("   SCC_SMAIL_ATTEND=?,");
            sql.addSql("   SCC_INI_SAME=?,");
            sql.addSql("   SCC_REMINDER=?,");
            sql.addSql("   SCC_DSP_VIEWLIST=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getSccFrDate());
            sql.addDateValue(bean.getSccToDate());
            sql.addIntValue(bean.getSccDspGroup());
            sql.addIntValue(bean.getSccSortKey1());
            sql.addIntValue(bean.getSccSortOrder1());
            sql.addIntValue(bean.getSccSortKey2());
            sql.addIntValue(bean.getSccSortOrder2());
            sql.addDateValue(bean.getSccIniFrDate());
            sql.addDateValue(bean.getSccIniToDate());
            sql.addIntValue(bean.getSccIniPublic());
            sql.addIntValue(bean.getSccIniFcolor());
            sql.addIntValue(bean.getSccIniEdit());
            sql.addIntValue(bean.getSccDspList());
            sql.addIntValue(bean.getSccDspMygroup());
            sql.addIntValue(bean.getSccSmail());
            sql.addIntValue(bean.getSccSmailGroup());
            sql.addIntValue(bean.getSccReload());
            sql.addIntValue(bean.getSccIniWeek());
            sql.addIntValue(bean.getSccSortEdit());
            sql.addIntValue(bean.getSccDefDsp());
            sql.addIntValue(bean.getSccRepeatKbn());
            sql.addIntValue(bean.getSccRepeatMyKbn());
            sql.addIntValue(bean.getSccAuid());
            sql.addDateValue(bean.getSccAdate());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
            sql.addIntValue(bean.getSccGrpShowKbn());
            sql.addIntValue(bean.getSccSmailAttend());
            sql.addIntValue(bean.getSccIniSame());
            sql.addIntValue(bean.getSccReminder());
            sql.addIntValue(bean.getSccDspViewlist());
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
     * <p>スケジュール登録初期値の更新を行う
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateInitData(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_INI_FR_DATE=?,");
            sql.addSql("   SCC_INI_TO_DATE=?,");
            sql.addSql("   SCC_INI_PUBLIC=?,");
            sql.addSql("   SCC_INI_FCOLOR=?,");
            sql.addSql("   SCC_INI_EDIT=?,");
            sql.addSql("   SCC_INI_SAME=?,");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?,");
            sql.addSql("   SCC_REMINDER=?,");
            sql.addSql("   SCC_DEF_DSP=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getSccIniFrDate());
            sql.addDateValue(bean.getSccIniToDate());
            sql.addIntValue(bean.getSccIniPublic());
            sql.addIntValue(bean.getSccIniFcolor());
            sql.addIntValue(bean.getSccIniEdit());
            sql.addIntValue(bean.getSccIniSame());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
            sql.addIntValue(bean.getSccReminder());
            sql.addIntValue(bean.getSccDefDsp());
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
     * <p>スケジュール個人設定の表示設定を更新する
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateDsp(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_FR_DATE=?,");
            sql.addSql("   SCC_TO_DATE=?,");
            sql.addSql("   SCC_DSP_GROUP=?,");
            sql.addSql("   SCC_SORT_KEY1=?,");
            sql.addSql("   SCC_SORT_ORDER1=?,");
            sql.addSql("   SCC_SORT_KEY2=?,");
            sql.addSql("   SCC_SORT_ORDER2=?,");
            sql.addSql("   SCC_DSP_MYGROUP=?,");
            sql.addSql("   SCC_DSP_VIEWLIST=?,");
            sql.addSql("   SCC_SORT_EDIT=?,");
            sql.addSql("   SCC_GRP_SHOW_KBN=?,");
            sql.addSql("   SCC_DSP_LIST=?,");
            sql.addSql("   SCC_RELOAD=?,");
            sql.addSql("   SCC_INI_WEEK=?,");
            sql.addSql("   SCC_DEF_DSP=?,");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getSccFrDate());
            sql.addDateValue(bean.getSccToDate());
            sql.addIntValue(bean.getSccDspGroup());
            sql.addIntValue(bean.getSccSortKey1());
            sql.addIntValue(bean.getSccSortOrder1());
            sql.addIntValue(bean.getSccSortKey2());
            sql.addIntValue(bean.getSccSortOrder2());
            sql.addIntValue(bean.getSccDspMygroup());
            sql.addIntValue(bean.getSccDspViewlist());
            sql.addIntValue(bean.getSccSortEdit());
            sql.addIntValue(bean.getSccGrpShowKbn());
            sql.addIntValue(bean.getSccDspList());
            sql.addIntValue(bean.getSccReload());
            sql.addIntValue(bean.getSccIniWeek());
            sql.addIntValue(bean.getSccDefDsp());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
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
     * <p>スケジュール個人設定のショートメール通知設定を更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateSmail(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_SMAIL=?,");
            sql.addSql("   SCC_SMAIL_GROUP=?,");
            sql.addSql("   SCC_SMAIL_ATTEND=?,");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSccSmail());
            sql.addIntValue(bean.getSccSmailGroup());
            sql.addIntValue(bean.getSccSmailAttend());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
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
     * <p>スケジュール個人設定の重複登録設定を更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateRepeatKbn(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_REPEAT_KBN=?,");
            sql.addSql("   SCC_REPEAT_MY_KBN=?,");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSccRepeatKbn());
            sql.addIntValue(bean.getSccRepeatMyKbn());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
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
     * <p>メンバ表示順を変更していないユーザのデフォルト表示を更新する
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateMemDspUsrNotExe(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_SORT_KEY1=?,");
            sql.addSql("   SCC_SORT_ORDER1=?,");
            sql.addSql("   SCC_SORT_KEY2=?,");
            sql.addSql("   SCC_SORT_ORDER2=?,");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());


            sql.addIntValue(bean.getSccSortKey1());
            sql.addIntValue(bean.getSccSortOrder1());
            sql.addIntValue(bean.getSccSortKey2());
            sql.addIntValue(bean.getSccSortOrder2());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
            sql.addIntValue(0);

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
     * <p>リマインダー通知設定を更新する。
     * @param bean SCH_PRI_CONF Data Bindding JavaBean
     * @return int 更新数
     * @throws SQLException SQL実行例外
     */
    public int updateReminder(SchPriConfModel bean) throws SQLException {
        if (bean == null) {
            return 0;
        }
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   SCC_TARGET_REMINDER=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSccReminder());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Select SCH_PRI_CONF All Data
     * @return List in SCH_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<SchPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchPriConfModel> ret = new ArrayList<SchPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   SCC_FR_DATE,");
            sql.addSql("   SCC_TO_DATE,");
            sql.addSql("   SCC_DSP_GROUP,");
            sql.addSql("   SCC_SORT_KEY1,");
            sql.addSql("   SCC_SORT_ORDER1,");
            sql.addSql("   SCC_SORT_KEY2,");
            sql.addSql("   SCC_SORT_ORDER2,");
            sql.addSql("   SCC_INI_FR_DATE,");
            sql.addSql("   SCC_INI_TO_DATE,");
            sql.addSql("   SCC_INI_PUBLIC,");
            sql.addSql("   SCC_INI_FCOLOR,");
            sql.addSql("   SCC_INI_EDIT,");
            sql.addSql("   SCC_DSP_LIST,");
            sql.addSql("   SCC_DSP_MYGROUP,");
            sql.addSql("   SCC_SMAIL,");
            sql.addSql("   SCC_SMAIL_GROUP,");
            sql.addSql("   SCC_RELOAD,");
            sql.addSql("   SCC_INI_WEEK,");
            sql.addSql("   SCC_SORT_EDIT,");
            sql.addSql("   SCC_DEF_DSP,");
            sql.addSql("   SCC_REPEAT_KBN,");
            sql.addSql("   SCC_REPEAT_MY_KBN,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE,");
            sql.addSql("   SCC_GRP_SHOW_KBN,");
            sql.addSql("   SCC_SMAIL_ATTEND,");
            sql.addSql("   SCC_INI_SAME,");
            sql.addSql("   SCC_REMINDER,");
            sql.addSql("   SCC_DSP_VIEWLIST");
            sql.addSql(" from ");
            sql.addSql("   SCH_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchPriConfFromRs(rs));
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
     * <p>Select SCH_PRI_CONF
     * @param usrSid USR_SID
     * @return SCH_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public SchPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   SCC_FR_DATE,");
            sql.addSql("   SCC_TO_DATE,");
            sql.addSql("   SCC_DSP_GROUP,");
            sql.addSql("   SCC_SORT_KEY1,");
            sql.addSql("   SCC_SORT_ORDER1,");
            sql.addSql("   SCC_SORT_KEY2,");
            sql.addSql("   SCC_SORT_ORDER2,");
            sql.addSql("   SCC_INI_FR_DATE,");
            sql.addSql("   SCC_INI_TO_DATE,");
            sql.addSql("   SCC_INI_PUBLIC,");
            sql.addSql("   SCC_INI_FCOLOR,");
            sql.addSql("   SCC_INI_EDIT,");
            sql.addSql("   SCC_DSP_LIST,");
            sql.addSql("   SCC_DSP_MYGROUP,");
            sql.addSql("   SCC_SMAIL,");
            sql.addSql("   SCC_SMAIL_GROUP,");
            sql.addSql("   SCC_RELOAD,");
            sql.addSql("   SCC_INI_WEEK,");
            sql.addSql("   SCC_SORT_EDIT,");
            sql.addSql("   SCC_DEF_DSP,");
            sql.addSql("   SCC_REPEAT_KBN,");
            sql.addSql("   SCC_REPEAT_MY_KBN,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE,");
            sql.addSql("   SCC_GRP_SHOW_KBN,");
            sql.addSql("   SCC_SMAIL_ATTEND,");
            sql.addSql("   SCC_INI_SAME,");
            sql.addSql("   SCC_REMINDER,");
            sql.addSql("   SCC_DSP_VIEWLIST");
            sql.addSql(" from");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchPriConfFromRs(rs);
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
     * <br>[機  能] 指定したユーザの個人設定からリマインダー通知情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSidList
     * @return ユーザSid と SchPriConfModelのMap を返す
     * @throws SQLException
     */
    public Map<Integer, SchPriConfModel> getPushDataUser(List<Integer> usrSidList) throws SQLException {

        Map<Integer, SchPriConfModel> ret = new HashMap<Integer, SchPriConfModel>();
        if (usrSidList == null || usrSidList.size() == 0) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   SCH_PRI_CONF.USR_SID,");
            sql.addSql("   SCH_PRI_CONF.SCC_FR_DATE,");
            sql.addSql("   SCH_PRI_CONF.SCC_TO_DATE,");
            sql.addSql("   SCH_PRI_CONF.SCC_DSP_GROUP,");
            sql.addSql("   SCH_PRI_CONF.SCC_SORT_KEY1,");
            sql.addSql("   SCH_PRI_CONF.SCC_SORT_ORDER1,");
            sql.addSql("   SCH_PRI_CONF.SCC_SORT_KEY2,");
            sql.addSql("   SCH_PRI_CONF.SCC_SORT_ORDER2,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_FR_DATE,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_TO_DATE,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_PUBLIC,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_FCOLOR,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_EDIT,");
            sql.addSql("   SCH_PRI_CONF.SCC_DSP_LIST,");
            sql.addSql("   SCH_PRI_CONF.SCC_DSP_MYGROUP,");
            sql.addSql("   SCH_PRI_CONF.SCC_SMAIL,");
            sql.addSql("   SCH_PRI_CONF.SCC_SMAIL_GROUP,");
            sql.addSql("   SCH_PRI_CONF.SCC_RELOAD,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_WEEK,");
            sql.addSql("   SCH_PRI_CONF.SCC_SORT_EDIT,");
            sql.addSql("   SCH_PRI_CONF.SCC_DEF_DSP,");
            sql.addSql("   SCH_PRI_CONF.SCC_REPEAT_KBN,");
            sql.addSql("   SCH_PRI_CONF.SCC_REPEAT_MY_KBN,");
            sql.addSql("   SCH_PRI_CONF.SCC_AUID,");
            sql.addSql("   SCH_PRI_CONF.SCC_ADATE,");
            sql.addSql("   SCH_PRI_CONF.SCC_EUID,");
            sql.addSql("   SCH_PRI_CONF.SCC_EDATE,");
            sql.addSql("   SCH_PRI_CONF.SCC_GRP_SHOW_KBN,");
            sql.addSql("   SCH_PRI_CONF.SCC_SMAIL_ATTEND,");
            sql.addSql("   SCH_PRI_CONF.SCC_INI_SAME,");
            sql.addSql("   SCH_PRI_CONF.SCC_REMINDER,");
            sql.addSql("   SCH_PRI_CONF.SCC_DSP_VIEWLIST");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM ");
            sql.addSql(" left join ");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = SCH_PRI_CONF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID in (");
            for (int i = 0; i < usrSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(usrSidList.get(i));
            }
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), __getSchPriConfFromRs(rs));
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
     * <br>[機  能] 指定したユーザの個人設定からリマインダー通知情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID一覧
     * @return ユーザSID と SchPriConfModelのMap を返す
     * @throws SQLException
     */
    public Map<Integer, SchPriPushModel> getPushDataUser(String[] usrSid) throws SQLException {

        Map<Integer, SchPriPushModel> ret = new HashMap<Integer, SchPriPushModel>();
        if (usrSid == null || usrSid.length == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   coalesce(SCH_PRI_CONF.SCC_REMINDER, 4) as SCC_REMINDER ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM ");
            sql.addSql(" left join ");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = SCH_PRI_CONF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID in (-1");
            for (int i = 0; i < usrSid.length; i++) {
                sql.addSql("     , ");
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(usrSid[i]));
            }
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), __getSchPriConfPushFromRs(rs));

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
     * <br>[機  能] 指定したグループに所属しているユーザ個人設定からリマインダー通知情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSidList
     * @return ユーザSid と SchPriConfModelのMap を返す
     * @throws SQLException
     */
    public Map<Integer, SchPriPushModel> getPushDataGroup(List<Integer> groupSidList) throws SQLException {

        Map<Integer, SchPriPushModel> ret = new HashMap<Integer, SchPriPushModel>();
        if (groupSidList == null || groupSidList.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   coalesce(SCH_PRI_CONF.SCC_REMINDER, 4) as SCC_REMINDER ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM ");
            sql.addSql(" left join ");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = SCH_PRI_CONF.USR_SID");
            sql.addSql(" right join ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID");
            sql.addSql(" in(");
            sql.addSql("  select  ");
            sql.addSql("      CMN_BELONGM.USR_SID ");
            sql.addSql("  from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("  where");
            sql.addSql("      CMN_BELONGM.GRP_SID in (");
            for (int i = 0; i < groupSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(groupSidList.get(i));
            }
            sql.addSql("   )");
            sql.addSql(" )");


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), __getSchPriConfPushFromRs(rs));
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
     * <br>[機  能] 指定したグループに所属しているユーザ個人設定からリマインダー通知情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid グループSID 
     * @return グループSID と SchPriConfModelのMap を返す
     * @throws SQLException
     */
    public Map<Integer, SchPriPushModel> getPushDataGroup(int groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, SchPriPushModel> ret = new HashMap<Integer, SchPriPushModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   coalesce(SCH_PRI_CONF.SCC_REMINDER, 4) as SCC_REMINDER ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM ");
            sql.addSql(" left join ");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = SCH_PRI_CONF.USR_SID");
            sql.addSql(" right join ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID");
            sql.addSql(" in (");
            sql.addSql("  select  ");
            sql.addSql("      CMN_BELONGM.USR_SID ");
            sql.addSql("  from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("  where");
            sql.addSql("      CMN_BELONGM.GRP_SID = ? ");
            sql.addSql(" )");
            sql.addIntValue(groupSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), __getSchPriConfPushFromRs(rs));
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
     * <p>指定したユーザから指定した重複区分を設定しているユーザSIDリストを取得します。
     * @param usidList ユーザSIDリスト
     * @param repeatKbn リピート区分
     * @return SCH_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getUsrSidListRepeatKbn(List<Integer> usidList, int repeatKbn)
    throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        if (usidList.isEmpty()) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_PRI_CONF");

            sql.addSql(" where ");
            sql.addSql("   SCC_REPEAT_KBN=?");
            sql.addIntValue(repeatKbn);
            sql.addSql(" and (");
            int i = 0;
            for (Integer usrSid : usidList) {
                if (i > 0) {
                    sql.addSql(" or");
                }
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSid);
                i++;
            }
            sql.addSql(" )");
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
     * <p>Delete SCH_PRI_CONF
     * @param usrSid USR_SID
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
            sql.addSql("   SCH_PRI_CONF");
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
     * <p>Create SCH_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchPriConfModel
     * @throws SQLException SQL実行例外
     */
    private SchPriConfModel __getSchPriConfFromRs(ResultSet rs) throws SQLException {
        SchPriConfModel bean = new SchPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSccFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_FR_DATE")));
        bean.setSccToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_TO_DATE")));
        bean.setSccDspGroup(rs.getInt("SCC_DSP_GROUP"));
        bean.setSccSortKey1(rs.getInt("SCC_SORT_KEY1"));
        bean.setSccSortOrder1(rs.getInt("SCC_SORT_ORDER1"));
        bean.setSccSortKey2(rs.getInt("SCC_SORT_KEY2"));
        bean.setSccSortOrder2(rs.getInt("SCC_SORT_ORDER2"));
        bean.setSccIniFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_INI_FR_DATE")));
        bean.setSccIniToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_INI_TO_DATE")));
        bean.setSccIniPublic(rs.getInt("SCC_INI_PUBLIC"));
        bean.setSccIniFcolor(rs.getInt("SCC_INI_FCOLOR"));
        bean.setSccIniEdit(rs.getInt("SCC_INI_EDIT"));
        bean.setSccDspList(rs.getInt("SCC_DSP_LIST"));
        bean.setSccDspMygroup(rs.getInt("SCC_DSP_MYGROUP"));
        bean.setSccSmail(rs.getInt("SCC_SMAIL"));
        bean.setSccSmailGroup(rs.getInt("SCC_SMAIL_GROUP"));
        bean.setSccReload(rs.getInt("SCC_RELOAD"));
        bean.setSccIniWeek(rs.getInt("SCC_INI_WEEK"));
        bean.setSccSortEdit(rs.getInt("SCC_SORT_EDIT"));
        bean.setSccDefDsp(rs.getInt("SCC_DEF_DSP"));
        bean.setSccRepeatKbn(rs.getInt("SCC_REPEAT_KBN"));
        bean.setSccRepeatMyKbn(rs.getInt("SCC_REPEAT_MY_KBN"));
        bean.setSccAuid(rs.getInt("SCC_AUID"));
        bean.setSccAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_ADATE")));
        bean.setSccEuid(rs.getInt("SCC_EUID"));
        bean.setSccEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_EDATE")));
        bean.setSccGrpShowKbn(rs.getInt("SCC_GRP_SHOW_KBN"));
        bean.setSccSmailAttend(rs.getInt("SCC_SMAIL_ATTEND"));
        bean.setSccIniSame(rs.getInt("SCC_INI_SAME"));
        bean.setSccReminder(rs.getInt("SCC_REMINDER"));
        bean.setSccDspViewlist(rs.getInt("SCC_DSP_VIEWLIST"));
        return bean;
    }

    /**
     * <p>Create SCH_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchPriConfModel
     * @throws SQLException SQL実行例外
     */
    private SchPriPushModel __getSchPriConfPushFromRs(ResultSet rs) throws SQLException {
        SchPriPushModel bean = new SchPriPushModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSccReminder(rs.getInt("SCC_REMINDER"));
        return bean;
    }

}
