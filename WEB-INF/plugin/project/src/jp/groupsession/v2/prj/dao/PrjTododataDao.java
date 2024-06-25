package jp.groupsession.v2.prj.dao;

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
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;

/**
 * <p>PRJ_TODODATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTododataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTododataDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTododataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTododataDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODODATA Data Bindding JavaBean
     * @param bean PRJ_TODODATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTododataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODODATA(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PTD_NO,");
            sql.addSql("   PTD_CATEGORY,");
            sql.addSql("   PTD_TITLE,");
            sql.addSql("   PTD_DATE_PLAN,");
            sql.addSql("   PRJ_DATE_LIMIT,");
            sql.addSql("   PTD_DATE_START,");
            sql.addSql("   PTD_DATE_END,");
            sql.addSql("   PTD_PLAN_KOSU,");
            sql.addSql("   PTD_RESULTS_KOSU,");
            sql.addSql("   PTD_ALARM_KBN,");
            sql.addSql("   PTD_IMPORTANCE,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTD_CONTENT,");
            sql.addSql("   PTD_AUID,");
            sql.addSql("   PTD_ADATE,");
            sql.addSql("   PTD_EUID,");
            sql.addSql("   PTD_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getPtdNo());
            sql.addIntValue(bean.getPtdCategory());
            sql.addStrValue(bean.getPtdTitle());
            sql.addDateValue(bean.getPtdDatePlan());
            sql.addDateValue(bean.getPrjDateLimit());
            sql.addDateValue(bean.getPtdDateStart());
            sql.addDateValue(bean.getPtdDateEnd());
            sql.addDecimalValue(bean.getPtdPlanKosu());
            sql.addDecimalValue(bean.getPtdResultsKosu());
            sql.addIntValue(bean.getPtdAlarmKbn());
            sql.addIntValue(bean.getPtdImportance());
            sql.addIntValue(bean.getPshSid());
            sql.addIntValue(bean.getPtsSid());
            sql.addStrValue(bean.getPtdContent());
            sql.addIntValue(bean.getPtdAuid());
            sql.addDateValue(bean.getPtdAdate());
            sql.addIntValue(bean.getPtdEuid());
            sql.addDateValue(bean.getPtdEdate());
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
     * <p>Update PRJ_TODODATA Data Bindding JavaBean
     * @param bean PRJ_TODODATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTododataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" set ");
            sql.addSql("   PRJ_SID=?,");
            sql.addSql("   PTD_NO=?,");
            sql.addSql("   PTD_CATEGORY=?,");
            sql.addSql("   PTD_TITLE=?,");
            sql.addSql("   PTD_DATE_PLAN=?,");
            sql.addSql("   PRJ_DATE_LIMIT=?,");
            sql.addSql("   PTD_DATE_START=?,");
            sql.addSql("   PTD_DATE_END=?,");
            sql.addSql("   PTD_PLAN_KOSU=?,");
            sql.addSql("   PTD_RESULTS_KOSU=?,");
            sql.addSql("   PTD_ALARM_KBN=?,");
            sql.addSql("   PTD_IMPORTANCE=?,");
            sql.addSql("   PSH_SID=?,");
            sql.addSql("   PTS_SID=?,");
            sql.addSql("   PTD_CONTENT=?,");
            sql.addSql("   PTD_EUID=?,");
            sql.addSql("   PTD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");

            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdNo());
            sql.addIntValue(bean.getPtdCategory());
            sql.addStrValue(bean.getPtdTitle());
            sql.addDateValue(bean.getPtdDatePlan());
            sql.addDateValue(bean.getPrjDateLimit());
            sql.addDateValue(bean.getPtdDateStart());
            sql.addDateValue(bean.getPtdDateEnd());
            sql.addDecimalValue(bean.getPtdPlanKosu());
            sql.addDecimalValue(bean.getPtdResultsKosu());
            sql.addIntValue(bean.getPtdAlarmKbn());
            sql.addIntValue(bean.getPtdImportance());
            sql.addIntValue(bean.getPshSid());
            sql.addIntValue(bean.getPtsSid());
            sql.addStrValue(bean.getPtdContent());
            sql.addIntValue(bean.getPtdEuid());
            sql.addDateValue(bean.getPtdEdate());
            //where
            sql.addIntValue(bean.getPtdSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] TODO状態を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_TODODATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateStatus(PrjTododataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" set ");
            sql.addSql("   PSH_SID=?,");
            sql.addSql("   PTS_SID=?,");
            sql.addSql("   PTD_EUID=?,");
            sql.addSql("   PTD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTD_SID=?");

            sql.addIntValue(bean.getPshSid());
            sql.addIntValue(bean.getPtsSid());
            sql.addIntValue(bean.getPtdEuid());
            sql.addDateValue(bean.getPtdEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] TODO状態を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_TODODATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDate(PrjTododataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" set ");
            sql.addSql("   PSH_SID=?,");
            sql.addSql("   PTD_EUID=?,");
            sql.addSql("   PTD_EDATE=?,");
            if (bean.getPtdDatePlan() != null && bean.getPrjDateLimit() != null) {
                sql.addSql("   PTD_DATE_PLAN=?,");
                sql.addSql("   PRJ_DATE_LIMIT=?");
            }
            if (bean.getPtdDatePlan() != null && bean.getPrjDateLimit() == null) {
                sql.addSql("   PTD_DATE_PLAN=?");
            }
            if (bean.getPtdDatePlan() == null && bean.getPrjDateLimit() != null) {
                sql.addSql("   PRJ_DATE_LIMIT=?");
            }
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTD_SID=?");

            sql.addIntValue(bean.getPshSid());
            sql.addIntValue(bean.getPtdEuid());
            sql.addDateValue(bean.getPtdEdate());
            if (bean.getPtdDatePlan() != null) {
                sql.addDateValue(bean.getPtdDatePlan());
            }
            if (bean.getPrjDateLimit() != null) {
                sql.addDateValue(bean.getPrjDateLimit());
            }
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] TODOの実績を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_TODODATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateZisseki(PrjTododataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" set ");
            sql.addSql("   PTD_DATE_START = ?,");
            sql.addSql("   PTD_DATE_END = ?,");
            sql.addSql("   PTD_RESULTS_KOSU = ?,");
            sql.addSql("   PTD_EUID = ?,");
            sql.addSql("   PTD_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   PTD_SID = ?");

            sql.addDateValue(bean.getPtdDateStart());
            sql.addDateValue(bean.getPtdDateEnd());
            sql.addDecimalValue(bean.getPtdResultsKosu());
            sql.addIntValue(bean.getPtdEuid());
            sql.addDateValue(bean.getPtdEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] プロジェクトSIDからTODOリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @return List in PRJ_TODODATAModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTododataModel> getTodoList(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTododataModel> ret = new ArrayList<PrjTododataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PTD_NO,");
            sql.addSql("   PTD_CATEGORY,");
            sql.addSql("   PTD_TITLE,");
            sql.addSql("   PTD_DATE_PLAN,");
            sql.addSql("   PRJ_DATE_LIMIT,");
            sql.addSql("   PTD_DATE_START,");
            sql.addSql("   PTD_DATE_END,");
            sql.addSql("   PTD_ALARM_KBN,");
            sql.addSql("   PTD_IMPORTANCE,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTD_CONTENT,");
            sql.addSql("   PTD_AUID,");
            sql.addSql("   PTD_ADATE,");
            sql.addSql("   PTD_EUID,");
            sql.addSql("   PTD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTododataFromRs(rs));
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
     * <br>[機  能] プロジェクトSIDからTODOSIDをを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @return List in TODOSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getTodoSidList(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTD_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("PTD_SID"));
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
     * <br>[機  能] TODOタイトルリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSidList TODOSID
     * @return TODOタイトルリスト
     * @throws SQLException SQL実行例外
     */
    public List<String> getTodoTitleList(String[] todoSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTD_TITLE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID in (");

            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(todoSidList[0]));
            for (String todoSid : todoSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(todoSid));
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("PTD_TITLE"));
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
     * <p>Select PRJ_TODODATA
     * @return PRJ_TODODATAModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTododataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjTododataModel> ret = new ArrayList<PrjTododataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PTD_NO,");
            sql.addSql("   PTD_CATEGORY,");
            sql.addSql("   PTD_TITLE,");
            sql.addSql("   PTD_DATE_PLAN,");
            sql.addSql("   PRJ_DATE_LIMIT,");
            sql.addSql("   PTD_DATE_START,");
            sql.addSql("   PTD_DATE_END,");
            sql.addSql("   PTD_PLAN_KOSU,");
            sql.addSql("   PTD_RESULTS_KOSU,");
            sql.addSql("   PTD_ALARM_KBN,");
            sql.addSql("   PTD_IMPORTANCE,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTD_CONTENT,");
            sql.addSql("   PTD_AUID,");
            sql.addSql("   PTD_ADATE,");
            sql.addSql("   PTD_EUID,");
            sql.addSql("   PTD_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTododataAllFromRs(rs));
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
     * <p>Select PRJ_TODODATA
     * @param prjSid PRJ_SID
     * @param ptdSid PTD_SID
     * @return PRJ_TODODATAModel
     * @throws SQLException SQL実行例外
     */
    public PrjTododataModel select(int prjSid, int ptdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTododataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PTD_NO,");
            sql.addSql("   PTD_CATEGORY,");
            sql.addSql("   PTD_TITLE,");
            sql.addSql("   PTD_DATE_PLAN,");
            sql.addSql("   PRJ_DATE_LIMIT,");
            sql.addSql("   PTD_DATE_START,");
            sql.addSql("   PTD_DATE_END,");
            sql.addSql("   PTD_RESULTS_KOSU,");
            sql.addSql("   PTD_ALARM_KBN,");
            sql.addSql("   PTD_IMPORTANCE,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTD_CONTENT,");
            sql.addSql("   PTD_AUID,");
            sql.addSql("   PTD_ADATE,");
            sql.addSql("   PTD_EUID,");
            sql.addSql("   PTD_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTododataFromRs(rs);
                ret.setPtdResultsKosu(rs.getBigDecimal("PTD_RESULTS_KOSU"));
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
     * <br>[機  能] TODO情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSid PTD_SID
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public ProjectItemModel getTodoData(int ptdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ProjectItemModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_TODODATA.PRJ_SID,");
            sql.addSql("   PRJ_TODODATA.PTD_NO,");
            sql.addSql("   PRJ_TODODATA.PTD_CATEGORY,");
            sql.addSql("   PRJ_TODODATA.PTD_TITLE,");
            sql.addSql("   PRJ_TODODATA.PTD_DATE_PLAN,");
            sql.addSql("   PRJ_TODODATA.PRJ_DATE_LIMIT,");
            sql.addSql("   PRJ_TODODATA.PTD_DATE_START,");
            sql.addSql("   PRJ_TODODATA.PTD_DATE_END,");
            sql.addSql("   PRJ_TODODATA.PTD_PLAN_KOSU,");
            sql.addSql("   PRJ_TODODATA.PTD_RESULTS_KOSU,");
            sql.addSql("   PRJ_TODODATA.PTD_ALARM_KBN,");
            sql.addSql("   PRJ_TODODATA.PTD_IMPORTANCE,");
            sql.addSql("   PRJ_TODODATA.PSH_SID,");
            sql.addSql("   PRJ_TODODATA.PTS_SID,");
            sql.addSql("   PRJ_TODODATA.PTD_CONTENT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   PRJ_TODODATA.PTD_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODODATA.PTD_SID = ?");
            sql.addIntValue(ptdSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new ProjectItemModel();
                ret.setProjectSid(rs.getInt("PRJ_SID"));
                ret.setKanriNo(rs.getInt("PTD_NO"));
                ret.setCategorySid(rs.getInt("PTD_CATEGORY"));
                ret.setTodoTitle(rs.getString("PTD_TITLE"));
                ret.setStartDate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_PLAN")));
                ret.setEndDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_LIMIT")));
                ret.setStartJissekiDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_START")));
                ret.setEndJissekiDate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_END")));
                ret.setYoteiKosu(rs.getBigDecimal("PTD_PLAN_KOSU"));
                ret.setJissekiKosu(rs.getBigDecimal("PTD_RESULTS_KOSU"));
                ret.setKeikoku(rs.getInt("PTD_ALARM_KBN"));
                ret.setJuyo(rs.getInt("PTD_IMPORTANCE"));
                ret.setHisSid(rs.getInt("PSH_SID"));
                ret.setStatus(rs.getInt("PTS_SID"));
                ret.setNaiyou(rs.getString("PTD_CONTENT"));
                ret.setAddUserSei(rs.getString("USI_SEI"));
                ret.setAddUserMei(rs.getString("USI_MEI"));
                ret.setAddUserStatus(rs.getInt("USR_JKBN"));
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
     * <br>[機  能] プロジェクトSIDからTODO情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return int TODO情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getTodoCount(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PTD_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したTODO情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSidList PTD_SID
     * @return TODO情報のデータサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTodoDataSize(List<Integer> ptdSidList) throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(PTD_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(PTD_CONTENT)) as CONTENT_SIZE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID in (");

            for (int idx = 0; idx < ptdSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(ptdSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("CONTENT_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <br>[機  能] プロジェクトSID、カテゴリからTODO情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param cate カテゴリ
     * @return int TODO情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getTodoCateCount(int prjSid, int cate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PTD_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTD_CATEGORY=?");
            sql.addIntValue(prjSid);
            sql.addIntValue(cate);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] プロジェクトSID、状態からTODO情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param status 状態
     * @return int TODO情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getTodoStatesCount(int prjSid, int status) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PTD_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTS_SID=?");
            sql.addIntValue(prjSid);
            sql.addIntValue(status);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] プロジェクトSIDを指定してTODO情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] TODOSIDを指定してTODO情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteFromTodoSid(int todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");
            sql.addIntValue(todoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create PRJ_TODODATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    private PrjTododataModel __getPrjTododataFromRs(ResultSet rs) throws SQLException {
        PrjTododataModel bean = new PrjTododataModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtdSid(rs.getInt("PTD_SID"));
        bean.setPtdNo(rs.getInt("PTD_NO"));
        bean.setPtdCategory(rs.getInt("PTD_CATEGORY"));
        bean.setPtdTitle(rs.getString("PTD_TITLE"));
        bean.setPtdDatePlan(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_PLAN")));
        bean.setPrjDateLimit(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_LIMIT")));
        bean.setPtdDateStart(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_START")));
        bean.setPtdDateEnd(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_END")));
        bean.setPtdAlarmKbn(rs.getInt("PTD_ALARM_KBN"));
        bean.setPtdImportance(rs.getInt("PTD_IMPORTANCE"));
        bean.setPshSid(rs.getInt("PSH_SID"));
        bean.setPtsSid(rs.getInt("PTS_SID"));
        bean.setPtdContent(rs.getString("PTD_CONTENT"));
        bean.setPtdAuid(rs.getInt("PTD_AUID"));
        bean.setPtdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_ADATE")));
        bean.setPtdEuid(rs.getInt("PTD_EUID"));
        bean.setPtdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_EDATE")));
        return bean;
    }
    /**
     * <p>Create PRJ_TODODATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    private PrjTododataModel __getPrjTododataAllFromRs(ResultSet rs) throws SQLException {
        PrjTododataModel bean = new PrjTododataModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtdSid(rs.getInt("PTD_SID"));
        bean.setPtdNo(rs.getInt("PTD_NO"));
        bean.setPtdCategory(rs.getInt("PTD_CATEGORY"));
        bean.setPtdTitle(rs.getString("PTD_TITLE"));
        bean.setPtdDatePlan(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_PLAN")));
        bean.setPrjDateLimit(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_LIMIT")));
        bean.setPtdDateStart(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_START")));
        bean.setPtdDateEnd(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_END")));
        bean.setPtdPlanKosu(rs.getBigDecimal("PTD_PLAN_KOSU"));
        bean.setPtdResultsKosu(rs.getBigDecimal("PTD_RESULTS_KOSU"));
        bean.setPtdAlarmKbn(rs.getInt("PTD_ALARM_KBN"));
        bean.setPtdImportance(rs.getInt("PTD_IMPORTANCE"));
        bean.setPshSid(rs.getInt("PSH_SID"));
        bean.setPtsSid(rs.getInt("PTS_SID"));
        bean.setPtdContent(rs.getString("PTD_CONTENT"));
        bean.setPtdAuid(rs.getInt("PTD_AUID"));
        bean.setPtdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_ADATE")));
        bean.setPtdEuid(rs.getInt("PTD_EUID"));
        bean.setPtdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_EDATE")));
        return bean;
    }
}
