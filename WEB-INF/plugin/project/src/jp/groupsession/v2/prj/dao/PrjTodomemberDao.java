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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.prj.model.PrjTodomemberModel;
import jp.groupsession.v2.prj.model.TodoTantoModel;
import jp.groupsession.v2.prj.model.UserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_TODOMEMBER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodomemberDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodomemberDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodomemberDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodomemberDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODOMEMBER Data Bindding JavaBean
     * @param bean PRJ_TODOMEMBER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodomemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOMEMBER(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTM_EMPLOYEE_KBN,");
            sql.addSql("   PTM_AUID,");
            sql.addSql("   PTM_ADATE,");
            sql.addSql("   PTM_EUID,");
            sql.addSql("   PTM_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPtmEmployeeKbn());
            sql.addIntValue(bean.getPtmAuid());
            sql.addDateValue(bean.getPtmAdate());
            sql.addIntValue(bean.getPtmEuid());
            sql.addDateValue(bean.getPtmEdate());
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
     * <p>Update PRJ_TODOMEMBER Data Bindding JavaBean
     * @param bean PRJ_TODOMEMBER Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodomemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOMEMBER");
            sql.addSql(" set ");
            sql.addSql("   PTM_AUID=?,");
            sql.addSql("   PTM_ADATE=?,");
            sql.addSql("   PTM_EUID=?,");
            sql.addSql("   PTM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTM_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtmAuid());
            sql.addDateValue(bean.getPtmAdate());
            sql.addIntValue(bean.getPtmEuid());
            sql.addDateValue(bean.getPtmEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPtmEmployeeKbn());

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
     * <br>[機  能] TODO担当者リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in PRJ_TODOMEMBERModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodomemberModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjTodomemberModel> ret = new ArrayList<PrjTodomemberModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTM_EMPLOYEE_KBN,");
            sql.addSql("   PTM_AUID,");
            sql.addSql("   PTM_ADATE,");
            sql.addSql("   PTM_EUID,");
            sql.addSql("   PTM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOMEMBER");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodomemberFromRs(rs));
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
     * <br>[機  能] TODO担当者リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @return List in PRJ_TODOMEMBERModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodomemberModel> getTantoBaseList(int todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjTodomemberModel> ret = new ArrayList<PrjTodomemberModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTM_EMPLOYEE_KBN,");
            sql.addSql("   PTM_AUID,");
            sql.addSql("   PTM_ADATE,");
            sql.addSql("   PTM_EUID,");
            sql.addSql("   PTM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOMEMBER");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID = ?");
            sql.addIntValue(todoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodomemberFromRs(rs));
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
     * <br>[機  能] TODO担当者情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @return TODO担当者情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<TodoTantoModel> getTodoTantoList(int prjSid, int todoSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TodoTantoModel> ret = new ArrayList<TodoTantoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   PRJ_TODOMEMBER");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = PRJ_TODOMEMBER.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   PRJ_TODOMEMBER.PRJ_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   PRJ_TODOMEMBER.PTD_SID = ?");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                TodoTantoModel model = new TodoTantoModel();
                model.setSei(rs.getString("USI_SEI"));
                model.setMei(rs.getString("USI_MEI"));
                model.setDelUser(rs.getInt("USR_JKBN") == GSConst.JTKBN_DELETE);
                model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                ret.add(model);
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
     * <p>Select PRJ_TODOMEMBER
     * @param bean PRJ_TODOMEMBER Model
     * @return PRJ_TODOMEMBERModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodomemberModel select(PrjTodomemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodomemberModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTM_EMPLOYEE_KBN,");
            sql.addSql("   PTM_AUID,");
            sql.addSql("   PTM_ADATE,");
            sql.addSql("   PTM_EUID,");
            sql.addSql("   PTM_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOMEMBER");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTM_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPtmEmployeeKbn());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodomemberFromRs(rs);
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
     * <br>[機  能] TODOの担当メンバー情報を取得する
     * <br>[解  説] メンバーの氏名、状態区分、役職等も取得
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param todoSid TODOSID
     * @return List in UserModel
     * @throws SQLException SQL実行例外
     */
    public List<UserModel> getTantoMemberList(int projectSid, int todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<UserModel> ret = new ArrayList<UserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_SID,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else CMN_POSITION.POS_NAME");
            sql.addSql("    end) as YAKUSYOKU_NAME");
            sql.addSql("  from");
            sql.addSql("    PRJ_TODOMEMBER,");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    CMN_POSITION");
            sql.addSql("  where");
            sql.addSql("    PRJ_TODOMEMBER.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("  and");
            sql.addSql("    PRJ_TODOMEMBER.PRJ_SID = ?");
            sql.addSql("  and");
            sql.addSql("    PRJ_TODOMEMBER.PTD_SID = ?");
            sql.addIntValue(projectSid);
            sql.addIntValue(todoSid);

            sql.addSql(" order by");
            sql.addSql("  YAKUSYOKU_EXIST asc,");
            sql.addSql("  CMN_POSITION.POS_SORT asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserModel bean = new UserModel();
                bean.setUserSid(rs.getInt("USR_SID"));
                bean.setStatus(rs.getInt("USR_JKBN"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setSei(rs.getString("USI_SEI"));
                bean.setMei(rs.getString("USI_MEI"));
                bean.setPosition(rs.getString("YAKUSYOKU_NAME"));
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
     * <p>Delete PRJ_TODOMEMBER
     * @param prjSid PRJ_SID
     * @return int 削除件数
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
            sql.addSql("   PRJ_TODOMEMBER");
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
     * <br>[機  能] TODOSIDを指定してTODO担当者情報を削除する
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
            sql.addSql("   PRJ_TODOMEMBER");
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
     * <p>Create PRJ_TODOMEMBER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodomemberModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodomemberModel __getPrjTodomemberFromRs(ResultSet rs) throws SQLException {
        PrjTodomemberModel bean = new PrjTodomemberModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtdSid(rs.getInt("PTD_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setPtmEmployeeKbn(rs.getInt("PTM_EMPLOYEE_KBN"));
        bean.setPtmAuid(rs.getInt("PTM_AUID"));
        bean.setPtmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTM_ADATE")));
        bean.setPtmEuid(rs.getInt("PTM_EUID"));
        bean.setPtmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PTM_EDATE")));
        return bean;
    }
}
