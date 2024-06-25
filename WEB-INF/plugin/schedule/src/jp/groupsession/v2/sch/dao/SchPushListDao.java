package jp.groupsession.v2.sch.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPushListModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>SCH_PUSH_LIST Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchPushListDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchPushListDao.class);

    /**
     * <p>Default Constructor
     */
    public SchPushListDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchPushListDao(Connection con) {
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
            sql.addSql("drop table SCH_PUSH_LIST");

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
            sql.addSql(" create table SCH_PUSH_LIST (");
            sql.addSql("   SCD_SID integer not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   SPL_REMINDER timestamp not null,");
            sql.addSql("   SPL_REMINDER_KBN integer not null,");
            sql.addSql("   primary key (SCD_SID,USR_SID)");
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
     * <p>Insert SCH_PUSH_LIST Data Bindding JavaBean
     * @param bean SCH_PUSH_LIST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchPushListModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_PUSH_LIST(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SPL_REMINDER,");
            sql.addSql("   SPL_REMINDER_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getSplReminder());
            sql.addIntValue(bean.getSplReminderKbn());
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
     * <p>Insert SCH_PUSH_LIST Data Bindding JavaBean
     * @param beanList SCH_PUSH_LIST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SchPushListModel> beanList) throws SQLException {
        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchPushListModel> exeList = new ArrayList<>();
        Iterator<SchPushListModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();

        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_PUSH_LIST(");
        sb.append("   SCD_SID,");
        sb.append("   USR_SID,");
        sb.append("   SPL_REMINDER,");
        sb.append("   SPL_REMINDER_KBN");
        sb.append(" )");
        sb.append(" values");

        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());

            Iterator<SchPushListModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchPushListModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");
                sql.addIntValue(bean.getScdSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addDateValue(bean.getSplReminder());
                sql.addIntValue(bean.getSplReminderKbn());
                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                pstmt.executeUpdate();

            }
            exeList.clear();
        }
    }
    /**
     * <p>Update SCH_PUSH_LIST Data Bindding JavaBean
     * @param bean SCH_PUSH_LIST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchPushListModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" set ");
            sql.addSql("   SPL_REMINDER=?,");
            sql.addSql("   SPL_REMINDER_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getSplReminder());
            sql.addIntValue(bean.getSplReminderKbn());
            //where
            sql.addIntValue(bean.getScdSid());
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
     * <p>Select SCH_PUSH_LIST All Data
     * @return List in SCH_PUSH_LISTModel
     * @throws SQLException SQL実行例外
     */
    public List<SchPushListModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchPushListModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SPL_REMINDER,");
            sql.addSql("   SPL_REMINDER_KBN");
            sql.addSql(" from ");
            sql.addSql("   SCH_PUSH_LIST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchPushListFromRs(rs));
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
     * <p>Select SCH_PUSH_LIST
     * @param scdSid SCD_SID
     * @param usrSid USR_SID
     * @return SCH_PUSH_LISTModel
     * @throws SQLException SQL実行例外
     */
    public SchPushListModel select(int scdSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchPushListModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SPL_REMINDER,");
            sql.addSql("   SPL_REMINDER_KBN");
            sql.addSql(" from");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchPushListFromRs(rs);
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
     * <p>Delete SCH_PUSH_LIST
     * @param scdSid SCD_SID
     * @return 実行行数
     * @throws SQLException SQL実行例外
     */
    public int delete(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

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
     * <p>Delete SCH_PUSH_LIST
     * @param scdSid SCD_SID
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(int scdSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
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
     * <p>Create SCH_PUSH_LIST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchPushListModel
     * @throws SQLException SQL実行例外
     */
    private SchPushListModel __getSchPushListFromRs(ResultSet rs) throws SQLException {
        SchPushListModel bean = new SchPushListModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSplReminder(UDate.getInstanceTimestamp(rs.getTimestamp("SPL_REMINDER")));
        bean.setSplReminderKbn(rs.getInt("SPL_REMINDER_KBN"));
        return bean;
    }
    /**
     * <br>[機  能] スケジュールSIDリストによって削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList 削除対象リスト
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete(List<Integer> scdSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        ArrayList<Integer> delList = new ArrayList<Integer>();
        Iterator<Integer> it = scdSidList.iterator();

        while (it.hasNext()) {
            Integer scdSid = it.next();
            delList.add(scdSid);

            if (delList.size() < GSConstSchedule.SCH_BATCH_DELETE_COUNT
                    && it.hasNext()) {
                continue;
            }

            try {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   SCH_PUSH_LIST");
                sql.addSql(" where ");
                sql.addSql("   SCD_SID in (");
                Iterator<Integer> delIt = delList.iterator();
                while (delIt.hasNext()) {
                    sql.addSql(" ? ");
                    sql.addIntValue(delIt.next());
                    if (delIt.hasNext()) {
                        sql.addSql(" , ");
                    }
                }
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);

                count += pstmt.executeUpdate();
                delList = new ArrayList<Integer>();
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeStatement(pstmt);
            }
        }


        return count;
    }

    /**
     * <br>[機  能] 指定日時以前の通知リスト情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param toDate
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete(UDate toDate) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" where ");
            sql.addSql("   SPL_REMINDER < ? ");
            sql.addDateValue(toDate);

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
     * <p>Select SCH_PUSH_LIST All Data
     * @param scdSidList
     * @param grpSid
     * @return List in SCH_PUSH_LISTModel
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, SchPushListModel> selectGroupData(
            List<Integer> scdSidList, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, SchPushListModel> ret = new HashMap<Integer, SchPushListModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCH_PUSH_LIST.USR_SID,");
            sql.addSql("   SCH_PUSH_LIST.SCD_SID,");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   coalesce(SCH_PUSH_LIST.SPL_REMINDER, 4) as SPL_REMINDER,");
            sql.addSql("   coalesce(SCH_PUSH_LIST.SPL_REMINDER_KBN, 4) as SPL_REMINDER_KBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join ");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = SCH_PUSH_LIST.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select  ");
            sql.addSql("       CMN_BELONGM.USR_SID ");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM");
            sql.addSql("     where");
            sql.addSql("       CMN_BELONGM.GRP_SID = ? ");
            sql.addSql("  )");
            sql.addIntValue(grpSid);
            sql.addSql(" and ");
            sql.addSql("   SCH_PUSH_LIST.SCD_SID in (");
            int idx = 0;
            for (int scdSid : scdSidList) {
                if (idx != 0) {
                    sql.addSql("    , ");
                }
                sql.addSql("   ? ");
                sql.addIntValue(scdSid);
                idx++;
            }
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), __getSchPushListFromRs(rs));
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
     * <p>Select SCH_PUSH_LIST All Data
     * @param scdSidList
     * @param delScdUsrSid
     * @return List in SCH_PUSH_LISTModel
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, SchPushListModel> selectUserData(
            List<Integer> scdSidList, String[] delScdUsrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, SchPushListModel> ret = new HashMap<Integer, SchPushListModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCH_PUSH_LIST.USR_SID,");
            sql.addSql("   SCH_PUSH_LIST.SCD_SID,");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   coalesce(SCH_PUSH_LIST.SPL_REMINDER_KBN, 4) as SPL_REMINDER,");
            sql.addSql("   coalesce(SCH_PUSH_LIST.SPL_REMINDER, 4) as SPL_REMINDER_KBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join ");
            sql.addSql("   SCH_PUSH_LIST");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = SCH_PUSH_LIST.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID in (");
            int idx = 0;
            for (String usrSid : delScdUsrSid) {
                if (idx != 0) {
                    sql.addSql("    , ");
                }
                sql.addSql("   ? ");
                sql.addIntValue(Integer.parseInt(usrSid));
                idx++;
            }
            sql.addSql("   ) ");
            sql.addSql(" and ");
            sql.addSql("   SCH_PUSH_LIST.SCD_SID in (");
            idx = 0;
            for (int scdSid : scdSidList) {
                if (idx != 0) {
                    sql.addSql("    , ");
                }
                sql.addSql("   ? ");
                sql.addIntValue(scdSid);
                idx++;
            }
            sql.addSql("   ) ");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), __getSchPushListFromRs(rs));
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
     * <p>Delete SCH_PUSH_LIST
     * @param scdSidList
     * @param schUsrSid
     * @throws SQLException SQL実行例外
     */
    public void deletePushData(
            List<Integer> scdSidList, String[] schUsrSid) throws SQLException {
        if (scdSidList.size() == 0 || schUsrSid.length == 0) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
              //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   SCH_PUSH_LIST");
                sql.addSql(" where ");
                sql.addSql("   SCD_SID in (");
                int idx = 0;
                for (int scdSid : scdSidList) {
                    if (idx != 0) {
                        sql.addSql("     ,");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(scdSid);
                    idx++;
                }
                sql.addSql(") ");
                sql.addSql(" and ");
                sql.addSql("   USR_SID in (");
                idx = 0;
                for (String userSid : schUsrSid) {
                    if (idx != 0) {
                        sql.addSql("     ,");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(userSid));
                    idx++;
                }
                sql.addSql(") ");
                pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete SCH_PUSH_LIST
     * @param scdRsSid 施設予約SID
     * @param schUsrSid ユーザSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deletePushData(int scdRsSid, String[] schUsrSid) throws SQLException {
        if (schUsrSid.length == 0) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
              //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   SCH_PUSH_LIST");
                sql.addSql(" where ");
                sql.addSql("   SCD_SID in (");
                sql.addSql("     select");
                sql.addSql("       SCD_SID");
                sql.addSql("     from");
                sql.addSql("       SCH_DATA");
                sql.addSql("     where");
                sql.addSql("       SCD_RSSID = ?");
                sql.addIntValue(scdRsSid);
                sql.addSql("     and ");
                sql.addSql("       SCD_USR_SID in (");
                int idx = 0;
                for (String userSid : schUsrSid) {
                    if (idx != 0) {
                        sql.addSql("     ,");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(userSid));
                    idx++;
                }
                sql.addSql("     ) ");
                sql.addSql("   ) ");
                pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete SCH_PUSH_LIST
     * @param scdSidList
     * @param schUsrSid
     * @throws SQLException SQL実行例外
     */
    public void deletePushData(
            List<Integer> scdSidList, List<String> schUsrSid) throws SQLException {
        if (scdSidList.size() == 0 || schUsrSid.size() == 0) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
              //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   SCH_PUSH_LIST");
                sql.addSql(" where ");
                sql.addSql("   SCD_SID in (");
                int idx = 0;
                for (int scdSid : scdSidList) {
                    if (idx != 0) {
                        sql.addSql("     ,");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(scdSid);
                    idx++;
                }
                sql.addSql(") ");
                sql.addSql(" and ");
                sql.addSql("   USR_SID in (");
                idx = 0;
                for (String userSid : schUsrSid) {
                    if (idx != 0) {
                        sql.addSql("     ,");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(userSid));
                    idx++;
                }
                sql.addSql(") ");
                pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete SCH_PUSH_LIST
     * @param scdSidList
     * @param schGrpSid
     * @throws SQLException SQL実行例外
     */
    public void deletePushGroupData(
            List<Integer> scdSidList, int schGrpSid) throws SQLException {
        if (scdSidList.size() == 0) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
              //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   SCH_PUSH_LIST");
                sql.addSql(" where ");
                sql.addSql("   SCD_SID in (");
                int idx = 0;
                for (int scdSid : scdSidList) {
                    if (idx != 0) {
                        sql.addSql("     ,");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(scdSid);
                    idx++;
                }
                sql.addSql(") ");
                sql.addSql(" and ");
                sql.addSql("   USR_SID in (");
                sql.addSql("   select  ");
                sql.addSql("      CMN_BELONGM.USR_SID ");
                sql.addSql("   from");
                sql.addSql("      CMN_BELONGM");
                sql.addSql("   where");
                sql.addSql("      CMN_BELONGM.GRP_SID = ? ");
                sql.addSql(") ");
                sql.addIntValue(schGrpSid);
                pstmt = con.prepareStatement(sql.toSqlString());

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
    *
    * <br>[機  能] 指定日時範囲にあり、未登録の通知情報リストを生成する
    * <br>[解  説]
    * <br>[備  考]
    * @param frDate 範囲開始
    * @param toDate 範囲終了
    * @return 生成された通知情報リスト
    * @throws SQLException SQL実行時例外
    */
   public List<SchPushListModel> createPushListIfNeed(
           UDate frDate,
           UDate toDate) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       ArrayList<SchPushListModel> ret = new ArrayList<SchPushListModel>();
       con = getCon();
       SchCommonBiz biz = new SchCommonBiz();
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql(" SCH_DATA.SCD_SID as SCD_SID, ");
           //USR_SID
           //ユーザスケジュールはSCD_USR_SIDを使用
           //グループスケジュールは所属ユーザSIDを使用
           sql.addSql(" case SCH_DATA.SCD_USR_KBN ");
           sql.addSql("   when " + GSConstSchedule.USER_KBN_USER
                   + "      then SCH_DATA.SCD_USR_SID ");
           sql.addSql("   when " + GSConstSchedule.USER_KBN_GROUP
                   + "      then CMN_BELONGM.USR_SID ");
           sql.addSql("   else -1 end as USR_SID, ");
           //SPL_REMINDER_SEL この値とスケジュールの開始時間からSPL_REMINDERを計算
           //ユーザスケジュールはSCD_REMINDERを使用
           //グループスケジュールは所属ユーザのSCH_PRI_CONF.SCC_REMINDER
           //なければデフォルト設定(GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES)を使用
           sql.addSql(" coalesce(case SCH_DATA.SCD_USR_KBN ");
           sql.addSql(" when " + GSConstSchedule.USER_KBN_USER
                   + "    then SCH_DATA.SCD_REMINDER ");
           sql.addSql(" when " + GSConstSchedule.USER_KBN_GROUP
                   + "    then SCH_PRI_CONF.SCC_REMINDER ");
           sql.addSql(" else null end, ?) as SPL_REMINDER_KBN, ");
           sql.addIntValue(GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES);

           sql.addSql(" SCH_DATA.SCD_FR_DATE as SPL_REMINDER ");

           sql.addSql(" from  ");
           //あらかじめスケジュールを日付で絞り込む
           sql.addSql(" ( select * from SCH_DATA ");
           sql.addSql("   where  ");
           //日付範囲指定
           sql.addSql("     SCH_DATA.SCD_FR_DATE between ?  ");
           sql.addSql("                            and ? ");
           sql.addDateValue(frDate);
           sql.addDateValue(toDate);
           //対象は通知するになっているもののみ
           sql.addSql("     and (");
           sql.addSql("       SCH_DATA.SCD_REMINDER > " + GSConstSchedule.REMINDER_TIME_NO);
           sql.addSql("       or SCH_DATA.SCD_TARGET_GRP = " + GSConstSchedule.REMINDER_USE_YES);
           sql.addSql("     )");
           sql.addSql(" ) as SCH_DATA  ");

           //グループスケジュール用ユーザのleftjoin
           //システムユーザは対象外
           sql.addSql(" left join CMN_BELONGM ");
           sql.addSql("  on SCH_DATA.SCD_USR_KBN = " +  GSConstSchedule.USER_KBN_GROUP
                   + " and SCH_DATA.SCD_USR_SID = CMN_BELONGM.GRP_SID"
                   + " and CMN_BELONGM.USR_SID > " + GSConstUser.USER_RESERV_SID);

           //個人設定のleftjoin
           sql.addSql(" left join SCH_PRI_CONF ");
           sql.addSql("  on SCH_DATA.SCD_USR_KBN = " +  GSConstSchedule.USER_KBN_GROUP
                   + " and CMN_BELONGM.USR_SID = SCH_PRI_CONF.USR_SID ");

           //登録済みの通知リスト情報のleftjoin
           sql.addSql(" left join SCH_PUSH_LIST ");
           sql.addSql("  on (SCH_DATA.SCD_USR_KBN = " +  GSConstSchedule.USER_KBN_USER
                   + "       and SCH_DATA.SCD_SID = SCH_PUSH_LIST.SCD_SID) ");
           sql.addSql("    or (SCH_DATA.SCD_USR_KBN = " +  GSConstSchedule.USER_KBN_GROUP
                   + "       and SCH_DATA.SCD_SID = SCH_PUSH_LIST.SCD_SID"
                   + "       and CMN_BELONGM.USR_SID = SCH_PUSH_LIST.USR_SID) ");
           sql.addSql(" where  ");
           //登録済みの通知リストを除外
           sql.addSql("  SCH_PUSH_LIST.SCD_SID is null ");
           sql.addSql(" order by  ");
           sql.addSql("  SCH_DATA.SCD_SID ");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               if (rs.getInt("USR_SID") > GSConstUser.USER_RESERV_SID
                       && rs.getInt("SPL_REMINDER_KBN") != 0) {
                   SchPushListModel bean = __getSchPushListFromRs(rs);
                   bean.setSplReminder(
                           biz.getReminderDate(bean.getSplReminder(),
                                   rs.getInt("SPL_REMINDER_KBN")));
                   ret.add(bean);
               }
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
     * <br>[機  能] 範囲内の通知リストとスケジュールを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 範囲開始
     * @param toDate 範囲終了
     * @return 通知情報とスケジュール情報のMappingリストマップ
     * @throws SQLException SQL実行時間
     */
    public Map<SchPushListModel, SchDataModel> getPushListMap(
            UDate frDate, UDate toDate) throws SQLException {
        Map<SchPushListModel, SchDataModel> ret = new HashMap<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCH_DATA.SCD_SID as SCD_SID ,");
            sql.addSql("   SCH_DATA.SCD_USR_SID as SCD_USR_SID ,");
            sql.addSql("   SCH_DATA.SCD_GRP_SID as SCD_GRP_SID ,");
            sql.addSql("   SCH_DATA.SCD_USR_KBN as SCD_USR_KBN ,");
            sql.addSql("   SCH_DATA.SCD_FR_DATE as SCD_FR_DATE ,");
            sql.addSql("   SCH_DATA.SCD_TO_DATE as SCD_TO_DATE ,");
            sql.addSql("   SCH_DATA.SCD_DAILY as SCD_DAILY ,");
            sql.addSql("   SCH_DATA.SCD_BGCOLOR as SCD_BGCOLOR ,");
            sql.addSql("   SCH_DATA.SCD_TITLE as SCD_TITLE ,");
            sql.addSql("   SCH_DATA.SCD_PUBLIC as SCD_PUBLIC ,");
            sql.addSql("   SCH_DATA.SCD_AUID as SCD_AUID ,");
            sql.addSql("   SCH_DATA.SCD_ADATE as SCD_ADATE ,");
            sql.addSql("   SCH_DATA.SCD_EUID as SCD_EUID ,");
            sql.addSql("   SCH_DATA.SCD_EDATE as SCD_EDATE ,");
            sql.addSql("   SCH_DATA.SCE_SID as SCE_SID ,");
            sql.addSql("   SCH_DATA.SCD_EDIT as SCD_EDIT ,");
            sql.addSql("   SCH_DATA.SCD_RSSID as SCD_RSSID ,");
            sql.addSql("   SCH_DATA.SCD_ATTEND_KBN as SCD_ATTEND_KBN ,");
            sql.addSql("   SCH_DATA.SCD_ATTEND_ANS as SCD_ATTEND_ANS ,");
            sql.addSql("   SCH_DATA.SCD_ATTEND_AU_KBN as SCD_ATTEND_AU_KBN ,");
            sql.addSql("   SCH_DATA.SCD_ATTEND_COMMENT as SCD_ATTEND_COMMENT ,");
            sql.addSql("   SCH_DATA.SCD_TARGET_GRP as SCD_TARGET_GRP ,");
            sql.addSql("   SCH_DATA.SCD_REMINDER as SCD_REMINDER, ");
            sql.addSql("   SCH_PUSH_LIST.USR_SID as USR_SID ,");
            sql.addSql("   SCH_PUSH_LIST.SPL_REMINDER as SPL_REMINDER, ");
            sql.addSql("   SCH_PUSH_LIST.SPL_REMINDER_KBN as SPL_REMINDER_KBN ");
            sql.addSql(" from  ");
            //あらかじめスケジュールを日付で絞り込む
            sql.addSql("   SCH_PUSH_LIST ");

            //登録済みの通知リスト情報のleftjoin
            sql.addSql(" inner join SCH_DATA ");
            sql.addSql("  on SCH_PUSH_LIST.SCD_SID = SCH_DATA.SCD_SID ");
            sql.addSql(" where  ");
            sql.addSql("  SCH_PUSH_LIST.SPL_REMINDER >= ?");
            sql.addSql(" and  ");
            sql.addSql("  SCH_PUSH_LIST.SPL_REMINDER < ?");
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            //スケジュールの通知を行わないユーザは除外する
            sql.addSql(" and");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from MBL_PUSH_TARGET_IGNORE");
            sql.addSql("     where");
            sql.addSql("       MBL_PUSH_TARGET_IGNORE.USR_SID = SCH_PUSH_LIST.USR_SID");
            sql.addSql("     and");
            sql.addSql("       MBL_PUSH_TARGET_IGNORE.APP_ID = ?");
            sql.addSql("     and");
            sql.addSql("       MBL_PUSH_TARGET_IGNORE.PLUGIN_ID = ?");
            sql.addSql("     and");
            sql.addSql("       MBL_PUSH_TARGET_IGNORE.PUC_SUBKBN = ?");
            sql.addSql("     and");
            sql.addSql("       MBL_PUSH_TARGET_IGNORE.PUC_SUBTARGET_SID = -1");
            sql.addSql("   )");
            sql.addStrValue(GSConst.APP_GS_MOBILE);
            sql.addStrValue(GSConstSchedule.PLUGIN_ID_SCHEDULE);
            sql.addStrValue("all");

            sql.addSql(" order by SCH_DATA.SCD_FR_DATE, SCH_DATA.SCD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SchPushListModel push = __getSchPushListFromRs(rs);
                SchDataModel bean = new SchDataModel();
                bean.setScdSid(rs.getInt("SCD_SID"));
                bean.setScdUsrSid(rs.getInt("SCD_USR_SID"));
                bean.setScdGrpSid(rs.getInt("SCD_GRP_SID"));
                bean.setScdUsrKbn(rs.getInt("SCD_USR_KBN"));
                bean.setScdFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_FR_DATE")));
                bean.setScdToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_TO_DATE")));
                bean.setScdDaily(rs.getInt("SCD_DAILY"));
                bean.setScdBgcolor(rs.getInt("SCD_BGCOLOR"));
                bean.setScdTitle(rs.getString("SCD_TITLE"));
                bean.setScdPublic(rs.getInt("SCD_PUBLIC"));
                bean.setScdAuid(rs.getInt("SCD_AUID"));
                bean.setScdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_ADATE")));
                bean.setScdEuid(rs.getInt("SCD_EUID"));
                bean.setScdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_EDATE")));
                bean.setSceSid(rs.getInt("SCE_SID"));
                bean.setScdEdit(rs.getInt("SCD_EDIT"));
                bean.setScdRsSid(rs.getInt("SCD_RSSID"));
                bean.setScdAttendKbn(rs.getInt("SCD_ATTEND_KBN"));
                bean.setScdAttendAns(rs.getInt("SCD_ATTEND_ANS"));
                bean.setScdAttendAuKbn(rs.getInt("SCD_ATTEND_AU_KBN"));
                bean.setScdAttendComment(rs.getString("SCD_ATTEND_COMMENT"));
                bean.setScdTargetGrp(rs.getInt("SCD_TARGET_GRP"));
                bean.setScdReminder(rs.getInt("SCD_REMINDER"));

                ret.put(push, bean);

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
