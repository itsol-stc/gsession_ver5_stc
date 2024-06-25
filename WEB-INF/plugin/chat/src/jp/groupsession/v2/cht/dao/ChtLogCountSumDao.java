package jp.groupsession.v2.cht.dao;

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
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChtLogCountSumModel;

/**
 * <p>CHT_LOG_COUNT_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtLogCountSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtLogCountSumDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtLogCountSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtLogCountSumDao(Connection con) {
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
            sql.addSql("drop table CHT_LOG_COUNT_SUM");

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
            sql.addSql(" create table CHT_LOG_COUNT_SUM (");
            sql.addSql("   CLS_SEND_CNT integer not null,");
            sql.addSql("   CLS_SEND_UCNT integer not null,");
            sql.addSql("   CLS_SEND_GCNT integer not null,");
            sql.addSql("   CLS_USER_CNT integer not null,");
            sql.addSql("   CLS_DATE Date not null,");
            sql.addSql("   CLS_YEAR_MONTH integer not null,");
            sql.addSql("   CLS_EDATE timestamp not null,");
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
     * <p>Insert CHT_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean CHT_LOG_COUNT_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_LOG_COUNT_SUM(");
            sql.addSql("   CLS_SEND_CNT,");
            sql.addSql("   CLS_SEND_UCNT,");
            sql.addSql("   CLS_SEND_GCNT,");
            sql.addSql("   CLS_USER_CNT,");
            sql.addSql("   CLS_DATE,");
            sql.addSql("   CLS_YEAR_MONTH,");
            sql.addSql("   CLS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClsSendCnt());
            sql.addIntValue(bean.getClsSendUcnt());
            sql.addIntValue(bean.getClsSendGcnt());
            sql.addIntValue(bean.getClsUserCnt());
            sql.addDateValue(bean.getClsDate());
            sql.addIntValue(bean.getClsYearMonth());
            sql.addDateValue(bean.getClsEdate());
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
     * <p>Update CHT_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean CHT_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   CLS_SEND_CNT=?,");
            sql.addSql("   CLS_SEND_UCNT=?,");
            sql.addSql("   CLS_SEND_GCNT=?,");
            sql.addSql("   CLS_USER_CNT=?,");
            sql.addSql("   CLS_DATE=?,");
            sql.addSql("   CLS_YEAR_MONTH=?,");
            sql.addSql("   CLS_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClsSendCnt());
            sql.addIntValue(bean.getClsSendUcnt());
            sql.addIntValue(bean.getClsSendGcnt());
            sql.addIntValue(bean.getClsUserCnt());
            sql.addDateValue(bean.getClsDate());
            sql.addIntValue(bean.getClsYearMonth());
            sql.addDateValue(bean.getClsEdate());

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
     * <p>Update CHT_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean CHT_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateNitizi(ChtLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   CLS_SEND_CNT=?,");
            sql.addSql("   CLS_SEND_UCNT=?,");
            sql.addSql("   CLS_SEND_GCNT=?,");
            sql.addSql("   CLS_USER_CNT=?,");
            sql.addSql("   CLS_YEAR_MONTH=?,");
            sql.addSql("   CLS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CLS_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClsSendCnt());
            sql.addIntValue(bean.getClsSendUcnt());
            sql.addIntValue(bean.getClsSendGcnt());
            sql.addIntValue(bean.getClsUserCnt());
            sql.addIntValue(bean.getClsYearMonth());
            sql.addDateValue(bean.getClsEdate());

            sql.addDateValue(bean.getClsDate());

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
     * <p>Select CHT_LOG_COUNT_SUM All Data
     * @return List in CHT_LOG_COUNT_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtLogCountSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtLogCountSumModel> ret = new ArrayList<ChtLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLS_SEND_CNT,");
            sql.addSql("   CLS_SEND_UCNT,");
            sql.addSql("   CLS_SEND_GCNT,");
            sql.addSql("   CLS_USER_CNT,");
            sql.addSql("   CLS_DATE,");
            sql.addSql("   CLS_YEAR_MONTH,");
            sql.addSql("   CLS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_LOG_COUNT_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtLogCountSumFromRs(rs));
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
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説] 集計結果(ChtLogCountSum)に未登録のデータのみを対象とする。基準日含む過去3日間
     * <br>[備  考]
     * @param date 基準日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<ChtLogCountSumModel> getNonRegisteredList(UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<ChtLogCountSumModel> logSumList = new ArrayList<ChtLogCountSumModel>();
        UDate to = date.cloneUDate();
        to.setMaxHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("    LOG_SUM.LOG_DATE,");
            sql.addSql("    LOG_SUM.SEND_CNT,");
            sql.addSql("    LOG_SUM.MAX_DATE,");
            sql.addSql("    LOG_SUM.USE_CNT,");
            sql.addSql("    LOG_SUM.USER_CNT,");
            sql.addSql("    LOG_SUM.GROUP_CNT");
            sql.addSql("  from");
            sql.addSql("    (");
            sql.addSql("     select");
            sql.addSql("       cast(CLC_DATE as date) as LOG_DATE,");
            sql.addSql("       count(*) as SEND_CNT,");
            sql.addSql("       max(CLC_DATE) as MAX_DATE,");
            sql.addSql("       count(distinct CLC_USR_SID) as USE_CNT,");
            sql.addSql("       count(case when CLC_CHAT_KBN = ?");
            sql.addSql("        then 1 else null end) as USER_CNT,");
            sql.addSql("       count(case when CLC_CHAT_KBN = ?");
            sql.addSql("        then 1 else null end) as GROUP_CNT");
            sql.addSql("     from");
            sql.addSql("       CHT_LOG_COUNT");
            sql.addSql("     where ");
            sql.addSql("       CLC_DATE <= ?");
            sql.addSql("     group by");
            sql.addSql("       LOG_DATE");
            sql.addSql("    ) as LOG_SUM");
            sql.addSql("   where");
            sql.addSql("    not exists (");
            sql.addSql("      select ");
            sql.addSql("        1");
            sql.addSql("      from ");
            sql.addSql("        CHT_LOG_COUNT_SUM");
            sql.addSql("      where");
            sql.addSql("        LOG_SUM.LOG_DATE = CHT_LOG_COUNT_SUM.CLS_DATE");
            sql.addSql("      and");
            sql.addSql("        LOG_SUM.MAX_DATE = CHT_LOG_COUNT_SUM.CLS_EDATE");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   LOG_SUM.LOG_DATE");
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addDateValue(to);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChtLogCountSumModel model = new ChtLogCountSumModel();
                model.setClsSendCnt(rs.getInt("SEND_CNT"));
                model.setClsSendUcnt(rs.getInt("USER_CNT"));
                model.setClsSendGcnt(rs.getInt("GROUP_CNT"));
                model.setClsUserCnt(rs.getInt("USE_CNT"));
                UDate blsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
                model.setClsDate(blsDate);
                model.setClsYearMonth(Integer.parseInt(blsDate.getDateString().substring(0, 6)));
                model.setClsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE")));
                logSumList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return logSumList;
    }

    /**
     * <p>Create CHT_LOG_COUNT_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtLogCountSumModel
     * @throws SQLException SQL実行例外
     */
    private ChtLogCountSumModel __getChtLogCountSumFromRs(ResultSet rs) throws SQLException {
        ChtLogCountSumModel bean = new ChtLogCountSumModel();
        bean.setClsSendCnt(rs.getInt("CLS_SEND_CNT"));
        bean.setClsSendUcnt(rs.getInt("CLS_SEND_UCNT"));
        bean.setClsSendGcnt(rs.getInt("CLS_SEND_GCNT"));
        bean.setClsUserCnt(rs.getInt("CLS_USER_CNT"));
        bean.setClsDate(UDate.getInstanceTimestamp(rs.getTimestamp("CLS_DATE")));
        bean.setClsYearMonth(rs.getInt("CLS_YEAR_MONTH"));
        bean.setClsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CLS_EDATE")));
        return bean;
    }
}
