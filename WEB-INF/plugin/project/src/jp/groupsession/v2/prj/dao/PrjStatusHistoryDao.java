package jp.groupsession.v2.prj.dao;

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
import jp.groupsession.v2.prj.model.PrjStatusHistoryModel;
import jp.groupsession.v2.prj.model.StatusHistoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_STATUS_HISTORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjStatusHistoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjStatusHistoryDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjStatusHistoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjStatusHistoryDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_STATUS_HISTORY Data Bindding JavaBean
     * @param bean PRJ_STATUS_HISTORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjStatusHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_STATUS_HISTORY(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PSH_REASON,");
            sql.addSql("   PSH_AUID,");
            sql.addSql("   PSH_ADATE,");
            sql.addSql("   PSH_EUID,");
            sql.addSql("   PSH_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getPshSid());
            sql.addIntValue(bean.getPtsSid());
            sql.addStrValue(bean.getPshReason());
            sql.addIntValue(bean.getPshAuid());
            sql.addDateValue(bean.getPshAdate());
            sql.addIntValue(bean.getPshEuid());
            sql.addDateValue(bean.getPshEdate());
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
     * <p>Update PRJ_STATUS_HISTORY Data Bindding JavaBean
     * @param bean PRJ_STATUS_HISTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjStatusHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_STATUS_HISTORY");
            sql.addSql(" set ");
            sql.addSql("   PTS_SID=?,");
            sql.addSql("   PSH_REASON=?,");
            sql.addSql("   PSH_AUID=?,");
            sql.addSql("   PSH_ADATE=?,");
            sql.addSql("   PSH_EUID=?,");
            sql.addSql("   PSH_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   PSH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtsSid());
            sql.addStrValue(bean.getPshReason());
            sql.addIntValue(bean.getPshAuid());
            sql.addDateValue(bean.getPshAdate());
            sql.addIntValue(bean.getPshEuid());
            sql.addDateValue(bean.getPshEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getPshSid());

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
     * <br>[機  能] 変更履歴リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @return List in PRJ_STATUS_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<StatusHistoryModel> getStatusHistoryList(int prjSid, int todoSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<StatusHistoryModel> ret = new ArrayList<StatusHistoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_TODOSTATUS.PTS_NAME,");
            sql.addSql("   PRJ_TODOSTATUS.PTS_RATE,");
            sql.addSql("   PRJ_STATUS_HISTORY.PSH_SID,");
            sql.addSql("   PRJ_STATUS_HISTORY.PSH_ADATE,");
            sql.addSql("   PRJ_STATUS_HISTORY.PSH_REASON,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM.USR_JKBN");
            sql.addSql(" from");
            sql.addSql("   PRJ_STATUS_HISTORY,");
            sql.addSql("   PRJ_TODOSTATUS,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   PRJ_STATUS_HISTORY.PRJ_SID = PRJ_TODOSTATUS.PRJ_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_STATUS_HISTORY.PTS_SID = PRJ_TODOSTATUS.PTS_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_STATUS_HISTORY.PSH_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_STATUS_HISTORY.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_STATUS_HISTORY.PTD_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   PRJ_STATUS_HISTORY.PSH_SID asc");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StatusHistoryModel bean = new StatusHistoryModel();
                bean.setStatusName(rs.getString("PTS_NAME"));
                bean.setRate(rs.getInt("PTS_RATE"));
                bean.setHisSid(rs.getInt("PSH_SID"));
                bean.setAddDate(UDate.getInstanceTimestamp(rs.getTimestamp("PSH_ADATE")));
                bean.setReason(rs.getString("PSH_REASON"));
                bean.setSei(rs.getString("USI_SEI"));
                bean.setMei(rs.getString("USI_MEI"));
                bean.setUserStatus(rs.getInt("USR_JKBN"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
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
     * <p>Select PRJ_STATUS_HISTORY
     * @return PRJ_STATUS_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjStatusHistoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjStatusHistoryModel> ret = new ArrayList<PrjStatusHistoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PSH_REASON,");
            sql.addSql("   PSH_AUID,");
            sql.addSql("   PSH_ADATE,");
            sql.addSql("   PSH_EUID,");
            sql.addSql("   PSH_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_STATUS_HISTORY");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjStatusHistoryFromRs(rs));
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
     * <p>Select PRJ_STATUS_HISTORY
     * @param prjSid PRJ_SID
     * @param ptdSid PTD_SID
     * @param pshSid PSH_SID
     * @return PRJ_STATUS_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public PrjStatusHistoryModel select(int prjSid, int ptdSid, int pshSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjStatusHistoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PSH_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PSH_REASON,");
            sql.addSql("   PSH_AUID,");
            sql.addSql("   PSH_ADATE,");
            sql.addSql("   PSH_EUID,");
            sql.addSql("   PSH_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_STATUS_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   PSH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptdSid);
            sql.addIntValue(pshSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjStatusHistoryFromRs(rs);
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
     * <br>[機  能] TODO変更履歴の件数をカウントする
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSid PTD_SID
     * @return int TODO変更履歴の件数
     * @throws SQLException SQL実行例外
     */
    public int getHisCount(String[] ptdSid) throws SQLException {

        if (ptdSid == null || ptdSid.length == 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PRJ_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_STATUS_HISTORY");
            sql.addSql(" where ");

            if (ptdSid.length == 0) {
                sql.addSql("   PTD_SID=?");
                //posgre版の影響でString⇒intへキャスト
                sql.addIntValue(Integer.parseInt(ptdSid[0]));
            } else {
                sql.addSql("   PTD_SID in (");
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(ptdSid[0]));

                for (int idx = 1; idx < ptdSid.length; idx++) {
                    sql.addSql("     ,?");
                    sql.addIntValue(Integer.parseInt(ptdSid[idx]));
                }
                sql.addSql("   )");
            }

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
     * <br>[機  能] プロジェクトSID、状態からTODO変更履歴の件数をカウントする
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param status 状態
     * @return int TODO変更履歴の件数
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
            sql.addSql("   count(PRJ_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_STATUS_HISTORY");
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
     * <br>[機  能] プロジェクトSIDを指定してTODO変更履歴情報を削除する
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
            sql.addSql("   PRJ_STATUS_HISTORY");
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
     * <br>[機  能] TODOSIDを指定してTODO変更履歴情報を削除する
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
            sql.addSql("   PRJ_STATUS_HISTORY");
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
     * <br>[機  能] 状態変更履歴情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @param hisSid 変更履歴SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteHis(int prjSid, int todoSid, int hisSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_STATUS_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PSH_SID=?");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);
            sql.addIntValue(hisSid);

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
     * <p>Create PRJ_STATUS_HISTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjStatusHistoryModel
     * @throws SQLException SQL実行例外
     */
    private PrjStatusHistoryModel __getPrjStatusHistoryFromRs(ResultSet rs) throws SQLException {
        PrjStatusHistoryModel bean = new PrjStatusHistoryModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtdSid(rs.getInt("PTD_SID"));
        bean.setPshSid(rs.getInt("PSH_SID"));
        bean.setPtsSid(rs.getInt("PTS_SID"));
        bean.setPshReason(rs.getString("PSH_REASON"));
        bean.setPshAuid(rs.getInt("PSH_AUID"));
        bean.setPshAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PSH_ADATE")));
        bean.setPshEuid(rs.getInt("PSH_EUID"));
        bean.setPshEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PSH_EDATE")));
        return bean;
    }
}
